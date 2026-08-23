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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CssTagSelectorItem
/*    */   implements ICssSelectorItem
/*    */ {
/*    */   private String tagName;
/*    */   private boolean isUniversal;
/*    */   
/*    */   public CssTagSelectorItem(String tagName) {
/* 68 */     this.tagName = tagName.toLowerCase();
/* 69 */     this.isUniversal = "*".equals(tagName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSpecificity() {
/* 77 */     return this.isUniversal ? 0 : 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 85 */     if (!(node instanceof IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/* 86 */       return false;
/*    */     }
/* 88 */     IElementNode element = (IElementNode)node;
/* 89 */     return (this.isUniversal || this.tagName.equals(element.name()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 97 */     return this.tagName;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssTagSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */