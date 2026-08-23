/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Subpath
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3464451279777771490L;
/*     */   private Point startPoint;
/*  60 */   private List<IShape> segments = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Subpath() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Subpath(Subpath subpath) {
/*  75 */     this.startPoint = subpath.startPoint;
/*  76 */     this.segments.addAll(subpath.getSegments());
/*  77 */     this.closed = subpath.closed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Subpath(Point startPoint) {
/*  86 */     this((float)startPoint.getX(), (float)startPoint.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Subpath(float startPointX, float startPointY) {
/*  96 */     this.startPoint = new Point(startPointX, startPointY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartPoint(Point startPoint) {
/* 104 */     setStartPoint((float)startPoint.getX(), (float)startPoint.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartPoint(float x, float y) {
/* 113 */     this.startPoint = new Point(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getStartPoint() {
/* 120 */     return this.startPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getLastPoint() {
/* 127 */     Point lastPoint = this.startPoint;
/*     */     
/* 129 */     if (this.segments.size() > 0 && !this.closed) {
/* 130 */       IShape shape = this.segments.get(this.segments.size() - 1);
/* 131 */       lastPoint = shape.getBasePoints().get(shape.getBasePoints().size() - 1);
/*     */     } 
/*     */     
/* 134 */     return lastPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSegment(IShape segment) {
/* 143 */     if (this.closed) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     if (isSinglePointOpen()) {
/* 148 */       this.startPoint = segment.getBasePoints().get(0);
/*     */     }
/*     */     
/* 151 */     this.segments.add(segment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IShape> getSegments() {
/* 159 */     return this.segments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 167 */     return (this.startPoint == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSinglePointOpen() {
/* 175 */     return (this.segments.size() == 0 && !this.closed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSinglePointClosed() {
/* 183 */     return (this.segments.size() == 0 && this.closed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 194 */     return this.closed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClosed(boolean closed) {
/* 203 */     this.closed = closed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDegenerate() {
/* 214 */     if (this.segments.size() > 0 && this.closed) {
/* 215 */       return false;
/*     */     }
/*     */     
/* 218 */     for (IShape segment : this.segments) {
/* 219 */       Set<Point> points = new HashSet<>(segment.getBasePoints());
/*     */ 
/*     */       
/* 222 */       if (points.size() != 1) {
/* 223 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 228 */     return (this.segments.size() > 0 || this.closed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Point> getPiecewiseLinearApproximation() {
/* 236 */     List<Point> result = new ArrayList<>();
/*     */     
/* 238 */     if (this.segments.size() == 0) {
/* 239 */       return result;
/*     */     }
/*     */     
/* 242 */     if (this.segments.get(0) instanceof BezierCurve) {
/* 243 */       result.addAll(((BezierCurve)this.segments.get(0)).getPiecewiseLinearApproximation());
/*     */     } else {
/* 245 */       result.addAll(((IShape)this.segments.get(0)).getBasePoints());
/*     */     } 
/*     */     
/* 248 */     for (int i = 1; i < this.segments.size(); i++) {
/*     */       List<Point> segApprox;
/*     */       
/* 251 */       if (this.segments.get(i) instanceof BezierCurve) {
/* 252 */         segApprox = ((BezierCurve)this.segments.get(i)).getPiecewiseLinearApproximation();
/* 253 */         segApprox = segApprox.subList(1, segApprox.size());
/*     */       } else {
/* 255 */         segApprox = ((IShape)this.segments.get(i)).getBasePoints();
/* 256 */         segApprox = segApprox.subList(1, segApprox.size());
/*     */       } 
/*     */       
/* 259 */       result.addAll(segApprox);
/*     */     } 
/*     */     
/* 262 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/Subpath.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */