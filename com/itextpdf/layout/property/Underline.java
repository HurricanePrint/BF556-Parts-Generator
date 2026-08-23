/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Underline
/*     */ {
/*     */   protected TransparentColor transparentColor;
/*     */   protected float thickness;
/*     */   protected float thicknessMul;
/*     */   protected float yPosition;
/*     */   protected float yPositionMul;
/*  63 */   protected int lineCapStyle = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Underline(Color color, float thickness, float thicknessMul, float yPosition, float yPositionMul, int lineCapStyle) {
/*  82 */     this(color, 1.0F, thickness, thicknessMul, yPosition, yPositionMul, lineCapStyle);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Underline(Color color, float opacity, float thickness, float thicknessMul, float yPosition, float yPositionMul, int lineCapStyle) {
/* 103 */     this.transparentColor = new TransparentColor(color, opacity);
/* 104 */     this.thickness = thickness;
/* 105 */     this.thicknessMul = thicknessMul;
/* 106 */     this.yPosition = yPosition;
/* 107 */     this.yPositionMul = yPositionMul;
/* 108 */     this.lineCapStyle = lineCapStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 116 */     return this.transparentColor.getColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getOpacity() {
/* 124 */     return this.transparentColor.getOpacity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getThickness(float fontSize) {
/* 133 */     return this.thickness + this.thicknessMul * fontSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getYPosition(float fontSize) {
/* 142 */     return this.yPosition + this.yPositionMul * fontSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getYPositionMul() {
/* 150 */     return this.yPositionMul;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineCapStyle() {
/* 159 */     return this.lineCapStyle;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/Underline.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */