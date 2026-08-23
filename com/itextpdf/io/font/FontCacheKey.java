/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FontCacheKey
/*     */ {
/*     */   public static FontCacheKey create(String fontName) {
/*  50 */     return new FontCacheStringKey(fontName);
/*     */   }
/*     */   
/*     */   public static FontCacheKey create(String fontName, int ttcIndex) {
/*  54 */     return new FontCacheTtcKey(fontName, ttcIndex);
/*     */   }
/*     */   
/*     */   public static FontCacheKey create(byte[] fontProgram) {
/*  58 */     return new FontCacheBytesKey(fontProgram);
/*     */   }
/*     */   
/*     */   public static FontCacheKey create(byte[] fontProgram, int ttcIndex) {
/*  62 */     return new FontCacheTtcKey(fontProgram, ttcIndex);
/*     */   }
/*     */   
/*     */   private static class FontCacheStringKey extends FontCacheKey {
/*     */     private String fontName;
/*     */     
/*     */     FontCacheStringKey(String fontName) {
/*  69 */       this.fontName = fontName;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  74 */       if (this == o) return true; 
/*  75 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/*  77 */       FontCacheStringKey that = (FontCacheStringKey)o;
/*     */       
/*  79 */       return (this.fontName != null) ? this.fontName.equals(that.fontName) : ((that.fontName == null));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  84 */       return (this.fontName != null) ? this.fontName.hashCode() : 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FontCacheBytesKey
/*     */     extends FontCacheKey {
/*     */     private byte[] firstFontBytes;
/*     */     private int fontLength;
/*     */     private int hashcode;
/*     */     
/*     */     FontCacheBytesKey(byte[] fontBytes) {
/*  95 */       if (fontBytes != null) {
/*  96 */         int maxBytesNum = 10000;
/*  97 */         this.firstFontBytes = (fontBytes.length > maxBytesNum) ? Arrays.copyOf(fontBytes, maxBytesNum) : fontBytes;
/*  98 */         this.fontLength = fontBytes.length;
/*     */       } 
/* 100 */       this.hashcode = calcHashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 105 */       if (this == o) return true; 
/* 106 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 108 */       FontCacheBytesKey that = (FontCacheBytesKey)o;
/*     */       
/* 110 */       if (this.fontLength != that.fontLength) return false; 
/* 111 */       return Arrays.equals(this.firstFontBytes, that.firstFontBytes);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 116 */       return this.hashcode;
/*     */     }
/*     */     
/*     */     private int calcHashCode() {
/* 120 */       int result = Arrays.hashCode(this.firstFontBytes);
/* 121 */       result = 31 * result + this.fontLength;
/* 122 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FontCacheTtcKey extends FontCacheKey {
/*     */     private FontCacheKey ttcKey;
/*     */     private int ttcIndex;
/*     */     
/*     */     FontCacheTtcKey(String fontName, int ttcIndex) {
/* 131 */       this.ttcKey = new FontCacheKey.FontCacheStringKey(fontName);
/* 132 */       this.ttcIndex = ttcIndex;
/*     */     }
/*     */     
/*     */     FontCacheTtcKey(byte[] fontBytes, int ttcIndex) {
/* 136 */       this.ttcKey = new FontCacheKey.FontCacheBytesKey(fontBytes);
/* 137 */       this.ttcIndex = ttcIndex;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 142 */       if (this == o) return true; 
/* 143 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 145 */       FontCacheTtcKey that = (FontCacheTtcKey)o;
/*     */       
/* 147 */       if (this.ttcIndex != that.ttcIndex) return false; 
/* 148 */       return this.ttcKey.equals(that.ttcKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       int result = this.ttcKey.hashCode();
/* 154 */       result = 31 * result + this.ttcIndex;
/* 155 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontCacheKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */