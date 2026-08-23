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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OutputStreamStandardEncryption
/*     */   extends OutputStreamEncryption
/*     */ {
/*     */   protected ARCFOUREncryption arcfour;
/*     */   
/*     */   public OutputStreamStandardEncryption(OutputStream out, byte[] key, int off, int len) {
/*  60 */     super(out);
/*  61 */     this.arcfour = new ARCFOUREncryption();
/*  62 */     this.arcfour.prepareARCFOURKey(key, off, len);
/*     */   }
/*     */   
/*     */   public OutputStreamStandardEncryption(OutputStream out, byte[] key) {
/*  66 */     this(out, key, 0, key.length);
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
/*  98 */     byte[] b2 = new byte[Math.min(len, 4192)];
/*  99 */     while (len > 0) {
/* 100 */       int sz = Math.min(len, b2.length);
/* 101 */       this.arcfour.encryptARCFOUR(b, off, sz, b2, 0);
/* 102 */       this.out.write(b2, 0, sz);
/* 103 */       len -= sz;
/* 104 */       off += sz;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void finish() {}
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/OutputStreamStandardEncryption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */