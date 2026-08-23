/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GsubLookupType1
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = 1047931810962199937L;
/*     */   private IntHashtable substMap;
/*     */   
/*     */   public GsubLookupType1(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  60 */     super(openReader, lookupFlag, subTableLocations);
/*  61 */     this.substMap = new IntHashtable();
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
/*  73 */       int substCode = this.substMap.get(g.getCode());
/*     */ 
/*     */       
/*  76 */       if (substCode != 0 && substCode != g.getCode()) {
/*  77 */         line.substituteOneToOne(this.openReader, substCode);
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
/*  89 */     if (substFormat == 1) {
/*  90 */       int coverage = this.openReader.rf.readUnsignedShort();
/*  91 */       int deltaGlyphID = this.openReader.rf.readShort();
/*  92 */       List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverage);
/*  93 */       for (Iterator<Integer> iterator = coverageGlyphIds.iterator(); iterator.hasNext(); ) { int coverageGlyphId = ((Integer)iterator.next()).intValue();
/*  94 */         int substituteGlyphId = coverageGlyphId + deltaGlyphID;
/*  95 */         this.substMap.put(coverageGlyphId, substituteGlyphId); }
/*     */     
/*  97 */     } else if (substFormat == 2) {
/*  98 */       int coverage = this.openReader.rf.readUnsignedShort();
/*  99 */       int glyphCount = this.openReader.rf.readUnsignedShort();
/* 100 */       int[] substitute = new int[glyphCount];
/* 101 */       for (int k = 0; k < glyphCount; k++) {
/* 102 */         substitute[k] = this.openReader.rf.readUnsignedShort();
/*     */       }
/* 104 */       List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverage);
/* 105 */       for (int i = 0; i < glyphCount; i++) {
/* 106 */         this.substMap.put(((Integer)coverageGlyphIds.get(i)).intValue(), substitute[i]);
/*     */       }
/*     */     } else {
/* 109 */       throw new IllegalArgumentException("Bad substFormat: " + substFormat);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSubstitution(int index) {
/* 115 */     return this.substMap.containsKey(index);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GsubLookupType1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */