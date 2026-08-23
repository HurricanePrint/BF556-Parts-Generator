/*     */ package com.itextpdf.kernel.xmp;
/*     */ 
/*     */ import com.itextpdf.kernel.xmp.impl.Utils;
/*     */ import com.itextpdf.kernel.xmp.impl.xpath.XMPPath;
/*     */ import com.itextpdf.kernel.xmp.impl.xpath.XMPPathParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMPPathFactory
/*     */ {
/*     */   public static String composeArrayItemPath(String arrayName, int itemIndex) throws XMPException {
/* 105 */     if (itemIndex > 0)
/*     */     {
/* 107 */       return arrayName + '[' + itemIndex + ']';
/*     */     }
/* 109 */     if (itemIndex == -1)
/*     */     {
/* 111 */       return arrayName + "[last()]";
/*     */     }
/*     */ 
/*     */     
/* 115 */     throw new XMPException("Array index must be larger than zero", 104);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String composeStructFieldPath(String fieldNS, String fieldName) throws XMPException {
/* 137 */     assertFieldNS(fieldNS);
/* 138 */     assertFieldName(fieldName);
/*     */     
/* 140 */     XMPPath fieldPath = XMPPathParser.expandXPath(fieldNS, fieldName);
/* 141 */     if (fieldPath.size() != 2)
/*     */     {
/* 143 */       throw new XMPException("The field name must be simple", 102);
/*     */     }
/*     */     
/* 146 */     return '/' + fieldPath.getSegment(1).getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String composeQualifierPath(String qualNS, String qualName) throws XMPException {
/* 166 */     assertQualNS(qualNS);
/* 167 */     assertQualName(qualName);
/*     */     
/* 169 */     XMPPath qualPath = XMPPathParser.expandXPath(qualNS, qualName);
/* 170 */     if (qualPath.size() != 2)
/*     */     {
/* 172 */       throw new XMPException("The qualifier name must be simple", 102);
/*     */     }
/*     */     
/* 175 */     return "/?" + qualPath.getSegment(1).getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String composeLangSelector(String arrayName, String langName) {
/* 204 */     return arrayName + "[?xml:lang=\"" + Utils.normalizeLangValue(langName) + "\"]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String composeFieldSelector(String arrayName, String fieldNS, String fieldName, String fieldValue) throws XMPException {
/* 243 */     XMPPath fieldPath = XMPPathParser.expandXPath(fieldNS, fieldName);
/* 244 */     if (fieldPath.size() != 2)
/*     */     {
/* 246 */       throw new XMPException("The fieldName name must be simple", 102);
/*     */     }
/*     */     
/* 249 */     return arrayName + '[' + fieldPath.getSegment(1).getName() + "=\"" + fieldValue + "\"]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertQualNS(String qualNS) throws XMPException {
/* 261 */     if (qualNS == null || qualNS.length() == 0)
/*     */     {
/* 263 */       throw new XMPException("Empty qualifier namespace URI", 101);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertQualName(String qualName) throws XMPException {
/* 276 */     if (qualName == null || qualName.length() == 0)
/*     */     {
/* 278 */       throw new XMPException("Empty qualifier name", 102);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertFieldNS(String fieldNS) throws XMPException {
/* 290 */     if (fieldNS == null || fieldNS.length() == 0)
/*     */     {
/* 292 */       throw new XMPException("Empty field namespace URI", 101);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertFieldName(String fieldName) throws XMPException {
/* 305 */     if (fieldName == null || fieldName.length() == 0)
/*     */     {
/* 307 */       throw new XMPException("Empty f name", 102);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/XMPPathFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */