/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
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
/*     */ public class GsubLookupType3
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = -5408042853790920298L;
/*     */   private Map<Integer, int[]> substMap;
/*     */   
/*     */   public GsubLookupType3(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  60 */     super(openReader, lookupFlag, subTableLocations);
/*  61 */     this.substMap = (Map)new HashMap<>();
/*  62 */     readSubTables();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean transformOne(GlyphLine line) {
/*  67 */     if (line.idx >= line.end) {
/*  68 */       return false;
/*     */     }
/*  70 */     Glyph g = line.get(line.idx);
/*  71 */     boolean changed = false;
/*  72 */     if (!this.openReader.isSkip(g.getCode(), this.lookupFlag)) {
/*  73 */       int[] substCode = this.substMap.get(Integer.valueOf(g.getCode()));
/*     */ 
/*     */       
/*  76 */       if (substCode != null && substCode[0] != g.getCode()) {
/*  77 */         line.substituteOneToOne(this.openReader, substCode[0]);
/*  78 */         changed = true;
/*     */       } 
/*     */     } 
/*  81 */     line.idx++;
/*  82 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/*  87 */     this.openReader.rf.seek(subTableLocation);
/*  88 */     int substFormat = this.openReader.rf.readShort();
/*  89 */     assert substFormat == 1;
/*  90 */     int coverage = this.openReader.rf.readUnsignedShort();
/*  91 */     int alternateSetCount = this.openReader.rf.readUnsignedShort();
/*  92 */     int[][] substitute = new int[alternateSetCount][];
/*  93 */     int[] alternateLocations = this.openReader.readUShortArray(alternateSetCount, subTableLocation);
/*  94 */     for (int k = 0; k < alternateSetCount; k++) {
/*  95 */       this.openReader.rf.seek(alternateLocations[k]);
/*  96 */       int glyphCount = this.openReader.rf.readUnsignedShort();
/*  97 */       substitute[k] = this.openReader.readUShortArray(glyphCount);
/*     */     } 
/*  99 */     List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverage);
/* 100 */     for (int i = 0; i < alternateSetCount; i++) {
/* 101 */       this.substMap.put(coverageGlyphIds.get(i), substitute[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSubstitution(int index) {
/* 107 */     return this.substMap.containsKey(Integer.valueOf(index));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GsubLookupType3.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */