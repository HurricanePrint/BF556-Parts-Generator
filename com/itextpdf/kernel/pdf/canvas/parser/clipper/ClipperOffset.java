/*     */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ public class ClipperOffset
/*     */ {
/*     */   private Paths destPolys;
/*     */   private Path srcPoly;
/*     */   private Path destPoly;
/*     */   private final List<Point.DoublePoint> normals;
/*     */   private double delta;
/*     */   private double inA;
/*     */   private double sin;
/*     */   private double cos;
/*     */   private double miterLim;
/*     */   private double stepsPerRad;
/*     */   private Point.LongPoint lowest;
/*     */   private final PolyNode polyNodes;
/*     */   private final double arcTolerance;
/*     */   private final double miterLimit;
/*     */   private static final double TWO_PI = 6.283185307179586D;
/*     */   private static final double DEFAULT_ARC_TOLERANCE = 0.25D;
/*     */   private static final double TOLERANCE = 1.0E-20D;
/*     */   
/*     */   private static boolean nearZero(double val) {
/*  44 */     return (val > -1.0E-20D && val < 1.0E-20D);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ClipperOffset() {
/*  68 */     this(2.0D, 0.25D);
/*     */   }
/*     */   
/*     */   public ClipperOffset(double miterLimit) {
/*  72 */     this(miterLimit, 0.25D);
/*     */   }
/*     */   
/*     */   public ClipperOffset(double miterLimit, double arcTolerance) {
/*  76 */     this.miterLimit = miterLimit;
/*  77 */     this.arcTolerance = arcTolerance;
/*  78 */     this.lowest = new Point.LongPoint();
/*  79 */     this.lowest.setX(Long.valueOf(-1L));
/*  80 */     this.polyNodes = new PolyNode();
/*  81 */     this.normals = new ArrayList<>();
/*     */   }
/*     */   
/*     */   public void addPath(Path path, IClipper.JoinType joinType, IClipper.EndType endType) {
/*  85 */     int highI = path.size() - 1;
/*  86 */     if (highI < 0) {
/*     */       return;
/*     */     }
/*  89 */     PolyNode newNode = new PolyNode();
/*  90 */     newNode.setJoinType(joinType);
/*  91 */     newNode.setEndType(endType);
/*     */ 
/*     */     
/*  94 */     if (endType == IClipper.EndType.CLOSED_LINE || endType == IClipper.EndType.CLOSED_POLYGON) {
/*  95 */       while (highI > 0 && path.get(0).equals(path.get(highI))) {
/*  96 */         highI--;
/*     */       }
/*     */     }
/*     */     
/* 100 */     newNode.getPolygon().add(path.get(0));
/* 101 */     int j = 0, k = 0;
/* 102 */     for (int i = 1; i <= highI; i++) {
/* 103 */       if (!newNode.getPolygon().get(j).equals(path.get(i))) {
/* 104 */         j++;
/* 105 */         newNode.getPolygon().add(path.get(i));
/* 106 */         if (path.get(i).getY() > newNode.getPolygon().get(k).getY() || (path.get(i).getY() == newNode.getPolygon().get(k).getY() && path
/* 107 */           .get(i).getX() < newNode.getPolygon().get(k).getX())) {
/* 108 */           k = j;
/*     */         }
/*     */       } 
/*     */     } 
/* 112 */     if (endType == IClipper.EndType.CLOSED_POLYGON && j < 2) {
/*     */       return;
/*     */     }
/*     */     
/* 116 */     this.polyNodes.addChild(newNode);
/*     */ 
/*     */     
/* 119 */     if (endType != IClipper.EndType.CLOSED_POLYGON) {
/*     */       return;
/*     */     }
/* 122 */     if (this.lowest.getX() < 0L) {
/* 123 */       this.lowest = new Point.LongPoint((this.polyNodes.getChildCount() - 1), k);
/*     */     } else {
/*     */       
/* 126 */       Point.LongPoint ip = ((PolyNode)this.polyNodes.getChilds().get((int)this.lowest.getX())).getPolygon().get((int)this.lowest.getY());
/* 127 */       if (newNode.getPolygon().get(k).getY() > ip.getY() || (newNode.getPolygon().get(k).getY() == ip.getY() && newNode
/* 128 */         .getPolygon().get(k).getX() < ip.getX())) {
/* 129 */         this.lowest = new Point.LongPoint((this.polyNodes.getChildCount() - 1), k);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addPaths(Paths paths, IClipper.JoinType joinType, IClipper.EndType endType) {
/* 135 */     for (Path p : paths) {
/* 136 */       addPath(p, joinType, endType);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 141 */     this.polyNodes.getChilds().clear();
/* 142 */     this.lowest.setX(Long.valueOf(-1L));
/*     */   }
/*     */   
/*     */   private void doMiter(int j, int k, double r) {
/* 146 */     double q = this.delta / r;
/* 147 */     this.destPoly.add(new Point.LongPoint(Math.round(this.srcPoly.get(j).getX() + (((Point.DoublePoint)this.normals.get(k)).getX() + ((Point.DoublePoint)this.normals.get(j)).getX()) * q), 
/* 148 */           Math.round(this.srcPoly.get(j).getY() + (((Point.DoublePoint)this.normals.get(k)).getY() + ((Point.DoublePoint)this.normals.get(j)).getY()) * q)));
/*     */   }
/*     */   private void doOffset(double delta) {
/*     */     double y;
/* 152 */     this.destPolys = new Paths();
/* 153 */     this.delta = delta;
/*     */ 
/*     */     
/* 156 */     if (nearZero(delta)) {
/* 157 */       for (int j = 0; j < this.polyNodes.getChildCount(); j++) {
/* 158 */         PolyNode node = this.polyNodes.getChilds().get(j);
/* 159 */         if (node.getEndType() == IClipper.EndType.CLOSED_POLYGON) {
/* 160 */           this.destPolys.add(node.getPolygon());
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 167 */     if (this.miterLimit > 2.0D) {
/* 168 */       this.miterLim = 2.0D / this.miterLimit * this.miterLimit;
/*     */     } else {
/*     */       
/* 171 */       this.miterLim = 0.5D;
/*     */     } 
/*     */ 
/*     */     
/* 175 */     if (this.arcTolerance <= 0.0D) {
/* 176 */       y = 0.25D;
/*     */     }
/* 178 */     else if (this.arcTolerance > Math.abs(delta) * 0.25D) {
/* 179 */       y = Math.abs(delta) * 0.25D;
/*     */     } else {
/*     */       
/* 182 */       y = this.arcTolerance;
/*     */     } 
/*     */     
/* 185 */     double steps = Math.PI / Math.acos(1.0D - y / Math.abs(delta));
/* 186 */     this.sin = Math.sin(6.283185307179586D / steps);
/* 187 */     this.cos = Math.cos(6.283185307179586D / steps);
/* 188 */     this.stepsPerRad = steps / 6.283185307179586D;
/* 189 */     if (delta < 0.0D) {
/* 190 */       this.sin = -this.sin;
/*     */     }
/*     */     
/* 193 */     for (int i = 0; i < this.polyNodes.getChildCount(); i++) {
/* 194 */       PolyNode node = this.polyNodes.getChilds().get(i);
/* 195 */       this.srcPoly = node.getPolygon();
/*     */       
/* 197 */       int len = this.srcPoly.size();
/*     */       
/* 199 */       if (len != 0 && (delta > 0.0D || (len >= 3 && node.getEndType() == IClipper.EndType.CLOSED_POLYGON))) {
/*     */ 
/*     */ 
/*     */         
/* 203 */         this.destPoly = new Path();
/*     */         
/* 205 */         if (len == 1) {
/* 206 */           if (node.getJoinType() == IClipper.JoinType.ROUND) {
/* 207 */             double X = 1.0D, Y = 0.0D;
/* 208 */             for (int j = 1; j <= steps; j++) {
/* 209 */               this.destPoly.add(new Point.LongPoint(Math.round(this.srcPoly.get(0).getX() + X * delta), Math.round(this.srcPoly.get(0).getY() + Y * delta)));
/*     */               
/* 211 */               double X2 = X;
/* 212 */               X = X * this.cos - this.sin * Y;
/* 213 */               Y = X2 * this.sin + Y * this.cos;
/*     */             } 
/*     */           } else {
/*     */             
/* 217 */             double X = -1.0D, Y = -1.0D;
/* 218 */             for (int j = 0; j < 4; j++) {
/* 219 */               this.destPoly.add(new Point.LongPoint(Math.round(this.srcPoly.get(0).getX() + X * delta), Math.round(this.srcPoly.get(0).getY() + Y * delta)));
/*     */               
/* 221 */               if (X < 0.0D) {
/* 222 */                 X = 1.0D;
/*     */               }
/* 224 */               else if (Y < 0.0D) {
/* 225 */                 Y = 1.0D;
/*     */               } else {
/*     */                 
/* 228 */                 X = -1.0D;
/*     */               } 
/*     */             } 
/*     */           } 
/* 232 */           this.destPolys.add(this.destPoly);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 237 */           this.normals.clear();
/* 238 */           for (int j = 0; j < len - 1; j++) {
/* 239 */             this.normals.add(Point.getUnitNormal(this.srcPoly.get(j), this.srcPoly.get(j + 1)));
/*     */           }
/* 241 */           if (node.getEndType() == IClipper.EndType.CLOSED_LINE || node.getEndType() == IClipper.EndType.CLOSED_POLYGON) {
/* 242 */             this.normals.add(Point.getUnitNormal(this.srcPoly.get(len - 1), this.srcPoly.get(0)));
/*     */           } else {
/*     */             
/* 245 */             this.normals.add(new Point.DoublePoint(this.normals.get(len - 2)));
/*     */           } 
/*     */           
/* 248 */           if (node.getEndType() == IClipper.EndType.CLOSED_POLYGON) {
/* 249 */             int[] k = { len - 1 };
/* 250 */             for (int m = 0; m < len; m++) {
/* 251 */               offsetPoint(m, k, node.getJoinType());
/*     */             }
/* 253 */             this.destPolys.add(this.destPoly);
/*     */           }
/* 255 */           else if (node.getEndType() == IClipper.EndType.CLOSED_LINE) {
/* 256 */             int[] k = { len - 1 };
/* 257 */             for (int m = 0; m < len; m++) {
/* 258 */               offsetPoint(m, k, node.getJoinType());
/*     */             }
/* 260 */             this.destPolys.add(this.destPoly);
/* 261 */             this.destPoly = new Path();
/*     */             
/* 263 */             Point.DoublePoint n = this.normals.get(len - 1); int i1;
/* 264 */             for (i1 = len - 1; i1 > 0; i1--) {
/* 265 */               this.normals.set(i1, new Point.DoublePoint(-((Point.DoublePoint)this.normals.get(i1 - 1)).getX(), -((Point.DoublePoint)this.normals.get(i1 - 1)).getY()));
/*     */             }
/* 267 */             this.normals.set(0, new Point.DoublePoint(-n.getX(), -n.getY(), 0.0D));
/* 268 */             k[0] = 0;
/* 269 */             for (i1 = len - 1; i1 >= 0; i1--) {
/* 270 */               offsetPoint(i1, k, node.getJoinType());
/*     */             }
/* 272 */             this.destPolys.add(this.destPoly);
/*     */           } else {
/*     */             
/* 275 */             int[] k = new int[1];
/* 276 */             for (int m = 1; m < len - 1; m++) {
/* 277 */               offsetPoint(m, k, node.getJoinType());
/*     */             }
/*     */ 
/*     */             
/* 281 */             if (node.getEndType() == IClipper.EndType.OPEN_BUTT) {
/* 282 */               int i1 = len - 1;
/* 283 */               Point.LongPoint pt1 = new Point.LongPoint(Math.round(this.srcPoly.get(i1).getX() + ((Point.DoublePoint)this.normals.get(i1)).getX() * delta), Math.round(this.srcPoly.get(i1)
/* 284 */                     .getY() + ((Point.DoublePoint)this.normals.get(i1)).getY() * delta), 0L);
/* 285 */               this.destPoly.add(pt1);
/* 286 */               pt1 = new Point.LongPoint(Math.round(this.srcPoly.get(i1).getX() - ((Point.DoublePoint)this.normals.get(i1)).getX() * delta), Math.round(this.srcPoly.get(i1)
/* 287 */                     .getY() - ((Point.DoublePoint)this.normals.get(i1)).getY() * delta), 0L);
/* 288 */               this.destPoly.add(pt1);
/*     */             } else {
/*     */               
/* 291 */               int i1 = len - 1;
/* 292 */               k[0] = len - 2;
/* 293 */               this.inA = 0.0D;
/* 294 */               this.normals.set(i1, new Point.DoublePoint(-((Point.DoublePoint)this.normals.get(i1)).getX(), -((Point.DoublePoint)this.normals.get(i1)).getY()));
/* 295 */               if (node.getEndType() == IClipper.EndType.OPEN_SQUARE) {
/* 296 */                 doSquare(i1, k[0], true);
/*     */               } else {
/*     */                 
/* 299 */                 doRound(i1, k[0]);
/*     */               } 
/*     */             } 
/*     */             
/*     */             int n;
/* 304 */             for (n = len - 1; n > 0; n--) {
/* 305 */               this.normals.set(n, new Point.DoublePoint(-((Point.DoublePoint)this.normals.get(n - 1)).getX(), -((Point.DoublePoint)this.normals.get(n - 1)).getY()));
/*     */             }
/*     */             
/* 308 */             this.normals.set(0, new Point.DoublePoint(-((Point.DoublePoint)this.normals.get(1)).getX(), -((Point.DoublePoint)this.normals.get(1)).getY()));
/*     */             
/* 310 */             k[0] = len - 1;
/* 311 */             for (n = k[0] - 1; n > 0; n--) {
/* 312 */               offsetPoint(n, k, node.getJoinType());
/*     */             }
/*     */             
/* 315 */             if (node.getEndType() == IClipper.EndType.OPEN_BUTT) {
/* 316 */               Point.LongPoint pt1 = new Point.LongPoint(Math.round(this.srcPoly.get(0).getX() - ((Point.DoublePoint)this.normals.get(0)).getX() * delta), Math.round(this.srcPoly.get(0)
/* 317 */                     .getY() - ((Point.DoublePoint)this.normals.get(0)).getY() * delta));
/* 318 */               this.destPoly.add(pt1);
/* 319 */               pt1 = new Point.LongPoint(Math.round(this.srcPoly.get(0).getX() + ((Point.DoublePoint)this.normals.get(0)).getX() * delta), Math.round(this.srcPoly.get(0)
/* 320 */                     .getY() + ((Point.DoublePoint)this.normals.get(0)).getY() * delta));
/* 321 */               this.destPoly.add(pt1);
/*     */             } else {
/*     */               
/* 324 */               k[0] = 1;
/* 325 */               this.inA = 0.0D;
/* 326 */               if (node.getEndType() == IClipper.EndType.OPEN_SQUARE) {
/* 327 */                 doSquare(0, 1, true);
/*     */               } else {
/*     */                 
/* 330 */                 doRound(0, 1);
/*     */               } 
/*     */             } 
/* 333 */             this.destPolys.add(this.destPoly);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private void doRound(int j, int k) {
/* 339 */     double a = Math.atan2(this.inA, ((Point.DoublePoint)this.normals.get(k)).getX() * ((Point.DoublePoint)this.normals.get(j)).getX() + ((Point.DoublePoint)this.normals.get(k)).getY() * ((Point.DoublePoint)this.normals.get(j)).getY());
/* 340 */     int steps = Math.max((int)Math.round(this.stepsPerRad * Math.abs(a)), 1);
/*     */     
/* 342 */     double X = ((Point.DoublePoint)this.normals.get(k)).getX(), Y = ((Point.DoublePoint)this.normals.get(k)).getY();
/* 343 */     for (int i = 0; i < steps; i++) {
/* 344 */       this.destPoly.add(new Point.LongPoint(Math.round(this.srcPoly.get(j).getX() + X * this.delta), Math.round(this.srcPoly.get(j).getY() + Y * this.delta)));
/* 345 */       double X2 = X;
/* 346 */       X = X * this.cos - this.sin * Y;
/* 347 */       Y = X2 * this.sin + Y * this.cos;
/*     */     } 
/* 349 */     this.destPoly.add(new Point.LongPoint(Math.round(this.srcPoly.get(j).getX() + ((Point.DoublePoint)this.normals.get(j)).getX() * this.delta), Math.round(this.srcPoly.get(j).getY() + ((Point.DoublePoint)this.normals
/* 350 */             .get(j)).getY() * this.delta)));
/*     */   }
/*     */   
/*     */   private void doSquare(int j, int k, boolean addExtra) {
/* 354 */     double nkx = ((Point.DoublePoint)this.normals.get(k)).getX();
/* 355 */     double nky = ((Point.DoublePoint)this.normals.get(k)).getY();
/* 356 */     double njx = ((Point.DoublePoint)this.normals.get(j)).getX();
/* 357 */     double njy = ((Point.DoublePoint)this.normals.get(j)).getY();
/* 358 */     double sjx = this.srcPoly.get(j).getX();
/* 359 */     double sjy = this.srcPoly.get(j).getY();
/* 360 */     double dx = Math.tan(Math.atan2(this.inA, nkx * njx + nky * njy) / 4.0D);
/* 361 */     this.destPoly.add(new Point.LongPoint(Math.round(sjx + this.delta * (nkx - (addExtra ? (nky * dx) : 0.0D))), Math.round(sjy + this.delta * (nky + (addExtra ? (nkx * dx) : 0.0D))), 0L));
/* 362 */     this.destPoly.add(new Point.LongPoint(Math.round(sjx + this.delta * (njx + (addExtra ? (njy * dx) : 0.0D))), Math.round(sjy + this.delta * (njy - (addExtra ? (njx * dx) : 0.0D))), 0L));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(Paths solution, double delta) {
/* 368 */     solution.clear();
/* 369 */     fixOrientations();
/* 370 */     doOffset(delta);
/*     */     
/* 372 */     DefaultClipper clpr = new DefaultClipper(1);
/* 373 */     clpr.addPaths(this.destPolys, IClipper.PolyType.SUBJECT, true);
/* 374 */     if (delta > 0.0D) {
/* 375 */       clpr.execute(IClipper.ClipType.UNION, solution, IClipper.PolyFillType.POSITIVE, IClipper.PolyFillType.POSITIVE);
/*     */     } else {
/*     */       
/* 378 */       LongRect r = this.destPolys.getBounds();
/* 379 */       Path outer = new Path(4);
/*     */       
/* 381 */       outer.add(new Point.LongPoint(r.left - 10L, r.bottom + 10L, 0L));
/* 382 */       outer.add(new Point.LongPoint(r.right + 10L, r.bottom + 10L, 0L));
/* 383 */       outer.add(new Point.LongPoint(r.right + 10L, r.top - 10L, 0L));
/* 384 */       outer.add(new Point.LongPoint(r.left - 10L, r.top - 10L, 0L));
/*     */       
/* 386 */       clpr.addPath(outer, IClipper.PolyType.SUBJECT, true);
/*     */       
/* 388 */       clpr.execute(IClipper.ClipType.UNION, solution, IClipper.PolyFillType.NEGATIVE, IClipper.PolyFillType.NEGATIVE);
/* 389 */       if (solution.size() > 0) {
/* 390 */         solution.remove(0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(PolyTree solution, double delta) {
/* 398 */     solution.Clear();
/* 399 */     fixOrientations();
/* 400 */     doOffset(delta);
/*     */ 
/*     */     
/* 403 */     DefaultClipper clpr = new DefaultClipper(1);
/* 404 */     clpr.addPaths(this.destPolys, IClipper.PolyType.SUBJECT, true);
/* 405 */     if (delta > 0.0D) {
/* 406 */       clpr.execute(IClipper.ClipType.UNION, solution, IClipper.PolyFillType.POSITIVE, IClipper.PolyFillType.POSITIVE);
/*     */     } else {
/*     */       
/* 409 */       LongRect r = this.destPolys.getBounds();
/* 410 */       Path outer = new Path(4);
/*     */       
/* 412 */       outer.add(new Point.LongPoint(r.left - 10L, r.bottom + 10L, 0L));
/* 413 */       outer.add(new Point.LongPoint(r.right + 10L, r.bottom + 10L, 0L));
/* 414 */       outer.add(new Point.LongPoint(r.right + 10L, r.top - 10L, 0L));
/* 415 */       outer.add(new Point.LongPoint(r.left - 10L, r.top - 10L, 0L));
/*     */       
/* 417 */       clpr.addPath(outer, IClipper.PolyType.SUBJECT, true);
/*     */       
/* 419 */       clpr.execute(IClipper.ClipType.UNION, solution, IClipper.PolyFillType.NEGATIVE, IClipper.PolyFillType.NEGATIVE);
/*     */       
/* 421 */       if (solution.getChildCount() == 1 && ((PolyNode)solution.getChilds().get(0)).getChildCount() > 0) {
/* 422 */         PolyNode outerNode = solution.getChilds().get(0);
/* 423 */         solution.getChilds().set(0, outerNode.getChilds().get(0));
/* 424 */         ((PolyNode)solution.getChilds().get(0)).setParent(solution);
/* 425 */         for (int i = 1; i < outerNode.getChildCount(); i++) {
/* 426 */           solution.addChild(outerNode.getChilds().get(i));
/*     */         }
/*     */       } else {
/*     */         
/* 430 */         solution.Clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fixOrientations() {
/* 440 */     if (this.lowest.getX() >= 0L && !((PolyNode)this.polyNodes.childs.get((int)this.lowest.getX())).getPolygon().orientation()) {
/* 441 */       for (int i = 0; i < this.polyNodes.getChildCount(); i++) {
/* 442 */         PolyNode node = this.polyNodes.childs.get(i);
/* 443 */         if (node.getEndType() == IClipper.EndType.CLOSED_POLYGON || (node.getEndType() == IClipper.EndType.CLOSED_LINE && node.getPolygon().orientation())) {
/* 444 */           Collections.reverse(node.getPolygon());
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 450 */       for (int i = 0; i < this.polyNodes.getChildCount(); i++) {
/* 451 */         PolyNode node = this.polyNodes.childs.get(i);
/* 452 */         if (node.getEndType() == IClipper.EndType.CLOSED_LINE && !node.getPolygon().orientation()) {
/* 453 */           Collections.reverse(node.getPolygon());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void offsetPoint(int j, int[] kV, IClipper.JoinType jointype) {
/* 461 */     int k = kV[0];
/* 462 */     double nkx = ((Point.DoublePoint)this.normals.get(k)).getX();
/* 463 */     double nky = ((Point.DoublePoint)this.normals.get(k)).getY();
/* 464 */     double njy = ((Point.DoublePoint)this.normals.get(j)).getY();
/* 465 */     double njx = ((Point.DoublePoint)this.normals.get(j)).getX();
/* 466 */     long sjx = this.srcPoly.get(j).getX();
/* 467 */     long sjy = this.srcPoly.get(j).getY();
/* 468 */     this.inA = nkx * njy - njx * nky;
/*     */     
/* 470 */     if (Math.abs(this.inA * this.delta) < 1.0D) {
/*     */ 
/*     */       
/* 473 */       double cosA = nkx * njx + njy * nky;
/* 474 */       if (cosA > 0.0D) {
/*     */         
/* 476 */         this.destPoly.add(new Point.LongPoint(Math.round(sjx + nkx * this.delta), Math.round(sjy + nky * this.delta), 0L));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/* 481 */     } else if (this.inA > 1.0D) {
/* 482 */       this.inA = 1.0D;
/*     */     }
/* 484 */     else if (this.inA < -1.0D) {
/* 485 */       this.inA = -1.0D;
/*     */     } 
/*     */     
/* 488 */     if (this.inA * this.delta < 0.0D) {
/* 489 */       this.destPoly.add(new Point.LongPoint(Math.round(sjx + nkx * this.delta), Math.round(sjy + nky * this.delta)));
/* 490 */       this.destPoly.add(this.srcPoly.get(j));
/* 491 */       this.destPoly.add(new Point.LongPoint(Math.round(sjx + njx * this.delta), Math.round(sjy + njy * this.delta)));
/*     */     } else {
/*     */       double r;
/* 494 */       switch (jointype) {
/*     */         case MITER:
/* 496 */           r = 1.0D + njx * nkx + njy * nky;
/* 497 */           if (r >= this.miterLim) {
/* 498 */             doMiter(j, k, r);
/*     */             break;
/*     */           } 
/* 501 */           doSquare(j, k, false);
/*     */           break;
/*     */ 
/*     */         
/*     */         case BEVEL:
/* 506 */           doSquare(j, k, false);
/*     */           break;
/*     */         case ROUND:
/* 509 */           doRound(j, k);
/*     */           break;
/*     */       } 
/*     */     } 
/* 513 */     kV[0] = j;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/ClipperOffset.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */