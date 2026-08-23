/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
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
/*     */ class RAFRandomAccessSource
/*     */   implements IRandomAccessSource
/*     */ {
/*     */   private final RandomAccessFile raf;
/*     */   private final long length;
/*     */   
/*     */   public RAFRandomAccessSource(RandomAccessFile raf) throws IOException {
/*  70 */     this.raf = raf;
/*  71 */     this.length = raf.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position) throws IOException {
/*  78 */     if (position > this.length) {
/*  79 */       return -1;
/*     */     }
/*     */     
/*  82 */     if (this.raf.getFilePointer() != position) {
/*  83 */       this.raf.seek(position);
/*     */     }
/*     */     
/*  86 */     return this.raf.read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/*  93 */     if (position > this.length) {
/*  94 */       return -1;
/*     */     }
/*     */     
/*  97 */     if (this.raf.getFilePointer() != position) {
/*  98 */       this.raf.seek(position);
/*     */     }
/*     */     
/* 101 */     return this.raf.read(bytes, off, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() {
/* 110 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 117 */     this.raf.close();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/RAFRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */