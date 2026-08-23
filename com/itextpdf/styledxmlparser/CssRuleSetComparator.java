/*    */ package com.itextpdf.styledxmlparser;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.CssRuleSet;
/*    */ import com.itextpdf.styledxmlparser.css.selector.CssSelectorComparator;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CssRuleSetComparator
/*    */   implements Comparator<CssRuleSet>
/*    */ {
/* 58 */   private CssSelectorComparator selectorComparator = new CssSelectorComparator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(CssRuleSet o1, CssRuleSet o2) {
/* 65 */     return this.selectorComparator.compare(o1.getSelector(), o2.getSelector());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/CssRuleSetComparator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */