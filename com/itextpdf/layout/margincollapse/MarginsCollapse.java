/*    */ package com.itextpdf.layout.margincollapse;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ class MarginsCollapse
/*    */   implements Cloneable, Serializable
/*    */ {
/* 48 */   private float maxPositiveMargin = 0.0F;
/* 49 */   private float minNegativeMargin = 0.0F;
/*    */   
/*    */   void joinMargin(float margin) {
/* 52 */     if (this.maxPositiveMargin < margin) {
/* 53 */       this.maxPositiveMargin = margin;
/* 54 */     } else if (this.minNegativeMargin > margin) {
/* 55 */       this.minNegativeMargin = margin;
/*    */     } 
/*    */   }
/*    */   
/*    */   void joinMargin(MarginsCollapse marginsCollapse) {
/* 60 */     joinMargin(marginsCollapse.maxPositiveMargin);
/* 61 */     joinMargin(marginsCollapse.minNegativeMargin);
/*    */   }
/*    */   
/*    */   float getCollapsedMarginsSize() {
/* 65 */     return this.maxPositiveMargin + this.minNegativeMargin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MarginsCollapse clone() {
/*    */     try {
/* 78 */       return (MarginsCollapse)super.clone();
/* 79 */     } catch (CloneNotSupportedException e) {
/*    */       
/* 81 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/margincollapse/MarginsCollapse.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */