/*    */ package com.itextpdf.kernel.pdf.canvas.parser.data;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Matrix;
/*    */ import com.itextpdf.kernel.geom.Path;
/*    */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClippingPathInfo
/*    */   extends AbstractRenderInfo
/*    */ {
/*    */   private Path path;
/*    */   private Matrix ctm;
/*    */   
/*    */   public ClippingPathInfo(CanvasGraphicsState gs, Path path, Matrix ctm) {
/* 59 */     super(gs);
/* 60 */     this.path = path;
/* 61 */     this.ctm = ctm;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Path getClippingPath() {
/* 68 */     return this.path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Matrix getCtm() {
/* 75 */     return this.ctm;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/data/ClippingPathInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */