/*    */ package com.itextpdf.kernel.pdf.tagging;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
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
/*    */ public class PdfMcrDictionary
/*    */   extends PdfMcr
/*    */ {
/*    */   private static final long serialVersionUID = 3562443854685749324L;
/*    */   
/*    */   public PdfMcrDictionary(PdfDictionary pdfObject, PdfStructElem parent) {
/* 56 */     super((PdfObject)pdfObject, parent);
/*    */   }
/*    */   
/*    */   public PdfMcrDictionary(PdfPage page, PdfStructElem parent) {
/* 60 */     super((PdfObject)new PdfDictionary(), parent);
/* 61 */     PdfDictionary dict = (PdfDictionary)getPdfObject();
/* 62 */     dict.put(PdfName.Type, (PdfObject)PdfName.MCR);
/*    */     
/* 64 */     dict.put(PdfName.Pg, (PdfObject)((PdfDictionary)page.getPdfObject()).getIndirectReference());
/* 65 */     dict.put(PdfName.MCID, (PdfObject)new PdfNumber(page.getNextMcid()));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMcid() {
/* 70 */     PdfNumber mcidNumber = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.MCID);
/* 71 */     return (mcidNumber != null) ? mcidNumber.intValue() : -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfDictionary getPageObject() {
/* 76 */     return super.getPageObject();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfMcrDictionary.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */