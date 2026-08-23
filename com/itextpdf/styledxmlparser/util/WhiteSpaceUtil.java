/*    */ package com.itextpdf.styledxmlparser.util;
/*    */ 
/*    */ import java.util.HashSet;
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
/*    */ public class WhiteSpaceUtil
/*    */ {
/* 53 */   private static final Set<Character> EM_SPACES = new HashSet<>();
/*    */   
/*    */   static {
/* 56 */     EM_SPACES.add(Character.valueOf(' '));
/* 57 */     EM_SPACES.add(Character.valueOf(' '));
/* 58 */     EM_SPACES.add(Character.valueOf(' '));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String collapseConsecutiveSpaces(String s) {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     for (int i = 0; i < s.length(); i++) {
/* 69 */       if (isNonEmSpace(s.charAt(i))) {
/* 70 */         if (sb.length() == 0 || !isNonEmSpace(sb.charAt(sb.length() - 1))) {
/* 71 */           sb.append(" ");
/*    */         }
/*    */       } else {
/* 74 */         sb.append(s.charAt(i));
/*    */       } 
/*    */     } 
/* 77 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isNonEmSpace(char ch) {
/* 87 */     return (Character.isWhitespace(ch) && !EM_SPACES.contains(Character.valueOf(ch)));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/util/WhiteSpaceUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */