/*    */ package com.itextpdf.layout.property;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum BlendMode
/*    */ {
/* 55 */   NORMAL(PdfExtGState.BM_NORMAL),
/* 56 */   MULTIPLY(PdfExtGState.BM_MULTIPLY),
/* 57 */   SCREEN(PdfExtGState.BM_SCREEN),
/* 58 */   OVERLAY(PdfExtGState.BM_OVERLAY),
/* 59 */   DARKEN(PdfExtGState.BM_DARKEN),
/* 60 */   LIGHTEN(PdfExtGState.BM_LIGHTEN),
/* 61 */   COLOR_DODGE(PdfExtGState.BM_COLOR_DODGE),
/* 62 */   COLOR_BURN(PdfExtGState.BM_COLOR_BURN),
/* 63 */   HARD_LIGHT(PdfExtGState.BM_HARD_LIGHT),
/* 64 */   SOFT_LIGHT(PdfExtGState.BM_SOFT_LIGHT),
/* 65 */   DIFFERENCE(PdfExtGState.BM_DIFFERENCE),
/* 66 */   EXCLUSION(PdfExtGState.BM_EXCLUSION),
/*    */   
/* 68 */   HUE(PdfExtGState.BM_HUE),
/* 69 */   SATURATION(PdfExtGState.BM_SATURATION),
/* 70 */   COLOR(PdfExtGState.BM_COLOR),
/* 71 */   LUMINOSITY(PdfExtGState.BM_LUMINOSITY);
/*    */   
/*    */   private final PdfName pdfRepresentation;
/*    */   
/*    */   BlendMode(PdfName pdfRepresentation) {
/* 76 */     this.pdfRepresentation = pdfRepresentation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PdfName getPdfRepresentation() {
/* 85 */     return this.pdfRepresentation;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/BlendMode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */