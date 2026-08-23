/*    */ package com.itextpdf.styledxmlparser.css.selector;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.parse.CssPageSelectorParser;
/*    */ import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
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
/*    */ public class CssPageSelector
/*    */   extends AbstractCssSelector
/*    */ {
/*    */   public CssPageSelector(String pageSelectorStr) {
/* 61 */     super(CssPageSelectorParser.parseSelectorItems(pageSelectorStr));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 69 */     if (!(node instanceof com.itextpdf.styledxmlparser.css.page.PageContextNode)) {
/* 70 */       return false;
/*    */     }
/*    */     
/* 73 */     for (ICssSelectorItem selectorItem : this.selectorItems) {
/* 74 */       if (!selectorItem.matches(node)) {
/* 75 */         return false;
/*    */       }
/*    */     } 
/* 78 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/CssPageSelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */