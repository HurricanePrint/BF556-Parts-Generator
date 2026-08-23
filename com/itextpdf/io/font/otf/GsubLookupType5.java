/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format1;
/*     */ import com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format2;
/*     */ import com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format3;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
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
/*     */ public class GsubLookupType5
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = 1499367592878919320L;
/*     */   protected List<ContextualSubTable> subTables;
/*     */   
/*     */   protected GsubLookupType5(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  66 */     super(openReader, lookupFlag, subTableLocations);
/*  67 */     this.subTables = new ArrayList<>();
/*  68 */     readSubTables();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean transformOne(GlyphLine line) {
/*  73 */     boolean changed = false;
/*  74 */     int oldLineStart = line.start;
/*  75 */     int oldLineEnd = line.end;
/*  76 */     int initialLineIndex = line.idx;
/*     */     
/*  78 */     for (ContextualSubTable subTable : this.subTables) {
/*  79 */       ContextualSubstRule contextRule = subTable.getMatchingContextRule(line);
/*  80 */       if (contextRule == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  84 */       int lineEndBeforeSubstitutions = line.end;
/*  85 */       SubstLookupRecord[] substLookupRecords = contextRule.getSubstLookupRecords();
/*  86 */       OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
/*  87 */       gidx.line = line;
/*  88 */       for (SubstLookupRecord substRecord : substLookupRecords) {
/*     */ 
/*     */         
/*  91 */         gidx.idx = initialLineIndex;
/*  92 */         for (int i = 0; i < substRecord.sequenceIndex; i++) {
/*  93 */           gidx.nextGlyph(this.openReader, this.lookupFlag);
/*     */         }
/*     */         
/*  96 */         line.idx = gidx.idx;
/*  97 */         OpenTableLookup lookupTable = this.openReader.getLookupTable(substRecord.lookupListIndex);
/*  98 */         changed = (lookupTable.transformOne(line) || changed);
/*     */       } 
/*     */       
/* 101 */       line.idx = line.end;
/* 102 */       line.start = oldLineStart;
/* 103 */       int lenDelta = lineEndBeforeSubstitutions - line.end;
/* 104 */       line.end = oldLineEnd - lenDelta;
/* 105 */       return changed;
/*     */     } 
/*     */     
/* 108 */     line.idx++;
/* 109 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/* 114 */     this.openReader.rf.seek(subTableLocation);
/* 115 */     int substFormat = this.openReader.rf.readShort();
/* 116 */     if (substFormat == 1) {
/* 117 */       readSubTableFormat1(subTableLocation);
/* 118 */     } else if (substFormat == 2) {
/* 119 */       readSubTableFormat2(subTableLocation);
/* 120 */     } else if (substFormat == 3) {
/* 121 */       readSubTableFormat3(subTableLocation);
/*     */     } else {
/* 123 */       throw new IllegalArgumentException("Bad substFormat: " + substFormat);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readSubTableFormat1(int subTableLocation) throws IOException {
/* 128 */     Map<Integer, List<ContextualSubstRule>> substMap = new HashMap<>();
/*     */     
/* 130 */     int coverageOffset = this.openReader.rf.readUnsignedShort();
/* 131 */     int subRuleSetCount = this.openReader.rf.readUnsignedShort();
/* 132 */     int[] subRuleSetOffsets = this.openReader.readUShortArray(subRuleSetCount, subTableLocation);
/*     */     
/* 134 */     List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverageOffset);
/* 135 */     for (int i = 0; i < subRuleSetCount; i++) {
/* 136 */       this.openReader.rf.seek(subRuleSetOffsets[i]);
/* 137 */       int subRuleCount = this.openReader.rf.readUnsignedShort();
/* 138 */       int[] subRuleOffsets = this.openReader.readUShortArray(subRuleCount, subRuleSetOffsets[i]);
/*     */       
/* 140 */       List<ContextualSubstRule> subRuleSet = new ArrayList<>(subRuleCount);
/* 141 */       for (int j = 0; j < subRuleCount; j++) {
/* 142 */         this.openReader.rf.seek(subRuleOffsets[j]);
/* 143 */         int glyphCount = this.openReader.rf.readUnsignedShort();
/* 144 */         int substCount = this.openReader.rf.readUnsignedShort();
/* 145 */         int[] inputGlyphIds = this.openReader.readUShortArray(glyphCount - 1);
/* 146 */         SubstLookupRecord[] substLookupRecords = this.openReader.readSubstLookupRecords(substCount);
/*     */         
/* 148 */         subRuleSet.add(new SubTableLookup5Format1.SubstRuleFormat1(inputGlyphIds, substLookupRecords));
/*     */       } 
/* 150 */       substMap.put(coverageGlyphIds.get(i), subRuleSet);
/*     */     } 
/*     */     
/* 153 */     this.subTables.add(new SubTableLookup5Format1(this.openReader, this.lookupFlag, substMap));
/*     */   }
/*     */   
/*     */   protected void readSubTableFormat2(int subTableLocation) throws IOException {
/* 157 */     int coverageOffset = this.openReader.rf.readUnsignedShort();
/* 158 */     int classDefOffset = this.openReader.rf.readUnsignedShort();
/* 159 */     int subClassSetCount = this.openReader.rf.readUnsignedShort();
/* 160 */     int[] subClassSetOffsets = this.openReader.readUShortArray(subClassSetCount, subTableLocation);
/*     */     
/* 162 */     Set<Integer> coverageGlyphIds = new HashSet<>(this.openReader.readCoverageFormat(subTableLocation + coverageOffset));
/* 163 */     OtfClass classDefinition = this.openReader.readClassDefinition(subTableLocation + classDefOffset);
/*     */     
/* 165 */     SubTableLookup5Format2 t = new SubTableLookup5Format2(this.openReader, this.lookupFlag, coverageGlyphIds, classDefinition);
/*     */     
/* 167 */     List<List<ContextualSubstRule>> subClassSets = new ArrayList<>(subClassSetCount);
/* 168 */     for (int i = 0; i < subClassSetCount; i++) {
/* 169 */       List<ContextualSubstRule> subClassSet = null;
/* 170 */       if (subClassSetOffsets[i] != 0) {
/* 171 */         this.openReader.rf.seek(subClassSetOffsets[i]);
/* 172 */         int subClassRuleCount = this.openReader.rf.readUnsignedShort();
/* 173 */         int[] subClassRuleOffsets = this.openReader.readUShortArray(subClassRuleCount, subClassSetOffsets[i]);
/*     */         
/* 175 */         subClassSet = new ArrayList<>(subClassRuleCount);
/* 176 */         for (int j = 0; j < subClassRuleCount; j++) {
/*     */           
/* 178 */           this.openReader.rf.seek(subClassRuleOffsets[j]);
/*     */           
/* 180 */           int glyphCount = this.openReader.rf.readUnsignedShort();
/* 181 */           int substCount = this.openReader.rf.readUnsignedShort();
/* 182 */           int[] inputClassIds = this.openReader.readUShortArray(glyphCount - 1);
/* 183 */           SubstLookupRecord[] substLookupRecords = this.openReader.readSubstLookupRecords(substCount);
/*     */           
/* 185 */           SubTableLookup5Format2.SubstRuleFormat2 substRuleFormat2 = new SubTableLookup5Format2.SubstRuleFormat2(t, inputClassIds, substLookupRecords);
/* 186 */           subClassSet.add(substRuleFormat2);
/*     */         } 
/*     */       } 
/* 189 */       subClassSets.add(subClassSet);
/*     */     } 
/*     */     
/* 192 */     t.setSubClassSets(subClassSets);
/* 193 */     this.subTables.add(t);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTableFormat3(int subTableLocation) throws IOException {
/* 198 */     int glyphCount = this.openReader.rf.readUnsignedShort();
/* 199 */     int substCount = this.openReader.rf.readUnsignedShort();
/* 200 */     int[] coverageOffsets = this.openReader.readUShortArray(glyphCount, subTableLocation);
/* 201 */     SubstLookupRecord[] substLookupRecords = this.openReader.readSubstLookupRecords(substCount);
/*     */     
/* 203 */     List<Set<Integer>> coverages = new ArrayList<>(glyphCount);
/* 204 */     this.openReader.readCoverages(coverageOffsets, coverages);
/*     */     
/* 206 */     SubTableLookup5Format3.SubstRuleFormat3 rule = new SubTableLookup5Format3.SubstRuleFormat3(coverages, substLookupRecords);
/* 207 */     this.subTables.add(new SubTableLookup5Format3(this.openReader, this.lookupFlag, rule));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GsubLookupType5.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */