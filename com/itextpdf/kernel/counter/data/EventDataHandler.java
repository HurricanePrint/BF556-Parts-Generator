/*     */ package com.itextpdf.kernel.counter.data;
/*     */ 
/*     */ import com.itextpdf.io.util.SystemUtil;
/*     */ import com.itextpdf.kernel.counter.event.IEvent;
/*     */ import com.itextpdf.kernel.counter.event.IMetaInfo;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ 
/*     */ 
/*     */ public abstract class EventDataHandler<T, V extends EventData<T>>
/*     */ {
/*  74 */   private final Object processLock = new Object();
/*     */   
/*     */   private final IEventDataCache<T, V> cache;
/*     */   private final IEventDataFactory<T, V> factory;
/*  78 */   private final AtomicLong lastProcessedTime = new AtomicLong();
/*     */   
/*     */   private volatile WaitTime waitTime;
/*     */   
/*     */   public EventDataHandler(IEventDataCache<T, V> cache, IEventDataFactory<T, V> factory, long initialWaitTimeMillis, long maxWaitTimeMillis) {
/*  83 */     this.cache = cache;
/*  84 */     this.factory = factory;
/*  85 */     this.waitTime = new WaitTime(initialWaitTimeMillis, maxWaitTimeMillis);
/*     */   }
/*     */   
/*     */   public List<V> clear() {
/*     */     List<V> all;
/*  90 */     synchronized (this.cache) {
/*  91 */       all = this.cache.clear();
/*     */     } 
/*  93 */     this.lastProcessedTime.set(0L);
/*  94 */     resetWaitTime();
/*  95 */     return (all != null) ? all : Collections.<V>emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(IEvent event, IMetaInfo metaInfo) {
/*     */     V data;
/* 101 */     synchronized (this.factory) {
/* 102 */       data = this.factory.create(event, metaInfo);
/*     */     } 
/* 104 */     if (data != null) {
/* 105 */       synchronized (this.cache) {
/* 106 */         this.cache.put(data);
/*     */       } 
/* 108 */       tryProcessNextAsync();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tryProcessNext() {
/* 113 */     long currentTime = SystemUtil.getRelativeTimeMillis();
/* 114 */     if (currentTime - this.lastProcessedTime.get() > this.waitTime.getTime()) {
/* 115 */       V data; this.lastProcessedTime.set(SystemUtil.getRelativeTimeMillis());
/*     */       
/* 117 */       synchronized (this.cache) {
/* 118 */         data = this.cache.retrieveNext();
/*     */       } 
/* 120 */       if (data != null) {
/*     */         boolean successful;
/* 122 */         synchronized (this.processLock) {
/* 123 */           successful = tryProcess(data);
/*     */         } 
/* 125 */         if (successful) {
/* 126 */           onSuccess(data);
/*     */         } else {
/* 128 */           synchronized (this.cache) {
/* 129 */             this.cache.put(data);
/*     */           } 
/* 131 */           onFailure(data);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tryProcessNextAsync() {
/* 138 */     tryProcessNextAsync(null);
/*     */   }
/*     */   
/*     */   public void tryProcessNextAsync(Boolean daemon) {
/* 142 */     long currentTime = SystemUtil.getRelativeTimeMillis();
/* 143 */     if (currentTime - this.lastProcessedTime.get() > this.waitTime.getTime()) {
/* 144 */       Thread thread = new Thread()
/*     */         {
/*     */           public void run() {
/* 147 */             EventDataHandler.this.tryProcessNext();
/*     */           }
/*     */         };
/* 150 */       if (daemon != null) {
/* 151 */         thread.setDaemon(daemon.booleanValue());
/*     */       }
/* 153 */       thread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tryProcessRest() {
/* 161 */     List<V> unprocessedEvents = clear();
/* 162 */     if (!unprocessedEvents.isEmpty()) {
/*     */       try {
/* 164 */         synchronized (this.processLock) {
/* 165 */           for (EventData eventData : unprocessedEvents) {
/* 166 */             process((V)eventData);
/*     */           }
/*     */         } 
/* 169 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetWaitTime() {
/* 175 */     WaitTime local = this.waitTime;
/* 176 */     this.waitTime = new WaitTime(local.getInitial(), local.getMaximum());
/*     */   }
/*     */   
/*     */   public void increaseWaitTime() {
/* 180 */     WaitTime local = this.waitTime;
/* 181 */     this.waitTime = new WaitTime(local.getInitial(), local.getMaximum(), Math.min(local.getTime() * 2L, local.getMaximum()));
/*     */   }
/*     */   
/*     */   public void setNoWaitTime() {
/* 185 */     WaitTime local = this.waitTime;
/* 186 */     this.waitTime = new WaitTime(local.getInitial(), local.getMaximum(), 0L);
/*     */   }
/*     */   
/*     */   public WaitTime getWaitTime() {
/* 190 */     return this.waitTime;
/*     */   }
/*     */   
/*     */   protected void onSuccess(V data) {
/* 194 */     resetWaitTime();
/*     */   }
/*     */   
/*     */   protected void onFailure(V data) {
/* 198 */     increaseWaitTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean onProcessException(Exception exception) {
/* 208 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract boolean process(V paramV);
/*     */   
/*     */   private boolean tryProcess(V data) {
/*     */     try {
/* 215 */       return process(data);
/* 216 */     } catch (Exception any) {
/* 217 */       return onProcessException(any);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/data/EventDataHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */