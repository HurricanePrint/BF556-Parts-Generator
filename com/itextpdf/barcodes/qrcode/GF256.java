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
/*     */ final class GF256
/*     */ {
/*  59 */   public static final GF256 QR_CODE_FIELD = new GF256(285);
/*     */ 
/*     */   
/*  62 */   public static final GF256 DATA_MATRIX_FIELD = new GF256(301);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private final int[] expTable = new int[256];
/*  78 */   private final int[] logTable = new int[256]; private final GF256Poly zero; private GF256(int primitive) {
/*  79 */     int x = 1; int i;
/*  80 */     for (i = 0; i < 256; i++) {
/*  81 */       this.expTable[i] = x;
/*     */ 
/*     */       
/*  84 */       x <<= 1;
/*  85 */       if (x >= 256) {
/*  86 */         x ^= primitive;
/*     */       }
/*     */     } 
/*  89 */     for (i = 0; i < 255; i++) {
/*  90 */       this.logTable[this.expTable[i]] = i;
/*     */     }
/*     */ 
/*     */     
/*  94 */     this.zero = new GF256Poly(this, new int[] { 0 });
/*  95 */     this.one = new GF256Poly(this, new int[] { 1 });
/*     */   }
/*     */   private final GF256Poly one;
/*     */   GF256Poly getZero() {
/*  99 */     return this.zero;
/*     */   }
/*     */   
/*     */   GF256Poly getOne() {
/* 103 */     return this.one;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GF256Poly buildMonomial(int degree, int coefficient) {
/* 110 */     if (degree < 0) {
/* 111 */       throw new IllegalArgumentException();
/*     */     }
/* 113 */     if (coefficient == 0) {
/* 114 */       return this.zero;
/*     */     }
/* 116 */     int[] coefficients = new int[degree + 1];
/* 117 */     coefficients[0] = coefficient;
/* 118 */     return new GF256Poly(this, coefficients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int addOrSubtract(int a, int b) {
/* 127 */     return a ^ b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int exp(int a) {
/* 134 */     return this.expTable[a];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int log(int a) {
/* 141 */     if (a == 0) {
/* 142 */       throw new IllegalArgumentException();
/*     */     }
/* 144 */     return this.logTable[a];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int inverse(int a) {
/* 151 */     if (a == 0) {
/* 152 */       throw new ArithmeticException();
/*     */     }
/* 154 */     return this.expTable[255 - this.logTable[a]];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int multiply(int a, int b) {
/* 163 */     if (a == 0 || b == 0) {
/* 164 */       return 0;
/*     */     }
/* 166 */     if (a == 1) {
/* 167 */       return b;
/*     */     }
/* 169 */     if (b == 1) {
/* 170 */       return a;
/*     */     }
/* 172 */     return this.expTable[(this.logTable[a] + this.logTable[b]) % 255];
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/GF256.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */