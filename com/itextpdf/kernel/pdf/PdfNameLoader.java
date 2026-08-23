/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PdfNameLoader
/*    */ {
/*    */   static Map<String, PdfName> loadNames() {
/* 54 */     Field[] fields = PdfName.class.getDeclaredFields();
/* 55 */     Map<String, PdfName> staticNames = new HashMap<>(fields.length);
/* 56 */     int flags = 25;
/*    */     try {
/* 58 */       for (Field field : fields) {
/* 59 */         if ((field.getModifiers() & 0x19) == 25 && field.getType().equals(PdfName.class)) {
/* 60 */           PdfName name = (PdfName)field.get(null);
/* 61 */           staticNames.put(name.getValue(), name);
/*    */         } 
/*    */       } 
/* 64 */     } catch (Exception e) {
/* 65 */       return null;
/*    */     } 
/* 67 */     return staticNames;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfNameLoader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */