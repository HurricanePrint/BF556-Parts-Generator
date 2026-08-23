/*    */ package com.itextpdf.styledxmlparser.css.parse.syntax;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PropertiesState
/*    */   implements IParserState
/*    */ {
/*    */   private CssParserStateController controller;
/*    */   
/*    */   PropertiesState(CssParserStateController controller) {
/* 62 */     this.controller = controller;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(char ch) {
/* 70 */     if (ch == '}') {
/* 71 */       this.controller.storeCurrentProperties();
/* 72 */       this.controller.enterUnknownStateIfNestedBlocksFinished();
/* 73 */     } else if (ch == '/') {
/* 74 */       this.controller.enterCommentStartState();
/*    */     } else {
/* 76 */       this.controller.appendToBuffer(ch);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/syntax/PropertiesState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */