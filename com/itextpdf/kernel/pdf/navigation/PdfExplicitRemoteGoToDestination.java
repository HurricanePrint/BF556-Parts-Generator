/*     */ package com.itextpdf.kernel.pdf.navigation;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfExplicitRemoteGoToDestination
/*     */   extends PdfDestination
/*     */ {
/*     */   private static final long serialVersionUID = 5354781072160968173L;
/*     */   
/*     */   public PdfExplicitRemoteGoToDestination() {
/*  62 */     this(new PdfArray());
/*     */   }
/*     */   
/*     */   public PdfExplicitRemoteGoToDestination(PdfArray pdfObject) {
/*  66 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfObject getDestinationPage(Map<String, PdfObject> names) {
/*  71 */     return ((PdfArray)getPdfObject()).get(0);
/*     */   }
/*     */   public static PdfExplicitRemoteGoToDestination createXYZ(int pageNum, float left, float top, float zoom) {
/*  74 */     return create(pageNum, PdfName.XYZ, left, Float.NaN, Float.NaN, top, zoom);
/*     */   }
/*     */   
/*     */   public static PdfExplicitRemoteGoToDestination createFit(int pageNum) {
/*  78 */     return create(pageNum, PdfName.Fit, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitRemoteGoToDestination createFitH(int pageNum, float top) {
/*  82 */     return create(pageNum, PdfName.FitH, Float.NaN, Float.NaN, Float.NaN, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitRemoteGoToDestination createFitV(int pageNum, float left) {
/*  86 */     return create(pageNum, PdfName.FitV, left, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitRemoteGoToDestination createFitR(int pageNum, float left, float bottom, float right, float top) {
/*  90 */     return create(pageNum, PdfName.FitR, left, bottom, right, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitRemoteGoToDestination createFitB(int pageNum) {
/*  94 */     return create(pageNum, PdfName.FitB, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitRemoteGoToDestination createFitBH(int pageNum, float top) {
/*  98 */     return create(pageNum, PdfName.FitBH, Float.NaN, Float.NaN, Float.NaN, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitRemoteGoToDestination createFitBV(int pageNum, float left) {
/* 102 */     return create(pageNum, PdfName.FitBH, left, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitRemoteGoToDestination create(int pageNum, PdfName type, float left, float bottom, float right, float top, float zoom) {
/* 106 */     return (new PdfExplicitRemoteGoToDestination()).add(--pageNum).add(type).add(left).add(bottom).add(right).add(top).add(zoom);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   private PdfExplicitRemoteGoToDestination add(float value) {
/* 115 */     if (!Float.isNaN(value)) {
/* 116 */       ((PdfArray)getPdfObject()).add((PdfObject)new PdfNumber(value));
/*     */     }
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   private PdfExplicitRemoteGoToDestination add(int value) {
/* 122 */     ((PdfArray)getPdfObject()).add((PdfObject)new PdfNumber(value));
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   private PdfExplicitRemoteGoToDestination add(PdfName type) {
/* 127 */     ((PdfArray)getPdfObject()).add((PdfObject)type);
/* 128 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/navigation/PdfExplicitRemoteGoToDestination.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */