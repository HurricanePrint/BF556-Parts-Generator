/*    */ package com.itextpdf.styledxmlparser.css;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CssSemicolonAtRule
/*    */   extends CssAtRule
/*    */ {
/*    */   private String ruleParams;
/*    */   
/*    */   public CssSemicolonAtRule(String ruleDeclaration) {
/* 61 */     super(CssNestedAtRuleFactory.extractRuleNameFromDeclaration(ruleDeclaration.trim()));
/* 62 */     this.ruleParams = ruleDeclaration.trim().substring(this.ruleName.length()).trim();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return MessageFormatUtil.format("@{0} {1};", new Object[] { this.ruleName, this.ruleParams });
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/CssSemicolonAtRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */