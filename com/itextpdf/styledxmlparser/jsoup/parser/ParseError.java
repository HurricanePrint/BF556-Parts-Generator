/*    */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParseError
/*    */ {
/*    */   private int pos;
/*    */   private String errorMsg;
/*    */   
/*    */   ParseError(int pos, String errorMsg) {
/* 55 */     this.pos = pos;
/* 56 */     this.errorMsg = errorMsg;
/*    */   }
/*    */   
/*    */   ParseError(int pos, String errorFormat, Object... args) {
/* 60 */     this.errorMsg = MessageFormatUtil.format(errorFormat, args);
/* 61 */     this.pos = pos;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getErrorMessage() {
/* 69 */     return this.errorMsg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPosition() {
/* 77 */     return this.pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     return this.pos + ": " + this.errorMsg;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/ParseError.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */