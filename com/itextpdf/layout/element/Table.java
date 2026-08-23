/*      */ package com.itextpdf.layout.element;
/*      */ 
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*      */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*      */ import com.itextpdf.layout.Document;
/*      */ import com.itextpdf.layout.borders.Border;
/*      */ import com.itextpdf.layout.property.BorderCollapsePropertyValue;
/*      */ import com.itextpdf.layout.property.CaptionSide;
/*      */ import com.itextpdf.layout.property.UnitValue;
/*      */ import com.itextpdf.layout.renderer.IRenderer;
/*      */ import com.itextpdf.layout.renderer.TableRenderer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Table
/*      */   extends BlockElement<Table>
/*      */   implements ILargeElement
/*      */ {
/*      */   protected DefaultAccessibilityProperties tagProperties;
/*      */   private List<Cell[]> rows;
/*      */   private UnitValue[] columnWidths;
/*   78 */   private int currentColumn = 0;
/*   79 */   private int currentRow = -1;
/*      */   
/*      */   private Table header;
/*      */   
/*      */   private Table footer;
/*      */   private boolean skipFirstHeader;
/*      */   private boolean skipLastFooter;
/*      */   private boolean isComplete;
/*      */   private List<RowRange> lastAddedRowGroups;
/*   88 */   private int rowWindowStart = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Document document;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Cell[] lastAddedRow;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Div caption;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table(float[] columnWidths, boolean largeTable) {
/*  113 */     if (columnWidths == null) {
/*  114 */       throw new IllegalArgumentException("The widths array in table constructor can not be null.");
/*      */     }
/*  116 */     if (columnWidths.length == 0) {
/*  117 */       throw new IllegalArgumentException("The widths array in table constructor can not have zero length.");
/*      */     }
/*  119 */     this.columnWidths = normalizeColumnWidths(columnWidths);
/*  120 */     initializeLargeTable(largeTable);
/*  121 */     initializeRows();
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
/*      */   public Table(UnitValue[] columnWidths, boolean largeTable) {
/*  144 */     if (columnWidths == null) {
/*  145 */       throw new IllegalArgumentException("The widths array in table constructor can not be null.");
/*      */     }
/*  147 */     if (columnWidths.length == 0) {
/*  148 */       throw new IllegalArgumentException("The widths array in table constructor can not have zero length.");
/*      */     }
/*  150 */     this.columnWidths = normalizeColumnWidths(columnWidths);
/*  151 */     initializeLargeTable(largeTable);
/*  152 */     initializeRows();
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
/*      */   public Table(UnitValue[] columnWidths) {
/*  172 */     this(columnWidths, false);
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
/*      */   public Table(float[] pointColumnWidths) {
/*  192 */     this(pointColumnWidths, false);
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
/*      */   
/*      */   public Table(int numColumns, boolean largeTable) {
/*  218 */     if (numColumns <= 0) {
/*  219 */       throw new IllegalArgumentException("The number of columns in Table constructor must be greater than zero");
/*      */     }
/*  221 */     this.columnWidths = normalizeColumnWidths(numColumns);
/*  222 */     initializeLargeTable(largeTable);
/*  223 */     initializeRows();
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
/*      */   public Table(int numColumns) {
/*  247 */     this(numColumns, false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table setFixedLayout() {
/*  277 */     setProperty(93, "fixed");
/*  278 */     return this;
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
/*      */   public Table setAutoLayout() {
/*  300 */     setProperty(93, "auto");
/*  301 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table useAllAvailableWidth() {
/*  310 */     setProperty(77, UnitValue.createPercentValue(100.0F));
/*  311 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnitValue getColumnWidth(int column) {
/*  321 */     return this.columnWidths[column];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumberOfColumns() {
/*  330 */     return this.columnWidths.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumberOfRows() {
/*  339 */     return this.rows.size();
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
/*      */   public Table addHeaderCell(Cell headerCell) {
/*  351 */     ensureHeaderIsInitialized();
/*  352 */     this.header.addCell(headerCell);
/*  353 */     return this;
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
/*      */   public <T extends IElement> Table addHeaderCell(BlockElement<T> blockElement) {
/*  366 */     ensureHeaderIsInitialized();
/*  367 */     this.header.addCell(blockElement);
/*  368 */     return this;
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
/*      */   public Table addHeaderCell(Image image) {
/*  380 */     ensureHeaderIsInitialized();
/*  381 */     this.header.addCell(image);
/*  382 */     return this;
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
/*      */   public Table addHeaderCell(String content) {
/*  394 */     ensureHeaderIsInitialized();
/*  395 */     this.header.addCell(content);
/*  396 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table getHeader() {
/*  405 */     return this.header;
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
/*      */   public Table addFooterCell(Cell footerCell) {
/*  417 */     ensureFooterIsInitialized();
/*  418 */     this.footer.addCell(footerCell);
/*  419 */     return this;
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
/*      */   public <T extends IElement> Table addFooterCell(BlockElement<T> blockElement) {
/*  432 */     ensureFooterIsInitialized();
/*  433 */     this.footer.addCell(blockElement);
/*  434 */     return this;
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
/*      */   public Table addFooterCell(Image image) {
/*  446 */     ensureFooterIsInitialized();
/*  447 */     this.footer.addCell(image);
/*  448 */     return this;
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
/*      */   public Table addFooterCell(String content) {
/*  460 */     ensureFooterIsInitialized();
/*  461 */     this.footer.addCell(content);
/*  462 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table getFooter() {
/*  471 */     return this.footer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSkipFirstHeader() {
/*  481 */     return this.skipFirstHeader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table setSkipFirstHeader(boolean skipFirstHeader) {
/*  492 */     this.skipFirstHeader = skipFirstHeader;
/*  493 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSkipLastFooter() {
/*  503 */     return this.skipLastFooter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table setSkipLastFooter(boolean skipLastFooter) {
/*  514 */     this.skipLastFooter = skipLastFooter;
/*  515 */     return this;
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
/*      */   public Table setCaption(Div caption) {
/*  529 */     this.caption = caption;
/*  530 */     if (null != caption) {
/*  531 */       ensureCaptionPropertiesAreSet();
/*      */     }
/*  533 */     return this;
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
/*      */   public Table setCaption(Div caption, CaptionSide side) {
/*  546 */     if (null != caption) {
/*  547 */       caption.setProperty(119, side);
/*      */     }
/*  549 */     setCaption(caption);
/*  550 */     return this;
/*      */   }
/*      */   
/*      */   private void ensureCaptionPropertiesAreSet() {
/*  554 */     this.caption.getAccessibilityProperties().setRole("Caption");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Div getCaption() {
/*  563 */     return this.caption;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table startNewRow() {
/*  572 */     this.currentColumn = 0;
/*  573 */     this.currentRow++;
/*  574 */     if (this.currentRow >= this.rows.size()) {
/*  575 */       this.rows.add(new Cell[this.columnWidths.length]);
/*      */     }
/*  577 */     return this;
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
/*      */   public Table addCell(Cell cell) {
/*  589 */     if (this.isComplete && null != this.lastAddedRow) {
/*  590 */       throw new PdfException("The large table was completed. It's prohibited to use it anymore. Created different Table instance instead.");
/*      */     }
/*      */ 
/*      */     
/*      */     while (true) {
/*  595 */       if (this.currentColumn >= this.columnWidths.length || this.currentColumn == -1) {
/*  596 */         startNewRow();
/*      */       }
/*  598 */       if (((Cell[])this.rows.get(this.currentRow - this.rowWindowStart))[this.currentColumn] != null) {
/*  599 */         this.currentColumn++;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/*  605 */     this.childElements.add(cell);
/*  606 */     cell.updateCellIndexes(this.currentRow, this.currentColumn, this.columnWidths.length);
/*      */     
/*  608 */     while (this.currentRow - this.rowWindowStart + cell.getRowspan() > this.rows.size()) {
/*  609 */       this.rows.add(new Cell[this.columnWidths.length]);
/*      */     }
/*      */ 
/*      */     
/*  613 */     for (int i = this.currentRow; i < this.currentRow + cell.getRowspan(); i++) {
/*  614 */       Cell[] row = this.rows.get(i - this.rowWindowStart);
/*  615 */       for (int j = this.currentColumn; j < this.currentColumn + cell.getColspan(); j++) {
/*  616 */         if (row[j] == null) {
/*  617 */           row[j] = cell;
/*      */         }
/*      */       } 
/*      */     } 
/*  621 */     this.currentColumn += cell.getColspan();
/*  622 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends IElement> Table addCell(BlockElement<T> blockElement) {
/*  633 */     return addCell((new Cell()).add(blockElement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table addCell(Image image) {
/*  643 */     return addCell((new Cell()).add(image));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Table addCell(String content) {
/*  653 */     return addCell((new Cell()).add(new Paragraph(content)));
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
/*      */   public Cell getCell(int row, int column) {
/*  665 */     if (row - this.rowWindowStart < this.rows.size()) {
/*  666 */       Cell cell = ((Cell[])this.rows.get(row - this.rowWindowStart))[column];
/*      */       
/*  668 */       if (cell != null && cell.getRow() == row && cell.getCol() == column) {
/*  669 */         return cell;
/*      */       }
/*      */     } 
/*  672 */     return null;
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
/*      */   public IRenderer createRendererSubTree() {
/*  684 */     TableRenderer rendererRoot = (TableRenderer)getRenderer();
/*  685 */     for (IElement child : this.childElements) {
/*  686 */       boolean childShouldBeAdded = (this.isComplete || cellBelongsToAnyRowGroup((Cell)child, this.lastAddedRowGroups));
/*  687 */       if (childShouldBeAdded) {
/*  688 */         rendererRoot.addChild(child.createRendererSubTree());
/*      */       }
/*      */     } 
/*  691 */     return (IRenderer)rendererRoot;
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
/*      */   public IRenderer getRenderer() {
/*  703 */     if (this.nextRenderer != null) {
/*  704 */       if (this.nextRenderer instanceof TableRenderer) {
/*  705 */         IRenderer renderer = this.nextRenderer;
/*  706 */         this.nextRenderer = this.nextRenderer.getNextRenderer();
/*  707 */         return renderer;
/*      */       } 
/*  709 */       Logger logger = LoggerFactory.getLogger(Table.class);
/*  710 */       logger.error("Invalid renderer for Table: must be inherited from TableRenderer");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  715 */     if (this.isComplete) {
/*      */       
/*  717 */       if (null != this.lastAddedRow && 0 != this.rows.size()) {
/*  718 */         List<RowRange> allRows = new ArrayList<>();
/*  719 */         allRows.add(new RowRange(this.rowWindowStart, this.rowWindowStart + this.rows.size() - 1));
/*  720 */         this.lastAddedRowGroups = allRows;
/*      */       } 
/*      */     } else {
/*  723 */       this.lastAddedRowGroups = getRowGroups();
/*      */     } 
/*  725 */     if (this.isComplete) {
/*  726 */       return (IRenderer)new TableRenderer(this, new RowRange(this.rowWindowStart, this.rowWindowStart + this.rows.size() - 1));
/*      */     }
/*  728 */     int rowWindowFinish = (this.lastAddedRowGroups.size() != 0) ? ((RowRange)this.lastAddedRowGroups.get(this.lastAddedRowGroups.size() - 1)).finishRow : -1;
/*  729 */     return (IRenderer)new TableRenderer(this, new RowRange(this.rowWindowStart, rowWindowFinish));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isComplete() {
/*  735 */     return this.isComplete;
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
/*      */   public void complete() {
/*  750 */     assert !this.isComplete;
/*  751 */     this.isComplete = true;
/*  752 */     flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() {
/*  760 */     Cell[] row = null;
/*  761 */     int rowNum = this.rows.size();
/*  762 */     if (!this.rows.isEmpty()) {
/*  763 */       row = this.rows.get(this.rows.size() - 1);
/*      */     }
/*  765 */     this.document.add(this);
/*  766 */     if (row != null && rowNum != this.rows.size()) {
/*  767 */       this.lastAddedRow = row;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flushContent() {
/*  777 */     if (this.lastAddedRowGroups == null || this.lastAddedRowGroups.isEmpty())
/*      */       return; 
/*  779 */     int firstRow = ((RowRange)this.lastAddedRowGroups.get(0)).startRow;
/*  780 */     int lastRow = ((RowRange)this.lastAddedRowGroups.get(this.lastAddedRowGroups.size() - 1)).finishRow;
/*      */     
/*  782 */     List<IElement> toRemove = new ArrayList<>();
/*  783 */     for (IElement cell : this.childElements) {
/*  784 */       if (((Cell)cell).getRow() >= firstRow && ((Cell)cell).getRow() <= lastRow) {
/*  785 */         toRemove.add(cell);
/*      */       }
/*      */     } 
/*  788 */     this.childElements.removeAll(toRemove);
/*      */     
/*  790 */     for (int i = 0; i < lastRow - firstRow; i++) {
/*  791 */       this.rows.remove(firstRow - this.rowWindowStart);
/*      */     }
/*  793 */     this.lastAddedRow = this.rows.remove(firstRow - this.rowWindowStart);
/*  794 */     this.rowWindowStart = ((RowRange)this.lastAddedRowGroups.get(this.lastAddedRowGroups.size() - 1)).getFinishRow() + 1;
/*      */     
/*  796 */     this.lastAddedRowGroups = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDocument(Document document) {
/*  801 */     this.document = document;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Border> getLastRowBottomBorder() {
/*  810 */     List<Border> horizontalBorder = new ArrayList<>();
/*  811 */     if (this.lastAddedRow != null) {
/*  812 */       for (int i = 0; i < this.lastAddedRow.length; i++) {
/*  813 */         Cell cell = this.lastAddedRow[i];
/*  814 */         Border border = null;
/*  815 */         if (cell != null) {
/*  816 */           if (cell.hasProperty(10)) {
/*  817 */             border = (Border)cell.getProperty(10);
/*  818 */           } else if (cell.hasProperty(9)) {
/*  819 */             border = (Border)cell.getProperty(9);
/*      */           } else {
/*  821 */             border = cell.<Border>getDefaultProperty(9);
/*      */           } 
/*      */         }
/*  824 */         horizontalBorder.add(border);
/*      */       } 
/*      */     }
/*      */     
/*  828 */     return horizontalBorder;
/*      */   }
/*      */   
/*      */   public Table setExtendBottomRow(boolean isExtended) {
/*  832 */     setProperty(86, Boolean.valueOf(isExtended));
/*  833 */     return this;
/*      */   }
/*      */   
/*      */   public Table setExtendBottomRowOnSplit(boolean isExtended) {
/*  837 */     setProperty(87, Boolean.valueOf(isExtended));
/*  838 */     return this;
/*      */   }
/*      */   
/*      */   public Table setBorderCollapse(BorderCollapsePropertyValue collapsePropertyValue) {
/*  842 */     setProperty(114, collapsePropertyValue);
/*  843 */     if (null != this.header) {
/*  844 */       this.header.setBorderCollapse(collapsePropertyValue);
/*      */     }
/*  846 */     if (null != this.footer) {
/*  847 */       this.footer.setBorderCollapse(collapsePropertyValue);
/*      */     }
/*  849 */     return this;
/*      */   }
/*      */   
/*      */   public Table setHorizontalBorderSpacing(float spacing) {
/*  853 */     setProperty(115, Float.valueOf(spacing));
/*  854 */     if (null != this.header) {
/*  855 */       this.header.setHorizontalBorderSpacing(spacing);
/*      */     }
/*  857 */     if (null != this.footer) {
/*  858 */       this.footer.setHorizontalBorderSpacing(spacing);
/*      */     }
/*  860 */     return this;
/*      */   }
/*      */   
/*      */   public Table setVerticalBorderSpacing(float spacing) {
/*  864 */     setProperty(116, Float.valueOf(spacing));
/*  865 */     if (null != this.header) {
/*  866 */       this.header.setVerticalBorderSpacing(spacing);
/*      */     }
/*  868 */     if (null != this.footer) {
/*  869 */       this.footer.setVerticalBorderSpacing(spacing);
/*      */     }
/*  871 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public AccessibilityProperties getAccessibilityProperties() {
/*  876 */     if (this.tagProperties == null) {
/*  877 */       this.tagProperties = new DefaultAccessibilityProperties("Table");
/*      */     }
/*  879 */     return (AccessibilityProperties)this.tagProperties;
/*      */   }
/*      */ 
/*      */   
/*      */   protected IRenderer makeNewRenderer() {
/*  884 */     return (IRenderer)new TableRenderer(this);
/*      */   }
/*      */   
/*      */   private static UnitValue[] normalizeColumnWidths(float[] pointColumnWidths) {
/*  888 */     UnitValue[] normalized = new UnitValue[pointColumnWidths.length];
/*  889 */     for (int i = 0; i < normalized.length; i++) {
/*  890 */       if (pointColumnWidths[i] >= 0.0F) {
/*  891 */         normalized[i] = UnitValue.createPointValue(pointColumnWidths[i]);
/*      */       }
/*      */     } 
/*  894 */     return normalized;
/*      */   }
/*      */   
/*      */   private static UnitValue[] normalizeColumnWidths(UnitValue[] unitColumnWidths) {
/*  898 */     UnitValue[] normalized = new UnitValue[unitColumnWidths.length];
/*  899 */     for (int i = 0; i < unitColumnWidths.length; i++) {
/*  900 */       normalized[i] = (unitColumnWidths[i] != null && unitColumnWidths[i].getValue() >= 0.0F) ? new UnitValue(unitColumnWidths[i]) : null;
/*      */     }
/*      */ 
/*      */     
/*  904 */     return normalized;
/*      */   }
/*      */   
/*      */   private static UnitValue[] normalizeColumnWidths(int numberOfColumns) {
/*  908 */     UnitValue[] normalized = new UnitValue[numberOfColumns];
/*  909 */     return normalized;
/*      */   }
/*      */   
/*      */   protected List<RowRange> getRowGroups() {
/*  913 */     int lastRowWeCanFlush = (this.currentColumn == this.columnWidths.length) ? this.currentRow : (this.currentRow - 1);
/*  914 */     int[] cellBottomRows = new int[this.columnWidths.length];
/*  915 */     int currentRowGroupStart = this.rowWindowStart;
/*  916 */     List<RowRange> rowGroups = new ArrayList<>();
/*  917 */     while (currentRowGroupStart <= lastRowWeCanFlush) {
/*  918 */       for (int i = 0; i < this.columnWidths.length; i++) {
/*  919 */         cellBottomRows[i] = currentRowGroupStart;
/*      */       }
/*  921 */       int maxRowGroupFinish = cellBottomRows[0] + ((Cell[])this.rows.get(cellBottomRows[0] - this.rowWindowStart))[0].getRowspan() - 1;
/*  922 */       boolean converged = false;
/*  923 */       boolean rowGroupComplete = true;
/*  924 */       while (!converged) {
/*  925 */         converged = true;
/*  926 */         for (int j = 0; j < this.columnWidths.length; j++) {
/*  927 */           while (cellBottomRows[j] < lastRowWeCanFlush && cellBottomRows[j] + ((Cell[])this.rows.get(cellBottomRows[j] - this.rowWindowStart))[j].getRowspan() - 1 < maxRowGroupFinish) {
/*  928 */             cellBottomRows[j] = cellBottomRows[j] + ((Cell[])this.rows.get(cellBottomRows[j] - this.rowWindowStart))[j].getRowspan();
/*      */           }
/*  930 */           if (cellBottomRows[j] + ((Cell[])this.rows.get(cellBottomRows[j] - this.rowWindowStart))[j].getRowspan() - 1 > maxRowGroupFinish) {
/*  931 */             maxRowGroupFinish = cellBottomRows[j] + ((Cell[])this.rows.get(cellBottomRows[j] - this.rowWindowStart))[j].getRowspan() - 1;
/*  932 */             converged = false;
/*  933 */           } else if (cellBottomRows[j] + ((Cell[])this.rows.get(cellBottomRows[j] - this.rowWindowStart))[j].getRowspan() - 1 < maxRowGroupFinish) {
/*      */             
/*  935 */             rowGroupComplete = false;
/*      */           } 
/*      */         } 
/*      */       } 
/*  939 */       if (rowGroupComplete) {
/*  940 */         rowGroups.add(new RowRange(currentRowGroupStart, maxRowGroupFinish));
/*      */       }
/*  942 */       currentRowGroupStart = maxRowGroupFinish + 1;
/*      */     } 
/*      */     
/*  945 */     return rowGroups;
/*      */   }
/*      */   
/*      */   private void initializeRows() {
/*  949 */     this.rows = (List)new ArrayList<>();
/*  950 */     this.currentColumn = -1;
/*      */   }
/*      */   
/*      */   private boolean cellBelongsToAnyRowGroup(Cell cell, List<RowRange> rowGroups) {
/*  954 */     return (rowGroups != null && rowGroups.size() > 0 && cell.getRow() >= ((RowRange)rowGroups.get(0)).getStartRow() && cell
/*  955 */       .getRow() <= ((RowRange)rowGroups.get(rowGroups.size() - 1)).getFinishRow());
/*      */   }
/*      */   
/*      */   private void ensureHeaderIsInitialized() {
/*  959 */     if (this.header == null) {
/*  960 */       this.header = new Table(this.columnWidths);
/*  961 */       UnitValue width = getWidth();
/*  962 */       if (width != null) this.header.setWidth(width); 
/*  963 */       this.header.getAccessibilityProperties().setRole("THead");
/*  964 */       if (hasOwnProperty(114)) {
/*  965 */         this.header.setBorderCollapse((BorderCollapsePropertyValue)getProperty(114));
/*      */       }
/*  967 */       if (hasOwnProperty(115)) {
/*  968 */         this.header.setHorizontalBorderSpacing(((Float)getProperty(115)).floatValue());
/*      */       }
/*  970 */       if (hasOwnProperty(116)) {
/*  971 */         this.header.setVerticalBorderSpacing(((Float)getProperty(116)).floatValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void ensureFooterIsInitialized() {
/*  977 */     if (this.footer == null) {
/*  978 */       this.footer = new Table(this.columnWidths);
/*  979 */       UnitValue width = getWidth();
/*  980 */       if (width != null) this.footer.setWidth(width); 
/*  981 */       this.footer.getAccessibilityProperties().setRole("TFoot");
/*  982 */       if (hasOwnProperty(114)) {
/*  983 */         this.footer.setBorderCollapse((BorderCollapsePropertyValue)getProperty(114));
/*      */       }
/*  985 */       if (hasOwnProperty(115)) {
/*  986 */         this.footer.setHorizontalBorderSpacing(((Float)getProperty(115)).floatValue());
/*      */       }
/*  988 */       if (hasOwnProperty(116)) {
/*  989 */         this.footer.setVerticalBorderSpacing(((Float)getProperty(116)).floatValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initializeLargeTable(boolean largeTable) {
/*  995 */     this.isComplete = !largeTable;
/*  996 */     if (largeTable) {
/*  997 */       setWidth(UnitValue.createPercentValue(100.0F));
/*  998 */       setFixedLayout();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class RowRange
/*      */   {
/*      */     int startRow;
/*      */ 
/*      */ 
/*      */     
/*      */     int finishRow;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public RowRange(int startRow, int finishRow) {
/* 1018 */       this.startRow = startRow;
/* 1019 */       this.finishRow = finishRow;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getStartRow() {
/* 1028 */       return this.startRow;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getFinishRow() {
/* 1037 */       return this.finishRow;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/Table.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */