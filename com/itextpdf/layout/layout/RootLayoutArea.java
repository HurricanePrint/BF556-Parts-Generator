/*    */ package com.itextpdf.layout.layout;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Rectangle;
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
/*    */ public class RootLayoutArea
/*    */   extends LayoutArea
/*    */   implements Cloneable
/*    */ {
/*    */   protected boolean emptyArea = true;
/*    */   
/*    */   public RootLayoutArea(int pageNumber, Rectangle bBox) {
/* 58 */     super(pageNumber, bBox);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmptyArea() {
/* 67 */     return this.emptyArea;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEmptyArea(boolean emptyArea) {
/* 76 */     this.emptyArea = emptyArea;
/*    */   }
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
/*    */   public LayoutArea clone() {
/* 89 */     RootLayoutArea area = (RootLayoutArea)super.clone();
/* 90 */     area.setEmptyArea(this.emptyArea);
/* 91 */     return area;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/RootLayoutArea.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */