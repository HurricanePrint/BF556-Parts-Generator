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
/*    */ public class IndependentRandomAccessSource
/*    */   implements IRandomAccessSource
/*    */ {
/*    */   private final IRandomAccessSource source;
/*    */   
/*    */   public IndependentRandomAccessSource(IRandomAccessSource source) {
/* 62 */     this.source = source;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int get(long position) throws IOException {
/* 69 */     return this.source.get(position);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int get(long position, byte[] bytes, int off, int len) throws IOException {
/* 76 */     return this.source.get(position, bytes, off, len);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long length() {
/* 83 */     return this.source.length();
/*    */   }
/*    */   
/*    */   public void close() throws IOException {}
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/IndependentRandomAccessSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */