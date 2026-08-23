/*    */ package com.itextpdf.pdfa;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.PageSize;
/*    */ import com.itextpdf.kernel.pdf.IPdfPageFactory;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfDocument;
/*    */ import com.itextpdf.kernel.pdf.PdfPage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PdfAPageFactory
/*    */   implements IPdfPageFactory
/*    */ {
/*    */   public PdfPage createPdfPage(PdfDictionary pdfObject) {
/* 55 */     return new PdfAPage(pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfPage createPdfPage(PdfDocument pdfDocument, PageSize pageSize) {
/* 60 */     return new PdfAPage(pdfDocument, pageSize);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/pdfa/PdfAPageFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */