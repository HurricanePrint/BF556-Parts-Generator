/*     */ package com.itextpdf.io.font.cmap;
/*     */ 
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCMap
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -9057458889624600915L;
/*     */   private String cmapName;
/*     */   private String registry;
/*     */   private String ordering;
/*     */   private int supplement;
/*     */   
/*     */   public String getName() {
/*  65 */     return this.cmapName;
/*     */   }
/*     */   
/*     */   void setName(String cmapName) {
/*  69 */     this.cmapName = cmapName;
/*     */   }
/*     */   
/*     */   public String getOrdering() {
/*  73 */     return this.ordering;
/*     */   }
/*     */   
/*     */   void setOrdering(String ordering) {
/*  77 */     this.ordering = ordering;
/*     */   }
/*     */   
/*     */   public String getRegistry() {
/*  81 */     return this.registry;
/*     */   }
/*     */   
/*     */   void setRegistry(String registry) {
/*  85 */     this.registry = registry;
/*     */   }
/*     */   
/*     */   public int getSupplement() {
/*  89 */     return this.supplement;
/*     */   }
/*     */   
/*     */   void setSupplement(int supplement) {
/*  93 */     this.supplement = supplement;
/*     */   }
/*     */ 
/*     */   
/*     */   abstract void addChar(String paramString, CMapObject paramCMapObject);
/*     */   
/*     */   void addCodeSpaceRange(byte[] low, byte[] high) {}
/*     */   
/*     */   void addRange(String from, String to, CMapObject code) {
/* 102 */     byte[] a1 = decodeStringToByte(from);
/* 103 */     byte[] a2 = decodeStringToByte(to);
/* 104 */     if (a1.length != a2.length || a1.length == 0) {
/* 105 */       throw new IllegalArgumentException("Invalid map.");
/*     */     }
/* 107 */     byte[] sout = null;
/* 108 */     if (code.isString()) {
/* 109 */       sout = decodeStringToByte(code.toString());
/*     */     }
/* 111 */     int start = byteArrayToInt(a1);
/* 112 */     int end = byteArrayToInt(a2);
/* 113 */     for (int k = start; k <= end; k++) {
/* 114 */       intToByteArray(k, a1);
/* 115 */       String mark = PdfEncodings.convertToString(a1, null);
/* 116 */       if (code.isArray()) {
/* 117 */         List<CMapObject> codes = (ArrayList)code.getValue();
/* 118 */         addChar(mark, codes.get(k - start));
/* 119 */       } else if (code.isNumber()) {
/* 120 */         int nn = ((Integer)code.getValue()).intValue() + k - start;
/* 121 */         addChar(mark, new CMapObject(4, Integer.valueOf(nn)));
/* 122 */       } else if (code.isString()) {
/* 123 */         CMapObject s1 = new CMapObject(2, sout);
/* 124 */         addChar(mark, s1);
/* 125 */         assert sout != null;
/* 126 */         intToByteArray(byteArrayToInt(sout) + 1, sout);
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
/*     */ 
/*     */   
/*     */   public static byte[] decodeStringToByte(String range) {
/* 140 */     byte[] bytes = new byte[range.length()];
/* 141 */     for (int i = 0; i < range.length(); i++) {
/* 142 */       bytes[i] = (byte)range.charAt(i);
/*     */     }
/* 144 */     return bytes;
/*     */   }
/*     */   
/*     */   protected String toUnicodeString(String value, boolean isHexWriting) {
/* 148 */     byte[] bytes = decodeStringToByte(value);
/* 149 */     if (isHexWriting) {
/* 150 */       return PdfEncodings.convertToString(bytes, "UnicodeBigUnmarked");
/*     */     }
/* 152 */     if (bytes.length >= 2 && bytes[0] == -2 && bytes[1] == -1) {
/* 153 */       return PdfEncodings.convertToString(bytes, "UnicodeBig");
/*     */     }
/* 155 */     return PdfEncodings.convertToString(bytes, "PDF");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void intToByteArray(int n, byte[] b) {
/* 161 */     for (int k = b.length - 1; k >= 0; k--) {
/* 162 */       b[k] = (byte)n;
/* 163 */       n >>>= 8;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int byteArrayToInt(byte[] b) {
/* 168 */     int n = 0;
/* 169 */     for (int k = 0; k < b.length; k++) {
/* 170 */       n <<= 8;
/* 171 */       n |= b[k] & 0xFF;
/*     */     } 
/* 173 */     return n;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/AbstractCMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */