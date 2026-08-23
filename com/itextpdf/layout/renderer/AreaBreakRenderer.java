/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ import com.itextpdf.layout.element.AreaBreak;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
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
/*     */ public class AreaBreakRenderer
/*     */   implements IRenderer
/*     */ {
/*     */   protected AreaBreak areaBreak;
/*     */   
/*     */   public AreaBreakRenderer(AreaBreak areaBreak) {
/*  68 */     this.areaBreak = areaBreak;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChild(IRenderer renderer) {
/*  73 */     throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   
/*     */   public LayoutResult layout(LayoutContext layoutContext) {
/*  78 */     return (new LayoutResult(3, null, null, null, this)).setAreaBreak(this.areaBreak);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(DrawContext drawContext) {
/*  83 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public LayoutArea getOccupiedArea() {
/*  88 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProperty(int property) {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasOwnProperty(int property) {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getProperty(int key) {
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getOwnProperty(int property) {
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getProperty(int property, T1 defaultValue) {
/* 118 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(int property, Object value) {
/* 123 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteOwnProperty(int property) {}
/*     */ 
/*     */   
/*     */   public IRenderer setParent(IRenderer parent) {
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public IPropertyContainer getModelElement() {
/* 137 */     return null;
/*     */   }
/*     */   
/*     */   public IRenderer getParent() {
/* 141 */     return null;
/*     */   }
/*     */   
/*     */   public List<IRenderer> getChildRenderers() {
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlushed() {
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(float dx, float dy) {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 160 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/AreaBreakRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */