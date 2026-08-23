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
/*     */ public class SolidLine
/*     */   implements ILineDrawer
/*     */ {
/*  57 */   private float lineWidth = 1.0F;
/*     */   
/*  59 */   private Color color = ColorConstants.BLACK;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolidLine() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolidLine(float lineWidth) {
/*  73 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, Rectangle drawArea) {
/*  78 */     canvas.saveState()
/*  79 */       .setStrokeColor(this.color)
/*  80 */       .setLineWidth(this.lineWidth)
/*  81 */       .moveTo(drawArea.getX(), (drawArea.getY() + this.lineWidth / 2.0F))
/*  82 */       .lineTo((drawArea.getX() + drawArea.getWidth()), (drawArea.getY() + this.lineWidth / 2.0F))
/*  83 */       .stroke()
/*  84 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLineWidth() {
/*  94 */     return this.lineWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineWidth(float lineWidth) {
/* 104 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 109 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 114 */     this.color = color;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/draw/SolidLine.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */