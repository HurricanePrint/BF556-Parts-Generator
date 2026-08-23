/*     */ package com.itextpdf.layout.layout;
/*     */ 
/*     */ import com.itextpdf.layout.renderer.IRenderer;
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
/*     */ public class TextLayoutResult
/*     */   extends MinMaxWidthLayoutResult
/*     */ {
/*     */   protected boolean wordHasBeenSplit;
/*     */   protected boolean splitForcedByNewline;
/*     */   
/*     */   public TextLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer) {
/*  72 */     super(status, occupiedArea, splitRenderer, overflowRenderer);
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
/*     */   public TextLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer, IRenderer cause) {
/*  85 */     super(status, occupiedArea, splitRenderer, overflowRenderer, cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWordHasBeenSplit() {
/*  95 */     return this.wordHasBeenSplit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextLayoutResult setWordHasBeenSplit(boolean wordHasBeenSplit) {
/* 105 */     this.wordHasBeenSplit = wordHasBeenSplit;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSplitForcedByNewline() {
/* 117 */     return this.splitForcedByNewline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextLayoutResult setSplitForcedByNewline(boolean isSplitForcedByNewline) {
/* 128 */     this.splitForcedByNewline = isSplitForcedByNewline;
/* 129 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/TextLayoutResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */