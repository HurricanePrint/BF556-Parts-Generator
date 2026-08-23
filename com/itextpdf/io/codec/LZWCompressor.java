/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LZWCompressor
/*     */ {
/*     */   int codeSize_;
/*     */   int clearCode_;
/*     */   int endOfInfo_;
/*     */   int numBits_;
/*     */   int limit_;
/*     */   short prefix_;
/*     */   BitFile bf_;
/*     */   LZWStringTable lzss_;
/*     */   boolean tiffFudge_;
/*     */   
/*     */   public LZWCompressor(OutputStream outputStream, int codeSize, boolean TIFF) throws IOException {
/* 109 */     this.bf_ = new BitFile(outputStream, !TIFF);
/* 110 */     this.codeSize_ = codeSize;
/* 111 */     this.tiffFudge_ = TIFF;
/* 112 */     this.clearCode_ = 1 << this.codeSize_;
/* 113 */     this.endOfInfo_ = this.clearCode_ + 1;
/* 114 */     this.numBits_ = this.codeSize_ + 1;
/*     */     
/* 116 */     this.limit_ = (1 << this.numBits_) - 1;
/* 117 */     if (this.tiffFudge_) {
/* 118 */       this.limit_--;
/*     */     }
/*     */     
/* 121 */     this.prefix_ = -1;
/* 122 */     this.lzss_ = new LZWStringTable();
/* 123 */     this.lzss_.ClearTable(this.codeSize_);
/* 124 */     this.bf_.writeBits(this.clearCode_, this.numBits_);
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
/*     */   public void compress(byte[] buf, int offset, int length) throws IOException {
/* 139 */     int maxOffset = offset + length;
/* 140 */     for (int idx = offset; idx < maxOffset; idx++) {
/* 141 */       byte c = buf[idx]; short index;
/* 142 */       if ((index = this.lzss_.FindCharString(this.prefix_, c)) != -1) {
/* 143 */         this.prefix_ = index;
/*     */       } else {
/* 145 */         this.bf_.writeBits(this.prefix_, this.numBits_);
/* 146 */         if (this.lzss_.AddCharString(this.prefix_, c) > this.limit_) {
/* 147 */           if (this.numBits_ == 12) {
/* 148 */             this.bf_.writeBits(this.clearCode_, this.numBits_);
/* 149 */             this.lzss_.ClearTable(this.codeSize_);
/* 150 */             this.numBits_ = this.codeSize_ + 1;
/*     */           } else {
/* 152 */             this.numBits_++;
/*     */           } 
/* 154 */           this.limit_ = (1 << this.numBits_) - 1;
/* 155 */           if (this.tiffFudge_)
/* 156 */             this.limit_--; 
/*     */         } 
/* 158 */         this.prefix_ = (short)((short)c & 0xFF);
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
/*     */   public void flush() throws IOException {
/* 170 */     if (this.prefix_ != -1) {
/* 171 */       this.bf_.writeBits(this.prefix_, this.numBits_);
/*     */     }
/* 173 */     this.bf_.writeBits(this.endOfInfo_, this.numBits_);
/* 174 */     this.bf_.flush();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/LZWCompressor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */