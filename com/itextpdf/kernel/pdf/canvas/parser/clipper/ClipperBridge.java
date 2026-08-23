/*     */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Path;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Subpath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClipperBridge
/*     */ {
/*  70 */   public static double floatMultiplier = Math.pow(10.0D, 14.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Path convertToPath(PolyTree result) {
/*  80 */     Path path = new Path();
/*  81 */     PolyNode node = result.getFirst();
/*     */     
/*  83 */     while (node != null) {
/*  84 */       addContour(path, node.getContour(), !node.isOpen());
/*  85 */       node = node.getNext();
/*     */     } 
/*     */     
/*  88 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addPath(IClipper clipper, Path path, IClipper.PolyType polyType) {
/*  98 */     for (Subpath subpath : path.getSubpaths()) {
/*  99 */       if (!subpath.isSinglePointClosed() && !subpath.isSinglePointOpen()) {
/* 100 */         List<Point> linearApproxPoints = subpath.getPiecewiseLinearApproximation();
/* 101 */         clipper.addPath(new Path(convertToLongPoints(linearApproxPoints)), polyType, subpath.isClosed());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Subpath> addPath(ClipperOffset offset, Path path, IClipper.JoinType joinType, IClipper.EndType endType) {
/* 120 */     List<Subpath> degenerateSubpaths = new ArrayList<>();
/*     */     
/* 122 */     for (Subpath subpath : path.getSubpaths()) {
/* 123 */       if (subpath.isDegenerate()) {
/* 124 */         degenerateSubpaths.add(subpath);
/*     */         
/*     */         continue;
/*     */       } 
/* 128 */       if (!subpath.isSinglePointClosed() && !subpath.isSinglePointOpen()) {
/*     */         IClipper.EndType et;
/*     */         
/* 131 */         if (subpath.isClosed()) {
/*     */           
/* 133 */           et = IClipper.EndType.CLOSED_LINE;
/*     */         } else {
/* 135 */           et = endType;
/*     */         } 
/*     */         
/* 138 */         List<Point> linearApproxPoints = subpath.getPiecewiseLinearApproximation();
/* 139 */         offset.addPath(new Path(convertToLongPoints(linearApproxPoints)), joinType, et);
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     return degenerateSubpaths;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Point> convertToFloatPoints(List<Point.LongPoint> points) {
/* 154 */     List<Point> convertedPoints = new ArrayList<>(points.size());
/*     */     
/* 156 */     for (Point.LongPoint point : points) {
/* 157 */       convertedPoints.add(new Point(point
/* 158 */             .getX() / floatMultiplier, point
/* 159 */             .getY() / floatMultiplier));
/*     */     }
/*     */ 
/*     */     
/* 163 */     return convertedPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Point.LongPoint> convertToLongPoints(List<Point> points) {
/* 174 */     List<Point.LongPoint> convertedPoints = new ArrayList<>(points.size());
/*     */     
/* 176 */     for (Point point : points) {
/* 177 */       convertedPoints.add(new Point.LongPoint(floatMultiplier * point
/* 178 */             .getX(), floatMultiplier * point
/* 179 */             .getY()));
/*     */     }
/*     */ 
/*     */     
/* 183 */     return convertedPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IClipper.JoinType getJoinType(int lineJoinStyle) {
/* 193 */     switch (lineJoinStyle) {
/*     */       case 2:
/* 195 */         return IClipper.JoinType.BEVEL;
/*     */       
/*     */       case 0:
/* 198 */         return IClipper.JoinType.MITER;
/*     */     } 
/*     */     
/* 201 */     return IClipper.JoinType.ROUND;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IClipper.EndType getEndType(int lineCapStyle) {
/* 211 */     switch (lineCapStyle) {
/*     */       case 0:
/* 213 */         return IClipper.EndType.OPEN_BUTT;
/*     */       
/*     */       case 2:
/* 216 */         return IClipper.EndType.OPEN_SQUARE;
/*     */     } 
/*     */     
/* 219 */     return IClipper.EndType.OPEN_ROUND;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IClipper.PolyFillType getFillType(int fillingRule) {
/* 230 */     IClipper.PolyFillType fillType = IClipper.PolyFillType.NON_ZERO;
/*     */     
/* 232 */     if (fillingRule == 2) {
/* 233 */       fillType = IClipper.PolyFillType.EVEN_ODD;
/*     */     }
/*     */     
/* 236 */     return fillType;
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
/*     */   public static boolean addPolygonToClipper(IClipper clipper, Point[] polyVertices, IClipper.PolyType polyType) {
/* 257 */     return clipper.addPath(new Path(convertToLongPoints(new ArrayList<>(Arrays.asList(polyVertices)))), polyType, true);
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
/*     */   public static boolean addPolylineSubjectToClipper(IClipper clipper, Point[] lineVertices) {
/* 276 */     return clipper.addPath(new Path(convertToLongPoints(new ArrayList<>(Arrays.asList(lineVertices)))), IClipper.PolyType.SUBJECT, false);
/*     */   }
/*     */   
/*     */   static void addContour(Path path, List<Point.LongPoint> contour, boolean close) {
/* 280 */     List<Point> floatContour = convertToFloatPoints(contour);
/* 281 */     Point point = floatContour.get(0);
/* 282 */     path.moveTo((float)point.getX(), (float)point.getY());
/*     */     
/* 284 */     for (int i = 1; i < floatContour.size(); i++) {
/* 285 */       point = floatContour.get(i);
/* 286 */       path.lineTo((float)point.getX(), (float)point.getY());
/*     */     } 
/*     */     
/* 289 */     if (close) {
/* 290 */       path.closeSubpath();
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void addRectToClipper(IClipper clipper, Point[] rectVertices, IClipper.PolyType polyType) {
/* 307 */     clipper.addPath(new Path(convertToLongPoints(new ArrayList<>(Arrays.asList(rectVertices)))), polyType, true);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/ClipperBridge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */