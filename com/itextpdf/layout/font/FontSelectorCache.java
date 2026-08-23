/*     */ package com.itextpdf.layout.font;
/*     */ 
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
/*     */ class FontSelectorCache
/*     */ {
/*     */   private final FontSetSelectors defaultSelectors;
/*     */   private final FontSet defaultFontSet;
/*  52 */   private final Map<Long, FontSetSelectors> caches = new HashMap<>();
/*     */   
/*     */   FontSelectorCache(FontSet defaultFontSet) {
/*  55 */     assert defaultFontSet != null;
/*  56 */     this.defaultSelectors = new FontSetSelectors();
/*  57 */     this.defaultSelectors.update(defaultFontSet);
/*  58 */     this.defaultFontSet = defaultFontSet;
/*     */   }
/*     */   
/*     */   FontSelector get(FontSelectorKey key) {
/*  62 */     if (update(null, null)) {
/*  63 */       return null;
/*     */     }
/*  65 */     return this.defaultSelectors.map.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   FontSelector get(FontSelectorKey key, FontSet additionalFonts) {
/*  70 */     if (additionalFonts == null) {
/*  71 */       return get(key);
/*     */     }
/*  73 */     FontSetSelectors selectors = this.caches.get(Long.valueOf(additionalFonts.getId()));
/*  74 */     if (selectors == null) {
/*  75 */       this.caches.put(Long.valueOf(additionalFonts.getId()), selectors = new FontSetSelectors());
/*     */     }
/*  77 */     if (update(selectors, additionalFonts)) {
/*  78 */       return null;
/*     */     }
/*  80 */     return selectors.map.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void put(FontSelectorKey key, FontSelector fontSelector) {
/*  87 */     update(null, null);
/*  88 */     this.defaultSelectors.map.put(key, fontSelector);
/*     */   }
/*     */   
/*     */   void put(FontSelectorKey key, FontSelector fontSelector, FontSet fontSet) {
/*  92 */     if (fontSet == null) {
/*  93 */       put(key, fontSelector);
/*     */     } else {
/*  95 */       FontSetSelectors selectors = this.caches.get(Long.valueOf(fontSet.getId()));
/*  96 */       if (selectors == null) {
/*  97 */         this.caches.put(Long.valueOf(fontSet.getId()), selectors = new FontSetSelectors());
/*     */       }
/*     */       
/* 100 */       update(selectors, fontSet);
/* 101 */       selectors.map.put(key, fontSelector);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean update(FontSetSelectors selectors, FontSet fontSet) {
/* 106 */     boolean updated = false;
/* 107 */     if (this.defaultSelectors.update(this.defaultFontSet)) {
/* 108 */       updated = true;
/*     */     }
/* 110 */     if (selectors != null && selectors.update(fontSet)) {
/* 111 */       updated = true;
/*     */     }
/* 113 */     return updated;
/*     */   }
/*     */   
/*     */   private static class FontSetSelectors {
/* 117 */     final Map<FontSelectorKey, FontSelector> map = new HashMap<>();
/* 118 */     private int fontSetSize = -1;
/*     */     
/*     */     boolean update(FontSet fontSet) {
/* 121 */       assert fontSet != null;
/* 122 */       if (this.fontSetSize == fontSet.size()) {
/* 123 */         return false;
/*     */       }
/* 125 */       this.map.clear();
/* 126 */       this.fontSetSize = fontSet.size();
/* 127 */       return true;
/*     */     }
/*     */     
/*     */     private FontSetSelectors() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontSelectorCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */