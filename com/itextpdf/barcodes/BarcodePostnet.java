/*     */ package com.itextpdf.barcodes;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
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
/*     */ public class BarcodePostnet
/*     */   extends Barcode1D
/*     */ {
/*  56 */   public static int TYPE_POSTNET = 1;
/*  57 */   public static int TYPE_PLANET = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final byte[][] BARS = new byte[][] { { 1, 1, 0, 0, 0 }, { 0, 0, 0, 1, 1 }, { 0, 0, 1, 0, 1 }, { 0, 0, 1, 1, 0 }, { 0, 1, 0, 0, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 1, 0, 0 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 1, 0 }, { 1, 0, 1, 0, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodePostnet(PdfDocument document) {
/*  77 */     super(document);
/*     */     
/*  79 */     this.n = 3.2727273F;
/*     */     
/*  81 */     this.x = 1.4399999F;
/*     */     
/*  83 */     this.barHeight = 9.0F;
/*     */     
/*  85 */     this.size = 3.6000001F;
/*     */     
/*  87 */     this.codeType = TYPE_POSTNET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsPostnet(String text) {
/*  95 */     int total = 0;
/*  96 */     for (int k = text.length() - 1; k >= 0; k--) {
/*  97 */       int n = text.charAt(k) - 48;
/*  98 */       total += n;
/*     */     } 
/* 100 */     text = text + (char)((10 - total % 10) % 10 + 48);
/* 101 */     byte[] bars = new byte[text.length() * 5 + 2];
/* 102 */     bars[0] = 1;
/* 103 */     bars[bars.length - 1] = 1;
/* 104 */     for (int i = 0; i < text.length(); i++) {
/* 105 */       int c = text.charAt(i) - 48;
/* 106 */       System.arraycopy(BARS[c], 0, bars, i * 5 + 1, 5);
/*     */     } 
/* 108 */     return bars;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle getBarcodeSize() {
/* 113 */     float width = ((this.code.length() + 1) * 5 + 1) * this.n + this.x;
/* 114 */     return new Rectangle(width, this.barHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fitWidth(float width) {
/* 119 */     byte[] bars = getBarsPostnet(this.code);
/* 120 */     float currentWidth = getBarcodeSize().getWidth();
/* 121 */     this.x *= width / currentWidth;
/* 122 */     this.n = (width - this.x) / (bars.length - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
/* 127 */     if (barColor != null)
/* 128 */       canvas.setFillColor(barColor); 
/* 129 */     byte[] bars = getBarsPostnet(this.code);
/* 130 */     byte flip = 1;
/* 131 */     if (this.codeType == TYPE_PLANET) {
/* 132 */       flip = 0;
/* 133 */       bars[0] = 0;
/* 134 */       bars[bars.length - 1] = 0;
/*     */     } 
/* 136 */     float startX = 0.0F;
/* 137 */     for (int k = 0; k < bars.length; k++) {
/* 138 */       canvas.rectangle(startX, 0.0D, (this.x - this.inkSpreading), (bars[k] == flip) ? this.barHeight : this.size);
/* 139 */       startX += this.n;
/*     */     } 
/* 141 */     canvas.fill();
/* 142 */     return getBarcodeSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Image createAwtImage(Color foreground, Color background) {
/* 147 */     int f = (foreground == null) ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
/* 148 */     int g = (background == null) ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
/* 149 */     Canvas canvas = new Canvas();
/* 150 */     int barWidth = (int)this.x;
/* 151 */     if (barWidth <= 0)
/* 152 */       barWidth = 1; 
/* 153 */     int barDistance = (int)this.n;
/* 154 */     if (barDistance <= barWidth)
/* 155 */       barDistance = barWidth + 1; 
/* 156 */     int barShort = (int)this.size;
/* 157 */     if (barShort <= 0)
/* 158 */       barShort = 1; 
/* 159 */     int barTall = (int)this.barHeight;
/* 160 */     if (barTall <= barShort)
/* 161 */       barTall = barShort + 1; 
/* 162 */     int width = ((this.code.length() + 1) * 5 + 1) * barDistance + barWidth;
/* 163 */     int[] pix = new int[width * barTall];
/* 164 */     byte[] bars = getBarsPostnet(this.code);
/* 165 */     byte flip = 1;
/* 166 */     if (this.codeType == TYPE_PLANET) {
/* 167 */       flip = 0;
/* 168 */       bars[0] = 0;
/* 169 */       bars[bars.length - 1] = 0;
/*     */     } 
/* 171 */     int idx = 0;
/* 172 */     for (int k = 0; k < bars.length; k++) {
/* 173 */       boolean dot = (bars[k] == flip);
/* 174 */       for (int j = 0; j < barDistance; j++) {
/* 175 */         pix[idx + j] = (dot && j < barWidth) ? f : g;
/*     */       }
/* 177 */       idx += barDistance;
/*     */     } 
/* 179 */     int limit = width * (barTall - barShort); int i;
/* 180 */     for (i = width; i < limit; i += width)
/* 181 */       System.arraycopy(pix, 0, pix, i, width); 
/* 182 */     idx = limit;
/* 183 */     for (i = 0; i < bars.length; i++) {
/* 184 */       for (int j = 0; j < barDistance; j++) {
/* 185 */         pix[idx + j] = (j < barWidth) ? f : g;
/*     */       }
/* 187 */       idx += barDistance;
/*     */     } 
/* 189 */     for (i = limit + width; i < pix.length; i += width)
/* 190 */       System.arraycopy(pix, limit, pix, i, width); 
/* 191 */     Image img = canvas.createImage(new MemoryImageSource(width, barTall, pix, 0, width));
/*     */     
/* 193 */     return img;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/BarcodePostnet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */