/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public class BezierCurve
/*     */   implements IShape
/*     */ {
/*     */   private static final long serialVersionUID = -2158496565016776969L;
/*  59 */   public static double curveCollinearityEpsilon = 1.0E-30D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static double distanceToleranceSquare = 0.025D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static double distanceToleranceManhattan = 0.4D;
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<Point> controlPoints;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BezierCurve(List<Point> controlPoints) {
/*  91 */     this.controlPoints = new ArrayList<>(controlPoints);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Point> getBasePoints() {
/*  98 */     return this.controlPoints;
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
/*     */   public List<Point> getPiecewiseLinearApproximation() {
/* 110 */     List<Point> points = new ArrayList<>();
/* 111 */     points.add(this.controlPoints.get(0));
/*     */     
/* 113 */     recursiveApproximation(((Point)this.controlPoints.get(0)).getX(), ((Point)this.controlPoints.get(0)).getY(), ((Point)this.controlPoints
/* 114 */         .get(1)).getX(), ((Point)this.controlPoints.get(1)).getY(), ((Point)this.controlPoints
/* 115 */         .get(2)).getX(), ((Point)this.controlPoints.get(2)).getY(), ((Point)this.controlPoints
/* 116 */         .get(3)).getX(), ((Point)this.controlPoints.get(3)).getY(), points);
/*     */     
/* 118 */     points.add(this.controlPoints.get(this.controlPoints.size() - 1));
/* 119 */     return points;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void recursiveApproximation(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, List<Point> points) {
/* 126 */     double x12 = (x1 + x2) / 2.0D;
/* 127 */     double y12 = (y1 + y2) / 2.0D;
/* 128 */     double x23 = (x2 + x3) / 2.0D;
/* 129 */     double y23 = (y2 + y3) / 2.0D;
/* 130 */     double x34 = (x3 + x4) / 2.0D;
/* 131 */     double y34 = (y3 + y4) / 2.0D;
/* 132 */     double x123 = (x12 + x23) / 2.0D;
/* 133 */     double y123 = (y12 + y23) / 2.0D;
/* 134 */     double x234 = (x23 + x34) / 2.0D;
/* 135 */     double y234 = (y23 + y34) / 2.0D;
/* 136 */     double x1234 = (x123 + x234) / 2.0D;
/* 137 */     double y1234 = (y123 + y234) / 2.0D;
/*     */     
/* 139 */     double dx = x4 - x1;
/* 140 */     double dy = y4 - y1;
/*     */ 
/*     */ 
/*     */     
/* 144 */     double d2 = Math.abs((x2 - x4) * dy - (y2 - y4) * dx);
/*     */ 
/*     */     
/* 147 */     double d3 = Math.abs((x3 - x4) * dy - (y3 - y4) * dx);
/*     */ 
/*     */ 
/*     */     
/* 151 */     if (d2 > curveCollinearityEpsilon || d3 > curveCollinearityEpsilon) {
/*     */ 
/*     */       
/* 154 */       if ((d2 + d3) * (d2 + d3) <= distanceToleranceSquare * (dx * dx + dy * dy)) {
/* 155 */         points.add(new Point(x1234, y1234));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/* 160 */     } else if (Math.abs(x1 + x3 - x2 - x2) + Math.abs(y1 + y3 - y2 - y2) + 
/* 161 */       Math.abs(x2 + x4 - x3 - x3) + Math.abs(y2 + y4 - y3 - y3) <= distanceToleranceManhattan) {
/* 162 */       points.add(new Point(x1234, y1234));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 167 */     recursiveApproximation(x1, y1, x12, y12, x123, y123, x1234, y1234, points);
/* 168 */     recursiveApproximation(x1234, y1234, x234, y234, x34, y34, x4, y4, points);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/BezierCurve.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */