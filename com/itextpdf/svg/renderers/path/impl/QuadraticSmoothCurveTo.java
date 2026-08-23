/*    */ package com.itextpdf.svg.renderers.path.impl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuadraticSmoothCurveTo
/*    */   extends QuadraticCurveTo
/*    */ {
/*    */   static final int ARGUMENT_SIZE = 2;
/*    */   
/*    */   public QuadraticSmoothCurveTo() {
/* 54 */     this(false);
/*    */   }
/*    */   
/*    */   public QuadraticSmoothCurveTo(boolean relative) {
/* 58 */     super(relative, new SmoothOperatorConverter());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/QuadraticSmoothCurveTo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */