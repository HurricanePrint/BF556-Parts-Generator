/*     */ package com.itextpdf.kernel.numbering;
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
/*     */ public class RomanNumbering
/*     */ {
/*  56 */   private static final RomanDigit[] ROMAN_DIGITS = new RomanDigit[] { new RomanDigit('m', 1000, false), new RomanDigit('d', 500, false), new RomanDigit('c', 100, true), new RomanDigit('l', 50, false), new RomanDigit('x', 10, true), new RomanDigit('v', 5, false), new RomanDigit('i', 1, true) };
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
/*     */   public static String toRomanLowerCase(int number) {
/*  73 */     return convert(number);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toRomanUpperCase(int number) {
/*  83 */     return convert(number).toUpperCase();
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
/*     */   public static String toRoman(int number, boolean upperCase) {
/*  95 */     return upperCase ? toRomanUpperCase(number) : toRomanLowerCase(number);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String convert(int index) {
/* 105 */     StringBuilder buf = new StringBuilder();
/*     */ 
/*     */     
/* 108 */     if (index < 0) {
/* 109 */       buf.append('-');
/* 110 */       index = -index;
/*     */     } 
/*     */     
/* 113 */     if (index >= 4000) {
/* 114 */       buf.append('|');
/* 115 */       buf.append(convert(index / 1000));
/* 116 */       buf.append('|');
/*     */       
/* 118 */       index -= index / 1000 * 1000;
/*     */     } 
/*     */ 
/*     */     
/* 122 */     int pos = 0;
/*     */     
/*     */     while (true) {
/* 125 */       RomanDigit dig = ROMAN_DIGITS[pos];
/*     */       
/* 127 */       while (index >= dig.value) {
/* 128 */         buf.append(dig.digit);
/* 129 */         index -= dig.value;
/*     */       } 
/*     */       
/* 132 */       if (index <= 0) {
/*     */         break;
/*     */       }
/*     */       
/* 136 */       int j = pos;
/* 137 */       while (!(ROMAN_DIGITS[++j]).pre);
/*     */ 
/*     */       
/* 140 */       if (index + (ROMAN_DIGITS[j]).value >= dig.value) {
/* 141 */         buf.append((ROMAN_DIGITS[j]).digit).append(dig.digit);
/* 142 */         index -= dig.value - (ROMAN_DIGITS[j]).value;
/*     */       } 
/* 144 */       pos++;
/*     */     } 
/* 146 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RomanDigit
/*     */   {
/*     */     public char digit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean pre;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     RomanDigit(char digit, int value, boolean pre) {
/* 177 */       this.digit = digit;
/* 178 */       this.value = value;
/* 179 */       this.pre = pre;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/numbering/RomanNumbering.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */