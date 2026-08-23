/*     */ package com.itextpdf.kernel.crypto;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public abstract class OutputStreamEncryption
/*     */   extends OutputStream
/*     */ {
/*     */   protected OutputStream out;
/*  51 */   private byte[] sb = new byte[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OutputStreamEncryption(OutputStream out) {
/*  58 */     this.out = out;
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
/*     */   public void close() throws IOException {
/*  72 */     finish();
/*  73 */     this.out.close();
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
/*     */   public void flush() throws IOException {
/*  89 */     this.out.flush();
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
/*     */   public void write(byte[] b) throws IOException {
/* 103 */     write(b, 0, b.length);
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
/*     */   public void write(int b) throws IOException {
/* 121 */     this.sb[0] = (byte)b;
/* 122 */     write(this.sb, 0, 1);
/*     */   }
/*     */   
/*     */   public abstract void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   public abstract void finish();
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/OutputStreamEncryption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */