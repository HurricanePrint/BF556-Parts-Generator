/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import java.util.HashMap;
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
/*     */ public class RectangleSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */ {
/*  61 */   private float x = 0.0F;
/*  62 */   private float y = 0.0F;
/*     */   private float width;
/*     */   private float height;
/*     */   private boolean rxPresent = false;
/*     */   private boolean ryPresent = false;
/*  67 */   private float rx = 0.0F;
/*  68 */   private float ry = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleSvgNodeRenderer() {
/*  74 */     this.attributesAndStyles = new HashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDraw(SvgDrawContext context) {
/*  79 */     PdfCanvas cv = context.getCurrentCanvas();
/*  80 */     cv.writeLiteral("% rect\n");
/*  81 */     setParameters();
/*  82 */     boolean singleValuePresent = ((this.rxPresent && !this.ryPresent) || (!this.rxPresent && this.ryPresent));
/*     */     
/*  84 */     if (!this.rxPresent && !this.ryPresent) {
/*  85 */       cv.rectangle(this.x, this.y, this.width, this.height);
/*  86 */     } else if (singleValuePresent) {
/*  87 */       cv.writeLiteral("% circle rounded rect\n");
/*     */       
/*  89 */       float radius = findCircularRadius(this.rx, this.ry, this.width, this.height);
/*  90 */       cv.roundRectangle(this.x, this.y, this.width, this.height, radius);
/*     */     } else {
/*  92 */       cv.writeLiteral("% ellipse rounded rect\n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       cv.moveTo((this.x + this.rx), this.y);
/* 113 */       cv.lineTo((this.x + this.width - this.rx), this.y);
/* 114 */       arc(this.x + this.width - 2.0F * this.rx, this.y, this.x + this.width, this.y + 2.0F * this.ry, -90.0F, 90.0F, cv);
/* 115 */       cv.lineTo((this.x + this.width), (this.y + this.height - this.ry));
/* 116 */       arc(this.x + this.width, this.y + this.height - 2.0F * this.ry, this.x + this.width - 2.0F * this.rx, this.y + this.height, 0.0F, 90.0F, cv);
/* 117 */       cv.lineTo((this.x + this.rx), (this.y + this.height));
/* 118 */       arc(this.x + 2.0F * this.rx, this.y + this.height, this.x, this.y + this.height - 2.0F * this.ry, 90.0F, 90.0F, cv);
/* 119 */       cv.lineTo(this.x, (this.y + this.ry));
/* 120 */       arc(this.x, this.y + 2.0F * this.ry, this.x + 2.0F * this.rx, this.y, 180.0F, 90.0F, cv);
/* 121 */       cv.closePath();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle getObjectBoundingBox(SvgDrawContext context) {
/* 127 */     setParameters();
/* 128 */     return new Rectangle(this.x, this.y, this.width, this.height);
/*     */   }
/*     */   
/*     */   private void setParameters() {
/* 132 */     if (getAttribute("x") != null) {
/* 133 */       this.x = CssUtils.parseAbsoluteLength(getAttribute("x"));
/*     */     }
/* 135 */     if (getAttribute("y") != null) {
/* 136 */       this.y = CssUtils.parseAbsoluteLength(getAttribute("y"));
/*     */     }
/* 138 */     this.width = CssUtils.parseAbsoluteLength(getAttribute("width"));
/* 139 */     this.height = CssUtils.parseAbsoluteLength(getAttribute("height"));
/*     */     
/* 141 */     if (this.attributesAndStyles.containsKey("rx")) {
/* 142 */       this.rx = checkRadius(CssUtils.parseAbsoluteLength(getAttribute("rx")), this.width);
/* 143 */       this.rxPresent = true;
/*     */     } 
/* 145 */     if (this.attributesAndStyles.containsKey("ry")) {
/* 146 */       this.ry = checkRadius(CssUtils.parseAbsoluteLength(getAttribute("ry")), this.height);
/* 147 */       this.ryPresent = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void arc(float x1, float y1, float x2, float y2, float startAng, float extent, PdfCanvas cv) {
/* 152 */     List<double[]> ar = PdfCanvas.bezierArc(x1, y1, x2, y2, startAng, extent);
/* 153 */     if (!ar.isEmpty())
/*     */     {
/* 155 */       for (int k = 0; k < ar.size(); k++) {
/* 156 */         double[] pt = ar.get(k);
/* 157 */         cv.curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float checkRadius(float radius, float distance) {
/* 169 */     if (radius <= 0.0F) {
/* 170 */       return 0.0F;
/*     */     }
/* 172 */     if (radius > distance / 2.0F) {
/* 173 */       return distance / 2.0F;
/*     */     }
/* 175 */     return radius;
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
/*     */   float findCircularRadius(float rx, float ry, float width, float height) {
/* 189 */     float maxRadius = Math.min(width, height) / 2.0F;
/* 190 */     float biggestRadius = Math.max(rx, ry);
/* 191 */     return Math.min(maxRadius, biggestRadius);
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/* 196 */     RectangleSvgNodeRenderer copy = new RectangleSvgNodeRenderer();
/* 197 */     deepCopyAttributesAndStyles(copy);
/* 198 */     return copy;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/RectangleSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */