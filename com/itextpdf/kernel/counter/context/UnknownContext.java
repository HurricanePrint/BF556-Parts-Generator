/*    */ package com.itextpdf.kernel.counter.context;
/*    */ 
/*    */ import com.itextpdf.kernel.counter.event.IEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnknownContext
/*    */   implements IContext
/*    */ {
/* 56 */   public static final IContext RESTRICTIVE = new UnknownContext(false);
/*    */ 
/*    */ 
/*    */   
/* 60 */   public static final IContext PERMISSIVE = new UnknownContext(true);
/*    */   
/*    */   private final boolean allowEvents;
/*    */   
/*    */   public UnknownContext(boolean allowEvents) {
/* 65 */     this.allowEvents = allowEvents;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean allow(IEvent event) {
/* 70 */     return this.allowEvents;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/context/UnknownContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */