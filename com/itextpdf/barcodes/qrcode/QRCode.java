/*     */ package com.itextpdf.barcodes.qrcode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QRCode
/*     */ {
/*     */   public static final int NUM_MASK_PATTERNS = 8;
/*  69 */   private Mode mode = null;
/*  70 */   private ErrorCorrectionLevel ecLevel = null;
/*  71 */   private int version = -1;
/*  72 */   private int matrixWidth = -1;
/*  73 */   private int maskPattern = -1;
/*  74 */   private int numTotalBytes = -1;
/*  75 */   private int numDataBytes = -1;
/*  76 */   private int numECBytes = -1;
/*  77 */   private int numRSBlocks = -1;
/*  78 */   private ByteMatrix matrix = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mode getMode() {
/*  86 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorCorrectionLevel getECLevel() {
/*  94 */     return this.ecLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVersion() {
/* 102 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMatrixWidth() {
/* 109 */     return this.matrixWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaskPattern() {
/* 116 */     return this.maskPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumTotalBytes() {
/* 123 */     return this.numTotalBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumDataBytes() {
/* 130 */     return this.numDataBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumECBytes() {
/* 137 */     return this.numECBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumRSBlocks() {
/* 144 */     return this.numRSBlocks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteMatrix getMatrix() {
/* 151 */     return this.matrix;
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
/*     */   public int at(int x, int y) {
/* 163 */     int value = this.matrix.get(x, y);
/* 164 */     if (value != 0 && value != 1)
/*     */     {
/* 166 */       throw new RuntimeException("Bad value");
/*     */     }
/* 168 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 176 */     return (this.mode != null && this.ecLevel != null && this.version != -1 && this.matrixWidth != -1 && this.maskPattern != -1 && this.numTotalBytes != -1 && this.numDataBytes != -1 && this.numECBytes != -1 && this.numRSBlocks != -1 && 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       isValidMaskPattern(this.maskPattern) && this.numTotalBytes == this.numDataBytes + this.numECBytes && this.matrix != null && this.matrixWidth == this.matrix
/*     */ 
/*     */ 
/*     */       
/* 192 */       .getWidth() && this.matrix
/*     */ 
/*     */       
/* 195 */       .getWidth() == this.matrix.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 203 */     StringBuffer result = new StringBuffer(200);
/* 204 */     result.append("<<\n");
/* 205 */     result.append(" mode: ");
/* 206 */     result.append(this.mode);
/* 207 */     result.append("\n ecLevel: ");
/* 208 */     result.append(this.ecLevel);
/* 209 */     result.append("\n version: ");
/* 210 */     result.append(this.version);
/* 211 */     result.append("\n matrixWidth: ");
/* 212 */     result.append(this.matrixWidth);
/* 213 */     result.append("\n maskPattern: ");
/* 214 */     result.append(this.maskPattern);
/* 215 */     result.append("\n numTotalBytes: ");
/* 216 */     result.append(this.numTotalBytes);
/* 217 */     result.append("\n numDataBytes: ");
/* 218 */     result.append(this.numDataBytes);
/* 219 */     result.append("\n numECBytes: ");
/* 220 */     result.append(this.numECBytes);
/* 221 */     result.append("\n numRSBlocks: ");
/* 222 */     result.append(this.numRSBlocks);
/* 223 */     if (this.matrix == null) {
/* 224 */       result.append("\n matrix: null\n");
/*     */     } else {
/* 226 */       result.append("\n matrix:\n");
/* 227 */       result.append(this.matrix.toString());
/*     */     } 
/* 229 */     result.append(">>\n");
/* 230 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(Mode value) {
/* 239 */     this.mode = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setECLevel(ErrorCorrectionLevel value) {
/* 248 */     this.ecLevel = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersion(int value) {
/* 258 */     this.version = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMatrixWidth(int value) {
/* 266 */     this.matrixWidth = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaskPattern(int value) {
/* 274 */     this.maskPattern = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumTotalBytes(int value) {
/* 282 */     this.numTotalBytes = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumDataBytes(int value) {
/* 290 */     this.numDataBytes = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumECBytes(int value) {
/* 298 */     this.numECBytes = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumRSBlocks(int value) {
/* 306 */     this.numRSBlocks = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMatrix(ByteMatrix value) {
/* 314 */     this.matrix = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidMaskPattern(int maskPattern) {
/* 323 */     return (maskPattern >= 0 && maskPattern < 8);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/QRCode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */