/*    */ package com.itextpdf.layout.element;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
/*    */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*    */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*    */ import com.itextpdf.layout.renderer.IRenderer;
/*    */ import com.itextpdf.layout.renderer.LineSeparatorRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LineSeparator
/*    */   extends BlockElement<LineSeparator>
/*    */ {
/*    */   protected DefaultAccessibilityProperties tagProperties;
/*    */   
/*    */   public LineSeparator(ILineDrawer lineDrawer) {
/* 69 */     setProperty(35, lineDrawer);
/*    */   }
/*    */ 
/*    */   
/*    */   public AccessibilityProperties getAccessibilityProperties() {
/* 74 */     if (this.tagProperties == null) {
/* 75 */       this.tagProperties = new DefaultAccessibilityProperties("Artifact");
/*    */     }
/* 77 */     return (AccessibilityProperties)this.tagProperties;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IRenderer makeNewRenderer() {
/* 82 */     return (IRenderer)new LineSeparatorRenderer(this);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/LineSeparator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */