/*    */ package com.itextpdf.io.font.cmap;
/*    */ 
/*    */ import com.itextpdf.io.util.IntHashtable;
/*    */ import com.itextpdf.io.util.TextUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CMapCidUni
/*    */   extends AbstractCMap
/*    */ {
/*    */   private static final long serialVersionUID = 6879167385978230141L;
/* 55 */   private IntHashtable map = new IntHashtable(65537);
/*    */ 
/*    */   
/*    */   void addChar(String mark, CMapObject code) {
/* 59 */     if (code.isNumber()) {
/*    */       int codePoint;
/* 61 */       String s = toUnicodeString(mark, true);
/* 62 */       if (TextUtil.isSurrogatePair(s, 0)) {
/* 63 */         codePoint = TextUtil.convertToUtf32(s, 0);
/*    */       } else {
/* 65 */         codePoint = s.charAt(0);
/*    */       } 
/* 67 */       this.map.put(((Integer)code.getValue()).intValue(), codePoint);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int lookup(int character) {
/* 72 */     return this.map.get(character);
/*    */   }
/*    */   
/*    */   public int[] getCids() {
/* 76 */     return this.map.getKeys();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapCidUni.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */