/*     */ package com.itextpdf.styledxmlparser.css.parse;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.CssAttributeSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.CssClassSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.CssIdSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.CssPseudoElementSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.CssSeparatorSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.CssTagSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CssSelectorParser
/*     */ {
/*  71 */   private static final Set<String> legacyPseudoElements = new HashSet<>();
/*     */   static {
/*  73 */     legacyPseudoElements.add("first-line");
/*  74 */     legacyPseudoElements.add("first-letter");
/*  75 */     legacyPseudoElements.add("before");
/*  76 */     legacyPseudoElements.add("after");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String SELECTOR_PATTERN_STR = "(\\*)|([_a-zA-Z][\\w-]*)|(\\.[_a-zA-Z][\\w-]*)|(#[_a-z][\\w-]*)|(\\[[_a-zA-Z][\\w-]*(([~^$*|])?=((\"[^\"]+\")|([^\"]+)|('[^']+')|(\"\")|('')))?\\])|(::?[a-zA-Z-]*)|( )|(\\+)|(>)|(~)";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final Pattern selectorPattern = Pattern.compile("(\\*)|([_a-zA-Z][\\w-]*)|(\\.[_a-zA-Z][\\w-]*)|(#[_a-z][\\w-]*)|(\\[[_a-zA-Z][\\w-]*(([~^$*|])?=((\"[^\"]+\")|([^\"]+)|('[^']+')|(\"\")|('')))?\\])|(::?[a-zA-Z-]*)|( )|(\\+)|(>)|(~)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ICssSelectorItem> parseSelectorItems(String selector) {
/* 103 */     List<ICssSelectorItem> selectorItems = new ArrayList<>();
/* 104 */     CssSelectorParserMatch match = new CssSelectorParserMatch(selector, selectorPattern);
/* 105 */     boolean tagSelectorDescription = false;
/* 106 */     while (match.success()) {
/* 107 */       ICssSelectorItem lastItem; CssSeparatorSelectorItem curItem; String selectorItem = match.getValue();
/* 108 */       char firstChar = selectorItem.charAt(0);
/* 109 */       switch (firstChar) {
/*     */         case '#':
/* 111 */           match.next();
/* 112 */           selectorItems.add(new CssIdSelectorItem(selectorItem.substring(1)));
/*     */           continue;
/*     */         case '.':
/* 115 */           match.next();
/* 116 */           selectorItems.add(new CssClassSelectorItem(selectorItem.substring(1)));
/*     */           continue;
/*     */         case '[':
/* 119 */           match.next();
/* 120 */           selectorItems.add(new CssAttributeSelectorItem(selectorItem));
/*     */           continue;
/*     */         case ':':
/* 123 */           appendPseudoSelector(selectorItems, selectorItem, match);
/*     */           continue;
/*     */         case ' ':
/*     */         case '+':
/*     */         case '>':
/*     */         case '~':
/* 129 */           match.next();
/* 130 */           if (selectorItems.size() == 0) {
/* 131 */             throw new IllegalArgumentException(MessageFormatUtil.format("Invalid token detected in the start of the selector string: {0}", new Object[] { Character.valueOf(firstChar) }));
/*     */           }
/* 133 */           lastItem = selectorItems.get(selectorItems.size() - 1);
/* 134 */           curItem = new CssSeparatorSelectorItem(firstChar);
/* 135 */           if (lastItem instanceof CssSeparatorSelectorItem) {
/* 136 */             if (curItem.getSeparator() == ' ')
/*     */               continue; 
/* 138 */             if (((CssSeparatorSelectorItem)lastItem).getSeparator() == ' ') {
/* 139 */               selectorItems.set(selectorItems.size() - 1, curItem); continue;
/*     */             } 
/* 141 */             throw new IllegalArgumentException(MessageFormatUtil.format("Invalid selector description. Two consequent characters occurred: {0}, {1}", new Object[] { Character.valueOf(((CssSeparatorSelectorItem)lastItem).getSeparator()), Character.valueOf(curItem.getSeparator()) }));
/*     */           } 
/*     */           
/* 144 */           selectorItems.add(curItem);
/* 145 */           tagSelectorDescription = false;
/*     */           continue;
/*     */       } 
/*     */       
/* 149 */       match.next();
/* 150 */       if (tagSelectorDescription) {
/* 151 */         throw new IllegalStateException("Invalid selector string");
/*     */       }
/* 153 */       tagSelectorDescription = true;
/* 154 */       selectorItems.add(new CssTagSelectorItem(selectorItem));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (selectorItems.size() == 0) {
/* 160 */       throw new IllegalArgumentException("Selector declaration is invalid");
/*     */     }
/*     */     
/* 163 */     return selectorItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void appendPseudoSelector(List<ICssSelectorItem> selectorItems, String pseudoSelector, CssSelectorParserMatch match) {
/* 174 */     pseudoSelector = pseudoSelector.toLowerCase();
/* 175 */     int start = match.getIndex() + pseudoSelector.length();
/* 176 */     String source = match.getSource();
/* 177 */     if (start < source.length() && source.charAt(start) == '(') {
/* 178 */       int bracketDepth = 1;
/* 179 */       int curr = start + 1;
/* 180 */       while (bracketDepth > 0 && curr < source.length()) {
/* 181 */         if (source.charAt(curr) == '(') {
/* 182 */           bracketDepth++;
/* 183 */         } else if (source.charAt(curr) == ')') {
/* 184 */           bracketDepth--;
/* 185 */         } else if (source.charAt(curr) == '"' || source.charAt(curr) == '\'') {
/* 186 */           curr = CssUtils.findNextUnescapedChar(source, source.charAt(curr), curr + 1);
/*     */         } 
/* 188 */         curr++;
/*     */       } 
/* 190 */       if (bracketDepth == 0) {
/* 191 */         match.next(curr);
/* 192 */         pseudoSelector = pseudoSelector + source.substring(start, curr);
/*     */       } else {
/* 194 */         match.next();
/*     */       } 
/*     */     } else {
/* 197 */       match.next();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     if (pseudoSelector.startsWith("::")) {
/* 207 */       selectorItems.add(new CssPseudoElementSelectorItem(pseudoSelector.substring(2)));
/* 208 */     } else if (pseudoSelector.startsWith(":") && legacyPseudoElements.contains(pseudoSelector.substring(1))) {
/* 209 */       selectorItems.add(new CssPseudoElementSelectorItem(pseudoSelector.substring(1)));
/*     */     } else {
/* 211 */       CssPseudoClassSelectorItem cssPseudoClassSelectorItem = CssPseudoClassSelectorItem.create(pseudoSelector.substring(1));
/* 212 */       if (cssPseudoClassSelectorItem == null) {
/* 213 */         throw new IllegalArgumentException(MessageFormatUtil.format("Unsupported pseudo css selector: {0}", new Object[] { pseudoSelector }));
/*     */       }
/* 215 */       selectorItems.add(cssPseudoClassSelectorItem);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/CssSelectorParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */