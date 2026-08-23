/*    */ package com.itextpdf.svg.renderers.path.impl;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
/*    */ import com.itextpdf.kernel.geom.Point;
/*    */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*    */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MoveTo
/*    */   extends AbstractPathShape
/*    */ {
/*    */   static final int ARGUMENT_SIZE = 2;
/*    */   
/*    */   public MoveTo() {
/* 63 */     this(false);
/*    */   }
/*    */   
/*    */   public MoveTo(boolean relative) {
/* 67 */     super(relative);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(PdfCanvas canvas) {
/* 72 */     float x = CssUtils.parseAbsoluteLength(this.coordinates[0]);
/* 73 */     float y = CssUtils.parseAbsoluteLength(this.coordinates[1]);
/* 74 */     canvas.moveTo(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCoordinates(String[] inputCoordinates, Point startPoint) {
/* 79 */     if (inputCoordinates.length != 2) {
/* 80 */       throw new IllegalArgumentException(MessageFormatUtil.format("(x y)+ parameters are expected for moveTo operator. Got: {0}", new Object[] { Arrays.toString(this.coordinates) }));
/*    */     }
/* 82 */     this.coordinates = new String[] { inputCoordinates[0], inputCoordinates[1] };
/* 83 */     if (isRelative())
/* 84 */       this.coordinates = this.copier.makeCoordinatesAbsolute(this.coordinates, new double[] { startPoint.x, startPoint.y }); 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/MoveTo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */