/*     */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractBoxShorthandResolver
/*     */   implements IShorthandResolver
/*     */ {
/*     */   private static final String _0_LEFT_1 = "{0}-left{1}";
/*     */   private static final String _0_RIGHT_1 = "{0}-right{1}";
/*     */   private static final String _0_BOTTOM_1 = "{0}-bottom{1}";
/*     */   private static final String _0_TOP_1 = "{0}-top{1}";
/*     */   
/*     */   protected abstract String getPrefix();
/*     */   
/*     */   protected abstract String getPostfix();
/*     */   
/*     */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/*  93 */     String[] props = shorthandExpression.split("\\s+");
/*  94 */     List<CssDeclaration> resolvedDecl = new ArrayList<>();
/*  95 */     String topProperty = MessageFormatUtil.format("{0}-top{1}", new Object[] { getPrefix(), getPostfix() });
/*  96 */     String rightProperty = MessageFormatUtil.format("{0}-right{1}", new Object[] { getPrefix(), getPostfix() });
/*  97 */     String bottomProperty = MessageFormatUtil.format("{0}-bottom{1}", new Object[] { getPrefix(), getPostfix() });
/*  98 */     String leftProperty = MessageFormatUtil.format("{0}-left{1}", new Object[] { getPrefix(), getPostfix() });
/*  99 */     if (props.length == 1) {
/* 100 */       resolvedDecl.add(new CssDeclaration(topProperty, props[0]));
/* 101 */       resolvedDecl.add(new CssDeclaration(rightProperty, props[0]));
/* 102 */       resolvedDecl.add(new CssDeclaration(bottomProperty, props[0]));
/* 103 */       resolvedDecl.add(new CssDeclaration(leftProperty, props[0]));
/*     */     } else {
/* 105 */       for (String prop : props) {
/* 106 */         if ("inherit".equals(prop) || "initial".equals(prop)) {
/* 107 */           Logger logger = LoggerFactory.getLogger(AbstractBoxShorthandResolver.class);
/* 108 */           logger.warn(MessageFormatUtil.format("Invalid css property declaration: {0}", new Object[] { shorthandExpression }));
/* 109 */           return Collections.emptyList();
/*     */         } 
/*     */       } 
/* 112 */       if (props.length == 2) {
/* 113 */         resolvedDecl.add(new CssDeclaration(topProperty, props[0]));
/* 114 */         resolvedDecl.add(new CssDeclaration(rightProperty, props[1]));
/* 115 */         resolvedDecl.add(new CssDeclaration(bottomProperty, props[0]));
/* 116 */         resolvedDecl.add(new CssDeclaration(leftProperty, props[1]));
/* 117 */       } else if (props.length == 3) {
/* 118 */         resolvedDecl.add(new CssDeclaration(topProperty, props[0]));
/* 119 */         resolvedDecl.add(new CssDeclaration(rightProperty, props[1]));
/* 120 */         resolvedDecl.add(new CssDeclaration(bottomProperty, props[2]));
/* 121 */         resolvedDecl.add(new CssDeclaration(leftProperty, props[1]));
/* 122 */       } else if (props.length == 4) {
/* 123 */         resolvedDecl.add(new CssDeclaration(topProperty, props[0]));
/* 124 */         resolvedDecl.add(new CssDeclaration(rightProperty, props[1]));
/* 125 */         resolvedDecl.add(new CssDeclaration(bottomProperty, props[2]));
/* 126 */         resolvedDecl.add(new CssDeclaration(leftProperty, props[3]));
/*     */       } 
/*     */     } 
/* 129 */     return resolvedDecl;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/AbstractBoxShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */