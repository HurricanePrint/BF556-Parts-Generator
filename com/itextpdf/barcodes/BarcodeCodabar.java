/*     */ package com.itextpdf.barcodes;
/*     */ 
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
/*     */ public class BarcodeCodabar
/*     */   extends Barcode1D
/*     */ {
/*     */   private static final String CHARS = "0123456789-$:/.+ABCD";
/*     */   private static final int START_STOP_IDX = 16;
/*  65 */   private static final byte[][] BARS = new byte[][] { { 0, 0, 0, 0, 0, 1, 1 }, { 0, 0, 0, 0, 1, 1, 0 }, { 0, 0, 0, 1, 0, 0, 1 }, { 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 1, 0 }, { 1, 0, 0, 0, 0, 1, 0 }, { 0, 1, 0, 0, 0, 0, 1 }, { 0, 1, 0, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0, 0, 0 }, { 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 1, 1, 0, 0, 0 }, { 1, 0, 0, 0, 1, 0, 1 }, { 1, 0, 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1, 0, 0 }, { 0, 0, 1, 0, 1, 0, 1 }, { 0, 0, 1, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 0, 1 }, { 0, 0, 0, 1, 0, 1, 1 }, { 0, 0, 0, 1, 1, 1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeCodabar(PdfDocument document) {
/* 137 */     this(document, document.getDefaultFont());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeCodabar(PdfDocument document, PdfFont font) {
/* 147 */     super(document);
/* 148 */     this.x = 0.8F;
/* 149 */     this.n = 2.0F;
/* 150 */     this.font = font;
/* 151 */     this.size = 8.0F;
/* 152 */     this.baseline = this.size;
/* 153 */     this.barHeight = this.size * 3.0F;
/* 154 */     this.textAlignment = 3;
/* 155 */     this.generateChecksum = false;
/* 156 */     this.checksumText = false;
/* 157 */     this.startStopText = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsCodabar(String text) {
/* 167 */     text = text.toUpperCase();
/* 168 */     int len = text.length();
/* 169 */     if (len < 2) {
/* 170 */       throw new IllegalArgumentException("Codabar must have at least start and stop character.");
/*     */     }
/* 172 */     if ("0123456789-$:/.+ABCD".indexOf(text.charAt(0)) < 16 || "0123456789-$:/.+ABCD".indexOf(text.charAt(len - 1)) < 16) {
/* 173 */       throw new IllegalArgumentException("Codabar must have one of 'ABCD' as start/stop character.");
/*     */     }
/* 175 */     byte[] bars = new byte[text.length() * 8 - 1];
/* 176 */     for (int k = 0; k < len; k++) {
/* 177 */       int idx = "0123456789-$:/.+ABCD".indexOf(text.charAt(k));
/* 178 */       if (idx >= 16 && k > 0 && k < len - 1) {
/* 179 */         throw new IllegalArgumentException("In Codabar, start/stop characters are only allowed at the extremes.");
/*     */       }
/* 181 */       if (idx < 0) {
/* 182 */         throw new IllegalArgumentException("Illegal character in Codabar Barcode.");
/*     */       }
/* 184 */       System.arraycopy(BARS[idx], 0, bars, k * 8, 7);
/*     */     } 
/* 186 */     return bars;
/*     */   }
/*     */   
/*     */   public static String calculateChecksum(String code) {
/* 190 */     if (code.length() < 2)
/* 191 */       return code; 
/* 192 */     String text = code.toUpperCase();
/* 193 */     int sum = 0;
/* 194 */     int len = text.length();
/* 195 */     for (int k = 0; k < len; k++) {
/* 196 */       sum += "0123456789-$:/.+ABCD".indexOf(text.charAt(k));
/*     */     }
/* 198 */     sum = (sum + 15) / 16 * 16 - sum;
/* 199 */     return code.substring(0, len - 1) + "0123456789-$:/.+ABCD".charAt(sum) + code.substring(len - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBarcodeSize() {
/* 209 */     float fontX = 0.0F;
/* 210 */     float fontY = 0.0F;
/* 211 */     String text = this.code;
/* 212 */     if (this.generateChecksum && this.checksumText) {
/* 213 */       text = calculateChecksum(this.code);
/*     */     }
/* 215 */     if (!this.startStopText) {
/* 216 */       text = text.substring(1, text.length() - 1);
/*     */     }
/* 218 */     if (this.font != null) {
/* 219 */       if (this.baseline > 0.0F) {
/* 220 */         fontY = this.baseline - getDescender();
/*     */       } else {
/* 222 */         fontY = -this.baseline + this.size;
/*     */       } 
/* 224 */       fontX = this.font.getWidth((this.altText != null) ? this.altText : text, this.size);
/*     */     } 
/* 226 */     text = this.code;
/* 227 */     if (this.generateChecksum) {
/* 228 */       text = calculateChecksum(this.code);
/*     */     }
/* 230 */     byte[] bars = getBarsCodabar(text);
/* 231 */     int wide = 0;
/* 232 */     for (int k = 0; k < bars.length; k++) {
/* 233 */       wide += bars[k];
/*     */     }
/* 235 */     int narrow = bars.length - wide;
/* 236 */     float fullWidth = this.x * (narrow + wide * this.n);
/* 237 */     fullWidth = Math.max(fullWidth, fontX);
/* 238 */     float fullHeight = this.barHeight + fontY;
/* 239 */     return new Rectangle(fullWidth, fullHeight);
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
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
/* 282 */     String fullCode = this.code;
/* 283 */     if (this.generateChecksum && this.checksumText) {
/* 284 */       fullCode = calculateChecksum(this.code);
/*     */     }
/* 286 */     if (!this.startStopText) {
/* 287 */       fullCode = fullCode.substring(1, fullCode.length() - 1);
/*     */     }
/* 289 */     float fontX = 0.0F;
/* 290 */     if (this.font != null) {
/* 291 */       fontX = this.font.getWidth(fullCode = (this.altText != null) ? this.altText : fullCode, this.size);
/*     */     }
/* 293 */     byte[] bars = getBarsCodabar(this.generateChecksum ? calculateChecksum(this.code) : this.code);
/* 294 */     int wide = 0;
/* 295 */     for (int k = 0; k < bars.length; k++) {
/* 296 */       wide += bars[k];
/*     */     }
/* 298 */     int narrow = bars.length - wide;
/* 299 */     float fullWidth = this.x * (narrow + wide * this.n);
/* 300 */     float barStartX = 0.0F;
/* 301 */     float textStartX = 0.0F;
/* 302 */     switch (this.textAlignment) {
/*     */       case 1:
/*     */         break;
/*     */       case 2:
/* 306 */         if (fontX > fullWidth) {
/* 307 */           barStartX = fontX - fullWidth; break;
/*     */         } 
/* 309 */         textStartX = fullWidth - fontX;
/*     */         break;
/*     */       
/*     */       default:
/* 313 */         if (fontX > fullWidth) {
/* 314 */           barStartX = (fontX - fullWidth) / 2.0F; break;
/*     */         } 
/* 316 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */         break;
/*     */     } 
/*     */     
/* 320 */     float barStartY = 0.0F;
/* 321 */     float textStartY = 0.0F;
/* 322 */     if (this.font != null) {
/* 323 */       if (this.baseline <= 0.0F) {
/* 324 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 326 */         textStartY = -getDescender();
/* 327 */         barStartY = textStartY + this.baseline;
/*     */       } 
/*     */     }
/* 330 */     boolean print = true;
/* 331 */     if (barColor != null) {
/* 332 */       canvas.setFillColor(barColor);
/*     */     }
/* 334 */     for (int i = 0; i < bars.length; i++) {
/* 335 */       float w = (bars[i] == 0) ? this.x : (this.x * this.n);
/* 336 */       if (print) {
/* 337 */         canvas.rectangle(barStartX, barStartY, (w - this.inkSpreading), this.barHeight);
/*     */       }
/* 339 */       print = !print;
/* 340 */       barStartX += w;
/*     */     } 
/* 342 */     canvas.fill();
/* 343 */     if (this.font != null) {
/* 344 */       if (textColor != null) {
/* 345 */         canvas.setFillColor(textColor);
/*     */       }
/* 347 */       canvas.beginText();
/* 348 */       canvas.setFontAndSize(this.font, this.size);
/* 349 */       canvas.setTextMatrix(textStartX, textStartY);
/* 350 */       canvas.showText(fullCode);
/* 351 */       canvas.endText();
/*     */     } 
/* 353 */     return getBarcodeSize();
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
/*     */   public Image createAwtImage(Color foreground, Color background) {
/* 367 */     int f = (foreground == null) ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
/* 368 */     int g = (background == null) ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
/* 369 */     Canvas canvas = new Canvas();
/*     */     
/* 371 */     byte[] bars = getBarsCodabar(this.generateChecksum ? calculateChecksum(this.code) : this.code);
/* 372 */     int wide = 0;
/* 373 */     for (int k = 0; k < bars.length; k++) {
/* 374 */       wide += bars[k];
/*     */     }
/* 376 */     int narrow = bars.length - wide;
/* 377 */     int fullWidth = narrow + wide * (int)this.n;
/* 378 */     boolean print = true;
/* 379 */     int ptr = 0;
/* 380 */     int height = (int)this.barHeight;
/* 381 */     int[] pix = new int[fullWidth * height]; int i;
/* 382 */     for (i = 0; i < bars.length; i++) {
/* 383 */       int w = (bars[i] == 0) ? 1 : (int)this.n;
/* 384 */       int c = g;
/* 385 */       if (print) {
/* 386 */         c = f;
/*     */       }
/* 388 */       print = !print;
/* 389 */       for (int j = 0; j < w; j++) {
/* 390 */         pix[ptr++] = c;
/*     */       }
/*     */     } 
/* 393 */     for (i = fullWidth; i < pix.length; i += fullWidth) {
/* 394 */       System.arraycopy(pix, 0, pix, i, fullWidth);
/*     */     }
/* 396 */     return canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/BarcodeCodabar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */