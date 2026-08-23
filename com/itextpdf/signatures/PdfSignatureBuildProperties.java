/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfSignatureBuildProperties
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   public PdfSignatureBuildProperties() {
/*  61 */     super((PdfObject)new PdfDictionary());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfSignatureBuildProperties(PdfDictionary dict) {
/*  70 */     super((PdfObject)dict);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSignatureCreator(String name) {
/*  80 */     getPdfSignatureAppProperty().setSignatureCreator(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfSignatureApp getPdfSignatureAppProperty() {
/*  91 */     PdfDictionary appPropDic = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.App);
/*     */     
/*  93 */     if (appPropDic == null) {
/*  94 */       appPropDic = new PdfDictionary();
/*  95 */       ((PdfDictionary)getPdfObject()).put(PdfName.App, (PdfObject)appPropDic);
/*     */     } 
/*     */     
/*  98 */     return new PdfSignatureApp(appPropDic);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/PdfSignatureBuildProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */