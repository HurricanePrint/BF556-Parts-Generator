/*    */ package org.slf4j.impl;
/*    */ 
/*    */ import org.slf4j.spi.MDCAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StaticMDCBinder
/*    */ {
/* 39 */   public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final StaticMDCBinder getSingleton() {
/* 51 */     return SINGLETON;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MDCAdapter getMDCA() {
/* 59 */     return new Log4jMDCAdapter();
/*    */   }
/*    */   
/*    */   public String getMDCAdapterClassStr() {
/* 63 */     return Log4jMDCAdapter.class.getName();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/org/slf4j/impl/StaticMDCBinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */