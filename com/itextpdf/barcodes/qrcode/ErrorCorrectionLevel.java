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
/*     */ public final class ErrorCorrectionLevel
/*     */ {
/*  57 */   public static final ErrorCorrectionLevel L = new ErrorCorrectionLevel(0, 1, "L");
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final ErrorCorrectionLevel M = new ErrorCorrectionLevel(1, 0, "M");
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final ErrorCorrectionLevel Q = new ErrorCorrectionLevel(2, 3, "Q");
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final ErrorCorrectionLevel H = new ErrorCorrectionLevel(3, 2, "H");
/*     */   
/*  71 */   private static final ErrorCorrectionLevel[] FOR_BITS = new ErrorCorrectionLevel[] { M, L, H, Q };
/*     */   
/*     */   private final int ordinal;
/*     */   private final int bits;
/*     */   private final String name;
/*     */   
/*     */   private ErrorCorrectionLevel(int ordinal, int bits, String name) {
/*  78 */     this.ordinal = ordinal;
/*  79 */     this.bits = bits;
/*  80 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ordinal() {
/*  89 */     return this.ordinal;
/*     */   }
/*     */   
/*     */   public int getBits() {
/*  93 */     return this.bits;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  97 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ErrorCorrectionLevel forBits(int bits) {
/* 110 */     if (bits < 0 || bits >= FOR_BITS.length) {
/* 111 */       throw new IllegalArgumentException();
/*     */     }
/* 113 */     return FOR_BITS[bits];
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/ErrorCorrectionLevel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */