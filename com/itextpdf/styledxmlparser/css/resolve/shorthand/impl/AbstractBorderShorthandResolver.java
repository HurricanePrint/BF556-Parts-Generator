/*     */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CommonCssConstants;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractBorderShorthandResolver
/*     */   implements IShorthandResolver
/*     */ {
/*     */   private static final String _0_WIDTH = "{0}-width";
/*     */   private static final String _0_STYLE = "{0}-style";
/*     */   private static final String _0_COLOR = "{0}-color";
/*     */   
/*     */   protected abstract String getPrefix();
/*     */   
/*     */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/*  85 */     String widthPropName = MessageFormatUtil.format("{0}-width", new Object[] { getPrefix() });
/*  86 */     String stylePropName = MessageFormatUtil.format("{0}-style", new Object[] { getPrefix() });
/*  87 */     String colorPropName = MessageFormatUtil.format("{0}-color", new Object[] { getPrefix() });
/*     */     
/*  89 */     if ("initial".equals(shorthandExpression) || "inherit".equals(shorthandExpression)) {
/*  90 */       return Arrays.asList(new CssDeclaration[] { new CssDeclaration(widthPropName, shorthandExpression), new CssDeclaration(stylePropName, shorthandExpression), new CssDeclaration(colorPropName, shorthandExpression) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     String[] props = shorthandExpression.split("\\s+");
/*     */     
/*  98 */     String borderColorValue = null;
/*  99 */     String borderStyleValue = null;
/* 100 */     String borderWidthValue = null;
/*     */     
/* 102 */     for (String value : props) {
/* 103 */       if ("initial".equals(value) || "inherit".equals(value)) {
/* 104 */         Logger logger = LoggerFactory.getLogger(AbstractBorderShorthandResolver.class);
/* 105 */         logger.warn(MessageFormatUtil.format("Invalid css property declaration: {0}", new Object[] { shorthandExpression }));
/* 106 */         return Collections.emptyList();
/*     */       } 
/* 108 */       if (CommonCssConstants.BORDER_WIDTH_VALUES.contains(value) || CssUtils.isNumericValue(value) || 
/* 109 */         CssUtils.isMetricValue(value) || CssUtils.isRelativeValue(value)) {
/* 110 */         borderWidthValue = value;
/* 111 */       } else if (CommonCssConstants.BORDER_STYLE_VALUES.contains(value) || value.equals("auto")) {
/* 112 */         borderStyleValue = value;
/* 113 */       } else if (CssUtils.isColorProperty(value)) {
/* 114 */         borderColorValue = value;
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     List<CssDeclaration> resolvedDecl = new ArrayList<>();
/* 119 */     resolvedDecl.add(new CssDeclaration(widthPropName, (borderWidthValue == null) ? "initial" : borderWidthValue));
/* 120 */     resolvedDecl.add(new CssDeclaration(stylePropName, (borderStyleValue == null) ? "initial" : borderStyleValue));
/* 121 */     resolvedDecl.add(new CssDeclaration(colorPropName, (borderColorValue == null) ? "initial" : borderColorValue));
/* 122 */     return resolvedDecl;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/AbstractBorderShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */