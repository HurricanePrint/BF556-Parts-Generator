/*    */ package com.itextpdf.io.font.woff2;
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
/*    */ class Woff2Common
/*    */ {
/*    */   public static final int kWoff2Signature = 2001684018;
/*    */   public static final int kWoff2FlagsTransform = 256;
/*    */   public static final int kTtcFontFlavor = 1953784678;
/*    */   public static final int kSfntHeaderSize = 12;
/*    */   public static final int kSfntEntrySize = 16;
/*    */   
/*    */   public static class Point
/*    */   {
/*    */     public int x;
/*    */     public int y;
/*    */     public boolean on_curve;
/*    */     
/*    */     public Point(int x, int y, boolean on_curve) {
/* 41 */       this.x = x;
/* 42 */       this.y = y;
/* 43 */       this.on_curve = on_curve;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Table
/*    */     implements Comparable<Table>
/*    */   {
/*    */     public int tag;
/*    */     public int flags;
/*    */     public int src_offset;
/*    */     public int src_length;
/*    */     public int transform_length;
/*    */     public int dst_offset;
/*    */     public int dst_length;
/*    */     
/*    */     public int compareTo(Table o) {
/* 60 */       return JavaUnsignedUtil.compareAsUnsigned(this.tag, o.tag);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int collectionHeaderSize(int header_version, int num_fonts) {
/* 68 */     int size = 0;
/* 69 */     if (header_version == 131072) {
/* 70 */       size += 12;
/*    */     }
/* 72 */     if (header_version == 65536 || header_version == 131072) {
/* 73 */       size += 12 + 4 * num_fonts;
/*    */     }
/*    */     
/* 76 */     return size;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int computeULongSum(byte[] buf, int offset, int size) {
/* 81 */     int checksum = 0;
/* 82 */     int aligned_size = size & 0xFFFFFFFC;
/* 83 */     for (int i = 0; i < aligned_size; i += 4) {
/* 84 */       checksum += JavaUnsignedUtil.asU8(buf[offset + i]) << 24 | JavaUnsignedUtil.asU8(buf[offset + i + 1]) << 16 | 
/* 85 */         JavaUnsignedUtil.asU8(buf[offset + i + 2]) << 8 | JavaUnsignedUtil.asU8(buf[offset + i + 3]);
/*    */     }
/*    */ 
/*    */     
/* 89 */     if (size != aligned_size) {
/* 90 */       int v = 0;
/* 91 */       for (int j = aligned_size; j < size; j++) {
/* 92 */         v |= JavaUnsignedUtil.asU8(buf[offset + j]) << 24 - 8 * (j & 0x3);
/*    */       }
/* 94 */       checksum += v;
/*    */     } 
/*    */     
/* 97 */     return checksum;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/woff2/Woff2Common.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */