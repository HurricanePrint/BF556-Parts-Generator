/*    */ package com.itextpdf.kernel.counter;
/*    */ 
/*    */ import com.itextpdf.kernel.counter.context.IContext;
/*    */ import com.itextpdf.kernel.counter.context.UnknownContext;
/*    */ import com.itextpdf.kernel.counter.event.IEvent;
/*    */ import com.itextpdf.kernel.counter.event.IMetaInfo;
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
/*    */ 
/*    */ 
/*    */ public abstract class EventCounter
/*    */ {
/*    */   final IContext fallback;
/*    */   
/*    */   public EventCounter() {
/* 66 */     this(UnknownContext.PERMISSIVE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventCounter(IContext fallback) {
/* 74 */     if (fallback == null) {
/* 75 */       throw new IllegalArgumentException("The fallback context in EventCounter constructor cannot be null");
/*    */     }
/* 77 */     this.fallback = fallback;
/*    */   }
/*    */   
/*    */   protected abstract void onEvent(IEvent paramIEvent, IMetaInfo paramIMetaInfo);
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/EventCounter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */