/*     */ package com.itextpdf.kernel.pdf.action;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfWin
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -3057526285278565800L;
/*     */   
/*     */   public PdfWin(PdfDictionary pdfObject) {
/*  64 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWin(PdfString f) {
/*  75 */     this(new PdfDictionary());
/*  76 */     ((PdfDictionary)getPdfObject()).put(PdfName.F, (PdfObject)f);
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
/*     */   public PdfWin(PdfString f, PdfString d, PdfString o, PdfString p) {
/*  92 */     this(new PdfDictionary());
/*  93 */     ((PdfDictionary)getPdfObject()).put(PdfName.F, (PdfObject)f);
/*  94 */     ((PdfDictionary)getPdfObject()).put(PdfName.D, (PdfObject)d);
/*  95 */     ((PdfDictionary)getPdfObject()).put(PdfName.O, (PdfObject)o);
/*  96 */     ((PdfDictionary)getPdfObject()).put(PdfName.P, (PdfObject)p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/action/PdfWin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */