/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.element.LineSeparator;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
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
/*     */ public class LineSeparatorRenderer
/*     */   extends BlockRenderer
/*     */ {
/*     */   public LineSeparatorRenderer(LineSeparator lineSeparator) {
/*  63 */     super((IElement)lineSeparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutResult layout(LayoutContext layoutContext) {
/*  71 */     Rectangle parentBBox = layoutContext.getArea().getBBox().clone();
/*  72 */     if (getProperty(55) != null) {
/*  73 */       parentBBox.moveDown(1000000.0F - parentBBox.getHeight()).setHeight(1000000.0F);
/*     */     }
/*     */     
/*  76 */     ILineDrawer lineDrawer = getProperty(35);
/*  77 */     float height = (lineDrawer != null) ? lineDrawer.getLineWidth() : 0.0F;
/*     */     
/*  79 */     this.occupiedArea = new LayoutArea(layoutContext.getArea().getPageNumber(), parentBBox.clone());
/*  80 */     applyMargins(this.occupiedArea.getBBox(), false);
/*     */     
/*  82 */     Float calculatedWidth = retrieveWidth(layoutContext.getArea().getBBox().getWidth());
/*  83 */     if (calculatedWidth == null) {
/*  84 */       calculatedWidth = Float.valueOf(this.occupiedArea.getBBox().getWidth());
/*     */     }
/*  86 */     if ((this.occupiedArea.getBBox().getHeight() < height || this.occupiedArea.getBBox().getWidth() < calculatedWidth.floatValue()) && !hasOwnProperty(26)) {
/*  87 */       return new LayoutResult(3, null, null, this, this);
/*     */     }
/*     */     
/*  90 */     this.occupiedArea.getBBox().setWidth(calculatedWidth.floatValue()).moveUp(this.occupiedArea.getBBox().getHeight() - height).setHeight(height);
/*     */     
/*  92 */     applyMargins(this.occupiedArea.getBBox(), true);
/*     */     
/*  94 */     if (getProperty(55) != null) {
/*  95 */       applyRotationLayout(layoutContext.getArea().getBBox().clone());
/*  96 */       if (isNotFittingLayoutArea(layoutContext.getArea()) && 
/*  97 */         !Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
/*  98 */         return new LayoutResult(3, null, null, this, this);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return new LayoutResult(1, this.occupiedArea, this, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 111 */     return new LineSeparatorRenderer((LineSeparator)this.modelElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawChildren(DrawContext drawContext) {
/* 119 */     ILineDrawer lineDrawer = getProperty(35);
/* 120 */     if (lineDrawer != null) {
/* 121 */       PdfCanvas canvas = drawContext.getCanvas();
/* 122 */       boolean isTagged = drawContext.isTaggingEnabled();
/* 123 */       if (isTagged) {
/* 124 */         canvas.openTag((CanvasTag)new CanvasArtifact());
/*     */       }
/*     */       
/* 127 */       Rectangle area = getOccupiedAreaBBox();
/* 128 */       applyMargins(area, false);
/* 129 */       lineDrawer.draw(canvas, area);
/*     */       
/* 131 */       if (isTagged)
/* 132 */         canvas.closeTag(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/LineSeparatorRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */