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
/*     */ public class Background
/*     */ {
/*     */   protected TransparentColor transparentColor;
/*     */   protected float extraLeft;
/*     */   protected float extraRight;
/*     */   protected float extraTop;
/*     */   protected float extraBottom;
/*  62 */   private BackgroundBox backgroundClip = BackgroundBox.BORDER_BOX;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Background(Color color) {
/*  69 */     this(color, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Background(Color color, float opacity) {
/*  78 */     this(color, opacity, 0.0F, 0.0F, 0.0F, 0.0F);
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
/*     */   public Background(Color color, float extraLeft, float extraTop, float extraRight, float extraBottom) {
/*  92 */     this(color, 1.0F, extraLeft, extraTop, extraRight, extraBottom);
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
/*     */   public Background(Color color, float opacity, float extraLeft, float extraTop, float extraRight, float extraBottom) {
/* 107 */     this.transparentColor = new TransparentColor(color, opacity);
/* 108 */     this.extraLeft = extraLeft;
/* 109 */     this.extraRight = extraRight;
/* 110 */     this.extraTop = extraTop;
/* 111 */     this.extraBottom = extraBottom;
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
/*     */   public Background(Color color, float opacity, BackgroundBox clip) {
/* 123 */     this(color, opacity);
/* 124 */     this.backgroundClip = clip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 132 */     return this.transparentColor.getColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getOpacity() {
/* 140 */     return this.transparentColor.getOpacity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExtraLeft() {
/* 148 */     return this.extraLeft;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExtraRight() {
/* 156 */     return this.extraRight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExtraTop() {
/* 164 */     return this.extraTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExtraBottom() {
/* 172 */     return this.extraBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundBox getBackgroundClip() {
/* 181 */     return this.backgroundClip;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/Background.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */