/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.lookuptype7.PosTableLookup7Format2;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class GposLookupType7
/*     */   extends OpenTableLookup
/*     */ {
/*  62 */   private static final Logger LOGGER = LoggerFactory.getLogger(GposLookupType7.class);
/*     */   
/*     */   private static final long serialVersionUID = 4596977183462695970L;
/*     */   
/*     */   private List<ContextualPositionTable> subTables;
/*     */ 
/*     */   
/*     */   public GposLookupType7(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  70 */     super(openReader, lookupFlag, subTableLocations);
/*  71 */     this.subTables = new ArrayList<>();
/*  72 */     readSubTables();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean transformOne(GlyphLine line) {
/*  77 */     boolean changed = false;
/*  78 */     int oldLineStart = line.start;
/*  79 */     int oldLineEnd = line.end;
/*  80 */     int initialLineIndex = line.idx;
/*     */     
/*  82 */     for (ContextualPositionTable subTable : this.subTables) {
/*  83 */       ContextualPositionRule contextRule = subTable.getMatchingContextRule(line);
/*  84 */       if (contextRule == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  88 */       int lineEndBeforeTransformations = line.end;
/*  89 */       PosLookupRecord[] posLookupRecords = contextRule.getPosLookupRecords();
/*  90 */       OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
/*  91 */       gidx.line = line;
/*  92 */       for (PosLookupRecord posRecord : posLookupRecords) {
/*     */ 
/*     */         
/*  95 */         gidx.idx = initialLineIndex;
/*  96 */         for (int i = 0; i < posRecord.sequenceIndex; i++) {
/*  97 */           gidx.nextGlyph(this.openReader, this.lookupFlag);
/*     */         }
/*     */         
/* 100 */         line.idx = gidx.idx;
/* 101 */         OpenTableLookup lookupTable = this.openReader.getLookupTable(posRecord.lookupListIndex);
/* 102 */         changed = (lookupTable.transformOne(line) || changed);
/*     */       } 
/*     */       
/* 105 */       line.idx = line.end;
/* 106 */       line.start = oldLineStart;
/* 107 */       int lenDelta = lineEndBeforeTransformations - line.end;
/* 108 */       line.end = oldLineEnd - lenDelta;
/* 109 */       return changed;
/*     */     } 
/*     */     
/* 112 */     line.idx++;
/* 113 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/* 118 */     this.openReader.rf.seek(subTableLocation);
/* 119 */     int substFormat = this.openReader.rf.readShort();
/* 120 */     switch (substFormat) {
/*     */       case 2:
/* 122 */         readSubTableFormat2(subTableLocation);
/*     */         return;
/*     */       case 1:
/*     */       case 3:
/* 126 */         LOGGER.warn(MessageFormatUtil.format("Subtable format {0} of GPOS Lookup Type {1} is not supported yet", new Object[] {
/* 127 */                 Integer.valueOf(substFormat), Integer.valueOf(7) }));
/*     */         return;
/*     */     } 
/* 130 */     throw new IllegalArgumentException("Bad subtable format identifier: " + substFormat);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTableFormat2(int subTableLocation) throws IOException {
/* 135 */     int coverageOffset = this.openReader.rf.readUnsignedShort();
/* 136 */     int classDefOffset = this.openReader.rf.readUnsignedShort();
/* 137 */     int posClassSetCount = this.openReader.rf.readUnsignedShort();
/* 138 */     int[] posClassSetOffsets = this.openReader.readUShortArray(posClassSetCount, subTableLocation);
/*     */     
/* 140 */     Set<Integer> coverageGlyphIds = new HashSet<>(this.openReader.readCoverageFormat(subTableLocation + coverageOffset));
/* 141 */     OtfClass classDefinition = this.openReader.readClassDefinition(subTableLocation + classDefOffset);
/*     */     
/* 143 */     PosTableLookup7Format2 t = new PosTableLookup7Format2(this.openReader, this.lookupFlag, coverageGlyphIds, classDefinition);
/*     */ 
/*     */     
/* 146 */     List<List<ContextualPositionRule>> subClassSets = new ArrayList<>(posClassSetCount);
/* 147 */     for (int i = 0; i < posClassSetCount; i++) {
/* 148 */       List<ContextualPositionRule> subClassSet = null;
/* 149 */       if (posClassSetOffsets[i] != 0) {
/* 150 */         this.openReader.rf.seek(posClassSetOffsets[i]);
/* 151 */         int posClassRuleCount = this.openReader.rf.readUnsignedShort();
/* 152 */         int[] posClassRuleOffsets = this.openReader.readUShortArray(posClassRuleCount, posClassSetOffsets[i]);
/*     */         
/* 154 */         subClassSet = new ArrayList<>(posClassRuleCount);
/* 155 */         for (int j = 0; j < posClassRuleCount; j++) {
/*     */           
/* 157 */           this.openReader.rf.seek(posClassRuleOffsets[j]);
/*     */           
/* 159 */           int glyphCount = this.openReader.rf.readUnsignedShort();
/* 160 */           int posCount = this.openReader.rf.readUnsignedShort();
/* 161 */           int[] inputClassIds = this.openReader.readUShortArray(glyphCount - 1);
/* 162 */           PosLookupRecord[] posLookupRecords = this.openReader.readPosLookupRecords(posCount);
/*     */           
/* 164 */           PosTableLookup7Format2.PosRuleFormat2 posRuleFormat2 = new PosTableLookup7Format2.PosRuleFormat2(t, inputClassIds, posLookupRecords);
/* 165 */           subClassSet.add(posRuleFormat2);
/*     */         } 
/*     */       } 
/* 168 */       subClassSets.add(subClassSet);
/*     */     } 
/*     */     
/* 171 */     t.setPosClassSets(subClassSets);
/* 172 */     this.subTables.add(t);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GposLookupType7.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */