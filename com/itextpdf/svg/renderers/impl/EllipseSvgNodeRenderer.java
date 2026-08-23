/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.DrawUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EllipseSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */ {
/*     */   float cx;
/*     */   float cy;
/*     */   float rx;
/*     */   float ry;
/*     */   
/*     */   protected void doDraw(SvgDrawContext context) {
/*  62 */     PdfCanvas cv = context.getCurrentCanvas();
/*  63 */     cv.writeLiteral("% ellipse\n");
/*  64 */     if (setParameters()) {
/*     */       
/*  66 */       cv.moveTo(this.cx + this.rx, this.cy);
/*  67 */       DrawUtils.arc(this.cx - this.rx, this.cy - this.ry, this.cx + this.rx, this.cy + this.ry, 0.0D, 360.0D, cv);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle getObjectBoundingBox(SvgDrawContext context) {
/*  74 */     if (setParameters()) {
/*  75 */       return new Rectangle(this.cx - this.rx, this.cy - this.ry, this.rx + this.rx, this.ry + this.ry);
/*     */     }
/*  77 */     return super.getObjectBoundingBox(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean setParameters() {
/*  88 */     this.cx = 0.0F;
/*  89 */     this.cy = 0.0F;
/*  90 */     if (getAttribute("cx") != null) {
/*  91 */       this.cx = CssUtils.parseAbsoluteLength(getAttribute("cx"));
/*     */     }
/*  93 */     if (getAttribute("cy") != null) {
/*  94 */       this.cy = CssUtils.parseAbsoluteLength(getAttribute("cy"));
/*     */     }
/*     */     
/*  97 */     if (getAttribute("rx") != null && 
/*  98 */       CssUtils.parseAbsoluteLength(getAttribute("rx")) > 0.0F) {
/*  99 */       this.rx = CssUtils.parseAbsoluteLength(getAttribute("rx"));
/*     */     } else {
/*     */       
/* 102 */       return false;
/*     */     } 
/* 104 */     if (getAttribute("ry") != null && 
/* 105 */       CssUtils.parseAbsoluteLength(getAttribute("ry")) > 0.0F) {
/* 106 */       this.ry = CssUtils.parseAbsoluteLength(getAttribute("ry"));
/*     */     } else {
/*     */       
/* 109 */       return false;
/*     */     } 
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/* 116 */     EllipseSvgNodeRenderer copy = new EllipseSvgNodeRenderer();
/* 117 */     deepCopyAttributesAndStyles(copy);
/* 118 */     return copy;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/EllipseSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */