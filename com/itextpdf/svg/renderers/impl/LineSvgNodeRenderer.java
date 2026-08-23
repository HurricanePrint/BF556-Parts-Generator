/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.MarkerVertexType;
/*     */ import com.itextpdf.svg.renderers.IMarkerCapable;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.SvgCoordinateUtils;
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
/*     */ public class LineSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */   implements IMarkerCapable
/*     */ {
/*  63 */   private float x1 = 0.0F;
/*  64 */   private float y1 = 0.0F;
/*  65 */   private float x2 = 0.0F;
/*  66 */   private float y2 = 0.0F;
/*     */ 
/*     */   
/*     */   public void doDraw(SvgDrawContext context) {
/*  70 */     PdfCanvas canvas = context.getCurrentCanvas();
/*  71 */     canvas.writeLiteral("% line\n");
/*     */     
/*  73 */     if (setParameterss()) {
/*  74 */       canvas.moveTo(this.x1, this.y1).lineTo(this.x2, this.y2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle getObjectBoundingBox(SvgDrawContext context) {
/*  80 */     if (setParameterss()) {
/*  81 */       float x = Math.min(this.x1, this.x2);
/*  82 */       float y = Math.min(this.y1, this.y2);
/*     */       
/*  84 */       float width = Math.abs(this.x1 - this.x2);
/*  85 */       float height = Math.abs(this.y1 - this.y2);
/*     */       
/*  87 */       return new Rectangle(x, y, width, height);
/*     */     } 
/*  89 */     return super.getObjectBoundingBox(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canElementFill() {
/*  95 */     return false;
/*     */   }
/*     */   
/*     */   float getAttribute(Map<String, String> attributes, String key) {
/*  99 */     String value = attributes.get(key);
/* 100 */     if (value != null && !value.isEmpty()) {
/* 101 */       return CssUtils.parseAbsoluteLength(attributes.get(key));
/*     */     }
/* 103 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/* 108 */     LineSvgNodeRenderer copy = new LineSvgNodeRenderer();
/* 109 */     deepCopyAttributesAndStyles(copy);
/* 110 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawMarker(SvgDrawContext context, MarkerVertexType markerVertexType) {
/* 115 */     String moveX = null;
/* 116 */     String moveY = null;
/* 117 */     if (MarkerVertexType.MARKER_START.equals(markerVertexType)) {
/* 118 */       moveX = this.attributesAndStyles.get("x1");
/* 119 */       moveY = this.attributesAndStyles.get("y1");
/* 120 */     } else if (MarkerVertexType.MARKER_END.equals(markerVertexType)) {
/* 121 */       moveX = this.attributesAndStyles.get("x2");
/* 122 */       moveY = this.attributesAndStyles.get("y2");
/*     */     } 
/* 124 */     if (moveX != null && moveY != null) {
/* 125 */       MarkerSvgNodeRenderer.drawMarker(context, moveX, moveY, markerVertexType, this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAutoOrientAngle(MarkerSvgNodeRenderer marker, boolean reverse) {
/* 133 */     Vector v = new Vector(getAttribute(this.attributesAndStyles, "x2") - getAttribute(this.attributesAndStyles, "x1"), getAttribute(this.attributesAndStyles, "y2") - getAttribute(this.attributesAndStyles, "y1"), 0.0F);
/*     */     
/* 135 */     Vector xAxis = new Vector(1.0F, 0.0F, 0.0F);
/* 136 */     double rotAngle = SvgCoordinateUtils.calculateAngleBetweenTwoVectors(xAxis, v);
/* 137 */     return (v.get(1) >= 0.0F && !reverse) ? rotAngle : (rotAngle * -1.0D);
/*     */   }
/*     */   
/*     */   private boolean setParameterss() {
/* 141 */     if (this.attributesAndStyles.size() > 0) {
/* 142 */       if (this.attributesAndStyles.containsKey("x1")) {
/* 143 */         this.x1 = getAttribute(this.attributesAndStyles, "x1");
/*     */       }
/*     */       
/* 146 */       if (this.attributesAndStyles.containsKey("y1")) {
/* 147 */         this.y1 = getAttribute(this.attributesAndStyles, "y1");
/*     */       }
/*     */       
/* 150 */       if (this.attributesAndStyles.containsKey("x2")) {
/* 151 */         this.x2 = getAttribute(this.attributesAndStyles, "x2");
/*     */       }
/*     */       
/* 154 */       if (this.attributesAndStyles.containsKey("y2")) {
/* 155 */         this.y2 = getAttribute(this.attributesAndStyles, "y2");
/*     */       }
/* 157 */       return true;
/*     */     } 
/* 159 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/LineSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */