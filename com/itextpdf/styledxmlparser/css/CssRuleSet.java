/*     */ package com.itextpdf.styledxmlparser.css;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
/*     */ import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class CssRuleSet
/*     */   extends CssStatement
/*     */ {
/*  60 */   private static final Pattern importantMatcher = Pattern.compile(".*!\\s*important$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ICssSelector selector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<CssDeclaration> normalDeclarations;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<CssDeclaration> importantDeclarations;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CssRuleSet(ICssSelector selector, List<CssDeclaration> declarations) {
/*  81 */     this.selector = selector;
/*  82 */     this.normalDeclarations = new ArrayList<>();
/*  83 */     this.importantDeclarations = new ArrayList<>();
/*  84 */     splitDeclarationsIntoNormalAndImportant(declarations, this.normalDeclarations, this.importantDeclarations);
/*     */   }
/*     */   
/*     */   public CssRuleSet(ICssSelector selector, List<CssDeclaration> normalDeclarations, List<CssDeclaration> importantDeclarations) {
/*  88 */     this.selector = selector;
/*  89 */     this.normalDeclarations = normalDeclarations;
/*  90 */     this.importantDeclarations = importantDeclarations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssRuleSet> getCssRuleSets(INode element, MediaDeviceDescription deviceDescription) {
/*  98 */     if (this.selector.matches(element)) {
/*  99 */       return Collections.singletonList(this);
/*     */     }
/* 101 */     return super.getCssRuleSets(element, deviceDescription);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     StringBuilder sb = new StringBuilder();
/* 111 */     sb.append(this.selector.toString());
/* 112 */     sb.append(" {\n"); int i;
/* 113 */     for (i = 0; i < this.normalDeclarations.size(); i++) {
/* 114 */       if (i > 0) {
/* 115 */         sb.append(";").append("\n");
/*     */       }
/* 117 */       CssDeclaration declaration = this.normalDeclarations.get(i);
/* 118 */       sb.append("    ").append(declaration.toString());
/*     */     } 
/* 120 */     for (i = 0; i < this.importantDeclarations.size(); i++) {
/* 121 */       if (i > 0 || this.normalDeclarations.size() > 0) {
/* 122 */         sb.append(";").append("\n");
/*     */       }
/* 124 */       CssDeclaration declaration = this.importantDeclarations.get(i);
/* 125 */       sb.append("    ").append(declaration.toString()).append(" !important");
/*     */     } 
/* 127 */     sb.append("\n}");
/* 128 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICssSelector getSelector() {
/* 137 */     return this.selector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssDeclaration> getNormalDeclarations() {
/* 146 */     return this.normalDeclarations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssDeclaration> getImportantDeclarations() {
/* 155 */     return this.importantDeclarations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void splitDeclarationsIntoNormalAndImportant(List<CssDeclaration> declarations, List<CssDeclaration> normalDeclarations, List<CssDeclaration> importantDeclarations) {
/* 164 */     for (CssDeclaration declaration : declarations) {
/* 165 */       int exclIndex = declaration.getExpression().indexOf('!');
/* 166 */       if (exclIndex > 0 && importantMatcher.matcher(declaration.getExpression()).matches()) {
/* 167 */         importantDeclarations.add(new CssDeclaration(declaration.getProperty(), declaration.getExpression().substring(0, exclIndex).trim())); continue;
/*     */       } 
/* 169 */       normalDeclarations.add(declaration);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/CssRuleSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */