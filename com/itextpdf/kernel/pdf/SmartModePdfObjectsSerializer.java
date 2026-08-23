/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import java.io.Serializable;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.HashMap;
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
/*     */ class SmartModePdfObjectsSerializer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2502203520776244051L;
/*     */   private transient MessageDigest md5;
/*  57 */   private HashMap<SerializedObjectContent, PdfIndirectReference> serializedContentToObj = new HashMap<>();
/*     */   
/*     */   SmartModePdfObjectsSerializer() {
/*     */     try {
/*  61 */       this.md5 = MessageDigest.getInstance("MD5");
/*  62 */     } catch (Exception e) {
/*  63 */       throw new PdfException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveSerializedObject(SerializedObjectContent serializedContent, PdfIndirectReference objectReference) {
/*  68 */     this.serializedContentToObj.put(serializedContent, objectReference);
/*     */   }
/*     */   
/*     */   public PdfIndirectReference getSavedSerializedObject(SerializedObjectContent serializedContent) {
/*  72 */     if (serializedContent != null) {
/*  73 */       return this.serializedContentToObj.get(serializedContent);
/*     */     }
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   public SerializedObjectContent serializeObject(PdfObject obj) {
/*  79 */     if (!obj.isStream() && !obj.isDictionary()) {
/*  80 */       return null;
/*     */     }
/*  82 */     PdfIndirectReference indRef = obj.getIndirectReference();
/*  83 */     assert indRef != null;
/*  84 */     Map<PdfIndirectReference, byte[]> serializedCache = (indRef.getDocument()).serializedObjectsCache;
/*     */     
/*  86 */     byte[] content = serializedCache.get(indRef);
/*  87 */     if (content == null) {
/*  88 */       ByteBuffer bb = new ByteBuffer();
/*  89 */       int level = 100;
/*     */       try {
/*  91 */         serObject(obj, bb, level, serializedCache);
/*  92 */       } catch (SelfReferenceException e) {
/*  93 */         return null;
/*     */       } 
/*  95 */       content = bb.toByteArray();
/*     */     } 
/*  97 */     return new SerializedObjectContent(content);
/*     */   }
/*     */   
/*     */   private void serObject(PdfObject obj, ByteBuffer bb, int level, Map<PdfIndirectReference, byte[]> serializedCache) throws SelfReferenceException {
/* 101 */     if (level <= 0) {
/*     */       return;
/*     */     }
/* 104 */     if (obj == null) {
/* 105 */       bb.append("$Lnull");
/*     */       return;
/*     */     } 
/* 108 */     PdfIndirectReference reference = null;
/* 109 */     ByteBuffer savedBb = null;
/*     */     
/* 111 */     if (obj.isIndirectReference()) {
/* 112 */       reference = (PdfIndirectReference)obj;
/* 113 */       byte[] cached = serializedCache.get(reference);
/* 114 */       if (cached != null) {
/* 115 */         bb.append(cached);
/*     */         
/*     */         return;
/*     */       } 
/* 119 */       if (serializedCache.keySet().contains(reference))
/*     */       {
/* 121 */         throw new SelfReferenceException();
/*     */       }
/* 123 */       serializedCache.put(reference, null);
/*     */       
/* 125 */       savedBb = bb;
/* 126 */       bb = new ByteBuffer();
/* 127 */       obj = reference.getRefersTo();
/*     */     } 
/*     */ 
/*     */     
/* 131 */     if (obj.isStream()) {
/* 132 */       serDic((PdfDictionary)obj, bb, level - 1, serializedCache);
/* 133 */       bb.append("$B");
/* 134 */       if (level > 0) {
/* 135 */         bb.append(this.md5.digest(((PdfStream)obj).getBytes(false)));
/*     */       }
/* 137 */     } else if (obj.isDictionary()) {
/* 138 */       serDic((PdfDictionary)obj, bb, level - 1, serializedCache);
/* 139 */     } else if (obj.isArray()) {
/* 140 */       serArray((PdfArray)obj, bb, level - 1, serializedCache);
/* 141 */     } else if (obj.isString()) {
/*     */       
/* 143 */       bb.append("$S").append(obj.toString());
/* 144 */     } else if (obj.isName()) {
/* 145 */       bb.append("$N").append(obj.toString());
/*     */     } else {
/*     */       
/* 148 */       bb.append("$L").append(obj.toString());
/*     */     } 
/*     */     
/* 151 */     if (savedBb != null) {
/* 152 */       serializedCache.put(reference, bb.toByteArray());
/* 153 */       savedBb.append(bb.getInternalBuffer(), 0, bb.size());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void serDic(PdfDictionary dic, ByteBuffer bb, int level, Map<PdfIndirectReference, byte[]> serializedCache) throws SelfReferenceException {
/* 159 */     bb.append("$D");
/* 160 */     if (level <= 0)
/*     */       return; 
/* 162 */     for (PdfName key : dic.keySet()) {
/* 163 */       if (isKeyRefersBack(dic, key)) {
/*     */         continue;
/*     */       }
/* 166 */       serObject(key, bb, level, serializedCache);
/* 167 */       serObject(dic.get(key, false), bb, level, serializedCache);
/*     */     } 
/*     */     
/* 170 */     bb.append("$\\D");
/*     */   }
/*     */ 
/*     */   
/*     */   private void serArray(PdfArray array, ByteBuffer bb, int level, Map<PdfIndirectReference, byte[]> serializedCache) throws SelfReferenceException {
/* 175 */     bb.append("$A");
/* 176 */     if (level <= 0)
/*     */       return; 
/* 178 */     for (int k = 0; k < array.size(); k++) {
/* 179 */       serObject(array.get(k, false), bb, level, serializedCache);
/*     */     }
/* 181 */     bb.append("$\\A");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isKeyRefersBack(PdfDictionary dic, PdfName key) {
/* 187 */     return ((key.equals(PdfName.P) && (dic.get(key).isIndirectReference() || dic.get(key).isDictionary())) || key
/* 188 */       .equals(PdfName.Parent));
/*     */   }
/*     */   
/*     */   private static class SelfReferenceException extends Exception {
/*     */     private SelfReferenceException() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/SmartModePdfObjectsSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */