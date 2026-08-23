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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfLineAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = -6047928061827404283L;
/*     */   
/*     */   public PdfLineAnnotation(Rectangle rect, float[] line) {
/*  75 */     super(rect);
/*  76 */     put(PdfName.L, (PdfObject)new PdfArray(line));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfLineAnnotation(PdfDictionary pdfObject) {
/*  87 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  95 */     return PdfName.Line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getLine() {
/* 105 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.L);
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
/* 117 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLineAnnotation setBorderStyle(PdfDictionary borderStyle) {
/* 128 */     return (PdfLineAnnotation)put(PdfName.BS, (PdfObject)borderStyle);
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
/*     */   public PdfLineAnnotation setBorderStyle(PdfName style) {
/* 146 */     return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
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
/*     */   public PdfLineAnnotation setDashPattern(PdfArray dashPattern) {
/* 158 */     return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
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
/*     */   public PdfArray getLineEndingStyles() {
/* 170 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.LE);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLineAnnotation setLineEndingStyles(PdfArray lineEndingStyles) {
/* 196 */     return (PdfLineAnnotation)put(PdfName.LE, (PdfObject)lineEndingStyles);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getInteriorColor() {
/* 206 */     return InteriorColorUtil.parseInteriorColor(((PdfDictionary)getPdfObject()).getAsArray(PdfName.IC));
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
/*     */   public PdfLineAnnotation setInteriorColor(PdfArray interiorColor) {
/* 220 */     return (PdfLineAnnotation)put(PdfName.IC, (PdfObject)interiorColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLineAnnotation setInteriorColor(float[] interiorColor) {
/* 231 */     return setInteriorColor(new PdfArray(interiorColor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLeaderLineLength() {
/* 242 */     PdfNumber n = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.LL);
/* 243 */     return (n == null) ? 0.0F : n.floatValue();
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
/*     */   public PdfLineAnnotation setLeaderLineLength(float leaderLineLength) {
/* 255 */     return (PdfLineAnnotation)put(PdfName.LL, (PdfObject)new PdfNumber(leaderLineLength));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLeaderLineExtension() {
/* 265 */     PdfNumber n = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.LLE);
/* 266 */     return (n == null) ? 0.0F : n.floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLineAnnotation setLeaderLineExtension(float leaderLineExtension) {
/* 276 */     return (PdfLineAnnotation)put(PdfName.LLE, (PdfObject)new PdfNumber(leaderLineExtension));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLeaderLineOffset() {
/* 286 */     PdfNumber n = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.LLO);
/* 287 */     return (n == null) ? 0.0F : n.floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLineAnnotation setLeaderLineOffset(float leaderLineOffset) {
/* 297 */     return (PdfLineAnnotation)put(PdfName.LLO, (PdfObject)new PdfNumber(leaderLineOffset));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getContentsAsCaption() {
/* 308 */     PdfBoolean b = ((PdfDictionary)getPdfObject()).getAsBoolean(PdfName.Cap);
/* 309 */     return (b != null && b.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLineAnnotation setContentsAsCaption(boolean contentsAsCaption) {
/* 320 */     return (PdfLineAnnotation)put(PdfName.Cap, (PdfObject)PdfBoolean.valueOf(contentsAsCaption));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getCaptionPosition() {
/* 330 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.CP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLineAnnotation setCaptionPosition(PdfName captionPosition) {
/* 340 */     return (PdfLineAnnotation)put(PdfName.CP, (PdfObject)captionPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getMeasure() {
/* 348 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Measure);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfLineAnnotation setMeasure(PdfDictionary measure) {
/* 358 */     return (PdfLineAnnotation)put(PdfName.Measure, (PdfObject)measure);
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
/*     */   public PdfArray getCaptionOffset() {
/* 371 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.CO);
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
/*     */   public PdfLineAnnotation setCaptionOffset(PdfArray captionOffset) {
/* 384 */     return (PdfLineAnnotation)put(PdfName.CO, (PdfObject)captionOffset);
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
/*     */   public PdfLineAnnotation setCaptionOffset(float[] captionOffset) {
/* 397 */     return setCaptionOffset(new PdfArray(captionOffset));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfLineAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */