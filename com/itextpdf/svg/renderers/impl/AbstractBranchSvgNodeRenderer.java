/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.NoninvertibleTransformException;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.SvgConstants;
/*     */ import com.itextpdf.svg.renderers.IBranchSvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.SvgCssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public abstract class AbstractBranchSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */   implements IBranchSvgNodeRenderer
/*     */ {
/*  80 */   private final List<ISvgNodeRenderer> children = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDraw(SvgDrawContext context) {
/*  92 */     if (getChildren().size() > 0) {
/*  93 */       PdfStream stream = new PdfStream();
/*  94 */       stream.put(PdfName.Type, (PdfObject)PdfName.XObject);
/*  95 */       stream.put(PdfName.Subtype, (PdfObject)PdfName.Form);
/*     */       
/*  97 */       PdfFormXObject xObject = (PdfFormXObject)PdfXObject.makeXObject(stream);
/*     */       
/*  99 */       PdfCanvas newCanvas = new PdfCanvas(xObject, context.getCurrentCanvas().getDocument());
/* 100 */       applyViewBox(context);
/*     */       
/* 102 */       boolean overflowVisible = isOverflowVisible(this);
/*     */       
/* 104 */       if (this instanceof MarkerSvgNodeRenderer && overflowVisible) {
/* 105 */         writeBBoxAccordingToVisibleOverflow(context, stream);
/*     */       } else {
/* 107 */         Rectangle bbBox = context.getCurrentViewPort().clone();
/* 108 */         stream.put(PdfName.BBox, (PdfObject)new PdfArray(bbBox));
/*     */       } 
/*     */       
/* 111 */       if (this instanceof MarkerSvgNodeRenderer) {
/* 112 */         ((MarkerSvgNodeRenderer)this).applyMarkerAttributes(context);
/*     */       }
/*     */       
/* 115 */       context.pushCanvas(newCanvas);
/*     */ 
/*     */       
/* 118 */       if (!(this instanceof MarkerSvgNodeRenderer) || !overflowVisible) {
/* 119 */         applyViewportClip(context);
/*     */       }
/*     */       
/* 122 */       applyViewportTranslationCorrection(context);
/*     */       
/* 124 */       for (ISvgNodeRenderer child : getChildren()) {
/* 125 */         if (!(child instanceof MarkerSvgNodeRenderer)) {
/* 126 */           newCanvas.saveState();
/* 127 */           child.draw(context);
/* 128 */           newCanvas.restoreState();
/*     */         } 
/*     */       } 
/*     */       
/* 132 */       cleanUp(context);
/*     */ 
/*     */       
/* 135 */       context.getCurrentCanvas().addXObject((PdfXObject)xObject, 0.0F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void applyViewBox(SvgDrawContext context) {
/* 145 */     if (this.attributesAndStyles != null && this.attributesAndStyles.containsKey(SvgConstants.Attributes.VIEWBOX)) {
/* 146 */       float[] values = getViewBoxValues();
/* 147 */       Rectangle currentViewPort = context.getCurrentViewPort();
/* 148 */       calculateAndApplyViewBox(context, values, currentViewPort);
/*     */     } else {
/* 150 */       float[] values = { 0.0F, 0.0F, context.getCurrentViewPort().getWidth(), context.getCurrentViewPort().getHeight() };
/* 151 */       Rectangle currentViewPort = context.getCurrentViewPort();
/* 152 */       calculateAndApplyViewBox(context, values, currentViewPort);
/*     */     } 
/*     */   }
/*     */   
/*     */   String[] retrieveAlignAndMeet() {
/* 157 */     String meetOrSlice = "meet";
/* 158 */     String align = "xmidymid";
/*     */     
/* 160 */     if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.PRESERVE_ASPECT_RATIO)) {
/*     */       
/* 162 */       String preserveAspectRatioValue = this.attributesAndStyles.get(SvgConstants.Attributes.PRESERVE_ASPECT_RATIO);
/* 163 */       List<String> aspectRatioValuesSplitValues = SvgCssUtils.splitValueList(preserveAspectRatioValue);
/*     */       
/* 165 */       align = ((String)aspectRatioValuesSplitValues.get(0)).toLowerCase();
/* 166 */       if (aspectRatioValuesSplitValues.size() > 1) {
/* 167 */         meetOrSlice = ((String)aspectRatioValuesSplitValues.get(1)).toLowerCase();
/*     */       }
/*     */     } 
/*     */     
/* 171 */     if (this instanceof MarkerSvgNodeRenderer && !"none".equals(align) && "meet"
/* 172 */       .equals(meetOrSlice))
/*     */     {
/*     */       
/* 175 */       align = "xminymin";
/*     */     }
/*     */     
/* 178 */     return new String[] { align, meetOrSlice };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyViewportClip(SvgDrawContext context) {
/* 187 */     PdfCanvas currentCanvas = context.getCurrentCanvas();
/* 188 */     currentCanvas.rectangle(context.getCurrentViewPort());
/* 189 */     currentCanvas.clip();
/* 190 */     currentCanvas.endPath();
/*     */   }
/*     */   
/*     */   private void applyViewportTranslationCorrection(SvgDrawContext context) {
/* 194 */     PdfCanvas currentCanvas = context.getCurrentCanvas();
/* 195 */     AffineTransform tf = calculateViewPortTranslation(context);
/* 196 */     if (!tf.isIdentity() && "none"
/* 197 */       .equals(getAttribute(SvgConstants.Attributes.PRESERVE_ASPECT_RATIO))) {
/* 198 */       currentCanvas.concatMatrix(tf);
/*     */     }
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
/*     */   AffineTransform processAspectRatioPosition(SvgDrawContext context, float[] viewBoxValues, String align, float scaleWidth, float scaleHeight) {
/* 214 */     AffineTransform transform = new AffineTransform();
/* 215 */     Rectangle currentViewPort = context.getCurrentViewPort();
/*     */     
/* 217 */     float midXBox = viewBoxValues[0] + viewBoxValues[2] / 2.0F;
/* 218 */     float midYBox = viewBoxValues[1] + viewBoxValues[3] / 2.0F;
/*     */     
/* 220 */     float midXPort = currentViewPort.getX() + currentViewPort.getWidth() / 2.0F;
/* 221 */     float midYPort = currentViewPort.getY() + currentViewPort.getHeight() / 2.0F;
/*     */     
/* 223 */     float x = 0.0F;
/* 224 */     float y = 0.0F;
/*     */ 
/*     */     
/* 227 */     if (this.attributesAndStyles.containsKey("x")) {
/* 228 */       x = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("x"));
/*     */     }
/*     */ 
/*     */     
/* 232 */     if (this.attributesAndStyles.containsKey("y")) {
/* 233 */       y = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("y"));
/*     */     }
/*     */     
/* 236 */     if (!(this instanceof MarkerSvgNodeRenderer)) {
/* 237 */       x -= currentViewPort.getX();
/* 238 */       y -= currentViewPort.getY();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 243 */     switch (align.toLowerCase())
/*     */     
/*     */     { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case "none":
/* 286 */         x /= scaleWidth;
/* 287 */         y /= scaleHeight;
/*     */         
/* 289 */         transform.translate(x, y);
/*     */         
/* 291 */         return transform;
/*     */       case "xminymin": x -= viewBoxValues[0]; y -= viewBoxValues[1];
/*     */       case "xminymid": x -= viewBoxValues[0]; y += midYPort - midYBox;
/*     */       case "xminymax": x -= viewBoxValues[0]; y += currentViewPort.getHeight() - viewBoxValues[3];
/*     */       case "xmidymin": x += midXPort - midXBox; y -= viewBoxValues[1];
/*     */       case "xmidymax": x += midXPort - midXBox; y += currentViewPort.getHeight() - viewBoxValues[3];
/*     */       case "xmaxymin": x += currentViewPort.getWidth() - viewBoxValues[2]; y -= viewBoxValues[1];
/*     */       case "xmaxymid": x += currentViewPort.getWidth() - viewBoxValues[2]; y += midYPort - midYBox;
/*     */       case "xmaxymax":
/* 300 */         x += currentViewPort.getWidth() - viewBoxValues[2]; y += currentViewPort.getHeight() - viewBoxValues[3]; }  x += midXPort - midXBox; y += midYPort - midYBox; } private void cleanUp(SvgDrawContext context) { if (getParent() != null) {
/* 301 */       context.removeCurrentViewPort();
/*     */     }
/*     */     
/* 304 */     context.popCanvas(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addChild(ISvgNodeRenderer child) {
/* 310 */     if (child != null) {
/* 311 */       this.children.add(child);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<ISvgNodeRenderer> getChildren() {
/* 318 */     return Collections.unmodifiableList(this.children);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void deepCopyChildren(AbstractBranchSvgNodeRenderer deepCopy) {
/* 327 */     for (ISvgNodeRenderer child : this.children) {
/* 328 */       ISvgNodeRenderer newChild = child.createDeepCopy();
/* 329 */       child.setParent(deepCopy);
/* 330 */       deepCopy.addChild(newChild);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void postDraw(SvgDrawContext context) {}
/*     */ 
/*     */   
/*     */   public abstract ISvgNodeRenderer createDeepCopy();
/*     */ 
/*     */   
/*     */   void setPartOfClipPath(boolean isPart) {
/* 343 */     super.setPartOfClipPath(isPart);
/* 344 */     for (ISvgNodeRenderer child : this.children) {
/* 345 */       if (child instanceof AbstractSvgNodeRenderer) {
/* 346 */         ((AbstractSvgNodeRenderer)child).setPartOfClipPath(isPart);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void calculateAndApplyViewBox(SvgDrawContext context, float[] values, Rectangle currentViewPort) {
/* 352 */     String[] alignAndMeet = retrieveAlignAndMeet();
/* 353 */     String align = alignAndMeet[0];
/* 354 */     String meetOrSlice = alignAndMeet[1];
/*     */     
/* 356 */     float scaleWidth = currentViewPort.getWidth() / values[2];
/* 357 */     float scaleHeight = currentViewPort.getHeight() / values[3];
/*     */     
/* 359 */     boolean forceUniformScaling = !"none".equals(align);
/* 360 */     if (forceUniformScaling) {
/*     */       
/* 362 */       if ("meet".equals(meetOrSlice)) {
/* 363 */         scaleWidth = Math.min(scaleWidth, scaleHeight);
/*     */       } else {
/* 365 */         scaleWidth = Math.max(scaleWidth, scaleHeight);
/*     */       } 
/* 367 */       scaleHeight = scaleWidth;
/*     */     } 
/*     */     
/* 370 */     AffineTransform scale = AffineTransform.getScaleInstance(scaleWidth, scaleHeight);
/*     */     
/* 372 */     float[] scaledViewBoxValues = scaleViewBoxValues(values, scaleWidth, scaleHeight);
/*     */     
/* 374 */     AffineTransform transform = processAspectRatioPosition(context, scaledViewBoxValues, align, scaleWidth, scaleHeight);
/*     */     
/* 376 */     if (!scale.isIdentity()) {
/* 377 */       context.getCurrentCanvas().concatMatrix(scale);
/*     */       
/* 379 */       context.getCurrentViewPort()
/* 380 */         .setWidth(currentViewPort.getWidth() / scaleWidth)
/* 381 */         .setX(currentViewPort.getX() / scaleWidth)
/* 382 */         .setHeight(currentViewPort.getHeight() / scaleHeight)
/* 383 */         .setY(currentViewPort.getY() / scaleHeight);
/*     */     } 
/*     */     
/* 386 */     if (!transform.isIdentity()) {
/* 387 */       context.getCurrentCanvas()
/* 388 */         .concatMatrix(transform);
/*     */ 
/*     */       
/* 391 */       context.getCurrentViewPort()
/* 392 */         .setX(currentViewPort.getX() + -1.0F * (float)transform.getTranslateX())
/* 393 */         .setY(currentViewPort.getY() + -1.0F * (float)transform.getTranslateY());
/*     */     } 
/*     */   }
/*     */   
/*     */   float[] getViewBoxValues() {
/* 398 */     String viewBoxValues = this.attributesAndStyles.get(SvgConstants.Attributes.VIEWBOX);
/* 399 */     List<String> valueStrings = SvgCssUtils.splitValueList(viewBoxValues);
/* 400 */     float[] values = new float[valueStrings.size()];
/* 401 */     for (int i = 0; i < values.length; i++) {
/* 402 */       values[i] = CssUtils.parseAbsoluteLength((String)valueStrings.get(i));
/*     */     }
/* 404 */     return values;
/*     */   }
/*     */   
/*     */   private static float[] scaleViewBoxValues(float[] values, float scaleWidth, float scaleHeight) {
/* 408 */     float[] scaledViewBoxValues = new float[values.length];
/* 409 */     scaledViewBoxValues[0] = values[0] * scaleWidth;
/* 410 */     scaledViewBoxValues[1] = values[1] * scaleHeight;
/* 411 */     scaledViewBoxValues[2] = values[2] * scaleWidth;
/* 412 */     scaledViewBoxValues[3] = values[3] * scaleHeight;
/* 413 */     return scaledViewBoxValues;
/*     */   }
/*     */   
/*     */   private static boolean isOverflowVisible(AbstractSvgNodeRenderer currentElement) {
/* 417 */     return ("visible".equals(currentElement.attributesAndStyles.get("overflow")) || "auto"
/* 418 */       .equals(currentElement.attributesAndStyles.get("overflow")));
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
/*     */   private static void writeBBoxAccordingToVisibleOverflow(SvgDrawContext context, PdfStream stream) {
/* 431 */     List<PdfCanvas> canvases = new ArrayList<>();
/* 432 */     int canvasesSize = context.size();
/* 433 */     for (int i = 0; i < canvasesSize; i++) {
/* 434 */       canvases.add(context.popCanvas());
/*     */     }
/* 436 */     AffineTransform transform = new AffineTransform();
/* 437 */     for (int j = canvases.size() - 1; j >= 0; j--) {
/* 438 */       PdfCanvas canvas = canvases.get(j);
/* 439 */       Matrix matrix = canvas.getGraphicsState().getCtm();
/* 440 */       transform.concatenate(new AffineTransform(matrix.get(0), matrix.get(1), matrix.get(3), matrix
/* 441 */             .get(4), matrix.get(6), matrix.get(7)));
/* 442 */       context.pushCanvas(canvas);
/*     */     } 
/*     */     try {
/* 445 */       transform = transform.createInverse();
/* 446 */     } catch (NoninvertibleTransformException e) {
/*     */ 
/*     */       
/* 449 */       stream.put(PdfName.BBox, (PdfObject)new PdfArray(new Rectangle(0.0F, 0.0F, 0.0F, 0.0F)));
/* 450 */       Logger logger = LoggerFactory.getLogger(AbstractBranchSvgNodeRenderer.class);
/* 451 */       logger.warn("Unable to get inverse transformation matrix and thus calculate a viewport for the element because some of the transformation matrices, which are written to document, have a determinant of zero value. A bbox of zero values will be used as a viewport for this element.");
/*     */       return;
/*     */     } 
/* 454 */     Point[] points = context.getRootViewPort().toPointsArray();
/* 455 */     transform.transform(points, 0, points, 0, points.length);
/* 456 */     Rectangle bbox = Rectangle.calculateBBox(Arrays.asList(points));
/* 457 */     stream.put(PdfName.BBox, (PdfObject)new PdfArray(bbox));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/AbstractBranchSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */