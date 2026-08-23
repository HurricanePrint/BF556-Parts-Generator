/*    */ package com.itextpdf.kernel.pdf.navigation;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.PdfString;
/*    */ import java.util.Map;
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
/*    */ public class PdfStringDestination
/*    */   extends PdfDestination
/*    */ {
/*    */   private static final long serialVersionUID = -5949596673571485743L;
/*    */   
/*    */   public PdfStringDestination(String string) {
/* 57 */     this(new PdfString(string));
/*    */   }
/*    */   
/*    */   public PdfStringDestination(PdfString pdfObject) {
/* 61 */     super((PdfObject)pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfObject getDestinationPage(Map<String, PdfObject> names) {
/* 66 */     PdfArray array = (PdfArray)names.get(((PdfString)getPdfObject()).toUnicodeString());
/*    */     
/* 68 */     return (array != null) ? array.get(0) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/navigation/PdfStringDestination.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */