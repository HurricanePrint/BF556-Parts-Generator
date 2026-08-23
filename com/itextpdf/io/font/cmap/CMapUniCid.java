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
/*    */ public class CMapUniCid
/*    */   extends AbstractCMap
/*    */ {
/*    */   private static final long serialVersionUID = -6111821751136011584L;
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
/* 67 */       this.map.put(codePoint, ((Integer)code.getValue()).intValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   public int lookup(int character) {
/* 72 */     return this.map.get(character);
/*    */   }
/*    */   
/*    */   public CMapToUnicode exportToUnicode() {
/* 76 */     CMapToUnicode uni = new CMapToUnicode();
/* 77 */     int[] keys = this.map.toOrderedKeys();
/* 78 */     for (int key : keys) {
/* 79 */       uni.addChar(this.map.get(key), TextUtil.convertFromUtf32(key));
/*    */     }
/* 81 */     int spaceCid = lookup(32);
/* 82 */     if (spaceCid != 0) {
/* 83 */       uni.addChar(spaceCid, TextUtil.convertFromUtf32(32));
/*    */     }
/* 85 */     return uni;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapUniCid.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */