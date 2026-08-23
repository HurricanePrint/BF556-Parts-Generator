/*      */ package com.itextpdf.layout.renderer;
/*      */ 
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.kernel.geom.AffineTransform;
/*      */ import com.itextpdf.kernel.geom.Point;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*      */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*      */ import com.itextpdf.layout.borders.Border;
/*      */ import com.itextpdf.layout.element.IElement;
/*      */ import com.itextpdf.layout.layout.LayoutArea;
/*      */ import com.itextpdf.layout.layout.LayoutContext;
/*      */ import com.itextpdf.layout.layout.LayoutResult;
/*      */ import com.itextpdf.layout.layout.MinMaxWidthLayoutResult;
/*      */ import com.itextpdf.layout.layout.PositionedLayoutContext;
/*      */ import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
/*      */ import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*      */ import com.itextpdf.layout.property.AreaBreakType;
/*      */ import com.itextpdf.layout.property.ClearPropertyValue;
/*      */ import com.itextpdf.layout.property.FloatPropertyValue;
/*      */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*      */ import com.itextpdf.layout.property.UnitValue;
/*      */ import com.itextpdf.layout.property.VerticalAlignment;
/*      */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class BlockRenderer
/*      */   extends AbstractRenderer
/*      */ {
/*      */   protected BlockRenderer(IElement modelElement) {
/*   86 */     super(modelElement);
/*      */   }
/*      */   
/*      */   public LayoutResult layout(LayoutContext layoutContext) {
/*      */     List<Rectangle> areas;
/*   91 */     this.isLastRendererForModelElement = true;
/*      */     
/*   93 */     Map<Integer, IRenderer> waitingFloatsSplitRenderers = new LinkedHashMap<>();
/*   94 */     List<IRenderer> waitingOverflowFloatRenderers = new ArrayList<>();
/*   95 */     boolean floatOverflowedCompletely = false;
/*   96 */     boolean wasHeightClipped = false;
/*   97 */     boolean wasParentsHeightClipped = layoutContext.isClippedHeight();
/*   98 */     int pageNumber = layoutContext.getArea().getPageNumber();
/*      */     
/*  100 */     boolean isPositioned = isPositioned();
/*      */     
/*  102 */     Rectangle parentBBox = layoutContext.getArea().getBBox().clone();
/*      */     
/*  104 */     List<Rectangle> floatRendererAreas = layoutContext.getFloatRendererAreas();
/*  105 */     FloatPropertyValue floatPropertyValue = getProperty(99);
/*  106 */     Float rotation = getPropertyAsFloat(55);
/*      */     
/*  108 */     OverflowPropertyValue overflowX = getProperty(103);
/*      */     
/*  110 */     MarginsCollapseHandler marginsCollapseHandler = null;
/*  111 */     boolean marginsCollapsingEnabled = Boolean.TRUE.equals(getPropertyAsBoolean(89));
/*  112 */     if (marginsCollapsingEnabled) {
/*  113 */       marginsCollapseHandler = new MarginsCollapseHandler(this, layoutContext.getMarginsCollapseInfo());
/*      */     }
/*  115 */     Float blockWidth = retrieveWidth(parentBBox.getWidth());
/*  116 */     if (rotation != null || isFixedLayout()) {
/*  117 */       parentBBox.moveDown(1000000.0F - parentBBox.getHeight()).setHeight(1000000.0F);
/*      */     }
/*  119 */     if (rotation != null && !FloatingHelper.isRendererFloating(this, floatPropertyValue)) {
/*  120 */       blockWidth = RotationUtils.retrieveRotatedLayoutWidth(parentBBox.getWidth(), this);
/*      */     }
/*  122 */     boolean includeFloatsInOccupiedArea = BlockFormattingContextUtil.isRendererCreateBfc(this);
/*  123 */     float clearHeightCorrection = FloatingHelper.calculateClearHeightCorrection(this, floatRendererAreas, parentBBox);
/*  124 */     FloatingHelper.applyClearance(parentBBox, marginsCollapseHandler, clearHeightCorrection, FloatingHelper.isRendererFloating(this));
/*  125 */     if (FloatingHelper.isRendererFloating(this, floatPropertyValue)) {
/*  126 */       blockWidth = FloatingHelper.adjustFloatedBlockLayoutBox(this, parentBBox, blockWidth, floatRendererAreas, floatPropertyValue, overflowX);
/*  127 */       floatRendererAreas = new ArrayList<>();
/*      */     } 
/*      */     
/*  130 */     boolean isCellRenderer = this instanceof CellRenderer;
/*  131 */     if (marginsCollapsingEnabled) {
/*  132 */       marginsCollapseHandler.startMarginsCollapse(parentBBox);
/*      */     }
/*      */     
/*  135 */     Border[] borders = getBorders();
/*  136 */     UnitValue[] paddings = getPaddings();
/*      */     
/*  138 */     applyBordersPaddingsMargins(parentBBox, borders, paddings);
/*  139 */     Float blockMaxHeight = retrieveMaxHeight();
/*      */ 
/*      */ 
/*      */     
/*  143 */     OverflowPropertyValue overflowY = ((null == blockMaxHeight || blockMaxHeight.floatValue() > parentBBox.getHeight()) && !wasParentsHeightClipped) ? OverflowPropertyValue.FIT : getProperty(104);
/*  144 */     applyWidth(parentBBox, blockWidth, overflowX);
/*  145 */     wasHeightClipped = applyMaxHeight(parentBBox, blockMaxHeight, marginsCollapseHandler, isCellRenderer, wasParentsHeightClipped, overflowY);
/*      */ 
/*      */     
/*  148 */     if (isPositioned) {
/*  149 */       areas = Collections.singletonList(parentBBox);
/*      */     } else {
/*  151 */       areas = initElementAreas(new LayoutArea(pageNumber, parentBBox));
/*      */     } 
/*      */     
/*  154 */     this.occupiedArea = new LayoutArea(pageNumber, new Rectangle(parentBBox.getX(), parentBBox.getY() + parentBBox.getHeight(), parentBBox.getWidth(), 0.0F));
/*  155 */     shrinkOccupiedAreaForAbsolutePosition();
/*  156 */     int currentAreaPos = 0;
/*      */     
/*  158 */     Rectangle layoutBox = ((Rectangle)areas.get(0)).clone();
/*      */ 
/*      */     
/*  161 */     Set<Rectangle> nonChildFloatingRendererAreas = new HashSet<>(floatRendererAreas);
/*      */ 
/*      */     
/*  164 */     IRenderer causeOfNothing = null;
/*  165 */     boolean anythingPlaced = false;
/*  166 */     for (int childPos = 0; childPos < this.childRenderers.size(); childPos++) {
/*  167 */       IRenderer childRenderer = this.childRenderers.get(childPos);
/*      */       
/*  169 */       childRenderer.setParent(this);
/*  170 */       MarginsCollapseInfo childMarginsInfo = null;
/*      */       
/*  172 */       if (floatOverflowedCompletely && FloatingHelper.isRendererFloating(childRenderer)) {
/*  173 */         waitingFloatsSplitRenderers.put(Integer.valueOf(childPos), null);
/*  174 */         waitingOverflowFloatRenderers.add(childRenderer);
/*      */ 
/*      */       
/*      */       }
/*  178 */       else if (!waitingOverflowFloatRenderers.isEmpty() && FloatingHelper.isClearanceApplied(waitingOverflowFloatRenderers, (ClearPropertyValue)childRenderer.getProperty(100))) {
/*  179 */         if (FloatingHelper.isRendererFloating(childRenderer)) {
/*  180 */           waitingFloatsSplitRenderers.put(Integer.valueOf(childPos), null);
/*  181 */           waitingOverflowFloatRenderers.add(childRenderer);
/*  182 */           floatOverflowedCompletely = true;
/*      */         } else {
/*      */           
/*  185 */           if (marginsCollapsingEnabled && !isCellRenderer) {
/*  186 */             marginsCollapseHandler.endMarginsCollapse(layoutBox);
/*      */           }
/*      */           
/*  189 */           FloatingHelper.includeChildFloatsInOccupiedArea(floatRendererAreas, this, nonChildFloatingRendererAreas);
/*  190 */           fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/*      */           
/*  192 */           LayoutResult result = new LayoutResult(3, null, null, childRenderer);
/*  193 */           int i = anythingPlaced ? 2 : 3;
/*  194 */           AbstractRenderer[] splitAndOverflowRenderers = createSplitAndOverflowRenderers(childPos, i, result, waitingFloatsSplitRenderers, waitingOverflowFloatRenderers);
/*      */           
/*  196 */           AbstractRenderer abstractRenderer1 = splitAndOverflowRenderers[0];
/*  197 */           AbstractRenderer abstractRenderer2 = splitAndOverflowRenderers[1];
/*      */           
/*  199 */           updateHeightsOnSplit(wasHeightClipped, abstractRenderer1, abstractRenderer2);
/*  200 */           applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/*  201 */           applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/*  202 */           applyMargins(this.occupiedArea.getBBox(), true);
/*      */           
/*  204 */           if (Boolean.TRUE.equals(getPropertyAsBoolean(26)) || wasHeightClipped) {
/*  205 */             LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, layoutContext.getFloatRendererAreas(), layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*  206 */             return new LayoutResult(1, editedArea, abstractRenderer1, null, null);
/*      */           } 
/*  208 */           if (i != 3) {
/*  209 */             LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, layoutContext.getFloatRendererAreas(), layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*  210 */             return (new LayoutResult(i, editedArea, abstractRenderer1, abstractRenderer2, null)).setAreaBreak(result.getAreaBreak());
/*      */           } 
/*  212 */           floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/*  213 */           return (new LayoutResult(i, null, null, abstractRenderer2, result.getCauseOfNothing())).setAreaBreak(result.getAreaBreak());
/*      */         } 
/*      */       } else {
/*      */         LayoutResult result;
/*      */         
/*  218 */         if (marginsCollapsingEnabled)
/*  219 */           childMarginsInfo = marginsCollapseHandler.startChildMarginsHandling(childRenderer, layoutBox); 
/*      */         while (true) {
/*  221 */           if ((result = childRenderer.setParent(this).layout(new LayoutContext(new LayoutArea(pageNumber, layoutBox), childMarginsInfo, floatRendererAreas, (wasHeightClipped || wasParentsHeightClipped))))
/*  222 */             .getStatus() != 1) {
/*      */             
/*  224 */             if (Boolean.TRUE.equals(getPropertyAsBoolean(87)) || Boolean.TRUE
/*  225 */               .equals(getPropertyAsBoolean(86))) {
/*  226 */               this.occupiedArea.setBBox(Rectangle.getCommonRectangle(new Rectangle[] { this.occupiedArea.getBBox(), layoutBox }));
/*  227 */             } else if (result.getOccupiedArea() != null && result.getStatus() != 3) {
/*  228 */               this.occupiedArea.setBBox(Rectangle.getCommonRectangle(new Rectangle[] { this.occupiedArea.getBBox(), result.getOccupiedArea().getBBox() }));
/*  229 */               fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/*      */             } 
/*      */             
/*  232 */             if (marginsCollapsingEnabled && result.getStatus() != 3) {
/*  233 */               marginsCollapseHandler.endChildMarginsHandling(layoutBox);
/*      */             }
/*      */             
/*  236 */             if (FloatingHelper.isRendererFloating(childRenderer)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  243 */               boolean immediatelyReturnNothing = (result.getStatus() == 3 && !anythingPlaced && floatRendererAreas.isEmpty() && isFirstOnRootArea());
/*  244 */               if (!immediatelyReturnNothing) {
/*  245 */                 waitingFloatsSplitRenderers.put(Integer.valueOf(childPos), (result.getStatus() == 2) ? result.getSplitRenderer() : null);
/*  246 */                 waitingOverflowFloatRenderers.add(result.getOverflowRenderer());
/*  247 */                 floatOverflowedCompletely = (result.getStatus() == 3);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*  252 */             if (marginsCollapsingEnabled) {
/*  253 */               marginsCollapseHandler.endMarginsCollapse(layoutBox);
/*      */             }
/*      */ 
/*      */             
/*  257 */             FloatingHelper.includeChildFloatsInOccupiedArea(floatRendererAreas, this, nonChildFloatingRendererAreas);
/*  258 */             fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/*      */             
/*  260 */             if (result.getSplitRenderer() != null)
/*      */             {
/*      */ 
/*      */               
/*  264 */               alignChildHorizontally(result.getSplitRenderer(), this.occupiedArea.getBBox());
/*      */             }
/*      */ 
/*      */             
/*  268 */             if (null == causeOfNothing && null != result.getCauseOfNothing()) {
/*  269 */               causeOfNothing = result.getCauseOfNothing();
/*      */             }
/*      */ 
/*      */             
/*  273 */             if (currentAreaPos + 1 < areas.size() && (result.getAreaBreak() == null || result.getAreaBreak().getType() != AreaBreakType.NEXT_PAGE)) {
/*  274 */               if (result.getStatus() == 2) {
/*  275 */                 this.childRenderers.set(childPos, result.getSplitRenderer());
/*      */                 
/*  277 */                 this.childRenderers.add(childPos + 1, result.getOverflowRenderer());
/*      */               } else {
/*  279 */                 if (result.getOverflowRenderer() != null) {
/*  280 */                   this.childRenderers.set(childPos, result.getOverflowRenderer());
/*      */                 } else {
/*  282 */                   this.childRenderers.remove(childPos);
/*      */                 } 
/*  284 */                 childPos--;
/*      */               } 
/*  286 */               layoutBox = ((Rectangle)areas.get(++currentAreaPos)).clone();
/*      */               break;
/*      */             } 
/*  289 */             if (result.getStatus() == 2) {
/*  290 */               if (currentAreaPos + 1 == areas.size()) {
/*      */                 
/*  292 */                 AbstractRenderer[] splitAndOverflowRenderers = createSplitAndOverflowRenderers(childPos, 2, result, waitingFloatsSplitRenderers, waitingOverflowFloatRenderers);
/*      */ 
/*      */                 
/*  295 */                 AbstractRenderer abstractRenderer1 = splitAndOverflowRenderers[0];
/*  296 */                 AbstractRenderer abstractRenderer2 = splitAndOverflowRenderers[1];
/*  297 */                 abstractRenderer2.deleteOwnProperty(26);
/*      */                 
/*  299 */                 updateHeightsOnSplit(wasHeightClipped, abstractRenderer1, abstractRenderer2);
/*  300 */                 applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/*  301 */                 applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/*  302 */                 applyMargins(this.occupiedArea.getBBox(), true);
/*      */                 
/*  304 */                 correctFixedLayout(layoutBox);
/*      */                 
/*  306 */                 LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, layoutContext.getFloatRendererAreas(), layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*  307 */                 if (wasHeightClipped) {
/*  308 */                   return new LayoutResult(1, editedArea, abstractRenderer1, null);
/*      */                 }
/*  310 */                 return new LayoutResult(2, editedArea, abstractRenderer1, abstractRenderer2, causeOfNothing);
/*      */               } 
/*      */               
/*  313 */               this.childRenderers.set(childPos, result.getSplitRenderer());
/*  314 */               this.childRenderers.add(childPos + 1, result.getOverflowRenderer());
/*  315 */               layoutBox = ((Rectangle)areas.get(++currentAreaPos)).clone();
/*      */               break;
/*      */             } 
/*  318 */             if (result.getStatus() == 3) {
/*  319 */               boolean keepTogether = isKeepTogether();
/*  320 */               int i = (anythingPlaced && !keepTogether) ? 2 : 3;
/*      */               
/*  322 */               AbstractRenderer[] splitAndOverflowRenderers = createSplitAndOverflowRenderers(childPos, i, result, waitingFloatsSplitRenderers, waitingOverflowFloatRenderers);
/*      */ 
/*      */               
/*  325 */               AbstractRenderer abstractRenderer1 = splitAndOverflowRenderers[0];
/*  326 */               AbstractRenderer abstractRenderer2 = splitAndOverflowRenderers[1];
/*      */               
/*  328 */               if (isRelativePosition() && this.positionedRenderers.size() > 0) {
/*  329 */                 abstractRenderer2.positionedRenderers = new ArrayList<>(this.positionedRenderers);
/*      */               }
/*      */               
/*  332 */               updateHeightsOnSplit(wasHeightClipped, abstractRenderer1, abstractRenderer2);
/*      */               
/*  334 */               if (keepTogether) {
/*  335 */                 abstractRenderer1 = null;
/*  336 */                 abstractRenderer2.childRenderers.clear();
/*  337 */                 abstractRenderer2.childRenderers = new ArrayList<>(this.childRenderers);
/*      */               } 
/*      */               
/*  340 */               correctFixedLayout(layoutBox);
/*      */               
/*  342 */               applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/*  343 */               applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/*  344 */               applyMargins(this.occupiedArea.getBBox(), true);
/*      */               
/*  346 */               applyAbsolutePositionIfNeeded(layoutContext);
/*      */               
/*  348 */               if (Boolean.TRUE.equals(getPropertyAsBoolean(26)) || wasHeightClipped) {
/*  349 */                 LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, layoutContext.getFloatRendererAreas(), layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*  350 */                 return new LayoutResult(1, editedArea, abstractRenderer1, null, null);
/*      */               } 
/*  352 */               if (i != 3) {
/*  353 */                 LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, layoutContext.getFloatRendererAreas(), layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*  354 */                 return (new LayoutResult(i, editedArea, abstractRenderer1, abstractRenderer2, null)).setAreaBreak(result.getAreaBreak());
/*      */               } 
/*  356 */               floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/*  357 */               return (new LayoutResult(i, null, null, abstractRenderer2, result.getCauseOfNothing())).setAreaBreak(result.getAreaBreak());
/*      */             } 
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */         } 
/*  363 */         anythingPlaced = (anythingPlaced || result.getStatus() != 3);
/*      */ 
/*      */         
/*  366 */         if (result.getOccupiedArea() != null && (!FloatingHelper.isRendererFloating(childRenderer) || includeFloatsInOccupiedArea)) {
/*  367 */           this.occupiedArea.setBBox(Rectangle.getCommonRectangle(new Rectangle[] { this.occupiedArea.getBBox(), result.getOccupiedArea().getBBox() }));
/*  368 */           fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/*      */         } 
/*  370 */         if (marginsCollapsingEnabled) {
/*  371 */           marginsCollapseHandler.endChildMarginsHandling(layoutBox);
/*      */         }
/*  373 */         if (result.getStatus() == 1) {
/*  374 */           layoutBox.setHeight(result.getOccupiedArea().getBBox().getY() - layoutBox.getY());
/*  375 */           if (childRenderer.getOccupiedArea() != null)
/*      */           {
/*      */ 
/*      */             
/*  379 */             alignChildHorizontally(childRenderer, this.occupiedArea.getBBox());
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  384 */         if (null == causeOfNothing && null != result.getCauseOfNothing()) {
/*  385 */           causeOfNothing = result.getCauseOfNothing();
/*      */         }
/*      */       } 
/*      */     } 
/*  389 */     if (includeFloatsInOccupiedArea) {
/*  390 */       FloatingHelper.includeChildFloatsInOccupiedArea(floatRendererAreas, this, nonChildFloatingRendererAreas);
/*  391 */       fixOccupiedAreaIfOverflowedX(overflowX, layoutBox);
/*      */     } 
/*  393 */     if (wasHeightClipped) {
/*  394 */       fixOccupiedAreaIfOverflowedY(overflowY, layoutBox);
/*      */     }
/*  396 */     if (marginsCollapsingEnabled) {
/*  397 */       marginsCollapseHandler.endMarginsCollapse(layoutBox);
/*      */     }
/*      */     
/*  400 */     if (Boolean.TRUE.equals(getPropertyAsBoolean(86))) {
/*  401 */       this.occupiedArea.setBBox(Rectangle.getCommonRectangle(new Rectangle[] { this.occupiedArea.getBBox(), layoutBox }));
/*      */     }
/*      */     
/*  404 */     int layoutResult = 1;
/*  405 */     boolean processOverflowedFloats = (!waitingOverflowFloatRenderers.isEmpty() && !wasHeightClipped && !Boolean.TRUE.equals(getPropertyAsBoolean(26)));
/*      */     
/*  407 */     AbstractRenderer overflowRenderer = null;
/*  408 */     if (!includeFloatsInOccupiedArea || !processOverflowedFloats) {
/*  409 */       overflowRenderer = applyMinHeight(overflowY, layoutBox);
/*      */     }
/*      */     
/*  412 */     boolean minHeightOverflow = (overflowRenderer != null);
/*  413 */     if (minHeightOverflow && isKeepTogether()) {
/*  414 */       floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/*  415 */       return new LayoutResult(3, null, null, this, this);
/*      */     } 
/*      */ 
/*      */     
/*  419 */     if (overflowRenderer != null || processOverflowedFloats) {
/*  420 */       layoutResult = (!anythingPlaced && !waitingOverflowFloatRenderers.isEmpty()) ? 3 : 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  427 */     if (processOverflowedFloats) {
/*  428 */       if (overflowRenderer == null || layoutResult == 3)
/*      */       {
/*      */         
/*  431 */         overflowRenderer = createOverflowRenderer(layoutResult);
/*      */       }
/*  433 */       overflowRenderer.getChildRenderers().addAll(waitingOverflowFloatRenderers);
/*  434 */       if (layoutResult == 2 && !minHeightOverflow && !includeFloatsInOccupiedArea) {
/*  435 */         FloatingHelper.removeParentArtifactsOnPageSplitIfOnlyFloatsOverflow(overflowRenderer);
/*      */       }
/*      */     } 
/*  438 */     AbstractRenderer splitRenderer = this;
/*  439 */     if (waitingFloatsSplitRenderers.size() > 0 && layoutResult != 3) {
/*  440 */       splitRenderer = createSplitRenderer(layoutResult);
/*  441 */       splitRenderer.childRenderers = new ArrayList<>(this.childRenderers);
/*  442 */       replaceSplitRendererKidFloats(waitingFloatsSplitRenderers, splitRenderer);
/*      */       
/*  444 */       float usedHeight = this.occupiedArea.getBBox().getHeight();
/*  445 */       if (!includeFloatsInOccupiedArea) {
/*  446 */         Rectangle commonRectangle = Rectangle.getCommonRectangle(new Rectangle[] { layoutBox, this.occupiedArea.getBBox() });
/*  447 */         usedHeight = commonRectangle.getHeight();
/*      */       } 
/*      */       
/*  450 */       updateHeightsOnSplit(usedHeight, wasHeightClipped, splitRenderer, overflowRenderer, includeFloatsInOccupiedArea);
/*      */     } 
/*      */     
/*  453 */     if (this.positionedRenderers.size() > 0) {
/*  454 */       for (IRenderer childPositionedRenderer : this.positionedRenderers) {
/*  455 */         Rectangle fullBbox = this.occupiedArea.getBBox().clone();
/*      */ 
/*      */         
/*  458 */         float layoutMinHeight = 1000.0F;
/*  459 */         fullBbox.moveDown(layoutMinHeight).setHeight(layoutMinHeight + fullBbox.getHeight());
/*  460 */         LayoutArea parentArea = new LayoutArea(this.occupiedArea.getPageNumber(), this.occupiedArea.getBBox().clone());
/*  461 */         applyPaddings(parentArea.getBBox(), paddings, true);
/*      */         
/*  463 */         preparePositionedRendererAndAreaForLayout(childPositionedRenderer, fullBbox, parentArea.getBBox());
/*  464 */         childPositionedRenderer.layout((LayoutContext)new PositionedLayoutContext(new LayoutArea(this.occupiedArea.getPageNumber(), fullBbox), parentArea));
/*      */       } 
/*      */     }
/*      */     
/*  468 */     if (isPositioned) {
/*  469 */       correctFixedLayout(layoutBox);
/*      */     }
/*      */     
/*  472 */     applyPaddings(this.occupiedArea.getBBox(), paddings, true);
/*  473 */     applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/*  474 */     applyMargins(this.occupiedArea.getBBox(), true);
/*      */     
/*  476 */     applyAbsolutePositionIfNeeded(layoutContext);
/*      */     
/*  478 */     if (rotation != null) {
/*  479 */       applyRotationLayout(layoutContext.getArea().getBBox().clone());
/*  480 */       if (isNotFittingLayoutArea(layoutContext.getArea())) {
/*  481 */         if (isNotFittingWidth(layoutContext.getArea()) && !isNotFittingHeight(layoutContext.getArea())) {
/*  482 */           LoggerFactory.getLogger(getClass()).warn(MessageFormatUtil.format("Element does not fit current area. {0}", new Object[] { "It fits by height so it will be forced placed" }));
/*  483 */         } else if (!Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
/*  484 */           floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/*  485 */           return (LayoutResult)new MinMaxWidthLayoutResult(3, null, null, this, this);
/*      */         } 
/*      */       }
/*      */     } 
/*  489 */     applyVerticalAlignment();
/*      */     
/*  491 */     FloatingHelper.removeFloatsAboveRendererBottom(floatRendererAreas, this);
/*      */     
/*  493 */     if (layoutResult != 3) {
/*  494 */       LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, layoutContext.getFloatRendererAreas(), layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*  495 */       return new LayoutResult(layoutResult, editedArea, splitRenderer, overflowRenderer, causeOfNothing);
/*      */     } 
/*  497 */     if (this.positionedRenderers.size() > 0) {
/*  498 */       overflowRenderer.positionedRenderers = new ArrayList<>(this.positionedRenderers);
/*      */     }
/*  500 */     floatRendererAreas.retainAll(nonChildFloatingRendererAreas);
/*  501 */     return new LayoutResult(3, null, null, overflowRenderer, causeOfNothing);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AbstractRenderer createSplitRenderer(int layoutResult) {
/*  506 */     AbstractRenderer splitRenderer = (AbstractRenderer)getNextRenderer();
/*  507 */     splitRenderer.parent = this.parent;
/*  508 */     splitRenderer.modelElement = this.modelElement;
/*  509 */     splitRenderer.occupiedArea = this.occupiedArea;
/*  510 */     splitRenderer.isLastRendererForModelElement = false;
/*  511 */     splitRenderer.addAllProperties(getOwnProperties());
/*  512 */     return splitRenderer;
/*      */   }
/*      */   
/*      */   protected AbstractRenderer createOverflowRenderer(int layoutResult) {
/*  516 */     AbstractRenderer overflowRenderer = (AbstractRenderer)getNextRenderer();
/*  517 */     overflowRenderer.parent = this.parent;
/*  518 */     overflowRenderer.modelElement = this.modelElement;
/*  519 */     overflowRenderer.addAllProperties(getOwnProperties());
/*  520 */     return overflowRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void draw(DrawContext drawContext) {
/*  525 */     if (this.occupiedArea == null) {
/*  526 */       Logger logger = LoggerFactory.getLogger(BlockRenderer.class);
/*  527 */       logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Drawing won't be performed." }));
/*      */       
/*      */       return;
/*      */     } 
/*  531 */     boolean isTagged = drawContext.isTaggingEnabled();
/*  532 */     LayoutTaggingHelper taggingHelper = null;
/*  533 */     if (isTagged) {
/*  534 */       taggingHelper = getProperty(108);
/*  535 */       if (taggingHelper == null) {
/*  536 */         isTagged = false;
/*      */       } else {
/*  538 */         TagTreePointer tagPointer = taggingHelper.useAutoTaggingPointerAndRememberItsPosition(this);
/*  539 */         if (taggingHelper.createTag(this, tagPointer)) {
/*  540 */           tagPointer.getProperties()
/*  541 */             .addAttributes(0, AccessibleAttributesApplier.getListAttributes(this, tagPointer))
/*  542 */             .addAttributes(0, AccessibleAttributesApplier.getTableAttributes(this, tagPointer))
/*  543 */             .addAttributes(0, AccessibleAttributesApplier.getLayoutAttributes(this, tagPointer));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  548 */     beginTransformationIfApplied(drawContext.getCanvas());
/*  549 */     applyDestinationsAndAnnotation(drawContext);
/*      */     
/*  551 */     boolean isRelativePosition = isRelativePosition();
/*  552 */     if (isRelativePosition) {
/*  553 */       applyRelativePositioningTranslation(false);
/*      */     }
/*      */     
/*  556 */     beginElementOpacityApplying(drawContext);
/*  557 */     beginRotationIfApplied(drawContext.getCanvas());
/*      */     
/*  559 */     boolean overflowXHidden = isOverflowProperty(OverflowPropertyValue.HIDDEN, 103);
/*  560 */     boolean overflowYHidden = isOverflowProperty(OverflowPropertyValue.HIDDEN, 104);
/*  561 */     boolean processOverflow = (overflowXHidden || overflowYHidden);
/*      */     
/*  563 */     drawBackground(drawContext);
/*  564 */     drawBorder(drawContext);
/*      */     
/*  566 */     if (processOverflow) {
/*  567 */       Rectangle clippedArea; drawContext.getCanvas().saveState();
/*  568 */       int pageNumber = this.occupiedArea.getPageNumber();
/*      */       
/*  570 */       if (pageNumber < 1 || pageNumber > drawContext.getDocument().getNumberOfPages()) {
/*  571 */         clippedArea = new Rectangle(-500000.0F, -500000.0F, 1000000.0F, 1000000.0F);
/*      */       } else {
/*  573 */         clippedArea = drawContext.getDocument().getPage(pageNumber).getPageSize();
/*      */       } 
/*  575 */       Rectangle area = getBorderAreaBBox();
/*  576 */       if (overflowXHidden) {
/*  577 */         clippedArea.setX(area.getX()).setWidth(area.getWidth());
/*      */       }
/*  579 */       if (overflowYHidden) {
/*  580 */         clippedArea.setY(area.getY()).setHeight(area.getHeight());
/*      */       }
/*  582 */       drawContext.getCanvas().rectangle(clippedArea).clip().endPath();
/*      */     } 
/*      */     
/*  585 */     drawChildren(drawContext);
/*  586 */     drawPositionedChildren(drawContext);
/*      */     
/*  588 */     if (processOverflow) {
/*  589 */       drawContext.getCanvas().restoreState();
/*      */     }
/*      */     
/*  592 */     endRotationIfApplied(drawContext.getCanvas());
/*  593 */     endElementOpacityApplying(drawContext);
/*      */     
/*  595 */     if (isRelativePosition) {
/*  596 */       applyRelativePositioningTranslation(true);
/*      */     }
/*      */     
/*  599 */     if (isTagged) {
/*  600 */       if (this.isLastRendererForModelElement) {
/*  601 */         taggingHelper.finishTaggingHint(this);
/*      */       }
/*  603 */       taggingHelper.restoreAutoTaggingPointerPosition(this);
/*      */     } 
/*      */     
/*  606 */     this.flushed = true;
/*  607 */     endTransformationIfApplied(drawContext.getCanvas());
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle getOccupiedAreaBBox() {
/*  612 */     Rectangle bBox = this.occupiedArea.getBBox().clone();
/*  613 */     Float rotationAngle = getProperty(55);
/*  614 */     if (rotationAngle != null) {
/*  615 */       if (!hasOwnProperty(57) || !hasOwnProperty(56)) {
/*  616 */         Logger logger = LoggerFactory.getLogger(BlockRenderer.class);
/*  617 */         logger.error(MessageFormatUtil.format("Rotation was not correctly processed for {0}", new Object[] { getClass().getSimpleName() }));
/*      */       } else {
/*  619 */         bBox.setWidth(getPropertyAsFloat(57).floatValue());
/*  620 */         bBox.setHeight(getPropertyAsFloat(56).floatValue());
/*      */       } 
/*      */     }
/*  623 */     return bBox;
/*      */   }
/*      */   
/*      */   protected void applyVerticalAlignment() {
/*  627 */     VerticalAlignment verticalAlignment = getProperty(75);
/*  628 */     if (verticalAlignment == null || verticalAlignment == VerticalAlignment.TOP || this.childRenderers.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  632 */     float lowestChildBottom = Float.MAX_VALUE;
/*  633 */     if (FloatingHelper.isRendererFloating(this) || this instanceof CellRenderer) {
/*      */       
/*  635 */       for (IRenderer child : this.childRenderers) {
/*  636 */         if (child.getOccupiedArea().getBBox().getBottom() < lowestChildBottom) {
/*  637 */           lowestChildBottom = child.getOccupiedArea().getBBox().getBottom();
/*      */         }
/*      */       } 
/*      */     } else {
/*  641 */       int lastChildIndex = this.childRenderers.size() - 1;
/*  642 */       while (lastChildIndex >= 0) {
/*  643 */         IRenderer child = this.childRenderers.get(lastChildIndex--);
/*  644 */         if (!FloatingHelper.isRendererFloating(child)) {
/*  645 */           lowestChildBottom = child.getOccupiedArea().getBBox().getBottom();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  651 */     if (lowestChildBottom == Float.MAX_VALUE) {
/*      */       return;
/*      */     }
/*      */     
/*  655 */     float deltaY = lowestChildBottom - getInnerAreaBBox().getY();
/*  656 */     if (deltaY < 0.0F) {
/*      */       return;
/*      */     }
/*  659 */     switch (verticalAlignment) {
/*      */       case BOTTOM:
/*  661 */         for (IRenderer child : this.childRenderers) {
/*  662 */           child.move(0.0F, -deltaY);
/*      */         }
/*      */         break;
/*      */       case MIDDLE:
/*  666 */         for (IRenderer child : this.childRenderers) {
/*  667 */           child.move(0.0F, -deltaY / 2.0F);
/*      */         }
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void applyRotationLayout(Rectangle layoutBox) {
/*  674 */     float angle = getPropertyAsFloat(55).floatValue();
/*      */     
/*  676 */     float x = this.occupiedArea.getBBox().getX();
/*  677 */     float y = this.occupiedArea.getBBox().getY();
/*  678 */     float height = this.occupiedArea.getBBox().getHeight();
/*  679 */     float width = this.occupiedArea.getBBox().getWidth();
/*      */     
/*  681 */     setProperty(57, Float.valueOf(width));
/*  682 */     setProperty(56, Float.valueOf(height));
/*      */     
/*  684 */     AffineTransform rotationTransform = new AffineTransform();
/*      */ 
/*      */     
/*  687 */     if (isPositioned()) {
/*  688 */       Float rotationPointX = getPropertyAsFloat(58);
/*  689 */       Float rotationPointY = getPropertyAsFloat(59);
/*      */       
/*  691 */       if (rotationPointX == null || rotationPointY == null) {
/*      */         
/*  693 */         rotationPointX = Float.valueOf(x);
/*  694 */         rotationPointY = Float.valueOf(y);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  699 */       rotationTransform.translate(rotationPointX.floatValue(), rotationPointY.floatValue());
/*      */ 
/*      */       
/*  702 */       rotationTransform.rotate(angle);
/*      */ 
/*      */       
/*  705 */       rotationTransform.translate(-rotationPointX.floatValue(), -rotationPointY.floatValue());
/*      */       
/*  707 */       List<Point> rotatedPoints = transformPoints(rectangleToPointsList(this.occupiedArea.getBBox()), rotationTransform);
/*  708 */       Rectangle newBBox = calculateBBox(rotatedPoints);
/*      */ 
/*      */       
/*  711 */       this.occupiedArea.getBBox().setWidth(newBBox.getWidth());
/*  712 */       this.occupiedArea.getBBox().setHeight(newBBox.getHeight());
/*  713 */       float occupiedAreaShiftX = newBBox.getX() - x;
/*  714 */       float occupiedAreaShiftY = newBBox.getY() - y;
/*  715 */       move(occupiedAreaShiftX, occupiedAreaShiftY);
/*      */     } else {
/*  717 */       rotationTransform = AffineTransform.getRotateInstance(angle);
/*  718 */       List<Point> rotatedPoints = transformPoints(rectangleToPointsList(this.occupiedArea.getBBox()), rotationTransform);
/*  719 */       float[] shift = calculateShiftToPositionBBoxOfPointsAt(x, y + height, rotatedPoints);
/*      */       
/*  721 */       for (Point point : rotatedPoints) {
/*  722 */         point.setLocation(point.getX() + shift[0], point.getY() + shift[1]);
/*      */       }
/*      */       
/*  725 */       Rectangle newBBox = calculateBBox(rotatedPoints);
/*      */       
/*  727 */       this.occupiedArea.getBBox().setWidth(newBBox.getWidth());
/*  728 */       this.occupiedArea.getBBox().setHeight(newBBox.getHeight());
/*      */       
/*  730 */       float heightDiff = height - newBBox.getHeight();
/*  731 */       move(0.0F, heightDiff);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AffineTransform createRotationTransformInsideOccupiedArea() {
/*  743 */     Float angle = getProperty(55);
/*  744 */     AffineTransform rotationTransform = AffineTransform.getRotateInstance(angle.floatValue());
/*      */     
/*  746 */     Rectangle contentBox = getOccupiedAreaBBox();
/*  747 */     List<Point> rotatedContentBoxPoints = transformPoints(rectangleToPointsList(contentBox), rotationTransform);
/*      */ 
/*      */     
/*  750 */     float[] shift = calculateShiftToPositionBBoxOfPointsAt(this.occupiedArea.getBBox().getLeft(), this.occupiedArea.getBBox().getTop(), rotatedContentBoxPoints);
/*  751 */     rotationTransform.preConcatenate(AffineTransform.getTranslateInstance(shift[0], shift[1]));
/*      */     
/*  753 */     return rotationTransform;
/*      */   }
/*      */   
/*      */   protected void beginRotationIfApplied(PdfCanvas canvas) {
/*  757 */     Float angle = getPropertyAsFloat(55);
/*  758 */     if (angle != null) {
/*  759 */       if (!hasOwnProperty(56)) {
/*  760 */         Logger logger = LoggerFactory.getLogger(BlockRenderer.class);
/*  761 */         logger.error(MessageFormatUtil.format("Rotation was not correctly processed for {0}", new Object[] { getClass().getSimpleName() }));
/*      */       } else {
/*  763 */         AffineTransform transform = createRotationTransformInsideOccupiedArea();
/*  764 */         canvas.saveState().concatMatrix(transform);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void endRotationIfApplied(PdfCanvas canvas) {
/*  770 */     Float angle = getPropertyAsFloat(55);
/*  771 */     if (angle != null && hasOwnProperty(56)) {
/*  772 */       canvas.restoreState();
/*      */     }
/*      */   }
/*      */   
/*      */   void correctFixedLayout(Rectangle layoutBox) {
/*  777 */     if (isFixedLayout()) {
/*  778 */       float y = getPropertyAsFloat(14).floatValue();
/*  779 */       move(0.0F, y - this.occupiedArea.getBBox().getY());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void applyWidth(Rectangle parentBBox, Float blockWidth, OverflowPropertyValue overflowX) {
/*  786 */     Float rotation = getPropertyAsFloat(55);
/*      */     
/*  788 */     if (blockWidth != null && (blockWidth
/*  789 */       .floatValue() < parentBBox.getWidth() || 
/*  790 */       isPositioned() || rotation != null || 
/*      */       
/*  792 */       !isOverflowFit(overflowX))) {
/*  793 */       parentBBox.setWidth(blockWidth.floatValue());
/*      */     } else {
/*  795 */       Float minWidth = retrieveMinWidth(parentBBox.getWidth());
/*      */       
/*  797 */       if (minWidth != null && minWidth.floatValue() > parentBBox.getWidth()) {
/*  798 */         parentBBox.setWidth(minWidth.floatValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean applyMaxHeight(Rectangle parentBBox, Float blockMaxHeight, MarginsCollapseHandler marginsCollapseHandler, boolean isCellRenderer, boolean wasParentsHeightClipped, OverflowPropertyValue overflowY) {
/*  805 */     if (null == blockMaxHeight || (blockMaxHeight.floatValue() >= parentBBox.getHeight() && isOverflowFit(overflowY))) {
/*  806 */       return false;
/*      */     }
/*  808 */     boolean wasHeightClipped = false;
/*  809 */     if (blockMaxHeight.floatValue() <= parentBBox.getHeight()) {
/*  810 */       wasHeightClipped = true;
/*      */     }
/*  812 */     float heightDelta = parentBBox.getHeight() - blockMaxHeight.floatValue();
/*  813 */     if (marginsCollapseHandler != null && !isCellRenderer) {
/*  814 */       marginsCollapseHandler.processFixedHeightAdjustment(heightDelta);
/*      */     }
/*  816 */     parentBBox.moveUp(heightDelta).setHeight(blockMaxHeight.floatValue());
/*  817 */     return wasHeightClipped;
/*      */   }
/*      */   
/*      */   AbstractRenderer applyMinHeight(OverflowPropertyValue overflowY, Rectangle layoutBox) {
/*  821 */     AbstractRenderer overflowRenderer = null;
/*  822 */     Float blockMinHeight = retrieveMinHeight();
/*  823 */     if (!Boolean.TRUE.equals(getPropertyAsBoolean(26)) && null != blockMinHeight && blockMinHeight.floatValue() > this.occupiedArea.getBBox().getHeight()) {
/*  824 */       float blockBottom = this.occupiedArea.getBBox().getBottom() - blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight();
/*  825 */       if (isFixedLayout()) {
/*  826 */         this.occupiedArea.getBBox().setY(blockBottom).setHeight(blockMinHeight.floatValue());
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  831 */       else if (isOverflowFit(overflowY) && blockBottom + 1.0E-4F < layoutBox.getBottom()) {
/*  832 */         float hDelta = this.occupiedArea.getBBox().getBottom() - layoutBox.getBottom();
/*  833 */         this.occupiedArea.getBBox()
/*  834 */           .increaseHeight(hDelta)
/*  835 */           .setY(layoutBox.getBottom());
/*      */         
/*  837 */         if (this.occupiedArea.getBBox().getHeight() < 0.0F) {
/*  838 */           this.occupiedArea.getBBox().setHeight(0.0F);
/*      */         }
/*      */         
/*  841 */         this.isLastRendererForModelElement = false;
/*  842 */         overflowRenderer = createOverflowRenderer(2);
/*  843 */         overflowRenderer.updateMinHeight(UnitValue.createPointValue(blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight()));
/*  844 */         if (hasProperty(27)) {
/*  845 */           overflowRenderer.updateHeight(UnitValue.createPointValue(retrieveHeight().floatValue() - this.occupiedArea.getBBox().getHeight()));
/*      */         }
/*      */       } else {
/*  848 */         this.occupiedArea.getBBox().setY(blockBottom).setHeight(blockMinHeight.floatValue());
/*      */       } 
/*      */     } 
/*      */     
/*  852 */     return overflowRenderer;
/*      */   }
/*      */   
/*      */   void fixOccupiedAreaIfOverflowedX(OverflowPropertyValue overflowX, Rectangle layoutBox) {
/*  856 */     if (isOverflowFit(overflowX)) {
/*      */       return;
/*      */     }
/*      */     
/*  860 */     if (this.occupiedArea.getBBox().getWidth() > layoutBox.getWidth() || this.occupiedArea.getBBox().getLeft() < layoutBox.getLeft()) {
/*  861 */       this.occupiedArea.getBBox().setX(layoutBox.getX()).setWidth(layoutBox.getWidth());
/*      */     }
/*      */   }
/*      */   
/*      */   void fixOccupiedAreaIfOverflowedY(OverflowPropertyValue overflowY, Rectangle layoutBox) {
/*  866 */     if (isOverflowFit(overflowY)) {
/*      */       return;
/*      */     }
/*  869 */     if (this.occupiedArea.getBBox().getBottom() < layoutBox.getBottom()) {
/*  870 */       float difference = layoutBox.getBottom() - this.occupiedArea.getBBox().getBottom();
/*  871 */       this.occupiedArea.getBBox().moveUp(difference).decreaseHeight(difference);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected float applyBordersPaddingsMargins(Rectangle parentBBox, Border[] borders, UnitValue[] paddings) {
/*  876 */     float parentWidth = parentBBox.getWidth();
/*      */     
/*  878 */     applyMargins(parentBBox, false);
/*  879 */     applyBorderBox(parentBBox, borders, false);
/*  880 */     if (isFixedLayout()) {
/*  881 */       parentBBox.setX(getPropertyAsFloat(34).floatValue());
/*      */     }
/*  883 */     applyPaddings(parentBBox, paddings, false);
/*  884 */     return parentWidth - parentBBox.getWidth();
/*      */   }
/*      */ 
/*      */   
/*      */   public MinMaxWidth getMinMaxWidth() {
/*  889 */     MinMaxWidth minMaxWidth = new MinMaxWidth(calculateAdditionalWidth(this));
/*  890 */     if (!setMinMaxWidthBasedOnFixedWidth(minMaxWidth)) {
/*  891 */       Float minWidth = hasAbsoluteUnitValue(80) ? retrieveMinWidth(0.0F) : null;
/*  892 */       Float maxWidth = hasAbsoluteUnitValue(79) ? retrieveMaxWidth(0.0F) : null;
/*  893 */       if (minWidth == null || maxWidth == null) {
/*  894 */         AbstractWidthHandler handler = new MaxMaxWidthHandler(minMaxWidth);
/*  895 */         int epsilonNum = 0;
/*  896 */         int curEpsNum = 0;
/*  897 */         float previousFloatingChildWidth = 0.0F;
/*  898 */         for (IRenderer childRenderer : this.childRenderers) {
/*      */           MinMaxWidth childMinMaxWidth;
/*  900 */           childRenderer.setParent(this);
/*  901 */           if (childRenderer instanceof AbstractRenderer) {
/*  902 */             childMinMaxWidth = ((AbstractRenderer)childRenderer).getMinMaxWidth();
/*      */           } else {
/*  904 */             childMinMaxWidth = MinMaxWidthUtils.countDefaultMinMaxWidth(childRenderer);
/*      */           } 
/*  906 */           handler.updateMaxChildWidth(childMinMaxWidth.getMaxWidth() + (FloatingHelper.isRendererFloating(childRenderer) ? previousFloatingChildWidth : 0.0F));
/*  907 */           handler.updateMinChildWidth(childMinMaxWidth.getMinWidth());
/*  908 */           previousFloatingChildWidth = FloatingHelper.isRendererFloating(childRenderer) ? (previousFloatingChildWidth + childMinMaxWidth.getMaxWidth()) : 0.0F;
/*  909 */           if (FloatingHelper.isRendererFloating(childRenderer)) {
/*  910 */             curEpsNum++; continue;
/*      */           } 
/*  912 */           epsilonNum = Math.max(epsilonNum, curEpsNum);
/*  913 */           curEpsNum = 0;
/*      */         } 
/*      */         
/*  916 */         epsilonNum = Math.max(epsilonNum, curEpsNum);
/*  917 */         handler.minMaxWidth.setChildrenMaxWidth(handler.minMaxWidth.getChildrenMaxWidth() + epsilonNum * 1.0E-4F);
/*  918 */         handler.minMaxWidth.setChildrenMinWidth(handler.minMaxWidth.getChildrenMinWidth() + epsilonNum * 1.0E-4F);
/*      */       } 
/*  920 */       if (minWidth != null) {
/*  921 */         minMaxWidth.setChildrenMinWidth(minWidth.floatValue());
/*      */       }
/*      */ 
/*      */       
/*  925 */       if (maxWidth != null) {
/*  926 */         minMaxWidth.setChildrenMaxWidth(maxWidth.floatValue());
/*      */       }
/*  928 */       else if (minMaxWidth.getChildrenMinWidth() > minMaxWidth.getChildrenMaxWidth()) {
/*  929 */         minMaxWidth.setChildrenMaxWidth(minMaxWidth.getChildrenMinWidth());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  934 */     if (getPropertyAsFloat(55) != null) {
/*  935 */       return RotationUtils.countRotationMinMaxWidth(minMaxWidth, this);
/*      */     }
/*      */     
/*  938 */     return minMaxWidth;
/*      */   }
/*      */ 
/*      */   
/*      */   private AbstractRenderer[] createSplitAndOverflowRenderers(int childPos, int layoutStatus, LayoutResult childResult, Map<Integer, IRenderer> waitingFloatsSplitRenderers, List<IRenderer> waitingOverflowFloatRenderers) {
/*  943 */     AbstractRenderer splitRenderer = createSplitRenderer(layoutStatus);
/*  944 */     splitRenderer.childRenderers = new ArrayList<>(this.childRenderers.subList(0, childPos));
/*  945 */     if (childResult.getStatus() == 2 && childResult.getSplitRenderer() != null) {
/*  946 */       splitRenderer.childRenderers.add(childResult.getSplitRenderer());
/*      */     }
/*      */ 
/*      */     
/*  950 */     replaceSplitRendererKidFloats(waitingFloatsSplitRenderers, splitRenderer);
/*  951 */     for (IRenderer renderer : splitRenderer.childRenderers) {
/*  952 */       renderer.setParent(splitRenderer);
/*      */     }
/*      */     
/*  955 */     AbstractRenderer overflowRenderer = createOverflowRenderer(layoutStatus);
/*  956 */     overflowRenderer.childRenderers.addAll(waitingOverflowFloatRenderers);
/*  957 */     if (childResult.getOverflowRenderer() != null) {
/*  958 */       overflowRenderer.childRenderers.add(childResult.getOverflowRenderer());
/*      */     }
/*  960 */     overflowRenderer.childRenderers.addAll(this.childRenderers.subList(childPos + 1, this.childRenderers.size()));
/*      */     
/*  962 */     if (childResult.getStatus() == 2)
/*      */     {
/*  964 */       overflowRenderer.deleteOwnProperty(26);
/*      */     }
/*      */     
/*  967 */     return new AbstractRenderer[] { splitRenderer, overflowRenderer };
/*      */   }
/*      */   
/*      */   private void replaceSplitRendererKidFloats(Map<Integer, IRenderer> waitingFloatsSplitRenderers, IRenderer splitRenderer) {
/*  971 */     for (Map.Entry<Integer, IRenderer> waitingSplitRenderer : waitingFloatsSplitRenderers.entrySet()) {
/*  972 */       if (waitingSplitRenderer.getValue() != null) {
/*  973 */         splitRenderer.getChildRenderers().set(((Integer)waitingSplitRenderer.getKey()).intValue(), waitingSplitRenderer.getValue()); continue;
/*      */       } 
/*  975 */       splitRenderer.getChildRenderers().set(((Integer)waitingSplitRenderer.getKey()).intValue(), null);
/*      */     } 
/*      */     
/*  978 */     for (int i = splitRenderer.getChildRenderers().size() - 1; i >= 0; i--) {
/*  979 */       if (splitRenderer.getChildRenderers().get(i) == null) {
/*  980 */         splitRenderer.getChildRenderers().remove(i);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private List<Point> clipPolygon(List<Point> points, Point clipLineBeg, Point clipLineEnd) {
/*  986 */     List<Point> filteredPoints = new ArrayList<>();
/*      */     
/*  988 */     boolean prevOnRightSide = false;
/*  989 */     Point filteringPoint = points.get(0);
/*  990 */     if (checkPointSide(filteringPoint, clipLineBeg, clipLineEnd) >= 0) {
/*  991 */       filteredPoints.add(filteringPoint);
/*  992 */       prevOnRightSide = true;
/*      */     } 
/*      */     
/*  995 */     Point prevPoint = filteringPoint;
/*  996 */     for (int i = 1; i < points.size() + 1; i++) {
/*  997 */       filteringPoint = points.get(i % points.size());
/*  998 */       if (checkPointSide(filteringPoint, clipLineBeg, clipLineEnd) >= 0) {
/*  999 */         if (!prevOnRightSide) {
/* 1000 */           filteredPoints.add(getIntersectionPoint(prevPoint, filteringPoint, clipLineBeg, clipLineEnd));
/*      */         }
/* 1002 */         filteredPoints.add(filteringPoint);
/* 1003 */         prevOnRightSide = true;
/* 1004 */       } else if (prevOnRightSide) {
/* 1005 */         filteredPoints.add(getIntersectionPoint(prevPoint, filteringPoint, clipLineBeg, clipLineEnd));
/*      */       } 
/*      */       
/* 1008 */       prevPoint = filteringPoint;
/*      */     } 
/*      */     
/* 1011 */     return filteredPoints;
/*      */   }
/*      */ 
/*      */   
/*      */   private int checkPointSide(Point filteredPoint, Point clipLineBeg, Point clipLineEnd) {
/* 1016 */     double x1 = filteredPoint.getX() - clipLineBeg.getX();
/* 1017 */     double y2 = clipLineEnd.getY() - clipLineBeg.getY();
/*      */     
/* 1019 */     double x2 = clipLineEnd.getX() - clipLineBeg.getX();
/* 1020 */     double y1 = filteredPoint.getY() - clipLineBeg.getY();
/*      */     
/* 1022 */     double sgn = x1 * y2 - x2 * y1;
/*      */     
/* 1024 */     if (Math.abs(sgn) < 0.001D) return 0; 
/* 1025 */     if (sgn > 0.0D) return 1; 
/* 1026 */     if (sgn < 0.0D) return -1;
/*      */     
/* 1028 */     return 0;
/*      */   }
/*      */   
/*      */   private Point getIntersectionPoint(Point lineBeg, Point lineEnd, Point clipLineBeg, Point clipLineEnd) {
/* 1032 */     double A1 = lineBeg.getY() - lineEnd.getY(), A2 = clipLineBeg.getY() - clipLineEnd.getY();
/* 1033 */     double B1 = lineEnd.getX() - lineBeg.getX(), B2 = clipLineEnd.getX() - clipLineBeg.getX();
/* 1034 */     double C1 = lineBeg.getX() * lineEnd.getY() - lineBeg.getY() * lineEnd.getX();
/* 1035 */     double C2 = clipLineBeg.getX() * clipLineEnd.getY() - clipLineBeg.getY() * clipLineEnd.getX();
/*      */     
/* 1037 */     double M = B1 * A2 - B2 * A1;
/*      */     
/* 1039 */     return new Point((B2 * C1 - B1 * C2) / M, (C2 * A1 - C1 * A2) / M);
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/BlockRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */