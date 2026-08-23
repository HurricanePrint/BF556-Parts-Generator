/*     */ package com.itextpdf.layout.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.util.FileUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontSet
/*     */ {
/*  74 */   private static final AtomicLong lastId = new AtomicLong();
/*     */ 
/*     */ 
/*     */   
/*  78 */   private final Set<FontInfo> fonts = new LinkedHashSet<>();
/*  79 */   private final Map<FontInfo, FontProgram> fontPrograms = new HashMap<>();
/*     */ 
/*     */   
/*     */   private final long id;
/*     */ 
/*     */   
/*     */   public FontSet() {
/*  86 */     this.id = lastId.incrementAndGet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addDirectory(String dir, boolean scanSubdirectories) {
/*  97 */     int count = 0;
/*  98 */     String[] files = FileUtil.listFilesInDirectory(dir, scanSubdirectories);
/*  99 */     if (files == null)
/* 100 */       return 0; 
/* 101 */     for (String file : files) {
/*     */       try {
/* 103 */         String suffix = (file.length() < 4) ? null : file.substring(file.length() - 4).toLowerCase();
/* 104 */         if (".afm".equals(suffix) || ".pfm".equals(suffix)) {
/*     */           
/* 106 */           String pfb = file.substring(0, file.length() - 4) + ".pfb";
/* 107 */           if (FileUtil.fileExists(pfb) && addFont(file)) {
/* 108 */             count++;
/*     */           }
/* 110 */         } else if ((".ttf".equals(suffix) || ".otf".equals(suffix) || ".ttc".equals(suffix)) && 
/* 111 */           addFont(file)) {
/* 112 */           count++;
/*     */         } 
/* 114 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 117 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addDirectory(String dir) {
/* 127 */     return addDirectory(dir, false);
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
/*     */   public boolean addFont(FontProgram fontProgram, String encoding, String alias, Range unicodeRange) {
/* 144 */     if (fontProgram == null) {
/* 145 */       return false;
/*     */     }
/* 147 */     if (fontProgram instanceof com.itextpdf.kernel.font.Type3Font) {
/* 148 */       Logger logger = LoggerFactory.getLogger(FontSet.class);
/* 149 */       logger.error("Type 3 font cannot be added to FontSet. Custom FontProvider class may be created for this purpose.");
/* 150 */       return false;
/*     */     } 
/* 152 */     FontInfo fi = FontInfo.create(fontProgram, encoding, alias, unicodeRange);
/* 153 */     if (addFont(fi)) {
/* 154 */       this.fontPrograms.put(fi, fontProgram);
/* 155 */       return true;
/*     */     } 
/* 157 */     return false;
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
/*     */   public boolean addFont(FontProgram fontProgram, String encoding, String alias) {
/* 174 */     return addFont(fontProgram, encoding, alias, (Range)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addFont(FontProgram fontProgram, String encoding) {
/* 185 */     return addFont(fontProgram, encoding, (String)null);
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
/*     */   public boolean addFont(String fontPath, String encoding, String alias, Range unicodeRange) {
/* 204 */     return addFont(FontInfo.create(fontPath, encoding, alias, unicodeRange));
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
/*     */   public boolean addFont(String fontPath, String encoding, String alias) {
/* 222 */     return addFont(fontPath, encoding, alias, (Range)null);
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
/*     */   public boolean addFont(String fontPath, String encoding) {
/* 235 */     return addFont(FontInfo.create(fontPath, encoding, (String)null, (Range)null));
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
/*     */   public boolean addFont(byte[] fontData, String encoding, String alias, Range unicodeRange) {
/* 254 */     return addFont(FontInfo.create(fontData, encoding, alias, unicodeRange));
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
/*     */   public boolean addFont(byte[] fontData, String encoding, String alias) {
/* 272 */     return addFont(fontData, encoding, alias, (Range)null);
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
/*     */   public boolean addFont(byte[] fontData, String encoding) {
/* 285 */     return addFont(FontInfo.create(fontData, encoding, (String)null, (Range)null));
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
/*     */   public boolean addFont(String fontPath) {
/* 297 */     return addFont(fontPath, (String)null, (String)null);
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
/*     */   public boolean addFont(byte[] fontData) {
/* 309 */     return addFont(fontData, (String)null, (String)null);
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
/*     */   public boolean addFont(FontInfo fontInfo, String alias, Range unicodeRange) {
/* 325 */     return addFont(FontInfo.create(fontInfo, alias, unicodeRange));
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
/*     */   public boolean addFont(FontInfo fontInfo, String alias) {
/* 340 */     return addFont(fontInfo, alias, (Range)null);
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
/*     */   public final boolean addFont(FontInfo fontInfo) {
/* 355 */     if (fontInfo != null && !this.fonts.contains(fontInfo)) {
/*     */ 
/*     */ 
/*     */       
/* 359 */       this.fonts.add(fontInfo);
/* 360 */       return true;
/*     */     } 
/* 362 */     return false;
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
/*     */   public boolean contains(String fontName) {
/* 374 */     if (fontName == null || fontName.length() == 0) {
/* 375 */       return false;
/*     */     }
/* 377 */     fontName = fontName.toLowerCase();
/*     */     
/* 379 */     for (FontInfo fi : getFonts()) {
/* 380 */       if (fontName.equals(fi.getDescriptor().getFullNameLowerCase()) || fontName
/* 381 */         .equals(fi.getDescriptor().getFontNameLowerCase())) {
/* 382 */         return true;
/*     */       }
/*     */     } 
/* 385 */     return false;
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
/*     */   public Collection<FontInfo> get(String fontName) {
/* 397 */     if (fontName == null || fontName.length() == 0) {
/* 398 */       return Collections.emptyList();
/*     */     }
/* 400 */     fontName = fontName.toLowerCase();
/* 401 */     List<FontInfo> list = new ArrayList<>();
/* 402 */     for (FontInfo fi : getFonts()) {
/* 403 */       if (fontName.equals(fi.getDescriptor().getFullNameLowerCase()) || fontName
/* 404 */         .equals(fi.getDescriptor().getFontNameLowerCase())) {
/* 405 */         list.add(fi);
/*     */       }
/*     */     } 
/* 408 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<FontInfo> getFonts() {
/* 419 */     return getFonts(null);
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
/*     */   public Collection<FontInfo> getFonts(FontSet additionalFonts) {
/* 431 */     return new FontSetCollection(this.fonts, (additionalFonts != null) ? additionalFonts.fonts : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 440 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 449 */     return this.fonts.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long getId() {
/* 455 */     return this.id;
/*     */   }
/*     */   
/*     */   FontProgram getFontProgram(FontInfo fontInfo) {
/* 459 */     return this.fontPrograms.get(fontInfo);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */