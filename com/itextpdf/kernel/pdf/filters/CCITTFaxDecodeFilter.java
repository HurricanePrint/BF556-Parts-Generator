/*     */ package com.itextpdf.kernel.pdf.filters;
/*     */ 
/*     */ import com.itextpdf.io.codec.TIFFFaxDecoder;
/*     */ import com.itextpdf.io.codec.TIFFFaxDecompressor;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CCITTFaxDecodeFilter
/*     */   implements IFilterHandler
/*     */ {
/*     */   public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
/*  63 */     PdfNumber wn = streamDictionary.getAsNumber(PdfName.Width);
/*  64 */     PdfNumber hn = streamDictionary.getAsNumber(PdfName.Height);
/*  65 */     if (wn == null || hn == null) {
/*  66 */       throw new PdfException("Filter CCITTFaxDecode is only supported for images");
/*     */     }
/*  68 */     int width = wn.intValue();
/*  69 */     int height = hn.intValue();
/*     */     
/*  71 */     PdfDictionary param = (decodeParams instanceof PdfDictionary) ? (PdfDictionary)decodeParams : null;
/*  72 */     int k = 0;
/*  73 */     boolean blackIs1 = false;
/*  74 */     boolean byteAlign = false;
/*  75 */     if (param != null) {
/*  76 */       PdfNumber kn = param.getAsNumber(PdfName.K);
/*  77 */       if (kn != null) {
/*  78 */         k = kn.intValue();
/*     */       }
/*  80 */       PdfBoolean bo = param.getAsBoolean(PdfName.BlackIs1);
/*  81 */       if (bo != null) {
/*  82 */         blackIs1 = bo.getValue();
/*     */       }
/*  84 */       bo = param.getAsBoolean(PdfName.EncodedByteAlign);
/*  85 */       if (bo != null) {
/*  86 */         byteAlign = bo.getValue();
/*     */       }
/*     */     } 
/*  89 */     byte[] outBuf = new byte[(width + 7) / 8 * height];
/*  90 */     TIFFFaxDecompressor decoder = new TIFFFaxDecompressor();
/*  91 */     if (k == 0 || k > 0) {
/*  92 */       int tiffT4Options = (k > 0) ? 1 : 0;
/*  93 */       tiffT4Options |= byteAlign ? 4 : 0;
/*  94 */       decoder.SetOptions(1, 3, tiffT4Options, 0);
/*  95 */       decoder.decodeRaw(outBuf, b, width, height);
/*  96 */       if (decoder.fails > 0) {
/*  97 */         byte[] outBuf2 = new byte[(width + 7) / 8 * height];
/*  98 */         int oldFails = decoder.fails;
/*  99 */         decoder.SetOptions(1, 2, tiffT4Options, 0);
/* 100 */         decoder.decodeRaw(outBuf2, b, width, height);
/* 101 */         if (decoder.fails < oldFails) {
/* 102 */           outBuf = outBuf2;
/*     */         }
/*     */       } 
/*     */     } else {
/* 106 */       long tiffT6Options = 0L;
/* 107 */       tiffT6Options |= byteAlign ? 4L : 0L;
/* 108 */       TIFFFaxDecoder deca = new TIFFFaxDecoder(1, width, height);
/* 109 */       deca.decodeT6(outBuf, b, 0, height, tiffT6Options);
/*     */     } 
/* 111 */     if (!blackIs1) {
/* 112 */       int len = outBuf.length;
/* 113 */       for (int t = 0; t < len; t++) {
/* 114 */         outBuf[t] = (byte)(outBuf[t] ^ 0xFF);
/*     */       }
/*     */     } 
/* 117 */     b = outBuf;
/* 118 */     return b;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/CCITTFaxDecodeFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */