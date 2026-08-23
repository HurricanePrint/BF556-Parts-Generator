/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.element.Paragraph;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.layout.LineLayoutContext;
/*     */ import com.itextpdf.layout.layout.LineLayoutResult;
/*     */ import com.itextpdf.layout.layout.MinMaxWidthLayoutResult;
/*     */ import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*     */ import com.itextpdf.layout.property.BaseDirection;
/*     */ import com.itextpdf.layout.property.FloatPropertyValue;
/*     */ import com.itextpdf.layout.property.Leading;
/*     */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*     */ import com.itextpdf.layout.property.ParagraphOrphansControl;
/*     */ import com.itextpdf.layout.property.ParagraphWidowsControl;
/*     */ import com.itextpdf.layout.property.RenderingMode;
/*     */ import com.itextpdf.layout.property.TextAlignment;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class ParagraphRenderer
/*     */   extends BlockRenderer
/*     */ {
/*     */   @Deprecated
/*  84 */   protected float previousDescent = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   protected List<LineRenderer> lines = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParagraphRenderer(Paragraph modelElement) {
/*  97 */     super((IElement)modelElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutResult layout(LayoutContext layoutContext) {
/* 105 */     ParagraphOrphansControl orphansControl = getProperty(121);
/* 106 */     ParagraphWidowsControl widowsControl = getProperty(122);
/* 107 */     if (orphansControl != null || widowsControl != null) {
/* 108 */       return OrphansWidowsLayoutHelper.orphansWidowsAwareLayout(this, layoutContext, orphansControl, widowsControl);
/*     */     }
/* 110 */     return directLayout(layoutContext);
/*     */   }
/*     */   protected LayoutResult directLayout(LayoutContext layoutContext) {
/*     */     List<Rectangle> areas;
/* 114 */     boolean wasHeightClipped = false;
/* 115 */     boolean wasParentsHeightClipped = layoutContext.isClippedHeight();
/* 116 */     int pageNumber = layoutContext.getArea().getPageNumber();
/* 117 */     boolean anythingPlaced = false;
/* 118 */     boolean firstLineInBox = true;
/* 119 */     LineRenderer currentRenderer = (LineRenderer)(new LineRenderer()).setParent(this);
/* 120 */     Rectangle parentBBox = layoutContext.getArea().getBBox().clone();
/*     */     
/* 122 */     MarginsCollapseHandler marginsCollapseHandler = null;
/* 123 */     boolean marginsCollapsingEnabled = Boolean.TRUE.equals(getPropertyAsBoolean(89));
/* 124 */     if (marginsCollapsingEnabled) {
/* 125 */       marginsCollapseHandler = new MarginsCollapseHandler(this, layoutContext.getMarginsCollapseInfo());
/*     */     }
/*     */     
/* 128 */     OverflowPropertyValue overflowX = getProperty(103);
/*     */     
/* 130 */     Boolean nowrapProp = getPropertyAsBoolean(118);
/* 131 */     currentRenderer.setProperty(118, nowrapProp);
/*     */     
/* 133 */     boolean notAllKidsAreFloats = false;
/* 134 */     List<Rectangle> floatRendererAreas = layoutContext.getFloatRendererAreas();
/* 135 */     FloatPropertyValue floatPropertyValue = getProperty(99);
/* 136 */     float clearHeightCorrection = FloatingHelper.calculateClearHeightCorrection(this, floatRendererAreas, parentBBox);
/* 137 */     FloatingHelper.applyClearance(parentBBox, marginsCollapseHandler, clearHeightCorrection, FloatingHelper.isRendererFloating(this));
/* 138 */     Float blockWidth = retrieveWidth(parentBBox.getWidth());
/* 139 */     if (FloatingHelper.isRendererFloating(this, floatPropertyValue)) {
/* 140 */       blockWidth = FloatingHelper.adjustFloatedBlockLayoutBox(this, parentBBox, blockWidth, floatRendererAreas, floatPropertyValue, overflowX);
/* 141 */       floatRendererAreas = new ArrayList<>();
/*     */     } 
/*     */     
/* 144 */     if (0 == this.childRenderers.size()) {
/* 145 */       anythingPlaced = true;
/* 146 */       currentRenderer = null;
/*     */     } 
/*     */     
/* 149 */     boolean isPositioned = isPositioned();
/* 150 */     Float rotation = getPropertyAsFloat(55);
/*     */     
/* 152 */     Float blockMaxHeight = retrieveMaxHeight();
/*     */ 
/*     */ 
/*     */     
/* 156 */     OverflowPropertyValue overflowY = ((null == blockMaxHeight || blockMaxHeight.floatValue() > parentBBox.getHeight()) && !wasParentsHeightClipped) ? OverflowPropertyValue.FIT : getProperty(104);
/*     */     
/* 158 */     if (rotation != null || isFixedLayout()) {
/* 159 */       parentBBox.moveDown(1000000.0F - parentBBox.getHeight()).setHeight(1000000.0F);
/*     */     }
/* 161 */     if (rotation != null && !FloatingHelper.isRendererFloating(this)) {
/* 162 */       blockWidth = RotationUtils.retrieveRotatedLayoutWidth(parentBBox.getWidth(), this);
/*     */     }
/*     */     
/* 165 */     if (marginsCollapsingEnabled) {
/* 166 */       marginsCollapseHandler.startMarginsCollapse(parentBBox);
/*     */     }
/* 168 */     Border[] borders = getBorders();
/* 169 */     UnitValue[] paddings = getPaddings();
/*     */     
/* 171 */     float additionalWidth = applyBordersPaddingsMargins(parentBBox, borders, paddings);
/* 172 */     applyWidth(parentBBox, blockWidth, overflowX);
/* 173 */     wasHeightClipped = applyMaxHeight(parentBBox, blockMaxHeight, marginsCollapseHandler, false, wasParentsHeightClipped, overflowY);
/*     */     
/* 175 */     MinMaxWidth minMaxWidth = new MinMaxWidth(additionalWidth);
/* 176 */     AbstractWidthHandler widthHandler = new MaxMaxWidthHandler(minMaxWidth);
/*     */ 
/*     */     
/* 179 */     if (isPositioned) {
/* 180 */       areas = Collections.singletonList(parentBBox);
/*     */     } else {
/* 182 */       areas = initElementAreas(new LayoutArea(pageNumber, parentBBox));
/*     */     } 
/*     */     
/* 185 */     this.occupiedArea = new LayoutArea(pageNumber, new Rectangle(parentBBox.getX(), parentBBox.getY() + parentBBox.getHeight(), parentBBox.getWidth(), 0.0F));
/* 186 */     shrinkOccupiedAreaForAbsolutePosition();
/*     */     
/* 188 */     int currentAreaPos = 0;
/* 189 */     Rectangle layoutBox = ((Rectangle)areas.get(0)).clone();
/* 190 */     this.lines = new ArrayList<>();
/* 191 */     for (IRenderer child : this.childRenderers) {
/* 192 */       notAllKidsAreFloats = (notAllKidsAreFloats || !FloatingHelper.isRendererFloating(child));
/* 193 */       currentRenderer.addChild(child);
/*     */     } 
/*     */     
/* 196 */     float lastYLine = layoutBox.getY() + layoutBox.getHeight();
/*     */     
/* 198 */     float previousDescent = 0.0F;
/* 199 */     float lastLineBottomLeadingIndent = 0.0F;
/* 200 */     boolean onlyOverflowedFloatsLeft = false;
/* 201 */     List<IRenderer> inlineFloatsOverflowedToNextPage = new ArrayList<>();
/* 202 */     boolean floatOverflowedToNextPageWithNothing = false;
/*     */ 
/*     */     
/* 205 */     Set<Rectangle> nonChildFloatingRendererAreas = new HashSet<>(floatRendererAreas);
/*     */     
/* 207 */     if (marginsCollapsingEnabled && this.childRenderers.size() > 0)
/*     */     {
/* 209 */       marginsCollapseHandler.startChildMarginsHandling(null, layoutBox);
/*     */     }
/* 211 */     boolean includeFloatsInOccupiedArea = BlockFormattingContextUtil.isRendererCreateBfc(this);
/* 212 */     while (currentRenderer != null) {
/* 213 */       currentRenderer.setProperty(67, getPropertyAsFloat(67));
/* 214 */       currentRenderer.setProperty(69, getProperty(69));
/*     */       
/* 216 */       float lineIndent = anythingPlaced ? 0.0F : getPropertyAsFloat(18).floatValue();
/* 217 */       Rectangle childLayoutBox = new Rectangle(layoutBox.getX(), layoutBox.getY(), layoutBox.getWidth(), layoutBox.getHeight());
/* 218 */       currentRenderer.setProperty(103, overflowX);
/* 219 */       currentRenderer.setProperty(104, overflowY);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       LineLayoutContext lineLayoutContext = (new LineLayoutContext(new LayoutArea(pageNumber, childLayoutBox), null, floatRendererAreas, (wasHeightClipped || wasParentsHeightClipped))).setTextIndent(lineIndent).setFloatOverflowedToNextPageWithNothing(floatOverflowedToNextPageWithNothing);
/* 225 */       LineLayoutResult result = (LineLayoutResult)((LineRenderer)currentRenderer.setParent(this)).layout((LayoutContext)lineLayoutContext);
/*     */       
/* 227 */       if (result.getStatus() == 3) {
/* 228 */         Float lineShiftUnderFloats = FloatingHelper.calculateLineShiftUnderFloats(floatRendererAreas, layoutBox);
/* 229 */         if (lineShiftUnderFloats != null) {
/* 230 */           layoutBox.decreaseHeight(lineShiftUnderFloats.floatValue());
/* 231 */           firstLineInBox = true;
/*     */           
/*     */           continue;
/*     */         } 
/* 235 */         boolean allRemainingKidsAreFloats = !currentRenderer.childRenderers.isEmpty();
/* 236 */         for (IRenderer renderer : currentRenderer.childRenderers) {
/* 237 */           allRemainingKidsAreFloats = (allRemainingKidsAreFloats && FloatingHelper.isRendererFloating(renderer));
/*     */         }
/* 239 */         if (allRemainingKidsAreFloats) {
/* 240 */           onlyOverflowedFloatsLeft = true;
/*     */         }
/*     */       } 
/*     */       
/* 244 */       floatOverflowedToNextPageWithNothing = lineLayoutContext.isFloatOverflowedToNextPageWithNothing();
/* 245 */       if (result.getFloatsOverflowedToNextPage() != null) {
/* 246 */         inlineFloatsOverflowedToNextPage.addAll(result.getFloatsOverflowedToNextPage());
/*     */       }
/*     */       
/* 249 */       float minChildWidth = 0.0F;
/* 250 */       float maxChildWidth = 0.0F;
/* 251 */       if (result instanceof MinMaxWidthLayoutResult) {
/* 252 */         minChildWidth = result.getMinMaxWidth().getMinWidth();
/* 253 */         maxChildWidth = result.getMinMaxWidth().getMaxWidth();
/*     */       } 
/*     */       
/* 256 */       widthHandler.updateMinChildWidth(minChildWidth);
/* 257 */       widthHandler.updateMaxChildWidth(maxChildWidth);
/*     */       
/* 259 */       LineRenderer processedRenderer = null;
/* 260 */       if (result.getStatus() == 1) {
/* 261 */         processedRenderer = currentRenderer;
/* 262 */       } else if (result.getStatus() == 2) {
/* 263 */         processedRenderer = (LineRenderer)result.getSplitRenderer();
/*     */       } 
/*     */       
/* 266 */       if (onlyOverflowedFloatsLeft)
/*     */       {
/*     */ 
/*     */         
/* 270 */         processedRenderer = null;
/*     */       }
/*     */       
/* 273 */       TextAlignment textAlignment = getProperty(70, TextAlignment.LEFT);
/* 274 */       applyTextAlignment(textAlignment, result, processedRenderer, layoutBox, floatRendererAreas, onlyOverflowedFloatsLeft, lineIndent);
/*     */ 
/*     */ 
/*     */       
/* 278 */       Leading leading = RenderingMode.HTML_MODE.equals(getProperty(123)) ? null : getProperty(33);
/*     */       
/* 280 */       boolean lineHasContent = (processedRenderer != null && processedRenderer.getOccupiedArea().getBBox().getHeight() > 0.0F);
/* 281 */       boolean isFit = (processedRenderer != null);
/* 282 */       float deltaY = 0.0F;
/* 283 */       if (isFit && !RenderingMode.HTML_MODE.equals(getProperty(123))) {
/* 284 */         if (lineHasContent) {
/* 285 */           float indentFromLastLine = previousDescent - lastLineBottomLeadingIndent - ((leading != null) ? processedRenderer.getTopLeadingIndent(leading) : 0.0F) - processedRenderer.getMaxAscent();
/*     */           
/* 287 */           if (processedRenderer != null && processedRenderer.containsImage()) {
/* 288 */             indentFromLastLine += previousDescent;
/*     */           }
/* 290 */           deltaY = lastYLine + indentFromLastLine - processedRenderer.getYLine();
/* 291 */           lastLineBottomLeadingIndent = (leading != null) ? processedRenderer.getBottomLeadingIndent(leading) : 0.0F;
/*     */           
/* 293 */           if (lastLineBottomLeadingIndent < 0.0F && processedRenderer.containsImage()) {
/* 294 */             lastLineBottomLeadingIndent = 0.0F;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 299 */         if (firstLineInBox) {
/* 300 */           deltaY = (processedRenderer != null && leading != null) ? -processedRenderer.getTopLeadingIndent(leading) : 0.0F;
/*     */         }
/* 302 */         isFit = (leading == null || processedRenderer.getOccupiedArea().getBBox().getY() + deltaY >= layoutBox.getY());
/*     */       } 
/*     */       
/* 305 */       if (!isFit && (null == processedRenderer || isOverflowFit(overflowY))) {
/* 306 */         if (currentAreaPos + 1 < areas.size()) {
/* 307 */           layoutBox = ((Rectangle)areas.get(++currentAreaPos)).clone();
/* 308 */           lastYLine = layoutBox.getY() + layoutBox.getHeight();
/* 309 */           firstLineInBox = true; continue;
/*     */         } 
/* 311 */         boolean keepTogether = isKeepTogether();
/* 312 */         if (keepTogether) {
/* 313 */           floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/* 314 */           return (LayoutResult)new MinMaxWidthLayoutResult(3, null, null, this, (null == result.getCauseOfNothing()) ? this : result.getCauseOfNothing());
/*     */         } 
/* 316 */         if (marginsCollapsingEnabled && 
/* 317 */           anythingPlaced && notAllKidsAreFloats) {
/* 318 */           marginsCollapseHandler.endChildMarginsHandling(layoutBox);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 323 */         boolean includeFloatsInOccupiedAreaOnSplit = (!onlyOverflowedFloatsLeft || includeFloatsInOccupiedArea);
/* 324 */         if (includeFloatsInOccupiedAreaOnSplit) {
/* 325 */           FloatingHelper.includeChildFloatsInOccupiedArea(floatRendererAreas, this, nonChildFloatingRendererAreas);
/* 326 */           fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/*     */         } 
/*     */         
/* 329 */         if (marginsCollapsingEnabled) {
/* 330 */           marginsCollapseHandler.endMarginsCollapse(layoutBox);
/*     */         }
/*     */         
/* 333 */         boolean minHeightOverflowed = false;
/* 334 */         if (!includeFloatsInOccupiedAreaOnSplit) {
/* 335 */           AbstractRenderer minHeightOverflow = applyMinHeight(overflowY, layoutBox);
/* 336 */           minHeightOverflowed = (minHeightOverflow != null);
/* 337 */           applyVerticalAlignment();
/*     */         } 
/*     */         
/* 340 */         ParagraphRenderer[] split = split();
/* 341 */         (split[0]).lines = this.lines;
/* 342 */         for (LineRenderer line : this.lines) {
/* 343 */           (split[0]).childRenderers.addAll(line.getChildRenderers());
/*     */         }
/* 345 */         (split[1]).childRenderers.addAll(inlineFloatsOverflowedToNextPage);
/* 346 */         if (processedRenderer != null) {
/* 347 */           (split[1]).childRenderers.addAll(processedRenderer.getChildRenderers());
/*     */         }
/* 349 */         if (result.getOverflowRenderer() != null) {
/* 350 */           (split[1]).childRenderers.addAll(result.getOverflowRenderer().getChildRenderers());
/*     */         }
/*     */         
/* 353 */         if (onlyOverflowedFloatsLeft && !includeFloatsInOccupiedArea && !minHeightOverflowed) {
/* 354 */           FloatingHelper.removeParentArtifactsOnPageSplitIfOnlyFloatsOverflow(split[1]);
/*     */         }
/*     */ 
/*     */         
/* 358 */         float usedHeight = this.occupiedArea.getBBox().getHeight();
/* 359 */         if (!includeFloatsInOccupiedAreaOnSplit) {
/* 360 */           Rectangle commonRectangle = Rectangle.getCommonRectangle(new Rectangle[] { layoutBox, this.occupiedArea.getBBox() });
/* 361 */           usedHeight = commonRectangle.getHeight();
/*     */         } 
/*     */         
/* 364 */         updateHeightsOnSplit(usedHeight, wasHeightClipped, this, split[1], includeFloatsInOccupiedAreaOnSplit);
/* 365 */         correctFixedLayout(layoutBox);
/* 366 */         applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/* 367 */         applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/* 368 */         applyMargins(this.occupiedArea.getBBox(), true);
/*     */         
/* 370 */         applyAbsolutePositionIfNeeded(layoutContext);
/*     */         
/* 372 */         LayoutArea layoutArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, layoutContext.getFloatRendererAreas(), layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/* 373 */         if (wasHeightClipped)
/* 374 */           return (LayoutResult)(new MinMaxWidthLayoutResult(1, layoutArea, split[0], null)).setMinMaxWidth(minMaxWidth); 
/* 375 */         if (anythingPlaced) {
/* 376 */           return (LayoutResult)(new MinMaxWidthLayoutResult(2, layoutArea, split[0], split[1])).setMinMaxWidth(minMaxWidth);
/*     */         }
/* 378 */         if (Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
/* 379 */           this.occupiedArea.setBBox(Rectangle.getCommonRectangle(new Rectangle[] { this.occupiedArea.getBBox(), currentRenderer.getOccupiedArea().getBBox() }));
/* 380 */           fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/* 381 */           this.parent.setProperty(25, Boolean.valueOf(true));
/* 382 */           this.lines.add(currentRenderer);
/*     */           
/* 384 */           if (2 == result.getStatus()) {
/* 385 */             IRenderer childNotRendered = result.getCauseOfNothing();
/* 386 */             int firstNotRendered = currentRenderer.childRenderers.indexOf(childNotRendered);
/* 387 */             currentRenderer.childRenderers.retainAll(currentRenderer.childRenderers.subList(0, firstNotRendered));
/* 388 */             (split[1]).childRenderers.removeAll((split[1]).childRenderers.subList(0, firstNotRendered));
/* 389 */             return (LayoutResult)(new MinMaxWidthLayoutResult(2, layoutArea, this, split[1], null)).setMinMaxWidth(minMaxWidth);
/*     */           } 
/* 391 */           return (LayoutResult)(new MinMaxWidthLayoutResult(1, layoutArea, null, null, this)).setMinMaxWidth(minMaxWidth);
/*     */         } 
/*     */         
/* 394 */         floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/* 395 */         return (LayoutResult)new MinMaxWidthLayoutResult(3, null, null, this, (null == result.getCauseOfNothing()) ? this : result.getCauseOfNothing());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 401 */       if (leading != null) {
/* 402 */         processedRenderer.applyLeading(deltaY);
/* 403 */         if (lineHasContent) {
/* 404 */           lastYLine = processedRenderer.getYLine();
/*     */         }
/*     */       } 
/* 407 */       if (lineHasContent) {
/* 408 */         this.occupiedArea.setBBox(Rectangle.getCommonRectangle(new Rectangle[] { this.occupiedArea.getBBox(), processedRenderer.getOccupiedArea().getBBox() }));
/* 409 */         fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/*     */       } 
/* 411 */       firstLineInBox = false;
/*     */       
/* 413 */       layoutBox.setHeight(processedRenderer.getOccupiedArea().getBBox().getY() - layoutBox.getY());
/* 414 */       this.lines.add(processedRenderer);
/*     */       
/* 416 */       anythingPlaced = true;
/*     */       
/* 418 */       currentRenderer = (LineRenderer)result.getOverflowRenderer();
/* 419 */       previousDescent = processedRenderer.getMaxDescent();
/*     */       
/* 421 */       if (!inlineFloatsOverflowedToNextPage.isEmpty() && result.getOverflowRenderer() == null) {
/* 422 */         onlyOverflowedFloatsLeft = true;
/*     */ 
/*     */         
/* 425 */         currentRenderer = new LineRenderer();
/*     */       } 
/*     */     } 
/*     */     
/* 429 */     if (!RenderingMode.HTML_MODE.equals(getProperty(123))) {
/* 430 */       float moveDown = lastLineBottomLeadingIndent;
/* 431 */       if (isOverflowFit(overflowY) && moveDown > this.occupiedArea.getBBox().getY() - layoutBox.getY()) {
/* 432 */         moveDown = this.occupiedArea.getBBox().getY() - layoutBox.getY();
/*     */       }
/* 434 */       this.occupiedArea.getBBox().moveDown(moveDown);
/* 435 */       this.occupiedArea.getBBox().setHeight(this.occupiedArea.getBBox().getHeight() + moveDown);
/*     */     } 
/*     */     
/* 438 */     if (marginsCollapsingEnabled && 
/* 439 */       this.childRenderers.size() > 0 && notAllKidsAreFloats) {
/* 440 */       marginsCollapseHandler.endChildMarginsHandling(layoutBox);
/*     */     }
/*     */ 
/*     */     
/* 444 */     if (includeFloatsInOccupiedArea) {
/* 445 */       FloatingHelper.includeChildFloatsInOccupiedArea(floatRendererAreas, this, nonChildFloatingRendererAreas);
/* 446 */       fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/*     */     } 
/*     */     
/* 449 */     if (wasHeightClipped) {
/* 450 */       fixOccupiedAreaIfOverflowedY(overflowY, layoutBox);
/*     */     }
/*     */     
/* 453 */     if (marginsCollapsingEnabled) {
/* 454 */       marginsCollapseHandler.endMarginsCollapse(layoutBox);
/*     */     }
/*     */     
/* 457 */     AbstractRenderer overflowRenderer = applyMinHeight(overflowY, layoutBox);
/* 458 */     if (overflowRenderer != null && isKeepTogether()) {
/* 459 */       floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/* 460 */       return new LayoutResult(3, null, null, this, this);
/*     */     } 
/*     */     
/* 463 */     correctFixedLayout(layoutBox);
/*     */     
/* 465 */     applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/* 466 */     applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/* 467 */     applyMargins(this.occupiedArea.getBBox(), true);
/*     */     
/* 469 */     applyAbsolutePositionIfNeeded(layoutContext);
/*     */     
/* 471 */     if (rotation != null) {
/* 472 */       applyRotationLayout(layoutContext.getArea().getBBox().clone());
/* 473 */       if (isNotFittingLayoutArea(layoutContext.getArea())) {
/* 474 */         if (isNotFittingWidth(layoutContext.getArea()) && !isNotFittingHeight(layoutContext.getArea())) {
/* 475 */           LoggerFactory.getLogger(getClass()).warn(MessageFormatUtil.format("Element does not fit current area. {0}", new Object[] { "It fits by height so it will be forced placed" }));
/* 476 */         } else if (!Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
/* 477 */           floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/* 478 */           return (LayoutResult)new MinMaxWidthLayoutResult(3, null, null, this, this);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 483 */     applyVerticalAlignment();
/*     */     
/* 485 */     FloatingHelper.removeFloatsAboveRendererBottom(floatRendererAreas, this);
/* 486 */     LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, layoutContext.getFloatRendererAreas(), layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*     */ 
/*     */     
/* 489 */     if (null == overflowRenderer) {
/* 490 */       return (LayoutResult)(new MinMaxWidthLayoutResult(1, editedArea, null, null, null)).setMinMaxWidth(minMaxWidth);
/*     */     }
/* 492 */     return (LayoutResult)(new MinMaxWidthLayoutResult(2, editedArea, this, overflowRenderer, null)).setMinMaxWidth(minMaxWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 501 */     return new ParagraphRenderer((Paragraph)this.modelElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/* 509 */     if ((property == 46 || property == 43) && this.parent instanceof CellRenderer) {
/* 510 */       return (T1)UnitValue.createPointValue(0.0F);
/*     */     }
/* 512 */     return super.getDefaultProperty(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 520 */     StringBuilder sb = new StringBuilder();
/* 521 */     if (this.lines != null && this.lines.size() > 0) {
/* 522 */       for (int i = 0; i < this.lines.size(); i++) {
/* 523 */         if (i > 0) {
/* 524 */           sb.append("\n");
/*     */         }
/* 526 */         sb.append(((LineRenderer)this.lines.get(i)).toString());
/*     */       } 
/*     */     } else {
/* 529 */       for (IRenderer renderer : this.childRenderers) {
/* 530 */         sb.append(renderer.toString());
/*     */       }
/*     */     } 
/* 533 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawChildren(DrawContext drawContext) {
/* 541 */     if (this.lines != null) {
/* 542 */       for (LineRenderer line : this.lines) {
/* 543 */         line.draw(drawContext);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void move(float dxRight, float dyUp) {
/* 553 */     this.occupiedArea.getBBox().moveRight(dxRight);
/* 554 */     this.occupiedArea.getBBox().moveUp(dyUp);
/* 555 */     if (null != this.lines) {
/* 556 */       for (LineRenderer line : this.lines) {
/* 557 */         line.move(dxRight, dyUp);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<LineRenderer> getLines() {
/* 567 */     return this.lines;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Float getFirstYLineRecursively() {
/* 572 */     if (this.lines == null || this.lines.size() == 0) {
/* 573 */       return null;
/*     */     }
/* 575 */     return ((LineRenderer)this.lines.get(0)).getFirstYLineRecursively();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Float getLastYLineRecursively() {
/* 580 */     if (!allowLastYLineRecursiveExtraction()) {
/* 581 */       return null;
/*     */     }
/* 583 */     if (this.lines == null || this.lines.size() == 0) {
/* 584 */       return null;
/*     */     }
/* 586 */     for (int i = this.lines.size() - 1; i >= 0; i--) {
/* 587 */       Float yLine = ((LineRenderer)this.lines.get(i)).getLastYLineRecursively();
/* 588 */       if (yLine != null) {
/* 589 */         return yLine;
/*     */       }
/*     */     } 
/* 592 */     return null;
/*     */   }
/*     */   
/*     */   private ParagraphRenderer createOverflowRenderer() {
/* 596 */     return (ParagraphRenderer)getNextRenderer();
/*     */   }
/*     */   
/*     */   private ParagraphRenderer createSplitRenderer() {
/* 600 */     return (ParagraphRenderer)getNextRenderer();
/*     */   }
/*     */   
/*     */   protected ParagraphRenderer createOverflowRenderer(IRenderer parent) {
/* 604 */     ParagraphRenderer overflowRenderer = createOverflowRenderer();
/* 605 */     overflowRenderer.parent = parent;
/* 606 */     fixOverflowRenderer(overflowRenderer);
/* 607 */     overflowRenderer.addAllProperties(getOwnProperties());
/* 608 */     return overflowRenderer;
/*     */   }
/*     */   
/*     */   protected ParagraphRenderer createSplitRenderer(IRenderer parent) {
/* 612 */     ParagraphRenderer splitRenderer = createSplitRenderer();
/* 613 */     splitRenderer.parent = parent;
/* 614 */     splitRenderer.addAllProperties(getOwnProperties());
/* 615 */     return splitRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRenderer createOverflowRenderer(int layoutResult) {
/* 620 */     return createOverflowRenderer(this.parent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MinMaxWidth getMinMaxWidth() {
/* 626 */     MinMaxWidth minMaxWidth = new MinMaxWidth();
/* 627 */     Float rotation = getPropertyAsFloat(55);
/* 628 */     if (!setMinMaxWidthBasedOnFixedWidth(minMaxWidth)) {
/* 629 */       Float minWidth = hasAbsoluteUnitValue(80) ? retrieveMinWidth(0.0F) : null;
/* 630 */       Float maxWidth = hasAbsoluteUnitValue(79) ? retrieveMaxWidth(0.0F) : null;
/* 631 */       if (minWidth == null || maxWidth == null) {
/* 632 */         boolean restoreRotation = hasOwnProperty(55);
/* 633 */         setProperty(55, null);
/* 634 */         MinMaxWidthLayoutResult result = (MinMaxWidthLayoutResult)layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0F))));
/* 635 */         if (restoreRotation) {
/* 636 */           setProperty(55, rotation);
/*     */         } else {
/* 638 */           deleteOwnProperty(55);
/*     */         } 
/* 640 */         minMaxWidth = result.getMinMaxWidth();
/*     */       } 
/* 642 */       if (minWidth != null) {
/* 643 */         minMaxWidth.setChildrenMinWidth(minWidth.floatValue());
/*     */       }
/* 645 */       if (maxWidth != null) {
/* 646 */         minMaxWidth.setChildrenMaxWidth(maxWidth.floatValue());
/*     */       }
/* 648 */       if (minMaxWidth.getChildrenMinWidth() > minMaxWidth.getChildrenMaxWidth()) {
/* 649 */         minMaxWidth.setChildrenMaxWidth(minMaxWidth.getChildrenMaxWidth());
/*     */       }
/*     */     } else {
/* 652 */       minMaxWidth.setAdditionalWidth(calculateAdditionalWidth(this));
/*     */     } 
/*     */     
/* 655 */     return (rotation != null) ? RotationUtils.countRotationMinMaxWidth(minMaxWidth, this) : minMaxWidth;
/*     */   }
/*     */   
/*     */   protected ParagraphRenderer[] split() {
/* 659 */     ParagraphRenderer splitRenderer = createSplitRenderer(this.parent);
/* 660 */     splitRenderer.occupiedArea = this.occupiedArea;
/* 661 */     splitRenderer.isLastRendererForModelElement = false;
/*     */     
/* 663 */     ParagraphRenderer overflowRenderer = createOverflowRenderer(this.parent);
/*     */     
/* 665 */     return new ParagraphRenderer[] { splitRenderer, overflowRenderer };
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixOverflowRenderer(ParagraphRenderer overflowRenderer) {
/* 670 */     float firstLineIndent = overflowRenderer.getPropertyAsFloat(18).floatValue();
/* 671 */     if (firstLineIndent != 0.0F) {
/* 672 */       overflowRenderer.setProperty(18, Float.valueOf(0.0F));
/*     */     }
/*     */   }
/*     */   
/*     */   private void alignStaticKids(LineRenderer renderer, float dxRight) {
/* 677 */     renderer.getOccupiedArea().getBBox().moveRight(dxRight);
/* 678 */     for (IRenderer childRenderer : renderer.getChildRenderers()) {
/* 679 */       if (FloatingHelper.isRendererFloating(childRenderer)) {
/*     */         continue;
/*     */       }
/* 682 */       childRenderer.move(dxRight, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyTextAlignment(TextAlignment textAlignment, LineLayoutResult result, LineRenderer processedRenderer, Rectangle layoutBox, List<Rectangle> floatRendererAreas, boolean onlyOverflowedFloatsLeft, float lineIndent) {
/* 688 */     if ((textAlignment == TextAlignment.JUSTIFIED && result.getStatus() == 2 && !result.isSplitForcedByNewline() && !onlyOverflowedFloatsLeft) || textAlignment == TextAlignment.JUSTIFIED_ALL) {
/*     */       
/* 690 */       if (processedRenderer != null) {
/* 691 */         Rectangle actualLineLayoutBox = layoutBox.clone();
/* 692 */         FloatingHelper.adjustLineAreaAccordingToFloats(floatRendererAreas, actualLineLayoutBox);
/* 693 */         processedRenderer.justify(actualLineLayoutBox.getWidth() - lineIndent);
/*     */       } 
/* 695 */     } else if (textAlignment != TextAlignment.LEFT && processedRenderer != null) {
/* 696 */       Rectangle actualLineLayoutBox = layoutBox.clone();
/* 697 */       FloatingHelper.adjustLineAreaAccordingToFloats(floatRendererAreas, actualLineLayoutBox);
/* 698 */       float deltaX = Math.max(0.0F, actualLineLayoutBox.getWidth() - lineIndent - processedRenderer.getOccupiedArea().getBBox().getWidth());
/* 699 */       switch (textAlignment) {
/*     */         case RIGHT:
/* 701 */           alignStaticKids(processedRenderer, deltaX);
/*     */           break;
/*     */         case CENTER:
/* 704 */           alignStaticKids(processedRenderer, deltaX / 2.0F);
/*     */           break;
/*     */         case JUSTIFIED:
/* 707 */           if (BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7)))
/* 708 */             alignStaticKids(processedRenderer, deltaX); 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/ParagraphRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */