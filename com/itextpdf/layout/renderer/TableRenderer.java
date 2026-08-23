/*      */ package com.itextpdf.layout.renderer;
/*      */ 
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*      */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*      */ import com.itextpdf.layout.borders.Border;
/*      */ import com.itextpdf.layout.element.Cell;
/*      */ import com.itextpdf.layout.element.Div;
/*      */ import com.itextpdf.layout.element.IElement;
/*      */ import com.itextpdf.layout.element.Table;
/*      */ import com.itextpdf.layout.layout.LayoutArea;
/*      */ import com.itextpdf.layout.layout.LayoutContext;
/*      */ import com.itextpdf.layout.layout.LayoutResult;
/*      */ import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*      */ import com.itextpdf.layout.property.BorderCollapsePropertyValue;
/*      */ import com.itextpdf.layout.property.CaptionSide;
/*      */ import com.itextpdf.layout.property.FloatPropertyValue;
/*      */ import com.itextpdf.layout.property.UnitValue;
/*      */ import com.itextpdf.layout.property.VerticalAlignment;
/*      */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Deque;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
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
/*      */ 
/*      */ public class TableRenderer
/*      */   extends AbstractRenderer
/*      */ {
/*   86 */   protected List<CellRenderer[]> rows = (List)new ArrayList<>();
/*      */   
/*      */   protected Table.RowRange rowRange;
/*      */   
/*      */   protected TableRenderer headerRenderer;
/*      */   
/*      */   protected TableRenderer footerRenderer;
/*      */   
/*      */   protected DivRenderer captionRenderer;
/*      */   protected boolean isOriginalNonSplitRenderer = true;
/*      */   TableBorders bordersHandler;
/*   97 */   private float[] columnWidths = null;
/*   98 */   private List<Float> heights = new ArrayList<>();
/*   99 */   private float[] countedColumnWidth = null;
/*      */ 
/*      */   
/*      */   private float totalWidthForColumns;
/*      */ 
/*      */   
/*      */   private float topBorderMaxWidth;
/*      */ 
/*      */ 
/*      */   
/*      */   private TableRenderer() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public TableRenderer(Table modelElement, Table.RowRange rowRange) {
/*  114 */     super((IElement)modelElement);
/*  115 */     setRowRange(rowRange);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableRenderer(Table modelElement) {
/*  124 */     this(modelElement, new Table.RowRange(0, modelElement.getNumberOfRows() - 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChild(IRenderer renderer) {
/*  132 */     if (renderer instanceof CellRenderer) {
/*      */ 
/*      */       
/*  135 */       Cell cell = (Cell)renderer.getModelElement();
/*  136 */       ((CellRenderer[])this.rows.get(cell.getRow() - this.rowRange.getStartRow() + cell.getRowspan() - 1))[cell.getCol()] = (CellRenderer)renderer;
/*      */     } else {
/*  138 */       Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/*  139 */       logger.error("Only CellRenderer could be added");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Rectangle applyBorderBox(Rectangle rect, Border[] borders, boolean reverse) {
/*  145 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/*  146 */       super.applyBorderBox(rect, borders, reverse);
/*      */     }
/*      */ 
/*      */     
/*  150 */     return rect;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Rectangle applyPaddings(Rectangle rect, UnitValue[] paddings, boolean reverse) {
/*  155 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/*  156 */       super.applyPaddings(rect, paddings, reverse);
/*      */     }
/*      */ 
/*      */     
/*  160 */     return rect;
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle applyPaddings(Rectangle rect, boolean reverse) {
/*  165 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/*  166 */       super.applyPaddings(rect, reverse);
/*      */     }
/*      */ 
/*      */     
/*  170 */     return rect;
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
/*      */   private Rectangle applySpacing(Rectangle rect, float horizontalSpacing, float verticalSpacing, boolean reverse) {
/*  184 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/*  185 */       return rect.applyMargins(verticalSpacing / 2.0F, horizontalSpacing / 2.0F, verticalSpacing / 2.0F, horizontalSpacing / 2.0F, reverse);
/*      */     }
/*      */ 
/*      */     
/*  189 */     return rect;
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
/*      */   private Rectangle applySingleSpacing(Rectangle rect, float spacing, boolean isHorizontal, boolean reverse) {
/*  203 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/*  204 */       if (isHorizontal) {
/*  205 */         return rect.applyMargins(0.0F, spacing / 2.0F, 0.0F, spacing / 2.0F, reverse);
/*      */       }
/*  207 */       return rect.applyMargins(spacing / 2.0F, 0.0F, spacing / 2.0F, 0.0F, reverse);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  212 */     return rect;
/*      */   }
/*      */   
/*      */   Table getTable() {
/*  216 */     return (Table)getModelElement();
/*      */   }
/*      */   
/*      */   private void initializeHeaderAndFooter(boolean isFirstOnThePage) {
/*  220 */     Table table = (Table)getModelElement();
/*  221 */     Border[] tableBorder = getBorders();
/*      */     
/*  223 */     Table headerElement = table.getHeader();
/*  224 */     boolean isFirstHeader = (this.rowRange.getStartRow() == 0 && this.isOriginalNonSplitRenderer);
/*      */     
/*  226 */     boolean headerShouldBeApplied = ((table.isComplete() || !this.rows.isEmpty()) && isFirstOnThePage && (!table.isSkipFirstHeader() || !isFirstHeader) && !Boolean.TRUE.equals(getOwnProperty(97)));
/*  227 */     if (headerElement != null && headerShouldBeApplied) {
/*  228 */       this.headerRenderer = initFooterOrHeaderRenderer(false, tableBorder);
/*      */     }
/*      */     
/*  231 */     Table footerElement = table.getFooter();
/*      */ 
/*      */     
/*  234 */     boolean footerShouldBeApplied = ((!table.isComplete() || 0 == table.getLastRowBottomBorder().size() || !table.isSkipLastFooter()) && !Boolean.TRUE.equals(getOwnProperty(96)));
/*  235 */     if (footerElement != null && footerShouldBeApplied) {
/*  236 */       this.footerRenderer = initFooterOrHeaderRenderer(true, tableBorder);
/*      */     }
/*      */   }
/*      */   
/*      */   private void initializeCaptionRenderer(Div caption) {
/*  241 */     if (this.isOriginalNonSplitRenderer && null != caption) {
/*  242 */       this.captionRenderer = (DivRenderer)caption.createRendererSubTree();
/*  243 */       this.captionRenderer.setParent(this.parent);
/*  244 */       LayoutTaggingHelper taggingHelper = getProperty(108);
/*  245 */       if (taggingHelper != null) {
/*  246 */         taggingHelper.addKidsHint(this, Collections.singletonList(this.captionRenderer));
/*  247 */         LayoutTaggingHelper.addTreeHints(taggingHelper, this.captionRenderer);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isOriginalRenderer() {
/*  253 */     return (this.isOriginalNonSplitRenderer && !isFooterRenderer() && !isHeaderRenderer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutResult layout(LayoutContext layoutContext) {
/*  261 */     Float blockMinHeight = retrieveMinHeight();
/*  262 */     Float blockMaxHeight = retrieveMaxHeight();
/*      */     
/*  264 */     LayoutArea area = layoutContext.getArea();
/*  265 */     boolean wasParentsHeightClipped = layoutContext.isClippedHeight();
/*  266 */     boolean wasHeightClipped = false;
/*  267 */     Rectangle layoutBox = area.getBBox().clone();
/*      */     
/*  269 */     Table tableModel = (Table)getModelElement();
/*  270 */     if (!tableModel.isComplete()) {
/*  271 */       setProperty(43, UnitValue.createPointValue(0.0F));
/*      */     }
/*  273 */     if (this.rowRange.getStartRow() != 0) {
/*  274 */       setProperty(46, UnitValue.createPointValue(0.0F));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  279 */     this.heights.clear();
/*  280 */     this.childRenderers.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  285 */     Map<Integer, Integer> rowMoves = new HashMap<>();
/*      */ 
/*      */ 
/*      */     
/*  289 */     int numberOfColumns = ((Table)getModelElement()).getNumberOfColumns();
/*      */ 
/*      */     
/*  292 */     List<Border> lastFlushedRowBottomBorder = tableModel.getLastRowBottomBorder();
/*  293 */     boolean isAndWasComplete = (tableModel.isComplete() && 0 == lastFlushedRowBottomBorder.size());
/*  294 */     boolean isFirstOnThePage = (0 == this.rowRange.getStartRow() || isFirstOnRootArea(true));
/*      */     
/*  296 */     if (!isFooterRenderer() && !isHeaderRenderer() && 
/*  297 */       this.isOriginalNonSplitRenderer) {
/*  298 */       boolean isSeparated = BorderCollapsePropertyValue.SEPARATE.equals(getProperty(114));
/*  299 */       if (isSeparated) {  } else {  }  this
/*      */         
/*  301 */         .bordersHandler = new CollapsedTableBorders(this.rows, numberOfColumns, getBorders(), !isAndWasComplete ? this.rowRange.getStartRow() : 0);
/*  302 */       this.bordersHandler.initializeBorders();
/*      */     } 
/*      */     
/*  305 */     this.bordersHandler.setRowRange(this.rowRange.getStartRow(), this.rowRange.getFinishRow());
/*  306 */     initializeHeaderAndFooter(isFirstOnThePage);
/*      */ 
/*      */     
/*  309 */     this.bordersHandler.updateBordersOnNewPage(this.isOriginalNonSplitRenderer, (isFooterRenderer() || isHeaderRenderer()), this, this.headerRenderer, this.footerRenderer);
/*  310 */     if (this.isOriginalNonSplitRenderer) {
/*  311 */       correctRowRange();
/*      */     }
/*      */     
/*  314 */     float horizontalBorderSpacing = (this.bordersHandler instanceof SeparatedTableBorders && null != getPropertyAsFloat(115)) ? getPropertyAsFloat(115).floatValue() : 0.0F;
/*      */ 
/*      */     
/*  317 */     float verticalBorderSpacing = (this.bordersHandler instanceof SeparatedTableBorders && null != getPropertyAsFloat(116)) ? getPropertyAsFloat(116).floatValue() : 0.0F;
/*      */     
/*  319 */     if (!isAndWasComplete && !isFirstOnThePage) {
/*  320 */       layoutBox.increaseHeight(verticalBorderSpacing);
/*      */     }
/*  322 */     if (isOriginalRenderer()) {
/*  323 */       applyMarginsAndPaddingsAndCalculateColumnWidths(layoutBox);
/*      */     }
/*  325 */     float tableWidth = getTableWidth();
/*      */     
/*  327 */     MarginsCollapseHandler marginsCollapseHandler = null;
/*  328 */     boolean marginsCollapsingEnabled = Boolean.TRUE.equals(getPropertyAsBoolean(89));
/*  329 */     if (marginsCollapsingEnabled) {
/*  330 */       marginsCollapseHandler = new MarginsCollapseHandler(this, layoutContext.getMarginsCollapseInfo());
/*      */     }
/*      */     
/*  333 */     List<Rectangle> siblingFloatRendererAreas = layoutContext.getFloatRendererAreas();
/*  334 */     float clearHeightCorrection = FloatingHelper.calculateClearHeightCorrection(this, siblingFloatRendererAreas, layoutBox);
/*  335 */     FloatPropertyValue floatPropertyValue = getProperty(99);
/*  336 */     if (FloatingHelper.isRendererFloating(this, floatPropertyValue)) {
/*  337 */       layoutBox.decreaseHeight(clearHeightCorrection);
/*  338 */       FloatingHelper.adjustFloatedTableLayoutBox(this, layoutBox, tableWidth, siblingFloatRendererAreas, floatPropertyValue);
/*      */     } else {
/*  340 */       clearHeightCorrection = FloatingHelper.adjustLayoutBoxAccordingToFloats(siblingFloatRendererAreas, layoutBox, Float.valueOf(tableWidth), clearHeightCorrection, marginsCollapseHandler);
/*      */     } 
/*      */     
/*  343 */     if (marginsCollapsingEnabled) {
/*  344 */       marginsCollapseHandler.startMarginsCollapse(layoutBox);
/*      */     }
/*  346 */     applyMargins(layoutBox, false);
/*  347 */     applyFixedXOrYPosition(true, layoutBox);
/*  348 */     applyPaddings(layoutBox, false);
/*      */     
/*  350 */     if (null != blockMaxHeight && blockMaxHeight.floatValue() <= layoutBox.getHeight() && 
/*  351 */       !Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
/*  352 */       layoutBox.moveUp(layoutBox.getHeight() - blockMaxHeight.floatValue()).setHeight(blockMaxHeight.floatValue());
/*  353 */       wasHeightClipped = true;
/*      */     } 
/*      */     
/*  356 */     initializeCaptionRenderer(getTable().getCaption());
/*  357 */     if (this.captionRenderer != null) {
/*  358 */       float minCaptionWidth = this.captionRenderer.getMinMaxWidth().getMinWidth();
/*  359 */       LayoutResult captionLayoutResult = this.captionRenderer.layout(new LayoutContext(new LayoutArea(area
/*  360 */               .getPageNumber(), new Rectangle(layoutBox.getX(), layoutBox.getY(), Math.max(tableWidth, minCaptionWidth), layoutBox.getHeight())), (wasHeightClipped || wasParentsHeightClipped)));
/*  361 */       if (1 != captionLayoutResult.getStatus()) {
/*  362 */         return new LayoutResult(3, null, null, this, captionLayoutResult.getCauseOfNothing());
/*      */       }
/*  364 */       float captionHeight = captionLayoutResult.getOccupiedArea().getBBox().getHeight();
/*  365 */       if (CaptionSide.BOTTOM.equals(tableModel.getCaption().getProperty(119))) {
/*  366 */         this.captionRenderer.move(0.0F, -(layoutBox.getHeight() - captionHeight));
/*  367 */         layoutBox.decreaseHeight(captionHeight);
/*  368 */         layoutBox.moveUp(captionHeight);
/*      */       } else {
/*  370 */         layoutBox.decreaseHeight(captionHeight);
/*      */       } 
/*      */     } 
/*      */     
/*  374 */     this.occupiedArea = new LayoutArea(area.getPageNumber(), new Rectangle(layoutBox.getX(), layoutBox.getY() + layoutBox.getHeight(), tableWidth, 0.0F));
/*      */     
/*  376 */     if (this.footerRenderer != null) {
/*      */       
/*  378 */       prepareFooterOrHeaderRendererForLayout(this.footerRenderer, layoutBox.getWidth());
/*      */ 
/*      */       
/*  381 */       if (0 != this.rows.size() || !isAndWasComplete) {
/*  382 */         this.bordersHandler.collapseTableWithFooter(this.footerRenderer.bordersHandler, false);
/*  383 */       } else if (null != this.headerRenderer) {
/*  384 */         this.headerRenderer.bordersHandler.collapseTableWithFooter(this.footerRenderer.bordersHandler, false);
/*      */       } 
/*      */       
/*  387 */       LayoutResult result = this.footerRenderer.layout(new LayoutContext(new LayoutArea(area.getPageNumber(), layoutBox), (wasHeightClipped || wasParentsHeightClipped)));
/*  388 */       if (result.getStatus() != 1) {
/*      */         
/*  390 */         deleteOwnProperty(10);
/*  391 */         return new LayoutResult(3, null, null, this, result.getCauseOfNothing());
/*      */       } 
/*  393 */       float footerHeight = result.getOccupiedArea().getBBox().getHeight();
/*  394 */       this.footerRenderer.move(0.0F, -(layoutBox.getHeight() - footerHeight));
/*  395 */       layoutBox.moveUp(footerHeight).decreaseHeight(footerHeight);
/*      */ 
/*      */       
/*  398 */       layoutBox.moveDown(verticalBorderSpacing).increaseHeight(verticalBorderSpacing);
/*  399 */       if (!tableModel.isEmpty()) {
/*  400 */         float maxFooterTopBorderWidth = this.footerRenderer.bordersHandler.getMaxTopWidth();
/*  401 */         this.footerRenderer.occupiedArea.getBBox().decreaseHeight(maxFooterTopBorderWidth);
/*  402 */         layoutBox.moveDown(maxFooterTopBorderWidth).increaseHeight(maxFooterTopBorderWidth);
/*      */       } 
/*      */ 
/*      */       
/*  406 */       if (Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
/*  407 */         this.footerRenderer.setProperty(26, Boolean.valueOf(true));
/*      */       }
/*      */     } 
/*      */     
/*  411 */     if (this.headerRenderer != null) {
/*  412 */       prepareFooterOrHeaderRendererForLayout(this.headerRenderer, layoutBox.getWidth());
/*  413 */       if (0 != this.rows.size()) {
/*  414 */         this.bordersHandler.collapseTableWithHeader(this.headerRenderer.bordersHandler, !tableModel.isEmpty());
/*  415 */       } else if (null != this.footerRenderer) {
/*  416 */         this.footerRenderer.bordersHandler.collapseTableWithHeader(this.headerRenderer.bordersHandler, true);
/*      */       } 
/*      */ 
/*      */       
/*  420 */       this.topBorderMaxWidth = this.bordersHandler.getMaxTopWidth();
/*  421 */       LayoutResult result = this.headerRenderer.layout(new LayoutContext(new LayoutArea(area.getPageNumber(), layoutBox), (wasHeightClipped || wasParentsHeightClipped)));
/*  422 */       if (result.getStatus() != 1) {
/*      */         
/*  424 */         deleteOwnProperty(13);
/*  425 */         return new LayoutResult(3, null, null, this, result.getCauseOfNothing());
/*      */       } 
/*  427 */       float headerHeight = result.getOccupiedArea().getBBox().getHeight();
/*  428 */       layoutBox.decreaseHeight(headerHeight);
/*  429 */       this.occupiedArea.getBBox().moveDown(headerHeight).increaseHeight(headerHeight);
/*  430 */       this.bordersHandler.fixHeaderOccupiedArea(this.occupiedArea.getBBox(), layoutBox);
/*      */ 
/*      */       
/*  433 */       layoutBox.increaseHeight(verticalBorderSpacing);
/*  434 */       this.occupiedArea.getBBox().moveUp(verticalBorderSpacing).decreaseHeight(verticalBorderSpacing);
/*      */     } 
/*      */ 
/*      */     
/*  438 */     applySpacing(layoutBox, horizontalBorderSpacing, verticalBorderSpacing, false);
/*  439 */     applySingleSpacing(this.occupiedArea.getBBox(), horizontalBorderSpacing, true, false);
/*  440 */     this.occupiedArea.getBBox().moveDown(verticalBorderSpacing / 2.0F);
/*      */     
/*  442 */     this.topBorderMaxWidth = this.bordersHandler.getMaxTopWidth();
/*  443 */     this.bordersHandler.applyLeftAndRightTableBorder(layoutBox, false);
/*      */     
/*  445 */     this.bordersHandler.applyTopTableBorder(this.occupiedArea.getBBox(), layoutBox, (tableModel
/*  446 */         .isEmpty() || 0 == this.rows.size()), isAndWasComplete, false);
/*  447 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/*  448 */       float bottomBorderWidth = this.bordersHandler.getMaxBottomWidth();
/*  449 */       layoutBox
/*  450 */         .moveUp(bottomBorderWidth)
/*  451 */         .decreaseHeight(bottomBorderWidth);
/*      */     } 
/*      */     
/*  454 */     LayoutResult[] splits = new LayoutResult[numberOfColumns];
/*      */ 
/*      */     
/*  457 */     int[] targetOverflowRowIndex = new int[numberOfColumns];
/*      */     
/*  459 */     List<Boolean> rowsHasCellWithSetHeight = new ArrayList<>();
/*      */     
/*  461 */     for (int row = 0; row < this.rows.size(); row++) {
/*  462 */       List<Rectangle> childFloatRendererAreas = new ArrayList<>();
/*      */ 
/*      */       
/*  465 */       if (row == 1 && Boolean.TRUE.equals(getProperty(26))) {
/*  466 */         if (Boolean.TRUE.equals(getOwnProperty(26))) {
/*  467 */           deleteOwnProperty(26);
/*      */         } else {
/*  469 */           setProperty(26, Boolean.valueOf(false));
/*      */         } 
/*      */       }
/*      */       
/*  473 */       CellRenderer[] currentRow = this.rows.get(row);
/*  474 */       float rowHeight = 0.0F;
/*  475 */       boolean split = false;
/*      */       
/*  477 */       boolean hasContent = true;
/*      */ 
/*      */ 
/*      */       
/*  481 */       boolean cellWithBigRowspanAdded = false;
/*  482 */       List<CellRenderer> currChildRenderers = new ArrayList<>();
/*      */       
/*  484 */       Deque<CellRendererInfo> cellProcessingQueue = new ArrayDeque<>(); int col;
/*  485 */       for (col = 0; col < currentRow.length; col++) {
/*  486 */         if (currentRow[col] != null) {
/*  487 */           cellProcessingQueue.addLast(new CellRendererInfo(currentRow[col], col, row));
/*      */         }
/*      */       } 
/*  490 */       boolean rowHasCellWithSetHeight = false;
/*      */       
/*  492 */       IRenderer firstCauseOfNothing = null;
/*      */ 
/*      */       
/*  495 */       this.bordersHandler.setFinishRow(this.rowRange.getStartRow() + row);
/*  496 */       Border widestRowBottomBorder = this.bordersHandler.getWidestHorizontalBorder(this.rowRange.getStartRow() + row + 1);
/*  497 */       this.bordersHandler.setFinishRow(this.rowRange.getFinishRow());
/*  498 */       float widestRowBottomBorderWidth = (null == widestRowBottomBorder) ? 0.0F : widestRowBottomBorder.getWidth();
/*      */ 
/*      */       
/*  501 */       while (cellProcessingQueue.size() > 0) {
/*  502 */         CellRendererInfo currentCellInfo = cellProcessingQueue.pop();
/*  503 */         col = currentCellInfo.column;
/*  504 */         CellRenderer cell = currentCellInfo.cellRenderer;
/*  505 */         int colspan = cell.getPropertyAsInteger(16).intValue();
/*  506 */         int rowspan = cell.getPropertyAsInteger(60).intValue();
/*  507 */         if (1 != rowspan) {
/*  508 */           cellWithBigRowspanAdded = true;
/*      */         }
/*  510 */         targetOverflowRowIndex[col] = currentCellInfo.finishRowInd;
/*      */         
/*  512 */         boolean currentCellHasBigRowspan = (row != currentCellInfo.finishRowInd);
/*  513 */         if (cell.hasOwnOrModelProperty(27)) {
/*  514 */           rowHasCellWithSetHeight = true;
/*      */         }
/*  516 */         float cellWidth = 0.0F, colOffset = 0.0F;
/*  517 */         for (int k = col; k < col + colspan; k++) {
/*  518 */           cellWidth += this.countedColumnWidth[k];
/*      */         }
/*  520 */         for (int l = 0; l < col; l++) {
/*  521 */           colOffset += this.countedColumnWidth[l];
/*      */         }
/*  523 */         float rowspanOffset = 0.0F;
/*  524 */         for (int m = row - 1; m > currentCellInfo.finishRowInd - rowspan && m >= 0; m--) {
/*  525 */           rowspanOffset += ((Float)this.heights.get(m)).floatValue();
/*      */         }
/*  527 */         float cellLayoutBoxHeight = rowspanOffset + ((!currentCellHasBigRowspan || hasContent) ? layoutBox.getHeight() : 0.0F);
/*  528 */         float cellLayoutBoxBottom = layoutBox.getY() + ((!currentCellHasBigRowspan || hasContent) ? 0.0F : layoutBox.getHeight());
/*  529 */         Rectangle cellLayoutBox = new Rectangle(layoutBox.getX() + colOffset, cellLayoutBoxBottom, cellWidth, cellLayoutBoxHeight);
/*  530 */         LayoutArea cellArea = new LayoutArea(layoutContext.getArea().getPageNumber(), cellLayoutBox);
/*  531 */         VerticalAlignment verticalAlignment = cell.<VerticalAlignment>getProperty(75);
/*  532 */         cell.setProperty(75, null);
/*  533 */         UnitValue cellWidthProperty = cell.<UnitValue>getProperty(77);
/*  534 */         if (cellWidthProperty != null && cellWidthProperty.isPercentValue()) {
/*  535 */           cell.setProperty(77, UnitValue.createPointValue(cellWidth));
/*      */         }
/*      */         
/*  538 */         float[] cellIndents = this.bordersHandler.getCellBorderIndents(currentCellInfo.finishRowInd, col, rowspan, colspan);
/*  539 */         if (!(this.bordersHandler instanceof SeparatedTableBorders)) {
/*  540 */           this.bordersHandler.applyCellIndents(cellArea.getBBox(), cellIndents[0], cellIndents[1], cellIndents[2] + widestRowBottomBorderWidth, cellIndents[3], false);
/*      */         }
/*      */         
/*  543 */         cellWidth = cellArea.getBBox().getWidth();
/*      */ 
/*      */         
/*  546 */         LayoutTaggingHelper taggingHelper = getProperty(108);
/*  547 */         if (taggingHelper != null) {
/*  548 */           taggingHelper.addKidsHint(this, Collections.singletonList(cell));
/*  549 */           LayoutTaggingHelper.addTreeHints(taggingHelper, cell);
/*      */         } 
/*      */         
/*  552 */         LayoutResult cellResult = cell.setParent(this).layout(new LayoutContext(cellArea, null, childFloatRendererAreas, (wasHeightClipped || wasParentsHeightClipped)));
/*      */         
/*  554 */         cell.setProperty(75, verticalAlignment);
/*      */         
/*  556 */         if (cellResult.getStatus() != 3) {
/*  557 */           cell.getOccupiedArea().getBBox().setWidth(cellWidth);
/*  558 */         } else if (null == firstCauseOfNothing) {
/*  559 */           firstCauseOfNothing = cellResult.getCauseOfNothing();
/*      */         } 
/*      */         
/*  562 */         if (currentCellHasBigRowspan) {
/*      */           
/*  564 */           if (cellResult.getStatus() != 1) {
/*  565 */             splits[col] = cellResult;
/*  566 */             if (cellResult.getStatus() != 3)
/*      */             {
/*  568 */               splits[col].getOverflowRenderer().setProperty(75, VerticalAlignment.TOP);
/*      */             }
/*      */           } 
/*  571 */           if (cellResult.getStatus() == 2) {
/*  572 */             currentRow[col] = (CellRenderer)cellResult.getSplitRenderer();
/*      */           } else {
/*  574 */             ((CellRenderer[])this.rows.get(currentCellInfo.finishRowInd))[col] = null;
/*  575 */             currentRow[col] = cell;
/*  576 */             rowMoves.put(Integer.valueOf(col), Integer.valueOf(currentCellInfo.finishRowInd));
/*      */           }
/*      */         
/*      */         }
/*  580 */         else if (cellResult.getStatus() != 1) {
/*      */           
/*  582 */           if (!split) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  588 */             boolean skipLastFooter = (null != this.footerRenderer && tableModel.isSkipLastFooter() && tableModel.isComplete() && !Boolean.TRUE.equals(getOwnProperty(26)));
/*  589 */             if (skipLastFooter) {
/*  590 */               LayoutArea potentialArea = new LayoutArea(area.getPageNumber(), layoutBox.clone());
/*  591 */               applySingleSpacing(potentialArea.getBBox(), horizontalBorderSpacing, true, true);
/*      */               
/*  593 */               Border widestRowTopBorder = this.bordersHandler.getWidestHorizontalBorder(this.rowRange.getStartRow() + row);
/*  594 */               if (this.bordersHandler instanceof CollapsedTableBorders && null != widestRowTopBorder) {
/*  595 */                 potentialArea.getBBox().increaseHeight(widestRowTopBorder.getWidth() / 2.0F);
/*      */               }
/*  597 */               if (null == this.headerRenderer) {
/*  598 */                 potentialArea.getBBox().increaseHeight(this.bordersHandler.getMaxTopWidth());
/*      */               }
/*  600 */               this.bordersHandler.applyLeftAndRightTableBorder(potentialArea.getBBox(), true);
/*  601 */               float footerHeight = this.footerRenderer.getOccupiedArea().getBBox().getHeight();
/*  602 */               potentialArea.getBBox().moveDown(footerHeight - verticalBorderSpacing / 2.0F).increaseHeight(footerHeight);
/*      */               
/*  604 */               TableRenderer overflowRenderer = createOverflowRenderer(new Table.RowRange(this.rowRange.getStartRow() + row, this.rowRange.getFinishRow()));
/*  605 */               overflowRenderer.rows = (List)this.rows.subList(row, this.rows.size());
/*  606 */               overflowRenderer.setProperty(97, Boolean.valueOf(true));
/*  607 */               overflowRenderer.setProperty(96, Boolean.valueOf(true));
/*  608 */               overflowRenderer.setProperty(46, UnitValue.createPointValue(0.0F));
/*  609 */               overflowRenderer.setProperty(43, UnitValue.createPointValue(0.0F));
/*  610 */               overflowRenderer.setProperty(44, UnitValue.createPointValue(0.0F));
/*  611 */               overflowRenderer.setProperty(45, UnitValue.createPointValue(0.0F));
/*      */               
/*  613 */               if (null != this.headerRenderer) {
/*  614 */                 overflowRenderer.setProperty(13, Border.NO_BORDER);
/*      */               }
/*  616 */               overflowRenderer.bordersHandler = this.bordersHandler;
/*      */               
/*  618 */               this.bordersHandler.skipFooter(overflowRenderer.getBorders());
/*  619 */               if (null != this.headerRenderer) {
/*  620 */                 this.bordersHandler.skipHeader(overflowRenderer.getBorders());
/*      */               }
/*  622 */               int savedStartRow = overflowRenderer.bordersHandler.startRow;
/*  623 */               overflowRenderer.bordersHandler.setStartRow(row);
/*  624 */               prepareFooterOrHeaderRendererForLayout(overflowRenderer, potentialArea.getBBox().getWidth());
/*  625 */               LayoutResult res = overflowRenderer.layout(new LayoutContext(potentialArea, (wasHeightClipped || wasParentsHeightClipped)));
/*  626 */               this.bordersHandler.setStartRow(savedStartRow);
/*  627 */               if (1 == res.getStatus()) {
/*  628 */                 if (taggingHelper != null)
/*      */                 {
/*  630 */                   taggingHelper.markArtifactHint(this.footerRenderer);
/*      */                 }
/*  632 */                 this.footerRenderer = null;
/*      */                 
/*  634 */                 layoutBox.increaseHeight(footerHeight).moveDown(footerHeight);
/*  635 */                 deleteOwnProperty(10);
/*      */                 
/*  637 */                 this.bordersHandler.setFinishRow(this.rowRange.getStartRow() + row);
/*  638 */                 widestRowBottomBorder = this.bordersHandler.getWidestHorizontalBorder(this.rowRange.getStartRow() + row + 1);
/*  639 */                 this.bordersHandler.setFinishRow(this.rowRange.getFinishRow());
/*  640 */                 widestRowBottomBorderWidth = (null == widestRowBottomBorder) ? 0.0F : widestRowBottomBorder.getWidth();
/*      */                 
/*  642 */                 cellProcessingQueue.clear();
/*  643 */                 currChildRenderers.clear();
/*  644 */                 for (int i = 0; i < currentRow.length; i++) {
/*  645 */                   if (currentRow[i] != null) {
/*  646 */                     cellProcessingQueue.addLast(new CellRendererInfo(currentRow[i], i, row));
/*      */                   }
/*      */                 } 
/*      */                 continue;
/*      */               } 
/*  651 */               if (null != this.headerRenderer) {
/*  652 */                 this.bordersHandler.collapseTableWithHeader(this.headerRenderer.bordersHandler, false);
/*      */               }
/*  654 */               this.bordersHandler.collapseTableWithFooter(this.footerRenderer.bordersHandler, false);
/*  655 */               this.bordersHandler.tableBoundingBorders[2] = Border.NO_BORDER;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  662 */             for (int addCol = 0; addCol < currentRow.length; addCol++) {
/*  663 */               if (currentRow[addCol] == null)
/*      */               {
/*  665 */                 for (int addRow = row + 1; addRow < this.rows.size(); addRow++) {
/*  666 */                   if (((CellRenderer[])this.rows.get(addRow))[addCol] != null) {
/*  667 */                     CellRenderer addRenderer = ((CellRenderer[])this.rows.get(addRow))[addCol];
/*  668 */                     if (row + addRenderer.getPropertyAsInteger(60).intValue() - 1 >= addRow) {
/*  669 */                       cellProcessingQueue.addLast(new CellRendererInfo(addRenderer, addCol, addRow));
/*      */                     }
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*  677 */           split = true;
/*  678 */           splits[col] = cellResult;
/*  679 */           if (cellResult.getStatus() == 3) {
/*  680 */             hasContent = false;
/*  681 */             splits[col].getOverflowRenderer().setProperty(75, verticalAlignment);
/*      */           } 
/*      */         } 
/*      */         
/*  685 */         currChildRenderers.add(cell);
/*  686 */         if (cellResult.getStatus() != 3) {
/*  687 */           rowHeight = Math.max(rowHeight, cellResult.getOccupiedArea().getBBox().getHeight() + this.bordersHandler.getCellVerticalAddition(cellIndents) - rowspanOffset);
/*      */         }
/*      */       } 
/*  690 */       if (hasContent) {
/*  691 */         this.heights.add(Float.valueOf(rowHeight));
/*  692 */         rowsHasCellWithSetHeight.add(Boolean.valueOf(rowHasCellWithSetHeight));
/*  693 */         this.occupiedArea.getBBox().moveDown(rowHeight);
/*  694 */         this.occupiedArea.getBBox().increaseHeight(rowHeight);
/*  695 */         layoutBox.decreaseHeight(rowHeight);
/*      */       } 
/*      */       
/*  698 */       if (split || row == this.rows.size() - 1) {
/*  699 */         this.bordersHandler.setFinishRow(this.bordersHandler.getStartRow() + row);
/*  700 */         if (!hasContent && this.bordersHandler.getFinishRow() != this.bordersHandler.getStartRow()) {
/*  701 */           this.bordersHandler.setFinishRow(this.bordersHandler.getFinishRow() - 1);
/*      */         }
/*  703 */         boolean skip = false;
/*  704 */         if (null != this.footerRenderer && tableModel.isComplete() && tableModel.isSkipLastFooter() && !split && 
/*  705 */           !Boolean.TRUE.equals(getOwnProperty(26))) {
/*  706 */           LayoutTaggingHelper taggingHelper = getProperty(108);
/*  707 */           if (taggingHelper != null)
/*      */           {
/*  709 */             taggingHelper.markArtifactHint(this.footerRenderer);
/*      */           }
/*  711 */           this.footerRenderer = null;
/*  712 */           if (tableModel.isEmpty()) {
/*  713 */             deleteOwnProperty(13);
/*      */           }
/*  715 */           skip = true;
/*      */         } 
/*      */         
/*  718 */         correctLayoutedCellsOccupiedAreas(splits, row, targetOverflowRowIndex, blockMinHeight, layoutBox, rowsHasCellWithSetHeight, !split, (!hasContent && cellWithBigRowspanAdded), skip);
/*      */       } 
/*      */       
/*  721 */       if ((split || row == this.rows.size() - 1) && null != this.footerRenderer) {
/*      */         
/*  723 */         if (!hasContent && this.childRenderers.size() == 0) {
/*  724 */           this.bordersHandler.applyTopTableBorder(this.occupiedArea.getBBox(), layoutBox, true);
/*      */         } else {
/*  726 */           this.bordersHandler.applyBottomTableBorder(this.occupiedArea.getBBox(), layoutBox, tableModel.isEmpty(), false, true);
/*      */         } 
/*  728 */         if (!(this.bordersHandler instanceof SeparatedTableBorders)) {
/*  729 */           layoutBox.moveDown(this.footerRenderer.occupiedArea.getBBox().getHeight()).increaseHeight(this.footerRenderer.occupiedArea.getBBox().getHeight());
/*      */           
/*  731 */           this.bordersHandler.applyLeftAndRightTableBorder(layoutBox, true);
/*  732 */           prepareFooterOrHeaderRendererForLayout(this.footerRenderer, layoutBox.getWidth());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  738 */           if (this.bordersHandler instanceof CollapsedTableBorders) {
/*  739 */             ((CollapsedTableBorders)this.bordersHandler).setBottomBorderCollapseWith(null);
/*      */           }
/*  741 */           this.bordersHandler.collapseTableWithFooter(this.footerRenderer.bordersHandler, (hasContent || 0 != this.childRenderers.size()));
/*      */           
/*  743 */           if (this.bordersHandler instanceof CollapsedTableBorders) {
/*  744 */             this.footerRenderer.setBorders(CollapsedTableBorders.getCollapsedBorder(this.footerRenderer.getBorders()[2], getBorders()[2]), 2);
/*      */           }
/*  746 */           this.footerRenderer.layout(new LayoutContext(new LayoutArea(area.getPageNumber(), layoutBox), (wasHeightClipped || wasParentsHeightClipped)));
/*  747 */           this.bordersHandler.applyLeftAndRightTableBorder(layoutBox, false);
/*  748 */           float footerHeight = this.footerRenderer.getOccupiedAreaBBox().getHeight();
/*  749 */           this.footerRenderer.move(0.0F, -(layoutBox.getHeight() - footerHeight));
/*  750 */           layoutBox.setY(this.footerRenderer.occupiedArea.getBBox().getTop()).setHeight(this.occupiedArea.getBBox().getBottom() - layoutBox.getBottom());
/*      */         } 
/*      */       } 
/*  753 */       if (!split) {
/*  754 */         this.childRenderers.addAll((Collection)currChildRenderers);
/*  755 */         currChildRenderers.clear();
/*      */       } 
/*  757 */       if (split && this.footerRenderer != null) {
/*  758 */         LayoutTaggingHelper taggingHelper = getProperty(108);
/*  759 */         if (taggingHelper != null) {
/*  760 */           taggingHelper.markArtifactHint(this.footerRenderer);
/*      */         }
/*      */       } 
/*  763 */       if (split) {
/*  764 */         if (marginsCollapsingEnabled) {
/*  765 */           marginsCollapseHandler.endMarginsCollapse(layoutBox);
/*      */         }
/*  767 */         TableRenderer[] splitResult = split(row, hasContent, cellWithBigRowspanAdded);
/*  768 */         OverflowRowsWrapper overflowRows = new OverflowRowsWrapper(splitResult[1]);
/*      */         
/*  770 */         if (null != this.headerRenderer || null != this.footerRenderer) {
/*  771 */           if (null != this.headerRenderer || tableModel.isEmpty()) {
/*  772 */             splitResult[1].deleteOwnProperty(13);
/*      */           }
/*  774 */           if (null != this.footerRenderer || tableModel.isEmpty()) {
/*  775 */             splitResult[1].deleteOwnProperty(10);
/*      */           }
/*      */         } 
/*  778 */         if (split) {
/*  779 */           int[] rowspans = new int[currentRow.length];
/*  780 */           boolean[] columnsWithCellToBeEnlarged = new boolean[currentRow.length];
/*  781 */           for (col = 0; col < currentRow.length; col++) {
/*  782 */             if (splits[col] != null) {
/*  783 */               CellRenderer cellSplit = (CellRenderer)splits[col].getSplitRenderer();
/*  784 */               if (null != cellSplit) {
/*  785 */                 rowspans[col] = ((Cell)cellSplit.getModelElement()).getRowspan();
/*      */               }
/*  787 */               if (splits[col].getStatus() != 3 && (hasContent || cellWithBigRowspanAdded)) {
/*  788 */                 this.childRenderers.add(cellSplit);
/*      */               }
/*  790 */               LayoutArea cellOccupiedArea = currentRow[col].getOccupiedArea();
/*  791 */               if (hasContent || cellWithBigRowspanAdded || splits[col].getStatus() == 3) {
/*  792 */                 CellRenderer cellOverflow = (CellRenderer)splits[col].getOverflowRenderer();
/*  793 */                 CellRenderer originalCell = currentRow[col];
/*  794 */                 currentRow[col] = null;
/*  795 */                 ((CellRenderer[])this.rows.get(targetOverflowRowIndex[col]))[col] = originalCell;
/*  796 */                 overflowRows.setCell(0, col, null);
/*  797 */                 overflowRows.setCell(targetOverflowRowIndex[col] - row, col, (CellRenderer)cellOverflow.setParent(splitResult[1]));
/*      */               } else {
/*  799 */                 overflowRows.setCell(targetOverflowRowIndex[col] - row, col, (CellRenderer)currentRow[col].setParent(splitResult[1]));
/*      */               } 
/*  801 */               (overflowRows.getCell(targetOverflowRowIndex[col] - row, col)).occupiedArea = cellOccupiedArea;
/*  802 */             } else if (currentRow[col] != null) {
/*  803 */               if (hasContent) {
/*  804 */                 rowspans[col] = ((Cell)currentRow[col].getModelElement()).getRowspan();
/*      */               }
/*  806 */               boolean isBigRowspannedCell = (1 != ((Cell)currentRow[col].getModelElement()).getRowspan());
/*  807 */               if (hasContent || isBigRowspannedCell) {
/*  808 */                 columnsWithCellToBeEnlarged[col] = true;
/*      */               }
/*      */             } 
/*      */           } 
/*      */           
/*  813 */           int minRowspan = Integer.MAX_VALUE;
/*  814 */           for (col = 0; col < rowspans.length; col++) {
/*  815 */             if (0 != rowspans[col]) {
/*  816 */               minRowspan = Math.min(minRowspan, rowspans[col]);
/*      */             }
/*      */           } 
/*      */           
/*  820 */           for (col = 0; col < numberOfColumns; col++) {
/*  821 */             if (columnsWithCellToBeEnlarged[col]) {
/*  822 */               enlargeCell(col, row, minRowspan, currentRow, overflowRows, targetOverflowRowIndex, splitResult);
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  827 */         applySpacing(layoutBox, horizontalBorderSpacing, verticalBorderSpacing, true);
/*  828 */         applySingleSpacing(this.occupiedArea.getBBox(), horizontalBorderSpacing, true, true);
/*  829 */         if (null != this.footerRenderer) {
/*  830 */           layoutBox.moveUp(verticalBorderSpacing).decreaseHeight(verticalBorderSpacing);
/*      */         }
/*  832 */         if (null != this.headerRenderer || !tableModel.isEmpty()) {
/*  833 */           layoutBox.decreaseHeight(verticalBorderSpacing);
/*      */         }
/*  835 */         if (0 == row && !hasContent && null == this.headerRenderer) {
/*  836 */           this.occupiedArea.getBBox().moveUp(verticalBorderSpacing / 2.0F);
/*      */         } else {
/*  838 */           applySingleSpacing(this.occupiedArea.getBBox(), verticalBorderSpacing, false, true);
/*      */         } 
/*      */         
/*  841 */         if (!isAndWasComplete && null != this.footerRenderer && 0 == (splitResult[0]).rows.size()) {
/*  842 */           layoutBox.increaseHeight(verticalBorderSpacing);
/*      */         }
/*      */         
/*  845 */         if (null == this.footerRenderer) {
/*  846 */           if (0 != this.childRenderers.size()) {
/*  847 */             this.bordersHandler.applyBottomTableBorder(this.occupiedArea.getBBox(), layoutBox, false);
/*      */           } else {
/*  849 */             this.bordersHandler.applyTopTableBorder(this.occupiedArea.getBBox(), layoutBox, true);
/*      */             
/*  851 */             if (!isAndWasComplete && !isFirstOnThePage) {
/*  852 */               this.bordersHandler.applyTopTableBorder(this.occupiedArea.getBBox(), layoutBox, (0 == this.childRenderers.size()), true, false);
/*      */             }
/*      */           } 
/*      */         }
/*  856 */         if (Boolean.TRUE.equals(getPropertyAsBoolean(86)) || Boolean.TRUE
/*  857 */           .equals(getPropertyAsBoolean(87))) {
/*  858 */           extendLastRow((splitResult[1]).rows.get(0), layoutBox);
/*      */         }
/*  860 */         adjustFooterAndFixOccupiedArea(layoutBox, (0 != this.heights.size()) ? verticalBorderSpacing : 0.0F);
/*  861 */         adjustCaptionAndFixOccupiedArea(layoutBox, (0 != this.heights.size()) ? verticalBorderSpacing : 0.0F);
/*      */ 
/*      */         
/*  864 */         for (Map.Entry<Integer, Integer> entry : rowMoves.entrySet()) {
/*      */           
/*  866 */           if (null == ((CellRenderer[])(splitResult[1]).rows.get(((Integer)entry.getValue()).intValue() - (splitResult[0]).rows.size()))[((Integer)entry.getKey()).intValue()]) {
/*  867 */             CellRenderer originalCellRenderer = ((CellRenderer[])this.rows.get(row))[((Integer)entry.getKey()).intValue()];
/*  868 */             CellRenderer overflowCellRenderer = ((CellRenderer[])(splitResult[1]).rows.get(row - (splitResult[0]).rows.size()))[((Integer)entry.getKey()).intValue()];
/*  869 */             ((CellRenderer[])this.rows.get(((Integer)entry.getValue()).intValue()))[((Integer)entry.getKey()).intValue()] = originalCellRenderer;
/*  870 */             ((CellRenderer[])this.rows.get(row))[((Integer)entry.getKey()).intValue()] = null;
/*  871 */             overflowRows.setCell(((Integer)entry.getValue()).intValue() - (splitResult[0]).rows.size(), ((Integer)entry.getKey()).intValue(), overflowCellRenderer);
/*  872 */             overflowRows.setCell(row - (splitResult[0]).rows.size(), ((Integer)entry.getKey()).intValue(), null);
/*      */           } 
/*      */         } 
/*      */         
/*  876 */         if (isKeepTogether() && 0 == lastFlushedRowBottomBorder.size() && !Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
/*  877 */           return new LayoutResult(3, null, null, this, (null == firstCauseOfNothing) ? this : firstCauseOfNothing);
/*      */         }
/*      */ 
/*      */         
/*  881 */         int status = (this.occupiedArea.getBBox().getHeight() - ((null == this.footerRenderer) ? 0.0F : this.footerRenderer.getOccupiedArea().getBBox().getHeight()) - ((null == this.headerRenderer) ? 0.0F : (this.headerRenderer.getOccupiedArea().getBBox().getHeight() - this.headerRenderer.bordersHandler.getMaxBottomWidth())) == 0.0F && (isAndWasComplete || isFirstOnThePage)) ? 3 : 2;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  886 */         if ((status == 3 && Boolean.TRUE.equals(getPropertyAsBoolean(26))) || wasHeightClipped) {
/*      */           
/*  888 */           if (wasHeightClipped) {
/*  889 */             Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/*  890 */             logger.warn("Element content was clipped because some height properties are set.");
/*      */             
/*  892 */             if (status == 3) {
/*  893 */               this.bordersHandler.applyTopTableBorder(this.occupiedArea.getBBox(), layoutBox, (0 == this.childRenderers.size()), true, false);
/*  894 */               this.bordersHandler.applyBottomTableBorder(this.occupiedArea.getBBox(), layoutBox, (0 == this.childRenderers.size()), true, false);
/*      */             } 
/*      */             
/*  897 */             if (null != blockMinHeight && blockMinHeight.floatValue() > this.occupiedArea.getBBox().getHeight()) {
/*  898 */               float blockBottom = Math.max(this.occupiedArea.getBBox().getBottom() - blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight(), layoutBox.getBottom());
/*  899 */               if (0 == this.heights.size()) {
/*  900 */                 this.heights.add(Float.valueOf(blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight() / 2.0F));
/*      */               } else {
/*  902 */                 this.heights.set(this.heights.size() - 1, Float.valueOf(((Float)this.heights.get(this.heights.size() - 1)).floatValue() + blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight()));
/*      */               } 
/*  904 */               this.occupiedArea.getBBox()
/*  905 */                 .increaseHeight(this.occupiedArea.getBBox().getBottom() - blockBottom)
/*  906 */                 .setY(blockBottom);
/*      */             } 
/*      */           } 
/*  909 */           applyFixedXOrYPosition(false, layoutBox);
/*  910 */           applyPaddings(this.occupiedArea.getBBox(), true);
/*  911 */           applyMargins(this.occupiedArea.getBBox(), true);
/*      */           
/*  913 */           LayoutArea layoutArea1 = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, siblingFloatRendererAreas, layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*  914 */           return new LayoutResult(1, layoutArea1, splitResult[0], null);
/*      */         } 
/*  916 */         updateHeightsOnSplit(false, splitResult[0], splitResult[1]);
/*  917 */         applyFixedXOrYPosition(false, layoutBox);
/*  918 */         applyPaddings(this.occupiedArea.getBBox(), true);
/*  919 */         applyMargins(this.occupiedArea.getBBox(), true);
/*      */         
/*  921 */         LayoutArea layoutArea = null;
/*  922 */         if (status != 3) {
/*  923 */           layoutArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, siblingFloatRendererAreas, layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*      */         }
/*  925 */         return new LayoutResult(status, layoutArea, splitResult[0], splitResult[1], (null == firstCauseOfNothing) ? this : firstCauseOfNothing);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  931 */     if (tableModel.isComplete() && !tableModel.isEmpty()) {
/*  932 */       CellRenderer[] lastRow = this.rows.get(this.rows.size() - 1);
/*  933 */       int lastInRow = lastRow.length - 1;
/*  934 */       while (lastInRow >= 0 && null == lastRow[lastInRow]) {
/*  935 */         lastInRow--;
/*      */       }
/*  937 */       if (lastInRow < 0 || lastRow.length != lastInRow + lastRow[lastInRow].getPropertyAsInteger(16).intValue()) {
/*  938 */         Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/*  939 */         logger.warn("Last row is not completed. Table bottom border may collapse as you do not expect it");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  944 */     if (!(this.bordersHandler instanceof SeparatedTableBorders) && tableModel.isComplete() && (0 != lastFlushedRowBottomBorder.size() || tableModel.isEmpty()) && null != this.footerRenderer) {
/*  945 */       layoutBox.moveDown(this.footerRenderer.occupiedArea.getBBox().getHeight()).increaseHeight(this.footerRenderer.occupiedArea.getBBox().getHeight());
/*      */       
/*  947 */       this.bordersHandler.applyLeftAndRightTableBorder(layoutBox, true);
/*  948 */       prepareFooterOrHeaderRendererForLayout(this.footerRenderer, layoutBox.getWidth());
/*  949 */       if (0 != this.rows.size() || !isAndWasComplete) {
/*  950 */         this.bordersHandler.collapseTableWithFooter(this.footerRenderer.bordersHandler, true);
/*  951 */       } else if (null != this.headerRenderer) {
/*  952 */         this.headerRenderer.bordersHandler.collapseTableWithFooter(this.footerRenderer.bordersHandler, true);
/*      */       } 
/*      */       
/*  955 */       this.footerRenderer.layout(new LayoutContext(new LayoutArea(area.getPageNumber(), layoutBox), (wasHeightClipped || wasParentsHeightClipped)));
/*  956 */       this.bordersHandler.applyLeftAndRightTableBorder(layoutBox, false);
/*      */       
/*  958 */       float footerHeight = this.footerRenderer.getOccupiedAreaBBox().getHeight();
/*  959 */       this.footerRenderer.move(0.0F, -(layoutBox.getHeight() - footerHeight));
/*  960 */       layoutBox.moveUp(footerHeight).decreaseHeight(footerHeight);
/*      */     } 
/*      */     
/*  963 */     applySpacing(layoutBox, horizontalBorderSpacing, verticalBorderSpacing, true);
/*  964 */     applySingleSpacing(this.occupiedArea.getBBox(), horizontalBorderSpacing, true, true);
/*  965 */     if (null != this.footerRenderer) {
/*  966 */       layoutBox.moveUp(verticalBorderSpacing).decreaseHeight(verticalBorderSpacing);
/*      */     }
/*  968 */     if (null != this.headerRenderer || !tableModel.isEmpty()) {
/*  969 */       layoutBox.decreaseHeight(verticalBorderSpacing);
/*      */     }
/*  971 */     if (tableModel.isEmpty() && null == this.headerRenderer) {
/*  972 */       this.occupiedArea.getBBox().moveUp(verticalBorderSpacing / 2.0F);
/*  973 */     } else if (isAndWasComplete || 0 != this.rows.size()) {
/*  974 */       applySingleSpacing(this.occupiedArea.getBBox(), verticalBorderSpacing, false, true);
/*      */     } 
/*      */     
/*  977 */     float bottomTableBorderWidth = this.bordersHandler.getMaxBottomWidth();
/*      */     
/*  979 */     if (tableModel.isComplete()) {
/*  980 */       if (null == this.footerRenderer) {
/*  981 */         if (0 != this.childRenderers.size()) {
/*  982 */           this.bordersHandler.applyBottomTableBorder(this.occupiedArea.getBBox(), layoutBox, false);
/*      */         }
/*  984 */         else if (0 != lastFlushedRowBottomBorder.size()) {
/*  985 */           this.bordersHandler.applyTopTableBorder(this.occupiedArea.getBBox(), layoutBox, (0 == this.childRenderers.size()), true, false);
/*      */         } else {
/*  987 */           this.bordersHandler.applyBottomTableBorder(this.occupiedArea.getBBox(), layoutBox, (0 == this.childRenderers.size()), true, false);
/*      */         }
/*      */       
/*      */       }
/*  991 */       else if (tableModel.isEmpty() && null != this.headerRenderer) {
/*  992 */         float headerBottomBorderWidth = this.headerRenderer.bordersHandler.getMaxBottomWidth();
/*  993 */         this.headerRenderer.bordersHandler.applyBottomTableBorder(this.headerRenderer.occupiedArea.getBBox(), layoutBox, true, true, true);
/*  994 */         this.occupiedArea.getBBox().moveUp(headerBottomBorderWidth).decreaseHeight(headerBottomBorderWidth);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  999 */       if (0 != this.heights.size()) {
/* 1000 */         this.heights.set(this.heights.size() - 1, Float.valueOf(((Float)this.heights.get(this.heights.size() - 1)).floatValue() - bottomTableBorderWidth / 2.0F));
/*      */       }
/* 1002 */       if (null == this.footerRenderer) {
/* 1003 */         if (0 != this.childRenderers.size()) {
/* 1004 */           this.bordersHandler.applyBottomTableBorder(this.occupiedArea.getBBox(), layoutBox, (0 == this.childRenderers.size()), false, true);
/*      */         }
/*      */       } else {
/*      */         
/* 1008 */         layoutBox.increaseHeight(bottomTableBorderWidth);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1013 */     if (0 != this.rows.size()) {
/* 1014 */       if (Boolean.TRUE.equals(getPropertyAsBoolean(86))) {
/* 1015 */         extendLastRow(this.rows.get(this.rows.size() - 1), layoutBox);
/*      */       }
/*      */     }
/* 1018 */     else if (null != blockMinHeight && blockMinHeight.floatValue() > this.occupiedArea.getBBox().getHeight()) {
/* 1019 */       float blockBottom = Math.max(this.occupiedArea.getBBox().getBottom() - blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight(), layoutBox.getBottom());
/* 1020 */       if (0 != this.heights.size()) {
/* 1021 */         this.heights.set(this.heights.size() - 1, Float.valueOf(((Float)this.heights.get(this.heights.size() - 1)).floatValue() + this.occupiedArea.getBBox().getBottom() - blockBottom));
/*      */       } else {
/* 1023 */         this.heights.add(Float.valueOf(this.occupiedArea.getBBox().getBottom() - blockBottom + this.occupiedArea.getBBox().getHeight() / 2.0F));
/*      */       } 
/*      */       
/* 1026 */       this.occupiedArea.getBBox()
/* 1027 */         .increaseHeight(this.occupiedArea.getBBox().getBottom() - blockBottom)
/* 1028 */         .setY(blockBottom);
/*      */     } 
/*      */ 
/*      */     
/* 1032 */     applyFixedXOrYPosition(false, layoutBox);
/*      */     
/* 1034 */     if (marginsCollapsingEnabled) {
/* 1035 */       marginsCollapseHandler.endMarginsCollapse(layoutBox);
/*      */     }
/*      */     
/* 1038 */     applyPaddings(this.occupiedArea.getBBox(), true);
/* 1039 */     applyMargins(this.occupiedArea.getBBox(), true);
/*      */ 
/*      */     
/* 1042 */     if (!tableModel.isComplete() && null != this.footerRenderer) {
/* 1043 */       LayoutTaggingHelper taggingHelper = getProperty(108);
/* 1044 */       if (taggingHelper != null)
/*      */       {
/* 1046 */         taggingHelper.markArtifactHint(this.footerRenderer);
/*      */       }
/* 1048 */       this.footerRenderer = null;
/* 1049 */       this.bordersHandler.skipFooter(this.bordersHandler.tableBoundingBorders);
/*      */     } 
/* 1051 */     adjustFooterAndFixOccupiedArea(layoutBox, (null != this.headerRenderer || !tableModel.isEmpty()) ? verticalBorderSpacing : 0.0F);
/* 1052 */     adjustCaptionAndFixOccupiedArea(layoutBox, (null != this.headerRenderer || !tableModel.isEmpty()) ? verticalBorderSpacing : 0.0F);
/*      */     
/* 1054 */     FloatingHelper.removeFloatsAboveRendererBottom(siblingFloatRendererAreas, this);
/*      */     
/* 1056 */     if (!isAndWasComplete && !isFirstOnThePage && (0 != this.rows.size() || (null != this.footerRenderer && tableModel.isComplete()))) {
/* 1057 */       this.occupiedArea.getBBox().decreaseHeight(verticalBorderSpacing);
/*      */     }
/*      */     
/* 1060 */     LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, siblingFloatRendererAreas, layoutContext.getArea().getBBox(), clearHeightCorrection, marginsCollapsingEnabled);
/*      */     
/* 1062 */     return new LayoutResult(1, editedArea, null, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(DrawContext drawContext) {
/* 1070 */     boolean isTagged = drawContext.isTaggingEnabled();
/* 1071 */     LayoutTaggingHelper taggingHelper = null;
/* 1072 */     if (isTagged) {
/* 1073 */       taggingHelper = getProperty(108);
/* 1074 */       if (taggingHelper == null) {
/* 1075 */         isTagged = false;
/*      */       } else {
/* 1077 */         TagTreePointer tagPointer = taggingHelper.useAutoTaggingPointerAndRememberItsPosition(this);
/* 1078 */         if (taggingHelper.createTag(this, tagPointer)) {
/* 1079 */           tagPointer.getProperties().addAttributes(0, AccessibleAttributesApplier.getLayoutAttributes(this, tagPointer));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1084 */     beginTransformationIfApplied(drawContext.getCanvas());
/*      */     
/* 1086 */     applyDestinationsAndAnnotation(drawContext);
/*      */     
/* 1088 */     boolean relativePosition = isRelativePosition();
/* 1089 */     if (relativePosition) {
/* 1090 */       applyRelativePositioningTranslation(false);
/*      */     }
/*      */     
/* 1093 */     beginElementOpacityApplying(drawContext);
/* 1094 */     float captionHeight = (null != this.captionRenderer) ? this.captionRenderer.getOccupiedArea().getBBox().getHeight() : 0.0F;
/* 1095 */     boolean isBottomCaption = CaptionSide.BOTTOM.equals((0.0F != captionHeight) ? this.captionRenderer.<CaptionSide>getProperty(119) : null);
/* 1096 */     if (0.0F != captionHeight) {
/* 1097 */       this.occupiedArea.getBBox().applyMargins(isBottomCaption ? 0.0F : captionHeight, 0.0F, isBottomCaption ? captionHeight : 0.0F, 0.0F, false);
/*      */     }
/* 1099 */     drawBackground(drawContext);
/* 1100 */     if (this.bordersHandler instanceof SeparatedTableBorders && !isHeaderRenderer() && !isFooterRenderer()) {
/* 1101 */       drawBorder(drawContext);
/*      */     }
/* 1103 */     drawChildren(drawContext);
/* 1104 */     drawPositionedChildren(drawContext);
/* 1105 */     if (0.0F != captionHeight) {
/* 1106 */       this.occupiedArea.getBBox().applyMargins(isBottomCaption ? 0.0F : captionHeight, 0.0F, isBottomCaption ? captionHeight : 0.0F, 0.0F, true);
/*      */     }
/* 1108 */     drawCaption(drawContext);
/* 1109 */     endElementOpacityApplying(drawContext);
/*      */     
/* 1111 */     if (relativePosition) {
/* 1112 */       applyRelativePositioningTranslation(true);
/*      */     }
/*      */     
/* 1115 */     this.flushed = true;
/*      */     
/* 1117 */     endTransformationIfApplied(drawContext.getCanvas());
/*      */     
/* 1119 */     if (isTagged) {
/* 1120 */       if (this.isLastRendererForModelElement && ((Table)getModelElement()).isComplete()) {
/* 1121 */         taggingHelper.finishTaggingHint(this);
/*      */       }
/* 1123 */       taggingHelper.restoreAutoTaggingPointerPosition(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawChildren(DrawContext drawContext) {
/* 1132 */     if (this.headerRenderer != null) {
/* 1133 */       this.headerRenderer.draw(drawContext);
/*      */     }
/*      */     
/* 1136 */     for (IRenderer child : this.childRenderers) {
/* 1137 */       child.draw(drawContext);
/*      */     }
/*      */     
/* 1140 */     if (this.bordersHandler instanceof CollapsedTableBorders) {
/* 1141 */       drawBorders(drawContext);
/*      */     }
/*      */     
/* 1144 */     if (this.footerRenderer != null) {
/* 1145 */       this.footerRenderer.draw(drawContext);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawBackgrounds(DrawContext drawContext) {
/* 1150 */     boolean shrinkBackgroundArea = (this.bordersHandler instanceof CollapsedTableBorders && (isHeaderRenderer() || isFooterRenderer()));
/* 1151 */     if (shrinkBackgroundArea) {
/* 1152 */       this.occupiedArea.getBBox().applyMargins(this.bordersHandler.getMaxTopWidth() / 2.0F, this.bordersHandler.getRightBorderMaxWidth() / 2.0F, this.bordersHandler
/* 1153 */           .getMaxBottomWidth() / 2.0F, this.bordersHandler.getLeftBorderMaxWidth() / 2.0F, false);
/*      */     }
/* 1155 */     super.drawBackground(drawContext);
/* 1156 */     if (shrinkBackgroundArea) {
/* 1157 */       this.occupiedArea.getBBox().applyMargins(this.bordersHandler.getMaxTopWidth() / 2.0F, this.bordersHandler.getRightBorderMaxWidth() / 2.0F, this.bordersHandler
/* 1158 */           .getMaxBottomWidth() / 2.0F, this.bordersHandler.getLeftBorderMaxWidth() / 2.0F, true);
/*      */     }
/* 1160 */     if (null != this.headerRenderer) {
/* 1161 */       this.headerRenderer.drawBackgrounds(drawContext);
/*      */     }
/* 1163 */     if (null != this.footerRenderer) {
/* 1164 */       this.footerRenderer.drawBackgrounds(drawContext);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawCaption(DrawContext drawContext) {
/* 1170 */     if (null != this.captionRenderer && !isFooterRenderer() && !isHeaderRenderer()) {
/* 1171 */       this.captionRenderer.draw(drawContext);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawBackground(DrawContext drawContext) {
/* 1178 */     if (!isFooterRenderer() && !isHeaderRenderer()) {
/* 1179 */       drawBackgrounds(drawContext);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IRenderer getNextRenderer() {
/* 1188 */     TableRenderer nextTable = new TableRenderer();
/* 1189 */     nextTable.modelElement = this.modelElement;
/* 1190 */     return nextTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void move(float dxRight, float dyUp) {
/* 1198 */     super.move(dxRight, dyUp);
/* 1199 */     if (this.headerRenderer != null) {
/* 1200 */       this.headerRenderer.move(dxRight, dyUp);
/*      */     }
/* 1202 */     if (this.footerRenderer != null) {
/* 1203 */       this.footerRenderer.move(dxRight, dyUp);
/*      */     }
/*      */   }
/*      */   
/*      */   protected TableRenderer[] split(int row) {
/* 1208 */     return split(row, false);
/*      */   }
/*      */   
/*      */   protected TableRenderer[] split(int row, boolean hasContent) {
/* 1212 */     return split(row, hasContent, false);
/*      */   }
/*      */ 
/*      */   
/*      */   protected TableRenderer[] split(int row, boolean hasContent, boolean cellWithBigRowspanAdded) {
/* 1217 */     TableRenderer splitRenderer = createSplitRenderer(new Table.RowRange(this.rowRange.getStartRow(), this.rowRange.getStartRow() + row));
/* 1218 */     splitRenderer.rows = (List)this.rows.subList(0, row);
/*      */     
/* 1220 */     splitRenderer.bordersHandler = this.bordersHandler;
/*      */     
/* 1222 */     splitRenderer.heights = this.heights;
/* 1223 */     splitRenderer.columnWidths = this.columnWidths;
/* 1224 */     splitRenderer.countedColumnWidth = this.countedColumnWidth;
/* 1225 */     splitRenderer.totalWidthForColumns = this.totalWidthForColumns;
/* 1226 */     TableRenderer overflowRenderer = createOverflowRenderer(new Table.RowRange(this.rowRange.getStartRow() + row, this.rowRange.getFinishRow()));
/* 1227 */     if (0 == row && !hasContent && !cellWithBigRowspanAdded && 0 == this.rowRange.getStartRow()) {
/* 1228 */       overflowRenderer.isOriginalNonSplitRenderer = this.isOriginalNonSplitRenderer;
/*      */     }
/* 1230 */     overflowRenderer.rows = (List)this.rows.subList(row, this.rows.size());
/* 1231 */     splitRenderer.occupiedArea = this.occupiedArea;
/*      */     
/* 1233 */     overflowRenderer.bordersHandler = this.bordersHandler;
/*      */     
/* 1235 */     return new TableRenderer[] { splitRenderer, overflowRenderer };
/*      */   }
/*      */   
/*      */   protected TableRenderer createSplitRenderer(Table.RowRange rowRange) {
/* 1239 */     TableRenderer splitRenderer = (TableRenderer)getNextRenderer();
/* 1240 */     splitRenderer.rowRange = rowRange;
/* 1241 */     splitRenderer.parent = this.parent;
/* 1242 */     splitRenderer.modelElement = this.modelElement;
/* 1243 */     splitRenderer.childRenderers = this.childRenderers;
/* 1244 */     splitRenderer.addAllProperties(getOwnProperties());
/* 1245 */     splitRenderer.headerRenderer = this.headerRenderer;
/* 1246 */     splitRenderer.footerRenderer = this.footerRenderer;
/* 1247 */     splitRenderer.isLastRendererForModelElement = false;
/* 1248 */     splitRenderer.topBorderMaxWidth = this.topBorderMaxWidth;
/* 1249 */     splitRenderer.captionRenderer = this.captionRenderer;
/* 1250 */     splitRenderer.isOriginalNonSplitRenderer = this.isOriginalNonSplitRenderer;
/*      */     
/* 1252 */     return splitRenderer;
/*      */   }
/*      */   
/*      */   protected TableRenderer createOverflowRenderer(Table.RowRange rowRange) {
/* 1256 */     TableRenderer overflowRenderer = (TableRenderer)getNextRenderer();
/* 1257 */     overflowRenderer.setRowRange(rowRange);
/* 1258 */     overflowRenderer.parent = this.parent;
/* 1259 */     overflowRenderer.modelElement = this.modelElement;
/* 1260 */     overflowRenderer.addAllProperties(getOwnProperties());
/* 1261 */     overflowRenderer.isOriginalNonSplitRenderer = false;
/* 1262 */     overflowRenderer.countedColumnWidth = this.countedColumnWidth;
/* 1263 */     return overflowRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Float retrieveWidth(float parentBoxWidth) {
/* 1268 */     Float tableWidth = super.retrieveWidth(parentBoxWidth);
/* 1269 */     Table tableModel = (Table)getModelElement();
/* 1270 */     if (tableWidth == null || tableWidth.floatValue() == 0.0F) {
/* 1271 */       float totalColumnWidthInPercent = 0.0F;
/* 1272 */       for (int col = 0; col < tableModel.getNumberOfColumns(); col++) {
/* 1273 */         UnitValue columnWidth = tableModel.getColumnWidth(col);
/* 1274 */         if (columnWidth.isPercentValue()) {
/* 1275 */           totalColumnWidthInPercent += columnWidth.getValue();
/*      */         }
/*      */       } 
/* 1278 */       tableWidth = Float.valueOf(parentBoxWidth);
/* 1279 */       if (totalColumnWidthInPercent > 0.0F) {
/* 1280 */         tableWidth = Float.valueOf(parentBoxWidth * totalColumnWidthInPercent / 100.0F);
/*      */       }
/*      */     } 
/* 1283 */     return tableWidth;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinMaxWidth getMinMaxWidth() {
/* 1288 */     if (this.isOriginalNonSplitRenderer) {
/* 1289 */       initializeTableLayoutBorders();
/*      */     }
/* 1291 */     float rightMaxBorder = this.bordersHandler.getRightBorderMaxWidth();
/* 1292 */     float leftMaxBorder = this.bordersHandler.getLeftBorderMaxWidth();
/* 1293 */     TableWidths tableWidths = new TableWidths(this, MinMaxWidthUtils.getInfWidth(), true, rightMaxBorder, leftMaxBorder);
/* 1294 */     float maxColTotalWidth = 0.0F;
/* 1295 */     float[] columns = this.isOriginalNonSplitRenderer ? tableWidths.layout() : this.countedColumnWidth;
/* 1296 */     for (float column : columns) {
/* 1297 */       maxColTotalWidth += column;
/*      */     }
/* 1299 */     float minWidth = this.isOriginalNonSplitRenderer ? tableWidths.getMinWidth() : maxColTotalWidth;
/* 1300 */     UnitValue marginRightUV = getPropertyAsUnitValue(45);
/* 1301 */     if (!marginRightUV.isPointValue()) {
/* 1302 */       Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1303 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(45) }));
/*      */     } 
/* 1305 */     UnitValue marginLefttUV = getPropertyAsUnitValue(44);
/* 1306 */     if (!marginLefttUV.isPointValue()) {
/* 1307 */       Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1308 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*      */     } 
/* 1310 */     float additionalWidth = marginLefttUV.getValue() + marginRightUV.getValue() + rightMaxBorder / 2.0F + leftMaxBorder / 2.0F;
/* 1311 */     return new MinMaxWidth(minWidth, maxColTotalWidth, additionalWidth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected Float getLastYLineRecursively() {
/* 1321 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean allowLastYLineRecursiveExtraction() {
/* 1326 */     return false;
/*      */   }
/*      */   
/*      */   private void initializeTableLayoutBorders() {
/* 1330 */     boolean isSeparated = BorderCollapsePropertyValue.SEPARATE.equals(getProperty(114));
/* 1331 */     this
/*      */       
/* 1333 */       .bordersHandler = isSeparated ? new SeparatedTableBorders(this.rows, ((Table)getModelElement()).getNumberOfColumns(), getBorders()) : new CollapsedTableBorders(this.rows, ((Table)getModelElement()).getNumberOfColumns(), getBorders());
/* 1334 */     this.bordersHandler.initializeBorders();
/* 1335 */     this.bordersHandler.setTableBoundingBorders(getBorders());
/* 1336 */     this.bordersHandler.setRowRange(this.rowRange.getStartRow(), this.rowRange.getFinishRow());
/* 1337 */     initializeHeaderAndFooter(true);
/* 1338 */     this.bordersHandler.updateBordersOnNewPage(this.isOriginalNonSplitRenderer, (isFooterRenderer() || isHeaderRenderer()), this, this.headerRenderer, this.footerRenderer);
/* 1339 */     correctRowRange();
/*      */   }
/*      */   
/*      */   private void correctRowRange() {
/* 1343 */     if (this.rows.size() < this.rowRange.getFinishRow() - this.rowRange.getStartRow() + 1) {
/* 1344 */       this.rowRange = new Table.RowRange(this.rowRange.getStartRow(), this.rowRange.getStartRow() + this.rows.size() - 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawBorder(DrawContext drawContext) {
/* 1350 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/* 1351 */       super.drawBorder(drawContext);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawBorders(DrawContext drawContext) {
/* 1358 */     drawBorders(drawContext, (null != this.headerRenderer), (null != this.footerRenderer));
/*      */   }
/*      */   
/*      */   private void drawBorders(DrawContext drawContext, boolean hasHeader, boolean hasFooter) {
/* 1362 */     float height = this.occupiedArea.getBBox().getHeight();
/* 1363 */     if (null != this.footerRenderer) {
/* 1364 */       height -= this.footerRenderer.occupiedArea.getBBox().getHeight();
/*      */     }
/* 1366 */     if (null != this.headerRenderer) {
/* 1367 */       height -= this.headerRenderer.occupiedArea.getBBox().getHeight();
/*      */     }
/* 1369 */     if (height < 1.0E-4F) {
/*      */       return;
/*      */     }
/*      */     
/* 1373 */     float startX = getOccupiedArea().getBBox().getX() + this.bordersHandler.getLeftBorderMaxWidth() / 2.0F;
/* 1374 */     float startY = getOccupiedArea().getBBox().getY() + getOccupiedArea().getBBox().getHeight();
/* 1375 */     if (null != this.headerRenderer) {
/* 1376 */       startY -= this.headerRenderer.occupiedArea.getBBox().getHeight();
/* 1377 */       startY += this.topBorderMaxWidth / 2.0F;
/*      */     } else {
/* 1379 */       startY -= this.topBorderMaxWidth / 2.0F;
/*      */     } 
/* 1381 */     if (hasProperty(46)) {
/* 1382 */       UnitValue topMargin = getPropertyAsUnitValue(46);
/* 1383 */       if (null != topMargin && !topMargin.isPointValue()) {
/* 1384 */         Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1385 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*      */       } 
/* 1387 */       startY -= (null == topMargin) ? 0.0F : topMargin.getValue();
/*      */     } 
/* 1389 */     if (hasProperty(44)) {
/* 1390 */       UnitValue leftMargin = getPropertyAsUnitValue(44);
/* 1391 */       if (null != leftMargin && !leftMargin.isPointValue()) {
/* 1392 */         Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1393 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*      */       } 
/* 1395 */       startX += (null == leftMargin) ? 0.0F : leftMargin.getValue();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1400 */     if (this.childRenderers.size() == 0) {
/* 1401 */       Border[] borders = this.bordersHandler.tableBoundingBorders;
/* 1402 */       if (null != borders[0]) {
/* 1403 */         if (null != borders[2] && 
/* 1404 */           0 == this.heights.size()) {
/* 1405 */           this.heights.add(0, Float.valueOf(borders[0].getWidth() / 2.0F + borders[2].getWidth() / 2.0F));
/*      */         }
/*      */       }
/* 1408 */       else if (null != borders[2]) {
/* 1409 */         startY -= borders[2].getWidth() / 2.0F;
/*      */       } 
/* 1411 */       if (0 == this.heights.size()) {
/* 1412 */         this.heights.add(Float.valueOf(0.0F));
/*      */       }
/*      */     } 
/*      */     
/* 1416 */     boolean isTagged = drawContext.isTaggingEnabled();
/* 1417 */     if (isTagged) {
/* 1418 */       drawContext.getCanvas().openTag((CanvasTag)new CanvasArtifact());
/*      */     }
/*      */ 
/*      */     
/* 1422 */     boolean isTopTablePart = isTopTablePart();
/* 1423 */     boolean isBottomTablePart = isBottomTablePart();
/* 1424 */     boolean isComplete = getTable().isComplete();
/* 1425 */     boolean isFooterRendererOfLargeTable = isFooterRendererOfLargeTable();
/*      */     
/* 1427 */     this.bordersHandler.setRowRange(this.rowRange.getStartRow(), this.rowRange.getStartRow() + this.heights.size() - 1);
/*      */     
/* 1429 */     if (this.bordersHandler instanceof CollapsedTableBorders) {
/* 1430 */       if (hasFooter) {
/* 1431 */         ((CollapsedTableBorders)this.bordersHandler).setBottomBorderCollapseWith(this.footerRenderer.bordersHandler.getFirstHorizontalBorder());
/* 1432 */       } else if (isBottomTablePart) {
/* 1433 */         ((CollapsedTableBorders)this.bordersHandler).setBottomBorderCollapseWith(null);
/*      */       } 
/*      */     }
/*      */     
/* 1437 */     float y1 = startY;
/* 1438 */     if (isFooterRendererOfLargeTable) {
/* 1439 */       this.bordersHandler.drawHorizontalBorder(0, startX, y1, drawContext.getCanvas(), this.countedColumnWidth);
/*      */     }
/* 1441 */     if (0 != this.heights.size()) {
/* 1442 */       y1 -= ((Float)this.heights.get(0)).floatValue();
/*      */     }
/* 1444 */     for (int i = 1; i < this.heights.size(); i++) {
/* 1445 */       this.bordersHandler.drawHorizontalBorder(i, startX, y1, drawContext.getCanvas(), this.countedColumnWidth);
/* 1446 */       if (i < this.heights.size()) {
/* 1447 */         y1 -= ((Float)this.heights.get(i)).floatValue();
/*      */       }
/*      */     } 
/* 1450 */     if (!isBottomTablePart && isComplete) {
/* 1451 */       this.bordersHandler.drawHorizontalBorder(this.heights.size(), startX, y1, drawContext.getCanvas(), this.countedColumnWidth);
/*      */     }
/*      */     
/* 1454 */     float x1 = startX;
/* 1455 */     if (this.countedColumnWidth.length > 0) {
/* 1456 */       x1 += this.countedColumnWidth[0];
/*      */     }
/* 1458 */     for (int j = 1; j < this.bordersHandler.getNumberOfColumns(); j++) {
/* 1459 */       this.bordersHandler.drawVerticalBorder(j, startY, x1, drawContext.getCanvas(), this.heights);
/* 1460 */       if (j < this.countedColumnWidth.length) {
/* 1461 */         x1 += this.countedColumnWidth[j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1466 */     if (isTopTablePart) {
/* 1467 */       this.bordersHandler.drawHorizontalBorder(0, startX, startY, drawContext.getCanvas(), this.countedColumnWidth);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1474 */     if (isBottomTablePart && (isComplete || (!this.isLastRendererForModelElement && !isEmptyTableRenderer()))) {
/* 1475 */       this.bordersHandler.drawHorizontalBorder(this.heights.size(), startX, y1, drawContext.getCanvas(), this.countedColumnWidth);
/*      */     }
/*      */     
/* 1478 */     this.bordersHandler.drawVerticalBorder(0, startY, startX, drawContext.getCanvas(), this.heights);
/*      */     
/* 1480 */     this.bordersHandler.drawVerticalBorder(this.bordersHandler.getNumberOfColumns(), startY, x1, drawContext.getCanvas(), this.heights);
/*      */     
/* 1482 */     if (isTagged) {
/* 1483 */       drawContext.getCanvas().closeTag();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isEmptyTableRenderer() {
/* 1488 */     return (this.rows.isEmpty() && this.heights.size() == 1 && ((Float)this.heights.get(0)).floatValue() == 0.0F);
/*      */   }
/*      */   
/*      */   private void applyFixedXOrYPosition(boolean isXPosition, Rectangle layoutBox) {
/* 1492 */     if (isPositioned() && 
/* 1493 */       isFixedLayout()) {
/* 1494 */       if (isXPosition) {
/* 1495 */         float x = getPropertyAsFloat(34).floatValue();
/* 1496 */         layoutBox.setX(x);
/*      */       } else {
/* 1498 */         float y = getPropertyAsFloat(14).floatValue();
/* 1499 */         move(0.0F, y - this.occupiedArea.getBBox().getY());
/*      */       } 
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
/*      */   private void adjustFooterAndFixOccupiedArea(Rectangle layoutBox, float verticalBorderSpacing) {
/* 1512 */     if (this.footerRenderer != null) {
/* 1513 */       this.footerRenderer.move(0.0F, layoutBox.getHeight() + verticalBorderSpacing);
/* 1514 */       float footerHeight = this.footerRenderer.getOccupiedArea().getBBox().getHeight() - verticalBorderSpacing;
/* 1515 */       this.occupiedArea.getBBox().moveDown(footerHeight).increaseHeight(footerHeight);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustCaptionAndFixOccupiedArea(Rectangle layoutBox, float verticalBorderSpacing) {
/* 1526 */     if (this.captionRenderer != null) {
/* 1527 */       float captionHeight = this.captionRenderer.getOccupiedArea().getBBox().getHeight();
/* 1528 */       this.occupiedArea.getBBox().moveDown(captionHeight).increaseHeight(captionHeight);
/* 1529 */       if (CaptionSide.BOTTOM.equals(this.captionRenderer.getProperty(119))) {
/* 1530 */         this.captionRenderer.move(0.0F, layoutBox.getHeight() + verticalBorderSpacing);
/*      */       } else {
/* 1532 */         this.occupiedArea.getBBox().moveUp(captionHeight);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void correctLayoutedCellsOccupiedAreas(LayoutResult[] splits, int row, int[] targetOverflowRowIndex, Float blockMinHeight, Rectangle layoutBox, List<Boolean> rowsHasCellWithSetHeight, boolean isLastRenderer, boolean processBigRowspan, boolean skip) {
/* 1543 */     int finish = this.bordersHandler.getFinishRow();
/* 1544 */     this.bordersHandler.setFinishRow(this.rowRange.getFinishRow());
/*      */ 
/*      */     
/* 1547 */     Border currentBorder = this.bordersHandler.getWidestHorizontalBorder(finish + 1);
/* 1548 */     this.bordersHandler.setFinishRow(finish);
/* 1549 */     if (skip) {
/*      */       
/* 1551 */       this.bordersHandler.tableBoundingBorders[2] = getBorders()[2];
/* 1552 */       this.bordersHandler.skipFooter(this.bordersHandler.tableBoundingBorders);
/*      */     } 
/*      */     
/* 1555 */     float currentBottomIndent = (this.bordersHandler instanceof CollapsedTableBorders) ? ((null == currentBorder) ? 0.0F : currentBorder.getWidth()) : 0.0F;
/*      */ 
/*      */     
/* 1558 */     float realBottomIndent = (this.bordersHandler instanceof CollapsedTableBorders) ? this.bordersHandler.getMaxBottomWidth() : 0.0F;
/*      */     
/* 1560 */     if (0 != this.heights.size()) {
/* 1561 */       this.heights.set(this.heights.size() - 1, Float.valueOf(((Float)this.heights.get(this.heights.size() - 1)).floatValue() + (realBottomIndent - currentBottomIndent) / 2.0F));
/*      */       
/* 1563 */       this.occupiedArea.getBBox().increaseHeight((realBottomIndent - currentBottomIndent) / 2.0F).moveDown((realBottomIndent - currentBottomIndent) / 2.0F);
/* 1564 */       layoutBox.decreaseHeight((realBottomIndent - currentBottomIndent) / 2.0F);
/* 1565 */       if (processBigRowspan) {
/*      */         
/* 1567 */         CellRenderer[] currentRow = this.rows.get(this.heights.size());
/* 1568 */         for (int col = 0; col < currentRow.length; col++) {
/* 1569 */           CellRenderer cell = (null == splits[col]) ? currentRow[col] : (CellRenderer)splits[col].getSplitRenderer();
/* 1570 */           if (cell != null) {
/*      */ 
/*      */             
/* 1573 */             float cellHeightInLastRow, height = 0.0F;
/* 1574 */             int rowspan = cell.getPropertyAsInteger(60).intValue();
/* 1575 */             int colspan = cell.getPropertyAsInteger(16).intValue();
/* 1576 */             float[] indents = this.bordersHandler.getCellBorderIndents((this.bordersHandler instanceof SeparatedTableBorders) ? row : targetOverflowRowIndex[col], col, rowspan, colspan);
/* 1577 */             for (int l = this.heights.size() - 1 - 1; l > targetOverflowRowIndex[col] - rowspan && l >= 0; l--) {
/* 1578 */               height += ((Float)this.heights.get(l)).floatValue();
/*      */             }
/*      */             
/* 1581 */             if (this.bordersHandler instanceof SeparatedTableBorders) {
/* 1582 */               cellHeightInLastRow = cell.getOccupiedArea().getBBox().getHeight() - height;
/*      */             } else {
/* 1584 */               cellHeightInLastRow = cell.getOccupiedArea().getBBox().getHeight() + indents[0] / 2.0F + indents[2] / 2.0F - height;
/*      */             } 
/* 1586 */             if (((Float)this.heights.get(this.heights.size() - 1)).floatValue() < cellHeightInLastRow) {
/* 1587 */               if (this.bordersHandler instanceof SeparatedTableBorders) {
/* 1588 */                 float differenceToConsider = cellHeightInLastRow - ((Float)this.heights.get(this.heights.size() - 1)).floatValue();
/* 1589 */                 this.occupiedArea.getBBox().moveDown(differenceToConsider);
/* 1590 */                 this.occupiedArea.getBBox().increaseHeight(differenceToConsider);
/*      */               } 
/* 1592 */               this.heights.set(this.heights.size() - 1, Float.valueOf(cellHeightInLastRow));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1597 */     }  float additionalCellHeight = 0.0F;
/* 1598 */     int numOfRowsWithFloatHeight = 0;
/* 1599 */     if (isLastRenderer) {
/* 1600 */       float additionalHeight = 0.0F;
/* 1601 */       if (null != blockMinHeight && blockMinHeight.floatValue() > this.occupiedArea.getBBox().getHeight() + realBottomIndent / 2.0F) {
/* 1602 */         additionalHeight = Math.min(layoutBox.getHeight() - realBottomIndent / 2.0F, blockMinHeight.floatValue() - this.occupiedArea.getBBox().getHeight() - realBottomIndent / 2.0F);
/* 1603 */         for (int j = 0; j < rowsHasCellWithSetHeight.size(); j++) {
/* 1604 */           if (Boolean.FALSE.equals(rowsHasCellWithSetHeight.get(j))) {
/* 1605 */             numOfRowsWithFloatHeight++;
/*      */           }
/*      */         } 
/*      */       } 
/* 1609 */       additionalCellHeight = additionalHeight / ((0 == numOfRowsWithFloatHeight) ? this.heights.size() : numOfRowsWithFloatHeight);
/* 1610 */       for (int i = 0; i < this.heights.size(); i++) {
/* 1611 */         if (0 == numOfRowsWithFloatHeight || Boolean.FALSE.equals(rowsHasCellWithSetHeight.get(i))) {
/* 1612 */           this.heights.set(i, Float.valueOf(((Float)this.heights.get(i)).floatValue() + additionalCellHeight));
/*      */         }
/*      */       } 
/*      */     } 
/* 1616 */     float cumulativeShift = 0.0F;
/*      */     
/* 1618 */     for (int k = 0; k < this.heights.size(); k++) {
/* 1619 */       correctRowCellsOccupiedAreas(splits, row, targetOverflowRowIndex, k, rowsHasCellWithSetHeight, cumulativeShift, additionalCellHeight);
/* 1620 */       if (isLastRenderer && (
/* 1621 */         0 == numOfRowsWithFloatHeight || Boolean.FALSE.equals(rowsHasCellWithSetHeight.get(k)))) {
/* 1622 */         cumulativeShift += additionalCellHeight;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1627 */     this.occupiedArea.getBBox().moveDown(cumulativeShift).increaseHeight(cumulativeShift);
/* 1628 */     layoutBox.decreaseHeight(cumulativeShift);
/*      */   }
/*      */ 
/*      */   
/*      */   private void correctRowCellsOccupiedAreas(LayoutResult[] splits, int row, int[] targetOverflowRowIndex, int currentRowIndex, List<Boolean> rowsHasCellWithSetHeight, float cumulativeShift, float additionalCellHeight) {
/* 1633 */     CellRenderer[] currentRow = this.rows.get(currentRowIndex);
/* 1634 */     for (int col = 0; col < currentRow.length; col++) {
/* 1635 */       CellRenderer cell = (currentRowIndex < row || null == splits[col]) ? currentRow[col] : (CellRenderer)splits[col].getSplitRenderer();
/* 1636 */       if (cell != null) {
/*      */ 
/*      */         
/* 1639 */         float height = 0.0F;
/* 1640 */         int colspan = cell.getPropertyAsInteger(16).intValue();
/* 1641 */         int rowspan = cell.getPropertyAsInteger(60).intValue();
/* 1642 */         float rowspanOffset = 0.0F;
/* 1643 */         float[] indents = this.bordersHandler.getCellBorderIndents((currentRowIndex < row || this.bordersHandler instanceof SeparatedTableBorders) ? currentRowIndex : targetOverflowRowIndex[col], col, rowspan, colspan);
/*      */         
/* 1645 */         for (int l = ((currentRowIndex < row) ? currentRowIndex : (this.heights.size() - 1)) - 1; l > ((currentRowIndex < row) ? currentRowIndex : targetOverflowRowIndex[col]) - rowspan && l >= 0; l--) {
/* 1646 */           height += ((Float)this.heights.get(l)).floatValue();
/* 1647 */           if (Boolean.FALSE.equals(rowsHasCellWithSetHeight.get(l))) {
/* 1648 */             rowspanOffset += additionalCellHeight;
/*      */           }
/*      */         } 
/* 1651 */         height += ((Float)this.heights.get((currentRowIndex < row) ? currentRowIndex : (this.heights.size() - 1))).floatValue();
/* 1652 */         if (!(this.bordersHandler instanceof SeparatedTableBorders)) {
/* 1653 */           height -= indents[0] / 2.0F + indents[2] / 2.0F;
/*      */         }
/*      */ 
/*      */         
/* 1657 */         float shift = height - cell.getOccupiedArea().getBBox().getHeight();
/* 1658 */         Rectangle bBox = cell.getOccupiedArea().getBBox();
/* 1659 */         bBox.moveDown(shift);
/*      */         try {
/* 1661 */           cell.move(0.0F, -(cumulativeShift - rowspanOffset));
/* 1662 */           bBox.setHeight(height);
/* 1663 */           cell.applyVerticalAlignment();
/*      */         
/*      */         }
/* 1666 */         catch (NullPointerException e) {
/* 1667 */           Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1668 */           logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Some of the cell's content might not end up placed correctly." }));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected void extendLastRow(CellRenderer[] lastRow, Rectangle freeBox) {
/* 1674 */     if (null != lastRow && 0 != this.heights.size()) {
/* 1675 */       this.heights.set(this.heights.size() - 1, Float.valueOf(((Float)this.heights.get(this.heights.size() - 1)).floatValue() + freeBox.getHeight()));
/* 1676 */       this.occupiedArea.getBBox().moveDown(freeBox.getHeight()).increaseHeight(freeBox.getHeight());
/* 1677 */       for (CellRenderer cell : lastRow) {
/* 1678 */         if (null != cell) {
/* 1679 */           cell.occupiedArea.getBBox().moveDown(freeBox.getHeight()).increaseHeight(freeBox.getHeight());
/*      */         }
/*      */       } 
/* 1682 */       freeBox.moveUp(freeBox.getHeight()).setHeight(0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setRowRange(Table.RowRange rowRange) {
/* 1692 */     this.rowRange = rowRange;
/* 1693 */     for (int row = rowRange.getStartRow(); row <= rowRange.getFinishRow(); row++) {
/* 1694 */       this.rows.add(new CellRenderer[((Table)this.modelElement).getNumberOfColumns()]);
/*      */     }
/*      */   }
/*      */   
/*      */   private TableRenderer initFooterOrHeaderRenderer(boolean footer, Border[] tableBorders) {
/* 1699 */     Table table = (Table)getModelElement();
/* 1700 */     boolean isSeparated = BorderCollapsePropertyValue.SEPARATE.equals(getProperty(114));
/* 1701 */     Table footerOrHeader = footer ? table.getFooter() : table.getHeader();
/* 1702 */     int innerBorder = footer ? 0 : 2;
/* 1703 */     int outerBorder = footer ? 2 : 0;
/* 1704 */     TableRenderer renderer = (TableRenderer)footerOrHeader.createRendererSubTree().setParent(this);
/* 1705 */     ensureFooterOrHeaderHasTheSamePropertiesAsParentTableRenderer(renderer);
/* 1706 */     boolean firstHeader = (!footer && this.rowRange.getStartRow() == 0 && this.isOriginalNonSplitRenderer);
/* 1707 */     LayoutTaggingHelper taggingHelper = getProperty(108);
/* 1708 */     if (taggingHelper != null) {
/* 1709 */       taggingHelper.addKidsHint(this, Collections.singletonList(renderer));
/* 1710 */       LayoutTaggingHelper.addTreeHints(taggingHelper, renderer);
/*      */ 
/*      */       
/* 1713 */       if (!footer && !firstHeader) {
/* 1714 */         taggingHelper.markArtifactHint(renderer);
/*      */       }
/*      */     } 
/*      */     
/* 1718 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/* 1719 */       if (table.isEmpty()) {
/*      */         
/* 1721 */         if (!footer || null == this.headerRenderer) {
/* 1722 */           renderer.setBorders(tableBorders[innerBorder], innerBorder);
/*      */         }
/* 1724 */         this.bordersHandler.tableBoundingBorders[innerBorder] = Border.NO_BORDER;
/*      */       } 
/* 1726 */       renderer.setBorders(tableBorders[1], 1);
/* 1727 */       renderer.setBorders(tableBorders[3], 3);
/* 1728 */       renderer.setBorders(tableBorders[outerBorder], outerBorder);
/* 1729 */       this.bordersHandler.tableBoundingBorders[outerBorder] = Border.NO_BORDER;
/* 1730 */     } else if (this.bordersHandler instanceof CollapsedTableBorders) {
/* 1731 */       Border[] borders = renderer.getBorders();
/* 1732 */       if (table.isEmpty()) {
/* 1733 */         renderer.setBorders(CollapsedTableBorders.getCollapsedBorder(borders[innerBorder], tableBorders[innerBorder]), innerBorder);
/* 1734 */         this.bordersHandler.tableBoundingBorders[innerBorder] = Border.NO_BORDER;
/*      */       } 
/* 1736 */       renderer.setBorders(CollapsedTableBorders.getCollapsedBorder(borders[1], tableBorders[1]), 1);
/* 1737 */       renderer.setBorders(CollapsedTableBorders.getCollapsedBorder(borders[3], tableBorders[3]), 3);
/* 1738 */       renderer.setBorders(CollapsedTableBorders.getCollapsedBorder(borders[outerBorder], tableBorders[outerBorder]), outerBorder);
/* 1739 */       this.bordersHandler.tableBoundingBorders[outerBorder] = Border.NO_BORDER;
/*      */     } 
/*      */     
/* 1742 */     renderer
/*      */       
/* 1744 */       .bordersHandler = isSeparated ? new SeparatedTableBorders(renderer.rows, ((Table)renderer.getModelElement()).getNumberOfColumns(), renderer.getBorders()) : new CollapsedTableBorders(renderer.rows, ((Table)renderer.getModelElement()).getNumberOfColumns(), renderer.getBorders());
/* 1745 */     renderer.bordersHandler.initializeBorders();
/* 1746 */     renderer.bordersHandler.setRowRange(renderer.rowRange.getStartRow(), renderer.rowRange.getFinishRow());
/* 1747 */     renderer.bordersHandler.processAllBordersAndEmptyRows();
/* 1748 */     renderer.correctRowRange();
/* 1749 */     return renderer;
/*      */   }
/*      */   
/*      */   private void ensureFooterOrHeaderHasTheSamePropertiesAsParentTableRenderer(TableRenderer headerOrFooterRenderer) {
/* 1753 */     headerOrFooterRenderer.setProperty(114, getProperty(114));
/* 1754 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/* 1755 */       headerOrFooterRenderer.setProperty(115, getPropertyAsFloat(115));
/* 1756 */       headerOrFooterRenderer.setProperty(116, getPropertyAsFloat(116));
/* 1757 */       headerOrFooterRenderer.setProperty(9, Border.NO_BORDER);
/* 1758 */       headerOrFooterRenderer.setProperty(11, Border.NO_BORDER);
/* 1759 */       headerOrFooterRenderer.setProperty(13, Border.NO_BORDER);
/* 1760 */       headerOrFooterRenderer.setProperty(12, Border.NO_BORDER);
/* 1761 */       headerOrFooterRenderer.setProperty(10, Border.NO_BORDER);
/*      */     } 
/*      */   }
/*      */   
/*      */   private TableRenderer prepareFooterOrHeaderRendererForLayout(TableRenderer renderer, float layoutBoxWidth) {
/* 1766 */     renderer.countedColumnWidth = this.countedColumnWidth;
/* 1767 */     renderer.bordersHandler.leftBorderMaxWidth = this.bordersHandler.getLeftBorderMaxWidth();
/* 1768 */     renderer.bordersHandler.rightBorderMaxWidth = this.bordersHandler.getRightBorderMaxWidth();
/* 1769 */     if (hasProperty(77)) {
/* 1770 */       renderer.setProperty(77, UnitValue.createPointValue(layoutBoxWidth));
/*      */     }
/* 1772 */     return this;
/*      */   }
/*      */   
/*      */   private boolean isHeaderRenderer() {
/* 1776 */     return (this.parent instanceof TableRenderer && ((TableRenderer)this.parent).headerRenderer == this);
/*      */   }
/*      */   
/*      */   private boolean isFooterRenderer() {
/* 1780 */     return (this.parent instanceof TableRenderer && ((TableRenderer)this.parent).footerRenderer == this);
/*      */   }
/*      */   
/*      */   private boolean isFooterRendererOfLargeTable() {
/* 1784 */     return (isFooterRenderer() && (!((TableRenderer)this.parent).getTable().isComplete() || 0 != ((TableRenderer)this.parent).getTable().getLastRowBottomBorder().size()));
/*      */   }
/*      */   
/*      */   private boolean isTopTablePart() {
/* 1788 */     return (null == this.headerRenderer && (
/* 1789 */       !isFooterRenderer() || (0 == ((TableRenderer)this.parent).rows.size() && null == ((TableRenderer)this.parent).headerRenderer)));
/*      */   }
/*      */   
/*      */   private boolean isBottomTablePart() {
/* 1793 */     return (null == this.footerRenderer && (
/* 1794 */       !isHeaderRenderer() || (0 == ((TableRenderer)this.parent).rows.size() && null == ((TableRenderer)this.parent).footerRenderer)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void calculateColumnWidths(float availableWidth) {
/* 1801 */     if (this.countedColumnWidth == null || this.totalWidthForColumns != availableWidth) {
/* 1802 */       TableWidths tableWidths = new TableWidths(this, availableWidth, false, this.bordersHandler.rightBorderMaxWidth, this.bordersHandler.leftBorderMaxWidth);
/* 1803 */       this.countedColumnWidth = tableWidths.layout();
/*      */     } 
/*      */   }
/*      */   
/*      */   private float getTableWidth() {
/* 1808 */     float sum = 0.0F;
/* 1809 */     for (float column : this.countedColumnWidth) {
/* 1810 */       sum += column;
/*      */     }
/* 1812 */     if (this.bordersHandler instanceof SeparatedTableBorders) {
/* 1813 */       sum += this.bordersHandler.getRightBorderMaxWidth() + this.bordersHandler.getLeftBorderMaxWidth();
/* 1814 */       Float horizontalSpacing = getPropertyAsFloat(115);
/* 1815 */       sum += (null == horizontalSpacing) ? 0.0F : horizontalSpacing.floatValue();
/*      */     } else {
/* 1817 */       sum += this.bordersHandler.getRightBorderMaxWidth() / 2.0F + this.bordersHandler.getLeftBorderMaxWidth() / 2.0F;
/*      */     } 
/* 1819 */     return sum;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CellRendererInfo
/*      */   {
/*      */     public CellRenderer cellRenderer;
/*      */     
/*      */     public int column;
/*      */     public int finishRowInd;
/*      */     
/*      */     public CellRendererInfo(CellRenderer cellRenderer, int column, int finishRow) {
/* 1831 */       this.cellRenderer = cellRenderer;
/* 1832 */       this.column = column;
/*      */ 
/*      */       
/* 1835 */       this.finishRowInd = finishRow;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class OverflowRowsWrapper
/*      */   {
/*      */     private TableRenderer overflowRenderer;
/*      */     
/* 1844 */     private HashMap<Integer, Boolean> isRowReplaced = new HashMap<>();
/*      */     private boolean isReplaced = false;
/*      */     
/*      */     public OverflowRowsWrapper(TableRenderer overflowRenderer) {
/* 1848 */       this.overflowRenderer = overflowRenderer;
/*      */     }
/*      */     
/*      */     public CellRenderer getCell(int row, int col) {
/* 1852 */       return ((CellRenderer[])this.overflowRenderer.rows.get(row))[col];
/*      */     }
/*      */     
/*      */     public CellRenderer setCell(int row, int col, CellRenderer newCell) {
/* 1856 */       if (!this.isReplaced) {
/* 1857 */         this.overflowRenderer.rows = (List)new ArrayList<>(this.overflowRenderer.rows);
/* 1858 */         this.isReplaced = true;
/*      */       } 
/* 1860 */       if (!Boolean.TRUE.equals(this.isRowReplaced.get(Integer.valueOf(row)))) {
/* 1861 */         this.overflowRenderer.rows.set(row, (CellRenderer[])((CellRenderer[])this.overflowRenderer.rows.get(row)).clone());
/*      */       }
/* 1863 */       ((CellRenderer[])this.overflowRenderer.rows.get(row))[col] = newCell; return newCell;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void enlargeCellWithBigRowspan(CellRenderer[] currentRow, OverflowRowsWrapper overflowRows, int row, int col, int minRowspan, TableRenderer[] splitResult, int[] targetOverflowRowIndex) {
/* 1870 */     this.childRenderers.add(currentRow[col]);
/*      */     
/* 1872 */     int i = row;
/* 1873 */     for (; i < row + minRowspan && i + 1 < this.rows.size() && ((CellRenderer[])(splitResult[1]).rows.get(i + 1 - row))[col] != null; i++) {
/* 1874 */       overflowRows.setCell(i - row, col, ((CellRenderer[])(splitResult[1]).rows.get(i + 1 - row))[col]);
/* 1875 */       overflowRows.setCell(i + 1 - row, col, null);
/* 1876 */       ((CellRenderer[])this.rows.get(i))[col] = ((CellRenderer[])this.rows.get(i + 1))[col];
/* 1877 */       ((CellRenderer[])this.rows.get(i + 1))[col] = null;
/*      */     } 
/*      */ 
/*      */     
/* 1881 */     if (i != row + minRowspan - 1 && null != ((CellRenderer[])this.rows.get(i))[col]) {
/* 1882 */       CellRenderer overflowCell = (CellRenderer)((Cell)((CellRenderer[])this.rows.get(i))[col].getModelElement()).getRenderer().setParent(this);
/* 1883 */       overflowRows.setCell(i - row, col, null);
/* 1884 */       overflowRows.setCell(targetOverflowRowIndex[col] - row, col, overflowCell);
/* 1885 */       CellRenderer originalCell = ((CellRenderer[])this.rows.get(i))[col];
/* 1886 */       ((CellRenderer[])this.rows.get(i))[col] = null;
/* 1887 */       ((CellRenderer[])this.rows.get(targetOverflowRowIndex[col]))[col] = originalCell;
/* 1888 */       originalCell.isLastRendererForModelElement = false;
/* 1889 */       overflowCell.setProperty(109, originalCell.getProperty(109));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void enlargeCell(int col, int row, int minRowspan, CellRenderer[] currentRow, OverflowRowsWrapper overflowRows, int[] targetOverflowRowIndex, TableRenderer[] splitResult) {
/* 1895 */     LayoutArea cellOccupiedArea = currentRow[col].getOccupiedArea();
/* 1896 */     if (1 == minRowspan) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1901 */       CellRenderer overflowCell = (CellRenderer)((Cell)currentRow[col].getModelElement()).clone(true).getRenderer();
/* 1902 */       overflowCell.setParent(this);
/* 1903 */       overflowCell.deleteProperty(27);
/* 1904 */       overflowCell.deleteProperty(85);
/* 1905 */       overflowCell.deleteProperty(84);
/* 1906 */       overflowRows.setCell(0, col, null);
/* 1907 */       overflowRows.setCell(targetOverflowRowIndex[col] - row, col, overflowCell);
/* 1908 */       this.childRenderers.add(currentRow[col]);
/* 1909 */       CellRenderer originalCell = currentRow[col];
/* 1910 */       currentRow[col] = null;
/* 1911 */       ((CellRenderer[])this.rows.get(targetOverflowRowIndex[col]))[col] = originalCell;
/* 1912 */       originalCell.isLastRendererForModelElement = false;
/* 1913 */       overflowCell.setProperty(109, originalCell.getProperty(109));
/*      */     } else {
/* 1915 */       enlargeCellWithBigRowspan(currentRow, overflowRows, row, col, minRowspan, splitResult, targetOverflowRowIndex);
/*      */     } 
/* 1917 */     (overflowRows.getCell(targetOverflowRowIndex[col] - row, col)).occupiedArea = cellOccupiedArea;
/*      */   }
/*      */   
/*      */   void applyMarginsAndPaddingsAndCalculateColumnWidths(Rectangle layoutBox) {
/* 1921 */     UnitValue[] margins = getMargins();
/* 1922 */     if (!margins[1].isPointValue()) {
/* 1923 */       Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1924 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(45) }));
/*      */     } 
/* 1926 */     if (!margins[3].isPointValue()) {
/* 1927 */       Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1928 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*      */     } 
/* 1930 */     UnitValue[] paddings = getPaddings();
/* 1931 */     if (!paddings[1].isPointValue()) {
/* 1932 */       Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1933 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(49) }));
/*      */     } 
/* 1935 */     if (!paddings[3].isPointValue()) {
/* 1936 */       Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 1937 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(48) }));
/*      */     } 
/* 1939 */     calculateColumnWidths(layoutBox.getWidth() - margins[1]
/* 1940 */         .getValue() - margins[3].getValue() - paddings[1]
/* 1941 */         .getValue() - paddings[3].getValue());
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/TableRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */