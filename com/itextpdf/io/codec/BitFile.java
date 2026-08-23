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
/*     */ class BitFile
/*     */ {
/*     */   OutputStream output;
/*     */   byte[] buffer;
/*     */   int index;
/*     */   int bitsLeft;
/*     */   boolean blocks = false;
/*     */   
/*     */   public BitFile(OutputStream output, boolean blocks) {
/*  75 */     this.output = output;
/*  76 */     this.blocks = blocks;
/*  77 */     this.buffer = new byte[256];
/*  78 */     this.index = 0;
/*  79 */     this.bitsLeft = 8;
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/*  83 */     int numBytes = this.index + ((this.bitsLeft == 8) ? 0 : 1);
/*  84 */     if (numBytes > 0) {
/*  85 */       if (this.blocks)
/*  86 */         this.output.write(numBytes); 
/*  87 */       this.output.write(this.buffer, 0, numBytes);
/*  88 */       this.buffer[0] = 0;
/*  89 */       this.index = 0;
/*  90 */       this.bitsLeft = 8;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeBits(int bits, int numbits) throws IOException {
/*  95 */     int bitsWritten = 0;
/*     */     
/*  97 */     int numBytes = 255;
/*     */     
/*     */     do {
/* 100 */       if ((this.index == 254 && this.bitsLeft == 0) || this.index > 254) {
/* 101 */         if (this.blocks) {
/* 102 */           this.output.write(numBytes);
/*     */         }
/* 104 */         this.output.write(this.buffer, 0, numBytes);
/*     */         
/* 106 */         this.buffer[0] = 0;
/* 107 */         this.index = 0;
/* 108 */         this.bitsLeft = 8;
/*     */       } 
/*     */ 
/*     */       
/* 112 */       if (numbits <= this.bitsLeft)
/*     */       {
/*     */         
/* 115 */         if (this.blocks) {
/*     */           
/* 117 */           this.buffer[this.index] = (byte)(this.buffer[this.index] | (byte)((bits & (1 << numbits) - 1) << 8 - this.bitsLeft));
/* 118 */           bitsWritten += numbits;
/* 119 */           this.bitsLeft -= numbits;
/* 120 */           numbits = 0;
/*     */         } else {
/* 122 */           this.buffer[this.index] = (byte)(this.buffer[this.index] | (byte)((bits & (1 << numbits) - 1) << this.bitsLeft - numbits));
/* 123 */           bitsWritten += numbits;
/* 124 */           this.bitsLeft -= numbits;
/* 125 */           numbits = 0;
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 132 */       else if (this.blocks)
/*     */       {
/*     */ 
/*     */         
/* 136 */         this.buffer[this.index] = (byte)(this.buffer[this.index] | (byte)((bits & (1 << this.bitsLeft) - 1) << 8 - this.bitsLeft));
/* 137 */         bitsWritten += this.bitsLeft;
/* 138 */         bits >>= this.bitsLeft;
/* 139 */         numbits -= this.bitsLeft;
/* 140 */         this.buffer[++this.index] = 0;
/* 141 */         this.bitsLeft = 8;
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 146 */         int topbits = bits >>> numbits - this.bitsLeft & (1 << this.bitsLeft) - 1;
/* 147 */         this.buffer[this.index] = (byte)(this.buffer[this.index] | (byte)topbits);
/*     */         
/* 149 */         numbits -= this.bitsLeft;
/* 150 */         bitsWritten += this.bitsLeft;
/*     */         
/* 152 */         this.buffer[++this.index] = 0;
/* 153 */         this.bitsLeft = 8;
/*     */       }
/*     */     
/*     */     }
/* 157 */     while (numbits != 0);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/BitFile.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */