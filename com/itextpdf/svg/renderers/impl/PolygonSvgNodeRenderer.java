/*    */ package com.itextpdf.svg.renderers.impl;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Point;
/*    */ import com.itextpdf.svg.renderers.IMarkerCapable;
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
/*    */ public class PolygonSvgNodeRenderer
/*    */   extends PolylineSvgNodeRenderer
/*    */   implements IMarkerCapable
/*    */ {
/*    */   protected void setPoints(String pointsAttribute) {
/* 60 */     super.setPoints(pointsAttribute);
/* 61 */     connectPoints();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void connectPoints() {
/* 68 */     if (this.points.size() < 2) {
/*    */       return;
/*    */     }
/*    */     
/* 72 */     Point start = this.points.get(0);
/* 73 */     Point end = this.points.get(this.points.size() - 1);
/* 74 */     if (Double.compare(start.x, end.x) != 0 || Double.compare(start.y, end.y) != 0) {
/* 75 */       this.points.add(new Point(start.x, start.y));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ISvgNodeRenderer createDeepCopy() {
/* 81 */     PolygonSvgNodeRenderer copy = new PolygonSvgNodeRenderer();
/* 82 */     deepCopyAttributesAndStyles(copy);
/* 83 */     return copy;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/impl/PolygonSvgNodeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */