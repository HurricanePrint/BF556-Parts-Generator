/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
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
/*     */ public class PdfRedactAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 8488431772407790511L;
/*     */   
/*     */   public PdfRedactAnnotation(Rectangle rect) {
/*  66 */     super(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfRedactAnnotation(PdfDictionary pdfObject) {
/*  77 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  82 */     return PdfName.Redact;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getDefaultAppearance() {
/*  90 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.DA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfRedactAnnotation setDefaultAppearance(PdfString appearanceString) {
/*  99 */     return (PdfRedactAnnotation)put(PdfName.DA, (PdfObject)appearanceString);
/*     */   }
/*     */   
/*     */   public PdfRedactAnnotation setDefaultAppearance(AnnotationDefaultAppearance da) {
/* 103 */     return setDefaultAppearance(da.toPdfString());
/*     */   }
/*     */   
/*     */   public PdfRedactAnnotation setOverlayText(PdfString text) {
/* 107 */     return (PdfRedactAnnotation)put(PdfName.OverlayText, (PdfObject)text);
/*     */   }
/*     */   
/*     */   public PdfString getOverlayText() {
/* 111 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.OverlayText);
/*     */   }
/*     */   
/*     */   public PdfRedactAnnotation setRedactRolloverAppearance(PdfStream stream) {
/* 115 */     return (PdfRedactAnnotation)put(PdfName.RO, (PdfObject)stream);
/*     */   }
/*     */   
/*     */   public PdfStream getRedactRolloverAppearance() {
/* 119 */     return ((PdfDictionary)getPdfObject()).getAsStream(PdfName.RO);
/*     */   }
/*     */   
/*     */   public PdfRedactAnnotation setRepeat(PdfBoolean repeat) {
/* 123 */     return (PdfRedactAnnotation)put(PdfName.Repeat, (PdfObject)repeat);
/*     */   }
/*     */   
/*     */   public PdfBoolean getRepeat() {
/* 127 */     return ((PdfDictionary)getPdfObject()).getAsBoolean(PdfName.Repeat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getQuadPoints() {
/* 137 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.QuadPoints);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfRedactAnnotation setQuadPoints(PdfArray quadPoints) {
/* 148 */     return (PdfRedactAnnotation)put(PdfName.QuadPoints, (PdfObject)quadPoints);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getInteriorColor() {
/* 158 */     return InteriorColorUtil.parseInteriorColor(((PdfDictionary)getPdfObject()).getAsArray(PdfName.IC));
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
/*     */   public PdfRedactAnnotation setInteriorColor(PdfArray interiorColor) {
/* 172 */     return (PdfRedactAnnotation)put(PdfName.IC, (PdfObject)interiorColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfRedactAnnotation setInteriorColor(float[] interiorColor) {
/* 183 */     return setInteriorColor(new PdfArray(interiorColor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getJustification() {
/* 192 */     PdfNumber q = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.Q);
/* 193 */     return (q == null) ? 0 : q.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfRedactAnnotation setJustification(int justification) {
/* 203 */     return (PdfRedactAnnotation)put(PdfName.Q, (PdfObject)new PdfNumber(justification));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfRedactAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */