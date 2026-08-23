/*    */ package com.itextpdf.io.util;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenericArray<T>
/*    */ {
/*    */   private List<T> array;
/*    */   
/*    */   public GenericArray(int size) {
/* 54 */     this.array = new ArrayList<>(size);
/* 55 */     for (int i = 0; i < size; i++) {
/* 56 */       this.array.add(null);
/*    */     }
/*    */   }
/*    */   
/*    */   public T get(int index) {
/* 61 */     return this.array.get(index);
/*    */   }
/*    */   
/*    */   public T set(int index, T element) {
/* 65 */     return this.array.set(index, element);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/GenericArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */