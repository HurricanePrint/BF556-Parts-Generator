/*     */ package com.itextpdf.layout;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.renderer.CanvasRenderer;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.RootRenderer;
/*     */ import org.slf4j.Logger;
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
/*     */ public class Canvas
/*     */   extends RootElement<Canvas>
/*     */ {
/*     */   protected PdfCanvas pdfCanvas;
/*     */   protected Rectangle rootArea;
/*     */   protected PdfPage page;
/*     */   private boolean isCanvasOfPage;
/*     */   
/*     */   public Canvas(PdfPage page, Rectangle rootArea) {
/*  93 */     this(initPdfCanvasOrThrowIfPageIsFlushed(page), rootArea);
/*  94 */     enableAutoTagging(page);
/*  95 */     this.isCanvasOfPage = true;
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
/*     */   public Canvas(PdfCanvas pdfCanvas, Rectangle rootArea) {
/* 107 */     this.pdfDocument = pdfCanvas.getDocument();
/* 108 */     this.pdfCanvas = pdfCanvas;
/* 109 */     this.rootArea = rootArea;
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
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Canvas(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rootArea) {
/* 126 */     this.pdfDocument = pdfDocument;
/* 127 */     this.pdfCanvas = pdfCanvas;
/* 128 */     this.rootArea = rootArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Canvas(PdfCanvas pdfCanvas, Rectangle rootArea, boolean immediateFlush) {
/* 139 */     this(pdfCanvas, rootArea);
/* 140 */     this.immediateFlush = immediateFlush;
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
/*     */   
/*     */   @Deprecated
/*     */   public Canvas(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rootArea, boolean immediateFlush) {
/* 156 */     this(pdfCanvas, rootArea);
/* 157 */     this.immediateFlush = immediateFlush;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Canvas(PdfFormXObject formXObject, PdfDocument pdfDocument) {
/* 167 */     this(new PdfCanvas(formXObject, pdfDocument), formXObject.getBBox().toRectangle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDocument getPdfDocument() {
/* 175 */     return this.pdfDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getRootArea() {
/* 183 */     return this.rootArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCanvas getPdfCanvas() {
/* 191 */     return this.pdfCanvas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderer(CanvasRenderer canvasRenderer) {
/* 200 */     this.rootRenderer = (RootRenderer)canvasRenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPage getPage() {
/* 208 */     return this.page;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableAutoTagging(PdfPage page) {
/* 216 */     if (isCanvasOfPage() && this.page != page) {
/* 217 */       Logger logger = LoggerFactory.getLogger(Canvas.class);
/* 218 */       logger.error("The page passed to Canvas#enableAutoTagging(PdfPage) method shall be the one on which this canvas will be rendered. However the actual passed PdfPage instance sets not such page. This might lead to creation of malformed PDF document.");
/*     */     } 
/* 220 */     this.page = page;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoTaggingEnabled() {
/* 227 */     return (this.page != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCanvasOfPage() {
/* 238 */     return this.isCanvasOfPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void relayout() {
/*     */     CanvasRenderer canvasRenderer;
/* 249 */     if (this.immediateFlush) {
/* 250 */       throw new IllegalStateException("Operation not supported with immediate flush");
/*     */     }
/*     */     
/* 253 */     IRenderer nextRelayoutRenderer = (this.rootRenderer != null) ? this.rootRenderer.getNextRenderer() : null;
/* 254 */     if (nextRelayoutRenderer == null || !(nextRelayoutRenderer instanceof RootRenderer)) {
/* 255 */       canvasRenderer = new CanvasRenderer(this, this.immediateFlush);
/*     */     }
/* 257 */     this.rootRenderer = (RootRenderer)canvasRenderer;
/* 258 */     for (IElement element : this.childElements) {
/* 259 */       createAndAddRendererSubTree(element);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 268 */     this.rootRenderer.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 279 */     if (this.rootRenderer != null) {
/* 280 */       this.rootRenderer.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected RootRenderer ensureRootRendererNotNull() {
/* 286 */     if (this.rootRenderer == null)
/* 287 */       this.rootRenderer = (RootRenderer)new CanvasRenderer(this, this.immediateFlush); 
/* 288 */     return this.rootRenderer;
/*     */   }
/*     */   
/*     */   private static PdfCanvas initPdfCanvasOrThrowIfPageIsFlushed(PdfPage page) {
/* 292 */     if (page.isFlushed()) {
/* 293 */       throw new PdfException("Cannot draw elements on already flushed pages.");
/*     */     }
/* 295 */     return new PdfCanvas(page);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/Canvas.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */