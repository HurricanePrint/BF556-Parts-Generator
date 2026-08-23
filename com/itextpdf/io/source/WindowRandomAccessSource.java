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
/*     */ public class WindowRandomAccessSource
/*     */   implements IRandomAccessSource, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8539987600466289182L;
/*     */   private final IRandomAccessSource source;
/*     */   private final long offset;
/*     */   private final long length;
/*     */   
/*     */   public WindowRandomAccessSource(IRandomAccessSource source, long offset) {
/*  75 */     this(source, offset, source.length() - offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WindowRandomAccessSource(IRandomAccessSource source, long offset, long length) {
/*  85 */     this.source = source;
/*  86 */     this.offset = offset;
/*  87 */     this.length = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position) throws IOException {
/*  95 */     if (position >= this.length) return -1; 
/*  96 */     return this.source.get(this.offset + position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/* 104 */     if (position >= this.length) {
/* 105 */       return -1;
/*     */     }
/* 107 */     long toRead = Math.min(len, this.length - position);
/* 108 */     return this.source.get(this.offset + position, bytes, off, (int)toRead);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() {
/* 116 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 123 */     this.source.close();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/WindowRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */