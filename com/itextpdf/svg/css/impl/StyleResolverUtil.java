/*     */ package com.itextpdf.svg.css.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.DecimalFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.CssInheritance;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.CssPropertyMerger;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class StyleResolverUtil
/*     */ {
/*     */   private Set<IStyleInheritance> inheritanceRules;
/*  71 */   private static final List<String> fontSizeDependentPercentage = new ArrayList<>(2);
/*     */   
/*     */   static {
/*  74 */     fontSizeDependentPercentage.add("font-size");
/*  75 */     fontSizeDependentPercentage.add("line-height");
/*     */   }
/*     */   
/*     */   public StyleResolverUtil() {
/*  79 */     this.inheritanceRules = new HashSet<>();
/*  80 */     this.inheritanceRules.add(new CssInheritance());
/*  81 */     this.inheritanceRules.add(new SvgAttributeInheritance());
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
/*     */   public void mergeParentStyleDeclaration(Map<String, String> styles, String styleProperty, String parentPropValue, String parentFontSizeString) {
/*  93 */     String childPropValue = styles.get(styleProperty);
/*     */     
/*  95 */     if ((childPropValue == null && checkInheritance(styleProperty)) || "inherit".equals(childPropValue)) {
/*  96 */       if (valueIsOfMeasurement(parentPropValue, "em") || 
/*  97 */         valueIsOfMeasurement(parentPropValue, "ex") || (
/*  98 */         valueIsOfMeasurement(parentPropValue, "%") && fontSizeDependentPercentage.contains(styleProperty))) {
/*     */         
/* 100 */         float absoluteParentFontSize = CssUtils.parseAbsoluteLength(parentFontSizeString);
/*     */         
/* 102 */         styles.put(styleProperty, DecimalFormatUtil.formatNumber(CssUtils.parseRelativeValue(parentPropValue, absoluteParentFontSize), "0.####") + "pt");
/*     */       }
/*     */       else {
/*     */         
/* 106 */         styles.put(styleProperty, parentPropValue);
/*     */       } 
/* 108 */     } else if (("text-decoration-line".equals(styleProperty) || "text-decoration".equals(styleProperty)) && !"inline-block".equals(styles.get("display"))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 115 */       styles.put(styleProperty, CssPropertyMerger.mergeTextDecoration(childPropValue, parentPropValue));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkInheritance(String styleProperty) {
/* 127 */     for (IStyleInheritance inheritanceRule : this.inheritanceRules) {
/* 128 */       if (inheritanceRule.isInheritable(styleProperty)) {
/* 129 */         return true;
/*     */       }
/*     */     } 
/* 132 */     return false;
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
/* 143 */     if (value == null)
/* 144 */       return false; 
/* 145 */     if (value.endsWith(measurement) && CssUtils.isNumericValue(value.substring(0, value.length() - measurement.length()).trim()))
/* 146 */       return true; 
/* 147 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/css/impl/StyleResolverUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */