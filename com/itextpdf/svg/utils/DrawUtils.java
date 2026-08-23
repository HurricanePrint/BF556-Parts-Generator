/*    */ package com.itextpdf.svg.utils;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DrawUtils
/*    */ {
/*    */   @Deprecated
/*    */   public static void arc(float x1, float y1, float x2, float y2, float startAng, float extent, PdfCanvas cv) {
/* 73 */     arc(x1, y1, x2, y2, startAng, extent, cv);
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
/*    */   
/*    */   public static void arc(double x1, double y1, double x2, double y2, double startAng, double extent, PdfCanvas cv) {
/* 89 */     List<double[]> ar = PdfCanvas.bezierArc(x1, y1, x2, y2, startAng, extent);
/* 90 */     if (!ar.isEmpty())
/* 91 */       for (double[] pt : ar)
/* 92 */         cv.curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);  
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/utils/DrawUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */