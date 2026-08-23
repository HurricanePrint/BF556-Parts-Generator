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
/*     */ 
/*     */ 
/*     */ final class BitVector
/*     */ {
/*  66 */   private int sizeInBits = 0;
/*  67 */   private byte[] array = new byte[32];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_SIZE_IN_BYTES = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int at(int index) {
/*  78 */     if (index < 0 || index >= this.sizeInBits) {
/*  79 */       throw new IllegalArgumentException("Bad index: " + index);
/*     */     }
/*  81 */     int value = this.array[index >> 3] & 0xFF;
/*  82 */     return value >> 7 - (index & 0x7) & 0x1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  89 */     return this.sizeInBits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  97 */     return this.sizeInBits + 7 >> 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendBit(int bit) {
/* 107 */     if (bit != 0 && bit != 1) {
/* 108 */       throw new IllegalArgumentException("Bad bit");
/*     */     }
/* 110 */     int numBitsInLastByte = this.sizeInBits & 0x7;
/*     */     
/* 112 */     if (numBitsInLastByte == 0) {
/* 113 */       appendByte(0);
/* 114 */       this.sizeInBits -= 8;
/*     */     } 
/*     */     
/* 117 */     this.array[this.sizeInBits >> 3] = (byte)(this.array[this.sizeInBits >> 3] | (byte)(bit << 7 - numBitsInLastByte));
/* 118 */     this.sizeInBits++;
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
/*     */   public void appendBits(int value, int numBits) {
/* 140 */     if (numBits < 0 || numBits > 32) {
/* 141 */       throw new IllegalArgumentException("Num bits must be between 0 and 32");
/*     */     }
/* 143 */     int numBitsLeft = numBits;
/* 144 */     while (numBitsLeft > 0) {
/*     */       
/* 146 */       if ((this.sizeInBits & 0x7) == 0 && numBitsLeft >= 8) {
/* 147 */         int newByte = value >> numBitsLeft - 8 & 0xFF;
/* 148 */         appendByte(newByte);
/* 149 */         numBitsLeft -= 8; continue;
/*     */       } 
/* 151 */       int bit = value >> numBitsLeft - 1 & 0x1;
/* 152 */       appendBit(bit);
/* 153 */       numBitsLeft--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendBitVector(BitVector bits) {
/* 163 */     int size = bits.size();
/* 164 */     for (int i = 0; i < size; i++) {
/* 165 */       appendBit(bits.at(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void xor(BitVector other) {
/* 175 */     if (this.sizeInBits != other.size()) {
/* 176 */       throw new IllegalArgumentException("BitVector sizes don't match");
/*     */     }
/* 178 */     int sizeInBytes = this.sizeInBits + 7 >> 3;
/* 179 */     for (int i = 0; i < sizeInBytes; i++)
/*     */     {
/*     */       
/* 182 */       this.array[i] = (byte)(this.array[i] ^ other.array[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 192 */     StringBuffer result = new StringBuffer(this.sizeInBits);
/* 193 */     for (int i = 0; i < this.sizeInBits; i++) {
/* 194 */       if (at(i) == 0) {
/* 195 */         result.append('0');
/* 196 */       } else if (at(i) == 1) {
/* 197 */         result.append('1');
/*     */       } else {
/* 199 */         throw new IllegalArgumentException("Byte isn't 0 or 1");
/*     */       } 
/*     */     } 
/* 202 */     return result.toString();
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
/*     */   public byte[] getArray() {
/* 214 */     return this.array;
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
/*     */   private void appendByte(int value) {
/* 226 */     if (this.sizeInBits >> 3 == this.array.length) {
/* 227 */       byte[] newArray = new byte[this.array.length << 1];
/* 228 */       System.arraycopy(this.array, 0, newArray, 0, this.array.length);
/* 229 */       this.array = newArray;
/*     */     } 
/* 231 */     this.array[this.sizeInBits >> 3] = (byte)value;
/* 232 */     this.sizeInBits += 8;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/BitVector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */