/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteBuffer
/*     */   implements Serializable
/*     */ {
/*  51 */   private static final byte[] bytes = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
/*     */   
/*     */   private static final long serialVersionUID = -4380712536267312975L;
/*     */   
/*     */   protected int count;
/*     */   private byte[] buffer;
/*     */   
/*     */   public ByteBuffer() {
/*  59 */     this(128);
/*     */   }
/*     */   
/*     */   public ByteBuffer(int size) {
/*  63 */     if (size < 1)
/*  64 */       size = 128; 
/*  65 */     this.buffer = new byte[size];
/*     */   }
/*     */   
/*     */   public static int getHex(int v) {
/*  69 */     if (v >= 48 && v <= 57)
/*  70 */       return v - 48; 
/*  71 */     if (v >= 65 && v <= 70)
/*  72 */       return v - 65 + 10; 
/*  73 */     if (v >= 97 && v <= 102)
/*  74 */       return v - 97 + 10; 
/*  75 */     return -1;
/*     */   }
/*     */   
/*     */   public ByteBuffer append(byte b) {
/*  79 */     int newCount = this.count + 1;
/*  80 */     if (newCount > this.buffer.length) {
/*  81 */       byte[] newBuffer = new byte[Math.max(this.buffer.length << 1, newCount)];
/*  82 */       System.arraycopy(this.buffer, 0, newBuffer, 0, this.count);
/*  83 */       this.buffer = newBuffer;
/*     */     } 
/*  85 */     this.buffer[this.count] = b;
/*  86 */     this.count = newCount;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuffer append(byte[] b, int off, int len) {
/*  91 */     if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0 || len == 0)
/*     */     {
/*  93 */       return this; } 
/*  94 */     int newCount = this.count + len;
/*  95 */     if (newCount > this.buffer.length) {
/*  96 */       byte[] newBuffer = new byte[Math.max(this.buffer.length << 1, newCount)];
/*  97 */       System.arraycopy(this.buffer, 0, newBuffer, 0, this.count);
/*  98 */       this.buffer = newBuffer;
/*     */     } 
/* 100 */     System.arraycopy(b, off, this.buffer, this.count, len);
/* 101 */     this.count = newCount;
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuffer append(byte[] b) {
/* 106 */     return append(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public ByteBuffer append(int b) {
/* 110 */     return append((byte)b);
/*     */   }
/*     */   
/*     */   public ByteBuffer append(String str) {
/* 114 */     return append(ByteUtils.getIsoBytes(str));
/*     */   }
/*     */   
/*     */   public ByteBuffer appendHex(byte b) {
/* 118 */     append(bytes[b >> 4 & 0xF]);
/* 119 */     return append(bytes[b & 0xF]);
/*     */   }
/*     */   
/*     */   public byte get(int index) {
/* 123 */     if (index >= this.count) {
/* 124 */       throw new IndexOutOfBoundsException(MessageFormatUtil.format("Index: {0}, Size: {1}", new Object[] { Integer.valueOf(index), Integer.valueOf(this.count) }));
/*     */     }
/* 126 */     return this.buffer[index];
/*     */   }
/*     */   
/*     */   public byte[] getInternalBuffer() {
/* 130 */     return this.buffer;
/*     */   }
/*     */   
/*     */   public int size() {
/* 134 */     return this.count;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 138 */     return (size() == 0);
/*     */   }
/*     */   
/*     */   public int capacity() {
/* 142 */     return this.buffer.length;
/*     */   }
/*     */   
/*     */   public ByteBuffer reset() {
/* 146 */     this.count = 0;
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public byte[] toByteArray(int off, int len) {
/* 151 */     byte[] newBuf = new byte[len];
/* 152 */     System.arraycopy(this.buffer, off, newBuf, 0, len);
/* 153 */     return newBuf;
/*     */   }
/*     */   
/*     */   public byte[] toByteArray() {
/* 157 */     return toByteArray(0, this.count);
/*     */   }
/*     */   
/*     */   public boolean startsWith(byte[] b) {
/* 161 */     if (size() < b.length)
/* 162 */       return false; 
/* 163 */     for (int k = 0; k < b.length; k++) {
/* 164 */       if (this.buffer[k] != b[k])
/* 165 */         return false; 
/*     */     } 
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ByteBuffer prepend(byte b) {
/* 177 */     this.buffer[this.buffer.length - this.count - 1] = b;
/* 178 */     this.count++;
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ByteBuffer prepend(byte[] b) {
/* 189 */     System.arraycopy(b, 0, this.buffer, this.buffer.length - this.count - b.length, b.length);
/* 190 */     this.count += b.length;
/* 191 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/ByteBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */