/*      */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultClipper
/*      */   extends ClipperBase
/*      */ {
/*      */   protected final List<Path.OutRec> polyOuts;
/*      */   private IClipper.ClipType clipType;
/*      */   private ClipperBase.Scanbeam scanbeam;
/*      */   private Path.Maxima maxima;
/*      */   private Edge activeEdges;
/*      */   private Edge sortedEdges;
/*      */   private final List<IntersectNode> intersectList;
/*      */   private final Comparator<IntersectNode> intersectNodeComparer;
/*      */   private IClipper.PolyFillType clipFillType;
/*      */   private IClipper.PolyFillType subjFillType;
/*      */   private final List<Path.Join> joins;
/*      */   private final List<Path.Join> ghostJoins;
/*      */   private boolean usingPolyTree;
/*      */   public IClipper.IZFillCallback zFillFunction;
/*      */   private final boolean reverseSolution;
/*      */   private final boolean strictlySimple;
/*      */   
/*      */   private class IntersectNode
/*      */   {
/*      */     Edge edge1;
/*      */     Edge Edge2;
/*      */     private Point.LongPoint pt;
/*      */     
/*      */     private IntersectNode() {}
/*      */     
/*      */     public Point.LongPoint getPt() {
/*   51 */       return this.pt;
/*      */     }
/*      */     
/*      */     public void setPt(Point.LongPoint pt) {
/*   55 */       this.pt = pt;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void getHorzDirection(Edge HorzEdge, IClipper.Direction[] Dir, long[] Left, long[] Right) {
/*   61 */     if (HorzEdge.getBot().getX() < HorzEdge.getTop().getX()) {
/*   62 */       Left[0] = HorzEdge.getBot().getX();
/*   63 */       Right[0] = HorzEdge.getTop().getX();
/*   64 */       Dir[0] = IClipper.Direction.LEFT_TO_RIGHT;
/*      */     } else {
/*      */       
/*   67 */       Left[0] = HorzEdge.getTop().getX();
/*   68 */       Right[0] = HorzEdge.getBot().getX();
/*   69 */       Dir[0] = IClipper.Direction.RIGHT_TO_LEFT;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean getOverlap(long a1, long a2, long b1, long b2, long[] Left, long[] Right) {
/*   74 */     if (a1 < a2) {
/*   75 */       if (b1 < b2) {
/*   76 */         Left[0] = Math.max(a1, b1);
/*   77 */         Right[0] = Math.min(a2, b2);
/*      */       } else {
/*      */         
/*   80 */         Left[0] = Math.max(a1, b2);
/*   81 */         Right[0] = Math.min(a2, b1);
/*      */       }
/*      */     
/*      */     }
/*   85 */     else if (b1 < b2) {
/*   86 */       Left[0] = Math.max(a2, b1);
/*   87 */       Right[0] = Math.min(a1, b2);
/*      */     } else {
/*      */       
/*   90 */       Left[0] = Math.max(a2, b2);
/*   91 */       Right[0] = Math.min(a1, b1);
/*      */     } 
/*      */     
/*   94 */     return (Left[0] < Right[0]);
/*      */   }
/*      */   
/*      */   private static boolean isParam1RightOfParam2(Path.OutRec outRec1, Path.OutRec outRec2) {
/*      */     while (true) {
/*   99 */       outRec1 = outRec1.firstLeft;
/*  100 */       if (outRec1 == outRec2) {
/*  101 */         return true;
/*      */       }
/*      */       
/*  104 */       if (outRec1 == null) {
/*  105 */         return false;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int isPointInPolygon(Point.LongPoint pt, Path.OutPt op) {
/*  112 */     int result = 0;
/*  113 */     Path.OutPt startOp = op;
/*  114 */     long ptx = pt.getX(), pty = pt.getY();
/*  115 */     long poly0x = op.getPt().getX(), poly0y = op.getPt().getY();
/*      */     do {
/*  117 */       op = op.next;
/*  118 */       long poly1x = op.getPt().getX(), poly1y = op.getPt().getY();
/*      */       
/*  120 */       if (poly1y == pty) {
/*  121 */         if (poly1x != ptx) { if (poly0y == pty) if (((poly1x > ptx) ? true : false) == ((poly0x < ptx) ? true : false))
/*  122 */               return -1;   } else { return -1; }
/*      */       
/*      */       }
/*  125 */       if (((poly0y < pty) ? true : false) != ((poly1y < pty) ? true : false)) {
/*  126 */         if (poly0x >= ptx) {
/*  127 */           if (poly1x > ptx) {
/*  128 */             result = 1 - result;
/*      */           } else {
/*      */             
/*  131 */             double d = (poly0x - ptx) * (poly1y - pty) - (poly1x - ptx) * (poly0y - pty);
/*  132 */             if (d == 0.0D) {
/*  133 */               return -1;
/*      */             }
/*  135 */             if (((d > 0.0D) ? true : false) == ((poly1y > poly0y) ? true : false)) {
/*  136 */               result = 1 - result;
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*  141 */         else if (poly1x > ptx) {
/*  142 */           double d = (poly0x - ptx) * (poly1y - pty) - (poly1x - ptx) * (poly0y - pty);
/*  143 */           if (d == 0.0D) {
/*  144 */             return -1;
/*      */           }
/*  146 */           if (((d > 0.0D) ? true : false) == ((poly1y > poly0y) ? true : false)) {
/*  147 */             result = 1 - result;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  152 */       poly0x = poly1x;
/*  153 */       poly0y = poly1y;
/*      */     }
/*  155 */     while (startOp != op);
/*      */     
/*  157 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean joinHorz(Path.OutPt op1, Path.OutPt op1b, Path.OutPt op2, Path.OutPt op2b, Point.LongPoint Pt, boolean DiscardLeft) {
/*  162 */     IClipper.Direction Dir1 = (op1.getPt().getX() > op1b.getPt().getX()) ? IClipper.Direction.RIGHT_TO_LEFT : IClipper.Direction.LEFT_TO_RIGHT;
/*  163 */     IClipper.Direction Dir2 = (op2.getPt().getX() > op2b.getPt().getX()) ? IClipper.Direction.RIGHT_TO_LEFT : IClipper.Direction.LEFT_TO_RIGHT;
/*  164 */     if (Dir1 == Dir2) {
/*  165 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  173 */     if (Dir1 == IClipper.Direction.LEFT_TO_RIGHT) {
/*  174 */       while (op1.next.getPt().getX() <= Pt.getX() && op1.next.getPt().getX() >= op1.getPt().getX() && op1.next.getPt().getY() == Pt.getY()) {
/*  175 */         op1 = op1.next;
/*      */       }
/*  177 */       if (DiscardLeft && op1.getPt().getX() != Pt.getX()) {
/*  178 */         op1 = op1.next;
/*      */       }
/*  180 */       op1b = op1.duplicate(!DiscardLeft);
/*  181 */       if (!op1b.getPt().equals(Pt)) {
/*  182 */         op1 = op1b;
/*  183 */         op1.setPt(Pt);
/*  184 */         op1b = op1.duplicate(!DiscardLeft);
/*      */       } 
/*      */     } else {
/*      */       
/*  188 */       while (op1.next.getPt().getX() >= Pt.getX() && op1.next.getPt().getX() <= op1.getPt().getX() && op1.next.getPt().getY() == Pt.getY()) {
/*  189 */         op1 = op1.next;
/*      */       }
/*  191 */       if (!DiscardLeft && op1.getPt().getX() != Pt.getX()) {
/*  192 */         op1 = op1.next;
/*      */       }
/*  194 */       op1b = op1.duplicate(DiscardLeft);
/*  195 */       if (!op1b.getPt().equals(Pt)) {
/*  196 */         op1 = op1b;
/*  197 */         op1.setPt(Pt);
/*  198 */         op1b = op1.duplicate(DiscardLeft);
/*      */       } 
/*      */     } 
/*      */     
/*  202 */     if (Dir2 == IClipper.Direction.LEFT_TO_RIGHT) {
/*  203 */       while (op2.next.getPt().getX() <= Pt.getX() && op2.next.getPt().getX() >= op2.getPt().getX() && op2.next.getPt().getY() == Pt.getY()) {
/*  204 */         op2 = op2.next;
/*      */       }
/*  206 */       if (DiscardLeft && op2.getPt().getX() != Pt.getX()) {
/*  207 */         op2 = op2.next;
/*      */       }
/*  209 */       op2b = op2.duplicate(!DiscardLeft);
/*  210 */       if (!op2b.getPt().equals(Pt)) {
/*  211 */         op2 = op2b;
/*  212 */         op2.setPt(Pt);
/*  213 */         op2b = op2.duplicate(!DiscardLeft);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  218 */       while (op2.next.getPt().getX() >= Pt.getX() && op2.next.getPt().getX() <= op2.getPt().getX() && op2.next.getPt().getY() == Pt.getY()) {
/*  219 */         op2 = op2.next;
/*      */       }
/*  221 */       if (!DiscardLeft && op2.getPt().getX() != Pt.getX()) {
/*  222 */         op2 = op2.next;
/*      */       }
/*  224 */       op2b = op2.duplicate(DiscardLeft);
/*  225 */       if (!op2b.getPt().equals(Pt)) {
/*  226 */         op2 = op2b;
/*  227 */         op2.setPt(Pt);
/*  228 */         op2b = op2.duplicate(DiscardLeft);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  234 */     if (((Dir1 == IClipper.Direction.LEFT_TO_RIGHT)) == DiscardLeft) {
/*  235 */       op1.prev = op2;
/*  236 */       op2.next = op1;
/*  237 */       op1b.next = op2b;
/*  238 */       op2b.prev = op1b;
/*      */     } else {
/*      */       
/*  241 */       op1.next = op2;
/*  242 */       op2.prev = op1;
/*  243 */       op1b.prev = op2b;
/*  244 */       op2b.next = op1b;
/*      */     } 
/*  246 */     return true;
/*      */   }
/*      */   
/*      */   private boolean joinPoints(Path.Join j, Path.OutRec outRec1, Path.OutRec outRec2) {
/*  250 */     Path.OutPt op1 = j.outPt1;
/*  251 */     Path.OutPt op2 = j.outPt2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  260 */     boolean isHorizontal = (j.outPt1.getPt().getY() == j.getOffPt().getY());
/*      */     
/*  262 */     if (isHorizontal && j.getOffPt().equals(j.outPt1.getPt()) && j.getOffPt().equals(j.outPt2.getPt())) {
/*      */       
/*  264 */       if (outRec1 != outRec2) {
/*  265 */         return false;
/*      */       }
/*  267 */       Path.OutPt outPt1 = j.outPt1.next;
/*  268 */       while (outPt1 != op1 && outPt1.getPt().equals(j.getOffPt())) {
/*  269 */         outPt1 = outPt1.next;
/*      */       }
/*  271 */       boolean reverse1 = (outPt1.getPt().getY() > j.getOffPt().getY());
/*  272 */       Path.OutPt outPt2 = j.outPt2.next;
/*  273 */       while (outPt2 != op2 && outPt2.getPt().equals(j.getOffPt())) {
/*  274 */         outPt2 = outPt2.next;
/*      */       }
/*  276 */       boolean reverse2 = (outPt2.getPt().getY() > j.getOffPt().getY());
/*  277 */       if (reverse1 == reverse2) {
/*  278 */         return false;
/*      */       }
/*  280 */       if (reverse1) {
/*  281 */         outPt1 = op1.duplicate(false);
/*  282 */         outPt2 = op2.duplicate(true);
/*  283 */         op1.prev = op2;
/*  284 */         op2.next = op1;
/*  285 */         outPt1.next = outPt2;
/*  286 */         outPt2.prev = outPt1;
/*  287 */         j.outPt1 = op1;
/*  288 */         j.outPt2 = outPt1;
/*  289 */         return true;
/*      */       } 
/*      */       
/*  292 */       outPt1 = op1.duplicate(true);
/*  293 */       outPt2 = op2.duplicate(false);
/*  294 */       op1.next = op2;
/*  295 */       op2.prev = op1;
/*  296 */       outPt1.prev = outPt2;
/*  297 */       outPt2.next = outPt1;
/*  298 */       j.outPt1 = op1;
/*  299 */       j.outPt2 = outPt1;
/*  300 */       return true;
/*      */     } 
/*      */     
/*  303 */     if (isHorizontal) {
/*      */       Point.LongPoint Pt;
/*      */       
/*      */       boolean DiscardLeftSide;
/*  307 */       Path.OutPt outPt1 = op1;
/*  308 */       while (op1.prev.getPt().getY() == op1.getPt().getY() && op1.prev != outPt1 && op1.prev != op2) {
/*  309 */         op1 = op1.prev;
/*      */       }
/*  311 */       while (outPt1.next.getPt().getY() == outPt1.getPt().getY() && outPt1.next != op1 && outPt1.next != op2) {
/*  312 */         outPt1 = outPt1.next;
/*      */       }
/*  314 */       if (outPt1.next == op1 || outPt1.next == op2) {
/*  315 */         return false;
/*      */       }
/*      */       
/*  318 */       Path.OutPt outPt2 = op2;
/*  319 */       while (op2.prev.getPt().getY() == op2.getPt().getY() && op2.prev != outPt2 && op2.prev != outPt1) {
/*  320 */         op2 = op2.prev;
/*      */       }
/*  322 */       while (outPt2.next.getPt().getY() == outPt2.getPt().getY() && outPt2.next != op2 && outPt2.next != op1) {
/*  323 */         outPt2 = outPt2.next;
/*      */       }
/*  325 */       if (outPt2.next == op2 || outPt2.next == op1) {
/*  326 */         return false;
/*      */       }
/*      */       
/*  329 */       long[] LeftV = new long[1], RightV = new long[1];
/*      */       
/*  331 */       if (!getOverlap(op1.getPt().getX(), outPt1.getPt().getX(), op2.getPt().getX(), outPt2.getPt().getX(), LeftV, RightV)) {
/*  332 */         return false;
/*      */       }
/*  334 */       long Left = LeftV[0];
/*  335 */       long Right = RightV[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  342 */       if (op1.getPt().getX() >= Left && op1.getPt().getX() <= Right) {
/*  343 */         Pt = new Point.LongPoint(op1.getPt());
/*  344 */         DiscardLeftSide = (op1.getPt().getX() > outPt1.getPt().getX());
/*      */       }
/*  346 */       else if (op2.getPt().getX() >= Left && op2.getPt().getX() <= Right) {
/*  347 */         Pt = new Point.LongPoint(op2.getPt());
/*  348 */         DiscardLeftSide = (op2.getPt().getX() > outPt2.getPt().getX());
/*      */       }
/*  350 */       else if (outPt1.getPt().getX() >= Left && outPt1.getPt().getX() <= Right) {
/*  351 */         Pt = new Point.LongPoint(outPt1.getPt());
/*  352 */         DiscardLeftSide = (outPt1.getPt().getX() > op1.getPt().getX());
/*      */       } else {
/*      */         
/*  355 */         Pt = new Point.LongPoint(outPt2.getPt());
/*  356 */         DiscardLeftSide = (outPt2.getPt().getX() > op2.getPt().getX());
/*      */       } 
/*  358 */       j.outPt1 = op1;
/*  359 */       j.outPt2 = op2;
/*  360 */       return joinHorz(op1, outPt1, op2, outPt2, Pt, DiscardLeftSide);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  368 */     Path.OutPt op1b = op1.next;
/*  369 */     while (op1b.getPt().equals(op1.getPt()) && op1b != op1) {
/*  370 */       op1b = op1b.next;
/*      */     }
/*  372 */     boolean Reverse1 = (op1b.getPt().getY() > op1.getPt().getY() || !Point.slopesEqual(op1.getPt(), op1b.getPt(), j.getOffPt(), this.useFullRange));
/*  373 */     if (Reverse1) {
/*  374 */       op1b = op1.prev;
/*  375 */       while (op1b.getPt().equals(op1.getPt()) && op1b != op1) {
/*  376 */         op1b = op1b.prev;
/*      */       }
/*  378 */       if (op1b.getPt().getY() > op1.getPt().getY() || !Point.slopesEqual(op1.getPt(), op1b.getPt(), j.getOffPt(), this.useFullRange)) {
/*  379 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  383 */     Path.OutPt op2b = op2.next;
/*  384 */     while (op2b.getPt().equals(op2.getPt()) && op2b != op2) {
/*  385 */       op2b = op2b.next;
/*      */     }
/*  387 */     boolean Reverse2 = (op2b.getPt().getY() > op2.getPt().getY() || !Point.slopesEqual(op2.getPt(), op2b.getPt(), j.getOffPt(), this.useFullRange));
/*  388 */     if (Reverse2) {
/*  389 */       op2b = op2.prev;
/*  390 */       while (op2b.getPt().equals(op2.getPt()) && op2b != op2) {
/*  391 */         op2b = op2b.prev;
/*      */       }
/*  393 */       if (op2b.getPt().getY() > op2.getPt().getY() || !Point.slopesEqual(op2.getPt(), op2b.getPt(), j.getOffPt(), this.useFullRange)) {
/*  394 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  398 */     if (op1b == op1 || op2b == op2 || op1b == op2b || (outRec1 == outRec2 && Reverse1 == Reverse2)) {
/*  399 */       return false;
/*      */     }
/*      */     
/*  402 */     if (Reverse1) {
/*  403 */       op1b = op1.duplicate(false);
/*  404 */       op2b = op2.duplicate(true);
/*  405 */       op1.prev = op2;
/*  406 */       op2.next = op1;
/*  407 */       op1b.next = op2b;
/*  408 */       op2b.prev = op1b;
/*  409 */       j.outPt1 = op1;
/*  410 */       j.outPt2 = op1b;
/*  411 */       return true;
/*      */     } 
/*      */     
/*  414 */     op1b = op1.duplicate(true);
/*  415 */     op2b = op2.duplicate(false);
/*  416 */     op1.next = op2;
/*  417 */     op2.prev = op1;
/*  418 */     op1b.prev = op2b;
/*  419 */     op2b.next = op1b;
/*  420 */     j.outPt1 = op1;
/*  421 */     j.outPt2 = op1b;
/*  422 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Paths minkowski(Path pattern, Path path, boolean IsSum, boolean IsClosed) {
/*  428 */     int delta = IsClosed ? 1 : 0;
/*  429 */     int polyCnt = pattern.size();
/*  430 */     int pathCnt = path.size();
/*  431 */     Paths result = new Paths(pathCnt);
/*  432 */     if (IsSum) {
/*  433 */       for (int j = 0; j < pathCnt; j++) {
/*  434 */         Path p = new Path(polyCnt);
/*  435 */         for (Point.LongPoint ip : pattern) {
/*  436 */           p.add(new Point.LongPoint(path.get(j).getX() + ip.getX(), path.get(j).getY() + ip.getY(), 0L));
/*      */         }
/*  438 */         result.add(p);
/*      */       } 
/*      */     } else {
/*      */       
/*  442 */       for (int j = 0; j < pathCnt; j++) {
/*  443 */         Path p = new Path(polyCnt);
/*  444 */         for (Point.LongPoint ip : pattern) {
/*  445 */           p.add(new Point.LongPoint(path.get(j).getX() - ip.getX(), path.get(j).getY() - ip.getY(), 0L));
/*      */         }
/*  447 */         result.add(p);
/*      */       } 
/*      */     } 
/*      */     
/*  451 */     Paths quads = new Paths((pathCnt + delta) * (polyCnt + 1));
/*  452 */     for (int i = 0; i < pathCnt - 1 + delta; i++) {
/*  453 */       for (int j = 0; j < polyCnt; j++) {
/*  454 */         Path quad = new Path(4);
/*  455 */         quad.add(result.get(i % pathCnt).get(j % polyCnt));
/*  456 */         quad.add(result.get((i + 1) % pathCnt).get(j % polyCnt));
/*  457 */         quad.add(result.get((i + 1) % pathCnt).get((j + 1) % polyCnt));
/*  458 */         quad.add(result.get(i % pathCnt).get((j + 1) % polyCnt));
/*  459 */         if (!quad.orientation()) {
/*  460 */           Collections.reverse(quad);
/*      */         }
/*  462 */         quads.add(quad);
/*      */       } 
/*      */     } 
/*  465 */     return quads;
/*      */   }
/*      */   
/*      */   public static Paths minkowskiDiff(Path poly1, Path poly2) {
/*  469 */     Paths paths = minkowski(poly1, poly2, false, true);
/*  470 */     DefaultClipper c = new DefaultClipper();
/*  471 */     c.addPaths(paths, IClipper.PolyType.SUBJECT, true);
/*  472 */     c.execute(IClipper.ClipType.UNION, paths, IClipper.PolyFillType.NON_ZERO, IClipper.PolyFillType.NON_ZERO);
/*  473 */     return paths;
/*      */   }
/*      */   
/*      */   public static Paths minkowskiSum(Path pattern, Path path, boolean pathIsClosed) {
/*  477 */     Paths paths = minkowski(pattern, path, true, pathIsClosed);
/*  478 */     DefaultClipper c = new DefaultClipper();
/*  479 */     c.addPaths(paths, IClipper.PolyType.SUBJECT, true);
/*  480 */     c.execute(IClipper.ClipType.UNION, paths, IClipper.PolyFillType.NON_ZERO, IClipper.PolyFillType.NON_ZERO);
/*  481 */     return paths;
/*      */   }
/*      */   
/*      */   public static Paths minkowskiSum(Path pattern, Paths paths, boolean pathIsClosed) {
/*  485 */     Paths solution = new Paths();
/*  486 */     DefaultClipper c = new DefaultClipper();
/*  487 */     for (int i = 0; i < paths.size(); i++) {
/*  488 */       Paths tmp = minkowski(pattern, paths.get(i), true, pathIsClosed);
/*  489 */       c.addPaths(tmp, IClipper.PolyType.SUBJECT, true);
/*  490 */       if (pathIsClosed) {
/*  491 */         Path path = paths.get(i).TranslatePath(pattern.get(0));
/*  492 */         c.addPath(path, IClipper.PolyType.CLIP, true);
/*      */       } 
/*      */     } 
/*  495 */     c.execute(IClipper.ClipType.UNION, solution, IClipper.PolyFillType.NON_ZERO, IClipper.PolyFillType.NON_ZERO);
/*  496 */     return solution;
/*      */   }
/*      */   
/*      */   private static boolean poly2ContainsPoly1(Path.OutPt outPt1, Path.OutPt outPt2) {
/*  500 */     Path.OutPt op = outPt1;
/*      */     
/*      */     while (true) {
/*  503 */       int res = isPointInPolygon(op.getPt(), outPt2);
/*  504 */       if (res >= 0) {
/*  505 */         return (res > 0);
/*      */       }
/*  507 */       op = op.next;
/*      */       
/*  509 */       if (op == outPt1) {
/*  510 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Paths simplifyPolygon(Path poly) {
/*  518 */     return simplifyPolygon(poly, IClipper.PolyFillType.EVEN_ODD);
/*      */   }
/*      */   
/*      */   public static Paths simplifyPolygon(Path poly, IClipper.PolyFillType fillType) {
/*  522 */     Paths result = new Paths();
/*  523 */     DefaultClipper c = new DefaultClipper(2);
/*      */     
/*  525 */     c.addPath(poly, IClipper.PolyType.SUBJECT, true);
/*  526 */     c.execute(IClipper.ClipType.UNION, result, fillType, fillType);
/*  527 */     return result;
/*      */   }
/*      */   
/*      */   public static Paths simplifyPolygons(Paths polys) {
/*  531 */     return simplifyPolygons(polys, IClipper.PolyFillType.EVEN_ODD);
/*      */   }
/*      */   
/*      */   public static Paths simplifyPolygons(Paths polys, IClipper.PolyFillType fillType) {
/*  535 */     Paths result = new Paths();
/*  536 */     DefaultClipper c = new DefaultClipper(2);
/*      */     
/*  538 */     c.addPaths(polys, IClipper.PolyType.SUBJECT, true);
/*  539 */     c.execute(IClipper.ClipType.UNION, result, fillType, fillType);
/*  540 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  585 */   private static final Logger LOGGER = Logger.getLogger(DefaultClipper.class.getName());
/*      */   
/*      */   public DefaultClipper() {
/*  588 */     this(0);
/*      */   }
/*      */ 
/*      */   
/*      */   public DefaultClipper(int InitOptions) {
/*  593 */     super(((0x4 & InitOptions) != 0));
/*  594 */     this.scanbeam = null;
/*  595 */     this.maxima = null;
/*  596 */     this.activeEdges = null;
/*  597 */     this.sortedEdges = null;
/*  598 */     this.intersectList = new ArrayList<>();
/*  599 */     this.intersectNodeComparer = new Comparator<IntersectNode>() {
/*      */         public int compare(DefaultClipper.IntersectNode o1, DefaultClipper.IntersectNode o2) {
/*  601 */           long i = o2.getPt().getY() - o1.getPt().getY();
/*  602 */           if (i > 0L) {
/*  603 */             return 1;
/*      */           }
/*  605 */           if (i < 0L) {
/*  606 */             return -1;
/*      */           }
/*      */           
/*  609 */           return 0;
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  614 */     this.usingPolyTree = false;
/*  615 */     this.polyOuts = new ArrayList<>();
/*  616 */     this.joins = new ArrayList<>();
/*  617 */     this.ghostJoins = new ArrayList<>();
/*  618 */     this.reverseSolution = ((0x1 & InitOptions) != 0);
/*  619 */     this.strictlySimple = ((0x2 & InitOptions) != 0);
/*      */     
/*  621 */     this.zFillFunction = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertScanbeam(long Y) {
/*  629 */     if (this.scanbeam == null) {
/*      */       
/*  631 */       this.scanbeam = new ClipperBase.Scanbeam(this);
/*  632 */       this.scanbeam.next = null;
/*  633 */       this.scanbeam.y = Y;
/*      */     }
/*  635 */     else if (Y > this.scanbeam.y) {
/*      */       
/*  637 */       ClipperBase.Scanbeam newSb = new ClipperBase.Scanbeam(this);
/*  638 */       newSb.y = Y;
/*  639 */       newSb.next = this.scanbeam;
/*  640 */       this.scanbeam = newSb;
/*      */     }
/*      */     else {
/*      */       
/*  644 */       ClipperBase.Scanbeam sb2 = this.scanbeam;
/*  645 */       for (; sb2.next != null && Y <= sb2.next.y; sb2 = sb2.next);
/*  646 */       if (Y == sb2.y)
/*  647 */         return;  ClipperBase.Scanbeam newSb = new ClipperBase.Scanbeam(this);
/*  648 */       newSb.y = Y;
/*  649 */       newSb.next = sb2.next;
/*  650 */       sb2.next = newSb;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void InsertMaxima(long X) {
/*  658 */     Path.Maxima newMax = new Path.Maxima();
/*  659 */     newMax.X = X;
/*  660 */     if (this.maxima == null) {
/*      */       
/*  662 */       this.maxima = newMax;
/*  663 */       this.maxima.Next = null;
/*  664 */       this.maxima.Prev = null;
/*      */     }
/*  666 */     else if (X < this.maxima.X) {
/*      */       
/*  668 */       newMax.Next = this.maxima;
/*  669 */       newMax.Prev = null;
/*  670 */       this.maxima = newMax;
/*      */     }
/*      */     else {
/*      */       
/*  674 */       Path.Maxima m = this.maxima;
/*  675 */       for (; m.Next != null && X >= m.Next.X; m = m.Next);
/*  676 */       if (X == m.X)
/*      */         return; 
/*  678 */       newMax.Next = m.Next;
/*  679 */       newMax.Prev = m;
/*  680 */       if (m.Next != null) m.Next.Prev = newMax; 
/*  681 */       m.Next = newMax;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addEdgeToSEL(Edge edge) {
/*  687 */     LOGGER.entering(DefaultClipper.class.getName(), "addEdgeToSEL");
/*      */ 
/*      */ 
/*      */     
/*  691 */     if (this.sortedEdges == null) {
/*  692 */       this.sortedEdges = edge;
/*  693 */       edge.prevInSEL = null;
/*  694 */       edge.nextInSEL = null;
/*      */     } else {
/*      */       
/*  697 */       edge.nextInSEL = this.sortedEdges;
/*  698 */       edge.prevInSEL = null;
/*  699 */       this.sortedEdges.prevInSEL = edge;
/*  700 */       this.sortedEdges = edge;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addGhostJoin(Path.OutPt Op, Point.LongPoint OffPt) {
/*  705 */     Path.Join j = new Path.Join();
/*  706 */     j.outPt1 = Op;
/*  707 */     j.setOffPt(OffPt);
/*  708 */     this.ghostJoins.add(j);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addJoin(Path.OutPt Op1, Path.OutPt Op2, Point.LongPoint OffPt) {
/*  714 */     LOGGER.entering(DefaultClipper.class.getName(), "addJoin");
/*  715 */     Path.Join j = new Path.Join();
/*  716 */     j.outPt1 = Op1;
/*  717 */     j.outPt2 = Op2;
/*  718 */     j.setOffPt(OffPt);
/*  719 */     this.joins.add(j);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addLocalMaxPoly(Edge e1, Edge e2, Point.LongPoint pt) {
/*  725 */     addOutPt(e1, pt);
/*  726 */     if (e2.windDelta == 0) {
/*  727 */       addOutPt(e2, pt);
/*      */     }
/*  729 */     if (e1.outIdx == e2.outIdx) {
/*  730 */       e1.outIdx = -1;
/*  731 */       e2.outIdx = -1;
/*      */     }
/*  733 */     else if (e1.outIdx < e2.outIdx) {
/*  734 */       appendPolygon(e1, e2);
/*      */     } else {
/*      */       
/*  737 */       appendPolygon(e2, e1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private Path.OutPt addLocalMinPoly(Edge e1, Edge e2, Point.LongPoint pt) {
/*      */     Path.OutPt result;
/*      */     Edge e, prevE;
/*  744 */     LOGGER.entering(DefaultClipper.class.getName(), "addLocalMinPoly");
/*      */ 
/*      */     
/*  747 */     if (e2.isHorizontal() || e1.deltaX > e2.deltaX) {
/*  748 */       result = addOutPt(e1, pt);
/*  749 */       e2.outIdx = e1.outIdx;
/*  750 */       e1.side = Edge.Side.LEFT;
/*  751 */       e2.side = Edge.Side.RIGHT;
/*  752 */       e = e1;
/*  753 */       if (e.prevInAEL == e2) {
/*  754 */         prevE = e2.prevInAEL;
/*      */       } else {
/*      */         
/*  757 */         prevE = e.prevInAEL;
/*      */       } 
/*      */     } else {
/*      */       
/*  761 */       result = addOutPt(e2, pt);
/*  762 */       e1.outIdx = e2.outIdx;
/*  763 */       e1.side = Edge.Side.RIGHT;
/*  764 */       e2.side = Edge.Side.LEFT;
/*  765 */       e = e2;
/*  766 */       if (e.prevInAEL == e1) {
/*  767 */         prevE = e1.prevInAEL;
/*      */       } else {
/*      */         
/*  770 */         prevE = e.prevInAEL;
/*      */       } 
/*      */     } 
/*      */     
/*  774 */     if (prevE != null && prevE.outIdx >= 0 && 
/*  775 */       Edge.topX(prevE, pt.getY()) == Edge.topX(e, pt.getY()) && 
/*  776 */       Edge.slopesEqual(e, prevE, this.useFullRange) && e.windDelta != 0 && prevE.windDelta != 0) {
/*      */       
/*  778 */       Path.OutPt outPt = addOutPt(prevE, pt);
/*  779 */       addJoin(result, outPt, e.getTop());
/*      */     } 
/*  781 */     return result;
/*      */   }
/*      */   
/*      */   private Path.OutPt addOutPt(Edge e, Point.LongPoint pt) {
/*  785 */     LOGGER.entering(DefaultClipper.class.getName(), "addOutPt");
/*  786 */     if (e.outIdx < 0) {
/*      */       
/*  788 */       Path.OutRec outRec1 = createOutRec();
/*  789 */       outRec1.isOpen = (e.windDelta == 0);
/*  790 */       Path.OutPt outPt = new Path.OutPt();
/*  791 */       outRec1.pts = outPt;
/*  792 */       outPt.idx = outRec1.Idx;
/*  793 */       outPt.pt = pt;
/*  794 */       outPt.next = outPt;
/*  795 */       outPt.prev = outPt;
/*  796 */       if (!outRec1.isOpen)
/*  797 */         setHoleState(e, outRec1); 
/*  798 */       e.outIdx = outRec1.Idx;
/*  799 */       return outPt;
/*      */     } 
/*      */     
/*  802 */     Path.OutRec outRec = this.polyOuts.get(e.outIdx);
/*      */     
/*  804 */     Path.OutPt op = outRec.getPoints();
/*  805 */     boolean ToFront = (e.side == Edge.Side.LEFT);
/*  806 */     LOGGER.finest("op=" + op.getPointCount());
/*  807 */     LOGGER.finest(ToFront + " " + pt + " " + op.getPt());
/*  808 */     if (ToFront && pt.equals(op.getPt())) {
/*  809 */       return op;
/*      */     }
/*  811 */     if (!ToFront && pt.equals(op.prev.getPt())) {
/*  812 */       return op.prev;
/*      */     }
/*      */     
/*  815 */     Path.OutPt newOp = new Path.OutPt();
/*  816 */     newOp.idx = outRec.Idx;
/*  817 */     newOp.setPt(new Point.LongPoint(pt));
/*  818 */     newOp.next = op;
/*  819 */     newOp.prev = op.prev;
/*  820 */     newOp.prev.next = newOp;
/*  821 */     op.prev = newOp;
/*  822 */     if (ToFront) {
/*  823 */       outRec.setPoints(newOp);
/*      */     }
/*  825 */     return newOp;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Path.OutPt GetLastOutPt(Edge e) {
/*  831 */     Path.OutRec outRec = this.polyOuts.get(e.outIdx);
/*  832 */     if (e.side == Edge.Side.LEFT) {
/*  833 */       return outRec.pts;
/*      */     }
/*  835 */     return outRec.pts.prev;
/*      */   }
/*      */   private void appendPolygon(Edge e1, Edge e2) {
/*      */     Path.OutRec holeStateRec;
/*      */     Edge.Side side;
/*  840 */     LOGGER.entering(DefaultClipper.class.getName(), "appendPolygon");
/*      */ 
/*      */     
/*  843 */     Path.OutRec outRec1 = this.polyOuts.get(e1.outIdx);
/*  844 */     Path.OutRec outRec2 = this.polyOuts.get(e2.outIdx);
/*  845 */     LOGGER.finest("" + e1.outIdx);
/*  846 */     LOGGER.finest("" + e2.outIdx);
/*      */ 
/*      */     
/*  849 */     if (isParam1RightOfParam2(outRec1, outRec2)) {
/*  850 */       holeStateRec = outRec2;
/*      */     }
/*  852 */     else if (isParam1RightOfParam2(outRec2, outRec1)) {
/*  853 */       holeStateRec = outRec1;
/*      */     } else {
/*      */       
/*  856 */       holeStateRec = Path.OutPt.getLowerMostRec(outRec1, outRec2);
/*      */     } 
/*      */     
/*  859 */     Path.OutPt p1_lft = outRec1.getPoints();
/*  860 */     Path.OutPt p1_rt = p1_lft.prev;
/*  861 */     Path.OutPt p2_lft = outRec2.getPoints();
/*  862 */     Path.OutPt p2_rt = p2_lft.prev;
/*      */     
/*  864 */     LOGGER.finest("p1_lft.getPointCount() = " + p1_lft.getPointCount());
/*  865 */     LOGGER.finest("p1_rt.getPointCount() = " + p1_rt.getPointCount());
/*  866 */     LOGGER.finest("p2_lft.getPointCount() = " + p2_lft.getPointCount());
/*  867 */     LOGGER.finest("p2_rt.getPointCount() = " + p2_rt.getPointCount());
/*      */ 
/*      */ 
/*      */     
/*  871 */     if (e1.side == Edge.Side.LEFT) {
/*  872 */       if (e2.side == Edge.Side.LEFT) {
/*      */         
/*  874 */         p2_lft.reversePolyPtLinks();
/*  875 */         p2_lft.next = p1_lft;
/*  876 */         p1_lft.prev = p2_lft;
/*  877 */         p1_rt.next = p2_rt;
/*  878 */         p2_rt.prev = p1_rt;
/*  879 */         outRec1.setPoints(p2_rt);
/*      */       }
/*      */       else {
/*      */         
/*  883 */         p2_rt.next = p1_lft;
/*  884 */         p1_lft.prev = p2_rt;
/*  885 */         p2_lft.prev = p1_rt;
/*  886 */         p1_rt.next = p2_lft;
/*  887 */         outRec1.setPoints(p2_lft);
/*      */       } 
/*  889 */       side = Edge.Side.LEFT;
/*      */     } else {
/*      */       
/*  892 */       if (e2.side == Edge.Side.RIGHT) {
/*      */         
/*  894 */         p2_lft.reversePolyPtLinks();
/*  895 */         p1_rt.next = p2_rt;
/*  896 */         p2_rt.prev = p1_rt;
/*  897 */         p2_lft.next = p1_lft;
/*  898 */         p1_lft.prev = p2_lft;
/*      */       }
/*      */       else {
/*      */         
/*  902 */         p1_rt.next = p2_lft;
/*  903 */         p2_lft.prev = p1_rt;
/*  904 */         p1_lft.prev = p2_rt;
/*  905 */         p2_rt.next = p1_lft;
/*      */       } 
/*  907 */       side = Edge.Side.RIGHT;
/*      */     } 
/*  909 */     outRec1.bottomPt = null;
/*  910 */     if (holeStateRec.equals(outRec2)) {
/*  911 */       if (outRec2.firstLeft != outRec1) {
/*  912 */         outRec1.firstLeft = outRec2.firstLeft;
/*      */       }
/*  914 */       outRec1.isHole = outRec2.isHole;
/*      */     } 
/*  916 */     outRec2.setPoints(null);
/*  917 */     outRec2.bottomPt = null;
/*      */     
/*  919 */     outRec2.firstLeft = outRec1;
/*      */     
/*  921 */     int OKIdx = e1.outIdx;
/*  922 */     int ObsoleteIdx = e2.outIdx;
/*      */     
/*  924 */     e1.outIdx = -1;
/*  925 */     e2.outIdx = -1;
/*      */     
/*  927 */     Edge e = this.activeEdges;
/*  928 */     while (e != null) {
/*  929 */       if (e.outIdx == ObsoleteIdx) {
/*  930 */         e.outIdx = OKIdx;
/*  931 */         e.side = side;
/*      */         break;
/*      */       } 
/*  934 */       e = e.nextInAEL;
/*      */     } 
/*  936 */     outRec2.Idx = outRec1.Idx;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildIntersectList(long topY) {
/*  942 */     if (this.activeEdges == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  947 */     Edge e = this.activeEdges;
/*  948 */     this.sortedEdges = e;
/*  949 */     while (e != null) {
/*  950 */       e.prevInSEL = e.prevInAEL;
/*  951 */       e.nextInSEL = e.nextInAEL;
/*  952 */       e.getCurrent().setX(Long.valueOf(Edge.topX(e, topY)));
/*  953 */       e = e.nextInAEL;
/*      */     } 
/*      */ 
/*      */     
/*  957 */     boolean isModified = true;
/*  958 */     while (isModified && this.sortedEdges != null) {
/*  959 */       isModified = false;
/*  960 */       e = this.sortedEdges;
/*  961 */       while (e.nextInSEL != null) {
/*  962 */         Edge eNext = e.nextInSEL;
/*  963 */         Point.LongPoint[] pt = new Point.LongPoint[1];
/*  964 */         if (e.getCurrent().getX() > eNext.getCurrent().getX()) {
/*  965 */           intersectPoint(e, eNext, pt);
/*  966 */           IntersectNode newNode = new IntersectNode();
/*  967 */           newNode.edge1 = e;
/*  968 */           newNode.Edge2 = eNext;
/*  969 */           newNode.setPt(pt[0]);
/*  970 */           this.intersectList.add(newNode);
/*      */           
/*  972 */           swapPositionsInSEL(e, eNext);
/*  973 */           isModified = true;
/*      */           continue;
/*      */         } 
/*  976 */         e = eNext;
/*      */       } 
/*      */       
/*  979 */       if (e.prevInSEL != null) {
/*  980 */         e.prevInSEL.nextInSEL = null;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  986 */     this.sortedEdges = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildResult(Paths polyg) {
/*  992 */     polyg.clear();
/*  993 */     for (int i = 0; i < this.polyOuts.size(); i++) {
/*  994 */       Path.OutRec outRec = this.polyOuts.get(i);
/*  995 */       if (outRec.getPoints() != null) {
/*      */ 
/*      */         
/*  998 */         Path.OutPt p = (outRec.getPoints()).prev;
/*  999 */         int cnt = p.getPointCount();
/* 1000 */         LOGGER.finest("cnt = " + cnt);
/* 1001 */         if (cnt >= 2) {
/*      */ 
/*      */           
/* 1004 */           Path pg = new Path(cnt);
/* 1005 */           for (int j = 0; j < cnt; j++) {
/* 1006 */             pg.add(p.getPt());
/* 1007 */             p = p.prev;
/*      */           } 
/* 1009 */           polyg.add(pg);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   } private void buildResult2(PolyTree polytree) {
/* 1014 */     polytree.Clear();
/*      */     
/*      */     int i;
/* 1017 */     for (i = 0; i < this.polyOuts.size(); i++) {
/* 1018 */       Path.OutRec outRec = this.polyOuts.get(i);
/* 1019 */       int cnt = (outRec.getPoints() != null) ? outRec.getPoints().getPointCount() : 0;
/* 1020 */       if ((!outRec.isOpen || cnt >= 2) && (outRec.isOpen || cnt >= 3)) {
/*      */ 
/*      */         
/* 1023 */         outRec.fixHoleLinkage();
/* 1024 */         PolyNode pn = new PolyNode();
/* 1025 */         polytree.getAllPolys().add(pn);
/* 1026 */         outRec.polyNode = pn;
/* 1027 */         Path.OutPt op = (outRec.getPoints()).prev;
/* 1028 */         for (int j = 0; j < cnt; j++) {
/* 1029 */           pn.getPolygon().add(op.getPt());
/* 1030 */           op = op.prev;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1035 */     for (i = 0; i < this.polyOuts.size(); i++) {
/* 1036 */       Path.OutRec outRec = this.polyOuts.get(i);
/* 1037 */       if (outRec.polyNode != null)
/*      */       {
/*      */         
/* 1040 */         if (outRec.isOpen) {
/* 1041 */           outRec.polyNode.setOpen(true);
/* 1042 */           polytree.addChild(outRec.polyNode);
/*      */         }
/* 1044 */         else if (outRec.firstLeft != null && outRec.firstLeft.polyNode != null) {
/* 1045 */           outRec.firstLeft.polyNode.addChild(outRec.polyNode);
/*      */         } else {
/*      */           
/* 1048 */           polytree.addChild(outRec.polyNode);
/*      */         }  } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void copyAELToSEL() {
/* 1054 */     Edge e = this.activeEdges;
/* 1055 */     this.sortedEdges = e;
/* 1056 */     while (e != null) {
/* 1057 */       e.prevInSEL = e.prevInAEL;
/* 1058 */       e.nextInSEL = e.nextInAEL;
/* 1059 */       e = e.nextInAEL;
/*      */     } 
/*      */   }
/*      */   
/*      */   private Path.OutRec createOutRec() {
/* 1064 */     Path.OutRec result = new Path.OutRec();
/* 1065 */     result.Idx = -1;
/* 1066 */     result.isHole = false;
/* 1067 */     result.isOpen = false;
/* 1068 */     result.firstLeft = null;
/* 1069 */     result.setPoints(null);
/* 1070 */     result.bottomPt = null;
/* 1071 */     result.polyNode = null;
/* 1072 */     this.polyOuts.add(result);
/* 1073 */     result.Idx = this.polyOuts.size() - 1;
/* 1074 */     return result;
/*      */   }
/*      */   
/*      */   private void deleteFromAEL(Edge e) {
/* 1078 */     LOGGER.entering(DefaultClipper.class.getName(), "deleteFromAEL");
/*      */     
/* 1080 */     Edge AelPrev = e.prevInAEL;
/* 1081 */     Edge AelNext = e.nextInAEL;
/* 1082 */     if (AelPrev == null && AelNext == null && e != this.activeEdges) {
/*      */       return;
/*      */     }
/* 1085 */     if (AelPrev != null) {
/* 1086 */       AelPrev.nextInAEL = AelNext;
/*      */     } else {
/*      */       
/* 1089 */       this.activeEdges = AelNext;
/*      */     } 
/* 1091 */     if (AelNext != null) {
/* 1092 */       AelNext.prevInAEL = AelPrev;
/*      */     }
/* 1094 */     e.nextInAEL = null;
/* 1095 */     e.prevInAEL = null;
/* 1096 */     LOGGER.exiting(DefaultClipper.class.getName(), "deleteFromAEL");
/*      */   }
/*      */   
/*      */   private void deleteFromSEL(Edge e) {
/* 1100 */     LOGGER.entering(DefaultClipper.class.getName(), "deleteFromSEL");
/*      */     
/* 1102 */     Edge SelPrev = e.prevInSEL;
/* 1103 */     Edge SelNext = e.nextInSEL;
/* 1104 */     if (SelPrev == null && SelNext == null && !e.equals(this.sortedEdges)) {
/*      */       return;
/*      */     }
/* 1107 */     if (SelPrev != null) {
/* 1108 */       SelPrev.nextInSEL = SelNext;
/*      */     } else {
/*      */       
/* 1111 */       this.sortedEdges = SelNext;
/*      */     } 
/* 1113 */     if (SelNext != null) {
/* 1114 */       SelNext.prevInSEL = SelPrev;
/*      */     }
/* 1116 */     e.nextInSEL = null;
/* 1117 */     e.prevInSEL = null;
/*      */   }
/*      */   
/*      */   private boolean doHorzSegmentsOverlap(long seg1a, long seg1b, long seg2a, long seg2b) {
/* 1121 */     if (seg1a > seg1b) {
/* 1122 */       long tmp = seg1a;
/* 1123 */       seg1a = seg1b;
/* 1124 */       seg1b = tmp;
/*      */     } 
/* 1126 */     if (seg2a > seg2b) {
/* 1127 */       long tmp = seg2a;
/* 1128 */       seg2a = seg2b;
/* 1129 */       seg2b = tmp;
/*      */     } 
/* 1131 */     return (seg1a < seg2b && seg2a < seg1b);
/*      */   }
/*      */   
/*      */   private void doMaxima(Edge e) {
/* 1135 */     Edge eMaxPair = e.getMaximaPair();
/* 1136 */     if (eMaxPair == null) {
/* 1137 */       if (e.outIdx >= 0) {
/* 1138 */         addOutPt(e, e.getTop());
/*      */       }
/* 1140 */       deleteFromAEL(e);
/*      */       
/*      */       return;
/*      */     } 
/* 1144 */     Edge eNext = e.nextInAEL;
/* 1145 */     while (eNext != null && eNext != eMaxPair) {
/* 1146 */       Point.LongPoint tmp = new Point.LongPoint(e.getTop());
/* 1147 */       intersectEdges(e, eNext, tmp);
/* 1148 */       e.setTop(tmp);
/* 1149 */       swapPositionsInAEL(e, eNext);
/* 1150 */       eNext = e.nextInAEL;
/*      */     } 
/*      */     
/* 1153 */     if (e.outIdx == -1 && eMaxPair.outIdx == -1) {
/* 1154 */       deleteFromAEL(e);
/* 1155 */       deleteFromAEL(eMaxPair);
/*      */     }
/* 1157 */     else if (e.outIdx >= 0 && eMaxPair.outIdx >= 0) {
/* 1158 */       if (e.outIdx >= 0) {
/* 1159 */         addLocalMaxPoly(e, eMaxPair, e.getTop());
/*      */       }
/* 1161 */       deleteFromAEL(e);
/* 1162 */       deleteFromAEL(eMaxPair);
/*      */     
/*      */     }
/* 1165 */     else if (e.windDelta == 0) {
/* 1166 */       if (e.outIdx >= 0) {
/* 1167 */         addOutPt(e, e.getTop());
/* 1168 */         e.outIdx = -1;
/*      */       } 
/* 1170 */       deleteFromAEL(e);
/*      */       
/* 1172 */       if (eMaxPair.outIdx >= 0) {
/* 1173 */         addOutPt(eMaxPair, e.getTop());
/* 1174 */         eMaxPair.outIdx = -1;
/*      */       } 
/* 1176 */       deleteFromAEL(eMaxPair);
/*      */     } else {
/*      */       
/* 1179 */       throw new IllegalStateException("DoMaxima error");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doSimplePolygons() {
/* 1186 */     int i = 0;
/* 1187 */     label43: while (i < this.polyOuts.size()) {
/* 1188 */       Path.OutRec outrec = this.polyOuts.get(i++);
/* 1189 */       Path.OutPt op = outrec.getPoints();
/* 1190 */       if (op == null || outrec.isOpen) {
/*      */         continue;
/*      */       }
/*      */       
/*      */       while (true) {
/* 1195 */         Path.OutPt op2 = op.next;
/* 1196 */         while (op2 != outrec.getPoints()) {
/* 1197 */           if (op.getPt().equals(op2.getPt()) && !op2.next.equals(op) && !op2.prev.equals(op)) {
/*      */             
/* 1199 */             Path.OutPt op3 = op.prev;
/* 1200 */             Path.OutPt op4 = op2.prev;
/* 1201 */             op.prev = op4;
/* 1202 */             op4.next = op;
/* 1203 */             op2.prev = op3;
/* 1204 */             op3.next = op2;
/*      */             
/* 1206 */             outrec.setPoints(op);
/* 1207 */             Path.OutRec outrec2 = createOutRec();
/* 1208 */             outrec2.setPoints(op2);
/* 1209 */             updateOutPtIdxs(outrec2);
/* 1210 */             if (poly2ContainsPoly1(outrec2.getPoints(), outrec.getPoints())) {
/*      */               
/* 1212 */               outrec2.isHole = !outrec.isHole;
/* 1213 */               outrec2.firstLeft = outrec;
/* 1214 */               if (this.usingPolyTree) {
/* 1215 */                 fixupFirstLefts2(outrec2, outrec);
/*      */               }
/*      */             }
/* 1218 */             else if (poly2ContainsPoly1(outrec.getPoints(), outrec2.getPoints())) {
/*      */               
/* 1220 */               outrec2.isHole = outrec.isHole;
/* 1221 */               outrec.isHole = !outrec2.isHole;
/* 1222 */               outrec2.firstLeft = outrec.firstLeft;
/* 1223 */               outrec.firstLeft = outrec2;
/* 1224 */               if (this.usingPolyTree) {
/* 1225 */                 fixupFirstLefts2(outrec, outrec2);
/*      */               }
/*      */             }
/*      */             else {
/*      */               
/* 1230 */               outrec2.isHole = outrec.isHole;
/* 1231 */               outrec2.firstLeft = outrec.firstLeft;
/* 1232 */               if (this.usingPolyTree) {
/* 1233 */                 fixupFirstLefts1(outrec, outrec2);
/*      */               }
/*      */             } 
/* 1236 */             op2 = op;
/*      */           } 
/* 1238 */           op2 = op2.next;
/*      */         } 
/* 1240 */         op = op.next;
/*      */         
/* 1242 */         if (op == outrec.getPoints())
/*      */           continue label43; 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean EdgesAdjacent(IntersectNode inode) {
/* 1249 */     return (inode.edge1.nextInSEL == inode.Edge2 || inode.edge1.prevInSEL == inode.Edge2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean execute(IClipper.ClipType clipType, Paths solution, IClipper.PolyFillType FillType) {
/* 1258 */     return execute(clipType, solution, FillType, FillType);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean execute(IClipper.ClipType clipType, PolyTree polytree) {
/* 1263 */     return execute(clipType, polytree, IClipper.PolyFillType.EVEN_ODD);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean execute(IClipper.ClipType clipType, PolyTree polytree, IClipper.PolyFillType FillType) {
/* 1269 */     return execute(clipType, polytree, FillType, FillType);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean execute(IClipper.ClipType clipType, Paths solution) {
/* 1274 */     return execute(clipType, solution, IClipper.PolyFillType.EVEN_ODD);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean execute(IClipper.ClipType clipType, Paths solution, IClipper.PolyFillType subjFillType, IClipper.PolyFillType clipFillType) {
/* 1279 */     synchronized (this) {
/*      */       
/* 1281 */       if (this.hasOpenPaths) {
/* 1282 */         throw new IllegalStateException("Error: PolyTree struct is needed for open path clipping.");
/*      */       }
/*      */       
/* 1285 */       solution.clear();
/* 1286 */       this.subjFillType = subjFillType;
/* 1287 */       this.clipFillType = clipFillType;
/* 1288 */       this.clipType = clipType;
/* 1289 */       this.usingPolyTree = false;
/*      */       
/*      */       try {
/* 1292 */         boolean succeeded = executeInternal();
/*      */         
/* 1294 */         if (succeeded) {
/* 1295 */           buildResult(solution);
/*      */         }
/* 1297 */         return succeeded;
/*      */       } finally {
/*      */         
/* 1300 */         this.polyOuts.clear();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean execute(IClipper.ClipType clipType, PolyTree polytree, IClipper.PolyFillType subjFillType, IClipper.PolyFillType clipFillType) {
/* 1308 */     synchronized (this) {
/* 1309 */       boolean succeeded; this.subjFillType = subjFillType;
/* 1310 */       this.clipFillType = clipFillType;
/* 1311 */       this.clipType = clipType;
/* 1312 */       this.usingPolyTree = true;
/*      */       
/*      */       try {
/* 1315 */         succeeded = executeInternal();
/*      */         
/* 1317 */         if (succeeded) {
/* 1318 */           buildResult2(polytree);
/*      */         }
/*      */       } finally {
/*      */         
/* 1322 */         this.polyOuts.clear();
/*      */       } 
/* 1324 */       return succeeded;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean executeInternal() {
/*      */     try {
/* 1332 */       reset();
/* 1333 */       if (this.currentLM == null) {
/* 1334 */         return false;
/*      */       }
/* 1336 */       long botY = popScanbeam();
/*      */       do {
/* 1338 */         insertLocalMinimaIntoAEL(botY);
/* 1339 */         processHorizontals();
/* 1340 */         this.ghostJoins.clear();
/* 1341 */         if (this.scanbeam == null)
/*      */           break; 
/* 1343 */         long topY = popScanbeam();
/* 1344 */         if (!processIntersections(topY))
/* 1345 */           return false; 
/* 1346 */         processEdgesAtTopOfScanbeam(topY);
/* 1347 */         botY = topY;
/* 1348 */       } while (this.scanbeam != null || this.currentLM != null);
/*      */       
/*      */       int i;
/* 1351 */       for (i = 0; i < this.polyOuts.size(); i++) {
/* 1352 */         Path.OutRec outRec = this.polyOuts.get(i);
/* 1353 */         if (outRec.pts != null && !outRec.isOpen)
/*      */         {
/* 1355 */           if ((outRec.isHole ^ this.reverseSolution) == ((outRec.area() > 0.0D) ? 1 : 0))
/* 1356 */             outRec.getPoints().reversePolyPtLinks(); 
/*      */         }
/*      */       } 
/* 1359 */       joinCommonEdges();
/*      */       
/* 1361 */       for (i = 0; i < this.polyOuts.size(); i++) {
/* 1362 */         Path.OutRec outRec = this.polyOuts.get(i);
/* 1363 */         if (outRec.getPoints() != null)
/*      */         {
/* 1365 */           if (outRec.isOpen) {
/* 1366 */             fixupOutPolyline(outRec);
/*      */           } else {
/* 1368 */             fixupOutPolygon(outRec);
/*      */           }  } 
/*      */       } 
/* 1371 */       if (this.strictlySimple)
/* 1372 */         doSimplePolygons(); 
/* 1373 */       i = 1; return i;
/*      */     }
/*      */     finally {
/*      */       
/* 1377 */       this.joins.clear();
/* 1378 */       this.ghostJoins.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fixupFirstLefts1(Path.OutRec OldOutRec, Path.OutRec NewOutRec) {
/* 1385 */     for (int i = 0; i < this.polyOuts.size(); i++) {
/* 1386 */       Path.OutRec outRec = this.polyOuts.get(i);
/* 1387 */       if (outRec.getPoints() != null && outRec.firstLeft != null) {
/*      */ 
/*      */         
/* 1390 */         Path.OutRec firstLeft = parseFirstLeft(outRec.firstLeft);
/* 1391 */         if (firstLeft.equals(OldOutRec) && 
/* 1392 */           poly2ContainsPoly1(outRec.getPoints(), NewOutRec.getPoints())) {
/* 1393 */           outRec.firstLeft = NewOutRec;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void fixupFirstLefts2(Path.OutRec OldOutRec, Path.OutRec NewOutRec) {
/* 1400 */     for (Path.OutRec outRec : this.polyOuts) {
/* 1401 */       if (outRec.firstLeft == OldOutRec) {
/* 1402 */         outRec.firstLeft = NewOutRec;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fixupIntersectionOrder() {
/* 1411 */     Collections.sort(this.intersectList, this.intersectNodeComparer);
/*      */     
/* 1413 */     copyAELToSEL();
/* 1414 */     int cnt = this.intersectList.size();
/* 1415 */     for (int i = 0; i < cnt; i++) {
/* 1416 */       if (!EdgesAdjacent(this.intersectList.get(i))) {
/* 1417 */         int j = i + 1;
/* 1418 */         while (j < cnt && !EdgesAdjacent(this.intersectList.get(j))) {
/* 1419 */           j++;
/*      */         }
/* 1421 */         if (j == cnt) {
/* 1422 */           return false;
/*      */         }
/*      */         
/* 1425 */         IntersectNode tmp = this.intersectList.get(i);
/* 1426 */         this.intersectList.set(i, this.intersectList.get(j));
/* 1427 */         this.intersectList.set(j, tmp);
/*      */       } 
/*      */       
/* 1430 */       swapPositionsInSEL(((IntersectNode)this.intersectList.get(i)).edge1, ((IntersectNode)this.intersectList.get(i)).Edge2);
/*      */     } 
/* 1432 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fixupOutPolyline(Path.OutRec outrec) {
/* 1439 */     Path.OutPt pp = outrec.pts;
/* 1440 */     Path.OutPt lastPP = pp.prev;
/* 1441 */     while (pp != lastPP) {
/*      */       
/* 1443 */       pp = pp.next;
/* 1444 */       if (pp.pt.equals(pp.prev.pt)) {
/*      */         
/* 1446 */         if (pp == lastPP) lastPP = pp.prev; 
/* 1447 */         Path.OutPt tmpPP = pp.prev;
/* 1448 */         tmpPP.next = pp.next;
/* 1449 */         pp.next.prev = tmpPP;
/* 1450 */         pp = tmpPP;
/*      */       } 
/*      */     } 
/* 1453 */     if (pp == pp.prev) outrec.pts = null;
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   private void fixupOutPolygon(Path.OutRec outRec) {
/* 1459 */     Path.OutPt lastOK = null;
/* 1460 */     outRec.bottomPt = null;
/* 1461 */     Path.OutPt pp = outRec.getPoints();
/* 1462 */     boolean preserveCol = (this.preserveCollinear || this.strictlySimple);
/*      */     while (true) {
/* 1464 */       if (pp.prev == pp || pp.prev == pp.next) {
/* 1465 */         outRec.setPoints(null);
/*      */         
/*      */         return;
/*      */       } 
/* 1469 */       if (pp.getPt().equals(pp.next.getPt()) || pp.getPt().equals(pp.prev.getPt()) || (
/* 1470 */         Point.slopesEqual(pp.prev.getPt(), pp.getPt(), pp.next.getPt(), this.useFullRange) && (!preserveCol || 
/* 1471 */         !Point.isPt2BetweenPt1AndPt3(pp.prev.getPt(), pp.getPt(), pp.next.getPt())))) {
/* 1472 */         lastOK = null;
/* 1473 */         pp.prev.next = pp.next;
/* 1474 */         pp.next.prev = pp.prev;
/* 1475 */         pp = pp.prev; continue;
/*      */       } 
/* 1477 */       if (pp == lastOK) {
/*      */         break;
/*      */       }
/*      */       
/* 1481 */       if (lastOK == null) {
/* 1482 */         lastOK = pp;
/*      */       }
/* 1484 */       pp = pp.next;
/*      */     } 
/*      */     
/* 1487 */     outRec.setPoints(pp);
/*      */   }
/*      */   
/*      */   private Path.OutRec getOutRec(int idx) {
/* 1491 */     Path.OutRec outrec = this.polyOuts.get(idx);
/* 1492 */     while (outrec != this.polyOuts.get(outrec.Idx)) {
/* 1493 */       outrec = this.polyOuts.get(outrec.Idx);
/*      */     }
/* 1495 */     return outrec;
/*      */   }
/*      */   
/*      */   private void insertEdgeIntoAEL(Edge edge, Edge startEdge) {
/* 1499 */     LOGGER.entering(DefaultClipper.class.getName(), "insertEdgeIntoAEL");
/*      */     
/* 1501 */     if (this.activeEdges == null) {
/* 1502 */       edge.prevInAEL = null;
/* 1503 */       edge.nextInAEL = null;
/* 1504 */       LOGGER.finest("Edge " + edge.outIdx + " -> " + null);
/* 1505 */       this.activeEdges = edge;
/*      */     }
/* 1507 */     else if (startEdge == null && Edge.doesE2InsertBeforeE1(this.activeEdges, edge)) {
/* 1508 */       edge.prevInAEL = null;
/* 1509 */       edge.nextInAEL = this.activeEdges;
/* 1510 */       LOGGER.finest("Edge " + edge.outIdx + " -> " + edge.nextInAEL.outIdx);
/* 1511 */       this.activeEdges.prevInAEL = edge;
/* 1512 */       this.activeEdges = edge;
/*      */     } else {
/*      */       
/* 1515 */       LOGGER.finest("activeEdges unchanged");
/* 1516 */       if (startEdge == null) {
/* 1517 */         startEdge = this.activeEdges;
/*      */       }
/* 1519 */       while (startEdge.nextInAEL != null && 
/* 1520 */         !Edge.doesE2InsertBeforeE1(startEdge.nextInAEL, edge)) {
/* 1521 */         startEdge = startEdge.nextInAEL;
/*      */       }
/* 1523 */       edge.nextInAEL = startEdge.nextInAEL;
/* 1524 */       if (startEdge.nextInAEL != null) {
/* 1525 */         startEdge.nextInAEL.prevInAEL = edge;
/*      */       }
/* 1527 */       edge.prevInAEL = startEdge;
/* 1528 */       startEdge.nextInAEL = edge;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertLocalMinimaIntoAEL(long botY) {
/* 1535 */     LOGGER.entering(DefaultClipper.class.getName(), "insertLocalMinimaIntoAEL");
/*      */     
/* 1537 */     while (this.currentLM != null && this.currentLM.y == botY) {
/* 1538 */       Edge lb = this.currentLM.leftBound;
/* 1539 */       Edge rb = this.currentLM.rightBound;
/* 1540 */       popLocalMinima();
/*      */       
/* 1542 */       Path.OutPt Op1 = null;
/* 1543 */       if (lb == null) {
/* 1544 */         insertEdgeIntoAEL(rb, (Edge)null);
/* 1545 */         updateWindingCount(rb);
/* 1546 */         if (rb.isContributing(this.clipFillType, this.subjFillType, this.clipType)) {
/* 1547 */           Op1 = addOutPt(rb, rb.getBot());
/*      */         }
/*      */       }
/* 1550 */       else if (rb == null) {
/* 1551 */         insertEdgeIntoAEL(lb, (Edge)null);
/* 1552 */         updateWindingCount(lb);
/* 1553 */         if (lb.isContributing(this.clipFillType, this.subjFillType, this.clipType)) {
/* 1554 */           Op1 = addOutPt(lb, lb.getBot());
/*      */         }
/* 1556 */         insertScanbeam(lb.getTop().getY());
/*      */       } else {
/*      */         
/* 1559 */         insertEdgeIntoAEL(lb, (Edge)null);
/* 1560 */         insertEdgeIntoAEL(rb, lb);
/* 1561 */         updateWindingCount(lb);
/* 1562 */         rb.windCnt = lb.windCnt;
/* 1563 */         rb.windCnt2 = lb.windCnt2;
/* 1564 */         if (lb.isContributing(this.clipFillType, this.subjFillType, this.clipType)) {
/* 1565 */           Op1 = addLocalMinPoly(lb, rb, lb.getBot());
/*      */         }
/* 1567 */         insertScanbeam(lb.getTop().getY());
/*      */       } 
/*      */       
/* 1570 */       if (rb != null) {
/* 1571 */         if (rb.isHorizontal()) {
/* 1572 */           addEdgeToSEL(rb);
/*      */         } else {
/*      */           
/* 1575 */           insertScanbeam(rb.getTop().getY());
/*      */         } 
/*      */       }
/*      */       
/* 1579 */       if (lb == null || rb == null) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1584 */       if (Op1 != null && rb.isHorizontal() && this.ghostJoins
/* 1585 */         .size() > 0 && rb.windDelta != 0) {
/* 1586 */         for (int i = 0; i < this.ghostJoins.size(); i++) {
/*      */ 
/*      */           
/* 1589 */           Path.Join j = this.ghostJoins.get(i);
/* 1590 */           if (doHorzSegmentsOverlap(j.outPt1.getPt().getX(), j.getOffPt().getX(), rb.getBot().getX(), rb.getTop().getX())) {
/* 1591 */             addJoin(j.outPt1, Op1, j.getOffPt());
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1596 */       if (lb.outIdx >= 0 && lb.prevInAEL != null && lb.prevInAEL
/* 1597 */         .getCurrent().getX() == lb.getBot().getX() && lb.prevInAEL.outIdx >= 0 && 
/*      */         
/* 1599 */         Edge.slopesEqual(lb.prevInAEL, lb, this.useFullRange) && lb.windDelta != 0 && lb.prevInAEL.windDelta != 0) {
/*      */         
/* 1601 */         Path.OutPt Op2 = addOutPt(lb.prevInAEL, lb.getBot());
/* 1602 */         addJoin(Op1, Op2, lb.getTop());
/*      */       } 
/*      */       
/* 1605 */       if (lb.nextInAEL != rb) {
/*      */         
/* 1607 */         if (rb.outIdx >= 0 && rb.prevInAEL.outIdx >= 0 && 
/* 1608 */           Edge.slopesEqual(rb.prevInAEL, rb, this.useFullRange) && rb.windDelta != 0 && rb.prevInAEL.windDelta != 0) {
/*      */           
/* 1610 */           Path.OutPt Op2 = addOutPt(rb.prevInAEL, rb.getBot());
/* 1611 */           addJoin(Op1, Op2, rb.getTop());
/*      */         } 
/*      */         
/* 1614 */         Edge e = lb.nextInAEL;
/* 1615 */         if (e != null) {
/* 1616 */           while (e != rb) {
/*      */ 
/*      */             
/* 1619 */             intersectEdges(rb, e, lb.getCurrent());
/* 1620 */             e = e.nextInAEL;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void intersectEdges(Edge e1, Edge e2, Point.LongPoint pt) {
/*      */     IClipper.PolyFillType e1FillType, e2FillType, e1FillType2, e2FillType2;
/*      */     int e1Wc, e2Wc;
/* 1662 */     LOGGER.entering(DefaultClipper.class.getName(), "insersectEdges");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1667 */     boolean e1Contributing = (e1.outIdx >= 0);
/* 1668 */     boolean e2Contributing = (e2.outIdx >= 0);
/*      */     
/* 1670 */     setZ(pt, e1, e2);
/*      */ 
/*      */     
/* 1673 */     if (e1.windDelta == 0 || e2.windDelta == 0) {
/*      */ 
/*      */       
/* 1676 */       if (e1.windDelta == 0 && e2.windDelta == 0) {
/*      */         return;
/*      */       }
/* 1679 */       if (e1.polyTyp == e2.polyTyp && e1.windDelta != e2.windDelta && this.clipType == IClipper.ClipType.UNION) {
/*      */         
/* 1681 */         if (e1.windDelta == 0) {
/* 1682 */           if (e2Contributing) {
/* 1683 */             addOutPt(e1, pt);
/* 1684 */             if (e1Contributing) {
/* 1685 */               e1.outIdx = -1;
/*      */             }
/*      */           }
/*      */         
/*      */         }
/* 1690 */         else if (e1Contributing) {
/* 1691 */           addOutPt(e2, pt);
/* 1692 */           if (e2Contributing) {
/* 1693 */             e2.outIdx = -1;
/*      */           }
/*      */         }
/*      */       
/*      */       }
/* 1698 */       else if (e1.polyTyp != e2.polyTyp) {
/* 1699 */         if (e1.windDelta == 0 && Math.abs(e2.windCnt) == 1 && (this.clipType != IClipper.ClipType.UNION || e2.windCnt2 == 0)) {
/* 1700 */           addOutPt(e1, pt);
/* 1701 */           if (e1Contributing) {
/* 1702 */             e1.outIdx = -1;
/*      */           }
/*      */         }
/* 1705 */         else if (e2.windDelta == 0 && Math.abs(e1.windCnt) == 1 && (this.clipType != IClipper.ClipType.UNION || e1.windCnt2 == 0)) {
/* 1706 */           addOutPt(e2, pt);
/* 1707 */           if (e2Contributing) {
/* 1708 */             e2.outIdx = -1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1717 */     if (e1.polyTyp == e2.polyTyp) {
/* 1718 */       if (e1.isEvenOddFillType(this.clipFillType, this.subjFillType)) {
/* 1719 */         int oldE1WindCnt = e1.windCnt;
/* 1720 */         e1.windCnt = e2.windCnt;
/* 1721 */         e2.windCnt = oldE1WindCnt;
/*      */       } else {
/*      */         
/* 1724 */         if (e1.windCnt + e2.windDelta == 0) {
/* 1725 */           e1.windCnt = -e1.windCnt;
/*      */         } else {
/*      */           
/* 1728 */           e1.windCnt += e2.windDelta;
/*      */         } 
/* 1730 */         if (e2.windCnt - e1.windDelta == 0) {
/* 1731 */           e2.windCnt = -e2.windCnt;
/*      */         } else {
/*      */           
/* 1734 */           e2.windCnt -= e1.windDelta;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1739 */       if (!e2.isEvenOddFillType(this.clipFillType, this.subjFillType)) {
/* 1740 */         e1.windCnt2 += e2.windDelta;
/*      */       } else {
/*      */         
/* 1743 */         e1.windCnt2 = (e1.windCnt2 == 0) ? 1 : 0;
/*      */       } 
/* 1745 */       if (!e1.isEvenOddFillType(this.clipFillType, this.subjFillType)) {
/* 1746 */         e2.windCnt2 -= e1.windDelta;
/*      */       } else {
/*      */         
/* 1749 */         e2.windCnt2 = (e2.windCnt2 == 0) ? 1 : 0;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1754 */     if (e1.polyTyp == IClipper.PolyType.SUBJECT) {
/* 1755 */       e1FillType = this.subjFillType;
/* 1756 */       e1FillType2 = this.clipFillType;
/*      */     } else {
/*      */       
/* 1759 */       e1FillType = this.clipFillType;
/* 1760 */       e1FillType2 = this.subjFillType;
/*      */     } 
/* 1762 */     if (e2.polyTyp == IClipper.PolyType.SUBJECT) {
/* 1763 */       e2FillType = this.subjFillType;
/* 1764 */       e2FillType2 = this.clipFillType;
/*      */     } else {
/*      */       
/* 1767 */       e2FillType = this.clipFillType;
/* 1768 */       e2FillType2 = this.subjFillType;
/*      */     } 
/*      */ 
/*      */     
/* 1772 */     switch (e1FillType) {
/*      */       case INTERSECTION:
/* 1774 */         e1Wc = e1.windCnt;
/*      */         break;
/*      */       case UNION:
/* 1777 */         e1Wc = -e1.windCnt;
/*      */         break;
/*      */       default:
/* 1780 */         e1Wc = Math.abs(e1.windCnt);
/*      */         break;
/*      */     } 
/* 1783 */     switch (e2FillType) {
/*      */       case INTERSECTION:
/* 1785 */         e2Wc = e2.windCnt;
/*      */         break;
/*      */       case UNION:
/* 1788 */         e2Wc = -e2.windCnt;
/*      */         break;
/*      */       default:
/* 1791 */         e2Wc = Math.abs(e2.windCnt);
/*      */         break;
/*      */     } 
/*      */     
/* 1795 */     if (e1Contributing && e2Contributing) {
/* 1796 */       if ((e1Wc != 0 && e1Wc != 1) || (e2Wc != 0 && e2Wc != 1) || (e1.polyTyp != e2.polyTyp && this.clipType != IClipper.ClipType.XOR)) {
/* 1797 */         addLocalMaxPoly(e1, e2, pt);
/*      */       } else {
/*      */         
/* 1800 */         addOutPt(e1, pt);
/* 1801 */         addOutPt(e2, pt);
/* 1802 */         Edge.swapSides(e1, e2);
/* 1803 */         Edge.swapPolyIndexes(e1, e2);
/*      */       }
/*      */     
/* 1806 */     } else if (e1Contributing) {
/* 1807 */       if (e2Wc == 0 || e2Wc == 1) {
/* 1808 */         addOutPt(e1, pt);
/* 1809 */         Edge.swapSides(e1, e2);
/* 1810 */         Edge.swapPolyIndexes(e1, e2);
/*      */       }
/*      */     
/*      */     }
/* 1814 */     else if (e2Contributing) {
/* 1815 */       if (e1Wc == 0 || e1Wc == 1) {
/* 1816 */         addOutPt(e2, pt);
/* 1817 */         Edge.swapSides(e1, e2);
/* 1818 */         Edge.swapPolyIndexes(e1, e2);
/*      */       }
/*      */     
/* 1821 */     } else if ((e1Wc == 0 || e1Wc == 1) && (e2Wc == 0 || e2Wc == 1)) {
/*      */       int e1Wc2;
/*      */       int e2Wc2;
/* 1824 */       switch (e1FillType2) {
/*      */         case INTERSECTION:
/* 1826 */           e1Wc2 = e1.windCnt2;
/*      */           break;
/*      */         case UNION:
/* 1829 */           e1Wc2 = -e1.windCnt2;
/*      */           break;
/*      */         default:
/* 1832 */           e1Wc2 = Math.abs(e1.windCnt2);
/*      */           break;
/*      */       } 
/* 1835 */       switch (e2FillType2) {
/*      */         case INTERSECTION:
/* 1837 */           e2Wc2 = e2.windCnt2;
/*      */           break;
/*      */         case UNION:
/* 1840 */           e2Wc2 = -e2.windCnt2;
/*      */           break;
/*      */         default:
/* 1843 */           e2Wc2 = Math.abs(e2.windCnt2);
/*      */           break;
/*      */       } 
/*      */       
/* 1847 */       if (e1.polyTyp != e2.polyTyp) {
/* 1848 */         addLocalMinPoly(e1, e2, pt);
/*      */       }
/* 1850 */       else if (e1Wc == 1 && e2Wc == 1) {
/* 1851 */         switch (this.clipType) {
/*      */           case INTERSECTION:
/* 1853 */             if (e1Wc2 > 0 && e2Wc2 > 0) {
/* 1854 */               addLocalMinPoly(e1, e2, pt);
/*      */             }
/*      */             break;
/*      */           case UNION:
/* 1858 */             if (e1Wc2 <= 0 && e2Wc2 <= 0) {
/* 1859 */               addLocalMinPoly(e1, e2, pt);
/*      */             }
/*      */             break;
/*      */           case DIFFERENCE:
/* 1863 */             if ((e1.polyTyp == IClipper.PolyType.CLIP && e1Wc2 > 0 && e2Wc2 > 0) || (e1.polyTyp == IClipper.PolyType.SUBJECT && e1Wc2 <= 0 && e2Wc2 <= 0)) {
/* 1864 */               addLocalMinPoly(e1, e2, pt);
/*      */             }
/*      */             break;
/*      */           case XOR:
/* 1868 */             addLocalMinPoly(e1, e2, pt);
/*      */             break;
/*      */         } 
/*      */       
/*      */       } else {
/* 1873 */         Edge.swapSides(e1, e2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void intersectPoint(Edge edge1, Edge edge2, Point.LongPoint[] ipV) {
/* 1879 */     Point.LongPoint ip = ipV[0] = new Point.LongPoint();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1884 */     if (edge1.deltaX == edge2.deltaX) {
/* 1885 */       ip.setY(Long.valueOf(edge1.getCurrent().getY()));
/* 1886 */       ip.setX(Long.valueOf(Edge.topX(edge1, ip.getY())));
/*      */       
/*      */       return;
/*      */     } 
/* 1890 */     if (edge1.getDelta().getX() == 0L) {
/* 1891 */       ip.setX(Long.valueOf(edge1.getBot().getX()));
/* 1892 */       if (edge2.isHorizontal()) {
/* 1893 */         ip.setY(Long.valueOf(edge2.getBot().getY()));
/*      */       } else {
/*      */         
/* 1896 */         double b2 = edge2.getBot().getY() - edge2.getBot().getX() / edge2.deltaX;
/* 1897 */         ip.setY(Long.valueOf(Math.round(ip.getX() / edge2.deltaX + b2)));
/*      */       }
/*      */     
/* 1900 */     } else if (edge2.getDelta().getX() == 0L) {
/* 1901 */       ip.setX(Long.valueOf(edge2.getBot().getX()));
/* 1902 */       if (edge1.isHorizontal()) {
/* 1903 */         ip.setY(Long.valueOf(edge1.getBot().getY()));
/*      */       } else {
/*      */         
/* 1906 */         double b1 = edge1.getBot().getY() - edge1.getBot().getX() / edge1.deltaX;
/* 1907 */         ip.setY(Long.valueOf(Math.round(ip.getX() / edge1.deltaX + b1)));
/*      */       } 
/*      */     } else {
/*      */       
/* 1911 */       double b1 = edge1.getBot().getX() - edge1.getBot().getY() * edge1.deltaX;
/* 1912 */       double b2 = edge2.getBot().getX() - edge2.getBot().getY() * edge2.deltaX;
/* 1913 */       double q = (b2 - b1) / (edge1.deltaX - edge2.deltaX);
/* 1914 */       ip.setY(Long.valueOf(Math.round(q)));
/* 1915 */       if (Math.abs(edge1.deltaX) < Math.abs(edge2.deltaX)) {
/* 1916 */         ip.setX(Long.valueOf(Math.round(edge1.deltaX * q + b1)));
/*      */       } else {
/*      */         
/* 1919 */         ip.setX(Long.valueOf(Math.round(edge2.deltaX * q + b2)));
/*      */       } 
/*      */     } 
/*      */     
/* 1923 */     if (ip.getY() < edge1.getTop().getY() || ip.getY() < edge2.getTop().getY()) {
/* 1924 */       if (edge1.getTop().getY() > edge2.getTop().getY()) {
/* 1925 */         ip.setY(Long.valueOf(edge1.getTop().getY()));
/*      */       } else {
/*      */         
/* 1928 */         ip.setY(Long.valueOf(edge2.getTop().getY()));
/*      */       } 
/* 1930 */       if (Math.abs(edge1.deltaX) < Math.abs(edge2.deltaX)) {
/* 1931 */         ip.setX(Long.valueOf(Edge.topX(edge1, ip.getY())));
/*      */       } else {
/*      */         
/* 1934 */         ip.setX(Long.valueOf(Edge.topX(edge2, ip.getY())));
/*      */       } 
/*      */     } 
/*      */     
/* 1938 */     if (ip.getY() > edge1.getCurrent().getY()) {
/* 1939 */       ip.setY(Long.valueOf(edge1.getCurrent().getY()));
/*      */       
/* 1941 */       if (Math.abs(edge1.deltaX) > Math.abs(edge2.deltaX)) {
/* 1942 */         ip.setX(Long.valueOf(Edge.topX(edge2, ip.getY())));
/*      */       } else {
/*      */         
/* 1945 */         ip.setX(Long.valueOf(Edge.topX(edge1, ip.getY())));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void joinCommonEdges() {
/* 1951 */     for (int i = 0; i < this.joins.size(); i++) {
/* 1952 */       Path.Join join = this.joins.get(i);
/*      */       
/* 1954 */       Path.OutRec outRec1 = getOutRec(join.outPt1.idx);
/* 1955 */       Path.OutRec outRec2 = getOutRec(join.outPt2.idx);
/*      */       
/* 1957 */       if (outRec1.getPoints() != null && outRec2.getPoints() != null)
/*      */       {
/*      */         
/* 1960 */         if (!outRec1.isOpen && !outRec2.isOpen) {
/*      */           Path.OutRec holeStateRec;
/*      */ 
/*      */ 
/*      */           
/* 1965 */           if (outRec1 == outRec2) {
/* 1966 */             holeStateRec = outRec1;
/*      */           }
/* 1968 */           else if (isParam1RightOfParam2(outRec1, outRec2)) {
/* 1969 */             holeStateRec = outRec2;
/*      */           }
/* 1971 */           else if (isParam1RightOfParam2(outRec2, outRec1)) {
/* 1972 */             holeStateRec = outRec1;
/*      */           } else {
/*      */             
/* 1975 */             holeStateRec = Path.OutPt.getLowerMostRec(outRec1, outRec2);
/*      */           } 
/*      */           
/* 1978 */           if (joinPoints(join, outRec1, outRec2))
/*      */           {
/*      */ 
/*      */             
/* 1982 */             if (outRec1 == outRec2) {
/*      */ 
/*      */               
/* 1985 */               outRec1.setPoints(join.outPt1);
/* 1986 */               outRec1.bottomPt = null;
/* 1987 */               outRec2 = createOutRec();
/* 1988 */               outRec2.setPoints(join.outPt2);
/*      */ 
/*      */               
/* 1991 */               updateOutPtIdxs(outRec2);
/*      */ 
/*      */ 
/*      */               
/* 1995 */               if (this.usingPolyTree) {
/* 1996 */                 for (int j = 0; j < this.polyOuts.size() - 1; j++) {
/* 1997 */                   Path.OutRec oRec = this.polyOuts.get(j);
/* 1998 */                   if (oRec.getPoints() != null && parseFirstLeft(oRec.firstLeft) == outRec1 && oRec.isHole != outRec1.isHole)
/*      */                   {
/*      */                     
/* 2001 */                     if (poly2ContainsPoly1(oRec.getPoints(), join.outPt2)) {
/* 2002 */                       oRec.firstLeft = outRec2;
/*      */                     }
/*      */                   }
/*      */                 } 
/*      */               }
/* 2007 */               if (poly2ContainsPoly1(outRec2.getPoints(), outRec1.getPoints()))
/*      */               {
/* 2009 */                 outRec2.isHole = !outRec1.isHole;
/* 2010 */                 outRec2.firstLeft = outRec1;
/*      */ 
/*      */                 
/* 2013 */                 if (this.usingPolyTree) {
/* 2014 */                   fixupFirstLefts2(outRec2, outRec1);
/*      */                 }
/*      */                 
/* 2017 */                 if ((outRec2.isHole ^ this.reverseSolution) == ((outRec2.area() > 0.0D) ? 1 : 0)) {
/* 2018 */                   outRec2.getPoints().reversePolyPtLinks();
/*      */                 
/*      */                 }
/*      */               }
/* 2022 */               else if (poly2ContainsPoly1(outRec1.getPoints(), outRec2.getPoints()))
/*      */               {
/* 2024 */                 outRec2.isHole = outRec1.isHole;
/* 2025 */                 outRec1.isHole = !outRec2.isHole;
/* 2026 */                 outRec2.firstLeft = outRec1.firstLeft;
/* 2027 */                 outRec1.firstLeft = outRec2;
/*      */ 
/*      */                 
/* 2030 */                 if (this.usingPolyTree) {
/* 2031 */                   fixupFirstLefts2(outRec1, outRec2);
/*      */                 }
/*      */                 
/* 2034 */                 if ((outRec1.isHole ^ this.reverseSolution) == ((outRec1.area() > 0.0D) ? 1 : 0)) {
/* 2035 */                   outRec1.getPoints().reversePolyPtLinks();
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/* 2040 */                 outRec2.isHole = outRec1.isHole;
/* 2041 */                 outRec2.firstLeft = outRec1.firstLeft;
/*      */ 
/*      */                 
/* 2044 */                 if (this.usingPolyTree) {
/* 2045 */                   fixupFirstLefts1(outRec1, outRec2);
/*      */                 
/*      */                 }
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 2053 */               outRec2.setPoints(null);
/* 2054 */               outRec2.bottomPt = null;
/* 2055 */               outRec2.Idx = outRec1.Idx;
/*      */               
/* 2057 */               outRec1.isHole = holeStateRec.isHole;
/* 2058 */               if (holeStateRec == outRec2) {
/* 2059 */                 outRec1.firstLeft = outRec2.firstLeft;
/*      */               }
/* 2061 */               outRec2.firstLeft = outRec1;
/*      */ 
/*      */               
/* 2064 */               if (this.usingPolyTree)
/* 2065 */                 fixupFirstLefts2(outRec2, outRec1); 
/*      */             }  } 
/*      */         }  } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private long popScanbeam() {
/* 2072 */     LOGGER.entering(DefaultClipper.class.getName(), "popBeam");
/*      */     
/* 2074 */     long y = this.scanbeam.y;
/* 2075 */     this.scanbeam = this.scanbeam.next;
/* 2076 */     return y;
/*      */   }
/*      */   
/*      */   private void processEdgesAtTopOfScanbeam(long topY) {
/* 2080 */     LOGGER.entering(DefaultClipper.class.getName(), "processEdgesAtTopOfScanbeam");
/*      */     
/* 2082 */     Edge e = this.activeEdges;
/* 2083 */     while (e != null) {
/*      */ 
/*      */       
/* 2086 */       boolean IsMaximaEdge = e.isMaxima(topY);
/*      */       
/* 2088 */       if (IsMaximaEdge) {
/* 2089 */         Edge eMaxPair = e.getMaximaPair();
/* 2090 */         IsMaximaEdge = (eMaxPair == null || !eMaxPair.isHorizontal());
/*      */       } 
/*      */       
/* 2093 */       if (IsMaximaEdge) {
/* 2094 */         if (this.strictlySimple) InsertMaxima(e.getTop().getX()); 
/* 2095 */         Edge ePrev = e.prevInAEL;
/* 2096 */         doMaxima(e);
/* 2097 */         if (ePrev == null) {
/* 2098 */           e = this.activeEdges;
/*      */           continue;
/*      */         } 
/* 2101 */         e = ePrev.nextInAEL;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 2106 */       if (e.isIntermediate(topY) && e.nextInLML.isHorizontal()) {
/* 2107 */         Edge[] t = { e };
/* 2108 */         updateEdgeIntoAEL(t);
/* 2109 */         e = t[0];
/* 2110 */         if (e.outIdx >= 0) {
/* 2111 */           addOutPt(e, e.getBot());
/*      */         }
/* 2113 */         addEdgeToSEL(e);
/*      */       } else {
/*      */         
/* 2116 */         e.getCurrent().setX(Long.valueOf(Edge.topX(e, topY)));
/* 2117 */         e.getCurrent().setY(Long.valueOf(topY));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2122 */       if (this.strictlySimple) {
/* 2123 */         Edge ePrev = e.prevInAEL;
/* 2124 */         if (e.outIdx >= 0 && e.windDelta != 0 && ePrev != null && ePrev.outIdx >= 0 && ePrev.getCurrent().getX() == e.getCurrent().getX() && ePrev.windDelta != 0) {
/*      */           
/* 2126 */           Point.LongPoint ip = new Point.LongPoint(e.getCurrent());
/*      */           
/* 2128 */           setZ(ip, ePrev, e);
/*      */           
/* 2130 */           Path.OutPt op = addOutPt(ePrev, ip);
/* 2131 */           Path.OutPt op2 = addOutPt(e, ip);
/* 2132 */           addJoin(op, op2, ip);
/*      */         } 
/*      */       } 
/*      */       
/* 2136 */       e = e.nextInAEL;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2141 */     processHorizontals();
/* 2142 */     this.maxima = null;
/*      */ 
/*      */     
/* 2145 */     e = this.activeEdges;
/* 2146 */     while (e != null) {
/* 2147 */       if (e.isIntermediate(topY)) {
/* 2148 */         Path.OutPt op = null;
/* 2149 */         if (e.outIdx >= 0) {
/* 2150 */           op = addOutPt(e, e.getTop());
/*      */         }
/* 2152 */         Edge[] t = { e };
/* 2153 */         updateEdgeIntoAEL(t);
/* 2154 */         e = t[0];
/*      */ 
/*      */         
/* 2157 */         Edge ePrev = e.prevInAEL;
/* 2158 */         Edge eNext = e.nextInAEL;
/* 2159 */         if (ePrev != null && ePrev.getCurrent().equals(e.getBot()) && op != null && ePrev.outIdx >= 0 && ePrev
/* 2160 */           .getCurrent().getY() > ePrev.getTop().getY() && Edge.slopesEqual(e, ePrev, this.useFullRange) && e.windDelta != 0 && ePrev.windDelta != 0) {
/*      */           
/* 2162 */           Path.OutPt op2 = addOutPt(ePrev, e.getBot());
/* 2163 */           addJoin(op, op2, e.getTop());
/*      */         }
/* 2165 */         else if (eNext != null && eNext.getCurrent().equals(e.getBot()) && op != null && eNext.outIdx >= 0 && eNext
/* 2166 */           .getCurrent().getY() > eNext.getTop().getY() && Edge.slopesEqual(e, eNext, this.useFullRange) && e.windDelta != 0 && eNext.windDelta != 0) {
/*      */           
/* 2168 */           Path.OutPt op2 = addOutPt(eNext, e.getBot());
/* 2169 */           addJoin(op, op2, e.getTop());
/*      */         } 
/*      */       } 
/* 2172 */       e = e.nextInAEL;
/*      */     } 
/* 2174 */     LOGGER.exiting(DefaultClipper.class.getName(), "processEdgesAtTopOfScanbeam");
/*      */   }
/*      */   
/*      */   private void processHorizontal(Edge horzEdge) {
/* 2178 */     LOGGER.entering(DefaultClipper.class.getName(), "isHorizontal");
/* 2179 */     IClipper.Direction[] dir = new IClipper.Direction[1];
/* 2180 */     long[] horzLeft = new long[1], horzRight = new long[1];
/* 2181 */     boolean IsOpen = (horzEdge.outIdx >= 0 && ((Path.OutRec)this.polyOuts.get(horzEdge.outIdx)).isOpen);
/*      */     
/* 2183 */     getHorzDirection(horzEdge, dir, horzLeft, horzRight);
/*      */     
/* 2185 */     Edge eLastHorz = horzEdge, eMaxPair = null;
/* 2186 */     while (eLastHorz.nextInLML != null && eLastHorz.nextInLML.isHorizontal()) {
/* 2187 */       eLastHorz = eLastHorz.nextInLML;
/*      */     }
/* 2189 */     if (eLastHorz.nextInLML == null) {
/* 2190 */       eMaxPair = eLastHorz.getMaximaPair();
/*      */     }
/*      */     
/* 2193 */     Path.Maxima currMax = this.maxima;
/* 2194 */     if (currMax != null)
/*      */     {
/*      */       
/* 2197 */       if (dir[0] == IClipper.Direction.LEFT_TO_RIGHT) {
/*      */         
/* 2199 */         while (currMax != null && currMax.X <= horzEdge.getBot().getX())
/* 2200 */           currMax = currMax.Next; 
/* 2201 */         if (currMax != null && currMax.X >= eLastHorz.getBot().getX()) {
/* 2202 */           currMax = null;
/*      */         }
/*      */       } else {
/*      */         
/* 2206 */         while (currMax.Next != null && currMax.Next.X < horzEdge.getBot().getX())
/* 2207 */           currMax = currMax.Next; 
/* 2208 */         if (currMax.X <= eLastHorz.getTop().getX()) currMax = null;
/*      */       
/*      */       } 
/*      */     }
/* 2212 */     Path.OutPt op1 = null;
/*      */     while (true) {
/* 2214 */       boolean IsLastHorz = (horzEdge == eLastHorz);
/* 2215 */       Edge e = horzEdge.getNextInAEL(dir[0]);
/* 2216 */       while (e != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2221 */         if (currMax != null)
/*      */         {
/* 2223 */           if (dir[0] == IClipper.Direction.LEFT_TO_RIGHT) {
/*      */             
/* 2225 */             while (currMax != null && currMax.X < e.getCurrent().getX())
/*      */             {
/* 2227 */               if (horzEdge.outIdx >= 0 && !IsOpen)
/* 2228 */                 addOutPt(horzEdge, new Point.LongPoint(currMax.X, horzEdge.getBot().getY())); 
/* 2229 */               currMax = currMax.Next;
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 2234 */             while (currMax != null && currMax.X > e.getCurrent().getX()) {
/*      */               
/* 2236 */               if (horzEdge.outIdx >= 0 && !IsOpen)
/* 2237 */                 addOutPt(horzEdge, new Point.LongPoint(currMax.X, horzEdge.getBot().getY())); 
/* 2238 */               currMax = currMax.Prev;
/*      */             } 
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/* 2244 */         if ((dir[0] == IClipper.Direction.LEFT_TO_RIGHT && e.getCurrent().getX() > horzRight[0]) || (dir[0] == IClipper.Direction.RIGHT_TO_LEFT && e
/* 2245 */           .getCurrent().getX() < horzLeft[0])) {
/*      */           break;
/*      */         }
/* 2248 */         if (e.getCurrent().getX() == horzEdge.getTop().getX() && horzEdge.nextInLML != null && e.deltaX < horzEdge.nextInLML.deltaX) {
/*      */           break;
/*      */         }
/* 2251 */         if (horzEdge.outIdx >= 0 && !IsOpen) {
/*      */           
/* 2253 */           op1 = addOutPt(horzEdge, e.getCurrent());
/* 2254 */           Edge eNextHorz = this.sortedEdges;
/* 2255 */           while (eNextHorz != null) {
/*      */             
/* 2257 */             if (eNextHorz.outIdx >= 0 && 
/* 2258 */               doHorzSegmentsOverlap(horzEdge.getBot().getX(), horzEdge
/* 2259 */                 .getTop().getX(), eNextHorz.getBot().getX(), eNextHorz.getTop().getX())) {
/*      */               
/* 2261 */               Path.OutPt op2 = GetLastOutPt(eNextHorz);
/* 2262 */               addJoin(op2, op1, eNextHorz.getTop());
/*      */             } 
/* 2264 */             eNextHorz = eNextHorz.nextInSEL;
/*      */           } 
/* 2266 */           addGhostJoin(op1, horzEdge.getBot());
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2271 */         if (e == eMaxPair && IsLastHorz) {
/*      */           
/* 2273 */           if (horzEdge.outIdx >= 0)
/* 2274 */             addLocalMaxPoly(horzEdge, eMaxPair, horzEdge.getTop()); 
/* 2275 */           deleteFromAEL(horzEdge);
/* 2276 */           deleteFromAEL(eMaxPair);
/*      */           
/*      */           return;
/*      */         } 
/* 2280 */         if (dir[0] == IClipper.Direction.LEFT_TO_RIGHT) {
/*      */           
/* 2282 */           Point.LongPoint Pt = new Point.LongPoint(e.getCurrent().getX(), horzEdge.getCurrent().getY());
/* 2283 */           intersectEdges(horzEdge, e, Pt);
/*      */         }
/*      */         else {
/*      */           
/* 2287 */           Point.LongPoint Pt = new Point.LongPoint(e.getCurrent().getX(), horzEdge.getCurrent().getY());
/* 2288 */           intersectEdges(e, horzEdge, Pt);
/*      */         } 
/* 2290 */         Edge eNext = e.getNextInAEL(dir[0]);
/* 2291 */         swapPositionsInAEL(horzEdge, e);
/* 2292 */         e = eNext;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2297 */       if (horzEdge.nextInLML == null || !horzEdge.nextInLML.isHorizontal())
/*      */         break; 
/* 2299 */       Edge[] temp = new Edge[1];
/* 2300 */       temp[0] = horzEdge;
/* 2301 */       updateEdgeIntoAEL(temp);
/* 2302 */       horzEdge = temp[0];
/*      */       
/* 2304 */       if (horzEdge.outIdx >= 0) addOutPt(horzEdge, horzEdge.getBot()); 
/* 2305 */       getHorzDirection(horzEdge, dir, horzLeft, horzRight);
/*      */     } 
/*      */ 
/*      */     
/* 2309 */     if (horzEdge.outIdx >= 0 && op1 == null) {
/*      */       
/* 2311 */       op1 = GetLastOutPt(horzEdge);
/* 2312 */       Edge eNextHorz = this.sortedEdges;
/* 2313 */       while (eNextHorz != null) {
/*      */         
/* 2315 */         if (eNextHorz.outIdx >= 0 && 
/* 2316 */           doHorzSegmentsOverlap(horzEdge.getBot().getX(), horzEdge
/* 2317 */             .getTop().getX(), eNextHorz.getBot().getX(), eNextHorz.getTop().getX())) {
/*      */           
/* 2319 */           Path.OutPt op2 = GetLastOutPt(eNextHorz);
/* 2320 */           addJoin(op2, op1, eNextHorz.getTop());
/*      */         } 
/* 2322 */         eNextHorz = eNextHorz.nextInSEL;
/*      */       } 
/* 2324 */       addGhostJoin(op1, horzEdge.getTop());
/*      */     } 
/*      */     
/* 2327 */     if (horzEdge.nextInLML != null) {
/* 2328 */       if (horzEdge.outIdx >= 0) {
/* 2329 */         op1 = addOutPt(horzEdge, horzEdge.getTop());
/*      */         
/* 2331 */         Edge[] t = { horzEdge };
/* 2332 */         updateEdgeIntoAEL(t);
/* 2333 */         horzEdge = t[0];
/*      */         
/* 2335 */         if (horzEdge.windDelta == 0) {
/*      */           return;
/*      */         }
/*      */         
/* 2339 */         Edge ePrev = horzEdge.prevInAEL;
/* 2340 */         Edge eNext = horzEdge.nextInAEL;
/* 2341 */         if (ePrev != null && ePrev.getCurrent().equals(horzEdge.getBot()) && ePrev.windDelta != 0 && ePrev.outIdx >= 0 && ePrev
/* 2342 */           .getCurrent().getY() > ePrev.getTop().getY() && 
/* 2343 */           Edge.slopesEqual(horzEdge, ePrev, this.useFullRange)) {
/* 2344 */           Path.OutPt op2 = addOutPt(ePrev, horzEdge.getBot());
/* 2345 */           addJoin(op1, op2, horzEdge.getTop());
/*      */         }
/* 2347 */         else if (eNext != null && eNext.getCurrent().equals(horzEdge.getBot()) && eNext.windDelta != 0 && eNext.outIdx >= 0 && eNext
/* 2348 */           .getCurrent().getY() > eNext.getTop().getY() && 
/* 2349 */           Edge.slopesEqual(horzEdge, eNext, this.useFullRange)) {
/* 2350 */           Path.OutPt op2 = addOutPt(eNext, horzEdge.getBot());
/* 2351 */           addJoin(op1, op2, horzEdge.getTop());
/*      */         } 
/*      */       } else {
/*      */         
/* 2355 */         Edge[] t = { horzEdge };
/* 2356 */         updateEdgeIntoAEL(t);
/* 2357 */         horzEdge = t[0];
/*      */       } 
/*      */     } else {
/*      */       
/* 2361 */       if (horzEdge.outIdx >= 0) {
/* 2362 */         addOutPt(horzEdge, horzEdge.getTop());
/*      */       }
/* 2364 */       deleteFromAEL(horzEdge);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void processHorizontals() {
/* 2371 */     LOGGER.entering(DefaultClipper.class.getName(), "processHorizontals");
/*      */     
/* 2373 */     Edge horzEdge = this.sortedEdges;
/* 2374 */     while (horzEdge != null) {
/* 2375 */       deleteFromSEL(horzEdge);
/* 2376 */       processHorizontal(horzEdge);
/* 2377 */       horzEdge = this.sortedEdges;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean processIntersections(long topY) {
/* 2384 */     LOGGER.entering(DefaultClipper.class.getName(), "processIntersections");
/*      */     
/* 2386 */     if (this.activeEdges == null) {
/* 2387 */       return true;
/*      */     }
/*      */     try {
/* 2390 */       buildIntersectList(topY);
/* 2391 */       if (this.intersectList.size() == 0) {
/* 2392 */         return true;
/*      */       }
/* 2394 */       if (this.intersectList.size() == 1 || fixupIntersectionOrder()) {
/* 2395 */         processIntersectList();
/*      */       } else {
/*      */         
/* 2398 */         return false;
/*      */       }
/*      */     
/* 2401 */     } catch (Exception e) {
/* 2402 */       this.sortedEdges = null;
/* 2403 */       this.intersectList.clear();
/* 2404 */       throw new IllegalStateException("ProcessIntersections error", e);
/*      */     } 
/* 2406 */     this.sortedEdges = null;
/* 2407 */     return true;
/*      */   }
/*      */   
/*      */   private void processIntersectList() {
/* 2411 */     for (int i = 0; i < this.intersectList.size(); i++) {
/* 2412 */       IntersectNode iNode = this.intersectList.get(i);
/*      */       
/* 2414 */       intersectEdges(iNode.edge1, iNode.Edge2, iNode.getPt());
/* 2415 */       swapPositionsInAEL(iNode.edge1, iNode.Edge2);
/*      */     } 
/*      */     
/* 2418 */     this.intersectList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reset() {
/* 2425 */     super.reset();
/* 2426 */     this.scanbeam = null;
/* 2427 */     this.maxima = null;
/* 2428 */     this.activeEdges = null;
/* 2429 */     this.sortedEdges = null;
/* 2430 */     ClipperBase.LocalMinima lm = this.minimaList;
/* 2431 */     while (lm != null) {
/* 2432 */       insertScanbeam(lm.y);
/* 2433 */       lm = lm.next;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setHoleState(Edge e, Path.OutRec outRec) {
/* 2438 */     boolean isHole = false;
/* 2439 */     Edge e2 = e.prevInAEL;
/* 2440 */     while (e2 != null) {
/* 2441 */       if (e2.outIdx >= 0 && e2.windDelta != 0) {
/* 2442 */         isHole = !isHole;
/* 2443 */         if (outRec.firstLeft == null) {
/* 2444 */           outRec.firstLeft = this.polyOuts.get(e2.outIdx);
/*      */         }
/*      */       } 
/* 2447 */       e2 = e2.prevInAEL;
/*      */     } 
/* 2449 */     if (isHole) {
/* 2450 */       outRec.isHole = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void setZ(Point.LongPoint pt, Edge e1, Edge e2) {
/* 2455 */     if (pt.getZ() != 0L || this.zFillFunction == null) {
/*      */       return;
/*      */     }
/* 2458 */     if (pt.equals(e1.getBot())) {
/* 2459 */       pt.setZ(Long.valueOf(e1.getBot().getZ()));
/*      */     }
/* 2461 */     else if (pt.equals(e1.getTop())) {
/* 2462 */       pt.setZ(Long.valueOf(e1.getTop().getZ()));
/*      */     }
/* 2464 */     else if (pt.equals(e2.getBot())) {
/* 2465 */       pt.setZ(Long.valueOf(e2.getBot().getZ()));
/*      */     }
/* 2467 */     else if (pt.equals(e2.getTop())) {
/* 2468 */       pt.setZ(Long.valueOf(e2.getTop().getZ()));
/*      */     } else {
/*      */       
/* 2471 */       this.zFillFunction.zFill(e1.getBot(), e1.getTop(), e2.getBot(), e2.getTop(), pt);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void swapPositionsInAEL(Edge edge1, Edge edge2) {
/* 2476 */     LOGGER.entering(DefaultClipper.class.getName(), "swapPositionsInAEL");
/*      */ 
/*      */     
/* 2479 */     if (edge1.nextInAEL == edge1.prevInAEL || edge2.nextInAEL == edge2.prevInAEL) {
/*      */       return;
/*      */     }
/*      */     
/* 2483 */     if (edge1.nextInAEL == edge2) {
/* 2484 */       Edge next = edge2.nextInAEL;
/* 2485 */       if (next != null) {
/* 2486 */         next.prevInAEL = edge1;
/*      */       }
/* 2488 */       Edge prev = edge1.prevInAEL;
/* 2489 */       if (prev != null) {
/* 2490 */         prev.nextInAEL = edge2;
/*      */       }
/* 2492 */       edge2.prevInAEL = prev;
/* 2493 */       edge2.nextInAEL = edge1;
/* 2494 */       edge1.prevInAEL = edge2;
/* 2495 */       edge1.nextInAEL = next;
/*      */     }
/* 2497 */     else if (edge2.nextInAEL == edge1) {
/* 2498 */       Edge next = edge1.nextInAEL;
/* 2499 */       if (next != null) {
/* 2500 */         next.prevInAEL = edge2;
/*      */       }
/* 2502 */       Edge prev = edge2.prevInAEL;
/* 2503 */       if (prev != null) {
/* 2504 */         prev.nextInAEL = edge1;
/*      */       }
/* 2506 */       edge1.prevInAEL = prev;
/* 2507 */       edge1.nextInAEL = edge2;
/* 2508 */       edge2.prevInAEL = edge1;
/* 2509 */       edge2.nextInAEL = next;
/*      */     } else {
/*      */       
/* 2512 */       Edge next = edge1.nextInAEL;
/* 2513 */       Edge prev = edge1.prevInAEL;
/* 2514 */       edge1.nextInAEL = edge2.nextInAEL;
/* 2515 */       if (edge1.nextInAEL != null) {
/* 2516 */         edge1.nextInAEL.prevInAEL = edge1;
/*      */       }
/* 2518 */       edge1.prevInAEL = edge2.prevInAEL;
/* 2519 */       if (edge1.prevInAEL != null) {
/* 2520 */         edge1.prevInAEL.nextInAEL = edge1;
/*      */       }
/* 2522 */       edge2.nextInAEL = next;
/* 2523 */       if (edge2.nextInAEL != null) {
/* 2524 */         edge2.nextInAEL.prevInAEL = edge2;
/*      */       }
/* 2526 */       edge2.prevInAEL = prev;
/* 2527 */       if (edge2.prevInAEL != null) {
/* 2528 */         edge2.prevInAEL.nextInAEL = edge2;
/*      */       }
/*      */     } 
/*      */     
/* 2532 */     if (edge1.prevInAEL == null) {
/* 2533 */       this.activeEdges = edge1;
/*      */     }
/* 2535 */     else if (edge2.prevInAEL == null) {
/* 2536 */       this.activeEdges = edge2;
/*      */     } 
/*      */     
/* 2539 */     LOGGER.exiting(DefaultClipper.class.getName(), "swapPositionsInAEL");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void swapPositionsInSEL(Edge edge1, Edge edge2) {
/* 2545 */     if (edge1.nextInSEL == null && edge1.prevInSEL == null) {
/*      */       return;
/*      */     }
/* 2548 */     if (edge2.nextInSEL == null && edge2.prevInSEL == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2552 */     if (edge1.nextInSEL == edge2) {
/* 2553 */       Edge next = edge2.nextInSEL;
/* 2554 */       if (next != null) {
/* 2555 */         next.prevInSEL = edge1;
/*      */       }
/* 2557 */       Edge prev = edge1.prevInSEL;
/* 2558 */       if (prev != null) {
/* 2559 */         prev.nextInSEL = edge2;
/*      */       }
/* 2561 */       edge2.prevInSEL = prev;
/* 2562 */       edge2.nextInSEL = edge1;
/* 2563 */       edge1.prevInSEL = edge2;
/* 2564 */       edge1.nextInSEL = next;
/*      */     }
/* 2566 */     else if (edge2.nextInSEL == edge1) {
/* 2567 */       Edge next = edge1.nextInSEL;
/* 2568 */       if (next != null) {
/* 2569 */         next.prevInSEL = edge2;
/*      */       }
/* 2571 */       Edge prev = edge2.prevInSEL;
/* 2572 */       if (prev != null) {
/* 2573 */         prev.nextInSEL = edge1;
/*      */       }
/* 2575 */       edge1.prevInSEL = prev;
/* 2576 */       edge1.nextInSEL = edge2;
/* 2577 */       edge2.prevInSEL = edge1;
/* 2578 */       edge2.nextInSEL = next;
/*      */     } else {
/*      */       
/* 2581 */       Edge next = edge1.nextInSEL;
/* 2582 */       Edge prev = edge1.prevInSEL;
/* 2583 */       edge1.nextInSEL = edge2.nextInSEL;
/* 2584 */       if (edge1.nextInSEL != null) {
/* 2585 */         edge1.nextInSEL.prevInSEL = edge1;
/*      */       }
/* 2587 */       edge1.prevInSEL = edge2.prevInSEL;
/* 2588 */       if (edge1.prevInSEL != null) {
/* 2589 */         edge1.prevInSEL.nextInSEL = edge1;
/*      */       }
/* 2591 */       edge2.nextInSEL = next;
/* 2592 */       if (edge2.nextInSEL != null) {
/* 2593 */         edge2.nextInSEL.prevInSEL = edge2;
/*      */       }
/* 2595 */       edge2.prevInSEL = prev;
/* 2596 */       if (edge2.prevInSEL != null) {
/* 2597 */         edge2.prevInSEL.nextInSEL = edge2;
/*      */       }
/*      */     } 
/*      */     
/* 2601 */     if (edge1.prevInSEL == null) {
/* 2602 */       this.sortedEdges = edge1;
/*      */     }
/* 2604 */     else if (edge2.prevInSEL == null) {
/* 2605 */       this.sortedEdges = edge2;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateEdgeIntoAEL(Edge[] eV) {
/* 2610 */     Edge e = eV[0];
/* 2611 */     if (e.nextInLML == null) {
/* 2612 */       throw new IllegalStateException("UpdateEdgeIntoAEL: invalid call");
/*      */     }
/* 2614 */     Edge AelPrev = e.prevInAEL;
/* 2615 */     Edge AelNext = e.nextInAEL;
/* 2616 */     e.nextInLML.outIdx = e.outIdx;
/* 2617 */     if (AelPrev != null) {
/* 2618 */       AelPrev.nextInAEL = e.nextInLML;
/*      */     } else {
/*      */       
/* 2621 */       this.activeEdges = e.nextInLML;
/*      */     } 
/* 2623 */     if (AelNext != null) {
/* 2624 */       AelNext.prevInAEL = e.nextInLML;
/*      */     }
/* 2626 */     e.nextInLML.side = e.side;
/* 2627 */     e.nextInLML.windDelta = e.windDelta;
/* 2628 */     e.nextInLML.windCnt = e.windCnt;
/* 2629 */     e.nextInLML.windCnt2 = e.windCnt2;
/* 2630 */     eV[0] = e = e.nextInLML;
/* 2631 */     e.setCurrent(e.getBot());
/* 2632 */     e.prevInAEL = AelPrev;
/* 2633 */     e.nextInAEL = AelNext;
/* 2634 */     if (!e.isHorizontal()) {
/* 2635 */       insertScanbeam(e.getTop().getY());
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateOutPtIdxs(Path.OutRec outrec) {
/* 2640 */     Path.OutPt op = outrec.getPoints();
/*      */     do {
/* 2642 */       op.idx = outrec.Idx;
/* 2643 */       op = op.prev;
/*      */     }
/* 2645 */     while (op != outrec.getPoints());
/*      */   }
/*      */   
/*      */   private void updateWindingCount(Edge edge) {
/* 2649 */     LOGGER.entering(DefaultClipper.class.getName(), "updateWindingCount");
/*      */     
/* 2651 */     Edge e = edge.prevInAEL;
/*      */     
/* 2653 */     while (e != null && (e.polyTyp != edge.polyTyp || e.windDelta == 0)) {
/* 2654 */       e = e.prevInAEL;
/*      */     }
/* 2656 */     if (e == null) {
/* 2657 */       edge.windCnt = (edge.windDelta == 0) ? 1 : edge.windDelta;
/* 2658 */       edge.windCnt2 = 0;
/* 2659 */       e = this.activeEdges;
/*      */     }
/* 2661 */     else if (edge.windDelta == 0 && this.clipType != IClipper.ClipType.UNION) {
/* 2662 */       edge.windCnt = 1;
/* 2663 */       edge.windCnt2 = e.windCnt2;
/* 2664 */       e = e.nextInAEL;
/*      */     }
/* 2666 */     else if (edge.isEvenOddFillType(this.clipFillType, this.subjFillType)) {
/*      */       
/* 2668 */       if (edge.windDelta == 0) {
/*      */         
/* 2670 */         boolean Inside = true;
/* 2671 */         Edge e2 = e.prevInAEL;
/* 2672 */         while (e2 != null) {
/* 2673 */           if (e2.polyTyp == e.polyTyp && e2.windDelta != 0) {
/* 2674 */             Inside = !Inside;
/*      */           }
/* 2676 */           e2 = e2.prevInAEL;
/*      */         } 
/* 2678 */         edge.windCnt = Inside ? 0 : 1;
/*      */       } else {
/*      */         
/* 2681 */         edge.windCnt = edge.windDelta;
/*      */       } 
/* 2683 */       edge.windCnt2 = e.windCnt2;
/* 2684 */       e = e.nextInAEL;
/*      */     }
/*      */     else {
/*      */       
/* 2688 */       if (e.windCnt * e.windDelta < 0) {
/*      */ 
/*      */         
/* 2691 */         if (Math.abs(e.windCnt) > 1) {
/*      */ 
/*      */           
/* 2694 */           if (e.windDelta * edge.windDelta < 0) {
/* 2695 */             edge.windCnt = e.windCnt;
/*      */           } else {
/*      */             
/* 2698 */             e.windCnt += edge.windDelta;
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2703 */           edge.windCnt = (edge.windDelta == 0) ? 1 : edge.windDelta;
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 2709 */       else if (edge.windDelta == 0) {
/* 2710 */         edge.windCnt = (e.windCnt < 0) ? (e.windCnt - 1) : (e.windCnt + 1);
/*      */       }
/* 2712 */       else if (e.windDelta * edge.windDelta < 0) {
/* 2713 */         edge.windCnt = e.windCnt;
/*      */       } else {
/*      */         
/* 2716 */         e.windCnt += edge.windDelta;
/*      */       } 
/*      */       
/* 2719 */       edge.windCnt2 = e.windCnt2;
/* 2720 */       e = e.nextInAEL;
/*      */     } 
/*      */ 
/*      */     
/* 2724 */     if (edge.isEvenOddAltFillType(this.clipFillType, this.subjFillType)) {
/*      */       
/* 2726 */       while (e != edge) {
/* 2727 */         if (e.windDelta != 0) {
/* 2728 */           edge.windCnt2 = (edge.windCnt2 == 0) ? 1 : 0;
/*      */         }
/* 2730 */         e = e.nextInAEL;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2735 */       while (e != edge) {
/* 2736 */         edge.windCnt2 += e.windDelta;
/* 2737 */         e = e.nextInAEL;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/DefaultClipper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */