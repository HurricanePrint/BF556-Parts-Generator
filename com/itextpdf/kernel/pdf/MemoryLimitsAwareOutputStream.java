/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.Arrays;
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
/*     */ class MemoryLimitsAwareOutputStream
/*     */   extends ByteArrayOutputStream
/*     */ {
/*     */   private static final int DEFAULT_MAX_STREAM_SIZE = 2147483639;
/*  65 */   private int maxStreamSize = 2147483639;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemoryLimitsAwareOutputStream() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemoryLimitsAwareOutputStream(int size) {
/*  83 */     super(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxStreamSize() {
/*  92 */     return this.maxStreamSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemoryLimitsAwareOutputStream setMaxStreamSize(int maxStreamSize) {
/* 102 */     this.maxStreamSize = maxStreamSize;
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void write(byte[] b, int off, int len) {
/* 112 */     if (off < 0 || off > b.length || len < 0 || off + len - b.length > 0)
/*     */     {
/* 114 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/* 117 */     int minCapacity = this.count + len;
/* 118 */     if (minCapacity < 0)
/*     */     {
/* 120 */       throw new MemoryLimitsAwareException("During decompression a single stream occupied more than a maximum integer value. Please check your pdf.");
/*     */     }
/* 122 */     if (minCapacity > this.maxStreamSize) {
/* 123 */       throw new MemoryLimitsAwareException("During decompression a single stream occupied more memory than allowed. Please either check your pdf or increase the allowed multiple decompressed pdf streams maximum size value by setting the appropriate parameter of ReaderProperties's MemoryLimitsAwareHandler.");
/*     */     }
/*     */ 
/*     */     
/* 127 */     int oldCapacity = this.buf.length;
/* 128 */     int newCapacity = oldCapacity << 1;
/* 129 */     if (newCapacity < 0 || newCapacity - minCapacity < 0)
/*     */     {
/* 131 */       newCapacity = minCapacity;
/*     */     }
/*     */     
/* 134 */     if (newCapacity - this.maxStreamSize > 0) {
/* 135 */       newCapacity = this.maxStreamSize;
/* 136 */       this.buf = Arrays.copyOf(this.buf, newCapacity);
/*     */     } 
/* 138 */     super.write(b, off, len);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/MemoryLimitsAwareOutputStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */