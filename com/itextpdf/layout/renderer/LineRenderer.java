/*      */ package com.itextpdf.layout.renderer;
/*      */ 
/*      */ import com.itextpdf.io.font.otf.ActualTextIterator;
/*      */ import com.itextpdf.io.font.otf.Glyph;
/*      */ import com.itextpdf.io.font.otf.GlyphLine;
/*      */ import com.itextpdf.io.util.ArrayUtil;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.io.util.TextUtil;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.layout.element.TabStop;
/*      */ import com.itextpdf.layout.layout.LayoutArea;
/*      */ import com.itextpdf.layout.layout.LayoutContext;
/*      */ import com.itextpdf.layout.layout.LayoutResult;
/*      */ import com.itextpdf.layout.layout.LineLayoutContext;
/*      */ import com.itextpdf.layout.layout.LineLayoutResult;
/*      */ import com.itextpdf.layout.layout.MinMaxWidthLayoutResult;
/*      */ import com.itextpdf.layout.layout.TextLayoutResult;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*      */ import com.itextpdf.layout.property.BaseDirection;
/*      */ import com.itextpdf.layout.property.FloatPropertyValue;
/*      */ import com.itextpdf.layout.property.Leading;
/*      */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*      */ import com.itextpdf.layout.property.RenderingMode;
/*      */ import com.itextpdf.layout.property.TabAlignment;
/*      */ import com.itextpdf.layout.property.UnitValue;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NavigableMap;
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
/*      */ 
/*      */ public class LineRenderer
/*      */   extends AbstractRenderer
/*      */ {
/*      */   private static final float MIN_MAX_WIDTH_CORRECTION_EPS = 0.001F;
/*   90 */   private static final Logger logger = LoggerFactory.getLogger(LineRenderer.class);
/*      */   
/*      */   protected float maxAscent;
/*      */   
/*      */   protected float maxDescent;
/*      */   protected byte[] levels;
/*      */   private float maxTextAscent;
/*      */   private float maxTextDescent;
/*      */   private float maxBlockAscent;
/*      */   private float maxBlockDescent;
/*      */   
/*      */   public LayoutResult layout(LayoutContext layoutContext) {
/*      */     AbstractWidthHandler widthHandler;
/*  103 */     Rectangle layoutBox = layoutContext.getArea().getBBox().clone();
/*  104 */     boolean wasParentsHeightClipped = layoutContext.isClippedHeight();
/*  105 */     List<Rectangle> floatRendererAreas = layoutContext.getFloatRendererAreas();
/*      */     
/*  107 */     OverflowPropertyValue oldXOverflow = null;
/*  108 */     boolean wasXOverflowChanged = false;
/*      */     
/*  110 */     if (floatRendererAreas != null) {
/*  111 */       float layoutWidth = layoutBox.getWidth();
/*  112 */       FloatingHelper.adjustLineAreaAccordingToFloats(floatRendererAreas, layoutBox);
/*  113 */       if (layoutWidth > layoutBox.getWidth()) {
/*  114 */         oldXOverflow = getProperty(103);
/*  115 */         wasXOverflowChanged = true;
/*  116 */         setProperty(103, OverflowPropertyValue.FIT);
/*      */       } 
/*      */     } 
/*      */     
/*  120 */     boolean noSoftWrap = Boolean.TRUE.equals(getOwnProperty(118));
/*      */     
/*  122 */     LineLayoutContext lineLayoutContext = (layoutContext instanceof LineLayoutContext) ? (LineLayoutContext)layoutContext : new LineLayoutContext(layoutContext);
/*  123 */     if (lineLayoutContext.getTextIndent() != 0.0F) {
/*  124 */       layoutBox
/*  125 */         .moveRight(lineLayoutContext.getTextIndent())
/*  126 */         .setWidth(layoutBox.getWidth() - lineLayoutContext.getTextIndent());
/*      */     }
/*      */     
/*  129 */     this.occupiedArea = new LayoutArea(layoutContext.getArea().getPageNumber(), layoutBox.clone().moveUp(layoutBox.getHeight()).setHeight(0.0F).setWidth(0.0F));
/*      */     
/*  131 */     updateChildrenParent();
/*      */     
/*  133 */     float curWidth = 0.0F;
/*  134 */     if (RenderingMode.HTML_MODE.equals(getProperty(123)) && 
/*  135 */       hasChildRendererInHtmlMode()) {
/*  136 */       float[] ascenderDescender = LineHeightHelper.getActualAscenderDescender(this);
/*  137 */       this.maxAscent = ascenderDescender[0];
/*  138 */       this.maxDescent = ascenderDescender[1];
/*      */     } else {
/*  140 */       this.maxAscent = 0.0F;
/*  141 */       this.maxDescent = 0.0F;
/*      */     } 
/*  143 */     this.maxTextAscent = 0.0F;
/*  144 */     this.maxTextDescent = 0.0F;
/*  145 */     this.maxBlockAscent = -1.0E20F;
/*  146 */     this.maxBlockDescent = 1.0E20F;
/*      */     
/*  148 */     int childPos = 0;
/*      */     
/*  150 */     MinMaxWidth minMaxWidth = new MinMaxWidth();
/*      */     
/*  152 */     if (noSoftWrap) {
/*  153 */       widthHandler = new SumSumWidthHandler(minMaxWidth);
/*      */     } else {
/*  155 */       widthHandler = new MaxSumWidthHandler(minMaxWidth);
/*      */     } 
/*      */     
/*  158 */     resolveChildrenFonts();
/*      */     
/*  160 */     int totalNumberOfTrimmedGlyphs = trimFirst();
/*      */     
/*  162 */     BaseDirection baseDirection = applyOtf();
/*      */     
/*  164 */     updateBidiLevels(totalNumberOfTrimmedGlyphs, baseDirection);
/*      */     
/*  166 */     boolean anythingPlaced = false;
/*  167 */     TabStop hangingTabStop = null;
/*  168 */     LineLayoutResult result = null;
/*      */     
/*  170 */     boolean floatsPlaced = false;
/*  171 */     Map<Integer, IRenderer> floatsToNextPageSplitRenderers = new LinkedHashMap<>();
/*  172 */     List<IRenderer> floatsToNextPageOverflowRenderers = new ArrayList<>();
/*  173 */     List<IRenderer> floatsOverflowedToNextLine = new ArrayList<>();
/*  174 */     int lastTabIndex = 0;
/*      */     
/*  176 */     Map<Integer, LayoutResult> specialScriptLayoutResults = new HashMap<>();
/*      */     
/*  178 */     while (childPos < this.childRenderers.size()) {
/*  179 */       LineLayoutResult lineLayoutResult; LayoutResult layoutResult1; IRenderer childRenderer = this.childRenderers.get(childPos);
/*  180 */       LayoutResult childResult = null;
/*  181 */       Rectangle bbox = new Rectangle(layoutBox.getX() + curWidth, layoutBox.getY(), layoutBox.getWidth() - curWidth, layoutBox.getHeight());
/*      */       
/*  183 */       if (childRenderer instanceof TextRenderer) {
/*      */         
/*  185 */         childRenderer.deleteOwnProperty(15);
/*  186 */         childRenderer.deleteOwnProperty(78);
/*  187 */       } else if (childRenderer instanceof TabRenderer) {
/*  188 */         if (hangingTabStop != null) {
/*  189 */           IRenderer tabRenderer = this.childRenderers.get(childPos - 1);
/*  190 */           tabRenderer.layout(new LayoutContext(new LayoutArea(layoutContext.getArea().getPageNumber(), bbox), wasParentsHeightClipped));
/*  191 */           curWidth += tabRenderer.getOccupiedArea().getBBox().getWidth();
/*  192 */           widthHandler.updateMaxChildWidth(tabRenderer.getOccupiedArea().getBBox().getWidth());
/*      */         } 
/*  194 */         hangingTabStop = calculateTab(childRenderer, curWidth, layoutBox.getWidth());
/*  195 */         if (childPos == this.childRenderers.size() - 1)
/*  196 */           hangingTabStop = null; 
/*  197 */         if (hangingTabStop != null) {
/*  198 */           lastTabIndex = childPos;
/*  199 */           childPos++;
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*  204 */       if (hangingTabStop != null && hangingTabStop.getTabAlignment() == TabAlignment.ANCHOR && childRenderer instanceof TextRenderer)
/*      */       {
/*  206 */         childRenderer.setProperty(66, hangingTabStop.getTabAnchor());
/*      */       }
/*      */ 
/*      */       
/*  210 */       Object childWidth = childRenderer.getProperty(77);
/*  211 */       boolean childWidthWasReplaced = false;
/*  212 */       boolean childRendererHasOwnWidthProperty = childRenderer.hasOwnProperty(77);
/*  213 */       if (childWidth instanceof UnitValue && ((UnitValue)childWidth).isPercentValue()) {
/*  214 */         float normalizedChildWidth = ((UnitValue)childWidth).getValue() / 100.0F * layoutContext.getArea().getBBox().getWidth();
/*  215 */         normalizedChildWidth = decreaseRelativeWidthByChildAdditionalWidth(childRenderer, normalizedChildWidth);
/*      */         
/*  217 */         if (normalizedChildWidth > 0.0F) {
/*  218 */           childRenderer.setProperty(77, UnitValue.createPointValue(normalizedChildWidth));
/*  219 */           childWidthWasReplaced = true;
/*      */         } 
/*      */       } 
/*      */       
/*  223 */       FloatPropertyValue kidFloatPropertyVal = (FloatPropertyValue)childRenderer.getProperty(99);
/*  224 */       boolean isChildFloating = (childRenderer instanceof AbstractRenderer && FloatingHelper.isRendererFloating(childRenderer, kidFloatPropertyVal));
/*  225 */       if (isChildFloating) {
/*  226 */         childResult = null;
/*  227 */         MinMaxWidth kidMinMaxWidth = FloatingHelper.calculateMinMaxWidthForFloat((AbstractRenderer)childRenderer, kidFloatPropertyVal);
/*  228 */         float floatingBoxFullWidth = kidMinMaxWidth.getMaxWidth();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  234 */         if (!wasXOverflowChanged && childPos > 0) {
/*  235 */           oldXOverflow = getProperty(103);
/*  236 */           wasXOverflowChanged = true;
/*  237 */           setProperty(103, OverflowPropertyValue.FIT);
/*      */         } 
/*  239 */         if (!lineLayoutContext.isFloatOverflowedToNextPageWithNothing() && floatsOverflowedToNextLine.isEmpty() && (!anythingPlaced || floatingBoxFullWidth <= bbox
/*  240 */           .getWidth())) {
/*  241 */           childResult = childRenderer.layout(new LayoutContext(new LayoutArea(layoutContext.getArea().getPageNumber(), layoutContext.getArea().getBBox().clone()), null, floatRendererAreas, wasParentsHeightClipped));
/*      */         }
/*      */         
/*  244 */         if (childWidthWasReplaced) {
/*  245 */           if (childRendererHasOwnWidthProperty) {
/*  246 */             childRenderer.setProperty(77, childWidth);
/*      */           } else {
/*  248 */             childRenderer.deleteOwnProperty(77);
/*      */           } 
/*      */         }
/*      */         
/*  252 */         float f1 = 0.0F;
/*  253 */         float f2 = 0.0F;
/*  254 */         if (childResult instanceof MinMaxWidthLayoutResult) {
/*  255 */           if (!childWidthWasReplaced) {
/*  256 */             f1 = ((MinMaxWidthLayoutResult)childResult).getMinMaxWidth().getMinWidth();
/*      */           }
/*      */           
/*  259 */           f2 = ((MinMaxWidthLayoutResult)childResult).getMinMaxWidth().getMaxWidth();
/*  260 */           widthHandler.updateMinChildWidth(f1 + 1.0E-4F);
/*  261 */           widthHandler.updateMaxChildWidth(f2 + 1.0E-4F);
/*      */         } else {
/*  263 */           widthHandler.updateMinChildWidth(kidMinMaxWidth.getMinWidth() + 1.0E-4F);
/*  264 */           widthHandler.updateMaxChildWidth(kidMinMaxWidth.getMaxWidth() + 1.0E-4F);
/*      */         } 
/*      */         
/*  267 */         if (childResult == null && !lineLayoutContext.isFloatOverflowedToNextPageWithNothing()) {
/*  268 */           floatsOverflowedToNextLine.add(childRenderer);
/*  269 */         } else if (lineLayoutContext.isFloatOverflowedToNextPageWithNothing() || childResult.getStatus() == 3) {
/*  270 */           floatsToNextPageSplitRenderers.put(Integer.valueOf(childPos), null);
/*  271 */           floatsToNextPageOverflowRenderers.add(childRenderer);
/*  272 */           lineLayoutContext.setFloatOverflowedToNextPageWithNothing(true);
/*  273 */         } else if (childResult.getStatus() == 2) {
/*  274 */           floatsPlaced = true;
/*      */           
/*  276 */           if (childRenderer instanceof TextRenderer) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  283 */             LineRenderer[] split = splitNotFittingFloat(childPos, childResult);
/*  284 */             IRenderer splitRenderer = childResult.getSplitRenderer();
/*  285 */             if (splitRenderer instanceof TextRenderer) {
/*  286 */               ((TextRenderer)splitRenderer).trimFirst();
/*  287 */               ((TextRenderer)splitRenderer).trimLast();
/*      */             } 
/*      */             
/*  290 */             splitRenderer.getOccupiedArea().getBBox().setWidth(layoutContext.getArea().getBBox().getWidth());
/*  291 */             result = new LineLayoutResult(2, this.occupiedArea, split[0], split[1], null);
/*      */             break;
/*      */           } 
/*  294 */           floatsToNextPageSplitRenderers.put(Integer.valueOf(childPos), childResult.getSplitRenderer());
/*  295 */           floatsToNextPageOverflowRenderers.add(childResult.getOverflowRenderer());
/*  296 */           adjustLineOnFloatPlaced(layoutBox, childPos, kidFloatPropertyVal, childResult.getSplitRenderer().getOccupiedArea().getBBox());
/*      */         } else {
/*      */           
/*  299 */           floatsPlaced = true;
/*      */           
/*  301 */           if (childRenderer instanceof TextRenderer) {
/*  302 */             ((TextRenderer)childRenderer).trimFirst();
/*  303 */             ((TextRenderer)childRenderer).trimLast();
/*      */           } 
/*      */           
/*  306 */           adjustLineOnFloatPlaced(layoutBox, childPos, kidFloatPropertyVal, childRenderer.getOccupiedArea().getBBox());
/*      */         } 
/*      */         
/*  309 */         childPos++;
/*  310 */         if (!anythingPlaced && childResult != null && childResult.getStatus() == 3 && floatRendererAreas.isEmpty() && 
/*  311 */           isFirstOnRootArea()) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/*  321 */       MinMaxWidth childBlockMinMaxWidth = null;
/*  322 */       boolean isInlineBlockChild = isInlineBlockChild(childRenderer);
/*  323 */       if (!childWidthWasReplaced && 
/*  324 */         isInlineBlockChild && childRenderer instanceof AbstractRenderer) {
/*  325 */         childBlockMinMaxWidth = ((AbstractRenderer)childRenderer).getMinMaxWidth();
/*  326 */         float childMaxWidth = childBlockMinMaxWidth.getMaxWidth();
/*  327 */         float lineFullAvailableWidth = layoutContext.getArea().getBBox().getWidth() - lineLayoutContext.getTextIndent();
/*  328 */         if (!noSoftWrap && childMaxWidth > bbox.getWidth() + 0.001F && bbox.getWidth() != lineFullAvailableWidth) {
/*  329 */           lineLayoutResult = new LineLayoutResult(3, null, null, childRenderer, childRenderer);
/*      */         } else {
/*  331 */           childMaxWidth += 0.001F;
/*  332 */           float inlineBlockWidth = Math.min(childMaxWidth, lineFullAvailableWidth);
/*      */           
/*  334 */           if (!isOverflowFit(getProperty(103))) {
/*  335 */             float childMinWidth = childBlockMinMaxWidth.getMinWidth() + 0.001F;
/*  336 */             inlineBlockWidth = Math.max(childMinWidth, inlineBlockWidth);
/*      */           } 
/*  338 */           bbox.setWidth(inlineBlockWidth);
/*      */           
/*  340 */           if (childBlockMinMaxWidth.getMinWidth() > bbox.getWidth()) {
/*  341 */             if (logger.isWarnEnabled()) {
/*  342 */               logger.warn("Inline block element does not fit into parent element and will be clipped");
/*      */             }
/*  344 */             childRenderer.setProperty(26, Boolean.valueOf(true));
/*      */           } 
/*      */         } 
/*  347 */         childBlockMinMaxWidth.setChildrenMaxWidth(childBlockMinMaxWidth.getChildrenMaxWidth() + 0.001F);
/*  348 */         childBlockMinMaxWidth.setChildrenMinWidth(childBlockMinMaxWidth.getChildrenMinWidth() + 0.001F);
/*      */       } 
/*      */ 
/*      */       
/*  352 */       if (lineLayoutResult == null) {
/*  353 */         if (!wasXOverflowChanged && childPos > 0) {
/*  354 */           oldXOverflow = getProperty(103);
/*  355 */           wasXOverflowChanged = true;
/*  356 */           setProperty(103, OverflowPropertyValue.FIT);
/*      */         } 
/*      */         
/*  359 */         if (TypographyUtils.isPdfCalligraphAvailable() && 
/*  360 */           isTextRendererAndRequiresSpecialScriptPreLayoutProcessing(childRenderer)) {
/*  361 */           specialScriptPreLayoutProcessing(childPos);
/*      */         }
/*      */         
/*  364 */         layoutResult1 = childRenderer.layout(new LayoutContext(new LayoutArea(layoutContext.getArea().getPageNumber(), bbox), wasParentsHeightClipped));
/*      */         
/*  366 */         updateSpecialScriptLayoutResults(specialScriptLayoutResults, childRenderer, childPos, layoutResult1);
/*      */ 
/*      */         
/*  369 */         if (layoutResult1 instanceof MinMaxWidthLayoutResult && null != childBlockMinMaxWidth) {
/*  370 */           MinMaxWidth childResultMinMaxWidth = ((MinMaxWidthLayoutResult)layoutResult1).getMinMaxWidth();
/*  371 */           childResultMinMaxWidth.setChildrenMaxWidth(childResultMinMaxWidth.getChildrenMaxWidth() + 0.001F);
/*  372 */           childResultMinMaxWidth.setChildrenMinWidth(childResultMinMaxWidth.getChildrenMinWidth() + 0.001F);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  377 */       if (childWidthWasReplaced) {
/*  378 */         if (childRendererHasOwnWidthProperty) {
/*  379 */           childRenderer.setProperty(77, childWidth);
/*      */         } else {
/*  381 */           childRenderer.deleteOwnProperty(77);
/*      */         } 
/*      */       }
/*      */       
/*  385 */       float minChildWidth = 0.0F;
/*  386 */       float maxChildWidth = 0.0F;
/*  387 */       if (layoutResult1 instanceof MinMaxWidthLayoutResult) {
/*  388 */         if (!childWidthWasReplaced) {
/*  389 */           minChildWidth = ((MinMaxWidthLayoutResult)layoutResult1).getMinMaxWidth().getMinWidth();
/*      */         }
/*  391 */         maxChildWidth = ((MinMaxWidthLayoutResult)layoutResult1).getMinMaxWidth().getMaxWidth();
/*  392 */       } else if (childBlockMinMaxWidth != null) {
/*  393 */         minChildWidth = childBlockMinMaxWidth.getMinWidth();
/*  394 */         maxChildWidth = childBlockMinMaxWidth.getMaxWidth();
/*      */       } 
/*      */       
/*  397 */       float childAscent = 0.0F;
/*  398 */       float childDescent = 0.0F;
/*  399 */       if (childRenderer instanceof ILeafElementRenderer && layoutResult1
/*  400 */         .getStatus() != 3) {
/*  401 */         if (RenderingMode.HTML_MODE.equals(childRenderer.getProperty(123)) && childRenderer instanceof TextRenderer) {
/*      */           
/*  403 */           float[] ascenderDescender = LineHeightHelper.getActualAscenderDescender((TextRenderer)childRenderer);
/*  404 */           childAscent = ascenderDescender[0];
/*  405 */           childDescent = ascenderDescender[1];
/*      */         } else {
/*  407 */           childAscent = ((ILeafElementRenderer)childRenderer).getAscent();
/*  408 */           childDescent = ((ILeafElementRenderer)childRenderer).getDescent();
/*      */         } 
/*  410 */       } else if (isInlineBlockChild && layoutResult1.getStatus() != 3) {
/*  411 */         if (childRenderer instanceof AbstractRenderer) {
/*  412 */           Float yLine = ((AbstractRenderer)childRenderer).getLastYLineRecursively();
/*  413 */           if (yLine == null) {
/*  414 */             childAscent = childRenderer.getOccupiedArea().getBBox().getHeight();
/*      */           } else {
/*  416 */             childAscent = childRenderer.getOccupiedArea().getBBox().getTop() - yLine.floatValue();
/*  417 */             childDescent = -(yLine.floatValue() - childRenderer.getOccupiedArea().getBBox().getBottom());
/*      */           } 
/*      */         } else {
/*  420 */           childAscent = childRenderer.getOccupiedArea().getBBox().getHeight();
/*      */         } 
/*      */       } 
/*      */       
/*  424 */       boolean newLineOccurred = (layoutResult1 instanceof TextLayoutResult && ((TextLayoutResult)layoutResult1).isSplitForcedByNewline());
/*  425 */       boolean shouldBreakLayouting = (layoutResult1.getStatus() != 1 || newLineOccurred);
/*      */       
/*  427 */       boolean wordWasSplitAndItWillFitOntoNextLine = false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  432 */       if (shouldBreakLayouting && layoutResult1 instanceof TextLayoutResult && ((TextLayoutResult)layoutResult1)
/*  433 */         .isWordHasBeenSplit() && 
/*  434 */         !((TextRenderer)childRenderer).textContainsSpecialScriptGlyphs(true)) {
/*  435 */         if (wasXOverflowChanged) {
/*  436 */           setProperty(103, oldXOverflow);
/*      */         }
/*  438 */         LayoutResult newLayoutResult = childRenderer.layout(new LayoutContext(new LayoutArea(layoutContext.getArea().getPageNumber(), layoutBox), wasParentsHeightClipped));
/*  439 */         if (wasXOverflowChanged) {
/*  440 */           setProperty(103, OverflowPropertyValue.FIT);
/*      */         }
/*  442 */         if (newLayoutResult instanceof TextLayoutResult && !((TextLayoutResult)newLayoutResult).isWordHasBeenSplit()) {
/*  443 */           wordWasSplitAndItWillFitOntoNextLine = true;
/*      */         }
/*  445 */       } else if (shouldBreakLayouting && !newLineOccurred && this.childRenderers.get(childPos) instanceof TextRenderer && ((TextRenderer)this.childRenderers
/*  446 */         .get(childPos)).textContainsSpecialScriptGlyphs(true)) {
/*      */ 
/*      */         
/*  449 */         LastFittingChildRendererData lastFittingChildRendererData = getIndexAndLayoutResultOfTheLastRendererToRemainOnTheLine(childPos, specialScriptLayoutResults, wasParentsHeightClipped, floatsOverflowedToNextLine);
/*      */         
/*  451 */         curWidth -= getCurWidthSpecialScriptsDecrement(childPos, lastFittingChildRendererData.childIndex, specialScriptLayoutResults);
/*      */ 
/*      */         
/*  454 */         childPos = lastFittingChildRendererData.childIndex;
/*  455 */         layoutResult1 = lastFittingChildRendererData.childLayoutResult;
/*      */       } 
/*      */       
/*  458 */       if (!wordWasSplitAndItWillFitOntoNextLine) {
/*  459 */         this.maxAscent = Math.max(this.maxAscent, childAscent);
/*  460 */         if (childRenderer instanceof TextRenderer) {
/*  461 */           this.maxTextAscent = Math.max(this.maxTextAscent, childAscent);
/*  462 */         } else if (!isChildFloating) {
/*  463 */           this.maxBlockAscent = Math.max(this.maxBlockAscent, childAscent);
/*      */         } 
/*  465 */         this.maxDescent = Math.min(this.maxDescent, childDescent);
/*  466 */         if (childRenderer instanceof TextRenderer) {
/*  467 */           this.maxTextDescent = Math.min(this.maxTextDescent, childDescent);
/*  468 */         } else if (!isChildFloating) {
/*  469 */           this.maxBlockDescent = Math.min(this.maxBlockDescent, childDescent);
/*      */         } 
/*      */       } 
/*  472 */       float maxHeight = this.maxAscent - this.maxDescent;
/*      */       
/*  474 */       float currChildTextIndent = anythingPlaced ? 0.0F : lineLayoutContext.getTextIndent();
/*  475 */       if (hangingTabStop != null && (TabAlignment.LEFT == hangingTabStop
/*  476 */         .getTabAlignment() || shouldBreakLayouting || this.childRenderers.size() - 1 == childPos || this.childRenderers.get(childPos + 1) instanceof TabRenderer)) {
/*  477 */         IRenderer tabRenderer = this.childRenderers.get(lastTabIndex);
/*  478 */         List<IRenderer> affectedRenderers = new ArrayList<>();
/*  479 */         affectedRenderers.addAll(this.childRenderers.subList(lastTabIndex + 1, childPos + 1));
/*  480 */         float tabWidth = calculateTab(layoutBox, curWidth, hangingTabStop, affectedRenderers, tabRenderer);
/*      */         
/*  482 */         tabRenderer.layout(new LayoutContext(new LayoutArea(layoutContext.getArea().getPageNumber(), bbox), wasParentsHeightClipped));
/*  483 */         float sumOfAffectedRendererWidths = 0.0F;
/*  484 */         for (IRenderer renderer : affectedRenderers) {
/*  485 */           renderer.move(tabWidth + sumOfAffectedRendererWidths, 0.0F);
/*  486 */           sumOfAffectedRendererWidths += renderer.getOccupiedArea().getBBox().getWidth();
/*      */         } 
/*  488 */         if (layoutResult1.getSplitRenderer() != null) {
/*  489 */           layoutResult1.getSplitRenderer().move(tabWidth + sumOfAffectedRendererWidths - layoutResult1.getSplitRenderer().getOccupiedArea().getBBox().getWidth(), 0.0F);
/*      */         }
/*  491 */         float tabAndNextElemWidth = tabWidth + layoutResult1.getOccupiedArea().getBBox().getWidth();
/*  492 */         if (hangingTabStop.getTabAlignment() == TabAlignment.RIGHT && curWidth + tabAndNextElemWidth < hangingTabStop.getTabPosition()) {
/*  493 */           curWidth = hangingTabStop.getTabPosition();
/*      */         } else {
/*  495 */           curWidth += tabAndNextElemWidth;
/*      */         } 
/*  497 */         widthHandler.updateMinChildWidth(minChildWidth + currChildTextIndent);
/*  498 */         widthHandler.updateMaxChildWidth(tabWidth + maxChildWidth + currChildTextIndent);
/*  499 */         hangingTabStop = null;
/*  500 */       } else if (null == hangingTabStop) {
/*  501 */         if (layoutResult1.getOccupiedArea() != null && layoutResult1.getOccupiedArea().getBBox() != null) {
/*  502 */           curWidth += layoutResult1.getOccupiedArea().getBBox().getWidth();
/*      */         }
/*  504 */         widthHandler.updateMinChildWidth(minChildWidth + currChildTextIndent);
/*  505 */         widthHandler.updateMaxChildWidth(maxChildWidth + currChildTextIndent);
/*      */       } 
/*  507 */       if (!wordWasSplitAndItWillFitOntoNextLine) {
/*  508 */         this.occupiedArea.setBBox(new Rectangle(layoutBox.getX(), layoutBox.getY() + layoutBox.getHeight() - maxHeight, curWidth, maxHeight));
/*      */       }
/*      */       
/*  511 */       if (shouldBreakLayouting) {
/*  512 */         LineRenderer[] split = split();
/*  513 */         (split[0]).childRenderers = new ArrayList<>(this.childRenderers.subList(0, childPos));
/*      */         
/*  515 */         if (wordWasSplitAndItWillFitOntoNextLine) {
/*  516 */           (split[1]).childRenderers.add(childRenderer);
/*  517 */           (split[1]).childRenderers.addAll(this.childRenderers.subList(childPos + 1, this.childRenderers.size()));
/*      */         } else {
/*  519 */           boolean forcePlacement = Boolean.TRUE.equals(getPropertyAsBoolean(26));
/*  520 */           boolean isInlineBlockAndFirstOnRootArea = (isInlineBlockChild && isFirstOnRootArea());
/*  521 */           if ((layoutResult1.getStatus() == 2 && (!isInlineBlockChild || forcePlacement || isInlineBlockAndFirstOnRootArea)) || layoutResult1.getStatus() == 1) {
/*  522 */             split[0].addChild(layoutResult1.getSplitRenderer());
/*  523 */             anythingPlaced = true;
/*      */           } 
/*      */           
/*  526 */           if (null != layoutResult1.getOverflowRenderer()) {
/*  527 */             if (isInlineBlockChild && !forcePlacement && !isInlineBlockAndFirstOnRootArea) {
/*  528 */               (split[1]).childRenderers.add(childRenderer);
/*      */             }
/*  530 */             else if (isInlineBlockChild && layoutResult1.getOverflowRenderer().getChildRenderers().size() == 0 && layoutResult1
/*  531 */               .getStatus() == 2) {
/*  532 */               if (logger.isWarnEnabled()) {
/*  533 */                 logger.warn("Inline block element does not fit into parent element and will be clipped");
/*      */               }
/*      */             } else {
/*  536 */               (split[1]).childRenderers.add(layoutResult1.getOverflowRenderer());
/*      */             } 
/*      */           }
/*      */           
/*  540 */           (split[1]).childRenderers.addAll(this.childRenderers.subList(childPos + 1, this.childRenderers.size()));
/*      */         } 
/*      */         
/*  543 */         replaceSplitRendererKidFloats(floatsToNextPageSplitRenderers, split[0]);
/*  544 */         (split[0]).childRenderers.removeAll(floatsOverflowedToNextLine);
/*  545 */         (split[1]).childRenderers.addAll(0, floatsOverflowedToNextLine);
/*      */ 
/*      */         
/*  548 */         if ((split[1]).childRenderers.size() == 0 && floatsToNextPageOverflowRenderers.isEmpty()) {
/*  549 */           split[1] = null;
/*      */         }
/*      */         
/*  552 */         IRenderer causeOfNothing = (layoutResult1.getStatus() == 3) ? layoutResult1.getCauseOfNothing() : this.childRenderers.get(childPos);
/*  553 */         if (split[1] == null) {
/*  554 */           result = new LineLayoutResult(1, this.occupiedArea, split[0], split[1], causeOfNothing);
/*      */         } else {
/*  556 */           if (anythingPlaced || floatsPlaced) {
/*  557 */             result = new LineLayoutResult(2, this.occupiedArea, split[0], split[1], causeOfNothing);
/*      */           } else {
/*  559 */             result = new LineLayoutResult(3, null, split[0], split[1], null);
/*      */           } 
/*  561 */           result.setFloatsOverflowedToNextPage(floatsToNextPageOverflowRenderers);
/*      */         } 
/*  563 */         if (newLineOccurred) {
/*  564 */           result.setSplitForcedByNewline(true);
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*  569 */       anythingPlaced = true;
/*  570 */       childPos++;
/*      */     } 
/*      */ 
/*      */     
/*  574 */     if (result == null) {
/*  575 */       boolean noOverflowedFloats = (floatsOverflowedToNextLine.isEmpty() && floatsToNextPageOverflowRenderers.isEmpty());
/*  576 */       if (((anythingPlaced || floatsPlaced) && noOverflowedFloats) || 0 == this.childRenderers.size()) {
/*  577 */         result = new LineLayoutResult(1, this.occupiedArea, null, null);
/*      */       }
/*  579 */       else if (noOverflowedFloats) {
/*      */ 
/*      */         
/*  582 */         result = new LineLayoutResult(1, this.occupiedArea, null, null);
/*  583 */       } else if (anythingPlaced || floatsPlaced) {
/*  584 */         LineRenderer[] split = split();
/*  585 */         (split[0]).childRenderers.addAll(this.childRenderers.subList(0, childPos));
/*  586 */         replaceSplitRendererKidFloats(floatsToNextPageSplitRenderers, split[0]);
/*  587 */         (split[0]).childRenderers.removeAll(floatsOverflowedToNextLine);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  593 */         (split[1]).childRenderers.addAll(floatsOverflowedToNextLine);
/*  594 */         result = new LineLayoutResult(2, this.occupiedArea, split[0], split[1], null);
/*  595 */         result.setFloatsOverflowedToNextPage(floatsToNextPageOverflowRenderers);
/*      */       } else {
/*  597 */         IRenderer causeOfNothing = floatsOverflowedToNextLine.isEmpty() ? floatsToNextPageOverflowRenderers.get(0) : floatsOverflowedToNextLine.get(0);
/*  598 */         result = new LineLayoutResult(3, null, null, this, causeOfNothing);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  603 */     if (baseDirection != null && baseDirection != BaseDirection.NO_BIDI) {
/*  604 */       List<IRenderer> children = null;
/*  605 */       if (result.getStatus() == 2) {
/*  606 */         children = result.getSplitRenderer().getChildRenderers();
/*  607 */       } else if (result.getStatus() == 1) {
/*  608 */         children = getChildRenderers();
/*      */       } 
/*      */       
/*  611 */       if (children != null) {
/*  612 */         boolean newLineFound = false;
/*  613 */         List<RendererGlyph> lineGlyphs = new ArrayList<>();
/*      */ 
/*      */ 
/*      */         
/*  617 */         Map<TextRenderer, List<IRenderer>> insertAfter = new HashMap<>();
/*  618 */         List<IRenderer> starterNonTextRenderers = new ArrayList<>();
/*  619 */         TextRenderer lastTextRenderer = null;
/*      */         
/*  621 */         for (IRenderer child : children) {
/*  622 */           if (newLineFound) {
/*      */             break;
/*      */           }
/*  625 */           if (child instanceof TextRenderer) {
/*  626 */             GlyphLine childLine = ((TextRenderer)child).line;
/*  627 */             for (int i = childLine.start; i < childLine.end; i++) {
/*  628 */               if (TextUtil.isNewLine(childLine.get(i))) {
/*  629 */                 newLineFound = true;
/*      */                 break;
/*      */               } 
/*  632 */               lineGlyphs.add(new RendererGlyph(childLine.get(i), (TextRenderer)child));
/*      */             } 
/*  634 */             lastTextRenderer = (TextRenderer)child; continue;
/*  635 */           }  if (lastTextRenderer != null) {
/*  636 */             if (!insertAfter.containsKey(lastTextRenderer)) {
/*  637 */               insertAfter.put(lastTextRenderer, new ArrayList<>());
/*      */             }
/*  639 */             ((List<IRenderer>)insertAfter.get(lastTextRenderer)).add(child); continue;
/*      */           } 
/*  641 */           starterNonTextRenderers.add(child);
/*      */         } 
/*      */         
/*  644 */         byte[] lineLevels = new byte[lineGlyphs.size()];
/*  645 */         if (this.levels != null) {
/*  646 */           System.arraycopy(this.levels, 0, lineLevels, 0, lineGlyphs.size());
/*      */         }
/*      */         
/*  649 */         int[] reorder = TypographyUtils.reorderLine(lineGlyphs, lineLevels, this.levels);
/*      */         
/*  651 */         if (reorder != null) {
/*  652 */           children.clear();
/*  653 */           int pos = 0;
/*  654 */           int initialPos = 0;
/*  655 */           boolean reversed = false;
/*  656 */           int offset = 0;
/*      */ 
/*      */           
/*  659 */           for (IRenderer child : starterNonTextRenderers) {
/*  660 */             children.add(child);
/*      */           }
/*      */           
/*  663 */           while (pos < lineGlyphs.size()) {
/*  664 */             IRenderer renderer = ((RendererGlyph)lineGlyphs.get(pos)).renderer;
/*  665 */             TextRenderer newRenderer = (new TextRenderer((TextRenderer)renderer)).removeReversedRanges();
/*  666 */             children.add(newRenderer);
/*      */ 
/*      */             
/*  669 */             if (insertAfter.containsKey(renderer)) {
/*  670 */               children.addAll(insertAfter.get(renderer));
/*  671 */               insertAfter.remove(renderer);
/*      */             } 
/*      */             
/*  674 */             newRenderer.line = new GlyphLine(newRenderer.line);
/*  675 */             List<Glyph> replacementGlyphs = new ArrayList<>();
/*  676 */             while (pos < lineGlyphs.size() && ((RendererGlyph)lineGlyphs.get(pos)).renderer == renderer) {
/*  677 */               if (pos + 1 < lineGlyphs.size()) {
/*  678 */                 if (reorder[pos] == reorder[pos + 1] + 1 && 
/*  679 */                   !TextUtil.isSpaceOrWhitespace(((RendererGlyph)lineGlyphs.get(pos + 1)).glyph) && !TextUtil.isSpaceOrWhitespace(((RendererGlyph)lineGlyphs.get(pos)).glyph)) {
/*  680 */                   reversed = true;
/*      */                 } else {
/*  682 */                   if (reversed) {
/*  683 */                     List<int[]> reversedRange = newRenderer.initReversedRanges();
/*  684 */                     reversedRange.add(new int[] { initialPos - offset, pos - offset });
/*  685 */                     reversed = false;
/*      */                   } 
/*  687 */                   initialPos = pos + 1;
/*      */                 } 
/*      */               }
/*      */               
/*  691 */               replacementGlyphs.add(((RendererGlyph)lineGlyphs.get(pos)).glyph);
/*  692 */               pos++;
/*      */             } 
/*      */             
/*  695 */             if (reversed) {
/*  696 */               List<int[]> reversedRange = newRenderer.initReversedRanges();
/*  697 */               reversedRange.add(new int[] { initialPos - offset, pos - 1 - offset });
/*  698 */               reversed = false;
/*  699 */               initialPos = pos;
/*      */             } 
/*  701 */             offset = initialPos;
/*  702 */             newRenderer.line.setGlyphs(replacementGlyphs);
/*      */           } 
/*      */           
/*  705 */           adjustChildPositionsAfterReordering(children, this.occupiedArea.getBBox().getLeft());
/*      */         } 
/*      */         
/*  708 */         if (result.getStatus() == 2) {
/*  709 */           LineRenderer overflow = (LineRenderer)result.getOverflowRenderer();
/*  710 */           if (this.levels != null) {
/*  711 */             overflow.levels = new byte[this.levels.length - lineLevels.length];
/*  712 */             System.arraycopy(this.levels, lineLevels.length, overflow.levels, 0, overflow.levels.length);
/*  713 */             if (overflow.levels.length == 0) {
/*  714 */               overflow.levels = null;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  720 */     LineRenderer processed = (result.getStatus() == 1) ? this : (LineRenderer)result.getSplitRenderer();
/*  721 */     if (anythingPlaced || floatsPlaced) {
/*  722 */       processed.adjustChildrenYLine().trimLast();
/*  723 */       result.setMinMaxWidth(minMaxWidth);
/*      */     } 
/*      */     
/*  726 */     if (wasXOverflowChanged) {
/*  727 */       setProperty(103, oldXOverflow);
/*  728 */       if (null != result.getSplitRenderer()) {
/*  729 */         result.getSplitRenderer().setProperty(103, oldXOverflow);
/*      */       }
/*  731 */       if (null != result.getOverflowRenderer()) {
/*  732 */         result.getOverflowRenderer().setProperty(103, oldXOverflow);
/*      */       }
/*      */     } 
/*  735 */     return (LayoutResult)result;
/*      */   }
/*      */   
/*      */   public float getMaxAscent() {
/*  739 */     return this.maxAscent;
/*      */   }
/*      */   
/*      */   public float getMaxDescent() {
/*  743 */     return this.maxDescent;
/*      */   }
/*      */   
/*      */   public float getYLine() {
/*  747 */     return this.occupiedArea.getBBox().getY() - this.maxDescent;
/*      */   }
/*      */   
/*      */   public float getLeadingValue(Leading leading) {
/*  751 */     switch (leading.getType()) {
/*      */       case 1:
/*  753 */         return Math.max(leading.getValue(), this.maxBlockAscent - this.maxBlockDescent);
/*      */       case 2:
/*  755 */         return getTopLeadingIndent(leading) + getBottomLeadingIndent(leading);
/*      */     } 
/*  757 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IRenderer getNextRenderer() {
/*  763 */     return new LineRenderer();
/*      */   }
/*      */ 
/*      */   
/*      */   protected Float getFirstYLineRecursively() {
/*  768 */     return Float.valueOf(getYLine());
/*      */   }
/*      */ 
/*      */   
/*      */   protected Float getLastYLineRecursively() {
/*  773 */     return Float.valueOf(getYLine());
/*      */   }
/*      */   
/*      */   public void justify(float width) {
/*  777 */     float ratio = getPropertyAsFloat(61).floatValue();
/*  778 */     IRenderer lastChildRenderer = getLastNonFloatChildRenderer();
/*  779 */     if (lastChildRenderer == null) {
/*      */       return;
/*      */     }
/*      */     
/*  783 */     float freeWidth = this.occupiedArea.getBBox().getX() + width - lastChildRenderer.getOccupiedArea().getBBox().getX() - lastChildRenderer.getOccupiedArea().getBBox().getWidth();
/*  784 */     int numberOfSpaces = getNumberOfSpaces();
/*  785 */     int baseCharsCount = baseCharactersCount();
/*  786 */     float baseFactor = freeWidth / (ratio * numberOfSpaces + (1.0F - ratio) * (baseCharsCount - 1));
/*      */ 
/*      */     
/*  789 */     if (Float.isInfinite(baseFactor)) {
/*  790 */       baseFactor = 0.0F;
/*      */     }
/*  792 */     float wordSpacing = ratio * baseFactor;
/*  793 */     float characterSpacing = (1.0F - ratio) * baseFactor;
/*      */     
/*  795 */     float lastRightPos = this.occupiedArea.getBBox().getX();
/*  796 */     for (IRenderer child : this.childRenderers) {
/*  797 */       if (FloatingHelper.isRendererFloating(child)) {
/*      */         continue;
/*      */       }
/*  800 */       float childX = child.getOccupiedArea().getBBox().getX();
/*  801 */       child.move(lastRightPos - childX, 0.0F);
/*  802 */       childX = lastRightPos;
/*  803 */       if (child instanceof TextRenderer) {
/*  804 */         float childHSCale = ((TextRenderer)child).getPropertyAsFloat(29, Float.valueOf(1.0F)).floatValue();
/*  805 */         Float oldCharacterSpacing = ((TextRenderer)child).getPropertyAsFloat(15);
/*  806 */         Float oldWordSpacing = ((TextRenderer)child).getPropertyAsFloat(78);
/*  807 */         child.setProperty(15, Float.valueOf(((null == oldCharacterSpacing) ? 0.0F : oldCharacterSpacing.floatValue()) + characterSpacing / childHSCale));
/*  808 */         child.setProperty(78, Float.valueOf(((null == oldWordSpacing) ? 0.0F : oldWordSpacing.floatValue()) + wordSpacing / childHSCale));
/*  809 */         boolean isLastTextRenderer = (child == lastChildRenderer);
/*      */         
/*  811 */         float widthAddition = (isLastTextRenderer ? (((TextRenderer)child).lineLength() - 1) : ((TextRenderer)child).lineLength()) * characterSpacing + wordSpacing * ((TextRenderer)child).getNumberOfSpaces();
/*  812 */         child.getOccupiedArea().getBBox().setWidth(child.getOccupiedArea().getBBox().getWidth() + widthAddition);
/*      */       } 
/*  814 */       lastRightPos = childX + child.getOccupiedArea().getBBox().getWidth();
/*      */     } 
/*      */     
/*  817 */     getOccupiedArea().getBBox().setWidth(width);
/*      */   }
/*      */   
/*      */   protected int getNumberOfSpaces() {
/*  821 */     int spaces = 0;
/*  822 */     for (IRenderer child : this.childRenderers) {
/*  823 */       if (child instanceof TextRenderer && !FloatingHelper.isRendererFloating(child)) {
/*  824 */         spaces += ((TextRenderer)child).getNumberOfSpaces();
/*      */       }
/*      */     } 
/*  827 */     return spaces;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int length() {
/*  836 */     int length = 0;
/*  837 */     for (IRenderer child : this.childRenderers) {
/*  838 */       if (child instanceof TextRenderer && !FloatingHelper.isRendererFloating(child)) {
/*  839 */         length += ((TextRenderer)child).lineLength();
/*      */       }
/*      */     } 
/*  842 */     return length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int baseCharactersCount() {
/*  850 */     int count = 0;
/*  851 */     for (IRenderer child : this.childRenderers) {
/*  852 */       if (child instanceof TextRenderer && !FloatingHelper.isRendererFloating(child)) {
/*  853 */         count += ((TextRenderer)child).baseCharactersCount();
/*      */       }
/*      */     } 
/*  856 */     return count;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  861 */     StringBuilder sb = new StringBuilder();
/*  862 */     for (IRenderer renderer : this.childRenderers) {
/*  863 */       sb.append(renderer.toString());
/*      */     }
/*  865 */     return sb.toString();
/*      */   }
/*      */   
/*      */   protected LineRenderer createSplitRenderer() {
/*  869 */     return (LineRenderer)getNextRenderer();
/*      */   }
/*      */   
/*      */   protected LineRenderer createOverflowRenderer() {
/*  873 */     return (LineRenderer)getNextRenderer();
/*      */   }
/*      */   
/*      */   protected LineRenderer[] split() {
/*  877 */     LineRenderer splitRenderer = createSplitRenderer();
/*  878 */     splitRenderer.occupiedArea = this.occupiedArea.clone();
/*  879 */     splitRenderer.parent = this.parent;
/*  880 */     splitRenderer.maxAscent = this.maxAscent;
/*  881 */     splitRenderer.maxDescent = this.maxDescent;
/*  882 */     splitRenderer.maxTextAscent = this.maxTextAscent;
/*  883 */     splitRenderer.maxTextDescent = this.maxTextDescent;
/*  884 */     splitRenderer.maxBlockAscent = this.maxBlockAscent;
/*  885 */     splitRenderer.maxBlockDescent = this.maxBlockDescent;
/*  886 */     splitRenderer.levels = this.levels;
/*  887 */     splitRenderer.addAllProperties(getOwnProperties());
/*      */     
/*  889 */     LineRenderer overflowRenderer = createOverflowRenderer();
/*  890 */     overflowRenderer.parent = this.parent;
/*  891 */     overflowRenderer.addAllProperties(getOwnProperties());
/*      */     
/*  893 */     return new LineRenderer[] { splitRenderer, overflowRenderer };
/*      */   }
/*      */   
/*      */   protected LineRenderer adjustChildrenYLine() {
/*  897 */     float actualYLine = this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight() - this.maxAscent;
/*  898 */     for (IRenderer renderer : this.childRenderers) {
/*  899 */       if (FloatingHelper.isRendererFloating(renderer)) {
/*      */         continue;
/*      */       }
/*  902 */       if (renderer instanceof ILeafElementRenderer) {
/*  903 */         float descent = ((ILeafElementRenderer)renderer).getDescent();
/*  904 */         renderer.move(0.0F, actualYLine - renderer.getOccupiedArea().getBBox().getBottom() + descent); continue;
/*      */       } 
/*  906 */       Float yLine = (isInlineBlockChild(renderer) && renderer instanceof AbstractRenderer) ? ((AbstractRenderer)renderer).getLastYLineRecursively() : null;
/*  907 */       renderer.move(0.0F, actualYLine - ((yLine == null) ? renderer.getOccupiedArea().getBBox().getBottom() : yLine.floatValue()));
/*      */     } 
/*      */     
/*  910 */     return this;
/*      */   }
/*      */   
/*      */   protected void applyLeading(float deltaY) {
/*  914 */     this.occupiedArea.getBBox().moveUp(deltaY);
/*  915 */     this.occupiedArea.getBBox().decreaseHeight(deltaY);
/*  916 */     for (IRenderer child : this.childRenderers) {
/*  917 */       if (!FloatingHelper.isRendererFloating(child)) {
/*  918 */         child.move(0.0F, deltaY);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected LineRenderer trimLast() {
/*  924 */     int lastIndex = this.childRenderers.size();
/*  925 */     IRenderer lastRenderer = null;
/*  926 */     while (--lastIndex >= 0) {
/*  927 */       lastRenderer = this.childRenderers.get(lastIndex);
/*  928 */       if (!FloatingHelper.isRendererFloating(lastRenderer)) {
/*      */         break;
/*      */       }
/*      */     } 
/*  932 */     if (lastRenderer instanceof TextRenderer && lastIndex >= 0) {
/*  933 */       float trimmedSpace = ((TextRenderer)lastRenderer).trimLast();
/*  934 */       this.occupiedArea.getBBox().setWidth(this.occupiedArea.getBBox().getWidth() - trimmedSpace);
/*      */     } 
/*  936 */     return this;
/*      */   }
/*      */   
/*      */   public boolean containsImage() {
/*  940 */     for (IRenderer renderer : this.childRenderers) {
/*  941 */       if (renderer instanceof ImageRenderer) {
/*  942 */         return true;
/*      */       }
/*      */     } 
/*  945 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinMaxWidth getMinMaxWidth() {
/*  950 */     LineLayoutResult result = (LineLayoutResult)layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0F))));
/*  951 */     return result.getMinMaxWidth();
/*      */   }
/*      */   
/*      */   boolean hasChildRendererInHtmlMode() {
/*  955 */     for (IRenderer childRenderer : this.childRenderers) {
/*  956 */       if (RenderingMode.HTML_MODE.equals(childRenderer.getProperty(123))) {
/*  957 */         return true;
/*      */       }
/*      */     } 
/*  960 */     return false; } float getTopLeadingIndent(Leading leading) {
/*      */     UnitValue fontSize;
/*      */     float textAscent;
/*      */     float textDescent;
/*  964 */     switch (leading.getType()) {
/*      */       case 1:
/*  966 */         return (Math.max(leading.getValue(), this.maxBlockAscent - this.maxBlockDescent) - this.occupiedArea.getBBox().getHeight()) / 2.0F;
/*      */       case 2:
/*  968 */         fontSize = getProperty(24, UnitValue.createPointValue(0.0F));
/*  969 */         if (!fontSize.isPointValue()) {
/*  970 */           logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(24) }));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  976 */         textAscent = (this.maxTextAscent == 0.0F && this.maxTextDescent == 0.0F && Math.abs(this.maxAscent) + Math.abs(this.maxDescent) != 0.0F && !containsImage()) ? (fontSize.getValue() * 0.8F) : this.maxTextAscent;
/*  977 */         textDescent = (this.maxTextAscent == 0.0F && this.maxTextDescent == 0.0F && Math.abs(this.maxAscent) + Math.abs(this.maxDescent) != 0.0F && !containsImage()) ? (-fontSize.getValue() * 0.2F) : this.maxTextDescent;
/*  978 */         return Math.max(textAscent + (textAscent - textDescent) * (leading.getValue() - 1.0F) / 2.0F, this.maxBlockAscent) - this.maxAscent;
/*      */     } 
/*  980 */     throw new IllegalStateException();
/*      */   } float getBottomLeadingIndent(Leading leading) {
/*      */     UnitValue fontSize;
/*      */     float textAscent;
/*      */     float textDescent;
/*  985 */     switch (leading.getType()) {
/*      */       case 1:
/*  987 */         return (Math.max(leading.getValue(), this.maxBlockAscent - this.maxBlockDescent) - this.occupiedArea.getBBox().getHeight()) / 2.0F;
/*      */       case 2:
/*  989 */         fontSize = getProperty(24, UnitValue.createPointValue(0.0F));
/*  990 */         if (!fontSize.isPointValue()) {
/*  991 */           logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(24) }));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  997 */         textAscent = (this.maxTextAscent == 0.0F && this.maxTextDescent == 0.0F && !containsImage()) ? (fontSize.getValue() * 0.8F) : this.maxTextAscent;
/*  998 */         textDescent = (this.maxTextAscent == 0.0F && this.maxTextDescent == 0.0F && !containsImage()) ? (-fontSize.getValue() * 0.2F) : this.maxTextDescent;
/*  999 */         return Math.max(-textDescent + (textAscent - textDescent) * (leading.getValue() - 1.0F) / 2.0F, -this.maxBlockDescent) + this.maxDescent;
/*      */     } 
/* 1001 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   static void adjustChildPositionsAfterReordering(List<IRenderer> children, float initialXPos) {
/* 1006 */     float currentXPos = initialXPos;
/* 1007 */     for (IRenderer child : children) {
/* 1008 */       if (!FloatingHelper.isRendererFloating(child)) {
/*      */         float currentWidth;
/* 1010 */         if (child instanceof TextRenderer) {
/* 1011 */           currentWidth = ((TextRenderer)child).calculateLineWidth();
/* 1012 */           UnitValue[] margins = ((TextRenderer)child).getMargins();
/* 1013 */           if (!margins[1].isPointValue() && logger.isErrorEnabled()) {
/* 1014 */             logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { "right margin" }));
/*      */           }
/* 1016 */           if (!margins[3].isPointValue() && logger.isErrorEnabled()) {
/* 1017 */             logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { "left margin" }));
/*      */           }
/* 1019 */           UnitValue[] paddings = ((TextRenderer)child).getPaddings();
/* 1020 */           if (!paddings[1].isPointValue() && logger.isErrorEnabled()) {
/* 1021 */             logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { "right padding" }));
/*      */           }
/* 1023 */           if (!paddings[3].isPointValue() && logger.isErrorEnabled()) {
/* 1024 */             logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { "left padding" }));
/*      */           }
/* 1026 */           currentWidth += margins[1].getValue() + margins[3].getValue() + paddings[1].getValue() + paddings[3].getValue();
/* 1027 */           ((TextRenderer)child).occupiedArea.getBBox().setX(currentXPos).setWidth(currentWidth);
/*      */         } else {
/* 1029 */           currentWidth = child.getOccupiedArea().getBBox().getWidth();
/* 1030 */           child.move(currentXPos - child.getOccupiedArea().getBBox().getX(), 0.0F);
/*      */         } 
/* 1032 */         currentXPos += currentWidth;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private LineRenderer[] splitNotFittingFloat(int childPos, LayoutResult childResult) {
/* 1038 */     LineRenderer[] split = split();
/* 1039 */     (split[0]).childRenderers.addAll(this.childRenderers.subList(0, childPos));
/* 1040 */     (split[0]).childRenderers.add(childResult.getSplitRenderer());
/* 1041 */     (split[1]).childRenderers.add(childResult.getOverflowRenderer());
/* 1042 */     (split[1]).childRenderers.addAll(this.childRenderers.subList(childPos + 1, this.childRenderers.size()));
/*      */     
/* 1044 */     return split;
/*      */   }
/*      */   
/*      */   private void adjustLineOnFloatPlaced(Rectangle layoutBox, int childPos, FloatPropertyValue kidFloatPropertyVal, Rectangle justPlacedFloatBox) {
/* 1048 */     if (justPlacedFloatBox.getBottom() >= layoutBox.getTop() || justPlacedFloatBox.getTop() < layoutBox.getTop()) {
/*      */       return;
/*      */     }
/*      */     
/* 1052 */     float floatWidth = justPlacedFloatBox.getWidth();
/* 1053 */     if (kidFloatPropertyVal.equals(FloatPropertyValue.LEFT)) {
/* 1054 */       layoutBox.setWidth(layoutBox.getWidth() - floatWidth).moveRight(floatWidth);
/* 1055 */       this.occupiedArea.getBBox().moveRight(floatWidth);
/* 1056 */       for (int i = 0; i < childPos; i++) {
/* 1057 */         IRenderer prevChild = this.childRenderers.get(i);
/* 1058 */         if (!FloatingHelper.isRendererFloating(prevChild)) {
/* 1059 */           prevChild.move(floatWidth, 0.0F);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 1064 */       layoutBox.setWidth(layoutBox.getWidth() - floatWidth);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void replaceSplitRendererKidFloats(Map<Integer, IRenderer> floatsToNextPageSplitRenderers, LineRenderer splitRenderer) {
/* 1069 */     for (Map.Entry<Integer, IRenderer> splitFloat : floatsToNextPageSplitRenderers.entrySet()) {
/* 1070 */       if (splitFloat.getValue() != null) {
/* 1071 */         splitRenderer.childRenderers.set(((Integer)splitFloat.getKey()).intValue(), splitFloat.getValue()); continue;
/*      */       } 
/* 1073 */       splitRenderer.childRenderers.set(((Integer)splitFloat.getKey()).intValue(), null);
/*      */     } 
/*      */     
/* 1076 */     for (int i = splitRenderer.getChildRenderers().size() - 1; i >= 0; i--) {
/* 1077 */       if (splitRenderer.getChildRenderers().get(i) == null) {
/* 1078 */         splitRenderer.getChildRenderers().remove(i);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private IRenderer getLastNonFloatChildRenderer() {
/* 1084 */     for (int i = this.childRenderers.size() - 1; i >= 0; ) {
/* 1085 */       if (FloatingHelper.isRendererFloating(this.childRenderers.get(i))) {
/*      */         i--; continue;
/*      */       } 
/* 1088 */       return this.childRenderers.get(i);
/*      */     } 
/* 1090 */     return null;
/*      */   }
/*      */   
/*      */   private TabStop getNextTabStop(float curWidth) {
/* 1094 */     NavigableMap<Float, TabStop> tabStops = getProperty(69);
/*      */     
/* 1096 */     Map.Entry<Float, TabStop> nextTabStopEntry = null;
/* 1097 */     TabStop nextTabStop = null;
/*      */     
/* 1099 */     if (tabStops != null)
/* 1100 */       nextTabStopEntry = tabStops.higherEntry(Float.valueOf(curWidth)); 
/* 1101 */     if (nextTabStopEntry != null) {
/* 1102 */       nextTabStop = nextTabStopEntry.getValue();
/*      */     }
/*      */     
/* 1105 */     return nextTabStop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TabStop calculateTab(IRenderer childRenderer, float curWidth, float lineWidth) {
/* 1114 */     TabStop nextTabStop = getNextTabStop(curWidth);
/*      */     
/* 1116 */     if (nextTabStop == null) {
/* 1117 */       processDefaultTab(childRenderer, curWidth, lineWidth);
/* 1118 */       return null;
/*      */     } 
/*      */     
/* 1121 */     childRenderer.setProperty(68, nextTabStop.getTabLeader());
/* 1122 */     childRenderer.setProperty(77, UnitValue.createPointValue(nextTabStop.getTabPosition() - curWidth));
/* 1123 */     childRenderer.setProperty(85, UnitValue.createPointValue(this.maxAscent - this.maxDescent));
/*      */     
/* 1125 */     if (nextTabStop.getTabAlignment() == TabAlignment.LEFT) {
/* 1126 */       return null;
/*      */     }
/*      */     
/* 1129 */     return nextTabStop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float calculateTab(Rectangle layoutBox, float curWidth, TabStop tabStop, List<IRenderer> affectedRenderers, IRenderer tabRenderer) {
/* 1137 */     float anchorPosition, processedRenderersWidth, sumOfAffectedRendererWidths = 0.0F;
/* 1138 */     for (IRenderer renderer : affectedRenderers) {
/* 1139 */       sumOfAffectedRendererWidths += renderer.getOccupiedArea().getBBox().getWidth();
/*      */     }
/* 1141 */     float tabWidth = 0.0F;
/* 1142 */     switch (tabStop.getTabAlignment()) {
/*      */       case RIGHT:
/* 1144 */         tabWidth = tabStop.getTabPosition() - curWidth - sumOfAffectedRendererWidths;
/*      */         break;
/*      */       case CENTER:
/* 1147 */         tabWidth = tabStop.getTabPosition() - curWidth - sumOfAffectedRendererWidths / 2.0F;
/*      */         break;
/*      */       case ANCHOR:
/* 1150 */         anchorPosition = -1.0F;
/* 1151 */         processedRenderersWidth = 0.0F;
/* 1152 */         for (IRenderer renderer : affectedRenderers) {
/* 1153 */           anchorPosition = ((TextRenderer)renderer).getTabAnchorCharacterPosition();
/* 1154 */           if (-1.0F != anchorPosition) {
/*      */             break;
/*      */           }
/* 1157 */           processedRenderersWidth += renderer.getOccupiedArea().getBBox().getWidth();
/*      */         } 
/*      */         
/* 1160 */         if (anchorPosition == -1.0F) {
/* 1161 */           anchorPosition = 0.0F;
/*      */         }
/* 1163 */         tabWidth = tabStop.getTabPosition() - curWidth - anchorPosition - processedRenderersWidth;
/*      */         break;
/*      */     } 
/* 1166 */     if (tabWidth < 0.0F) {
/* 1167 */       tabWidth = 0.0F;
/*      */     }
/* 1169 */     if (curWidth + tabWidth + sumOfAffectedRendererWidths > layoutBox.getWidth()) {
/* 1170 */       tabWidth -= curWidth + sumOfAffectedRendererWidths + tabWidth - layoutBox.getWidth();
/*      */     }
/*      */     
/* 1173 */     tabRenderer.setProperty(77, UnitValue.createPointValue(tabWidth));
/* 1174 */     tabRenderer.setProperty(85, UnitValue.createPointValue(this.maxAscent - this.maxDescent));
/*      */     
/* 1176 */     return tabWidth;
/*      */   }
/*      */   
/*      */   private void processDefaultTab(IRenderer tabRenderer, float curWidth, float lineWidth) {
/* 1180 */     Float tabDefault = getPropertyAsFloat(67);
/* 1181 */     Float tabWidth = Float.valueOf(tabDefault.floatValue() - curWidth % tabDefault.floatValue());
/* 1182 */     if (curWidth + tabWidth.floatValue() > lineWidth)
/* 1183 */       tabWidth = Float.valueOf(lineWidth - curWidth); 
/* 1184 */     tabRenderer.setProperty(77, UnitValue.createPointValue(tabWidth.floatValue()));
/* 1185 */     tabRenderer.setProperty(85, UnitValue.createPointValue(this.maxAscent - this.maxDescent));
/*      */   }
/*      */   
/*      */   private void updateChildrenParent() {
/* 1189 */     for (IRenderer renderer : this.childRenderers) {
/* 1190 */       renderer.setParent(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int trimFirst() {
/* 1200 */     int totalNumberOfTrimmedGlyphs = 0;
/* 1201 */     for (IRenderer renderer : this.childRenderers) {
/* 1202 */       if (FloatingHelper.isRendererFloating(renderer)) {
/*      */         continue;
/*      */       }
/* 1205 */       if (renderer instanceof TextRenderer) {
/* 1206 */         TextRenderer textRenderer = (TextRenderer)renderer;
/* 1207 */         GlyphLine currentText = textRenderer.getText();
/* 1208 */         if (currentText != null) {
/* 1209 */           int prevTextStart = currentText.start;
/* 1210 */           textRenderer.trimFirst();
/* 1211 */           int numOfTrimmedGlyphs = (textRenderer.getText()).start - prevTextStart;
/* 1212 */           totalNumberOfTrimmedGlyphs += numOfTrimmedGlyphs;
/*      */         } 
/* 1214 */         if (textRenderer.length() > 0) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1221 */     return totalNumberOfTrimmedGlyphs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BaseDirection applyOtf() {
/* 1230 */     BaseDirection baseDirection = getProperty(7);
/* 1231 */     for (IRenderer renderer : this.childRenderers) {
/* 1232 */       if (renderer instanceof TextRenderer) {
/* 1233 */         ((TextRenderer)renderer).applyOtf();
/* 1234 */         if (baseDirection == null || baseDirection == BaseDirection.NO_BIDI) {
/* 1235 */           baseDirection = (BaseDirection)renderer.getOwnProperty(7);
/*      */         }
/*      */       } 
/*      */     } 
/* 1239 */     return baseDirection;
/*      */   }
/*      */   
/*      */   static boolean isTextRendererAndRequiresSpecialScriptPreLayoutProcessing(IRenderer childRenderer) {
/* 1243 */     return (childRenderer instanceof TextRenderer && ((TextRenderer)childRenderer)
/* 1244 */       .getSpecialScriptsWordBreakPoints() == null && ((TextRenderer)childRenderer)
/* 1245 */       .textContainsSpecialScriptGlyphs(false));
/*      */   }
/*      */   
/*      */   static boolean isChildFloating(IRenderer childRenderer) {
/* 1249 */     FloatPropertyValue kidFloatPropertyVal = (FloatPropertyValue)childRenderer.getProperty(99);
/* 1250 */     return (childRenderer instanceof AbstractRenderer && 
/* 1251 */       FloatingHelper.isRendererFloating(childRenderer, kidFloatPropertyVal));
/*      */   }
/*      */ 
/*      */   
/*      */   static void updateSpecialScriptLayoutResults(Map<Integer, LayoutResult> specialScriptLayoutResults, IRenderer childRenderer, int childPos, LayoutResult childResult) {
/* 1256 */     if (childRenderer instanceof TextRenderer && ((TextRenderer)childRenderer)
/* 1257 */       .textContainsSpecialScriptGlyphs(true)) {
/* 1258 */       specialScriptLayoutResults.put(Integer.valueOf(childPos), childResult);
/* 1259 */     } else if (!specialScriptLayoutResults.isEmpty() && !isChildFloating(childRenderer)) {
/* 1260 */       specialScriptLayoutResults.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static float getCurWidthSpecialScriptsDecrement(int childPos, int newChildPos, Map<Integer, LayoutResult> specialScriptLayoutResults) {
/* 1266 */     float decrement = 0.0F;
/*      */     
/* 1268 */     if (childPos != newChildPos) {
/* 1269 */       for (int i = childPos - 1; i >= newChildPos; i--) {
/* 1270 */         if (specialScriptLayoutResults.get(Integer.valueOf(i)) != null) {
/* 1271 */           decrement += ((LayoutResult)specialScriptLayoutResults.get(Integer.valueOf(i))).getOccupiedArea().getBBox().getWidth();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1276 */     return decrement;
/*      */   }
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
/*      */   void specialScriptPreLayoutProcessing(int childPos) {
/* 1301 */     SpecialScriptsContainingTextRendererSequenceInfo info = getSpecialScriptsContainingTextRendererSequenceInfo(childPos);
/* 1302 */     int numberOfSequentialTextRenderers = info.numberOfSequentialTextRenderers;
/* 1303 */     String sequentialTextContent = info.sequentialTextContent;
/* 1304 */     List<Integer> indicesOfFloating = info.indicesOfFloating;
/* 1305 */     List<Integer> possibleBreakPointsGlobal = TypographyUtils.getPossibleBreaks(sequentialTextContent);
/*      */     
/* 1307 */     distributePossibleBreakPointsOverSequentialTextRenderers(childPos, numberOfSequentialTextRenderers, possibleBreakPointsGlobal, indicesOfFloating);
/*      */   }
/*      */ 
/*      */   
/*      */   SpecialScriptsContainingTextRendererSequenceInfo getSpecialScriptsContainingTextRendererSequenceInfo(int childPos) {
/* 1312 */     StringBuilder sequentialTextContentBuilder = new StringBuilder();
/* 1313 */     int numberOfSequentialTextRenderers = 0;
/* 1314 */     List<Integer> indicesOfFloating = new ArrayList<>();
/* 1315 */     for (int i = childPos; i < this.childRenderers.size(); i++) {
/* 1316 */       if (isChildFloating(this.childRenderers.get(i))) {
/* 1317 */         numberOfSequentialTextRenderers++;
/* 1318 */         indicesOfFloating.add(Integer.valueOf(i));
/*      */       }
/* 1320 */       else if (this.childRenderers.get(i) instanceof TextRenderer && ((TextRenderer)this.childRenderers
/* 1321 */         .get(i))
/* 1322 */         .textContainsSpecialScriptGlyphs(false)) {
/* 1323 */         sequentialTextContentBuilder.append(((TextRenderer)this.childRenderers.get(i)).text.toString());
/* 1324 */         numberOfSequentialTextRenderers++;
/*      */       } else {
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1330 */     return new SpecialScriptsContainingTextRendererSequenceInfo(numberOfSequentialTextRenderers, sequentialTextContentBuilder
/* 1331 */         .toString(), indicesOfFloating);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void distributePossibleBreakPointsOverSequentialTextRenderers(int childPos, int numberOfSequentialTextRenderers, List<Integer> possibleBreakPointsGlobal, List<Integer> indicesOfFloating) {
/* 1337 */     int alreadyProcessedNumberOfCharsWithinGlyphLines = 0;
/* 1338 */     int indexToBeginWith = 0;
/* 1339 */     for (int i = 0; i < numberOfSequentialTextRenderers; i++) {
/* 1340 */       if (!indicesOfFloating.contains(Integer.valueOf(i))) {
/* 1341 */         TextRenderer childTextRenderer = (TextRenderer)this.childRenderers.get(childPos + i);
/* 1342 */         List<Integer> amountOfCharsBetweenTextStartAndActualTextChunk = new ArrayList<>();
/* 1343 */         List<Integer> glyphLineBasedIndicesOfActualTextChunkEnds = new ArrayList<>();
/*      */         
/* 1345 */         fillActualTextChunkRelatedLists(childTextRenderer.getText(), amountOfCharsBetweenTextStartAndActualTextChunk, glyphLineBasedIndicesOfActualTextChunkEnds);
/*      */ 
/*      */         
/* 1348 */         List<Integer> possibleBreakPoints = new ArrayList<>();
/* 1349 */         for (int j = indexToBeginWith; j < possibleBreakPointsGlobal.size(); j++) {
/* 1350 */           int shiftedBreakPoint = ((Integer)possibleBreakPointsGlobal.get(j)).intValue() - alreadyProcessedNumberOfCharsWithinGlyphLines;
/*      */ 
/*      */           
/* 1353 */           int amountOfCharsBetweenTextStartAndTextEnd = ((Integer)amountOfCharsBetweenTextStartAndActualTextChunk.get(amountOfCharsBetweenTextStartAndActualTextChunk.size() - 1)).intValue();
/* 1354 */           if (shiftedBreakPoint > amountOfCharsBetweenTextStartAndTextEnd) {
/* 1355 */             indexToBeginWith = j;
/* 1356 */             alreadyProcessedNumberOfCharsWithinGlyphLines += amountOfCharsBetweenTextStartAndTextEnd;
/*      */             break;
/*      */           } 
/* 1359 */           possibleBreakPoints.add(Integer.valueOf(shiftedBreakPoint));
/*      */         } 
/*      */         
/* 1362 */         List<Integer> glyphLineBasedPossibleBreakPoints = convertPossibleBreakPointsToGlyphLineBased(possibleBreakPoints, amountOfCharsBetweenTextStartAndActualTextChunk, glyphLineBasedIndicesOfActualTextChunkEnds);
/*      */ 
/*      */         
/* 1365 */         childTextRenderer.setSpecialScriptsWordBreakPoints(glyphLineBasedPossibleBreakPoints);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   LastFittingChildRendererData getIndexAndLayoutResultOfTheLastRendererToRemainOnTheLine(int childPos, Map<Integer, LayoutResult> specialScriptLayoutResults, boolean wasParentsHeightClipped, List<IRenderer> floatsOverflowedToNextLine) {
/*      */     TextLayoutResult textLayoutResult;
/* 1373 */     int indexOfRendererContainingLastFullyFittingWord = childPos;
/* 1374 */     int splitPosition = 0;
/* 1375 */     boolean needToSplitRendererContainingLastFullyFittingWord = false;
/* 1376 */     int fittingLengthWithTrailingRightSideSpaces = 0;
/* 1377 */     int amountOfTrailingRightSideSpaces = 0;
/* 1378 */     Set<Integer> indicesOfFloats = new HashSet<>();
/* 1379 */     LayoutResult childPosLayoutResult = specialScriptLayoutResults.get(Integer.valueOf(childPos));
/* 1380 */     LayoutResult returnLayoutResult = null;
/* 1381 */     for (int analyzedTextRendererIndex = childPos; analyzedTextRendererIndex >= 0; analyzedTextRendererIndex--) {
/*      */       
/* 1383 */       TextRenderer textRenderer = (TextRenderer)this.childRenderers.get(analyzedTextRendererIndex);
/* 1384 */       if (analyzedTextRendererIndex != childPos) {
/* 1385 */         fittingLengthWithTrailingRightSideSpaces = textRenderer.length();
/* 1386 */       } else if (childPosLayoutResult.getSplitRenderer() != null) {
/* 1387 */         TextRenderer splitTextRenderer = (TextRenderer)childPosLayoutResult.getSplitRenderer();
/* 1388 */         GlyphLine splitText = splitTextRenderer.text;
/* 1389 */         if (splitTextRenderer.length() > 0) {
/* 1390 */           fittingLengthWithTrailingRightSideSpaces = splitTextRenderer.length();
/* 1391 */           while (splitText.end + amountOfTrailingRightSideSpaces < splitText.size() && 
/* 1392 */             TextUtil.isWhitespace(splitText.get(splitText.end + amountOfTrailingRightSideSpaces))) {
/* 1393 */             fittingLengthWithTrailingRightSideSpaces++;
/* 1394 */             amountOfTrailingRightSideSpaces++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1400 */       if (fittingLengthWithTrailingRightSideSpaces > 0) {
/* 1401 */         List<Integer> breakPoints = textRenderer.getSpecialScriptsWordBreakPoints();
/* 1402 */         if (breakPoints != null && breakPoints.size() > 0 && ((Integer)breakPoints.get(0)).intValue() != -1) {
/* 1403 */           int possibleBreakPointPosition = TextRenderer.findPossibleBreaksSplitPosition(textRenderer
/* 1404 */               .getSpecialScriptsWordBreakPoints(), fittingLengthWithTrailingRightSideSpaces + textRenderer.text.start, false);
/*      */           
/* 1406 */           if (possibleBreakPointPosition > -1) {
/* 1407 */             splitPosition = ((Integer)breakPoints.get(possibleBreakPointPosition)).intValue() - amountOfTrailingRightSideSpaces;
/* 1408 */             needToSplitRendererContainingLastFullyFittingWord = (splitPosition != textRenderer.text.end);
/* 1409 */             if (!needToSplitRendererContainingLastFullyFittingWord) {
/* 1410 */               analyzedTextRendererIndex++;
/* 1411 */               while (analyzedTextRendererIndex <= childPos && 
/* 1412 */                 isChildFloating(this.childRenderers.get(analyzedTextRendererIndex))) {
/* 1413 */                 analyzedTextRendererIndex++;
/*      */               }
/*      */             } 
/* 1416 */             indexOfRendererContainingLastFullyFittingWord = analyzedTextRendererIndex;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1422 */       int amountOfFloating = 0;
/*      */       
/* 1424 */       while (analyzedTextRendererIndex - 1 >= 0 && 
/* 1425 */         isChildFloating(this.childRenderers.get(analyzedTextRendererIndex - 1))) {
/* 1426 */         indicesOfFloats.add(Integer.valueOf(analyzedTextRendererIndex - 1));
/* 1427 */         analyzedTextRendererIndex--;
/* 1428 */         amountOfFloating++;
/*      */       } 
/*      */ 
/*      */       
/* 1432 */       SpecialScriptsContainingSequenceStatus status = getSpecialScriptsContainingSequenceStatus(analyzedTextRendererIndex);
/*      */ 
/*      */ 
/*      */       
/* 1436 */       if (status == SpecialScriptsContainingSequenceStatus.FORCED_SPLIT) {
/* 1437 */         if (childPosLayoutResult.getStatus() != 3) {
/* 1438 */           returnLayoutResult = childPosLayoutResult;
/*      */         }
/* 1440 */         indexOfRendererContainingLastFullyFittingWord = childPos;
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 1446 */       if (status == SpecialScriptsContainingSequenceStatus.MOVE_SEQUENCE_CONTAINING_SPECIAL_SCRIPTS_ON_NEXT_LINE) {
/*      */         
/* 1448 */         indexOfRendererContainingLastFullyFittingWord = analyzedTextRendererIndex + amountOfFloating;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1453 */     updateFloatsOverflowedToNextLine(floatsOverflowedToNextLine, indicesOfFloats, indexOfRendererContainingLastFullyFittingWord);
/*      */ 
/*      */     
/* 1456 */     if (returnLayoutResult == null) {
/* 1457 */       returnLayoutResult = childPosLayoutResult;
/*      */       
/* 1459 */       TextRenderer childRenderer = (TextRenderer)this.childRenderers.get(indexOfRendererContainingLastFullyFittingWord);
/*      */       
/* 1461 */       if (needToSplitRendererContainingLastFullyFittingWord) {
/* 1462 */         int amountOfFitOnTheFirstLayout = fittingLengthWithTrailingRightSideSpaces - amountOfTrailingRightSideSpaces + childRenderer.text.start;
/*      */         
/* 1464 */         if (amountOfFitOnTheFirstLayout != splitPosition) {
/* 1465 */           LayoutArea layoutArea = childRenderer.getOccupiedArea();
/* 1466 */           childRenderer.setSpecialScriptFirstNotFittingIndex(splitPosition);
/* 1467 */           returnLayoutResult = childRenderer.layout(new LayoutContext(layoutArea, wasParentsHeightClipped));
/* 1468 */           childRenderer.setSpecialScriptFirstNotFittingIndex(-1);
/*      */         } 
/*      */       } else {
/* 1471 */         textLayoutResult = new TextLayoutResult(3, null, null, childRenderer);
/*      */       } 
/*      */     } 
/*      */     
/* 1475 */     return new LastFittingChildRendererData(indexOfRendererContainingLastFullyFittingWord, (LayoutResult)textLayoutResult);
/*      */   }
/*      */ 
/*      */   
/*      */   void updateFloatsOverflowedToNextLine(List<IRenderer> floatsOverflowedToNextLine, Set<Integer> indicesOfFloats, int indexOfRendererContainingLastFullyFittingWord) {
/* 1480 */     for (Iterator<Integer> iterator = indicesOfFloats.iterator(); iterator.hasNext(); ) { int index = ((Integer)iterator.next()).intValue();
/* 1481 */       if (index > indexOfRendererContainingLastFullyFittingWord) {
/* 1482 */         floatsOverflowedToNextLine.remove(this.childRenderers.get(index));
/*      */       } }
/*      */   
/*      */   }
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
/*      */   SpecialScriptsContainingSequenceStatus getSpecialScriptsContainingSequenceStatus(int analyzedTextRendererIndex) {
/* 1502 */     boolean moveSequenceContainingSpecialScriptsOnNextLine = false;
/* 1503 */     boolean moveToPreviousTextRendererContainingSpecialScripts = false;
/*      */     
/* 1505 */     if (analyzedTextRendererIndex > 0) {
/* 1506 */       IRenderer prevChildRenderer = this.childRenderers.get(analyzedTextRendererIndex - 1);
/* 1507 */       if (prevChildRenderer instanceof TextRenderer) {
/* 1508 */         if (((TextRenderer)prevChildRenderer).textContainsSpecialScriptGlyphs(true)) {
/* 1509 */           moveToPreviousTextRendererContainingSpecialScripts = true;
/*      */         } else {
/* 1511 */           moveSequenceContainingSpecialScriptsOnNextLine = true;
/*      */         } 
/* 1513 */       } else if (prevChildRenderer instanceof ImageRenderer || isInlineBlockChild(prevChildRenderer)) {
/* 1514 */         moveSequenceContainingSpecialScriptsOnNextLine = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1518 */     boolean forcedSplit = (!moveToPreviousTextRendererContainingSpecialScripts && !moveSequenceContainingSpecialScriptsOnNextLine);
/*      */ 
/*      */     
/* 1521 */     if (moveSequenceContainingSpecialScriptsOnNextLine)
/* 1522 */       return SpecialScriptsContainingSequenceStatus.MOVE_SEQUENCE_CONTAINING_SPECIAL_SCRIPTS_ON_NEXT_LINE; 
/* 1523 */     if (forcedSplit) {
/* 1524 */       return SpecialScriptsContainingSequenceStatus.FORCED_SPLIT;
/*      */     }
/* 1526 */     return SpecialScriptsContainingSequenceStatus.MOVE_TO_PREVIOUS_TEXT_RENDERER_CONTAINING_SPECIAL_SCRIPTS;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateBidiLevels(int totalNumberOfTrimmedGlyphs, BaseDirection baseDirection) {
/* 1531 */     if (totalNumberOfTrimmedGlyphs != 0 && this.levels != null) {
/* 1532 */       this.levels = Arrays.copyOfRange(this.levels, totalNumberOfTrimmedGlyphs, this.levels.length);
/*      */     }
/*      */     
/* 1535 */     List<Integer> unicodeIdsReorderingList = null;
/* 1536 */     if (this.levels == null && baseDirection != null && baseDirection != BaseDirection.NO_BIDI) {
/* 1537 */       unicodeIdsReorderingList = new ArrayList<>();
/* 1538 */       boolean newLineFound = false;
/* 1539 */       for (IRenderer child : this.childRenderers) {
/* 1540 */         if (newLineFound) {
/*      */           break;
/*      */         }
/* 1543 */         if (child instanceof TextRenderer) {
/* 1544 */           GlyphLine text = ((TextRenderer)child).getText();
/* 1545 */           for (int i = text.start; i < text.end; i++) {
/* 1546 */             Glyph glyph = text.get(i);
/* 1547 */             if (TextUtil.isNewLine(glyph)) {
/* 1548 */               newLineFound = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/* 1553 */             int unicode = glyph.hasValidUnicode() ? glyph.getUnicode() : glyph.getUnicodeChars()[0];
/* 1554 */             unicodeIdsReorderingList.add(Integer.valueOf(unicode));
/*      */           } 
/*      */         } 
/*      */       } 
/* 1558 */       this.levels = (unicodeIdsReorderingList.size() > 0) ? TypographyUtils.getBidiLevels(baseDirection, ArrayUtil.toIntArray(unicodeIdsReorderingList)) : null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resolveChildrenFonts() {
/* 1566 */     List<IRenderer> newChildRenderers = new ArrayList<>(this.childRenderers.size());
/* 1567 */     boolean updateChildRenderers = false;
/* 1568 */     for (IRenderer child : this.childRenderers) {
/* 1569 */       if (child instanceof TextRenderer) {
/* 1570 */         if (((TextRenderer)child).resolveFonts(newChildRenderers))
/* 1571 */           updateChildRenderers = true; 
/*      */         continue;
/*      */       } 
/* 1574 */       newChildRenderers.add(child);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1579 */     if (updateChildRenderers) {
/* 1580 */       this.childRenderers = newChildRenderers;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float decreaseRelativeWidthByChildAdditionalWidth(IRenderer childRenderer, float normalizedChildWidth) {
/* 1588 */     if (childRenderer instanceof AbstractRenderer) {
/* 1589 */       Rectangle dummyRect = new Rectangle(normalizedChildWidth, 0.0F);
/* 1590 */       ((AbstractRenderer)childRenderer).applyMargins(dummyRect, false);
/* 1591 */       if (!isBorderBoxSizing(childRenderer)) {
/* 1592 */         ((AbstractRenderer)childRenderer).applyBorderBox(dummyRect, false);
/* 1593 */         ((AbstractRenderer)childRenderer).applyPaddings(dummyRect, false);
/*      */       } 
/* 1595 */       normalizedChildWidth = dummyRect.getWidth();
/*      */     } 
/* 1597 */     return normalizedChildWidth;
/*      */   }
/*      */   
/*      */   private boolean isInlineBlockChild(IRenderer child) {
/* 1601 */     return (child instanceof BlockRenderer || child instanceof TableRenderer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void fillActualTextChunkRelatedLists(GlyphLine glyphLine, List<Integer> amountOfCharsBetweenTextStartAndActualTextChunk, List<Integer> glyphLineBasedIndicesOfActualTextChunkEnds) {
/* 1608 */     ActualTextIterator actualTextIterator = new ActualTextIterator(glyphLine);
/*      */     
/* 1610 */     int amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph = 0;
/* 1611 */     while (actualTextIterator.hasNext()) {
/* 1612 */       GlyphLine.GlyphLinePart part = actualTextIterator.next();
/* 1613 */       int amountOfCharsWithinCurrentActualTextOrGlyph = 0;
/* 1614 */       if (part.actualText != null) {
/* 1615 */         amountOfCharsWithinCurrentActualTextOrGlyph = part.actualText.length();
/* 1616 */         int nextAmountOfChars = amountOfCharsWithinCurrentActualTextOrGlyph + amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph;
/*      */         
/* 1618 */         amountOfCharsBetweenTextStartAndActualTextChunk.add(Integer.valueOf(nextAmountOfChars));
/* 1619 */         glyphLineBasedIndicesOfActualTextChunkEnds.add(Integer.valueOf(part.end));
/* 1620 */         amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph = nextAmountOfChars; continue;
/*      */       } 
/* 1622 */       for (int j = part.start; j < part.end; j++) {
/* 1623 */         char[] chars = glyphLine.get(j).getChars();
/* 1624 */         amountOfCharsWithinCurrentActualTextOrGlyph = (chars != null) ? chars.length : 0;
/* 1625 */         int nextAmountOfChars = amountOfCharsWithinCurrentActualTextOrGlyph + amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph;
/*      */         
/* 1627 */         amountOfCharsBetweenTextStartAndActualTextChunk.add(Integer.valueOf(nextAmountOfChars));
/* 1628 */         glyphLineBasedIndicesOfActualTextChunkEnds.add(Integer.valueOf(j + 1));
/* 1629 */         amountOfCharsBetweenTextStartAndCurrentActualTextStartOrGlyph = nextAmountOfChars;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<Integer> convertPossibleBreakPointsToGlyphLineBased(List<Integer> possibleBreakPoints, List<Integer> amountOfChars, List<Integer> indices) {
/* 1637 */     if (possibleBreakPoints.isEmpty()) {
/* 1638 */       possibleBreakPoints.add(Integer.valueOf(-1));
/* 1639 */       return possibleBreakPoints;
/*      */     } 
/* 1641 */     List<Integer> glyphLineBased = new ArrayList<>();
/*      */     
/* 1643 */     for (Iterator<Integer> iterator = possibleBreakPoints.iterator(); iterator.hasNext(); ) { int j = ((Integer)iterator.next()).intValue();
/* 1644 */       int found = TextRenderer.findPossibleBreaksSplitPosition(amountOfChars, j, true);
/* 1645 */       if (found >= 0) {
/* 1646 */         glyphLineBased.add(indices.get(found));
/*      */       } }
/*      */     
/* 1649 */     return glyphLineBased;
/*      */   }
/*      */   
/*      */   static class RendererGlyph
/*      */   {
/*      */     public Glyph glyph;
/*      */     public TextRenderer renderer;
/*      */     
/*      */     public RendererGlyph(Glyph glyph, TextRenderer textRenderer) {
/* 1658 */       this.glyph = glyph;
/* 1659 */       this.renderer = textRenderer;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class SpecialScriptsContainingTextRendererSequenceInfo
/*      */   {
/*      */     public int numberOfSequentialTextRenderers;
/*      */     
/*      */     public String sequentialTextContent;
/*      */     
/*      */     List<Integer> indicesOfFloating;
/*      */ 
/*      */     
/*      */     public SpecialScriptsContainingTextRendererSequenceInfo(int numberOfSequentialTextRenderers, String sequentialTextContent, List<Integer> indicesOfFloating) {
/* 1674 */       this.numberOfSequentialTextRenderers = numberOfSequentialTextRenderers;
/* 1675 */       this.sequentialTextContent = sequentialTextContent;
/* 1676 */       this.indicesOfFloating = indicesOfFloating;
/*      */     }
/*      */   }
/*      */   
/*      */   class LastFittingChildRendererData {
/*      */     public int childIndex;
/*      */     public LayoutResult childLayoutResult;
/*      */     
/*      */     public LastFittingChildRendererData(int childIndex, LayoutResult childLayoutResult) {
/* 1685 */       this.childIndex = childIndex;
/* 1686 */       this.childLayoutResult = childLayoutResult;
/*      */     }
/*      */   }
/*      */   
/*      */   enum SpecialScriptsContainingSequenceStatus {
/* 1691 */     MOVE_SEQUENCE_CONTAINING_SPECIAL_SCRIPTS_ON_NEXT_LINE,
/* 1692 */     MOVE_TO_PREVIOUS_TEXT_RENDERER_CONTAINING_SPECIAL_SCRIPTS,
/* 1693 */     FORCED_SPLIT;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/LineRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */