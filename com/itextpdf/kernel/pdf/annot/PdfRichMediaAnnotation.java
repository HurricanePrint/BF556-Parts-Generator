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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class PdfRichMediaAnnotation
/*    */   extends PdfAnnotation
/*    */ {
/*    */   private static final long serialVersionUID = 5368329326723025646L;
/*    */   
/*    */   public PdfRichMediaAnnotation(Rectangle rect) {
/* 60 */     super(rect);
/*    */   }
/*    */   
/*    */   public PdfRichMediaAnnotation(PdfDictionary pdfObject) {
/* 64 */     super(pdfObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfName getSubtype() {
/* 69 */     return PdfName.RichMedia;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfRichMediaAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */