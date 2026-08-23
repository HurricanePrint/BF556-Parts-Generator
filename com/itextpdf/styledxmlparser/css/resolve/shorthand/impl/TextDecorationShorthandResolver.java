/*    */ package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*    */ import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextDecorationShorthandResolver
/*    */   implements IShorthandResolver
/*    */ {
/* 37 */   private static final Set<String> TEXT_DECORATION_LINE_VALUES = new HashSet<>(Arrays.asList(new String[] { "underline", "overline", "line-through", "blink" }));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   private static final Set<String> TEXT_DECORATION_STYLE_VALUES = new HashSet<>(Arrays.asList(new String[] { "solid", "double", "dotted", "dashed", "wavy" }));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
/* 49 */     if ("initial".equals(shorthandExpression) || "inherit"
/* 50 */       .equals(shorthandExpression)) {
/* 51 */       return Arrays.asList(new CssDeclaration[] { new CssDeclaration("text-decoration-line", shorthandExpression), new CssDeclaration("text-decoration-style", shorthandExpression), new CssDeclaration("text-decoration-color", shorthandExpression) });
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 59 */     String[] props = shorthandExpression.split("\\s+(?![^\\(]*\\))");
/*    */     
/* 61 */     List<String> textDecorationLineValues = new ArrayList<>();
/* 62 */     String textDecorationStyleValue = null;
/* 63 */     String textDecorationColorValue = null;
/*    */     
/* 65 */     for (String value : props) {
/*    */ 
/*    */       
/* 68 */       if (TEXT_DECORATION_LINE_VALUES.contains(value) || "none"
/* 69 */         .equals(value)) {
/* 70 */         textDecorationLineValues.add(value);
/* 71 */       } else if (TEXT_DECORATION_STYLE_VALUES.contains(value)) {
/* 72 */         textDecorationStyleValue = value;
/* 73 */       } else if (!value.isEmpty()) {
/* 74 */         textDecorationColorValue = value;
/*    */       } 
/*    */     } 
/*    */     
/* 78 */     List<CssDeclaration> resolvedDecl = new ArrayList<>();
/* 79 */     if (textDecorationLineValues.isEmpty()) {
/* 80 */       resolvedDecl.add(new CssDeclaration("text-decoration-line", "initial"));
/*    */     } else {
/* 82 */       StringBuilder resultLine = new StringBuilder();
/* 83 */       for (String line : textDecorationLineValues) {
/* 84 */         resultLine.append(line).append(" ");
/*    */       }
/* 86 */       resolvedDecl.add(new CssDeclaration("text-decoration-line", resultLine.toString().trim()));
/*    */     } 
/*    */     
/* 89 */     resolvedDecl.add(new CssDeclaration("text-decoration-style", (textDecorationStyleValue == null) ? "initial" : textDecorationStyleValue));
/*    */     
/* 91 */     resolvedDecl.add(new CssDeclaration("text-decoration-color", (textDecorationColorValue == null) ? "initial" : textDecorationColorValue));
/*    */     
/* 93 */     return resolvedDecl;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/resolve/shorthand/impl/TextDecorationShorthandResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */