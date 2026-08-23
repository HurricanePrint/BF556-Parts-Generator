/*     */ package com.itextpdf.kernel.utils;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.counter.event.IMetaInfo;
/*     */ import com.itextpdf.kernel.pdf.DocumentProperties;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfOutline;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfWriter;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfSplitter
/*     */ {
/*     */   private PdfDocument pdfDocument;
/*     */   private boolean preserveTagged;
/*     */   private boolean preserveOutlines;
/*     */   private IMetaInfo metaInfo;
/*     */   
/*     */   public PdfSplitter(PdfDocument pdfDocument) {
/*  75 */     if (pdfDocument.getWriter() != null) {
/*  76 */       throw new PdfException("Cannot split document that is being written.");
/*     */     }
/*  78 */     this.pdfDocument = pdfDocument;
/*  79 */     this.preserveTagged = true;
/*  80 */     this.preserveOutlines = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEventCountingMetaInfo(IMetaInfo metaInfo) {
/*  89 */     this.metaInfo = metaInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreserveTagged(boolean preserveTagged) {
/* 100 */     this.preserveTagged = preserveTagged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreserveOutlines(boolean preserveOutlines) {
/* 111 */     this.preserveOutlines = preserveOutlines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfDocument> splitBySize(long size) {
/* 122 */     List<PageRange> splitRanges = new ArrayList<>();
/* 123 */     int currentPage = 1;
/* 124 */     int numOfPages = this.pdfDocument.getNumberOfPages();
/*     */     
/* 126 */     while (currentPage <= numOfPages) {
/* 127 */       PageRange nextRange = getNextRange(currentPage, numOfPages, size);
/* 128 */       splitRanges.add(nextRange);
/* 129 */       List<Integer> allPages = nextRange.getQualifyingPageNums(numOfPages);
/* 130 */       currentPage = ((Integer)allPages.get(allPages.size() - 1)).intValue() + 1;
/*     */     } 
/*     */     
/* 133 */     return extractPageRanges(splitRanges);
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
/*     */   public void splitByPageNumbers(List<Integer> pageNumbers, IDocumentReadyListener documentReady) {
/* 145 */     int currentPageNumber = 1;
/*     */     
/* 147 */     for (int ind = 0; ind <= pageNumbers.size(); ind++) {
/* 148 */       int nextPageNumber = (ind == pageNumbers.size()) ? (this.pdfDocument.getNumberOfPages() + 1) : ((Integer)pageNumbers.get(ind)).intValue();
/* 149 */       if (ind != 0 || nextPageNumber != 1) {
/*     */ 
/*     */         
/* 152 */         PageRange currentPageRange = (new PageRange()).addPageSequence(currentPageNumber, nextPageNumber - 1);
/* 153 */         PdfDocument currentDocument = createPdfDocument(currentPageRange);
/* 154 */         this.pdfDocument.copyPagesTo(currentPageNumber, nextPageNumber - 1, currentDocument);
/* 155 */         documentReady.documentReady(currentDocument, currentPageRange);
/*     */         
/* 157 */         currentPageNumber = nextPageNumber;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfDocument> splitByPageNumbers(List<Integer> pageNumbers) {
/* 169 */     final List<PdfDocument> splitDocuments = new ArrayList<>();
/*     */     
/* 171 */     splitByPageNumbers(pageNumbers, new IDocumentReadyListener()
/*     */         {
/*     */           public void documentReady(PdfDocument pdfDocument, PageRange pageRange) {
/* 174 */             splitDocuments.add(pdfDocument);
/*     */           }
/*     */         });
/*     */     
/* 178 */     return splitDocuments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void splitByPageCount(int pageCount, IDocumentReadyListener documentReady) {
/* 189 */     for (int startPage = 1; startPage <= this.pdfDocument.getNumberOfPages(); startPage += pageCount) {
/* 190 */       int endPage = Math.min(startPage + pageCount - 1, this.pdfDocument.getNumberOfPages());
/*     */       
/* 192 */       PageRange currentPageRange = (new PageRange()).addPageSequence(startPage, endPage);
/* 193 */       PdfDocument currentDocument = createPdfDocument(currentPageRange);
/* 194 */       this.pdfDocument.copyPagesTo(startPage, endPage, currentDocument);
/* 195 */       documentReady.documentReady(currentDocument, currentPageRange);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfDocument> splitByPageCount(int pageCount) {
/* 206 */     final List<PdfDocument> splitDocuments = new ArrayList<>();
/*     */     
/* 208 */     splitByPageCount(pageCount, new IDocumentReadyListener()
/*     */         {
/*     */           public void documentReady(PdfDocument pdfDocument, PageRange pageRange) {
/* 211 */             splitDocuments.add(pdfDocument);
/*     */           }
/*     */         });
/*     */     
/* 215 */     return splitDocuments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfDocument> extractPageRanges(List<PageRange> pageRanges) {
/* 226 */     List<PdfDocument> splitDocuments = new ArrayList<>();
/*     */     
/* 228 */     for (PageRange currentPageRange : pageRanges) {
/* 229 */       PdfDocument currentPdfDocument = createPdfDocument(currentPageRange);
/* 230 */       splitDocuments.add(currentPdfDocument);
/* 231 */       this.pdfDocument.copyPagesTo(currentPageRange.getQualifyingPageNums(this.pdfDocument.getNumberOfPages()), currentPdfDocument);
/*     */     } 
/*     */     
/* 234 */     return splitDocuments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDocument extractPageRange(PageRange pageRange) {
/* 245 */     return extractPageRanges(Collections.singletonList(pageRange)).get(0);
/*     */   }
/*     */   
/*     */   public PdfDocument getPdfDocument() {
/* 249 */     return this.pdfDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfWriter getNextPdfWriter(PageRange documentPageRange) {
/* 260 */     return new PdfWriter((OutputStream)new ByteArrayOutputStream());
/*     */   }
/*     */   
/*     */   private PdfDocument createPdfDocument(PageRange currentPageRange) {
/* 264 */     PdfDocument newDocument = new PdfDocument(getNextPdfWriter(currentPageRange), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/* 265 */     if (this.pdfDocument.isTagged() && this.preserveTagged)
/* 266 */       newDocument.setTagged(); 
/* 267 */     if (this.pdfDocument.hasOutlines() && this.preserveOutlines)
/* 268 */       newDocument.initializeOutlines(); 
/* 269 */     return newDocument;
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
/*     */   public List<PdfDocument> splitByOutlines(List<String> outlineTitles) {
/* 284 */     if (outlineTitles == null || outlineTitles.size() == 0) {
/* 285 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 288 */     List<PdfDocument> documentList = new ArrayList<>(outlineTitles.size());
/* 289 */     for (String title : outlineTitles) {
/* 290 */       PdfDocument document = splitByOutline(title);
/* 291 */       if (document != null) {
/* 292 */         documentList.add(document);
/*     */       }
/*     */     } 
/*     */     
/* 296 */     return documentList;
/*     */   }
/*     */ 
/*     */   
/*     */   private PdfDocument splitByOutline(String outlineTitle) {
/* 301 */     int startPage = -1;
/* 302 */     int endPage = -1;
/*     */     
/* 304 */     PdfDocument toDocument = createPdfDocument(null);
/*     */     
/* 306 */     int size = this.pdfDocument.getNumberOfPages();
/* 307 */     for (int i = 1; i <= size; i++) {
/* 308 */       PdfPage pdfPage = this.pdfDocument.getPage(i);
/* 309 */       List<PdfOutline> outlineList = pdfPage.getOutlines(false);
/* 310 */       if (outlineList != null) {
/* 311 */         for (PdfOutline pdfOutline : outlineList) {
/* 312 */           if (pdfOutline.getTitle().equals(outlineTitle)) {
/* 313 */             startPage = this.pdfDocument.getPageNumber(pdfPage);
/* 314 */             PdfOutline nextOutLine = getAbsoluteTreeNextOutline(pdfOutline);
/* 315 */             if (nextOutLine != null) {
/* 316 */               endPage = this.pdfDocument.getPageNumber(getPageByOutline(i, nextOutLine)) - 1;
/*     */             } else {
/* 318 */               endPage = size;
/*     */             } 
/*     */             
/* 321 */             if (startPage - endPage == 1) {
/* 322 */               endPage = startPage;
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 330 */     if (startPage == -1 || endPage == -1) {
/* 331 */       return null;
/*     */     }
/* 333 */     this.pdfDocument.copyPagesTo(startPage, endPage, toDocument);
/*     */     
/* 335 */     return toDocument;
/*     */   }
/*     */   
/*     */   private PdfPage getPageByOutline(int fromPage, PdfOutline outline) {
/* 339 */     int size = this.pdfDocument.getNumberOfPages();
/* 340 */     for (int i = fromPage; i <= size; i++) {
/* 341 */       PdfPage pdfPage = this.pdfDocument.getPage(i);
/* 342 */       List<PdfOutline> outlineList = pdfPage.getOutlines(false);
/* 343 */       if (outlineList != null) {
/* 344 */         for (PdfOutline pdfOutline : outlineList) {
/* 345 */           if (pdfOutline.equals(outline)) {
/* 346 */             return pdfPage;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 351 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfOutline getAbsoluteTreeNextOutline(PdfOutline outline) {
/* 361 */     PdfObject nextPdfObject = outline.getContent().get(PdfName.Next);
/* 362 */     PdfOutline nextPdfOutline = null;
/*     */     
/* 364 */     if (outline.getParent() != null && nextPdfObject != null) {
/* 365 */       for (PdfOutline pdfOutline : outline.getParent().getAllChildren()) {
/* 366 */         if (pdfOutline.getContent().getIndirectReference().equals(nextPdfObject.getIndirectReference())) {
/* 367 */           nextPdfOutline = pdfOutline;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 372 */     if (nextPdfOutline == null && outline.getParent() != null) {
/* 373 */       nextPdfOutline = getAbsoluteTreeNextOutline(outline.getParent());
/*     */     }
/* 375 */     return nextPdfOutline;
/*     */   }
/*     */   
/*     */   private PageRange getNextRange(int startPage, int endPage, long size) {
/* 379 */     PdfResourceCounter counter = new PdfResourceCounter((PdfObject)this.pdfDocument.getTrailer());
/* 380 */     Map<Integer, PdfObject> resources = counter.getResources();
/*     */     
/* 382 */     long lengthWithoutXref = counter.getLength(null);
/* 383 */     int currentPage = startPage;
/* 384 */     boolean oversized = false;
/*     */     
/*     */     do {
/* 387 */       PdfPage page = this.pdfDocument.getPage(currentPage++);
/* 388 */       counter = new PdfResourceCounter(page.getPdfObject());
/* 389 */       lengthWithoutXref += counter.getLength(resources);
/* 390 */       resources.putAll(counter.getResources());
/*     */       
/* 392 */       if (lengthWithoutXref + xrefLength(resources.size()) <= size)
/* 393 */         continue;  oversized = true;
/*     */     }
/* 395 */     while (currentPage <= endPage && !oversized);
/*     */ 
/*     */     
/* 398 */     if (oversized && currentPage - 1 != startPage)
/*     */     {
/*     */       
/* 401 */       currentPage--;
/*     */     }
/*     */     
/* 404 */     return (new PageRange()).addPageSequence(startPage, currentPage - 1);
/*     */   }
/*     */   
/*     */   private long xrefLength(int size) {
/* 408 */     return 20L * (size + 1);
/*     */   }
/*     */   
/*     */   public static interface IDocumentReadyListener {
/*     */     void documentReady(PdfDocument param1PdfDocument, PageRange param1PageRange);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/utils/PdfSplitter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */