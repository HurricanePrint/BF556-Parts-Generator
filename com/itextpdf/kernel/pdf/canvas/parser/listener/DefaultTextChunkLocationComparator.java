/*    */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ class DefaultTextChunkLocationComparator
/*    */   implements Comparator<ITextChunkLocation>
/*    */ {
/*    */   private boolean leftToRight = true;
/*    */   
/*    */   public DefaultTextChunkLocationComparator() {
/* 51 */     this(true);
/*    */   }
/*    */   
/*    */   public DefaultTextChunkLocationComparator(boolean leftToRight) {
/* 55 */     this.leftToRight = leftToRight;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(ITextChunkLocation first, ITextChunkLocation second) {
/* 61 */     if (first == second) return 0;
/*    */ 
/*    */     
/* 64 */     int result = Integer.compare(first.orientationMagnitude(), second.orientationMagnitude());
/* 65 */     if (result != 0) {
/* 66 */       return result;
/*    */     }
/*    */     
/* 69 */     int distPerpendicularDiff = first.distPerpendicular() - second.distPerpendicular();
/* 70 */     if (distPerpendicularDiff != 0) {
/* 71 */       return distPerpendicularDiff;
/*    */     }
/*    */     
/* 74 */     return this.leftToRight ? Float.compare(first.distParallelStart(), second.distParallelStart()) : 
/* 75 */       -Float.compare(first.distParallelEnd(), second.distParallelEnd());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/DefaultTextChunkLocationComparator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */