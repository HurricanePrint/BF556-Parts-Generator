/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
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
/*     */ public abstract class OpenTypeFontTableReader
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4826484598227913292L;
/*     */   protected final RandomAccessFileOrArray rf;
/*     */   protected final int tableLocation;
/*     */   protected List<OpenTableLookup> lookupList;
/*     */   protected OpenTypeScript scriptsType;
/*     */   protected OpenTypeFeature featuresType;
/*     */   private final Map<Integer, Glyph> indexGlyphMap;
/*     */   private final OpenTypeGdefTableReader gdef;
/*     */   private final int unitsPerEm;
/*     */   
/*     */   protected OpenTypeFontTableReader(RandomAccessFileOrArray rf, int tableLocation, OpenTypeGdefTableReader gdef, Map<Integer, Glyph> indexGlyphMap, int unitsPerEm) throws IOException {
/*  76 */     this.rf = rf;
/*  77 */     this.tableLocation = tableLocation;
/*  78 */     this.indexGlyphMap = indexGlyphMap;
/*  79 */     this.gdef = gdef;
/*  80 */     this.unitsPerEm = unitsPerEm;
/*     */   }
/*     */   
/*     */   public Glyph getGlyph(int index) {
/*  84 */     return this.indexGlyphMap.get(Integer.valueOf(index));
/*     */   }
/*     */   
/*     */   public OpenTableLookup getLookupTable(int idx) {
/*  88 */     if (idx < 0 || idx >= this.lookupList.size()) {
/*  89 */       return null;
/*     */     }
/*  91 */     return this.lookupList.get(idx);
/*     */   }
/*     */   
/*     */   public List<ScriptRecord> getScriptRecords() {
/*  95 */     return this.scriptsType.getScriptRecords();
/*     */   }
/*     */   
/*     */   public List<FeatureRecord> getFeatureRecords() {
/*  99 */     return this.featuresType.getRecords();
/*     */   }
/*     */   
/*     */   public List<FeatureRecord> getFeatures(String[] scripts, String language) {
/* 103 */     LanguageRecord rec = this.scriptsType.getLanguageRecord(scripts, language);
/* 104 */     if (rec == null) {
/* 105 */       return null;
/*     */     }
/* 107 */     List<FeatureRecord> ret = new ArrayList<>();
/* 108 */     for (int f : rec.features) {
/* 109 */       ret.add(this.featuresType.getRecord(f));
/*     */     }
/* 111 */     return ret;
/*     */   }
/*     */   
/*     */   public List<FeatureRecord> getSpecificFeatures(List<FeatureRecord> features, String[] specific) {
/* 115 */     if (specific == null) {
/* 116 */       return features;
/*     */     }
/* 118 */     Set<String> hs = new HashSet<>();
/*     */     
/* 120 */     for (String s : specific) {
/* 121 */       hs.add(s);
/*     */     }
/* 123 */     List<FeatureRecord> recs = new ArrayList<>();
/* 124 */     for (FeatureRecord rec : features) {
/* 125 */       if (hs.contains(rec.tag)) {
/* 126 */         recs.add(rec);
/*     */       }
/*     */     } 
/* 129 */     return recs;
/*     */   }
/*     */   
/*     */   public FeatureRecord getRequiredFeature(String[] scripts, String language) {
/* 133 */     LanguageRecord rec = this.scriptsType.getLanguageRecord(scripts, language);
/* 134 */     if (rec == null)
/* 135 */       return null; 
/* 136 */     return this.featuresType.getRecord(rec.featureRequired);
/*     */   }
/*     */   
/*     */   public List<OpenTableLookup> getLookups(FeatureRecord[] features) {
/* 140 */     IntHashtable hash = new IntHashtable();
/* 141 */     for (FeatureRecord rec : features) {
/* 142 */       for (int idx : rec.lookups) {
/* 143 */         hash.put(idx, 1);
/*     */       }
/*     */     } 
/* 146 */     List<OpenTableLookup> ret = new ArrayList<>();
/* 147 */     for (int idx : hash.toOrderedKeys()) {
/* 148 */       ret.add(this.lookupList.get(idx));
/*     */     }
/* 150 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<OpenTableLookup> getLookups(FeatureRecord feature) {
/* 155 */     List<OpenTableLookup> ret = new ArrayList<>(feature.lookups.length);
/* 156 */     for (int idx : feature.lookups) {
/* 157 */       ret.add(this.lookupList.get(idx));
/*     */     }
/* 159 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean isSkip(int glyph, int flag) {
/* 163 */     return this.gdef.isSkip(glyph, flag);
/*     */   }
/*     */   
/*     */   public int getGlyphClass(int glyphCode) {
/* 167 */     return this.gdef.getGlyphClassTable().getOtfClass(glyphCode);
/*     */   }
/*     */   
/*     */   public int getUnitsPerEm() {
/* 171 */     return this.unitsPerEm;
/*     */   }
/*     */   
/*     */   public LanguageRecord getLanguageRecord(String otfScriptTag) {
/* 175 */     LanguageRecord languageRecord = null;
/* 176 */     if (otfScriptTag != null) {
/* 177 */       for (ScriptRecord record : getScriptRecords()) {
/* 178 */         if (otfScriptTag.equals(record.tag)) {
/* 179 */           languageRecord = record.defaultLanguage;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 184 */     return languageRecord;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract OpenTableLookup readLookupTable(int paramInt1, int paramInt2, int[] paramArrayOfint) throws IOException;
/*     */   
/*     */   protected final OtfClass readClassDefinition(int classLocation) throws IOException {
/* 191 */     return OtfClass.create(this.rf, classLocation);
/*     */   }
/*     */   
/*     */   protected final int[] readUShortArray(int size, int location) throws IOException {
/* 195 */     return OtfReadCommon.readUShortArray(this.rf, size, location);
/*     */   }
/*     */   
/*     */   protected final int[] readUShortArray(int size) throws IOException {
/* 199 */     return OtfReadCommon.readUShortArray(this.rf, size);
/*     */   }
/*     */   
/*     */   protected void readCoverages(int[] locations, List<Set<Integer>> coverage) throws IOException {
/* 203 */     OtfReadCommon.readCoverages(this.rf, locations, coverage);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final List<Integer> readCoverageFormat(int coverageLocation) throws IOException {
/* 208 */     return OtfReadCommon.readCoverageFormat(this.rf, coverageLocation);
/*     */   }
/*     */   
/*     */   protected SubstLookupRecord[] readSubstLookupRecords(int substCount) throws IOException {
/* 212 */     return OtfReadCommon.readSubstLookupRecords(this.rf, substCount);
/*     */   }
/*     */   
/*     */   protected PosLookupRecord[] readPosLookupRecords(int substCount) throws IOException {
/* 216 */     return OtfReadCommon.readPosLookupRecords(this.rf, substCount);
/*     */   }
/*     */   
/*     */   protected TagAndLocation[] readTagAndLocations(int baseLocation) throws IOException {
/* 220 */     int count = this.rf.readUnsignedShort();
/* 221 */     TagAndLocation[] tagslLocs = new TagAndLocation[count];
/* 222 */     for (int k = 0; k < count; k++) {
/* 223 */       TagAndLocation tl = new TagAndLocation();
/* 224 */       tl.tag = this.rf.readString(4, "utf-8");
/* 225 */       tl.location = this.rf.readUnsignedShort() + baseLocation;
/* 226 */       tagslLocs[k] = tl;
/*     */     } 
/* 228 */     return tagslLocs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void startReadingTable() throws FontReadingException {
/*     */     try {
/* 239 */       this.rf.seek(this.tableLocation);
/*     */ 
/*     */       
/* 242 */       this.rf.readInt();
/* 243 */       int scriptListOffset = this.rf.readUnsignedShort();
/* 244 */       int featureListOffset = this.rf.readUnsignedShort();
/* 245 */       int lookupListOffset = this.rf.readUnsignedShort();
/*     */       
/* 247 */       this.scriptsType = new OpenTypeScript(this, this.tableLocation + scriptListOffset);
/*     */       
/* 249 */       this.featuresType = new OpenTypeFeature(this, this.tableLocation + featureListOffset);
/*     */       
/* 251 */       readLookupListTable(this.tableLocation + lookupListOffset);
/* 252 */     } catch (IOException e) {
/* 253 */       throw new FontReadingException("Error reading font file", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readLookupListTable(int lookupListTableLocation) throws IOException {
/* 258 */     this.lookupList = new ArrayList<>();
/* 259 */     this.rf.seek(lookupListTableLocation);
/* 260 */     int lookupCount = this.rf.readUnsignedShort();
/* 261 */     int[] lookupTableLocations = readUShortArray(lookupCount, lookupListTableLocation);
/*     */     
/* 263 */     for (int lookupLocation : lookupTableLocations) {
/*     */ 
/*     */       
/* 266 */       if (lookupLocation != 0)
/*     */       {
/*     */         
/* 269 */         readLookupTable(lookupLocation); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readLookupTable(int lookupTableLocation) throws IOException {
/* 274 */     this.rf.seek(lookupTableLocation);
/* 275 */     int lookupType = this.rf.readUnsignedShort();
/* 276 */     int lookupFlag = this.rf.readUnsignedShort();
/* 277 */     int subTableCount = this.rf.readUnsignedShort();
/* 278 */     int[] subTableLocations = readUShortArray(subTableCount, lookupTableLocation);
/* 279 */     this.lookupList.add(readLookupTable(lookupType, lookupFlag, subTableLocations));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/OpenTypeFontTableReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */