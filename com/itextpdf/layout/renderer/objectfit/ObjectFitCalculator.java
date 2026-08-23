/*     */ package com.itextpdf.layout.renderer.objectfit;
/*     */ 
/*     */ import com.itextpdf.layout.property.ObjectFit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectFitCalculator
/*     */ {
/*     */   public static ObjectFitApplyingResult calculateRenderedImageSize(ObjectFit objectFit, double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
/*  71 */     switch (objectFit) {
/*     */       case FILL:
/*  73 */         return processFill(imageContainerWidth, imageContainerHeight);
/*     */       case CONTAIN:
/*  75 */         return processContain(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight);
/*     */       
/*     */       case COVER:
/*  78 */         return processCover(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight);
/*     */       
/*     */       case SCALE_DOWN:
/*  81 */         return processScaleDown(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight);
/*     */       
/*     */       case NONE:
/*  84 */         return processNone(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight);
/*     */     } 
/*     */     
/*  87 */     throw new IllegalArgumentException("Object fit parameter cannot be null!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectFitApplyingResult processFill(double imageContainerWidth, double imageContainerHeight) {
/*  93 */     return new ObjectFitApplyingResult(imageContainerWidth, imageContainerHeight, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private static ObjectFitApplyingResult processContain(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
/*  98 */     return processToFitSide(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectFitApplyingResult processCover(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
/* 104 */     return processToFitSide(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectFitApplyingResult processScaleDown(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
/* 110 */     if (imageContainerWidth >= absoluteImageWidth && imageContainerHeight >= absoluteImageHeight)
/*     */     {
/* 112 */       return new ObjectFitApplyingResult(absoluteImageWidth, absoluteImageHeight, false);
/*     */     }
/* 114 */     return processToFitSide(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectFitApplyingResult processNone(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
/* 121 */     boolean doesObjectFitRequireCutting = (imageContainerWidth <= absoluteImageWidth || imageContainerHeight <= absoluteImageHeight);
/*     */     
/* 123 */     return new ObjectFitApplyingResult(absoluteImageWidth, absoluteImageHeight, doesObjectFitRequireCutting);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectFitApplyingResult processToFitSide(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight, boolean clipToFit) {
/* 130 */     double renderedImageWidth, renderedImageHeight, widthCoeff = imageContainerWidth / absoluteImageWidth;
/* 131 */     double heightCoeff = imageContainerHeight / absoluteImageHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     int i = ((heightCoeff > widthCoeff) ? 1 : 0) ^ clipToFit;
/*     */     
/* 138 */     if (i != 0) {
/* 139 */       renderedImageWidth = imageContainerWidth;
/* 140 */       renderedImageHeight = absoluteImageHeight * imageContainerWidth / absoluteImageWidth;
/*     */     } else {
/* 142 */       renderedImageHeight = imageContainerHeight;
/* 143 */       renderedImageWidth = absoluteImageWidth * imageContainerHeight / absoluteImageHeight;
/*     */     } 
/*     */     
/* 146 */     return new ObjectFitApplyingResult(renderedImageWidth, renderedImageHeight, clipToFit);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/objectfit/ObjectFitCalculator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */