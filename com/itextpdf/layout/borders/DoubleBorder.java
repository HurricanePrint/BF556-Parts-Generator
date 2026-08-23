/*     */ package com.itextpdf.layout.borders;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
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
/*     */ public class DoubleBorder
/*     */   extends Border
/*     */ {
/*     */   public DoubleBorder(float width) {
/*  63 */     super(width);
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
/*     */   public DoubleBorder(Color color, float width) {
/*  75 */     super(color, width);
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
/*     */   public DoubleBorder(Color color, float width, float opacity) {
/*  88 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  96 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 104 */     float x3 = 0.0F, y3 = 0.0F;
/* 105 */     float x4 = 0.0F, y4 = 0.0F;
/* 106 */     float thirdOfWidth = this.width / 3.0F;
/* 107 */     float thirdOfWidthBefore = borderWidthBefore / 3.0F;
/* 108 */     float thirdOfWidthAfter = borderWidthAfter / 3.0F;
/*     */     
/* 110 */     Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
/*     */     
/* 112 */     switch (borderSide) {
/*     */       case TOP:
/* 114 */         x3 = x2 + thirdOfWidthAfter;
/* 115 */         y3 = y2 + thirdOfWidth;
/* 116 */         x4 = x1 - thirdOfWidthBefore;
/* 117 */         y4 = y1 + thirdOfWidth;
/*     */         break;
/*     */       case RIGHT:
/* 120 */         x3 = x2 + thirdOfWidth;
/* 121 */         y3 = y2 - thirdOfWidthAfter;
/* 122 */         x4 = x1 + thirdOfWidth;
/* 123 */         y4 = y1 + thirdOfWidthBefore;
/*     */         break;
/*     */       case BOTTOM:
/* 126 */         x3 = x2 - thirdOfWidthAfter;
/* 127 */         y3 = y2 - thirdOfWidth;
/* 128 */         x4 = x1 + thirdOfWidthBefore;
/* 129 */         y4 = y1 - thirdOfWidth;
/*     */         break;
/*     */       case LEFT:
/* 132 */         x3 = x2 - thirdOfWidth;
/* 133 */         y3 = y2 + thirdOfWidthAfter;
/* 134 */         x4 = x1 - thirdOfWidth;
/* 135 */         y4 = y1 - thirdOfWidthBefore;
/*     */         break;
/*     */     } 
/*     */     
/* 139 */     canvas.saveState()
/* 140 */       .setFillColor(this.transparentColor.getColor());
/* 141 */     this.transparentColor.applyFillTransparency(canvas);
/* 142 */     canvas
/* 143 */       .moveTo(x1, y1).lineTo(x2, y2).lineTo(x3, y3).lineTo(x4, y4).lineTo(x1, y1).fill();
/*     */     
/* 145 */     switch (borderSide) {
/*     */       case TOP:
/* 147 */         x2 += 2.0F * thirdOfWidthAfter;
/* 148 */         y2 += 2.0F * thirdOfWidth;
/* 149 */         x3 += 2.0F * thirdOfWidthAfter;
/* 150 */         y3 += 2.0F * thirdOfWidth;
/* 151 */         x4 -= 2.0F * thirdOfWidthBefore;
/* 152 */         y4 += 2.0F * thirdOfWidth;
/* 153 */         x1 -= 2.0F * thirdOfWidthBefore;
/* 154 */         y1 += 2.0F * thirdOfWidth;
/*     */         break;
/*     */       case RIGHT:
/* 157 */         x2 += 2.0F * thirdOfWidth;
/* 158 */         y2 -= 2.0F * thirdOfWidthAfter;
/* 159 */         x3 += 2.0F * thirdOfWidth;
/* 160 */         y3 -= 2.0F * thirdOfWidthAfter;
/* 161 */         x4 += 2.0F * thirdOfWidth;
/* 162 */         y4 += 2.0F * thirdOfWidthBefore;
/* 163 */         x1 += 2.0F * thirdOfWidth;
/* 164 */         y1 += 2.0F * thirdOfWidthBefore;
/*     */         break;
/*     */       case BOTTOM:
/* 167 */         x2 -= 2.0F * thirdOfWidthAfter;
/* 168 */         y2 -= 2.0F * thirdOfWidth;
/* 169 */         x3 -= 2.0F * thirdOfWidthAfter;
/* 170 */         y3 -= 2.0F * thirdOfWidth;
/* 171 */         x4 += 2.0F * thirdOfWidthBefore;
/* 172 */         y4 -= 2.0F * thirdOfWidth;
/* 173 */         x1 += 2.0F * thirdOfWidthBefore;
/* 174 */         y1 -= 2.0F * thirdOfWidth;
/*     */         break;
/*     */       case LEFT:
/* 177 */         x2 -= 2.0F * thirdOfWidth;
/* 178 */         y2 += 2.0F * thirdOfWidthAfter;
/* 179 */         x3 -= 2.0F * thirdOfWidth;
/* 180 */         y3 += 2.0F * thirdOfWidthAfter;
/* 181 */         x4 -= 2.0F * thirdOfWidth;
/* 182 */         y4 -= 2.0F * thirdOfWidthBefore;
/* 183 */         x1 -= 2.0F * thirdOfWidth;
/* 184 */         y1 -= 2.0F * thirdOfWidthBefore;
/*     */         break;
/*     */     } 
/*     */     
/* 188 */     canvas.moveTo(x1, y1).lineTo(x2, y2).lineTo(x3, y3).lineTo(x4, y4).lineTo(x1, y1).fill()
/* 189 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
/* 197 */     float thirdOfWidth = this.width / 3.0F;
/*     */     
/* 199 */     Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
/*     */     
/* 201 */     switch (borderSide) {
/*     */       case TOP:
/* 203 */         y1 -= thirdOfWidth;
/* 204 */         y2 = y1;
/*     */         break;
/*     */       case RIGHT:
/* 207 */         x1 -= thirdOfWidth;
/* 208 */         x2 -= thirdOfWidth;
/* 209 */         y1 += thirdOfWidth;
/* 210 */         y2 -= thirdOfWidth;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     canvas
/* 219 */       .saveState()
/* 220 */       .setLineWidth(thirdOfWidth)
/* 221 */       .setStrokeColor(this.transparentColor.getColor());
/* 222 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 223 */     canvas
/* 224 */       .moveTo(x1, y1)
/* 225 */       .lineTo(x2, y2)
/* 226 */       .stroke()
/* 227 */       .restoreState();
/*     */     
/* 229 */     switch (borderSide) {
/*     */       
/*     */       case TOP:
/* 232 */         y2 += 2.0F * thirdOfWidth;
/* 233 */         y1 += 2.0F * thirdOfWidth;
/*     */         break;
/*     */       case RIGHT:
/* 236 */         x2 += 2.0F * thirdOfWidth;
/* 237 */         x1 += 2.0F * thirdOfWidth;
/*     */         break;
/*     */       
/*     */       case BOTTOM:
/* 241 */         x2 -= 2.0F * thirdOfWidth;
/* 242 */         y2 -= 2.0F * thirdOfWidth;
/* 243 */         x1 += 2.0F * thirdOfWidth;
/* 244 */         y1 -= 2.0F * thirdOfWidth;
/*     */         break;
/*     */       case LEFT:
/* 247 */         y2 += 2.0F * thirdOfWidth;
/* 248 */         x1 -= 2.0F * thirdOfWidth;
/* 249 */         y1 -= 2.0F * thirdOfWidth;
/*     */         break;
/*     */     } 
/*     */     
/* 253 */     canvas
/* 254 */       .saveState()
/* 255 */       .setLineWidth(thirdOfWidth)
/* 256 */       .setStrokeColor(this.transparentColor.getColor());
/* 257 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 258 */     canvas
/* 259 */       .moveTo(x1, y1)
/* 260 */       .lineTo(x2, y2)
/* 261 */       .stroke()
/* 262 */       .restoreState();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/DoubleBorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */