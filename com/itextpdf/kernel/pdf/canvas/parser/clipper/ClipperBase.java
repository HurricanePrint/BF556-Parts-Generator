/*     */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ClipperBase
/*     */   implements IClipper
/*     */ {
/*     */   private static final long LOW_RANGE = 1073741823L;
/*     */   private static final long HI_RANGE = 4611686018427387903L;
/*     */   protected LocalMinima minimaList;
/*     */   protected LocalMinima currentLM;
/*     */   private final List<List<Edge>> edges;
/*     */   protected boolean useFullRange;
/*     */   protected boolean hasOpenPaths;
/*     */   protected final boolean preserveCollinear;
/*     */   
/*     */   protected class LocalMinima
/*     */   {
/*     */     long y;
/*     */     Edge leftBound;
/*     */     Edge rightBound;
/*     */     LocalMinima next;
/*     */   }
/*     */   
/*     */   protected class Scanbeam
/*     */   {
/*     */     long y;
/*     */     Scanbeam next;
/*     */   }
/*     */   
/*     */   private static void initEdge(Edge e, Edge eNext, Edge ePrev, Point.LongPoint pt) {
/*  54 */     e.next = eNext;
/*  55 */     e.prev = ePrev;
/*  56 */     e.setCurrent(new Point.LongPoint(pt));
/*  57 */     e.outIdx = -1;
/*     */   }
/*     */   
/*     */   private static void initEdge2(Edge e, IClipper.PolyType polyType) {
/*  61 */     if (e.getCurrent().getY() >= e.next.getCurrent().getY()) {
/*  62 */       e.setBot(new Point.LongPoint(e.getCurrent()));
/*  63 */       e.setTop(new Point.LongPoint(e.next.getCurrent()));
/*     */     } else {
/*     */       
/*  66 */       e.setTop(new Point.LongPoint(e.getCurrent()));
/*  67 */       e.setBot(new Point.LongPoint(e.next.getCurrent()));
/*     */     } 
/*  69 */     e.updateDeltaX();
/*  70 */     e.polyTyp = polyType;
/*     */   }
/*     */   
/*     */   private static boolean rangeTest(Point.LongPoint Pt, boolean useFullRange) {
/*  74 */     if (useFullRange) {
/*  75 */       if (Pt.getX() > 4611686018427387903L || Pt.getY() > 4611686018427387903L || -Pt.getX() > 4611686018427387903L || -Pt.getY() > 4611686018427387903L)
/*  76 */         throw new ClipperException("Coordinate outside allowed range."); 
/*  77 */     } else if (Pt.getX() > 1073741823L || Pt.getY() > 1073741823L || -Pt.getX() > 1073741823L || -Pt.getY() > 1073741823L) {
/*  78 */       return rangeTest(Pt, true);
/*     */     } 
/*     */     
/*  81 */     return useFullRange;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Edge removeEdge(Edge e) {
/*  86 */     e.prev.next = e.next;
/*  87 */     e.next.prev = e.prev;
/*  88 */     Edge result = e.next;
/*  89 */     e.prev = null;
/*  90 */     return result;
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
/* 109 */   private static final Logger LOGGER = Logger.getLogger(IClipper.class.getName());
/*     */ 
/*     */   
/*     */   protected ClipperBase(boolean preserveCollinear) {
/* 113 */     this.preserveCollinear = preserveCollinear;
/* 114 */     this.minimaList = null;
/* 115 */     this.currentLM = null;
/* 116 */     this.hasOpenPaths = false;
/* 117 */     this.edges = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addPath(Path pg, IClipper.PolyType polyType, boolean Closed) {
/* 122 */     if (!Closed && polyType == IClipper.PolyType.CLIP) {
/* 123 */       throw new IllegalStateException("AddPath: Open paths must be subject.");
/*     */     }
/*     */     
/* 126 */     int highI = pg.size() - 1;
/* 127 */     if (Closed) {
/* 128 */       while (highI > 0 && pg.get(highI).equals(pg.get(0))) {
/* 129 */         highI--;
/*     */       }
/*     */     }
/* 132 */     while (highI > 0 && pg.get(highI).equals(pg.get(highI - 1))) {
/* 133 */       highI--;
/*     */     }
/* 135 */     if ((Closed && highI < 2) || (!Closed && highI < 1)) {
/* 136 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 140 */     List<Edge> edges = new ArrayList<>(highI + 1);
/* 141 */     for (int i = 0; i <= highI; i++) {
/* 142 */       edges.add(new Edge());
/*     */     }
/*     */     
/* 145 */     boolean IsFlat = true;
/*     */ 
/*     */     
/* 148 */     ((Edge)edges.get(1)).setCurrent(new Point.LongPoint(pg.get(1)));
/* 149 */     this.useFullRange = rangeTest(pg.get(0), this.useFullRange);
/* 150 */     this.useFullRange = rangeTest(pg.get(highI), this.useFullRange);
/* 151 */     initEdge(edges.get(0), edges.get(1), edges.get(highI), pg.get(0));
/* 152 */     initEdge(edges.get(highI), edges.get(0), edges.get(highI - 1), pg.get(highI));
/* 153 */     for (int j = highI - 1; j >= 1; j--) {
/* 154 */       this.useFullRange = rangeTest(pg.get(j), this.useFullRange);
/* 155 */       initEdge(edges.get(j), edges.get(j + 1), edges.get(j - 1), pg.get(j));
/*     */     } 
/* 157 */     Edge eStart = edges.get(0);
/*     */ 
/*     */     
/* 160 */     Edge e = eStart, eLoopStop = eStart;
/*     */     
/*     */     while (true) {
/* 163 */       if (e.getCurrent().equals(e.next.getCurrent()) && (Closed || !e.next.equals(eStart))) {
/* 164 */         if (e == e.next) {
/*     */           break;
/*     */         }
/* 167 */         if (e == eStart) {
/* 168 */           eStart = e.next;
/*     */         }
/* 170 */         e = removeEdge(e);
/* 171 */         eLoopStop = e;
/*     */         continue;
/*     */       } 
/* 174 */       if (e.prev == e.next) {
/*     */         break;
/*     */       }
/* 177 */       if (Closed && Point.slopesEqual(e.prev.getCurrent(), e.getCurrent(), e.next.getCurrent(), this.useFullRange) && (
/* 178 */         !isPreserveCollinear() || !Point.isPt2BetweenPt1AndPt3(e.prev.getCurrent(), e.getCurrent(), e.next.getCurrent()))) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 183 */         if (e == eStart) {
/* 184 */           eStart = e.next;
/*     */         }
/* 186 */         e = removeEdge(e);
/* 187 */         e = e.prev;
/* 188 */         eLoopStop = e;
/*     */         continue;
/*     */       } 
/* 191 */       e = e.next;
/* 192 */       if (e == eLoopStop || (!Closed && e.next == eStart)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 197 */     if ((!Closed && e == e.next) || (Closed && e.prev == e.next)) {
/* 198 */       return false;
/*     */     }
/*     */     
/* 201 */     if (!Closed) {
/* 202 */       this.hasOpenPaths = true;
/* 203 */       eStart.prev.outIdx = -2;
/*     */     } 
/*     */ 
/*     */     
/* 207 */     e = eStart;
/*     */     do {
/* 209 */       initEdge2(e, polyType);
/* 210 */       e = e.next;
/* 211 */       if (!IsFlat || e.getCurrent().getY() == eStart.getCurrent().getY())
/* 212 */         continue;  IsFlat = false;
/*     */     
/*     */     }
/* 215 */     while (e != eStart);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     if (IsFlat) {
/* 222 */       if (Closed) {
/* 223 */         return false;
/*     */       }
/* 225 */       e.prev.outIdx = -2;
/* 226 */       LocalMinima locMin = new LocalMinima();
/* 227 */       locMin.next = null;
/* 228 */       locMin.y = e.getBot().getY();
/* 229 */       locMin.leftBound = null;
/* 230 */       locMin.rightBound = e;
/* 231 */       locMin.rightBound.side = Edge.Side.RIGHT;
/* 232 */       locMin.rightBound.windDelta = 0;
/*     */       
/*     */       while (true) {
/* 235 */         if (e.getBot().getX() != e.prev.getTop().getX()) e.reverseHorizontal(); 
/* 236 */         if (e.next.outIdx == -2)
/* 237 */           break;  e.nextInLML = e.next;
/* 238 */         e = e.next;
/*     */       } 
/* 240 */       insertLocalMinima(locMin);
/* 241 */       this.edges.add(edges);
/* 242 */       return true;
/*     */     } 
/*     */     
/* 245 */     this.edges.add(edges);
/*     */     
/* 247 */     Edge EMin = null;
/*     */ 
/*     */ 
/*     */     
/* 251 */     if (e.prev.getBot().equals(e.prev.getTop())) {
/* 252 */       e = e.next;
/*     */     }
/*     */     while (true) {
/*     */       boolean leftBoundIsForward;
/* 256 */       e = e.findNextLocMin();
/* 257 */       if (e == EMin) {
/*     */         break;
/*     */       }
/* 260 */       if (EMin == null) {
/* 261 */         EMin = e;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 266 */       LocalMinima locMin = new LocalMinima();
/* 267 */       locMin.next = null;
/* 268 */       locMin.y = e.getBot().getY();
/* 269 */       if (e.deltaX < e.prev.deltaX) {
/* 270 */         locMin.leftBound = e.prev;
/* 271 */         locMin.rightBound = e;
/* 272 */         leftBoundIsForward = false;
/*     */       } else {
/*     */         
/* 275 */         locMin.leftBound = e;
/* 276 */         locMin.rightBound = e.prev;
/* 277 */         leftBoundIsForward = true;
/*     */       } 
/* 279 */       locMin.leftBound.side = Edge.Side.LEFT;
/* 280 */       locMin.rightBound.side = Edge.Side.RIGHT;
/*     */       
/* 282 */       if (!Closed) {
/* 283 */         locMin.leftBound.windDelta = 0;
/*     */       }
/* 285 */       else if (locMin.leftBound.next == locMin.rightBound) {
/* 286 */         locMin.leftBound.windDelta = -1;
/*     */       } else {
/*     */         
/* 289 */         locMin.leftBound.windDelta = 1;
/*     */       } 
/* 291 */       locMin.rightBound.windDelta = -locMin.leftBound.windDelta;
/*     */       
/* 293 */       e = processBound(locMin.leftBound, leftBoundIsForward);
/* 294 */       if (e.outIdx == -2) {
/* 295 */         e = processBound(e, leftBoundIsForward);
/*     */       }
/*     */       
/* 298 */       Edge E2 = processBound(locMin.rightBound, !leftBoundIsForward);
/* 299 */       if (E2.outIdx == -2) {
/* 300 */         E2 = processBound(E2, !leftBoundIsForward);
/*     */       }
/*     */       
/* 303 */       if (locMin.leftBound.outIdx == -2) {
/* 304 */         locMin.leftBound = null;
/*     */       }
/* 306 */       else if (locMin.rightBound.outIdx == -2) {
/* 307 */         locMin.rightBound = null;
/*     */       } 
/* 309 */       insertLocalMinima(locMin);
/* 310 */       if (!leftBoundIsForward) {
/* 311 */         e = E2;
/*     */       }
/*     */     } 
/* 314 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addPaths(Paths ppg, IClipper.PolyType polyType, boolean closed) {
/* 319 */     boolean result = false;
/* 320 */     for (int i = 0; i < ppg.size(); i++) {
/* 321 */       if (addPath(ppg.get(i), polyType, closed)) {
/* 322 */         result = true;
/*     */       }
/*     */     } 
/* 325 */     return result;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 329 */     disposeLocalMinimaList();
/* 330 */     this.edges.clear();
/* 331 */     this.useFullRange = false;
/* 332 */     this.hasOpenPaths = false;
/*     */   }
/*     */   
/*     */   private void disposeLocalMinimaList() {
/* 336 */     while (this.minimaList != null) {
/* 337 */       LocalMinima tmpLm = this.minimaList.next;
/* 338 */       this.minimaList = null;
/* 339 */       this.minimaList = tmpLm;
/*     */     } 
/* 341 */     this.currentLM = null;
/*     */   }
/*     */   
/*     */   private void insertLocalMinima(LocalMinima newLm) {
/* 345 */     if (this.minimaList == null) {
/* 346 */       this.minimaList = newLm;
/*     */     }
/* 348 */     else if (newLm.y >= this.minimaList.y) {
/* 349 */       newLm.next = this.minimaList;
/* 350 */       this.minimaList = newLm;
/*     */     } else {
/*     */       
/* 353 */       LocalMinima tmpLm = this.minimaList;
/* 354 */       while (tmpLm.next != null && newLm.y < tmpLm.next.y) {
/* 355 */         tmpLm = tmpLm.next;
/*     */       }
/* 357 */       newLm.next = tmpLm.next;
/* 358 */       tmpLm.next = newLm;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPreserveCollinear() {
/* 363 */     return this.preserveCollinear;
/*     */   }
/*     */   
/*     */   protected void popLocalMinima() {
/* 367 */     LOGGER.entering(ClipperBase.class.getName(), "popLocalMinima");
/* 368 */     if (this.currentLM == null) {
/*     */       return;
/*     */     }
/* 371 */     this.currentLM = this.currentLM.next;
/*     */   }
/*     */   
/*     */   private Edge processBound(Edge e, boolean LeftBoundIsForward) {
/* 375 */     Edge result = e;
/*     */ 
/*     */     
/* 378 */     if (result.outIdx == -2) {
/*     */ 
/*     */       
/* 381 */       e = result;
/* 382 */       if (LeftBoundIsForward) {
/* 383 */         while (e.getTop().getY() == e.next.getBot().getY()) {
/* 384 */           e = e.next;
/*     */         }
/* 386 */         while (e != result && e.deltaX == -3.4E38D) {
/* 387 */           e = e.prev;
/*     */         }
/*     */       } else {
/*     */         
/* 391 */         while (e.getTop().getY() == e.prev.getBot().getY()) {
/* 392 */           e = e.prev;
/*     */         }
/* 394 */         while (e != result && e.deltaX == -3.4E38D) {
/* 395 */           e = e.next;
/*     */         }
/*     */       } 
/* 398 */       if (e == result) {
/* 399 */         if (LeftBoundIsForward) {
/* 400 */           result = e.next;
/*     */         } else {
/*     */           
/* 403 */           result = e.prev;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 408 */         if (LeftBoundIsForward) {
/* 409 */           e = result.next;
/*     */         } else {
/*     */           
/* 412 */           e = result.prev;
/*     */         } 
/* 414 */         LocalMinima locMin = new LocalMinima();
/* 415 */         locMin.next = null;
/* 416 */         locMin.y = e.getBot().getY();
/* 417 */         locMin.leftBound = null;
/* 418 */         locMin.rightBound = e;
/* 419 */         e.windDelta = 0;
/* 420 */         result = processBound(e, LeftBoundIsForward);
/* 421 */         insertLocalMinima(locMin);
/*     */       } 
/* 423 */       return result;
/*     */     } 
/*     */     
/* 426 */     if (e.deltaX == -3.4E38D) {
/*     */       Edge edge;
/*     */ 
/*     */       
/* 430 */       if (LeftBoundIsForward) {
/* 431 */         edge = e.prev;
/*     */       } else {
/*     */         
/* 434 */         edge = e.next;
/*     */       } 
/* 436 */       if (edge.deltaX == -3.4E38D) {
/*     */         
/* 438 */         if (edge.getBot().getX() != e.getBot().getX() && edge.getTop().getX() != e.getBot().getX()) {
/* 439 */           e.reverseHorizontal();
/*     */         }
/* 441 */       } else if (edge.getBot().getX() != e.getBot().getX()) {
/* 442 */         e.reverseHorizontal();
/*     */       } 
/*     */     } 
/* 445 */     Edge EStart = e;
/* 446 */     if (LeftBoundIsForward) {
/* 447 */       while (result.getTop().getY() == result.next.getBot().getY() && result.next.outIdx != -2) {
/* 448 */         result = result.next;
/*     */       }
/* 450 */       if (result.deltaX == -3.4E38D && result.next.outIdx != -2) {
/*     */ 
/*     */ 
/*     */         
/* 454 */         Edge Horz = result;
/* 455 */         while (Horz.prev.deltaX == -3.4E38D) {
/* 456 */           Horz = Horz.prev;
/*     */         }
/* 458 */         if (Horz.prev.getTop().getX() > result.next.getTop().getX()) result = Horz.prev; 
/*     */       } 
/* 460 */       while (e != result) {
/* 461 */         e.nextInLML = e.next;
/* 462 */         if (e.deltaX == -3.4E38D && e != EStart && e.getBot().getX() != e.prev.getTop().getX()) {
/* 463 */           e.reverseHorizontal();
/*     */         }
/* 465 */         e = e.next;
/*     */       } 
/* 467 */       if (e.deltaX == -3.4E38D && e != EStart && e.getBot().getX() != e.prev.getTop().getX()) {
/* 468 */         e.reverseHorizontal();
/*     */       }
/* 470 */       result = result.next;
/*     */     } else {
/*     */       
/* 473 */       while (result.getTop().getY() == result.prev.getBot().getY() && result.prev.outIdx != -2) {
/* 474 */         result = result.prev;
/*     */       }
/* 476 */       if (result.deltaX == -3.4E38D && result.prev.outIdx != -2) {
/* 477 */         Edge Horz = result;
/* 478 */         while (Horz.next.deltaX == -3.4E38D) {
/* 479 */           Horz = Horz.next;
/*     */         }
/* 481 */         if (Horz.next.getTop().getX() == result.prev.getTop().getX() || Horz.next
/* 482 */           .getTop().getX() > result.prev.getTop().getX()) result = Horz.next;
/*     */       
/*     */       } 
/* 485 */       while (e != result) {
/* 486 */         e.nextInLML = e.prev;
/* 487 */         if (e.deltaX == -3.4E38D && e != EStart && e.getBot().getX() != e.next.getTop().getX()) {
/* 488 */           e.reverseHorizontal();
/*     */         }
/* 490 */         e = e.prev;
/*     */       } 
/* 492 */       if (e.deltaX == -3.4E38D && e != EStart && e.getBot().getX() != e.next.getTop().getX()) {
/* 493 */         e.reverseHorizontal();
/*     */       }
/* 495 */       result = result.prev;
/*     */     } 
/* 497 */     return result;
/*     */   }
/*     */   
/*     */   protected static Path.OutRec parseFirstLeft(Path.OutRec FirstLeft) {
/* 501 */     while (FirstLeft != null && FirstLeft.getPoints() == null)
/* 502 */       FirstLeft = FirstLeft.firstLeft; 
/* 503 */     return FirstLeft;
/*     */   }
/*     */   
/*     */   protected void reset() {
/* 507 */     this.currentLM = this.minimaList;
/* 508 */     if (this.currentLM == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 513 */     LocalMinima lm = this.minimaList;
/* 514 */     while (lm != null) {
/* 515 */       Edge e = lm.leftBound;
/* 516 */       if (e != null) {
/* 517 */         e.setCurrent(new Point.LongPoint(e.getBot()));
/* 518 */         e.side = Edge.Side.LEFT;
/* 519 */         e.outIdx = -1;
/*     */       } 
/* 521 */       e = lm.rightBound;
/* 522 */       if (e != null) {
/* 523 */         e.setCurrent(new Point.LongPoint(e.getBot()));
/* 524 */         e.side = Edge.Side.RIGHT;
/* 525 */         e.outIdx = -1;
/*     */       } 
/* 527 */       lm = lm.next;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/ClipperBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */