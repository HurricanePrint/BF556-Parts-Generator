/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VersionConforming
/*    */ {
/*    */   public static final String DEPRECATED_AES256_REVISION = "It seems that PDF 1.7 document encrypted with AES256 was updated to PDF 2.0 version and StampingProperties#preserveEncryption flag was set: encryption shall be updated via WriterProperties#setStandardEncryption method. Standard security handler was found with revision 5, which is deprecated and shall not be used in PDF 2.0 documents.";
/*    */   public static final String DEPRECATED_ENCRYPTION_ALGORITHMS = "Encryption algorithms STANDARD_ENCRYPTION_40, STANDARD_ENCRYPTION_128 and ENCRYPTION_AES_128 (see com.itextpdf.kernel.pdf.EncryptionConstants) are deprecated in PDF 2.0. It is highly recommended not to use it.";
/*    */   public static final String DEPRECATED_NEED_APPEARANCES_IN_ACROFORM = "NeedAppearances has been deprecated in PDF 2.0. Appearance streams are required in PDF 2.0.";
/*    */   public static final String DEPRECATED_XFA_FORMS = "XFA is deprecated in PDF 2.0. The XFA form will not be written to the document";
/* 58 */   private static final Logger logger = LoggerFactory.getLogger(VersionConforming.class);
/*    */   
/*    */   public static boolean validatePdfVersionForDictEntry(PdfDocument document, PdfVersion expectedVersion, PdfName entryKey, PdfName dictType) {
/* 61 */     if (document != null && document.getPdfVersion().compareTo(expectedVersion) < 0) {
/* 62 */       logger.warn(MessageFormat.format("\"{0}\" entry in the \"{1}\" dictionary is a {2} and higher version feature. It is meaningless for the current {3} version.", new Object[] { entryKey, dictType, expectedVersion, document.getPdfVersion() }));
/* 63 */       return true;
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean validatePdfVersionForDeprecatedFeatureLogWarn(PdfDocument document, PdfVersion expectedVersion, String deprecatedFeatureLogMessage) {
/* 70 */     if (document.getPdfVersion().compareTo(expectedVersion) >= 0) {
/* 71 */       logger.warn(deprecatedFeatureLogMessage);
/* 72 */       return true;
/*    */     } 
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean validatePdfVersionForDeprecatedFeatureLogError(PdfDocument document, PdfVersion expectedVersion, String deprecatedFeatureLogMessage) {
/* 79 */     if (document.getPdfVersion().compareTo(expectedVersion) >= 0) {
/* 80 */       logger.error(deprecatedFeatureLogMessage);
/* 81 */       return true;
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/VersionConforming.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */