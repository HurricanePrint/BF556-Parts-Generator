/*     */ package com.itextpdf.layout.font;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Range
/*     */ {
/*     */   private SubRange[] ranges;
/*     */   
/*     */   private Range() {}
/*     */   
/*     */   Range(List<SubRange> ranges) {
/*  63 */     if (ranges.size() == 0) {
/*  64 */       throw new IllegalArgumentException("Ranges shall not be empty");
/*     */     }
/*  66 */     this.ranges = normalizeSubRanges(ranges);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int n) {
/*  76 */     int low = 0;
/*  77 */     int high = this.ranges.length - 1;
/*     */     
/*  79 */     while (low <= high) {
/*  80 */       int mid = low + high >>> 1;
/*  81 */       if (this.ranges[mid].compareTo(n) < 0) {
/*  82 */         low = mid + 1; continue;
/*  83 */       }  if (this.ranges[mid].compareTo(n) > 0) {
/*  84 */         high = mid - 1; continue;
/*     */       } 
/*  86 */       return true;
/*     */     } 
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  93 */     if (this == o) return true; 
/*  94 */     if (o == null || getClass() != o.getClass()) return false; 
/*  95 */     Range range = (Range)o;
/*  96 */     return Arrays.equals((Object[])this.ranges, (Object[])range.ranges);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Arrays.hashCode((Object[])this.ranges);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 106 */     return Arrays.toString((Object[])this.ranges);
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
/*     */   private static SubRange[] normalizeSubRanges(List<SubRange> ranges) {
/* 118 */     Collections.sort(ranges);
/* 119 */     List<SubRange> union = new ArrayList<>(ranges.size());
/*     */     
/* 121 */     assert ranges.size() > 0;
/* 122 */     SubRange curr = ranges.get(0);
/* 123 */     union.add(curr);
/* 124 */     for (int i = 1; i < ranges.size(); i++) {
/* 125 */       SubRange next = ranges.get(i);
/*     */       
/* 127 */       if (next.low <= curr.high) {
/*     */         
/* 129 */         if (next.high > curr.high) {
/* 130 */           curr.high = next.high;
/*     */         }
/*     */       } else {
/* 133 */         curr = next;
/* 134 */         union.add(curr);
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return union.<SubRange>toArray(new SubRange[0]);
/*     */   }
/*     */   
/*     */   static class SubRange implements Comparable<SubRange> {
/*     */     int low;
/*     */     int high;
/*     */     
/*     */     SubRange(int low, int high) {
/* 146 */       this.low = low;
/* 147 */       this.high = high;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(SubRange o) {
/* 152 */       return this.low - o.low;
/*     */     }
/*     */     
/*     */     public int compareTo(int n) {
/* 156 */       if (n < this.low) return 1; 
/* 157 */       if (n > this.high) return -1; 
/* 158 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 163 */       if (this == o) return true; 
/* 164 */       if (o == null || getClass() != o.getClass()) return false; 
/* 165 */       SubRange subRange = (SubRange)o;
/* 166 */       return (this.low == subRange.low && this.high == subRange.high);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 172 */       return 31 * this.low + this.high;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 177 */       return "(" + this.low + "; " + this.high + ')';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class FullRange
/*     */     extends Range
/*     */   {
/*     */     public boolean contains(int uni) {
/* 188 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 193 */       return (this == o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 198 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 203 */       return "[FullRange]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/Range.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */