/*     */ package com.itextpdf.pdfa.checker;
/*     */ 
/*     */ import com.itextpdf.io.colors.IccProfile;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.font.PdfTrueTypeFont;
/*     */ import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfCatalog;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.PdfXrefTable;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PdfAChecker
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -9138950508285715228L;
/*     */   public static final String ICC_COLOR_SPACE_RGB = "RGB ";
/*     */   public static final String ICC_COLOR_SPACE_CMYK = "CMYK";
/*     */   public static final String ICC_COLOR_SPACE_GRAY = "GRAY";
/*     */   public static final String ICC_DEVICE_CLASS_OUTPUT_PROFILE = "prtr";
/*     */   public static final String ICC_DEVICE_CLASS_MONITOR_PROFILE = "mntr";
/*     */   public static final int maxGsStackDepth = 28;
/*     */   protected PdfAConformanceLevel conformanceLevel;
/*     */   protected String pdfAOutputIntentColorSpace;
/* 135 */   protected int gsStackDepth = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean rgbIsUsed = false;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean cmykIsUsed = false;
/*     */ 
/*     */   
/*     */   protected boolean grayIsUsed = false;
/*     */ 
/*     */   
/* 149 */   protected Set<PdfObject> checkedObjects = new HashSet<>();
/* 150 */   protected Map<PdfObject, PdfColorSpace> checkedObjectsColorspace = new HashMap<>();
/*     */   
/*     */   private boolean fullCheckMode = false;
/*     */   
/*     */   protected PdfAChecker(PdfAConformanceLevel conformanceLevel) {
/* 155 */     this.conformanceLevel = conformanceLevel;
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
/*     */   public void checkDocument(PdfCatalog catalog) {
/* 167 */     PdfDictionary catalogDict = (PdfDictionary)catalog.getPdfObject();
/* 168 */     setPdfAOutputIntentColorSpace(catalogDict);
/*     */     
/* 170 */     checkOutputIntents(catalogDict);
/* 171 */     checkMetaData(catalogDict);
/* 172 */     checkCatalogValidEntries(catalogDict);
/* 173 */     checkTrailer(catalog.getDocument().getTrailer());
/* 174 */     checkLogicalStructure(catalogDict);
/* 175 */     checkForm(catalogDict.getAsDictionary(PdfName.AcroForm));
/* 176 */     checkOutlines(catalogDict);
/* 177 */     checkPages(catalog.getDocument());
/* 178 */     checkOpenAction(catalogDict.get(PdfName.OpenAction));
/* 179 */     checkColorsUsages();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkSinglePage(PdfPage page) {
/* 188 */     checkPage(page);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkPdfObject(PdfObject obj) {
/*     */     PdfArray array;
/*     */     PdfDictionary dict;
/*     */     PdfName type;
/*     */     PdfStream stream;
/* 198 */     switch (obj.getType()) {
/*     */       case 6:
/* 200 */         checkPdfName((PdfName)obj);
/*     */         break;
/*     */       case 8:
/* 203 */         checkPdfNumber((PdfNumber)obj);
/*     */         break;
/*     */       case 10:
/* 206 */         checkPdfString((PdfString)obj);
/*     */         break;
/*     */       case 1:
/* 209 */         array = (PdfArray)obj;
/* 210 */         checkPdfArray(array);
/* 211 */         checkArrayRecursively(array);
/*     */         break;
/*     */       case 3:
/* 214 */         dict = (PdfDictionary)obj;
/* 215 */         type = dict.getAsName(PdfName.Type);
/* 216 */         if (PdfName.Filespec.equals(type)) {
/* 217 */           checkFileSpec(dict);
/*     */         }
/* 219 */         checkPdfDictionary(dict);
/* 220 */         checkDictionaryRecursively(dict);
/*     */         break;
/*     */       case 9:
/* 223 */         stream = (PdfStream)obj;
/* 224 */         checkPdfStream(stream);
/* 225 */         checkDictionaryRecursively((PdfDictionary)stream);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfAConformanceLevel getConformanceLevel() {
/* 236 */     return this.conformanceLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullCheckMode() {
/* 247 */     return this.fullCheckMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFullCheckMode(boolean fullCheckMode) {
/* 258 */     this.fullCheckMode = fullCheckMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean objectIsChecked(PdfObject object) {
/* 269 */     return this.checkedObjects.contains(object);
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
/*     */   public void checkTagStructureElement(PdfObject obj) {
/* 281 */     this.checkedObjects.add(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void checkCanvasStack(char paramChar);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void checkInlineImage(PdfStream paramPdfStream, PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract void checkColor(Color paramColor, PdfDictionary paramPdfDictionary, Boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill, PdfStream contentStream) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void checkColorSpace(PdfColorSpace paramPdfColorSpace, PdfDictionary paramPdfDictionary, boolean paramBoolean, Boolean paramBoolean1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void checkRenderingIntent(PdfName paramPdfName);
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract void checkExtGState(CanvasGraphicsState paramCanvasGraphicsState);
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkExtGState(CanvasGraphicsState extGState, PdfStream contentStream) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void checkFont(PdfFont paramPdfFont);
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkFontGlyphs(PdfFont font, PdfStream contentStream) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void checkXrefTable(PdfXrefTable paramPdfXrefTable);
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkPageTransparency(PdfDictionary pageDict, PdfDictionary pageResources) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkContentStream(PdfStream contentStream) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkContentStreamObject(PdfObject object) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract long getMaxNumberOfIndirectObjects();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Set<PdfName> getForbiddenActions();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Set<PdfName> getAllowedNamedActions();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkAction(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkAnnotation(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkCatalogValidEntries(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkColorsUsages();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkImage(PdfStream paramPdfStream, PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkFileSpec(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkForm(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkFormXObject(PdfStream paramPdfStream);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkLogicalStructure(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkMetaData(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkNonSymbolicTrueTypeFont(PdfTrueTypeFont paramPdfTrueTypeFont);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkOutputIntents(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkPageObject(PdfDictionary paramPdfDictionary1, PdfDictionary paramPdfDictionary2);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkPageSize(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkPdfArray(PdfArray paramPdfArray);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkPdfDictionary(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkPdfName(PdfName paramPdfName);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkPdfNumber(PdfNumber paramPdfNumber);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkPdfStream(PdfStream paramPdfStream);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkPdfString(PdfString paramPdfString);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkSymbolicTrueTypeFont(PdfTrueTypeFont paramPdfTrueTypeFont);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkTrailer(PdfDictionary paramPdfDictionary);
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkResources(PdfDictionary resources) {
/* 451 */     if (resources == null) {
/*     */       return;
/*     */     }
/* 454 */     PdfDictionary xObjects = resources.getAsDictionary(PdfName.XObject);
/* 455 */     PdfDictionary shadings = resources.getAsDictionary(PdfName.Shading);
/* 456 */     PdfDictionary patterns = resources.getAsDictionary(PdfName.Pattern);
/*     */     
/* 458 */     if (xObjects != null) {
/* 459 */       for (PdfObject xObject : xObjects.values()) {
/* 460 */         PdfStream xObjStream = (PdfStream)xObject;
/* 461 */         PdfObject subtype = null;
/* 462 */         boolean isFlushed = xObjStream.isFlushed();
/* 463 */         if (!isFlushed) {
/* 464 */           subtype = xObjStream.get(PdfName.Subtype);
/*     */         }
/*     */         
/* 467 */         if (PdfName.Image.equals(subtype) || isFlushed) {
/*     */           
/* 469 */           checkImage(xObjStream, resources.getAsDictionary(PdfName.ColorSpace)); continue;
/* 470 */         }  if (PdfName.Form.equals(subtype)) {
/* 471 */           checkFormXObject(xObjStream);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 476 */     if (shadings != null) {
/* 477 */       for (PdfObject shading : shadings.values()) {
/* 478 */         PdfDictionary shadingDict = (PdfDictionary)shading;
/* 479 */         if (!isAlreadyChecked(shadingDict)) {
/* 480 */           checkColorSpace(PdfColorSpace.makeColorSpace(shadingDict.get(PdfName.ColorSpace)), resources.getAsDictionary(PdfName.ColorSpace), true, null);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 485 */     if (patterns != null) {
/* 486 */       for (PdfObject p : patterns.values()) {
/* 487 */         if (p.isStream()) {
/* 488 */           PdfStream pStream = (PdfStream)p;
/* 489 */           if (!isAlreadyChecked((PdfDictionary)pStream)) {
/* 490 */             checkResources(pStream.getAsDictionary(PdfName.Resources));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected static boolean checkFlag(int flags, int flag) {
/* 498 */     return ((flags & flag) != 0);
/*     */   }
/*     */   
/*     */   protected static boolean checkStructure(PdfAConformanceLevel conformanceLevel) {
/* 502 */     return (conformanceLevel == PdfAConformanceLevel.PDF_A_1A || conformanceLevel == PdfAConformanceLevel.PDF_A_2A || conformanceLevel == PdfAConformanceLevel.PDF_A_3A);
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
/*     */   protected static boolean isContainsTransparencyGroup(PdfDictionary dictionary) {
/* 515 */     return (dictionary.containsKey(PdfName.Group) && PdfName.Transparency.equals(dictionary
/* 516 */         .getAsDictionary(PdfName.Group).getAsName(PdfName.S)));
/*     */   }
/*     */   
/*     */   protected boolean isAlreadyChecked(PdfDictionary dictionary) {
/* 520 */     if (this.checkedObjects.contains(dictionary)) {
/* 521 */       return true;
/*     */     }
/* 523 */     this.checkedObjects.add(dictionary);
/* 524 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkResourcesOfAppearanceStreams(PdfDictionary appearanceStreamsDict) {
/* 533 */     checkResourcesOfAppearanceStreams(appearanceStreamsDict, new HashSet<>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkAppearanceStream(PdfStream appearanceStream) {
/* 542 */     if (isAlreadyChecked((PdfDictionary)appearanceStream)) {
/*     */       return;
/*     */     }
/*     */     
/* 546 */     checkResources(appearanceStream.getAsDictionary(PdfName.Resources));
/*     */   }
/*     */   
/*     */   private void checkResourcesOfAppearanceStreams(PdfDictionary appearanceStreamsDict, Set<PdfObject> checkedObjects) {
/* 550 */     if (checkedObjects.contains(appearanceStreamsDict)) {
/*     */       return;
/*     */     }
/* 553 */     checkedObjects.add(appearanceStreamsDict);
/*     */     
/* 555 */     for (PdfObject val : appearanceStreamsDict.values()) {
/* 556 */       if (val instanceof PdfDictionary) {
/* 557 */         PdfDictionary ap = (PdfDictionary)val;
/* 558 */         if (ap.isDictionary()) {
/* 559 */           checkResourcesOfAppearanceStreams(ap, checkedObjects); continue;
/* 560 */         }  if (ap.isStream()) {
/* 561 */           checkAppearanceStream((PdfStream)ap);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkArrayRecursively(PdfArray array) {
/* 568 */     for (int i = 0; i < array.size(); i++) {
/* 569 */       PdfObject object = array.get(i, false);
/* 570 */       if (object != null && !object.isIndirect()) {
/* 571 */         checkPdfObject(object);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkDictionaryRecursively(PdfDictionary dictionary) {
/* 577 */     for (PdfName name : dictionary.keySet()) {
/* 578 */       checkPdfName(name);
/* 579 */       PdfObject object = dictionary.get(name, false);
/* 580 */       if (object != null && !object.isIndirect()) {
/* 581 */         checkPdfObject(object);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkPages(PdfDocument document) {
/* 587 */     for (int i = 1; i <= document.getNumberOfPages(); i++) {
/* 588 */       checkPage(document.getPage(i));
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkPage(PdfPage page) {
/* 593 */     PdfDictionary pageDict = (PdfDictionary)page.getPdfObject();
/*     */     
/* 595 */     if (isAlreadyChecked(pageDict))
/*     */       return; 
/* 597 */     checkPageObject(pageDict, (PdfDictionary)page.getResources().getPdfObject());
/* 598 */     PdfDictionary pageResources = (PdfDictionary)page.getResources().getPdfObject();
/* 599 */     checkResources(pageResources);
/* 600 */     checkAnnotations(pageDict);
/* 601 */     checkPageSize(pageDict);
/* 602 */     checkPageTransparency(pageDict, (PdfDictionary)page.getResources().getPdfObject());
/*     */     
/* 604 */     int contentStreamCount = page.getContentStreamCount();
/* 605 */     for (int j = 0; j < contentStreamCount; j++) {
/* 606 */       PdfStream contentStream = page.getContentStream(j);
/* 607 */       checkContentStream(contentStream);
/* 608 */       this.checkedObjects.add(contentStream);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkOpenAction(PdfObject openAction) {
/* 613 */     if (openAction != null && openAction.isDictionary()) {
/* 614 */       checkAction((PdfDictionary)openAction);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkAnnotations(PdfDictionary page) {
/* 619 */     PdfArray annots = page.getAsArray(PdfName.Annots);
/* 620 */     if (annots != null) {
/* 621 */       for (int i = 0; i < annots.size(); i++) {
/* 622 */         PdfDictionary annot = annots.getAsDictionary(i);
/* 623 */         checkAnnotation(annot);
/* 624 */         PdfDictionary action = annot.getAsDictionary(PdfName.A);
/* 625 */         if (action != null) {
/* 626 */           checkAction(action);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkOutlines(PdfDictionary catalogDict) {
/* 633 */     PdfDictionary outlines = catalogDict.getAsDictionary(PdfName.Outlines);
/* 634 */     if (outlines != null) {
/* 635 */       for (PdfDictionary outline : getOutlines(outlines)) {
/* 636 */         PdfDictionary action = outline.getAsDictionary(PdfName.A);
/* 637 */         if (action != null) {
/* 638 */           checkAction(action);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private List<PdfDictionary> getOutlines(PdfDictionary item) {
/* 645 */     List<PdfDictionary> outlines = new ArrayList<>();
/* 646 */     outlines.add(item);
/*     */     
/* 648 */     PdfDictionary processItem = item.getAsDictionary(PdfName.First);
/* 649 */     if (processItem != null) {
/* 650 */       outlines.addAll(getOutlines(processItem));
/*     */     }
/* 652 */     processItem = item.getAsDictionary(PdfName.Next);
/* 653 */     if (processItem != null) {
/* 654 */       outlines.addAll(getOutlines(processItem));
/*     */     }
/*     */     
/* 657 */     return outlines;
/*     */   }
/*     */   
/*     */   private void setPdfAOutputIntentColorSpace(PdfDictionary catalog) {
/* 661 */     PdfArray outputIntents = catalog.getAsArray(PdfName.OutputIntents);
/* 662 */     if (outputIntents == null) {
/*     */       return;
/*     */     }
/* 665 */     PdfDictionary pdfAOutputIntent = getPdfAOutputIntent(outputIntents);
/* 666 */     setCheckerOutputIntent(pdfAOutputIntent);
/*     */   }
/*     */   
/*     */   private PdfDictionary getPdfAOutputIntent(PdfArray outputIntents) {
/* 670 */     for (int i = 0; i < outputIntents.size(); i++) {
/* 671 */       PdfName outputIntentSubtype = outputIntents.getAsDictionary(i).getAsName(PdfName.S);
/* 672 */       if (PdfName.GTS_PDFA1.equals(outputIntentSubtype)) {
/* 673 */         return outputIntents.getAsDictionary(i);
/*     */       }
/*     */     } 
/*     */     
/* 677 */     return null;
/*     */   }
/*     */   
/*     */   private void setCheckerOutputIntent(PdfDictionary outputIntent) {
/* 681 */     if (outputIntent != null) {
/* 682 */       PdfStream destOutputProfile = outputIntent.getAsStream(PdfName.DestOutputProfile);
/* 683 */       if (destOutputProfile != null) {
/* 684 */         String intentCS = IccProfile.getIccColorSpaceName(destOutputProfile.getBytes());
/* 685 */         this.pdfAOutputIntentColorSpace = intentCS;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/pdfa/checker/PdfAChecker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */