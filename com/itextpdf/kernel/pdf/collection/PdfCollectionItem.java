/*     */ package com.itextpdf.kernel.pdf.collection;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfDate;
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
/*     */ public class PdfCollectionItem
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -6471103872805179766L;
/*     */   private PdfCollectionSchema schema;
/*     */   
/*     */   public PdfCollectionItem(PdfCollectionSchema schema) {
/*  62 */     super((PdfObject)new PdfDictionary());
/*  63 */     this.schema = schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollectionItem addItem(String key, String value) {
/*  74 */     PdfCollectionField field = this.schema.getField(key);
/*  75 */     ((PdfDictionary)getPdfObject()).put(new PdfName(key), field.getValue(value));
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(String key, PdfDate date) {
/*  86 */     PdfCollectionField field = this.schema.getField(key);
/*  87 */     if (field.subType == 1) {
/*  88 */       ((PdfDictionary)getPdfObject()).put(new PdfName(key), date.getPdfObject());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(String key, PdfNumber number) {
/*  99 */     PdfCollectionField field = this.schema.getField(key);
/* 100 */     if (field.subType == 2) {
/* 101 */       ((PdfDictionary)getPdfObject()).put(new PdfName(key), (PdfObject)number);
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
/*     */   public PdfCollectionItem setPrefix(String key, String prefix) {
/* 114 */     PdfName fieldName = new PdfName(key);
/* 115 */     PdfObject obj = ((PdfDictionary)getPdfObject()).get(fieldName);
/* 116 */     if (obj == null) {
/* 117 */       throw new PdfException("You must set a value before adding a prefix.");
/*     */     }
/* 119 */     PdfDictionary subItem = new PdfDictionary();
/* 120 */     subItem.put(PdfName.D, obj);
/* 121 */     subItem.put(PdfName.P, (PdfObject)new PdfString(prefix));
/* 122 */     ((PdfDictionary)getPdfObject()).put(fieldName, (PdfObject)subItem);
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 128 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/collection/PdfCollectionItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */