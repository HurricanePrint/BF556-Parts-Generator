/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Rectangle;
/*    */ import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
/*    */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
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
/*    */ public abstract class AbstractContainerSvgNodeRenderer
/*    */   extends AbstractBranchSvgNodeRenderer
/*    */ {
/*    */   public float getCurrentFontSize() {
/* 35 */     String fontSizeValue = getAttribute("font-size");
/* 36 */     if (fontSizeValue == null) {
/* 37 */       fontSizeValue = CssDefaults.getDefaultValue("font-size");
/*    */     }
/* 39 */     return CssUtils.parseAbsoluteFontSize(fontSizeValue);
/*    */   }
/*    */   
/*    */   public boolean canConstructViewPort() {
/* 43 */     return true;
/*    */   }
/*    */   
/*    */   protected boolean canElementFill() {
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDraw(SvgDrawContext context) {
/* 52 */     context.addViewPort(calculateViewPort(context));
/* 53 */     super.doDraw(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Rectangle calculateViewPort(SvgDrawContext context) {
/* 63 */     Rectangle currentViewPort = context.getCurrentViewPort();
/*    */ 
/*    */     
/* 66 */     float portX = currentViewPort.getX();
/* 67 */     float portY = currentViewPort.getY();
/*    */     
/* 69 */     float portWidth = currentViewPort.getWidth();
/*    */     
/* 71 */     float portHeight = currentViewPort.getHeight();
/*    */ 
/*    */     
/* 74 */     if (this.attributesAndStyles != null) {
/* 75 */       if (this.attributesAndStyles.containsKey("x")) {
/* 76 */         portX = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("x"));
/*    */       }
/* 78 */       if (this.attributesAndStyles.containsKey("y")) {
/* 79 */         portY = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("y"));
/*    */       }
/* 81 */       if (this.attributesAndStyles.containsKey("width")) {
/* 82 */         portWidth = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("width"));
/*    */       }
/* 84 */       if (this.attributesAndStyles.containsKey("height")) {
/* 85 */         portHeight = CssUtils.parseAbsoluteLength(this.attributesAndStyles.get("height"));
/*    */       }
/*    */     } 
/*    */     
/* 89 */     return new Rectangle(portX, portY, portWidth, portHeight);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/AbstractContainerSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */