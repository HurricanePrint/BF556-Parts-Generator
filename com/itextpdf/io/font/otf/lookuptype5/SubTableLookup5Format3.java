/*    */ package com.itextpdf.io.font.otf.lookuptype5;
/*    */ 
/*    */ import com.itextpdf.io.font.otf.ContextualSubTable;
/*    */ import com.itextpdf.io.font.otf.ContextualSubstRule;
/*    */ import com.itextpdf.io.font.otf.OpenTypeFontTableReader;
/*    */ import com.itextpdf.io.font.otf.SubstLookupRecord;
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
/*    */ public class SubTableLookup5Format3
/*    */   extends ContextualSubTable
/*    */ {
/*    */   private static final long serialVersionUID = -9142690964201749548L;
/*    */   ContextualSubstRule substitutionRule;
/*    */   
/*    */   public SubTableLookup5Format3(OpenTypeFontTableReader openReader, int lookupFlag, SubstRuleFormat3 rule) {
/* 63 */     super(openReader, lookupFlag);
/* 64 */     this.substitutionRule = rule;
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startId) {
/* 69 */     SubstRuleFormat3 ruleFormat3 = (SubstRuleFormat3)this.substitutionRule;
/* 70 */     if (((Set)ruleFormat3.coverages.get(0)).contains(Integer.valueOf(startId)) && !this.openReader.isSkip(startId, this.lookupFlag)) {
/* 71 */       return Collections.singletonList(this.substitutionRule);
/*    */     }
/* 73 */     return Collections.emptyList();
/*    */   }
/*    */   
/*    */   public static class SubstRuleFormat3 extends ContextualSubstRule {
/*    */     private static final long serialVersionUID = -1840126702536353850L;
/*    */     List<Set<Integer>> coverages;
/*    */     SubstLookupRecord[] substLookupRecords;
/*    */     
/*    */     public SubstRuleFormat3(List<Set<Integer>> coverages, SubstLookupRecord[] substLookupRecords) {
/* 82 */       this.coverages = coverages;
/* 83 */       this.substLookupRecords = substLookupRecords;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getContextLength() {
/* 88 */       return this.coverages.size();
/*    */     }
/*    */ 
/*    */     
/*    */     public SubstLookupRecord[] getSubstLookupRecords() {
/* 93 */       return this.substLookupRecords;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
/* 98 */       return ((Set)this.coverages.get(atIdx)).contains(Integer.valueOf(glyphId));
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/lookuptype5/SubTableLookup5Format3.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */