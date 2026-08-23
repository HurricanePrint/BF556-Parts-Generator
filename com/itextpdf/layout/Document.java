/*     */ package com.itextpdf.layout;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.geom.PageSize;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.layout.element.AreaBreak;
/*     */ import com.itextpdf.layout.element.IBlockElement;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.element.ILargeElement;
/*     */ import com.itextpdf.layout.renderer.DocumentRenderer;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.RootRenderer;
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
/*     */ public class Document
/*     */   extends RootElement<Document>
/*     */ {
/*     */   @Deprecated
/*  74 */   protected float leftMargin = 36.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  79 */   protected float rightMargin = 36.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  84 */   protected float topMargin = 36.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  89 */   protected float bottomMargin = 36.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document(PdfDocument pdfDoc) {
/*  99 */     this(pdfDoc, pdfDoc.getDefaultPageSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document(PdfDocument pdfDoc, PageSize pageSize) {
/* 110 */     this(pdfDoc, pageSize, true);
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
/*     */   public Document(PdfDocument pdfDoc, PageSize pageSize, boolean immediateFlush) {
/* 124 */     this.pdfDocument = pdfDoc;
/* 125 */     this.pdfDocument.setDefaultPageSize(pageSize);
/* 126 */     this.immediateFlush = immediateFlush;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 134 */     if (this.rootRenderer != null) {
/* 135 */       this.rootRenderer.close();
/*     */     }
/* 137 */     this.pdfDocument.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document add(AreaBreak areaBreak) {
/* 148 */     checkClosingStatus();
/* 149 */     this.childElements.add(areaBreak);
/* 150 */     ensureRootRendererNotNull().addChild(areaBreak.createRendererSubTree());
/* 151 */     if (this.immediateFlush) {
/* 152 */       this.childElements.remove(this.childElements.size() - 1);
/*     */     }
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Document add(IBlockElement element) {
/* 159 */     checkClosingStatus();
/* 160 */     super.add(element);
/* 161 */     if (element instanceof ILargeElement) {
/* 162 */       ((ILargeElement)element).setDocument(this);
/* 163 */       ((ILargeElement)element).flushContent();
/*     */     } 
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDocument getPdfDocument() {
/* 174 */     return this.pdfDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderer(DocumentRenderer documentRenderer) {
/* 184 */     this.rootRenderer = (RootRenderer)documentRenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 192 */     this.rootRenderer.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void relayout() {
/*     */     DocumentRenderer documentRenderer;
/* 203 */     if (this.immediateFlush) {
/* 204 */       throw new IllegalStateException("Operation not supported with immediate flush");
/*     */     }
/*     */     
/* 207 */     IRenderer nextRelayoutRenderer = (this.rootRenderer != null) ? this.rootRenderer.getNextRenderer() : null;
/* 208 */     if (nextRelayoutRenderer == null || !(nextRelayoutRenderer instanceof RootRenderer)) {
/* 209 */       documentRenderer = new DocumentRenderer(this, this.immediateFlush);
/*     */     }
/*     */     
/* 212 */     while (this.pdfDocument.getNumberOfPages() > 0) {
/* 213 */       this.pdfDocument.removePage(this.pdfDocument.getNumberOfPages());
/*     */     }
/*     */     
/* 216 */     this.rootRenderer = (RootRenderer)documentRenderer;
/* 217 */     for (IElement element : this.childElements) {
/* 218 */       createAndAddRendererSubTree(element);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLeftMargin() {
/* 228 */     Float property = (Float)getProperty(44);
/* 229 */     return ((property != null) ? property : getDefaultProperty(44)).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLeftMargin(float leftMargin) {
/* 238 */     setProperty(44, Float.valueOf(leftMargin));
/* 239 */     this.leftMargin = leftMargin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRightMargin() {
/* 248 */     Float property = (Float)getProperty(45);
/* 249 */     return ((property != null) ? property : getDefaultProperty(45)).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRightMargin(float rightMargin) {
/* 258 */     setProperty(45, Float.valueOf(rightMargin));
/* 259 */     this.rightMargin = rightMargin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTopMargin() {
/* 268 */     Float property = (Float)getProperty(46);
/* 269 */     return ((property != null) ? property : getDefaultProperty(46)).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTopMargin(float topMargin) {
/* 278 */     setProperty(46, Float.valueOf(topMargin));
/* 279 */     this.topMargin = topMargin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBottomMargin() {
/* 288 */     Float property = (Float)getProperty(43);
/* 289 */     return ((property != null) ? property : getDefaultProperty(43)).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBottomMargin(float bottomMargin) {
/* 298 */     setProperty(43, Float.valueOf(bottomMargin));
/* 299 */     this.bottomMargin = bottomMargin;
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
/*     */   public void setMargins(float topMargin, float rightMargin, float bottomMargin, float leftMargin) {
/* 311 */     setTopMargin(topMargin);
/* 312 */     setRightMargin(rightMargin);
/* 313 */     setBottomMargin(bottomMargin);
/* 314 */     setLeftMargin(leftMargin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getPageEffectiveArea(PageSize pageSize) {
/* 325 */     float x = pageSize.getLeft() + getLeftMargin();
/* 326 */     float y = pageSize.getBottom() + getBottomMargin();
/* 327 */     float width = pageSize.getWidth() - getLeftMargin() - getRightMargin();
/* 328 */     float height = pageSize.getHeight() - getBottomMargin() - getTopMargin();
/* 329 */     return new Rectangle(x, y, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/* 335 */     switch (property) {
/*     */       case 43:
/*     */       case 44:
/*     */       case 45:
/*     */       case 46:
/* 340 */         return (T1)Float.valueOf(36.0F);
/*     */     } 
/* 342 */     return super.getDefaultProperty(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RootRenderer ensureRootRendererNotNull() {
/* 349 */     if (this.rootRenderer == null)
/* 350 */       this.rootRenderer = (RootRenderer)new DocumentRenderer(this, this.immediateFlush); 
/* 351 */     return this.rootRenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkClosingStatus() {
/* 358 */     if (getPdfDocument().isClosed())
/* 359 */       throw new PdfException("Document was closed. It is impossible to execute action."); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/Document.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */