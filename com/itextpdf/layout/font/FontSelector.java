/*     */ package com.itextpdf.layout.font;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontSelector
/*     */ {
/*     */   protected List<FontInfo> fonts;
/*     */   private static final int EXPECTED_FONT_IS_BOLD_AWARD = 5;
/*     */   private static final int EXPECTED_FONT_IS_NOT_BOLD_AWARD = 3;
/*     */   private static final int EXPECTED_FONT_IS_ITALIC_AWARD = 5;
/*     */   private static final int EXPECTED_FONT_IS_NOT_ITALIC_AWARD = 3;
/*     */   private static final int EXPECTED_FONT_IS_MONOSPACED_AWARD = 5;
/*     */   private static final int EXPECTED_FONT_IS_NOT_MONOSPACED_AWARD = 1;
/*     */   private static final int FONT_FAMILY_EQUALS_AWARD = 13;
/*     */   
/*     */   public FontSelector(Collection<FontInfo> allFonts, List<String> fontFamilies, FontCharacteristics fc) {
/*  75 */     this.fonts = new ArrayList<>(allFonts);
/*     */     
/*  77 */     Collections.sort(this.fonts, getComparator(fontFamilies, fc));
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
/*     */   public final FontInfo bestMatch() {
/*  89 */     return this.fonts.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Iterable<FontInfo> getFonts() {
/*  98 */     return this.fonts;
/*     */   }
/*     */   
/*     */   protected Comparator<FontInfo> getComparator(List<String> fontFamilies, FontCharacteristics fc) {
/* 102 */     return new PdfFontComparator(fontFamilies, fc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PdfFontComparator
/*     */     implements Comparator<FontInfo>
/*     */   {
/* 110 */     List<String> fontFamilies = new ArrayList<>();
/* 111 */     List<FontCharacteristics> fontStyles = new ArrayList<>(); PdfFontComparator(List<String> fontFamilies, FontCharacteristics fc) {
/* 112 */       if (fontFamilies != null && fontFamilies.size() > 0) {
/* 113 */         for (String fontFamily : fontFamilies) {
/* 114 */           String lowercaseFontFamily = fontFamily.toLowerCase();
/* 115 */           this.fontFamilies.add(lowercaseFontFamily);
/* 116 */           this.fontStyles.add(parseFontStyle(lowercaseFontFamily, fc));
/*     */         } 
/*     */       } else {
/* 119 */         this.fontStyles.add(fc);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int compare(FontInfo o1, FontInfo o2) {
/* 125 */       int res = 0;
/*     */ 
/*     */       
/* 128 */       for (int i = 0; i < this.fontFamilies.size() && res == 0; i++) {
/* 129 */         FontCharacteristics fc = this.fontStyles.get(i);
/* 130 */         String fontFamily = this.fontFamilies.get(i);
/*     */         
/* 132 */         if ("monospace".equalsIgnoreCase(fontFamily)) {
/* 133 */           fc.setMonospaceFlag(true);
/*     */         }
/* 135 */         boolean isLastFontFamilyToBeProcessed = (i == this.fontFamilies.size() - 1);
/* 136 */         res = characteristicsSimilarity(fontFamily, fc, o2, isLastFontFamilyToBeProcessed) - characteristicsSimilarity(fontFamily, fc, o1, isLastFontFamilyToBeProcessed);
/*     */       } 
/* 138 */       return res;
/*     */     }
/*     */     
/*     */     private static FontCharacteristics parseFontStyle(String fontFamily, FontCharacteristics fc) {
/* 142 */       if (fc == null) {
/* 143 */         fc = new FontCharacteristics();
/*     */       }
/* 145 */       if (fc.isUndefined()) {
/* 146 */         if (fontFamily.contains("bold")) {
/* 147 */           fc.setBoldFlag(true);
/*     */         }
/* 149 */         if (fontFamily.contains("italic") || fontFamily.contains("oblique")) {
/* 150 */           fc.setItalicFlag(true);
/*     */         }
/*     */       } 
/* 153 */       return fc;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static int characteristicsSimilarity(String fontFamily, FontCharacteristics fc, FontInfo fontInfo, boolean isLastFontFamilyToBeProcessed) {
/* 174 */       boolean isFontBold = (fontInfo.getDescriptor().isBold() || fontInfo.getDescriptor().getFontWeight() > 500);
/* 175 */       boolean isFontItalic = (fontInfo.getDescriptor().isItalic() || fontInfo.getDescriptor().getItalicAngle() < 0.0F);
/* 176 */       boolean isFontMonospace = fontInfo.getDescriptor().isMonospace();
/* 177 */       int score = 0;
/*     */ 
/*     */       
/* 180 */       boolean fontFamilySetByCharacteristics = false;
/*     */ 
/*     */       
/* 183 */       if (fc.isMonospace()) {
/* 184 */         fontFamilySetByCharacteristics = true;
/* 185 */         if (isFontMonospace) {
/* 186 */           score += 5;
/*     */         } else {
/* 188 */           score -= 5;
/*     */         }
/*     */       
/* 191 */       } else if (isFontMonospace) {
/* 192 */         score--;
/*     */       } 
/*     */ 
/*     */       
/* 196 */       if (!fontFamilySetByCharacteristics)
/*     */       {
/* 198 */         if (!"".equals(fontFamily) && ((null == fontInfo
/* 199 */           .getAlias() && null != fontInfo
/* 200 */           .getDescriptor().getFamilyNameLowerCase() && fontInfo
/* 201 */           .getDescriptor().getFamilyNameLowerCase().equals(fontFamily)) || (null != fontInfo
/* 202 */           .getAlias() && fontInfo.getAlias().toLowerCase().equals(fontFamily)))) {
/* 203 */           score += 13;
/*     */         }
/* 205 */         else if (!isLastFontFamilyToBeProcessed) {
/* 206 */           return score;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 212 */       if (fc.isBold()) {
/* 213 */         if (isFontBold) {
/* 214 */           score += 5;
/*     */         } else {
/* 216 */           score -= 5;
/*     */         }
/*     */       
/* 219 */       } else if (isFontBold) {
/* 220 */         score -= 3;
/*     */       } 
/*     */ 
/*     */       
/* 224 */       if (fc.isItalic()) {
/* 225 */         if (isFontItalic) {
/* 226 */           score += 5;
/*     */         } else {
/* 228 */           score -= 5;
/*     */         }
/*     */       
/* 231 */       } else if (isFontItalic) {
/* 232 */         score -= 3;
/*     */       } 
/*     */ 
/*     */       
/* 236 */       return score;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontSelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */