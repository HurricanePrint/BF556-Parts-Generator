/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.svg.exceptions.SvgProcessingException;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfRootSvgNodeRenderer
/*     */   implements ISvgNodeRenderer
/*     */ {
/*     */   ISvgNodeRenderer subTreeRoot;
/*     */   
/*     */   public PdfRootSvgNodeRenderer(ISvgNodeRenderer subTreeRoot) {
/*  70 */     this.subTreeRoot = subTreeRoot;
/*  71 */     subTreeRoot.setParent(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(ISvgNodeRenderer parent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer getParent() {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(SvgDrawContext context) {
/*  88 */     context.addViewPort(calculateViewPort(context));
/*  89 */     PdfCanvas currentCanvas = context.getCurrentCanvas();
/*  90 */     currentCanvas.concatMatrix(calculateTransformation(context));
/*  91 */     currentCanvas.writeLiteral("% svg root\n");
/*  92 */     this.subTreeRoot.draw(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributesAndStyles(Map<String, String> attributesAndStyles) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String key) {
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String key, String value) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getAttributeMapCopy() {
/* 112 */     return null;
/*     */   }
/*     */   
/*     */   AffineTransform calculateTransformation(SvgDrawContext context) {
/* 116 */     Rectangle viewPort = context.getCurrentViewPort();
/* 117 */     float horizontal = viewPort.getX();
/* 118 */     float vertical = viewPort.getY() + viewPort.getHeight();
/*     */     
/* 120 */     AffineTransform transform = AffineTransform.getTranslateInstance(0.0D, 0.0D);
/* 121 */     transform.concatenate(AffineTransform.getTranslateInstance(horizontal, vertical));
/* 122 */     transform.concatenate(new AffineTransform(1.0D, 0.0D, 0.0D, -1.0D, 0.0D, 0.0D));
/*     */     
/* 124 */     return transform;
/*     */   }
/*     */   
/*     */   Rectangle calculateViewPort(SvgDrawContext context) {
/* 128 */     float portX = 0.0F;
/* 129 */     float portY = 0.0F;
/* 130 */     float portWidth = 0.0F;
/* 131 */     float portHeight = 0.0F;
/*     */     
/* 133 */     PdfStream contentStream = context.getCurrentCanvas().getContentStream();
/*     */     
/* 135 */     if (!contentStream.containsKey(PdfName.BBox)) {
/* 136 */       throw new SvgProcessingException("The root svg tag needs to have a bounding box defined.");
/*     */     }
/*     */     
/* 139 */     PdfArray bboxArray = contentStream.getAsArray(PdfName.BBox);
/*     */     
/* 141 */     portX = bboxArray.getAsNumber(0).floatValue();
/* 142 */     portY = bboxArray.getAsNumber(1).floatValue();
/* 143 */     portWidth = bboxArray.getAsNumber(2).floatValue() - portX;
/* 144 */     portHeight = bboxArray.getAsNumber(3).floatValue() - portY;
/*     */     
/* 146 */     return new Rectangle(portX, portY, portWidth, portHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/* 151 */     PdfRootSvgNodeRenderer copy = new PdfRootSvgNodeRenderer(this.subTreeRoot.createDeepCopy());
/* 152 */     return copy;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/PdfRootSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */