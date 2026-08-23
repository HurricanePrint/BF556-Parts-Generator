/*      */ package com.itextpdf.io.font;
/*      */ 
/*      */ import com.itextpdf.io.IOException;
/*      */ import com.itextpdf.io.font.constants.FontStretches;
/*      */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*      */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*      */ import com.itextpdf.io.util.IntHashtable;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class OpenTypeParser
/*      */   implements Serializable, Closeable
/*      */ {
/*      */   private static final long serialVersionUID = 3399061674525229738L;
/*      */   private static final int HEAD_LOCA_FORMAT_OFFSET = 51;
/*      */   protected String fileName;
/*      */   protected RandomAccessFileOrArray raf;
/*      */   
/*      */   static class HeaderTable
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 5849907401352439751L;
/*      */     int flags;
/*      */     int unitsPerEm;
/*      */     short xMin;
/*      */     short yMin;
/*      */     short xMax;
/*      */     short yMax;
/*      */     int macStyle;
/*      */   }
/*      */   
/*      */   static class HorizontalHeader
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -6857266170153679811L;
/*      */     short Ascender;
/*      */     short Descender;
/*      */     short LineGap;
/*      */     int advanceWidthMax;
/*      */     short minLeftSideBearing;
/*      */     short minRightSideBearing;
/*      */     short xMaxExtent;
/*      */     short caretSlopeRise;
/*      */     short caretSlopeRun;
/*      */     int numberOfHMetrics;
/*      */   }
/*      */   
/*      */   static class WindowsMetrics
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -9117114979326346658L;
/*      */     short xAvgCharWidth;
/*      */     int usWeightClass;
/*      */     int usWidthClass;
/*      */     short fsType;
/*      */     short ySubscriptXSize;
/*      */     short ySubscriptYSize;
/*      */     short ySubscriptXOffset;
/*      */     short ySubscriptYOffset;
/*      */     short ySuperscriptXSize;
/*      */     short ySuperscriptYSize;
/*      */     short ySuperscriptXOffset;
/*      */     short ySuperscriptYOffset;
/*      */     short yStrikeoutSize;
/*      */     short yStrikeoutPosition;
/*      */     short sFamilyClass;
/*  118 */     byte[] panose = new byte[10];
/*  119 */     byte[] achVendID = new byte[4];
/*      */ 
/*      */     
/*      */     int fsSelection;
/*      */ 
/*      */     
/*      */     int usFirstCharIndex;
/*      */ 
/*      */     
/*      */     int usLastCharIndex;
/*      */ 
/*      */     
/*      */     short sTypoAscender;
/*      */ 
/*      */     
/*      */     short sTypoDescender;
/*      */     
/*      */     short sTypoLineGap;
/*      */     
/*      */     int usWinAscent;
/*      */     
/*      */     int usWinDescent;
/*      */     
/*      */     int ulCodePageRange1;
/*      */     
/*      */     int ulCodePageRange2;
/*      */     
/*      */     int sxHeight;
/*      */     
/*      */     int sCapHeight;
/*      */   }
/*      */ 
/*      */   
/*      */   static class PostTable
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 5735677308357646890L;
/*      */     
/*      */     float italicAngle;
/*      */     
/*      */     int underlinePosition;
/*      */     
/*      */     int underlineThickness;
/*      */     
/*      */     boolean isFixedPitch;
/*      */   }
/*      */ 
/*      */   
/*      */   static class CmapTable
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 8923883989692194983L;
/*      */     
/*      */     Map<Integer, int[]> cmap10;
/*      */     
/*      */     Map<Integer, int[]> cmap31;
/*      */     
/*      */     Map<Integer, int[]> cmapExt;
/*      */     
/*      */     boolean fontSpecific = false;
/*      */   }
/*      */   
/*  181 */   protected int ttcIndex = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int directoryOffset;
/*      */ 
/*      */   
/*      */   protected String fontName;
/*      */ 
/*      */   
/*      */   protected Map<Integer, List<String[]>> allNameEntries;
/*      */ 
/*      */   
/*      */   protected boolean cff = false;
/*      */ 
/*      */   
/*      */   protected int cffOffset;
/*      */ 
/*      */   
/*      */   protected int cffLength;
/*      */ 
/*      */   
/*      */   private int[] glyphWidthsByIndex;
/*      */ 
/*      */   
/*      */   protected HeaderTable head;
/*      */ 
/*      */   
/*      */   protected HorizontalHeader hhea;
/*      */ 
/*      */   
/*      */   protected WindowsMetrics os_2;
/*      */ 
/*      */   
/*      */   protected PostTable post;
/*      */ 
/*      */   
/*      */   protected CmapTable cmaps;
/*      */ 
/*      */   
/*      */   protected Map<String, int[]> tables;
/*      */ 
/*      */ 
/*      */   
/*      */   public OpenTypeParser(byte[] ttf) throws IOException {
/*  226 */     this.raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(ttf));
/*  227 */     initializeSfntTables();
/*      */   }
/*      */   
/*      */   public OpenTypeParser(byte[] ttc, int ttcIndex) throws IOException {
/*  231 */     this.ttcIndex = ttcIndex;
/*  232 */     this.raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(ttc));
/*  233 */     initializeSfntTables();
/*      */   }
/*      */   
/*      */   public OpenTypeParser(String ttcPath, int ttcIndex) throws IOException {
/*  237 */     this.ttcIndex = ttcIndex;
/*  238 */     this.raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createBestSource(ttcPath));
/*  239 */     initializeSfntTables();
/*      */   }
/*      */   
/*      */   public OpenTypeParser(String name) throws IOException {
/*  243 */     String ttcName = getTTCName(name);
/*  244 */     this.fileName = ttcName;
/*  245 */     if (ttcName.length() < name.length()) {
/*  246 */       this.ttcIndex = Integer.parseInt(name.substring(ttcName.length() + 1));
/*      */     }
/*  248 */     this.raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createBestSource(this.fileName));
/*  249 */     initializeSfntTables();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPsFontName() {
/*  256 */     if (this.fontName == null) {
/*  257 */       List<String[]> names = this.allNameEntries.get(Integer.valueOf(6));
/*  258 */       if (names != null && names.size() > 0) {
/*  259 */         this.fontName = ((String[])names.get(0))[3];
/*      */       } else {
/*  261 */         this.fontName = (new File(this.fileName)).getName().replace(' ', '-');
/*      */       } 
/*      */     } 
/*  264 */     return this.fontName;
/*      */   }
/*      */   
/*      */   public Map<Integer, List<String[]>> getAllNameEntries() {
/*  268 */     return this.allNameEntries;
/*      */   }
/*      */   
/*      */   public PostTable getPostTable() {
/*  272 */     return this.post;
/*      */   }
/*      */   
/*      */   public WindowsMetrics getOs_2Table() {
/*  276 */     return this.os_2;
/*      */   }
/*      */   
/*      */   public HorizontalHeader getHheaTable() {
/*  280 */     return this.hhea;
/*      */   }
/*      */   
/*      */   public HeaderTable getHeadTable() {
/*  284 */     return this.head;
/*      */   }
/*      */   
/*      */   public CmapTable getCmapTable() {
/*  288 */     return this.cmaps;
/*      */   }
/*      */   
/*      */   public int[] getGlyphWidthsByIndex() {
/*  292 */     return this.glyphWidthsByIndex;
/*      */   }
/*      */   
/*      */   public FontNames getFontNames() {
/*  296 */     FontNames fontNames = new FontNames();
/*  297 */     fontNames.setAllNames(getAllNameEntries());
/*  298 */     fontNames.setFontName(getPsFontName());
/*  299 */     fontNames.setFullName(fontNames.getNames(4));
/*  300 */     String[][] otfFamilyName = fontNames.getNames(16);
/*  301 */     if (otfFamilyName != null) {
/*  302 */       fontNames.setFamilyName(otfFamilyName);
/*      */     } else {
/*  304 */       fontNames.setFamilyName(fontNames.getNames(1));
/*      */     } 
/*  306 */     String[][] subfamily = fontNames.getNames(2);
/*  307 */     if (subfamily != null) {
/*  308 */       fontNames.setStyle(subfamily[0][3]);
/*      */     }
/*  310 */     String[][] otfSubFamily = fontNames.getNames(17);
/*  311 */     if (otfFamilyName != null) {
/*  312 */       fontNames.setSubfamily(otfSubFamily);
/*      */     } else {
/*  314 */       fontNames.setSubfamily(subfamily);
/*      */     } 
/*  316 */     String[][] cidName = fontNames.getNames(20);
/*  317 */     if (cidName != null) {
/*  318 */       fontNames.setCidFontName(cidName[0][3]);
/*      */     }
/*  320 */     fontNames.setFontWeight(this.os_2.usWeightClass);
/*  321 */     fontNames.setFontStretch(FontStretches.fromOpenTypeWidthClass(this.os_2.usWidthClass));
/*  322 */     fontNames.setMacStyle(this.head.macStyle);
/*  323 */     fontNames.setAllowEmbedding((this.os_2.fsType != 2));
/*  324 */     return fontNames;
/*      */   }
/*      */   
/*      */   public boolean isCff() {
/*  328 */     return this.cff;
/*      */   }
/*      */   
/*      */   public byte[] getFullFont() throws IOException {
/*  332 */     RandomAccessFileOrArray rf2 = null;
/*      */     try {
/*  334 */       rf2 = this.raf.createView();
/*  335 */       byte[] b = new byte[(int)rf2.length()];
/*  336 */       rf2.readFully(b);
/*  337 */       return b;
/*      */     } finally {
/*      */       try {
/*  340 */         if (rf2 != null) {
/*  341 */           rf2.close();
/*      */         }
/*  343 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] readCffFont() throws IOException {
/*  356 */     if (!isCff()) {
/*  357 */       return null;
/*      */     }
/*  359 */     RandomAccessFileOrArray rf2 = null;
/*      */     try {
/*  361 */       rf2 = this.raf.createView();
/*  362 */       rf2.seek(this.cffOffset);
/*  363 */       byte[] cff = new byte[this.cffLength];
/*  364 */       rf2.readFully(cff);
/*  365 */       return cff;
/*      */     } finally {
/*      */       try {
/*  368 */         if (rf2 != null) {
/*  369 */           rf2.close();
/*      */         }
/*  371 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] getSubset(Set<Integer> glyphs, boolean subset) throws IOException {
/*  378 */     TrueTypeFontSubset sb = new TrueTypeFontSubset(this.fileName, this.raf.createView(), glyphs, this.directoryOffset, subset);
/*  379 */     return sb.process();
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*  384 */     if (this.raf != null) {
/*  385 */       this.raf.close();
/*      */     }
/*  387 */     this.raf = null;
/*      */   }
/*      */   
/*      */   private void initializeSfntTables() throws IOException {
/*  391 */     this.tables = (Map)new LinkedHashMap<>();
/*  392 */     if (this.ttcIndex >= 0) {
/*  393 */       int dirIdx = this.ttcIndex;
/*  394 */       if (dirIdx < 0) {
/*  395 */         if (this.fileName != null) {
/*  396 */           throw (new IOException("The font index for {0} must be positive.")).setMessageParams(new Object[] { this.fileName });
/*      */         }
/*  398 */         throw new IOException("The font index must be positive.");
/*      */       } 
/*      */       
/*  401 */       String mainTag = readStandardString(4);
/*  402 */       if (!mainTag.equals("ttcf")) {
/*  403 */         if (this.fileName != null) {
/*  404 */           throw (new IOException("{0} is not a valid ttc file.")).setMessageParams(new Object[] { this.fileName });
/*      */         }
/*  406 */         throw new IOException("Not a valid ttc file.");
/*      */       } 
/*      */       
/*  409 */       this.raf.skipBytes(4);
/*  410 */       int dirCount = this.raf.readInt();
/*  411 */       if (dirIdx >= dirCount) {
/*  412 */         if (this.fileName != null) {
/*  413 */           throw (new IOException("The font index for {0} must be between 0 and {1}. It is {2}."))
/*  414 */             .setMessageParams(new Object[] { this.fileName, Integer.valueOf(dirCount - 1), Integer.valueOf(dirIdx) });
/*      */         }
/*  416 */         throw (new IOException("The font index must be between 0 and {0}. It is {1}."))
/*  417 */           .setMessageParams(new Object[] { Integer.valueOf(dirCount - 1), Integer.valueOf(dirIdx) });
/*      */       } 
/*      */       
/*  420 */       this.raf.skipBytes(dirIdx * 4);
/*  421 */       this.directoryOffset = this.raf.readInt();
/*      */     } 
/*  423 */     this.raf.seek(this.directoryOffset);
/*  424 */     int ttId = this.raf.readInt();
/*  425 */     if (ttId != 65536 && ttId != 1330926671) {
/*  426 */       if (this.fileName != null) {
/*  427 */         throw (new IOException("{0} is not a valid ttf or otf file.")).setMessageParams(new Object[] { this.fileName });
/*      */       }
/*  429 */       throw new IOException("Not a valid ttf or otf file.");
/*      */     } 
/*      */     
/*  432 */     int num_tables = this.raf.readUnsignedShort();
/*  433 */     this.raf.skipBytes(6);
/*  434 */     for (int k = 0; k < num_tables; k++) {
/*  435 */       String tag = readStandardString(4);
/*  436 */       this.raf.skipBytes(4);
/*  437 */       int[] table_location = new int[2];
/*  438 */       table_location[0] = this.raf.readInt();
/*  439 */       table_location[1] = this.raf.readInt();
/*  440 */       this.tables.put(tag, table_location);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadTables(boolean all) throws IOException {
/*  449 */     readNameTable();
/*  450 */     readHeadTable();
/*  451 */     readOs_2Table();
/*  452 */     readPostTable();
/*  453 */     if (all) {
/*  454 */       checkCff();
/*  455 */       readHheaTable();
/*  456 */       readGlyphWidths();
/*  457 */       readCmapTable();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String getTTCName(String name) {
/*  470 */     if (name == null) {
/*  471 */       return null;
/*      */     }
/*  473 */     int idx = name.toLowerCase().indexOf(".ttc,");
/*  474 */     if (idx < 0) {
/*  475 */       return name;
/*      */     }
/*  477 */     return name.substring(0, idx + 4);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkCff() {
/*  482 */     int[] table_location = this.tables.get("CFF ");
/*  483 */     if (table_location != null) {
/*  484 */       this.cff = true;
/*  485 */       this.cffOffset = table_location[0];
/*  486 */       this.cffLength = table_location[1];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void readGlyphWidths() throws IOException {
/*  498 */     int numberOfHMetrics = this.hhea.numberOfHMetrics;
/*  499 */     int unitsPerEm = this.head.unitsPerEm;
/*      */     
/*  501 */     int[] table_location = this.tables.get("hmtx");
/*  502 */     if (table_location == null) {
/*  503 */       if (this.fileName != null) {
/*  504 */         throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "hmtx", this.fileName });
/*      */       }
/*  506 */       throw (new IOException("Table {0} does not exist.")).setMessageParams(new Object[] { "hmtx" });
/*      */     } 
/*      */     
/*  509 */     this.glyphWidthsByIndex = new int[readNumGlyphs()];
/*  510 */     this.raf.seek(table_location[0]); int k;
/*  511 */     for (k = 0; k < numberOfHMetrics; k++) {
/*  512 */       this.glyphWidthsByIndex[k] = this.raf.readUnsignedShort() * 1000 / unitsPerEm;
/*      */       
/*  514 */       int i = this.raf.readShort() * 1000 / unitsPerEm;
/*      */     } 
/*      */ 
/*      */     
/*  518 */     if (numberOfHMetrics > 0) {
/*  519 */       for (k = numberOfHMetrics; k < this.glyphWidthsByIndex.length; k++) {
/*  520 */         this.glyphWidthsByIndex[k] = this.glyphWidthsByIndex[numberOfHMetrics - 1];
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IntHashtable readKerning(int unitsPerEm) throws IOException {
/*  533 */     int[] table_location = this.tables.get("kern");
/*  534 */     IntHashtable kerning = new IntHashtable();
/*  535 */     if (table_location == null) {
/*  536 */       return kerning;
/*      */     }
/*  538 */     this.raf.seek((table_location[0] + 2));
/*  539 */     int nTables = this.raf.readUnsignedShort();
/*  540 */     int checkpoint = table_location[0] + 4;
/*  541 */     int length = 0;
/*  542 */     for (int k = 0; k < nTables; k++) {
/*  543 */       checkpoint += length;
/*  544 */       this.raf.seek(checkpoint);
/*  545 */       this.raf.skipBytes(2);
/*  546 */       length = this.raf.readUnsignedShort();
/*  547 */       int coverage = this.raf.readUnsignedShort();
/*  548 */       if ((coverage & 0xFFF7) == 1) {
/*  549 */         int nPairs = this.raf.readUnsignedShort();
/*  550 */         this.raf.skipBytes(6);
/*  551 */         for (int j = 0; j < nPairs; j++) {
/*  552 */           int pair = this.raf.readInt();
/*  553 */           int value = this.raf.readShort() * 1000 / unitsPerEm;
/*  554 */           kerning.put(pair, value);
/*      */         } 
/*      */       } 
/*      */     } 
/*  558 */     return kerning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int[][] readBbox(int unitsPerEm) throws IOException {
/*  570 */     int[] locaTable, tableLocation = this.tables.get("head");
/*  571 */     if (tableLocation == null) {
/*  572 */       if (this.fileName != null) {
/*  573 */         throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "head", this.fileName });
/*      */       }
/*  575 */       throw (new IOException("Table {0} does not exist.")).setMessageParams(new Object[] { "head" });
/*      */     } 
/*      */     
/*  578 */     this.raf.seek((tableLocation[0] + 51));
/*  579 */     boolean locaShortTable = (this.raf.readUnsignedShort() == 0);
/*  580 */     tableLocation = this.tables.get("loca");
/*  581 */     if (tableLocation == null) {
/*  582 */       return (int[][])null;
/*      */     }
/*  584 */     this.raf.seek(tableLocation[0]);
/*      */     
/*  586 */     if (locaShortTable) {
/*  587 */       int entries = tableLocation[1] / 2;
/*  588 */       locaTable = new int[entries];
/*  589 */       for (int k = 0; k < entries; k++) {
/*  590 */         locaTable[k] = this.raf.readUnsignedShort() * 2;
/*      */       }
/*      */     } else {
/*  593 */       int entries = tableLocation[1] / 4;
/*  594 */       locaTable = new int[entries];
/*  595 */       for (int k = 0; k < entries; k++) {
/*  596 */         locaTable[k] = this.raf.readInt();
/*      */       }
/*      */     } 
/*      */     
/*  600 */     tableLocation = this.tables.get("glyf");
/*  601 */     if (tableLocation == null) {
/*  602 */       if (this.fileName != null) {
/*  603 */         throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "glyf", this.fileName });
/*      */       }
/*  605 */       throw (new IOException("Table {0} does not exist.")).setMessageParams(new Object[] { "glyf" });
/*      */     } 
/*      */     
/*  608 */     int tableGlyphOffset = tableLocation[0];
/*  609 */     int[][] bboxes = new int[locaTable.length - 1][];
/*  610 */     for (int glyph = 0; glyph < locaTable.length - 1; glyph++) {
/*  611 */       int start = locaTable[glyph];
/*  612 */       if (start != locaTable[glyph + 1]) {
/*  613 */         this.raf.seek((tableGlyphOffset + start + 2));
/*  614 */         (new int[4])[0] = this.raf
/*  615 */           .readShort() * 1000 / unitsPerEm; (new int[4])[1] = this.raf
/*  616 */           .readShort() * 1000 / unitsPerEm; (new int[4])[2] = this.raf
/*  617 */           .readShort() * 1000 / unitsPerEm; (new int[4])[3] = this.raf
/*  618 */           .readShort() * 1000 / unitsPerEm;
/*      */         bboxes[glyph] = new int[4];
/*      */       } 
/*      */     } 
/*  622 */     return bboxes;
/*      */   }
/*      */   
/*      */   protected int readNumGlyphs() throws IOException {
/*  626 */     int[] table_location = this.tables.get("maxp");
/*  627 */     if (table_location == null) {
/*  628 */       return 65536;
/*      */     }
/*  630 */     this.raf.seek((table_location[0] + 4));
/*  631 */     return this.raf.readUnsignedShort();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readNameTable() throws IOException {
/*  642 */     int[] table_location = this.tables.get("name");
/*  643 */     if (table_location == null) {
/*  644 */       if (this.fileName != null) {
/*  645 */         throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "name", this.fileName });
/*      */       }
/*  647 */       throw (new IOException("Table {0} does not exist.")).setMessageParams(new Object[] { "name" });
/*      */     } 
/*      */     
/*  650 */     this.allNameEntries = new LinkedHashMap<>();
/*  651 */     this.raf.seek((table_location[0] + 2));
/*  652 */     int numRecords = this.raf.readUnsignedShort();
/*  653 */     int startOfStorage = this.raf.readUnsignedShort();
/*  654 */     for (int k = 0; k < numRecords; k++) {
/*  655 */       List<String[]> names; String name; int platformID = this.raf.readUnsignedShort();
/*  656 */       int platformEncodingID = this.raf.readUnsignedShort();
/*  657 */       int languageID = this.raf.readUnsignedShort();
/*  658 */       int nameID = this.raf.readUnsignedShort();
/*  659 */       int length = this.raf.readUnsignedShort();
/*  660 */       int offset = this.raf.readUnsignedShort();
/*      */       
/*  662 */       if (this.allNameEntries.containsKey(Integer.valueOf(nameID))) {
/*  663 */         names = this.allNameEntries.get(Integer.valueOf(nameID));
/*      */       } else {
/*  665 */         this.allNameEntries.put(Integer.valueOf(nameID), names = (List)new ArrayList<>());
/*      */       } 
/*  667 */       int pos = (int)this.raf.getPosition();
/*  668 */       this.raf.seek((table_location[0] + startOfStorage + offset));
/*      */       
/*  670 */       if (platformID == 0 || platformID == 3 || (platformID == 2 && platformEncodingID == 1)) {
/*  671 */         name = readUnicodeString(length);
/*      */       } else {
/*  673 */         name = readStandardString(length);
/*      */       } 
/*  675 */       names.add(new String[] {
/*  676 */             Integer.toString(platformID), 
/*  677 */             Integer.toString(platformEncodingID), 
/*  678 */             Integer.toString(languageID), name
/*      */           });
/*      */       
/*  681 */       this.raf.seek(pos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readHheaTable() throws IOException {
/*  692 */     int[] table_location = this.tables.get("hhea");
/*  693 */     if (table_location == null) {
/*  694 */       if (this.fileName != null) {
/*  695 */         throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "hhea", this.fileName });
/*      */       }
/*  697 */       throw (new IOException("Table {0} does not exist.")).setMessageParams(new Object[] { "hhea" });
/*      */     } 
/*      */     
/*  700 */     this.raf.seek((table_location[0] + 4));
/*  701 */     this.hhea = new HorizontalHeader();
/*  702 */     this.hhea.Ascender = this.raf.readShort();
/*  703 */     this.hhea.Descender = this.raf.readShort();
/*  704 */     this.hhea.LineGap = this.raf.readShort();
/*  705 */     this.hhea.advanceWidthMax = this.raf.readUnsignedShort();
/*  706 */     this.hhea.minLeftSideBearing = this.raf.readShort();
/*  707 */     this.hhea.minRightSideBearing = this.raf.readShort();
/*  708 */     this.hhea.xMaxExtent = this.raf.readShort();
/*  709 */     this.hhea.caretSlopeRise = this.raf.readShort();
/*  710 */     this.hhea.caretSlopeRun = this.raf.readShort();
/*  711 */     this.raf.skipBytes(12);
/*  712 */     this.hhea.numberOfHMetrics = this.raf.readUnsignedShort();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readHeadTable() throws IOException {
/*  722 */     int[] table_location = this.tables.get("head");
/*  723 */     if (table_location == null) {
/*  724 */       if (this.fileName != null) {
/*  725 */         throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "head", this.fileName });
/*      */       }
/*  727 */       throw (new IOException("Table {0} does not exist.")).setMessageParams(new Object[] { "head" });
/*      */     } 
/*      */     
/*  730 */     this.raf.seek((table_location[0] + 16));
/*  731 */     this.head = new HeaderTable();
/*  732 */     this.head.flags = this.raf.readUnsignedShort();
/*  733 */     this.head.unitsPerEm = this.raf.readUnsignedShort();
/*  734 */     this.raf.skipBytes(16);
/*  735 */     this.head.xMin = this.raf.readShort();
/*  736 */     this.head.yMin = this.raf.readShort();
/*  737 */     this.head.xMax = this.raf.readShort();
/*  738 */     this.head.yMax = this.raf.readShort();
/*  739 */     this.head.macStyle = this.raf.readUnsignedShort();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readOs_2Table() throws IOException {
/*  750 */     int[] table_location = this.tables.get("OS/2");
/*  751 */     if (table_location == null) {
/*  752 */       if (this.fileName != null) {
/*  753 */         throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "os/2", this.fileName });
/*      */       }
/*  755 */       throw (new IOException("Table {0} does not exist.")).setMessageParams(new Object[] { "os/2" });
/*      */     } 
/*      */     
/*  758 */     this.os_2 = new WindowsMetrics();
/*  759 */     this.raf.seek(table_location[0]);
/*  760 */     int version = this.raf.readUnsignedShort();
/*  761 */     this.os_2.xAvgCharWidth = this.raf.readShort();
/*  762 */     this.os_2.usWeightClass = this.raf.readUnsignedShort();
/*  763 */     this.os_2.usWidthClass = this.raf.readUnsignedShort();
/*  764 */     this.os_2.fsType = this.raf.readShort();
/*  765 */     this.os_2.ySubscriptXSize = this.raf.readShort();
/*  766 */     this.os_2.ySubscriptYSize = this.raf.readShort();
/*  767 */     this.os_2.ySubscriptXOffset = this.raf.readShort();
/*  768 */     this.os_2.ySubscriptYOffset = this.raf.readShort();
/*  769 */     this.os_2.ySuperscriptXSize = this.raf.readShort();
/*  770 */     this.os_2.ySuperscriptYSize = this.raf.readShort();
/*  771 */     this.os_2.ySuperscriptXOffset = this.raf.readShort();
/*  772 */     this.os_2.ySuperscriptYOffset = this.raf.readShort();
/*  773 */     this.os_2.yStrikeoutSize = this.raf.readShort();
/*  774 */     this.os_2.yStrikeoutPosition = this.raf.readShort();
/*  775 */     this.os_2.sFamilyClass = this.raf.readShort();
/*  776 */     this.raf.readFully(this.os_2.panose);
/*  777 */     this.raf.skipBytes(16);
/*  778 */     this.raf.readFully(this.os_2.achVendID);
/*  779 */     this.os_2.fsSelection = this.raf.readUnsignedShort();
/*  780 */     this.os_2.usFirstCharIndex = this.raf.readUnsignedShort();
/*  781 */     this.os_2.usLastCharIndex = this.raf.readUnsignedShort();
/*  782 */     this.os_2.sTypoAscender = this.raf.readShort();
/*  783 */     this.os_2.sTypoDescender = this.raf.readShort();
/*  784 */     if (this.os_2.sTypoDescender > 0) {
/*  785 */       this.os_2.sTypoDescender = (short)-this.os_2.sTypoDescender;
/*      */     }
/*  787 */     this.os_2.sTypoLineGap = this.raf.readShort();
/*  788 */     this.os_2.usWinAscent = this.raf.readUnsignedShort();
/*  789 */     this.os_2.usWinDescent = this.raf.readUnsignedShort();
/*  790 */     if (this.os_2.usWinDescent > 0) {
/*  791 */       this.os_2.usWinDescent = (short)-this.os_2.usWinDescent;
/*      */     }
/*  793 */     this.os_2.ulCodePageRange1 = 0;
/*  794 */     this.os_2.ulCodePageRange2 = 0;
/*  795 */     if (version > 0) {
/*  796 */       this.os_2.ulCodePageRange1 = this.raf.readInt();
/*  797 */       this.os_2.ulCodePageRange2 = this.raf.readInt();
/*      */     } 
/*  799 */     if (version > 1) {
/*      */       
/*  801 */       this.raf.skipBytes(2);
/*  802 */       this.os_2.sCapHeight = this.raf.readShort();
/*      */     } else {
/*  804 */       this.os_2.sCapHeight = (int)(0.7D * this.head.unitsPerEm);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readPostTable() throws IOException {
/*  809 */     int[] table_location = this.tables.get("post");
/*  810 */     if (table_location != null) {
/*  811 */       this.raf.seek((table_location[0] + 4));
/*  812 */       short mantissa = this.raf.readShort();
/*  813 */       int fraction = this.raf.readUnsignedShort();
/*  814 */       this.post = new PostTable();
/*  815 */       this.post.italicAngle = (float)(mantissa + fraction / 16384.0D);
/*  816 */       this.post.underlinePosition = this.raf.readShort();
/*  817 */       this.post.underlineThickness = this.raf.readShort();
/*  818 */       this.post.isFixedPitch = (this.raf.readInt() != 0);
/*      */     } else {
/*  820 */       this.post = new PostTable();
/*  821 */       this.post.italicAngle = (float)(-Math.atan2(this.hhea.caretSlopeRun, this.hhea.caretSlopeRise) * 180.0D / Math.PI);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readCmapTable() throws IOException {
/*  833 */     int[] table_location = this.tables.get("cmap");
/*  834 */     if (table_location == null) {
/*  835 */       if (this.fileName != null) {
/*  836 */         throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "cmap", this.fileName });
/*      */       }
/*  838 */       throw (new IOException("Table {0} does not exist.")).setMessageParams(new Object[] { "cmap" });
/*      */     } 
/*      */     
/*  841 */     this.raf.seek(table_location[0]);
/*  842 */     this.raf.skipBytes(2);
/*  843 */     int num_tables = this.raf.readUnsignedShort();
/*  844 */     int map10 = 0;
/*  845 */     int map31 = 0;
/*  846 */     int map30 = 0;
/*  847 */     int mapExt = 0;
/*  848 */     this.cmaps = new CmapTable();
/*  849 */     for (int k = 0; k < num_tables; k++) {
/*  850 */       int platId = this.raf.readUnsignedShort();
/*  851 */       int platSpecId = this.raf.readUnsignedShort();
/*  852 */       int offset = this.raf.readInt();
/*  853 */       if (platId == 3 && platSpecId == 0) {
/*  854 */         this.cmaps.fontSpecific = true;
/*  855 */         map30 = offset;
/*  856 */       } else if (platId == 3 && platSpecId == 1) {
/*  857 */         map31 = offset;
/*  858 */       } else if (platId == 3 && platSpecId == 10) {
/*  859 */         mapExt = offset;
/*  860 */       } else if (platId == 1 && platSpecId == 0) {
/*  861 */         map10 = offset;
/*      */       } 
/*      */     } 
/*  864 */     if (map10 > 0) {
/*  865 */       this.raf.seek((table_location[0] + map10));
/*  866 */       int format = this.raf.readUnsignedShort();
/*  867 */       switch (format) {
/*      */         case 0:
/*  869 */           this.cmaps.cmap10 = readFormat0();
/*      */           break;
/*      */         case 4:
/*  872 */           this.cmaps.cmap10 = readFormat4(false);
/*      */           break;
/*      */         case 6:
/*  875 */           this.cmaps.cmap10 = readFormat6();
/*      */           break;
/*      */       } 
/*      */     } 
/*  879 */     if (map31 > 0) {
/*  880 */       this.raf.seek((table_location[0] + map31));
/*  881 */       int format = this.raf.readUnsignedShort();
/*  882 */       if (format == 4) {
/*  883 */         this.cmaps.cmap31 = readFormat4(false);
/*      */       }
/*      */     } 
/*  886 */     if (map30 > 0) {
/*  887 */       this.raf.seek((table_location[0] + map30));
/*  888 */       int format = this.raf.readUnsignedShort();
/*  889 */       if (format == 4) {
/*  890 */         this.cmaps.cmap10 = readFormat4(this.cmaps.fontSpecific);
/*      */       } else {
/*  892 */         this.cmaps.fontSpecific = false;
/*      */       } 
/*      */     } 
/*  895 */     if (mapExt > 0) {
/*  896 */       this.raf.seek((table_location[0] + mapExt));
/*  897 */       int format = this.raf.readUnsignedShort();
/*  898 */       switch (format) {
/*      */         case 0:
/*  900 */           this.cmaps.cmapExt = readFormat0();
/*      */           break;
/*      */         case 4:
/*  903 */           this.cmaps.cmapExt = readFormat4(false);
/*      */           break;
/*      */         case 6:
/*  906 */           this.cmaps.cmapExt = readFormat6();
/*      */           break;
/*      */         case 12:
/*  909 */           this.cmaps.cmapExt = readFormat12();
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readStandardString(int length) throws IOException {
/*  924 */     return this.raf.readString(length, "Cp1252");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readUnicodeString(int length) throws IOException {
/*  935 */     StringBuilder buf = new StringBuilder();
/*  936 */     length /= 2;
/*  937 */     for (int k = 0; k < length; k++) {
/*  938 */       buf.append(this.raf.readChar());
/*      */     }
/*  940 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getGlyphWidth(int glyph) {
/*  950 */     if (glyph >= this.glyphWidthsByIndex.length)
/*  951 */       glyph = this.glyphWidthsByIndex.length - 1; 
/*  952 */     return this.glyphWidthsByIndex[glyph];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, int[]> readFormat0() throws IOException {
/*  963 */     Map<Integer, int[]> h = (Map)new LinkedHashMap<>();
/*  964 */     this.raf.skipBytes(4);
/*  965 */     for (int k = 0; k < 256; k++) {
/*  966 */       int[] r = new int[2];
/*  967 */       r[0] = this.raf.readUnsignedByte();
/*  968 */       r[1] = getGlyphWidth(r[0]);
/*  969 */       h.put(Integer.valueOf(k), r);
/*      */     } 
/*  971 */     return h;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, int[]> readFormat4(boolean fontSpecific) throws IOException {
/*  982 */     Map<Integer, int[]> h = (Map)new LinkedHashMap<>();
/*  983 */     int table_lenght = this.raf.readUnsignedShort();
/*  984 */     this.raf.skipBytes(2);
/*  985 */     int segCount = this.raf.readUnsignedShort() / 2;
/*  986 */     this.raf.skipBytes(6);
/*  987 */     int[] endCount = new int[segCount];
/*  988 */     for (int k = 0; k < segCount; k++) {
/*  989 */       endCount[k] = this.raf.readUnsignedShort();
/*      */     }
/*  991 */     this.raf.skipBytes(2);
/*  992 */     int[] startCount = new int[segCount];
/*  993 */     for (int i = 0; i < segCount; i++) {
/*  994 */       startCount[i] = this.raf.readUnsignedShort();
/*      */     }
/*  996 */     int[] idDelta = new int[segCount];
/*  997 */     for (int j = 0; j < segCount; j++) {
/*  998 */       idDelta[j] = this.raf.readUnsignedShort();
/*      */     }
/* 1000 */     int[] idRO = new int[segCount];
/* 1001 */     for (int m = 0; m < segCount; m++) {
/* 1002 */       idRO[m] = this.raf.readUnsignedShort();
/*      */     }
/* 1004 */     int[] glyphId = new int[table_lenght / 2 - 8 - segCount * 4]; int n;
/* 1005 */     for (n = 0; n < glyphId.length; n++) {
/* 1006 */       glyphId[n] = this.raf.readUnsignedShort();
/*      */     }
/* 1008 */     for (n = 0; n < segCount; n++) {
/*      */       
/* 1010 */       for (int i1 = startCount[n]; i1 <= endCount[n] && i1 != 65535; i1++) {
/* 1011 */         int glyph; if (idRO[n] == 0) {
/* 1012 */           glyph = i1 + idDelta[n] & 0xFFFF;
/*      */         } else {
/* 1014 */           int idx = n + idRO[n] / 2 - segCount + i1 - startCount[n];
/* 1015 */           if (idx >= glyphId.length)
/*      */             continue; 
/* 1017 */           glyph = glyphId[idx] + idDelta[n] & 0xFFFF;
/*      */         } 
/* 1019 */         int[] r = new int[2];
/* 1020 */         r[0] = glyph;
/* 1021 */         r[1] = getGlyphWidth(r[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1026 */         if (fontSpecific && (i1 & 0xFF00) == 61440) {
/* 1027 */           h.put(Integer.valueOf(i1 & 0xFF), r);
/*      */         }
/* 1029 */         h.put(Integer.valueOf(i1), r); continue;
/*      */       } 
/*      */     } 
/* 1032 */     return h;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, int[]> readFormat6() throws IOException {
/* 1044 */     Map<Integer, int[]> h = (Map)new LinkedHashMap<>();
/* 1045 */     this.raf.skipBytes(4);
/* 1046 */     int start_code = this.raf.readUnsignedShort();
/* 1047 */     int code_count = this.raf.readUnsignedShort();
/* 1048 */     for (int k = 0; k < code_count; k++) {
/* 1049 */       int[] r = new int[2];
/* 1050 */       r[0] = this.raf.readUnsignedShort();
/* 1051 */       r[1] = getGlyphWidth(r[0]);
/* 1052 */       h.put(Integer.valueOf(k + start_code), r);
/*      */     } 
/* 1054 */     return h;
/*      */   }
/*      */   
/*      */   private Map<Integer, int[]> readFormat12() throws IOException {
/* 1058 */     Map<Integer, int[]> h = (Map)new LinkedHashMap<>();
/* 1059 */     this.raf.skipBytes(2);
/*      */     
/* 1061 */     int table_length = this.raf.readInt();
/* 1062 */     this.raf.skipBytes(4);
/* 1063 */     int nGroups = this.raf.readInt();
/* 1064 */     for (int k = 0; k < nGroups; k++) {
/* 1065 */       int startCharCode = this.raf.readInt();
/* 1066 */       int endCharCode = this.raf.readInt();
/* 1067 */       int startGlyphID = this.raf.readInt();
/* 1068 */       for (int i = startCharCode; i <= endCharCode; i++) {
/* 1069 */         int[] r = new int[2];
/* 1070 */         r[0] = startGlyphID;
/* 1071 */         r[1] = getGlyphWidth(r[0]);
/* 1072 */         h.put(Integer.valueOf(i), r);
/* 1073 */         startGlyphID++;
/*      */       } 
/*      */     } 
/* 1076 */     return h;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/OpenTypeParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */