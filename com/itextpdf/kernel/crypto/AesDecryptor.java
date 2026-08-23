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
/*    */ public class AesDecryptor
/*    */   implements IDecryptor
/*    */ {
/*    */   private AESCipher cipher;
/*    */   private byte[] key;
/*    */   private boolean initiated;
/* 50 */   private byte[] iv = new byte[16];
/*    */ 
/*    */ 
/*    */   
/*    */   private int ivptr;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AesDecryptor(byte[] key, int off, int len) {
/* 60 */     this.key = new byte[len];
/* 61 */     System.arraycopy(key, off, this.key, 0, len);
/*    */   }
/*    */   
/*    */   public byte[] update(byte[] b, int off, int len) {
/* 65 */     if (this.initiated) {
/* 66 */       return this.cipher.update(b, off, len);
/*    */     }
/* 68 */     int left = Math.min(this.iv.length - this.ivptr, len);
/* 69 */     System.arraycopy(b, off, this.iv, this.ivptr, left);
/* 70 */     off += left;
/* 71 */     len -= left;
/* 72 */     this.ivptr += left;
/* 73 */     if (this.ivptr == this.iv.length) {
/* 74 */       this.cipher = new AESCipher(false, this.key, this.iv);
/* 75 */       this.initiated = true;
/* 76 */       if (len > 0)
/* 77 */         return this.cipher.update(b, off, len); 
/*    */     } 
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] finish() {
/* 84 */     if (this.cipher != null) {
/* 85 */       return this.cipher.doFinal();
/*    */     }
/* 87 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/AesDecryptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */