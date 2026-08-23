/*     */ package com.itextpdf.layout.borders;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Point;
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
/*     */ public class SolidBorder
/*     */   extends Border
/*     */ {
/*     */   public SolidBorder(float width) {
/*  61 */     super(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolidBorder(Color color, float width) {
/*  71 */     super(color, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolidBorder(Color color, float width, float opacity) {
/*  82 */     super(color, width, opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  90 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/*  98 */     float x3 = 0.0F, y3 = 0.0F;
/*  99 */     float x4 = 0.0F, y4 = 0.0F;
/*     */     
/* 101 */     Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
/* 102 */     switch (borderSide) {
/*     */       case TOP:
/* 104 */         x3 = x2 + borderWidthAfter;
/* 105 */         y3 = y2 + this.width;
/* 106 */         x4 = x1 - borderWidthBefore;
/* 107 */         y4 = y1 + this.width;
/*     */         break;
/*     */       case RIGHT:
/* 110 */         x3 = x2 + this.width;
/* 111 */         y3 = y2 - borderWidthAfter;
/* 112 */         x4 = x1 + this.width;
/* 113 */         y4 = y1 + borderWidthBefore;
/*     */         break;
/*     */       case BOTTOM:
/* 116 */         x3 = x2 - borderWidthAfter;
/* 117 */         y3 = y2 - this.width;
/* 118 */         x4 = x1 + borderWidthBefore;
/* 119 */         y4 = y1 - this.width;
/*     */         break;
/*     */       case LEFT:
/* 122 */         x3 = x2 - this.width;
/* 123 */         y3 = y2 + borderWidthAfter;
/* 124 */         x4 = x1 - this.width;
/* 125 */         y4 = y1 - borderWidthBefore;
/*     */         break;
/*     */     } 
/*     */     
/* 129 */     canvas.saveState()
/* 130 */       .setFillColor(this.transparentColor.getColor());
/* 131 */     this.transparentColor.applyFillTransparency(canvas);
/* 132 */     canvas
/* 133 */       .moveTo(x1, y1).lineTo(x2, y2).lineTo(x3, y3).lineTo(x4, y4).lineTo(x1, y1).fill()
/* 134 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 142 */     float innerRadiusBefore, innerRadiusFirst, innerRadiusSecond, innerRadiusAfter, x3 = 0.0F, y3 = 0.0F;
/* 143 */     float x4 = 0.0F, y4 = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
/* 151 */     switch (borderSide) {
/*     */       case TOP:
/* 153 */         x3 = x2 + borderWidthAfter;
/* 154 */         y3 = y2 + this.width;
/* 155 */         x4 = x1 - borderWidthBefore;
/* 156 */         y4 = y1 + this.width;
/*     */         
/* 158 */         innerRadiusBefore = Math.max(0.0F, horizontalRadius1 - borderWidthBefore);
/* 159 */         innerRadiusFirst = Math.max(0.0F, verticalRadius1 - this.width);
/* 160 */         innerRadiusSecond = Math.max(0.0F, verticalRadius2 - this.width);
/* 161 */         innerRadiusAfter = Math.max(0.0F, horizontalRadius2 - borderWidthAfter);
/*     */         
/* 163 */         if (innerRadiusBefore > innerRadiusFirst) {
/* 164 */           x1 = (float)getIntersectionPoint(new Point(x1, y1), new Point(x4, y4), new Point(x4, (y1 - innerRadiusFirst)), new Point((x1 + innerRadiusBefore), (y1 - innerRadiusFirst))).getX();
/* 165 */           y1 -= innerRadiusFirst;
/* 166 */         } else if (0.0F != innerRadiusBefore && 0.0F != innerRadiusFirst) {
/* 167 */           y1 = (float)getIntersectionPoint(new Point(x1, y1), new Point(x4, y4), new Point((x1 + innerRadiusBefore), y1), new Point((x1 + innerRadiusBefore), (y1 - innerRadiusFirst))).getY();
/* 168 */           x1 += innerRadiusBefore;
/*     */         } 
/* 170 */         if (innerRadiusAfter > innerRadiusSecond) {
/* 171 */           x2 = (float)getIntersectionPoint(new Point(x2, y2), new Point(x3, y3), new Point(x2, (y2 - innerRadiusSecond)), new Point((x2 - innerRadiusAfter), (y2 - innerRadiusSecond))).getX();
/* 172 */           y2 -= innerRadiusSecond; break;
/* 173 */         }  if (0.0F != innerRadiusAfter && 0.0F != innerRadiusSecond) {
/* 174 */           y2 = (float)getIntersectionPoint(new Point(x2, y2), new Point(x3, y3), new Point((x2 - innerRadiusAfter), y2), new Point((x2 - innerRadiusAfter), (y2 - innerRadiusSecond))).getY();
/* 175 */           x2 -= innerRadiusAfter;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case RIGHT:
/* 180 */         x3 = x2 + this.width;
/* 181 */         y3 = y2 - borderWidthAfter;
/* 182 */         x4 = x1 + this.width;
/* 183 */         y4 = y1 + borderWidthBefore;
/*     */         
/* 185 */         innerRadiusBefore = Math.max(0.0F, verticalRadius1 - borderWidthBefore);
/* 186 */         innerRadiusFirst = Math.max(0.0F, horizontalRadius1 - this.width);
/* 187 */         innerRadiusSecond = Math.max(0.0F, horizontalRadius2 - this.width);
/* 188 */         innerRadiusAfter = Math.max(0.0F, verticalRadius2 - borderWidthAfter);
/*     */         
/* 190 */         if (innerRadiusFirst > innerRadiusBefore) {
/* 191 */           x1 = (float)getIntersectionPoint(new Point(x1, y1), new Point(x4, y4), new Point(x1, (y1 - innerRadiusBefore)), new Point((x1 - innerRadiusFirst), (y1 - innerRadiusBefore))).getX();
/* 192 */           y1 -= innerRadiusBefore;
/* 193 */         } else if (0.0F != innerRadiusBefore && 0.0F != innerRadiusFirst) {
/* 194 */           y1 = (float)getIntersectionPoint(new Point(x1, y1), new Point(x4, y4), new Point((x1 - innerRadiusFirst), y1), new Point((x1 - innerRadiusFirst), (y1 - innerRadiusBefore))).getY();
/* 195 */           x1 -= innerRadiusFirst;
/*     */         } 
/*     */         
/* 198 */         if (innerRadiusAfter > innerRadiusSecond) {
/* 199 */           y2 = (float)getIntersectionPoint(new Point(x2, y2), new Point(x3, y3), new Point((x2 - innerRadiusSecond), y2), new Point((x2 - innerRadiusSecond), (y2 + innerRadiusAfter))).getY();
/* 200 */           x2 -= innerRadiusSecond; break;
/* 201 */         }  if (0.0F != innerRadiusAfter && 0.0F != innerRadiusSecond) {
/* 202 */           x2 = (float)getIntersectionPoint(new Point(x2, y2), new Point(x3, y3), new Point(x2, (y2 + innerRadiusAfter)), new Point((x2 - innerRadiusSecond), (y2 + innerRadiusAfter))).getX();
/* 203 */           y2 += innerRadiusAfter;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case BOTTOM:
/* 208 */         x3 = x2 - borderWidthAfter;
/* 209 */         y3 = y2 - this.width;
/* 210 */         x4 = x1 + borderWidthBefore;
/* 211 */         y4 = y1 - this.width;
/*     */         
/* 213 */         innerRadiusBefore = Math.max(0.0F, horizontalRadius1 - borderWidthBefore);
/* 214 */         innerRadiusFirst = Math.max(0.0F, verticalRadius1 - this.width);
/* 215 */         innerRadiusSecond = Math.max(0.0F, verticalRadius2 - this.width);
/* 216 */         innerRadiusAfter = Math.max(0.0F, horizontalRadius2 - borderWidthAfter);
/*     */         
/* 218 */         if (innerRadiusFirst > innerRadiusBefore) {
/* 219 */           y1 = (float)getIntersectionPoint(new Point(x1, y1), new Point(x4, y4), new Point((x1 - innerRadiusBefore), y1), new Point((x1 - innerRadiusBefore), (y1 + innerRadiusFirst))).getY();
/* 220 */           x1 -= innerRadiusBefore;
/* 221 */         } else if (0.0F != innerRadiusBefore && 0.0F != innerRadiusFirst) {
/* 222 */           x1 = (float)getIntersectionPoint(new Point(x1, y1), new Point(x4, y4), new Point(x1, (y1 + innerRadiusFirst)), new Point((x1 - innerRadiusBefore), (y1 + innerRadiusFirst))).getX();
/* 223 */           y1 += innerRadiusFirst;
/*     */         } 
/*     */         
/* 226 */         if (innerRadiusAfter > innerRadiusSecond) {
/* 227 */           x2 = (float)getIntersectionPoint(new Point(x2, y2), new Point(x3, y3), new Point(x2, (y2 + innerRadiusSecond)), new Point((x2 + innerRadiusAfter), (y2 + innerRadiusSecond))).getX();
/* 228 */           y2 += innerRadiusSecond; break;
/* 229 */         }  if (0.0F != innerRadiusAfter && 0.0F != innerRadiusSecond) {
/* 230 */           y2 = (float)getIntersectionPoint(new Point(x2, y2), new Point(x3, y3), new Point((x2 + innerRadiusAfter), y2), new Point((x2 + innerRadiusAfter), (y2 + innerRadiusSecond))).getY();
/* 231 */           x2 += innerRadiusAfter;
/*     */         } 
/*     */         break;
/*     */       case LEFT:
/* 235 */         x3 = x2 - this.width;
/* 236 */         y3 = y2 + borderWidthAfter;
/* 237 */         x4 = x1 - this.width;
/* 238 */         y4 = y1 - borderWidthBefore;
/*     */         
/* 240 */         innerRadiusBefore = Math.max(0.0F, verticalRadius1 - borderWidthBefore);
/* 241 */         innerRadiusFirst = Math.max(0.0F, horizontalRadius1 - this.width);
/* 242 */         innerRadiusSecond = Math.max(0.0F, horizontalRadius2 - this.width);
/* 243 */         innerRadiusAfter = Math.max(0.0F, verticalRadius2 - borderWidthAfter);
/*     */         
/* 245 */         if (innerRadiusFirst > innerRadiusBefore) {
/* 246 */           x1 = (float)getIntersectionPoint(new Point(x1, y1), new Point(x4, y4), new Point(x1, (y1 + innerRadiusBefore)), new Point((x1 + innerRadiusFirst), (y1 + innerRadiusBefore))).getX();
/* 247 */           y1 += innerRadiusBefore;
/* 248 */         } else if (0.0F != innerRadiusBefore && 0.0F != innerRadiusFirst) {
/* 249 */           y1 = (float)getIntersectionPoint(new Point(x1, y1), new Point(x4, y4), new Point((x1 + innerRadiusFirst), y1), new Point((x1 + innerRadiusFirst), (y1 + innerRadiusBefore))).getY();
/* 250 */           x1 += innerRadiusFirst;
/*     */         } 
/*     */         
/* 253 */         if (innerRadiusAfter > innerRadiusSecond) {
/* 254 */           y2 = (float)getIntersectionPoint(new Point(x2, y2), new Point(x3, y3), new Point((x2 + innerRadiusSecond), y2), new Point((x2 + innerRadiusSecond), (y2 - innerRadiusAfter))).getY();
/* 255 */           x2 += innerRadiusSecond; break;
/* 256 */         }  if (0.0F != innerRadiusAfter && 0.0F != innerRadiusSecond) {
/* 257 */           x2 = (float)getIntersectionPoint(new Point(x2, y2), new Point(x3, y3), new Point(x2, (y2 - innerRadiusAfter)), new Point((x2 + innerRadiusSecond), (y2 - innerRadiusAfter))).getX();
/* 258 */           y2 -= innerRadiusAfter;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */     
/* 263 */     canvas.saveState()
/* 264 */       .setFillColor(this.transparentColor.getColor());
/* 265 */     this.transparentColor.applyFillTransparency(canvas);
/* 266 */     canvas
/* 267 */       .moveTo(x1, y1).lineTo(x2, y2).lineTo(x3, y3).lineTo(x4, y4).lineTo(x1, y1).fill()
/* 268 */       .restoreState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
/* 276 */     canvas
/* 277 */       .saveState()
/* 278 */       .setStrokeColor(this.transparentColor.getColor());
/* 279 */     this.transparentColor.applyStrokeTransparency(canvas);
/* 280 */     canvas
/* 281 */       .setLineWidth(this.width)
/* 282 */       .moveTo(x1, y1)
/* 283 */       .lineTo(x2, y2)
/* 284 */       .stroke()
/* 285 */       .restoreState();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/SolidBorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */