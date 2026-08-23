/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.kernel.crypto.AesDecryptor;
/*     */ import com.itextpdf.kernel.crypto.IDecryptor;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamAesEncryption;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamEncryption;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardHandlerUsingAes128
/*     */   extends StandardHandlerUsingStandard128
/*     */ {
/*  57 */   private static final byte[] salt = new byte[] { 115, 65, 108, 84 };
/*     */   
/*     */   private static final long serialVersionUID = -5459302622100333593L;
/*     */ 
/*     */   
/*     */   public StandardHandlerUsingAes128(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, byte[] documentId) {
/*  63 */     super(encryptionDictionary, userPassword, ownerPassword, permissions, encryptMetadata, embeddedFilesOnly, documentId);
/*     */   }
/*     */   
/*     */   public StandardHandlerUsingAes128(PdfDictionary encryptionDictionary, byte[] password, byte[] documentId, boolean encryptMetadata) {
/*  67 */     super(encryptionDictionary, password, documentId, encryptMetadata);
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStreamEncryption getEncryptionStream(OutputStream os) {
/*  72 */     return (OutputStreamEncryption)new OutputStreamAesEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
/*     */   }
/*     */ 
/*     */   
/*     */   public IDecryptor getDecryptor() {
/*  77 */     return (IDecryptor)new AesDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHashKeyForNextObject(int objNumber, int objGeneration) {
/*  83 */     this.md5.reset();
/*  84 */     this.extra[0] = (byte)objNumber;
/*  85 */     this.extra[1] = (byte)(objNumber >> 8);
/*  86 */     this.extra[2] = (byte)(objNumber >> 16);
/*  87 */     this.extra[3] = (byte)objGeneration;
/*  88 */     this.extra[4] = (byte)(objGeneration >> 8);
/*  89 */     this.md5.update(this.mkey);
/*  90 */     this.md5.update(this.extra);
/*  91 */     this.md5.update(salt);
/*  92 */     this.nextObjectKey = this.md5.digest();
/*  93 */     this.nextObjectKeySize = this.mkey.length + 5;
/*  94 */     if (this.nextObjectKeySize > 16) {
/*  95 */       this.nextObjectKeySize = 16;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 100 */     if (!encryptMetadata) {
/* 101 */       encryptionDictionary.put(PdfName.EncryptMetadata, (PdfObject)PdfBoolean.FALSE);
/*     */     }
/* 103 */     encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(4));
/* 104 */     encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(4));
/* 105 */     PdfDictionary stdcf = new PdfDictionary();
/* 106 */     stdcf.put(PdfName.Length, (PdfObject)new PdfNumber(16));
/* 107 */     if (embeddedFilesOnly) {
/* 108 */       stdcf.put(PdfName.AuthEvent, (PdfObject)PdfName.EFOpen);
/* 109 */       encryptionDictionary.put(PdfName.EFF, (PdfObject)PdfName.StdCF);
/* 110 */       encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.Identity);
/* 111 */       encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.Identity);
/*     */     } else {
/* 113 */       stdcf.put(PdfName.AuthEvent, (PdfObject)PdfName.DocOpen);
/* 114 */       encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.StdCF);
/* 115 */       encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.StdCF);
/*     */     } 
/* 117 */     stdcf.put(PdfName.CFM, (PdfObject)PdfName.AESV2);
/* 118 */     PdfDictionary cf = new PdfDictionary();
/* 119 */     cf.put(PdfName.StdCF, (PdfObject)stdcf);
/* 120 */     encryptionDictionary.put(PdfName.CF, (PdfObject)cf);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/StandardHandlerUsingAes128.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */