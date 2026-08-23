/*    */ package com.itextpdf.kernel.pdf.canvas.parser.clipper;
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
/*    */ public class LongRect
/*    */ {
/*    */   public long left;
/*    */   public long top;
/*    */   public long right;
/*    */   public long bottom;
/*    */   
/*    */   public LongRect() {}
/*    */   
/*    */   public LongRect(long l, long t, long r, long b) {
/* 45 */     this.left = l;
/* 46 */     this.top = t;
/* 47 */     this.right = r;
/* 48 */     this.bottom = b;
/*    */   }
/*    */   
/*    */   public LongRect(LongRect ir) {
/* 52 */     this.left = ir.left;
/* 53 */     this.top = ir.top;
/* 54 */     this.right = ir.right;
/* 55 */     this.bottom = ir.bottom;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/clipper/LongRect.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */