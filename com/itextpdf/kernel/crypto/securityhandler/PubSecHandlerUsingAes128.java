/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.kernel.crypto.AesDecryptor;
/*     */ import com.itextpdf.kernel.crypto.IDecryptor;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamAesEncryption;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamEncryption;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.security.IExternalDecryptionProcess;
/*     */ import java.io.OutputStream;
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
/*     */ public class PubSecHandlerUsingAes128
/*     */   extends PubKeySecurityHandler
/*     */ {
/*  61 */   private static final byte[] salt = new byte[] { 115, 65, 108, 84 };
/*     */   
/*     */   private static final long serialVersionUID = -6752298218106272395L;
/*     */   
/*     */   public PubSecHandlerUsingAes128(PdfDictionary encryptionDictionary, Certificate[] certs, int[] permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
/*  66 */     initKeyAndFillDictionary(encryptionDictionary, certs, permissions, encryptMetadata, embeddedFilesOnly);
/*     */   }
/*     */   
/*     */   public PubSecHandlerUsingAes128(PdfDictionary encryptionDictionary, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata) {
/*  70 */     initKeyAndReadDictionary(encryptionDictionary, certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, encryptMetadata);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStreamEncryption getEncryptionStream(OutputStream os) {
/*  76 */     return (OutputStreamEncryption)new OutputStreamAesEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
/*     */   }
/*     */ 
/*     */   
/*     */   public IDecryptor getDecryptor() {
/*  81 */     return (IDecryptor)new AesDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHashKeyForNextObject(int objNumber, int objGeneration) {
/*  87 */     this.md5.reset();
/*  88 */     this.extra[0] = (byte)objNumber;
/*  89 */     this.extra[1] = (byte)(objNumber >> 8);
/*  90 */     this.extra[2] = (byte)(objNumber >> 16);
/*  91 */     this.extra[3] = (byte)objGeneration;
/*  92 */     this.extra[4] = (byte)(objGeneration >> 8);
/*  93 */     this.md5.update(this.mkey);
/*  94 */     this.md5.update(this.extra);
/*  95 */     this.md5.update(salt);
/*  96 */     this.nextObjectKey = this.md5.digest();
/*  97 */     this.nextObjectKeySize = this.mkey.length + 5;
/*  98 */     if (this.nextObjectKeySize > 16)
/*  99 */       this.nextObjectKeySize = 16; 
/*     */   }
/*     */   
/*     */   protected String getDigestAlgorithm() {
/* 103 */     return "SHA-1";
/*     */   }
/*     */   
/*     */   protected void initKey(byte[] globalKey, int keyLength) {
/* 107 */     this.mkey = new byte[keyLength / 8];
/* 108 */     System.arraycopy(globalKey, 0, this.mkey, 0, this.mkey.length);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setPubSecSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 113 */     encryptionDictionary.put(PdfName.Filter, (PdfObject)PdfName.Adobe_PubSec);
/* 114 */     encryptionDictionary.put(PdfName.SubFilter, (PdfObject)PdfName.Adbe_pkcs7_s5);
/*     */     
/* 116 */     encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(4));
/* 117 */     encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(4));
/*     */     
/* 119 */     PdfArray recipients = createRecipientsArray();
/* 120 */     PdfDictionary stdcf = new PdfDictionary();
/* 121 */     stdcf.put(PdfName.Recipients, (PdfObject)recipients);
/* 122 */     if (!encryptMetadata) {
/* 123 */       stdcf.put(PdfName.EncryptMetadata, (PdfObject)PdfBoolean.FALSE);
/*     */     }
/* 125 */     stdcf.put(PdfName.CFM, (PdfObject)PdfName.AESV2);
/* 126 */     stdcf.put(PdfName.Length, (PdfObject)new PdfNumber(128));
/* 127 */     PdfDictionary cf = new PdfDictionary();
/* 128 */     cf.put(PdfName.DefaultCryptFilter, (PdfObject)stdcf);
/* 129 */     encryptionDictionary.put(PdfName.CF, (PdfObject)cf);
/* 130 */     if (embeddedFilesOnly) {
/* 131 */       encryptionDictionary.put(PdfName.EFF, (PdfObject)PdfName.DefaultCryptFilter);
/* 132 */       encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.Identity);
/* 133 */       encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.Identity);
/*     */     } else {
/* 135 */       encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.DefaultCryptFilter);
/* 136 */       encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.DefaultCryptFilter);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/PubSecHandlerUsingAes128.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */