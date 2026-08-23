/*    */ package com.itextpdf.forms.fields;
/*    */ 
/*    */ import com.itextpdf.layout.element.Text;
/*    */ import com.itextpdf.layout.layout.LayoutContext;
/*    */ import com.itextpdf.layout.layout.LayoutResult;
/*    */ import com.itextpdf.layout.layout.TextLayoutResult;
/*    */ import com.itextpdf.layout.renderer.IRenderer;
/*    */ import com.itextpdf.layout.renderer.TextRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class FormFieldValueNonTrimmingTextRenderer
/*    */   extends TextRenderer
/*    */ {
/*    */   private boolean callTrimFirst = false;
/*    */   
/*    */   public FormFieldValueNonTrimmingTextRenderer(Text textElement) {
/* 43 */     super(textElement);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRenderer getNextRenderer() {
/* 48 */     return (IRenderer)new FormFieldValueNonTrimmingTextRenderer((Text)getModelElement());
/*    */   }
/*    */ 
/*    */   
/*    */   public LayoutResult layout(LayoutContext layoutContext) {
/* 53 */     LayoutResult baseLayoutResult = super.layout(layoutContext);
/* 54 */     if (baseLayoutResult instanceof TextLayoutResult && baseLayoutResult
/* 55 */       .getOverflowRenderer() instanceof FormFieldValueNonTrimmingTextRenderer && 
/* 56 */       !((TextLayoutResult)baseLayoutResult).isSplitForcedByNewline())
/*    */     {
/*    */       
/* 59 */       ((FormFieldValueNonTrimmingTextRenderer)baseLayoutResult.getOverflowRenderer()).setCallTrimFirst(true);
/*    */     }
/* 61 */     return baseLayoutResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public void trimFirst() {
/* 66 */     if (this.callTrimFirst) {
/* 67 */       super.trimFirst();
/*    */     }
/*    */   }
/*    */   
/*    */   private void setCallTrimFirst(boolean callTrimFirst) {
/* 72 */     this.callTrimFirst = callTrimFirst;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/fields/FormFieldValueNonTrimmingTextRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */