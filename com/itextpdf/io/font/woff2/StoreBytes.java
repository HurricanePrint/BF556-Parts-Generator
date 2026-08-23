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
/*    */ class StoreBytes
/*    */ {
/*    */   public static int storeU32(byte[] dst, int offset, int x) {
/* 25 */     dst[offset] = JavaUnsignedUtil.toU8(x >> 24);
/* 26 */     dst[offset + 1] = JavaUnsignedUtil.toU8(x >> 16);
/* 27 */     dst[offset + 2] = JavaUnsignedUtil.toU8(x >> 8);
/* 28 */     dst[offset + 3] = JavaUnsignedUtil.toU8(x);
/* 29 */     return offset + 4;
/*    */   }
/*    */   
/*    */   public static int storeU16(byte[] dst, int offset, int x) {
/* 33 */     dst[offset] = JavaUnsignedUtil.toU8(x >> 8);
/* 34 */     dst[offset + 1] = JavaUnsignedUtil.toU8(x);
/* 35 */     return offset + 2;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/StoreBytes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */