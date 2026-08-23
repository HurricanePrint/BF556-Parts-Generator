/*     */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCornersShorthandResolver
/*     */   implements IShorthandResolver
/*     */ {
/*     */   private static final String _0_BOTTOM_LEFT_1 = "{0}-bottom-left{1}";
/*     */   private static final String _0_BOTTOM_RIGHT_1 = "{0}-bottom-right{1}";
/*     */   private static final String _0_TOP_LEFT_1 = "{0}-top-left{1}";
/*     */   private static final String _0_TOP_RIGHT_1 = "{0}-top-right{1}";
/*     */   
/*     */   protected abstract String getPrefix();
/*     */   
/*     */   protected abstract String getPostfix();
/*     */   
/*     */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/*  96 */     String[] props = shorthandExpression.split("\\s*\\/\\s*");
/*  97 */     String[][] properties = new String[props.length][];
/*  98 */     for (int i = 0; i < props.length; i++) {
/*  99 */       properties[i] = props[i].split("\\s+");
/*     */     }
/*     */     
/* 102 */     String[] resultExpressions = new String[4];
/* 103 */     for (int j = 0; j < resultExpressions.length; j++) {
/* 104 */       resultExpressions[j] = "";
/*     */     }
/*     */     
/* 107 */     List<CssDeclaration> resolvedDecl = new ArrayList<>();
/* 108 */     String topLeftProperty = MessageFormatUtil.format("{0}-top-left{1}", new Object[] { getPrefix(), getPostfix() });
/* 109 */     String topRightProperty = MessageFormatUtil.format("{0}-top-right{1}", new Object[] { getPrefix(), getPostfix() });
/* 110 */     String bottomRightProperty = MessageFormatUtil.format("{0}-bottom-right{1}", new Object[] { getPrefix(), getPostfix() });
/* 111 */     String bottomLeftProperty = MessageFormatUtil.format("{0}-bottom-left{1}", new Object[] { getPrefix(), getPostfix() });
/*     */     
/* 113 */     for (int k = 0; k < properties.length; k++) {
/* 114 */       if ((properties[k]).length == 1) {
/* 115 */         resultExpressions[0] = resultExpressions[0] + properties[k][0] + " ";
/* 116 */         resultExpressions[1] = resultExpressions[1] + properties[k][0] + " ";
/* 117 */         resultExpressions[2] = resultExpressions[2] + properties[k][0] + " ";
/* 118 */         resultExpressions[3] = resultExpressions[3] + properties[k][0] + " ";
/* 119 */       } else if ((properties[k]).length == 2) {
/* 120 */         resultExpressions[0] = resultExpressions[0] + properties[k][0] + " ";
/* 121 */         resultExpressions[1] = resultExpressions[1] + properties[k][1] + " ";
/* 122 */         resultExpressions[2] = resultExpressions[2] + properties[k][0] + " ";
/* 123 */         resultExpressions[3] = resultExpressions[3] + properties[k][1] + " ";
/* 124 */       } else if ((properties[k]).length == 3) {
/* 125 */         resultExpressions[0] = resultExpressions[0] + properties[k][0] + " ";
/* 126 */         resultExpressions[1] = resultExpressions[1] + properties[k][1] + " ";
/* 127 */         resultExpressions[2] = resultExpressions[2] + properties[k][2] + " ";
/* 128 */         resultExpressions[3] = resultExpressions[3] + properties[k][1] + " ";
/* 129 */       } else if ((properties[k]).length == 4) {
/* 130 */         resultExpressions[0] = resultExpressions[0] + properties[k][0] + " ";
/* 131 */         resultExpressions[1] = resultExpressions[1] + properties[k][1] + " ";
/* 132 */         resultExpressions[2] = resultExpressions[2] + properties[k][2] + " ";
/* 133 */         resultExpressions[3] = resultExpressions[3] + properties[k][3] + " ";
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     resolvedDecl.add(new CssDeclaration(topLeftProperty, resultExpressions[0]));
/* 138 */     resolvedDecl.add(new CssDeclaration(topRightProperty, resultExpressions[1]));
/* 139 */     resolvedDecl.add(new CssDeclaration(bottomRightProperty, resultExpressions[2]));
/* 140 */     resolvedDecl.add(new CssDeclaration(bottomLeftProperty, resultExpressions[3]));
/* 141 */     return resolvedDecl;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/AbstractCornersShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */