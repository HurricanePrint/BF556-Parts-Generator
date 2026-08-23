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
/*    */ 
/*    */ public class SystemOutEventCounterFactory
/*    */   implements IEventCounterFactory
/*    */ {
/*    */   public EventCounter getCounter(Class<?> cls) {
/* 53 */     return (cls != null) ? new SystemOutEventCounter(cls) : new SystemOutEventCounter();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/SystemOutEventCounterFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */