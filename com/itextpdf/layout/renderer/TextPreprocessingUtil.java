/*    */ package com.itextpdf.layout.renderer;
/*    */ 
/*    */ import com.itextpdf.io.font.otf.Glyph;
/*    */ import com.itextpdf.io.font.otf.GlyphLine;
/*    */ import com.itextpdf.kernel.font.PdfFont;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TextPreprocessingUtil
/*    */ {
/*    */   public static GlyphLine replaceSpecialWhitespaceGlyphs(GlyphLine line, PdfFont font) {
/* 42 */     if (null != line) {
/* 43 */       boolean isMonospaceFont = font.getFontProgram().getFontMetrics().isFixedPitch();
/* 44 */       Glyph space = font.getGlyph(32);
/* 45 */       int spaceWidth = space.getWidth();
/*    */       
/* 47 */       int lineSize = line.size();
/* 48 */       for (int i = 0; i < lineSize; i++) {
/* 49 */         Glyph glyph = line.get(i);
/*    */         
/* 51 */         int xAdvance = 0;
/* 52 */         boolean isSpecialWhitespaceGlyph = false;
/*    */         
/* 54 */         if (glyph.getCode() <= 0) {
/* 55 */           switch (glyph.getUnicode()) {
/*    */             
/*    */             case 8194:
/* 58 */               xAdvance = isMonospaceFont ? 0 : (500 - spaceWidth);
/* 59 */               isSpecialWhitespaceGlyph = true;
/*    */               break;
/*    */ 
/*    */             
/*    */             case 8195:
/* 64 */               xAdvance = isMonospaceFont ? 0 : (1000 - spaceWidth);
/* 65 */               isSpecialWhitespaceGlyph = true;
/*    */               break;
/*    */ 
/*    */             
/*    */             case 8201:
/* 70 */               xAdvance = isMonospaceFont ? 0 : (200 - spaceWidth);
/* 71 */               isSpecialWhitespaceGlyph = true;
/*    */               break;
/*    */             
/*    */             case 9:
/* 75 */               xAdvance = 3 * spaceWidth;
/* 76 */               isSpecialWhitespaceGlyph = true;
/*    */               break;
/*    */           } 
/*    */ 
/*    */         
/*    */         }
/* 82 */         if (isSpecialWhitespaceGlyph) {
/* 83 */           Glyph newGlyph = new Glyph(space, glyph.getUnicode());
/* 84 */           assert xAdvance <= 32767 && xAdvance >= -32768;
/* 85 */           newGlyph.setXAdvance((short)xAdvance);
/* 86 */           line.set(i, newGlyph);
/*    */         } 
/*    */       } 
/*    */     } 
/* 90 */     return line;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/TextPreprocessingUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */