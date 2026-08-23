/*    */ package com.itextpdf.layout.renderer;
/*    */ 
/*    */ import com.itextpdf.layout.element.Div;
/*    */ import com.itextpdf.layout.element.IElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DivRenderer
/*    */   extends BlockRenderer
/*    */ {
/*    */   public DivRenderer(Div modelElement) {
/* 56 */     super((IElement)modelElement);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IRenderer getNextRenderer() {
/* 64 */     return new DivRenderer((Div)this.modelElement);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/DivRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */