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
/*    */ class UnknownState
/*    */   implements IParserState
/*    */ {
/*    */   private CssParserStateController controller;
/*    */   
/*    */   UnknownState(CssParserStateController controller) {
/* 61 */     this.controller = controller;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(char ch) {
/* 69 */     if (ch == '/') {
/* 70 */       this.controller.enterCommentStartState();
/* 71 */     } else if (ch == '@') {
/* 72 */       this.controller.enterRuleState();
/* 73 */     } else if (ch == '{') {
/* 74 */       this.controller.storeCurrentSelector();
/* 75 */       this.controller.enterPropertiesState();
/* 76 */     } else if ((ch == '-' && this.controller.getBufferContents().endsWith("<!-")) || (ch == '>' && this.controller.getBufferContents().endsWith("--"))) {
/*    */       
/* 78 */       this.controller.resetBuffer();
/* 79 */     } else if ((ch == '[' && this.controller.getBufferContents().endsWith("<![CDATA")) || (ch == '>' && this.controller.getBufferContents().endsWith("]]"))) {
/*    */       
/* 81 */       this.controller.resetBuffer();
/*    */     } else {
/* 83 */       this.controller.appendToBuffer(ch);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/syntax/UnknownState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */