/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GsubLookupType4
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = -8106254947137506056L;
/*     */   private Map<Integer, List<int[]>> ligatures;
/*     */   
/*     */   public GsubLookupType4(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  64 */     super(openReader, lookupFlag, subTableLocations);
/*  65 */     this.ligatures = new HashMap<>();
/*  66 */     readSubTables();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean transformOne(GlyphLine line) {
/*  72 */     if (line.idx >= line.end)
/*  73 */       return false; 
/*  74 */     boolean changed = false;
/*  75 */     Glyph g = line.get(line.idx);
/*  76 */     boolean match = false;
/*  77 */     if (this.ligatures.containsKey(Integer.valueOf(g.getCode())) && !this.openReader.isSkip(g.getCode(), this.lookupFlag)) {
/*  78 */       OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
/*  79 */       gidx.line = line;
/*  80 */       List<int[]> ligs = this.ligatures.get(Integer.valueOf(g.getCode()));
/*  81 */       for (int[] lig : ligs) {
/*  82 */         match = true;
/*  83 */         gidx.idx = line.idx;
/*  84 */         for (int j = 1; j < lig.length; j++) {
/*  85 */           gidx.nextGlyph(this.openReader, this.lookupFlag);
/*  86 */           if (gidx.glyph == null || gidx.glyph.getCode() != lig[j]) {
/*  87 */             match = false;
/*     */             break;
/*     */           } 
/*     */         } 
/*  91 */         if (match) {
/*  92 */           line.substituteManyToOne(this.openReader, this.lookupFlag, lig.length - 1, lig[0]);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  97 */     if (match) {
/*  98 */       changed = true;
/*     */     }
/* 100 */     line.idx++;
/* 101 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/* 106 */     this.openReader.rf.seek(subTableLocation);
/*     */     
/* 108 */     this.openReader.rf.readShort();
/* 109 */     int coverage = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 110 */     int ligSetCount = this.openReader.rf.readUnsignedShort();
/* 111 */     int[] ligatureSet = new int[ligSetCount];
/* 112 */     for (int k = 0; k < ligSetCount; k++) {
/* 113 */       ligatureSet[k] = this.openReader.rf.readUnsignedShort() + subTableLocation;
/*     */     }
/* 115 */     List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(coverage);
/* 116 */     for (int i = 0; i < ligSetCount; i++) {
/* 117 */       this.openReader.rf.seek(ligatureSet[i]);
/* 118 */       int ligatureCount = this.openReader.rf.readUnsignedShort();
/* 119 */       int[] ligature = new int[ligatureCount];
/* 120 */       for (int j = 0; j < ligatureCount; j++) {
/* 121 */         ligature[j] = this.openReader.rf.readUnsignedShort() + ligatureSet[i];
/*     */       }
/* 123 */       List<int[]> components = (List)new ArrayList<>(ligatureCount);
/* 124 */       for (int m = 0; m < ligatureCount; m++) {
/* 125 */         this.openReader.rf.seek(ligature[m]);
/* 126 */         int ligGlyph = this.openReader.rf.readUnsignedShort();
/* 127 */         int compCount = this.openReader.rf.readUnsignedShort();
/* 128 */         int[] component = new int[compCount];
/* 129 */         component[0] = ligGlyph;
/* 130 */         for (int n = 1; n < compCount; n++) {
/* 131 */           component[n] = this.openReader.rf.readUnsignedShort();
/*     */         }
/* 133 */         components.add(component);
/*     */       } 
/* 135 */       this.ligatures.put(coverageGlyphIds.get(i), components);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GsubLookupType4.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */