/*     */ package com.itextpdf.styledxmlparser.css.resolve;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CssPropertyMerger
/*     */ {
/*     */   public static String mergeTextDecoration(String firstValue, String secondValue) {
/*  69 */     if (firstValue == null)
/*  70 */       return secondValue; 
/*  71 */     if (secondValue == null) {
/*  72 */       return firstValue;
/*     */     }
/*     */     
/*  75 */     Set<String> merged = normalizeTextDecoration(firstValue);
/*  76 */     merged.addAll(normalizeTextDecoration(secondValue));
/*     */     
/*  78 */     StringBuilder sb = new StringBuilder();
/*  79 */     for (String mergedProp : merged) {
/*  80 */       if (sb.length() != 0) {
/*  81 */         sb.append(" ");
/*     */       }
/*  83 */       sb.append(mergedProp);
/*     */     } 
/*  85 */     return (sb.length() != 0) ? sb.toString() : "none";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Set<String> normalizeTextDecoration(String value) {
/*  95 */     String[] parts = value.split("\\s+");
/*     */     
/*  97 */     Set<String> merged = new LinkedHashSet<>();
/*  98 */     merged.addAll(Arrays.asList(parts));
/*     */     
/* 100 */     if (merged.contains("none")) {
/* 101 */       merged.clear();
/*     */     }
/* 103 */     return merged;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/CssPropertyMerger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */