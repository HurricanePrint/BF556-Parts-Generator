/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
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
/*     */ public class Pdf3DAnnotation
/*     */   extends PdfAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 3823509772499230844L;
/*     */   
/*     */   public Pdf3DAnnotation(Rectangle rect, PdfObject artwork) {
/*  59 */     super(rect);
/*  60 */     put(PdfName._3DD, artwork);
/*     */   }
/*     */   
/*     */   public Pdf3DAnnotation(PdfDictionary pdfObject) {
/*  64 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  69 */     return PdfName._3D;
/*     */   }
/*     */   
/*     */   public Pdf3DAnnotation setDefaultInitialView(PdfObject initialView) {
/*  73 */     return (Pdf3DAnnotation)put(PdfName._3DV, initialView);
/*     */   }
/*     */   
/*     */   public PdfObject getDefaultInitialView() {
/*  77 */     return ((PdfDictionary)getPdfObject()).get(PdfName._3DV);
/*     */   }
/*     */   
/*     */   public Pdf3DAnnotation setActivationDictionary(PdfDictionary activationDictionary) {
/*  81 */     return (Pdf3DAnnotation)put(PdfName._3DA, (PdfObject)activationDictionary);
/*     */   }
/*     */   
/*     */   public PdfDictionary getActivationDictionary() {
/*  85 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName._3DA);
/*     */   }
/*     */   
/*     */   public Pdf3DAnnotation setInteractive(boolean interactive) {
/*  89 */     return (Pdf3DAnnotation)put(PdfName._3DI, (PdfObject)PdfBoolean.valueOf(interactive));
/*     */   }
/*     */   
/*     */   public PdfBoolean isInteractive() {
/*  93 */     return ((PdfDictionary)getPdfObject()).getAsBoolean(PdfName._3DI);
/*     */   }
/*     */   
/*     */   public Pdf3DAnnotation setViewBox(Rectangle viewBox) {
/*  97 */     return (Pdf3DAnnotation)put(PdfName._3DB, (PdfObject)new PdfArray(viewBox));
/*     */   }
/*     */   
/*     */   public Rectangle getViewBox() {
/* 101 */     return ((PdfDictionary)getPdfObject()).getAsRectangle(PdfName._3DB);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/Pdf3DAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */