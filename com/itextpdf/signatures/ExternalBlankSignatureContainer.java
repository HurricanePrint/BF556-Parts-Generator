/*    */ package com.itextpdf.signatures;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import java.io.InputStream;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ public class ExternalBlankSignatureContainer
/*    */   implements IExternalSignatureContainer
/*    */ {
/*    */   private PdfDictionary sigDic;
/*    */   
/*    */   public ExternalBlankSignatureContainer(PdfDictionary sigDic) {
/* 67 */     this.sigDic = sigDic;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExternalBlankSignatureContainer(PdfName filter, PdfName subFilter) {
/* 78 */     this.sigDic = new PdfDictionary();
/* 79 */     this.sigDic.put(PdfName.Filter, (PdfObject)filter);
/* 80 */     this.sigDic.put(PdfName.SubFilter, (PdfObject)subFilter);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] sign(InputStream data) throws GeneralSecurityException {
/* 85 */     return new byte[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifySigningDictionary(PdfDictionary signDic) {
/* 90 */     signDic.putAll(this.sigDic);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/ExternalBlankSignatureContainer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */