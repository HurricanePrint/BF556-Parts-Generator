/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfScreenAnnotation
/*     */   extends PdfAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 1334399136151450493L;
/*     */   
/*     */   public PdfScreenAnnotation(Rectangle rect) {
/*  57 */     super(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfScreenAnnotation(PdfDictionary pdfObject) {
/*  68 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  73 */     return PdfName.Screen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getAction() {
/*  82 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.A);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfScreenAnnotation setAction(PdfAction action) {
/*  91 */     return (PdfScreenAnnotation)put(PdfName.A, action.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getAdditionalAction() {
/* 101 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.AA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfScreenAnnotation setAdditionalAction(PdfName key, PdfAction action) {
/* 112 */     PdfAction.setAdditionalAction(this, key, action);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getAppearanceCharacteristics() {
/* 123 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.MK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfScreenAnnotation setAppearanceCharacteristics(PdfDictionary characteristics) {
/* 134 */     return (PdfScreenAnnotation)put(PdfName.MK, (PdfObject)characteristics);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfScreenAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */