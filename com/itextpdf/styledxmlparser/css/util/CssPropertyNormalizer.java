/*     */ package com.itextpdf.styledxmlparser.css.util;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.PortUtil;
/*     */ import java.util.regex.Pattern;
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
/*     */ class CssPropertyNormalizer
/*     */ {
/*  57 */   private static final Pattern URL_PATTERN = PortUtil.createRegexPatternWithDotMatchingNewlines("^[uU][rR][lL]\\(.*?");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String normalize(String str) {
/*  66 */     StringBuilder sb = new StringBuilder();
/*  67 */     boolean isWhitespace = false;
/*  68 */     int i = 0;
/*  69 */     while (i < str.length()) {
/*  70 */       if (str.charAt(i) == '\\') {
/*  71 */         sb.append(str.charAt(i));
/*  72 */         i++;
/*  73 */         if (i < str.length()) {
/*  74 */           sb.append(str.charAt(i));
/*  75 */           i++;
/*     */         }  continue;
/*  77 */       }  if (Character.isWhitespace(str.charAt(i))) {
/*  78 */         isWhitespace = true;
/*  79 */         i++; continue;
/*     */       } 
/*  81 */       if (isWhitespace) {
/*  82 */         if (sb.length() > 0 && !trimSpaceAfter(sb.charAt(sb.length() - 1)) && !trimSpaceBefore(str.charAt(i))) {
/*  83 */           sb.append(" ");
/*     */         }
/*  85 */         isWhitespace = false;
/*     */       } 
/*  87 */       if (str.charAt(i) == '\'' || str.charAt(i) == '"') {
/*  88 */         i = appendQuotedString(sb, str, i); continue;
/*  89 */       }  if ((str.charAt(i) == 'u' || str.charAt(i) == 'U') && URL_PATTERN.matcher(str.substring(i)).matches()) {
/*  90 */         sb.append(str.substring(i, i + 4).toLowerCase());
/*  91 */         i = appendUrlContent(sb, str, i + 4); continue;
/*     */       } 
/*  93 */       sb.append(Character.toLowerCase(str.charAt(i)));
/*  94 */       i++;
/*     */     } 
/*     */ 
/*     */     
/*  98 */     return sb.toString();
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
/*     */   private static int appendQuotedString(StringBuilder buffer, String source, int start) {
/* 110 */     char endQuoteSymbol = source.charAt(start);
/* 111 */     int end = CssUtils.findNextUnescapedChar(source, endQuoteSymbol, start + 1);
/* 112 */     if (end == -1) {
/* 113 */       end = source.length();
/* 114 */       LoggerFactory.getLogger(CssPropertyNormalizer.class).warn(MessageFormatUtil.format("The quote is not closed in css expression: {0}", new Object[] { source }));
/*     */     } else {
/* 116 */       end++;
/*     */     } 
/* 118 */     buffer.append(source, start, end);
/* 119 */     return end;
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
/*     */   private static int appendUrlContent(StringBuilder buffer, String source, int start) {
/* 131 */     while (Character.isWhitespace(source.charAt(start)) && start < source.length()) {
/* 132 */       start++;
/*     */     }
/* 134 */     if (start < source.length()) {
/* 135 */       int curr = start;
/* 136 */       if (source.charAt(curr) == '"' || source.charAt(curr) == '\'') {
/* 137 */         curr = appendQuotedString(buffer, source, curr);
/* 138 */         return curr;
/*     */       } 
/* 140 */       curr = CssUtils.findNextUnescapedChar(source, ')', curr);
/* 141 */       if (curr == -1) {
/* 142 */         LoggerFactory.getLogger(CssPropertyNormalizer.class).warn(MessageFormatUtil.format("url function is not properly closed in expression:{0}", new Object[] { source }));
/* 143 */         return source.length();
/*     */       } 
/* 145 */       buffer.append(source.substring(start, curr).trim());
/* 146 */       buffer.append(')');
/* 147 */       return curr + 1;
/*     */     } 
/*     */ 
/*     */     
/* 151 */     LoggerFactory.getLogger(CssPropertyNormalizer.class).warn(MessageFormatUtil.format("url function is empty in expression:{0}", new Object[] { source }));
/* 152 */     return source.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean trimSpaceAfter(char ch) {
/* 163 */     return (ch == ',' || ch == '(');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean trimSpaceBefore(char ch) {
/* 173 */     return (ch == ',' || ch == ')');
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/util/CssPropertyNormalizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */