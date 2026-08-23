/*     */ package com.itextpdf.layout.layout;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayoutContext
/*     */ {
/*     */   protected LayoutArea area;
/*     */   protected MarginsCollapseInfo marginsCollapseInfo;
/*  64 */   protected List<Rectangle> floatRendererAreas = new ArrayList<>();
/*     */ 
/*     */   
/*     */   protected boolean clippedHeight = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutContext(LayoutArea area) {
/*  72 */     this.area = area;
/*     */   }
/*     */   
/*     */   public LayoutContext(LayoutArea area, MarginsCollapseInfo marginsCollapseInfo) {
/*  76 */     this.area = area;
/*  77 */     this.marginsCollapseInfo = marginsCollapseInfo;
/*     */   }
/*     */   
/*     */   public LayoutContext(LayoutArea area, MarginsCollapseInfo marginsCollapseInfo, List<Rectangle> floatedRendererAreas) {
/*  81 */     this(area, marginsCollapseInfo);
/*  82 */     if (floatedRendererAreas != null) {
/*  83 */       this.floatRendererAreas = floatedRendererAreas;
/*     */     }
/*     */   }
/*     */   
/*     */   public LayoutContext(LayoutArea area, boolean clippedHeight) {
/*  88 */     this(area);
/*  89 */     this.clippedHeight = clippedHeight;
/*     */   }
/*     */   
/*     */   public LayoutContext(LayoutArea area, MarginsCollapseInfo marginsCollapseInfo, List<Rectangle> floatedRendererAreas, boolean clippedHeight) {
/*  93 */     this(area, marginsCollapseInfo);
/*  94 */     if (floatedRendererAreas != null) {
/*  95 */       this.floatRendererAreas = floatedRendererAreas;
/*     */     }
/*  97 */     this.clippedHeight = clippedHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutArea getArea() {
/* 106 */     return this.area;
/*     */   }
/*     */   
/*     */   public MarginsCollapseInfo getMarginsCollapseInfo() {
/* 110 */     return this.marginsCollapseInfo;
/*     */   }
/*     */   
/*     */   public List<Rectangle> getFloatRendererAreas() {
/* 114 */     return this.floatRendererAreas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClippedHeight() {
/* 123 */     return this.clippedHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClippedHeight(boolean clippedHeight) {
/* 132 */     this.clippedHeight = clippedHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     return this.area.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/LayoutContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */