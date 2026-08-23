/*     */ package com.itextpdf.layout.hyphenation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HyphenationTreeCache
/*     */ {
/*  32 */   private Map<String, HyphenationTree> hyphenTrees = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> missingHyphenationTrees;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HyphenationTree getHyphenationTree(String lang, String country) {
/*  43 */     String key = constructLlccKey(lang, country);
/*     */     
/*  45 */     if (key == null) {
/*  46 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  50 */     if (this.hyphenTrees.containsKey(key))
/*  51 */       return this.hyphenTrees.get(key); 
/*  52 */     if (this.hyphenTrees.containsKey(lang)) {
/*  53 */       return this.hyphenTrees.get(lang);
/*     */     }
/*  55 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String constructLlccKey(String lang, String country) {
/*  66 */     String key = lang;
/*     */     
/*  68 */     if (country != null && !country.equals("none")) {
/*  69 */       key = key + "_" + country;
/*     */     }
/*  71 */     return key;
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
/*     */   public static String constructUserKey(String lang, String country, Map<String, String> hyphPatNames) {
/*  83 */     String userKey = null;
/*  84 */     if (hyphPatNames != null) {
/*  85 */       String key = constructLlccKey(lang, country);
/*  86 */       key = key.replace('_', '-');
/*  87 */       userKey = hyphPatNames.get(key);
/*     */     } 
/*  89 */     return userKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cache(String key, HyphenationTree hTree) {
/*  98 */     this.hyphenTrees.put(key, hTree);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void noteMissing(String key) {
/* 108 */     if (this.missingHyphenationTrees == null) {
/* 109 */       this.missingHyphenationTrees = new HashSet<>();
/*     */     }
/* 111 */     this.missingHyphenationTrees.add(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMissing(String key) {
/* 122 */     return (this.missingHyphenationTrees != null && this.missingHyphenationTrees.contains(key));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/HyphenationTreeCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */