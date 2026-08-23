/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.security.IExternalDecryptionProcess;
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
/*     */ public class PubSecHandlerUsingAes256
/*     */   extends PubSecHandlerUsingAes128
/*     */ {
/*     */   private static final long serialVersionUID = -9158784716845784422L;
/*     */   
/*     */   public PubSecHandlerUsingAes256(PdfDictionary encryptionDictionary, Certificate[] certs, int[] permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
/*  59 */     super(encryptionDictionary, certs, permissions, encryptMetadata, embeddedFilesOnly);
/*     */   }
/*     */   
/*     */   public PubSecHandlerUsingAes256(PdfDictionary encryptionDictionary, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata) {
/*  63 */     super(encryptionDictionary, certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, encryptMetadata);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHashKeyForNextObject(int objNumber, int objGeneration) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDigestAlgorithm() {
/*  73 */     return "SHA-256";
/*     */   }
/*     */   
/*     */   protected void initKey(byte[] globalKey, int keyLength) {
/*  77 */     this.nextObjectKey = globalKey;
/*  78 */     this.nextObjectKeySize = 32;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setPubSecSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
/*  83 */     encryptionDictionary.put(PdfName.Filter, (PdfObject)PdfName.Adobe_PubSec);
/*  84 */     encryptionDictionary.put(PdfName.SubFilter, (PdfObject)PdfName.Adbe_pkcs7_s5);
/*     */     
/*  86 */     encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(5));
/*  87 */     encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(5));
/*     */     
/*  89 */     PdfArray recipients = createRecipientsArray();
/*  90 */     PdfDictionary stdcf = new PdfDictionary();
/*  91 */     stdcf.put(PdfName.Recipients, (PdfObject)recipients);
/*  92 */     if (!encryptMetadata) {
/*  93 */       stdcf.put(PdfName.EncryptMetadata, (PdfObject)PdfBoolean.FALSE);
/*     */     }
/*  95 */     stdcf.put(PdfName.CFM, (PdfObject)PdfName.AESV3);
/*  96 */     stdcf.put(PdfName.Length, (PdfObject)new PdfNumber(256));
/*  97 */     PdfDictionary cf = new PdfDictionary();
/*  98 */     cf.put(PdfName.DefaultCryptFilter, (PdfObject)stdcf);
/*  99 */     encryptionDictionary.put(PdfName.CF, (PdfObject)cf);
/* 100 */     if (embeddedFilesOnly) {
/* 101 */       encryptionDictionary.put(PdfName.EFF, (PdfObject)PdfName.DefaultCryptFilter);
/* 102 */       encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.Identity);
/* 103 */       encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.Identity);
/*     */     } else {
/* 105 */       encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.DefaultCryptFilter);
/* 106 */       encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.DefaultCryptFilter);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/PubSecHandlerUsingAes256.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */