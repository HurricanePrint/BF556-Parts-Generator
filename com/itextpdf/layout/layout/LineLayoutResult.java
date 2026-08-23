/*     */ package com.itextpdf.layout.layout;
/*     */ 
/*     */ import com.itextpdf.layout.renderer.IRenderer;
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
/*     */ public class LineLayoutResult
/*     */   extends MinMaxWidthLayoutResult
/*     */ {
/*     */   protected boolean splitForcedByNewline;
/*     */   private List<IRenderer> floatsOverflowedToNextPage;
/*     */   
/*     */   public LineLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer) {
/*  70 */     super(status, occupiedArea, splitRenderer, overflowRenderer);
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
/*     */   public LineLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer, IRenderer cause) {
/*  83 */     super(status, occupiedArea, splitRenderer, overflowRenderer, cause);
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
/*  94 */     return this.splitForcedByNewline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineLayoutResult setSplitForcedByNewline(boolean isSplitForcedByNewline) {
/* 105 */     this.splitForcedByNewline = isSplitForcedByNewline;
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public List<IRenderer> getFloatsOverflowedToNextPage() {
/* 110 */     return this.floatsOverflowedToNextPage;
/*     */   }
/*     */   
/*     */   public void setFloatsOverflowedToNextPage(List<IRenderer> floatsOverflowedToNextPage) {
/* 114 */     this.floatsOverflowedToNextPage = floatsOverflowedToNextPage;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/LineLayoutResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */