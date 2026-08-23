/*     */ package com.itextpdf.styledxmlparser.css.validate.impl.datatype;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.CommonCssConstants;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssBackgroundUtils;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssBackgroundValidator
/*     */   implements ICssDataTypeValidator
/*     */ {
/*     */   private static final int MAX_AMOUNT_OF_VALUES = 2;
/*     */   private final String backgroundProperty;
/*     */   
/*     */   public CssBackgroundValidator(String backgroundProperty) {
/*  48 */     this.backgroundProperty = backgroundProperty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String objectString) {
/*  56 */     if (objectString == null) {
/*  57 */       return false;
/*     */     }
/*  59 */     if (CssUtils.isInitialOrInheritOrUnset(objectString)) {
/*  60 */       return true;
/*     */     }
/*     */     
/*  63 */     List<List<String>> extractedProperties = CssUtils.extractShorthandProperties(objectString);
/*  64 */     for (List<String> propertyValues : extractedProperties) {
/*  65 */       if (propertyValues.isEmpty() || propertyValues.size() > 2) {
/*  66 */         return false;
/*     */       }
/*  68 */       for (int i = 0; i < propertyValues.size(); i++) {
/*  69 */         if (!isValidProperty(propertyValues, i)) {
/*  70 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isValidProperty(List<String> propertyValues, int index) {
/*  78 */     if (isPropertyValueCorrespondsPropertyType(propertyValues.get(index))) {
/*  79 */       if (propertyValues.size() == 2) {
/*  80 */         if (isMultiValueAllowedForThisType() && isMultiValueAllowedForThisValue(propertyValues.get(index)))
/*     */         {
/*  82 */           return checkMultiValuePositionXY(propertyValues, index);
/*     */         }
/*  84 */         return false;
/*     */       } 
/*     */       
/*  87 */       return true;
/*     */     } 
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   private boolean checkMultiValuePositionXY(List<String> propertyValues, int index) {
/*  93 */     if ("background-position-x".equals(this.backgroundProperty) || "background-position-y"
/*  94 */       .equals(this.backgroundProperty)) {
/*  95 */       if (CommonCssConstants.BACKGROUND_POSITION_VALUES.contains(propertyValues.get(index)) && index == 1) {
/*  96 */         return false;
/*     */       }
/*  98 */       return (CommonCssConstants.BACKGROUND_POSITION_VALUES.contains(propertyValues.get(index)) || index == 1);
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isMultiValueAllowedForThisType() {
/* 104 */     return (!"background-origin".equals(this.backgroundProperty) && 
/* 105 */       !"background-clip".equals(this.backgroundProperty) && 
/* 106 */       !"background-image".equals(this.backgroundProperty) && 
/* 107 */       !"background-attachment".equals(this.backgroundProperty));
/*     */   }
/*     */   
/*     */   private static boolean isMultiValueAllowedForThisValue(String value) {
/* 111 */     return (!"repeat-x".equals(value) && 
/* 112 */       !"repeat-y".equals(value) && 
/* 113 */       !"cover".equals(value) && 
/* 114 */       !"contain".equals(value) && 
/* 115 */       !"center".equals(value));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPropertyValueCorrespondsPropertyType(String value) {
/* 120 */     CssBackgroundUtils.BackgroundPropertyType propertyType = CssBackgroundUtils.resolveBackgroundPropertyType(value);
/* 121 */     if (propertyType == CssBackgroundUtils.BackgroundPropertyType.UNDEFINED) {
/* 122 */       return false;
/*     */     }
/* 124 */     if (CssBackgroundUtils.getBackgroundPropertyNameFromType(propertyType).equals(this.backgroundProperty)) {
/* 125 */       return true;
/*     */     }
/* 127 */     if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION && ("background-position-x"
/* 128 */       .equals(this.backgroundProperty) || "background-position-y"
/* 129 */       .equals(this.backgroundProperty))) {
/* 130 */       return true;
/*     */     }
/* 132 */     if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN_OR_CLIP && ("background-clip"
/* 133 */       .equals(this.backgroundProperty) || "background-origin"
/* 134 */       .equals(this.backgroundProperty))) {
/* 135 */       return true;
/*     */     }
/* 137 */     return (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE && ("background-position-x"
/* 138 */       .equals(this.backgroundProperty) || "background-position-y"
/* 139 */       .equals(this.backgroundProperty) || "background-size"
/* 140 */       .equals(this.backgroundProperty)));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/datatype/CssBackgroundValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */