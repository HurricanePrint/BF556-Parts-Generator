/*    */ package com.itextpdf.layout.font;
/*    */ 
/*    */ import java.util.AbstractCollection;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
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
/*    */ class FontSetCollection
/*    */   extends AbstractCollection<FontInfo>
/*    */ {
/*    */   private final Collection<FontInfo> primary;
/*    */   private final Collection<FontInfo> additional;
/*    */   
/*    */   FontSetCollection(Collection<FontInfo> primary, Collection<FontInfo> additional) {
/* 55 */     this.primary = primary;
/* 56 */     this.additional = additional;
/*    */   }
/*    */   
/*    */   public int size() {
/* 60 */     return this.primary.size() + ((this.additional != null) ? this.additional.size() : 0);
/*    */   }
/*    */   
/*    */   public Iterator<FontInfo> iterator() {
/* 64 */     return new Iterator<FontInfo>() {
/* 65 */         private Iterator<FontInfo> i = FontSetCollection.this.primary.iterator();
/*    */         boolean isPrimary = true;
/*    */         
/*    */         public boolean hasNext() {
/* 69 */           boolean hasNext = this.i.hasNext();
/* 70 */           if (!hasNext && this.isPrimary && FontSetCollection.this.additional != null) {
/* 71 */             this.i = FontSetCollection.this.additional.iterator();
/* 72 */             this.isPrimary = false;
/* 73 */             return this.i.hasNext();
/*    */           } 
/* 75 */           return hasNext;
/*    */         }
/*    */ 
/*    */         
/*    */         public FontInfo next() {
/* 80 */           return this.i.next();
/*    */         }
/*    */         
/*    */         public void remove() {
/* 84 */           throw new UnsupportedOperationException();
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public boolean remove(Object o) {
/* 90 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontSetCollection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */