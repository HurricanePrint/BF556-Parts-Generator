/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
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
/*     */ public class CRLVerifier
/*     */   extends RootStoreVerifier
/*     */ {
/*  65 */   protected static final Logger LOGGER = LoggerFactory.getLogger(CRLVerifier.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<X509CRL> crls;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLVerifier(CertificateVerifier verifier, List<X509CRL> crls) {
/*  76 */     super(verifier);
/*  77 */     this.crls = crls;
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
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
/*  92 */     List<VerificationOK> result = new ArrayList<>();
/*  93 */     int validCrlsFound = 0;
/*     */     
/*  95 */     if (this.crls != null) {
/*  96 */       for (X509CRL crl : this.crls) {
/*  97 */         if (verify(crl, signCert, issuerCert, signDate)) {
/*  98 */           validCrlsFound++;
/*     */         }
/*     */       } 
/*     */     }
/* 102 */     boolean online = false;
/* 103 */     if (this.onlineCheckingAllowed && validCrlsFound == 0 && 
/* 104 */       verify(getCRL(signCert, issuerCert), signCert, issuerCert, signDate)) {
/* 105 */       validCrlsFound++;
/* 106 */       online = true;
/*     */     } 
/*     */ 
/*     */     
/* 110 */     LOGGER.info("Valid CRLs found: " + validCrlsFound);
/* 111 */     if (validCrlsFound > 0) {
/* 112 */       result.add(new VerificationOK(signCert, (Class)getClass(), "Valid CRLs found: " + validCrlsFound + (online ? " (online)" : "")));
/*     */     }
/* 114 */     if (this.verifier != null) {
/* 115 */       result.addAll(this.verifier.verify(signCert, issuerCert, signDate));
/*     */     }
/* 117 */     return result;
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
/*     */   public boolean verify(X509CRL crl, X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException {
/* 130 */     if (crl == null || signDate == TimestampConstants.UNDEFINED_TIMESTAMP_DATE) {
/* 131 */       return false;
/*     */     }
/*     */     
/* 134 */     if (crl.getIssuerX500Principal().equals(signCert.getIssuerX500Principal()) && signDate.before(crl.getNextUpdate())) {
/*     */       
/* 136 */       if (isSignatureValid(crl, issuerCert) && crl.isRevoked(signCert)) {
/* 137 */         throw new VerificationException(signCert, "The certificate has been revoked.");
/*     */       }
/* 139 */       return true;
/*     */     } 
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509CRL getCRL(X509Certificate signCert, X509Certificate issuerCert) {
/* 151 */     if (issuerCert == null) {
/* 152 */       issuerCert = signCert;
/*     */     }
/*     */     try {
/* 155 */       String crlurl = CertificateUtil.getCRLURL(signCert);
/* 156 */       if (crlurl == null)
/* 157 */         return null; 
/* 158 */       LOGGER.info("Getting CRL from " + crlurl);
/* 159 */       return (X509CRL)SignUtils.parseCrlFromStream((new URL(crlurl)).openStream());
/*     */     }
/* 161 */     catch (IOException e) {
/* 162 */       return null;
/*     */     }
/* 164 */     catch (GeneralSecurityException e) {
/* 165 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSignatureValid(X509CRL crl, X509Certificate crlIssuer) {
/* 177 */     if (crlIssuer != null) {
/*     */       try {
/* 179 */         crl.verify(crlIssuer.getPublicKey());
/* 180 */         return true;
/* 181 */       } catch (GeneralSecurityException e) {
/* 182 */         LOGGER.warn("CRL not issued by the same authority as the certificate that is being checked");
/*     */       } 
/*     */     }
/*     */     
/* 186 */     if (this.rootStore == null) {
/* 187 */       return false;
/*     */     }
/*     */     try {
/* 190 */       for (X509Certificate anchor : SignUtils.getCertificates(this.rootStore))
/*     */       {
/*     */         try {
/* 193 */           crl.verify(anchor.getPublicKey());
/* 194 */           return true;
/* 195 */         } catch (GeneralSecurityException e) {}
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 200 */     catch (GeneralSecurityException e) {
/* 201 */       return false;
/*     */     } 
/* 203 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/CRLVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */