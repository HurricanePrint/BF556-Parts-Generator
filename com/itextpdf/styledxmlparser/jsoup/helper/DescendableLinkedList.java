/*     */ package com.itextpdf.styledxmlparser.jsoup.helper;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DescendableLinkedList<E>
/*     */   extends LinkedList<E>
/*     */ {
/*     */   public void push(E e) {
/*  67 */     addFirst(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peekLast() {
/*  75 */     return (size() == 0) ? null : getLast();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E pollLast() {
/*  83 */     return (size() == 0) ? null : removeLast();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> descendingIterator() {
/*  91 */     return new DescendingIterator<>(size());
/*     */   }
/*     */   
/*     */   private class DescendingIterator<E>
/*     */     implements Iterator<E> {
/*     */     private final ListIterator<E> iter;
/*     */     
/*     */     private DescendingIterator(int index) {
/*  99 */       this.iter = DescendableLinkedList.this.listIterator(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 107 */       return this.iter.hasPrevious();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public E next() {
/* 115 */       return this.iter.previous();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 122 */       this.iter.remove();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/helper/DescendableLinkedList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */