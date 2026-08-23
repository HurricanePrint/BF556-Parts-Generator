/*    */ package com.itextpdf.barcodes.dmcode;
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
/*    */ public class DmParams
/*    */ {
/*    */   public int height;
/*    */   public int width;
/*    */   public int heightSection;
/*    */   public int widthSection;
/*    */   public int dataSize;
/*    */   public int dataBlock;
/*    */   public int errorBlock;
/*    */   
/*    */   public DmParams(int height, int width, int heightSection, int widthSection, int dataSize, int dataBlock, int errorBlock) {
/* 58 */     this.height = height;
/* 59 */     this.width = width;
/* 60 */     this.heightSection = heightSection;
/* 61 */     this.widthSection = widthSection;
/* 62 */     this.dataSize = dataSize;
/* 63 */     this.dataBlock = dataBlock;
/* 64 */     this.errorBlock = errorBlock;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/dmcode/DmParams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */