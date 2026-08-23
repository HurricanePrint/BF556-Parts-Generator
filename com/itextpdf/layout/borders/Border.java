/*     */ package com.itextpdf.layout.borders;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.ColorConstants;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.property.TransparentColor;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Border
/*     */ {
/*  65 */   public static final Border NO_BORDER = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float CURV = 0.447F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SOLID = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DASHED = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DOTTED = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DOUBLE = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ROUND_DOTS = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int _3D_GROOVE = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int _3D_INSET = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int _3D_OUTSET = 7;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int _3D_RIDGE = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TransparentColor transparentColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float width;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int type;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border(float width) {
/* 153 */     this(ColorConstants.BLACK, width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border(Color color, float width) {
/* 163 */     this.transparentColor = new TransparentColor(color);
/* 164 */     this.width = width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Border(Color color, float width, float opacity) {
/* 175 */     this.transparentColor = new TransparentColor(color, opacity);
/* 176 */     this.width = width;
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
/*     */   
/*     */   public abstract void draw(PdfCanvas paramPdfCanvas, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Side paramSide, float paramFloat5, float paramFloat6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float borderRadius, Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 229 */     draw(canvas, x1, y1, x2, y2, borderRadius, borderRadius, borderRadius, borderRadius, defaultSide, borderWidthBefore, borderWidthAfter);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/* 262 */     Logger logger = LoggerFactory.getLogger(Border.class);
/* 263 */     logger.warn(MessageFormatUtil.format("Method {0} is not implemented by default: please, override and implement it. {1} will be used instead.", new Object[] { "Border#draw(PdfCanvas, float, float, float, float, float, float, float, float, Side, float, float", "Border#draw(PdfCanvas, float, float, float, float, Side, float, float)" }));
/*     */ 
/*     */     
/* 266 */     draw(canvas, x1, y1, x2, y2, defaultSide, borderWidthBefore, borderWidthAfter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void drawCellBorder(PdfCanvas paramPdfCanvas, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Side paramSide);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 294 */     return this.transparentColor.getColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getOpacity() {
/* 303 */     return this.transparentColor.getOpacity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 312 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 321 */     this.transparentColor = new TransparentColor(color, this.transparentColor.getOpacity());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/* 330 */     this.width = width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object anObject) {
/* 339 */     if (this == anObject) {
/* 340 */       return true;
/*     */     }
/* 342 */     if (anObject instanceof Border) {
/* 343 */       Border anotherBorder = (Border)anObject;
/* 344 */       if (anotherBorder.getType() != getType() || 
/* 345 */         !anotherBorder.getColor().equals(getColor()) || anotherBorder
/* 346 */         .getWidth() != getWidth() || anotherBorder.transparentColor
/* 347 */         .getOpacity() != this.transparentColor.getOpacity()) {
/* 348 */         return false;
/*     */       }
/*     */     } else {
/* 351 */       return false;
/*     */     } 
/* 353 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 361 */     int h = this.hash;
/*     */     
/* 363 */     if (h == 0) {
/* 364 */       h = (int)getWidth() * 31 + getColor().hashCode();
/* 365 */       h = h * 31 + (int)this.transparentColor.getOpacity();
/* 366 */       this.hash = h;
/*     */     } 
/*     */     
/* 369 */     return h;
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
/*     */   protected Side getBorderSide(float x1, float y1, float x2, float y2, Side defaultSide) {
/* 385 */     boolean isLeft = false;
/* 386 */     boolean isRight = false;
/* 387 */     if (Math.abs(y2 - y1) > 5.0E-4F) {
/* 388 */       isLeft = (y2 - y1 > 0.0F);
/* 389 */       isRight = (y2 - y1 < 0.0F);
/*     */     } 
/*     */     
/* 392 */     boolean isTop = false;
/* 393 */     boolean isBottom = false;
/* 394 */     if (Math.abs(x2 - x1) > 5.0E-4F) {
/* 395 */       isTop = (x2 - x1 > 0.0F);
/* 396 */       isBottom = (x2 - x1 < 0.0F);
/*     */     } 
/*     */     
/* 399 */     if (isTop)
/* 400 */       return isLeft ? Side.LEFT : Side.TOP; 
/* 401 */     if (isRight)
/* 402 */       return Side.RIGHT; 
/* 403 */     if (isBottom)
/* 404 */       return Side.BOTTOM; 
/* 405 */     if (isLeft) {
/* 406 */       return Side.LEFT;
/*     */     }
/*     */     
/* 409 */     return defaultSide;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Side
/*     */   {
/* 418 */     NONE, TOP, RIGHT, BOTTOM, LEFT;
/*     */   }
/*     */   
/*     */   protected Point getIntersectionPoint(Point lineBeg, Point lineEnd, Point clipLineBeg, Point clipLineEnd) {
/* 422 */     double A1 = lineBeg.getY() - lineEnd.getY(), A2 = clipLineBeg.getY() - clipLineEnd.getY();
/* 423 */     double B1 = lineEnd.getX() - lineBeg.getX(), B2 = clipLineEnd.getX() - clipLineBeg.getX();
/* 424 */     double C1 = lineBeg.getX() * lineEnd.getY() - lineBeg.getY() * lineEnd.getX();
/* 425 */     double C2 = clipLineBeg.getX() * clipLineEnd.getY() - clipLineBeg.getY() * clipLineEnd.getX();
/*     */     
/* 427 */     double M = B1 * A2 - B2 * A1;
/*     */     
/* 429 */     return new Point((B2 * C1 - B1 * C2) / M, (C2 * A1 - C1 * A2) / M);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getDotsGap(double distance, float initialGap) {
/* 440 */     double gapsNum = Math.ceil(distance / initialGap);
/* 441 */     if (gapsNum == 0.0D) {
/* 442 */       return initialGap;
/*     */     }
/* 444 */     return (float)(distance / gapsNum);
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
/*     */   protected void drawDiscontinuousBorders(PdfCanvas canvas, Rectangle boundingRectangle, float[] horizontalRadii, float[] verticalRadii, Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
/*     */     float innerRadiusBefore, innerRadiusFirst, innerRadiusSecond, innerRadiusAfter;
/*     */     Point clipPoint1, clipPoint2;
/* 467 */     float x1 = boundingRectangle.getX();
/* 468 */     float y1 = boundingRectangle.getY();
/* 469 */     float x2 = boundingRectangle.getRight();
/* 470 */     float y2 = boundingRectangle.getTop();
/*     */     
/* 472 */     float horizontalRadius1 = horizontalRadii[0];
/* 473 */     float horizontalRadius2 = horizontalRadii[1];
/*     */     
/* 475 */     float verticalRadius1 = verticalRadii[0];
/* 476 */     float verticalRadius2 = verticalRadii[1];
/*     */ 
/*     */     
/* 479 */     float x0 = boundingRectangle.getX();
/* 480 */     float y0 = boundingRectangle.getY();
/* 481 */     float x3 = boundingRectangle.getRight();
/* 482 */     float y3 = boundingRectangle.getTop();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     float widthHalf = this.width / 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 494 */     Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
/* 495 */     switch (borderSide) {
/*     */       
/*     */       case TOP:
/* 498 */         innerRadiusBefore = Math.max(0.0F, horizontalRadius1 - borderWidthBefore);
/* 499 */         innerRadiusFirst = Math.max(0.0F, verticalRadius1 - this.width);
/* 500 */         innerRadiusSecond = Math.max(0.0F, verticalRadius2 - this.width);
/* 501 */         innerRadiusAfter = Math.max(0.0F, horizontalRadius2 - borderWidthAfter);
/*     */ 
/*     */         
/* 504 */         x0 -= borderWidthBefore / 2.0F;
/* 505 */         y0 -= innerRadiusFirst;
/*     */         
/* 507 */         x3 += borderWidthAfter / 2.0F;
/* 508 */         y3 -= innerRadiusSecond;
/*     */         
/* 510 */         clipPoint1 = getIntersectionPoint(new Point((x1 - borderWidthBefore), (y1 + this.width)), new Point(x1, y1), new Point(x0, y0), new Point((x0 + 10.0F), y0));
/* 511 */         clipPoint2 = getIntersectionPoint(new Point((x2 + borderWidthAfter), (y2 + this.width)), new Point(x2, y2), new Point(x3, y3), new Point((x3 - 10.0F), y3));
/* 512 */         if (clipPoint1.x > clipPoint2.x) {
/* 513 */           Point clipPoint = getIntersectionPoint(new Point((x1 - borderWidthBefore), (y1 + this.width)), clipPoint1, clipPoint2, new Point((x2 + borderWidthAfter), (y2 + this.width)));
/* 514 */           canvas.moveTo((x1 - borderWidthBefore), (y1 + this.width)).lineTo(clipPoint.x, clipPoint.y).lineTo((x2 + borderWidthAfter), (y2 + this.width)).lineTo((x1 - borderWidthBefore), (y1 + this.width));
/*     */         } else {
/* 516 */           canvas.moveTo((x1 - borderWidthBefore), (y1 + this.width)).lineTo(clipPoint1.x, clipPoint1.y).lineTo(clipPoint2.x, clipPoint2.y).lineTo((x2 + borderWidthAfter), (y2 + this.width)).lineTo((x1 - borderWidthBefore), (y1 + this.width));
/*     */         } 
/* 518 */         canvas.clip().endPath();
/*     */         
/* 520 */         x1 += innerRadiusBefore;
/* 521 */         y1 += widthHalf;
/*     */         
/* 523 */         x2 -= innerRadiusAfter;
/* 524 */         y2 += widthHalf;
/*     */         
/* 526 */         canvas
/* 527 */           .moveTo(x0, y0).curveTo(x0, (y0 + innerRadiusFirst * 0.447F), (x1 - innerRadiusBefore * 0.447F), y1, x1, y1)
/* 528 */           .lineTo(x2, y2)
/* 529 */           .curveTo((x2 + innerRadiusAfter * 0.447F), y2, x3, (y3 + innerRadiusSecond * 0.447F), x3, y3);
/*     */         break;
/*     */       case RIGHT:
/* 532 */         innerRadiusBefore = Math.max(0.0F, verticalRadius1 - borderWidthBefore);
/* 533 */         innerRadiusFirst = Math.max(0.0F, horizontalRadius1 - this.width);
/* 534 */         innerRadiusSecond = Math.max(0.0F, horizontalRadius2 - this.width);
/* 535 */         innerRadiusAfter = Math.max(0.0F, verticalRadius2 - borderWidthAfter);
/*     */         
/* 537 */         x0 -= innerRadiusFirst;
/* 538 */         y0 += borderWidthBefore / 2.0F;
/*     */         
/* 540 */         x3 -= innerRadiusSecond;
/* 541 */         y3 -= borderWidthAfter / 2.0F;
/*     */         
/* 543 */         clipPoint1 = getIntersectionPoint(new Point((x1 + this.width), (y1 + borderWidthBefore)), new Point(x1, y1), new Point(x0, y0), new Point(x0, (y0 - 10.0F)));
/* 544 */         clipPoint2 = getIntersectionPoint(new Point((x2 + this.width), (y2 - borderWidthAfter)), new Point(x2, y2), new Point(x3, y3), new Point(x3, (y3 - 10.0F)));
/* 545 */         if (clipPoint1.y < clipPoint2.y) {
/* 546 */           Point clipPoint = getIntersectionPoint(new Point((x1 + this.width), (y1 + borderWidthBefore)), clipPoint1, clipPoint2, new Point((x2 + this.width), (y2 - borderWidthAfter)));
/* 547 */           canvas.moveTo((x1 + this.width), (y1 + borderWidthBefore)).lineTo(clipPoint.x, clipPoint.y).lineTo((x2 + this.width), (y2 - borderWidthAfter)).lineTo((x1 + this.width), (y1 + borderWidthBefore)).clip().endPath();
/*     */         } else {
/* 549 */           canvas.moveTo((x1 + this.width), (y1 + borderWidthBefore)).lineTo(clipPoint1.x, clipPoint1.y).lineTo(clipPoint2.x, clipPoint2.y).lineTo((x2 + this.width), (y2 - borderWidthAfter)).lineTo((x1 + this.width), (y1 + borderWidthBefore)).clip().endPath();
/*     */         } 
/* 551 */         canvas.clip().endPath();
/*     */         
/* 553 */         x1 += widthHalf;
/* 554 */         y1 -= innerRadiusBefore;
/*     */         
/* 556 */         x2 += widthHalf;
/* 557 */         y2 += innerRadiusAfter;
/*     */         
/* 559 */         canvas
/* 560 */           .moveTo(x0, y0).curveTo((x0 + innerRadiusFirst * 0.447F), y0, x1, (y1 + innerRadiusBefore * 0.447F), x1, y1)
/* 561 */           .lineTo(x2, y2)
/* 562 */           .curveTo(x2, (y2 - innerRadiusAfter * 0.447F), (x3 + innerRadiusSecond * 0.447F), y3, x3, y3);
/*     */         break;
/*     */       
/*     */       case BOTTOM:
/* 566 */         innerRadiusBefore = Math.max(0.0F, horizontalRadius1 - borderWidthBefore);
/* 567 */         innerRadiusFirst = Math.max(0.0F, verticalRadius1 - this.width);
/* 568 */         innerRadiusSecond = Math.max(0.0F, verticalRadius2 - this.width);
/* 569 */         innerRadiusAfter = Math.max(0.0F, horizontalRadius2 - borderWidthAfter);
/*     */         
/* 571 */         x0 += borderWidthBefore / 2.0F;
/* 572 */         y0 += innerRadiusFirst;
/*     */         
/* 574 */         x3 -= borderWidthAfter / 2.0F;
/* 575 */         y3 += innerRadiusSecond;
/*     */         
/* 577 */         clipPoint1 = getIntersectionPoint(new Point((x1 + borderWidthBefore), (y1 - this.width)), new Point(x1, y1), new Point(x0, y0), new Point((x0 - 10.0F), y0));
/* 578 */         clipPoint2 = getIntersectionPoint(new Point((x2 - borderWidthAfter), (y2 - this.width)), new Point(x2, y2), new Point(x3, y3), new Point((x3 + 10.0F), y3));
/* 579 */         if (clipPoint1.x < clipPoint2.x) {
/* 580 */           Point clipPoint = getIntersectionPoint(new Point((x1 + borderWidthBefore), (y1 - this.width)), clipPoint1, clipPoint2, new Point((x2 - borderWidthAfter), (y2 - this.width)));
/* 581 */           canvas.moveTo((x1 + borderWidthBefore), (y1 - this.width)).lineTo(clipPoint.x, clipPoint.y).lineTo((x2 - borderWidthAfter), (y2 - this.width)).lineTo((x1 + borderWidthBefore), (y1 - this.width));
/*     */         } else {
/* 583 */           canvas.moveTo((x1 + borderWidthBefore), (y1 - this.width)).lineTo(clipPoint1.x, clipPoint1.y).lineTo(clipPoint2.x, clipPoint2.y).lineTo((x2 - borderWidthAfter), (y2 - this.width)).lineTo((x1 + borderWidthBefore), (y1 - this.width));
/*     */         } 
/* 585 */         canvas.clip().endPath();
/*     */         
/* 587 */         x1 -= innerRadiusBefore;
/* 588 */         y1 -= widthHalf;
/*     */         
/* 590 */         x2 += innerRadiusAfter;
/* 591 */         y2 -= widthHalf;
/*     */         
/* 593 */         canvas
/* 594 */           .moveTo(x0, y0).curveTo(x0, (y0 - innerRadiusFirst * 0.447F), (x1 + innerRadiusBefore * 0.447F), y1, x1, y1)
/* 595 */           .lineTo(x2, y2)
/* 596 */           .curveTo((x2 - innerRadiusAfter * 0.447F), y2, x3, (y3 - innerRadiusSecond * 0.447F), x3, y3);
/*     */         break;
/*     */       
/*     */       case LEFT:
/* 600 */         innerRadiusBefore = Math.max(0.0F, verticalRadius1 - borderWidthBefore);
/* 601 */         innerRadiusFirst = Math.max(0.0F, horizontalRadius1 - this.width);
/* 602 */         innerRadiusSecond = Math.max(0.0F, horizontalRadius2 - this.width);
/* 603 */         innerRadiusAfter = Math.max(0.0F, verticalRadius2 - borderWidthAfter);
/*     */         
/* 605 */         x0 += innerRadiusFirst;
/* 606 */         y0 -= borderWidthBefore / 2.0F;
/*     */         
/* 608 */         x3 += innerRadiusSecond;
/* 609 */         y3 += borderWidthAfter / 2.0F;
/*     */         
/* 611 */         clipPoint1 = getIntersectionPoint(new Point((x1 - this.width), (y1 - borderWidthBefore)), new Point(x1, y1), new Point(x0, y0), new Point(x0, (y0 + 10.0F)));
/* 612 */         clipPoint2 = getIntersectionPoint(new Point((x2 - this.width), (y2 + borderWidthAfter)), new Point(x2, y2), new Point(x3, y3), new Point(x3, (y3 + 10.0F)));
/* 613 */         if (clipPoint1.y > clipPoint2.y) {
/* 614 */           Point clipPoint = getIntersectionPoint(new Point((x1 - this.width), (y1 - borderWidthBefore)), clipPoint1, clipPoint2, new Point((x2 - this.width), (y2 + borderWidthAfter)));
/* 615 */           canvas.moveTo((x1 - this.width), (y1 - borderWidthBefore)).lineTo(clipPoint.x, clipPoint.y).lineTo((x2 - this.width), (y2 + borderWidthAfter)).lineTo((x1 - this.width), (y1 - borderWidthBefore));
/*     */         } else {
/* 617 */           canvas.moveTo((x1 - this.width), (y1 - borderWidthBefore)).lineTo(clipPoint1.x, clipPoint1.y).lineTo(clipPoint2.x, clipPoint2.y).lineTo((x2 - this.width), (y2 + borderWidthAfter)).lineTo((x1 - this.width), (y1 - borderWidthBefore));
/*     */         } 
/* 619 */         canvas.clip().endPath();
/*     */         
/* 621 */         x1 -= widthHalf;
/* 622 */         y1 += innerRadiusBefore;
/*     */         
/* 624 */         x2 -= widthHalf;
/* 625 */         y2 -= innerRadiusAfter;
/*     */         
/* 627 */         canvas
/* 628 */           .moveTo(x0, y0).curveTo((x0 - innerRadiusFirst * 0.447F), y0, x1, (y1 - innerRadiusBefore * 0.447F), x1, y1)
/* 629 */           .lineTo(x2, y2)
/* 630 */           .curveTo(x2, (y2 + innerRadiusAfter * 0.447F), (x3 - innerRadiusSecond * 0.447F), y3, x3, y3);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 635 */     canvas
/* 636 */       .stroke()
/* 637 */       .restoreState();
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
/*     */   protected float[] getStartingPointsForBorderSide(float x1, float y1, float x2, float y2, Side defaultSide) {
/* 651 */     float widthHalf = this.width / 2.0F;
/*     */     
/* 653 */     Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
/* 654 */     switch (borderSide) {
/*     */       case TOP:
/* 656 */         y1 += widthHalf;
/* 657 */         y2 += widthHalf;
/*     */         break;
/*     */       case RIGHT:
/* 660 */         x1 += widthHalf;
/* 661 */         x2 += widthHalf;
/*     */         break;
/*     */       case BOTTOM:
/* 664 */         y1 -= widthHalf;
/* 665 */         y2 -= widthHalf;
/*     */         break;
/*     */       case LEFT:
/* 668 */         x1 -= widthHalf;
/* 669 */         x2 -= widthHalf;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 674 */     return new float[] { x1, y1, x2, y2 };
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/borders/Border.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */