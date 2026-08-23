/*    */ package com.itextpdf.styledxmlparser.jsoup;
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
/*    */ public class UnsupportedMimeTypeException
/*    */   extends IOException
/*    */ {
/*    */   private String mimeType;
/*    */   private String url;
/*    */   
/*    */   public UnsupportedMimeTypeException(String message, String mimeType, String url) {
/* 55 */     super(message);
/* 56 */     this.mimeType = mimeType;
/* 57 */     this.url = url;
/*    */   }
/*    */   
/*    */   public String getMimeType() {
/* 61 */     return this.mimeType;
/*    */   }
/*    */   
/*    */   public String getUrl() {
/* 65 */     return this.url;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return super.toString() + ". Mimetype=" + this.mimeType + ", URL=" + this.url;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/UnsupportedMimeTypeException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */