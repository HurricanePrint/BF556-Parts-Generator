/*    */ package com.itextpdf.layout.hyphenation;
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
/*    */ public class Hyphenation
/*    */ {
/*    */   private int[] hyphenPoints;
/*    */   private String word;
/*    */   private int len;
/*    */   
/*    */   Hyphenation(String word, int[] points) {
/* 40 */     this.word = word;
/* 41 */     this.hyphenPoints = points;
/* 42 */     this.len = points.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int length() {
/* 49 */     return this.len;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPreHyphenText(int index) {
/* 57 */     return this.word.substring(0, this.hyphenPoints[index]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPostHyphenText(int index) {
/* 65 */     return this.word.substring(this.hyphenPoints[index]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getHyphenationPoints() {
/* 72 */     return this.hyphenPoints;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 77 */     StringBuffer str = new StringBuffer();
/* 78 */     int start = 0;
/* 79 */     for (int i = 0; i < this.len; i++) {
/* 80 */       str.append(this.word.substring(start, this.hyphenPoints[i]) + "-");
/* 81 */       start = this.hyphenPoints[i];
/*    */     } 
/* 83 */     str.append(this.word.substring(start));
/* 84 */     return str.toString();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/Hyphenation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */