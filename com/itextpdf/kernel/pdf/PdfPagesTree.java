/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ class PdfPagesTree
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4189501363348296036L;
/*  63 */   private final int leafSize = 10;
/*     */   
/*     */   private List<PdfIndirectReference> pageRefs;
/*     */   
/*     */   private List<PdfPages> parents;
/*     */   private List<PdfPage> pages;
/*     */   private PdfDocument document;
/*     */   private boolean generated = false;
/*     */   private PdfPages root;
/*  72 */   private static final Logger LOGGER = LoggerFactory.getLogger(PdfPagesTree.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPagesTree(PdfCatalog pdfCatalog) {
/*  80 */     this.document = pdfCatalog.getDocument();
/*  81 */     this.pageRefs = new ArrayList<>();
/*  82 */     this.parents = new ArrayList<>();
/*  83 */     this.pages = new ArrayList<>();
/*  84 */     if (pdfCatalog.getPdfObject().containsKey(PdfName.Pages)) {
/*  85 */       PdfDictionary pages = pdfCatalog.getPdfObject().getAsDictionary(PdfName.Pages);
/*  86 */       if (pages == null)
/*  87 */         throw new PdfException("Invalid page structure. /Pages must be PdfDictionary."); 
/*  88 */       this.root = new PdfPages(0, 2147483647, pages, null);
/*  89 */       this.parents.add(this.root);
/*  90 */       for (int i = 0; i < this.root.getCount(); i++) {
/*  91 */         this.pageRefs.add(null);
/*  92 */         this.pages.add(null);
/*     */       } 
/*     */     } else {
/*  95 */       this.root = null;
/*  96 */       this.parents.add(new PdfPages(0, this.document));
/*     */     } 
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
/*     */   public PdfPage getPage(int pageNum) {
/* 109 */     if (pageNum < 1 || pageNum > getNumberOfPages()) {
/* 110 */       throw new IndexOutOfBoundsException(MessageFormatUtil.format("Requested page number {0} is out of bounds.", new Object[] { Integer.valueOf(pageNum) }));
/*     */     }
/* 112 */     pageNum--;
/* 113 */     PdfPage pdfPage = this.pages.get(pageNum);
/* 114 */     if (pdfPage == null) {
/* 115 */       loadPage(pageNum);
/* 116 */       if (this.pageRefs.get(pageNum) != null) {
/* 117 */         int parentIndex = findPageParent(pageNum);
/* 118 */         PdfObject pageObject = ((PdfIndirectReference)this.pageRefs.get(pageNum)).getRefersTo();
/* 119 */         if (pageObject instanceof PdfDictionary) {
/* 120 */           pdfPage = this.document.getPageFactory().createPdfPage((PdfDictionary)pageObject);
/* 121 */           pdfPage.parentPages = this.parents.get(parentIndex);
/*     */         } else {
/* 123 */           LOGGER.error(MessageFormatUtil.format("Page tree is broken. Failed to retrieve page number {0}. Null will be returned.", new Object[] { Integer.valueOf(pageNum + 1) }));
/*     */         } 
/*     */       } else {
/* 126 */         LOGGER.error(MessageFormatUtil.format("Page tree is broken. Failed to retrieve page number {0}. Null will be returned.", new Object[] { Integer.valueOf(pageNum + 1) }));
/*     */       } 
/* 128 */       this.pages.set(pageNum, pdfPage);
/*     */     } 
/* 130 */     return pdfPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPage getPage(PdfDictionary pageDictionary) {
/* 140 */     int pageNum = getPageNumber(pageDictionary);
/* 141 */     if (pageNum > 0) {
/* 142 */       return getPage(pageNum);
/*     */     }
/*     */     
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfPages() {
/* 154 */     return this.pageRefs.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPageNumber(PdfPage page) {
/* 162 */     return this.pages.indexOf(page) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPageNumber(PdfDictionary pageDictionary) {
/* 170 */     int pageNum = this.pageRefs.indexOf(pageDictionary.getIndirectReference());
/* 171 */     if (pageNum >= 0) {
/* 172 */       return pageNum + 1;
/*     */     }
/* 174 */     for (int i = 0; i < this.pageRefs.size(); i++) {
/* 175 */       if (this.pageRefs.get(i) == null) {
/* 176 */         loadPage(i);
/*     */       }
/* 178 */       if (((PdfIndirectReference)this.pageRefs.get(i)).equals(pageDictionary.getIndirectReference())) {
/* 179 */         return i + 1;
/*     */       }
/*     */     } 
/*     */     
/* 183 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPage(PdfPage pdfPage) {
/*     */     PdfPages pdfPages;
/* 193 */     if (this.root != null) {
/*     */ 
/*     */       
/* 196 */       if (this.pageRefs.size() == 0) {
/* 197 */         pdfPages = this.root;
/*     */       } else {
/* 199 */         loadPage(this.pageRefs.size() - 1);
/* 200 */         pdfPages = this.parents.get(this.parents.size() - 1);
/*     */       } 
/*     */     } else {
/* 203 */       pdfPages = this.parents.get(this.parents.size() - 1);
/* 204 */       if (pdfPages.getCount() % 10 == 0 && this.pageRefs.size() > 0) {
/* 205 */         pdfPages = new PdfPages(pdfPages.getFrom() + pdfPages.getCount(), this.document);
/* 206 */         this.parents.add(pdfPages);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 211 */     pdfPage.makeIndirect(this.document);
/* 212 */     pdfPages.addPage(pdfPage.getPdfObject());
/* 213 */     pdfPage.parentPages = pdfPages;
/* 214 */     this.pageRefs.add(pdfPage.getPdfObject().getIndirectReference());
/* 215 */     this.pages.add(pdfPage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPage(int index, PdfPage pdfPage) {
/* 225 */     index--;
/* 226 */     if (index > this.pageRefs.size())
/* 227 */       throw new IndexOutOfBoundsException("index"); 
/* 228 */     if (index == this.pageRefs.size()) {
/* 229 */       addPage(pdfPage);
/*     */       return;
/*     */     } 
/* 232 */     loadPage(index);
/* 233 */     pdfPage.makeIndirect(this.document);
/* 234 */     int parentIndex = findPageParent(index);
/* 235 */     PdfPages parentPages = this.parents.get(parentIndex);
/* 236 */     parentPages.addPage(index, pdfPage);
/* 237 */     pdfPage.parentPages = parentPages;
/* 238 */     correctPdfPagesFromProperty(parentIndex + 1, 1);
/* 239 */     this.pageRefs.add(index, pdfPage.getPdfObject().getIndirectReference());
/* 240 */     this.pages.add(index, pdfPage);
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
/*     */   public PdfPage removePage(int pageNum) {
/* 252 */     PdfPage pdfPage = getPage(pageNum);
/* 253 */     if (pdfPage.isFlushed()) {
/* 254 */       LOGGER.warn("The page requested to be removed has already been flushed.");
/*     */     }
/* 256 */     if (internalRemovePage(--pageNum)) {
/* 257 */       return pdfPage;
/*     */     }
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   void releasePage(int pageNumber) {
/* 264 */     pageNumber--;
/* 265 */     if (this.pageRefs.get(pageNumber) != null && !((PdfIndirectReference)this.pageRefs.get(pageNumber)).checkState((short)1) && 
/* 266 */       !((PdfIndirectReference)this.pageRefs.get(pageNumber)).checkState((short)8) && (((PdfIndirectReference)this.pageRefs
/* 267 */       .get(pageNumber)).getOffset() > 0L || ((PdfIndirectReference)this.pageRefs.get(pageNumber)).getIndex() >= 0)) {
/* 268 */       this.pages.set(pageNumber, null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfObject generateTree() {
/* 279 */     if (this.pageRefs.size() == 0)
/* 280 */       throw new PdfException("Document has no pages."); 
/* 281 */     if (this.generated) {
/* 282 */       throw new PdfException("PdfPages tree could be generated only once.");
/*     */     }
/* 284 */     if (this.root == null) {
/* 285 */       while (this.parents.size() != 1) {
/* 286 */         List<PdfPages> nextParents = new ArrayList<>();
/*     */         
/* 288 */         int dynamicLeafSize = 10;
/* 289 */         PdfPages current = null;
/* 290 */         for (int i = 0; i < this.parents.size(); i++) {
/* 291 */           PdfPages pages = this.parents.get(i);
/* 292 */           int pageCount = pages.getCount();
/* 293 */           if (i % dynamicLeafSize == 0) {
/* 294 */             if (pageCount <= 1) {
/* 295 */               dynamicLeafSize++;
/*     */             } else {
/* 297 */               current = new PdfPages(-1, this.document);
/* 298 */               nextParents.add(current);
/* 299 */               dynamicLeafSize = 10;
/*     */             } 
/*     */           }
/* 302 */           assert current != null;
/* 303 */           current.addPages(pages);
/*     */         } 
/* 305 */         this.parents = nextParents;
/*     */       } 
/* 307 */       this.root = this.parents.get(0);
/*     */     } 
/* 309 */     this.generated = true;
/* 310 */     return this.root.getPdfObject();
/*     */   }
/*     */   
/*     */   protected void clearPageRefs() {
/* 314 */     this.pageRefs = null;
/* 315 */     this.pages = null;
/*     */   }
/*     */   
/*     */   protected List<PdfPages> getParents() {
/* 319 */     return this.parents;
/*     */   }
/*     */   
/*     */   protected PdfPages getRoot() {
/* 323 */     return this.root;
/*     */   }
/*     */   
/*     */   protected PdfPages findPageParent(PdfPage pdfPage) {
/* 327 */     int pageNum = getPageNumber(pdfPage) - 1;
/* 328 */     int parentIndex = findPageParent(pageNum);
/* 329 */     return this.parents.get(parentIndex);
/*     */   }
/*     */   
/*     */   private void loadPage(int pageNum) {
/* 333 */     PdfIndirectReference targetPage = this.pageRefs.get(pageNum);
/* 334 */     if (targetPage != null) {
/*     */       return;
/*     */     }
/*     */     
/* 338 */     int parentIndex = findPageParent(pageNum);
/* 339 */     PdfPages parent = this.parents.get(parentIndex);
/* 340 */     PdfArray kids = parent.getKids();
/* 341 */     if (kids == null) {
/* 342 */       throw (new PdfException("Invalid page structure {0}.")).setMessageParams(new Object[] { Integer.valueOf(pageNum + 1) });
/*     */     }
/* 344 */     int kidsCount = parent.getCount();
/*     */ 
/*     */ 
/*     */     
/* 348 */     boolean findPdfPages = false;
/*     */ 
/*     */     
/* 351 */     for (int i = 0; i < kids.size(); i++) {
/* 352 */       PdfDictionary page = kids.getAsDictionary(i);
/*     */ 
/*     */       
/* 355 */       if (page == null) {
/* 356 */         throw (new PdfException("Invalid page structure {0}.")).setMessageParams(new Object[] { Integer.valueOf(pageNum + 1) });
/*     */       }
/* 358 */       PdfObject pageKids = page.get(PdfName.Kids);
/* 359 */       if (pageKids != null) {
/* 360 */         if (pageKids.isArray()) {
/* 361 */           findPdfPages = true;
/*     */         } else {
/*     */           
/* 364 */           throw (new PdfException("Invalid page structure {0}.")).setMessageParams(new Object[] { Integer.valueOf(pageNum + 1) });
/*     */         } 
/*     */       }
/* 367 */       if (this.document.getReader().isMemorySavingMode() && !findPdfPages && parent.getFrom() + i != pageNum) {
/* 368 */         page.release();
/*     */       }
/*     */     } 
/* 371 */     if (findPdfPages) {
/*     */ 
/*     */ 
/*     */       
/* 375 */       List<PdfPages> newParents = new ArrayList<>(kids.size());
/* 376 */       PdfPages lastPdfPages = null; int j;
/* 377 */       for (j = 0; j < kids.size() && kidsCount > 0; j++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 385 */         PdfDictionary pdfPagesObject = kids.getAsDictionary(j);
/* 386 */         if (pdfPagesObject.getAsArray(PdfName.Kids) == null) {
/*     */ 
/*     */ 
/*     */           
/* 390 */           if (lastPdfPages == null) {
/*     */             
/* 392 */             lastPdfPages = new PdfPages(parent.getFrom(), this.document, parent);
/* 393 */             kids.set(j, lastPdfPages.getPdfObject());
/* 394 */             newParents.add(lastPdfPages);
/*     */           }
/*     */           else {
/*     */             
/* 398 */             kids.remove(j);
/* 399 */             j--;
/*     */           } 
/*     */ 
/*     */           
/* 403 */           parent.decrementCount();
/* 404 */           lastPdfPages.addPage(pdfPagesObject);
/* 405 */           kidsCount--;
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 411 */           int from = (lastPdfPages == null) ? parent.getFrom() : (lastPdfPages.getFrom() + lastPdfPages.getCount());
/* 412 */           lastPdfPages = new PdfPages(from, kidsCount, pdfPagesObject, parent);
/* 413 */           newParents.add(lastPdfPages);
/* 414 */           kidsCount -= lastPdfPages.getCount();
/*     */         } 
/*     */       } 
/* 417 */       this.parents.remove(parentIndex);
/* 418 */       for (j = newParents.size() - 1; j >= 0; j--) {
/* 419 */         this.parents.add(parentIndex, newParents.get(j));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 424 */       loadPage(pageNum);
/*     */     } else {
/* 426 */       int from = parent.getFrom();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 431 */       for (int j = 0; j < parent.getCount(); j++) {
/* 432 */         PdfObject kid = kids.get(j, false);
/* 433 */         if (kid instanceof PdfIndirectReference) {
/* 434 */           this.pageRefs.set(from + j, (PdfIndirectReference)kid);
/*     */         } else {
/* 436 */           this.pageRefs.set(from + j, kid.getIndirectReference());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean internalRemovePage(int pageNum) {
/* 445 */     int parentIndex = findPageParent(pageNum);
/* 446 */     PdfPages pdfPages = this.parents.get(parentIndex);
/* 447 */     if (pdfPages.removePage(pageNum)) {
/* 448 */       if (pdfPages.getCount() == 0) {
/* 449 */         this.parents.remove(parentIndex);
/* 450 */         pdfPages.removeFromParent();
/* 451 */         parentIndex--;
/*     */       } 
/* 453 */       if (this.parents.size() == 0) {
/* 454 */         this.root = null;
/* 455 */         this.parents.add(new PdfPages(0, this.document));
/*     */       } else {
/* 457 */         correctPdfPagesFromProperty(parentIndex + 1, -1);
/*     */       } 
/* 459 */       this.pageRefs.remove(pageNum);
/* 460 */       this.pages.remove(pageNum);
/* 461 */       return true;
/*     */     } 
/* 463 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findPageParent(int pageNum) {
/* 469 */     int low = 0;
/* 470 */     int high = this.parents.size() - 1;
/* 471 */     while (low != high) {
/* 472 */       int middle = (low + high + 1) / 2;
/* 473 */       if (((PdfPages)this.parents.get(middle)).compareTo(pageNum) > 0) {
/* 474 */         high = middle - 1; continue;
/*     */       } 
/* 476 */       low = middle;
/*     */     } 
/*     */     
/* 479 */     return low;
/*     */   }
/*     */   
/*     */   private void correctPdfPagesFromProperty(int index, int correction) {
/* 483 */     for (int i = index; i < this.parents.size(); i++) {
/* 484 */       if (this.parents.get(i) != null)
/* 485 */         ((PdfPages)this.parents.get(i)).correctFrom(correction); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfPagesTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */