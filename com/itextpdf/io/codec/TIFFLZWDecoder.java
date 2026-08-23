/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TIFFLZWDecoder
/*     */ {
/*     */   byte[][] stringTable;
/*  56 */   byte[] data = null;
/*     */   byte[] uncompData;
/*     */   int tableIndex;
/*  59 */   int bitsToGet = 9;
/*     */   int bytePointer;
/*     */   int bitPointer;
/*     */   int dstIndex;
/*     */   int w;
/*     */   int h;
/*     */   int predictor;
/*     */   int samplesPerPixel;
/*  67 */   int nextData = 0;
/*  68 */   int nextBits = 0;
/*     */   
/*  70 */   int[] andTable = new int[] { 511, 1023, 2047, 4095 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIFFLZWDecoder(int w, int predictor, int samplesPerPixel) {
/*  78 */     this.w = w;
/*  79 */     this.predictor = predictor;
/*  80 */     this.samplesPerPixel = samplesPerPixel;
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
/*     */   public byte[] decode(byte[] data, byte[] uncompData, int h) {
/*  93 */     if (data[0] == 0 && data[1] == 1) {
/*  94 */       throw new IOException("TIFF 5.0-style LZW codes are not supported.");
/*     */     }
/*     */     
/*  97 */     initializeStringTable();
/*     */     
/*  99 */     this.data = data;
/* 100 */     this.h = h;
/* 101 */     this.uncompData = uncompData;
/*     */ 
/*     */     
/* 104 */     this.bytePointer = 0;
/* 105 */     this.bitPointer = 0;
/* 106 */     this.dstIndex = 0;
/*     */     
/* 108 */     this.nextData = 0;
/* 109 */     this.nextBits = 0;
/*     */     
/* 111 */     int oldCode = 0;
/*     */     
/*     */     int code;
/* 114 */     while ((code = getNextCode()) != 257 && this.dstIndex < uncompData.length) {
/*     */ 
/*     */       
/* 117 */       if (code == 256) {
/* 118 */         initializeStringTable();
/* 119 */         code = getNextCode();
/* 120 */         if (code == 257) {
/*     */           break;
/*     */         }
/* 123 */         writeString(this.stringTable[code]);
/* 124 */         oldCode = code;
/*     */         continue;
/*     */       } 
/* 127 */       if (code < this.tableIndex) {
/* 128 */         byte[] arrayOfByte = this.stringTable[code];
/* 129 */         writeString(arrayOfByte);
/* 130 */         addStringToTable(this.stringTable[oldCode], arrayOfByte[0]);
/* 131 */         oldCode = code; continue;
/*     */       } 
/* 133 */       byte[] str = this.stringTable[oldCode];
/* 134 */       str = composeString(str, str[0]);
/* 135 */       writeString(str);
/* 136 */       addStringToTable(str);
/* 137 */       oldCode = code;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (this.predictor == 2)
/*     */     {
/* 145 */       for (int j = 0; j < h; j++) {
/* 146 */         int count = this.samplesPerPixel * (j * this.w + 1);
/* 147 */         for (int i = this.samplesPerPixel; i < this.w * this.samplesPerPixel; i++) {
/*     */           
/* 149 */           uncompData[count] = (byte)(uncompData[count] + uncompData[count - this.samplesPerPixel]);
/* 150 */           count++;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 155 */     return uncompData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeStringTable() {
/* 163 */     this.stringTable = new byte[4096][];
/*     */     
/* 165 */     for (int i = 0; i < 256; i++) {
/* 166 */       this.stringTable[i] = new byte[1];
/* 167 */       this.stringTable[i][0] = (byte)i;
/*     */     } 
/*     */     
/* 170 */     this.tableIndex = 258;
/* 171 */     this.bitsToGet = 9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeString(byte[] str) {
/* 181 */     int max = this.uncompData.length - this.dstIndex;
/* 182 */     if (str.length < max)
/* 183 */       max = str.length; 
/* 184 */     System.arraycopy(str, 0, this.uncompData, this.dstIndex, max);
/* 185 */     this.dstIndex += max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStringToTable(byte[] oldString, byte newString) {
/* 196 */     int length = oldString.length;
/* 197 */     byte[] str = new byte[length + 1];
/* 198 */     System.arraycopy(oldString, 0, str, 0, length);
/* 199 */     str[length] = newString;
/*     */ 
/*     */     
/* 202 */     this.stringTable[this.tableIndex++] = str;
/*     */     
/* 204 */     if (this.tableIndex == 511) {
/* 205 */       this.bitsToGet = 10;
/* 206 */     } else if (this.tableIndex == 1023) {
/* 207 */       this.bitsToGet = 11;
/* 208 */     } else if (this.tableIndex == 2047) {
/* 209 */       this.bitsToGet = 12;
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
/*     */   public void addStringToTable(byte[] str) {
/* 221 */     this.stringTable[this.tableIndex++] = str;
/*     */     
/* 223 */     if (this.tableIndex == 511) {
/* 224 */       this.bitsToGet = 10;
/* 225 */     } else if (this.tableIndex == 1023) {
/* 226 */       this.bitsToGet = 11;
/* 227 */     } else if (this.tableIndex == 2047) {
/* 228 */       this.bitsToGet = 12;
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
/*     */   public byte[] composeString(byte[] oldString, byte newString) {
/* 240 */     int length = oldString.length;
/* 241 */     byte[] str = new byte[length + 1];
/* 242 */     System.arraycopy(oldString, 0, str, 0, length);
/* 243 */     str[length] = newString;
/*     */     
/* 245 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextCode() {
/*     */     try {
/* 255 */       this.nextData = this.nextData << 8 | this.data[this.bytePointer++] & 0xFF;
/* 256 */       this.nextBits += 8;
/*     */       
/* 258 */       if (this.nextBits < this.bitsToGet) {
/* 259 */         this.nextData = this.nextData << 8 | this.data[this.bytePointer++] & 0xFF;
/* 260 */         this.nextBits += 8;
/*     */       } 
/*     */       
/* 263 */       int code = this.nextData >> this.nextBits - this.bitsToGet & this.andTable[this.bitsToGet - 9];
/*     */       
/* 265 */       this.nextBits -= this.bitsToGet;
/*     */       
/* 267 */       return code;
/* 268 */     } catch (ArrayIndexOutOfBoundsException e) {
/*     */       
/* 270 */       return 257;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/TIFFLZWDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */