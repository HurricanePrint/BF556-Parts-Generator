/*     */ package com.itextpdf.layout;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.layout.RootLayoutArea;
/*     */ import com.itextpdf.layout.property.AreaBreakType;
/*     */ import com.itextpdf.layout.renderer.DocumentRenderer;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColumnDocumentRenderer
/*     */   extends DocumentRenderer
/*     */ {
/*     */   protected Rectangle[] columns;
/*     */   protected int nextAreaNumber;
/*     */   
/*     */   public ColumnDocumentRenderer(Document document, Rectangle[] columns) {
/*  71 */     super(document);
/*  72 */     this.columns = columns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColumnDocumentRenderer(Document document, boolean immediateFlush, Rectangle[] columns) {
/*  86 */     super(document, immediateFlush);
/*  87 */     this.columns = columns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextAreaNumber() {
/*  96 */     return this.nextAreaNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 101 */     return (IRenderer)new ColumnDocumentRenderer(this.document, this.immediateFlush, this.columns);
/*     */   }
/*     */ 
/*     */   
/*     */   protected LayoutArea updateCurrentArea(LayoutResult overflowResult) {
/* 106 */     if (overflowResult != null && overflowResult.getAreaBreak() != null && overflowResult.getAreaBreak().getType() != AreaBreakType.NEXT_AREA) {
/* 107 */       this.nextAreaNumber = 0;
/*     */     }
/* 109 */     if (this.nextAreaNumber % this.columns.length == 0) {
/* 110 */       super.updateCurrentArea(overflowResult);
/*     */     }
/* 112 */     return (LayoutArea)(this.currentArea = new RootLayoutArea(this.currentPageNumber, this.columns[this.nextAreaNumber++ % this.columns.length].clone()));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/ColumnDocumentRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */