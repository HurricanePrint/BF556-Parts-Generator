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
/*     */ public class DashedLine
/*     */   implements ILineDrawer
/*     */ {
/*  57 */   private float lineWidth = 1.0F;
/*     */   
/*  59 */   private Color color = ColorConstants.BLACK;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DashedLine() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public DashedLine(float lineWidth) {
/*  69 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, Rectangle drawArea) {
/*  74 */     canvas.saveState()
/*  75 */       .setLineWidth(this.lineWidth)
/*  76 */       .setStrokeColor(this.color)
/*  77 */       .setLineDash(2.0F, 2.0F)
/*  78 */       .moveTo(drawArea.getX(), (drawArea.getY() + this.lineWidth / 2.0F))
/*  79 */       .lineTo((drawArea.getX() + drawArea.getWidth()), (drawArea.getY() + this.lineWidth / 2.0F))
/*  80 */       .stroke()
/*  81 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLineWidth() {
/*  90 */     return this.lineWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineWidth(float lineWidth) {
/*  99 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 104 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 109 */     this.color = color;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/draw/DashedLine.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */