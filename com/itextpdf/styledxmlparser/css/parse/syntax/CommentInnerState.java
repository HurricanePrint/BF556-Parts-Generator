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
/*    */ class CommentInnerState
/*    */   implements IParserState
/*    */ {
/*    */   private CssParserStateController controller;
/*    */   
/*    */   CommentInnerState(CssParserStateController controller) {
/* 61 */     this.controller = controller;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(char ch) {
/* 69 */     if (ch == '*')
/* 70 */       this.controller.enterCommentEndState(); 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/syntax/CommentInnerState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */