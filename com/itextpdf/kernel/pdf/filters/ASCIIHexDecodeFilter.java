/*     */ package com.itextpdf.kernel.pdf.filters;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.io.source.PdfTokenizer;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.MemoryLimitsAwareFilter;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ASCIIHexDecodeFilter
/*     */   extends MemoryLimitsAwareFilter
/*     */ {
/*     */   public static byte[] ASCIIHexDecode(byte[] in) {
/*  68 */     return ASCIIHexDecodeInternal(in, new ByteArrayOutputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
/*  76 */     ByteArrayOutputStream outputStream = enableMemoryLimitsAwareHandler(streamDictionary);
/*  77 */     b = ASCIIHexDecodeInternal(b, outputStream);
/*  78 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] ASCIIHexDecodeInternal(byte[] in, ByteArrayOutputStream out) {
/*  89 */     boolean first = true;
/*  90 */     int n1 = 0;
/*  91 */     for (int k = 0; k < in.length; k++) {
/*  92 */       int ch = in[k] & 0xFF;
/*  93 */       if (ch == 62) {
/*     */         break;
/*     */       }
/*  96 */       if (!PdfTokenizer.isWhitespace(ch)) {
/*     */ 
/*     */         
/*  99 */         int n = ByteBuffer.getHex(ch);
/* 100 */         if (n == -1) {
/* 101 */           throw new PdfException("illegal character in ASCIIHexDecode.");
/*     */         }
/* 103 */         if (first) {
/* 104 */           n1 = n;
/*     */         } else {
/* 106 */           out.write((byte)((n1 << 4) + n));
/*     */         } 
/* 108 */         first = !first;
/*     */       } 
/* 110 */     }  if (!first) {
/* 111 */       out.write((byte)(n1 << 4));
/*     */     }
/* 113 */     return out.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/ASCIIHexDecodeFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */