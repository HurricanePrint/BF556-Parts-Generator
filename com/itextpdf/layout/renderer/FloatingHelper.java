/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.colors.ColorConstants;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.borders.SolidBorder;
/*     */ import com.itextpdf.layout.layout.LayoutArea;
/*     */ import com.itextpdf.layout.margincollapse.MarginsCollapseHandler;
/*     */ import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
/*     */ import com.itextpdf.layout.property.ClearPropertyValue;
/*     */ import com.itextpdf.layout.property.FloatPropertyValue;
/*     */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ class FloatingHelper
/*     */ {
/*     */   static void adjustLineAreaAccordingToFloats(List<Rectangle> floatRendererAreas, Rectangle layoutBox) {
/*  71 */     adjustLayoutBoxAccordingToFloats(floatRendererAreas, layoutBox, null, 0.0F, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static float adjustLayoutBoxAccordingToFloats(List<Rectangle> floatRendererAreas, Rectangle layoutBox, Float boxWidth, float clearHeightCorrection, MarginsCollapseHandler marginsCollapseHandler) {
/*  77 */     float left, right, topShift = clearHeightCorrection;
/*     */ 
/*     */     
/*  80 */     Rectangle[] lastLeftAndRightBoxes = null;
/*     */     do {
/*  82 */       if (lastLeftAndRightBoxes != null) {
/*  83 */         float bottomLeft = (lastLeftAndRightBoxes[0] != null) ? lastLeftAndRightBoxes[0].getBottom() : Float.MAX_VALUE;
/*  84 */         float bottomRight = (lastLeftAndRightBoxes[1] != null) ? lastLeftAndRightBoxes[1].getBottom() : Float.MAX_VALUE;
/*  85 */         float updatedHeight = Math.min(bottomLeft, bottomRight) - layoutBox.getY();
/*  86 */         topShift = layoutBox.getHeight() - updatedHeight;
/*     */       } 
/*  88 */       List<Rectangle> boxesAtYLevel = getBoxesAtYLevel(floatRendererAreas, layoutBox.getTop() - topShift);
/*  89 */       if (boxesAtYLevel.isEmpty()) {
/*  90 */         applyClearance(layoutBox, marginsCollapseHandler, topShift, false);
/*  91 */         return topShift;
/*     */       } 
/*     */       
/*  94 */       lastLeftAndRightBoxes = findLastLeftAndRightBoxes(layoutBox, boxesAtYLevel);
/*  95 */       left = (lastLeftAndRightBoxes[0] != null) ? lastLeftAndRightBoxes[0].getRight() : Float.MIN_VALUE;
/*  96 */       right = (lastLeftAndRightBoxes[1] != null) ? lastLeftAndRightBoxes[1].getLeft() : Float.MAX_VALUE;
/*     */       
/*  98 */       if (left > right || left > layoutBox.getRight() || right < layoutBox.getLeft()) {
/*  99 */         left = layoutBox.getLeft();
/* 100 */         right = left;
/*     */       } else {
/* 102 */         if (right > layoutBox.getRight()) {
/* 103 */           right = layoutBox.getRight();
/*     */         }
/* 105 */         if (left < layoutBox.getLeft()) {
/* 106 */           left = layoutBox.getLeft();
/*     */         }
/*     */       } 
/* 109 */     } while (boxWidth != null && boxWidth.floatValue() > right - left);
/*     */     
/* 111 */     if (layoutBox.getWidth() > right - left) {
/* 112 */       layoutBox.setX(left).setWidth(right - left);
/*     */     }
/*     */     
/* 115 */     applyClearance(layoutBox, marginsCollapseHandler, topShift, false);
/* 116 */     return topShift;
/*     */   }
/*     */   
/*     */   static Float calculateLineShiftUnderFloats(List<Rectangle> floatRendererAreas, Rectangle layoutBox) {
/* 120 */     List<Rectangle> boxesAtYLevel = getBoxesAtYLevel(floatRendererAreas, layoutBox.getTop());
/* 121 */     if (boxesAtYLevel.isEmpty()) {
/* 122 */       return null;
/*     */     }
/*     */     
/* 125 */     Rectangle[] lastLeftAndRightBoxes = findLastLeftAndRightBoxes(layoutBox, boxesAtYLevel);
/* 126 */     float left = (lastLeftAndRightBoxes[0] != null) ? lastLeftAndRightBoxes[0].getRight() : layoutBox.getLeft();
/* 127 */     float right = (lastLeftAndRightBoxes[1] != null) ? lastLeftAndRightBoxes[1].getLeft() : layoutBox.getRight();
/* 128 */     if (layoutBox.getLeft() < left || layoutBox.getRight() > right) {
/*     */       float maxLastFloatBottom;
/* 130 */       if (lastLeftAndRightBoxes[0] != null && lastLeftAndRightBoxes[1] != null) {
/* 131 */         maxLastFloatBottom = Math.max(lastLeftAndRightBoxes[0].getBottom(), lastLeftAndRightBoxes[1].getBottom());
/* 132 */       } else if (lastLeftAndRightBoxes[0] != null) {
/* 133 */         maxLastFloatBottom = lastLeftAndRightBoxes[0].getBottom();
/*     */       } else {
/* 135 */         maxLastFloatBottom = lastLeftAndRightBoxes[1].getBottom();
/*     */       } 
/*     */       
/* 138 */       return Float.valueOf(layoutBox.getTop() - maxLastFloatBottom + 1.0E-4F);
/*     */     } 
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   static void adjustFloatedTableLayoutBox(TableRenderer tableRenderer, Rectangle layoutBox, float tableWidth, List<Rectangle> floatRendererAreas, FloatPropertyValue floatPropertyValue) {
/* 144 */     tableRenderer.setProperty(28, null);
/* 145 */     UnitValue[] margins = tableRenderer.getMargins();
/* 146 */     if (!margins[1].isPointValue()) {
/* 147 */       Logger logger = LoggerFactory.getLogger(FloatingHelper.class);
/* 148 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(45) }));
/*     */     } 
/* 150 */     if (!margins[3].isPointValue()) {
/* 151 */       Logger logger = LoggerFactory.getLogger(FloatingHelper.class);
/* 152 */       logger.error(MessageFormatUtil.format("Property {0} in percents is not supported", new Object[] { Integer.valueOf(44) }));
/*     */     } 
/* 154 */     adjustBlockAreaAccordingToFloatRenderers(floatRendererAreas, layoutBox, tableWidth + margins[1].getValue() + margins[3].getValue(), FloatPropertyValue.LEFT.equals(floatPropertyValue));
/*     */   }
/*     */   static Float adjustFloatedBlockLayoutBox(AbstractRenderer renderer, Rectangle parentBBox, Float blockWidth, List<Rectangle> floatRendererAreas, FloatPropertyValue floatPropertyValue, OverflowPropertyValue overflowX) {
/*     */     float floatElemWidth;
/* 158 */     renderer.setProperty(28, null);
/*     */ 
/*     */     
/* 161 */     boolean overflowFit = AbstractRenderer.isOverflowFit(overflowX);
/* 162 */     if (blockWidth != null) {
/* 163 */       floatElemWidth = blockWidth.floatValue() + AbstractRenderer.calculateAdditionalWidth(renderer);
/* 164 */       if (overflowFit && floatElemWidth > parentBBox.getWidth()) {
/* 165 */         floatElemWidth = parentBBox.getWidth();
/*     */       }
/*     */     } else {
/* 168 */       MinMaxWidth minMaxWidth = calculateMinMaxWidthForFloat(renderer, floatPropertyValue);
/*     */       
/* 170 */       float maxWidth = minMaxWidth.getMaxWidth();
/* 171 */       if (maxWidth > parentBBox.getWidth()) {
/* 172 */         maxWidth = parentBBox.getWidth();
/*     */       }
/* 174 */       if (!overflowFit && minMaxWidth.getMinWidth() > parentBBox.getWidth()) {
/* 175 */         maxWidth = minMaxWidth.getMinWidth();
/*     */       }
/* 177 */       floatElemWidth = maxWidth + 1.0E-4F;
/* 178 */       blockWidth = Float.valueOf(maxWidth - minMaxWidth.getAdditionalWidth() + 1.0E-4F);
/*     */     } 
/*     */     
/* 181 */     adjustBlockAreaAccordingToFloatRenderers(floatRendererAreas, parentBBox, floatElemWidth, FloatPropertyValue.LEFT.equals(floatPropertyValue));
/* 182 */     return blockWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void adjustBlockAreaAccordingToFloatRenderers(List<Rectangle> floatRendererAreas, Rectangle layoutBox, float blockWidth, boolean isFloatLeft) {
/* 187 */     if (floatRendererAreas.isEmpty()) {
/* 188 */       if (!isFloatLeft) {
/* 189 */         adjustBoxForFloatRight(layoutBox, blockWidth);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 195 */     if (((Rectangle)floatRendererAreas.get(floatRendererAreas.size() - 1)).getTop() < layoutBox.getTop()) {
/* 196 */       float currY = ((Rectangle)floatRendererAreas.get(floatRendererAreas.size() - 1)).getTop();
/*     */     } else {
/*     */       
/* 199 */       float currY = layoutBox.getTop();
/*     */     } 
/* 201 */     Rectangle[] lastLeftAndRightBoxes = null;
/* 202 */     float left = 0.0F;
/* 203 */     float right = 0.0F;
/* 204 */     while (lastLeftAndRightBoxes == null || right - left < blockWidth) {
/* 205 */       float f; if (lastLeftAndRightBoxes != null) {
/* 206 */         if (isFloatLeft) {
/* 207 */           f = (lastLeftAndRightBoxes[0] != null) ? lastLeftAndRightBoxes[0].getBottom() : lastLeftAndRightBoxes[1].getBottom();
/*     */         } else {
/* 209 */           f = (lastLeftAndRightBoxes[1] != null) ? lastLeftAndRightBoxes[1].getBottom() : lastLeftAndRightBoxes[0].getBottom();
/*     */         } 
/*     */       }
/* 212 */       layoutBox.setHeight(f - layoutBox.getY());
/* 213 */       List<Rectangle> yLevelBoxes = getBoxesAtYLevel(floatRendererAreas, f);
/* 214 */       if (yLevelBoxes.isEmpty()) {
/* 215 */         if (!isFloatLeft) {
/* 216 */           adjustBoxForFloatRight(layoutBox, blockWidth);
/*     */         }
/*     */         return;
/*     */       } 
/* 220 */       lastLeftAndRightBoxes = findLastLeftAndRightBoxes(layoutBox, yLevelBoxes);
/* 221 */       left = (lastLeftAndRightBoxes[0] != null) ? lastLeftAndRightBoxes[0].getRight() : layoutBox.getLeft();
/* 222 */       right = (lastLeftAndRightBoxes[1] != null) ? lastLeftAndRightBoxes[1].getLeft() : layoutBox.getRight();
/*     */     } 
/*     */     
/* 225 */     layoutBox.setX(left);
/* 226 */     layoutBox.setWidth(right - left);
/*     */     
/* 228 */     if (!isFloatLeft) {
/* 229 */       adjustBoxForFloatRight(layoutBox, blockWidth);
/*     */     }
/*     */   }
/*     */   
/*     */   static void removeFloatsAboveRendererBottom(List<Rectangle> floatRendererAreas, IRenderer renderer) {
/* 234 */     if (!isRendererFloating(renderer)) {
/* 235 */       float bottom = renderer.getOccupiedArea().getBBox().getBottom();
/* 236 */       for (int i = floatRendererAreas.size() - 1; i >= 0; i--) {
/* 237 */         if (((Rectangle)floatRendererAreas.get(i)).getBottom() >= bottom) {
/* 238 */           floatRendererAreas.remove(i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static LayoutArea adjustResultOccupiedAreaForFloatAndClear(IRenderer renderer, List<Rectangle> floatRendererAreas, Rectangle parentBBox, float clearHeightCorrection, boolean marginsCollapsingEnabled) {
/* 246 */     LayoutArea occupiedArea = renderer.getOccupiedArea();
/* 247 */     LayoutArea editedArea = occupiedArea;
/* 248 */     if (isRendererFloating(renderer)) {
/* 249 */       editedArea = occupiedArea.clone();
/* 250 */       if (occupiedArea.getBBox().getWidth() > 0.0F) {
/* 251 */         floatRendererAreas.add(occupiedArea.getBBox());
/*     */       }
/* 253 */       editedArea.getBBox().setY(parentBBox.getTop());
/* 254 */       editedArea.getBBox().setHeight(0.0F);
/* 255 */     } else if (clearHeightCorrection > 0.0F && !marginsCollapsingEnabled) {
/* 256 */       editedArea = occupiedArea.clone();
/* 257 */       editedArea.getBBox().increaseHeight(clearHeightCorrection);
/*     */     } 
/*     */     
/* 260 */     return editedArea;
/*     */   }
/*     */   
/*     */   static void includeChildFloatsInOccupiedArea(List<Rectangle> floatRendererAreas, IRenderer renderer, Set<Rectangle> nonChildFloatingRendererAreas) {
/* 264 */     Rectangle commonRectangle = includeChildFloatsInOccupiedArea(floatRendererAreas, renderer.getOccupiedArea().getBBox(), nonChildFloatingRendererAreas);
/* 265 */     renderer.getOccupiedArea().setBBox(commonRectangle);
/*     */   }
/*     */   
/*     */   static Rectangle includeChildFloatsInOccupiedArea(List<Rectangle> floatRendererAreas, Rectangle occupiedAreaBbox, Set<Rectangle> nonChildFloatingRendererAreas) {
/* 269 */     for (Rectangle floatBox : floatRendererAreas) {
/* 270 */       if (nonChildFloatingRendererAreas.contains(floatBox)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 275 */       occupiedAreaBbox = Rectangle.getCommonRectangle(new Rectangle[] { occupiedAreaBbox, floatBox });
/*     */     } 
/* 277 */     return occupiedAreaBbox;
/*     */   }
/*     */   
/*     */   static MinMaxWidth calculateMinMaxWidthForFloat(AbstractRenderer renderer, FloatPropertyValue floatPropertyVal) {
/* 281 */     boolean floatPropIsRendererOwn = renderer.hasOwnProperty(99);
/* 282 */     renderer.setProperty(99, FloatPropertyValue.NONE);
/* 283 */     MinMaxWidth kidMinMaxWidth = renderer.getMinMaxWidth();
/* 284 */     if (floatPropIsRendererOwn) {
/* 285 */       renderer.setProperty(99, floatPropertyVal);
/*     */     } else {
/* 287 */       renderer.deleteOwnProperty(99);
/*     */     } 
/* 289 */     return kidMinMaxWidth;
/*     */   }
/*     */   static float calculateClearHeightCorrection(IRenderer renderer, List<Rectangle> floatRendererAreas, Rectangle parentBBox) {
/*     */     float currY;
/* 293 */     ClearPropertyValue clearPropertyValue = (ClearPropertyValue)renderer.getProperty(100);
/* 294 */     float clearHeightCorrection = 0.0F;
/* 295 */     if (clearPropertyValue == null || floatRendererAreas.isEmpty()) {
/* 296 */       return clearHeightCorrection;
/*     */     }
/*     */ 
/*     */     
/* 300 */     if (((Rectangle)floatRendererAreas.get(floatRendererAreas.size() - 1)).getTop() < parentBBox.getTop()) {
/* 301 */       currY = ((Rectangle)floatRendererAreas.get(floatRendererAreas.size() - 1)).getTop();
/*     */     } else {
/* 303 */       currY = parentBBox.getTop();
/*     */     } 
/*     */     
/* 306 */     List<Rectangle> boxesAtYLevel = getBoxesAtYLevel(floatRendererAreas, currY);
/* 307 */     Rectangle[] lastLeftAndRightBoxes = findLastLeftAndRightBoxes(parentBBox, boxesAtYLevel);
/* 308 */     float lowestFloatBottom = Float.MAX_VALUE;
/* 309 */     boolean isBoth = clearPropertyValue.equals(ClearPropertyValue.BOTH);
/* 310 */     if ((clearPropertyValue.equals(ClearPropertyValue.LEFT) || isBoth) && lastLeftAndRightBoxes[0] != null) {
/* 311 */       for (Rectangle floatBox : floatRendererAreas) {
/* 312 */         if (floatBox.getBottom() < lowestFloatBottom && floatBox.getLeft() <= lastLeftAndRightBoxes[0].getLeft()) {
/* 313 */           lowestFloatBottom = floatBox.getBottom();
/*     */         }
/*     */       } 
/*     */     }
/* 317 */     if ((clearPropertyValue.equals(ClearPropertyValue.RIGHT) || isBoth) && lastLeftAndRightBoxes[1] != null) {
/* 318 */       for (Rectangle floatBox : floatRendererAreas) {
/* 319 */         if (floatBox.getBottom() < lowestFloatBottom && floatBox.getRight() >= lastLeftAndRightBoxes[1].getRight()) {
/* 320 */           lowestFloatBottom = floatBox.getBottom();
/*     */         }
/*     */       } 
/*     */     }
/* 324 */     if (lowestFloatBottom < Float.MAX_VALUE) {
/* 325 */       clearHeightCorrection = parentBBox.getTop() - lowestFloatBottom + 1.0E-4F;
/*     */     }
/*     */     
/* 328 */     return clearHeightCorrection;
/*     */   }
/*     */   
/*     */   static void applyClearance(Rectangle layoutBox, MarginsCollapseHandler marginsCollapseHandler, float clearHeightAdjustment, boolean isFloat) {
/* 332 */     if (clearHeightAdjustment <= 0.0F) {
/*     */       return;
/*     */     }
/*     */     
/* 336 */     if (marginsCollapseHandler == null || isFloat) {
/* 337 */       layoutBox.decreaseHeight(clearHeightAdjustment);
/*     */     } else {
/* 339 */       marginsCollapseHandler.applyClearance(clearHeightAdjustment);
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean isRendererFloating(IRenderer renderer) {
/* 344 */     return isRendererFloating(renderer, (FloatPropertyValue)renderer.getProperty(99));
/*     */   }
/*     */   
/*     */   static boolean isRendererFloating(IRenderer renderer, FloatPropertyValue kidFloatPropertyVal) {
/* 348 */     Integer position = (Integer)renderer.getProperty(52);
/* 349 */     boolean notAbsolutePos = (position == null || position.intValue() != 3);
/* 350 */     return (notAbsolutePos && kidFloatPropertyVal != null && !kidFloatPropertyVal.equals(FloatPropertyValue.NONE));
/*     */   }
/*     */   
/*     */   static boolean isClearanceApplied(List<IRenderer> floatingRenderers, ClearPropertyValue clearPropertyValue) {
/* 354 */     if (clearPropertyValue == null || clearPropertyValue.equals(ClearPropertyValue.NONE)) {
/* 355 */       return false;
/*     */     }
/* 357 */     for (IRenderer floatingRenderer : floatingRenderers) {
/* 358 */       FloatPropertyValue floatPropertyValue = (FloatPropertyValue)floatingRenderer.getProperty(99);
/*     */       
/* 360 */       if (clearPropertyValue.equals(ClearPropertyValue.BOTH) || (floatPropertyValue
/* 361 */         .equals(FloatPropertyValue.LEFT) && clearPropertyValue.equals(ClearPropertyValue.LEFT)) || (floatPropertyValue
/* 362 */         .equals(FloatPropertyValue.RIGHT) && clearPropertyValue.equals(ClearPropertyValue.RIGHT))) {
/* 363 */         return true;
/*     */       }
/*     */     } 
/* 366 */     return false;
/*     */   }
/*     */   
/*     */   static void removeParentArtifactsOnPageSplitIfOnlyFloatsOverflow(IRenderer overflowRenderer) {
/* 370 */     overflowRenderer.setProperty(6, null);
/* 371 */     overflowRenderer.setProperty(90, null);
/* 372 */     overflowRenderer.setProperty(106, null);
/*     */     
/* 374 */     Border[] borders = AbstractRenderer.getBorders(overflowRenderer);
/* 375 */     overflowRenderer.setProperty(13, null);
/* 376 */     overflowRenderer.setProperty(10, null);
/* 377 */     if (borders[1] != null) {
/* 378 */       overflowRenderer.setProperty(12, new SolidBorder(ColorConstants.BLACK, borders[1].getWidth(), 0.0F));
/*     */     }
/* 380 */     if (borders[3] != null) {
/* 381 */       overflowRenderer.setProperty(11, new SolidBorder(ColorConstants.BLACK, borders[3].getWidth(), 0.0F));
/*     */     }
/*     */     
/* 384 */     overflowRenderer.setProperty(46, UnitValue.createPointValue(0.0F));
/* 385 */     overflowRenderer.setProperty(43, UnitValue.createPointValue(0.0F));
/* 386 */     overflowRenderer.setProperty(50, UnitValue.createPointValue(0.0F));
/* 387 */     overflowRenderer.setProperty(47, UnitValue.createPointValue(0.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void adjustBoxForFloatRight(Rectangle layoutBox, float blockWidth) {
/* 392 */     layoutBox.setX(layoutBox.getRight() - blockWidth);
/* 393 */     layoutBox.setWidth(blockWidth);
/*     */   }
/*     */   
/*     */   private static Rectangle[] findLastLeftAndRightBoxes(Rectangle layoutBox, List<Rectangle> yLevelBoxes) {
/* 397 */     Rectangle lastLeftFloatAtY = null;
/* 398 */     Rectangle lastRightFloatAtY = null;
/* 399 */     float left = layoutBox.getLeft();
/* 400 */     for (Rectangle box : yLevelBoxes) {
/* 401 */       if (box.getLeft() < left) {
/* 402 */         left = box.getLeft();
/*     */       }
/*     */     } 
/* 405 */     for (Rectangle box : yLevelBoxes) {
/* 406 */       if (left >= box.getLeft() && left < box.getRight()) {
/* 407 */         lastLeftFloatAtY = box;
/* 408 */         left = box.getRight(); continue;
/*     */       } 
/* 410 */       lastRightFloatAtY = box;
/*     */     } 
/*     */ 
/*     */     
/* 414 */     return new Rectangle[] { lastLeftFloatAtY, lastRightFloatAtY };
/*     */   }
/*     */   
/*     */   private static List<Rectangle> getBoxesAtYLevel(List<Rectangle> floatRendererAreas, float currY) {
/* 418 */     List<Rectangle> yLevelBoxes = new ArrayList<>();
/* 419 */     for (Rectangle box : floatRendererAreas) {
/* 420 */       if (box.getBottom() + 1.0E-4F < currY && box.getTop() + 1.0E-4F >= currY) {
/* 421 */         yLevelBoxes.add(box);
/*     */       }
/*     */     } 
/* 424 */     return yLevelBoxes;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/FloatingHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */