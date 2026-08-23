/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*     */ import com.itextpdf.layout.Canvas;
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.layout.RootLayoutArea;
/*     */ import com.itextpdf.layout.property.Transform;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public class CanvasRenderer
/*     */   extends RootRenderer
/*     */ {
/*     */   protected Canvas canvas;
/*     */   
/*     */   public CanvasRenderer(Canvas canvas) {
/*  67 */     this(canvas, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasRenderer(Canvas canvas, boolean immediateFlush) {
/*  78 */     this.canvas = canvas;
/*  79 */     this.modelElement = (IPropertyContainer)canvas;
/*  80 */     this.immediateFlush = immediateFlush;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChild(IRenderer renderer) {
/*  85 */     if (Boolean.TRUE.equals(getPropertyAsBoolean(25))) {
/*  86 */       LoggerFactory.getLogger(CanvasRenderer.class).warn("Canvas is already full. Element will be skipped.");
/*     */     } else {
/*  88 */       super.addChild(renderer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flushSingleRenderer(IRenderer resultRenderer) {
/*  97 */     Transform transformProp = (Transform)resultRenderer.getProperty(53);
/*  98 */     if (!this.waitingDrawingElements.contains(resultRenderer)) {
/*  99 */       processWaitingDrawing(resultRenderer, transformProp, this.waitingDrawingElements);
/* 100 */       if (FloatingHelper.isRendererFloating(resultRenderer) || transformProp != null) {
/*     */         return;
/*     */       }
/*     */     } 
/* 104 */     if (!resultRenderer.isFlushed()) {
/* 105 */       boolean toTag = (this.canvas.getPdfDocument().isTagged() && this.canvas.isAutoTaggingEnabled());
/* 106 */       TagTreePointer tagPointer = null;
/* 107 */       if (toTag) {
/* 108 */         tagPointer = this.canvas.getPdfDocument().getTagStructureContext().getAutoTaggingPointer();
/* 109 */         tagPointer.setPageForTagging(this.canvas.getPage());
/*     */         
/* 111 */         boolean pageStream = false;
/* 112 */         for (int i = this.canvas.getPage().getContentStreamCount() - 1; i >= 0; i--) {
/* 113 */           if (this.canvas.getPage().getContentStream(i).equals(this.canvas.getPdfCanvas().getContentStream())) {
/* 114 */             pageStream = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 118 */         if (!pageStream) {
/* 119 */           tagPointer.setContentStreamForTagging(this.canvas.getPdfCanvas().getContentStream());
/*     */         }
/*     */       } 
/* 122 */       resultRenderer.draw(new DrawContext(this.canvas.getPdfDocument(), this.canvas.getPdfCanvas(), toTag));
/* 123 */       if (toTag) {
/* 124 */         tagPointer.setContentStreamForTagging(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutArea updateCurrentArea(LayoutResult overflowResult) {
/* 134 */     if (this.currentArea == null) {
/* 135 */       int pageNumber = this.canvas.isCanvasOfPage() ? this.canvas.getPdfDocument().getPageNumber(this.canvas.getPage()) : 0;
/* 136 */       this.currentArea = new RootLayoutArea(pageNumber, this.canvas.getRootArea().clone());
/*     */     } else {
/* 138 */       setProperty(25, Boolean.valueOf(true));
/* 139 */       this.currentArea = null;
/*     */     } 
/* 141 */     return (LayoutArea)this.currentArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 151 */     return new CanvasRenderer(this.canvas, this.immediateFlush);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/CanvasRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */