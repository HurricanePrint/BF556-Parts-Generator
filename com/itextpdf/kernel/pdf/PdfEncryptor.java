/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.counter.event.IMetaInfo;
/*     */ import java.io.OutputStream;
/*     */ import java.security.PrivateKey;
/*     */ import java.util.Map;
/*     */ import org.bouncycastle.cms.CMSException;
/*     */ import org.bouncycastle.cms.Recipient;
/*     */ import org.bouncycastle.cms.RecipientInformation;
/*     */ import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
/*     */ import org.bouncycastle.cms.jcajce.JceKeyTransRecipient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PdfEncryptor
/*     */ {
/*     */   private IMetaInfo metaInfo;
/*     */   private EncryptionProperties properties;
/*     */   
/*     */   public static void encrypt(PdfReader reader, OutputStream os, EncryptionProperties properties, Map<String, String> newInfo) {
/*  80 */     (new PdfEncryptor()).setEncryptionProperties(properties).encrypt(reader, os, newInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void encrypt(PdfReader reader, OutputStream os, EncryptionProperties properties) {
/*  91 */     encrypt(reader, os, properties, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPermissionsVerbose(int permissions) {
/* 101 */     StringBuilder buf = new StringBuilder("Allowed:");
/* 102 */     if ((0x804 & permissions) == 2052) buf.append(" Printing"); 
/* 103 */     if ((0x8 & permissions) == 8)
/* 104 */       buf.append(" Modify contents"); 
/* 105 */     if ((0x10 & permissions) == 16) buf.append(" Copy"); 
/* 106 */     if ((0x20 & permissions) == 32)
/* 107 */       buf.append(" Modify annotations"); 
/* 108 */     if ((0x100 & permissions) == 256) buf.append(" Fill in"); 
/* 109 */     if ((0x200 & permissions) == 512)
/* 110 */       buf.append(" Screen readers"); 
/* 111 */     if ((0x400 & permissions) == 1024) buf.append(" Assembly"); 
/* 112 */     if ((0x4 & permissions) == 4)
/* 113 */       buf.append(" Degraded printing"); 
/* 114 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPrintingAllowed(int permissions) {
/* 124 */     return ((0x804 & permissions) == 2052);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isModifyContentsAllowed(int permissions) {
/* 134 */     return ((0x8 & permissions) == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCopyAllowed(int permissions) {
/* 144 */     return ((0x10 & permissions) == 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isModifyAnnotationsAllowed(int permissions) {
/* 154 */     return ((0x20 & permissions) == 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFillInAllowed(int permissions) {
/* 164 */     return ((0x100 & permissions) == 256);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isScreenReadersAllowed(int permissions) {
/* 174 */     return ((0x200 & permissions) == 512);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAssemblyAllowed(int permissions) {
/* 184 */     return ((0x400 & permissions) == 1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDegradedPrintingAllowed(int permissions) {
/* 194 */     return ((0x4 & permissions) == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getContent(RecipientInformation recipientInfo, PrivateKey certificateKey, String certificateKeyProvider) throws CMSException {
/* 201 */     JceKeyTransRecipient jceKeyTransRecipient = (new JceKeyTransEnvelopedRecipient(certificateKey)).setProvider(certificateKeyProvider);
/* 202 */     return recipientInfo.getContent((Recipient)jceKeyTransRecipient);
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
/*     */   public PdfEncryptor setEventCountingMetaInfo(IMetaInfo metaInfo) {
/* 214 */     this.metaInfo = metaInfo;
/* 215 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfEncryptor setEncryptionProperties(EncryptionProperties properties) {
/* 224 */     this.properties = properties;
/* 225 */     return this;
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
/*     */   public void encrypt(PdfReader reader, OutputStream os, Map<String, String> newInfo) {
/* 238 */     WriterProperties writerProperties = new WriterProperties();
/* 239 */     writerProperties.encryptionProperties = this.properties;
/* 240 */     PdfWriter writer = new PdfWriter(os, writerProperties);
/* 241 */     StampingProperties stampingProperties = new StampingProperties();
/* 242 */     stampingProperties.setEventCountingMetaInfo(this.metaInfo);
/* 243 */     PdfDocument document = new PdfDocument(reader, writer, stampingProperties);
/* 244 */     document.getDocumentInfo().setMoreInfo(newInfo);
/* 245 */     document.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encrypt(PdfReader reader, OutputStream os) {
/* 255 */     encrypt(reader, os, (Map<String, String>)null);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfEncryptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */