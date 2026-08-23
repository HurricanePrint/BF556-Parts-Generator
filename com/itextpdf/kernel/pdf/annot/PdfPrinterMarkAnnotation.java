/*    */ package com.itextpdf.kernel.pdf.annot;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Rectangle;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfPrinterMarkAnnotation
/*    */   extends PdfAnnotation
/*    */ {
/*    */   private static final long serialVersionUID = -7709626622860134020L;
/*    */   
/*    */   public PdfPrinterMarkAnnotation(Rectangle rect, PdfFormXObject appearanceStream) {
/* 57 */     super(rect);
/* 58 */     setNormalAppearance((PdfDictionary)appearanceStream.getPdfObject());
/* 59 */     setFlags(68);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected PdfPrinterMarkAnnotation(PdfDictionary pdfObject) {
/* 70 */     super(pdfObject);
/*    */   }
/*    */   
/*    */   public PdfName getSubtype() {
/* 74 */     return PdfName.PrinterMark;
/*    */   }
/*    */   
/*    */   public PdfMarkupAnnotation setArbitraryTypeName(PdfName arbitraryTypeName) {
/* 78 */     return (PdfMarkupAnnotation)put(PdfName.MN, (PdfObject)arbitraryTypeName);
/*    */   }
/*    */   
/*    */   public PdfName getArbitraryTypeName() {
/* 82 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.MN);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfPrinterMarkAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */