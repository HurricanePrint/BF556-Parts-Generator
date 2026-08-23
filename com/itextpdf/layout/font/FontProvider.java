/*     */ package com.itextpdf.layout.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.FontProgramFactory;
/*     */ import com.itextpdf.io.util.FileUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.font.PdfFontFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontProvider
/*     */ {
/*     */   private static final String DEFAULT_FONT_FAMILY = "Helvetica";
/*     */   private final FontSet fontSet;
/*     */   private final FontSelectorCache fontSelectorCache;
/*     */   protected final String defaultFontFamily;
/*     */   protected final Map<FontInfo, PdfFont> pdfFonts;
/*     */   
/*     */   public FontProvider(FontSet fontSet) {
/*  96 */     this(fontSet, "Helvetica");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProvider() {
/* 103 */     this(new FontSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProvider(String defaultFontFamily) {
/* 112 */     this(new FontSet(), defaultFontFamily);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProvider(FontSet fontSet, String defaultFontFamily) {
/* 122 */     this.fontSet = (fontSet != null) ? fontSet : new FontSet();
/* 123 */     this.pdfFonts = new HashMap<>();
/* 124 */     this.fontSelectorCache = new FontSelectorCache(this.fontSet);
/* 125 */     this.defaultFontFamily = defaultFontFamily;
/*     */   }
/*     */   
/*     */   public boolean addFont(FontProgram fontProgram, String encoding, Range unicodeRange) {
/* 129 */     return this.fontSet.addFont(fontProgram, encoding, (String)null, unicodeRange);
/*     */   }
/*     */   
/*     */   public boolean addFont(FontProgram fontProgram, String encoding) {
/* 133 */     return addFont(fontProgram, encoding, (Range)null);
/*     */   }
/*     */   
/*     */   public boolean addFont(FontProgram fontProgram) {
/* 137 */     return addFont(fontProgram, getDefaultEncoding(fontProgram));
/*     */   }
/*     */   
/*     */   public boolean addFont(String fontPath, String encoding, Range unicodeRange) {
/* 141 */     return this.fontSet.addFont(fontPath, encoding, (String)null, unicodeRange);
/*     */   }
/*     */   
/*     */   public boolean addFont(String fontPath, String encoding) {
/* 145 */     return addFont(fontPath, encoding, (Range)null);
/*     */   }
/*     */   
/*     */   public boolean addFont(String fontPath) {
/* 149 */     return addFont(fontPath, (String)null);
/*     */   }
/*     */   
/*     */   public boolean addFont(byte[] fontData, String encoding, Range unicodeRange) {
/* 153 */     return this.fontSet.addFont(fontData, encoding, (String)null, unicodeRange);
/*     */   }
/*     */   
/*     */   public boolean addFont(byte[] fontData, String encoding) {
/* 157 */     return addFont(fontData, encoding, (Range)null);
/*     */   }
/*     */   
/*     */   public boolean addFont(byte[] fontData) {
/* 161 */     return addFont(fontData, (String)null);
/*     */   }
/*     */   
/*     */   public int addDirectory(String dir) {
/* 165 */     return this.fontSet.addDirectory(dir);
/*     */   }
/*     */   
/*     */   public int addSystemFonts() {
/* 169 */     int count = 0;
/*     */     
/* 171 */     String[] withSubDirs = { FileUtil.getFontsDir(), "/usr/share/X11/fonts", "/usr/X/lib/X11/fonts", "/usr/openwin/lib/X11/fonts", "/usr/share/fonts", "/usr/X11R6/lib/X11/fonts" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     for (String directory : withSubDirs) {
/* 179 */       count += this.fontSet.addDirectory(directory, true);
/*     */     }
/*     */     
/* 182 */     String[] withoutSubDirs = { "/Library/Fonts", "/System/Library/Fonts" };
/*     */ 
/*     */ 
/*     */     
/* 186 */     for (String directory : withoutSubDirs) {
/* 187 */       count += this.fontSet.addDirectory(directory, false);
/*     */     }
/*     */     
/* 190 */     return count;
/*     */   }
/*     */   
/*     */   public int addStandardPdfFonts() {
/* 194 */     addFont("Courier");
/* 195 */     addFont("Courier-Bold");
/* 196 */     addFont("Courier-BoldOblique");
/* 197 */     addFont("Courier-Oblique");
/* 198 */     addFont("Helvetica");
/* 199 */     addFont("Helvetica-Bold");
/* 200 */     addFont("Helvetica-BoldOblique");
/* 201 */     addFont("Helvetica-Oblique");
/* 202 */     addFont("Symbol");
/* 203 */     addFont("Times-Roman");
/* 204 */     addFont("Times-Bold");
/* 205 */     addFont("Times-BoldItalic");
/* 206 */     addFont("Times-Italic");
/* 207 */     addFont("ZapfDingbats");
/* 208 */     return 14;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontSet getFontSet() {
/* 216 */     return this.fontSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultFontFamily() {
/* 224 */     return this.defaultFontFamily;
/*     */   }
/*     */   
/*     */   public String getDefaultEncoding(FontProgram fontProgram) {
/* 228 */     if (fontProgram instanceof com.itextpdf.io.font.Type1Font) {
/* 229 */       return "Cp1252";
/*     */     }
/* 231 */     return "Identity-H";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getDefaultCacheFlag() {
/* 236 */     return true;
/*     */   }
/*     */   
/*     */   public boolean getDefaultEmbeddingFlag() {
/* 240 */     return true;
/*     */   }
/*     */   
/*     */   public FontSelectorStrategy getStrategy(String text, List<String> fontFamilies, FontCharacteristics fc, FontSet additionalFonts) {
/* 244 */     return new ComplexFontSelectorStrategy(text, getFontSelector(fontFamilies, fc, additionalFonts), this, additionalFonts);
/*     */   }
/*     */   
/*     */   public FontSelectorStrategy getStrategy(String text, List<String> fontFamilies, FontCharacteristics fc) {
/* 248 */     return getStrategy(text, fontFamilies, fc, null);
/*     */   }
/*     */   
/*     */   public FontSelectorStrategy getStrategy(String text, List<String> fontFamilies) {
/* 252 */     return getStrategy(text, fontFamilies, null);
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
/*     */   public final FontSelector getFontSelector(List<String> fontFamilies, FontCharacteristics fc) {
/* 265 */     FontSelectorKey key = new FontSelectorKey(fontFamilies, fc);
/* 266 */     FontSelector fontSelector = this.fontSelectorCache.get(key);
/* 267 */     if (fontSelector == null) {
/* 268 */       fontSelector = createFontSelector(this.fontSet.getFonts(), fontFamilies, fc);
/* 269 */       this.fontSelectorCache.put(key, fontSelector);
/*     */     } 
/* 271 */     return fontSelector;
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
/*     */   public final FontSelector getFontSelector(List<String> fontFamilies, FontCharacteristics fc, FontSet additionalFonts) {
/* 285 */     FontSelectorKey key = new FontSelectorKey(fontFamilies, fc);
/* 286 */     FontSelector fontSelector = this.fontSelectorCache.get(key, additionalFonts);
/* 287 */     if (fontSelector == null) {
/* 288 */       fontSelector = createFontSelector(this.fontSet.getFonts(additionalFonts), fontFamilies, fc);
/* 289 */       this.fontSelectorCache.put(key, fontSelector, additionalFonts);
/*     */     } 
/* 291 */     return fontSelector;
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
/*     */   protected FontSelector createFontSelector(Collection<FontInfo> fonts, List<String> fontFamilies, FontCharacteristics fc) {
/* 306 */     List<String> fontFamiliesToBeProcessed = new ArrayList<>(fontFamilies);
/* 307 */     fontFamiliesToBeProcessed.add(this.defaultFontFamily);
/* 308 */     return new FontSelector(fonts, fontFamiliesToBeProcessed, fc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFont getPdfFont(FontInfo fontInfo) {
/* 318 */     return getPdfFont(fontInfo, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFont getPdfFont(FontInfo fontInfo, FontSet additionalFonts) {
/*     */     PdfFont pdfFont;
/* 329 */     if (this.pdfFonts.containsKey(fontInfo)) {
/* 330 */       return this.pdfFonts.get(fontInfo);
/*     */     }
/* 332 */     FontProgram fontProgram = null;
/* 333 */     if (additionalFonts != null) {
/* 334 */       fontProgram = additionalFonts.getFontProgram(fontInfo);
/*     */     }
/* 336 */     if (fontProgram == null) {
/* 337 */       fontProgram = this.fontSet.getFontProgram(fontInfo);
/*     */     }
/*     */     
/*     */     try {
/* 341 */       if (fontProgram == null) {
/* 342 */         if (fontInfo.getFontData() != null) {
/* 343 */           fontProgram = FontProgramFactory.createFont(fontInfo.getFontData(), getDefaultCacheFlag());
/*     */         } else {
/* 345 */           fontProgram = FontProgramFactory.createFont(fontInfo.getFontName(), getDefaultCacheFlag());
/*     */         } 
/*     */       }
/* 348 */       String encoding = fontInfo.getEncoding();
/* 349 */       if (encoding == null || encoding.length() == 0) {
/* 350 */         encoding = getDefaultEncoding(fontProgram);
/*     */       }
/*     */       
/* 353 */       pdfFont = PdfFontFactory.createFont(fontProgram, encoding, getDefaultEmbeddingFlag());
/*     */     }
/* 355 */     catch (IOException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 365 */       throw new PdfException("I/O exception while creating Font", e);
/*     */     } 
/*     */     
/* 368 */     this.pdfFonts.put(fontInfo, pdfFont);
/* 369 */     return pdfFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 377 */     this.pdfFonts.clear();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */