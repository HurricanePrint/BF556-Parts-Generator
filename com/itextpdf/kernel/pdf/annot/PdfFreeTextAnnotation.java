/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.annot.da.AnnotationDefaultAppearance;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfFreeTextAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = -7835504102518915220L;
/*     */   public static final int LEFT_JUSTIFIED = 0;
/*     */   public static final int CENTERED = 1;
/*     */   public static final int RIGHT_JUSTIFIED = 2;
/*     */   
/*     */   public PdfFreeTextAnnotation(Rectangle rect, PdfString contents) {
/*  72 */     super(rect);
/*  73 */     setContents(contents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfFreeTextAnnotation(PdfDictionary pdfObject) {
/*  84 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  89 */     return PdfName.FreeText;
/*     */   }
/*     */   
/*     */   public PdfString getDefaultStyleString() {
/*  93 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.DS);
/*     */   }
/*     */   
/*     */   public PdfFreeTextAnnotation setDefaultStyleString(PdfString defaultStyleString) {
/*  97 */     return (PdfFreeTextAnnotation)put(PdfName.DS, (PdfObject)defaultStyleString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getDefaultAppearance() {
/* 105 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.DA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFreeTextAnnotation setDefaultAppearance(PdfString appearanceString) {
/* 114 */     return (PdfFreeTextAnnotation)put(PdfName.DA, (PdfObject)appearanceString);
/*     */   }
/*     */   
/*     */   public PdfFreeTextAnnotation setDefaultAppearance(AnnotationDefaultAppearance da) {
/* 118 */     return setDefaultAppearance(da.toPdfString());
/*     */   }
/*     */   
/*     */   public PdfArray getCalloutLine() {
/* 122 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.CL);
/*     */   }
/*     */   
/*     */   public PdfFreeTextAnnotation setCalloutLine(float[] calloutLine) {
/* 126 */     return setCalloutLine(new PdfArray(calloutLine));
/*     */   }
/*     */   
/*     */   public PdfFreeTextAnnotation setCalloutLine(PdfArray calloutLine) {
/* 130 */     return (PdfFreeTextAnnotation)put(PdfName.CL, (PdfObject)calloutLine);
/*     */   }
/*     */   
/*     */   public PdfName getLineEndingStyle() {
/* 134 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.LE);
/*     */   }
/*     */   
/*     */   public PdfFreeTextAnnotation setLineEndingStyle(PdfName lineEndingStyle) {
/* 138 */     return (PdfFreeTextAnnotation)put(PdfName.LE, (PdfObject)lineEndingStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getJustification() {
/* 147 */     PdfNumber q = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.Q);
/* 148 */     return (q == null) ? 0 : q.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFreeTextAnnotation setJustification(int justification) {
/* 158 */     return (PdfFreeTextAnnotation)put(PdfName.Q, (PdfObject)new PdfNumber(justification));
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
/* 170 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFreeTextAnnotation setBorderStyle(PdfDictionary borderStyle) {
/* 181 */     return (PdfFreeTextAnnotation)put(PdfName.BS, (PdfObject)borderStyle);
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
/*     */   public PdfFreeTextAnnotation setBorderStyle(PdfName style) {
/* 199 */     return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
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
/*     */   public PdfFreeTextAnnotation setDashPattern(PdfArray dashPattern) {
/* 211 */     return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
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
/* 223 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.RD);
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
/*     */   public PdfFreeTextAnnotation setRectangleDifferences(PdfArray rect) {
/* 238 */     return (PdfFreeTextAnnotation)put(PdfName.RD, (PdfObject)rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getBorderEffect() {
/* 247 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFreeTextAnnotation setBorderEffect(PdfDictionary borderEffect) {
/* 257 */     return (PdfFreeTextAnnotation)put(PdfName.BE, (PdfObject)borderEffect);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfFreeTextAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */