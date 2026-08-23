/*    */ package com.itextpdf.io.source;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.zip.Deflater;
/*    */ import java.util.zip.DeflaterOutputStream;
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
/*    */ public class DeflaterOutputStream
/*    */   extends DeflaterOutputStream
/*    */ {
/*    */   public DeflaterOutputStream(OutputStream out, int level, int size) {
/* 53 */     super(out, new Deflater(level), size);
/*    */   }
/*    */   
/*    */   public DeflaterOutputStream(OutputStream out, int level) {
/* 57 */     this(out, level, 512);
/*    */   }
/*    */   
/*    */   public DeflaterOutputStream(OutputStream out) {
/* 61 */     this(out, -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 66 */     finish();
/* 67 */     super.close();
/*    */   }
/*    */ 
/*    */   
/*    */   public void finish() throws IOException {
/* 72 */     super.finish();
/* 73 */     this.def.end();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/DeflaterOutputStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */