/*    */ package com.itextpdf.layout.font;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class FontSelectorKey
/*    */ {
/*    */   private List<String> fontFamilies;
/*    */   private FontCharacteristics fc;
/*    */   
/*    */   FontSelectorKey(List<String> fontFamilies, FontCharacteristics fc) {
/* 58 */     this.fontFamilies = new ArrayList<>(fontFamilies);
/* 59 */     this.fc = fc;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 64 */     if (this == o) return true; 
/* 65 */     if (o == null || getClass() != o.getClass()) return false; 
/* 66 */     FontSelectorKey that = (FontSelectorKey)o;
/*    */     
/* 68 */     return (this.fontFamilies.equals(that.fontFamilies) && ((this.fc != null) ? this.fc
/* 69 */       .equals(that.fc) : (that.fc == null)));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 74 */     int result = (this.fontFamilies != null) ? this.fontFamilies.hashCode() : 0;
/* 75 */     result = 31 * result + ((this.fc != null) ? this.fc.hashCode() : 0);
/* 76 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/font/FontSelectorKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */