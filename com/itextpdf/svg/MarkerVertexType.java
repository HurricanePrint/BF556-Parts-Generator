/*    */ package com.itextpdf.svg;
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
/*    */ public enum MarkerVertexType
/*    */ {
/* 35 */   MARKER_START("marker-start"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   MARKER_MID("marker-mid"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   MARKER_END("marker-end");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   MarkerVertexType(String s) {
/* 50 */     this.name = s;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     return this.name;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/MarkerVertexType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */