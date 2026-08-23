/*     */ package com.itextpdf.kernel.pdf.filters;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
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
/*     */ public class LZWDecoder
/*     */ {
/*     */   byte[][] stringTable;
/*     */   OutputStream uncompData;
/*  57 */   byte[] data = null;
/*     */   int tableIndex;
/*  59 */   int bitsToGet = 9;
/*     */   int bytePointer;
/*  61 */   int nextData = 0; int bitPointer;
/*  62 */   int nextBits = 0;
/*     */   
/*  64 */   int[] andTable = new int[] { 511, 1023, 2047, 4095 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(byte[] data, OutputStream uncompData) {
/*  86 */     if (data[0] == 0 && data[1] == 1) {
/*  87 */       throw new PdfException("LZW flavour not supported.");
/*     */     }
/*     */     
/*  90 */     initializeStringTable();
/*     */     
/*  92 */     this.data = data;
/*  93 */     this.uncompData = uncompData;
/*     */ 
/*     */     
/*  96 */     this.bytePointer = 0;
/*  97 */     this.bitPointer = 0;
/*     */     
/*  99 */     this.nextData = 0;
/* 100 */     this.nextBits = 0;
/*     */     
/* 102 */     int oldCode = 0;
/*     */     
/*     */     int code;
/* 105 */     while ((code = getNextCode()) != 257) {
/*     */       
/* 107 */       if (code == 256) {
/*     */         
/* 109 */         initializeStringTable();
/* 110 */         code = getNextCode();
/*     */         
/* 112 */         if (code == 257) {
/*     */           break;
/*     */         }
/*     */         
/* 116 */         writeString(this.stringTable[code]);
/* 117 */         oldCode = code;
/*     */         
/*     */         continue;
/*     */       } 
/* 121 */       if (code < this.tableIndex) {
/*     */         
/* 123 */         byte[] arrayOfByte = this.stringTable[code];
/*     */         
/* 125 */         writeString(arrayOfByte);
/* 126 */         addStringToTable(this.stringTable[oldCode], arrayOfByte[0]);
/* 127 */         oldCode = code;
/*     */         
/*     */         continue;
/*     */       } 
/* 131 */       byte[] string = this.stringTable[oldCode];
/* 132 */       string = composeString(string, string[0]);
/* 133 */       writeString(string);
/* 134 */       addStringToTable(string);
/* 135 */       oldCode = code;
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
/*     */   public void initializeStringTable() {
/* 147 */     this.stringTable = new byte[8192][];
/*     */     
/* 149 */     for (int i = 0; i < 256; i++) {
/* 150 */       this.stringTable[i] = new byte[1];
/* 151 */       this.stringTable[i][0] = (byte)i;
/*     */     } 
/*     */     
/* 154 */     this.tableIndex = 258;
/* 155 */     this.bitsToGet = 9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeString(byte[] string) {
/*     */     try {
/* 165 */       this.uncompData.write(string);
/* 166 */     } catch (IOException e) {
/* 167 */       throw new PdfException("LZW decoder exception.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStringToTable(byte[] oldString, byte newString) {
/* 178 */     int length = oldString.length;
/* 179 */     byte[] string = new byte[length + 1];
/* 180 */     System.arraycopy(oldString, 0, string, 0, length);
/* 181 */     string[length] = newString;
/*     */ 
/*     */     
/* 184 */     this.stringTable[this.tableIndex++] = string;
/*     */     
/* 186 */     if (this.tableIndex == 511) {
/* 187 */       this.bitsToGet = 10;
/* 188 */     } else if (this.tableIndex == 1023) {
/* 189 */       this.bitsToGet = 11;
/* 190 */     } else if (this.tableIndex == 2047) {
/* 191 */       this.bitsToGet = 12;
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
/*     */   public void addStringToTable(byte[] string) {
/* 203 */     this.stringTable[this.tableIndex++] = string;
/*     */     
/* 205 */     if (this.tableIndex == 511) {
/* 206 */       this.bitsToGet = 10;
/* 207 */     } else if (this.tableIndex == 1023) {
/* 208 */       this.bitsToGet = 11;
/* 209 */     } else if (this.tableIndex == 2047) {
/* 210 */       this.bitsToGet = 12;
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
/*     */   public byte[] composeString(byte[] oldString, byte newString) {
/* 222 */     int length = oldString.length;
/* 223 */     byte[] string = new byte[length + 1];
/* 224 */     System.arraycopy(oldString, 0, string, 0, length);
/* 225 */     string[length] = newString;
/*     */     
/* 227 */     return string;
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
/*     */   public int getNextCode() {
/*     */     try {
/* 243 */       this.nextData = this.nextData << 8 | this.data[this.bytePointer++] & 0xFF;
/* 244 */       this.nextBits += 8;
/*     */       
/* 246 */       if (this.nextBits < this.bitsToGet) {
/* 247 */         this.nextData = this.nextData << 8 | this.data[this.bytePointer++] & 0xFF;
/* 248 */         this.nextBits += 8;
/*     */       } 
/*     */       
/* 251 */       int code = this.nextData >> this.nextBits - this.bitsToGet & this.andTable[this.bitsToGet - 9];
/*     */       
/* 253 */       this.nextBits -= this.bitsToGet;
/*     */       
/* 255 */       return code;
/* 256 */     } catch (ArrayIndexOutOfBoundsException e) {
/*     */       
/* 258 */       return 257;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/LZWDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */