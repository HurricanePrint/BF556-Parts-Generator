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
/*    */ public class MaxMaxWidthHandler
/*    */   extends AbstractWidthHandler
/*    */ {
/*    */   public MaxMaxWidthHandler(MinMaxWidth minMaxWidth) {
/* 50 */     super(minMaxWidth);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateMinChildWidth(float childMinWidth) {
/* 55 */     this.minMaxWidth.setChildrenMinWidth(Math.max(this.minMaxWidth.getChildrenMinWidth(), childMinWidth));
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateMaxChildWidth(float childMaxWidth) {
/* 60 */     this.minMaxWidth.setChildrenMaxWidth(Math.max(this.minMaxWidth.getChildrenMaxWidth(), childMaxWidth));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/MaxMaxWidthHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */