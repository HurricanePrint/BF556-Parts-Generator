/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*     */ import com.itextpdf.kernel.pdf.collection.PdfCollection;
/*     */ import com.itextpdf.kernel.pdf.layer.PdfOCProperties;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfDestination;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination;
/*     */ import com.itextpdf.kernel.pdf.navigation.PdfStringDestination;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class PdfCatalog
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -1354567597112193418L;
/*     */   private final PdfPagesTree pageTree;
/*  71 */   protected Map<PdfName, PdfNameTree> nameTrees = new LinkedHashMap<>();
/*     */   
/*     */   protected PdfNumTree pageLabels;
/*     */   
/*     */   protected PdfOCProperties ocProperties;
/*     */   private static final String OutlineRoot = "Outlines";
/*     */   private PdfOutline outlines;
/*  78 */   private Map<PdfObject, List<PdfOutline>> pagesWithOutlines = new HashMap<>();
/*     */   
/*     */   private boolean outlineMode;
/*     */   
/*  82 */   private static final Set<PdfName> PAGE_MODES = new HashSet<>(
/*  83 */       Arrays.asList(new PdfName[] { PdfName.UseNone, PdfName.UseOutlines, PdfName.UseThumbs, PdfName.FullScreen, PdfName.UseOC, PdfName.UseAttachments }));
/*     */ 
/*     */   
/*  86 */   private static final Set<PdfName> PAGE_LAYOUTS = new HashSet<>(
/*  87 */       Arrays.asList(new PdfName[] { PdfName.SinglePage, PdfName.OneColumn, PdfName.TwoColumnLeft, PdfName.TwoColumnRight, PdfName.TwoPageLeft, PdfName.TwoPageRight }));
/*     */ 
/*     */   
/*     */   protected PdfCatalog(PdfDictionary pdfObject) {
/*  91 */     super(pdfObject);
/*  92 */     if (pdfObject == null) {
/*  93 */       throw new PdfException("Document has no PDF Catalog object.");
/*     */     }
/*  95 */     ensureObjectIsAddedToDocument(pdfObject);
/*  96 */     getPdfObject().put(PdfName.Type, PdfName.Catalog);
/*  97 */     setForbidRelease();
/*  98 */     this.pageTree = new PdfPagesTree(this);
/*     */   }
/*     */   
/*     */   protected PdfCatalog(PdfDocument pdfDocument) {
/* 102 */     this((PdfDictionary)(new PdfDictionary()).makeIndirect(pdfDocument));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOCProperties getOCProperties(boolean createIfNotExists) {
/* 125 */     if (this.ocProperties != null) {
/* 126 */       return this.ocProperties;
/*     */     }
/* 128 */     PdfDictionary ocPropertiesDict = getPdfObject().getAsDictionary(PdfName.OCProperties);
/* 129 */     if (ocPropertiesDict != null) {
/* 130 */       if (getDocument().getWriter() != null) {
/* 131 */         ocPropertiesDict.makeIndirect(getDocument());
/*     */       }
/* 133 */       this.ocProperties = new PdfOCProperties(ocPropertiesDict);
/* 134 */     } else if (createIfNotExists) {
/* 135 */       this.ocProperties = new PdfOCProperties(getDocument());
/*     */     } 
/*     */     
/* 138 */     return this.ocProperties;
/*     */   }
/*     */   
/*     */   public PdfDocument getDocument() {
/* 142 */     return getPdfObject().getIndirectReference().getDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 150 */     Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 151 */     logger.warn("PdfCatalog cannot be flushed manually");
/*     */   }
/*     */   
/*     */   public PdfCatalog setOpenAction(PdfDestination destination) {
/* 155 */     return put(PdfName.OpenAction, destination.getPdfObject());
/*     */   }
/*     */   
/*     */   public PdfCatalog setOpenAction(PdfAction action) {
/* 159 */     return put(PdfName.OpenAction, action.getPdfObject());
/*     */   }
/*     */   
/*     */   public PdfCatalog setAdditionalAction(PdfName key, PdfAction action) {
/* 163 */     PdfAction.setAdditionalAction(this, key, action);
/* 164 */     return this;
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
/*     */   public PdfCatalog setPageMode(PdfName pageMode) {
/* 177 */     if (PAGE_MODES.contains(pageMode)) {
/* 178 */       return put(PdfName.PageMode, pageMode);
/*     */     }
/* 180 */     return this;
/*     */   }
/*     */   
/*     */   public PdfName getPageMode() {
/* 184 */     return getPdfObject().getAsName(PdfName.PageMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCatalog setPageLayout(PdfName pageLayout) {
/* 194 */     if (PAGE_LAYOUTS.contains(pageLayout)) {
/* 195 */       return put(PdfName.PageLayout, pageLayout);
/*     */     }
/* 197 */     return this;
/*     */   }
/*     */   
/*     */   public PdfName getPageLayout() {
/* 201 */     return getPdfObject().getAsName(PdfName.PageLayout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCatalog setViewerPreferences(PdfViewerPreferences preferences) {
/* 212 */     return put(PdfName.ViewerPreferences, preferences.getPdfObject());
/*     */   }
/*     */   
/*     */   public PdfViewerPreferences getViewerPreferences() {
/* 216 */     PdfDictionary viewerPreferences = getPdfObject().getAsDictionary(PdfName.ViewerPreferences);
/* 217 */     if (viewerPreferences != null) {
/* 218 */       return new PdfViewerPreferences(viewerPreferences);
/*     */     }
/* 220 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNameTree getNameTree(PdfName treeType) {
/* 231 */     PdfNameTree tree = this.nameTrees.get(treeType);
/* 232 */     if (tree == null) {
/* 233 */       tree = new PdfNameTree(this, treeType);
/* 234 */       this.nameTrees.put(treeType, tree);
/*     */     } 
/*     */     
/* 237 */     return tree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNumTree getPageLabelsTree(boolean createIfNotExists) {
/* 248 */     if (this.pageLabels == null && (getPdfObject().containsKey(PdfName.PageLabels) || createIfNotExists)) {
/* 249 */       this.pageLabels = new PdfNumTree(this, PdfName.PageLabels);
/*     */     }
/*     */     
/* 252 */     return this.pageLabels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLang(PdfString lang) {
/* 263 */     put(PdfName.Lang, lang);
/*     */   }
/*     */   
/*     */   public PdfString getLang() {
/* 267 */     return getPdfObject().getAsString(PdfName.Lang);
/*     */   }
/*     */   
/*     */   public void addDeveloperExtension(PdfDeveloperExtension extension) {
/* 271 */     PdfDictionary extensions = getPdfObject().getAsDictionary(PdfName.Extensions);
/*     */     
/* 273 */     if (extensions == null) {
/* 274 */       extensions = new PdfDictionary();
/* 275 */       put(PdfName.Extensions, extensions);
/*     */     } else {
/* 277 */       PdfDictionary existingExtensionDict = extensions.getAsDictionary(extension.getPrefix());
/* 278 */       if (existingExtensionDict != null) {
/* 279 */         int diff = extension.getBaseVersion().compareTo(existingExtensionDict.getAsName(PdfName.BaseVersion));
/* 280 */         if (diff < 0)
/*     */           return; 
/* 282 */         diff = extension.getExtensionLevel() - existingExtensionDict.getAsNumber(PdfName.ExtensionLevel).intValue();
/* 283 */         if (diff <= 0) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 288 */     extensions.put(extension.getPrefix(), extension.getDeveloperExtensions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCollection getCollection() {
/* 298 */     PdfDictionary collectionDictionary = getPdfObject().getAsDictionary(PdfName.Collection);
/* 299 */     if (collectionDictionary != null) {
/* 300 */       return new PdfCollection(collectionDictionary);
/*     */     }
/* 302 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCatalog setCollection(PdfCollection collection) {
/* 313 */     put(PdfName.Collection, collection.getPdfObject());
/* 314 */     return this;
/*     */   }
/*     */   
/*     */   public PdfCatalog put(PdfName key, PdfObject value) {
/* 318 */     getPdfObject().put(key, value);
/* 319 */     setModified();
/* 320 */     return this;
/*     */   }
/*     */   
/*     */   public PdfCatalog remove(PdfName key) {
/* 324 */     getPdfObject().remove(key);
/* 325 */     setModified();
/* 326 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 331 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isOCPropertiesMayHaveChanged() {
/* 341 */     return (this.ocProperties != null);
/*     */   }
/*     */   
/*     */   PdfPagesTree getPageTree() {
/* 345 */     return this.pageTree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Map<PdfObject, List<PdfOutline>> getPagesWithOutlines() {
/* 354 */     return this.pagesWithOutlines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addNamedDestination(String key, PdfObject value) {
/* 365 */     addNameToNameTree(key, value, PdfName.Dests);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addNameToNameTree(String key, PdfObject value, PdfName treeType) {
/* 376 */     getNameTree(treeType).addEntry(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfOutline getOutlines(boolean updateOutlines) {
/* 387 */     if (this.outlines != null && !updateOutlines)
/* 388 */       return this.outlines; 
/* 389 */     if (this.outlines != null) {
/* 390 */       this.outlines.clear();
/* 391 */       this.pagesWithOutlines.clear();
/*     */     } 
/*     */     
/* 394 */     this.outlineMode = true;
/* 395 */     PdfNameTree destsTree = getNameTree(PdfName.Dests);
/*     */     
/* 397 */     PdfDictionary outlineRoot = getPdfObject().getAsDictionary(PdfName.Outlines);
/* 398 */     if (outlineRoot == null) {
/* 399 */       if (null == getDocument().getWriter()) {
/* 400 */         return null;
/*     */       }
/* 402 */       this.outlines = new PdfOutline(getDocument());
/*     */     } else {
/* 404 */       constructOutlines(outlineRoot, destsTree.getNames());
/*     */     } 
/*     */     
/* 407 */     return this.outlines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasOutlines() {
/* 416 */     return getPdfObject().containsKey(PdfName.Outlines);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOutlineMode() {
/* 425 */     return this.outlineMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeOutlines(PdfPage page) {
/* 434 */     if (getDocument().getWriter() == null) {
/*     */       return;
/*     */     }
/* 437 */     if (hasOutlines()) {
/* 438 */       getOutlines(false);
/* 439 */       if (this.pagesWithOutlines.size() > 0 && 
/* 440 */         this.pagesWithOutlines.get(page.getPdfObject()) != null) {
/* 441 */         for (PdfOutline outline : this.pagesWithOutlines.get(page.getPdfObject())) {
/* 442 */           outline.removeOutline();
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addRootOutline(PdfOutline outline) {
/* 455 */     if (!this.outlineMode) {
/*     */       return;
/*     */     }
/* 458 */     if (this.pagesWithOutlines.size() == 0)
/* 459 */       put(PdfName.Outlines, outline.getContent()); 
/*     */   }
/*     */   
/*     */   PdfDestination copyDestination(PdfObject dest, Map<PdfPage, PdfPage> page2page, PdfDocument toDocument) {
/*     */     PdfStringDestination pdfStringDestination;
/* 464 */     PdfDestination d = null;
/* 465 */     if (dest.isArray()) {
/* 466 */       PdfObject pageObject = ((PdfArray)dest).get(0);
/* 467 */       for (PdfPage oldPage : page2page.keySet()) {
/* 468 */         if (oldPage.getPdfObject() == pageObject) {
/*     */           
/* 470 */           PdfArray copiedArray = (PdfArray)dest.copyTo(toDocument, false);
/* 471 */           PdfExplicitDestination pdfExplicitDestination = new PdfExplicitDestination(copiedArray);
/*     */           break;
/*     */         } 
/*     */       } 
/* 475 */     } else if (dest.isString() || dest.isName()) {
/* 476 */       PdfNameTree destsTree = getNameTree(PdfName.Dests);
/* 477 */       Map<String, PdfObject> dests = destsTree.getNames();
/* 478 */       String srcDestName = dest.isString() ? ((PdfString)dest).toUnicodeString() : ((PdfName)dest).getValue();
/* 479 */       PdfArray srcDestArray = (PdfArray)dests.get(srcDestName);
/* 480 */       if (srcDestArray != null) {
/* 481 */         PdfObject pageObject = srcDestArray.get(0);
/* 482 */         if (pageObject instanceof PdfNumber)
/* 483 */           pageObject = getDocument().getPage(((PdfNumber)pageObject).intValue() + 1).getPdfObject(); 
/* 484 */         for (PdfPage oldPage : page2page.keySet()) {
/* 485 */           if (oldPage.getPdfObject() == pageObject) {
/* 486 */             pdfStringDestination = new PdfStringDestination(srcDestName);
/* 487 */             if (!isEqualSameNameDestExist(page2page, toDocument, srcDestName, srcDestArray, oldPage)) {
/*     */               
/* 489 */               PdfArray copiedArray = (PdfArray)srcDestArray.copyTo(toDocument, false);
/*     */ 
/*     */ 
/*     */               
/* 493 */               copiedArray.set(0, ((PdfPage)page2page.get(oldPage)).getPdfObject());
/* 494 */               toDocument.addNamedDestination(srcDestName, copiedArray);
/*     */             } 
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 501 */     return (PdfDestination)pdfStringDestination;
/*     */   }
/*     */   
/*     */   PdfDictionary fillAndGetOcPropertiesDictionary() {
/* 505 */     if (this.ocProperties != null) {
/* 506 */       this.ocProperties.fillDictionary(false);
/* 507 */       getPdfObject().put(PdfName.OCProperties, this.ocProperties.getPdfObject());
/* 508 */       this.ocProperties = null;
/*     */     } 
/* 510 */     if (getPdfObject().getAsDictionary(PdfName.OCProperties) == null) {
/* 511 */       PdfDictionary pdfDictionary = new PdfDictionary();
/* 512 */       pdfDictionary.makeIndirect(getDocument());
/* 513 */       getDocument().getCatalog().getPdfObject().put(PdfName.OCProperties, pdfDictionary);
/*     */     } 
/* 515 */     return getPdfObject().getAsDictionary(PdfName.OCProperties);
/*     */   }
/*     */   
/*     */   private boolean isEqualSameNameDestExist(Map<PdfPage, PdfPage> page2page, PdfDocument toDocument, String srcDestName, PdfArray srcDestArray, PdfPage oldPage) {
/* 519 */     PdfArray sameNameDest = (PdfArray)toDocument.getCatalog().getNameTree(PdfName.Dests).getNames().get(srcDestName);
/* 520 */     boolean equalSameNameDestExists = false;
/* 521 */     if (sameNameDest != null && sameNameDest.getAsDictionary(0) != null) {
/* 522 */       PdfIndirectReference existingDestPageRef = sameNameDest.getAsDictionary(0).getIndirectReference();
/* 523 */       PdfIndirectReference newDestPageRef = ((PdfPage)page2page.get(oldPage)).getPdfObject().getIndirectReference();
/* 524 */       if (equalSameNameDestExists = (existingDestPageRef.equals(newDestPageRef) && sameNameDest.size() == srcDestArray.size())) {
/* 525 */         for (int i = 1; i < sameNameDest.size(); i++) {
/* 526 */           equalSameNameDestExists = (equalSameNameDestExists && sameNameDest.get(i).equals(srcDestArray.get(i)));
/*     */         }
/*     */       }
/*     */     } 
/* 530 */     return equalSameNameDestExists;
/*     */   }
/*     */   
/*     */   private void addOutlineToPage(PdfOutline outline, Map<String, PdfObject> names) {
/* 534 */     PdfObject pageObj = outline.getDestination().getDestinationPage(names);
/* 535 */     if (pageObj instanceof PdfNumber)
/* 536 */       pageObj = getDocument().getPage(((PdfNumber)pageObj).intValue() + 1).getPdfObject(); 
/* 537 */     if (pageObj != null) {
/* 538 */       List<PdfOutline> outs = this.pagesWithOutlines.get(pageObj);
/* 539 */       if (outs == null) {
/* 540 */         outs = new ArrayList<>();
/* 541 */         this.pagesWithOutlines.put(pageObj, outs);
/*     */       } 
/* 543 */       outs.add(outline);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfDictionary getNextOutline(PdfDictionary first, PdfDictionary next, PdfDictionary parent) {
/* 554 */     if (first != null)
/* 555 */       return first; 
/* 556 */     if (next != null) {
/* 557 */       return next;
/*     */     }
/* 559 */     return getParentNextOutline(parent);
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
/*     */   private PdfDictionary getParentNextOutline(PdfDictionary parent) {
/* 571 */     if (parent == null) {
/* 572 */       return null;
/*     */     }
/* 574 */     PdfDictionary current = null;
/* 575 */     while (current == null) {
/* 576 */       current = parent.getAsDictionary(PdfName.Next);
/* 577 */       if (current == null) {
/* 578 */         parent = parent.getAsDictionary(PdfName.Parent);
/* 579 */         if (parent == null) {
/* 580 */           return null;
/*     */         }
/*     */       } 
/*     */     } 
/* 584 */     return current;
/*     */   }
/*     */   
/*     */   private void addOutlineToPage(PdfOutline outline, PdfDictionary item, Map<String, PdfObject> names) {
/* 588 */     PdfObject dest = item.get(PdfName.Dest);
/* 589 */     if (dest != null) {
/* 590 */       PdfDestination destination = PdfDestination.makeDestination(dest);
/* 591 */       outline.setDestination(destination);
/* 592 */       addOutlineToPage(outline, names);
/*     */     } else {
/*     */       
/* 595 */       PdfDictionary action = item.getAsDictionary(PdfName.A);
/* 596 */       if (action != null) {
/* 597 */         PdfName actionType = action.getAsName(PdfName.S);
/*     */         
/* 599 */         if (PdfName.GoTo.equals(actionType)) {
/*     */           
/* 601 */           PdfObject destObject = action.get(PdfName.D);
/* 602 */           if (destObject != null) {
/*     */             
/* 604 */             PdfDestination destination = PdfDestination.makeDestination(destObject);
/* 605 */             outline.setDestination(destination);
/* 606 */             addOutlineToPage(outline, names);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void constructOutlines(PdfDictionary outlineRoot, Map<String, PdfObject> names) {
/* 617 */     if (outlineRoot == null) {
/*     */       return;
/*     */     }
/* 620 */     PdfDictionary first = outlineRoot.getAsDictionary(PdfName.First);
/* 621 */     PdfDictionary current = first;
/*     */ 
/*     */     
/* 624 */     HashMap<PdfDictionary, PdfOutline> parentOutlineMap = new HashMap<>();
/*     */     
/* 626 */     this.outlines = new PdfOutline("Outlines", outlineRoot, getDocument());
/* 627 */     PdfOutline parentOutline = this.outlines;
/* 628 */     parentOutlineMap.put(outlineRoot, parentOutline);
/*     */     
/* 630 */     while (current != null) {
/* 631 */       first = current.getAsDictionary(PdfName.First);
/* 632 */       PdfDictionary next = current.getAsDictionary(PdfName.Next);
/* 633 */       PdfDictionary parent = current.getAsDictionary(PdfName.Parent);
/*     */       
/* 635 */       parentOutline = parentOutlineMap.get(parent);
/* 636 */       PdfOutline currentOutline = new PdfOutline(current.getAsString(PdfName.Title).toUnicodeString(), current, parentOutline);
/* 637 */       addOutlineToPage(currentOutline, current, names);
/* 638 */       parentOutline.getAllChildren().add(currentOutline);
/*     */       
/* 640 */       if (first != null) {
/* 641 */         parentOutlineMap.put(current, currentOutline);
/*     */       }
/* 643 */       current = getNextOutline(first, next, parent);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfCatalog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */