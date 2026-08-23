/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontEncoding;
/*     */ import com.itextpdf.io.font.FontMetrics;
/*     */ import com.itextpdf.io.font.FontNames;
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.cmap.CMapToUnicode;
/*     */ import com.itextpdf.io.font.constants.FontDescriptorFlags;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.font.otf.GlyphLine;
/*     */ import com.itextpdf.io.util.ArrayUtil;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfOutputStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
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
/*     */ public abstract class PdfSimpleFont<T extends FontProgram>
/*     */   extends PdfFont
/*     */ {
/*     */   private static final long serialVersionUID = -4942318223894676176L;
/*     */   protected FontEncoding fontEncoding;
/*     */   protected boolean forceWidthsOutput = false;
/*  82 */   protected byte[] shortTag = new byte[256];
/*     */ 
/*     */ 
/*     */   
/*     */   protected CMapToUnicode toUnicode;
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfSimpleFont(PdfDictionary fontDictionary) {
/*  91 */     super(fontDictionary);
/*  92 */     this.toUnicode = FontUtil.processToUnicode(fontDictionary.get(PdfName.ToUnicode));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfSimpleFont() {}
/*     */ 
/*     */   
/*     */   public boolean isBuiltWith(String fontProgram, String encoding) {
/* 101 */     return (getFontProgram().isBuiltWith(fontProgram) && this.fontEncoding
/* 102 */       .isBuiltWith(encoding));
/*     */   }
/*     */ 
/*     */   
/*     */   public GlyphLine createGlyphLine(String content) {
/* 107 */     List<Glyph> glyphs = new ArrayList<>(content.length());
/* 108 */     if (this.fontEncoding.isFontSpecific()) {
/* 109 */       for (int i = 0; i < content.length(); i++) {
/* 110 */         Glyph glyph = this.fontProgram.getGlyphByCode(content.charAt(i));
/* 111 */         if (glyph != null) {
/* 112 */           glyphs.add(glyph);
/*     */         }
/*     */       } 
/*     */     } else {
/* 116 */       for (int i = 0; i < content.length(); i++) {
/* 117 */         Glyph glyph = getGlyph(content.charAt(i));
/* 118 */         if (glyph != null) {
/* 119 */           glyphs.add(glyph);
/*     */         }
/*     */       } 
/*     */     } 
/* 123 */     return new GlyphLine(glyphs);
/*     */   }
/*     */ 
/*     */   
/*     */   public int appendGlyphs(String text, int from, int to, List<Glyph> glyphs) {
/* 128 */     int processed = 0;
/*     */     
/* 130 */     if (this.fontEncoding.isFontSpecific()) {
/* 131 */       for (int i = from; i <= to; ) {
/* 132 */         Glyph glyph = this.fontProgram.getGlyphByCode(text.charAt(i) & 0xFF);
/* 133 */         if (glyph != null) {
/* 134 */           glyphs.add(glyph);
/* 135 */           processed++;
/*     */           
/*     */           i++;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 141 */       for (int i = from; i <= to; i++) {
/* 142 */         Glyph glyph = getGlyph(text.charAt(i));
/* 143 */         if (glyph != null && (containsGlyph(glyph.getUnicode()) || isAppendableGlyph(glyph))) {
/* 144 */           glyphs.add(glyph);
/* 145 */           processed++;
/* 146 */         } else if (glyph == null && TextUtil.isWhitespaceOrNonPrintable(text.charAt(i))) {
/* 147 */           processed++;
/*     */         } else {
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     return processed;
/*     */   }
/*     */ 
/*     */   
/*     */   public int appendAnyGlyph(String text, int from, List<Glyph> glyphs) {
/*     */     Glyph glyph;
/* 160 */     if (this.fontEncoding.isFontSpecific()) {
/* 161 */       glyph = this.fontProgram.getGlyphByCode(text.charAt(from));
/*     */     } else {
/* 163 */       glyph = getGlyph(text.charAt(from));
/*     */     } 
/*     */     
/* 166 */     if (glyph != null) {
/* 167 */       glyphs.add(glyph);
/*     */     }
/* 169 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAppendableGlyph(Glyph glyph) {
/* 180 */     return (glyph.getCode() > 0 || TextUtil.isWhitespaceOrNonPrintable(glyph.getUnicode()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontEncoding getFontEncoding() {
/* 189 */     return this.fontEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] convertToBytes(String text) {
/* 194 */     byte[] bytes = this.fontEncoding.convertToBytes(text);
/* 195 */     for (byte b : bytes) {
/* 196 */       this.shortTag[b & 0xFF] = 1;
/*     */     }
/* 198 */     return bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] convertToBytes(GlyphLine glyphLine) {
/* 203 */     if (glyphLine != null) {
/* 204 */       byte[] bytes = new byte[glyphLine.size()];
/* 205 */       int ptr = 0;
/* 206 */       if (this.fontEncoding.isFontSpecific()) {
/* 207 */         for (int i = 0; i < glyphLine.size(); i++) {
/* 208 */           bytes[ptr++] = (byte)glyphLine.get(i).getCode();
/*     */         }
/*     */       } else {
/* 211 */         for (int i = 0; i < glyphLine.size(); i++) {
/* 212 */           if (this.fontEncoding.canEncode(glyphLine.get(i).getUnicode())) {
/* 213 */             bytes[ptr++] = (byte)this.fontEncoding.convertToByte(glyphLine.get(i).getUnicode());
/*     */           }
/*     */         } 
/*     */       } 
/* 217 */       bytes = ArrayUtil.shortenArray(bytes, ptr);
/* 218 */       for (byte b : bytes) {
/* 219 */         this.shortTag[b & 0xFF] = 1;
/*     */       }
/* 221 */       return bytes;
/*     */     } 
/* 223 */     return EMPTY_BYTES;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] convertToBytes(Glyph glyph) {
/* 229 */     byte[] bytes = new byte[1];
/* 230 */     if (this.fontEncoding.isFontSpecific()) {
/* 231 */       bytes[0] = (byte)glyph.getCode();
/*     */     }
/* 233 */     else if (this.fontEncoding.canEncode(glyph.getUnicode())) {
/* 234 */       bytes[0] = (byte)this.fontEncoding.convertToByte(glyph.getUnicode());
/*     */     } else {
/* 236 */       return EMPTY_BYTES;
/*     */     } 
/*     */     
/* 239 */     this.shortTag[bytes[0] & 0xFF] = 1;
/* 240 */     return bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeText(GlyphLine text, int from, int to, PdfOutputStream stream) {
/* 245 */     byte[] bytes = new byte[to - from + 1];
/* 246 */     int ptr = 0;
/*     */     
/* 248 */     if (this.fontEncoding.isFontSpecific()) {
/* 249 */       for (int i = from; i <= to; i++) {
/* 250 */         bytes[ptr++] = (byte)text.get(i).getCode();
/*     */       }
/*     */     } else {
/* 253 */       for (int i = from; i <= to; i++) {
/* 254 */         if (this.fontEncoding.canEncode(text.get(i).getUnicode())) {
/* 255 */           bytes[ptr++] = (byte)this.fontEncoding.convertToByte(text.get(i).getUnicode());
/*     */         }
/*     */       } 
/*     */     } 
/* 259 */     bytes = ArrayUtil.shortenArray(bytes, ptr);
/* 260 */     for (byte b : bytes) {
/* 261 */       this.shortTag[b & 0xFF] = 1;
/*     */     }
/* 263 */     StreamUtil.writeEscapedString((OutputStream)stream, bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeText(String text, PdfOutputStream stream) {
/* 268 */     StreamUtil.writeEscapedString((OutputStream)stream, convertToBytes(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public String decode(PdfString content) {
/* 273 */     return decodeIntoGlyphLine(content).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlyphLine decodeIntoGlyphLine(PdfString content) {
/* 281 */     byte[] contentBytes = content.getValueBytes();
/* 282 */     List<Glyph> glyphs = new ArrayList<>(contentBytes.length);
/* 283 */     for (byte b : contentBytes) {
/* 284 */       int code = b & 0xFF;
/* 285 */       Glyph glyph = null;
/* 286 */       if (this.toUnicode != null && this.toUnicode.lookup(code) != null && (glyph = this.fontProgram.getGlyphByCode(code)) != null) {
/* 287 */         if (!Arrays.equals(this.toUnicode.lookup(code), glyph.getChars())) {
/*     */           
/* 289 */           glyph = new Glyph(glyph);
/* 290 */           glyph.setChars(this.toUnicode.lookup(code));
/*     */         } 
/*     */       } else {
/* 293 */         int uni = this.fontEncoding.getUnicode(code);
/* 294 */         if (uni > -1) {
/* 295 */           glyph = getGlyph(uni);
/* 296 */         } else if (this.fontEncoding.getBaseEncoding() == null) {
/* 297 */           glyph = this.fontProgram.getGlyphByCode(code);
/*     */         } 
/*     */       } 
/* 300 */       if (glyph != null) {
/* 301 */         glyphs.add(glyph);
/*     */       }
/*     */     } 
/* 304 */     return new GlyphLine(glyphs);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getContentWidth(PdfString content) {
/* 309 */     float width = 0.0F;
/* 310 */     GlyphLine glyphLine = decodeIntoGlyphLine(content);
/* 311 */     for (int i = glyphLine.start; i < glyphLine.end; i++) {
/* 312 */       width += glyphLine.get(i).getWidth();
/*     */     }
/* 314 */     return width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isForceWidthsOutput() {
/* 323 */     return this.forceWidthsOutput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForceWidthsOutput(boolean forceWidthsOutput) {
/* 332 */     this.forceWidthsOutput = forceWidthsOutput;
/*     */   }
/*     */   
/*     */   protected void flushFontData(String fontName, PdfName subtype) {
/* 336 */     ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)subtype);
/* 337 */     if (fontName != null && fontName.length() > 0) {
/* 338 */       ((PdfDictionary)getPdfObject()).put(PdfName.BaseFont, (PdfObject)new PdfName(fontName));
/*     */     }
/*     */     
/*     */     int firstChar;
/* 342 */     for (firstChar = 0; firstChar <= 255 && 
/* 343 */       this.shortTag[firstChar] == 0; firstChar++);
/*     */     int lastChar;
/* 345 */     for (lastChar = 255; lastChar >= firstChar && 
/* 346 */       this.shortTag[lastChar] == 0; lastChar--);
/*     */     
/* 348 */     if (firstChar > 255) {
/* 349 */       firstChar = 255;
/* 350 */       lastChar = 255;
/*     */     } 
/* 352 */     if (!isSubset() || !isEmbedded()) {
/* 353 */       firstChar = 0;
/* 354 */       lastChar = this.shortTag.length - 1;
/* 355 */       for (int k = 0; k < this.shortTag.length; k++) {
/*     */ 
/*     */         
/* 358 */         if (this.fontEncoding.canDecode(k)) {
/* 359 */           this.shortTag[k] = 1;
/* 360 */         } else if (!this.fontEncoding.hasDifferences() && this.fontProgram.getGlyphByCode(k) != null) {
/* 361 */           this.shortTag[k] = 1;
/*     */         } else {
/* 363 */           this.shortTag[k] = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/* 367 */     if (this.fontEncoding.hasDifferences()) {
/*     */       int k;
/* 369 */       for (k = firstChar; k <= lastChar; k++) {
/* 370 */         if (!".notdef".equals(this.fontEncoding.getDifference(k))) {
/* 371 */           firstChar = k;
/*     */           break;
/*     */         } 
/*     */       } 
/* 375 */       for (k = lastChar; k >= firstChar; k--) {
/* 376 */         if (!".notdef".equals(this.fontEncoding.getDifference(k))) {
/* 377 */           lastChar = k;
/*     */           break;
/*     */         } 
/*     */       } 
/* 381 */       PdfDictionary enc = new PdfDictionary();
/* 382 */       enc.put(PdfName.Type, (PdfObject)PdfName.Encoding);
/* 383 */       PdfArray diff = new PdfArray();
/* 384 */       boolean gap = true;
/* 385 */       for (int i = firstChar; i <= lastChar; i++) {
/* 386 */         if (this.shortTag[i] != 0) {
/* 387 */           if (gap) {
/* 388 */             diff.add((PdfObject)new PdfNumber(i));
/* 389 */             gap = false;
/*     */           } 
/* 391 */           diff.add((PdfObject)new PdfName(this.fontEncoding.getDifference(i)));
/*     */         } else {
/* 393 */           gap = true;
/*     */         } 
/*     */       } 
/* 396 */       enc.put(PdfName.Differences, (PdfObject)diff);
/* 397 */       ((PdfDictionary)getPdfObject()).put(PdfName.Encoding, (PdfObject)enc);
/* 398 */     } else if (!this.fontEncoding.isFontSpecific()) {
/* 399 */       ((PdfDictionary)getPdfObject()).put(PdfName.Encoding, "Cp1252".equals(this.fontEncoding.getBaseEncoding()) ? (PdfObject)PdfName.WinAnsiEncoding : (PdfObject)PdfName.MacRomanEncoding);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 404 */     if (isForceWidthsOutput() || !isBuiltInFont() || this.fontEncoding.hasDifferences()) {
/* 405 */       ((PdfDictionary)getPdfObject()).put(PdfName.FirstChar, (PdfObject)new PdfNumber(firstChar));
/* 406 */       ((PdfDictionary)getPdfObject()).put(PdfName.LastChar, (PdfObject)new PdfNumber(lastChar));
/* 407 */       PdfArray wd = new PdfArray();
/* 408 */       for (int k = firstChar; k <= lastChar; k++) {
/* 409 */         if (this.shortTag[k] == 0) {
/* 410 */           wd.add((PdfObject)new PdfNumber(0));
/*     */         } else {
/*     */           
/* 413 */           int uni = this.fontEncoding.getUnicode(k);
/* 414 */           Glyph glyph = (uni > -1) ? getGlyph(uni) : this.fontProgram.getGlyphByCode(k);
/* 415 */           wd.add((PdfObject)new PdfNumber(getGlyphWidth(glyph)));
/*     */         } 
/*     */       } 
/* 418 */       ((PdfDictionary)getPdfObject()).put(PdfName.Widths, (PdfObject)wd);
/*     */     } 
/* 420 */     PdfDictionary fontDescriptor = !isBuiltInFont() ? getFontDescriptor(fontName) : null;
/* 421 */     if (fontDescriptor != null) {
/* 422 */       ((PdfDictionary)getPdfObject()).put(PdfName.FontDescriptor, (PdfObject)fontDescriptor);
/* 423 */       if (fontDescriptor.getIndirectReference() != null) {
/* 424 */         fontDescriptor.flush();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isBuiltInFont() {
/* 434 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfDictionary getFontDescriptor(String fontName) {
/* 444 */     assert fontName != null && fontName.length() > 0;
/* 445 */     FontMetrics fontMetrics = this.fontProgram.getFontMetrics();
/* 446 */     FontNames fontNames = this.fontProgram.getFontNames();
/* 447 */     PdfDictionary fontDescriptor = new PdfDictionary();
/* 448 */     makeObjectIndirect((PdfObject)fontDescriptor);
/* 449 */     fontDescriptor.put(PdfName.Type, (PdfObject)PdfName.FontDescriptor);
/* 450 */     fontDescriptor.put(PdfName.FontName, (PdfObject)new PdfName(fontName));
/* 451 */     fontDescriptor.put(PdfName.Ascent, (PdfObject)new PdfNumber(fontMetrics.getTypoAscender()));
/* 452 */     fontDescriptor.put(PdfName.CapHeight, (PdfObject)new PdfNumber(fontMetrics.getCapHeight()));
/* 453 */     fontDescriptor.put(PdfName.Descent, (PdfObject)new PdfNumber(fontMetrics.getTypoDescender()));
/* 454 */     fontDescriptor.put(PdfName.FontBBox, (PdfObject)new PdfArray(ArrayUtil.cloneArray(fontMetrics.getBbox())));
/* 455 */     fontDescriptor.put(PdfName.ItalicAngle, (PdfObject)new PdfNumber(fontMetrics.getItalicAngle()));
/* 456 */     fontDescriptor.put(PdfName.StemV, (PdfObject)new PdfNumber(fontMetrics.getStemV()));
/* 457 */     if (fontMetrics.getXHeight() > 0) {
/* 458 */       fontDescriptor.put(PdfName.XHeight, (PdfObject)new PdfNumber(fontMetrics.getXHeight()));
/*     */     }
/* 460 */     if (fontMetrics.getStemH() > 0) {
/* 461 */       fontDescriptor.put(PdfName.StemH, (PdfObject)new PdfNumber(fontMetrics.getStemH()));
/*     */     }
/* 463 */     if (fontNames.getFontWeight() > 0) {
/* 464 */       fontDescriptor.put(PdfName.FontWeight, (PdfObject)new PdfNumber(fontNames.getFontWeight()));
/*     */     }
/* 466 */     if (fontNames.getFamilyName() != null && (fontNames.getFamilyName()).length > 0 && (fontNames.getFamilyName()[0]).length >= 4) {
/* 467 */       fontDescriptor.put(PdfName.FontFamily, (PdfObject)new PdfString(fontNames.getFamilyName()[0][3]));
/*     */     }
/*     */     
/* 470 */     addFontStream(fontDescriptor);
/* 471 */     int flags = this.fontProgram.getPdfFontFlags();
/*     */     
/* 473 */     flags &= (FontDescriptorFlags.Symbolic | FontDescriptorFlags.Nonsymbolic) ^ 0xFFFFFFFF;
/*     */     
/* 475 */     flags |= this.fontEncoding.isFontSpecific() ? FontDescriptorFlags.Symbolic : FontDescriptorFlags.Nonsymbolic;
/*     */ 
/*     */     
/* 478 */     fontDescriptor.put(PdfName.Flags, (PdfObject)new PdfNumber(flags));
/* 479 */     return fontDescriptor;
/*     */   }
/*     */   
/*     */   protected abstract void addFontStream(PdfDictionary paramPdfDictionary);
/*     */   
/*     */   protected void setFontProgram(T fontProgram) {
/* 485 */     this.fontProgram = (FontProgram)fontProgram;
/*     */   }
/*     */   
/*     */   protected double getGlyphWidth(Glyph glyph) {
/* 489 */     return (glyph != null) ? glyph.getWidth() : 0.0D;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/PdfSimpleFont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */