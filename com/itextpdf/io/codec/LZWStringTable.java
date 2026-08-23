/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LZWStringTable
/*     */ {
/*  96 */   byte[] strChr_ = new byte[4096];
/*  97 */   short[] strNxt_ = new short[4096];
/*  98 */   int[] strLen_ = new int[4096];
/*  99 */   short[] strHsh_ = new short[9973];
/*     */   
/*     */   private static final int RES_CODES = 2;
/*     */   
/*     */   private static final short HASH_FREE = -1;
/*     */   
/*     */   private static final short NEXT_FIRST = -1;
/*     */   private static final int MAXBITS = 12;
/*     */   private static final int MAXSTR = 4096;
/*     */   private static final short HASHSIZE = 9973;
/*     */   private static final short HASHSTEP = 2039;
/*     */   short numStrings_;
/*     */   
/*     */   public int AddCharString(short index, byte b) {
/* 113 */     if (this.numStrings_ >= 4096)
/*     */     {
/* 115 */       return 65535;
/*     */     }
/*     */     
/* 118 */     int hshidx = Hash(index, b);
/* 119 */     while (this.strHsh_[hshidx] != -1) {
/* 120 */       hshidx = (hshidx + 2039) % 9973;
/*     */     }
/* 122 */     this.strHsh_[hshidx] = this.numStrings_;
/* 123 */     this.strChr_[this.numStrings_] = b;
/* 124 */     if (index == -1) {
/* 125 */       this.strNxt_[this.numStrings_] = -1;
/* 126 */       this.strLen_[this.numStrings_] = 1;
/*     */     } else {
/* 128 */       this.strNxt_[this.numStrings_] = index;
/* 129 */       this.strLen_[this.numStrings_] = this.strLen_[index] + 1;
/*     */     } 
/*     */ 
/*     */     
/* 133 */     this.numStrings_ = (short)(this.numStrings_ + 1); return this.numStrings_;
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
/*     */   public short FindCharString(short index, byte b) {
/* 145 */     if (index == -1)
/*     */     {
/*     */       
/* 148 */       return (short)(b & 0xFF);
/*     */     }
/* 150 */     int hshidx = Hash(index, b);
/*     */     
/*     */     int nxtidx;
/* 153 */     while ((nxtidx = this.strHsh_[hshidx]) != -1) {
/*     */       
/* 155 */       if (this.strNxt_[nxtidx] == index && this.strChr_[nxtidx] == b)
/* 156 */         return (short)nxtidx; 
/* 157 */       hshidx = (hshidx + 2039) % 9973;
/*     */     } 
/*     */ 
/*     */     
/* 161 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ClearTable(int codesize) {
/* 169 */     this.numStrings_ = 0;
/*     */     
/* 171 */     for (int q = 0; q < 9973; q++) {
/* 172 */       this.strHsh_[q] = -1;
/*     */     }
/* 174 */     int w = (1 << codesize) + 2;
/* 175 */     for (int i = 0; i < w; i++)
/*     */     {
/*     */       
/* 178 */       AddCharString((short)-1, (byte)i);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int Hash(short index, byte lastbyte) {
/* 183 */     return (((short)(lastbyte << 8) ^ index) & 0xFFFF) % 9973;
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
/*     */   public int expandCode(byte[] buf, int offset, short code, int skipHead) {
/*     */     int expandLen;
/* 207 */     if (offset == -2 && 
/* 208 */       skipHead == 1) skipHead = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     if (code == -1 || skipHead == this.strLen_[code])
/*     */     {
/*     */ 
/*     */       
/* 217 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     int codeLen = this.strLen_[code] - skipHead;
/*     */ 
/*     */     
/* 226 */     int bufSpace = buf.length - offset;
/* 227 */     if (bufSpace > codeLen) {
/*     */ 
/*     */       
/* 230 */       expandLen = codeLen;
/*     */     } else {
/* 232 */       expandLen = bufSpace;
/*     */     } 
/*     */ 
/*     */     
/* 236 */     int skipTail = codeLen - expandLen;
/*     */ 
/*     */     
/* 239 */     int idx = offset + expandLen;
/*     */ 
/*     */ 
/*     */     
/* 243 */     while (idx > offset && code != -1) {
/*     */ 
/*     */       
/* 246 */       if (--skipTail < 0)
/*     */       {
/* 248 */         buf[--idx] = this.strChr_[code];
/*     */       }
/*     */ 
/*     */       
/* 252 */       code = this.strNxt_[code];
/*     */     } 
/*     */     
/* 255 */     if (codeLen > expandLen)
/*     */     {
/*     */       
/* 258 */       return -expandLen;
/*     */     }
/*     */ 
/*     */     
/* 262 */     return expandLen;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(PrintStream output) {
/* 268 */     for (int i = 258; i < this.numStrings_; i++)
/* 269 */       output.println(" strNxt_[" + i + "] = " + this.strNxt_[i] + " strChr_ " + 
/* 270 */           Integer.toHexString(this.strChr_[i] & 0xFF) + " strLen_ " + 
/* 271 */           Integer.toHexString(this.strLen_[i])); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/LZWStringTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */