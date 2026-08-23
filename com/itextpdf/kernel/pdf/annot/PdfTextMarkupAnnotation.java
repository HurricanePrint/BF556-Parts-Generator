/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
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
/*     */ public class PdfTextMarkupAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 2189266742204503217L;
/*  58 */   public static final PdfName MarkupHighlight = PdfName.Highlight;
/*  59 */   public static final PdfName MarkupUnderline = PdfName.Underline;
/*  60 */   public static final PdfName MarkupStrikeout = PdfName.StrikeOut;
/*  61 */   public static final PdfName MarkupSquiggly = PdfName.Squiggly;
/*     */   
/*     */   public PdfTextMarkupAnnotation(Rectangle rect, PdfName subtype, float[] quadPoints) {
/*  64 */     super(rect);
/*  65 */     put(PdfName.Subtype, (PdfObject)subtype);
/*  66 */     setQuadPoints(new PdfArray(quadPoints));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfTextMarkupAnnotation(PdfDictionary pdfObject) {
/*  77 */     super(pdfObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTextMarkupAnnotation createHighLight(Rectangle rect, float[] quadPoints) {
/*  99 */     return new PdfTextMarkupAnnotation(rect, MarkupHighlight, quadPoints);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTextMarkupAnnotation createUnderline(Rectangle rect, float[] quadPoints) {
/* 121 */     return new PdfTextMarkupAnnotation(rect, MarkupUnderline, quadPoints);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTextMarkupAnnotation createStrikeout(Rectangle rect, float[] quadPoints) {
/* 143 */     return new PdfTextMarkupAnnotation(rect, MarkupStrikeout, quadPoints);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfTextMarkupAnnotation createSquiggly(Rectangle rect, float[] quadPoints) {
/* 165 */     return new PdfTextMarkupAnnotation(rect, MarkupSquiggly, quadPoints);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/* 170 */     PdfName subType = ((PdfDictionary)getPdfObject()).getAsName(PdfName.Subtype);
/* 171 */     if (subType == null) {
/* 172 */       subType = PdfName.Underline;
/*     */     }
/* 174 */     return subType;
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
/*     */   
/*     */   public PdfArray getQuadPoints() {
/* 193 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.QuadPoints);
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
/*     */ 
/*     */   
/*     */   public PdfTextMarkupAnnotation setQuadPoints(PdfArray quadPoints) {
/* 213 */     return (PdfTextMarkupAnnotation)put(PdfName.QuadPoints, (PdfObject)quadPoints);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfTextMarkupAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */