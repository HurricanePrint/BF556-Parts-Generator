/*     */ package com.itextpdf.kernel.pdf.canvas.draw;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.ColorConstants;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DottedLine
/*     */   implements ILineDrawer
/*     */ {
/*  61 */   protected float gap = 4.0F;
/*     */   
/*  63 */   private float lineWidth = 1.0F;
/*     */   
/*  65 */   private Color color = ColorConstants.BLACK;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DottedLine() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DottedLine(float lineWidth, float gap) {
/*  80 */     this.lineWidth = lineWidth;
/*  81 */     this.gap = gap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DottedLine(float lineWidth) {
/*  90 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, Rectangle drawArea) {
/*  95 */     canvas.saveState()
/*  96 */       .setLineWidth(this.lineWidth)
/*  97 */       .setStrokeColor(this.color)
/*  98 */       .setLineDash(0.0F, this.gap, this.gap / 2.0F)
/*  99 */       .setLineCapStyle(1)
/* 100 */       .moveTo(drawArea.getX(), (drawArea.getY() + this.lineWidth / 2.0F))
/* 101 */       .lineTo((drawArea.getX() + drawArea.getWidth()), (drawArea.getY() + this.lineWidth / 2.0F))
/* 102 */       .stroke()
/* 103 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGap() {
/* 112 */     return this.gap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGap(float gap) {
/* 121 */     this.gap = gap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLineWidth() {
/* 130 */     return this.lineWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineWidth(float lineWidth) {
/* 139 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 144 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 149 */     this.color = color;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/draw/DottedLine.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */