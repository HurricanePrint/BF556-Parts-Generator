/*     */ package com.itextpdf.layout;
/*     */ 
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.property.VerticalAlignment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Style
/*     */   extends ElementPropertyContainer<Style>
/*     */ {
/*     */   public UnitValue getMarginLeft() {
/*  69 */     return (UnitValue)getProperty(44);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMarginLeft(float value) {
/*  79 */     UnitValue marginUV = UnitValue.createPointValue(value);
/*  80 */     setProperty(44, marginUV);
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginRight() {
/*  90 */     return (UnitValue)getProperty(45);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMarginRight(float value) {
/* 100 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 101 */     setProperty(45, marginUV);
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginTop() {
/* 111 */     return (UnitValue)getProperty(46);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMarginTop(float value) {
/* 121 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 122 */     setProperty(46, marginUV);
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getMarginBottom() {
/* 132 */     return (UnitValue)getProperty(43);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMarginBottom(float value) {
/* 142 */     UnitValue marginUV = UnitValue.createPointValue(value);
/* 143 */     setProperty(43, marginUV);
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMargin(float commonMargin) {
/* 154 */     return setMargins(commonMargin, commonMargin, commonMargin, commonMargin);
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
/*     */   public Style setMargins(float marginTop, float marginRight, float marginBottom, float marginLeft) {
/* 167 */     setMarginTop(marginTop);
/* 168 */     setMarginRight(marginRight);
/* 169 */     setMarginBottom(marginBottom);
/* 170 */     setMarginLeft(marginLeft);
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingLeft() {
/* 180 */     return (UnitValue)getProperty(48);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setPaddingLeft(float value) {
/* 190 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 191 */     setProperty(48, paddingUV);
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingRight() {
/* 201 */     return (UnitValue)getProperty(49);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setPaddingRight(float value) {
/* 211 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 212 */     setProperty(49, paddingUV);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingTop() {
/* 222 */     return (UnitValue)getProperty(50);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setPaddingTop(float value) {
/* 232 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 233 */     setProperty(50, paddingUV);
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getPaddingBottom() {
/* 243 */     return (UnitValue)getProperty(47);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setPaddingBottom(float value) {
/* 253 */     UnitValue paddingUV = UnitValue.createPointValue(value);
/* 254 */     setProperty(47, paddingUV);
/* 255 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setPadding(float commonPadding) {
/* 265 */     return setPaddings(commonPadding, commonPadding, commonPadding, commonPadding);
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
/*     */   public Style setPaddings(float paddingTop, float paddingRight, float paddingBottom, float paddingLeft) {
/* 278 */     setPaddingTop(paddingTop);
/* 279 */     setPaddingRight(paddingRight);
/* 280 */     setPaddingBottom(paddingBottom);
/* 281 */     setPaddingLeft(paddingLeft);
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setVerticalAlignment(VerticalAlignment verticalAlignment) {
/* 292 */     setProperty(75, verticalAlignment);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setSpacingRatio(float ratio) {
/* 309 */     setProperty(61, Float.valueOf(ratio));
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isKeepTogether() {
/* 320 */     return (Boolean)getProperty(32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setKeepTogether(boolean keepTogether) {
/* 331 */     setProperty(32, Boolean.valueOf(keepTogether));
/* 332 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setRotationAngle(float radAngle) {
/* 342 */     setProperty(55, Float.valueOf(radAngle));
/* 343 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setRotationAngle(double angle) {
/* 353 */     setProperty(55, Float.valueOf((float)angle));
/* 354 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setWidth(float width) {
/* 364 */     setProperty(77, UnitValue.createPointValue(width));
/* 365 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setWidth(UnitValue width) {
/* 375 */     setProperty(77, width);
/* 376 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getWidth() {
/* 386 */     return (UnitValue)getProperty(77);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setHeight(UnitValue height) {
/* 396 */     setProperty(27, height);
/* 397 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setHeight(float height) {
/* 407 */     UnitValue heightAsUV = UnitValue.createPointValue(height);
/* 408 */     setProperty(27, heightAsUV);
/* 409 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getHeight() {
/* 418 */     return (UnitValue)getProperty(27);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMaxHeight(float maxHeight) {
/* 428 */     UnitValue maxHeightAsUV = UnitValue.createPointValue(maxHeight);
/* 429 */     setProperty(84, maxHeightAsUV);
/* 430 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMaxHeight(UnitValue maxHeight) {
/* 440 */     setProperty(84, maxHeight);
/* 441 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMinHeight(UnitValue minHeight) {
/* 451 */     setProperty(85, minHeight);
/* 452 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMinHeight(float minHeight) {
/* 462 */     UnitValue minHeightAsUV = UnitValue.createPointValue(minHeight);
/* 463 */     setProperty(85, minHeightAsUV);
/* 464 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMaxWidth(UnitValue maxWidth) {
/* 474 */     setProperty(79, maxWidth);
/* 475 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMaxWidth(float maxWidth) {
/* 485 */     setProperty(79, UnitValue.createPointValue(maxWidth));
/* 486 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMinWidth(UnitValue minWidth) {
/* 496 */     setProperty(80, minWidth);
/* 497 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setMinWidth(float minWidth) {
/* 507 */     setProperty(80, UnitValue.createPointValue(minWidth));
/* 508 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/Style.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */