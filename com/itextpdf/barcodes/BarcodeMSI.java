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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BarcodeMSI
/*     */   extends Barcode1D
/*     */ {
/*     */   private static final String CHARS = "0123456789";
/*  67 */   private static final byte[] BARS_START = new byte[] { 1, 1, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final byte[] BARS_END = new byte[] { 1, 0, 0, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final byte[][] BARS = new byte[][] { { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0 }, { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0 }, { 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0 }, { 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0 }, { 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0 }, { 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0 }, { 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0 }, { 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0 }, { 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0 }, { 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BARS_PER_CHARACTER = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BARS_FOR_START = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BARS_FOR_STOP = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeMSI(PdfDocument document) {
/* 133 */     this(document, document.getDefaultFont());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeMSI(PdfDocument document, PdfFont font) {
/* 143 */     super(document);
/* 144 */     this.x = 0.8F;
/* 145 */     this.n = 2.0F;
/* 146 */     this.font = font;
/* 147 */     this.size = 8.0F;
/* 148 */     this.baseline = this.size;
/* 149 */     this.barHeight = this.size * 3.0F;
/* 150 */     this.generateChecksum = false;
/* 151 */     this.checksumText = false;
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
/* 162 */     float fontX = 0.0F;
/* 163 */     float fontY = 0.0F;
/* 164 */     String fCode = this.code;
/* 165 */     if (this.font != null) {
/* 166 */       if (this.baseline > 0.0F) {
/* 167 */         fontY = this.baseline - getDescender();
/*     */       } else {
/* 169 */         fontY = -this.baseline + this.size;
/*     */       } 
/* 171 */       String fullCode = this.code;
/* 172 */       fontX = this.font.getWidth((this.altText != null) ? this.altText : fullCode, this.size);
/*     */     } 
/*     */     
/* 175 */     int len = fCode.length();
/* 176 */     if (this.generateChecksum) {
/* 177 */       len++;
/*     */     }
/*     */     
/* 180 */     float fullWidth = (len * 12 + 3 + 4) * this.x;
/* 181 */     fullWidth = Math.max(fullWidth, fontX);
/* 182 */     float fullHeight = this.barHeight + fontY;
/* 183 */     return new Rectangle(fullWidth, fullHeight);
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
/*     */   
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
/* 228 */     String fullCode = this.code;
/* 229 */     if (this.checksumText) {
/* 230 */       fullCode = fullCode + Integer.toString(getChecksum(this.code));
/*     */     }
/* 232 */     float fontX = 0.0F;
/* 233 */     if (this.font != null) {
/* 234 */       String var10001 = (this.altText != null) ? this.altText : fullCode;
/* 235 */       fullCode = (this.altText != null) ? this.altText : fullCode;
/* 236 */       fontX = this.font.getWidth(var10001, this.size);
/*     */     } 
/*     */     
/* 239 */     String bCode = this.code;
/* 240 */     if (this.generateChecksum) {
/* 241 */       bCode = bCode + getChecksum(bCode);
/*     */     }
/*     */     
/* 244 */     int idx = bCode.length();
/* 245 */     float fullWidth = (idx * 12 + 3 + 4) * this.x;
/* 246 */     float barStartX = 0.0F;
/* 247 */     float textStartX = 0.0F;
/* 248 */     switch (this.textAlignment) {
/*     */       case 1:
/*     */         break;
/*     */       case 2:
/* 252 */         if (fontX > fullWidth) {
/* 253 */           barStartX = fontX - fullWidth; break;
/*     */         } 
/* 255 */         textStartX = fullWidth - fontX;
/*     */         break;
/*     */       
/*     */       default:
/* 259 */         if (fontX > fullWidth) {
/* 260 */           barStartX = (fontX - fullWidth) / 2.0F; break;
/*     */         } 
/* 262 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */         break;
/*     */     } 
/*     */     
/* 266 */     float barStartY = 0.0F;
/* 267 */     float textStartY = 0.0F;
/* 268 */     if (this.font != null) {
/* 269 */       if (this.baseline <= 0.0F) {
/* 270 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 272 */         textStartY = -getDescender();
/* 273 */         barStartY = textStartY + this.baseline;
/*     */       } 
/*     */     }
/* 276 */     byte[] bars = getBarsMSI(bCode);
/* 277 */     if (barColor != null) {
/* 278 */       canvas.setFillColor(barColor);
/*     */     }
/* 280 */     for (int k = 0; k < bars.length; k++) {
/* 281 */       float w = bars[k] * this.x;
/* 282 */       if (bars[k] == 1)
/* 283 */         canvas.rectangle(barStartX, barStartY, (w - this.inkSpreading), this.barHeight); 
/* 284 */       barStartX += this.x;
/*     */     } 
/* 286 */     canvas.fill();
/* 287 */     if (this.font != null) {
/* 288 */       if (textColor != null) {
/* 289 */         canvas.setFillColor(textColor);
/*     */       }
/* 291 */       canvas.beginText();
/* 292 */       canvas.setFontAndSize(this.font, this.size);
/* 293 */       canvas.setTextMatrix(textStartX, textStartY);
/* 294 */       canvas.showText(fullCode);
/* 295 */       canvas.endText();
/*     */     } 
/* 297 */     return getBarcodeSize();
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
/*     */   public Image createAwtImage(Color foreground, Color background) {
/* 310 */     int foregroundColor = (foreground == null) ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
/* 311 */     int backgroundColor = (background == null) ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
/* 312 */     Canvas canvas = new Canvas();
/* 313 */     String bCode = this.code;
/* 314 */     if (this.generateChecksum) {
/* 315 */       bCode = bCode + Integer.toString(getChecksum(this.code));
/*     */     }
/*     */     
/* 318 */     byte[] bars = getBarsMSI(bCode);
/* 319 */     int fullWidth = bars.length;
/* 320 */     int fullHeight = (int)this.barHeight;
/* 321 */     int[] pix = new int[fullWidth * fullHeight];
/*     */     
/* 323 */     for (int x = 0; x < bars.length; x++) {
/* 324 */       int color = (bars[x] == 1) ? foregroundColor : backgroundColor;
/* 325 */       for (int y = 0; y < fullHeight; y++) {
/* 326 */         int currentPixel = x + y * fullWidth;
/* 327 */         pix[currentPixel] = color;
/*     */       } 
/*     */     } 
/* 330 */     return canvas.createImage(new MemoryImageSource(fullWidth, fullHeight, pix, 0, fullWidth));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsMSI(String text) {
/* 340 */     if (text == null) {
/* 341 */       throw new IllegalArgumentException("Valid code required to generate MSI barcode.");
/*     */     }
/* 343 */     byte[] bars = new byte[text.length() * 12 + 7];
/* 344 */     System.arraycopy(BARS_START, 0, bars, 0, 3);
/* 345 */     for (int x = 0; x < text.length(); x++) {
/* 346 */       char ch = text.charAt(x);
/* 347 */       int idx = "0123456789".indexOf(ch);
/* 348 */       if (idx < 0) {
/* 349 */         throw new IllegalArgumentException("The character " + text.charAt(x) + " is illegal in MSI bar codes.");
/*     */       }
/* 351 */       System.arraycopy(BARS[idx], 0, bars, 3 + x * 12, 12);
/*     */     } 
/* 353 */     System.arraycopy(BARS_END, 0, bars, bars.length - 4, 4);
/* 354 */     return bars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getChecksum(String text) {
/* 364 */     if (text == null) {
/* 365 */       throw new IllegalArgumentException("Valid code required to generate checksum for MSI barcode");
/*     */     }
/* 367 */     int[] digits = new int[text.length()];
/* 368 */     for (int x = 0; x < text.length(); x++) {
/* 369 */       digits[x] = text.charAt(x) - 48;
/* 370 */       if (digits[x] < 0 || digits[x] > 9) {
/* 371 */         throw new IllegalArgumentException("The character " + text.charAt(x) + " is illegal in MSI bar codes.");
/*     */       }
/*     */     } 
/* 374 */     int sum = 0;
/* 375 */     int length = digits.length;
/* 376 */     for (int i = 0; i < length; i++) {
/* 377 */       int digit = digits[length - i - 1];
/* 378 */       if (i % 2 == 0) {
/* 379 */         digit *= 2;
/*     */       }
/* 381 */       sum += (digit > 9) ? (digit - 9) : digit;
/*     */     } 
/* 383 */     return sum * 9 % 10;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/BarcodeMSI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */