/*    */ package com.itextpdf.layout.font;
/*    */ 
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
/*    */ @Deprecated
/*    */ public final class FontFamilySplitter
/*    */ {
/* 57 */   private static final Pattern FONT_FAMILY_PATTERN = Pattern.compile("^ *([\\w-]+) *$");
/* 58 */   private static final Pattern FONT_FAMILY_PATTERN_QUOTED = Pattern.compile("^ *(('[\\w -]+')|(\"[\\w -]+\")) *$");
/* 59 */   private static final Pattern FONT_FAMILY_PATTERN_QUOTED_SELECT = Pattern.compile("[\\w-]+( +[\\w-]+)*");
/*    */   
/*    */   public static List<String> splitFontFamily(String fontFamilies) {
/* 62 */     if (fontFamilies == null) {
/* 63 */       return null;
/*    */     }
/* 65 */     String[] names = fontFamilies.split(",");
/* 66 */     List<String> result = new ArrayList<>(names.length);
/* 67 */     for (String name : names) {
/*    */       
/* 69 */       if (FONT_FAMILY_PATTERN.matcher(name).matches()) {
/* 70 */         result.add(name.trim());
/* 71 */       } else if (FONT_FAMILY_PATTERN_QUOTED.matcher(name).matches()) {
/* 72 */         Matcher selectMatcher = FONT_FAMILY_PATTERN_QUOTED_SELECT.matcher(name);
/* 73 */         if (selectMatcher.find()) {
/* 74 */           result.add(selectMatcher.group());
/*    */         }
/*    */       } 
/*    */     } 
/* 78 */     return result;
/*    */   }
/*    */   
/*    */   public static String removeQuotes(String fontFamily) {
/* 82 */     Matcher selectMatcher = FONT_FAMILY_PATTERN_QUOTED_SELECT.matcher(fontFamily);
/* 83 */     if (selectMatcher.find()) {
/* 84 */       return selectMatcher.group();
/*    */     }
/* 86 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontFamilySplitter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */