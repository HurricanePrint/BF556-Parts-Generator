/*    */ package com.itextpdf.io.font.woff2;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Round
/*    */ {
/*    */   public static int round4(int value) {
/* 24 */     if (Integer.MAX_VALUE - value < 3) {
/* 25 */       return value;
/*    */     }
/* 27 */     return value + 3 & 0xFFFFFFFC;
/*    */   }
/*    */   
/*    */   public static long round4(long value) {
/* 31 */     if (Long.MAX_VALUE - value < 3L) {
/* 32 */       return value;
/*    */     }
/* 34 */     return value + 3L & 0xFFFFFFFFFFFFFFFCL;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/Round.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */