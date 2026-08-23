/*     */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends ArrayList<Point.LongPoint>
/*     */ {
/*     */   private static final long serialVersionUID = -7120161578077546673L;
/*     */   
/*     */   static class Join
/*     */   {
/*     */     Path.OutPt outPt1;
/*     */     Path.OutPt outPt2;
/*     */     private Point.LongPoint offPt;
/*     */     
/*     */     public Point.LongPoint getOffPt() {
/*  51 */       return this.offPt;
/*     */     }
/*     */     
/*     */     public void setOffPt(Point.LongPoint offPt) {
/*  55 */       this.offPt = offPt;
/*     */     } }
/*     */   
/*     */   static class OutPt {
/*     */     int idx;
/*     */     protected Point.LongPoint pt;
/*     */     
/*     */     public static Path.OutRec getLowerMostRec(Path.OutRec outRec1, Path.OutRec outRec2) {
/*  63 */       if (outRec1.bottomPt == null) {
/*  64 */         outRec1.bottomPt = outRec1.pts.getBottomPt();
/*     */       }
/*  66 */       if (outRec2.bottomPt == null) {
/*  67 */         outRec2.bottomPt = outRec2.pts.getBottomPt();
/*     */       }
/*  69 */       OutPt bPt1 = outRec1.bottomPt;
/*  70 */       OutPt bPt2 = outRec2.bottomPt;
/*  71 */       if (bPt1.getPt().getY() > bPt2.getPt().getY()) {
/*  72 */         return outRec1;
/*     */       }
/*  74 */       if (bPt1.getPt().getY() < bPt2.getPt().getY()) {
/*  75 */         return outRec2;
/*     */       }
/*  77 */       if (bPt1.getPt().getX() < bPt2.getPt().getX()) {
/*  78 */         return outRec1;
/*     */       }
/*  80 */       if (bPt1.getPt().getX() > bPt2.getPt().getX()) {
/*  81 */         return outRec2;
/*     */       }
/*  83 */       if (bPt1.next == bPt1) {
/*  84 */         return outRec2;
/*     */       }
/*  86 */       if (bPt2.next == bPt2) {
/*  87 */         return outRec1;
/*     */       }
/*  89 */       if (isFirstBottomPt(bPt1, bPt2)) {
/*  90 */         return outRec1;
/*     */       }
/*     */       
/*  93 */       return outRec2;
/*     */     }
/*     */     OutPt next; OutPt prev;
/*     */     
/*     */     private static boolean isFirstBottomPt(OutPt btmPt1, OutPt btmPt2) {
/*  98 */       OutPt p = btmPt1.prev;
/*  99 */       while (p.getPt().equals(btmPt1.getPt()) && !p.equals(btmPt1)) {
/* 100 */         p = p.prev;
/*     */       }
/* 102 */       double dx1p = Math.abs(Point.LongPoint.getDeltaX(btmPt1.getPt(), p.getPt()));
/* 103 */       p = btmPt1.next;
/* 104 */       while (p.getPt().equals(btmPt1.getPt()) && !p.equals(btmPt1)) {
/* 105 */         p = p.next;
/*     */       }
/* 107 */       double dx1n = Math.abs(Point.LongPoint.getDeltaX(btmPt1.getPt(), p.getPt()));
/*     */       
/* 109 */       p = btmPt2.prev;
/* 110 */       while (p.getPt().equals(btmPt2.getPt()) && !p.equals(btmPt2)) {
/* 111 */         p = p.prev;
/*     */       }
/* 113 */       double dx2p = Math.abs(Point.LongPoint.getDeltaX(btmPt2.getPt(), p.getPt()));
/* 114 */       p = btmPt2.next;
/* 115 */       while (p.getPt().equals(btmPt2.getPt()) && p.equals(btmPt2)) {
/* 116 */         p = p.next;
/*     */       }
/* 118 */       double dx2n = Math.abs(Point.LongPoint.getDeltaX(btmPt2.getPt(), p.getPt()));
/* 119 */       return ((dx1p >= dx2p && dx1p >= dx2n) || (dx1n >= dx2p && dx1n >= dx2n));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutPt duplicate(boolean InsertAfter) {
/* 129 */       OutPt result = new OutPt();
/* 130 */       result.setPt(new Point.LongPoint(getPt()));
/* 131 */       result.idx = this.idx;
/* 132 */       if (InsertAfter) {
/* 133 */         result.next = this.next;
/* 134 */         result.prev = this;
/* 135 */         this.next.prev = result;
/* 136 */         this.next = result;
/*     */       } else {
/*     */         
/* 139 */         result.prev = this.prev;
/* 140 */         result.next = this;
/* 141 */         this.prev.next = result;
/* 142 */         this.prev = result;
/*     */       } 
/* 144 */       return result;
/*     */     }
/*     */     
/*     */     OutPt getBottomPt() {
/* 148 */       OutPt dups = null;
/* 149 */       OutPt p = this.next;
/* 150 */       OutPt pp = this;
/* 151 */       while (p != pp) {
/* 152 */         if (p.getPt().getY() > pp.getPt().getY()) {
/* 153 */           pp = p;
/* 154 */           dups = null;
/*     */         }
/* 156 */         else if (p.getPt().getY() == pp.getPt().getY() && p.getPt().getX() <= pp.getPt().getX()) {
/* 157 */           if (p.getPt().getX() < pp.getPt().getX()) {
/* 158 */             dups = null;
/* 159 */             pp = p;
/*     */           
/*     */           }
/* 162 */           else if (p.next != pp && p.prev != pp) {
/* 163 */             dups = p;
/*     */           } 
/*     */         } 
/*     */         
/* 167 */         p = p.next;
/*     */       } 
/* 169 */       if (dups != null)
/*     */       {
/* 171 */         while (dups != p) {
/* 172 */           if (!isFirstBottomPt(p, dups)) {
/* 173 */             pp = dups;
/*     */           }
/* 175 */           dups = dups.next;
/* 176 */           while (!dups.getPt().equals(pp.getPt())) {
/* 177 */             dups = dups.next;
/*     */           }
/*     */         } 
/*     */       }
/* 181 */       return pp;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPointCount() {
/* 186 */       int result = 0;
/* 187 */       OutPt p = this;
/*     */       do {
/* 189 */         result++;
/* 190 */         p = p.next;
/*     */       }
/* 192 */       while (p != this && p != null);
/* 193 */       return result;
/*     */     }
/*     */     
/*     */     public Point.LongPoint getPt() {
/* 197 */       return this.pt;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void reversePolyPtLinks() {
/* 204 */       OutPt pp1 = this;
/*     */       do {
/* 206 */         OutPt pp2 = pp1.next;
/* 207 */         pp1.next = pp1.prev;
/* 208 */         pp1.prev = pp2;
/* 209 */         pp1 = pp2;
/*     */       }
/* 211 */       while (pp1 != this);
/*     */     }
/*     */     
/*     */     public void setPt(Point.LongPoint pt) {
/* 215 */       this.pt = pt;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static class Maxima
/*     */   {
/*     */     protected long X;
/*     */     protected Maxima Next;
/*     */     protected Maxima Prev;
/*     */   }
/*     */   
/*     */   static class OutRec
/*     */   {
/*     */     int Idx;
/*     */     boolean isHole;
/*     */     boolean isOpen;
/*     */     OutRec firstLeft;
/*     */     protected Path.OutPt pts;
/*     */     Path.OutPt bottomPt;
/*     */     PolyNode polyNode;
/*     */     
/*     */     public double area() {
/* 238 */       Path.OutPt op = this.pts;
/* 239 */       if (op == null) {
/* 240 */         return 0.0D;
/*     */       }
/* 242 */       double a = 0.0D;
/*     */       while (true) {
/* 244 */         a += (op.prev.getPt().getX() + op.getPt().getX()) * (op.prev.getPt().getY() - op.getPt().getY());
/* 245 */         op = op.next;
/*     */         
/* 247 */         if (op == this.pts) {
/* 248 */           return a * 0.5D;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void fixHoleLinkage() {
/* 254 */       if (this.firstLeft == null || (this.isHole != this.firstLeft.isHole && this.firstLeft.pts != null)) {
/*     */         return;
/*     */       }
/*     */       
/* 258 */       OutRec orfl = this.firstLeft;
/* 259 */       while (orfl != null && (orfl.isHole == this.isHole || orfl.pts == null)) {
/* 260 */         orfl = orfl.firstLeft;
/*     */       }
/* 262 */       this.firstLeft = orfl;
/*     */     }
/*     */     
/*     */     public Path.OutPt getPoints() {
/* 266 */       return this.pts;
/*     */     }
/*     */     
/*     */     public void setPoints(Path.OutPt pts) {
/* 270 */       this.pts = pts;
/*     */     }
/*     */   }
/*     */   
/*     */   private static OutPt excludeOp(OutPt op) {
/* 275 */     OutPt result = op.prev;
/* 276 */     result.next = op.next;
/* 277 */     op.next.prev = result;
/* 278 */     result.idx = 0;
/* 279 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path(Point.LongPoint[] points) {
/* 292 */     this();
/* 293 */     for (Point.LongPoint point : points) {
/* 294 */       add(point);
/*     */     }
/*     */   }
/*     */   
/*     */   public Path(int cnt) {
/* 299 */     super(cnt);
/*     */   }
/*     */   
/*     */   public Path(Collection<? extends Point.LongPoint> c) {
/* 303 */     super(c);
/*     */   }
/*     */   
/*     */   public double area() {
/* 307 */     int cnt = size();
/* 308 */     if (cnt < 3) {
/* 309 */       return 0.0D;
/*     */     }
/* 311 */     double a = 0.0D;
/* 312 */     for (int i = 0, j = cnt - 1; i < cnt; i++) {
/* 313 */       a += (get(j).getX() + get(i).getX()) * (get(j).getY() - get(i).getY());
/* 314 */       j = i;
/*     */     } 
/* 316 */     return -a * 0.5D;
/*     */   }
/*     */   
/*     */   public Path cleanPolygon() {
/* 320 */     return cleanPolygon(1.415D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path cleanPolygon(double distance) {
/* 328 */     int cnt = size();
/*     */     
/* 330 */     if (cnt == 0) {
/* 331 */       return new Path();
/*     */     }
/*     */     
/* 334 */     OutPt[] outPts = new OutPt[cnt]; int i;
/* 335 */     for (i = 0; i < cnt; i++) {
/* 336 */       outPts[i] = new OutPt();
/*     */     }
/*     */     
/* 339 */     for (i = 0; i < cnt; i++) {
/* 340 */       (outPts[i]).pt = get(i);
/* 341 */       (outPts[i]).next = outPts[(i + 1) % cnt];
/* 342 */       (outPts[i]).next.prev = outPts[i];
/* 343 */       (outPts[i]).idx = 0;
/*     */     } 
/*     */     
/* 346 */     double distSqrd = distance * distance;
/* 347 */     OutPt op = outPts[0];
/* 348 */     while (op.idx == 0 && op.next != op.prev) {
/* 349 */       if (Point.arePointsClose(op.pt, op.prev.pt, distSqrd)) {
/* 350 */         op = excludeOp(op);
/* 351 */         cnt--; continue;
/*     */       } 
/* 353 */       if (Point.arePointsClose(op.prev.pt, op.next.pt, distSqrd)) {
/* 354 */         excludeOp(op.next);
/* 355 */         op = excludeOp(op);
/* 356 */         cnt -= 2; continue;
/*     */       } 
/* 358 */       if (Point.slopesNearCollinear(op.prev.pt, op.pt, op.next.pt, distSqrd)) {
/* 359 */         op = excludeOp(op);
/* 360 */         cnt--;
/*     */         continue;
/*     */       } 
/* 363 */       op.idx = 1;
/* 364 */       op = op.next;
/*     */     } 
/*     */ 
/*     */     
/* 368 */     if (cnt < 3) {
/* 369 */       cnt = 0;
/*     */     }
/* 371 */     Path result = new Path(cnt);
/* 372 */     for (int j = 0; j < cnt; j++) {
/* 373 */       result.add(op.pt);
/* 374 */       op = op.next;
/*     */     } 
/* 376 */     outPts = null;
/* 377 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int isPointInPolygon(Point.LongPoint pt) {
/* 384 */     int result = 0;
/* 385 */     int cnt = size();
/* 386 */     if (cnt < 3) {
/* 387 */       return 0;
/*     */     }
/* 389 */     Point.LongPoint ip = get(0);
/* 390 */     for (int i = 1; i <= cnt; i++) {
/* 391 */       Point.LongPoint ipNext = (i == cnt) ? get(0) : get(i);
/* 392 */       if (ipNext.getY() == pt.getY()) {
/* 393 */         if (ipNext.getX() != pt.getX()) { if (ip.getY() == pt.getY()) if (((ipNext.getX() > pt.getX()) ? true : false) == ((ip.getX() < pt.getX()) ? true : false))
/* 394 */               return -1;   } else { return -1; }
/*     */       
/*     */       }
/* 397 */       if (((ip.getY() < pt.getY()) ? true : false) != ((ipNext.getY() < pt.getY()) ? true : false)) {
/* 398 */         if (ip.getX() >= pt.getX()) {
/* 399 */           if (ipNext.getX() > pt.getX()) {
/* 400 */             result = 1 - result;
/*     */           }
/*     */           else {
/*     */             
/* 404 */             double d = (ip.getX() - pt.getX()) * (ipNext.getY() - pt.getY()) - (ipNext.getX() - pt.getX()) * (ip.getY() - pt.getY());
/* 405 */             if (d == 0.0D) {
/* 406 */               return -1;
/*     */             }
/* 408 */             if (((d > 0.0D) ? true : false) == ((ipNext.getY() > ip.getY()) ? true : false)) {
/* 409 */               result = 1 - result;
/*     */             }
/*     */           }
/*     */         
/*     */         }
/* 414 */         else if (ipNext.getX() > pt.getX()) {
/*     */           
/* 416 */           double d = (ip.getX() - pt.getX()) * (ipNext.getY() - pt.getY()) - (ipNext.getX() - pt.getX()) * (ip.getY() - pt.getY());
/* 417 */           if (d == 0.0D) {
/* 418 */             return -1;
/*     */           }
/* 420 */           if (((d > 0.0D) ? true : false) == ((ipNext.getY() > ip.getY()) ? true : false)) {
/* 421 */             result = 1 - result;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 426 */       ip = ipNext;
/*     */     } 
/* 428 */     return result;
/*     */   }
/*     */   
/*     */   public boolean orientation() {
/* 432 */     return (area() >= 0.0D);
/*     */   }
/*     */   
/*     */   public void reverse() {
/* 436 */     Collections.reverse(this);
/*     */   }
/*     */   
/*     */   public Path TranslatePath(Point.LongPoint delta) {
/* 440 */     Path outPath = new Path(size());
/* 441 */     for (int i = 0; i < size(); i++) {
/* 442 */       outPath.add(new Point.LongPoint(get(i).getX() + delta.getX(), get(i).getY() + delta.getY()));
/*     */     }
/* 444 */     return outPath;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/Path.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */