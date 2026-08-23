/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.constants.StandardFonts;
/*     */ import com.itextpdf.io.font.woff2.Woff2Converter;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontProgramDescriptorFactory
/*     */ {
/*     */   private static boolean FETCH_CACHED_FIRST = true;
/*     */   
/*     */   public static FontProgramDescriptor fetchDescriptor(String fontName) {
/*  53 */     if (fontName == null || fontName.length() == 0) {
/*  54 */       return null;
/*     */     }
/*     */     
/*  57 */     String baseName = FontProgram.trimFontStyle(fontName);
/*     */     
/*  59 */     boolean isBuiltinFonts14 = StandardFonts.isStandardFont(fontName);
/*  60 */     boolean isCidFont = (!isBuiltinFonts14 && FontCache.isPredefinedCidFont(baseName));
/*     */     
/*  62 */     FontProgramDescriptor fontDescriptor = null;
/*  63 */     if (FETCH_CACHED_FIRST) {
/*  64 */       fontDescriptor = fetchCachedDescriptor(fontName, null);
/*  65 */       if (fontDescriptor != null) {
/*  66 */         return fontDescriptor;
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/*  71 */       String fontNameLowerCase = baseName.toLowerCase();
/*  72 */       if (isBuiltinFonts14 || fontNameLowerCase.endsWith(".afm") || fontNameLowerCase.endsWith(".pfm")) {
/*  73 */         fontDescriptor = fetchType1FontDescriptor(fontName, null);
/*  74 */       } else if (isCidFont) {
/*  75 */         fontDescriptor = fetchCidFontDescriptor(fontName);
/*  76 */       } else if (fontNameLowerCase.endsWith(".ttf") || fontNameLowerCase.endsWith(".otf")) {
/*  77 */         fontDescriptor = fetchTrueTypeFontDescriptor(fontName);
/*  78 */       } else if (fontNameLowerCase.endsWith(".woff") || fontNameLowerCase.endsWith(".woff2")) {
/*     */         byte[] fontProgram;
/*  80 */         if (fontNameLowerCase.endsWith(".woff")) {
/*  81 */           fontProgram = WoffConverter.convert(FontProgramFactory.readFontBytesFromPath(baseName));
/*     */         } else {
/*  83 */           fontProgram = Woff2Converter.convert(FontProgramFactory.readFontBytesFromPath(baseName));
/*     */         } 
/*  85 */         fontDescriptor = fetchTrueTypeFontDescriptor(fontProgram);
/*     */       } else {
/*  87 */         fontDescriptor = fetchTTCDescriptor(baseName);
/*     */       } 
/*  89 */     } catch (Exception ignored) {
/*  90 */       fontDescriptor = null;
/*     */     } 
/*     */     
/*  93 */     return fontDescriptor;
/*     */   }
/*     */   
/*     */   public static FontProgramDescriptor fetchDescriptor(byte[] fontProgram) {
/*  97 */     if (fontProgram == null || fontProgram.length == 0) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     FontProgramDescriptor fontDescriptor = null;
/* 102 */     if (FETCH_CACHED_FIRST) {
/* 103 */       fontDescriptor = fetchCachedDescriptor(null, fontProgram);
/* 104 */       if (fontDescriptor != null) {
/* 105 */         return fontDescriptor;
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 110 */       fontDescriptor = fetchTrueTypeFontDescriptor(fontProgram);
/* 111 */     } catch (Exception exception) {}
/*     */     
/* 113 */     if (fontDescriptor == null) {
/*     */       try {
/* 115 */         fontDescriptor = fetchType1FontDescriptor(null, fontProgram);
/* 116 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 119 */     return fontDescriptor;
/*     */   }
/*     */   
/*     */   public static FontProgramDescriptor fetchDescriptor(FontProgram fontProgram) {
/* 123 */     return fetchDescriptorFromFontProgram(fontProgram);
/*     */   }
/*     */ 
/*     */   
/*     */   private static FontProgramDescriptor fetchCachedDescriptor(String fontName, byte[] fontProgram) {
/*     */     FontCacheKey key;
/* 129 */     if (fontName != null) {
/* 130 */       key = FontCacheKey.create(fontName);
/*     */     } else {
/* 132 */       key = FontCacheKey.create(fontProgram);
/*     */     } 
/* 134 */     FontProgram fontFound = FontCache.getFont(key);
/* 135 */     return (fontFound != null) ? fetchDescriptorFromFontProgram(fontFound) : null;
/*     */   }
/*     */   
/*     */   private static FontProgramDescriptor fetchTTCDescriptor(String baseName) throws IOException {
/* 139 */     int ttcSplit = baseName.toLowerCase().indexOf(".ttc,");
/* 140 */     if (ttcSplit > 0) {
/*     */       String ttcName;
/*     */       
/*     */       int ttcIndex;
/*     */       try {
/* 145 */         ttcName = baseName.substring(0, ttcSplit + 4);
/*     */         
/* 147 */         ttcIndex = Integer.parseInt(baseName.substring(ttcSplit + 5));
/* 148 */       } catch (NumberFormatException nfe) {
/* 149 */         throw new IOException(nfe.getMessage(), nfe);
/*     */       } 
/* 151 */       OpenTypeParser parser = new OpenTypeParser(ttcName, ttcIndex);
/* 152 */       FontProgramDescriptor descriptor = fetchOpenTypeFontDescriptor(parser);
/* 153 */       parser.close();
/* 154 */       return descriptor;
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static FontProgramDescriptor fetchTrueTypeFontDescriptor(String fontName) throws IOException {
/* 161 */     try (OpenTypeParser parser = new OpenTypeParser(fontName)) {
/* 162 */       return fetchOpenTypeFontDescriptor(parser);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static FontProgramDescriptor fetchTrueTypeFontDescriptor(byte[] fontProgram) throws IOException {
/* 167 */     try (OpenTypeParser parser = new OpenTypeParser(fontProgram)) {
/* 168 */       return fetchOpenTypeFontDescriptor(parser);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static FontProgramDescriptor fetchOpenTypeFontDescriptor(OpenTypeParser fontParser) throws IOException {
/* 173 */     fontParser.loadTables(false);
/* 174 */     return new FontProgramDescriptor(fontParser.getFontNames(), (fontParser.getPostTable()).italicAngle, 
/* 175 */         (fontParser.getPostTable()).isFixedPitch);
/*     */   }
/*     */ 
/*     */   
/*     */   private static FontProgramDescriptor fetchType1FontDescriptor(String fontName, byte[] afm) throws IOException {
/* 180 */     Type1Font fp = new Type1Font(fontName, null, afm, null);
/* 181 */     return new FontProgramDescriptor(fp.getFontNames(), fp.getFontMetrics());
/*     */   }
/*     */   
/*     */   private static FontProgramDescriptor fetchCidFontDescriptor(String fontName) {
/* 185 */     CidFont font = new CidFont(fontName, null);
/* 186 */     return new FontProgramDescriptor(font.getFontNames(), font.getFontMetrics());
/*     */   }
/*     */   
/*     */   private static FontProgramDescriptor fetchDescriptorFromFontProgram(FontProgram fontProgram) {
/* 190 */     return new FontProgramDescriptor(fontProgram.getFontNames(), fontProgram.getFontMetrics());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontProgramDescriptorFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */