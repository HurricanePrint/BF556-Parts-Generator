/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
/*     */ import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import com.itextpdf.layout.renderer.TextRenderer;
/*     */ import com.itextpdf.layout.tagging.IAccessibleElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Text
/*     */   extends AbstractElement<Text>
/*     */   implements ILeafElement, IAccessibleElement
/*     */ {
/*     */   protected String text;
/*     */   protected DefaultAccessibilityProperties tagProperties;
/*     */   
/*     */   public Text(String text) {
/*  68 */     if (null == text) {
/*  69 */       throw new IllegalArgumentException();
/*     */     }
/*  71 */     this.text = text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/*  80 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/*  88 */     this.text = text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTextRise() {
/*  96 */     return ((Float)getProperty(72)).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Text setTextRise(float textRise) {
/* 105 */     setProperty(72, Float.valueOf(textRise));
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getHorizontalScaling() {
/* 115 */     return (Float)getProperty(29);
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
/*     */   public Text setSkew(float alpha, float beta) {
/* 128 */     alpha = (float)Math.tan(alpha * Math.PI / 180.0D);
/* 129 */     beta = (float)Math.tan(beta * Math.PI / 180.0D);
/* 130 */     setProperty(65, new float[] { alpha, beta });
/* 131 */     return this;
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
/*     */   public Text setHorizontalScaling(float horizontalScaling) {
/* 143 */     setProperty(29, Float.valueOf(horizontalScaling));
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getAccessibilityProperties() {
/* 149 */     if (this.tagProperties == null) {
/* 150 */       this.tagProperties = new DefaultAccessibilityProperties("Span");
/*     */     }
/* 152 */     return (AccessibilityProperties)this.tagProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IRenderer makeNewRenderer() {
/* 157 */     return (IRenderer)new TextRenderer(this, this.text);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/Text.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */