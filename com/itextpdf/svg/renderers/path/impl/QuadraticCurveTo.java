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
/*     */ public class QuadraticCurveTo
/*     */   extends AbstractPathShape
/*     */   implements IControlPointCurve
/*     */ {
/*     */   static final int ARGUMENT_SIZE = 4;
/*     */   
/*     */   public QuadraticCurveTo() {
/*  64 */     this(false);
/*     */   }
/*     */   
/*     */   public QuadraticCurveTo(boolean relative) {
/*  68 */     this(relative, new DefaultOperatorConverter());
/*     */   }
/*     */   
/*     */   public QuadraticCurveTo(boolean relative, IOperatorConverter copier) {
/*  72 */     super(relative, copier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(PdfCanvas canvas) {
/*  80 */     float x1 = CssUtils.parseAbsoluteLength(this.coordinates[0]);
/*  81 */     float y1 = CssUtils.parseAbsoluteLength(this.coordinates[1]);
/*  82 */     float x = CssUtils.parseAbsoluteLength(this.coordinates[2]);
/*  83 */     float y = CssUtils.parseAbsoluteLength(this.coordinates[3]);
/*  84 */     canvas.curveTo(x1, y1, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCoordinates(String[] inputCoordinates, Point startPoint) {
/*  90 */     if (inputCoordinates.length < 4) {
/*  91 */       throw new IllegalArgumentException(MessageFormatUtil.format("(x1 y1 x y)+ parameters are expected for quadratic curves. Got: {0}", new Object[] { Arrays.toString(this.coordinates) }));
/*     */     }
/*  93 */     this.coordinates = new String[4];
/*  94 */     System.arraycopy(inputCoordinates, 0, this.coordinates, 0, 4);
/*  95 */     double[] initialPoint = { startPoint.getX(), startPoint.getY() };
/*  96 */     if (isRelative()) {
/*  97 */       this.coordinates = this.copier.makeCoordinatesAbsolute(this.coordinates, initialPoint);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getLastControlPoint() {
/* 103 */     return createPoint(this.coordinates[0], this.coordinates[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle getPathShapeRectangle(Point lastPoint) {
/* 108 */     Point controlPoint = getLastControlPoint();
/* 109 */     Point endingPoint = getEndingPoint();
/* 110 */     double[] points = getBezierMinMaxPoints(lastPoint.getX(), lastPoint.getY(), controlPoint
/* 111 */         .getX(), controlPoint.getY(), endingPoint
/* 112 */         .getX(), endingPoint.getY());
/* 113 */     return new Rectangle((float)CssUtils.convertPxToPts(points[0]), 
/* 114 */         (float)CssUtils.convertPxToPts(points[1]), 
/* 115 */         (float)CssUtils.convertPxToPts(points[2] - points[0]), 
/* 116 */         (float)CssUtils.convertPxToPts(points[3] - points[1]));
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
/*     */   private static double[] getBezierMinMaxPoints(double x0, double y0, double x1, double y1, double x2, double y2) {
/* 132 */     double xMin = Math.min(x0, x2);
/* 133 */     double yMin = Math.min(y0, y2);
/* 134 */     double xMax = Math.max(x0, x2);
/* 135 */     double yMax = Math.max(y0, y2);
/* 136 */     double[] extremeTValues = getExtremeTValues(x0, y0, x1, y1, x2, y2);
/* 137 */     for (double t : extremeTValues) {
/* 138 */       double xValue = calculateExtremeCoordinate(t, x0, x1, x2);
/* 139 */       double yValue = calculateExtremeCoordinate(t, y0, y1, y2);
/* 140 */       xMin = Math.min(xValue, xMin);
/* 141 */       yMin = Math.min(yValue, yMin);
/* 142 */       xMax = Math.max(xValue, xMax);
/* 143 */       yMax = Math.max(yValue, yMax);
/*     */     } 
/* 145 */     return new double[] { xMin, yMin, xMax, yMax };
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
/*     */   private static double[] getExtremeTValues(double x0, double y0, double x1, double y1, double x2, double y2) {
/* 162 */     List<Double> tValuesList = new ArrayList<>();
/* 163 */     addTValueToList(getTValue(x0, x1, x2), tValuesList);
/* 164 */     addTValueToList(getTValue(y0, y1, y2), tValuesList);
/* 165 */     double[] tValuesArray = new double[tValuesList.size()];
/* 166 */     for (int i = 0; i < tValuesList.size(); i++) {
/* 167 */       tValuesArray[i] = ((Double)tValuesList.get(i)).doubleValue();
/*     */     }
/* 169 */     return tValuesArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addTValueToList(double t, List<Double> tValuesList) {
/* 178 */     if (0.0D <= t && t <= 1.0D) {
/* 179 */       tValuesList.add(Double.valueOf(t));
/*     */     }
/*     */   }
/*     */   
/*     */   private static double getTValue(double p0, double p1, double p2) {
/* 184 */     double b = 2.0D * p1 - 2.0D * p0;
/* 185 */     double a = p0 - 2.0D * p1 + p2;
/* 186 */     return -b / 2.0D * a;
/*     */   }
/*     */   
/*     */   private static double calculateExtremeCoordinate(double t, double p0, double p1, double p2) {
/* 190 */     double minusT = 1.0D - t;
/* 191 */     return minusT * minusT * p0 + 2.0D * minusT * t * p1 + t * t * p2;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/QuadraticCurveTo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */