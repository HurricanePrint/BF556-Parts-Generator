/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.MarkerVertexType;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import com.itextpdf.svg.renderers.IMarkerCapable;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.SvgCoordinateUtils;
/*     */ import com.itextpdf.svg.utils.SvgCssUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolylineSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */   implements IMarkerCapable
/*     */ {
/*  72 */   protected List<Point> points = new ArrayList<>();
/*     */   
/*     */   protected List<Point> getPoints() {
/*  75 */     return this.points;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPoints(String pointsAttribute) {
/*  86 */     if (pointsAttribute == null) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     List<String> points = SvgCssUtils.splitValueList(pointsAttribute);
/*  91 */     if (points.size() % 2 != 0) {
/*  92 */       throw (new SvgProcessingException("Points attribute {0} on polyline tag does not contain a valid set of points"))
/*  93 */         .setMessageParams(new Object[] { pointsAttribute });
/*     */     }
/*     */     
/*  96 */     this.points.clear();
/*     */     int i;
/*  98 */     for (i = 0; i < points.size(); i += 2) {
/*  99 */       float x = CssUtils.parseAbsoluteLength(points.get(i));
/* 100 */       float y = CssUtils.parseAbsoluteLength(points.get(i + 1));
/* 101 */       this.points.add(new Point(x, y));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle getObjectBoundingBox(SvgDrawContext context) {
/* 107 */     setPoints(getAttribute("points"));
/* 108 */     if (this.points.size() > 1) {
/* 109 */       Point firstPoint = this.points.get(0);
/* 110 */       double minX = firstPoint.getX();
/* 111 */       double minY = firstPoint.getY();
/* 112 */       double maxX = minX;
/* 113 */       double maxY = minY;
/*     */       
/* 115 */       for (int i = 1; i < this.points.size(); i++) {
/* 116 */         Point current = this.points.get(i);
/*     */         
/* 118 */         double currentX = current.getX();
/* 119 */         minX = Math.min(minX, currentX);
/* 120 */         maxX = Math.max(maxX, currentX);
/*     */         
/* 122 */         double currentY = current.getY();
/* 123 */         minY = Math.min(minY, currentY);
/* 124 */         maxY = Math.max(maxY, currentY);
/*     */       } 
/*     */       
/* 127 */       double width = maxX - minX;
/* 128 */       double height = maxY - minY;
/*     */       
/* 130 */       return new Rectangle((float)minX, (float)minY, (float)width, (float)height);
/*     */     } 
/* 132 */     return super.getObjectBoundingBox(context);
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
/*     */   protected void doDraw(SvgDrawContext context) {
/* 144 */     String pointsAttribute = this.attributesAndStyles.containsKey("points") ? this.attributesAndStyles.get("points") : null;
/* 145 */     setPoints(pointsAttribute);
/*     */     
/* 147 */     PdfCanvas canvas = context.getCurrentCanvas();
/* 148 */     canvas.writeLiteral("% polyline\n");
/* 149 */     if (this.points.size() > 1) {
/* 150 */       Point currentPoint = this.points.get(0);
/* 151 */       canvas.moveTo(currentPoint.getX(), currentPoint.getY());
/* 152 */       for (int x = 1; x < this.points.size(); x++) {
/* 153 */         currentPoint = this.points.get(x);
/* 154 */         canvas.lineTo(currentPoint.getX(), currentPoint.getY());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/* 161 */     PolylineSvgNodeRenderer copy = new PolylineSvgNodeRenderer();
/* 162 */     deepCopyAttributesAndStyles(copy);
/* 163 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawMarker(SvgDrawContext context, MarkerVertexType markerVertexType) {
/* 168 */     Point point = null;
/* 169 */     if (MarkerVertexType.MARKER_START.equals(markerVertexType)) {
/* 170 */       point = this.points.get(0);
/* 171 */     } else if (MarkerVertexType.MARKER_END.equals(markerVertexType)) {
/* 172 */       point = this.points.get(this.points.size() - 1);
/*     */     } 
/* 174 */     if (point != null) {
/* 175 */       String moveX = SvgCssUtils.convertDoubleToString(CssUtils.convertPtsToPx(point.x));
/* 176 */       String moveY = SvgCssUtils.convertDoubleToString(CssUtils.convertPtsToPx(point.y));
/* 177 */       MarkerSvgNodeRenderer.drawMarker(context, moveX, moveY, markerVertexType, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAutoOrientAngle(MarkerSvgNodeRenderer marker, boolean reverse) {
/* 183 */     if (this.points.size() > 1) {
/* 184 */       Vector v = new Vector(0.0F, 0.0F, 0.0F);
/* 185 */       if ("marker-end".equals(marker.attributesAndStyles.get("marker"))) {
/* 186 */         Point lastPoint = this.points.get(this.points.size() - 1);
/* 187 */         Point secondToLastPoint = this.points.get(this.points.size() - 2);
/*     */         
/* 189 */         v = new Vector((float)(lastPoint.getX() - secondToLastPoint.getX()), (float)(lastPoint.getY() - secondToLastPoint.getY()), 0.0F);
/* 190 */       } else if ("marker-start"
/* 191 */         .equals(marker.attributesAndStyles.get("marker"))) {
/* 192 */         Point firstPoint = this.points.get(0);
/* 193 */         Point secondPoint = this.points.get(1);
/*     */         
/* 195 */         v = new Vector((float)(secondPoint.getX() - firstPoint.getX()), (float)(secondPoint.getY() - firstPoint.getY()), 0.0F);
/*     */       } 
/* 197 */       Vector xAxis = new Vector(1.0F, 0.0F, 0.0F);
/* 198 */       double rotAngle = SvgCoordinateUtils.calculateAngleBetweenTwoVectors(xAxis, v);
/* 199 */       return (v.get(1) >= 0.0F && !reverse) ? rotAngle : (rotAngle * -1.0D);
/*     */     } 
/* 201 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/PolylineSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */