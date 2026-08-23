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
/*     */ public class DottedBorder
/*     */   extends Border
/*     */ {
/*     */   private static final float GAP_MODIFIER = 1.5F;
/*     */   
/*     */   public DottedBorder(float width) {
/*  67 */     super(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DottedBorder(Color color, float width) {
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
/*     */   public DottedBorder(Color color, float width, float opacity) {
/*  88 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  96 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 104 */     float initialGap = this.width * 1.5F;
/* 105 */     float dx = x2 - x1;
/* 106 */     float dy = y2 - y1;
/* 107 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/*     */     
/* 109 */     float adjustedGap = super.getDotsGap(borderLength, initialGap + this.width);
/* 110 */     if (adjustedGap > this.width) {
/* 111 */       adjustedGap -= this.width;
/*     */     }
/*     */     
/* 114 */     float[] startingPoints = getStartingPointsForBorderSide(x1, y1, x2, y2, defaultSide);
/* 115 */     x1 = startingPoints[0];
/* 116 */     y1 = startingPoints[1];
/* 117 */     x2 = startingPoints[2];
/* 118 */     y2 = startingPoints[3];
/*     */     
/* 120 */     canvas
/* 121 */       .saveState()
/* 122 */       .setLineWidth(this.width)
/* 123 */       .setStrokeColor(this.transparentColor.getColor());
/* 124 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 125 */     canvas
/* 126 */       .setLineDash(this.width, adjustedGap, this.width + adjustedGap / 2.0F)
/* 127 */       .moveTo(x1, y1).lineTo(x2, y2)
/* 128 */       .stroke()
/* 129 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 138 */     float initialGap = this.width * 1.5F;
/* 139 */     float dx = x2 - x1;
/* 140 */     float dy = y2 - y1;
/* 141 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/* 142 */     float adjustedGap = super.getDotsGap(borderLength, initialGap);
/* 143 */     if (adjustedGap > this.width) {
/* 144 */       adjustedGap -= this.width;
/*     */     }
/*     */     
/* 147 */     canvas
/* 148 */       .saveState()
/* 149 */       .setLineWidth(this.width)
/* 150 */       .setStrokeColor(this.transparentColor.getColor());
/* 151 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 152 */     canvas.setLineDash(this.width, adjustedGap, this.width + adjustedGap / 2.0F);
/*     */     
/* 154 */     Rectangle boundingRectangle = new Rectangle(x1, y1, x2 - x1, y2 - y1);
/* 155 */     float[] horizontalRadii = { horizontalRadius1, horizontalRadius2 };
/* 156 */     float[] verticalRadii = { verticalRadius1, verticalRadius2 };
/*     */     
/* 158 */     drawDiscontinuousBorders(canvas, boundingRectangle, horizontalRadii, verticalRadii, defaultSide, borderWidthBefore, borderWidthAfter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
/* 166 */     float initialGap = this.width * 1.5F;
/* 167 */     float dx = x2 - x1;
/* 168 */     float dy = y2 - y1;
/* 169 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/*     */     
/* 171 */     float adjustedGap = super.getDotsGap(borderLength, initialGap + this.width);
/* 172 */     if (adjustedGap > this.width) {
/* 173 */       adjustedGap -= this.width;
/*     */     }
/*     */     
/* 176 */     canvas
/* 177 */       .saveState()
/* 178 */       .setLineWidth(this.width)
/* 179 */       .setStrokeColor(this.transparentColor.getColor());
/* 180 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 181 */     canvas
/* 182 */       .setLineDash(this.width, adjustedGap, this.width + adjustedGap / 2.0F)
/* 183 */       .moveTo(x1, y1)
/* 184 */       .lineTo(x2, y2)
/* 185 */       .stroke()
/* 186 */       .restoreState();
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
/* 199 */     return super.getDotsGap(distance, initialGap);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/DottedBorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */