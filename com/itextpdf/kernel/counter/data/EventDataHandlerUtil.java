/*     */ package com.itextpdf.kernel.counter.data;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EventDataHandlerUtil
/*     */ {
/*     */   public static <T, V extends EventData<T>> void registerProcessAllShutdownHook(final EventDataHandler<T, V> dataHandler) {
/*     */     try {
/*  70 */       Runtime.getRuntime().addShutdownHook(new Thread()
/*     */           {
/*     */             public void run() {
/*  73 */               dataHandler.tryProcessRest();
/*     */             }
/*     */           });
/*  76 */     } catch (SecurityException security) {
/*  77 */       LoggerFactory.getLogger(EventDataHandlerUtil.class).error("Unable to register event data handler shutdown hook because of security reasons.");
/*  78 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T, V extends EventData<T>> void registerTimedProcessing(final EventDataHandler<T, V> dataHandler) {
/*  92 */     Thread thread = new Thread()
/*     */       {
/*     */         public void run() {
/*     */           try {
/*     */             while (true)
/*  97 */             { Thread.sleep(dataHandler.getWaitTime().getTime());
/*  98 */               dataHandler.tryProcessNextAsync(Boolean.valueOf(false)); } 
/*  99 */           } catch (InterruptedException e) {
/*     */           
/* 101 */           } catch (Exception any) {
/* 102 */             LoggerFactory.getLogger(EventDataHandlerUtil.class).error("Unexpected exception encountered in service thread. Shutting it down.", any);
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 108 */     thread.setDaemon(true);
/* 109 */     thread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BiggerCountComparator<T, V extends EventData<T>>
/*     */     implements Comparator<V>
/*     */   {
/*     */     public int compare(V o1, V o2) {
/* 122 */       return Long.compare(o2.getCount(), o1.getCount());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/data/EventDataHandlerUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */