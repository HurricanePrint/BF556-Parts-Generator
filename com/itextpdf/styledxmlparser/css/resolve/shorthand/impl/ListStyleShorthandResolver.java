/*     */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssGradientUtil;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListStyleShorthandResolver
/*     */   implements IShorthandResolver
/*     */ {
/*  63 */   private static final Set<String> LIST_STYLE_TYPE_VALUES = new HashSet<>(Arrays.asList(new String[] { "disc", "armenian", "circle", "cjk-ideographic", "decimal", "decimal-leading-zero", "georgian", "hebrew", "hiragana", "hiragana-iroha", "lower-alpha", "lower-greek", "lower-latin", "lower-roman", "none", "square", "upper-alpha", "upper-latin", "upper-roman" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final Set<String> LIST_STYLE_POSITION_VALUES = new HashSet<>(Arrays.asList(new String[] { "inside", "outside" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/*  81 */     if ("initial".equals(shorthandExpression) || "inherit".equals(shorthandExpression)) {
/*  82 */       return Arrays.asList(new CssDeclaration[] { new CssDeclaration("list-style-type", shorthandExpression), new CssDeclaration("list-style-position", shorthandExpression), new CssDeclaration("list-style-image", shorthandExpression) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     List<String> props = CssUtils.extractShorthandProperties(shorthandExpression).get(0);
/*     */     
/*  90 */     String listStyleTypeValue = null;
/*  91 */     String listStylePositionValue = null;
/*  92 */     String listStyleImageValue = null;
/*     */     
/*  94 */     for (String value : props) {
/*  95 */       if (value.contains("url(") || CssGradientUtil.isCssLinearGradientValue(value) || ("none"
/*  96 */         .equals(value) && listStyleTypeValue != null)) {
/*  97 */         listStyleImageValue = value; continue;
/*  98 */       }  if (LIST_STYLE_TYPE_VALUES.contains(value)) {
/*  99 */         listStyleTypeValue = value; continue;
/* 100 */       }  if (LIST_STYLE_POSITION_VALUES.contains(value)) {
/* 101 */         listStylePositionValue = value;
/*     */       }
/*     */     } 
/*     */     
/* 105 */     List<CssDeclaration> resolvedDecl = new ArrayList<>();
/* 106 */     resolvedDecl.add(new CssDeclaration("list-style-type", (listStyleTypeValue == null) ? "initial" : listStyleTypeValue));
/* 107 */     resolvedDecl.add(new CssDeclaration("list-style-position", (listStylePositionValue == null) ? "initial" : listStylePositionValue));
/* 108 */     resolvedDecl.add(new CssDeclaration("list-style-image", (listStyleImageValue == null) ? "initial" : listStyleImageValue));
/* 109 */     return resolvedDecl;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/ListStyleShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */