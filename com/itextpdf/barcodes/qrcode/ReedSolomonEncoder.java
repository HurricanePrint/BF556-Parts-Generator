/*     */ package com.itextpdf.barcodes.qrcode;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ReedSolomonEncoder
/*     */ {
/*     */   private final GF256 field;
/*     */   private final List<GF256Poly> cachedGenerators;
/*     */   
/*     */   public ReedSolomonEncoder(GF256 field) {
/*  67 */     if (!GF256.QR_CODE_FIELD.equals(field)) {
/*  68 */       throw new UnsupportedOperationException("Only QR Code is supported at this time");
/*     */     }
/*  70 */     this.field = field;
/*  71 */     this.cachedGenerators = new ArrayList<>();
/*  72 */     this.cachedGenerators.add(new GF256Poly(field, new int[] { 1 }));
/*     */   }
/*     */   
/*     */   private GF256Poly buildGenerator(int degree) {
/*  76 */     if (degree >= this.cachedGenerators.size()) {
/*  77 */       GF256Poly lastGenerator = this.cachedGenerators.get(this.cachedGenerators.size() - 1);
/*  78 */       for (int d = this.cachedGenerators.size(); d <= degree; d++) {
/*  79 */         GF256Poly nextGenerator = lastGenerator.multiply(new GF256Poly(this.field, new int[] { 1, this.field.exp(d - 1) }));
/*  80 */         this.cachedGenerators.add(nextGenerator);
/*  81 */         lastGenerator = nextGenerator;
/*     */       } 
/*     */     } 
/*  84 */     return this.cachedGenerators.get(degree);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(int[] toEncode, int ecBytes) {
/*  94 */     if (ecBytes == 0) {
/*  95 */       throw new IllegalArgumentException("No error correction bytes");
/*     */     }
/*  97 */     int dataBytes = toEncode.length - ecBytes;
/*  98 */     if (dataBytes <= 0) {
/*  99 */       throw new IllegalArgumentException("No data bytes provided");
/*     */     }
/* 101 */     GF256Poly generator = buildGenerator(ecBytes);
/* 102 */     int[] infoCoefficients = new int[dataBytes];
/* 103 */     System.arraycopy(toEncode, 0, infoCoefficients, 0, dataBytes);
/* 104 */     GF256Poly info = new GF256Poly(this.field, infoCoefficients);
/* 105 */     info = info.multiplyByMonomial(ecBytes, 1);
/* 106 */     GF256Poly remainder = info.divide(generator)[1];
/* 107 */     int[] coefficients = remainder.getCoefficients();
/* 108 */     int numZeroCoefficients = ecBytes - coefficients.length;
/* 109 */     for (int i = 0; i < numZeroCoefficients; i++) {
/* 110 */       toEncode[dataBytes + i] = 0;
/*     */     }
/* 112 */     System.arraycopy(coefficients, 0, toEncode, dataBytes + numZeroCoefficients, coefficients.length);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/ReedSolomonEncoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */