/*    */ package com.itextpdf.svg.utils;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Point;
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
/*    */ public class TextRectangle
/*    */   extends Rectangle
/*    */ {
/*    */   private static final long serialVersionUID = -1263921426258495543L;
/*    */   private float textBaseLineYCoordinate;
/*    */   
/*    */   public TextRectangle(float x, float y, float width, float height, float textBaseLineYCoordinate) {
/* 50 */     super(x, y, width, height);
/* 51 */     this.textBaseLineYCoordinate = textBaseLineYCoordinate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Point getTextBaseLineRightPoint() {
/* 60 */     return new Point(getRight(), this.textBaseLineYCoordinate);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/utils/TextRectangle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */