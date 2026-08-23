/*     */ package com.itextpdf.io.font.otf.lookuptype6;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.ContextualSubstRule;
/*     */ import com.itextpdf.io.font.otf.OpenTypeFontTableReader;
/*     */ import com.itextpdf.io.font.otf.SubstLookupRecord;
/*     */ import java.util.Collections;
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
/*     */ public class SubTableLookup6Format1
/*     */   extends SubTableLookup6
/*     */ {
/*     */   private static final long serialVersionUID = 4252117327329368679L;
/*     */   private Map<Integer, List<ContextualSubstRule>> substMap;
/*     */   
/*     */   public SubTableLookup6Format1(OpenTypeFontTableReader openReader, int lookupFlag, Map<Integer, List<ContextualSubstRule>> substMap) {
/*  62 */     super(openReader, lookupFlag);
/*  63 */     this.substMap = substMap;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startGlyphId) {
/*  68 */     if (this.substMap.containsKey(Integer.valueOf(startGlyphId)) && !this.openReader.isSkip(startGlyphId, this.lookupFlag)) {
/*  69 */       return this.substMap.get(Integer.valueOf(startGlyphId));
/*     */     }
/*  71 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public static class SubstRuleFormat1
/*     */     extends ContextualSubstRule
/*     */   {
/*     */     private static final long serialVersionUID = 6962160437871819250L;
/*     */     private int[] inputGlyphIds;
/*     */     private int[] backtrackGlyphIds;
/*     */     private int[] lookAheadGlyphIds;
/*     */     private SubstLookupRecord[] substLookupRecords;
/*     */     
/*     */     public SubstRuleFormat1(int[] backtrackGlyphIds, int[] inputGlyphIds, int[] lookAheadGlyphIds, SubstLookupRecord[] substLookupRecords) {
/*  84 */       this.backtrackGlyphIds = backtrackGlyphIds;
/*  85 */       this.inputGlyphIds = inputGlyphIds;
/*  86 */       this.lookAheadGlyphIds = lookAheadGlyphIds;
/*  87 */       this.substLookupRecords = substLookupRecords;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getContextLength() {
/*  92 */       return this.inputGlyphIds.length + 1;
/*     */     }
/*     */     
/*     */     public int getLookaheadContextLength() {
/*  96 */       return this.lookAheadGlyphIds.length;
/*     */     }
/*     */     
/*     */     public int getBacktrackContextLength() {
/* 100 */       return this.backtrackGlyphIds.length;
/*     */     }
/*     */ 
/*     */     
/*     */     public SubstLookupRecord[] getSubstLookupRecords() {
/* 105 */       return this.substLookupRecords;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
/* 110 */       return (glyphId == this.inputGlyphIds[atIdx - 1]);
/*     */     }
/*     */     
/*     */     public boolean isGlyphMatchesLookahead(int glyphId, int atIdx) {
/* 114 */       return (glyphId == this.lookAheadGlyphIds[atIdx]);
/*     */     }
/*     */     
/*     */     public boolean isGlyphMatchesBacktrack(int glyphId, int atIdx) {
/* 118 */       return (glyphId == this.backtrackGlyphIds[atIdx]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/lookuptype6/SubTableLookup6Format1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */