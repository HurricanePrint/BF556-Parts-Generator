/*     */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Paths
/*     */   extends ArrayList<Path>
/*     */ {
/*     */   private static final long serialVersionUID = 1910552127810480852L;
/*     */   
/*     */   public static Paths closedPathsFromPolyTree(PolyTree polytree) {
/*  45 */     Paths result = new Paths();
/*     */     
/*  47 */     result.addPolyNode(polytree, PolyNode.NodeType.CLOSED);
/*  48 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Paths makePolyTreeToPaths(PolyTree polytree) {
/*  53 */     Paths result = new Paths();
/*     */     
/*  55 */     result.addPolyNode(polytree, PolyNode.NodeType.ANY);
/*  56 */     return result;
/*     */   }
/*     */   
/*     */   public static Paths openPathsFromPolyTree(PolyTree polytree) {
/*  60 */     Paths result = new Paths();
/*     */     
/*  62 */     for (PolyNode c : polytree.getChilds()) {
/*  63 */       if (c.isOpen()) {
/*  64 */         result.add(c.getPolygon());
/*     */       }
/*     */     } 
/*  67 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paths() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paths(int initialCapacity) {
/*  80 */     super(initialCapacity);
/*     */   }
/*     */   
/*     */   public void addPolyNode(PolyNode polynode, PolyNode.NodeType nt) {
/*  84 */     boolean match = true;
/*  85 */     switch (nt) {
/*     */       case OPEN:
/*     */         return;
/*     */       case CLOSED:
/*  89 */         match = !polynode.isOpen();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (polynode.getPolygon().size() > 0 && match) {
/*  96 */       add(polynode.getPolygon());
/*     */     }
/*  98 */     for (PolyNode pn : polynode.getChilds()) {
/*  99 */       addPolyNode(pn, nt);
/*     */     }
/*     */   }
/*     */   
/*     */   public Paths cleanPolygons() {
/* 104 */     return cleanPolygons(1.415D);
/*     */   }
/*     */   
/*     */   public Paths cleanPolygons(double distance) {
/* 108 */     Paths result = new Paths(size());
/* 109 */     for (int i = 0; i < size(); i++) {
/* 110 */       result.add(get(i).cleanPolygon(distance));
/*     */     }
/* 112 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public LongRect getBounds() {
/* 117 */     int i = 0;
/* 118 */     int cnt = size();
/* 119 */     LongRect result = new LongRect();
/* 120 */     while (i < cnt && get(i).isEmpty()) {
/* 121 */       i++;
/*     */     }
/* 123 */     if (i == cnt) {
/* 124 */       return result;
/*     */     }
/*     */     
/* 127 */     result.left = get(i).get(0).getX();
/* 128 */     result.right = result.left;
/* 129 */     result.top = get(i).get(0).getY();
/* 130 */     result.bottom = result.top;
/* 131 */     for (; i < cnt; i++) {
/* 132 */       for (int j = 0; j < get(i).size(); j++) {
/* 133 */         if (get(i).get(j).getX() < result.left) {
/* 134 */           result.left = get(i).get(j).getX();
/*     */         }
/* 136 */         else if (get(i).get(j).getX() > result.right) {
/* 137 */           result.right = get(i).get(j).getX();
/*     */         } 
/* 139 */         if (get(i).get(j).getY() < result.top) {
/* 140 */           result.top = get(i).get(j).getY();
/*     */         }
/* 142 */         else if (get(i).get(j).getY() > result.bottom) {
/* 143 */           result.bottom = get(i).get(j).getY();
/*     */         } 
/*     */       } 
/*     */     } 
/* 147 */     return result;
/*     */   }
/*     */   
/*     */   public void reversePaths() {
/* 151 */     for (Path poly : this)
/* 152 */       poly.reverse(); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/Paths.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */