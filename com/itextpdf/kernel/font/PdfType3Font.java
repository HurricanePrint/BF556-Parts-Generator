/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.AdobeGlyphList;
/*     */ import com.itextpdf.io.font.FontEncoding;
/*     */ import com.itextpdf.io.font.FontMetrics;
/*     */ import com.itextpdf.io.font.FontNames;
/*     */ import com.itextpdf.io.font.constants.FontDescriptorFlags;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfType3Font
/*     */   extends PdfSimpleFont<Type3Font>
/*     */ {
/*     */   private static final long serialVersionUID = 4940119184993066859L;
/*  83 */   private double[] fontMatrix = DEFAULT_FONT_MATRIX;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double dimensionsMultiplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfType3Font(PdfDocument document, boolean colorized) {
/* 103 */     makeIndirect(document);
/* 104 */     this.subset = true;
/* 105 */     this.embedded = true;
/* 106 */     this.fontProgram = new Type3Font(colorized);
/* 107 */     this.fontEncoding = FontEncoding.createEmptyFontEncoding();
/* 108 */     this.dimensionsMultiplier = 1.0D;
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
/*     */   PdfType3Font(PdfDocument document, String fontName, String fontFamily, boolean colorized) {
/* 120 */     this(document, colorized);
/* 121 */     ((Type3Font)this.fontProgram).setFontName(fontName);
/* 122 */     ((Type3Font)this.fontProgram).setFontFamily(fontFamily);
/* 123 */     this.dimensionsMultiplier = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfType3Font(PdfDictionary fontDictionary) {
/* 132 */     super(fontDictionary);
/* 133 */     this.subset = true;
/* 134 */     this.embedded = true;
/* 135 */     this.fontProgram = new Type3Font(false);
/* 136 */     this.fontEncoding = DocFontEncoding.createDocFontEncoding(fontDictionary.get(PdfName.Encoding), this.toUnicode);
/* 137 */     PdfDictionary charProcsDic = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.CharProcs);
/* 138 */     PdfArray fontMatrixArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.FontMatrix);
/*     */     
/* 140 */     double[] fontMatrix = new double[6]; int i;
/* 141 */     for (i = 0; i < fontMatrixArray.size(); i++) {
/* 142 */       fontMatrix[i] = ((PdfNumber)fontMatrixArray.get(i)).getValue();
/*     */     }
/* 144 */     setDimensionsMultiplier(fontMatrix[0] * 1000.0D);
/* 145 */     for (i = 0; i < 6; i++) {
/* 146 */       fontMatrix[i] = fontMatrix[i] / getDimensionsMultiplier();
/*     */     }
/* 148 */     setFontMatrix(fontMatrix);
/*     */ 
/*     */     
/* 151 */     if (((PdfDictionary)getPdfObject()).containsKey(PdfName.FontBBox)) {
/* 152 */       PdfArray fontBBox = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.FontBBox);
/* 153 */       this.fontProgram.getFontMetrics().setBbox((int)(fontBBox.getAsNumber(0).doubleValue() * getDimensionsMultiplier()), 
/* 154 */           (int)(fontBBox.getAsNumber(1).doubleValue() * getDimensionsMultiplier()), 
/* 155 */           (int)(fontBBox.getAsNumber(2).doubleValue() * getDimensionsMultiplier()), 
/* 156 */           (int)(fontBBox.getAsNumber(3).doubleValue() * getDimensionsMultiplier()));
/*     */     } else {
/* 158 */       this.fontProgram.getFontMetrics().setBbox(0, 0, 0, 0);
/*     */     } 
/* 160 */     int firstChar = normalizeFirstLastChar(fontDictionary.getAsNumber(PdfName.FirstChar), 0);
/* 161 */     int lastChar = normalizeFirstLastChar(fontDictionary.getAsNumber(PdfName.LastChar), 255);
/*     */     
/* 163 */     for (int j = firstChar; j <= lastChar; j++) {
/* 164 */       this.shortTag[j] = 1;
/*     */     }
/*     */     
/* 167 */     PdfArray pdfWidths = fontDictionary.getAsArray(PdfName.Widths);
/*     */     
/* 169 */     double[] multipliedWidths = new double[pdfWidths.size()];
/* 170 */     for (int k = 0; k < pdfWidths.size(); k++) {
/* 171 */       multipliedWidths[k] = pdfWidths.getAsNumber(k).doubleValue() * getDimensionsMultiplier();
/*     */     }
/* 173 */     PdfArray multipliedPdfWidths = new PdfArray(multipliedWidths);
/*     */     
/* 175 */     int[] widths = FontUtil.convertSimpleWidthsArray(multipliedPdfWidths, firstChar, 0);
/*     */     
/* 177 */     if (this.toUnicode != null && this.toUnicode.hasByteMappings() && this.fontEncoding.hasDifferences()) {
/* 178 */       for (int m = 0; m <= 255; m++) {
/* 179 */         int unicode = this.fontEncoding.getUnicode(m);
/* 180 */         PdfName glyphName = new PdfName(this.fontEncoding.getDifference(m));
/* 181 */         if (unicode != -1 && 
/* 182 */           !".notdef".equals(glyphName.getValue()) && charProcsDic
/* 183 */           .containsKey(glyphName)) {
/* 184 */           ((Type3Font)getFontProgram()).addGlyph(m, unicode, widths[m], null, new Type3Glyph(charProcsDic.getAsStream(glyphName), getDocument()));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 190 */     Map<Integer, Integer> unicodeToCode = null;
/* 191 */     if (this.toUnicode != null) {
/* 192 */       try { unicodeToCode = this.toUnicode.createReverseMapping(); } catch (Exception exception) {}
/*     */     }
/*     */     
/* 195 */     for (PdfName glyphName : charProcsDic.keySet()) {
/* 196 */       int unicode = AdobeGlyphList.nameToUnicode(glyphName.getValue());
/* 197 */       int code = -1;
/* 198 */       if (this.fontEncoding.canEncode(unicode)) {
/* 199 */         code = this.fontEncoding.convertToByte(unicode);
/* 200 */       } else if (unicodeToCode != null && unicodeToCode.containsKey(Integer.valueOf(unicode))) {
/* 201 */         code = ((Integer)unicodeToCode.get(Integer.valueOf(unicode))).intValue();
/*     */       } 
/* 203 */       if (code != -1 && getFontProgram().getGlyphByCode(code) == null) {
/* 204 */         ((Type3Font)getFontProgram()).addGlyph(code, unicode, widths[code], null, new Type3Glyph(charProcsDic
/* 205 */               .getAsStream(glyphName), getDocument()));
/*     */       }
/*     */     } 
/*     */     
/* 209 */     fillFontDescriptor(fontDictionary.getAsDictionary(PdfName.FontDescriptor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontName(String fontName) {
/* 218 */     ((Type3Font)this.fontProgram).setFontName(fontName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontFamily(String fontFamily) {
/* 227 */     ((Type3Font)this.fontProgram).setFontFamily(fontFamily);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontWeight(int fontWeight) {
/* 236 */     ((Type3Font)this.fontProgram).setFontWeight(fontWeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItalicAngle(int italicAngle) {
/* 247 */     ((Type3Font)this.fontProgram).setItalicAngle(italicAngle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontStretch(String fontWidth) {
/* 256 */     ((Type3Font)this.fontProgram).setFontStretch(fontWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPdfFontFlags(int flags) {
/* 266 */     ((Type3Font)this.fontProgram).setPdfFontFlags(flags);
/*     */   }
/*     */ 
/*     */   
/*     */   public Type3Glyph getType3Glyph(int unicode) {
/* 271 */     return ((Type3Font)getFontProgram()).getType3Glyph(unicode);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSubset() {
/* 276 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmbedded() {
/* 281 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] getFontMatrix() {
/* 286 */     return this.fontMatrix;
/*     */   }
/*     */   
/*     */   public void setFontMatrix(double[] fontMatrix) {
/* 290 */     this.fontMatrix = fontMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfGlyphs() {
/* 299 */     return ((Type3Font)getFontProgram()).getNumberOfGlyphs();
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
/*     */   public Type3Glyph addGlyph(char c, int wx, int llx, int lly, int urx, int ury) {
/* 318 */     Type3Glyph glyph = getType3Glyph(c);
/* 319 */     if (glyph != null) {
/* 320 */       return glyph;
/*     */     }
/* 322 */     int code = getFirstEmptyCode();
/* 323 */     glyph = new Type3Glyph(getDocument(), wx, llx, lly, urx, ury, ((Type3Font)getFontProgram()).isColorized());
/* 324 */     ((Type3Font)getFontProgram()).addGlyph(code, c, wx, new int[] { llx, lly, urx, ury }, glyph);
/* 325 */     this.fontEncoding.addSymbol((byte)code, c);
/*     */     
/* 327 */     if (!((Type3Font)getFontProgram()).isColorized()) {
/* 328 */       if (this.fontProgram.countOfGlyphs() == 0) {
/* 329 */         this.fontProgram.getFontMetrics().setBbox(llx, lly, urx, ury);
/*     */       } else {
/* 331 */         int[] bbox = this.fontProgram.getFontMetrics().getBbox();
/* 332 */         int newLlx = Math.min(bbox[0], llx);
/* 333 */         int newLly = Math.min(bbox[1], lly);
/* 334 */         int newUrx = Math.max(bbox[2], urx);
/* 335 */         int newUry = Math.max(bbox[3], ury);
/* 336 */         this.fontProgram.getFontMetrics().setBbox(newLlx, newLly, newUrx, newUry);
/*     */       } 
/*     */     }
/* 339 */     return glyph;
/*     */   }
/*     */ 
/*     */   
/*     */   public Glyph getGlyph(int unicode) {
/* 344 */     if (this.fontEncoding.canEncode(unicode) || unicode < 33) {
/* 345 */       Glyph glyph = getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode));
/* 346 */       if (glyph == null && (glyph = this.notdefGlyphs.get(Integer.valueOf(unicode))) == null) {
/*     */ 
/*     */         
/* 349 */         glyph = new Glyph(-1, 0, unicode);
/* 350 */         this.notdefGlyphs.put(Integer.valueOf(unicode), glyph);
/*     */       } 
/* 352 */       return glyph;
/*     */     } 
/* 354 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsGlyph(int unicode) {
/* 359 */     return ((this.fontEncoding.canEncode(unicode) || unicode < 33) && 
/* 360 */       getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode)) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/* 365 */     if (isFlushed())
/* 366 */       return;  ensureUnderlyingObjectHasIndirectReference();
/* 367 */     if (((Type3Font)getFontProgram()).getNumberOfGlyphs() < 1) {
/* 368 */       throw new PdfException("No glyphs defined for type3 font.");
/*     */     }
/*     */     
/* 371 */     PdfDictionary charProcs = new PdfDictionary(); int i;
/* 372 */     for (i = 0; i <= 255; i++) {
/* 373 */       if (this.fontEncoding.canDecode(i)) {
/* 374 */         Type3Glyph glyph = getType3Glyph(this.fontEncoding.getUnicode(i));
/* 375 */         if (glyph != null) {
/* 376 */           charProcs.put(new PdfName(this.fontEncoding.getDifference(i)), (PdfObject)glyph.getContentStream());
/* 377 */           glyph.getContentStream().flush();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 382 */     ((PdfDictionary)getPdfObject()).put(PdfName.CharProcs, (PdfObject)charProcs);
/*     */     
/* 384 */     for (i = 0; i < this.fontMatrix.length; i++) {
/* 385 */       this.fontMatrix[i] = this.fontMatrix[i] * getDimensionsMultiplier();
/*     */     }
/*     */     
/* 388 */     ((PdfDictionary)getPdfObject()).put(PdfName.FontMatrix, (PdfObject)new PdfArray(getFontMatrix()));
/* 389 */     ((PdfDictionary)getPdfObject()).put(PdfName.FontBBox, (PdfObject)normalizeBBox(this.fontProgram.getFontMetrics().getBbox()));
/*     */     
/* 391 */     String fontName = this.fontProgram.getFontNames().getFontName();
/* 392 */     flushFontData(fontName, PdfName.Type3);
/*     */     
/* 394 */     ((PdfDictionary)getPdfObject()).remove(PdfName.BaseFont);
/* 395 */     super.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfDictionary getFontDescriptor(String fontName) {
/* 400 */     if (fontName != null && fontName.length() > 0) {
/* 401 */       PdfDictionary fontDescriptor = new PdfDictionary();
/* 402 */       makeObjectIndirect((PdfObject)fontDescriptor);
/* 403 */       fontDescriptor.put(PdfName.Type, (PdfObject)PdfName.FontDescriptor);
/*     */       
/* 405 */       FontMetrics fontMetrics = this.fontProgram.getFontMetrics();
/* 406 */       fontDescriptor.put(PdfName.CapHeight, (PdfObject)new PdfNumber(fontMetrics.getCapHeight()));
/* 407 */       fontDescriptor.put(PdfName.ItalicAngle, (PdfObject)new PdfNumber(fontMetrics.getItalicAngle()));
/*     */       
/* 409 */       FontNames fontNames = this.fontProgram.getFontNames();
/* 410 */       fontDescriptor.put(PdfName.FontWeight, (PdfObject)new PdfNumber(fontNames.getFontWeight()));
/* 411 */       fontDescriptor.put(PdfName.FontName, (PdfObject)new PdfName(fontName));
/* 412 */       if (fontNames.getFamilyName() != null && (fontNames.getFamilyName()).length > 0 && (fontNames.getFamilyName()[0]).length >= 4) {
/* 413 */         fontDescriptor.put(PdfName.FontFamily, (PdfObject)new PdfString(fontNames.getFamilyName()[0][3]));
/*     */       }
/*     */       
/* 416 */       int flags = this.fontProgram.getPdfFontFlags();
/*     */       
/* 418 */       flags &= (FontDescriptorFlags.Symbolic | FontDescriptorFlags.Nonsymbolic) ^ 0xFFFFFFFF;
/*     */       
/* 420 */       flags |= this.fontEncoding.isFontSpecific() ? FontDescriptorFlags.Symbolic : FontDescriptorFlags.Nonsymbolic;
/*     */ 
/*     */       
/* 423 */       fontDescriptor.put(PdfName.Flags, (PdfObject)new PdfNumber(flags));
/* 424 */       return fontDescriptor;
/* 425 */     }  if (((PdfDictionary)getPdfObject()).getIndirectReference() != null && ((PdfDictionary)
/* 426 */       getPdfObject()).getIndirectReference().getDocument().isTagged()) {
/* 427 */       Logger logger = LoggerFactory.getLogger(PdfType3Font.class);
/* 428 */       logger.warn("Type 3 font issue. Font Descriptor is required for tagged PDF. FontName shall be specified.");
/*     */     } 
/* 430 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addFontStream(PdfDictionary fontDescriptor) {}
/*     */ 
/*     */   
/*     */   protected PdfDocument getDocument() {
/* 438 */     return ((PdfDictionary)getPdfObject()).getIndirectReference().getDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getGlyphWidth(Glyph glyph) {
/* 443 */     return (glyph != null) ? (glyph.getWidth() / getDimensionsMultiplier()) : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double getDimensionsMultiplier() {
/* 451 */     return this.dimensionsMultiplier;
/*     */   }
/*     */   
/*     */   void setDimensionsMultiplier(double dimensionsMultiplier) {
/* 455 */     this.dimensionsMultiplier = dimensionsMultiplier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getFirstEmptyCode() {
/* 464 */     int startFrom = 1;
/* 465 */     for (int i = 1; i <= 255; i++) {
/* 466 */       if (!this.fontEncoding.canDecode(i)) {
/* 467 */         return i;
/*     */       }
/*     */     } 
/* 470 */     return -1;
/*     */   }
/*     */   
/*     */   private void fillFontDescriptor(PdfDictionary fontDesc) {
/* 474 */     if (fontDesc == null) {
/*     */       return;
/*     */     }
/* 477 */     PdfNumber v = fontDesc.getAsNumber(PdfName.ItalicAngle);
/* 478 */     if (v != null) {
/* 479 */       setItalicAngle(v.intValue());
/*     */     }
/* 481 */     v = fontDesc.getAsNumber(PdfName.FontWeight);
/* 482 */     if (v != null) {
/* 483 */       setFontWeight(v.intValue());
/*     */     }
/*     */     
/* 486 */     PdfName fontStretch = fontDesc.getAsName(PdfName.FontStretch);
/* 487 */     if (fontStretch != null) {
/* 488 */       setFontStretch(fontStretch.getValue());
/*     */     }
/*     */     
/* 491 */     PdfName fontName = fontDesc.getAsName(PdfName.FontName);
/* 492 */     if (fontName != null) {
/* 493 */       setFontName(fontName.getValue());
/*     */     }
/*     */     
/* 496 */     PdfString fontFamily = fontDesc.getAsString(PdfName.FontFamily);
/* 497 */     if (fontFamily != null) {
/* 498 */       setFontFamily(fontFamily.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   private int normalizeFirstLastChar(PdfNumber firstLast, int defaultValue) {
/* 503 */     if (firstLast == null) return defaultValue; 
/* 504 */     int result = firstLast.intValue();
/* 505 */     return (result < 0 || result > 255) ? defaultValue : result;
/*     */   }
/*     */   
/*     */   private PdfArray normalizeBBox(int[] bBox) {
/* 509 */     double[] normalizedBBox = new double[4];
/* 510 */     for (int i = 0; i < 4; i++) {
/* 511 */       normalizedBBox[i] = bBox[i] / getDimensionsMultiplier();
/*     */     }
/* 513 */     return new PdfArray(normalizedBBox);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/PdfType3Font.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */