/*    */ package com.itextpdf.kernel.pdf.navigation;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
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
/*    */ 
/*    */ public abstract class PdfDestination
/*    */   extends PdfObjectWrapper<PdfObject>
/*    */ {
/*    */   private static final long serialVersionUID = 8102903000978704308L;
/*    */   
/*    */   protected PdfDestination(PdfObject pdfObject) {
/* 61 */     super(pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract PdfObject getDestinationPage(Map<String, PdfObject> paramMap);
/*    */   
/*    */   public static PdfDestination makeDestination(PdfObject pdfObject) {
/* 68 */     if (pdfObject.getType() == 10)
/* 69 */       return new PdfStringDestination((PdfString)pdfObject); 
/* 70 */     if (pdfObject.getType() == 6)
/* 71 */       return new PdfNamedDestination((PdfName)pdfObject); 
/* 72 */     if (pdfObject.getType() == 1) {
/* 73 */       PdfArray destArray = (PdfArray)pdfObject;
/* 74 */       if (destArray.size() == 0) {
/* 75 */         throw new IllegalArgumentException();
/*    */       }
/* 77 */       PdfObject firstObj = destArray.get(0);
/*    */       
/* 79 */       if (firstObj.isNumber()) {
/* 80 */         return new PdfExplicitRemoteGoToDestination(destArray);
/*    */       }
/*    */       
/* 83 */       if (firstObj.isDictionary() && PdfName.Page.equals(((PdfDictionary)firstObj).getAsName(PdfName.Type))) {
/* 84 */         return new PdfExplicitDestination(destArray);
/*    */       }
/*    */       
/* 87 */       return new PdfStructureDestination(destArray);
/*    */     } 
/*    */     
/* 90 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/navigation/PdfDestination.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */