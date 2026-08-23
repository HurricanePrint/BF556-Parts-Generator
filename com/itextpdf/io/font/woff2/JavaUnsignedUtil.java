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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class JavaUnsignedUtil
/*    */ {
/*    */   public static int asU16(short number) {
/* 50 */     return number & 0xFFFF;
/*    */   }
/*    */   
/*    */   public static int asU8(byte number) {
/* 54 */     return number & 0xFF;
/*    */   }
/*    */   
/*    */   public static byte toU8(int number) {
/* 58 */     return (byte)(number & 0xFF);
/*    */   }
/*    */   
/*    */   public static short toU16(int number) {
/* 62 */     return (short)(number & 0xFFFF);
/*    */   }
/*    */   
/*    */   public static int compareAsUnsigned(int left, int right) {
/* 66 */     return Long.valueOf(left & 0xFFFFFFFFL).compareTo(Long.valueOf(right & 0xFFFFFFFFL));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/JavaUnsignedUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */