/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.element.Image;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.layout.MinMaxWidthLayoutResult;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*     */ import com.itextpdf.layout.property.FloatPropertyValue;
/*     */ import com.itextpdf.layout.property.ObjectFit;
/*     */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.renderer.objectfit.ObjectFitApplyingResult;
/*     */ import com.itextpdf.layout.renderer.objectfit.ObjectFitCalculator;
/*     */ import com.itextpdf.layout.tagging.LayoutTaggingHelper;
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
/*     */ public class ImageRenderer
/*     */   extends AbstractRenderer
/*     */   implements ILeafElementRenderer
/*     */ {
/*     */   protected Float fixedXPosition;
/*     */   protected Float fixedYPosition;
/*     */   protected float pivotY;
/*     */   protected float deltaX;
/*     */   protected float imageWidth;
/*     */   protected float imageHeight;
/*  87 */   float[] matrix = new float[6];
/*     */   
/*     */   private Float height;
/*     */   
/*     */   private Float width;
/*     */   
/*     */   private float renderedImageHeight;
/*     */   
/*     */   private float renderedImageWidth;
/*     */   
/*     */   private boolean doesObjectFitRequireCutting;
/*     */   private Rectangle initialOccupiedAreaBBox;
/*     */   private float rotatedDeltaX;
/*     */   private float rotatedDeltaY;
/*     */   
/*     */   public ImageRenderer(Image image) {
/* 103 */     super((IElement)image);
/*     */   }
/*     */   
/*     */   public LayoutResult layout(LayoutContext layoutContext) {
/*     */     float imageItselfWidth, imageItselfHeight;
/* 108 */     LayoutArea area = layoutContext.getArea().clone();
/* 109 */     Rectangle layoutBox = area.getBBox().clone();
/*     */     
/* 111 */     AffineTransform t = new AffineTransform();
/* 112 */     Image modelElement = (Image)getModelElement();
/* 113 */     PdfXObject xObject = modelElement.getXObject();
/* 114 */     this.imageWidth = modelElement.getImageWidth();
/* 115 */     this.imageHeight = modelElement.getImageHeight();
/*     */     
/* 117 */     calculateImageDimensions(layoutBox, t, xObject);
/*     */ 
/*     */     
/* 120 */     OverflowPropertyValue overflowX = (null != this.parent) ? (OverflowPropertyValue)this.parent.getProperty(103) : OverflowPropertyValue.FIT;
/*     */ 
/*     */     
/* 123 */     boolean nowrap = false;
/* 124 */     if (this.parent instanceof LineRenderer) {
/* 125 */       nowrap = Boolean.TRUE.equals(this.parent.getOwnProperty(118));
/*     */     }
/*     */     
/* 128 */     List<Rectangle> floatRendererAreas = layoutContext.getFloatRendererAreas();
/* 129 */     float clearHeightCorrection = FloatingHelper.calculateClearHeightCorrection(this, floatRendererAreas, layoutBox);
/* 130 */     FloatPropertyValue floatPropertyValue = getProperty(99);
/* 131 */     if (FloatingHelper.isRendererFloating(this, floatPropertyValue)) {
/* 132 */       layoutBox.decreaseHeight(clearHeightCorrection);
/* 133 */       FloatingHelper.adjustFloatedBlockLayoutBox(this, layoutBox, this.width, floatRendererAreas, floatPropertyValue, overflowX);
/*     */     } else {
/* 135 */       clearHeightCorrection = FloatingHelper.adjustLayoutBoxAccordingToFloats(floatRendererAreas, layoutBox, this.width, clearHeightCorrection, null);
/*     */     } 
/*     */     
/* 138 */     applyMargins(layoutBox, false);
/* 139 */     Border[] borders = getBorders();
/* 140 */     applyBorderBox(layoutBox, borders, false);
/*     */     
/* 142 */     Float declaredMaxHeight = retrieveMaxHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     OverflowPropertyValue overflowY = (null == this.parent || ((null == declaredMaxHeight || declaredMaxHeight.floatValue() > layoutBox.getHeight()) && !layoutContext.isClippedHeight())) ? OverflowPropertyValue.FIT : (OverflowPropertyValue)this.parent.getProperty(104);
/* 148 */     boolean processOverflowX = (!isOverflowFit(overflowX) || nowrap);
/* 149 */     boolean processOverflowY = !isOverflowFit(overflowY);
/* 150 */     if (isAbsolutePosition()) {
/* 151 */       applyAbsolutePosition(layoutBox);
/*     */     }
/* 153 */     this.occupiedArea = new LayoutArea(area.getPageNumber(), new Rectangle(layoutBox.getX(), layoutBox.getY() + layoutBox.getHeight(), 0.0F, 0.0F));
/*     */     
/* 155 */     float imageContainerWidth = this.width.floatValue();
/* 156 */     float imageContainerHeight = this.height.floatValue();
/*     */     
/* 158 */     if (isFixedLayout()) {
/* 159 */       this.fixedXPosition = getPropertyAsFloat(34);
/* 160 */       this.fixedYPosition = getPropertyAsFloat(14);
/*     */     } 
/*     */     
/* 163 */     Float angle = getPropertyAsFloat(55);
/*     */     
/* 165 */     if (null == angle) {
/* 166 */       angle = Float.valueOf(0.0F);
/*     */     }
/* 168 */     t.rotate(angle.floatValue());
/* 169 */     this.initialOccupiedAreaBBox = getOccupiedAreaBBox().clone();
/* 170 */     float scaleCoef = adjustPositionAfterRotation(angle.floatValue(), layoutBox.getWidth(), layoutBox.getHeight());
/*     */     
/* 172 */     imageContainerHeight *= scaleCoef;
/* 173 */     imageContainerWidth *= scaleCoef;
/*     */     
/* 175 */     this.initialOccupiedAreaBBox.moveDown(imageContainerHeight);
/* 176 */     this.initialOccupiedAreaBBox.setHeight(imageContainerHeight);
/* 177 */     this.initialOccupiedAreaBBox.setWidth(imageContainerWidth);
/* 178 */     if (xObject instanceof com.itextpdf.kernel.pdf.xobject.PdfFormXObject) {
/* 179 */       t.scale(scaleCoef, scaleCoef);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     applyObjectFit(modelElement.getObjectFit(), this.imageWidth, this.imageHeight);
/* 186 */     if (modelElement.getObjectFit() == ObjectFit.FILL) {
/* 187 */       imageItselfWidth = imageContainerWidth;
/* 188 */       imageItselfHeight = imageContainerHeight;
/*     */     } else {
/* 190 */       imageItselfWidth = this.renderedImageWidth;
/* 191 */       imageItselfHeight = this.renderedImageHeight;
/*     */     } 
/* 193 */     getMatrix(t, imageItselfWidth, imageItselfHeight);
/*     */ 
/*     */     
/* 196 */     boolean isPlacingForced = false;
/* 197 */     if (this.width.floatValue() > layoutBox.getWidth() || this.height.floatValue() > layoutBox.getHeight()) {
/* 198 */       if (Boolean.TRUE.equals(getPropertyAsBoolean(26)) || (this.width.floatValue() > layoutBox.getWidth() && processOverflowX) || (this.height.floatValue() > layoutBox.getHeight() && processOverflowY)) {
/* 199 */         isPlacingForced = true;
/*     */       } else {
/* 201 */         applyMargins(this.initialOccupiedAreaBBox, true);
/* 202 */         applyBorderBox(this.initialOccupiedAreaBBox, true);
/* 203 */         this.occupiedArea.getBBox().setHeight(this.initialOccupiedAreaBBox.getHeight());
/* 204 */         return (LayoutResult)new MinMaxWidthLayoutResult(3, this.occupiedArea, null, this, this);
/*     */       } 
/*     */     }
/*     */     
/* 208 */     this.occupiedArea.getBBox().moveDown(this.height.floatValue());
/* 209 */     if (borders[3] != null) {
/* 210 */       float delta = (float)Math.sin(angle.floatValue()) * borders[3].getWidth();
/* 211 */       float renderScaling = this.renderedImageHeight / this.height.floatValue();
/* 212 */       this.height = Float.valueOf(this.height.floatValue() + delta);
/* 213 */       this.renderedImageHeight += delta * renderScaling;
/*     */     } 
/* 215 */     this.occupiedArea.getBBox().setHeight(this.height.floatValue());
/* 216 */     this.occupiedArea.getBBox().setWidth(this.width.floatValue());
/*     */     
/* 218 */     UnitValue leftMargin = getPropertyAsUnitValue(44);
/* 219 */     if (!leftMargin.isPointValue()) {
/* 220 */       Logger logger = LoggerFactory.getLogger(ImageRenderer.class);
/* 221 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*     */     } 
/* 223 */     UnitValue topMargin = getPropertyAsUnitValue(46);
/* 224 */     if (!topMargin.isPointValue()) {
/* 225 */       Logger logger = LoggerFactory.getLogger(ImageRenderer.class);
/* 226 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(46) }));
/*     */     } 
/*     */     
/* 229 */     if (0.0F != leftMargin.getValue() || 0.0F != topMargin.getValue()) {
/* 230 */       translateImage(leftMargin.getValue(), topMargin.getValue(), t);
/* 231 */       getMatrix(t, imageContainerWidth, imageContainerHeight);
/*     */     } 
/*     */     
/* 234 */     applyBorderBox(this.occupiedArea.getBBox(), borders, true);
/* 235 */     applyMargins(this.occupiedArea.getBBox(), true);
/*     */     
/* 237 */     if (angle.floatValue() != 0.0F) {
/* 238 */       applyRotationLayout(angle.floatValue());
/*     */     }
/*     */     
/* 241 */     float unscaledWidth = this.occupiedArea.getBBox().getWidth() / scaleCoef;
/* 242 */     MinMaxWidth minMaxWidth = new MinMaxWidth(unscaledWidth, unscaledWidth, 0.0F);
/* 243 */     UnitValue rendererWidth = getProperty(77);
/*     */     
/* 245 */     if (rendererWidth != null && rendererWidth.isPercentValue()) {
/* 246 */       minMaxWidth.setChildrenMinWidth(0.0F);
/* 247 */       float coeff = this.imageWidth / retrieveWidth(area.getBBox().getWidth()).floatValue();
/* 248 */       minMaxWidth.setChildrenMaxWidth(unscaledWidth * coeff);
/*     */     } else {
/* 250 */       boolean autoScale = (hasProperty(3) && ((Boolean)getProperty(3)).booleanValue());
/* 251 */       boolean autoScaleWidth = (hasProperty(5) && ((Boolean)getProperty(5)).booleanValue());
/* 252 */       if (autoScale || autoScaleWidth) {
/* 253 */         minMaxWidth.setChildrenMinWidth(0.0F);
/*     */       }
/*     */     } 
/*     */     
/* 257 */     FloatingHelper.removeFloatsAboveRendererBottom(floatRendererAreas, this);
/* 258 */     LayoutArea editedArea = FloatingHelper.adjustResultOccupiedAreaForFloatAndClear(this, floatRendererAreas, layoutContext.getArea().getBBox(), clearHeightCorrection, false);
/*     */     
/* 260 */     applyAbsolutePositionIfNeeded(layoutContext);
/*     */     
/* 262 */     return (LayoutResult)(new MinMaxWidthLayoutResult(1, editedArea, null, null, isPlacingForced ? this : null))
/* 263 */       .setMinMaxWidth(minMaxWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(DrawContext drawContext) {
/* 268 */     if (this.occupiedArea == null) {
/* 269 */       Logger logger = LoggerFactory.getLogger(ImageRenderer.class);
/* 270 */       logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Drawing won't be performed." }));
/*     */       
/*     */       return;
/*     */     } 
/* 274 */     boolean isRelativePosition = isRelativePosition();
/* 275 */     if (isRelativePosition) {
/* 276 */       applyRelativePositioningTranslation(false);
/*     */     }
/*     */     
/* 279 */     boolean isTagged = drawContext.isTaggingEnabled();
/* 280 */     LayoutTaggingHelper taggingHelper = null;
/* 281 */     boolean isArtifact = false;
/* 282 */     TagTreePointer tagPointer = null;
/* 283 */     if (isTagged) {
/* 284 */       taggingHelper = getProperty(108);
/* 285 */       if (taggingHelper == null) {
/* 286 */         isArtifact = true;
/*     */       } else {
/* 288 */         isArtifact = taggingHelper.isArtifact(this);
/* 289 */         if (!isArtifact) {
/* 290 */           tagPointer = taggingHelper.useAutoTaggingPointerAndRememberItsPosition(this);
/* 291 */           if (taggingHelper.createTag(this, tagPointer)) {
/* 292 */             tagPointer.getProperties().addAttributes(0, AccessibleAttributesApplier.getLayoutAttributes(this, tagPointer));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     beginTransformationIfApplied(drawContext.getCanvas());
/*     */     
/* 300 */     Float angle = getPropertyAsFloat(55);
/* 301 */     if (angle != null) {
/* 302 */       drawContext.getCanvas().saveState();
/* 303 */       applyConcatMatrix(drawContext, angle);
/*     */     } 
/*     */     
/* 306 */     super.draw(drawContext);
/*     */     
/* 308 */     boolean clipImageInAViewOfBorderRadius = clipBackgroundArea(drawContext, applyMargins(getOccupiedAreaBBox(), false), true);
/* 309 */     applyMargins(this.occupiedArea.getBBox(), false);
/* 310 */     applyBorderBox(this.occupiedArea.getBBox(), getBorders(), false);
/*     */     
/* 312 */     if (this.fixedYPosition == null) {
/* 313 */       this.fixedYPosition = Float.valueOf(this.occupiedArea.getBBox().getY() + this.pivotY);
/*     */     }
/* 315 */     if (this.fixedXPosition == null) {
/* 316 */       this.fixedXPosition = Float.valueOf(this.occupiedArea.getBBox().getX());
/*     */     }
/*     */     
/* 319 */     if (angle != null) {
/* 320 */       this.fixedXPosition = Float.valueOf(this.fixedXPosition.floatValue() + this.rotatedDeltaX);
/* 321 */       this.fixedYPosition = Float.valueOf(this.fixedYPosition.floatValue() - this.rotatedDeltaY);
/* 322 */       drawContext.getCanvas().restoreState();
/*     */     } 
/* 324 */     PdfCanvas canvas = drawContext.getCanvas();
/* 325 */     if (isTagged) {
/* 326 */       if (isArtifact) {
/* 327 */         canvas.openTag((CanvasTag)new CanvasArtifact());
/*     */       } else {
/* 329 */         canvas.openTag(tagPointer.getTagReference());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 334 */     beginObjectFitImageClipping(canvas);
/*     */     
/* 336 */     PdfXObject xObject = ((Image)getModelElement()).getXObject();
/* 337 */     beginElementOpacityApplying(drawContext);
/*     */     
/* 339 */     float renderedImageShiftX = (this.width.floatValue() - this.renderedImageWidth) / 2.0F;
/* 340 */     float renderedImageShiftY = (this.height.floatValue() - this.renderedImageHeight) / 2.0F;
/* 341 */     canvas.addXObject(xObject, this.matrix[0], this.matrix[1], this.matrix[2], this.matrix[3], this.fixedXPosition.floatValue() + this.deltaX + renderedImageShiftX, this.fixedYPosition
/* 342 */         .floatValue() + renderedImageShiftY);
/*     */     
/* 344 */     endElementOpacityApplying(drawContext);
/* 345 */     endObjectFitImageClipping(canvas);
/* 346 */     endTransformationIfApplied(drawContext.getCanvas());
/*     */     
/* 348 */     if (Boolean.TRUE.equals(getPropertyAsBoolean(19))) {
/* 349 */       xObject.flush();
/*     */     }
/*     */     
/* 352 */     if (isTagged) {
/* 353 */       canvas.closeTag();
/*     */     }
/*     */     
/* 356 */     if (clipImageInAViewOfBorderRadius) {
/* 357 */       canvas.restoreState();
/*     */     }
/*     */     
/* 360 */     if (isRelativePosition) {
/* 361 */       applyRelativePositioningTranslation(true);
/*     */     }
/* 363 */     applyBorderBox(this.occupiedArea.getBBox(), getBorders(), true);
/* 364 */     applyMargins(this.occupiedArea.getBBox(), true);
/*     */     
/* 366 */     if (isTagged && !isArtifact) {
/* 367 */       taggingHelper.finishTaggingHint(this);
/* 368 */       taggingHelper.restoreAutoTaggingPointerPosition(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 374 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle getBorderAreaBBox() {
/* 379 */     applyMargins(this.initialOccupiedAreaBBox, false);
/* 380 */     applyBorderBox(this.initialOccupiedAreaBBox, getBorders(), false);
/*     */     
/* 382 */     boolean isRelativePosition = isRelativePosition();
/* 383 */     if (isRelativePosition) {
/* 384 */       applyRelativePositioningTranslation(false);
/*     */     }
/* 386 */     applyMargins(this.initialOccupiedAreaBBox, true);
/* 387 */     applyBorderBox(this.initialOccupiedAreaBBox, true);
/* 388 */     return this.initialOccupiedAreaBBox;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle applyPaddings(Rectangle rect, UnitValue[] paddings, boolean reverse) {
/* 393 */     return rect;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(float dxRight, float dyUp) {
/* 398 */     super.move(dxRight, dyUp);
/* 399 */     if (this.initialOccupiedAreaBBox != null) {
/* 400 */       this.initialOccupiedAreaBBox.moveRight(dxRight);
/* 401 */       this.initialOccupiedAreaBBox.moveUp(dyUp);
/*     */     } 
/* 403 */     if (this.fixedXPosition != null) {
/* 404 */       this.fixedXPosition = Float.valueOf(this.fixedXPosition.floatValue() + dxRight);
/*     */     }
/* 406 */     if (this.fixedYPosition != null) {
/* 407 */       this.fixedYPosition = Float.valueOf(this.fixedYPosition.floatValue() + dyUp);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MinMaxWidth getMinMaxWidth() {
/* 413 */     return ((MinMaxWidthLayoutResult)layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0F))))).getMinMaxWidth();
/*     */   }
/*     */   
/*     */   protected ImageRenderer autoScale(LayoutArea layoutArea) {
/* 417 */     Rectangle area = layoutArea.getBBox().clone();
/* 418 */     applyMargins(area, false);
/* 419 */     applyBorderBox(area, false);
/*     */     
/* 421 */     float angleScaleCoef = this.imageWidth / this.width.floatValue();
/* 422 */     if (this.width.floatValue() > angleScaleCoef * area.getWidth()) {
/* 423 */       updateHeight(UnitValue.createPointValue(area.getWidth() / this.width.floatValue() * this.imageHeight));
/* 424 */       updateWidth(UnitValue.createPointValue(angleScaleCoef * area.getWidth()));
/*     */     } 
/*     */     
/* 427 */     return this;
/*     */   }
/*     */   
/*     */   private void applyObjectFit(ObjectFit objectFit, float imageWidth, float imageHeight) {
/* 431 */     ObjectFitApplyingResult result = ObjectFitCalculator.calculateRenderedImageSize(objectFit, imageWidth, imageHeight, this.width
/* 432 */         .floatValue(), this.height.floatValue());
/* 433 */     this.renderedImageWidth = (float)result.getRenderedImageWidth();
/* 434 */     this.renderedImageHeight = (float)result.getRenderedImageHeight();
/* 435 */     this.doesObjectFitRequireCutting = result.isImageCuttingRequired();
/*     */   }
/*     */   
/*     */   private void beginObjectFitImageClipping(PdfCanvas canvas) {
/* 439 */     if (this.doesObjectFitRequireCutting) {
/* 440 */       canvas.saveState();
/*     */       
/* 442 */       Rectangle clippedArea = new Rectangle(this.fixedXPosition.floatValue(), this.fixedYPosition.floatValue(), this.width.floatValue(), this.height.floatValue());
/* 443 */       canvas.rectangle(clippedArea).clip().endPath();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void endObjectFitImageClipping(PdfCanvas canvas) {
/* 448 */     if (this.doesObjectFitRequireCutting) {
/* 449 */       canvas.restoreState();
/*     */     }
/*     */   }
/*     */   
/*     */   private void calculateImageDimensions(Rectangle layoutBox, AffineTransform t, PdfXObject xObject) {
/* 454 */     this.width = (getProperty(77) != null) ? retrieveWidth(layoutBox.getWidth()) : null;
/* 455 */     Float declaredHeight = retrieveHeight();
/* 456 */     this.height = declaredHeight;
/* 457 */     if (this.width == null && this.height == null) {
/* 458 */       this.width = Float.valueOf(this.imageWidth);
/* 459 */       this.height = Float.valueOf(this.width.floatValue() / this.imageWidth * this.imageHeight);
/* 460 */     } else if (this.width == null) {
/* 461 */       this.width = Float.valueOf(this.height.floatValue() / this.imageHeight * this.imageWidth);
/* 462 */     } else if (this.height == null) {
/* 463 */       this.height = Float.valueOf(this.width.floatValue() / this.imageWidth * this.imageHeight);
/*     */     } 
/*     */     
/* 466 */     Float horizontalScaling = getPropertyAsFloat(29, Float.valueOf(1.0F));
/* 467 */     Float verticalScaling = getPropertyAsFloat(76, Float.valueOf(1.0F));
/*     */ 
/*     */     
/* 470 */     if (xObject instanceof com.itextpdf.kernel.pdf.xobject.PdfFormXObject && this.width.floatValue() != this.imageWidth) {
/* 471 */       horizontalScaling = Float.valueOf(horizontalScaling.floatValue() * this.width.floatValue() / this.imageWidth);
/* 472 */       verticalScaling = Float.valueOf(verticalScaling.floatValue() * this.height.floatValue() / this.imageHeight);
/*     */     } 
/*     */     
/* 475 */     if (horizontalScaling.floatValue() != 1.0F) {
/* 476 */       if (xObject instanceof com.itextpdf.kernel.pdf.xobject.PdfFormXObject) {
/* 477 */         t.scale(horizontalScaling.floatValue(), 1.0D);
/* 478 */         this.width = Float.valueOf(this.imageWidth * horizontalScaling.floatValue());
/*     */       } else {
/* 480 */         this.width = Float.valueOf(this.width.floatValue() * horizontalScaling.floatValue());
/*     */       } 
/*     */     }
/* 483 */     if (verticalScaling.floatValue() != 1.0F) {
/* 484 */       if (xObject instanceof com.itextpdf.kernel.pdf.xobject.PdfFormXObject) {
/* 485 */         t.scale(1.0D, verticalScaling.floatValue());
/* 486 */         this.height = Float.valueOf(this.imageHeight * verticalScaling.floatValue());
/*     */       } else {
/* 488 */         this.height = Float.valueOf(this.height.floatValue() * verticalScaling.floatValue());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 493 */     Float minWidth = retrieveMinWidth(layoutBox.getWidth());
/* 494 */     Float maxWidth = retrieveMaxWidth(layoutBox.getWidth());
/* 495 */     if (null != minWidth && this.width.floatValue() < minWidth.floatValue()) {
/* 496 */       this.height = Float.valueOf(this.height.floatValue() * minWidth.floatValue() / this.width.floatValue());
/* 497 */       this.width = minWidth;
/* 498 */     } else if (null != maxWidth && this.width.floatValue() > maxWidth.floatValue()) {
/* 499 */       this.height = Float.valueOf(this.height.floatValue() * maxWidth.floatValue() / this.width.floatValue());
/* 500 */       this.width = maxWidth;
/*     */     } 
/*     */ 
/*     */     
/* 504 */     Float minHeight = retrieveMinHeight();
/* 505 */     Float maxHeight = retrieveMaxHeight();
/* 506 */     if (null != minHeight && this.height.floatValue() < minHeight.floatValue()) {
/* 507 */       this.width = Float.valueOf(this.width.floatValue() * minHeight.floatValue() / this.height.floatValue());
/* 508 */       this.height = minHeight;
/* 509 */     } else if (null != maxHeight && this.height.floatValue() > maxHeight.floatValue()) {
/* 510 */       this.width = Float.valueOf(this.width.floatValue() * maxHeight.floatValue() / this.height.floatValue());
/* 511 */       this.height = maxHeight;
/* 512 */     } else if (null != declaredHeight && !this.height.equals(declaredHeight)) {
/* 513 */       this.width = Float.valueOf(this.width.floatValue() * declaredHeight.floatValue() / this.height.floatValue());
/* 514 */       this.height = declaredHeight;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getMatrix(AffineTransform t, float imageItselfScaledWidth, float imageItselfScaledHeight) {
/* 519 */     t.getMatrix(this.matrix);
/* 520 */     PdfXObject xObject = ((Image)getModelElement()).getXObject();
/* 521 */     if (xObject instanceof com.itextpdf.kernel.pdf.xobject.PdfImageXObject) {
/* 522 */       this.matrix[0] = this.matrix[0] * imageItselfScaledWidth;
/* 523 */       this.matrix[1] = this.matrix[1] * imageItselfScaledWidth;
/* 524 */       this.matrix[2] = this.matrix[2] * imageItselfScaledHeight;
/* 525 */       this.matrix[3] = this.matrix[3] * imageItselfScaledHeight;
/*     */     } 
/*     */   }
/*     */   
/*     */   private float adjustPositionAfterRotation(float angle, float maxWidth, float maxHeight) {
/* 530 */     if (angle != 0.0F) {
/* 531 */       AffineTransform t = AffineTransform.getRotateInstance(angle);
/* 532 */       Point p00 = t.transform(new Point(0, 0), new Point());
/* 533 */       Point p01 = t.transform(new Point(0.0D, this.height.floatValue()), new Point());
/* 534 */       Point p10 = t.transform(new Point(this.width.floatValue(), 0.0D), new Point());
/* 535 */       Point p11 = t.transform(new Point(this.width.floatValue(), this.height.floatValue()), new Point());
/*     */       
/* 537 */       double[] xValues = { p01.getX(), p10.getX(), p11.getX() };
/* 538 */       double[] yValues = { p01.getY(), p10.getY(), p11.getY() };
/*     */       
/* 540 */       double minX = p00.getX();
/* 541 */       double minY = p00.getY();
/* 542 */       double maxX = minX;
/* 543 */       double maxY = minY;
/*     */       
/* 545 */       for (double x : xValues) {
/* 546 */         minX = Math.min(minX, x);
/* 547 */         maxX = Math.max(maxX, x);
/*     */       } 
/* 549 */       for (double y : yValues) {
/* 550 */         minY = Math.min(minY, y);
/* 551 */         maxY = Math.max(maxY, y);
/*     */       } 
/*     */       
/* 554 */       this.height = Float.valueOf((float)(maxY - minY));
/* 555 */       this.width = Float.valueOf((float)(maxX - minX));
/* 556 */       this.pivotY = (float)(p00.getY() - minY);
/*     */       
/* 558 */       this.deltaX = -((float)minX);
/*     */     } 
/*     */ 
/*     */     
/* 562 */     float scaleCoeff = 1.0F;
/* 563 */     if (Boolean.TRUE.equals(getPropertyAsBoolean(3))) {
/* 564 */       if (maxWidth / this.width.floatValue() < maxHeight / this.height.floatValue()) {
/* 565 */         scaleCoeff = maxWidth / this.width.floatValue();
/* 566 */         this.height = Float.valueOf(this.height.floatValue() * maxWidth / this.width.floatValue());
/* 567 */         this.width = Float.valueOf(maxWidth);
/*     */       } else {
/* 569 */         scaleCoeff = maxHeight / this.height.floatValue();
/* 570 */         this.width = Float.valueOf(this.width.floatValue() * maxHeight / this.height.floatValue());
/* 571 */         this.height = Float.valueOf(maxHeight);
/*     */       } 
/* 573 */     } else if (Boolean.TRUE.equals(getPropertyAsBoolean(5))) {
/* 574 */       scaleCoeff = maxWidth / this.width.floatValue();
/* 575 */       this.height = Float.valueOf(this.height.floatValue() * scaleCoeff);
/* 576 */       this.width = Float.valueOf(maxWidth);
/* 577 */     } else if (Boolean.TRUE.equals(getPropertyAsBoolean(4))) {
/* 578 */       scaleCoeff = maxHeight / this.height.floatValue();
/* 579 */       this.height = Float.valueOf(maxHeight);
/* 580 */       this.width = Float.valueOf(this.width.floatValue() * scaleCoeff);
/*     */     } 
/* 582 */     this.pivotY *= scaleCoeff;
/* 583 */     this.deltaX *= scaleCoeff;
/* 584 */     return scaleCoeff;
/*     */   }
/*     */   
/*     */   private void translateImage(float xDistance, float yDistance, AffineTransform t) {
/* 588 */     t.translate(xDistance, yDistance);
/* 589 */     t.getMatrix(this.matrix);
/* 590 */     if (this.fixedXPosition != null) {
/* 591 */       this.fixedXPosition = Float.valueOf(this.fixedXPosition.floatValue() + (float)t.getTranslateX());
/*     */     }
/* 593 */     if (this.fixedYPosition != null) {
/* 594 */       this.fixedYPosition = Float.valueOf(this.fixedYPosition.floatValue() + (float)t.getTranslateY());
/*     */     }
/*     */   }
/*     */   
/*     */   private void applyConcatMatrix(DrawContext drawContext, Float angle) {
/* 599 */     AffineTransform rotationTransform = AffineTransform.getRotateInstance(angle.floatValue());
/* 600 */     Rectangle rect = getBorderAreaBBox();
/*     */     
/* 602 */     List<Point> rotatedPoints = transformPoints(rectangleToPointsList(rect), rotationTransform);
/*     */     
/* 604 */     float[] shift = calculateShiftToPositionBBoxOfPointsAt(rect.getX(), rect.getY() + rect.getHeight(), rotatedPoints);
/*     */     
/* 606 */     double[] matrix = new double[6];
/* 607 */     rotationTransform.getMatrix(matrix);
/*     */     
/* 609 */     drawContext.getCanvas().concatMatrix(matrix[0], matrix[1], matrix[2], matrix[3], shift[0], shift[1]);
/*     */   }
/*     */   
/*     */   private void applyRotationLayout(float angle) {
/* 613 */     Border[] borders = getBorders();
/* 614 */     Rectangle rect = getBorderAreaBBox();
/*     */     
/* 616 */     float leftBorderWidth = (borders[3] == null) ? 0.0F : borders[3].getWidth();
/* 617 */     float rightBorderWidth = (borders[1] == null) ? 0.0F : borders[1].getWidth();
/* 618 */     float topBorderWidth = (borders[0] == null) ? 0.0F : borders[0].getWidth();
/* 619 */     if (leftBorderWidth != 0.0F) {
/* 620 */       float gip = (float)Math.sqrt(Math.pow(topBorderWidth, 2.0D) + Math.pow(leftBorderWidth, 2.0D));
/* 621 */       double atan = Math.atan((topBorderWidth / leftBorderWidth));
/* 622 */       if (angle < 0.0F) {
/* 623 */         atan = -atan;
/*     */       }
/* 625 */       this.rotatedDeltaX = Math.abs((float)(gip * Math.cos(angle - atan) - leftBorderWidth));
/*     */     } else {
/* 627 */       this.rotatedDeltaX = 0.0F;
/*     */     } 
/*     */     
/* 630 */     rect.moveRight(this.rotatedDeltaX);
/* 631 */     this.occupiedArea.getBBox().setWidth(this.occupiedArea.getBBox().getWidth() + this.rotatedDeltaX);
/*     */     
/* 633 */     if (rightBorderWidth != 0.0F) {
/* 634 */       float gip = (float)Math.sqrt(Math.pow(topBorderWidth, 2.0D) + Math.pow(leftBorderWidth, 2.0D));
/* 635 */       double atan = Math.atan((rightBorderWidth / topBorderWidth));
/* 636 */       if (angle < 0.0F) {
/* 637 */         atan = -atan;
/*     */       }
/* 639 */       this.rotatedDeltaY = Math.abs((float)(gip * Math.cos(angle - atan) - topBorderWidth));
/*     */     } else {
/* 641 */       this.rotatedDeltaY = 0.0F;
/*     */     } 
/*     */     
/* 644 */     rect.moveDown(this.rotatedDeltaY);
/* 645 */     if (angle < 0.0F) {
/* 646 */       this.rotatedDeltaY += rightBorderWidth;
/*     */     }
/* 648 */     this.occupiedArea.getBBox().increaseHeight(this.rotatedDeltaY);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAscent() {
/* 653 */     return this.occupiedArea.getBBox().getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDescent() {
/* 658 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/ImageRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */