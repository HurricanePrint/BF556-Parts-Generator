/*     */ package com.itextpdf.kernel.counter;
/*     */ 
/*     */ import com.itextpdf.kernel.counter.context.IContext;
/*     */ import com.itextpdf.kernel.counter.event.IEvent;
/*     */ import com.itextpdf.kernel.counter.event.IMetaInfo;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class EventCounterHandler
/*     */ {
/*  69 */   private static final EventCounterHandler instance = new EventCounterHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private Map<IEventCounterFactory, Boolean> factories = new ConcurrentHashMap<>();
/*     */   
/*     */   private EventCounterHandler() {
/*  77 */     register(new SimpleEventCounterFactory(new DefaultEventCounter()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EventCounterHandler getInstance() {
/*  84 */     return instance;
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
/*     */   public void onEvent(IEvent event, IMetaInfo metaInfo, Class<?> caller) {
/*  96 */     IContext context = null;
/*  97 */     boolean contextInitialized = false;
/*  98 */     for (IEventCounterFactory factory : this.factories.keySet()) {
/*  99 */       EventCounter counter = factory.getCounter(caller);
/* 100 */       if (counter != null) {
/* 101 */         if (!contextInitialized) {
/* 102 */           if (metaInfo != null) {
/* 103 */             context = ContextManager.getInstance().getContext(metaInfo.getClass());
/*     */           }
/* 105 */           if (context == null) {
/* 106 */             context = ContextManager.getInstance().getContext(caller);
/*     */           }
/* 108 */           if (context == null) {
/* 109 */             context = ContextManager.getInstance().getContext(event.getClass());
/*     */           }
/* 111 */           contextInitialized = true;
/*     */         } 
/* 113 */         if ((context != null && context.allow(event)) || (context == null && counter.fallback.allow(event))) {
/* 114 */           counter.onEvent(event, metaInfo);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(IEventCounterFactory factory) {
/* 126 */     if (factory != null) {
/* 127 */       this.factories.put(factory, Boolean.valueOf(true));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegistered(IEventCounterFactory factory) {
/* 137 */     if (factory != null) {
/* 138 */       return this.factories.containsKey(factory);
/*     */     }
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unregister(IEventCounterFactory factory) {
/* 150 */     if (factory != null) {
/* 151 */       return (this.factories.remove(factory) != null);
/*     */     }
/* 153 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/EventCounterHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */