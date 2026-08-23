/*    */ package com.itextpdf.kernel.pdf.annot;
/*    */ 
/*    */ import com.itextpdf.kernel.colors.Color;
/*    */ import com.itextpdf.kernel.colors.DeviceCmyk;
/*    */ import com.itextpdf.kernel.colors.DeviceGray;
/*    */ import com.itextpdf.kernel.colors.DeviceRgb;
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class InteriorColorUtil
/*    */ {
/*    */   public static Color parseInteriorColor(PdfArray color) {
/* 65 */     if (color == null) {
/* 66 */       return null;
/*    */     }
/* 68 */     switch (color.size()) {
/*    */       case 1:
/* 70 */         return (Color)new DeviceGray(color.getAsNumber(0).floatValue());
/*    */       case 3:
/* 72 */         return (Color)new DeviceRgb(color.getAsNumber(0).floatValue(), color.getAsNumber(1).floatValue(), color.getAsNumber(2).floatValue());
/*    */       case 4:
/* 74 */         return (Color)new DeviceCmyk(color.getAsNumber(0).floatValue(), color.getAsNumber(1).floatValue(), color.getAsNumber(2).floatValue(), color.getAsNumber(3).floatValue());
/*    */     } 
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/InteriorColorUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */