/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public class GposLookupType4
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = 8820454200196341970L;
/*     */   private final List<MarkToBase> marksbases;
/*     */   
/*     */   public GposLookupType4(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  62 */     super(openReader, lookupFlag, subTableLocations);
/*  63 */     this.marksbases = new ArrayList<>();
/*  64 */     readSubTables();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean transformOne(GlyphLine line) {
/*  69 */     if (line.idx >= line.end)
/*  70 */       return false; 
/*  71 */     if (this.openReader.isSkip(line.get(line.idx).getCode(), this.lookupFlag)) {
/*  72 */       line.idx++;
/*  73 */       return false;
/*     */     } 
/*     */     
/*  76 */     boolean changed = false;
/*  77 */     OpenTableLookup.GlyphIndexer gi = null;
/*  78 */     for (MarkToBase mb : this.marksbases) {
/*  79 */       OtfMarkRecord omr = mb.marks.get(Integer.valueOf(line.get(line.idx).getCode()));
/*  80 */       if (omr == null)
/*     */         continue; 
/*  82 */       if (gi == null) {
/*  83 */         gi = new OpenTableLookup.GlyphIndexer();
/*  84 */         gi.idx = line.idx;
/*  85 */         gi.line = line;
/*     */         do {
/*  87 */           gi.previousGlyph(this.openReader, this.lookupFlag);
/*  88 */           if (gi.glyph == null) {
/*     */             break;
/*     */           }
/*  91 */         } while (this.openReader.getGlyphClass(gi.glyph.getCode()) == 3);
/*     */ 
/*     */ 
/*     */         
/*  95 */         if (gi.glyph == null)
/*     */           break; 
/*     */       } 
/*  98 */       GposAnchor[] gpas = mb.bases.get(Integer.valueOf(gi.glyph.getCode()));
/*  99 */       if (gpas == null)
/*     */         continue; 
/* 101 */       int markClass = omr.markClass;
/* 102 */       int xPlacement = 0;
/* 103 */       int yPlacement = 0;
/* 104 */       GposAnchor baseAnchor = gpas[markClass];
/* 105 */       if (baseAnchor != null) {
/* 106 */         xPlacement = baseAnchor.XCoordinate;
/* 107 */         yPlacement = baseAnchor.YCoordinate;
/*     */       } 
/* 109 */       GposAnchor markAnchor = omr.anchor;
/* 110 */       if (markAnchor != null) {
/* 111 */         xPlacement -= markAnchor.XCoordinate;
/* 112 */         yPlacement -= markAnchor.YCoordinate;
/*     */       } 
/* 114 */       line.set(line.idx, new Glyph(line.get(line.idx), xPlacement, yPlacement, 0, 0, gi.idx - line.idx));
/*     */ 
/*     */       
/* 117 */       changed = true;
/*     */     } 
/*     */     
/* 120 */     line.idx++;
/* 121 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/* 126 */     this.openReader.rf.seek(subTableLocation);
/*     */ 
/*     */     
/* 129 */     this.openReader.rf.readUnsignedShort();
/* 130 */     int markCoverageLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 131 */     int baseCoverageLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 132 */     int classCount = this.openReader.rf.readUnsignedShort();
/* 133 */     int markArrayLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 134 */     int baseArrayLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 135 */     List<Integer> markCoverage = this.openReader.readCoverageFormat(markCoverageLocation);
/* 136 */     List<Integer> baseCoverage = this.openReader.readCoverageFormat(baseCoverageLocation);
/* 137 */     List<OtfMarkRecord> markRecords = OtfReadCommon.readMarkArray(this.openReader, markArrayLocation);
/* 138 */     MarkToBase markToBase = new MarkToBase();
/* 139 */     for (int k = 0; k < markCoverage.size(); k++) {
/* 140 */       markToBase.marks.put(markCoverage.get(k), markRecords.get(k));
/*     */     }
/* 142 */     List<GposAnchor[]> baseArray = OtfReadCommon.readBaseArray(this.openReader, classCount, baseArrayLocation);
/* 143 */     for (int i = 0; i < baseCoverage.size(); i++) {
/* 144 */       markToBase.bases.put(baseCoverage.get(i), baseArray.get(i));
/*     */     }
/* 146 */     this.marksbases.add(markToBase);
/*     */   }
/*     */   
/*     */   public static class MarkToBase implements Serializable {
/*     */     private static final long serialVersionUID = 1518537209432079627L;
/* 151 */     public final Map<Integer, OtfMarkRecord> marks = new HashMap<>();
/* 152 */     public final Map<Integer, GposAnchor[]> bases = (Map)new HashMap<>();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GposLookupType4.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */