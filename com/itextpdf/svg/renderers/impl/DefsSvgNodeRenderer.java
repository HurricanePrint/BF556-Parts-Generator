/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
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
/*    */ public class DefsSvgNodeRenderer
/*    */   extends AbstractBranchSvgNodeRenderer
/*    */   implements INoDrawSvgNodeRenderer
/*    */ {
/*    */   protected void doDraw(SvgDrawContext context) {
/* 33 */     throw new UnsupportedOperationException("Can't draw current SvgNodeRenderer.");
/*    */   }
/*    */ 
/*    */   
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 38 */     DefsSvgNodeRenderer copy = new DefsSvgNodeRenderer();
/* 39 */     deepCopyAttributesAndStyles(copy);
/* 40 */     return copy;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/DefsSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */