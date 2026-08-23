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
/*     */ public class ByteVector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1554572867863466772L;
/*     */   private static final int DEFAULT_BLOCK_SIZE = 2048;
/*     */   private int blockSize;
/*     */   private byte[] array;
/*     */   private int n;
/*     */   
/*     */   public ByteVector() {
/*  52 */     this(2048);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector(int capacity) {
/*  60 */     if (capacity > 0) {
/*  61 */       this.blockSize = capacity;
/*     */     } else {
/*  63 */       this.blockSize = 2048;
/*     */     } 
/*  65 */     this.array = new byte[this.blockSize];
/*  66 */     this.n = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector(byte[] a) {
/*  76 */     this.blockSize = 2048;
/*  77 */     this.array = a;
/*  78 */     this.n = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector(byte[] a, int capacity) {
/*  89 */     if (capacity > 0) {
/*  90 */       this.blockSize = capacity;
/*     */     } else {
/*  92 */       this.blockSize = 2048;
/*     */     } 
/*  94 */     this.array = a;
/*  95 */     this.n = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getArray() {
/* 103 */     return this.array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 111 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 119 */     return this.array.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(int index, byte val) {
/* 128 */     this.array[index] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte get(int index) {
/* 137 */     return this.array[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int alloc(int size) {
/* 146 */     int index = this.n;
/* 147 */     int len = this.array.length;
/* 148 */     if (this.n + size >= len) {
/* 149 */       byte[] aux = new byte[len + this.blockSize];
/* 150 */       System.arraycopy(this.array, 0, aux, 0, len);
/* 151 */       this.array = aux;
/*     */     } 
/* 153 */     this.n += size;
/* 154 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimToSize() {
/* 161 */     if (this.n < this.array.length) {
/* 162 */       byte[] aux = new byte[this.n];
/* 163 */       System.arraycopy(this.array, 0, aux, 0, this.n);
/* 164 */       this.array = aux;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/ByteVector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */