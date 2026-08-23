/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ class SerializedObjectContent
/*    */ {
/*    */   private final byte[] serializedContent;
/*    */   private final int hash;
/*    */   
/*    */   SerializedObjectContent(byte[] serializedContent) {
/* 52 */     this.serializedContent = serializedContent;
/* 53 */     this.hash = calculateHash(serializedContent);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 58 */     return (obj instanceof SerializedObjectContent && 
/* 59 */       hashCode() == obj.hashCode() && 
/* 60 */       Arrays.equals(this.serializedContent, ((SerializedObjectContent)obj).serializedContent));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     return this.hash;
/*    */   }
/*    */   
/*    */   private static int calculateHash(byte[] b) {
/* 69 */     int hash = 0;
/* 70 */     int len = b.length;
/* 71 */     for (int k = 0; k < len; k++) {
/* 72 */       hash = hash * 31 + (b[k] & 0xFF);
/*    */     }
/* 74 */     return hash;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/SerializedObjectContent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */