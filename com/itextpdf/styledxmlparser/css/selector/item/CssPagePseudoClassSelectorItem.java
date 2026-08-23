/*    */ package com.itextpdf.styledxmlparser.css.selector.item;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.page.PageContextNode;
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
/*    */ public class CssPagePseudoClassSelectorItem
/*    */   implements ICssSelectorItem
/*    */ {
/*    */   private boolean isSpreadPseudoClass;
/*    */   private String pagePseudoClass;
/*    */   
/*    */   public CssPagePseudoClassSelectorItem(String pagePseudoClass) {
/* 66 */     this.isSpreadPseudoClass = (pagePseudoClass.equals("left") || pagePseudoClass.equals("right"));
/* 67 */     this.pagePseudoClass = pagePseudoClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSpecificity() {
/* 75 */     return this.isSpreadPseudoClass ? 1 : 1024;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 83 */     if (!(node instanceof PageContextNode)) {
/* 84 */       return false;
/*    */     }
/* 86 */     return ((PageContextNode)node).getPageClasses().contains(this.pagePseudoClass);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPagePseudoClassSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */