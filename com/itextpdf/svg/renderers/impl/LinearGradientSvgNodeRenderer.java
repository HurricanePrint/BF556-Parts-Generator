/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.gradients.GradientColorStop;
/*     */ import com.itextpdf.kernel.colors.gradients.LinearGradientBuilder;
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinearGradientSvgNodeRenderer
/*     */   extends AbstractGradientSvgNodeRenderer
/*     */   implements INoDrawSvgNodeRenderer
/*     */ {
/*     */   public Color createColor(SvgDrawContext context, Rectangle objectBoundingBox, float objectBoundingBoxMargin, float parentOpacity) {
/*  50 */     if (objectBoundingBox == null) {
/*  51 */       return null;
/*     */     }
/*     */     
/*  54 */     LinearGradientBuilder builder = new LinearGradientBuilder();
/*     */     
/*  56 */     for (GradientColorStop stopColor : parseStops(parentOpacity)) {
/*  57 */       builder.addColorStop(stopColor);
/*     */     }
/*  59 */     builder.setSpreadMethod(parseSpreadMethod());
/*     */     
/*  61 */     boolean isObjectBoundingBox = isObjectBoundingBoxUnits();
/*     */     
/*  63 */     Point[] coordinates = getCoordinates(context, isObjectBoundingBox);
/*     */     
/*  65 */     builder.setGradientVector(coordinates[0].getX(), coordinates[0].getY(), coordinates[1]
/*  66 */         .getX(), coordinates[1].getY());
/*     */     
/*  68 */     AffineTransform gradientTransform = getGradientTransformToUserSpaceOnUse(objectBoundingBox, isObjectBoundingBox);
/*     */ 
/*     */     
/*  71 */     builder.setCurrentSpaceToGradientVectorSpaceTransformation(gradientTransform);
/*     */     
/*  73 */     return builder.buildColor(objectBoundingBox
/*  74 */         .applyMargins(objectBoundingBoxMargin, objectBoundingBoxMargin, objectBoundingBoxMargin, objectBoundingBoxMargin, true), context
/*  75 */         .getCurrentCanvasTransform(), context.getCurrentCanvas().getDocument());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/*  81 */     LinearGradientSvgNodeRenderer copy = new LinearGradientSvgNodeRenderer();
/*  82 */     deepCopyAttributesAndStyles(copy);
/*  83 */     deepCopyChildren(copy);
/*  84 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<GradientColorStop> parseStops(float parentOpacity) {
/*  90 */     List<GradientColorStop> stopsList = new ArrayList<>();
/*  91 */     for (StopSvgNodeRenderer stopRenderer : getChildStopRenderers()) {
/*  92 */       float[] stopColor = stopRenderer.getStopColor();
/*  93 */       double offset = stopRenderer.getOffset();
/*  94 */       stopsList.add(new GradientColorStop(stopColor, offset, GradientColorStop.OffsetType.RELATIVE));
/*     */     } 
/*     */     
/*  97 */     if (!stopsList.isEmpty()) {
/*  98 */       GradientColorStop firstStop = stopsList.get(0);
/*  99 */       if (firstStop.getOffset() > 0.0D) {
/* 100 */         stopsList.add(0, new GradientColorStop(firstStop, 0.0D, GradientColorStop.OffsetType.RELATIVE));
/*     */       }
/*     */       
/* 103 */       GradientColorStop lastStop = stopsList.get(stopsList.size() - 1);
/* 104 */       if (lastStop.getOffset() < 1.0D) {
/* 105 */         stopsList.add(new GradientColorStop(lastStop, 1.0D, GradientColorStop.OffsetType.RELATIVE));
/*     */       }
/*     */     } 
/* 108 */     return stopsList;
/*     */   }
/*     */ 
/*     */   
/*     */   private AffineTransform getGradientTransformToUserSpaceOnUse(Rectangle objectBoundingBox, boolean isObjectBoundingBox) {
/* 113 */     AffineTransform gradientTransform = new AffineTransform();
/* 114 */     if (isObjectBoundingBox) {
/* 115 */       gradientTransform.translate(objectBoundingBox.getX(), objectBoundingBox.getY());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       gradientTransform.scale(objectBoundingBox.getWidth() / 0.75D, objectBoundingBox.getHeight() / 0.75D);
/*     */     } 
/*     */     
/* 127 */     AffineTransform svgGradientTransformation = getGradientTransform();
/* 128 */     if (svgGradientTransformation != null) {
/* 129 */       gradientTransform.concatenate(svgGradientTransformation);
/*     */     }
/* 131 */     return gradientTransform;
/*     */   }
/*     */   
/*     */   private Point[] getCoordinates(SvgDrawContext context, boolean isObjectBoundingBox) {
/*     */     Point start;
/*     */     Point end;
/* 137 */     if (isObjectBoundingBox) {
/*     */       
/* 139 */       start = new Point(getCoordinateForObjectBoundingBox("x1", 0.0D), getCoordinateForObjectBoundingBox("y1", 0.0D));
/*     */       
/* 141 */       end = new Point(getCoordinateForObjectBoundingBox("x2", 1.0D), getCoordinateForObjectBoundingBox("y2", 0.0D));
/*     */     } else {
/* 143 */       Rectangle currentViewPort = context.getCurrentViewPort();
/* 144 */       double x = currentViewPort.getX();
/* 145 */       double y = currentViewPort.getY();
/* 146 */       double width = currentViewPort.getWidth();
/* 147 */       double height = currentViewPort.getHeight();
/* 148 */       float em = getCurrentFontSize();
/* 149 */       float rem = context.getRemValue();
/*     */       
/* 151 */       start = new Point(getCoordinateForUserSpaceOnUse("x1", x, x, width, em, rem), getCoordinateForUserSpaceOnUse("y1", y, y, height, em, rem));
/*     */       
/* 153 */       end = new Point(getCoordinateForUserSpaceOnUse("x2", x + width, x, width, em, rem), getCoordinateForUserSpaceOnUse("y2", y, y, height, em, rem));
/*     */     } 
/*     */     
/* 156 */     return new Point[] { start, end };
/*     */   }
/*     */   
/*     */   private double getCoordinateForObjectBoundingBox(String attributeName, double defaultValue) {
/* 160 */     String attributeValue = getAttribute(attributeName);
/* 161 */     double absoluteValue = defaultValue;
/* 162 */     if (CssUtils.isPercentageValue(attributeValue)) {
/* 163 */       absoluteValue = CssUtils.parseRelativeValue(attributeValue, 1.0F);
/* 164 */     } else if (CssUtils.isNumericValue(attributeValue) || 
/* 165 */       CssUtils.isMetricValue(attributeValue) || 
/* 166 */       CssUtils.isRelativeValue(attributeValue)) {
/*     */       
/* 168 */       int unitsPosition = CssUtils.determinePositionBetweenValueAndUnit(attributeValue);
/* 169 */       if (unitsPosition > 0)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 180 */         absoluteValue = CssUtils.parseDouble(attributeValue.substring(0, unitsPosition)).doubleValue();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     return absoluteValue * 0.75D;
/*     */   }
/*     */   
/*     */   private double getCoordinateForUserSpaceOnUse(String attributeName, double defaultValue, double start, double length, float em, float rem) {
/*     */     double absoluteValue;
/* 192 */     String attributeValue = getAttribute(attributeName);
/*     */     
/* 194 */     UnitValue unitValue = CssUtils.parseLengthValueToPt(attributeValue, em, rem);
/* 195 */     if (unitValue == null) {
/* 196 */       absoluteValue = defaultValue;
/* 197 */     } else if (unitValue.getUnitType() == 2) {
/* 198 */       absoluteValue = start + length * unitValue.getValue() / 100.0D;
/*     */     } else {
/* 200 */       absoluteValue = unitValue.getValue();
/*     */     } 
/* 202 */     return absoluteValue;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/LinearGradientSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */