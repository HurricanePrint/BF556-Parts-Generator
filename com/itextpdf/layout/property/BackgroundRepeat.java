/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BackgroundRepeat
/*     */ {
/*     */   private final BackgroundRepeatValue xAxisRepeat;
/*     */   private final BackgroundRepeatValue yAxisRepeat;
/*     */   
/*     */   public BackgroundRepeat() {
/*  40 */     this(BackgroundRepeatValue.REPEAT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundRepeat(BackgroundRepeatValue repeat) {
/*  49 */     this(repeat, repeat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundRepeat(BackgroundRepeatValue xAxisRepeat, BackgroundRepeatValue yAxisRepeat) {
/*  59 */     this.xAxisRepeat = xAxisRepeat;
/*  60 */     this.yAxisRepeat = yAxisRepeat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundRepeatValue getXAxisRepeat() {
/*  69 */     return this.xAxisRepeat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundRepeatValue getYAxisRepeat() {
/*  78 */     return this.yAxisRepeat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNoRepeatOnXAxis() {
/*  87 */     return (this.xAxisRepeat == BackgroundRepeatValue.NO_REPEAT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNoRepeatOnYAxis() {
/*  96 */     return (this.yAxisRepeat == BackgroundRepeatValue.NO_REPEAT);
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
/*     */   public Point prepareRectangleToDrawingAndGetWhitespace(Rectangle imageRectangle, Rectangle backgroundArea, BackgroundSize backgroundSize) {
/* 109 */     if (BackgroundRepeatValue.ROUND == this.xAxisRepeat) {
/* 110 */       int ratio = calculateRatio(backgroundArea.getWidth(), imageRectangle.getWidth());
/* 111 */       float initialImageRatio = imageRectangle.getHeight() / imageRectangle.getWidth();
/* 112 */       imageRectangle.setWidth(backgroundArea.getWidth() / ratio);
/* 113 */       if (BackgroundRepeatValue.ROUND != this.yAxisRepeat && backgroundSize.getBackgroundHeightSize() == null) {
/* 114 */         imageRectangle.moveUp(imageRectangle.getHeight() - imageRectangle.getWidth() * initialImageRatio);
/* 115 */         imageRectangle.setHeight(imageRectangle.getWidth() * initialImageRatio);
/*     */       } 
/*     */     } 
/* 118 */     if (BackgroundRepeatValue.ROUND == this.yAxisRepeat) {
/* 119 */       int ratio = calculateRatio(backgroundArea.getHeight(), imageRectangle.getHeight());
/* 120 */       float initialImageRatio = imageRectangle.getWidth() / imageRectangle.getHeight();
/* 121 */       imageRectangle.moveUp(imageRectangle.getHeight() - backgroundArea.getHeight() / ratio);
/* 122 */       imageRectangle.setHeight(backgroundArea.getHeight() / ratio);
/* 123 */       if (BackgroundRepeatValue.ROUND != this.xAxisRepeat && backgroundSize.getBackgroundWidthSize() == null) {
/* 124 */         imageRectangle.setWidth(imageRectangle.getHeight() * initialImageRatio);
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return processSpaceValueAndCalculateWhitespace(imageRectangle, backgroundArea);
/*     */   }
/*     */   
/*     */   private Point processSpaceValueAndCalculateWhitespace(Rectangle imageRectangle, Rectangle backgroundArea) {
/* 132 */     Point whitespace = new Point();
/* 133 */     if (BackgroundRepeatValue.SPACE == this.xAxisRepeat) {
/* 134 */       if (imageRectangle.getWidth() * 2.0F <= backgroundArea.getWidth()) {
/* 135 */         imageRectangle.setX(backgroundArea.getX());
/* 136 */         whitespace.setLocation(calculateWhitespace(backgroundArea.getWidth(), imageRectangle.getWidth()), 0.0D);
/*     */       } else {
/* 138 */         float rightSpace = backgroundArea.getRight() - imageRectangle.getRight();
/* 139 */         float leftSpace = imageRectangle.getLeft() - backgroundArea.getLeft();
/* 140 */         float xWhitespace = Math.max(rightSpace, leftSpace);
/* 141 */         xWhitespace = (xWhitespace > 0.0F) ? xWhitespace : 0.0F;
/* 142 */         whitespace.setLocation(xWhitespace, 0.0D);
/*     */       } 
/*     */     }
/* 145 */     if (BackgroundRepeatValue.SPACE == this.yAxisRepeat) {
/* 146 */       if (imageRectangle.getHeight() * 2.0F <= backgroundArea.getHeight()) {
/* 147 */         imageRectangle.setY(backgroundArea.getY() + backgroundArea.getHeight() - imageRectangle.getHeight());
/* 148 */         whitespace.setLocation(whitespace.getX(), calculateWhitespace(backgroundArea.getHeight(), imageRectangle.getHeight()));
/*     */       } else {
/* 150 */         float topSpace = backgroundArea.getTop() - imageRectangle.getTop();
/* 151 */         float bottomSpace = imageRectangle.getBottom() - backgroundArea.getBottom();
/* 152 */         float yWhitespace = Math.max(topSpace, bottomSpace);
/* 153 */         yWhitespace = (yWhitespace > 0.0F) ? yWhitespace : 0.0F;
/* 154 */         whitespace.setLocation(whitespace.getX(), yWhitespace);
/*     */       } 
/*     */     }
/* 157 */     return whitespace;
/*     */   }
/*     */   
/*     */   private static int calculateRatio(float areaSize, float backgroundSize) {
/* 161 */     int ratio = (int)Math.floor((areaSize / backgroundSize));
/* 162 */     float remainSpace = areaSize - ratio * backgroundSize;
/* 163 */     if (remainSpace >= backgroundSize / 2.0F) {
/* 164 */       ratio++;
/*     */     }
/* 166 */     return (ratio == 0) ? 1 : ratio;
/*     */   }
/*     */   
/*     */   private static float calculateWhitespace(float areaSize, float backgroundSize) {
/* 170 */     float whitespace = 0.0F;
/* 171 */     int ratio = (int)Math.floor((areaSize / backgroundSize));
/* 172 */     if (ratio > 0) {
/* 173 */       whitespace = areaSize - ratio * backgroundSize;
/* 174 */       if (ratio > 1) {
/* 175 */         whitespace /= (ratio - 1);
/*     */       }
/*     */     } 
/* 178 */     return whitespace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum BackgroundRepeatValue
/*     */   {
/* 189 */     NO_REPEAT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     REPEAT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     ROUND,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     SPACE;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/BackgroundRepeat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */