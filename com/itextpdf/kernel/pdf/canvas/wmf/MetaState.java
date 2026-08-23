/*     */ package com.itextpdf.kernel.pdf.canvas.wmf;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.ColorConstants;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetaState
/*     */ {
/*     */   public static final int TA_NOUPDATECP = 0;
/*     */   public static final int TA_UPDATECP = 1;
/*     */   public static final int TA_LEFT = 0;
/*     */   public static final int TA_RIGHT = 2;
/*     */   public static final int TA_CENTER = 6;
/*     */   public static final int TA_TOP = 0;
/*     */   public static final int TA_BOTTOM = 8;
/*     */   public static final int TA_BASELINE = 24;
/*     */   public static final int TRANSPARENT = 1;
/*     */   public static final int OPAQUE = 2;
/*     */   public static final int ALTERNATE = 1;
/*     */   public static final int WINDING = 2;
/*     */   public Stack<MetaState> savedStates;
/*     */   public List<MetaObject> MetaObjects;
/*     */   public Point currentPoint;
/*     */   public MetaPen currentPen;
/*     */   public MetaBrush currentBrush;
/*     */   public MetaFont currentFont;
/* 111 */   public Color currentBackgroundColor = ColorConstants.WHITE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public Color currentTextColor = ColorConstants.BLACK;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public int backgroundMode = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public int polyFillMode = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public int lineJoin = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int textAlign;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int offsetWx;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int offsetWy;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int extentWx;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int extentWy;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float scalingX;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float scalingY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaState() {
/* 173 */     this.savedStates = new Stack<>();
/* 174 */     this.MetaObjects = new ArrayList<>();
/* 175 */     this.currentPoint = new Point(0, 0);
/* 176 */     this.currentPen = new MetaPen();
/* 177 */     this.currentBrush = new MetaBrush();
/* 178 */     this.currentFont = new MetaFont();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaState(MetaState state) {
/* 187 */     setMetaState(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetaState(MetaState state) {
/* 196 */     this.savedStates = state.savedStates;
/* 197 */     this.MetaObjects = state.MetaObjects;
/* 198 */     this.currentPoint = state.currentPoint;
/* 199 */     this.currentPen = state.currentPen;
/* 200 */     this.currentBrush = state.currentBrush;
/* 201 */     this.currentFont = state.currentFont;
/* 202 */     this.currentBackgroundColor = state.currentBackgroundColor;
/* 203 */     this.currentTextColor = state.currentTextColor;
/* 204 */     this.backgroundMode = state.backgroundMode;
/* 205 */     this.polyFillMode = state.polyFillMode;
/* 206 */     this.textAlign = state.textAlign;
/* 207 */     this.lineJoin = state.lineJoin;
/* 208 */     this.offsetWx = state.offsetWx;
/* 209 */     this.offsetWy = state.offsetWy;
/* 210 */     this.extentWx = state.extentWx;
/* 211 */     this.extentWy = state.extentWy;
/* 212 */     this.scalingX = state.scalingX;
/* 213 */     this.scalingY = state.scalingY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMetaObject(MetaObject object) {
/* 222 */     for (int k = 0; k < this.MetaObjects.size(); k++) {
/* 223 */       if (this.MetaObjects.get(k) == null) {
/* 224 */         this.MetaObjects.set(k, object);
/*     */         return;
/*     */       } 
/*     */     } 
/* 228 */     this.MetaObjects.add(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectMetaObject(int index, PdfCanvas cb) {
/*     */     int style;
/* 238 */     MetaObject obj = this.MetaObjects.get(index);
/* 239 */     if (obj == null) {
/*     */       return;
/*     */     }
/* 242 */     switch (obj.getType()) {
/*     */       case 2:
/* 244 */         this.currentBrush = (MetaBrush)obj;
/* 245 */         style = this.currentBrush.getStyle();
/* 246 */         if (style == 0) {
/* 247 */           Color color = this.currentBrush.getColor();
/* 248 */           cb.setFillColor(color); break;
/*     */         } 
/* 250 */         if (style == 2) {
/* 251 */           Color color = this.currentBackgroundColor;
/* 252 */           cb.setFillColor(color);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 1:
/* 257 */         this.currentPen = (MetaPen)obj;
/* 258 */         style = this.currentPen.getStyle();
/* 259 */         if (style != 5) {
/* 260 */           Color color = this.currentPen.getColor();
/* 261 */           cb.setStrokeColor(color);
/* 262 */           cb.setLineWidth(Math.abs(this.currentPen.getPenWidth() * this.scalingX / this.extentWx));
/* 263 */           switch (style) {
/*     */             case 1:
/* 265 */               cb.setLineDash(18.0F, 6.0F, 0.0F);
/*     */               break;
/*     */             case 3:
/* 268 */               cb.writeLiteral("[9 6 3 6]0 d\n");
/*     */               break;
/*     */             case 4:
/* 271 */               cb.writeLiteral("[9 3 3 3 3 3]0 d\n");
/*     */               break;
/*     */             case 2:
/* 274 */               cb.setLineDash(3.0F, 0.0F);
/*     */               break;
/*     */           } 
/* 277 */           cb.setLineDash(0.0F);
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 285 */         this.currentFont = (MetaFont)obj;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteMetaObject(int index) {
/* 297 */     this.MetaObjects.set(index, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveState(PdfCanvas cb) {
/* 306 */     cb.saveState();
/* 307 */     MetaState state = new MetaState(this);
/* 308 */     this.savedStates.push(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreState(int index, PdfCanvas cb) {
/*     */     int pops;
/* 319 */     if (index < 0) {
/* 320 */       pops = Math.min(-index, this.savedStates.size());
/*     */     } else {
/* 322 */       pops = Math.max(this.savedStates.size() - index, 0);
/* 323 */     }  if (pops == 0)
/*     */       return; 
/* 325 */     MetaState state = null;
/* 326 */     while (pops-- != 0) {
/* 327 */       cb.restoreState();
/* 328 */       state = this.savedStates.pop();
/*     */     } 
/* 330 */     setMetaState(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup(PdfCanvas cb) {
/* 339 */     int k = this.savedStates.size();
/* 340 */     while (k-- > 0) {
/* 341 */       cb.restoreState();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float transformX(int x) {
/* 351 */     return (x - this.offsetWx) * this.scalingX / this.extentWx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float transformY(int y) {
/* 361 */     return (1.0F - (y - this.offsetWy) / this.extentWy) * this.scalingY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScalingX(float scalingX) {
/* 370 */     this.scalingX = scalingX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScalingY(float scalingY) {
/* 379 */     this.scalingY = scalingY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOffsetWx(int offsetWx) {
/* 388 */     this.offsetWx = offsetWx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOffsetWy(int offsetWy) {
/* 397 */     this.offsetWy = offsetWy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtentWx(int extentWx) {
/* 406 */     this.extentWx = extentWx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtentWy(int extentWy) {
/* 415 */     this.extentWy = extentWy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float transformAngle(float angle) {
/* 426 */     float ta = (this.scalingY < 0.0F) ? -angle : angle;
/* 427 */     return (float)((this.scalingX < 0.0F) ? (Math.PI - ta) : ta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentPoint(Point p) {
/* 436 */     this.currentPoint = p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getCurrentPoint() {
/* 445 */     return this.currentPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaBrush getCurrentBrush() {
/* 454 */     return this.currentBrush;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaPen getCurrentPen() {
/* 463 */     return this.currentPen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaFont getCurrentFont() {
/* 472 */     return this.currentFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getCurrentBackgroundColor() {
/* 480 */     return this.currentBackgroundColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentBackgroundColor(Color currentBackgroundColor) {
/* 487 */     this.currentBackgroundColor = currentBackgroundColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getCurrentTextColor() {
/* 494 */     return this.currentTextColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentTextColor(Color currentTextColor) {
/* 501 */     this.currentTextColor = currentTextColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBackgroundMode() {
/* 508 */     return this.backgroundMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundMode(int backgroundMode) {
/* 515 */     this.backgroundMode = backgroundMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextAlign() {
/* 522 */     return this.textAlign;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextAlign(int textAlign) {
/* 529 */     this.textAlign = textAlign;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPolyFillMode() {
/* 536 */     return this.polyFillMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolyFillMode(int polyFillMode) {
/* 543 */     this.polyFillMode = polyFillMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineJoinRectangle(PdfCanvas cb) {
/* 552 */     if (this.lineJoin != 0) {
/* 553 */       this.lineJoin = 0;
/* 554 */       cb.setLineJoinStyle(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineJoinPolygon(PdfCanvas cb) {
/* 564 */     if (this.lineJoin == 0) {
/* 565 */       this.lineJoin = 1;
/* 566 */       cb.setLineJoinStyle(1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getLineNeutral() {
/* 576 */     return (this.lineJoin == 0);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/wmf/MetaState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */