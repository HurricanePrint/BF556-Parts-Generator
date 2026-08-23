/*     */ package com.itextpdf.styledxmlparser.css.page;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.CssNestedAtRule;
/*     */ import com.itextpdf.styledxmlparser.css.CssStatement;
/*     */ import com.itextpdf.styledxmlparser.css.selector.CssPageSelector;
/*     */ import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssPageRule
/*     */   extends CssNestedAtRule
/*     */ {
/*     */   private List<ICssSelector> pageSelectors;
/*     */   
/*     */   public CssPageRule(String ruleParameters) {
/*  71 */     super("page", ruleParameters);
/*  72 */     this.pageSelectors = new ArrayList<>();
/*     */     
/*  74 */     String[] selectors = ruleParameters.split(",");
/*  75 */     for (int i = 0; i < selectors.length; i++) {
/*  76 */       selectors[i] = CssUtils.removeDoubleSpacesAndTrim(selectors[i]);
/*     */     }
/*  78 */     for (String currentSelectorStr : selectors) {
/*  79 */       this.pageSelectors.add(new CssPageSelector(currentSelectorStr));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBodyCssDeclarations(List<CssDeclaration> cssDeclarations) {
/*  99 */     for (ICssSelector pageSelector : this.pageSelectors) {
/* 100 */       this.body.add(new CssNonStandardRuleSet(pageSelector, cssDeclarations));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStatementToBody(CssStatement statement) {
/* 109 */     if (statement instanceof CssMarginRule) {
/* 110 */       ((CssMarginRule)statement).setPageSelectors(this.pageSelectors);
/*     */     }
/* 112 */     this.body.add(statement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStatementsToBody(Collection<CssStatement> statements) {
/* 120 */     for (CssStatement statement : statements)
/* 121 */       addStatementToBody(statement); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/page/CssPageRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */