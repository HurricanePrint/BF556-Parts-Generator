/*    */ package com.itextpdf.signatures;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfSignatureApp
/*    */   extends PdfObjectWrapper<PdfDictionary>
/*    */ {
/*    */   public PdfSignatureApp() {
/* 59 */     super((PdfObject)new PdfDictionary());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PdfSignatureApp(PdfDictionary pdfObject) {
/* 68 */     super((PdfObject)pdfObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSignatureCreator(String name) {
/* 78 */     ((PdfDictionary)getPdfObject()).put(PdfName.Name, (PdfObject)new PdfName(name));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/PdfSignatureApp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */