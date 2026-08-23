/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import com.itextpdf.io.util.HashCode;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Point
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -5276940640259749850L;
/*     */   public double x;
/*     */   public double y;
/*     */   
/*     */   public Point() {
/*  39 */     setLocation(0, 0);
/*     */   }
/*     */   
/*     */   public Point(int x, int y) {
/*  43 */     setLocation(x, y);
/*     */   }
/*     */   
/*     */   public Point(double x, double y) {
/*  47 */     setLocation(x, y);
/*     */   }
/*     */   
/*     */   public Point(Point p) {
/*  51 */     setLocation(p.x, p.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  56 */     if (obj == this) {
/*  57 */       return true;
/*     */     }
/*  59 */     if (obj instanceof Point) {
/*  60 */       Point p = (Point)obj;
/*  61 */       return (this.x == p.x && this.y == p.y);
/*     */     } 
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  69 */     return MessageFormatUtil.format("Point: [x={0},y={1}]", new Object[] { Double.valueOf(this.x), Double.valueOf(this.y) });
/*     */   }
/*     */   
/*     */   public double getX() {
/*  73 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  77 */     return this.y;
/*     */   }
/*     */   
/*     */   public Point getLocation() {
/*  81 */     return new Point(this.x, this.y);
/*     */   }
/*     */   
/*     */   public void setLocation(Point p) {
/*  85 */     setLocation(p.x, p.y);
/*     */   }
/*     */   
/*     */   public void setLocation(int x, int y) {
/*  89 */     setLocation(x, y);
/*     */   }
/*     */   
/*     */   public void setLocation(double x, double y) {
/*  93 */     this.x = x;
/*  94 */     this.y = y;
/*     */   }
/*     */   
/*     */   public void move(double x, double y) {
/*  98 */     setLocation(x, y);
/*     */   }
/*     */   
/*     */   public void translate(double dx, double dy) {
/* 102 */     this.x += dx;
/* 103 */     this.y += dy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     HashCode hash = new HashCode();
/* 110 */     hash.append(getX());
/* 111 */     hash.append(getY());
/* 112 */     return hash.hashCode();
/*     */   }
/*     */   
/*     */   public static double distanceSq(double x1, double y1, double x2, double y2) {
/* 116 */     x2 -= x1;
/* 117 */     y2 -= y1;
/* 118 */     return x2 * x2 + y2 * y2;
/*     */   }
/*     */   
/*     */   public double distanceSq(double px, double py) {
/* 122 */     return distanceSq(getX(), getY(), px, py);
/*     */   }
/*     */   
/*     */   public double distanceSq(Point p) {
/* 126 */     return distanceSq(getX(), getY(), p.getX(), p.getY());
/*     */   }
/*     */   
/*     */   public static double distance(double x1, double y1, double x2, double y2) {
/* 130 */     return Math.sqrt(distanceSq(x1, y1, x2, y2));
/*     */   }
/*     */   
/*     */   public double distance(double px, double py) {
/* 134 */     return Math.sqrt(distanceSq(px, py));
/*     */   }
/*     */   
/*     */   public double distance(Point p) {
/* 138 */     return Math.sqrt(distanceSq(p));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 144 */     return new Point(this.x, this.y);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/Point.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */