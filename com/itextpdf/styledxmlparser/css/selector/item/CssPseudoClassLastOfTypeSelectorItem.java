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
/*    */ class CssPseudoClassLastOfTypeSelectorItem
/*    */   extends CssPseudoClassChildSelectorItem
/*    */ {
/* 53 */   private static final CssPseudoClassLastOfTypeSelectorItem instance = new CssPseudoClassLastOfTypeSelectorItem();
/*    */   
/*    */   private CssPseudoClassLastOfTypeSelectorItem() {
/* 56 */     super("last-of-type");
/*    */   }
/*    */   
/*    */   public static CssPseudoClassLastOfTypeSelectorItem getInstance() {
/* 60 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 65 */     if (!(node instanceof com.itextpdf.styledxmlparser.node.IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/* 66 */       return false;
/*    */     }
/* 68 */     List<INode> children = getAllSiblingsOfNodeType(node);
/* 69 */     return (!children.isEmpty() && node.equals(children.get(children.size() - 1)));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoClassLastOfTypeSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */