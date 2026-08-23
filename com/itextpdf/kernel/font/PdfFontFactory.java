/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.CidFont;
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.FontProgramFactory;
/*     */ import com.itextpdf.io.font.TrueTypeFont;
/*     */ import com.itextpdf.io.font.Type1Font;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PdfFontFactory
/*     */ {
/*  73 */   private static String DEFAULT_ENCODING = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean DEFAULT_EMBEDDING = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean DEFAULT_CACHED = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfFont createFont() throws IOException {
/*  93 */     return createFont("Helvetica", DEFAULT_ENCODING);
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
/*     */   public static PdfFont createFont(PdfDictionary fontDictionary) {
/* 111 */     if (checkFontDictionary(fontDictionary, PdfName.Type1, false))
/* 112 */       return new PdfType1Font(fontDictionary); 
/* 113 */     if (checkFontDictionary(fontDictionary, PdfName.Type0, false))
/* 114 */       return new PdfType0Font(fontDictionary); 
/* 115 */     if (checkFontDictionary(fontDictionary, PdfName.TrueType, false))
/* 116 */       return new PdfTrueTypeFont(fontDictionary); 
/* 117 */     if (checkFontDictionary(fontDictionary, PdfName.Type3, false))
/* 118 */       return new PdfType3Font(fontDictionary); 
/* 119 */     if (checkFontDictionary(fontDictionary, PdfName.MMType1, false))
/*     */     {
/* 121 */       return new PdfType1Font(fontDictionary);
/*     */     }
/* 123 */     throw new PdfException("Dictionary doesn't have supported font data.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfFont createFont(String fontProgram, String encoding, PdfDocument cacheTo) throws IOException {
/* 129 */     if (cacheTo != null) {
/* 130 */       PdfFont pdfFont1 = cacheTo.findFont(fontProgram, encoding);
/* 131 */       if (pdfFont1 != null) {
/* 132 */         return pdfFont1;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     PdfFont pdfFont = createFont(fontProgram, encoding);
/* 137 */     if (cacheTo != null) pdfFont.makeIndirect(cacheTo);
/*     */     
/* 139 */     return pdfFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfFont createFont(String fontProgram) throws IOException {
/* 150 */     return createFont(fontProgram, DEFAULT_ENCODING);
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
/*     */   public static PdfFont createFont(String fontProgram, String encoding) throws IOException {
/* 162 */     return createFont(fontProgram, encoding, DEFAULT_EMBEDDING);
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
/*     */   public static PdfFont createTtcFont(byte[] ttc, int ttcIndex, String encoding, boolean embedded, boolean cached) throws IOException {
/* 177 */     FontProgram fontProgram = FontProgramFactory.createFont(ttc, ttcIndex, cached);
/* 178 */     return createFont(fontProgram, encoding, embedded);
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
/*     */   public static PdfFont createTtcFont(String ttc, int ttcIndex, String encoding, boolean embedded, boolean cached) throws IOException {
/* 194 */     FontProgram fontProgram = FontProgramFactory.createFont(ttc, ttcIndex, cached);
/* 195 */     return createFont(fontProgram, encoding, embedded);
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
/*     */   public static PdfFont createFont(String fontProgram, boolean embedded) throws IOException {
/* 207 */     return createFont(fontProgram, DEFAULT_ENCODING, embedded);
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
/*     */   public static PdfFont createFont(String fontProgram, String encoding, boolean embedded) throws IOException {
/* 220 */     return createFont(fontProgram, encoding, embedded, DEFAULT_CACHED);
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
/*     */   public static PdfFont createFont(String fontProgram, String encoding, boolean embedded, boolean cached) throws IOException {
/* 234 */     FontProgram fp = FontProgramFactory.createFont(fontProgram, cached);
/* 235 */     return createFont(fp, encoding, embedded);
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
/*     */   public static PdfFont createFont(FontProgram fontProgram, String encoding, boolean embedded) {
/* 247 */     if (fontProgram == null)
/* 248 */       return null; 
/* 249 */     if (fontProgram instanceof Type1Font)
/* 250 */       return new PdfType1Font((Type1Font)fontProgram, encoding, embedded); 
/* 251 */     if (fontProgram instanceof TrueTypeFont) {
/* 252 */       if ("Identity-H".equals(encoding) || "Identity-V".equals(encoding)) {
/* 253 */         return new PdfType0Font((TrueTypeFont)fontProgram, encoding);
/*     */       }
/* 255 */       return new PdfTrueTypeFont((TrueTypeFont)fontProgram, encoding, embedded);
/*     */     } 
/* 257 */     if (fontProgram instanceof CidFont) {
/* 258 */       if (((CidFont)fontProgram).compatibleWith(encoding)) {
/* 259 */         return new PdfType0Font((CidFont)fontProgram, encoding);
/*     */       }
/* 261 */       return null;
/*     */     } 
/*     */     
/* 264 */     return null;
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
/*     */   public static PdfFont createFont(FontProgram fontProgram, String encoding) {
/* 276 */     return createFont(fontProgram, encoding, DEFAULT_EMBEDDING);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfFont createFont(FontProgram fontProgram) {
/* 286 */     return createFont(fontProgram, DEFAULT_ENCODING);
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
/*     */   public static PdfFont createFont(byte[] fontProgram, String encoding) throws IOException {
/* 298 */     return createFont(fontProgram, encoding, DEFAULT_EMBEDDING);
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
/*     */   public static PdfFont createFont(byte[] fontProgram, boolean embedded) throws IOException {
/* 310 */     return createFont(fontProgram, (String)null, embedded);
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
/*     */   public static PdfFont createFont(byte[] fontProgram, String encoding, boolean embedded) throws IOException {
/* 323 */     return createFont(fontProgram, encoding, embedded, DEFAULT_CACHED);
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
/*     */   public static PdfFont createFont(byte[] fontProgram, String encoding, boolean embedded, boolean cached) throws IOException {
/* 337 */     FontProgram fp = FontProgramFactory.createFont(fontProgram, cached);
/* 338 */     return createFont(fp, encoding, embedded);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfType3Font createType3Font(PdfDocument document, boolean colorized) {
/* 349 */     return new PdfType3Font(document, colorized);
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
/*     */   public static PdfType3Font createType3Font(PdfDocument document, String fontName, String fontFamily, boolean colorized) {
/* 362 */     return new PdfType3Font(document, fontName, fontFamily, colorized);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfFont createRegisteredFont(String fontName, String encoding, boolean embedded, int style, boolean cached) throws IOException {
/* 385 */     FontProgram fp = FontProgramFactory.createRegisteredFont(fontName, style, cached);
/* 386 */     return createFont(fp, encoding, embedded);
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
/*     */ 
/*     */   
/*     */   public static PdfFont createRegisteredFont(String fontName, String encoding, boolean embedded, boolean cached) throws IOException {
/* 408 */     return createRegisteredFont(fontName, encoding, embedded, -1, cached);
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
/*     */   
/*     */   public static PdfFont createRegisteredFont(String fontName, String encoding, boolean embedded) throws IOException {
/* 429 */     return createRegisteredFont(fontName, encoding, embedded, -1);
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
/*     */ 
/*     */   
/*     */   public static PdfFont createRegisteredFont(String fontName, String encoding, boolean embedded, int style) throws IOException {
/* 451 */     return createRegisteredFont(fontName, encoding, embedded, style, DEFAULT_CACHED);
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
/*     */   public static PdfFont createRegisteredFont(String fontName, String encoding) throws IOException {
/* 471 */     return createRegisteredFont(fontName, encoding, false, -1);
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
/*     */   public static PdfFont createRegisteredFont(String fontName) throws IOException {
/* 490 */     return createRegisteredFont(fontName, (String)null, false, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFamily(String familyName, String fullName, String path) {
/* 501 */     FontProgramFactory.registerFontFamily(familyName, fullName, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(String path) {
/* 512 */     register(path, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(String path, String alias) {
/* 522 */     FontProgramFactory.registerFont(path, alias);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int registerDirectory(String dirPath) {
/* 532 */     return FontProgramFactory.registerFontDirectory(dirPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int registerSystemDirectories() {
/* 542 */     return FontProgramFactory.registerSystemFontDirectories();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> getRegisteredFonts() {
/* 551 */     return FontProgramFactory.getRegisteredFonts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> getRegisteredFamilies() {
/* 560 */     return FontProgramFactory.getRegisteredFontFamilies();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRegistered(String fontName) {
/* 570 */     return FontProgramFactory.isRegisteredFont(fontName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkFontDictionary(PdfDictionary fontDic, PdfName fontType, boolean isException) {
/* 579 */     if (fontDic == null || fontDic.get(PdfName.Subtype) == null || 
/* 580 */       !fontDic.get(PdfName.Subtype).equals(fontType)) {
/* 581 */       if (isException) {
/* 582 */         throw (new PdfException("Dictionary doesn't have {0} font data.")).setMessageParams(new Object[] { fontType.getValue() });
/*     */       }
/* 584 */       return false;
/*     */     } 
/* 586 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/PdfFontFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */