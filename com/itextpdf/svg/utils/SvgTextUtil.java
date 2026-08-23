/*     */ package com.itextpdf.svg.utils;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.styledxmlparser.util.WhiteSpaceUtil;
/*     */ import com.itextpdf.svg.renderers.impl.ISvgTextNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.TextLeafSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.TextSvgBranchRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SvgTextUtil
/*     */ {
/*     */   public static String trimLeadingWhitespace(String toTrim) {
/*  71 */     if (toTrim == null) {
/*  72 */       return "";
/*     */     }
/*  74 */     int current = 0;
/*  75 */     int end = toTrim.length();
/*  76 */     while (current < end) {
/*  77 */       char currentChar = toTrim.charAt(current);
/*  78 */       if (Character.isWhitespace(currentChar) && currentChar != '\n' && currentChar != '\r')
/*     */       {
/*  80 */         current++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  85 */     return toTrim.substring(current);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String trimTrailingWhitespace(String toTrim) {
/*  95 */     if (toTrim == null) {
/*  96 */       return "";
/*     */     }
/*  98 */     int end = toTrim.length();
/*  99 */     if (end > 0) {
/* 100 */       int current = end - 1;
/* 101 */       while (current >= 0) {
/* 102 */         char currentChar = toTrim.charAt(current);
/* 103 */         if (Character.isWhitespace(currentChar) && currentChar != '\n' && currentChar != '\r')
/*     */         {
/* 105 */           current--;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 110 */       if (current < 0) {
/* 111 */         return "";
/*     */       }
/* 113 */       return toTrim.substring(0, current + 1);
/*     */     } 
/*     */     
/* 116 */     return toTrim;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processWhiteSpace(TextSvgBranchRenderer root, boolean isLeadingElement) {
/* 133 */     boolean performLeadingTrim = isLeadingElement;
/* 134 */     for (ISvgTextNodeRenderer child : root.getChildren()) {
/*     */       
/* 136 */       if (child instanceof TextSvgBranchRenderer) {
/*     */         
/* 138 */         processWhiteSpace((TextSvgBranchRenderer)child, child.containsAbsolutePositionChange());
/* 139 */         ((TextSvgBranchRenderer)child).markWhiteSpaceProcessed();
/*     */       } 
/* 141 */       if (child instanceof TextLeafSvgNodeRenderer) {
/*     */         
/* 143 */         TextLeafSvgNodeRenderer leafRend = (TextLeafSvgNodeRenderer)child;
/*     */         
/* 145 */         String toProcess = leafRend.getAttribute("text_content");
/* 146 */         toProcess = toProcess.replaceAll("\\s+", " ");
/* 147 */         toProcess = WhiteSpaceUtil.collapseConsecutiveSpaces(toProcess);
/* 148 */         if (performLeadingTrim) {
/*     */           
/* 150 */           toProcess = trimLeadingWhitespace(toProcess);
/* 151 */           toProcess = trimTrailingWhitespace(toProcess);
/* 152 */           performLeadingTrim = false;
/*     */         } else {
/*     */           
/* 155 */           toProcess = trimTrailingWhitespace(toProcess);
/*     */         } 
/* 157 */         leafRend.setAttribute("text_content", toProcess);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOnlyWhiteSpace(String s) {
/* 169 */     String trimmedText = s.replaceAll("\\s+", " ");
/*     */     
/* 171 */     trimmedText = trimLeadingWhitespace(trimmedText);
/*     */     
/* 173 */     trimmedText = trimTrailingWhitespace(trimmedText);
/* 174 */     return "".equals(trimmedText);
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
/*     */   public static float resolveFontSize(ISvgTextNodeRenderer renderer, float parentFontSize) {
/* 186 */     float fontSize = Float.NaN;
/* 187 */     String elementFontSize = renderer.getAttribute("font-size");
/* 188 */     if (null != elementFontSize && !elementFontSize.isEmpty()) {
/* 189 */       if (CssUtils.isRelativeValue(elementFontSize) || "larger".equals(elementFontSize) || "smaller".equals(elementFontSize)) {
/* 190 */         fontSize = CssUtils.parseRelativeFontSize(elementFontSize, parentFontSize);
/*     */       } else {
/* 192 */         fontSize = CssUtils.parseAbsoluteFontSize(elementFontSize, "px");
/*     */       } 
/*     */     }
/* 195 */     if (Float.isNaN(fontSize) || fontSize < 0.0F) {
/* 196 */       fontSize = parentFontSize;
/*     */     }
/* 198 */     return fontSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String filterReferenceValue(String name) {
/* 208 */     return name.replace("#", "").replace("url(", "").replace(")", "").trim();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/utils/SvgTextUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */