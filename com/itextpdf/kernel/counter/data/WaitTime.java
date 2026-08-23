/*    */ package com.itextpdf.kernel.counter.data;
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
/*    */ public final class WaitTime
/*    */ {
/*    */   private final long time;
/*    */   private final long initial;
/*    */   private final long maximum;
/*    */   
/*    */   public WaitTime(long initial, long maximum) {
/* 52 */     this(initial, maximum, initial);
/*    */   }
/*    */   
/*    */   public WaitTime(long initial, long maximum, long time) {
/* 56 */     this.initial = initial;
/* 57 */     this.maximum = maximum;
/* 58 */     this.time = time;
/*    */   }
/*    */   
/*    */   public long getInitial() {
/* 62 */     return this.initial;
/*    */   }
/*    */   
/*    */   public long getMaximum() {
/* 66 */     return this.maximum;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 70 */     return this.time;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/data/WaitTime.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */