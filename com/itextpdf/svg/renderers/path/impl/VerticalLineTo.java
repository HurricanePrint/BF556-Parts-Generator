/*    */ package com.itextpdf.svg.renderers.path.impl;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Point;
/*    */ import com.itextpdf.svg.utils.SvgCssUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VerticalLineTo
/*    */   extends LineTo
/*    */ {
/*    */   static final int ARGUMENT_SIZE = 1;
/*    */   
/*    */   public VerticalLineTo() {
/* 58 */     this(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public VerticalLineTo(boolean relative) {
/* 67 */     super(relative);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCoordinates(String[] inputCoordinates, Point startPoint) {
/* 72 */     String[] normalizedCoords = new String[2];
/*    */     
/* 74 */     normalizedCoords[0] = isRelative() ? "0" : SvgCssUtils.convertDoubleToString(startPoint.getX());
/* 75 */     normalizedCoords[1] = inputCoordinates[0];
/* 76 */     super.setCoordinates(normalizedCoords, startPoint);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/VerticalLineTo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */