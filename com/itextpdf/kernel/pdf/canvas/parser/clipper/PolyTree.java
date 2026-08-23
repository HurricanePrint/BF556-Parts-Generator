/*    */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolyTree
/*    */   extends PolyNode
/*    */ {
/* 38 */   private final List<PolyNode> allPolys = new ArrayList<>();
/*    */   
/*    */   public void Clear() {
/* 41 */     this.allPolys.clear();
/* 42 */     this.childs.clear();
/*    */   }
/*    */   
/*    */   public List<PolyNode> getAllPolys() {
/* 46 */     return this.allPolys;
/*    */   }
/*    */   
/*    */   public PolyNode getFirst() {
/* 50 */     if (!this.childs.isEmpty()) {
/* 51 */       return this.childs.get(0);
/*    */     }
/*    */     
/* 54 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTotalSize() {
/* 59 */     int result = this.allPolys.size();
/*    */     
/* 61 */     if (result > 0 && this.childs.get(0) != this.allPolys.get(0)) {
/* 62 */       result--;
/*    */     }
/* 64 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/PolyTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */