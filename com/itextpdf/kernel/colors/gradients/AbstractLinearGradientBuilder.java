/*     */ package com.itextpdf.kernel.colors.gradients;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.PatternColor;
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.NoninvertibleTransformException;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfShading;
/*     */ import com.itextpdf.kernel.pdf.function.PdfFunction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public abstract class AbstractLinearGradientBuilder
/*     */ {
/*     */   protected static final double ZERO_EPSILON = 1.0E-10D;
/*  64 */   private final List<GradientColorStop> stops = new ArrayList<>();
/*  65 */   private GradientSpreadMethod spreadMethod = GradientSpreadMethod.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractLinearGradientBuilder addColorStop(GradientColorStop gradientColorStop) {
/*  79 */     if (gradientColorStop != null) {
/*  80 */       this.stops.add(gradientColorStop);
/*     */     }
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractLinearGradientBuilder setSpreadMethod(GradientSpreadMethod gradientSpreadMethod) {
/*  92 */     if (this.spreadMethod != null) {
/*  93 */       this.spreadMethod = gradientSpreadMethod;
/*     */     } else {
/*  95 */       this.spreadMethod = GradientSpreadMethod.NONE;
/*     */     } 
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GradientColorStop> getColorStops() {
/* 106 */     return new ArrayList<>(this.stops);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientSpreadMethod getSpreadMethod() {
/* 115 */     return this.spreadMethod;
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
/*     */   public Color buildColor(Rectangle targetBoundingBox, AffineTransform contextTransform, PdfDocument document) {
/* 133 */     Point[] baseCoordinatesVector = getGradientVector(targetBoundingBox, contextTransform);
/* 134 */     if (baseCoordinatesVector == null || this.stops.isEmpty())
/*     */     {
/* 136 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 140 */     AffineTransform shadingTransform = new AffineTransform();
/* 141 */     if (contextTransform != null) {
/* 142 */       shadingTransform.concatenate(contextTransform);
/*     */     }
/*     */     
/* 145 */     AffineTransform gradientTransformation = getCurrentSpaceToGradientVectorSpaceTransformation(targetBoundingBox, contextTransform);
/*     */     
/* 147 */     if (gradientTransformation != null) {
/*     */       try {
/* 149 */         if (targetBoundingBox != null) {
/* 150 */           targetBoundingBox = Rectangle.calculateBBox(Arrays.asList(new Point[] { gradientTransformation
/* 151 */                   .inverseTransform(new Point(targetBoundingBox
/* 152 */                       .getLeft(), targetBoundingBox.getBottom()), null), gradientTransformation
/* 153 */                   .inverseTransform(new Point(targetBoundingBox
/* 154 */                       .getLeft(), targetBoundingBox.getTop()), null), gradientTransformation
/* 155 */                   .inverseTransform(new Point(targetBoundingBox
/* 156 */                       .getRight(), targetBoundingBox.getBottom()), null), gradientTransformation
/* 157 */                   .inverseTransform(new Point(targetBoundingBox
/* 158 */                       .getRight(), targetBoundingBox.getTop()), null) }));
/*     */         }
/*     */         
/* 161 */         shadingTransform.concatenate(gradientTransformation);
/* 162 */       } catch (NoninvertibleTransformException e) {
/* 163 */         LoggerFactory.getLogger(getClass()).error("Unable to invert gradient transformation, ignoring it");
/*     */       } 
/*     */     }
/*     */     
/* 167 */     PdfShading.Axial axial = createAxialShading(baseCoordinatesVector, this.stops, this.spreadMethod, targetBoundingBox);
/*     */     
/* 169 */     if (axial == null) {
/* 170 */       return null;
/*     */     }
/*     */     
/* 173 */     PdfPattern.Shading shading = new PdfPattern.Shading((PdfShading)axial);
/* 174 */     if (!shadingTransform.isIdentity()) {
/* 175 */       double[] matrix = new double[6];
/* 176 */       shadingTransform.getMatrix(matrix);
/* 177 */       shading.setMatrix(new PdfArray(matrix));
/*     */     } 
/* 179 */     return (Color)new PatternColor((PdfPattern)shading);
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
/*     */   protected abstract Point[] getGradientVector(Rectangle paramRectangle, AffineTransform paramAffineTransform);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AffineTransform getCurrentSpaceToGradientVectorSpaceTransformation(Rectangle targetBoundingBox, AffineTransform contextTransform) {
/* 206 */     return null;
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
/*     */   protected static double[] evaluateCoveringDomain(Point[] coords, Rectangle toCover) {
/* 221 */     if (toCover == null) {
/* 222 */       return new double[] { 0.0D, 1.0D };
/*     */     }
/* 224 */     AffineTransform transform = new AffineTransform();
/* 225 */     double scale = 1.0D / coords[0].distance(coords[1]);
/* 226 */     double sin = -(coords[1].getY() - coords[0].getY()) * scale;
/* 227 */     double cos = (coords[1].getX() - coords[0].getX()) * scale;
/* 228 */     if (Math.abs(cos) < 1.0E-10D) {
/* 229 */       cos = 0.0D;
/* 230 */       sin = (sin > 0.0D) ? 1.0D : -1.0D;
/* 231 */     } else if (Math.abs(sin) < 1.0E-10D) {
/* 232 */       sin = 0.0D;
/* 233 */       cos = (cos > 0.0D) ? 1.0D : -1.0D;
/*     */     } 
/* 235 */     transform.concatenate(new AffineTransform(cos, sin, -sin, cos, 0.0D, 0.0D));
/*     */     
/* 237 */     transform.scale(scale, scale);
/* 238 */     transform.translate(-coords[0].getX(), -coords[0].getY());
/*     */     
/* 240 */     Point[] rectanglePoints = toCover.toPointsArray();
/* 241 */     double minX = transform.transform(rectanglePoints[0], null).getX();
/* 242 */     double maxX = minX;
/* 243 */     for (int i = 1; i < rectanglePoints.length; i++) {
/* 244 */       double currentX = transform.transform(rectanglePoints[i], null).getX();
/* 245 */       minX = Math.min(minX, currentX);
/* 246 */       maxX = Math.max(maxX, currentX);
/*     */     } 
/*     */     
/* 249 */     return new double[] { minX, maxX };
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
/*     */   protected static Point[] createCoordinatesForNewDomain(double[] newDomain, Point[] baseVector) {
/* 262 */     double xDiff = baseVector[1].getX() - baseVector[0].getX();
/* 263 */     double yDiff = baseVector[1].getY() - baseVector[0].getY();
/*     */ 
/*     */ 
/*     */     
/* 267 */     Point[] targetCoords = { baseVector[0].getLocation(), baseVector[1].getLocation() };
/*     */     
/* 269 */     targetCoords[0].translate(xDiff * newDomain[0], yDiff * newDomain[0]);
/* 270 */     targetCoords[1].translate(xDiff * (newDomain[1] - 1.0D), yDiff * (newDomain[1] - 1.0D));
/* 271 */     return targetCoords;
/*     */   }
/*     */   
/*     */   private static PdfShading.Axial createAxialShading(Point[] baseCoordinatesVector, List<GradientColorStop> stops, GradientSpreadMethod spreadMethod, Rectangle targetBoundingBox) {
/*     */     Point[] actualCoordinates;
/* 276 */     double baseVectorLength = baseCoordinatesVector[1].distance(baseCoordinatesVector[0]);
/*     */     
/* 278 */     List<GradientColorStop> stopsToConstruct = normalizeStops(stops, baseVectorLength);
/* 279 */     double[] coordinatesDomain = { 0.0D, 1.0D };
/*     */     
/* 281 */     if (baseVectorLength < 1.0E-10D || stopsToConstruct.size() == 1) {
/*     */       
/* 283 */       if (spreadMethod == GradientSpreadMethod.NONE) {
/* 284 */         return null;
/*     */       }
/*     */       
/* 287 */       actualCoordinates = new Point[] { new Point(targetBoundingBox.getLeft(), targetBoundingBox.getBottom()), new Point(targetBoundingBox.getRight(), targetBoundingBox.getBottom()) };
/*     */       
/* 289 */       GradientColorStop lastColorStop = stopsToConstruct.get(stopsToConstruct.size() - 1);
/* 290 */       stopsToConstruct = Arrays.asList(new GradientColorStop[] { new GradientColorStop(lastColorStop, 0.0D, GradientColorStop.OffsetType.RELATIVE), new GradientColorStop(lastColorStop, 1.0D, GradientColorStop.OffsetType.RELATIVE) });
/*     */     } else {
/*     */       
/* 293 */       coordinatesDomain = evaluateCoveringDomain(baseCoordinatesVector, targetBoundingBox);
/* 294 */       if (spreadMethod == GradientSpreadMethod.REPEAT || spreadMethod == GradientSpreadMethod.REFLECT) {
/* 295 */         stopsToConstruct = adjustNormalizedStopsToCoverDomain(stopsToConstruct, coordinatesDomain, spreadMethod);
/*     */       }
/* 297 */       else if (spreadMethod == GradientSpreadMethod.PAD) {
/* 298 */         adjustStopsForPadIfNeeded(stopsToConstruct, coordinatesDomain);
/*     */       } else {
/*     */         
/* 301 */         double firstStopOffset = ((GradientColorStop)stopsToConstruct.get(0)).getOffset();
/* 302 */         double lastStopOffset = ((GradientColorStop)stopsToConstruct.get(stopsToConstruct.size() - 1)).getOffset();
/* 303 */         if (lastStopOffset - firstStopOffset < 1.0E-10D || coordinatesDomain[1] <= firstStopOffset || coordinatesDomain[0] >= lastStopOffset)
/*     */         {
/*     */           
/* 306 */           return null;
/*     */         }
/* 308 */         coordinatesDomain[0] = Math.max(coordinatesDomain[0], firstStopOffset);
/* 309 */         coordinatesDomain[1] = Math.min(coordinatesDomain[1], lastStopOffset);
/*     */       } 
/* 311 */       assert coordinatesDomain[0] <= coordinatesDomain[1];
/*     */       
/* 313 */       actualCoordinates = createCoordinatesForNewDomain(coordinatesDomain, baseCoordinatesVector);
/*     */     } 
/*     */     
/* 316 */     return new PdfShading.Axial((PdfColorSpace)new PdfDeviceCs.Rgb(), 
/*     */         
/* 318 */         createCoordsPdfArray(actualCoordinates), new PdfArray(coordinatesDomain), 
/*     */         
/* 320 */         constructFunction(stopsToConstruct));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<GradientColorStop> normalizeStops(List<GradientColorStop> toNormalize, double baseVectorLength) {
/* 327 */     if (baseVectorLength < 1.0E-10D) {
/* 328 */       return Arrays.asList(new GradientColorStop[] { new GradientColorStop(toNormalize.get(toNormalize.size() - 1), 0.0D, GradientColorStop.OffsetType.RELATIVE) });
/*     */     }
/*     */ 
/*     */     
/* 332 */     List<GradientColorStop> result = copyStopsAndNormalizeAbsoluteOffsets(toNormalize, baseVectorLength);
/*     */     
/* 334 */     normalizeFirstStopOffset(result);
/*     */     
/* 336 */     normalizeAutoStops(result);
/*     */     
/* 338 */     normalizeHintsOffsets(result);
/*     */     
/* 340 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void normalizeHintsOffsets(List<GradientColorStop> result) {
/* 345 */     for (int i = 0; i < result.size() - 1; i++) {
/* 346 */       GradientColorStop stopColor = result.get(i);
/* 347 */       if (stopColor.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
/* 348 */         double currentStopOffset = stopColor.getOffset();
/* 349 */         double nextStopOffset = ((GradientColorStop)result.get(i + 1)).getOffset();
/* 350 */         if (currentStopOffset != nextStopOffset) {
/* 351 */           double hintOffset = (stopColor.getHintOffset() - currentStopOffset) / (nextStopOffset - currentStopOffset);
/*     */           
/* 353 */           stopColor.setHint(hintOffset, GradientColorStop.HintOffsetType.RELATIVE_BETWEEN_COLORS);
/*     */         } else {
/*     */           
/* 356 */           stopColor.setHint(0.0D, GradientColorStop.HintOffsetType.NONE);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 361 */     ((GradientColorStop)result.get(result.size() - 1)).setHint(0.0D, GradientColorStop.HintOffsetType.NONE);
/*     */   }
/*     */   
/*     */   private static void normalizeAutoStops(List<GradientColorStop> toNormalize) {
/* 365 */     assert ((GradientColorStop)toNormalize.get(0)).getOffsetType() == GradientColorStop.OffsetType.RELATIVE;
/*     */     
/* 367 */     int firstAutoStopIndex = 1;
/* 368 */     GradientColorStop firstStopColor = toNormalize.get(0);
/*     */     
/* 370 */     double prevOffset = (firstStopColor.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) ? firstStopColor.getHintOffset() : firstStopColor.getOffset();
/* 371 */     for (int i = 1; i < toNormalize.size(); i++) {
/* 372 */       GradientColorStop currentStop = toNormalize.get(i);
/* 373 */       if (currentStop.getOffsetType() == GradientColorStop.OffsetType.AUTO) {
/* 374 */         if (currentStop.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
/* 375 */           double hintOffset = currentStop.getHintOffset();
/* 376 */           normalizeAutoStops(toNormalize, firstAutoStopIndex, i + 1, prevOffset, hintOffset);
/* 377 */           prevOffset = hintOffset;
/* 378 */           firstAutoStopIndex = i + 1;
/*     */         } 
/*     */       } else {
/* 381 */         if (firstAutoStopIndex < i) {
/*     */           
/* 383 */           double offset = currentStop.getOffset();
/* 384 */           normalizeAutoStops(toNormalize, firstAutoStopIndex, i, prevOffset, offset);
/*     */         } 
/* 386 */         firstAutoStopIndex = i + 1;
/*     */         
/* 388 */         prevOffset = (currentStop.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) ? currentStop.getHintOffset() : currentStop.getOffset();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 393 */     if (firstAutoStopIndex < toNormalize.size()) {
/* 394 */       double lastStopOffset = Math.max(1.0D, prevOffset);
/* 395 */       normalizeAutoStops(toNormalize, firstAutoStopIndex, toNormalize.size(), prevOffset, lastStopOffset);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void normalizeAutoStops(List<GradientColorStop> toNormalizeList, int fromIndex, int toIndex, double prevOffset, double nextOffset) {
/* 401 */     assert toIndex >= fromIndex;
/*     */     
/* 403 */     int intervalsCount = Math.min(toIndex, toNormalizeList.size() - 1) - fromIndex + 1;
/* 404 */     double offsetShift = (nextOffset - prevOffset) / intervalsCount;
/* 405 */     double currentOffset = prevOffset;
/* 406 */     for (int i = fromIndex; i < toIndex; i++) {
/* 407 */       currentOffset += offsetShift;
/* 408 */       GradientColorStop currentAutoStop = toNormalizeList.get(i);
/*     */       
/* 410 */       assert currentAutoStop.getOffsetType() == GradientColorStop.OffsetType.AUTO;
/*     */       
/* 412 */       currentAutoStop.setOffset(currentOffset, GradientColorStop.OffsetType.RELATIVE);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void normalizeFirstStopOffset(List<GradientColorStop> result) {
/* 418 */     GradientColorStop firstStop = result.get(0);
/* 419 */     if (firstStop.getOffsetType() != GradientColorStop.OffsetType.AUTO) {
/*     */       return;
/*     */     }
/* 422 */     double firstStopOffset = 0.0D;
/* 423 */     for (GradientColorStop stopColor : result) {
/* 424 */       if (stopColor.getOffsetType() == GradientColorStop.OffsetType.RELATIVE) {
/* 425 */         firstStopOffset = stopColor.getOffset(); break;
/*     */       } 
/* 427 */       if (stopColor.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
/* 428 */         firstStopOffset = stopColor.getHintOffset();
/*     */         break;
/*     */       } 
/*     */     } 
/* 432 */     firstStopOffset = Math.min(0.0D, firstStopOffset);
/* 433 */     firstStop.setOffset(firstStopOffset, GradientColorStop.OffsetType.RELATIVE);
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<GradientColorStop> copyStopsAndNormalizeAbsoluteOffsets(List<GradientColorStop> toNormalize, double baseVectorLength) {
/* 438 */     double lastUsedOffset = Double.NEGATIVE_INFINITY;
/* 439 */     List<GradientColorStop> copy = new ArrayList<>(toNormalize.size());
/* 440 */     for (GradientColorStop stop : toNormalize) {
/* 441 */       double offset = stop.getOffset();
/* 442 */       GradientColorStop.OffsetType offsetType = stop.getOffsetType();
/* 443 */       if (offsetType == GradientColorStop.OffsetType.ABSOLUTE) {
/* 444 */         offsetType = GradientColorStop.OffsetType.RELATIVE;
/* 445 */         offset /= baseVectorLength;
/*     */       } 
/*     */       
/* 448 */       if (offsetType == GradientColorStop.OffsetType.RELATIVE) {
/* 449 */         if (offset < lastUsedOffset) {
/* 450 */           offset = lastUsedOffset;
/*     */         }
/* 452 */         lastUsedOffset = offset;
/*     */       } 
/*     */       
/* 455 */       GradientColorStop result = new GradientColorStop(stop, offset, offsetType);
/*     */       
/* 457 */       double hintOffset = stop.getHintOffset();
/* 458 */       GradientColorStop.HintOffsetType hintOffsetType = stop.getHintOffsetType();
/* 459 */       if (hintOffsetType == GradientColorStop.HintOffsetType.ABSOLUTE_ON_GRADIENT) {
/* 460 */         hintOffsetType = GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT;
/* 461 */         hintOffset /= baseVectorLength;
/*     */       } 
/*     */       
/* 464 */       if (hintOffsetType == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
/* 465 */         if (hintOffset < lastUsedOffset) {
/* 466 */           hintOffset = lastUsedOffset;
/*     */         }
/* 468 */         lastUsedOffset = hintOffset;
/*     */       } 
/*     */       
/* 471 */       result.setHint(hintOffset, hintOffsetType);
/* 472 */       copy.add(result);
/*     */     } 
/* 474 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void adjustStopsForPadIfNeeded(List<GradientColorStop> stopsToConstruct, double[] coordinatesDomain) {
/* 479 */     GradientColorStop firstStop = stopsToConstruct.get(0);
/* 480 */     if (coordinatesDomain[0] < firstStop.getOffset()) {
/* 481 */       stopsToConstruct.add(0, new GradientColorStop(firstStop, coordinatesDomain[0], GradientColorStop.OffsetType.RELATIVE));
/*     */     }
/* 483 */     GradientColorStop lastStop = stopsToConstruct.get(stopsToConstruct.size() - 1);
/* 484 */     if (coordinatesDomain[1] > lastStop.getOffset()) {
/* 485 */       stopsToConstruct.add(new GradientColorStop(lastStop, coordinatesDomain[1], GradientColorStop.OffsetType.RELATIVE));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<GradientColorStop> adjustNormalizedStopsToCoverDomain(List<GradientColorStop> normalizedStops, double[] targetDomain, GradientSpreadMethod spreadMethod) {
/* 491 */     List<GradientColorStop> adjustedStops = new ArrayList<>();
/*     */     
/* 493 */     GradientColorStop lastColorStop = normalizedStops.get(normalizedStops.size() - 1);
/* 494 */     double originalIntervalEnd = lastColorStop.getOffset();
/* 495 */     double originalIntervalStart = ((GradientColorStop)normalizedStops.get(0)).getOffset();
/* 496 */     double originalIntervalLength = originalIntervalEnd - originalIntervalStart;
/*     */     
/* 498 */     if (originalIntervalLength <= 1.0E-10D) {
/* 499 */       return Arrays.asList(new GradientColorStop[] { new GradientColorStop(lastColorStop, targetDomain[0], GradientColorStop.OffsetType.RELATIVE), new GradientColorStop(lastColorStop, targetDomain[1], GradientColorStop.OffsetType.RELATIVE) });
/*     */     }
/*     */ 
/*     */     
/* 503 */     double startIntervalsShift = Math.floor((targetDomain[0] - originalIntervalStart) / originalIntervalLength);
/* 504 */     double iterationOffset = originalIntervalStart + originalIntervalLength * startIntervalsShift;
/*     */     
/* 506 */     boolean isIterationInverse = (spreadMethod == GradientSpreadMethod.REFLECT && Math.abs(startIntervalsShift) % 2.0D != 0.0D);
/*     */     
/* 508 */     int currentIterationIndex = isIterationInverse ? (normalizedStops.size() - 1) : 0;
/*     */     
/* 510 */     double lastComputedOffset = iterationOffset;
/* 511 */     while (lastComputedOffset <= targetDomain[1]) {
/* 512 */       GradientColorStop currentStop = normalizedStops.get(currentIterationIndex);
/*     */ 
/*     */       
/* 515 */       lastComputedOffset = isIterationInverse ? (iterationOffset + originalIntervalEnd - currentStop.getOffset()) : (iterationOffset + currentStop.getOffset() - originalIntervalStart);
/* 516 */       GradientColorStop computedStop = new GradientColorStop(currentStop, lastComputedOffset, GradientColorStop.OffsetType.RELATIVE);
/*     */ 
/*     */       
/* 519 */       if (lastComputedOffset < targetDomain[0] && !adjustedStops.isEmpty()) {
/* 520 */         adjustedStops.set(0, computedStop);
/*     */       } else {
/* 522 */         adjustedStops.add(computedStop);
/*     */       } 
/*     */       
/* 525 */       if (isIterationInverse) {
/* 526 */         currentIterationIndex--;
/* 527 */         if (currentIterationIndex < 0) {
/* 528 */           iterationOffset += originalIntervalLength;
/* 529 */           isIterationInverse = false;
/* 530 */           currentIterationIndex = 1;
/*     */         } 
/*     */       } else {
/* 533 */         currentIterationIndex++;
/* 534 */         if (currentIterationIndex == normalizedStops.size()) {
/* 535 */           iterationOffset += originalIntervalLength;
/* 536 */           isIterationInverse = (spreadMethod == GradientSpreadMethod.REFLECT);
/* 537 */           currentIterationIndex = isIterationInverse ? (normalizedStops.size() - 2) : 0;
/*     */         } 
/*     */       } 
/*     */       
/* 541 */       if (isIterationInverse) {
/* 542 */         GradientColorStop nextColor = normalizedStops.get(currentIterationIndex);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 547 */         computedStop.setHint(1.0D - nextColor.getHintOffset(), nextColor.getHintOffsetType()); continue;
/*     */       } 
/* 549 */       computedStop.setHint(currentStop.getHintOffset(), currentStop.getHintOffsetType());
/*     */     } 
/*     */ 
/*     */     
/* 553 */     return adjustedStops;
/*     */   }
/*     */   
/*     */   private static PdfFunction constructFunction(List<GradientColorStop> toConstruct) {
/* 557 */     int functionsAmount = toConstruct.size() - 1;
/*     */     
/* 559 */     double[] bounds = new double[functionsAmount - 1];
/* 560 */     List<PdfFunction> type2Functions = new ArrayList<>(functionsAmount);
/*     */ 
/*     */     
/* 563 */     GradientColorStop nextStop = toConstruct.get(0);
/* 564 */     double domainStart = nextStop.getOffset();
/* 565 */     for (int i = 1; i < functionsAmount; i++) {
/* 566 */       GradientColorStop gradientColorStop = nextStop;
/* 567 */       nextStop = toConstruct.get(i);
/* 568 */       bounds[i - 1] = nextStop.getOffset();
/* 569 */       type2Functions.add(constructSingleGradientSegmentFunction(gradientColorStop, nextStop));
/*     */     } 
/*     */     
/* 572 */     GradientColorStop currentStop = nextStop;
/* 573 */     nextStop = toConstruct.get(toConstruct.size() - 1);
/* 574 */     type2Functions.add(constructSingleGradientSegmentFunction(currentStop, nextStop));
/* 575 */     double domainEnd = nextStop.getOffset();
/*     */     
/* 577 */     double[] encode = new double[functionsAmount * 2];
/* 578 */     for (int j = 0; j < encode.length; j += 2) {
/* 579 */       encode[j] = 0.0D;
/* 580 */       encode[j + 1] = 1.0D;
/*     */     } 
/*     */     
/* 583 */     return (PdfFunction)new PdfFunction.Type3(new PdfArray(new double[] { domainStart, domainEnd }, ), null, type2Functions, new PdfArray(bounds), new PdfArray(encode));
/*     */   }
/*     */ 
/*     */   
/*     */   private static PdfFunction constructSingleGradientSegmentFunction(GradientColorStop from, GradientColorStop to) {
/* 588 */     double exponent = 1.0D;
/* 589 */     float[] fromColor = from.getRgbArray();
/* 590 */     float[] toColor = to.getRgbArray();
/* 591 */     if (from.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_BETWEEN_COLORS) {
/* 592 */       double hintOffset = from.getHintOffset();
/* 593 */       if (hintOffset <= 1.0E-10D) {
/* 594 */         fromColor = toColor;
/* 595 */       } else if (hintOffset >= 0.9999999999D) {
/* 596 */         toColor = fromColor;
/*     */       } else {
/*     */         
/* 599 */         exponent = Math.log(0.5D) / Math.log(hintOffset);
/*     */       } 
/*     */     } 
/* 602 */     return (PdfFunction)new PdfFunction.Type2(new PdfArray(new float[] { 0.0F, 1.0F }, ), null, new PdfArray(fromColor), new PdfArray(toColor), new PdfNumber(exponent));
/*     */   }
/*     */ 
/*     */   
/*     */   private static PdfArray createCoordsPdfArray(Point[] coordsPoints) {
/* 607 */     assert coordsPoints != null && coordsPoints.length == 2;
/*     */     
/* 609 */     return new PdfArray(new double[] { coordsPoints[0].getX(), coordsPoints[0].getY(), coordsPoints[1]
/* 610 */           .getX(), coordsPoints[1].getY() });
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/gradients/AbstractLinearGradientBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */