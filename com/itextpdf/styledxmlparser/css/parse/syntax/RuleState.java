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
/*    */ class RuleState
/*    */   implements IParserState
/*    */ {
/*    */   private CssParserStateController controller;
/*    */   
/*    */   RuleState(CssParserStateController controller) {
/* 61 */     this.controller = controller;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(char ch) {
/* 69 */     if (ch == '{') {
/* 70 */       this.controller.pushBlockPrecedingAtRule();
/* 71 */       this.controller.enterRuleStateBasedOnItsType();
/* 72 */     } else if (ch == ';') {
/* 73 */       this.controller.storeSemicolonAtRule();
/* 74 */       this.controller.enterUnknownState();
/*    */     } else {
/* 76 */       this.controller.appendToBuffer(ch);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/syntax/RuleState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */