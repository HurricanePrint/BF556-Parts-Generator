/*    */ package com.itextpdf.io.font.woff2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TableTags
/*    */ {
/*    */   public static final int kGlyfTableTag = 1735162214;
/*    */   public static final int kHeadTableTag = 1751474532;
/*    */   public static final int kLocaTableTag = 1819239265;
/*    */   public static final int kDsigTableTag = 1146308935;
/*    */   public static final int kCffTableTag = 1128678944;
/*    */   public static final int kHmtxTableTag = 1752003704;
/*    */   public static final int kHheaTableTag = 1751672161;
/*    */   public static final int kMaxpTableTag = 1835104368;
/*    */   
/*    */   private static int tag(char a, char b, char c, char d) {
/* 22 */     return a << 24 | b << 16 | c << 8 | d;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public static int[] kKnownTags = new int[] { 
/* 36 */       tag('c', 'm', 'a', 'p'), 
/* 37 */       tag('h', 'e', 'a', 'd'), 
/* 38 */       tag('h', 'h', 'e', 'a'), 
/* 39 */       tag('h', 'm', 't', 'x'), 
/* 40 */       tag('m', 'a', 'x', 'p'), 
/* 41 */       tag('n', 'a', 'm', 'e'), 
/* 42 */       tag('O', 'S', '/', '2'), 
/* 43 */       tag('p', 'o', 's', 't'), 
/* 44 */       tag('c', 'v', 't', ' '), 
/* 45 */       tag('f', 'p', 'g', 'm'), 
/* 46 */       tag('g', 'l', 'y', 'f'), 
/* 47 */       tag('l', 'o', 'c', 'a'), 
/* 48 */       tag('p', 'r', 'e', 'p'), 
/* 49 */       tag('C', 'F', 'F', ' '), 
/* 50 */       tag('V', 'O', 'R', 'G'), 
/* 51 */       tag('E', 'B', 'D', 'T'), 
/* 52 */       tag('E', 'B', 'L', 'C'), 
/* 53 */       tag('g', 'a', 's', 'p'), 
/* 54 */       tag('h', 'd', 'm', 'x'), 
/* 55 */       tag('k', 'e', 'r', 'n'), 
/* 56 */       tag('L', 'T', 'S', 'H'), 
/* 57 */       tag('P', 'C', 'L', 'T'), 
/* 58 */       tag('V', 'D', 'M', 'X'), 
/* 59 */       tag('v', 'h', 'e', 'a'), 
/* 60 */       tag('v', 'm', 't', 'x'), 
/* 61 */       tag('B', 'A', 'S', 'E'), 
/* 62 */       tag('G', 'D', 'E', 'F'), 
/* 63 */       tag('G', 'P', 'O', 'S'), 
/* 64 */       tag('G', 'S', 'U', 'B'), 
/* 65 */       tag('E', 'B', 'S', 'C'), 
/* 66 */       tag('J', 'S', 'T', 'F'), 
/* 67 */       tag('M', 'A', 'T', 'H'), 
/* 68 */       tag('C', 'B', 'D', 'T'), 
/* 69 */       tag('C', 'B', 'L', 'C'), 
/* 70 */       tag('C', 'O', 'L', 'R'), 
/* 71 */       tag('C', 'P', 'A', 'L'), 
/* 72 */       tag('S', 'V', 'G', ' '), 
/* 73 */       tag('s', 'b', 'i', 'x'), 
/* 74 */       tag('a', 'c', 'n', 't'), 
/* 75 */       tag('a', 'v', 'a', 'r'), 
/* 76 */       tag('b', 'd', 'a', 't'), 
/* 77 */       tag('b', 'l', 'o', 'c'), 
/* 78 */       tag('b', 's', 'l', 'n'), 
/* 79 */       tag('c', 'v', 'a', 'r'), 
/* 80 */       tag('f', 'd', 's', 'c'), 
/* 81 */       tag('f', 'e', 'a', 't'), 
/* 82 */       tag('f', 'm', 't', 'x'), 
/* 83 */       tag('f', 'v', 'a', 'r'), 
/* 84 */       tag('g', 'v', 'a', 'r'), 
/* 85 */       tag('h', 's', 't', 'y'), 
/* 86 */       tag('j', 'u', 's', 't'), 
/* 87 */       tag('l', 'c', 'a', 'r'), 
/* 88 */       tag('m', 'o', 'r', 't'), 
/* 89 */       tag('m', 'o', 'r', 'x'), 
/* 90 */       tag('o', 'p', 'b', 'd'), 
/* 91 */       tag('p', 'r', 'o', 'p'), 
/* 92 */       tag('t', 'r', 'a', 'k'), 
/* 93 */       tag('Z', 'a', 'p', 'f'), 
/* 94 */       tag('S', 'i', 'l', 'f'), 
/* 95 */       tag('G', 'l', 'a', 't'), 
/* 96 */       tag('G', 'l', 'o', 'c'), 
/* 97 */       tag('F', 'e', 'a', 't'), 
/* 98 */       tag('S', 'i', 'l', 'l') };
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/TableTags.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */