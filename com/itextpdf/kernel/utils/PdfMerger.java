/*     */ package com.itextpdf.kernel.utils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfMerger
/*     */ {
/*     */   private PdfDocument pdfDocument;
/*     */   private boolean closeSrcDocuments;
/*     */   private boolean mergeTags;
/*     */   private boolean mergeOutlines;
/*     */   
/*     */   public PdfMerger(PdfDocument pdfDocument) {
/*  66 */     this(pdfDocument, true, true);
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
/*     */   public PdfMerger(PdfDocument pdfDocument, boolean mergeTags, boolean mergeOutlines) {
/*  81 */     this.pdfDocument = pdfDocument;
/*  82 */     this.mergeTags = mergeTags;
/*  83 */     this.mergeOutlines = mergeOutlines;
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
/*     */   public PdfMerger setCloseSourceDocuments(boolean closeSourceDocuments) {
/*  95 */     this.closeSrcDocuments = closeSourceDocuments;
/*  96 */     return this;
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
/*     */   
/*     */   public PdfMerger merge(PdfDocument from, int fromPage, int toPage) {
/* 113 */     List<Integer> pages = new ArrayList<>(toPage - fromPage);
/* 114 */     for (int pageNum = fromPage; pageNum <= toPage; pageNum++) {
/* 115 */       pages.add(Integer.valueOf(pageNum));
/*     */     }
/* 117 */     return merge(from, pages);
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
/*     */   public PdfMerger merge(PdfDocument from, List<Integer> pages) {
/* 133 */     if (this.mergeTags && from.isTagged()) {
/* 134 */       this.pdfDocument.setTagged();
/*     */     }
/* 136 */     if (this.mergeOutlines && from.hasOutlines()) {
/* 137 */       this.pdfDocument.initializeOutlines();
/*     */     }
/*     */     
/* 140 */     from.copyPagesTo(pages, this.pdfDocument);
/* 141 */     if (this.closeSrcDocuments) {
/* 142 */       from.close();
/*     */     }
/* 144 */     return this;
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
/*     */   public void close() {
/* 156 */     this.pdfDocument.close();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/utils/PdfMerger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */