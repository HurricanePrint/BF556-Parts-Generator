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
/*    */ class VariableLength
/*    */ {
/*    */   public static int read255UShort(Buffer buf) {
/* 26 */     int kWordCode = 253;
/* 27 */     int kOneMoreByteCode2 = 254;
/* 28 */     int kOneMoreByteCode1 = 255;
/* 29 */     int kLowestUCode = 253;
/* 30 */     byte code = 0;
/* 31 */     code = buf.readByte();
/* 32 */     if (JavaUnsignedUtil.asU8(code) == 253) {
/* 33 */       short result = buf.readShort();
/* 34 */       return JavaUnsignedUtil.asU16(result);
/* 35 */     }  if (JavaUnsignedUtil.asU8(code) == 255) {
/* 36 */       byte result = buf.readByte();
/* 37 */       return JavaUnsignedUtil.asU8(result) + 253;
/* 38 */     }  if (JavaUnsignedUtil.asU8(code) == 254) {
/* 39 */       byte result = buf.readByte();
/* 40 */       return JavaUnsignedUtil.asU8(result) + 506;
/*    */     } 
/* 42 */     return JavaUnsignedUtil.asU8(code);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int readBase128(Buffer buf) {
/* 47 */     int result = 0;
/* 48 */     for (int i = 0; i < 5; i++) {
/* 49 */       byte code = 0;
/* 50 */       code = buf.readByte();
/*    */       
/* 52 */       if (i == 0 && JavaUnsignedUtil.asU8(code) == 128) {
/* 53 */         throw new FontCompressionException("Reading woff2 base 128 number exception");
/*    */       }
/*    */       
/* 56 */       if ((result & 0xFE000000) != 0) {
/* 57 */         throw new FontCompressionException("Reading woff2 base 128 number exception");
/*    */       }
/* 59 */       result = result << 7 | code & Byte.MAX_VALUE;
/* 60 */       if ((code & 0x80) == 0) {
/* 61 */         return result;
/*    */       }
/*    */     } 
/*    */     
/* 65 */     throw new FontCompressionException("Reading woff2 base 128 number exception");
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/VariableLength.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */