/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public abstract class ContextualTable<T extends ContextualRule>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6482616632143439036L;
/*     */   protected OpenTypeFontTableReader openReader;
/*     */   protected int lookupFlag;
/*     */   
/*     */   protected ContextualTable(OpenTypeFontTableReader openReader, int lookupFlag) {
/*  35 */     this.openReader = openReader;
/*  36 */     this.lookupFlag = lookupFlag;
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
/*     */   public T getMatchingContextRule(GlyphLine line) {
/*  50 */     if (line.idx >= line.end) {
/*  51 */       return null;
/*     */     }
/*     */     
/*  54 */     Glyph g = line.get(line.idx);
/*  55 */     List<T> rules = getSetOfRulesForStartGlyph(g.getCode());
/*  56 */     for (ContextualRule contextualRule : rules) {
/*  57 */       int lastGlyphIndex = checkIfContextMatch(line, (T)contextualRule);
/*  58 */       if (lastGlyphIndex != -1) {
/*  59 */         line.start = line.idx;
/*  60 */         line.end = lastGlyphIndex + 1;
/*  61 */         return (T)contextualRule;
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     return null;
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
/*     */   protected abstract List<T> getSetOfRulesForStartGlyph(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int checkIfContextMatch(GlyphLine line, T rule) {
/*  87 */     OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
/*  88 */     gidx.line = line;
/*  89 */     gidx.idx = line.idx;
/*     */     
/*     */     int j;
/*  92 */     for (j = 1; j < rule.getContextLength(); j++) {
/*  93 */       gidx.nextGlyph(this.openReader, this.lookupFlag);
/*  94 */       if (gidx.glyph == null || !rule.isGlyphMatchesInput(gidx.glyph.getCode(), j)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     boolean isMatch = (j == rule.getContextLength());
/* 100 */     if (isMatch) {
/* 101 */       return gidx.idx;
/*     */     }
/* 103 */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/ContextualTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */