/*     */ package com.itextpdf.layout.hyphenation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HyphenationConfig
/*     */ {
/*     */   protected Hyphenator hyphenator;
/*  59 */   protected char hyphenSymbol = '-';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HyphenationConfig(int leftMin, int rightMin) {
/*  68 */     this.hyphenator = new Hyphenator(null, null, leftMin, rightMin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HyphenationConfig(Hyphenator hyphenator) {
/*  77 */     this.hyphenator = hyphenator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HyphenationConfig(String lang, String country, int leftMin, int rightMin) {
/*  88 */     this.hyphenator = new Hyphenator(lang, country, leftMin, rightMin);
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
/*     */   public Hyphenation hyphenate(String word) {
/* 100 */     return (this.hyphenator != null) ? this.hyphenator.hyphenate(word) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getHyphenSymbol() {
/* 109 */     return this.hyphenSymbol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHyphenSymbol(char hyphenSymbol) {
/* 118 */     this.hyphenSymbol = hyphenSymbol;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/HyphenationConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */