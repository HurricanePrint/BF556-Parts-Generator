/*    */ package com.itextpdf.io.source;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class ThreadSafeRandomAccessSource
/*    */   implements IRandomAccessSource
/*    */ {
/*    */   private final IRandomAccessSource source;
/* 49 */   private final Object lockObj = new Object();
/*    */   
/*    */   public ThreadSafeRandomAccessSource(IRandomAccessSource source) {
/* 52 */     this.source = source;
/*    */   }
/*    */ 
/*    */   
/*    */   public int get(long position) throws IOException {
/* 57 */     synchronized (this.lockObj) {
/* 58 */       return this.source.get(position);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/* 64 */     synchronized (this.lockObj) {
/* 65 */       return this.source.get(position, bytes, off, len);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public long length() {
/* 71 */     synchronized (this.lockObj) {
/* 72 */       return this.source.length();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 78 */     synchronized (this.lockObj) {
/* 79 */       this.source.close();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/ThreadSafeRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */