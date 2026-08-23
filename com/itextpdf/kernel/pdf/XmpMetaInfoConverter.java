/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.xmp.XMPException;
/*     */ import com.itextpdf.kernel.xmp.XMPMeta;
/*     */ import com.itextpdf.kernel.xmp.XMPMetaFactory;
/*     */ import com.itextpdf.kernel.xmp.options.PropertyOptions;
/*     */ import com.itextpdf.kernel.xmp.properties.XMPProperty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XmpMetaInfoConverter
/*     */ {
/*     */   static void appendMetadataToInfo(byte[] xmpMetadata, PdfDocumentInfo info) {
/*  60 */     if (xmpMetadata != null) {
/*     */       try {
/*  62 */         XMPMeta meta = XMPMetaFactory.parseFromBuffer(xmpMetadata);
/*     */         
/*  64 */         XMPProperty title = meta.getLocalizedText("http://purl.org/dc/elements/1.1/", "title", "x-default", "x-default");
/*  65 */         if (title != null) {
/*  66 */           info.setTitle(title.getValue());
/*     */         }
/*     */         
/*  69 */         String author = fetchArrayIntoString(meta, "http://purl.org/dc/elements/1.1/", "creator");
/*  70 */         if (author != null) {
/*  71 */           info.setAuthor(author);
/*     */         }
/*     */ 
/*     */         
/*  75 */         XMPProperty keywords = meta.getProperty("http://ns.adobe.com/pdf/1.3/", "Keywords");
/*  76 */         if (keywords != null) {
/*  77 */           info.setKeywords(keywords.getValue());
/*     */         } else {
/*  79 */           String keywordsStr = fetchArrayIntoString(meta, "http://purl.org/dc/elements/1.1/", "subject");
/*  80 */           if (keywordsStr != null) {
/*  81 */             info.setKeywords(keywordsStr);
/*     */           }
/*     */         } 
/*     */         
/*  85 */         XMPProperty subject = meta.getLocalizedText("http://purl.org/dc/elements/1.1/", "description", "x-default", "x-default");
/*  86 */         if (subject != null) {
/*  87 */           info.setSubject(subject.getValue());
/*     */         }
/*     */         
/*  90 */         XMPProperty creator = meta.getProperty("http://ns.adobe.com/xap/1.0/", "CreatorTool");
/*  91 */         if (creator != null) {
/*  92 */           info.setCreator(creator.getValue());
/*     */         }
/*     */         
/*  95 */         XMPProperty producer = meta.getProperty("http://ns.adobe.com/pdf/1.3/", "Producer");
/*  96 */         if (producer != null) {
/*  97 */           info.put(PdfName.Producer, new PdfString(producer.getValue(), "UnicodeBig"));
/*     */         }
/*     */         
/* 100 */         XMPProperty trapped = meta.getProperty("http://ns.adobe.com/pdf/1.3/", "Trapped");
/* 101 */         if (trapped != null) {
/* 102 */           info.setTrapped(new PdfName(trapped.getValue()));
/*     */         }
/* 104 */       } catch (XMPException xMPException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void appendDocumentInfoToMetadata(PdfDocumentInfo info, XMPMeta xmpMeta) throws XMPException {
/* 111 */     PdfDictionary docInfo = info.getPdfObject();
/* 112 */     if (docInfo != null)
/*     */     {
/*     */ 
/*     */       
/* 116 */       for (PdfName pdfName : docInfo.keySet()) {
/* 117 */         String value; PdfName key = pdfName;
/* 118 */         PdfObject obj = docInfo.get(key);
/* 119 */         if (obj == null)
/*     */           continue; 
/* 121 */         if (obj.isString()) {
/* 122 */           value = ((PdfString)obj).toUnicodeString();
/* 123 */         } else if (obj.isName()) {
/* 124 */           value = ((PdfName)obj).getValue();
/*     */         } else {
/*     */           continue;
/*     */         } 
/* 128 */         if (PdfName.Title.equals(key)) {
/* 129 */           xmpMeta.setLocalizedText("http://purl.org/dc/elements/1.1/", "title", "x-default", "x-default", value); continue;
/* 130 */         }  if (PdfName.Author.equals(key)) {
/* 131 */           for (String v : value.split(",|;")) {
/* 132 */             if (v.trim().length() > 0)
/* 133 */               appendArrayItemIfDoesNotExist(xmpMeta, "http://purl.org/dc/elements/1.1/", "creator", v.trim(), 1024); 
/*     */           }  continue;
/*     */         } 
/* 136 */         if (PdfName.Subject.equals(key)) {
/* 137 */           xmpMeta.setLocalizedText("http://purl.org/dc/elements/1.1/", "description", "x-default", "x-default", value); continue;
/* 138 */         }  if (PdfName.Keywords.equals(key)) {
/* 139 */           for (String v : value.split(",|;")) {
/* 140 */             if (v.trim().length() > 0) {
/* 141 */               appendArrayItemIfDoesNotExist(xmpMeta, "http://purl.org/dc/elements/1.1/", "subject", v.trim(), 512);
/*     */             }
/*     */           } 
/* 144 */           xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Keywords", value); continue;
/* 145 */         }  if (PdfName.Creator.equals(key)) {
/* 146 */           xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "CreatorTool", value); continue;
/* 147 */         }  if (PdfName.Producer.equals(key)) {
/* 148 */           xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Producer", value); continue;
/* 149 */         }  if (PdfName.CreationDate.equals(key)) {
/* 150 */           xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "CreateDate", PdfDate.getW3CDate(value)); continue;
/* 151 */         }  if (PdfName.ModDate.equals(key)) {
/* 152 */           xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", "ModifyDate", PdfDate.getW3CDate(value)); continue;
/* 153 */         }  if (PdfName.Trapped.equals(key)) {
/* 154 */           xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Trapped", value);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void appendArrayItemIfDoesNotExist(XMPMeta meta, String ns, String arrayName, String value, int arrayOption) throws XMPException {
/* 161 */     int currentCnt = meta.countArrayItems(ns, arrayName);
/* 162 */     for (int i = 0; i < currentCnt; i++) {
/* 163 */       XMPProperty item = meta.getArrayItem(ns, arrayName, i + 1);
/* 164 */       if (value.equals(item.getValue())) {
/*     */         return;
/*     */       }
/*     */     } 
/* 168 */     meta.appendArrayItem(ns, arrayName, new PropertyOptions(arrayOption), value, null);
/*     */   }
/*     */   
/*     */   private static String fetchArrayIntoString(XMPMeta meta, String ns, String arrayName) throws XMPException {
/* 172 */     int keywordsCnt = meta.countArrayItems(ns, arrayName);
/* 173 */     StringBuilder sb = null;
/* 174 */     for (int i = 0; i < keywordsCnt; i++) {
/* 175 */       XMPProperty curKeyword = meta.getArrayItem(ns, arrayName, i + 1);
/* 176 */       if (sb == null) {
/* 177 */         sb = new StringBuilder();
/* 178 */       } else if (sb.length() > 0) {
/* 179 */         sb.append("; ");
/*     */       } 
/* 181 */       sb.append(curKeyword.getValue());
/*     */     } 
/* 183 */     return (sb != null) ? sb.toString() : null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/XmpMetaInfoConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */