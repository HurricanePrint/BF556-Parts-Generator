/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomAccessFileOrArray
/*     */   implements DataInput, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -169314546265954851L;
/*     */   public static boolean plainRandomAccess = false;
/*     */   private IRandomAccessSource byteSource;
/*     */   private long byteSourcePosition;
/*     */   private byte back;
/*     */   private boolean isBack = false;
/*     */   
/*     */   public RandomAccessFileOrArray(IRandomAccessSource byteSource) {
/*  91 */     this.byteSource = byteSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomAccessFileOrArray createView() {
/* 101 */     ensureByteSourceIsThreadSafe();
/* 102 */     return new RandomAccessFileOrArray(new IndependentRandomAccessSource(this.byteSource));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRandomAccessSource createSourceView() {
/* 112 */     ensureByteSourceIsThreadSafe();
/* 113 */     return new IndependentRandomAccessSource(this.byteSource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushBack(byte b) {
/* 122 */     this.back = b;
/* 123 */     this.isBack = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 133 */     if (this.isBack) {
/* 134 */       this.isBack = false;
/* 135 */       return this.back & 0xFF;
/*     */     } 
/*     */     
/* 138 */     return this.byteSource.get(this.byteSourcePosition++);
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
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 151 */     if (len == 0)
/* 152 */       return 0; 
/* 153 */     int count = 0;
/* 154 */     if (this.isBack && len > 0) {
/* 155 */       this.isBack = false;
/* 156 */       b[off++] = this.back;
/* 157 */       len--;
/* 158 */       count++;
/*     */     } 
/* 160 */     if (len > 0) {
/* 161 */       int byteSourceCount = this.byteSource.get(this.byteSourcePosition, b, off, len);
/* 162 */       if (byteSourceCount > 0) {
/* 163 */         count += byteSourceCount;
/* 164 */         this.byteSourcePosition += byteSourceCount;
/*     */       } 
/*     */     } 
/* 167 */     if (count == 0)
/* 168 */       return -1; 
/* 169 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 180 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFully(byte[] b) throws IOException {
/* 187 */     readFully(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFully(byte[] b, int off, int len) throws IOException {
/* 194 */     int n = 0;
/*     */     do {
/* 196 */       int count = read(b, off + n, len - n);
/* 197 */       if (count < 0)
/* 198 */         throw new EOFException(); 
/* 199 */       n += count;
/* 200 */     } while (n < len);
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
/*     */   public long skip(long n) throws IOException {
/* 212 */     if (n <= 0L) {
/* 213 */       return 0L;
/*     */     }
/* 215 */     int adj = 0;
/* 216 */     if (this.isBack) {
/* 217 */       this.isBack = false;
/* 218 */       if (n == 1L) {
/* 219 */         return 1L;
/*     */       }
/* 221 */       n--;
/* 222 */       adj = 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     long pos = getPosition();
/* 230 */     long len = length();
/* 231 */     long newpos = pos + n;
/* 232 */     if (newpos > len) {
/* 233 */       newpos = len;
/*     */     }
/* 235 */     seek(newpos);
/*     */     
/* 237 */     return newpos - pos + adj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int skipBytes(int n) throws IOException {
/* 244 */     return (int)skip(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 253 */     this.isBack = false;
/*     */     
/* 255 */     this.byteSource.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() throws IOException {
/* 265 */     return this.byteSource.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void seek(long pos) throws IOException {
/* 275 */     this.byteSourcePosition = pos;
/* 276 */     this.isBack = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getPosition() throws IOException {
/* 287 */     return this.byteSourcePosition - (this.isBack ? 1L : 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean readBoolean() throws IOException {
/* 294 */     int ch = read();
/* 295 */     if (ch < 0)
/* 296 */       throw new EOFException(); 
/* 297 */     return (ch != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte readByte() throws IOException {
/* 304 */     int ch = read();
/* 305 */     if (ch < 0)
/* 306 */       throw new EOFException(); 
/* 307 */     return (byte)ch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readUnsignedByte() throws IOException {
/* 314 */     int ch = read();
/* 315 */     if (ch < 0)
/* 316 */       throw new EOFException(); 
/* 317 */     return ch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short readShort() throws IOException {
/* 324 */     int ch1 = read();
/* 325 */     int ch2 = read();
/* 326 */     if ((ch1 | ch2) < 0)
/* 327 */       throw new EOFException(); 
/* 328 */     return (short)((ch1 << 8) + ch2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final short readShortLE() throws IOException {
/* 353 */     int ch1 = read();
/* 354 */     int ch2 = read();
/* 355 */     if ((ch1 | ch2) < 0)
/* 356 */       throw new EOFException(); 
/* 357 */     return (short)((ch2 << 8) + ch1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() throws IOException {
/* 364 */     int ch1 = read();
/* 365 */     int ch2 = read();
/* 366 */     if ((ch1 | ch2) < 0)
/* 367 */       throw new EOFException(); 
/* 368 */     return (ch1 << 8) + ch2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int readUnsignedShortLE() throws IOException {
/* 392 */     int ch1 = read();
/* 393 */     int ch2 = read();
/* 394 */     if ((ch1 | ch2) < 0)
/* 395 */       throw new EOFException(); 
/* 396 */     return (ch2 << 8) + ch1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char readChar() throws IOException {
/* 403 */     int ch1 = read();
/* 404 */     int ch2 = read();
/* 405 */     if ((ch1 | ch2) < 0)
/* 406 */       throw new EOFException(); 
/* 407 */     return (char)((ch1 << 8) + ch2);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public final char readCharLE() throws IOException {
/* 430 */     int ch1 = read();
/* 431 */     int ch2 = read();
/* 432 */     if ((ch1 | ch2) < 0)
/* 433 */       throw new EOFException(); 
/* 434 */     return (char)((ch2 << 8) + ch2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readInt() throws IOException {
/* 441 */     int ch1 = read();
/* 442 */     int ch2 = read();
/* 443 */     int ch3 = read();
/* 444 */     int ch4 = read();
/* 445 */     if ((ch1 | ch2 | ch3 | ch4) < 0)
/* 446 */       throw new EOFException(); 
/* 447 */     return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public final int readIntLE() throws IOException {
/* 470 */     int ch1 = read();
/* 471 */     int ch2 = read();
/* 472 */     int ch3 = read();
/* 473 */     int ch4 = read();
/* 474 */     if ((ch1 | ch2 | ch3 | ch4) < 0)
/* 475 */       throw new EOFException(); 
/* 476 */     return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
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
/*     */ 
/*     */   
/*     */   public final long readUnsignedInt() throws IOException {
/* 498 */     long ch1 = read();
/* 499 */     long ch2 = read();
/* 500 */     long ch3 = read();
/* 501 */     long ch4 = read();
/* 502 */     if ((ch1 | ch2 | ch3 | ch4) < 0L)
/* 503 */       throw new EOFException(); 
/* 504 */     return (ch1 << 24L) + (ch2 << 16L) + (ch3 << 8L) + ch4;
/*     */   }
/*     */   
/*     */   public final long readUnsignedIntLE() throws IOException {
/* 508 */     long ch1 = read();
/* 509 */     long ch2 = read();
/* 510 */     long ch3 = read();
/* 511 */     long ch4 = read();
/* 512 */     if ((ch1 | ch2 | ch3 | ch4) < 0L)
/* 513 */       throw new EOFException(); 
/* 514 */     return (ch4 << 24L) + (ch3 << 16L) + (ch2 << 8L) + ch1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long readLong() throws IOException {
/* 521 */     return (readInt() << 32L) + (readInt() & 0xFFFFFFFFL);
/*     */   }
/*     */   
/*     */   public final long readLongLE() throws IOException {
/* 525 */     int i1 = readIntLE();
/* 526 */     int i2 = readIntLE();
/* 527 */     return (i2 << 32L) + (i1 & 0xFFFFFFFFL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float readFloat() throws IOException {
/* 534 */     return Float.intBitsToFloat(readInt());
/*     */   }
/*     */   
/*     */   public final float readFloatLE() throws IOException {
/* 538 */     return Float.intBitsToFloat(readIntLE());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double readDouble() throws IOException {
/* 545 */     return Double.longBitsToDouble(readLong());
/*     */   }
/*     */   
/*     */   public final double readDoubleLE() throws IOException {
/* 549 */     return Double.longBitsToDouble(readLongLE());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String readLine() throws IOException {
/* 556 */     StringBuilder input = new StringBuilder();
/* 557 */     int c = -1;
/* 558 */     boolean eol = false;
/*     */     
/* 560 */     while (!eol) {
/* 561 */       long cur; switch (c = read()) {
/*     */         case -1:
/*     */         case 10:
/* 564 */           eol = true;
/*     */           continue;
/*     */         case 13:
/* 567 */           eol = true;
/* 568 */           cur = getPosition();
/* 569 */           if (read() != 10) {
/* 570 */             seek(cur);
/*     */           }
/*     */           continue;
/*     */       } 
/* 574 */       input.append((char)c);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 579 */     if (c == -1 && input.length() == 0) {
/* 580 */       return null;
/*     */     }
/* 582 */     return input.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String readUTF() throws IOException {
/* 589 */     return DataInputStream.readUTF(this);
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
/*     */   public String readString(int length, String encoding) throws IOException {
/* 601 */     byte[] buf = new byte[length];
/* 602 */     readFully(buf);
/* 603 */     return new String(buf, encoding);
/*     */   }
/*     */   
/*     */   private void ensureByteSourceIsThreadSafe() {
/* 607 */     if (!(this.byteSource instanceof ThreadSafeRandomAccessSource))
/* 608 */       this.byteSource = new ThreadSafeRandomAccessSource(this.byteSource); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/RandomAccessFileOrArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */