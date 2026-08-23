/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.ArrayUtil;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.element.Cell;
/*     */ import com.itextpdf.layout.element.Table;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*     */ import com.itextpdf.layout.property.BorderCollapsePropertyValue;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ final class TableWidths
/*     */ {
/*     */   private final TableRenderer tableRenderer;
/*     */   private final int numberOfColumns;
/*     */   private final float rightBorderMaxWidth;
/*     */   private final float leftBorderMaxWidth;
/*     */   private final ColumnWidthData[] widths;
/*     */   private final float horizontalBorderSpacing;
/*     */   private List<CellInfo> cells;
/*     */   private float tableWidth;
/*     */   private boolean fixedTableWidth;
/*     */   private boolean fixedTableLayout = false;
/*     */   private float layoutMinWidth;
/*     */   private float tableMinWidth;
/*     */   private float tableMaxWidth;
/*     */   
/*     */   TableWidths(TableRenderer tableRenderer, float availableWidth, boolean calculateTableMaxWidth, float rightBorderMaxWidth, float leftBorderMaxWidth) {
/*  82 */     this.tableRenderer = tableRenderer;
/*  83 */     this.numberOfColumns = ((Table)tableRenderer.getModelElement()).getNumberOfColumns();
/*  84 */     this.widths = new ColumnWidthData[this.numberOfColumns];
/*  85 */     this.rightBorderMaxWidth = rightBorderMaxWidth;
/*  86 */     this.leftBorderMaxWidth = leftBorderMaxWidth;
/*  87 */     if (tableRenderer.bordersHandler instanceof SeparatedTableBorders) {
/*  88 */       Float horizontalSpacing = tableRenderer.getPropertyAsFloat(115);
/*  89 */       this.horizontalBorderSpacing = (null == horizontalSpacing) ? 0.0F : horizontalSpacing.floatValue();
/*     */     } else {
/*  91 */       this.horizontalBorderSpacing = 0.0F;
/*     */     } 
/*  93 */     calculateTableWidth(availableWidth, calculateTableMaxWidth);
/*     */   }
/*     */   
/*     */   boolean hasFixedLayout() {
/*  97 */     return this.fixedTableLayout;
/*     */   }
/*     */   
/*     */   float[] layout() {
/* 101 */     if (hasFixedLayout()) {
/* 102 */       return fixedLayout();
/*     */     }
/* 104 */     return autoLayout();
/*     */   }
/*     */ 
/*     */   
/*     */   float getMinWidth() {
/* 109 */     return this.layoutMinWidth;
/*     */   }
/*     */   
/*     */   float[] autoLayout() {
/* 113 */     assert this.tableRenderer.getTable().isComplete();
/* 114 */     fillAndSortCells();
/* 115 */     calculateMinMaxWidths();
/*     */     
/* 117 */     float minSum = 0.0F;
/* 118 */     for (ColumnWidthData width : this.widths) {
/* 119 */       minSum += width.min;
/*     */     }
/*     */     
/* 122 */     for (CellInfo cell : this.cells) {
/* 123 */       processCell(cell);
/*     */     }
/*     */     
/* 126 */     processColumns();
/*     */     
/* 128 */     recalculate(minSum);
/*     */     
/* 130 */     return extractWidths();
/*     */   }
/*     */   
/*     */   List<CellInfo> autoLayoutCustom() {
/* 134 */     assert this.tableRenderer.getTable().isComplete();
/* 135 */     fillAndSortCells();
/* 136 */     calculateMinMaxWidths();
/* 137 */     return this.cells;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void processCell(CellInfo cell) {
/* 144 */     UnitValue cellWidth = getCellWidth(cell.getCell(), false);
/* 145 */     if (cellWidth != null) {
/* 146 */       assert cellWidth.getValue() > 0.0F;
/* 147 */       if (cellWidth.isPercentValue()) {
/*     */ 
/*     */         
/* 150 */         if (cell.getColspan() == 1) {
/* 151 */           this.widths[cell.getCol()].setPercents(cellWidth.getValue());
/*     */         } else {
/* 153 */           int pointColumns = 0;
/* 154 */           float percentSum = 0.0F;
/* 155 */           for (int i = cell.getCol(); i < cell.getCol() + cell.getColspan(); i++) {
/* 156 */             if (!(this.widths[i]).isPercent) {
/* 157 */               pointColumns++;
/*     */             } else {
/* 159 */               percentSum += (this.widths[i]).width;
/*     */             } 
/*     */           } 
/* 162 */           float percentAddition = cellWidth.getValue() - percentSum;
/* 163 */           if (percentAddition > 0.0F) {
/* 164 */             if (pointColumns == 0)
/*     */             {
/*     */               
/* 167 */               for (int j = cell.getCol(); j < cell.getCol() + cell.getColspan(); j++) {
/* 168 */                 this.widths[j].addPercents(percentAddition / cell.getColspan());
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 173 */               for (int j = cell.getCol(); j < cell.getCol() + cell.getColspan(); j++) {
/* 174 */                 if (!(this.widths[j]).isPercent) {
/* 175 */                   this.widths[j].setPercents(percentAddition / pointColumns);
/*     */                 }
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */         }
/*     */       
/* 184 */       } else if (cell.getColspan() == 1) {
/* 185 */         if (!(this.widths[cell.getCol()]).isPercent) {
/* 186 */           if ((this.widths[cell.getCol()]).min <= cellWidth.getValue()) {
/* 187 */             this.widths[cell.getCol()].setPoints(cellWidth.getValue()).setFixed(true);
/*     */           } else {
/* 189 */             this.widths[cell.getCol()].setPoints((this.widths[cell.getCol()]).min);
/*     */           } 
/*     */         }
/*     */       } else {
/* 193 */         processCellsRemainWidth(cell, cellWidth);
/*     */       }
/*     */     
/* 196 */     } else if (this.widths[cell.getCol()].isFlexible()) {
/*     */ 
/*     */       
/* 199 */       int flexibleCols = 0;
/* 200 */       float remainWidth = 0.0F; int i;
/* 201 */       for (i = cell.getCol(); i < cell.getCol() + cell.getColspan(); i++) {
/* 202 */         if (this.widths[i].isFlexible()) {
/* 203 */           remainWidth += (this.widths[i]).max - (this.widths[i]).width;
/* 204 */           flexibleCols++;
/*     */         } 
/*     */       } 
/* 207 */       if (remainWidth > 0.0F)
/*     */       {
/*     */         
/* 210 */         for (i = cell.getCol(); i < cell.getCol() + cell.getColspan(); i++) {
/* 211 */           if (this.widths[i].isFlexible()) {
/* 212 */             this.widths[i].addPoints(remainWidth / flexibleCols);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void processColumns() {
/* 220 */     for (int i = 0; i < this.numberOfColumns; i++) {
/* 221 */       UnitValue colWidth = getTable().getColumnWidth(i);
/* 222 */       if (colWidth != null && colWidth.getValue() > 0.0F) {
/* 223 */         if (colWidth.isPercentValue()) {
/* 224 */           if (!(this.widths[i]).isPercent) {
/* 225 */             if ((this.widths[i]).isFixed && (this.widths[i]).width > (this.widths[i]).min) {
/* 226 */               (this.widths[i]).max = (this.widths[i]).width;
/*     */             }
/* 228 */             this.widths[i].setPercents(colWidth.getValue());
/*     */           } 
/* 230 */         } else if (!(this.widths[i]).isPercent && colWidth.getValue() >= (this.widths[i]).min) {
/* 231 */           if ((this.widths[i]).isFixed) {
/* 232 */             this.widths[i].setPoints(colWidth.getValue());
/*     */           } else {
/* 234 */             this.widths[i].resetPoints(colWidth.getValue()).setFixed(true);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void recalculate(float minSum) {
/* 242 */     if (this.tableWidth - minSum < 0.0F) {
/* 243 */       for (int i = 0; i < this.numberOfColumns; i++) {
/* 244 */         (this.widths[i]).finalWidth = (this.widths[i]).min;
/*     */       }
/*     */     } else {
/* 247 */       float sumOfPercents = 0.0F;
/*     */       
/* 249 */       float minTableWidth = 0.0F;
/* 250 */       float totalNonPercent = 0.0F;
/*     */ 
/*     */       
/* 253 */       for (int i = 0; i < this.widths.length; i++) {
/* 254 */         if ((this.widths[i]).isPercent) {
/* 255 */           if (sumOfPercents < 100.0F && sumOfPercents + (this.widths[i]).width > 100.0F) {
/* 256 */             (this.widths[i]).width = 100.0F - sumOfPercents;
/* 257 */             sumOfPercents += (this.widths[i]).width;
/* 258 */             warn100percent();
/* 259 */           } else if (sumOfPercents >= 100.0F) {
/* 260 */             this.widths[i].resetPoints((this.widths[i]).min);
/* 261 */             minTableWidth += (this.widths[i]).min;
/* 262 */             warn100percent();
/*     */           } else {
/* 264 */             sumOfPercents += (this.widths[i]).width;
/*     */           } 
/*     */         } else {
/* 267 */           minTableWidth += (this.widths[i]).min;
/* 268 */           totalNonPercent += (this.widths[i]).width;
/*     */         } 
/*     */       } 
/* 271 */       assert sumOfPercents <= 100.0F;
/*     */       
/* 273 */       boolean toBalance = true;
/* 274 */       if (!this.fixedTableWidth) {
/* 275 */         float tableWidthBasedOnPercents = (sumOfPercents < 100.0F) ? (totalNonPercent * 100.0F / (100.0F - sumOfPercents)) : 0.0F;
/*     */         
/* 277 */         for (int j = 0; j < this.numberOfColumns; j++) {
/* 278 */           if ((this.widths[j]).isPercent && (this.widths[j]).width > 0.0F) {
/* 279 */             tableWidthBasedOnPercents = Math.max((this.widths[j]).max * 100.0F / (this.widths[j]).width, tableWidthBasedOnPercents);
/*     */           }
/*     */         } 
/*     */         
/* 283 */         if (tableWidthBasedOnPercents <= this.tableWidth) {
/* 284 */           if (tableWidthBasedOnPercents >= minTableWidth) {
/* 285 */             this.tableWidth = tableWidthBasedOnPercents;
/*     */             
/* 287 */             toBalance = false;
/*     */           } else {
/* 289 */             this.tableWidth = minTableWidth;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 294 */       if (sumOfPercents > 0.0F && sumOfPercents < 100.0F && totalNonPercent == 0.0F) {
/*     */ 
/*     */         
/* 297 */         for (int j = 0; j < this.widths.length; j++) {
/* 298 */           (this.widths[j]).width = 100.0F * (this.widths[j]).width / sumOfPercents;
/*     */         }
/* 300 */         sumOfPercents = 100.0F;
/*     */       } 
/*     */       
/* 303 */       if (!toBalance) {
/*     */         
/* 305 */         for (int j = 0; j < this.numberOfColumns; j++) {
/* 306 */           (this.widths[j]).finalWidth = (this.widths[j]).isPercent ? (this.tableWidth * (this.widths[j]).width / 100.0F) : (this.widths[j]).width;
/*     */         
/*     */         }
/*     */       }
/* 310 */       else if (sumOfPercents >= 100.0F) {
/* 311 */         sumOfPercents = 100.0F;
/* 312 */         boolean recalculatePercents = false;
/* 313 */         float remainWidth = this.tableWidth - minTableWidth; int j;
/* 314 */         for (j = 0; j < this.numberOfColumns; j++) {
/* 315 */           if ((this.widths[j]).isPercent) {
/* 316 */             if (remainWidth * (this.widths[j]).width / 100.0F >= (this.widths[j]).min) {
/* 317 */               (this.widths[j]).finalWidth = remainWidth * (this.widths[j]).width / 100.0F;
/*     */             } else {
/* 319 */               (this.widths[j]).finalWidth = (this.widths[j]).min;
/* 320 */               (this.widths[j]).isPercent = false;
/* 321 */               remainWidth -= (this.widths[j]).min;
/* 322 */               sumOfPercents -= (this.widths[j]).width;
/* 323 */               recalculatePercents = true;
/*     */             } 
/*     */           } else {
/* 326 */             (this.widths[j]).finalWidth = (this.widths[j]).min;
/*     */           } 
/*     */         } 
/* 329 */         if (recalculatePercents) {
/* 330 */           for (j = 0; j < this.numberOfColumns; j++) {
/* 331 */             if ((this.widths[j]).isPercent) {
/* 332 */               (this.widths[j]).finalWidth = remainWidth * (this.widths[j]).width / sumOfPercents;
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 344 */         float totalPercent = 0.0F;
/* 345 */         float minTotalNonPercent = 0.0F;
/* 346 */         float fixedAddition = 0.0F;
/* 347 */         float flexibleAddition = 0.0F;
/* 348 */         boolean hasFlexibleCell = false;
/*     */         
/* 350 */         for (int j = 0; j < this.numberOfColumns; j++) {
/* 351 */           if ((this.widths[j]).isPercent) {
/* 352 */             if (this.tableWidth * (this.widths[j]).width / 100.0F >= (this.widths[j]).min) {
/* 353 */               (this.widths[j]).finalWidth = this.tableWidth * (this.widths[j]).width / 100.0F;
/* 354 */               totalPercent += (this.widths[j]).finalWidth;
/*     */             } else {
/* 356 */               sumOfPercents -= (this.widths[j]).width;
/* 357 */               this.widths[j].resetPoints((this.widths[j]).min);
/* 358 */               (this.widths[j]).finalWidth = (this.widths[j]).min;
/* 359 */               minTotalNonPercent += (this.widths[j]).min;
/*     */             } 
/*     */           } else {
/* 362 */             (this.widths[j]).finalWidth = (this.widths[j]).min;
/* 363 */             minTotalNonPercent += (this.widths[j]).min;
/* 364 */             float addition = (this.widths[j]).width - (this.widths[j]).min;
/* 365 */             if ((this.widths[j]).isFixed) {
/* 366 */               fixedAddition += addition;
/*     */             } else {
/* 368 */               flexibleAddition += addition;
/* 369 */               hasFlexibleCell = true;
/*     */             } 
/*     */           } 
/*     */         } 
/* 373 */         if (totalPercent + minTotalNonPercent > this.tableWidth) {
/*     */           
/* 375 */           float extraWidth = this.tableWidth - minTotalNonPercent;
/* 376 */           if (sumOfPercents > 0.0F) {
/* 377 */             for (int k = 0; k < this.numberOfColumns; k++) {
/* 378 */               if ((this.widths[k]).isPercent) {
/* 379 */                 (this.widths[k]).finalWidth = extraWidth * (this.widths[k]).width / sumOfPercents;
/*     */               }
/*     */             } 
/*     */           }
/*     */         } else {
/* 384 */           float extraWidth = this.tableWidth - totalPercent - minTotalNonPercent;
/* 385 */           if (fixedAddition > 0.0F && (extraWidth < fixedAddition || !hasFlexibleCell)) {
/* 386 */             for (int k = 0; k < this.numberOfColumns; k++) {
/*     */               
/* 388 */               if ((this.widths[k]).isFixed) {
/* 389 */                 (this.widths[k]).finalWidth += ((this.widths[k]).width - (this.widths[k]).min) * extraWidth / fixedAddition;
/*     */               }
/*     */             } 
/*     */           } else {
/* 393 */             extraWidth -= fixedAddition;
/* 394 */             if (extraWidth < flexibleAddition) {
/* 395 */               for (int k = 0; k < this.numberOfColumns; k++) {
/* 396 */                 if ((this.widths[k]).isFixed) {
/* 397 */                   (this.widths[k]).finalWidth = (this.widths[k]).width;
/* 398 */                 } else if (!(this.widths[k]).isPercent) {
/* 399 */                   (this.widths[k]).finalWidth += ((this.widths[k]).width - (this.widths[k]).min) * extraWidth / flexibleAddition;
/*     */                 } 
/*     */               } 
/*     */             } else {
/* 403 */               float totalFixed = 0.0F;
/* 404 */               float totalFlexible = 0.0F;
/* 405 */               float flexibleCount = 0.0F; int k;
/* 406 */               for (k = 0; k < this.numberOfColumns; k++) {
/* 407 */                 if ((this.widths[k]).isFixed) {
/* 408 */                   (this.widths[k]).finalWidth = (this.widths[k]).width;
/* 409 */                   totalFixed += (this.widths[k]).width;
/* 410 */                 } else if (!(this.widths[k]).isPercent) {
/* 411 */                   totalFlexible += (this.widths[k]).width;
/* 412 */                   flexibleCount++;
/*     */                 } 
/*     */               } 
/* 415 */               assert totalFlexible > 0.0F || flexibleCount > 0.0F;
/* 416 */               extraWidth = this.tableWidth - totalPercent - totalFixed;
/* 417 */               for (k = 0; k < this.numberOfColumns; k++) {
/* 418 */                 if (!(this.widths[k]).isPercent && !(this.widths[k]).isFixed) {
/* 419 */                   (this.widths[k]).finalWidth = (totalFlexible > 0.0F) ? ((this.widths[k]).width * extraWidth / totalFlexible) : (extraWidth / flexibleCount);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void processCellsRemainWidth(CellInfo cell, UnitValue cellWidth) {
/* 432 */     int flexibleCols = 0;
/* 433 */     float remainWidth = cellWidth.getValue();
/* 434 */     for (int i = cell.getCol(); i < cell.getCol() + cell.getColspan(); i++) {
/* 435 */       if (!(this.widths[i]).isPercent) {
/* 436 */         remainWidth -= (this.widths[i]).width;
/* 437 */         if (!(this.widths[i]).isFixed) {
/* 438 */           flexibleCols++;
/*     */         }
/*     */       } else {
/*     */         
/* 442 */         remainWidth = 0.0F;
/*     */         break;
/*     */       } 
/*     */     } 
/* 446 */     if (remainWidth > 0.0F) {
/* 447 */       int[] flexibleColIndexes = ArrayUtil.fillWithValue(new int[cell.getColspan()], -1);
/* 448 */       if (flexibleCols > 0) {
/*     */         int j;
/* 450 */         for (j = cell.getCol(); j < cell.getCol() + cell.getColspan(); j++) {
/* 451 */           if (this.widths[j].isFlexible())
/*     */           {
/* 453 */             if ((this.widths[j]).min > (this.widths[j]).width + remainWidth / flexibleCols) {
/* 454 */               this.widths[j].resetPoints((this.widths[j]).min);
/* 455 */               remainWidth -= (this.widths[j]).min - (this.widths[j]).width;
/* 456 */               flexibleCols--;
/* 457 */               if (flexibleCols == 0 || remainWidth <= 0.0F) {
/*     */                 break;
/*     */               }
/*     */             } else {
/* 461 */               flexibleColIndexes[j - cell.getCol()] = j;
/*     */             }  } 
/*     */         } 
/* 464 */         if (flexibleCols > 0 && remainWidth > 0.0F) {
/* 465 */           for (j = 0; j < flexibleColIndexes.length; j++) {
/* 466 */             if (flexibleColIndexes[j] >= 0) {
/* 467 */               this.widths[flexibleColIndexes[j]].addPoints(remainWidth / flexibleCols).setFixed(true);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } else {
/* 472 */         for (int j = cell.getCol(); j < cell.getCol() + cell.getColspan(); j++)
/* 473 */           this.widths[j].addPoints(remainWidth / cell.getColspan()); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   float[] fixedLayout() {
/*     */     CellRenderer[] firtsRow;
/* 480 */     float[] columnWidths = new float[this.numberOfColumns];
/*     */     
/* 482 */     for (int i = 0; i < this.numberOfColumns; i++) {
/* 483 */       UnitValue colWidth = getTable().getColumnWidth(i);
/* 484 */       if (colWidth == null || colWidth.getValue() < 0.0F) {
/* 485 */         columnWidths[i] = -1.0F;
/* 486 */       } else if (colWidth.isPercentValue()) {
/* 487 */         columnWidths[i] = colWidth.getValue() * this.tableWidth / 100.0F;
/*     */       } else {
/* 489 */         columnWidths[i] = colWidth.getValue();
/*     */       } 
/*     */     } 
/*     */     
/* 493 */     int processedColumns = 0;
/* 494 */     float remainWidth = this.tableWidth;
/*     */     
/* 496 */     if (this.tableRenderer.headerRenderer != null && this.tableRenderer.headerRenderer.rows.size() > 0) {
/* 497 */       firtsRow = this.tableRenderer.headerRenderer.rows.get(0);
/* 498 */     } else if (this.tableRenderer.rows.size() > 0 && getTable().isComplete() && 0 == getTable().getLastRowBottomBorder().size()) {
/* 499 */       firtsRow = this.tableRenderer.rows.get(0);
/*     */     } else {
/*     */       
/* 502 */       firtsRow = null;
/*     */     } 
/*     */     
/* 505 */     float[] columnWidthIfPercent = new float[columnWidths.length];
/* 506 */     for (int j = 0; j < columnWidthIfPercent.length; j++) {
/* 507 */       columnWidthIfPercent[j] = -1.0F;
/*     */     }
/* 509 */     float sumOfPercents = 0.0F;
/*     */ 
/*     */     
/* 512 */     if (firtsRow != null && getTable().isComplete() && getTable().getLastRowBottomBorder().isEmpty()) {
/* 513 */       for (int m = 0; m < this.numberOfColumns; m++) {
/* 514 */         if (columnWidths[m] == -1.0F) {
/* 515 */           CellRenderer cell = firtsRow[m];
/* 516 */           if (cell != null) {
/* 517 */             UnitValue cellWidth = getCellWidth(cell, true);
/* 518 */             if (cellWidth != null) {
/* 519 */               assert cellWidth.getValue() >= 0.0F;
/* 520 */               float width = 0.0F;
/* 521 */               if (cellWidth.isPercentValue()) {
/* 522 */                 width = this.tableWidth * cellWidth.getValue() / 100.0F;
/* 523 */                 columnWidthIfPercent[m] = cellWidth.getValue();
/* 524 */                 sumOfPercents += columnWidthIfPercent[m];
/*     */               } else {
/* 526 */                 width = cellWidth.getValue();
/*     */               } 
/* 528 */               int colspan = ((Cell)cell.getModelElement()).getColspan();
/* 529 */               for (int n = 0; n < colspan; n++) {
/* 530 */                 columnWidths[m + n] = width / colspan;
/*     */               }
/* 532 */               remainWidth -= columnWidths[m];
/* 533 */               processedColumns++;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 537 */           remainWidth -= columnWidths[m];
/* 538 */           processedColumns++;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 542 */       for (int m = 0; m < this.numberOfColumns; m++) {
/* 543 */         if (columnWidths[m] != -1.0F) {
/* 544 */           processedColumns++;
/* 545 */           remainWidth -= columnWidths[m];
/*     */         } 
/*     */       } 
/*     */     } 
/* 549 */     if (sumOfPercents > 100.0F) {
/* 550 */       warn100percent();
/*     */     }
/* 552 */     if (remainWidth > 0.0F) {
/* 553 */       if (this.numberOfColumns == processedColumns)
/*     */       {
/* 555 */         for (int m = 0; m < this.numberOfColumns; m++) {
/* 556 */           columnWidths[m] = this.tableWidth * columnWidths[m] / (this.tableWidth - remainWidth);
/*     */         }
/*     */       }
/* 559 */     } else if (remainWidth < 0.0F) {
/*     */       
/* 561 */       for (int m = 0; m < this.numberOfColumns; m++) {
/* 562 */         columnWidths[m] = columnWidths[m] + ((-1.0F != columnWidthIfPercent[m]) ? (remainWidth * columnWidthIfPercent[m] / sumOfPercents) : 0.0F);
/*     */       }
/*     */     } 
/*     */     
/*     */     int k;
/* 567 */     for (k = 0; k < this.numberOfColumns; k++) {
/* 568 */       if (columnWidths[k] == -1.0F) {
/* 569 */         columnWidths[k] = Math.max(0.0F, remainWidth / (this.numberOfColumns - processedColumns));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 574 */     if (this.tableRenderer.bordersHandler instanceof SeparatedTableBorders) {
/* 575 */       for (k = 0; k < this.numberOfColumns; k++) {
/* 576 */         columnWidths[k] = columnWidths[k] + this.horizontalBorderSpacing;
/*     */       }
/*     */     }
/* 579 */     return columnWidths;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateTableWidth(float availableWidth, boolean calculateTableMaxWidth) {
/* 585 */     this.fixedTableLayout = "fixed".equals(((String)this.tableRenderer
/* 586 */         .<String>getProperty(93, "auto")).toLowerCase());
/* 587 */     UnitValue width = this.tableRenderer.<UnitValue>getProperty(77);
/* 588 */     if (this.fixedTableLayout && width != null && width.getValue() >= 0.0F) {
/* 589 */       if (0 != getTable().getLastRowBottomBorder().size()) {
/* 590 */         width = getTable().getWidth();
/* 591 */       } else if (!getTable().isComplete() && null != getTable().getWidth() && getTable().getWidth().isPercentValue()) {
/* 592 */         getTable().setWidth(this.tableRenderer.retrieveUnitValue(availableWidth, 77).floatValue());
/*     */       } 
/* 594 */       this.fixedTableWidth = true;
/* 595 */       this.tableWidth = retrieveTableWidth(width, availableWidth).floatValue();
/* 596 */       this.layoutMinWidth = width.isPercentValue() ? 0.0F : this.tableWidth;
/*     */     } else {
/* 598 */       this.fixedTableLayout = false;
/*     */       
/* 600 */       this.layoutMinWidth = -1.0F;
/* 601 */       if (calculateTableMaxWidth) {
/* 602 */         this.fixedTableWidth = false;
/* 603 */         this.tableWidth = retrieveTableWidth(availableWidth);
/* 604 */       } else if (width != null && width.getValue() >= 0.0F) {
/* 605 */         this.fixedTableWidth = true;
/* 606 */         this.tableWidth = retrieveTableWidth(width, availableWidth).floatValue();
/*     */       } else {
/* 608 */         this.fixedTableWidth = false;
/* 609 */         this.tableWidth = retrieveTableWidth(availableWidth);
/*     */       } 
/*     */     } 
/* 612 */     Float min = retrieveTableWidth(this.tableRenderer.<UnitValue>getProperty(80), availableWidth);
/* 613 */     Float max = retrieveTableWidth(this.tableRenderer.<UnitValue>getProperty(79), availableWidth);
/*     */     
/* 615 */     this.tableMinWidth = (min != null) ? min.floatValue() : this.layoutMinWidth;
/* 616 */     this.tableMaxWidth = (max != null) ? max.floatValue() : this.tableWidth;
/*     */     
/* 618 */     if (this.tableMinWidth > this.tableMaxWidth) {
/* 619 */       this.tableMaxWidth = this.tableMinWidth;
/*     */     }
/* 621 */     if (this.tableMinWidth > this.tableWidth) {
/* 622 */       this.tableWidth = this.tableMinWidth;
/*     */     }
/* 624 */     if (this.tableMaxWidth < this.tableWidth)
/* 625 */       this.tableWidth = this.tableMaxWidth; 
/*     */   }
/*     */   
/*     */   private Float retrieveTableWidth(UnitValue width, float availableWidth) {
/* 629 */     if (width == null) return null; 
/* 630 */     return Float.valueOf(retrieveTableWidth(width.isPercentValue() ? (width
/* 631 */           .getValue() * availableWidth / 100.0F) : width
/* 632 */           .getValue()));
/*     */   }
/*     */   
/*     */   private float retrieveTableWidth(float width) {
/* 636 */     if (BorderCollapsePropertyValue.SEPARATE.equals(this.tableRenderer.getProperty(114))) {
/* 637 */       width -= this.rightBorderMaxWidth + this.leftBorderMaxWidth;
/* 638 */       width -= (this.numberOfColumns + 1) * this.horizontalBorderSpacing;
/*     */     } else {
/* 640 */       width -= (this.rightBorderMaxWidth + this.leftBorderMaxWidth) / 2.0F;
/*     */     } 
/* 642 */     return Math.max(width, 0.0F);
/*     */   }
/*     */   
/*     */   private Table getTable() {
/* 646 */     return (Table)this.tableRenderer.getModelElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateMinMaxWidths() {
/* 654 */     float[] minWidths = new float[this.numberOfColumns];
/* 655 */     float[] maxWidths = new float[this.numberOfColumns];
/*     */     
/* 657 */     for (CellInfo cell : this.cells) {
/* 658 */       cell.setParent(this.tableRenderer);
/* 659 */       MinMaxWidth minMax = cell.getCell().getMinMaxWidth();
/* 660 */       float[] indents = getCellBorderIndents(cell);
/* 661 */       if (BorderCollapsePropertyValue.SEPARATE.equals(this.tableRenderer.getProperty(114))) {
/* 662 */         minMax.setAdditionalWidth(minMax.getAdditionalWidth() - this.horizontalBorderSpacing);
/*     */       } else {
/* 664 */         minMax.setAdditionalWidth(minMax.getAdditionalWidth() + indents[1] / 2.0F + indents[3] / 2.0F);
/*     */       } 
/*     */       
/* 667 */       if (cell.getColspan() == 1) {
/* 668 */         minWidths[cell.getCol()] = Math.max(minMax.getMinWidth(), minWidths[cell.getCol()]);
/* 669 */         maxWidths[cell.getCol()] = Math.max(minMax.getMaxWidth(), maxWidths[cell.getCol()]); continue;
/*     */       } 
/* 671 */       float remainMin = minMax.getMinWidth();
/* 672 */       float remainMax = minMax.getMaxWidth(); int j;
/* 673 */       for (j = cell.getCol(); j < cell.getCol() + cell.getColspan(); j++) {
/* 674 */         remainMin -= minWidths[j];
/* 675 */         remainMax -= maxWidths[j];
/*     */       } 
/* 677 */       if (remainMin > 0.0F) {
/* 678 */         for (j = cell.getCol(); j < cell.getCol() + cell.getColspan(); j++) {
/* 679 */           minWidths[j] = minWidths[j] + remainMin / cell.getColspan();
/*     */         }
/*     */       }
/* 682 */       if (remainMax > 0.0F) {
/* 683 */         for (j = cell.getCol(); j < cell.getCol() + cell.getColspan(); j++) {
/* 684 */           maxWidths[j] = maxWidths[j] + remainMax / cell.getColspan();
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 690 */     for (int i = 0; i < this.widths.length; i++) {
/* 691 */       this.widths[i] = new ColumnWidthData(minWidths[i], maxWidths[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private float[] getCellBorderIndents(CellInfo cell) {
/*     */     TableRenderer renderer;
/* 697 */     if (cell.region == 1) {
/* 698 */       renderer = this.tableRenderer.headerRenderer;
/* 699 */     } else if (cell.region == 3) {
/* 700 */       renderer = this.tableRenderer.footerRenderer;
/*     */     } else {
/* 702 */       renderer = this.tableRenderer;
/*     */     } 
/* 704 */     return renderer.bordersHandler.getCellBorderIndents(cell.getRow(), cell.getCol(), cell.getRowspan(), cell.getColspan());
/*     */   }
/*     */   
/*     */   private void fillAndSortCells() {
/* 708 */     this.cells = new ArrayList<>();
/* 709 */     if (this.tableRenderer.headerRenderer != null) {
/* 710 */       fillRendererCells(this.tableRenderer.headerRenderer, (byte)1);
/*     */     }
/* 712 */     fillRendererCells(this.tableRenderer, (byte)2);
/* 713 */     if (this.tableRenderer.footerRenderer != null) {
/* 714 */       fillRendererCells(this.tableRenderer.footerRenderer, (byte)3);
/*     */     }
/*     */ 
/*     */     
/* 718 */     Collections.sort(this.cells);
/*     */   }
/*     */   
/*     */   private void fillRendererCells(TableRenderer renderer, byte region) {
/* 722 */     for (int row = 0; row < renderer.rows.size(); row++) {
/* 723 */       for (int col = 0; col < this.numberOfColumns; col++) {
/* 724 */         CellRenderer cell = ((CellRenderer[])renderer.rows.get(row))[col];
/* 725 */         if (cell != null) {
/* 726 */           this.cells.add(new CellInfo(cell, row, col, region));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void warn100percent() {
/* 733 */     Logger logger = LoggerFactory.getLogger(TableWidths.class);
/* 734 */     logger.warn("Sum of table columns is greater than 100%.");
/*     */   }
/*     */   
/*     */   private float[] extractWidths() {
/* 738 */     float actualWidth = 0.0F;
/* 739 */     this.layoutMinWidth = 0.0F;
/* 740 */     float[] columnWidths = new float[this.widths.length];
/* 741 */     for (int i = 0; i < this.widths.length; i++) {
/* 742 */       assert (this.widths[i]).finalWidth >= 0.0F;
/* 743 */       columnWidths[i] = (this.widths[i]).finalWidth + this.horizontalBorderSpacing;
/* 744 */       actualWidth += (this.widths[i]).finalWidth;
/* 745 */       this.layoutMinWidth += (this.widths[i]).min + this.horizontalBorderSpacing;
/*     */     } 
/* 747 */     if (actualWidth > this.tableWidth + MinMaxWidthUtils.getEps() * this.widths.length) {
/* 748 */       Logger logger = LoggerFactory.getLogger(TableWidths.class);
/* 749 */       logger.warn("Table width is more than expected due to min width of cell(s).");
/*     */     } 
/* 751 */     return columnWidths;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 760 */     return "width=" + this.tableWidth + (this.fixedTableWidth ? "!!" : "");
/*     */   }
/*     */   
/*     */   private static class ColumnWidthData {
/*     */     final float min;
/*     */     float max;
/* 766 */     float width = 0.0F;
/* 767 */     float finalWidth = -1.0F;
/*     */     
/*     */     boolean isPercent = false;
/*     */     boolean isFixed = false;
/*     */     
/*     */     ColumnWidthData(float min, float max) {
/* 773 */       assert min >= 0.0F;
/* 774 */       assert max >= 0.0F;
/* 775 */       this.min = (min > 0.0F) ? (min + MinMaxWidthUtils.getEps()) : 0.0F;
/*     */ 
/*     */       
/* 778 */       this.max = (max > 0.0F) ? Math.min(max + MinMaxWidthUtils.getEps(), 32760.0F) : 0.0F;
/*     */     }
/*     */     
/*     */     ColumnWidthData setPoints(float width) {
/* 782 */       assert !this.isPercent;
/* 783 */       assert this.min <= width;
/* 784 */       this.width = Math.max(this.width, width);
/* 785 */       return this;
/*     */     }
/*     */     
/*     */     ColumnWidthData resetPoints(float width) {
/* 789 */       assert this.min <= width;
/* 790 */       this.width = width;
/* 791 */       this.isPercent = false;
/* 792 */       return this;
/*     */     }
/*     */     
/*     */     ColumnWidthData addPoints(float width) {
/* 796 */       assert !this.isPercent;
/* 797 */       this.width += width;
/* 798 */       return this;
/*     */     }
/*     */     
/*     */     ColumnWidthData setPercents(float percent) {
/* 802 */       if (this.isPercent) {
/* 803 */         this.width = Math.max(this.width, percent);
/*     */       } else {
/* 805 */         this.isPercent = true;
/* 806 */         this.width = percent;
/*     */       } 
/* 808 */       this.isFixed = false;
/* 809 */       return this;
/*     */     }
/*     */     
/*     */     ColumnWidthData addPercents(float width) {
/* 813 */       assert this.isPercent;
/* 814 */       this.width += width;
/* 815 */       return this;
/*     */     }
/*     */     
/*     */     ColumnWidthData setFixed(boolean fixed) {
/* 819 */       this.isFixed = fixed;
/* 820 */       return this;
/*     */     }
/*     */     
/*     */     boolean isFlexible() {
/* 824 */       return (!this.isFixed && !this.isPercent);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 829 */       return "w=" + this.width + (this.isPercent ? "%" : "pt") + (this.isFixed ? " !!" : "") + ", min=" + this.min + ", max=" + this.max + ", finalWidth=" + this.finalWidth;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 838 */   private static final UnitValue ZeroWidth = UnitValue.createPointValue(0.0F);
/*     */   
/*     */   private UnitValue getCellWidth(CellRenderer cell, boolean zeroIsValid) {
/* 841 */     UnitValue widthValue = cell.<UnitValue>getProperty(77);
/*     */     
/* 843 */     if (widthValue == null || widthValue.getValue() < 0.0F)
/* 844 */       return null; 
/* 845 */     if (widthValue.getValue() == 0.0F)
/* 846 */       return zeroIsValid ? ZeroWidth : null; 
/* 847 */     if (widthValue.isPercentValue()) {
/* 848 */       return widthValue;
/*     */     }
/* 850 */     widthValue = resolveMinMaxCollision(cell, widthValue);
/* 851 */     if (!AbstractRenderer.isBorderBoxSizing(cell)) {
/* 852 */       Border[] borders = cell.getBorders();
/* 853 */       if (borders[1] != null) {
/* 854 */         widthValue.setValue(widthValue.getValue() + ((this.tableRenderer.bordersHandler instanceof SeparatedTableBorders) ? borders[1]
/*     */             
/* 856 */             .getWidth() : (borders[1]
/* 857 */             .getWidth() / 2.0F)));
/*     */       }
/* 859 */       if (borders[3] != null) {
/* 860 */         widthValue.setValue(widthValue.getValue() + ((this.tableRenderer.bordersHandler instanceof SeparatedTableBorders) ? borders[3]
/*     */             
/* 862 */             .getWidth() : (borders[3]
/* 863 */             .getWidth() / 2.0F)));
/*     */       }
/* 865 */       UnitValue[] paddings = cell.getPaddings();
/* 866 */       if (!paddings[1].isPointValue()) {
/* 867 */         Logger logger = LoggerFactory.getLogger(TableWidths.class);
/* 868 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(48) }));
/*     */       } 
/* 870 */       if (!paddings[3].isPointValue()) {
/* 871 */         Logger logger = LoggerFactory.getLogger(TableWidths.class);
/* 872 */         logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(49) }));
/*     */       } 
/* 874 */       widthValue.setValue(widthValue.getValue() + paddings[1].getValue() + paddings[3].getValue());
/*     */     } 
/* 876 */     return widthValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private UnitValue resolveMinMaxCollision(CellRenderer cell, UnitValue widthValue) {
/* 881 */     assert widthValue.isPointValue();
/*     */     
/* 883 */     UnitValue minWidthValue = cell.<UnitValue>getProperty(80);
/* 884 */     if (minWidthValue != null && minWidthValue.isPointValue() && minWidthValue
/* 885 */       .getValue() > widthValue.getValue()) {
/* 886 */       return minWidthValue;
/*     */     }
/* 888 */     UnitValue maxWidthValue = cell.<UnitValue>getProperty(79);
/* 889 */     if (maxWidthValue != null && maxWidthValue.isPointValue() && maxWidthValue
/* 890 */       .getValue() < widthValue.getValue()) {
/* 891 */       return maxWidthValue;
/*     */     }
/* 893 */     return widthValue;
/*     */   }
/*     */   
/*     */   static class CellInfo
/*     */     implements Comparable<CellInfo> {
/*     */     static final byte HEADER = 1;
/*     */     static final byte BODY = 2;
/*     */     static final byte FOOTER = 3;
/*     */     private final CellRenderer cell;
/*     */     private final int row;
/*     */     private final int col;
/*     */     final byte region;
/*     */     
/*     */     CellInfo(CellRenderer cell, int row, int col, byte region) {
/* 907 */       this.cell = cell;
/* 908 */       this.region = region;
/*     */       
/* 910 */       this.row = row;
/* 911 */       this.col = col;
/*     */     }
/*     */     
/*     */     CellRenderer getCell() {
/* 915 */       return this.cell;
/*     */     }
/*     */     
/*     */     int getCol() {
/* 919 */       return this.col;
/*     */     }
/*     */ 
/*     */     
/*     */     int getColspan() {
/* 924 */       return this.cell.getPropertyAsInteger(16).intValue();
/*     */     }
/*     */     
/*     */     int getRow() {
/* 928 */       return this.row;
/*     */     }
/*     */ 
/*     */     
/*     */     int getRowspan() {
/* 933 */       return this.cell.getPropertyAsInteger(60).intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(CellInfo o) {
/* 938 */       if ((((getColspan() == 1) ? 1 : 0) ^ ((o.getColspan() == 1) ? 1 : 0)) != 0) {
/* 939 */         return getColspan() - o.getColspan();
/*     */       }
/* 941 */       if (this.region == o.region && getRow() == o.getRow()) {
/* 942 */         return getCol() + getColspan() - o.getCol() - o.getColspan();
/*     */       }
/* 944 */       return (this.region == o.region) ? (getRow() - o.getRow()) : (this.region - o.region);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 949 */       String str = MessageFormatUtil.format("row={0}, col={1}, rowspan={2}, colspan={3}, ", new Object[] {
/* 950 */             Integer.valueOf(getRow()), Integer.valueOf(getCol()), Integer.valueOf(getRowspan()), Integer.valueOf(getColspan()) });
/* 951 */       if (this.region == 1) {
/* 952 */         str = str + "header";
/* 953 */       } else if (this.region == 2) {
/* 954 */         str = str + "body";
/* 955 */       } else if (this.region == 3) {
/* 956 */         str = str + "footer";
/*     */       } 
/* 958 */       return str;
/*     */     }
/*     */     
/*     */     public void setParent(TableRenderer tableRenderer) {
/* 962 */       if (this.region == 1) {
/* 963 */         this.cell.setParent(tableRenderer.headerRenderer);
/* 964 */       } else if (this.region == 3) {
/* 965 */         this.cell.setParent(tableRenderer.footerRenderer);
/*     */       } else {
/* 967 */         this.cell.setParent(tableRenderer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/TableWidths.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */