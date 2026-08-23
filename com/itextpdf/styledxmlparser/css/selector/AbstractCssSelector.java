/*    */ package com.itextpdf.styledxmlparser.css.selector;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
/*    */ import java.util.Collections;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractCssSelector
/*    */   implements ICssSelector
/*    */ {
/*    */   protected List<ICssSelectorItem> selectorItems;
/*    */   
/*    */   public AbstractCssSelector(List<ICssSelectorItem> selectorItems) {
/* 65 */     this.selectorItems = selectorItems;
/*    */   }
/*    */   
/*    */   public List<ICssSelectorItem> getSelectorItems() {
/* 69 */     return Collections.unmodifiableList(this.selectorItems);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int calculateSpecificity() {
/* 77 */     int specificity = 0;
/* 78 */     for (ICssSelectorItem item : this.selectorItems) {
/* 79 */       specificity += item.getSpecificity();
/*    */     }
/* 81 */     return specificity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 89 */     StringBuilder sb = new StringBuilder();
/* 90 */     for (ICssSelectorItem item : this.selectorItems) {
/* 91 */       sb.append(item.toString());
/*    */     }
/* 93 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/AbstractCssSelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */