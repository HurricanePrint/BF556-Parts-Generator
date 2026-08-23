/*    */ package com.itextpdf.styledxmlparser.css.selector.item;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.node.INode;
/*    */ import com.itextpdf.styledxmlparser.node.ITextNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CssPseudoClassEmptySelectorItem
/*    */   extends CssPseudoClassSelectorItem
/*    */ {
/* 53 */   private static final CssPseudoClassEmptySelectorItem instance = new CssPseudoClassEmptySelectorItem();
/*    */   
/*    */   private CssPseudoClassEmptySelectorItem() {
/* 56 */     super("empty");
/*    */   }
/*    */   
/*    */   public static CssPseudoClassEmptySelectorItem getInstance() {
/* 60 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 65 */     if (!(node instanceof com.itextpdf.styledxmlparser.node.IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/* 66 */       return false;
/*    */     }
/* 68 */     if (node.childNodes().isEmpty()) {
/* 69 */       return true;
/*    */     }
/* 71 */     for (INode childNode : node.childNodes()) {
/* 72 */       if (!(childNode instanceof ITextNode) || !((ITextNode)childNode).wholeText().isEmpty()) {
/* 73 */         return false;
/*    */       }
/*    */     } 
/* 76 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoClassEmptySelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */