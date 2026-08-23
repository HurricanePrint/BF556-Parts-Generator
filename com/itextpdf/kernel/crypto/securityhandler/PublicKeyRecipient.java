/*    */ package com.itextpdf.kernel.crypto.securityhandler;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.security.cert.Certificate;
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
/*    */ public class PublicKeyRecipient
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6985649182567287907L;
/* 52 */   private Certificate certificate = null;
/*    */   
/* 54 */   private int permission = 0;
/*    */   
/* 56 */   protected byte[] cms = null;
/*    */   
/*    */   public PublicKeyRecipient(Certificate certificate, int permission) {
/* 59 */     this.certificate = certificate;
/* 60 */     this.permission = permission;
/*    */   }
/*    */   
/*    */   public Certificate getCertificate() {
/* 64 */     return this.certificate;
/*    */   }
/*    */   
/*    */   public int getPermission() {
/* 68 */     return this.permission;
/*    */   }
/*    */   
/*    */   protected void setCms(byte[] cms) {
/* 72 */     this.cms = cms;
/*    */   }
/*    */   
/*    */   protected byte[] getCms() {
/* 76 */     return this.cms;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/PublicKeyRecipient.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */