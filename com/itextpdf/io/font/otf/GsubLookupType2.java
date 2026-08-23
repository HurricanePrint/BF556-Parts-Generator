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
/*     */ public class GsubLookupType2
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = 48861238131801306L;
/*     */   private Map<Integer, int[]> substMap;
/*     */   
/*     */   public GsubLookupType2(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  59 */     super(openReader, lookupFlag, subTableLocations);
/*  60 */     this.substMap = (Map)new HashMap<>();
/*  61 */     readSubTables();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean transformOne(GlyphLine line) {
/*  66 */     if (line.idx >= line.end) {
/*  67 */       return false;
/*     */     }
/*  69 */     Glyph g = line.get(line.idx);
/*  70 */     boolean changed = false;
/*  71 */     if (!this.openReader.isSkip(g.getCode(), this.lookupFlag)) {
/*  72 */       int[] substSequence = this.substMap.get(Integer.valueOf(g.getCode()));
/*  73 */       if (substSequence != null)
/*     */       {
/*  75 */         if (substSequence.length > 0) {
/*  76 */           line.substituteOneToMany(this.openReader, substSequence);
/*  77 */           changed = true;
/*     */         } 
/*     */       }
/*     */     } 
/*  81 */     line.idx++;
/*  82 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/*  87 */     this.openReader.rf.seek(subTableLocation);
/*  88 */     int substFormat = this.openReader.rf.readUnsignedShort();
/*  89 */     if (substFormat == 1) {
/*  90 */       int coverage = this.openReader.rf.readUnsignedShort();
/*  91 */       int sequenceCount = this.openReader.rf.readUnsignedShort();
/*  92 */       int[] sequenceLocations = this.openReader.readUShortArray(sequenceCount, subTableLocation);
/*     */       
/*  94 */       List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverage);
/*  95 */       for (int i = 0; i < sequenceCount; i++) {
/*  96 */         this.openReader.rf.seek(sequenceLocations[i]);
/*  97 */         int glyphCount = this.openReader.rf.readUnsignedShort();
/*  98 */         this.substMap.put(coverageGlyphIds.get(i), this.openReader.readUShortArray(glyphCount));
/*     */       } 
/*     */     } else {
/* 101 */       throw new IllegalArgumentException("Bad substFormat: " + substFormat);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSubstitution(int index) {
/* 107 */     return this.substMap.containsKey(Integer.valueOf(index));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GsubLookupType2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */