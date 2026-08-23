/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.zip.InflaterInputStream;
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
/*     */ class WoffConverter
/*     */ {
/*     */   private static final long woffSignature = 2001684038L;
/*     */   
/*     */   public static boolean isWoffFont(byte[] woffBytes) {
/*  55 */     return (bytesToUInt(woffBytes, 0) == 2001684038L);
/*     */   }
/*     */   
/*     */   public static byte[] convert(byte[] woffBytes) throws IOException {
/*  59 */     int srcPos = 0;
/*  60 */     int destPos = 0;
/*     */ 
/*     */     
/*  63 */     if (bytesToUInt(woffBytes, srcPos) != 2001684038L) {
/*  64 */       throw new IllegalArgumentException();
/*     */     }
/*  66 */     srcPos += 4;
/*     */     
/*  68 */     byte[] flavor = new byte[4];
/*  69 */     System.arraycopy(woffBytes, srcPos, flavor, 0, 4);
/*  70 */     srcPos += 4;
/*     */ 
/*     */     
/*  73 */     if (bytesToUInt(woffBytes, srcPos) != woffBytes.length) {
/*  74 */       throw new IllegalArgumentException();
/*     */     }
/*  76 */     srcPos += 4;
/*     */     
/*  78 */     byte[] numTables = new byte[2];
/*  79 */     System.arraycopy(woffBytes, srcPos, numTables, 0, 2);
/*  80 */     srcPos += 2;
/*     */ 
/*     */     
/*  83 */     if (bytesToUShort(woffBytes, srcPos) != 0) {
/*  84 */       throw new IllegalArgumentException();
/*     */     }
/*  86 */     srcPos += 2;
/*     */     
/*  88 */     long totalSfntSize = bytesToUInt(woffBytes, srcPos);
/*  89 */     srcPos += 4;
/*     */ 
/*     */     
/*  92 */     srcPos += 2;
/*     */     
/*  94 */     srcPos += 2;
/*     */     
/*  96 */     srcPos += 4;
/*     */     
/*  98 */     srcPos += 4;
/*     */     
/* 100 */     srcPos += 4;
/*     */     
/* 102 */     srcPos += 4;
/*     */     
/* 104 */     srcPos += 4;
/*     */ 
/*     */ 
/*     */     
/* 108 */     byte[] otfBytes = new byte[(int)totalSfntSize];
/* 109 */     System.arraycopy(flavor, 0, otfBytes, destPos, 4);
/* 110 */     destPos += 4;
/* 111 */     System.arraycopy(numTables, 0, otfBytes, destPos, 2);
/* 112 */     destPos += 2;
/*     */     
/* 114 */     int entrySelector = -1;
/* 115 */     int searchRange = -1;
/* 116 */     int numTablesVal = bytesToUShort(numTables, 0);
/* 117 */     for (int i = 0; i < 17; i++) {
/* 118 */       int powOfTwo = (int)Math.pow(2.0D, i);
/* 119 */       if (powOfTwo > numTablesVal) {
/* 120 */         entrySelector = i;
/* 121 */         searchRange = powOfTwo * 16;
/*     */         break;
/*     */       } 
/*     */     } 
/* 125 */     if (entrySelector < 0) {
/* 126 */       throw new IllegalArgumentException();
/*     */     }
/* 128 */     otfBytes[destPos] = (byte)(searchRange >> 8);
/* 129 */     otfBytes[destPos + 1] = (byte)searchRange;
/* 130 */     destPos += 2;
/* 131 */     otfBytes[destPos] = (byte)(entrySelector >> 8);
/* 132 */     otfBytes[destPos + 1] = (byte)entrySelector;
/* 133 */     destPos += 2;
/* 134 */     int rangeShift = numTablesVal * 16 - searchRange;
/* 135 */     otfBytes[destPos] = (byte)(rangeShift >> 8);
/* 136 */     otfBytes[destPos + 1] = (byte)rangeShift;
/* 137 */     destPos += 2;
/*     */     
/* 139 */     int outTableOffset = destPos;
/* 140 */     List<TableDirectory> tdList = new ArrayList<>(numTablesVal);
/* 141 */     for (int j = 0; j < numTablesVal; j++) {
/* 142 */       TableDirectory td = new TableDirectory();
/* 143 */       System.arraycopy(woffBytes, srcPos, td.tag, 0, 4);
/* 144 */       srcPos += 4;
/* 145 */       td.offset = bytesToUInt(woffBytes, srcPos);
/* 146 */       srcPos += 4;
/*     */       
/* 148 */       if (td.offset % 4L != 0L) {
/* 149 */         throw new IllegalArgumentException();
/*     */       }
/*     */       
/* 152 */       td.compLength = bytesToUInt(woffBytes, srcPos);
/* 153 */       srcPos += 4;
/* 154 */       System.arraycopy(woffBytes, srcPos, td.origLength, 0, 4);
/* 155 */       td.origLengthVal = bytesToUInt(td.origLength, 0);
/* 156 */       srcPos += 4;
/* 157 */       System.arraycopy(woffBytes, srcPos, td.origChecksum, 0, 4);
/* 158 */       srcPos += 4;
/*     */       
/* 160 */       tdList.add(td);
/* 161 */       outTableOffset += 16;
/*     */     } 
/*     */     
/* 164 */     for (TableDirectory td : tdList) {
/* 165 */       System.arraycopy(td.tag, 0, otfBytes, destPos, 4);
/* 166 */       destPos += 4;
/*     */       
/* 168 */       System.arraycopy(td.origChecksum, 0, otfBytes, destPos, 4);
/* 169 */       destPos += 4;
/*     */       
/* 171 */       otfBytes[destPos] = (byte)(outTableOffset >> 24);
/* 172 */       otfBytes[destPos + 1] = (byte)(outTableOffset >> 16);
/* 173 */       otfBytes[destPos + 2] = (byte)(outTableOffset >> 8);
/* 174 */       otfBytes[destPos + 3] = (byte)outTableOffset;
/* 175 */       destPos += 4;
/*     */       
/* 177 */       System.arraycopy(td.origLength, 0, otfBytes, destPos, 4);
/* 178 */       destPos += 4;
/*     */       
/* 180 */       td.outOffset = outTableOffset;
/*     */       
/* 182 */       outTableOffset += (int)td.origLengthVal;
/* 183 */       if (outTableOffset % 4 != 0) {
/* 184 */         outTableOffset += 4 - outTableOffset % 4;
/*     */       }
/*     */     } 
/*     */     
/* 188 */     if (outTableOffset != totalSfntSize) {
/* 189 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 192 */     for (TableDirectory td : tdList) {
/* 193 */       byte[] uncompressedData, compressedData = new byte[(int)td.compLength];
/*     */       
/* 195 */       System.arraycopy(woffBytes, (int)td.offset, compressedData, 0, (int)td.compLength);
/* 196 */       int expectedUncompressedLen = (int)td.origLengthVal;
/* 197 */       if (td.compLength > td.origLengthVal) {
/* 198 */         throw new IllegalArgumentException();
/*     */       }
/* 200 */       if (td.compLength != td.origLengthVal) {
/* 201 */         ByteArrayInputStream stream = new ByteArrayInputStream(compressedData);
/* 202 */         InflaterInputStream zip = new InflaterInputStream(stream);
/* 203 */         uncompressedData = new byte[expectedUncompressedLen];
/* 204 */         int bytesRead = 0;
/* 205 */         while (expectedUncompressedLen - bytesRead > 0) {
/* 206 */           int readRes = zip.read(uncompressedData, bytesRead, expectedUncompressedLen - bytesRead);
/* 207 */           if (readRes < 0) {
/* 208 */             throw new IllegalArgumentException();
/*     */           }
/* 210 */           bytesRead += readRes;
/*     */         } 
/* 212 */         if (zip.read() >= 0) {
/* 213 */           throw new IllegalArgumentException();
/*     */         }
/*     */       } else {
/* 216 */         uncompressedData = compressedData;
/*     */       } 
/*     */       
/* 219 */       System.arraycopy(uncompressedData, 0, otfBytes, td.outOffset, expectedUncompressedLen);
/*     */     } 
/*     */     
/* 222 */     return otfBytes;
/*     */   }
/*     */   
/*     */   private static long bytesToUInt(byte[] b, int start) {
/* 226 */     return (b[start] & 0xFFL) << 24L | (b[start + 1] & 0xFFL) << 16L | (b[start + 2] & 0xFFL) << 8L | b[start + 3] & 0xFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int bytesToUShort(byte[] b, int start) {
/* 233 */     return (b[start] & 0xFF) << 8 | b[start + 1] & 0xFF;
/*     */   }
/*     */   
/*     */   private static class TableDirectory
/*     */   {
/* 238 */     byte[] tag = new byte[4];
/*     */     private TableDirectory() {}
/*     */     long offset; long compLength;
/* 241 */     byte[] origLength = new byte[4];
/*     */     long origLengthVal;
/* 243 */     byte[] origChecksum = new byte[4];
/*     */     int outOffset;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/WoffConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */