/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.layout.property.BackgroundImage;
/*     */ import com.itextpdf.layout.property.BackgroundSize;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BackgroundSizeCalculationUtil
/*     */ {
/*     */   private static final int PERCENT_100 = 100;
/*  56 */   private static final UnitValue PERCENT_VALUE_100 = UnitValue.createPercentValue(100.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] calculateBackgroundImageSize(BackgroundImage image, float areaWidth, float areaHeight) {
/*     */     BackgroundSize size;
/*  73 */     boolean isGradient = (image.getLinearGradientBuilder() != null);
/*     */ 
/*     */     
/*  76 */     if (!isGradient && image.getBackgroundSize().isSpecificSize()) {
/*  77 */       size = calculateBackgroundSizeForArea(image, areaWidth, areaHeight);
/*     */     } else {
/*  79 */       size = image.getBackgroundSize();
/*     */     } 
/*  81 */     UnitValue width = size.getBackgroundWidthSize();
/*  82 */     UnitValue height = size.getBackgroundHeightSize();
/*     */     
/*  84 */     Float[] widthAndHeight = new Float[2];
/*     */     
/*  86 */     if (width != null && width.getValue() >= 0.0F) {
/*  87 */       boolean needScale = (!isGradient && height == null);
/*  88 */       calculateBackgroundWidth(width, areaWidth, needScale, image, widthAndHeight);
/*     */     } 
/*  90 */     if (height != null && height.getValue() >= 0.0F) {
/*  91 */       boolean needScale = (!isGradient && width == null);
/*  92 */       calculateBackgroundHeight(height, areaHeight, needScale, image, widthAndHeight);
/*     */     } 
/*  94 */     setDefaultSizeIfNull(widthAndHeight, areaWidth, areaHeight, image, isGradient);
/*  95 */     return new float[] { widthAndHeight[0].floatValue(), widthAndHeight[1].floatValue() };
/*     */   }
/*     */ 
/*     */   
/*     */   private static BackgroundSize calculateBackgroundSizeForArea(BackgroundImage image, float areaWidth, float areaHeight) {
/* 100 */     double widthDifference = (areaWidth / image.getImageWidth());
/* 101 */     double heightDifference = (areaHeight / image.getImageHeight());
/* 102 */     if (image.getBackgroundSize().isCover())
/* 103 */       return createSizeWithMaxValueSide((widthDifference > heightDifference)); 
/* 104 */     if (image.getBackgroundSize().isContain()) {
/* 105 */       return createSizeWithMaxValueSide((widthDifference < heightDifference));
/*     */     }
/* 107 */     return new BackgroundSize();
/*     */   }
/*     */ 
/*     */   
/*     */   private static BackgroundSize createSizeWithMaxValueSide(boolean maxWidth) {
/* 112 */     BackgroundSize size = new BackgroundSize();
/* 113 */     if (maxWidth) {
/* 114 */       size.setBackgroundSizeToValues(PERCENT_VALUE_100, null);
/*     */     } else {
/* 116 */       size.setBackgroundSizeToValues(null, PERCENT_VALUE_100);
/*     */     } 
/* 118 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void calculateBackgroundWidth(UnitValue width, float areaWidth, boolean scale, BackgroundImage image, Float[] widthAndHeight) {
/* 123 */     if (scale) {
/* 124 */       if (width.isPercentValue()) {
/* 125 */         scaleWidth(areaWidth * width.getValue() / 100.0F, image, widthAndHeight);
/*     */       } else {
/* 127 */         scaleWidth(width.getValue(), image, widthAndHeight);
/*     */       }
/*     */     
/* 130 */     } else if (width.isPercentValue()) {
/* 131 */       widthAndHeight[0] = Float.valueOf(areaWidth * width.getValue() / 100.0F);
/*     */     } else {
/* 133 */       widthAndHeight[0] = Float.valueOf(width.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void calculateBackgroundHeight(UnitValue height, float areaHeight, boolean scale, BackgroundImage image, Float[] widthAndHeight) {
/* 140 */     if (scale) {
/* 141 */       if (height.isPercentValue()) {
/* 142 */         scaleHeight(areaHeight * height.getValue() / 100.0F, image, widthAndHeight);
/*     */       } else {
/* 144 */         scaleHeight(height.getValue(), image, widthAndHeight);
/*     */       }
/*     */     
/* 147 */     } else if (height.isPercentValue()) {
/* 148 */       widthAndHeight[1] = Float.valueOf(areaHeight * height.getValue() / 100.0F);
/*     */     } else {
/* 150 */       widthAndHeight[1] = Float.valueOf(height.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void scaleWidth(float newWidth, BackgroundImage image, Float[] imageWidthAndHeight) {
/* 156 */     float difference = (image.getImageWidth() == 0.0F) ? 1.0F : (newWidth / image.getImageWidth());
/* 157 */     imageWidthAndHeight[0] = Float.valueOf(newWidth);
/* 158 */     imageWidthAndHeight[1] = Float.valueOf(image.getImageHeight() * difference);
/*     */   }
/*     */   
/*     */   private static void scaleHeight(float newHeight, BackgroundImage image, Float[] imageWidthAndHeight) {
/* 162 */     float difference = (image.getImageHeight() == 0.0F) ? 1.0F : (newHeight / image.getImageHeight());
/* 163 */     imageWidthAndHeight[0] = Float.valueOf(image.getImageWidth() * difference);
/* 164 */     imageWidthAndHeight[1] = Float.valueOf(newHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setDefaultSizeIfNull(Float[] widthAndHeight, float areaWidth, float areaHeight, BackgroundImage image, boolean isGradient) {
/* 169 */     if (isGradient) {
/* 170 */       widthAndHeight[0] = Float.valueOf((widthAndHeight[0] == null) ? areaWidth : widthAndHeight[0].floatValue());
/* 171 */       widthAndHeight[1] = Float.valueOf((widthAndHeight[1] == null) ? areaHeight : widthAndHeight[1].floatValue());
/*     */     } else {
/* 173 */       widthAndHeight[0] = Float.valueOf((widthAndHeight[0] == null) ? image.getImageWidth() : widthAndHeight[0].floatValue());
/* 174 */       widthAndHeight[1] = Float.valueOf((widthAndHeight[1] == null) ? image.getImageHeight() : widthAndHeight[1].floatValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/BackgroundSizeCalculationUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */