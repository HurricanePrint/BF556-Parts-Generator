/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
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
/*     */ public final class ShapeTransformUtil
/*     */ {
/*     */   public static BezierCurve transformBezierCurve(BezierCurve bezierCurve, Matrix ctm) {
/*  44 */     return (BezierCurve)transformSegment(bezierCurve, ctm);
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
/*     */   public static Line transformLine(Line line, Matrix ctm) {
/*  57 */     return (Line)transformSegment(line, ctm);
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
/*     */   public static Path transformPath(Path path, Matrix ctm) {
/*  70 */     Path newPath = new Path();
/*     */     
/*  72 */     for (Subpath subpath : path.getSubpaths()) {
/*  73 */       Subpath transformedSubpath = transformSubpath(subpath, ctm);
/*  74 */       newPath.addSubpath(transformedSubpath);
/*     */     } 
/*     */     
/*  77 */     return newPath;
/*     */   }
/*     */   
/*     */   private static Subpath transformSubpath(Subpath subpath, Matrix ctm) {
/*  81 */     Subpath newSubpath = new Subpath();
/*  82 */     newSubpath.setClosed(subpath.isClosed());
/*     */     
/*  84 */     for (IShape segment : subpath.getSegments()) {
/*  85 */       IShape transformedSegment = transformSegment(segment, ctm);
/*  86 */       newSubpath.addSegment(transformedSegment);
/*     */     } 
/*     */     
/*  89 */     return newSubpath;
/*     */   }
/*     */   private static IShape transformSegment(IShape segment, Matrix ctm) {
/*     */     IShape newSegment;
/*  93 */     List<Point> basePoints = segment.getBasePoints();
/*  94 */     Point[] newBasePoints = transformPoints(ctm, basePoints.<Point>toArray(new Point[basePoints.size()]));
/*     */ 
/*     */     
/*  97 */     if (segment instanceof BezierCurve) {
/*  98 */       newSegment = new BezierCurve(Arrays.asList(newBasePoints));
/*     */     } else {
/* 100 */       newSegment = new Line(newBasePoints[0], newBasePoints[1]);
/*     */     } 
/*     */     
/* 103 */     return newSegment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Point[] transformPoints(Matrix ctm, Point... points) {
/*     */     try {
/* 111 */       AffineTransform t = new AffineTransform(ctm.get(0), ctm.get(1), ctm.get(3), ctm.get(4), ctm.get(6), ctm.get(7));
/*     */       
/* 113 */       t = t.createInverse();
/*     */       
/* 115 */       Point[] newPoints = new Point[points.length];
/* 116 */       t.transform(points, 0, newPoints, 0, points.length);
/* 117 */       return newPoints;
/* 118 */     } catch (NoninvertibleTransformException e) {
/* 119 */       throw new PdfException("A noninvertible matrix has been parsed. The behaviour is unpredictable.", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/ShapeTransformUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */