/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.geom.PageSize;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.Document;
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ import com.itextpdf.layout.element.AreaBreak;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.layout.RootLayoutArea;
/*     */ import com.itextpdf.layout.property.AreaBreakType;
/*     */ import com.itextpdf.layout.property.Transform;
/*     */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class DocumentRenderer
/*     */   extends RootRenderer
/*     */ {
/*     */   protected Document document;
/*  68 */   protected List<Integer> wrappedContentPage = new ArrayList<>();
/*     */   
/*     */   public DocumentRenderer(Document document) {
/*  71 */     this(document, true);
/*     */   }
/*     */   
/*     */   public DocumentRenderer(Document document, boolean immediateFlush) {
/*  75 */     this.document = document;
/*  76 */     this.immediateFlush = immediateFlush;
/*  77 */     this.modelElement = (IPropertyContainer)document;
/*     */   }
/*     */ 
/*     */   
/*     */   public LayoutArea getOccupiedArea() {
/*  82 */     throw new IllegalStateException("Not applicable for DocumentRenderer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/*  92 */     return new DocumentRenderer(this.document, this.immediateFlush);
/*     */   }
/*     */   
/*     */   protected LayoutArea updateCurrentArea(LayoutResult overflowResult) {
/*  96 */     flushWaitingDrawingElements(false);
/*  97 */     LayoutTaggingHelper taggingHelper = getProperty(108);
/*  98 */     if (taggingHelper != null) {
/*  99 */       taggingHelper.releaseFinishedHints();
/*     */     }
/* 101 */     AreaBreak areaBreak = (overflowResult != null && overflowResult.getAreaBreak() != null) ? overflowResult.getAreaBreak() : null;
/* 102 */     if (areaBreak != null && areaBreak.getType() == AreaBreakType.LAST_PAGE) {
/* 103 */       while (this.currentPageNumber < this.document.getPdfDocument().getNumberOfPages()) {
/* 104 */         moveToNextPage();
/*     */       }
/*     */     } else {
/* 107 */       moveToNextPage();
/*     */     } 
/* 109 */     PageSize customPageSize = (areaBreak != null) ? areaBreak.getPageSize() : null;
/* 110 */     while (this.document.getPdfDocument().getNumberOfPages() >= this.currentPageNumber && this.document.getPdfDocument().getPage(this.currentPageNumber).isFlushed()) {
/* 111 */       this.currentPageNumber++;
/*     */     }
/* 113 */     PageSize lastPageSize = ensureDocumentHasNPages(this.currentPageNumber, customPageSize);
/* 114 */     if (lastPageSize == null) {
/* 115 */       lastPageSize = new PageSize(this.document.getPdfDocument().getPage(this.currentPageNumber).getTrimBox());
/*     */     }
/* 117 */     return (LayoutArea)(this.currentArea = new RootLayoutArea(this.currentPageNumber, getCurrentPageEffectiveArea(lastPageSize)));
/*     */   }
/*     */   
/*     */   protected void flushSingleRenderer(IRenderer resultRenderer) {
/* 121 */     Transform transformProp = (Transform)resultRenderer.getProperty(53);
/* 122 */     if (!this.waitingDrawingElements.contains(resultRenderer)) {
/* 123 */       processWaitingDrawing(resultRenderer, transformProp, this.waitingDrawingElements);
/* 124 */       if (FloatingHelper.isRendererFloating(resultRenderer) || transformProp != null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 129 */     if (!resultRenderer.isFlushed() && null != resultRenderer.getOccupiedArea()) {
/* 130 */       int pageNum = resultRenderer.getOccupiedArea().getPageNumber();
/*     */       
/* 132 */       PdfDocument pdfDocument = this.document.getPdfDocument();
/* 133 */       ensureDocumentHasNPages(pageNum, (PageSize)null);
/* 134 */       PdfPage correspondingPage = pdfDocument.getPage(pageNum);
/* 135 */       if (correspondingPage.isFlushed()) {
/* 136 */         throw new PdfException("Cannot draw elements on already flushed pages.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 141 */       boolean wrapOldContent = (pdfDocument.getReader() != null && pdfDocument.getWriter() != null && correspondingPage.getContentStreamCount() > 0 && correspondingPage.getLastContentStream().getLength() > 0 && !this.wrappedContentPage.contains(Integer.valueOf(pageNum)) && pdfDocument.getNumberOfPages() >= pageNum);
/* 142 */       this.wrappedContentPage.add(Integer.valueOf(pageNum));
/*     */       
/* 144 */       if (pdfDocument.isTagged()) {
/* 145 */         pdfDocument.getTagStructureContext().getAutoTaggingPointer().setPageForTagging(correspondingPage);
/*     */       }
/* 147 */       resultRenderer.draw(new DrawContext(pdfDocument, new PdfCanvas(correspondingPage, wrapOldContent), pdfDocument.isTagged()));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected PageSize addNewPage(PageSize customPageSize) {
/* 152 */     if (customPageSize != null) {
/* 153 */       this.document.getPdfDocument().addNewPage(customPageSize);
/*     */     } else {
/* 155 */       this.document.getPdfDocument().addNewPage();
/*     */     } 
/* 157 */     return (customPageSize != null) ? customPageSize : this.document.getPdfDocument().getDefaultPageSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PageSize ensureDocumentHasNPages(int n, PageSize customPageSize) {
/* 165 */     PageSize lastPageSize = null;
/* 166 */     while (this.document.getPdfDocument().getNumberOfPages() < n) {
/* 167 */       lastPageSize = addNewPage(customPageSize);
/*     */     }
/* 169 */     return lastPageSize;
/*     */   }
/*     */   
/*     */   private Rectangle getCurrentPageEffectiveArea(PageSize pageSize) {
/* 173 */     float leftMargin = getPropertyAsFloat(44).floatValue();
/* 174 */     float bottomMargin = getPropertyAsFloat(43).floatValue();
/* 175 */     float topMargin = getPropertyAsFloat(46).floatValue();
/* 176 */     float rightMargin = getPropertyAsFloat(45).floatValue();
/* 177 */     return new Rectangle(pageSize.getLeft() + leftMargin, pageSize
/* 178 */         .getBottom() + bottomMargin, pageSize
/* 179 */         .getWidth() - leftMargin - rightMargin, pageSize
/* 180 */         .getHeight() - bottomMargin - topMargin);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveToNextPage() {
/* 186 */     if (this.immediateFlush && this.currentPageNumber > 1) {
/* 187 */       this.document.getPdfDocument().getPage(this.currentPageNumber - 1).flush();
/*     */     }
/* 189 */     this.currentPageNumber++;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/DocumentRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */