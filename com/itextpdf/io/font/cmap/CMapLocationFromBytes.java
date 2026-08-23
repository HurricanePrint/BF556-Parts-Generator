/*    */ package com.itextpdf.io.font.cmap;
/*    */ 
/*    */ import com.itextpdf.io.source.PdfTokenizer;
/*    */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*    */ import com.itextpdf.io.source.RandomAccessSourceFactory;
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
/*    */ public class CMapLocationFromBytes
/*    */   implements ICMapLocation
/*    */ {
/*    */   private byte[] data;
/*    */   
/*    */   public CMapLocationFromBytes(byte[] data) {
/* 58 */     this.data = data;
/*    */   }
/*    */   
/*    */   public PdfTokenizer getLocation(String location) throws IOException {
/* 62 */     return new PdfTokenizer(new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(this.data)));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapLocationFromBytes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */