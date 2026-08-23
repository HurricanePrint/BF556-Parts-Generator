/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*     */ import com.itextpdf.layout.ElementPropertyContainer;
/*     */ import com.itextpdf.layout.Style;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractElement<T extends IElement>
/*     */   extends ElementPropertyContainer<T>
/*     */   implements IElement
/*     */ {
/*     */   protected IRenderer nextRenderer;
/*  65 */   protected List<IElement> childElements = new ArrayList<>();
/*     */   
/*     */   protected Set<Style> styles;
/*     */   
/*     */   public IRenderer getRenderer() {
/*  70 */     if (this.nextRenderer != null) {
/*  71 */       IRenderer renderer = this.nextRenderer;
/*  72 */       this.nextRenderer = this.nextRenderer.getNextRenderer();
/*  73 */       return renderer;
/*     */     } 
/*  75 */     return makeNewRenderer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNextRenderer(IRenderer renderer) {
/*  80 */     this.nextRenderer = renderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public IRenderer createRendererSubTree() {
/*  85 */     IRenderer rendererRoot = getRenderer();
/*  86 */     for (IElement child : this.childElements) {
/*  87 */       rendererRoot.addChild(child.createRendererSubTree());
/*     */     }
/*  89 */     return rendererRoot;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProperty(int property) {
/*  94 */     boolean hasProperty = super.hasProperty(property);
/*  95 */     if (this.styles != null && this.styles.size() > 0 && !hasProperty) {
/*  96 */       for (Style style : this.styles) {
/*  97 */         if (style.hasProperty(property)) {
/*  98 */           hasProperty = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 103 */     return hasProperty;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getProperty(int property) {
/* 108 */     Object result = super.getProperty(property);
/* 109 */     if (this.styles != null && this.styles.size() > 0 && result == null && !super.hasProperty(property)) {
/* 110 */       for (Style style : this.styles) {
/* 111 */         T1 foundInStyle = (T1)style.getProperty(property);
/* 112 */         if (foundInStyle != null || style.hasProperty(property)) {
/* 113 */           result = foundInStyle;
/*     */         }
/*     */       } 
/*     */     }
/* 117 */     return (T1)result;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public T addStyle(Style style) {
/* 135 */     if (this.styles == null) {
/* 136 */       this.styles = new LinkedHashSet<>();
/*     */     }
/* 138 */     this.styles.add(style);
/* 139 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IElement> getChildren() {
/* 148 */     return this.childElements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 157 */     return (0 == this.childElements.size());
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
/*     */   public T setAction(PdfAction action) {
/* 169 */     setProperty(1, action);
/* 170 */     return (T)this;
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
/*     */   public T setPageNumber(int pageNumber) {
/* 184 */     setProperty(51, Integer.valueOf(pageNumber));
/* 185 */     return (T)this;
/*     */   }
/*     */   
/*     */   protected abstract IRenderer makeNewRenderer();
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/AbstractElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */