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
/*     */ public class OutsetBorder
/*     */   extends Border3D
/*     */ {
/*     */   public OutsetBorder(float width) {
/*  59 */     super(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutsetBorder(DeviceRgb color, float width) {
/*  69 */     super(color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutsetBorder(DeviceCmyk color, float width) {
/*  79 */     super(color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutsetBorder(DeviceGray color, float width) {
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
/*     */   public OutsetBorder(DeviceRgb color, float width, float opacity) {
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
/*     */   public OutsetBorder(DeviceCmyk color, float width, float opacity) {
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
/*     */   public OutsetBorder(DeviceGray color, float width, float opacity) {
/* 122 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 130 */     return 7;
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
/* 158 */         canvas.setFillColor(getColor());
/*     */         break;
/*     */       case BOTTOM:
/*     */       case RIGHT:
/* 162 */         canvas.setFillColor(getDarkerColor());
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/OutsetBorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */