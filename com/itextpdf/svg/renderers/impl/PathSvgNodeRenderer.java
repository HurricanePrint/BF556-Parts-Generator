/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.svg.MarkerVertexType;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import com.itextpdf.svg.renderers.IMarkerCapable;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.renderers.path.IPathShape;
/*     */ import com.itextpdf.svg.renderers.path.SvgPathShapeFactory;
/*     */ import com.itextpdf.svg.renderers.path.impl.AbstractPathShape;
/*     */ import com.itextpdf.svg.renderers.path.impl.ClosePath;
/*     */ import com.itextpdf.svg.renderers.path.impl.IControlPointCurve;
/*     */ import com.itextpdf.svg.utils.SvgCoordinateUtils;
/*     */ import com.itextpdf.svg.utils.SvgCssUtils;
/*     */ import com.itextpdf.svg.utils.SvgRegexUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */   implements IMarkerCapable
/*     */ {
/*     */   private static final String SPACE_CHAR = " ";
/*     */   private static final String INVALID_OPERATOR_REGEX = "(?:(?![mzlhvcsqtae])\\p{L})";
/*  89 */   private static Pattern invalidRegexPattern = Pattern.compile("(?:(?![mzlhvcsqtae])\\p{L})", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private static final Pattern SPLIT_PATTERN = Pattern.compile("(?=[mlhvcsqtaz])", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private Point currentPoint = new Point(0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private ClosePath zOperator = null;
/*     */ 
/*     */   
/*     */   public void doDraw(SvgDrawContext context) {
/* 124 */     PdfCanvas canvas = context.getCurrentCanvas();
/* 125 */     canvas.writeLiteral("% path\n");
/* 126 */     this.currentPoint = new Point(0, 0);
/* 127 */     for (IPathShape item : getShapes()) {
/* 128 */       item.draw(canvas);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/* 134 */     PathSvgNodeRenderer copy = new PathSvgNodeRenderer();
/* 135 */     deepCopyAttributesAndStyles(copy);
/* 136 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle getObjectBoundingBox(SvgDrawContext context) {
/* 141 */     Point lastPoint = null;
/* 142 */     Rectangle commonRectangle = null;
/* 143 */     for (IPathShape item : getShapes()) {
/* 144 */       if (lastPoint == null) {
/* 145 */         lastPoint = item.getEndingPoint();
/*     */       }
/*     */       
/* 148 */       if (item instanceof AbstractPathShape) {
/* 149 */         Rectangle rectangle = ((AbstractPathShape)item).getPathShapeRectangle(lastPoint);
/* 150 */         commonRectangle = Rectangle.getCommonRectangle(new Rectangle[] { commonRectangle, rectangle });
/*     */       } 
/* 152 */       lastPoint = item.getEndingPoint();
/*     */     } 
/* 154 */     return commonRectangle;
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
/*     */   private String[] getShapeCoordinates(IPathShape shape, IPathShape previousShape, String[] pathProperties) {
/* 166 */     if (shape instanceof ClosePath) {
/* 167 */       return null;
/*     */     }
/* 169 */     String[] shapeCoordinates = null;
/* 170 */     if (shape instanceof com.itextpdf.svg.renderers.path.impl.SmoothSCurveTo || shape instanceof com.itextpdf.svg.renderers.path.impl.QuadraticSmoothCurveTo) {
/* 171 */       String[] startingControlPoint = new String[2];
/* 172 */       if (previousShape != null) {
/* 173 */         Point previousEndPoint = previousShape.getEndingPoint();
/*     */         
/* 175 */         if (previousShape instanceof IControlPointCurve) {
/* 176 */           Point lastControlPoint = ((IControlPointCurve)previousShape).getLastControlPoint();
/* 177 */           float reflectedX = (float)(2.0D * previousEndPoint.getX() - lastControlPoint.getX());
/* 178 */           float reflectedY = (float)(2.0D * previousEndPoint.getY() - lastControlPoint.getY());
/*     */           
/* 180 */           startingControlPoint[0] = SvgCssUtils.convertFloatToString(reflectedX);
/* 181 */           startingControlPoint[1] = SvgCssUtils.convertFloatToString(reflectedY);
/*     */         } else {
/* 183 */           startingControlPoint[0] = SvgCssUtils.convertDoubleToString(previousEndPoint.getX());
/* 184 */           startingControlPoint[1] = SvgCssUtils.convertDoubleToString(previousEndPoint.getY());
/*     */         } 
/*     */       } else {
/* 187 */         throw new SvgProcessingException("The smooth curve operations (S, s, T, t) may not be used as a first operator in path.");
/*     */       } 
/* 189 */       shapeCoordinates = concatenate(startingControlPoint, pathProperties);
/*     */     } 
/* 191 */     if (shapeCoordinates == null) {
/* 192 */       shapeCoordinates = pathProperties;
/*     */     }
/* 194 */     return shapeCoordinates;
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
/*     */   private List<IPathShape> processPathOperator(String[] pathProperties, IPathShape previousShape) {
/* 207 */     List<IPathShape> shapes = new ArrayList<>();
/* 208 */     if (pathProperties.length == 0 || pathProperties[0].isEmpty() || 
/* 209 */       SvgPathShapeFactory.getArgumentCount(pathProperties[0]) < 0) {
/* 210 */       return shapes;
/*     */     }
/*     */     
/* 213 */     int argumentCount = SvgPathShapeFactory.getArgumentCount(pathProperties[0]);
/* 214 */     if (argumentCount == 0) {
/* 215 */       if (previousShape == null) {
/* 216 */         throw new SvgProcessingException("The close path operator (Z) may not be used before a move to operation (M)");
/*     */       }
/* 218 */       shapes.add(this.zOperator);
/* 219 */       this.currentPoint = this.zOperator.getEndingPoint();
/* 220 */       return shapes;
/*     */     }  int index;
/* 222 */     for (index = 1; index < pathProperties.length && 
/* 223 */       index + argumentCount <= pathProperties.length; index += argumentCount) {
/*     */ 
/*     */       
/* 226 */       IPathShape pathShape = SvgPathShapeFactory.createPathShape(pathProperties[0]);
/* 227 */       if (pathShape instanceof com.itextpdf.svg.renderers.path.impl.MoveTo) {
/* 228 */         shapes.addAll(addMoveToShapes(pathShape, pathProperties));
/* 229 */         return shapes;
/*     */       } 
/*     */       
/* 232 */       String[] shapeCoordinates = getShapeCoordinates(pathShape, previousShape, 
/* 233 */           Arrays.<String>copyOfRange(pathProperties, index, index + argumentCount));
/* 234 */       if (pathShape != null) {
/* 235 */         if (shapeCoordinates != null) {
/* 236 */           pathShape.setCoordinates(shapeCoordinates, this.currentPoint);
/*     */         }
/* 238 */         this.currentPoint = pathShape.getEndingPoint();
/* 239 */         shapes.add(pathShape);
/*     */       } 
/* 241 */       previousShape = pathShape;
/*     */     } 
/* 243 */     return shapes;
/*     */   }
/*     */   
/*     */   private List<IPathShape> addMoveToShapes(IPathShape pathShape, String[] pathProperties) {
/* 247 */     List<IPathShape> shapes = new ArrayList<>();
/* 248 */     int argumentCount = 2;
/* 249 */     String[] shapeCoordinates = getShapeCoordinates(pathShape, (IPathShape)null, Arrays.<String>copyOfRange(pathProperties, 1, 3));
/* 250 */     this.zOperator = new ClosePath(pathShape.isRelative());
/* 251 */     this.zOperator.setCoordinates(shapeCoordinates, this.currentPoint);
/* 252 */     pathShape.setCoordinates(shapeCoordinates, this.currentPoint);
/* 253 */     this.currentPoint = pathShape.getEndingPoint();
/* 254 */     shapes.add(pathShape);
/* 255 */     IPathShape previousShape = pathShape;
/* 256 */     if (pathProperties.length > 3) {
/* 257 */       int index; for (index = 3; index < pathProperties.length && 
/* 258 */         index + 2 <= pathProperties.length; index += argumentCount) {
/*     */ 
/*     */ 
/*     */         
/* 262 */         pathShape = pathShape.isRelative() ? SvgPathShapeFactory.createPathShape("l") : SvgPathShapeFactory.createPathShape("L");
/* 263 */         shapeCoordinates = getShapeCoordinates(pathShape, previousShape, 
/* 264 */             Arrays.<String>copyOfRange(pathProperties, index, index + 2));
/* 265 */         pathShape.setCoordinates(shapeCoordinates, previousShape.getEndingPoint());
/* 266 */         shapes.add(pathShape);
/* 267 */         previousShape = pathShape;
/*     */       } 
/*     */     } 
/* 270 */     return shapes;
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
/*     */   Collection<IPathShape> getShapes() {
/* 283 */     Collection<String> parsedResults = parsePathOperations();
/* 284 */     List<IPathShape> shapes = new ArrayList<>();
/*     */     
/* 286 */     for (String parsedResult : parsedResults) {
/* 287 */       String[] pathProperties = parsedResult.split(" +");
/* 288 */       IPathShape previousShape = (shapes.size() == 0) ? null : shapes.get(shapes.size() - 1);
/* 289 */       List<IPathShape> operatorShapes = processPathOperator(pathProperties, previousShape);
/* 290 */       shapes.addAll(operatorShapes);
/*     */     } 
/* 292 */     return shapes;
/*     */   }
/*     */   
/*     */   private static String[] concatenate(String[] first, String[] second) {
/* 296 */     String[] arr = new String[first.length + second.length];
/* 297 */     System.arraycopy(first, 0, arr, 0, first.length);
/* 298 */     System.arraycopy(second, 0, arr, first.length, second.length);
/* 299 */     return arr;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean containsInvalidAttributes(String attributes) {
/* 304 */     return SvgRegexUtils.containsAtLeastOneMatch(invalidRegexPattern, attributes);
/*     */   }
/*     */   
/*     */   Collection<String> parsePathOperations() {
/* 308 */     Collection<String> result = new ArrayList<>();
/* 309 */     String attributes = this.attributesAndStyles.get("d");
/* 310 */     if (attributes == null) {
/* 311 */       throw new SvgProcessingException("A Path object must have an attribute with the name 'd'.");
/*     */     }
/* 313 */     if (containsInvalidAttributes(attributes)) {
/* 314 */       throw (new SvgProcessingException("Invalid operators found in path data attribute: {0}"))
/* 315 */         .setMessageParams(new Object[] { attributes });
/*     */     }
/*     */     
/* 318 */     String[] operators = splitPathStringIntoOperators(attributes);
/*     */     
/* 320 */     for (String inst : operators) {
/* 321 */       String instTrim = inst.trim();
/* 322 */       if (!instTrim.isEmpty()) {
/* 323 */         char instruction = instTrim.charAt(0);
/* 324 */         String temp = instruction + " " + instTrim.substring(1).replace(",", " ").trim();
/*     */         
/* 326 */         temp = separateDecimalPoints(temp);
/* 327 */         result.add(temp);
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String separateDecimalPoints(String input) {
/* 340 */     StringBuilder res = new StringBuilder();
/*     */     
/* 342 */     boolean fractionalPartAfterDecimalPoint = false;
/*     */     
/* 344 */     boolean exponentSignMagnitude = false;
/* 345 */     for (int i = 0; i < input.length(); i++) {
/* 346 */       char c = input.charAt(i);
/*     */       
/* 348 */       if (c == '-' || Character.isWhitespace(c)) {
/* 349 */         fractionalPartAfterDecimalPoint = false;
/*     */       }
/* 351 */       if (Character.isWhitespace(c)) {
/* 352 */         exponentSignMagnitude = false;
/*     */       }
/*     */ 
/*     */       
/* 356 */       if (endsWithNonWhitespace(res) && ((c == '.' && fractionalPartAfterDecimalPoint) || (c == '-' && !exponentSignMagnitude)))
/*     */       {
/* 358 */         res.append(" ");
/*     */       }
/*     */       
/* 361 */       if (c == '.') {
/* 362 */         fractionalPartAfterDecimalPoint = true;
/* 363 */       } else if (c == 'e') {
/* 364 */         exponentSignMagnitude = true;
/*     */       } 
/*     */       
/* 367 */       res.append(c);
/*     */     } 
/* 369 */     return res.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String[] splitPathStringIntoOperators(String path) {
/* 377 */     return SPLIT_PATTERN.split(path);
/*     */   }
/*     */   
/*     */   private static boolean endsWithNonWhitespace(StringBuilder sb) {
/* 381 */     return (sb.length() > 0 && !Character.isWhitespace(sb.charAt(sb.length() - 1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawMarker(SvgDrawContext context, MarkerVertexType markerVertexType) {
/* 386 */     Object[] allShapesOrdered = getShapes().toArray();
/* 387 */     Point point = null;
/* 388 */     if (MarkerVertexType.MARKER_START.equals(markerVertexType)) {
/* 389 */       point = ((AbstractPathShape)allShapesOrdered[0]).getEndingPoint();
/* 390 */     } else if (MarkerVertexType.MARKER_END.equals(markerVertexType)) {
/*     */       
/* 392 */       point = ((AbstractPathShape)allShapesOrdered[allShapesOrdered.length - 1]).getEndingPoint();
/*     */     } 
/* 394 */     if (point != null) {
/* 395 */       String moveX = SvgCssUtils.convertDoubleToString(point.x);
/* 396 */       String moveY = SvgCssUtils.convertDoubleToString(point.y);
/* 397 */       MarkerSvgNodeRenderer.drawMarker(context, moveX, moveY, markerVertexType, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAutoOrientAngle(MarkerSvgNodeRenderer marker, boolean reverse) {
/* 403 */     Object[] pathShapes = getShapes().toArray();
/* 404 */     if (pathShapes.length > 1) {
/* 405 */       Vector v = new Vector(0.0F, 0.0F, 0.0F);
/* 406 */       if ("marker-end".equals(marker.attributesAndStyles.get("marker"))) {
/*     */         
/* 408 */         IPathShape lastShape = (IPathShape)pathShapes[pathShapes.length - 1];
/* 409 */         IPathShape secondToLastShape = (IPathShape)pathShapes[pathShapes.length - 2];
/*     */         
/* 411 */         v = new Vector((float)(lastShape.getEndingPoint().getX() - secondToLastShape.getEndingPoint().getX()), (float)(lastShape.getEndingPoint().getY() - secondToLastShape.getEndingPoint().getY()), 0.0F);
/*     */       }
/* 413 */       else if ("marker-start"
/* 414 */         .equals(marker.attributesAndStyles.get("marker"))) {
/*     */         
/* 416 */         IPathShape firstShape = (IPathShape)pathShapes[0];
/* 417 */         IPathShape secondShape = (IPathShape)pathShapes[1];
/*     */         
/* 419 */         v = new Vector((float)(secondShape.getEndingPoint().getX() - firstShape.getEndingPoint().getX()), (float)(secondShape.getEndingPoint().getY() - firstShape.getEndingPoint().getY()), 0.0F);
/*     */       } 
/*     */ 
/*     */       
/* 423 */       Vector xAxis = new Vector(1.0F, 0.0F, 0.0F);
/* 424 */       double rotAngle = SvgCoordinateUtils.calculateAngleBetweenTwoVectors(xAxis, v);
/* 425 */       return (v.get(1) >= 0.0F && !reverse) ? rotAngle : (rotAngle * -1.0D);
/*     */     } 
/* 427 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/PathSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */