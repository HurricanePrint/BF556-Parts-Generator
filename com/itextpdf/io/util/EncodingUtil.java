/*    */ package com.itextpdf.io.util;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.CharBuffer;
/*    */ import java.nio.charset.CharacterCodingException;
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.CharsetEncoder;
/*    */ import java.nio.charset.CodingErrorAction;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EncodingUtil
/*    */ {
/*    */   public static byte[] convertToBytes(char[] chars, String encoding) throws CharacterCodingException {
/* 66 */     Charset cc = Charset.forName(encoding);
/* 67 */     CharsetEncoder ce = cc.newEncoder();
/* 68 */     ce.onUnmappableCharacter(CodingErrorAction.IGNORE);
/* 69 */     ByteBuffer bb = ce.encode(CharBuffer.wrap(chars));
/* 70 */     bb.rewind();
/* 71 */     int lim = bb.limit();
/* 72 */     int offset = "UTF-8".equals(encoding) ? 3 : 0;
/* 73 */     byte[] br = new byte[lim + offset];
/* 74 */     if ("UTF-8".equals(encoding)) {
/* 75 */       br[0] = -17;
/* 76 */       br[1] = -69;
/* 77 */       br[2] = -65;
/*    */     } 
/* 79 */     bb.get(br, offset, lim);
/* 80 */     return br;
/*    */   }
/*    */   
/*    */   public static String convertToString(byte[] bytes, String encoding) throws UnsupportedEncodingException {
/* 84 */     if (encoding.equals("UTF-8") && bytes[0] == -17 && bytes[1] == -69 && bytes[2] == -65)
/*    */     {
/* 86 */       return new String(bytes, 3, bytes.length - 3, "UTF-8"); } 
/* 87 */     return new String(bytes, encoding);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/EncodingUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */