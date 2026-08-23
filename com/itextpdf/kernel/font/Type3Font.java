/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontNames;
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.otf.Glyph;
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
/*     */ 
/*     */ 
/*     */ public class Type3Font
/*     */   extends FontProgram
/*     */ {
/*     */   private static final long serialVersionUID = 1027076515537536993L;
/*  65 */   private final Map<Integer, Type3Glyph> type3Glyphs = new HashMap<>();
/*     */   private boolean colorized = false;
/*  67 */   private int flags = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Type3Font(boolean colorized) {
/*  75 */     this.colorized = colorized;
/*  76 */     this.fontNames = new FontNames();
/*  77 */     getFontMetrics().setBbox(0, 0, 0, 0);
/*     */   }
/*     */   
/*     */   public Type3Glyph getType3Glyph(int unicode) {
/*  81 */     return this.type3Glyphs.get(Integer.valueOf(unicode));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPdfFontFlags() {
/*  86 */     return this.flags;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFontSpecific() {
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isColorized() {
/*  95 */     return this.colorized;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKerning(Glyph glyph1, Glyph glyph2) {
/* 100 */     return 0;
/*     */   }
/*     */   
/*     */   public int getNumberOfGlyphs() {
/* 104 */     return this.type3Glyphs.size();
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
/*     */   protected void setFontName(String fontName) {
/* 116 */     super.setFontName(fontName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontFamily(String fontFamily) {
/* 127 */     super.setFontFamily(fontFamily);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontWeight(int fontWeight) {
/* 138 */     super.setFontWeight(fontWeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontStretch(String fontWidth) {
/* 149 */     super.setFontStretch(fontWidth);
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
/*     */   protected void setItalicAngle(int italicAngle) {
/* 162 */     super.setItalicAngle(italicAngle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setPdfFontFlags(int flags) {
/* 172 */     this.flags = flags;
/*     */   }
/*     */   
/*     */   void addGlyph(int code, int unicode, int width, int[] bbox, Type3Glyph type3Glyph) {
/* 176 */     Glyph glyph = new Glyph(code, width, unicode, bbox);
/* 177 */     this.codeToGlyph.put(Integer.valueOf(code), glyph);
/* 178 */     this.unicodeToGlyph.put(Integer.valueOf(unicode), glyph);
/* 179 */     this.type3Glyphs.put(Integer.valueOf(unicode), type3Glyph);
/* 180 */     recalculateAverageWidth();
/*     */   }
/*     */   
/*     */   private void recalculateAverageWidth() {
/* 184 */     int widthSum = 0;
/* 185 */     for (Glyph glyph : this.codeToGlyph.values()) {
/* 186 */       widthSum += glyph.getWidth();
/*     */     }
/* 188 */     this.avgWidth = widthSum / this.codeToGlyph.size();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/Type3Font.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */