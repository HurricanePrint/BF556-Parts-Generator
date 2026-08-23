/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.io.image.ImageData;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import com.itextpdf.layout.property.ObjectFit;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.ImageRenderer;
/*     */ import com.itextpdf.layout.tagging.IAccessibleElement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Image
/*     */   extends AbstractElement<Image>
/*     */   implements ILeafElement, IAccessibleElement
/*     */ {
/*     */   protected PdfXObject xObject;
/*     */   protected DefaultAccessibilityProperties tagProperties;
/*     */   
/*     */   public Image(PdfImageXObject xObject) {
/*  82 */     this.xObject = (PdfXObject)xObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image(PdfFormXObject xObject) {
/*  92 */     this.xObject = (PdfXObject)xObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image(PdfImageXObject xObject, float width) {
/* 103 */     this.xObject = (PdfXObject)xObject;
/* 104 */     setWidth(width);
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
/*     */   public Image(PdfImageXObject xObject, float left, float bottom, float width) {
/* 117 */     this.xObject = (PdfXObject)xObject;
/* 118 */     setProperty(34, Float.valueOf(left));
/* 119 */     setProperty(14, Float.valueOf(bottom));
/* 120 */     setWidth(width);
/* 121 */     setProperty(52, Integer.valueOf(4));
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
/*     */   public Image(PdfImageXObject xObject, float left, float bottom) {
/* 133 */     this.xObject = (PdfXObject)xObject;
/* 134 */     setProperty(34, Float.valueOf(left));
/* 135 */     setProperty(14, Float.valueOf(bottom));
/* 136 */     setProperty(52, Integer.valueOf(4));
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
/*     */   public Image(PdfFormXObject xObject, float left, float bottom) {
/* 148 */     this.xObject = (PdfXObject)xObject;
/* 149 */     setProperty(34, Float.valueOf(left));
/* 150 */     setProperty(14, Float.valueOf(bottom));
/* 151 */     setProperty(52, Integer.valueOf(4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image(ImageData img) {
/* 161 */     this(new PdfImageXObject(checkImageType(img)));
/* 162 */     setProperty(19, Boolean.valueOf(true));
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
/*     */   public Image(ImageData img, float left, float bottom) {
/* 174 */     this(new PdfImageXObject(checkImageType(img)), left, bottom);
/* 175 */     setProperty(19, Boolean.valueOf(true));
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
/*     */   public Image(ImageData img, float left, float bottom, float width) {
/* 188 */     this(new PdfImageXObject(checkImageType(img)), left, bottom, width);
/* 189 */     setProperty(19, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfXObject getXObject() {
/* 198 */     return this.xObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setRotationAngle(double radAngle) {
/* 208 */     setProperty(55, Double.valueOf(radAngle));
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginLeft() {
/* 218 */     return (UnitValue)getProperty(44);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMarginLeft(float value) {
/* 228 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 229 */     setProperty(44, marginUV);
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginRight() {
/* 239 */     return (UnitValue)getProperty(45);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMarginRight(float value) {
/* 249 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 250 */     setProperty(45, marginUV);
/* 251 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginTop() {
/* 260 */     return (UnitValue)getProperty(46);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMarginTop(float value) {
/* 270 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 271 */     setProperty(46, marginUV);
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginBottom() {
/* 281 */     return (UnitValue)getProperty(43);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMarginBottom(float value) {
/* 291 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 292 */     setProperty(43, marginUV);
/* 293 */     return this;
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
/*     */   public Image setMargins(float marginTop, float marginRight, float marginBottom, float marginLeft) {
/* 306 */     return setMarginTop(marginTop).setMarginRight(marginRight).setMarginBottom(marginBottom).setMarginLeft(marginLeft);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingLeft() {
/* 315 */     return (UnitValue)getProperty(48);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setPaddingLeft(float value) {
/* 325 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 326 */     setProperty(48, paddingUV);
/* 327 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingRight() {
/* 336 */     return (UnitValue)getProperty(49);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setPaddingRight(float value) {
/* 346 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 347 */     setProperty(49, paddingUV);
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingTop() {
/* 357 */     return (UnitValue)getProperty(50);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setPaddingTop(float value) {
/* 367 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 368 */     setProperty(50, paddingUV);
/* 369 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingBottom() {
/* 378 */     return (UnitValue)getProperty(47);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setPaddingBottom(float value) {
/* 388 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 389 */     setProperty(47, paddingUV);
/* 390 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setPadding(float commonPadding) {
/* 400 */     return setPaddings(commonPadding, commonPadding, commonPadding, commonPadding);
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
/*     */   public Image setPaddings(float paddingTop, float paddingRight, float paddingBottom, float paddingLeft) {
/* 413 */     setPaddingTop(paddingTop);
/* 414 */     setPaddingRight(paddingRight);
/* 415 */     setPaddingBottom(paddingBottom);
/* 416 */     setPaddingLeft(paddingLeft);
/* 417 */     return this;
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
/*     */   public Image scale(float horizontalScaling, float verticalScaling) {
/* 429 */     setProperty(29, Float.valueOf(horizontalScaling));
/* 430 */     setProperty(76, Float.valueOf(verticalScaling));
/* 431 */     return this;
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
/*     */   public Image scaleToFit(float fitWidth, float fitHeight) {
/* 443 */     float horizontalScaling = fitWidth / this.xObject.getWidth();
/* 444 */     float verticalScaling = fitHeight / this.xObject.getHeight();
/* 445 */     return scale(Math.min(horizontalScaling, verticalScaling), Math.min(horizontalScaling, verticalScaling));
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
/*     */   public Image scaleAbsolute(float fitWidth, float fitHeight) {
/* 457 */     float horizontalScaling = fitWidth / this.xObject.getWidth();
/* 458 */     float verticalScaling = fitHeight / this.xObject.getHeight();
/* 459 */     return scale(horizontalScaling, verticalScaling);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setAutoScale(boolean autoScale) {
/* 469 */     if (hasProperty(5) && hasProperty(4) && autoScale && (((Boolean)
/* 470 */       getProperty(5)).booleanValue() || ((Boolean)
/* 471 */       getProperty(4)).booleanValue())) {
/* 472 */       Logger logger = LoggerFactory.getLogger(Image.class);
/* 473 */       logger.warn("The image cannot be auto scaled and scaled by a certain parameter simultaneously");
/*     */     } 
/* 475 */     setProperty(3, Boolean.valueOf(autoScale));
/* 476 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setAutoScaleHeight(boolean autoScale) {
/* 487 */     if (hasProperty(5) && autoScale && ((Boolean)getProperty(5)).booleanValue()) {
/* 488 */       setProperty(5, Boolean.valueOf(false));
/* 489 */       setProperty(4, Boolean.valueOf(false));
/* 490 */       setProperty(3, Boolean.valueOf(true));
/*     */     } else {
/* 492 */       setProperty(5, Boolean.valueOf(autoScale));
/*     */     } 
/* 494 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setAutoScaleWidth(boolean autoScale) {
/* 504 */     if (hasProperty(4) && autoScale && ((Boolean)getProperty(4)).booleanValue()) {
/* 505 */       setProperty(5, Boolean.valueOf(false));
/* 506 */       setProperty(4, Boolean.valueOf(false));
/* 507 */       setProperty(3, Boolean.valueOf(true));
/*     */     } else {
/* 509 */       setProperty(5, Boolean.valueOf(autoScale));
/*     */     } 
/* 511 */     return this;
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
/*     */   public Image setFixedPosition(float left, float bottom) {
/* 524 */     setFixedPosition(left, bottom, getWidth());
/* 525 */     return this;
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
/*     */   public Image setFixedPosition(int pageNumber, float left, float bottom) {
/* 539 */     setFixedPosition(pageNumber, left, bottom, getWidth());
/* 540 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getImageWidth() {
/* 550 */     return this.xObject.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getImageHeight() {
/* 560 */     return this.xObject.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setHeight(float height) {
/* 570 */     UnitValue heightAsUV = UnitValue.createPointValue(height);
/* 571 */     setProperty(27, heightAsUV);
/* 572 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setHeight(UnitValue height) {
/* 582 */     setProperty(27, height);
/* 583 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMaxHeight(float maxHeight) {
/* 593 */     UnitValue maxHeightAsUv = UnitValue.createPointValue(maxHeight);
/* 594 */     setProperty(84, maxHeightAsUv);
/* 595 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMaxHeight(UnitValue maxHeight) {
/* 605 */     setProperty(84, maxHeight);
/* 606 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMinHeight(float minHeight) {
/* 616 */     UnitValue minHeightAsUv = UnitValue.createPointValue(minHeight);
/* 617 */     setProperty(85, minHeightAsUv);
/* 618 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMinHeight(UnitValue minHeight) {
/* 628 */     setProperty(85, minHeight);
/* 629 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMaxWidth(float maxWidth) {
/* 639 */     UnitValue minHeightAsUv = UnitValue.createPointValue(maxWidth);
/* 640 */     setProperty(79, minHeightAsUv);
/* 641 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMaxWidth(UnitValue maxWidth) {
/* 651 */     setProperty(79, maxWidth);
/* 652 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMinWidth(float minWidth) {
/* 662 */     UnitValue minHeightAsUv = UnitValue.createPointValue(minWidth);
/* 663 */     setProperty(80, minHeightAsUv);
/* 664 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setMinWidth(UnitValue minWidth) {
/* 674 */     setProperty(80, minWidth);
/* 675 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setWidth(float width) {
/* 685 */     setProperty(77, UnitValue.createPointValue(width));
/* 686 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setWidth(UnitValue width) {
/* 696 */     setProperty(77, width);
/* 697 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getWidth() {
/* 707 */     return (UnitValue)getProperty(77);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getImageScaledWidth() {
/* 717 */     return (null == getProperty(29)) ? this.xObject
/* 718 */       .getWidth() : (this.xObject
/* 719 */       .getWidth() * ((Float)getProperty(29)).floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getImageScaledHeight() {
/* 728 */     return (null == getProperty(76)) ? this.xObject
/* 729 */       .getHeight() : (this.xObject
/* 730 */       .getHeight() * ((Float)getProperty(76)).floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image setObjectFit(ObjectFit objectFit) {
/* 740 */     setProperty(125, objectFit);
/* 741 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectFit getObjectFit() {
/* 751 */     if (hasProperty(125)) {
/* 752 */       return (ObjectFit)getProperty(125);
/*     */     }
/* 754 */     return ObjectFit.FILL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/* 760 */     if (this.tagProperties == null) {
/* 761 */       this.tagProperties = new DefaultAccessibilityProperties("Figure");
/*     */     }
/* 763 */     return (AccessibilityProperties)this.tagProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 768 */     return (IRenderer)new ImageRenderer(this);
/*     */   }
/*     */   
/*     */   private static ImageData checkImageType(ImageData image) {
/* 772 */     if (image instanceof com.itextpdf.kernel.pdf.canvas.wmf.WmfImageData) {
/* 773 */       throw new PdfException("Cannot create layout image by WmfImage instance. First convert the image into FormXObject and then use the corresponding layout image constructor.");
/*     */     }
/* 775 */     return image;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/Image.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */