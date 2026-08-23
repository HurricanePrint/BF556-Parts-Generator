/*    */ package com.itextpdf.io.font.otf.lookuptype5;
/*    */ 
/*    */ import com.itextpdf.io.font.otf.ContextualSubTable;
/*    */ import com.itextpdf.io.font.otf.ContextualSubstRule;
/*    */ import com.itextpdf.io.font.otf.OpenTypeFontTableReader;
/*    */ import com.itextpdf.io.font.otf.SubstLookupRecord;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class SubTableLookup5Format1
/*    */   extends ContextualSubTable
/*    */ {
/*    */   private static final long serialVersionUID = -6061489236592337747L;
/*    */   private Map<Integer, List<ContextualSubstRule>> substMap;
/*    */   
/*    */   public SubTableLookup5Format1(OpenTypeFontTableReader openReader, int lookupFlag, Map<Integer, List<ContextualSubstRule>> substMap) {
/* 63 */     super(openReader, lookupFlag);
/* 64 */     this.substMap = substMap;
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startGlyphId) {
/* 69 */     if (this.substMap.containsKey(Integer.valueOf(startGlyphId)) && !this.openReader.isSkip(startGlyphId, this.lookupFlag)) {
/* 70 */       return this.substMap.get(Integer.valueOf(startGlyphId));
/*    */     }
/* 72 */     return Collections.emptyList();
/*    */   }
/*    */   
/*    */   public static class SubstRuleFormat1
/*    */     extends ContextualSubstRule
/*    */   {
/*    */     private static final long serialVersionUID = -540799242670887211L;
/*    */     private int[] inputGlyphIds;
/*    */     private SubstLookupRecord[] substLookupRecords;
/*    */     
/*    */     public SubstRuleFormat1(int[] inputGlyphIds, SubstLookupRecord[] substLookupRecords) {
/* 83 */       this.inputGlyphIds = inputGlyphIds;
/* 84 */       this.substLookupRecords = substLookupRecords;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getContextLength() {
/* 89 */       return this.inputGlyphIds.length + 1;
/*    */     }
/*    */ 
/*    */     
/*    */     public SubstLookupRecord[] getSubstLookupRecords() {
/* 94 */       return this.substLookupRecords;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
/* 99 */       return (glyphId == this.inputGlyphIds[atIdx - 1]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/lookuptype5/SubTableLookup5Format1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */