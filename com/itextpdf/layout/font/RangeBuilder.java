/*     */ package com.itextpdf.layout.font;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RangeBuilder
/*     */ {
/*  53 */   private static final Range fullRangeSingleton = new Range.FullRange();
/*     */   
/*  55 */   private List<Range.SubRange> ranges = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Range getFullRange() {
/*  63 */     return fullRangeSingleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder(int low, int high) {
/*  80 */     addRange(low, high);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder(int n) {
/*  89 */     this(n, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder(char low, char high) {
/*  99 */     this(low, high);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder(char ch) {
/* 108 */     this(ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder addRange(int low, int high) {
/* 119 */     if (high < low) {
/* 120 */       throw new IllegalArgumentException("'from' shall be less than 'to'");
/*     */     }
/* 122 */     this.ranges.add(new Range.SubRange(low, high));
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder addRange(char low, char high) {
/* 134 */     return addRange(low, high);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder addRange(int n) {
/* 144 */     return addRange(n, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeBuilder addRange(char ch) {
/* 154 */     return addRange(ch);
/*     */   }
/*     */ 
/*     */   
/*     */   public Range create() {
/* 159 */     return new Range(this.ranges);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/RangeBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */