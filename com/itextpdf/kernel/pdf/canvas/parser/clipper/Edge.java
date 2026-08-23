/*     */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*     */ 
/*     */ import java.math.BigInteger;
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
/*     */ class Edge
/*     */ {
/*     */   private final Point.LongPoint bot;
/*     */   private final Point.LongPoint current;
/*     */   private final Point.LongPoint top;
/*     */   private final Point.LongPoint delta;
/*     */   double deltaX;
/*     */   IClipper.PolyType polyTyp;
/*     */   Side side;
/*     */   int windDelta;
/*     */   int windCnt;
/*     */   int windCnt2;
/*     */   int outIdx;
/*     */   Edge next;
/*     */   Edge prev;
/*     */   Edge nextInLML;
/*     */   Edge nextInAEL;
/*     */   Edge prevInAEL;
/*     */   Edge nextInSEL;
/*     */   Edge prevInSEL;
/*     */   protected static final int SKIP = -2;
/*     */   protected static final int UNASSIGNED = -1;
/*     */   protected static final double HORIZONTAL = -3.4E38D;
/*     */   
/*     */   enum Side
/*     */   {
/*  45 */     LEFT, RIGHT;
/*     */   }
/*     */   
/*     */   static boolean doesE2InsertBeforeE1(Edge e1, Edge e2) {
/*  49 */     if (e2.current.getX() == e1.current.getX()) {
/*  50 */       if (e2.top.getY() > e1.top.getY()) {
/*  51 */         return (e2.top.getX() < topX(e1, e2.top.getY()));
/*     */       }
/*     */       
/*  54 */       return (e1.top.getX() > topX(e2, e1.top.getY()));
/*     */     } 
/*     */ 
/*     */     
/*  58 */     return (e2.current.getX() < e1.current.getX());
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean slopesEqual(Edge e1, Edge e2, boolean useFullRange) {
/*  63 */     if (useFullRange) {
/*  64 */       return BigInteger.valueOf(e1.getDelta().getY()).multiply(BigInteger.valueOf(e2.getDelta().getX())).equals(
/*  65 */           BigInteger.valueOf(e1.getDelta().getX()).multiply(BigInteger.valueOf(e2.getDelta().getY())));
/*     */     }
/*  67 */     return (e1.getDelta().getY() * e2.getDelta().getX() == e1.getDelta().getX() * e2.getDelta().getY());
/*     */   }
/*     */ 
/*     */   
/*     */   static void swapPolyIndexes(Edge edge1, Edge edge2) {
/*  72 */     int outIdx = edge1.outIdx;
/*  73 */     edge1.outIdx = edge2.outIdx;
/*  74 */     edge2.outIdx = outIdx;
/*     */   }
/*     */   
/*     */   static void swapSides(Edge edge1, Edge edge2) {
/*  78 */     Side side = edge1.side;
/*  79 */     edge1.side = edge2.side;
/*  80 */     edge2.side = side;
/*     */   }
/*     */   
/*     */   static long topX(Edge edge, long currentY) {
/*  84 */     if (currentY == edge.getTop().getY()) {
/*  85 */       return edge.getTop().getX();
/*     */     }
/*  87 */     return edge.getBot().getX() + Math.round(edge.deltaX * (currentY - edge.getBot().getY()));
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
/* 122 */   private static final Logger LOGGER = Logger.getLogger(Edge.class.getName());
/*     */   
/*     */   public Edge() {
/* 125 */     this.delta = new Point.LongPoint();
/* 126 */     this.top = new Point.LongPoint();
/* 127 */     this.bot = new Point.LongPoint();
/* 128 */     this.current = new Point.LongPoint();
/*     */   }
/*     */   
/*     */   public Edge findNextLocMin() {
/* 132 */     Edge e = this;
/*     */     
/*     */     while (true) {
/* 135 */       if (!e.bot.equals(e.prev.bot) || e.current.equals(e.top)) {
/* 136 */         e = e.next; continue;
/*     */       } 
/* 138 */       if (e.deltaX != -3.4E38D && e.prev.deltaX != -3.4E38D) {
/*     */         break;
/*     */       }
/* 141 */       while (e.prev.deltaX == -3.4E38D) {
/* 142 */         e = e.prev;
/*     */       }
/* 144 */       Edge e2 = e;
/* 145 */       while (e.deltaX == -3.4E38D) {
/* 146 */         e = e.next;
/*     */       }
/* 148 */       if (e.top.getY() == e.prev.bot.getY()) {
/*     */         continue;
/*     */       }
/* 151 */       if (e2.prev.bot.getX() < e.bot.getX()) {
/* 152 */         e = e2;
/*     */       }
/*     */       break;
/*     */     } 
/* 156 */     return e;
/*     */   }
/*     */   
/*     */   public Point.LongPoint getBot() {
/* 160 */     return this.bot;
/*     */   }
/*     */   
/*     */   public Point.LongPoint getCurrent() {
/* 164 */     return this.current;
/*     */   }
/*     */   
/*     */   public Point.LongPoint getDelta() {
/* 168 */     return this.delta;
/*     */   }
/*     */   
/*     */   public Edge getMaximaPair() {
/* 172 */     Edge result = null;
/* 173 */     if (this.next.top.equals(this.top) && this.next.nextInLML == null) {
/* 174 */       result = this.next;
/*     */     }
/* 176 */     else if (this.prev.top.equals(this.top) && this.prev.nextInLML == null) {
/* 177 */       result = this.prev;
/*     */     } 
/* 179 */     if (result != null && (result.outIdx == -2 || (result.nextInAEL == result.prevInAEL && !result.isHorizontal()))) {
/* 180 */       return null;
/*     */     }
/* 182 */     return result;
/*     */   }
/*     */   
/*     */   public Edge getNextInAEL(IClipper.Direction direction) {
/* 186 */     return (direction == IClipper.Direction.LEFT_TO_RIGHT) ? this.nextInAEL : this.prevInAEL;
/*     */   }
/*     */   
/*     */   public Point.LongPoint getTop() {
/* 190 */     return this.top;
/*     */   }
/*     */   public boolean isContributing(IClipper.PolyFillType clipFillType, IClipper.PolyFillType subjFillType, IClipper.ClipType clipType) {
/*     */     IClipper.PolyFillType pft, pft2;
/* 194 */     LOGGER.entering(Edge.class.getName(), "isContributing");
/*     */ 
/*     */     
/* 197 */     if (this.polyTyp == IClipper.PolyType.SUBJECT) {
/* 198 */       pft = subjFillType;
/* 199 */       pft2 = clipFillType;
/*     */     } else {
/*     */       
/* 202 */       pft = clipFillType;
/* 203 */       pft2 = subjFillType;
/*     */     } 
/*     */     
/* 206 */     switch (pft) {
/*     */       
/*     */       case INTERSECTION:
/* 209 */         if (this.windDelta == 0 && this.windCnt != 1) {
/* 210 */           return false;
/*     */         }
/*     */         break;
/*     */       case UNION:
/* 214 */         if (Math.abs(this.windCnt) != 1) {
/* 215 */           return false;
/*     */         }
/*     */         break;
/*     */       case DIFFERENCE:
/* 219 */         if (this.windCnt != 1) {
/* 220 */           return false;
/*     */         }
/*     */         break;
/*     */       default:
/* 224 */         if (this.windCnt != -1) {
/* 225 */           return false;
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/* 230 */     switch (clipType) {
/*     */       case INTERSECTION:
/* 232 */         switch (pft2) {
/*     */           case INTERSECTION:
/*     */           case UNION:
/* 235 */             return (this.windCnt2 != 0);
/*     */           case DIFFERENCE:
/* 237 */             return (this.windCnt2 > 0);
/*     */         } 
/* 239 */         return (this.windCnt2 < 0);
/*     */       
/*     */       case UNION:
/* 242 */         switch (pft2) {
/*     */           case INTERSECTION:
/*     */           case UNION:
/* 245 */             return (this.windCnt2 == 0);
/*     */           case DIFFERENCE:
/* 247 */             return (this.windCnt2 <= 0);
/*     */         } 
/* 249 */         return (this.windCnt2 >= 0);
/*     */       
/*     */       case DIFFERENCE:
/* 252 */         if (this.polyTyp == IClipper.PolyType.SUBJECT) {
/* 253 */           switch (pft2) {
/*     */             case INTERSECTION:
/*     */             case UNION:
/* 256 */               return (this.windCnt2 == 0);
/*     */             case DIFFERENCE:
/* 258 */               return (this.windCnt2 <= 0);
/*     */           } 
/* 260 */           return (this.windCnt2 >= 0);
/*     */         } 
/*     */ 
/*     */         
/* 264 */         switch (pft2) {
/*     */           case INTERSECTION:
/*     */           case UNION:
/* 267 */             return (this.windCnt2 != 0);
/*     */           case DIFFERENCE:
/* 269 */             return (this.windCnt2 > 0);
/*     */         } 
/* 271 */         return (this.windCnt2 < 0);
/*     */ 
/*     */       
/*     */       case XOR:
/* 275 */         if (this.windDelta == 0) {
/* 276 */           switch (pft2) {
/*     */             case INTERSECTION:
/*     */             case UNION:
/* 279 */               return (this.windCnt2 == 0);
/*     */             case DIFFERENCE:
/* 281 */               return (this.windCnt2 <= 0);
/*     */           } 
/* 283 */           return (this.windCnt2 >= 0);
/*     */         } 
/*     */ 
/*     */         
/* 287 */         return true;
/*     */     } 
/*     */     
/* 290 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isEvenOddAltFillType(IClipper.PolyFillType clipFillType, IClipper.PolyFillType subjFillType) {
/* 294 */     if (this.polyTyp == IClipper.PolyType.SUBJECT) {
/* 295 */       return (clipFillType == IClipper.PolyFillType.EVEN_ODD);
/*     */     }
/*     */     
/* 298 */     return (subjFillType == IClipper.PolyFillType.EVEN_ODD);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEvenOddFillType(IClipper.PolyFillType clipFillType, IClipper.PolyFillType subjFillType) {
/* 303 */     if (this.polyTyp == IClipper.PolyType.SUBJECT) {
/* 304 */       return (subjFillType == IClipper.PolyFillType.EVEN_ODD);
/*     */     }
/*     */     
/* 307 */     return (clipFillType == IClipper.PolyFillType.EVEN_ODD);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHorizontal() {
/* 312 */     return (this.delta.getY() == 0L);
/*     */   }
/*     */   
/*     */   public boolean isIntermediate(double y) {
/* 316 */     return (this.top.getY() == y && this.nextInLML != null);
/*     */   }
/*     */   
/*     */   public boolean isMaxima(double Y) {
/* 320 */     return (this.top.getY() == Y && this.nextInLML == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reverseHorizontal() {
/* 327 */     long temp = this.top.getX();
/* 328 */     this.top.setX(Long.valueOf(this.bot.getX()));
/* 329 */     this.bot.setX(Long.valueOf(temp));
/*     */     
/* 331 */     temp = this.top.getZ();
/* 332 */     this.top.setZ(Long.valueOf(this.bot.getZ()));
/* 333 */     this.bot.setZ(Long.valueOf(temp));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBot(Point.LongPoint bot) {
/* 338 */     this.bot.set(bot);
/*     */   }
/*     */   
/*     */   public void setCurrent(Point.LongPoint current) {
/* 342 */     this.current.set(current);
/*     */   }
/*     */   
/*     */   public void setTop(Point.LongPoint top) {
/* 346 */     this.top.set(top);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 351 */     return "TEdge [Bot=" + this.bot + ", Curr=" + this.current + ", Top=" + this.top + ", Delta=" + this.delta + ", Dx=" + this.deltaX + ", PolyTyp=" + this.polyTyp + ", Side=" + this.side + ", WindDelta=" + this.windDelta + ", WindCnt=" + this.windCnt + ", WindCnt2=" + this.windCnt2 + ", OutIdx=" + this.outIdx + ", Next=" + this.next + ", Prev=" + this.prev + ", NextInLML=" + this.nextInLML + ", NextInAEL=" + this.nextInAEL + ", PrevInAEL=" + this.prevInAEL + ", NextInSEL=" + this.nextInSEL + ", PrevInSEL=" + this.prevInSEL + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateDeltaX() {
/* 359 */     this.delta.setX(Long.valueOf(this.top.getX() - this.bot.getX()));
/* 360 */     this.delta.setY(Long.valueOf(this.top.getY() - this.bot.getY()));
/* 361 */     if (this.delta.getY() == 0L) {
/* 362 */       this.deltaX = -3.4E38D;
/*     */     } else {
/*     */       
/* 365 */       this.deltaX = this.delta.getX() / this.delta.getY();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/Edge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */