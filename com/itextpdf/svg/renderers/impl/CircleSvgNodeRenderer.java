/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*    */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
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
/*    */ public class CircleSvgNodeRenderer
/*    */   extends EllipseSvgNodeRenderer
/*    */ {
/*    */   protected boolean setParameters() {
/* 58 */     this.cx = 0.0F;
/* 59 */     this.cy = 0.0F;
/* 60 */     if (getAttribute("cx") != null) {
/* 61 */       this.cx = CssUtils.parseAbsoluteLength(getAttribute("cx"));
/*    */     }
/* 63 */     if (getAttribute("cy") != null) {
/* 64 */       this.cy = CssUtils.parseAbsoluteLength(getAttribute("cy"));
/*    */     }
/*    */     
/* 67 */     if (getAttribute("r") != null && 
/* 68 */       CssUtils.parseAbsoluteLength(getAttribute("r")) > 0.0F) {
/* 69 */       this.rx = CssUtils.parseAbsoluteLength(getAttribute("r"));
/* 70 */       this.ry = this.rx;
/*    */     } else {
/* 72 */       return false;
/*    */     } 
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 80 */     CircleSvgNodeRenderer copy = new CircleSvgNodeRenderer();
/* 81 */     deepCopyAttributesAndStyles(copy);
/* 82 */     return copy;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/CircleSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */