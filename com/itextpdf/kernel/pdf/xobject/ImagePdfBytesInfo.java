/*     */ package com.itextpdf.kernel.pdf.xobject;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.codec.PngWriter;
/*     */ import com.itextpdf.io.codec.TiffWriter;
/*     */ import com.itextpdf.kernel.Version;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ImagePdfBytesInfo
/*     */ {
/*     */   private int pngColorType;
/*     */   private int pngBitDepth;
/*     */   private int bpc;
/*     */   private byte[] palette;
/*     */   private byte[] icc;
/*     */   private int stride;
/*     */   private int width;
/*     */   private int height;
/*     */   private PdfObject colorspace;
/*     */   private PdfArray decode;
/*     */   
/*     */   public ImagePdfBytesInfo(PdfImageXObject imageXObject) {
/*  69 */     this.pngColorType = -1;
/*  70 */     this.bpc = ((PdfStream)imageXObject.getPdfObject()).getAsNumber(PdfName.BitsPerComponent).intValue();
/*  71 */     this.pngBitDepth = this.bpc;
/*     */     
/*  73 */     this.palette = null;
/*  74 */     this.icc = null;
/*  75 */     this.stride = 0;
/*  76 */     this.width = (int)imageXObject.getWidth();
/*  77 */     this.height = (int)imageXObject.getHeight();
/*  78 */     this.colorspace = ((PdfStream)imageXObject.getPdfObject()).get(PdfName.ColorSpace);
/*  79 */     this.decode = ((PdfStream)imageXObject.getPdfObject()).getAsArray(PdfName.Decode);
/*  80 */     findColorspace(this.colorspace, true);
/*     */   }
/*     */   
/*     */   public int getPngColorType() {
/*  84 */     return this.pngColorType;
/*     */   }
/*     */   
/*     */   public byte[] decodeTiffAndPngBytes(byte[] imageBytes) throws IOException {
/*  88 */     ByteArrayOutputStream ms = new ByteArrayOutputStream();
/*  89 */     if (this.pngColorType < 0) {
/*  90 */       if (this.bpc != 8) {
/*  91 */         throw (new IOException("The color depth {0} is not supported.")).setMessageParams(new Object[] { Integer.valueOf(this.bpc) });
/*     */       }
/*  93 */       if (this.colorspace instanceof PdfArray) {
/*  94 */         PdfArray ca = (PdfArray)this.colorspace;
/*  95 */         PdfObject tyca = ca.get(0);
/*  96 */         if (!PdfName.ICCBased.equals(tyca))
/*  97 */           throw (new IOException("The color space {0} is not supported.")).setMessageParams(new Object[] { tyca.toString() }); 
/*  98 */         PdfStream pr = (PdfStream)ca.get(1);
/*  99 */         int n = pr.getAsNumber(PdfName.N).intValue();
/* 100 */         if (n != 4) {
/* 101 */           throw (new IOException("N value {1} is not supported.")).setMessageParams(new Object[] { Integer.valueOf(n) });
/*     */         }
/* 103 */         this.icc = pr.getBytes();
/* 104 */       } else if (!PdfName.DeviceCMYK.equals(this.colorspace)) {
/* 105 */         throw (new IOException("The color space {0} is not supported.")).setMessageParams(new Object[] { this.colorspace.toString() });
/*     */       } 
/* 107 */       this.stride = 4 * this.width;
/* 108 */       TiffWriter wr = new TiffWriter();
/* 109 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldShort(277, 4));
/* 110 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldShort(258, new int[] { 8, 8, 8, 8 }));
/* 111 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldShort(262, 5));
/* 112 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldLong(256, this.width));
/* 113 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldLong(257, this.height));
/* 114 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldShort(259, 5));
/* 115 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldShort(317, 2));
/* 116 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldLong(278, this.height));
/* 117 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldRational(282, new int[] { 300, 1 }));
/* 118 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldRational(283, new int[] { 300, 1 }));
/* 119 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldShort(296, 2));
/* 120 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldAscii(305, Version.getInstance().getVersion()));
/* 121 */       ByteArrayOutputStream comp = new ByteArrayOutputStream();
/* 122 */       TiffWriter.compressLZW(comp, 2, imageBytes, this.height, 4, this.stride);
/* 123 */       byte[] buf = comp.toByteArray();
/* 124 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldImage(buf));
/* 125 */       wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldLong(279, buf.length));
/* 126 */       if (this.icc != null) {
/* 127 */         wr.addField((TiffWriter.FieldBase)new TiffWriter.FieldUndefined(34675, this.icc));
/*     */       }
/* 129 */       wr.writeFile(ms);
/*     */       
/* 131 */       imageBytes = ms.toByteArray();
/* 132 */       return imageBytes;
/*     */     } 
/* 134 */     PngWriter png = new PngWriter(ms);
/* 135 */     if (this.decode != null && 
/* 136 */       this.pngBitDepth == 1)
/*     */     {
/* 138 */       if (this.decode.getAsNumber(0).intValue() == 1 && this.decode.getAsNumber(1).intValue() == 0) {
/* 139 */         int len = imageBytes.length;
/* 140 */         for (int t = 0; t < len; t++) {
/* 141 */           imageBytes[t] = (byte)(imageBytes[t] ^ 0xFF);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     png.writeHeader(this.width, this.height, this.pngBitDepth, this.pngColorType);
/* 151 */     if (this.icc != null) {
/* 152 */       png.writeIccProfile(this.icc);
/*     */     }
/* 154 */     if (this.palette != null) {
/* 155 */       png.writePalette(this.palette);
/*     */     }
/* 157 */     png.writeData(imageBytes, this.stride);
/* 158 */     png.writeEnd();
/* 159 */     imageBytes = ms.toByteArray();
/* 160 */     return imageBytes;
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
/*     */   private void findColorspace(PdfObject csObj, boolean allowIndexed) {
/* 172 */     if (PdfName.DeviceGray.equals(csObj) || (csObj == null && this.bpc == 1)) {
/*     */       
/* 174 */       this.stride = (this.width * this.bpc + 7) / 8;
/* 175 */       this.pngColorType = 0;
/* 176 */     } else if (PdfName.DeviceRGB.equals(csObj)) {
/* 177 */       if (this.bpc == 8 || this.bpc == 16) {
/* 178 */         this.stride = (this.width * this.bpc * 3 + 7) / 8;
/* 179 */         this.pngColorType = 2;
/*     */       } 
/* 181 */     } else if (csObj instanceof PdfArray) {
/* 182 */       PdfArray ca = (PdfArray)csObj;
/* 183 */       PdfObject tyca = ca.get(0);
/* 184 */       if (PdfName.CalGray.equals(tyca)) {
/* 185 */         this.stride = (this.width * this.bpc + 7) / 8;
/* 186 */         this.pngColorType = 0;
/* 187 */       } else if (PdfName.CalRGB.equals(tyca)) {
/* 188 */         if (this.bpc == 8 || this.bpc == 16) {
/* 189 */           this.stride = (this.width * this.bpc * 3 + 7) / 8;
/* 190 */           this.pngColorType = 2;
/*     */         } 
/* 192 */       } else if (PdfName.ICCBased.equals(tyca)) {
/* 193 */         PdfStream pr = (PdfStream)ca.get(1);
/* 194 */         int n = pr.getAsNumber(PdfName.N).intValue();
/* 195 */         if (n == 1) {
/* 196 */           this.stride = (this.width * this.bpc + 7) / 8;
/* 197 */           this.pngColorType = 0;
/* 198 */           this.icc = pr.getBytes();
/* 199 */         } else if (n == 3) {
/* 200 */           this.stride = (this.width * this.bpc * 3 + 7) / 8;
/* 201 */           this.pngColorType = 2;
/* 202 */           this.icc = pr.getBytes();
/*     */         } 
/* 204 */       } else if (allowIndexed && PdfName.Indexed.equals(tyca)) {
/* 205 */         findColorspace(ca.get(1), false);
/* 206 */         if (this.pngColorType == 2) {
/* 207 */           PdfObject id2 = ca.get(3);
/* 208 */           if (id2 instanceof PdfString) {
/* 209 */             this.palette = ((PdfString)id2).getValueBytes();
/* 210 */           } else if (id2 instanceof PdfStream) {
/* 211 */             this.palette = ((PdfStream)id2).getBytes();
/*     */           } 
/* 213 */           this.stride = (this.width * this.bpc + 7) / 8;
/* 214 */           this.pngColorType = 3;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/xobject/ImagePdfBytesInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */