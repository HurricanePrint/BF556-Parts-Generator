/*     */ package com.itextpdf.kernel.events;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfDocumentEvent
/*     */   extends Event
/*     */ {
/*     */   public static final String START_PAGE = "StartPdfPage";
/*     */   public static final String INSERT_PAGE = "InsertPdfPage";
/*     */   public static final String REMOVE_PAGE = "RemovePdfPage";
/*     */   public static final String END_PAGE = "EndPdfPage";
/*     */   protected PdfPage page;
/*     */   private PdfDocument document;
/*     */   
/*     */   public PdfDocumentEvent(String type, PdfDocument document) {
/*  93 */     super(type);
/*  94 */     this.document = document;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDocumentEvent(String type, PdfPage page) {
/* 104 */     super(type);
/* 105 */     this.page = page;
/* 106 */     this.document = page.getDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDocument getDocument() {
/* 115 */     return this.document;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPage getPage() {
/* 124 */     return this.page;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/events/PdfDocumentEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */