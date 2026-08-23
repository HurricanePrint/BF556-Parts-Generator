/*    */ package com.itextpdf.layout.element;
/*    */ 
/*    */ import com.itextpdf.layout.renderer.IRenderer;
/*    */ import com.itextpdf.layout.renderer.TabRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tab
/*    */   extends AbstractElement<Tab>
/*    */   implements ILeafElement
/*    */ {
/*    */   protected IRenderer makeNewRenderer() {
/* 58 */     return (IRenderer)new TabRenderer(this);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/Tab.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */