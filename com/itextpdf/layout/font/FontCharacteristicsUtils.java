/*    */ package com.itextpdf.layout.font;
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
/*    */ final class FontCharacteristicsUtils
/*    */ {
/*    */   static short normalizeFontWeight(short fw) {
/* 50 */     fw = (short)(fw / 100 * 100);
/* 51 */     if (fw < 100) return 100; 
/* 52 */     if (fw > 900) return 900; 
/* 53 */     return fw;
/*    */   }
/*    */   
/*    */   static short parseFontWeight(String fw) {
/* 57 */     if (fw == null || fw.length() == 0) {
/* 58 */       return -1;
/*    */     }
/* 60 */     fw = fw.trim().toLowerCase();
/* 61 */     switch (fw) {
/*    */       case "bold":
/* 63 */         return 700;
/*    */       case "normal":
/* 65 */         return 400;
/*    */     } 
/*    */     try {
/* 68 */       return normalizeFontWeight((short)Integer.parseInt(fw));
/* 69 */     } catch (NumberFormatException ignored) {
/* 70 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontCharacteristicsUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */