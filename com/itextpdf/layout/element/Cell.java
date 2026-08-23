/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.layout.Style;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.borders.SolidBorder;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.renderer.CellRenderer;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
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
/*     */ public class Cell
/*     */   extends BlockElement<Cell>
/*     */ {
/*  74 */   private static final Border DEFAULT_BORDER = (Border)new SolidBorder(0.5F);
/*     */ 
/*     */   
/*     */   private int row;
/*     */ 
/*     */   
/*     */   private int col;
/*     */   
/*     */   private int rowspan;
/*     */   
/*     */   private int colspan;
/*     */   
/*     */   protected DefaultAccessibilityProperties tagProperties;
/*     */ 
/*     */   
/*     */   public Cell(int rowspan, int colspan) {
/*  90 */     this.rowspan = Math.max(rowspan, 1);
/*  91 */     this.colspan = Math.max(colspan, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell() {
/*  98 */     this(1, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getRenderer() {
/* 109 */     CellRenderer cellRenderer = null;
/* 110 */     if (this.nextRenderer != null) {
/* 111 */       if (this.nextRenderer instanceof CellRenderer) {
/* 112 */         IRenderer renderer = this.nextRenderer;
/* 113 */         this.nextRenderer = this.nextRenderer.getNextRenderer();
/* 114 */         cellRenderer = (CellRenderer)renderer;
/*     */       } else {
/* 116 */         Logger logger = LoggerFactory.getLogger(Table.class);
/* 117 */         logger.error("Invalid renderer for Table: must be inherited from TableRenderer");
/*     */       } 
/*     */     }
/*     */     
/* 121 */     return (cellRenderer == null) ? makeNewRenderer() : (IRenderer)cellRenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRow() {
/* 129 */     return this.row;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCol() {
/* 137 */     return this.col;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowspan() {
/* 145 */     return this.rowspan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColspan() {
/* 153 */     return this.colspan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell add(IBlockElement element) {
/* 163 */     this.childElements.add(element);
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell add(Image element) {
/* 174 */     this.childElements.add(element);
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell clone(boolean includeContent) {
/* 185 */     Cell newCell = new Cell(this.rowspan, this.colspan);
/* 186 */     newCell.row = this.row;
/* 187 */     newCell.col = this.col;
/* 188 */     newCell.properties = new HashMap<>(this.properties);
/* 189 */     if (null != this.styles) {
/* 190 */       newCell.styles = new LinkedHashSet<>(this.styles);
/*     */     }
/* 192 */     if (includeContent) {
/* 193 */       newCell.childElements = new ArrayList<>(this.childElements);
/*     */     }
/* 195 */     return newCell;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/* 200 */     switch (property) {
/*     */       case 9:
/* 202 */         return (T1)DEFAULT_BORDER;
/*     */       case 47:
/*     */       case 48:
/*     */       case 49:
/*     */       case 50:
/* 207 */         return (T1)UnitValue.createPointValue(2.0F);
/*     */     } 
/* 209 */     return super.getDefaultProperty(property);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 215 */     return MessageFormatUtil.format("Cell[row={0}, col={1}, rowspan={2}, colspan={3}]", new Object[] { Integer.valueOf(this.row), Integer.valueOf(this.col), Integer.valueOf(this.rowspan), Integer.valueOf(this.colspan) });
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/* 220 */     if (this.tagProperties == null) {
/* 221 */       this.tagProperties = new DefaultAccessibilityProperties("TD");
/*     */     }
/* 223 */     return (AccessibilityProperties)this.tagProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 228 */     return (IRenderer)new CellRenderer(this);
/*     */   }
/*     */   
/*     */   protected Cell updateCellIndexes(int row, int col, int numberOfColumns) {
/* 232 */     this.row = row;
/* 233 */     this.col = col;
/* 234 */     this.colspan = Math.min(this.colspan, numberOfColumns - this.col);
/* 235 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/Cell.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */