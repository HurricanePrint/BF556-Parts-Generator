/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format1;
/*     */ import com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format2;
/*     */ import com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format3;
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
/*     */ public class GsubLookupType6
/*     */   extends GsubLookupType5
/*     */ {
/*     */   private static final long serialVersionUID = 6205375104387477124L;
/*     */   
/*     */   protected GsubLookupType6(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  64 */     super(openReader, lookupFlag, subTableLocations);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTableFormat1(int subTableLocation) throws IOException {
/*  69 */     Map<Integer, List<ContextualSubstRule>> substMap = new HashMap<>();
/*     */     
/*  71 */     int coverageOffset = this.openReader.rf.readUnsignedShort();
/*  72 */     int chainSubRuleSetCount = this.openReader.rf.readUnsignedShort();
/*  73 */     int[] chainSubRuleSetOffsets = this.openReader.readUShortArray(chainSubRuleSetCount, subTableLocation);
/*     */     
/*  75 */     List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverageOffset);
/*  76 */     for (int i = 0; i < chainSubRuleSetCount; i++) {
/*  77 */       this.openReader.rf.seek(chainSubRuleSetOffsets[i]);
/*  78 */       int chainSubRuleCount = this.openReader.rf.readUnsignedShort();
/*  79 */       int[] chainSubRuleOffsets = this.openReader.readUShortArray(chainSubRuleCount, chainSubRuleSetOffsets[i]);
/*     */       
/*  81 */       List<ContextualSubstRule> chainSubRuleSet = new ArrayList<>(chainSubRuleCount);
/*  82 */       for (int j = 0; j < chainSubRuleCount; j++) {
/*  83 */         this.openReader.rf.seek(chainSubRuleOffsets[j]);
/*  84 */         int backtrackGlyphCount = this.openReader.rf.readUnsignedShort();
/*  85 */         int[] backtrackGlyphIds = this.openReader.readUShortArray(backtrackGlyphCount);
/*  86 */         int inputGlyphCount = this.openReader.rf.readUnsignedShort();
/*  87 */         int[] inputGlyphIds = this.openReader.readUShortArray(inputGlyphCount - 1);
/*  88 */         int lookAheadGlyphCount = this.openReader.rf.readUnsignedShort();
/*  89 */         int[] lookAheadGlyphIds = this.openReader.readUShortArray(lookAheadGlyphCount);
/*  90 */         int substCount = this.openReader.rf.readUnsignedShort();
/*  91 */         SubstLookupRecord[] substLookupRecords = this.openReader.readSubstLookupRecords(substCount);
/*     */         
/*  93 */         chainSubRuleSet.add(new SubTableLookup6Format1.SubstRuleFormat1(backtrackGlyphIds, inputGlyphIds, lookAheadGlyphIds, substLookupRecords));
/*     */       } 
/*  95 */       substMap.put(coverageGlyphIds.get(i), chainSubRuleSet);
/*     */     } 
/*     */     
/*  98 */     this.subTables.add(new SubTableLookup6Format1(this.openReader, this.lookupFlag, substMap));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTableFormat2(int subTableLocation) throws IOException {
/* 103 */     int coverageOffset = this.openReader.rf.readUnsignedShort();
/* 104 */     int backtrackClassDefOffset = this.openReader.rf.readUnsignedShort();
/* 105 */     int inputClassDefOffset = this.openReader.rf.readUnsignedShort();
/* 106 */     int lookaheadClassDefOffset = this.openReader.rf.readUnsignedShort();
/* 107 */     int chainSubClassSetCount = this.openReader.rf.readUnsignedShort();
/* 108 */     int[] chainSubClassSetOffsets = this.openReader.readUShortArray(chainSubClassSetCount, subTableLocation);
/*     */     
/* 110 */     Set<Integer> coverageGlyphIds = new HashSet<>(this.openReader.readCoverageFormat(subTableLocation + coverageOffset));
/* 111 */     OtfClass backtrackClassDefinition = this.openReader.readClassDefinition(subTableLocation + backtrackClassDefOffset);
/* 112 */     OtfClass inputClassDefinition = this.openReader.readClassDefinition(subTableLocation + inputClassDefOffset);
/* 113 */     OtfClass lookaheadClassDefinition = this.openReader.readClassDefinition(subTableLocation + lookaheadClassDefOffset);
/*     */     
/* 115 */     SubTableLookup6Format2 t = new SubTableLookup6Format2(this.openReader, this.lookupFlag, coverageGlyphIds, backtrackClassDefinition, inputClassDefinition, lookaheadClassDefinition);
/*     */ 
/*     */     
/* 118 */     List<List<ContextualSubstRule>> subClassSets = new ArrayList<>(chainSubClassSetCount);
/* 119 */     for (int i = 0; i < chainSubClassSetCount; i++) {
/* 120 */       List<ContextualSubstRule> subClassSet = null;
/* 121 */       if (chainSubClassSetOffsets[i] != 0) {
/* 122 */         this.openReader.rf.seek(chainSubClassSetOffsets[i]);
/* 123 */         int chainSubClassRuleCount = this.openReader.rf.readUnsignedShort();
/* 124 */         int[] chainSubClassRuleOffsets = this.openReader.readUShortArray(chainSubClassRuleCount, chainSubClassSetOffsets[i]);
/*     */         
/* 126 */         subClassSet = new ArrayList<>(chainSubClassRuleCount);
/* 127 */         for (int j = 0; j < chainSubClassRuleCount; j++) {
/*     */           
/* 129 */           this.openReader.rf.seek(chainSubClassRuleOffsets[j]);
/*     */           
/* 131 */           int backtrackClassCount = this.openReader.rf.readUnsignedShort();
/* 132 */           int[] backtrackClassIds = this.openReader.readUShortArray(backtrackClassCount);
/* 133 */           int inputClassCount = this.openReader.rf.readUnsignedShort();
/* 134 */           int[] inputClassIds = this.openReader.readUShortArray(inputClassCount - 1);
/* 135 */           int lookAheadClassCount = this.openReader.rf.readUnsignedShort();
/* 136 */           int[] lookAheadClassIds = this.openReader.readUShortArray(lookAheadClassCount);
/* 137 */           int substCount = this.openReader.rf.readUnsignedShort();
/* 138 */           SubstLookupRecord[] substLookupRecords = this.openReader.readSubstLookupRecords(substCount);
/*     */           
/* 140 */           SubTableLookup6Format2.SubstRuleFormat2 rule = new SubTableLookup6Format2.SubstRuleFormat2(t, backtrackClassIds, inputClassIds, lookAheadClassIds, substLookupRecords);
/* 141 */           subClassSet.add(rule);
/*     */         } 
/*     */       } 
/* 144 */       subClassSets.add(subClassSet);
/*     */     } 
/*     */     
/* 147 */     t.setSubClassSets(subClassSets);
/* 148 */     this.subTables.add(t);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTableFormat3(int subTableLocation) throws IOException {
/* 153 */     int backtrackGlyphCount = this.openReader.rf.readUnsignedShort();
/* 154 */     int[] backtrackCoverageOffsets = this.openReader.readUShortArray(backtrackGlyphCount, subTableLocation);
/* 155 */     int inputGlyphCount = this.openReader.rf.readUnsignedShort();
/* 156 */     int[] inputCoverageOffsets = this.openReader.readUShortArray(inputGlyphCount, subTableLocation);
/* 157 */     int lookaheadGlyphCount = this.openReader.rf.readUnsignedShort();
/* 158 */     int[] lookaheadCoverageOffsets = this.openReader.readUShortArray(lookaheadGlyphCount, subTableLocation);
/* 159 */     int substCount = this.openReader.rf.readUnsignedShort();
/* 160 */     SubstLookupRecord[] substLookupRecords = this.openReader.readSubstLookupRecords(substCount);
/*     */     
/* 162 */     List<Set<Integer>> backtrackCoverages = new ArrayList<>(backtrackGlyphCount);
/* 163 */     this.openReader.readCoverages(backtrackCoverageOffsets, backtrackCoverages);
/*     */     
/* 165 */     List<Set<Integer>> inputCoverages = new ArrayList<>(inputGlyphCount);
/* 166 */     this.openReader.readCoverages(inputCoverageOffsets, inputCoverages);
/*     */     
/* 168 */     List<Set<Integer>> lookaheadCoverages = new ArrayList<>(lookaheadGlyphCount);
/* 169 */     this.openReader.readCoverages(lookaheadCoverageOffsets, lookaheadCoverages);
/*     */     
/* 171 */     SubTableLookup6Format3.SubstRuleFormat3 rule = new SubTableLookup6Format3.SubstRuleFormat3(backtrackCoverages, inputCoverages, lookaheadCoverages, substLookupRecords);
/*     */     
/* 173 */     this.subTables.add(new SubTableLookup6Format3(this.openReader, this.lookupFlag, rule));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GsubLookupType6.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */