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
/*     */ public class ArmenianNumbering
/*     */ {
/*  51 */   private static final ArmenianDigit[] DIGITS = new ArmenianDigit[] { new ArmenianDigit('Ա', 1), new ArmenianDigit('Բ', 2), new ArmenianDigit('Գ', 3), new ArmenianDigit('Դ', 4), new ArmenianDigit('Ե', 5), new ArmenianDigit('Զ', 6), new ArmenianDigit('Է', 7), new ArmenianDigit('Ը', 8), new ArmenianDigit('Թ', 9), new ArmenianDigit('Ժ', 10), new ArmenianDigit('Ի', 20), new ArmenianDigit('Լ', 30), new ArmenianDigit('Խ', 40), new ArmenianDigit('Ծ', 50), new ArmenianDigit('Կ', 60), new ArmenianDigit('Հ', 70), new ArmenianDigit('Ձ', 80), new ArmenianDigit('Ղ', 90), new ArmenianDigit('Ճ', 100), new ArmenianDigit('Մ', 200), new ArmenianDigit('Յ', 300), new ArmenianDigit('Ն', 400), new ArmenianDigit('Շ', 500), new ArmenianDigit('Ո', 600), new ArmenianDigit('Չ', 700), new ArmenianDigit('Պ', 800), new ArmenianDigit('Ջ', 900), new ArmenianDigit('Ռ', 1000), new ArmenianDigit('Ս', 2000), new ArmenianDigit('Վ', 3000), new ArmenianDigit('Տ', 4000), new ArmenianDigit('Ր', 5000), new ArmenianDigit('Ց', 6000), new ArmenianDigit('Ւ', 7000), new ArmenianDigit('Փ', 8000), new ArmenianDigit('Ք', 9000) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toArmenian(int number) {
/* 100 */     StringBuilder result = new StringBuilder();
/* 101 */     for (int i = DIGITS.length - 1; i >= 0; i--) {
/* 102 */       ArmenianDigit curDigit = DIGITS[i];
/* 103 */       while (number >= curDigit.value) {
/* 104 */         result.append(curDigit.digit);
/* 105 */         number -= curDigit.value;
/*     */       } 
/*     */     } 
/* 108 */     return result.toString();
/*     */   }
/*     */   
/*     */   private static class ArmenianDigit {
/*     */     char digit;
/*     */     int value;
/*     */     
/*     */     ArmenianDigit(char digit, int value) {
/* 116 */       this.digit = digit;
/* 117 */       this.value = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/numbering/ArmenianNumbering.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */