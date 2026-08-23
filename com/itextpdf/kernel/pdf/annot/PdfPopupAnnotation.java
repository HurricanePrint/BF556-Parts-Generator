/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
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
/*     */ public class PdfPopupAnnotation
/*     */   extends PdfAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = -8892617787951569855L;
/*     */   protected PdfAnnotation parent;
/*     */   
/*     */   public PdfPopupAnnotation(Rectangle rect) {
/*  59 */     super(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfPopupAnnotation(PdfDictionary pdfObject) {
/*  70 */     super(pdfObject);
/*     */   }
/*     */   
/*     */   public PdfName getSubtype() {
/*  74 */     return PdfName.Popup;
/*     */   }
/*     */   
/*     */   public PdfDictionary getParentObject() {
/*  78 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Parent);
/*     */   }
/*     */   
/*     */   public PdfAnnotation getParent() {
/*  82 */     if (this.parent == null) {
/*  83 */       this.parent = makeAnnotation((PdfObject)getParentObject());
/*     */     }
/*  85 */     return this.parent;
/*     */   }
/*     */   
/*     */   public PdfPopupAnnotation setParent(PdfAnnotation parent) {
/*  89 */     this.parent = parent;
/*  90 */     return (PdfPopupAnnotation)put(PdfName.Parent, parent.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getOpen() {
/*  99 */     return PdfBoolean.TRUE.equals(((PdfDictionary)getPdfObject()).getAsBoolean(PdfName.Open));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPopupAnnotation setOpen(boolean open) {
/* 109 */     return (PdfPopupAnnotation)put(PdfName.Open, (PdfObject)PdfBoolean.valueOf(open));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfPopupAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */