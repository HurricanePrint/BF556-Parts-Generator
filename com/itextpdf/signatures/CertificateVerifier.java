/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateVerifier
/*     */ {
/*     */   protected CertificateVerifier verifier;
/*     */   protected boolean onlineCheckingAllowed = true;
/*     */   
/*     */   public CertificateVerifier(CertificateVerifier verifier) {
/*  74 */     this.verifier = verifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnlineCheckingAllowed(boolean onlineCheckingAllowed) {
/*  83 */     this.onlineCheckingAllowed = onlineCheckingAllowed;
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
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
/*  99 */     if (signDate != null) {
/* 100 */       signCert.checkValidity(signDate);
/*     */     }
/* 102 */     if (issuerCert != null) {
/* 103 */       signCert.verify(issuerCert.getPublicKey());
/*     */     }
/*     */     else {
/*     */       
/* 107 */       signCert.verify(signCert.getPublicKey());
/*     */     } 
/* 109 */     List<VerificationOK> result = new ArrayList<>();
/* 110 */     if (this.verifier != null)
/* 111 */       result.addAll(this.verifier.verify(signCert, issuerCert, signDate)); 
/* 112 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/CertificateVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */