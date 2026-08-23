/*    */ package com.itextpdf.kernel.crypto.securityhandler;
/*    */ 
/*    */ import com.itextpdf.kernel.crypto.IDecryptor;
/*    */ import com.itextpdf.kernel.crypto.OutputStreamEncryption;
/*    */ import com.itextpdf.kernel.crypto.OutputStreamStandardEncryption;
/*    */ import com.itextpdf.kernel.crypto.StandardDecryptor;
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfNumber;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.security.IExternalDecryptionProcess;
/*    */ import java.io.OutputStream;
/*    */ import java.security.Key;
/*    */ import java.security.cert.Certificate;
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
/*    */ public class PubSecHandlerUsingStandard40
/*    */   extends PubKeySecurityHandler
/*    */ {
/*    */   private static final long serialVersionUID = -4875474035831723279L;
/*    */   
/*    */   public PubSecHandlerUsingStandard40(PdfDictionary encryptionDictionary, Certificate[] certs, int[] permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 63 */     initKeyAndFillDictionary(encryptionDictionary, certs, permissions, encryptMetadata, embeddedFilesOnly);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PubSecHandlerUsingStandard40(PdfDictionary encryptionDictionary, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata) {
/* 69 */     initKeyAndReadDictionary(encryptionDictionary, certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, encryptMetadata);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStreamEncryption getEncryptionStream(OutputStream os) {
/* 75 */     return (OutputStreamEncryption)new OutputStreamStandardEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
/*    */   }
/*    */ 
/*    */   
/*    */   public IDecryptor getDecryptor() {
/* 80 */     return (IDecryptor)new StandardDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
/*    */   }
/*    */   
/*    */   protected String getDigestAlgorithm() {
/* 84 */     return "SHA-1";
/*    */   }
/*    */   
/*    */   protected void initKey(byte[] globalKey, int keyLength) {
/* 88 */     this.mkey = new byte[keyLength / 8];
/* 89 */     System.arraycopy(globalKey, 0, this.mkey, 0, this.mkey.length);
/*    */   }
/*    */   
/*    */   protected void setPubSecSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 93 */     encryptionDictionary.put(PdfName.Filter, (PdfObject)PdfName.Adobe_PubSec);
/* 94 */     encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(2));
/*    */     
/* 96 */     PdfArray recipients = createRecipientsArray();
/* 97 */     encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(1));
/* 98 */     encryptionDictionary.put(PdfName.SubFilter, (PdfObject)PdfName.Adbe_pkcs7_s4);
/* 99 */     encryptionDictionary.put(PdfName.Recipients, (PdfObject)recipients);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/PubSecHandlerUsingStandard40.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */