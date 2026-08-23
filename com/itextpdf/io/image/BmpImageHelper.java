/*      */ package com.itextpdf.io.image;
/*      */ 
/*      */ import com.itextpdf.io.IOException;
/*      */ import com.itextpdf.io.font.PdfEncodings;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
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
/*      */ final class BmpImageHelper
/*      */ {
/*      */   private static final int VERSION_2_1_BIT = 0;
/*      */   private static final int VERSION_2_4_BIT = 1;
/*      */   private static final int VERSION_2_8_BIT = 2;
/*      */   private static final int VERSION_2_24_BIT = 3;
/*      */   private static final int VERSION_3_1_BIT = 4;
/*      */   private static final int VERSION_3_4_BIT = 5;
/*      */   private static final int VERSION_3_8_BIT = 6;
/*      */   private static final int VERSION_3_24_BIT = 7;
/*      */   private static final int VERSION_3_NT_16_BIT = 8;
/*      */   private static final int VERSION_3_NT_32_BIT = 9;
/*      */   private static final int VERSION_4_1_BIT = 10;
/*      */   private static final int VERSION_4_4_BIT = 11;
/*      */   private static final int VERSION_4_8_BIT = 12;
/*      */   private static final int VERSION_4_16_BIT = 13;
/*      */   private static final int VERSION_4_24_BIT = 14;
/*      */   private static final int VERSION_4_32_BIT = 15;
/*      */   private static final int LCS_CALIBRATED_RGB = 0;
/*      */   private static final int LCS_SRGB = 1;
/*      */   private static final int LCS_CMYK = 2;
/*      */   private static final int BI_RGB = 0;
/*      */   private static final int BI_RLE8 = 1;
/*      */   private static final int BI_RLE4 = 2;
/*      */   private static final int BI_BITFIELDS = 3;
/*      */   
/*      */   private static class BmpParameters
/*      */   {
/*      */     BmpImageData image;
/*      */     int width;
/*      */     int height;
/*      */     Map<String, Object> additional;
/*      */     InputStream inputStream;
/*      */     long bitmapFileSize;
/*      */     long bitmapOffset;
/*      */     long compression;
/*      */     long imageSize;
/*      */     byte[] palette;
/*      */     int imageType;
/*      */     int numBands;
/*      */     boolean isBottomUp;
/*      */     int bitsPerPixel;
/*      */     int redMask;
/*      */     int greenMask;
/*      */     int blueMask;
/*      */     int alphaMask;
/*      */     Map<String, Object> properties;
/*      */     long xPelsPerMeter;
/*      */     long yPelsPerMeter;
/*      */     
/*      */     public BmpParameters(BmpImageData image) {
/*   78 */       this.properties = new HashMap<>();
/*      */       this.image = image;
/*      */     }
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
/*      */   public static void processImage(ImageData image) {
/*      */     BmpParameters bmp;
/*  121 */     if (image.getOriginalType() != ImageType.BMP) {
/*  122 */       throw new IllegalArgumentException("BMP image expected");
/*      */     }
/*      */     
/*      */     try {
/*  126 */       if (image.getData() == null) {
/*  127 */         image.loadData();
/*      */       }
/*  129 */       InputStream bmpStream = new ByteArrayInputStream(image.getData());
/*  130 */       image.imageSize = (image.getData()).length;
/*  131 */       bmp = new BmpParameters((BmpImageData)image);
/*  132 */       process(bmp, bmpStream);
/*  133 */       if (getImage(bmp)) {
/*  134 */         image.setWidth(bmp.width);
/*  135 */         image.setHeight(bmp.height);
/*  136 */         image.setDpi((int)(bmp.xPelsPerMeter * 0.0254D + 0.5D), (int)(bmp.yPelsPerMeter * 0.0254D + 0.5D));
/*      */       } 
/*  138 */     } catch (IOException e) {
/*  139 */       throw new IOException("Bmp image exception.", e);
/*      */     } 
/*  141 */     RawImageHelper.updateImageAttributes(bmp.image, bmp.additional);
/*      */   }
/*      */   
/*      */   private static void process(BmpParameters bmp, InputStream stream) throws IOException {
/*  145 */     bmp.inputStream = stream;
/*  146 */     if (!bmp.image.isNoHeader()) {
/*      */       
/*  148 */       if (readUnsignedByte(bmp.inputStream) != 66 || 
/*  149 */         readUnsignedByte(bmp.inputStream) != 77) {
/*  150 */         throw new IOException("Invalid magic value for bmp file. Must be 'BM'");
/*      */       }
/*      */ 
/*      */       
/*  154 */       bmp.bitmapFileSize = readDWord(bmp.inputStream);
/*      */ 
/*      */       
/*  157 */       readWord(bmp.inputStream);
/*  158 */       readWord(bmp.inputStream);
/*      */ 
/*      */       
/*  161 */       bmp.bitmapOffset = readDWord(bmp.inputStream);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  166 */     long size = readDWord(bmp.inputStream);
/*      */     
/*  168 */     if (size == 12L) {
/*  169 */       bmp.width = readWord(bmp.inputStream);
/*  170 */       bmp.height = readWord(bmp.inputStream);
/*      */     } else {
/*  172 */       bmp.width = readLong(bmp.inputStream);
/*  173 */       bmp.height = readLong(bmp.inputStream);
/*      */     } 
/*      */     
/*  176 */     int planes = readWord(bmp.inputStream);
/*  177 */     bmp.bitsPerPixel = readWord(bmp.inputStream);
/*      */     
/*  179 */     bmp.properties.put("color_planes", Integer.valueOf(planes));
/*  180 */     bmp.properties.put("bits_per_pixel", Integer.valueOf(bmp.bitsPerPixel));
/*      */ 
/*      */ 
/*      */     
/*  184 */     bmp.numBands = 3;
/*  185 */     if (bmp.bitmapOffset == 0L)
/*  186 */       bmp.bitmapOffset = size; 
/*  187 */     if (size == 12L) {
/*      */       
/*  189 */       bmp.properties.put("bmp_version", "BMP v. 2.x");
/*      */ 
/*      */       
/*  192 */       if (bmp.bitsPerPixel == 1) {
/*  193 */         bmp.imageType = 0;
/*  194 */       } else if (bmp.bitsPerPixel == 4) {
/*  195 */         bmp.imageType = 1;
/*  196 */       } else if (bmp.bitsPerPixel == 8) {
/*  197 */         bmp.imageType = 2;
/*  198 */       } else if (bmp.bitsPerPixel == 24) {
/*  199 */         bmp.imageType = 3;
/*      */       } 
/*      */ 
/*      */       
/*  203 */       int numberOfEntries = (int)((bmp.bitmapOffset - 14L - size) / 3L);
/*  204 */       int sizeOfPalette = numberOfEntries * 3;
/*  205 */       if (bmp.bitmapOffset == size) {
/*  206 */         switch (bmp.imageType) {
/*      */           case 0:
/*  208 */             sizeOfPalette = 6;
/*      */             break;
/*      */           case 1:
/*  211 */             sizeOfPalette = 48;
/*      */             break;
/*      */           case 2:
/*  214 */             sizeOfPalette = 768;
/*      */             break;
/*      */           case 3:
/*  217 */             sizeOfPalette = 0;
/*      */             break;
/*      */         } 
/*  220 */         bmp.bitmapOffset = size + sizeOfPalette;
/*      */       } 
/*  222 */       readPalette(sizeOfPalette, bmp);
/*      */     } else {
/*  224 */       bmp.compression = readDWord(bmp.inputStream);
/*  225 */       bmp.imageSize = readDWord(bmp.inputStream);
/*  226 */       bmp.xPelsPerMeter = readLong(bmp.inputStream);
/*  227 */       bmp.yPelsPerMeter = readLong(bmp.inputStream);
/*  228 */       long colorsUsed = readDWord(bmp.inputStream);
/*  229 */       long colorsImportant = readDWord(bmp.inputStream);
/*      */       
/*  231 */       switch ((int)bmp.compression) {
/*      */         case 0:
/*  233 */           bmp.properties.put("compression", "BI_RGB");
/*      */           break;
/*      */         
/*      */         case 1:
/*  237 */           bmp.properties.put("compression", "BI_RLE8");
/*      */           break;
/*      */         
/*      */         case 2:
/*  241 */           bmp.properties.put("compression", "BI_RLE4");
/*      */           break;
/*      */         
/*      */         case 3:
/*  245 */           bmp.properties.put("compression", "BI_BITFIELDS");
/*      */           break;
/*      */       } 
/*      */       
/*  249 */       bmp.properties.put("x_pixels_per_meter", Long.valueOf(bmp.xPelsPerMeter));
/*  250 */       bmp.properties.put("y_pixels_per_meter", Long.valueOf(bmp.yPelsPerMeter));
/*  251 */       bmp.properties.put("colors_used", Long.valueOf(colorsUsed));
/*  252 */       bmp.properties.put("colors_important", Long.valueOf(colorsImportant));
/*      */       
/*  254 */       if (size == 40L || size == 52L || size == 56L) {
/*      */         int sizeOfPalette;
/*      */         int numberOfEntries;
/*  257 */         switch ((int)bmp.compression) {
/*      */ 
/*      */           
/*      */           case 0:
/*      */           case 1:
/*      */           case 2:
/*  263 */             if (bmp.bitsPerPixel == 1) {
/*  264 */               bmp.imageType = 4;
/*  265 */             } else if (bmp.bitsPerPixel == 4) {
/*  266 */               bmp.imageType = 5;
/*  267 */             } else if (bmp.bitsPerPixel == 8) {
/*  268 */               bmp.imageType = 6;
/*  269 */             } else if (bmp.bitsPerPixel == 24) {
/*  270 */               bmp.imageType = 7;
/*  271 */             } else if (bmp.bitsPerPixel == 16) {
/*  272 */               bmp.imageType = 8;
/*  273 */               bmp.redMask = 31744;
/*  274 */               bmp.greenMask = 992;
/*  275 */               bmp.blueMask = 31;
/*  276 */               bmp.properties.put("red_mask", Integer.valueOf(bmp.redMask));
/*  277 */               bmp.properties.put("green_mask", Integer.valueOf(bmp.greenMask));
/*  278 */               bmp.properties.put("blue_mask", Integer.valueOf(bmp.blueMask));
/*  279 */             } else if (bmp.bitsPerPixel == 32) {
/*  280 */               bmp.imageType = 9;
/*  281 */               bmp.redMask = 16711680;
/*  282 */               bmp.greenMask = 65280;
/*  283 */               bmp.blueMask = 255;
/*  284 */               bmp.properties.put("red_mask", Integer.valueOf(bmp.redMask));
/*  285 */               bmp.properties.put("green_mask", Integer.valueOf(bmp.greenMask));
/*  286 */               bmp.properties.put("blue_mask", Integer.valueOf(bmp.blueMask));
/*      */             } 
/*      */ 
/*      */             
/*  290 */             if (size >= 52L) {
/*  291 */               bmp.redMask = (int)readDWord(bmp.inputStream);
/*  292 */               bmp.greenMask = (int)readDWord(bmp.inputStream);
/*  293 */               bmp.blueMask = (int)readDWord(bmp.inputStream);
/*  294 */               bmp.properties.put("red_mask", Integer.valueOf(bmp.redMask));
/*  295 */               bmp.properties.put("green_mask", Integer.valueOf(bmp.greenMask));
/*  296 */               bmp.properties.put("blue_mask", Integer.valueOf(bmp.blueMask));
/*      */             } 
/*      */             
/*  299 */             if (size == 56L) {
/*  300 */               bmp.alphaMask = (int)readDWord(bmp.inputStream);
/*  301 */               bmp.properties.put("alpha_mask", Integer.valueOf(bmp.alphaMask));
/*      */             } 
/*      */ 
/*      */             
/*  305 */             numberOfEntries = (int)((bmp.bitmapOffset - 14L - size) / 4L);
/*  306 */             sizeOfPalette = numberOfEntries * 4;
/*  307 */             if (bmp.bitmapOffset == size) {
/*  308 */               switch (bmp.imageType) {
/*      */                 case 4:
/*  310 */                   sizeOfPalette = (int)((colorsUsed == 0L) ? 2L : colorsUsed) * 4;
/*      */                   break;
/*      */                 case 5:
/*  313 */                   sizeOfPalette = (int)((colorsUsed == 0L) ? 16L : colorsUsed) * 4;
/*      */                   break;
/*      */                 case 6:
/*  316 */                   sizeOfPalette = (int)((colorsUsed == 0L) ? 256L : colorsUsed) * 4;
/*      */                   break;
/*      */                 default:
/*  319 */                   sizeOfPalette = 0;
/*      */                   break;
/*      */               } 
/*  322 */               bmp.bitmapOffset = size + sizeOfPalette;
/*      */             } 
/*  324 */             readPalette(sizeOfPalette, bmp);
/*      */             
/*  326 */             bmp.properties.put("bmp_version", "BMP v. 3.x");
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/*  331 */             if (bmp.bitsPerPixel == 16) {
/*  332 */               bmp.imageType = 8;
/*  333 */             } else if (bmp.bitsPerPixel == 32) {
/*  334 */               bmp.imageType = 9;
/*      */             } 
/*      */ 
/*      */             
/*  338 */             bmp.redMask = (int)readDWord(bmp.inputStream);
/*  339 */             bmp.greenMask = (int)readDWord(bmp.inputStream);
/*  340 */             bmp.blueMask = (int)readDWord(bmp.inputStream);
/*      */ 
/*      */             
/*  343 */             if (size == 56L) {
/*  344 */               bmp.alphaMask = (int)readDWord(bmp.inputStream);
/*  345 */               bmp.properties.put("alpha_mask", Integer.valueOf(bmp.alphaMask));
/*      */             } 
/*      */             
/*  348 */             bmp.properties.put("red_mask", Integer.valueOf(bmp.redMask));
/*  349 */             bmp.properties.put("green_mask", Integer.valueOf(bmp.greenMask));
/*  350 */             bmp.properties.put("blue_mask", Integer.valueOf(bmp.blueMask));
/*      */             
/*  352 */             if (colorsUsed != 0L) {
/*      */               
/*  354 */               sizeOfPalette = (int)colorsUsed * 4;
/*  355 */               readPalette(sizeOfPalette, bmp);
/*      */             } 
/*      */             
/*  358 */             bmp.properties.put("bmp_version", "BMP v. 3.x NT");
/*      */             break;
/*      */           
/*      */           default:
/*  362 */             throw new IOException("Invalid BMP file compression.");
/*      */         } 
/*  364 */       } else if (size == 108L) {
/*      */ 
/*      */         
/*  367 */         bmp.properties.put("bmp_version", "BMP v. 4.x");
/*      */ 
/*      */         
/*  370 */         bmp.redMask = (int)readDWord(bmp.inputStream);
/*  371 */         bmp.greenMask = (int)readDWord(bmp.inputStream);
/*  372 */         bmp.blueMask = (int)readDWord(bmp.inputStream);
/*      */         
/*  374 */         bmp.alphaMask = (int)readDWord(bmp.inputStream);
/*  375 */         long csType = readDWord(bmp.inputStream);
/*  376 */         int redX = readLong(bmp.inputStream);
/*  377 */         int redY = readLong(bmp.inputStream);
/*  378 */         int redZ = readLong(bmp.inputStream);
/*  379 */         int greenX = readLong(bmp.inputStream);
/*  380 */         int greenY = readLong(bmp.inputStream);
/*  381 */         int greenZ = readLong(bmp.inputStream);
/*  382 */         int blueX = readLong(bmp.inputStream);
/*  383 */         int blueY = readLong(bmp.inputStream);
/*  384 */         int blueZ = readLong(bmp.inputStream);
/*  385 */         long gammaRed = readDWord(bmp.inputStream);
/*  386 */         long gammaGreen = readDWord(bmp.inputStream);
/*  387 */         long gammaBlue = readDWord(bmp.inputStream);
/*      */         
/*  389 */         if (bmp.bitsPerPixel == 1) {
/*  390 */           bmp.imageType = 10;
/*  391 */         } else if (bmp.bitsPerPixel == 4) {
/*  392 */           bmp.imageType = 11;
/*  393 */         } else if (bmp.bitsPerPixel == 8) {
/*  394 */           bmp.imageType = 12;
/*  395 */         } else if (bmp.bitsPerPixel == 16) {
/*  396 */           bmp.imageType = 13;
/*  397 */           if ((int)bmp.compression == 0) {
/*  398 */             bmp.redMask = 31744;
/*  399 */             bmp.greenMask = 992;
/*  400 */             bmp.blueMask = 31;
/*      */           } 
/*  402 */         } else if (bmp.bitsPerPixel == 24) {
/*  403 */           bmp.imageType = 14;
/*  404 */         } else if (bmp.bitsPerPixel == 32) {
/*  405 */           bmp.imageType = 15;
/*  406 */           if ((int)bmp.compression == 0) {
/*  407 */             bmp.redMask = 16711680;
/*  408 */             bmp.greenMask = 65280;
/*  409 */             bmp.blueMask = 255;
/*      */           } 
/*      */         } 
/*      */         
/*  413 */         bmp.properties.put("red_mask", Integer.valueOf(bmp.redMask));
/*  414 */         bmp.properties.put("green_mask", Integer.valueOf(bmp.greenMask));
/*  415 */         bmp.properties.put("blue_mask", Integer.valueOf(bmp.blueMask));
/*  416 */         bmp.properties.put("alpha_mask", Integer.valueOf(bmp.alphaMask));
/*      */ 
/*      */         
/*  419 */         int numberOfEntries = (int)((bmp.bitmapOffset - 14L - size) / 4L);
/*  420 */         int sizeOfPalette = numberOfEntries * 4;
/*  421 */         if (bmp.bitmapOffset == size) {
/*  422 */           switch (bmp.imageType) {
/*      */             case 10:
/*  424 */               sizeOfPalette = (int)((colorsUsed == 0L) ? 2L : colorsUsed) * 4;
/*      */               break;
/*      */             case 11:
/*  427 */               sizeOfPalette = (int)((colorsUsed == 0L) ? 16L : colorsUsed) * 4;
/*      */               break;
/*      */             case 12:
/*  430 */               sizeOfPalette = (int)((colorsUsed == 0L) ? 256L : colorsUsed) * 4;
/*      */               break;
/*      */             default:
/*  433 */               sizeOfPalette = 0;
/*      */               break;
/*      */           } 
/*  436 */           bmp.bitmapOffset = size + sizeOfPalette;
/*      */         } 
/*  438 */         readPalette(sizeOfPalette, bmp);
/*      */         
/*  440 */         switch ((int)csType) {
/*      */           
/*      */           case 0:
/*  443 */             bmp.properties.put("color_space", "LCS_CALIBRATED_RGB");
/*  444 */             bmp.properties.put("redX", Integer.valueOf(redX));
/*  445 */             bmp.properties.put("redY", Integer.valueOf(redY));
/*  446 */             bmp.properties.put("redZ", Integer.valueOf(redZ));
/*  447 */             bmp.properties.put("greenX", Integer.valueOf(greenX));
/*  448 */             bmp.properties.put("greenY", Integer.valueOf(greenY));
/*  449 */             bmp.properties.put("greenZ", Integer.valueOf(greenZ));
/*  450 */             bmp.properties.put("blueX", Integer.valueOf(blueX));
/*  451 */             bmp.properties.put("blueY", Integer.valueOf(blueY));
/*  452 */             bmp.properties.put("blueZ", Integer.valueOf(blueZ));
/*  453 */             bmp.properties.put("gamma_red", Long.valueOf(gammaRed));
/*  454 */             bmp.properties.put("gamma_green", Long.valueOf(gammaGreen));
/*  455 */             bmp.properties.put("gamma_blue", Long.valueOf(gammaBlue));
/*  456 */             throw new RuntimeException("Not implemented yet.");
/*      */ 
/*      */           
/*      */           case 1:
/*  460 */             bmp.properties.put("color_space", "LCS_sRGB");
/*      */             break;
/*      */           
/*      */           case 2:
/*  464 */             bmp.properties.put("color_space", "LCS_CMYK");
/*      */             
/*  466 */             throw new RuntimeException("Not implemented yet.");
/*      */         } 
/*      */       } else {
/*  469 */         bmp.properties.put("bmp_version", "BMP v. 5.x");
/*  470 */         throw new RuntimeException("Not implemented yet.");
/*      */       } 
/*      */     } 
/*      */     
/*  474 */     if (bmp.height > 0) {
/*      */       
/*  476 */       bmp.isBottomUp = true;
/*      */     } else {
/*      */       
/*  479 */       bmp.isBottomUp = false;
/*  480 */       bmp.height = Math.abs(bmp.height);
/*      */     } 
/*      */     
/*  483 */     if (bmp.bitsPerPixel == 1 || bmp.bitsPerPixel == 4 || bmp.bitsPerPixel == 8) {
/*  484 */       bmp.numBands = 1;
/*      */ 
/*      */ 
/*      */       
/*  488 */       if (bmp.imageType == 0 || bmp.imageType == 1 || bmp.imageType == 2) {
/*      */ 
/*      */ 
/*      */         
/*  492 */         int sizep = bmp.palette.length / 3;
/*      */         
/*  494 */         if (sizep > 256) {
/*  495 */           sizep = 256;
/*      */         }
/*      */ 
/*      */         
/*  499 */         byte[] r = new byte[sizep];
/*  500 */         byte[] g = new byte[sizep];
/*  501 */         byte[] b = new byte[sizep];
/*  502 */         for (int i = 0; i < sizep; i++) {
/*  503 */           int off = 3 * i;
/*  504 */           b[i] = bmp.palette[off];
/*  505 */           g[i] = bmp.palette[off + 1];
/*  506 */           r[i] = bmp.palette[off + 2];
/*      */         } 
/*      */       } else {
/*  509 */         int sizep = bmp.palette.length / 4;
/*      */         
/*  511 */         if (sizep > 256) {
/*  512 */           sizep = 256;
/*      */         }
/*      */ 
/*      */         
/*  516 */         byte[] r = new byte[sizep];
/*  517 */         byte[] g = new byte[sizep];
/*  518 */         byte[] b = new byte[sizep];
/*  519 */         for (int i = 0; i < sizep; i++) {
/*  520 */           int off = 4 * i;
/*  521 */           b[i] = bmp.palette[off];
/*  522 */           g[i] = bmp.palette[off + 1];
/*  523 */           r[i] = bmp.palette[off + 2];
/*      */         }
/*      */       
/*      */       } 
/*  527 */     } else if (bmp.bitsPerPixel == 16) {
/*  528 */       bmp.numBands = 3;
/*  529 */     } else if (bmp.bitsPerPixel == 32) {
/*  530 */       bmp.numBands = (bmp.alphaMask == 0) ? 3 : 4;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  535 */       bmp.numBands = 3;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static byte[] getPalette(int group, BmpParameters bmp) {
/*  540 */     if (bmp.palette == null)
/*  541 */       return null; 
/*  542 */     byte[] np = new byte[bmp.palette.length / group * 3];
/*  543 */     int e = bmp.palette.length / group;
/*  544 */     for (int k = 0; k < e; k++) {
/*  545 */       int src = k * group;
/*  546 */       int dest = k * 3;
/*  547 */       np[dest + 2] = bmp.palette[src++];
/*  548 */       np[dest + 1] = bmp.palette[src++];
/*  549 */       np[dest] = bmp.palette[src];
/*      */     } 
/*  551 */     return np;
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
/*      */   private static boolean getImage(BmpParameters bmp) throws IOException {
/*      */     byte[] bdata;
/*  565 */     switch (bmp.imageType) {
/*      */       
/*      */       case 0:
/*  568 */         read1Bit(3, bmp);
/*  569 */         return true;
/*      */       
/*      */       case 1:
/*  572 */         read4Bit(3, bmp);
/*  573 */         return true;
/*      */       
/*      */       case 2:
/*  576 */         read8Bit(3, bmp);
/*  577 */         return true;
/*      */       
/*      */       case 3:
/*  580 */         bdata = new byte[bmp.width * bmp.height * 3];
/*  581 */         read24Bit(bdata, bmp);
/*  582 */         RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 3, 8, bdata);
/*  583 */         return true;
/*      */       
/*      */       case 4:
/*  586 */         read1Bit(4, bmp);
/*  587 */         return true;
/*      */       case 5:
/*  589 */         switch ((int)bmp.compression)
/*      */         { case 0:
/*  591 */             read4Bit(4, bmp);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  599 */             return true;case 2: readRLE4(bmp); return true; }  throw new IOException("Invalid BMP file compression.");
/*      */       case 6:
/*  601 */         switch ((int)bmp.compression) {
/*      */           case 0:
/*  603 */             read8Bit(4, bmp);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  611 */             return true;case 1: readRLE8(bmp); return true;
/*      */         }  throw new IOException("Invalid BMP file compression.");
/*      */       case 7:
/*  614 */         bdata = new byte[bmp.width * bmp.height * 3];
/*  615 */         read24Bit(bdata, bmp);
/*  616 */         RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 3, 8, bdata);
/*  617 */         return true;
/*      */       case 8:
/*  619 */         read1632Bit(false, bmp);
/*  620 */         return true;
/*      */       case 9:
/*  622 */         read1632Bit(true, bmp);
/*  623 */         return true;
/*      */       case 10:
/*  625 */         read1Bit(4, bmp);
/*  626 */         return true;
/*      */       case 11:
/*  628 */         switch ((int)bmp.compression)
/*      */         { case 0:
/*  630 */             read4Bit(4, bmp);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  638 */             return true;case 2: readRLE4(bmp); return true; }  throw new IOException("Invalid BMP file compression.");
/*      */       case 12:
/*  640 */         switch ((int)bmp.compression)
/*      */         { case 0:
/*  642 */             read8Bit(4, bmp);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  650 */             return true;case 1: readRLE8(bmp); return true; }  throw new IOException("Invalid BMP file compression.");
/*      */       case 13:
/*  652 */         read1632Bit(false, bmp);
/*  653 */         return true;
/*      */       case 14:
/*  655 */         bdata = new byte[bmp.width * bmp.height * 3];
/*  656 */         read24Bit(bdata, bmp);
/*  657 */         RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 3, 8, bdata);
/*  658 */         return true;
/*      */       case 15:
/*  660 */         read1632Bit(true, bmp);
/*  661 */         return true;
/*      */     } 
/*  663 */     return false;
/*      */   }
/*      */   
/*      */   private static void indexedModel(byte[] bdata, int bpc, int paletteEntries, BmpParameters bmp) {
/*  667 */     RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 1, bpc, bdata);
/*  668 */     Object[] colorSpace = new Object[4];
/*  669 */     colorSpace[0] = "/Indexed";
/*  670 */     colorSpace[1] = "/DeviceRGB";
/*  671 */     byte[] np = getPalette(paletteEntries, bmp);
/*  672 */     int len = np.length;
/*  673 */     colorSpace[2] = Integer.valueOf(len / 3 - 1);
/*  674 */     colorSpace[3] = PdfEncodings.convertToString(np, null);
/*  675 */     bmp.additional = new HashMap<>();
/*  676 */     bmp.additional.put("ColorSpace", colorSpace);
/*      */   }
/*      */   
/*      */   private static void readPalette(int sizeOfPalette, BmpParameters bmp) throws IOException {
/*  680 */     if (sizeOfPalette == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  684 */     bmp.palette = new byte[sizeOfPalette];
/*  685 */     int bytesRead = 0;
/*  686 */     while (bytesRead < sizeOfPalette) {
/*  687 */       int r = bmp.inputStream.read(bmp.palette, bytesRead, sizeOfPalette - bytesRead);
/*  688 */       if (r < 0) {
/*  689 */         throw new IOException("Incomplete palette.");
/*      */       }
/*  691 */       bytesRead += r;
/*      */     } 
/*  693 */     bmp.properties.put("palette", bmp.palette);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void read1Bit(int paletteEntries, BmpParameters bmp) throws IOException {
/*  698 */     byte[] bdata = new byte[(bmp.width + 7) / 8 * bmp.height];
/*  699 */     int padding = 0;
/*  700 */     int bytesPerScanline = (int)Math.ceil(bmp.width / 8.0D);
/*      */     
/*  702 */     int remainder = bytesPerScanline % 4;
/*  703 */     if (remainder != 0) {
/*  704 */       padding = 4 - remainder;
/*      */     }
/*      */     
/*  707 */     int imSize = (bytesPerScanline + padding) * bmp.height;
/*      */ 
/*      */     
/*  710 */     byte[] values = new byte[imSize];
/*  711 */     int bytesRead = 0;
/*  712 */     while (bytesRead < imSize) {
/*  713 */       bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/*      */     
/*  717 */     if (bmp.isBottomUp) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  722 */       for (int i = 0; i < bmp.height; i++) {
/*  723 */         System.arraycopy(values, imSize - (i + 1) * (bytesPerScanline + padding), bdata, i * bytesPerScanline, bytesPerScanline);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  730 */       for (int i = 0; i < bmp.height; i++) {
/*  731 */         System.arraycopy(values, i * (bytesPerScanline + padding), bdata, i * bytesPerScanline, bytesPerScanline);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  738 */     indexedModel(bdata, 1, paletteEntries, bmp);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void read4Bit(int paletteEntries, BmpParameters bmp) throws IOException {
/*  743 */     byte[] bdata = new byte[(bmp.width + 1) / 2 * bmp.height];
/*      */ 
/*      */     
/*  746 */     int padding = 0;
/*      */     
/*  748 */     int bytesPerScanline = (int)Math.ceil(bmp.width / 2.0D);
/*  749 */     int remainder = bytesPerScanline % 4;
/*  750 */     if (remainder != 0) {
/*  751 */       padding = 4 - remainder;
/*      */     }
/*      */     
/*  754 */     int imSize = (bytesPerScanline + padding) * bmp.height;
/*      */ 
/*      */     
/*  757 */     byte[] values = new byte[imSize];
/*  758 */     int bytesRead = 0;
/*  759 */     while (bytesRead < imSize) {
/*  760 */       bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/*      */     
/*  764 */     if (bmp.isBottomUp) {
/*      */ 
/*      */ 
/*      */       
/*  768 */       for (int i = 0; i < bmp.height; i++) {
/*  769 */         System.arraycopy(values, imSize - (i + 1) * (bytesPerScanline + padding), bdata, i * bytesPerScanline, bytesPerScanline);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  776 */       for (int i = 0; i < bmp.height; i++) {
/*  777 */         System.arraycopy(values, i * (bytesPerScanline + padding), bdata, i * bytesPerScanline, bytesPerScanline);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  784 */     indexedModel(bdata, 4, paletteEntries, bmp);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void read8Bit(int paletteEntries, BmpParameters bmp) throws IOException {
/*  789 */     byte[] bdata = new byte[bmp.width * bmp.height];
/*      */     
/*  791 */     int padding = 0;
/*      */ 
/*      */     
/*  794 */     int bitsPerScanline = bmp.width * 8;
/*  795 */     if (bitsPerScanline % 32 != 0) {
/*  796 */       padding = (bitsPerScanline / 32 + 1) * 32 - bitsPerScanline;
/*  797 */       padding = (int)Math.ceil(padding / 8.0D);
/*      */     } 
/*      */     
/*  800 */     int imSize = (bmp.width + padding) * bmp.height;
/*      */ 
/*      */     
/*  803 */     byte[] values = new byte[imSize];
/*  804 */     int bytesRead = 0;
/*  805 */     while (bytesRead < imSize) {
/*  806 */       bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */     
/*  809 */     if (bmp.isBottomUp) {
/*      */ 
/*      */ 
/*      */       
/*  813 */       for (int i = 0; i < bmp.height; i++) {
/*  814 */         System.arraycopy(values, imSize - (i + 1) * (bmp.width + padding), bdata, i * bmp.width, bmp.width);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  821 */       for (int i = 0; i < bmp.height; i++) {
/*  822 */         System.arraycopy(values, i * (bmp.width + padding), bdata, i * bmp.width, bmp.width);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  829 */     indexedModel(bdata, 8, paletteEntries, bmp);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void read24Bit(byte[] bdata, BmpParameters bmp) throws IOException {
/*  835 */     int padding = 0;
/*      */ 
/*      */     
/*  838 */     int bitsPerScanline = bmp.width * 24;
/*  839 */     if (bitsPerScanline % 32 != 0) {
/*  840 */       padding = (bitsPerScanline / 32 + 1) * 32 - bitsPerScanline;
/*  841 */       padding = (int)Math.ceil(padding / 8.0D);
/*      */     } 
/*      */ 
/*      */     
/*  845 */     int imSize = (bmp.width * 3 + 3) / 4 * 4 * bmp.height;
/*      */     
/*  847 */     byte[] values = new byte[imSize];
/*  848 */     int bytesRead = 0;
/*  849 */     while (bytesRead < imSize) {
/*  850 */       int r = bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */       
/*  852 */       if (r < 0)
/*      */         break; 
/*  854 */       bytesRead += r;
/*      */     } 
/*      */     
/*  857 */     int l = 0;
/*      */     
/*  859 */     if (bmp.isBottomUp) {
/*  860 */       int max = bmp.width * bmp.height * 3 - 1;
/*      */       
/*  862 */       int count = -padding;
/*  863 */       for (int i = 0; i < bmp.height; i++) {
/*  864 */         l = max - (i + 1) * bmp.width * 3 + 1;
/*  865 */         count += padding;
/*  866 */         for (int j = 0; j < bmp.width; j++) {
/*  867 */           bdata[l + 2] = values[count++];
/*  868 */           bdata[l + 1] = values[count++];
/*  869 */           bdata[l] = values[count++];
/*  870 */           l += 3;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  874 */       int count = -padding;
/*  875 */       for (int i = 0; i < bmp.height; i++) {
/*  876 */         count += padding;
/*  877 */         for (int j = 0; j < bmp.width; j++) {
/*  878 */           bdata[l + 2] = values[count++];
/*  879 */           bdata[l + 1] = values[count++];
/*  880 */           bdata[l] = values[count++];
/*  881 */           l += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int findMask(int mask) {
/*  888 */     int k = 0;
/*  889 */     for (; k < 32 && (
/*  890 */       mask & 0x1) != 1; k++)
/*      */     {
/*  892 */       mask >>>= 1;
/*      */     }
/*  894 */     return mask;
/*      */   }
/*      */   
/*      */   private static int findShift(int mask) {
/*  898 */     int k = 0;
/*  899 */     for (; k < 32 && (
/*  900 */       mask & 0x1) != 1; k++)
/*      */     {
/*  902 */       mask >>>= 1;
/*      */     }
/*  904 */     return k;
/*      */   }
/*      */   
/*      */   private static void read1632Bit(boolean is32, BmpParameters bmp) throws IOException {
/*  908 */     int red_mask = findMask(bmp.redMask);
/*  909 */     int red_shift = findShift(bmp.redMask);
/*  910 */     int red_factor = red_mask + 1;
/*  911 */     int green_mask = findMask(bmp.greenMask);
/*  912 */     int green_shift = findShift(bmp.greenMask);
/*  913 */     int green_factor = green_mask + 1;
/*  914 */     int blue_mask = findMask(bmp.blueMask);
/*  915 */     int blue_shift = findShift(bmp.blueMask);
/*  916 */     int blue_factor = blue_mask + 1;
/*  917 */     byte[] bdata = new byte[bmp.width * bmp.height * 3];
/*      */     
/*  919 */     int padding = 0;
/*      */     
/*  921 */     if (!is32) {
/*      */       
/*  923 */       int bitsPerScanline = bmp.width * 16;
/*  924 */       if (bitsPerScanline % 32 != 0) {
/*  925 */         padding = (bitsPerScanline / 32 + 1) * 32 - bitsPerScanline;
/*  926 */         padding = (int)Math.ceil(padding / 8.0D);
/*      */       } 
/*      */     } 
/*      */     
/*  930 */     int imSize = (int)bmp.imageSize;
/*  931 */     if (imSize == 0) {
/*  932 */       imSize = (int)(bmp.bitmapFileSize - bmp.bitmapOffset);
/*      */     }
/*      */     
/*  935 */     int l = 0;
/*      */     
/*  937 */     if (bmp.isBottomUp) {
/*  938 */       for (int i = bmp.height - 1; i >= 0; i--) {
/*  939 */         l = bmp.width * 3 * i;
/*  940 */         for (int j = 0; j < bmp.width; j++) {
/*  941 */           int v; if (is32) {
/*  942 */             v = (int)readDWord(bmp.inputStream);
/*      */           } else {
/*  944 */             v = readWord(bmp.inputStream);
/*  945 */           }  bdata[l++] = (byte)((v >>> red_shift & red_mask) * 256 / red_factor);
/*  946 */           bdata[l++] = (byte)((v >>> green_shift & green_mask) * 256 / green_factor);
/*  947 */           bdata[l++] = (byte)((v >>> blue_shift & blue_mask) * 256 / blue_factor);
/*      */         } 
/*  949 */         for (int m = 0; m < padding; m++) {
/*  950 */           bmp.inputStream.read();
/*      */         }
/*      */       } 
/*      */     } else {
/*  954 */       for (int i = 0; i < bmp.height; i++) {
/*  955 */         for (int j = 0; j < bmp.width; j++) {
/*  956 */           int v; if (is32) {
/*  957 */             v = (int)readDWord(bmp.inputStream);
/*      */           } else {
/*  959 */             v = readWord(bmp.inputStream);
/*  960 */           }  bdata[l++] = (byte)((v >>> red_shift & red_mask) * 256 / red_factor);
/*  961 */           bdata[l++] = (byte)((v >>> green_shift & green_mask) * 256 / green_factor);
/*  962 */           bdata[l++] = (byte)((v >>> blue_shift & blue_mask) * 256 / blue_factor);
/*      */         } 
/*  964 */         for (int m = 0; m < padding; m++) {
/*  965 */           bmp.inputStream.read();
/*      */         }
/*      */       } 
/*      */     } 
/*  969 */     RawImageHelper.updateRawImageParameters(bmp.image, bmp.width, bmp.height, 3, 8, bdata);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readRLE8(BmpParameters bmp) throws IOException {
/*  975 */     int imSize = (int)bmp.imageSize;
/*  976 */     if (imSize == 0) {
/*  977 */       imSize = (int)(bmp.bitmapFileSize - bmp.bitmapOffset);
/*      */     }
/*      */ 
/*      */     
/*  981 */     byte[] values = new byte[imSize];
/*  982 */     int bytesRead = 0;
/*  983 */     while (bytesRead < imSize) {
/*  984 */       bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  989 */     byte[] val = decodeRLE(true, values, bmp);
/*      */ 
/*      */     
/*  992 */     imSize = bmp.width * bmp.height;
/*      */     
/*  994 */     if (bmp.isBottomUp) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  999 */       byte[] temp = new byte[val.length];
/* 1000 */       int bytesPerScanline = bmp.width;
/* 1001 */       for (int i = 0; i < bmp.height; i++) {
/* 1002 */         System.arraycopy(val, imSize - (i + 1) * bytesPerScanline, temp, i * bytesPerScanline, bytesPerScanline);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1007 */       val = temp;
/*      */     } 
/* 1009 */     indexedModel(val, 8, 4, bmp);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void readRLE4(BmpParameters bmp) throws IOException {
/* 1014 */     int imSize = (int)bmp.imageSize;
/* 1015 */     if (imSize == 0) {
/* 1016 */       imSize = (int)(bmp.bitmapFileSize - bmp.bitmapOffset);
/*      */     }
/*      */ 
/*      */     
/* 1020 */     byte[] values = new byte[imSize];
/* 1021 */     int bytesRead = 0;
/* 1022 */     while (bytesRead < imSize) {
/* 1023 */       bytesRead += bmp.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1028 */     byte[] val = decodeRLE(false, values, bmp);
/*      */ 
/*      */     
/* 1031 */     if (bmp.isBottomUp) {
/*      */       
/* 1033 */       byte[] inverted = val;
/* 1034 */       val = new byte[bmp.width * bmp.height];
/* 1035 */       int l = 0;
/*      */       
/* 1037 */       for (int i = bmp.height - 1; i >= 0; i--) {
/* 1038 */         int index = i * bmp.width;
/* 1039 */         int lineEnd = l + bmp.width;
/* 1040 */         while (l != lineEnd) {
/* 1041 */           val[l++] = inverted[index++];
/*      */         }
/*      */       } 
/*      */     } 
/* 1045 */     int stride = (bmp.width + 1) / 2;
/* 1046 */     byte[] bdata = new byte[stride * bmp.height];
/* 1047 */     int ptr = 0;
/* 1048 */     int sh = 0;
/* 1049 */     for (int h = 0; h < bmp.height; h++) {
/* 1050 */       for (int w = 0; w < bmp.width; w++) {
/* 1051 */         if ((w & 0x1) == 0) {
/* 1052 */           bdata[sh + w / 2] = (byte)(val[ptr++] << 4);
/*      */         } else {
/* 1054 */           bdata[sh + w / 2] = (byte)(bdata[sh + w / 2] | (byte)(val[ptr++] & 0xF));
/*      */         } 
/* 1056 */       }  sh += stride;
/*      */     } 
/* 1058 */     indexedModel(bdata, 4, 4, bmp);
/*      */   }
/*      */   
/*      */   private static byte[] decodeRLE(boolean is8, byte[] values, BmpParameters bmp) {
/* 1062 */     byte[] val = new byte[bmp.width * bmp.height];
/*      */     try {
/* 1064 */       int ptr = 0;
/* 1065 */       int x = 0;
/* 1066 */       int q = 0;
/* 1067 */       for (int y = 0; y < bmp.height && ptr < values.length; ) {
/* 1068 */         int count = values[ptr++] & 0xFF;
/* 1069 */         if (count != 0) {
/*      */           
/* 1071 */           int bt = values[ptr++] & 0xFF;
/* 1072 */           if (is8) {
/* 1073 */             for (int i = count; i != 0; i--) {
/* 1074 */               val[q++] = (byte)bt;
/*      */             }
/*      */           } else {
/* 1077 */             for (int i = 0; i < count; i++) {
/* 1078 */               val[q++] = (byte)(((i & 0x1) == 1) ? (bt & 0xF) : (bt >>> 4 & 0xF));
/*      */             }
/*      */           } 
/* 1081 */           x += count;
/*      */           continue;
/*      */         } 
/* 1084 */         count = values[ptr++] & 0xFF;
/* 1085 */         if (count == 1)
/*      */           break; 
/* 1087 */         switch (count) {
/*      */           case 0:
/* 1089 */             x = 0;
/* 1090 */             y++;
/* 1091 */             q = y * bmp.width;
/*      */             continue;
/*      */           
/*      */           case 2:
/* 1095 */             x += values[ptr++] & 0xFF;
/* 1096 */             y += values[ptr++] & 0xFF;
/* 1097 */             q = y * bmp.width + x;
/*      */             continue;
/*      */         } 
/*      */         
/* 1101 */         if (is8) {
/* 1102 */           for (int i = count; i != 0; i--)
/* 1103 */             val[q++] = (byte)(values[ptr++] & 0xFF); 
/*      */         } else {
/* 1105 */           int bt = 0;
/* 1106 */           for (int i = 0; i < count; i++) {
/* 1107 */             if ((i & 0x1) == 0)
/* 1108 */               bt = values[ptr++] & 0xFF; 
/* 1109 */             val[q++] = (byte)(((i & 0x1) == 1) ? (bt & 0xF) : (bt >>> 4 & 0xF));
/*      */           } 
/*      */         } 
/* 1112 */         x += count;
/*      */         
/* 1114 */         if (is8) {
/* 1115 */           if ((count & 0x1) == 1)
/* 1116 */             ptr++;  continue;
/*      */         } 
/* 1118 */         if ((count & 0x3) == 1 || (count & 0x3) == 2) {
/* 1119 */           ptr++;
/*      */         
/*      */         }
/*      */       }
/*      */     
/*      */     }
/* 1125 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1129 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int readUnsignedByte(InputStream stream) throws IOException {
/* 1136 */     return stream.read() & 0xFF;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readUnsignedShort(InputStream stream) throws IOException {
/* 1141 */     int b1 = readUnsignedByte(stream);
/* 1142 */     int b2 = readUnsignedByte(stream);
/* 1143 */     return (b2 << 8 | b1) & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readShort(InputStream stream) throws IOException {
/* 1148 */     int b1 = readUnsignedByte(stream);
/* 1149 */     int b2 = readUnsignedByte(stream);
/* 1150 */     return b2 << 8 | b1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readWord(InputStream stream) throws IOException {
/* 1155 */     return readUnsignedShort(stream);
/*      */   }
/*      */ 
/*      */   
/*      */   private static long readUnsignedInt(InputStream stream) throws IOException {
/* 1160 */     int b1 = readUnsignedByte(stream);
/* 1161 */     int b2 = readUnsignedByte(stream);
/* 1162 */     int b3 = readUnsignedByte(stream);
/* 1163 */     int b4 = readUnsignedByte(stream);
/* 1164 */     long l = (b4 << 24 | b3 << 16 | b2 << 8 | b1);
/* 1165 */     return l & 0xFFFFFFFFFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readInt(InputStream stream) throws IOException {
/* 1170 */     int b1 = readUnsignedByte(stream);
/* 1171 */     int b2 = readUnsignedByte(stream);
/* 1172 */     int b3 = readUnsignedByte(stream);
/* 1173 */     int b4 = readUnsignedByte(stream);
/* 1174 */     return b4 << 24 | b3 << 16 | b2 << 8 | b1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static long readDWord(InputStream stream) throws IOException {
/* 1179 */     return readUnsignedInt(stream);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readLong(InputStream stream) throws IOException {
/* 1184 */     return readInt(stream);
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/BmpImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */