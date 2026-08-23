/*    */ package com.itextpdf.kernel.counter;
/*    */ 
/*    */ import com.itextpdf.kernel.counter.context.IContext;
/*    */ import com.itextpdf.kernel.counter.context.UnknownContext;
/*    */ import com.itextpdf.kernel.counter.data.EventData;
/*    */ import com.itextpdf.kernel.counter.data.EventDataHandler;
/*    */ import com.itextpdf.kernel.counter.data.EventDataHandlerUtil;
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
/*    */ public class DataHandlerCounter<T, V extends EventData<T>>
/*    */   extends EventCounter
/*    */ {
/*    */   private final EventDataHandler<T, V> dataHandler;
/*    */   
/*    */   public DataHandlerCounter(EventDataHandler<T, V> dataHandler) {
/* 66 */     this(dataHandler, UnknownContext.PERMISSIVE);
/*    */   }
/*    */   
/*    */   public DataHandlerCounter(EventDataHandler<T, V> dataHandler, IContext fallback) {
/* 70 */     super(fallback);
/* 71 */     this.dataHandler = dataHandler;
/* 72 */     EventDataHandlerUtil.registerProcessAllShutdownHook(dataHandler);
/* 73 */     EventDataHandlerUtil.registerTimedProcessing(dataHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onEvent(IEvent event, IMetaInfo metaInfo) {
/* 78 */     this.dataHandler.register(event, metaInfo);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/DataHandlerCounter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */