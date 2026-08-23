/*     */ package com.itextpdf.barcodes.qrcode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ByteArray
/*     */ {
/*     */   private static final int INITIAL_SIZE = 32;
/*     */   private byte[] bytes;
/*     */   private int size;
/*     */   
/*     */   public ByteArray() {
/*  62 */     this.bytes = null;
/*  63 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArray(int size) {
/*  72 */     this.bytes = new byte[size];
/*  73 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArray(byte[] byteArray) {
/*  82 */     this.bytes = byteArray;
/*  83 */     this.size = this.bytes.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int at(int index) {
/*  92 */     return this.bytes[index] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int index, int value) {
/* 101 */     this.bytes[index] = (byte)value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 108 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 115 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendByte(int value) {
/* 123 */     if (this.size == 0 || this.size >= this.bytes.length) {
/* 124 */       int newSize = Math.max(32, this.size << 1);
/* 125 */       reserve(newSize);
/*     */     } 
/* 127 */     this.bytes[this.size] = (byte)value;
/* 128 */     this.size++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reserve(int capacity) {
/* 136 */     if (this.bytes == null || this.bytes.length < capacity) {
/* 137 */       byte[] newArray = new byte[capacity];
/* 138 */       if (this.bytes != null) {
/* 139 */         System.arraycopy(this.bytes, 0, newArray, 0, this.bytes.length);
/*     */       }
/* 141 */       this.bytes = newArray;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(byte[] source, int offset, int count) {
/* 152 */     this.bytes = new byte[count];
/* 153 */     this.size = count;
/* 154 */     for (int x = 0; x < count; x++)
/* 155 */       this.bytes[x] = source[offset + x]; 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/ByteArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */