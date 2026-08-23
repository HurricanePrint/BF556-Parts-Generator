/*     */ package com.itextpdf.layout.borders;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoundDotsBorder
/*     */   extends Border
/*     */ {
/*     */   private static final float GAP_MODIFIER = 2.5F;
/*     */   
/*     */   public RoundDotsBorder(float width) {
/*  67 */     super(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoundDotsBorder(Color color, float width) {
/*  77 */     super(color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoundDotsBorder(Color color, float width, float opacity) {
/*  88 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  96 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 104 */     float initialGap = this.width * 2.5F;
/* 105 */     float dx = x2 - x1;
/* 106 */     float dy = y2 - y1;
/* 107 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/* 108 */     float adjustedGap = super.getDotsGap(borderLength, initialGap);
/*     */     
/* 110 */     float[] startingPoints = getStartingPointsForBorderSide(x1, y1, x2, y2, defaultSide);
/* 111 */     x1 = startingPoints[0];
/* 112 */     y1 = startingPoints[1];
/* 113 */     x2 = startingPoints[2];
/* 114 */     y2 = startingPoints[3];
/*     */     
/* 116 */     canvas.saveState()
/* 117 */       .setStrokeColor(this.transparentColor.getColor())
/* 118 */       .setLineWidth(this.width)
/* 119 */       .setLineCapStyle(1);
/* 120 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 121 */     canvas.setLineDash(0.0F, adjustedGap, adjustedGap / 2.0F)
/* 122 */       .moveTo(x1, y1).lineTo(x2, y2)
/* 123 */       .stroke()
/* 124 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
/* 132 */     float initialGap = this.width * 2.5F;
/* 133 */     float dx = x2 - x1;
/* 134 */     float dy = y2 - y1;
/* 135 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/* 136 */     float adjustedGap = super.getDotsGap(borderLength, initialGap);
/* 137 */     boolean isHorizontal = false;
/* 138 */     if (Math.abs(y2 - y1) < 5.0E-4F) {
/* 139 */       isHorizontal = true;
/*     */     }
/*     */     
/* 142 */     if (isHorizontal) {
/* 143 */       x2 -= this.width;
/*     */     }
/* 145 */     canvas.saveState();
/* 146 */     canvas.setStrokeColor(this.transparentColor.getColor());
/* 147 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 148 */     canvas.setLineWidth(this.width);
/* 149 */     canvas.setLineCapStyle(1);
/*     */     
/* 151 */     canvas.setLineDash(0.0F, adjustedGap, adjustedGap / 2.0F)
/* 152 */       .moveTo(x1, y1).lineTo(x2, y2)
/* 153 */       .stroke();
/* 154 */     canvas.restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 162 */     float initialGap = this.width * 2.5F;
/* 163 */     float dx = x2 - x1;
/* 164 */     float dy = y2 - y1;
/* 165 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/* 166 */     float adjustedGap = super.getDotsGap(borderLength, initialGap);
/*     */     
/* 168 */     canvas
/* 169 */       .saveState()
/* 170 */       .setStrokeColor(this.transparentColor.getColor());
/* 171 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 172 */     canvas
/* 173 */       .setLineWidth(this.width)
/* 174 */       .setLineCapStyle(1)
/* 175 */       .setLineDash(0.0F, adjustedGap, adjustedGap / 2.0F);
/*     */     
/* 177 */     Rectangle boundingRectangle = new Rectangle(x1, y1, x2 - x1, y2 - y1);
/* 178 */     float[] horizontalRadii = { horizontalRadius1, horizontalRadius2 };
/* 179 */     float[] verticalRadii = { verticalRadius1, verticalRadius2 };
/*     */     
/* 181 */     drawDiscontinuousBorders(canvas, boundingRectangle, horizontalRadii, verticalRadii, defaultSide, borderWidthBefore, borderWidthAfter);
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
/*     */   @Deprecated
/*     */   protected float getDotsGap(double distance, float initialGap) {
/* 194 */     return super.getDotsGap(distance, initialGap);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/RoundDotsBorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */