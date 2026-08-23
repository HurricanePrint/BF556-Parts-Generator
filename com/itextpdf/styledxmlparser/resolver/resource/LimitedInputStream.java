/*     */ package com.itextpdf.styledxmlparser.resolver.resource;
/*     */ 
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
/*     */ class LimitedInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private boolean isStreamRead;
/*     */   private boolean isLimitViolated;
/*     */   private long readingByteLimit;
/*     */   private InputStream inputStream;
/*     */   
/*     */   public LimitedInputStream(InputStream inputStream, long readingByteLimit) {
/*  51 */     if (readingByteLimit < 0L) {
/*  52 */       throw new IllegalArgumentException("The reading byte limit argument must not be less than zero.");
/*     */     }
/*  54 */     this.isStreamRead = false;
/*  55 */     this.isLimitViolated = false;
/*  56 */     this.inputStream = inputStream;
/*  57 */     this.readingByteLimit = readingByteLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*  62 */     if (this.isStreamRead) {
/*  63 */       return -1;
/*     */     }
/*  65 */     if (this.isLimitViolated) {
/*  66 */       throw new ReadingByteLimitException();
/*     */     }
/*     */     
/*  69 */     int nextByte = this.inputStream.read();
/*  70 */     this.readingByteLimit--;
/*     */     
/*  72 */     checkReadingByteLimit(nextByte);
/*  73 */     return nextByte;
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/*     */     int numberOfReadingBytes;
/*  78 */     if (this.isStreamRead) {
/*  79 */       return -1;
/*     */     }
/*  81 */     if (this.isLimitViolated) {
/*  82 */       throw new ReadingByteLimitException();
/*     */     }
/*     */ 
/*     */     
/*  86 */     if (b.length > this.readingByteLimit) {
/*     */       byte[] validArray;
/*  88 */       if (this.readingByteLimit == 0L) {
/*     */         
/*  90 */         validArray = new byte[1];
/*     */       } else {
/*     */         
/*  93 */         validArray = new byte[(int)this.readingByteLimit];
/*     */       } 
/*  95 */       numberOfReadingBytes = this.inputStream.read(validArray);
/*  96 */       if (numberOfReadingBytes != -1) {
/*  97 */         System.arraycopy(validArray, 0, b, 0, numberOfReadingBytes);
/*     */       }
/*     */     } else {
/* 100 */       numberOfReadingBytes = this.inputStream.read(b);
/*     */     } 
/* 102 */     this.readingByteLimit -= numberOfReadingBytes;
/*     */     
/* 104 */     checkReadingByteLimit(numberOfReadingBytes);
/* 105 */     return numberOfReadingBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 110 */     if (this.isStreamRead) {
/* 111 */       return -1;
/*     */     }
/* 113 */     if (this.isLimitViolated) {
/* 114 */       throw new ReadingByteLimitException();
/*     */     }
/*     */     
/* 117 */     if (len > this.readingByteLimit) {
/* 118 */       if (this.readingByteLimit == 0L) {
/*     */         
/* 120 */         len = 1;
/*     */       } else {
/*     */         
/* 123 */         len = (int)this.readingByteLimit;
/*     */       } 
/*     */     }
/* 126 */     int numberOfReadingBytes = this.inputStream.read(b, off, len);
/* 127 */     this.readingByteLimit -= numberOfReadingBytes;
/*     */     
/* 129 */     checkReadingByteLimit(numberOfReadingBytes);
/* 130 */     return numberOfReadingBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 135 */     this.inputStream.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 140 */     return this.inputStream.skip(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 145 */     return this.inputStream.available();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void mark(int readlimit) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void reset() throws IOException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 160 */     return false;
/*     */   }
/*     */   
/*     */   private void checkReadingByteLimit(int byteValue) throws ReadingByteLimitException {
/* 164 */     if (byteValue == -1) {
/* 165 */       this.isStreamRead = true;
/* 166 */     } else if (this.readingByteLimit < 0L) {
/* 167 */       this.isLimitViolated = true;
/* 168 */       throw new ReadingByteLimitException();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/resolver/resource/LimitedInputStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */