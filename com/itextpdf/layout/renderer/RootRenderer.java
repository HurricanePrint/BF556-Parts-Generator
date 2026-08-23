/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.layout.PositionedLayoutContext;
/*     */ import com.itextpdf.layout.layout.RootLayoutArea;
/*     */ import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
/*     */ import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
/*     */ import com.itextpdf.layout.property.ClearPropertyValue;
/*     */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public abstract class RootRenderer
/*     */   extends AbstractRenderer
/*     */ {
/*     */   protected boolean immediateFlush = true;
/*     */   protected RootLayoutArea currentArea;
/*     */   protected int currentPageNumber;
/*  73 */   protected List<IRenderer> waitingDrawingElements = new ArrayList<>();
/*     */   List<Rectangle> floatRendererAreas;
/*     */   private IRenderer keepWithNextHangingRenderer;
/*     */   private LayoutResult keepWithNextHangingRendererLayoutResult;
/*     */   private MarginsCollapseHandler marginsCollapseHandler;
/*     */   private LayoutArea initialCurrentArea;
/*  79 */   private List<IRenderer> waitingNextPageRenderers = new ArrayList<>();
/*     */   private boolean floatOverflowedCompletely = false;
/*     */   
/*     */   public void addChild(IRenderer renderer) {
/*  83 */     LayoutTaggingHelper taggingHelper = getProperty(108);
/*  84 */     if (taggingHelper != null) {
/*  85 */       LayoutTaggingHelper.addTreeHints(taggingHelper, renderer);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  90 */     int numberOfChildRenderers = this.childRenderers.size();
/*  91 */     int numberOfPositionedChildRenderers = this.positionedRenderers.size();
/*  92 */     super.addChild(renderer);
/*  93 */     List<IRenderer> addedRenderers = new ArrayList<>(1);
/*  94 */     List<IRenderer> addedPositionedRenderers = new ArrayList<>(1);
/*  95 */     while (this.childRenderers.size() > numberOfChildRenderers) {
/*  96 */       addedRenderers.add(this.childRenderers.get(numberOfChildRenderers));
/*  97 */       this.childRenderers.remove(numberOfChildRenderers);
/*     */     } 
/*  99 */     while (this.positionedRenderers.size() > numberOfPositionedChildRenderers) {
/* 100 */       addedPositionedRenderers.add(this.positionedRenderers.get(numberOfPositionedChildRenderers));
/* 101 */       this.positionedRenderers.remove(numberOfPositionedChildRenderers);
/*     */     } 
/*     */     
/* 104 */     boolean marginsCollapsingEnabled = Boolean.TRUE.equals(getPropertyAsBoolean(89));
/* 105 */     if (this.currentArea == null) {
/* 106 */       updateCurrentAndInitialArea((LayoutResult)null);
/* 107 */       if (marginsCollapsingEnabled) {
/* 108 */         this.marginsCollapseHandler = new MarginsCollapseHandler(this, null);
/*     */       }
/*     */     } 
/*     */     
/*     */     int i;
/* 113 */     for (i = 0; this.currentArea != null && i < addedRenderers.size(); i++) {
/* 114 */       RootRendererAreaStateHandler rootRendererStateHandler = new RootRendererAreaStateHandler();
/*     */       
/* 116 */       renderer = addedRenderers.get(i);
/* 117 */       boolean rendererIsFloat = FloatingHelper.isRendererFloating(renderer);
/* 118 */       boolean clearanceOverflowsToNextPage = FloatingHelper.isClearanceApplied(this.waitingNextPageRenderers, (ClearPropertyValue)renderer.getProperty(100));
/* 119 */       if (rendererIsFloat && (this.floatOverflowedCompletely || clearanceOverflowsToNextPage)) {
/* 120 */         this.waitingNextPageRenderers.add(renderer);
/* 121 */         this.floatOverflowedCompletely = true;
/*     */       }
/*     */       else {
/*     */         
/* 125 */         processWaitingKeepWithNextElement(renderer);
/*     */         
/* 127 */         List<IRenderer> resultRenderers = new ArrayList<>();
/* 128 */         LayoutResult result = null;
/*     */         
/* 130 */         MarginsCollapseInfo childMarginsInfo = null;
/* 131 */         if (marginsCollapsingEnabled && this.currentArea != null && renderer != null) {
/* 132 */           childMarginsInfo = this.marginsCollapseHandler.startChildMarginsHandling(renderer, this.currentArea.getBBox());
/*     */         }
/* 134 */         while (clearanceOverflowsToNextPage || (this.currentArea != null && renderer != null && (
/*     */           
/* 136 */           result = renderer.setParent(this).layout(new LayoutContext(this.currentArea.clone(), childMarginsInfo, this.floatRendererAreas))).getStatus() != 1)) {
/* 137 */           boolean currentAreaNeedsToBeUpdated = false;
/* 138 */           if (clearanceOverflowsToNextPage) {
/* 139 */             result = new LayoutResult(3, null, null, renderer);
/* 140 */             currentAreaNeedsToBeUpdated = true;
/*     */           } 
/* 142 */           if (result.getStatus() == 2) {
/* 143 */             if (rendererIsFloat) {
/* 144 */               this.waitingNextPageRenderers.add(result.getOverflowRenderer());
/*     */               break;
/*     */             } 
/* 147 */             processRenderer(result.getSplitRenderer(), resultRenderers);
/* 148 */             if (!rootRendererStateHandler.attemptGoForwardToStoredNextState(this)) {
/* 149 */               currentAreaNeedsToBeUpdated = true;
/*     */             }
/*     */           }
/* 152 */           else if (result.getStatus() == 3 && !clearanceOverflowsToNextPage) {
/* 153 */             if (result.getOverflowRenderer() instanceof ImageRenderer) {
/* 154 */               float imgHeight = ((ImageRenderer)result.getOverflowRenderer()).getOccupiedArea().getBBox().getHeight();
/* 155 */               if (!this.floatRendererAreas.isEmpty() || (this.currentArea
/* 156 */                 .getBBox().getHeight() < imgHeight && !this.currentArea.isEmptyArea())) {
/* 157 */                 if (rendererIsFloat) {
/* 158 */                   this.waitingNextPageRenderers.add(result.getOverflowRenderer());
/* 159 */                   this.floatOverflowedCompletely = true;
/*     */                   break;
/*     */                 } 
/* 162 */                 currentAreaNeedsToBeUpdated = true;
/*     */               } else {
/* 164 */                 ((ImageRenderer)result.getOverflowRenderer()).autoScale((LayoutArea)this.currentArea);
/* 165 */                 result.getOverflowRenderer().setProperty(26, Boolean.valueOf(true));
/* 166 */                 Logger logger = LoggerFactory.getLogger(RootRenderer.class);
/* 167 */                 logger.warn(MessageFormatUtil.format("Element does not fit current area. {0}", new Object[] { "" }));
/*     */               }
/*     */             
/* 170 */             } else if (this.currentArea.isEmptyArea() && result.getAreaBreak() == null) {
/* 171 */               if (Boolean.TRUE.equals(result.getOverflowRenderer().getModelElement().getProperty(32))) {
/* 172 */                 result.getOverflowRenderer().getModelElement().setProperty(32, Boolean.valueOf(false));
/* 173 */                 Logger logger = LoggerFactory.getLogger(RootRenderer.class);
/* 174 */                 logger.warn(MessageFormatUtil.format("Element does not fit current area. {0}", new Object[] { "KeepTogether property will be ignored." }));
/* 175 */                 if (!rendererIsFloat) {
/* 176 */                   rootRendererStateHandler.attemptGoBackToStoredPreviousStateAndStoreNextState(this);
/*     */                 }
/* 178 */               } else if (null != result.getCauseOfNothing() && Boolean.TRUE.equals(result.getCauseOfNothing().getProperty(32))) {
/*     */                 
/* 180 */                 IRenderer theDeepestKeptTogether = result.getCauseOfNothing();
/*     */                 
/* 182 */                 while (null == theDeepestKeptTogether.getModelElement() || null == theDeepestKeptTogether.getModelElement().getOwnProperty(32)) {
/* 183 */                   IRenderer parent = ((AbstractRenderer)theDeepestKeptTogether).parent;
/* 184 */                   if (parent == null) {
/*     */                     break;
/*     */                   }
/* 187 */                   theDeepestKeptTogether = parent;
/*     */                 } 
/* 189 */                 theDeepestKeptTogether.getModelElement().setProperty(32, Boolean.valueOf(false));
/* 190 */                 Logger logger = LoggerFactory.getLogger(RootRenderer.class);
/* 191 */                 logger.warn(MessageFormatUtil.format("Element does not fit current area. {0}", new Object[] { "KeepTogether property of inner element will be ignored." }));
/* 192 */                 if (!rendererIsFloat) {
/* 193 */                   rootRendererStateHandler.attemptGoBackToStoredPreviousStateAndStoreNextState(this);
/*     */                 }
/* 195 */               } else if (!Boolean.TRUE.equals(renderer.getProperty(26))) {
/* 196 */                 result.getOverflowRenderer().setProperty(26, Boolean.valueOf(true));
/* 197 */                 Logger logger = LoggerFactory.getLogger(RootRenderer.class);
/* 198 */                 logger.warn(MessageFormatUtil.format("Element does not fit current area. {0}", new Object[] { "" }));
/*     */               } else {
/*     */                 assert false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } else {
/* 209 */               rootRendererStateHandler.storePreviousState(this);
/* 210 */               if (!rootRendererStateHandler.attemptGoForwardToStoredNextState(this)) {
/* 211 */                 if (rendererIsFloat) {
/* 212 */                   this.waitingNextPageRenderers.add(result.getOverflowRenderer());
/* 213 */                   this.floatOverflowedCompletely = true;
/*     */                   break;
/*     */                 } 
/* 216 */                 currentAreaNeedsToBeUpdated = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 222 */           renderer = result.getOverflowRenderer();
/*     */           
/* 224 */           if (marginsCollapsingEnabled) {
/* 225 */             this.marginsCollapseHandler.endChildMarginsHandling(this.currentArea.getBBox());
/*     */           }
/* 227 */           if (currentAreaNeedsToBeUpdated) {
/* 228 */             updateCurrentAndInitialArea(result);
/*     */           }
/* 230 */           if (marginsCollapsingEnabled) {
/* 231 */             this.marginsCollapseHandler = new MarginsCollapseHandler(this, null);
/* 232 */             childMarginsInfo = this.marginsCollapseHandler.startChildMarginsHandling(renderer, this.currentArea.getBBox());
/*     */           } 
/*     */ 
/*     */           
/* 236 */           clearanceOverflowsToNextPage = (clearanceOverflowsToNextPage && FloatingHelper.isClearanceApplied(this.waitingNextPageRenderers, (ClearPropertyValue)renderer.getProperty(100)));
/*     */         } 
/* 238 */         if (marginsCollapsingEnabled) {
/* 239 */           this.marginsCollapseHandler.endChildMarginsHandling(this.currentArea.getBBox());
/*     */         }
/*     */         
/* 242 */         if (null != result && null != result.getSplitRenderer()) {
/* 243 */           renderer = result.getSplitRenderer();
/*     */         }
/*     */ 
/*     */         
/* 247 */         if (renderer != null && result != null) {
/* 248 */           if (Boolean.TRUE.equals(renderer.getProperty(81))) {
/* 249 */             if (Boolean.TRUE.equals(renderer.getProperty(26))) {
/* 250 */               Logger logger = LoggerFactory.getLogger(RootRenderer.class);
/* 251 */               logger.warn("Element was placed in a forced way. Keep with next property will be ignored");
/* 252 */               shrinkCurrentAreaAndProcessRenderer(renderer, resultRenderers, result);
/*     */             } else {
/* 254 */               this.keepWithNextHangingRenderer = renderer;
/* 255 */               this.keepWithNextHangingRendererLayoutResult = result;
/*     */             } 
/* 257 */           } else if (result.getStatus() != 3) {
/* 258 */             shrinkCurrentAreaAndProcessRenderer(renderer, resultRenderers, result);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 263 */     for (i = 0; i < addedPositionedRenderers.size(); i++) {
/* 264 */       LayoutArea layoutArea; this.positionedRenderers.add(addedPositionedRenderers.get(i));
/* 265 */       renderer = this.positionedRenderers.get(this.positionedRenderers.size() - 1);
/* 266 */       Integer positionedPageNumber = (Integer)renderer.getProperty(51);
/* 267 */       if (positionedPageNumber == null) {
/* 268 */         positionedPageNumber = Integer.valueOf(this.currentPageNumber);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       if (Integer.valueOf(3).equals(renderer.getProperty(52)) && AbstractRenderer.noAbsolutePositionInfo(renderer)) {
/* 275 */         layoutArea = new LayoutArea(positionedPageNumber.intValue(), this.currentArea.getBBox().clone());
/*     */       } else {
/* 277 */         layoutArea = new LayoutArea(positionedPageNumber.intValue(), this.initialCurrentArea.getBBox().clone());
/*     */       } 
/* 279 */       Rectangle fullBbox = layoutArea.getBBox().clone();
/* 280 */       preparePositionedRendererAndAreaForLayout(renderer, fullBbox, layoutArea.getBBox());
/* 281 */       renderer.layout((LayoutContext)new PositionedLayoutContext(new LayoutArea(layoutArea.getPageNumber(), fullBbox), layoutArea));
/*     */       
/* 283 */       if (this.immediateFlush) {
/* 284 */         flushSingleRenderer(renderer);
/* 285 */         this.positionedRenderers.remove(this.positionedRenderers.size() - 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 296 */     for (IRenderer resultRenderer : this.childRenderers) {
/* 297 */       flushSingleRenderer(resultRenderer);
/*     */     }
/* 299 */     for (IRenderer resultRenderer : this.positionedRenderers) {
/* 300 */       flushSingleRenderer(resultRenderer);
/*     */     }
/* 302 */     this.childRenderers.clear();
/* 303 */     this.positionedRenderers.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 312 */     addAllWaitingNextPageRenderers();
/* 313 */     if (this.keepWithNextHangingRenderer != null) {
/* 314 */       this.keepWithNextHangingRenderer.setProperty(81, Boolean.valueOf(false));
/* 315 */       IRenderer rendererToBeAdded = this.keepWithNextHangingRenderer;
/* 316 */       this.keepWithNextHangingRenderer = null;
/* 317 */       addChild(rendererToBeAdded);
/*     */     } 
/* 319 */     if (!this.immediateFlush) {
/* 320 */       flush();
/*     */     }
/* 322 */     flushWaitingDrawingElements(true);
/* 323 */     LayoutTaggingHelper taggingHelper = getProperty(108);
/* 324 */     if (taggingHelper != null) {
/* 325 */       taggingHelper.releaseAllHints();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutResult layout(LayoutContext layoutContext) {
/* 334 */     throw new IllegalStateException("Layout is not supported for root renderers.");
/*     */   }
/*     */   
/*     */   public LayoutArea getCurrentArea() {
/* 338 */     if (this.currentArea == null) {
/* 339 */       updateCurrentAndInitialArea((LayoutResult)null);
/*     */     }
/* 341 */     return (LayoutArea)this.currentArea;
/*     */   }
/*     */   
/*     */   protected abstract void flushSingleRenderer(IRenderer paramIRenderer);
/*     */   
/*     */   protected abstract LayoutArea updateCurrentArea(LayoutResult paramLayoutResult);
/*     */   
/*     */   protected void shrinkCurrentAreaAndProcessRenderer(IRenderer renderer, List<IRenderer> resultRenderers, LayoutResult result) {
/* 349 */     if (this.currentArea != null) {
/* 350 */       float resultRendererHeight = result.getOccupiedArea().getBBox().getHeight();
/* 351 */       this.currentArea.getBBox().setHeight(this.currentArea.getBBox().getHeight() - resultRendererHeight);
/* 352 */       if (this.currentArea.isEmptyArea() && (resultRendererHeight > 0.0F || FloatingHelper.isRendererFloating(renderer))) {
/* 353 */         this.currentArea.setEmptyArea(false);
/*     */       }
/* 355 */       processRenderer(renderer, resultRenderers);
/*     */     } 
/*     */     
/* 358 */     if (!this.immediateFlush) {
/* 359 */       this.childRenderers.addAll(resultRenderers);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void flushWaitingDrawingElements() {
/* 364 */     flushWaitingDrawingElements(true);
/*     */   }
/*     */   
/*     */   void flushWaitingDrawingElements(boolean force) {
/* 368 */     Set<IRenderer> flushedElements = new HashSet<>();
/* 369 */     for (int i = 0; i < this.waitingDrawingElements.size(); i++) {
/*     */       
/* 371 */       IRenderer waitingDrawingElement = this.waitingDrawingElements.get(i);
/*     */       
/* 373 */       if (force || (null != waitingDrawingElement.getOccupiedArea() && waitingDrawingElement.getOccupiedArea().getPageNumber() < this.currentArea.getPageNumber())) {
/* 374 */         flushSingleRenderer(waitingDrawingElement);
/* 375 */         flushedElements.add(waitingDrawingElement);
/* 376 */       } else if (null == waitingDrawingElement.getOccupiedArea()) {
/* 377 */         flushedElements.add(waitingDrawingElement);
/*     */       } 
/*     */     } 
/* 380 */     this.waitingDrawingElements.removeAll(flushedElements);
/*     */   }
/*     */   
/*     */   private void processRenderer(IRenderer renderer, List<IRenderer> resultRenderers) {
/* 384 */     alignChildHorizontally(renderer, this.currentArea.getBBox());
/* 385 */     if (this.immediateFlush) {
/* 386 */       flushSingleRenderer(renderer);
/*     */     } else {
/* 388 */       resultRenderers.add(renderer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processWaitingKeepWithNextElement(IRenderer renderer) {
/* 393 */     if (this.keepWithNextHangingRenderer != null) {
/* 394 */       LayoutArea rest = this.currentArea.clone();
/* 395 */       rest.getBBox().setHeight(rest.getBBox().getHeight() - this.keepWithNextHangingRendererLayoutResult.getOccupiedArea().getBBox().getHeight());
/* 396 */       boolean ableToProcessKeepWithNext = false;
/* 397 */       if (renderer.setParent(this).layout(new LayoutContext(rest)).getStatus() != 3) {
/*     */         
/* 399 */         shrinkCurrentAreaAndProcessRenderer(this.keepWithNextHangingRenderer, new ArrayList<>(), this.keepWithNextHangingRendererLayoutResult);
/* 400 */         ableToProcessKeepWithNext = true;
/*     */       } else {
/* 402 */         float originalElementHeight = this.keepWithNextHangingRendererLayoutResult.getOccupiedArea().getBBox().getHeight();
/* 403 */         List<Float> trySplitHeightPoints = new ArrayList<>();
/* 404 */         float delta = 35.0F; int i;
/* 405 */         for (i = 1; i <= 5 && originalElementHeight - delta * i > originalElementHeight / 2.0F; i++) {
/* 406 */           trySplitHeightPoints.add(Float.valueOf(originalElementHeight - delta * i));
/*     */         }
/* 408 */         for (i = 0; i < trySplitHeightPoints.size() && !ableToProcessKeepWithNext; i++) {
/* 409 */           float curElementSplitHeight = ((Float)trySplitHeightPoints.get(i)).floatValue();
/* 410 */           RootLayoutArea firstElementSplitLayoutArea = (RootLayoutArea)this.currentArea.clone();
/* 411 */           firstElementSplitLayoutArea.getBBox().setHeight(curElementSplitHeight)
/* 412 */             .moveUp(this.currentArea.getBBox().getHeight() - curElementSplitHeight);
/* 413 */           LayoutResult firstElementSplitLayoutResult = this.keepWithNextHangingRenderer.setParent(this).layout(new LayoutContext(firstElementSplitLayoutArea.clone()));
/* 414 */           if (firstElementSplitLayoutResult.getStatus() == 2) {
/* 415 */             RootLayoutArea storedArea = this.currentArea;
/* 416 */             updateCurrentAndInitialArea(firstElementSplitLayoutResult);
/* 417 */             LayoutResult firstElementOverflowLayoutResult = firstElementSplitLayoutResult.getOverflowRenderer().layout(new LayoutContext(this.currentArea.clone()));
/* 418 */             if (firstElementOverflowLayoutResult.getStatus() == 1) {
/* 419 */               LayoutArea secondElementLayoutArea = this.currentArea.clone();
/* 420 */               secondElementLayoutArea.getBBox().setHeight(secondElementLayoutArea.getBBox().getHeight() - firstElementOverflowLayoutResult.getOccupiedArea().getBBox().getHeight());
/* 421 */               LayoutResult secondElementLayoutResult = renderer.setParent(this).layout(new LayoutContext(secondElementLayoutArea));
/* 422 */               if (secondElementLayoutResult.getStatus() != 3) {
/* 423 */                 ableToProcessKeepWithNext = true;
/*     */                 
/* 425 */                 this.currentArea = firstElementSplitLayoutArea;
/* 426 */                 this.currentPageNumber = firstElementSplitLayoutArea.getPageNumber();
/* 427 */                 shrinkCurrentAreaAndProcessRenderer(firstElementSplitLayoutResult.getSplitRenderer(), new ArrayList<>(), firstElementSplitLayoutResult);
/* 428 */                 updateCurrentAndInitialArea(firstElementSplitLayoutResult);
/* 429 */                 shrinkCurrentAreaAndProcessRenderer(firstElementSplitLayoutResult.getOverflowRenderer(), new ArrayList<>(), firstElementOverflowLayoutResult);
/*     */               } 
/*     */             } 
/* 432 */             if (!ableToProcessKeepWithNext) {
/* 433 */               this.currentArea = storedArea;
/* 434 */               this.currentPageNumber = storedArea.getPageNumber();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 439 */       if (!ableToProcessKeepWithNext && !this.currentArea.isEmptyArea()) {
/* 440 */         RootLayoutArea storedArea = this.currentArea;
/* 441 */         updateCurrentAndInitialArea((LayoutResult)null);
/* 442 */         LayoutResult firstElementLayoutResult = this.keepWithNextHangingRenderer.setParent(this).layout(new LayoutContext(this.currentArea.clone()));
/* 443 */         if (firstElementLayoutResult.getStatus() == 1) {
/* 444 */           LayoutArea secondElementLayoutArea = this.currentArea.clone();
/* 445 */           secondElementLayoutArea.getBBox().setHeight(secondElementLayoutArea.getBBox().getHeight() - firstElementLayoutResult.getOccupiedArea().getBBox().getHeight());
/* 446 */           LayoutResult secondElementLayoutResult = renderer.setParent(this).layout(new LayoutContext(secondElementLayoutArea));
/* 447 */           if (secondElementLayoutResult.getStatus() != 3) {
/* 448 */             ableToProcessKeepWithNext = true;
/* 449 */             shrinkCurrentAreaAndProcessRenderer(this.keepWithNextHangingRenderer, new ArrayList<>(), this.keepWithNextHangingRendererLayoutResult);
/*     */           } 
/*     */         } 
/* 452 */         if (!ableToProcessKeepWithNext) {
/* 453 */           this.currentArea = storedArea;
/* 454 */           this.currentPageNumber = storedArea.getPageNumber();
/*     */         } 
/*     */       } 
/* 457 */       if (!ableToProcessKeepWithNext) {
/* 458 */         Logger logger = LoggerFactory.getLogger(RootRenderer.class);
/* 459 */         logger.warn("The renderer was not able to process keep with next property properly");
/* 460 */         shrinkCurrentAreaAndProcessRenderer(this.keepWithNextHangingRenderer, new ArrayList<>(), this.keepWithNextHangingRendererLayoutResult);
/*     */       } 
/* 462 */       this.keepWithNextHangingRenderer = null;
/* 463 */       this.keepWithNextHangingRendererLayoutResult = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateCurrentAndInitialArea(LayoutResult overflowResult) {
/* 468 */     this.floatRendererAreas = new ArrayList<>();
/* 469 */     updateCurrentArea(overflowResult);
/* 470 */     this.initialCurrentArea = (this.currentArea == null) ? null : this.currentArea.clone();
/*     */     
/* 472 */     addWaitingNextPageRenderers();
/*     */   }
/*     */   
/*     */   private void addAllWaitingNextPageRenderers() {
/* 476 */     boolean marginsCollapsingEnabled = Boolean.TRUE.equals(getPropertyAsBoolean(89));
/* 477 */     while (!this.waitingNextPageRenderers.isEmpty()) {
/* 478 */       if (marginsCollapsingEnabled) {
/* 479 */         this.marginsCollapseHandler = new MarginsCollapseHandler(this, null);
/*     */       }
/* 481 */       updateCurrentAndInitialArea((LayoutResult)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addWaitingNextPageRenderers() {
/* 486 */     this.floatOverflowedCompletely = false;
/* 487 */     List<IRenderer> waitingFloatRenderers = new ArrayList<>(this.waitingNextPageRenderers);
/* 488 */     this.waitingNextPageRenderers.clear();
/* 489 */     for (IRenderer renderer : waitingFloatRenderers)
/* 490 */       addChild(renderer); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/RootRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */