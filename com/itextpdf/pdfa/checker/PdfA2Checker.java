/*      */ package com.itextpdf.pdfa.checker;
/*      */ 
/*      */ import com.itextpdf.io.colors.IccProfile;
/*      */ import com.itextpdf.io.font.FontEncoding;
/*      */ import com.itextpdf.io.image.ImageDataFactory;
/*      */ import com.itextpdf.io.image.Jpeg2000ImageData;
/*      */ import com.itextpdf.kernel.colors.Color;
/*      */ import com.itextpdf.kernel.colors.PatternColor;
/*      */ import com.itextpdf.kernel.font.PdfFont;
/*      */ import com.itextpdf.kernel.font.PdfTrueTypeFont;
/*      */ import com.itextpdf.kernel.font.PdfType3Font;
/*      */ import com.itextpdf.kernel.font.Type3Glyph;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfStream;
/*      */ import com.itextpdf.kernel.pdf.PdfString;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
/*      */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
/*      */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*      */ import com.itextpdf.pdfa.PdfAConformanceException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
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
/*      */ public class PdfA2Checker
/*      */   extends PdfA1Checker
/*      */ {
/*   98 */   protected static final Set<PdfName> forbiddenAnnotations = new HashSet<>(Arrays.asList(new PdfName[] { PdfName._3D, PdfName.Sound, PdfName.Screen, PdfName.Movie }));
/*   99 */   protected static final Set<PdfName> forbiddenActions = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.Launch, PdfName.Sound, PdfName.Movie, PdfName.ResetForm, PdfName.ImportData, PdfName.JavaScript, PdfName.Hide, PdfName.SetOCGState, PdfName.Rendition, PdfName.Trans, PdfName.GoTo3DView }));
/*      */   
/*  101 */   protected static final Set<PdfName> allowedBlendModes = new HashSet<>(Arrays.asList(new PdfName[] { PdfName.Normal, PdfName.Compatible, PdfName.Multiply, PdfName.Screen, PdfName.Overlay, PdfName.Darken, PdfName.Lighten, PdfName.ColorDodge, PdfName.ColorBurn, PdfName.HardLight, PdfName.SoftLight, PdfName.Difference, PdfName.Exclusion, PdfName.Hue, PdfName.Saturation, PdfName.Color, PdfName.Luminosity }));
/*      */   
/*      */   static final int MAX_PAGE_SIZE = 14400;
/*      */   
/*      */   static final int MIN_PAGE_SIZE = 3;
/*      */   
/*      */   private static final int MAX_NUMBER_OF_DEVICEN_COLOR_COMPONENTS = 32;
/*      */   
/*      */   private static final long serialVersionUID = -5937712517954260687L;
/*      */   
/*      */   private boolean currentFillCsIsIccBasedCMYK = false;
/*      */   
/*      */   private boolean currentStrokeCsIsIccBasedCMYK = false;
/*      */   
/*  115 */   private Map<PdfName, PdfArray> separationColorSpaces = new HashMap<>();
/*      */   
/*  117 */   private Set<PdfObject> transparencyObjects = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfA2Checker(PdfAConformanceLevel conformanceLevel) {
/*  126 */     super(conformanceLevel);
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkInlineImage(PdfStream inlineImage, PdfDictionary currentColorSpaces) {
/*  131 */     PdfObject filter = inlineImage.get(PdfName.Filter);
/*  132 */     if (filter instanceof PdfName) {
/*  133 */       if (filter.equals(PdfName.LZWDecode))
/*  134 */         throw new PdfAConformanceException("LZWDecode filter is not permitted"); 
/*  135 */       if (filter.equals(PdfName.Crypt)) {
/*  136 */         throw new PdfAConformanceException("Crypt filter is not permitted inline image");
/*      */       }
/*  138 */     } else if (filter instanceof PdfArray) {
/*  139 */       for (int i = 0; i < ((PdfArray)filter).size(); i++) {
/*  140 */         PdfName f = ((PdfArray)filter).getAsName(i);
/*  141 */         if (f.equals(PdfName.LZWDecode))
/*  142 */           throw new PdfAConformanceException("LZWDecode filter is not permitted"); 
/*  143 */         if (f.equals(PdfName.Crypt)) {
/*  144 */           throw new PdfAConformanceException("Crypt filter is not permitted inline image");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  149 */     checkImage(inlineImage, currentColorSpaces);
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill) {
/*  154 */     checkColor(color, currentColorSpaces, fill, (PdfStream)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill, PdfStream contentStream) {
/*  159 */     if (color instanceof PatternColor) {
/*  160 */       PdfPattern pattern = ((PatternColor)color).getPattern();
/*  161 */       if (pattern instanceof PdfPattern.Shading) {
/*  162 */         PdfDictionary shadingDictionary = ((PdfPattern.Shading)pattern).getShading();
/*  163 */         PdfObject colorSpace = shadingDictionary.get(PdfName.ColorSpace);
/*  164 */         checkColorSpace(PdfColorSpace.makeColorSpace(colorSpace), currentColorSpaces, true, Boolean.valueOf(true));
/*  165 */         final PdfDictionary extGStateDict = ((PdfDictionary)pattern.getPdfObject()).getAsDictionary(PdfName.ExtGState);
/*  166 */         CanvasGraphicsState gState = new CanvasGraphicsState()
/*      */           {
/*      */           
/*      */           };
/*      */         
/*  171 */         checkExtGState(gState, contentStream);
/*  172 */       } else if (pattern instanceof PdfPattern.Tiling) {
/*  173 */         checkContentStream((PdfStream)pattern.getPdfObject());
/*      */       } 
/*      */     } 
/*      */     
/*  177 */     super.checkColor(color, currentColorSpaces, fill, contentStream);
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkColorSpace(PdfColorSpace colorSpace, PdfDictionary currentColorSpaces, boolean checkAlternate, Boolean fill) {
/*  182 */     if (fill != null) {
/*  183 */       if (fill.booleanValue()) {
/*  184 */         this.currentFillCsIsIccBasedCMYK = false;
/*      */       } else {
/*  186 */         this.currentStrokeCsIsIccBasedCMYK = false;
/*      */       } 
/*      */     }
/*      */     
/*  190 */     if (colorSpace instanceof PdfSpecialCs.Separation) {
/*      */       
/*  192 */       PdfSpecialCs.Separation separation = (PdfSpecialCs.Separation)colorSpace;
/*  193 */       checkSeparationCS((PdfArray)separation.getPdfObject());
/*  194 */       if (checkAlternate) {
/*  195 */         checkColorSpace(separation.getBaseCs(), currentColorSpaces, false, fill);
/*      */       }
/*      */     }
/*  198 */     else if (colorSpace instanceof PdfSpecialCs.DeviceN) {
/*      */       
/*  200 */       PdfSpecialCs.DeviceN deviceN = (PdfSpecialCs.DeviceN)colorSpace;
/*  201 */       if (deviceN.getNumberOfComponents() > 32) {
/*  202 */         throw new PdfAConformanceException("The number of color components in DeviceN colorspace should not exceed {0}", 
/*      */             
/*  204 */             Integer.valueOf(32));
/*      */       }
/*      */ 
/*      */       
/*  208 */       PdfDictionary attributes = ((PdfArray)deviceN.getPdfObject()).getAsDictionary(4);
/*  209 */       PdfDictionary colorants = attributes.getAsDictionary(PdfName.Colorants);
/*      */ 
/*      */       
/*  212 */       if (colorants != null) {
/*  213 */         for (Map.Entry<PdfName, PdfObject> entry : (Iterable<Map.Entry<PdfName, PdfObject>>)colorants.entrySet()) {
/*  214 */           PdfArray separation = (PdfArray)entry.getValue();
/*  215 */           checkSeparationInsideDeviceN(separation, ((PdfArray)deviceN.getPdfObject()).get(2), ((PdfArray)deviceN.getPdfObject()).get(3));
/*      */         } 
/*      */       }
/*      */       
/*  219 */       if (checkAlternate) {
/*  220 */         checkColorSpace(deviceN.getBaseCs(), currentColorSpaces, false, fill);
/*      */       }
/*      */     }
/*  223 */     else if (colorSpace instanceof PdfSpecialCs.Indexed) {
/*  224 */       if (checkAlternate) {
/*  225 */         checkColorSpace(((PdfSpecialCs.Indexed)colorSpace).getBaseCs(), currentColorSpaces, true, fill);
/*      */       }
/*  227 */     } else if (colorSpace instanceof PdfSpecialCs.UncoloredTilingPattern) {
/*  228 */       if (checkAlternate) {
/*  229 */         checkColorSpace(((PdfSpecialCs.UncoloredTilingPattern)colorSpace).getUnderlyingColorSpace(), currentColorSpaces, true, fill);
/*      */       
/*      */       }
/*      */     }
/*  233 */     else if (colorSpace instanceof PdfDeviceCs.Rgb) {
/*  234 */       if (!checkDefaultCS(currentColorSpaces, fill, PdfName.DefaultRGB, 3)) {
/*  235 */         this.rgbIsUsed = true;
/*      */       }
/*  237 */     } else if (colorSpace instanceof PdfDeviceCs.Cmyk) {
/*  238 */       if (!checkDefaultCS(currentColorSpaces, fill, PdfName.DefaultCMYK, 4)) {
/*  239 */         this.cmykIsUsed = true;
/*      */       }
/*  241 */     } else if (colorSpace instanceof PdfDeviceCs.Gray && 
/*  242 */       !checkDefaultCS(currentColorSpaces, fill, PdfName.DefaultGray, 1)) {
/*  243 */       this.grayIsUsed = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  248 */     if (fill != null && colorSpace instanceof com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs.IccBased) {
/*  249 */       byte[] iccBytes = ((PdfArray)colorSpace.getPdfObject()).getAsStream(1).getBytes();
/*  250 */       if ("CMYK".equals(IccProfile.getIccColorSpaceName(iccBytes))) {
/*  251 */         if (fill.booleanValue()) {
/*  252 */           this.currentFillCsIsIccBasedCMYK = true;
/*      */         } else {
/*  254 */           this.currentStrokeCsIsIccBasedCMYK = true;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkExtGState(CanvasGraphicsState extGState) {
/*  262 */     checkExtGState(extGState, (PdfStream)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkExtGState(CanvasGraphicsState extGState, PdfStream contentStream) {
/*  267 */     if (Integer.valueOf(1).equals(Integer.valueOf(extGState.getOverprintMode()))) {
/*  268 */       if (extGState.getFillOverprint() && this.currentFillCsIsIccBasedCMYK) {
/*  269 */         throw new PdfAConformanceException("Overprint mode shall not be one when an ICCBased CMYK colour space is used and when overprinting is set to true");
/*      */       }
/*  271 */       if (extGState.getStrokeOverprint() && this.currentStrokeCsIsIccBasedCMYK) {
/*  272 */         throw new PdfAConformanceException("Overprint mode shall not be one when an ICCBased CMYK colour space is used and when overprinting is set to true");
/*      */       }
/*      */     } 
/*      */     
/*  276 */     if (extGState.getTransferFunction() != null) {
/*  277 */       throw new PdfAConformanceException("An extgstate dictionary shall not contain the tr key");
/*      */     }
/*  279 */     if (extGState.getHTP() != null) {
/*  280 */       throw new PdfAConformanceException("An extgstate dictionary shall not contain the HTP key");
/*      */     }
/*      */     
/*  283 */     PdfObject transferFunction2 = extGState.getTransferFunction2();
/*  284 */     if (transferFunction2 != null && !PdfName.Default.equals(transferFunction2)) {
/*  285 */       throw new PdfAConformanceException("An extgstate dictionary shall not contain the TR2 key with a value other than default");
/*      */     }
/*      */     
/*  288 */     if (extGState.getHalftone() instanceof PdfDictionary) {
/*  289 */       PdfDictionary halftoneDict = (PdfDictionary)extGState.getHalftone();
/*  290 */       Integer halftoneType = halftoneDict.getAsInt(PdfName.HalftoneType);
/*  291 */       if (halftoneType.intValue() != 1 && halftoneType.intValue() != 5) {
/*  292 */         throw new PdfAConformanceException("All halftones shall have halftonetype 1 or 5");
/*      */       }
/*      */       
/*  295 */       if (halftoneDict.containsKey(PdfName.HalftoneName)) {
/*  296 */         throw new PdfAConformanceException("Halftones shall not contain halftonename");
/*      */       }
/*      */     } 
/*      */     
/*  300 */     checkRenderingIntent(extGState.getRenderingIntent());
/*      */     
/*  302 */     if (extGState.getSoftMask() != null && extGState.getSoftMask() instanceof PdfDictionary) {
/*  303 */       this.transparencyObjects.add(contentStream);
/*      */     }
/*  305 */     if (extGState.getStrokeOpacity() < 1.0F) {
/*  306 */       this.transparencyObjects.add(contentStream);
/*      */     }
/*  308 */     if (extGState.getFillOpacity() < 1.0F) {
/*  309 */       this.transparencyObjects.add(contentStream);
/*      */     }
/*      */     
/*  312 */     PdfObject bm = extGState.getBlendMode();
/*  313 */     if (bm != null) {
/*  314 */       if (!PdfName.Normal.equals(bm)) {
/*  315 */         this.transparencyObjects.add(contentStream);
/*      */       }
/*  317 */       if (bm instanceof PdfArray) {
/*  318 */         for (PdfObject b : bm) {
/*  319 */           checkBlendMode((PdfName)b);
/*      */         }
/*  321 */       } else if (bm instanceof PdfName) {
/*  322 */         checkBlendMode((PdfName)bm);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkNonSymbolicTrueTypeFont(PdfTrueTypeFont trueTypeFont) {
/*  329 */     String encoding = trueTypeFont.getFontEncoding().getBaseEncoding();
/*      */     
/*  331 */     if (!"Cp1252".equals(encoding) && !"MacRoman".equals(encoding)) {
/*  332 */       throw new PdfAConformanceException("All non-symbolic TrueType fonts shall specify MacRomanEncoding or WinAnsiEncoding as the value of the Encoding entry in the font dictionary ", trueTypeFont);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getMaxRealValue() {
/*  340 */     return 3.4028234663852886E38D;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getMaxStringLength() {
/*  345 */     return 32767;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkPdfArray(PdfArray array) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkPdfDictionary(PdfDictionary dictionary) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkAnnotation(PdfDictionary annotDic) {
/*  359 */     PdfName subtype = annotDic.getAsName(PdfName.Subtype);
/*      */     
/*  361 */     if (subtype == null) {
/*  362 */       throw (new PdfAConformanceException("Annotation type {0} is not permitted")).setMessageParams(new Object[] { "null" });
/*      */     }
/*  364 */     if (forbiddenAnnotations.contains(subtype)) {
/*  365 */       throw (new PdfAConformanceException("Annotation type {0} is not permitted")).setMessageParams(new Object[] { subtype.getValue() });
/*      */     }
/*      */     
/*  368 */     if (!subtype.equals(PdfName.Popup)) {
/*  369 */       PdfNumber f = annotDic.getAsNumber(PdfName.F);
/*  370 */       if (f == null) {
/*  371 */         throw new PdfAConformanceException("An annotation dictionary shall contain the f key");
/*      */       }
/*  373 */       int flags = f.intValue();
/*  374 */       if (!checkFlag(flags, 4) || 
/*  375 */         checkFlag(flags, 2) || 
/*  376 */         checkFlag(flags, 1) || 
/*  377 */         checkFlag(flags, 32) || 
/*  378 */         checkFlag(flags, 256)) {
/*  379 */         throw new PdfAConformanceException("The f keys print flag bit shall be set to 1 and its hidden invisible noview and togglenoview flag bits shall be set to 0");
/*      */       }
/*  381 */       if (subtype.equals(PdfName.Text) && (
/*  382 */         !checkFlag(flags, 8) || !checkFlag(flags, 16))) {
/*  383 */         throw new PdfAConformanceException("Text annotations should set the nozoom and norotate flag bits of the f key to 1");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  388 */     if (PdfName.Widget.equals(subtype) && (annotDic.containsKey(PdfName.AA) || annotDic.containsKey(PdfName.A))) {
/*  389 */       throw new PdfAConformanceException("Widget annotation dictionary or field dictionary shall not include a or aa entry");
/*      */     }
/*      */     
/*  392 */     if (annotDic.containsKey(PdfName.AA)) {
/*  393 */       throw new PdfAConformanceException("An annotation dictionary shall not contain aa key");
/*      */     }
/*      */     
/*  396 */     if (checkStructure(this.conformanceLevel) && 
/*  397 */       contentAnnotations.contains(subtype) && !annotDic.containsKey(PdfName.Contents)) {
/*  398 */       throw (new PdfAConformanceException("Annotation of type {0} should have contents key")).setMessageParams(new Object[] { subtype.getValue() });
/*      */     }
/*      */ 
/*      */     
/*  402 */     PdfDictionary ap = annotDic.getAsDictionary(PdfName.AP);
/*  403 */     if (ap != null) {
/*  404 */       if (ap.containsKey(PdfName.R) || ap.containsKey(PdfName.D)) {
/*  405 */         throw new PdfAConformanceException("Appearance dictionary shall contain only the n key with stream value");
/*      */       }
/*  407 */       PdfObject n = ap.get(PdfName.N);
/*  408 */       if (PdfName.Widget.equals(subtype) && PdfName.Btn.equals(annotDic.getAsName(PdfName.FT))) {
/*  409 */         if (n == null || !n.isDictionary()) {
/*  410 */           throw new PdfAConformanceException("Appearance dictionary of widget subtype and btn field type shall contain only the n key with dictionary value");
/*      */         }
/*  412 */       } else if (n == null || !n.isStream()) {
/*  413 */         throw new PdfAConformanceException("Appearance dictionary shall contain only the n key with stream value");
/*      */       } 
/*      */       
/*  416 */       checkResourcesOfAppearanceStreams(ap);
/*      */     } else {
/*  418 */       boolean isCorrectRect = false;
/*  419 */       PdfArray rect = annotDic.getAsArray(PdfName.Rect);
/*  420 */       if (rect != null && rect.size() == 4) {
/*  421 */         PdfNumber index0 = rect.getAsNumber(0);
/*  422 */         PdfNumber index1 = rect.getAsNumber(1);
/*  423 */         PdfNumber index2 = rect.getAsNumber(2);
/*  424 */         PdfNumber index3 = rect.getAsNumber(3);
/*  425 */         if (index0 != null && index1 != null && index2 != null && index3 != null && index0
/*  426 */           .floatValue() == index2.floatValue() && index1.floatValue() == index3.floatValue())
/*  427 */           isCorrectRect = true; 
/*      */       } 
/*  429 */       if (!PdfName.Popup.equals(subtype) && 
/*  430 */         !PdfName.Link.equals(subtype) && !isCorrectRect)
/*      */       {
/*  432 */         throw new PdfAConformanceException("Every annotation shall have at least one appearance dictionary");
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkAppearanceStream(PdfStream appearanceStream) {
/*  438 */     if (isAlreadyChecked((PdfDictionary)appearanceStream)) {
/*      */       return;
/*      */     }
/*      */     
/*  442 */     if (isContainsTransparencyGroup((PdfDictionary)appearanceStream)) {
/*  443 */       this.transparencyObjects.add(appearanceStream);
/*      */     }
/*  445 */     checkResources(appearanceStream.getAsDictionary(PdfName.Resources));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkForm(PdfDictionary form) {
/*  450 */     if (form != null) {
/*  451 */       PdfBoolean needAppearances = form.getAsBoolean(PdfName.NeedAppearances);
/*  452 */       if (needAppearances != null && needAppearances.getValue()) {
/*  453 */         throw new PdfAConformanceException("Needappearances flag of the interactive form dictionary shall either not be presented or shall be false");
/*      */       }
/*  455 */       if (form.containsKey(PdfName.XFA)) {
/*  456 */         throw new PdfAConformanceException("The interactive form dictionary shall not contain the xfa key");
/*      */       }
/*  458 */       checkResources(form.getAsDictionary(PdfName.DR));
/*      */       
/*  460 */       PdfArray fields = form.getAsArray(PdfName.Fields);
/*  461 */       if (fields != null) {
/*  462 */         fields = getFormFields(fields);
/*  463 */         for (PdfObject field : fields) {
/*  464 */           PdfDictionary fieldDic = (PdfDictionary)field;
/*  465 */           checkResources(fieldDic.getAsDictionary(PdfName.DR));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkCatalogValidEntries(PdfDictionary catalogDict) {
/*  473 */     if (catalogDict.containsKey(PdfName.NeedsRendering)) {
/*  474 */       throw new PdfAConformanceException("The catalog dictionary shall not contain the needsrendering key");
/*      */     }
/*      */     
/*  477 */     if (catalogDict.containsKey(PdfName.AA)) {
/*  478 */       throw new PdfAConformanceException("A catalog dictionary shall not contain aa entry");
/*      */     }
/*      */     
/*  481 */     if (catalogDict.containsKey(PdfName.Requirements)) {
/*  482 */       throw new PdfAConformanceException("A catalog dictionary shall not contain a requirements entry");
/*      */     }
/*      */     
/*  485 */     PdfDictionary permissions = catalogDict.getAsDictionary(PdfName.Perms);
/*  486 */     if (permissions != null) {
/*  487 */       for (PdfName dictKey : permissions.keySet()) {
/*  488 */         if (PdfName.DocMDP.equals(dictKey)) {
/*  489 */           PdfDictionary signatureDict = permissions.getAsDictionary(PdfName.DocMDP);
/*  490 */           if (signatureDict != null) {
/*  491 */             PdfArray references = signatureDict.getAsArray(PdfName.Reference);
/*  492 */             if (references != null)
/*  493 */               for (int i = 0; i < references.size(); i++) {
/*  494 */                 PdfDictionary referenceDict = references.getAsDictionary(i);
/*  495 */                 if (referenceDict.containsKey(PdfName.DigestLocation) || referenceDict
/*  496 */                   .containsKey(PdfName.DigestMethod) || referenceDict
/*  497 */                   .containsKey(PdfName.DigestValue))
/*  498 */                   throw new PdfAConformanceException("Signature references dictionary shall not contain digestlocation digestmethod digestvalue"); 
/*      */               }  
/*      */           } 
/*      */           continue;
/*      */         } 
/*  503 */         if (PdfName.UR3.equals(dictKey))
/*      */           continue; 
/*  505 */         throw new PdfAConformanceException("No keys other than UR3 and DocMDP shall be present in a permissions dictionary");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  510 */     PdfDictionary namesDictionary = catalogDict.getAsDictionary(PdfName.Names);
/*  511 */     if (namesDictionary != null && namesDictionary.containsKey(PdfName.AlternatePresentations)) {
/*  512 */       throw new PdfAConformanceException("A catalog dictionary shall not contain alternatepresentations names entry");
/*      */     }
/*      */     
/*  515 */     PdfDictionary oCProperties = catalogDict.getAsDictionary(PdfName.OCProperties);
/*  516 */     if (oCProperties != null) {
/*  517 */       List<PdfDictionary> configList = new ArrayList<>();
/*  518 */       PdfDictionary d = oCProperties.getAsDictionary(PdfName.D);
/*  519 */       if (d != null) {
/*  520 */         configList.add(d);
/*      */       }
/*  522 */       PdfArray configs = oCProperties.getAsArray(PdfName.Configs);
/*  523 */       if (configs != null) {
/*  524 */         for (PdfObject config : configs) {
/*  525 */           configList.add((PdfDictionary)config);
/*      */         }
/*      */       }
/*      */       
/*  529 */       HashSet<PdfObject> ocgs = new HashSet<>();
/*  530 */       PdfArray ocgsArray = oCProperties.getAsArray(PdfName.OCGs);
/*  531 */       if (ocgsArray != null) {
/*  532 */         for (PdfObject ocg : ocgsArray) {
/*  533 */           ocgs.add(ocg);
/*      */         }
/*      */       }
/*      */       
/*  537 */       HashSet<String> names = new HashSet<>();
/*      */       
/*  539 */       for (PdfDictionary config : configList) {
/*  540 */         checkCatalogConfig(config, ocgs, names);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkPageSize(PdfDictionary page) {
/*  547 */     PdfName[] boxNames = { PdfName.MediaBox, PdfName.CropBox, PdfName.TrimBox, PdfName.ArtBox, PdfName.BleedBox };
/*  548 */     for (PdfName boxName : boxNames) {
/*  549 */       Rectangle box = page.getAsRectangle(boxName);
/*  550 */       if (box != null) {
/*  551 */         float width = box.getWidth();
/*  552 */         float height = box.getHeight();
/*  553 */         if (width < 3.0F || width > 14400.0F || height < 3.0F || height > 14400.0F) {
/*  554 */           throw new PdfAConformanceException("The page less 3 units no greater 14400 in either direction");
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkFileSpec(PdfDictionary fileSpec) {
/*  561 */     if (fileSpec.containsKey(PdfName.EF)) {
/*  562 */       if (!fileSpec.containsKey(PdfName.F) || !fileSpec.containsKey(PdfName.UF)) {
/*  563 */         throw new PdfAConformanceException("File specification dictionary shall contain f key and uf key");
/*      */       }
/*  565 */       if (!fileSpec.containsKey(PdfName.Desc)) {
/*  566 */         Logger logger1 = LoggerFactory.getLogger(PdfAChecker.class);
/*  567 */         logger1.warn("File specification dictionary should contain desc key");
/*      */       } 
/*      */       
/*  570 */       PdfDictionary ef = fileSpec.getAsDictionary(PdfName.EF);
/*  571 */       PdfStream embeddedFile = ef.getAsStream(PdfName.F);
/*  572 */       if (embeddedFile == null) {
/*  573 */         throw new PdfAConformanceException("Ef key of file specification dictionary shall contain dictionary with valid f key");
/*      */       }
/*      */       
/*  576 */       Logger logger = LoggerFactory.getLogger(PdfAChecker.class);
/*  577 */       logger.warn("Embedded file shall be compliant with either ISO 19005-1 (PDF-A/1 standard) or ISO 19005-2 (PDF-A/2 standard). Please ensure that fact, because iText doesn't check embedded file.");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkPdfStream(PdfStream stream) {
/*  583 */     checkPdfDictionary((PdfDictionary)stream);
/*      */     
/*  585 */     if (stream.containsKey(PdfName.F) || stream.containsKey(PdfName.FFilter) || stream.containsKey(PdfName.FDecodeParams)) {
/*  586 */       throw new PdfAConformanceException("Stream object dictionary shall not contain the f ffilter or fdecodeparams keys");
/*      */     }
/*      */     
/*  589 */     PdfObject filter = stream.get(PdfName.Filter);
/*  590 */     if (filter instanceof PdfName) {
/*  591 */       if (filter.equals(PdfName.LZWDecode))
/*  592 */         throw new PdfAConformanceException("LZWDecode filter is not permitted"); 
/*  593 */       if (filter.equals(PdfName.Crypt)) {
/*  594 */         PdfDictionary decodeParams = stream.getAsDictionary(PdfName.DecodeParms);
/*  595 */         if (decodeParams != null) {
/*  596 */           PdfName cryptFilterName = decodeParams.getAsName(PdfName.Name);
/*  597 */           if (cryptFilterName != null && !cryptFilterName.equals(PdfName.Identity)) {
/*  598 */             throw new PdfAConformanceException("Not identity crypt filter is not permitted");
/*      */           }
/*      */         } 
/*      */       } 
/*  602 */     } else if (filter instanceof PdfArray) {
/*  603 */       for (int i = 0; i < ((PdfArray)filter).size(); i++) {
/*  604 */         PdfName f = ((PdfArray)filter).getAsName(i);
/*  605 */         if (f.equals(PdfName.LZWDecode))
/*  606 */           throw new PdfAConformanceException("LZWDecode filter is not permitted"); 
/*  607 */         if (f.equals(PdfName.Crypt)) {
/*  608 */           PdfArray decodeParams = stream.getAsArray(PdfName.DecodeParms);
/*  609 */           if (decodeParams != null && i < decodeParams.size()) {
/*  610 */             PdfDictionary decodeParam = decodeParams.getAsDictionary(i);
/*  611 */             PdfName cryptFilterName = decodeParam.getAsName(PdfName.Name);
/*  612 */             if (cryptFilterName != null && !cryptFilterName.equals(PdfName.Identity)) {
/*  613 */               throw new PdfAConformanceException("Not identity crypt filter is not permitted");
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkPageObject(PdfDictionary pageDict, PdfDictionary pageResources) {
/*  623 */     if (pageDict.containsKey(PdfName.AA)) {
/*  624 */       throw new PdfAConformanceException("The page dictionary shall not contain aa entry");
/*      */     }
/*      */     
/*  627 */     if (pageDict.containsKey(PdfName.PresSteps)) {
/*  628 */       throw new PdfAConformanceException("The page dictionary shall not contain pressteps entry");
/*      */     }
/*      */     
/*  631 */     if (isContainsTransparencyGroup(pageDict)) {
/*  632 */       PdfObject cs = pageDict.getAsDictionary(PdfName.Group).get(PdfName.CS);
/*  633 */       if (cs != null) {
/*  634 */         PdfDictionary currentColorSpaces = pageResources.getAsDictionary(PdfName.ColorSpace);
/*  635 */         checkColorSpace(PdfColorSpace.makeColorSpace(cs), currentColorSpaces, true, (Boolean)null);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkPageTransparency(PdfDictionary pageDict, PdfDictionary pageResources) {
/*  642 */     if (this.pdfAOutputIntentColorSpace == null && this.transparencyObjects
/*  643 */       .size() > 0 && (pageDict
/*  644 */       .getAsDictionary(PdfName.Group) == null || pageDict.getAsDictionary(PdfName.Group).get(PdfName.CS) == null)) {
/*  645 */       if (this.transparencyObjects.contains(pageDict)) {
/*  646 */         throw new PdfAConformanceException("If the document does not contain a OutputIntent, then page with transparency shall include the dictionary with Group key that include a CS with blending colour space");
/*      */       }
/*  648 */       checkContentsForTransparency(pageDict);
/*  649 */       checkAnnotationsForTransparency(pageDict.getAsArray(PdfName.Annots));
/*  650 */       checkResourcesForTransparency(pageResources, new HashSet<>());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkOutputIntents(PdfDictionary catalog) {
/*  656 */     PdfArray outputIntents = catalog.getAsArray(PdfName.OutputIntents);
/*  657 */     if (outputIntents == null) {
/*      */       return;
/*      */     }
/*      */     
/*  661 */     PdfObject destOutputProfile = null; int i;
/*  662 */     for (i = 0; i < outputIntents.size() && destOutputProfile == null; i++) {
/*  663 */       destOutputProfile = outputIntents.getAsDictionary(i).get(PdfName.DestOutputProfile);
/*      */     }
/*  665 */     for (; i < outputIntents.size(); i++) {
/*  666 */       PdfObject otherDestOutputProfile = outputIntents.getAsDictionary(i).get(PdfName.DestOutputProfile);
/*  667 */       if (otherDestOutputProfile != null && destOutputProfile != otherDestOutputProfile) {
/*  668 */         throw new PdfAConformanceException("If outputintents array has more than one entry with destoutputprofile key the same indirect object shall be used as the value of that object");
/*      */       }
/*      */     } 
/*      */     
/*  672 */     if (destOutputProfile != null) {
/*  673 */       String deviceClass = IccProfile.getIccDeviceClass(((PdfStream)destOutputProfile).getBytes());
/*  674 */       if (!"prtr".equals(deviceClass) && !"mntr".equals(deviceClass)) {
/*  675 */         throw new PdfAConformanceException("Profile stream of outputintent shall be output profile (prtr) or monitor profile (mntr)");
/*      */       }
/*      */       
/*  678 */       String cs = IccProfile.getIccColorSpaceName(((PdfStream)destOutputProfile).getBytes());
/*  679 */       if (!"RGB ".equals(cs) && !"CMYK".equals(cs) && !"GRAY".equals(cs)) {
/*  680 */         throw new PdfAConformanceException("Output intent color space shall be either gray rgb or cmyk");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Set<PdfName> getForbiddenActions() {
/*  687 */     return forbiddenActions;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Set<PdfName> getAllowedNamedActions() {
/*  692 */     return allowedNamedActions;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkColorsUsages() {
/*  697 */     if ((this.rgbIsUsed || this.cmykIsUsed || this.grayIsUsed) && this.pdfAOutputIntentColorSpace == null) {
/*  698 */       throw new PdfAConformanceException("If device rgb cmyk gray used in file that file shall contain pdfa outputintent orDefaultRgb Cmyk Gray in usage context");
/*      */     }
/*      */     
/*  701 */     if (this.rgbIsUsed && 
/*  702 */       !"RGB ".equals(this.pdfAOutputIntentColorSpace)) {
/*  703 */       throw new PdfAConformanceException("Devicergb may be used only if the file has a rgb pdfa outputIntent or defaultrgb in usage context");
/*      */     }
/*      */     
/*  706 */     if (this.cmykIsUsed && 
/*  707 */       !"CMYK".equals(this.pdfAOutputIntentColorSpace)) {
/*  708 */       throw new PdfAConformanceException("Devicecmyk may be used only if the file has a cmyk pdfa outputIntent or defaultcmyk in usage context");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkImage(PdfStream image, PdfDictionary currentColorSpaces) {
/*  715 */     PdfColorSpace colorSpace = null;
/*  716 */     if (isAlreadyChecked((PdfDictionary)image)) {
/*  717 */       colorSpace = this.checkedObjectsColorspace.get(image);
/*  718 */       checkColorSpace(colorSpace, currentColorSpaces, true, (Boolean)null);
/*      */       
/*      */       return;
/*      */     } 
/*  722 */     PdfObject colorSpaceObj = image.get(PdfName.ColorSpace);
/*  723 */     if (colorSpaceObj != null) {
/*  724 */       colorSpace = PdfColorSpace.makeColorSpace(colorSpaceObj);
/*  725 */       checkColorSpace(colorSpace, currentColorSpaces, true, (Boolean)null);
/*  726 */       this.checkedObjectsColorspace.put(image, colorSpace);
/*      */     } 
/*      */     
/*  729 */     if (image.containsKey(PdfName.Alternates)) {
/*  730 */       throw new PdfAConformanceException("An image dictionary shall not contain alternates key");
/*      */     }
/*  732 */     if (image.containsKey(PdfName.OPI)) {
/*  733 */       throw new PdfAConformanceException("An image dictionary shall not contain opi key");
/*      */     }
/*      */     
/*  736 */     if (image.containsKey(PdfName.Interpolate) && image.getAsBool(PdfName.Interpolate).booleanValue()) {
/*  737 */       throw new PdfAConformanceException("The value of interpolate key shall not be true");
/*      */     }
/*  739 */     checkRenderingIntent(image.getAsName(PdfName.Intent));
/*      */     
/*  741 */     if (image.getAsStream(PdfName.SMask) != null) {
/*  742 */       this.transparencyObjects.add(image);
/*      */     }
/*      */     
/*  745 */     if (image.containsKey(PdfName.SMaskInData) && image.getAsInt(PdfName.SMaskInData).intValue() > 0) {
/*  746 */       this.transparencyObjects.add(image);
/*      */     }
/*      */     
/*  749 */     if (PdfName.JPXDecode.equals(image.get(PdfName.Filter))) {
/*  750 */       Jpeg2000ImageData jpgImage = (Jpeg2000ImageData)ImageDataFactory.createJpeg2000(image.getBytes(false));
/*  751 */       Jpeg2000ImageData.Parameters params = jpgImage.getParameters();
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
/*  770 */       if (!params.isJp2) {
/*  771 */         throw new PdfAConformanceException("Only jpx baseline set of features shall be used");
/*      */       }
/*      */       
/*  774 */       if (params.numOfComps != 1 && params.numOfComps != 3 && params.numOfComps != 4) {
/*  775 */         throw new PdfAConformanceException("The number of colour channels in the jpeg2000 data shall be 1, 3 or 4");
/*      */       }
/*      */       
/*  778 */       if (params.colorSpecBoxes != null && params.colorSpecBoxes.size() > 1) {
/*  779 */         int numOfApprox0x01 = 0;
/*  780 */         for (Jpeg2000ImageData.ColorSpecBox colorSpecBox : params.colorSpecBoxes) {
/*  781 */           if (colorSpecBox.getApprox() == 1) {
/*  782 */             numOfApprox0x01++;
/*  783 */             if (numOfApprox0x01 == 1 && colorSpecBox
/*  784 */               .getMeth() != 1 && colorSpecBox.getMeth() != 2 && colorSpecBox.getMeth() != 3) {
/*  785 */               throw new PdfAConformanceException("The value of the meth entry in colr box shall be 1, 2 or 3");
/*      */             }
/*      */             
/*  788 */             if (image.get(PdfName.ColorSpace) == null) {
/*  789 */               PdfDeviceCs.Gray deviceGrayCs; PdfDeviceCs.Rgb deviceRgbCs; PdfDeviceCs.Cmyk deviceCmykCs; switch (colorSpecBox.getEnumCs()) {
/*      */                 case 1:
/*  791 */                   deviceGrayCs = new PdfDeviceCs.Gray();
/*  792 */                   checkColorSpace((PdfColorSpace)deviceGrayCs, currentColorSpaces, true, (Boolean)null);
/*  793 */                   this.checkedObjectsColorspace.put(image, deviceGrayCs);
/*      */                   break;
/*      */                 case 3:
/*  796 */                   deviceRgbCs = new PdfDeviceCs.Rgb();
/*  797 */                   checkColorSpace((PdfColorSpace)deviceRgbCs, currentColorSpaces, true, (Boolean)null);
/*  798 */                   this.checkedObjectsColorspace.put(image, deviceRgbCs);
/*      */                   break;
/*      */                 case 12:
/*  801 */                   deviceCmykCs = new PdfDeviceCs.Cmyk();
/*  802 */                   checkColorSpace((PdfColorSpace)deviceCmykCs, currentColorSpaces, true, (Boolean)null);
/*  803 */                   this.checkedObjectsColorspace.put(image, deviceCmykCs);
/*      */                   break;
/*      */               } 
/*      */             } 
/*      */           } 
/*  808 */           if (colorSpecBox.getEnumCs() == 19) {
/*  809 */             throw new PdfAConformanceException("jpeg2000 enumerated colour space 19 (CIEJab) shall not be used");
/*      */           }
/*      */         } 
/*  812 */         if (numOfApprox0x01 != 1) {
/*  813 */           throw new PdfAConformanceException("Exactly one colour space specification shall have the value 0x01 in the approx field");
/*      */         }
/*      */       } 
/*      */       
/*  817 */       if (jpgImage.getBpc() < 1 || jpgImage.getBpc() > 38) {
/*  818 */         throw new PdfAConformanceException("The bit-depth of the jpeg2000 data shall have a value in the range 1 to 38");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  824 */       if (params.bpcBoxData != null) {
/*  825 */         throw new PdfAConformanceException("All colour channels in the jpeg2000 data shall have the same bit-depth");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkFontGlyphs(PdfFont font, PdfStream contentStream) {
/*  832 */     if (font instanceof PdfType3Font) {
/*  833 */       checkType3FontGlyphs((PdfType3Font)font, contentStream);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkFormXObject(PdfStream form) {
/*  839 */     checkFormXObject(form, (PdfStream)null);
/*      */   }
/*      */   
/*      */   protected void checkFormXObject(PdfStream form, PdfStream contentStream) {
/*  843 */     if (isAlreadyChecked((PdfDictionary)form))
/*      */       return; 
/*  845 */     if (form.containsKey(PdfName.OPI)) {
/*  846 */       throw new PdfAConformanceException("A form xobject dictionary shall not contain opi key");
/*      */     }
/*  848 */     if (form.containsKey(PdfName.PS)) {
/*  849 */       throw new PdfAConformanceException("A form xobject dictionary shall not contain PS key");
/*      */     }
/*  851 */     if (PdfName.PS.equals(form.getAsName(PdfName.Subtype2))) {
/*  852 */       throw new PdfAConformanceException("A form xobject dictionary shall not contain subtype2 key with a value of PS");
/*      */     }
/*      */     
/*  855 */     if (isContainsTransparencyGroup((PdfDictionary)form)) {
/*  856 */       if (contentStream != null) {
/*  857 */         this.transparencyObjects.add(contentStream);
/*      */       } else {
/*  859 */         this.transparencyObjects.add(form);
/*      */       } 
/*  861 */       PdfObject cs = form.getAsDictionary(PdfName.Group).get(PdfName.CS);
/*  862 */       PdfDictionary resources = form.getAsDictionary(PdfName.Resources);
/*  863 */       if (cs != null && resources != null) {
/*  864 */         PdfDictionary currentColorSpaces = resources.getAsDictionary(PdfName.ColorSpace);
/*  865 */         checkColorSpace(PdfColorSpace.makeColorSpace(cs), currentColorSpaces, true, (Boolean)null);
/*      */       } 
/*      */     } 
/*      */     
/*  869 */     checkResources(form.getAsDictionary(PdfName.Resources));
/*  870 */     checkContentStream(form);
/*      */   }
/*      */   
/*      */   private void checkContentsForTransparency(PdfDictionary pageDict) {
/*  874 */     PdfStream contentStream = pageDict.getAsStream(PdfName.Contents);
/*  875 */     if (contentStream != null && this.transparencyObjects.contains(contentStream)) {
/*  876 */       throw new PdfAConformanceException("If the document does not contain a OutputIntent, then page with transparency shall include the dictionary with Group key that include a CS with blending colour space");
/*      */     }
/*  878 */     PdfArray contentSteamArray = pageDict.getAsArray(PdfName.Contents);
/*  879 */     if (contentSteamArray != null) {
/*  880 */       for (int i = 0; i < contentSteamArray.size(); i++) {
/*  881 */         if (this.transparencyObjects.contains(contentSteamArray.get(i))) {
/*  882 */           throw new PdfAConformanceException("If the document does not contain a OutputIntent, then page with transparency shall include the dictionary with Group key that include a CS with blending colour space");
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkAnnotationsForTransparency(PdfArray annotations) {
/*  890 */     if (annotations == null) {
/*      */       return;
/*      */     }
/*  893 */     for (int i = 0; i < annotations.size(); i++) {
/*  894 */       PdfDictionary annot = annotations.getAsDictionary(i);
/*  895 */       PdfDictionary ap = annot.getAsDictionary(PdfName.AP);
/*  896 */       if (ap != null) {
/*  897 */         checkAppearanceStreamForTransparency(ap, new HashSet<>());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkAppearanceStreamForTransparency(PdfDictionary ap, Set<PdfObject> checkedObjects) {
/*  903 */     if (checkedObjects.contains(ap)) {
/*      */       return;
/*      */     }
/*  906 */     checkedObjects.add(ap);
/*      */     
/*  908 */     for (PdfObject val : ap.values()) {
/*  909 */       if (this.transparencyObjects.contains(val))
/*  910 */         throw new PdfAConformanceException("If the document does not contain a OutputIntent, then page with transparency shall include the dictionary with Group key that include a CS with blending colour space"); 
/*  911 */       if (val.isDictionary()) {
/*  912 */         checkAppearanceStreamForTransparency((PdfDictionary)val, checkedObjects); continue;
/*  913 */       }  if (val.isStream()) {
/*  914 */         checkObjectWithResourcesForTransparency(val, checkedObjects);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkObjectWithResourcesForTransparency(PdfObject objectWithResources, Set<PdfObject> checkedObjects) {
/*  920 */     if (checkedObjects.contains(objectWithResources)) {
/*      */       return;
/*      */     }
/*  923 */     checkedObjects.add(objectWithResources);
/*      */ 
/*      */     
/*  926 */     if (this.transparencyObjects.contains(objectWithResources)) {
/*  927 */       throw new PdfAConformanceException("If the document does not contain a OutputIntent, then page with transparency shall include the dictionary with Group key that include a CS with blending colour space");
/*      */     }
/*  929 */     if (objectWithResources instanceof PdfDictionary) {
/*  930 */       checkResourcesForTransparency(((PdfDictionary)objectWithResources).getAsDictionary(PdfName.Resources), checkedObjects);
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkResourcesForTransparency(PdfDictionary resources, Set<PdfObject> checkedObjects) {
/*  935 */     if (resources != null) {
/*  936 */       checkSingleResourceTypeForTransparency(resources.getAsDictionary(PdfName.XObject), checkedObjects);
/*  937 */       checkSingleResourceTypeForTransparency(resources.getAsDictionary(PdfName.Pattern), checkedObjects);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkSingleResourceTypeForTransparency(PdfDictionary singleResourceDict, Set<PdfObject> checkedObjects) {
/*  942 */     if (singleResourceDict != null) {
/*  943 */       for (PdfObject resource : singleResourceDict.values()) {
/*  944 */         checkObjectWithResourcesForTransparency(resource, checkedObjects);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkBlendMode(PdfName blendMode) {
/*  950 */     if (!allowedBlendModes.contains(blendMode)) {
/*  951 */       throw new PdfAConformanceException("Only standard blend modes shall be used for the value of the BM key in an extended graphic state dictionary");
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkSeparationInsideDeviceN(PdfArray separation, PdfObject deviceNColorSpace, PdfObject deviceNTintTransform) {
/*  956 */     if (!isAltCSIsTheSame(separation.get(2), deviceNColorSpace) || 
/*  957 */       !deviceNTintTransform.equals(separation.get(3))) {
/*  958 */       Logger logger = LoggerFactory.getLogger(PdfAChecker.class);
/*  959 */       logger.warn("TintTransform and alternateSpace of separation arrays in the colorants of deviceN should be consistent with same attributes of deviceN");
/*      */     } 
/*  961 */     checkSeparationCS(separation);
/*      */   }
/*      */   
/*      */   private void checkSeparationCS(PdfArray separation) {
/*  965 */     if (this.separationColorSpaces.containsKey(separation.getAsName(0))) {
/*      */ 
/*      */ 
/*      */       
/*  969 */       PdfArray sameNameSeparation = this.separationColorSpaces.get(separation.getAsName(0));
/*  970 */       PdfObject cs1 = separation.get(2);
/*  971 */       PdfObject cs2 = sameNameSeparation.get(2);
/*  972 */       boolean altCSIsTheSame = isAltCSIsTheSame(cs1, cs2);
/*      */ 
/*      */ 
/*      */       
/*  976 */       PdfObject f1Obj = separation.get(3);
/*  977 */       PdfObject f2Obj = sameNameSeparation.get(3);
/*      */       
/*  979 */       boolean bothAllowedType = (f1Obj.getType() == f2Obj.getType() && (f1Obj.isDictionary() || f1Obj.isStream()));
/*      */       
/*  981 */       boolean tintTransformIsTheSame = (bothAllowedType && f1Obj.equals(f2Obj));
/*      */ 
/*      */       
/*  984 */       if (!altCSIsTheSame || !tintTransformIsTheSame) {
/*  985 */         throw new PdfAConformanceException("TintTransform and alternateSpace shall be the same for the all separation cs with the same name");
/*      */       }
/*      */     } else {
/*  988 */       this.separationColorSpaces.put(separation.getAsName(0), separation);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isAltCSIsTheSame(PdfObject cs1, PdfObject cs2) {
/*  994 */     boolean altCSIsTheSame = false;
/*  995 */     if (cs1 instanceof PdfName) {
/*  996 */       altCSIsTheSame = cs1.equals(cs2);
/*  997 */     } else if (cs1 instanceof PdfArray && cs2 instanceof PdfArray) {
/*      */ 
/*      */ 
/*      */       
/* 1001 */       altCSIsTheSame = ((PdfArray)cs1).get(0).equals(((PdfArray)cs1).get(0));
/*      */     } 
/* 1003 */     return altCSIsTheSame;
/*      */   }
/*      */   
/*      */   private void checkCatalogConfig(PdfDictionary config, HashSet<PdfObject> ocgs, HashSet<String> names) {
/* 1007 */     PdfString name = config.getAsString(PdfName.Name);
/* 1008 */     if (name == null) {
/* 1009 */       throw new PdfAConformanceException("Optional content configuration dictionary shall contain name entry");
/*      */     }
/* 1011 */     if (!names.add(name.toUnicodeString())) {
/* 1012 */       throw new PdfAConformanceException("Value of name entry shall be unique among all optional content configuration dictionaries");
/*      */     }
/* 1014 */     if (config.containsKey(PdfName.AS)) {
/* 1015 */       throw new PdfAConformanceException("The as key shall not appear in any optional content configuration dictionary");
/*      */     }
/* 1017 */     PdfArray orderArray = config.getAsArray(PdfName.Order);
/* 1018 */     if (orderArray != null) {
/* 1019 */       HashSet<PdfObject> order = new HashSet<>();
/* 1020 */       fillOrderRecursively(orderArray, order);
/* 1021 */       if (!order.equals(ocgs)) {
/* 1022 */         throw new PdfAConformanceException("Order array shall contain references to all ocgs");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void fillOrderRecursively(PdfArray orderArray, Set<PdfObject> order) {
/* 1029 */     for (PdfObject orderItem : orderArray) {
/* 1030 */       if (!orderItem.isArray()) {
/* 1031 */         order.add(orderItem); continue;
/*      */       } 
/* 1033 */       fillOrderRecursively((PdfArray)orderItem, order);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean checkDefaultCS(PdfDictionary currentColorSpaces, Boolean fill, PdfName defaultCsName, int numOfComponents) {
/* 1039 */     if (currentColorSpaces == null)
/* 1040 */       return false; 
/* 1041 */     if (!currentColorSpaces.containsKey(defaultCsName)) {
/* 1042 */       return false;
/*      */     }
/* 1044 */     PdfObject defaultCsObj = currentColorSpaces.get(defaultCsName);
/* 1045 */     PdfColorSpace defaultCs = PdfColorSpace.makeColorSpace(defaultCsObj);
/* 1046 */     if (defaultCs instanceof PdfDeviceCs) {
/* 1047 */       throw (new PdfAConformanceException("Color space {0} shall be device independent")).setMessageParams(new Object[] { defaultCsName.toString() });
/*      */     }
/* 1049 */     if (defaultCs.getNumberOfComponents() != numOfComponents) {
/* 1050 */       throw (new PdfAConformanceException("Color space {0} shall have {1} components")).setMessageParams(new Object[] { defaultCsName.getValue(), Integer.valueOf(numOfComponents) });
/*      */     }
/* 1052 */     checkColorSpace(defaultCs, currentColorSpaces, false, fill);
/* 1053 */     return true;
/*      */   }
/*      */   
/*      */   private void checkType3FontGlyphs(PdfType3Font font, PdfStream contentStream) {
/* 1057 */     for (int i = 0; i <= 255; i++) {
/* 1058 */       FontEncoding fontEncoding = font.getFontEncoding();
/* 1059 */       if (fontEncoding.canDecode(i)) {
/* 1060 */         Type3Glyph type3Glyph = font.getType3Glyph(fontEncoding.getUnicode(i));
/* 1061 */         if (type3Glyph != null)
/* 1062 */           checkFormXObject(type3Glyph.getContentStream(), contentStream); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/pdfa/checker/PdfA2Checker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */