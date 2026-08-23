/*     */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.EventType;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.filter.IEventFilter;
/*     */ import java.util.ArrayList;
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
/*     */ public class FilteredEventListener
/*     */   implements IEventListener
/*     */ {
/*  66 */   protected final List<IEventListener> delegates = new ArrayList<>();
/*  67 */   protected final List<IEventFilter[]> filters = (List)new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilteredEventListener() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilteredEventListener(IEventListener delegate, IEventFilter... filterSet) {
/*  78 */     this();
/*  79 */     attachEventListener(delegate, filterSet);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends IEventListener> T attachEventListener(T delegate, IEventFilter... filterSet) {
/*  96 */     this.delegates.add((IEventListener)delegate);
/*  97 */     this.filters.add(filterSet);
/*     */     
/*  99 */     return delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void eventOccurred(IEventData data, EventType type) {
/* 104 */     for (int i = 0; i < this.delegates.size(); i++) {
/* 105 */       IEventListener delegate = this.delegates.get(i);
/* 106 */       boolean filtersPassed = (delegate.getSupportedEvents() == null || delegate.getSupportedEvents().contains(type));
/* 107 */       for (IEventFilter filter : (IEventFilter[])this.filters.get(i)) {
/* 108 */         if (!filter.accept(data, type)) {
/* 109 */           filtersPassed = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 113 */       if (filtersPassed) {
/* 114 */         delegate.eventOccurred(data, type);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EventType> getSupportedEvents() {
/* 121 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/FilteredEventListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */