/*    */ package com.itextpdf.styledxmlparser.css.page;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*    */ import com.itextpdf.styledxmlparser.css.CssNestedAtRule;
/*    */ import com.itextpdf.styledxmlparser.css.selector.CssPageMarginBoxSelector;
/*    */ import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
/*    */ import java.util.ArrayList;
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
/*    */ public class CssMarginRule
/*    */   extends CssNestedAtRule
/*    */ {
/*    */   private List<ICssSelector> pageSelectors;
/*    */   
/*    */   public CssMarginRule(String ruleName) {
/* 67 */     this(ruleName, "");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public CssMarginRule(String ruleName, String ruleParameters) {
/* 79 */     super(ruleName, ruleParameters);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBodyCssDeclarations(List<CssDeclaration> cssDeclarations) {
/* 87 */     for (ICssSelector pageSelector : this.pageSelectors) {
/* 88 */       this.body.add(new CssNonStandardRuleSet((ICssSelector)new CssPageMarginBoxSelector(getRuleName(), pageSelector), cssDeclarations));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void setPageSelectors(List<ICssSelector> pageSelectors) {
/* 98 */     this.pageSelectors = new ArrayList<>(pageSelectors);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/page/CssMarginRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */