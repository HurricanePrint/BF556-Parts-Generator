/*    */ package com.itextpdf.kernel.numbering;
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
/*    */ 
/*    */ 
/*    */ public class EnglishAlphabetNumbering
/*    */ {
/* 57 */   protected static final char[] ALPHABET_LOWERCASE = new char[26];
/* 58 */   protected static final char[] ALPHABET_UPPERCASE = new char[26]; static {
/* 59 */     for (int i = 0; i < 26; i++) {
/* 60 */       ALPHABET_LOWERCASE[i] = (char)(97 + i);
/* 61 */       ALPHABET_UPPERCASE[i] = (char)(65 + i);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static final int ALPHABET_LENGTH = 26;
/*    */ 
/*    */ 
/*    */   
/*    */   public static String toLatinAlphabetNumberLowerCase(int number) {
/* 73 */     return AlphabetNumbering.toAlphabetNumber(number, ALPHABET_LOWERCASE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String toLatinAlphabetNumberUpperCase(int number) {
/* 84 */     return AlphabetNumbering.toAlphabetNumber(number, ALPHABET_UPPERCASE);
/*    */   }
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
/*    */   public static String toLatinAlphabetNumber(int number, boolean upperCase) {
/* 97 */     return upperCase ? toLatinAlphabetNumberUpperCase(number) : toLatinAlphabetNumberLowerCase(number);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/numbering/EnglishAlphabetNumbering.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */