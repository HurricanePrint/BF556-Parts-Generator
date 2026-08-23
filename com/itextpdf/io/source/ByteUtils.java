/*     */ package com.itextpdf.io.source;
/*     */ 
/*     */ import com.itextpdf.io.util.DecimalFormatUtil;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ByteUtils
/*     */ {
/*     */   static boolean HighPrecision = false;
/*  57 */   private static final byte[] bytes = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
/*  58 */   private static final byte[] zero = new byte[] { 48 };
/*  59 */   private static final byte[] one = new byte[] { 49 };
/*  60 */   private static final byte[] negOne = new byte[] { 45, 49 };
/*     */   
/*     */   public static byte[] getIsoBytes(String text) {
/*  63 */     if (text == null)
/*  64 */       return null; 
/*  65 */     int len = text.length();
/*  66 */     byte[] b = new byte[len];
/*  67 */     for (int k = 0; k < len; k++)
/*  68 */       b[k] = (byte)text.charAt(k); 
/*  69 */     return b;
/*     */   }
/*     */   
/*     */   public static byte[] getIsoBytes(byte pre, String text) {
/*  73 */     return getIsoBytes(pre, text, (byte)0);
/*     */   }
/*     */   
/*     */   public static byte[] getIsoBytes(byte pre, String text, byte post) {
/*  77 */     if (text == null)
/*  78 */       return null; 
/*  79 */     int len = text.length();
/*  80 */     int start = 0;
/*  81 */     if (pre != 0) {
/*  82 */       len++;
/*  83 */       start = 1;
/*     */     } 
/*  85 */     if (post != 0) {
/*  86 */       len++;
/*     */     }
/*  88 */     byte[] b = new byte[len];
/*  89 */     if (pre != 0) {
/*  90 */       b[0] = pre;
/*     */     }
/*  92 */     if (post != 0) {
/*  93 */       b[len - 1] = post;
/*     */     }
/*  95 */     for (int k = 0; k < text.length(); k++)
/*  96 */       b[k + start] = (byte)text.charAt(k); 
/*  97 */     return b;
/*     */   }
/*     */   
/*     */   public static byte[] getIsoBytes(int n) {
/* 101 */     return getIsoBytes(n, (ByteBuffer)null);
/*     */   }
/*     */   
/*     */   public static byte[] getIsoBytes(double d) {
/* 105 */     return getIsoBytes(d, (ByteBuffer)null);
/*     */   }
/*     */   
/*     */   static byte[] getIsoBytes(int n, ByteBuffer buffer) {
/* 109 */     boolean negative = false;
/* 110 */     if (n < 0) {
/* 111 */       negative = true;
/* 112 */       n = -n;
/*     */     } 
/* 114 */     int intLen = intSize(n);
/* 115 */     if (buffer == null) {  } else {  }  ByteBuffer buf = buffer;
/* 116 */     for (int i = 0; i < intLen; i++) {
/* 117 */       buf.prepend(bytes[n % 10]);
/* 118 */       n /= 10;
/*     */     } 
/* 120 */     if (negative) {
/* 121 */       buf.prepend((byte)45);
/*     */     }
/* 123 */     return (buffer == null) ? buf.getInternalBuffer() : null;
/*     */   }
/*     */   
/*     */   static byte[] getIsoBytes(double d, ByteBuffer buffer) {
/* 127 */     return getIsoBytes(d, buffer, HighPrecision);
/*     */   }
/*     */   static byte[] getIsoBytes(double d, ByteBuffer buffer, boolean highPrecision) {
/*     */     ByteBuffer buf;
/* 131 */     if (highPrecision) {
/* 132 */       if (Math.abs(d) < 1.0E-6D) {
/* 133 */         if (buffer != null) {
/* 134 */           buffer.prepend(zero);
/* 135 */           return null;
/*     */         } 
/* 137 */         return zero;
/*     */       } 
/*     */       
/* 140 */       if (Double.isNaN(d)) {
/* 141 */         Logger logger = LoggerFactory.getLogger(ByteUtils.class);
/* 142 */         logger.error("Attempt to process NaN in PdfNumber or when writing to PDF. Zero value will be used as a fallback.");
/* 143 */         d = 0.0D;
/*     */       } 
/* 145 */       byte[] result = DecimalFormatUtil.formatNumber(d, "0.######").getBytes(StandardCharsets.ISO_8859_1);
/* 146 */       if (buffer != null) {
/* 147 */         buffer.prepend(result);
/* 148 */         return null;
/*     */       } 
/* 150 */       return result;
/*     */     } 
/*     */     
/* 153 */     boolean negative = false;
/* 154 */     if (Math.abs(d) < 1.5E-5D) {
/* 155 */       if (buffer != null) {
/* 156 */         buffer.prepend(zero);
/* 157 */         return null;
/*     */       } 
/* 159 */       return zero;
/*     */     } 
/*     */ 
/*     */     
/* 163 */     if (d < 0.0D) {
/* 164 */       negative = true;
/* 165 */       d = -d;
/*     */     } 
/* 167 */     if (d < 1.0D) {
/* 168 */       d += 5.0E-6D;
/* 169 */       if (d >= 1.0D) {
/*     */         byte[] result;
/* 171 */         if (negative) {
/* 172 */           result = negOne;
/*     */         } else {
/* 174 */           result = one;
/*     */         } 
/* 176 */         if (buffer != null) {
/* 177 */           buffer.prepend(result);
/* 178 */           return null;
/*     */         } 
/* 180 */         return result;
/*     */       } 
/*     */       
/* 183 */       int v = (int)(d * 100000.0D);
/* 184 */       int len = 5;
/* 185 */       for (; len > 0 && 
/* 186 */         v % 10 == 0; len--) {
/* 187 */         v /= 10;
/*     */       }
/* 189 */       buf = (buffer != null) ? buffer : new ByteBuffer(negative ? (len + 3) : (len + 2));
/* 190 */       for (int i = 0; i < len; i++) {
/* 191 */         buf.prepend(bytes[v % 10]);
/* 192 */         v /= 10;
/*     */       } 
/* 194 */       buf.prepend((byte)46).prepend((byte)48);
/* 195 */       if (negative) {
/* 196 */         buf.prepend((byte)45);
/*     */       }
/* 198 */     } else if (d <= 32767.0D) {
/* 199 */       int intLen; d += 0.005D;
/* 200 */       int v = (int)(d * 100.0D);
/*     */       
/* 202 */       if (v >= 1000000) {
/* 203 */         intLen = 5;
/* 204 */       } else if (v >= 100000) {
/* 205 */         intLen = 4;
/* 206 */       } else if (v >= 10000) {
/* 207 */         intLen = 3;
/* 208 */       } else if (v >= 1000) {
/* 209 */         intLen = 2;
/*     */       } else {
/* 211 */         intLen = 1;
/*     */       } 
/* 213 */       int fracLen = 0;
/* 214 */       if (v % 100 != 0) {
/*     */         
/* 216 */         fracLen = 2;
/* 217 */         if (v % 10 != 0) {
/* 218 */           fracLen++;
/*     */         } else {
/* 220 */           v /= 10;
/*     */         } 
/*     */       } else {
/* 223 */         v /= 100;
/*     */       } 
/* 225 */       buf = (buffer != null) ? buffer : new ByteBuffer(intLen + fracLen + (negative ? 1 : 0));
/*     */       int i;
/* 227 */       for (i = 0; i < fracLen - 1; i++) {
/* 228 */         buf.prepend(bytes[v % 10]);
/* 229 */         v /= 10;
/*     */       } 
/* 231 */       if (fracLen > 0) {
/* 232 */         buf.prepend((byte)46);
/*     */       }
/* 234 */       for (i = 0; i < intLen; i++) {
/* 235 */         buf.prepend(bytes[v % 10]);
/* 236 */         v /= 10;
/*     */       } 
/* 238 */       if (negative)
/* 239 */         buf.prepend((byte)45); 
/*     */     } else {
/*     */       long v;
/* 242 */       d += 0.5D;
/*     */       
/* 244 */       if (d > 9.223372036854776E18D) {
/*     */         
/* 246 */         v = Long.MAX_VALUE;
/*     */       } else {
/* 248 */         if (Double.isNaN(d)) {
/* 249 */           Logger logger = LoggerFactory.getLogger(ByteUtils.class);
/* 250 */           logger.error("Attempt to process NaN in PdfNumber or when writing to PDF. Zero value will be used as a fallback.");
/*     */           
/* 252 */           d = 0.0D;
/*     */         } 
/* 254 */         v = (long)d;
/*     */       } 
/* 256 */       int intLen = longSize(v);
/* 257 */       if (buffer == null) {  } else {  }  buf = buffer;
/* 258 */       for (int i = 0; i < intLen; i++) {
/* 259 */         buf.prepend(bytes[(int)(v % 10L)]);
/* 260 */         v /= 10L;
/*     */       } 
/* 262 */       if (negative) {
/* 263 */         buf.prepend((byte)45);
/*     */       }
/*     */     } 
/*     */     
/* 267 */     return (buffer == null) ? buf.getInternalBuffer() : null;
/*     */   }
/*     */   
/*     */   private static int longSize(long l) {
/* 271 */     long m = 10L;
/* 272 */     for (int i = 1; i < 19; i++) {
/* 273 */       if (l < m)
/* 274 */         return i; 
/* 275 */       m *= 10L;
/*     */     } 
/* 277 */     return 19;
/*     */   }
/*     */   
/*     */   private static int intSize(int l) {
/* 281 */     long m = 10L;
/* 282 */     for (int i = 1; i < 10; i++) {
/* 283 */       if (l < m)
/* 284 */         return i; 
/* 285 */       m *= 10L;
/*     */     } 
/* 287 */     return 10;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/source/ByteUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */