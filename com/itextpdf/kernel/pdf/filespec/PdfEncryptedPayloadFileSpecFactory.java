/*     */ package com.itextpdf.kernel.pdf.filespec;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfEncryptedPayload;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfEncryptedPayloadFileSpecFactory
/*     */ {
/*     */   public static PdfFileSpec create(PdfDocument doc, byte[] fileStore, PdfEncryptedPayload encryptedPayload, PdfName mimeType, PdfDictionary fileParameter) {
/*  69 */     return addEncryptedPayloadDictionary(PdfFileSpec.createEmbeddedFileSpec(doc, fileStore, generateDescription(encryptedPayload), generateFileDisplay(encryptedPayload), mimeType, fileParameter, PdfName.EncryptedPayload), encryptedPayload);
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
/*     */   public static PdfFileSpec create(PdfDocument doc, byte[] fileStore, PdfEncryptedPayload encryptedPayload, PdfDictionary fileParameter) {
/*  82 */     return create(doc, fileStore, encryptedPayload, (PdfName)null, fileParameter);
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
/*     */   public static PdfFileSpec create(PdfDocument doc, byte[] fileStore, PdfEncryptedPayload encryptedPayload) {
/*  94 */     return create(doc, fileStore, encryptedPayload, (PdfName)null, (PdfDictionary)null);
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
/*     */   public static PdfFileSpec create(PdfDocument doc, String filePath, PdfEncryptedPayload encryptedPayload, PdfName mimeType, PdfDictionary fileParameter) throws IOException {
/* 109 */     return addEncryptedPayloadDictionary(PdfFileSpec.createEmbeddedFileSpec(doc, filePath, generateDescription(encryptedPayload), generateFileDisplay(encryptedPayload), mimeType, fileParameter, PdfName.EncryptedPayload), encryptedPayload);
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
/*     */   public static PdfFileSpec create(PdfDocument doc, String filePath, PdfEncryptedPayload encryptedPayload, PdfName mimeType) throws IOException {
/* 123 */     return create(doc, filePath, encryptedPayload, mimeType, (PdfDictionary)null);
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
/*     */   public static PdfFileSpec create(PdfDocument doc, String filePath, PdfEncryptedPayload encryptedPayload) throws IOException {
/* 136 */     return create(doc, filePath, encryptedPayload, (PdfName)null, (PdfDictionary)null);
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
/*     */   public static PdfFileSpec create(PdfDocument doc, InputStream is, PdfEncryptedPayload encryptedPayload, PdfName mimeType, PdfDictionary fileParameter) {
/* 150 */     return addEncryptedPayloadDictionary(PdfFileSpec.createEmbeddedFileSpec(doc, is, generateDescription(encryptedPayload), generateFileDisplay(encryptedPayload), mimeType, fileParameter, PdfName.EncryptedPayload), encryptedPayload);
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
/*     */   public static PdfFileSpec create(PdfDocument doc, InputStream is, PdfEncryptedPayload encryptedPayload, PdfName mimeType) {
/* 163 */     return create(doc, is, encryptedPayload, mimeType, (PdfDictionary)null);
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
/*     */   public static PdfFileSpec create(PdfDocument doc, InputStream is, PdfEncryptedPayload encryptedPayload) {
/* 175 */     return create(doc, is, encryptedPayload, (PdfName)null, (PdfDictionary)null);
/*     */   }
/*     */   
/*     */   public static PdfFileSpec wrap(PdfDictionary dictionary) {
/* 179 */     if (!PdfName.EncryptedPayload.equals(dictionary.getAsName(PdfName.AFRelationship))) {
/* 180 */       LoggerFactory.getLogger(PdfEncryptedPayloadFileSpecFactory.class).error("Encrypted payload file spec shall have 'AFRelationship' filed equal to 'EncryptedPayload'");
/*     */     }
/* 182 */     PdfDictionary ef = dictionary.getAsDictionary(PdfName.EF);
/* 183 */     if (ef == null || (ef.getAsStream(PdfName.F) == null && ef.getAsStream(PdfName.UF) == null)) {
/* 184 */       throw new PdfException("Encrypted payload file spec shall have 'EF' key. The value of such key shall be a dictionary that contains embedded file stream.");
/*     */     }
/* 186 */     if (!PdfName.Filespec.equals(dictionary.getAsName(PdfName.Type))) {
/* 187 */       throw new PdfException("Encrypted payload file spec shall have 'Type' key. The value of such key shall be 'Filespec'.");
/*     */     }
/* 189 */     if (!dictionary.isIndirect()) {
/* 190 */       throw new PdfException("Encrypted payload file spec shall be indirect.");
/*     */     }
/* 192 */     PdfFileSpec fileSpec = PdfFileSpec.wrapFileSpecObject((PdfObject)dictionary);
/* 193 */     if (PdfEncryptedPayload.extractFrom(fileSpec) == null) {
/* 194 */       throw new PdfException("Encrypted payload file spec shall have encrypted payload dictionary.");
/*     */     }
/* 196 */     return fileSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateDescription(PdfEncryptedPayload ep) {
/* 205 */     String result = "This embedded file is encrypted using " + ep.getSubtype().getValue();
/* 206 */     PdfName version = ep.getVersion();
/* 207 */     if (version != null) {
/* 208 */       result = result + " , version: " + version.getValue();
/*     */     }
/* 210 */     return result;
/*     */   }
/*     */   
/*     */   public static String generateFileDisplay(PdfEncryptedPayload ep) {
/* 214 */     return ep.getSubtype().getValue() + "Protected.pdf";
/*     */   }
/*     */   
/*     */   private static PdfFileSpec addEncryptedPayloadDictionary(PdfFileSpec fs, PdfEncryptedPayload ep) {
/* 218 */     ((PdfDictionary)fs.getPdfObject()).put(PdfName.EP, ep.getPdfObject());
/* 219 */     return fs;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filespec/PdfEncryptedPayloadFileSpecFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */