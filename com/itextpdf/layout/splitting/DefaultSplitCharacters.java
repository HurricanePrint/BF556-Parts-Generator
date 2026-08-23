/*    */ package com.itextpdf.layout.splitting;
/*    */ 
/*    */ import com.itextpdf.io.font.otf.GlyphLine;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultSplitCharacters
/*    */   implements ISplitCharacters
/*    */ {
/*    */   public boolean isSplitCharacter(GlyphLine text, int glyphPos) {
/* 55 */     if (!text.get(glyphPos).hasValidUnicode()) {
/* 56 */       return false;
/*    */     }
/* 58 */     int charCode = text.get(glyphPos).getUnicode();
/*    */     
/* 60 */     if (glyphPos == 0 && charCode == 45 && text.size() - 1 > glyphPos && isADigitChar(text, glyphPos + 1)) {
/* 61 */       return false;
/*    */     }
/* 63 */     return (charCode <= 32 || charCode == 45 || charCode == 8208 || (charCode >= 8194 && charCode <= 8203) || (charCode >= 11904 && charCode < 55200) || (charCode >= 63744 && charCode < 64256) || (charCode >= 65072 && charCode < 65104) || (charCode >= 65377 && charCode < 65440));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isADigitChar(GlyphLine text, int glyphPos) {
/* 72 */     return Character.isDigit(text.get(glyphPos).getChars()[0]);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/splitting/DefaultSplitCharacters.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */