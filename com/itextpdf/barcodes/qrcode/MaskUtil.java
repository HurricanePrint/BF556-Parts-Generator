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
/*     */ final class MaskUtil
/*     */ {
/*     */   public static int applyMaskPenaltyRule1(ByteMatrix matrix) {
/*  62 */     return applyMaskPenaltyRule1Internal(matrix, true) + applyMaskPenaltyRule1Internal(matrix, false);
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
/*     */   public static int applyMaskPenaltyRule2(ByteMatrix matrix) {
/*  74 */     int penalty = 0;
/*  75 */     byte[][] array = matrix.getArray();
/*  76 */     int width = matrix.getWidth();
/*  77 */     int height = matrix.getHeight();
/*  78 */     for (int y = 0; y < height - 1; y++) {
/*  79 */       for (int x = 0; x < width - 1; x++) {
/*  80 */         int value = array[y][x];
/*  81 */         if (value == array[y][x + 1] && value == array[y + 1][x] && value == array[y + 1][x + 1]) {
/*  82 */           penalty += 3;
/*     */         }
/*     */       } 
/*     */     } 
/*  86 */     return penalty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int applyMaskPenaltyRule3(ByteMatrix matrix) {
/*  97 */     int penalty = 0;
/*  98 */     byte[][] array = matrix.getArray();
/*  99 */     int width = matrix.getWidth();
/* 100 */     int height = matrix.getHeight();
/* 101 */     for (int y = 0; y < height; y++) {
/* 102 */       for (int x = 0; x < width; x++) {
/*     */         
/* 104 */         if (x + 6 < width && array[y][x] == 1 && array[y][x + 1] == 0 && array[y][x + 2] == 1 && array[y][x + 3] == 1 && array[y][x + 4] == 1 && array[y][x + 5] == 0 && array[y][x + 6] == 1 && ((x + 10 < width && array[y][x + 7] == 0 && array[y][x + 8] == 0 && array[y][x + 9] == 0 && array[y][x + 10] == 0) || (x - 4 >= 0 && array[y][x - 1] == 0 && array[y][x - 2] == 0 && array[y][x - 3] == 0 && array[y][x - 4] == 0)))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 122 */           penalty += 40;
/*     */         }
/* 124 */         if (y + 6 < height && array[y][x] == 1 && array[y + 1][x] == 0 && array[y + 2][x] == 1 && array[y + 3][x] == 1 && array[y + 4][x] == 1 && array[y + 5][x] == 0 && array[y + 6][x] == 1 && ((y + 10 < height && array[y + 7][x] == 0 && array[y + 8][x] == 0 && array[y + 9][x] == 0 && array[y + 10][x] == 0) || (y - 4 >= 0 && array[y - 1][x] == 0 && array[y - 2][x] == 0 && array[y - 3][x] == 0 && array[y - 4][x] == 0)))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 142 */           penalty += 40;
/*     */         }
/*     */       } 
/*     */     } 
/* 146 */     return penalty;
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
/*     */   public static int applyMaskPenaltyRule4(ByteMatrix matrix) {
/* 165 */     int numDarkCells = 0;
/* 166 */     byte[][] array = matrix.getArray();
/* 167 */     int width = matrix.getWidth();
/* 168 */     int height = matrix.getHeight();
/* 169 */     for (int y = 0; y < height; y++) {
/* 170 */       for (int x = 0; x < width; x++) {
/* 171 */         if (array[y][x] == 1) {
/* 172 */           numDarkCells++;
/*     */         }
/*     */       } 
/*     */     } 
/* 176 */     int numTotalCells = matrix.getHeight() * matrix.getWidth();
/* 177 */     double darkRatio = numDarkCells / numTotalCells;
/* 178 */     return Math.abs((int)(darkRatio * 100.0D - 50.0D)) / 5 * 10;
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
/*     */   public static boolean getDataMaskBit(int maskPattern, int x, int y) {
/*     */     int intermediate;
/*     */     int temp;
/* 192 */     if (!QRCode.isValidMaskPattern(maskPattern)) {
/* 193 */       throw new IllegalArgumentException("Invalid mask pattern");
/*     */     }
/*     */     
/* 196 */     switch (maskPattern) {
/*     */       case 0:
/* 198 */         intermediate = y + x & 0x1;
/*     */         break;
/*     */       case 1:
/* 201 */         intermediate = y & 0x1;
/*     */         break;
/*     */       case 2:
/* 204 */         intermediate = x % 3;
/*     */         break;
/*     */       case 3:
/* 207 */         intermediate = (y + x) % 3;
/*     */         break;
/*     */       case 4:
/* 210 */         intermediate = (y >>> 1) + x / 3 & 0x1;
/*     */         break;
/*     */       case 5:
/* 213 */         temp = y * x;
/* 214 */         intermediate = (temp & 0x1) + temp % 3;
/*     */         break;
/*     */       case 6:
/* 217 */         temp = y * x;
/* 218 */         intermediate = (temp & 0x1) + temp % 3 & 0x1;
/*     */         break;
/*     */       case 7:
/* 221 */         temp = y * x;
/* 222 */         intermediate = temp % 3 + (y + x & 0x1) & 0x1;
/*     */         break;
/*     */       default:
/* 225 */         throw new IllegalArgumentException("Invalid mask pattern: " + maskPattern);
/*     */     } 
/* 227 */     return (intermediate == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int applyMaskPenaltyRule1Internal(ByteMatrix matrix, boolean isHorizontal) {
/* 233 */     int penalty = 0;
/* 234 */     int numSameBitCells = 0;
/* 235 */     int prevBit = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     int iLimit = isHorizontal ? matrix.getHeight() : matrix.getWidth();
/* 245 */     int jLimit = isHorizontal ? matrix.getWidth() : matrix.getHeight();
/* 246 */     byte[][] array = matrix.getArray();
/* 247 */     for (int i = 0; i < iLimit; i++) {
/* 248 */       for (int j = 0; j < jLimit; j++) {
/* 249 */         int bit = isHorizontal ? array[i][j] : array[j][i];
/* 250 */         if (bit == prevBit) {
/* 251 */           numSameBitCells++;
/*     */ 
/*     */           
/* 254 */           if (numSameBitCells == 5) {
/* 255 */             penalty += 3;
/* 256 */           } else if (numSameBitCells > 5) {
/*     */ 
/*     */             
/* 259 */             penalty++;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 264 */           numSameBitCells = 1;
/* 265 */           prevBit = bit;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 270 */       numSameBitCells = 0;
/*     */     } 
/* 272 */     return penalty;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/MaskUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */