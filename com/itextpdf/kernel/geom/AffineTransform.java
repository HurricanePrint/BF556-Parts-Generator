/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AffineTransform
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1330973210523860834L;
/*     */   public static final int TYPE_IDENTITY = 0;
/*     */   public static final int TYPE_TRANSLATION = 1;
/*     */   public static final int TYPE_UNIFORM_SCALE = 2;
/*     */   public static final int TYPE_GENERAL_SCALE = 4;
/*     */   public static final int TYPE_QUADRANT_ROTATION = 8;
/*     */   public static final int TYPE_GENERAL_ROTATION = 16;
/*     */   public static final int TYPE_GENERAL_TRANSFORM = 32;
/*     */   public static final int TYPE_FLIP = 64;
/*     */   public static final int TYPE_MASK_SCALE = 6;
/*     */   public static final int TYPE_MASK_ROTATION = 24;
/*     */   static final int TYPE_UNKNOWN = -1;
/*     */   static final double ZERO = 1.0E-10D;
/*     */   double m00;
/*     */   double m10;
/*     */   double m01;
/*     */   double m11;
/*     */   double m02;
/*     */   double m12;
/*     */   int type;
/*     */   
/*     */   public AffineTransform() {
/* 129 */     this.type = 0;
/* 130 */     this.m00 = this.m11 = 1.0D;
/* 131 */     this.m10 = this.m01 = this.m02 = this.m12 = 0.0D;
/*     */   }
/*     */   
/*     */   public AffineTransform(AffineTransform t) {
/* 135 */     this.type = t.type;
/* 136 */     this.m00 = t.m00;
/* 137 */     this.m10 = t.m10;
/* 138 */     this.m01 = t.m01;
/* 139 */     this.m11 = t.m11;
/* 140 */     this.m02 = t.m02;
/* 141 */     this.m12 = t.m12;
/*     */   }
/*     */   
/*     */   public AffineTransform(double m00, double m10, double m01, double m11, double m02, double m12) {
/* 145 */     this.type = -1;
/* 146 */     this.m00 = m00;
/* 147 */     this.m10 = m10;
/* 148 */     this.m01 = m01;
/* 149 */     this.m11 = m11;
/* 150 */     this.m02 = m02;
/* 151 */     this.m12 = m12;
/*     */   }
/*     */   
/*     */   public AffineTransform(float[] matrix) {
/* 155 */     this.type = -1;
/* 156 */     this.m00 = matrix[0];
/* 157 */     this.m10 = matrix[1];
/* 158 */     this.m01 = matrix[2];
/* 159 */     this.m11 = matrix[3];
/* 160 */     if (matrix.length > 4) {
/* 161 */       this.m02 = matrix[4];
/* 162 */       this.m12 = matrix[5];
/*     */     } 
/*     */   }
/*     */   
/*     */   public AffineTransform(double[] matrix) {
/* 167 */     this.type = -1;
/* 168 */     this.m00 = matrix[0];
/* 169 */     this.m10 = matrix[1];
/* 170 */     this.m01 = matrix[2];
/* 171 */     this.m11 = matrix[3];
/* 172 */     if (matrix.length > 4) {
/* 173 */       this.m02 = matrix[4];
/* 174 */       this.m12 = matrix[5];
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 202 */     if (this.type != -1) {
/* 203 */       return this.type;
/*     */     }
/*     */     
/* 206 */     int type = 0;
/*     */     
/* 208 */     if (this.m00 * this.m01 + this.m10 * this.m11 != 0.0D) {
/* 209 */       type |= 0x20;
/* 210 */       return type;
/*     */     } 
/*     */     
/* 213 */     if (this.m02 != 0.0D || this.m12 != 0.0D) {
/* 214 */       type |= 0x1;
/* 215 */     } else if (this.m00 == 1.0D && this.m11 == 1.0D && this.m01 == 0.0D && this.m10 == 0.0D) {
/* 216 */       type = 0;
/* 217 */       return type;
/*     */     } 
/*     */     
/* 220 */     if (this.m00 * this.m11 - this.m01 * this.m10 < 0.0D) {
/* 221 */       type |= 0x40;
/*     */     }
/*     */     
/* 224 */     double dx = this.m00 * this.m00 + this.m10 * this.m10;
/* 225 */     double dy = this.m01 * this.m01 + this.m11 * this.m11;
/* 226 */     if (dx != dy) {
/* 227 */       type |= 0x4;
/* 228 */     } else if (dx != 1.0D) {
/* 229 */       type |= 0x2;
/*     */     } 
/*     */     
/* 232 */     if ((this.m00 == 0.0D && this.m11 == 0.0D) || (this.m10 == 0.0D && this.m01 == 0.0D && (this.m00 < 0.0D || this.m11 < 0.0D))) {
/*     */       
/* 234 */       type |= 0x8;
/* 235 */     } else if (this.m01 != 0.0D || this.m10 != 0.0D) {
/* 236 */       type |= 0x10;
/*     */     } 
/*     */     
/* 239 */     return type;
/*     */   }
/*     */   
/*     */   public double getScaleX() {
/* 243 */     return this.m00;
/*     */   }
/*     */   
/*     */   public double getScaleY() {
/* 247 */     return this.m11;
/*     */   }
/*     */   
/*     */   public double getShearX() {
/* 251 */     return this.m01;
/*     */   }
/*     */   
/*     */   public double getShearY() {
/* 255 */     return this.m10;
/*     */   }
/*     */   
/*     */   public double getTranslateX() {
/* 259 */     return this.m02;
/*     */   }
/*     */   
/*     */   public double getTranslateY() {
/* 263 */     return this.m12;
/*     */   }
/*     */   
/*     */   public boolean isIdentity() {
/* 267 */     return (getType() == 0);
/*     */   }
/*     */   
/*     */   public void getMatrix(float[] matrix) {
/* 271 */     matrix[0] = (float)this.m00;
/* 272 */     matrix[1] = (float)this.m10;
/* 273 */     matrix[2] = (float)this.m01;
/* 274 */     matrix[3] = (float)this.m11;
/* 275 */     if (matrix.length > 4) {
/* 276 */       matrix[4] = (float)this.m02;
/* 277 */       matrix[5] = (float)this.m12;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getMatrix(double[] matrix) {
/* 282 */     matrix[0] = this.m00;
/* 283 */     matrix[1] = this.m10;
/* 284 */     matrix[2] = this.m01;
/* 285 */     matrix[3] = this.m11;
/* 286 */     if (matrix.length > 4) {
/* 287 */       matrix[4] = this.m02;
/* 288 */       matrix[5] = this.m12;
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getDeterminant() {
/* 293 */     return this.m00 * this.m11 - this.m01 * this.m10;
/*     */   }
/*     */   
/*     */   public void setTransform(float m00, float m10, float m01, float m11, float m02, float m12) {
/* 297 */     this.type = -1;
/* 298 */     this.m00 = m00;
/* 299 */     this.m10 = m10;
/* 300 */     this.m01 = m01;
/* 301 */     this.m11 = m11;
/* 302 */     this.m02 = m02;
/* 303 */     this.m12 = m12;
/*     */   }
/*     */   
/*     */   public void setTransform(double m00, double m10, double m01, double m11, double m02, double m12) {
/* 307 */     this.type = -1;
/* 308 */     this.m00 = m00;
/* 309 */     this.m10 = m10;
/* 310 */     this.m01 = m01;
/* 311 */     this.m11 = m11;
/* 312 */     this.m02 = m02;
/* 313 */     this.m12 = m12;
/*     */   }
/*     */   
/*     */   public void setTransform(AffineTransform t) {
/* 317 */     this.type = t.type;
/* 318 */     setTransform(t.m00, t.m10, t.m01, t.m11, t.m02, t.m12);
/*     */   }
/*     */   
/*     */   public void setToIdentity() {
/* 322 */     this.type = 0;
/* 323 */     this.m00 = this.m11 = 1.0D;
/* 324 */     this.m10 = this.m01 = this.m02 = this.m12 = 0.0D;
/*     */   }
/*     */   
/*     */   public void setToTranslation(double mx, double my) {
/* 328 */     this.m00 = this.m11 = 1.0D;
/* 329 */     this.m01 = this.m10 = 0.0D;
/* 330 */     this.m02 = mx;
/* 331 */     this.m12 = my;
/* 332 */     if (mx == 0.0D && my == 0.0D) {
/* 333 */       this.type = 0;
/*     */     } else {
/* 335 */       this.type = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setToScale(double scx, double scy) {
/* 340 */     this.m00 = scx;
/* 341 */     this.m11 = scy;
/* 342 */     this.m10 = this.m01 = this.m02 = this.m12 = 0.0D;
/* 343 */     if (scx != 1.0D || scy != 1.0D) {
/* 344 */       this.type = -1;
/*     */     } else {
/* 346 */       this.type = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setToShear(double shx, double shy) {
/* 351 */     this.m00 = this.m11 = 1.0D;
/* 352 */     this.m02 = this.m12 = 0.0D;
/* 353 */     this.m01 = shx;
/* 354 */     this.m10 = shy;
/* 355 */     if (shx != 0.0D || shy != 0.0D) {
/* 356 */       this.type = -1;
/*     */     } else {
/* 358 */       this.type = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setToRotation(double angle) {
/* 368 */     double sin = Math.sin(angle);
/* 369 */     double cos = Math.cos(angle);
/* 370 */     if (Math.abs(cos) < 1.0E-10D) {
/* 371 */       cos = 0.0D;
/* 372 */       sin = (sin > 0.0D) ? 1.0D : -1.0D;
/* 373 */     } else if (Math.abs(sin) < 1.0E-10D) {
/* 374 */       sin = 0.0D;
/* 375 */       cos = (cos > 0.0D) ? 1.0D : -1.0D;
/*     */     } 
/* 377 */     this.m00 = this.m11 = (float)cos;
/* 378 */     this.m01 = (float)-sin;
/* 379 */     this.m10 = (float)sin;
/* 380 */     this.m02 = this.m12 = 0.0D;
/* 381 */     this.type = -1;
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
/*     */   public void setToRotation(double angle, double px, double py) {
/* 393 */     setToRotation(angle);
/* 394 */     this.m02 = px * (1.0D - this.m00) + py * this.m10;
/* 395 */     this.m12 = py * (1.0D - this.m00) - px * this.m10;
/* 396 */     this.type = -1;
/*     */   }
/*     */   
/*     */   public static AffineTransform getTranslateInstance(double mx, double my) {
/* 400 */     AffineTransform t = new AffineTransform();
/* 401 */     t.setToTranslation(mx, my);
/* 402 */     return t;
/*     */   }
/*     */   
/*     */   public static AffineTransform getScaleInstance(double scx, double scY) {
/* 406 */     AffineTransform t = new AffineTransform();
/* 407 */     t.setToScale(scx, scY);
/* 408 */     return t;
/*     */   }
/*     */   
/*     */   public static AffineTransform getShearInstance(double shx, double shy) {
/* 412 */     AffineTransform m = new AffineTransform();
/* 413 */     m.setToShear(shx, shy);
/* 414 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AffineTransform getRotateInstance(double angle) {
/* 424 */     AffineTransform t = new AffineTransform();
/* 425 */     t.setToRotation(angle);
/* 426 */     return t;
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
/*     */   public static AffineTransform getRotateInstance(double angle, double x, double y) {
/* 439 */     AffineTransform t = new AffineTransform();
/* 440 */     t.setToRotation(angle, x, y);
/* 441 */     return t;
/*     */   }
/*     */   
/*     */   public void translate(double mx, double my) {
/* 445 */     concatenate(getTranslateInstance(mx, my));
/*     */   }
/*     */   
/*     */   public void scale(double scx, double scy) {
/* 449 */     concatenate(getScaleInstance(scx, scy));
/*     */   }
/*     */   
/*     */   public void shear(double shx, double shy) {
/* 453 */     concatenate(getShearInstance(shx, shy));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotate(double angle) {
/* 462 */     concatenate(getRotateInstance(angle));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotate(double angle, double px, double py) {
/* 473 */     concatenate(getRotateInstance(angle, px, py));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AffineTransform multiply(AffineTransform t1, AffineTransform t2) {
/* 484 */     return new AffineTransform(t1.m00 * t2.m00 + t1.m10 * t2.m01, t1.m00 * t2.m10 + t1.m10 * t2.m11, t1.m01 * t2.m00 + t1.m11 * t2.m01, t1.m01 * t2.m10 + t1.m11 * t2.m11, t1.m02 * t2.m00 + t1.m12 * t2.m01 + t2.m02, t1.m02 * t2.m10 + t1.m12 * t2.m11 + t2.m12);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void concatenate(AffineTransform t) {
/* 494 */     setTransform(multiply(t, this));
/*     */   }
/*     */   
/*     */   public void preConcatenate(AffineTransform t) {
/* 498 */     setTransform(multiply(this, t));
/*     */   }
/*     */   
/*     */   public AffineTransform createInverse() throws NoninvertibleTransformException {
/* 502 */     double det = getDeterminant();
/* 503 */     if (Math.abs(det) < 1.0E-10D)
/*     */     {
/*     */       
/* 506 */       throw new NoninvertibleTransformException("Determinant is zero. Cannot invert transformation.");
/*     */     }
/* 508 */     return new AffineTransform(this.m11 / det, -this.m10 / det, -this.m01 / det, this.m00 / det, (this.m01 * this.m12 - this.m11 * this.m02) / det, (this.m10 * this.m02 - this.m00 * this.m12) / det);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point transform(Point src, Point dst) {
/* 519 */     if (dst == null) {
/* 520 */       dst = new Point();
/*     */     }
/*     */     
/* 523 */     double x = src.getX();
/* 524 */     double y = src.getY();
/*     */     
/* 526 */     dst.setLocation(x * this.m00 + y * this.m01 + this.m02, x * this.m10 + y * this.m11 + this.m12);
/* 527 */     return dst;
/*     */   }
/*     */   
/*     */   public void transform(Point[] src, int srcOff, Point[] dst, int dstOff, int length) {
/* 531 */     while (--length >= 0) {
/* 532 */       Point srcPoint = src[srcOff++];
/* 533 */       double x = srcPoint.getX();
/* 534 */       double y = srcPoint.getY();
/* 535 */       Point dstPoint = dst[dstOff];
/* 536 */       if (dstPoint == null) {
/* 537 */         dstPoint = new Point();
/*     */       }
/* 539 */       dstPoint.setLocation(x * this.m00 + y * this.m01 + this.m02, x * this.m10 + y * this.m11 + this.m12);
/* 540 */       dst[dstOff++] = dstPoint;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void transform(double[] src, int srcOff, double[] dst, int dstOff, int length) {
/* 545 */     int step = 2;
/* 546 */     if (src == dst && srcOff < dstOff && dstOff < srcOff + length * 2) {
/* 547 */       srcOff = srcOff + length * 2 - 2;
/* 548 */       dstOff = dstOff + length * 2 - 2;
/* 549 */       step = -2;
/*     */     } 
/* 551 */     while (--length >= 0) {
/* 552 */       double x = src[srcOff + 0];
/* 553 */       double y = src[srcOff + 1];
/* 554 */       dst[dstOff + 0] = x * this.m00 + y * this.m01 + this.m02;
/* 555 */       dst[dstOff + 1] = x * this.m10 + y * this.m11 + this.m12;
/* 556 */       srcOff += step;
/* 557 */       dstOff += step;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void transform(float[] src, int srcOff, float[] dst, int dstOff, int length) {
/* 562 */     int step = 2;
/* 563 */     if (src == dst && srcOff < dstOff && dstOff < srcOff + length * 2) {
/* 564 */       srcOff = srcOff + length * 2 - 2;
/* 565 */       dstOff = dstOff + length * 2 - 2;
/* 566 */       step = -2;
/*     */     } 
/* 568 */     while (--length >= 0) {
/* 569 */       float x = src[srcOff + 0];
/* 570 */       float y = src[srcOff + 1];
/* 571 */       dst[dstOff + 0] = (float)(x * this.m00 + y * this.m01 + this.m02);
/* 572 */       dst[dstOff + 1] = (float)(x * this.m10 + y * this.m11 + this.m12);
/* 573 */       srcOff += step;
/* 574 */       dstOff += step;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void transform(float[] src, int srcOff, double[] dst, int dstOff, int length) {
/* 579 */     while (--length >= 0) {
/* 580 */       float x = src[srcOff++];
/* 581 */       float y = src[srcOff++];
/* 582 */       dst[dstOff++] = x * this.m00 + y * this.m01 + this.m02;
/* 583 */       dst[dstOff++] = x * this.m10 + y * this.m11 + this.m12;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void transform(double[] src, int srcOff, float[] dst, int dstOff, int length) {
/* 588 */     while (--length >= 0) {
/* 589 */       double x = src[srcOff++];
/* 590 */       double y = src[srcOff++];
/* 591 */       dst[dstOff++] = (float)(x * this.m00 + y * this.m01 + this.m02);
/* 592 */       dst[dstOff++] = (float)(x * this.m10 + y * this.m11 + this.m12);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Point deltaTransform(Point src, Point dst) {
/* 597 */     if (dst == null) {
/* 598 */       dst = new Point();
/*     */     }
/*     */     
/* 601 */     double x = src.getX();
/* 602 */     double y = src.getY();
/*     */     
/* 604 */     dst.setLocation(x * this.m00 + y * this.m01, x * this.m10 + y * this.m11);
/* 605 */     return dst;
/*     */   }
/*     */   
/*     */   public void deltaTransform(double[] src, int srcOff, double[] dst, int dstOff, int length) {
/* 609 */     while (--length >= 0) {
/* 610 */       double x = src[srcOff++];
/* 611 */       double y = src[srcOff++];
/* 612 */       dst[dstOff++] = x * this.m00 + y * this.m01;
/* 613 */       dst[dstOff++] = x * this.m10 + y * this.m11;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Point inverseTransform(Point src, Point dst) throws NoninvertibleTransformException {
/* 618 */     double det = getDeterminant();
/* 619 */     if (Math.abs(det) < 1.0E-10D)
/*     */     {
/*     */       
/* 622 */       throw new NoninvertibleTransformException("Determinant is zero. Cannot invert transformation.");
/*     */     }
/*     */     
/* 625 */     if (dst == null) {
/* 626 */       dst = new Point();
/*     */     }
/*     */     
/* 629 */     double x = src.getX() - this.m02;
/* 630 */     double y = src.getY() - this.m12;
/*     */     
/* 632 */     dst.setLocation((x * this.m11 - y * this.m01) / det, (y * this.m00 - x * this.m10) / det);
/* 633 */     return dst;
/*     */   }
/*     */ 
/*     */   
/*     */   public void inverseTransform(double[] src, int srcOff, double[] dst, int dstOff, int length) throws NoninvertibleTransformException {
/* 638 */     double det = getDeterminant();
/* 639 */     if (Math.abs(det) < 1.0E-10D)
/*     */     {
/*     */       
/* 642 */       throw new NoninvertibleTransformException("Determinant is zero. Cannot invert transformation.");
/*     */     }
/*     */     
/* 645 */     while (--length >= 0) {
/* 646 */       double x = src[srcOff++] - this.m02;
/* 647 */       double y = src[srcOff++] - this.m12;
/* 648 */       dst[dstOff++] = (x * this.m11 - y * this.m01) / det;
/* 649 */       dst[dstOff++] = (y * this.m00 - x * this.m10) / det;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void inverseTransform(float[] src, int srcOff, float[] dst, int dstOff, int length) throws NoninvertibleTransformException {
/* 655 */     float det = (float)getDeterminant();
/* 656 */     if (Math.abs(det) < 1.0E-10D)
/*     */     {
/*     */       
/* 659 */       throw new NoninvertibleTransformException("Determinant is zero. Cannot invert transformation.");
/*     */     }
/*     */     
/* 662 */     while (--length >= 0) {
/* 663 */       float x = (float)(src[srcOff++] - this.m02);
/* 664 */       float y = (float)(src[srcOff++] - this.m12);
/* 665 */       dst[dstOff++] = (float)((x * this.m11 - y * this.m01) / det);
/* 666 */       dst[dstOff++] = (float)((y * this.m00 - x * this.m10) / det);
/*     */     } 
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
/*     */   public AffineTransform clone() throws CloneNotSupportedException {
/* 679 */     return (AffineTransform)super.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 685 */     if (this == o) {
/* 686 */       return true;
/*     */     }
/* 688 */     if (o == null || getClass() != o.getClass()) {
/* 689 */       return false;
/*     */     }
/* 691 */     AffineTransform that = (AffineTransform)o;
/*     */     
/* 693 */     return (Double.compare(that.m00, this.m00) == 0 && 
/* 694 */       Double.compare(that.m10, this.m10) == 0 && 
/* 695 */       Double.compare(that.m01, this.m01) == 0 && 
/* 696 */       Double.compare(that.m11, this.m11) == 0 && 
/* 697 */       Double.compare(that.m02, this.m02) == 0 && 
/* 698 */       Double.compare(that.m12, this.m12) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 703 */     return Objects.hash(new Object[] { Double.valueOf(this.m00), Double.valueOf(this.m10), Double.valueOf(this.m01), Double.valueOf(this.m11), Double.valueOf(this.m02), Double.valueOf(this.m12) });
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/AffineTransform.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */