/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PdfDictionaryValues
/*     */   extends AbstractCollection<PdfObject>
/*     */ {
/*     */   private final Collection<PdfObject> collection;
/*     */   
/*     */   PdfDictionaryValues(Collection<PdfObject> collection) {
/*  54 */     this.collection = collection;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(PdfObject object) {
/*  59 */     return this.collection.add(object);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  64 */     if (this.collection.contains(o))
/*  65 */       return true; 
/*  66 */     if (o == null)
/*  67 */       return false; 
/*  68 */     for (PdfObject pdfObject : this) {
/*  69 */       if (PdfObject.equalContent((PdfObject)o, pdfObject)) {
/*  70 */         return true;
/*     */       }
/*     */     } 
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  78 */     if (this.collection.remove(o))
/*  79 */       return true; 
/*  80 */     if (o == null)
/*  81 */       return false; 
/*  82 */     Iterator<PdfObject> it = iterator();
/*  83 */     while (it.hasNext()) {
/*  84 */       if (PdfObject.equalContent((PdfObject)o, it.next())) {
/*  85 */         it.remove();
/*  86 */         return true;
/*     */       } 
/*     */     } 
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  94 */     return this.collection.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  99 */     this.collection.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<PdfObject> iterator() {
/* 104 */     return new DirectIterator(this.collection.iterator());
/*     */   }
/*     */   
/*     */   private static class DirectIterator implements Iterator<PdfObject> {
/*     */     Iterator<PdfObject> parentIterator;
/*     */     
/*     */     DirectIterator(Iterator<PdfObject> parentIterator) {
/* 111 */       this.parentIterator = parentIterator;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 116 */       return this.parentIterator.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public PdfObject next() {
/* 121 */       PdfObject obj = this.parentIterator.next();
/* 122 */       if (obj != null && obj.isIndirectReference()) {
/* 123 */         obj = ((PdfIndirectReference)obj).getRefersTo(true);
/*     */       }
/* 125 */       return obj;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 130 */       this.parentIterator.remove();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfDictionaryValues.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */