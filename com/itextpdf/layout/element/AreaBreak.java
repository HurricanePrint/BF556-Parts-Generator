/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.PageSize;
/*     */ import com.itextpdf.layout.property.AreaBreakType;
/*     */ import com.itextpdf.layout.renderer.AreaBreakRenderer;
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
/*     */ public class AreaBreak
/*     */   extends AbstractElement<AreaBreak>
/*     */ {
/*     */   protected PageSize pageSize;
/*     */   
/*     */   public AreaBreak() {
/*  66 */     this(AreaBreakType.NEXT_AREA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AreaBreak(AreaBreakType areaBreakType) {
/*  74 */     setProperty(2, areaBreakType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AreaBreak(PageSize pageSize) {
/*  83 */     this(AreaBreakType.NEXT_PAGE);
/*  84 */     this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageSize getPageSize() {
/*  92 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPageSize(PageSize pageSize) {
/* 100 */     this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AreaBreakType getType() {
/* 108 */     return (AreaBreakType)getProperty(2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 113 */     return (IRenderer)new AreaBreakRenderer(this);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/AreaBreak.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */