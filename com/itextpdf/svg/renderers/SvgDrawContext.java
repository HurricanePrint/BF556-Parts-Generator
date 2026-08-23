/*     */ package com.itextpdf.svg.renderers;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.font.FontProvider;
/*     */ import com.itextpdf.layout.font.FontSet;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
/*     */ import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import com.itextpdf.svg.renderers.impl.AbstractSvgNodeRenderer;
/*     */ import java.util.Deque;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SvgDrawContext
/*     */ {
/*     */   private final Map<String, ISvgNodeRenderer> namedObjects;
/*     */   private final Deque<PdfCanvas> canvases;
/*     */   private final Deque<Rectangle> viewports;
/*     */   private final Stack<String> useIds;
/*     */   private ResourceResolver resourceResolver;
/*     */   private FontProvider fontProvider;
/*     */   private FontSet tempFonts;
/*     */   private float remValue;
/*     */   private AffineTransform lastTextTransform;
/*     */   private float[] textMove;
/*     */   
/*     */   public SvgDrawContext(ResourceResolver resourceResolver, FontProvider fontProvider) {
/*  92 */     this(resourceResolver, fontProvider, null);
/*     */   }
/*     */   
/*     */   public SvgDrawContext(ResourceResolver resourceResolver, FontProvider fontProvider, ISvgNodeRenderer svgRootRenderer) {
/*     */     BasicFontProvider basicFontProvider;
/*     */     this.namedObjects = new HashMap<>();
/*     */     this.canvases = new LinkedList<>();
/*     */     this.viewports = new LinkedList<>();
/*     */     this.useIds = new Stack<>();
/*     */     this.lastTextTransform = new AffineTransform();
/*     */     this.textMove = new float[] { 0.0F, 0.0F };
/* 103 */     if (resourceResolver == null) {
/* 104 */       resourceResolver = new ResourceResolver(null);
/*     */     }
/* 106 */     this.resourceResolver = resourceResolver;
/* 107 */     if (fontProvider == null) {
/* 108 */       basicFontProvider = new BasicFontProvider();
/*     */     }
/* 110 */     this.fontProvider = (FontProvider)basicFontProvider;
/* 111 */     if (svgRootRenderer instanceof AbstractSvgNodeRenderer) {
/* 112 */       this.remValue = ((AbstractSvgNodeRenderer)svgRootRenderer).getCurrentFontSize();
/*     */     } else {
/*     */       
/* 115 */       this.remValue = CssUtils.parseAbsoluteFontSize(CssDefaults.getDefaultValue("font-size"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCanvas getCurrentCanvas() {
/* 125 */     return this.canvases.getFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCanvas popCanvas() {
/* 135 */     PdfCanvas canvas = this.canvases.getFirst();
/* 136 */     this.canvases.removeFirst();
/* 137 */     return canvas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushCanvas(PdfCanvas canvas) {
/* 148 */     this.canvases.addFirst(canvas);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 158 */     return this.canvases.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addViewPort(Rectangle viewPort) {
/* 167 */     this.viewports.addFirst(viewPort);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getCurrentViewPort() {
/* 176 */     return this.viewports.getFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getRootViewPort() {
/* 185 */     return this.viewports.getLast();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeCurrentViewPort() {
/* 192 */     if (this.viewports.size() > 0) {
/* 193 */       this.viewports.removeFirst();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNamedObject(String name, ISvgNodeRenderer namedObject) {
/* 204 */     if (namedObject == null) {
/* 205 */       throw new SvgProcessingException("A named object can't be null.");
/*     */     }
/*     */     
/* 208 */     if (name == null || name.isEmpty()) {
/* 209 */       throw new SvgProcessingException("The name of the named object can't be null or empty.");
/*     */     }
/*     */     
/* 212 */     if (!this.namedObjects.containsKey(name)) {
/* 213 */       this.namedObjects.put(name, namedObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer getNamedObject(String name) {
/* 224 */     return this.namedObjects.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceResolver getResourceResolver() {
/* 233 */     return this.resourceResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNamedObjects(Map<String, ISvgNodeRenderer> namedObjects) {
/* 242 */     this.namedObjects.putAll(namedObjects);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProvider getFontProvider() {
/* 251 */     return this.fontProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontSet getTempFonts() {
/* 260 */     return this.tempFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTempFonts(FontSet tempFonts) {
/* 269 */     this.tempFonts = tempFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIdUsedByUseTagBefore(String elementId) {
/* 279 */     return this.useIds.contains(elementId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUsedId(String elementId) {
/* 288 */     this.useIds.push(elementId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeUsedId(String elementId) {
/* 297 */     this.useIds.pop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AffineTransform getLastTextTransform() {
/* 305 */     if (this.lastTextTransform == null) {
/* 306 */       this.lastTextTransform = new AffineTransform();
/*     */     }
/* 308 */     return this.lastTextTransform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastTextTransform(AffineTransform newTransform) {
/* 316 */     this.lastTextTransform = newTransform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getTextMove() {
/* 324 */     return this.textMove;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTextMove() {
/* 331 */     this.textMove = new float[] { 0.0F, 0.0F };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTextMove(float additionalMoveX, float additionalMoveY) {
/* 340 */     this.textMove[0] = this.textMove[0] + additionalMoveX;
/* 341 */     this.textMove[1] = this.textMove[1] + additionalMoveY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AffineTransform getCurrentCanvasTransform() {
/* 349 */     Matrix currentTransform = getCurrentCanvas().getGraphicsState().getCtm();
/* 350 */     if (currentTransform != null) {
/* 351 */       return new AffineTransform(currentTransform.get(0), currentTransform.get(1), currentTransform
/* 352 */           .get(3), currentTransform.get(4), currentTransform.get(6), currentTransform.get(7));
/*     */     }
/* 354 */     return new AffineTransform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRemValue() {
/* 363 */     return this.remValue;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/SvgDrawContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */