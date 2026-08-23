/*     */ package com.itextpdf.io.codec.brotli.dec;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class State
/*     */ {
/*  13 */   int runningState = 0;
/*     */   int nextRunningState;
/*  15 */   final BitReader br = new BitReader();
/*     */   byte[] ringBuffer;
/*  17 */   final int[] blockTypeTrees = new int[3240];
/*  18 */   final int[] blockLenTrees = new int[3240];
/*     */   
/*     */   int metaBlockLength;
/*     */   
/*     */   boolean inputEnd;
/*     */   
/*     */   boolean isUncompressed;
/*     */   boolean isMetadata;
/*  26 */   final HuffmanTreeGroup hGroup0 = new HuffmanTreeGroup();
/*  27 */   final HuffmanTreeGroup hGroup1 = new HuffmanTreeGroup();
/*  28 */   final HuffmanTreeGroup hGroup2 = new HuffmanTreeGroup();
/*  29 */   final int[] blockLength = new int[3];
/*  30 */   final int[] numBlockTypes = new int[3];
/*  31 */   final int[] blockTypeRb = new int[6];
/*  32 */   final int[] distRb = new int[] { 16, 15, 11, 4 };
/*  33 */   int pos = 0;
/*  34 */   int maxDistance = 0;
/*  35 */   int distRbIdx = 0;
/*     */   boolean trivialLiteralContext = false;
/*  37 */   int literalTreeIndex = 0;
/*     */   int literalTree;
/*     */   int j;
/*     */   int insertLength;
/*     */   byte[] contextModes;
/*     */   byte[] contextMap;
/*     */   int contextMapSlice;
/*     */   int distContextMapSlice;
/*     */   int contextLookupOffset1;
/*     */   int contextLookupOffset2;
/*     */   int treeCommandOffset;
/*     */   int distanceCode;
/*     */   byte[] distContextMap;
/*     */   int numDirectDistanceCodes;
/*     */   int distancePostfixMask;
/*     */   int distancePostfixBits;
/*     */   int distance;
/*     */   int copyLength;
/*     */   int copyDst;
/*     */   int maxBackwardDistance;
/*     */   int maxRingBufferSize;
/*  58 */   int ringBufferSize = 0;
/*  59 */   long expectedTotalSize = 0L;
/*  60 */   byte[] customDictionary = new byte[0];
/*  61 */   int bytesToIgnore = 0;
/*     */   
/*     */   int outputOffset;
/*     */   
/*     */   int outputLength;
/*     */   int outputUsed;
/*     */   int bytesWritten;
/*     */   int bytesToWrite;
/*     */   byte[] output;
/*     */   
/*     */   private static int decodeWindowBits(BitReader br) {
/*  72 */     if (BitReader.readBits(br, 1) == 0) {
/*  73 */       return 16;
/*     */     }
/*  75 */     int n = BitReader.readBits(br, 3);
/*  76 */     if (n != 0) {
/*  77 */       return 17 + n;
/*     */     }
/*  79 */     n = BitReader.readBits(br, 3);
/*  80 */     if (n != 0) {
/*  81 */       return 8 + n;
/*     */     }
/*  83 */     return 17;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setInput(State state, InputStream input) {
/*  93 */     if (state.runningState != 0) {
/*  94 */       throw new IllegalStateException("State MUST be uninitialized");
/*     */     }
/*  96 */     BitReader.init(state.br, input);
/*  97 */     int windowBits = decodeWindowBits(state.br);
/*  98 */     if (windowBits == 9) {
/*  99 */       throw new BrotliRuntimeException("Invalid 'windowBits' code");
/*     */     }
/* 101 */     state.maxRingBufferSize = 1 << windowBits;
/* 102 */     state.maxBackwardDistance = state.maxRingBufferSize - 16;
/* 103 */     state.runningState = 1;
/*     */   }
/*     */   
/*     */   static void close(State state) throws IOException {
/* 107 */     if (state.runningState == 0) {
/* 108 */       throw new IllegalStateException("State MUST be initialized");
/*     */     }
/* 110 */     if (state.runningState == 11) {
/*     */       return;
/*     */     }
/* 113 */     state.runningState = 11;
/* 114 */     BitReader.close(state.br);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/brotli/dec/State.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */