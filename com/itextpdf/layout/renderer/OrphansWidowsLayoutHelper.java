/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
/*     */ import com.itextpdf.layout.property.ParagraphOrphansControl;
/*     */ import com.itextpdf.layout.property.ParagraphWidowsControl;
/*     */ import java.util.ArrayList;
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
/*     */ class OrphansWidowsLayoutHelper
/*     */ {
/*     */   static LayoutResult orphansWidowsAwareLayout(ParagraphRenderer renderer, LayoutContext context, ParagraphOrphansControl orphansControl, ParagraphWidowsControl widowsControl) {
/*  41 */     OrphansWidowsLayoutAttempt layoutAttempt = attemptLayout(renderer, context, context.getArea().clone());
/*     */     
/*  43 */     if (context.isClippedHeight() || renderer.isPositioned() || layoutAttempt.attemptResult
/*  44 */       .getStatus() != 2 || layoutAttempt.attemptResult.getSplitRenderer() == null) {
/*  45 */       return handleAttemptAsSuccessful(layoutAttempt, context);
/*     */     }
/*     */     
/*  48 */     ParagraphRenderer splitRenderer = (ParagraphRenderer)layoutAttempt.attemptResult.getSplitRenderer();
/*     */     
/*  50 */     boolean orphansViolation = (orphansControl != null && splitRenderer != null && splitRenderer.getLines().size() < orphansControl.getMinOrphans() && !renderer.isFirstOnRootArea());
/*  51 */     boolean forcedPlacement = Boolean.TRUE.equals(renderer.getPropertyAsBoolean(26));
/*  52 */     if (orphansViolation && forcedPlacement) {
/*  53 */       orphansControl.handleViolatedOrphans(splitRenderer, "Ignored orphans constraint due to forced placement.");
/*     */     }
/*     */     
/*  56 */     if (orphansViolation && !forcedPlacement) {
/*  57 */       layoutAttempt = null;
/*  58 */     } else if (widowsControl != null && splitRenderer != null && layoutAttempt.attemptResult.getOverflowRenderer() != null) {
/*  59 */       ParagraphRenderer overflowRenderer = (ParagraphRenderer)layoutAttempt.attemptResult.getOverflowRenderer();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  64 */       int simulationHeight = 3500;
/*     */       
/*  66 */       LayoutArea simulationArea = new LayoutArea(context.getArea().getPageNumber(), context.getArea().getBBox().clone().setHeight(simulationHeight));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       LayoutContext simulationContext = new LayoutContext(simulationArea);
/*     */       
/*  76 */       LayoutResult simulationResult = overflowRenderer.directLayout(simulationContext);
/*     */       
/*  78 */       if (simulationResult.getStatus() == 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  86 */         int extraWidows = widowsControl.getMinWidows() - overflowRenderer.getLines().size();
/*  87 */         if (extraWidows > 0) {
/*  88 */           int extraLinesToMove = (orphansControl != null) ? Math.max(orphansControl.getMinOrphans(), 1) : 1;
/*  89 */           if (extraWidows <= widowsControl.getMaxLinesToMove() && splitRenderer.getLines().size() - extraWidows >= extraLinesToMove) {
/*  90 */             LineRenderer lastLine = splitRenderer.getLines().get(splitRenderer.getLines().size() - 1);
/*  91 */             LineRenderer lastLineToLeave = splitRenderer.getLines().get(splitRenderer.getLines().size() - extraWidows - 1);
/*  92 */             float d = lastLineToLeave.getOccupiedArea().getBBox().getY() - lastLine.getOccupiedArea().getBBox().getY() - 1.0E-4F;
/*     */             
/*  94 */             Rectangle smallerBBox = new Rectangle(context.getArea().getBBox());
/*  95 */             smallerBBox.decreaseHeight(d);
/*  96 */             smallerBBox.moveUp(d);
/*     */             
/*  98 */             LayoutArea smallerAvailableArea = new LayoutArea(context.getArea().getPageNumber(), smallerBBox);
/*     */             
/* 100 */             layoutAttempt = attemptLayout(renderer, context, smallerAvailableArea);
/*     */           }
/* 102 */           else if (forcedPlacement || renderer.isFirstOnRootArea() || !widowsControl.isOverflowOnWidowsViolation()) {
/* 103 */             if (forcedPlacement) {
/* 104 */               widowsControl.handleViolatedWidows(overflowRenderer, "forced placement");
/*     */             } else {
/* 106 */               widowsControl.handleViolatedWidows(overflowRenderer, "inability to fix it");
/*     */             } 
/*     */           } else {
/* 109 */             layoutAttempt = null;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (layoutAttempt != null) {
/* 117 */       return handleAttemptAsSuccessful(layoutAttempt, context);
/*     */     }
/* 119 */     return new LayoutResult(3, null, null, renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   private static OrphansWidowsLayoutAttempt attemptLayout(ParagraphRenderer renderer, LayoutContext originalContext, LayoutArea attemptArea) {
/* 124 */     OrphansWidowsLayoutAttempt attemptResult = new OrphansWidowsLayoutAttempt();
/*     */     
/* 126 */     MarginsCollapseInfo copiedMarginsCollapseInfo = null;
/* 127 */     if (originalContext.getMarginsCollapseInfo() != null) {
/* 128 */       copiedMarginsCollapseInfo = MarginsCollapseInfo.createDeepCopy(originalContext.getMarginsCollapseInfo());
/*     */     }
/* 130 */     ArrayList<Rectangle> attemptFloatRectsList = new ArrayList<>(originalContext.getFloatRendererAreas());
/* 131 */     LayoutContext attemptContext = new LayoutContext(attemptArea, copiedMarginsCollapseInfo, attemptFloatRectsList, originalContext.isClippedHeight());
/*     */     
/* 133 */     attemptResult.attemptContext = attemptContext;
/* 134 */     attemptResult.attemptResult = renderer.directLayout(attemptContext);
/* 135 */     return attemptResult;
/*     */   }
/*     */   
/*     */   private static LayoutResult handleAttemptAsSuccessful(OrphansWidowsLayoutAttempt attemptResult, LayoutContext originalContext) {
/* 139 */     originalContext.getFloatRendererAreas().clear();
/* 140 */     originalContext.getFloatRendererAreas().addAll(attemptResult.attemptContext.getFloatRendererAreas());
/* 141 */     if (originalContext.getMarginsCollapseInfo() != null) {
/* 142 */       MarginsCollapseInfo.updateFromCopy(originalContext.getMarginsCollapseInfo(), attemptResult.attemptContext.getMarginsCollapseInfo());
/*     */     }
/* 144 */     return attemptResult.attemptResult;
/*     */   }
/*     */   
/*     */   private static class OrphansWidowsLayoutAttempt {
/*     */     LayoutContext attemptContext;
/*     */     LayoutResult attemptResult;
/*     */     
/*     */     private OrphansWidowsLayoutAttempt() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/OrphansWidowsLayoutHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */