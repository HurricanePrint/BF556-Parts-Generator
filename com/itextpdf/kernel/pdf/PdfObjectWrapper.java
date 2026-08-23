/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PdfObjectWrapper<T extends PdfObject>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3516473712028588356L;
/*  54 */   private T pdfObject = null;
/*     */   
/*     */   protected PdfObjectWrapper(T pdfObject) {
/*  57 */     this.pdfObject = pdfObject;
/*  58 */     if (isWrappedObjectMustBeIndirect()) {
/*  59 */       markObjectAsIndirect((PdfObject)this.pdfObject);
/*     */     }
/*     */   }
/*     */   
/*     */   public T getPdfObject() {
/*  64 */     return this.pdfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObjectWrapper<T> makeIndirect(PdfDocument document, PdfIndirectReference reference) {
/*  75 */     getPdfObject().makeIndirect(document, reference);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObjectWrapper<T> makeIndirect(PdfDocument document) {
/*  86 */     return makeIndirect(document, null);
/*     */   }
/*     */   
/*     */   public PdfObjectWrapper<T> setModified() {
/*  90 */     this.pdfObject.setModified();
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   public void flush() {
/*  95 */     this.pdfObject.flush();
/*     */   }
/*     */   
/*     */   public boolean isFlushed() {
/*  99 */     return this.pdfObject.isFlushed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isWrappedObjectMustBeIndirect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPdfObject(T pdfObject) {
/* 119 */     this.pdfObject = pdfObject;
/*     */   }
/*     */   
/*     */   protected void setForbidRelease() {
/* 123 */     if (this.pdfObject != null) {
/* 124 */       this.pdfObject.setState((short)128);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void unsetForbidRelease() {
/* 129 */     if (this.pdfObject != null) {
/* 130 */       this.pdfObject.clearState((short)128);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void ensureUnderlyingObjectHasIndirectReference() {
/* 135 */     if (getPdfObject().getIndirectReference() == null) {
/* 136 */       throw new PdfException("To manually flush this wrapper, you have to ensure that the object behind this wrapper is added to the document, i.e. it has an indirect reference.");
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void markObjectAsIndirect(PdfObject pdfObject) {
/* 141 */     if (pdfObject.getIndirectReference() == null) {
/* 142 */       pdfObject.setState((short)64);
/*     */     }
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
/*     */   protected static void ensureObjectIsAddedToDocument(PdfObject object) {
/* 157 */     if (object.getIndirectReference() == null)
/* 158 */       throw new PdfException("Object must be indirect to work with this wrapper."); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfObjectWrapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */