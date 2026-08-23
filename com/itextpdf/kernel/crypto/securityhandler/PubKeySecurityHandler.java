/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfLiteral;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.security.IExternalDecryptionProcess;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.Key;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bouncycastle.asn1.ASN1Encodable;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.ASN1OctetString;
/*     */ import org.bouncycastle.asn1.ASN1Primitive;
/*     */ import org.bouncycastle.asn1.ASN1Set;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.DEROutputStream;
/*     */ import org.bouncycastle.asn1.DERSet;
/*     */ import org.bouncycastle.asn1.cms.ContentInfo;
/*     */ import org.bouncycastle.asn1.cms.EncryptedContentInfo;
/*     */ import org.bouncycastle.asn1.cms.EnvelopedData;
/*     */ import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
/*     */ import org.bouncycastle.asn1.cms.KeyTransRecipientInfo;
/*     */ import org.bouncycastle.asn1.cms.RecipientIdentifier;
/*     */ import org.bouncycastle.asn1.cms.RecipientInfo;
/*     */ import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
/*     */ import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
/*     */ import org.bouncycastle.asn1.x509.TBSCertificateStructure;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PubKeySecurityHandler
/*     */   extends SecurityHandler
/*     */ {
/*     */   private static final int SEED_LENGTH = 20;
/*     */   private static final long serialVersionUID = -6093031394871440268L;
/*  90 */   private List<PublicKeyRecipient> recipients = null;
/*     */   
/*     */   private byte[] seed;
/*     */   
/*     */   protected PubKeySecurityHandler() {
/*  95 */     this.seed = EncryptionUtils.generateSeed(20);
/*  96 */     this.recipients = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] computeGlobalKey(String messageDigestAlgorithm, boolean encryptMetadata) {
/*     */     MessageDigest md;
/*     */     try {
/* 104 */       md = MessageDigest.getInstance(messageDigestAlgorithm);
/* 105 */       md.update(getSeed());
/* 106 */       for (int i = 0; i < getRecipientsSize(); i++) {
/* 107 */         byte[] encodedRecipient = getEncodedRecipient(i);
/* 108 */         md.update(encodedRecipient);
/*     */       } 
/* 110 */       if (!encryptMetadata) {
/* 111 */         md.update(new byte[] { -1, -1, -1, -1 });
/*     */       }
/* 113 */     } catch (Exception e) {
/* 114 */       throw new PdfException("PdfEncryption exception.", e);
/*     */     } 
/*     */     
/* 117 */     return md.digest();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static byte[] computeGlobalKeyOnReading(PdfDictionary encryptionDictionary, PrivateKey certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata, String digestAlgorithm) {
/*     */     byte[] encryptionKey;
/* 124 */     PdfArray recipients = encryptionDictionary.getAsArray(PdfName.Recipients);
/* 125 */     if (recipients == null)
/*     */     {
/*     */       
/* 128 */       recipients = encryptionDictionary.getAsDictionary(PdfName.CF).getAsDictionary(PdfName.DefaultCryptFilter).getAsArray(PdfName.Recipients);
/*     */     }
/*     */     
/* 131 */     byte[] envelopedData = EncryptionUtils.fetchEnvelopedData(certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, recipients);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 137 */       MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
/* 138 */       md.update(envelopedData, 0, 20);
/* 139 */       for (int i = 0; i < recipients.size(); i++) {
/* 140 */         byte[] encodedRecipient = recipients.getAsString(i).getValueBytes();
/* 141 */         md.update(encodedRecipient);
/*     */       } 
/* 143 */       if (!encryptMetadata) {
/* 144 */         md.update(new byte[] { -1, -1, -1, -1 });
/*     */       }
/* 146 */       encryptionKey = md.digest();
/* 147 */     } catch (Exception f) {
/* 148 */       throw new PdfException("Exception occurred with PDF document decryption. One of the possible reasons is wrong password or wrong public key certificate and private key.", f);
/*     */     } 
/* 150 */     return encryptionKey;
/*     */   }
/*     */   
/*     */   protected void addAllRecipients(Certificate[] certs, int[] permissions) {
/* 154 */     if (certs != null) {
/* 155 */       for (int i = 0; i < certs.length; i++) {
/* 156 */         addRecipient(certs[i], permissions[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected PdfArray createRecipientsArray() {
/*     */     PdfArray recipients;
/*     */     try {
/* 164 */       recipients = getEncodedRecipients();
/* 165 */     } catch (Exception e) {
/* 166 */       throw new PdfException("PdfEncryption exception.", e);
/*     */     } 
/* 168 */     return recipients;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void setPubSecSpecificHandlerDicEntries(PdfDictionary paramPdfDictionary, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   protected abstract String getDigestAlgorithm();
/*     */   
/*     */   protected abstract void initKey(byte[] paramArrayOfbyte, int paramInt);
/*     */   
/*     */   protected void initKeyAndFillDictionary(PdfDictionary encryptionDictionary, Certificate[] certs, int[] permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 179 */     addAllRecipients(certs, permissions);
/*     */     
/* 181 */     Integer keyLen = encryptionDictionary.getAsInt(PdfName.Length);
/* 182 */     int keyLength = (keyLen != null) ? keyLen.intValue() : 40;
/*     */     
/* 184 */     String digestAlgorithm = getDigestAlgorithm();
/* 185 */     byte[] digest = computeGlobalKey(digestAlgorithm, encryptMetadata);
/* 186 */     initKey(digest, keyLength);
/*     */     
/* 188 */     setPubSecSpecificHandlerDicEntries(encryptionDictionary, encryptMetadata, embeddedFilesOnly);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initKeyAndReadDictionary(PdfDictionary encryptionDictionary, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata) {
/* 194 */     String digestAlgorithm = getDigestAlgorithm();
/* 195 */     byte[] encryptionKey = computeGlobalKeyOnReading(encryptionDictionary, (PrivateKey)certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, encryptMetadata, digestAlgorithm);
/*     */ 
/*     */     
/* 198 */     Integer keyLen = encryptionDictionary.getAsInt(PdfName.Length);
/* 199 */     int keyLength = (keyLen != null) ? keyLen.intValue() : 40;
/* 200 */     initKey(encryptionKey, keyLength);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addRecipient(Certificate cert, int permission) {
/* 205 */     this.recipients.add(new PublicKeyRecipient(cert, permission));
/*     */   }
/*     */   
/*     */   private byte[] getSeed() {
/* 209 */     byte[] clonedSeed = new byte[this.seed.length];
/* 210 */     System.arraycopy(this.seed, 0, clonedSeed, 0, this.seed.length);
/* 211 */     return clonedSeed;
/*     */   }
/*     */   
/*     */   private int getRecipientsSize() {
/* 215 */     return this.recipients.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] getEncodedRecipient(int index) throws IOException, GeneralSecurityException {
/* 220 */     PublicKeyRecipient recipient = this.recipients.get(index);
/* 221 */     byte[] cms = recipient.getCms();
/*     */     
/* 223 */     if (cms != null) return cms;
/*     */     
/* 225 */     Certificate certificate = recipient.getCertificate();
/*     */     
/* 227 */     int permission = recipient.getPermission();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     permission |= 0xFFFFF0C0;
/* 234 */     permission &= 0xFFFFFFFC;
/* 235 */     permission++;
/*     */     
/* 237 */     byte[] pkcs7input = new byte[24];
/*     */     
/* 239 */     byte one = (byte)permission;
/* 240 */     byte two = (byte)(permission >> 8);
/* 241 */     byte three = (byte)(permission >> 16);
/* 242 */     byte four = (byte)(permission >> 24);
/*     */ 
/*     */     
/* 245 */     System.arraycopy(this.seed, 0, pkcs7input, 0, 20);
/*     */     
/* 247 */     pkcs7input[20] = four;
/* 248 */     pkcs7input[21] = three;
/* 249 */     pkcs7input[22] = two;
/* 250 */     pkcs7input[23] = one;
/*     */     
/* 252 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 253 */     DEROutputStream k = new DEROutputStream(baos);
/* 254 */     ASN1Primitive obj = createDERForRecipient(pkcs7input, (X509Certificate)certificate);
/* 255 */     k.writeObject(obj);
/* 256 */     cms = baos.toByteArray();
/* 257 */     recipient.setCms(cms);
/*     */     
/* 259 */     return cms;
/*     */   }
/*     */   
/*     */   private PdfArray getEncodedRecipients() {
/* 263 */     PdfArray EncodedRecipients = new PdfArray();
/*     */     
/* 265 */     for (int i = 0; i < this.recipients.size(); i++) {
/*     */       try {
/* 267 */         byte[] cms = getEncodedRecipient(i);
/* 268 */         EncodedRecipients.add((PdfObject)new PdfLiteral(StreamUtil.createEscapedString(cms)));
/* 269 */       } catch (GeneralSecurityException e) {
/* 270 */         EncodedRecipients = null;
/*     */         
/*     */         break;
/* 273 */       } catch (IOException e) {
/* 274 */         EncodedRecipients = null;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     return EncodedRecipients;
/*     */   }
/*     */ 
/*     */   
/*     */   private ASN1Primitive createDERForRecipient(byte[] in, X509Certificate cert) throws IOException, GeneralSecurityException {
/* 285 */     EncryptionUtils.DERForRecipientParams parameters = EncryptionUtils.calculateDERForRecipientParams(in);
/*     */     
/* 287 */     KeyTransRecipientInfo keytransrecipientinfo = computeRecipientInfo(cert, parameters.abyte0);
/* 288 */     DEROctetString deroctetstring = new DEROctetString(parameters.abyte1);
/* 289 */     DERSet derset = new DERSet((ASN1Encodable)new RecipientInfo(keytransrecipientinfo));
/* 290 */     EncryptedContentInfo encryptedcontentinfo = new EncryptedContentInfo(PKCSObjectIdentifiers.data, parameters.algorithmIdentifier, (ASN1OctetString)deroctetstring);
/*     */     
/* 292 */     EnvelopedData env = new EnvelopedData(null, (ASN1Set)derset, encryptedcontentinfo, (ASN1Set)null);
/* 293 */     ContentInfo contentinfo = new ContentInfo(PKCSObjectIdentifiers.envelopedData, (ASN1Encodable)env);
/* 294 */     return contentinfo.toASN1Primitive();
/*     */   }
/*     */ 
/*     */   
/*     */   private KeyTransRecipientInfo computeRecipientInfo(X509Certificate x509certificate, byte[] abyte0) throws GeneralSecurityException, IOException {
/* 299 */     ASN1InputStream asn1inputstream = new ASN1InputStream(new ByteArrayInputStream(x509certificate.getTBSCertificate()));
/* 300 */     TBSCertificateStructure tbscertificatestructure = TBSCertificateStructure.getInstance(asn1inputstream.readObject());
/* 301 */     assert tbscertificatestructure != null;
/* 302 */     AlgorithmIdentifier algorithmidentifier = tbscertificatestructure.getSubjectPublicKeyInfo().getAlgorithm();
/*     */ 
/*     */     
/* 305 */     IssuerAndSerialNumber issuerandserialnumber = new IssuerAndSerialNumber(tbscertificatestructure.getIssuer(), tbscertificatestructure.getSerialNumber().getValue());
/* 306 */     byte[] cipheredBytes = EncryptionUtils.cipherBytes(x509certificate, abyte0, algorithmidentifier);
/* 307 */     DEROctetString deroctetstring = new DEROctetString(cipheredBytes);
/* 308 */     RecipientIdentifier recipId = new RecipientIdentifier(issuerandserialnumber);
/* 309 */     return new KeyTransRecipientInfo(recipId, algorithmidentifier, (ASN1OctetString)deroctetstring);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/PubKeySecurityHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */