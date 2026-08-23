/*     */ package com.itextpdf.layout.renderer.objectfit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectFitApplyingResult
/*     */ {
/*     */   private double renderedImageWidth;
/*     */   private double renderedImageHeight;
/*     */   private boolean imageCuttingRequired;
/*     */   
/*     */   public ObjectFitApplyingResult() {}
/*     */   
/*     */   public ObjectFitApplyingResult(double renderedImageWidth, double renderedImageHeight, boolean imageCuttingRequired) {
/*  72 */     this.renderedImageWidth = renderedImageWidth;
/*  73 */     this.renderedImageHeight = renderedImageHeight;
/*  74 */     this.imageCuttingRequired = imageCuttingRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRenderedImageWidth() {
/*  84 */     return this.renderedImageWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderedImageWidth(double renderedImageWidth) {
/*  93 */     this.renderedImageWidth = renderedImageWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRenderedImageHeight() {
/* 102 */     return this.renderedImageHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderedImageHeight(double renderedImageHeight) {
/* 111 */     this.renderedImageHeight = renderedImageHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImageCuttingRequired() {
/* 121 */     return this.imageCuttingRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImageCuttingRequired(boolean imageCuttingRequired) {
/* 131 */     this.imageCuttingRequired = imageCuttingRequired;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/objectfit/ObjectFitApplyingResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */