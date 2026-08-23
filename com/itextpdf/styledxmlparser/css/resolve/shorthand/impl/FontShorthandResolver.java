/*     */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class FontShorthandResolver
/*     */   implements IShorthandResolver
/*     */ {
/*  66 */   private static final Set<String> UNSUPPORTED_VALUES_OF_FONT_SHORTHAND = new HashSet<>(Arrays.asList(new String[] { "caption", "icon", "menu", "message-box", "small-caption", "status-bar" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final Set<String> FONT_WEIGHT_NOT_DEFAULT_VALUES = new HashSet<>(Arrays.asList(new String[] { "bold", "bolder", "lighter", "100", "200", "300", "400", "500", "600", "700", "800", "900" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static final Set<String> FONT_SIZE_VALUES = new HashSet<>(Arrays.asList(new String[] { "medium", "xx-small", "x-small", "small", "large", "x-large", "xx-large", "smaller", "larger" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/*  88 */     if (UNSUPPORTED_VALUES_OF_FONT_SHORTHAND.contains(shorthandExpression)) {
/*  89 */       Logger logger = LoggerFactory.getLogger(FontShorthandResolver.class);
/*  90 */       logger.error(MessageFormatUtil.format("The \"{0}\" value of CSS shorthand property \"font\" is not supported", new Object[] { shorthandExpression }));
/*     */     } 
/*  92 */     if ("initial".equals(shorthandExpression) || "inherit".equals(shorthandExpression)) {
/*  93 */       return Arrays.asList(new CssDeclaration[] { new CssDeclaration("font-style", shorthandExpression), new CssDeclaration("font-variant", shorthandExpression), new CssDeclaration("font-weight", shorthandExpression), new CssDeclaration("font-size", shorthandExpression), new CssDeclaration("line-height", shorthandExpression), new CssDeclaration("font-family", shorthandExpression) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     String fontStyleValue = null;
/* 104 */     String fontVariantValue = null;
/* 105 */     String fontWeightValue = null;
/* 106 */     String fontSizeValue = null;
/* 107 */     String lineHeightValue = null;
/* 108 */     String fontFamilyValue = null;
/*     */     
/* 110 */     List<String> properties = getFontProperties(shorthandExpression.replaceAll("\\s*,\\s*", ","));
/* 111 */     for (String value : properties) {
/* 112 */       int slashSymbolIndex = value.indexOf('/');
/* 113 */       if ("italic".equals(value) || "oblique".equals(value)) {
/* 114 */         fontStyleValue = value; continue;
/* 115 */       }  if ("small-caps".equals(value)) {
/* 116 */         fontVariantValue = value; continue;
/* 117 */       }  if (FONT_WEIGHT_NOT_DEFAULT_VALUES.contains(value)) {
/* 118 */         fontWeightValue = value; continue;
/* 119 */       }  if (slashSymbolIndex > 0) {
/* 120 */         fontSizeValue = value.substring(0, slashSymbolIndex);
/* 121 */         lineHeightValue = value.substring(slashSymbolIndex + 1, value.length()); continue;
/* 122 */       }  if (FONT_SIZE_VALUES.contains(value) || CssUtils.isMetricValue(value) || 
/* 123 */         CssUtils.isNumericValue(value) || CssUtils.isRelativeValue(value)) {
/* 124 */         fontSizeValue = value; continue;
/*     */       } 
/* 126 */       fontFamilyValue = value;
/*     */     } 
/*     */ 
/*     */     
/* 130 */     List<CssDeclaration> cssDeclarations = Arrays.asList(new CssDeclaration[] { new CssDeclaration("font-style", (fontStyleValue == null) ? "initial" : fontStyleValue), new CssDeclaration("font-variant", (fontVariantValue == null) ? "initial" : fontVariantValue), new CssDeclaration("font-weight", (fontWeightValue == null) ? "initial" : fontWeightValue), new CssDeclaration("font-size", (fontSizeValue == null) ? "initial" : fontSizeValue), new CssDeclaration("line-height", (lineHeightValue == null) ? "initial" : lineHeightValue), new CssDeclaration("font-family", (fontFamilyValue == null) ? "initial" : fontFamilyValue) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     return cssDeclarations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> getFontProperties(String shorthandExpression) {
/* 149 */     boolean doubleQuotesAreSpotted = false;
/* 150 */     boolean singleQuoteIsSpotted = false;
/* 151 */     List<String> properties = new ArrayList<>();
/* 152 */     StringBuilder sb = new StringBuilder();
/* 153 */     for (int i = 0; i < shorthandExpression.length(); i++) {
/* 154 */       char currentChar = shorthandExpression.charAt(i);
/* 155 */       if (currentChar == '"') {
/* 156 */         doubleQuotesAreSpotted = !doubleQuotesAreSpotted;
/* 157 */         sb.append(currentChar);
/* 158 */       } else if (currentChar == '\'') {
/* 159 */         singleQuoteIsSpotted = !singleQuoteIsSpotted;
/* 160 */         sb.append(currentChar);
/* 161 */       } else if (!doubleQuotesAreSpotted && !singleQuoteIsSpotted && Character.isWhitespace(currentChar)) {
/* 162 */         if (sb.length() > 0) {
/* 163 */           properties.add(sb.toString());
/* 164 */           sb = new StringBuilder();
/*     */         } 
/*     */       } else {
/* 167 */         sb.append(currentChar);
/*     */       } 
/*     */     } 
/* 170 */     if (sb.length() > 0) {
/* 171 */       properties.add(sb.toString());
/*     */     }
/* 173 */     return properties;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/FontShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */