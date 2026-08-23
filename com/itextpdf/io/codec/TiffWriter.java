/*     */ package com.itextpdf.io.codec;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TiffWriter
/*     */ {
/*  56 */   private TreeMap<Integer, FieldBase> ifd = new TreeMap<>();
/*     */   
/*     */   public void addField(FieldBase field) {
/*  59 */     this.ifd.put(Integer.valueOf(field.getTag()), field);
/*     */   }
/*     */   
/*     */   public int getIfdSize() {
/*  63 */     return 6 + this.ifd.size() * 12;
/*     */   }
/*     */   
/*     */   public void writeFile(OutputStream stream) throws IOException {
/*  67 */     stream.write(77);
/*  68 */     stream.write(77);
/*  69 */     stream.write(0);
/*  70 */     stream.write(42);
/*  71 */     writeLong(8, stream);
/*  72 */     writeShort(this.ifd.size(), stream);
/*  73 */     int offset = 8 + getIfdSize();
/*  74 */     for (FieldBase field : this.ifd.values()) {
/*  75 */       int size = field.getValueSize();
/*  76 */       if (size > 4) {
/*  77 */         field.setOffset(offset);
/*  78 */         offset += size;
/*     */       } 
/*  80 */       field.writeField(stream);
/*     */     } 
/*  82 */     writeLong(0, stream);
/*  83 */     for (FieldBase field : this.ifd.values()) {
/*  84 */       field.writeValue(stream);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class FieldBase
/*     */   {
/*     */     private int tag;
/*     */     
/*     */     private int fieldType;
/*     */     private int count;
/*     */     protected byte[] data;
/*     */     private int offset;
/*     */     
/*     */     protected FieldBase(int tag, int fieldType, int count) {
/*  99 */       this.tag = tag;
/* 100 */       this.fieldType = fieldType;
/* 101 */       this.count = count;
/*     */     }
/*     */     
/*     */     public int getValueSize() {
/* 105 */       return this.data.length + 1 & 0xFFFFFFFE;
/*     */     }
/*     */     
/*     */     public int getTag() {
/* 109 */       return this.tag;
/*     */     }
/*     */     
/*     */     public void setOffset(int offset) {
/* 113 */       this.offset = offset;
/*     */     }
/*     */     
/*     */     public void writeField(OutputStream stream) throws IOException {
/* 117 */       TiffWriter.writeShort(this.tag, stream);
/* 118 */       TiffWriter.writeShort(this.fieldType, stream);
/* 119 */       TiffWriter.writeLong(this.count, stream);
/* 120 */       if (this.data.length <= 4) {
/* 121 */         stream.write(this.data);
/* 122 */         for (int k = this.data.length; k < 4; k++) {
/* 123 */           stream.write(0);
/*     */         }
/*     */       } else {
/* 126 */         TiffWriter.writeLong(this.offset, stream);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeValue(OutputStream stream) throws IOException {
/* 131 */       if (this.data.length <= 4)
/*     */         return; 
/* 133 */       stream.write(this.data);
/* 134 */       if ((this.data.length & 0x1) == 1) {
/* 135 */         stream.write(0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FieldShort
/*     */     extends FieldBase
/*     */   {
/*     */     public FieldShort(int tag, int value) {
/* 144 */       super(tag, 3, 1);
/* 145 */       this.data = new byte[2];
/* 146 */       this.data[0] = (byte)(value >> 8);
/* 147 */       this.data[1] = (byte)value;
/*     */     }
/*     */     
/*     */     public FieldShort(int tag, int[] values) {
/* 151 */       super(tag, 3, values.length);
/* 152 */       this.data = new byte[values.length * 2];
/* 153 */       int ptr = 0;
/* 154 */       for (int value : values) {
/* 155 */         this.data[ptr++] = (byte)(value >> 8);
/* 156 */         this.data[ptr++] = (byte)value;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldLong
/*     */     extends FieldBase
/*     */   {
/*     */     public FieldLong(int tag, int value) {
/* 166 */       super(tag, 4, 1);
/* 167 */       this.data = new byte[4];
/* 168 */       this.data[0] = (byte)(value >> 24);
/* 169 */       this.data[1] = (byte)(value >> 16);
/* 170 */       this.data[2] = (byte)(value >> 8);
/* 171 */       this.data[3] = (byte)value;
/*     */     }
/*     */     
/*     */     public FieldLong(int tag, int[] values) {
/* 175 */       super(tag, 4, values.length);
/* 176 */       this.data = new byte[values.length * 4];
/* 177 */       int ptr = 0;
/* 178 */       for (int value : values) {
/* 179 */         this.data[ptr++] = (byte)(value >> 24);
/* 180 */         this.data[ptr++] = (byte)(value >> 16);
/* 181 */         this.data[ptr++] = (byte)(value >> 8);
/* 182 */         this.data[ptr++] = (byte)value;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldRational
/*     */     extends FieldBase
/*     */   {
/*     */     public FieldRational(int tag, int[] value) {
/* 192 */       this(tag, new int[][] { value });
/*     */     }
/*     */     
/*     */     public FieldRational(int tag, int[][] values) {
/* 196 */       super(tag, 5, values.length);
/* 197 */       this.data = new byte[values.length * 8];
/* 198 */       int ptr = 0;
/* 199 */       for (int[] value : values) {
/* 200 */         this.data[ptr++] = (byte)(value[0] >> 24);
/* 201 */         this.data[ptr++] = (byte)(value[0] >> 16);
/* 202 */         this.data[ptr++] = (byte)(value[0] >> 8);
/* 203 */         this.data[ptr++] = (byte)value[0];
/* 204 */         this.data[ptr++] = (byte)(value[1] >> 24);
/* 205 */         this.data[ptr++] = (byte)(value[1] >> 16);
/* 206 */         this.data[ptr++] = (byte)(value[1] >> 8);
/* 207 */         this.data[ptr++] = (byte)value[1];
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldByte
/*     */     extends FieldBase
/*     */   {
/*     */     public FieldByte(int tag, byte[] values) {
/* 217 */       super(tag, 1, values.length);
/* 218 */       this.data = values;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldUndefined
/*     */     extends FieldBase
/*     */   {
/*     */     public FieldUndefined(int tag, byte[] values) {
/* 227 */       super(tag, 7, values.length);
/* 228 */       this.data = values;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldImage
/*     */     extends FieldBase
/*     */   {
/*     */     public FieldImage(byte[] values) {
/* 237 */       super(273, 4, 1);
/* 238 */       this.data = values;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldAscii
/*     */     extends FieldBase
/*     */   {
/*     */     public FieldAscii(int tag, String values) {
/* 247 */       super(tag, 2, (values.getBytes(StandardCharsets.US_ASCII)).length + 1);
/* 248 */       byte[] b = values.getBytes(StandardCharsets.US_ASCII);
/* 249 */       this.data = new byte[b.length + 1];
/* 250 */       System.arraycopy(b, 0, this.data, 0, b.length);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void writeShort(int v, OutputStream stream) throws IOException {
/* 255 */     stream.write(v >> 8 & 0xFF);
/* 256 */     stream.write(v & 0xFF);
/*     */   }
/*     */   
/*     */   public static void writeLong(int v, OutputStream stream) throws IOException {
/* 260 */     stream.write(v >> 24 & 0xFF);
/* 261 */     stream.write(v >> 16 & 0xFF);
/* 262 */     stream.write(v >> 8 & 0xFF);
/* 263 */     stream.write(v & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void compressLZW(OutputStream stream, int predictor, byte[] b, int height, int samplesPerPixel, int stride) throws IOException {
/* 268 */     LZWCompressor lzwCompressor = new LZWCompressor(stream, 8, true);
/* 269 */     boolean usePredictor = (predictor == 2);
/*     */     
/* 271 */     if (!usePredictor) {
/* 272 */       lzwCompressor.compress(b, 0, b.length);
/*     */     } else {
/* 274 */       int off = 0;
/* 275 */       byte[] rowBuf = new byte[stride];
/* 276 */       for (int i = 0; i < height; i++) {
/* 277 */         System.arraycopy(b, off, rowBuf, 0, stride);
/* 278 */         for (int j = stride - 1; j >= samplesPerPixel; j--) {
/* 279 */           rowBuf[j] = (byte)(rowBuf[j] - rowBuf[j - samplesPerPixel]);
/*     */         }
/* 281 */         lzwCompressor.compress(rowBuf, 0, stride);
/* 282 */         off += stride;
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     lzwCompressor.flush();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/TiffWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */