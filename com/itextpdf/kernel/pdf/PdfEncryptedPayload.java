/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
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
/*     */ public class PdfEncryptedPayload
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   public PdfEncryptedPayload(String subtype) {
/*  51 */     this(new PdfDictionary());
/*  52 */     getPdfObject().put(PdfName.Type, PdfName.EncryptedPayload);
/*  53 */     setSubtype(subtype);
/*     */   }
/*     */   
/*     */   private PdfEncryptedPayload(PdfDictionary pdfObject) {
/*  57 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PdfEncryptedPayload extractFrom(PdfFileSpec fileSpec) {
/*  62 */     if (fileSpec != null && fileSpec.getPdfObject().isDictionary()) {
/*  63 */       return wrap(((PdfDictionary)fileSpec.getPdfObject()).getAsDictionary(PdfName.EP));
/*     */     }
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public static PdfEncryptedPayload wrap(PdfDictionary dictionary) {
/*  69 */     PdfName type = dictionary.getAsName(PdfName.Type);
/*  70 */     if (type != null && !type.equals(PdfName.EncryptedPayload)) {
/*  71 */       throw new PdfException("Encrypted payload dictionary shall have field 'Type' equal to 'EncryptedPayload' if present");
/*     */     }
/*  73 */     if (dictionary.getAsName(PdfName.Subtype) == null) {
/*  74 */       throw new PdfException("Encrypted payload shall have 'Subtype' field specifying crypto filter");
/*     */     }
/*  76 */     return new PdfEncryptedPayload(dictionary);
/*     */   }
/*     */   
/*     */   public PdfName getSubtype() {
/*  80 */     return getPdfObject().getAsName(PdfName.Subtype);
/*     */   }
/*     */   
/*     */   public PdfEncryptedPayload setSubtype(String subtype) {
/*  84 */     return setSubtype(new PdfName(subtype));
/*     */   }
/*     */   
/*     */   public PdfEncryptedPayload setSubtype(PdfName subtype) {
/*  88 */     setModified();
/*  89 */     getPdfObject().put(PdfName.Subtype, subtype);
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public PdfName getVersion() {
/*  94 */     return getPdfObject().getAsName(PdfName.Version);
/*     */   }
/*     */   
/*     */   public PdfEncryptedPayload setVersion(String version) {
/*  98 */     return setVersion(new PdfName(version));
/*     */   }
/*     */   
/*     */   public PdfEncryptedPayload setVersion(PdfName version) {
/* 102 */     setModified();
/* 103 */     getPdfObject().put(PdfName.Version, version);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 109 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfEncryptedPayload.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */