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
/*    */ class ConditionalGroupAtRuleBlockState
/*    */   implements IParserState
/*    */ {
/*    */   private CssParserStateController controller;
/*    */   
/*    */   ConditionalGroupAtRuleBlockState(CssParserStateController controller) {
/* 61 */     this.controller = controller;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(char ch) {
/* 70 */     if (ch == '/') {
/* 71 */       this.controller.enterCommentStartState();
/* 72 */     } else if (ch == '@') {
/* 73 */       this.controller.enterRuleState();
/* 74 */     } else if (ch == '{') {
/* 75 */       this.controller.storeCurrentSelector();
/* 76 */       this.controller.enterPropertiesState();
/* 77 */     } else if (ch == '}') {
/* 78 */       this.controller.finishAtRuleBlock();
/* 79 */       this.controller.enterUnknownStateIfNestedBlocksFinished();
/*    */     } else {
/* 81 */       this.controller.appendToBuffer(ch);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/syntax/ConditionalGroupAtRuleBlockState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */