/*    */ package com.itextpdf.layout.renderer;
/*    */ 
/*    */ import com.itextpdf.kernel.font.PdfFont;
/*    */ import com.itextpdf.layout.property.LineHeight;
/*    */ import com.itextpdf.layout.property.RenderingMode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class LineHeightHelper
/*    */ {
/* 32 */   private static float DEFAULT_LINE_HEIGHT_COEFF = 1.15F;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static float[] getActualAscenderDescender(AbstractRenderer renderer) {
/* 40 */     float lineHeight = calculateLineHeight(renderer);
/* 41 */     float[] fontAscenderDescender = getFontAscenderDescenderNormalized(renderer);
/* 42 */     float leading = lineHeight - fontAscenderDescender[0] - fontAscenderDescender[1];
/* 43 */     float ascender = fontAscenderDescender[0] + leading / 2.0F;
/* 44 */     float descender = fontAscenderDescender[1] - leading / 2.0F;
/* 45 */     return new float[] { ascender, descender };
/*    */   }
/*    */   
/*    */   static float[] getFontAscenderDescenderNormalized(AbstractRenderer renderer) {
/* 49 */     PdfFont font = renderer.resolveFirstPdfFont();
/* 50 */     float fontSize = renderer.getPropertyAsUnitValue(24).getValue();
/* 51 */     float[] fontAscenderDescenderFromMetrics = TextRenderer.calculateAscenderDescender(font, RenderingMode.HTML_MODE);
/* 52 */     float fontAscender = fontAscenderDescenderFromMetrics[0] / 1000.0F * fontSize;
/* 53 */     float fontDescender = fontAscenderDescenderFromMetrics[1] / 1000.0F * fontSize;
/* 54 */     return new float[] { fontAscender, fontDescender };
/*    */   }
/*    */   static float calculateLineHeight(AbstractRenderer renderer) {
/*    */     float lineHeightValue;
/* 58 */     LineHeight lineHeight = renderer.<LineHeight>getProperty(124);
/* 59 */     float fontSize = renderer.getPropertyAsUnitValue(24).getValue();
/*    */     
/* 61 */     if (lineHeight == null || lineHeight.isNormalValue() || lineHeight.getValue() < 0.0F) {
/* 62 */       lineHeightValue = DEFAULT_LINE_HEIGHT_COEFF * fontSize;
/* 63 */       float[] fontAscenderDescender = getFontAscenderDescenderNormalized(renderer);
/* 64 */       float fontAscenderDescenderSum = fontAscenderDescender[0] - fontAscenderDescender[1];
/* 65 */       if (fontAscenderDescenderSum > lineHeightValue) {
/* 66 */         lineHeightValue = fontAscenderDescenderSum;
/*    */       }
/*    */     }
/* 69 */     else if (lineHeight.isFixedValue()) {
/* 70 */       lineHeightValue = lineHeight.getValue();
/*    */     } else {
/* 72 */       lineHeightValue = lineHeight.getValue() * fontSize;
/*    */     } 
/*    */     
/* 75 */     return lineHeightValue;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/LineHeightHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */