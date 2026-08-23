/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.io.util.DateTimeUtil;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.tsp.TimeStampToken;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateVerification
/*     */ {
/*  71 */   private static final Logger LOGGER = LoggerFactory.getLogger(CrlClientOnline.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String verifyCertificate(X509Certificate cert, Collection<CRL> crls) {
/*  82 */     return verifyCertificate(cert, crls, DateTimeUtil.getCurrentTimeCalendar());
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
/*     */   public static String verifyCertificate(X509Certificate cert, Collection<CRL> crls, Calendar calendar) {
/*  95 */     if (SignUtils.hasUnsupportedCriticalExtension(cert))
/*  96 */       return "Has unsupported critical extension"; 
/*     */     try {
/*  98 */       cert.checkValidity(calendar.getTime());
/*  99 */     } catch (Exception e) {
/* 100 */       return e.getMessage();
/*     */     } 
/* 102 */     if (crls != null)
/* 103 */       for (CRL crl : crls) {
/* 104 */         if (crl.isRevoked(cert)) {
/* 105 */           return "Certificate revoked";
/*     */         }
/*     */       }  
/* 108 */     return null;
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
/*     */   public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore, Collection<CRL> crls) {
/* 123 */     return verifyCertificates(certs, keystore, crls, DateTimeUtil.getCurrentTimeCalendar());
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
/*     */   public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore, Collection<CRL> crls, Calendar calendar) {
/* 138 */     List<VerificationException> result = new ArrayList<>();
/* 139 */     for (int k = 0; k < certs.length; k++) {
/* 140 */       X509Certificate cert = (X509Certificate)certs[k];
/* 141 */       String err = verifyCertificate(cert, crls, calendar);
/* 142 */       if (err != null)
/* 143 */         result.add(new VerificationException(cert, err)); 
/*     */       try {
/* 145 */         for (X509Certificate certStoreX509 : SignUtils.getCertificates(keystore)) {
/*     */           try {
/* 147 */             if (verifyCertificate(certStoreX509, crls, calendar) != null)
/*     */               continue; 
/*     */             try {
/* 150 */               cert.verify(certStoreX509.getPublicKey());
/* 151 */               return result;
/* 152 */             } catch (Exception e) {}
/*     */           
/*     */           }
/* 155 */           catch (Exception exception) {}
/*     */         }
/*     */       
/* 158 */       } catch (Exception exception) {}
/*     */       
/*     */       int j;
/* 161 */       for (j = 0; j < certs.length; j++) {
/* 162 */         if (j != k) {
/*     */           
/* 164 */           X509Certificate certNext = (X509Certificate)certs[j];
/*     */           try {
/* 166 */             cert.verify(certNext.getPublicKey());
/*     */             break;
/* 168 */           } catch (Exception exception) {}
/*     */         } 
/*     */       } 
/* 171 */       if (j == certs.length) {
/* 172 */         result.add(new VerificationException(cert, "Cannot be verified against the KeyStore or the certificate chain"));
/*     */       }
/*     */     } 
/* 175 */     if (result.size() == 0)
/* 176 */       result.add(new VerificationException((Certificate)null, "Invalid state. Possible circular certificate chain")); 
/* 177 */     return result;
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
/*     */   public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore) {
/* 190 */     return verifyCertificates(certs, keystore, DateTimeUtil.getCurrentTimeCalendar());
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
/*     */   public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore, Calendar calendar) {
/* 204 */     return verifyCertificates(certs, keystore, null, calendar);
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
/*     */   public static boolean verifyOcspCertificates(BasicOCSPResp ocsp, KeyStore keystore, String provider) {
/* 216 */     List<Exception> exceptionsThrown = new ArrayList<>();
/*     */     try {
/* 218 */       for (X509Certificate certStoreX509 : SignUtils.getCertificates(keystore)) {
/*     */         try {
/* 220 */           return SignUtils.isSignatureValid(ocsp, certStoreX509, provider);
/* 221 */         } catch (Exception ex) {
/* 222 */           exceptionsThrown.add(ex);
/*     */         } 
/*     */       } 
/* 225 */     } catch (Exception e) {
/* 226 */       exceptionsThrown.add(e);
/*     */     } 
/* 228 */     for (Exception ex : exceptionsThrown) {
/* 229 */       LOGGER.error(ex.getMessage(), ex);
/*     */     }
/* 231 */     return false;
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
/*     */   public static boolean verifyTimestampCertificates(TimeStampToken ts, KeyStore keystore, String provider) {
/* 243 */     List<Exception> exceptionsThrown = new ArrayList<>();
/*     */     try {
/* 245 */       for (X509Certificate certStoreX509 : SignUtils.getCertificates(keystore)) {
/*     */         
/*     */         try {
/* 248 */           SignUtils.isSignatureValid(ts, certStoreX509, provider);
/* 249 */           return true;
/* 250 */         } catch (Exception ex) {
/* 251 */           exceptionsThrown.add(ex);
/*     */         }
/*     */       
/*     */       } 
/* 255 */     } catch (Exception e) {
/* 256 */       exceptionsThrown.add(e);
/*     */     } 
/*     */     
/* 259 */     for (Exception ex : exceptionsThrown) {
/* 260 */       LOGGER.error(ex.getMessage(), ex);
/*     */     }
/* 262 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/CertificateVerification.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */