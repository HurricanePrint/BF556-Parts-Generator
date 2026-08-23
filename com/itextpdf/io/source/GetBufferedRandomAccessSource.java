/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public class GetBufferedRandomAccessSource
/*     */   implements IRandomAccessSource, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8922625738755763494L;
/*     */   private final IRandomAccessSource source;
/*     */   private final byte[] getBuffer;
/*  54 */   private long getBufferStart = -1L;
/*  55 */   private long getBufferEnd = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GetBufferedRandomAccessSource(IRandomAccessSource source) {
/*  62 */     this.source = source;
/*  63 */     this.getBuffer = new byte[(int)Math.min(Math.max(source.length() / 4L, 1L), 4096L)];
/*  64 */     this.getBufferStart = -1L;
/*  65 */     this.getBufferEnd = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position) throws IOException {
/*  72 */     if (position < this.getBufferStart || position > this.getBufferEnd) {
/*  73 */       int count = this.source.get(position, this.getBuffer, 0, this.getBuffer.length);
/*  74 */       if (count == -1)
/*  75 */         return -1; 
/*  76 */       this.getBufferStart = position;
/*  77 */       this.getBufferEnd = position + count - 1L;
/*     */     } 
/*  79 */     int bufPos = (int)(position - this.getBufferStart);
/*  80 */     return 0xFF & this.getBuffer[bufPos];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/*  87 */     return this.source.get(position, bytes, off, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() {
/*  94 */     return this.source.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 101 */     this.source.close();
/* 102 */     this.getBufferStart = -1L;
/* 103 */     this.getBufferEnd = -1L;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/GetBufferedRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */