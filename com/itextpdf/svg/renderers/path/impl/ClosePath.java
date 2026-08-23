/*    */ package com.itextpdf.svg.renderers.path.impl;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClosePath
/*    */   extends LineTo
/*    */ {
/*    */   static final int ARGUMENT_SIZE = 0;
/*    */   
/*    */   public ClosePath() {
/* 54 */     this(false);
/*    */   }
/*    */   
/*    */   public ClosePath(boolean relative) {
/* 58 */     super(relative);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(PdfCanvas canvas) {
/* 63 */     canvas.closePath();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/ClosePath.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */