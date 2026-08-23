/*     */ package com.itextpdf.styledxmlparser.css.util;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.CommonCssConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CssBackgroundUtils
/*     */ {
/*     */   public static String getBackgroundPropertyNameFromType(BackgroundPropertyType propertyType) {
/*  42 */     switch (propertyType) {
/*     */       case BACKGROUND_COLOR:
/*  44 */         return "background-color";
/*     */       case BACKGROUND_IMAGE:
/*  46 */         return "background-image";
/*     */       case BACKGROUND_POSITION:
/*  48 */         return "background-position";
/*     */       case BACKGROUND_POSITION_X:
/*  50 */         return "background-position-x";
/*     */       case BACKGROUND_POSITION_Y:
/*  52 */         return "background-position-y";
/*     */       case BACKGROUND_SIZE:
/*  54 */         return "background-size";
/*     */       case BACKGROUND_REPEAT:
/*  56 */         return "background-repeat";
/*     */       case BACKGROUND_ORIGIN:
/*  58 */         return "background-origin";
/*     */       case BACKGROUND_CLIP:
/*  60 */         return "background-clip";
/*     */       case BACKGROUND_ATTACHMENT:
/*  62 */         return "background-attachment";
/*     */     } 
/*  64 */     return "undefined";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BackgroundPropertyType resolveBackgroundPropertyType(String value) {
/*  75 */     String url = "url(";
/*  76 */     if (value.startsWith("url(") && value.indexOf('(', "url(".length()) == -1 && value
/*  77 */       .indexOf(')') == value.length() - 1) {
/*  78 */       return BackgroundPropertyType.BACKGROUND_IMAGE;
/*     */     }
/*  80 */     if (CssGradientUtil.isCssLinearGradientValue(value) || "none".equals(value)) {
/*  81 */       return BackgroundPropertyType.BACKGROUND_IMAGE;
/*     */     }
/*  83 */     if (CommonCssConstants.BACKGROUND_REPEAT_VALUES.contains(value)) {
/*  84 */       return BackgroundPropertyType.BACKGROUND_REPEAT;
/*     */     }
/*  86 */     if (CommonCssConstants.BACKGROUND_ATTACHMENT_VALUES.contains(value)) {
/*  87 */       return BackgroundPropertyType.BACKGROUND_ATTACHMENT;
/*     */     }
/*  89 */     if (CommonCssConstants.BACKGROUND_POSITION_X_VALUES.contains(value) && 
/*  90 */       !"center".equals(value)) {
/*  91 */       return BackgroundPropertyType.BACKGROUND_POSITION_X;
/*     */     }
/*  93 */     if (CommonCssConstants.BACKGROUND_POSITION_Y_VALUES.contains(value) && 
/*  94 */       !"center".equals(value)) {
/*  95 */       return BackgroundPropertyType.BACKGROUND_POSITION_Y;
/*     */     }
/*  97 */     if ("center".equals(value)) {
/*  98 */       return BackgroundPropertyType.BACKGROUND_POSITION;
/*     */     }
/* 100 */     if (Integer.valueOf(0).equals(CssUtils.parseInteger(value)) || 
/* 101 */       CssUtils.isMetricValue(value) || CssUtils.isRelativeValue(value)) {
/* 102 */       return BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE;
/*     */     }
/* 104 */     if (CommonCssConstants.BACKGROUND_SIZE_VALUES.contains(value)) {
/* 105 */       return BackgroundPropertyType.BACKGROUND_SIZE;
/*     */     }
/* 107 */     if (CssUtils.isColorProperty(value)) {
/* 108 */       return BackgroundPropertyType.BACKGROUND_COLOR;
/*     */     }
/* 110 */     if (CommonCssConstants.BACKGROUND_ORIGIN_OR_CLIP_VALUES.contains(value)) {
/* 111 */       return BackgroundPropertyType.BACKGROUND_ORIGIN_OR_CLIP;
/*     */     }
/* 113 */     return BackgroundPropertyType.UNDEFINED;
/*     */   }
/*     */   
/*     */   public enum BackgroundPropertyType {
/* 117 */     BACKGROUND_COLOR,
/* 118 */     BACKGROUND_IMAGE,
/* 119 */     BACKGROUND_POSITION,
/* 120 */     BACKGROUND_POSITION_X,
/* 121 */     BACKGROUND_POSITION_Y,
/* 122 */     BACKGROUND_SIZE,
/* 123 */     BACKGROUND_REPEAT,
/* 124 */     BACKGROUND_ORIGIN,
/* 125 */     BACKGROUND_CLIP,
/* 126 */     BACKGROUND_ATTACHMENT,
/* 127 */     BACKGROUND_POSITION_OR_SIZE,
/* 128 */     BACKGROUND_ORIGIN_OR_CLIP,
/* 129 */     UNDEFINED;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/util/CssBackgroundUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */