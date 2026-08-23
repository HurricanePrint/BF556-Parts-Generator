/*    */ package com.itextpdf.kernel.crypto.securityhandler;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfNumber;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import com.itextpdf.kernel.security.IExternalDecryptionProcess;
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
/*    */ 
/*    */ public class PubSecHandlerUsingStandard128
/*    */   extends PubSecHandlerUsingStandard40
/*    */ {
/*    */   private static final long serialVersionUID = 4243832116977499452L;
/*    */   
/*    */   public PubSecHandlerUsingStandard128(PdfDictionary encryptionDictionary, Certificate[] certs, int[] permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 60 */     super(encryptionDictionary, certs, permissions, encryptMetadata, embeddedFilesOnly);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PubSecHandlerUsingStandard128(PdfDictionary encryptionDictionary, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata) {
/* 66 */     super(encryptionDictionary, certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, encryptMetadata);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setPubSecSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 71 */     encryptionDictionary.put(PdfName.Filter, (PdfObject)PdfName.Adobe_PubSec);
/* 72 */     PdfArray recipients = createRecipientsArray();
/* 73 */     if (encryptMetadata) {
/* 74 */       encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(3));
/* 75 */       encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(2));
/* 76 */       encryptionDictionary.put(PdfName.SubFilter, (PdfObject)PdfName.Adbe_pkcs7_s4);
/* 77 */       encryptionDictionary.put(PdfName.Recipients, (PdfObject)recipients);
/*    */     } else {
/* 79 */       encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(4));
/* 80 */       encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(4));
/* 81 */       encryptionDictionary.put(PdfName.SubFilter, (PdfObject)PdfName.Adbe_pkcs7_s5);
/*    */       
/* 83 */       PdfDictionary stdcf = new PdfDictionary();
/* 84 */       stdcf.put(PdfName.Recipients, (PdfObject)recipients);
/* 85 */       stdcf.put(PdfName.EncryptMetadata, (PdfObject)PdfBoolean.FALSE);
/* 86 */       stdcf.put(PdfName.CFM, (PdfObject)PdfName.V2);
/*    */       
/* 88 */       PdfDictionary cf = new PdfDictionary();
/* 89 */       cf.put(PdfName.DefaultCryptFilter, (PdfObject)stdcf);
/* 90 */       encryptionDictionary.put(PdfName.CF, (PdfObject)cf);
/*    */       
/* 92 */       if (embeddedFilesOnly) {
/* 93 */         encryptionDictionary.put(PdfName.EFF, (PdfObject)PdfName.DefaultCryptFilter);
/* 94 */         encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.Identity);
/* 95 */         encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.Identity);
/*    */       } else {
/* 97 */         encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.DefaultCryptFilter);
/* 98 */         encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.DefaultCryptFilter);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/PubSecHandlerUsingStandard128.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */