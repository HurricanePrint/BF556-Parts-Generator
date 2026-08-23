/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ class PdfArrayDirectIterator
/*    */   implements Iterator<PdfObject>
/*    */ {
/*    */   Iterator<PdfObject> array;
/*    */   
/*    */   PdfArrayDirectIterator(List<PdfObject> array) {
/* 52 */     this.array = array.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 57 */     return this.array.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfObject next() {
/* 62 */     PdfObject obj = this.array.next();
/* 63 */     if (obj.isIndirectReference()) {
/* 64 */       obj = ((PdfIndirectReference)obj).getRefersTo(true);
/*    */     }
/* 66 */     return obj;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 71 */     this.array.remove();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfArrayDirectIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */