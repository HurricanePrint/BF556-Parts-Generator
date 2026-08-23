/*    */ package com.itextpdf.io.font.cmap;
/*    */ 
/*    */ import com.itextpdf.io.IOException;
/*    */ import com.itextpdf.io.source.PdfTokenizer;
/*    */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*    */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*    */ import com.itextpdf.io.util.ResourceUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class CMapLocationResource
/*    */   implements ICMapLocation
/*    */ {
/*    */   public PdfTokenizer getLocation(String location) throws IOException {
/* 62 */     String fullName = "com/itextpdf/io/font/cmap/" + location;
/* 63 */     InputStream inp = ResourceUtil.getResourceStream(fullName);
/* 64 */     if (inp == null) {
/* 65 */       throw (new IOException("The CMap {0} was not found.")).setMessageParams(new Object[] { fullName });
/*    */     }
/* 67 */     return new PdfTokenizer(new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(inp)));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapLocationResource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */