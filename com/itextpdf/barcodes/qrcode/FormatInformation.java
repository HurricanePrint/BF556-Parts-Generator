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
/*     */ final class FormatInformation
/*     */ {
/*     */   private static final int FORMAT_INFO_MASK_QR = 21522;
/*  60 */   private static final int[][] FORMAT_INFO_DECODE_LOOKUP = new int[][] { { 21522, 0 }, { 20773, 1 }, { 24188, 2 }, { 23371, 3 }, { 17913, 4 }, { 16590, 5 }, { 20375, 6 }, { 19104, 7 }, { 30660, 8 }, { 29427, 9 }, { 32170, 10 }, { 30877, 11 }, { 26159, 12 }, { 25368, 13 }, { 27713, 14 }, { 26998, 15 }, { 5769, 16 }, { 5054, 17 }, { 7399, 18 }, { 6608, 19 }, { 1890, 20 }, { 597, 21 }, { 3340, 22 }, { 2107, 23 }, { 13663, 24 }, { 12392, 25 }, { 16177, 26 }, { 14854, 27 }, { 9396, 28 }, { 8579, 29 }, { 11994, 30 }, { 11245, 31 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   private static final int[] BITS_SET_IN_HALF_BYTE = new int[] { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4 };
/*     */   
/*     */   private final ErrorCorrectionLevel errorCorrectionLevel;
/*     */   
/*     */   private final byte dataMask;
/*     */ 
/*     */   
/*     */   private FormatInformation(int formatInfo) {
/* 106 */     this.errorCorrectionLevel = ErrorCorrectionLevel.forBits(formatInfo >> 3 & 0x3);
/*     */     
/* 108 */     this.dataMask = (byte)(formatInfo & 0x7);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int numBitsDiffering(int a, int b) {
/* 114 */     a ^= b;
/*     */ 
/*     */     
/* 117 */     return BITS_SET_IN_HALF_BYTE[a & 0xF] + BITS_SET_IN_HALF_BYTE[a >>> 4 & 0xF] + BITS_SET_IN_HALF_BYTE[a >>> 8 & 0xF] + BITS_SET_IN_HALF_BYTE[a >>> 12 & 0xF] + BITS_SET_IN_HALF_BYTE[a >>> 16 & 0xF] + BITS_SET_IN_HALF_BYTE[a >>> 20 & 0xF] + BITS_SET_IN_HALF_BYTE[a >>> 24 & 0xF] + BITS_SET_IN_HALF_BYTE[a >>> 28 & 0xF];
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
/*     */   static FormatInformation decodeFormatInformation(int maskedFormatInfo1, int maskedFormatInfo2) {
/* 135 */     FormatInformation formatInfo = doDecodeFormatInformation(maskedFormatInfo1, maskedFormatInfo2);
/* 136 */     if (formatInfo != null) {
/* 137 */       return formatInfo;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     return doDecodeFormatInformation(maskedFormatInfo1 ^ 0x5412, maskedFormatInfo2 ^ 0x5412);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static FormatInformation doDecodeFormatInformation(int maskedFormatInfo1, int maskedFormatInfo2) {
/* 148 */     int bestDifference = Integer.MAX_VALUE;
/* 149 */     int bestFormatInfo = 0;
/* 150 */     for (int i = 0; i < FORMAT_INFO_DECODE_LOOKUP.length; i++) {
/* 151 */       int[] decodeInfo = FORMAT_INFO_DECODE_LOOKUP[i];
/* 152 */       int targetInfo = decodeInfo[0];
/* 153 */       if (targetInfo == maskedFormatInfo1 || targetInfo == maskedFormatInfo2)
/*     */       {
/* 155 */         return new FormatInformation(decodeInfo[1]);
/*     */       }
/* 157 */       int bitsDifference = numBitsDiffering(maskedFormatInfo1, targetInfo);
/* 158 */       if (bitsDifference < bestDifference) {
/* 159 */         bestFormatInfo = decodeInfo[1];
/* 160 */         bestDifference = bitsDifference;
/*     */       } 
/* 162 */       if (maskedFormatInfo1 != maskedFormatInfo2) {
/*     */         
/* 164 */         bitsDifference = numBitsDiffering(maskedFormatInfo2, targetInfo);
/* 165 */         if (bitsDifference < bestDifference) {
/* 166 */           bestFormatInfo = decodeInfo[1];
/* 167 */           bestDifference = bitsDifference;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 173 */     if (bestDifference <= 3) {
/* 174 */       return new FormatInformation(bestFormatInfo);
/*     */     }
/* 176 */     return null;
/*     */   }
/*     */   
/*     */   ErrorCorrectionLevel getErrorCorrectionLevel() {
/* 180 */     return this.errorCorrectionLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte getDataMask() {
/* 187 */     return this.dataMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 194 */     return this.errorCorrectionLevel.ordinal() << 3 | this.dataMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 203 */     if (!(o instanceof FormatInformation)) {
/* 204 */       return false;
/*     */     }
/* 206 */     FormatInformation other = (FormatInformation)o;
/* 207 */     return (this.errorCorrectionLevel == other.errorCorrectionLevel && this.dataMask == other.dataMask);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/FormatInformation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */