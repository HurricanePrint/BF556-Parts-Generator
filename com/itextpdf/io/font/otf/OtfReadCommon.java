/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ public class OtfReadCommon
/*     */ {
/*     */   public static int[] readUShortArray(RandomAccessFileOrArray rf, int size, int location) throws IOException {
/*  57 */     int[] ret = new int[size];
/*  58 */     for (int k = 0; k < size; k++) {
/*  59 */       int offset = rf.readUnsignedShort();
/*  60 */       ret[k] = (offset == 0) ? offset : (offset + location);
/*     */     } 
/*  62 */     return ret;
/*     */   }
/*     */   
/*     */   public static int[] readUShortArray(RandomAccessFileOrArray rf, int size) throws IOException {
/*  66 */     return readUShortArray(rf, size, 0);
/*     */   }
/*     */   
/*     */   public static void readCoverages(RandomAccessFileOrArray rf, int[] locations, List<Set<Integer>> coverage) throws IOException {
/*  70 */     for (int location : locations) {
/*  71 */       coverage.add(new HashSet<>(readCoverageFormat(rf, location)));
/*     */     }
/*     */   }
/*     */   
/*     */   public static List<Integer> readCoverageFormat(RandomAccessFileOrArray rf, int coverageLocation) throws IOException {
/*     */     List<Integer> glyphIds;
/*  77 */     rf.seek(coverageLocation);
/*  78 */     int coverageFormat = rf.readShort();
/*     */     
/*  80 */     if (coverageFormat == 1) {
/*  81 */       int glyphCount = rf.readShort();
/*  82 */       glyphIds = new ArrayList<>(glyphCount);
/*  83 */       for (int i = 0; i < glyphCount; i++) {
/*  84 */         int coverageGlyphId = rf.readShort();
/*  85 */         glyphIds.add(Integer.valueOf(coverageGlyphId));
/*     */       } 
/*  87 */     } else if (coverageFormat == 2) {
/*  88 */       int rangeCount = rf.readShort();
/*  89 */       glyphIds = new ArrayList<>();
/*  90 */       for (int i = 0; i < rangeCount; i++) {
/*  91 */         readRangeRecord(rf, glyphIds);
/*     */       }
/*     */     } else {
/*     */       
/*  95 */       throw new UnsupportedOperationException(MessageFormatUtil.format("Invalid coverage format: {0}", new Object[] { Integer.valueOf(coverageFormat) }));
/*     */     } 
/*     */     
/*  98 */     return Collections.unmodifiableList(glyphIds);
/*     */   }
/*     */   
/*     */   private static void readRangeRecord(RandomAccessFileOrArray rf, List<Integer> glyphIds) throws IOException {
/* 102 */     int startGlyphId = rf.readShort();
/* 103 */     int endGlyphId = rf.readShort();
/*     */     
/* 105 */     int startCoverageIndex = rf.readShort();
/* 106 */     for (int glyphId = startGlyphId; glyphId <= endGlyphId; glyphId++) {
/* 107 */       glyphIds.add(Integer.valueOf(glyphId));
/*     */     }
/*     */   }
/*     */   
/*     */   public static GposValueRecord readGposValueRecord(OpenTypeFontTableReader tableReader, int mask) throws IOException {
/* 112 */     GposValueRecord vr = new GposValueRecord();
/* 113 */     if ((mask & 0x1) != 0) {
/* 114 */       vr.XPlacement = tableReader.rf.readShort() * 1000 / tableReader.getUnitsPerEm();
/*     */     }
/* 116 */     if ((mask & 0x2) != 0) {
/* 117 */       vr.YPlacement = tableReader.rf.readShort() * 1000 / tableReader.getUnitsPerEm();
/*     */     }
/* 119 */     if ((mask & 0x4) != 0) {
/* 120 */       vr.XAdvance = tableReader.rf.readShort() * 1000 / tableReader.getUnitsPerEm();
/*     */     }
/* 122 */     if ((mask & 0x8) != 0) {
/* 123 */       vr.YAdvance = tableReader.rf.readShort() * 1000 / tableReader.getUnitsPerEm();
/*     */     }
/* 125 */     if ((mask & 0x10) != 0) {
/* 126 */       tableReader.rf.skip(2L);
/*     */     }
/* 128 */     if ((mask & 0x20) != 0) {
/* 129 */       tableReader.rf.skip(2L);
/*     */     }
/* 131 */     if ((mask & 0x40) != 0) {
/* 132 */       tableReader.rf.skip(2L);
/*     */     }
/* 134 */     if ((mask & 0x80) != 0) {
/* 135 */       tableReader.rf.skip(2L);
/*     */     }
/* 137 */     return vr;
/*     */   }
/*     */   
/*     */   public static GposAnchor readGposAnchor(OpenTypeFontTableReader tableReader, int location) throws IOException {
/* 141 */     if (location == 0) {
/* 142 */       return null;
/*     */     }
/* 144 */     tableReader.rf.seek(location);
/* 145 */     int format = tableReader.rf.readUnsignedShort();
/* 146 */     GposAnchor t = null;
/*     */     
/* 148 */     switch (format) {
/*     */     
/* 150 */     }  t = new GposAnchor();
/* 151 */     t.XCoordinate = tableReader.rf.readShort() * 1000 / tableReader.getUnitsPerEm();
/* 152 */     t.YCoordinate = tableReader.rf.readShort() * 1000 / tableReader.getUnitsPerEm();
/*     */ 
/*     */ 
/*     */     
/* 156 */     return t;
/*     */   }
/*     */   
/*     */   public static List<OtfMarkRecord> readMarkArray(OpenTypeFontTableReader tableReader, int location) throws IOException {
/* 160 */     tableReader.rf.seek(location);
/* 161 */     int markCount = tableReader.rf.readUnsignedShort();
/* 162 */     int[] classes = new int[markCount];
/* 163 */     int[] locations = new int[markCount];
/* 164 */     for (int k = 0; k < markCount; k++) {
/* 165 */       classes[k] = tableReader.rf.readUnsignedShort();
/* 166 */       int offset = tableReader.rf.readUnsignedShort();
/* 167 */       locations[k] = location + offset;
/*     */     } 
/* 169 */     List<OtfMarkRecord> marks = new ArrayList<>();
/* 170 */     for (int i = 0; i < markCount; i++) {
/* 171 */       OtfMarkRecord rec = new OtfMarkRecord();
/* 172 */       rec.markClass = classes[i];
/* 173 */       rec.anchor = readGposAnchor(tableReader, locations[i]);
/* 174 */       marks.add(rec);
/*     */     } 
/* 176 */     return marks;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SubstLookupRecord[] readSubstLookupRecords(RandomAccessFileOrArray rf, int substCount) throws IOException {
/* 181 */     SubstLookupRecord[] substLookUpRecords = new SubstLookupRecord[substCount];
/* 182 */     for (int i = 0; i < substCount; i++) {
/* 183 */       SubstLookupRecord slr = new SubstLookupRecord();
/* 184 */       slr.sequenceIndex = rf.readUnsignedShort();
/* 185 */       slr.lookupListIndex = rf.readUnsignedShort();
/* 186 */       substLookUpRecords[i] = slr;
/*     */     } 
/* 188 */     return substLookUpRecords;
/*     */   }
/*     */ 
/*     */   
/*     */   public static PosLookupRecord[] readPosLookupRecords(RandomAccessFileOrArray rf, int recordCount) throws IOException {
/* 193 */     PosLookupRecord[] posLookUpRecords = new PosLookupRecord[recordCount];
/* 194 */     for (int i = 0; i < recordCount; i++) {
/* 195 */       PosLookupRecord lookupRecord = new PosLookupRecord();
/* 196 */       lookupRecord.sequenceIndex = rf.readUnsignedShort();
/* 197 */       lookupRecord.lookupListIndex = rf.readUnsignedShort();
/* 198 */       posLookUpRecords[i] = lookupRecord;
/*     */     } 
/* 200 */     return posLookUpRecords;
/*     */   }
/*     */   
/*     */   public static GposAnchor[] readAnchorArray(OpenTypeFontTableReader tableReader, int[] locations, int left, int right) throws IOException {
/* 204 */     GposAnchor[] anchors = new GposAnchor[right - left];
/* 205 */     for (int i = left; i < right; i++) {
/* 206 */       anchors[i - left] = readGposAnchor(tableReader, locations[i]);
/*     */     }
/* 208 */     return anchors;
/*     */   }
/*     */   
/*     */   public static List<GposAnchor[]> readBaseArray(OpenTypeFontTableReader tableReader, int classCount, int location) throws IOException {
/* 212 */     List<GposAnchor[]> baseArray = (List)new ArrayList<>();
/* 213 */     tableReader.rf.seek(location);
/* 214 */     int baseCount = tableReader.rf.readUnsignedShort();
/* 215 */     int[] anchorLocations = readUShortArray(tableReader.rf, baseCount * classCount, location);
/* 216 */     int idx = 0;
/* 217 */     for (int k = 0; k < baseCount; k++) {
/* 218 */       baseArray.add(readAnchorArray(tableReader, anchorLocations, idx, idx + classCount));
/* 219 */       idx += classCount;
/*     */     } 
/* 221 */     return baseArray;
/*     */   }
/*     */   
/*     */   public static List<List<GposAnchor[]>> readLigatureArray(OpenTypeFontTableReader tableReader, int classCount, int location) throws IOException {
/* 225 */     List<List<GposAnchor[]>> ligatureArray = new ArrayList<>();
/* 226 */     tableReader.rf.seek(location);
/* 227 */     int ligatureCount = tableReader.rf.readUnsignedShort();
/* 228 */     int[] ligatureAttachLocations = readUShortArray(tableReader.rf, ligatureCount, location);
/* 229 */     for (int liga = 0; liga < ligatureCount; liga++) {
/* 230 */       int ligatureAttachLocation = ligatureAttachLocations[liga];
/* 231 */       List<GposAnchor[]> ligatureAttach = (List)new ArrayList<>();
/* 232 */       tableReader.rf.seek(ligatureAttachLocation);
/* 233 */       int componentCount = tableReader.rf.readUnsignedShort();
/* 234 */       int[] componentRecordsLocation = readUShortArray(tableReader.rf, classCount * componentCount, ligatureAttachLocation);
/* 235 */       int idx = 0;
/* 236 */       for (int k = 0; k < componentCount; k++) {
/* 237 */         ligatureAttach.add(readAnchorArray(tableReader, componentRecordsLocation, idx, idx + classCount));
/* 238 */         idx += classCount;
/*     */       } 
/* 240 */       ligatureArray.add(ligatureAttach);
/*     */     } 
/* 242 */     return ligatureArray;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/OtfReadCommon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */