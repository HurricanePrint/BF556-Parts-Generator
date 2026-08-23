/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SvgTagSvgNodeRenderer
/*    */   extends AbstractContainerSvgNodeRenderer
/*    */ {
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 53 */     SvgTagSvgNodeRenderer copy = new SvgTagSvgNodeRenderer();
/* 54 */     deepCopyAttributesAndStyles(copy);
/* 55 */     deepCopyChildren(copy);
/* 56 */     return copy;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/SvgTagSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */