/*      */ package com.itextpdf.barcodes;
/*      */ 
/*      */ import com.itextpdf.barcodes.dmcode.DmParams;
/*      */ import com.itextpdf.barcodes.dmcode.Placement;
/*      */ import com.itextpdf.barcodes.dmcode.ReedSolomon;
/*      */ import com.itextpdf.kernel.colors.Color;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*      */ import java.awt.Canvas;
/*      */ import java.awt.Color;
/*      */ import java.awt.Image;
/*      */ import java.awt.image.MemoryImageSource;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BarcodeDataMatrix
/*      */   extends Barcode2D
/*      */ {
/*      */   public static final int DM_NO_ERROR = 0;
/*      */   public static final int DM_ERROR_TEXT_TOO_BIG = 1;
/*      */   public static final int DM_ERROR_INVALID_SQUARE = 3;
/*      */   public static final int DM_ERROR_EXTENSION = 5;
/*      */   public static final int DM_AUTO = 0;
/*      */   public static final int DM_ASCII = 1;
/*      */   public static final int DM_C40 = 2;
/*      */   public static final int DM_TEXT = 3;
/*      */   public static final int DM_B256 = 4;
/*      */   public static final int DM_X12 = 5;
/*      */   public static final int DM_EDIFACT = 6;
/*      */   public static final int DM_RAW = 7;
/*      */   public static final int DM_EXTENSION = 32;
/*      */   public static final int DM_TEST = 64;
/*      */   public static final String DEFAULT_DATA_MATRIX_ENCODING = "iso-8859-1";
/*      */   private static final byte LATCH_B256 = -25;
/*      */   private static final byte LATCH_EDIFACT = -16;
/*      */   private static final byte LATCH_X12 = -18;
/*      */   private static final byte LATCH_TEXT = -17;
/*      */   private static final byte LATCH_C40 = -26;
/*      */   private static final byte UNLATCH = -2;
/*      */   private static final byte EXTENDED_ASCII = -21;
/*      */   private static final byte PADDING = -127;
/*      */   private String encoding;
/*  140 */   private static final DmParams[] dmSizes = new DmParams[] { new DmParams(10, 10, 10, 10, 3, 3, 5), new DmParams(12, 12, 12, 12, 5, 5, 7), new DmParams(8, 18, 8, 18, 5, 5, 7), new DmParams(14, 14, 14, 14, 8, 8, 10), new DmParams(8, 32, 8, 16, 10, 10, 11), new DmParams(16, 16, 16, 16, 12, 12, 12), new DmParams(12, 26, 12, 26, 16, 16, 14), new DmParams(18, 18, 18, 18, 18, 18, 14), new DmParams(20, 20, 20, 20, 22, 22, 18), new DmParams(12, 36, 12, 18, 22, 22, 18), new DmParams(22, 22, 22, 22, 30, 30, 20), new DmParams(16, 36, 16, 18, 32, 32, 24), new DmParams(24, 24, 24, 24, 36, 36, 24), new DmParams(26, 26, 26, 26, 44, 44, 28), new DmParams(16, 48, 16, 24, 49, 49, 28), new DmParams(32, 32, 16, 16, 62, 62, 36), new DmParams(36, 36, 18, 18, 86, 86, 42), new DmParams(40, 40, 20, 20, 114, 114, 48), new DmParams(44, 44, 22, 22, 144, 144, 56), new DmParams(48, 48, 24, 24, 174, 174, 68), new DmParams(52, 52, 26, 26, 204, 102, 42), new DmParams(64, 64, 16, 16, 280, 140, 56), new DmParams(72, 72, 18, 18, 368, 92, 36), new DmParams(80, 80, 20, 20, 456, 114, 48), new DmParams(88, 88, 22, 22, 576, 144, 56), new DmParams(96, 96, 24, 24, 696, 174, 68), new DmParams(104, 104, 26, 26, 816, 136, 56), new DmParams(120, 120, 20, 20, 1050, 175, 68), new DmParams(132, 132, 22, 22, 1304, 163, 62), new DmParams(144, 144, 24, 24, 1558, 156, 62) };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String X12 = "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int extOut;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private short[] place;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] image;
/*      */ 
/*      */ 
/*      */   
/*      */   private int height;
/*      */ 
/*      */ 
/*      */   
/*      */   private int width;
/*      */ 
/*      */ 
/*      */   
/*      */   private int ws;
/*      */ 
/*      */ 
/*      */   
/*      */   private int options;
/*      */ 
/*      */ 
/*      */   
/*      */   private int[][] f;
/*      */ 
/*      */ 
/*      */   
/*      */   private int[][] switchMode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BarcodeDataMatrix() {
/*  190 */     this.encoding = "iso-8859-1";
/*      */   }
/*      */   
/*      */   public BarcodeDataMatrix(String code) {
/*  194 */     this.encoding = "iso-8859-1";
/*  195 */     setCode(code);
/*      */   }
/*      */   
/*      */   public BarcodeDataMatrix(String code, String encoding) {
/*  199 */     this.encoding = encoding;
/*  200 */     setCode(code);
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle getBarcodeSize() {
/*  205 */     return new Rectangle(0.0F, 0.0F, (this.width + 2 * this.ws), (this.height + 2 * this.ws));
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle placeBarcode(PdfCanvas canvas, Color foreground) {
/*  210 */     return placeBarcode(canvas, foreground, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public PdfFormXObject createFormXObject(Color foreground, PdfDocument document) {
/*  215 */     return createFormXObject(foreground, 1.0F, document);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormXObject createFormXObject(Color foreground, float moduleSide, PdfDocument document) {
/*  227 */     PdfFormXObject xObject = new PdfFormXObject((Rectangle)null);
/*  228 */     Rectangle rect = placeBarcode(new PdfCanvas(xObject, document), foreground, moduleSide);
/*  229 */     xObject.setBBox(new PdfArray(rect));
/*      */     
/*  231 */     return xObject;
/*      */   }
/*      */   
/*      */   public Rectangle placeBarcode(PdfCanvas canvas, Color foreground, float moduleSide) {
/*  235 */     if (this.image == null) {
/*  236 */       return null;
/*      */     }
/*      */     
/*  239 */     if (foreground != null) {
/*  240 */       canvas.setFillColor(foreground);
/*      */     }
/*      */     
/*  243 */     int w = this.width + 2 * this.ws;
/*  244 */     int h = this.height + 2 * this.ws;
/*  245 */     int stride = (w + 7) / 8;
/*      */     
/*  247 */     for (int k = 0; k < h; k++) {
/*  248 */       int p = k * stride;
/*  249 */       for (int j = 0; j < w; j++) {
/*  250 */         int b = this.image[p + j / 8] & 0xFF;
/*  251 */         b <<= j % 8;
/*  252 */         if ((b & 0x80) != 0) {
/*  253 */           canvas.rectangle((j * moduleSide), ((h - k - 1) * moduleSide), moduleSide, moduleSide);
/*      */         }
/*      */       } 
/*      */     } 
/*  257 */     canvas.fill();
/*      */     
/*  259 */     return getBarcodeSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image createAwtImage(Color foreground, Color background) {
/*  273 */     if (this.image == null)
/*  274 */       return null; 
/*  275 */     int f = foreground.getRGB();
/*  276 */     int g = background.getRGB();
/*  277 */     Canvas canvas = new Canvas();
/*      */     
/*  279 */     int w = this.width + 2 * this.ws;
/*  280 */     int h = this.height + 2 * this.ws;
/*  281 */     int[] pix = new int[w * h];
/*  282 */     int stride = (w + 7) / 8;
/*  283 */     int ptr = 0;
/*  284 */     for (int k = 0; k < h; k++) {
/*  285 */       int p = k * stride;
/*  286 */       for (int j = 0; j < w; j++) {
/*  287 */         int b = this.image[p + j / 8] & 0xFF;
/*  288 */         b <<= j % 8;
/*  289 */         pix[ptr++] = ((b & 0x80) == 0) ? g : f;
/*      */       } 
/*      */     } 
/*  292 */     Image img = canvas.createImage(new MemoryImageSource(w, h, pix, 0, w));
/*  293 */     return img;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getBarcodeSize(float moduleHeight, float moduleWidth) {
/*  304 */     return new Rectangle(0.0F, 0.0F, (this.width + 2 * this.ws) * moduleHeight, (this.height + 2 * this.ws) * moduleWidth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int setCode(String text) {
/*      */     byte[] t;
/*      */     try {
/*  321 */       t = text.getBytes(this.encoding);
/*  322 */     } catch (UnsupportedEncodingException exc) {
/*  323 */       throw new IllegalArgumentException("text has to be encoded in iso-8859-1");
/*      */     } 
/*  325 */     return setCode(t, 0, t.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int setCode(byte[] text, int textOffset, int textSize) {
/*      */     DmParams dm;
/*  342 */     if (textOffset < 0) {
/*  343 */       throw new IndexOutOfBoundsException("" + textOffset);
/*      */     }
/*  345 */     if (textOffset + textSize > text.length || textSize < 0) {
/*  346 */       throw new IndexOutOfBoundsException("" + textSize);
/*      */     }
/*      */ 
/*      */     
/*  350 */     byte[] data = new byte[2500];
/*  351 */     this.extOut = 0;
/*  352 */     int extCount = processExtensions(text, textOffset, textSize, data);
/*  353 */     if (extCount < 0) {
/*  354 */       return 5;
/*      */     }
/*  356 */     int e = -1;
/*  357 */     this.f = new int[6][textSize - this.extOut];
/*  358 */     this.switchMode = new int[6][textSize - this.extOut];
/*  359 */     if (this.height == 0 || this.width == 0) {
/*  360 */       DmParams last = dmSizes[dmSizes.length - 1];
/*  361 */       e = getEncodation(text, textOffset + this.extOut, textSize - this.extOut, data, extCount, last.dataSize - extCount, this.options, false);
/*  362 */       if (e < 0) {
/*  363 */         return 1;
/*      */       }
/*  365 */       e += extCount; int k;
/*  366 */       for (k = 0; k < dmSizes.length && 
/*  367 */         (dmSizes[k]).dataSize < e; k++);
/*      */ 
/*      */       
/*  370 */       dm = dmSizes[k];
/*  371 */       this.height = dm.height;
/*  372 */       this.width = dm.width;
/*      */     } else {
/*  374 */       int k; for (k = 0; k < dmSizes.length && (
/*  375 */         this.height != (dmSizes[k]).height || this.width != (dmSizes[k]).width); k++);
/*      */ 
/*      */       
/*  378 */       if (k == dmSizes.length) {
/*  379 */         return 3;
/*      */       }
/*  381 */       dm = dmSizes[k];
/*  382 */       e = getEncodation(text, textOffset + this.extOut, textSize - this.extOut, data, extCount, dm.dataSize - extCount, this.options, true);
/*  383 */       if (e < 0) {
/*  384 */         return 1;
/*      */       }
/*  386 */       e += extCount;
/*      */     } 
/*  388 */     if ((this.options & 0x40) != 0) {
/*  389 */       return 0;
/*      */     }
/*  391 */     this.image = new byte[(dm.width + 2 * this.ws + 7) / 8 * (dm.height + 2 * this.ws)];
/*  392 */     makePadding(data, e, dm.dataSize - e);
/*  393 */     this.place = Placement.doPlacement(dm.height - dm.height / dm.heightSection * 2, dm.width - dm.width / dm.widthSection * 2);
/*  394 */     int full = dm.dataSize + (dm.dataSize + 2) / dm.dataBlock * dm.errorBlock;
/*  395 */     ReedSolomon.generateECC(data, dm.dataSize, dm.dataBlock, dm.errorBlock);
/*  396 */     draw(data, full, dm);
/*  397 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/*  407 */     return this.height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeight(int height) {
/*  449 */     this.height = height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWidth() {
/*  459 */     return this.width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWidth(int width) {
/*  501 */     this.width = width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWs() {
/*  510 */     return this.ws;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWs(int ws) {
/*  519 */     this.ws = ws;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOptions() {
/*  528 */     return this.options;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptions(int options) {
/*  561 */     this.options = options;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncoding(String encoding) {
/*  570 */     this.encoding = encoding;
/*      */   }
/*      */   
/*      */   public String getEncoding() {
/*  574 */     return this.encoding;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void makePadding(byte[] data, int position, int count) {
/*  580 */     if (count <= 0)
/*      */       return; 
/*  582 */     data[position++] = -127;
/*  583 */     while (--count > 0) {
/*  584 */       int t = 129 + (position + 1) * 149 % 253 + 1;
/*  585 */       if (t > 254)
/*  586 */         t -= 254; 
/*  587 */       data[position++] = (byte)t;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isDigit(int c) {
/*  592 */     return (c >= 48 && c <= 57);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int asciiEncodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, int symbolIndex, int prevEnc, int origDataOffset) {
/*  598 */     int ptrIn = textOffset;
/*  599 */     int ptrOut = dataOffset;
/*  600 */     textLength += textOffset;
/*  601 */     dataLength += dataOffset;
/*  602 */     while (ptrIn < textLength) {
/*  603 */       int c = text[ptrIn++] & 0xFF;
/*  604 */       if (isDigit(c) && symbolIndex > 0 && prevEnc == 1 && isDigit(text[ptrIn - 2] & 0xFF) && data[dataOffset - 1] > 48 && data[dataOffset - 1] < 59) {
/*      */         
/*  606 */         data[ptrOut - 1] = (byte)(((text[ptrIn - 2] & 0xFF) - 48) * 10 + c - 48 + 130);
/*  607 */         return ptrOut - origDataOffset;
/*      */       } 
/*  609 */       if (ptrOut >= dataLength)
/*  610 */         return -1; 
/*  611 */       if (isDigit(c) && symbolIndex < 0 && ptrIn < textLength && isDigit(text[ptrIn] & 0xFF)) {
/*  612 */         data[ptrOut++] = (byte)((c - 48) * 10 + (text[ptrIn++] & 0xFF) - 48 + 130); continue;
/*  613 */       }  if (c > 127) {
/*  614 */         if (ptrOut + 1 >= dataLength)
/*  615 */           return -1; 
/*  616 */         data[ptrOut++] = -21;
/*  617 */         data[ptrOut++] = (byte)(c - 128 + 1); continue;
/*      */       } 
/*  619 */       data[ptrOut++] = (byte)(c + 1);
/*      */     } 
/*      */     
/*  622 */     return ptrOut - origDataOffset;
/*      */   }
/*      */   
/*      */   private int b256Encodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, int symbolIndex, int prevEnc, int origDataOffset) {
/*      */     int minRequiredDataIncrement;
/*  627 */     if (textLength == 0)
/*  628 */       return 0; 
/*  629 */     int simulatedDataOffset = dataOffset;
/*  630 */     if (prevEnc != 4) {
/*  631 */       if (textLength < 250 && textLength + 2 > dataLength)
/*  632 */         return -1; 
/*  633 */       if (textLength >= 250 && textLength + 3 > dataLength)
/*  634 */         return -1; 
/*  635 */       data[dataOffset] = -25;
/*      */     } else {
/*  637 */       int latestModeEntry = symbolIndex - 1;
/*  638 */       while (latestModeEntry > 0 && this.switchMode[3][latestModeEntry] == 4) {
/*  639 */         latestModeEntry--;
/*      */       }
/*  641 */       textLength = symbolIndex - latestModeEntry + 1;
/*  642 */       if (textLength != 250 && 1 > dataLength)
/*  643 */         return -1; 
/*  644 */       if (textLength == 250 && 2 > dataLength)
/*  645 */         return -1; 
/*  646 */       simulatedDataOffset -= textLength - 1 + ((textLength < 250) ? 2 : 3);
/*      */     } 
/*  648 */     if (textLength < 250) {
/*  649 */       data[simulatedDataOffset + 1] = (byte)textLength;
/*  650 */       minRequiredDataIncrement = (prevEnc != 4) ? 2 : 0;
/*  651 */     } else if (textLength == 250 && prevEnc == 4) {
/*  652 */       data[simulatedDataOffset + 1] = (byte)(textLength / 250 + 249);
/*  653 */       for (int i = dataOffset + 1; i > simulatedDataOffset + 2; i--)
/*  654 */         data[i] = data[i - 1]; 
/*  655 */       data[simulatedDataOffset + 2] = (byte)(textLength % 250);
/*  656 */       minRequiredDataIncrement = 1;
/*      */     } else {
/*  658 */       data[simulatedDataOffset + 1] = (byte)(textLength / 250 + 249);
/*  659 */       data[simulatedDataOffset + 2] = (byte)(textLength % 250);
/*  660 */       minRequiredDataIncrement = (prevEnc != 4) ? 3 : 0;
/*      */     } 
/*  662 */     if (prevEnc == 4)
/*  663 */       textLength = 1; 
/*  664 */     System.arraycopy(text, textOffset, data, minRequiredDataIncrement + dataOffset, textLength);
/*  665 */     for (int j = (prevEnc != 4) ? (dataOffset + 1) : dataOffset; j < minRequiredDataIncrement + textLength + dataOffset; j++) {
/*  666 */       randomizationAlgorithm255(data, j);
/*      */     }
/*  668 */     if (prevEnc == 4)
/*  669 */       randomizationAlgorithm255(data, simulatedDataOffset + 1); 
/*  670 */     return textLength + dataOffset + minRequiredDataIncrement - origDataOffset;
/*      */   }
/*      */   
/*      */   private void randomizationAlgorithm255(byte[] data, int j) {
/*  674 */     int c = data[j] & 0xFF;
/*  675 */     int prn = 149 * (j + 1) % 255 + 1;
/*  676 */     int tv = c + prn;
/*  677 */     if (tv > 255)
/*  678 */       tv -= 256; 
/*  679 */     data[j] = (byte)tv;
/*      */   }
/*      */ 
/*      */   
/*      */   private int X12Encodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, int symbolIndex, int prevEnc, int origDataOffset) {
/*  684 */     boolean latch = true;
/*      */     
/*  686 */     if (textLength == 0)
/*  687 */       return 0; 
/*  688 */     int ptrIn = 0;
/*  689 */     int ptrOut = 0;
/*  690 */     byte[] x = new byte[textLength];
/*  691 */     int count = 0;
/*  692 */     for (; ptrIn < textLength; ptrIn++) {
/*  693 */       int i = "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf((char)text[ptrIn + textOffset]);
/*  694 */       if (i >= 0) {
/*  695 */         x[ptrIn] = (byte)i;
/*  696 */         count++;
/*      */       } else {
/*  698 */         x[ptrIn] = 100;
/*  699 */         if (count >= 6)
/*  700 */           count -= count / 3 * 3; 
/*  701 */         for (int j = 0; j < count; j++)
/*  702 */           x[ptrIn - j - 1] = 100; 
/*  703 */         count = 0;
/*      */       } 
/*      */     } 
/*  706 */     if (count >= 6)
/*  707 */       count -= count / 3 * 3; 
/*  708 */     for (int k = 0; k < count; k++)
/*  709 */       x[ptrIn - k - 1] = 100; 
/*  710 */     ptrIn = 0;
/*  711 */     byte c = 0;
/*  712 */     for (; ptrIn < textLength; ptrIn++) {
/*  713 */       c = x[ptrIn];
/*  714 */       if (ptrOut > dataLength)
/*      */         break; 
/*  716 */       if (c < 40) {
/*  717 */         if ((ptrIn == 0 && latch) || (ptrIn > 0 && x[ptrIn - 1] > 40))
/*  718 */           data[dataOffset + ptrOut++] = -18; 
/*  719 */         if (ptrOut + 2 > dataLength)
/*      */           break; 
/*  721 */         int n = 1600 * x[ptrIn] + 40 * x[ptrIn + 1] + x[ptrIn + 2] + 1;
/*  722 */         data[dataOffset + ptrOut++] = (byte)(n / 256);
/*  723 */         data[dataOffset + ptrOut++] = (byte)n;
/*  724 */         ptrIn += 2;
/*      */       } else {
/*  726 */         boolean enterASCII = true;
/*  727 */         if (symbolIndex <= 0) {
/*  728 */           if (ptrIn > 0 && x[ptrIn - 1] < 40)
/*  729 */             data[dataOffset + ptrOut++] = -2; 
/*  730 */         } else if (symbolIndex > 4 && prevEnc == 5 && "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf((char)text[textOffset]) >= 0 && "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf((char)text[textOffset - 1]) >= 0) {
/*  731 */           int latestModeEntry = symbolIndex - 1;
/*  732 */           while (latestModeEntry > 0 && this.switchMode[4][latestModeEntry] == 5 && "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
/*  733 */             .indexOf((char)text[textOffset - symbolIndex - latestModeEntry + 1]) >= 0) {
/*  734 */             latestModeEntry--;
/*      */           }
/*  736 */           int unlatch = -1;
/*  737 */           if (symbolIndex - latestModeEntry >= 5) {
/*  738 */             for (int i = 1; i <= symbolIndex - latestModeEntry; i++) {
/*  739 */               if (data[dataOffset - i] == -2) {
/*  740 */                 unlatch = dataOffset - i;
/*      */                 break;
/*      */               } 
/*      */             } 
/*  744 */             int amountOfEncodedWithASCII = (unlatch >= 0) ? (dataOffset - unlatch - 1) : (symbolIndex - latestModeEntry);
/*  745 */             if (amountOfEncodedWithASCII % 3 == 2) {
/*  746 */               enterASCII = false;
/*  747 */               textLength = amountOfEncodedWithASCII + 1;
/*  748 */               textOffset -= amountOfEncodedWithASCII;
/*  749 */               dataLength += (unlatch < 0) ? amountOfEncodedWithASCII : (amountOfEncodedWithASCII + 1);
/*  750 */               dataOffset -= (unlatch < 0) ? amountOfEncodedWithASCII : (amountOfEncodedWithASCII + 1);
/*  751 */               ptrIn = -1;
/*  752 */               latch = (unlatch != dataOffset);
/*  753 */               x = new byte[amountOfEncodedWithASCII + 1];
/*  754 */               for (int j = 0; j <= amountOfEncodedWithASCII; j++) {
/*  755 */                 x[j] = (byte)"\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf((char)text[textOffset + j]);
/*      */               }
/*      */             } else {
/*  758 */               x = new byte[1];
/*  759 */               x[0] = 100;
/*      */             } 
/*      */           } 
/*      */         } 
/*  763 */         if (enterASCII) {
/*  764 */           int i = asciiEncodation(text, textOffset + ptrIn, 1, data, dataOffset + ptrOut, dataLength, -1, -1, origDataOffset);
/*  765 */           if (i < 0)
/*  766 */             return -1; 
/*  767 */           if (data[dataOffset + ptrOut] == -21)
/*  768 */             ptrOut++; 
/*  769 */           ptrOut++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  773 */     c = 100;
/*  774 */     if (textLength > 0)
/*  775 */       c = x[textLength - 1]; 
/*  776 */     if (ptrIn != textLength)
/*  777 */       return -1; 
/*  778 */     if (c < 40)
/*  779 */       data[dataOffset + ptrOut++] = -2; 
/*  780 */     if (ptrOut > dataLength)
/*  781 */       return -1; 
/*  782 */     return ptrOut + dataOffset - origDataOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   private int EdifactEncodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, int symbolIndex, int prevEnc, int origDataOffset, boolean sizeFixed) {
/*  787 */     if (textLength == 0)
/*  788 */       return 0; 
/*  789 */     int ptrIn = 0;
/*  790 */     int ptrOut = 0;
/*  791 */     int edi = 0;
/*  792 */     int pedi = 18;
/*  793 */     boolean ascii = true;
/*  794 */     int latestModeEntryActual = -1, latestModeEntryC40orX12 = -1, prevMode = -1;
/*  795 */     if (prevEnc == 6 && ((text[textOffset] & 0xFF & 0xE0) == 64 || (text[textOffset] & 0xFF & 0xE0) == 32) && (text[textOffset] & 0xFF) != 95 && ((text[textOffset - 1] & 0xFF & 0xE0) == 64 || (text[textOffset - 1] & 0xFF & 0xE0) == 32) && (text[textOffset - 1] & 0xFF) != 95) {
/*      */       
/*  797 */       latestModeEntryActual = symbolIndex - 1;
/*  798 */       while (latestModeEntryActual > 0 && this.switchMode[5][latestModeEntryActual] == 6) {
/*  799 */         int c = text[textOffset - symbolIndex - latestModeEntryActual + 1] & 0xFF;
/*  800 */         if (((c & 0xE0) == 64 || (c & 0xE0) == 32) && c != 95) {
/*  801 */           latestModeEntryActual--;
/*      */         }
/*      */       } 
/*      */       
/*  805 */       prevMode = (this.switchMode[5][latestModeEntryActual] == 2 || this.switchMode[5][latestModeEntryActual] == 5) ? this.switchMode[5][latestModeEntryActual] : -1;
/*      */       
/*  807 */       if (prevMode > 0)
/*  808 */         latestModeEntryC40orX12 = latestModeEntryActual; 
/*  809 */       while (prevMode > 0 && latestModeEntryC40orX12 > 0 && this.switchMode[prevMode - 1][latestModeEntryC40orX12] == prevMode) {
/*  810 */         int c = text[textOffset - symbolIndex - latestModeEntryC40orX12 + 1] & 0xFF;
/*  811 */         if (((c & 0xE0) == 64 || (c & 0xE0) == 32) && c != 95) {
/*  812 */           latestModeEntryC40orX12--; continue;
/*      */         } 
/*  814 */         latestModeEntryC40orX12 = -1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  819 */     int dataSize = dataOffset + dataLength;
/*  820 */     boolean asciiOneSymbol = false;
/*  821 */     if (symbolIndex != -1)
/*  822 */       asciiOneSymbol = true; 
/*  823 */     int dataTaken = 0, dataRequired = 0;
/*  824 */     if (latestModeEntryC40orX12 >= 0 && symbolIndex - latestModeEntryC40orX12 + 1 > 9) {
/*  825 */       textLength = symbolIndex - latestModeEntryC40orX12 + 1;
/*  826 */       dataTaken = 0;
/*  827 */       dataRequired = 0;
/*  828 */       dataRequired += 1 + textLength / 4 * 3;
/*  829 */       if (!sizeFixed && (symbolIndex == text.length - 1 || symbolIndex < 0) && textLength % 4 < 3) {
/*  830 */         dataSize = Integer.MAX_VALUE;
/*  831 */         for (int j = 0; j < dmSizes.length; j++) {
/*  832 */           if ((dmSizes[j]).dataSize >= dataRequired + textLength % 4) {
/*  833 */             dataSize = (dmSizes[j]).dataSize;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  838 */       if (dataSize - dataOffset - dataRequired <= 2 && textLength % 4 <= 2) {
/*  839 */         dataRequired += textLength % 4;
/*      */       } else {
/*  841 */         dataRequired += textLength % 4 + 1;
/*  842 */         if (textLength % 4 == 3)
/*  843 */           dataRequired--; 
/*      */       } 
/*  845 */       for (int i = dataOffset - 1; i >= 0; i--) {
/*  846 */         dataTaken++;
/*  847 */         if (data[i] == ((prevMode == 2) ? -26 : -18)) {
/*      */           break;
/*      */         }
/*      */       } 
/*  851 */       if (dataRequired <= dataTaken) {
/*  852 */         asciiOneSymbol = false;
/*  853 */         textOffset -= textLength - 1;
/*  854 */         dataOffset -= dataTaken;
/*  855 */         dataLength += dataTaken;
/*      */       } 
/*  857 */     } else if (latestModeEntryActual >= 0 && symbolIndex - latestModeEntryActual + 1 > 9) {
/*  858 */       textLength = symbolIndex - latestModeEntryActual + 1;
/*  859 */       dataRequired += 1 + textLength / 4 * 3;
/*  860 */       if (dataSize - dataOffset - dataRequired <= 2 && textLength % 4 <= 2) {
/*  861 */         dataRequired += textLength % 4;
/*      */       } else {
/*  863 */         dataRequired += textLength % 4 + 1;
/*  864 */         if (textLength % 4 == 3)
/*  865 */           dataRequired--; 
/*      */       } 
/*  867 */       int dataNewOffset = 0;
/*  868 */       int latchEdi = -1;
/*  869 */       for (int i = origDataOffset; i < dataOffset; i++) {
/*  870 */         if (data[i] == -16 && dataOffset - i <= dataRequired) {
/*  871 */           latchEdi = i; break;
/*      */         } 
/*      */       } 
/*  874 */       if (latchEdi != -1) {
/*  875 */         dataTaken += dataOffset - latchEdi;
/*  876 */         if ((text[textOffset] & 0xFF) > 127) {
/*  877 */           dataTaken += 2;
/*      */         } else {
/*  879 */           if (isDigit(text[textOffset] & 0xFF) && isDigit(text[textOffset - 1] & 0xFF) && data[dataOffset - 1] >= 49 && data[dataOffset - 1] <= 58)
/*      */           {
/*  881 */             dataTaken--;
/*      */           }
/*  883 */           dataTaken++;
/*      */         } 
/*  885 */         dataNewOffset = dataOffset - latchEdi;
/*      */       } else {
/*  887 */         for (int j = symbolIndex - latestModeEntryActual; j >= 0; j--) {
/*  888 */           if ((text[textOffset - j] & 0xFF) > 127) {
/*  889 */             dataTaken += 2;
/*      */           } else {
/*  891 */             if (j > 0 && isDigit(text[textOffset - j] & 0xFF) && isDigit(text[textOffset - j + 1] & 0xFF)) {
/*  892 */               if (j == 1)
/*  893 */                 dataNewOffset = dataTaken; 
/*  894 */               j--;
/*      */             } 
/*  896 */             dataTaken++;
/*      */           } 
/*  898 */           if (j == 1)
/*  899 */             dataNewOffset = dataTaken; 
/*      */         } 
/*      */       } 
/*  902 */       if (dataRequired <= dataTaken) {
/*  903 */         asciiOneSymbol = false;
/*  904 */         textOffset -= textLength - 1;
/*  905 */         dataOffset -= dataNewOffset;
/*  906 */         dataLength += dataNewOffset;
/*      */       } 
/*      */     } 
/*  909 */     if (asciiOneSymbol) {
/*  910 */       int c = text[textOffset] & 0xFF;
/*  911 */       if (isDigit(c) && textOffset + ptrIn > 0 && isDigit(text[textOffset - 1] & 0xFF) && prevEnc == 6 && data[dataOffset - 1] >= 49 && data[dataOffset - 1] <= 58) {
/*      */         
/*  913 */         data[dataOffset + ptrOut - 1] = (byte)(((text[textOffset - 1] & 0xFF) - 48) * 10 + c - 48 + 130);
/*  914 */         return dataOffset - origDataOffset;
/*      */       } 
/*  916 */       return asciiEncodation(text, textOffset + ptrIn, 1, data, dataOffset + ptrOut, dataLength, -1, -1, origDataOffset);
/*      */     } 
/*      */     
/*  919 */     for (; ptrIn < textLength; ptrIn++) {
/*  920 */       int c = text[ptrIn + textOffset] & 0xFF;
/*  921 */       if (((c & 0xE0) == 64 || (c & 0xE0) == 32) && c != 95)
/*  922 */       { if (ascii) {
/*  923 */           if (ptrOut + 1 > dataLength)
/*      */             break; 
/*  925 */           data[dataOffset + ptrOut++] = -16;
/*  926 */           ascii = false;
/*      */         } 
/*  928 */         c &= 0x3F;
/*  929 */         edi |= c << pedi;
/*  930 */         if (pedi == 0) {
/*  931 */           if (ptrOut + 3 > dataLength)
/*      */             break; 
/*  933 */           data[dataOffset + ptrOut++] = (byte)(edi >> 16);
/*  934 */           data[dataOffset + ptrOut++] = (byte)(edi >> 8);
/*  935 */           data[dataOffset + ptrOut++] = (byte)edi;
/*  936 */           edi = 0;
/*  937 */           pedi = 18;
/*      */         } else {
/*  939 */           pedi -= 6;
/*      */         }  }
/*  941 */       else { if (!ascii) {
/*  942 */           edi |= 31 << pedi;
/*  943 */           if (ptrOut + 3 - pedi / 8 > dataLength)
/*      */             break; 
/*  945 */           data[dataOffset + ptrOut++] = (byte)(edi >> 16);
/*  946 */           if (pedi <= 12)
/*  947 */             data[dataOffset + ptrOut++] = (byte)(edi >> 8); 
/*  948 */           if (pedi <= 6)
/*  949 */             data[dataOffset + ptrOut++] = (byte)edi; 
/*  950 */           ascii = true;
/*  951 */           pedi = 18;
/*  952 */           edi = 0;
/*      */         } 
/*  954 */         if (isDigit(c) && textOffset + ptrIn > 0 && isDigit(text[textOffset + ptrIn - 1] & 0xFF) && prevEnc == 6 && data[dataOffset - 1] >= 49 && data[dataOffset - 1] <= 58) {
/*      */           
/*  956 */           data[dataOffset + ptrOut - 1] = (byte)(((text[textOffset - 1] & 0xFF) - 48) * 10 + c - 48 + 130);
/*  957 */           ptrOut--;
/*      */         } else {
/*  959 */           int i = asciiEncodation(text, textOffset + ptrIn, 1, data, dataOffset + ptrOut, dataLength, -1, -1, origDataOffset);
/*  960 */           if (i < 0)
/*  961 */             return -1; 
/*  962 */           if (data[dataOffset + ptrOut] == -21)
/*  963 */             ptrOut++; 
/*  964 */           ptrOut++;
/*      */         }  }
/*      */     
/*      */     } 
/*  968 */     if (ptrIn != textLength)
/*  969 */       return -1; 
/*  970 */     if (!sizeFixed && (symbolIndex == text.length - 1 || symbolIndex < 0)) {
/*  971 */       dataSize = Integer.MAX_VALUE;
/*  972 */       for (int i = 0; i < dmSizes.length; i++) {
/*  973 */         if ((dmSizes[i]).dataSize >= dataOffset + ptrOut + 3 - pedi / 6) {
/*  974 */           dataSize = (dmSizes[i]).dataSize;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  979 */     if (dataSize - dataOffset - ptrOut <= 2 && pedi >= 6) {
/*  980 */       if (pedi != 18 && ptrOut + 2 - pedi / 8 > dataLength)
/*  981 */         return -1; 
/*  982 */       if (pedi <= 12) {
/*  983 */         byte val = (byte)(edi >> 18 & 0x3F);
/*  984 */         if ((val & 0x20) == 0)
/*  985 */           val = (byte)(val | 0x40); 
/*  986 */         data[dataOffset + ptrOut++] = (byte)(val + 1);
/*      */       } 
/*  988 */       if (pedi <= 6) {
/*  989 */         byte val = (byte)(edi >> 12 & 0x3F);
/*  990 */         if ((val & 0x20) == 0)
/*  991 */           val = (byte)(val | 0x40); 
/*  992 */         data[dataOffset + ptrOut++] = (byte)(val + 1);
/*      */       } 
/*  994 */     } else if (!ascii) {
/*  995 */       edi |= 31 << pedi;
/*  996 */       if (ptrOut + 3 - pedi / 8 > dataLength)
/*  997 */         return -1; 
/*  998 */       data[dataOffset + ptrOut++] = (byte)(edi >> 16);
/*  999 */       if (pedi <= 12)
/* 1000 */         data[dataOffset + ptrOut++] = (byte)(edi >> 8); 
/* 1001 */       if (pedi <= 6)
/* 1002 */         data[dataOffset + ptrOut++] = (byte)edi; 
/*      */     } 
/* 1004 */     return ptrOut + dataOffset - origDataOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   private int C40OrTextEncodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, boolean c40, int symbolIndex, int prevEnc, int origDataOffset) {
/*      */     String basic, shift3;
/* 1010 */     if (textLength == 0)
/* 1011 */       return 0; 
/* 1012 */     int ptrIn = 0;
/* 1013 */     int ptrOut = 0;
/* 1014 */     String shift2 = "!\"#$%&'()*+,-./:;<=>?@[\\]^_";
/* 1015 */     if (c40) {
/* 1016 */       basic = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/* 1017 */       shift3 = "`abcdefghijklmnopqrstuvwxyz{|}~";
/*      */     } else {
/* 1019 */       basic = " 0123456789abcdefghijklmnopqrstuvwxyz";
/* 1020 */       shift3 = "`ABCDEFGHIJKLMNOPQRSTUVWXYZ{|}~";
/*      */     } 
/* 1022 */     boolean addLatch = true, usingASCII = false;
/* 1023 */     int mode = c40 ? 2 : 3;
/* 1024 */     if (prevEnc == mode) {
/* 1025 */       usingASCII = true;
/* 1026 */       int latestModeEntry = symbolIndex - 1;
/* 1027 */       while (latestModeEntry > 0 && this.switchMode[mode - 1][latestModeEntry] == mode) {
/* 1028 */         latestModeEntry--;
/*      */       }
/* 1030 */       int unlatch = -1;
/* 1031 */       int dataAmountOfEncodedWithASCII = 0;
/* 1032 */       if (symbolIndex - latestModeEntry >= 5) {
/* 1033 */         int j; for (j = symbolIndex - latestModeEntry; j > 0; j--) {
/* 1034 */           int c = text[textOffset - j] & 0xFF;
/* 1035 */           if (c > 127) {
/* 1036 */             dataAmountOfEncodedWithASCII += 2;
/*      */           } else {
/* 1038 */             dataAmountOfEncodedWithASCII++;
/*      */           } 
/* 1040 */         }  for (j = 1; j <= dataAmountOfEncodedWithASCII && 
/* 1041 */           j <= dataOffset; j++) {
/*      */           
/* 1043 */           if (data[dataOffset - j] == -2) {
/* 1044 */             unlatch = dataOffset - j;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1048 */         int amountOfEncodedWithASCII = 0;
/* 1049 */         if (unlatch >= 0) {
/* 1050 */           for (j = unlatch + 1; j < dataOffset; j++) {
/* 1051 */             if (data[j] == -21)
/* 1052 */               j++; 
/* 1053 */             if (data[j] >= -127 && data[j] <= -27)
/* 1054 */               amountOfEncodedWithASCII++; 
/* 1055 */             amountOfEncodedWithASCII++;
/*      */           } 
/*      */         } else {
/* 1058 */           amountOfEncodedWithASCII = symbolIndex - latestModeEntry;
/* 1059 */         }  int dataOffsetNew = 0;
/* 1060 */         for (j = amountOfEncodedWithASCII; j > 0; j--) {
/* 1061 */           int requiredCapacityForASCII = 0;
/* 1062 */           int requiredCapacityForC40orText = 0;
/* 1063 */           for (int k = j; k >= 0; k--) {
/* 1064 */             int c = text[textOffset - k] & 0xFF;
/* 1065 */             if (c > 127) {
/* 1066 */               c -= 128;
/* 1067 */               requiredCapacityForC40orText += 2;
/*      */             } 
/* 1069 */             requiredCapacityForC40orText += (basic.indexOf((char)c) >= 0) ? 1 : 2;
/* 1070 */             if (c > 127) {
/* 1071 */               requiredCapacityForASCII += 2;
/*      */             } else {
/* 1073 */               if (k > 0 && isDigit(c) && isDigit(text[textOffset - k + 1] & 0xFF)) {
/* 1074 */                 requiredCapacityForC40orText += (basic.indexOf((char)text[textOffset - k + 1]) >= 0) ? 1 : 2;
/* 1075 */                 k--;
/* 1076 */                 dataOffsetNew = requiredCapacityForASCII + 1;
/*      */               } 
/* 1078 */               requiredCapacityForASCII++;
/*      */             } 
/* 1080 */             if (k == 1)
/* 1081 */               dataOffsetNew = requiredCapacityForASCII; 
/*      */           } 
/* 1083 */           addLatch = (unlatch < 0 || dataOffset - requiredCapacityForASCII != unlatch);
/* 1084 */           if (requiredCapacityForC40orText % 3 == 0 && requiredCapacityForC40orText / 3 * 2 + (addLatch ? 2 : 0) < requiredCapacityForASCII) {
/*      */             
/* 1086 */             usingASCII = false;
/* 1087 */             textLength = j + 1;
/* 1088 */             textOffset -= j;
/* 1089 */             dataOffset -= addLatch ? dataOffsetNew : (dataOffsetNew + 1);
/* 1090 */             dataLength += addLatch ? dataOffsetNew : (dataOffsetNew + 1);
/*      */             break;
/*      */           } 
/* 1093 */           if (isDigit(text[textOffset - j] & 0xFF) && isDigit(text[textOffset - j + 1] & 0xFF))
/* 1094 */             j--; 
/*      */         } 
/*      */       } 
/* 1097 */     } else if (symbolIndex != -1) {
/* 1098 */       usingASCII = true;
/*      */     } 
/* 1100 */     if (dataOffset < 0) {
/* 1101 */       return -1;
/*      */     }
/* 1103 */     if (usingASCII) {
/* 1104 */       return asciiEncodation(text, textOffset, 1, data, dataOffset, dataLength, (prevEnc == mode) ? 1 : -1, 1, origDataOffset);
/*      */     }
/* 1106 */     if (addLatch) {
/* 1107 */       data[dataOffset + ptrOut++] = c40 ? -26 : -17;
/*      */     }
/* 1109 */     int[] enc = new int[textLength * 4 + 10];
/* 1110 */     int encPtr = 0;
/* 1111 */     int last0 = 0;
/* 1112 */     int last1 = 0;
/* 1113 */     while (ptrIn < textLength) {
/* 1114 */       if (encPtr % 3 == 0) {
/* 1115 */         last0 = ptrIn;
/* 1116 */         last1 = encPtr;
/*      */       } 
/* 1118 */       int c = text[textOffset + ptrIn++] & 0xFF;
/* 1119 */       if (c > 127) {
/* 1120 */         c -= 128;
/* 1121 */         enc[encPtr++] = 1;
/* 1122 */         enc[encPtr++] = 30;
/*      */       } 
/* 1124 */       int idx = basic.indexOf((char)c);
/* 1125 */       if (idx >= 0) {
/* 1126 */         enc[encPtr++] = idx + 3; continue;
/* 1127 */       }  if (c < 32) {
/* 1128 */         enc[encPtr++] = 0;
/* 1129 */         enc[encPtr++] = c; continue;
/* 1130 */       }  if ((idx = shift2.indexOf((char)c)) >= 0) {
/* 1131 */         enc[encPtr++] = 1;
/* 1132 */         enc[encPtr++] = idx; continue;
/* 1133 */       }  if ((idx = shift3.indexOf((char)c)) >= 0) {
/* 1134 */         enc[encPtr++] = 2;
/* 1135 */         enc[encPtr++] = idx;
/*      */       } 
/*      */     } 
/* 1138 */     if (encPtr % 3 != 0) {
/* 1139 */       ptrIn = last0;
/* 1140 */       encPtr = last1;
/*      */     } 
/* 1142 */     if (encPtr / 3 * 2 > dataLength - 2) {
/* 1143 */       return -1;
/*      */     }
/* 1145 */     int i = 0;
/* 1146 */     for (; i < encPtr; i += 3) {
/* 1147 */       int a = 1600 * enc[i] + 40 * enc[i + 1] + enc[i + 2] + 1;
/* 1148 */       data[dataOffset + ptrOut++] = (byte)(a / 256);
/* 1149 */       data[dataOffset + ptrOut++] = (byte)a;
/*      */     } 
/* 1151 */     if (dataLength - ptrOut > 2)
/* 1152 */       data[dataOffset + ptrOut++] = -2; 
/* 1153 */     if (symbolIndex < 0 && textLength > ptrIn) {
/* 1154 */       i = asciiEncodation(text, textOffset + ptrIn, textLength - ptrIn, data, dataOffset + ptrOut, dataLength - ptrOut, -1, -1, origDataOffset);
/* 1155 */       return i;
/*      */     } 
/* 1157 */     return ptrOut + dataOffset - origDataOffset;
/*      */   }
/*      */   
/*      */   private void setBit(int x, int y, int xByte) {
/* 1161 */     this.image[y * xByte + x / 8] = (byte)(this.image[y * xByte + x / 8] | (byte)(128 >> (x & 0x7)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void draw(byte[] data, int dataSize, DmParams dm) {
/* 1166 */     int xByte = (dm.width + this.ws * 2 + 7) / 8;
/* 1167 */     Arrays.fill(this.image, (byte)0);
/*      */     
/*      */     int i;
/* 1170 */     for (i = this.ws; i < dm.height + this.ws; i += dm.heightSection) {
/* 1171 */       for (int j = this.ws; j < dm.width + this.ws; j += 2) {
/* 1172 */         setBit(j, i, xByte);
/*      */       }
/*      */     } 
/*      */     
/* 1176 */     for (i = dm.heightSection - 1 + this.ws; i < dm.height + this.ws; i += dm.heightSection) {
/* 1177 */       for (int j = this.ws; j < dm.width + this.ws; j++) {
/* 1178 */         setBit(j, i, xByte);
/*      */       }
/*      */     } 
/*      */     
/* 1182 */     for (i = this.ws; i < dm.width + this.ws; i += dm.widthSection) {
/* 1183 */       for (int j = this.ws; j < dm.height + this.ws; j++) {
/* 1184 */         setBit(i, j, xByte);
/*      */       }
/*      */     } 
/*      */     
/* 1188 */     for (i = dm.widthSection - 1 + this.ws; i < dm.width + this.ws; i += dm.widthSection) {
/* 1189 */       for (int j = 1 + this.ws; j < dm.height + this.ws; j += 2) {
/* 1190 */         setBit(i, j, xByte);
/*      */       }
/*      */     } 
/* 1193 */     int p = 0; int ys;
/* 1194 */     for (ys = 0; ys < dm.height; ys += dm.heightSection) {
/* 1195 */       for (int y = 1; y < dm.heightSection - 1; y++) {
/* 1196 */         int xs; for (xs = 0; xs < dm.width; xs += dm.widthSection) {
/* 1197 */           for (int x = 1; x < dm.widthSection - 1; x++) {
/* 1198 */             int z = this.place[p++];
/* 1199 */             if (z == 1 || (z > 1 && (data[z / 8 - 1] & 0xFF & 128 >> z % 8) != 0))
/* 1200 */               setBit(x + xs + this.ws, y + ys + this.ws, xByte); 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int minValueInColumn(int[][] array, int column) {
/* 1208 */     int min = Integer.MAX_VALUE;
/* 1209 */     for (int i = 0; i < 6; i++) {
/* 1210 */       if (array[i][column] < min && array[i][column] >= 0)
/* 1211 */         min = array[i][column]; 
/* 1212 */     }  return (min != Integer.MAX_VALUE) ? min : -1;
/*      */   }
/*      */   
/*      */   private static int valuePositionInColumn(int[][] array, int column, int value) {
/* 1216 */     for (int i = 0; i < 6; i++) {
/* 1217 */       if (array[i][column] == value)
/* 1218 */         return i; 
/* 1219 */     }  return -1;
/*      */   }
/*      */   
/*      */   private void solveFAndSwitchMode(int[] forMin, int mode, int currIndex) {
/* 1223 */     if (forMin[mode] >= 0 && this.f[mode][currIndex - 1] >= 0) {
/* 1224 */       this.f[mode][currIndex] = forMin[mode];
/* 1225 */       this.switchMode[mode][currIndex] = mode + 1;
/*      */     } else {
/* 1227 */       this.f[mode][currIndex] = Integer.MAX_VALUE;
/*      */     } 
/* 1229 */     for (int i = 0; i < 6; i++) {
/* 1230 */       if (forMin[i] < this.f[mode][currIndex] && forMin[i] >= 0 && this.f[i][currIndex - 1] >= 0) {
/* 1231 */         this.f[mode][currIndex] = forMin[i];
/* 1232 */         this.switchMode[mode][currIndex] = i + 1;
/*      */       } 
/*      */     } 
/* 1235 */     if (this.f[mode][currIndex] == Integer.MAX_VALUE) {
/* 1236 */       this.f[mode][currIndex] = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private int getEncodation(byte[] text, int textOffset, int textSize, byte[] data, int dataOffset, int dataSize, int options, boolean sizeFixed) {
/* 1242 */     if (dataSize < 0)
/* 1243 */       return -1; 
/* 1244 */     options &= 0x7;
/* 1245 */     if (options == 0) {
/* 1246 */       if (textSize == 0)
/* 1247 */         return 0; 
/* 1248 */       byte[][] dataDynamic = new byte[6][data.length]; int i;
/* 1249 */       for (i = 0; i < 6; i++) {
/* 1250 */         System.arraycopy(data, 0, dataDynamic[i], 0, data.length);
/* 1251 */         this.switchMode[i][0] = i + 1;
/*      */       } 
/* 1253 */       this.f[0][0] = asciiEncodation(text, textOffset, 1, dataDynamic[0], dataOffset, dataSize, 0, -1, dataOffset);
/* 1254 */       this.f[1][0] = C40OrTextEncodation(text, textOffset, 1, dataDynamic[1], dataOffset, dataSize, true, 0, -1, dataOffset);
/* 1255 */       this.f[2][0] = C40OrTextEncodation(text, textOffset, 1, dataDynamic[2], dataOffset, dataSize, false, 0, -1, dataOffset);
/* 1256 */       this.f[3][0] = b256Encodation(text, textOffset, 1, dataDynamic[3], dataOffset, dataSize, 0, -1, dataOffset);
/* 1257 */       this.f[4][0] = X12Encodation(text, textOffset, 1, dataDynamic[4], dataOffset, dataSize, 0, -1, dataOffset);
/* 1258 */       this.f[5][0] = EdifactEncodation(text, textOffset, 1, dataDynamic[5], dataOffset, dataSize, 0, -1, dataOffset, sizeFixed);
/* 1259 */       for (i = 1; i < textSize; i++) {
/* 1260 */         int[] tempForMin = new int[6];
/* 1261 */         for (int currEnc = 0; currEnc < 6; currEnc++) {
/* 1262 */           byte[][] dataDynamicInner = new byte[6][data.length];
/* 1263 */           for (int prevEnc = 0; prevEnc < 6; prevEnc++) {
/* 1264 */             System.arraycopy(dataDynamic[prevEnc], 0, dataDynamicInner[prevEnc], 0, data.length);
/* 1265 */             if (this.f[prevEnc][i - 1] < 0) {
/* 1266 */               tempForMin[prevEnc] = -1;
/*      */             } else {
/* 1268 */               if (currEnc == 0)
/* 1269 */                 tempForMin[prevEnc] = asciiEncodation(text, textOffset + i, 1, dataDynamicInner[prevEnc], this.f[prevEnc][i - 1] + dataOffset, dataSize - this.f[prevEnc][i - 1], i, prevEnc + 1, dataOffset); 
/* 1270 */               if (currEnc == 1)
/* 1271 */                 tempForMin[prevEnc] = C40OrTextEncodation(text, textOffset + i, 1, dataDynamicInner[prevEnc], this.f[prevEnc][i - 1] + dataOffset, dataSize - this.f[prevEnc][i - 1], true, i, prevEnc + 1, dataOffset); 
/* 1272 */               if (currEnc == 2)
/* 1273 */                 tempForMin[prevEnc] = C40OrTextEncodation(text, textOffset + i, 1, dataDynamicInner[prevEnc], this.f[prevEnc][i - 1] + dataOffset, dataSize - this.f[prevEnc][i - 1], false, i, prevEnc + 1, dataOffset); 
/* 1274 */               if (currEnc == 3)
/* 1275 */                 tempForMin[prevEnc] = b256Encodation(text, textOffset + i, 1, dataDynamicInner[prevEnc], this.f[prevEnc][i - 1] + dataOffset, dataSize - this.f[prevEnc][i - 1], i, prevEnc + 1, dataOffset); 
/* 1276 */               if (currEnc == 4)
/* 1277 */                 tempForMin[prevEnc] = X12Encodation(text, textOffset + i, 1, dataDynamicInner[prevEnc], this.f[prevEnc][i - 1] + dataOffset, dataSize - this.f[prevEnc][i - 1], i, prevEnc + 1, dataOffset); 
/* 1278 */               if (currEnc == 5)
/* 1279 */                 tempForMin[prevEnc] = EdifactEncodation(text, textOffset + i, 1, dataDynamicInner[prevEnc], this.f[prevEnc][i - 1] + dataOffset, dataSize - this.f[prevEnc][i - 1], i, prevEnc + 1, dataOffset, sizeFixed); 
/*      */             } 
/*      */           } 
/* 1282 */           solveFAndSwitchMode(tempForMin, currEnc, i);
/* 1283 */           if (this.switchMode[currEnc][i] != 0)
/* 1284 */             System.arraycopy(dataDynamicInner[this.switchMode[currEnc][i] - 1], 0, dataDynamic[currEnc], 0, data.length); 
/*      */         } 
/*      */       } 
/* 1287 */       int e = minValueInColumn(this.f, textSize - 1);
/* 1288 */       if (e > dataSize || e < 0)
/* 1289 */         return -1; 
/* 1290 */       int bestDataDynamicResultIndex = valuePositionInColumn(this.f, textSize - 1, e);
/* 1291 */       System.arraycopy(dataDynamic[bestDataDynamicResultIndex], 0, data, 0, data.length);
/* 1292 */       return e;
/*      */     } 
/* 1294 */     switch (options) {
/*      */       case 1:
/* 1296 */         return asciiEncodation(text, textOffset, textSize, data, dataOffset, dataSize, -1, -1, dataOffset);
/*      */       case 2:
/* 1298 */         return C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, true, -1, -1, dataOffset);
/*      */       case 3:
/* 1300 */         return C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, false, -1, -1, dataOffset);
/*      */       case 4:
/* 1302 */         return b256Encodation(text, textOffset, textSize, data, dataOffset, dataSize, -1, -1, dataOffset);
/*      */       case 5:
/* 1304 */         return X12Encodation(text, textOffset, textSize, data, dataOffset, dataSize, -1, -1, dataOffset);
/*      */       case 6:
/* 1306 */         return EdifactEncodation(text, textOffset, textSize, data, dataOffset, dataSize, -1, -1, dataOffset, sizeFixed);
/*      */       case 7:
/* 1308 */         if (textSize > dataSize)
/* 1309 */           return -1; 
/* 1310 */         System.arraycopy(text, textOffset, data, dataOffset, textSize);
/* 1311 */         return textSize;
/*      */     } 
/* 1313 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getNumber(byte[] text, int ptrIn, int n) {
/* 1318 */     int v = 0;
/* 1319 */     for (int j = 0; j < n; j++) {
/* 1320 */       int c = text[ptrIn++] & 0xFF;
/* 1321 */       if (c < 48 || c > 57)
/* 1322 */         return -1; 
/* 1323 */       v = v * 10 + c - 48;
/*      */     } 
/* 1325 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   private int processExtensions(byte[] text, int textOffset, int textSize, byte[] data) {
/* 1330 */     if ((this.options & 0x20) == 0)
/* 1331 */       return 0; 
/* 1332 */     int order = 0;
/* 1333 */     int ptrIn = 0;
/* 1334 */     int ptrOut = 0;
/* 1335 */     while (ptrIn < textSize) {
/* 1336 */       int eci, fn, ft, fi; if (order > 20)
/* 1337 */         return -1; 
/* 1338 */       int c = text[textOffset + ptrIn++] & 0xFF;
/* 1339 */       order++;
/* 1340 */       switch (c) {
/*      */         case 46:
/* 1342 */           this.extOut = ptrIn;
/* 1343 */           return ptrOut;
/*      */         case 101:
/* 1345 */           if (ptrIn + 6 > textSize)
/* 1346 */             return -1; 
/* 1347 */           eci = getNumber(text, textOffset + ptrIn, 6);
/* 1348 */           if (eci < 0)
/* 1349 */             return -1; 
/* 1350 */           ptrIn += 6;
/* 1351 */           data[ptrOut++] = -15;
/* 1352 */           if (eci < 127) {
/* 1353 */             data[ptrOut++] = (byte)(eci + 1); continue;
/* 1354 */           }  if (eci < 16383) {
/* 1355 */             data[ptrOut++] = (byte)((eci - 127) / 254 + 128);
/* 1356 */             data[ptrOut++] = (byte)((eci - 127) % 254 + 1); continue;
/*      */           } 
/* 1358 */           data[ptrOut++] = (byte)((eci - 16383) / 64516 + 192);
/* 1359 */           data[ptrOut++] = (byte)((eci - 16383) / 254 % 254 + 1);
/* 1360 */           data[ptrOut++] = (byte)((eci - 16383) % 254 + 1);
/*      */ 
/*      */         
/*      */         case 115:
/* 1364 */           if (order != 1)
/* 1365 */             return -1; 
/* 1366 */           if (ptrIn + 9 > textSize)
/* 1367 */             return -1; 
/* 1368 */           fn = getNumber(text, textOffset + ptrIn, 2);
/* 1369 */           if (fn <= 0 || fn > 16)
/* 1370 */             return -1; 
/* 1371 */           ptrIn += 2;
/* 1372 */           ft = getNumber(text, textOffset + ptrIn, 2);
/* 1373 */           if (ft <= 1 || ft > 16)
/* 1374 */             return -1; 
/* 1375 */           ptrIn += 2;
/* 1376 */           fi = getNumber(text, textOffset + ptrIn, 5);
/* 1377 */           if (fi < 0 || fn >= 64516)
/* 1378 */             return -1; 
/* 1379 */           ptrIn += 5;
/* 1380 */           data[ptrOut++] = -23;
/* 1381 */           data[ptrOut++] = (byte)(fn - 1 << 4 | 17 - ft);
/* 1382 */           data[ptrOut++] = (byte)(fi / 254 + 1);
/* 1383 */           data[ptrOut++] = (byte)(fi % 254 + 1);
/*      */         
/*      */         case 112:
/* 1386 */           if (order != 1)
/* 1387 */             return -1; 
/* 1388 */           data[ptrOut++] = -22;
/*      */         
/*      */         case 109:
/* 1391 */           if (order != 1)
/* 1392 */             return -1; 
/* 1393 */           if (ptrIn + 1 > textSize)
/* 1394 */             return -1; 
/* 1395 */           c = text[textOffset + ptrIn++] & 0xFF;
/* 1396 */           if (c != 53)
/* 1397 */             return -1; 
/* 1398 */           data[ptrOut++] = -22;
/* 1399 */           data[ptrOut++] = -20;
/*      */         
/*      */         case 102:
/* 1402 */           if (order != 1 && (order != 2 || (text[textOffset] != 115 && text[textOffset] != 109)))
/* 1403 */             return -1; 
/* 1404 */           data[ptrOut++] = -24;
/*      */       } 
/*      */     } 
/* 1407 */     return -1;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/BarcodeDataMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */