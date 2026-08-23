/*    */ package com.itextpdf.signatures;
/*    */ 
/*    */ import java.security.cert.X509Certificate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VerificationOK
/*    */ {
/*    */   protected X509Certificate certificate;
/*    */   protected Class<? extends CertificateVerifier> verifierClass;
/*    */   protected String message;
/*    */   
/*    */   public VerificationOK(X509Certificate certificate, Class<? extends CertificateVerifier> verifierClass, String message) {
/* 70 */     this.certificate = certificate;
/* 71 */     this.verifierClass = verifierClass;
/* 72 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 80 */     StringBuilder sb = new StringBuilder();
/* 81 */     if (this.certificate != null) {
/* 82 */       sb.append(this.certificate.getSubjectDN().getName());
/* 83 */       sb.append(" verified with ");
/*    */     } 
/* 85 */     sb.append(this.verifierClass.getName());
/* 86 */     sb.append(": ");
/* 87 */     sb.append(this.message);
/* 88 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/VerificationOK.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */