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
/*     */ final class MatrixUtil
/*     */ {
/*  55 */   private static final int[][] POSITION_DETECTION_PATTERN = new int[][] { { 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final int[][] HORIZONTAL_SEPARATION_PATTERN = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0 } };
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final int[][] VERTICAL_SEPARATION_PATTERN = new int[][] { { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 } };
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final int[][] POSITION_ADJUSTMENT_PATTERN = new int[][] { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = new int[][] { { -1, -1, -1, -1, -1, -1, -1 }, { 6, 18, -1, -1, -1, -1, -1 }, { 6, 22, -1, -1, -1, -1, -1 }, { 6, 26, -1, -1, -1, -1, -1 }, { 6, 30, -1, -1, -1, -1, -1 }, { 6, 34, -1, -1, -1, -1, -1 }, { 6, 22, 38, -1, -1, -1, -1 }, { 6, 24, 42, -1, -1, -1, -1 }, { 6, 26, 46, -1, -1, -1, -1 }, { 6, 28, 50, -1, -1, -1, -1 }, { 6, 30, 54, -1, -1, -1, -1 }, { 6, 32, 58, -1, -1, -1, -1 }, { 6, 34, 62, -1, -1, -1, -1 }, { 6, 26, 46, 66, -1, -1, -1 }, { 6, 26, 48, 70, -1, -1, -1 }, { 6, 26, 50, 74, -1, -1, -1 }, { 6, 30, 54, 78, -1, -1, -1 }, { 6, 30, 56, 82, -1, -1, -1 }, { 6, 30, 58, 86, -1, -1, -1 }, { 6, 34, 62, 90, -1, -1, -1 }, { 6, 28, 50, 72, 94, -1, -1 }, { 6, 26, 50, 74, 98, -1, -1 }, { 6, 30, 54, 78, 102, -1, -1 }, { 6, 28, 54, 80, 106, -1, -1 }, { 6, 32, 58, 84, 110, -1, -1 }, { 6, 30, 58, 86, 114, -1, -1 }, { 6, 34, 62, 90, 118, -1, -1 }, { 6, 26, 50, 74, 98, 122, -1 }, { 6, 30, 54, 78, 102, 126, -1 }, { 6, 26, 52, 78, 104, 130, -1 }, { 6, 30, 56, 82, 108, 134, -1 }, { 6, 34, 60, 86, 112, 138, -1 }, { 6, 30, 58, 86, 114, 142, -1 }, { 6, 34, 62, 90, 118, 146, -1 }, { 6, 30, 54, 78, 102, 126, 150 }, { 6, 24, 50, 76, 102, 128, 154 }, { 6, 28, 54, 80, 106, 132, 158 }, { 6, 32, 58, 84, 110, 136, 162 }, { 6, 26, 54, 82, 110, 138, 166 }, { 6, 30, 58, 86, 114, 142, 170 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   private static final int[][] TYPE_INFO_COORDINATES = new int[][] { { 8, 0 }, { 8, 1 }, { 8, 2 }, { 8, 3 }, { 8, 4 }, { 8, 5 }, { 8, 7 }, { 8, 8 }, { 7, 8 }, { 5, 8 }, { 4, 8 }, { 3, 8 }, { 2, 8 }, { 1, 8 }, { 0, 8 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int VERSION_INFO_POLY = 7973;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TYPE_INFO_POLY = 1335;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TYPE_INFO_MASK_PATTERN = 21522;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearMatrix(ByteMatrix matrix) {
/* 237 */     matrix.clear((byte)-1);
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
/*     */   public static void buildMatrix(BitVector dataBits, ErrorCorrectionLevel ecLevel, int version, int maskPattern, ByteMatrix matrix) throws WriterException {
/* 252 */     clearMatrix(matrix);
/* 253 */     embedBasicPatterns(version, matrix);
/*     */     
/* 255 */     embedTypeInfo(ecLevel, maskPattern, matrix);
/*     */     
/* 257 */     maybeEmbedVersionInfo(version, matrix);
/*     */     
/* 259 */     embedDataBits(dataBits, maskPattern, matrix);
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
/*     */   public static void embedBasicPatterns(int version, ByteMatrix matrix) throws WriterException {
/* 275 */     embedPositionDetectionPatternsAndSeparators(matrix);
/*     */     
/* 277 */     embedDarkDotAtLeftBottomCorner(matrix);
/*     */ 
/*     */     
/* 280 */     maybeEmbedPositionAdjustmentPatterns(version, matrix);
/*     */     
/* 282 */     embedTimingPatterns(matrix);
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
/*     */   public static void embedTypeInfo(ErrorCorrectionLevel ecLevel, int maskPattern, ByteMatrix matrix) throws WriterException {
/* 294 */     BitVector typeInfoBits = new BitVector();
/* 295 */     makeTypeInfoBits(ecLevel, maskPattern, typeInfoBits);
/*     */     
/* 297 */     for (int i = 0; i < typeInfoBits.size(); i++) {
/*     */ 
/*     */       
/* 300 */       int bit = typeInfoBits.at(typeInfoBits.size() - 1 - i);
/*     */ 
/*     */       
/* 303 */       int x1 = TYPE_INFO_COORDINATES[i][0];
/* 304 */       int y1 = TYPE_INFO_COORDINATES[i][1];
/* 305 */       matrix.set(x1, y1, bit);
/*     */       
/* 307 */       if (i < 8) {
/*     */         
/* 309 */         int x2 = matrix.getWidth() - i - 1;
/* 310 */         int y2 = 8;
/* 311 */         matrix.set(x2, y2, bit);
/*     */       } else {
/*     */         
/* 314 */         int x2 = 8;
/* 315 */         int y2 = matrix.getHeight() - 7 + i - 8;
/* 316 */         matrix.set(x2, y2, bit);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void maybeEmbedVersionInfo(int version, ByteMatrix matrix) throws WriterException {
/* 336 */     if (version < 7) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 341 */     BitVector versionInfoBits = new BitVector();
/* 342 */     makeVersionInfoBits(version, versionInfoBits);
/*     */ 
/*     */     
/* 345 */     int bitIndex = 17;
/* 346 */     for (int i = 0; i < 6; i++) {
/* 347 */       for (int j = 0; j < 3; j++) {
/*     */ 
/*     */         
/* 350 */         int bit = versionInfoBits.at(bitIndex);
/* 351 */         bitIndex--;
/*     */ 
/*     */         
/* 354 */         matrix.set(i, matrix.getHeight() - 11 + j, bit);
/*     */ 
/*     */         
/* 357 */         matrix.set(matrix.getHeight() - 11 + j, i, bit);
/*     */       } 
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
/*     */   
/*     */   public static void embedDataBits(BitVector dataBits, int maskPattern, ByteMatrix matrix) throws WriterException {
/* 373 */     int bitIndex = 0;
/* 374 */     int direction = -1;
/*     */ 
/*     */     
/* 377 */     int x = matrix.getWidth() - 1;
/* 378 */     int y = matrix.getHeight() - 1;
/* 379 */     while (x > 0) {
/*     */ 
/*     */       
/* 382 */       if (x == 6) {
/* 383 */         x--;
/*     */       }
/* 385 */       while (y >= 0 && y < matrix.getHeight()) {
/* 386 */         for (int i = 0; i < 2; i++) {
/* 387 */           int xx = x - i;
/*     */ 
/*     */           
/* 390 */           if (isEmpty(matrix.get(xx, y))) {
/*     */             int bit;
/*     */ 
/*     */             
/* 394 */             if (bitIndex < dataBits.size()) {
/* 395 */               bit = dataBits.at(bitIndex);
/* 396 */               bitIndex++;
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 401 */               bit = 0;
/*     */             } 
/*     */ 
/*     */             
/* 405 */             if (maskPattern != -1 && 
/* 406 */               MaskUtil.getDataMaskBit(maskPattern, xx, y)) {
/* 407 */               bit ^= 0x1;
/*     */             }
/*     */             
/* 410 */             matrix.set(xx, y, bit);
/*     */           } 
/* 412 */         }  y += direction;
/*     */       } 
/*     */ 
/*     */       
/* 416 */       direction = -direction;
/* 417 */       y += direction;
/*     */ 
/*     */       
/* 420 */       x -= 2;
/*     */     } 
/*     */ 
/*     */     
/* 424 */     if (bitIndex != dataBits.size()) {
/* 425 */       throw new WriterException("Not all bits consumed: " + bitIndex + '/' + dataBits.size());
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
/*     */   public static int findMSBSet(int value) {
/* 439 */     int numDigits = 0;
/* 440 */     while (value != 0) {
/* 441 */       value >>>= 1;
/* 442 */       numDigits++;
/*     */     } 
/* 444 */     return numDigits;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateBCHCode(int value, int poly) {
/* 479 */     int msbSetInPoly = findMSBSet(poly);
/* 480 */     value <<= msbSetInPoly - 1;
/*     */     
/* 482 */     while (findMSBSet(value) >= msbSetInPoly) {
/* 483 */       value ^= poly << findMSBSet(value) - msbSetInPoly;
/*     */     }
/*     */     
/* 486 */     return value;
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
/*     */   public static void makeTypeInfoBits(ErrorCorrectionLevel ecLevel, int maskPattern, BitVector bits) throws WriterException {
/* 499 */     if (!QRCode.isValidMaskPattern(maskPattern)) {
/* 500 */       throw new WriterException("Invalid mask pattern");
/*     */     }
/* 502 */     int typeInfo = ecLevel.getBits() << 3 | maskPattern;
/* 503 */     bits.appendBits(typeInfo, 5);
/*     */     
/* 505 */     int bchCode = calculateBCHCode(typeInfo, 1335);
/* 506 */     bits.appendBits(bchCode, 10);
/*     */     
/* 508 */     BitVector maskBits = new BitVector();
/* 509 */     maskBits.appendBits(21522, 15);
/* 510 */     bits.xor(maskBits);
/*     */ 
/*     */     
/* 513 */     if (bits.size() != 15) {
/* 514 */       throw new WriterException("should not happen but we got: " + bits.size());
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
/*     */   
/*     */   public static void makeVersionInfoBits(int version, BitVector bits) throws WriterException {
/* 529 */     bits.appendBits(version, 6);
/* 530 */     int bchCode = calculateBCHCode(version, 7973);
/* 531 */     bits.appendBits(bchCode, 12);
/*     */ 
/*     */     
/* 534 */     if (bits.size() != 18) {
/* 535 */       throw new WriterException("should not happen but we got: " + bits.size());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isEmpty(byte value) {
/* 541 */     return (value == -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidValue(byte value) {
/* 548 */     return (value == -1 || value == 0 || value == 1);
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
/*     */   private static void embedTimingPatterns(ByteMatrix matrix) throws WriterException {
/* 561 */     for (int i = 8; i < matrix.getWidth() - 8; i++) {
/* 562 */       int bit = (i + 1) % 2;
/*     */ 
/*     */       
/* 565 */       if (!isValidValue(matrix.get(i, 6))) {
/* 566 */         throw new WriterException();
/*     */       }
/* 568 */       if (isEmpty(matrix.get(i, 6))) {
/* 569 */         matrix.set(i, 6, bit);
/*     */       }
/*     */ 
/*     */       
/* 573 */       if (!isValidValue(matrix.get(6, i))) {
/* 574 */         throw new WriterException();
/*     */       }
/* 576 */       if (isEmpty(matrix.get(6, i))) {
/* 577 */         matrix.set(6, i, bit);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void embedDarkDotAtLeftBottomCorner(ByteMatrix matrix) throws WriterException {
/* 584 */     if (matrix.get(8, matrix.getHeight() - 8) == 0) {
/* 585 */       throw new WriterException();
/*     */     }
/* 587 */     matrix.set(8, matrix.getHeight() - 8, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedHorizontalSeparationPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
/* 593 */     if ((HORIZONTAL_SEPARATION_PATTERN[0]).length != 8 || HORIZONTAL_SEPARATION_PATTERN.length != 1) {
/* 594 */       throw new WriterException("Bad horizontal separation pattern");
/*     */     }
/* 596 */     for (int x = 0; x < 8; x++) {
/* 597 */       if (!isEmpty(matrix.get(xStart + x, yStart))) {
/* 598 */         throw new WriterException();
/*     */       }
/* 600 */       matrix.set(xStart + x, yStart, HORIZONTAL_SEPARATION_PATTERN[0][x]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedVerticalSeparationPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
/* 607 */     if ((VERTICAL_SEPARATION_PATTERN[0]).length != 1 || VERTICAL_SEPARATION_PATTERN.length != 7) {
/* 608 */       throw new WriterException("Bad vertical separation pattern");
/*     */     }
/* 610 */     for (int y = 0; y < 7; y++) {
/* 611 */       if (!isEmpty(matrix.get(xStart, yStart + y))) {
/* 612 */         throw new WriterException();
/*     */       }
/* 614 */       matrix.set(xStart, yStart + y, VERTICAL_SEPARATION_PATTERN[y][0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedPositionAdjustmentPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
/* 624 */     if ((POSITION_ADJUSTMENT_PATTERN[0]).length != 5 || POSITION_ADJUSTMENT_PATTERN.length != 5) {
/* 625 */       throw new WriterException("Bad position adjustment");
/*     */     }
/* 627 */     for (int y = 0; y < 5; y++) {
/* 628 */       for (int x = 0; x < 5; x++) {
/* 629 */         if (!isEmpty(matrix.get(xStart + x, yStart + y))) {
/* 630 */           throw new WriterException();
/*     */         }
/* 632 */         matrix.set(xStart + x, yStart + y, POSITION_ADJUSTMENT_PATTERN[y][x]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedPositionDetectionPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
/* 640 */     if ((POSITION_DETECTION_PATTERN[0]).length != 7 || POSITION_DETECTION_PATTERN.length != 7) {
/* 641 */       throw new WriterException("Bad position detection pattern");
/*     */     }
/* 643 */     for (int y = 0; y < 7; y++) {
/* 644 */       for (int x = 0; x < 7; x++) {
/* 645 */         if (!isEmpty(matrix.get(xStart + x, yStart + y))) {
/* 646 */           throw new WriterException();
/*     */         }
/* 648 */         matrix.set(xStart + x, yStart + y, POSITION_DETECTION_PATTERN[y][x]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedPositionDetectionPatternsAndSeparators(ByteMatrix matrix) throws WriterException {
/* 656 */     int pdpWidth = (POSITION_DETECTION_PATTERN[0]).length;
/*     */     
/* 658 */     embedPositionDetectionPattern(0, 0, matrix);
/*     */     
/* 660 */     embedPositionDetectionPattern(matrix.getWidth() - pdpWidth, 0, matrix);
/*     */     
/* 662 */     embedPositionDetectionPattern(0, matrix.getWidth() - pdpWidth, matrix);
/*     */ 
/*     */     
/* 665 */     int hspWidth = (HORIZONTAL_SEPARATION_PATTERN[0]).length;
/*     */     
/* 667 */     embedHorizontalSeparationPattern(0, hspWidth - 1, matrix);
/*     */     
/* 669 */     embedHorizontalSeparationPattern(matrix.getWidth() - hspWidth, hspWidth - 1, matrix);
/*     */ 
/*     */     
/* 672 */     embedHorizontalSeparationPattern(0, matrix.getWidth() - hspWidth, matrix);
/*     */ 
/*     */     
/* 675 */     int vspSize = VERTICAL_SEPARATION_PATTERN.length;
/*     */     
/* 677 */     embedVerticalSeparationPattern(vspSize, 0, matrix);
/*     */     
/* 679 */     embedVerticalSeparationPattern(matrix.getHeight() - vspSize - 1, 0, matrix);
/*     */     
/* 681 */     embedVerticalSeparationPattern(vspSize, matrix.getHeight() - vspSize, matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void maybeEmbedPositionAdjustmentPatterns(int version, ByteMatrix matrix) throws WriterException {
/* 690 */     if (version < 2) {
/*     */       return;
/*     */     }
/* 693 */     int index = version - 1;
/* 694 */     int[] coordinates = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[index];
/* 695 */     int numCoordinates = (POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[index]).length;
/* 696 */     for (int i = 0; i < numCoordinates; i++) {
/* 697 */       for (int j = 0; j < numCoordinates; j++) {
/* 698 */         int y = coordinates[i];
/* 699 */         int x = coordinates[j];
/* 700 */         if (x != -1 && y != -1)
/*     */         {
/*     */ 
/*     */           
/* 704 */           if (isEmpty(matrix.get(x, y)))
/*     */           {
/*     */             
/* 707 */             embedPositionAdjustmentPattern(x - 2, y - 2, matrix);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/MatrixUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */