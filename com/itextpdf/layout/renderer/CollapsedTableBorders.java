/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.element.Cell;
/*     */ import com.itextpdf.layout.element.Table;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ class CollapsedTableBorders
/*     */   extends TableBorders
/*     */ {
/*  60 */   private List<Border> topBorderCollapseWith = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private List<Border> bottomBorderCollapseWith = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public CollapsedTableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders) {
/*  70 */     super(rows, numberOfColumns, tableBoundingBorders);
/*     */   }
/*     */   
/*     */   public CollapsedTableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders, int largeTableIndexOffset) {
/*  74 */     super(rows, numberOfColumns, tableBoundingBorders, largeTableIndexOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Border> getTopBorderCollapseWith() {
/*  80 */     return this.topBorderCollapseWith;
/*     */   }
/*     */   
/*     */   public List<Border> getBottomBorderCollapseWith() {
/*  84 */     return this.bottomBorderCollapseWith;
/*     */   }
/*     */   
/*     */   public float[] getCellBorderIndents(int row, int col, int rowspan, int colspan) {
/*  88 */     float[] indents = new float[4];
/*     */ 
/*     */ 
/*     */     
/*  92 */     List<Border> borderList = getHorizontalBorder(this.startRow + row - rowspan + 1); int i;
/*  93 */     for (i = col; i < col + colspan; i++) {
/*  94 */       Border border = borderList.get(i);
/*  95 */       if (null != border && border.getWidth() > indents[0]) {
/*  96 */         indents[0] = border.getWidth();
/*     */       }
/*     */     } 
/*     */     
/* 100 */     borderList = getVerticalBorder(col + colspan);
/* 101 */     for (i = this.startRow - this.largeTableIndexOffset + row - rowspan + 1; i < this.startRow - this.largeTableIndexOffset + row + 1; i++) {
/* 102 */       Border border = borderList.get(i);
/* 103 */       if (null != border && border.getWidth() > indents[1]) {
/* 104 */         indents[1] = border.getWidth();
/*     */       }
/*     */     } 
/*     */     
/* 108 */     borderList = getHorizontalBorder(this.startRow + row + 1);
/* 109 */     for (i = col; i < col + colspan; i++) {
/* 110 */       Border border = borderList.get(i);
/* 111 */       if (null != border && border.getWidth() > indents[2]) {
/* 112 */         indents[2] = border.getWidth();
/*     */       }
/*     */     } 
/*     */     
/* 116 */     borderList = getVerticalBorder(col);
/* 117 */     for (i = this.startRow - this.largeTableIndexOffset + row - rowspan + 1; i < this.startRow - this.largeTableIndexOffset + row + 1; i++) {
/* 118 */       Border border = borderList.get(i);
/* 119 */       if (null != border && border.getWidth() > indents[3]) {
/* 120 */         indents[3] = border.getWidth();
/*     */       }
/*     */     } 
/* 123 */     return indents;
/*     */   }
/*     */   
/*     */   public List<Border> getVerticalBorder(int index) {
/* 127 */     if (index == 0) {
/* 128 */       List<Border> borderList = TableBorderUtil.createAndFillBorderList(null, this.tableBoundingBorders[3], ((List)this.verticalBorders.get(0)).size());
/* 129 */       return getCollapsedList(this.verticalBorders.get(0), borderList);
/* 130 */     }  if (index == this.numberOfColumns) {
/* 131 */       List<Border> borderList = TableBorderUtil.createAndFillBorderList(null, this.tableBoundingBorders[1], ((List)this.verticalBorders.get(0)).size());
/* 132 */       return getCollapsedList(this.verticalBorders.get(this.verticalBorders.size() - 1), borderList);
/*     */     } 
/* 134 */     return this.verticalBorders.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Border> getHorizontalBorder(int index) {
/* 140 */     if (index == this.startRow) {
/* 141 */       List<Border> firstBorderOnCurrentPage = TableBorderUtil.createAndFillBorderList(this.topBorderCollapseWith, this.tableBoundingBorders[0], this.numberOfColumns);
/* 142 */       if (index == this.largeTableIndexOffset) {
/* 143 */         return getCollapsedList(this.horizontalBorders.get(index - this.largeTableIndexOffset), firstBorderOnCurrentPage);
/*     */       }
/* 145 */       if (0 != this.rows.size()) {
/* 146 */         int col = 0;
/* 147 */         int row = index;
/* 148 */         while (col < this.numberOfColumns) {
/* 149 */           if (null != ((CellRenderer[])this.rows.get(row - this.largeTableIndexOffset))[col] && row - index + 1 <= (
/* 150 */             (Cell)((CellRenderer[])this.rows.get(row - this.largeTableIndexOffset))[col].getModelElement()).getRowspan()) {
/* 151 */             CellRenderer cell = ((CellRenderer[])this.rows.get(row - this.largeTableIndexOffset))[col];
/* 152 */             Border cellModelTopBorder = TableBorderUtil.getCellSideBorder((Cell)cell.getModelElement(), 13);
/* 153 */             int colspan = cell.getPropertyAsInteger(16).intValue();
/* 154 */             if (null == firstBorderOnCurrentPage.get(col) || (null != cellModelTopBorder && cellModelTopBorder.getWidth() > ((Border)firstBorderOnCurrentPage.get(col)).getWidth())) {
/* 155 */               for (int i = col; i < col + colspan; i++) {
/* 156 */                 firstBorderOnCurrentPage.set(i, cellModelTopBorder);
/*     */               }
/*     */             }
/* 159 */             col += colspan;
/* 160 */             row = index; continue;
/*     */           } 
/* 162 */           row++;
/* 163 */           if (row == this.rows.size()) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 169 */       return firstBorderOnCurrentPage;
/*     */     } 
/* 171 */     if (index == this.finishRow + 1) {
/* 172 */       List<Border> lastBorderOnCurrentPage = TableBorderUtil.createAndFillBorderList(this.bottomBorderCollapseWith, this.tableBoundingBorders[2], this.numberOfColumns);
/* 173 */       if (index - this.largeTableIndexOffset == this.horizontalBorders.size() - 1) {
/* 174 */         return getCollapsedList(this.horizontalBorders.get(index - this.largeTableIndexOffset), lastBorderOnCurrentPage);
/*     */       }
/* 176 */       if (0 != this.rows.size()) {
/* 177 */         int col = 0;
/* 178 */         int row = index - 1;
/* 179 */         while (col < this.numberOfColumns) {
/* 180 */           if (null != ((CellRenderer[])this.rows.get(row - this.largeTableIndexOffset))[col]) {
/* 181 */             CellRenderer cell = ((CellRenderer[])this.rows.get(row - this.largeTableIndexOffset))[col];
/* 182 */             Border cellModelBottomBorder = TableBorderUtil.getCellSideBorder((Cell)cell.getModelElement(), 10);
/* 183 */             int colspan = cell.getPropertyAsInteger(16).intValue();
/* 184 */             if (null == lastBorderOnCurrentPage.get(col) || (null != cellModelBottomBorder && cellModelBottomBorder.getWidth() > ((Border)lastBorderOnCurrentPage.get(col)).getWidth())) {
/* 185 */               for (int i = col; i < col + colspan; i++) {
/* 186 */                 lastBorderOnCurrentPage.set(i, cellModelBottomBorder);
/*     */               }
/*     */             }
/* 189 */             col += colspan;
/* 190 */             row = index - 1; continue;
/*     */           } 
/* 192 */           row++;
/* 193 */           if (row == this.rows.size()) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 199 */       return lastBorderOnCurrentPage;
/*     */     } 
/* 201 */     return this.horizontalBorders.get(index - this.largeTableIndexOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollapsedTableBorders setTopBorderCollapseWith(List<Border> topBorderCollapseWith) {
/* 208 */     this.topBorderCollapseWith = new ArrayList<>();
/* 209 */     if (null != topBorderCollapseWith) {
/* 210 */       this.topBorderCollapseWith.addAll(topBorderCollapseWith);
/*     */     }
/* 212 */     return this;
/*     */   }
/*     */   
/*     */   public CollapsedTableBorders setBottomBorderCollapseWith(List<Border> bottomBorderCollapseWith) {
/* 216 */     this.bottomBorderCollapseWith = new ArrayList<>();
/* 217 */     if (null != bottomBorderCollapseWith) {
/* 218 */       this.bottomBorderCollapseWith.addAll(bottomBorderCollapseWith);
/*     */     }
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void buildBordersArrays(CellRenderer cell, int row, int col, int[] rowspansToDeduct) {
/* 227 */     if (row > this.horizontalBorders.size()) {
/* 228 */       row--;
/*     */     }
/* 230 */     int currCellColspan = cell.getPropertyAsInteger(16).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     if (col != 0 && null == ((CellRenderer[])this.rows.get(row))[col - 1]) {
/* 237 */       int nextCellRow, i = col;
/*     */       do {
/* 239 */         i--;
/* 240 */         nextCellRow = row;
/* 241 */         while (this.rows.size() != nextCellRow && null == ((CellRenderer[])this.rows.get(nextCellRow))[i]) {
/* 242 */           nextCellRow++;
/*     */         }
/*     */       }
/* 245 */       while (i > 0 && this.rows.size() != nextCellRow && (i + ((CellRenderer[])this.rows
/* 246 */         .get(nextCellRow))[i].getPropertyAsInteger(16).intValue() != col || nextCellRow - ((CellRenderer[])this.rows
/* 247 */         .get(nextCellRow))[i].getPropertyAsInteger(60).intValue() + 1 + rowspansToDeduct[i] != row));
/*     */       
/* 249 */       if (i >= 0 && nextCellRow != this.rows.size() && nextCellRow > row) {
/* 250 */         CellRenderer nextCell = ((CellRenderer[])this.rows.get(nextCellRow))[i];
/* 251 */         nextCell.setProperty(60, Integer.valueOf(nextCell.getPropertyAsInteger(60).intValue() - rowspansToDeduct[i]));
/* 252 */         int nextCellColspan = nextCell.getPropertyAsInteger(16).intValue();
/* 253 */         for (int k = i; k < i + nextCellColspan; k++) {
/* 254 */           rowspansToDeduct[k] = 0;
/*     */         }
/* 256 */         buildBordersArrays(nextCell, nextCellRow, true);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 261 */     int j = 0;
/* 262 */     while (j < currCellColspan) {
/* 263 */       int nextCellRow = row + 1;
/* 264 */       while (nextCellRow < this.rows.size() && null == ((CellRenderer[])this.rows.get(nextCellRow))[col + j]) {
/* 265 */         nextCellRow++;
/*     */       }
/* 267 */       if (nextCellRow == this.rows.size()) {
/*     */         break;
/*     */       }
/* 270 */       CellRenderer nextCell = ((CellRenderer[])this.rows.get(nextCellRow))[col + j];
/*     */       
/* 272 */       if (row == nextCellRow - nextCell.getPropertyAsInteger(60).intValue()) {
/* 273 */         buildBordersArrays(nextCell, nextCellRow, true);
/*     */       }
/* 275 */       j += nextCell.getPropertyAsInteger(16).intValue();
/*     */     } 
/*     */ 
/*     */     
/* 279 */     if (col + currCellColspan < ((CellRenderer[])this.rows.get(row)).length) {
/* 280 */       int nextCellRow = row;
/* 281 */       while (nextCellRow < this.rows.size() && null == ((CellRenderer[])this.rows.get(nextCellRow))[col + currCellColspan]) {
/* 282 */         nextCellRow++;
/*     */       }
/* 284 */       if (nextCellRow != this.rows.size()) {
/* 285 */         CellRenderer nextCell = ((CellRenderer[])this.rows.get(nextCellRow))[col + currCellColspan];
/* 286 */         nextCell.setProperty(60, Integer.valueOf(nextCell.getPropertyAsInteger(60).intValue() - rowspansToDeduct[col + currCellColspan]));
/* 287 */         int nextCellColspan = nextCell.getPropertyAsInteger(16).intValue();
/* 288 */         for (int i = col + currCellColspan; i < col + currCellColspan + nextCellColspan; i++) {
/* 289 */           rowspansToDeduct[i] = 0;
/*     */         }
/* 291 */         buildBordersArrays(nextCell, nextCellRow, true);
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     buildBordersArrays(cell, row, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void buildBordersArrays(CellRenderer cell, int row, boolean isNeighbourCell) {
/* 300 */     int colspan = cell.getPropertyAsInteger(16).intValue();
/* 301 */     int rowspan = cell.getPropertyAsInteger(60).intValue();
/* 302 */     int colN = ((Cell)cell.getModelElement()).getCol();
/* 303 */     Border[] cellBorders = cell.getBorders();
/*     */ 
/*     */     
/* 306 */     if (row + 1 - rowspan < 0) {
/* 307 */       rowspan = row + 1;
/*     */     }
/*     */     
/*     */     int k;
/* 311 */     for (k = 0; k < colspan; k++) {
/* 312 */       checkAndReplaceBorderInArray(this.horizontalBorders, row + 1 - rowspan, colN + k, cellBorders[0], false);
/*     */     }
/*     */     
/* 315 */     for (k = 0; k < colspan; k++) {
/* 316 */       checkAndReplaceBorderInArray(this.horizontalBorders, row + 1, colN + k, cellBorders[2], true);
/*     */     }
/*     */     
/* 319 */     for (int j = row - rowspan + 1; j <= row; j++) {
/* 320 */       checkAndReplaceBorderInArray(this.verticalBorders, colN, j, cellBorders[3], false);
/*     */     }
/*     */     
/* 323 */     for (int i = row - rowspan + 1; i <= row; i++) {
/* 324 */       checkAndReplaceBorderInArray(this.verticalBorders, colN + colspan, i, cellBorders[1], true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkAndReplaceBorderInArray(List<List<Border>> borderArray, int i, int j, Border borderToAdd, boolean hasPriority) {
/* 336 */     List<Border> borders = borderArray.get(i);
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
/* 353 */     Border neighbour = borders.get(j);
/* 354 */     if (neighbour == null) {
/* 355 */       borders.set(j, borderToAdd);
/* 356 */       return true;
/*     */     } 
/* 358 */     if (neighbour != borderToAdd && 
/* 359 */       borderToAdd != null && neighbour.getWidth() <= borderToAdd.getWidth()) {
/* 360 */       if (!hasPriority && neighbour.getWidth() == borderToAdd.getWidth()) {
/* 361 */         return false;
/*     */       }
/* 363 */       borders.set(j, borderToAdd);
/* 364 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 369 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableBorders drawHorizontalBorder(int i, float startX, float y1, PdfCanvas canvas, float[] countedColumnWidth) {
/* 375 */     List<Border> borders = getHorizontalBorder(this.startRow + i);
/* 376 */     float x1 = startX;
/* 377 */     float x2 = x1 + countedColumnWidth[0];
/* 378 */     if (i == 0) {
/* 379 */       Border firstBorder = getFirstVerticalBorder().get(this.startRow - this.largeTableIndexOffset);
/* 380 */       if (firstBorder != null) {
/* 381 */         x1 -= firstBorder.getWidth() / 2.0F;
/*     */       }
/* 383 */     } else if (i == this.finishRow - this.startRow + 1) {
/* 384 */       Border firstBorder = getFirstVerticalBorder().get(this.startRow - this.largeTableIndexOffset + this.finishRow - this.startRow + 1 - 1);
/* 385 */       if (firstBorder != null) {
/* 386 */         x1 -= firstBorder.getWidth() / 2.0F;
/*     */       }
/*     */     } 
/*     */     
/*     */     int j;
/* 391 */     for (j = 1; j < borders.size(); j++) {
/* 392 */       Border prevBorder = borders.get(j - 1);
/* 393 */       Border curBorder = borders.get(j);
/* 394 */       if (prevBorder != null) {
/* 395 */         if (!prevBorder.equals(curBorder)) {
/* 396 */           prevBorder.drawCellBorder(canvas, x1, y1, x2, y1, Border.Side.NONE);
/* 397 */           x1 = x2;
/*     */         } 
/*     */       } else {
/* 400 */         x1 += countedColumnWidth[j - 1];
/* 401 */         x2 = x1;
/*     */       } 
/* 403 */       if (curBorder != null) {
/* 404 */         x2 += countedColumnWidth[j];
/*     */       }
/*     */     } 
/*     */     
/* 408 */     Border lastBorder = (borders.size() > j - 1) ? borders.get(j - 1) : null;
/* 409 */     if (lastBorder != null) {
/* 410 */       if (i == 0) {
/* 411 */         if (getVerticalBorder(j).get(this.startRow - this.largeTableIndexOffset + i) != null)
/* 412 */           x2 += ((Border)getVerticalBorder(j).get(this.startRow - this.largeTableIndexOffset + i)).getWidth() / 2.0F; 
/* 413 */       } else if (i == this.finishRow - this.startRow + 1 && getVerticalBorder(j).size() > this.startRow - this.largeTableIndexOffset + i - 1 && getVerticalBorder(j).get(this.startRow - this.largeTableIndexOffset + i - 1) != null) {
/* 414 */         x2 += ((Border)getVerticalBorder(j).get(this.startRow - this.largeTableIndexOffset + i - 1)).getWidth() / 2.0F;
/*     */       } 
/*     */       
/* 417 */       lastBorder.drawCellBorder(canvas, x1, y1, x2, y1, Border.Side.NONE);
/*     */     } 
/* 419 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders drawVerticalBorder(int i, float startY, float x1, PdfCanvas canvas, List<Float> heights) {
/* 423 */     List<Border> borders = getVerticalBorder(i);
/* 424 */     float y1 = startY;
/* 425 */     float y2 = y1;
/* 426 */     if (!heights.isEmpty()) {
/* 427 */       y2 = y1 - ((Float)heights.get(0)).floatValue();
/*     */     }
/*     */     int j;
/* 430 */     for (j = 1; j < heights.size(); j++) {
/* 431 */       Border prevBorder = borders.get(this.startRow - this.largeTableIndexOffset + j - 1);
/* 432 */       Border curBorder = borders.get(this.startRow - this.largeTableIndexOffset + j);
/* 433 */       if (prevBorder != null) {
/* 434 */         if (!prevBorder.equals(curBorder)) {
/* 435 */           prevBorder.drawCellBorder(canvas, x1, y1, x1, y2, Border.Side.NONE);
/* 436 */           y1 = y2;
/*     */         } 
/*     */       } else {
/* 439 */         y1 -= ((Float)heights.get(j - 1)).floatValue();
/* 440 */         y2 = y1;
/*     */       } 
/* 442 */       if (curBorder != null) {
/* 443 */         y2 -= ((Float)heights.get(j)).floatValue();
/*     */       }
/*     */     } 
/* 446 */     if (borders.size() == 0) {
/* 447 */       return this;
/*     */     }
/* 449 */     Border lastBorder = borders.get(this.startRow - this.largeTableIndexOffset + j - 1);
/* 450 */     if (lastBorder != null) {
/* 451 */       lastBorder.drawCellBorder(canvas, x1, y1, x1, y2, Border.Side.NONE);
/*     */     }
/* 453 */     return this;
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
/*     */   public static Border getCollapsedBorder(Border cellBorder, Border tableBorder) {
/* 468 */     if (null != tableBorder && (
/* 469 */       null == cellBorder || cellBorder.getWidth() < tableBorder.getWidth())) {
/* 470 */       return tableBorder;
/*     */     }
/*     */     
/* 473 */     if (null != cellBorder) {
/* 474 */       return cellBorder;
/*     */     }
/* 476 */     return Border.NO_BORDER;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Border> getCollapsedList(List<Border> innerList, List<Border> outerList) {
/* 481 */     int size = Math.min((null == innerList) ? 0 : innerList.size(), (null == outerList) ? 0 : outerList.size());
/* 482 */     List<Border> collapsedList = new ArrayList<>();
/* 483 */     for (int i = 0; i < size; i++) {
/* 484 */       collapsedList.add(getCollapsedBorder(innerList.get(i), outerList.get(i)));
/*     */     }
/* 486 */     return collapsedList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableBorders applyLeftAndRightTableBorder(Rectangle layoutBox, boolean reverse) {
/* 493 */     if (null != layoutBox) {
/* 494 */       layoutBox.applyMargins(0.0F, this.rightBorderMaxWidth / 2.0F, 0.0F, this.leftBorderMaxWidth / 2.0F, reverse);
/*     */     }
/*     */     
/* 497 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders applyTopTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean isEmpty, boolean force, boolean reverse) {
/* 501 */     if (!isEmpty)
/* 502 */       return applyTopTableBorder(occupiedBox, layoutBox, reverse); 
/* 503 */     if (force) {
/*     */       
/* 505 */       applyTopTableBorder(occupiedBox, layoutBox, reverse);
/* 506 */       return applyTopTableBorder(occupiedBox, layoutBox, reverse);
/*     */     } 
/* 508 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders applyBottomTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean isEmpty, boolean force, boolean reverse) {
/* 512 */     if (!isEmpty)
/* 513 */       return applyBottomTableBorder(occupiedBox, layoutBox, reverse); 
/* 514 */     if (force) {
/*     */       
/* 516 */       applyBottomTableBorder(occupiedBox, layoutBox, reverse);
/* 517 */       return applyBottomTableBorder(occupiedBox, layoutBox, reverse);
/*     */     } 
/* 519 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders applyTopTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean reverse) {
/* 523 */     float topIndent = (reverse ? -1 : true) * getMaxTopWidth();
/* 524 */     layoutBox.decreaseHeight(topIndent / 2.0F);
/* 525 */     occupiedBox.moveDown(topIndent / 2.0F).increaseHeight(topIndent / 2.0F);
/* 526 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders applyBottomTableBorder(Rectangle occupiedBox, Rectangle layoutBox, boolean reverse) {
/* 530 */     float bottomTableBorderWidth = (reverse ? -1 : true) * getMaxBottomWidth();
/* 531 */     layoutBox.decreaseHeight(bottomTableBorderWidth / 2.0F);
/* 532 */     occupiedBox.moveDown(bottomTableBorderWidth / 2.0F).increaseHeight(bottomTableBorderWidth / 2.0F);
/* 533 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TableBorders applyCellIndents(Rectangle box, float topIndent, float rightIndent, float bottomIndent, float leftIndent, boolean reverse) {
/* 538 */     box.applyMargins(topIndent / 2.0F, rightIndent / 2.0F, bottomIndent / 2.0F, leftIndent / 2.0F, false);
/* 539 */     return this;
/*     */   }
/*     */   
/*     */   protected float getCellVerticalAddition(float[] indents) {
/* 543 */     return indents[0] / 2.0F + indents[2] / 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableBorders updateBordersOnNewPage(boolean isOriginalNonSplitRenderer, boolean isFooterOrHeader, TableRenderer currentRenderer, TableRenderer headerRenderer, TableRenderer footerRenderer) {
/* 549 */     if (!isFooterOrHeader)
/*     */     {
/* 551 */       if (isOriginalNonSplitRenderer) {
/* 552 */         if (null != this.rows) {
/* 553 */           processAllBordersAndEmptyRows();
/* 554 */           this.rightBorderMaxWidth = getMaxRightWidth();
/* 555 */           this.leftBorderMaxWidth = getMaxLeftWidth();
/*     */         } 
/* 557 */         setTopBorderCollapseWith(((Table)currentRenderer.getModelElement()).getLastRowBottomBorder());
/*     */       } else {
/* 559 */         setTopBorderCollapseWith((List<Border>)null);
/* 560 */         setBottomBorderCollapseWith((List<Border>)null);
/*     */       } 
/*     */     }
/* 563 */     if (null != footerRenderer) {
/* 564 */       float rightFooterBorderWidth = footerRenderer.bordersHandler.getMaxRightWidth();
/* 565 */       float leftFooterBorderWidth = footerRenderer.bordersHandler.getMaxLeftWidth();
/*     */       
/* 567 */       this.leftBorderMaxWidth = Math.max(this.leftBorderMaxWidth, leftFooterBorderWidth);
/* 568 */       this.rightBorderMaxWidth = Math.max(this.rightBorderMaxWidth, rightFooterBorderWidth);
/*     */     } 
/*     */     
/* 571 */     if (null != headerRenderer) {
/* 572 */       float rightHeaderBorderWidth = headerRenderer.bordersHandler.getMaxRightWidth();
/* 573 */       float leftHeaderBorderWidth = headerRenderer.bordersHandler.getMaxLeftWidth();
/*     */       
/* 575 */       this.leftBorderMaxWidth = Math.max(this.leftBorderMaxWidth, leftHeaderBorderWidth);
/* 576 */       this.rightBorderMaxWidth = Math.max(this.rightBorderMaxWidth, rightHeaderBorderWidth);
/*     */     } 
/*     */     
/* 579 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders skipFooter(Border[] borders) {
/* 583 */     setTableBoundingBorders(borders);
/* 584 */     setBottomBorderCollapseWith((List<Border>)null);
/* 585 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders skipHeader(Border[] borders) {
/* 589 */     setTableBoundingBorders(borders);
/* 590 */     setTopBorderCollapseWith((List<Border>)null);
/* 591 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders collapseTableWithFooter(TableBorders footerBordersHandler, boolean hasContent) {
/* 595 */     ((CollapsedTableBorders)footerBordersHandler).setTopBorderCollapseWith(hasContent ? getLastHorizontalBorder() : getTopBorderCollapseWith());
/* 596 */     setBottomBorderCollapseWith(footerBordersHandler.getHorizontalBorder(0));
/* 597 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders collapseTableWithHeader(TableBorders headerBordersHandler, boolean updateBordersHandler) {
/* 601 */     ((CollapsedTableBorders)headerBordersHandler).setBottomBorderCollapseWith(getHorizontalBorder(this.startRow));
/* 602 */     if (updateBordersHandler) {
/* 603 */       setTopBorderCollapseWith(headerBordersHandler.getLastHorizontalBorder());
/*     */     }
/* 605 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders fixHeaderOccupiedArea(Rectangle occupiedBox, Rectangle layoutBox) {
/* 609 */     float topBorderMaxWidth = getMaxTopWidth();
/* 610 */     layoutBox.increaseHeight(topBorderMaxWidth);
/* 611 */     occupiedBox.moveUp(topBorderMaxWidth).decreaseHeight(topBorderMaxWidth);
/* 612 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/CollapsedTableBorders.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */