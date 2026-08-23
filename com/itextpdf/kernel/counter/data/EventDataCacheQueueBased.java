/*    */ package com.itextpdf.kernel.counter.data;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventDataCacheQueueBased<T, V extends EventData<T>>
/*    */   implements IEventDataCache<T, V>
/*    */ {
/* 64 */   private Map<T, V> map = new HashMap<>();
/* 65 */   private LinkedList<T> signatureQueue = new LinkedList<>();
/*    */ 
/*    */   
/*    */   public void put(V data) {
/* 69 */     if (data != null) {
/* 70 */       EventData eventData = (EventData)this.map.put(data.getSignature(), data);
/* 71 */       if (eventData != null) {
/* 72 */         data.mergeWith(eventData);
/*    */       } else {
/* 74 */         this.signatureQueue.addLast(data.getSignature());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public V retrieveNext() {
/* 81 */     if (!this.signatureQueue.isEmpty()) {
/* 82 */       return this.map.remove(this.signatureQueue.pollFirst());
/*    */     }
/* 84 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<V> clear() {
/* 89 */     ArrayList<V> result = new ArrayList<>(this.map.values());
/* 90 */     this.map.clear();
/* 91 */     this.signatureQueue.clear();
/* 92 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/counter/data/EventDataCacheQueueBased.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */