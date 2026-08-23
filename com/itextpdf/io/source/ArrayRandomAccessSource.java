/*    */ package com.itextpdf.io.source;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
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
/*    */ class ArrayRandomAccessSource
/*    */   implements IRandomAccessSource, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8497059230517630513L;
/*    */   private byte[] array;
/*    */   
/*    */   public ArrayRandomAccessSource(byte[] array) {
/* 58 */     if (array == null) throw new IllegalArgumentException("Passed byte array can not be null."); 
/* 59 */     this.array = array;
/*    */   }
/*    */   
/*    */   public int get(long offset) {
/* 63 */     if (offset >= this.array.length) return -1; 
/* 64 */     return 0xFF & this.array[(int)offset];
/*    */   }
/*    */   
/*    */   public int get(long offset, byte[] bytes, int off, int len) {
/* 68 */     if (this.array == null) throw new IllegalStateException("Already closed");
/*    */     
/* 70 */     if (offset >= this.array.length) {
/* 71 */       return -1;
/*    */     }
/* 73 */     if (offset + len > this.array.length) {
/* 74 */       len = (int)(this.array.length - offset);
/*    */     }
/* 76 */     System.arraycopy(this.array, (int)offset, bytes, off, len);
/*    */     
/* 78 */     return len;
/*    */   }
/*    */   
/*    */   public long length() {
/* 82 */     return this.array.length;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 86 */     this.array = null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/ArrayRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */