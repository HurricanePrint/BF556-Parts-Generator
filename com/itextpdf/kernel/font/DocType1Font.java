/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontEncoding;
/*     */ import com.itextpdf.io.font.FontProgramFactory;
/*     */ import com.itextpdf.io.font.Type1Font;
/*     */ import com.itextpdf.io.font.cmap.CMapToUnicode;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
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
/*     */ class DocType1Font
/*     */   extends Type1Font
/*     */   implements IDocFontProgram
/*     */ {
/*     */   private static final long serialVersionUID = 6260280563455951912L;
/*     */   private PdfStream fontFile;
/*     */   private PdfName fontFileName;
/*     */   private PdfName subtype;
/*  68 */   private int missingWidth = 0;
/*     */   
/*     */   private DocType1Font(String fontName) {
/*  71 */     super(fontName);
/*     */   }
/*     */   static Type1Font createFontProgram(PdfDictionary fontDictionary, FontEncoding fontEncoding, CMapToUnicode toUnicode) {
/*     */     String baseFont;
/*  75 */     PdfName baseFontName = fontDictionary.getAsName(PdfName.BaseFont);
/*     */     
/*  77 */     if (baseFontName != null) {
/*  78 */       baseFont = baseFontName.getValue();
/*     */     } else {
/*  80 */       baseFont = FontUtil.createRandomFontName();
/*     */     } 
/*  82 */     if (!fontDictionary.containsKey(PdfName.FontDescriptor)) {
/*     */       Type1Font type1StdFont;
/*     */ 
/*     */       
/*     */       try {
/*  87 */         type1StdFont = (Type1Font)FontProgramFactory.createFont(baseFont, true);
/*  88 */       } catch (Exception e) {
/*  89 */         type1StdFont = null;
/*     */       } 
/*  91 */       if (type1StdFont != null) {
/*  92 */         return type1StdFont;
/*     */       }
/*     */     } 
/*  95 */     DocType1Font fontProgram = new DocType1Font(baseFont);
/*  96 */     PdfDictionary fontDesc = fontDictionary.getAsDictionary(PdfName.FontDescriptor);
/*  97 */     fontProgram.subtype = (fontDesc != null) ? fontDesc.getAsName(PdfName.Subtype) : null;
/*  98 */     fillFontDescriptor(fontProgram, fontDesc);
/*     */     
/* 100 */     PdfNumber firstCharNumber = fontDictionary.getAsNumber(PdfName.FirstChar);
/* 101 */     int firstChar = (firstCharNumber != null) ? Math.max(firstCharNumber.intValue(), 0) : 0;
/* 102 */     int[] widths = FontUtil.convertSimpleWidthsArray(fontDictionary.getAsArray(PdfName.Widths), firstChar, fontProgram.getMissingWidth());
/* 103 */     fontProgram.avgWidth = 0;
/* 104 */     int glyphsWithWidths = 0;
/* 105 */     for (int i = 0; i < 256; i++) {
/* 106 */       Glyph glyph = new Glyph(i, widths[i], fontEncoding.getUnicode(i));
/* 107 */       fontProgram.codeToGlyph.put(Integer.valueOf(i), glyph);
/* 108 */       if (glyph.hasValidUnicode()) {
/*     */         
/* 110 */         if (fontEncoding.convertToByte(glyph.getUnicode()) == i) {
/* 111 */           fontProgram.unicodeToGlyph.put(Integer.valueOf(glyph.getUnicode()), glyph);
/*     */         }
/* 113 */       } else if (toUnicode != null) {
/* 114 */         glyph.setChars(toUnicode.lookup(i));
/*     */       } 
/* 116 */       if (widths[i] > 0) {
/* 117 */         glyphsWithWidths++;
/* 118 */         fontProgram.avgWidth += widths[i];
/*     */       } 
/*     */     } 
/* 121 */     if (glyphsWithWidths != 0) {
/* 122 */       fontProgram.avgWidth /= glyphsWithWidths;
/*     */     }
/* 124 */     return fontProgram;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfStream getFontFile() {
/* 129 */     return this.fontFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getFontFileName() {
/* 134 */     return this.fontFileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/* 139 */     return this.subtype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBuiltWith(String fontName) {
/* 150 */     return false;
/*     */   }
/*     */   
/*     */   public int getMissingWidth() {
/* 154 */     return this.missingWidth;
/*     */   }
/*     */   
/*     */   static void fillFontDescriptor(DocType1Font font, PdfDictionary fontDesc) {
/* 158 */     if (fontDesc == null) {
/* 159 */       Logger logger = LoggerFactory.getLogger(FontUtil.class);
/* 160 */       logger.warn("Font dictionary does not contain required /FontDescriptor entry.");
/*     */       return;
/*     */     } 
/* 163 */     PdfNumber v = fontDesc.getAsNumber(PdfName.Ascent);
/* 164 */     if (v != null) {
/* 165 */       font.setTypoAscender(v.intValue());
/*     */     }
/* 167 */     v = fontDesc.getAsNumber(PdfName.Descent);
/* 168 */     if (v != null) {
/* 169 */       font.setTypoDescender(v.intValue());
/*     */     }
/* 171 */     v = fontDesc.getAsNumber(PdfName.CapHeight);
/* 172 */     if (v != null) {
/* 173 */       font.setCapHeight(v.intValue());
/*     */     }
/* 175 */     v = fontDesc.getAsNumber(PdfName.XHeight);
/* 176 */     if (v != null) {
/* 177 */       font.setXHeight(v.intValue());
/*     */     }
/* 179 */     v = fontDesc.getAsNumber(PdfName.ItalicAngle);
/* 180 */     if (v != null) {
/* 181 */       font.setItalicAngle(v.intValue());
/*     */     }
/* 183 */     v = fontDesc.getAsNumber(PdfName.StemV);
/* 184 */     if (v != null) {
/* 185 */       font.setStemV(v.intValue());
/*     */     }
/* 187 */     v = fontDesc.getAsNumber(PdfName.StemH);
/* 188 */     if (v != null) {
/* 189 */       font.setStemH(v.intValue());
/*     */     }
/* 191 */     v = fontDesc.getAsNumber(PdfName.FontWeight);
/* 192 */     if (v != null) {
/* 193 */       font.setFontWeight(v.intValue());
/*     */     }
/* 195 */     v = fontDesc.getAsNumber(PdfName.MissingWidth);
/* 196 */     if (v != null) {
/* 197 */       font.missingWidth = v.intValue();
/*     */     }
/*     */     
/* 200 */     PdfName fontStretch = fontDesc.getAsName(PdfName.FontStretch);
/* 201 */     if (fontStretch != null) {
/* 202 */       font.setFontStretch(fontStretch.getValue());
/*     */     }
/*     */     
/* 205 */     PdfArray bboxValue = fontDesc.getAsArray(PdfName.FontBBox);
/*     */     
/* 207 */     if (bboxValue != null) {
/* 208 */       int[] bbox = new int[4];
/*     */       
/* 210 */       bbox[0] = bboxValue.getAsNumber(0).intValue();
/*     */       
/* 212 */       bbox[1] = bboxValue.getAsNumber(1).intValue();
/*     */       
/* 214 */       bbox[2] = bboxValue.getAsNumber(2).intValue();
/*     */       
/* 216 */       bbox[3] = bboxValue.getAsNumber(3).intValue();
/*     */       
/* 218 */       if (bbox[0] > bbox[2]) {
/* 219 */         int t = bbox[0];
/* 220 */         bbox[0] = bbox[2];
/* 221 */         bbox[2] = t;
/*     */       } 
/* 223 */       if (bbox[1] > bbox[3]) {
/* 224 */         int t = bbox[1];
/* 225 */         bbox[1] = bbox[3];
/* 226 */         bbox[3] = t;
/*     */       } 
/* 228 */       font.setBbox(bbox);
/*     */ 
/*     */ 
/*     */       
/* 232 */       if (font.getFontMetrics().getTypoAscender() == 0 && font.getFontMetrics().getTypoDescender() == 0) {
/* 233 */         float maxAscent = Math.max(bbox[3], font.getFontMetrics().getTypoAscender());
/* 234 */         float minDescent = Math.min(bbox[1], font.getFontMetrics().getTypoDescender());
/* 235 */         font.setTypoAscender((int)(maxAscent * 1000.0F / (maxAscent - minDescent)));
/* 236 */         font.setTypoDescender((int)(minDescent * 1000.0F / (maxAscent - minDescent)));
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     PdfString fontFamily = fontDesc.getAsString(PdfName.FontFamily);
/* 241 */     if (fontFamily != null) {
/* 242 */       font.setFontFamily(fontFamily.getValue());
/*     */     }
/*     */     
/* 245 */     PdfNumber flagsValue = fontDesc.getAsNumber(PdfName.Flags);
/* 246 */     if (flagsValue != null) {
/* 247 */       int flags = flagsValue.intValue();
/* 248 */       if ((flags & 0x1) != 0) {
/* 249 */         font.setFixedPitch(true);
/*     */       }
/* 251 */       if ((flags & 0x40000) != 0) {
/* 252 */         font.setBold(true);
/*     */       }
/*     */     } 
/*     */     
/* 256 */     PdfName[] fontFileNames = { PdfName.FontFile, PdfName.FontFile2, PdfName.FontFile3 };
/* 257 */     for (PdfName fontFile : fontFileNames) {
/* 258 */       if (fontDesc.containsKey(fontFile)) {
/* 259 */         font.fontFileName = fontFile;
/* 260 */         font.fontFile = fontDesc.getAsStream(fontFile);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/DocType1Font.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */