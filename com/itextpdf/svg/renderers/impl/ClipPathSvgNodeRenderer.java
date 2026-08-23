/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*    */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*    */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClipPathSvgNodeRenderer
/*    */   extends AbstractBranchSvgNodeRenderer
/*    */ {
/*    */   private AbstractSvgNodeRenderer clippedRenderer;
/*    */   
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 63 */     AbstractBranchSvgNodeRenderer copy = new ClipPathSvgNodeRenderer();
/* 64 */     deepCopyAttributesAndStyles(copy);
/* 65 */     deepCopyChildren(copy);
/* 66 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   void preDraw(SvgDrawContext context) {}
/*    */   
/*    */   protected void doDraw(SvgDrawContext context) {
/* 73 */     PdfCanvas currentCanvas = context.getCurrentCanvas();
/* 74 */     for (ISvgNodeRenderer child : getChildren()) {
/* 75 */       currentCanvas.saveState();
/*    */       
/* 77 */       if (child instanceof AbstractSvgNodeRenderer) {
/* 78 */         ((AbstractSvgNodeRenderer)child).setPartOfClipPath(true);
/*    */       }
/*    */       
/* 81 */       child.draw(context);
/*    */       
/* 83 */       if (child instanceof AbstractSvgNodeRenderer) {
/* 84 */         ((AbstractSvgNodeRenderer)child).setPartOfClipPath(false);
/*    */       }
/*    */       
/* 87 */       if (this.clippedRenderer != null) {
/* 88 */         this.clippedRenderer.preDraw(context);
/* 89 */         this.clippedRenderer.doDraw(context);
/* 90 */         this.clippedRenderer.postDraw(context);
/*    */       } 
/*    */       
/* 93 */       currentCanvas.restoreState();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setClippedRenderer(AbstractSvgNodeRenderer clippedRenderer) {
/* 99 */     this.clippedRenderer = clippedRenderer;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/ClipPathSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */