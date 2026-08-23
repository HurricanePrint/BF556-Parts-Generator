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
/*    */ 
/*    */ 
/*    */ public class PdfWatermarkAnnotation
/*    */   extends PdfAnnotation
/*    */ {
/*    */   private static final long serialVersionUID = -4490286782196827176L;
/*    */   
/*    */   public PdfWatermarkAnnotation(Rectangle rect) {
/* 56 */     super(rect);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected PdfWatermarkAnnotation(PdfDictionary pdfObject) {
/* 67 */     super(pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfName getSubtype() {
/* 72 */     return PdfName.Watermark;
/*    */   }
/*    */   
/*    */   public PdfWatermarkAnnotation setFixedPrint(PdfFixedPrint fixedPrint) {
/* 76 */     return (PdfWatermarkAnnotation)put(PdfName.FixedPrint, fixedPrint.getPdfObject());
/*    */   }
/*    */   
/*    */   public PdfDictionary getFixedPrint() {
/* 80 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.FixedPrint);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfWatermarkAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */