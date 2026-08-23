/*     */ package com.itextpdf.styledxmlparser.css.media;
/*     */ 
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
/*     */ public final class MediaQueryParser
/*     */ {
/*     */   static List<MediaQuery> parseMediaQueries(String mediaQueriesStr) {
/*  66 */     String[] mediaQueryStrs = mediaQueriesStr.split(",");
/*  67 */     List<MediaQuery> mediaQueries = new ArrayList<>();
/*  68 */     for (String mediaQueryStr : mediaQueryStrs) {
/*  69 */       MediaQuery mediaQuery = parseMediaQuery(mediaQueryStr);
/*  70 */       if (mediaQuery != null) {
/*  71 */         mediaQueries.add(mediaQuery);
/*     */       }
/*     */     } 
/*  74 */     return mediaQueries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MediaQuery parseMediaQuery(String mediaQueryStr) {
/*  84 */     mediaQueryStr = mediaQueryStr.trim().toLowerCase();
/*  85 */     boolean only = false;
/*  86 */     boolean not = false;
/*  87 */     if (mediaQueryStr.startsWith("only")) {
/*  88 */       only = true;
/*  89 */       mediaQueryStr = mediaQueryStr.substring("only".length()).trim();
/*  90 */     } else if (mediaQueryStr.startsWith("not")) {
/*  91 */       not = true;
/*  92 */       mediaQueryStr = mediaQueryStr.substring("not".length()).trim();
/*     */     } 
/*     */     
/*  95 */     int indexOfSpace = mediaQueryStr.indexOf(' ');
/*  96 */     String firstWord = (indexOfSpace != -1) ? mediaQueryStr.substring(0, indexOfSpace) : mediaQueryStr;
/*     */     
/*  98 */     String mediaType = null;
/*  99 */     List<MediaExpression> mediaExpressions = null;
/*     */     
/* 101 */     if (only || not || MediaType.isValidMediaType(firstWord)) {
/* 102 */       mediaType = firstWord;
/* 103 */       mediaExpressions = parseMediaExpressions(mediaQueryStr.substring(firstWord.length()), true);
/*     */     } else {
/* 105 */       mediaExpressions = parseMediaExpressions(mediaQueryStr, false);
/*     */     } 
/*     */     
/* 108 */     return new MediaQuery(mediaType, mediaExpressions, only, not);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<MediaExpression> parseMediaExpressions(String mediaExpressionsStr, boolean shallStartWithAnd) {
/* 119 */     mediaExpressionsStr = mediaExpressionsStr.trim();
/* 120 */     boolean startsWithEnd = mediaExpressionsStr.startsWith("and");
/*     */     
/* 122 */     boolean firstExpression = true;
/* 123 */     String[] mediaExpressionStrs = mediaExpressionsStr.split("and");
/* 124 */     List<MediaExpression> expressions = new ArrayList<>();
/* 125 */     for (String mediaExpressionStr : mediaExpressionStrs) {
/* 126 */       MediaExpression expression = parseMediaExpression(mediaExpressionStr);
/* 127 */       if (expression != null) {
/* 128 */         if (firstExpression && 
/* 129 */           shallStartWithAnd && !startsWithEnd) {
/* 130 */           throw new IllegalStateException("Expected 'and' while parsing media expression");
/*     */         }
/*     */         
/* 133 */         firstExpression = false;
/* 134 */         expressions.add(expression);
/*     */       } 
/*     */     } 
/* 137 */     return expressions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MediaExpression parseMediaExpression(String mediaExpressionStr) {
/*     */     String mediaFeature;
/* 147 */     mediaExpressionStr = mediaExpressionStr.trim();
/* 148 */     if (!mediaExpressionStr.startsWith("(") || !mediaExpressionStr.endsWith(")")) {
/* 149 */       return null;
/*     */     }
/* 151 */     mediaExpressionStr = mediaExpressionStr.substring(1, mediaExpressionStr.length() - 1);
/* 152 */     if (mediaExpressionStr.length() == 0) {
/* 153 */       return null;
/*     */     }
/* 155 */     int colonPos = mediaExpressionStr.indexOf(':');
/*     */     
/* 157 */     String value = null;
/* 158 */     if (colonPos == -1) {
/* 159 */       mediaFeature = mediaExpressionStr;
/*     */     } else {
/* 161 */       mediaFeature = mediaExpressionStr.substring(0, colonPos).trim();
/* 162 */       value = mediaExpressionStr.substring(colonPos + 1).trim();
/*     */     } 
/* 164 */     return new MediaExpression(mediaFeature, value);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/media/MediaQueryParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */