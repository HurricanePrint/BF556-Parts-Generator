/*     */ package com.itextpdf.layout.minmaxwidth;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.layout.LayoutContext;
/*     */ import com.itextpdf.layout.layout.LayoutResult;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
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
/*     */ public final class MinMaxWidthUtils
/*     */ {
/*     */   private static final float eps = 0.01F;
/*     */   private static final float max = 32760.0F;
/*     */   
/*     */   public static float getEps() {
/*  65 */     return 0.01F;
/*     */   }
/*     */   
/*     */   public static float getInfWidth() {
/*  69 */     return 32760.0F;
/*     */   }
/*     */   private static float getInfHeight() {
/*  72 */     return 1000000.0F;
/*     */   }
/*     */   public static boolean isEqual(double x, double y) {
/*  75 */     return (Math.abs(x - y) < 0.009999999776482582D);
/*     */   }
/*     */   
/*     */   public static MinMaxWidth countDefaultMinMaxWidth(IRenderer renderer) {
/*  79 */     LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(getInfWidth(), getInfHeight()))));
/*  80 */     return (result.getStatus() == 3) ? new MinMaxWidth() : new MinMaxWidth(0.0F, result
/*  81 */         .getOccupiedArea().getBBox().getWidth(), 0.0F);
/*     */   }
/*     */   
/*     */   public static float getBorderWidth(IPropertyContainer element) {
/*  85 */     Border border = (Border)element.getProperty(9);
/*  86 */     Border rightBorder = (Border)element.getProperty(12);
/*  87 */     Border leftBorder = (Border)element.getProperty(11);
/*     */     
/*  89 */     if (!element.hasOwnProperty(12)) {
/*  90 */       rightBorder = border;
/*     */     }
/*  92 */     if (!element.hasOwnProperty(11)) {
/*  93 */       leftBorder = border;
/*     */     }
/*     */     
/*  96 */     float rightBorderWidth = (rightBorder != null) ? rightBorder.getWidth() : 0.0F;
/*  97 */     float leftBorderWidth = (leftBorder != null) ? leftBorder.getWidth() : 0.0F;
/*  98 */     return rightBorderWidth + leftBorderWidth;
/*     */   }
/*     */   
/*     */   public static float getMarginsWidth(IPropertyContainer element) {
/* 102 */     UnitValue rightMargin = (UnitValue)element.getProperty(45);
/* 103 */     if (null != rightMargin && !rightMargin.isPointValue()) {
/* 104 */       Logger logger = LoggerFactory.getLogger(MinMaxWidthUtils.class);
/* 105 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(45) }));
/*     */     } 
/* 107 */     UnitValue leftMargin = (UnitValue)element.getProperty(44);
/* 108 */     if (null != leftMargin && !leftMargin.isPointValue()) {
/* 109 */       Logger logger = LoggerFactory.getLogger(MinMaxWidthUtils.class);
/* 110 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*     */     } 
/*     */     
/* 113 */     float rightMarginWidth = (rightMargin != null) ? rightMargin.getValue() : 0.0F;
/* 114 */     float leftMarginWidth = (leftMargin != null) ? leftMargin.getValue() : 0.0F;
/*     */     
/* 116 */     return rightMarginWidth + leftMarginWidth;
/*     */   }
/*     */   
/*     */   public static float getPaddingWidth(IPropertyContainer element) {
/* 120 */     UnitValue rightPadding = (UnitValue)element.getProperty(49);
/* 121 */     if (null != rightPadding && !rightPadding.isPointValue()) {
/* 122 */       Logger logger = LoggerFactory.getLogger(MinMaxWidthUtils.class);
/* 123 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(49) }));
/*     */     } 
/* 125 */     UnitValue leftPadding = (UnitValue)element.getProperty(48);
/* 126 */     if (null != leftPadding && !leftPadding.isPointValue()) {
/* 127 */       Logger logger = LoggerFactory.getLogger(MinMaxWidthUtils.class);
/* 128 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(48) }));
/*     */     } 
/*     */     
/* 131 */     float rightPaddingWidth = (rightPadding != null) ? rightPadding.getValue() : 0.0F;
/* 132 */     float leftPaddingWidth = (leftPadding != null) ? leftPadding.getValue() : 0.0F;
/*     */     
/* 134 */     return rightPaddingWidth + leftPaddingWidth;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/minmaxwidth/MinMaxWidthUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */