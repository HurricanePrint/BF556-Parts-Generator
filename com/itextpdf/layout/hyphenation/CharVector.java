/*     */ package com.itextpdf.layout.hyphenation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharVector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4263472982169004048L;
/*     */   private static final int DEFAULT_BLOCK_SIZE = 2048;
/*     */   private int blockSize;
/*     */   private char[] array;
/*     */   private int n;
/*     */   
/*     */   public CharVector() {
/*  52 */     this(2048);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharVector(int capacity) {
/*  60 */     if (capacity > 0) {
/*  61 */       this.blockSize = capacity;
/*     */     } else {
/*  63 */       this.blockSize = 2048;
/*     */     } 
/*  65 */     this.array = new char[this.blockSize];
/*  66 */     this.n = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharVector(char[] a) {
/*  74 */     this.blockSize = 2048;
/*  75 */     this.array = a;
/*  76 */     this.n = a.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharVector(char[] a, int capacity) {
/*  85 */     if (capacity > 0) {
/*  86 */       this.blockSize = capacity;
/*     */     } else {
/*  88 */       this.blockSize = 2048;
/*     */     } 
/*  90 */     this.array = a;
/*  91 */     this.n = a.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharVector(CharVector cv) {
/*  99 */     this.array = (char[])cv.array.clone();
/* 100 */     this.blockSize = cv.blockSize;
/* 101 */     this.n = cv.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 108 */     this.n = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getArray() {
/* 116 */     return this.array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 124 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 132 */     return this.array.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(int index, char val) {
/* 141 */     this.array[index] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char get(int index) {
/* 150 */     return this.array[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int alloc(int size) {
/* 159 */     int index = this.n;
/* 160 */     int len = this.array.length;
/* 161 */     if (this.n + size >= len) {
/* 162 */       char[] aux = new char[len + this.blockSize];
/* 163 */       System.arraycopy(this.array, 0, aux, 0, len);
/* 164 */       this.array = aux;
/*     */     } 
/* 166 */     this.n += size;
/* 167 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimToSize() {
/* 174 */     if (this.n < this.array.length) {
/* 175 */       char[] aux = new char[this.n];
/* 176 */       System.arraycopy(this.array, 0, aux, 0, this.n);
/* 177 */       this.array = aux;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/CharVector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */