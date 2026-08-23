/*    */ package com.itextpdf.kernel.pdf.filters;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.MemoryLimitsAwareFilter;
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
/*    */ 
/*    */ public class RunLengthDecodeFilter
/*    */   extends MemoryLimitsAwareFilter
/*    */ {
/*    */   public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
/* 63 */     ByteArrayOutputStream outputStream = enableMemoryLimitsAwareHandler(streamDictionary);
/*    */     
/* 65 */     for (int i = 0; i < b.length; i++) {
/* 66 */       byte dupCount = b[i];
/* 67 */       if (dupCount == Byte.MIN_VALUE) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 72 */       if ((dupCount & 0x80) == 0) {
/* 73 */         int bytesToCopy = dupCount + 1;
/* 74 */         outputStream.write(b, i + 1, bytesToCopy);
/* 75 */         i += bytesToCopy;
/*    */       }
/*    */       else {
/*    */         
/* 79 */         i++;
/* 80 */         for (int j = 0; j < 257 - (dupCount & 0xFF); j++) {
/* 81 */           outputStream.write(b[i]);
/*    */         }
/*    */       } 
/*    */     } 
/* 85 */     return outputStream.toByteArray();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/RunLengthDecodeFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */