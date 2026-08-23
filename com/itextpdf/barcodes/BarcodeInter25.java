/*     */ package com.itextpdf.barcodes;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.MemoryImageSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BarcodeInter25
/*     */   extends Barcode1D
/*     */ {
/*  79 */   private static final byte[][] BARS = new byte[][] { { 0, 0, 1, 1, 0 }, { 1, 0, 0, 0, 1 }, { 0, 1, 0, 0, 1 }, { 1, 1, 0, 0, 0 }, { 0, 0, 1, 0, 1 }, { 1, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1 }, { 1, 0, 0, 1, 0 }, { 0, 1, 0, 1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeInter25(PdfDocument document) {
/* 101 */     this(document, document.getDefaultFont());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeInter25(PdfDocument document, PdfFont font) {
/* 111 */     super(document);
/* 112 */     this.x = 0.8F;
/* 113 */     this.n = 2.0F;
/* 114 */     this.font = font;
/* 115 */     this.size = 8.0F;
/* 116 */     this.baseline = this.size;
/* 117 */     this.barHeight = this.size * 3.0F;
/* 118 */     this.textAlignment = 3;
/* 119 */     this.generateChecksum = false;
/* 120 */     this.checksumText = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String keepNumbers(String text) {
/* 130 */     StringBuilder sb = new StringBuilder();
/* 131 */     for (int k = 0; k < text.length(); k++) {
/* 132 */       char c = text.charAt(k);
/* 133 */       if (c >= '0' && c <= '9') {
/* 134 */         sb.append(c);
/*     */       }
/*     */     } 
/* 137 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char getChecksum(String text) {
/* 147 */     int mul = 3;
/* 148 */     int total = 0;
/* 149 */     for (int k = text.length() - 1; k >= 0; k--) {
/* 150 */       int n = text.charAt(k) - 48;
/* 151 */       total += mul * n;
/* 152 */       mul ^= 0x2;
/*     */     } 
/* 154 */     return (char)((10 - total % 10) % 10 + 48);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsInter25(String text) {
/* 164 */     text = keepNumbers(text);
/* 165 */     if ((text.length() & 0x1) != 0) {
/* 166 */       throw new PdfException("The text length must be even.");
/*     */     }
/* 168 */     byte[] bars = new byte[text.length() * 5 + 7];
/* 169 */     int pb = 0;
/* 170 */     bars[pb++] = 0;
/* 171 */     bars[pb++] = 0;
/* 172 */     bars[pb++] = 0;
/* 173 */     bars[pb++] = 0;
/* 174 */     int len = text.length() / 2;
/* 175 */     for (int k = 0; k < len; k++) {
/* 176 */       int c1 = text.charAt(k * 2) - 48;
/* 177 */       int c2 = text.charAt(k * 2 + 1) - 48;
/* 178 */       byte[] b1 = BARS[c1];
/* 179 */       byte[] b2 = BARS[c2];
/* 180 */       for (int j = 0; j < 5; j++) {
/* 181 */         bars[pb++] = b1[j];
/* 182 */         bars[pb++] = b2[j];
/*     */       } 
/*     */     } 
/* 185 */     bars[pb++] = 1;
/* 186 */     bars[pb++] = 0;
/* 187 */     bars[pb++] = 0;
/* 188 */     return bars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBarcodeSize() {
/* 199 */     float fontX = 0.0F;
/* 200 */     float fontY = 0.0F;
/* 201 */     if (this.font != null) {
/* 202 */       if (this.baseline > 0.0F) {
/* 203 */         fontY = this.baseline - getDescender();
/*     */       } else {
/* 205 */         fontY = -this.baseline + this.size;
/*     */       } 
/* 207 */       String str = this.code;
/* 208 */       if (this.generateChecksum && this.checksumText) {
/* 209 */         str = str + getChecksum(str);
/*     */       }
/* 211 */       fontX = this.font.getWidth((this.altText != null) ? this.altText : str, this.size);
/*     */     } 
/* 213 */     String fullCode = keepNumbers(this.code);
/* 214 */     int len = fullCode.length();
/* 215 */     if (this.generateChecksum) {
/* 216 */       len++;
/*     */     }
/* 218 */     float fullWidth = len * (3.0F * this.x + 2.0F * this.x * this.n) + (6.0F + this.n) * this.x;
/* 219 */     fullWidth = Math.max(fullWidth, fontX);
/* 220 */     float fullHeight = this.barHeight + fontY;
/* 221 */     return new Rectangle(fullWidth, fullHeight);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
/* 265 */     String fullCode = this.code;
/* 266 */     float fontX = 0.0F;
/* 267 */     if (this.font != null) {
/* 268 */       if (this.generateChecksum && this.checksumText)
/* 269 */         fullCode = fullCode + getChecksum(fullCode); 
/* 270 */       fontX = this.font.getWidth(fullCode = (this.altText != null) ? this.altText : fullCode, this.size);
/*     */     } 
/* 272 */     String bCode = keepNumbers(this.code);
/* 273 */     if (this.generateChecksum)
/* 274 */       bCode = bCode + getChecksum(bCode); 
/* 275 */     int len = bCode.length();
/* 276 */     float fullWidth = len * (3.0F * this.x + 2.0F * this.x * this.n) + (6.0F + this.n) * this.x;
/* 277 */     float barStartX = 0.0F;
/* 278 */     float textStartX = 0.0F;
/* 279 */     switch (this.textAlignment) {
/*     */       case 1:
/*     */         break;
/*     */       case 2:
/* 283 */         if (fontX > fullWidth) {
/* 284 */           barStartX = fontX - fullWidth; break;
/*     */         } 
/* 286 */         textStartX = fullWidth - fontX;
/*     */         break;
/*     */       
/*     */       default:
/* 290 */         if (fontX > fullWidth) {
/* 291 */           barStartX = (fontX - fullWidth) / 2.0F; break;
/*     */         } 
/* 293 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */         break;
/*     */     } 
/*     */     
/* 297 */     float barStartY = 0.0F;
/* 298 */     float textStartY = 0.0F;
/* 299 */     if (this.font != null) {
/* 300 */       if (this.baseline <= 0.0F) {
/* 301 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 303 */         textStartY = -getDescender();
/* 304 */         barStartY = textStartY + this.baseline;
/*     */       } 
/*     */     }
/* 307 */     byte[] bars = getBarsInter25(bCode);
/* 308 */     boolean print = true;
/* 309 */     if (barColor != null)
/* 310 */       canvas.setFillColor(barColor); 
/* 311 */     for (int k = 0; k < bars.length; k++) {
/* 312 */       float w = (bars[k] == 0) ? this.x : (this.x * this.n);
/* 313 */       if (print) {
/* 314 */         canvas.rectangle(barStartX, barStartY, (w - this.inkSpreading), this.barHeight);
/*     */       }
/* 316 */       print = !print;
/* 317 */       barStartX += w;
/*     */     } 
/* 319 */     canvas.fill();
/* 320 */     if (this.font != null) {
/* 321 */       if (textColor != null) {
/* 322 */         canvas.setFillColor(textColor);
/*     */       }
/* 324 */       canvas.beginText();
/* 325 */       canvas.setFontAndSize(this.font, this.size);
/* 326 */       canvas.setTextMatrix(textStartX, textStartY);
/* 327 */       canvas.showText(fullCode);
/* 328 */       canvas.endText();
/*     */     } 
/* 330 */     return getBarcodeSize();
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
/*     */   public Image createAwtImage(Color foreground, Color background) {
/* 345 */     int f = (foreground == null) ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
/* 346 */     int g = (background == null) ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
/* 347 */     Canvas canvas = new Canvas();
/* 348 */     String bCode = keepNumbers(this.code);
/* 349 */     if (this.generateChecksum) {
/* 350 */       bCode = bCode + getChecksum(bCode);
/*     */     }
/* 352 */     int len = bCode.length();
/* 353 */     int nn = (int)this.n;
/* 354 */     int fullWidth = len * (3 + 2 * nn) + 6 + nn;
/* 355 */     byte[] bars = getBarsInter25(bCode);
/* 356 */     boolean print = true;
/* 357 */     int ptr = 0;
/* 358 */     int height = (int)this.barHeight;
/* 359 */     int[] pix = new int[fullWidth * height]; int k;
/* 360 */     for (k = 0; k < bars.length; k++) {
/* 361 */       int w = (bars[k] == 0) ? 1 : nn;
/* 362 */       int c = g;
/* 363 */       if (print) {
/* 364 */         c = f;
/*     */       }
/* 366 */       print = !print;
/* 367 */       for (int j = 0; j < w; j++) {
/* 368 */         pix[ptr++] = c;
/*     */       }
/*     */     } 
/* 371 */     for (k = fullWidth; k < pix.length; k += fullWidth) {
/* 372 */       System.arraycopy(pix, 0, pix, k, fullWidth);
/*     */     }
/* 374 */     return canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/BarcodeInter25.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */