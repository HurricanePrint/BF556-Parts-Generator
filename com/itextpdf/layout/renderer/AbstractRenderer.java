/*      */ package com.itextpdf.layout.renderer;
/*      */ 
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.io.util.NumberUtil;
/*      */ import com.itextpdf.kernel.colors.Color;
/*      */ import com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder;
/*      */ import com.itextpdf.kernel.font.PdfFont;
/*      */ import com.itextpdf.kernel.geom.AffineTransform;
/*      */ import com.itextpdf.kernel.geom.Point;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfPage;
/*      */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*      */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*      */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*      */ import com.itextpdf.layout.IPropertyContainer;
/*      */ import com.itextpdf.layout.borders.Border;
/*      */ import com.itextpdf.layout.element.Div;
/*      */ import com.itextpdf.layout.element.IElement;
/*      */ import com.itextpdf.layout.font.FontCharacteristics;
/*      */ import com.itextpdf.layout.font.FontFamilySplitter;
/*      */ import com.itextpdf.layout.font.FontProvider;
/*      */ import com.itextpdf.layout.font.FontSelector;
/*      */ import com.itextpdf.layout.font.FontSet;
/*      */ import com.itextpdf.layout.layout.LayoutArea;
/*      */ import com.itextpdf.layout.layout.LayoutContext;
/*      */ import com.itextpdf.layout.layout.PositionedLayoutContext;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*      */ import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
/*      */ import com.itextpdf.layout.property.Background;
/*      */ import com.itextpdf.layout.property.BackgroundBox;
/*      */ import com.itextpdf.layout.property.BackgroundImage;
/*      */ import com.itextpdf.layout.property.BaseDirection;
/*      */ import com.itextpdf.layout.property.BlendMode;
/*      */ import com.itextpdf.layout.property.BorderRadius;
/*      */ import com.itextpdf.layout.property.BoxSizingPropertyValue;
/*      */ import com.itextpdf.layout.property.HorizontalAlignment;
/*      */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*      */ import com.itextpdf.layout.property.Property;
/*      */ import com.itextpdf.layout.property.Transform;
/*      */ import com.itextpdf.layout.property.TransparentColor;
/*      */ import com.itextpdf.layout.property.UnitValue;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractRenderer
/*      */   implements IRenderer
/*      */ {
/*      */   public static final float OVERLAP_EPSILON = 1.0E-4F;
/*      */   protected static final float EPS = 1.0E-4F;
/*      */   protected static final float INF = 1000000.0F;
/*  128 */   protected List<IRenderer> childRenderers = new ArrayList<>();
/*  129 */   protected List<IRenderer> positionedRenderers = new ArrayList<>();
/*      */   protected IPropertyContainer modelElement;
/*      */   protected boolean flushed = false;
/*      */   protected LayoutArea occupiedArea;
/*      */   protected IRenderer parent;
/*  134 */   protected Map<Integer, Object> properties = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isLastRendererForModelElement = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractRenderer() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractRenderer(IElement modelElement) {
/*  149 */     this.modelElement = (IPropertyContainer)modelElement;
/*      */   }
/*      */   
/*      */   protected AbstractRenderer(AbstractRenderer other) {
/*  153 */     this.childRenderers = other.childRenderers;
/*  154 */     this.positionedRenderers = other.positionedRenderers;
/*  155 */     this.modelElement = other.modelElement;
/*  156 */     this.flushed = other.flushed;
/*  157 */     this.occupiedArea = (other.occupiedArea != null) ? other.occupiedArea.clone() : null;
/*  158 */     this.parent = other.parent;
/*  159 */     this.properties.putAll(other.properties);
/*  160 */     this.isLastRendererForModelElement = other.isLastRendererForModelElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChild(IRenderer renderer) {
/*  170 */     Integer positioning = (Integer)renderer.getProperty(52);
/*  171 */     if (positioning == null || positioning.intValue() == 2 || positioning.intValue() == 1) {
/*  172 */       this.childRenderers.add(renderer);
/*  173 */     } else if (positioning.intValue() == 4) {
/*  174 */       AbstractRenderer root = this;
/*  175 */       while (root.parent instanceof AbstractRenderer) {
/*  176 */         root = (AbstractRenderer)root.parent;
/*      */       }
/*  178 */       if (root == this) {
/*  179 */         this.positionedRenderers.add(renderer);
/*      */       } else {
/*  181 */         root.addChild(renderer);
/*      */       } 
/*  183 */     } else if (positioning.intValue() == 3) {
/*      */ 
/*      */ 
/*      */       
/*  187 */       AbstractRenderer positionedParent = this;
/*  188 */       boolean noPositionInfo = noAbsolutePositionInfo(renderer);
/*  189 */       while (!positionedParent.isPositioned() && !noPositionInfo) {
/*  190 */         IRenderer parent = positionedParent.parent;
/*  191 */         if (parent instanceof AbstractRenderer) {
/*  192 */           positionedParent = (AbstractRenderer)parent;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  197 */       if (positionedParent == this) {
/*  198 */         this.positionedRenderers.add(renderer);
/*      */       } else {
/*  200 */         positionedParent.addChild(renderer);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  205 */     if (renderer instanceof AbstractRenderer && !((AbstractRenderer)renderer).isPositioned() && ((AbstractRenderer)renderer).positionedRenderers.size() > 0) {
/*      */ 
/*      */ 
/*      */       
/*  209 */       int pos = 0;
/*  210 */       List<IRenderer> childPositionedRenderers = ((AbstractRenderer)renderer).positionedRenderers;
/*  211 */       while (pos < childPositionedRenderers.size()) {
/*  212 */         if (noAbsolutePositionInfo(childPositionedRenderers.get(pos))) {
/*  213 */           pos++; continue;
/*      */         } 
/*  215 */         this.positionedRenderers.add(childPositionedRenderers.get(pos));
/*  216 */         childPositionedRenderers.remove(pos);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IPropertyContainer getModelElement() {
/*  227 */     return this.modelElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<IRenderer> getChildRenderers() {
/*  235 */     return this.childRenderers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasProperty(int property) {
/*  243 */     return (hasOwnProperty(property) || (this.modelElement != null && this.modelElement
/*  244 */       .hasProperty(property)) || (this.parent != null && 
/*  245 */       Property.isPropertyInherited(property) && this.parent.hasProperty(property)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasOwnProperty(int property) {
/*  253 */     return this.properties.containsKey(Integer.valueOf(property));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasOwnOrModelProperty(int property) {
/*  264 */     return hasOwnOrModelProperty(this, property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteOwnProperty(int property) {
/*  272 */     this.properties.remove(Integer.valueOf(property));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteProperty(int property) {
/*  282 */     if (this.properties.containsKey(Integer.valueOf(property))) {
/*  283 */       this.properties.remove(Integer.valueOf(property));
/*      */     }
/*  285 */     else if (this.modelElement != null) {
/*  286 */       this.modelElement.deleteOwnProperty(property);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T1> T1 getProperty(int key) {
/*      */     Object property;
/*  297 */     if ((property = this.properties.get(Integer.valueOf(key))) != null || this.properties.containsKey(Integer.valueOf(key))) {
/*  298 */       return (T1)property;
/*      */     }
/*  300 */     if (this.modelElement != null && ((property = this.modelElement.getProperty(key)) != null || this.modelElement.hasProperty(key))) {
/*  301 */       return (T1)property;
/*      */     }
/*      */     
/*  304 */     if (this.parent != null && Property.isPropertyInherited(key) && (property = this.parent.getProperty(key)) != null) {
/*  305 */       return (T1)property;
/*      */     }
/*  307 */     property = getDefaultProperty(key);
/*  308 */     if (property != null) {
/*  309 */       return (T1)property;
/*      */     }
/*  311 */     return (this.modelElement != null) ? (T1)this.modelElement.getDefaultProperty(key) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T1> T1 getOwnProperty(int property) {
/*  319 */     return (T1)this.properties.get(Integer.valueOf(property));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T1> T1 getProperty(int property, T1 defaultValue) {
/*  327 */     T1 result = getProperty(property);
/*  328 */     return (result != null) ? result : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(int property, Object value) {
/*  336 */     this.properties.put(Integer.valueOf(property), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T1> T1 getDefaultProperty(int property) {
/*  344 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFont getPropertyAsFont(int property) {
/*  354 */     return getProperty(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getPropertyAsColor(int property) {
/*  364 */     return getProperty(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransparentColor getPropertyAsTransparentColor(int property) {
/*  374 */     return getProperty(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float getPropertyAsFloat(int property) {
/*  384 */     return NumberUtil.asFloat(getProperty(property));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float getPropertyAsFloat(int property, Float defaultValue) {
/*  395 */     return NumberUtil.asFloat(getProperty(property, defaultValue));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean getPropertyAsBoolean(int property) {
/*  405 */     return getProperty(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UnitValue getPropertyAsUnitValue(int property) {
/*  415 */     return getProperty(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPropertyAsInteger(int property) {
/*  425 */     return NumberUtil.asInteger(getProperty(property));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  436 */     StringBuilder sb = new StringBuilder();
/*  437 */     for (IRenderer renderer : this.childRenderers) {
/*  438 */       sb.append(renderer.toString());
/*      */     }
/*  440 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutArea getOccupiedArea() {
/*  448 */     return this.occupiedArea;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(DrawContext drawContext) {
/*  456 */     applyDestinationsAndAnnotation(drawContext);
/*      */     
/*  458 */     boolean relativePosition = isRelativePosition();
/*  459 */     if (relativePosition) {
/*  460 */       applyRelativePositioningTranslation(false);
/*      */     }
/*      */     
/*  463 */     beginElementOpacityApplying(drawContext);
/*  464 */     drawBackground(drawContext);
/*  465 */     drawBorder(drawContext);
/*  466 */     drawChildren(drawContext);
/*  467 */     drawPositionedChildren(drawContext);
/*  468 */     endElementOpacityApplying(drawContext);
/*      */     
/*  470 */     if (relativePosition) {
/*  471 */       applyRelativePositioningTranslation(true);
/*      */     }
/*      */     
/*  474 */     this.flushed = true;
/*      */   }
/*      */   
/*      */   protected void beginElementOpacityApplying(DrawContext drawContext) {
/*  478 */     Float opacity = getPropertyAsFloat(92);
/*  479 */     if (opacity != null && opacity.floatValue() < 1.0F) {
/*  480 */       PdfExtGState extGState = new PdfExtGState();
/*  481 */       extGState
/*  482 */         .setStrokeOpacity(opacity.floatValue())
/*  483 */         .setFillOpacity(opacity.floatValue());
/*  484 */       drawContext.getCanvas()
/*  485 */         .saveState()
/*  486 */         .setExtGState(extGState);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void endElementOpacityApplying(DrawContext drawContext) {
/*  491 */     Float opacity = getPropertyAsFloat(92);
/*  492 */     if (opacity != null && opacity.floatValue() < 1.0F) {
/*  493 */       drawContext.getCanvas().restoreState();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawBackground(DrawContext drawContext) {
/*      */     List<BackgroundImage> backgroundImagesList;
/*  504 */     Background background = getProperty(6);
/*  505 */     Object uncastedBackgroundImage = getProperty(90);
/*      */ 
/*      */     
/*  508 */     if (uncastedBackgroundImage instanceof BackgroundImage) {
/*  509 */       backgroundImagesList = Collections.singletonList((BackgroundImage)uncastedBackgroundImage);
/*      */     } else {
/*  511 */       backgroundImagesList = getProperty(90);
/*      */     } 
/*  513 */     if (background != null || backgroundImagesList != null) {
/*  514 */       Rectangle bBox = getOccupiedAreaBBox();
/*  515 */       boolean isTagged = drawContext.isTaggingEnabled();
/*  516 */       if (isTagged) {
/*  517 */         drawContext.getCanvas().openTag((CanvasTag)new CanvasArtifact());
/*      */       }
/*  519 */       Rectangle backgroundArea = getBackgroundArea(applyMargins(bBox, false));
/*  520 */       if (backgroundArea.getWidth() <= 0.0F || backgroundArea.getHeight() <= 0.0F) {
/*  521 */         Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/*  522 */         logger.info(MessageFormatUtil.format("The {0} rectangle has negative or zero sizes. It will not be displayed.", new Object[] { "background" }));
/*      */       } else {
/*      */         
/*  525 */         boolean backgroundAreaIsClipped = false;
/*  526 */         if (background != null) {
/*      */           
/*  528 */           Rectangle clippedBackgroundArea = applyBackgroundBoxProperty(backgroundArea.clone(), background
/*  529 */               .getBackgroundClip());
/*  530 */           backgroundAreaIsClipped = clipBackgroundArea(drawContext, clippedBackgroundArea);
/*  531 */           drawColorBackground(background, drawContext, clippedBackgroundArea);
/*      */         } 
/*  533 */         if (backgroundImagesList != null) {
/*  534 */           backgroundAreaIsClipped = drawBackgroundImagesList(backgroundImagesList, backgroundAreaIsClipped, drawContext, backgroundArea);
/*      */         }
/*      */         
/*  537 */         if (backgroundAreaIsClipped) {
/*  538 */           drawContext.getCanvas().restoreState();
/*      */         }
/*      */       } 
/*  541 */       if (isTagged) {
/*  542 */         drawContext.getCanvas().closeTag();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawColorBackground(Background background, DrawContext drawContext, Rectangle colorBackgroundArea) {
/*  549 */     TransparentColor backgroundColor = new TransparentColor(background.getColor(), background.getOpacity());
/*  550 */     drawContext.getCanvas().saveState().setFillColor(backgroundColor.getColor());
/*  551 */     backgroundColor.applyFillTransparency(drawContext.getCanvas());
/*  552 */     drawContext.getCanvas().rectangle(colorBackgroundArea.getX() - background.getExtraLeft(), colorBackgroundArea
/*  553 */         .getY() - background.getExtraBottom(), colorBackgroundArea
/*  554 */         .getWidth() + background
/*  555 */         .getExtraLeft() + background.getExtraRight(), colorBackgroundArea
/*  556 */         .getHeight() + background
/*  557 */         .getExtraTop() + background.getExtraBottom()).fill().restoreState();
/*      */   }
/*      */   
/*      */   private Rectangle applyBackgroundBoxProperty(Rectangle rectangle, BackgroundBox clip) {
/*  561 */     if (BackgroundBox.PADDING_BOX == clip) {
/*  562 */       applyBorderBox(rectangle, false);
/*  563 */     } else if (BackgroundBox.CONTENT_BOX == clip) {
/*  564 */       applyBorderBox(rectangle, false);
/*  565 */       applyPaddings(rectangle, false);
/*      */     } 
/*  567 */     return rectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean drawBackgroundImagesList(List<BackgroundImage> backgroundImagesList, boolean backgroundAreaIsClipped, DrawContext drawContext, Rectangle backgroundArea) {
/*  573 */     for (int i = backgroundImagesList.size() - 1; i >= 0; i--) {
/*  574 */       BackgroundImage backgroundImage = backgroundImagesList.get(i);
/*  575 */       if (backgroundImage != null && backgroundImage.isBackgroundSpecified()) {
/*      */         
/*  577 */         if (!backgroundAreaIsClipped) {
/*  578 */           backgroundAreaIsClipped = clipBackgroundArea(drawContext, backgroundArea);
/*      */         }
/*  580 */         drawBackgroundImage(backgroundImage, drawContext, backgroundArea);
/*      */       } 
/*      */     } 
/*  583 */     return backgroundAreaIsClipped;
/*      */   }
/*      */   
/*      */   private void drawBackgroundImage(BackgroundImage backgroundImage, DrawContext drawContext, Rectangle backgroundArea) {
/*      */     PdfFormXObject pdfFormXObject;
/*  588 */     Rectangle imageRectangle, originBackgroundArea = applyBackgroundBoxProperty(backgroundArea.clone(), backgroundImage
/*  589 */         .getBackgroundOrigin());
/*  590 */     float[] imageWidthAndHeight = BackgroundSizeCalculationUtil.calculateBackgroundImageSize(backgroundImage, originBackgroundArea
/*  591 */         .getWidth(), originBackgroundArea.getHeight());
/*  592 */     PdfImageXObject pdfImageXObject = backgroundImage.getImage();
/*  593 */     if (pdfImageXObject == null) {
/*  594 */       pdfFormXObject = backgroundImage.getForm();
/*      */     }
/*      */     
/*  597 */     UnitValue xPosition = UnitValue.createPointValue(0.0F);
/*  598 */     UnitValue yPosition = UnitValue.createPointValue(0.0F);
/*  599 */     if (pdfFormXObject == null) {
/*  600 */       AbstractLinearGradientBuilder gradientBuilder = backgroundImage.getLinearGradientBuilder();
/*  601 */       if (gradientBuilder == null) {
/*      */         return;
/*      */       }
/*      */       
/*  605 */       backgroundImage.getBackgroundPosition().calculatePositionValues(0.0F, 0.0F, xPosition, yPosition);
/*  606 */       pdfFormXObject = createXObject(gradientBuilder, originBackgroundArea, drawContext.getDocument());
/*      */       
/*  608 */       imageRectangle = new Rectangle(originBackgroundArea.getLeft() + xPosition.getValue(), originBackgroundArea.getTop() - imageWidthAndHeight[1] - yPosition.getValue(), imageWidthAndHeight[0], imageWidthAndHeight[1]);
/*      */     } else {
/*      */       
/*  611 */       backgroundImage.getBackgroundPosition().calculatePositionValues(originBackgroundArea
/*  612 */           .getWidth() - imageWidthAndHeight[0], originBackgroundArea
/*  613 */           .getHeight() - imageWidthAndHeight[1], xPosition, yPosition);
/*      */       
/*  615 */       imageRectangle = new Rectangle(originBackgroundArea.getLeft() + xPosition.getValue(), originBackgroundArea.getTop() - imageWidthAndHeight[1] - yPosition.getValue(), imageWidthAndHeight[0], imageWidthAndHeight[1]);
/*      */     } 
/*      */     
/*  618 */     if (imageRectangle.getWidth() <= 0.0F || imageRectangle.getHeight() <= 0.0F) {
/*  619 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/*  620 */       logger.info(MessageFormatUtil.format("The {0} rectangle has negative or zero sizes. It will not be displayed.", new Object[] { "background-image" }));
/*      */     }
/*      */     else {
/*      */       
/*  624 */       Rectangle clippedBackgroundArea = applyBackgroundBoxProperty(backgroundArea.clone(), backgroundImage
/*  625 */           .getBackgroundClip());
/*  626 */       drawContext.getCanvas()
/*  627 */         .saveState()
/*  628 */         .rectangle(clippedBackgroundArea)
/*  629 */         .clip()
/*  630 */         .endPath();
/*  631 */       drawPdfXObject(imageRectangle, backgroundImage, drawContext, (PdfXObject)pdfFormXObject, backgroundArea, originBackgroundArea);
/*      */       
/*  633 */       drawContext.getCanvas().restoreState();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void drawPdfXObject(Rectangle imageRectangle, BackgroundImage backgroundImage, DrawContext drawContext, PdfXObject backgroundXObject, Rectangle backgroundArea, Rectangle originBackgroundArea) {
/*      */     boolean isCurrentOverlaps, isNextOverlaps;
/*  640 */     BlendMode blendMode = backgroundImage.getBlendMode();
/*  641 */     if (blendMode != BlendMode.NORMAL) {
/*  642 */       drawContext.getCanvas().setExtGState((new PdfExtGState()).setBlendMode((PdfObject)blendMode.getPdfRepresentation()));
/*      */     }
/*      */     
/*  645 */     Point whitespace = backgroundImage.getRepeat().prepareRectangleToDrawingAndGetWhitespace(imageRectangle, originBackgroundArea, backgroundImage
/*  646 */         .getBackgroundSize());
/*  647 */     float initialX = imageRectangle.getX();
/*  648 */     int counterY = 1;
/*  649 */     boolean firstDraw = true;
/*      */ 
/*      */     
/*      */     do {
/*  653 */       drawPdfXObjectHorizontally(imageRectangle, backgroundImage, drawContext, backgroundXObject, backgroundArea, firstDraw, 
/*  654 */           (float)whitespace.getX());
/*  655 */       firstDraw = false;
/*  656 */       imageRectangle.setX(initialX);
/*  657 */       isCurrentOverlaps = imageRectangle.overlaps(backgroundArea, 1.0E-4F);
/*  658 */       if (counterY % 2 == 1) {
/*      */ 
/*      */         
/*  661 */         isNextOverlaps = imageRectangle.moveDown((imageRectangle.getHeight() + (float)whitespace.getY()) * counterY).overlaps(backgroundArea, 1.0E-4F);
/*      */       } else {
/*      */         
/*  664 */         isNextOverlaps = imageRectangle.moveUp((imageRectangle.getHeight() + (float)whitespace.getY()) * counterY).overlaps(backgroundArea, 1.0E-4F);
/*      */       } 
/*  666 */       counterY++;
/*  667 */     } while (!backgroundImage.getRepeat().isNoRepeatOnYAxis() && (isCurrentOverlaps || isNextOverlaps));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void drawPdfXObjectHorizontally(Rectangle imageRectangle, BackgroundImage backgroundImage, DrawContext drawContext, PdfXObject backgroundXObject, Rectangle backgroundArea, boolean firstDraw, float xWhitespace) {
/*  673 */     boolean isCurrentOverlaps, isNextOverlaps, isItFirstDraw = firstDraw;
/*  674 */     int counterX = 1;
/*      */ 
/*      */     
/*      */     do {
/*  678 */       if (imageRectangle.overlaps(backgroundArea, 1.0E-4F) || isItFirstDraw) {
/*  679 */         drawContext.getCanvas().addXObjectFittedIntoRectangle(backgroundXObject, imageRectangle);
/*  680 */         isItFirstDraw = false;
/*      */       } 
/*  682 */       isCurrentOverlaps = imageRectangle.overlaps(backgroundArea, 1.0E-4F);
/*  683 */       if (counterX % 2 == 1) {
/*      */ 
/*      */         
/*  686 */         isNextOverlaps = imageRectangle.moveRight((imageRectangle.getWidth() + xWhitespace) * counterX).overlaps(backgroundArea, 1.0E-4F);
/*      */       } else {
/*      */         
/*  689 */         isNextOverlaps = imageRectangle.moveLeft((imageRectangle.getWidth() + xWhitespace) * counterX).overlaps(backgroundArea, 1.0E-4F);
/*      */       } 
/*  691 */       counterX++;
/*      */     }
/*  693 */     while (!backgroundImage.getRepeat().isNoRepeatOnXAxis() && (isCurrentOverlaps || isNextOverlaps));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfFormXObject createXObject(AbstractLinearGradientBuilder linearGradientBuilder, Rectangle xObjectArea, PdfDocument document) {
/*  706 */     Rectangle formBBox = new Rectangle(0.0F, 0.0F, xObjectArea.getWidth(), xObjectArea.getHeight());
/*  707 */     PdfFormXObject xObject = new PdfFormXObject(formBBox);
/*  708 */     if (linearGradientBuilder != null) {
/*  709 */       Color gradientColor = linearGradientBuilder.buildColor(formBBox, null, document);
/*  710 */       if (gradientColor != null) {
/*  711 */         (new PdfCanvas(xObject, document))
/*  712 */           .setColor(gradientColor, true)
/*  713 */           .rectangle(formBBox)
/*  714 */           .fill();
/*      */       }
/*      */     } 
/*  717 */     return xObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle getBackgroundArea(Rectangle occupiedAreaWithMargins) {
/*  727 */     return occupiedAreaWithMargins;
/*      */   }
/*      */   
/*      */   protected boolean clipBorderArea(DrawContext drawContext, Rectangle outerBorderBox) {
/*  731 */     return clipArea(drawContext, outerBorderBox, true, true, false, true);
/*      */   }
/*      */   
/*      */   protected boolean clipBackgroundArea(DrawContext drawContext, Rectangle outerBorderBox) {
/*  735 */     return clipArea(drawContext, outerBorderBox, true, false, false, false);
/*      */   }
/*      */   
/*      */   protected boolean clipBackgroundArea(DrawContext drawContext, Rectangle outerBorderBox, boolean considerBordersBeforeClipping) {
/*  739 */     return clipArea(drawContext, outerBorderBox, true, false, considerBordersBeforeClipping, false);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean clipArea(DrawContext drawContext, Rectangle outerBorderBox, boolean clipOuter, boolean clipInner, boolean considerBordersBeforeOuterClipping, boolean considerBordersBeforeInnerClipping) {
/*  744 */     assert false == considerBordersBeforeOuterClipping || false == considerBordersBeforeInnerClipping;
/*      */     
/*  746 */     double curv = 0.44769999384880066D;
/*      */ 
/*      */     
/*  749 */     float[] borderWidths = { 0.0F, 0.0F, 0.0F, 0.0F };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  755 */     float[] outerBox = { outerBorderBox.getTop(), outerBorderBox.getRight(), outerBorderBox.getBottom(), outerBorderBox.getLeft() };
/*      */ 
/*      */ 
/*      */     
/*  759 */     boolean hasNotNullRadius = false;
/*  760 */     BorderRadius[] borderRadii = getBorderRadii();
/*  761 */     float[] verticalRadii = calculateRadii(borderRadii, outerBorderBox, false);
/*  762 */     float[] horizontalRadii = calculateRadii(borderRadii, outerBorderBox, true);
/*  763 */     for (int i = 0; i < 4; i++) {
/*  764 */       verticalRadii[i] = Math.min(verticalRadii[i], outerBorderBox.getHeight() / 2.0F);
/*  765 */       horizontalRadii[i] = Math.min(horizontalRadii[i], outerBorderBox.getWidth() / 2.0F);
/*  766 */       if (!hasNotNullRadius && (0.0F != verticalRadii[i] || 0.0F != horizontalRadii[i])) {
/*  767 */         hasNotNullRadius = true;
/*      */       }
/*      */     } 
/*  770 */     if (hasNotNullRadius) {
/*      */       
/*  772 */       float[] cornersX = { outerBox[3] + horizontalRadii[0], outerBox[1] - horizontalRadii[1], outerBox[1] - horizontalRadii[2], outerBox[3] + horizontalRadii[3] };
/*  773 */       float[] cornersY = { outerBox[0] - verticalRadii[0], outerBox[0] - verticalRadii[1], outerBox[2] + verticalRadii[2], outerBox[2] + verticalRadii[3] };
/*      */       
/*  775 */       PdfCanvas canvas = drawContext.getCanvas();
/*  776 */       canvas.saveState();
/*      */       
/*  778 */       if (considerBordersBeforeOuterClipping) {
/*  779 */         borderWidths = decreaseBorderRadiiWithBorders(horizontalRadii, verticalRadii, outerBox, cornersX, cornersY);
/*      */       }
/*      */ 
/*      */       
/*  783 */       if (clipOuter) {
/*  784 */         clipOuterArea(canvas, 0.44769999384880066D, horizontalRadii, verticalRadii, outerBox, cornersX, cornersY);
/*      */       }
/*      */       
/*  787 */       if (considerBordersBeforeInnerClipping) {
/*  788 */         borderWidths = decreaseBorderRadiiWithBorders(horizontalRadii, verticalRadii, outerBox, cornersX, cornersY);
/*      */       }
/*      */ 
/*      */       
/*  792 */       if (clipInner) {
/*  793 */         clipInnerArea(canvas, 0.44769999384880066D, horizontalRadii, verticalRadii, outerBox, cornersX, cornersY, borderWidths);
/*      */       }
/*      */     } 
/*  796 */     return hasNotNullRadius;
/*      */   }
/*      */   
/*      */   private void clipOuterArea(PdfCanvas canvas, double curv, float[] horizontalRadii, float[] verticalRadii, float[] outerBox, float[] cornersX, float[] cornersY) {
/*  800 */     float top = outerBox[0], right = outerBox[1];
/*  801 */     float bottom = outerBox[2];
/*  802 */     float left = outerBox[3];
/*      */     
/*  804 */     float x1 = cornersX[0], y1 = cornersY[0];
/*  805 */     float x2 = cornersX[1], y2 = cornersY[1];
/*  806 */     float x3 = cornersX[2], y3 = cornersY[2];
/*  807 */     float x4 = cornersX[3], y4 = cornersY[3];
/*      */ 
/*      */     
/*  810 */     if (0.0F != horizontalRadii[0] || 0.0F != verticalRadii[0]) {
/*  811 */       canvas
/*  812 */         .moveTo(left, bottom)
/*  813 */         .lineTo(left, y1)
/*  814 */         .curveTo(left, y1 + verticalRadii[0] * curv, x1 - horizontalRadii[0] * curv, top, x1, top)
/*  815 */         .lineTo(right, top)
/*  816 */         .lineTo(right, bottom)
/*  817 */         .lineTo(left, bottom);
/*  818 */       canvas.clip().endPath();
/*      */     } 
/*      */     
/*  821 */     if (0.0F != horizontalRadii[1] || 0.0F != verticalRadii[1]) {
/*  822 */       canvas
/*  823 */         .moveTo(left, top)
/*  824 */         .lineTo(x2, top)
/*  825 */         .curveTo(x2 + horizontalRadii[1] * curv, top, right, y2 + verticalRadii[1] * curv, right, y2)
/*  826 */         .lineTo(right, bottom)
/*  827 */         .lineTo(left, bottom)
/*  828 */         .lineTo(left, top);
/*  829 */       canvas.clip().endPath();
/*      */     } 
/*      */     
/*  832 */     if (0.0F != horizontalRadii[2] || 0.0F != verticalRadii[2]) {
/*  833 */       canvas
/*  834 */         .moveTo(right, top)
/*  835 */         .lineTo(right, y3)
/*  836 */         .curveTo(right, y3 - verticalRadii[2] * curv, x3 + horizontalRadii[2] * curv, bottom, x3, bottom)
/*  837 */         .lineTo(left, bottom)
/*  838 */         .lineTo(left, top)
/*  839 */         .lineTo(right, top);
/*  840 */       canvas.clip().endPath();
/*      */     } 
/*      */     
/*  843 */     if (0.0F != horizontalRadii[3] || 0.0F != verticalRadii[3]) {
/*  844 */       canvas
/*  845 */         .moveTo(right, bottom)
/*  846 */         .lineTo(x4, bottom)
/*  847 */         .curveTo(x4 - horizontalRadii[3] * curv, bottom, left, y4 - verticalRadii[3] * curv, left, y4)
/*  848 */         .lineTo(left, top)
/*  849 */         .lineTo(right, top)
/*  850 */         .lineTo(right, bottom);
/*  851 */       canvas.clip().endPath();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clipInnerArea(PdfCanvas canvas, double curv, float[] horizontalRadii, float[] verticalRadii, float[] outerBox, float[] cornersX, float[] cornersY, float[] borderWidths) {
/*  856 */     float top = outerBox[0];
/*  857 */     float right = outerBox[1];
/*  858 */     float bottom = outerBox[2];
/*  859 */     float left = outerBox[3];
/*      */     
/*  861 */     float x1 = cornersX[0], y1 = cornersY[0];
/*  862 */     float x2 = cornersX[1], y2 = cornersY[1];
/*  863 */     float x3 = cornersX[2], y3 = cornersY[2];
/*  864 */     float x4 = cornersX[3], y4 = cornersY[3];
/*  865 */     float topBorderWidth = borderWidths[0];
/*  866 */     float rightBorderWidth = borderWidths[1];
/*  867 */     float bottomBorderWidth = borderWidths[2];
/*  868 */     float leftBorderWidth = borderWidths[3];
/*      */ 
/*      */     
/*  871 */     if (0.0F != horizontalRadii[0] || 0.0F != verticalRadii[0]) {
/*  872 */       canvas
/*  873 */         .moveTo(left, y1)
/*  874 */         .curveTo(left, y1 + verticalRadii[0] * curv, x1 - horizontalRadii[0] * curv, top, x1, top)
/*  875 */         .lineTo(x2, top)
/*  876 */         .lineTo(right, y2)
/*  877 */         .lineTo(right, y3)
/*  878 */         .lineTo(x3, bottom)
/*  879 */         .lineTo(x4, bottom)
/*  880 */         .lineTo(left, y4)
/*  881 */         .lineTo(left, y1)
/*  882 */         .lineTo((left - leftBorderWidth), y1)
/*  883 */         .lineTo((left - leftBorderWidth), (bottom - bottomBorderWidth))
/*  884 */         .lineTo((right + rightBorderWidth), (bottom - bottomBorderWidth))
/*  885 */         .lineTo((right + rightBorderWidth), (top + topBorderWidth))
/*  886 */         .lineTo((left - leftBorderWidth), (top + topBorderWidth))
/*  887 */         .lineTo((left - leftBorderWidth), y1);
/*  888 */       canvas.clip().endPath();
/*      */     } 
/*      */     
/*  891 */     if (0.0F != horizontalRadii[1] || 0.0F != verticalRadii[1]) {
/*  892 */       canvas
/*  893 */         .moveTo(x2, top)
/*  894 */         .curveTo(x2 + horizontalRadii[1] * curv, top, right, y2 + verticalRadii[1] * curv, right, y2)
/*  895 */         .lineTo(right, y3)
/*  896 */         .lineTo(x3, bottom)
/*  897 */         .lineTo(x4, bottom)
/*  898 */         .lineTo(left, y4)
/*  899 */         .lineTo(left, y1)
/*  900 */         .lineTo(x1, top)
/*  901 */         .lineTo(x2, top)
/*  902 */         .lineTo(x2, (top + topBorderWidth))
/*  903 */         .lineTo((left - leftBorderWidth), (top + topBorderWidth))
/*  904 */         .lineTo((left - leftBorderWidth), (bottom - bottomBorderWidth))
/*  905 */         .lineTo((right + rightBorderWidth), (bottom - bottomBorderWidth))
/*  906 */         .lineTo((right + rightBorderWidth), (top + topBorderWidth))
/*  907 */         .lineTo(x2, (top + topBorderWidth));
/*  908 */       canvas.clip().endPath();
/*      */     } 
/*      */     
/*  911 */     if (0.0F != horizontalRadii[2] || 0.0F != verticalRadii[2]) {
/*  912 */       canvas
/*  913 */         .moveTo(right, y3)
/*  914 */         .curveTo(right, y3 - verticalRadii[2] * curv, x3 + horizontalRadii[2] * curv, bottom, x3, bottom)
/*  915 */         .lineTo(x4, bottom)
/*  916 */         .lineTo(left, y4)
/*  917 */         .lineTo(left, y1)
/*  918 */         .lineTo(x1, top)
/*  919 */         .lineTo(x2, top)
/*  920 */         .lineTo(right, y2)
/*  921 */         .lineTo(right, y3)
/*  922 */         .lineTo((right + rightBorderWidth), y3)
/*  923 */         .lineTo((right + rightBorderWidth), (top + topBorderWidth))
/*  924 */         .lineTo((left - leftBorderWidth), (top + topBorderWidth))
/*  925 */         .lineTo((left - leftBorderWidth), (bottom - bottomBorderWidth))
/*  926 */         .lineTo((right + rightBorderWidth), (bottom - bottomBorderWidth))
/*  927 */         .lineTo((right + rightBorderWidth), y3);
/*  928 */       canvas.clip().endPath();
/*      */     } 
/*      */     
/*  931 */     if (0.0F != horizontalRadii[3] || 0.0F != verticalRadii[3]) {
/*  932 */       canvas
/*  933 */         .moveTo(x4, bottom)
/*  934 */         .curveTo(x4 - horizontalRadii[3] * curv, bottom, left, y4 - verticalRadii[3] * curv, left, y4)
/*  935 */         .lineTo(left, y1)
/*  936 */         .lineTo(x1, top)
/*  937 */         .lineTo(x2, top)
/*  938 */         .lineTo(right, y2)
/*  939 */         .lineTo(right, y3)
/*  940 */         .lineTo(x3, bottom)
/*  941 */         .lineTo(x4, bottom)
/*  942 */         .lineTo(x4, (bottom - bottomBorderWidth))
/*  943 */         .lineTo((right + rightBorderWidth), (bottom - bottomBorderWidth))
/*  944 */         .lineTo((right + rightBorderWidth), (top + topBorderWidth))
/*  945 */         .lineTo((left - leftBorderWidth), (top + topBorderWidth))
/*  946 */         .lineTo((left - leftBorderWidth), (bottom - bottomBorderWidth))
/*  947 */         .lineTo(x4, (bottom - bottomBorderWidth));
/*  948 */       canvas.clip().endPath();
/*      */     } 
/*      */   }
/*      */   
/*      */   private float[] decreaseBorderRadiiWithBorders(float[] horizontalRadii, float[] verticalRadii, float[] outerBox, float[] cornersX, float[] cornersY) {
/*  953 */     Border[] borders = getBorders();
/*  954 */     float[] borderWidths = { 0.0F, 0.0F, 0.0F, 0.0F };
/*      */     
/*  956 */     if (borders[0] != null) {
/*  957 */       borderWidths[0] = borders[0].getWidth();
/*  958 */       outerBox[0] = outerBox[0] - borders[0].getWidth();
/*  959 */       if (cornersY[1] > outerBox[0]) {
/*  960 */         cornersY[1] = outerBox[0];
/*      */       }
/*  962 */       if (cornersY[0] > outerBox[0]) {
/*  963 */         cornersY[0] = outerBox[0];
/*      */       }
/*  965 */       verticalRadii[0] = Math.max(0.0F, verticalRadii[0] - borders[0].getWidth());
/*  966 */       verticalRadii[1] = Math.max(0.0F, verticalRadii[1] - borders[0].getWidth());
/*      */     } 
/*  968 */     if (borders[1] != null) {
/*  969 */       borderWidths[1] = borders[1].getWidth();
/*  970 */       outerBox[1] = outerBox[1] - borders[1].getWidth();
/*  971 */       if (cornersX[1] > outerBox[1]) {
/*  972 */         cornersX[1] = outerBox[1];
/*      */       }
/*  974 */       if (cornersX[2] > outerBox[1]) {
/*  975 */         cornersX[2] = outerBox[1];
/*      */       }
/*  977 */       horizontalRadii[1] = Math.max(0.0F, horizontalRadii[1] - borders[1].getWidth());
/*  978 */       horizontalRadii[2] = Math.max(0.0F, horizontalRadii[2] - borders[1].getWidth());
/*      */     } 
/*  980 */     if (borders[2] != null) {
/*  981 */       borderWidths[2] = borders[2].getWidth();
/*  982 */       outerBox[2] = outerBox[2] + borders[2].getWidth();
/*  983 */       if (cornersY[2] < outerBox[2]) {
/*  984 */         cornersY[2] = outerBox[2];
/*      */       }
/*  986 */       if (cornersY[3] < outerBox[2]) {
/*  987 */         cornersY[3] = outerBox[2];
/*      */       }
/*  989 */       verticalRadii[2] = Math.max(0.0F, verticalRadii[2] - borders[2].getWidth());
/*  990 */       verticalRadii[3] = Math.max(0.0F, verticalRadii[3] - borders[2].getWidth());
/*      */     } 
/*  992 */     if (borders[3] != null) {
/*  993 */       borderWidths[3] = borders[3].getWidth();
/*  994 */       outerBox[3] = outerBox[3] + borders[3].getWidth();
/*  995 */       if (cornersX[3] < outerBox[3]) {
/*  996 */         cornersX[3] = outerBox[3];
/*      */       }
/*  998 */       if (cornersX[0] < outerBox[3]) {
/*  999 */         cornersX[0] = outerBox[3];
/*      */       }
/* 1001 */       horizontalRadii[3] = Math.max(0.0F, horizontalRadii[3] - borders[3].getWidth());
/* 1002 */       horizontalRadii[0] = Math.max(0.0F, horizontalRadii[0] - borders[3].getWidth());
/*      */     } 
/* 1004 */     return borderWidths;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawChildren(DrawContext drawContext) {
/* 1014 */     List<IRenderer> waitingRenderers = new ArrayList<>();
/* 1015 */     for (IRenderer child : this.childRenderers) {
/* 1016 */       Transform transformProp = (Transform)child.getProperty(53);
/* 1017 */       RootRenderer rootRenderer = getRootRenderer();
/* 1018 */       List<IRenderer> waiting = (rootRenderer != null && !rootRenderer.waitingDrawingElements.contains(child)) ? rootRenderer.waitingDrawingElements : waitingRenderers;
/* 1019 */       processWaitingDrawing(child, transformProp, waiting);
/* 1020 */       if (!FloatingHelper.isRendererFloating(child) && transformProp == null) {
/* 1021 */         child.draw(drawContext);
/*      */       }
/*      */     } 
/* 1024 */     for (IRenderer waitingRenderer : waitingRenderers) {
/* 1025 */       waitingRenderer.draw(drawContext);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawBorder(DrawContext drawContext) {
/* 1037 */     Border[] borders = getBorders();
/* 1038 */     boolean gotBorders = false;
/*      */     
/* 1040 */     for (Border border : borders) {
/* 1041 */       gotBorders = (gotBorders || border != null);
/*      */     }
/* 1043 */     if (gotBorders) {
/* 1044 */       float topWidth = (borders[0] != null) ? borders[0].getWidth() : 0.0F;
/* 1045 */       float rightWidth = (borders[1] != null) ? borders[1].getWidth() : 0.0F;
/* 1046 */       float bottomWidth = (borders[2] != null) ? borders[2].getWidth() : 0.0F;
/* 1047 */       float leftWidth = (borders[3] != null) ? borders[3].getWidth() : 0.0F;
/*      */       
/* 1049 */       Rectangle bBox = getBorderAreaBBox();
/* 1050 */       if (bBox.getWidth() < 0.0F || bBox.getHeight() < 0.0F) {
/* 1051 */         Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1052 */         logger.error(MessageFormatUtil.format("The {0} rectangle has negative size. It will not be displayed.", new Object[] { "border" }));
/*      */         return;
/*      */       } 
/* 1055 */       float x1 = bBox.getX();
/* 1056 */       float y1 = bBox.getY();
/* 1057 */       float x2 = bBox.getX() + bBox.getWidth();
/* 1058 */       float y2 = bBox.getY() + bBox.getHeight();
/*      */       
/* 1060 */       boolean isTagged = drawContext.isTaggingEnabled();
/* 1061 */       PdfCanvas canvas = drawContext.getCanvas();
/* 1062 */       if (isTagged) {
/* 1063 */         canvas.openTag((CanvasTag)new CanvasArtifact());
/*      */       }
/*      */       
/* 1066 */       Rectangle borderRect = applyMargins(this.occupiedArea.getBBox().clone(), getMargins(), false);
/* 1067 */       boolean isAreaClipped = clipBorderArea(drawContext, borderRect);
/* 1068 */       BorderRadius[] borderRadii = getBorderRadii();
/* 1069 */       float[] verticalRadii = calculateRadii(borderRadii, borderRect, false);
/* 1070 */       float[] horizontalRadii = calculateRadii(borderRadii, borderRect, true);
/* 1071 */       for (int i = 0; i < 4; i++) {
/* 1072 */         verticalRadii[i] = Math.min(verticalRadii[i], borderRect.getHeight() / 2.0F);
/* 1073 */         horizontalRadii[i] = Math.min(horizontalRadii[i], borderRect.getWidth() / 2.0F);
/*      */       } 
/* 1075 */       if (borders[0] != null) {
/* 1076 */         if (0.0F != horizontalRadii[0] || 0.0F != verticalRadii[0] || 0.0F != horizontalRadii[1] || 0.0F != verticalRadii[1]) {
/* 1077 */           borders[0].draw(canvas, x1, y2, x2, y2, horizontalRadii[0], verticalRadii[0], horizontalRadii[1], verticalRadii[1], Border.Side.TOP, leftWidth, rightWidth);
/*      */         } else {
/* 1079 */           borders[0].draw(canvas, x1, y2, x2, y2, Border.Side.TOP, leftWidth, rightWidth);
/*      */         } 
/*      */       }
/* 1082 */       if (borders[1] != null) {
/* 1083 */         if (0.0F != horizontalRadii[1] || 0.0F != verticalRadii[1] || 0.0F != horizontalRadii[2] || 0.0F != verticalRadii[2]) {
/* 1084 */           borders[1].draw(canvas, x2, y2, x2, y1, horizontalRadii[1], verticalRadii[1], horizontalRadii[2], verticalRadii[2], Border.Side.RIGHT, topWidth, bottomWidth);
/*      */         } else {
/* 1086 */           borders[1].draw(canvas, x2, y2, x2, y1, Border.Side.RIGHT, topWidth, bottomWidth);
/*      */         } 
/*      */       }
/* 1089 */       if (borders[2] != null) {
/* 1090 */         if (0.0F != horizontalRadii[2] || 0.0F != verticalRadii[2] || 0.0F != horizontalRadii[3] || 0.0F != verticalRadii[3]) {
/* 1091 */           borders[2].draw(canvas, x2, y1, x1, y1, horizontalRadii[2], verticalRadii[2], horizontalRadii[3], verticalRadii[3], Border.Side.BOTTOM, rightWidth, leftWidth);
/*      */         } else {
/* 1093 */           borders[2].draw(canvas, x2, y1, x1, y1, Border.Side.BOTTOM, rightWidth, leftWidth);
/*      */         } 
/*      */       }
/* 1096 */       if (borders[3] != null) {
/* 1097 */         if (0.0F != horizontalRadii[3] || 0.0F != verticalRadii[3] || 0.0F != horizontalRadii[0] || 0.0F != verticalRadii[0]) {
/* 1098 */           borders[3].draw(canvas, x1, y1, x1, y2, horizontalRadii[3], verticalRadii[3], horizontalRadii[0], verticalRadii[0], Border.Side.LEFT, bottomWidth, topWidth);
/*      */         } else {
/* 1100 */           borders[3].draw(canvas, x1, y1, x1, y2, Border.Side.LEFT, bottomWidth, topWidth);
/*      */         } 
/*      */       }
/*      */       
/* 1104 */       if (isAreaClipped) {
/* 1105 */         drawContext.getCanvas().restoreState();
/*      */       }
/*      */       
/* 1108 */       if (isTagged) {
/* 1109 */         canvas.closeTag();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlushed() {
/* 1124 */     return this.flushed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IRenderer setParent(IRenderer parent) {
/* 1132 */     this.parent = parent;
/* 1133 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IRenderer getParent() {
/* 1141 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void move(float dxRight, float dyUp) {
/* 1149 */     this.occupiedArea.getBBox().moveRight(dxRight);
/* 1150 */     this.occupiedArea.getBBox().moveUp(dyUp);
/* 1151 */     for (IRenderer childRenderer : this.childRenderers) {
/* 1152 */       childRenderer.move(dxRight, dyUp);
/*      */     }
/* 1154 */     for (IRenderer childRenderer : this.positionedRenderers) {
/* 1155 */       childRenderer.move(dxRight, dyUp);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Rectangle> initElementAreas(LayoutArea area) {
/* 1166 */     return Collections.singletonList(area.getBBox());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getOccupiedAreaBBox() {
/* 1176 */     return this.occupiedArea.getBBox().clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getBorderAreaBBox() {
/* 1186 */     Rectangle rect = getOccupiedAreaBBox();
/* 1187 */     applyMargins(rect, false);
/* 1188 */     applyBorderBox(rect, false);
/* 1189 */     return rect;
/*      */   }
/*      */   
/*      */   public Rectangle getInnerAreaBBox() {
/* 1193 */     Rectangle rect = getOccupiedAreaBBox();
/* 1194 */     applyMargins(rect, false);
/* 1195 */     applyBorderBox(rect, false);
/* 1196 */     applyPaddings(rect, false);
/* 1197 */     return rect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle applyMargins(Rectangle rect, boolean reverse) {
/* 1210 */     return applyMargins(rect, getMargins(), reverse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle applyBorderBox(Rectangle rect, boolean reverse) {
/* 1224 */     Border[] borders = getBorders();
/* 1225 */     return applyBorderBox(rect, borders, reverse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle applyPaddings(Rectangle rect, boolean reverse) {
/* 1238 */     return applyPaddings(rect, getPaddings(), reverse);
/*      */   }
/*      */   
/*      */   public boolean isFirstOnRootArea() {
/* 1242 */     return isFirstOnRootArea(false);
/*      */   }
/*      */   
/*      */   protected void applyDestinationsAndAnnotation(DrawContext drawContext) {
/* 1246 */     applyDestination(drawContext.getDocument());
/* 1247 */     applyAction(drawContext.getDocument());
/* 1248 */     applyLinkAnnotation(drawContext.getDocument());
/*      */   }
/*      */   
/*      */   protected static boolean isBorderBoxSizing(IRenderer renderer) {
/* 1252 */     BoxSizingPropertyValue boxSizing = (BoxSizingPropertyValue)renderer.getProperty(105);
/* 1253 */     return (boxSizing != null && boxSizing.equals(BoxSizingPropertyValue.BORDER_BOX));
/*      */   }
/*      */   
/*      */   protected boolean isOverflowProperty(OverflowPropertyValue equalsTo, int overflowProperty) {
/* 1257 */     return isOverflowProperty(equalsTo, getProperty(overflowProperty));
/*      */   }
/*      */   
/*      */   protected static boolean isOverflowProperty(OverflowPropertyValue equalsTo, IRenderer renderer, int overflowProperty) {
/* 1261 */     return isOverflowProperty(equalsTo, (OverflowPropertyValue)renderer.getProperty(overflowProperty));
/*      */   }
/*      */   
/*      */   protected static boolean isOverflowProperty(OverflowPropertyValue equalsTo, OverflowPropertyValue rendererOverflowProperty) {
/* 1265 */     return (equalsTo.equals(rendererOverflowProperty) || (equalsTo.equals(OverflowPropertyValue.FIT) && rendererOverflowProperty == null));
/*      */   }
/*      */   
/*      */   protected static boolean isOverflowFit(OverflowPropertyValue rendererOverflowProperty) {
/* 1269 */     return (rendererOverflowProperty == null || OverflowPropertyValue.FIT.equals(rendererOverflowProperty));
/*      */   }
/*      */   
/*      */   static void processWaitingDrawing(IRenderer child, Transform transformProp, List<IRenderer> waitingDrawing) {
/* 1273 */     if (FloatingHelper.isRendererFloating(child) || transformProp != null) {
/* 1274 */       waitingDrawing.add(child);
/*      */     }
/* 1276 */     Border outlineProp = (Border)child.getProperty(106);
/* 1277 */     if (outlineProp != null && child instanceof AbstractRenderer) {
/* 1278 */       AbstractRenderer abstractChild = (AbstractRenderer)child;
/* 1279 */       if (abstractChild.isRelativePosition())
/* 1280 */         abstractChild.applyRelativePositioningTranslation(false); 
/* 1281 */       Div outlines = new Div();
/* 1282 */       outlines.getAccessibilityProperties().setRole(null);
/* 1283 */       if (transformProp != null)
/* 1284 */         outlines.setProperty(53, transformProp); 
/* 1285 */       outlines.setProperty(9, outlineProp);
/* 1286 */       float offset = ((Border)outlines.getProperty(9)).getWidth();
/* 1287 */       if (abstractChild.getPropertyAsFloat(107) != null)
/* 1288 */         offset += abstractChild.getPropertyAsFloat(107).floatValue(); 
/* 1289 */       DivRenderer div = new DivRenderer(outlines);
/* 1290 */       div.setParent(abstractChild.getParent());
/* 1291 */       Rectangle divOccupiedArea = abstractChild.applyMargins(abstractChild.occupiedArea.clone().getBBox(), false).moveLeft(offset).moveDown(offset);
/* 1292 */       divOccupiedArea.setWidth(divOccupiedArea.getWidth() + 2.0F * offset).setHeight(divOccupiedArea.getHeight() + 2.0F * offset);
/* 1293 */       div.occupiedArea = new LayoutArea(abstractChild.getOccupiedArea().getPageNumber(), divOccupiedArea);
/* 1294 */       float outlineWidth = ((Border)div.<Border>getProperty(9)).getWidth();
/* 1295 */       if (divOccupiedArea.getWidth() >= outlineWidth * 2.0F && divOccupiedArea.getHeight() >= outlineWidth * 2.0F) {
/* 1296 */         waitingDrawing.add(div);
/*      */       }
/* 1298 */       if (abstractChild.isRelativePosition()) {
/* 1299 */         abstractChild.applyRelativePositioningTranslation(true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Float retrieveWidth(float parentBoxWidth) {
/* 1315 */     Float minWidth = retrieveUnitValue(parentBoxWidth, 80);
/*      */     
/* 1317 */     Float maxWidth = retrieveUnitValue(parentBoxWidth, 79);
/* 1318 */     if (maxWidth != null && minWidth != null && minWidth.floatValue() > maxWidth.floatValue()) {
/* 1319 */       maxWidth = minWidth;
/*      */     }
/*      */     
/* 1322 */     Float width = retrieveUnitValue(parentBoxWidth, 77);
/* 1323 */     if (width != null) {
/* 1324 */       if (maxWidth != null) {
/* 1325 */         width = (width.floatValue() > maxWidth.floatValue()) ? maxWidth : width;
/*      */       }
/* 1327 */       if (minWidth != null) {
/* 1328 */         width = (width.floatValue() < minWidth.floatValue()) ? minWidth : width;
/*      */       }
/* 1330 */     } else if (maxWidth != null) {
/* 1331 */       width = (maxWidth.floatValue() < parentBoxWidth) ? maxWidth : null;
/*      */     } 
/*      */     
/* 1334 */     if (width != null && isBorderBoxSizing(this)) {
/* 1335 */       width = Float.valueOf(width.floatValue() - calculatePaddingBorderWidth(this));
/*      */     }
/*      */     
/* 1338 */     return (width != null) ? Float.valueOf(Math.max(0.0F, width.floatValue())) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Float retrieveMaxWidth(float parentBoxWidth) {
/* 1352 */     Float maxWidth = retrieveUnitValue(parentBoxWidth, 79);
/* 1353 */     if (maxWidth != null) {
/* 1354 */       Float minWidth = retrieveUnitValue(parentBoxWidth, 80);
/* 1355 */       if (minWidth != null && minWidth.floatValue() > maxWidth.floatValue()) {
/* 1356 */         maxWidth = minWidth;
/*      */       }
/*      */       
/* 1359 */       if (isBorderBoxSizing(this)) {
/* 1360 */         maxWidth = Float.valueOf(maxWidth.floatValue() - calculatePaddingBorderWidth(this));
/*      */       }
/* 1362 */       return Float.valueOf((maxWidth.floatValue() > 0.0F) ? maxWidth.floatValue() : 0.0F);
/*      */     } 
/* 1364 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Float retrieveMinWidth(float parentBoxWidth) {
/* 1379 */     Float minWidth = retrieveUnitValue(parentBoxWidth, 80);
/* 1380 */     if (minWidth != null) {
/* 1381 */       if (isBorderBoxSizing(this)) {
/* 1382 */         minWidth = Float.valueOf(minWidth.floatValue() - calculatePaddingBorderWidth(this));
/*      */       }
/* 1384 */       return Float.valueOf((minWidth.floatValue() > 0.0F) ? minWidth.floatValue() : 0.0F);
/*      */     } 
/* 1386 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateWidth(UnitValue updatedWidthValue) {
/* 1397 */     if (updatedWidthValue.isPointValue() && isBorderBoxSizing(this)) {
/* 1398 */       updatedWidthValue.setValue(updatedWidthValue.getValue() + calculatePaddingBorderWidth(this));
/*      */     }
/* 1400 */     setProperty(77, updatedWidthValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Float retrieveHeight() {
/* 1411 */     Float height = null;
/* 1412 */     UnitValue heightUV = getPropertyAsUnitValue(27);
/* 1413 */     Float parentResolvedHeight = retrieveResolvedParentDeclaredHeight();
/* 1414 */     Float minHeight = null;
/* 1415 */     Float maxHeight = null;
/* 1416 */     if (heightUV != null) {
/* 1417 */       if (parentResolvedHeight == null) {
/* 1418 */         if (heightUV.isPercentValue()) {
/*      */           
/* 1420 */           height = null;
/*      */         } else {
/*      */           
/* 1423 */           UnitValue minHeightUV = getPropertyAsUnitValue(85);
/* 1424 */           if (minHeightUV != null && minHeightUV.isPointValue()) {
/* 1425 */             minHeight = Float.valueOf(minHeightUV.getValue());
/*      */           }
/* 1427 */           UnitValue maxHeightUV = getPropertyAsUnitValue(84);
/* 1428 */           if (maxHeightUV != null && maxHeightUV.isPointValue()) {
/* 1429 */             maxHeight = Float.valueOf(maxHeightUV.getValue());
/*      */           }
/*      */           
/* 1432 */           height = Float.valueOf(heightUV.getValue());
/*      */         } 
/*      */       } else {
/* 1435 */         minHeight = retrieveUnitValue(parentResolvedHeight.floatValue(), 85);
/* 1436 */         maxHeight = retrieveUnitValue(parentResolvedHeight.floatValue(), 84);
/* 1437 */         height = retrieveUnitValue(parentResolvedHeight.floatValue(), 27);
/*      */       } 
/* 1439 */       if (maxHeight != null && minHeight != null && minHeight.floatValue() > maxHeight.floatValue()) {
/* 1440 */         maxHeight = minHeight;
/*      */       }
/* 1442 */       if (height != null) {
/* 1443 */         if (maxHeight != null) {
/* 1444 */           height = (height.floatValue() > maxHeight.floatValue()) ? maxHeight : height;
/*      */         }
/* 1446 */         if (minHeight != null) {
/* 1447 */           height = (height.floatValue() < minHeight.floatValue()) ? minHeight : height;
/*      */         }
/*      */       } 
/* 1450 */       if (height != null && isBorderBoxSizing(this)) {
/* 1451 */         height = Float.valueOf(height.floatValue() - calculatePaddingBorderHeight(this));
/*      */       }
/*      */     } 
/* 1454 */     return (height != null) ? Float.valueOf(Math.max(0.0F, height.floatValue())) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] calculateRadii(BorderRadius[] radii, Rectangle area, boolean horizontal) {
/* 1467 */     float[] results = new float[4];
/*      */     
/* 1469 */     for (int i = 0; i < 4; i++) {
/* 1470 */       if (null != radii[i]) {
/* 1471 */         UnitValue value = horizontal ? radii[i].getHorizontalRadius() : radii[i].getVerticalRadius();
/* 1472 */         if (value != null) {
/* 1473 */           if (value.getUnitType() == 2) {
/* 1474 */             results[i] = value.getValue() * (horizontal ? area.getWidth() : area.getHeight()) / 100.0F;
/*      */           } else {
/* 1476 */             assert value.getUnitType() == 1;
/* 1477 */             results[i] = value.getValue();
/*      */           } 
/*      */         } else {
/* 1480 */           results[i] = 0.0F;
/*      */         } 
/*      */       } else {
/* 1483 */         results[i] = 0.0F;
/*      */       } 
/*      */     } 
/* 1486 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateHeight(UnitValue updatedHeight) {
/* 1496 */     if (isBorderBoxSizing(this) && updatedHeight.isPointValue()) {
/* 1497 */       updatedHeight.setValue(updatedHeight.getValue() + calculatePaddingBorderHeight(this));
/*      */     }
/*      */     
/* 1500 */     setProperty(27, updatedHeight);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Float retrieveMaxHeight() {
/* 1510 */     Float maxHeight = null, minHeight = null;
/* 1511 */     Float directParentDeclaredHeight = retrieveDirectParentDeclaredHeight();
/* 1512 */     UnitValue maxHeightAsUV = getPropertyAsUnitValue(84);
/* 1513 */     if (maxHeightAsUV != null) {
/* 1514 */       if (directParentDeclaredHeight == null) {
/* 1515 */         if (maxHeightAsUV.isPercentValue()) {
/* 1516 */           maxHeight = null;
/*      */         } else {
/* 1518 */           minHeight = retrieveMinHeight();
/*      */           
/* 1520 */           UnitValue minHeightUV = getPropertyAsUnitValue(85);
/* 1521 */           if (minHeightUV != null && minHeightUV.isPointValue()) {
/* 1522 */             minHeight = Float.valueOf(minHeightUV.getValue());
/*      */           }
/*      */           
/* 1525 */           maxHeight = Float.valueOf(maxHeightAsUV.getValue());
/*      */         } 
/*      */       } else {
/* 1528 */         maxHeight = retrieveUnitValue(directParentDeclaredHeight.floatValue(), 84);
/*      */       } 
/* 1530 */       if (maxHeight != null) {
/* 1531 */         if (minHeight != null && minHeight.floatValue() > maxHeight.floatValue()) {
/* 1532 */           maxHeight = minHeight;
/*      */         }
/* 1534 */         if (isBorderBoxSizing(this)) {
/* 1535 */           maxHeight = Float.valueOf(maxHeight.floatValue() - calculatePaddingBorderHeight(this));
/*      */         }
/* 1537 */         return Float.valueOf((maxHeight.floatValue() > 0.0F) ? maxHeight.floatValue() : 0.0F);
/*      */       } 
/*      */     } 
/*      */     
/* 1541 */     return retrieveHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateMaxHeight(UnitValue updatedMaxHeight) {
/* 1552 */     if (isBorderBoxSizing(this) && updatedMaxHeight.isPointValue()) {
/* 1553 */       updatedMaxHeight.setValue(updatedMaxHeight.getValue() + calculatePaddingBorderHeight(this));
/*      */     }
/*      */     
/* 1556 */     setProperty(84, updatedMaxHeight);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Float retrieveMinHeight() {
/* 1567 */     Float minHeight = null;
/* 1568 */     Float directParentDeclaredHeight = retrieveDirectParentDeclaredHeight();
/* 1569 */     UnitValue minHeightUV = getPropertyAsUnitValue(this, 85);
/* 1570 */     if (minHeightUV != null) {
/* 1571 */       if (directParentDeclaredHeight == null) {
/* 1572 */         if (minHeightUV.isPercentValue()) {
/*      */           
/* 1574 */           minHeight = null;
/*      */         } else {
/*      */           
/* 1577 */           minHeight = Float.valueOf(minHeightUV.getValue());
/*      */         } 
/*      */       } else {
/* 1580 */         minHeight = retrieveUnitValue(directParentDeclaredHeight.floatValue(), 85);
/*      */       } 
/* 1582 */       if (minHeight != null) {
/* 1583 */         if (isBorderBoxSizing(this)) {
/* 1584 */           minHeight = Float.valueOf(minHeight.floatValue() - calculatePaddingBorderHeight(this));
/*      */         }
/* 1586 */         return Float.valueOf((minHeight.floatValue() > 0.0F) ? minHeight.floatValue() : 0.0F);
/*      */       } 
/*      */     } 
/*      */     
/* 1590 */     return retrieveHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateMinHeight(UnitValue updatedMinHeight) {
/* 1601 */     if (isBorderBoxSizing(this) && updatedMinHeight.isPointValue()) {
/* 1602 */       updatedMinHeight.setValue(updatedMinHeight.getValue() + calculatePaddingBorderHeight(this));
/*      */     }
/* 1604 */     setProperty(85, updatedMinHeight);
/*      */   }
/*      */   
/*      */   protected Float retrieveUnitValue(float baseValue, int property) {
/* 1608 */     return retrieveUnitValue(baseValue, property, false);
/*      */   }
/*      */   
/*      */   protected Float retrieveUnitValue(float baseValue, int property, boolean pointOnly) {
/* 1612 */     UnitValue value = getProperty(property);
/* 1613 */     if (pointOnly && value.getUnitType() == 1) {
/* 1614 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1615 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(property) }));
/*      */     } 
/* 1617 */     if (value != null) {
/* 1618 */       if (value.getUnitType() == 2)
/*      */       {
/*      */         
/* 1621 */         return Float.valueOf((value.getValue() != 100.0F) ? (baseValue * value.getValue() / 100.0F) : baseValue);
/*      */       }
/* 1623 */       assert value.getUnitType() == 1;
/* 1624 */       return Float.valueOf(value.getValue());
/*      */     } 
/*      */     
/* 1627 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map<Integer, Object> getOwnProperties() {
/* 1633 */     return this.properties;
/*      */   }
/*      */   
/*      */   protected void addAllProperties(Map<Integer, Object> properties) {
/* 1637 */     this.properties.putAll(properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Float getFirstYLineRecursively() {
/* 1648 */     if (this.childRenderers.size() == 0) {
/* 1649 */       return null;
/*      */     }
/* 1651 */     return ((AbstractRenderer)this.childRenderers.get(0)).getFirstYLineRecursively();
/*      */   }
/*      */   
/*      */   protected Float getLastYLineRecursively() {
/* 1655 */     if (!allowLastYLineRecursiveExtraction()) {
/* 1656 */       return null;
/*      */     }
/* 1658 */     for (int i = this.childRenderers.size() - 1; i >= 0; i--) {
/* 1659 */       IRenderer child = this.childRenderers.get(i);
/* 1660 */       if (child instanceof AbstractRenderer) {
/* 1661 */         Float lastYLine = ((AbstractRenderer)child).getLastYLineRecursively();
/* 1662 */         if (lastYLine != null) {
/* 1663 */           return lastYLine;
/*      */         }
/*      */       } 
/*      */     } 
/* 1667 */     return null;
/*      */   }
/*      */   
/*      */   protected boolean allowLastYLineRecursiveExtraction() {
/* 1671 */     return (!isOverflowProperty(OverflowPropertyValue.HIDDEN, 103) && 
/* 1672 */       !isOverflowProperty(OverflowPropertyValue.HIDDEN, 104));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle applyMargins(Rectangle rect, UnitValue[] margins, boolean reverse) {
/* 1685 */     if (!margins[0].isPointValue()) {
/* 1686 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1687 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(46) }));
/*      */     } 
/* 1689 */     if (!margins[1].isPointValue()) {
/* 1690 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1691 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(45) }));
/*      */     } 
/* 1693 */     if (!margins[2].isPointValue()) {
/* 1694 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1695 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(43) }));
/*      */     } 
/* 1697 */     if (!margins[3].isPointValue()) {
/* 1698 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1699 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*      */     } 
/* 1701 */     return rect.applyMargins(margins[0].getValue(), margins[1].getValue(), margins[2].getValue(), margins[3].getValue(), reverse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected UnitValue[] getMargins() {
/* 1710 */     return getMargins(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected UnitValue[] getPaddings() {
/* 1719 */     return getPaddings(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle applyPaddings(Rectangle rect, UnitValue[] paddings, boolean reverse) {
/* 1732 */     if (!paddings[0].isPointValue()) {
/* 1733 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1734 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(50) }));
/*      */     } 
/* 1736 */     if (!paddings[1].isPointValue()) {
/* 1737 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1738 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(49) }));
/*      */     } 
/* 1740 */     if (!paddings[2].isPointValue()) {
/* 1741 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1742 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(47) }));
/*      */     } 
/* 1744 */     if (!paddings[3].isPointValue()) {
/* 1745 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1746 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(48) }));
/*      */     } 
/* 1748 */     return rect.applyMargins(paddings[0].getValue(), paddings[1].getValue(), paddings[2].getValue(), paddings[3].getValue(), reverse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle applyBorderBox(Rectangle rect, Border[] borders, boolean reverse) {
/* 1761 */     float topWidth = (borders[0] != null) ? borders[0].getWidth() : 0.0F;
/* 1762 */     float rightWidth = (borders[1] != null) ? borders[1].getWidth() : 0.0F;
/* 1763 */     float bottomWidth = (borders[2] != null) ? borders[2].getWidth() : 0.0F;
/* 1764 */     float leftWidth = (borders[3] != null) ? borders[3].getWidth() : 0.0F;
/* 1765 */     return rect.applyMargins(topWidth, rightWidth, bottomWidth, leftWidth, reverse);
/*      */   }
/*      */   
/*      */   protected void applyAbsolutePosition(Rectangle parentRect) {
/* 1769 */     Float top = getPropertyAsFloat(73);
/* 1770 */     Float bottom = getPropertyAsFloat(14);
/* 1771 */     Float left = getPropertyAsFloat(34);
/* 1772 */     Float right = getPropertyAsFloat(54);
/*      */     
/* 1774 */     if (left == null && right == null && BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7))) {
/* 1775 */       right = Float.valueOf(0.0F);
/*      */     }
/*      */     
/* 1778 */     if (top == null && bottom == null) {
/* 1779 */       top = Float.valueOf(0.0F);
/*      */     }
/*      */     
/*      */     try {
/* 1783 */       if (right != null) {
/* 1784 */         move(parentRect.getRight() - right.floatValue() - this.occupiedArea.getBBox().getRight(), 0.0F);
/*      */       }
/*      */       
/* 1787 */       if (left != null) {
/* 1788 */         move(parentRect.getLeft() + left.floatValue() - this.occupiedArea.getBBox().getLeft(), 0.0F);
/*      */       }
/*      */       
/* 1791 */       if (top != null) {
/* 1792 */         move(0.0F, parentRect.getTop() - top.floatValue() - this.occupiedArea.getBBox().getTop());
/*      */       }
/*      */       
/* 1795 */       if (bottom != null) {
/* 1796 */         move(0.0F, parentRect.getBottom() + bottom.floatValue() - this.occupiedArea.getBBox().getBottom());
/*      */       }
/* 1798 */     } catch (Exception exc) {
/* 1799 */       Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1800 */       logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Absolute positioning might be applied incorrectly." }));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void applyRelativePositioningTranslation(boolean reverse) {
/* 1805 */     float top = getPropertyAsFloat(73, Float.valueOf(0.0F)).floatValue();
/* 1806 */     float bottom = getPropertyAsFloat(14, Float.valueOf(0.0F)).floatValue();
/* 1807 */     float left = getPropertyAsFloat(34, Float.valueOf(0.0F)).floatValue();
/* 1808 */     float right = getPropertyAsFloat(54, Float.valueOf(0.0F)).floatValue();
/*      */     
/* 1810 */     int reverseMultiplier = reverse ? -1 : 1;
/*      */     
/* 1812 */     float dxRight = (left != 0.0F) ? (left * reverseMultiplier) : (-right * reverseMultiplier);
/* 1813 */     float dyUp = (top != 0.0F) ? (-top * reverseMultiplier) : (bottom * reverseMultiplier);
/*      */     
/* 1815 */     if (dxRight != 0.0F || dyUp != 0.0F)
/* 1816 */       move(dxRight, dyUp); 
/*      */   }
/*      */   
/*      */   protected void applyDestination(PdfDocument document) {
/* 1820 */     String destination = getProperty(17);
/* 1821 */     if (destination != null) {
/* 1822 */       int pageNumber = this.occupiedArea.getPageNumber();
/* 1823 */       if (pageNumber < 1 || pageNumber > document.getNumberOfPages()) {
/* 1824 */         Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1825 */         String logMessageArg = "Property.DESTINATION, which specifies this element location as destination, see ElementPropertyContainer.setDestination.";
/* 1826 */         logger.warn(MessageFormatUtil.format("Unable to apply page dependent property, because the page on which element is drawn is unknown. Usually this means that element was added to the Canvas instance that was created not with constructor taking PdfPage as argument. Not processed property: {0}", new Object[] { logMessageArg }));
/*      */         return;
/*      */       } 
/* 1829 */       PdfArray array = new PdfArray();
/* 1830 */       array.add(document.getPage(pageNumber).getPdfObject());
/* 1831 */       array.add((PdfObject)PdfName.XYZ);
/* 1832 */       array.add((PdfObject)new PdfNumber(this.occupiedArea.getBBox().getX()));
/* 1833 */       array.add((PdfObject)new PdfNumber((this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight())));
/* 1834 */       array.add((PdfObject)new PdfNumber(0));
/* 1835 */       document.addNamedDestination(destination, array.makeIndirect(document));
/*      */       
/* 1837 */       deleteProperty(17);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void applyAction(PdfDocument document) {
/* 1842 */     PdfAction action = getProperty(1);
/* 1843 */     if (action != null) {
/* 1844 */       PdfLinkAnnotation link = getProperty(88);
/* 1845 */       if (link == null) {
/* 1846 */         link = (PdfLinkAnnotation)(new PdfLinkAnnotation(new Rectangle(0.0F, 0.0F, 0.0F, 0.0F))).setFlags(4);
/* 1847 */         Border border = getProperty(9);
/* 1848 */         if (border != null) {
/* 1849 */           link.setBorder(new PdfArray(new float[] { 0.0F, 0.0F, border.getWidth() }));
/*      */         } else {
/* 1851 */           link.setBorder(new PdfArray(new float[] { 0.0F, 0.0F, 0.0F }));
/*      */         } 
/* 1853 */         setProperty(88, link);
/*      */       } 
/* 1855 */       link.setAction(action);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void applyLinkAnnotation(PdfDocument document) {
/* 1860 */     PdfLinkAnnotation linkAnnotation = getProperty(88);
/* 1861 */     if (linkAnnotation != null) {
/* 1862 */       int pageNumber = this.occupiedArea.getPageNumber();
/* 1863 */       if (pageNumber < 1 || pageNumber > document.getNumberOfPages()) {
/* 1864 */         Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 1865 */         String logMessageArg = "Property.LINK_ANNOTATION, which specifies a link associated with this element content area, see com.itextpdf.layout.element.Link.";
/* 1866 */         logger.warn(MessageFormatUtil.format("Unable to apply page dependent property, because the page on which element is drawn is unknown. Usually this means that element was added to the Canvas instance that was created not with constructor taking PdfPage as argument. Not processed property: {0}", new Object[] { logMessageArg }));
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1872 */       PdfDictionary oldAnnotation = (PdfDictionary)((PdfDictionary)linkAnnotation.getPdfObject()).clone();
/* 1873 */       linkAnnotation = (PdfLinkAnnotation)PdfAnnotation.makeAnnotation((PdfObject)oldAnnotation);
/* 1874 */       Rectangle pdfBBox = calculateAbsolutePdfBBox();
/* 1875 */       linkAnnotation.setRectangle(new PdfArray(pdfBBox));
/*      */       
/* 1877 */       PdfPage page = document.getPage(pageNumber);
/* 1878 */       page.addAnnotation((PdfAnnotation)linkAnnotation);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Float retrieveResolvedParentDeclaredHeight() {
/* 1890 */     if (this.parent != null && this.parent.getProperty(27) != null) {
/* 1891 */       UnitValue parentHeightUV = getPropertyAsUnitValue(this.parent, 27);
/* 1892 */       if (parentHeightUV.isPointValue()) {
/* 1893 */         return Float.valueOf(parentHeightUV.getValue());
/*      */       }
/* 1895 */       return ((AbstractRenderer)this.parent).retrieveHeight();
/*      */     } 
/*      */     
/* 1898 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Float retrieveDirectParentDeclaredHeight() {
/* 1908 */     if (this.parent != null && this.parent.getProperty(27) != null) {
/* 1909 */       UnitValue parentHeightUV = getPropertyAsUnitValue(this.parent, 27);
/* 1910 */       if (parentHeightUV.isPointValue()) {
/* 1911 */         return Float.valueOf(parentHeightUV.getValue());
/*      */       }
/*      */     } 
/* 1914 */     return null;
/*      */   }
/*      */   
/*      */   protected void updateHeightsOnSplit(boolean wasHeightClipped, AbstractRenderer splitRenderer, AbstractRenderer overflowRenderer) {
/* 1918 */     updateHeightsOnSplit(this.occupiedArea.getBBox().getHeight(), wasHeightClipped, splitRenderer, overflowRenderer, true);
/*      */   }
/*      */   
/*      */   void updateHeightsOnSplit(float usedHeight, boolean wasHeightClipped, AbstractRenderer splitRenderer, AbstractRenderer overflowRenderer, boolean enlargeOccupiedAreaOnHeightWasClipped) {
/* 1922 */     if (wasHeightClipped) {
/*      */       
/* 1924 */       Logger logger = LoggerFactory.getLogger(BlockRenderer.class);
/* 1925 */       logger.warn("Element content was clipped because some height properties are set.");
/*      */       
/* 1927 */       if (enlargeOccupiedAreaOnHeightWasClipped) {
/* 1928 */         Float maxHeight = retrieveMaxHeight();
/* 1929 */         splitRenderer.occupiedArea.getBBox()
/* 1930 */           .moveDown(maxHeight.floatValue() - usedHeight)
/* 1931 */           .setHeight(maxHeight.floatValue());
/* 1932 */         usedHeight = maxHeight.floatValue();
/*      */       } 
/*      */     } 
/*      */     
/* 1936 */     if (overflowRenderer == null || isKeepTogether()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1942 */     Float parentResolvedHeightPropertyValue = retrieveResolvedParentDeclaredHeight();
/* 1943 */     UnitValue maxHeightUV = getPropertyAsUnitValue(this, 84);
/* 1944 */     if (maxHeightUV != null) {
/* 1945 */       if (maxHeightUV.isPointValue()) {
/* 1946 */         Float maxHeight = retrieveMaxHeight();
/* 1947 */         UnitValue updateMaxHeight = UnitValue.createPointValue(maxHeight.floatValue() - usedHeight);
/* 1948 */         overflowRenderer.updateMaxHeight(updateMaxHeight);
/* 1949 */       } else if (parentResolvedHeightPropertyValue != null) {
/*      */         
/* 1951 */         float currentOccupiedFraction = usedHeight / parentResolvedHeightPropertyValue.floatValue() * 100.0F;
/*      */         
/* 1953 */         float newFraction = maxHeightUV.getValue() - currentOccupiedFraction;
/*      */         
/* 1955 */         overflowRenderer.updateMinHeight(UnitValue.createPercentValue(newFraction));
/*      */       } 
/*      */     }
/*      */     
/* 1959 */     UnitValue minHeightUV = getPropertyAsUnitValue(this, 85);
/* 1960 */     if (minHeightUV != null) {
/* 1961 */       if (minHeightUV.isPointValue()) {
/* 1962 */         Float minHeight = retrieveMinHeight();
/* 1963 */         UnitValue updateminHeight = UnitValue.createPointValue(minHeight.floatValue() - usedHeight);
/* 1964 */         overflowRenderer.updateMinHeight(updateminHeight);
/* 1965 */       } else if (parentResolvedHeightPropertyValue != null) {
/*      */         
/* 1967 */         float currentOccupiedFraction = usedHeight / parentResolvedHeightPropertyValue.floatValue() * 100.0F;
/*      */         
/* 1969 */         float newFraction = minHeightUV.getValue() - currentOccupiedFraction;
/*      */         
/* 1971 */         overflowRenderer.updateMinHeight(UnitValue.createPercentValue(newFraction));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1976 */     UnitValue heightUV = getPropertyAsUnitValue(this, 27);
/* 1977 */     if (heightUV != null) {
/* 1978 */       if (heightUV.isPointValue()) {
/* 1979 */         Float height = retrieveHeight();
/* 1980 */         UnitValue updateHeight = UnitValue.createPointValue(height.floatValue() - usedHeight);
/* 1981 */         overflowRenderer.updateHeight(updateHeight);
/* 1982 */       } else if (parentResolvedHeightPropertyValue != null) {
/*      */         
/* 1984 */         float currentOccupiedFraction = usedHeight / parentResolvedHeightPropertyValue.floatValue() * 100.0F;
/*      */         
/* 1986 */         float newFraction = heightUV.getValue() - currentOccupiedFraction;
/*      */         
/* 1988 */         overflowRenderer.updateMinHeight(UnitValue.createPercentValue(newFraction));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public MinMaxWidth getMinMaxWidth() {
/* 1995 */     return MinMaxWidthUtils.countDefaultMinMaxWidth(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean setMinMaxWidthBasedOnFixedWidth(MinMaxWidth minMaxWidth) {
/* 2000 */     if (hasAbsoluteUnitValue(77)) {
/*      */       
/* 2002 */       Float width = retrieveWidth(0.0F);
/* 2003 */       if (width != null) {
/* 2004 */         minMaxWidth.setChildrenMaxWidth(width.floatValue());
/* 2005 */         minMaxWidth.setChildrenMinWidth(width.floatValue());
/* 2006 */         return true;
/*      */       } 
/*      */     } 
/* 2009 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isNotFittingHeight(LayoutArea layoutArea) {
/* 2013 */     return (!isPositioned() && this.occupiedArea.getBBox().getHeight() > layoutArea.getBBox().getHeight());
/*      */   }
/*      */   
/*      */   protected boolean isNotFittingWidth(LayoutArea layoutArea) {
/* 2017 */     return (!isPositioned() && this.occupiedArea.getBBox().getWidth() > layoutArea.getBBox().getWidth());
/*      */   }
/*      */   
/*      */   protected boolean isNotFittingLayoutArea(LayoutArea layoutArea) {
/* 2021 */     return (isNotFittingHeight(layoutArea) || isNotFittingWidth(layoutArea));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPositioned() {
/* 2030 */     return !isStaticLayout();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isFixedLayout() {
/* 2039 */     Object positioning = getProperty(52);
/* 2040 */     return Integer.valueOf(4).equals(positioning);
/*      */   }
/*      */   
/*      */   protected boolean isStaticLayout() {
/* 2044 */     Object positioning = getProperty(52);
/* 2045 */     return (positioning == null || Integer.valueOf(1).equals(positioning));
/*      */   }
/*      */   
/*      */   protected boolean isRelativePosition() {
/* 2049 */     Integer positioning = getPropertyAsInteger(52);
/* 2050 */     return Integer.valueOf(2).equals(positioning);
/*      */   }
/*      */   
/*      */   protected boolean isAbsolutePosition() {
/* 2054 */     Integer positioning = getPropertyAsInteger(52);
/* 2055 */     return Integer.valueOf(3).equals(positioning);
/*      */   }
/*      */   
/*      */   protected boolean isKeepTogether() {
/* 2059 */     return Boolean.TRUE.equals(getPropertyAsBoolean(32));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void alignChildHorizontally(IRenderer childRenderer, Rectangle currentArea) {
/* 2066 */     float availableWidth = currentArea.getWidth();
/* 2067 */     HorizontalAlignment horizontalAlignment = (HorizontalAlignment)childRenderer.getProperty(28);
/* 2068 */     if (horizontalAlignment != null && horizontalAlignment != HorizontalAlignment.LEFT) {
/* 2069 */       float freeSpace = availableWidth - childRenderer.getOccupiedArea().getBBox().getWidth();
/* 2070 */       if (freeSpace > 0.0F) {
/*      */         try {
/* 2072 */           switch (horizontalAlignment) {
/*      */             case RIGHT:
/* 2074 */               childRenderer.move(freeSpace, 0.0F);
/*      */               break;
/*      */             case CENTER:
/* 2077 */               childRenderer.move(freeSpace / 2.0F, 0.0F);
/*      */               break;
/*      */           } 
/* 2080 */         } catch (NullPointerException e) {
/* 2081 */           Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 2082 */           logger.error(MessageFormatUtil.format("Occupied area has not been initialized. {0}", new Object[] { "Some of the children might not end up aligned horizontally." }));
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Border[] getBorders() {
/* 2097 */     return getBorders(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BorderRadius[] getBorderRadii() {
/* 2109 */     return getBorderRadii(this);
/*      */   }
/*      */   
/*      */   protected AbstractRenderer setBorders(Border border, int borderNumber) {
/* 2113 */     switch (borderNumber) {
/*      */       case 0:
/* 2115 */         setProperty(13, border);
/*      */         break;
/*      */       case 1:
/* 2118 */         setProperty(12, border);
/*      */         break;
/*      */       case 2:
/* 2121 */         setProperty(10, border);
/*      */         break;
/*      */       case 3:
/* 2124 */         setProperty(11, border);
/*      */         break;
/*      */     } 
/*      */     
/* 2128 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle calculateAbsolutePdfBBox() {
/* 2140 */     Rectangle contentBox = getOccupiedAreaBBox();
/* 2141 */     List<Point> contentBoxPoints = rectangleToPointsList(contentBox);
/* 2142 */     AbstractRenderer renderer = this;
/* 2143 */     while (renderer.parent != null) {
/* 2144 */       if (renderer instanceof BlockRenderer) {
/* 2145 */         Float angle = renderer.<Float>getProperty(55);
/* 2146 */         if (angle != null) {
/* 2147 */           BlockRenderer blockRenderer = (BlockRenderer)renderer;
/* 2148 */           AffineTransform rotationTransform = blockRenderer.createRotationTransformInsideOccupiedArea();
/* 2149 */           transformPoints(contentBoxPoints, rotationTransform);
/*      */         } 
/*      */       } 
/*      */       
/* 2153 */       if (renderer.getProperty(53) != null && (
/* 2154 */         renderer instanceof BlockRenderer || renderer instanceof ImageRenderer || renderer instanceof TableRenderer)) {
/* 2155 */         AffineTransform rotationTransform = renderer.createTransformationInsideOccupiedArea();
/* 2156 */         transformPoints(contentBoxPoints, rotationTransform);
/*      */       } 
/*      */       
/* 2159 */       renderer = (AbstractRenderer)renderer.parent;
/*      */     } 
/*      */     
/* 2162 */     return calculateBBox(contentBoxPoints);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle calculateBBox(List<Point> points) {
/* 2172 */     return Rectangle.calculateBBox(points);
/*      */   }
/*      */   
/*      */   protected List<Point> rectangleToPointsList(Rectangle rect) {
/* 2176 */     return Arrays.asList(rect.toPointsArray());
/*      */   }
/*      */   
/*      */   protected List<Point> transformPoints(List<Point> points, AffineTransform transform) {
/* 2180 */     for (Point point : points) {
/* 2181 */       transform.transform(point, point);
/*      */     }
/*      */     
/* 2184 */     return points;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float[] calculateShiftToPositionBBoxOfPointsAt(float left, float top, List<Point> points) {
/* 2198 */     double minX = Double.MAX_VALUE;
/* 2199 */     double maxY = -1.7976931348623157E308D;
/* 2200 */     for (Point point : points) {
/* 2201 */       minX = Math.min(point.getX(), minX);
/* 2202 */       maxY = Math.max(point.getY(), maxY);
/*      */     } 
/*      */     
/* 2205 */     float dx = (float)(left - minX);
/* 2206 */     float dy = (float)(top - maxY);
/* 2207 */     return new float[] { dx, dy };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean hasAbsoluteUnitValue(int property) {
/* 2217 */     UnitValue value = getProperty(property);
/* 2218 */     return (value != null && value.isPointValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean hasRelativeUnitValue(int property) {
/* 2228 */     UnitValue value = getProperty(property);
/* 2229 */     return (value != null && value.isPercentValue());
/*      */   }
/*      */   
/*      */   boolean isFirstOnRootArea(boolean checkRootAreaOnly) {
/* 2233 */     boolean isFirstOnRootArea = true;
/* 2234 */     IRenderer ancestor = this;
/* 2235 */     while (isFirstOnRootArea && ancestor.getParent() != null) {
/* 2236 */       IRenderer parent = ancestor.getParent();
/* 2237 */       if (parent instanceof RootRenderer)
/* 2238 */       { isFirstOnRootArea = ((RootRenderer)parent).currentArea.isEmptyArea(); }
/* 2239 */       else { if (parent.getOccupiedArea() == null)
/*      */           break; 
/* 2241 */         if (!checkRootAreaOnly)
/* 2242 */           isFirstOnRootArea = (parent.getOccupiedArea().getBBox().getHeight() < 1.0E-4F);  }
/*      */       
/* 2244 */       ancestor = parent;
/*      */     } 
/* 2246 */     return isFirstOnRootArea;
/*      */   }
/*      */   
/*      */   RootRenderer getRootRenderer() {
/* 2250 */     IRenderer currentRenderer = this;
/* 2251 */     while (currentRenderer instanceof AbstractRenderer) {
/* 2252 */       if (currentRenderer instanceof RootRenderer) {
/* 2253 */         return (RootRenderer)currentRenderer;
/*      */       }
/* 2255 */       currentRenderer = ((AbstractRenderer)currentRenderer).getParent();
/*      */     } 
/* 2257 */     return null;
/*      */   }
/*      */   
/*      */   static float calculateAdditionalWidth(AbstractRenderer renderer) {
/* 2261 */     Rectangle dummy = new Rectangle(0.0F, 0.0F);
/* 2262 */     renderer.applyMargins(dummy, true);
/* 2263 */     renderer.applyBorderBox(dummy, true);
/* 2264 */     renderer.applyPaddings(dummy, true);
/* 2265 */     return dummy.getWidth();
/*      */   }
/*      */   
/*      */   static boolean noAbsolutePositionInfo(IRenderer renderer) {
/* 2269 */     return (!renderer.hasProperty(73) && !renderer.hasProperty(14) && !renderer.hasProperty(34) && !renderer.hasProperty(54));
/*      */   }
/*      */   
/*      */   static Float getPropertyAsFloat(IRenderer renderer, int property) {
/* 2273 */     return NumberUtil.asFloat(renderer.getProperty(property));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static UnitValue getPropertyAsUnitValue(IRenderer renderer, int property) {
/* 2284 */     UnitValue result = (UnitValue)renderer.getProperty(property);
/* 2285 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void shrinkOccupiedAreaForAbsolutePosition() {
/* 2292 */     if (isAbsolutePosition()) {
/* 2293 */       Float left = getPropertyAsFloat(34);
/* 2294 */       Float right = getPropertyAsFloat(54);
/* 2295 */       UnitValue width = getProperty(77);
/* 2296 */       if (left == null && right == null && width == null) {
/* 2297 */         this.occupiedArea.getBBox().setWidth(0.0F);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   void drawPositionedChildren(DrawContext drawContext) {
/* 2303 */     for (IRenderer positionedChild : this.positionedRenderers) {
/* 2304 */       positionedChild.draw(drawContext);
/*      */     }
/*      */   }
/*      */   
/*      */   FontCharacteristics createFontCharacteristics() {
/* 2309 */     FontCharacteristics fc = new FontCharacteristics();
/* 2310 */     if (hasProperty(95)) {
/* 2311 */       fc.setFontWeight(getProperty(95));
/*      */     }
/* 2313 */     if (hasProperty(94)) {
/* 2314 */       fc.setFontStyle(getProperty(94));
/*      */     }
/* 2316 */     return fc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PdfFont resolveFirstPdfFont() {
/* 2330 */     Object font = getProperty(20);
/* 2331 */     if (font instanceof PdfFont)
/* 2332 */       return (PdfFont)font; 
/* 2333 */     if (font instanceof String || font instanceof String[]) {
/* 2334 */       if (font instanceof String) {
/*      */         
/* 2336 */         Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);
/* 2337 */         logger.warn("The \"Property.FONT\" property with values of String type is deprecated, use String[] as property value type instead.");
/* 2338 */         List<String> splitFontFamily = FontFamilySplitter.splitFontFamily((String)font);
/* 2339 */         font = splitFontFamily.toArray(new String[splitFontFamily.size()]);
/*      */       } 
/* 2341 */       FontProvider provider = getProperty(91);
/* 2342 */       if (provider == null) {
/* 2343 */         throw new IllegalStateException("FontProvider and FontSet are empty. Cannot resolve font family name (see ElementPropertyContainer#setFontFamily) without initialized FontProvider (see RootElement#setFontProvider).");
/*      */       }
/* 2345 */       FontSet fontSet = getProperty(98);
/* 2346 */       if (provider.getFontSet().isEmpty() && (fontSet == null || fontSet.isEmpty())) {
/* 2347 */         throw new IllegalStateException("FontProvider and FontSet are empty. Cannot resolve font family name (see ElementPropertyContainer#setFontFamily) without initialized FontProvider (see RootElement#setFontProvider).");
/*      */       }
/* 2349 */       FontCharacteristics fc = createFontCharacteristics();
/* 2350 */       return resolveFirstPdfFont((String[])font, provider, fc, fontSet);
/*      */     } 
/* 2352 */     throw new IllegalStateException("String[] or PdfFont expected as value of FONT property");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PdfFont resolveFirstPdfFont(String[] font, FontProvider provider, FontCharacteristics fc, FontSet additionalFonts) {
/* 2366 */     FontSelector fontSelector = provider.getFontSelector(Arrays.asList(font), fc, additionalFonts);
/* 2367 */     return provider.getPdfFont(fontSelector.bestMatch(), additionalFonts);
/*      */   }
/*      */   
/*      */   static Border[] getBorders(IRenderer renderer) {
/* 2371 */     Border border = (Border)renderer.getProperty(9);
/* 2372 */     Border topBorder = (Border)renderer.getProperty(13);
/* 2373 */     Border rightBorder = (Border)renderer.getProperty(12);
/* 2374 */     Border bottomBorder = (Border)renderer.getProperty(10);
/* 2375 */     Border leftBorder = (Border)renderer.getProperty(11);
/*      */     
/* 2377 */     Border[] borders = { topBorder, rightBorder, bottomBorder, leftBorder };
/*      */     
/* 2379 */     if (!hasOwnOrModelProperty(renderer, 13)) {
/* 2380 */       borders[0] = border;
/*      */     }
/* 2382 */     if (!hasOwnOrModelProperty(renderer, 12)) {
/* 2383 */       borders[1] = border;
/*      */     }
/* 2385 */     if (!hasOwnOrModelProperty(renderer, 10)) {
/* 2386 */       borders[2] = border;
/*      */     }
/* 2388 */     if (!hasOwnOrModelProperty(renderer, 11)) {
/* 2389 */       borders[3] = border;
/*      */     }
/*      */     
/* 2392 */     return borders;
/*      */   }
/*      */   
/*      */   void applyAbsolutePositionIfNeeded(LayoutContext layoutContext) {
/* 2396 */     if (isAbsolutePosition()) {
/* 2397 */       applyAbsolutePosition((layoutContext instanceof PositionedLayoutContext) ? ((PositionedLayoutContext)layoutContext).getParentOccupiedArea().getBBox() : layoutContext.getArea().getBBox());
/*      */     }
/*      */   }
/*      */   
/*      */   void preparePositionedRendererAndAreaForLayout(IRenderer childPositionedRenderer, Rectangle fullBbox, Rectangle parentBbox) {
/* 2402 */     Float left = getPropertyAsFloat(childPositionedRenderer, 34);
/* 2403 */     Float right = getPropertyAsFloat(childPositionedRenderer, 54);
/* 2404 */     Float top = getPropertyAsFloat(childPositionedRenderer, 73);
/* 2405 */     Float bottom = getPropertyAsFloat(childPositionedRenderer, 14);
/* 2406 */     childPositionedRenderer.setParent(this);
/* 2407 */     adjustPositionedRendererLayoutBoxWidth(childPositionedRenderer, fullBbox, left, right);
/*      */     
/* 2409 */     if (Integer.valueOf(3).equals(childPositionedRenderer.getProperty(52))) {
/* 2410 */       updateMinHeightForAbsolutelyPositionedRenderer(childPositionedRenderer, parentBbox, top, bottom);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateMinHeightForAbsolutelyPositionedRenderer(IRenderer renderer, Rectangle parentRendererBox, Float top, Float bottom) {
/* 2415 */     if (top != null && bottom != null && !renderer.hasProperty(27)) {
/* 2416 */       UnitValue currentMaxHeight = getPropertyAsUnitValue(renderer, 84);
/* 2417 */       UnitValue currentMinHeight = getPropertyAsUnitValue(renderer, 85);
/* 2418 */       float resolvedMinHeight = Math.max(0.0F, parentRendererBox.getTop() - top.floatValue() - parentRendererBox.getBottom() - bottom.floatValue());
/*      */       
/* 2420 */       Rectangle dummy = new Rectangle(0.0F, 0.0F);
/* 2421 */       if (!isBorderBoxSizing(renderer)) {
/* 2422 */         applyPaddings(dummy, getPaddings(renderer), true);
/* 2423 */         applyBorderBox(dummy, getBorders(renderer), true);
/*      */       } 
/* 2425 */       applyMargins(dummy, getMargins(renderer), true);
/* 2426 */       resolvedMinHeight -= dummy.getHeight();
/*      */       
/* 2428 */       if (currentMinHeight != null) {
/* 2429 */         resolvedMinHeight = Math.max(resolvedMinHeight, currentMinHeight.getValue());
/*      */       }
/* 2431 */       if (currentMaxHeight != null) {
/* 2432 */         resolvedMinHeight = Math.min(resolvedMinHeight, currentMaxHeight.getValue());
/*      */       }
/*      */       
/* 2435 */       renderer.setProperty(85, UnitValue.createPointValue(resolvedMinHeight));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void adjustPositionedRendererLayoutBoxWidth(IRenderer renderer, Rectangle fullBbox, Float left, Float right) {
/* 2440 */     if (left != null) {
/* 2441 */       fullBbox.setWidth(fullBbox.getWidth() - left.floatValue()).setX(fullBbox.getX() + left.floatValue());
/*      */     }
/* 2443 */     if (right != null) {
/* 2444 */       fullBbox.setWidth(fullBbox.getWidth() - right.floatValue());
/*      */     }
/*      */     
/* 2447 */     if (left == null && right == null && !renderer.hasProperty(77)) {
/*      */       
/* 2449 */       MinMaxWidth minMaxWidth = (renderer instanceof BlockRenderer) ? ((BlockRenderer)renderer).getMinMaxWidth() : null;
/* 2450 */       if (minMaxWidth != null && minMaxWidth.getMaxWidth() < fullBbox.getWidth()) {
/* 2451 */         fullBbox.setWidth(minMaxWidth.getMaxWidth() + 1.0E-4F);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static float calculatePaddingBorderWidth(AbstractRenderer renderer) {
/* 2457 */     Rectangle dummy = new Rectangle(0.0F, 0.0F);
/* 2458 */     renderer.applyBorderBox(dummy, true);
/* 2459 */     renderer.applyPaddings(dummy, true);
/* 2460 */     return dummy.getWidth();
/*      */   }
/*      */   
/*      */   private static float calculatePaddingBorderHeight(AbstractRenderer renderer) {
/* 2464 */     Rectangle dummy = new Rectangle(0.0F, 0.0F);
/* 2465 */     renderer.applyBorderBox(dummy, true);
/* 2466 */     renderer.applyPaddings(dummy, true);
/* 2467 */     return dummy.getHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AffineTransform createTransformationInsideOccupiedArea() {
/* 2478 */     Rectangle backgroundArea = applyMargins(this.occupiedArea.clone().getBBox(), false);
/* 2479 */     float x = backgroundArea.getX();
/* 2480 */     float y = backgroundArea.getY();
/* 2481 */     float height = backgroundArea.getHeight();
/* 2482 */     float width = backgroundArea.getWidth();
/*      */     
/* 2484 */     AffineTransform transform = AffineTransform.getTranslateInstance((-1.0F * (x + width / 2.0F)), (-1.0F * (y + height / 2.0F)));
/* 2485 */     transform.preConcatenate(Transform.getAffineTransform(getProperty(53), width, height));
/* 2486 */     transform.preConcatenate(AffineTransform.getTranslateInstance((x + width / 2.0F), (y + height / 2.0F)));
/*      */     
/* 2488 */     return transform;
/*      */   }
/*      */   
/*      */   protected void beginTransformationIfApplied(PdfCanvas canvas) {
/* 2492 */     if (getProperty(53) != null) {
/* 2493 */       AffineTransform transform = createTransformationInsideOccupiedArea();
/* 2494 */       canvas.saveState().concatMatrix(transform);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void endTransformationIfApplied(PdfCanvas canvas) {
/* 2499 */     if (getProperty(53) != null) {
/* 2500 */       canvas.restoreState();
/*      */     }
/*      */   }
/*      */   
/*      */   private static UnitValue[] getMargins(IRenderer renderer) {
/* 2505 */     return new UnitValue[] { (UnitValue)renderer.getProperty(46), (UnitValue)renderer.getProperty(45), (UnitValue)renderer
/* 2506 */         .getProperty(43), (UnitValue)renderer.getProperty(44) };
/*      */   }
/*      */   
/*      */   private static BorderRadius[] getBorderRadii(IRenderer renderer) {
/* 2510 */     BorderRadius radius = (BorderRadius)renderer.getProperty(101);
/* 2511 */     BorderRadius topLeftRadius = (BorderRadius)renderer.getProperty(110);
/* 2512 */     BorderRadius topRightRadius = (BorderRadius)renderer.getProperty(111);
/* 2513 */     BorderRadius bottomRightRadius = (BorderRadius)renderer.getProperty(112);
/* 2514 */     BorderRadius bottomLeftRadius = (BorderRadius)renderer.getProperty(113);
/*      */     
/* 2516 */     BorderRadius[] borderRadii = { topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius };
/*      */     
/* 2518 */     if (!hasOwnOrModelProperty(renderer, 110)) {
/* 2519 */       borderRadii[0] = radius;
/*      */     }
/* 2521 */     if (!hasOwnOrModelProperty(renderer, 111)) {
/* 2522 */       borderRadii[1] = radius;
/*      */     }
/* 2524 */     if (!hasOwnOrModelProperty(renderer, 112)) {
/* 2525 */       borderRadii[2] = radius;
/*      */     }
/* 2527 */     if (!hasOwnOrModelProperty(renderer, 113)) {
/* 2528 */       borderRadii[3] = radius;
/*      */     }
/*      */     
/* 2531 */     return borderRadii;
/*      */   }
/*      */   
/*      */   private static UnitValue[] getPaddings(IRenderer renderer) {
/* 2535 */     return new UnitValue[] { (UnitValue)renderer.getProperty(50), (UnitValue)renderer.getProperty(49), (UnitValue)renderer
/* 2536 */         .getProperty(47), (UnitValue)renderer.getProperty(48) };
/*      */   }
/*      */   
/*      */   private static boolean hasOwnOrModelProperty(IRenderer renderer, int property) {
/* 2540 */     return (renderer.hasOwnProperty(property) || (null != renderer.getModelElement() && renderer.getModelElement().hasProperty(property)));
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/AbstractRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */