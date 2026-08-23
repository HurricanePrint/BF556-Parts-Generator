/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfDocumentInfo
/*     */   implements Serializable
/*     */ {
/*  53 */   static final PdfName[] PDF20_DEPRECATED_KEYS = new PdfName[] { PdfName.Title, PdfName.Author, PdfName.Subject, PdfName.Keywords, PdfName.Creator, PdfName.Producer, PdfName.Trapped };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -21957940280527125L;
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfDictionary infoDictionary;
/*     */ 
/*     */ 
/*     */   
/*     */   PdfDocumentInfo(PdfDictionary pdfObject, PdfDocument pdfDocument) {
/*  66 */     this.infoDictionary = pdfObject;
/*  67 */     if (pdfDocument.getWriter() != null) {
/*  68 */       this.infoDictionary.makeIndirect(pdfDocument);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfDocumentInfo(PdfDocument pdfDocument) {
/*  78 */     this(new PdfDictionary(), pdfDocument);
/*     */   }
/*     */   
/*     */   public PdfDocumentInfo setTitle(String title) {
/*  82 */     return put(PdfName.Title, new PdfString(title, "UnicodeBig"));
/*     */   }
/*     */   
/*     */   public PdfDocumentInfo setAuthor(String author) {
/*  86 */     return put(PdfName.Author, new PdfString(author, "UnicodeBig"));
/*     */   }
/*     */   
/*     */   public PdfDocumentInfo setSubject(String subject) {
/*  90 */     return put(PdfName.Subject, new PdfString(subject, "UnicodeBig"));
/*     */   }
/*     */   
/*     */   public PdfDocumentInfo setKeywords(String keywords) {
/*  94 */     return put(PdfName.Keywords, new PdfString(keywords, "UnicodeBig"));
/*     */   }
/*     */   
/*     */   public PdfDocumentInfo setCreator(String creator) {
/*  98 */     return put(PdfName.Creator, new PdfString(creator, "UnicodeBig"));
/*     */   }
/*     */   
/*     */   public PdfDocumentInfo setTrapped(PdfName trapped) {
/* 102 */     return put(PdfName.Trapped, trapped);
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 106 */     return getStringValue(PdfName.Title);
/*     */   }
/*     */   
/*     */   public String getAuthor() {
/* 110 */     return getStringValue(PdfName.Author);
/*     */   }
/*     */   
/*     */   public String getSubject() {
/* 114 */     return getStringValue(PdfName.Subject);
/*     */   }
/*     */   
/*     */   public String getKeywords() {
/* 118 */     return getStringValue(PdfName.Keywords);
/*     */   }
/*     */   
/*     */   public String getCreator() {
/* 122 */     return getStringValue(PdfName.Creator);
/*     */   }
/*     */   
/*     */   public String getProducer() {
/* 126 */     return getStringValue(PdfName.Producer);
/*     */   }
/*     */   
/*     */   public PdfName getTrapped() {
/* 130 */     return this.infoDictionary.getAsName(PdfName.Trapped);
/*     */   }
/*     */   
/*     */   public PdfDocumentInfo addCreationDate() {
/* 134 */     return put(PdfName.CreationDate, (new PdfDate()).getPdfObject());
/*     */   }
/*     */   
/*     */   public PdfDocumentInfo addModDate() {
/* 138 */     return put(PdfName.ModDate, (new PdfDate()).getPdfObject());
/*     */   }
/*     */   
/*     */   public void setMoreInfo(Map<String, String> moreInfo) {
/* 142 */     if (moreInfo != null) {
/* 143 */       for (Map.Entry<String, String> entry : moreInfo.entrySet()) {
/* 144 */         String key = entry.getKey();
/* 145 */         String value = entry.getValue();
/* 146 */         setMoreInfo(key, value);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void setMoreInfo(String key, String value) {
/* 152 */     PdfName keyName = new PdfName(key);
/* 153 */     if (value == null) {
/* 154 */       this.infoDictionary.remove(keyName);
/* 155 */       this.infoDictionary.setModified();
/*     */     } else {
/* 157 */       put(keyName, new PdfString(value, "UnicodeBig"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getMoreInfo(String key) {
/* 162 */     return getStringValue(new PdfName(key));
/*     */   }
/*     */   
/*     */   PdfDictionary getPdfObject() {
/* 166 */     return this.infoDictionary;
/*     */   }
/*     */   
/*     */   PdfDocumentInfo put(PdfName key, PdfObject value) {
/* 170 */     getPdfObject().put(key, value);
/* 171 */     getPdfObject().setModified();
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   private String getStringValue(PdfName name) {
/* 176 */     PdfString pdfString = this.infoDictionary.getAsString(name);
/* 177 */     return (pdfString != null) ? pdfString.toUnicodeString() : null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfDocumentInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */