/*    */ package com.itextpdf.kernel.geom;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Line
/*    */   implements IShape
/*    */ {
/*    */   private static final long serialVersionUID = 4796508543986646437L;
/*    */   private final Point p1;
/*    */   private final Point p2;
/*    */   
/*    */   public Line() {
/* 62 */     this(0.0F, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Line(float x1, float y1, float x2, float y2) {
/* 73 */     this.p1 = new Point(x1, y1);
/* 74 */     this.p2 = new Point(x2, y2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Line(Point p1, Point p2) {
/* 83 */     this((float)p1.getX(), (float)p1.getY(), (float)p2.getX(), (float)p2.getY());
/*    */   }
/*    */   
/*    */   public List<Point> getBasePoints() {
/* 87 */     List<Point> basePoints = new ArrayList<>(2);
/* 88 */     basePoints.add(this.p1);
/* 89 */     basePoints.add(this.p2);
/*    */     
/* 91 */     return basePoints;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/Line.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */