/*     */ package com.itextpdf.kernel.colors.gradients;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.AffineTransform;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StrategyBasedLinearGradientBuilder
/*     */   extends AbstractLinearGradientBuilder
/*     */ {
/*  35 */   private double rotateVectorAngle = 0.0D;
/*  36 */   private GradientStrategy gradientStrategy = GradientStrategy.TO_BOTTOM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCentralRotationAngleStrategy = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StrategyBasedLinearGradientBuilder setGradientDirectionAsCentralRotationAngle(double radians) {
/*  54 */     this.rotateVectorAngle = radians;
/*  55 */     this.isCentralRotationAngleStrategy = true;
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StrategyBasedLinearGradientBuilder setGradientDirectionAsStrategy(GradientStrategy gradientStrategy) {
/*  66 */     this.gradientStrategy = (gradientStrategy != null) ? gradientStrategy : GradientStrategy.TO_BOTTOM;
/*  67 */     this.isCentralRotationAngleStrategy = false;
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRotateVectorAngle() {
/*  77 */     return this.rotateVectorAngle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientStrategy getGradientStrategy() {
/*  86 */     return this.gradientStrategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCentralRotationAngleStrategy() {
/*  95 */     return this.isCentralRotationAngleStrategy;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Point[] getGradientVector(Rectangle targetBoundingBox, AffineTransform contextTransform) {
/* 100 */     if (targetBoundingBox == null) {
/* 101 */       return null;
/*     */     }
/* 103 */     return this.isCentralRotationAngleStrategy ? 
/* 104 */       buildCentralRotationCoordinates(targetBoundingBox, this.rotateVectorAngle) : 
/* 105 */       buildCoordinatesWithGradientStrategy(targetBoundingBox, this.gradientStrategy);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Point[] buildCoordinatesWithGradientStrategy(Rectangle targetBoundingBox, GradientStrategy gradientStrategy) {
/* 110 */     double xCenter = (targetBoundingBox.getX() + targetBoundingBox.getWidth() / 2.0F);
/* 111 */     double yCenter = (targetBoundingBox.getY() + targetBoundingBox.getHeight() / 2.0F);
/* 112 */     switch (gradientStrategy) {
/*     */       case TO_TOP:
/* 114 */         return createCoordinates(xCenter, targetBoundingBox.getBottom(), xCenter, targetBoundingBox
/* 115 */             .getTop());
/*     */       case TO_LEFT:
/* 117 */         return createCoordinates(targetBoundingBox.getRight(), yCenter, targetBoundingBox.getLeft(), yCenter);
/*     */       
/*     */       case TO_RIGHT:
/* 120 */         return createCoordinates(targetBoundingBox.getLeft(), yCenter, targetBoundingBox.getRight(), yCenter);
/*     */       
/*     */       case TO_TOP_LEFT:
/* 123 */         return buildToCornerCoordinates(targetBoundingBox, new Point(targetBoundingBox
/* 124 */               .getRight(), targetBoundingBox.getTop()));
/*     */       case TO_TOP_RIGHT:
/* 126 */         return buildToCornerCoordinates(targetBoundingBox, new Point(targetBoundingBox
/* 127 */               .getRight(), targetBoundingBox.getBottom()));
/*     */       case TO_BOTTOM_RIGHT:
/* 129 */         return buildToCornerCoordinates(targetBoundingBox, new Point(targetBoundingBox
/* 130 */               .getLeft(), targetBoundingBox.getBottom()));
/*     */       case TO_BOTTOM_LEFT:
/* 132 */         return buildToCornerCoordinates(targetBoundingBox, new Point(targetBoundingBox
/* 133 */               .getLeft(), targetBoundingBox.getTop()));
/*     */     } 
/*     */ 
/*     */     
/* 137 */     return createCoordinates(xCenter, targetBoundingBox.getTop(), xCenter, targetBoundingBox
/* 138 */         .getBottom());
/*     */   }
/*     */ 
/*     */   
/*     */   private static Point[] buildCentralRotationCoordinates(Rectangle targetBoundingBox, double angle) {
/* 143 */     double xCenter = (targetBoundingBox.getX() + targetBoundingBox.getWidth() / 2.0F);
/* 144 */     AffineTransform rotateInstance = AffineTransform.getRotateInstance(angle, xCenter, (targetBoundingBox
/* 145 */         .getY() + targetBoundingBox.getHeight() / 2.0F));
/* 146 */     return buildCoordinates(targetBoundingBox, rotateInstance);
/*     */   }
/*     */   
/*     */   private static Point[] buildToCornerCoordinates(Rectangle targetBoundingBox, Point gradientCenterLineRightCorner) {
/* 150 */     AffineTransform transform = buildToCornerTransform(new Point((targetBoundingBox
/* 151 */           .getX() + targetBoundingBox.getWidth() / 2.0F), (targetBoundingBox
/* 152 */           .getY() + targetBoundingBox.getHeight() / 2.0F)), gradientCenterLineRightCorner);
/*     */     
/* 154 */     return buildCoordinates(targetBoundingBox, transform);
/*     */   }
/*     */   
/*     */   private static AffineTransform buildToCornerTransform(Point center, Point gradientCenterLineRightCorner) {
/* 158 */     double scale = 1.0D / center.distance(gradientCenterLineRightCorner);
/* 159 */     double sin = (gradientCenterLineRightCorner.getY() - center.getY()) * scale;
/* 160 */     double cos = (gradientCenterLineRightCorner.getX() - center.getX()) * scale;
/* 161 */     if (Math.abs(cos) < 1.0E-10D) {
/* 162 */       cos = 0.0D;
/* 163 */       sin = (sin > 0.0D) ? 1.0D : -1.0D;
/* 164 */     } else if (Math.abs(sin) < 1.0E-10D) {
/* 165 */       sin = 0.0D;
/* 166 */       cos = (cos > 0.0D) ? 1.0D : -1.0D;
/*     */     } 
/* 168 */     double m02 = center.getX() * (1.0D - cos) + center.getY() * sin;
/* 169 */     double m12 = center.getY() * (1.0D - cos) - center.getX() * sin;
/* 170 */     return new AffineTransform(cos, sin, -sin, cos, m02, m12);
/*     */   }
/*     */   
/*     */   private static Point[] buildCoordinates(Rectangle targetBoundingBox, AffineTransform transformation) {
/* 174 */     double xCenter = (targetBoundingBox.getX() + targetBoundingBox.getWidth() / 2.0F);
/* 175 */     Point start = transformation.transform(new Point(xCenter, targetBoundingBox.getBottom()), null);
/* 176 */     Point end = transformation.transform(new Point(xCenter, targetBoundingBox.getTop()), null);
/* 177 */     Point[] baseVector = { start, end };
/* 178 */     double[] targetDomain = evaluateCoveringDomain(baseVector, targetBoundingBox);
/* 179 */     return createCoordinatesForNewDomain(targetDomain, baseVector);
/*     */   }
/*     */   
/*     */   private static Point[] createCoordinates(double x1, double y1, double x2, double y2) {
/* 183 */     return new Point[] { new Point(x1, y1), new Point(x2, y2) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum GradientStrategy
/*     */   {
/* 193 */     TO_BOTTOM,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     TO_BOTTOM_LEFT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     TO_BOTTOM_RIGHT,
/*     */ 
/*     */ 
/*     */     
/* 209 */     TO_LEFT,
/*     */ 
/*     */ 
/*     */     
/* 213 */     TO_RIGHT,
/*     */ 
/*     */ 
/*     */     
/* 217 */     TO_TOP,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     TO_TOP_LEFT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     TO_TOP_RIGHT;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/gradients/StrategyBasedLinearGradientBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */