/*     */ package com.itextpdf.barcodes;
/*     */ 
/*     */ import com.itextpdf.barcodes.qrcode.ByteMatrix;
/*     */ import com.itextpdf.barcodes.qrcode.EncodeHintType;
/*     */ import com.itextpdf.barcodes.qrcode.QRCodeWriter;
/*     */ import com.itextpdf.barcodes.qrcode.WriterException;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.MemoryImageSource;
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
/*     */ public class BarcodeQRCode
/*     */   extends Barcode2D
/*     */ {
/*     */   ByteMatrix bm;
/*     */   Map<EncodeHintType, Object> hints;
/*     */   String code;
/*     */   
/*     */   public BarcodeQRCode(String code, Map<EncodeHintType, Object> hints) {
/*  77 */     this.code = code;
/*  78 */     this.hints = hints;
/*  79 */     regenerate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeQRCode(String content) {
/*  89 */     this(content, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeQRCode() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCode() {
/* 100 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCode(String code) {
/* 108 */     this.code = code;
/* 109 */     regenerate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<EncodeHintType, Object> getHints() {
/* 116 */     return this.hints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHints(Map<EncodeHintType, Object> hints) {
/* 127 */     this.hints = hints;
/* 128 */     regenerate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void regenerate() {
/* 135 */     if (this.code != null) {
/*     */       try {
/* 137 */         QRCodeWriter qc = new QRCodeWriter();
/* 138 */         this.bm = qc.encode(this.code, 1, 1, this.hints);
/* 139 */       } catch (WriterException ex) {
/* 140 */         throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBarcodeSize() {
/* 150 */     return new Rectangle(0.0F, 0.0F, this.bm.getWidth(), this.bm.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBarcodeSize(float moduleSize) {
/* 159 */     return new Rectangle(0.0F, 0.0F, this.bm.getWidth() * moduleSize, this.bm.getHeight() * moduleSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color foreground) {
/* 164 */     return placeBarcode(canvas, foreground, 1.0F);
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
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color foreground, float moduleSide) {
/* 178 */     int width = this.bm.getWidth();
/* 179 */     int height = this.bm.getHeight();
/* 180 */     byte[][] mt = this.bm.getArray();
/*     */     
/* 182 */     if (foreground != null) {
/* 183 */       canvas.setFillColor(foreground);
/*     */     }
/*     */     
/* 186 */     for (int y = 0; y < height; y++) {
/* 187 */       byte[] line = mt[y];
/* 188 */       for (int x = 0; x < width; x++) {
/* 189 */         if (line[x] == 0) {
/* 190 */           canvas.rectangle((x * moduleSide), ((height - y - 1) * moduleSide), moduleSide, moduleSide);
/*     */         }
/*     */       } 
/*     */     } 
/* 194 */     canvas.fill();
/*     */     
/* 196 */     return getBarcodeSize(moduleSide);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject createFormXObject(Color foreground, PdfDocument document) {
/* 207 */     return createFormXObject(foreground, 1.0F, document);
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
/*     */   public PdfFormXObject createFormXObject(Color foreground, float moduleSize, PdfDocument document) {
/* 219 */     PdfFormXObject xObject = new PdfFormXObject((Rectangle)null);
/* 220 */     Rectangle rect = placeBarcode(new PdfCanvas(xObject, document), foreground, moduleSize);
/* 221 */     xObject.setBBox(new PdfArray(rect));
/*     */     
/* 223 */     return xObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image createAwtImage(Color foreground, Color background) {
/* 234 */     int f = foreground.getRGB();
/* 235 */     int g = background.getRGB();
/* 236 */     Canvas canvas = new Canvas();
/*     */     
/* 238 */     int width = this.bm.getWidth();
/* 239 */     int height = this.bm.getHeight();
/* 240 */     int[] pix = new int[width * height];
/* 241 */     byte[][] mt = this.bm.getArray();
/* 242 */     for (int y = 0; y < height; y++) {
/* 243 */       byte[] line = mt[y];
/* 244 */       for (int x = 0; x < width; x++) {
/* 245 */         pix[y * width + x] = (line[x] == 0) ? f : g;
/*     */       }
/*     */     } 
/*     */     
/* 249 */     Image img = canvas.createImage(new MemoryImageSource(width, height, pix, 0, width));
/* 250 */     return img;
/*     */   }
/*     */   
/*     */   private byte[] getBitMatrix() {
/* 254 */     int width = this.bm.getWidth();
/* 255 */     int height = this.bm.getHeight();
/* 256 */     int stride = (width + 7) / 8;
/* 257 */     byte[] b = new byte[stride * height];
/* 258 */     byte[][] mt = this.bm.getArray();
/* 259 */     for (int y = 0; y < height; y++) {
/* 260 */       byte[] line = mt[y];
/* 261 */       for (int x = 0; x < width; x++) {
/* 262 */         if (line[x] != 0) {
/* 263 */           int offset = stride * y + x / 8;
/* 264 */           b[offset] = (byte)(b[offset] | (byte)(128 >> x % 8));
/*     */         } 
/*     */       } 
/*     */     } 
/* 268 */     return b;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/BarcodeQRCode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */