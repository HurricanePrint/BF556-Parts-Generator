/*    */ package com.itextpdf.kernel.counter.context;
/*    */ 
/*    */ import com.itextpdf.kernel.counter.event.IEvent;
/*    */ import com.itextpdf.kernel.counter.event.IGenericEvent;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenericContext
/*    */   implements IContext
/*    */ {
/*    */   private final Set<String> supported;
/*    */   
/*    */   public GenericContext(Collection<String> supported) {
/* 61 */     this.supported = new HashSet<>();
/* 62 */     this.supported.addAll(supported);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean allow(IEvent event) {
/* 67 */     if (event instanceof IGenericEvent) {
/* 68 */       return this.supported.contains(((IGenericEvent)event).getOriginId());
/*    */     }
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/context/GenericContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */