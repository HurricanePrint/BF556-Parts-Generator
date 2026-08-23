/*     */ package com.itextpdf.styledxmlparser.css;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.media.CssMediaRule;
/*     */ import com.itextpdf.styledxmlparser.css.page.CssMarginRule;
/*     */ import com.itextpdf.styledxmlparser.css.page.CssPageRule;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CssNestedAtRuleFactory
/*     */ {
/*     */   public static CssNestedAtRule createNestedRule(String ruleDeclaration) {
/*  68 */     ruleDeclaration = ruleDeclaration.trim();
/*  69 */     String ruleName = extractRuleNameFromDeclaration(ruleDeclaration);
/*  70 */     String ruleParameters = ruleDeclaration.substring(ruleName.length()).trim();
/*     */     
/*  72 */     switch (ruleName) {
/*     */       case "media":
/*  74 */         return (CssNestedAtRule)new CssMediaRule(ruleParameters);
/*     */       case "page":
/*  76 */         return (CssNestedAtRule)new CssPageRule(ruleParameters);
/*     */       case "top-left-corner":
/*     */       case "top-left":
/*     */       case "top-center":
/*     */       case "top-right":
/*     */       case "top-right-corner":
/*     */       case "left-top":
/*     */       case "left-middle":
/*     */       case "left-bottom":
/*     */       case "right-top":
/*     */       case "right-middle":
/*     */       case "right-bottom":
/*     */       case "bottom-left-corner":
/*     */       case "bottom-left":
/*     */       case "bottom-center":
/*     */       case "bottom-right":
/*     */       case "bottom-right-corner":
/*  93 */         return (CssNestedAtRule)new CssMarginRule(ruleName);
/*     */       case "font-face":
/*  95 */         return new CssFontFaceRule();
/*     */     } 
/*  97 */     return new CssNestedAtRule(ruleName, ruleParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String extractRuleNameFromDeclaration(String ruleDeclaration) {
/* 108 */     int separatorIndex, spaceIndex = ruleDeclaration.indexOf(' ');
/* 109 */     int colonIndex = ruleDeclaration.indexOf(':');
/*     */     
/* 111 */     if (spaceIndex == -1) {
/* 112 */       separatorIndex = colonIndex;
/* 113 */     } else if (colonIndex == -1) {
/* 114 */       separatorIndex = spaceIndex;
/*     */     } else {
/* 116 */       separatorIndex = Math.min(spaceIndex, colonIndex);
/*     */     } 
/* 118 */     return (separatorIndex == -1) ? ruleDeclaration : ruleDeclaration.substring(0, separatorIndex);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/CssNestedAtRuleFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */