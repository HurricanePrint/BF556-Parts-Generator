/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.codec.CCITTG4Encoder;
/*     */ import com.itextpdf.io.codec.TIFFDirectory;
/*     */ import com.itextpdf.io.codec.TIFFFaxDecoder;
/*     */ import com.itextpdf.io.codec.TIFFField;
/*     */ import com.itextpdf.io.codec.TIFFLZWDecoder;
/*     */ import com.itextpdf.io.colors.IccProfile;
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.io.source.DeflaterOutputStream;
/*     */ import com.itextpdf.io.source.IRandomAccessSource;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import com.itextpdf.io.util.FilterUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
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
/*     */ class TiffImageHelper
/*     */ {
/*     */   private static class TiffParameters
/*     */   {
/*     */     TiffImageData image;
/*     */     boolean jpegProcessing;
/*     */     Map<String, Object> additional;
/*     */     
/*     */     TiffParameters(TiffImageData image) {
/*  69 */       this.image = image;
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
/*     */   public static void processImage(ImageData image) {
/*  82 */     if (image.getOriginalType() != ImageType.TIFF) {
/*  83 */       throw new IllegalArgumentException("TIFF image expected");
/*     */     }
/*     */     try {
/*  86 */       if (image.getData() == null) {
/*  87 */         image.loadData();
/*     */       }
/*  89 */       IRandomAccessSource ras = (new RandomAccessSourceFactory()).createSource(image.getData());
/*  90 */       RandomAccessFileOrArray raf = new RandomAccessFileOrArray(ras);
/*  91 */       TiffParameters tiff = new TiffParameters((TiffImageData)image);
/*  92 */       processTiffImage(raf, tiff);
/*  93 */       raf.close();
/*     */       
/*  95 */       if (!tiff.jpegProcessing) {
/*  96 */         RawImageHelper.updateImageAttributes(tiff.image, tiff.additional);
/*     */       }
/*  98 */     } catch (IOException e) {
/*  99 */       throw new IOException("TIFF image exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processTiffImage(RandomAccessFileOrArray s, TiffParameters tiff) {
/* 104 */     boolean recoverFromImageError = tiff.image.isRecoverFromImageError();
/* 105 */     int page = tiff.image.getPage();
/* 106 */     boolean direct = tiff.image.isDirect();
/* 107 */     if (page < 1)
/* 108 */       throw new IOException("Page number must be >= 1.");  try {
/*     */       TIFFField t4OptionsField, t6OptionsField;
/* 110 */       TIFFDirectory dir = new TIFFDirectory(s, page - 1);
/* 111 */       if (dir.isTagPresent(322))
/* 112 */         throw new IOException("Tiles are not supported."); 
/* 113 */       int compression = 1;
/* 114 */       if (dir.isTagPresent(259)) {
/* 115 */         compression = (int)dir.getFieldAsLong(259);
/*     */       }
/* 117 */       switch (compression) {
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/*     */         case 32771:
/*     */           break;
/*     */         default:
/* 124 */           processTiffImageColor(dir, s, tiff);
/*     */           return;
/*     */       } 
/* 127 */       float rotation = 0.0F;
/* 128 */       if (dir.isTagPresent(274)) {
/* 129 */         int rot = (int)dir.getFieldAsLong(274);
/* 130 */         if (rot == 3 || rot == 4) {
/* 131 */           rotation = 3.1415927F;
/* 132 */         } else if (rot == 5 || rot == 8) {
/* 133 */           rotation = 1.5707964F;
/* 134 */         } else if (rot == 6 || rot == 7) {
/* 135 */           rotation = -1.5707964F;
/*     */         } 
/*     */       } 
/* 138 */       long tiffT4Options = 0L;
/* 139 */       long tiffT6Options = 0L;
/* 140 */       int fillOrder = 1;
/* 141 */       int h = (int)dir.getFieldAsLong(257);
/* 142 */       int w = (int)dir.getFieldAsLong(256);
/* 143 */       float XYRatio = 0.0F;
/* 144 */       int resolutionUnit = 2;
/* 145 */       if (dir.isTagPresent(296))
/* 146 */         resolutionUnit = (int)dir.getFieldAsLong(296); 
/* 147 */       int dpiX = getDpi(dir.getField(282), resolutionUnit);
/* 148 */       int dpiY = getDpi(dir.getField(283), resolutionUnit);
/* 149 */       if (resolutionUnit == 1) {
/* 150 */         if (dpiY != 0)
/* 151 */           XYRatio = dpiX / dpiY; 
/* 152 */         dpiX = 0;
/* 153 */         dpiY = 0;
/*     */       } 
/* 155 */       int rowsStrip = h;
/* 156 */       if (dir.isTagPresent(278))
/* 157 */         rowsStrip = (int)dir.getFieldAsLong(278); 
/* 158 */       if (rowsStrip <= 0 || rowsStrip > h)
/* 159 */         rowsStrip = h; 
/* 160 */       long[] offset = getArrayLongShort(dir, 273);
/* 161 */       long[] size = getArrayLongShort(dir, 279);
/*     */ 
/*     */       
/* 164 */       if ((size == null || (size.length == 1 && (size[0] == 0L || size[0] + offset[0] > s.length()))) && h == rowsStrip) {
/* 165 */         size = new long[] { s.length() - (int)offset[0] };
/*     */       }
/* 167 */       boolean reverse = false;
/* 168 */       TIFFField fillOrderField = dir.getField(266);
/* 169 */       if (fillOrderField != null)
/* 170 */         fillOrder = fillOrderField.getAsInt(0); 
/* 171 */       reverse = (fillOrder == 2);
/* 172 */       int parameters = 0;
/* 173 */       if (dir.isTagPresent(262)) {
/* 174 */         long photo = dir.getFieldAsLong(262);
/* 175 */         if (photo == 1L)
/* 176 */           parameters |= 0x1; 
/*     */       } 
/* 178 */       int imagecomp = 0;
/* 179 */       switch (compression) {
/*     */         case 2:
/*     */         case 32771:
/* 182 */           imagecomp = 257;
/* 183 */           parameters |= 0xA;
/*     */           break;
/*     */         case 3:
/* 186 */           imagecomp = 257;
/* 187 */           parameters |= 0xC;
/* 188 */           t4OptionsField = dir.getField(292);
/* 189 */           if (t4OptionsField != null) {
/* 190 */             tiffT4Options = t4OptionsField.getAsLong(0);
/* 191 */             if ((tiffT4Options & 0x1L) != 0L)
/* 192 */               imagecomp = 258; 
/* 193 */             if ((tiffT4Options & 0x4L) != 0L)
/* 194 */               parameters |= 0x2; 
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 198 */           imagecomp = 256;
/* 199 */           t6OptionsField = dir.getField(293);
/* 200 */           if (t6OptionsField != null) {
/* 201 */             tiffT6Options = t6OptionsField.getAsLong(0);
/*     */           }
/*     */           break;
/*     */       } 
/*     */       
/* 206 */       if (direct && rowsStrip == h) {
/* 207 */         byte[] im = new byte[(int)size[0]];
/* 208 */         s.seek(offset[0]);
/* 209 */         s.readFully(im);
/* 210 */         RawImageHelper.updateRawImageParameters(tiff.image, w, h, false, imagecomp, parameters, im, null);
/* 211 */         tiff.image.setInverted(true);
/*     */       } else {
/* 213 */         int rowsLeft = h;
/* 214 */         CCITTG4Encoder g4 = new CCITTG4Encoder(w);
/* 215 */         for (int k = 0; k < offset.length; k++) {
/* 216 */           byte[] im = new byte[(int)size[k]];
/* 217 */           s.seek(offset[k]);
/* 218 */           s.readFully(im);
/* 219 */           int height = Math.min(rowsStrip, rowsLeft);
/* 220 */           TIFFFaxDecoder decoder = new TIFFFaxDecoder(fillOrder, w, height);
/* 221 */           decoder.setRecoverFromImageError(recoverFromImageError);
/* 222 */           byte[] outBuf = new byte[(w + 7) / 8 * height];
/* 223 */           switch (compression) {
/*     */             case 2:
/*     */             case 32771:
/* 226 */               decoder.decode1D(outBuf, im, 0, height);
/* 227 */               g4.fax4Encode(outBuf, height);
/*     */               break;
/*     */             case 3:
/*     */               try {
/* 231 */                 decoder.decode2D(outBuf, im, 0, height, tiffT4Options);
/* 232 */               } catch (RuntimeException e) {
/*     */                 
/* 234 */                 tiffT4Options ^= 0x4L;
/*     */                 try {
/* 236 */                   decoder.decode2D(outBuf, im, 0, height, tiffT4Options);
/* 237 */                 } catch (RuntimeException e2) {
/* 238 */                   if (!recoverFromImageError)
/* 239 */                     throw e; 
/* 240 */                   if (rowsStrip == 1) {
/* 241 */                     throw e;
/*     */                   }
/*     */                   
/* 244 */                   im = new byte[(int)size[0]];
/* 245 */                   s.seek(offset[0]);
/* 246 */                   s.readFully(im);
/* 247 */                   RawImageHelper.updateRawImageParameters(tiff.image, w, h, false, imagecomp, parameters, im, null);
/* 248 */                   tiff.image.setInverted(true);
/* 249 */                   tiff.image.setDpi(dpiX, dpiY);
/* 250 */                   tiff.image.setXYRatio(XYRatio);
/* 251 */                   if (rotation != 0.0F)
/* 252 */                     tiff.image.setRotation(rotation); 
/*     */                   return;
/*     */                 } 
/*     */               } 
/* 256 */               g4.fax4Encode(outBuf, height);
/*     */               break;
/*     */             case 4:
/*     */               try {
/* 260 */                 decoder.decodeT6(outBuf, im, 0, height, tiffT6Options);
/* 261 */               } catch (IOException e) {
/* 262 */                 if (!recoverFromImageError) {
/* 263 */                   throw e;
/*     */                 }
/*     */               } 
/* 266 */               g4.fax4Encode(outBuf, height);
/*     */               break;
/*     */           } 
/* 269 */           rowsLeft -= rowsStrip;
/*     */         } 
/* 271 */         byte[] g4pic = g4.close();
/* 272 */         RawImageHelper.updateRawImageParameters(tiff.image, w, h, false, 256, parameters & 0x1, g4pic, null);
/*     */       } 
/*     */       
/* 275 */       tiff.image.setDpi(dpiX, dpiY);
/* 276 */       if (dir.isTagPresent(34675)) {
/*     */         try {
/* 278 */           TIFFField fd = dir.getField(34675);
/* 279 */           IccProfile icc_prof = IccProfile.getInstance(fd.getAsBytes());
/* 280 */           if (icc_prof.getNumComponents() == 1)
/* 281 */             tiff.image.setProfile(icc_prof); 
/* 282 */         } catch (RuntimeException runtimeException) {}
/*     */       }
/*     */ 
/*     */       
/* 286 */       if (rotation != 0.0F)
/* 287 */         tiff.image.setRotation(rotation); 
/* 288 */     } catch (Exception e) {
/* 289 */       throw new IOException("Cannot read TIFF image.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processTiffImageColor(TIFFDirectory dir, RandomAccessFileOrArray s, TiffParameters tiff) {
/*     */     try {
/* 295 */       int compression = 1;
/* 296 */       if (dir.isTagPresent(259)) {
/* 297 */         compression = (int)dir.getFieldAsLong(259);
/*     */       }
/* 299 */       int predictor = 1;
/* 300 */       TIFFLZWDecoder lzwDecoder = null;
/* 301 */       switch (compression) {
/*     */         case 1:
/*     */         case 5:
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/*     */         case 32773:
/*     */         case 32946:
/*     */           break;
/*     */         default:
/* 311 */           throw (new IOException("Compression {0} is not supported.")).setMessageParams(new Object[] { Integer.valueOf(compression) });
/*     */       } 
/* 313 */       int photometric = (int)dir.getFieldAsLong(262);
/* 314 */       switch (photometric) {
/*     */         case 0:
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 5:
/*     */           break;
/*     */         default:
/* 322 */           if (compression != 6 && compression != 7)
/* 323 */             throw (new IOException("Photometric {0} is not supported.")).setMessageParams(new Object[] { Integer.valueOf(photometric) });  break;
/*     */       } 
/* 325 */       float rotation = 0.0F;
/* 326 */       if (dir.isTagPresent(274)) {
/* 327 */         int rot = (int)dir.getFieldAsLong(274);
/* 328 */         if (rot == 3 || rot == 4) {
/* 329 */           rotation = 3.1415927F;
/* 330 */         } else if (rot == 5 || rot == 8) {
/* 331 */           rotation = 1.5707964F;
/* 332 */         } else if (rot == 6 || rot == 7) {
/* 333 */           rotation = -1.5707964F;
/*     */         } 
/* 335 */       }  if (dir.isTagPresent(284) && dir
/* 336 */         .getFieldAsLong(284) == 2L)
/* 337 */         throw new IOException("Planar images are not supported."); 
/* 338 */       int extraSamples = 0;
/* 339 */       if (dir.isTagPresent(338))
/* 340 */         extraSamples = 1; 
/* 341 */       int samplePerPixel = 1;
/*     */ 
/*     */       
/* 344 */       if (dir.isTagPresent(277))
/* 345 */         samplePerPixel = (int)dir.getFieldAsLong(277); 
/* 346 */       int bitsPerSample = 1;
/* 347 */       if (dir.isTagPresent(258))
/* 348 */         bitsPerSample = (int)dir.getFieldAsLong(258); 
/* 349 */       switch (bitsPerSample) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 4:
/*     */         case 8:
/*     */           break;
/*     */         default:
/* 356 */           throw (new IOException("Bits per sample {0} is not supported.")).setMessageParams(new Object[] { Integer.valueOf(bitsPerSample) });
/*     */       } 
/* 358 */       int h = (int)dir.getFieldAsLong(257);
/* 359 */       int w = (int)dir.getFieldAsLong(256);
/*     */ 
/*     */       
/* 362 */       int resolutionUnit = 2;
/* 363 */       if (dir.isTagPresent(296))
/* 364 */         resolutionUnit = (int)dir.getFieldAsLong(296); 
/* 365 */       int dpiX = getDpi(dir.getField(282), resolutionUnit);
/* 366 */       int dpiY = getDpi(dir.getField(283), resolutionUnit);
/* 367 */       int fillOrder = 1;
/* 368 */       TIFFField fillOrderField = dir.getField(266);
/* 369 */       if (fillOrderField != null)
/* 370 */         fillOrder = fillOrderField.getAsInt(0); 
/* 371 */       boolean reverse = (fillOrder == 2);
/* 372 */       int rowsStrip = h;
/*     */ 
/*     */       
/* 375 */       if (dir.isTagPresent(278))
/* 376 */         rowsStrip = (int)dir.getFieldAsLong(278); 
/* 377 */       if (rowsStrip <= 0 || rowsStrip > h)
/* 378 */         rowsStrip = h; 
/* 379 */       long[] offset = getArrayLongShort(dir, 273);
/* 380 */       long[] size = getArrayLongShort(dir, 279);
/*     */ 
/*     */       
/* 383 */       if ((size == null || (size.length == 1 && (size[0] == 0L || size[0] + offset[0] > s.length()))) && h == rowsStrip) {
/* 384 */         size = new long[] { s.length() - (int)offset[0] };
/*     */       }
/* 386 */       if (compression == 5 || compression == 32946 || compression == 8) {
/* 387 */         TIFFField predictorField = dir.getField(317);
/* 388 */         if (predictorField != null) {
/* 389 */           predictor = predictorField.getAsInt(0);
/* 390 */           if (predictor != 1 && predictor != 2) {
/* 391 */             throw new IOException("Illegal value for predictor in TIFF file.");
/*     */           }
/* 393 */           if (predictor == 2 && bitsPerSample != 8) {
/* 394 */             throw (new IOException("{0} bit samples are not supported for horizontal differencing predictor.")).setMessageParams(new Object[] { Integer.valueOf(bitsPerSample) });
/*     */           }
/*     */         } 
/*     */       } 
/* 398 */       if (compression == 5) {
/* 399 */         lzwDecoder = new TIFFLZWDecoder(w, predictor, samplePerPixel);
/*     */       }
/* 401 */       int rowsLeft = h;
/* 402 */       ByteArrayOutputStream stream = null;
/* 403 */       ByteArrayOutputStream mstream = null;
/* 404 */       DeflaterOutputStream zip = null;
/* 405 */       DeflaterOutputStream mzip = null;
/* 406 */       if (extraSamples > 0) {
/* 407 */         mstream = new ByteArrayOutputStream();
/* 408 */         mzip = new DeflaterOutputStream((OutputStream)mstream);
/*     */       } 
/*     */       
/* 411 */       CCITTG4Encoder g4 = null;
/* 412 */       if (bitsPerSample == 1 && samplePerPixel == 1 && photometric != 3) {
/* 413 */         g4 = new CCITTG4Encoder(w);
/*     */       } else {
/* 415 */         stream = new ByteArrayOutputStream();
/* 416 */         if (compression != 6 && compression != 7)
/* 417 */           zip = new DeflaterOutputStream((OutputStream)stream); 
/*     */       } 
/* 419 */       if (compression == 6) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 424 */         if (!dir.isTagPresent(513)) {
/* 425 */           throw new IOException("Missing tag(s) for OJPEG compression");
/*     */         }
/* 427 */         int jpegOffset = (int)dir.getFieldAsLong(513);
/* 428 */         int jpegLength = (int)s.length() - jpegOffset;
/*     */         
/* 430 */         if (dir.isTagPresent(514)) {
/* 431 */           jpegLength = (int)dir.getFieldAsLong(514) + (int)size[0];
/*     */         }
/*     */ 
/*     */         
/* 435 */         byte[] jpeg = new byte[Math.min(jpegLength, (int)s.length() - jpegOffset)];
/*     */         
/* 437 */         int posFilePointer = (int)s.getPosition();
/* 438 */         posFilePointer += jpegOffset;
/* 439 */         s.seek(posFilePointer);
/* 440 */         s.readFully(jpeg);
/* 441 */         tiff.image.data = jpeg;
/* 442 */         tiff.image.setOriginalType(ImageType.JPEG);
/* 443 */         JpegImageHelper.processImage(tiff.image);
/* 444 */         tiff.jpegProcessing = true;
/* 445 */       } else if (compression == 7) {
/* 446 */         if (size.length > 1)
/* 447 */           throw (new IOException("Compression jpeg is only supported with a single strip. This image has {0} strips.")).setMessageParams(new Object[] { Integer.valueOf(size.length) }); 
/* 448 */         byte[] jpeg = new byte[(int)size[0]];
/* 449 */         s.seek(offset[0]);
/* 450 */         s.readFully(jpeg);
/*     */ 
/*     */         
/* 453 */         TIFFField jpegtables = dir.getField(347);
/* 454 */         if (jpegtables != null) {
/* 455 */           byte[] temp = jpegtables.getAsBytes();
/* 456 */           int tableoffset = 0;
/* 457 */           int tablelength = temp.length;
/*     */           
/* 459 */           if (temp[0] == -1 && temp[1] == -40) {
/* 460 */             tableoffset = 2;
/* 461 */             tablelength -= 2;
/*     */           } 
/*     */           
/* 464 */           if (temp[temp.length - 2] == -1 && temp[temp.length - 1] == -39)
/* 465 */             tablelength -= 2; 
/* 466 */           byte[] tables = new byte[tablelength];
/* 467 */           System.arraycopy(temp, tableoffset, tables, 0, tablelength);
/*     */           
/* 469 */           byte[] jpegwithtables = new byte[jpeg.length + tables.length];
/* 470 */           System.arraycopy(jpeg, 0, jpegwithtables, 0, 2);
/* 471 */           System.arraycopy(tables, 0, jpegwithtables, 2, tables.length);
/* 472 */           System.arraycopy(jpeg, 2, jpegwithtables, tables.length + 2, jpeg.length - 2);
/* 473 */           jpeg = jpegwithtables;
/*     */         } 
/* 475 */         tiff.image.data = jpeg;
/* 476 */         tiff.image.setOriginalType(ImageType.JPEG);
/* 477 */         JpegImageHelper.processImage(tiff.image);
/* 478 */         tiff.jpegProcessing = true;
/* 479 */         if (photometric == 2) {
/* 480 */           tiff.image.setColorTransform(0);
/*     */         }
/*     */       } else {
/* 483 */         for (int k = 0; k < offset.length; k++) {
/* 484 */           byte[] im = new byte[(int)size[k]];
/* 485 */           s.seek(offset[k]);
/* 486 */           s.readFully(im);
/* 487 */           int height = Math.min(rowsStrip, rowsLeft);
/* 488 */           byte[] outBuf = null;
/* 489 */           if (compression != 1)
/* 490 */             outBuf = new byte[(w * bitsPerSample * samplePerPixel + 7) / 8 * height]; 
/* 491 */           if (reverse)
/* 492 */             TIFFFaxDecoder.reverseBits(im); 
/* 493 */           switch (compression) {
/*     */             case 8:
/*     */             case 32946:
/* 496 */               FilterUtil.inflateData(im, outBuf);
/* 497 */               applyPredictor(outBuf, predictor, w, height, samplePerPixel);
/*     */               break;
/*     */             case 1:
/* 500 */               outBuf = im;
/*     */               break;
/*     */             case 32773:
/* 503 */               decodePackbits(im, outBuf);
/*     */               break;
/*     */             case 5:
/* 506 */               lzwDecoder.decode(im, outBuf, height);
/*     */               break;
/*     */           } 
/* 509 */           if (bitsPerSample == 1 && samplePerPixel == 1 && photometric != 3) {
/* 510 */             g4.fax4Encode(outBuf, height);
/*     */           }
/* 512 */           else if (extraSamples > 0) {
/* 513 */             processExtraSamples(zip, mzip, outBuf, samplePerPixel, bitsPerSample, w, height);
/*     */           } else {
/* 515 */             zip.write(outBuf);
/*     */           } 
/* 517 */           rowsLeft -= rowsStrip;
/*     */         } 
/* 519 */         if (bitsPerSample == 1 && samplePerPixel == 1 && photometric != 3) {
/* 520 */           RawImageHelper.updateRawImageParameters(tiff.image, w, h, false, 256, (photometric == 1) ? 1 : 0, g4
/* 521 */               .close(), null);
/*     */         } else {
/* 523 */           zip.close();
/* 524 */           RawImageHelper.updateRawImageParameters(tiff.image, w, h, samplePerPixel - extraSamples, bitsPerSample, stream.toByteArray());
/* 525 */           tiff.image.setDeflated(true);
/*     */         } 
/*     */       } 
/* 528 */       tiff.image.setDpi(dpiX, dpiY);
/* 529 */       if (compression != 6 && compression != 7) {
/* 530 */         if (dir.isTagPresent(34675)) {
/*     */           try {
/* 532 */             TIFFField fd = dir.getField(34675);
/* 533 */             IccProfile icc_prof = IccProfile.getInstance(fd.getAsBytes());
/* 534 */             if (samplePerPixel - extraSamples == icc_prof.getNumComponents()) {
/* 535 */               tiff.image.setProfile(icc_prof);
/*     */             }
/* 537 */           } catch (RuntimeException runtimeException) {}
/*     */         }
/*     */ 
/*     */         
/* 541 */         if (dir.isTagPresent(320)) {
/* 542 */           TIFFField fd = dir.getField(320);
/* 543 */           char[] rgb = fd.getAsChars();
/* 544 */           byte[] palette = new byte[rgb.length];
/* 545 */           int gColor = rgb.length / 3;
/* 546 */           int bColor = gColor * 2;
/* 547 */           for (int k = 0; k < gColor; k++) {
/*     */             
/* 549 */             palette[k * 3] = (byte)(rgb[k] >> 8);
/* 550 */             palette[k * 3 + 1] = (byte)(rgb[k + gColor] >> 8);
/* 551 */             palette[k * 3 + 2] = (byte)(rgb[k + bColor] >> 8);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 556 */           boolean colormapBroken = true; int i;
/* 557 */           for (i = 0; i < palette.length; i++) {
/* 558 */             if (palette[i] != 0) {
/* 559 */               colormapBroken = false;
/*     */               break;
/*     */             } 
/*     */           } 
/* 563 */           if (colormapBroken) {
/* 564 */             for (i = 0; i < gColor; i++) {
/* 565 */               palette[i * 3] = (byte)rgb[i];
/* 566 */               palette[i * 3 + 1] = (byte)rgb[i + gColor];
/* 567 */               palette[i * 3 + 2] = (byte)rgb[i + bColor];
/*     */             } 
/*     */           }
/* 570 */           Object[] indexed = new Object[4];
/* 571 */           indexed[0] = "/Indexed";
/* 572 */           indexed[1] = "/DeviceRGB";
/* 573 */           indexed[2] = Integer.valueOf(gColor - 1);
/* 574 */           indexed[3] = PdfEncodings.convertToString(palette, null);
/* 575 */           tiff.additional = new HashMap<>();
/* 576 */           tiff.additional.put("ColorSpace", indexed);
/*     */         } 
/*     */       } 
/* 579 */       if (photometric == 0)
/* 580 */         tiff.image.setInverted(true); 
/* 581 */       if (rotation != 0.0F)
/* 582 */         tiff.image.setRotation(rotation); 
/* 583 */       if (extraSamples > 0) {
/* 584 */         mzip.close();
/* 585 */         RawImageData mimg = (RawImageData)ImageDataFactory.createRawImage(null);
/* 586 */         RawImageHelper.updateRawImageParameters(mimg, w, h, 1, bitsPerSample, mstream.toByteArray());
/* 587 */         mimg.makeMask();
/* 588 */         mimg.setDeflated(true);
/* 589 */         tiff.image.setImageMask(mimg);
/*     */       } 
/* 591 */     } catch (Exception e) {
/* 592 */       throw new IOException("Cannot get TIFF image color.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getDpi(TIFFField fd, int resolutionUnit) {
/* 597 */     if (fd == null)
/* 598 */       return 0; 
/* 599 */     long[] res = fd.getAsRational(0);
/* 600 */     float frac = (float)res[0] / (float)res[1];
/* 601 */     int dpi = 0;
/* 602 */     switch (resolutionUnit) {
/*     */       case 1:
/*     */       case 2:
/* 605 */         dpi = (int)(frac + 0.5D);
/*     */         break;
/*     */       case 3:
/* 608 */         dpi = (int)(frac * 2.54D + 0.5D);
/*     */         break;
/*     */     } 
/* 611 */     return dpi;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processExtraSamples(DeflaterOutputStream zip, DeflaterOutputStream mzip, byte[] outBuf, int samplePerPixel, int bitsPerSample, int width, int height) throws IOException {
/* 616 */     if (bitsPerSample == 8) {
/* 617 */       byte[] mask = new byte[width * height];
/* 618 */       int mptr = 0;
/* 619 */       int optr = 0;
/* 620 */       int total = width * height * samplePerPixel; int k;
/* 621 */       for (k = 0; k < total; k += samplePerPixel) {
/* 622 */         for (int s = 0; s < samplePerPixel - 1; s++) {
/* 623 */           outBuf[optr++] = outBuf[k + s];
/*     */         }
/* 625 */         mask[mptr++] = outBuf[k + samplePerPixel - 1];
/*     */       } 
/* 627 */       zip.write(outBuf, 0, optr);
/* 628 */       mzip.write(mask, 0, mptr);
/*     */     } else {
/* 630 */       throw new IOException("Extra samples are not supported.");
/*     */     } 
/*     */   } private static long[] getArrayLongShort(TIFFDirectory dir, int tag) {
/*     */     long[] offset;
/* 634 */     TIFFField field = dir.getField(tag);
/* 635 */     if (field == null) {
/* 636 */       return null;
/*     */     }
/* 638 */     if (field.getType() == 4) {
/* 639 */       offset = field.getAsLongs();
/*     */     }
/*     */     else {
/*     */       
/* 643 */       char[] temp = field.getAsChars();
/* 644 */       offset = new long[temp.length];
/* 645 */       for (int k = 0; k < temp.length; k++)
/* 646 */         offset[k] = temp[k]; 
/*     */     } 
/* 648 */     return offset;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void decodePackbits(byte[] data, byte[] dst) {
/* 653 */     int srcCount = 0, dstCount = 0;
/*     */     
/*     */     try {
/* 656 */       while (dstCount < dst.length) {
/* 657 */         byte b = data[srcCount++];
/*     */ 
/*     */         
/* 660 */         if (b >= 0 && b <= Byte.MAX_VALUE) {
/*     */           
/* 662 */           for (int i = 0; i < b + 1; i++) {
/* 663 */             dst[dstCount++] = data[srcCount++];
/*     */           }
/*     */           continue;
/*     */         } 
/* 667 */         if ((b & 0x80) != 0 && b != Byte.MIN_VALUE) {
/*     */           
/* 669 */           byte repeat = data[srcCount++];
/*     */ 
/*     */ 
/*     */           
/* 673 */           for (int i = 0; i < ((b ^ 0xFFFFFFFF) & 0xFF) + 2; i++) {
/* 674 */             dst[dstCount++] = repeat;
/*     */           }
/*     */           continue;
/*     */         } 
/* 678 */         srcCount++;
/*     */       }
/*     */     
/* 681 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyPredictor(byte[] uncompData, int predictor, int w, int h, int samplesPerPixel) {
/* 687 */     if (predictor != 2) {
/*     */       return;
/*     */     }
/* 690 */     for (int j = 0; j < h; j++) {
/* 691 */       int count = samplesPerPixel * (j * w + 1);
/* 692 */       for (int i = samplesPerPixel; i < w * samplesPerPixel; i++) {
/* 693 */         uncompData[count] = (byte)(uncompData[count] + uncompData[count - samplesPerPixel]);
/* 694 */         count++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/TiffImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */