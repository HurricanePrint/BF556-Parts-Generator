/*    */ package com.itextpdf.layout.renderer;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Rectangle;
/*    */ import com.itextpdf.layout.layout.RootLayoutArea;
/*    */ import java.util.ArrayList;
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
/*    */ class RootRendererAreaStateHandler
/*    */ {
/*    */   private RootLayoutArea storedPreviousArea;
/*    */   private RootLayoutArea storedNextArea;
/* 35 */   private List<Rectangle> storedPreviousFloatRenderAreas = null;
/* 36 */   private List<Rectangle> storedNextFloatRenderAreas = null;
/*    */   
/*    */   public boolean attemptGoBackToStoredPreviousStateAndStoreNextState(RootRenderer rootRenderer) {
/* 39 */     boolean result = false;
/* 40 */     if (this.storedPreviousArea != null) {
/* 41 */       this.storedNextArea = rootRenderer.currentArea;
/*    */       
/* 43 */       rootRenderer.currentArea = this.storedPreviousArea;
/* 44 */       rootRenderer.currentPageNumber = this.storedPreviousArea.getPageNumber();
/*    */       
/* 46 */       this.storedNextFloatRenderAreas = new ArrayList<>(rootRenderer.floatRendererAreas);
/* 47 */       rootRenderer.floatRendererAreas = this.storedPreviousFloatRenderAreas;
/*    */       
/* 49 */       this.storedPreviousFloatRenderAreas = null;
/* 50 */       this.storedPreviousArea = null;
/*    */       
/* 52 */       result = true;
/*    */     } 
/* 54 */     return result;
/*    */   }
/*    */   
/*    */   public boolean attemptGoForwardToStoredNextState(RootRenderer rootRenderer) {
/* 58 */     if (this.storedNextArea != null) {
/* 59 */       rootRenderer.currentArea = this.storedNextArea;
/* 60 */       rootRenderer.currentPageNumber = this.storedNextArea.getPageNumber();
/* 61 */       rootRenderer.floatRendererAreas = this.storedNextFloatRenderAreas;
/*    */       
/* 63 */       this.storedNextArea = null;
/* 64 */       this.storedNextFloatRenderAreas = null;
/* 65 */       return true;
/*    */     } 
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public RootRendererAreaStateHandler storePreviousState(RootRenderer rootRenderer) {
/* 72 */     this.storedPreviousArea = rootRenderer.currentArea;
/* 73 */     this.storedPreviousFloatRenderAreas = new ArrayList<>(rootRenderer.floatRendererAreas);
/* 74 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/RootRendererAreaStateHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */