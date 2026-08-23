/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
/*     */ import com.itextpdf.svg.SvgConstants;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */ {
/*     */   public ISvgNodeRenderer createDeepCopy() {
/*  63 */     ImageSvgNodeRenderer copy = new ImageSvgNodeRenderer();
/*  64 */     deepCopyAttributesAndStyles(copy);
/*  65 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDraw(SvgDrawContext context) {
/*  70 */     ResourceResolver resourceResolver = context.getResourceResolver();
/*     */     
/*  72 */     if (resourceResolver == null || this.attributesAndStyles == null) {
/*     */       return;
/*     */     }
/*  75 */     String uri = this.attributesAndStyles.get("xlink:href");
/*  76 */     PdfXObject xObject = resourceResolver.retrieveImageExtended(uri);
/*     */     
/*  78 */     if (xObject == null) {
/*     */       return;
/*     */     }
/*  81 */     PdfCanvas currentCanvas = context.getCurrentCanvas();
/*     */     
/*  83 */     float x = 0.0F;
/*  84 */     if (this.attributesAndStyles.containsKey("x")) {
/*  85 */       x = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("x"));
/*     */     }
/*     */     
/*  88 */     float y = 0.0F;
/*  89 */     if (this.attributesAndStyles.containsKey("y")) {
/*  90 */       y = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("y"));
/*     */     }
/*     */     
/*  93 */     float width = 0.0F;
/*     */     
/*  95 */     if (this.attributesAndStyles.containsKey("width")) {
/*  96 */       width = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("width"));
/*     */     }
/*     */     
/*  99 */     float height = 0.0F;
/*     */     
/* 101 */     if (this.attributesAndStyles.containsKey("height")) {
/* 102 */       height = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("height"));
/*     */     }
/*     */     
/* 105 */     String preserveAspectRatio = "";
/*     */     
/* 107 */     if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.PRESERVE_ASPECT_RATIO)) {
/* 108 */       preserveAspectRatio = this.attributesAndStyles.get(SvgConstants.Attributes.PRESERVE_ASPECT_RATIO);
/*     */     }
/*     */     
/* 111 */     preserveAspectRatio = preserveAspectRatio.toLowerCase();
/* 112 */     if (!"none".equals(preserveAspectRatio) && width != 0.0F && height != 0.0F) {
/*     */       float normalizedWidth, normalizedHeight;
/*     */       
/* 115 */       if (xObject.getWidth() / width > xObject.getHeight() / height) {
/* 116 */         normalizedWidth = width;
/* 117 */         normalizedHeight = xObject.getHeight() / xObject.getWidth() * width;
/*     */       } else {
/* 119 */         normalizedWidth = xObject.getWidth() / xObject.getHeight() * height;
/* 120 */         normalizedHeight = height;
/*     */       } 
/*     */       
/* 123 */       switch (preserveAspectRatio.toLowerCase()) {
/*     */         case "xminymin":
/*     */           break;
/*     */         case "xminymid":
/* 127 */           y += Math.abs(normalizedHeight - height) / 2.0F;
/*     */           break;
/*     */         case "xminymax":
/* 130 */           y += Math.abs(normalizedHeight - height);
/*     */           break;
/*     */         case "xmidymin":
/* 133 */           x += Math.abs(normalizedWidth - width) / 2.0F;
/*     */           break;
/*     */         case "xmidymax":
/* 136 */           x += Math.abs(normalizedWidth - width) / 2.0F;
/* 137 */           y += Math.abs(normalizedHeight - height);
/*     */           break;
/*     */         case "xmaxymin":
/* 140 */           x += Math.abs(normalizedWidth - width);
/*     */           break;
/*     */         case "xmaxymid":
/* 143 */           x += Math.abs(normalizedWidth - width);
/* 144 */           y += Math.abs(normalizedHeight - height) / 2.0F;
/*     */           break;
/*     */         case "xmaxymax":
/* 147 */           x += Math.abs(normalizedWidth - width);
/* 148 */           y += Math.abs(normalizedHeight - height);
/*     */           break;
/*     */         
/*     */         default:
/* 152 */           x += Math.abs(normalizedWidth - width) / 2.0F;
/* 153 */           y += Math.abs(normalizedHeight - height) / 2.0F;
/*     */           break;
/*     */       } 
/*     */       
/* 157 */       width = normalizedWidth;
/* 158 */       height = normalizedHeight;
/*     */     } 
/*     */     
/* 161 */     float v = y + height;
/* 162 */     currentCanvas.addXObject(xObject, width, 0.0F, 0.0F, -height, x, v);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/ImageSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */