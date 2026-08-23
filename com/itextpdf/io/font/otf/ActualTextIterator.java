/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import java.util.Iterator;
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
/*     */ public class ActualTextIterator
/*     */   implements Iterator<GlyphLine.GlyphLinePart>
/*     */ {
/*     */   private GlyphLine glyphLine;
/*     */   private int pos;
/*     */   
/*     */   public ActualTextIterator(GlyphLine glyphLine) {
/*  55 */     this.glyphLine = glyphLine;
/*  56 */     this.pos = glyphLine.start;
/*     */   }
/*     */   
/*     */   public ActualTextIterator(GlyphLine glyphLine, int start, int end) {
/*  60 */     this(new GlyphLine(glyphLine.glyphs, glyphLine.actualText, start, end));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  67 */     return (this.pos < this.glyphLine.end);
/*     */   }
/*     */ 
/*     */   
/*     */   public GlyphLine.GlyphLinePart next() {
/*  72 */     if (this.glyphLine.actualText == null) {
/*  73 */       GlyphLine.GlyphLinePart result = new GlyphLine.GlyphLinePart(this.pos, this.glyphLine.end, null);
/*  74 */       this.pos = this.glyphLine.end;
/*  75 */       return result;
/*     */     } 
/*  77 */     GlyphLine.GlyphLinePart currentResult = nextGlyphLinePart(this.pos);
/*  78 */     if (currentResult == null) {
/*  79 */       return null;
/*     */     }
/*  81 */     this.pos = currentResult.end;
/*     */     
/*  83 */     if (!glyphLinePartNeedsActualText(currentResult)) {
/*  84 */       currentResult.actualText = null;
/*     */       
/*  86 */       while (this.pos < this.glyphLine.end) {
/*  87 */         GlyphLine.GlyphLinePart nextResult = nextGlyphLinePart(this.pos);
/*  88 */         if (nextResult != null && !glyphLinePartNeedsActualText(nextResult)) {
/*  89 */           currentResult.end = nextResult.end;
/*  90 */           this.pos = nextResult.end;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  96 */     return currentResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/* 102 */     throw new IllegalStateException("Operation not supported");
/*     */   }
/*     */   
/*     */   private GlyphLine.GlyphLinePart nextGlyphLinePart(int pos) {
/* 106 */     if (pos >= this.glyphLine.end) {
/* 107 */       return null;
/*     */     }
/* 109 */     int startPos = pos;
/* 110 */     GlyphLine.ActualText startActualText = this.glyphLine.actualText.get(pos);
/* 111 */     while (pos < this.glyphLine.end && this.glyphLine.actualText.get(pos) == startActualText) {
/* 112 */       pos++;
/*     */     }
/* 114 */     return new GlyphLine.GlyphLinePart(startPos, pos, (startActualText != null) ? startActualText.value : null);
/*     */   }
/*     */   
/*     */   private boolean glyphLinePartNeedsActualText(GlyphLine.GlyphLinePart glyphLinePart) {
/* 118 */     if (glyphLinePart.actualText == null) {
/* 119 */       return false;
/*     */     }
/* 121 */     boolean needsActualText = false;
/* 122 */     StringBuilder toUnicodeMapResult = new StringBuilder();
/* 123 */     for (int i = glyphLinePart.start; i < glyphLinePart.end; i++) {
/* 124 */       Glyph currentGlyph = this.glyphLine.glyphs.get(i);
/* 125 */       if (!currentGlyph.hasValidUnicode()) {
/* 126 */         needsActualText = true;
/*     */         break;
/*     */       } 
/* 129 */       toUnicodeMapResult.append(TextUtil.convertFromUtf32(currentGlyph.getUnicode()));
/*     */     } 
/*     */     
/* 132 */     return (needsActualText || !toUnicodeMapResult.toString().equals(glyphLinePart.actualText));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/ActualTextIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */