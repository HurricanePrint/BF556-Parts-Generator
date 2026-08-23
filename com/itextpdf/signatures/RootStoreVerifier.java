/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStore;
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
/*     */ public class RootStoreVerifier
/*     */   extends CertificateVerifier
/*     */ {
/*  61 */   protected KeyStore rootStore = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RootStoreVerifier(CertificateVerifier verifier) {
/*  70 */     super(verifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRootStore(KeyStore keyStore) {
/*  80 */     this.rootStore = keyStore;
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
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
/*  98 */     if (this.rootStore == null)
/*  99 */       return super.verify(signCert, issuerCert, signDate); 
/*     */     try {
/* 101 */       List<VerificationOK> result = new ArrayList<>();
/*     */       
/* 103 */       for (X509Certificate anchor : SignUtils.getCertificates(this.rootStore)) {
/*     */         try {
/* 105 */           signCert.verify(anchor.getPublicKey());
/* 106 */           result.add(new VerificationOK(signCert, (Class)getClass(), "Certificate verified against root store."));
/* 107 */           result.addAll(super.verify(signCert, issuerCert, signDate));
/* 108 */           return result;
/* 109 */         } catch (GeneralSecurityException e) {}
/*     */       } 
/*     */ 
/*     */       
/* 113 */       result.addAll(super.verify(signCert, issuerCert, signDate));
/* 114 */       return result;
/* 115 */     } catch (GeneralSecurityException e) {
/* 116 */       return super.verify(signCert, issuerCert, signDate);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/RootStoreVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */