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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolyNode
/*     */ {
/*     */   private PolyNode parent;
/*     */   
/*     */   enum NodeType
/*     */   {
/*  43 */     ANY, OPEN, CLOSED;
/*     */   }
/*     */ 
/*     */   
/*  47 */   private final Path polygon = new Path();
/*     */   private int index;
/*     */   private IClipper.JoinType joinType;
/*     */   private IClipper.EndType endType;
/*  51 */   protected final List<PolyNode> childs = new ArrayList<>();
/*     */   private boolean isOpen;
/*     */   
/*     */   public void addChild(PolyNode child) {
/*  55 */     int cnt = this.childs.size();
/*  56 */     this.childs.add(child);
/*  57 */     child.parent = this;
/*  58 */     child.index = cnt;
/*     */   }
/*     */   
/*     */   public int getChildCount() {
/*  62 */     return this.childs.size();
/*     */   }
/*     */   
/*     */   public List<PolyNode> getChilds() {
/*  66 */     return Collections.unmodifiableList(this.childs);
/*     */   }
/*     */   
/*     */   public List<Point.LongPoint> getContour() {
/*  70 */     return this.polygon;
/*     */   }
/*     */   
/*     */   public IClipper.EndType getEndType() {
/*  74 */     return this.endType;
/*     */   }
/*     */   
/*     */   public IClipper.JoinType getJoinType() {
/*  78 */     return this.joinType;
/*     */   }
/*     */   
/*     */   public PolyNode getNext() {
/*  82 */     if (!this.childs.isEmpty()) {
/*  83 */       return this.childs.get(0);
/*     */     }
/*     */     
/*  86 */     return getNextSiblingUp();
/*     */   }
/*     */ 
/*     */   
/*     */   private PolyNode getNextSiblingUp() {
/*  91 */     if (this.parent == null) {
/*  92 */       return null;
/*     */     }
/*  94 */     if (this.index == this.parent.childs.size() - 1) {
/*  95 */       return this.parent.getNextSiblingUp();
/*     */     }
/*     */     
/*  98 */     return this.parent.childs.get(this.index + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public PolyNode getParent() {
/* 103 */     return this.parent;
/*     */   }
/*     */   
/*     */   public Path getPolygon() {
/* 107 */     return this.polygon;
/*     */   }
/*     */   
/*     */   public boolean isHole() {
/* 111 */     return isHoleNode();
/*     */   }
/*     */   
/*     */   private boolean isHoleNode() {
/* 115 */     boolean result = true;
/* 116 */     PolyNode node = this.parent;
/* 117 */     while (node != null) {
/* 118 */       result = !result;
/* 119 */       node = node.parent;
/*     */     } 
/* 121 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 125 */     return this.isOpen;
/*     */   }
/*     */   
/*     */   public void setEndType(IClipper.EndType value) {
/* 129 */     this.endType = value;
/*     */   }
/*     */   
/*     */   public void setJoinType(IClipper.JoinType value) {
/* 133 */     this.joinType = value;
/*     */   }
/*     */   
/*     */   public void setOpen(boolean isOpen) {
/* 137 */     this.isOpen = isOpen;
/*     */   }
/*     */   
/*     */   public void setParent(PolyNode n) {
/* 141 */     this.parent = n;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/PolyNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */