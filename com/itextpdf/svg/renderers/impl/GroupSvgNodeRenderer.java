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
/*    */ public class GroupSvgNodeRenderer
/*    */   extends AbstractBranchSvgNodeRenderer
/*    */ {
/*    */   protected void doDraw(SvgDrawContext context) {
/* 56 */     PdfCanvas currentCanvas = context.getCurrentCanvas();
/*    */     
/* 58 */     for (ISvgNodeRenderer child : getChildren()) {
/* 59 */       currentCanvas.saveState();
/* 60 */       child.draw(context);
/* 61 */       currentCanvas.restoreState();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 67 */     GroupSvgNodeRenderer copy = new GroupSvgNodeRenderer();
/* 68 */     deepCopyAttributesAndStyles(copy);
/* 69 */     deepCopyChildren(copy);
/* 70 */     return copy;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/GroupSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */