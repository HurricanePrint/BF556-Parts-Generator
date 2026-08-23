/*    */ package com.itextpdf.kernel.pdf.tagging;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfNumber;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
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
/*    */ public class PdfMcrNumber
/*    */   extends PdfMcr
/*    */ {
/*    */   private static final long serialVersionUID = -9039654592261202430L;
/*    */   
/*    */   public PdfMcrNumber(PdfNumber pdfObject, PdfStructElem parent) {
/* 55 */     super((PdfObject)pdfObject, parent);
/*    */   }
/*    */   
/*    */   public PdfMcrNumber(PdfPage page, PdfStructElem parent) {
/* 59 */     super((PdfObject)new PdfNumber(page.getNextMcid()), parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMcid() {
/* 64 */     return ((PdfNumber)getPdfObject()).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfDictionary getPageObject() {
/* 69 */     return super.getPageObject();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfMcrNumber.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */