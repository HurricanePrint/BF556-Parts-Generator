/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.filters.IFilterHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MemoryLimitsAwareFilter
/*    */   implements IFilterHandler
/*    */ {
/*    */   public ByteArrayOutputStream enableMemoryLimitsAwareHandler(PdfDictionary streamDictionary) {
/* 63 */     MemoryLimitsAwareOutputStream outputStream = new MemoryLimitsAwareOutputStream();
/* 64 */     MemoryLimitsAwareHandler memoryLimitsAwareHandler = null;
/* 65 */     if (null != streamDictionary.getIndirectReference()) {
/* 66 */       memoryLimitsAwareHandler = (streamDictionary.getIndirectReference().getDocument()).memoryLimitsAwareHandler;
/*    */     } else {
/*    */       
/* 69 */       memoryLimitsAwareHandler = new MemoryLimitsAwareHandler();
/*    */     } 
/* 71 */     if (null != memoryLimitsAwareHandler && memoryLimitsAwareHandler.considerCurrentPdfStream) {
/* 72 */       outputStream.setMaxStreamSize(memoryLimitsAwareHandler.getMaxSizeOfSingleDecompressedPdfStream());
/*    */     }
/* 74 */     return outputStream;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/MemoryLimitsAwareFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */