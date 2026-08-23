/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.ColorConstants;
/*     */ import com.itextpdf.kernel.colors.WebColors;
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*     */ import com.itextpdf.layout.property.TransparentColor;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.MarkerVertexType;
/*     */ import com.itextpdf.svg.renderers.IMarkerCapable;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.TransformUtils;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSvgNodeRenderer
/*     */   implements ISvgNodeRenderer
/*     */ {
/*  77 */   private static final MarkerVertexType[] MARKER_VERTEX_TYPES = new MarkerVertexType[] { MarkerVertexType.MARKER_START, MarkerVertexType.MARKER_END };
/*     */ 
/*     */   
/*     */   protected Map<String, String> attributesAndStyles;
/*     */ 
/*     */   
/*     */   boolean partOfClipPath;
/*     */   
/*     */   boolean doFill = false;
/*     */   
/*     */   boolean doStroke = false;
/*     */   
/*     */   private ISvgNodeRenderer parent;
/*     */ 
/*     */   
/*     */   public void setParent(ISvgNodeRenderer parent) {
/*  93 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer getParent() {
/*  98 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttributesAndStyles(Map<String, String> attributesAndStyles) {
/* 103 */     this.attributesAndStyles = attributesAndStyles;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(String key) {
/* 108 */     return this.attributesAndStyles.get(key);
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
/*     */   public String getAttributeOrDefault(String key, String defaultValue) {
/* 121 */     String rawValue = getAttribute(key);
/* 122 */     return (rawValue != null) ? rawValue : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttribute(String key, String value) {
/* 127 */     if (this.attributesAndStyles == null) {
/* 128 */       this.attributesAndStyles = new HashMap<>();
/*     */     }
/*     */     
/* 131 */     this.attributesAndStyles.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> getAttributeMapCopy() {
/* 136 */     HashMap<String, String> copy = new HashMap<>();
/* 137 */     if (this.attributesAndStyles == null) {
/* 138 */       return copy;
/*     */     }
/* 140 */     copy.putAll(this.attributesAndStyles);
/* 141 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void draw(SvgDrawContext context) {
/* 152 */     PdfCanvas currentCanvas = context.getCurrentCanvas();
/*     */     
/* 154 */     if (this.attributesAndStyles != null) {
/* 155 */       String transformString = this.attributesAndStyles.get("transform");
/*     */       
/* 157 */       if (transformString != null && !transformString.isEmpty()) {
/* 158 */         AffineTransform transformation = TransformUtils.parseTransform(transformString);
/* 159 */         if (!transformation.isIdentity()) {
/* 160 */           currentCanvas.concatMatrix(transformation);
/*     */         }
/*     */       } 
/*     */       
/* 164 */       if (this.attributesAndStyles.containsKey("id")) {
/* 165 */         context.addUsedId(this.attributesAndStyles.get("id"));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     if (!drawInClipPath(context)) {
/* 173 */       preDraw(context);
/* 174 */       doDraw(context);
/* 175 */       postDraw(context);
/*     */     } 
/*     */     
/* 178 */     if (this.attributesAndStyles.containsKey("id")) {
/* 179 */       context.removeUsedId(this.attributesAndStyles.get("id"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canElementFill() {
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConstructViewPort() {
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentFontSize() {
/* 209 */     return CssUtils.parseAbsoluteFontSize(getAttribute("font-size"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deepCopyAttributesAndStyles(ISvgNodeRenderer deepCopy) {
/* 219 */     Map<String, String> stylesDeepCopy = new HashMap<>();
/* 220 */     if (this.attributesAndStyles != null) {
/* 221 */       stylesDeepCopy.putAll(this.attributesAndStyles);
/* 222 */       deepCopy.setAttributesAndStyles(stylesDeepCopy);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doDraw(SvgDrawContext paramSvgDrawContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Rectangle getObjectBoundingBox(SvgDrawContext context) {
/* 242 */     return null;
/*     */   }
/*     */   
/*     */   static float getAlphaFromRGBA(String value) {
/*     */     try {
/* 247 */       return WebColors.getRGBAColor(value)[3];
/* 248 */     } catch (ArrayIndexOutOfBoundsException|NullPointerException exc) {
/* 249 */       return 1.0F;
/*     */     } 
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
/*     */   AffineTransform calculateViewPortTranslation(SvgDrawContext context) {
/* 262 */     Rectangle viewPort = context.getCurrentViewPort();
/*     */     
/* 264 */     AffineTransform transform = AffineTransform.getTranslateInstance(viewPort.getX(), viewPort.getY());
/* 265 */     return transform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void postDraw(SvgDrawContext context) {
/* 276 */     if (this.attributesAndStyles != null) {
/* 277 */       PdfCanvas currentCanvas = context.getCurrentCanvas();
/*     */ 
/*     */       
/* 280 */       if (this.partOfClipPath) {
/* 281 */         if ("evenodd"
/* 282 */           .equalsIgnoreCase(getAttribute("clip-rule"))) {
/* 283 */           currentCanvas.eoClip();
/*     */         } else {
/* 285 */           currentCanvas.clip();
/*     */         } 
/* 287 */         currentCanvas.endPath();
/*     */       }
/* 289 */       else if (this.doFill && canElementFill()) {
/* 290 */         String fillRuleRawValue = getAttribute("fill-rule");
/*     */         
/* 292 */         if ("evenodd".equalsIgnoreCase(fillRuleRawValue)) {
/* 293 */           if (this.doStroke) {
/* 294 */             currentCanvas.eoFillStroke();
/*     */           } else {
/* 296 */             currentCanvas.eoFill();
/*     */           }
/*     */         
/* 299 */         } else if (this.doStroke) {
/* 300 */           currentCanvas.fillStroke();
/*     */         } else {
/* 302 */           currentCanvas.fill();
/*     */         }
/*     */       
/* 305 */       } else if (this.doStroke) {
/* 306 */         currentCanvas.stroke();
/* 307 */       } else if (!TextSvgBranchRenderer.class.isInstance(this)) {
/* 308 */         currentCanvas.endPath();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 313 */       if (this instanceof IMarkerCapable)
/*     */       {
/* 315 */         for (MarkerVertexType markerVertexType : MARKER_VERTEX_TYPES) {
/* 316 */           if (this.attributesAndStyles.containsKey(markerVertexType.toString())) {
/* 317 */             currentCanvas.saveState();
/* 318 */             ((IMarkerCapable)this).drawMarker(context, markerVertexType);
/* 319 */             currentCanvas.restoreState();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void setPartOfClipPath(boolean value) {
/* 327 */     this.partOfClipPath = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void preDraw(SvgDrawContext context) {
/* 337 */     if (this.attributesAndStyles != null) {
/* 338 */       PdfCanvas currentCanvas = context.getCurrentCanvas();
/*     */       
/* 340 */       PdfExtGState opacityGraphicsState = new PdfExtGState();
/* 341 */       if (!this.partOfClipPath) {
/* 342 */         float generalOpacity = getOpacity();
/*     */ 
/*     */         
/* 345 */         String fillRawValue = getAttributeOrDefault("fill", "black");
/* 346 */         this.doFill = !"none".equalsIgnoreCase(fillRawValue);
/*     */         
/* 348 */         if (this.doFill && canElementFill()) {
/*     */           
/* 350 */           float fillOpacity = getOpacityByAttributeName("fill-opacity", generalOpacity);
/*     */ 
/*     */           
/* 353 */           Color fillColor = null;
/* 354 */           TransparentColor transparentColor = getColorFromAttributeValue(context, fillRawValue, 0.0F, fillOpacity);
/*     */           
/* 356 */           if (transparentColor != null) {
/* 357 */             fillColor = transparentColor.getColor();
/* 358 */             fillOpacity = transparentColor.getOpacity();
/*     */           } 
/*     */           
/* 361 */           if (!CssUtils.compareFloats(fillOpacity, 1.0F)) {
/* 362 */             opacityGraphicsState.setFillOpacity(fillOpacity);
/*     */           }
/*     */ 
/*     */           
/* 366 */           if (fillColor == null) {
/* 367 */             fillColor = ColorConstants.BLACK;
/*     */           }
/* 369 */           currentCanvas.setFillColor(fillColor);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 374 */         String strokeRawValue = getAttributeOrDefault("stroke", "none");
/*     */ 
/*     */         
/* 377 */         if (!"none".equalsIgnoreCase(strokeRawValue)) {
/* 378 */           String strokeWidthRawValue = getAttribute("stroke-width");
/*     */ 
/*     */           
/* 381 */           float strokeWidth = 0.75F;
/*     */           
/* 383 */           if (strokeWidthRawValue != null) {
/* 384 */             strokeWidth = CssUtils.parseAbsoluteLength(strokeWidthRawValue);
/*     */           }
/*     */           
/* 387 */           float strokeOpacity = getOpacityByAttributeName("stroke-opacity", generalOpacity);
/*     */ 
/*     */           
/* 390 */           Color strokeColor = null;
/* 391 */           TransparentColor transparentColor = getColorFromAttributeValue(context, strokeRawValue, strokeWidth / 2.0F, strokeOpacity);
/*     */           
/* 393 */           if (transparentColor != null) {
/* 394 */             strokeColor = transparentColor.getColor();
/* 395 */             strokeOpacity = transparentColor.getOpacity();
/*     */           } 
/*     */           
/* 398 */           if (!CssUtils.compareFloats(strokeOpacity, 1.0F)) {
/* 399 */             opacityGraphicsState.setStrokeOpacity(strokeOpacity);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 404 */           if (strokeColor != null) {
/* 405 */             currentCanvas.setStrokeColor(strokeColor);
/*     */           }
/*     */           
/* 408 */           currentCanvas.setLineWidth(strokeWidth);
/* 409 */           this.doStroke = true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 414 */         if (!((PdfDictionary)opacityGraphicsState.getPdfObject()).isEmpty()) {
/* 415 */           currentCanvas.setExtGState(opacityGraphicsState);
/*     */         }
/*     */       } 
/*     */     } 
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
/*     */   protected float parseAbsoluteLength(String length, float percentRelativeValue, float defaultValue, SvgDrawContext context) {
/* 432 */     if (CssUtils.isPercentageValue(length)) {
/* 433 */       return CssUtils.parseRelativeValue(length, percentRelativeValue);
/*     */     }
/* 435 */     float em = getCurrentFontSize();
/* 436 */     float rem = context.getRemValue();
/* 437 */     UnitValue unitValue = CssUtils.parseLengthValueToPt(length, em, rem);
/* 438 */     if (unitValue != null && unitValue.isPointValue()) {
/* 439 */       return unitValue.getValue();
/*     */     }
/* 441 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TransparentColor getColorFromAttributeValue(SvgDrawContext context, String rawColorValue, float objectBoundingBoxMargin, float parentOpacity) {
/* 448 */     if (rawColorValue == null) {
/* 449 */       return null;
/*     */     }
/* 451 */     CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(rawColorValue);
/* 452 */     CssDeclarationValueTokenizer.Token token = tokenizer.getNextValidToken();
/* 453 */     if (token == null) {
/* 454 */       return null;
/*     */     }
/* 456 */     String tokenValue = token.getValue();
/* 457 */     if (tokenValue.startsWith("url(#") && tokenValue.endsWith(")")) {
/* 458 */       Color resolvedColor = null;
/* 459 */       float resolvedOpacity = 1.0F;
/* 460 */       String normalizedName = tokenValue.substring(5, tokenValue.length() - 1).trim();
/* 461 */       ISvgNodeRenderer colorRenderer = context.getNamedObject(normalizedName);
/* 462 */       if (colorRenderer instanceof AbstractGradientSvgNodeRenderer) {
/* 463 */         resolvedColor = ((AbstractGradientSvgNodeRenderer)colorRenderer).createColor(context, 
/* 464 */             getObjectBoundingBox(context), objectBoundingBoxMargin, parentOpacity);
/*     */       }
/* 466 */       if (resolvedColor != null) {
/* 467 */         return new TransparentColor(resolvedColor, resolvedOpacity);
/*     */       }
/* 469 */       token = tokenizer.getNextValidToken();
/*     */     } 
/*     */     
/* 472 */     if (token != null) {
/* 473 */       String value = token.getValue();
/* 474 */       if (!"none".equalsIgnoreCase(value)) {
/* 475 */         return new TransparentColor((Color)WebColors.getRGBColor(value), parentOpacity * 
/* 476 */             getAlphaFromRGBA(value));
/*     */       }
/*     */     } 
/* 479 */     return null;
/*     */   }
/*     */   
/*     */   private float getOpacityByAttributeName(String attributeName, float generalOpacity) {
/* 483 */     float opacity = generalOpacity;
/*     */     
/* 485 */     String opacityValue = getAttribute(attributeName);
/* 486 */     if (opacityValue != null && !"none".equalsIgnoreCase(opacityValue)) {
/* 487 */       opacity *= Float.valueOf(opacityValue).floatValue();
/*     */     }
/* 489 */     return opacity;
/*     */   }
/*     */   
/*     */   private boolean drawInClipPath(SvgDrawContext context) {
/* 493 */     if (this.attributesAndStyles.containsKey("clip-path")) {
/* 494 */       String clipPathName = this.attributesAndStyles.get("clip-path");
/* 495 */       ISvgNodeRenderer template = context.getNamedObject(normalizeLocalUrlName(clipPathName));
/*     */       
/* 497 */       if (template instanceof ClipPathSvgNodeRenderer) {
/* 498 */         ClipPathSvgNodeRenderer clipPath = (ClipPathSvgNodeRenderer)template.createDeepCopy();
/* 499 */         clipPath.setClippedRenderer(this);
/* 500 */         clipPath.draw(context);
/* 501 */         return !clipPath.getChildren().isEmpty();
/*     */       } 
/*     */     } 
/* 504 */     return false;
/*     */   }
/*     */   
/*     */   private String normalizeLocalUrlName(String name) {
/* 508 */     return name.replace("url(#", "").replace(")", "").trim();
/*     */   }
/*     */   
/*     */   private float getOpacity() {
/* 512 */     float result = 1.0F;
/*     */     
/* 514 */     String opacityValue = getAttribute("opacity");
/* 515 */     if (opacityValue != null && !"none".equalsIgnoreCase(opacityValue)) {
/* 516 */       result = Float.valueOf(opacityValue).floatValue();
/*     */     }
/* 518 */     if (this.parent != null && this.parent instanceof AbstractSvgNodeRenderer) {
/* 519 */       result *= ((AbstractSvgNodeRenderer)this.parent).getOpacity();
/*     */     }
/*     */     
/* 522 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/AbstractSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */