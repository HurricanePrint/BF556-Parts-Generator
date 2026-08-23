/*     */ package com.itextpdf.svg.renderers.path.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EllipticalCurveTo
/*     */   extends AbstractPathShape
/*     */ {
/*     */   static final int ARGUMENT_SIZE = 7;
/*     */   private Point startPoint;
/*     */   private static final double EPS = 1.0E-5D;
/*     */   
/*     */   public EllipticalCurveTo() {
/*  76 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EllipticalCurveTo(boolean relative) {
/*  85 */     super(relative);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCoordinates(String[] inputCoordinates, Point previous) {
/*  90 */     this.startPoint = previous;
/*  91 */     if (inputCoordinates.length < 7) {
/*  92 */       throw new IllegalArgumentException(MessageFormatUtil.format("(rx ry rot largearc sweep x y)+ parameters are expected for elliptical arcs. Got: {0}", new Object[] { Arrays.toString(inputCoordinates) }));
/*     */     }
/*  94 */     this.coordinates = new String[7];
/*  95 */     System.arraycopy(inputCoordinates, 0, this.coordinates, 0, 7);
/*     */     
/*  97 */     double[] initialPoint = { previous.getX(), previous.getY() };
/*     */     
/*  99 */     if (isRelative()) {
/* 100 */       String[] relativeEndPoint = { inputCoordinates[5], inputCoordinates[6] };
/* 101 */       String[] absoluteEndPoint = this.copier.makeCoordinatesAbsolute(relativeEndPoint, initialPoint);
/* 102 */       this.coordinates[5] = absoluteEndPoint[0];
/* 103 */       this.coordinates[6] = absoluteEndPoint[1];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas) {
/* 109 */     Point start = new Point(this.startPoint.x * 0.75D, this.startPoint.y * 0.75D);
/* 110 */     double rx = Math.abs(CssUtils.parseAbsoluteLength(this.coordinates[0]));
/* 111 */     double ry = Math.abs(CssUtils.parseAbsoluteLength(this.coordinates[1]));
/*     */ 
/*     */     
/* 114 */     double rotation = Double.parseDouble(this.coordinates[2]) % 360.0D;
/*     */     
/* 116 */     rotation = Math.toRadians(rotation);
/*     */ 
/*     */     
/* 119 */     boolean largeArc = !CssUtils.compareFloats(CssUtils.parseFloat(this.coordinates[3]).floatValue(), 0.0F);
/* 120 */     boolean sweep = !CssUtils.compareFloats(CssUtils.parseFloat(this.coordinates[4]).floatValue(), 0.0F);
/*     */     
/* 122 */     Point end = new Point(CssUtils.parseAbsoluteLength(this.coordinates[5]), CssUtils.parseAbsoluteLength(this.coordinates[6]));
/*     */     
/* 124 */     if (CssUtils.compareFloats(start.x, end.x) && CssUtils.compareFloats(start.y, end.y)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 130 */     if (CssUtils.compareFloats(rx, 0.0D) || CssUtils.compareFloats(ry, 0.0D)) {
/*     */ 
/*     */ 
/*     */       
/* 134 */       canvas.lineTo(end.x, end.y);
/*     */     } else {
/*     */       EllipseArc arc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (CssUtils.compareFloats(rotation, 0.0D)) {
/* 143 */         arc = EllipseArc.getEllipse(start, end, rx, ry, sweep, largeArc);
/*     */       } else {
/* 145 */         AffineTransform normalizer = AffineTransform.getRotateInstance(-rotation);
/* 146 */         normalizer.translate(-start.x, -start.y);
/* 147 */         Point newArcEnd = normalizer.transform(end, null);
/* 148 */         newArcEnd.translate(start.x, start.y);
/* 149 */         arc = EllipseArc.getEllipse(start, newArcEnd, rx, ry, sweep, largeArc);
/*     */       } 
/* 151 */       Point[][] points = makePoints(PdfCanvas.bezierArc(arc.ll.x, arc.ll.y, arc.ur.x, arc.ur.y, arc.startAng, arc.extent));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 160 */       if (sweep) {
/* 161 */         points = rotate(points, rotation, points[0][0]);
/* 162 */         for (int i = 0; i < points.length; i++) {
/* 163 */           drawCurve(canvas, points[i][1], points[i][2], points[i][3]);
/*     */         }
/*     */       } else {
/* 166 */         points = rotate(points, rotation, points[points.length - 1][3]);
/* 167 */         for (int i = points.length - 1; i >= 0; i--) {
/* 168 */           drawCurve(canvas, points[i][2], points[i][1], points[i][0]);
/*     */         }
/*     */       } 
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
/*     */   static Point[][] rotate(Point[][] list, double rotation, Point rotator) {
/* 183 */     if (!CssUtils.compareFloats(rotation, 0.0D)) {
/* 184 */       Point[][] result = new Point[list.length][];
/* 185 */       AffineTransform transRotTrans = AffineTransform.getRotateInstance(rotation, rotator.x, rotator.y);
/*     */       
/* 187 */       for (int i = 0; i < list.length; i++) {
/* 188 */         Point[] input = list[i];
/* 189 */         Point[] row = new Point[input.length];
/*     */         
/* 191 */         for (int j = 0; j < input.length; j++) {
/* 192 */           row[j] = transRotTrans.transform(input[j], null);
/*     */         }
/* 194 */         result[i] = row;
/*     */       } 
/* 196 */       return result;
/*     */     } 
/* 198 */     return list;
/*     */   }
/*     */   
/*     */   String[] getCoordinates() {
/* 202 */     return this.coordinates;
/*     */   }
/*     */   
/*     */   private static void drawCurve(PdfCanvas canvas, Point cp1, Point cp2, Point end) {
/* 206 */     canvas.curveTo(cp1.x, cp1.y, cp2.x, cp2.y, end.x, end.y);
/*     */   }
/*     */   
/*     */   private Point[][] makePoints(List<double[]> input) {
/* 210 */     Point[][] result = new Point[input.size()][];
/* 211 */     for (int i = 0; i < input.size(); i++) {
/* 212 */       result[i] = new Point[((double[])input.get(i)).length / 2];
/* 213 */       for (int j = 0; j < ((double[])input.get(i)).length; j += 2) {
/* 214 */         result[i][j / 2] = new Point(((double[])input.get(i))[j], ((double[])input.get(i))[j + 1]);
/*     */       }
/*     */     } 
/* 217 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class EllipseArc
/*     */   {
/*     */     final Point ll;
/*     */     
/*     */     final Point ur;
/*     */     
/*     */     final double startAng;
/*     */     
/*     */     final double extent;
/*     */ 
/*     */     
/*     */     EllipseArc(Point center, double a, double b, double startAng, double extent) {
/* 234 */       this.ll = new Point(center.x - a, center.y - b);
/* 235 */       this.ur = new Point(center.x + a, center.y + b);
/* 236 */       this.startAng = startAng;
/* 237 */       this.extent = extent;
/*     */     }
/*     */     
/*     */     static EllipseArc getEllipse(Point start, Point end, double a, double b, boolean sweep, boolean largeArc) {
/* 241 */       double r1 = (start.x - end.x) / -2.0D * a;
/* 242 */       double r2 = (start.y - end.y) / 2.0D * b;
/*     */       
/* 244 */       double factor = Math.sqrt(r1 * r1 + r2 * r2);
/* 245 */       if (factor > 1.0D)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 250 */         return getEllipse(start, end, a * factor, b * factor, sweep, largeArc);
/*     */       }
/*     */       
/* 253 */       double between1 = Math.atan(r1 / r2);
/* 254 */       double between2 = Math.asin(factor);
/*     */       
/* 256 */       EllipseArc result = calculatePossibleMiddle(start, end, a, b, between1 + between2, sweep, largeArc);
/* 257 */       if (result != null) {
/* 258 */         return result;
/*     */       }
/* 260 */       result = calculatePossibleMiddle(start, end, a, b, Math.PI + between1 - between2, sweep, largeArc);
/* 261 */       if (result != null) {
/* 262 */         return result;
/*     */       }
/* 264 */       result = calculatePossibleMiddle(start, end, a, b, Math.PI + between1 + between2, sweep, largeArc);
/* 265 */       if (result != null) {
/* 266 */         return result;
/*     */       }
/* 268 */       result = calculatePossibleMiddle(start, end, a, b, between1 - between2, sweep, largeArc);
/* 269 */       if (result != null) {
/* 270 */         return result;
/*     */       }
/* 272 */       throw new SvgProcessingException("Could not determine the middle point of the ellipse traced by this elliptical arc");
/*     */     }
/*     */ 
/*     */     
/*     */     static EllipseArc calculatePossibleMiddle(Point start, Point end, double a, double b, double startToCenterAngle, boolean sweep, boolean largeArc) {
/* 277 */       double x0 = start.x - a * Math.cos(startToCenterAngle);
/* 278 */       double y0 = start.y - b * Math.sin(startToCenterAngle);
/* 279 */       Point center = new Point(x0, y0);
/*     */       
/* 281 */       double check = Math.pow((end.x - center.x) / a, 2.0D) + Math.pow((end.y - center.y) / b, 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 287 */       if (CssUtils.compareFloats(check, 1.0D)) {
/*     */         
/* 289 */         double theta1 = calculateAngle(start, center, a, b);
/* 290 */         double theta2 = calculateAngle(end, center, a, b);
/* 291 */         double startAngl = 0.0D;
/* 292 */         double extent = 0.0D;
/*     */ 
/*     */         
/* 295 */         long deltaTheta = Math.abs(Math.round(theta2 - theta1));
/*     */         
/* 297 */         if (largeArc) {
/* 298 */           if (sweep) {
/*     */             
/* 300 */             if (theta2 > theta1 && deltaTheta >= 180L) {
/* 301 */               startAngl = theta1;
/* 302 */               extent = theta2 - theta1;
/*     */             } 
/* 304 */             if (theta1 > theta2 && deltaTheta <= 180L) {
/* 305 */               startAngl = theta1;
/* 306 */               extent = 360.0D - theta1 + theta2;
/*     */             } 
/*     */           } else {
/* 309 */             if (theta2 > theta1 && deltaTheta <= 180L) {
/* 310 */               startAngl = theta2;
/* 311 */               extent = 360.0D - theta2 + theta1;
/*     */             } 
/* 313 */             if (theta1 > theta2 && deltaTheta >= 180L) {
/* 314 */               startAngl = theta2;
/* 315 */               extent = theta1 - theta2;
/*     */             }
/*     */           
/*     */           } 
/* 319 */         } else if (sweep) {
/* 320 */           if (theta2 > theta1 && deltaTheta <= 180L) {
/* 321 */             startAngl = theta1;
/* 322 */             extent = theta2 - theta1;
/*     */           } 
/* 324 */           if (theta1 > theta2 && deltaTheta >= 180L) {
/* 325 */             startAngl = theta1;
/* 326 */             extent = 360.0D - theta1 + theta2;
/*     */           } 
/*     */         } else {
/*     */           
/* 330 */           if (theta2 > theta1 && deltaTheta >= 180L) {
/* 331 */             startAngl = theta2;
/* 332 */             extent = 360.0D - theta2 + theta1;
/*     */           } 
/* 334 */           if (theta1 > theta2 && deltaTheta <= 180L) {
/* 335 */             startAngl = theta2;
/* 336 */             extent = theta1 - theta2;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 341 */         if (startAngl >= 0.0D && extent > 0.0D) {
/* 342 */           return new EllipseArc(center, a, b, startAngl, extent);
/*     */         }
/*     */       } 
/* 345 */       return null;
/*     */     }
/*     */     
/*     */     static double calculateAngle(Point pt, Point center, double a, double b) {
/* 349 */       double result = Math.pow((pt.x - center.x) / a, 2.0D) + Math.pow((pt.y - center.y) / b, 2.0D);
/*     */       
/* 351 */       double cos = (pt.x - center.x) / a;
/* 352 */       double sin = (pt.y - center.y) / b;
/*     */       
/* 354 */       cos = Math.max(Math.min(cos, 1.0D), -1.0D);
/*     */       
/* 356 */       if ((cos >= 0.0D && sin >= 0.0D) || (cos < 0.0D && sin >= 0.0D)) {
/* 357 */         result = toDegrees(Math.acos(cos));
/*     */       }
/* 359 */       if ((cos >= 0.0D && sin < 0.0D) || (cos < 0.0D && sin < 0.0D)) {
/* 360 */         result = 360.0D - toDegrees(Math.acos(cos));
/*     */       }
/* 362 */       return result;
/*     */     }
/*     */     
/*     */     static double toDegrees(double radians) {
/* 366 */       return radians * 180.0D / Math.PI;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getPathShapeRectangle(Point lastPoint) {
/* 373 */     double[] points = getEllipticalArcMinMaxPoints(lastPoint.getX(), lastPoint.getY(), 
/* 374 */         getCoordinate(0), getCoordinate(1), getCoordinate(2), 
/* 375 */         (getCoordinate(3) != 0.0D), (getCoordinate(4) != 0.0D), 
/* 376 */         getCoordinate(5), getCoordinate(6));
/* 377 */     return new Rectangle((float)CssUtils.convertPxToPts(points[0]), 
/* 378 */         (float)CssUtils.convertPxToPts(points[1]), 
/* 379 */         (float)CssUtils.convertPxToPts(points[2] - points[0]), 
/* 380 */         (float)CssUtils.convertPxToPts(points[3] - points[1]));
/*     */   }
/*     */ 
/*     */   
/*     */   private double getCoordinate(int index) {
/* 385 */     return CssUtils.parseDouble(this.coordinates[index]).doubleValue();
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
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] getEllipticalArcMinMaxPoints(double x1, double y1, double rx, double ry, double phi, boolean largeArc, boolean sweep, double x2, double y2) {
/* 421 */     phi = Math.toRadians(phi);
/* 422 */     rx = Math.abs(rx);
/* 423 */     ry = Math.abs(ry);
/*     */     
/* 425 */     if (rx == 0.0D || ry == 0.0D) {
/* 426 */       return new double[] { Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2) };
/*     */     }
/*     */     
/* 429 */     double[] centerCoordinatesAndRxRy = getEllipseCenterCoordinates(x1, y1, rx, ry, phi, largeArc, sweep, x2, y2);
/*     */     
/* 431 */     if (centerCoordinatesAndRxRy == null) {
/* 432 */       return new double[] { Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2) };
/*     */     }
/* 434 */     double cx = centerCoordinatesAndRxRy[0];
/* 435 */     double cy = centerCoordinatesAndRxRy[1];
/*     */     
/* 437 */     rx = centerCoordinatesAndRxRy[2];
/* 438 */     ry = centerCoordinatesAndRxRy[3];
/*     */     
/* 440 */     double[][] extremeCoordinatesAndThetas = getExtremeCoordinatesAndAngles(rx, ry, phi, cx, cy);
/* 441 */     double[] extremeCoordinates = extremeCoordinatesAndThetas[0];
/* 442 */     double[] angles = extremeCoordinatesAndThetas[1];
/* 443 */     double xMin = extremeCoordinates[0];
/* 444 */     double yMin = extremeCoordinates[1];
/* 445 */     double xMax = extremeCoordinates[2];
/* 446 */     double yMax = extremeCoordinates[3];
/* 447 */     double xMinAngle = angles[0];
/* 448 */     double yMinAngle = angles[1];
/* 449 */     double xMaxAngle = angles[2];
/* 450 */     double yMaxAngle = angles[3];
/*     */ 
/*     */     
/* 453 */     double angle1 = getAngleBetweenVectors(x1 - cx, y1 - cy);
/* 454 */     double angle2 = getAngleBetweenVectors(x2 - cx, y2 - cy);
/*     */ 
/*     */ 
/*     */     
/* 458 */     if (!sweep) {
/* 459 */       double temp = angle1;
/* 460 */       angle1 = angle2;
/* 461 */       angle2 = temp;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     boolean otherArc = (angle1 > angle2);
/* 469 */     if (otherArc) {
/* 470 */       double temp = angle1;
/* 471 */       angle1 = angle2;
/* 472 */       angle2 = temp;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 477 */     if (!isPointOnTheArc(xMinAngle, angle1, angle2, otherArc)) {
/* 478 */       xMin = Math.min(x1, x2);
/*     */     }
/* 480 */     if (!isPointOnTheArc(xMaxAngle, angle1, angle2, otherArc)) {
/* 481 */       xMax = Math.max(x1, x2);
/*     */     }
/* 483 */     if (!isPointOnTheArc(yMinAngle, angle1, angle2, otherArc)) {
/* 484 */       yMin = Math.min(y1, y2);
/*     */     }
/* 486 */     if (!isPointOnTheArc(yMaxAngle, angle1, angle2, otherArc)) {
/* 487 */       yMax = Math.max(y1, y2);
/*     */     }
/* 489 */     return new double[] { xMin, yMin, xMax, yMax };
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
/*     */   private double[] getEllipseCenterCoordinates(double x1, double y1, double rx, double ry, double phi, boolean largeArc, boolean sweep, double x2, double y2) {
/* 510 */     double x1Prime = Math.cos(phi) * (x1 - x2) / 2.0D + Math.sin(phi) * (y1 - y2) / 2.0D;
/* 511 */     double y1Prime = -Math.sin(phi) * (x1 - x2) / 2.0D + Math.cos(phi) * (y1 - y2) / 2.0D;
/* 512 */     double radicant = rx * rx * ry * ry - rx * rx * y1Prime * y1Prime - ry * ry * x1Prime * x1Prime;
/* 513 */     radicant /= rx * rx * y1Prime * y1Prime + ry * ry * x1Prime * x1Prime;
/*     */     
/* 515 */     double cxPrime = 0.0D;
/* 516 */     double cyPrime = 0.0D;
/* 517 */     if (radicant < 0.0D) {
/* 518 */       double ratio = rx / ry;
/* 519 */       radicant = y1Prime * y1Prime + x1Prime * x1Prime / ratio * ratio;
/* 520 */       if (radicant < 0.0D) {
/* 521 */         return null;
/*     */       }
/* 523 */       ry = Math.sqrt(radicant);
/* 524 */       rx = ratio * ry;
/*     */     } else {
/* 526 */       double factor = ((largeArc == sweep) ? -1.0D : 1.0D) * Math.sqrt(radicant);
/* 527 */       cxPrime = factor * rx * y1Prime / ry;
/* 528 */       cyPrime = -factor * ry * x1Prime / rx;
/*     */     } 
/*     */     
/* 531 */     double cx = cxPrime * Math.cos(phi) - cyPrime * Math.sin(phi) + (x1 + x2) / 2.0D;
/* 532 */     double cy = cxPrime * Math.sin(phi) + cyPrime * Math.cos(phi) + (y1 + y2) / 2.0D;
/*     */     
/* 534 */     return new double[] { cx, cy, rx, ry };
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
/*     */   private double[][] getExtremeCoordinatesAndAngles(double rx, double ry, double phi, double cx, double cy) {
/*     */     double xMin, yMin, xMax, yMax, xMinAngle, yMinAngle, xMaxAngle, yMaxAngle;
/* 551 */     if (anglesAreEquals(phi, 0.0D) || anglesAreEquals(phi, Math.PI)) {
/* 552 */       xMin = cx - rx;
/* 553 */       xMinAngle = getAngleBetweenVectors(-rx, 0.0D);
/* 554 */       xMax = cx + rx;
/* 555 */       xMaxAngle = getAngleBetweenVectors(rx, 0.0D);
/* 556 */       yMin = cy - ry;
/* 557 */       yMinAngle = getAngleBetweenVectors(0.0D, -ry);
/* 558 */       yMax = cy + ry;
/* 559 */       yMaxAngle = getAngleBetweenVectors(0.0D, ry);
/* 560 */     } else if (anglesAreEquals(phi, 1.5707963267948966D) || anglesAreEquals(phi, 4.71238898038469D)) {
/* 561 */       xMin = cx - ry;
/* 562 */       xMinAngle = getAngleBetweenVectors(-ry, 0.0D);
/* 563 */       xMax = cx + ry;
/* 564 */       xMaxAngle = getAngleBetweenVectors(ry, 0.0D);
/* 565 */       yMin = cy - rx;
/* 566 */       yMinAngle = getAngleBetweenVectors(0.0D, -rx);
/* 567 */       yMax = cy + rx;
/* 568 */       yMaxAngle = getAngleBetweenVectors(0.0D, rx);
/*     */     } else {
/*     */       
/* 571 */       double txMin = -Math.atan(ry * Math.tan(phi) / rx);
/* 572 */       double txMax = Math.PI - Math.atan(ry * Math.tan(phi) / rx);
/*     */       
/* 574 */       xMin = cx + rx * Math.cos(txMin) * Math.cos(phi) - ry * Math.sin(txMin) * Math.sin(phi);
/* 575 */       xMax = cx + rx * Math.cos(txMax) * Math.cos(phi) - ry * Math.sin(txMax) * Math.sin(phi);
/* 576 */       if (xMin > xMax) {
/* 577 */         double temp = xMin;
/* 578 */         xMin = xMax;
/* 579 */         xMax = temp;
/* 580 */         temp = txMin;
/* 581 */         txMin = txMax;
/* 582 */         txMax = temp;
/*     */       } 
/*     */       
/* 585 */       double tempY = cy + rx * Math.cos(txMin) * Math.sin(phi) + ry * Math.sin(txMin) * Math.cos(phi);
/* 586 */       xMinAngle = getAngleBetweenVectors(xMin - cx, tempY - cy);
/* 587 */       tempY = cy + rx * Math.cos(txMax) * Math.sin(phi) + ry * Math.sin(txMax) * Math.cos(phi);
/* 588 */       xMaxAngle = getAngleBetweenVectors(xMax - cx, tempY - cy);
/*     */ 
/*     */       
/* 591 */       double tyMin = Math.atan(ry / Math.tan(phi) * rx);
/* 592 */       double tyMax = Math.atan(ry / Math.tan(phi) * rx) + Math.PI;
/*     */       
/* 594 */       yMin = cy + rx * Math.cos(tyMin) * Math.sin(phi) + ry * Math.sin(tyMin) * Math.cos(phi);
/* 595 */       yMax = cy + rx * Math.cos(tyMax) * Math.sin(phi) + ry * Math.sin(tyMax) * Math.cos(phi);
/* 596 */       if (yMin > yMax) {
/* 597 */         double temp = yMin;
/* 598 */         yMin = yMax;
/* 599 */         yMax = temp;
/* 600 */         temp = tyMin;
/* 601 */         tyMin = tyMax;
/* 602 */         tyMax = temp;
/*     */       } 
/*     */       
/* 605 */       double tmpX = cx + rx * Math.cos(tyMin) * Math.cos(phi) - ry * Math.sin(tyMin) * Math.sin(phi);
/* 606 */       yMinAngle = getAngleBetweenVectors(tmpX - cx, yMin - cy);
/* 607 */       tmpX = cx + rx * Math.cos(tyMax) * Math.cos(phi) - ry * Math.sin(tyMax) * Math.sin(phi);
/* 608 */       yMaxAngle = getAngleBetweenVectors(tmpX - cx, yMax - cy);
/*     */     } 
/*     */ 
/*     */     
/* 612 */     double[] coordinates = { xMin, yMin, xMax, yMax };
/*     */     
/* 614 */     double[] angles = { xMinAngle, yMinAngle, xMaxAngle, yMaxAngle };
/* 615 */     return new double[][] { coordinates, angles };
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
/*     */   private boolean isPointOnTheArc(double pointAngle, double angle1, double angle2, boolean otherArc) {
/* 629 */     boolean isThetaBetweenAngles = (angle1 <= pointAngle && angle2 >= pointAngle);
/*     */     
/* 631 */     return (otherArc != isThetaBetweenAngles);
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
/*     */   private double getAngleBetweenVectors(double bx, double by) {
/* 645 */     return (6.283185307179586D + ((by > 0.0D) ? 1.0D : -1.0D) * Math.acos(bx / Math.sqrt(bx * bx + by * by))) % 6.283185307179586D;
/*     */   }
/*     */   
/*     */   private boolean anglesAreEquals(double angle1, double angle2) {
/* 649 */     return (Math.abs(angle1 - angle2) < 1.0E-5D);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/EllipticalCurveTo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */