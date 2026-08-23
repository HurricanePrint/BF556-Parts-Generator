/*    */ package com.itextpdf.signatures;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.MessageDigest;
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
/*    */ public class ProviderDigest
/*    */   implements IExternalDigest
/*    */ {
/*    */   private String provider;
/*    */   
/*    */   public ProviderDigest(String provider) {
/* 64 */     this.provider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   public MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
/* 69 */     return DigestAlgorithms.getMessageDigest(hashAlgorithm, this.provider);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/ProviderDigest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */