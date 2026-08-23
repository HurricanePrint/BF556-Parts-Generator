/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.cert.Certificate;
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
/*     */ public class WriterProperties
/*     */   implements Serializable
/*     */ {
/*     */   protected boolean smartMode = false;
/*     */   protected boolean debugMode = false;
/*     */   protected boolean addUAXmpMetadata = false;
/*  84 */   protected int compressionLevel = -1;
/*  85 */   protected Boolean isFullCompression = null;
/*  86 */   protected EncryptionProperties encryptionProperties = new EncryptionProperties();
/*     */   
/*     */   private static final long serialVersionUID = -8692165914703604764L;
/*     */   
/*     */   protected boolean addXmpMetadata;
/*     */   protected PdfVersion pdfVersion;
/*     */   protected PdfString initialDocumentId;
/*     */   protected PdfString modifiedDocumentId;
/*     */   
/*     */   public WriterProperties setPdfVersion(PdfVersion version) {
/*  96 */     this.pdfVersion = version;
/*  97 */     return this;
/*     */   }
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
/*     */   public WriterProperties useSmartMode() {
/* 112 */     this.smartMode = true;
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WriterProperties addXmpMetadata() {
/* 123 */     this.addXmpMetadata = true;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WriterProperties setCompressionLevel(int compressionLevel) {
/* 135 */     this.compressionLevel = compressionLevel;
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WriterProperties setFullCompressionMode(boolean fullCompressionMode) {
/* 147 */     this.isFullCompression = Boolean.valueOf(fullCompressionMode);
/* 148 */     return this;
/*     */   }
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
/*     */   public WriterProperties setStandardEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, int encryptionAlgorithm) {
/* 186 */     this.encryptionProperties.setStandardEncryption(userPassword, ownerPassword, permissions, encryptionAlgorithm);
/* 187 */     return this;
/*     */   }
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
/*     */   public WriterProperties setPublicKeyEncryption(Certificate[] certs, int[] permissions, int encryptionAlgorithm) {
/* 223 */     this.encryptionProperties.setPublicKeyEncryption(certs, permissions, encryptionAlgorithm);
/* 224 */     return this;
/*     */   }
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
/*     */   public WriterProperties setInitialDocumentId(PdfString initialDocumentId) {
/* 239 */     this.initialDocumentId = initialDocumentId;
/* 240 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WriterProperties setModifiedDocumentId(PdfString modifiedDocumentId) {
/* 252 */     this.modifiedDocumentId = modifiedDocumentId;
/* 253 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WriterProperties useDebugMode() {
/* 263 */     this.debugMode = true;
/* 264 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WriterProperties addUAXmpMetadata() {
/* 276 */     this.addUAXmpMetadata = true;
/* 277 */     return addXmpMetadata();
/*     */   }
/*     */   
/*     */   boolean isStandardEncryptionUsed() {
/* 281 */     return this.encryptionProperties.isStandardEncryptionUsed();
/*     */   }
/*     */   
/*     */   boolean isPublicKeyEncryptionUsed() {
/* 285 */     return this.encryptionProperties.isPublicKeyEncryptionUsed();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/WriterProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */