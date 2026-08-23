/*    */ package com.itextpdf.kernel.log;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class SimpleCounterFactory
/*    */   implements ICounterFactory
/*    */ {
/*    */   private ICounter counter;
/*    */   
/*    */   public SimpleCounterFactory(ICounter counter) {
/* 56 */     this.counter = counter;
/*    */   }
/*    */ 
/*    */   
/*    */   public ICounter getCounter(Class<?> cls) {
/* 61 */     return this.counter;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/log/SimpleCounterFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */