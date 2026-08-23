/*     */ package com.itextpdf.kernel.pdf.collection;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfCollectionSort
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -3871923275239410475L;
/*     */   
/*     */   public PdfCollectionSort(PdfDictionary pdfObject) {
/*  61 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionSort(String key) {
/*  70 */     this(new PdfDictionary());
/*  71 */     ((PdfDictionary)getPdfObject()).put(PdfName.S, (PdfObject)new PdfName(key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionSort(String[] keys) {
/*  80 */     this(new PdfDictionary());
/*  81 */     ((PdfDictionary)getPdfObject()).put(PdfName.S, (PdfObject)new PdfArray(Arrays.asList(keys), true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionSort setSortOrder(boolean ascending) {
/*  91 */     PdfObject obj = ((PdfDictionary)getPdfObject()).get(PdfName.S);
/*  92 */     if (obj.isName()) {
/*  93 */       ((PdfDictionary)getPdfObject()).put(PdfName.A, (PdfObject)PdfBoolean.valueOf(ascending));
/*     */     } else {
/*  95 */       throw new PdfException("You have to define a boolean array for this collection sort dictionary.");
/*     */     } 
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionSort setSortOrder(boolean[] ascending) {
/* 107 */     PdfObject obj = ((PdfDictionary)getPdfObject()).get(PdfName.S);
/* 108 */     if (obj.isArray()) {
/* 109 */       if (((PdfArray)obj).size() != ascending.length) {
/* 110 */         throw new PdfException("The number of booleans in the array doesn't correspond with the number of fields.");
/*     */       }
/* 112 */       ((PdfDictionary)getPdfObject()).put(PdfName.A, (PdfObject)new PdfArray(ascending));
/* 113 */       return this;
/*     */     } 
/* 115 */     throw new PdfException("You need a single boolean for this collection sort dictionary.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/collection/PdfCollectionSort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */