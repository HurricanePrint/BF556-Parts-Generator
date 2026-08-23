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
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import com.itextpdf.kernel.pdf.tagging.IStructureNode;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfMcr;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfObjRef;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
/*     */ import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TagStructureContext
/*     */ {
/*  89 */   private static final Set<String> allowedRootTagRoles = new HashSet<>(); private PdfDocument document;
/*     */   
/*     */   static {
/*  92 */     allowedRootTagRoles.add("Document");
/*  93 */     allowedRootTagRoles.add("Part");
/*  94 */     allowedRootTagRoles.add("Art");
/*  95 */     allowedRootTagRoles.add("Sect");
/*  96 */     allowedRootTagRoles.add("Div");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfStructElem rootTagElement;
/*     */ 
/*     */   
/*     */   protected TagTreePointer autoTaggingPointer;
/*     */   
/*     */   private PdfVersion tagStructureTargetVersion;
/*     */   
/*     */   private boolean forbidUnknownRoles;
/*     */   
/*     */   private WaitingTagsManager waitingTagsManager;
/*     */   
/*     */   private Set<PdfDictionary> namespaces;
/*     */   
/*     */   private Map<String, PdfNamespace> nameToNamespace;
/*     */   
/*     */   private PdfNamespace documentDefaultNamespace;
/*     */ 
/*     */   
/*     */   public TagStructureContext(PdfDocument document) {
/* 120 */     this(document, document.getPdfVersion());
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
/*     */   public TagStructureContext(PdfDocument document, PdfVersion tagStructureTargetVersion) {
/* 133 */     this.document = document;
/* 134 */     if (!document.isTagged()) {
/* 135 */       throw new PdfException("Must be a tagged document.");
/*     */     }
/* 137 */     this.waitingTagsManager = new WaitingTagsManager();
/* 138 */     this.namespaces = new LinkedHashSet<>();
/* 139 */     this.nameToNamespace = new HashMap<>();
/*     */     
/* 141 */     this.tagStructureTargetVersion = tagStructureTargetVersion;
/* 142 */     this.forbidUnknownRoles = true;
/*     */     
/* 144 */     if (targetTagStructureVersionIs2()) {
/* 145 */       initRegisteredNamespaces();
/* 146 */       setNamespaceForNewTagsBasedOnExistingRoot();
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
/*     */   public TagStructureContext setForbidUnknownRoles(boolean forbidUnknownRoles) {
/* 158 */     this.forbidUnknownRoles = forbidUnknownRoles;
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public PdfVersion getTagStructureTargetVersion() {
/* 163 */     return this.tagStructureTargetVersion;
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
/*     */   public TagTreePointer getAutoTaggingPointer() {
/* 175 */     if (this.autoTaggingPointer == null) {
/* 176 */       this.autoTaggingPointer = new TagTreePointer(this.document);
/*     */     }
/* 178 */     return this.autoTaggingPointer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaitingTagsManager getWaitingTagsManager() {
/* 187 */     return this.waitingTagsManager;
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
/*     */   public PdfNamespace getDocumentDefaultNamespace() {
/* 202 */     return this.documentDefaultNamespace;
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
/*     */   public TagStructureContext setDocumentDefaultNamespace(PdfNamespace namespace) {
/* 221 */     this.documentDefaultNamespace = namespace;
/* 222 */     return this;
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
/*     */   public PdfNamespace fetchNamespace(String namespaceName) {
/* 240 */     PdfNamespace ns = this.nameToNamespace.get(namespaceName);
/* 241 */     if (ns == null) {
/* 242 */       ns = new PdfNamespace(namespaceName);
/* 243 */       this.nameToNamespace.put(namespaceName, ns);
/*     */     } 
/*     */     
/* 246 */     return ns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRoleMappingResolver getRoleMappingResolver(String role) {
/* 256 */     return getRoleMappingResolver(role, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRoleMappingResolver getRoleMappingResolver(String role, PdfNamespace namespace) {
/* 266 */     if (targetTagStructureVersionIs2()) {
/* 267 */       return new RoleMappingResolverPdf2(role, namespace, getDocument());
/*     */     }
/* 269 */     return new RoleMappingResolver(role, getDocument());
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
/*     */   public boolean checkIfRoleShallBeMappedToStandardRole(String role, PdfNamespace namespace) {
/* 283 */     return (resolveMappingToStandardOrDomainSpecificRole(role, namespace) != null);
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
/*     */   public IRoleMappingResolver resolveMappingToStandardOrDomainSpecificRole(String role, PdfNamespace namespace) {
/* 298 */     IRoleMappingResolver mappingResolver = getRoleMappingResolver(role, namespace);
/* 299 */     mappingResolver.resolveNextMapping();
/* 300 */     int i = 0;
/*     */     
/* 302 */     int maxIters = 100;
/* 303 */     while (mappingResolver.currentRoleShallBeMappedToStandard()) {
/* 304 */       if (++i > maxIters) {
/* 305 */         Logger logger = LoggerFactory.getLogger(TagStructureContext.class);
/* 306 */         logger.error(composeTooMuchTransitiveMappingsException(role, namespace));
/* 307 */         return null;
/*     */       } 
/* 309 */       if (!mappingResolver.resolveNextMapping()) {
/* 310 */         return null;
/*     */       }
/*     */     } 
/* 313 */     return mappingResolver;
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
/*     */   public TagTreePointer removeAnnotationTag(PdfAnnotation annotation) {
/* 325 */     PdfStructElem structElem = null;
/* 326 */     PdfDictionary annotDic = (PdfDictionary)annotation.getPdfObject();
/*     */     
/* 328 */     PdfNumber structParentIndex = (PdfNumber)annotDic.get(PdfName.StructParent);
/* 329 */     if (structParentIndex != null) {
/* 330 */       PdfObjRef objRef = this.document.getStructTreeRoot().findObjRefByStructParentIndex(annotDic.getAsDictionary(PdfName.P), structParentIndex.intValue());
/*     */       
/* 332 */       if (objRef != null) {
/* 333 */         PdfStructElem parent = (PdfStructElem)objRef.getParent();
/* 334 */         parent.removeKid((IStructureNode)objRef);
/* 335 */         structElem = parent;
/*     */       } 
/*     */     } 
/* 338 */     annotDic.remove(PdfName.StructParent);
/* 339 */     annotDic.setModified();
/*     */     
/* 341 */     if (structElem != null) {
/* 342 */       return (new TagTreePointer(this.document)).setCurrentStructElem(structElem);
/*     */     }
/* 344 */     return null;
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
/*     */   public TagTreePointer removeContentItem(PdfPage page, int mcid) {
/* 357 */     PdfMcr mcr = this.document.getStructTreeRoot().findMcrByMcid((PdfDictionary)page.getPdfObject(), mcid);
/* 358 */     if (mcr == null) {
/* 359 */       return null;
/*     */     }
/*     */     
/* 362 */     PdfStructElem parent = (PdfStructElem)mcr.getParent();
/* 363 */     parent.removeKid((IStructureNode)mcr);
/* 364 */     return (new TagTreePointer(this.document)).setCurrentStructElem(parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagStructureContext removePageTags(PdfPage page) {
/* 375 */     PdfStructTreeRoot structTreeRoot = this.document.getStructTreeRoot();
/* 376 */     Collection<PdfMcr> pageMcrs = structTreeRoot.getPageMarkedContentReferences(page);
/* 377 */     if (pageMcrs != null) {
/*     */       
/* 379 */       List<PdfMcr> mcrsList = new ArrayList<>(pageMcrs);
/* 380 */       for (PdfMcr mcr : mcrsList) {
/* 381 */         removePageTagFromParent((IStructureNode)mcr, mcr.getParent());
/*     */       }
/*     */     } 
/* 384 */     return this;
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
/*     */   public TagStructureContext flushPageTags(PdfPage page) {
/* 403 */     PdfStructTreeRoot structTreeRoot = this.document.getStructTreeRoot();
/* 404 */     Collection<PdfMcr> pageMcrs = structTreeRoot.getPageMarkedContentReferences(page);
/* 405 */     if (pageMcrs != null) {
/* 406 */       for (PdfMcr mcr : pageMcrs) {
/* 407 */         PdfStructElem parent = (PdfStructElem)mcr.getParent();
/* 408 */         flushParentIfBelongsToPage(parent, page);
/*     */       } 
/*     */     }
/*     */     
/* 412 */     return this;
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
/*     */   public void normalizeDocumentRootTag() {
/* 435 */     boolean forbid = this.forbidUnknownRoles;
/* 436 */     this.forbidUnknownRoles = false;
/*     */     
/* 438 */     List<IStructureNode> rootKids = this.document.getStructTreeRoot().getKids();
/* 439 */     IRoleMappingResolver mapping = null;
/* 440 */     if (rootKids.size() > 0) {
/* 441 */       PdfStructElem firstKid = (PdfStructElem)rootKids.get(0);
/* 442 */       mapping = resolveMappingToStandardOrDomainSpecificRole(firstKid.getRole().getValue(), firstKid.getNamespace());
/*     */     } 
/*     */     
/* 445 */     if (rootKids.size() == 1 && mapping != null && mapping
/* 446 */       .currentRoleIsStandard() && 
/* 447 */       isRoleAllowedToBeRoot(mapping.getRole())) {
/* 448 */       this.rootTagElement = (PdfStructElem)rootKids.get(0);
/*     */     } else {
/* 450 */       ((PdfDictionary)this.document.getStructTreeRoot().getPdfObject()).remove(PdfName.K);
/* 451 */       this.rootTagElement = (new RootTagNormalizer(this, this.rootTagElement, this.document)).makeSingleStandardRootTag(rootKids);
/*     */     } 
/* 453 */     this.forbidUnknownRoles = forbid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareToDocumentClosing() {
/* 461 */     this.waitingTagsManager.removeAllWaitingStates();
/* 462 */     actualizeNamespacesInStructTreeRoot();
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
/*     */   public PdfStructElem getPointerStructElem(TagTreePointer pointer) {
/* 475 */     return pointer.getCurrentStructElem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagTreePointer createPointerForStructElem(PdfStructElem structElem) {
/* 484 */     return new TagTreePointer(structElem, this.document);
/*     */   }
/*     */   
/*     */   PdfStructElem getRootTag() {
/* 488 */     if (this.rootTagElement == null) {
/* 489 */       normalizeDocumentRootTag();
/*     */     }
/* 491 */     return this.rootTagElement;
/*     */   }
/*     */   PdfDocument getDocument() {
/* 494 */     return this.document;
/*     */   }
/*     */   
/*     */   void ensureNamespaceRegistered(PdfNamespace namespace) {
/* 498 */     if (namespace != null) {
/* 499 */       PdfDictionary namespaceObj = (PdfDictionary)namespace.getPdfObject();
/* 500 */       if (!this.namespaces.contains(namespaceObj)) {
/* 501 */         this.namespaces.add(namespaceObj);
/*     */       }
/* 503 */       this.nameToNamespace.put(namespace.getNamespaceName(), namespace);
/*     */     } 
/*     */   }
/*     */   
/*     */   void throwExceptionIfRoleIsInvalid(AccessibilityProperties properties, PdfNamespace pointerCurrentNamespace) {
/* 508 */     PdfNamespace namespace = properties.getNamespace();
/* 509 */     if (namespace == null) {
/* 510 */       namespace = pointerCurrentNamespace;
/*     */     }
/* 512 */     throwExceptionIfRoleIsInvalid(properties.getRole(), namespace);
/*     */   }
/*     */   
/*     */   void throwExceptionIfRoleIsInvalid(String role, PdfNamespace namespace) {
/* 516 */     if (!checkIfRoleShallBeMappedToStandardRole(role, namespace)) {
/* 517 */       String exMessage = composeInvalidRoleException(role, namespace);
/* 518 */       if (this.forbidUnknownRoles) {
/* 519 */         throw new PdfException(exMessage);
/*     */       }
/* 521 */       Logger logger = LoggerFactory.getLogger(TagStructureContext.class);
/* 522 */       logger.warn(exMessage);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean targetTagStructureVersionIs2() {
/* 528 */     return (PdfVersion.PDF_2_0.compareTo(this.tagStructureTargetVersion) <= 0);
/*     */   }
/*     */   
/*     */   void flushParentIfBelongsToPage(PdfStructElem parent, PdfPage currentPage) {
/* 532 */     if (parent.isFlushed() || this.waitingTagsManager.getObjForStructDict((PdfDictionary)parent.getPdfObject()) != null || parent
/* 533 */       .getParent() instanceof PdfStructTreeRoot) {
/*     */       return;
/*     */     }
/*     */     
/* 537 */     List<IStructureNode> kids = parent.getKids();
/* 538 */     boolean readyToBeFlushed = true;
/* 539 */     for (IStructureNode kid : kids) {
/* 540 */       if (kid instanceof PdfMcr) {
/* 541 */         PdfDictionary kidPage = ((PdfMcr)kid).getPageObject();
/* 542 */         if (!kidPage.isFlushed() && (currentPage == null || !kidPage.equals(currentPage.getPdfObject()))) {
/* 543 */           readyToBeFlushed = false; break;
/*     */         }  continue;
/*     */       } 
/* 546 */       if (kid instanceof PdfStructElem) {
/*     */ 
/*     */         
/* 549 */         readyToBeFlushed = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 554 */     if (readyToBeFlushed) {
/* 555 */       IStructureNode parentsParent = parent.getParent();
/* 556 */       parent.flush();
/* 557 */       if (parentsParent instanceof PdfStructElem) {
/* 558 */         flushParentIfBelongsToPage((PdfStructElem)parentsParent, currentPage);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isRoleAllowedToBeRoot(String role) {
/* 564 */     if (targetTagStructureVersionIs2()) {
/* 565 */       return "Document".equals(role);
/*     */     }
/* 567 */     return allowedRootTagRoles.contains(role);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setNamespaceForNewTagsBasedOnExistingRoot() {
/* 572 */     List<IStructureNode> rootKids = this.document.getStructTreeRoot().getKids();
/* 573 */     if (rootKids.size() > 0) {
/* 574 */       PdfStructElem firstKid = (PdfStructElem)rootKids.get(0);
/* 575 */       IRoleMappingResolver resolvedMapping = resolveMappingToStandardOrDomainSpecificRole(firstKid.getRole().getValue(), firstKid.getNamespace());
/* 576 */       if (resolvedMapping == null || !resolvedMapping.currentRoleIsStandard()) {
/*     */         String nsStr;
/* 578 */         Logger logger = LoggerFactory.getLogger(TagStructureContext.class);
/*     */         
/* 580 */         if (firstKid.getNamespace() != null) {
/* 581 */           nsStr = firstKid.getNamespace().getNamespaceName();
/*     */         } else {
/* 583 */           nsStr = StandardNamespaces.getDefault();
/*     */         } 
/* 585 */         logger.warn(MessageFormat.format("Existing tag structure of the document has a root of \"{0}\" role in \"{1}\" namespace that is not mapped to the standard role.", new Object[] { firstKid.getRole().getValue(), nsStr }));
/*     */       } 
/* 587 */       if (resolvedMapping == null || !"http://iso.org/pdf/ssn".equals(resolvedMapping.getNamespace().getNamespaceName())) {
/* 588 */         this.documentDefaultNamespace = fetchNamespace("http://iso.org/pdf2/ssn");
/*     */       }
/*     */     } else {
/* 591 */       this.documentDefaultNamespace = fetchNamespace("http://iso.org/pdf2/ssn");
/*     */     } 
/*     */   }
/*     */   
/*     */   private String composeInvalidRoleException(String role, PdfNamespace namespace) {
/* 596 */     return composeExceptionBasedOnNamespacePresence(role, namespace, "Role \"{0}\" is not mapped to any standard role.", "Role \"{0}\" in namespace {1} is not mapped to any standard role.");
/*     */   }
/*     */ 
/*     */   
/*     */   private String composeTooMuchTransitiveMappingsException(String role, PdfNamespace namespace) {
/* 601 */     return composeExceptionBasedOnNamespacePresence(role, namespace, "Cannot resolve \"{0}\" role mapping to standard role, because of the too much transitive mappings.", "Cannot resolve \"{0}\" role in {1} namespace mapping to standard role, because of the too much transitive mappings.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initRegisteredNamespaces() {
/* 607 */     PdfStructTreeRoot structTreeRoot = this.document.getStructTreeRoot();
/* 608 */     for (PdfNamespace namespace : structTreeRoot.getNamespaces()) {
/* 609 */       this.namespaces.add(namespace.getPdfObject());
/* 610 */       this.nameToNamespace.put(namespace.getNamespaceName(), namespace);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void actualizeNamespacesInStructTreeRoot() {
/* 615 */     if (this.namespaces.size() > 0) {
/* 616 */       PdfStructTreeRoot structTreeRoot = getDocument().getStructTreeRoot();
/* 617 */       PdfArray rootNamespaces = structTreeRoot.getNamespacesObject();
/* 618 */       Set<PdfDictionary> newNamespaces = new LinkedHashSet<>(this.namespaces);
/* 619 */       for (int i = 0; i < rootNamespaces.size(); i++) {
/* 620 */         newNamespaces.remove(rootNamespaces.getAsDictionary(i));
/*     */       }
/* 622 */       for (PdfDictionary newNs : newNamespaces) {
/* 623 */         rootNamespaces.add((PdfObject)newNs);
/*     */       }
/* 625 */       if (!newNamespaces.isEmpty()) {
/* 626 */         structTreeRoot.setModified();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removePageTagFromParent(IStructureNode pageTag, IStructureNode parent) {
/* 632 */     if (parent instanceof PdfStructElem) {
/* 633 */       PdfStructElem structParent = (PdfStructElem)parent;
/* 634 */       if (!structParent.isFlushed()) {
/* 635 */         structParent.removeKid(pageTag);
/* 636 */         PdfDictionary parentStructDict = (PdfDictionary)structParent.getPdfObject();
/* 637 */         if (this.waitingTagsManager.getObjForStructDict(parentStructDict) == null && parent.getKids().size() == 0 && 
/* 638 */           !(structParent.getParent() instanceof PdfStructTreeRoot)) {
/* 639 */           removePageTagFromParent((IStructureNode)structParent, parent.getParent());
/* 640 */           PdfIndirectReference indRef = parentStructDict.getIndirectReference();
/* 641 */           if (indRef != null)
/*     */           {
/* 643 */             indRef.setFree();
/*     */           }
/*     */         }
/*     */       
/* 647 */       } else if (pageTag instanceof PdfMcr) {
/* 648 */         throw new PdfException("Cannot remove tag, because its parent is flushed.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String composeExceptionBasedOnNamespacePresence(String role, PdfNamespace namespace, String withoutNsEx, String withNsEx) {
/* 658 */     if (namespace == null) {
/* 659 */       return MessageFormat.format(withoutNsEx, new Object[] { role });
/*     */     }
/* 661 */     String nsName = namespace.getNamespaceName();
/* 662 */     PdfIndirectReference ref = ((PdfDictionary)namespace.getPdfObject()).getIndirectReference();
/* 663 */     if (ref != null)
/*     */     {
/* 665 */       nsName = nsName + " (" + Integer.toString(ref.getObjNumber()) + " " + Integer.toString(ref.getGenNumber()) + " obj)";
/*     */     }
/*     */     
/* 668 */     return MessageFormat.format(withNsEx, new Object[] { role, nsName });
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/TagStructureContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */