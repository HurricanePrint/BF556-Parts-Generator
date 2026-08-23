/*    */ package com.itextpdf.layout.renderer;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDocument;
/*    */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DrawContext
/*    */ {
/*    */   private PdfDocument document;
/*    */   private PdfCanvas canvas;
/*    */   private boolean taggingEnabled;
/*    */   
/*    */   public DrawContext(PdfDocument document, PdfCanvas canvas) {
/* 56 */     this(document, canvas, false);
/*    */   }
/*    */   
/*    */   public DrawContext(PdfDocument document, PdfCanvas canvas, boolean enableTagging) {
/* 60 */     this.document = document;
/* 61 */     this.canvas = canvas;
/* 62 */     this.taggingEnabled = enableTagging;
/*    */   }
/*    */   
/*    */   public PdfDocument getDocument() {
/* 66 */     return this.document;
/*    */   }
/*    */   
/*    */   public PdfCanvas getCanvas() {
/* 70 */     return this.canvas;
/*    */   }
/*    */   
/*    */   public boolean isTaggingEnabled() {
/* 74 */     return this.taggingEnabled;
/*    */   }
/*    */   
/*    */   public void setTaggingEnabled(boolean taggingEnabled) {
/* 78 */     this.taggingEnabled = taggingEnabled;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/DrawContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */