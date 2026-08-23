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
/*     */ public class GposLookupType5
/*     */   extends OpenTableLookup
/*     */ {
/*     */   private static final long serialVersionUID = 6409145706785333023L;
/*     */   private final List<MarkToLigature> marksligatures;
/*     */   
/*     */   public GposLookupType5(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
/*  62 */     super(openReader, lookupFlag, subTableLocations);
/*  63 */     this.marksligatures = new ArrayList<>();
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
/*  77 */     OpenTableLookup.GlyphIndexer ligatureGlyphIndexer = null;
/*  78 */     for (MarkToLigature mb : this.marksligatures) {
/*  79 */       OtfMarkRecord omr = mb.marks.get(Integer.valueOf(line.get(line.idx).getCode()));
/*  80 */       if (omr == null)
/*     */         continue; 
/*  82 */       if (ligatureGlyphIndexer == null) {
/*  83 */         ligatureGlyphIndexer = new OpenTableLookup.GlyphIndexer();
/*  84 */         ligatureGlyphIndexer.idx = line.idx;
/*  85 */         ligatureGlyphIndexer.line = line;
/*     */         do {
/*  87 */           ligatureGlyphIndexer.previousGlyph(this.openReader, this.lookupFlag);
/*  88 */           if (ligatureGlyphIndexer.glyph == null) {
/*     */             break;
/*     */           }
/*     */         }
/*  92 */         while (mb.marks.containsKey(Integer.valueOf(ligatureGlyphIndexer.glyph.getCode())));
/*     */ 
/*     */ 
/*     */         
/*  96 */         if (ligatureGlyphIndexer.glyph == null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 100 */       List<GposAnchor[]> componentAnchors = mb.ligatures.get(Integer.valueOf(ligatureGlyphIndexer.glyph.getCode()));
/* 101 */       if (componentAnchors == null) {
/*     */         continue;
/*     */       }
/* 104 */       int markClass = omr.markClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       for (int component = componentAnchors.size() - 1; component >= 0; component--) {
/* 119 */         if (((GposAnchor[])componentAnchors.get(component))[markClass] != null) {
/* 120 */           GposAnchor baseAnchor = ((GposAnchor[])componentAnchors.get(component))[markClass];
/* 121 */           GposAnchor markAnchor = omr.anchor;
/* 122 */           line.set(line.idx, new Glyph(line.get(line.idx), baseAnchor.XCoordinate - markAnchor.XCoordinate, baseAnchor.YCoordinate - markAnchor.YCoordinate, 0, 0, ligatureGlyphIndexer.idx - line.idx));
/*     */ 
/*     */ 
/*     */           
/* 126 */           changed = true;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     line.idx++;
/* 135 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSubTable(int subTableLocation) throws IOException {
/* 140 */     this.openReader.rf.seek(subTableLocation);
/*     */ 
/*     */     
/* 143 */     this.openReader.rf.readUnsignedShort();
/* 144 */     int markCoverageLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 145 */     int ligatureCoverageLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 146 */     int classCount = this.openReader.rf.readUnsignedShort();
/* 147 */     int markArrayLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 148 */     int ligatureArrayLocation = this.openReader.rf.readUnsignedShort() + subTableLocation;
/* 149 */     List<Integer> markCoverage = this.openReader.readCoverageFormat(markCoverageLocation);
/* 150 */     List<Integer> ligatureCoverage = this.openReader.readCoverageFormat(ligatureCoverageLocation);
/* 151 */     List<OtfMarkRecord> markRecords = OtfReadCommon.readMarkArray(this.openReader, markArrayLocation);
/* 152 */     MarkToLigature markToLigature = new MarkToLigature();
/* 153 */     for (int k = 0; k < markCoverage.size(); k++) {
/* 154 */       markToLigature.marks.put(markCoverage.get(k), markRecords.get(k));
/*     */     }
/* 156 */     List<List<GposAnchor[]>> ligatureArray = OtfReadCommon.readLigatureArray(this.openReader, classCount, ligatureArrayLocation);
/* 157 */     for (int i = 0; i < ligatureCoverage.size(); i++) {
/* 158 */       markToLigature.ligatures.put(ligatureCoverage.get(i), ligatureArray.get(i));
/*     */     }
/* 160 */     this.marksligatures.add(markToLigature);
/*     */   }
/*     */   
/*     */   public static class MarkToLigature
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = 4249432630962669432L;
/* 166 */     public final Map<Integer, OtfMarkRecord> marks = new HashMap<>();
/*     */ 
/*     */     
/* 169 */     public final Map<Integer, List<GposAnchor[]>> ligatures = new HashMap<>();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/GposLookupType5.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */