/*    */ package com.itextpdf.kernel.counter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleEventCounterFactory
/*    */   implements IEventCounterFactory
/*    */ {
/*    */   private EventCounter counter;
/*    */   
/*    */   public SimpleEventCounterFactory(EventCounter counter) {
/* 54 */     this.counter = counter;
/*    */   }
/*    */ 
/*    */   
/*    */   public EventCounter getCounter(Class<?> cls) {
/* 59 */     return this.counter;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/SimpleEventCounterFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */