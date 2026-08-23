/*     */ package com.itextpdf.styledxmlparser.css.parse;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.CssRuleSet;
/*     */ import com.itextpdf.styledxmlparser.css.selector.CssSelector;
/*     */ import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class CssRuleSetParser
/*     */ {
/*  65 */   private static final Logger logger = LoggerFactory.getLogger(CssRuleSetParser.class);
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
/*     */   public static List<CssDeclaration> parsePropertyDeclarations(String propertiesStr) {
/*  80 */     List<CssDeclaration> declarations = new ArrayList<>();
/*  81 */     int openedCommentPos = propertiesStr.indexOf("/*", 0);
/*  82 */     if (openedCommentPos != -1) {
/*  83 */       declarations.addAll(parsePropertyDeclarations(propertiesStr.substring(0, openedCommentPos)));
/*  84 */       int closedCommentPos = propertiesStr.indexOf("*/", openedCommentPos);
/*  85 */       if (closedCommentPos != -1) {
/*  86 */         declarations.addAll(parsePropertyDeclarations(propertiesStr.substring(closedCommentPos + 2, propertiesStr.length())));
/*     */       }
/*     */     } else {
/*  89 */       int pos = getSemicolonPosition(propertiesStr, 0);
/*  90 */       while (pos != -1) {
/*  91 */         String[] propertySplit = splitCssProperty(propertiesStr.substring(0, pos));
/*  92 */         if (propertySplit != null) {
/*  93 */           declarations.add(new CssDeclaration(propertySplit[0], propertySplit[1]));
/*     */         }
/*  95 */         propertiesStr = propertiesStr.substring(pos + 1);
/*  96 */         pos = getSemicolonPosition(propertiesStr, 0);
/*     */       } 
/*  98 */       if (!propertiesStr.replaceAll("[\\n\\r\\t ]", "").isEmpty()) {
/*  99 */         String[] propertySplit = splitCssProperty(propertiesStr);
/* 100 */         if (propertySplit != null) {
/* 101 */           declarations.add(new CssDeclaration(propertySplit[0], propertySplit[1]));
/*     */         }
/* 103 */         return declarations;
/*     */       } 
/*     */     } 
/* 106 */     return declarations;
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
/*     */   public static List<CssRuleSet> parseRuleSet(String selectorStr, String propertiesStr) {
/* 120 */     List<CssDeclaration> declarations = parsePropertyDeclarations(propertiesStr);
/* 121 */     List<CssRuleSet> ruleSets = new ArrayList<>();
/*     */ 
/*     */     
/* 124 */     String[] selectors = selectorStr.split(",");
/* 125 */     for (int i = 0; i < selectors.length; i++) {
/* 126 */       selectors[i] = CssUtils.removeDoubleSpacesAndTrim(selectors[i]);
/* 127 */       if (selectors[i].length() == 0)
/* 128 */         return ruleSets; 
/*     */     } 
/* 130 */     for (String currentSelectorStr : selectors) {
/*     */       try {
/* 132 */         ruleSets.add(new CssRuleSet((ICssSelector)new CssSelector(currentSelectorStr), declarations));
/* 133 */       } catch (Exception exc) {
/* 134 */         logger.error(MessageFormatUtil.format("Error while parsing css selector: {0}", new Object[] { currentSelectorStr }), exc);
/*     */ 
/*     */         
/* 137 */         declarations.clear();
/* 138 */         return ruleSets;
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     return ruleSets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] splitCssProperty(String property) {
/* 152 */     if (property.trim().isEmpty()) {
/* 153 */       return null;
/*     */     }
/* 155 */     String[] result = new String[2];
/* 156 */     int position = property.indexOf(":");
/* 157 */     if (position < 0) {
/* 158 */       logger.error(MessageFormatUtil.format("Invalid css property declaration: {0}", new Object[] { property.trim() }));
/* 159 */       return null;
/*     */     } 
/* 161 */     result[0] = property.substring(0, position);
/* 162 */     result[1] = property.substring(position + 1);
/*     */     
/* 164 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getSemicolonPosition(String propertiesStr, int fromIndex) {
/* 175 */     int semiColonPos = propertiesStr.indexOf(";", fromIndex);
/* 176 */     int closedBracketPos = propertiesStr.indexOf(")", semiColonPos + 1);
/* 177 */     int openedBracketPos = propertiesStr.indexOf("(", fromIndex);
/* 178 */     if (semiColonPos != -1 && openedBracketPos < semiColonPos && closedBracketPos > 0) {
/* 179 */       int nextOpenedBracketPos = openedBracketPos;
/*     */       do {
/* 181 */         openedBracketPos = nextOpenedBracketPos;
/* 182 */         nextOpenedBracketPos = propertiesStr.indexOf("(", openedBracketPos + 1);
/* 183 */       } while (nextOpenedBracketPos < closedBracketPos && nextOpenedBracketPos > 0);
/*     */     } 
/* 185 */     if (semiColonPos != -1 && semiColonPos > openedBracketPos && semiColonPos < closedBracketPos) {
/* 186 */       return getSemicolonPosition(propertiesStr, closedBracketPos + 1);
/*     */     }
/* 188 */     return semiColonPos;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/CssRuleSetParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */