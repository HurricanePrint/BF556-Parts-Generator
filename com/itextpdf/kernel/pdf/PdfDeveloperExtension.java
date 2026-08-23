/*     */ package com.itextpdf.kernel.pdf;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfDeveloperExtension
/*     */ {
/*  61 */   public static final PdfDeveloperExtension ADOBE_1_7_EXTENSIONLEVEL3 = new PdfDeveloperExtension(PdfName.ADBE, PdfName.Pdf_Version_1_7, 3);
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final PdfDeveloperExtension ESIC_1_7_EXTENSIONLEVEL2 = new PdfDeveloperExtension(PdfName.ESIC, PdfName.Pdf_Version_1_7, 2);
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final PdfDeveloperExtension ESIC_1_7_EXTENSIONLEVEL5 = new PdfDeveloperExtension(PdfName.ESIC, PdfName.Pdf_Version_1_7, 5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfName prefix;
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfName baseVersion;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int extensionLevel;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDeveloperExtension(PdfName prefix, PdfName baseVersion, int extensionLevel) {
/*  88 */     this.prefix = prefix;
/*  89 */     this.baseVersion = baseVersion;
/*  90 */     this.extensionLevel = extensionLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getPrefix() {
/*  98 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getBaseVersion() {
/* 106 */     return this.baseVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtensionLevel() {
/* 114 */     return this.extensionLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getDeveloperExtensions() {
/* 123 */     PdfDictionary developerextensions = new PdfDictionary();
/* 124 */     developerextensions.put(PdfName.BaseVersion, this.baseVersion);
/* 125 */     developerextensions.put(PdfName.ExtensionLevel, new PdfNumber(this.extensionLevel));
/*     */     
/* 127 */     return developerextensions;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfDeveloperExtension.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */