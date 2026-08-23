/*    */ package com.itextpdf.kernel.pdf.annot;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfNumber;
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
/*    */ public class PdfFixedPrint
/*    */   extends PdfObjectWrapper<PdfDictionary>
/*    */ {
/*    */   private static final long serialVersionUID = 4253232541458560135L;
/*    */   
/*    */   public PdfFixedPrint() {
/* 57 */     this(new PdfDictionary());
/*    */   }
/*    */   
/*    */   public PdfFixedPrint(PdfDictionary pdfObject) {
/* 61 */     super((PdfObject)pdfObject);
/* 62 */     pdfObject.put(PdfName.Type, (PdfObject)PdfName.FixedPrint);
/*    */   }
/*    */   
/*    */   public PdfFixedPrint setMatrix(PdfArray matrix) {
/* 66 */     ((PdfDictionary)getPdfObject()).put(PdfName.Matrix, (PdfObject)matrix);
/* 67 */     return this;
/*    */   }
/*    */   
/*    */   public PdfFixedPrint setMatrix(float[] matrix) {
/* 71 */     ((PdfDictionary)getPdfObject()).put(PdfName.Matrix, (PdfObject)new PdfArray(matrix));
/* 72 */     return this;
/*    */   }
/*    */   
/*    */   public PdfFixedPrint setHorizontalTranslation(float horizontal) {
/* 76 */     ((PdfDictionary)getPdfObject()).put(PdfName.H, (PdfObject)new PdfNumber(horizontal));
/* 77 */     return this;
/*    */   }
/*    */   
/*    */   public PdfFixedPrint setVerticalTranslation(float vertical) {
/* 81 */     ((PdfDictionary)getPdfObject()).put(PdfName.V, (PdfObject)new PdfNumber(vertical));
/* 82 */     return this;
/*    */   }
/*    */   
/*    */   public PdfArray getMatrix() {
/* 86 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Matrix);
/*    */   }
/*    */   
/*    */   public PdfNumber getHorizontalTranslation() {
/* 90 */     return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.H);
/*    */   }
/*    */   
/*    */   public PdfNumber getVerticalTranslation() {
/* 94 */     return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.V);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfFixedPrint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */