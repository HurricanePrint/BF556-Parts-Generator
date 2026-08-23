/*    */ package com.itextpdf.kernel.pdf.canvas.parser.data;
/*    */ 
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
/*    */ public class AbstractRenderInfo
/*    */   implements IEventData
/*    */ {
/*    */   protected CanvasGraphicsState gs;
/*    */   private boolean graphicsStateIsPreserved;
/*    */   
/*    */   public AbstractRenderInfo(CanvasGraphicsState gs) {
/* 54 */     this.gs = gs;
/*    */   }
/*    */ 
/*    */   
/*    */   public CanvasGraphicsState getGraphicsState() {
/* 59 */     checkGraphicsState();
/* 60 */     return this.graphicsStateIsPreserved ? this.gs : new CanvasGraphicsState(this.gs);
/*    */   }
/*    */   
/*    */   public boolean isGraphicsStatePreserved() {
/* 64 */     return this.graphicsStateIsPreserved;
/*    */   }
/*    */   
/*    */   public void preserveGraphicsState() {
/* 68 */     checkGraphicsState();
/* 69 */     this.graphicsStateIsPreserved = true;
/* 70 */     this.gs = new CanvasGraphicsState(this.gs);
/*    */   }
/*    */   
/*    */   public void releaseGraphicsState() {
/* 74 */     if (!this.graphicsStateIsPreserved) {
/* 75 */       this.gs = null;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void checkGraphicsState() {
/* 82 */     if (null == this.gs)
/* 83 */       throw new IllegalStateException("Graphics state is always deleted after event dispatching. If you want to preserve it in renderer info, use preserveGraphicsState method after receiving renderer info."); 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/data/AbstractRenderInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */