/*    */ package com.itextpdf.kernel.counter.event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoreEvent
/*    */   implements IGenericEvent
/*    */ {
/* 53 */   public static final CoreEvent PROCESS = new CoreEvent("process");
/*    */   
/*    */   private final String subtype;
/*    */   
/*    */   private CoreEvent(String subtype) {
/* 58 */     this.subtype = subtype;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getEventType() {
/* 63 */     return "core-" + this.subtype;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getOriginId() {
/* 68 */     return "com.itextpdf";
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/event/CoreEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */