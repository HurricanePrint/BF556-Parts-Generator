/*    */ package com.itextpdf.layout.renderer;
/*    */ 
/*    */ import com.itextpdf.io.util.NumberUtil;
/*    */ import com.itextpdf.layout.property.OverflowPropertyValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFormattingContextUtil
/*    */ {
/*    */   public static boolean isRendererCreateBfc(IRenderer renderer) {
/* 68 */     return (renderer instanceof RootRenderer || renderer instanceof CellRenderer || 
/*    */       
/* 70 */       isInlineBlock(renderer) || 
/* 71 */       FloatingHelper.isRendererFloating(renderer) || 
/* 72 */       isAbsolutePosition(renderer) || 
/* 73 */       isFixedPosition(renderer) || 
/* 74 */       isCaption(renderer) || 
/* 75 */       AbstractRenderer.isOverflowProperty(OverflowPropertyValue.HIDDEN, renderer, 103) || 
/* 76 */       AbstractRenderer.isOverflowProperty(OverflowPropertyValue.HIDDEN, renderer, 104));
/*    */   }
/*    */   
/*    */   private static boolean isInlineBlock(IRenderer renderer) {
/* 80 */     return (renderer.getParent() instanceof LineRenderer && (renderer instanceof BlockRenderer || renderer instanceof TableRenderer));
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean isAbsolutePosition(IRenderer renderer) {
/* 85 */     Integer positioning = NumberUtil.asInteger(renderer.getProperty(52));
/* 86 */     return Integer.valueOf(3).equals(positioning);
/*    */   }
/*    */   
/*    */   private static boolean isFixedPosition(IRenderer renderer) {
/* 90 */     Integer positioning = NumberUtil.asInteger(renderer.getProperty(52));
/* 91 */     return Integer.valueOf(4).equals(positioning);
/*    */   }
/*    */   
/*    */   private static boolean isCaption(IRenderer renderer) {
/* 95 */     return (renderer.getParent() instanceof TableRenderer && renderer instanceof DivRenderer);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/BlockFormattingContextUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */