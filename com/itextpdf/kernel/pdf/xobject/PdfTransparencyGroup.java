/*     */ package com.itextpdf.kernel.pdf.xobject;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
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
/*     */ public class PdfTransparencyGroup
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = 753843601750097627L;
/*     */   
/*     */   public PdfTransparencyGroup() {
/*  58 */     super((PdfObject)new PdfDictionary());
/*  59 */     ((PdfDictionary)getPdfObject()).put(PdfName.S, (PdfObject)PdfName.Transparency);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsolated(boolean isolated) {
/*  68 */     if (isolated) {
/*  69 */       ((PdfDictionary)getPdfObject()).put(PdfName.I, (PdfObject)PdfBoolean.TRUE);
/*     */     } else {
/*  71 */       ((PdfDictionary)getPdfObject()).remove(PdfName.I);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKnockout(boolean knockout) {
/*  81 */     if (knockout) {
/*  82 */       ((PdfDictionary)getPdfObject()).put(PdfName.K, (PdfObject)PdfBoolean.TRUE);
/*     */     } else {
/*  84 */       ((PdfDictionary)getPdfObject()).remove(PdfName.K);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setColorSpace(PdfName colorSpace) {
/*  89 */     ((PdfDictionary)getPdfObject()).put(PdfName.CS, (PdfObject)colorSpace);
/*     */   }
/*     */   
/*     */   public void setColorSpace(PdfArray colorSpace) {
/*  93 */     ((PdfDictionary)getPdfObject()).put(PdfName.CS, (PdfObject)colorSpace);
/*     */   }
/*     */   
/*     */   public PdfTransparencyGroup put(PdfName key, PdfObject value) {
/*  97 */     ((PdfDictionary)getPdfObject()).put(key, value);
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/xobject/PdfTransparencyGroup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */