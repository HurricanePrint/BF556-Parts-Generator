/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.font.otf.GlyphLine;
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfOutputStream;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public abstract class PdfFont
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   public static final int SIMPLE_FONT_MAX_CHAR_CODE_VALUE = 255;
/*     */   private static final long serialVersionUID = -7661159455613720321L;
/*     */   protected FontProgram fontProgram;
/*  79 */   protected static final byte[] EMPTY_BYTES = new byte[0];
/*  80 */   protected static final double[] DEFAULT_FONT_MATRIX = new double[] { 0.001D, 0.0D, 0.0D, 0.001D, 0.0D, 0.0D };
/*     */   
/*  82 */   protected Map<Integer, Glyph> notdefGlyphs = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean newFont = true;
/*     */ 
/*     */   
/*     */   protected boolean embedded = false;
/*     */ 
/*     */   
/*     */   protected boolean subset = true;
/*     */ 
/*     */   
/*     */   protected List<int[]> subsetRanges;
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfFont(PdfDictionary fontDictionary) {
/* 100 */     super((PdfObject)fontDictionary);
/* 101 */     ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.Font);
/*     */   }
/*     */   
/*     */   protected PdfFont() {
/* 105 */     super((PdfObject)new PdfDictionary());
/* 106 */     ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.Font);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Glyph getGlyph(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsGlyph(int unicode) {
/* 125 */     Glyph glyph = getGlyph(unicode);
/* 126 */     if (glyph != null) {
/* 127 */       if (getFontProgram() != null && getFontProgram().isFontSpecific())
/*     */       {
/* 129 */         return (glyph.getCode() > -1);
/*     */       }
/* 131 */       return (glyph.getCode() > 0);
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract GlyphLine createGlyphLine(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int appendGlyphs(String paramString, int paramInt1, int paramInt2, List<Glyph> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int appendAnyGlyph(String paramString, int paramInt, List<Glyph> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte[] convertToBytes(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte[] convertToBytes(GlyphLine paramGlyphLine);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String decode(PdfString paramPdfString);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract GlyphLine decodeIntoGlyphLine(PdfString paramPdfString);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract float getContentWidth(PdfString paramPdfString);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte[] convertToBytes(Glyph paramGlyph);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void writeText(GlyphLine paramGlyphLine, int paramInt1, int paramInt2, PdfOutputStream paramPdfOutputStream);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void writeText(String paramString, PdfOutputStream paramPdfOutputStream);
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getFontMatrix() {
/* 194 */     return DEFAULT_FONT_MATRIX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(int unicode) {
/* 204 */     Glyph glyph = getGlyph(unicode);
/* 205 */     return (glyph != null) ? glyph.getWidth() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth(int unicode, float fontSize) {
/* 216 */     return getWidth(unicode) * fontSize / 1000.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(String text) {
/* 226 */     int total = 0;
/* 227 */     for (int i = 0; i < text.length(); i++) {
/*     */       int ch;
/* 229 */       if (TextUtil.isSurrogatePair(text, i)) {
/* 230 */         ch = TextUtil.convertToUtf32(text, i);
/* 231 */         i++;
/*     */       } else {
/* 233 */         ch = text.charAt(i);
/*     */       } 
/* 235 */       Glyph glyph = getGlyph(ch);
/* 236 */       if (glyph != null) {
/* 237 */         total += glyph.getWidth();
/*     */       }
/*     */     } 
/* 240 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth(String text, float fontSize) {
/* 251 */     return getWidth(text) * fontSize / 1000.0F;
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
/*     */   public int getDescent(String text, float fontSize) {
/* 263 */     int min = 0;
/* 264 */     for (int k = 0; k < text.length(); k++) {
/*     */       int ch;
/* 266 */       if (TextUtil.isSurrogatePair(text, k)) {
/* 267 */         ch = TextUtil.convertToUtf32(text, k);
/* 268 */         k++;
/*     */       } else {
/* 270 */         ch = text.charAt(k);
/*     */       } 
/* 272 */       Glyph glyph = getGlyph(ch);
/* 273 */       if (glyph != null) {
/* 274 */         int[] bbox = glyph.getBbox();
/* 275 */         if (bbox != null && bbox[1] < min) {
/* 276 */           min = bbox[1];
/* 277 */         } else if (bbox == null && getFontProgram().getFontMetrics().getTypoDescender() < min) {
/* 278 */           min = getFontProgram().getFontMetrics().getTypoDescender();
/*     */         } 
/*     */       } 
/*     */     } 
/* 282 */     return (int)(min * fontSize / 1000.0F);
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
/*     */   public int getDescent(int unicode, float fontSize) {
/* 294 */     int min = 0;
/* 295 */     Glyph glyph = getGlyph(unicode);
/* 296 */     if (glyph == null) {
/* 297 */       return 0;
/*     */     }
/* 299 */     int[] bbox = glyph.getBbox();
/* 300 */     if (bbox != null && bbox[1] < min) {
/* 301 */       min = bbox[1];
/* 302 */     } else if (bbox == null && getFontProgram().getFontMetrics().getTypoDescender() < min) {
/* 303 */       min = getFontProgram().getFontMetrics().getTypoDescender();
/*     */     } 
/*     */     
/* 306 */     return (int)(min * fontSize / 1000.0F);
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
/*     */   public int getAscent(String text, float fontSize) {
/* 318 */     int max = 0;
/* 319 */     for (int k = 0; k < text.length(); k++) {
/*     */       int ch;
/* 321 */       if (TextUtil.isSurrogatePair(text, k)) {
/* 322 */         ch = TextUtil.convertToUtf32(text, k);
/* 323 */         k++;
/*     */       } else {
/* 325 */         ch = text.charAt(k);
/*     */       } 
/* 327 */       Glyph glyph = getGlyph(ch);
/* 328 */       if (glyph != null) {
/* 329 */         int[] bbox = glyph.getBbox();
/* 330 */         if (bbox != null && bbox[3] > max) {
/* 331 */           max = bbox[3];
/* 332 */         } else if (bbox == null && getFontProgram().getFontMetrics().getTypoAscender() > max) {
/* 333 */           max = getFontProgram().getFontMetrics().getTypoAscender();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 338 */     return (int)(max * fontSize / 1000.0F);
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
/*     */   public int getAscent(int unicode, float fontSize) {
/* 350 */     int max = 0;
/* 351 */     Glyph glyph = getGlyph(unicode);
/* 352 */     if (glyph == null) {
/* 353 */       return 0;
/*     */     }
/* 355 */     int[] bbox = glyph.getBbox();
/* 356 */     if (bbox != null && bbox[3] > max) {
/* 357 */       max = bbox[3];
/* 358 */     } else if (bbox == null && getFontProgram().getFontMetrics().getTypoAscender() > max) {
/* 359 */       max = getFontProgram().getFontMetrics().getTypoAscender();
/*     */     } 
/*     */     
/* 362 */     return (int)(max * fontSize / 1000.0F);
/*     */   }
/*     */   
/*     */   public FontProgram getFontProgram() {
/* 366 */     return this.fontProgram;
/*     */   }
/*     */   
/*     */   public boolean isEmbedded() {
/* 370 */     return this.embedded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSubset() {
/* 380 */     return this.subset;
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
/*     */   public void setSubset(boolean subset) {
/* 393 */     this.subset = subset;
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
/*     */   public void addSubsetRange(int[] range) {
/* 406 */     if (this.subsetRanges == null) {
/* 407 */       this.subsetRanges = (List)new ArrayList<>();
/*     */     }
/* 409 */     this.subsetRanges.add(range);
/* 410 */     setSubset(true);
/*     */   }
/*     */   
/*     */   public List<String> splitString(String text, float fontSize, float maxWidth) {
/* 414 */     List<String> resultString = new ArrayList<>();
/* 415 */     int lastWhiteSpace = 0;
/* 416 */     int startPos = 0;
/*     */     
/* 418 */     float tokenLength = 0.0F;
/* 419 */     for (int i = 0; i < text.length(); i++) {
/* 420 */       char ch = text.charAt(i);
/* 421 */       if (Character.isWhitespace(ch)) {
/* 422 */         lastWhiteSpace = i;
/*     */       }
/* 424 */       float currentCharWidth = getWidth(ch, fontSize);
/* 425 */       if (tokenLength + currentCharWidth >= maxWidth || ch == '\n') {
/* 426 */         if (startPos < lastWhiteSpace) {
/* 427 */           resultString.add(text.substring(startPos, lastWhiteSpace));
/* 428 */           startPos = lastWhiteSpace + 1;
/* 429 */           tokenLength = 0.0F;
/* 430 */           i = lastWhiteSpace;
/* 431 */         } else if (startPos != i) {
/* 432 */           resultString.add(text.substring(startPos, i));
/* 433 */           startPos = i;
/* 434 */           tokenLength = currentCharWidth;
/*     */         } else {
/* 436 */           resultString.add(text.substring(startPos, startPos + 1));
/* 437 */           startPos = i + 1;
/* 438 */           tokenLength = 0.0F;
/*     */         } 
/*     */       } else {
/* 441 */         tokenLength += currentCharWidth;
/*     */       } 
/*     */     } 
/*     */     
/* 445 */     resultString.add(text.substring(startPos));
/* 446 */     return resultString;
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
/*     */   public boolean isBuiltWith(String fontProgram, String encoding) {
/* 462 */     return false;
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
/*     */   public void flush() {
/* 474 */     super.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract PdfDictionary getFontDescriptor(String paramString);
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 481 */     return true;
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
/*     */   protected static String updateSubsetPrefix(String fontName, boolean isSubset, boolean isEmbedded) {
/* 494 */     if (isSubset && isEmbedded) {
/* 495 */       StringBuilder s = new StringBuilder(fontName.length() + 7);
/* 496 */       for (int k = 0; k < 6; k++) {
/* 497 */         s.append((char)(int)(Math.random() * 26.0D + 65.0D));
/*     */       }
/* 499 */       return s.append('+').append(fontName).toString();
/*     */     } 
/* 501 */     return fontName;
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
/*     */   protected PdfStream getPdfFontStream(byte[] fontStreamBytes, int[] fontStreamLengths) {
/* 513 */     if (fontStreamBytes == null) {
/* 514 */       throw new PdfException("Font embedding issue.");
/*     */     }
/* 516 */     PdfStream fontStream = new PdfStream(fontStreamBytes);
/* 517 */     makeObjectIndirect((PdfObject)fontStream);
/* 518 */     for (int k = 0; k < fontStreamLengths.length; k++) {
/* 519 */       fontStream.put(new PdfName("Length" + (k + 1)), (PdfObject)new PdfNumber(fontStreamLengths[k]));
/*     */     }
/* 521 */     return fontStream;
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
/*     */   @Deprecated
/*     */   protected static int[] compactRanges(List<int[]> ranges) {
/* 534 */     List<int[]> simp = (List)new ArrayList<>();
/* 535 */     for (int[] range : ranges) {
/* 536 */       for (int j = 0; j < range.length; j += 2) {
/* 537 */         simp.add(new int[] { Math.max(0, Math.min(range[j], range[j + 1])), Math.min(65535, Math.max(range[j], range[j + 1])) });
/*     */       } 
/*     */     } 
/* 540 */     for (int k1 = 0; k1 < simp.size() - 1; k1++) {
/* 541 */       for (int k2 = k1 + 1; k2 < simp.size(); k2++) {
/* 542 */         int[] r1 = simp.get(k1);
/* 543 */         int[] r2 = simp.get(k2);
/* 544 */         if ((r1[0] >= r2[0] && r1[0] <= r2[1]) || (r1[1] >= r2[0] && r1[0] <= r2[1])) {
/* 545 */           r1[0] = Math.min(r1[0], r2[0]);
/* 546 */           r1[1] = Math.max(r1[1], r2[1]);
/* 547 */           simp.remove(k2);
/* 548 */           k2--;
/*     */         } 
/*     */       } 
/*     */     } 
/* 552 */     int[] s = new int[simp.size() * 2];
/* 553 */     for (int k = 0; k < simp.size(); k++) {
/* 554 */       int[] r = simp.get(k);
/* 555 */       s[k * 2] = r[0];
/* 556 */       s[k * 2 + 1] = r[1];
/*     */     } 
/* 558 */     return s;
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
/*     */   boolean makeObjectIndirect(PdfObject obj) {
/* 570 */     if (((PdfDictionary)getPdfObject()).getIndirectReference() != null) {
/* 571 */       obj.makeIndirect(((PdfDictionary)getPdfObject()).getIndirectReference().getDocument());
/* 572 */       return true;
/*     */     } 
/* 574 */     markObjectAsIndirect(obj);
/* 575 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 581 */     return "PdfFont{fontProgram=" + this.fontProgram + '}';
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/PdfFont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */