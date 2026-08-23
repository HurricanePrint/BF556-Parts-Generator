/*     */ package com.itextpdf.styledxmlparser.css;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.CssRuleSetComparator;
/*     */ import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.ShorthandResolverFactory;
/*     */ import com.itextpdf.styledxmlparser.css.validate.CssDeclarationValidationMaster;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public class CssStyleSheet
/*     */ {
/*  74 */   private List<CssStatement> statements = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStatement(CssStatement statement) {
/*  83 */     this.statements.add(statement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendCssStyleSheet(CssStyleSheet anotherCssStyleSheet) {
/*  93 */     this.statements.addAll(anotherCssStyleSheet.statements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     StringBuilder sb = new StringBuilder();
/* 102 */     for (CssStatement statement : this.statements) {
/* 103 */       if (sb.length() > 0) {
/* 104 */         sb.append("\n");
/*     */       }
/* 106 */       sb.append(statement.toString());
/*     */     } 
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssStatement> getStatements() {
/* 117 */     return Collections.unmodifiableList(this.statements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssDeclaration> getCssDeclarations(INode node, MediaDeviceDescription deviceDescription) {
/* 128 */     List<CssRuleSet> ruleSets = getCssRuleSets(node, deviceDescription);
/* 129 */     Map<String, CssDeclaration> declarations = new LinkedHashMap<>();
/* 130 */     for (CssRuleSet ruleSet : ruleSets) {
/* 131 */       populateDeclarationsMap(ruleSet.getNormalDeclarations(), declarations);
/*     */     }
/* 133 */     for (CssRuleSet ruleSet : ruleSets) {
/* 134 */       populateDeclarationsMap(ruleSet.getImportantDeclarations(), declarations);
/*     */     }
/* 136 */     return new ArrayList<>(declarations.values());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> extractStylesFromRuleSets(List<CssRuleSet> ruleSets) {
/* 146 */     Map<String, CssDeclaration> declarations = new LinkedHashMap<>();
/* 147 */     for (CssRuleSet ruleSet : ruleSets) {
/* 148 */       populateDeclarationsMap(ruleSet.getNormalDeclarations(), declarations);
/*     */     }
/* 150 */     for (CssRuleSet ruleSet : ruleSets) {
/* 151 */       populateDeclarationsMap(ruleSet.getImportantDeclarations(), declarations);
/*     */     }
/* 153 */     Map<String, String> stringMap = new LinkedHashMap<>();
/* 154 */     for (Map.Entry<String, CssDeclaration> entry : declarations.entrySet()) {
/* 155 */       stringMap.put(entry.getKey(), ((CssDeclaration)entry.getValue()).getExpression());
/*     */     }
/* 157 */     return stringMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void populateDeclarationsMap(List<CssDeclaration> declarations, Map<String, CssDeclaration> map) {
/* 167 */     for (CssDeclaration declaration : declarations) {
/* 168 */       IShorthandResolver shorthandResolver = ShorthandResolverFactory.getShorthandResolver(declaration.getProperty());
/* 169 */       if (shorthandResolver == null) {
/* 170 */         putDeclarationInMapIfValid(map, declaration); continue;
/*     */       } 
/* 172 */       List<CssDeclaration> resolvedShorthandProps = shorthandResolver.resolveShorthand(declaration.getExpression());
/* 173 */       populateDeclarationsMap(resolvedShorthandProps, map);
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
/*     */   public List<CssRuleSet> getCssRuleSets(INode node, MediaDeviceDescription deviceDescription) {
/* 186 */     List<CssRuleSet> ruleSets = new ArrayList<>();
/* 187 */     for (CssStatement statement : this.statements) {
/* 188 */       ruleSets.addAll(statement.getCssRuleSets(node, deviceDescription));
/*     */     }
/* 190 */     Collections.sort(ruleSets, (Comparator<? super CssRuleSet>)new CssRuleSetComparator());
/* 191 */     return ruleSets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void putDeclarationInMapIfValid(Map<String, CssDeclaration> stylesMap, CssDeclaration cssDeclaration) {
/* 201 */     if (CssDeclarationValidationMaster.checkDeclaration(cssDeclaration)) {
/* 202 */       stylesMap.put(cssDeclaration.getProperty(), cssDeclaration);
/*     */     } else {
/* 204 */       Logger logger = LoggerFactory.getLogger(ICssResolver.class);
/* 205 */       logger.warn(MessageFormatUtil.format("Invalid css property declaration: {0}", new Object[] { cssDeclaration }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/CssStyleSheet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */