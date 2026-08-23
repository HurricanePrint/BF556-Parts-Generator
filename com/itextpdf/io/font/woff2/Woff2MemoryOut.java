/*    */ package com.itextpdf.io.font.woff2;
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
/*    */ class Woff2MemoryOut
/*    */   implements Woff2Out
/*    */ {
/*    */   private byte[] buf_;
/*    */   private int buf_size_;
/*    */   private int offset_;
/*    */   
/*    */   public Woff2MemoryOut(byte[] buf_, int buf_size_) {
/* 27 */     this.buf_ = buf_;
/* 28 */     this.buf_size_ = buf_size_;
/* 29 */     this.offset_ = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] buf, int buff_offset, int n) {
/* 34 */     write(buf, buff_offset, this.offset_, n);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] buf, int buff_offset, int offset, int n) {
/* 39 */     if (offset > this.buf_size_ || n > this.buf_size_ - offset) {
/* 40 */       throw new FontCompressionException("Writing woff2 exception");
/*    */     }
/* 42 */     System.arraycopy(buf, buff_offset, this.buf_, offset, n);
/* 43 */     this.offset_ = Math.max(this.offset_, offset + n);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 48 */     return this.offset_;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/Woff2MemoryOut.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */