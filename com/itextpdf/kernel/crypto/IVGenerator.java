/*    */ package com.itextpdf.kernel.crypto;
/*    */ 
/*    */ import com.itextpdf.io.util.SystemUtil;
/*    */ import java.nio.charset.StandardCharsets;
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
/*    */ public final class IVGenerator
/*    */ {
/* 60 */   private static final ARCFOUREncryption arcfour = new ARCFOUREncryption(); static {
/* 61 */     long time = SystemUtil.getTimeBasedSeed();
/* 62 */     long mem = SystemUtil.getFreeMemory();
/* 63 */     String s = time + "+" + mem;
/* 64 */     arcfour.prepareARCFOURKey(s.getBytes(StandardCharsets.ISO_8859_1));
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
/*    */   
/*    */   public static byte[] getIV() {
/* 79 */     return getIV(16);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] getIV(int len) {
/* 89 */     byte[] b = new byte[len];
/* 90 */     synchronized (arcfour) {
/* 91 */       arcfour.encryptARCFOUR(b);
/*    */     } 
/* 93 */     return b;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/IVGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */