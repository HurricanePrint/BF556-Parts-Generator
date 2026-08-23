/*     */ package com.itextpdf.kernel.pdf.xobject;
/*     */ 
/*     */ import com.itextpdf.io.colors.IccProfile;
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import com.itextpdf.io.image.ImageData;
/*     */ import com.itextpdf.io.image.ImageType;
/*     */ import com.itextpdf.io.image.PngChromaticities;
/*     */ import com.itextpdf.io.image.PngImageData;
/*     */ import com.itextpdf.io.image.RawImageData;
/*     */ import com.itextpdf.io.image.RawImageHelper;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfLiteral;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfReader;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
/*     */ import com.itextpdf.kernel.pdf.filters.DoNothingFilter;
/*     */ import com.itextpdf.kernel.pdf.filters.FilterHandlers;
/*     */ import com.itextpdf.kernel.pdf.filters.IFilterHandler;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfImageXObject
/*     */   extends PdfXObject
/*     */ {
/*     */   private static final long serialVersionUID = -205889576153966580L;
/*     */   private float width;
/*     */   private float height;
/*     */   private boolean mask;
/*     */   private boolean softMask;
/*     */   
/*     */   public PdfImageXObject(ImageData image) {
/* 106 */     this(image, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfImageXObject(ImageData image, PdfImageXObject imageMask) {
/* 116 */     this(createPdfStream(checkImageType(image), imageMask));
/* 117 */     this.mask = image.isMask();
/* 118 */     this.softMask = image.isSoftMask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfImageXObject(PdfStream pdfStream) {
/* 129 */     super(pdfStream);
/* 130 */     if (!pdfStream.isFlushed()) {
/* 131 */       initWidthField();
/* 132 */       initHeightField();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 143 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 153 */     return this.height;
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
/*     */   public void flush() {
/* 165 */     super.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfImageXObject copyTo(PdfDocument document) {
/* 175 */     PdfImageXObject image = new PdfImageXObject((PdfStream)((PdfStream)getPdfObject()).copyTo(document));
/* 176 */     image.mask = this.mask;
/* 177 */     image.softMask = this.softMask;
/* 178 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage getBufferedImage() throws IOException {
/* 188 */     byte[] img = getImageBytes();
/* 189 */     return ImageIO.read(new ByteArrayInputStream(img));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getImageBytes() {
/* 198 */     return getImageBytes(true);
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
/*     */   public byte[] getImageBytes(boolean decoded) {
/* 211 */     byte[] bytes = ((PdfStream)getPdfObject()).getBytes(false);
/* 212 */     if (decoded) {
/* 213 */       Map<PdfName, IFilterHandler> filters = new HashMap<>(FilterHandlers.getDefaultFilterHandlers());
/* 214 */       filters.put(PdfName.JBIG2Decode, new DoNothingFilter());
/* 215 */       bytes = PdfReader.decodeBytes(bytes, (PdfDictionary)getPdfObject(), filters);
/*     */       
/* 217 */       ImageType imageType = identifyImageType();
/* 218 */       if (imageType == ImageType.TIFF || imageType == ImageType.PNG) {
/*     */         try {
/* 220 */           bytes = (new ImagePdfBytesInfo(this)).decodeTiffAndPngBytes(bytes);
/* 221 */         } catch (IOException e) {
/* 222 */           throw new RuntimeException("IO exception in PdfImageXObject", e);
/*     */         } 
/*     */       }
/*     */     } 
/* 226 */     return bytes;
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
/*     */   public ImageType identifyImageType() {
/* 240 */     PdfObject filter = ((PdfStream)getPdfObject()).get(PdfName.Filter);
/* 241 */     PdfArray filters = new PdfArray();
/* 242 */     if (filter != null) {
/* 243 */       if (filter.getType() == 6) {
/* 244 */         filters.add(filter);
/* 245 */       } else if (filter.getType() == 1) {
/* 246 */         filters = (PdfArray)filter;
/*     */       } 
/*     */     }
/* 249 */     for (int i = filters.size() - 1; i >= 0; i--) {
/* 250 */       PdfName filterName = (PdfName)filters.get(i);
/* 251 */       if (PdfName.DCTDecode.equals(filterName))
/* 252 */         return ImageType.JPEG; 
/* 253 */       if (PdfName.JBIG2Decode.equals(filterName))
/* 254 */         return ImageType.JBIG2; 
/* 255 */       if (PdfName.JPXDecode.equals(filterName)) {
/* 256 */         return ImageType.JPEG2000;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 261 */     ImagePdfBytesInfo imageInfo = new ImagePdfBytesInfo(this);
/* 262 */     if (imageInfo.getPngColorType() < 0) {
/* 263 */       return ImageType.TIFF;
/*     */     }
/* 265 */     return ImageType.PNG;
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
/*     */   public String identifyImageFileExtension() {
/* 278 */     ImageType bytesType = identifyImageType();
/* 279 */     switch (bytesType) {
/*     */       case PNG:
/* 281 */         return "png";
/*     */       case JPEG:
/* 283 */         return "jpg";
/*     */       case JPEG2000:
/* 285 */         return "jp2";
/*     */       case TIFF:
/* 287 */         return "tif";
/*     */       case JBIG2:
/* 289 */         return "jbig2";
/*     */     } 
/* 291 */     throw new IllegalStateException("Should have never happened. This type of image is not allowed for ImageXObject");
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
/*     */   public PdfImageXObject put(PdfName key, PdfObject value) {
/* 304 */     ((PdfStream)getPdfObject()).put(key, value);
/* 305 */     return this;
/*     */   }
/*     */   
/*     */   private float initWidthField() {
/* 309 */     PdfNumber wNum = ((PdfStream)getPdfObject()).getAsNumber(PdfName.Width);
/* 310 */     if (wNum != null) {
/* 311 */       this.width = wNum.floatValue();
/*     */     }
/* 313 */     return this.width;
/*     */   }
/*     */   
/*     */   private float initHeightField() {
/* 317 */     PdfNumber hNum = ((PdfStream)getPdfObject()).getAsNumber(PdfName.Height);
/* 318 */     if (hNum != null) {
/* 319 */       this.height = hNum.floatValue();
/*     */     }
/* 321 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   private static PdfStream createPdfStream(ImageData image, PdfImageXObject imageMask) {
/* 326 */     if (image.getOriginalType() == ImageType.RAW) {
/* 327 */       RawImageHelper.updateImageAttributes((RawImageData)image, null);
/*     */     }
/* 329 */     PdfStream stream = new PdfStream(image.getData());
/* 330 */     String filter = image.getFilter();
/* 331 */     if (filter != null && "JPXDecode".equals(filter) && image.getColorSpace() <= 0) {
/* 332 */       stream.setCompressionLevel(0);
/* 333 */       image.setBpc(0);
/*     */     } 
/* 335 */     stream.put(PdfName.Type, (PdfObject)PdfName.XObject);
/* 336 */     stream.put(PdfName.Subtype, (PdfObject)PdfName.Image);
/* 337 */     PdfDictionary decodeParms = createDictionaryFromMap(stream, image.getDecodeParms());
/* 338 */     if (decodeParms != null) {
/* 339 */       stream.put(PdfName.DecodeParms, (PdfObject)decodeParms);
/*     */     }
/*     */     
/* 342 */     if (!(image instanceof PngImageData)) {
/*     */       PdfName colorSpace;
/* 344 */       switch (image.getColorSpace()) {
/*     */         case 1:
/* 346 */           colorSpace = PdfName.DeviceGray;
/*     */           break;
/*     */         case 3:
/* 349 */           colorSpace = PdfName.DeviceRGB;
/*     */           break;
/*     */         default:
/* 352 */           colorSpace = PdfName.DeviceCMYK; break;
/*     */       } 
/* 354 */       stream.put(PdfName.ColorSpace, (PdfObject)colorSpace);
/*     */     } 
/*     */     
/* 357 */     if (image.getBpc() != 0) {
/* 358 */       stream.put(PdfName.BitsPerComponent, (PdfObject)new PdfNumber(image.getBpc()));
/*     */     }
/*     */     
/* 361 */     if (image.getFilter() != null) {
/* 362 */       stream.put(PdfName.Filter, (PdfObject)new PdfName(image.getFilter()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     if (image.getColorSpace() == -1) {
/* 369 */       stream.remove(PdfName.ColorSpace);
/*     */     }
/*     */     
/* 372 */     PdfDictionary additional = null;
/* 373 */     if (image instanceof PngImageData) {
/* 374 */       PngImageData pngImage = (PngImageData)image;
/*     */       
/* 376 */       if (pngImage.isIndexed()) {
/* 377 */         PdfArray colorspace = new PdfArray();
/* 378 */         colorspace.add((PdfObject)PdfName.Indexed);
/* 379 */         colorspace.add(getColorSpaceInfo(pngImage));
/*     */         
/* 381 */         if (pngImage.getColorPalette() != null && (pngImage.getColorPalette()).length > 0)
/*     */         {
/*     */           
/* 384 */           colorspace.add((PdfObject)new PdfNumber((pngImage.getColorPalette()).length / 3 - 1));
/*     */         }
/*     */         
/* 387 */         if (pngImage.getColorPalette() != null) {
/* 388 */           colorspace.add((PdfObject)new PdfString(
/* 389 */                 PdfEncodings.convertToString(pngImage.getColorPalette(), null)));
/*     */         }
/*     */         
/* 392 */         stream.put(PdfName.ColorSpace, (PdfObject)colorspace);
/*     */       } else {
/* 394 */         stream.put(PdfName.ColorSpace, getColorSpaceInfo(pngImage));
/*     */       } 
/*     */     } 
/* 397 */     additional = createDictionaryFromMap(stream, image.getImageAttributes());
/*     */     
/* 399 */     if (additional != null) {
/* 400 */       stream.putAll(additional);
/*     */     }
/*     */     
/* 403 */     IccProfile iccProfile = image.getProfile();
/* 404 */     if (iccProfile != null) {
/* 405 */       PdfStream iccProfileStream = PdfCieBasedCs.IccBased.getIccProfileStream(iccProfile);
/* 406 */       PdfArray iccBasedColorSpace = new PdfArray();
/* 407 */       iccBasedColorSpace.add((PdfObject)PdfName.ICCBased);
/* 408 */       iccBasedColorSpace.add((PdfObject)iccProfileStream);
/* 409 */       PdfObject colorSpaceObject = stream.get(PdfName.ColorSpace);
/* 410 */       boolean iccProfileShouldBeApplied = true;
/* 411 */       if (colorSpaceObject != null) {
/* 412 */         PdfColorSpace cs = PdfColorSpace.makeColorSpace(colorSpaceObject);
/* 413 */         if (cs == null) {
/* 414 */           LoggerFactory.getLogger(PdfImageXObject.class).error("Image has incorrect or unsupported color space, that will be overridden by one based on embedded icc profile.");
/* 415 */         } else if (cs instanceof PdfSpecialCs.Indexed) {
/* 416 */           PdfColorSpace baseCs = ((PdfSpecialCs.Indexed)cs).getBaseCs();
/* 417 */           if (baseCs == null) {
/* 418 */             LoggerFactory.getLogger(PdfImageXObject.class).error("Image has incorrect or unsupported base color space in indexed color space, it will be overridden by one based on embedded icc profile.");
/* 419 */           } else if (baseCs.getNumberOfComponents() != iccProfile.getNumComponents()) {
/* 420 */             LoggerFactory.getLogger(PdfImageXObject.class).error("Image has icc profile with incompatible number of color components compared to base color space in image indexed color space. The icc profile will be ignored.");
/* 421 */             iccProfileShouldBeApplied = false;
/*     */           } else {
/* 423 */             iccProfileStream.put(PdfName.Alternate, baseCs.getPdfObject());
/*     */           } 
/* 425 */           if (iccProfileShouldBeApplied) {
/* 426 */             ((PdfArray)colorSpaceObject).set(1, (PdfObject)iccBasedColorSpace);
/* 427 */             iccProfileShouldBeApplied = false;
/*     */           } 
/* 429 */         } else if (cs.getNumberOfComponents() != iccProfile.getNumComponents()) {
/* 430 */           LoggerFactory.getLogger(PdfImageXObject.class).error("Image has icc profile with incompatible number of color components compared to image color space. The icc profile will be ignored.");
/* 431 */           iccProfileShouldBeApplied = false;
/*     */         } else {
/* 433 */           iccProfileStream.put(PdfName.Alternate, colorSpaceObject);
/*     */         } 
/*     */       } 
/* 436 */       if (iccProfileShouldBeApplied) {
/* 437 */         stream.put(PdfName.ColorSpace, (PdfObject)iccBasedColorSpace);
/*     */       }
/*     */     } 
/*     */     
/* 441 */     if (image.isMask() && (image.getBpc() == 1 || image.getBpc() > 255)) {
/* 442 */       stream.put(PdfName.ImageMask, (PdfObject)PdfBoolean.TRUE);
/*     */     }
/*     */     
/* 445 */     if (imageMask != null) {
/* 446 */       if (imageMask.softMask) {
/* 447 */         stream.put(PdfName.SMask, imageMask.getPdfObject());
/* 448 */       } else if (imageMask.mask) {
/* 449 */         stream.put(PdfName.Mask, imageMask.getPdfObject());
/*     */       } 
/*     */     }
/*     */     
/* 453 */     ImageData mask = image.getImageMask();
/* 454 */     if (mask != null) {
/* 455 */       if (mask.isSoftMask()) {
/* 456 */         stream.put(PdfName.SMask, (new PdfImageXObject(image.getImageMask())).getPdfObject());
/* 457 */       } else if (mask.isMask()) {
/* 458 */         stream.put(PdfName.Mask, (new PdfImageXObject(image.getImageMask())).getPdfObject());
/*     */       } 
/*     */     }
/*     */     
/* 462 */     if (image.getDecode() != null) {
/* 463 */       stream.put(PdfName.Decode, (PdfObject)new PdfArray(image.getDecode()));
/*     */     }
/* 465 */     if (image.isMask() && image.isInverted()) {
/* 466 */       stream.put(PdfName.Decode, (PdfObject)new PdfArray(new float[] { 1.0F, 0.0F }));
/*     */     }
/* 468 */     if (image.isInterpolation()) {
/* 469 */       stream.put(PdfName.Interpolate, (PdfObject)PdfBoolean.TRUE);
/*     */     }
/*     */     
/* 472 */     int[] transparency = image.getTransparency();
/* 473 */     if (transparency != null && !image.isMask() && imageMask == null) {
/* 474 */       PdfArray t = new PdfArray();
/* 475 */       for (int transparencyItem : transparency) {
/* 476 */         t.add((PdfObject)new PdfNumber(transparencyItem));
/*     */       }
/* 478 */       stream.put(PdfName.Mask, (PdfObject)t);
/*     */     } 
/*     */     
/* 481 */     stream.put(PdfName.Width, (PdfObject)new PdfNumber(image.getWidth()));
/* 482 */     stream.put(PdfName.Height, (PdfObject)new PdfNumber(image.getHeight()));
/* 483 */     return stream;
/*     */   }
/*     */   
/*     */   private static PdfDictionary createDictionaryFromMap(PdfStream stream, Map<String, Object> parms) {
/* 487 */     if (parms != null) {
/* 488 */       PdfDictionary dictionary = new PdfDictionary();
/* 489 */       for (Map.Entry<String, Object> entry : parms.entrySet()) {
/* 490 */         Object value = entry.getValue();
/* 491 */         String key = entry.getKey();
/* 492 */         if (value instanceof Integer) {
/* 493 */           dictionary.put(new PdfName(key), (PdfObject)new PdfNumber(((Integer)value).intValue())); continue;
/* 494 */         }  if (value instanceof Float) {
/* 495 */           dictionary.put(new PdfName(key), (PdfObject)new PdfNumber(((Float)value).floatValue())); continue;
/* 496 */         }  if (value instanceof String) {
/* 497 */           if (value.equals("Mask")) {
/* 498 */             dictionary.put(PdfName.Mask, (PdfObject)new PdfLiteral((String)value)); continue;
/*     */           } 
/* 500 */           String str = (String)value;
/* 501 */           if (str.indexOf('/') == 0) {
/* 502 */             dictionary.put(new PdfName(key), (PdfObject)new PdfName(str.substring(1))); continue;
/*     */           } 
/* 504 */           dictionary.put(new PdfName(key), (PdfObject)new PdfString(str));
/*     */           continue;
/*     */         } 
/* 507 */         if (value instanceof byte[]) {
/*     */           
/* 509 */           PdfStream globalsStream = new PdfStream();
/* 510 */           globalsStream.getOutputStream().writeBytes((byte[])value);
/* 511 */           dictionary.put(PdfName.JBIG2Globals, (PdfObject)globalsStream); continue;
/* 512 */         }  if (value instanceof Boolean) {
/* 513 */           dictionary.put(new PdfName(key), (PdfObject)PdfBoolean.valueOf(((Boolean)value).booleanValue())); continue;
/* 514 */         }  if (value instanceof Object[]) {
/* 515 */           dictionary.put(new PdfName(key), (PdfObject)createArray(stream, (Object[])value)); continue;
/* 516 */         }  if (value instanceof float[]) {
/* 517 */           dictionary.put(new PdfName(key), (PdfObject)new PdfArray((float[])value)); continue;
/* 518 */         }  if (value instanceof int[]) {
/* 519 */           dictionary.put(new PdfName(key), (PdfObject)new PdfArray((int[])value));
/*     */         }
/*     */       } 
/* 522 */       return dictionary;
/*     */     } 
/* 524 */     return null;
/*     */   }
/*     */   
/*     */   private static PdfArray createArray(PdfStream stream, Object[] objects) {
/* 528 */     PdfArray array = new PdfArray();
/* 529 */     for (Object obj : objects) {
/* 530 */       if (obj instanceof String) {
/* 531 */         String str = (String)obj;
/* 532 */         if (str.indexOf('/') == 0) {
/* 533 */           array.add((PdfObject)new PdfName(str.substring(1)));
/*     */         } else {
/* 535 */           array.add((PdfObject)new PdfString(str));
/*     */         } 
/* 537 */       } else if (obj instanceof Integer) {
/* 538 */         array.add((PdfObject)new PdfNumber(((Integer)obj).intValue()));
/* 539 */       } else if (obj instanceof Float) {
/* 540 */         array.add((PdfObject)new PdfNumber(((Float)obj).floatValue()));
/* 541 */       } else if (obj instanceof Object[]) {
/* 542 */         array.add((PdfObject)createArray(stream, (Object[])obj));
/*     */       } else {
/*     */         
/* 545 */         array.add((PdfObject)createDictionaryFromMap(stream, (Map<String, Object>)obj));
/*     */       } 
/*     */     } 
/* 548 */     return array;
/*     */   }
/*     */   
/*     */   private static ImageData checkImageType(ImageData image) {
/* 552 */     if (image instanceof com.itextpdf.kernel.pdf.canvas.wmf.WmfImageData) {
/* 553 */       throw new PdfException("Cannot create PdfImageXObject instance by WmfImage. Use PdfFormXObject constructor instead.");
/*     */     }
/* 555 */     return image;
/*     */   }
/*     */   
/*     */   private static PdfObject getColorSpaceInfo(PngImageData pngImageData) {
/* 559 */     if (pngImageData.getProfile() != null) {
/* 560 */       if (pngImageData.isGrayscaleImage()) {
/* 561 */         return (PdfObject)PdfName.DeviceGray;
/*     */       }
/* 563 */       return (PdfObject)PdfName.DeviceRGB;
/*     */     } 
/*     */     
/* 566 */     if (pngImageData.getGamma() == 1.0F && !pngImageData.isHasCHRM()) {
/* 567 */       if (pngImageData.isGrayscaleImage()) {
/* 568 */         return (PdfObject)PdfName.DeviceGray;
/*     */       }
/* 570 */       return (PdfObject)PdfName.DeviceRGB;
/*     */     } 
/*     */     
/* 573 */     PdfArray array = new PdfArray();
/* 574 */     PdfDictionary map = new PdfDictionary();
/* 575 */     if (pngImageData.isGrayscaleImage()) {
/* 576 */       if (pngImageData.getGamma() == 1.0F) {
/* 577 */         return (PdfObject)PdfName.DeviceGray;
/*     */       }
/* 579 */       array.add((PdfObject)PdfName.CalGray);
/* 580 */       map.put(PdfName.Gamma, (PdfObject)new PdfNumber(pngImageData.getGamma()));
/* 581 */       map.put(PdfName.WhitePoint, (PdfObject)new PdfArray(new int[] { 1, 1, 1 }));
/*     */     } else {
/* 583 */       float[] wp = { 1.0F, 1.0F, 1.0F };
/* 584 */       array.add((PdfObject)PdfName.CalRGB);
/* 585 */       float gamma = pngImageData.getGamma();
/* 586 */       if (gamma != 1.0F) {
/* 587 */         float[] gm = new float[3];
/* 588 */         gm[0] = gamma;
/* 589 */         gm[1] = gamma;
/* 590 */         gm[2] = gamma;
/* 591 */         map.put(PdfName.Gamma, (PdfObject)new PdfArray(gm));
/*     */       } 
/* 593 */       if (pngImageData.isHasCHRM()) {
/* 594 */         PngChromaticitiesHelper helper = new PngChromaticitiesHelper();
/* 595 */         helper.constructMatrix(pngImageData);
/* 596 */         wp = helper.wp;
/* 597 */         map.put(PdfName.Matrix, (PdfObject)new PdfArray(helper.matrix));
/*     */       } 
/* 599 */       map.put(PdfName.WhitePoint, (PdfObject)new PdfArray(wp));
/*     */     } 
/* 601 */     array.add((PdfObject)map);
/* 602 */     return (PdfObject)array;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class PngChromaticitiesHelper
/*     */   {
/* 608 */     float[] matrix = new float[9];
/* 609 */     float[] wp = new float[3];
/*     */     
/*     */     public void constructMatrix(PngImageData pngImageData) {
/* 612 */       PngChromaticities pngChromaticities = pngImageData.getPngChromaticities();
/*     */ 
/*     */ 
/*     */       
/* 616 */       float z = pngChromaticities.getYW() * ((pngChromaticities.getXG() - pngChromaticities.getXB()) * pngChromaticities.getYR() - (pngChromaticities.getXR() - pngChromaticities.getXB()) * pngChromaticities.getYG() + (pngChromaticities.getXR() - pngChromaticities.getXG()) * pngChromaticities.getYB());
/*     */ 
/*     */ 
/*     */       
/* 620 */       float YA = pngChromaticities.getYR() * ((pngChromaticities.getXG() - pngChromaticities.getXB()) * pngChromaticities.getYW() - (pngChromaticities.getXW() - pngChromaticities.getXB()) * pngChromaticities.getYG() + (pngChromaticities.getXW() - pngChromaticities.getXG()) * pngChromaticities.getYB()) / z;
/* 621 */       float XA = YA * pngChromaticities.getXR() / pngChromaticities.getYR();
/* 622 */       float ZA = YA * ((1.0F - pngChromaticities.getXR()) / pngChromaticities.getYR() - 1.0F);
/*     */ 
/*     */ 
/*     */       
/* 626 */       float YB = -pngChromaticities.getYG() * ((pngChromaticities.getXR() - pngChromaticities.getXB()) * pngChromaticities.getYW() - (pngChromaticities.getXW() - pngChromaticities.getXB()) * pngChromaticities.getYR() + (pngChromaticities.getXW() - pngChromaticities.getXR()) * pngChromaticities.getYB()) / z;
/* 627 */       float XB = YB * pngChromaticities.getXG() / pngChromaticities.getYG();
/* 628 */       float ZB = YB * ((1.0F - pngChromaticities.getXG()) / pngChromaticities.getYG() - 1.0F);
/*     */ 
/*     */ 
/*     */       
/* 632 */       float YC = pngChromaticities.getYB() * ((pngChromaticities.getXR() - pngChromaticities.getXG()) * pngChromaticities.getYW() - (pngChromaticities.getXW() - pngChromaticities.getXG()) * pngChromaticities.getYW() + (pngChromaticities.getXW() - pngChromaticities.getXR()) * pngChromaticities.getYG()) / z;
/* 633 */       float XC = YC * pngChromaticities.getXB() / pngChromaticities.getYB();
/* 634 */       float ZC = YC * ((1.0F - pngChromaticities.getXB()) / pngChromaticities.getYB() - 1.0F);
/* 635 */       float XW = XA + XB + XC;
/* 636 */       float YW = 1.0F;
/* 637 */       float ZW = ZA + ZB + ZC;
/* 638 */       float[] wpa = new float[3];
/* 639 */       wpa[0] = XW;
/* 640 */       wpa[1] = YW;
/* 641 */       wpa[2] = ZW;
/* 642 */       this.wp = Arrays.copyOf(wpa, 3);
/* 643 */       float[] matrix = new float[9];
/* 644 */       matrix[0] = XA;
/* 645 */       matrix[1] = YA;
/* 646 */       matrix[2] = ZA;
/* 647 */       matrix[3] = XB;
/* 648 */       matrix[4] = YB;
/* 649 */       matrix[5] = ZB;
/* 650 */       matrix[6] = XC;
/* 651 */       matrix[7] = YC;
/* 652 */       matrix[8] = ZC;
/* 653 */       this.matrix = Arrays.copyOf(matrix, 9);
/*     */     }
/*     */     
/*     */     private PngChromaticitiesHelper() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/xobject/PdfImageXObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */