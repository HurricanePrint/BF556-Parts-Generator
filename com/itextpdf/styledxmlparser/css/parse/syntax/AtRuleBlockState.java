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
/*    */ class AtRuleBlockState
/*    */   implements IParserState
/*    */ {
/*    */   private CssParserStateController controller;
/*    */   
/*    */   AtRuleBlockState(CssParserStateController controller) {
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
/* 72 */       this.controller.storeCurrentPropertiesWithoutSelector();
/* 73 */       this.controller.enterRuleState();
/* 74 */     } else if (ch == '}') {
/* 75 */       this.controller.storeCurrentPropertiesWithoutSelector();
/* 76 */       this.controller.finishAtRuleBlock();
/* 77 */       this.controller.enterUnknownStateIfNestedBlocksFinished();
/*    */     } else {
/* 79 */       this.controller.appendToBuffer(ch);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/syntax/AtRuleBlockState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */