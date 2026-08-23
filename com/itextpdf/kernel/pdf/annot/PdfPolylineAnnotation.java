/*    */ package com.itextpdf.kernel.pdf.annot;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Rectangle;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PdfPolylineAnnotation
/*    */   extends PdfPolyGeomAnnotation
/*    */ {
/*    */   PdfPolylineAnnotation(Rectangle rect, float[] vertices) {
/* 52 */     super(rect, vertices);
/*    */   }
/*    */   
/*    */   PdfPolylineAnnotation(PdfDictionary pdfObject) {
/* 56 */     super(pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfName getSubtype() {
/* 61 */     return PdfName.PolyLine;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfPolylineAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */