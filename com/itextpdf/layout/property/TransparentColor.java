/*    */ package com.itextpdf.layout.property;
/*    */ 
/*    */ import com.itextpdf.kernel.colors.Color;
/*    */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*    */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransparentColor
/*    */ {
/*    */   private Color color;
/*    */   private float opacity;
/*    */   
/*    */   public TransparentColor(Color color) {
/* 54 */     this.color = color;
/* 55 */     this.opacity = 1.0F;
/*    */   }
/*    */   
/*    */   public TransparentColor(Color color, float opacity) {
/* 59 */     this.color = color;
/* 60 */     this.opacity = opacity;
/*    */   }
/*    */   
/*    */   public Color getColor() {
/* 64 */     return this.color;
/*    */   }
/*    */   
/*    */   public float getOpacity() {
/* 68 */     return this.opacity;
/*    */   }
/*    */   
/*    */   public void applyFillTransparency(PdfCanvas canvas) {
/* 72 */     applyTransparency(canvas, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyStrokeTransparency(PdfCanvas canvas) {
/* 77 */     applyTransparency(canvas, true);
/*    */   }
/*    */   
/*    */   private void applyTransparency(PdfCanvas canvas, boolean isStroke) {
/* 81 */     if (isTransparent()) {
/* 82 */       PdfExtGState extGState = new PdfExtGState();
/* 83 */       if (isStroke) {
/* 84 */         extGState.setStrokeOpacity(this.opacity);
/*    */       } else {
/* 86 */         extGState.setFillOpacity(this.opacity);
/*    */       } 
/* 88 */       canvas.setExtGState(extGState);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean isTransparent() {
/* 93 */     return (this.opacity < 1.0F);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/TransparentColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */