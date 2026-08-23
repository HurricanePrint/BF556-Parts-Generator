/*     */ package com.itextpdf.barcodes.qrcode;
/*     */ 
/*     */ import java.util.Map;
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
/*     */ public final class QRCodeWriter
/*     */ {
/*     */   private static final int QUIET_ZONE_SIZE = 4;
/*     */   
/*     */   public ByteMatrix encode(String contents, int width, int height) throws WriterException {
/*  68 */     return encode(contents, width, height, null);
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
/*     */   public ByteMatrix encode(String contents, int width, int height, Map<EncodeHintType, Object> hints) throws WriterException {
/*  84 */     if (contents == null || contents.length() == 0) {
/*  85 */       throw new IllegalArgumentException("Found empty contents");
/*     */     }
/*     */     
/*  88 */     if (width < 0 || height < 0) {
/*  89 */       throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
/*     */     }
/*     */ 
/*     */     
/*  93 */     ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
/*  94 */     if (hints != null) {
/*  95 */       ErrorCorrectionLevel requestedECLevel = (ErrorCorrectionLevel)hints.get(EncodeHintType.ERROR_CORRECTION);
/*     */ 
/*     */       
/*  98 */       if (requestedECLevel != null) {
/*  99 */         errorCorrectionLevel = requestedECLevel;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     QRCode code = new QRCode();
/* 104 */     Encoder.encode(contents, errorCorrectionLevel, hints, code);
/* 105 */     return renderResult(code, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteMatrix renderResult(QRCode code, int width, int height) {
/* 112 */     ByteMatrix input = code.getMatrix();
/* 113 */     int inputWidth = input.getWidth();
/* 114 */     int inputHeight = input.getHeight();
/* 115 */     int qrWidth = inputWidth + 8;
/* 116 */     int qrHeight = inputHeight + 8;
/* 117 */     int outputWidth = Math.max(width, qrWidth);
/* 118 */     int outputHeight = Math.max(height, qrHeight);
/*     */     
/* 120 */     int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     int leftPadding = (outputWidth - inputWidth * multiple) / 2;
/* 126 */     int topPadding = (outputHeight - inputHeight * multiple) / 2;
/*     */     
/* 128 */     ByteMatrix output = new ByteMatrix(outputWidth, outputHeight);
/* 129 */     byte[][] outputArray = output.getArray();
/*     */ 
/*     */ 
/*     */     
/* 133 */     byte[] row = new byte[outputWidth];
/*     */ 
/*     */     
/* 136 */     for (int y = 0; y < topPadding; y++) {
/* 137 */       setRowColor(outputArray[y], (byte)-1);
/*     */     }
/*     */ 
/*     */     
/* 141 */     byte[][] inputArray = input.getArray();
/* 142 */     for (int i = 0; i < inputHeight; i++) {
/*     */       
/* 144 */       for (int x = 0; x < leftPadding; x++) {
/* 145 */         row[x] = -1;
/*     */       }
/*     */ 
/*     */       
/* 149 */       int k = leftPadding; int m;
/* 150 */       for (m = 0; m < inputWidth; m++) {
/* 151 */         byte value = (inputArray[i][m] == 1) ? 0 : -1;
/* 152 */         for (int n = 0; n < multiple; n++) {
/* 153 */           row[k + n] = value;
/*     */         }
/* 155 */         k += multiple;
/*     */       } 
/*     */ 
/*     */       
/* 159 */       k = leftPadding + inputWidth * multiple;
/* 160 */       for (m = k; m < outputWidth; m++) {
/* 161 */         row[m] = -1;
/*     */       }
/*     */ 
/*     */       
/* 165 */       k = topPadding + i * multiple;
/* 166 */       for (int z = 0; z < multiple; z++) {
/* 167 */         System.arraycopy(row, 0, outputArray[k + z], 0, outputWidth);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 172 */     int offset = topPadding + inputHeight * multiple;
/* 173 */     for (int j = offset; j < outputHeight; j++) {
/* 174 */       setRowColor(outputArray[j], (byte)-1);
/*     */     }
/*     */     
/* 177 */     return output;
/*     */   }
/*     */   
/*     */   private static void setRowColor(byte[] row, byte value) {
/* 181 */     for (int x = 0; x < row.length; x++)
/* 182 */       row[x] = value; 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/QRCodeWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */