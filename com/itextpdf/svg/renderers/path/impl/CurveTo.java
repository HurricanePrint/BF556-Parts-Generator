/*     */ package com.itextpdf.svg.renderers.path.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
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
/*     */ public class CurveTo
/*     */   extends AbstractPathShape
/*     */   implements IControlPointCurve
/*     */ {
/*     */   static final int ARGUMENT_SIZE = 6;
/*  63 */   private static double ZERO_EPSILON = 1.0E-12D;
/*     */   
/*     */   public CurveTo() {
/*  66 */     this(false);
/*     */   }
/*     */   
/*     */   public CurveTo(boolean relative) {
/*  70 */     this(relative, new DefaultOperatorConverter());
/*     */   }
/*     */   
/*     */   public CurveTo(boolean relative, IOperatorConverter copier) {
/*  74 */     super(relative, copier);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas) {
/*  79 */     float x1 = CssUtils.parseAbsoluteLength(this.coordinates[0]);
/*  80 */     float y1 = CssUtils.parseAbsoluteLength(this.coordinates[1]);
/*  81 */     float x2 = CssUtils.parseAbsoluteLength(this.coordinates[2]);
/*  82 */     float y2 = CssUtils.parseAbsoluteLength(this.coordinates[3]);
/*  83 */     float x = CssUtils.parseAbsoluteLength(this.coordinates[4]);
/*  84 */     float y = CssUtils.parseAbsoluteLength(this.coordinates[5]);
/*  85 */     canvas.curveTo(x1, y1, x2, y2, x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCoordinates(String[] inputCoordinates, Point startPoint) {
/*  90 */     if (inputCoordinates.length < 6) {
/*  91 */       throw new IllegalArgumentException(MessageFormatUtil.format("(x1 y1 x2 y2 x y)+ parameters are expected for curves. Got: {0}", new Object[] { Arrays.toString(inputCoordinates) }));
/*     */     }
/*  93 */     this.coordinates = new String[6];
/*  94 */     System.arraycopy(inputCoordinates, 0, this.coordinates, 0, 6);
/*  95 */     double[] initialPoint = { startPoint.getX(), startPoint.getY() };
/*  96 */     if (isRelative()) {
/*  97 */       this.coordinates = this.copier.makeCoordinatesAbsolute(this.coordinates, initialPoint);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getLastControlPoint() {
/* 103 */     return createPoint(this.coordinates[2], this.coordinates[3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle getPathShapeRectangle(Point lastPoint) {
/* 108 */     Point firstControlPoint = getFirstControlPoint();
/* 109 */     Point lastControlPoint = getLastControlPoint();
/* 110 */     Point endingPoint = getEndingPoint();
/* 111 */     double[] points = getBezierMinMaxPoints(lastPoint.getX(), lastPoint.getY(), firstControlPoint
/* 112 */         .getX(), firstControlPoint.getY(), lastControlPoint
/* 113 */         .getX(), lastControlPoint.getY(), endingPoint
/* 114 */         .getX(), endingPoint.getY());
/* 115 */     return new Rectangle((float)CssUtils.convertPxToPts(points[0]), 
/* 116 */         (float)CssUtils.convertPxToPts(points[1]), 
/* 117 */         (float)CssUtils.convertPxToPts(points[2] - points[0]), 
/* 118 */         (float)CssUtils.convertPxToPts(points[3] - points[1]));
/*     */   }
/*     */   
/*     */   private Point getFirstControlPoint() {
/* 122 */     return createPoint(this.coordinates[0], this.coordinates[1]);
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
/*     */   private static double[] getBezierMinMaxPoints(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
/* 149 */     double xMin = Math.min(x0, x3);
/* 150 */     double yMin = Math.min(y0, y3);
/* 151 */     double xMax = Math.max(x0, x3);
/* 152 */     double yMax = Math.max(y0, y3);
/*     */     
/* 154 */     double[] extremeTValues = getTValuesInExtremePoints(x0, y0, x1, y1, x2, y2, x3, y3);
/* 155 */     for (double t : extremeTValues) {
/* 156 */       double xValue = calculateExtremeCoordinate(t, x0, x1, x2, x3);
/* 157 */       double yValue = calculateExtremeCoordinate(t, y0, y1, y2, y3);
/*     */       
/* 159 */       xMin = Math.min(xValue, xMin);
/* 160 */       yMin = Math.min(yValue, yMin);
/* 161 */       xMax = Math.max(xValue, xMax);
/* 162 */       yMax = Math.max(yValue, yMax);
/*     */     } 
/* 164 */     return new double[] { xMin, yMin, xMax, yMax };
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
/*     */   private static double[] getTValuesInExtremePoints(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
/* 183 */     List<Double> tValuesList = new ArrayList<>(calculateTValues(x0, x1, x2, x3));
/* 184 */     tValuesList.addAll(calculateTValues(y0, y1, y2, y3));
/* 185 */     double[] tValuesArray = new double[tValuesList.size()];
/* 186 */     for (int i = 0; i < tValuesList.size(); i++) {
/* 187 */       tValuesArray[i] = ((Double)tValuesList.get(i)).doubleValue();
/*     */     }
/* 189 */     return tValuesArray;
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
/*     */   private static List<Double> calculateTValues(double p0, double p1, double p2, double p3) {
/* 202 */     List<Double> tValuesList = new ArrayList<>();
/* 203 */     double a = (-p0 + 3.0D * p1 - 3.0D * p2 + p3) * 3.0D;
/* 204 */     double b = (3.0D * p0 - 6.0D * p1 + 3.0D * p2) * 2.0D;
/* 205 */     double c = 3.0D * p1 - 3.0D * p0;
/* 206 */     if (Math.abs(a) < ZERO_EPSILON) {
/* 207 */       if (Math.abs(b) >= ZERO_EPSILON)
/*     */       {
/* 209 */         addTValueToList(-c / b, tValuesList);
/*     */       }
/*     */     } else {
/* 212 */       double discriminant = b * b - 4.0D * c * a;
/*     */ 
/*     */       
/* 215 */       if (discriminant <= 0.0D && Math.abs(discriminant) < ZERO_EPSILON) {
/*     */         
/* 217 */         addTValueToList(-b / 2.0D * a, tValuesList);
/*     */       } else {
/* 219 */         double discriminantSqrt = Math.sqrt(discriminant);
/* 220 */         addTValueToList((-b + discriminantSqrt) / 2.0D * a, tValuesList);
/* 221 */         addTValueToList((-b - discriminantSqrt) / 2.0D * a, tValuesList);
/*     */       } 
/*     */     } 
/* 224 */     return tValuesList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addTValueToList(double t, List<Double> tValuesList) {
/* 233 */     if (0.0D <= t && t <= 1.0D) {
/* 234 */       tValuesList.add(Double.valueOf(t));
/*     */     }
/*     */   }
/*     */   
/*     */   private static double calculateExtremeCoordinate(double t, double p0, double p1, double p2, double p3) {
/* 239 */     double minusT = 1.0D - t;
/*     */     
/* 241 */     return minusT * minusT * minusT * p0 + 3.0D * minusT * minusT * t * p1 + 3.0D * minusT * t * t * p2 + t * t * t * p3;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/CurveTo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */