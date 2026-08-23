/*    */ package com.itextpdf.io.font.otf;
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
/*    */ public abstract class ContextualSubTable
/*    */   extends ContextualTable<ContextualSubstRule>
/*    */ {
/*    */   private static final long serialVersionUID = 1802216575331243298L;
/*    */   
/*    */   protected ContextualSubTable(OpenTypeFontTableReader openReader, int lookupFlag) {
/* 51 */     super(openReader, lookupFlag);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ContextualSubstRule getMatchingContextRule(GlyphLine line) {
/* 57 */     return super.getMatchingContextRule(line);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int checkIfContextMatch(GlyphLine line, ContextualSubstRule rule) {
/* 63 */     return super.checkIfContextMatch(line, rule);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/ContextualSubTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */