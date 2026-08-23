/*    */ package com.itextpdf.io.source;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
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
/*    */ public class ByteArrayOutputStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/*    */   public ByteArrayOutputStream() {}
/*    */   
/*    */   public ByteArrayOutputStream(int size) {
/* 53 */     super(size);
/*    */   }
/*    */   
/*    */   public ByteArrayOutputStream assignBytes(byte[] bytes, int count) {
/* 57 */     this.buf = bytes;
/* 58 */     this.count = count;
/* 59 */     return this;
/*    */   }
/*    */   
/*    */   public ByteArrayOutputStream assignBytes(byte[] bytes) {
/* 63 */     this.buf = bytes;
/* 64 */     this.count = bytes.length;
/* 65 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/ByteArrayOutputStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */