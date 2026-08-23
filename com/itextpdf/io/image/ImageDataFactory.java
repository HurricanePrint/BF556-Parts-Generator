/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.codec.CCITTG4Encoder;
/*     */ import com.itextpdf.io.codec.TIFFFaxDecoder;
/*     */ import com.itextpdf.io.util.UrlUtil;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ImageDataFactory
/*     */ {
/*     */   public static ImageData create(byte[] bytes, boolean recoverImage) {
/*  69 */     return createImageInstance(bytes, recoverImage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData create(byte[] bytes) {
/*  78 */     return create(bytes, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData create(URL url, boolean recoverImage) {
/*  88 */     return createImageInstance(url, recoverImage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData create(URL url) {
/*  97 */     return create(url, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData create(String filename, boolean recoverImage) throws MalformedURLException {
/* 108 */     return create(UrlUtil.toURL(filename), recoverImage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData create(String filename) throws MalformedURLException {
/* 118 */     return create(filename, false);
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
/*     */   public static ImageData create(int width, int height, boolean reverseBits, int typeCCITT, int parameters, byte[] data, int[] transparency) {
/* 136 */     if (transparency != null && transparency.length != 2)
/* 137 */       throw new IOException("Transparency length must be equal to 2 with CCITT images"); 
/* 138 */     if (typeCCITT != 256 && typeCCITT != 257 && typeCCITT != 258)
/* 139 */       throw new IOException("CCITT compression type must be CCITTG4, CCITTG3_1D or CCITTG3_2D."); 
/* 140 */     if (reverseBits)
/* 141 */       TIFFFaxDecoder.reverseBits(data); 
/* 142 */     RawImageData image = new RawImageData(data, ImageType.RAW);
/* 143 */     image.setTypeCcitt(typeCCITT);
/* 144 */     image.height = height;
/* 145 */     image.width = width;
/* 146 */     image.colorSpace = parameters;
/* 147 */     image.transparency = transparency;
/* 148 */     return image;
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
/*     */   public static ImageData create(int width, int height, int components, int bpc, byte[] data, int[] transparency) {
/* 164 */     if (transparency != null && transparency.length != components * 2)
/* 165 */       throw new IOException("Transparency length must be equal to 2 with CCITT images"); 
/* 166 */     if (components == 1 && bpc == 1) {
/* 167 */       byte[] g4 = CCITTG4Encoder.compress(data, width, height);
/* 168 */       return create(width, height, false, 256, 1, g4, transparency);
/*     */     } 
/* 170 */     RawImageData image = new RawImageData(data, ImageType.RAW);
/* 171 */     image.height = height;
/* 172 */     image.width = width;
/* 173 */     if (components != 1 && components != 3 && components != 4)
/* 174 */       throw new IOException("Components must be 1, 3 or 4."); 
/* 175 */     if (bpc != 1 && bpc != 2 && bpc != 4 && bpc != 8)
/* 176 */       throw new IOException("Bits per component must be 1, 2, 4 or 8."); 
/* 177 */     image.colorSpace = components;
/* 178 */     image.bpc = bpc;
/* 179 */     image.data = data;
/* 180 */     image.transparency = transparency;
/* 181 */     return image;
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
/*     */   public static ImageData create(Image image, Color color) throws IOException {
/* 193 */     return create(image, color, false);
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
/*     */   public static ImageData create(Image image, Color color, boolean forceBW) throws IOException {
/* 206 */     return AwtImageDataFactory.create(image, color, forceBW);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData createBmp(URL url, boolean noHeader) {
/* 217 */     return createBmp(url, noHeader, 0);
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
/*     */   @Deprecated
/*     */   public static ImageData createBmp(URL url, boolean noHeader, int size) {
/* 231 */     validateImageType(url, ImageType.BMP);
/* 232 */     ImageData image = new BmpImageData(url, noHeader, size);
/* 233 */     BmpImageHelper.processImage(image);
/* 234 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData createBmp(byte[] bytes, boolean noHeader) {
/* 245 */     return createBmp(bytes, noHeader, 0);
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
/*     */   @Deprecated
/*     */   public static ImageData createBmp(byte[] bytes, boolean noHeader, int size) {
/* 259 */     if (noHeader || ImageTypeDetector.detectImageType(bytes) == ImageType.BMP) {
/* 260 */       ImageData image = new BmpImageData(bytes, noHeader, size);
/* 261 */       BmpImageHelper.processImage(image);
/* 262 */       return image;
/*     */     } 
/* 264 */     throw new IllegalArgumentException("BMP image expected.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GifImageData createGif(byte[] bytes) {
/* 274 */     validateImageType(bytes, ImageType.GIF);
/* 275 */     GifImageData image = new GifImageData(bytes);
/* 276 */     GifImageHelper.processImage(image);
/* 277 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData createGifFrame(URL url, int frame) {
/* 288 */     return createGifFrames(url, new int[] { frame }).get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData createGifFrame(byte[] bytes, int frame) {
/* 299 */     return createGifFrames(bytes, new int[] { frame }).get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ImageData> createGifFrames(byte[] bytes, int[] frameNumbers) {
/* 310 */     validateImageType(bytes, ImageType.GIF);
/* 311 */     GifImageData image = new GifImageData(bytes);
/* 312 */     return processGifImageAndExtractFrames(frameNumbers, image);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ImageData> createGifFrames(URL url, int[] frameNumbers) {
/* 323 */     validateImageType(url, ImageType.GIF);
/* 324 */     GifImageData image = new GifImageData(url);
/* 325 */     return processGifImageAndExtractFrames(frameNumbers, image);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ImageData> createGifFrames(byte[] bytes) {
/* 335 */     validateImageType(bytes, ImageType.GIF);
/* 336 */     GifImageData image = new GifImageData(bytes);
/* 337 */     GifImageHelper.processImage(image);
/* 338 */     return image.getFrames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ImageData> createGifFrames(URL url) {
/* 348 */     validateImageType(url, ImageType.GIF);
/* 349 */     GifImageData image = new GifImageData(url);
/* 350 */     GifImageHelper.processImage(image);
/* 351 */     return image.getFrames();
/*     */   }
/*     */   
/*     */   public static ImageData createJbig2(URL url, int page) {
/* 355 */     if (page < 1)
/* 356 */       throw new IllegalArgumentException("The page number must be greater than 0"); 
/* 357 */     validateImageType(url, ImageType.JBIG2);
/* 358 */     ImageData image = new Jbig2ImageData(url, page);
/* 359 */     Jbig2ImageHelper.processImage(image);
/* 360 */     return image;
/*     */   }
/*     */   
/*     */   public static ImageData createJbig2(byte[] bytes, int page) {
/* 364 */     if (page < 1)
/* 365 */       throw new IllegalArgumentException("The page number must be greater than 0"); 
/* 366 */     validateImageType(bytes, ImageType.JBIG2);
/* 367 */     ImageData image = new Jbig2ImageData(bytes, page);
/* 368 */     Jbig2ImageHelper.processImage(image);
/* 369 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageData createJpeg(URL url) {
/* 379 */     validateImageType(url, ImageType.JPEG);
/* 380 */     ImageData image = new JpegImageData(url);
/* 381 */     JpegImageHelper.processImage(image);
/* 382 */     return image;
/*     */   }
/*     */   
/*     */   public static ImageData createJpeg(byte[] bytes) {
/* 386 */     validateImageType(bytes, ImageType.JPEG);
/* 387 */     ImageData image = new JpegImageData(bytes);
/* 388 */     JpegImageHelper.processImage(image);
/* 389 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImageData createJpeg2000(URL url) {
/* 394 */     validateImageType(url, ImageType.JPEG2000);
/* 395 */     ImageData image = new Jpeg2000ImageData(url);
/* 396 */     Jpeg2000ImageHelper.processImage(image);
/* 397 */     return image;
/*     */   }
/*     */   
/*     */   public static ImageData createJpeg2000(byte[] bytes) {
/* 401 */     validateImageType(bytes, ImageType.JPEG2000);
/* 402 */     ImageData image = new Jpeg2000ImageData(bytes);
/* 403 */     Jpeg2000ImageHelper.processImage(image);
/* 404 */     return image;
/*     */   }
/*     */   
/*     */   public static ImageData createPng(URL url) {
/* 408 */     validateImageType(url, ImageType.PNG);
/* 409 */     ImageData image = new PngImageData(url);
/* 410 */     PngImageHelper.processImage(image);
/* 411 */     return image;
/*     */   }
/*     */   
/*     */   public static ImageData createPng(byte[] bytes) {
/* 415 */     validateImageType(bytes, ImageType.PNG);
/* 416 */     ImageData image = new PngImageData(bytes);
/* 417 */     PngImageHelper.processImage(image);
/* 418 */     return image;
/*     */   }
/*     */   
/*     */   public static ImageData createTiff(URL url, boolean recoverFromImageError, int page, boolean direct) {
/* 422 */     validateImageType(url, ImageType.TIFF);
/* 423 */     ImageData image = new TiffImageData(url, recoverFromImageError, page, direct);
/* 424 */     TiffImageHelper.processImage(image);
/* 425 */     return image;
/*     */   }
/*     */   
/*     */   public static ImageData createTiff(byte[] bytes, boolean recoverFromImageError, int page, boolean direct) {
/* 429 */     validateImageType(bytes, ImageType.TIFF);
/* 430 */     ImageData image = new TiffImageData(bytes, recoverFromImageError, page, direct);
/* 431 */     TiffImageHelper.processImage(image);
/* 432 */     return image;
/*     */   }
/*     */   
/*     */   public static ImageData createRawImage(byte[] bytes) {
/* 436 */     return new RawImageData(bytes, ImageType.RAW);
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
/*     */   public static boolean isSupportedType(byte[] source) {
/* 448 */     if (source == null) {
/* 449 */       return false;
/*     */     }
/* 451 */     ImageType imageType = ImageTypeDetector.detectImageType(source);
/* 452 */     return isSupportedType(imageType);
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
/*     */   public static boolean isSupportedType(URL source) {
/* 464 */     if (source == null) {
/* 465 */       return false;
/*     */     }
/* 467 */     ImageType imageType = ImageTypeDetector.detectImageType(source);
/* 468 */     return isSupportedType(imageType);
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
/*     */   public static boolean isSupportedType(ImageType imageType) {
/* 480 */     return (imageType == ImageType.GIF || imageType == ImageType.JPEG || imageType == ImageType.JPEG2000 || imageType == ImageType.PNG || imageType == ImageType.BMP || imageType == ImageType.TIFF || imageType == ImageType.JBIG2);
/*     */   }
/*     */   
/*     */   private static ImageData createImageInstance(URL source, boolean recoverImage) {
/*     */     GifImageData gifImageData;
/*     */     ImageData image;
/* 486 */     ImageType imageType = ImageTypeDetector.detectImageType(source);
/* 487 */     switch (imageType) {
/*     */       case GIF:
/* 489 */         gifImageData = new GifImageData(source);
/* 490 */         GifImageHelper.processImage(gifImageData, 0);
/* 491 */         return gifImageData.getFrames().get(0);
/*     */       
/*     */       case JPEG:
/* 494 */         image = new JpegImageData(source);
/* 495 */         JpegImageHelper.processImage(image);
/* 496 */         return image;
/*     */       
/*     */       case JPEG2000:
/* 499 */         image = new Jpeg2000ImageData(source);
/* 500 */         Jpeg2000ImageHelper.processImage(image);
/* 501 */         return image;
/*     */       
/*     */       case PNG:
/* 504 */         image = new PngImageData(source);
/* 505 */         PngImageHelper.processImage(image);
/* 506 */         return image;
/*     */       
/*     */       case BMP:
/* 509 */         image = new BmpImageData(source, false);
/* 510 */         BmpImageHelper.processImage(image);
/* 511 */         return image;
/*     */       
/*     */       case TIFF:
/* 514 */         image = new TiffImageData(source, recoverImage, 1, false);
/* 515 */         TiffImageHelper.processImage(image);
/* 516 */         return image;
/*     */       
/*     */       case JBIG2:
/* 519 */         image = new Jbig2ImageData(source, 1);
/* 520 */         Jbig2ImageHelper.processImage(image);
/* 521 */         return image;
/*     */     } 
/*     */     
/* 524 */     throw new IOException("Image format cannot be recognized.");
/*     */   }
/*     */   private static ImageData createImageInstance(byte[] bytes, boolean recoverImage) {
/*     */     GifImageData gifImageData;
/*     */     ImageData image;
/* 529 */     ImageType imageType = ImageTypeDetector.detectImageType(bytes);
/* 530 */     switch (imageType) {
/*     */       case GIF:
/* 532 */         gifImageData = new GifImageData(bytes);
/* 533 */         GifImageHelper.processImage(gifImageData, 0);
/* 534 */         return gifImageData.getFrames().get(0);
/*     */       
/*     */       case JPEG:
/* 537 */         image = new JpegImageData(bytes);
/* 538 */         JpegImageHelper.processImage(image);
/* 539 */         return image;
/*     */       
/*     */       case JPEG2000:
/* 542 */         image = new Jpeg2000ImageData(bytes);
/* 543 */         Jpeg2000ImageHelper.processImage(image);
/* 544 */         return image;
/*     */       
/*     */       case PNG:
/* 547 */         image = new PngImageData(bytes);
/* 548 */         PngImageHelper.processImage(image);
/* 549 */         return image;
/*     */       
/*     */       case BMP:
/* 552 */         image = new BmpImageData(bytes, false);
/* 553 */         BmpImageHelper.processImage(image);
/* 554 */         return image;
/*     */       
/*     */       case TIFF:
/* 557 */         image = new TiffImageData(bytes, recoverImage, 1, false);
/* 558 */         TiffImageHelper.processImage(image);
/* 559 */         return image;
/*     */       
/*     */       case JBIG2:
/* 562 */         image = new Jbig2ImageData(bytes, 1);
/* 563 */         Jbig2ImageHelper.processImage(image);
/* 564 */         return image;
/*     */     } 
/*     */     
/* 567 */     throw new IOException("Image format cannot be recognized.");
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<ImageData> processGifImageAndExtractFrames(int[] frameNumbers, GifImageData image) {
/* 572 */     Arrays.sort(frameNumbers);
/* 573 */     GifImageHelper.processImage(image, frameNumbers[frameNumbers.length - 1] - 1);
/* 574 */     List<ImageData> frames = new ArrayList<>();
/* 575 */     for (int frame : frameNumbers) {
/* 576 */       frames.add(image.getFrames().get(frame - 1));
/*     */     }
/* 578 */     return frames;
/*     */   }
/*     */   
/*     */   private static void validateImageType(byte[] image, ImageType expectedType) {
/* 582 */     ImageType detectedType = ImageTypeDetector.detectImageType(image);
/* 583 */     if (detectedType != expectedType) {
/* 584 */       throw new IllegalArgumentException(expectedType.name() + " image expected. Detected image type: " + detectedType
/* 585 */           .name());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void validateImageType(URL imageUrl, ImageType expectedType) {
/* 590 */     ImageType detectedType = ImageTypeDetector.detectImageType(imageUrl);
/* 591 */     if (detectedType != expectedType)
/* 592 */       throw new IllegalArgumentException(expectedType.name() + " image expected. Detected image type: " + detectedType
/* 593 */           .name()); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/ImageDataFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */