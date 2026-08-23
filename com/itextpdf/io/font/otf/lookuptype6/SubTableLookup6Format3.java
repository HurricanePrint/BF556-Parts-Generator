/*     */ package com.itextpdf.io.font.otf.lookuptype6;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.ContextualSubstRule;
/*     */ import com.itextpdf.io.font.otf.OpenTypeFontTableReader;
/*     */ import com.itextpdf.io.font.otf.SubstLookupRecord;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubTableLookup6Format3
/*     */   extends SubTableLookup6
/*     */ {
/*     */   private static final long serialVersionUID = 764166925472080146L;
/*     */   ContextualSubstRule substitutionRule;
/*     */   
/*     */   public SubTableLookup6Format3(OpenTypeFontTableReader openReader, int lookupFlag, SubstRuleFormat3 rule) {
/*  62 */     super(openReader, lookupFlag);
/*  63 */     this.substitutionRule = rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startId) {
/*  68 */     SubstRuleFormat3 ruleFormat3 = (SubstRuleFormat3)this.substitutionRule;
/*  69 */     if (((Set)ruleFormat3.inputCoverages.get(0)).contains(Integer.valueOf(startId)) && !this.openReader.isSkip(startId, this.lookupFlag)) {
/*  70 */       return Collections.singletonList(this.substitutionRule);
/*     */     }
/*  72 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public static class SubstRuleFormat3
/*     */     extends ContextualSubstRule {
/*     */     private static final long serialVersionUID = -8817891790304481782L;
/*     */     List<Set<Integer>> backtrackCoverages;
/*     */     List<Set<Integer>> inputCoverages;
/*     */     List<Set<Integer>> lookaheadCoverages;
/*     */     SubstLookupRecord[] substLookupRecords;
/*     */     
/*     */     public SubstRuleFormat3(List<Set<Integer>> backtrackCoverages, List<Set<Integer>> inputCoverages, List<Set<Integer>> lookaheadCoverages, SubstLookupRecord[] substLookupRecords) {
/*  84 */       this.backtrackCoverages = backtrackCoverages;
/*  85 */       this.inputCoverages = inputCoverages;
/*  86 */       this.lookaheadCoverages = lookaheadCoverages;
/*  87 */       this.substLookupRecords = substLookupRecords;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getContextLength() {
/*  92 */       return this.inputCoverages.size();
/*     */     }
/*     */     
/*     */     public int getLookaheadContextLength() {
/*  96 */       return this.lookaheadCoverages.size();
/*     */     }
/*     */     
/*     */     public int getBacktrackContextLength() {
/* 100 */       return this.backtrackCoverages.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public SubstLookupRecord[] getSubstLookupRecords() {
/* 105 */       return this.substLookupRecords;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
/* 110 */       return ((Set)this.inputCoverages.get(atIdx)).contains(Integer.valueOf(glyphId));
/*     */     }
/*     */     
/*     */     public boolean isGlyphMatchesLookahead(int glyphId, int atIdx) {
/* 114 */       return ((Set)this.lookaheadCoverages.get(atIdx)).contains(Integer.valueOf(glyphId));
/*     */     }
/*     */     
/*     */     public boolean isGlyphMatchesBacktrack(int glyphId, int atIdx) {
/* 118 */       return ((Set)this.backtrackCoverages.get(atIdx)).contains(Integer.valueOf(glyphId));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/lookuptype6/SubTableLookup6Format3.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */