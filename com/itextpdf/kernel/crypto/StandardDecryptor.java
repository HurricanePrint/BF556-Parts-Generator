/*    */ package com.itextpdf.kernel.crypto;
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
/*    */ public class StandardDecryptor
/*    */   implements IDecryptor
/*    */ {
/*    */   protected ARCFOUREncryption arcfour;
/*    */   
/*    */   public StandardDecryptor(byte[] key, int off, int len) {
/* 58 */     this.arcfour = new ARCFOUREncryption();
/* 59 */     this.arcfour.prepareARCFOURKey(key, off, len);
/*    */   }
/*    */   
/*    */   public byte[] update(byte[] b, int off, int len) {
/* 63 */     byte[] b2 = new byte[len];
/* 64 */     this.arcfour.encryptARCFOUR(b, off, len, b2, 0);
/* 65 */     return b2;
/*    */   }
/*    */   
/*    */   public byte[] finish() {
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/StandardDecryptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */