/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Rectangle;
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
/*    */ public class TextSvgTSpanBranchRenderer
/*    */   extends TextSvgBranchRenderer
/*    */ {
/*    */   protected Rectangle getObjectBoundingBox(SvgDrawContext context) {
/* 57 */     if (getParent() instanceof AbstractSvgNodeRenderer) {
/* 58 */       return ((AbstractSvgNodeRenderer)getParent()).getObjectBoundingBox(context);
/*    */     }
/* 60 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/TextSvgTSpanBranchRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */