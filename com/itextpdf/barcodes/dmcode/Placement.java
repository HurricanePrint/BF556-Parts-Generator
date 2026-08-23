/*     */ package com.itextpdf.barcodes.dmcode;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Placement
/*     */ {
/*     */   private final int nrow;
/*     */   private final int ncol;
/*     */   private final short[] array;
/*  54 */   private static final Map<Integer, short[]> cache = (Map)new ConcurrentHashMap<>();
/*     */   
/*     */   private Placement(int nrow, int ncol) {
/*  57 */     this.nrow = nrow;
/*  58 */     this.ncol = ncol;
/*  59 */     this.array = new short[nrow * ncol];
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
/*     */   public static short[] doPlacement(int nrow, int ncol) {
/*  71 */     int key = nrow * 1000 + ncol;
/*  72 */     short[] pc = cache.get(Integer.valueOf(key));
/*  73 */     if (pc != null)
/*  74 */       return pc; 
/*  75 */     Placement p = new Placement(nrow, ncol);
/*  76 */     p.ecc200();
/*  77 */     cache.put(Integer.valueOf(key), p.array);
/*  78 */     return p.array;
/*     */   }
/*     */ 
/*     */   
/*     */   private void module(int row, int col, int chr, int bit) {
/*  83 */     if (row < 0) {
/*  84 */       row += this.nrow;
/*  85 */       col += 4 - (this.nrow + 4) % 8;
/*     */     } 
/*  87 */     if (col < 0) {
/*  88 */       col += this.ncol;
/*  89 */       row += 4 - (this.ncol + 4) % 8;
/*     */     } 
/*  91 */     this.array[row * this.ncol + col] = (short)(8 * chr + bit);
/*     */   }
/*     */ 
/*     */   
/*     */   private void utah(int row, int col, int chr) {
/*  96 */     module(row - 2, col - 2, chr, 0);
/*  97 */     module(row - 2, col - 1, chr, 1);
/*  98 */     module(row - 1, col - 2, chr, 2);
/*  99 */     module(row - 1, col - 1, chr, 3);
/* 100 */     module(row - 1, col, chr, 4);
/* 101 */     module(row, col - 2, chr, 5);
/* 102 */     module(row, col - 1, chr, 6);
/* 103 */     module(row, col, chr, 7);
/*     */   }
/*     */ 
/*     */   
/*     */   private void corner1(int chr) {
/* 108 */     module(this.nrow - 1, 0, chr, 0);
/* 109 */     module(this.nrow - 1, 1, chr, 1);
/* 110 */     module(this.nrow - 1, 2, chr, 2);
/* 111 */     module(0, this.ncol - 2, chr, 3);
/* 112 */     module(0, this.ncol - 1, chr, 4);
/* 113 */     module(1, this.ncol - 1, chr, 5);
/* 114 */     module(2, this.ncol - 1, chr, 6);
/* 115 */     module(3, this.ncol - 1, chr, 7);
/*     */   }
/*     */   
/*     */   private void corner2(int chr) {
/* 119 */     module(this.nrow - 3, 0, chr, 0);
/* 120 */     module(this.nrow - 2, 0, chr, 1);
/* 121 */     module(this.nrow - 1, 0, chr, 2);
/* 122 */     module(0, this.ncol - 4, chr, 3);
/* 123 */     module(0, this.ncol - 3, chr, 4);
/* 124 */     module(0, this.ncol - 2, chr, 5);
/* 125 */     module(0, this.ncol - 1, chr, 6);
/* 126 */     module(1, this.ncol - 1, chr, 7);
/*     */   }
/*     */   
/*     */   private void corner3(int chr) {
/* 130 */     module(this.nrow - 3, 0, chr, 0);
/* 131 */     module(this.nrow - 2, 0, chr, 1);
/* 132 */     module(this.nrow - 1, 0, chr, 2);
/* 133 */     module(0, this.ncol - 2, chr, 3);
/* 134 */     module(0, this.ncol - 1, chr, 4);
/* 135 */     module(1, this.ncol - 1, chr, 5);
/* 136 */     module(2, this.ncol - 1, chr, 6);
/* 137 */     module(3, this.ncol - 1, chr, 7);
/*     */   }
/*     */   
/*     */   private void corner4(int chr) {
/* 141 */     module(this.nrow - 1, 0, chr, 0);
/* 142 */     module(this.nrow - 1, this.ncol - 1, chr, 1);
/* 143 */     module(0, this.ncol - 3, chr, 2);
/* 144 */     module(0, this.ncol - 2, chr, 3);
/* 145 */     module(0, this.ncol - 1, chr, 4);
/* 146 */     module(1, this.ncol - 3, chr, 5);
/* 147 */     module(1, this.ncol - 2, chr, 6);
/* 148 */     module(1, this.ncol - 1, chr, 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ecc200() {
/* 155 */     Arrays.fill(this.array, (short)0);
/*     */     
/* 157 */     int chr = 1;
/* 158 */     int row = 4;
/* 159 */     int col = 0;
/*     */     
/*     */     do {
/* 162 */       if (row == this.nrow && col == 0) corner1(chr++); 
/* 163 */       if (row == this.nrow - 2 && col == 0 && this.ncol % 4 != 0) corner2(chr++); 
/* 164 */       if (row == this.nrow - 2 && col == 0 && this.ncol % 8 == 4) corner3(chr++); 
/* 165 */       if (row == this.nrow + 4 && col == 2 && this.ncol % 8 == 0) corner4(chr++);
/*     */       
/*     */       do {
/* 168 */         if (row < this.nrow && col >= 0 && this.array[row * this.ncol + col] == 0)
/* 169 */           utah(row, col, chr++); 
/* 170 */         row -= 2;
/* 171 */         col += 2;
/* 172 */       } while (row >= 0 && col < this.ncol);
/* 173 */       row++;
/* 174 */       col += 3;
/*     */ 
/*     */       
/*     */       do {
/* 178 */         if (row >= 0 && col < this.ncol && this.array[row * this.ncol + col] == 0)
/* 179 */           utah(row, col, chr++); 
/* 180 */         row += 2;
/* 181 */         col -= 2;
/* 182 */       } while (row < this.nrow && col >= 0);
/* 183 */       row += 3;
/* 184 */       col++;
/*     */     }
/* 186 */     while (row < this.nrow || col < this.ncol);
/*     */     
/* 188 */     if (this.array[this.nrow * this.ncol - 1] == 0) {
/* 189 */       this.array[this.nrow * this.ncol - this.ncol - 2] = 1; this.array[this.nrow * this.ncol - 1] = 1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/dmcode/Placement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */