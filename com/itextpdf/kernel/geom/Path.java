/*     */ package com.itextpdf.kernel.geom;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class Path
/*     */   implements Serializable
/*     */ {
/*     */   private static final String START_PATH_ERR_MSG = "Path shall start with \"re\" or \"m\" operator";
/*     */   private static final long serialVersionUID = 1658560770858987684L;
/*  62 */   private List<Subpath> subpaths = new ArrayList<>();
/*     */   
/*     */   private Point currentPoint;
/*     */   
/*     */   public Path() {}
/*     */   
/*     */   public Path(List<? extends Subpath> subpaths) {
/*  69 */     addSubpaths(subpaths);
/*     */   }
/*     */   
/*     */   public Path(Path path) {
/*  73 */     addSubpaths(path.getSubpaths());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Subpath> getSubpaths() {
/*  80 */     return this.subpaths;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSubpath(Subpath subpath) {
/*  89 */     this.subpaths.add(subpath);
/*  90 */     this.currentPoint = subpath.getLastPoint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSubpaths(List<? extends Subpath> subpaths) {
/*  99 */     if (subpaths.size() > 0) {
/* 100 */       for (Subpath subpath : subpaths) {
/* 101 */         this.subpaths.add(new Subpath(subpath));
/*     */       }
/* 103 */       this.currentPoint = ((Subpath)this.subpaths.get(subpaths.size() - 1)).getLastPoint();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getCurrentPoint() {
/* 113 */     return this.currentPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveTo(float x, float y) {
/* 122 */     this.currentPoint = new Point(x, y);
/* 123 */     Subpath lastSubpath = (this.subpaths.size() > 0) ? this.subpaths.get(this.subpaths.size() - 1) : null;
/*     */     
/* 125 */     if (lastSubpath != null && lastSubpath.isSinglePointOpen()) {
/* 126 */       lastSubpath.setStartPoint(this.currentPoint);
/*     */     } else {
/* 128 */       this.subpaths.add(new Subpath(this.currentPoint));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void lineTo(float x, float y) {
/* 138 */     if (this.currentPoint == null) {
/* 139 */       throw new RuntimeException("Path shall start with \"re\" or \"m\" operator");
/*     */     }
/* 141 */     Point targetPoint = new Point(x, y);
/* 142 */     getLastSubpath().addSegment(new Line(this.currentPoint, targetPoint));
/* 143 */     this.currentPoint = targetPoint;
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
/*     */   public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) {
/* 157 */     if (this.currentPoint == null) {
/* 158 */       throw new RuntimeException("Path shall start with \"re\" or \"m\" operator");
/*     */     }
/*     */     
/* 161 */     Point secondPoint = new Point(x1, y1);
/* 162 */     Point thirdPoint = new Point(x2, y2);
/* 163 */     Point fourthPoint = new Point(x3, y3);
/*     */     
/* 165 */     List<Point> controlPoints = new ArrayList<>(Arrays.asList(new Point[] { this.currentPoint, secondPoint, thirdPoint, fourthPoint }));
/* 166 */     getLastSubpath().addSegment(new BezierCurve(controlPoints));
/*     */     
/* 168 */     this.currentPoint = fourthPoint;
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
/*     */   public void curveTo(float x2, float y2, float x3, float y3) {
/* 182 */     if (this.currentPoint == null) {
/* 183 */       throw new RuntimeException("Path shall start with \"re\" or \"m\" operator");
/*     */     }
/* 185 */     curveTo((float)this.currentPoint.getX(), (float)this.currentPoint.getY(), x2, y2, x3, y3);
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
/*     */   public void curveFromTo(float x1, float y1, float x3, float y3) {
/* 199 */     if (this.currentPoint == null) {
/* 200 */       throw new RuntimeException("Path shall start with \"re\" or \"m\" operator");
/*     */     }
/* 202 */     curveTo(x1, y1, x3, y3, x3, y3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rectangle(Rectangle rect) {
/* 210 */     rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rectangle(float x, float y, float w, float h) {
/* 221 */     moveTo(x, y);
/* 222 */     lineTo(x + w, y);
/* 223 */     lineTo(x + w, y + h);
/* 224 */     lineTo(x, y + h);
/* 225 */     closeSubpath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeSubpath() {
/* 232 */     if (!isEmpty()) {
/* 233 */       Subpath lastSubpath = getLastSubpath();
/* 234 */       lastSubpath.setClosed(true);
/*     */       
/* 236 */       Point startPoint = lastSubpath.getStartPoint();
/* 237 */       moveTo((float)startPoint.getX(), (float)startPoint.getY());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeAllSubpaths() {
/* 245 */     for (Subpath subpath : this.subpaths) {
/* 246 */       subpath.setClosed(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> replaceCloseWithLine() {
/* 257 */     List<Integer> modifiedSubpathsIndices = new ArrayList<>();
/* 258 */     int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     for (Subpath subpath : this.subpaths) {
/* 265 */       if (subpath.isClosed()) {
/* 266 */         subpath.setClosed(false);
/* 267 */         subpath.addSegment(new Line(subpath.getLastPoint(), subpath.getStartPoint()));
/* 268 */         modifiedSubpathsIndices.add(Integer.valueOf(i));
/*     */       } 
/* 270 */       i++;
/*     */     } 
/*     */     
/* 273 */     return modifiedSubpathsIndices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 281 */     return (this.subpaths.size() == 0);
/*     */   }
/*     */   
/*     */   private Subpath getLastSubpath() {
/* 285 */     assert this.subpaths.size() > 0;
/* 286 */     return this.subpaths.get(this.subpaths.size() - 1);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/Path.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */