/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontEncoding;
/*     */ import com.itextpdf.io.font.TrueTypeFont;
/*     */ import com.itextpdf.io.font.cmap.CMapToUnicode;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.util.Iterator;
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
/*     */ class DocTrueTypeFont
/*     */   extends TrueTypeFont
/*     */   implements IDocFontProgram
/*     */ {
/*     */   private static final long serialVersionUID = 4611535787920619829L;
/*     */   private PdfStream fontFile;
/*     */   private PdfName fontFileName;
/*     */   private PdfName subtype;
/*  68 */   private int missingWidth = 0;
/*     */ 
/*     */   
/*     */   private DocTrueTypeFont(PdfDictionary fontDictionary) {
/*  72 */     PdfName baseFontName = fontDictionary.getAsName(PdfName.BaseFont);
/*  73 */     if (baseFontName != null) {
/*  74 */       setFontName(baseFontName.getValue());
/*     */     } else {
/*  76 */       setFontName(FontUtil.createRandomFontName());
/*     */     } 
/*  78 */     this.subtype = fontDictionary.getAsName(PdfName.Subtype);
/*     */   }
/*     */   
/*     */   static TrueTypeFont createFontProgram(PdfDictionary fontDictionary, FontEncoding fontEncoding, CMapToUnicode toUnicode) {
/*  82 */     DocTrueTypeFont fontProgram = new DocTrueTypeFont(fontDictionary);
/*  83 */     fillFontDescriptor(fontProgram, fontDictionary.getAsDictionary(PdfName.FontDescriptor));
/*     */     
/*  85 */     PdfNumber firstCharNumber = fontDictionary.getAsNumber(PdfName.FirstChar);
/*  86 */     int firstChar = (firstCharNumber != null) ? Math.max(firstCharNumber.intValue(), 0) : 0;
/*  87 */     int[] widths = FontUtil.convertSimpleWidthsArray(fontDictionary.getAsArray(PdfName.Widths), firstChar, fontProgram
/*  88 */         .getMissingWidth());
/*  89 */     fontProgram.avgWidth = 0;
/*  90 */     int glyphsWithWidths = 0;
/*  91 */     for (int i = 0; i < 256; i++) {
/*  92 */       Glyph glyph = new Glyph(i, widths[i], fontEncoding.getUnicode(i));
/*  93 */       fontProgram.codeToGlyph.put(Integer.valueOf(i), glyph);
/*     */       
/*  95 */       if (glyph.hasValidUnicode() && fontEncoding.convertToByte(glyph.getUnicode()) == i) {
/*  96 */         fontProgram.unicodeToGlyph.put(Integer.valueOf(glyph.getUnicode()), glyph);
/*  97 */       } else if (toUnicode != null) {
/*  98 */         glyph.setChars(toUnicode.lookup(i));
/*     */       } 
/* 100 */       if (widths[i] > 0) {
/* 101 */         glyphsWithWidths++;
/* 102 */         fontProgram.avgWidth += widths[i];
/*     */       } 
/*     */     } 
/* 105 */     if (glyphsWithWidths != 0) {
/* 106 */       fontProgram.avgWidth /= glyphsWithWidths;
/*     */     }
/* 108 */     return fontProgram;
/*     */   }
/*     */   static TrueTypeFont createFontProgram(PdfDictionary fontDictionary, CMapToUnicode toUnicode) {
/*     */     int dw;
/* 112 */     DocTrueTypeFont fontProgram = new DocTrueTypeFont(fontDictionary);
/* 113 */     PdfDictionary fontDescriptor = fontDictionary.getAsDictionary(PdfName.FontDescriptor);
/* 114 */     fillFontDescriptor(fontProgram, fontDescriptor);
/*     */     
/* 116 */     if (fontDescriptor != null && fontDescriptor.containsKey(PdfName.DW)) {
/* 117 */       dw = fontDescriptor.getAsInt(PdfName.DW).intValue();
/* 118 */     } else if (fontDictionary.containsKey(PdfName.DW)) {
/* 119 */       dw = fontDictionary.getAsInt(PdfName.DW).intValue();
/*     */     } else {
/* 121 */       dw = 1000;
/*     */     } 
/* 123 */     IntHashtable widths = null;
/* 124 */     if (toUnicode != null) {
/* 125 */       widths = FontUtil.convertCompositeWidthsArray(fontDictionary.getAsArray(PdfName.W));
/* 126 */       fontProgram.avgWidth = 0;
/* 127 */       for (Iterator<Integer> iterator = toUnicode.getCodes().iterator(); iterator.hasNext(); ) { int cid = ((Integer)iterator.next()).intValue();
/* 128 */         int width = widths.containsKey(cid) ? widths.get(cid) : dw;
/* 129 */         Glyph glyph = new Glyph(cid, width, toUnicode.lookup(cid));
/* 130 */         if (glyph.hasValidUnicode()) {
/* 131 */           fontProgram.unicodeToGlyph.put(Integer.valueOf(glyph.getUnicode()), glyph);
/*     */         }
/* 133 */         fontProgram.codeToGlyph.put(Integer.valueOf(cid), glyph);
/* 134 */         fontProgram.avgWidth += width; }
/*     */       
/* 136 */       if (fontProgram.codeToGlyph.size() != 0) {
/* 137 */         fontProgram.avgWidth /= fontProgram.codeToGlyph.size();
/*     */       }
/*     */     } 
/*     */     
/* 141 */     if (fontProgram.codeToGlyph.get(Integer.valueOf(0)) == null) {
/* 142 */       fontProgram.codeToGlyph.put(Integer.valueOf(0), new Glyph(0, (widths != null && widths.containsKey(0)) ? widths.get(0) : dw, -1));
/*     */     }
/* 144 */     return fontProgram;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfStream getFontFile() {
/* 149 */     return this.fontFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getFontFileName() {
/* 154 */     return this.fontFileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/* 159 */     return this.subtype;
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
/* 170 */     return false;
/*     */   }
/*     */   
/*     */   public int getMissingWidth() {
/* 174 */     return this.missingWidth;
/*     */   }
/*     */   
/*     */   static void fillFontDescriptor(DocTrueTypeFont font, PdfDictionary fontDesc) {
/* 178 */     if (fontDesc == null) {
/* 179 */       Logger logger = LoggerFactory.getLogger(FontUtil.class);
/* 180 */       logger.warn("Font dictionary does not contain required /FontDescriptor entry.");
/*     */       return;
/*     */     } 
/* 183 */     PdfNumber v = fontDesc.getAsNumber(PdfName.Ascent);
/* 184 */     if (v != null) {
/* 185 */       font.setTypoAscender(v.intValue());
/*     */     }
/* 187 */     v = fontDesc.getAsNumber(PdfName.Descent);
/* 188 */     if (v != null) {
/* 189 */       font.setTypoDescender(v.intValue());
/*     */     }
/* 191 */     v = fontDesc.getAsNumber(PdfName.CapHeight);
/* 192 */     if (v != null) {
/* 193 */       font.setCapHeight(v.intValue());
/*     */     }
/* 195 */     v = fontDesc.getAsNumber(PdfName.XHeight);
/* 196 */     if (v != null) {
/* 197 */       font.setXHeight(v.intValue());
/*     */     }
/* 199 */     v = fontDesc.getAsNumber(PdfName.ItalicAngle);
/* 200 */     if (v != null) {
/* 201 */       font.setItalicAngle(v.intValue());
/*     */     }
/* 203 */     v = fontDesc.getAsNumber(PdfName.StemV);
/* 204 */     if (v != null) {
/* 205 */       font.setStemV(v.intValue());
/*     */     }
/* 207 */     v = fontDesc.getAsNumber(PdfName.StemH);
/* 208 */     if (v != null) {
/* 209 */       font.setStemH(v.intValue());
/*     */     }
/* 211 */     v = fontDesc.getAsNumber(PdfName.FontWeight);
/* 212 */     if (v != null) {
/* 213 */       font.setFontWeight(v.intValue());
/*     */     }
/* 215 */     v = fontDesc.getAsNumber(PdfName.MissingWidth);
/* 216 */     if (v != null) {
/* 217 */       font.missingWidth = v.intValue();
/*     */     }
/*     */     
/* 220 */     PdfName fontStretch = fontDesc.getAsName(PdfName.FontStretch);
/* 221 */     if (fontStretch != null) {
/* 222 */       font.setFontStretch(fontStretch.getValue());
/*     */     }
/*     */     
/* 225 */     PdfArray bboxValue = fontDesc.getAsArray(PdfName.FontBBox);
/* 226 */     if (bboxValue != null) {
/* 227 */       int[] bbox = new int[4];
/*     */       
/* 229 */       bbox[0] = bboxValue.getAsNumber(0).intValue();
/*     */       
/* 231 */       bbox[1] = bboxValue.getAsNumber(1).intValue();
/*     */       
/* 233 */       bbox[2] = bboxValue.getAsNumber(2).intValue();
/*     */       
/* 235 */       bbox[3] = bboxValue.getAsNumber(3).intValue();
/* 236 */       if (bbox[0] > bbox[2]) {
/* 237 */         int t = bbox[0];
/* 238 */         bbox[0] = bbox[2];
/* 239 */         bbox[2] = t;
/*     */       } 
/* 241 */       if (bbox[1] > bbox[3]) {
/* 242 */         int t = bbox[1];
/* 243 */         bbox[1] = bbox[3];
/* 244 */         bbox[3] = t;
/*     */       } 
/* 246 */       font.setBbox(bbox);
/*     */ 
/*     */ 
/*     */       
/* 250 */       if (font.getFontMetrics().getTypoAscender() == 0 && font.getFontMetrics().getTypoDescender() == 0) {
/* 251 */         float maxAscent = Math.max(bbox[3], font.getFontMetrics().getTypoAscender());
/* 252 */         float minDescent = Math.min(bbox[1], font.getFontMetrics().getTypoDescender());
/* 253 */         font.setTypoAscender((int)(maxAscent * 1000.0F / (maxAscent - minDescent)));
/* 254 */         font.setTypoDescender((int)(minDescent * 1000.0F / (maxAscent - minDescent)));
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     PdfString fontFamily = fontDesc.getAsString(PdfName.FontFamily);
/* 259 */     if (fontFamily != null) {
/* 260 */       font.setFontFamily(fontFamily.getValue());
/*     */     }
/*     */     
/* 263 */     PdfNumber flagsValue = fontDesc.getAsNumber(PdfName.Flags);
/* 264 */     if (flagsValue != null) {
/* 265 */       int flags = flagsValue.intValue();
/* 266 */       if ((flags & 0x1) != 0) {
/* 267 */         font.setFixedPitch(true);
/*     */       }
/* 269 */       if ((flags & 0x40000) != 0) {
/* 270 */         font.setBold(true);
/*     */       }
/*     */     } 
/*     */     
/* 274 */     PdfName[] fontFileNames = { PdfName.FontFile, PdfName.FontFile2, PdfName.FontFile3 };
/* 275 */     for (PdfName fontFile : fontFileNames) {
/* 276 */       if (fontDesc.containsKey(fontFile)) {
/* 277 */         font.fontFileName = fontFile;
/* 278 */         font.fontFile = fontDesc.getAsStream(fontFile);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/DocTrueTypeFont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */