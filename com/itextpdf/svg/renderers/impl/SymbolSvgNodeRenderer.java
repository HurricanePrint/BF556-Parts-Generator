/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
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
/*    */ public class SymbolSvgNodeRenderer
/*    */   extends AbstractContainerSvgNodeRenderer
/*    */   implements INoDrawSvgNodeRenderer
/*    */ {
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 31 */     SymbolSvgNodeRenderer copy = new SymbolSvgNodeRenderer();
/* 32 */     deepCopyAttributesAndStyles(copy);
/* 33 */     deepCopyChildren(copy);
/* 34 */     return copy;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/SymbolSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */