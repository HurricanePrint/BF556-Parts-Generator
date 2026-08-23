/*    */ package com.itextpdf.kernel.pdf;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfDashPattern
/*    */ {
/* 49 */   private float dash = -1.0F;
/*    */ 
/*    */   
/* 52 */   private float gap = -1.0F;
/*    */ 
/*    */   
/* 55 */   private float phase = -1.0F;
/*    */ 
/*    */   
/*    */   public PdfDashPattern() {}
/*    */   
/*    */   public PdfDashPattern(float dash) {
/* 61 */     this.dash = dash;
/*    */   }
/*    */   
/*    */   public PdfDashPattern(float dash, float gap) {
/* 65 */     this.dash = dash;
/* 66 */     this.gap = gap;
/*    */   }
/*    */   
/*    */   public PdfDashPattern(float dash, float gap, float phase) {
/* 70 */     this(dash, gap);
/* 71 */     this.phase = phase;
/*    */   }
/*    */   
/*    */   public float getDash() {
/* 75 */     return this.dash;
/*    */   }
/*    */   
/*    */   public float getGap() {
/* 79 */     return this.gap;
/*    */   }
/*    */   
/*    */   public float getPhase() {
/* 83 */     return this.phase;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfDashPattern.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */