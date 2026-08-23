/*     */ package com.itextpdf.forms.fields;
/*     */ 
/*     */ import com.itextpdf.forms.PdfSigFieldLock;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfSignatureFormField
/*     */   extends PdfFormField
/*     */ {
/*     */   protected PdfSignatureFormField(PdfDocument pdfDocument) {
/*  60 */     super(pdfDocument);
/*     */   }
/*     */   
/*     */   protected PdfSignatureFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
/*  64 */     super(widget, pdfDocument);
/*     */   }
/*     */   
/*     */   protected PdfSignatureFormField(PdfDictionary pdfObject) {
/*  68 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getFormType() {
/*  78 */     return PdfName.Sig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureFormField setValue(PdfObject value) {
/*  88 */     return (PdfSignatureFormField)put(PdfName.V, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSigFieldLock getSigFieldLockDictionary() {
/*  99 */     PdfDictionary sigLockDict = (PdfDictionary)((PdfDictionary)getPdfObject()).get(PdfName.Lock);
/* 100 */     return (sigLockDict == null) ? null : new PdfSigFieldLock(sigLockDict);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/fields/PdfSignatureFormField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */