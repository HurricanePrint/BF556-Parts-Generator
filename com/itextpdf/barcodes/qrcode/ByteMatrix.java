/*     */ package com.itextpdf.barcodes.qrcode;
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
/*     */ public final class ByteMatrix
/*     */ {
/*     */   private final byte[][] bytes;
/*     */   private final int width;
/*     */   private final int height;
/*     */   
/*     */   public ByteMatrix(int width, int height) {
/*  68 */     this.bytes = new byte[height][];
/*  69 */     for (int i = 0; i < height; i++) {
/*  70 */       this.bytes[i] = new byte[width];
/*     */     }
/*  72 */     this.width = width;
/*  73 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  80 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  87 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte get(int x, int y) {
/*  97 */     return this.bytes[y][x];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[][] getArray() {
/* 104 */     return this.bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int x, int y, byte value) {
/* 114 */     this.bytes[y][x] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int x, int y, int value) {
/* 124 */     this.bytes[y][x] = (byte)value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(byte value) {
/* 132 */     for (int y = 0; y < this.height; y++) {
/* 133 */       for (int x = 0; x < this.width; x++) {
/* 134 */         this.bytes[y][x] = value;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 143 */     StringBuffer result = new StringBuffer(2 * this.width * this.height + 2);
/* 144 */     for (int y = 0; y < this.height; y++) {
/* 145 */       for (int x = 0; x < this.width; x++) {
/* 146 */         switch (this.bytes[y][x]) {
/*     */           case 0:
/* 148 */             result.append(" 0");
/*     */             break;
/*     */           case 1:
/* 151 */             result.append(" 1");
/*     */             break;
/*     */           default:
/* 154 */             result.append("  ");
/*     */             break;
/*     */         } 
/*     */       } 
/* 158 */       result.append('\n');
/*     */     } 
/* 160 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/ByteMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */