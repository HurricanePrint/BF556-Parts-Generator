/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
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
/*     */ public abstract class FontProgram
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3488910249070253659L;
/*     */   public static final int DEFAULT_WIDTH = 1000;
/*     */   public static final int UNITS_NORMALIZATION = 1000;
/*  64 */   protected Map<Integer, Glyph> codeToGlyph = new HashMap<>();
/*  65 */   protected Map<Integer, Glyph> unicodeToGlyph = new HashMap<>();
/*     */   
/*     */   protected boolean isFontSpecific;
/*     */   protected FontNames fontNames;
/*  69 */   protected FontMetrics fontMetrics = new FontMetrics();
/*  70 */   protected FontIdentification fontIdentification = new FontIdentification();
/*     */ 
/*     */ 
/*     */   
/*     */   protected int avgWidth;
/*     */ 
/*     */ 
/*     */   
/*  78 */   protected String encodingScheme = "FontSpecific";
/*     */   
/*     */   protected String registry;
/*     */   
/*     */   public int countOfGlyphs() {
/*  83 */     return Math.max(this.codeToGlyph.size(), this.unicodeToGlyph.size());
/*     */   }
/*     */   
/*     */   public FontNames getFontNames() {
/*  87 */     return this.fontNames;
/*     */   }
/*     */   
/*     */   public FontMetrics getFontMetrics() {
/*  91 */     return this.fontMetrics;
/*     */   }
/*     */   
/*     */   public FontIdentification getFontIdentification() {
/*  95 */     return this.fontIdentification;
/*     */   }
/*     */   
/*     */   public String getRegistry() {
/*  99 */     return this.registry;
/*     */   }
/*     */   
/*     */   public abstract int getPdfFontFlags();
/*     */   
/*     */   public boolean isFontSpecific() {
/* 105 */     return this.isFontSpecific;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(int unicode) {
/* 115 */     Glyph glyph = getGlyph(unicode);
/* 116 */     return (glyph != null) ? glyph.getWidth() : 0;
/*     */   }
/*     */   
/*     */   public int getAvgWidth() {
/* 120 */     return this.avgWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getCharBBox(int unicode) {
/* 130 */     Glyph glyph = getGlyph(unicode);
/* 131 */     return (glyph != null) ? glyph.getBbox() : null;
/*     */   }
/*     */   
/*     */   public Glyph getGlyph(int unicode) {
/* 135 */     return this.unicodeToGlyph.get(Integer.valueOf(unicode));
/*     */   }
/*     */ 
/*     */   
/*     */   public Glyph getGlyphByCode(int charCode) {
/* 140 */     return this.codeToGlyph.get(Integer.valueOf(charCode));
/*     */   }
/*     */   
/*     */   public boolean hasKernPairs() {
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getKerning(int first, int second) {
/* 155 */     return getKerning(this.unicodeToGlyph.get(Integer.valueOf(first)), this.unicodeToGlyph.get(Integer.valueOf(second)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getKerning(Glyph paramGlyph1, Glyph paramGlyph2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBuiltWith(String fontName) {
/* 175 */     return false;
/*     */   }
/*     */   
/*     */   protected void setRegistry(String registry) {
/* 179 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String trimFontStyle(String name) {
/* 189 */     if (name == null) {
/* 190 */       return null;
/*     */     }
/* 192 */     if (name.endsWith(",Bold"))
/* 193 */       return name.substring(0, name.length() - 5); 
/* 194 */     if (name.endsWith(",Italic"))
/* 195 */       return name.substring(0, name.length() - 7); 
/* 196 */     if (name.endsWith(",BoldItalic")) {
/* 197 */       return name.substring(0, name.length() - 11);
/*     */     }
/* 199 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setTypoAscender(int ascender) {
/* 204 */     this.fontMetrics.setTypoAscender(ascender);
/*     */   }
/*     */   
/*     */   protected void setTypoDescender(int descender) {
/* 208 */     this.fontMetrics.setTypoDescender(descender);
/*     */   }
/*     */   
/*     */   protected void setCapHeight(int capHeight) {
/* 212 */     this.fontMetrics.setCapHeight(capHeight);
/*     */   }
/*     */   
/*     */   protected void setXHeight(int xHeight) {
/* 216 */     this.fontMetrics.setXHeight(xHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setItalicAngle(int italicAngle) {
/* 227 */     this.fontMetrics.setItalicAngle(italicAngle);
/*     */   }
/*     */   
/*     */   protected void setStemV(int stemV) {
/* 231 */     this.fontMetrics.setStemV(stemV);
/*     */   }
/*     */   
/*     */   protected void setStemH(int stemH) {
/* 235 */     this.fontMetrics.setStemH(stemH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontWeight(int fontWeight) {
/* 244 */     this.fontNames.setFontWeight(fontWeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontStretch(String fontWidth) {
/* 253 */     this.fontNames.setFontStretch(fontWidth);
/*     */   }
/*     */   
/*     */   protected void setFixedPitch(boolean isFixedPitch) {
/* 257 */     this.fontMetrics.setIsFixedPitch(isFixedPitch);
/*     */   }
/*     */   
/*     */   protected void setBold(boolean isBold) {
/* 261 */     if (isBold) {
/* 262 */       this.fontNames.setMacStyle(this.fontNames.getMacStyle() | 0x1);
/*     */     } else {
/* 264 */       this.fontNames.setMacStyle(this.fontNames.getMacStyle() & 0xFFFFFFFE);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setBbox(int[] bbox) {
/* 269 */     this.fontMetrics.setBbox(bbox[0], bbox[1], bbox[2], bbox[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontFamily(String fontFamily) {
/* 278 */     this.fontNames.setFamilyName(fontFamily);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontName(String fontName) {
/* 289 */     this.fontNames.setFontName(fontName);
/* 290 */     if (this.fontNames.getFullName() == null) {
/* 291 */       this.fontNames.setFullName(fontName);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fixSpaceIssue() {
/* 296 */     Glyph space = this.unicodeToGlyph.get(Integer.valueOf(32));
/* 297 */     if (space != null) {
/* 298 */       this.codeToGlyph.put(Integer.valueOf(space.getCode()), space);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 304 */     String name = getFontNames().getFontName();
/* 305 */     return (name.length() > 0) ? name : super.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontProgram.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */