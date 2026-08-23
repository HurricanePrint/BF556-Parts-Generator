/*     */ package com.itextpdf.kernel.pdf.tagutils;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import com.itextpdf.kernel.pdf.tagging.IStructureNode;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfMcr;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfMcrDictionary;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfMcrNumber;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfObjRef;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TagTreePointer
/*     */ {
/*     */   private static final String MCR_MARKER = "MCR";
/*     */   private TagStructureContext tagStructureContext;
/*     */   private PdfStructElem currentStructElem;
/*     */   private PdfPage currentPage;
/*     */   private PdfStream contentStream;
/*     */   private PdfNamespace currentNamespace;
/*  97 */   private int nextNewKidIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer(PdfDocument document) {
/* 109 */     this.tagStructureContext = document.getTagStructureContext();
/* 110 */     setCurrentStructElem(this.tagStructureContext.getRootTag());
/* 111 */     setNamespaceForNewTags(this.tagStructureContext.getDocumentDefaultNamespace());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer(TagTreePointer tagPointer) {
/* 120 */     this.tagStructureContext = tagPointer.tagStructureContext;
/* 121 */     setCurrentStructElem(tagPointer.getCurrentStructElem());
/* 122 */     this.currentPage = tagPointer.currentPage;
/* 123 */     this.contentStream = tagPointer.contentStream;
/* 124 */     this.currentNamespace = tagPointer.currentNamespace;
/*     */   }
/*     */   
/*     */   TagTreePointer(PdfStructElem structElem, PdfDocument document) {
/* 128 */     this.tagStructureContext = document.getTagStructureContext();
/* 129 */     setCurrentStructElem(structElem);
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
/*     */   public TagTreePointer setPageForTagging(PdfPage page) {
/* 147 */     if (page.isFlushed()) {
/* 148 */       throw new PdfException("The page has been already flushed.");
/*     */     }
/* 150 */     this.currentPage = page;
/*     */     
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPage getCurrentPage() {
/* 159 */     return this.currentPage;
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
/*     */   public TagTreePointer setContentStreamForTagging(PdfStream contentStream) {
/* 175 */     this.contentStream = contentStream;
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfStream getCurrentContentStream() {
/* 183 */     return this.contentStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagStructureContext getContext() {
/* 190 */     return this.tagStructureContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDocument getDocument() {
/* 197 */     return this.tagStructureContext.getDocument();
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
/*     */   public TagTreePointer setNamespaceForNewTags(PdfNamespace namespace) {
/* 214 */     this.currentNamespace = namespace;
/* 215 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNamespace getNamespaceForNewTags() {
/* 224 */     return this.currentNamespace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer addTag(String role) {
/* 235 */     addTag(-1, role);
/* 236 */     return this;
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
/*     */   public TagTreePointer addTag(int index, String role) {
/* 250 */     this.tagStructureContext.throwExceptionIfRoleIsInvalid(role, this.currentNamespace);
/* 251 */     setNextNewKidIndex(index);
/* 252 */     setCurrentStructElem(addNewKid(role));
/* 253 */     return this;
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
/*     */   public TagTreePointer addTag(AccessibilityProperties properties) {
/* 266 */     addTag(-1, properties);
/* 267 */     return this;
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
/*     */   public TagTreePointer addTag(int index, AccessibilityProperties properties) {
/* 282 */     this.tagStructureContext.throwExceptionIfRoleIsInvalid(properties, this.currentNamespace);
/* 283 */     setNextNewKidIndex(index);
/* 284 */     setCurrentStructElem(addNewKid(properties));
/* 285 */     return this;
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
/*     */   public TagTreePointer addAnnotationTag(PdfAnnotation annotation) {
/* 299 */     throwExceptionIfCurrentPageIsNotInited();
/*     */     
/* 301 */     PdfObjRef kid = new PdfObjRef(annotation, getCurrentStructElem(), getDocument().getNextStructParentIndex());
/* 302 */     if (!ensureElementPageEqualsKidPage(getCurrentStructElem(), (PdfDictionary)this.currentPage.getPdfObject()))
/*     */     {
/* 304 */       ((PdfDictionary)kid.getPdfObject()).put(PdfName.Pg, (PdfObject)((PdfDictionary)this.currentPage.getPdfObject()).getIndirectReference());
/*     */     }
/* 306 */     addNewKid((PdfMcr)kid);
/* 307 */     return this;
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
/*     */   public TagTreePointer setNextNewKidIndex(int nextNewKidIndex) {
/* 325 */     if (nextNewKidIndex > -1) {
/* 326 */       this.nextNewKidIndex = nextNewKidIndex;
/*     */     }
/* 328 */     return this;
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
/*     */   public TagTreePointer removeTag() {
/* 341 */     PdfStructElem currentStructElem = getCurrentStructElem();
/* 342 */     IStructureNode parentElem = currentStructElem.getParent();
/* 343 */     if (parentElem instanceof PdfStructTreeRoot) {
/* 344 */       throw new PdfException("Cannot remove document root tag.");
/*     */     }
/*     */     
/* 347 */     List<IStructureNode> kids = currentStructElem.getKids();
/* 348 */     PdfStructElem parent = (PdfStructElem)parentElem;
/*     */     
/* 350 */     if (parent.isFlushed()) {
/* 351 */       throw new PdfException("Cannot remove tag, because its parent is flushed.");
/*     */     }
/*     */ 
/*     */     
/* 355 */     Object objForStructDict = this.tagStructureContext.getWaitingTagsManager().getObjForStructDict((PdfDictionary)currentStructElem.getPdfObject());
/* 356 */     this.tagStructureContext.getWaitingTagsManager().removeWaitingState(objForStructDict);
/*     */     
/* 358 */     int removedKidIndex = parent.removeKid((IStructureNode)currentStructElem);
/*     */     
/* 360 */     PdfIndirectReference indRef = ((PdfDictionary)currentStructElem.getPdfObject()).getIndirectReference();
/* 361 */     if (indRef != null)
/*     */     {
/* 363 */       indRef.setFree();
/*     */     }
/*     */     
/* 366 */     for (IStructureNode kid : kids) {
/* 367 */       if (kid instanceof PdfStructElem) {
/* 368 */         parent.addKid(removedKidIndex++, (PdfStructElem)kid); continue;
/*     */       } 
/* 370 */       PdfMcr mcr = prepareMcrForMovingToNewParent((PdfMcr)kid, parent);
/* 371 */       parent.addKid(removedKidIndex++, mcr);
/*     */     } 
/*     */     
/* 374 */     ((PdfDictionary)currentStructElem.getPdfObject()).clear();
/* 375 */     setCurrentStructElem(parent);
/* 376 */     return this;
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
/*     */   public TagTreePointer relocateKid(int kidIndex, TagTreePointer pointerToNewParent) {
/* 388 */     if (getDocument() != pointerToNewParent.getDocument()) {
/* 389 */       throw new PdfException("Tag cannot be moved to the another document's tag structure.");
/*     */     }
/* 391 */     if (getCurrentStructElem().isFlushed()) {
/* 392 */       throw new PdfException("Cannot relocate tag which parent is already flushed.");
/*     */     }
/*     */     
/* 395 */     if (isPointingToSameTag(pointerToNewParent)) {
/* 396 */       if (kidIndex == pointerToNewParent.nextNewKidIndex)
/* 397 */         return this; 
/* 398 */       if (kidIndex < pointerToNewParent.nextNewKidIndex) {
/* 399 */         pointerToNewParent.setNextNewKidIndex(pointerToNewParent.nextNewKidIndex - 1);
/*     */       }
/*     */     } 
/* 402 */     if (getCurrentStructElem().getKids().get(kidIndex) == null) {
/* 403 */       throw new PdfException("Cannot relocate tag which is already flushed.");
/*     */     }
/* 405 */     IStructureNode removedKid = getCurrentStructElem().removeKid(kidIndex, true);
/* 406 */     if (removedKid instanceof PdfStructElem) {
/* 407 */       pointerToNewParent.addNewKid((PdfStructElem)removedKid);
/* 408 */     } else if (removedKid instanceof PdfMcr) {
/* 409 */       PdfMcr mcrKid = prepareMcrForMovingToNewParent((PdfMcr)removedKid, pointerToNewParent.getCurrentStructElem());
/* 410 */       pointerToNewParent.addNewKid(mcrKid);
/*     */     } 
/*     */     
/* 413 */     return this;
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
/*     */   public TagTreePointer relocate(TagTreePointer pointerToNewParent) {
/* 425 */     if (getCurrentStructElem().getPdfObject() == this.tagStructureContext.getRootTag().getPdfObject()) {
/* 426 */       throw new PdfException("Cannot relocate root tag.");
/*     */     }
/* 428 */     if (getCurrentStructElem().isFlushed()) {
/* 429 */       throw new PdfException("Cannot relocate tag which is already flushed.");
/*     */     }
/* 431 */     int i = getIndexInParentKidsList();
/* 432 */     if (i < 0) {
/* 433 */       throw new PdfException("Cannot relocate tag which parent is already flushed.");
/*     */     }
/* 435 */     (new TagTreePointer(this)).moveToParent().relocateKid(i, pointerToNewParent);
/* 436 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagReference getTagReference() {
/* 446 */     return getTagReference(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagReference getTagReference(int index) {
/* 457 */     return new TagReference(getCurrentElemEnsureIndirect(), this, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer moveToRoot() {
/* 466 */     setCurrentStructElem(this.tagStructureContext.getRootTag());
/* 467 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer moveToParent() {
/* 476 */     if (getCurrentStructElem().getPdfObject() == this.tagStructureContext.getRootTag().getPdfObject()) {
/* 477 */       throw new PdfException("Cannot move to parent current element is root.");
/*     */     }
/*     */     
/* 480 */     PdfStructElem parent = (PdfStructElem)getCurrentStructElem().getParent();
/* 481 */     if (parent.isFlushed()) {
/* 482 */       Logger logger = LoggerFactory.getLogger(TagTreePointer.class);
/* 483 */       logger.warn("An attempt is made to move the tag tree pointer to the tag parent which has been already flushed. Tag tree pointer is moved to the root tag instead.");
/*     */       
/* 485 */       moveToRoot();
/*     */     } else {
/* 487 */       setCurrentStructElem(parent);
/*     */     } 
/* 489 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer moveToKid(int kidIndex) {
/* 499 */     IStructureNode kid = getCurrentStructElem().getKids().get(kidIndex);
/* 500 */     if (kid instanceof PdfStructElem)
/* 501 */     { setCurrentStructElem((PdfStructElem)kid); }
/* 502 */     else { if (kid instanceof PdfMcr) {
/* 503 */         throw new PdfException("Cannot move to marked content reference.");
/*     */       }
/* 505 */       throw new PdfException("Cannot move to flushed kid."); }
/*     */     
/* 507 */     return this;
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
/*     */   public TagTreePointer moveToKid(String role) {
/* 520 */     moveToKid(0, role);
/* 521 */     return this;
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
/*     */   public TagTreePointer moveToKid(int n, String role) {
/* 536 */     if ("MCR".equals(role)) {
/* 537 */       throw new PdfException("Cannot move to marked content reference.");
/*     */     }
/* 539 */     List<IStructureNode> descendants = new ArrayList<>(getCurrentStructElem().getKids());
/* 540 */     int k = 0;
/* 541 */     for (int i = 0; i < descendants.size(); i++) {
/* 542 */       if (descendants.get(i) != null && !(descendants.get(i) instanceof PdfMcr)) {
/*     */ 
/*     */         
/* 545 */         String descendantRole = ((IStructureNode)descendants.get(i)).getRole().getValue();
/* 546 */         if (descendantRole.equals(role) && k++ == n) {
/* 547 */           setCurrentStructElem((PdfStructElem)descendants.get(i));
/* 548 */           return this;
/*     */         } 
/* 550 */         descendants.addAll(((IStructureNode)descendants.get(i)).getKids());
/*     */       } 
/*     */     } 
/*     */     
/* 554 */     throw new PdfException("No kid with such role.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getKidsRoles() {
/* 565 */     List<String> roles = new ArrayList<>();
/* 566 */     List<IStructureNode> kids = getCurrentStructElem().getKids();
/* 567 */     for (IStructureNode kid : kids) {
/* 568 */       if (kid == null) {
/* 569 */         roles.add(null); continue;
/* 570 */       }  if (kid instanceof PdfStructElem) {
/* 571 */         roles.add(kid.getRole().getValue()); continue;
/*     */       } 
/* 573 */       roles.add("MCR");
/*     */     } 
/*     */     
/* 576 */     return roles;
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
/*     */   public TagTreePointer flushTag() {
/* 590 */     if (getCurrentStructElem().getPdfObject() == this.tagStructureContext.getRootTag().getPdfObject()) {
/* 591 */       throw new PdfException("Cannot flush document root tag before document is closed.");
/*     */     }
/* 593 */     IStructureNode parent = this.tagStructureContext.getWaitingTagsManager().flushTag(getCurrentStructElem());
/* 594 */     if (parent != null) {
/*     */ 
/*     */       
/* 597 */       setCurrentStructElem((PdfStructElem)parent);
/*     */     } else {
/* 599 */       setCurrentStructElem(this.tagStructureContext.getRootTag());
/*     */     } 
/* 601 */     return this;
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
/*     */   public TagTreePointer flushParentsIfAllKidsFlushed() {
/* 620 */     getContext().flushParentIfBelongsToPage(getCurrentStructElem(), null);
/* 621 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibilityProperties getProperties() {
/* 630 */     return new BackedAccessibilityProperties(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRole() {
/* 639 */     return getCurrentStructElem().getRole().getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer setRole(String role) {
/* 649 */     getCurrentStructElem().setRole(PdfStructTreeRoot.convertRoleToPdfName(role));
/* 650 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexInParentKidsList() {
/* 659 */     if (getCurrentStructElem().getPdfObject() == this.tagStructureContext.getRootTag().getPdfObject()) {
/* 660 */       return -1;
/*     */     }
/*     */     
/* 663 */     PdfStructElem parent = (PdfStructElem)getCurrentStructElem().getParent();
/* 664 */     if (parent.isFlushed()) {
/* 665 */       return -1;
/*     */     }
/* 667 */     PdfObject k = parent.getK();
/* 668 */     if (k == getCurrentStructElem().getPdfObject()) {
/* 669 */       return 0;
/*     */     }
/* 671 */     if (k.isArray()) {
/* 672 */       PdfArray kidsArr = (PdfArray)k;
/* 673 */       return kidsArr.indexOf(getCurrentStructElem().getPdfObject());
/*     */     } 
/* 675 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer moveToPointer(TagTreePointer tagTreePointer) {
/* 686 */     this.currentStructElem = tagTreePointer.currentStructElem;
/* 687 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPointingToSameTag(TagTreePointer otherPointer) {
/* 697 */     return ((PdfDictionary)getCurrentStructElem().getPdfObject()).equals(otherPointer.getCurrentStructElem().getPdfObject());
/*     */   }
/*     */   int createNextMcidForStructElem(PdfStructElem elem, int index) {
/*     */     PdfMcrDictionary pdfMcrDictionary;
/* 701 */     throwExceptionIfCurrentPageIsNotInited();
/*     */ 
/*     */     
/* 704 */     if (!markedContentNotInPageStream() && ensureElementPageEqualsKidPage(elem, (PdfDictionary)this.currentPage.getPdfObject())) {
/* 705 */       PdfMcrNumber pdfMcrNumber = new PdfMcrNumber(this.currentPage, elem);
/*     */     } else {
/* 707 */       pdfMcrDictionary = new PdfMcrDictionary(this.currentPage, elem);
/* 708 */       if (markedContentNotInPageStream()) {
/* 709 */         ((PdfDictionary)pdfMcrDictionary.getPdfObject()).put(PdfName.Stm, (PdfObject)this.contentStream);
/*     */       }
/*     */     } 
/* 712 */     elem.addKid(index, (PdfMcr)pdfMcrDictionary);
/* 713 */     return pdfMcrDictionary.getMcid();
/*     */   }
/*     */   
/*     */   TagTreePointer setCurrentStructElem(PdfStructElem structElem) {
/* 717 */     if (structElem.getParent() == null) {
/* 718 */       throw new PdfException("StructureElement shall contain parent object.");
/*     */     }
/*     */     
/* 721 */     this.currentStructElem = structElem;
/* 722 */     return this;
/*     */   }
/*     */   
/*     */   PdfStructElem getCurrentStructElem() {
/* 726 */     if (this.currentStructElem.isFlushed()) {
/* 727 */       throw new PdfException("TagTreePointer is in invalid state: it points at flushed element. Use TagTreePointer#moveToRoot.");
/*     */     }
/*     */     
/* 730 */     PdfIndirectReference indRef = ((PdfDictionary)this.currentStructElem.getPdfObject()).getIndirectReference();
/* 731 */     if (indRef != null && indRef.isFree())
/*     */     {
/*     */       
/* 734 */       throw new PdfException("TagTreePointer is in invalid state: it points at removed element use TagTreePointer#moveToRoot.");
/*     */     }
/*     */     
/* 737 */     return this.currentStructElem;
/*     */   }
/*     */   
/*     */   private int getNextNewKidPosition() {
/* 741 */     int nextPos = this.nextNewKidIndex;
/* 742 */     this.nextNewKidIndex = -1;
/* 743 */     return nextPos;
/*     */   }
/*     */   
/*     */   private PdfStructElem addNewKid(String role) {
/* 747 */     PdfStructElem kid = new PdfStructElem(getDocument(), PdfStructTreeRoot.convertRoleToPdfName(role));
/* 748 */     processKidNamespace(kid);
/* 749 */     return addNewKid(kid);
/*     */   }
/*     */   
/*     */   private PdfStructElem addNewKid(AccessibilityProperties properties) {
/* 753 */     PdfStructElem kid = new PdfStructElem(getDocument(), PdfStructTreeRoot.convertRoleToPdfName(properties.getRole()));
/* 754 */     AccessibilityPropertiesToStructElem.apply(properties, kid);
/* 755 */     processKidNamespace(kid);
/* 756 */     return addNewKid(kid);
/*     */   }
/*     */   
/*     */   private void processKidNamespace(PdfStructElem kid) {
/* 760 */     PdfNamespace kidNamespace = kid.getNamespace();
/* 761 */     if (this.currentNamespace != null && kidNamespace == null) {
/* 762 */       kid.setNamespace(this.currentNamespace);
/* 763 */       kidNamespace = this.currentNamespace;
/*     */     } 
/* 765 */     this.tagStructureContext.ensureNamespaceRegistered(kidNamespace);
/*     */   }
/*     */   
/*     */   private PdfStructElem addNewKid(PdfStructElem kid) {
/* 769 */     return getCurrentElemEnsureIndirect().addKid(getNextNewKidPosition(), kid);
/*     */   }
/*     */   
/*     */   private PdfMcr addNewKid(PdfMcr kid) {
/* 773 */     return getCurrentElemEnsureIndirect().addKid(getNextNewKidPosition(), kid);
/*     */   }
/*     */   
/*     */   private PdfStructElem getCurrentElemEnsureIndirect() {
/* 777 */     PdfStructElem currentStructElem = getCurrentStructElem();
/* 778 */     if (((PdfDictionary)currentStructElem.getPdfObject()).getIndirectReference() == null) {
/* 779 */       currentStructElem.makeIndirect(getDocument());
/*     */     }
/* 781 */     return currentStructElem;
/*     */   }
/*     */   private PdfMcr prepareMcrForMovingToNewParent(PdfMcr mcrKid, PdfStructElem newParent) {
/*     */     PdfMcrNumber pdfMcrNumber;
/* 785 */     PdfObject mcrObject = mcrKid.getPdfObject();
/* 786 */     PdfDictionary mcrPage = mcrKid.getPageObject();
/*     */     
/* 788 */     PdfDictionary mcrDict = null;
/* 789 */     if (!mcrObject.isNumber()) {
/* 790 */       mcrDict = (PdfDictionary)mcrObject;
/*     */     }
/* 792 */     if ((mcrDict == null || !mcrDict.containsKey(PdfName.Pg)) && 
/* 793 */       !ensureElementPageEqualsKidPage(newParent, mcrPage)) {
/* 794 */       if (mcrDict == null) {
/* 795 */         mcrDict = new PdfDictionary();
/* 796 */         mcrDict.put(PdfName.Type, (PdfObject)PdfName.MCR);
/* 797 */         mcrDict.put(PdfName.MCID, mcrKid.getPdfObject());
/*     */       } 
/*     */       
/* 800 */       mcrDict.put(PdfName.Pg, (PdfObject)mcrPage.getIndirectReference());
/*     */     } 
/*     */ 
/*     */     
/* 804 */     if (mcrDict != null) {
/* 805 */       if (PdfName.MCR.equals(mcrDict.get(PdfName.Type))) {
/* 806 */         PdfMcrDictionary pdfMcrDictionary = new PdfMcrDictionary(mcrDict, newParent);
/* 807 */       } else if (PdfName.OBJR.equals(mcrDict.get(PdfName.Type))) {
/* 808 */         PdfObjRef pdfObjRef = new PdfObjRef(mcrDict, newParent);
/*     */       } 
/*     */     } else {
/* 811 */       pdfMcrNumber = new PdfMcrNumber((PdfNumber)mcrObject, newParent);
/*     */     } 
/*     */     
/* 814 */     return (PdfMcr)pdfMcrNumber;
/*     */   }
/*     */   private boolean ensureElementPageEqualsKidPage(PdfStructElem elem, PdfDictionary kidPage) {
/*     */     PdfDictionary pdfDictionary;
/* 818 */     PdfObject pageObject = ((PdfDictionary)elem.getPdfObject()).get(PdfName.Pg);
/* 819 */     if (pageObject == null) {
/* 820 */       pdfDictionary = kidPage;
/*     */       
/* 822 */       elem.put(PdfName.Pg, (PdfObject)kidPage.getIndirectReference());
/*     */     } 
/*     */     
/* 825 */     return kidPage.equals(pdfDictionary);
/*     */   }
/*     */   
/*     */   private boolean markedContentNotInPageStream() {
/* 829 */     return (this.contentStream != null);
/*     */   }
/*     */   
/*     */   private void throwExceptionIfCurrentPageIsNotInited() {
/* 833 */     if (this.currentPage == null)
/* 834 */       throw new PdfException("Page is not set for the pdf tag structure."); 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/TagTreePointer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */