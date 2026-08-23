/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import com.itextpdf.io.source.DeflaterOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
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
/*     */ public class PngWriter
/*     */ {
/*  58 */   private static final byte[] PNG_SIGNTURE = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
/*     */   
/*  60 */   private static final byte[] IHDR = ByteUtils.getIsoBytes("IHDR");
/*  61 */   private static final byte[] PLTE = ByteUtils.getIsoBytes("PLTE");
/*  62 */   private static final byte[] IDAT = ByteUtils.getIsoBytes("IDAT");
/*  63 */   private static final byte[] IEND = ByteUtils.getIsoBytes("IEND");
/*  64 */   private static final byte[] iCCP = ByteUtils.getIsoBytes("iCCP");
/*     */   
/*     */   private static int[] crc_table;
/*     */   
/*     */   private OutputStream outp;
/*     */   
/*     */   public PngWriter(OutputStream outp) throws IOException {
/*  71 */     this.outp = outp;
/*  72 */     outp.write(PNG_SIGNTURE);
/*     */   }
/*     */   
/*     */   public void writeHeader(int width, int height, int bitDepth, int colorType) throws IOException {
/*  76 */     ByteArrayOutputStream ms = new ByteArrayOutputStream();
/*  77 */     outputInt(width, ms);
/*  78 */     outputInt(height, ms);
/*  79 */     ms.write(bitDepth);
/*  80 */     ms.write(colorType);
/*  81 */     ms.write(0);
/*  82 */     ms.write(0);
/*  83 */     ms.write(0);
/*  84 */     writeChunk(IHDR, ms.toByteArray());
/*     */   }
/*     */   
/*     */   public void writeEnd() throws IOException {
/*  88 */     writeChunk(IEND, new byte[0]);
/*     */   }
/*     */   
/*     */   public void writeData(byte[] data, int stride) throws IOException {
/*  92 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/*  93 */     DeflaterOutputStream zip = new DeflaterOutputStream(stream);
/*     */     int k;
/*  95 */     for (k = 0; k < data.length - stride; k += stride) {
/*  96 */       zip.write(0);
/*  97 */       zip.write(data, k, stride);
/*     */     } 
/*  99 */     int remaining = data.length - k;
/* 100 */     if (remaining > 0) {
/* 101 */       zip.write(0);
/* 102 */       zip.write(data, k, remaining);
/*     */     } 
/* 104 */     zip.close();
/* 105 */     writeChunk(IDAT, stream.toByteArray());
/*     */   }
/*     */   
/*     */   public void writePalette(byte[] data) throws IOException {
/* 109 */     writeChunk(PLTE, data);
/*     */   }
/*     */   
/*     */   public void writeIccProfile(byte[] data) throws IOException {
/* 113 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 114 */     stream.write(73);
/* 115 */     stream.write(67);
/* 116 */     stream.write(67);
/* 117 */     stream.write(0);
/* 118 */     stream.write(0);
/* 119 */     DeflaterOutputStream zip = new DeflaterOutputStream(stream);
/* 120 */     zip.write(data);
/* 121 */     zip.close();
/* 122 */     writeChunk(iCCP, stream.toByteArray());
/*     */   }
/*     */   
/*     */   private static void make_crc_table() {
/* 126 */     if (crc_table != null)
/*     */       return; 
/* 128 */     int[] crc2 = new int[256];
/* 129 */     for (int n = 0; n < 256; n++) {
/* 130 */       int c = n;
/* 131 */       for (int k = 0; k < 8; k++) {
/* 132 */         if ((c & 0x1) != 0) {
/* 133 */           c = 0xEDB88320 ^ c >>> 1;
/*     */         } else {
/* 135 */           c >>>= 1;
/*     */         } 
/* 137 */       }  crc2[n] = c;
/*     */     } 
/* 139 */     crc_table = crc2;
/*     */   }
/*     */   
/*     */   private static int update_crc(int crc, byte[] buf, int offset, int len) {
/* 143 */     int c = crc;
/*     */     
/* 145 */     if (crc_table == null)
/* 146 */       make_crc_table(); 
/* 147 */     for (int n = 0; n < len; n++) {
/* 148 */       c = crc_table[(c ^ buf[n + offset]) & 0xFF] ^ c >>> 8;
/*     */     }
/* 150 */     return c;
/*     */   }
/*     */   
/*     */   private static int crc(byte[] buf, int offset, int len) {
/* 154 */     return update_crc(-1, buf, offset, len) ^ 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   private static int crc(byte[] buf) {
/* 158 */     return update_crc(-1, buf, 0, buf.length) ^ 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   public void outputInt(int n) throws IOException {
/* 162 */     outputInt(n, this.outp);
/*     */   }
/*     */   
/*     */   public static void outputInt(int n, OutputStream s) throws IOException {
/* 166 */     s.write((byte)(n >> 24));
/* 167 */     s.write((byte)(n >> 16));
/* 168 */     s.write((byte)(n >> 8));
/* 169 */     s.write((byte)n);
/*     */   }
/*     */   
/*     */   public void writeChunk(byte[] chunkType, byte[] data) throws IOException {
/* 173 */     outputInt(data.length);
/* 174 */     this.outp.write(chunkType, 0, 4);
/* 175 */     this.outp.write(data);
/* 176 */     int c = update_crc(-1, chunkType, 0, chunkType.length);
/* 177 */     c = update_crc(c, data, 0, data.length) ^ 0xFFFFFFFF;
/* 178 */     outputInt(c);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/PngWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */