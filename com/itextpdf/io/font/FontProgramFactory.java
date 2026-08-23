/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.constants.StandardFonts;
/*     */ import com.itextpdf.io.font.woff2.FontCompressionException;
/*     */ import com.itextpdf.io.font.woff2.Woff2Converter;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontProgramFactory
/*     */ {
/*     */   private static boolean DEFAULT_CACHED = true;
/*  67 */   private static FontRegisterProvider fontRegisterProvider = new FontRegisterProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontProgram createFont() throws IOException {
/*  79 */     return createFont("Helvetica");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontProgram createFont(String fontProgram) throws IOException {
/*  98 */     return createFont(fontProgram, (byte[])null, DEFAULT_CACHED);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontProgram createFont(String fontProgram, boolean cached) throws IOException {
/* 118 */     return createFont(fontProgram, (byte[])null, cached);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontProgram createFont(byte[] fontProgram) throws IOException {
/* 137 */     return createFont((String)null, fontProgram, DEFAULT_CACHED);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontProgram createFont(byte[] fontProgram, boolean cached) throws IOException {
/* 157 */     return createFont((String)null, fontProgram, cached);
/*     */   }
/*     */   
/*     */   private static FontProgram createFont(String name, byte[] fontProgram, boolean cached) throws IOException {
/* 161 */     String baseName = FontProgram.trimFontStyle(name);
/*     */ 
/*     */     
/* 164 */     boolean isBuiltinFonts14 = StandardFonts.isStandardFont(name);
/* 165 */     boolean isCidFont = (!isBuiltinFonts14 && FontCache.isPredefinedCidFont(baseName));
/*     */ 
/*     */     
/* 168 */     FontCacheKey fontKey = null;
/* 169 */     if (cached) {
/* 170 */       fontKey = createFontCacheKey(name, fontProgram);
/* 171 */       FontProgram fontFound = FontCache.getFont(fontKey);
/* 172 */       if (fontFound != null) {
/* 173 */         return fontFound;
/*     */       }
/*     */     } 
/*     */     
/* 177 */     FontProgram fontBuilt = null;
/* 178 */     if (name == null) {
/* 179 */       if (fontProgram != null) {
/*     */         try {
/* 181 */           if (WoffConverter.isWoffFont(fontProgram)) {
/* 182 */             fontProgram = WoffConverter.convert(fontProgram);
/* 183 */           } else if (Woff2Converter.isWoff2Font(fontProgram)) {
/* 184 */             fontProgram = Woff2Converter.convert(fontProgram);
/*     */           } 
/* 186 */           fontBuilt = new TrueTypeFont(fontProgram);
/* 187 */         } catch (Exception exception) {}
/*     */         
/* 189 */         if (fontBuilt == null) {
/*     */           try {
/* 191 */             fontBuilt = new Type1Font(null, null, fontProgram, null);
/* 192 */           } catch (Exception exception) {}
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 197 */       String fontFileExtension = null;
/* 198 */       int extensionBeginIndex = baseName.lastIndexOf('.');
/* 199 */       if (extensionBeginIndex > 0) {
/* 200 */         fontFileExtension = baseName.substring(extensionBeginIndex).toLowerCase();
/*     */       }
/* 202 */       if (isBuiltinFonts14 || ".afm".equals(fontFileExtension) || ".pfm".equals(fontFileExtension)) {
/* 203 */         fontBuilt = new Type1Font(name, null, null, null);
/* 204 */       } else if (isCidFont) {
/* 205 */         fontBuilt = new CidFont(name, FontCache.getCompatibleCmaps(baseName));
/* 206 */       } else if (".ttf".equals(fontFileExtension) || ".otf".equals(fontFileExtension)) {
/* 207 */         if (fontProgram != null) {
/* 208 */           fontBuilt = new TrueTypeFont(fontProgram);
/*     */         } else {
/* 210 */           fontBuilt = new TrueTypeFont(name);
/*     */         } 
/* 212 */       } else if (".woff".equals(fontFileExtension) || ".woff2".equals(fontFileExtension)) {
/* 213 */         if (fontProgram == null) {
/* 214 */           fontProgram = readFontBytesFromPath(baseName);
/*     */         }
/* 216 */         if (".woff".equals(fontFileExtension)) {
/*     */           try {
/* 218 */             fontProgram = WoffConverter.convert(fontProgram);
/* 219 */           } catch (IllegalArgumentException woffException) {
/* 220 */             throw new IOException("Invalid WOFF font file.", woffException);
/*     */           } 
/*     */         } else {
/*     */ 
/*     */           
/*     */           try {
/* 226 */             fontProgram = Woff2Converter.convert(fontProgram);
/* 227 */           } catch (FontCompressionException woff2Exception) {
/* 228 */             throw new IOException("Invalid WOFF2 font file.", woff2Exception);
/*     */           } 
/*     */         } 
/* 231 */         fontBuilt = new TrueTypeFont(fontProgram);
/*     */       } else {
/* 233 */         int ttcSplit = baseName.toLowerCase().indexOf(".ttc,");
/* 234 */         if (ttcSplit > 0) {
/*     */           
/*     */           try {
/*     */             
/* 238 */             String ttcName = baseName.substring(0, ttcSplit + 4);
/*     */ 
/*     */             
/* 241 */             int ttcIndex = Integer.parseInt(baseName.substring(ttcSplit + 5));
/* 242 */             fontBuilt = new TrueTypeFont(ttcName, ttcIndex);
/* 243 */           } catch (NumberFormatException nfe) {
/* 244 */             throw new IOException(nfe.getMessage(), nfe);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 249 */     if (fontBuilt == null) {
/* 250 */       if (name != null) {
/* 251 */         throw (new IOException("Type of font {0} is not recognized.")).setMessageParams(new Object[] { name });
/*     */       }
/* 253 */       throw new IOException("Type of font is not recognized.");
/*     */     } 
/*     */     
/* 256 */     return cached ? FontCache.saveFont(fontBuilt, fontKey) : fontBuilt;
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
/*     */   public static FontProgram createType1Font(byte[] afm, byte[] pfb) throws IOException {
/* 268 */     return createType1Font(afm, pfb, DEFAULT_CACHED);
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
/*     */   public static FontProgram createType1Font(byte[] afm, byte[] pfb, boolean cached) throws IOException {
/* 281 */     return createType1Font(null, null, afm, pfb, cached);
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
/*     */   public static FontProgram createType1Font(String metricsPath, String binaryPath) throws IOException {
/* 293 */     return createType1Font(metricsPath, binaryPath, DEFAULT_CACHED);
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
/*     */   public static FontProgram createType1Font(String metricsPath, String binaryPath, boolean cached) throws IOException {
/* 306 */     return createType1Font(metricsPath, binaryPath, null, null, cached);
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
/*     */ 
/*     */   
/*     */   public static FontProgram createFont(String ttc, int ttcIndex, boolean cached) throws IOException {
/* 321 */     FontCacheKey fontCacheKey = FontCacheKey.create(ttc, ttcIndex);
/* 322 */     if (cached) {
/* 323 */       FontProgram fontFound = FontCache.getFont(fontCacheKey);
/* 324 */       if (fontFound != null) {
/* 325 */         return fontFound;
/*     */       }
/*     */     } 
/* 328 */     FontProgram fontBuilt = new TrueTypeFont(ttc, ttcIndex);
/* 329 */     return cached ? FontCache.saveFont(fontBuilt, fontCacheKey) : fontBuilt;
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
/*     */ 
/*     */   
/*     */   public static FontProgram createFont(byte[] ttc, int ttcIndex, boolean cached) throws IOException {
/* 344 */     FontCacheKey fontKey = FontCacheKey.create(ttc, ttcIndex);
/* 345 */     if (cached) {
/* 346 */       FontProgram fontFound = FontCache.getFont(fontKey);
/* 347 */       if (fontFound != null) {
/* 348 */         return fontFound;
/*     */       }
/*     */     } 
/* 351 */     FontProgram fontBuilt = new TrueTypeFont(ttc, ttcIndex);
/* 352 */     return cached ? FontCache.saveFont(fontBuilt, fontKey) : fontBuilt;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontProgram createRegisteredFont(String fontName, int style, boolean cached) throws IOException {
/* 368 */     return fontRegisterProvider.getFont(fontName, style, cached);
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
/*     */ 
/*     */   
/*     */   public static FontProgram createRegisteredFont(String fontName, int style) throws IOException {
/* 383 */     return fontRegisterProvider.getFont(fontName, style);
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
/*     */   public static FontProgram createRegisteredFont(String fontName) throws IOException {
/* 395 */     return fontRegisterProvider.getFont(fontName, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFontFamily(String familyName, String fullName, String path) {
/* 406 */     fontRegisterProvider.registerFontFamily(familyName, fullName, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFont(String path) {
/* 417 */     registerFont(path, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFont(String path, String alias) {
/* 427 */     fontRegisterProvider.registerFont(path, alias);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int registerFontDirectory(String dir) {
/* 437 */     return fontRegisterProvider.registerFontDirectory(dir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int registerSystemFontDirectories() {
/* 447 */     return fontRegisterProvider.registerSystemFontDirectories();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> getRegisteredFonts() {
/* 456 */     return fontRegisterProvider.getRegisteredFonts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> getRegisteredFontFamilies() {
/* 465 */     return fontRegisterProvider.getRegisteredFontFamilies();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRegisteredFont(String fontName) {
/* 475 */     return fontRegisterProvider.isRegisteredFont(fontName);
/*     */   }
/*     */ 
/*     */   
/*     */   private static FontProgram createType1Font(String metricsPath, String binaryPath, byte[] afm, byte[] pfb, boolean cached) throws IOException {
/* 480 */     FontCacheKey fontKey = null;
/* 481 */     if (cached) {
/* 482 */       fontKey = createFontCacheKey(metricsPath, afm);
/* 483 */       FontProgram fontProgram1 = FontCache.getFont(fontKey);
/* 484 */       if (fontProgram1 != null) {
/* 485 */         return fontProgram1;
/*     */       }
/*     */     } 
/*     */     
/* 489 */     FontProgram fontProgram = new Type1Font(metricsPath, binaryPath, afm, pfb);
/* 490 */     return cached ? FontCache.saveFont(fontProgram, fontKey) : fontProgram;
/*     */   }
/*     */   
/*     */   private static FontCacheKey createFontCacheKey(String name, byte[] fontProgram) {
/*     */     FontCacheKey key;
/* 495 */     if (name != null) {
/* 496 */       key = FontCacheKey.create(name);
/*     */     } else {
/* 498 */       key = FontCacheKey.create(fontProgram);
/*     */     } 
/* 500 */     return key;
/*     */   }
/*     */   public static void clearRegisteredFonts() {
/* 503 */     fontRegisterProvider.clearRegisteredFonts();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearRegisteredFontFamilies() {
/* 508 */     fontRegisterProvider.clearRegisteredFontFamilies();
/*     */   }
/*     */   static byte[] readFontBytesFromPath(String path) throws IOException {
/* 511 */     RandomAccessFileOrArray raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createBestSource(path));
/* 512 */     int bufLen = (int)raf.length();
/* 513 */     if (bufLen < raf.length()) {
/* 514 */       throw new IOException(MessageFormatUtil.format("Source data from \"{0}\" is bigger than byte array can hold.", new Object[] { path }));
/*     */     }
/* 516 */     byte[] buf = new byte[bufLen];
/* 517 */     raf.readFully(buf);
/* 518 */     return buf;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontProgramFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */