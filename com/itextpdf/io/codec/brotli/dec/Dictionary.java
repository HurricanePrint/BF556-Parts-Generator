/*    */ package com.itextpdf.io.codec.brotli.dec;
/*    */ 
/*    */ import java.nio.ByteBuffer;
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
/*    */ public final class Dictionary
/*    */ {
/*    */   private static volatile ByteBuffer data;
/*    */   
/*    */   private static class DataLoader
/*    */   {
/*    */     static final boolean OK;
/*    */     
/*    */     static {
/* 27 */       boolean ok = true;
/*    */       try {
/* 29 */         Class.forName(Dictionary.class.getPackage().getName() + ".DictionaryData");
/* 30 */       } catch (Throwable ex) {
/* 31 */         ok = false;
/*    */       } 
/* 33 */       OK = ok;
/*    */     }
/*    */   }
/*    */   
/*    */   public static void setData(ByteBuffer data) {
/* 38 */     Dictionary.data = data;
/*    */   }
/*    */   
/*    */   public static ByteBuffer getData() {
/* 42 */     if (data != null) {
/* 43 */       return data;
/*    */     }
/* 45 */     if (!DataLoader.OK) {
/* 46 */       throw new BrotliRuntimeException("brotli dictionary is not set");
/*    */     }
/*    */     
/* 49 */     return data;
/*    */   }
/*    */   
/* 52 */   static final int[] OFFSETS_BY_LENGTH = new int[] { 0, 0, 0, 0, 0, 4096, 9216, 21504, 35840, 44032, 53248, 63488, 74752, 87040, 93696, 100864, 104704, 106752, 108928, 113536, 115968, 118528, 119872, 121280, 122016 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   static final int[] SIZE_BITS_BY_LENGTH = new int[] { 0, 0, 0, 0, 10, 10, 11, 11, 10, 10, 10, 10, 10, 9, 9, 8, 7, 7, 8, 7, 7, 6, 6, 5, 5 };
/*    */   static final int MIN_WORD_LENGTH = 4;
/*    */   static final int MAX_WORD_LENGTH = 24;
/*    */   static final int MAX_TRANSFORMED_WORD_LENGTH = 37;
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/brotli/dec/Dictionary.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */