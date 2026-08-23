/*    */ package com.itextpdf.svg.renderers.path.impl;
/*    */ 
/*    */ import com.itextpdf.svg.utils.SvgCoordinateUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SmoothOperatorConverter
/*    */   implements IOperatorConverter
/*    */ {
/*    */   public String[] makeCoordinatesAbsolute(String[] relativeCoordinates, double[] initialPoint) {
/* 71 */     String[] result = new String[relativeCoordinates.length];
/* 72 */     System.arraycopy(relativeCoordinates, 0, result, 0, 2);
/*    */     
/* 74 */     relativeCoordinates = SvgCoordinateUtils.makeRelativeOperatorCoordinatesAbsolute(relativeCoordinates, initialPoint);
/*    */     
/* 76 */     System.arraycopy(relativeCoordinates, 2, result, 2, relativeCoordinates.length - 2);
/* 77 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/SmoothOperatorConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */