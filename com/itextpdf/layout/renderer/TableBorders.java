/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.borders.Border;
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
/*     */ abstract class TableBorders
/*     */ {
/*  66 */   protected List<List<Border>> horizontalBorders = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   protected List<List<Border>> verticalBorders = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int numberOfColumns;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   protected Border[] tableBoundingBorders = new Border[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<CellRenderer[]> rows;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int startRow;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int finishRow;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float leftBorderMaxWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float rightBorderMaxWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   protected int largeTableIndexOffset = 0;
/*     */   
/*     */   public TableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders) {
/* 129 */     this.rows = rows;
/* 130 */     this.numberOfColumns = numberOfColumns;
/* 131 */     setTableBoundingBorders(tableBoundingBorders);
/*     */   }
/*     */   
/*     */   public TableBorders(List<CellRenderer[]> rows, int numberOfColumns, Border[] tableBoundingBorders, int largeTableIndexOffset) {
/* 135 */     this(rows, numberOfColumns, tableBoundingBorders);
/* 136 */     this.largeTableIndexOffset = largeTableIndexOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract TableBorders drawHorizontalBorder(int paramInt, float paramFloat1, float paramFloat2, PdfCanvas paramPdfCanvas, float[] paramArrayOffloat);
/*     */ 
/*     */   
/*     */   protected abstract TableBorders drawVerticalBorder(int paramInt, float paramFloat1, float paramFloat2, PdfCanvas paramPdfCanvas, List<Float> paramList);
/*     */ 
/*     */   
/*     */   protected abstract TableBorders applyTopTableBorder(Rectangle paramRectangle1, Rectangle paramRectangle2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*     */ 
/*     */   
/*     */   protected abstract TableBorders applyTopTableBorder(Rectangle paramRectangle1, Rectangle paramRectangle2, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   protected abstract TableBorders applyBottomTableBorder(Rectangle paramRectangle1, Rectangle paramRectangle2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*     */ 
/*     */   
/*     */   protected abstract TableBorders applyBottomTableBorder(Rectangle paramRectangle1, Rectangle paramRectangle2, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   protected abstract TableBorders applyLeftAndRightTableBorder(Rectangle paramRectangle, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   protected abstract TableBorders skipFooter(Border[] paramArrayOfBorder);
/*     */ 
/*     */   
/*     */   protected abstract TableBorders skipHeader(Border[] paramArrayOfBorder);
/*     */   
/*     */   protected abstract TableBorders collapseTableWithFooter(TableBorders paramTableBorders, boolean paramBoolean);
/*     */   
/*     */   protected abstract TableBorders collapseTableWithHeader(TableBorders paramTableBorders, boolean paramBoolean);
/*     */   
/*     */   protected abstract TableBorders fixHeaderOccupiedArea(Rectangle paramRectangle1, Rectangle paramRectangle2);
/*     */   
/*     */   protected abstract TableBorders applyCellIndents(Rectangle paramRectangle, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean);
/*     */   
/*     */   public abstract List<Border> getVerticalBorder(int paramInt);
/*     */   
/*     */   public abstract List<Border> getHorizontalBorder(int paramInt);
/*     */   
/*     */   protected abstract float getCellVerticalAddition(float[] paramArrayOffloat);
/*     */   
/*     */   protected abstract void buildBordersArrays(CellRenderer paramCellRenderer, int paramInt1, int paramInt2, int[] paramArrayOfint);
/*     */   
/*     */   protected abstract TableBorders updateBordersOnNewPage(boolean paramBoolean1, boolean paramBoolean2, TableRenderer paramTableRenderer1, TableRenderer paramTableRenderer2, TableRenderer paramTableRenderer3);
/*     */   
/*     */   protected TableBorders processAllBordersAndEmptyRows() {
/* 185 */     int[] rowspansToDeduct = new int[this.numberOfColumns];
/* 186 */     int numOfRowsToRemove = 0;
/* 187 */     if (!this.rows.isEmpty()) {
/* 188 */       for (int row = this.startRow - this.largeTableIndexOffset; row <= this.finishRow - this.largeTableIndexOffset; row++) {
/* 189 */         CellRenderer[] currentRow = this.rows.get(row);
/* 190 */         boolean hasCells = false;
/* 191 */         for (int col = 0; col < this.numberOfColumns; col++) {
/* 192 */           if (null != currentRow[col]) {
/* 193 */             int colspan = currentRow[col].getPropertyAsInteger(16).intValue();
/* 194 */             if (rowspansToDeduct[col] > 0) {
/* 195 */               int rowspan = currentRow[col].getPropertyAsInteger(60).intValue() - rowspansToDeduct[col];
/* 196 */               if (rowspan < 1) {
/* 197 */                 Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 198 */                 logger.warn("Unexpected behaviour during table row collapsing. Calculated rowspan was less then 1.");
/* 199 */                 rowspan = 1;
/*     */               } 
/* 201 */               currentRow[col].setProperty(60, Integer.valueOf(rowspan));
/* 202 */               if (0 != numOfRowsToRemove) {
/* 203 */                 removeRows(row - numOfRowsToRemove, numOfRowsToRemove);
/* 204 */                 row -= numOfRowsToRemove;
/* 205 */                 numOfRowsToRemove = 0;
/*     */               } 
/*     */             } 
/* 208 */             buildBordersArrays(currentRow[col], row, col, rowspansToDeduct);
/* 209 */             hasCells = true;
/* 210 */             for (int i = 0; i < colspan; i++) {
/* 211 */               rowspansToDeduct[col + i] = 0;
/*     */             }
/* 213 */             col += colspan - 1;
/*     */           }
/* 215 */           else if (((List)this.horizontalBorders.get(row)).size() <= col) {
/* 216 */             ((List)this.horizontalBorders.get(row)).add(null);
/*     */           } 
/*     */         } 
/*     */         
/* 220 */         if (!hasCells) {
/* 221 */           if (row == this.rows.size() - 1) {
/* 222 */             removeRows(row - rowspansToDeduct[0], rowspansToDeduct[0]);
/*     */             
/* 224 */             this.rows.remove(row - rowspansToDeduct[0]);
/* 225 */             setFinishRow(this.finishRow - 1);
/*     */             
/* 227 */             Logger logger = LoggerFactory.getLogger(TableRenderer.class);
/* 228 */             logger.warn("Last row is not completed. Table bottom border may collapse as you do not expect it");
/*     */           } else {
/* 230 */             for (int i = 0; i < this.numberOfColumns; i++) {
/* 231 */               rowspansToDeduct[i] = rowspansToDeduct[i] + 1;
/*     */             }
/* 233 */             numOfRowsToRemove++;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 238 */     if (this.finishRow < this.startRow) {
/* 239 */       setFinishRow(this.startRow);
/*     */     }
/* 241 */     return this;
/*     */   }
/*     */   
/*     */   private void removeRows(int startRow, int numOfRows) {
/* 245 */     for (int row = startRow; row < startRow + numOfRows; row++) {
/* 246 */       this.rows.remove(startRow);
/* 247 */       this.horizontalBorders.remove(startRow + 1);
/* 248 */       for (int j = 0; j <= this.numberOfColumns; j++) {
/* 249 */         ((List)this.verticalBorders.get(j)).remove(startRow + 1);
/*     */       }
/*     */     } 
/* 252 */     setFinishRow(this.finishRow - numOfRows);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableBorders initializeBorders() {
/* 259 */     while (this.numberOfColumns + 1 > this.verticalBorders.size()) {
/* 260 */       List<Border> tempBorders = new ArrayList<>();
/* 261 */       while (Math.max(this.rows.size(), 1) > tempBorders.size()) {
/* 262 */         tempBorders.add(null);
/*     */       }
/* 264 */       this.verticalBorders.add(tempBorders);
/*     */     } 
/*     */     
/* 267 */     while (Math.max(this.rows.size(), 1) + 1 > this.horizontalBorders.size()) {
/* 268 */       List<Border> tempBorders = new ArrayList<>();
/* 269 */       while (this.numberOfColumns > tempBorders.size()) {
/* 270 */         tempBorders.add(null);
/*     */       }
/* 272 */       this.horizontalBorders.add(tempBorders);
/*     */     } 
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableBorders setTableBoundingBorders(Border[] borders) {
/* 280 */     this.tableBoundingBorders = new Border[4];
/* 281 */     if (null != borders) {
/* 282 */       for (int i = 0; i < borders.length; i++) {
/* 283 */         this.tableBoundingBorders[i] = borders[i];
/*     */       }
/*     */     }
/* 286 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders setRowRange(int startRow, int finishRow) {
/* 290 */     this.startRow = startRow;
/* 291 */     this.finishRow = finishRow;
/* 292 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders setStartRow(int row) {
/* 296 */     this.startRow = row;
/* 297 */     return this;
/*     */   }
/*     */   
/*     */   protected TableBorders setFinishRow(int row) {
/* 301 */     this.finishRow = row;
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLeftBorderMaxWidth() {
/* 308 */     return this.leftBorderMaxWidth;
/*     */   }
/*     */   
/*     */   public float getRightBorderMaxWidth() {
/* 312 */     return this.rightBorderMaxWidth;
/*     */   }
/*     */   
/*     */   public float getMaxTopWidth() {
/* 316 */     float width = 0.0F;
/* 317 */     Border widestBorder = getWidestHorizontalBorder(this.startRow);
/* 318 */     if (null != widestBorder && widestBorder.getWidth() >= width) {
/* 319 */       width = widestBorder.getWidth();
/*     */     }
/* 321 */     return width;
/*     */   }
/*     */   
/*     */   public float getMaxBottomWidth() {
/* 325 */     float width = 0.0F;
/* 326 */     Border widestBorder = getWidestHorizontalBorder(this.finishRow + 1);
/* 327 */     if (null != widestBorder && widestBorder.getWidth() >= width) {
/* 328 */       width = widestBorder.getWidth();
/*     */     }
/* 330 */     return width;
/*     */   }
/*     */   
/*     */   public float getMaxRightWidth() {
/* 334 */     float width = 0.0F;
/* 335 */     Border widestBorder = getWidestVerticalBorder(this.verticalBorders.size() - 1);
/* 336 */     if (null != widestBorder && widestBorder.getWidth() >= width) {
/* 337 */       width = widestBorder.getWidth();
/*     */     }
/* 339 */     return width;
/*     */   }
/*     */   
/*     */   public float getMaxLeftWidth() {
/* 343 */     float width = 0.0F;
/* 344 */     Border widestBorder = getWidestVerticalBorder(0);
/* 345 */     if (null != widestBorder && widestBorder.getWidth() >= width) {
/* 346 */       width = widestBorder.getWidth();
/*     */     }
/* 348 */     return width;
/*     */   }
/*     */   
/*     */   public Border getWidestVerticalBorder(int col) {
/* 352 */     return TableBorderUtil.getWidestBorder(getVerticalBorder(col));
/*     */   }
/*     */   
/*     */   public Border getWidestVerticalBorder(int col, int start, int end) {
/* 356 */     return TableBorderUtil.getWidestBorder(getVerticalBorder(col), start, end);
/*     */   }
/*     */   
/*     */   public Border getWidestHorizontalBorder(int row) {
/* 360 */     return TableBorderUtil.getWidestBorder(getHorizontalBorder(row));
/*     */   }
/*     */   
/*     */   public Border getWidestHorizontalBorder(int row, int start, int end) {
/* 364 */     return TableBorderUtil.getWidestBorder(getHorizontalBorder(row), start, end);
/*     */   }
/*     */   
/*     */   public List<Border> getFirstHorizontalBorder() {
/* 368 */     return getHorizontalBorder(this.startRow);
/*     */   }
/*     */   
/*     */   public List<Border> getLastHorizontalBorder() {
/* 372 */     return getHorizontalBorder(this.finishRow + 1);
/*     */   }
/*     */   
/*     */   public List<Border> getFirstVerticalBorder() {
/* 376 */     return getVerticalBorder(0);
/*     */   }
/*     */   
/*     */   public List<Border> getLastVerticalBorder() {
/* 380 */     return getVerticalBorder(this.verticalBorders.size() - 1);
/*     */   }
/*     */   
/*     */   public int getNumberOfColumns() {
/* 384 */     return this.numberOfColumns;
/*     */   }
/*     */   
/*     */   public int getStartRow() {
/* 388 */     return this.startRow;
/*     */   }
/*     */   
/*     */   public int getFinishRow() {
/* 392 */     return this.finishRow;
/*     */   }
/*     */   
/*     */   public Border[] getTableBoundingBorders() {
/* 396 */     return this.tableBoundingBorders;
/*     */   }
/*     */   
/*     */   public float[] getCellBorderIndents(int row, int col, int rowspan, int colspan) {
/* 400 */     float[] indents = new float[4];
/*     */ 
/*     */ 
/*     */     
/* 404 */     List<Border> borderList = getHorizontalBorder(this.startRow + row - rowspan + 1); int i;
/* 405 */     for (i = col; i < col + colspan; i++) {
/* 406 */       Border border = borderList.get(i);
/* 407 */       if (null != border && border.getWidth() > indents[0]) {
/* 408 */         indents[0] = border.getWidth();
/*     */       }
/*     */     } 
/*     */     
/* 412 */     borderList = getVerticalBorder(col + colspan);
/* 413 */     for (i = this.startRow + row - rowspan + 1; i < this.startRow + row + 1; i++) {
/* 414 */       Border border = borderList.get(i);
/* 415 */       if (null != border && border.getWidth() > indents[1]) {
/* 416 */         indents[1] = border.getWidth();
/*     */       }
/*     */     } 
/*     */     
/* 420 */     borderList = getHorizontalBorder(this.startRow + row + 1);
/* 421 */     for (i = col; i < col + colspan; i++) {
/* 422 */       Border border = borderList.get(i);
/* 423 */       if (null != border && border.getWidth() > indents[2]) {
/* 424 */         indents[2] = border.getWidth();
/*     */       }
/*     */     } 
/*     */     
/* 428 */     borderList = getVerticalBorder(col);
/* 429 */     for (i = this.startRow + row - rowspan + 1; i < this.startRow + row + 1; i++) {
/* 430 */       Border border = borderList.get(i);
/* 431 */       if (null != border && border.getWidth() > indents[3]) {
/* 432 */         indents[3] = border.getWidth();
/*     */       }
/*     */     } 
/* 435 */     return indents;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/TableBorders.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */