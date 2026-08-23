/*    */ package com.itextpdf.io.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NumberUtil
/*    */ {
/*    */   public static Float asFloat(Object obj) {
/* 56 */     Number value = (Number)obj;
/* 57 */     return (value != null) ? Float.valueOf(value.floatValue()) : null;
/*    */   }
/*    */   
/*    */   public static Integer asInteger(Object obj) {
/* 61 */     Number value = (Number)obj;
/* 62 */     return (value != null) ? Integer.valueOf(value.intValue()) : null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/NumberUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */