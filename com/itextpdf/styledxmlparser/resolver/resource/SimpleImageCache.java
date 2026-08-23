/*     */ package com.itextpdf.styledxmlparser.resolver.resource;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import java.util.LinkedHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class SimpleImageCache
/*     */ {
/*  56 */   private Map<String, PdfXObject> cache = new LinkedHashMap<>();
/*     */ 
/*     */   
/*  59 */   private Map<String, Integer> imagesFrequency = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private int capacity;
/*     */ 
/*     */ 
/*     */   
/*     */   SimpleImageCache() {
/*  68 */     this.capacity = 100;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SimpleImageCache(int capacity) {
/*  77 */     if (capacity < 1) {
/*  78 */       throw new IllegalArgumentException("capacity");
/*     */     }
/*  80 */     this.capacity = capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void putImage(String src, PdfXObject imageXObject) {
/*  90 */     if (this.cache.containsKey(src)) {
/*     */       return;
/*     */     }
/*  93 */     ensureCapacity();
/*  94 */     this.cache.put(src, imageXObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfXObject getImage(String src) {
/* 104 */     Integer frequency = this.imagesFrequency.get(src);
/* 105 */     if (frequency != null) {
/* 106 */       this.imagesFrequency.put(src, Integer.valueOf(frequency.intValue() + 1));
/*     */     } else {
/* 108 */       this.imagesFrequency.put(src, Integer.valueOf(1));
/*     */     } 
/*     */     
/* 111 */     return this.cache.get(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int size() {
/* 120 */     return this.cache.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void reset() {
/* 127 */     this.cache.clear();
/* 128 */     this.imagesFrequency.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureCapacity() {
/* 136 */     if (this.cache.size() >= this.capacity) {
/* 137 */       String mostUnpopularImg = null;
/* 138 */       int minFrequency = Integer.MAX_VALUE;
/*     */ 
/*     */       
/* 141 */       for (String imgSrc : this.cache.keySet()) {
/* 142 */         Integer imgFrequency = this.imagesFrequency.get(imgSrc);
/* 143 */         if (imgFrequency == null || imgFrequency.intValue() < minFrequency) {
/* 144 */           mostUnpopularImg = imgSrc;
/* 145 */           if (imgFrequency == null) {
/*     */             break;
/*     */           }
/* 148 */           minFrequency = imgFrequency.intValue();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 153 */       this.cache.remove(mostUnpopularImg);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/resolver/resource/SimpleImageCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */