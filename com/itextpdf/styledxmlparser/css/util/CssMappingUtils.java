/*    */ package com.itextpdf.styledxmlparser.css.util;
/*    */ 
/*    */ import com.itextpdf.layout.property.BackgroundRepeat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CssMappingUtils
/*    */ {
/*    */   public static BackgroundRepeat.BackgroundRepeatValue parseBackgroundRepeat(String value) {
/* 65 */     switch (value) {
/*    */       case "no-repeat":
/* 67 */         return BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT;
/*    */       case "round":
/* 69 */         return BackgroundRepeat.BackgroundRepeatValue.ROUND;
/*    */       case "space":
/* 71 */         return BackgroundRepeat.BackgroundRepeatValue.SPACE;
/*    */     } 
/*    */     
/* 74 */     return BackgroundRepeat.BackgroundRepeatValue.REPEAT;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/util/CssMappingUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */