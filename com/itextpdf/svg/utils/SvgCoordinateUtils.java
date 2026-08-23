/*    */ package com.itextpdf.svg.utils;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SvgCoordinateUtils
/*    */ {
/*    */   public static String[] makeRelativeOperatorCoordinatesAbsolute(String[] relativeCoordinates, double[] currentCoordinates) {
/* 61 */     if (relativeCoordinates.length % currentCoordinates.length != 0) {
/* 62 */       throw new IllegalArgumentException("Array of current coordinates must have length that is divisible by the length of the array with current coordinates");
/*    */     }
/*    */     
/* 65 */     String[] absoluteOperators = new String[relativeCoordinates.length];
/*    */     
/* 67 */     for (int i = 0; i < relativeCoordinates.length;) {
/* 68 */       for (int j = 0; j < currentCoordinates.length; j++, i++) {
/* 69 */         double relativeDouble = Double.parseDouble(relativeCoordinates[i]);
/* 70 */         relativeDouble += currentCoordinates[j];
/* 71 */         absoluteOperators[i] = SvgCssUtils.convertDoubleToString(relativeDouble);
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     return absoluteOperators;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double calculateAngleBetweenTwoVectors(Vector vectorA, Vector vectorB) {
/* 86 */     return Math.acos(vectorA.dot(vectorB) / vectorA.length() * vectorB.length());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/utils/SvgCoordinateUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */