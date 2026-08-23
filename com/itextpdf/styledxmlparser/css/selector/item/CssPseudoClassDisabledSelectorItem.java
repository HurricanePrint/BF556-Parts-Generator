/*    */ package com.itextpdf.styledxmlparser.css.selector.item;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*    */ import com.itextpdf.styledxmlparser.node.INode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CssPseudoClassDisabledSelectorItem
/*    */   extends CssPseudoClassSelectorItem
/*    */ {
/* 52 */   private static final CssPseudoClassDisabledSelectorItem instance = new CssPseudoClassDisabledSelectorItem();
/*    */   
/*    */   public static CssPseudoClassDisabledSelectorItem getInstance() {
/* 55 */     return instance;
/*    */   }
/*    */   
/*    */   private CssPseudoClassDisabledSelectorItem() {
/* 59 */     super("disabled");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 64 */     if (!(node instanceof IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/* 65 */       return false;
/*    */     }
/* 67 */     return (null != ((IElementNode)node).getAttribute("disabled"));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoClassDisabledSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */