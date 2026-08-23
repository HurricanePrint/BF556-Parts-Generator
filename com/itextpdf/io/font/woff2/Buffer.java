/*     */ package com.itextpdf.io.font.woff2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Buffer
/*     */ {
/*     */   private byte[] data;
/*     */   private int offset;
/*     */   private int initial_offset;
/*     */   private int length;
/*     */   
/*     */   public Buffer(byte[] data, int data_offset, int length) {
/*  39 */     this.offset = 0;
/*  40 */     this.initial_offset = data_offset;
/*  41 */     this.length = length;
/*  42 */     this.data = data;
/*     */   }
/*     */   
/*     */   public Buffer(Buffer other) {
/*  46 */     this.offset = other.offset;
/*  47 */     this.initial_offset = other.initial_offset;
/*  48 */     this.length = other.length;
/*  49 */     this.data = other.data;
/*     */   }
/*     */   
/*     */   public int readInt() {
/*  53 */     return readAsNumber(4);
/*     */   }
/*     */   
/*     */   public short readShort() {
/*  57 */     return JavaUnsignedUtil.toU16(readAsNumber(2));
/*     */   }
/*     */   
/*     */   public byte readByte() {
/*  61 */     return JavaUnsignedUtil.toU8(readAsNumber(1));
/*     */   }
/*     */   
/*     */   public void skip(int n_bytes) {
/*  65 */     read(null, 0, n_bytes);
/*     */   }
/*     */   
/*     */   public void read(byte[] data, int data_offset, int n_bytes) {
/*  69 */     if (this.offset + n_bytes > this.length || this.offset > this.length - n_bytes) {
/*  70 */       throw new FontCompressionException("Reading woff2 exception");
/*     */     }
/*  72 */     if (data != null) {
/*  73 */       if (data_offset + n_bytes > data.length || data_offset > data.length - n_bytes) {
/*  74 */         throw new FontCompressionException("Reading woff2 exception");
/*     */       }
/*  76 */       System.arraycopy(this.data, this.initial_offset + this.offset, data, data_offset, n_bytes);
/*     */     } 
/*  78 */     this.offset += n_bytes;
/*     */   }
/*     */   
/*     */   public int getOffset() {
/*  82 */     return this.offset;
/*     */   }
/*     */   
/*     */   public int getInitialOffset() {
/*  86 */     return this.initial_offset;
/*     */   }
/*     */   
/*     */   public int getLength() {
/*  90 */     return this.length;
/*     */   }
/*     */   
/*     */   private int readAsNumber(int n_bytes) {
/*  94 */     byte[] buffer = new byte[n_bytes];
/*  95 */     read(buffer, 0, n_bytes);
/*  96 */     int result = 0;
/*  97 */     for (int i = 0; i < n_bytes; i++) {
/*  98 */       result = result << 8 | JavaUnsignedUtil.asU8(buffer[i]);
/*     */     }
/* 100 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/Buffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */