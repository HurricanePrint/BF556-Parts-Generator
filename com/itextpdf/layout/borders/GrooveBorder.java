/*     */ package com.itextpdf.layout.borders;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.DeviceCmyk;
/*     */ import com.itextpdf.kernel.colors.DeviceGray;
/*     */ import com.itextpdf.kernel.colors.DeviceRgb;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GrooveBorder
/*     */   extends Border3D
/*     */ {
/*     */   public GrooveBorder(float width) {
/*  59 */     super(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GrooveBorder(DeviceRgb color, float width) {
/*  69 */     super(color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GrooveBorder(DeviceCmyk color, float width) {
/*  79 */     super(color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GrooveBorder(DeviceGray color, float width) {
/*  89 */     super(color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GrooveBorder(DeviceRgb color, float width, float opacity) {
/* 100 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GrooveBorder(DeviceCmyk color, float width, float opacity) {
/* 111 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GrooveBorder(DeviceGray color, float width, float opacity) {
/* 122 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 130 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setInnerHalfColor(PdfCanvas canvas, Border.Side side) {
/* 138 */     switch (side) {
/*     */       case TOP:
/*     */       case LEFT:
/* 141 */         canvas.setFillColor(getColor());
/*     */         break;
/*     */       case BOTTOM:
/*     */       case RIGHT:
/* 145 */         canvas.setFillColor(getDarkerColor());
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setOuterHalfColor(PdfCanvas canvas, Border.Side side) {
/* 155 */     switch (side) {
/*     */       case TOP:
/*     */       case LEFT:
/* 158 */         canvas.setFillColor(getDarkerColor());
/*     */         break;
/*     */       case BOTTOM:
/*     */       case RIGHT:
/* 162 */         canvas.setFillColor(getColor());
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/GrooveBorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */