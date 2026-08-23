/*    */ package com.itextpdf.layout.layout;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Rectangle;
/*    */ import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
/*    */ import java.util.List;
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
/*    */ public class LineLayoutContext
/*    */   extends LayoutContext
/*    */ {
/*    */   private boolean floatOverflowedToNextPageWithNothing = false;
/*    */   private float textIndent;
/*    */   
/*    */   public LineLayoutContext(LayoutArea area, MarginsCollapseInfo marginsCollapseInfo, List<Rectangle> floatedRendererAreas, boolean clippedHeight) {
/* 57 */     super(area, marginsCollapseInfo, floatedRendererAreas, clippedHeight);
/*    */   }
/*    */   
/*    */   public LineLayoutContext(LayoutContext layoutContext) {
/* 61 */     super(layoutContext.area, layoutContext.marginsCollapseInfo, layoutContext.floatRendererAreas, layoutContext.clippedHeight);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFloatOverflowedToNextPageWithNothing() {
/* 70 */     return this.floatOverflowedToNextPageWithNothing;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LineLayoutContext setFloatOverflowedToNextPageWithNothing(boolean floatOverflowedToNextPageWithNothing) {
/* 79 */     this.floatOverflowedToNextPageWithNothing = floatOverflowedToNextPageWithNothing;
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getTextIndent() {
/* 88 */     return this.textIndent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LineLayoutContext setTextIndent(float textIndent) {
/* 97 */     this.textIndent = textIndent;
/* 98 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/LineLayoutContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */