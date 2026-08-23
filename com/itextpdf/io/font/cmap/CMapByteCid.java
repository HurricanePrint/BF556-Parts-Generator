/*     */ package com.itextpdf.io.font.cmap;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class CMapByteCid
/*     */   extends AbstractCMap
/*     */ {
/*     */   private static final long serialVersionUID = 8843696844192313477L;
/*     */   
/*     */   protected static class Cursor
/*     */   {
/*     */     public int offset;
/*     */     public int length;
/*     */     
/*     */     public Cursor(int offset, int length) {
/*  64 */       this.offset = offset;
/*  65 */       this.length = length;
/*     */     }
/*     */   }
/*     */   
/*  69 */   private List<int[]> planes = (List)new ArrayList<>();
/*     */   
/*     */   public CMapByteCid() {
/*  72 */     this.planes.add(new int[256]);
/*     */   }
/*     */ 
/*     */   
/*     */   void addChar(String mark, CMapObject code) {
/*  77 */     if (code.isNumber()) {
/*  78 */       encodeSequence(decodeStringToByte(mark), ((Integer)code.getValue()).intValue());
/*     */     }
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
/*     */   public String decodeSequence(byte[] cidBytes, int offset, int length) {
/*  91 */     StringBuilder sb = new StringBuilder();
/*  92 */     Cursor cursor = new Cursor(offset, length);
/*     */     int cid;
/*  94 */     while ((cid = decodeSingle(cidBytes, cursor)) >= 0) {
/*  95 */       sb.append((char)cid);
/*     */     }
/*  97 */     return sb.toString();
/*     */   }
/*     */   
/*     */   protected int decodeSingle(byte[] cidBytes, Cursor cursor) {
/* 101 */     int end = cursor.offset + cursor.length;
/* 102 */     int currentPlane = 0;
/* 103 */     while (cursor.offset < end) {
/* 104 */       int one = cidBytes[cursor.offset++] & 0xFF;
/* 105 */       cursor.length--;
/* 106 */       int[] plane = this.planes.get(currentPlane);
/* 107 */       int cid = plane[one];
/* 108 */       if ((cid & 0x8000) == 0) {
/* 109 */         return cid;
/*     */       }
/* 111 */       currentPlane = cid & 0x7FFF;
/*     */     } 
/*     */     
/* 114 */     return -1;
/*     */   }
/*     */   
/*     */   private void encodeSequence(byte[] seq, int cid) {
/* 118 */     int size = seq.length - 1;
/* 119 */     int nextPlane = 0;
/* 120 */     for (int idx = 0; idx < size; idx++) {
/* 121 */       int[] arrayOfInt = this.planes.get(nextPlane);
/* 122 */       int i = seq[idx] & 0xFF;
/* 123 */       int j = arrayOfInt[i];
/* 124 */       if (j != 0 && (j & 0x8000) == 0)
/* 125 */         throw new IOException("Inconsistent mapping."); 
/* 126 */       if (j == 0) {
/* 127 */         this.planes.add(new int[256]);
/* 128 */         j = this.planes.size() - 1 | 0x8000;
/* 129 */         arrayOfInt[i] = j;
/*     */       } 
/* 131 */       nextPlane = j & 0x7FFF;
/*     */     } 
/* 133 */     int[] plane = this.planes.get(nextPlane);
/* 134 */     int one = seq[size] & 0xFF;
/* 135 */     int c = plane[one];
/* 136 */     if ((c & 0x8000) != 0)
/* 137 */       throw new IOException("Inconsistent mapping."); 
/* 138 */     plane[one] = cid;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapByteCid.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */