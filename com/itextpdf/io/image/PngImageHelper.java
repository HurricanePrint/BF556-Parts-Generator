/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.colors.IccProfile;
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.io.util.FilterUtil;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PngImageHelper
/*     */ {
/*     */   private static class PngParameters
/*     */   {
/*     */     PngImageData image;
/*     */     InputStream dataStream;
/*     */     int width;
/*     */     int height;
/*     */     int bitDepth;
/*     */     int compressionMethod;
/*     */     int filterMethod;
/*     */     int interlaceMethod;
/*     */     Map<String, Object> additional;
/*     */     byte[] imageData;
/*     */     byte[] smask;
/*     */     byte[] trans;
/*     */     ByteArrayOutputStream idat;
/*     */     int dpiX;
/*     */     int dpiY;
/*     */     float XYRatio;
/*     */     boolean genBWMask;
/*     */     boolean palShades;
/*     */     int transRedGray;
/*     */     int transGreen;
/*     */     int transBlue;
/*     */     int inputBands;
/*     */     int bytesPerPixel;
/*     */     String intent;
/*     */     IccProfile iccProfile;
/*     */     
/*     */     PngParameters(PngImageData image) {
/*  79 */       this.additional = new HashMap<>();
/*     */ 
/*     */ 
/*     */       
/*  83 */       this.idat = new ByteArrayOutputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       this.transRedGray = -1;
/*  90 */       this.transGreen = -1;
/*  91 */       this.transBlue = -1;
/*     */       this.image = image;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final int[] PNGID = new int[] { 137, 80, 78, 71, 13, 10, 26, 10 };
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String IHDR = "IHDR";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PLTE = "PLTE";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String IDAT = "IDAT";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String IEND = "IEND";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String tRNS = "tRNS";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String pHYs = "pHYs";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String gAMA = "gAMA";
/*     */ 
/*     */   
/*     */   public static final String cHRM = "cHRM";
/*     */ 
/*     */   
/*     */   public static final String sRGB = "sRGB";
/*     */ 
/*     */   
/*     */   public static final String iCCP = "iCCP";
/*     */ 
/*     */   
/*     */   private static final int TRANSFERSIZE = 4096;
/*     */ 
/*     */   
/*     */   private static final int PNG_FILTER_NONE = 0;
/*     */ 
/*     */   
/*     */   private static final int PNG_FILTER_SUB = 1;
/*     */ 
/*     */   
/*     */   private static final int PNG_FILTER_UP = 2;
/*     */ 
/*     */   
/*     */   private static final int PNG_FILTER_AVERAGE = 3;
/*     */ 
/*     */   
/*     */   private static final int PNG_FILTER_PAETH = 4;
/*     */ 
/*     */   
/* 160 */   private static final String[] intents = new String[] { "/Perceptual", "/RelativeColorimetric", "/Saturation", "/AbsoluteColormetric" };
/*     */ 
/*     */   
/*     */   public static void processImage(ImageData image) {
/*     */     PngParameters png;
/* 165 */     if (image.getOriginalType() != ImageType.PNG) {
/* 166 */       throw new IllegalArgumentException("PNG image expected");
/*     */     }
/* 168 */     InputStream pngStream = null;
/*     */     try {
/* 170 */       if (image.getData() == null) {
/* 171 */         image.loadData();
/*     */       }
/* 173 */       pngStream = new ByteArrayInputStream(image.getData());
/* 174 */       image.imageSize = (image.getData()).length;
/* 175 */       png = new PngParameters((PngImageData)image);
/* 176 */       processPng(pngStream, png);
/* 177 */     } catch (IOException e) {
/* 178 */       throw new IOException("PNG image exception.", e);
/*     */     } finally {
/* 180 */       if (pngStream != null) {
/*     */         try {
/* 182 */           pngStream.close();
/* 183 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */     
/* 187 */     RawImageHelper.updateImageAttributes(png.image, png.additional);
/*     */   }
/*     */   
/*     */   private static void processPng(InputStream pngStream, PngParameters png) throws IOException {
/* 191 */     readPng(pngStream, png);
/* 192 */     int colorType = png.image.getColorType();
/* 193 */     if (png.iccProfile != null && png.iccProfile.getNumComponents() != getExpectedNumberOfColorComponents(png)) {
/* 194 */       LoggerFactory.getLogger(PngImageHelper.class).warn("Png image has color profile with incompatible number of color components.");
/*     */     }
/*     */     try {
/* 197 */       int pal0 = 0;
/* 198 */       int palIdx = 0;
/* 199 */       png.palShades = false;
/* 200 */       if (png.trans != null) {
/* 201 */         for (int k = 0; k < png.trans.length; k++) {
/* 202 */           int n = png.trans[k] & 0xFF;
/* 203 */           if (n == 0) {
/* 204 */             pal0++;
/* 205 */             palIdx = k;
/*     */           } 
/* 207 */           if (n != 0 && n != 255) {
/* 208 */             png.palShades = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 213 */       if ((colorType & 0x4) != 0)
/* 214 */         png.palShades = true; 
/* 215 */       png.genBWMask = (!png.palShades && (pal0 > 1 || png.transRedGray >= 0));
/* 216 */       if (!png.palShades && !png.genBWMask && pal0 == 1) {
/* 217 */         png.additional.put("Mask", new int[] { palIdx, palIdx });
/*     */       }
/* 219 */       boolean needDecode = (png.interlaceMethod == 1 || png.bitDepth == 16 || (colorType & 0x4) != 0 || png.palShades || png.genBWMask);
/* 220 */       switch (colorType) {
/*     */         case 0:
/* 222 */           png.inputBands = 1;
/*     */           break;
/*     */         case 2:
/* 225 */           png.inputBands = 3;
/*     */           break;
/*     */         case 3:
/* 228 */           png.inputBands = 1;
/*     */           break;
/*     */         case 4:
/* 231 */           png.inputBands = 2;
/*     */           break;
/*     */         case 6:
/* 234 */           png.inputBands = 4;
/*     */           break;
/*     */       } 
/* 237 */       if (needDecode)
/* 238 */         decodeIdat(png); 
/* 239 */       int components = png.inputBands;
/* 240 */       if ((colorType & 0x4) != 0)
/* 241 */         components--; 
/* 242 */       int bpc = png.bitDepth;
/* 243 */       if (bpc == 16)
/* 244 */         bpc = 8; 
/* 245 */       if (png.imageData != null) {
/* 246 */         if (png.image.isIndexed()) {
/* 247 */           RawImageHelper.updateRawImageParameters(png.image, png.width, png.height, components, bpc, png.imageData);
/*     */         } else {
/* 249 */           RawImageHelper.updateRawImageParameters(png.image, png.width, png.height, components, bpc, png.imageData, null);
/*     */         } 
/*     */       } else {
/* 252 */         RawImageHelper.updateRawImageParameters(png.image, png.width, png.height, components, bpc, png.idat.toByteArray());
/* 253 */         png.image.setDeflated(true);
/* 254 */         Map<String, Object> decodeparms = new HashMap<>();
/* 255 */         decodeparms.put("BitsPerComponent", Integer.valueOf(png.bitDepth));
/* 256 */         decodeparms.put("Predictor", Integer.valueOf(15));
/* 257 */         decodeparms.put("Columns", Integer.valueOf(png.width));
/* 258 */         decodeparms.put("Colors", Integer.valueOf((png.image.isIndexed() || png.image.isGrayscaleImage()) ? 1 : 3));
/* 259 */         png.image.decodeParms = decodeparms;
/*     */       } 
/* 261 */       if (png.intent != null)
/* 262 */         png.additional.put("Intent", png.intent); 
/* 263 */       if (png.iccProfile != null)
/* 264 */         png.image.setProfile(png.iccProfile); 
/* 265 */       if (png.palShades) {
/* 266 */         RawImageData im2 = (RawImageData)ImageDataFactory.createRawImage(null);
/* 267 */         RawImageHelper.updateRawImageParameters(im2, png.width, png.height, 1, 8, png.smask);
/* 268 */         im2.makeMask();
/* 269 */         png.image.setImageMask(im2);
/*     */       } 
/* 271 */       if (png.genBWMask) {
/* 272 */         RawImageData im2 = (RawImageData)ImageDataFactory.createRawImage(null);
/* 273 */         RawImageHelper.updateRawImageParameters(im2, png.width, png.height, 1, 1, png.smask);
/* 274 */         im2.makeMask();
/* 275 */         png.image.setImageMask(im2);
/*     */       } 
/* 277 */       png.image.setDpi(png.dpiX, png.dpiY);
/* 278 */       png.image.setXYRatio(png.XYRatio);
/* 279 */     } catch (Exception e) {
/* 280 */       throw new IOException("PNG image exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getExpectedNumberOfColorComponents(PngParameters png) {
/* 285 */     return png.image.isGrayscaleImage() ? 1 : 3;
/*     */   }
/*     */   
/*     */   private static void readPng(InputStream pngStream, PngParameters png) throws IOException {
/* 289 */     for (int i = 0; i < PNGID.length; i++) {
/* 290 */       if (PNGID[i] != pngStream.read()) {
/* 291 */         throw new IOException("file.is.not.a.valid.png");
/*     */       }
/*     */     } 
/* 294 */     byte[] buffer = new byte[4096];
/*     */     while (true) {
/* 296 */       int len = getInt(pngStream);
/* 297 */       String marker = getString(pngStream);
/* 298 */       if (len < 0 || !checkMarker(marker))
/* 299 */         throw new IOException("corrupted.png.file"); 
/* 300 */       if ("IDAT".equals(marker))
/*     */       
/* 302 */       { while (len != 0) {
/* 303 */           int size = pngStream.read(buffer, 0, Math.min(len, 4096));
/* 304 */           if (size < 0)
/*     */             return; 
/* 306 */           png.idat.write(buffer, 0, size);
/* 307 */           len -= size;
/*     */         }  }
/* 309 */       else if ("tRNS".equals(marker))
/* 310 */       { switch (png.image.getColorType()) {
/*     */           case 0:
/* 312 */             if (len >= 2) {
/* 313 */               len -= 2;
/* 314 */               int gray = getWord(pngStream);
/* 315 */               if (png.bitDepth == 16) {
/* 316 */                 png.transRedGray = gray; break;
/*     */               } 
/* 318 */               png.additional.put("Mask", 
/* 319 */                   MessageFormatUtil.format("[{0} {1}]", new Object[] { Integer.valueOf(gray), Integer.valueOf(gray) }));
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 323 */             if (len >= 6) {
/* 324 */               len -= 6;
/* 325 */               int red = getWord(pngStream);
/* 326 */               int green = getWord(pngStream);
/* 327 */               int blue = getWord(pngStream);
/* 328 */               if (png.bitDepth == 16) {
/* 329 */                 png.transRedGray = red;
/* 330 */                 png.transGreen = green;
/* 331 */                 png.transBlue = blue; break;
/*     */               } 
/* 333 */               png.additional.put("Mask", MessageFormatUtil.format("[{0} {1} {2} {3} {4} {5}]", new Object[] { Integer.valueOf(red), Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(green), Integer.valueOf(blue), Integer.valueOf(blue) }));
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 337 */             if (len > 0) {
/* 338 */               png.trans = new byte[len];
/* 339 */               for (int k = 0; k < len; k++)
/* 340 */                 png.trans[k] = (byte)pngStream.read(); 
/* 341 */               len = 0;
/*     */             } 
/*     */             break;
/*     */         } 
/* 345 */         StreamUtil.skip(pngStream, len); }
/* 346 */       else if ("IHDR".equals(marker))
/* 347 */       { png.width = getInt(pngStream);
/* 348 */         png.height = getInt(pngStream);
/*     */         
/* 350 */         png.bitDepth = pngStream.read();
/* 351 */         png.image.setColorType(pngStream.read());
/* 352 */         png.compressionMethod = pngStream.read();
/* 353 */         png.filterMethod = pngStream.read();
/* 354 */         png.interlaceMethod = pngStream.read(); }
/* 355 */       else if ("PLTE".equals(marker))
/* 356 */       { if (png.image.isIndexed()) {
/* 357 */           ByteBuffer colorTableBuf = new ByteBuffer();
/* 358 */           while (len-- > 0) {
/* 359 */             colorTableBuf.append(pngStream.read());
/*     */           }
/* 361 */           png.image.setColorPalette(colorTableBuf.toByteArray());
/*     */         } else {
/* 363 */           StreamUtil.skip(pngStream, len);
/*     */         }  }
/* 365 */       else if ("pHYs".equals(marker))
/* 366 */       { int dx = getInt(pngStream);
/* 367 */         int dy = getInt(pngStream);
/* 368 */         int unit = pngStream.read();
/* 369 */         if (unit == 1) {
/* 370 */           png.dpiX = (int)(dx * 0.0254F + 0.5F);
/* 371 */           png.dpiY = (int)(dy * 0.0254F + 0.5F);
/*     */         }
/* 373 */         else if (dy != 0) {
/* 374 */           png.XYRatio = dx / dy;
/*     */         }  }
/* 376 */       else if ("cHRM".equals(marker))
/*     */       
/*     */       { 
/* 379 */         PngChromaticities pngChromaticities = new PngChromaticities(getInt(pngStream) / 100000.0F, getInt(pngStream) / 100000.0F, getInt(pngStream) / 100000.0F, getInt(pngStream) / 100000.0F, getInt(pngStream) / 100000.0F, getInt(pngStream) / 100000.0F, getInt(pngStream) / 100000.0F, getInt(pngStream) / 100000.0F);
/* 380 */         if (Math.abs(pngChromaticities.getXW()) >= 1.0E-4F && Math.abs(pngChromaticities.getYW()) >= 1.0E-4F && 
/* 381 */           Math.abs(pngChromaticities.getXR()) >= 1.0E-4F && Math.abs(pngChromaticities.getYR()) >= 1.0E-4F && 
/* 382 */           Math.abs(pngChromaticities.getXG()) >= 1.0E-4F && Math.abs(pngChromaticities.getYG()) >= 1.0E-4F && 
/* 383 */           Math.abs(pngChromaticities.getXB()) >= 1.0E-4F && Math.abs(pngChromaticities.getYB()) >= 1.0E-4F) {
/* 384 */           png.image.setPngChromaticities(pngChromaticities);
/*     */         } }
/* 386 */       else if ("sRGB".equals(marker))
/* 387 */       { int ri = pngStream.read();
/* 388 */         png.intent = intents[ri];
/* 389 */         png.image.setGamma(2.2F);
/* 390 */         PngChromaticities pngChromaticities = new PngChromaticities(0.3127F, 0.329F, 0.64F, 0.33F, 0.3F, 0.6F, 0.15F, 0.06F);
/*     */         
/* 392 */         png.image.setPngChromaticities(pngChromaticities); }
/* 393 */       else if ("gAMA".equals(marker))
/* 394 */       { int gm = getInt(pngStream);
/* 395 */         if (gm != 0) {
/* 396 */           png.image.setGamma(100000.0F / gm);
/* 397 */           if (!png.image.isHasCHRM()) {
/* 398 */             PngChromaticities pngChromaticities = new PngChromaticities(0.3127F, 0.329F, 0.64F, 0.33F, 0.3F, 0.6F, 0.15F, 0.06F);
/*     */             
/* 400 */             png.image.setPngChromaticities(pngChromaticities);
/*     */           } 
/*     */         }  }
/* 403 */       else if ("iCCP".equals(marker))
/*     */       { while (true)
/* 405 */         { len--;
/* 406 */           if (pngStream.read() == 0) {
/* 407 */             pngStream.read();
/* 408 */             len--;
/* 409 */             byte[] icccom = new byte[len];
/* 410 */             int p = 0;
/* 411 */             while (len > 0) {
/* 412 */               int r = pngStream.read(icccom, p, len);
/* 413 */               if (r < 0)
/* 414 */                 throw new IOException("premature.end.of.file"); 
/* 415 */               p += r;
/* 416 */               len -= r;
/*     */             } 
/* 418 */             byte[] iccp = FilterUtil.flateDecode(icccom, true);
/* 419 */             icccom = null;
/*     */             try {
/* 421 */               png.iccProfile = IccProfile.getInstance(iccp); break;
/* 422 */             } catch (RuntimeException e) {
/* 423 */               png.iccProfile = null;
/*     */             } 
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */ 
/*     */           
/* 430 */           StreamUtil.skip(pngStream, 4L); }  } else { if ("IEND".equals(marker)) break;  StreamUtil.skip(pngStream, len); }  StreamUtil.skip(pngStream, 4L);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean checkMarker(String s) {
/* 435 */     if (s.length() != 4)
/* 436 */       return false; 
/* 437 */     for (int k = 0; k < 4; k++) {
/* 438 */       char c = s.charAt(k);
/* 439 */       if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
/* 440 */         return false; 
/*     */     } 
/* 442 */     return true;
/*     */   }
/*     */   
/*     */   private static void decodeIdat(PngParameters png) {
/* 446 */     int nbitDepth = png.bitDepth;
/* 447 */     if (nbitDepth == 16)
/* 448 */       nbitDepth = 8; 
/* 449 */     int size = -1;
/* 450 */     png.bytesPerPixel = (png.bitDepth == 16) ? 2 : 1;
/* 451 */     switch (png.image.getColorType()) {
/*     */       case 0:
/* 453 */         size = (nbitDepth * png.width + 7) / 8 * png.height;
/*     */         break;
/*     */       case 2:
/* 456 */         size = png.width * 3 * png.height;
/* 457 */         png.bytesPerPixel *= 3;
/*     */         break;
/*     */       case 3:
/* 460 */         if (png.interlaceMethod == 1)
/* 461 */           size = (nbitDepth * png.width + 7) / 8 * png.height; 
/* 462 */         png.bytesPerPixel = 1;
/*     */         break;
/*     */       case 4:
/* 465 */         size = png.width * png.height;
/* 466 */         png.bytesPerPixel *= 2;
/*     */         break;
/*     */       case 6:
/* 469 */         size = png.width * 3 * png.height;
/* 470 */         png.bytesPerPixel *= 4;
/*     */         break;
/*     */     } 
/* 473 */     if (size >= 0)
/* 474 */       png.imageData = new byte[size]; 
/* 475 */     if (png.palShades) {
/* 476 */       png.smask = new byte[png.width * png.height];
/* 477 */     } else if (png.genBWMask) {
/* 478 */       png.smask = new byte[(png.width + 7) / 8 * png.height];
/* 479 */     }  ByteArrayInputStream bai = new ByteArrayInputStream(png.idat.toByteArray());
/* 480 */     png.dataStream = FilterUtil.getInflaterInputStream(bai);
/*     */     
/* 482 */     if (png.interlaceMethod != 1) {
/* 483 */       decodePass(0, 0, 1, 1, png.width, png.height, png);
/*     */     } else {
/* 485 */       decodePass(0, 0, 8, 8, (png.width + 7) / 8, (png.height + 7) / 8, png);
/* 486 */       decodePass(4, 0, 8, 8, (png.width + 3) / 8, (png.height + 7) / 8, png);
/* 487 */       decodePass(0, 4, 4, 8, (png.width + 3) / 4, (png.height + 3) / 8, png);
/* 488 */       decodePass(2, 0, 4, 4, (png.width + 1) / 4, (png.height + 3) / 4, png);
/* 489 */       decodePass(0, 2, 2, 4, (png.width + 1) / 2, (png.height + 1) / 4, png);
/* 490 */       decodePass(1, 0, 2, 2, png.width / 2, (png.height + 1) / 2, png);
/* 491 */       decodePass(0, 1, 1, 2, png.width, png.height / 2, png);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void decodePass(int xOffset, int yOffset, int xStep, int yStep, int passWidth, int passHeight, PngParameters png) {
/* 498 */     if (passWidth == 0 || passHeight == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 502 */     int bytesPerRow = (png.inputBands * passWidth * png.bitDepth + 7) / 8;
/* 503 */     byte[] curr = new byte[bytesPerRow];
/* 504 */     byte[] prior = new byte[bytesPerRow];
/*     */ 
/*     */ 
/*     */     
/* 508 */     int srcY = 0, dstY = yOffset;
/* 509 */     for (; srcY < passHeight; 
/* 510 */       srcY++, dstY += yStep) {
/*     */       
/* 512 */       int filter = 0;
/*     */       try {
/* 514 */         filter = png.dataStream.read();
/* 515 */         StreamUtil.readFully(png.dataStream, curr, 0, bytesPerRow);
/* 516 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/* 520 */       switch (filter) {
/*     */         case 0:
/*     */           break;
/*     */         case 1:
/* 524 */           decodeSubFilter(curr, bytesPerRow, png.bytesPerPixel);
/*     */           break;
/*     */         case 2:
/* 527 */           decodeUpFilter(curr, prior, bytesPerRow);
/*     */           break;
/*     */         case 3:
/* 530 */           decodeAverageFilter(curr, prior, bytesPerRow, png.bytesPerPixel);
/*     */           break;
/*     */         case 4:
/* 533 */           decodePaethFilter(curr, prior, bytesPerRow, png.bytesPerPixel);
/*     */           break;
/*     */         
/*     */         default:
/* 537 */           throw new IOException("Unknown PNG filter.");
/*     */       } 
/*     */       
/* 540 */       processPixels(curr, xOffset, xStep, dstY, passWidth, png);
/*     */ 
/*     */       
/* 543 */       byte[] tmp = prior;
/* 544 */       prior = curr;
/* 545 */       curr = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processPixels(byte[] curr, int xOffset, int step, int y, int width, PngParameters png) {
/* 551 */     int colorType = png.image.getColorType();
/*     */     
/* 553 */     int[] outPixel = getPixel(curr, png);
/* 554 */     int sizes = 0;
/* 555 */     switch (colorType) {
/*     */       case 0:
/*     */       case 3:
/*     */       case 4:
/* 559 */         sizes = 1;
/*     */         break;
/*     */       case 2:
/*     */       case 6:
/* 563 */         sizes = 3;
/*     */         break;
/*     */     } 
/* 566 */     if (png.imageData != null) {
/* 567 */       int dstX = xOffset;
/* 568 */       int yStride = (sizes * png.width * ((png.bitDepth == 16) ? 8 : png.bitDepth) + 7) / 8;
/* 569 */       for (int srcX = 0; srcX < width; srcX++) {
/* 570 */         setPixel(png.imageData, outPixel, png.inputBands * srcX, sizes, dstX, y, png.bitDepth, yStride);
/* 571 */         dstX += step;
/*     */       } 
/*     */     } 
/* 574 */     if (png.palShades) {
/* 575 */       if ((colorType & 0x4) != 0) {
/* 576 */         if (png.bitDepth == 16)
/* 577 */           for (int k = 0; k < width; k++) {
/* 578 */             outPixel[k * png.inputBands + sizes] = outPixel[k * png.inputBands + sizes] >>> 8;
/*     */           } 
/* 580 */         int yStride = png.width;
/* 581 */         int dstX = xOffset;
/* 582 */         for (int srcX = 0; srcX < width; srcX++) {
/* 583 */           setPixel(png.smask, outPixel, png.inputBands * srcX + sizes, 1, dstX, y, 8, yStride);
/* 584 */           dstX += step;
/*     */         } 
/*     */       } else {
/* 587 */         int yStride = png.width;
/* 588 */         int[] v = new int[1];
/* 589 */         int dstX = xOffset;
/* 590 */         for (int srcX = 0; srcX < width; srcX++) {
/* 591 */           int idx = outPixel[srcX];
/* 592 */           if (idx < png.trans.length) {
/* 593 */             v[0] = png.trans[idx];
/*     */           } else {
/*     */             
/* 596 */             v[0] = 255;
/*     */           } 
/* 598 */           setPixel(png.smask, v, 0, 1, dstX, y, 8, yStride);
/* 599 */           dstX += step;
/*     */         } 
/*     */       } 
/* 602 */     } else if (png.genBWMask) {
/* 603 */       int srcX; int dstX; int yStride; int[] v; switch (colorType) {
/*     */         case 3:
/* 605 */           yStride = (png.width + 7) / 8;
/* 606 */           v = new int[1];
/* 607 */           dstX = xOffset;
/* 608 */           for (srcX = 0; srcX < width; srcX++) {
/* 609 */             int idx = outPixel[srcX];
/* 610 */             v[0] = (idx < png.trans.length && png.trans[idx] == 0) ? 1 : 0;
/* 611 */             setPixel(png.smask, v, 0, 1, dstX, y, 1, yStride);
/* 612 */             dstX += step;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 0:
/* 617 */           yStride = (png.width + 7) / 8;
/* 618 */           v = new int[1];
/* 619 */           dstX = xOffset;
/* 620 */           for (srcX = 0; srcX < width; srcX++) {
/* 621 */             int g = outPixel[srcX];
/* 622 */             v[0] = (g == png.transRedGray) ? 1 : 0;
/* 623 */             setPixel(png.smask, v, 0, 1, dstX, y, 1, yStride);
/* 624 */             dstX += step;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 2:
/* 629 */           yStride = (png.width + 7) / 8;
/* 630 */           v = new int[1];
/* 631 */           dstX = xOffset;
/* 632 */           for (srcX = 0; srcX < width; srcX++) {
/* 633 */             int markRed = png.inputBands * srcX;
/* 634 */             v[0] = (outPixel[markRed] == png.transRedGray && outPixel[markRed + 1] == png.transGreen && outPixel[markRed + 2] == png.transBlue) ? 1 : 0;
/*     */             
/* 636 */             setPixel(png.smask, v, 0, 1, dstX, y, 1, yStride);
/* 637 */             dstX += step;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getPixel(byte[] image, int x, int y, int bitDepth, int bytesPerRow) {
/* 646 */     if (bitDepth == 8) {
/* 647 */       int i = bytesPerRow * y + x;
/* 648 */       return image[i] & 0xFF;
/*     */     } 
/* 650 */     int pos = bytesPerRow * y + x / 8 / bitDepth;
/* 651 */     int v = image[pos] >> 8 - bitDepth * x % 8 / bitDepth - bitDepth;
/* 652 */     return v & (1 << bitDepth) - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   static void setPixel(byte[] image, int[] data, int offset, int size, int x, int y, int bitDepth, int bytesPerRow) {
/* 657 */     if (bitDepth == 8) {
/* 658 */       int pos = bytesPerRow * y + size * x;
/* 659 */       for (int k = 0; k < size; k++)
/* 660 */         image[pos + k] = (byte)data[k + offset]; 
/* 661 */     } else if (bitDepth == 16) {
/* 662 */       int pos = bytesPerRow * y + size * x;
/* 663 */       for (int k = 0; k < size; k++)
/* 664 */         image[pos + k] = (byte)(data[k + offset] >>> 8); 
/*     */     } else {
/* 666 */       int pos = bytesPerRow * y + x / 8 / bitDepth;
/* 667 */       int v = data[offset] << 8 - bitDepth * x % 8 / bitDepth - bitDepth;
/* 668 */       image[pos] = (byte)(image[pos] | (byte)v);
/*     */     } 
/*     */   }
/*     */   private static int[] getPixel(byte[] curr, PngParameters png) {
/*     */     int k;
/* 673 */     switch (png.bitDepth) {
/*     */       case 8:
/* 675 */         res = new int[curr.length];
/* 676 */         for (k = 0; k < res.length; k++)
/* 677 */           res[k] = curr[k] & 0xFF; 
/* 678 */         return res;
/*     */       
/*     */       case 16:
/* 681 */         res = new int[curr.length / 2];
/* 682 */         for (k = 0; k < res.length; k++)
/* 683 */           res[k] = ((curr[k * 2] & 0xFF) << 8) + (curr[k * 2 + 1] & 0xFF); 
/* 684 */         return res;
/*     */     } 
/*     */     
/* 687 */     int[] res = new int[curr.length * 8 / png.bitDepth];
/* 688 */     int idx = 0;
/* 689 */     int passes = 8 / png.bitDepth;
/* 690 */     int mask = (1 << png.bitDepth) - 1;
/* 691 */     for (int i = 0; i < curr.length; i++) {
/* 692 */       for (int j = passes - 1; j >= 0; j--) {
/* 693 */         res[idx++] = curr[i] >>> png.bitDepth * j & mask;
/*     */       }
/*     */     } 
/* 696 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void decodeSubFilter(byte[] curr, int count, int bpp) {
/* 702 */     for (int i = bpp; i < count; i++) {
/* 703 */       int val = curr[i] & 0xFF;
/* 704 */       val += curr[i - bpp] & 0xFF;
/* 705 */       curr[i] = (byte)val;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void decodeUpFilter(byte[] curr, byte[] prev, int count) {
/* 710 */     for (int i = 0; i < count; i++) {
/* 711 */       int raw = curr[i] & 0xFF;
/* 712 */       int prior = prev[i] & 0xFF;
/* 713 */       curr[i] = (byte)(raw + prior);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void decodeAverageFilter(byte[] curr, byte[] prev, int count, int bpp) {
/*     */     int i;
/* 720 */     for (i = 0; i < bpp; i++) {
/* 721 */       int raw = curr[i] & 0xFF;
/* 722 */       int priorRow = prev[i] & 0xFF;
/* 723 */       curr[i] = (byte)(raw + priorRow / 2);
/*     */     } 
/*     */     
/* 726 */     for (i = bpp; i < count; i++) {
/* 727 */       int raw = curr[i] & 0xFF;
/* 728 */       int priorPixel = curr[i - bpp] & 0xFF;
/* 729 */       int priorRow = prev[i] & 0xFF;
/* 730 */       curr[i] = (byte)(raw + (priorPixel + priorRow) / 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int paethPredictor(int a, int b, int c) {
/* 735 */     int p = a + b - c;
/* 736 */     int pa = Math.abs(p - a);
/* 737 */     int pb = Math.abs(p - b);
/* 738 */     int pc = Math.abs(p - c);
/*     */     
/* 740 */     if (pa <= pb && pa <= pc)
/* 741 */       return a; 
/* 742 */     if (pb <= pc) {
/* 743 */       return b;
/*     */     }
/* 745 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void decodePaethFilter(byte[] curr, byte[] prev, int count, int bpp) {
/*     */     int i;
/* 752 */     for (i = 0; i < bpp; i++) {
/* 753 */       int raw = curr[i] & 0xFF;
/* 754 */       int priorRow = prev[i] & 0xFF;
/* 755 */       curr[i] = (byte)(raw + priorRow);
/*     */     } 
/*     */     
/* 758 */     for (i = bpp; i < count; i++) {
/* 759 */       int raw = curr[i] & 0xFF;
/* 760 */       int priorPixel = curr[i - bpp] & 0xFF;
/* 761 */       int priorRow = prev[i] & 0xFF;
/* 762 */       int priorRowPixel = prev[i - bpp] & 0xFF;
/* 763 */       curr[i] = (byte)(raw + paethPredictor(priorPixel, priorRow, priorRowPixel));
/*     */     } 
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
/*     */   public static int getInt(InputStream pngStream) throws IOException {
/* 776 */     return (pngStream.read() << 24) + (pngStream.read() << 16) + (pngStream
/* 777 */       .read() << 8) + pngStream.read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getWord(InputStream pngStream) throws IOException {
/* 787 */     return (pngStream.read() << 8) + pngStream.read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(InputStream pngStream) throws IOException {
/* 797 */     StringBuilder buf = new StringBuilder();
/* 798 */     for (int i = 0; i < 4; i++) {
/* 799 */       buf.append((char)pngStream.read());
/*     */     }
/* 801 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/PngImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */