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
/*    */ public class Woff2Converter
/*    */ {
/*    */   public static boolean isWoff2Font(byte[] woff2Bytes) {
/* 48 */     if (woff2Bytes.length < 4) {
/* 49 */       return false;
/*    */     }
/* 51 */     Buffer file = new Buffer(woff2Bytes, 0, 4);
/*    */     try {
/* 53 */       return (file.readInt() == 2001684018);
/* 54 */     } catch (Exception any) {
/* 55 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static byte[] convert(byte[] woff2Bytes) {
/* 60 */     byte[] inner_byte_buffer = new byte[Woff2Dec.computeWoff2FinalSize(woff2Bytes, woff2Bytes.length)];
/* 61 */     Woff2Out out = new Woff2MemoryOut(inner_byte_buffer, inner_byte_buffer.length);
/* 62 */     Woff2Dec.convertWoff2ToTtf(woff2Bytes, woff2Bytes.length, out);
/* 63 */     return inner_byte_buffer;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/Woff2Converter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */