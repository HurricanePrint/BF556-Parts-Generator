/*     */ package com.itextpdf.styledxmlparser.css.parse;
/*     */ 
/*     */ import java.util.regex.Matcher;
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
/*     */ 
/*     */ class CssSelectorParserMatch
/*     */ {
/*     */   private boolean success;
/*     */   private Matcher matcher;
/*     */   private String source;
/*     */   
/*     */   public CssSelectorParserMatch(String source, Pattern pattern) {
/*  64 */     this.source = source;
/*  65 */     this.matcher = pattern.matcher(source);
/*  66 */     next();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  73 */     return this.matcher.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  80 */     return this.matcher.group(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSource() {
/*  87 */     return this.source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean success() {
/*  94 */     return this.success;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() {
/* 101 */     this.success = this.matcher.find();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next(int startIndex) {
/* 110 */     this.success = this.matcher.find(startIndex);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/CssSelectorParserMatch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */