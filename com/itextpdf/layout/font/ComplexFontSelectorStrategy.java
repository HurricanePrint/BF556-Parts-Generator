/*     */ package com.itextpdf.layout.font;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComplexFontSelectorStrategy
/*     */   extends FontSelectorStrategy
/*     */ {
/*     */   private PdfFont font;
/*     */   private FontSelector selector;
/*     */   
/*     */   public ComplexFontSelectorStrategy(String text, FontSelector selector, FontProvider provider, FontSet additionalFonts) {
/*  64 */     super(text, provider, additionalFonts);
/*  65 */     this.font = null;
/*  66 */     this.selector = selector;
/*     */   }
/*     */   
/*     */   public ComplexFontSelectorStrategy(String text, FontSelector selector, FontProvider provider) {
/*  70 */     super(text, provider, null);
/*  71 */     this.font = null;
/*  72 */     this.selector = selector;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfFont getCurrentFont() {
/*  77 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Glyph> nextGlyphs() {
/*  82 */     this.font = null;
/*  83 */     int nextUnignorable = nextSignificantIndex();
/*  84 */     if (nextUnignorable < this.text.length()) {
/*  85 */       for (FontInfo f : this.selector.getFonts()) {
/*     */ 
/*     */         
/*  88 */         int codePoint = isSurrogatePair(this.text, nextUnignorable) ? TextUtil.convertToUtf32(this.text, nextUnignorable) : this.text.charAt(nextUnignorable);
/*     */         
/*  90 */         if (f.getFontUnicodeRange().contains(codePoint)) {
/*  91 */           PdfFont currentFont = getPdfFont(f);
/*  92 */           Glyph glyph = currentFont.getGlyph(codePoint);
/*  93 */           if (null != glyph && 0 != glyph.getCode()) {
/*  94 */             this.font = currentFont;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 100 */     List<Glyph> glyphs = new ArrayList<>();
/* 101 */     boolean anyGlyphsAppended = false;
/* 102 */     if (this.font != null) {
/* 103 */       Character.UnicodeScript unicodeScript = nextSignificantUnicodeScript(nextUnignorable);
/* 104 */       int to = nextUnignorable;
/* 105 */       for (int i = nextUnignorable; i < this.text.length(); i++) {
/* 106 */         int codePoint = isSurrogatePair(this.text, i) ? TextUtil.convertToUtf32(this.text, i) : this.text.charAt(i);
/* 107 */         Character.UnicodeScript currScript = Character.UnicodeScript.of(codePoint);
/* 108 */         if (isSignificantUnicodeScript(currScript) && currScript != unicodeScript) {
/*     */           break;
/*     */         }
/* 111 */         if (codePoint > 65535) i++; 
/* 112 */         to = i;
/*     */       } 
/*     */       
/* 115 */       int numOfAppendedGlyphs = this.font.appendGlyphs(this.text, this.index, to, glyphs);
/* 116 */       anyGlyphsAppended = (numOfAppendedGlyphs > 0);
/* 117 */       assert anyGlyphsAppended;
/* 118 */       this.index += numOfAppendedGlyphs;
/*     */     } 
/* 120 */     if (!anyGlyphsAppended) {
/* 121 */       this.font = getPdfFont(this.selector.bestMatch());
/* 122 */       if (this.index != nextUnignorable) {
/* 123 */         this.index += this.font.appendGlyphs(this.text, this.index, nextUnignorable - 1, glyphs);
/*     */       }
/* 125 */       while (this.index <= nextUnignorable && this.index < this.text.length()) {
/* 126 */         this.index += this.font.appendAnyGlyph(this.text, this.index, glyphs);
/*     */       }
/*     */     } 
/* 129 */     return glyphs;
/*     */   }
/*     */   
/*     */   private int nextSignificantIndex() {
/* 133 */     int nextValidChar = this.index;
/* 134 */     for (; nextValidChar < this.text.length() && 
/* 135 */       TextUtil.isWhitespaceOrNonPrintable(this.text.charAt(nextValidChar)); nextValidChar++);
/*     */ 
/*     */ 
/*     */     
/* 139 */     return nextValidChar;
/*     */   }
/*     */   
/*     */   private Character.UnicodeScript nextSignificantUnicodeScript(int from) {
/* 143 */     for (int i = from; i < this.text.length(); i++) {
/*     */       int codePoint;
/* 145 */       if (isSurrogatePair(this.text, i)) {
/* 146 */         codePoint = TextUtil.convertToUtf32(this.text, i);
/* 147 */         i++;
/*     */       } else {
/* 149 */         codePoint = this.text.charAt(i);
/*     */       } 
/* 151 */       Character.UnicodeScript unicodeScript = Character.UnicodeScript.of(codePoint);
/* 152 */       if (isSignificantUnicodeScript(unicodeScript)) {
/* 153 */         return unicodeScript;
/*     */       }
/*     */     } 
/* 156 */     return Character.UnicodeScript.COMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isSignificantUnicodeScript(Character.UnicodeScript unicodeScript) {
/* 161 */     return (unicodeScript != Character.UnicodeScript.COMMON && unicodeScript != Character.UnicodeScript.INHERITED);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isSurrogatePair(String text, int idx) {
/* 166 */     return (TextUtil.isSurrogateHigh(text.charAt(idx)) && idx < text.length() - 1 && 
/* 167 */       TextUtil.isSurrogateLow(text.charAt(idx + 1)));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/ComplexFontSelectorStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */