/*     */ package com.itextpdf.io.font.otf.lookuptype6;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.ContextualSubstRule;
/*     */ import com.itextpdf.io.font.otf.OpenTypeFontTableReader;
/*     */ import com.itextpdf.io.font.otf.OtfClass;
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
/*     */ 
/*     */ public class SubTableLookup6Format2
/*     */   extends SubTableLookup6
/*     */ {
/*     */   private static final long serialVersionUID = -4930056769443953242L;
/*     */   private Set<Integer> substCoverageGlyphIds;
/*     */   private List<List<ContextualSubstRule>> subClassSets;
/*     */   private OtfClass backtrackClassDefinition;
/*     */   private OtfClass inputClassDefinition;
/*     */   private OtfClass lookaheadClassDefinition;
/*     */   
/*     */   public SubTableLookup6Format2(OpenTypeFontTableReader openReader, int lookupFlag, Set<Integer> substCoverageGlyphIds, OtfClass backtrackClassDefinition, OtfClass inputClassDefinition, OtfClass lookaheadClassDefinition) {
/*  68 */     super(openReader, lookupFlag);
/*  69 */     this.substCoverageGlyphIds = substCoverageGlyphIds;
/*  70 */     this.backtrackClassDefinition = backtrackClassDefinition;
/*  71 */     this.inputClassDefinition = inputClassDefinition;
/*  72 */     this.lookaheadClassDefinition = lookaheadClassDefinition;
/*     */   }
/*     */   
/*     */   public void setSubClassSets(List<List<ContextualSubstRule>> subClassSets) {
/*  76 */     this.subClassSets = subClassSets;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startId) {
/*  81 */     if (this.substCoverageGlyphIds.contains(Integer.valueOf(startId)) && !this.openReader.isSkip(startId, this.lookupFlag)) {
/*  82 */       int gClass = this.inputClassDefinition.getOtfClass(startId);
/*  83 */       return this.subClassSets.get(gClass);
/*     */     } 
/*  85 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SubstRuleFormat2
/*     */     extends ContextualSubstRule
/*     */   {
/*     */     private static final long serialVersionUID = 5227942059467859541L;
/*     */     
/*     */     private int[] backtrackClassIds;
/*     */     
/*     */     private int[] inputClassIds;
/*     */     private int[] lookAheadClassIds;
/*     */     private SubstLookupRecord[] substLookupRecords;
/*     */     private SubTableLookup6Format2 subTable;
/*     */     
/*     */     public SubstRuleFormat2(SubTableLookup6Format2 subTable, int[] backtrackClassIds, int[] inputClassIds, int[] lookAheadClassIds, SubstLookupRecord[] substLookupRecords) {
/* 102 */       this.subTable = subTable;
/* 103 */       this.backtrackClassIds = backtrackClassIds;
/* 104 */       this.inputClassIds = inputClassIds;
/* 105 */       this.lookAheadClassIds = lookAheadClassIds;
/* 106 */       this.substLookupRecords = substLookupRecords;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getContextLength() {
/* 111 */       return this.inputClassIds.length + 1;
/*     */     }
/*     */     
/*     */     public int getLookaheadContextLength() {
/* 115 */       return this.lookAheadClassIds.length;
/*     */     }
/*     */     
/*     */     public int getBacktrackContextLength() {
/* 119 */       return this.backtrackClassIds.length;
/*     */     }
/*     */ 
/*     */     
/*     */     public SubstLookupRecord[] getSubstLookupRecords() {
/* 124 */       return this.substLookupRecords;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
/* 129 */       return (this.subTable.inputClassDefinition.getOtfClass(glyphId) == this.inputClassIds[atIdx - 1]);
/*     */     }
/*     */     
/*     */     public boolean isGlyphMatchesLookahead(int glyphId, int atIdx) {
/* 133 */       return (this.subTable.lookaheadClassDefinition.getOtfClass(glyphId) == this.lookAheadClassIds[atIdx]);
/*     */     }
/*     */     
/*     */     public boolean isGlyphMatchesBacktrack(int glyphId, int atIdx) {
/* 137 */       return (this.subTable.backtrackClassDefinition.getOtfClass(glyphId) == this.backtrackClassIds[atIdx]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/lookuptype6/SubTableLookup6Format2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */