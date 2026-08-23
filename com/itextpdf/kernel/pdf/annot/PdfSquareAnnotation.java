/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfSquareAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 5577194318058336359L;
/*     */   
/*     */   public PdfSquareAnnotation(Rectangle rect) {
/*  61 */     super(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfSquareAnnotation(PdfDictionary pdfObject) {
/*  72 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  77 */     return PdfName.Square;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getBorderStyle() {
/*  89 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSquareAnnotation setBorderStyle(PdfDictionary borderStyle) {
/* 100 */     return (PdfSquareAnnotation)put(PdfName.BS, (PdfObject)borderStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSquareAnnotation setBorderStyle(PdfName style) {
/* 118 */     return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSquareAnnotation setDashPattern(PdfArray dashPattern) {
/* 130 */     return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getRectangleDifferences() {
/* 142 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.RD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSquareAnnotation setRectangleDifferences(PdfArray rect) {
/* 157 */     return (PdfSquareAnnotation)put(PdfName.RD, (PdfObject)rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getBorderEffect() {
/* 165 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSquareAnnotation setBorderEffect(PdfDictionary borderEffect) {
/* 175 */     return (PdfSquareAnnotation)put(PdfName.BE, (PdfObject)borderEffect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getInteriorColor() {
/* 185 */     return InteriorColorUtil.parseInteriorColor(((PdfDictionary)getPdfObject()).getAsArray(PdfName.IC));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSquareAnnotation setInteriorColor(PdfArray interiorColor) {
/* 199 */     return (PdfSquareAnnotation)put(PdfName.IC, (PdfObject)interiorColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSquareAnnotation setInteriorColor(float[] interiorColor) {
/* 210 */     return setInteriorColor(new PdfArray(interiorColor));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfSquareAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */