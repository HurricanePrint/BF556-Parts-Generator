/*     */ package com.itextpdf.layout.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontCacheKey;
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.FontProgramDescriptor;
/*     */ import com.itextpdf.io.font.FontProgramDescriptorFactory;
/*     */ import com.itextpdf.io.util.ArrayUtil;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontInfo
/*     */ {
/*  70 */   private static final Map<FontCacheKey, FontProgramDescriptor> fontNamesCache = new ConcurrentHashMap<>();
/*     */   
/*     */   private final String fontName;
/*     */   
/*     */   private final byte[] fontData;
/*     */   private final FontProgramDescriptor descriptor;
/*     */   private final Range range;
/*     */   private final int hash;
/*     */   private final String encoding;
/*     */   private final String alias;
/*     */   
/*     */   private FontInfo(String fontName, byte[] fontData, String encoding, FontProgramDescriptor descriptor, Range unicodeRange, String alias) {
/*  82 */     this.fontName = fontName;
/*  83 */     this.fontData = fontData;
/*  84 */     this.encoding = encoding;
/*  85 */     this.descriptor = descriptor;
/*  86 */     this.range = (unicodeRange != null) ? unicodeRange : RangeBuilder.getFullRange();
/*  87 */     this.alias = (alias != null) ? alias.toLowerCase() : null;
/*  88 */     this.hash = calculateHashCode(this.fontName, this.fontData, this.encoding, this.range);
/*     */   }
/*     */   
/*     */   public static FontInfo create(FontInfo fontInfo, String alias, Range range) {
/*  92 */     return new FontInfo(fontInfo.fontName, fontInfo.fontData, fontInfo.encoding, fontInfo.descriptor, range, alias);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FontInfo create(FontInfo fontInfo, String alias) {
/*  97 */     return create(fontInfo, alias, (Range)null);
/*     */   }
/*     */   
/*     */   public static FontInfo create(FontProgram fontProgram, String encoding, String alias, Range range) {
/* 101 */     FontProgramDescriptor descriptor = FontProgramDescriptorFactory.fetchDescriptor(fontProgram);
/* 102 */     return new FontInfo(descriptor.getFontName(), null, encoding, descriptor, range, alias);
/*     */   }
/*     */   
/*     */   public static FontInfo create(FontProgram fontProgram, String encoding, String alias) {
/* 106 */     return create(fontProgram, encoding, alias, (Range)null);
/*     */   }
/*     */   
/*     */   static FontInfo create(String fontName, String encoding, String alias, Range range) {
/* 110 */     FontCacheKey cacheKey = FontCacheKey.create(fontName);
/* 111 */     FontProgramDescriptor descriptor = getFontNamesFromCache(cacheKey);
/* 112 */     if (descriptor == null) {
/* 113 */       descriptor = FontProgramDescriptorFactory.fetchDescriptor(fontName);
/* 114 */       putFontNamesToCache(cacheKey, descriptor);
/*     */     } 
/* 116 */     return (descriptor != null) ? new FontInfo(fontName, null, encoding, descriptor, range, alias) : null;
/*     */   }
/*     */   
/*     */   static FontInfo create(byte[] fontProgram, String encoding, String alias, Range range) {
/* 120 */     FontCacheKey cacheKey = FontCacheKey.create(fontProgram);
/* 121 */     FontProgramDescriptor descriptor = getFontNamesFromCache(cacheKey);
/* 122 */     if (descriptor == null) {
/* 123 */       descriptor = FontProgramDescriptorFactory.fetchDescriptor(fontProgram);
/* 124 */       putFontNamesToCache(cacheKey, descriptor);
/*     */     } 
/* 126 */     return (descriptor != null) ? new FontInfo(null, fontProgram, encoding, descriptor, range, alias) : null;
/*     */   }
/*     */   
/*     */   public FontProgramDescriptor getDescriptor() {
/* 130 */     return this.descriptor;
/*     */   }
/*     */ 
/*     */   
/*     */   public Range getFontUnicodeRange() {
/* 135 */     return this.range;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFontName() {
/* 144 */     return this.fontName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getFontData() {
/* 153 */     return this.fontData;
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 157 */     return this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlias() {
/* 166 */     return this.alias;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 171 */     if (this == o) return true; 
/* 172 */     if (!(o instanceof FontInfo)) return false;
/*     */     
/* 174 */     FontInfo that = (FontInfo)o;
/* 175 */     return (((this.fontName != null) ? this.fontName.equals(that.fontName) : (that.fontName == null)) && this.range
/* 176 */       .equals(that.range) && 
/* 177 */       Arrays.equals(this.fontData, that.fontData) && ((this.encoding != null) ? this.encoding
/* 178 */       .equals(that.encoding) : (that.encoding == null)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 183 */     return this.hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 188 */     String name = this.descriptor.getFontName();
/* 189 */     if (name.length() > 0) {
/* 190 */       if (this.encoding != null) {
/* 191 */         return MessageFormatUtil.format("{0}+{1}", new Object[] { name, this.encoding });
/*     */       }
/* 193 */       return name;
/*     */     } 
/*     */     
/* 196 */     return super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static int calculateHashCode(String fontName, byte[] bytes, String encoding, Range range) {
/* 201 */     int result = (fontName != null) ? fontName.hashCode() : 0;
/* 202 */     result = 31 * result + ArrayUtil.hashCode(bytes);
/* 203 */     result = 31 * result + ((encoding != null) ? encoding.hashCode() : 0);
/* 204 */     result = 31 * result + range.hashCode();
/* 205 */     return result;
/*     */   }
/*     */   
/*     */   private static FontProgramDescriptor getFontNamesFromCache(FontCacheKey key) {
/* 209 */     return fontNamesCache.get(key);
/*     */   }
/*     */   
/*     */   private static void putFontNamesToCache(FontCacheKey key, FontProgramDescriptor descriptor) {
/* 213 */     if (descriptor != null)
/* 214 */       fontNamesCache.put(key, descriptor); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */