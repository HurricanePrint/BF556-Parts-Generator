/*     */ package com.itextpdf.io.codec.brotli.dec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Decode
/*     */ {
/*     */   private static final int DEFAULT_CODE_LENGTH = 8;
/*     */   private static final int CODE_LENGTH_REPEAT_CODE = 16;
/*     */   private static final int NUM_LITERAL_CODES = 256;
/*     */   private static final int NUM_INSERT_AND_COPY_CODES = 704;
/*     */   private static final int NUM_BLOCK_LENGTH_CODES = 26;
/*     */   private static final int LITERAL_CONTEXT_BITS = 6;
/*     */   private static final int DISTANCE_CONTEXT_BITS = 2;
/*     */   private static final int HUFFMAN_TABLE_BITS = 8;
/*     */   private static final int HUFFMAN_TABLE_MASK = 255;
/*     */   private static final int CODE_LENGTH_CODES = 18;
/*  26 */   private static final int[] CODE_LENGTH_CODE_ORDER = new int[] { 1, 2, 3, 4, 0, 5, 17, 6, 16, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
/*     */ 
/*     */   
/*     */   private static final int NUM_DISTANCE_SHORT_CODES = 16;
/*     */   
/*  31 */   private static final int[] DISTANCE_SHORT_CODE_INDEX_OFFSET = new int[] { 3, 2, 1, 0, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2 };
/*     */ 
/*     */ 
/*     */   
/*  35 */   private static final int[] DISTANCE_SHORT_CODE_VALUE_OFFSET = new int[] { 0, 0, 0, 0, -1, 1, -2, 2, -3, 3, -1, 1, -2, 2, -3, 3 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private static final int[] FIXED_TABLE = new int[] { 131072, 131076, 131075, 196610, 131072, 131076, 131075, 262145, 131072, 131076, 131075, 196610, 131072, 131076, 131075, 262149 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int decodeVarLenUnsignedByte(BitReader br) {
/*  51 */     if (BitReader.readBits(br, 1) != 0) {
/*  52 */       int n = BitReader.readBits(br, 3);
/*  53 */       if (n == 0) {
/*  54 */         return 1;
/*     */       }
/*  56 */       return BitReader.readBits(br, n) + (1 << n);
/*     */     } 
/*     */     
/*  59 */     return 0;
/*     */   }
/*     */   
/*     */   private static void decodeMetaBlockLength(BitReader br, State state) {
/*  63 */     state.inputEnd = (BitReader.readBits(br, 1) == 1);
/*  64 */     state.metaBlockLength = 0;
/*  65 */     state.isUncompressed = false;
/*  66 */     state.isMetadata = false;
/*  67 */     if (state.inputEnd && BitReader.readBits(br, 1) != 0) {
/*     */       return;
/*     */     }
/*  70 */     int sizeNibbles = BitReader.readBits(br, 2) + 4;
/*  71 */     if (sizeNibbles == 7) {
/*  72 */       state.isMetadata = true;
/*  73 */       if (BitReader.readBits(br, 1) != 0) {
/*  74 */         throw new BrotliRuntimeException("Corrupted reserved bit");
/*     */       }
/*  76 */       int sizeBytes = BitReader.readBits(br, 2);
/*  77 */       if (sizeBytes == 0) {
/*     */         return;
/*     */       }
/*  80 */       for (int i = 0; i < sizeBytes; i++) {
/*  81 */         int bits = BitReader.readBits(br, 8);
/*  82 */         if (bits == 0 && i + 1 == sizeBytes && sizeBytes > 1) {
/*  83 */           throw new BrotliRuntimeException("Exuberant nibble");
/*     */         }
/*  85 */         state.metaBlockLength |= bits << i * 8;
/*     */       } 
/*     */     } else {
/*  88 */       for (int i = 0; i < sizeNibbles; i++) {
/*  89 */         int bits = BitReader.readBits(br, 4);
/*  90 */         if (bits == 0 && i + 1 == sizeNibbles && sizeNibbles > 4) {
/*  91 */           throw new BrotliRuntimeException("Exuberant nibble");
/*     */         }
/*  93 */         state.metaBlockLength |= bits << i * 4;
/*     */       } 
/*     */     } 
/*  96 */     state.metaBlockLength++;
/*  97 */     if (!state.inputEnd) {
/*  98 */       state.isUncompressed = (BitReader.readBits(br, 1) == 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readSymbol(int[] table, int offset, BitReader br) {
/* 106 */     int val = (int)(br.accumulator >>> br.bitOffset);
/* 107 */     offset += val & 0xFF;
/* 108 */     int bits = table[offset] >> 16;
/* 109 */     int sym = table[offset] & 0xFFFF;
/* 110 */     if (bits <= 8) {
/* 111 */       br.bitOffset += bits;
/* 112 */       return sym;
/*     */     } 
/* 114 */     offset += sym;
/* 115 */     int mask = (1 << bits) - 1;
/* 116 */     offset += (val & mask) >>> 8;
/* 117 */     br.bitOffset += (table[offset] >> 16) + 8;
/* 118 */     return table[offset] & 0xFFFF;
/*     */   }
/*     */   
/*     */   private static int readBlockLength(int[] table, int offset, BitReader br) {
/* 122 */     BitReader.fillBitWindow(br);
/* 123 */     int code = readSymbol(table, offset, br);
/* 124 */     int n = Prefix.BLOCK_LENGTH_N_BITS[code];
/* 125 */     return Prefix.BLOCK_LENGTH_OFFSET[code] + BitReader.readBits(br, n);
/*     */   }
/*     */   
/*     */   private static int translateShortCodes(int code, int[] ringBuffer, int index) {
/* 129 */     if (code < 16) {
/* 130 */       index += DISTANCE_SHORT_CODE_INDEX_OFFSET[code];
/* 131 */       index &= 0x3;
/* 132 */       return ringBuffer[index] + DISTANCE_SHORT_CODE_VALUE_OFFSET[code];
/*     */     } 
/* 134 */     return code - 16 + 1;
/*     */   }
/*     */   
/*     */   private static void moveToFront(int[] v, int index) {
/* 138 */     int value = v[index];
/* 139 */     for (; index > 0; index--) {
/* 140 */       v[index] = v[index - 1];
/*     */     }
/* 142 */     v[0] = value;
/*     */   }
/*     */   
/*     */   private static void inverseMoveToFrontTransform(byte[] v, int vLen) {
/* 146 */     int[] mtf = new int[256]; int i;
/* 147 */     for (i = 0; i < 256; i++) {
/* 148 */       mtf[i] = i;
/*     */     }
/* 150 */     for (i = 0; i < vLen; i++) {
/* 151 */       int index = v[i] & 0xFF;
/* 152 */       v[i] = (byte)mtf[index];
/* 153 */       if (index != 0) {
/* 154 */         moveToFront(mtf, index);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void readHuffmanCodeLengths(int[] codeLengthCodeLengths, int numSymbols, int[] codeLengths, BitReader br) {
/* 161 */     int symbol = 0;
/* 162 */     int prevCodeLen = 8;
/* 163 */     int repeat = 0;
/* 164 */     int repeatCodeLen = 0;
/* 165 */     int space = 32768;
/* 166 */     int[] table = new int[32];
/*     */     
/* 168 */     Huffman.buildHuffmanTable(table, 0, 5, codeLengthCodeLengths, 18);
/*     */     
/* 170 */     while (symbol < numSymbols && space > 0) {
/* 171 */       BitReader.readMoreInput(br);
/* 172 */       BitReader.fillBitWindow(br);
/* 173 */       int p = (int)(br.accumulator >>> br.bitOffset) & 0x1F;
/* 174 */       br.bitOffset += table[p] >> 16;
/* 175 */       int codeLen = table[p] & 0xFFFF;
/* 176 */       if (codeLen < 16) {
/* 177 */         repeat = 0;
/* 178 */         codeLengths[symbol++] = codeLen;
/* 179 */         if (codeLen != 0) {
/* 180 */           prevCodeLen = codeLen;
/* 181 */           space -= 32768 >> codeLen;
/*     */         }  continue;
/*     */       } 
/* 184 */       int extraBits = codeLen - 14;
/* 185 */       int newLen = 0;
/* 186 */       if (codeLen == 16) {
/* 187 */         newLen = prevCodeLen;
/*     */       }
/* 189 */       if (repeatCodeLen != newLen) {
/* 190 */         repeat = 0;
/* 191 */         repeatCodeLen = newLen;
/*     */       } 
/* 193 */       int oldRepeat = repeat;
/* 194 */       if (repeat > 0) {
/* 195 */         repeat -= 2;
/* 196 */         repeat <<= extraBits;
/*     */       } 
/* 198 */       repeat += BitReader.readBits(br, extraBits) + 3;
/* 199 */       int repeatDelta = repeat - oldRepeat;
/* 200 */       if (symbol + repeatDelta > numSymbols) {
/* 201 */         throw new BrotliRuntimeException("symbol + repeatDelta > numSymbols");
/*     */       }
/* 203 */       for (int i = 0; i < repeatDelta; i++) {
/* 204 */         codeLengths[symbol++] = repeatCodeLen;
/*     */       }
/* 206 */       if (repeatCodeLen != 0) {
/* 207 */         space -= repeatDelta << 15 - repeatCodeLen;
/*     */       }
/*     */     } 
/*     */     
/* 211 */     if (space != 0) {
/* 212 */       throw new BrotliRuntimeException("Unused space");
/*     */     }
/*     */     
/* 215 */     Utils.fillWithZeroes(codeLengths, symbol, numSymbols - symbol);
/*     */   }
/*     */ 
/*     */   
/*     */   static void readHuffmanCode(int alphabetSize, int[] table, int offset, BitReader br) {
/* 220 */     boolean ok = true;
/*     */     
/* 222 */     BitReader.readMoreInput(br);
/*     */     
/* 224 */     int[] codeLengths = new int[alphabetSize];
/* 225 */     int simpleCodeOrSkip = BitReader.readBits(br, 2);
/* 226 */     if (simpleCodeOrSkip == 1) {
/* 227 */       int maxBitsCounter = alphabetSize - 1;
/* 228 */       int maxBits = 0;
/* 229 */       int[] symbols = new int[4];
/* 230 */       int numSymbols = BitReader.readBits(br, 2) + 1;
/* 231 */       while (maxBitsCounter != 0) {
/* 232 */         maxBitsCounter >>= 1;
/* 233 */         maxBits++;
/*     */       } 
/*     */ 
/*     */       
/* 237 */       for (int i = 0; i < numSymbols; i++) {
/* 238 */         symbols[i] = BitReader.readBits(br, maxBits) % alphabetSize;
/* 239 */         codeLengths[symbols[i]] = 2;
/*     */       } 
/* 241 */       codeLengths[symbols[0]] = 1;
/* 242 */       switch (numSymbols) {
/*     */         case 1:
/*     */           break;
/*     */         case 2:
/* 246 */           ok = (symbols[0] != symbols[1]);
/* 247 */           codeLengths[symbols[1]] = 1;
/*     */           break;
/*     */         case 3:
/* 250 */           ok = (symbols[0] != symbols[1] && symbols[0] != symbols[2] && symbols[1] != symbols[2]);
/*     */           break;
/*     */         
/*     */         default:
/* 254 */           ok = (symbols[0] != symbols[1] && symbols[0] != symbols[2] && symbols[0] != symbols[3] && symbols[1] != symbols[2] && symbols[1] != symbols[3] && symbols[2] != symbols[3]);
/*     */           
/* 256 */           if (BitReader.readBits(br, 1) == 1) {
/* 257 */             codeLengths[symbols[2]] = 3;
/* 258 */             codeLengths[symbols[3]] = 3; break;
/*     */           } 
/* 260 */           codeLengths[symbols[0]] = 2;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } else {
/* 265 */       int[] codeLengthCodeLengths = new int[18];
/* 266 */       int space = 32;
/* 267 */       int numCodes = 0;
/* 268 */       for (int i = simpleCodeOrSkip; i < 18 && space > 0; i++) {
/* 269 */         int codeLenIdx = CODE_LENGTH_CODE_ORDER[i];
/* 270 */         BitReader.fillBitWindow(br);
/* 271 */         int p = (int)(br.accumulator >>> br.bitOffset) & 0xF;
/*     */         
/* 273 */         br.bitOffset += FIXED_TABLE[p] >> 16;
/* 274 */         int v = FIXED_TABLE[p] & 0xFFFF;
/* 275 */         codeLengthCodeLengths[codeLenIdx] = v;
/* 276 */         if (v != 0) {
/* 277 */           space -= 32 >> v;
/* 278 */           numCodes++;
/*     */         } 
/*     */       } 
/* 281 */       ok = (numCodes == 1 || space == 0);
/* 282 */       readHuffmanCodeLengths(codeLengthCodeLengths, alphabetSize, codeLengths, br);
/*     */     } 
/* 284 */     if (!ok) {
/* 285 */       throw new BrotliRuntimeException("Can't readHuffmanCode");
/*     */     }
/* 287 */     Huffman.buildHuffmanTable(table, offset, 8, codeLengths, alphabetSize);
/*     */   }
/*     */   
/*     */   private static int decodeContextMap(int contextMapSize, byte[] contextMap, BitReader br) {
/* 291 */     BitReader.readMoreInput(br);
/* 292 */     int numTrees = decodeVarLenUnsignedByte(br) + 1;
/*     */     
/* 294 */     if (numTrees == 1) {
/* 295 */       Utils.fillWithZeroes(contextMap, 0, contextMapSize);
/* 296 */       return numTrees;
/*     */     } 
/*     */     
/* 299 */     boolean useRleForZeros = (BitReader.readBits(br, 1) == 1);
/* 300 */     int maxRunLengthPrefix = 0;
/* 301 */     if (useRleForZeros) {
/* 302 */       maxRunLengthPrefix = BitReader.readBits(br, 4) + 1;
/*     */     }
/* 304 */     int[] table = new int[1080];
/* 305 */     readHuffmanCode(numTrees + maxRunLengthPrefix, table, 0, br);
/* 306 */     for (int i = 0; i < contextMapSize; ) {
/* 307 */       BitReader.readMoreInput(br);
/* 308 */       BitReader.fillBitWindow(br);
/* 309 */       int code = readSymbol(table, 0, br);
/* 310 */       if (code == 0) {
/* 311 */         contextMap[i] = 0;
/* 312 */         i++; continue;
/* 313 */       }  if (code <= maxRunLengthPrefix) {
/* 314 */         int reps = (1 << code) + BitReader.readBits(br, code);
/* 315 */         while (reps != 0) {
/* 316 */           if (i >= contextMapSize) {
/* 317 */             throw new BrotliRuntimeException("Corrupted context map");
/*     */           }
/* 319 */           contextMap[i] = 0;
/* 320 */           i++;
/* 321 */           reps--;
/*     */         }  continue;
/*     */       } 
/* 324 */       contextMap[i] = (byte)(code - maxRunLengthPrefix);
/* 325 */       i++;
/*     */     } 
/*     */     
/* 328 */     if (BitReader.readBits(br, 1) == 1) {
/* 329 */       inverseMoveToFrontTransform(contextMap, contextMapSize);
/*     */     }
/* 331 */     return numTrees;
/*     */   }
/*     */   
/*     */   private static void decodeBlockTypeAndLength(State state, int treeType) {
/* 335 */     BitReader br = state.br;
/* 336 */     int[] ringBuffers = state.blockTypeRb;
/* 337 */     int offset = treeType * 2;
/* 338 */     BitReader.fillBitWindow(br);
/* 339 */     int blockType = readSymbol(state.blockTypeTrees, treeType * 1080, br);
/*     */     
/* 341 */     state.blockLength[treeType] = readBlockLength(state.blockLenTrees, treeType * 1080, br);
/*     */ 
/*     */     
/* 344 */     if (blockType == 1) {
/* 345 */       blockType = ringBuffers[offset + 1] + 1;
/* 346 */     } else if (blockType == 0) {
/* 347 */       blockType = ringBuffers[offset];
/*     */     } else {
/* 349 */       blockType -= 2;
/*     */     } 
/* 351 */     if (blockType >= state.numBlockTypes[treeType]) {
/* 352 */       blockType -= state.numBlockTypes[treeType];
/*     */     }
/* 354 */     ringBuffers[offset] = ringBuffers[offset + 1];
/* 355 */     ringBuffers[offset + 1] = blockType;
/*     */   }
/*     */   
/*     */   private static void decodeLiteralBlockSwitch(State state) {
/* 359 */     decodeBlockTypeAndLength(state, 0);
/* 360 */     int literalBlockType = state.blockTypeRb[1];
/* 361 */     state.contextMapSlice = literalBlockType << 6;
/* 362 */     state.literalTreeIndex = state.contextMap[state.contextMapSlice] & 0xFF;
/* 363 */     state.literalTree = state.hGroup0.trees[state.literalTreeIndex];
/* 364 */     int contextMode = state.contextModes[literalBlockType];
/* 365 */     state.contextLookupOffset1 = Context.LOOKUP_OFFSETS[contextMode];
/* 366 */     state.contextLookupOffset2 = Context.LOOKUP_OFFSETS[contextMode + 1];
/*     */   }
/*     */   
/*     */   private static void decodeCommandBlockSwitch(State state) {
/* 370 */     decodeBlockTypeAndLength(state, 1);
/* 371 */     state.treeCommandOffset = state.hGroup1.trees[state.blockTypeRb[3]];
/*     */   }
/*     */   
/*     */   private static void decodeDistanceBlockSwitch(State state) {
/* 375 */     decodeBlockTypeAndLength(state, 2);
/* 376 */     state.distContextMapSlice = state.blockTypeRb[5] << 2;
/*     */   }
/*     */   
/*     */   private static void maybeReallocateRingBuffer(State state) {
/* 380 */     int newSize = state.maxRingBufferSize;
/* 381 */     if (newSize > state.expectedTotalSize) {
/*     */       
/* 383 */       int minimalNewSize = (int)state.expectedTotalSize + state.customDictionary.length;
/* 384 */       while (newSize >> 1 > minimalNewSize) {
/* 385 */         newSize >>= 1;
/*     */       }
/* 387 */       if (!state.inputEnd && newSize < 16384 && state.maxRingBufferSize >= 16384) {
/* 388 */         newSize = 16384;
/*     */       }
/*     */     } 
/* 391 */     if (newSize <= state.ringBufferSize) {
/*     */       return;
/*     */     }
/* 394 */     int ringBufferSizeWithSlack = newSize + 37;
/* 395 */     byte[] newBuffer = new byte[ringBufferSizeWithSlack];
/* 396 */     if (state.ringBuffer != null) {
/* 397 */       System.arraycopy(state.ringBuffer, 0, newBuffer, 0, state.ringBufferSize);
/*     */     
/*     */     }
/* 400 */     else if (state.customDictionary.length != 0) {
/* 401 */       int length = state.customDictionary.length;
/* 402 */       int offset = 0;
/* 403 */       if (length > state.maxBackwardDistance) {
/* 404 */         offset = length - state.maxBackwardDistance;
/* 405 */         length = state.maxBackwardDistance;
/*     */       } 
/* 407 */       System.arraycopy(state.customDictionary, offset, newBuffer, 0, length);
/* 408 */       state.pos = length;
/* 409 */       state.bytesToIgnore = length;
/*     */     } 
/*     */     
/* 412 */     state.ringBuffer = newBuffer;
/* 413 */     state.ringBufferSize = newSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void readMetablockInfo(State state) {
/* 422 */     BitReader br = state.br;
/*     */     
/* 424 */     if (state.inputEnd) {
/* 425 */       state.nextRunningState = 10;
/* 426 */       state.bytesToWrite = state.pos;
/* 427 */       state.bytesWritten = 0;
/* 428 */       state.runningState = 12;
/*     */       
/*     */       return;
/*     */     } 
/* 432 */     state.hGroup0.codes = null;
/* 433 */     state.hGroup0.trees = null;
/* 434 */     state.hGroup1.codes = null;
/* 435 */     state.hGroup1.trees = null;
/* 436 */     state.hGroup2.codes = null;
/* 437 */     state.hGroup2.trees = null;
/*     */     
/* 439 */     BitReader.readMoreInput(br);
/* 440 */     decodeMetaBlockLength(br, state);
/* 441 */     if (state.metaBlockLength == 0 && !state.isMetadata) {
/*     */       return;
/*     */     }
/* 444 */     if (state.isUncompressed || state.isMetadata) {
/* 445 */       BitReader.jumpToByteBoundary(br);
/* 446 */       state.runningState = state.isMetadata ? 4 : 5;
/*     */     } else {
/* 448 */       state.runningState = 2;
/*     */     } 
/*     */     
/* 451 */     if (state.isMetadata) {
/*     */       return;
/*     */     }
/* 454 */     state.expectedTotalSize += state.metaBlockLength;
/* 455 */     if (state.ringBufferSize < state.maxRingBufferSize) {
/* 456 */       maybeReallocateRingBuffer(state);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void readMetablockHuffmanCodesAndContextMaps(State state) {
/* 461 */     BitReader br = state.br;
/*     */     
/* 463 */     for (int i = 0; i < 3; i++) {
/* 464 */       state.numBlockTypes[i] = decodeVarLenUnsignedByte(br) + 1;
/* 465 */       state.blockLength[i] = 268435456;
/* 466 */       if (state.numBlockTypes[i] > 1) {
/* 467 */         readHuffmanCode(state.numBlockTypes[i] + 2, state.blockTypeTrees, i * 1080, br);
/*     */         
/* 469 */         readHuffmanCode(26, state.blockLenTrees, i * 1080, br);
/*     */         
/* 471 */         state.blockLength[i] = readBlockLength(state.blockLenTrees, i * 1080, br);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 476 */     BitReader.readMoreInput(br);
/* 477 */     state.distancePostfixBits = BitReader.readBits(br, 2);
/* 478 */     state
/* 479 */       .numDirectDistanceCodes = 16 + (BitReader.readBits(br, 4) << state.distancePostfixBits);
/* 480 */     state.distancePostfixMask = (1 << state.distancePostfixBits) - 1;
/* 481 */     int numDistanceCodes = state.numDirectDistanceCodes + (48 << state.distancePostfixBits);
/*     */     
/* 483 */     state.contextModes = new byte[state.numBlockTypes[0]];
/* 484 */     for (int k = 0; k < state.numBlockTypes[0]; ) {
/*     */       
/* 486 */       int limit = Math.min(k + 96, state.numBlockTypes[0]);
/* 487 */       for (; k < limit; k++) {
/* 488 */         state.contextModes[k] = (byte)(BitReader.readBits(br, 2) << 1);
/*     */       }
/* 490 */       BitReader.readMoreInput(br);
/*     */     } 
/*     */ 
/*     */     
/* 494 */     state.contextMap = new byte[state.numBlockTypes[0] << 6];
/* 495 */     int numLiteralTrees = decodeContextMap(state.numBlockTypes[0] << 6, state.contextMap, br);
/*     */     
/* 497 */     state.trivialLiteralContext = true;
/* 498 */     for (int j = 0; j < state.numBlockTypes[0] << 6; j++) {
/* 499 */       if (state.contextMap[j] != j >> 6) {
/* 500 */         state.trivialLiteralContext = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 506 */     state.distContextMap = new byte[state.numBlockTypes[2] << 2];
/* 507 */     int numDistTrees = decodeContextMap(state.numBlockTypes[2] << 2, state.distContextMap, br);
/*     */ 
/*     */     
/* 510 */     HuffmanTreeGroup.init(state.hGroup0, 256, numLiteralTrees);
/* 511 */     HuffmanTreeGroup.init(state.hGroup1, 704, state.numBlockTypes[1]);
/* 512 */     HuffmanTreeGroup.init(state.hGroup2, numDistanceCodes, numDistTrees);
/*     */     
/* 514 */     HuffmanTreeGroup.decode(state.hGroup0, br);
/* 515 */     HuffmanTreeGroup.decode(state.hGroup1, br);
/* 516 */     HuffmanTreeGroup.decode(state.hGroup2, br);
/*     */     
/* 518 */     state.contextMapSlice = 0;
/* 519 */     state.distContextMapSlice = 0;
/* 520 */     state.contextLookupOffset1 = Context.LOOKUP_OFFSETS[state.contextModes[0]];
/* 521 */     state.contextLookupOffset2 = Context.LOOKUP_OFFSETS[state.contextModes[0] + 1];
/* 522 */     state.literalTreeIndex = 0;
/* 523 */     state.literalTree = state.hGroup0.trees[0];
/* 524 */     state.treeCommandOffset = state.hGroup1.trees[0];
/*     */     
/* 526 */     state.blockTypeRb[4] = 1; state.blockTypeRb[2] = 1; state.blockTypeRb[0] = 1;
/* 527 */     state.blockTypeRb[5] = 0; state.blockTypeRb[3] = 0; state.blockTypeRb[1] = 0;
/*     */   }
/*     */   
/*     */   private static void copyUncompressedData(State state) {
/* 531 */     BitReader br = state.br;
/* 532 */     byte[] ringBuffer = state.ringBuffer;
/*     */ 
/*     */     
/* 535 */     if (state.metaBlockLength <= 0) {
/* 536 */       BitReader.reload(br);
/* 537 */       state.runningState = 1;
/*     */       
/*     */       return;
/*     */     } 
/* 541 */     int chunkLength = Math.min(state.ringBufferSize - state.pos, state.metaBlockLength);
/* 542 */     BitReader.copyBytes(br, ringBuffer, state.pos, chunkLength);
/* 543 */     state.metaBlockLength -= chunkLength;
/* 544 */     state.pos += chunkLength;
/* 545 */     if (state.pos == state.ringBufferSize) {
/* 546 */       state.nextRunningState = 5;
/* 547 */       state.bytesToWrite = state.ringBufferSize;
/* 548 */       state.bytesWritten = 0;
/* 549 */       state.runningState = 12;
/*     */       
/*     */       return;
/*     */     } 
/* 553 */     BitReader.reload(br);
/* 554 */     state.runningState = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean writeRingBuffer(State state) {
/* 559 */     if (state.bytesToIgnore != 0) {
/* 560 */       state.bytesWritten += state.bytesToIgnore;
/* 561 */       state.bytesToIgnore = 0;
/*     */     } 
/* 563 */     int toWrite = Math.min(state.outputLength - state.outputUsed, state.bytesToWrite - state.bytesWritten);
/*     */     
/* 565 */     if (toWrite != 0) {
/* 566 */       System.arraycopy(state.ringBuffer, state.bytesWritten, state.output, state.outputOffset + state.outputUsed, toWrite);
/*     */       
/* 568 */       state.outputUsed += toWrite;
/* 569 */       state.bytesWritten += toWrite;
/*     */     } 
/*     */     
/* 572 */     return (state.outputUsed < state.outputLength);
/*     */   }
/*     */   
/*     */   static void setCustomDictionary(State state, byte[] data) {
/* 576 */     state.customDictionary = (data == null) ? new byte[0] : data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void decompress(State state) {
/* 583 */     if (state.runningState == 0) {
/* 584 */       throw new IllegalStateException("Can't decompress until initialized");
/*     */     }
/* 586 */     if (state.runningState == 11) {
/* 587 */       throw new IllegalStateException("Can't decompress after close");
/*     */     }
/* 589 */     BitReader br = state.br;
/* 590 */     int ringBufferMask = state.ringBufferSize - 1;
/* 591 */     byte[] ringBuffer = state.ringBuffer;
/*     */     
/* 593 */     while (state.runningState != 10) {
/*     */       int cmdCode; int rangeIdx; int insertCode; int copyCode; int src; int dst; int copyLength;
/* 595 */       switch (state.runningState) {
/*     */         case 1:
/* 597 */           if (state.metaBlockLength < 0) {
/* 598 */             throw new BrotliRuntimeException("Invalid metablock length");
/*     */           }
/* 600 */           readMetablockInfo(state);
/*     */           
/* 602 */           ringBufferMask = state.ringBufferSize - 1;
/* 603 */           ringBuffer = state.ringBuffer;
/*     */           continue;
/*     */         
/*     */         case 2:
/* 607 */           readMetablockHuffmanCodesAndContextMaps(state);
/* 608 */           state.runningState = 3;
/*     */ 
/*     */         
/*     */         case 3:
/* 612 */           if (state.metaBlockLength <= 0) {
/* 613 */             state.runningState = 1;
/*     */             continue;
/*     */           } 
/* 616 */           BitReader.readMoreInput(br);
/* 617 */           if (state.blockLength[1] == 0) {
/* 618 */             decodeCommandBlockSwitch(state);
/*     */           }
/* 620 */           state.blockLength[1] = state.blockLength[1] - 1;
/* 621 */           BitReader.fillBitWindow(br);
/* 622 */           cmdCode = readSymbol(state.hGroup1.codes, state.treeCommandOffset, br);
/* 623 */           rangeIdx = cmdCode >>> 6;
/* 624 */           state.distanceCode = 0;
/* 625 */           if (rangeIdx >= 2) {
/* 626 */             rangeIdx -= 2;
/* 627 */             state.distanceCode = -1;
/*     */           } 
/* 629 */           insertCode = Prefix.INSERT_RANGE_LUT[rangeIdx] + (cmdCode >>> 3 & 0x7);
/* 630 */           copyCode = Prefix.COPY_RANGE_LUT[rangeIdx] + (cmdCode & 0x7);
/* 631 */           state
/* 632 */             .insertLength = Prefix.INSERT_LENGTH_OFFSET[insertCode] + BitReader.readBits(br, Prefix.INSERT_LENGTH_N_BITS[insertCode]);
/* 633 */           state
/* 634 */             .copyLength = Prefix.COPY_LENGTH_OFFSET[copyCode] + BitReader.readBits(br, Prefix.COPY_LENGTH_N_BITS[copyCode]);
/*     */           
/* 636 */           state.j = 0;
/* 637 */           state.runningState = 6;
/*     */ 
/*     */         
/*     */         case 6:
/* 641 */           if (state.trivialLiteralContext) {
/* 642 */             while (state.j < state.insertLength) {
/* 643 */               BitReader.readMoreInput(br);
/* 644 */               if (state.blockLength[0] == 0) {
/* 645 */                 decodeLiteralBlockSwitch(state);
/*     */               }
/* 647 */               state.blockLength[0] = state.blockLength[0] - 1;
/* 648 */               BitReader.fillBitWindow(br);
/* 649 */               ringBuffer[state.pos] = 
/* 650 */                 (byte)readSymbol(state.hGroup0.codes, state.literalTree, br);
/* 651 */               state.j++;
/* 652 */               if (state.pos++ == ringBufferMask) {
/* 653 */                 state.nextRunningState = 6;
/* 654 */                 state.bytesToWrite = state.ringBufferSize;
/* 655 */                 state.bytesWritten = 0;
/* 656 */                 state.runningState = 12;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } else {
/* 661 */             int prevByte1 = ringBuffer[state.pos - 1 & ringBufferMask] & 0xFF;
/* 662 */             int prevByte2 = ringBuffer[state.pos - 2 & ringBufferMask] & 0xFF;
/* 663 */             while (state.j < state.insertLength) {
/* 664 */               BitReader.readMoreInput(br);
/* 665 */               if (state.blockLength[0] == 0) {
/* 666 */                 decodeLiteralBlockSwitch(state);
/*     */               }
/* 668 */               int literalTreeIndex = state.contextMap[state.contextMapSlice + (Context.LOOKUP[state.contextLookupOffset1 + prevByte1] | Context.LOOKUP[state.contextLookupOffset2 + prevByte2])] & 0xFF;
/*     */ 
/*     */               
/* 671 */               state.blockLength[0] = state.blockLength[0] - 1;
/* 672 */               prevByte2 = prevByte1;
/* 673 */               BitReader.fillBitWindow(br);
/* 674 */               prevByte1 = readSymbol(state.hGroup0.codes, state.hGroup0.trees[literalTreeIndex], br);
/*     */               
/* 676 */               ringBuffer[state.pos] = (byte)prevByte1;
/* 677 */               state.j++;
/* 678 */               if (state.pos++ == ringBufferMask) {
/* 679 */                 state.nextRunningState = 6;
/* 680 */                 state.bytesToWrite = state.ringBufferSize;
/* 681 */                 state.bytesWritten = 0;
/* 682 */                 state.runningState = 12;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/* 687 */           if (state.runningState != 6) {
/*     */             continue;
/*     */           }
/* 690 */           state.metaBlockLength -= state.insertLength;
/* 691 */           if (state.metaBlockLength <= 0) {
/* 692 */             state.runningState = 3;
/*     */             continue;
/*     */           } 
/* 695 */           if (state.distanceCode < 0) {
/* 696 */             BitReader.readMoreInput(br);
/* 697 */             if (state.blockLength[2] == 0) {
/* 698 */               decodeDistanceBlockSwitch(state);
/*     */             }
/* 700 */             state.blockLength[2] = state.blockLength[2] - 1;
/* 701 */             BitReader.fillBitWindow(br);
/* 702 */             state.distanceCode = readSymbol(state.hGroup2.codes, state.hGroup2.trees[state.distContextMap[state.distContextMapSlice + ((state.copyLength > 4) ? 3 : (state.copyLength - 2))] & 0xFF], br);
/*     */ 
/*     */             
/* 705 */             if (state.distanceCode >= state.numDirectDistanceCodes) {
/* 706 */               state.distanceCode -= state.numDirectDistanceCodes;
/* 707 */               int postfix = state.distanceCode & state.distancePostfixMask;
/* 708 */               state.distanceCode >>>= state.distancePostfixBits;
/* 709 */               int n = (state.distanceCode >>> 1) + 1;
/* 710 */               int offset = (2 + (state.distanceCode & 0x1) << n) - 4;
/* 711 */               state
/* 712 */                 .distanceCode = state.numDirectDistanceCodes + postfix + (offset + BitReader.readBits(br, n) << state.distancePostfixBits);
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 718 */           state.distance = translateShortCodes(state.distanceCode, state.distRb, state.distRbIdx);
/* 719 */           if (state.distance < 0) {
/* 720 */             throw new BrotliRuntimeException("Negative distance");
/*     */           }
/*     */           
/* 723 */           if (state.maxDistance != state.maxBackwardDistance && state.pos < state.maxBackwardDistance) {
/*     */             
/* 725 */             state.maxDistance = state.pos;
/*     */           } else {
/* 727 */             state.maxDistance = state.maxBackwardDistance;
/*     */           } 
/*     */           
/* 730 */           state.copyDst = state.pos;
/* 731 */           if (state.distance > state.maxDistance) {
/* 732 */             state.runningState = 9;
/*     */             
/*     */             continue;
/*     */           } 
/* 736 */           if (state.distanceCode > 0) {
/* 737 */             state.distRb[state.distRbIdx & 0x3] = state.distance;
/* 738 */             state.distRbIdx++;
/*     */           } 
/*     */           
/* 741 */           if (state.copyLength > state.metaBlockLength) {
/* 742 */             throw new BrotliRuntimeException("Invalid backward reference");
/*     */           }
/* 744 */           state.j = 0;
/* 745 */           state.runningState = 7;
/*     */         
/*     */         case 7:
/* 748 */           src = state.pos - state.distance & ringBufferMask;
/* 749 */           dst = state.pos;
/* 750 */           copyLength = state.copyLength - state.j;
/* 751 */           if (src + copyLength < ringBufferMask && dst + copyLength < ringBufferMask) {
/* 752 */             for (int k = 0; k < copyLength; k++) {
/* 753 */               ringBuffer[dst++] = ringBuffer[src++];
/*     */             }
/* 755 */             state.j += copyLength;
/* 756 */             state.metaBlockLength -= copyLength;
/* 757 */             state.pos += copyLength;
/*     */           } else {
/* 759 */             while (state.j < state.copyLength) {
/* 760 */               ringBuffer[state.pos] = ringBuffer[state.pos - state.distance & ringBufferMask];
/*     */               
/* 762 */               state.metaBlockLength--;
/* 763 */               state.j++;
/* 764 */               if (state.pos++ == ringBufferMask) {
/* 765 */                 state.nextRunningState = 7;
/* 766 */                 state.bytesToWrite = state.ringBufferSize;
/* 767 */                 state.bytesWritten = 0;
/* 768 */                 state.runningState = 12;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/* 773 */           if (state.runningState == 7) {
/* 774 */             state.runningState = 3;
/*     */           }
/*     */           continue;
/*     */         
/*     */         case 9:
/* 779 */           if (state.copyLength >= 4 && state.copyLength <= 24) {
/*     */             
/* 781 */             int offset = Dictionary.OFFSETS_BY_LENGTH[state.copyLength];
/* 782 */             int wordId = state.distance - state.maxDistance - 1;
/* 783 */             int shift = Dictionary.SIZE_BITS_BY_LENGTH[state.copyLength];
/* 784 */             int mask = (1 << shift) - 1;
/* 785 */             int wordIdx = wordId & mask;
/* 786 */             int transformIdx = wordId >>> shift;
/* 787 */             offset += wordIdx * state.copyLength;
/* 788 */             if (transformIdx < Transform.TRANSFORMS.length) {
/* 789 */               int len = Transform.transformDictionaryWord(ringBuffer, state.copyDst, 
/* 790 */                   Dictionary.getData(), offset, state.copyLength, Transform.TRANSFORMS[transformIdx]);
/*     */               
/* 792 */               state.copyDst += len;
/* 793 */               state.pos += len;
/* 794 */               state.metaBlockLength -= len;
/* 795 */               if (state.copyDst >= state.ringBufferSize) {
/* 796 */                 state.nextRunningState = 8;
/* 797 */                 state.bytesToWrite = state.ringBufferSize;
/* 798 */                 state.bytesWritten = 0;
/* 799 */                 state.runningState = 12;
/*     */                 continue;
/*     */               } 
/*     */             } else {
/* 803 */               throw new BrotliRuntimeException("Invalid backward reference");
/*     */             } 
/*     */           } else {
/* 806 */             throw new BrotliRuntimeException("Invalid backward reference");
/*     */           } 
/* 808 */           state.runningState = 3;
/*     */           continue;
/*     */         
/*     */         case 8:
/* 812 */           System.arraycopy(ringBuffer, state.ringBufferSize, ringBuffer, 0, state.copyDst - state.ringBufferSize);
/*     */           
/* 814 */           state.runningState = 3;
/*     */           continue;
/*     */         
/*     */         case 4:
/* 818 */           while (state.metaBlockLength > 0) {
/* 819 */             BitReader.readMoreInput(br);
/*     */             
/* 821 */             BitReader.readBits(br, 8);
/* 822 */             state.metaBlockLength--;
/*     */           } 
/* 824 */           state.runningState = 1;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 5:
/* 829 */           copyUncompressedData(state);
/*     */           continue;
/*     */         
/*     */         case 12:
/* 833 */           if (!writeRingBuffer(state)) {
/*     */             return;
/*     */           }
/*     */           
/* 837 */           if (state.pos >= state.maxBackwardDistance) {
/* 838 */             state.maxDistance = state.maxBackwardDistance;
/*     */           }
/* 840 */           state.pos &= ringBufferMask;
/* 841 */           state.runningState = state.nextRunningState;
/*     */           continue;
/*     */       } 
/*     */       
/* 845 */       throw new BrotliRuntimeException("Unexpected state " + state.runningState);
/*     */     } 
/*     */     
/* 848 */     if (state.runningState == 10) {
/* 849 */       if (state.metaBlockLength < 0) {
/* 850 */         throw new BrotliRuntimeException("Invalid metablock length");
/*     */       }
/* 852 */       BitReader.jumpToByteBoundary(br);
/* 853 */       BitReader.checkHealth(state.br, true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/brotli/dec/Decode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */