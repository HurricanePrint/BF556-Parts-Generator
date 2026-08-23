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
/*    */ public class LineTo
/*    */   extends AbstractPathShape
/*    */ {
/*    */   static final int ARGUMENT_SIZE = 2;
/*    */   
/*    */   public LineTo() {
/* 61 */     this(false);
/*    */   }
/*    */   
/*    */   public LineTo(boolean relative) {
/* 65 */     super(relative);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(PdfCanvas canvas) {
/* 70 */     float x = CssUtils.parseAbsoluteLength(this.coordinates[0]);
/* 71 */     float y = CssUtils.parseAbsoluteLength(this.coordinates[1]);
/* 72 */     canvas.lineTo(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCoordinates(String[] inputCoordinates, Point startPoint) {
/* 77 */     if (inputCoordinates.length != 2) {
/* 78 */       throw new IllegalArgumentException(MessageFormatUtil.format("(x y)+ parameters are expected for lineTo operator. Got: {0}", new Object[] { Arrays.toString(inputCoordinates) }));
/*    */     }
/* 80 */     this.coordinates = new String[] { inputCoordinates[0], inputCoordinates[1] };
/* 81 */     if (isRelative())
/* 82 */       this.coordinates = this.copier.makeCoordinatesAbsolute(this.coordinates, new double[] { startPoint.x, startPoint.y }); 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/renderers/path/impl/LineTo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */