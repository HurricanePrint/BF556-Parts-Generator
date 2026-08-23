/*    */ package com.itextpdf.io.util;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.DecimalFormatSymbols;
/*    */ import java.util.Locale;
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
/*    */ public final class DecimalFormatUtil
/*    */ {
/* 56 */   private static final DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String formatNumber(double d, String pattern) {
/* 62 */     DecimalFormat dn = new DecimalFormat(pattern, dfs);
/* 63 */     return dn.format(d);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/DecimalFormatUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */