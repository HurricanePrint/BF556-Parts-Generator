/*     */ package com.itextpdf.io.font.otf.lookuptype5;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.ContextualSubTable;
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
/*     */ public class SubTableLookup5Format2
/*     */   extends ContextualSubTable
/*     */ {
/*     */   private static final long serialVersionUID = -2184080481143798249L;
/*     */   private Set<Integer> substCoverageGlyphIds;
/*     */   private List<List<ContextualSubstRule>> subClassSets;
/*     */   private OtfClass classDefinition;
/*     */   
/*     */   public SubTableLookup5Format2(OpenTypeFontTableReader openReader, int lookupFlag, Set<Integer> substCoverageGlyphIds, OtfClass classDefinition) {
/*  66 */     super(openReader, lookupFlag);
/*  67 */     this.substCoverageGlyphIds = substCoverageGlyphIds;
/*     */     
/*  69 */     this.classDefinition = classDefinition;
/*     */   }
/*     */   
/*     */   public void setSubClassSets(List<List<ContextualSubstRule>> subClassSets) {
/*  73 */     this.subClassSets = subClassSets;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startId) {
/*  78 */     if (this.substCoverageGlyphIds.contains(Integer.valueOf(startId)) && !this.openReader.isSkip(startId, this.lookupFlag)) {
/*  79 */       int gClass = this.classDefinition.getOtfClass(startId);
/*  80 */       return this.subClassSets.get(gClass);
/*     */     } 
/*  82 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public static class SubstRuleFormat2
/*     */     extends ContextualSubstRule
/*     */   {
/*     */     private static final long serialVersionUID = 652574134066355802L;
/*     */     private int[] inputClassIds;
/*     */     private SubstLookupRecord[] substLookupRecords;
/*     */     private OtfClass classDefinition;
/*     */     
/*     */     public SubstRuleFormat2(SubTableLookup5Format2 subTable, int[] inputClassIds, SubstLookupRecord[] substLookupRecords) {
/*  94 */       this.inputClassIds = inputClassIds;
/*  95 */       this.substLookupRecords = substLookupRecords;
/*  96 */       this.classDefinition = subTable.classDefinition;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getContextLength() {
/* 101 */       return this.inputClassIds.length + 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public SubstLookupRecord[] getSubstLookupRecords() {
/* 106 */       return this.substLookupRecords;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
/* 111 */       return (this.classDefinition.getOtfClass(glyphId) == this.inputClassIds[atIdx - 1]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/lookuptype5/SubTableLookup5Format2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */