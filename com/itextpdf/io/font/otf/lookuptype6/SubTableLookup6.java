/*     */ package com.itextpdf.io.font.otf.lookuptype6;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.ContextualRule;
/*     */ import com.itextpdf.io.font.otf.ContextualSubTable;
/*     */ import com.itextpdf.io.font.otf.ContextualSubstRule;
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.font.otf.GlyphLine;
/*     */ import com.itextpdf.io.font.otf.OpenTableLookup;
/*     */ import com.itextpdf.io.font.otf.OpenTypeFontTableReader;
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
/*     */ public abstract class SubTableLookup6
/*     */   extends ContextualSubTable
/*     */ {
/*     */   private static final long serialVersionUID = -7471613803606544198L;
/*     */   
/*     */   protected SubTableLookup6(OpenTypeFontTableReader openReader, int lookupFlag) {
/*  60 */     super(openReader, lookupFlag);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContextualSubstRule getMatchingContextRule(GlyphLine line) {
/*  65 */     if (line.idx >= line.end) {
/*  66 */       return null;
/*     */     }
/*  68 */     Glyph g = line.get(line.idx);
/*  69 */     List<ContextualSubstRule> rules = getSetOfRulesForStartGlyph(g.getCode());
/*  70 */     for (ContextualSubstRule rule : rules) {
/*  71 */       int lastGlyphIndex = checkIfContextMatch(line, rule);
/*     */       
/*  73 */       if (lastGlyphIndex != -1 && 
/*  74 */         checkIfLookaheadContextMatch(line, rule, lastGlyphIndex) && 
/*  75 */         checkIfBacktrackContextMatch(line, rule)) {
/*     */         
/*  77 */         line.start = line.idx;
/*  78 */         line.end = lastGlyphIndex + 1;
/*  79 */         return rule;
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkIfLookaheadContextMatch(GlyphLine line, ContextualSubstRule rule, int startIdx) {
/*  97 */     OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
/*  98 */     gidx.line = line;
/*  99 */     gidx.idx = startIdx; int j;
/* 100 */     for (j = 0; j < rule.getLookaheadContextLength(); j++) {
/* 101 */       gidx.nextGlyph(this.openReader, this.lookupFlag);
/* 102 */       if (gidx.glyph == null || !rule.isGlyphMatchesLookahead(gidx.glyph.getCode(), j)) {
/*     */         break;
/*     */       }
/*     */     } 
/* 106 */     return (j == rule.getLookaheadContextLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkIfBacktrackContextMatch(GlyphLine line, ContextualSubstRule rule) {
/* 118 */     OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
/* 119 */     gidx.line = line;
/* 120 */     gidx.idx = line.idx; int j;
/* 121 */     for (j = 0; j < rule.getBacktrackContextLength(); j++) {
/* 122 */       gidx.previousGlyph(this.openReader, this.lookupFlag);
/* 123 */       if (gidx.glyph == null || !rule.isGlyphMatchesBacktrack(gidx.glyph.getCode(), j)) {
/*     */         break;
/*     */       }
/*     */     } 
/* 127 */     return (j == rule.getBacktrackContextLength());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/lookuptype6/SubTableLookup6.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */