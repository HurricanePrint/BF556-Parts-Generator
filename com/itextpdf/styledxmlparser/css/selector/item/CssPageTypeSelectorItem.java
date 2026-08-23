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
/*    */ public class CssPageTypeSelectorItem
/*    */   implements ICssSelectorItem
/*    */ {
/*    */   private String pageTypeName;
/*    */   
/*    */   public CssPageTypeSelectorItem(String pageTypeName) {
/* 63 */     this.pageTypeName = pageTypeName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSpecificity() {
/* 71 */     return 1048576;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(INode node) {
/* 79 */     if (!(node instanceof PageContextNode)) {
/* 80 */       return false;
/*    */     }
/* 82 */     return (!"auto".equals(this.pageTypeName.toLowerCase()) && this.pageTypeName.equals(((PageContextNode)node).getPageTypeName()));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPageTypeSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */