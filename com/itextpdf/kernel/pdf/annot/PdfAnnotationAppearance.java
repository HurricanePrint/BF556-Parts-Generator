/*    */ package com.itextpdf.kernel.pdf.annot;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*    */ import com.itextpdf.kernel.pdf.PdfStream;
/*    */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ public class PdfAnnotationAppearance
/*    */   extends PdfObjectWrapper<PdfDictionary>
/*    */ {
/*    */   private static final long serialVersionUID = 6989855812604521083L;
/*    */   
/*    */   public PdfAnnotationAppearance(PdfDictionary pdfObject) {
/* 59 */     super((PdfObject)pdfObject);
/*    */   }
/*    */   
/*    */   public PdfAnnotationAppearance() {
/* 63 */     this(new PdfDictionary());
/*    */   }
/*    */   
/*    */   public PdfAnnotationAppearance setState(PdfName stateName, PdfFormXObject state) {
/* 67 */     ((PdfDictionary)getPdfObject()).put(stateName, state.getPdfObject());
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public PdfAnnotationAppearance setStateObject(PdfName stateName, PdfStream state) {
/* 72 */     ((PdfDictionary)getPdfObject()).put(stateName, (PdfObject)state);
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   public PdfStream getStateObject(PdfName stateName) {
/* 77 */     return ((PdfDictionary)getPdfObject()).getAsStream(stateName);
/*    */   }
/*    */   
/*    */   public Set<PdfName> getStates() {
/* 81 */     return ((PdfDictionary)getPdfObject()).keySet();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 86 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfAnnotationAppearance.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */