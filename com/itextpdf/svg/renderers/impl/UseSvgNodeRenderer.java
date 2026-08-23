/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.NoninvertibleTransformException;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import com.itextpdf.svg.css.impl.SvgNodeRendererInheritanceResolver;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.SvgTextUtil;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UseSvgNodeRenderer
/*     */   extends AbstractSvgNodeRenderer
/*     */ {
/*     */   protected void doDraw(SvgDrawContext context) {
/*  64 */     if (this.attributesAndStyles != null) {
/*  65 */       String elementToReUse = this.attributesAndStyles.get("xlink:href");
/*     */       
/*  67 */       if (elementToReUse == null) {
/*  68 */         elementToReUse = this.attributesAndStyles.get("href");
/*     */       }
/*     */       
/*  71 */       if (elementToReUse != null && !elementToReUse.isEmpty() && isValidHref(elementToReUse)) {
/*  72 */         String normalizedName = SvgTextUtil.filterReferenceValue(elementToReUse);
/*  73 */         if (!context.isIdUsedByUseTagBefore(normalizedName)) {
/*  74 */           ISvgNodeRenderer template = context.getNamedObject(normalizedName);
/*     */           
/*  76 */           ISvgNodeRenderer namedObject = (template == null) ? null : template.createDeepCopy();
/*     */           
/*  78 */           SvgNodeRendererInheritanceResolver iresolver = new SvgNodeRendererInheritanceResolver();
/*  79 */           iresolver.applyInheritanceToSubTree(this, namedObject);
/*     */           
/*  81 */           if (namedObject != null) {
/*  82 */             if (namedObject instanceof AbstractSvgNodeRenderer) {
/*  83 */               ((AbstractSvgNodeRenderer)namedObject).setPartOfClipPath(this.partOfClipPath);
/*     */             }
/*  85 */             PdfCanvas currentCanvas = context.getCurrentCanvas();
/*     */             
/*  87 */             float x = 0.0F;
/*  88 */             float y = 0.0F;
/*     */             
/*  90 */             if (this.attributesAndStyles.containsKey("x")) {
/*  91 */               x = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("x"));
/*     */             }
/*     */             
/*  94 */             if (this.attributesAndStyles.containsKey("y")) {
/*  95 */               y = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("y"));
/*     */             }
/*  97 */             AffineTransform inverseMatrix = null;
/*  98 */             if (!CssUtils.compareFloats(x, 0.0F) || !CssUtils.compareFloats(y, 0.0F)) {
/*  99 */               AffineTransform translation = AffineTransform.getTranslateInstance(x, y);
/* 100 */               currentCanvas.concatMatrix(translation);
/* 101 */               if (this.partOfClipPath) {
/*     */                 try {
/* 103 */                   inverseMatrix = translation.createInverse();
/* 104 */                 } catch (NoninvertibleTransformException ex) {
/* 105 */                   LoggerFactory.getLogger(UseSvgNodeRenderer.class)
/* 106 */                     .warn("Non-invertible transformation matrix was used in a clipping path context. Clipped elements may show undefined behavior.", (Throwable)ex);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 112 */             namedObject.setParent(this);
/* 113 */             namedObject.draw(context);
/*     */             
/* 115 */             namedObject.setParent(null);
/* 116 */             if (inverseMatrix != null) {
/* 117 */               currentCanvas.concatMatrix(inverseMatrix);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void postDraw(SvgDrawContext context) {}
/*     */   
/*     */   private boolean isValidHref(String name) {
/* 128 */     return name.startsWith("#");
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer createDeepCopy() {
/* 133 */     UseSvgNodeRenderer copy = new UseSvgNodeRenderer();
/* 134 */     deepCopyAttributesAndStyles(copy);
/* 135 */     return copy;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/UseSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */