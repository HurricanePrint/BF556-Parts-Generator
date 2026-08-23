/*     */ package com.itextpdf.kernel.pdf.canvas.wmf;
/*     */ 
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.DeviceRgb;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputMeta
/*     */ {
/*     */   InputStream in;
/*     */   int length;
/*     */   
/*     */   public InputMeta(InputStream in) {
/*  67 */     this.in = in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readWord() throws IOException {
/*  77 */     this.length += 2;
/*  78 */     int k1 = this.in.read();
/*  79 */     if (k1 < 0)
/*  80 */       return 0; 
/*  81 */     return k1 + (this.in.read() << 8) & 0xFFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readShort() throws IOException {
/*  91 */     int k = readWord();
/*  92 */     if (k > 32767)
/*  93 */       k -= 65536; 
/*  94 */     return k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readInt() throws IOException {
/* 104 */     this.length += 4;
/* 105 */     int k1 = this.in.read();
/* 106 */     if (k1 < 0)
/* 107 */       return 0; 
/* 108 */     int k2 = this.in.read() << 8;
/* 109 */     int k3 = this.in.read() << 16;
/* 110 */     return k1 + k2 + k3 + (this.in.read() << 24);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readByte() throws IOException {
/* 120 */     this.length++;
/* 121 */     return this.in.read() & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skip(int len) throws IOException {
/* 131 */     this.length += len;
/* 132 */     StreamUtil.skip(this.in, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 141 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color readColor() throws IOException {
/* 151 */     int red = readByte();
/* 152 */     int green = readByte();
/* 153 */     int blue = readByte();
/* 154 */     readByte();
/* 155 */     return (Color)new DeviceRgb(red, green, blue);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/wmf/InputMeta.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */