/*     */ package com.itextpdf.barcodes.qrcode;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Encoder
/*     */ {
/*  58 */   private static final int[] ALPHANUMERIC_TABLE = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calculateMaskPenalty(ByteMatrix matrix) {
/*  87 */     int penalty = 0;
/*  88 */     penalty += MaskUtil.applyMaskPenaltyRule1(matrix);
/*  89 */     penalty += MaskUtil.applyMaskPenaltyRule2(matrix);
/*  90 */     penalty += MaskUtil.applyMaskPenaltyRule3(matrix);
/*  91 */     penalty += MaskUtil.applyMaskPenaltyRule4(matrix);
/*  92 */     return penalty;
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
/*     */   public static void encode(String content, ErrorCorrectionLevel ecLevel, QRCode qrCode) throws WriterException {
/* 113 */     encode(content, ecLevel, null, qrCode);
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
/*     */   public static void encode(String content, ErrorCorrectionLevel ecLevel, Map<EncodeHintType, Object> hints, QRCode qrCode) throws WriterException {
/* 136 */     String encoding = (hints == null) ? null : (String)hints.get(EncodeHintType.CHARACTER_SET);
/* 137 */     if (encoding == null) {
/* 138 */       encoding = "ISO-8859-1";
/*     */     }
/* 140 */     int desiredMinVersion = (hints == null || hints.get(EncodeHintType.MIN_VERSION_NR) == null) ? 1 : ((Integer)hints.get(EncodeHintType.MIN_VERSION_NR)).intValue();
/*     */     
/* 142 */     if (desiredMinVersion < 1) desiredMinVersion = 1; 
/* 143 */     if (desiredMinVersion > 40) desiredMinVersion = 40;
/*     */     
/* 145 */     Mode mode = chooseMode(content, encoding);
/*     */ 
/*     */     
/* 148 */     BitVector dataBits = new BitVector();
/* 149 */     appendBytes(content, mode, dataBits, encoding);
/*     */     
/* 151 */     int numInputBytes = dataBits.sizeInBytes();
/* 152 */     initQRCode(numInputBytes, ecLevel, desiredMinVersion, mode, qrCode);
/*     */ 
/*     */     
/* 155 */     BitVector headerAndDataBits = new BitVector();
/*     */ 
/*     */     
/* 158 */     if (mode == Mode.BYTE && !"ISO-8859-1".equals(encoding)) {
/* 159 */       CharacterSetECI eci = CharacterSetECI.getCharacterSetECIByName(encoding);
/* 160 */       if (eci != null) {
/* 161 */         appendECI(eci, headerAndDataBits);
/*     */       }
/*     */     } 
/*     */     
/* 165 */     appendModeInfo(mode, headerAndDataBits);
/*     */     
/* 167 */     int numLetters = mode.equals(Mode.BYTE) ? dataBits.sizeInBytes() : content.length();
/* 168 */     appendLengthInfo(numLetters, qrCode.getVersion(), mode, headerAndDataBits);
/* 169 */     headerAndDataBits.appendBitVector(dataBits);
/*     */ 
/*     */     
/* 172 */     terminateBits(qrCode.getNumDataBytes(), headerAndDataBits);
/*     */ 
/*     */     
/* 175 */     BitVector finalBits = new BitVector();
/* 176 */     interleaveWithECBytes(headerAndDataBits, qrCode.getNumTotalBytes(), qrCode.getNumDataBytes(), qrCode
/* 177 */         .getNumRSBlocks(), finalBits);
/*     */ 
/*     */     
/* 180 */     ByteMatrix matrix = new ByteMatrix(qrCode.getMatrixWidth(), qrCode.getMatrixWidth());
/* 181 */     qrCode.setMaskPattern(chooseMaskPattern(finalBits, qrCode.getECLevel(), qrCode.getVersion(), matrix));
/*     */ 
/*     */ 
/*     */     
/* 185 */     MatrixUtil.buildMatrix(finalBits, qrCode.getECLevel(), qrCode.getVersion(), qrCode
/* 186 */         .getMaskPattern(), matrix);
/* 187 */     qrCode.setMatrix(matrix);
/*     */     
/* 189 */     if (!qrCode.isValid()) {
/* 190 */       throw new WriterException("Invalid QR code: " + qrCode.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getAlphanumericCode(int code) {
/* 199 */     if (code < ALPHANUMERIC_TABLE.length) {
/* 200 */       return ALPHANUMERIC_TABLE[code];
/*     */     }
/* 202 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mode chooseMode(String content) {
/* 212 */     return chooseMode(content, null);
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
/*     */   public static Mode chooseMode(String content, String encoding) {
/* 224 */     if ("Shift_JIS".equals(encoding))
/*     */     {
/* 226 */       return isOnlyDoubleByteKanji(content) ? Mode.KANJI : Mode.BYTE;
/*     */     }
/* 228 */     boolean hasNumeric = false;
/* 229 */     boolean hasAlphanumeric = false;
/* 230 */     for (int i = 0; i < content.length(); i++) {
/* 231 */       char c = content.charAt(i);
/* 232 */       if (c >= '0' && c <= '9') {
/* 233 */         hasNumeric = true;
/* 234 */       } else if (getAlphanumericCode(c) != -1) {
/* 235 */         hasAlphanumeric = true;
/*     */       } else {
/* 237 */         return Mode.BYTE;
/*     */       } 
/*     */     } 
/* 240 */     if (hasAlphanumeric)
/* 241 */       return Mode.ALPHANUMERIC; 
/* 242 */     if (hasNumeric) {
/* 243 */       return Mode.NUMERIC;
/*     */     }
/* 245 */     return Mode.BYTE;
/*     */   }
/*     */   
/*     */   private static boolean isOnlyDoubleByteKanji(String content) {
/*     */     byte[] bytes;
/*     */     try {
/* 251 */       bytes = content.getBytes("Shift_JIS");
/* 252 */     } catch (UnsupportedEncodingException uee) {
/* 253 */       return false;
/*     */     } 
/* 255 */     int length = bytes.length;
/* 256 */     if (length % 2 != 0) {
/* 257 */       return false;
/*     */     }
/* 259 */     for (int i = 0; i < length; i += 2) {
/* 260 */       int byte1 = bytes[i] & 0xFF;
/* 261 */       if ((byte1 < 129 || byte1 > 159) && (byte1 < 224 || byte1 > 235)) {
/* 262 */         return false;
/*     */       }
/*     */     } 
/* 265 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int chooseMaskPattern(BitVector bits, ErrorCorrectionLevel ecLevel, int version, ByteMatrix matrix) throws WriterException {
/* 272 */     int minPenalty = Integer.MAX_VALUE;
/* 273 */     int bestMaskPattern = -1;
/*     */ 
/*     */     
/* 276 */     for (int maskPattern = 0; maskPattern < 8; maskPattern++) {
/* 277 */       MatrixUtil.buildMatrix(bits, ecLevel, version, maskPattern, matrix);
/* 278 */       int penalty = calculateMaskPenalty(matrix);
/* 279 */       if (penalty < minPenalty) {
/* 280 */         minPenalty = penalty;
/* 281 */         bestMaskPattern = maskPattern;
/*     */       } 
/*     */     } 
/* 284 */     return bestMaskPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initQRCode(int numInputBytes, ErrorCorrectionLevel ecLevel, int desiredMinVersion, Mode mode, QRCode qrCode) throws WriterException {
/* 293 */     qrCode.setECLevel(ecLevel);
/* 294 */     qrCode.setMode(mode);
/*     */ 
/*     */     
/* 297 */     for (int versionNum = desiredMinVersion; versionNum <= 40; versionNum++) {
/* 298 */       Version version = Version.getVersionForNumber(versionNum);
/*     */       
/* 300 */       int numBytes = version.getTotalCodewords();
/*     */       
/* 302 */       Version.ECBlocks ecBlocks = version.getECBlocksForLevel(ecLevel);
/* 303 */       int numEcBytes = ecBlocks.getTotalECCodewords();
/*     */       
/* 305 */       int numRSBlocks = ecBlocks.getNumBlocks();
/*     */       
/* 307 */       int numDataBytes = numBytes - numEcBytes;
/*     */ 
/*     */ 
/*     */       
/* 311 */       if (numDataBytes >= numInputBytes + 3) {
/*     */         
/* 313 */         qrCode.setVersion(versionNum);
/* 314 */         qrCode.setNumTotalBytes(numBytes);
/* 315 */         qrCode.setNumDataBytes(numDataBytes);
/* 316 */         qrCode.setNumRSBlocks(numRSBlocks);
/*     */         
/* 318 */         qrCode.setNumECBytes(numEcBytes);
/*     */         
/* 320 */         qrCode.setMatrixWidth(version.getDimensionForVersion());
/*     */         return;
/*     */       } 
/*     */     } 
/* 324 */     throw new WriterException("Cannot find proper rs block info (input data too big?)");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void terminateBits(int numDataBytes, BitVector bits) throws WriterException {
/* 331 */     int capacity = numDataBytes << 3;
/* 332 */     if (bits.size() > capacity) {
/* 333 */       throw new WriterException("data bits cannot fit in the QR Code" + bits.size() + " > " + capacity);
/*     */     }
/*     */ 
/*     */     
/* 337 */     for (int i = 0; i < 4 && bits.size() < capacity; i++) {
/* 338 */       bits.appendBit(0);
/*     */     }
/* 340 */     int numBitsInLastByte = bits.size() % 8;
/*     */     
/* 342 */     if (numBitsInLastByte > 0) {
/* 343 */       int numPaddingBits = 8 - numBitsInLastByte;
/* 344 */       for (int k = 0; k < numPaddingBits; k++) {
/* 345 */         bits.appendBit(0);
/*     */       }
/*     */     } 
/*     */     
/* 349 */     if (bits.size() % 8 != 0) {
/* 350 */       throw new WriterException("Number of bits is not a multiple of 8");
/*     */     }
/*     */     
/* 353 */     int numPaddingBytes = numDataBytes - bits.sizeInBytes();
/* 354 */     for (int j = 0; j < numPaddingBytes; j++) {
/* 355 */       if (j % 2 == 0) {
/* 356 */         bits.appendBits(236, 8);
/*     */       } else {
/* 358 */         bits.appendBits(17, 8);
/*     */       } 
/*     */     } 
/* 361 */     if (bits.size() != capacity) {
/* 362 */       throw new WriterException("Bits size does not equal capacity");
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
/*     */   static void getNumDataBytesAndNumECBytesForBlockID(int numTotalBytes, int numDataBytes, int numRSBlocks, int blockID, int[] numDataBytesInBlock, int[] numECBytesInBlock) throws WriterException {
/* 374 */     if (blockID >= numRSBlocks) {
/* 375 */       throw new WriterException("Block ID too large");
/*     */     }
/*     */     
/* 378 */     int numRsBlocksInGroup2 = numTotalBytes % numRSBlocks;
/*     */     
/* 380 */     int numRsBlocksInGroup1 = numRSBlocks - numRsBlocksInGroup2;
/*     */     
/* 382 */     int numTotalBytesInGroup1 = numTotalBytes / numRSBlocks;
/*     */     
/* 384 */     int numTotalBytesInGroup2 = numTotalBytesInGroup1 + 1;
/*     */     
/* 386 */     int numDataBytesInGroup1 = numDataBytes / numRSBlocks;
/*     */     
/* 388 */     int numDataBytesInGroup2 = numDataBytesInGroup1 + 1;
/*     */     
/* 390 */     int numEcBytesInGroup1 = numTotalBytesInGroup1 - numDataBytesInGroup1;
/*     */     
/* 392 */     int numEcBytesInGroup2 = numTotalBytesInGroup2 - numDataBytesInGroup2;
/*     */ 
/*     */     
/* 395 */     if (numEcBytesInGroup1 != numEcBytesInGroup2) {
/* 396 */       throw new WriterException("EC bytes mismatch");
/*     */     }
/*     */     
/* 399 */     if (numRSBlocks != numRsBlocksInGroup1 + numRsBlocksInGroup2) {
/* 400 */       throw new WriterException("RS blocks mismatch");
/*     */     }
/*     */     
/* 403 */     if (numTotalBytes != (numDataBytesInGroup1 + numEcBytesInGroup1) * numRsBlocksInGroup1 + (numDataBytesInGroup2 + numEcBytesInGroup2) * numRsBlocksInGroup2)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 408 */       throw new WriterException("Total bytes mismatch");
/*     */     }
/*     */     
/* 411 */     if (blockID < numRsBlocksInGroup1) {
/* 412 */       numDataBytesInBlock[0] = numDataBytesInGroup1;
/* 413 */       numECBytesInBlock[0] = numEcBytesInGroup1;
/*     */     } else {
/* 415 */       numDataBytesInBlock[0] = numDataBytesInGroup2;
/* 416 */       numECBytesInBlock[0] = numEcBytesInGroup2;
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
/*     */   static void interleaveWithECBytes(BitVector bits, int numTotalBytes, int numDataBytes, int numRSBlocks, BitVector result) throws WriterException {
/* 428 */     if (bits.sizeInBytes() != numDataBytes) {
/* 429 */       throw new WriterException("Number of bits and data bytes does not match");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 434 */     int dataBytesOffset = 0;
/* 435 */     int maxNumDataBytes = 0;
/* 436 */     int maxNumEcBytes = 0;
/*     */ 
/*     */     
/* 439 */     List<BlockPair> blocks = new ArrayList<>(numRSBlocks);
/*     */     int i;
/* 441 */     for (i = 0; i < numRSBlocks; i++) {
/* 442 */       int[] numDataBytesInBlock = new int[1];
/* 443 */       int[] numEcBytesInBlock = new int[1];
/* 444 */       getNumDataBytesAndNumECBytesForBlockID(numTotalBytes, numDataBytes, numRSBlocks, i, numDataBytesInBlock, numEcBytesInBlock);
/*     */ 
/*     */ 
/*     */       
/* 448 */       ByteArray dataBytes = new ByteArray();
/* 449 */       dataBytes.set(bits.getArray(), dataBytesOffset, numDataBytesInBlock[0]);
/* 450 */       ByteArray ecBytes = generateECBytes(dataBytes, numEcBytesInBlock[0]);
/* 451 */       blocks.add(new BlockPair(dataBytes, ecBytes));
/*     */       
/* 453 */       maxNumDataBytes = Math.max(maxNumDataBytes, dataBytes.size());
/* 454 */       maxNumEcBytes = Math.max(maxNumEcBytes, ecBytes.size());
/* 455 */       dataBytesOffset += numDataBytesInBlock[0];
/*     */     } 
/* 457 */     if (numDataBytes != dataBytesOffset) {
/* 458 */       throw new WriterException("Data bytes does not match offset");
/*     */     }
/*     */ 
/*     */     
/* 462 */     for (i = 0; i < maxNumDataBytes; i++) {
/* 463 */       for (int j = 0; j < blocks.size(); j++) {
/* 464 */         ByteArray dataBytes = ((BlockPair)blocks.get(j)).getDataBytes();
/* 465 */         if (i < dataBytes.size()) {
/* 466 */           result.appendBits(dataBytes.at(i), 8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 471 */     for (i = 0; i < maxNumEcBytes; i++) {
/* 472 */       for (int j = 0; j < blocks.size(); j++) {
/* 473 */         ByteArray ecBytes = ((BlockPair)blocks.get(j)).getErrorCorrectionBytes();
/* 474 */         if (i < ecBytes.size()) {
/* 475 */           result.appendBits(ecBytes.at(i), 8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 481 */     if (numTotalBytes != result.sizeInBytes()) {
/* 482 */       throw new WriterException("Interleaving error: " + numTotalBytes + " and " + result
/* 483 */           .sizeInBytes() + " differ.");
/*     */     }
/*     */   }
/*     */   
/*     */   static ByteArray generateECBytes(ByteArray dataBytes, int numEcBytesInBlock) {
/* 488 */     int numDataBytes = dataBytes.size();
/* 489 */     int[] toEncode = new int[numDataBytes + numEcBytesInBlock];
/* 490 */     for (int i = 0; i < numDataBytes; i++) {
/* 491 */       toEncode[i] = dataBytes.at(i);
/*     */     }
/* 493 */     (new ReedSolomonEncoder(GF256.QR_CODE_FIELD)).encode(toEncode, numEcBytesInBlock);
/*     */     
/* 495 */     ByteArray ecBytes = new ByteArray(numEcBytesInBlock);
/* 496 */     for (int j = 0; j < numEcBytesInBlock; j++) {
/* 497 */       ecBytes.set(j, toEncode[numDataBytes + j]);
/*     */     }
/* 499 */     return ecBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void appendModeInfo(Mode mode, BitVector bits) {
/* 506 */     bits.appendBits(mode.getBits(), 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void appendLengthInfo(int numLetters, int version, Mode mode, BitVector bits) throws WriterException {
/* 515 */     int numBits = mode.getCharacterCountBits(Version.getVersionForNumber(version));
/* 516 */     if (numLetters > (1 << numBits) - 1) {
/* 517 */       throw new WriterException(numLetters + "is bigger than" + ((1 << numBits) - 1));
/*     */     }
/* 519 */     bits.appendBits(numLetters, numBits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void appendBytes(String content, Mode mode, BitVector bits, String encoding) throws WriterException {
/* 527 */     if (mode.equals(Mode.NUMERIC)) {
/* 528 */       appendNumericBytes(content, bits);
/* 529 */     } else if (mode.equals(Mode.ALPHANUMERIC)) {
/* 530 */       appendAlphanumericBytes(content, bits);
/* 531 */     } else if (mode.equals(Mode.BYTE)) {
/* 532 */       append8BitBytes(content, bits, encoding);
/* 533 */     } else if (mode.equals(Mode.KANJI)) {
/* 534 */       appendKanjiBytes(content, bits);
/*     */     } else {
/* 536 */       throw new WriterException("Invalid mode: " + mode);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void appendNumericBytes(String content, BitVector bits) {
/* 541 */     int length = content.length();
/* 542 */     int i = 0;
/* 543 */     while (i < length) {
/* 544 */       int num1 = content.charAt(i) - 48;
/* 545 */       if (i + 2 < length) {
/*     */         
/* 547 */         int num2 = content.charAt(i + 1) - 48;
/* 548 */         int num3 = content.charAt(i + 2) - 48;
/* 549 */         bits.appendBits(num1 * 100 + num2 * 10 + num3, 10);
/* 550 */         i += 3; continue;
/* 551 */       }  if (i + 1 < length) {
/*     */         
/* 553 */         int num2 = content.charAt(i + 1) - 48;
/* 554 */         bits.appendBits(num1 * 10 + num2, 7);
/* 555 */         i += 2;
/*     */         continue;
/*     */       } 
/* 558 */       bits.appendBits(num1, 4);
/* 559 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void appendAlphanumericBytes(String content, BitVector bits) throws WriterException {
/* 565 */     int length = content.length();
/* 566 */     int i = 0;
/* 567 */     while (i < length) {
/* 568 */       int code1 = getAlphanumericCode(content.charAt(i));
/* 569 */       if (code1 == -1) {
/* 570 */         throw new WriterException();
/*     */       }
/* 572 */       if (i + 1 < length) {
/* 573 */         int code2 = getAlphanumericCode(content.charAt(i + 1));
/* 574 */         if (code2 == -1) {
/* 575 */           throw new WriterException();
/*     */         }
/*     */         
/* 578 */         bits.appendBits(code1 * 45 + code2, 11);
/* 579 */         i += 2;
/*     */         continue;
/*     */       } 
/* 582 */       bits.appendBits(code1, 6);
/* 583 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void append8BitBytes(String content, BitVector bits, String encoding) throws WriterException {
/*     */     byte[] bytes;
/*     */     try {
/* 592 */       bytes = content.getBytes(encoding);
/* 593 */     } catch (UnsupportedEncodingException uee) {
/* 594 */       throw new WriterException(uee.toString());
/*     */     } 
/* 596 */     for (int i = 0; i < bytes.length; i++) {
/* 597 */       bits.appendBits(bytes[i], 8);
/*     */     }
/*     */   }
/*     */   
/*     */   static void appendKanjiBytes(String content, BitVector bits) throws WriterException {
/*     */     byte[] bytes;
/*     */     try {
/* 604 */       bytes = content.getBytes("Shift_JIS");
/* 605 */     } catch (UnsupportedEncodingException uee) {
/* 606 */       throw new WriterException(uee.toString());
/*     */     } 
/* 608 */     int length = bytes.length;
/* 609 */     for (int i = 0; i < length; i += 2) {
/* 610 */       int byte1 = bytes[i] & 0xFF;
/* 611 */       int byte2 = bytes[i + 1] & 0xFF;
/* 612 */       int code = byte1 << 8 | byte2;
/* 613 */       int subtracted = -1;
/* 614 */       if (code >= 33088 && code <= 40956) {
/* 615 */         subtracted = code - 33088;
/* 616 */       } else if (code >= 57408 && code <= 60351) {
/* 617 */         subtracted = code - 49472;
/*     */       } 
/* 619 */       if (subtracted == -1) {
/* 620 */         throw new WriterException("Invalid byte sequence");
/*     */       }
/* 622 */       int encoded = (subtracted >> 8) * 192 + (subtracted & 0xFF);
/* 623 */       bits.appendBits(encoded, 13);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void appendECI(CharacterSetECI eci, BitVector bits) {
/* 628 */     bits.appendBits(Mode.ECI.getBits(), 4);
/*     */     
/* 630 */     bits.appendBits(eci.getValue(), 8);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/Encoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */