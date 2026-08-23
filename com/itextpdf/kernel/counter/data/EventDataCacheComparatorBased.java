/*     */ package com.itextpdf.kernel.counter.data;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class EventDataCacheComparatorBased<T, V extends EventData<T>>
/*     */   implements IEventDataCache<T, V>
/*     */ {
/*  66 */   private Map<T, V> map = new HashMap<>();
/*     */   private Set<V> orderedCache;
/*     */   
/*     */   public EventDataCacheComparatorBased(Comparator<V> comparator) {
/*  70 */     this.orderedCache = new TreeSet<>(comparator);
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(V data) {
/*  75 */     if (data != null) {
/*  76 */       EventData eventData = (EventData)this.map.put(data.getSignature(), data);
/*  77 */       if (eventData != null) {
/*  78 */         this.orderedCache.remove(eventData);
/*  79 */         data.mergeWith(eventData);
/*  80 */         this.orderedCache.add(data);
/*     */       } else {
/*  82 */         this.orderedCache.add(data);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public V retrieveNext() {
/*  89 */     for (EventData eventData : this.orderedCache) {
/*  90 */       if (eventData != null) {
/*  91 */         this.map.remove(eventData.getSignature());
/*  92 */         this.orderedCache.remove(eventData);
/*  93 */         return (V)eventData;
/*     */       } 
/*     */     } 
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<V> clear() {
/* 101 */     ArrayList<V> result = new ArrayList<>(this.map.values());
/* 102 */     this.map.clear();
/* 103 */     this.orderedCache.clear();
/* 104 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/data/EventDataCacheComparatorBased.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */