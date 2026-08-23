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
/*     */ public class GposLookupType6
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = -2213669257401436260L;
/*     */   private final List<MarkToBaseMark> marksbases;
/*     */   
/*     */   public GposLookupType6(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  61 */     super(openReader, lookupFlag, subTableLocations);
/*  62 */     this.marksbases = new ArrayList<>();
/*  63 */     readSubTables();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean transformOne(GlyphLine line) {
/*  68 */     if (line.idx >= line.end)
/*  69 */       return false; 
/*  70 */     if (this.openReader.isSkip(line.get(line.idx).getCode(), this.lookupFlag)) {
/*  71 */       line.idx++;
/*  72 */       return false;
/*     */     } 
/*     */     
/*  75 */     boolean changed = false;
/*  76 */     OpenTableLookup.GlyphIndexer gi = null;
/*  77 */     for (MarkToBaseMark mb : this.marksbases) {
/*  78 */       OtfMarkRecord omr = mb.marks.get(Integer.valueOf(line.get(line.idx).getCode()));
/*  79 */       if (omr == null)
/*     */         continue; 
/*  81 */       if (gi == null) {
/*  82 */         gi = new OpenTableLookup.GlyphIndexer();
/*  83 */         gi.idx = line.idx;
/*  84 */         gi.line = line;
/*     */         do {
/*  86 */           int prev = gi.idx;
/*     */           
/*  88 */           boolean foundBaseGlyph = false;
/*  89 */           gi.previousGlyph(this.openReader, this.lookupFlag);
/*  90 */           if (gi.idx != -1) {
/*  91 */             for (int i = gi.idx; i < prev; i++) {
/*  92 */               if (this.openReader.getGlyphClass(line.get(i).getCode()) == 1) {
/*  93 */                 foundBaseGlyph = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/*  98 */           if (foundBaseGlyph) {
/*  99 */             gi.glyph = null;
/*     */             break;
/*     */           } 
/* 102 */           if (gi.glyph == null)
/*     */             break; 
/* 104 */         } while (!mb.baseMarks.containsKey(Integer.valueOf(gi.glyph.getCode())));
/*     */ 
/*     */         
/* 107 */         if (gi.glyph == null)
/*     */           break; 
/*     */       } 
/* 110 */       GposAnchor[] gpas = mb.baseMarks.get(Integer.valueOf(gi.glyph.getCode()));
/* 111 */       if (gpas == null)
/*     */         continue; 
/* 113 */       int markClass = omr.markClass;
/* 114 */       GposAnchor baseAnchor = gpas[markClass];
/* 115 */       GposAnchor markAnchor = omr.anchor;
/* 116 */       line.set(line.idx, new Glyph(line.get(line.idx), -markAnchor.XCoordinate + baseAnchor.XCoordinate, -markAnchor.YCoordinate + baseAnchor.YCoordinate, 0, 0, gi.idx - line.idx));
/*     */ 
/*     */ 
/*     */       
/* 120 */       changed = true;
/*     */     } 
/*     */     
/* 123 */     line.idx++;
/* 124 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/* 129 */     this.openReader.rf.seek(subTableLocation);
/*     */ 
/*     */     
/* 132 */     this.openReader.rf.readUnsignedShort();
/* 133 */     int markCoverageLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 134 */     int baseCoverageLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 135 */     int classCount = this.openReader.rf.readUnsignedShort();
/* 136 */     int markArrayLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 137 */     int baseArrayLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 138 */     List<Integer> markCoverage = this.openReader.readCoverageFormat(markCoverageLocation);
/* 139 */     List<Integer> baseCoverage = this.openReader.readCoverageFormat(baseCoverageLocation);
/* 140 */     List<OtfMarkRecord> markRecords = OtfReadCommon.readMarkArray(this.openReader, markArrayLocation);
/* 141 */     MarkToBaseMark markToBaseMark = new MarkToBaseMark();
/* 142 */     for (int k = 0; k < markCoverage.size(); k++) {
/* 143 */       markToBaseMark.marks.put(markCoverage.get(k), markRecords.get(k));
/*     */     }
/* 145 */     List<GposAnchor[]> baseArray = OtfReadCommon.readBaseArray(this.openReader, classCount, baseArrayLocation);
/* 146 */     for (int i = 0; i < baseCoverage.size(); i++) {
/* 147 */       markToBaseMark.baseMarks.put(baseCoverage.get(i), baseArray.get(i));
/*     */     }
/* 149 */     this.marksbases.add(markToBaseMark);
/*     */   }
/*     */   
/*     */   private static class MarkToBaseMark implements Serializable {
/*     */     private static final long serialVersionUID = -2097614797893579206L;
/* 154 */     public final Map<Integer, OtfMarkRecord> marks = new HashMap<>(); private MarkToBaseMark() {}
/* 155 */     public final Map<Integer, GposAnchor[]> baseMarks = (Map)new HashMap<>();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GposLookupType6.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */