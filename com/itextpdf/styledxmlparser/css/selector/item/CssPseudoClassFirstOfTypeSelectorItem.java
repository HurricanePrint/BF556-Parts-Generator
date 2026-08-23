/*    */ package com.itextpdf.styledxmlparser.css.selector.item;
/*    */ 
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
/*    */ class CssPseudoClassFirstOfTypeSelectorItem
/*    */   extends CssPseudoClassChildSelectorItem
/*    */ {
/* 53 */   private static final CssPseudoClassFirstOfTypeSelectorItem instance = new CssPseudoClassFirstOfTypeSelectorItem();
/*    */   
/*    */   private CssPseudoClassFirstOfTypeSelectorItem() {
/* 56 */     super("first-of-type");
/*    */   }
/*    */   
/*    */   public static CssPseudoClassFirstOfTypeSelectorItem getInstance() {
/* 60 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 65 */     if (!(node instanceof com.itextpdf.styledxmlparser.node.IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/* 66 */       return false;
/*    */     }
/* 68 */     List<INode> children = getAllSiblingsOfNodeType(node);
/* 69 */     return (!children.isEmpty() && node.equals(children.get(0)));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoClassFirstOfTypeSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */