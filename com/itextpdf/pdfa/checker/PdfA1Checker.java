/*     */ package com.itextpdf.pdfa.checker;
/*     */ 
/*     */ import com.itextpdf.io.source.PdfTokenizer;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.PatternColor;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.font.PdfTrueTypeFont;
/*     */ import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.PdfXrefTable;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.util.PdfCanvasParser;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
/*     */ import com.itextpdf.pdfa.PdfAConformanceException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
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
/*     */ public class PdfA1Checker
/*     */   extends PdfAChecker
/*     */ {
/*  94 */   protected static final Set<PdfName> forbiddenAnnotations = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.Sound, PdfName.Movie, PdfName.FileAttachment }));
/*  95 */   protected static final Set<PdfName> contentAnnotations = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.Text, PdfName.FreeText, PdfName.Line, PdfName.Square, PdfName.Circle, PdfName.Stamp, PdfName.Ink, PdfName.Popup }));
/*     */   
/*  97 */   protected static final Set<PdfName> forbiddenActions = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.Launch, PdfName.Sound, PdfName.Movie, PdfName.ResetForm, PdfName.ImportData, PdfName.JavaScript, PdfName.Hide }));
/*     */   
/*  99 */   protected static final Set<PdfName> allowedNamedActions = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.NextPage, PdfName.PrevPage, PdfName.FirstPage, PdfName.LastPage }));
/*     */   
/* 101 */   protected static final Set<PdfName> allowedRenderingIntents = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.RelativeColorimetric, PdfName.AbsoluteColorimetric, PdfName.Perceptual, PdfName.Saturation }));
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_NUMBER_OF_DEVICEN_COLOR_COMPONENTS = 8;
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 5103027349795298132L;
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfA1Checker(PdfAConformanceLevel conformanceLevel) {
/* 113 */     super(conformanceLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkCanvasStack(char stackOperation) {
/* 118 */     if ('q' == stackOperation) {
/* 119 */       if (++this.gsStackDepth > 28)
/* 120 */         throw new PdfAConformanceException("Graphics state stack depth is greater than 28"); 
/* 121 */     } else if ('Q' == stackOperation) {
/* 122 */       this.gsStackDepth--;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkInlineImage(PdfStream inlineImage, PdfDictionary currentColorSpaces) {
/* 128 */     PdfObject filter = inlineImage.get(PdfName.Filter);
/* 129 */     if (filter instanceof PdfName) {
/* 130 */       if (filter.equals(PdfName.LZWDecode)) {
/* 131 */         throw new PdfAConformanceException("LZWDecode filter is not permitted");
/*     */       }
/* 133 */     } else if (filter instanceof PdfArray) {
/* 134 */       for (int i = 0; i < ((PdfArray)filter).size(); i++) {
/* 135 */         PdfName f = ((PdfArray)filter).getAsName(i);
/* 136 */         if (f.equals(PdfName.LZWDecode)) {
/* 137 */           throw new PdfAConformanceException("LZWDecode filter is not permitted");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     checkImage(inlineImage, currentColorSpaces);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill) {
/* 147 */     checkColorSpace(color.getColorSpace(), currentColorSpaces, true, fill);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill, PdfStream stream) {
/* 152 */     checkColorSpace(color.getColorSpace(), currentColorSpaces, true, fill);
/* 153 */     if (color instanceof PatternColor) {
/* 154 */       PdfPattern pattern = ((PatternColor)color).getPattern();
/* 155 */       if (pattern instanceof PdfPattern.Tiling) {
/* 156 */         checkContentStream((PdfStream)pattern.getPdfObject());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkColorSpace(PdfColorSpace colorSpace, PdfDictionary currentColorSpaces, boolean checkAlternate, Boolean fill) {
/* 163 */     if (colorSpace instanceof PdfSpecialCs.Separation) {
/* 164 */       colorSpace = ((PdfSpecialCs.Separation)colorSpace).getBaseCs();
/* 165 */     } else if (colorSpace instanceof PdfSpecialCs.DeviceN) {
/* 166 */       PdfSpecialCs.DeviceN deviceNColorspace = (PdfSpecialCs.DeviceN)colorSpace;
/* 167 */       if (deviceNColorspace.getNumberOfComponents() > 8) {
/* 168 */         throw new PdfAConformanceException("The number of color components in DeviceN colorspace should not exceed {0}", 
/*     */             
/* 170 */             Integer.valueOf(8));
/*     */       }
/* 172 */       colorSpace = deviceNColorspace.getBaseCs();
/*     */     } 
/*     */     
/* 175 */     if (colorSpace instanceof com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Rgb) {
/* 176 */       if (this.cmykIsUsed) {
/* 177 */         throw new PdfAConformanceException("Devicergb and devicecmyk colorspaces cannot be used both in one file");
/*     */       }
/* 179 */       this.rgbIsUsed = true;
/* 180 */     } else if (colorSpace instanceof com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Cmyk) {
/* 181 */       if (this.rgbIsUsed) {
/* 182 */         throw new PdfAConformanceException("Devicergb and devicecmyk colorspaces cannot be used both in one file");
/*     */       }
/* 184 */       this.cmykIsUsed = true;
/* 185 */     } else if (colorSpace instanceof com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Gray) {
/* 186 */       this.grayIsUsed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkXrefTable(PdfXrefTable xrefTable) {
/* 193 */     if (xrefTable.getCountOfIndirectObjects() > getMaxNumberOfIndirectObjects()) {
/* 194 */       throw new PdfAConformanceException("Maximum number of indirect objects exceeded");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<PdfName> getForbiddenActions() {
/* 200 */     return forbiddenActions;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<PdfName> getAllowedNamedActions() {
/* 205 */     return allowedNamedActions;
/*     */   }
/*     */ 
/*     */   
/*     */   protected long getMaxNumberOfIndirectObjects() {
/* 210 */     return 8388607L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkColorsUsages() {
/* 215 */     if ((this.rgbIsUsed || this.cmykIsUsed || this.grayIsUsed) && this.pdfAOutputIntentColorSpace == null) {
/* 216 */       throw new PdfAConformanceException("If device rgb cmyk gray used in file, that file shall contain pdfa outputintent");
/*     */     }
/*     */     
/* 219 */     if (this.rgbIsUsed && 
/* 220 */       !"RGB ".equals(this.pdfAOutputIntentColorSpace)) {
/* 221 */       throw new PdfAConformanceException("Devicergb may be used only if the file has a rgb pdfa outputIntent");
/*     */     }
/*     */     
/* 224 */     if (this.cmykIsUsed && 
/* 225 */       !"CMYK".equals(this.pdfAOutputIntentColorSpace)) {
/* 226 */       throw new PdfAConformanceException("Devicecmyk may be used only if the file has a cmyk pdfa outputIntent");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkExtGState(CanvasGraphicsState extGState) {
/* 233 */     checkExtGState(extGState, (PdfStream)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkExtGState(CanvasGraphicsState extGState, PdfStream contentStream) {
/* 238 */     if (extGState.getTransferFunction() != null) {
/* 239 */       throw new PdfAConformanceException("An extgstate dictionary shall not contain the tr key");
/*     */     }
/* 241 */     PdfObject transferFunction2 = extGState.getTransferFunction2();
/* 242 */     if (transferFunction2 != null && !PdfName.Default.equals(transferFunction2)) {
/* 243 */       throw new PdfAConformanceException("An extgstate dictionary shall not contain the TR2 key with a value other than default");
/*     */     }
/*     */     
/* 246 */     checkRenderingIntent(extGState.getRenderingIntent());
/*     */     
/* 248 */     PdfObject softMask = extGState.getSoftMask();
/* 249 */     if (softMask != null && !PdfName.None.equals(softMask)) {
/* 250 */       throw new PdfAConformanceException("The smask key is not allowed in extgstate");
/*     */     }
/*     */     
/* 253 */     PdfObject bm = extGState.getBlendMode();
/* 254 */     if (bm != null && !PdfName.Normal.equals(bm) && !PdfName.Compatible.equals(bm)) {
/* 255 */       throw new PdfAConformanceException("Blend mode shall have value normal or compatible");
/*     */     }
/*     */     
/* 258 */     Float ca = Float.valueOf(extGState.getStrokeOpacity());
/* 259 */     if (ca != null && ca.floatValue() != 1.0F) {
/* 260 */       throw new PdfAConformanceException("Transparency is not allowed. CA shall be equal to 1");
/*     */     }
/*     */     
/* 263 */     ca = Float.valueOf(extGState.getFillOpacity());
/* 264 */     if (ca != null && ca.floatValue() != 1.0F) {
/* 265 */       throw new PdfAConformanceException("Transparency is not allowed. ca shall be equal to 1");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkRenderingIntent(PdfName intent) {
/* 271 */     if (intent == null) {
/*     */       return;
/*     */     }
/* 274 */     if (!allowedRenderingIntents.contains(intent)) {
/* 275 */       throw new PdfAConformanceException("If specified rendering shall be one of the following relativecolorimetric absolutecolorimetric perceptual or saturation");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkFont(PdfFont pdfFont) {
/* 281 */     if (!pdfFont.isEmbedded()) {
/* 282 */       throw (new PdfAConformanceException("All the fonts must be embedded. This one is not: {0}"))
/* 283 */         .setMessageParams(new Object[] { pdfFont.getFontProgram().getFontNames().getFontName() });
/*     */     }
/*     */     
/* 286 */     if (pdfFont instanceof PdfTrueTypeFont) {
/* 287 */       PdfTrueTypeFont trueTypeFont = (PdfTrueTypeFont)pdfFont;
/* 288 */       boolean symbolic = trueTypeFont.getFontEncoding().isFontSpecific();
/* 289 */       if (symbolic) {
/* 290 */         checkSymbolicTrueTypeFont(trueTypeFont);
/*     */       } else {
/* 292 */         checkNonSymbolicTrueTypeFont(trueTypeFont);
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     if (pdfFont instanceof com.itextpdf.kernel.font.PdfType3Font) {
/* 297 */       PdfDictionary charProcs = ((PdfDictionary)pdfFont.getPdfObject()).getAsDictionary(PdfName.CharProcs);
/* 298 */       for (PdfName charName : charProcs.keySet()) {
/* 299 */         checkContentStream(charProcs.getAsStream(charName));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkContentStream(PdfStream contentStream) {
/* 306 */     if (isFullCheckMode() || contentStream.isModified()) {
/* 307 */       byte[] contentBytes = contentStream.getBytes();
/*     */       
/* 309 */       PdfTokenizer tokenizer = new PdfTokenizer(new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(contentBytes)));
/*     */       
/* 311 */       PdfCanvasParser parser = new PdfCanvasParser(tokenizer);
/* 312 */       List<PdfObject> operands = new ArrayList<>();
/*     */       try {
/* 314 */         while (parser.parse(operands).size() > 0) {
/* 315 */           for (PdfObject operand : operands) {
/* 316 */             checkContentStreamObject(operand);
/*     */           }
/*     */         } 
/* 319 */       } catch (IOException e) {
/* 320 */         throw new PdfException("Cannot parse content stream.", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected void checkContentStreamObject(PdfObject object) {
/*     */     PdfArray array;
/*     */     PdfDictionary dictionary;
/* 327 */     byte type = object.getType();
/* 328 */     switch (type) {
/*     */       case 6:
/* 330 */         checkPdfName((PdfName)object);
/*     */         break;
/*     */       case 10:
/* 333 */         checkPdfString((PdfString)object);
/*     */         break;
/*     */       case 8:
/* 336 */         checkPdfNumber((PdfNumber)object);
/*     */         break;
/*     */       case 1:
/* 339 */         array = (PdfArray)object;
/* 340 */         checkPdfArray(array);
/* 341 */         for (PdfObject obj : array) {
/* 342 */           checkContentStreamObject(obj);
/*     */         }
/*     */         break;
/*     */       case 3:
/* 346 */         dictionary = (PdfDictionary)object;
/* 347 */         checkPdfDictionary(dictionary);
/* 348 */         for (PdfName name : dictionary.keySet()) {
/* 349 */           checkPdfName(name);
/* 350 */           checkPdfObject(dictionary.get(name, false));
/*     */         } 
/* 352 */         for (PdfObject obj : dictionary.values()) {
/* 353 */           checkContentStreamObject(obj);
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkNonSymbolicTrueTypeFont(PdfTrueTypeFont trueTypeFont) {
/* 361 */     String encoding = trueTypeFont.getFontEncoding().getBaseEncoding();
/*     */     
/* 363 */     if ((!"Cp1252".equals(encoding) && !"MacRoman".equals(encoding)) || trueTypeFont.getFontEncoding().hasDifferences()) {
/* 364 */       throw new PdfAConformanceException("All non-symbolic TrueType fonts shall specify MacRomanEncoding or WinAnsiEncoding as the value of the Encoding entry in the font dictionary  This also means that Encoding entry in the font dictionary shall not be an encoding dictionary ", trueTypeFont);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkSymbolicTrueTypeFont(PdfTrueTypeFont trueTypeFont) {
/* 370 */     if (trueTypeFont.getFontEncoding().hasDifferences()) {
/* 371 */       throw new PdfAConformanceException("All symbolic TrueType fonts shall not specify an Encoding entry in the font dictionary ");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkImage(PdfStream image, PdfDictionary currentColorSpaces) {
/* 379 */     PdfColorSpace colorSpace = null;
/* 380 */     if (isAlreadyChecked((PdfDictionary)image)) {
/* 381 */       colorSpace = this.checkedObjectsColorspace.get(image);
/* 382 */       checkColorSpace(colorSpace, currentColorSpaces, true, (Boolean)null);
/*     */       return;
/*     */     } 
/* 385 */     PdfObject colorSpaceObj = image.get(PdfName.ColorSpace);
/* 386 */     if (colorSpaceObj != null) {
/* 387 */       colorSpace = PdfColorSpace.makeColorSpace(colorSpaceObj);
/* 388 */       checkColorSpace(colorSpace, currentColorSpaces, true, (Boolean)null);
/* 389 */       this.checkedObjectsColorspace.put(image, colorSpace);
/*     */     } 
/*     */     
/* 392 */     if (image.containsKey(PdfName.Alternates)) {
/* 393 */       throw new PdfAConformanceException("An image dictionary shall not contain alternates key");
/*     */     }
/* 395 */     if (image.containsKey(PdfName.OPI)) {
/* 396 */       throw new PdfAConformanceException("An image dictionary shall not contain opi key");
/*     */     }
/*     */     
/* 399 */     if (image.containsKey(PdfName.Interpolate) && image.getAsBool(PdfName.Interpolate).booleanValue()) {
/* 400 */       throw new PdfAConformanceException("The value of interpolate key shall not be true");
/*     */     }
/*     */     
/* 403 */     checkRenderingIntent(image.getAsName(PdfName.Intent));
/*     */     
/* 405 */     if (image.containsKey(PdfName.SMask) && !PdfName.None.equals(image.getAsName(PdfName.SMask))) {
/* 406 */       throw new PdfAConformanceException("The smask key is not allowed in xobjects");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkFormXObject(PdfStream form) {
/* 412 */     if (isAlreadyChecked((PdfDictionary)form))
/*     */       return; 
/* 414 */     if (form.containsKey(PdfName.OPI)) {
/* 415 */       throw new PdfAConformanceException("A form xobject dictionary shall not contain opi key");
/*     */     }
/* 417 */     if (form.containsKey(PdfName.PS)) {
/* 418 */       throw new PdfAConformanceException("A form xobject dictionary shall not contain PS key");
/*     */     }
/* 420 */     if (PdfName.PS.equals(form.getAsName(PdfName.Subtype2))) {
/* 421 */       throw new PdfAConformanceException("A form xobject dictionary shall not contain subtype2 key with a value of PS");
/*     */     }
/*     */     
/* 424 */     if (form.containsKey(PdfName.SMask) && !PdfName.None.equals(form.getAsName(PdfName.SMask))) {
/* 425 */       throw new PdfAConformanceException("The smask key is not allowed in xobjects");
/*     */     }
/*     */     
/* 428 */     if (isContainsTransparencyGroup((PdfDictionary)form)) {
/* 429 */       throw new PdfAConformanceException("A group object with an s key with a value of transparency shall not be included in a form xobject");
/*     */     }
/*     */     
/* 432 */     checkResources(form.getAsDictionary(PdfName.Resources));
/* 433 */     checkContentStream(form);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkLogicalStructure(PdfDictionary catalog) {
/* 438 */     if (checkStructure(this.conformanceLevel)) {
/* 439 */       PdfDictionary markInfo = catalog.getAsDictionary(PdfName.MarkInfo);
/* 440 */       if (markInfo == null || markInfo.getAsBoolean(PdfName.Marked) == null || !markInfo.getAsBoolean(PdfName.Marked).getValue()) {
/* 441 */         throw new PdfAConformanceException("A catalog dictionary shall include a markinfo dictionary whose entry marked shall have a value of true");
/*     */       }
/* 443 */       if (!catalog.containsKey(PdfName.Lang)) {
/* 444 */         Logger logger = LoggerFactory.getLogger(PdfAChecker.class);
/* 445 */         logger.warn("Catalog dictionary should contain lang entry");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkMetaData(PdfDictionary catalog) {
/* 452 */     if (!catalog.containsKey(PdfName.Metadata)) {
/* 453 */       throw new PdfAConformanceException("A catalog dictionary shall contain metadata entry");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkOutputIntents(PdfDictionary catalog) {
/* 459 */     PdfArray outputIntents = catalog.getAsArray(PdfName.OutputIntents);
/* 460 */     if (outputIntents == null) {
/*     */       return;
/*     */     }
/*     */     
/* 464 */     PdfObject destOutputProfile = null; int i;
/* 465 */     for (i = 0; i < outputIntents.size() && destOutputProfile == null; i++) {
/* 466 */       destOutputProfile = outputIntents.getAsDictionary(i).get(PdfName.DestOutputProfile);
/*     */     }
/* 468 */     for (; i < outputIntents.size(); i++) {
/* 469 */       PdfObject otherDestOutputProfile = outputIntents.getAsDictionary(i).get(PdfName.DestOutputProfile);
/* 470 */       if (otherDestOutputProfile != null && destOutputProfile != otherDestOutputProfile) {
/* 471 */         throw new PdfAConformanceException("If outputintents array has more than one entry with destoutputprofile key the same indirect object shall be used as the value of that object");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkPdfNumber(PdfNumber number) {
/* 479 */     if (number.hasDecimalPoint()) {
/* 480 */       if (Math.abs(number.longValue()) > getMaxRealValue()) {
/* 481 */         throw new PdfAConformanceException("Real number is out of range");
/*     */       }
/*     */     }
/* 484 */     else if (number.longValue() > getMaxIntegerValue() || number.longValue() < getMinIntegerValue()) {
/* 485 */       throw new PdfAConformanceException("Integer number is out of range");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getMaxRealValue() {
/* 495 */     return 32767.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long getMaxIntegerValue() {
/* 503 */     return 2147483647L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long getMinIntegerValue() {
/* 511 */     return -2147483648L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkPdfArray(PdfArray array) {
/* 516 */     if (array.size() > getMaxArrayCapacity()) {
/* 517 */       throw new PdfAConformanceException("Maximum array capacity is exceeded");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkPdfDictionary(PdfDictionary dictionary) {
/* 523 */     if (dictionary.size() > getMaxDictionaryCapacity()) {
/* 524 */       throw new PdfAConformanceException("Maximum dictionary capacity is exceeded");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkPdfStream(PdfStream stream) {
/* 530 */     checkPdfDictionary((PdfDictionary)stream);
/*     */     
/* 532 */     if (stream.containsKey(PdfName.F) || stream.containsKey(PdfName.FFilter) || stream.containsKey(PdfName.FDecodeParams)) {
/* 533 */       throw new PdfAConformanceException("Stream object dictionary shall not contain the f ffilter or fdecodeparams keys");
/*     */     }
/*     */     
/* 536 */     PdfObject filter = stream.get(PdfName.Filter);
/* 537 */     if (filter instanceof PdfName) {
/* 538 */       if (filter.equals(PdfName.LZWDecode))
/* 539 */         throw new PdfAConformanceException("LZWDecode filter is not permitted"); 
/* 540 */     } else if (filter instanceof PdfArray) {
/* 541 */       for (PdfObject f : filter) {
/* 542 */         if (f.equals(PdfName.LZWDecode)) {
/* 543 */           throw new PdfAConformanceException("LZWDecode filter is not permitted");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void checkPdfName(PdfName name) {
/* 550 */     if (name.getValue().length() > getMaxNameLength()) {
/* 551 */       throw new PdfAConformanceException("PdfName is too long");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxNameLength() {
/* 561 */     return 127;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkPdfString(PdfString string) {
/* 566 */     if ((string.getValueBytes()).length > getMaxStringLength()) {
/* 567 */       throw new PdfAConformanceException("PdfString is too long");
/*     */     }
/*     */   }
/*     */   
/*     */   protected int getMaxStringLength() {
/* 572 */     return 65535;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkPageSize(PdfDictionary page) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkFileSpec(PdfDictionary fileSpec) {
/* 582 */     if (fileSpec.containsKey(PdfName.EF)) {
/* 583 */       throw new PdfAConformanceException("File specification dictionary shall not contain the EF key");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkAnnotation(PdfDictionary annotDic) {
/* 589 */     PdfName subtype = annotDic.getAsName(PdfName.Subtype);
/*     */     
/* 591 */     if (subtype == null) {
/* 592 */       throw (new PdfAConformanceException("Annotation type {0} is not permitted")).setMessageParams(new Object[] { "null" });
/*     */     }
/* 594 */     if (forbiddenAnnotations.contains(subtype)) {
/* 595 */       throw (new PdfAConformanceException("Annotation type {0} is not permitted")).setMessageParams(new Object[] { subtype.getValue() });
/*     */     }
/* 597 */     PdfNumber ca = annotDic.getAsNumber(PdfName.CA);
/* 598 */     if (ca != null && ca.floatValue() != 1.0D) {
/* 599 */       throw new PdfAConformanceException("An annotation dictionary shall not contain the ca key with a value other than 1");
/*     */     }
/* 601 */     if (!annotDic.containsKey(PdfName.F)) {
/* 602 */       throw new PdfAConformanceException("An annotation dictionary shall contain the f key");
/*     */     }
/*     */     
/* 605 */     int flags = annotDic.getAsInt(PdfName.F).intValue();
/* 606 */     if (!checkFlag(flags, 4) || checkFlag(flags, 2) || checkFlag(flags, 1) || 
/* 607 */       checkFlag(flags, 32)) {
/* 608 */       throw new PdfAConformanceException("The f keys print flag bit shall be set to 1 and its hidden invisible and noview flag bits shall be set to 0");
/*     */     }
/* 610 */     if (subtype.equals(PdfName.Text) && (!checkFlag(flags, 8) || !checkFlag(flags, 16))) {
/* 611 */       throw new PdfAConformanceException("Text annotations should set the nozoom and norotate flag bits of the f key to 1");
/*     */     }
/* 613 */     if ((annotDic.containsKey(PdfName.C) || annotDic.containsKey(PdfName.IC)) && 
/* 614 */       !"RGB ".equals(this.pdfAOutputIntentColorSpace)) {
/* 615 */       throw new PdfAConformanceException("Destoutputprofile in the pdfa1 outputintent dictionary shall be rgb");
/*     */     }
/*     */ 
/*     */     
/* 619 */     PdfDictionary ap = annotDic.getAsDictionary(PdfName.AP);
/* 620 */     if (ap != null) {
/* 621 */       if (ap.containsKey(PdfName.D) || ap.containsKey(PdfName.R)) {
/* 622 */         throw new PdfAConformanceException("Appearance dictionary shall contain only the n key with stream value");
/*     */       }
/* 624 */       if (PdfName.Widget.equals(annotDic.getAsName(PdfName.Subtype)) && PdfName.Btn.equals(annotDic.getAsName(PdfName.FT))) {
/* 625 */         if (ap.getAsDictionary(PdfName.N) == null) {
/* 626 */           throw new PdfAConformanceException("If an annotation dictionary's Subtype key has a value of Widget and its FT key has a value of Btn, the value of the N key shall be an appearance subdictionary");
/*     */         }
/*     */       }
/* 629 */       else if (ap.getAsStream(PdfName.N) == null) {
/* 630 */         throw new PdfAConformanceException("Appearance dictionary shall contain only the n key with stream value");
/*     */       } 
/*     */       
/* 633 */       checkResourcesOfAppearanceStreams(ap);
/*     */     } 
/*     */     
/* 636 */     if (PdfName.Widget.equals(subtype) && (annotDic.containsKey(PdfName.AA) || annotDic.containsKey(PdfName.A))) {
/* 637 */       throw new PdfAConformanceException("Widget annotation dictionary or field dictionary shall not include a or aa entry");
/*     */     }
/*     */     
/* 640 */     if (annotDic.containsKey(PdfName.AA)) {
/* 641 */       throw new PdfAConformanceException("An annotation dictionary shall not contain aa key");
/*     */     }
/*     */     
/* 644 */     if (checkStructure(this.conformanceLevel) && 
/* 645 */       contentAnnotations.contains(subtype) && !annotDic.containsKey(PdfName.Contents)) {
/* 646 */       throw (new PdfAConformanceException("Annotation of type {0} should have contents key")).setMessageParams(new Object[] { subtype.getValue() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkForm(PdfDictionary form) {
/* 653 */     if (form == null) {
/*     */       return;
/*     */     }
/* 656 */     PdfBoolean needAppearances = form.getAsBoolean(PdfName.NeedAppearances);
/* 657 */     if (needAppearances != null && needAppearances.getValue()) {
/* 658 */       throw new PdfAConformanceException("Needappearances flag of the interactive form dictionary shall either not be presented or shall be false");
/*     */     }
/*     */     
/* 661 */     checkResources(form.getAsDictionary(PdfName.DR));
/*     */     
/* 663 */     PdfArray fields = form.getAsArray(PdfName.Fields);
/* 664 */     if (fields != null) {
/* 665 */       fields = getFormFields(fields);
/* 666 */       for (PdfObject field : fields) {
/* 667 */         PdfDictionary fieldDic = (PdfDictionary)field;
/* 668 */         if (fieldDic.containsKey(PdfName.A) || fieldDic.containsKey(PdfName.AA)) {
/* 669 */           throw new PdfAConformanceException("Widget annotation dictionary or field dictionary shall not include a or aa entry");
/*     */         }
/* 671 */         checkResources(fieldDic.getAsDictionary(PdfName.DR));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkAction(PdfDictionary action) {
/* 678 */     if (isAlreadyChecked(action))
/*     */       return; 
/* 680 */     PdfName s = action.getAsName(PdfName.S);
/* 681 */     if (getForbiddenActions().contains(s)) {
/* 682 */       throw (new PdfAConformanceException("{0} actions are not allowed")).setMessageParams(new Object[] { s.getValue() });
/*     */     }
/* 684 */     if (s.equals(PdfName.Named)) {
/* 685 */       PdfName n = action.getAsName(PdfName.N);
/* 686 */       if (n != null && !getAllowedNamedActions().contains(n)) {
/* 687 */         throw (new PdfAConformanceException("Named action type {0} not allowed")).setMessageParams(new Object[] { n.getValue() });
/*     */       }
/*     */     } 
/* 690 */     if (s.equals(PdfName.SetState) || s.equals(PdfName.NoOp)) {
/* 691 */       throw new PdfAConformanceException("Deprecated setstate and noop actions are not allowed");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkCatalogValidEntries(PdfDictionary catalogDict) {
/* 697 */     if (catalogDict.containsKey(PdfName.AA)) {
/* 698 */       throw new PdfAConformanceException("A catalog dictionary shall not contain aa entry");
/*     */     }
/* 700 */     if (catalogDict.containsKey(PdfName.OCProperties)) {
/* 701 */       throw new PdfAConformanceException("A catalog dictionary shall not contain the ocproperties key");
/*     */     }
/* 703 */     if (catalogDict.containsKey(PdfName.Names) && 
/* 704 */       catalogDict.getAsDictionary(PdfName.Names).containsKey(PdfName.EmbeddedFiles)) {
/* 705 */       throw new PdfAConformanceException("A name dictionary shall not contain the EmbeddedFiles key");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkPageObject(PdfDictionary pageDict, PdfDictionary pageResources) {
/* 712 */     PdfDictionary actions = pageDict.getAsDictionary(PdfName.AA);
/* 713 */     if (actions != null) {
/* 714 */       for (PdfName key : actions.keySet()) {
/* 715 */         PdfDictionary action = actions.getAsDictionary(key);
/* 716 */         checkAction(action);
/*     */       } 
/*     */     }
/* 719 */     if (isContainsTransparencyGroup(pageDict)) {
/* 720 */       throw new PdfAConformanceException("A group object with an s key with a value of transparency shall not be included in a page xobject");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkTrailer(PdfDictionary trailer) {
/* 726 */     if (trailer.containsKey(PdfName.Encrypt)) {
/* 727 */       throw new PdfAConformanceException("Keyword encrypt shall not be used in the trailer dictionary");
/*     */     }
/*     */   }
/*     */   
/*     */   protected PdfArray getFormFields(PdfArray array) {
/* 732 */     PdfArray fields = new PdfArray();
/* 733 */     for (PdfObject field : array) {
/* 734 */       PdfArray kids = ((PdfDictionary)field).getAsArray(PdfName.Kids);
/* 735 */       fields.add(field);
/* 736 */       if (kids != null) {
/* 737 */         fields.addAll(getFormFields(kids));
/*     */       }
/*     */     } 
/* 740 */     return fields;
/*     */   }
/*     */   
/*     */   private int getMaxArrayCapacity() {
/* 744 */     return 8191;
/*     */   }
/*     */   
/*     */   private int getMaxDictionaryCapacity() {
/* 748 */     return 4095;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/pdfa/checker/PdfA1Checker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */