/*    */ package com.itextpdf.kernel.pdf.filters;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlateDecodeStrictFilter
/*    */   extends FlateDecodeFilter
/*    */ {
/*    */   public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
/* 61 */     ByteArrayOutputStream outputStream = enableMemoryLimitsAwareHandler(streamDictionary);
/* 62 */     byte[] res = flateDecode(b, outputStream);
/* 63 */     b = decodePredictor(res, decodeParams);
/* 64 */     return b;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static byte[] flateDecode(byte[] in, ByteArrayOutputStream out) {
/* 75 */     return flateDecodeInternal(in, true, out);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/FlateDecodeStrictFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */