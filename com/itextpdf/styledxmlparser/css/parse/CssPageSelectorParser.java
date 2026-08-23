/*    */ package com.itextpdf.styledxmlparser.css.parse;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.selector.item.CssPagePseudoClassSelectorItem;
/*    */ import com.itextpdf.styledxmlparser.css.selector.item.CssPageTypeSelectorItem;
/*    */ import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CssPageSelectorParser
/*    */ {
/*    */   private static final String PAGE_SELECTOR_PATTERN_STR = "(^-?[_a-zA-Z][\\w-]*)|(:(?i)(left|right|first|blank))";
/* 64 */   private static final Pattern selectorPattern = Pattern.compile("(^-?[_a-zA-Z][\\w-]*)|(:(?i)(left|right|first|blank))");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<ICssSelectorItem> parseSelectorItems(String selectorItemsStr) {
/* 73 */     List<ICssSelectorItem> selectorItems = new ArrayList<>();
/* 74 */     Matcher itemMatcher = selectorPattern.matcher(selectorItemsStr);
/* 75 */     while (itemMatcher.find()) {
/* 76 */       String selectorItem = itemMatcher.group(0);
/* 77 */       if (selectorItem.charAt(0) == ':') {
/* 78 */         selectorItems.add(new CssPagePseudoClassSelectorItem(selectorItem.substring(1).toLowerCase())); continue;
/*    */       } 
/* 80 */       selectorItems.add(new CssPageTypeSelectorItem(selectorItem));
/*    */     } 
/*    */     
/* 83 */     return selectorItems;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/CssPageSelectorParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */