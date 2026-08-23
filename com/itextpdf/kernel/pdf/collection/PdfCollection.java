/*     */ package com.itextpdf.kernel.pdf.collection;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
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
/*     */ public class PdfCollection
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = 5184499156015360355L;
/*     */   public static final int DETAILS = 0;
/*     */   public static final int TILE = 1;
/*     */   public static final int HIDDEN = 2;
/*     */   
/*     */   public PdfCollection(PdfDictionary pdfObject) {
/*  64 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollection() {
/*  71 */     this(new PdfDictionary());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollection setSchema(PdfCollectionSchema schema) {
/*  81 */     ((PdfDictionary)getPdfObject()).put(PdfName.Schema, schema.getPdfObject());
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionSchema getSchema() {
/*  91 */     return new PdfCollectionSchema(((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Schema));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollection setInitialDocument(String documentName) {
/* 102 */     ((PdfDictionary)getPdfObject()).put(PdfName.D, (PdfObject)new PdfString(documentName));
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getInitialDocument() {
/* 113 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollection setView(int viewType) {
/* 123 */     switch (viewType)
/*     */     { default:
/* 125 */         ((PdfDictionary)getPdfObject()).put(PdfName.View, (PdfObject)PdfName.D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 134 */         return this;case 1: ((PdfDictionary)getPdfObject()).put(PdfName.View, (PdfObject)PdfName.T); return this;case 2: break; }  ((PdfDictionary)getPdfObject()).put(PdfName.View, (PdfObject)PdfName.H); return this;
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
/*     */   @Deprecated
/*     */   public PdfNumber getView() {
/* 147 */     return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.View);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isViewDetails() {
/* 156 */     PdfName view = ((PdfDictionary)getPdfObject()).getAsName(PdfName.View);
/* 157 */     return (view == null || view.equals(PdfName.D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isViewTile() {
/* 166 */     return PdfName.T.equals(((PdfDictionary)getPdfObject()).getAsName(PdfName.View));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isViewHidden() {
/* 175 */     return PdfName.H.equals(((PdfDictionary)getPdfObject()).getAsName(PdfName.View));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollection setSort(PdfCollectionSort sort) {
/* 185 */     ((PdfDictionary)getPdfObject()).put(PdfName.Sort, sort.getPdfObject());
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionSort getSort() {
/* 195 */     return new PdfCollectionSort(((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Sort));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 200 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/collection/PdfCollection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */