/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rectangle
/*     */   implements Cloneable, Serializable
/*     */ {
/*  61 */   static float EPS = 1.0E-4F;
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 8025677415569233446L;
/*     */ 
/*     */   
/*     */   protected float x;
/*     */ 
/*     */   
/*     */   protected float y;
/*     */ 
/*     */   
/*     */   protected float width;
/*     */   
/*     */   protected float height;
/*     */ 
/*     */   
/*     */   public Rectangle(float x, float y, float width, float height) {
/*  79 */     this.x = x;
/*  80 */     this.y = y;
/*  81 */     this.width = width;
/*  82 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle(float width, float height) {
/*  92 */     this(0.0F, 0.0F, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle(Rectangle rect) {
/* 101 */     this(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Rectangle getCommonRectangle(Rectangle... rectangles) {
/* 111 */     float ury = -3.4028235E38F;
/* 112 */     float llx = Float.MAX_VALUE;
/* 113 */     float lly = Float.MAX_VALUE;
/* 114 */     float urx = -3.4028235E38F;
/* 115 */     for (Rectangle rectangle : rectangles) {
/* 116 */       if (rectangle != null) {
/*     */         
/* 118 */         Rectangle rec = rectangle.clone();
/* 119 */         if (rec.getY() < lly)
/* 120 */           lly = rec.getY(); 
/* 121 */         if (rec.getX() < llx)
/* 122 */           llx = rec.getX(); 
/* 123 */         if (rec.getY() + rec.getHeight() > ury)
/* 124 */           ury = rec.getY() + rec.getHeight(); 
/* 125 */         if (rec.getX() + rec.getWidth() > urx)
/* 126 */           urx = rec.getX() + rec.getWidth(); 
/*     */       } 
/*     */     } 
/* 129 */     return new Rectangle(llx, lly, urx - llx, ury - lly);
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
/*     */   
/*     */   public static Rectangle getRectangleOnRotatedPage(Rectangle rect, PdfPage page) {
/* 143 */     Rectangle resultRect = rect;
/* 144 */     int rotation = page.getRotation();
/* 145 */     if (0 != rotation) {
/* 146 */       Rectangle pageSize = page.getPageSize();
/* 147 */       switch (rotation / 90 % 4) {
/*     */         case 1:
/* 149 */           resultRect = new Rectangle(pageSize.getWidth() - resultRect.getTop(), resultRect.getLeft(), resultRect.getHeight(), resultRect.getWidth());
/*     */           break;
/*     */         case 2:
/* 152 */           resultRect = new Rectangle(pageSize.getWidth() - resultRect.getRight(), pageSize.getHeight() - resultRect.getTop(), resultRect.getWidth(), resultRect.getHeight());
/*     */           break;
/*     */         case 3:
/* 155 */           resultRect = new Rectangle(resultRect.getLeft(), pageSize.getHeight() - resultRect.getRight(), resultRect.getHeight(), resultRect.getWidth());
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 162 */     return resultRect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Rectangle calculateBBox(List<Point> points) {
/* 173 */     List<Double> xs = new ArrayList<>();
/* 174 */     List<Double> ys = new ArrayList<>();
/* 175 */     for (Point point : points) {
/* 176 */       xs.add(Double.valueOf(point.getX()));
/* 177 */       ys.add(Double.valueOf(point.getY()));
/*     */     } 
/*     */     
/* 180 */     double left = ((Double)Collections.<Double>min(xs)).doubleValue();
/* 181 */     double bottom = ((Double)Collections.<Double>min(ys)).doubleValue();
/* 182 */     double right = ((Double)Collections.<Double>max(xs)).doubleValue();
/* 183 */     double top = ((Double)Collections.<Double>max(ys)).doubleValue();
/*     */     
/* 185 */     return new Rectangle((float)left, (float)bottom, (float)(right - left), (float)(top - bottom));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point[] toPointsArray() {
/* 194 */     return new Point[] { new Point(this.x, this.y), new Point((this.x + this.width), this.y), new Point((this.x + this.width), (this.y + this.height)), new Point(this.x, (this.y + this.height)) };
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
/*     */   
/*     */   public Rectangle getIntersection(Rectangle rect) {
/* 208 */     Rectangle result = null;
/*     */ 
/*     */     
/* 211 */     float llx = Math.max(this.x, rect.x);
/* 212 */     float lly = Math.max(this.y, rect.y);
/* 213 */     float urx = Math.min(getRight(), rect.getRight());
/* 214 */     float ury = Math.min(getTop(), rect.getTop());
/*     */ 
/*     */     
/* 217 */     float width = urx - llx;
/* 218 */     if (Math.abs(width) < EPS) {
/* 219 */       width = 0.0F;
/*     */     }
/*     */     
/* 222 */     float height = ury - lly;
/* 223 */     if (Math.abs(height) < EPS) {
/* 224 */       height = 0.0F;
/*     */     }
/*     */     
/* 227 */     if (Float.compare(width, 0.0F) >= 0 && 
/* 228 */       Float.compare(height, 0.0F) >= 0) {
/* 229 */       if (Float.compare(width, 0.0F) < 0) width = 0.0F; 
/* 230 */       if (Float.compare(height, 0.0F) < 0) height = 0.0F; 
/* 231 */       result = new Rectangle(llx, lly, width, height);
/*     */     } 
/*     */     
/* 234 */     return result;
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
/*     */   public boolean contains(Rectangle rect) {
/* 246 */     float llx = getX();
/* 247 */     float lly = getY();
/* 248 */     float urx = llx + getWidth();
/* 249 */     float ury = lly + getHeight();
/*     */     
/* 251 */     float rllx = rect.getX();
/* 252 */     float rlly = rect.getY();
/* 253 */     float rurx = rllx + rect.getWidth();
/* 254 */     float rury = rlly + rect.getHeight();
/*     */     
/* 256 */     return (llx - EPS <= rllx && lly - EPS <= rlly && rurx <= urx + EPS && rury <= ury + EPS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overlaps(Rectangle rect) {
/* 267 */     return overlaps(rect, -EPS);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overlaps(Rectangle rect, float epsilon) {
/* 283 */     if (getX() + getWidth() < rect.getX() + epsilon) {
/* 284 */       return false;
/*     */     }
/*     */     
/* 287 */     if (getX() + epsilon > rect.getX() + rect.getWidth()) {
/* 288 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 292 */     if (getY() + getHeight() < rect.getY() + epsilon) {
/* 293 */       return false;
/*     */     }
/*     */     
/* 296 */     if (getY() + epsilon > rect.getY() + rect.getHeight()) {
/* 297 */       return false;
/*     */     }
/*     */     
/* 300 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle setBbox(float llx, float lly, float urx, float ury) {
/* 318 */     if (llx > urx) {
/* 319 */       float temp = llx;
/* 320 */       llx = urx;
/* 321 */       urx = temp;
/*     */     } 
/*     */     
/* 324 */     if (lly > ury) {
/* 325 */       float temp = lly;
/* 326 */       lly = ury;
/* 327 */       ury = temp;
/*     */     } 
/* 329 */     this.x = llx;
/* 330 */     this.y = lly;
/* 331 */     this.width = urx - llx;
/* 332 */     this.height = ury - lly;
/* 333 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/* 342 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle setX(float x) {
/* 352 */     this.x = x;
/* 353 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/* 362 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle setY(float y) {
/* 372 */     this.y = y;
/* 373 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 382 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle setWidth(float width) {
/* 392 */     this.width = width;
/* 393 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 402 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle setHeight(float height) {
/* 412 */     this.height = height;
/* 413 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle increaseHeight(float extra) {
/* 423 */     this.height += extra;
/* 424 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle decreaseHeight(float extra) {
/* 434 */     this.height -= extra;
/* 435 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle increaseWidth(float extra) {
/* 445 */     this.width += extra;
/* 446 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle decreaseWidth(float extra) {
/* 456 */     this.width -= extra;
/* 457 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLeft() {
/* 466 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRight() {
/* 475 */     return this.x + this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTop() {
/* 484 */     return this.y + this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBottom() {
/* 493 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle moveDown(float move) {
/* 503 */     this.y -= move;
/* 504 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle moveUp(float move) {
/* 515 */     this.y += move;
/* 516 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle moveRight(float move) {
/* 526 */     this.x += move;
/* 527 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle moveLeft(float move) {
/* 537 */     this.x -= move;
/* 538 */     return this;
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
/*     */   
/*     */   public Rectangle applyMargins(float topIndent, float rightIndent, float bottomIndent, float leftIndent, boolean reverse) {
/* 552 */     this.x += leftIndent * (reverse ? -1 : true);
/* 553 */     this.width -= (leftIndent + rightIndent) * (reverse ? -1 : true);
/* 554 */     this.y += bottomIndent * (reverse ? -1 : true);
/* 555 */     this.height -= (topIndent + bottomIndent) * (reverse ? -1 : true);
/* 556 */     return this;
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
/*     */   public boolean intersectsLine(float x1, float y1, float x2, float y2) {
/* 569 */     double rx1 = getX();
/* 570 */     double ry1 = getY();
/* 571 */     double rx2 = rx1 + getWidth();
/* 572 */     double ry2 = ry1 + getHeight();
/* 573 */     return ((rx1 <= x1 && x1 <= rx2 && ry1 <= y1 && y1 <= ry2) || (rx1 <= x2 && x2 <= rx2 && ry1 <= y2 && y2 <= ry2) || 
/*     */ 
/*     */       
/* 576 */       linesIntersect(rx1, ry1, rx2, ry2, x1, y1, x2, y2) || 
/* 577 */       linesIntersect(rx2, ry1, rx1, ry2, x1, y1, x2, y2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 587 */     return "Rectangle: " + getWidth() + 'x' + 
/*     */       
/* 589 */       getHeight();
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
/*     */   public Rectangle clone() {
/*     */     try {
/* 602 */       return (Rectangle)super.clone();
/* 603 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 605 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsWithEpsilon(Rectangle that) {
/* 616 */     return equalsWithEpsilon(that, EPS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsWithEpsilon(Rectangle that, float eps) {
/* 627 */     float dx = Math.abs(this.x - that.x);
/* 628 */     float dy = Math.abs(this.y - that.y);
/* 629 */     float dw = Math.abs(this.width - that.width);
/* 630 */     float dh = Math.abs(this.height - that.height);
/* 631 */     return (dx < eps && dy < eps && dw < eps && dh < eps);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean linesIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
/* 647 */     x2 -= x1;
/* 648 */     y2 -= y1;
/*     */     
/* 650 */     x3 -= x1;
/* 651 */     y3 -= y1;
/*     */     
/* 653 */     x4 -= x1;
/* 654 */     y4 -= y1;
/*     */     
/* 656 */     double AvB = x2 * y3 - x3 * y2;
/* 657 */     double AvC = x2 * y4 - x4 * y2;
/*     */ 
/*     */     
/* 660 */     if (AvB == 0.0D && AvC == 0.0D) {
/* 661 */       if (x2 != 0.0D) {
/* 662 */         return (x4 * x3 <= 0.0D || (x3 * x2 >= 0.0D && ((x2 > 0.0D) ? (x3 <= x2 || x4 <= x2) : (x3 >= x2 || x4 >= x2))));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 667 */       if (y2 != 0.0D) {
/* 668 */         return (y4 * y3 <= 0.0D || (y3 * y2 >= 0.0D && ((y2 > 0.0D) ? (y3 <= y2 || y4 <= y2) : (y3 >= y2 || y4 >= y2))));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 673 */       return false;
/*     */     } 
/*     */     
/* 676 */     double BvC = x3 * y4 - x4 * y3;
/*     */     
/* 678 */     return (AvB * AvC <= 0.0D && BvC * (AvB + BvC - AvC) <= 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Rectangle> createBoundingRectanglesFromQuadPoint(PdfArray quadPoints) throws PdfException {
/* 688 */     List<Rectangle> boundingRectangles = new ArrayList<>();
/* 689 */     if (quadPoints.size() % 8 != 0) {
/* 690 */       throw new PdfException("The QuadPoint Array length is not a multiple of 8.");
/*     */     }
/* 692 */     for (int i = 0; i < quadPoints.size(); i += 8) {
/* 693 */       float[] quadPointEntry = Arrays.copyOfRange(quadPoints.toFloatArray(), i, i + 8);
/* 694 */       PdfArray quadPointEntryFA = new PdfArray(quadPointEntry);
/* 695 */       boundingRectangles.add(createBoundingRectangleFromQuadPoint(quadPointEntryFA));
/*     */     } 
/* 697 */     return boundingRectangles;
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
/*     */   public static Rectangle createBoundingRectangleFromQuadPoint(PdfArray quadPoints) throws PdfException {
/* 709 */     if (quadPoints.size() % 8 != 0) {
/* 710 */       throw new PdfException("The QuadPoint Array length is not a multiple of 8.");
/*     */     }
/* 712 */     float llx = Float.MAX_VALUE;
/* 713 */     float lly = Float.MAX_VALUE;
/* 714 */     float urx = -3.4028235E38F;
/* 715 */     float ury = -3.4028235E38F;
/*     */ 
/*     */     
/* 718 */     for (int j = 0; j < 8; j += 2) {
/* 719 */       float x = quadPoints.getAsNumber(j).floatValue();
/* 720 */       float y = quadPoints.getAsNumber(j + 1).floatValue();
/*     */       
/* 722 */       if (x < llx) llx = x; 
/* 723 */       if (x > urx) urx = x; 
/* 724 */       if (y < lly) lly = y; 
/* 725 */       if (y > ury) ury = y; 
/*     */     } 
/* 727 */     return new Rectangle(llx, lly, urx - llx, ury - lly);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/Rectangle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */