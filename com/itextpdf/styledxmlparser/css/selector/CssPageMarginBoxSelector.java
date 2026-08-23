/*    */ package com.itextpdf.styledxmlparser.css.selector;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
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
/*    */ public class CssPageMarginBoxSelector
/*    */   implements ICssSelector
/*    */ {
/*    */   private String pageMarginBoxName;
/*    */   private ICssSelector pageSelector;
/*    */   
/*    */   public CssPageMarginBoxSelector(String pageMarginBoxName, ICssSelector pageSelector) {
/* 66 */     this.pageMarginBoxName = pageMarginBoxName;
/* 67 */     this.pageSelector = pageSelector;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int calculateSpecificity() {
/* 75 */     return this.pageSelector.calculateSpecificity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 83 */     if (!(node instanceof PageMarginBoxContextNode)) {
/* 84 */       return false;
/*    */     }
/* 86 */     PageMarginBoxContextNode marginBoxNode = (PageMarginBoxContextNode)node;
/* 87 */     if (this.pageMarginBoxName.equals(marginBoxNode.getMarginBoxName())) {
/* 88 */       INode parent = node.parentNode();
/* 89 */       return this.pageSelector.matches(parent);
/*    */     } 
/* 91 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/CssPageMarginBoxSelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */