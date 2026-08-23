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
/*    */ public class HttpStatusException
/*    */   extends IOException
/*    */ {
/*    */   private int statusCode;
/*    */   private String url;
/*    */   
/*    */   public HttpStatusException(String message, int statusCode, String url) {
/* 55 */     super(message);
/* 56 */     this.statusCode = statusCode;
/* 57 */     this.url = url;
/*    */   }
/*    */   
/*    */   public int getStatusCode() {
/* 61 */     return this.statusCode;
/*    */   }
/*    */   
/*    */   public String getUrl() {
/* 65 */     return this.url;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return super.toString() + ". Status=" + this.statusCode + ", URL=" + this.url;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/HttpStatusException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */