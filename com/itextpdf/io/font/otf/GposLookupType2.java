/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ public class GposLookupType2
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = 4781829862270887603L;
/*  62 */   private List<OpenTableLookup> listRules = new ArrayList<>();
/*     */   
/*     */   public GposLookupType2(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  65 */     super(openReader, lookupFlag, subTableLocations);
/*  66 */     readSubTables();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean transformOne(GlyphLine line) {
/*  71 */     if (line.idx >= line.end)
/*  72 */       return false; 
/*  73 */     if (this.openReader.isSkip(line.get(line.idx).getCode(), this.lookupFlag)) {
/*  74 */       line.idx++;
/*  75 */       return false;
/*     */     } 
/*  77 */     for (OpenTableLookup lookup : this.listRules) {
/*  78 */       if (lookup.transformOne(line))
/*  79 */         return true; 
/*     */     } 
/*  81 */     line.idx++;
/*  82 */     return false;
/*     */   }
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/*     */     PairPosAdjustmentFormat1 format1;
/*     */     PairPosAdjustmentFormat2 format2;
/*  87 */     this.openReader.rf.seek(subTableLocation);
/*  88 */     int gposFormat = this.openReader.rf.readShort();
/*  89 */     switch (gposFormat) {
/*     */       case 1:
/*  91 */         format1 = new PairPosAdjustmentFormat1(this.openReader, this.lookupFlag, subTableLocation);
/*  92 */         this.listRules.add(format1);
/*     */         break;
/*     */       
/*     */       case 2:
/*  96 */         format2 = new PairPosAdjustmentFormat2(this.openReader, this.lookupFlag, subTableLocation);
/*  97 */         this.listRules.add(format2);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class PairPosAdjustmentFormat1
/*     */     extends OpenTableLookup
/*     */   {
/*     */     private static final long serialVersionUID = -5556528810086852702L;
/* 107 */     private Map<Integer, Map<Integer, GposLookupType2.PairValueFormat>> gposMap = new HashMap<>();
/*     */     
/*     */     public PairPosAdjustmentFormat1(OpenTypeFontTableReader openReader, int lookupFlag, int subtableLocation) throws IOException {
/* 110 */       super(openReader, lookupFlag, null);
/* 111 */       readFormat(subtableLocation);
/*     */     }
/*     */     
/*     */     public boolean transformOne(GlyphLine line) {
/* 115 */       if (line.idx >= line.end || line.idx < line.start)
/* 116 */         return false; 
/* 117 */       boolean changed = false;
/* 118 */       Glyph g1 = line.get(line.idx);
/* 119 */       Map<Integer, GposLookupType2.PairValueFormat> m = this.gposMap.get(Integer.valueOf(g1.getCode()));
/* 120 */       if (m != null) {
/* 121 */         OpenTableLookup.GlyphIndexer gi = new OpenTableLookup.GlyphIndexer();
/* 122 */         gi.line = line;
/* 123 */         gi.idx = line.idx;
/* 124 */         gi.nextGlyph(this.openReader, this.lookupFlag);
/* 125 */         if (gi.glyph != null) {
/* 126 */           GposLookupType2.PairValueFormat pv = m.get(Integer.valueOf(gi.glyph.getCode()));
/* 127 */           if (pv != null) {
/* 128 */             Glyph g2 = gi.glyph;
/* 129 */             line.set(line.idx, new Glyph(g1, 0, 0, pv.first.XAdvance, pv.first.YAdvance, 0));
/* 130 */             line.set(gi.idx, new Glyph(g2, 0, 0, pv.second.XAdvance, pv.second.YAdvance, 0));
/* 131 */             line.idx = gi.idx;
/* 132 */             changed = true;
/*     */           } 
/*     */         } 
/*     */       } 
/* 136 */       return changed;
/*     */     }
/*     */     
/*     */     protected void readFormat(int subTableLocation) throws IOException {
/* 140 */       int coverage = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 141 */       int valueFormat1 = this.openReader.rf.readUnsignedShort();
/* 142 */       int valueFormat2 = this.openReader.rf.readUnsignedShort();
/* 143 */       int pairSetCount = this.openReader.rf.readUnsignedShort();
/* 144 */       int[] locationRule = this.openReader.readUShortArray(pairSetCount, subTableLocation);
/* 145 */       List<Integer> coverageList = this.openReader.readCoverageFormat(coverage);
/* 146 */       for (int k = 0; k < pairSetCount; k++) {
/* 147 */         this.openReader.rf.seek(locationRule[k]);
/* 148 */         Map<Integer, GposLookupType2.PairValueFormat> pairs = new HashMap<>();
/* 149 */         this.gposMap.put(coverageList.get(k), pairs);
/* 150 */         int pairValueCount = this.openReader.rf.readUnsignedShort();
/* 151 */         for (int j = 0; j < pairValueCount; j++) {
/* 152 */           int glyph2 = this.openReader.rf.readUnsignedShort();
/* 153 */           GposLookupType2.PairValueFormat pair = new GposLookupType2.PairValueFormat();
/* 154 */           pair.first = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat1);
/* 155 */           pair.second = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat2);
/* 156 */           pairs.put(Integer.valueOf(glyph2), pair);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readSubTable(int subTableLocation) throws IOException {}
/*     */   }
/*     */   
/*     */   private static class PairPosAdjustmentFormat2
/*     */     extends OpenTableLookup
/*     */   {
/*     */     private static final long serialVersionUID = 3056620748845862393L;
/*     */     private OtfClass classDef1;
/*     */     private OtfClass classDef2;
/*     */     private HashSet<Integer> coverageSet;
/* 172 */     private Map<Integer, GposLookupType2.PairValueFormat[]> posSubs = (Map)new HashMap<>();
/*     */     
/*     */     public PairPosAdjustmentFormat2(OpenTypeFontTableReader openReader, int lookupFlag, int subtableLocation) throws IOException {
/* 175 */       super(openReader, lookupFlag, null);
/* 176 */       readFormat(subtableLocation);
/*     */     }
/*     */     
/*     */     public boolean transformOne(GlyphLine line) {
/* 180 */       if (line.idx >= line.end || line.idx < line.start)
/* 181 */         return false; 
/* 182 */       Glyph g1 = line.get(line.idx);
/* 183 */       if (!this.coverageSet.contains(Integer.valueOf(g1.getCode())))
/* 184 */         return false; 
/* 185 */       int c1 = this.classDef1.getOtfClass(g1.getCode());
/* 186 */       GposLookupType2.PairValueFormat[] pvs = this.posSubs.get(Integer.valueOf(c1));
/* 187 */       if (pvs == null)
/* 188 */         return false; 
/* 189 */       OpenTableLookup.GlyphIndexer gi = new OpenTableLookup.GlyphIndexer();
/* 190 */       gi.line = line;
/* 191 */       gi.idx = line.idx;
/* 192 */       gi.nextGlyph(this.openReader, this.lookupFlag);
/* 193 */       if (gi.glyph == null)
/* 194 */         return false; 
/* 195 */       Glyph g2 = gi.glyph;
/* 196 */       int c2 = this.classDef2.getOtfClass(g2.getCode());
/* 197 */       if (c2 >= pvs.length)
/* 198 */         return false; 
/* 199 */       GposLookupType2.PairValueFormat pv = pvs[c2];
/* 200 */       line.set(line.idx, new Glyph(g1, 0, 0, pv.first.XAdvance, pv.first.YAdvance, 0));
/* 201 */       line.set(gi.idx, new Glyph(g2, 0, 0, pv.second.XAdvance, pv.second.YAdvance, 0));
/* 202 */       line.idx = gi.idx;
/* 203 */       return true;
/*     */     }
/*     */     
/*     */     protected void readFormat(int subTableLocation) throws IOException {
/* 207 */       int coverage = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 208 */       int valueFormat1 = this.openReader.rf.readUnsignedShort();
/* 209 */       int valueFormat2 = this.openReader.rf.readUnsignedShort();
/* 210 */       int locationClass1 = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 211 */       int locationClass2 = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 212 */       int class1Count = this.openReader.rf.readUnsignedShort();
/* 213 */       int class2Count = this.openReader.rf.readUnsignedShort();
/*     */       
/* 215 */       for (int k = 0; k < class1Count; k++) {
/* 216 */         GposLookupType2.PairValueFormat[] pairs = new GposLookupType2.PairValueFormat[class2Count];
/* 217 */         this.posSubs.put(Integer.valueOf(k), pairs);
/* 218 */         for (int j = 0; j < class2Count; j++) {
/* 219 */           GposLookupType2.PairValueFormat pair = new GposLookupType2.PairValueFormat();
/* 220 */           pair.first = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat1);
/* 221 */           pair.second = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat2);
/* 222 */           pairs[j] = pair;
/*     */         } 
/*     */       } 
/*     */       
/* 226 */       this.coverageSet = new HashSet<>(this.openReader.readCoverageFormat(coverage));
/* 227 */       this.classDef1 = this.openReader.readClassDefinition(locationClass1);
/* 228 */       this.classDef2 = this.openReader.readClassDefinition(locationClass2);
/*     */     }
/*     */     
/*     */     protected void readSubTable(int subTableLocation) throws IOException {}
/*     */   }
/*     */   
/*     */   private static class PairValueFormat implements Serializable {
/*     */     private static final long serialVersionUID = -6442882035589529495L;
/*     */     public GposValueRecord first;
/*     */     public GposValueRecord second;
/*     */     
/*     */     private PairValueFormat() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GposLookupType2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */