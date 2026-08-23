/*     */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Point<T extends Number & Comparable<T>>
/*     */ {
/*  38 */   private static final NumberComparator NUMBER_COMPARATOR = new NumberComparator<>();
/*     */   
/*     */   protected T x;
/*     */   protected T y;
/*     */   protected T z;
/*     */   
/*     */   protected Point(Point<T> pt) {
/*  45 */     this(pt.x, pt.y, pt.z);
/*     */   }
/*     */   
/*     */   protected Point(T x, T y, T z) {
/*  49 */     this.x = x;
/*  50 */     this.y = y;
/*  51 */     this.z = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  56 */     if (obj == null) {
/*  57 */       return false;
/*     */     }
/*  59 */     if (obj instanceof Point) {
/*  60 */       Point<?> a = (Point)obj;
/*  61 */       return (NUMBER_COMPARATOR.compare(this.x, a.x) == 0 && NUMBER_COMPARATOR.compare(this.y, a.y) == 0);
/*     */     } 
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(Point<T> other) {
/*  68 */     this.x = other.x;
/*  69 */     this.y = other.y;
/*  70 */     this.z = other.z;
/*     */   }
/*     */   
/*     */   public void setX(T x) {
/*  74 */     this.x = x;
/*     */   }
/*     */   
/*     */   public void setY(T y) {
/*  78 */     this.y = y;
/*     */   }
/*     */   
/*     */   public void setZ(T z) {
/*  82 */     this.z = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  87 */     return "Point [x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isPt2BetweenPt1AndPt3(LongPoint pt1, LongPoint pt2, LongPoint pt3) {
/*  92 */     if (pt1.equals(pt3) || pt1.equals(pt2) || pt3.equals(pt2))
/*  93 */       return false; 
/*  94 */     if (pt1.getX() != pt3.getX()) {
/*  95 */       return (((pt2.x.longValue() > pt1.x.longValue()) ? true : false) == ((pt2.x.longValue() < pt3.x.longValue()) ? true : false));
/*     */     }
/*  97 */     return (((pt2.y.longValue() > pt1.y.longValue()) ? true : false) == ((pt2.y.longValue() < pt3.y.longValue()) ? true : false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean slopesEqual(LongPoint pt1, LongPoint pt2, LongPoint pt3, boolean useFullRange) {
/* 102 */     return slopesEqual(pt1, pt2, pt2, pt3, useFullRange);
/*     */   }
/*     */   
/*     */   protected static boolean slopesEqual(LongPoint pt1, LongPoint pt2, LongPoint pt3, LongPoint pt4, boolean useFullRange) {
/* 106 */     if (useFullRange) {
/* 107 */       return BigInteger.valueOf(pt1.getY() - pt2.getY()).multiply(BigInteger.valueOf(pt3.getX() - pt4.getX()))
/* 108 */         .equals(
/* 109 */           BigInteger.valueOf(pt1.getX() - pt2.getX())
/* 110 */           .multiply(BigInteger.valueOf(pt3.getY() - pt4.getY())));
/*     */     }
/* 112 */     return 
/* 113 */       ((pt1.getY() - pt2.getY()) * (pt3.getX() - pt4.getX()) - (pt1.getX() - pt2.getX()) * (pt3.getY() - pt4.getY()) == 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean arePointsClose(Point<? extends Number> pt1, Point<? extends Number> pt2, double distSqrd) {
/* 118 */     double dx = pt1.x.doubleValue() - pt2.x.doubleValue();
/* 119 */     double dy = pt1.y.doubleValue() - pt2.y.doubleValue();
/* 120 */     return (dx * dx + dy * dy <= distSqrd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double distanceFromLineSqrd(Point<? extends Number> pt, Point<? extends Number> ln1, Point<? extends Number> ln2) {
/* 130 */     double A = ln1.y.doubleValue() - ln2.y.doubleValue();
/* 131 */     double B = ln2.x.doubleValue() - ln1.x.doubleValue();
/* 132 */     double C = A * ln1.x.doubleValue() + B * ln1.y.doubleValue();
/* 133 */     C = A * pt.x.doubleValue() + B * pt.y.doubleValue() - C;
/* 134 */     return C * C / (A * A + B * B);
/*     */   }
/*     */   
/*     */   static DoublePoint getUnitNormal(LongPoint pt1, LongPoint pt2) {
/* 138 */     double dx = (pt2.x.longValue() - pt1.x.longValue());
/* 139 */     double dy = (pt2.y.longValue() - pt1.y.longValue());
/* 140 */     if (dx == 0.0D && dy == 0.0D) {
/* 141 */       return new DoublePoint();
/*     */     }
/*     */     
/* 144 */     double f = 1.0D / Math.sqrt(dx * dx + dy * dy);
/* 145 */     dx *= f;
/* 146 */     dy *= f;
/*     */     
/* 148 */     return new DoublePoint(dy, -dx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean slopesNearCollinear(LongPoint pt1, LongPoint pt2, LongPoint pt3, double distSqrd) {
/* 155 */     if (Math.abs(pt1.x.longValue() - pt2.x.longValue()) > Math.abs(pt1.y.longValue() - pt2.y.longValue())) {
/* 156 */       if (((pt1.x.longValue() > pt2.x.longValue()) ? true : false) == ((pt1.x.longValue() < pt3.x.longValue()) ? true : false))
/* 157 */         return (distanceFromLineSqrd(pt1, pt2, pt3) < distSqrd); 
/* 158 */       if (((pt2.x.longValue() > pt1.x.longValue()) ? true : false) == ((pt2.x.longValue() < pt3.x.longValue()) ? true : false)) {
/* 159 */         return (distanceFromLineSqrd(pt2, pt1, pt3) < distSqrd);
/*     */       }
/* 161 */       return (distanceFromLineSqrd(pt3, pt1, pt2) < distSqrd);
/*     */     } 
/*     */     
/* 164 */     if (((pt1.y.longValue() > pt2.y.longValue()) ? true : false) == ((pt1.y.longValue() < pt3.y.longValue()) ? true : false))
/* 165 */       return (distanceFromLineSqrd(pt1, pt2, pt3) < distSqrd); 
/* 166 */     if (((pt2.y.longValue() > pt1.y.longValue()) ? true : false) == ((pt2.y.longValue() < pt3.y.longValue()) ? true : false)) {
/* 167 */       return (distanceFromLineSqrd(pt2, pt1, pt3) < distSqrd);
/*     */     }
/* 169 */     return (distanceFromLineSqrd(pt3, pt1, pt2) < distSqrd);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DoublePoint
/*     */     extends Point<Double>
/*     */   {
/*     */     public DoublePoint() {
/* 177 */       this(0.0D, 0.0D);
/*     */     }
/*     */     
/*     */     public DoublePoint(double x, double y) {
/* 181 */       this(x, y, 0.0D);
/*     */     }
/*     */     
/*     */     public DoublePoint(double x, double y, double z) {
/* 185 */       super(Double.valueOf(x), Double.valueOf(y), Double.valueOf(z));
/*     */     }
/*     */     
/*     */     public DoublePoint(DoublePoint other) {
/* 189 */       super(other);
/*     */     }
/*     */     
/*     */     public double getX() {
/* 193 */       return this.x.doubleValue();
/*     */     }
/*     */     
/*     */     public double getY() {
/* 197 */       return this.y.doubleValue();
/*     */     }
/*     */     
/*     */     public double getZ() {
/* 201 */       return this.z.doubleValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LongPoint extends Point<Long> {
/*     */     public LongPoint() {
/* 207 */       this(0L, 0L);
/*     */     }
/*     */     
/*     */     public LongPoint(long x, long y) {
/* 211 */       this(x, y, 0L);
/*     */     }
/*     */     
/*     */     public LongPoint(double x, double y) {
/* 215 */       this((long)x, (long)y);
/*     */     }
/*     */     
/*     */     public LongPoint(long x, long y, long z) {
/* 219 */       super(Long.valueOf(x), Long.valueOf(y), Long.valueOf(z));
/*     */     }
/*     */     
/*     */     public LongPoint(LongPoint other) {
/* 223 */       super(other);
/*     */     }
/*     */     
/*     */     public static double getDeltaX(LongPoint pt1, LongPoint pt2) {
/* 227 */       if (pt1.getY() == pt2.getY()) {
/* 228 */         return -3.4E38D;
/*     */       }
/* 230 */       return (pt2.getX() - pt1.getX()) / (pt2.getY() - pt1.getY());
/*     */     }
/*     */ 
/*     */     
/*     */     public long getX() {
/* 235 */       return this.x.longValue();
/*     */     }
/*     */     
/*     */     public long getY() {
/* 239 */       return this.y.longValue();
/*     */     }
/*     */     
/*     */     public long getZ() {
/* 243 */       return this.z.longValue();
/*     */     } }
/*     */   
/*     */   private static class NumberComparator<T extends Number & Comparable<T>> implements Comparator<T> {
/*     */     private NumberComparator() {}
/*     */     
/*     */     public int compare(T a, T b) throws ClassCastException {
/* 250 */       return ((Comparable<T>)a).compareTo(b);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/Point.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */