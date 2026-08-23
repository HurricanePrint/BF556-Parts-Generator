/*    */ package com.itextpdf.pdfa;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.PageSize;
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
/*    */ class PdfAPage
/*    */   extends PdfPage
/*    */ {
/*    */   PdfAPage(PdfDocument pdfDocument, PageSize pageSize) {
/* 53 */     super(pdfDocument, pageSize);
/*    */   }
/*    */   
/*    */   PdfAPage(PdfDictionary pdfObject) {
/* 57 */     super(pdfObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush(boolean flushResourcesContentStreams) {
/* 65 */     if (flushResourcesContentStreams || ((PdfADocument)
/* 66 */       getDocument()).isClosing() || ((PdfADocument)
/* 67 */       getDocument()).checker.objectIsChecked(getPdfObject())) {
/* 68 */       super.flush(flushResourcesContentStreams);
/*    */     } else {
/* 70 */       ((PdfADocument)getDocument()).logThatPdfAPageFlushingWasNotPerformed();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/pdfa/PdfAPage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */