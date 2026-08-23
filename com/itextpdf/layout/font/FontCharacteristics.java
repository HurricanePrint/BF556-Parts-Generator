/*     */ package com.itextpdf.layout.font;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontCharacteristics
/*     */ {
/*     */   private boolean isItalic = false;
/*     */   private boolean isBold = false;
/*  49 */   private short fontWeight = 400;
/*     */   
/*     */   private boolean undefined = true;
/*     */   private boolean isMonospace = false;
/*     */   
/*     */   public FontCharacteristics() {}
/*     */   
/*     */   public FontCharacteristics(FontCharacteristics other) {
/*  57 */     this();
/*  58 */     this.isItalic = other.isItalic;
/*  59 */     this.isBold = other.isBold;
/*  60 */     this.fontWeight = other.fontWeight;
/*  61 */     this.undefined = other.undefined;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontCharacteristics setFontWeight(short fw) {
/*  72 */     if (fw > 0) {
/*  73 */       this.fontWeight = FontCharacteristicsUtils.normalizeFontWeight(fw);
/*  74 */       modified();
/*     */     } 
/*  76 */     return this;
/*     */   }
/*     */   
/*     */   public FontCharacteristics setFontWeight(String fw) {
/*  80 */     return setFontWeight(FontCharacteristicsUtils.parseFontWeight(fw));
/*     */   }
/*     */   
/*     */   public FontCharacteristics setBoldFlag(boolean isBold) {
/*  84 */     this.isBold = isBold;
/*  85 */     if (this.isBold) modified(); 
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public FontCharacteristics setItalicFlag(boolean isItalic) {
/*  90 */     this.isItalic = isItalic;
/*  91 */     if (this.isItalic) modified(); 
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public FontCharacteristics setMonospaceFlag(boolean isMonospace) {
/*  96 */     this.isMonospace = isMonospace;
/*  97 */     if (this.isMonospace) modified(); 
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontCharacteristics setFontStyle(String fs) {
/* 107 */     if (fs != null && fs.length() > 0) {
/* 108 */       fs = fs.trim().toLowerCase();
/* 109 */       if ("normal".equals(fs)) {
/* 110 */         this.isItalic = false;
/* 111 */       } else if ("italic".equals(fs) || "oblique".equals(fs)) {
/* 112 */         this.isItalic = true;
/*     */       } 
/*     */     } 
/* 115 */     if (this.isItalic) modified(); 
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isItalic() {
/* 120 */     return this.isItalic;
/*     */   }
/*     */   
/*     */   public boolean isBold() {
/* 124 */     return (this.isBold || this.fontWeight > 500);
/*     */   }
/*     */   
/*     */   public boolean isMonospace() {
/* 128 */     return this.isMonospace;
/*     */   }
/*     */   
/*     */   public short getFontWeight() {
/* 132 */     return this.fontWeight;
/*     */   }
/*     */   
/*     */   public boolean isUndefined() {
/* 136 */     return this.undefined;
/*     */   }
/*     */   
/*     */   private void modified() {
/* 140 */     this.undefined = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 145 */     if (this == o) return true; 
/* 146 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 148 */     FontCharacteristics that = (FontCharacteristics)o;
/*     */     
/* 150 */     return (this.isItalic == that.isItalic && this.isBold == that.isBold && this.fontWeight == that.fontWeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 157 */     int result = this.isItalic ? 1 : 0;
/* 158 */     result = 31 * result + (this.isBold ? 1 : 0);
/* 159 */     result = 31 * result + this.fontWeight;
/* 160 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontCharacteristics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */