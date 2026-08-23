/*     */ package com.itextpdf.kernel.crypto;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ARCFOUREncryption
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1450279022122017100L;
/*  52 */   private byte[] state = new byte[256];
/*     */ 
/*     */   
/*     */   private int x;
/*     */ 
/*     */   
/*     */   private int y;
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareARCFOURKey(byte[] key) {
/*  63 */     prepareARCFOURKey(key, 0, key.length);
/*     */   }
/*     */   
/*     */   public void prepareARCFOURKey(byte[] key, int off, int len) {
/*  67 */     int index1 = 0;
/*  68 */     int index2 = 0;
/*  69 */     for (int k = 0; k < 256; k++) {
/*  70 */       this.state[k] = (byte)k;
/*     */     }
/*  72 */     this.x = 0;
/*  73 */     this.y = 0;
/*     */     
/*  75 */     for (int i = 0; i < 256; i++) {
/*  76 */       index2 = key[index1 + off] + this.state[i] + index2 & 0xFF;
/*  77 */       byte tmp = this.state[i];
/*  78 */       this.state[i] = this.state[index2];
/*  79 */       this.state[index2] = tmp;
/*  80 */       index1 = (index1 + 1) % len;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void encryptARCFOUR(byte[] dataIn, int off, int len, byte[] dataOut, int offOut) {
/*  85 */     int length = len + off;
/*     */     
/*  87 */     for (int k = off; k < length; k++) {
/*  88 */       this.x = this.x + 1 & 0xFF;
/*  89 */       this.y = this.state[this.x] + this.y & 0xFF;
/*  90 */       byte tmp = this.state[this.x];
/*  91 */       this.state[this.x] = this.state[this.y];
/*  92 */       this.state[this.y] = tmp;
/*  93 */       dataOut[k - off + offOut] = (byte)(dataIn[k] ^ this.state[this.state[this.x] + this.state[this.y] & 0xFF]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void encryptARCFOUR(byte[] data, int off, int len) {
/*  98 */     encryptARCFOUR(data, off, len, data, off);
/*     */   }
/*     */   
/*     */   public void encryptARCFOUR(byte[] dataIn, byte[] dataOut) {
/* 102 */     encryptARCFOUR(dataIn, 0, dataIn.length, dataOut, 0);
/*     */   }
/*     */   
/*     */   public void encryptARCFOUR(byte[] data) {
/* 106 */     encryptARCFOUR(data, 0, data.length, data, 0);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/ARCFOUREncryption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */