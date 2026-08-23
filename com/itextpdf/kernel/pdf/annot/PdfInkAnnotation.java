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
/*     */ public class PdfInkAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 9110158515957708155L;
/*     */   
/*     */   public PdfInkAnnotation(Rectangle rect) {
/*  57 */     super(rect);
/*     */   }
/*     */   
/*     */   public PdfInkAnnotation(Rectangle rect, PdfArray inkList) {
/*  61 */     this(rect);
/*  62 */     put(PdfName.InkList, (PdfObject)inkList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfInkAnnotation(PdfDictionary pdfObject) {
/*  73 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  78 */     return PdfName.Ink;
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
/*  90 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.BS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfInkAnnotation setBorderStyle(PdfDictionary borderStyle) {
/* 101 */     return (PdfInkAnnotation)put(PdfName.BS, (PdfObject)borderStyle);
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
/*     */   public PdfInkAnnotation setBorderStyle(PdfName style) {
/* 119 */     return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
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
/*     */   public PdfInkAnnotation setDashPattern(PdfArray dashPattern) {
/* 131 */     return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfInkAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */