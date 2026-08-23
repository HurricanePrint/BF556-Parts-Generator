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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlphabetNumbering
/*    */ {
/*    */   public static String toAlphabetNumber(int number, char[] alphabet) {
/* 64 */     if (number < 1) {
/* 65 */       throw new IllegalArgumentException("The parameter must be a positive integer");
/*    */     }
/*    */     
/* 68 */     int cardinality = alphabet.length;
/*    */     
/* 70 */     number--;
/* 71 */     int bytes = 1;
/* 72 */     int start = 0;
/* 73 */     int symbols = cardinality;
/*    */     
/* 75 */     while (number >= symbols + start) {
/* 76 */       bytes++;
/* 77 */       start += symbols;
/* 78 */       symbols *= cardinality;
/*    */     } 
/*    */     
/* 81 */     int c = number - start;
/* 82 */     char[] value = new char[bytes];
/* 83 */     while (bytes > 0) {
/* 84 */       value[--bytes] = alphabet[c % cardinality];
/* 85 */       c /= cardinality;
/*    */     } 
/*    */     
/* 88 */     return new String(value);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/numbering/AlphabetNumbering.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */