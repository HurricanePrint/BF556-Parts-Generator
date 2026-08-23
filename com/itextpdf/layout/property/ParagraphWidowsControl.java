/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.layout.renderer.ParagraphRenderer;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public class ParagraphWidowsControl
/*     */ {
/*     */   private int minWidows;
/*     */   private int maxLinesToMove;
/*     */   private boolean overflowOnWidowsViolation;
/*     */   
/*     */   public ParagraphWidowsControl(int minWidows, int maxLinesToMove, boolean overflowParagraphOnViolation) {
/*  51 */     this.minWidows = minWidows;
/*  52 */     this.maxLinesToMove = maxLinesToMove;
/*  53 */     this.overflowOnWidowsViolation = overflowParagraphOnViolation;
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
/*     */   
/*     */   public ParagraphWidowsControl setMinAllowedWidows(int minWidows, int maxLinesToMove, boolean overflowParagraphOnViolation) {
/*  68 */     this.minWidows = minWidows;
/*  69 */     this.maxLinesToMove = maxLinesToMove;
/*  70 */     this.overflowOnWidowsViolation = overflowParagraphOnViolation;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinWidows() {
/*  80 */     return this.minWidows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLinesToMove() {
/*  90 */     return this.maxLinesToMove;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOverflowOnWidowsViolation() {
/* 101 */     return this.overflowOnWidowsViolation;
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
/*     */   public void handleViolatedWidows(ParagraphRenderer widowsRenderer, String message) {
/* 113 */     Logger logger = LoggerFactory.getLogger(ParagraphWidowsControl.class);
/* 114 */     if (widowsRenderer.getOccupiedArea() != null && widowsRenderer.getLines() != null) {
/* 115 */       int pageNumber = widowsRenderer.getOccupiedArea().getPageNumber();
/* 116 */       String warnText = MessageFormatUtil.format("Widows constraint violated for paragraph split at page {0}. Min number of widows: {1}; actual: {2}.\nComment: {3}", new Object[] {
/* 117 */             Integer.valueOf(pageNumber), Integer.valueOf(this.minWidows), Integer.valueOf(widowsRenderer.getLines().size()), message });
/* 118 */       logger.warn(warnText);
/*     */     } else {
/* 120 */       logger.warn("Premature call of handleViolation method.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/ParagraphWidowsControl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */