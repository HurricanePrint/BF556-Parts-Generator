/*     */ package com.itextpdf.svg.renderers.impl;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.gradients.GradientSpreadMethod;
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.svg.SvgConstants;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*     */ import com.itextpdf.svg.utils.TransformUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public abstract class AbstractGradientSvgNodeRenderer
/*     */   extends NoDrawOperationSvgNodeRenderer
/*     */ {
/*     */   protected void doDraw(SvgDrawContext context) {
/*  50 */     throw new UnsupportedOperationException("Can't draw current SvgNodeRenderer.");
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
/*     */   
/*     */   public abstract Color createColor(SvgDrawContext paramSvgDrawContext, Rectangle paramRectangle, float paramFloat1, float paramFloat2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isObjectBoundingBoxUnits() {
/*  77 */     String gradientUnits = getAttribute(SvgConstants.Attributes.GRADIENT_UNITS);
/*  78 */     if ("userSpaceOnUse".equals(gradientUnits))
/*  79 */       return false; 
/*  80 */     if (gradientUnits != null && !"objectBoundingBox".equals(gradientUnits)) {
/*  81 */       LoggerFactory.getLogger(getClass()).warn(MessageFormatUtil.format("Could not recognize gradient units value {0}", new Object[] { gradientUnits }));
/*     */     }
/*     */     
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AffineTransform getGradientTransform() {
/*  92 */     String gradientTransform = getAttribute(SvgConstants.Attributes.GRADIENT_TRANSFORM);
/*  93 */     if (gradientTransform != null && !gradientTransform.isEmpty()) {
/*  94 */       return TransformUtils.parseTransform(gradientTransform);
/*     */     }
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<StopSvgNodeRenderer> getChildStopRenderers() {
/* 104 */     List<StopSvgNodeRenderer> stopRenderers = new ArrayList<>();
/* 105 */     for (ISvgNodeRenderer child : getChildren()) {
/* 106 */       if (child instanceof StopSvgNodeRenderer) {
/* 107 */         stopRenderers.add((StopSvgNodeRenderer)child);
/*     */       }
/*     */     } 
/* 110 */     return stopRenderers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GradientSpreadMethod parseSpreadMethod() {
/* 118 */     String spreadMethodValue = getAttribute(SvgConstants.Attributes.SPREAD_METHOD);
/* 119 */     if (spreadMethodValue == null)
/*     */     {
/* 121 */       return GradientSpreadMethod.PAD;
/*     */     }
/* 123 */     switch (spreadMethodValue) {
/*     */       case "pad":
/* 125 */         return GradientSpreadMethod.PAD;
/*     */       case "reflect":
/* 127 */         return GradientSpreadMethod.REFLECT;
/*     */       case "repeat":
/* 129 */         return GradientSpreadMethod.REPEAT;
/*     */     } 
/* 131 */     LoggerFactory.getLogger(getClass()).warn(MessageFormatUtil.format("Could not recognize gradient spread method value {0}", new Object[] { spreadMethodValue }));
/*     */     
/* 133 */     return GradientSpreadMethod.PAD;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/AbstractGradientSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */