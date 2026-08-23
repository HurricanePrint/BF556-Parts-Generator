/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MappedChannelRandomAccessSource
/*     */   implements IRandomAccessSource
/*     */ {
/*     */   private final FileChannel channel;
/*     */   private final long offset;
/*     */   private final long length;
/*     */   private ByteBufferRandomAccessSource source;
/*     */   
/*     */   public MappedChannelRandomAccessSource(FileChannel channel, long offset, long length) {
/*  80 */     if (offset < 0L)
/*  81 */       throw new IllegalArgumentException(offset + " is negative"); 
/*  82 */     if (length <= 0L) {
/*  83 */       throw new IllegalArgumentException(length + " is zero or negative");
/*     */     }
/*  85 */     this.channel = channel;
/*  86 */     this.offset = offset;
/*  87 */     this.length = length;
/*  88 */     this.source = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void open() throws IOException {
/*  96 */     if (this.source != null) {
/*     */       return;
/*     */     }
/*  99 */     if (!this.channel.isOpen()) {
/* 100 */       throw new IllegalStateException("Channel is closed");
/*     */     }
/* 102 */     this.source = new ByteBufferRandomAccessSource(this.channel.map(FileChannel.MapMode.READ_ONLY, this.offset, this.length));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position) throws IOException {
/* 111 */     if (this.source == null)
/* 112 */       throw new IOException("RandomAccessSource not opened"); 
/* 113 */     return this.source.get(position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/* 120 */     if (this.source == null)
/* 121 */       throw new IOException("RandomAccessSource not opened"); 
/* 122 */     return this.source.get(position, bytes, off, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() {
/* 129 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 136 */     if (this.source == null)
/*     */       return; 
/* 138 */     this.source.close();
/* 139 */     this.source = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return getClass().getName() + " (" + this.offset + ", " + this.length + ")";
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/MappedChannelRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */