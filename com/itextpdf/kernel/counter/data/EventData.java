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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventData<T>
/*    */ {
/*    */   private final T signature;
/*    */   private long count;
/*    */   
/*    */   public EventData(T signature) {
/* 60 */     this(signature, 1L);
/*    */   }
/*    */   
/*    */   public EventData(T signature, long count) {
/* 64 */     this.signature = signature;
/* 65 */     this.count = count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final T getSignature() {
/* 74 */     return this.signature;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final long getCount() {
/* 83 */     return this.count;
/*    */   }
/*    */   
/*    */   protected void mergeWith(EventData<T> data) {
/* 87 */     this.count += data.getCount();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/data/EventData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */