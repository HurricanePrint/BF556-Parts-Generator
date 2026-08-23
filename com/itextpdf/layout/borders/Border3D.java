/*     */ package com.itextpdf.layout.borders;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
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
/*     */ 
/*     */ public abstract class Border3D
/*     */   extends Border
/*     */ {
/*  60 */   private static final DeviceRgb GRAY = new DeviceRgb(212, 208, 200);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border3D(float width) {
/*  68 */     this(GRAY, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border3D(DeviceRgb color, float width) {
/*  78 */     super((Color)color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border3D(DeviceCmyk color, float width) {
/*  88 */     super((Color)color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border3D(DeviceGray color, float width) {
/*  98 */     super((Color)color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border3D(DeviceRgb color, float width, float opacity) {
/* 109 */     super((Color)color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border3D(DeviceCmyk color, float width, float opacity) {
/* 120 */     super((Color)color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border3D(DeviceGray color, float width, float opacity) {
/* 131 */     super((Color)color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 139 */     float x3 = 0.0F, y3 = 0.0F;
/* 140 */     float x4 = 0.0F, y4 = 0.0F;
/* 141 */     float widthHalf = this.width / 2.0F;
/* 142 */     float halfOfWidthBefore = borderWidthBefore / 2.0F;
/* 143 */     float halfOfWidthAfter = borderWidthAfter / 2.0F;
/*     */     
/* 145 */     Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
/* 146 */     switch (borderSide) {
/*     */       case TOP:
/* 148 */         x3 = x2 + halfOfWidthAfter;
/* 149 */         y3 = y2 + widthHalf;
/* 150 */         x4 = x1 - halfOfWidthBefore;
/* 151 */         y4 = y1 + widthHalf;
/*     */         break;
/*     */       case RIGHT:
/* 154 */         x3 = x2 + widthHalf;
/* 155 */         y3 = y2 - halfOfWidthAfter;
/* 156 */         x4 = x1 + widthHalf;
/* 157 */         y4 = y1 + halfOfWidthBefore;
/*     */         break;
/*     */       case BOTTOM:
/* 160 */         x3 = x2 - halfOfWidthAfter;
/* 161 */         y3 = y2 - widthHalf;
/* 162 */         x4 = x1 + halfOfWidthBefore;
/* 163 */         y4 = y1 - widthHalf;
/*     */         break;
/*     */       case LEFT:
/* 166 */         x3 = x2 - widthHalf;
/* 167 */         y3 = y2 + halfOfWidthAfter;
/* 168 */         x4 = x1 - widthHalf;
/* 169 */         y4 = y1 - halfOfWidthBefore;
/*     */         break;
/*     */     } 
/*     */     
/* 173 */     canvas.saveState();
/* 174 */     this.transparentColor.applyFillTransparency(canvas);
/* 175 */     setInnerHalfColor(canvas, borderSide);
/* 176 */     canvas.moveTo(x1, y1).lineTo(x2, y2).lineTo(x3, y3).lineTo(x4, y4).lineTo(x1, y1).fill();
/*     */     
/* 178 */     switch (borderSide) {
/*     */       case TOP:
/* 180 */         x2 += borderWidthAfter;
/* 181 */         y2 += this.width;
/* 182 */         x1 -= borderWidthBefore;
/* 183 */         y1 += this.width;
/*     */         break;
/*     */       case RIGHT:
/* 186 */         x2 += this.width;
/* 187 */         y2 -= borderWidthAfter;
/* 188 */         x1 += this.width;
/* 189 */         y1 += borderWidthBefore;
/*     */         break;
/*     */       case BOTTOM:
/* 192 */         x2 -= borderWidthAfter;
/* 193 */         y2 -= this.width;
/* 194 */         x1 += borderWidthBefore;
/* 195 */         y1 -= this.width;
/*     */         break;
/*     */       case LEFT:
/* 198 */         x2 -= this.width;
/* 199 */         y2 += borderWidthAfter;
/* 200 */         x1 -= this.width;
/* 201 */         y1 -= borderWidthBefore;
/*     */         break;
/*     */     } 
/*     */     
/* 205 */     setOuterHalfColor(canvas, borderSide);
/* 206 */     canvas.moveTo(x1, y1).lineTo(x2, y2).lineTo(x3, y3).lineTo(x4, y4).lineTo(x1, y1).fill();
/* 207 */     canvas.restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
/* 215 */     canvas
/* 216 */       .saveState()
/* 217 */       .setStrokeColor(this.transparentColor.getColor());
/* 218 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 219 */     canvas
/* 220 */       .setLineWidth(this.width)
/* 221 */       .moveTo(x1, y1)
/* 222 */       .lineTo(x2, y2)
/* 223 */       .stroke()
/* 224 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getDarkerColor() {
/* 232 */     Color color = this.transparentColor.getColor();
/* 233 */     if (color instanceof DeviceRgb)
/* 234 */       return (Color)DeviceRgb.makeDarker((DeviceRgb)color); 
/* 235 */     if (color instanceof DeviceCmyk)
/* 236 */       return (Color)DeviceCmyk.makeDarker((DeviceCmyk)color); 
/* 237 */     if (color instanceof DeviceGray) {
/* 238 */       return (Color)DeviceGray.makeDarker((DeviceGray)color);
/*     */     }
/* 240 */     return color;
/*     */   }
/*     */   
/*     */   protected abstract void setInnerHalfColor(PdfCanvas paramPdfCanvas, Border.Side paramSide);
/*     */   
/*     */   protected abstract void setOuterHalfColor(PdfCanvas paramPdfCanvas, Border.Side paramSide);
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/Border3D.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */