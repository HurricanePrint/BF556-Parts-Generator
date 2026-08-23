/*     */ package com.itextpdf.layout;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.hyphenation.HyphenationConfig;
/*     */ import com.itextpdf.layout.property.Background;
/*     */ import com.itextpdf.layout.property.BackgroundImage;
/*     */ import com.itextpdf.layout.property.BaseDirection;
/*     */ import com.itextpdf.layout.property.BorderRadius;
/*     */ import com.itextpdf.layout.property.FontKerning;
/*     */ import com.itextpdf.layout.property.HorizontalAlignment;
/*     */ import com.itextpdf.layout.property.TextAlignment;
/*     */ import com.itextpdf.layout.property.TransparentColor;
/*     */ import com.itextpdf.layout.property.Underline;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.splitting.ISplitCharacters;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public abstract class ElementPropertyContainer<T extends IPropertyContainer>
/*     */   implements IPropertyContainer
/*     */ {
/*  79 */   protected Map<Integer, Object> properties = new HashMap<>();
/*     */ 
/*     */   
/*     */   public void setProperty(int property, Object value) {
/*  83 */     this.properties.put(Integer.valueOf(property), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProperty(int property) {
/*  88 */     return hasOwnProperty(property);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasOwnProperty(int property) {
/*  93 */     return this.properties.containsKey(Integer.valueOf(property));
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteOwnProperty(int property) {
/*  98 */     this.properties.remove(Integer.valueOf(property));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getProperty(int property) {
/* 103 */     return getOwnProperty(property);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getOwnProperty(int property) {
/* 108 */     return (T1)this.properties.get(Integer.valueOf(property));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1 getDefaultProperty(int property) {
/* 113 */     switch (property) {
/*     */       case 43:
/*     */       case 44:
/*     */       case 45:
/*     */       case 46:
/*     */       case 47:
/*     */       case 48:
/*     */       case 49:
/*     */       case 50:
/* 122 */         return (T1)UnitValue.createPointValue(0.0F);
/*     */     } 
/* 124 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setRelativePosition(float left, float top, float right, float bottom) {
/* 146 */     setProperty(52, Integer.valueOf(2));
/* 147 */     setProperty(34, Float.valueOf(left));
/* 148 */     setProperty(54, Float.valueOf(right));
/* 149 */     setProperty(73, Float.valueOf(top));
/* 150 */     setProperty(14, Float.valueOf(bottom));
/* 151 */     return (T)this;
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
/*     */   public T setFixedPosition(float left, float bottom, float width) {
/* 167 */     setFixedPosition(left, bottom, UnitValue.createPointValue(width));
/* 168 */     return (T)this;
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
/*     */   public T setFixedPosition(float left, float bottom, UnitValue width) {
/* 184 */     setProperty(52, Integer.valueOf(4));
/* 185 */     setProperty(34, Float.valueOf(left));
/* 186 */     setProperty(14, Float.valueOf(bottom));
/* 187 */     setProperty(77, width);
/* 188 */     return (T)this;
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
/*     */   public T setFixedPosition(int pageNumber, float left, float bottom, float width) {
/* 205 */     setFixedPosition(left, bottom, width);
/* 206 */     setProperty(51, Integer.valueOf(pageNumber));
/* 207 */     return (T)this;
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
/*     */   public T setFixedPosition(int pageNumber, float left, float bottom, UnitValue width) {
/* 224 */     setFixedPosition(left, bottom, width);
/* 225 */     setProperty(51, Integer.valueOf(pageNumber));
/* 226 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
/* 236 */     setProperty(28, horizontalAlignment);
/* 237 */     return (T)this;
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
/*     */   public T setFont(PdfFont font) {
/* 249 */     setProperty(20, font);
/* 250 */     return (T)this;
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
/*     */ 
/*     */   
/*     */   public T setFontFamily(String... fontFamilyNames) {
/* 270 */     setProperty(20, fontFamilyNames);
/* 271 */     return (T)this;
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
/*     */ 
/*     */   
/*     */   public T setFontFamily(List<String> fontFamilyNames) {
/* 291 */     return setFontFamily(fontFamilyNames.<String>toArray(new String[fontFamilyNames.size()]));
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
/*     */   public T setFont(String font) {
/* 305 */     setProperty(20, font);
/* 306 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setFontColor(Color fontColor) {
/* 316 */     return setFontColor(fontColor, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setFontColor(Color fontColor, float opacity) {
/* 327 */     setProperty(21, (fontColor != null) ? new TransparentColor(fontColor, opacity) : null);
/* 328 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setFontSize(float fontSize) {
/* 338 */     UnitValue fontSizeAsUV = UnitValue.createPointValue(fontSize);
/* 339 */     setProperty(24, fontSizeAsUV);
/* 340 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setTextAlignment(TextAlignment alignment) {
/* 350 */     setProperty(70, alignment);
/* 351 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setCharacterSpacing(float charSpacing) {
/* 362 */     setProperty(15, Float.valueOf(charSpacing));
/* 363 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setWordSpacing(float wordSpacing) {
/* 374 */     setProperty(78, Float.valueOf(wordSpacing));
/* 375 */     return (T)this;
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
/*     */   public T setFontKerning(FontKerning fontKerning) {
/* 387 */     setProperty(22, fontKerning);
/* 388 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBackgroundColor(Color backgroundColor) {
/* 398 */     return setBackgroundColor(backgroundColor, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBackgroundColor(Color backgroundColor, float opacity) {
/* 409 */     return setBackgroundColor(backgroundColor, opacity, 0.0F, 0.0F, 0.0F, 0.0F);
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
/*     */   public T setBackgroundColor(Color backgroundColor, float extraLeft, float extraTop, float extraRight, float extraBottom) {
/* 424 */     return setBackgroundColor(backgroundColor, 1.0F, extraLeft, extraTop, extraRight, extraBottom);
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
/*     */   public T setBackgroundColor(Color backgroundColor, float opacity, float extraLeft, float extraTop, float extraRight, float extraBottom) {
/* 440 */     setProperty(6, (backgroundColor != null) ? new Background(backgroundColor, opacity, extraLeft, extraTop, extraRight, extraBottom) : null);
/* 441 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBackgroundImage(BackgroundImage image) {
/* 451 */     setProperty(90, image);
/* 452 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBackgroundImage(List<BackgroundImage> imagesList) {
/* 462 */     setProperty(90, imagesList);
/* 463 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorder(Border border) {
/* 473 */     setProperty(9, border);
/* 474 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderTop(Border border) {
/* 484 */     setProperty(13, border);
/* 485 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderRight(Border border) {
/* 495 */     setProperty(12, border);
/* 496 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderBottom(Border border) {
/* 506 */     setProperty(10, border);
/* 507 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderLeft(Border border) {
/* 517 */     setProperty(11, border);
/* 518 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderRadius(BorderRadius borderRadius) {
/* 528 */     setProperty(101, borderRadius);
/* 529 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderBottomLeftRadius(BorderRadius borderRadius) {
/* 539 */     setProperty(113, borderRadius);
/* 540 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderBottomRightRadius(BorderRadius borderRadius) {
/* 550 */     setProperty(112, borderRadius);
/* 551 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderTopLeftRadius(BorderRadius borderRadius) {
/* 561 */     setProperty(110, borderRadius);
/* 562 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBorderTopRightRadius(BorderRadius borderRadius) {
/* 572 */     setProperty(111, borderRadius);
/* 573 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setSplitCharacters(ISplitCharacters splitCharacters) {
/* 584 */     setProperty(62, splitCharacters);
/* 585 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISplitCharacters getSplitCharacters() {
/* 594 */     return getProperty(62);
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
/*     */   public Integer getTextRenderingMode() {
/* 606 */     return getProperty(71);
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
/*     */   public T setTextRenderingMode(int textRenderingMode) {
/* 619 */     setProperty(71, Integer.valueOf(textRenderingMode));
/* 620 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getStrokeColor() {
/* 630 */     return getProperty(63);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setStrokeColor(Color strokeColor) {
/* 641 */     setProperty(63, strokeColor);
/* 642 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getStrokeWidth() {
/* 652 */     return getProperty(64);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setStrokeWidth(float strokeWidth) {
/* 663 */     setProperty(64, Float.valueOf(strokeWidth));
/* 664 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setBold() {
/* 674 */     setProperty(8, Boolean.valueOf(true));
/* 675 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setItalic() {
/* 685 */     setProperty(31, Boolean.valueOf(true));
/* 686 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setLineThrough() {
/* 697 */     return setUnderline(null, 0.75F, 0.0F, 0.0F, 0.29166666F, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setUnderline() {
/* 707 */     return setUnderline(null, 0.75F, 0.0F, 0.0F, -0.125F, 0);
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
/*     */   public T setUnderline(float thickness, float yPosition) {
/* 720 */     return setUnderline(null, thickness, 0.0F, yPosition, 0.0F, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setUnderline(Color color, float thickness, float thicknessMul, float yPosition, float yPositionMul, int lineCapStyle) {
/* 742 */     return setUnderline(color, 1.0F, thickness, thicknessMul, yPosition, yPositionMul, lineCapStyle);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setUnderline(Color color, float opacity, float thickness, float thicknessMul, float yPosition, float yPositionMul, int lineCapStyle) {
/* 765 */     Underline newUnderline = new Underline(color, opacity, thickness, thicknessMul, yPosition, yPositionMul, lineCapStyle);
/* 766 */     Object currentProperty = getProperty(74);
/* 767 */     if (currentProperty instanceof List) {
/* 768 */       ((List<Underline>)currentProperty).add(newUnderline);
/* 769 */     } else if (currentProperty instanceof Underline) {
/* 770 */       List<Underline> mergedUnderlines = new ArrayList<>();
/* 771 */       mergedUnderlines.add((Underline)currentProperty);
/* 772 */       mergedUnderlines.add(newUnderline);
/* 773 */       setProperty(74, mergedUnderlines);
/*     */     } else {
/* 775 */       setProperty(74, newUnderline);
/*     */     } 
/* 777 */     return (T)this;
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
/*     */   public T setBaseDirection(BaseDirection baseDirection) {
/* 789 */     setProperty(7, baseDirection);
/* 790 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setHyphenation(HyphenationConfig hyphenationConfig) {
/* 801 */     setProperty(30, hyphenationConfig);
/* 802 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setFontScript(Character.UnicodeScript script) {
/* 812 */     setProperty(23, script);
/* 813 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setDestination(String destination) {
/* 823 */     setProperty(17, destination);
/* 824 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T setOpacity(Float opacity) {
/* 835 */     setProperty(92, opacity);
/* 836 */     return (T)this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/ElementPropertyContainer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */