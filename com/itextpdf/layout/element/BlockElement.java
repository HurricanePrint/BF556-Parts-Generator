/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.property.VerticalAlignment;
/*     */ import com.itextpdf.layout.tagging.IAccessibleElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockElement<T extends IElement>
/*     */   extends AbstractElement<T>
/*     */   implements IAccessibleElement, IBlockElement
/*     */ {
/*     */   public <T1> T1 getDefaultProperty(int property) {
/*  70 */     switch (property) {
/*     */       case 102:
/*     */       case 103:
/*     */       case 104:
/*  74 */         return (T1)OverflowPropertyValue.FIT;
/*     */     } 
/*  76 */     return (T1)super.getDefaultProperty(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginLeft() {
/*  86 */     return (UnitValue)getProperty(44);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMarginLeft(float value) {
/*  96 */     UnitValue marginUV = UnitValue.createPointValue(value);
/*  97 */     setProperty(44, marginUV);
/*  98 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginRight() {
/* 107 */     return (UnitValue)getProperty(45);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMarginRight(float value) {
/* 117 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 118 */     setProperty(45, marginUV);
/* 119 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginTop() {
/* 128 */     return (UnitValue)getProperty(46);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMarginTop(float value) {
/* 138 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 139 */     setProperty(46, marginUV);
/* 140 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginBottom() {
/* 149 */     return (UnitValue)getProperty(43);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMarginBottom(float value) {
/* 159 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 160 */     setProperty(43, marginUV);
/* 161 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMargin(float commonMargin) {
/* 171 */     return setMargins(commonMargin, commonMargin, commonMargin, commonMargin);
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
/*     */   public T setMargins(float marginTop, float marginRight, float marginBottom, float marginLeft) {
/* 184 */     setMarginTop(marginTop);
/* 185 */     setMarginRight(marginRight);
/* 186 */     setMarginBottom(marginBottom);
/* 187 */     setMarginLeft(marginLeft);
/* 188 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingLeft() {
/* 197 */     return (UnitValue)getProperty(48);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setPaddingLeft(float value) {
/* 207 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 208 */     setProperty(48, paddingUV);
/* 209 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingRight() {
/* 218 */     return (UnitValue)getProperty(49);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setPaddingRight(float value) {
/* 228 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 229 */     setProperty(49, paddingUV);
/* 230 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingTop() {
/* 239 */     return (UnitValue)getProperty(50);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setPaddingTop(float value) {
/* 249 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 250 */     setProperty(50, paddingUV);
/* 251 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingBottom() {
/* 260 */     return (UnitValue)getProperty(47);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setPaddingBottom(float value) {
/* 270 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 271 */     setProperty(47, paddingUV);
/* 272 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setPadding(float commonPadding) {
/* 282 */     return setPaddings(commonPadding, commonPadding, commonPadding, commonPadding);
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
/*     */   public T setPaddings(float paddingTop, float paddingRight, float paddingBottom, float paddingLeft) {
/* 295 */     setPaddingTop(paddingTop);
/* 296 */     setPaddingRight(paddingRight);
/* 297 */     setPaddingBottom(paddingBottom);
/* 298 */     setPaddingLeft(paddingLeft);
/* 299 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setVerticalAlignment(VerticalAlignment verticalAlignment) {
/* 309 */     setProperty(75, verticalAlignment);
/* 310 */     return (T)this;
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
/*     */   public T setSpacingRatio(float ratio) {
/* 326 */     setProperty(61, Float.valueOf(ratio));
/* 327 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isKeepTogether() {
/* 337 */     return (Boolean)getProperty(32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setKeepTogether(boolean keepTogether) {
/* 348 */     setProperty(32, Boolean.valueOf(keepTogether));
/* 349 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isKeepWithNext() {
/* 359 */     return (Boolean)getProperty(81);
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
/*     */   public T setKeepWithNext(boolean keepWithNext) {
/* 371 */     setProperty(81, Boolean.valueOf(keepWithNext));
/* 372 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setRotationAngle(float angleInRadians) {
/* 382 */     setProperty(55, Float.valueOf(angleInRadians));
/* 383 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setRotationAngle(double angleInRadians) {
/* 393 */     setProperty(55, Float.valueOf((float)angleInRadians));
/* 394 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setWidth(float width) {
/* 404 */     setProperty(77, UnitValue.createPointValue(width));
/* 405 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setWidth(UnitValue width) {
/* 415 */     setProperty(77, width);
/* 416 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getWidth() {
/* 426 */     return (UnitValue)getProperty(77);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setHeight(UnitValue height) {
/* 436 */     setProperty(27, height);
/* 437 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setHeight(float height) {
/* 447 */     UnitValue heightAsUV = UnitValue.createPointValue(height);
/* 448 */     setProperty(27, heightAsUV);
/* 449 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getHeight() {
/* 458 */     return (UnitValue)getProperty(27);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMaxHeight(float maxHeight) {
/* 468 */     UnitValue maxHeightAsUV = UnitValue.createPointValue(maxHeight);
/* 469 */     setProperty(84, maxHeightAsUV);
/* 470 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMaxHeight(UnitValue maxHeight) {
/* 480 */     setProperty(84, maxHeight);
/* 481 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMinHeight(UnitValue minHeight) {
/* 491 */     setProperty(85, minHeight);
/* 492 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMinHeight(float minHeight) {
/* 502 */     UnitValue minHeightAsUV = UnitValue.createPointValue(minHeight);
/* 503 */     setProperty(85, minHeightAsUV);
/* 504 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMaxWidth(UnitValue maxWidth) {
/* 514 */     setProperty(79, maxWidth);
/* 515 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMaxWidth(float maxWidth) {
/* 525 */     setProperty(79, UnitValue.createPointValue(maxWidth));
/* 526 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMinWidth(UnitValue minWidth) {
/* 536 */     setProperty(80, minWidth);
/* 537 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setMinWidth(float minWidth) {
/* 547 */     setProperty(80, UnitValue.createPointValue(minWidth));
/* 548 */     return (T)this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/BlockElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */