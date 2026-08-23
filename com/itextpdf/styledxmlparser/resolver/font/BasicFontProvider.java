/*    */ package com.itextpdf.styledxmlparser.resolver.font;
/*    */ 
/*    */ import com.itextpdf.layout.font.FontProvider;
/*    */ import com.itextpdf.layout.font.FontSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicFontProvider
/*    */   extends FontProvider
/*    */ {
/*    */   private static final String DEFAULT_FONT_FAMILY = "Times";
/*    */   
/*    */   public BasicFontProvider() {
/* 59 */     this(true, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicFontProvider(boolean registerStandardPdfFonts, boolean registerSystemFonts) {
/* 69 */     this(registerStandardPdfFonts, registerSystemFonts, "Times");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicFontProvider(boolean registerStandardPdfFonts, boolean registerSystemFonts, String defaultFontFamily) {
/* 80 */     super(defaultFontFamily);
/* 81 */     if (registerStandardPdfFonts) {
/* 82 */       addStandardPdfFonts();
/*    */     }
/* 84 */     if (registerSystemFonts) {
/* 85 */       addSystemFonts();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicFontProvider(FontSet fontSet, String defaultFontFamily) {
/* 96 */     super(fontSet, defaultFontFamily);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/resolver/font/BasicFontProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */