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
/*     */ 
/*     */ final class BitMatrix
/*     */ {
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int rowSize;
/*     */   private final int[] bits;
/*     */   
/*     */   public BitMatrix(int dimension) {
/*  70 */     this(dimension, dimension);
/*     */   }
/*     */   
/*     */   public BitMatrix(int width, int height) {
/*  74 */     if (width < 1 || height < 1) {
/*  75 */       throw new IllegalArgumentException("Both dimensions must be greater than 0");
/*     */     }
/*  77 */     this.width = width;
/*  78 */     this.height = height;
/*  79 */     int rowSize = width >> 5;
/*  80 */     if ((width & 0x1F) != 0) {
/*  81 */       rowSize++;
/*     */     }
/*  83 */     this.rowSize = rowSize;
/*  84 */     this.bits = new int[rowSize * height];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean get(int x, int y) {
/*  95 */     int offset = y * this.rowSize + (x >> 5);
/*  96 */     return ((this.bits[offset] >>> (x & 0x1F) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int x, int y) {
/* 106 */     int offset = y * this.rowSize + (x >> 5);
/* 107 */     this.bits[offset] = this.bits[offset] | 1 << (x & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flip(int x, int y) {
/* 117 */     int offset = y * this.rowSize + (x >> 5);
/* 118 */     this.bits[offset] = this.bits[offset] ^ 1 << (x & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 125 */     int max = this.bits.length;
/* 126 */     for (int i = 0; i < max; i++) {
/* 127 */       this.bits[i] = 0;
/*     */     }
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
/*     */   public void setRegion(int left, int top, int width, int height) {
/* 140 */     if (top < 0 || left < 0) {
/* 141 */       throw new IllegalArgumentException("Left and top must be nonnegative");
/*     */     }
/* 143 */     if (height < 1 || width < 1) {
/* 144 */       throw new IllegalArgumentException("Height and width must be at least 1");
/*     */     }
/* 146 */     int right = left + width;
/* 147 */     int bottom = top + height;
/* 148 */     if (bottom > this.height || right > this.width) {
/* 149 */       throw new IllegalArgumentException("The region must fit inside the matrix");
/*     */     }
/* 151 */     for (int y = top; y < bottom; y++) {
/* 152 */       int offset = y * this.rowSize;
/* 153 */       for (int x = left; x < right; x++) {
/* 154 */         this.bits[offset + (x >> 5)] = this.bits[offset + (x >> 5)] | 1 << (x & 0x1F);
/*     */       }
/*     */     } 
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
/*     */   public BitArray getRow(int y, BitArray row) {
/* 168 */     if (row == null || row.getSize() < this.width) {
/* 169 */       row = new BitArray(this.width);
/*     */     }
/* 171 */     int offset = y * this.rowSize;
/* 172 */     for (int x = 0; x < this.rowSize; x++) {
/* 173 */       row.setBulk(x << 5, this.bits[offset + x]);
/*     */     }
/* 175 */     return row;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 182 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 189 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 199 */     if (this.width != this.height) {
/* 200 */       throw new RuntimeException("Can't call getDimension() on a non-square matrix");
/*     */     }
/* 202 */     return this.width;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 206 */     StringBuffer result = new StringBuffer(this.height * (this.width + 1));
/* 207 */     for (int y = 0; y < this.height; y++) {
/* 208 */       for (int x = 0; x < this.width; x++) {
/* 209 */         result.append(get(x, y) ? "X " : "  ");
/*     */       }
/* 211 */       result.append('\n');
/*     */     } 
/* 213 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/BitMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */