/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CCITTG4Encoder
/*     */ {
/*     */   private int rowbytes;
/*     */   private int rowpixels;
/*  55 */   private int bit = 8;
/*     */   private int data;
/*     */   private byte[] refline;
/*  58 */   private ByteBuffer outBuf = new ByteBuffer(1024);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] dataBp;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int offsetData;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int sizeData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fax4Encode(byte[] data, int offset, int size) {
/*  80 */     this.dataBp = data;
/*  81 */     this.offsetData = offset;
/*  82 */     this.sizeData = size;
/*  83 */     while (this.sizeData > 0) {
/*  84 */       Fax3Encode2DRow();
/*  85 */       System.arraycopy(this.dataBp, this.offsetData, this.refline, 0, this.rowbytes);
/*  86 */       this.offsetData += this.rowbytes;
/*  87 */       this.sizeData -= this.rowbytes;
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
/*     */   public static byte[] compress(byte[] data, int width, int height) {
/* 100 */     CCITTG4Encoder g4 = new CCITTG4Encoder(width);
/* 101 */     g4.fax4Encode(data, 0, g4.rowbytes * height);
/* 102 */     return g4.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fax4Encode(byte[] data, int height) {
/* 111 */     fax4Encode(data, 0, this.rowbytes * height);
/*     */   }
/*     */   
/*     */   private void putcode(int[] table) {
/* 115 */     putBits(table[1], table[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void putspan(int span, int[][] tab) {
/* 121 */     while (span >= 2624) {
/* 122 */       int[] te = tab[103];
/* 123 */       int i = te[1];
/* 124 */       int j = te[0];
/* 125 */       putBits(i, j);
/* 126 */       span -= te[2];
/*     */     } 
/* 128 */     if (span >= 64) {
/* 129 */       int[] te = tab[63 + (span >> 6)];
/* 130 */       int i = te[1];
/* 131 */       int j = te[0];
/* 132 */       putBits(i, j);
/* 133 */       span -= te[2];
/*     */     } 
/* 135 */     int code = tab[span][1];
/* 136 */     int length = tab[span][0];
/* 137 */     putBits(code, length);
/*     */   }
/*     */   
/*     */   private void putBits(int bits, int length) {
/* 141 */     while (length > this.bit) {
/* 142 */       this.data |= bits >> length - this.bit;
/* 143 */       length -= this.bit;
/* 144 */       this.outBuf.append((byte)this.data);
/* 145 */       this.data = 0;
/* 146 */       this.bit = 8;
/*     */     } 
/* 148 */     this.data |= (bits & this.msbmask[length]) << this.bit - length;
/* 149 */     this.bit -= length;
/* 150 */     if (this.bit == 0) {
/* 151 */       this.outBuf.append((byte)this.data);
/* 152 */       this.data = 0;
/* 153 */       this.bit = 8;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void Fax3Encode2DRow() {
/* 158 */     int a0 = 0;
/* 159 */     int a1 = (pixel(this.dataBp, this.offsetData, 0) != 0) ? 0 : finddiff(this.dataBp, this.offsetData, 0, this.rowpixels, 0);
/* 160 */     int b1 = (pixel(this.refline, 0, 0) != 0) ? 0 : finddiff(this.refline, 0, 0, this.rowpixels, 0);
/*     */ 
/*     */     
/*     */     while (true) {
/* 164 */       int b2 = finddiff2(this.refline, 0, b1, this.rowpixels, pixel(this.refline, 0, b1));
/* 165 */       if (b2 >= a1) {
/* 166 */         int d = b1 - a1;
/* 167 */         if (-3 > d || d > 3) {
/* 168 */           int a2 = finddiff2(this.dataBp, this.offsetData, a1, this.rowpixels, pixel(this.dataBp, this.offsetData, a1));
/* 169 */           putcode(this.horizcode);
/* 170 */           if (a0 + a1 == 0 || pixel(this.dataBp, this.offsetData, a0) == 0) {
/* 171 */             putspan(a1 - a0, this.TIFFFaxWhiteCodes);
/* 172 */             putspan(a2 - a1, this.TIFFFaxBlackCodes);
/*     */           } else {
/* 174 */             putspan(a1 - a0, this.TIFFFaxBlackCodes);
/* 175 */             putspan(a2 - a1, this.TIFFFaxWhiteCodes);
/*     */           } 
/* 177 */           a0 = a2;
/*     */         } else {
/* 179 */           putcode(this.vcodes[d + 3]);
/* 180 */           a0 = a1;
/*     */         } 
/*     */       } else {
/* 183 */         putcode(this.passcode);
/* 184 */         a0 = b2;
/*     */       } 
/* 186 */       if (a0 >= this.rowpixels)
/*     */         break; 
/* 188 */       a1 = finddiff(this.dataBp, this.offsetData, a0, this.rowpixels, pixel(this.dataBp, this.offsetData, a0));
/* 189 */       b1 = finddiff(this.refline, 0, a0, this.rowpixels, pixel(this.dataBp, this.offsetData, a0) ^ 0x1);
/* 190 */       b1 = finddiff(this.refline, 0, b1, this.rowpixels, pixel(this.dataBp, this.offsetData, a0));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void Fax4PostEncode() {
/* 195 */     putBits(1, 12);
/* 196 */     putBits(1, 12);
/* 197 */     if (this.bit != 8) {
/* 198 */       this.outBuf.append((byte)this.data);
/* 199 */       this.data = 0;
/* 200 */       this.bit = 8;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] close() {
/* 209 */     Fax4PostEncode();
/* 210 */     return this.outBuf.toByteArray();
/*     */   }
/*     */   
/*     */   private int pixel(byte[] data, int offset, int bit) {
/* 214 */     if (bit >= this.rowpixels)
/* 215 */       return 0; 
/* 216 */     return (data[offset + (bit >> 3)] & 0xFF) >> 7 - (bit & 0x7) & 0x1;
/*     */   }
/*     */   
/*     */   private static int find1span(byte[] bp, int offset, int bs, int be) {
/* 220 */     int span, bits = be - bs;
/*     */ 
/*     */     
/* 223 */     int pos = offset + (bs >> 3);
/*     */     
/*     */     int n;
/*     */     
/* 227 */     if (bits > 0 && (n = bs & 0x7) != 0) {
/* 228 */       span = oneruns[bp[pos] << n & 0xFF];
/* 229 */       if (span > 8 - n)
/* 230 */         span = 8 - n; 
/* 231 */       if (span > bits)
/* 232 */         span = bits; 
/* 233 */       if (n + span < 8)
/* 234 */         return span; 
/* 235 */       bits -= span;
/* 236 */       pos++;
/*     */     } else {
/* 238 */       span = 0;
/*     */     } 
/*     */ 
/*     */     
/* 242 */     while (bits >= 8) {
/* 243 */       if (bp[pos] != -1)
/* 244 */         return span + oneruns[bp[pos] & 0xFF]; 
/* 245 */       span += 8;
/* 246 */       bits -= 8;
/* 247 */       pos++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 252 */     if (bits > 0) {
/* 253 */       n = oneruns[bp[pos] & 0xFF];
/* 254 */       span += (n > bits) ? bits : n;
/*     */     } 
/* 256 */     return span;
/*     */   }
/*     */   
/*     */   private static int find0span(byte[] bp, int offset, int bs, int be) {
/* 260 */     int span, bits = be - bs;
/*     */ 
/*     */     
/* 263 */     int pos = offset + (bs >> 3);
/*     */     
/*     */     int n;
/*     */     
/* 267 */     if (bits > 0 && (n = bs & 0x7) != 0) {
/* 268 */       span = zeroruns[bp[pos] << n & 0xFF];
/* 269 */       if (span > 8 - n)
/* 270 */         span = 8 - n; 
/* 271 */       if (span > bits)
/* 272 */         span = bits; 
/* 273 */       if (n + span < 8)
/* 274 */         return span; 
/* 275 */       bits -= span;
/* 276 */       pos++;
/*     */     } else {
/* 278 */       span = 0;
/*     */     } 
/*     */ 
/*     */     
/* 282 */     while (bits >= 8) {
/* 283 */       if (bp[pos] != 0)
/* 284 */         return span + zeroruns[bp[pos] & 0xFF]; 
/* 285 */       span += 8;
/* 286 */       bits -= 8;
/* 287 */       pos++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 292 */     if (bits > 0) {
/* 293 */       n = zeroruns[bp[pos] & 0xFF];
/* 294 */       span += (n > bits) ? bits : n;
/*     */     } 
/* 296 */     return span;
/*     */   }
/*     */   
/*     */   private static int finddiff(byte[] bp, int offset, int bs, int be, int color) {
/* 300 */     return bs + ((color != 0) ? find1span(bp, offset, bs, be) : find0span(bp, offset, bs, be));
/*     */   }
/*     */   
/*     */   private static int finddiff2(byte[] bp, int offset, int bs, int be, int color) {
/* 304 */     return (bs < be) ? finddiff(bp, offset, bs, be, color) : be;
/*     */   }
/*     */   
/* 307 */   private static byte[] zeroruns = new byte[] { 8, 7, 6, 6, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 326 */   private static byte[] oneruns = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 7, 8 };
/*     */   
/*     */   private static final int LENGTH = 0;
/*     */   
/*     */   private static final int CODE = 1;
/*     */   
/*     */   private static final int RUNLEN = 2;
/*     */   
/*     */   private static final int EOL = 1;
/*     */   
/*     */   private static final int G3CODE_EOL = -1;
/*     */   
/*     */   private static final int G3CODE_INVALID = -2;
/*     */   
/*     */   private static final int G3CODE_EOF = -3;
/*     */   
/*     */   private static final int G3CODE_INCOMP = -4;
/*     */   
/*     */   private int[][] TIFFFaxWhiteCodes;
/*     */   
/*     */   private int[][] TIFFFaxBlackCodes;
/*     */   
/*     */   private int[] horizcode;
/*     */   
/*     */   private int[] passcode;
/*     */   
/*     */   private int[][] vcodes;
/*     */   
/*     */   private int[] msbmask;
/*     */   
/*     */   public CCITTG4Encoder(int width) {
/* 357 */     this.TIFFFaxWhiteCodes = new int[][] { { 8, 53, 0 }, { 6, 7, 1 }, { 4, 7, 2 }, { 4, 8, 3 }, { 4, 11, 4 }, { 4, 12, 5 }, { 4, 14, 6 }, { 4, 15, 7 }, { 5, 19, 8 }, { 5, 20, 9 }, { 5, 7, 10 }, { 5, 8, 11 }, { 6, 8, 12 }, { 6, 3, 13 }, { 6, 52, 14 }, { 6, 53, 15 }, { 6, 42, 16 }, { 6, 43, 17 }, { 7, 39, 18 }, { 7, 12, 19 }, { 7, 8, 20 }, { 7, 23, 21 }, { 7, 3, 22 }, { 7, 4, 23 }, { 7, 40, 24 }, { 7, 43, 25 }, { 7, 19, 26 }, { 7, 36, 27 }, { 7, 24, 28 }, { 8, 2, 29 }, { 8, 3, 30 }, { 8, 26, 31 }, { 8, 27, 32 }, { 8, 18, 33 }, { 8, 19, 34 }, { 8, 20, 35 }, { 8, 21, 36 }, { 8, 22, 37 }, { 8, 23, 38 }, { 8, 40, 39 }, { 8, 41, 40 }, { 8, 42, 41 }, { 8, 43, 42 }, { 8, 44, 43 }, { 8, 45, 44 }, { 8, 4, 45 }, { 8, 5, 46 }, { 8, 10, 47 }, { 8, 11, 48 }, { 8, 82, 49 }, { 8, 83, 50 }, { 8, 84, 51 }, { 8, 85, 52 }, { 8, 36, 53 }, { 8, 37, 54 }, { 8, 88, 55 }, { 8, 89, 56 }, { 8, 90, 57 }, { 8, 91, 58 }, { 8, 74, 59 }, { 8, 75, 60 }, { 8, 50, 61 }, { 8, 51, 62 }, { 8, 52, 63 }, { 5, 27, 64 }, { 5, 18, 128 }, { 6, 23, 192 }, { 7, 55, 256 }, { 8, 54, 320 }, { 8, 55, 384 }, { 8, 100, 448 }, { 8, 101, 512 }, { 8, 104, 576 }, { 8, 103, 640 }, { 9, 204, 704 }, { 9, 205, 768 }, { 9, 210, 832 }, { 9, 211, 896 }, { 9, 212, 960 }, { 9, 213, 1024 }, { 9, 214, 1088 }, { 9, 215, 1152 }, { 9, 216, 1216 }, { 9, 217, 1280 }, { 9, 218, 1344 }, { 9, 219, 1408 }, { 9, 152, 1472 }, { 9, 153, 1536 }, { 9, 154, 1600 }, { 6, 24, 1664 }, { 9, 155, 1728 }, { 11, 8, 1792 }, { 11, 12, 1856 }, { 11, 13, 1920 }, { 12, 18, 1984 }, { 12, 19, 2048 }, { 12, 20, 2112 }, { 12, 21, 2176 }, { 12, 22, 2240 }, { 12, 23, 2304 }, { 12, 28, 2368 }, { 12, 29, 2432 }, { 12, 30, 2496 }, { 12, 31, 2560 }, { 12, 1, -1 }, { 9, 1, -2 }, { 10, 1, -2 }, { 11, 1, -2 }, { 12, 0, -2 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 469 */     this.TIFFFaxBlackCodes = new int[][] { { 10, 55, 0 }, { 3, 2, 1 }, { 2, 3, 2 }, { 2, 2, 3 }, { 3, 3, 4 }, { 4, 3, 5 }, { 4, 2, 6 }, { 5, 3, 7 }, { 6, 5, 8 }, { 6, 4, 9 }, { 7, 4, 10 }, { 7, 5, 11 }, { 7, 7, 12 }, { 8, 4, 13 }, { 8, 7, 14 }, { 9, 24, 15 }, { 10, 23, 16 }, { 10, 24, 17 }, { 10, 8, 18 }, { 11, 103, 19 }, { 11, 104, 20 }, { 11, 108, 21 }, { 11, 55, 22 }, { 11, 40, 23 }, { 11, 23, 24 }, { 11, 24, 25 }, { 12, 202, 26 }, { 12, 203, 27 }, { 12, 204, 28 }, { 12, 205, 29 }, { 12, 104, 30 }, { 12, 105, 31 }, { 12, 106, 32 }, { 12, 107, 33 }, { 12, 210, 34 }, { 12, 211, 35 }, { 12, 212, 36 }, { 12, 213, 37 }, { 12, 214, 38 }, { 12, 215, 39 }, { 12, 108, 40 }, { 12, 109, 41 }, { 12, 218, 42 }, { 12, 219, 43 }, { 12, 84, 44 }, { 12, 85, 45 }, { 12, 86, 46 }, { 12, 87, 47 }, { 12, 100, 48 }, { 12, 101, 49 }, { 12, 82, 50 }, { 12, 83, 51 }, { 12, 36, 52 }, { 12, 55, 53 }, { 12, 56, 54 }, { 12, 39, 55 }, { 12, 40, 56 }, { 12, 88, 57 }, { 12, 89, 58 }, { 12, 43, 59 }, { 12, 44, 60 }, { 12, 90, 61 }, { 12, 102, 62 }, { 12, 103, 63 }, { 10, 15, 64 }, { 12, 200, 128 }, { 12, 201, 192 }, { 12, 91, 256 }, { 12, 51, 320 }, { 12, 52, 384 }, { 12, 53, 448 }, { 13, 108, 512 }, { 13, 109, 576 }, { 13, 74, 640 }, { 13, 75, 704 }, { 13, 76, 768 }, { 13, 77, 832 }, { 13, 114, 896 }, { 13, 115, 960 }, { 13, 116, 1024 }, { 13, 117, 1088 }, { 13, 118, 1152 }, { 13, 119, 1216 }, { 13, 82, 1280 }, { 13, 83, 1344 }, { 13, 84, 1408 }, { 13, 85, 1472 }, { 13, 90, 1536 }, { 13, 91, 1600 }, { 13, 100, 1664 }, { 13, 101, 1728 }, { 11, 8, 1792 }, { 11, 12, 1856 }, { 11, 13, 1920 }, { 12, 18, 1984 }, { 12, 19, 2048 }, { 12, 20, 2112 }, { 12, 21, 2176 }, { 12, 22, 2240 }, { 12, 23, 2304 }, { 12, 28, 2368 }, { 12, 29, 2432 }, { 12, 30, 2496 }, { 12, 31, 2560 }, { 12, 1, -1 }, { 9, 1, -2 }, { 10, 1, -2 }, { 11, 1, -2 }, { 12, 0, -2 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 581 */     this.horizcode = new int[] { 3, 1, 0 };
/*     */     
/* 583 */     this.passcode = new int[] { 4, 1, 0 };
/*     */     
/* 585 */     this.vcodes = new int[][] { { 7, 3, 0 }, { 6, 3, 0 }, { 3, 3, 0 }, { 1, 1, 0 }, { 3, 2, 0 }, { 6, 2, 0 }, { 7, 2, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 594 */     this.msbmask = new int[] { 0, 1, 3, 7, 15, 31, 63, 127, 255 };
/*     */     this.rowpixels = width;
/*     */     this.rowbytes = (this.rowpixels + 7) / 8;
/*     */     this.refline = new byte[this.rowbytes];
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/CCITTG4Encoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */