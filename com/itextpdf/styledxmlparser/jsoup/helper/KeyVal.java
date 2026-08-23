/*    */ package com.itextpdf.styledxmlparser.jsoup.helper;
/*    */ 
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
/*    */ public class KeyVal
/*    */ {
/*    */   private String key;
/*    */   private String value;
/*    */   private InputStream stream;
/*    */   
/*    */   public static KeyVal create(String key, String value) {
/* 54 */     return (new KeyVal()).key(key).value(value);
/*    */   }
/*    */   
/*    */   public static KeyVal create(String key, String filename, InputStream stream) {
/* 58 */     return (new KeyVal()).key(key).value(filename).inputStream(stream);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public KeyVal key(String key) {
/* 64 */     Validate.notEmpty(key, "Data key must not be empty");
/* 65 */     this.key = key;
/* 66 */     return this;
/*    */   }
/*    */   
/*    */   public String key() {
/* 70 */     return this.key;
/*    */   }
/*    */   
/*    */   public KeyVal value(String value) {
/* 74 */     Validate.notNull(value, "Data value must not be null");
/* 75 */     this.value = value;
/* 76 */     return this;
/*    */   }
/*    */   
/*    */   public String value() {
/* 80 */     return this.value;
/*    */   }
/*    */   
/*    */   public KeyVal inputStream(InputStream inputStream) {
/* 84 */     Validate.notNull(this.value, "Data input stream must not be null");
/* 85 */     this.stream = inputStream;
/* 86 */     return this;
/*    */   }
/*    */   
/*    */   public InputStream inputStream() {
/* 90 */     return this.stream;
/*    */   }
/*    */   
/*    */   public boolean hasInputStream() {
/* 94 */     return (this.stream != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return this.key + "=" + this.value;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/helper/KeyVal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */