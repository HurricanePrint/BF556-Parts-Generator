/*     */ package com.itextpdf.kernel.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class EventDispatcher
/*     */   implements IEventDispatcher
/*     */ {
/*  58 */   protected Map<String, List<IEventHandler>> eventHandlers = new HashMap<>();
/*     */ 
/*     */   
/*     */   public void addEventHandler(String type, IEventHandler handler) {
/*  62 */     removeEventHandler(type, handler);
/*  63 */     List<IEventHandler> handlers = this.eventHandlers.get(type);
/*  64 */     if (handlers == null) {
/*  65 */       handlers = new ArrayList<>();
/*  66 */       this.eventHandlers.put(type, handlers);
/*     */     } 
/*  68 */     handlers.add(handler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispatchEvent(Event event) {
/*  73 */     dispatchEvent(event, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispatchEvent(Event event, boolean delayed) {
/*  78 */     List<IEventHandler> handlers = this.eventHandlers.get(event.getType());
/*  79 */     if (handlers != null) {
/*  80 */       for (IEventHandler handler : handlers) {
/*  81 */         handler.handleEvent(event);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEventHandler(String type) {
/*  88 */     return this.eventHandlers.containsKey(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEventHandler(String type, IEventHandler handler) {
/*  93 */     List<IEventHandler> handlers = this.eventHandlers.get(type);
/*  94 */     if (handlers == null)
/*     */       return; 
/*  96 */     handlers.remove(handler);
/*  97 */     if (handlers.size() == 0) {
/*  98 */       this.eventHandlers.remove(type);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeAllHandlers() {
/* 103 */     this.eventHandlers.clear();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/events/EventDispatcher.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */