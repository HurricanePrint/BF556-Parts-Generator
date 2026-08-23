/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.property.RenderingMode;
/*     */ import com.itextpdf.layout.renderer.TextRenderer;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.SvgTextUtil;
/*     */ import com.itextpdf.svg.utils.TextRectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextLeafSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */   implements ISvgTextNodeRenderer, ISvgTextNodeHelper
/*     */ {
/*     */   public ISvgNodeRenderer createDeepCopy() {
/*  66 */     TextLeafSvgNodeRenderer copy = new TextLeafSvgNodeRenderer();
/*  67 */     deepCopyAttributesAndStyles(copy);
/*  68 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTextContentLength(float parentFontSize, PdfFont font) {
/*  74 */     float contentLength = 0.0F;
/*  75 */     if (font != null && this.attributesAndStyles != null && this.attributesAndStyles.containsKey("text_content")) {
/*     */       
/*  77 */       float fontSize = SvgTextUtil.resolveFontSize(this, parentFontSize);
/*  78 */       String content = this.attributesAndStyles.get("text_content");
/*  79 */       contentLength = font.getWidth(content, fontSize);
/*     */     } 
/*  81 */     return contentLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getRelativeTranslation() {
/*  86 */     return new float[] { 0.0F, 0.0F };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsRelativeMove() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAbsolutePositionChange() {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[][] getAbsolutePositionChanges() {
/* 101 */     float[] part = { 0.0F };
/* 102 */     return new float[][] { part, part };
/*     */   }
/*     */ 
/*     */   
/*     */   public TextRectangle getTextRectangle(SvgDrawContext context, Point basePoint) {
/* 107 */     if (getParent() instanceof TextSvgBranchRenderer && basePoint != null) {
/* 108 */       float parentFontSize = ((TextSvgBranchRenderer)getParent()).getFontSize();
/* 109 */       PdfFont parentFont = ((TextSvgBranchRenderer)getParent()).getFont();
/* 110 */       float textLength = getTextContentLength(parentFontSize, parentFont);
/*     */       
/* 112 */       float[] fontAscenderDescenderFromMetrics = TextRenderer.calculateAscenderDescender(parentFont, RenderingMode.HTML_MODE);
/* 113 */       float fontAscender = fontAscenderDescenderFromMetrics[0] / 1000.0F * parentFontSize;
/* 114 */       float fontDescender = fontAscenderDescenderFromMetrics[1] / 1000.0F * parentFontSize;
/*     */ 
/*     */       
/* 117 */       float textHeight = fontAscender - fontDescender;
/* 118 */       return new TextRectangle((float)basePoint.getX(), (float)basePoint.getY() - fontAscender, textLength, textHeight, 
/* 119 */           (float)basePoint.getY());
/*     */     } 
/* 121 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDraw(SvgDrawContext context) {
/* 127 */     if (this.attributesAndStyles != null && this.attributesAndStyles.containsKey("text_content")) {
/* 128 */       PdfCanvas currentCanvas = context.getCurrentCanvas();
/*     */       
/* 130 */       currentCanvas.moveText(context.getTextMove()[0], context.getTextMove()[1]);
/* 131 */       currentCanvas.showText(this.attributesAndStyles.get("text_content"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canElementFill() {
/* 137 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/TextLeafSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */