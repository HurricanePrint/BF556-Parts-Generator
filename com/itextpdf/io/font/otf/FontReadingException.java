/*    */ package com.itextpdf.io.font.otf;
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
/*    */ public class FontReadingException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = -7058811479423740467L;
/*    */   
/*    */   public FontReadingException(String message) {
/* 55 */     super(message);
/*    */   }
/*    */   
/*    */   public FontReadingException(String message, Exception e) {
/* 59 */     super(message, e);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/FontReadingException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */