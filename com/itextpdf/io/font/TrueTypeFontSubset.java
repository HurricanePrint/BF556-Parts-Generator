/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TrueTypeFontSubset
/*     */ {
/*  66 */   private static final String[] TABLE_NAMES_SUBSET = new String[] { "cvt ", "fpgm", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "prep", "cmap", "OS/2" };
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final String[] TABLE_NAMES = new String[] { "cvt ", "fpgm", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "prep", "cmap", "OS/2", "name", "post" };
/*     */   
/*  72 */   private static final int[] entrySelectors = new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4 };
/*     */   
/*     */   private static final int TABLE_CHECKSUM = 0;
/*     */   
/*     */   private static final int TABLE_OFFSET = 1;
/*     */   
/*     */   private static final int TABLE_LENGTH = 2;
/*     */   
/*     */   private static final int HEAD_LOCA_FORMAT_OFFSET = 51;
/*     */   
/*     */   private static final int ARG_1_AND_2_ARE_WORDS = 1;
/*     */   
/*     */   private static final int WE_HAVE_A_SCALE = 8;
/*     */   
/*     */   private static final int MORE_COMPONENTS = 32;
/*     */   
/*     */   private static final int WE_HAVE_AN_X_AND_Y_SCALE = 64;
/*     */   
/*     */   private static final int WE_HAVE_A_TWO_BY_TWO = 128;
/*     */   
/*     */   private Map<String, int[]> tableDirectory;
/*     */   
/*     */   protected RandomAccessFileOrArray rf;
/*     */   
/*     */   private String fileName;
/*     */   
/*     */   private boolean locaShortTable;
/*     */   
/*     */   private int[] locaTable;
/*     */   
/*     */   private Set<Integer> glyphsUsed;
/*     */   
/*     */   private List<Integer> glyphsInList;
/*     */   
/*     */   private int tableGlyphOffset;
/*     */   
/*     */   private int[] newLocaTable;
/*     */   
/*     */   private byte[] newLocaTableOut;
/*     */   
/*     */   private byte[] newGlyfTable;
/*     */   
/*     */   private int glyfTableRealSize;
/*     */   
/*     */   private int locaTableRealSize;
/*     */   private byte[] outFont;
/*     */   private int fontPtr;
/*     */   private int directoryOffset;
/*     */   private final String[] tableNames;
/*     */   
/*     */   TrueTypeFontSubset(String fileName, RandomAccessFileOrArray rf, Set<Integer> glyphsUsed, int directoryOffset, boolean subset) {
/* 123 */     this.fileName = fileName;
/* 124 */     this.rf = rf;
/* 125 */     this.glyphsUsed = new HashSet<>(glyphsUsed);
/* 126 */     this.directoryOffset = directoryOffset;
/*     */     
/* 128 */     if (subset) {
/* 129 */       this.tableNames = TABLE_NAMES_SUBSET;
/*     */     } else {
/* 131 */       this.tableNames = TABLE_NAMES;
/*     */     } 
/* 133 */     this.glyphsInList = new ArrayList<>(glyphsUsed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] process() throws IOException {
/*     */     try {
/* 144 */       createTableDirectory();
/* 145 */       readLoca();
/* 146 */       flatGlyphs();
/* 147 */       createNewGlyphTables();
/* 148 */       locaToBytes();
/* 149 */       assembleFont();
/* 150 */       return this.outFont;
/*     */     } finally {
/*     */       try {
/* 153 */         this.rf.close();
/* 154 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void assembleFont() throws IOException {
/* 161 */     int fullFontSize = 0;
/* 162 */     int tablesUsed = 2;
/* 163 */     for (String name : this.tableNames) {
/* 164 */       if (!name.equals("glyf") && !name.equals("loca")) {
/*     */ 
/*     */         
/* 167 */         int[] tableLocation = this.tableDirectory.get(name);
/* 168 */         if (tableLocation != null)
/*     */         
/*     */         { 
/* 171 */           tablesUsed++;
/* 172 */           fullFontSize += tableLocation[2] + 3 & 0xFFFFFFFC; } 
/*     */       } 
/* 174 */     }  fullFontSize += this.newLocaTableOut.length;
/* 175 */     fullFontSize += this.newGlyfTable.length;
/* 176 */     int reference = 16 * tablesUsed + 12;
/* 177 */     fullFontSize += reference;
/* 178 */     this.outFont = new byte[fullFontSize];
/* 179 */     this.fontPtr = 0;
/* 180 */     writeFontInt(65536);
/* 181 */     writeFontShort(tablesUsed);
/* 182 */     int selector = entrySelectors[tablesUsed];
/* 183 */     writeFontShort((1 << selector) * 16);
/* 184 */     writeFontShort(selector);
/* 185 */     writeFontShort((tablesUsed - (1 << selector)) * 16);
/* 186 */     for (String name : this.tableNames) {
/*     */       
/* 188 */       int[] tableLocation = this.tableDirectory.get(name);
/* 189 */       if (tableLocation != null) {
/*     */         int len;
/*     */         
/* 192 */         writeFontString(name);
/* 193 */         switch (name) {
/*     */           case "glyf":
/* 195 */             writeFontInt(calculateChecksum(this.newGlyfTable));
/* 196 */             len = this.glyfTableRealSize;
/*     */             break;
/*     */           case "loca":
/* 199 */             writeFontInt(calculateChecksum(this.newLocaTableOut));
/* 200 */             len = this.locaTableRealSize;
/*     */             break;
/*     */           default:
/* 203 */             writeFontInt(tableLocation[0]);
/* 204 */             len = tableLocation[2];
/*     */             break;
/*     */         } 
/* 207 */         writeFontInt(reference);
/* 208 */         writeFontInt(len);
/* 209 */         reference += len + 3 & 0xFFFFFFFC;
/*     */       } 
/* 211 */     }  for (String name : this.tableNames) {
/* 212 */       int[] tableLocation = this.tableDirectory.get(name);
/* 213 */       if (tableLocation != null)
/*     */       {
/*     */         
/* 216 */         switch (name) {
/*     */           case "glyf":
/* 218 */             System.arraycopy(this.newGlyfTable, 0, this.outFont, this.fontPtr, this.newGlyfTable.length);
/* 219 */             this.fontPtr += this.newGlyfTable.length;
/* 220 */             this.newGlyfTable = null;
/*     */             break;
/*     */           case "loca":
/* 223 */             System.arraycopy(this.newLocaTableOut, 0, this.outFont, this.fontPtr, this.newLocaTableOut.length);
/* 224 */             this.fontPtr += this.newLocaTableOut.length;
/* 225 */             this.newLocaTableOut = null;
/*     */             break;
/*     */           default:
/* 228 */             this.rf.seek(tableLocation[1]);
/* 229 */             this.rf.readFully(this.outFont, this.fontPtr, tableLocation[2]);
/* 230 */             this.fontPtr += tableLocation[2] + 3 & 0xFFFFFFFC;
/*     */             break;
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createTableDirectory() throws IOException {
/* 237 */     this.tableDirectory = (Map)new HashMap<>();
/* 238 */     this.rf.seek(this.directoryOffset);
/* 239 */     int id = this.rf.readInt();
/* 240 */     if (id != 65536) {
/* 241 */       throw (new IOException("{0} is not a true type file")).setMessageParams(new Object[] { this.fileName });
/*     */     }
/* 243 */     int num_tables = this.rf.readUnsignedShort();
/* 244 */     this.rf.skipBytes(6);
/* 245 */     for (int k = 0; k < num_tables; k++) {
/* 246 */       String tag = readStandardString(4);
/* 247 */       int[] tableLocation = new int[3];
/* 248 */       tableLocation[0] = this.rf.readInt();
/* 249 */       tableLocation[1] = this.rf.readInt();
/* 250 */       tableLocation[2] = this.rf.readInt();
/* 251 */       this.tableDirectory.put(tag, tableLocation);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readLoca() throws IOException {
/* 256 */     int[] tableLocation = this.tableDirectory.get("head");
/* 257 */     if (tableLocation == null) {
/* 258 */       throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "head", this.fileName });
/*     */     }
/* 260 */     this.rf.seek((tableLocation[1] + 51));
/* 261 */     this.locaShortTable = (this.rf.readUnsignedShort() == 0);
/* 262 */     tableLocation = this.tableDirectory.get("loca");
/* 263 */     if (tableLocation == null) {
/* 264 */       throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "loca", this.fileName });
/*     */     }
/* 266 */     this.rf.seek(tableLocation[1]);
/* 267 */     if (this.locaShortTable) {
/* 268 */       int entries = tableLocation[2] / 2;
/* 269 */       this.locaTable = new int[entries];
/* 270 */       for (int k = 0; k < entries; k++) {
/* 271 */         this.locaTable[k] = this.rf.readUnsignedShort() * 2;
/*     */       }
/*     */     } else {
/* 274 */       int entries = tableLocation[2] / 4;
/* 275 */       this.locaTable = new int[entries];
/* 276 */       for (int k = 0; k < entries; k++) {
/* 277 */         this.locaTable[k] = this.rf.readInt();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createNewGlyphTables() throws IOException {
/* 283 */     this.newLocaTable = new int[this.locaTable.length];
/* 284 */     int[] activeGlyphs = new int[this.glyphsInList.size()];
/* 285 */     for (int k = 0; k < activeGlyphs.length; k++) {
/* 286 */       activeGlyphs[k] = ((Integer)this.glyphsInList.get(k)).intValue();
/*     */     }
/* 288 */     Arrays.sort(activeGlyphs);
/* 289 */     int glyfSize = 0;
/* 290 */     for (int glyph : activeGlyphs) {
/* 291 */       glyfSize += this.locaTable[glyph + 1] - this.locaTable[glyph];
/*     */     }
/* 293 */     this.glyfTableRealSize = glyfSize;
/* 294 */     glyfSize = glyfSize + 3 & 0xFFFFFFFC;
/* 295 */     this.newGlyfTable = new byte[glyfSize];
/* 296 */     int glyfPtr = 0;
/* 297 */     int listGlyf = 0;
/* 298 */     for (int i = 0; i < this.newLocaTable.length; i++) {
/* 299 */       this.newLocaTable[i] = glyfPtr;
/* 300 */       if (listGlyf < activeGlyphs.length && activeGlyphs[listGlyf] == i) {
/* 301 */         listGlyf++;
/* 302 */         this.newLocaTable[i] = glyfPtr;
/* 303 */         int start = this.locaTable[i];
/* 304 */         int len = this.locaTable[i + 1] - start;
/* 305 */         if (len > 0) {
/* 306 */           this.rf.seek((this.tableGlyphOffset + start));
/* 307 */           this.rf.readFully(this.newGlyfTable, glyfPtr, len);
/* 308 */           glyfPtr += len;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void locaToBytes() {
/* 315 */     if (this.locaShortTable) {
/* 316 */       this.locaTableRealSize = this.newLocaTable.length * 2;
/*     */     } else {
/* 318 */       this.locaTableRealSize = this.newLocaTable.length * 4;
/*     */     } 
/* 320 */     this.newLocaTableOut = new byte[this.locaTableRealSize + 3 & 0xFFFFFFFC];
/* 321 */     this.outFont = this.newLocaTableOut;
/* 322 */     this.fontPtr = 0;
/* 323 */     for (int location : this.newLocaTable) {
/* 324 */       if (this.locaShortTable) {
/* 325 */         writeFontShort(location / 2);
/*     */       } else {
/* 327 */         writeFontInt(location);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void flatGlyphs() throws IOException {
/* 332 */     int[] tableLocation = this.tableDirectory.get("glyf");
/* 333 */     if (tableLocation == null)
/* 334 */       throw (new IOException("Table {0} does not exist in {1}")).setMessageParams(new Object[] { "glyf", this.fileName }); 
/* 335 */     int glyph0 = 0;
/* 336 */     if (!this.glyphsUsed.contains(Integer.valueOf(glyph0))) {
/* 337 */       this.glyphsUsed.add(Integer.valueOf(glyph0));
/* 338 */       this.glyphsInList.add(Integer.valueOf(glyph0));
/*     */     } 
/* 340 */     this.tableGlyphOffset = tableLocation[1];
/*     */ 
/*     */     
/* 343 */     for (int i = 0; i < this.glyphsInList.size(); i++) {
/* 344 */       checkGlyphComposite(((Integer)this.glyphsInList.get(i)).intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkGlyphComposite(int glyph) throws IOException {
/* 349 */     int start = this.locaTable[glyph];
/*     */ 
/*     */     
/* 352 */     if (start == this.locaTable[glyph + 1]) {
/*     */       return;
/*     */     }
/* 355 */     this.rf.seek((this.tableGlyphOffset + start));
/* 356 */     int numContours = this.rf.readShort();
/* 357 */     if (numContours >= 0) {
/*     */       return;
/*     */     }
/* 360 */     this.rf.skipBytes(8);
/*     */     while (true) {
/* 362 */       int skip, flags = this.rf.readUnsignedShort();
/* 363 */       int cGlyph = this.rf.readUnsignedShort();
/* 364 */       if (!this.glyphsUsed.contains(Integer.valueOf(cGlyph))) {
/* 365 */         this.glyphsUsed.add(Integer.valueOf(cGlyph));
/* 366 */         this.glyphsInList.add(Integer.valueOf(cGlyph));
/*     */       } 
/* 368 */       if ((flags & 0x20) == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 372 */       if ((flags & 0x1) != 0) {
/* 373 */         skip = 4;
/*     */       } else {
/* 375 */         skip = 2;
/*     */       } 
/* 377 */       if ((flags & 0x8) != 0) {
/* 378 */         skip += 2;
/* 379 */       } else if ((flags & 0x40) != 0) {
/* 380 */         skip += 4;
/*     */       } 
/* 382 */       if ((flags & 0x80) != 0) {
/* 383 */         skip += 8;
/*     */       }
/* 385 */       this.rf.skipBytes(skip);
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
/*     */   private String readStandardString(int length) throws IOException {
/* 397 */     byte[] buf = new byte[length];
/* 398 */     this.rf.readFully(buf);
/*     */     try {
/* 400 */       return new String(buf, "Cp1252");
/* 401 */     } catch (Exception e) {
/* 402 */       throw new IOException("TrueType font", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeFontShort(int n) {
/* 407 */     this.outFont[this.fontPtr++] = (byte)(n >> 8);
/* 408 */     this.outFont[this.fontPtr++] = (byte)n;
/*     */   }
/*     */   
/*     */   private void writeFontInt(int n) {
/* 412 */     this.outFont[this.fontPtr++] = (byte)(n >> 24);
/* 413 */     this.outFont[this.fontPtr++] = (byte)(n >> 16);
/* 414 */     this.outFont[this.fontPtr++] = (byte)(n >> 8);
/* 415 */     this.outFont[this.fontPtr++] = (byte)n;
/*     */   }
/*     */   
/*     */   private void writeFontString(String s) {
/* 419 */     byte[] b = PdfEncodings.convertToBytes(s, "Cp1252");
/* 420 */     System.arraycopy(b, 0, this.outFont, this.fontPtr, b.length);
/* 421 */     this.fontPtr += b.length;
/*     */   }
/*     */   
/*     */   private int calculateChecksum(byte[] b) {
/* 425 */     int len = b.length / 4;
/* 426 */     int v0 = 0;
/* 427 */     int v1 = 0;
/* 428 */     int v2 = 0;
/* 429 */     int v3 = 0;
/* 430 */     int ptr = 0;
/* 431 */     for (int k = 0; k < len; k++) {
/* 432 */       v3 += b[ptr++] & 0xFF;
/* 433 */       v2 += b[ptr++] & 0xFF;
/* 434 */       v1 += b[ptr++] & 0xFF;
/* 435 */       v0 += b[ptr++] & 0xFF;
/*     */     } 
/* 437 */     return v0 + (v1 << 8) + (v2 << 16) + (v3 << 24);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/TrueTypeFontSubset.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */