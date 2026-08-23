/*     */ package com.itextpdf.styledxmlparser.util;
/*     */ 
/*     */ import com.itextpdf.io.util.DecimalFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.CssPropertyMerger;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StyleUtil
/*     */ {
/*  45 */   private static final List<String> fontSizeDependentPercentage = new ArrayList<>(2);
/*     */   
/*     */   static {
/*  48 */     fontSizeDependentPercentage.add("font-size");
/*  49 */     fontSizeDependentPercentage.add("line-height");
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
/*     */   public static Map<String, String> mergeParentStyleDeclaration(Map<String, String> styles, String styleProperty, String parentPropValue, String parentFontSizeString, Set<IStyleInheritance> inheritanceRules) {
/*  63 */     String childPropValue = styles.get(styleProperty);
/*  64 */     if ((childPropValue == null && checkInheritance(styleProperty, inheritanceRules)) || "inherit".equals(childPropValue)) {
/*  65 */       if (valueIsOfMeasurement(parentPropValue, "em") || 
/*  66 */         valueIsOfMeasurement(parentPropValue, "ex") || (
/*  67 */         valueIsOfMeasurement(parentPropValue, "%") && fontSizeDependentPercentage.contains(styleProperty))) {
/*  68 */         float absoluteParentFontSize = CssUtils.parseAbsoluteLength(parentFontSizeString);
/*     */         
/*  70 */         styles.put(styleProperty, 
/*  71 */             DecimalFormatUtil.formatNumber(CssUtils.parseRelativeValue(parentPropValue, absoluteParentFontSize), "0.####") + "pt");
/*     */       } else {
/*     */         
/*  74 */         styles.put(styleProperty, parentPropValue);
/*     */       } 
/*  76 */     } else if ("text-decoration-line".equals(styleProperty) && 
/*  77 */       !"inline-block".equals(styles.get("display"))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  84 */       styles.put(styleProperty, CssPropertyMerger.mergeTextDecoration(childPropValue, parentPropValue));
/*     */     } 
/*     */     
/*  87 */     return styles;
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
/*     */   private static boolean checkInheritance(String styleProperty, Set<IStyleInheritance> inheritanceRules) {
/*  99 */     for (IStyleInheritance inheritanceRule : inheritanceRules) {
/* 100 */       if (inheritanceRule.isInheritable(styleProperty)) {
/* 101 */         return true;
/*     */       }
/*     */     } 
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean valueIsOfMeasurement(String value, String measurement) {
/* 115 */     if (value == null) {
/* 116 */       return false;
/*     */     }
/* 118 */     return (value.endsWith(measurement) && 
/* 119 */       CssUtils.isNumericValue(value.substring(0, value.length() - measurement.length()).trim()));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/util/StyleUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */