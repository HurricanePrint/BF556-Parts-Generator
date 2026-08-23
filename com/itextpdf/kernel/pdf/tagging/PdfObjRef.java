/*    */ package com.itextpdf.kernel.pdf.tagging;
/*    */ 
/*    */ import com.itextpdf.kernel.PdfException;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfDocument;
/*    */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfNumber;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
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
/*    */ public class PdfObjRef
/*    */   extends PdfMcr
/*    */ {
/*    */   private static final long serialVersionUID = 344098256404114906L;
/*    */   
/*    */   public PdfObjRef(PdfDictionary pdfObject, PdfStructElem parent) {
/* 59 */     super((PdfObject)pdfObject, parent);
/*    */   }
/*    */   
/*    */   public PdfObjRef(PdfAnnotation annot, PdfStructElem parent, int nextStructParentIndex) {
/* 63 */     super((PdfObject)new PdfDictionary(), parent);
/* 64 */     ((PdfDictionary)annot.getPdfObject()).put(PdfName.StructParent, (PdfObject)new PdfNumber(nextStructParentIndex));
/* 65 */     annot.setModified();
/*    */     
/* 67 */     PdfDictionary dict = (PdfDictionary)getPdfObject();
/* 68 */     dict.put(PdfName.Type, (PdfObject)PdfName.OBJR);
/* 69 */     dict.put(PdfName.Obj, annot.getPdfObject());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMcid() {
/* 74 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfDictionary getPageObject() {
/* 79 */     return super.getPageObject();
/*    */   }
/*    */   
/*    */   public PdfDictionary getReferencedObject() {
/* 83 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Obj);
/*    */   }
/*    */   
/*    */   private static PdfDocument getDocEnsureIndirect(PdfStructElem structElem) {
/* 87 */     PdfIndirectReference indRef = ((PdfDictionary)structElem.getPdfObject()).getIndirectReference();
/* 88 */     if (indRef == null) {
/* 89 */       throw new PdfException("Structure element dictionary shall be an indirect object in order to have children.");
/*    */     }
/* 91 */     return indRef.getDocument();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfObjRef.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */