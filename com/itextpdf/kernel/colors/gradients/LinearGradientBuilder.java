/*    */ package com.itextpdf.kernel.colors.gradients;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.AffineTransform;
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
/*    */ public class LinearGradientBuilder
/*    */   extends AbstractLinearGradientBuilder
/*    */ {
/* 35 */   private final Point[] coordinates = new Point[] { new Point(), new Point() };
/* 36 */   private AffineTransform transformation = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LinearGradientBuilder setGradientVector(double x0, double y0, double x1, double y1) {
/* 54 */     this.coordinates[0].setLocation(x0, y0);
/* 55 */     this.coordinates[1].setLocation(x1, y1);
/* 56 */     return this;
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
/*    */ 
/*    */   
/*    */   public LinearGradientBuilder setCurrentSpaceToGradientVectorSpaceTransformation(AffineTransform transformation) {
/* 71 */     this.transformation = transformation;
/* 72 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Point[] getGradientVector(Rectangle targetBoundingBox, AffineTransform contextTransform) {
/* 77 */     return new Point[] { this.coordinates[0].getLocation(), this.coordinates[1].getLocation() };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AffineTransform getCurrentSpaceToGradientVectorSpaceTransformation(Rectangle targetBoundingBox, AffineTransform contextTransform) {
/* 83 */     return this.transformation;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/gradients/LinearGradientBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */