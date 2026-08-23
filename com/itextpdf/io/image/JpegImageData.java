/*    */ package com.itextpdf.io.image;
/*    */ 
/*    */ import java.net.URL;
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
/*    */ public class JpegImageData
/*    */   extends ImageData
/*    */ {
/*    */   protected JpegImageData(URL url) {
/* 51 */     super(url, ImageType.JPEG);
/*    */   }
/*    */   
/*    */   protected JpegImageData(byte[] bytes) {
/* 55 */     super(bytes, ImageType.JPEG);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/JpegImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */