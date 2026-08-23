/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ class PdfDictionaryEntrySet
/*     */   extends AbstractSet<Map.Entry<PdfName, PdfObject>>
/*     */ {
/*     */   private final Set<Map.Entry<PdfName, PdfObject>> set;
/*     */   
/*     */   PdfDictionaryEntrySet(Set<Map.Entry<PdfName, PdfObject>> set) {
/*  56 */     this.set = set;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  61 */     return (this.set.contains(o) || super.contains(o));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  66 */     return (this.set.remove(o) || super.remove(o));
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<PdfName, PdfObject>> iterator() {
/*  71 */     return new DirectIterator(this.set.iterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  76 */     return this.set.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  81 */     this.set.clear();
/*     */   }
/*     */   
/*     */   private static class DirectIterator implements Iterator<Map.Entry<PdfName, PdfObject>> {
/*     */     Iterator<Map.Entry<PdfName, PdfObject>> parentIterator;
/*     */     
/*     */     public DirectIterator(Iterator<Map.Entry<PdfName, PdfObject>> parentIterator) {
/*  88 */       this.parentIterator = parentIterator;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/*  93 */       return this.parentIterator.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<PdfName, PdfObject> next() {
/*  98 */       return new PdfDictionaryEntrySet.DirectEntry(this.parentIterator.next());
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 103 */       this.parentIterator.remove();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DirectEntry
/*     */     implements Map.Entry<PdfName, PdfObject> {
/*     */     Map.Entry<PdfName, PdfObject> entry;
/*     */     
/*     */     DirectEntry(Map.Entry<PdfName, PdfObject> entry) {
/* 112 */       this.entry = entry;
/*     */     }
/*     */ 
/*     */     
/*     */     public PdfName getKey() {
/* 117 */       return this.entry.getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public PdfObject getValue() {
/* 122 */       PdfObject obj = this.entry.getValue();
/* 123 */       if (obj != null && obj.isIndirectReference()) {
/* 124 */         obj = ((PdfIndirectReference)obj).getRefersTo(true);
/*     */       }
/* 126 */       return obj;
/*     */     }
/*     */ 
/*     */     
/*     */     public PdfObject setValue(PdfObject value) {
/* 131 */       return this.entry.setValue(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 136 */       if (!(o instanceof Map.Entry))
/* 137 */         return false; 
/* 138 */       Map.Entry e = (Map.Entry)o;
/* 139 */       Object k1 = getKey();
/* 140 */       Object k2 = e.getKey();
/* 141 */       if (k1 != null && k1.equals(k2)) {
/* 142 */         Object v1 = getValue();
/* 143 */         Object v2 = e.getValue();
/* 144 */         if (v1 != null && v1.equals(v2))
/* 145 */           return true; 
/*     */       } 
/* 147 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 152 */       return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfDictionaryEntrySet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */