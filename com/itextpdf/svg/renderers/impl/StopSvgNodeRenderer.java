/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.kernel.colors.WebColors;
/*    */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*    */ import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
/*    */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*    */ import com.itextpdf.svg.renderers.SvgDrawContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StopSvgNodeRenderer
/*    */   extends NoDrawOperationSvgNodeRenderer
/*    */   implements INoDrawSvgNodeRenderer
/*    */ {
/*    */   public double getOffset() {
/* 46 */     Double offset = null;
/* 47 */     String offsetAttribute = getAttribute("offset");
/* 48 */     if (CssUtils.isPercentageValue(offsetAttribute)) {
/* 49 */       offset = Double.valueOf(CssUtils.parseRelativeValue(offsetAttribute, 1.0F));
/* 50 */     } else if (CssUtils.isNumericValue(offsetAttribute)) {
/* 51 */       offset = CssUtils.parseDouble(offsetAttribute);
/*    */     } 
/* 53 */     double result = (offset != null) ? offset.doubleValue() : 0.0D;
/* 54 */     return (result > 1.0D) ? 1.0D : ((result > 0.0D) ? result : 0.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float[] getStopColor() {
/* 64 */     float[] color = null;
/* 65 */     String colorValue = getAttribute("stop-color");
/* 66 */     if (colorValue != null) {
/* 67 */       color = WebColors.getRGBAColor(colorValue);
/*    */     }
/* 69 */     if (color == null) {
/* 70 */       color = WebColors.getRGBAColor("black");
/*    */     }
/* 72 */     return color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getStopOpacity() {
/* 81 */     Float result = null;
/* 82 */     String opacityValue = getAttribute("stop-opacity");
/* 83 */     if (opacityValue != null && !"none".equalsIgnoreCase(opacityValue)) {
/* 84 */       result = CssUtils.parseFloat(opacityValue);
/*    */     }
/* 86 */     return (result != null) ? result.floatValue() : 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 91 */     StopSvgNodeRenderer copy = new StopSvgNodeRenderer();
/* 92 */     deepCopyAttributesAndStyles(copy);
/* 93 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDraw(SvgDrawContext context) {
/* 98 */     throw new UnsupportedOperationException("Can't draw current SvgNodeRenderer.");
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/StopSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */