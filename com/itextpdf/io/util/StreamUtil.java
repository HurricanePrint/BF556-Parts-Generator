/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import com.itextpdf.io.source.IRandomAccessSource;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public final class StreamUtil
/*     */ {
/*     */   private static final int TRANSFER_SIZE = 65536;
/*  66 */   private static final byte[] escR = ByteUtils.getIsoBytes("\\r");
/*  67 */   private static final byte[] escN = ByteUtils.getIsoBytes("\\n");
/*  68 */   private static final byte[] escT = ByteUtils.getIsoBytes("\\t");
/*  69 */   private static final byte[] escB = ByteUtils.getIsoBytes("\\b");
/*  70 */   private static final byte[] escF = ByteUtils.getIsoBytes("\\f");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void skip(InputStream stream, long size) throws IOException {
/*  86 */     while (size > 0L) {
/*  87 */       long n = stream.skip(size);
/*  88 */       if (n <= 0L) {
/*     */         break;
/*     */       }
/*  91 */       size -= n;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] createEscapedString(byte[] bytes) {
/* 102 */     return createBufferedEscapedString(bytes).toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeEscapedString(OutputStream outputStream, byte[] bytes) {
/* 112 */     ByteBuffer buf = createBufferedEscapedString(bytes);
/*     */     try {
/* 114 */       outputStream.write(buf.getInternalBuffer(), 0, buf.size());
/* 115 */     } catch (IOException e) {
/* 116 */       throw new IOException("Cannot write bytes.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeHexedString(OutputStream outputStream, byte[] bytes) {
/* 121 */     ByteBuffer buf = createBufferedHexedString(bytes);
/*     */     try {
/* 123 */       outputStream.write(buf.getInternalBuffer(), 0, buf.size());
/* 124 */     } catch (IOException e) {
/* 125 */       throw new IOException("Cannot write bytes.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ByteBuffer createBufferedEscapedString(byte[] bytes) {
/* 130 */     ByteBuffer buf = new ByteBuffer(bytes.length * 2 + 2);
/* 131 */     buf.append(40);
/* 132 */     for (byte b : bytes) {
/* 133 */       switch (b) {
/*     */         case 13:
/* 135 */           buf.append(escR);
/*     */           break;
/*     */         case 10:
/* 138 */           buf.append(escN);
/*     */           break;
/*     */         case 9:
/* 141 */           buf.append(escT);
/*     */           break;
/*     */         case 8:
/* 144 */           buf.append(escB);
/*     */           break;
/*     */         case 12:
/* 147 */           buf.append(escF);
/*     */           break;
/*     */         case 40:
/*     */         case 41:
/*     */         case 92:
/* 152 */           buf.append(92).append(b);
/*     */           break;
/*     */         default:
/* 155 */           if (b < 8 && b >= 0) {
/* 156 */             buf.append("\\00").append(Integer.toOctalString(b)); break;
/* 157 */           }  if (b >= 8 && b < 32) {
/* 158 */             buf.append("\\0").append(Integer.toOctalString(b)); break;
/*     */           } 
/* 160 */           buf.append(b);
/*     */           break;
/*     */       } 
/*     */     } 
/* 164 */     buf.append(41);
/* 165 */     return buf;
/*     */   }
/*     */   
/*     */   public static ByteBuffer createBufferedHexedString(byte[] bytes) {
/* 169 */     ByteBuffer buf = new ByteBuffer(bytes.length * 2 + 2);
/* 170 */     buf.append(60);
/* 171 */     for (byte b : bytes) {
/* 172 */       buf.appendHex(b);
/*     */     }
/* 174 */     buf.append(62);
/* 175 */     return buf;
/*     */   }
/*     */   
/*     */   public static void transferBytes(InputStream input, OutputStream output) throws IOException {
/* 179 */     byte[] buffer = new byte[65536];
/*     */     while (true) {
/* 181 */       int len = input.read(buffer, 0, 65536);
/* 182 */       if (len > 0) {
/* 183 */         output.write(buffer, 0, len);
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void transferBytes(RandomAccessFileOrArray input, OutputStream output) throws IOException {
/* 191 */     byte[] buffer = new byte[65536];
/*     */     while (true) {
/* 193 */       int len = input.read(buffer, 0, 65536);
/* 194 */       if (len > 0) {
/* 195 */         output.write(buffer, 0, len);
/*     */         continue;
/*     */       } 
/*     */       break;
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
/*     */   public static byte[] inputStreamToArray(InputStream stream) throws IOException {
/* 210 */     byte[] b = new byte[8192];
/* 211 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*     */     while (true) {
/* 213 */       int read = stream.read(b);
/* 214 */       if (read < 1) {
/*     */         break;
/*     */       }
/* 217 */       output.write(b, 0, read);
/*     */     } 
/* 219 */     output.close();
/* 220 */     return output.toByteArray();
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
/*     */   public static void copyBytes(IRandomAccessSource source, long start, long length, OutputStream output) throws IOException {
/* 233 */     if (length <= 0L) {
/*     */       return;
/*     */     }
/* 236 */     long idx = start;
/* 237 */     byte[] buf = new byte[8192];
/* 238 */     while (length > 0L) {
/* 239 */       long n = source.get(idx, buf, 0, (int)Math.min(buf.length, length));
/* 240 */       if (n <= 0L) {
/* 241 */         throw new EOFException();
/*     */       }
/* 243 */       output.write(buf, 0, (int)n);
/* 244 */       idx += n;
/* 245 */       length -= n;
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
/*     */   public static void readFully(InputStream input, byte[] b, int off, int len) throws IOException {
/* 259 */     if (len < 0)
/* 260 */       throw new IndexOutOfBoundsException(); 
/* 261 */     int n = 0;
/* 262 */     while (n < len) {
/* 263 */       int count = input.read(b, off + n, len - n);
/* 264 */       if (count < 0) {
/* 265 */         throw new EOFException();
/*     */       }
/* 267 */       n += count;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/StreamUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */