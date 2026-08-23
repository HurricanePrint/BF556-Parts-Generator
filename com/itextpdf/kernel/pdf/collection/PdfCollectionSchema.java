/*    */ package com.itextpdf.kernel.pdf.collection;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfCollectionSchema
/*    */   extends PdfObjectWrapper<PdfDictionary>
/*    */ {
/*    */   private static final long serialVersionUID = -4388183665435879535L;
/*    */   
/*    */   public PdfCollectionSchema(PdfDictionary pdfObject) {
/* 55 */     super((PdfObject)pdfObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PdfCollectionSchema() {
/* 62 */     this(new PdfDictionary());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PdfCollectionSchema addField(String name, PdfCollectionField field) {
/* 73 */     ((PdfDictionary)getPdfObject()).put(new PdfName(name), field.getPdfObject());
/* 74 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PdfCollectionField getField(String name) {
/* 84 */     return new PdfCollectionField(((PdfDictionary)getPdfObject()).getAsDictionary(new PdfName(name)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 89 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/collection/PdfCollectionSchema.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */