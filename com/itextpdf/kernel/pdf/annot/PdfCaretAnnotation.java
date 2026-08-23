/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfCaretAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = 1542932123958535397L;
/*     */   
/*     */   public PdfCaretAnnotation(Rectangle rect) {
/*  58 */     super(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfCaretAnnotation(PdfDictionary pdfObject) {
/*  69 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  74 */     return PdfName.Caret;
/*     */   }
/*     */   
/*     */   public PdfCaretAnnotation setSymbol(PdfString symbol) {
/*  78 */     return (PdfCaretAnnotation)put(PdfName.Sy, (PdfObject)symbol);
/*     */   }
/*     */   
/*     */   public PdfString getSymbol() {
/*  82 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.Sy);
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
/*  94 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.RD);
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
/*     */   public PdfCaretAnnotation setRectangleDifferences(PdfArray rect) {
/* 109 */     return (PdfCaretAnnotation)put(PdfName.RD, (PdfObject)rect);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfCaretAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */