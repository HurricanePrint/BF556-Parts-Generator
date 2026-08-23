/*    */ package com.itextpdf.barcodes.qrcode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class BlockPair
/*    */ {
/*    */   private final ByteArray dataBytes;
/*    */   private final ByteArray errorCorrectionBytes;
/*    */   
/*    */   BlockPair(ByteArray data, ByteArray errorCorrection) {
/* 55 */     this.dataBytes = data;
/* 56 */     this.errorCorrectionBytes = errorCorrection;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteArray getDataBytes() {
/* 63 */     return this.dataBytes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteArray getErrorCorrectionBytes() {
/* 70 */     return this.errorCorrectionBytes;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/BlockPair.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */