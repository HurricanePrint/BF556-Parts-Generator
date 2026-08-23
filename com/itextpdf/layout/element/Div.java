/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.layout.renderer.DivRenderer;
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
/*     */ public class Div
/*     */   extends BlockElement<Div>
/*     */ {
/*     */   protected DefaultAccessibilityProperties tagProperties;
/*     */   
/*     */   public Div add(IBlockElement element) {
/*  71 */     this.childElements.add(element);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Div add(Image element) {
/*  82 */     this.childElements.add(element);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Div add(AreaBreak areaBreak) {
/*  93 */     this.childElements.add(areaBreak);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/*  99 */     if (this.tagProperties == null) {
/* 100 */       this.tagProperties = new DefaultAccessibilityProperties("Div");
/*     */     }
/* 102 */     return (AccessibilityProperties)this.tagProperties;
/*     */   }
/*     */   
/*     */   public Div setFillAvailableArea(boolean fillArea) {
/* 106 */     setProperty(86, Boolean.valueOf(fillArea));
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public Div setFillAvailableAreaOnSplit(boolean fillAreaOnSplit) {
/* 111 */     setProperty(87, Boolean.valueOf(fillAreaOnSplit));
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 117 */     return (IRenderer)new DivRenderer(this);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/Div.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */