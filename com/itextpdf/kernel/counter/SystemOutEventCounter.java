/*    */ package com.itextpdf.kernel.counter;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
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
/*    */ public class SystemOutEventCounter
/*    */   extends EventCounter
/*    */ {
/*    */   protected String name;
/*    */   
/*    */   public SystemOutEventCounter(String name) {
/* 62 */     this.name = name;
/*    */   }
/*    */   
/*    */   public SystemOutEventCounter() {
/* 66 */     this("iText");
/*    */   }
/*    */   
/*    */   public SystemOutEventCounter(Class<?> cls) {
/* 70 */     this(cls.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onEvent(IEvent event, IMetaInfo metaInfo) {
/* 75 */     System.out.println(MessageFormatUtil.format("[{0}] {1} event", new Object[] { this.name, event.getEventType() }));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/SystemOutEventCounter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */