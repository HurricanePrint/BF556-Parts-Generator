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
/*     */ final class BitArray
/*     */ {
/*     */   private int[] bits;
/*     */   private final int size;
/*     */   
/*     */   public BitArray(int size) {
/*  58 */     if (size < 1) {
/*  59 */       throw new IllegalArgumentException("size must be at least 1");
/*     */     }
/*  61 */     this.size = size;
/*  62 */     this.bits = makeArray(size);
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  66 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean get(int i) {
/*  74 */     return ((this.bits[i >> 5] & 1 << (i & 0x1F)) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int i) {
/*  83 */     this.bits[i >> 5] = this.bits[i >> 5] | 1 << (i & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flip(int i) {
/*  92 */     this.bits[i >> 5] = this.bits[i >> 5] ^ 1 << (i & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBulk(int i, int newBits) {
/* 103 */     this.bits[i >> 5] = newBits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 110 */     int max = this.bits.length;
/* 111 */     for (int i = 0; i < max; i++) {
/* 112 */       this.bits[i] = 0;
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
/*     */   
/*     */   public boolean isRange(int start, int end, boolean value) {
/* 126 */     if (end < start) {
/* 127 */       throw new IllegalArgumentException();
/*     */     }
/* 129 */     if (end == start)
/*     */     {
/*     */       
/* 132 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 136 */     end--;
/* 137 */     int firstInt = start >> 5;
/* 138 */     int lastInt = end >> 5;
/* 139 */     for (int i = firstInt; i <= lastInt; i++) {
/* 140 */       int mask, firstBit = (i > firstInt) ? 0 : (start & 0x1F);
/* 141 */       int lastBit = (i < lastInt) ? 31 : (end & 0x1F);
/*     */       
/* 143 */       if (firstBit == 0 && lastBit == 31) {
/* 144 */         mask = -1;
/*     */       } else {
/* 146 */         mask = 0;
/* 147 */         for (int j = firstBit; j <= lastBit; j++) {
/* 148 */           mask |= 1 << j;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 154 */       if ((this.bits[i] & mask) != (value ? mask : 0)) {
/* 155 */         return false;
/*     */       }
/*     */     } 
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getBitArray() {
/* 166 */     return this.bits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reverse() {
/* 173 */     int[] newBits = new int[this.bits.length];
/* 174 */     int size = this.size;
/* 175 */     for (int i = 0; i < size; i++) {
/* 176 */       if (get(size - i - 1)) {
/* 177 */         newBits[i >> 5] = newBits[i >> 5] | 1 << (i & 0x1F);
/*     */       }
/*     */     } 
/* 180 */     this.bits = newBits;
/*     */   }
/*     */   
/*     */   private static int[] makeArray(int size) {
/* 184 */     int arraySize = size >> 5;
/* 185 */     if ((size & 0x1F) != 0) {
/* 186 */       arraySize++;
/*     */     }
/* 188 */     return new int[arraySize];
/*     */   }
/*     */   
/*     */   public String toString() {
/* 192 */     StringBuffer result = new StringBuffer(this.size);
/* 193 */     for (int i = 0; i < this.size; i++) {
/* 194 */       if ((i & 0x7) == 0) {
/* 195 */         result.append(' ');
/*     */       }
/* 197 */       result.append(get(i) ? 88 : 46);
/*     */     } 
/* 199 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/BitArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */