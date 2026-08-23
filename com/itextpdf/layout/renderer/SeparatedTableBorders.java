/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.element.Cell;
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
/*     */ class SeparatedTableBorders
/*     */   extends TableBorders
/*     */ {
/*     */   public SeparatedTableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders) {
/*  59 */     super(rows, numberOfColumns, tableBoundingBorders);
/*     */   }
/*     */   
/*     */   public SeparatedTableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders, int largeTableIndexOffset) {
/*  63 */     super(rows, numberOfColumns, tableBoundingBorders, largeTableIndexOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders drawHorizontalBorder(int i, float startX, float y1, PdfCanvas canvas, float[] countedColumnWidth) {
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders drawVerticalBorder(int i, float startY, float x1, PdfCanvas canvas, List<Float> heights) {
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders applyTopTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean isEmpty, boolean force, boolean reverse) {
/*  78 */     return applyTopTableBorder(occupiedBox, layoutBox, reverse);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders applyTopTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean reverse) {
/*  83 */     float topIndent = (reverse ? -1 : true) * getMaxTopWidth();
/*  84 */     layoutBox.decreaseHeight(topIndent);
/*  85 */     occupiedBox.moveDown(topIndent).increaseHeight(topIndent);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders applyBottomTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean isEmpty, boolean force, boolean reverse) {
/*  91 */     return applyBottomTableBorder(occupiedBox, layoutBox, reverse);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders applyBottomTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean reverse) {
/*  96 */     float bottomTableBorderWidth = (reverse ? -1 : true) * getMaxBottomWidth();
/*  97 */     layoutBox.decreaseHeight(bottomTableBorderWidth);
/*  98 */     occupiedBox.moveDown(bottomTableBorderWidth).increaseHeight(bottomTableBorderWidth);
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders applyLeftAndRightTableBorder(Rectangle layoutBox, boolean reverse) {
/* 104 */     if (null != layoutBox) {
/* 105 */       layoutBox.applyMargins(0.0F, this.rightBorderMaxWidth, 0.0F, this.leftBorderMaxWidth, reverse);
/*     */     }
/*     */     
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders skipFooter(Border[] borders) {
/* 113 */     setTableBoundingBorders(borders);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders skipHeader(Border[] borders) {
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders collapseTableWithFooter(TableBorders footerBordersHandler, boolean hasContent) {
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders collapseTableWithHeader(TableBorders headerBordersHandler, boolean updateBordersHandler) {
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders fixHeaderOccupiedArea(Rectangle occupiedBox, Rectangle layoutBox) {
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders applyCellIndents(Rectangle box, float topIndent, float rightIndent, float bottomIndent, float leftIndent, boolean reverse) {
/* 139 */     box.applyMargins(topIndent, rightIndent, bottomIndent, leftIndent, false);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Border> getVerticalBorder(int index) {
/* 145 */     return this.verticalBorders.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Border> getHorizontalBorder(int index) {
/* 150 */     return this.horizontalBorders.get(index - this.largeTableIndexOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getCellVerticalAddition(float[] indents) {
/* 155 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders updateBordersOnNewPage(boolean isOriginalNonSplitRenderer, boolean isFooterOrHeader, TableRenderer currentRenderer, TableRenderer headerRenderer, TableRenderer footerRenderer) {
/* 160 */     if (!isFooterOrHeader)
/*     */     {
/* 162 */       if (isOriginalNonSplitRenderer && 
/* 163 */         null != this.rows) {
/* 164 */         processAllBordersAndEmptyRows();
/* 165 */         this.rightBorderMaxWidth = getMaxRightWidth();
/* 166 */         this.leftBorderMaxWidth = getMaxLeftWidth();
/*     */       } 
/*     */     }
/*     */     
/* 170 */     if (null != footerRenderer) {
/* 171 */       float rightFooterBorderWidth = footerRenderer.bordersHandler.getMaxRightWidth();
/* 172 */       float leftFooterBorderWidth = footerRenderer.bordersHandler.getMaxLeftWidth();
/*     */       
/* 174 */       this.leftBorderMaxWidth = Math.max(this.leftBorderMaxWidth, leftFooterBorderWidth);
/* 175 */       this.rightBorderMaxWidth = Math.max(this.rightBorderMaxWidth, rightFooterBorderWidth);
/*     */     } 
/*     */     
/* 178 */     if (null != headerRenderer) {
/* 179 */       float rightHeaderBorderWidth = headerRenderer.bordersHandler.getMaxRightWidth();
/* 180 */       float leftHeaderBorderWidth = headerRenderer.bordersHandler.getMaxLeftWidth();
/*     */       
/* 182 */       this.leftBorderMaxWidth = Math.max(this.leftBorderMaxWidth, leftHeaderBorderWidth);
/* 183 */       this.rightBorderMaxWidth = Math.max(this.rightBorderMaxWidth, rightHeaderBorderWidth);
/*     */     } 
/*     */     
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getCellBorderIndents(int row, int col, int rowspan, int colspan) {
/* 192 */     float[] indents = new float[4];
/* 193 */     Border[] borders = ((CellRenderer[])this.rows.get(row + this.startRow - this.largeTableIndexOffset))[col].getBorders();
/*     */     
/* 195 */     for (int i = 0; i < 4; i++) {
/* 196 */       if (null != borders[i]) {
/* 197 */         indents[i] = borders[i].getWidth();
/*     */       }
/*     */     } 
/*     */     
/* 201 */     return indents;
/*     */   }
/*     */   
/*     */   protected void buildBordersArrays(CellRenderer cell, int row, int col, int[] rowspansToDeduct) {
/* 205 */     int colspan = cell.getPropertyAsInteger(16).intValue();
/* 206 */     int rowspan = cell.getPropertyAsInteger(60).intValue();
/* 207 */     int colN = ((Cell)cell.getModelElement()).getCol();
/* 208 */     Border[] cellBorders = cell.getBorders();
/*     */ 
/*     */     
/* 211 */     if (row + 1 - rowspan < 0) {
/* 212 */       rowspan = row + 1;
/*     */     }
/*     */     
/*     */     int k;
/* 216 */     for (k = 0; k < colspan; k++) {
/* 217 */       checkAndReplaceBorderInArray(this.horizontalBorders, 2 * (row + 1 - rowspan), colN + k, cellBorders[0], false);
/*     */     }
/*     */     
/* 220 */     for (k = 0; k < colspan; k++) {
/* 221 */       checkAndReplaceBorderInArray(this.horizontalBorders, 2 * row + 1, colN + k, cellBorders[2], true);
/*     */     }
/*     */     
/* 224 */     for (int j = row - rowspan + 1; j <= row; j++) {
/* 225 */       checkAndReplaceBorderInArray(this.verticalBorders, 2 * colN, j, cellBorders[3], false);
/*     */     }
/*     */     
/* 228 */     for (int i = row - rowspan + 1; i <= row; i++) {
/* 229 */       checkAndReplaceBorderInArray(this.verticalBorders, 2 * (colN + colspan) - 1, i, cellBorders[1], true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkAndReplaceBorderInArray(List<List<Border>> borderArray, int i, int j, Border borderToAdd, boolean hasPriority) {
/* 239 */     List<Border> borders = borderArray.get(i);
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
/* 256 */     Border neighbour = borders.get(j);
/* 257 */     if (neighbour == null) {
/* 258 */       borders.set(j, borderToAdd);
/*     */     } else {
/* 260 */       Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 261 */       logger.warn("Unexpected behaviour during table row collapsing. Calculated rowspan was less then 1.");
/*     */     } 
/*     */     
/* 264 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableBorders initializeBorders() {
/* 271 */     while (2 * Math.max(this.numberOfColumns, 1) > this.verticalBorders.size()) {
/* 272 */       List<Border> tempBorders = new ArrayList<>();
/* 273 */       while (2 * Math.max(this.rows.size(), 1) > tempBorders.size()) {
/* 274 */         tempBorders.add(null);
/*     */       }
/* 276 */       this.verticalBorders.add(tempBorders);
/*     */     } 
/*     */     
/* 279 */     while (2 * Math.max(this.rows.size(), 1) > this.horizontalBorders.size()) {
/* 280 */       List<Border> tempBorders = new ArrayList<>();
/* 281 */       while (this.numberOfColumns > tempBorders.size()) {
/* 282 */         tempBorders.add(null);
/*     */       }
/* 284 */       this.horizontalBorders.add(tempBorders);
/*     */     } 
/* 286 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Border> getFirstHorizontalBorder() {
/* 291 */     return getHorizontalBorder(2 * this.startRow);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Border> getLastHorizontalBorder() {
/* 296 */     return getHorizontalBorder(2 * this.finishRow + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxTopWidth() {
/* 301 */     return (null == this.tableBoundingBorders[0]) ? 0.0F : this.tableBoundingBorders[0].getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxBottomWidth() {
/* 306 */     return (null == this.tableBoundingBorders[2]) ? 0.0F : this.tableBoundingBorders[2].getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxRightWidth() {
/* 311 */     return (null == this.tableBoundingBorders[1]) ? 0.0F : this.tableBoundingBorders[1].getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxLeftWidth() {
/* 316 */     return (null == this.tableBoundingBorders[3]) ? 0.0F : this.tableBoundingBorders[3].getWidth();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/SeparatedTableBorders.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */