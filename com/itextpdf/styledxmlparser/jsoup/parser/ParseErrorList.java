/*    */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ParseErrorList
/*    */   extends ArrayList<ParseError>
/*    */ {
/*    */   private static final int INITIAL_CAPACITY = 16;
/*    */   private final int maxSize;
/*    */   
/*    */   ParseErrorList(int initialCapacity, int maxSize) {
/* 57 */     super(initialCapacity);
/* 58 */     this.maxSize = maxSize;
/*    */   }
/*    */   
/*    */   boolean canAddError() {
/* 62 */     return (size() < this.maxSize);
/*    */   }
/*    */   
/*    */   int getMaxSize() {
/* 66 */     return this.maxSize;
/*    */   }
/*    */   
/*    */   static ParseErrorList noTracking() {
/* 70 */     return new ParseErrorList(0, 0);
/*    */   }
/*    */   
/*    */   static ParseErrorList tracking(int maxSize) {
/* 74 */     return new ParseErrorList(16, maxSize);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/ParseErrorList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */