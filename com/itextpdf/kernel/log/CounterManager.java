/*     */ package com.itextpdf.kernel.log;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ @Deprecated
/*     */ public class CounterManager
/*     */ {
/*  71 */   private static CounterManager instance = new CounterManager();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private Set<ICounterFactory> factories = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CounterManager getInstance() {
/*  86 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ICounter> getCounters(Class<?> cls) {
/*  95 */     ArrayList<ICounter> result = new ArrayList<>();
/*  96 */     for (ICounterFactory factory : this.factories) {
/*  97 */       ICounter counter = factory.getCounter(cls);
/*  98 */       if (counter != null) {
/*  99 */         result.add(counter);
/*     */       }
/*     */     } 
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(ICounterFactory factory) {
/* 111 */     if (factory != null) {
/* 112 */       this.factories.add(factory);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unregister(ICounterFactory factory) {
/* 123 */     if (factory != null) {
/* 124 */       return this.factories.remove(factory);
/*     */     }
/* 126 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/log/CounterManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */