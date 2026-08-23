/*     */ package com.itextpdf.layout.margincollapse;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ import com.itextpdf.layout.property.FloatPropertyValue;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.renderer.AbstractRenderer;
/*     */ import com.itextpdf.layout.renderer.BlockFormattingContextUtil;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MarginsCollapseHandler
/*     */ {
/*     */   private IRenderer renderer;
/*     */   private MarginsCollapseInfo collapseInfo;
/*     */   private MarginsCollapseInfo childMarginInfo;
/*     */   private MarginsCollapseInfo prevChildMarginInfo;
/*  77 */   private int firstNotEmptyKidIndex = 0;
/*     */   
/*  79 */   private int processedChildrenNum = 0;
/*  80 */   private List<IRenderer> rendererChildren = new ArrayList<>();
/*     */   
/*     */   private Rectangle backupLayoutBox;
/*     */   
/*     */   private MarginsCollapseInfo backupCollapseInfo;
/*     */   
/*     */   private boolean lastKidCollapsedAfterHasClearanceApplied;
/*     */ 
/*     */   
/*     */   public MarginsCollapseHandler(IRenderer renderer, MarginsCollapseInfo marginsCollapseInfo) {
/*  90 */     this.renderer = renderer;
/*  91 */     this.collapseInfo = (marginsCollapseInfo != null) ? marginsCollapseInfo : new MarginsCollapseInfo();
/*     */   }
/*     */   
/*     */   public void processFixedHeightAdjustment(float heightDelta) {
/*  95 */     this.collapseInfo.setBufferSpaceOnTop(this.collapseInfo.getBufferSpaceOnTop() + heightDelta);
/*  96 */     this.collapseInfo.setBufferSpaceOnBottom(this.collapseInfo.getBufferSpaceOnBottom() + heightDelta);
/*     */   }
/*     */   
/*     */   public MarginsCollapseInfo startChildMarginsHandling(IRenderer child, Rectangle layoutBox) {
/* 100 */     if (this.backupLayoutBox != null) {
/*     */       
/* 102 */       restoreLayoutBoxAfterFailedLayoutAttempt(layoutBox);
/* 103 */       removeRendererChild(--this.processedChildrenNum);
/* 104 */       this.childMarginInfo = null;
/*     */     } 
/*     */     
/* 107 */     this.rendererChildren.add(child);
/*     */     
/* 109 */     int childIndex = this.processedChildrenNum++;
/*     */ 
/*     */ 
/*     */     
/* 113 */     boolean childIsBlockElement = (!rendererIsFloated(child) && isBlockElement(child));
/*     */     
/* 115 */     this.backupLayoutBox = layoutBox.clone();
/* 116 */     this.backupCollapseInfo = new MarginsCollapseInfo();
/* 117 */     this.collapseInfo.copyTo(this.backupCollapseInfo);
/*     */     
/* 119 */     prepareBoxForLayoutAttempt(layoutBox, childIndex, childIsBlockElement);
/*     */     
/* 121 */     if (childIsBlockElement) {
/* 122 */       this.childMarginInfo = createMarginsInfoForBlockChild(childIndex);
/*     */     }
/* 124 */     return this.childMarginInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyClearance(float clearHeightCorrection) {
/* 131 */     this.collapseInfo.setClearanceApplied(true);
/* 132 */     this.collapseInfo.getCollapseBefore().joinMargin(clearHeightCorrection);
/*     */   }
/*     */   private MarginsCollapseInfo createMarginsInfoForBlockChild(int childIndex) {
/*     */     MarginsCollapse childCollapseBefore;
/* 136 */     boolean ignoreChildTopMargin = false;
/*     */     
/* 138 */     boolean ignoreChildBottomMargin = lastChildMarginAdjoinedToParent(this.renderer);
/* 139 */     if (childIndex == this.firstNotEmptyKidIndex) {
/* 140 */       ignoreChildTopMargin = firstChildMarginAdjoinedToParent(this.renderer);
/*     */     }
/*     */ 
/*     */     
/* 144 */     if (childIndex == 0) {
/* 145 */       MarginsCollapse parentCollapseBefore = this.collapseInfo.getCollapseBefore();
/* 146 */       childCollapseBefore = ignoreChildTopMargin ? parentCollapseBefore : new MarginsCollapse();
/*     */     } else {
/* 148 */       MarginsCollapse prevChildCollapseAfter = (this.prevChildMarginInfo != null) ? this.prevChildMarginInfo.getOwnCollapseAfter() : null;
/* 149 */       childCollapseBefore = (prevChildCollapseAfter != null) ? prevChildCollapseAfter : new MarginsCollapse();
/*     */     } 
/*     */     
/* 152 */     MarginsCollapse parentCollapseAfter = this.collapseInfo.getCollapseAfter().clone();
/* 153 */     MarginsCollapse childCollapseAfter = ignoreChildBottomMargin ? parentCollapseAfter : new MarginsCollapse();
/* 154 */     MarginsCollapseInfo childMarginsInfo = new MarginsCollapseInfo(ignoreChildTopMargin, ignoreChildBottomMargin, childCollapseBefore, childCollapseAfter);
/* 155 */     if (ignoreChildTopMargin && childIndex == this.firstNotEmptyKidIndex) {
/* 156 */       childMarginsInfo.setBufferSpaceOnTop(this.collapseInfo.getBufferSpaceOnTop());
/*     */     }
/* 158 */     if (ignoreChildBottomMargin) {
/* 159 */       childMarginsInfo.setBufferSpaceOnBottom(this.collapseInfo.getBufferSpaceOnBottom());
/*     */     }
/* 161 */     return childMarginsInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endChildMarginsHandling(Rectangle layoutBox) {
/* 170 */     int childIndex = this.processedChildrenNum - 1;
/* 171 */     if (rendererIsFloated(getRendererChild(childIndex))) {
/*     */       return;
/*     */     }
/*     */     
/* 175 */     if (this.childMarginInfo != null) {
/* 176 */       if (this.firstNotEmptyKidIndex == childIndex && this.childMarginInfo.isSelfCollapsing()) {
/* 177 */         this.firstNotEmptyKidIndex = childIndex + 1;
/*     */       }
/* 179 */       this.collapseInfo.setSelfCollapsing((this.collapseInfo.isSelfCollapsing() && this.childMarginInfo.isSelfCollapsing()));
/*     */       
/* 181 */       this.lastKidCollapsedAfterHasClearanceApplied = (this.childMarginInfo.isSelfCollapsing() && this.childMarginInfo.isClearanceApplied());
/*     */     } else {
/* 183 */       this.lastKidCollapsedAfterHasClearanceApplied = false;
/* 184 */       this.collapseInfo.setSelfCollapsing(false);
/*     */     } 
/*     */     
/* 187 */     if (this.prevChildMarginInfo != null) {
/* 188 */       fixPrevChildOccupiedArea(childIndex);
/*     */       
/* 190 */       updateCollapseBeforeIfPrevKidIsFirstAndSelfCollapsed(this.prevChildMarginInfo.getOwnCollapseAfter());
/*     */     } 
/*     */     
/* 193 */     if (this.firstNotEmptyKidIndex == childIndex && firstChildMarginAdjoinedToParent(this.renderer) && 
/* 194 */       !this.collapseInfo.isSelfCollapsing()) {
/* 195 */       getRidOfCollapseArtifactsAtopOccupiedArea();
/* 196 */       if (this.childMarginInfo != null) {
/* 197 */         processUsedChildBufferSpaceOnTop(layoutBox);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 202 */     this.prevChildMarginInfo = this.childMarginInfo;
/* 203 */     this.childMarginInfo = null;
/*     */     
/* 205 */     this.backupLayoutBox = null;
/* 206 */     this.backupCollapseInfo = null;
/*     */   }
/*     */   
/*     */   public void startMarginsCollapse(Rectangle parentBBox) {
/* 210 */     this.collapseInfo.getCollapseBefore().joinMargin(getModelTopMargin(this.renderer));
/* 211 */     this.collapseInfo.getCollapseAfter().joinMargin(getModelBottomMargin(this.renderer));
/*     */     
/* 213 */     if (!firstChildMarginAdjoinedToParent(this.renderer)) {
/* 214 */       float topIndent = this.collapseInfo.getCollapseBefore().getCollapsedMarginsSize();
/* 215 */       applyTopMargin(parentBBox, topIndent);
/*     */     } 
/* 217 */     if (!lastChildMarginAdjoinedToParent(this.renderer)) {
/* 218 */       float bottomIndent = this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize();
/* 219 */       applyBottomMargin(parentBBox, bottomIndent);
/*     */     } 
/*     */ 
/*     */     
/* 223 */     ignoreModelTopMargin(this.renderer);
/* 224 */     ignoreModelBottomMargin(this.renderer);
/*     */   }
/*     */   public void endMarginsCollapse(Rectangle layoutBox) {
/*     */     MarginsCollapse ownCollapseAfter;
/* 228 */     if (this.backupLayoutBox != null) {
/* 229 */       restoreLayoutBoxAfterFailedLayoutAttempt(layoutBox);
/*     */     }
/*     */     
/* 232 */     if (this.prevChildMarginInfo != null) {
/* 233 */       updateCollapseBeforeIfPrevKidIsFirstAndSelfCollapsed(this.prevChildMarginInfo.getCollapseAfter());
/*     */     }
/*     */     
/* 236 */     boolean couldBeSelfCollapsing = (marginsCouldBeSelfCollapsing(this.renderer) && !this.lastKidCollapsedAfterHasClearanceApplied);
/* 237 */     boolean blockHasNoKidsWithContent = this.collapseInfo.isSelfCollapsing();
/* 238 */     if (firstChildMarginAdjoinedToParent(this.renderer) && 
/* 239 */       blockHasNoKidsWithContent && !couldBeSelfCollapsing) {
/* 240 */       addNotYetAppliedTopMargin(layoutBox);
/*     */     }
/*     */     
/* 243 */     this.collapseInfo.setSelfCollapsing((this.collapseInfo.isSelfCollapsing() && couldBeSelfCollapsing));
/*     */     
/* 245 */     if (!blockHasNoKidsWithContent && this.lastKidCollapsedAfterHasClearanceApplied) {
/* 246 */       applySelfCollapsedKidMarginWithClearance(layoutBox);
/*     */     }
/*     */ 
/*     */     
/* 250 */     boolean lastChildMarginJoinedToParent = (this.prevChildMarginInfo != null && this.prevChildMarginInfo.isIgnoreOwnMarginBottom() && !this.lastKidCollapsedAfterHasClearanceApplied);
/* 251 */     if (lastChildMarginJoinedToParent) {
/* 252 */       ownCollapseAfter = this.prevChildMarginInfo.getOwnCollapseAfter();
/*     */     } else {
/* 254 */       ownCollapseAfter = new MarginsCollapse();
/*     */     } 
/* 256 */     ownCollapseAfter.joinMargin(getModelBottomMargin(this.renderer));
/* 257 */     this.collapseInfo.setOwnCollapseAfter(ownCollapseAfter);
/*     */     
/* 259 */     if (this.collapseInfo.isSelfCollapsing()) {
/* 260 */       if (this.prevChildMarginInfo != null) {
/* 261 */         this.collapseInfo.setCollapseAfter(this.prevChildMarginInfo.getCollapseAfter());
/*     */       } else {
/* 263 */         this.collapseInfo.getCollapseAfter().joinMargin(this.collapseInfo.getCollapseBefore());
/* 264 */         this.collapseInfo.getOwnCollapseAfter().joinMargin(this.collapseInfo.getCollapseBefore());
/*     */       } 
/* 266 */       if (!this.collapseInfo.isIgnoreOwnMarginBottom() && !this.collapseInfo.isIgnoreOwnMarginTop()) {
/* 267 */         float collapsedMargins = this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize();
/* 268 */         overrideModelBottomMargin(this.renderer, collapsedMargins);
/*     */       } 
/*     */     } else {
/* 271 */       MarginsCollapse marginsCollapseBefore = this.collapseInfo.getCollapseBefore();
/* 272 */       if (!this.collapseInfo.isIgnoreOwnMarginTop()) {
/* 273 */         float collapsedMargins = marginsCollapseBefore.getCollapsedMarginsSize();
/* 274 */         overrideModelTopMargin(this.renderer, collapsedMargins);
/*     */       } 
/*     */       
/* 277 */       if (lastChildMarginJoinedToParent) {
/* 278 */         this.collapseInfo.setCollapseAfter(this.prevChildMarginInfo.getCollapseAfter());
/*     */       }
/* 280 */       if (!this.collapseInfo.isIgnoreOwnMarginBottom()) {
/* 281 */         float collapsedMargins = this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize();
/* 282 */         overrideModelBottomMargin(this.renderer, collapsedMargins);
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     if (lastChildMarginAdjoinedToParent(this.renderer) && (this.prevChildMarginInfo != null || blockHasNoKidsWithContent)) {
/*     */       
/* 288 */       float collapsedMargins = this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       applyBottomMargin(layoutBox, collapsedMargins);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateCollapseBeforeIfPrevKidIsFirstAndSelfCollapsed(MarginsCollapse collapseAfter) {
/* 301 */     if (this.prevChildMarginInfo.isSelfCollapsing() && this.prevChildMarginInfo.isIgnoreOwnMarginTop())
/*     */     {
/* 303 */       this.collapseInfo.getCollapseBefore().joinMargin(collapseAfter);
/*     */     }
/*     */   }
/*     */   
/*     */   private void prepareBoxForLayoutAttempt(Rectangle layoutBox, int childIndex, boolean childIsBlockElement) {
/* 308 */     if (this.prevChildMarginInfo != null) {
/*     */       
/* 310 */       boolean prevChildHasAppliedCollapseAfter = (!this.prevChildMarginInfo.isIgnoreOwnMarginBottom() && (!this.prevChildMarginInfo.isSelfCollapsing() || !this.prevChildMarginInfo.isIgnoreOwnMarginTop()));
/* 311 */       if (prevChildHasAppliedCollapseAfter) {
/* 312 */         layoutBox.setHeight(layoutBox.getHeight() + this.prevChildMarginInfo.getCollapseAfter().getCollapsedMarginsSize());
/*     */       }
/*     */       
/* 315 */       boolean prevChildCanApplyCollapseAfter = (!this.prevChildMarginInfo.isSelfCollapsing() || !this.prevChildMarginInfo.isIgnoreOwnMarginTop());
/* 316 */       if (!childIsBlockElement && prevChildCanApplyCollapseAfter) {
/* 317 */         MarginsCollapse ownCollapseAfter = this.prevChildMarginInfo.getOwnCollapseAfter();
/* 318 */         float ownCollapsedMargins = (ownCollapseAfter == null) ? 0.0F : ownCollapseAfter.getCollapsedMarginsSize();
/* 319 */         layoutBox.setHeight(layoutBox.getHeight() - ownCollapsedMargins);
/*     */       } 
/* 321 */     } else if (childIndex > this.firstNotEmptyKidIndex && 
/* 322 */       lastChildMarginAdjoinedToParent(this.renderer)) {
/*     */ 
/*     */       
/* 325 */       float bottomIndent = this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize() - this.collapseInfo.getUsedBufferSpaceOnBottom();
/* 326 */       this.collapseInfo.setBufferSpaceOnBottom(this.collapseInfo.getBufferSpaceOnBottom() + this.collapseInfo.getUsedBufferSpaceOnBottom());
/* 327 */       this.collapseInfo.setUsedBufferSpaceOnBottom(0.0F);
/* 328 */       layoutBox.setY(layoutBox.getY() - bottomIndent);
/* 329 */       layoutBox.setHeight(layoutBox.getHeight() + bottomIndent);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 334 */     if (!childIsBlockElement) {
/* 335 */       if (childIndex == this.firstNotEmptyKidIndex && firstChildMarginAdjoinedToParent(this.renderer)) {
/* 336 */         float topIndent = this.collapseInfo.getCollapseBefore().getCollapsedMarginsSize();
/* 337 */         applyTopMargin(layoutBox, topIndent);
/*     */       } 
/*     */ 
/*     */       
/* 341 */       if (lastChildMarginAdjoinedToParent(this.renderer)) {
/* 342 */         float bottomIndent = this.collapseInfo.getCollapseAfter().getCollapsedMarginsSize();
/* 343 */         applyBottomMargin(layoutBox, bottomIndent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void restoreLayoutBoxAfterFailedLayoutAttempt(Rectangle layoutBox) {
/* 349 */     layoutBox.setX(this.backupLayoutBox.getX()).setY(this.backupLayoutBox.getY())
/* 350 */       .setWidth(this.backupLayoutBox.getWidth()).setHeight(this.backupLayoutBox.getHeight());
/* 351 */     this.backupCollapseInfo.copyTo(this.collapseInfo);
/*     */     
/* 353 */     this.backupLayoutBox = null;
/* 354 */     this.backupCollapseInfo = null;
/*     */   }
/*     */   
/*     */   private void applyTopMargin(Rectangle box, float topIndent) {
/* 358 */     float bufferLeftoversOnTop = this.collapseInfo.getBufferSpaceOnTop() - topIndent;
/* 359 */     float usedTopBuffer = (bufferLeftoversOnTop > 0.0F) ? topIndent : this.collapseInfo.getBufferSpaceOnTop();
/* 360 */     this.collapseInfo.setUsedBufferSpaceOnTop(usedTopBuffer);
/* 361 */     subtractUsedTopBufferFromBottomBuffer(usedTopBuffer);
/*     */     
/* 363 */     if (bufferLeftoversOnTop >= 0.0F) {
/* 364 */       this.collapseInfo.setBufferSpaceOnTop(bufferLeftoversOnTop);
/* 365 */       box.moveDown(topIndent);
/*     */     } else {
/* 367 */       box.moveDown(this.collapseInfo.getBufferSpaceOnTop());
/* 368 */       this.collapseInfo.setBufferSpaceOnTop(0.0F);
/* 369 */       box.setHeight(box.getHeight() + bufferLeftoversOnTop);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyBottomMargin(Rectangle box, float bottomIndent) {
/* 378 */     float bottomIndentLeftovers = bottomIndent - this.collapseInfo.getBufferSpaceOnBottom();
/* 379 */     if (bottomIndentLeftovers < 0.0F) {
/* 380 */       this.collapseInfo.setUsedBufferSpaceOnBottom(bottomIndent);
/* 381 */       this.collapseInfo.setBufferSpaceOnBottom(-bottomIndentLeftovers);
/*     */     } else {
/* 383 */       this.collapseInfo.setUsedBufferSpaceOnBottom(this.collapseInfo.getBufferSpaceOnBottom());
/* 384 */       this.collapseInfo.setBufferSpaceOnBottom(0.0F);
/* 385 */       box.setY(box.getY() + bottomIndentLeftovers);
/* 386 */       box.setHeight(box.getHeight() - bottomIndentLeftovers);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processUsedChildBufferSpaceOnTop(Rectangle layoutBox) {
/* 391 */     float childUsedBufferSpaceOnTop = this.childMarginInfo.getUsedBufferSpaceOnTop();
/* 392 */     if (childUsedBufferSpaceOnTop > 0.0F) {
/* 393 */       if (childUsedBufferSpaceOnTop > this.collapseInfo.getBufferSpaceOnTop()) {
/* 394 */         childUsedBufferSpaceOnTop = this.collapseInfo.getBufferSpaceOnTop();
/*     */       }
/*     */       
/* 397 */       this.collapseInfo.setBufferSpaceOnTop(this.collapseInfo.getBufferSpaceOnTop() - childUsedBufferSpaceOnTop);
/* 398 */       this.collapseInfo.setUsedBufferSpaceOnTop(childUsedBufferSpaceOnTop);
/*     */ 
/*     */       
/* 401 */       layoutBox.moveDown(childUsedBufferSpaceOnTop);
/*     */       
/* 403 */       subtractUsedTopBufferFromBottomBuffer(childUsedBufferSpaceOnTop);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void subtractUsedTopBufferFromBottomBuffer(float usedTopBuffer) {
/* 408 */     if (this.collapseInfo.getBufferSpaceOnTop() > this.collapseInfo.getBufferSpaceOnBottom()) {
/* 409 */       float bufferLeftoversOnTop = this.collapseInfo.getBufferSpaceOnTop() - usedTopBuffer;
/* 410 */       if (bufferLeftoversOnTop < this.collapseInfo.getBufferSpaceOnBottom()) {
/* 411 */         this.collapseInfo.setBufferSpaceOnBottom(bufferLeftoversOnTop);
/*     */       }
/*     */     } else {
/* 414 */       this.collapseInfo.setBufferSpaceOnBottom(this.collapseInfo.getBufferSpaceOnBottom() - usedTopBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fixPrevChildOccupiedArea(int childIndex) {
/* 419 */     IRenderer prevRenderer = getRendererChild(childIndex - 1);
/*     */     
/* 421 */     Rectangle bBox = prevRenderer.getOccupiedArea().getBBox();
/*     */ 
/*     */     
/* 424 */     boolean prevChildHasAppliedCollapseAfter = (!this.prevChildMarginInfo.isIgnoreOwnMarginBottom() && (!this.prevChildMarginInfo.isSelfCollapsing() || !this.prevChildMarginInfo.isIgnoreOwnMarginTop()));
/*     */     
/* 426 */     if (prevChildHasAppliedCollapseAfter) {
/* 427 */       float bottomMargin = this.prevChildMarginInfo.getCollapseAfter().getCollapsedMarginsSize();
/* 428 */       bBox.setHeight(bBox.getHeight() - bottomMargin);
/* 429 */       bBox.moveUp(bottomMargin);
/* 430 */       ignoreModelBottomMargin(prevRenderer);
/*     */     } 
/*     */     
/* 433 */     boolean isNotBlockChild = !isBlockElement(getRendererChild(childIndex));
/* 434 */     boolean prevChildCanApplyCollapseAfter = (!this.prevChildMarginInfo.isSelfCollapsing() || !this.prevChildMarginInfo.isIgnoreOwnMarginTop());
/* 435 */     if (isNotBlockChild && prevChildCanApplyCollapseAfter) {
/* 436 */       float ownCollapsedMargins = this.prevChildMarginInfo.getOwnCollapseAfter().getCollapsedMarginsSize();
/* 437 */       bBox.setHeight(bBox.getHeight() + ownCollapsedMargins);
/* 438 */       bBox.moveDown(ownCollapsedMargins);
/* 439 */       overrideModelBottomMargin(prevRenderer, ownCollapsedMargins);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addNotYetAppliedTopMargin(Rectangle layoutBox) {
/* 446 */     float indentTop = this.collapseInfo.getCollapseBefore().getCollapsedMarginsSize();
/* 447 */     this.renderer.getOccupiedArea().getBBox().moveDown(indentTop);
/*     */ 
/*     */ 
/*     */     
/* 451 */     applyTopMargin(layoutBox, indentTop);
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
/*     */   private void applySelfCollapsedKidMarginWithClearance(Rectangle layoutBox) {
/* 466 */     float clearedKidMarginWithClearance = this.prevChildMarginInfo.getOwnCollapseAfter().getCollapsedMarginsSize();
/* 467 */     this.renderer.getOccupiedArea().getBBox()
/* 468 */       .increaseHeight(clearedKidMarginWithClearance)
/* 469 */       .moveDown(clearedKidMarginWithClearance);
/*     */     
/* 471 */     layoutBox.decreaseHeight(clearedKidMarginWithClearance);
/*     */   }
/*     */   
/*     */   private IRenderer getRendererChild(int index) {
/* 475 */     return this.rendererChildren.get(index);
/*     */   }
/*     */   
/*     */   private IRenderer removeRendererChild(int index) {
/* 479 */     return this.rendererChildren.remove(index);
/*     */   }
/*     */   
/*     */   private void getRidOfCollapseArtifactsAtopOccupiedArea() {
/* 483 */     Rectangle bBox = this.renderer.getOccupiedArea().getBBox();
/* 484 */     bBox.decreaseHeight(this.collapseInfo.getCollapseBefore().getCollapsedMarginsSize());
/*     */   }
/*     */   
/*     */   private static boolean marginsCouldBeSelfCollapsing(IRenderer renderer) {
/* 488 */     return (!(renderer instanceof com.itextpdf.layout.renderer.TableRenderer) && 
/* 489 */       !rendererIsFloated(renderer) && 
/* 490 */       !hasBottomBorders(renderer) && !hasTopBorders(renderer) && 
/* 491 */       !hasBottomPadding(renderer) && !hasTopPadding(renderer) && !hasPositiveHeight(renderer) && (
/*     */       
/* 493 */       !isBlockElement(renderer) || !(renderer instanceof AbstractRenderer) || !(((AbstractRenderer)renderer).getParent() instanceof com.itextpdf.layout.renderer.LineRenderer)));
/*     */   }
/*     */   
/*     */   private static boolean firstChildMarginAdjoinedToParent(IRenderer parent) {
/* 497 */     return (!BlockFormattingContextUtil.isRendererCreateBfc(parent) && !(parent instanceof com.itextpdf.layout.renderer.TableRenderer) && 
/*     */       
/* 499 */       !hasTopBorders(parent) && !hasTopPadding(parent));
/*     */   }
/*     */   
/*     */   private static boolean lastChildMarginAdjoinedToParent(IRenderer parent) {
/* 503 */     return (!BlockFormattingContextUtil.isRendererCreateBfc(parent) && !(parent instanceof com.itextpdf.layout.renderer.TableRenderer) && 
/*     */       
/* 505 */       !hasBottomBorders(parent) && !hasBottomPadding(parent) && !hasHeightProp(parent));
/*     */   }
/*     */   
/*     */   private static boolean isBlockElement(IRenderer renderer) {
/* 509 */     return (renderer instanceof com.itextpdf.layout.renderer.BlockRenderer || renderer instanceof com.itextpdf.layout.renderer.TableRenderer);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasHeightProp(IRenderer renderer) {
/* 514 */     return renderer.getModelElement().hasProperty(27);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasPositiveHeight(IRenderer renderer) {
/* 534 */     float height = renderer.getOccupiedArea().getBBox().getHeight();
/*     */     
/* 536 */     if (height == 0.0F) {
/* 537 */       UnitValue heightPropVal = (UnitValue)renderer.getProperty(27);
/* 538 */       UnitValue minHeightPropVal = (UnitValue)renderer.getProperty(85);
/*     */ 
/*     */       
/* 541 */       height = (minHeightPropVal != null) ? minHeightPropVal.getValue() : ((heightPropVal != null) ? heightPropVal.getValue() : 0.0F);
/*     */     } 
/* 543 */     return (height > 0.0F);
/*     */   }
/*     */   
/*     */   private static boolean hasTopPadding(IRenderer renderer) {
/* 547 */     UnitValue padding = (UnitValue)renderer.getModelElement().getProperty(50);
/* 548 */     if (null != padding && !padding.isPointValue()) {
/* 549 */       Logger logger = LoggerFactory.getLogger(MarginsCollapseHandler.class);
/* 550 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(50) }));
/*     */     } 
/* 552 */     return (padding != null && padding.getValue() > 0.0F);
/*     */   }
/*     */   
/*     */   private static boolean hasBottomPadding(IRenderer renderer) {
/* 556 */     UnitValue padding = (UnitValue)renderer.getModelElement().getProperty(47);
/* 557 */     if (null != padding && !padding.isPointValue()) {
/* 558 */       Logger logger = LoggerFactory.getLogger(MarginsCollapseHandler.class);
/* 559 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(47) }));
/*     */     } 
/* 561 */     return (padding != null && padding.getValue() > 0.0F);
/*     */   }
/*     */   
/*     */   private static boolean hasTopBorders(IRenderer renderer) {
/* 565 */     IPropertyContainer modelElement = renderer.getModelElement();
/* 566 */     return (modelElement.hasProperty(13) || modelElement.hasProperty(9));
/*     */   }
/*     */   
/*     */   private static boolean hasBottomBorders(IRenderer renderer) {
/* 570 */     IPropertyContainer modelElement = renderer.getModelElement();
/* 571 */     return (modelElement.hasProperty(10) || modelElement.hasProperty(9));
/*     */   }
/*     */   
/*     */   private static boolean rendererIsFloated(IRenderer renderer) {
/* 575 */     if (renderer == null) {
/* 576 */       return false;
/*     */     }
/* 578 */     FloatPropertyValue floatPropertyValue = (FloatPropertyValue)renderer.getProperty(99);
/* 579 */     return (floatPropertyValue != null && !floatPropertyValue.equals(FloatPropertyValue.NONE));
/*     */   }
/*     */   
/*     */   private static float getModelTopMargin(IRenderer renderer) {
/* 583 */     UnitValue marginUV = (UnitValue)renderer.getModelElement().getProperty(46);
/* 584 */     if (null != marginUV && !marginUV.isPointValue()) {
/* 585 */       Logger logger = LoggerFactory.getLogger(MarginsCollapseHandler.class);
/* 586 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(46) }));
/*     */     } 
/*     */     
/* 589 */     return (marginUV != null && !(renderer instanceof com.itextpdf.layout.renderer.CellRenderer)) ? marginUV.getValue() : 0.0F;
/*     */   }
/*     */   
/*     */   private static void ignoreModelTopMargin(IRenderer renderer) {
/* 593 */     renderer.setProperty(46, UnitValue.createPointValue(0.0F));
/*     */   }
/*     */   
/*     */   private static void overrideModelTopMargin(IRenderer renderer, float collapsedMargins) {
/* 597 */     renderer.setProperty(46, UnitValue.createPointValue(collapsedMargins));
/*     */   }
/*     */   
/*     */   private static float getModelBottomMargin(IRenderer renderer) {
/* 601 */     UnitValue marginUV = (UnitValue)renderer.getModelElement().getProperty(43);
/* 602 */     if (null != marginUV && !marginUV.isPointValue()) {
/* 603 */       Logger logger = LoggerFactory.getLogger(MarginsCollapseHandler.class);
/* 604 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(46) }));
/*     */     } 
/*     */     
/* 607 */     return (marginUV != null && !(renderer instanceof com.itextpdf.layout.renderer.CellRenderer)) ? marginUV.getValue() : 0.0F;
/*     */   }
/*     */   
/*     */   private static void ignoreModelBottomMargin(IRenderer renderer) {
/* 611 */     renderer.setProperty(43, UnitValue.createPointValue(0.0F));
/*     */   }
/*     */   
/*     */   private static void overrideModelBottomMargin(IRenderer renderer, float collapsedMargins) {
/* 615 */     renderer.setProperty(43, UnitValue.createPointValue(collapsedMargins));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/margincollapse/MarginsCollapseHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */