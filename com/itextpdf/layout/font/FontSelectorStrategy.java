/*    */ package com.itextpdf.layout.font;
/*    */ 
/*    */ import com.itextpdf.io.font.otf.Glyph;
/*    */ import com.itextpdf.kernel.font.PdfFont;
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
/*    */ public abstract class FontSelectorStrategy
/*    */ {
/*    */   protected String text;
/*    */   protected int index;
/*    */   protected final FontProvider provider;
/*    */   @Deprecated
/*    */   protected final FontSet tempFonts;
/*    */   
/*    */   protected FontSelectorStrategy(String text, FontProvider provider, FontSet additionalFonts) {
/* 66 */     this.text = text;
/* 67 */     this.index = 0;
/* 68 */     this.provider = provider;
/* 69 */     this.tempFonts = additionalFonts;
/*    */   }
/*    */   
/*    */   public boolean endOfText() {
/* 73 */     return (this.text == null || this.index >= this.text.length());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract PdfFont getCurrentFont();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract List<Glyph> nextGlyphs();
/*    */ 
/*    */ 
/*    */   
/*    */   protected PdfFont getPdfFont(FontInfo fontInfo) {
/* 88 */     return this.provider.getPdfFont(fontInfo, this.tempFonts);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontSelectorStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */