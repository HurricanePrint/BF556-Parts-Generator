/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public abstract class OpenTableLookup
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8381791136767127636L;
/*     */   protected int lookupFlag;
/*     */   protected int[] subTableLocations;
/*     */   protected OpenTypeFontTableReader openReader;
/*     */   
/*     */   protected OpenTableLookup(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) {
/*  56 */     this.lookupFlag = lookupFlag;
/*  57 */     this.subTableLocations = subTableLocations;
/*  58 */     this.openReader = openReader;
/*     */   }
/*     */   
/*     */   public int getLookupFlag() {
/*  62 */     return this.lookupFlag;
/*     */   }
/*     */   
/*     */   public abstract boolean transformOne(GlyphLine paramGlyphLine);
/*     */   
/*     */   public boolean transformLine(GlyphLine line) {
/*  68 */     boolean changed = false;
/*  69 */     line.idx = line.start;
/*  70 */     while (line.idx < line.end && line.idx >= line.start) {
/*  71 */       changed = (transformOne(line) || changed);
/*     */     }
/*  73 */     return changed;
/*     */   }
/*     */   
/*     */   public boolean hasSubstitution(int index) {
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   protected void readSubTables() throws IOException {
/*  81 */     for (int subTableLocation : this.subTableLocations)
/*  82 */       readSubTable(subTableLocation); 
/*     */   }
/*     */   
/*     */   protected abstract void readSubTable(int paramInt) throws IOException;
/*     */   
/*     */   public static class GlyphIndexer
/*     */   {
/*     */     public GlyphLine line;
/*     */     public Glyph glyph;
/*     */     public int idx;
/*     */     
/*     */     public void nextGlyph(OpenTypeFontTableReader openReader, int lookupFlag) {
/*  94 */       this.glyph = null;
/*  95 */       while (++this.idx < this.line.end) {
/*  96 */         Glyph g = this.line.get(this.idx);
/*  97 */         if (!openReader.isSkip(g.getCode(), lookupFlag)) {
/*  98 */           this.glyph = g;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void previousGlyph(OpenTypeFontTableReader openReader, int lookupFlag) {
/* 105 */       this.glyph = null;
/* 106 */       while (--this.idx >= this.line.start) {
/* 107 */         Glyph g = this.line.get(this.idx);
/* 108 */         if (!openReader.isSkip(g.getCode(), lookupFlag)) {
/* 109 */           this.glyph = g;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/OpenTableLookup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */