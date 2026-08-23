/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class TIFFField
/*     */   implements Comparable<TIFFField>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 9088332901412823834L;
/*     */   public static final int TIFF_BYTE = 1;
/*     */   public static final int TIFF_ASCII = 2;
/*     */   public static final int TIFF_SHORT = 3;
/*     */   public static final int TIFF_LONG = 4;
/*     */   public static final int TIFF_RATIONAL = 5;
/*     */   public static final int TIFF_SBYTE = 6;
/*     */   public static final int TIFF_UNDEFINED = 7;
/*     */   public static final int TIFF_SSHORT = 8;
/*     */   public static final int TIFF_SLONG = 9;
/*     */   public static final int TIFF_SRATIONAL = 10;
/*     */   public static final int TIFF_FLOAT = 11;
/*     */   public static final int TIFF_DOUBLE = 12;
/*     */   int tag;
/*     */   int type;
/*     */   int count;
/*     */   Object data;
/*     */   
/*     */   TIFFField() {}
/*     */   
/*     */   public TIFFField(int tag, int type, int count, Object data) {
/* 176 */     this.tag = tag;
/* 177 */     this.type = type;
/* 178 */     this.count = count;
/* 179 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTag() {
/* 188 */     return this.tag;
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
/*     */   public int getType() {
/* 200 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 209 */     return this.count;
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
/*     */   
/*     */   public byte[] getAsBytes() {
/* 227 */     return (byte[])this.data;
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
/*     */   public char[] getAsChars() {
/* 240 */     return (char[])this.data;
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
/*     */   public short[] getAsShorts() {
/* 253 */     return (short[])this.data;
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
/*     */   public int[] getAsInts() {
/* 266 */     return (int[])this.data;
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
/*     */   public long[] getAsLongs() {
/* 279 */     return (long[])this.data;
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
/*     */   public float[] getAsFloats() {
/* 291 */     return (float[])this.data;
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
/*     */   public double[] getAsDoubles() {
/* 303 */     return (double[])this.data;
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
/*     */   public String[] getAsStrings() {
/* 315 */     return (String[])this.data;
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
/*     */   public int[][] getAsSRationals() {
/* 327 */     return (int[][])this.data;
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
/*     */   public long[][] getAsRationals() {
/* 339 */     return (long[][])this.data;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAsInt(int index) {
/* 360 */     switch (this.type) { case 1:
/*     */       case 7:
/* 362 */         return ((byte[])this.data)[index] & 0xFF;
/*     */       case 6:
/* 364 */         return ((byte[])this.data)[index];
/*     */       case 3:
/* 366 */         return ((char[])this.data)[index] & Character.MAX_VALUE;
/*     */       case 8:
/* 368 */         return ((short[])this.data)[index];
/*     */       case 9:
/* 370 */         return ((int[])this.data)[index]; }
/*     */     
/* 372 */     throw new ClassCastException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAsLong(int index) {
/* 394 */     switch (this.type) { case 1:
/*     */       case 7:
/* 396 */         return (((byte[])this.data)[index] & 0xFF);
/*     */       case 6:
/* 398 */         return ((byte[])this.data)[index];
/*     */       case 3:
/* 400 */         return (((char[])this.data)[index] & Character.MAX_VALUE);
/*     */       case 8:
/* 402 */         return ((short[])this.data)[index];
/*     */       case 9:
/* 404 */         return ((int[])this.data)[index];
/*     */       case 4:
/* 406 */         return ((long[])this.data)[index]; }
/*     */     
/* 408 */     throw new ClassCastException();
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
/*     */   public float getAsFloat(int index) {
/*     */     int[] ivalue;
/*     */     long[] lvalue;
/* 427 */     switch (this.type) {
/*     */       case 1:
/* 429 */         return (((byte[])this.data)[index] & 0xFF);
/*     */       case 6:
/* 431 */         return ((byte[])this.data)[index];
/*     */       case 3:
/* 433 */         return (((char[])this.data)[index] & Character.MAX_VALUE);
/*     */       case 8:
/* 435 */         return ((short[])this.data)[index];
/*     */       case 9:
/* 437 */         return ((int[])this.data)[index];
/*     */       case 4:
/* 439 */         return (float)((long[])this.data)[index];
/*     */       case 11:
/* 441 */         return ((float[])this.data)[index];
/*     */       case 12:
/* 443 */         return (float)((double[])this.data)[index];
/*     */       case 10:
/* 445 */         ivalue = getAsSRational(index);
/* 446 */         return (float)(ivalue[0] / ivalue[1]);
/*     */       case 5:
/* 448 */         lvalue = getAsRational(index);
/* 449 */         return (float)(lvalue[0] / lvalue[1]);
/*     */     } 
/* 451 */     throw new ClassCastException();
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
/*     */   public double getAsDouble(int index) {
/*     */     int[] ivalue;
/*     */     long[] lvalue;
/* 468 */     switch (this.type) {
/*     */       case 1:
/* 470 */         return (((byte[])this.data)[index] & 0xFF);
/*     */       case 6:
/* 472 */         return ((byte[])this.data)[index];
/*     */       case 3:
/* 474 */         return (((char[])this.data)[index] & Character.MAX_VALUE);
/*     */       case 8:
/* 476 */         return ((short[])this.data)[index];
/*     */       case 9:
/* 478 */         return ((int[])this.data)[index];
/*     */       case 4:
/* 480 */         return ((long[])this.data)[index];
/*     */       case 11:
/* 482 */         return ((float[])this.data)[index];
/*     */       case 12:
/* 484 */         return ((double[])this.data)[index];
/*     */       case 10:
/* 486 */         ivalue = getAsSRational(index);
/* 487 */         return ivalue[0] / ivalue[1];
/*     */       case 5:
/* 489 */         lvalue = getAsRational(index);
/* 490 */         return lvalue[0] / lvalue[1];
/*     */     } 
/* 492 */     throw new ClassCastException();
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
/*     */   public String getAsString(int index) {
/* 506 */     return ((String[])this.data)[index];
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
/*     */   public int[] getAsSRational(int index) {
/* 520 */     return ((int[][])this.data)[index];
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
/*     */   public long[] getAsRational(int index) {
/* 534 */     if (this.type == 4)
/* 535 */       return getAsLongs(); 
/* 536 */     return ((long[][])this.data)[index];
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
/*     */   public int compareTo(TIFFField o) {
/* 549 */     if (o == null) {
/* 550 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 553 */     int oTag = o.getTag();
/*     */     
/* 555 */     if (this.tag < oTag)
/* 556 */       return -1; 
/* 557 */     if (this.tag > oTag) {
/* 558 */       return 1;
/*     */     }
/* 560 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/TIFFField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */