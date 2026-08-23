/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.CFFFontSubset;
/*     */ import com.itextpdf.io.font.CMapEncoding;
/*     */ import com.itextpdf.io.font.CidFont;
/*     */ import com.itextpdf.io.font.CidFontProperties;
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.FontProgramFactory;
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import com.itextpdf.io.font.TrueTypeFont;
/*     */ import com.itextpdf.io.font.cmap.CMapContentParser;
/*     */ import com.itextpdf.io.font.cmap.CMapToUnicode;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.font.otf.GlyphLine;
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.io.source.OutputStream;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfLiteral;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfOutputStream;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
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
/*     */ public class PdfType0Font
/*     */   extends PdfFont
/*     */ {
/*     */   private static final long serialVersionUID = -8033620300884193397L;
/*  90 */   private static final byte[] rotbits = new byte[] { Byte.MIN_VALUE, 64, 32, 16, 8, 4, 2, 1 };
/*     */ 
/*     */   
/*     */   protected static final int CID_FONT_TYPE_0 = 0;
/*     */ 
/*     */   
/*     */   protected static final int CID_FONT_TYPE_2 = 2;
/*     */   
/*     */   protected boolean vertical;
/*     */   
/*     */   protected CMapEncoding cmapEncoding;
/*     */   
/*     */   protected Set<Integer> longTag;
/*     */   
/*     */   protected int cidFontType;
/*     */   
/*     */   protected char[] specificUnicodeDifferences;
/*     */ 
/*     */   
/*     */   PdfType0Font(TrueTypeFont ttf, String cmap) {
/* 110 */     if (!"Identity-H".equals(cmap) && !"Identity-V".equals(cmap)) {
/* 111 */       throw new PdfException("Only Identity CMaps supports with truetype");
/*     */     }
/*     */     
/* 114 */     if (!ttf.getFontNames().allowEmbedding()) {
/* 115 */       throw (new PdfException("{0} cannot be embedded due to licensing restrictions."))
/* 116 */         .setMessageParams(new Object[] { ttf.getFontNames().getFontName() + ttf.getFontNames().getStyle() });
/*     */     }
/* 118 */     this.fontProgram = (FontProgram)ttf;
/* 119 */     this.embedded = true;
/* 120 */     this.vertical = cmap.endsWith("V");
/* 121 */     this.cmapEncoding = new CMapEncoding(cmap);
/* 122 */     this.longTag = new TreeSet<>();
/* 123 */     this.cidFontType = 2;
/* 124 */     if (ttf.isFontSpecific()) {
/* 125 */       this.specificUnicodeDifferences = new char[256];
/* 126 */       byte[] bytes = new byte[1];
/* 127 */       for (int k = 0; k < 256; k++) {
/* 128 */         bytes[0] = (byte)k;
/* 129 */         String s = PdfEncodings.convertToString(bytes, null);
/* 130 */         char ch = (s.length() > 0) ? s.charAt(0) : '?';
/* 131 */         this.specificUnicodeDifferences[k] = ch;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfType0Font(CidFont font, String cmap) {
/* 142 */     if (!CidFontProperties.isCidFont(font.getFontNames().getFontName(), cmap)) {
/* 143 */       throw (new PdfException("Font {0} with {1} encoding is not a cjk font."))
/* 144 */         .setMessageParams(new Object[] { font.getFontNames().getFontName(), cmap });
/*     */     }
/* 146 */     this.fontProgram = (FontProgram)font;
/* 147 */     this.vertical = cmap.endsWith("V");
/* 148 */     String uniMap = getCompatibleUniMap(this.fontProgram.getRegistry());
/* 149 */     this.cmapEncoding = new CMapEncoding(cmap, uniMap);
/* 150 */     this.longTag = new TreeSet<>();
/* 151 */     this.cidFontType = 0;
/*     */   }
/*     */   
/*     */   PdfType0Font(PdfDictionary fontDictionary) {
/* 155 */     super(fontDictionary);
/* 156 */     this.newFont = false;
/* 157 */     PdfDictionary cidFont = fontDictionary.getAsArray(PdfName.DescendantFonts).getAsDictionary(0);
/* 158 */     PdfObject cmap = fontDictionary.get(PdfName.Encoding);
/* 159 */     PdfObject toUnicode = fontDictionary.get(PdfName.ToUnicode);
/* 160 */     CMapToUnicode toUnicodeCMap = FontUtil.processToUnicode(toUnicode);
/* 161 */     if (cmap.isName() && ("Identity-H".equals(((PdfName)cmap).getValue()) || "Identity-V".equals(((PdfName)cmap).getValue()))) {
/* 162 */       if (toUnicodeCMap == null) {
/* 163 */         String uniMap = getUniMapFromOrdering(getOrdering(cidFont));
/* 164 */         toUnicodeCMap = FontUtil.getToUnicodeFromUniMap(uniMap);
/* 165 */         if (toUnicodeCMap == null) {
/* 166 */           toUnicodeCMap = FontUtil.getToUnicodeFromUniMap("Identity-H");
/* 167 */           Logger logger = LoggerFactory.getLogger(PdfType0Font.class);
/* 168 */           logger.error(MessageFormatUtil.format("Unknown CMap {0}", new Object[] { uniMap }));
/*     */         } 
/*     */       } 
/* 171 */       this.fontProgram = (FontProgram)DocTrueTypeFont.createFontProgram(cidFont, toUnicodeCMap);
/* 172 */       this.cmapEncoding = createCMap(cmap, (String)null);
/* 173 */       assert this.fontProgram instanceof IDocFontProgram;
/* 174 */       this.embedded = (((IDocFontProgram)this.fontProgram).getFontFile() != null);
/*     */     } else {
/* 176 */       String cidFontName = cidFont.getAsName(PdfName.BaseFont).getValue();
/* 177 */       String uniMap = getUniMapFromOrdering(getOrdering(cidFont));
/* 178 */       if (uniMap != null && uniMap.startsWith("Uni") && CidFontProperties.isCidFont(cidFontName, uniMap)) {
/*     */         try {
/* 180 */           this.fontProgram = FontProgramFactory.createFont(cidFontName);
/* 181 */           this.cmapEncoding = createCMap(cmap, uniMap);
/* 182 */           this.embedded = false;
/* 183 */         } catch (IOException ignored) {
/* 184 */           this.fontProgram = null;
/* 185 */           this.cmapEncoding = null;
/*     */         } 
/*     */       } else {
/* 188 */         if (toUnicodeCMap == null) {
/* 189 */           toUnicodeCMap = FontUtil.getToUnicodeFromUniMap(uniMap);
/*     */         }
/* 191 */         if (toUnicodeCMap != null) {
/* 192 */           this.fontProgram = (FontProgram)DocTrueTypeFont.createFontProgram(cidFont, toUnicodeCMap);
/* 193 */           this.cmapEncoding = createCMap(cmap, uniMap);
/*     */         } 
/*     */       } 
/* 196 */       if (this.fontProgram == null) {
/* 197 */         throw new PdfException(MessageFormatUtil.format("Cannot recognise document font {0} with {1} encoding", new Object[] { cidFontName, cmap }));
/*     */       }
/*     */     } 
/*     */     
/* 201 */     PdfDictionary cidFontDictionary = fontDictionary.getAsArray(PdfName.DescendantFonts).getAsDictionary(0);
/*     */     
/* 203 */     PdfName subtype = cidFontDictionary.getAsName(PdfName.Subtype);
/* 204 */     if (PdfName.CIDFontType0.equals(subtype)) {
/* 205 */       this.cidFontType = 0;
/* 206 */     } else if (PdfName.CIDFontType2.equals(subtype)) {
/* 207 */       this.cidFontType = 2;
/*     */     } else {
/* 209 */       LoggerFactory.getLogger(getClass()).error("Failed to determine CIDFont subtype. The type of CIDFont shall be CIDFontType0 or CIDFontType2.");
/*     */     } 
/* 211 */     this.longTag = new TreeSet<>();
/* 212 */     this.subset = false;
/*     */   }
/*     */   
/*     */   public static String getUniMapFromOrdering(String ordering) {
/* 216 */     switch (ordering) {
/*     */       case "CNS1":
/* 218 */         return "UniCNS-UTF16-H";
/*     */       case "Japan1":
/* 220 */         return "UniJIS-UTF16-H";
/*     */       case "Korea1":
/* 222 */         return "UniKS-UTF16-H";
/*     */       case "GB1":
/* 224 */         return "UniGB-UTF16-H";
/*     */       case "Identity":
/* 226 */         return "Identity-H";
/*     */     } 
/* 228 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Glyph getGlyph(int unicode) {
/* 235 */     Glyph glyph = getFontProgram().getGlyph(unicode);
/* 236 */     if (glyph == null && (glyph = this.notdefGlyphs.get(Integer.valueOf(unicode))) == null) {
/*     */ 
/*     */       
/* 239 */       Glyph notdef = getFontProgram().getGlyphByCode(0);
/* 240 */       if (notdef != null) {
/* 241 */         glyph = new Glyph(notdef, unicode);
/*     */       } else {
/* 243 */         glyph = new Glyph(-1, 0, unicode);
/*     */       } 
/* 245 */       this.notdefGlyphs.put(Integer.valueOf(unicode), glyph);
/*     */     } 
/* 247 */     return glyph;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsGlyph(int unicode) {
/* 252 */     if (this.cidFontType == 0) {
/* 253 */       if (this.cmapEncoding.isDirect()) {
/* 254 */         return (this.fontProgram.getGlyphByCode(unicode) != null);
/*     */       }
/* 256 */       return (getFontProgram().getGlyph(unicode) != null);
/*     */     } 
/* 258 */     if (this.cidFontType == 2) {
/* 259 */       if (this.fontProgram.isFontSpecific()) {
/* 260 */         byte[] b = PdfEncodings.convertToBytes((char)unicode, "symboltt");
/* 261 */         return (b.length > 0 && this.fontProgram.getGlyph(b[0] & 0xFF) != null);
/*     */       } 
/* 263 */       return (getFontProgram().getGlyph(unicode) != null);
/*     */     } 
/*     */     
/* 266 */     throw new PdfException("Invalid CID font type: " + this.cidFontType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] convertToBytes(String text) {
/* 272 */     int len = text.length();
/* 273 */     ByteBuffer buffer = new ByteBuffer();
/* 274 */     int i = 0;
/* 275 */     if (this.fontProgram.isFontSpecific()) {
/* 276 */       byte[] b = PdfEncodings.convertToBytes(text, "symboltt");
/* 277 */       len = b.length;
/* 278 */       for (int k = 0; k < len; k++) {
/* 279 */         Glyph glyph = this.fontProgram.getGlyph(b[k] & 0xFF);
/* 280 */         if (glyph != null) {
/* 281 */           convertToBytes(glyph, buffer);
/*     */         }
/*     */       } 
/*     */     } else {
/* 285 */       for (int k = 0; k < len; k++) {
/*     */         int val;
/* 287 */         if (TextUtil.isSurrogatePair(text, k)) {
/* 288 */           val = TextUtil.convertToUtf32(text, k);
/* 289 */           k++;
/*     */         } else {
/* 291 */           val = text.charAt(k);
/*     */         } 
/* 293 */         Glyph glyph = getGlyph(val);
/* 294 */         if (glyph.getCode() > 0) {
/* 295 */           convertToBytes(glyph, buffer);
/*     */         } else {
/*     */           
/* 298 */           int nullCode = this.cmapEncoding.getCmapCode(0);
/* 299 */           buffer.append(nullCode >> 8);
/* 300 */           buffer.append(nullCode);
/*     */         } 
/*     */       } 
/*     */     } 
/* 304 */     return buffer.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] convertToBytes(GlyphLine glyphLine) {
/* 309 */     if (glyphLine != null) {
/*     */       
/* 311 */       int totalByteCount = 0;
/* 312 */       for (int i = glyphLine.start; i < glyphLine.end; i++) {
/* 313 */         totalByteCount += this.cmapEncoding.getCmapBytesLength(glyphLine.get(i).getCode());
/*     */       }
/*     */       
/* 316 */       byte[] bytes = new byte[totalByteCount];
/* 317 */       int offset = 0;
/* 318 */       for (int j = glyphLine.start; j < glyphLine.end; j++) {
/* 319 */         this.longTag.add(Integer.valueOf(glyphLine.get(j).getCode()));
/* 320 */         offset = this.cmapEncoding.fillCmapBytes(glyphLine.get(j).getCode(), bytes, offset);
/*     */       } 
/* 322 */       return bytes;
/*     */     } 
/* 324 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] convertToBytes(Glyph glyph) {
/* 330 */     this.longTag.add(Integer.valueOf(glyph.getCode()));
/* 331 */     return this.cmapEncoding.getCmapBytes(glyph.getCode());
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeText(GlyphLine text, int from, int to, PdfOutputStream stream) {
/* 336 */     int len = to - from + 1;
/* 337 */     if (len > 0) {
/* 338 */       byte[] bytes = convertToBytes(new GlyphLine(text, from, to + 1));
/* 339 */       StreamUtil.writeHexedString((OutputStream)stream, bytes);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeText(String text, PdfOutputStream stream) {
/* 345 */     StreamUtil.writeHexedString((OutputStream)stream, convertToBytes(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public GlyphLine createGlyphLine(String content) {
/* 350 */     List<Glyph> glyphs = new ArrayList<>();
/* 351 */     if (this.cidFontType == 0) {
/* 352 */       int len = content.length();
/* 353 */       if (this.cmapEncoding.isDirect()) {
/* 354 */         for (int k = 0; k < len; k++) {
/* 355 */           Glyph glyph = this.fontProgram.getGlyphByCode(content.charAt(k));
/* 356 */           if (glyph != null) {
/* 357 */             glyphs.add(glyph);
/*     */           }
/*     */         } 
/*     */       } else {
/* 361 */         for (int k = 0; k < len; k++) {
/*     */           int ch;
/* 363 */           if (TextUtil.isSurrogatePair(content, k)) {
/* 364 */             ch = TextUtil.convertToUtf32(content, k);
/* 365 */             k++;
/*     */           } else {
/* 367 */             ch = content.charAt(k);
/*     */           } 
/* 369 */           glyphs.add(getGlyph(ch));
/*     */         } 
/*     */       } 
/* 372 */     } else if (this.cidFontType == 2) {
/* 373 */       int len = content.length();
/* 374 */       if (this.fontProgram.isFontSpecific()) {
/* 375 */         byte[] b = PdfEncodings.convertToBytes(content, "symboltt");
/* 376 */         len = b.length;
/* 377 */         for (int k = 0; k < len; k++) {
/* 378 */           Glyph glyph = this.fontProgram.getGlyph(b[k] & 0xFF);
/* 379 */           if (glyph != null) {
/* 380 */             glyphs.add(glyph);
/*     */           }
/*     */         } 
/*     */       } else {
/* 384 */         for (int k = 0; k < len; k++) {
/*     */           int val;
/* 386 */           if (TextUtil.isSurrogatePair(content, k)) {
/* 387 */             val = TextUtil.convertToUtf32(content, k);
/* 388 */             k++;
/*     */           } else {
/* 390 */             val = content.charAt(k);
/*     */           } 
/* 392 */           glyphs.add(getGlyph(val));
/*     */         } 
/*     */       } 
/*     */     } else {
/* 396 */       throw new PdfException("Font has no suitable cmap.");
/*     */     } 
/*     */     
/* 399 */     return new GlyphLine(glyphs);
/*     */   }
/*     */ 
/*     */   
/*     */   public int appendGlyphs(String text, int from, int to, List<Glyph> glyphs) {
/* 404 */     if (this.cidFontType == 0) {
/* 405 */       if (this.cmapEncoding.isDirect()) {
/* 406 */         int processed = 0;
/* 407 */         for (int k = from; k <= to; ) {
/* 408 */           Glyph glyph = this.fontProgram.getGlyphByCode(text.charAt(k));
/* 409 */           if (glyph != null && isAppendableGlyph(glyph)) {
/* 410 */             glyphs.add(glyph);
/* 411 */             processed++;
/*     */             
/*     */             k++;
/*     */           } 
/*     */         } 
/* 416 */         return processed;
/*     */       } 
/* 418 */       return appendUniGlyphs(text, from, to, glyphs);
/*     */     } 
/* 420 */     if (this.cidFontType == 2) {
/* 421 */       if (this.fontProgram.isFontSpecific()) {
/* 422 */         int processed = 0;
/* 423 */         for (int k = from; k <= to; ) {
/* 424 */           Glyph glyph = this.fontProgram.getGlyph(text.charAt(k) & 0xFF);
/* 425 */           if (glyph != null && isAppendableGlyph(glyph)) {
/* 426 */             glyphs.add(glyph);
/* 427 */             processed++;
/*     */             
/*     */             k++;
/*     */           } 
/*     */         } 
/* 432 */         return processed;
/*     */       } 
/* 434 */       return appendUniGlyphs(text, from, to, glyphs);
/*     */     } 
/*     */     
/* 437 */     throw new PdfException("Font has no suitable cmap.");
/*     */   }
/*     */ 
/*     */   
/*     */   private int appendUniGlyphs(String text, int from, int to, List<Glyph> glyphs) {
/* 442 */     int processed = 0;
/* 443 */     for (int k = from; k <= to; k++) {
/*     */       
/* 445 */       int val, currentlyProcessed = processed;
/* 446 */       if (TextUtil.isSurrogatePair(text, k)) {
/* 447 */         val = TextUtil.convertToUtf32(text, k);
/* 448 */         processed += 2;
/*     */       } else {
/* 450 */         val = text.charAt(k);
/* 451 */         processed++;
/*     */       } 
/* 453 */       Glyph glyph = getGlyph(val);
/* 454 */       if (isAppendableGlyph(glyph)) {
/* 455 */         glyphs.add(glyph);
/*     */       } else {
/* 457 */         processed = currentlyProcessed;
/*     */         break;
/*     */       } 
/*     */     } 
/* 461 */     return processed;
/*     */   }
/*     */ 
/*     */   
/*     */   public int appendAnyGlyph(String text, int from, List<Glyph> glyphs) {
/* 466 */     int process = 1;
/*     */     
/* 468 */     if (this.cidFontType == 0) {
/* 469 */       if (this.cmapEncoding.isDirect()) {
/* 470 */         Glyph glyph = this.fontProgram.getGlyphByCode(text.charAt(from));
/* 471 */         if (glyph != null) {
/* 472 */           glyphs.add(glyph);
/*     */         }
/*     */       } else {
/*     */         int ch;
/* 476 */         if (TextUtil.isSurrogatePair(text, from)) {
/* 477 */           ch = TextUtil.convertToUtf32(text, from);
/* 478 */           process = 2;
/*     */         } else {
/* 480 */           ch = text.charAt(from);
/*     */         } 
/* 482 */         glyphs.add(getGlyph(ch));
/*     */       } 
/* 484 */     } else if (this.cidFontType == 2) {
/* 485 */       TrueTypeFont ttf = (TrueTypeFont)this.fontProgram;
/* 486 */       if (ttf.isFontSpecific()) {
/* 487 */         byte[] b = PdfEncodings.convertToBytes(text, "symboltt");
/* 488 */         if (b.length > 0) {
/* 489 */           Glyph glyph = this.fontProgram.getGlyph(b[0] & 0xFF);
/* 490 */           if (glyph != null) {
/* 491 */             glyphs.add(glyph);
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         int ch;
/* 496 */         if (TextUtil.isSurrogatePair(text, from)) {
/* 497 */           ch = TextUtil.convertToUtf32(text, from);
/* 498 */           process = 2;
/*     */         } else {
/* 500 */           ch = text.charAt(from);
/*     */         } 
/* 502 */         glyphs.add(getGlyph(ch));
/*     */       } 
/*     */     } else {
/* 505 */       throw new PdfException("Font has no suitable cmap.");
/*     */     } 
/* 507 */     return process;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAppendableGlyph(Glyph glyph) {
/* 514 */     return (glyph.getCode() > 0 || TextUtil.isWhitespaceOrNonPrintable(glyph.getUnicode()));
/*     */   }
/*     */ 
/*     */   
/*     */   public String decode(PdfString content) {
/* 519 */     return decodeIntoGlyphLine(content).toString();
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
/*     */   public GlyphLine decodeIntoGlyphLine(PdfString content) {
/* 532 */     String cids = content.getValue();
/* 533 */     List<Glyph> glyphs = new ArrayList<>();
/* 534 */     for (int i = 0; i < cids.length(); i++) {
/*     */       
/* 536 */       int code = 0;
/* 537 */       Glyph glyph = null;
/* 538 */       int codeSpaceMatchedLength = 1;
/* 539 */       for (int codeLength = 1; codeLength <= 4 && i + codeLength <= cids.length(); codeLength++) {
/* 540 */         code = (code << 8) + cids.charAt(i + codeLength - 1);
/* 541 */         if (this.cmapEncoding.containsCodeInCodeSpaceRange(code, codeLength)) {
/*     */ 
/*     */           
/* 544 */           codeSpaceMatchedLength = codeLength;
/*     */           
/* 546 */           int glyphCode = this.cmapEncoding.getCidCode(code);
/* 547 */           glyph = this.fontProgram.getGlyphByCode(glyphCode);
/* 548 */           if (glyph != null) {
/* 549 */             i += codeLength - 1; break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 553 */       if (glyph == null) {
/* 554 */         StringBuilder failedCodes = new StringBuilder();
/* 555 */         for (int j = 1; j <= 4 && i + j <= cids.length(); j++) {
/* 556 */           failedCodes.append(cids.charAt(i + j - 1)).append(" ");
/*     */         }
/* 558 */         Logger logger = LoggerFactory.getLogger(PdfType0Font.class);
/* 559 */         logger.warn(MessageFormatUtil.format("Could not find glyph with the following code: {0}", new Object[] { failedCodes.toString() }));
/* 560 */         i += codeSpaceMatchedLength - 1;
/*     */       } 
/* 562 */       if (glyph != null && glyph.getChars() != null) {
/* 563 */         glyphs.add(glyph);
/*     */       } else {
/* 565 */         glyphs.add(new Glyph(0, this.fontProgram.getGlyphByCode(0).getWidth(), -1));
/*     */       } 
/*     */     } 
/* 568 */     return new GlyphLine(glyphs);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getContentWidth(PdfString content) {
/* 573 */     float width = 0.0F;
/* 574 */     GlyphLine glyphLine = decodeIntoGlyphLine(content);
/* 575 */     for (int i = glyphLine.start; i < glyphLine.end; i++) {
/* 576 */       width += glyphLine.get(i).getWidth();
/*     */     }
/* 578 */     return width;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltWith(String fontProgram, String encoding) {
/* 583 */     return (getFontProgram().isBuiltWith(fontProgram) && this.cmapEncoding
/* 584 */       .isBuiltWith(encoding));
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/* 589 */     if (isFlushed())
/* 590 */       return;  ensureUnderlyingObjectHasIndirectReference();
/* 591 */     if (this.newFont) {
/* 592 */       flushFontData();
/*     */     }
/* 594 */     super.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CMapEncoding getCmap() {
/* 604 */     return this.cmapEncoding;
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
/*     */   public PdfStream getToUnicode(Object[] metrics) {
/* 617 */     return getToUnicode();
/*     */   }
/*     */ 
/*     */   
/*     */   protected PdfDictionary getFontDescriptor(String fontName) {
/* 622 */     PdfDictionary fontDescriptor = new PdfDictionary();
/* 623 */     makeObjectIndirect((PdfObject)fontDescriptor);
/* 624 */     fontDescriptor.put(PdfName.Type, (PdfObject)PdfName.FontDescriptor);
/* 625 */     fontDescriptor.put(PdfName.FontName, (PdfObject)new PdfName(fontName));
/* 626 */     fontDescriptor.put(PdfName.FontBBox, (PdfObject)new PdfArray(getFontProgram().getFontMetrics().getBbox()));
/* 627 */     fontDescriptor.put(PdfName.Ascent, (PdfObject)new PdfNumber(getFontProgram().getFontMetrics().getTypoAscender()));
/* 628 */     fontDescriptor.put(PdfName.Descent, (PdfObject)new PdfNumber(getFontProgram().getFontMetrics().getTypoDescender()));
/* 629 */     fontDescriptor.put(PdfName.CapHeight, (PdfObject)new PdfNumber(getFontProgram().getFontMetrics().getCapHeight()));
/* 630 */     fontDescriptor.put(PdfName.ItalicAngle, (PdfObject)new PdfNumber(getFontProgram().getFontMetrics().getItalicAngle()));
/* 631 */     fontDescriptor.put(PdfName.StemV, (PdfObject)new PdfNumber(getFontProgram().getFontMetrics().getStemV()));
/* 632 */     fontDescriptor.put(PdfName.Flags, (PdfObject)new PdfNumber(getFontProgram().getPdfFontFlags()));
/* 633 */     if (this.fontProgram.getFontIdentification().getPanose() != null) {
/* 634 */       PdfDictionary styleDictionary = new PdfDictionary();
/* 635 */       styleDictionary.put(PdfName.Panose, (PdfObject)(new PdfString(this.fontProgram.getFontIdentification().getPanose())).setHexWriting(true));
/* 636 */       fontDescriptor.put(PdfName.Style, (PdfObject)styleDictionary);
/*     */     } 
/* 638 */     return fontDescriptor;
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
/*     */   @Deprecated
/*     */   protected PdfDictionary getCidFontType2(TrueTypeFont ttf, PdfDictionary fontDescriptor, String fontName, int[][] metrics) {
/* 653 */     return getCidFont(fontDescriptor, fontName, (ttf != null && !ttf.isCff()));
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
/*     */   @Deprecated
/*     */   protected void addRangeUni(TrueTypeFont ttf, Map<Integer, int[]> longTag, boolean includeMetrics) {
/* 669 */     addRangeUni(ttf, longTag.keySet());
/*     */   }
/*     */   
/*     */   private void convertToBytes(Glyph glyph, ByteBuffer result) {
/* 673 */     int code = glyph.getCode();
/* 674 */     this.longTag.add(Integer.valueOf(code));
/* 675 */     this.cmapEncoding.fillCmapBytes(code, result);
/*     */   }
/*     */   
/*     */   private static String getOrdering(PdfDictionary cidFont) {
/* 679 */     PdfDictionary cidinfo = cidFont.getAsDictionary(PdfName.CIDSystemInfo);
/* 680 */     if (cidinfo == null)
/* 681 */       return null; 
/* 682 */     return cidinfo.containsKey(PdfName.Ordering) ? cidinfo.get(PdfName.Ordering).toString() : null;
/*     */   }
/*     */   
/*     */   private void flushFontData() {
/* 686 */     if (this.cidFontType == 0) {
/* 687 */       ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.Font);
/* 688 */       ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.Type0);
/* 689 */       String name = this.fontProgram.getFontNames().getFontName();
/* 690 */       String style = this.fontProgram.getFontNames().getStyle();
/* 691 */       if (style.length() > 0) {
/* 692 */         name = name + "-" + style;
/*     */       }
/* 694 */       ((PdfDictionary)getPdfObject()).put(PdfName.BaseFont, (PdfObject)new PdfName(MessageFormatUtil.format("{0}-{1}", new Object[] { name, this.cmapEncoding.getCmapName() })));
/* 695 */       ((PdfDictionary)getPdfObject()).put(PdfName.Encoding, (PdfObject)new PdfName(this.cmapEncoding.getCmapName()));
/* 696 */       PdfDictionary fontDescriptor = getFontDescriptor(name);
/* 697 */       PdfDictionary cidFont = getCidFont(fontDescriptor, this.fontProgram.getFontNames().getFontName(), false);
/* 698 */       ((PdfDictionary)getPdfObject()).put(PdfName.DescendantFonts, (PdfObject)new PdfArray((PdfObject)cidFont));
/*     */       
/* 700 */       fontDescriptor.flush();
/* 701 */       cidFont.flush();
/* 702 */     } else if (this.cidFontType == 2) {
/* 703 */       PdfStream fontStream; TrueTypeFont ttf = (TrueTypeFont)getFontProgram();
/* 704 */       String fontName = updateSubsetPrefix(ttf.getFontNames().getFontName(), this.subset, this.embedded);
/* 705 */       PdfDictionary fontDescriptor = getFontDescriptor(fontName);
/*     */ 
/*     */       
/* 708 */       ttf.updateUsedGlyphs((SortedSet)this.longTag, this.subset, this.subsetRanges);
/* 709 */       if (ttf.isCff()) {
/*     */         byte[] cffBytes;
/* 711 */         if (this.subset) {
/* 712 */           cffBytes = (new CFFFontSubset(ttf.getFontStreamBytes(), this.longTag)).Process();
/*     */         } else {
/* 714 */           cffBytes = ttf.getFontStreamBytes();
/*     */         } 
/* 716 */         fontStream = getPdfFontStream(cffBytes, new int[] { cffBytes.length });
/* 717 */         fontStream.put(PdfName.Subtype, (PdfObject)new PdfName("CIDFontType0C"));
/*     */         
/* 719 */         ((PdfDictionary)getPdfObject()).put(PdfName.BaseFont, (PdfObject)new PdfName(
/* 720 */               MessageFormatUtil.format("{0}-{1}", new Object[] { fontName, this.cmapEncoding.getCmapName() })));
/* 721 */         fontDescriptor.put(PdfName.FontFile3, (PdfObject)fontStream);
/*     */       } else {
/* 723 */         byte[] ttfBytes = null;
/*     */         
/* 725 */         if (this.subset || ttf.getDirectoryOffset() > 0) {
/*     */           try {
/* 727 */             ttfBytes = ttf.getSubset(this.longTag, this.subset);
/* 728 */           } catch (IOException e) {
/* 729 */             Logger logger = LoggerFactory.getLogger(PdfType0Font.class);
/* 730 */             logger.warn("Font subset issue. Full font will be embedded.");
/* 731 */             ttfBytes = null;
/*     */           } 
/*     */         }
/* 734 */         if (ttfBytes == null) {
/* 735 */           ttfBytes = ttf.getFontStreamBytes();
/*     */         }
/* 737 */         fontStream = getPdfFontStream(ttfBytes, new int[] { ttfBytes.length });
/* 738 */         ((PdfDictionary)getPdfObject()).put(PdfName.BaseFont, (PdfObject)new PdfName(fontName));
/* 739 */         fontDescriptor.put(PdfName.FontFile2, (PdfObject)fontStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 744 */       int numOfGlyphs = ttf.getFontMetrics().getNumberOfGlyphs();
/* 745 */       byte[] cidSetBytes = new byte[ttf.getFontMetrics().getNumberOfGlyphs() / 8 + 1]; int i;
/* 746 */       for (i = 0; i < numOfGlyphs / 8; i++) {
/* 747 */         cidSetBytes[i] = (byte)(cidSetBytes[i] | 0xFF);
/*     */       }
/* 749 */       for (i = 0; i < numOfGlyphs % 8; i++) {
/* 750 */         cidSetBytes[cidSetBytes.length - 1] = (byte)(cidSetBytes[cidSetBytes.length - 1] | rotbits[i]);
/*     */       }
/* 752 */       fontDescriptor.put(PdfName.CIDSet, (PdfObject)new PdfStream(cidSetBytes));
/* 753 */       PdfDictionary cidFont = getCidFont(fontDescriptor, fontName, !ttf.isCff());
/*     */       
/* 755 */       ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.Font);
/* 756 */       ((PdfDictionary)getPdfObject()).put(PdfName.Subtype, (PdfObject)PdfName.Type0);
/* 757 */       ((PdfDictionary)getPdfObject()).put(PdfName.Encoding, (PdfObject)new PdfName(this.cmapEncoding.getCmapName()));
/* 758 */       ((PdfDictionary)getPdfObject()).put(PdfName.DescendantFonts, (PdfObject)new PdfArray((PdfObject)cidFont));
/*     */       
/* 760 */       PdfStream toUnicode = getToUnicode();
/* 761 */       if (toUnicode != null) {
/* 762 */         ((PdfDictionary)getPdfObject()).put(PdfName.ToUnicode, (PdfObject)toUnicode);
/* 763 */         if (toUnicode.getIndirectReference() != null) {
/* 764 */           toUnicode.flush();
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 770 */       if (((PdfDictionary)getPdfObject()).getIndirectReference().getDocument().getPdfVersion().compareTo(PdfVersion.PDF_2_0) >= 0)
/*     */       {
/* 772 */         fontDescriptor.remove(PdfName.CIDSet);
/*     */       }
/* 774 */       fontDescriptor.flush();
/* 775 */       cidFont.flush();
/* 776 */       fontStream.flush();
/*     */     } else {
/* 778 */       throw new IllegalStateException("Unsupported CID Font");
/*     */     } 
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
/*     */   @Deprecated
/*     */   protected PdfDictionary getCidFontType2(TrueTypeFont ttf, PdfDictionary fontDescriptor, String fontName, int[] glyphIds) {
/* 795 */     return getCidFont(fontDescriptor, fontName, (ttf != null && !ttf.isCff()));
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
/*     */   protected PdfDictionary getCidFont(PdfDictionary fontDescriptor, String fontName, boolean isType2) {
/* 808 */     PdfDictionary cidFont = new PdfDictionary();
/* 809 */     markObjectAsIndirect((PdfObject)cidFont);
/* 810 */     cidFont.put(PdfName.Type, (PdfObject)PdfName.Font);
/*     */     
/* 812 */     cidFont.put(PdfName.FontDescriptor, (PdfObject)fontDescriptor);
/* 813 */     if (isType2) {
/* 814 */       cidFont.put(PdfName.Subtype, (PdfObject)PdfName.CIDFontType2);
/* 815 */       cidFont.put(PdfName.CIDToGIDMap, (PdfObject)PdfName.Identity);
/*     */     } else {
/* 817 */       cidFont.put(PdfName.Subtype, (PdfObject)PdfName.CIDFontType0);
/*     */     } 
/* 819 */     cidFont.put(PdfName.BaseFont, (PdfObject)new PdfName(fontName));
/* 820 */     PdfDictionary cidInfo = new PdfDictionary();
/* 821 */     cidInfo.put(PdfName.Registry, (PdfObject)new PdfString(this.cmapEncoding.getRegistry()));
/* 822 */     cidInfo.put(PdfName.Ordering, (PdfObject)new PdfString(this.cmapEncoding.getOrdering()));
/* 823 */     cidInfo.put(PdfName.Supplement, (PdfObject)new PdfNumber(this.cmapEncoding.getSupplement()));
/* 824 */     cidFont.put(PdfName.CIDSystemInfo, (PdfObject)cidInfo);
/* 825 */     if (!this.vertical) {
/* 826 */       cidFont.put(PdfName.DW, (PdfObject)new PdfNumber(1000));
/* 827 */       PdfObject widthsArray = generateWidthsArray();
/* 828 */       if (widthsArray != null) {
/* 829 */         cidFont.put(PdfName.W, widthsArray);
/*     */       }
/*     */     } else {
/*     */       
/* 833 */       Logger logger = LoggerFactory.getLogger(PdfType0Font.class);
/* 834 */       logger.warn("Vertical writing has not been implemented yet.");
/*     */     } 
/* 836 */     return cidFont;
/*     */   }
/*     */   
/*     */   private PdfObject generateWidthsArray() {
/* 840 */     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/* 841 */     OutputStream<ByteArrayOutputStream> stream = new OutputStream((OutputStream)bytes);
/* 842 */     stream.writeByte(91);
/* 843 */     int lastNumber = -10;
/* 844 */     boolean firstTime = true;
/* 845 */     for (Iterator<Integer> iterator = this.longTag.iterator(); iterator.hasNext(); ) { int code = ((Integer)iterator.next()).intValue();
/* 846 */       Glyph glyph = this.fontProgram.getGlyphByCode(code);
/* 847 */       if (glyph.getWidth() == 1000) {
/*     */         continue;
/*     */       }
/* 850 */       if (glyph.getCode() == lastNumber + 1) {
/* 851 */         stream.writeByte(32);
/*     */       } else {
/* 853 */         if (!firstTime) {
/* 854 */           stream.writeByte(93);
/*     */         }
/* 856 */         firstTime = false;
/* 857 */         stream.writeInteger(glyph.getCode());
/* 858 */         stream.writeByte(91);
/*     */       } 
/* 860 */       stream.writeInteger(glyph.getWidth());
/* 861 */       lastNumber = glyph.getCode(); }
/*     */     
/* 863 */     if (stream.getCurrentPos() > 1L) {
/* 864 */       stream.writeString("]]");
/* 865 */       return (PdfObject)new PdfLiteral(bytes.toByteArray());
/*     */     } 
/* 867 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfStream getToUnicode() {
/* 876 */     OutputStream<ByteArrayOutputStream> stream = new OutputStream((OutputStream)new ByteArrayOutputStream());
/* 877 */     stream.writeString("/CIDInit /ProcSet findresource begin\n12 dict begin\nbegincmap\n/CIDSystemInfo\n<< /Registry (Adobe)\n/Ordering (UCS)\n/Supplement 0\n>> def\n/CMapName /Adobe-Identity-UCS def\n/CMapType 2 def\n1 begincodespacerange\n<0000><FFFF>\nendcodespacerange\n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 892 */     ArrayList<Glyph> glyphGroup = new ArrayList<>(100);
/*     */     
/* 894 */     int bfranges = 0;
/* 895 */     for (Integer glyphId : this.longTag) {
/* 896 */       Glyph glyph = this.fontProgram.getGlyphByCode(glyphId.intValue());
/* 897 */       if (glyph.getChars() != null) {
/* 898 */         glyphGroup.add(glyph);
/* 899 */         if (glyphGroup.size() == 100) {
/* 900 */           bfranges += writeBfrange(stream, glyphGroup);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 905 */     bfranges += writeBfrange(stream, glyphGroup);
/*     */     
/* 907 */     if (bfranges == 0) {
/* 908 */       return null;
/*     */     }
/* 910 */     stream.writeString("endcmap\nCMapName currentdict /CMap defineresource pop\nend end\n");
/*     */ 
/*     */     
/* 913 */     return new PdfStream(((ByteArrayOutputStream)stream.getOutputStream()).toByteArray());
/*     */   }
/*     */   
/*     */   private int writeBfrange(OutputStream<ByteArrayOutputStream> stream, List<Glyph> range) {
/* 917 */     if (range.isEmpty()) return 0; 
/* 918 */     stream.writeInteger(range.size());
/* 919 */     stream.writeString(" beginbfrange\n");
/* 920 */     for (Glyph glyph : range) {
/* 921 */       String fromTo = CMapContentParser.toHex(glyph.getCode());
/* 922 */       stream.writeString(fromTo);
/* 923 */       stream.writeString(fromTo);
/* 924 */       stream.writeByte(60);
/* 925 */       for (char ch : glyph.getChars()) {
/* 926 */         stream.writeString(toHex4(ch));
/*     */       }
/* 928 */       stream.writeByte(62);
/* 929 */       stream.writeByte(10);
/*     */     } 
/* 931 */     stream.writeString("endbfrange\n");
/* 932 */     range.clear();
/* 933 */     return 1;
/*     */   }
/*     */   
/*     */   private static String toHex4(char ch) {
/* 937 */     String s = "0000" + Integer.toHexString(ch);
/* 938 */     return s.substring(s.length() - 4);
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
/*     */   @Deprecated
/*     */   protected void addRangeUni(TrueTypeFont ttf, Set<Integer> longTag) {
/* 952 */     ttf.updateUsedGlyphs((SortedSet)longTag, this.subset, this.subsetRanges);
/*     */   }
/*     */   
/*     */   private String getCompatibleUniMap(String registry) {
/* 956 */     String uniMap = "";
/* 957 */     for (String name : CidFontProperties.getRegistryNames().get(registry + "_Uni")) {
/* 958 */       uniMap = name;
/* 959 */       if (name.endsWith("V") && this.vertical)
/*     */         break; 
/* 961 */       if (!name.endsWith("V") && !this.vertical) {
/*     */         break;
/*     */       }
/*     */     } 
/* 965 */     return uniMap;
/*     */   }
/*     */   
/*     */   private static CMapEncoding createCMap(PdfObject cmap, String uniMap) {
/* 969 */     if (cmap.isStream()) {
/* 970 */       PdfStream cmapStream = (PdfStream)cmap;
/* 971 */       byte[] cmapBytes = cmapStream.getBytes();
/* 972 */       return new CMapEncoding(cmapStream.getAsName(PdfName.CMapName).getValue(), cmapBytes);
/*     */     } 
/* 974 */     String cmapName = ((PdfName)cmap).getValue();
/* 975 */     if ("Identity-H".equals(cmapName) || "Identity-V".equals(cmapName)) {
/* 976 */       return new CMapEncoding(cmapName);
/*     */     }
/* 978 */     return new CMapEncoding(cmapName, uniMap);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/PdfType0Font.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */