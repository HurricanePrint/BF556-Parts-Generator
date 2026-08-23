/*    */ package com.itextpdf.styledxmlparser.css.selector.item;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.parse.CssSelectorParser;
/*    */ import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
/*    */ import com.itextpdf.styledxmlparser.node.INode;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CssPseudoClassNotSelectorItem
/*    */   extends CssPseudoClassSelectorItem
/*    */ {
/*    */   private ICssSelector argumentsSelector;
/*    */   
/*    */   CssPseudoClassNotSelectorItem(ICssSelector argumentsSelector) {
/* 59 */     super("not", argumentsSelector.toString());
/* 60 */     this.argumentsSelector = argumentsSelector;
/*    */   }
/*    */   
/*    */   public List<ICssSelectorItem> getArgumentsSelector() {
/* 64 */     return CssSelectorParser.parseSelectorItems(this.arguments);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 69 */     if (!(node instanceof com.itextpdf.styledxmlparser.node.IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/* 70 */       return false;
/*    */     }
/* 72 */     return !this.argumentsSelector.matches(node);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoClassNotSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */