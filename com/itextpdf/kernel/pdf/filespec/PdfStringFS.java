/*    */ package com.itextpdf.kernel.pdf.filespec;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.PdfString;
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
/*    */ public class PdfStringFS
/*    */   extends PdfFileSpec
/*    */ {
/*    */   private static final long serialVersionUID = 3440302276954369264L;
/*    */   
/*    */   public PdfStringFS(String string) {
/* 53 */     super((PdfObject)new PdfString(string));
/*    */   }
/*    */   
/*    */   public PdfStringFS(PdfString pdfObject) {
/* 57 */     super((PdfObject)pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 62 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filespec/PdfStringFS.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */