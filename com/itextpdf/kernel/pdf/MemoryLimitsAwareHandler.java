/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
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
/*     */ public class MemoryLimitsAwareHandler
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2499046471291214639L;
/*     */   private static final int SINGLE_SCALE_COEFFICIENT = 100;
/*     */   private static final int SUM_SCALE_COEFFICIENT = 500;
/*     */   private static final int SINGLE_DECOMPRESSED_PDF_STREAM_MIN_SIZE = 21474836;
/*     */   private static final long SUM_OF_DECOMPRESSED_PDF_STREAMW_MIN_SIZE = 107374182L;
/*     */   private int maxSizeOfSingleDecompressedPdfStream;
/*     */   private long maxSizeOfDecompressedPdfStreamsSum;
/*  72 */   private long allMemoryUsedForDecompression = 0L;
/*  73 */   private long memoryUsedForCurrentPdfStreamDecompression = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean considerCurrentPdfStream = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public MemoryLimitsAwareHandler() {
/*  82 */     this.maxSizeOfSingleDecompressedPdfStream = 21474836;
/*  83 */     this.maxSizeOfDecompressedPdfStreamsSum = 107374182L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemoryLimitsAwareHandler(long documentSize) {
/*  93 */     this.maxSizeOfSingleDecompressedPdfStream = (int)calculateDefaultParameter(documentSize, 100, 21474836L);
/*     */     
/*  95 */     this.maxSizeOfDecompressedPdfStreamsSum = calculateDefaultParameter(documentSize, 500, 107374182L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSizeOfSingleDecompressedPdfStream() {
/* 105 */     return this.maxSizeOfSingleDecompressedPdfStream;
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
/*     */   public MemoryLimitsAwareHandler setMaxSizeOfSingleDecompressedPdfStream(int maxSizeOfSingleDecompressedPdfStream) {
/* 121 */     this.maxSizeOfSingleDecompressedPdfStream = maxSizeOfSingleDecompressedPdfStream;
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxSizeOfDecompressedPdfStreamsSum() {
/* 131 */     return this.maxSizeOfDecompressedPdfStreamsSum;
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
/*     */   public MemoryLimitsAwareHandler setMaxSizeOfDecompressedPdfStreamsSum(long maxSizeOfDecompressedPdfStreamsSum) {
/* 148 */     this.maxSizeOfDecompressedPdfStreamsSum = maxSizeOfDecompressedPdfStreamsSum;
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMemoryLimitsAwarenessRequiredOnDecompression(PdfArray filters) {
/* 160 */     HashSet<PdfName> filterSet = new HashSet<>();
/* 161 */     for (int index = 0; index < filters.size(); index++) {
/* 162 */       PdfName filterName = filters.getAsName(index);
/* 163 */       if (!filterSet.add(filterName)) {
/* 164 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 168 */     return false;
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
/*     */   MemoryLimitsAwareHandler considerBytesOccupiedByDecompressedPdfStream(long numOfOccupiedBytes) {
/* 180 */     if (this.considerCurrentPdfStream && this.memoryUsedForCurrentPdfStreamDecompression < numOfOccupiedBytes) {
/* 181 */       this.memoryUsedForCurrentPdfStreamDecompression = numOfOccupiedBytes;
/* 182 */       if (this.memoryUsedForCurrentPdfStreamDecompression > this.maxSizeOfSingleDecompressedPdfStream) {
/* 183 */         throw new MemoryLimitsAwareException("During decompression a single stream occupied more memory than allowed. Please either check your pdf or increase the allowed multiple decompressed pdf streams maximum size value by setting the appropriate parameter of ReaderProperties's MemoryLimitsAwareHandler.");
/*     */       }
/*     */     } 
/*     */     
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MemoryLimitsAwareHandler beginDecompressedPdfStreamProcessing() {
/* 196 */     ensureCurrentStreamIsReset();
/* 197 */     this.considerCurrentPdfStream = true;
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MemoryLimitsAwareHandler endDecompressedPdfStreamProcessing() {
/* 209 */     this.allMemoryUsedForDecompression += this.memoryUsedForCurrentPdfStreamDecompression;
/* 210 */     if (this.allMemoryUsedForDecompression > this.maxSizeOfDecompressedPdfStreamsSum) {
/* 211 */       throw new MemoryLimitsAwareException("During decompression multiple streams in sum occupied more memory than allowed. Please either check your pdf or increase the allowed single decompressed pdf stream maximum size value by setting the appropriate parameter of ReaderProperties's MemoryLimitsAwareHandler.");
/*     */     }
/*     */     
/* 214 */     ensureCurrentStreamIsReset();
/* 215 */     this.considerCurrentPdfStream = false;
/* 216 */     return this;
/*     */   }
/*     */   
/*     */   long getAllMemoryUsedForDecompression() {
/* 220 */     return this.allMemoryUsedForDecompression;
/*     */   }
/*     */   
/*     */   private static long calculateDefaultParameter(long documentSize, int scale, long min) {
/* 224 */     long result = documentSize * scale;
/* 225 */     if (result < min) {
/* 226 */       result = min;
/*     */     }
/* 228 */     if (result > min * scale) {
/* 229 */       result = min * scale;
/*     */     }
/* 231 */     return result;
/*     */   }
/*     */   
/*     */   private void ensureCurrentStreamIsReset() {
/* 235 */     this.memoryUsedForCurrentPdfStreamDecompression = 0L;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/MemoryLimitsAwareHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */