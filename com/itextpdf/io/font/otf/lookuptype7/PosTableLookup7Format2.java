/*    */ package com.itextpdf.io.font.otf.lookuptype7;
/*    */ 
/*    */ import com.itextpdf.io.font.otf.ContextualPositionRule;
/*    */ import com.itextpdf.io.font.otf.ContextualPositionTable;
/*    */ import com.itextpdf.io.font.otf.OpenTypeFontTableReader;
/*    */ import com.itextpdf.io.font.otf.OtfClass;
/*    */ import com.itextpdf.io.font.otf.PosLookupRecord;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PosTableLookup7Format2
/*    */   extends ContextualPositionTable
/*    */ {
/*    */   private static final long serialVersionUID = 2542153457480614040L;
/*    */   private Set<Integer> posCoverageGlyphIds;
/*    */   private List<List<ContextualPositionRule>> subClassSets;
/*    */   private OtfClass classDefinition;
/*    */   
/*    */   public PosTableLookup7Format2(OpenTypeFontTableReader openReader, int lookupFlag, Set<Integer> posCoverageGlyphIds, OtfClass classDefinition) {
/* 44 */     super(openReader, lookupFlag);
/* 45 */     this.posCoverageGlyphIds = posCoverageGlyphIds;
/*    */     
/* 47 */     this.classDefinition = classDefinition;
/*    */   }
/*    */   
/*    */   public void setPosClassSets(List<List<ContextualPositionRule>> subClassSets) {
/* 51 */     this.subClassSets = subClassSets;
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<ContextualPositionRule> getSetOfRulesForStartGlyph(int startId) {
/* 56 */     if (this.posCoverageGlyphIds.contains(Integer.valueOf(startId)) && !this.openReader.isSkip(startId, this.lookupFlag)) {
/* 57 */       int gClass = this.classDefinition.getOtfClass(startId);
/* 58 */       return this.subClassSets.get(gClass);
/*    */     } 
/* 60 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public static class PosRuleFormat2
/*    */     extends ContextualPositionRule
/*    */   {
/*    */     private static final long serialVersionUID = 652574134066355802L;
/*    */     private int[] inputClassIds;
/*    */     private PosLookupRecord[] posLookupRecords;
/*    */     private OtfClass classDefinition;
/*    */     
/*    */     public PosRuleFormat2(PosTableLookup7Format2 subTable, int[] inputClassIds, PosLookupRecord[] posLookupRecords) {
/* 73 */       this.inputClassIds = inputClassIds;
/* 74 */       this.posLookupRecords = posLookupRecords;
/* 75 */       this.classDefinition = subTable.classDefinition;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getContextLength() {
/* 80 */       return this.inputClassIds.length + 1;
/*    */     }
/*    */ 
/*    */     
/*    */     public PosLookupRecord[] getPosLookupRecords() {
/* 85 */       return this.posLookupRecords;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
/* 90 */       return (this.classDefinition.getOtfClass(glyphId) == this.inputClassIds[atIdx - 1]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/lookuptype7/PosTableLookup7Format2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */