/*     */ package com.itextpdf.kernel.pdf.navigation;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfExplicitDestination
/*     */   extends PdfDestination
/*     */ {
/*     */   private static final long serialVersionUID = -1515785642472963298L;
/*     */   
/*     */   public PdfExplicitDestination() {
/*  69 */     this(new PdfArray());
/*     */   }
/*     */   
/*     */   public PdfExplicitDestination(PdfArray pdfObject) {
/*  73 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfObject getDestinationPage(Map<String, PdfObject> names) {
/*  78 */     return ((PdfArray)getPdfObject()).get(0);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination createXYZ(PdfPage page, float left, float top, float zoom) {
/*  82 */     return create(page, PdfName.XYZ, left, Float.NaN, Float.NaN, top, zoom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination createXYZ(int pageNum, float left, float top, float zoom) {
/*  90 */     return create(pageNum, PdfName.XYZ, left, Float.NaN, Float.NaN, top, zoom);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination createFit(PdfPage page) {
/*  94 */     return create(page, PdfName.Fit, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination createFit(int pageNum) {
/* 102 */     return create(pageNum, PdfName.Fit, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination createFitH(PdfPage page, float top) {
/* 106 */     return create(page, PdfName.FitH, Float.NaN, Float.NaN, Float.NaN, top, Float.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination createFitH(int pageNum, float top) {
/* 114 */     return create(pageNum, PdfName.FitH, Float.NaN, Float.NaN, Float.NaN, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination createFitV(PdfPage page, float left) {
/* 118 */     return create(page, PdfName.FitV, left, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination createFitV(int pageNum, float left) {
/* 126 */     return create(pageNum, PdfName.FitV, left, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination createFitR(PdfPage page, float left, float bottom, float right, float top) {
/* 130 */     return create(page, PdfName.FitR, left, bottom, right, top, Float.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination createFitR(int pageNum, float left, float bottom, float right, float top) {
/* 138 */     return create(pageNum, PdfName.FitR, left, bottom, right, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination createFitB(PdfPage page) {
/* 142 */     return create(page, PdfName.FitB, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination createFitB(int pageNum) {
/* 150 */     return create(pageNum, PdfName.FitB, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination createFitBH(PdfPage page, float top) {
/* 154 */     return create(page, PdfName.FitBH, Float.NaN, Float.NaN, Float.NaN, top, Float.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination createFitBH(int pageNum, float top) {
/* 162 */     return create(pageNum, PdfName.FitBH, Float.NaN, Float.NaN, Float.NaN, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination createFitBV(PdfPage page, float left) {
/* 166 */     return create(page, PdfName.FitBV, left, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination createFitBV(int pageNum, float left) {
/* 174 */     return create(pageNum, PdfName.FitBV, left, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfExplicitDestination create(PdfPage page, PdfName type, float left, float bottom, float right, float top, float zoom) {
/* 178 */     return (new PdfExplicitDestination()).add(page).add(type).add(left).add(bottom).add(right).add(top).add(zoom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PdfExplicitDestination create(int pageNum, PdfName type, float left, float bottom, float right, float top, float zoom) {
/* 186 */     return (new PdfExplicitDestination()).add(--pageNum).add(type).add(left).add(bottom).add(right).add(top).add(zoom);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 191 */     return false;
/*     */   }
/*     */   
/*     */   private PdfExplicitDestination add(float value) {
/* 195 */     if (!Float.isNaN(value)) {
/* 196 */       ((PdfArray)getPdfObject()).add((PdfObject)new PdfNumber(value));
/*     */     }
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   private PdfExplicitDestination add(int value) {
/* 202 */     ((PdfArray)getPdfObject()).add((PdfObject)new PdfNumber(value));
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private PdfExplicitDestination add(PdfPage page) {
/* 208 */     ((PdfArray)getPdfObject()).add((PdfObject)((PdfDictionary)page.getPdfObject()).getIndirectReference());
/* 209 */     return this;
/*     */   }
/*     */   
/*     */   private PdfExplicitDestination add(PdfName type) {
/* 213 */     ((PdfArray)getPdfObject()).add((PdfObject)type);
/* 214 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/navigation/PdfExplicitDestination.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */