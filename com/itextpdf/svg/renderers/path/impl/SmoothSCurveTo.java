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
/*    */ public class SmoothSCurveTo
/*    */   extends CurveTo
/*    */ {
/*    */   static final int ARGUMENT_SIZE = 4;
/*    */   
/*    */   public SmoothSCurveTo() {
/* 54 */     this(false);
/*    */   }
/*    */   
/*    */   public SmoothSCurveTo(boolean relative) {
/* 58 */     super(relative, new SmoothOperatorConverter());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/SmoothSCurveTo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */