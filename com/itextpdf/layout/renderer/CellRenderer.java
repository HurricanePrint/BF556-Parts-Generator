/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.NoninvertibleTransformException;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.element.Cell;
/*     */ import com.itextpdf.layout.element.IElement;
/*     */ import com.itextpdf.layout.property.BorderCollapsePropertyValue;
/*     */ import com.itextpdf.layout.property.UnitValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CellRenderer
/*     */   extends BlockRenderer
/*     */ {
/*     */   public CellRenderer(Cell modelElement) {
/*  66 */     super((IElement)modelElement);
/*  67 */     assert modelElement != null;
/*  68 */     setProperty(60, Integer.valueOf(modelElement.getRowspan()));
/*  69 */     setProperty(16, Integer.valueOf(modelElement.getColspan()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IPropertyContainer getModelElement() {
/*  77 */     return super.getModelElement();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Float retrieveWidth(float parentBoxWidth) {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractRenderer createSplitRenderer(int layoutResult) {
/*  90 */     CellRenderer splitRenderer = (CellRenderer)getNextRenderer();
/*  91 */     splitRenderer.parent = this.parent;
/*  92 */     splitRenderer.modelElement = this.modelElement;
/*  93 */     splitRenderer.occupiedArea = this.occupiedArea;
/*  94 */     splitRenderer.isLastRendererForModelElement = false;
/*  95 */     splitRenderer.addAllProperties(getOwnProperties());
/*  96 */     return splitRenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractRenderer createOverflowRenderer(int layoutResult) {
/* 104 */     CellRenderer overflowRenderer = (CellRenderer)getNextRenderer();
/* 105 */     overflowRenderer.parent = this.parent;
/* 106 */     overflowRenderer.modelElement = this.modelElement;
/* 107 */     overflowRenderer.addAllProperties(getOwnProperties());
/* 108 */     return overflowRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBackground(DrawContext drawContext) {
/* 113 */     PdfCanvas canvas = drawContext.getCanvas();
/* 114 */     Matrix ctm = canvas.getGraphicsState().getCtm();
/*     */ 
/*     */     
/* 117 */     Float angle = getPropertyAsFloat(55);
/* 118 */     boolean avoidRotation = (null != angle && hasProperty(6));
/* 119 */     boolean restoreRotation = hasOwnProperty(55);
/* 120 */     if (avoidRotation) {
/* 121 */       AffineTransform transform = new AffineTransform(ctm.get(0), ctm.get(1), ctm.get(3), ctm.get(4), ctm.get(6), ctm.get(7));
/*     */       try {
/* 123 */         transform = transform.createInverse();
/* 124 */       } catch (NoninvertibleTransformException e) {
/* 125 */         throw new PdfException("A noninvertible matrix has been parsed. The behaviour is unpredictable.", e);
/*     */       } 
/* 127 */       transform.concatenate(new AffineTransform());
/* 128 */       canvas.concatMatrix(transform);
/* 129 */       setProperty(55, null);
/*     */     } 
/*     */     
/* 132 */     super.drawBackground(drawContext);
/*     */ 
/*     */     
/* 135 */     if (avoidRotation) {
/* 136 */       if (restoreRotation) {
/* 137 */         setProperty(55, angle);
/*     */       } else {
/* 139 */         deleteOwnProperty(55);
/*     */       } 
/* 141 */       canvas.concatMatrix(new AffineTransform(ctm.get(0), ctm.get(1), ctm.get(3), ctm.get(4), ctm.get(6), ctm.get(7)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBorder(DrawContext drawContext) {
/* 150 */     if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
/* 151 */       super.drawBorder(drawContext);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle applyBorderBox(Rectangle rect, Border[] borders, boolean reverse) {
/* 159 */     if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
/* 160 */       super.applyBorderBox(rect, borders, reverse);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 165 */     return rect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle applyMargins(Rectangle rect, UnitValue[] margins, boolean reverse) {
/* 171 */     if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
/* 172 */       applySpacings(rect, reverse);
/*     */     }
/* 174 */     return rect;
/*     */   }
/*     */   
/*     */   protected Rectangle applySpacings(Rectangle rect, boolean reverse) {
/* 178 */     if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
/* 179 */       Float verticalBorderSpacing = (Float)this.parent.getProperty(116);
/* 180 */       Float horizontalBorderSpacing = (Float)this.parent.getProperty(115);
/* 181 */       float[] cellSpacings = new float[4];
/* 182 */       for (int i = 0; i < cellSpacings.length; i++) {
/* 183 */         cellSpacings[i] = (0 == i % 2) ? ((null != verticalBorderSpacing) ? verticalBorderSpacing
/* 184 */           .floatValue() : 0.0F) : ((null != horizontalBorderSpacing) ? horizontalBorderSpacing
/* 185 */           .floatValue() : 0.0F);
/*     */       }
/* 187 */       applySpacings(rect, cellSpacings, reverse);
/*     */     } 
/*     */ 
/*     */     
/* 191 */     return rect;
/*     */   }
/*     */   
/*     */   protected Rectangle applySpacings(Rectangle rect, float[] spacings, boolean reverse) {
/* 195 */     if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
/* 196 */       rect.applyMargins(spacings[0] / 2.0F, spacings[1] / 2.0F, spacings[2] / 2.0F, spacings[3] / 2.0F, reverse);
/*     */     }
/*     */ 
/*     */     
/* 200 */     return rect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRenderer getNextRenderer() {
/* 208 */     return new CellRenderer((Cell)getModelElement());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/CellRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */