/*     */ package com.itextpdf.kernel.crypto;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OutputStreamAesEncryption
/*     */   extends OutputStreamEncryption
/*     */ {
/*     */   protected AESCipher cipher;
/*     */   private boolean finished;
/*     */   
/*     */   public OutputStreamAesEncryption(OutputStream out, byte[] key, int off, int len) {
/*  61 */     super(out);
/*  62 */     byte[] iv = IVGenerator.getIV();
/*  63 */     byte[] nkey = new byte[len];
/*  64 */     System.arraycopy(key, off, nkey, 0, len);
/*  65 */     this.cipher = new AESCipher(true, nkey, iv);
/*     */     try {
/*  67 */       write(iv);
/*  68 */     } catch (IOException e) {
/*  69 */       throw new PdfException("PdfEncryption exception.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStreamAesEncryption(OutputStream out, byte[] key) {
/*  79 */     this(out, key, 0, key.length);
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
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 111 */     byte[] b2 = this.cipher.update(b, off, len);
/* 112 */     if (b2 == null || b2.length == 0)
/*     */       return; 
/* 114 */     this.out.write(b2, 0, b2.length);
/*     */   }
/*     */   
/*     */   public void finish() {
/* 118 */     if (!this.finished) {
/* 119 */       this.finished = true;
/*     */       
/* 121 */       byte[] b = this.cipher.doFinal();
/*     */       try {
/* 123 */         this.out.write(b, 0, b.length);
/* 124 */       } catch (IOException e) {
/* 125 */         throw new PdfException("PdfEncryption exception.", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/OutputStreamAesEncryption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */