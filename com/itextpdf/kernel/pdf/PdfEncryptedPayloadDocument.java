/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
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
/*    */ public class PdfEncryptedPayloadDocument
/*    */   extends PdfObjectWrapper<PdfStream>
/*    */ {
/*    */   private PdfFileSpec fileSpec;
/*    */   private String name;
/*    */   
/*    */   public PdfEncryptedPayloadDocument(PdfStream pdfObject, PdfFileSpec fileSpec, String name) {
/* 53 */     super(pdfObject);
/* 54 */     this.fileSpec = fileSpec;
/* 55 */     this.name = name;
/*    */   }
/*    */   
/*    */   public byte[] getDocumentBytes() {
/* 59 */     return getPdfObject().getBytes();
/*    */   }
/*    */   
/*    */   public PdfFileSpec getFileSpec() {
/* 63 */     return this.fileSpec;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 67 */     return this.name;
/*    */   }
/*    */   
/*    */   public PdfEncryptedPayload getEncryptedPayload() {
/* 71 */     return PdfEncryptedPayload.extractFrom(this.fileSpec);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 76 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfEncryptedPayloadDocument.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */