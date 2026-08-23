/*     */ package com.itextpdf.kernel.xmp.impl;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ {
/*     */   private byte[] buffer;
/*     */   private int length;
/*  50 */   private String encoding = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer(int initialCapacity) {
/*  58 */     this.buffer = new byte[initialCapacity];
/*  59 */     this.length = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer(byte[] buffer) {
/*  68 */     this.buffer = buffer;
/*  69 */     this.length = buffer.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer(byte[] buffer, int length) {
/*  79 */     if (length > buffer.length)
/*     */     {
/*  81 */       throw new ArrayIndexOutOfBoundsException("Valid length exceeds the buffer length.");
/*     */     }
/*  83 */     this.buffer = buffer;
/*  84 */     this.length = length;
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
/*     */   
/*     */   public ByteBuffer(InputStream in) throws IOException {
/*  97 */     int chunk = 16384;
/*  98 */     this.length = 0;
/*  99 */     this.buffer = new byte[chunk];
/*     */     
/*     */     int read;
/* 102 */     while ((read = in.read(this.buffer, this.length, chunk)) > 0) {
/*     */       
/* 104 */       this.length += read;
/* 105 */       if (read == chunk)
/*     */       {
/* 107 */         ensureCapacity(this.length + chunk);
/*     */       }
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer(byte[] buffer, int offset, int length) {
/* 124 */     if (length > buffer.length - offset)
/*     */     {
/* 126 */       throw new ArrayIndexOutOfBoundsException("Valid length exceeds the buffer length.");
/*     */     }
/* 128 */     this.buffer = new byte[length];
/* 129 */     System.arraycopy(buffer, offset, this.buffer, 0, length);
/* 130 */     this.length = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getByteStream() {
/* 139 */     return new ByteArrayInputStream(this.buffer, 0, this.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 149 */     return this.length;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte byteAt(int index) {
/* 169 */     if (index < this.length)
/*     */     {
/* 171 */       return this.buffer[index];
/*     */     }
/*     */ 
/*     */     
/* 175 */     throw new IndexOutOfBoundsException("The index exceeds the valid buffer area");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int charAt(int index) {
/* 186 */     if (index < this.length)
/*     */     {
/* 188 */       return this.buffer[index] & 0xFF;
/*     */     }
/*     */ 
/*     */     
/* 192 */     throw new IndexOutOfBoundsException("The index exceeds the valid buffer area");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(byte b) {
/* 203 */     ensureCapacity(this.length + 1);
/* 204 */     this.buffer[this.length++] = b;
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
/*     */ 
/*     */   
/*     */   public void append(byte[] bytes, int offset, int len) {
/* 218 */     ensureCapacity(this.length + len);
/* 219 */     System.arraycopy(bytes, offset, this.buffer, this.length, len);
/* 220 */     this.length += len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(byte[] bytes) {
/* 230 */     append(bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(ByteBuffer anotherBuffer) {
/* 240 */     append(anotherBuffer.buffer, 0, anotherBuffer.length);
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
/*     */   
/*     */   public String getEncoding() {
/* 253 */     if (this.encoding == null)
/*     */     {
/*     */       
/* 256 */       if (this.length < 2) {
/*     */ 
/*     */         
/* 259 */         this.encoding = "UTF-8";
/*     */       }
/* 261 */       else if (this.buffer[0] == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 268 */         if (this.length < 4 || this.buffer[1] != 0)
/*     */         {
/* 270 */           this.encoding = "UTF-16BE";
/*     */         }
/* 272 */         else if ((this.buffer[2] & 0xFF) == 254 && (this.buffer[3] & 0xFF) == 255)
/*     */         {
/* 274 */           this.encoding = "UTF-32BE";
/*     */         }
/*     */         else
/*     */         {
/* 278 */           this.encoding = "UTF-32";
/*     */         }
/*     */       
/* 281 */       } else if ((this.buffer[0] & 0xFF) < 128) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 287 */         if (this.buffer[1] != 0)
/*     */         {
/* 289 */           this.encoding = "UTF-8";
/*     */         }
/* 291 */         else if (this.length < 4 || this.buffer[2] != 0)
/*     */         {
/* 293 */           this.encoding = "UTF-16LE";
/*     */         }
/*     */         else
/*     */         {
/* 297 */           this.encoding = "UTF-32LE";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 308 */       else if ((this.buffer[0] & 0xFF) == 239) {
/*     */         
/* 310 */         this.encoding = "UTF-8";
/*     */       }
/* 312 */       else if ((this.buffer[0] & 0xFF) == 254) {
/*     */         
/* 314 */         this.encoding = "UTF-16";
/*     */       }
/* 316 */       else if (this.length < 4 || this.buffer[2] != 0) {
/*     */         
/* 318 */         this.encoding = "UTF-16";
/*     */       }
/*     */       else {
/*     */         
/* 322 */         this.encoding = "UTF-32";
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 327 */     return this.encoding;
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
/*     */   private void ensureCapacity(int requestedLength) {
/* 339 */     if (requestedLength > this.buffer.length) {
/*     */       
/* 341 */       byte[] oldBuf = this.buffer;
/* 342 */       this.buffer = new byte[oldBuf.length * 2];
/* 343 */       System.arraycopy(oldBuf, 0, this.buffer, 0, oldBuf.length);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/impl/ByteBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */