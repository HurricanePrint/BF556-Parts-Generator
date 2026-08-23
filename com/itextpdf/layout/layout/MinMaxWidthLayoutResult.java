/*    */ package com.itextpdf.layout.layout;
/*    */ 
/*    */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*    */ import com.itextpdf.layout.renderer.IRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MinMaxWidthLayoutResult
/*    */   extends LayoutResult
/*    */ {
/*    */   protected MinMaxWidth minMaxWidth;
/*    */   
/*    */   public MinMaxWidthLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer) {
/* 57 */     super(status, occupiedArea, splitRenderer, overflowRenderer);
/* 58 */     this.minMaxWidth = new MinMaxWidth();
/*    */   }
/*    */   
/*    */   public MinMaxWidthLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer, IRenderer cause) {
/* 62 */     super(status, occupiedArea, splitRenderer, overflowRenderer, cause);
/* 63 */     this.minMaxWidth = new MinMaxWidth();
/*    */   }
/*    */   
/*    */   public MinMaxWidth getMinMaxWidth() {
/* 67 */     return this.minMaxWidth;
/*    */   }
/*    */   
/*    */   public MinMaxWidthLayoutResult setMinMaxWidth(MinMaxWidth minMaxWidth) {
/* 71 */     this.minMaxWidth = minMaxWidth;
/* 72 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/MinMaxWidthLayoutResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */