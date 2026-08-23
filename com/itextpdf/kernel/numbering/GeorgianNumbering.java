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
/*     */ public class GeorgianNumbering
/*     */ {
/*  51 */   private static final GeorgianDigit[] DIGITS = new GeorgianDigit[] { new GeorgianDigit('ა', 1), new GeorgianDigit('ბ', 2), new GeorgianDigit('გ', 3), new GeorgianDigit('დ', 4), new GeorgianDigit('ე', 5), new GeorgianDigit('ვ', 6), new GeorgianDigit('ზ', 7), new GeorgianDigit('ჱ', 8), new GeorgianDigit('თ', 9), new GeorgianDigit('ი', 10), new GeorgianDigit('კ', 20), new GeorgianDigit('ლ', 30), new GeorgianDigit('მ', 40), new GeorgianDigit('ნ', 50), new GeorgianDigit('ჲ', 60), new GeorgianDigit('ო', 70), new GeorgianDigit('პ', 80), new GeorgianDigit('ჟ', 90), new GeorgianDigit('რ', 100), new GeorgianDigit('ს', 200), new GeorgianDigit('ტ', 300), new GeorgianDigit('ჳ', 400), new GeorgianDigit('ფ', 500), new GeorgianDigit('ქ', 600), new GeorgianDigit('ღ', 700), new GeorgianDigit('ყ', 800), new GeorgianDigit('შ', 900), new GeorgianDigit('ჩ', 1000), new GeorgianDigit('ც', 2000), new GeorgianDigit('ძ', 3000), new GeorgianDigit('წ', 4000), new GeorgianDigit('ჭ', 5000), new GeorgianDigit('ხ', 6000), new GeorgianDigit('ჴ', 7000), new GeorgianDigit('ჯ', 8000), new GeorgianDigit('ჰ', 9000), new GeorgianDigit('ჵ', 10000) };
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
/*     */   public static String toGeorgian(int number) {
/* 101 */     StringBuilder result = new StringBuilder();
/* 102 */     for (int i = DIGITS.length - 1; i >= 0; i--) {
/* 103 */       GeorgianDigit curDigit = DIGITS[i];
/* 104 */       while (number >= curDigit.value) {
/* 105 */         result.append(curDigit.digit);
/* 106 */         number -= curDigit.value;
/*     */       } 
/*     */     } 
/* 109 */     return result.toString();
/*     */   }
/*     */   
/*     */   private static class GeorgianDigit {
/*     */     char digit;
/*     */     int value;
/*     */     
/*     */     GeorgianDigit(char digit, int value) {
/* 117 */       this.digit = digit;
/* 118 */       this.value = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/numbering/GeorgianNumbering.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */