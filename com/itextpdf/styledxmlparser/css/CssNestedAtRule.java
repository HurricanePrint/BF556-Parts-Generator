/*     */ package com.itextpdf.styledxmlparser.css;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssNestedAtRule
/*     */   extends CssAtRule
/*     */ {
/*     */   private String ruleParameters;
/*     */   protected List<CssStatement> body;
/*     */   
/*     */   public CssNestedAtRule(String ruleName, String ruleParameters) {
/*  73 */     super(ruleName);
/*  74 */     this.ruleParameters = ruleParameters;
/*  75 */     this.body = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStatementToBody(CssStatement statement) {
/*  84 */     this.body.add(statement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStatementsToBody(Collection<CssStatement> statements) {
/*  93 */     this.body.addAll(statements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBodyCssDeclarations(List<CssDeclaration> cssDeclarations) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssRuleSet> getCssRuleSets(INode node, MediaDeviceDescription deviceDescription) {
/* 110 */     List<CssRuleSet> result = new ArrayList<>();
/* 111 */     for (CssStatement childStatement : this.body) {
/* 112 */       result.addAll(childStatement.getCssRuleSets(node, deviceDescription));
/*     */     }
/* 114 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssStatement> getStatements() {
/* 123 */     return this.body;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     StringBuilder sb = new StringBuilder();
/* 132 */     sb.append(MessageFormatUtil.format("@{0} {1} ", new Object[] { this.ruleName, this.ruleParameters }));
/* 133 */     sb.append("{");
/* 134 */     sb.append("\n");
/* 135 */     for (int i = 0; i < this.body.size(); i++) {
/* 136 */       sb.append("    ");
/* 137 */       sb.append(((CssStatement)this.body.get(i)).toString().replace("\n", "\n    "));
/* 138 */       if (i != this.body.size() - 1) {
/* 139 */         sb.append("\n");
/*     */       }
/*     */     } 
/* 142 */     sb.append("\n}");
/* 143 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String getRuleParameters() {
/* 147 */     return this.ruleParameters;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/CssNestedAtRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */