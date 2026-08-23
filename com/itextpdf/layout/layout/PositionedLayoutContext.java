/*    */ package com.itextpdf.layout.layout;
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
/*    */ public class PositionedLayoutContext
/*    */   extends LayoutContext
/*    */ {
/*    */   private LayoutArea parentOccupiedArea;
/*    */   
/*    */   public PositionedLayoutContext(LayoutArea area, LayoutArea parentOccupiedArea) {
/* 50 */     super(area);
/* 51 */     this.parentOccupiedArea = parentOccupiedArea;
/*    */   }
/*    */   
/*    */   public LayoutArea getParentOccupiedArea() {
/* 55 */     return this.parentOccupiedArea;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/PositionedLayoutContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */