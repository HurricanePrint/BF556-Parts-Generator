/*    */ package com.itextpdf.styledxmlparser.css.selector.item;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementNode;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CssPseudoElementSelectorItem
/*    */   implements ICssSelectorItem
/*    */ {
/*    */   private String pseudoElementName;
/*    */   
/*    */   public CssPseudoElementSelectorItem(String pseudoElementName) {
/* 63 */     this.pseudoElementName = pseudoElementName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSpecificity() {
/* 71 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 79 */     return (node instanceof CssPseudoElementNode && ((CssPseudoElementNode)node).getPseudoElementName().equals(this.pseudoElementName));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "::" + this.pseudoElementName;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoElementSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */