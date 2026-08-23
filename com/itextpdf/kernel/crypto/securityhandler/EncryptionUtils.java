/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfEncryptor;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.security.IExternalDecryptionProcess;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AlgorithmParameterGenerator;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Iterator;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.bouncycastle.asn1.ASN1Encodable;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.ASN1Primitive;
/*     */ import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
/*     */ import org.bouncycastle.cert.X509CertificateHolder;
/*     */ import org.bouncycastle.cms.CMSEnvelopedData;
/*     */ import org.bouncycastle.cms.RecipientInformation;
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
/*     */ final class EncryptionUtils
/*     */ {
/*     */   static byte[] generateSeed(int seedLength) {
/*     */     byte[] seedBytes;
/*     */     try {
/*  82 */       KeyGenerator key = KeyGenerator.getInstance("AES");
/*  83 */       key.init(192, new SecureRandom());
/*  84 */       SecretKey sk = key.generateKey();
/*  85 */       seedBytes = new byte[seedLength];
/*     */       
/*  87 */       System.arraycopy(sk.getEncoded(), 0, seedBytes, 0, seedLength);
/*  88 */     } catch (NoSuchAlgorithmException e) {
/*  89 */       seedBytes = SecureRandom.getSeed(seedLength);
/*     */     } 
/*  91 */     return seedBytes;
/*     */   }
/*     */   
/*     */   static byte[] fetchEnvelopedData(Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, PdfArray recipients) {
/*     */     X509CertificateHolder certHolder;
/*  96 */     boolean foundRecipient = false;
/*  97 */     byte[] envelopedData = null;
/*     */ 
/*     */     
/*     */     try {
/* 101 */       certHolder = new X509CertificateHolder(certificate.getEncoded());
/* 102 */     } catch (Exception f) {
/* 103 */       throw new PdfException("Exception occurred with PDF document decryption. One of the possible reasons is wrong password or wrong public key certificate and private key.", f);
/*     */     } 
/* 105 */     if (externalDecryptionProcess == null) {
/* 106 */       for (int i = 0; i < recipients.size(); i++) {
/* 107 */         PdfString recipient = recipients.getAsString(i);
/*     */         
/*     */         try {
/* 110 */           CMSEnvelopedData data = new CMSEnvelopedData(recipient.getValueBytes());
/* 111 */           Iterator<RecipientInformation> recipientCertificatesIt = data.getRecipientInfos().getRecipients().iterator();
/* 112 */           while (recipientCertificatesIt.hasNext()) {
/* 113 */             RecipientInformation recipientInfo = recipientCertificatesIt.next();
/*     */             
/* 115 */             if (recipientInfo.getRID().match(certHolder) && !foundRecipient) {
/* 116 */               envelopedData = PdfEncryptor.getContent(recipientInfo, (PrivateKey)certificateKey, certificateKeyProvider);
/* 117 */               foundRecipient = true;
/*     */             } 
/*     */           } 
/* 120 */         } catch (Exception f) {
/* 121 */           throw new PdfException("Exception occurred with PDF document decryption. One of the possible reasons is wrong password or wrong public key certificate and private key.", f);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 125 */       for (int i = 0; i < recipients.size(); i++) {
/* 126 */         PdfString recipient = recipients.getAsString(i);
/*     */         
/*     */         try {
/* 129 */           CMSEnvelopedData data = new CMSEnvelopedData(recipient.getValueBytes());
/* 130 */           RecipientInformation recipientInfo = data.getRecipientInfos().get(externalDecryptionProcess.getCmsRecipientId());
/* 131 */           if (recipientInfo != null) {
/* 132 */             envelopedData = recipientInfo.getContent(externalDecryptionProcess.getCmsRecipient());
/* 133 */             foundRecipient = true;
/*     */           } 
/* 135 */         } catch (Exception f) {
/* 136 */           throw new PdfException("Exception occurred with PDF document decryption. One of the possible reasons is wrong password or wrong public key certificate and private key.", f);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     if (!foundRecipient || envelopedData == null) {
/* 142 */       throw new PdfException("Bad public key certificate and/or private key.");
/*     */     }
/* 144 */     return envelopedData;
/*     */   }
/*     */ 
/*     */   
/*     */   static byte[] cipherBytes(X509Certificate x509certificate, byte[] abyte0, AlgorithmIdentifier algorithmidentifier) throws GeneralSecurityException {
/* 149 */     Cipher cipher = Cipher.getInstance(algorithmidentifier.getAlgorithm().getId());
/*     */     try {
/* 151 */       cipher.init(1, x509certificate);
/* 152 */     } catch (InvalidKeyException e) {
/* 153 */       cipher.init(1, x509certificate.getPublicKey());
/*     */     } 
/* 155 */     return cipher.doFinal(abyte0);
/*     */   }
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
/*     */   static DERForRecipientParams calculateDERForRecipientParams(byte[] in) throws IOException, GeneralSecurityException {
/* 169 */     String s = "1.2.840.113549.3.2";
/* 170 */     DERForRecipientParams parameters = new DERForRecipientParams();
/*     */     
/* 172 */     AlgorithmParameterGenerator algorithmparametergenerator = AlgorithmParameterGenerator.getInstance(s);
/* 173 */     AlgorithmParameters algorithmparameters = algorithmparametergenerator.generateParameters();
/* 174 */     ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(algorithmparameters.getEncoded("ASN.1"));
/* 175 */     ASN1InputStream asn1inputstream = new ASN1InputStream(bytearrayinputstream);
/* 176 */     ASN1Primitive derobject = asn1inputstream.readObject();
/* 177 */     KeyGenerator keygenerator = KeyGenerator.getInstance(s);
/* 178 */     keygenerator.init(128);
/* 179 */     SecretKey secretkey = keygenerator.generateKey();
/* 180 */     Cipher cipher = Cipher.getInstance(s);
/* 181 */     cipher.init(1, secretkey, algorithmparameters);
/*     */     
/* 183 */     parameters.abyte0 = secretkey.getEncoded();
/* 184 */     parameters.abyte1 = cipher.doFinal(in);
/* 185 */     parameters.algorithmIdentifier = new AlgorithmIdentifier(new ASN1ObjectIdentifier(s), (ASN1Encodable)derobject);
/*     */     
/* 187 */     return parameters;
/*     */   }
/*     */   
/*     */   static class DERForRecipientParams {
/*     */     byte[] abyte0;
/*     */     byte[] abyte1;
/*     */     AlgorithmIdentifier algorithmIdentifier;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/EncryptionUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */