/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontMetrics
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7113134666493365588L;
/*  52 */   protected float normalizationCoef = 1.0F;
/*     */ 
/*     */   
/*  55 */   private int unitsPerEm = 1000;
/*     */   
/*     */   private int numOfGlyphs;
/*     */   
/*     */   private int[] glyphWidths;
/*     */   
/*  61 */   private int typoAscender = 800;
/*     */   
/*  63 */   private int typoDescender = -200;
/*     */   
/*  65 */   private int capHeight = 700;
/*     */   
/*  67 */   private int xHeight = 0;
/*     */   
/*  69 */   private float italicAngle = 0.0F;
/*     */ 
/*     */   
/*  72 */   private int[] bbox = new int[] { -50, -200, 1000, 900 };
/*     */   
/*     */   private int ascender;
/*     */   
/*     */   private int descender;
/*     */   
/*     */   private int lineGap;
/*     */   
/*     */   private int winAscender;
/*     */   
/*     */   private int winDescender;
/*     */   
/*     */   private int advanceWidthMax;
/*     */   
/*  86 */   private int underlinePosition = -100;
/*     */   
/*  88 */   private int underlineThickness = 50;
/*     */   
/*     */   private int strikeoutPosition;
/*     */   
/*     */   private int strikeoutSize;
/*     */   
/*     */   private int subscriptSize;
/*     */   
/*     */   private int subscriptOffset;
/*     */   
/*     */   private int superscriptSize;
/*     */   
/*     */   private int superscriptOffset;
/*     */   
/* 102 */   private int stemV = 80;
/*     */   
/* 104 */   private int stemH = 0;
/*     */   
/*     */   private boolean isFixedPitch;
/*     */ 
/*     */   
/*     */   public int getUnitsPerEm() {
/* 110 */     return this.unitsPerEm;
/*     */   }
/*     */   
/*     */   public int getNumberOfGlyphs() {
/* 114 */     return this.numOfGlyphs;
/*     */   }
/*     */   
/*     */   public int[] getGlyphWidths() {
/* 118 */     return this.glyphWidths;
/*     */   }
/*     */   
/*     */   public int getTypoAscender() {
/* 122 */     return this.typoAscender;
/*     */   }
/*     */   
/*     */   public int getTypoDescender() {
/* 126 */     return this.typoDescender;
/*     */   }
/*     */   
/*     */   public int getCapHeight() {
/* 130 */     return this.capHeight;
/*     */   }
/*     */   
/*     */   public int getXHeight() {
/* 134 */     return this.xHeight;
/*     */   }
/*     */   
/*     */   public float getItalicAngle() {
/* 138 */     return this.italicAngle;
/*     */   }
/*     */   
/*     */   public int[] getBbox() {
/* 142 */     return this.bbox;
/*     */   }
/*     */   
/*     */   public void setBbox(int llx, int lly, int urx, int ury) {
/* 146 */     this.bbox[0] = llx;
/* 147 */     this.bbox[1] = lly;
/* 148 */     this.bbox[2] = urx;
/* 149 */     this.bbox[3] = ury;
/*     */   }
/*     */   
/*     */   public int getAscender() {
/* 153 */     return this.ascender;
/*     */   }
/*     */   
/*     */   public int getDescender() {
/* 157 */     return this.descender;
/*     */   }
/*     */   
/*     */   public int getLineGap() {
/* 161 */     return this.lineGap;
/*     */   }
/*     */   
/*     */   public int getWinAscender() {
/* 165 */     return this.winAscender;
/*     */   }
/*     */   
/*     */   public int getWinDescender() {
/* 169 */     return this.winDescender;
/*     */   }
/*     */   
/*     */   public int getAdvanceWidthMax() {
/* 173 */     return this.advanceWidthMax;
/*     */   }
/*     */   
/*     */   public int getUnderlinePosition() {
/* 177 */     return this.underlinePosition - this.underlineThickness / 2;
/*     */   }
/*     */   
/*     */   public int getUnderlineThickness() {
/* 181 */     return this.underlineThickness;
/*     */   }
/*     */   
/*     */   public int getStrikeoutPosition() {
/* 185 */     return this.strikeoutPosition;
/*     */   }
/*     */   
/*     */   public int getStrikeoutSize() {
/* 189 */     return this.strikeoutSize;
/*     */   }
/*     */   
/*     */   public int getSubscriptSize() {
/* 193 */     return this.subscriptSize;
/*     */   }
/*     */   
/*     */   public int getSubscriptOffset() {
/* 197 */     return this.subscriptOffset;
/*     */   }
/*     */   
/*     */   public int getSuperscriptSize() {
/* 201 */     return this.superscriptSize;
/*     */   }
/*     */   
/*     */   public int getSuperscriptOffset() {
/* 205 */     return this.superscriptOffset;
/*     */   }
/*     */   
/*     */   public int getStemV() {
/* 209 */     return this.stemV;
/*     */   }
/*     */   
/*     */   public int getStemH() {
/* 213 */     return this.stemH;
/*     */   }
/*     */   
/*     */   public boolean isFixedPitch() {
/* 217 */     return this.isFixedPitch;
/*     */   }
/*     */   
/*     */   protected void setUnitsPerEm(int unitsPerEm) {
/* 221 */     this.unitsPerEm = unitsPerEm;
/* 222 */     this.normalizationCoef = 1000.0F / unitsPerEm;
/*     */   }
/*     */   
/*     */   protected void updateBbox(float llx, float lly, float urx, float ury) {
/* 226 */     this.bbox[0] = (int)(llx * this.normalizationCoef);
/* 227 */     this.bbox[1] = (int)(lly * this.normalizationCoef);
/* 228 */     this.bbox[2] = (int)(urx * this.normalizationCoef);
/* 229 */     this.bbox[3] = (int)(ury * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setNumberOfGlyphs(int numOfGlyphs) {
/* 233 */     this.numOfGlyphs = numOfGlyphs;
/*     */   }
/*     */   
/*     */   protected void setGlyphWidths(int[] glyphWidths) {
/* 237 */     this.glyphWidths = glyphWidths;
/*     */   }
/*     */   
/*     */   protected void setTypoAscender(int typoAscender) {
/* 241 */     this.typoAscender = (int)(typoAscender * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setTypoDescender(int typoDesctender) {
/* 245 */     this.typoDescender = (int)(typoDesctender * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setCapHeight(int capHeight) {
/* 249 */     this.capHeight = (int)(capHeight * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setXHeight(int xHeight) {
/* 253 */     this.xHeight = (int)(xHeight * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setItalicAngle(float italicAngle) {
/* 257 */     this.italicAngle = italicAngle;
/*     */   }
/*     */   
/*     */   protected void setAscender(int ascender) {
/* 261 */     this.ascender = (int)(ascender * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setDescender(int descender) {
/* 265 */     this.descender = (int)(descender * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setLineGap(int lineGap) {
/* 269 */     this.lineGap = (int)(lineGap * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setWinAscender(int winAscender) {
/* 273 */     this.winAscender = (int)(winAscender * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setWinDescender(int winDescender) {
/* 277 */     this.winDescender = (int)(winDescender * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setAdvanceWidthMax(int advanceWidthMax) {
/* 281 */     this.advanceWidthMax = (int)(advanceWidthMax * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setUnderlinePosition(int underlinePosition) {
/* 285 */     this.underlinePosition = (int)(underlinePosition * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setUnderlineThickness(int underineThickness) {
/* 289 */     this.underlineThickness = underineThickness;
/*     */   }
/*     */   
/*     */   protected void setStrikeoutPosition(int strikeoutPosition) {
/* 293 */     this.strikeoutPosition = (int)(strikeoutPosition * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setStrikeoutSize(int strikeoutSize) {
/* 297 */     this.strikeoutSize = (int)(strikeoutSize * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setSubscriptSize(int subscriptSize) {
/* 301 */     this.subscriptSize = (int)(subscriptSize * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setSubscriptOffset(int subscriptOffset) {
/* 305 */     this.subscriptOffset = (int)(subscriptOffset * this.normalizationCoef);
/*     */   }
/*     */   
/*     */   protected void setSuperscriptSize(int superscriptSize) {
/* 309 */     this.superscriptSize = superscriptSize;
/*     */   }
/*     */   
/*     */   protected void setSuperscriptOffset(int superscriptOffset) {
/* 313 */     this.superscriptOffset = (int)(superscriptOffset * this.normalizationCoef);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStemV(int stemV) {
/* 318 */     this.stemV = stemV;
/*     */   }
/*     */   
/*     */   protected void setStemH(int stemH) {
/* 322 */     this.stemH = stemH;
/*     */   }
/*     */   
/*     */   protected void setIsFixedPitch(boolean isFixedPitch) {
/* 326 */     this.isFixedPitch = isFixedPitch;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontMetrics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */