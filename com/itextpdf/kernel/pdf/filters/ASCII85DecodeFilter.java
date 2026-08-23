/*     */ package com.itextpdf.kernel.pdf.filters;
/*     */ 
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
/*     */ 
/*     */ public class ASCII85DecodeFilter
/*     */   extends MemoryLimitsAwareFilter
/*     */ {
/*     */   public static byte[] ASCII85Decode(byte[] in) {
/*  68 */     return ASCII85DecodeInternal(in, new ByteArrayOutputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
/*  76 */     ByteArrayOutputStream outputStream = enableMemoryLimitsAwareHandler(streamDictionary);
/*  77 */     b = ASCII85DecodeInternal(b, outputStream);
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
/*     */   
/*     */   private static byte[] ASCII85DecodeInternal(byte[] in, ByteArrayOutputStream out) {
/*  90 */     int state = 0;
/*  91 */     int[] chn = new int[5];
/*  92 */     for (int k = 0; k < in.length; k++) {
/*  93 */       int ch = in[k] & 0xFF;
/*  94 */       if (ch == 126) {
/*     */         break;
/*     */       }
/*  97 */       if (!PdfTokenizer.isWhitespace(ch))
/*     */       {
/*     */         
/* 100 */         if (ch == 122 && state == 0) {
/* 101 */           out.write(0);
/* 102 */           out.write(0);
/* 103 */           out.write(0);
/* 104 */           out.write(0);
/*     */         } else {
/*     */           
/* 107 */           if (ch < 33 || ch > 117) {
/* 108 */             throw new PdfException("Illegal character in ASCII85Decode.");
/*     */           }
/* 110 */           chn[state] = ch - 33;
/* 111 */           state++;
/* 112 */           if (state == 5) {
/* 113 */             state = 0;
/* 114 */             int r = 0;
/* 115 */             for (int j = 0; j < 5; j++) {
/* 116 */               r = r * 85 + chn[j];
/*     */             }
/* 118 */             out.write((byte)(r >> 24));
/* 119 */             out.write((byte)(r >> 16));
/* 120 */             out.write((byte)(r >> 8));
/* 121 */             out.write((byte)r);
/*     */           } 
/*     */         }  } 
/* 124 */     }  if (state == 2) {
/* 125 */       int r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85 + 614125 + 7225 + 85;
/* 126 */       out.write((byte)(r >> 24));
/* 127 */     } else if (state == 3) {
/* 128 */       int r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85 + chn[2] * 85 * 85 + 7225 + 85;
/* 129 */       out.write((byte)(r >> 24));
/* 130 */       out.write((byte)(r >> 16));
/* 131 */     } else if (state == 4) {
/* 132 */       int r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85 + chn[2] * 85 * 85 + chn[3] * 85 + 85;
/* 133 */       out.write((byte)(r >> 24));
/* 134 */       out.write((byte)(r >> 16));
/* 135 */       out.write((byte)(r >> 8));
/*     */     } 
/* 137 */     return out.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/ASCII85DecodeFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */