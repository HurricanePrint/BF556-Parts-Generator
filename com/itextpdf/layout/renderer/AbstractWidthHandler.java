/*    */ package com.itextpdf.layout.renderer;
/*    */ 
/*    */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractWidthHandler
/*    */ {
/*    */   MinMaxWidth minMaxWidth;
/*    */   
/*    */   public AbstractWidthHandler(MinMaxWidth minMaxWidth) {
/* 51 */     this.minMaxWidth = minMaxWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void updateMinChildWidth(float paramFloat);
/*    */   
/*    */   public void updateMinMaxWidth(MinMaxWidth minMaxWidth) {
/* 58 */     if (minMaxWidth != null) {
/* 59 */       updateMaxChildWidth(minMaxWidth.getMaxWidth());
/* 60 */       updateMinChildWidth(minMaxWidth.getMinWidth());
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void updateMaxChildWidth(float paramFloat);
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/AbstractWidthHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */