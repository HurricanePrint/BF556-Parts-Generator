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
/*     */ public class Barcode39
/*     */   extends Barcode1D
/*     */ {
/*  59 */   private static final byte[][] BARS = new byte[][] { { 0, 0, 0, 1, 1, 0, 1, 0, 0 }, { 1, 0, 0, 1, 0, 0, 0, 0, 1 }, { 0, 0, 1, 1, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 1, 0, 0, 0, 1 }, { 1, 0, 0, 1, 1, 0, 0, 0, 0 }, { 0, 0, 1, 1, 1, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 1, 0, 1 }, { 1, 0, 0, 1, 0, 0, 1, 0, 0 }, { 0, 0, 1, 1, 0, 0, 1, 0, 0 }, { 1, 0, 0, 0, 0, 1, 0, 0, 1 }, { 0, 0, 1, 0, 0, 1, 0, 0, 1 }, { 1, 0, 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 1 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0 }, { 0, 0, 1, 0, 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 1 }, { 1, 0, 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 1, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 1, 1 }, { 0, 0, 1, 0, 0, 0, 0, 1, 1 }, { 1, 0, 1, 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 1, 0, 0, 1, 1 }, { 1, 0, 0, 0, 1, 0, 0, 1, 0 }, { 0, 0, 1, 0, 1, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 1, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1, 1, 0 }, { 0, 0, 1, 0, 0, 0, 1, 1, 0 }, { 0, 0, 0, 0, 1, 0, 1, 1, 0 }, { 1, 1, 0, 0, 0, 0, 0, 0, 1 }, { 0, 1, 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 0, 0, 0, 1 }, { 1, 1, 0, 0, 1, 0, 0, 0, 0 }, { 0, 1, 1, 0, 1, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 0, 1, 0, 1 }, { 1, 1, 0, 0, 0, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0, 0, 1, 0, 0 }, { 0, 1, 0, 1, 0, 1, 0, 0, 0 }, { 0, 1, 0, 1, 0, 0, 0, 1, 0 }, { 0, 1, 0, 0, 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 0, 1, 0, 1, 0, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String EXTENDED = "%U$A$B$C$D$E$F$G$H$I$J$K$L$M$N$O$P$Q$R$S$T$U$V$W$X$Y$Z%A%B%C%D%E  /A/B/C/D/E/F/G/H/I/J/K/L - ./O 0 1 2 3 4 5 6 7 8 9/Z%F%G%H%I%J%V A B C D E F G H I J K L M N O P Q R S T U V W X Y Z%K%L%M%N%O%W+A+B+C+D+E+F+G+H+I+J+K+L+M+N+O+P+Q+R+S+T+U+V+W+X+Y+Z%P%Q%R%S%T";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Barcode39(PdfDocument document) {
/* 135 */     this(document, document.getDefaultFont());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Barcode39(PdfDocument document, PdfFont font) {
/* 145 */     super(document);
/* 146 */     this.x = 0.8F;
/* 147 */     this.n = 2.0F;
/* 148 */     this.font = font;
/* 149 */     this.size = 8.0F;
/* 150 */     this.baseline = this.size;
/* 151 */     this.barHeight = this.size * 3.0F;
/* 152 */     this.generateChecksum = false;
/* 153 */     this.checksumText = false;
/* 154 */     this.startStopText = true;
/* 155 */     this.extended = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsCode39(String text) {
/* 165 */     text = "*" + text + "*";
/* 166 */     byte[] bars = new byte[text.length() * 10 - 1];
/* 167 */     for (int k = 0; k < text.length(); k++) {
/* 168 */       char ch = text.charAt(k);
/* 169 */       int idx = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*".indexOf(ch);
/* 170 */       if (ch == '*' && k != 0 && k != text.length() - 1) {
/* 171 */         throw new IllegalArgumentException("The character " + ch + " is illegal in code 39");
/*     */       }
/* 173 */       if (idx < 0) {
/* 174 */         throw new IllegalArgumentException("The character " + text.charAt(k) + " is illegal in code 39");
/*     */       }
/* 176 */       System.arraycopy(BARS[idx], 0, bars, k * 10, 9);
/*     */     } 
/* 178 */     return bars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCode39Ex(String text) {
/* 189 */     StringBuilder out = new StringBuilder("");
/* 190 */     for (int k = 0; k < text.length(); k++) {
/* 191 */       char c = text.charAt(k);
/* 192 */       if (c > '') {
/* 193 */         throw new IllegalArgumentException("The character " + c + " is illegal in code 39");
/*     */       }
/* 195 */       char c1 = "%U$A$B$C$D$E$F$G$H$I$J$K$L$M$N$O$P$Q$R$S$T$U$V$W$X$Y$Z%A%B%C%D%E  /A/B/C/D/E/F/G/H/I/J/K/L - ./O 0 1 2 3 4 5 6 7 8 9/Z%F%G%H%I%J%V A B C D E F G H I J K L M N O P Q R S T U V W X Y Z%K%L%M%N%O%W+A+B+C+D+E+F+G+H+I+J+K+L+M+N+O+P+Q+R+S+T+U+V+W+X+Y+Z%P%Q%R%S%T".charAt(c * 2);
/* 196 */       char c2 = "%U$A$B$C$D$E$F$G$H$I$J$K$L$M$N$O$P$Q$R$S$T$U$V$W$X$Y$Z%A%B%C%D%E  /A/B/C/D/E/F/G/H/I/J/K/L - ./O 0 1 2 3 4 5 6 7 8 9/Z%F%G%H%I%J%V A B C D E F G H I J K L M N O P Q R S T U V W X Y Z%K%L%M%N%O%W+A+B+C+D+E+F+G+H+I+J+K+L+M+N+O+P+Q+R+S+T+U+V+W+X+Y+Z%P%Q%R%S%T".charAt(c * 2 + 1);
/* 197 */       if (c1 != ' ') {
/* 198 */         out.append(c1);
/*     */       }
/* 200 */       out.append(c2);
/*     */     } 
/* 202 */     return out.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static char getChecksum(String text) {
/* 212 */     int chk = 0;
/* 213 */     for (int k = 0; k < text.length(); k++) {
/* 214 */       int idx = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*".indexOf(text.charAt(k));
/* 215 */       char ch = text.charAt(k);
/* 216 */       if (ch == '*' && k != 0 && k != text.length() - 1) {
/* 217 */         throw new IllegalArgumentException("The character " + ch + " is illegal in code 39");
/*     */       }
/* 219 */       if (idx < 0) {
/* 220 */         throw new IllegalArgumentException("The character " + text.charAt(k) + " is illegal in code 39");
/*     */       }
/* 222 */       chk += idx;
/*     */     } 
/* 224 */     return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*".charAt(chk % 43);
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
/* 235 */     float fontX = 0.0F;
/* 236 */     float fontY = 0.0F;
/* 237 */     String fCode = this.code;
/* 238 */     if (this.extended) {
/* 239 */       fCode = getCode39Ex(this.code);
/*     */     }
/* 241 */     if (this.font != null) {
/* 242 */       if (this.baseline > 0.0F) {
/* 243 */         fontY = this.baseline - getDescender();
/*     */       } else {
/* 245 */         fontY = -this.baseline + this.size;
/*     */       } 
/* 247 */       String fullCode = this.code;
/* 248 */       if (this.generateChecksum && this.checksumText) {
/* 249 */         fullCode = fullCode + getChecksum(fCode);
/*     */       }
/* 251 */       if (this.startStopText) {
/* 252 */         fullCode = "*" + fullCode + "*";
/*     */       }
/* 254 */       fontX = this.font.getWidth((this.altText != null) ? this.altText : fullCode, this.size);
/*     */     } 
/* 256 */     int len = fCode.length() + 2;
/* 257 */     if (this.generateChecksum) {
/* 258 */       len++;
/*     */     }
/* 260 */     float fullWidth = len * (6.0F * this.x + 3.0F * this.x * this.n) + (len - 1) * this.x;
/* 261 */     fullWidth = Math.max(fullWidth, fontX);
/* 262 */     float fullHeight = this.barHeight + fontY;
/* 263 */     return new Rectangle(fullWidth, fullHeight);
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
/* 307 */     String fullCode = this.code;
/* 308 */     float fontX = 0.0F;
/* 309 */     String bCode = this.code;
/* 310 */     if (this.extended) {
/* 311 */       bCode = getCode39Ex(this.code);
/*     */     }
/* 313 */     if (this.font != null) {
/* 314 */       if (this.generateChecksum && this.checksumText) {
/* 315 */         fullCode = fullCode + getChecksum(bCode);
/*     */       }
/* 317 */       if (this.startStopText) {
/* 318 */         fullCode = "*" + fullCode + "*";
/*     */       }
/* 320 */       fontX = this.font.getWidth(fullCode = (this.altText != null) ? this.altText : fullCode, this.size);
/*     */     } 
/* 322 */     if (this.generateChecksum) {
/* 323 */       bCode = bCode + getChecksum(bCode);
/*     */     }
/* 325 */     int len = bCode.length() + 2;
/* 326 */     float fullWidth = len * (6.0F * this.x + 3.0F * this.x * this.n) + (len - 1) * this.x;
/* 327 */     float barStartX = 0.0F;
/* 328 */     float textStartX = 0.0F;
/* 329 */     switch (this.textAlignment) {
/*     */       case 1:
/*     */         break;
/*     */       case 2:
/* 333 */         if (fontX > fullWidth) {
/* 334 */           barStartX = fontX - fullWidth; break;
/*     */         } 
/* 336 */         textStartX = fullWidth - fontX;
/*     */         break;
/*     */       
/*     */       default:
/* 340 */         if (fontX > fullWidth) {
/* 341 */           barStartX = (fontX - fullWidth) / 2.0F; break;
/*     */         } 
/* 343 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */         break;
/*     */     } 
/*     */     
/* 347 */     float barStartY = 0.0F;
/* 348 */     float textStartY = 0.0F;
/* 349 */     if (this.font != null) {
/* 350 */       if (this.baseline <= 0.0F) {
/* 351 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 353 */         textStartY = -getDescender();
/* 354 */         barStartY = textStartY + this.baseline;
/*     */       } 
/*     */     }
/* 357 */     byte[] bars = getBarsCode39(bCode);
/* 358 */     boolean print = true;
/* 359 */     if (barColor != null) {
/* 360 */       canvas.setFillColor(barColor);
/*     */     }
/* 362 */     for (int k = 0; k < bars.length; k++) {
/* 363 */       float w = (bars[k] == 0) ? this.x : (this.x * this.n);
/* 364 */       if (print) {
/* 365 */         canvas.rectangle(barStartX, barStartY, (w - this.inkSpreading), this.barHeight);
/*     */       }
/* 367 */       print = !print;
/* 368 */       barStartX += w;
/*     */     } 
/* 370 */     canvas.fill();
/* 371 */     if (this.font != null) {
/* 372 */       if (textColor != null) {
/* 373 */         canvas.setFillColor(textColor);
/*     */       }
/* 375 */       canvas
/* 376 */         .beginText()
/* 377 */         .setFontAndSize(this.font, this.size)
/* 378 */         .setTextMatrix(textStartX, textStartY)
/* 379 */         .showText(fullCode)
/* 380 */         .endText();
/*     */     } 
/* 382 */     return getBarcodeSize();
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
/* 395 */     int f = (foreground == null) ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
/* 396 */     int g = (background == null) ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
/* 397 */     Canvas canvas = new Canvas();
/* 398 */     String bCode = this.code;
/* 399 */     if (this.extended) {
/* 400 */       bCode = getCode39Ex(this.code);
/*     */     }
/* 402 */     if (this.generateChecksum) {
/* 403 */       bCode = bCode + getChecksum(bCode);
/*     */     }
/* 405 */     int len = bCode.length() + 2;
/* 406 */     int nn = (int)this.n;
/* 407 */     int fullWidth = len * (6 + 3 * nn) + len - 1;
/* 408 */     byte[] bars = getBarsCode39(bCode);
/* 409 */     boolean print = true;
/* 410 */     int ptr = 0;
/* 411 */     int height = (int)this.barHeight;
/* 412 */     int[] pix = new int[fullWidth * height]; int k;
/* 413 */     for (k = 0; k < bars.length; k++) {
/* 414 */       int w = (bars[k] == 0) ? 1 : nn;
/* 415 */       int c = g;
/* 416 */       if (print) {
/* 417 */         c = f;
/*     */       }
/* 419 */       print = !print;
/* 420 */       for (int j = 0; j < w; j++) {
/* 421 */         pix[ptr++] = c;
/*     */       }
/*     */     } 
/* 424 */     for (k = fullWidth; k < pix.length; k += fullWidth) {
/* 425 */       System.arraycopy(pix, 0, pix, k, fullWidth);
/*     */     }
/* 427 */     return canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/Barcode39.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */