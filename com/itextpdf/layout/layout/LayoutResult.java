/*     */ package com.itextpdf.layout.layout;
/*     */ 
/*     */ import com.itextpdf.layout.element.AreaBreak;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayoutResult
/*     */ {
/*     */   public static final int FULL = 1;
/*     */   public static final int PARTIAL = 2;
/*     */   public static final int NOTHING = 3;
/*     */   protected int status;
/*     */   protected LayoutArea occupiedArea;
/*     */   protected IRenderer splitRenderer;
/*     */   protected IRenderer overflowRenderer;
/*     */   protected AreaBreak areaBreak;
/*     */   protected IRenderer causeOfNothing;
/*     */   
/*     */   public LayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer) {
/* 108 */     this(status, occupiedArea, splitRenderer, overflowRenderer, null);
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
/*     */   public LayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer, IRenderer cause) {
/* 122 */     this.status = status;
/* 123 */     this.occupiedArea = occupiedArea;
/* 124 */     this.splitRenderer = splitRenderer;
/* 125 */     this.overflowRenderer = overflowRenderer;
/* 126 */     this.causeOfNothing = cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatus() {
/* 135 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(int status) {
/* 144 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutArea getOccupiedArea() {
/* 153 */     return this.occupiedArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getSplitRenderer() {
/* 162 */     return this.splitRenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSplitRenderer(IRenderer splitRenderer) {
/* 171 */     this.splitRenderer = splitRenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getOverflowRenderer() {
/* 180 */     return this.overflowRenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOverflowRenderer(IRenderer overflowRenderer) {
/* 189 */     this.overflowRenderer = overflowRenderer;
/*     */   }
/*     */   
/*     */   public AreaBreak getAreaBreak() {
/* 193 */     return this.areaBreak;
/*     */   }
/*     */   
/*     */   public LayoutResult setAreaBreak(AreaBreak areaBreak) {
/* 197 */     this.areaBreak = areaBreak;
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getCauseOfNothing() {
/* 207 */     return this.causeOfNothing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 216 */     switch (getStatus())
/*     */     { case 1:
/* 218 */         status = "Full";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 230 */         return "LayoutResult{" + status + ", areaBreak=" + this.areaBreak + ", occupiedArea=" + this.occupiedArea + '}';case 3: status = "Nothing"; return "LayoutResult{" + status + ", areaBreak=" + this.areaBreak + ", occupiedArea=" + this.occupiedArea + '}';case 2: status = "Partial"; return "LayoutResult{" + status + ", areaBreak=" + this.areaBreak + ", occupiedArea=" + this.occupiedArea + '}'; }  String status = "None"; return "LayoutResult{" + status + ", areaBreak=" + this.areaBreak + ", occupiedArea=" + this.occupiedArea + '}';
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/LayoutResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */