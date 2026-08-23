/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.MarkerVertexType;
/*     */ import com.itextpdf.svg.SvgConstants;
/*     */ import com.itextpdf.svg.renderers.IMarkerCapable;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.SvgCssUtils;
/*     */ import com.itextpdf.svg.utils.SvgTextUtil;
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
/*     */ public class MarkerSvgNodeRenderer
/*     */   extends AbstractBranchSvgNodeRenderer
/*     */ {
/*     */   private static final float DEFAULT_MARKER_WIDTH = 2.25F;
/*     */   private static final float DEFAULT_MARKER_HEIGHT = 2.25F;
/*     */   private static final float DEFAULT_REF_X = 0.0F;
/*     */   private static final float DEFAULT_REF_Y = 0.0F;
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/*  60 */     MarkerSvgNodeRenderer copy = new MarkerSvgNodeRenderer();
/*  61 */     deepCopyAttributesAndStyles(copy);
/*  62 */     deepCopyChildren(copy);
/*  63 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   void preDraw(SvgDrawContext context) {
/*  68 */     super.preDraw(context);
/*  69 */     float[] markerWidthHeight = getMarkerWidthHeightValues();
/*  70 */     float markerWidth = markerWidthHeight[0];
/*  71 */     float markerHeight = markerWidthHeight[1];
/*  72 */     String xAttribute = getAttribute("x");
/*  73 */     String yAttribute = getAttribute("y");
/*  74 */     float x = (xAttribute != null) ? CssUtils.parseAbsoluteLength(xAttribute) : 0.0F;
/*  75 */     float y = (yAttribute != null) ? CssUtils.parseAbsoluteLength(yAttribute) : 0.0F;
/*  76 */     Rectangle markerViewport = new Rectangle(x, y, markerWidth, markerHeight);
/*  77 */     context.addViewPort(markerViewport);
/*     */   }
/*     */   
/*     */   void applyMarkerAttributes(SvgDrawContext context) {
/*  81 */     applyRotation(context);
/*  82 */     applyUserSpaceScaling(context);
/*  83 */     applyCoordinatesTranslation(context);
/*     */   }
/*     */ 
/*     */   
/*     */   static void drawMarker(SvgDrawContext context, String moveX, String moveY, MarkerVertexType markerToUse, AbstractSvgNodeRenderer parent) {
/*  88 */     String elementToReUse = parent.attributesAndStyles.get(markerToUse.toString());
/*  89 */     String normalizedName = SvgTextUtil.filterReferenceValue(elementToReUse);
/*  90 */     ISvgNodeRenderer template = context.getNamedObject(normalizedName);
/*     */     
/*  92 */     ISvgNodeRenderer namedObject = (template == null) ? null : template.createDeepCopy();
/*  93 */     if (namedObject instanceof MarkerSvgNodeRenderer && 
/*     */       
/*  95 */       markerWidthHeightAreCorrect((MarkerSvgNodeRenderer)namedObject)) {
/*     */       
/*  97 */       namedObject.setParent(parent);
/*  98 */       namedObject.setAttribute("marker", markerToUse.toString());
/*  99 */       namedObject.setAttribute("x", moveX);
/* 100 */       namedObject.setAttribute("y", moveY);
/* 101 */       namedObject.draw(context);
/*     */       
/* 103 */       namedObject.setParent(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyViewBox(SvgDrawContext context) {
/* 109 */     if (this.attributesAndStyles != null) {
/* 110 */       float[] markerWidthHeight = getMarkerWidthHeightValues();
/* 111 */       float markerWidth = markerWidthHeight[0];
/* 112 */       float markerHeight = markerWidthHeight[1];
/* 113 */       float[] values = getViewBoxValues(markerWidth, markerHeight);
/* 114 */       Rectangle currentViewPort = context.getCurrentViewPort();
/* 115 */       calculateAndApplyViewBox(context, values, currentViewPort);
/*     */     } 
/*     */   }
/*     */   
/*     */   private float[] getMarkerWidthHeightValues() {
/* 120 */     float markerWidth = 2.25F;
/* 121 */     if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.MARKER_WIDTH)) {
/* 122 */       String markerWidthRawValue = this.attributesAndStyles.get(SvgConstants.Attributes.MARKER_WIDTH);
/* 123 */       markerWidth = CssUtils.parseAbsoluteLength(markerWidthRawValue);
/*     */     } 
/* 125 */     float markerHeight = 2.25F;
/* 126 */     if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.MARKER_HEIGHT)) {
/* 127 */       String markerHeightRawValue = this.attributesAndStyles.get(SvgConstants.Attributes.MARKER_HEIGHT);
/* 128 */       markerHeight = CssUtils.parseAbsoluteLength(markerHeightRawValue);
/*     */     } 
/* 130 */     return new float[] { markerWidth, markerHeight };
/*     */   }
/*     */   
/*     */   private static boolean markerWidthHeightAreCorrect(MarkerSvgNodeRenderer namedObject) {
/* 134 */     Logger log = LoggerFactory.getLogger(MarkerSvgNodeRenderer.class);
/* 135 */     String markerWidth = namedObject.getAttribute(SvgConstants.Attributes.MARKER_WIDTH);
/* 136 */     String markerHeight = namedObject.getAttribute(SvgConstants.Attributes.MARKER_HEIGHT);
/* 137 */     boolean isCorrect = true;
/* 138 */     if (markerWidth != null) {
/* 139 */       float absoluteMarkerWidthValue = CssUtils.parseAbsoluteLength(markerWidth);
/* 140 */       if (absoluteMarkerWidthValue == 0.0F) {
/* 141 */         log.warn("markerWidth has zero value. Marker will not be rendered.");
/* 142 */         isCorrect = false;
/* 143 */       } else if (absoluteMarkerWidthValue < 0.0F) {
/* 144 */         log.warn("markerWidth has negative value. Marker will not be rendered.");
/* 145 */         isCorrect = false;
/*     */       } 
/*     */     } 
/* 148 */     if (markerHeight != null) {
/* 149 */       float absoluteMarkerHeightValue = CssUtils.parseAbsoluteLength(markerHeight);
/* 150 */       if (absoluteMarkerHeightValue == 0.0F) {
/* 151 */         log.warn("markerHeight has zero value. Marker will not be rendered.");
/* 152 */         isCorrect = false;
/* 153 */       } else if (absoluteMarkerHeightValue < 0.0F) {
/* 154 */         log.warn("markerHeight has negative value. Marker will not be rendered.");
/* 155 */         isCorrect = false;
/*     */       } 
/*     */     } 
/* 158 */     return isCorrect;
/*     */   }
/*     */   
/*     */   private void applyRotation(SvgDrawContext context) {
/* 162 */     if (this.attributesAndStyles.containsKey("orient")) {
/* 163 */       String orient = this.attributesAndStyles.get("orient");
/* 164 */       double rotAngle = Double.NaN;
/*     */ 
/*     */ 
/*     */       
/* 168 */       if ("auto".equals(orient) || ("auto-start-reverse".equals(orient) && 
/*     */         
/* 170 */         !"marker-start".equals(this.attributesAndStyles.get("marker")))) {
/* 171 */         rotAngle = ((IMarkerCapable)getParent()).getAutoOrientAngle(this, false);
/* 172 */       } else if ("auto-start-reverse".equals(orient) && "marker-start"
/* 173 */         .equals(this.attributesAndStyles.get("marker"))) {
/* 174 */         rotAngle = ((IMarkerCapable)getParent()).getAutoOrientAngle(this, true);
/* 175 */       } else if (CssUtils.isAngleValue(orient) || CssUtils.isNumericValue(orient)) {
/* 176 */         rotAngle = CssUtils.parseAngle(this.attributesAndStyles.get("orient"));
/*     */       } 
/* 178 */       if (!Double.isNaN(rotAngle)) {
/* 179 */         context.getCurrentCanvas().concatMatrix(AffineTransform.getRotateInstance(rotAngle));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyUserSpaceScaling(SvgDrawContext context) {
/* 185 */     if (!this.attributesAndStyles.containsKey(SvgConstants.Attributes.MARKER_UNITS) || "strokeWidth"
/*     */       
/* 187 */       .equals(this.attributesAndStyles.get(SvgConstants.Attributes.MARKER_UNITS))) {
/* 188 */       String parentValue = getParent().getAttribute("stroke-width");
/* 189 */       if (parentValue != null) {
/*     */         
/* 191 */         double rootViewPortHeight = context.getRootViewPort().getHeight();
/* 192 */         double rootViewPortWidth = context.getRootViewPort().getWidth();
/* 193 */         double viewBoxDiagonalLength = CssUtils.convertPxToPts(
/* 194 */             Math.sqrt(rootViewPortHeight * rootViewPortHeight + rootViewPortWidth * rootViewPortWidth));
/*     */         
/* 196 */         float strokeWidthScale = CssUtils.convertPtsToPx(parseAbsoluteLength(parentValue, (float)viewBoxDiagonalLength, 1.0F, context));
/* 197 */         context.getCurrentCanvas()
/* 198 */           .concatMatrix(AffineTransform.getScaleInstance(strokeWidthScale, strokeWidthScale));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyCoordinatesTranslation(SvgDrawContext context) {
/* 204 */     float xScale = 1.0F;
/* 205 */     float yScale = 1.0F;
/* 206 */     if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.VIEWBOX)) {
/*     */       
/* 208 */       String viewBoxValues = this.attributesAndStyles.get(SvgConstants.Attributes.VIEWBOX);
/* 209 */       List<String> valueStrings = SvgCssUtils.splitValueList(viewBoxValues);
/* 210 */       float[] viewBox = getViewBoxValues();
/* 211 */       xScale = context.getCurrentViewPort().getWidth() / viewBox[2];
/* 212 */       yScale = context.getCurrentViewPort().getHeight() / viewBox[3];
/*     */     } 
/* 214 */     float moveX = 0.0F;
/* 215 */     if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.REFX)) {
/* 216 */       String refX = this.attributesAndStyles.get(SvgConstants.Attributes.REFX);
/* 217 */       moveX = parseAbsoluteLength(refX, context.getRootViewPort().getWidth(), moveX, context);
/*     */       
/* 219 */       moveX *= -1.0F * xScale;
/*     */     } 
/* 221 */     float moveY = 0.0F;
/* 222 */     if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.REFY)) {
/* 223 */       String refY = this.attributesAndStyles.get(SvgConstants.Attributes.REFY);
/* 224 */       moveY = parseAbsoluteLength(refY, context.getRootViewPort().getHeight(), moveY, context);
/* 225 */       moveY *= -1.0F * yScale;
/*     */     } 
/* 227 */     AffineTransform translation = AffineTransform.getTranslateInstance(moveX, moveY);
/* 228 */     if (!translation.isIdentity()) {
/* 229 */       context.getCurrentCanvas().concatMatrix(translation);
/*     */     }
/*     */   }
/*     */   
/*     */   private float[] getViewBoxValues(float defaultWidth, float defaultHeight) {
/*     */     float[] values;
/* 235 */     if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.VIEWBOX)) {
/*     */       
/* 237 */       values = getViewBoxValues();
/*     */     } else {
/*     */       
/* 240 */       values = new float[4];
/* 241 */       values[0] = 0.0F;
/* 242 */       values[1] = 0.0F;
/* 243 */       values[2] = defaultWidth;
/* 244 */       values[3] = defaultHeight;
/*     */     } 
/* 246 */     return values;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/MarkerSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */