/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
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
/*    */ @Deprecated
/*    */ public class NoDrawOperationSvgNodeRenderer
/*    */   extends AbstractBranchSvgNodeRenderer
/*    */ {
/*    */   protected void doDraw(SvgDrawContext context) {
/* 60 */     throw new UnsupportedOperationException("Can't draw current SvgNodeRenderer.");
/*    */   }
/*    */ 
/*    */   
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 65 */     NoDrawOperationSvgNodeRenderer copy = new NoDrawOperationSvgNodeRenderer();
/* 66 */     deepCopyAttributesAndStyles(copy);
/* 67 */     return copy;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/NoDrawOperationSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */