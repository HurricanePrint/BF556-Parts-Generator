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
/*     */ 
/*     */ 
/*     */ public class DashedBorder
/*     */   extends Border
/*     */ {
/*     */   private static final float DASH_MODIFIER = 5.0F;
/*     */   private static final float GAP_MODIFIER = 3.5F;
/*     */   
/*     */   public DashedBorder(float width) {
/*  70 */     super(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DashedBorder(Color color, float width) {
/*  80 */     super(color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DashedBorder(Color color, float width, float opacity) {
/*  91 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  99 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 107 */     float initialGap = this.width * 3.5F;
/* 108 */     float dash = this.width * 5.0F;
/* 109 */     float dx = x2 - x1;
/* 110 */     float dy = y2 - y1;
/* 111 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/*     */     
/* 113 */     float adjustedGap = super.getDotsGap(borderLength, initialGap + dash);
/* 114 */     if (adjustedGap > dash) {
/* 115 */       adjustedGap -= dash;
/*     */     }
/* 117 */     float[] startingPoints = getStartingPointsForBorderSide(x1, y1, x2, y2, defaultSide);
/*     */     
/* 119 */     x1 = startingPoints[0];
/* 120 */     y1 = startingPoints[1];
/* 121 */     x2 = startingPoints[2];
/* 122 */     y2 = startingPoints[3];
/*     */     
/* 124 */     canvas
/* 125 */       .saveState()
/* 126 */       .setLineWidth(this.width)
/* 127 */       .setStrokeColor(this.transparentColor.getColor());
/* 128 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 129 */     canvas
/* 130 */       .setLineDash(dash, adjustedGap, dash + adjustedGap / 2.0F)
/* 131 */       .moveTo(x1, y1).lineTo(x2, y2)
/* 132 */       .stroke()
/* 133 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
/* 141 */     float initialGap = this.width * 3.5F;
/* 142 */     float dash = this.width * 5.0F;
/* 143 */     float dx = x2 - x1;
/* 144 */     float dy = y2 - y1;
/* 145 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/*     */     
/* 147 */     float adjustedGap = super.getDotsGap(borderLength, initialGap + dash);
/* 148 */     if (adjustedGap > dash) {
/* 149 */       adjustedGap -= dash;
/*     */     }
/*     */     
/* 152 */     canvas
/* 153 */       .saveState()
/* 154 */       .setStrokeColor(this.transparentColor.getColor());
/* 155 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 156 */     canvas
/* 157 */       .setLineDash(dash, adjustedGap, dash + adjustedGap / 2.0F)
/* 158 */       .setLineWidth(this.width)
/* 159 */       .moveTo(x1, y1)
/* 160 */       .lineTo(x2, y2)
/* 161 */       .stroke()
/* 162 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 168 */     float initialGap = this.width * 3.5F;
/* 169 */     float dash = this.width * 5.0F;
/* 170 */     float dx = x2 - x1;
/* 171 */     float dy = y2 - y1;
/* 172 */     double borderLength = Math.sqrt((dx * dx + dy * dy));
/* 173 */     float adjustedGap = super.getDotsGap(borderLength, initialGap + dash);
/* 174 */     if (adjustedGap > dash) {
/* 175 */       adjustedGap -= dash;
/*     */     }
/* 177 */     canvas
/* 178 */       .saveState()
/* 179 */       .setLineWidth(this.width)
/* 180 */       .setStrokeColor(this.transparentColor.getColor());
/* 181 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 182 */     canvas.setLineDash(dash, adjustedGap, dash + adjustedGap / 2.0F);
/*     */     
/* 184 */     Rectangle boundingRectangle = new Rectangle(x1, y1, x2 - x1, y2 - y1);
/* 185 */     float[] horizontalRadii = { horizontalRadius1, horizontalRadius2 };
/* 186 */     float[] verticalRadii = { verticalRadius1, verticalRadius2 };
/*     */     
/* 188 */     drawDiscontinuousBorders(canvas, boundingRectangle, horizontalRadii, verticalRadii, defaultSide, borderWidthBefore, borderWidthAfter);
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
/* 201 */     return super.getDotsGap(distance, initialGap);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/DashedBorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */