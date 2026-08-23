/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.PageSize;
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
/*    */ class PdfPageFactory
/*    */   implements IPdfPageFactory
/*    */ {
/*    */   public PdfPage createPdfPage(PdfDictionary pdfObject) {
/* 51 */     return new PdfPage(pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfPage createPdfPage(PdfDocument pdfDocument, PageSize pageSize) {
/* 56 */     return new PdfPage(pdfDocument, pageSize);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfPageFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */