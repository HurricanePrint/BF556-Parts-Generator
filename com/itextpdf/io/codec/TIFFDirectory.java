/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TIFFDirectory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -168636766193675380L;
/*     */   boolean isBigEndian;
/*     */   int numEntries;
/*     */   TIFFField[] fields;
/* 101 */   Map<Integer, Integer> fieldIndex = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   long IFDOffset = 8L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   long nextIFDOffset = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   TIFFDirectory() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidEndianTag(int endian) {
/* 120 */     return (endian == 18761 || endian == 19789);
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
/*     */   public TIFFDirectory(RandomAccessFileOrArray stream, int directory) throws IOException {
/* 137 */     long global_save_offset = stream.getPosition();
/*     */ 
/*     */ 
/*     */     
/* 141 */     stream.seek(0L);
/* 142 */     int endian = stream.readUnsignedShort();
/* 143 */     if (!isValidEndianTag(endian)) {
/* 144 */       throw new IOException("Bad endianness tag: 0x4949 or 0x4d4d.");
/*     */     }
/* 146 */     this.isBigEndian = (endian == 19789);
/*     */     
/* 148 */     int magic = readUnsignedShort(stream);
/* 149 */     if (magic != 42) {
/* 150 */       throw new IOException("Bad magic number. Should be 42.");
/*     */     }
/*     */ 
/*     */     
/* 154 */     long ifd_offset = readUnsignedInt(stream);
/*     */     
/* 156 */     for (int i = 0; i < directory; i++) {
/* 157 */       if (ifd_offset == 0L) {
/* 158 */         throw new IOException("Directory number is too large.");
/*     */       }
/*     */       
/* 161 */       stream.seek(ifd_offset);
/* 162 */       int entries = readUnsignedShort(stream);
/* 163 */       stream.skip((12 * entries));
/*     */       
/* 165 */       ifd_offset = readUnsignedInt(stream);
/*     */     } 
/*     */     
/* 168 */     stream.seek(ifd_offset);
/* 169 */     initialize(stream);
/* 170 */     stream.seek(global_save_offset);
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
/*     */   public TIFFDirectory(RandomAccessFileOrArray stream, long ifd_offset, int directory) throws IOException {
/* 190 */     long global_save_offset = stream.getPosition();
/* 191 */     stream.seek(0L);
/* 192 */     int endian = stream.readUnsignedShort();
/* 193 */     if (!isValidEndianTag(endian)) {
/* 194 */       throw new IOException("Bad endianness tag: 0x4949 or 0x4d4d.");
/*     */     }
/* 196 */     this.isBigEndian = (endian == 19789);
/*     */ 
/*     */     
/* 199 */     stream.seek(ifd_offset);
/*     */ 
/*     */     
/* 202 */     int dirNum = 0;
/* 203 */     while (dirNum < directory) {
/*     */       
/* 205 */       int numEntries = readUnsignedShort(stream);
/*     */ 
/*     */       
/* 208 */       stream.seek(ifd_offset + (12 * numEntries));
/*     */ 
/*     */       
/* 211 */       ifd_offset = readUnsignedInt(stream);
/*     */ 
/*     */       
/* 214 */       stream.seek(ifd_offset);
/*     */ 
/*     */       
/* 217 */       dirNum++;
/*     */     } 
/*     */     
/* 220 */     initialize(stream);
/* 221 */     stream.seek(global_save_offset);
/*     */   }
/*     */   
/* 224 */   private static final int[] sizeOfType = new int[] { 0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize(RandomAccessFileOrArray stream) throws IOException {
/* 267 */     long nextTagOffset = 0L;
/* 268 */     long maxOffset = stream.length();
/*     */ 
/*     */     
/* 271 */     this.IFDOffset = stream.getPosition();
/*     */     
/* 273 */     this.numEntries = readUnsignedShort(stream);
/* 274 */     this.fields = new TIFFField[this.numEntries];
/*     */     
/* 276 */     for (int i = 0; i < this.numEntries && nextTagOffset < maxOffset; i++) {
/* 277 */       int tag = readUnsignedShort(stream);
/* 278 */       int type = readUnsignedShort(stream);
/* 279 */       int count = (int)readUnsignedInt(stream);
/* 280 */       boolean processTag = true;
/*     */ 
/*     */       
/* 283 */       nextTagOffset = stream.getPosition() + 4L;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 288 */         if (count * sizeOfType[type] > 4) {
/* 289 */           long valueOffset = readUnsignedInt(stream);
/*     */ 
/*     */           
/* 292 */           if (valueOffset < maxOffset) {
/* 293 */             stream.seek(valueOffset);
/*     */           } else {
/*     */             
/* 296 */             processTag = false;
/*     */           } 
/*     */         } 
/* 299 */       } catch (ArrayIndexOutOfBoundsException ae) {
/*     */         
/* 301 */         processTag = false;
/*     */       } 
/*     */       
/* 304 */       if (processTag) {
/* 305 */         int j; byte[] bvalues; char[] cvalues; long lvalues[], llvalues[][]; short[] svalues; int ivalues[], iivalues[][]; float[] fvalues; double[] dvalues; this.fieldIndex.put(Integer.valueOf(tag), Integer.valueOf(i));
/* 306 */         Object obj = null;
/*     */         
/* 308 */         switch (type) {
/*     */           case 1:
/*     */           case 2:
/*     */           case 6:
/*     */           case 7:
/* 313 */             bvalues = new byte[count];
/* 314 */             stream.readFully(bvalues, 0, count);
/*     */             
/* 316 */             if (type == 2) {
/*     */ 
/*     */               
/* 319 */               int index = 0, prevIndex = 0;
/* 320 */               List<String> v = new ArrayList<>();
/*     */               
/* 322 */               while (index < count) {
/*     */                 
/* 324 */                 while (index < count && bvalues[index++] != 0);
/*     */ 
/*     */                 
/* 327 */                 v.add(new String(bvalues, prevIndex, index - prevIndex));
/*     */                 
/* 329 */                 prevIndex = index;
/*     */               } 
/*     */               
/* 332 */               count = v.size();
/* 333 */               String[] strings = new String[count];
/* 334 */               for (int c = 0; c < count; c++) {
/* 335 */                 strings[c] = v.get(c);
/*     */               }
/*     */               
/* 338 */               obj = strings; break;
/*     */             } 
/* 340 */             obj = bvalues;
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case 3:
/* 346 */             cvalues = new char[count];
/* 347 */             for (j = 0; j < count; j++) {
/* 348 */               cvalues[j] = (char)readUnsignedShort(stream);
/*     */             }
/* 350 */             obj = cvalues;
/*     */             break;
/*     */           
/*     */           case 4:
/* 354 */             lvalues = new long[count];
/* 355 */             for (j = 0; j < count; j++) {
/* 356 */               lvalues[j] = readUnsignedInt(stream);
/*     */             }
/* 358 */             obj = lvalues;
/*     */             break;
/*     */           
/*     */           case 5:
/* 362 */             llvalues = new long[count][];
/* 363 */             for (j = 0; j < count; j++) {
/* 364 */               llvalues[j] = new long[2];
/* 365 */               llvalues[j][0] = readUnsignedInt(stream);
/* 366 */               llvalues[j][1] = readUnsignedInt(stream);
/*     */             } 
/* 368 */             obj = llvalues;
/*     */             break;
/*     */           
/*     */           case 8:
/* 372 */             svalues = new short[count];
/* 373 */             for (j = 0; j < count; j++) {
/* 374 */               svalues[j] = readShort(stream);
/*     */             }
/* 376 */             obj = svalues;
/*     */             break;
/*     */           
/*     */           case 9:
/* 380 */             ivalues = new int[count];
/* 381 */             for (j = 0; j < count; j++) {
/* 382 */               ivalues[j] = readInt(stream);
/*     */             }
/* 384 */             obj = ivalues;
/*     */             break;
/*     */           
/*     */           case 10:
/* 388 */             iivalues = new int[count][];
/* 389 */             for (j = 0; j < count; j++) {
/* 390 */               iivalues[j] = new int[2];
/* 391 */               iivalues[j][0] = readInt(stream);
/* 392 */               iivalues[j][1] = readInt(stream);
/*     */             } 
/* 394 */             obj = iivalues;
/*     */             break;
/*     */           
/*     */           case 11:
/* 398 */             fvalues = new float[count];
/* 399 */             for (j = 0; j < count; j++) {
/* 400 */               fvalues[j] = readFloat(stream);
/*     */             }
/* 402 */             obj = fvalues;
/*     */             break;
/*     */           
/*     */           case 12:
/* 406 */             dvalues = new double[count];
/* 407 */             for (j = 0; j < count; j++) {
/* 408 */               dvalues[j] = readDouble(stream);
/*     */             }
/* 410 */             obj = dvalues;
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 417 */         this.fields[i] = new TIFFField(tag, type, count, obj);
/*     */       } 
/*     */       
/* 420 */       stream.seek(nextTagOffset);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 425 */       this.nextIFDOffset = readUnsignedInt(stream);
/* 426 */     } catch (Exception e) {
/*     */       
/* 428 */       this.nextIFDOffset = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumEntries() {
/* 437 */     return this.numEntries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIFFField getField(int tag) {
/* 447 */     Integer i = this.fieldIndex.get(Integer.valueOf(tag));
/* 448 */     if (i == null) {
/* 449 */       return null;
/*     */     }
/* 451 */     return this.fields[i.intValue()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTagPresent(int tag) {
/* 461 */     return this.fieldIndex.containsKey(Integer.valueOf(tag));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getTags() {
/* 470 */     int[] tags = new int[this.fieldIndex.size()];
/* 471 */     int i = 0;
/*     */     
/* 473 */     for (Integer integer : this.fieldIndex.keySet()) {
/* 474 */       tags[i++] = integer.intValue();
/*     */     }
/*     */     
/* 477 */     return tags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIFFField[] getFields() {
/* 486 */     return this.fields;
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
/*     */   public byte getFieldAsByte(int tag, int index) {
/* 499 */     Integer i = this.fieldIndex.get(Integer.valueOf(tag));
/* 500 */     byte[] b = this.fields[i.intValue()].getAsBytes();
/* 501 */     return b[index];
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
/*     */   public byte getFieldAsByte(int tag) {
/* 513 */     return getFieldAsByte(tag, 0);
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
/*     */   public long getFieldAsLong(int tag, int index) {
/* 527 */     Integer i = this.fieldIndex.get(Integer.valueOf(tag));
/* 528 */     return this.fields[i.intValue()].getAsLong(index);
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
/*     */   public long getFieldAsLong(int tag) {
/* 541 */     return getFieldAsLong(tag, 0);
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
/*     */   public float getFieldAsFloat(int tag, int index) {
/* 555 */     Integer i = this.fieldIndex.get(Integer.valueOf(tag));
/* 556 */     return this.fields[i.intValue()].getAsFloat(index);
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
/*     */   public float getFieldAsFloat(int tag) {
/* 568 */     return getFieldAsFloat(tag, 0);
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
/*     */   public double getFieldAsDouble(int tag, int index) {
/* 582 */     Integer i = this.fieldIndex.get(Integer.valueOf(tag));
/* 583 */     return this.fields[i.intValue()].getAsDouble(index);
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
/*     */   public double getFieldAsDouble(int tag) {
/* 595 */     return getFieldAsDouble(tag, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private short readShort(RandomAccessFileOrArray stream) throws IOException {
/* 602 */     if (this.isBigEndian) {
/* 603 */       return stream.readShort();
/*     */     }
/* 605 */     return stream.readShortLE();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int readUnsignedShort(RandomAccessFileOrArray stream) throws IOException {
/* 611 */     if (this.isBigEndian) {
/* 612 */       return stream.readUnsignedShort();
/*     */     }
/* 614 */     return stream.readUnsignedShortLE();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int readInt(RandomAccessFileOrArray stream) throws IOException {
/* 620 */     if (this.isBigEndian) {
/* 621 */       return stream.readInt();
/*     */     }
/* 623 */     return stream.readIntLE();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long readUnsignedInt(RandomAccessFileOrArray stream) throws IOException {
/* 629 */     if (this.isBigEndian) {
/* 630 */       return stream.readUnsignedInt();
/*     */     }
/* 632 */     return stream.readUnsignedIntLE();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long readLong(RandomAccessFileOrArray stream) throws IOException {
/* 638 */     if (this.isBigEndian) {
/* 639 */       return stream.readLong();
/*     */     }
/* 641 */     return stream.readLongLE();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float readFloat(RandomAccessFileOrArray stream) throws IOException {
/* 647 */     if (this.isBigEndian) {
/* 648 */       return stream.readFloat();
/*     */     }
/* 650 */     return stream.readFloatLE();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double readDouble(RandomAccessFileOrArray stream) throws IOException {
/* 656 */     if (this.isBigEndian) {
/* 657 */       return stream.readDouble();
/*     */     }
/* 659 */     return stream.readDoubleLE();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readUnsignedShort(RandomAccessFileOrArray stream, boolean isBigEndian) throws IOException {
/* 666 */     if (isBigEndian) {
/* 667 */       return stream.readUnsignedShort();
/*     */     }
/* 669 */     return stream.readUnsignedShortLE();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long readUnsignedInt(RandomAccessFileOrArray stream, boolean isBigEndian) throws IOException {
/* 676 */     if (isBigEndian) {
/* 677 */       return stream.readUnsignedInt();
/*     */     }
/* 679 */     return stream.readUnsignedIntLE();
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
/*     */   public static int getNumDirectories(RandomAccessFileOrArray stream) throws IOException {
/* 698 */     long pointer = stream.getPosition();
/*     */     
/* 700 */     stream.seek(0L);
/* 701 */     int endian = stream.readUnsignedShort();
/* 702 */     if (!isValidEndianTag(endian)) {
/* 703 */       throw new IOException("Bad endianness tag: 0x4949 or 0x4d4d.");
/*     */     }
/* 705 */     boolean isBigEndian = (endian == 19789);
/* 706 */     int magic = readUnsignedShort(stream, isBigEndian);
/* 707 */     if (magic != 42) {
/* 708 */       throw new IOException("Bad magic number. Should be 42.");
/*     */     }
/*     */     
/* 711 */     stream.seek(4L);
/* 712 */     long offset = readUnsignedInt(stream, isBigEndian);
/*     */     
/* 714 */     int numDirectories = 0;
/* 715 */     while (offset != 0L) {
/* 716 */       numDirectories++;
/*     */ 
/*     */       
/*     */       try {
/* 720 */         stream.seek(offset);
/* 721 */         int entries = readUnsignedShort(stream, isBigEndian);
/* 722 */         stream.skip((12 * entries));
/* 723 */         offset = readUnsignedInt(stream, isBigEndian);
/* 724 */       } catch (EOFException eof) {
/* 725 */         numDirectories--;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 731 */     stream.seek(pointer);
/* 732 */     return numDirectories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBigEndian() {
/* 743 */     return this.isBigEndian;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getIFDOffset() {
/* 752 */     return this.IFDOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getNextIFDOffset() {
/* 763 */     return this.nextIFDOffset;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/TIFFDirectory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */