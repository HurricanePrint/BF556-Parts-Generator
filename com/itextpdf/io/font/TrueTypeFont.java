/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.constants.TrueTypeCodePages;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.font.otf.GlyphPositioningTableReader;
/*     */ import com.itextpdf.io.font.otf.GlyphSubstitutionTableReader;
/*     */ import com.itextpdf.io.font.otf.OpenTypeGdefTableReader;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
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
/*     */ public class TrueTypeFont
/*     */   extends FontProgram
/*     */ {
/*     */   private static final long serialVersionUID = -2232044646577669268L;
/*     */   private OpenTypeParser fontParser;
/*     */   protected int[][] bBoxes;
/*     */   protected boolean isVertical;
/*     */   private GlyphSubstitutionTableReader gsubTable;
/*     */   private GlyphPositioningTableReader gposTable;
/*     */   private OpenTypeGdefTableReader gdefTable;
/*  88 */   protected IntHashtable kerning = new IntHashtable();
/*     */   
/*     */   private byte[] fontStreamBytes;
/*     */   
/*     */   private TrueTypeFont(OpenTypeParser fontParser) throws IOException {
/*  93 */     this.fontParser = fontParser;
/*  94 */     this.fontParser.loadTables(true);
/*  95 */     initializeFontProperties();
/*     */   }
/*     */   
/*     */   protected TrueTypeFont() {
/*  99 */     this.fontNames = new FontNames();
/*     */   }
/*     */   
/*     */   public TrueTypeFont(String path) throws IOException {
/* 103 */     this(new OpenTypeParser(path));
/*     */   }
/*     */   
/*     */   public TrueTypeFont(byte[] ttf) throws IOException {
/* 107 */     this(new OpenTypeParser(ttf));
/*     */   }
/*     */   
/*     */   TrueTypeFont(String ttcPath, int ttcIndex) throws IOException {
/* 111 */     this(new OpenTypeParser(ttcPath, ttcIndex));
/*     */   }
/*     */   
/*     */   TrueTypeFont(byte[] ttc, int ttcIndex) throws IOException {
/* 115 */     this(new OpenTypeParser(ttc, ttcIndex));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasKernPairs() {
/* 120 */     return (this.kerning.size() > 0);
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
/*     */   public int getKerning(Glyph first, Glyph second) {
/* 132 */     if (first == null || second == null) {
/* 133 */       return 0;
/*     */     }
/* 135 */     return this.kerning.get((first.getCode() << 16) + second.getCode());
/*     */   }
/*     */   
/*     */   public boolean isCff() {
/* 139 */     return this.fontParser.isCff();
/*     */   }
/*     */   
/*     */   public Map<Integer, int[]> getActiveCmap() {
/* 143 */     OpenTypeParser.CmapTable cmaps = this.fontParser.getCmapTable();
/* 144 */     if (cmaps.cmapExt != null)
/* 145 */       return cmaps.cmapExt; 
/* 146 */     if (!cmaps.fontSpecific && cmaps.cmap31 != null)
/* 147 */       return cmaps.cmap31; 
/* 148 */     if (cmaps.fontSpecific && cmaps.cmap10 != null)
/* 149 */       return cmaps.cmap10; 
/* 150 */     if (cmaps.cmap31 != null) {
/* 151 */       return cmaps.cmap31;
/*     */     }
/* 153 */     return cmaps.cmap10;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getFontStreamBytes() {
/* 158 */     if (this.fontStreamBytes != null)
/* 159 */       return this.fontStreamBytes; 
/*     */     try {
/* 161 */       if (this.fontParser.isCff()) {
/* 162 */         this.fontStreamBytes = this.fontParser.readCffFont();
/*     */       } else {
/* 164 */         this.fontStreamBytes = this.fontParser.getFullFont();
/*     */       } 
/* 166 */     } catch (IOException e) {
/* 167 */       this.fontStreamBytes = null;
/* 168 */       throw new IOException("I/O exception.", e);
/*     */     } 
/* 170 */     return this.fontStreamBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPdfFontFlags() {
/* 175 */     int flags = 0;
/* 176 */     if (this.fontMetrics.isFixedPitch()) {
/* 177 */       flags |= 0x1;
/*     */     }
/* 179 */     flags |= isFontSpecific() ? 4 : 32;
/* 180 */     if (this.fontNames.isItalic()) {
/* 181 */       flags |= 0x40;
/*     */     }
/* 183 */     if (this.fontNames.isBold() || this.fontNames.getFontWeight() > 500) {
/* 184 */       flags |= 0x40000;
/*     */     }
/* 186 */     return flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDirectoryOffset() {
/* 196 */     return this.fontParser.directoryOffset;
/*     */   }
/*     */   
/*     */   public GlyphSubstitutionTableReader getGsubTable() {
/* 200 */     return this.gsubTable;
/*     */   }
/*     */   
/*     */   public GlyphPositioningTableReader getGposTable() {
/* 204 */     return this.gposTable;
/*     */   }
/*     */   
/*     */   public OpenTypeGdefTableReader getGdefTable() {
/* 208 */     return this.gdefTable;
/*     */   }
/*     */   
/*     */   public byte[] getSubset(Set<Integer> glyphs, boolean subset) {
/*     */     try {
/* 213 */       return this.fontParser.getSubset(glyphs, subset);
/* 214 */     } catch (IOException e) {
/* 215 */       throw new IOException("I/O exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readGdefTable() throws IOException {
/* 220 */     int[] gdef = this.fontParser.tables.get("GDEF");
/* 221 */     if (gdef != null) {
/* 222 */       this.gdefTable = new OpenTypeGdefTableReader(this.fontParser.raf, gdef[0]);
/*     */     } else {
/* 224 */       this.gdefTable = new OpenTypeGdefTableReader(this.fontParser.raf, 0);
/*     */     } 
/* 226 */     this.gdefTable.readTable();
/*     */   }
/*     */   
/*     */   protected void readGsubTable() throws IOException {
/* 230 */     int[] gsub = this.fontParser.tables.get("GSUB");
/* 231 */     if (gsub != null) {
/* 232 */       this.gsubTable = new GlyphSubstitutionTableReader(this.fontParser.raf, gsub[0], this.gdefTable, this.codeToGlyph, this.fontMetrics.getUnitsPerEm());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void readGposTable() throws IOException {
/* 237 */     int[] gpos = this.fontParser.tables.get("GPOS");
/* 238 */     if (gpos != null) {
/* 239 */       this.gposTable = new GlyphPositioningTableReader(this.fontParser.raf, gpos[0], this.gdefTable, this.codeToGlyph, this.fontMetrics.getUnitsPerEm());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeFontProperties() throws IOException {
/* 245 */     OpenTypeParser.HeaderTable head = this.fontParser.getHeadTable();
/* 246 */     OpenTypeParser.HorizontalHeader hhea = this.fontParser.getHheaTable();
/* 247 */     OpenTypeParser.WindowsMetrics os_2 = this.fontParser.getOs_2Table();
/* 248 */     OpenTypeParser.PostTable post = this.fontParser.getPostTable();
/* 249 */     this.isFontSpecific = (this.fontParser.getCmapTable()).fontSpecific;
/* 250 */     this.kerning = this.fontParser.readKerning(head.unitsPerEm);
/* 251 */     this.bBoxes = this.fontParser.readBbox(head.unitsPerEm);
/*     */ 
/*     */     
/* 254 */     this.fontNames = this.fontParser.getFontNames();
/*     */ 
/*     */     
/* 257 */     this.fontMetrics.setUnitsPerEm(head.unitsPerEm);
/* 258 */     this.fontMetrics.updateBbox(head.xMin, head.yMin, head.xMax, head.yMax);
/* 259 */     this.fontMetrics.setNumberOfGlyphs(this.fontParser.readNumGlyphs());
/* 260 */     this.fontMetrics.setGlyphWidths(this.fontParser.getGlyphWidthsByIndex());
/* 261 */     this.fontMetrics.setTypoAscender(os_2.sTypoAscender);
/* 262 */     this.fontMetrics.setTypoDescender(os_2.sTypoDescender);
/* 263 */     this.fontMetrics.setCapHeight(os_2.sCapHeight);
/* 264 */     this.fontMetrics.setXHeight(os_2.sxHeight);
/* 265 */     this.fontMetrics.setItalicAngle(post.italicAngle);
/* 266 */     this.fontMetrics.setAscender(hhea.Ascender);
/* 267 */     this.fontMetrics.setDescender(hhea.Descender);
/* 268 */     this.fontMetrics.setLineGap(hhea.LineGap);
/* 269 */     this.fontMetrics.setWinAscender(os_2.usWinAscent);
/* 270 */     this.fontMetrics.setWinDescender(os_2.usWinDescent);
/* 271 */     this.fontMetrics.setAdvanceWidthMax(hhea.advanceWidthMax);
/* 272 */     this.fontMetrics.setUnderlinePosition((post.underlinePosition - post.underlineThickness) / 2);
/* 273 */     this.fontMetrics.setUnderlineThickness(post.underlineThickness);
/* 274 */     this.fontMetrics.setStrikeoutPosition(os_2.yStrikeoutPosition);
/* 275 */     this.fontMetrics.setStrikeoutSize(os_2.yStrikeoutSize);
/* 276 */     this.fontMetrics.setSubscriptOffset(-os_2.ySubscriptYOffset);
/* 277 */     this.fontMetrics.setSubscriptSize(os_2.ySubscriptYSize);
/* 278 */     this.fontMetrics.setSuperscriptOffset(os_2.ySuperscriptYOffset);
/* 279 */     this.fontMetrics.setSuperscriptSize(os_2.ySuperscriptYSize);
/* 280 */     this.fontMetrics.setIsFixedPitch(post.isFixedPitch);
/*     */ 
/*     */     
/* 283 */     String[][] ttfVersion = this.fontNames.getNames(5);
/* 284 */     if (ttfVersion != null) {
/* 285 */       this.fontIdentification.setTtfVersion(ttfVersion[0][3]);
/*     */     }
/* 287 */     String[][] ttfUniqueId = this.fontNames.getNames(3);
/* 288 */     if (ttfUniqueId != null) {
/* 289 */       this.fontIdentification.setTtfVersion(ttfUniqueId[0][3]);
/*     */     }
/*     */     
/* 292 */     byte[] pdfPanose = new byte[12];
/* 293 */     pdfPanose[1] = (byte)os_2.sFamilyClass;
/* 294 */     pdfPanose[0] = (byte)(os_2.sFamilyClass >> 8);
/* 295 */     System.arraycopy(os_2.panose, 0, pdfPanose, 2, 10);
/* 296 */     this.fontIdentification.setPanose(pdfPanose);
/*     */     
/* 298 */     Map<Integer, int[]> cmap = getActiveCmap();
/* 299 */     int[] glyphWidths = this.fontParser.getGlyphWidthsByIndex();
/* 300 */     int numOfGlyphs = this.fontMetrics.getNumberOfGlyphs();
/* 301 */     this.unicodeToGlyph = new LinkedHashMap<>(cmap.size());
/* 302 */     this.codeToGlyph = new LinkedHashMap<>(numOfGlyphs);
/* 303 */     this.avgWidth = 0;
/* 304 */     for (Iterator<Integer> iterator = cmap.keySet().iterator(); iterator.hasNext(); ) { int charCode = ((Integer)iterator.next()).intValue();
/* 305 */       int i = ((int[])cmap.get(Integer.valueOf(charCode)))[0];
/* 306 */       if (i >= numOfGlyphs) {
/* 307 */         Logger LOGGER = LoggerFactory.getLogger(TrueTypeFont.class);
/* 308 */         LOGGER.warn(MessageFormatUtil.format("Font {0} has invalid glyph: {1}", new Object[] { getFontNames().getFontName(), Integer.valueOf(i) }));
/*     */         continue;
/*     */       } 
/* 311 */       Glyph glyph = new Glyph(i, glyphWidths[i], charCode, (this.bBoxes != null) ? this.bBoxes[i] : null);
/* 312 */       this.unicodeToGlyph.put(Integer.valueOf(charCode), glyph);
/*     */ 
/*     */       
/* 315 */       if (!this.codeToGlyph.containsKey(Integer.valueOf(i))) {
/* 316 */         this.codeToGlyph.put(Integer.valueOf(i), glyph);
/*     */       }
/* 318 */       this.avgWidth += glyph.getWidth(); }
/*     */     
/* 320 */     fixSpaceIssue();
/* 321 */     for (int index = 0; index < glyphWidths.length; index++) {
/* 322 */       if (!this.codeToGlyph.containsKey(Integer.valueOf(index))) {
/*     */ 
/*     */         
/* 325 */         Glyph glyph = new Glyph(index, glyphWidths[index], -1);
/* 326 */         this.codeToGlyph.put(Integer.valueOf(index), glyph);
/* 327 */         this.avgWidth += glyph.getWidth();
/*     */       } 
/*     */     } 
/* 330 */     if (this.codeToGlyph.size() != 0) {
/* 331 */       this.avgWidth /= this.codeToGlyph.size();
/*     */     }
/*     */     
/* 334 */     readGdefTable();
/* 335 */     readGsubTable();
/* 336 */     readGposTable();
/*     */     
/* 338 */     this.isVertical = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getCodePagesSupported() {
/* 347 */     long cp = ((this.fontParser.getOs_2Table()).ulCodePageRange2 << 32L) + ((this.fontParser.getOs_2Table()).ulCodePageRange1 & 0xFFFFFFFFL);
/* 348 */     int count = 0;
/* 349 */     long bit = 1L;
/* 350 */     for (int k = 0; k < 64; k++) {
/* 351 */       if ((cp & bit) != 0L && TrueTypeCodePages.get(k) != null)
/* 352 */         count++; 
/* 353 */       bit <<= 1L;
/*     */     } 
/* 355 */     String[] ret = new String[count];
/* 356 */     count = 0;
/* 357 */     bit = 1L;
/* 358 */     for (int i = 0; i < 64; i++) {
/* 359 */       if ((cp & bit) != 0L && TrueTypeCodePages.get(i) != null)
/* 360 */         ret[count++] = TrueTypeCodePages.get(i); 
/* 361 */       bit <<= 1L;
/*     */     } 
/* 363 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltWith(String fontProgram) {
/* 368 */     return Objects.equals(this.fontParser.fileName, fontProgram);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 372 */     if (this.fontParser != null) {
/* 373 */       this.fontParser.close();
/*     */     }
/* 375 */     this.fontParser = null;
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
/*     */   public void updateUsedGlyphs(SortedSet<Integer> usedGlyphs, boolean subset, List<int[]> subsetRanges) {
/*     */     int[] compactRange;
/* 389 */     if (subsetRanges != null) {
/* 390 */       compactRange = toCompactRange(subsetRanges);
/* 391 */     } else if (!subset) {
/* 392 */       compactRange = new int[] { 0, 65535 };
/*     */     } else {
/* 394 */       compactRange = new int[0];
/*     */     } 
/*     */     
/* 397 */     for (int k = 0; k < compactRange.length; k += 2) {
/* 398 */       int from = compactRange[k];
/* 399 */       int to = compactRange[k + 1];
/* 400 */       for (int glyphId = from; glyphId <= to; glyphId++) {
/* 401 */         if (getGlyphByCode(glyphId) != null) {
/* 402 */           usedGlyphs.add(Integer.valueOf(glyphId));
/*     */         }
/*     */       } 
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
/*     */   private static int[] toCompactRange(List<int[]> ranges) {
/* 416 */     List<int[]> simp = (List)new ArrayList<>();
/* 417 */     for (int[] range : ranges) {
/* 418 */       for (int j = 0; j < range.length; j += 2) {
/* 419 */         simp.add(new int[] { Math.max(0, Math.min(range[j], range[j + 1])), Math.min(65535, Math.max(range[j], range[j + 1])) });
/*     */       } 
/*     */     } 
/* 422 */     for (int k1 = 0; k1 < simp.size() - 1; k1++) {
/* 423 */       for (int k2 = k1 + 1; k2 < simp.size(); k2++) {
/* 424 */         int[] r1 = simp.get(k1);
/* 425 */         int[] r2 = simp.get(k2);
/* 426 */         if ((r1[0] >= r2[0] && r1[0] <= r2[1]) || (r1[1] >= r2[0] && r1[0] <= r2[1])) {
/* 427 */           r1[0] = Math.min(r1[0], r2[0]);
/* 428 */           r1[1] = Math.max(r1[1], r2[1]);
/* 429 */           simp.remove(k2);
/* 430 */           k2--;
/*     */         } 
/*     */       } 
/*     */     } 
/* 434 */     int[] s = new int[simp.size() * 2];
/* 435 */     for (int k = 0; k < simp.size(); k++) {
/* 436 */       int[] r = simp.get(k);
/* 437 */       s[k * 2] = r[0];
/* 438 */       s[k * 2 + 1] = r[1];
/*     */     } 
/* 440 */     return s;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/TrueTypeFont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */