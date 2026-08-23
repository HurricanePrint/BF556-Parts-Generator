/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BackgroundImage
/*     */ {
/*  53 */   private static final BlendMode DEFAULT_BLEND_MODE = BlendMode.NORMAL;
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfXObject image;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected boolean repeatX;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected boolean repeatY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractLinearGradientBuilder linearGradientBuilder;
/*     */ 
/*     */ 
/*     */   
/*  75 */   private BlendMode blendMode = DEFAULT_BLEND_MODE;
/*     */ 
/*     */   
/*     */   private BackgroundRepeat repeat;
/*     */ 
/*     */   
/*     */   private final BackgroundPosition position;
/*     */ 
/*     */   
/*     */   private final BackgroundSize backgroundSize;
/*     */ 
/*     */   
/*     */   private final BackgroundBox backgroundClip;
/*     */   
/*     */   private final BackgroundBox backgroundOrigin;
/*     */ 
/*     */   
/*     */   public BackgroundImage(BackgroundImage backgroundImage) {
/*  93 */     this((backgroundImage.getImage() == null) ? (PdfXObject)backgroundImage.getForm() : (PdfXObject)backgroundImage.getImage(), backgroundImage
/*  94 */         .getRepeat(), backgroundImage
/*  95 */         .getBackgroundPosition(), backgroundImage
/*  96 */         .getBackgroundSize(), backgroundImage
/*  97 */         .getLinearGradientBuilder(), backgroundImage
/*  98 */         .getBlendMode(), backgroundImage
/*  99 */         .getBackgroundClip(), backgroundImage
/* 100 */         .getBackgroundOrigin());
/* 101 */     this.repeatX = backgroundImage.isRepeatX();
/* 102 */     this.repeatY = backgroundImage.isRepeatY();
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
/*     */   @Deprecated
/*     */   public BackgroundImage(PdfImageXObject image, BackgroundRepeat repeat, BlendMode blendMode) {
/* 115 */     this((PdfXObject)image, repeat, new BackgroundPosition(), new BackgroundSize(), null, blendMode, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
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
/*     */   @Deprecated
/*     */   public BackgroundImage(PdfFormXObject image, BackgroundRepeat repeat, BlendMode blendMode) {
/* 129 */     this((PdfXObject)image, repeat, new BackgroundPosition(), new BackgroundSize(), null, blendMode, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
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
/*     */   @Deprecated
/*     */   public BackgroundImage(PdfImageXObject image, BackgroundRepeat repeat) {
/* 142 */     this((PdfXObject)image, repeat, new BackgroundPosition(), new BackgroundSize(), null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
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
/*     */   @Deprecated
/*     */   public BackgroundImage(PdfFormXObject image, BackgroundRepeat repeat) {
/* 155 */     this((PdfXObject)image, repeat, new BackgroundPosition(), new BackgroundSize(), null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BackgroundImage(PdfImageXObject image) {
/* 167 */     this((PdfXObject)image, new BackgroundRepeat(), new BackgroundPosition(), new BackgroundSize(), null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BackgroundImage(PdfFormXObject image) {
/* 179 */     this((PdfXObject)image, new BackgroundRepeat(), new BackgroundPosition(), new BackgroundSize(), null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
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
/*     */   @Deprecated
/*     */   public BackgroundImage(PdfImageXObject image, boolean repeatX, boolean repeatY) {
/* 193 */     this((PdfXObject)image, new BackgroundRepeat(repeatX ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT, repeatY ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT), new BackgroundPosition(), new BackgroundSize(), null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
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
/*     */   @Deprecated
/*     */   public BackgroundImage(PdfFormXObject image, boolean repeatX, boolean repeatY) {
/* 209 */     this((PdfXObject)image, new BackgroundRepeat(repeatX ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT, repeatY ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT), new BackgroundPosition(), new BackgroundSize(), null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
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
/*     */   @Deprecated
/*     */   public BackgroundImage(AbstractLinearGradientBuilder linearGradientBuilder) {
/* 224 */     this(linearGradientBuilder, DEFAULT_BLEND_MODE);
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
/*     */   @Deprecated
/*     */   public BackgroundImage(AbstractLinearGradientBuilder linearGradientBuilder, BlendMode blendMode) {
/* 237 */     this(null, new BackgroundRepeat(BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT), new BackgroundPosition(), new BackgroundSize(), linearGradientBuilder, blendMode, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfImageXObject getImage() {
/* 243 */     return (this.image instanceof PdfImageXObject) ? (PdfImageXObject)this.image : null;
/*     */   }
/*     */   
/*     */   public PdfFormXObject getForm() {
/* 247 */     return (this.image instanceof PdfFormXObject) ? (PdfFormXObject)this.image : null;
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
/*     */   private BackgroundImage(PdfXObject image, BackgroundRepeat repeat, BackgroundPosition position, BackgroundSize backgroundSize, AbstractLinearGradientBuilder linearGradientBuilder, BlendMode blendMode, BackgroundBox clip, BackgroundBox origin) {
/* 265 */     this.image = image;
/* 266 */     if (repeat != null) {
/* 267 */       this.repeatX = !repeat.isNoRepeatOnXAxis();
/* 268 */       this.repeatY = !repeat.isNoRepeatOnYAxis();
/*     */     } 
/* 270 */     this.repeat = repeat;
/* 271 */     this.position = position;
/* 272 */     this.backgroundSize = backgroundSize;
/* 273 */     this.linearGradientBuilder = linearGradientBuilder;
/* 274 */     if (blendMode != null) {
/* 275 */       this.blendMode = blendMode;
/*     */     }
/* 277 */     this.backgroundClip = clip;
/* 278 */     this.backgroundOrigin = origin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundPosition getBackgroundPosition() {
/* 287 */     return this.position;
/*     */   }
/*     */   
/*     */   public AbstractLinearGradientBuilder getLinearGradientBuilder() {
/* 291 */     return this.linearGradientBuilder;
/*     */   }
/*     */   
/*     */   public boolean isBackgroundSpecified() {
/* 295 */     return (this.image instanceof PdfFormXObject || this.image instanceof PdfImageXObject || this.linearGradientBuilder != null);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean isRepeatX() {
/* 300 */     return this.repeatX;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean isRepeatY() {
/* 305 */     return this.repeatY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundSize getBackgroundSize() {
/* 314 */     return this.backgroundSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getImageWidth() {
/* 321 */     return this.image.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getImageHeight() {
/* 328 */     return this.image.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public float getWidth() {
/* 338 */     return this.image.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public float getHeight() {
/* 348 */     return this.image.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundRepeat getRepeat() {
/* 358 */     if (this.repeatX == this.repeat.isNoRepeatOnXAxis()) {
/* 359 */       this
/* 360 */         .repeat = new BackgroundRepeat(this.repeatX ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT, this.repeat.getYAxisRepeat());
/*     */     }
/* 362 */     if (this.repeatY == this.repeat.isNoRepeatOnYAxis()) {
/* 363 */       this.repeat = new BackgroundRepeat(this.repeat.getXAxisRepeat(), this.repeatY ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT);
/*     */     }
/*     */ 
/*     */     
/* 367 */     return this.repeat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlendMode getBlendMode() {
/* 376 */     return this.blendMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundBox getBackgroundClip() {
/* 385 */     return this.backgroundClip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BackgroundBox getBackgroundOrigin() {
/* 394 */     return this.backgroundOrigin;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private PdfXObject image;
/*     */     
/*     */     private AbstractLinearGradientBuilder linearGradientBuilder;
/*     */     
/* 404 */     private BackgroundPosition position = new BackgroundPosition();
/* 405 */     private BackgroundRepeat repeat = new BackgroundRepeat();
/* 406 */     private BlendMode blendMode = BackgroundImage.DEFAULT_BLEND_MODE;
/* 407 */     private BackgroundSize backgroundSize = new BackgroundSize();
/* 408 */     private BackgroundBox clip = BackgroundBox.BORDER_BOX;
/* 409 */     private BackgroundBox origin = BackgroundBox.PADDING_BOX;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setImage(PdfXObject image) {
/* 423 */       this.image = image;
/* 424 */       this.linearGradientBuilder = null;
/* 425 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setLinearGradientBuilder(AbstractLinearGradientBuilder linearGradientBuilder) {
/* 437 */       this.linearGradientBuilder = linearGradientBuilder;
/* 438 */       this.repeat = new BackgroundRepeat(BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT);
/* 439 */       this.image = null;
/* 440 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setBackgroundRepeat(BackgroundRepeat repeat) {
/* 450 */       this.repeat = repeat;
/* 451 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setBackgroundPosition(BackgroundPosition position) {
/* 461 */       this.position = position;
/* 462 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setBackgroundBlendMode(BlendMode blendMode) {
/* 472 */       if (blendMode != null) {
/* 473 */         this.blendMode = blendMode;
/*     */       }
/* 475 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setBackgroundSize(BackgroundSize backgroundSize) {
/* 485 */       if (backgroundSize != null) {
/* 486 */         this.backgroundSize = backgroundSize;
/*     */       }
/* 488 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setBackgroundClip(BackgroundBox clip) {
/* 498 */       this.clip = clip;
/* 499 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setBackgroundOrigin(BackgroundBox origin) {
/* 509 */       this.origin = origin;
/* 510 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BackgroundImage build() {
/* 519 */       return new BackgroundImage(this.image, this.repeat, this.position, this.backgroundSize, this.linearGradientBuilder, this.blendMode, this.clip, this.origin);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/BackgroundImage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */