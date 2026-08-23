/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.security.IExternalDecryptionProcess;
/*     */ import java.io.Serializable;
/*     */ import java.security.Key;
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
/*     */ public class ReaderProperties
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5569118801793215916L;
/*     */   protected byte[] password;
/*     */   protected Key certificateKey;
/*     */   protected Certificate certificate;
/*     */   protected String certificateKeyProvider;
/*     */   protected IExternalDecryptionProcess externalDecryptionProcess;
/*     */   protected MemoryLimitsAwareHandler memoryLimitsAwareHandler;
/*     */   
/*     */   public ReaderProperties setPassword(byte[] password) {
/*  77 */     clearEncryptionParams();
/*  78 */     this.password = password;
/*  79 */     return this;
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
/*     */   public ReaderProperties setPublicKeySecurityParams(Certificate certificate, Key certificateKey, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess) {
/*  96 */     clearEncryptionParams();
/*  97 */     this.certificate = certificate;
/*  98 */     this.certificateKey = certificateKey;
/*  99 */     this.certificateKeyProvider = certificateKeyProvider;
/* 100 */     this.externalDecryptionProcess = externalDecryptionProcess;
/*     */     
/* 102 */     return this;
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
/*     */   public ReaderProperties setPublicKeySecurityParams(Certificate certificate, IExternalDecryptionProcess externalDecryptionProcess) {
/* 115 */     clearEncryptionParams();
/* 116 */     this.certificate = certificate;
/* 117 */     this.externalDecryptionProcess = externalDecryptionProcess;
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   private void clearEncryptionParams() {
/* 122 */     this.password = null;
/* 123 */     this.certificate = null;
/* 124 */     this.certificateKey = null;
/* 125 */     this.certificateKeyProvider = null;
/* 126 */     this.externalDecryptionProcess = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReaderProperties setMemoryLimitsAwareHandler(MemoryLimitsAwareHandler memoryLimitsAwareHandler) {
/* 136 */     this.memoryLimitsAwareHandler = memoryLimitsAwareHandler;
/* 137 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/ReaderProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */