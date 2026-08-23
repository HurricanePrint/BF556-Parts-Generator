/*    */ package com.itextpdf.kernel.pdf.navigation;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
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
/*    */ public class PdfNamedDestination
/*    */   extends PdfDestination
/*    */ {
/*    */   private static final long serialVersionUID = 5285810255133676086L;
/*    */   
/*    */   public PdfNamedDestination(String name) {
/* 57 */     this(new PdfName(name));
/*    */   }
/*    */   
/*    */   public PdfNamedDestination(PdfName pdfObject) {
/* 61 */     super((PdfObject)pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfObject getDestinationPage(Map<String, PdfObject> names) {
/* 66 */     PdfArray array = (PdfArray)names.get(((PdfName)getPdfObject()).getValue());
/* 67 */     return (array != null) ? array.get(0) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/navigation/PdfNamedDestination.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */