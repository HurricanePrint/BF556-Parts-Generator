/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.io.codec.Base64;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfEncryption;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.bouncycastle.asn1.ASN1Encodable;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.ASN1OctetString;
/*     */ import org.bouncycastle.asn1.ASN1Sequence;
/*     */ import org.bouncycastle.asn1.DERNull;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.esf.SigPolicyQualifierInfo;
/*     */ import org.bouncycastle.asn1.esf.SigPolicyQualifiers;
/*     */ import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
/*     */ import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
/*     */ import org.bouncycastle.asn1.x509.Extension;
/*     */ import org.bouncycastle.asn1.x509.Extensions;
/*     */ import org.bouncycastle.cert.X509CertificateHolder;
/*     */ import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
/*     */ import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.CertificateID;
/*     */ import org.bouncycastle.cert.ocsp.OCSPException;
/*     */ import org.bouncycastle.cert.ocsp.OCSPReq;
/*     */ import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
/*     */ import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
/*     */ import org.bouncycastle.jce.X509Principal;
/*     */ import org.bouncycastle.jce.provider.X509CertParser;
/*     */ import org.bouncycastle.operator.DigestCalculatorProvider;
/*     */ import org.bouncycastle.operator.OperatorCreationException;
/*     */ import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
/*     */ import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
/*     */ import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
/*     */ import org.bouncycastle.tsp.TSPException;
/*     */ import org.bouncycastle.tsp.TimeStampToken;
/*     */ import org.bouncycastle.x509.util.StreamParsingException;
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
/*     */ final class SignUtils
/*     */ {
/*     */   static String getPrivateKeyAlgorithm(PrivateKey pk) {
/* 119 */     String algorithm = pk.getAlgorithm();
/*     */     
/* 121 */     if (algorithm.equals("EC")) {
/* 122 */       algorithm = "ECDSA";
/*     */     }
/* 124 */     return algorithm;
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
/*     */   static CRL parseCrlFromStream(InputStream input) throws CertificateException, CRLException {
/* 136 */     return CertificateFactory.getInstance("X.509").generateCRL(input);
/*     */   }
/*     */   
/*     */   static byte[] getExtensionValueByOid(X509Certificate certificate, String oid) {
/* 140 */     return certificate.getExtensionValue(oid);
/*     */   }
/*     */   
/*     */   static MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
/* 144 */     return (new BouncyCastleDigest()).getMessageDigest(hashAlgorithm);
/*     */   }
/*     */   
/*     */   static MessageDigest getMessageDigest(String hashAlgorithm, IExternalDigest externalDigest) throws GeneralSecurityException {
/* 148 */     return externalDigest.getMessageDigest(hashAlgorithm);
/*     */   }
/*     */   
/*     */   static MessageDigest getMessageDigest(String hashAlgorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 152 */     if (provider == null || provider.startsWith("SunPKCS11") || provider.startsWith("SunMSCAPI")) {
/* 153 */       return MessageDigest.getInstance(DigestAlgorithms.normalizeDigestName(hashAlgorithm));
/*     */     }
/* 155 */     return MessageDigest.getInstance(hashAlgorithm, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   static InputStream getHttpResponse(URL urlt) throws IOException {
/* 160 */     HttpURLConnection con = (HttpURLConnection)urlt.openConnection();
/* 161 */     if (con.getResponseCode() / 100 != 2) {
/* 162 */       throw (new PdfException("Invalid http response {0}.")).setMessageParams(new Object[] { Integer.valueOf(con.getResponseCode()) });
/*     */     }
/* 164 */     return (InputStream)con.getContent();
/*     */   }
/*     */   
/*     */   static CertificateID generateCertificateId(X509Certificate issuerCert, BigInteger serialNumber, AlgorithmIdentifier digestAlgorithmIdentifier) throws OperatorCreationException, CertificateEncodingException, OCSPException {
/* 168 */     return new CertificateID((new JcaDigestCalculatorProviderBuilder())
/* 169 */         .build().get(digestAlgorithmIdentifier), (X509CertificateHolder)new JcaX509CertificateHolder(issuerCert), serialNumber);
/*     */   }
/*     */ 
/*     */   
/*     */   static CertificateID generateCertificateId(X509Certificate issuerCert, BigInteger serialNumber, ASN1ObjectIdentifier identifier) throws OperatorCreationException, CertificateEncodingException, OCSPException {
/* 174 */     return new CertificateID((new JcaDigestCalculatorProviderBuilder())
/* 175 */         .build().get(new AlgorithmIdentifier(identifier, (ASN1Encodable)DERNull.INSTANCE)), (X509CertificateHolder)new JcaX509CertificateHolder(issuerCert), serialNumber);
/*     */   }
/*     */ 
/*     */   
/*     */   static OCSPReq generateOcspRequestWithNonce(CertificateID id) throws IOException, OCSPException {
/* 180 */     OCSPReqBuilder gen = new OCSPReqBuilder();
/* 181 */     gen.addRequest(id);
/*     */     
/* 183 */     Extension ext = new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false, (ASN1OctetString)new DEROctetString((new DEROctetString(PdfEncryption.generateNewDocumentId())).getEncoded()));
/* 184 */     gen.setRequestExtensions(new Extensions(new Extension[] { ext }));
/* 185 */     return gen.build();
/*     */   }
/*     */   
/*     */   static InputStream getHttpResponseForOcspRequest(byte[] request, URL urlt) throws IOException {
/* 189 */     HttpURLConnection con = (HttpURLConnection)urlt.openConnection();
/* 190 */     con.setRequestProperty("Content-Type", "application/ocsp-request");
/* 191 */     con.setRequestProperty("Accept", "application/ocsp-response");
/* 192 */     con.setDoOutput(true);
/* 193 */     OutputStream out = con.getOutputStream();
/* 194 */     DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(out));
/* 195 */     dataOut.write(request);
/* 196 */     dataOut.flush();
/* 197 */     dataOut.close();
/* 198 */     if (con.getResponseCode() / 100 != 2) {
/* 199 */       throw (new PdfException("Invalid http response {0}.")).setMessageParams(new Object[] { Integer.valueOf(con.getResponseCode()) });
/*     */     }
/*     */     
/* 202 */     return (InputStream)con.getContent();
/*     */   }
/*     */   
/*     */   static boolean isSignatureValid(BasicOCSPResp validator, Certificate certStoreX509, String provider) throws OperatorCreationException, OCSPException {
/* 206 */     if (provider == null) provider = "BC"; 
/* 207 */     return validator.isSignatureValid((new JcaContentVerifierProviderBuilder()).setProvider(provider).build(certStoreX509.getPublicKey()));
/*     */   }
/*     */   
/*     */   static void isSignatureValid(TimeStampToken validator, X509Certificate certStoreX509, String provider) throws OperatorCreationException, TSPException {
/* 211 */     if (provider == null) provider = "BC"; 
/* 212 */     validator.validate((new JcaSimpleSignerInfoVerifierBuilder()).setProvider(provider).build(certStoreX509));
/*     */   }
/*     */   
/*     */   static boolean checkIfIssuersMatch(CertificateID certID, X509Certificate issuerCert) throws CertificateEncodingException, IOException, OCSPException {
/* 216 */     return certID.matchesIssuer(new X509CertificateHolder(issuerCert.getEncoded()), (DigestCalculatorProvider)new BcDigestCalculatorProvider());
/*     */   }
/*     */   
/*     */   static Date add180Sec(Date date) {
/* 220 */     return new Date(date.getTime() + 180000L);
/*     */   }
/*     */   
/*     */   static Iterable<X509Certificate> getCertsFromOcspResponse(BasicOCSPResp ocspResp) {
/* 224 */     List<X509Certificate> certs = new ArrayList<>();
/* 225 */     X509CertificateHolder[] certHolders = ocspResp.getCerts();
/* 226 */     JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
/* 227 */     for (X509CertificateHolder certHolder : certHolders) {
/*     */       try {
/* 229 */         certs.add(converter.getCertificate(certHolder));
/* 230 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 233 */     return certs;
/*     */   }
/*     */   
/*     */   static Collection<Certificate> readAllCerts(byte[] contentsKey) throws StreamParsingException {
/* 237 */     X509CertParser cr = new X509CertParser();
/* 238 */     cr.engineInit(new ByteArrayInputStream(contentsKey));
/* 239 */     return cr.engineReadAll();
/*     */   }
/*     */   
/*     */   static <T> T getFirstElement(Iterable<T> iterable) {
/* 243 */     return iterable.iterator().next();
/*     */   }
/*     */   
/*     */   static X509Principal getIssuerX509Name(ASN1Sequence issuerAndSerialNumber) throws IOException {
/* 247 */     return new X509Principal(issuerAndSerialNumber.getObjectAt(0).toASN1Primitive().getEncoded());
/*     */   }
/*     */   
/*     */   public static String dateToString(Calendar signDate) {
/* 251 */     return (new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z")).format(signDate.getTime());
/*     */   }
/*     */   
/*     */   static class TsaResponse {
/*     */     String encoding;
/*     */     InputStream tsaResponseStream; }
/*     */   
/*     */   static TsaResponse getTsaResponseForUserRequest(String tsaUrl, byte[] requestBytes, String tsaUsername, String tsaPassword) throws IOException {
/*     */     URLConnection tsaConnection;
/* 260 */     URL url = new URL(tsaUrl);
/*     */     
/*     */     try {
/* 263 */       tsaConnection = url.openConnection();
/*     */     }
/* 265 */     catch (IOException ioe) {
/* 266 */       throw (new PdfException("Failed to get TSA response from {0}.")).setMessageParams(new Object[] { tsaUrl });
/*     */     } 
/* 268 */     tsaConnection.setDoInput(true);
/* 269 */     tsaConnection.setDoOutput(true);
/* 270 */     tsaConnection.setUseCaches(false);
/* 271 */     tsaConnection.setRequestProperty("Content-Type", "application/timestamp-query");
/*     */     
/* 273 */     tsaConnection.setRequestProperty("Content-Transfer-Encoding", "binary");
/*     */     
/* 275 */     if (tsaUsername != null && !tsaUsername.equals("")) {
/* 276 */       String userPassword = tsaUsername + ":" + tsaPassword;
/* 277 */       tsaConnection.setRequestProperty("Authorization", "Basic " + 
/* 278 */           Base64.encodeBytes(userPassword.getBytes(StandardCharsets.UTF_8), 8));
/*     */     } 
/* 280 */     OutputStream out = tsaConnection.getOutputStream();
/* 281 */     out.write(requestBytes);
/* 282 */     out.close();
/*     */     
/* 284 */     TsaResponse response = new TsaResponse();
/* 285 */     response.tsaResponseStream = tsaConnection.getInputStream();
/* 286 */     response.encoding = tsaConnection.getContentEncoding();
/* 287 */     return response;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   static boolean hasUnsupportedCriticalExtension(X509Certificate cert) {
/* 306 */     if (cert == null) {
/* 307 */       throw new IllegalArgumentException("X509Certificate can't be null.");
/*     */     }
/*     */     
/* 310 */     if (cert.hasUnsupportedCriticalExtension()) {
/* 311 */       for (String oid : cert.getCriticalExtensionOIDs()) {
/* 312 */         if (OID.X509Extensions.SUPPORTED_CRITICAL_EXTENSIONS.contains(oid)) {
/*     */           continue;
/*     */         }
/* 315 */         return true;
/*     */       } 
/*     */     }
/*     */     
/* 319 */     return false;
/*     */   }
/*     */   
/*     */   static Calendar getTimeStampDate(TimeStampToken timeStampToken) {
/* 323 */     GregorianCalendar calendar = new GregorianCalendar();
/* 324 */     calendar.setTime(timeStampToken.getTimeStampInfo().getGenTime());
/* 325 */     return calendar;
/*     */   }
/*     */   
/*     */   static Signature getSignatureHelper(String algorithm, String provider) throws NoSuchProviderException, NoSuchAlgorithmException {
/* 329 */     return (provider == null) ? Signature.getInstance(algorithm) : Signature.getInstance(algorithm, provider);
/*     */   }
/*     */   
/*     */   static boolean verifyCertificateSignature(X509Certificate certificate, PublicKey issuerPublicKey, String provider) {
/* 333 */     boolean res = false;
/*     */     try {
/* 335 */       if (provider == null) {
/* 336 */         certificate.verify(issuerPublicKey);
/*     */       } else {
/* 338 */         certificate.verify(issuerPublicKey, provider);
/*     */       } 
/* 340 */       res = true;
/* 341 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 344 */     return res;
/*     */   }
/*     */   
/*     */   static SigPolicyQualifiers createSigPolicyQualifiers(SigPolicyQualifierInfo... sigPolicyQualifierInfo) {
/* 348 */     return new SigPolicyQualifiers(sigPolicyQualifierInfo);
/*     */   }
/*     */   
/*     */   static Iterable<X509Certificate> getCertificates(final KeyStore keyStore) throws KeyStoreException {
/* 352 */     final Enumeration<String> keyStoreAliases = keyStore.aliases();
/* 353 */     return new Iterable<X509Certificate>()
/*     */       {
/*     */         public Iterator<X509Certificate> iterator() {
/* 356 */           return new Iterator<X509Certificate>() {
/*     */               private X509Certificate nextCert;
/*     */               
/*     */               public boolean hasNext() {
/* 360 */                 if (this.nextCert == null) {
/* 361 */                   tryToGetNextCertificate();
/*     */                 }
/* 363 */                 return (this.nextCert != null);
/*     */               }
/*     */ 
/*     */               
/*     */               public X509Certificate next() {
/* 368 */                 if (!hasNext()) {
/* 369 */                   throw new NoSuchElementException();
/*     */                 }
/* 371 */                 X509Certificate cert = this.nextCert;
/* 372 */                 this.nextCert = null;
/* 373 */                 return cert;
/*     */               }
/*     */               
/*     */               private void tryToGetNextCertificate() {
/* 377 */                 while (keyStoreAliases.hasMoreElements()) {
/*     */                   try {
/* 379 */                     String alias = keyStoreAliases.nextElement();
/* 380 */                     if (keyStore.isCertificateEntry(alias) || keyStore.isKeyEntry(alias)) {
/* 381 */                       this.nextCert = (X509Certificate)keyStore.getCertificate(alias);
/*     */                       break;
/*     */                     } 
/* 384 */                   } catch (KeyStoreException e) {}
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public void remove() {
/* 392 */                 throw new UnsupportedOperationException("remove");
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/SignUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */