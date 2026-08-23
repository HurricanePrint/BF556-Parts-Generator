/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineSegment
/*     */ {
/*     */   private final Vector startPoint;
/*     */   private final Vector endPoint;
/*     */   
/*     */   public LineSegment(Vector startPoint, Vector endPoint) {
/*  62 */     this.startPoint = startPoint;
/*  63 */     this.endPoint = endPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getStartPoint() {
/*  70 */     return this.startPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getEndPoint() {
/*  77 */     return this.endPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLength() {
/*  84 */     return this.endPoint.subtract(this.startPoint).length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBoundingRectangle() {
/*  95 */     float x1 = getStartPoint().get(0);
/*  96 */     float y1 = getStartPoint().get(1);
/*  97 */     float x2 = getEndPoint().get(0);
/*  98 */     float y2 = getEndPoint().get(1);
/*  99 */     return new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineSegment transformBy(Matrix m) {
/* 108 */     Vector newStart = this.startPoint.cross(m);
/* 109 */     Vector newEnd = this.endPoint.cross(m);
/* 110 */     return new LineSegment(newStart, newEnd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsSegment(LineSegment other) {
/* 119 */     return (other != null && containsPoint(other.startPoint) && containsPoint(other.endPoint));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPoint(Vector point) {
/* 129 */     if (point == null) {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     Vector diff1 = point.subtract(this.startPoint);
/* 134 */     if (diff1.get(0) < 0.0F || diff1.get(1) < 0.0F || diff1.get(2) < 0.0F) {
/* 135 */       return false;
/*     */     }
/*     */     
/* 138 */     Vector diff2 = this.endPoint.subtract(point);
/* 139 */     if (diff2.get(0) < 0.0F || diff2.get(1) < 0.0F || diff2.get(2) < 0.0F) {
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/LineSegment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */