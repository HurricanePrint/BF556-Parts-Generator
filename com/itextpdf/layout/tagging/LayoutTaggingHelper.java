/*     */ package com.itextpdf.layout.tagging;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
/*     */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*     */ import com.itextpdf.kernel.pdf.tagutils.WaitingTagsManager;
/*     */ import com.itextpdf.layout.IPropertyContainer;
/*     */ import com.itextpdf.layout.element.ILargeElement;
/*     */ import com.itextpdf.layout.renderer.IRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayoutTaggingHelper
/*     */ {
/*     */   private TagStructureContext context;
/*     */   private PdfDocument document;
/*     */   private boolean immediateFlush;
/*     */   private Map<TaggingHintKey, List<TaggingHintKey>> kidsHints;
/*     */   private Map<TaggingHintKey, TaggingHintKey> parentHints;
/*     */   private Map<IRenderer, TagTreePointer> autoTaggingPointerSavedPosition;
/*     */   private Map<String, List<ITaggingRule>> taggingRules;
/*     */   private Map<PdfObject, TaggingDummyElement> existingTagsDummies;
/*  87 */   private final int RETVAL_NO_PARENT = -1;
/*  88 */   private final int RETVAL_PARENT_AND_KID_FINISHED = -2;
/*     */   
/*     */   public LayoutTaggingHelper(PdfDocument document, boolean immediateFlush) {
/*  91 */     this.document = document;
/*  92 */     this.context = document.getTagStructureContext();
/*  93 */     this.immediateFlush = immediateFlush;
/*     */     
/*  95 */     this.kidsHints = new LinkedHashMap<>();
/*  96 */     this.parentHints = new LinkedHashMap<>();
/*  97 */     this.autoTaggingPointerSavedPosition = new HashMap<>();
/*     */     
/*  99 */     this.taggingRules = new HashMap<>();
/* 100 */     registerRules(this.context.getTagStructureTargetVersion());
/*     */     
/* 102 */     this.existingTagsDummies = new LinkedHashMap<>();
/*     */   }
/*     */   
/*     */   public static void addTreeHints(LayoutTaggingHelper taggingHelper, IRenderer rootRenderer) {
/* 106 */     List<IRenderer> childRenderers = rootRenderer.getChildRenderers();
/* 107 */     if (childRenderers == null) {
/*     */       return;
/*     */     }
/* 110 */     taggingHelper.addKidsHint((IPropertyContainer)rootRenderer, (Iterable)childRenderers);
/* 111 */     for (IRenderer childRenderer : childRenderers) {
/* 112 */       addTreeHints(taggingHelper, childRenderer);
/*     */     }
/*     */   }
/*     */   
/*     */   public static TaggingHintKey getHintKey(IPropertyContainer container) {
/* 117 */     return (TaggingHintKey)container.getProperty(109);
/*     */   }
/*     */   
/*     */   public static TaggingHintKey getOrCreateHintKey(IPropertyContainer container) {
/* 121 */     return getOrCreateHintKey(container, true);
/*     */   }
/*     */   
/*     */   public void addKidsHint(TagTreePointer parentPointer, Iterable<? extends IPropertyContainer> newKids) {
/* 125 */     PdfDictionary pointerStructElem = (PdfDictionary)this.context.getPointerStructElem(parentPointer).getPdfObject();
/* 126 */     TaggingDummyElement dummy = this.existingTagsDummies.get(pointerStructElem);
/* 127 */     if (dummy == null) {
/* 128 */       dummy = new TaggingDummyElement(parentPointer.getRole());
/* 129 */       this.existingTagsDummies.put(pointerStructElem, dummy);
/*     */     } 
/* 131 */     this.context.getWaitingTagsManager().assignWaitingState(parentPointer, getOrCreateHintKey(dummy));
/* 132 */     addKidsHint(dummy, newKids);
/*     */   }
/*     */   
/*     */   public void addKidsHint(IPropertyContainer parent, Iterable<? extends IPropertyContainer> newKids) {
/* 136 */     addKidsHint(parent, newKids, -1);
/*     */   }
/*     */   
/*     */   public void addKidsHint(IPropertyContainer parent, Iterable<? extends IPropertyContainer> newKids, int insertIndex) {
/* 140 */     if (parent instanceof com.itextpdf.layout.renderer.AreaBreakRenderer) {
/*     */       return;
/*     */     }
/*     */     
/* 144 */     TaggingHintKey parentKey = getOrCreateHintKey(parent);
/*     */     
/* 146 */     List<TaggingHintKey> newKidsKeys = new ArrayList<>();
/* 147 */     for (IPropertyContainer kid : newKids) {
/* 148 */       if (kid instanceof com.itextpdf.layout.renderer.AreaBreakRenderer) {
/*     */         return;
/*     */       }
/* 151 */       newKidsKeys.add(getOrCreateHintKey(kid));
/*     */     } 
/* 153 */     addKidsHint(parentKey, newKidsKeys, insertIndex);
/*     */   }
/*     */   
/*     */   public void addKidsHint(TaggingHintKey parentKey, Collection<TaggingHintKey> newKidsKeys) {
/* 157 */     addKidsHint(parentKey, newKidsKeys, -1);
/*     */   }
/*     */   
/*     */   public void addKidsHint(TaggingHintKey parentKey, Collection<TaggingHintKey> newKidsKeys, int insertIndex) {
/* 161 */     addKidsHint(parentKey, newKidsKeys, insertIndex, false);
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
/*     */   public void setRoleHint(IPropertyContainer hintOwner, String role) {
/* 173 */     getOrCreateHintKey(hintOwner).setOverriddenRole(role);
/*     */   }
/*     */   
/*     */   public boolean isArtifact(IPropertyContainer hintOwner) {
/* 177 */     TaggingHintKey key = getHintKey(hintOwner);
/* 178 */     if (key != null) {
/* 179 */       return key.isArtifact();
/*     */     }
/* 181 */     IAccessibleElement aElem = null;
/* 182 */     if (hintOwner instanceof IRenderer && ((IRenderer)hintOwner).getModelElement() instanceof IAccessibleElement) {
/* 183 */       aElem = (IAccessibleElement)((IRenderer)hintOwner).getModelElement();
/* 184 */     } else if (hintOwner instanceof IAccessibleElement) {
/* 185 */       aElem = (IAccessibleElement)hintOwner;
/*     */     } 
/* 187 */     if (aElem != null) {
/* 188 */       return "Artifact".equals(aElem.getAccessibilityProperties().getRole());
/*     */     }
/*     */     
/* 191 */     return false;
/*     */   }
/*     */   
/*     */   public void markArtifactHint(IPropertyContainer hintOwner) {
/* 195 */     TaggingHintKey hintKey = getOrCreateHintKey(hintOwner);
/* 196 */     markArtifactHint(hintKey);
/*     */   }
/*     */   
/*     */   public void markArtifactHint(TaggingHintKey hintKey) {
/* 200 */     hintKey.setArtifact();
/* 201 */     hintKey.setFinished();
/* 202 */     TagTreePointer existingArtifactTag = new TagTreePointer(this.document);
/* 203 */     if (this.context.getWaitingTagsManager().tryMovePointerToWaitingTag(existingArtifactTag, hintKey)) {
/* 204 */       Logger logger = LoggerFactory.getLogger(LayoutTaggingHelper.class);
/* 205 */       logger.error("A layout tagging hint for which an actual tag was already created in tags structure is marked as artifact. Existing tag will be left in the tags tree.");
/*     */       
/* 207 */       this.context.getWaitingTagsManager().removeWaitingState(hintKey);
/* 208 */       if (this.immediateFlush) {
/* 209 */         existingArtifactTag.flushParentsIfAllKidsFlushed();
/*     */       }
/*     */     } 
/* 212 */     List<TaggingHintKey> kidsHint = getKidsHint(hintKey);
/* 213 */     for (TaggingHintKey kidKey : kidsHint) {
/* 214 */       markArtifactHint(kidKey);
/*     */     }
/* 216 */     removeParentHint(hintKey);
/*     */   }
/*     */   
/*     */   public TagTreePointer useAutoTaggingPointerAndRememberItsPosition(IRenderer renderer) {
/* 220 */     TagTreePointer autoTaggingPointer = this.context.getAutoTaggingPointer();
/* 221 */     TagTreePointer position = new TagTreePointer(autoTaggingPointer);
/* 222 */     this.autoTaggingPointerSavedPosition.put(renderer, position);
/* 223 */     return autoTaggingPointer;
/*     */   }
/*     */   
/*     */   public void restoreAutoTaggingPointerPosition(IRenderer renderer) {
/* 227 */     TagTreePointer autoTaggingPointer = this.context.getAutoTaggingPointer();
/* 228 */     TagTreePointer position = this.autoTaggingPointerSavedPosition.remove(renderer);
/* 229 */     if (position != null) {
/* 230 */       autoTaggingPointer.moveToPointer(position);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<TaggingHintKey> getKidsHint(TaggingHintKey parent) {
/* 235 */     List<TaggingHintKey> kidsHint = this.kidsHints.get(parent);
/* 236 */     if (kidsHint == null) {
/* 237 */       return Collections.emptyList();
/*     */     }
/* 239 */     return Collections.unmodifiableList(kidsHint);
/*     */   }
/*     */   
/*     */   public List<TaggingHintKey> getAccessibleKidsHint(TaggingHintKey parent) {
/* 243 */     List<TaggingHintKey> kidsHint = this.kidsHints.get(parent);
/* 244 */     if (kidsHint == null) {
/* 245 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 248 */     List<TaggingHintKey> accessibleKids = new ArrayList<>();
/*     */     
/* 250 */     for (TaggingHintKey kid : kidsHint) {
/* 251 */       if (isNonAccessibleHint(kid)) {
/* 252 */         accessibleKids.addAll(getAccessibleKidsHint(kid)); continue;
/*     */       } 
/* 254 */       accessibleKids.add(kid);
/*     */     } 
/*     */ 
/*     */     
/* 258 */     return accessibleKids;
/*     */   }
/*     */   
/*     */   public TaggingHintKey getParentHint(IPropertyContainer hintOwner) {
/* 262 */     TaggingHintKey hintKey = getHintKey(hintOwner);
/* 263 */     if (hintKey == null) {
/* 264 */       return null;
/*     */     }
/* 266 */     return getParentHint(hintKey);
/*     */   }
/*     */   
/*     */   public TaggingHintKey getParentHint(TaggingHintKey hintKey) {
/* 270 */     return this.parentHints.get(hintKey);
/*     */   }
/*     */   
/*     */   public TaggingHintKey getAccessibleParentHint(TaggingHintKey hintKey) {
/*     */     do {
/* 275 */       hintKey = getParentHint(hintKey);
/* 276 */     } while (hintKey != null && isNonAccessibleHint(hintKey));
/* 277 */     return hintKey;
/*     */   }
/*     */   
/*     */   public void releaseFinishedHints() {
/* 281 */     Set<TaggingHintKey> allHints = new HashSet<>();
/* 282 */     for (Map.Entry<TaggingHintKey, TaggingHintKey> entry : this.parentHints.entrySet()) {
/* 283 */       allHints.add(entry.getKey());
/* 284 */       allHints.add(entry.getValue());
/*     */     } 
/*     */     
/* 287 */     for (TaggingHintKey hint : allHints) {
/* 288 */       if (!hint.isFinished() || isNonAccessibleHint(hint) || hint.getAccessibleElement() instanceof TaggingDummyElement) {
/*     */         continue;
/*     */       }
/* 291 */       finishDummyKids(getKidsHint(hint));
/*     */     } 
/*     */     
/* 294 */     Set<TaggingHintKey> hintsToBeHeld = new HashSet<>();
/* 295 */     for (TaggingHintKey hint : allHints) {
/* 296 */       if (!isNonAccessibleHint(hint)) {
/* 297 */         List<TaggingHintKey> siblingsHints = getAccessibleKidsHint(hint);
/* 298 */         boolean holdTheFirstFinishedToBeFound = false;
/* 299 */         for (TaggingHintKey sibling : siblingsHints) {
/* 300 */           if (!sibling.isFinished()) {
/* 301 */             holdTheFirstFinishedToBeFound = true; continue;
/* 302 */           }  if (holdTheFirstFinishedToBeFound) {
/*     */             
/* 304 */             hintsToBeHeld.add(sibling);
/* 305 */             holdTheFirstFinishedToBeFound = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 311 */     for (TaggingHintKey hint : allHints) {
/* 312 */       if (hint.isFinished()) {
/* 313 */         releaseHint(hint, hintsToBeHeld, true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseAllHints() {
/* 319 */     for (TaggingDummyElement dummy : this.existingTagsDummies.values()) {
/* 320 */       finishTaggingHint(dummy);
/* 321 */       finishDummyKids(getKidsHint(getHintKey(dummy)));
/*     */     } 
/* 323 */     this.existingTagsDummies.clear();
/*     */     
/* 325 */     releaseFinishedHints();
/*     */     
/* 327 */     Set<TaggingHintKey> hangingHints = new HashSet<>();
/* 328 */     for (Map.Entry<TaggingHintKey, TaggingHintKey> entry : this.parentHints.entrySet()) {
/* 329 */       hangingHints.add(entry.getKey());
/* 330 */       hangingHints.add(entry.getValue());
/*     */     } 
/*     */     
/* 333 */     for (TaggingHintKey hint : hangingHints)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 342 */       releaseHint(hint, null, false);
/*     */     }
/*     */     
/* 345 */     assert this.parentHints.isEmpty();
/* 346 */     assert this.kidsHints.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean createTag(IRenderer renderer, TagTreePointer tagPointer) {
/* 350 */     TaggingHintKey hintKey = getHintKey((IPropertyContainer)renderer);
/* 351 */     boolean noHint = (hintKey == null);
/* 352 */     if (noHint) {
/* 353 */       hintKey = getOrCreateHintKey((IPropertyContainer)renderer, false);
/*     */     }
/* 355 */     boolean created = createTag(hintKey, tagPointer);
/* 356 */     if (noHint) {
/* 357 */       hintKey.setFinished();
/* 358 */       this.context.getWaitingTagsManager().removeWaitingState(hintKey);
/*     */     } 
/* 360 */     return created;
/*     */   }
/*     */   
/*     */   public boolean createTag(TaggingHintKey hintKey, TagTreePointer tagPointer) {
/* 364 */     if (hintKey.isArtifact()) {
/* 365 */       return false;
/*     */     }
/*     */     
/* 368 */     boolean created = createSingleTag(hintKey, tagPointer);
/*     */     
/* 370 */     if (created) {
/* 371 */       List<TaggingHintKey> kidsHint = getAccessibleKidsHint(hintKey);
/* 372 */       for (TaggingHintKey hint : kidsHint) {
/* 373 */         if (hint.getAccessibleElement() instanceof TaggingDummyElement) {
/* 374 */           createTag(hint, new TagTreePointer(this.document));
/*     */         }
/*     */       } 
/*     */     } 
/* 378 */     return created;
/*     */   }
/*     */   
/*     */   public void finishTaggingHint(IPropertyContainer hintOwner) {
/* 382 */     TaggingHintKey rendererKey = getHintKey(hintOwner);
/*     */ 
/*     */     
/* 385 */     if (rendererKey == null || rendererKey.isFinished()) {
/*     */       return;
/*     */     }
/*     */     
/* 389 */     if (rendererKey.isElementBasedFinishingOnly() && !(hintOwner instanceof com.itextpdf.layout.element.IElement)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 394 */     if (!isNonAccessibleHint(rendererKey)) {
/* 395 */       IAccessibleElement modelElement = rendererKey.getAccessibleElement();
/* 396 */       String role = modelElement.getAccessibilityProperties().getRole();
/* 397 */       if (rendererKey.getOverriddenRole() != null) {
/* 398 */         role = rendererKey.getOverriddenRole();
/*     */       }
/* 400 */       List<ITaggingRule> rules = this.taggingRules.get(role);
/* 401 */       boolean ruleResult = true;
/* 402 */       if (rules != null) {
/* 403 */         for (ITaggingRule rule : rules) {
/* 404 */           ruleResult = (ruleResult && rule.onTagFinish(this, rendererKey));
/*     */         }
/*     */       }
/* 407 */       if (!ruleResult) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 412 */     rendererKey.setFinished();
/*     */   }
/*     */   
/*     */   public int replaceKidHint(TaggingHintKey kidHintKey, Collection<TaggingHintKey> newKidsHintKeys) {
/* 416 */     TaggingHintKey parentKey = getParentHint(kidHintKey);
/* 417 */     if (parentKey == null) {
/* 418 */       return -1;
/*     */     }
/* 420 */     if (kidHintKey.isFinished()) {
/* 421 */       Logger logger = LoggerFactory.getLogger(LayoutTaggingHelper.class);
/* 422 */       logger.error("Layout tagging hints modification failed: cannot replace a kid hint that is already marked as finished.");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 427 */       return -1;
/*     */     } 
/*     */     
/* 430 */     int kidIndex = removeParentHint(kidHintKey);
/*     */     
/* 432 */     List<TaggingHintKey> kidsToBeAdded = new ArrayList<>();
/* 433 */     for (TaggingHintKey newKidKey : newKidsHintKeys) {
/* 434 */       int i = removeParentHint(newKidKey);
/* 435 */       if (i == -2 || (i == -1 && newKidKey
/* 436 */         .isFinished())) {
/* 437 */         Logger logger = LoggerFactory.getLogger(LayoutTaggingHelper.class);
/* 438 */         logger.error("Layout tagging hints modification failed: cannot move kid hint for which both itself and it's parent are already marked as finished.");
/*     */         continue;
/*     */       } 
/* 441 */       kidsToBeAdded.add(newKidKey);
/*     */     } 
/*     */     
/* 444 */     addKidsHint(parentKey, kidsToBeAdded, kidIndex, true);
/*     */     
/* 446 */     return kidIndex;
/*     */   }
/*     */   
/*     */   public int moveKidHint(TaggingHintKey hintKeyOfKidToMove, TaggingHintKey newParent) {
/* 450 */     return moveKidHint(hintKeyOfKidToMove, newParent, -1);
/*     */   }
/*     */   
/*     */   public int moveKidHint(TaggingHintKey hintKeyOfKidToMove, TaggingHintKey newParent, int insertIndex) {
/* 454 */     if (newParent.isFinished()) {
/* 455 */       Logger logger = LoggerFactory.getLogger(LayoutTaggingHelper.class);
/* 456 */       logger.error("Layout tagging hints modification failed: cannot move kid hint to a parent that is already marked as finished.");
/* 457 */       return -1;
/*     */     } 
/*     */     
/* 460 */     int removeRes = removeParentHint(hintKeyOfKidToMove);
/* 461 */     if (removeRes == -2 || (removeRes == -1 && hintKeyOfKidToMove
/* 462 */       .isFinished())) {
/* 463 */       Logger logger = LoggerFactory.getLogger(LayoutTaggingHelper.class);
/* 464 */       logger.error("Layout tagging hints modification failed: cannot move kid hint for which both itself and it's parent are already marked as finished.");
/* 465 */       return -1;
/*     */     } 
/* 467 */     addKidsHint(newParent, Collections.singletonList(hintKeyOfKidToMove), insertIndex, true);
/*     */     
/* 469 */     return removeRes;
/*     */   }
/*     */   
/*     */   public PdfDocument getPdfDocument() {
/* 473 */     return this.document;
/*     */   }
/*     */   
/*     */   private static TaggingHintKey getOrCreateHintKey(IPropertyContainer hintOwner, boolean setProperty) {
/* 477 */     TaggingHintKey hintKey = (TaggingHintKey)hintOwner.getProperty(109);
/* 478 */     if (hintKey == null) {
/* 479 */       IAccessibleElement elem = null;
/* 480 */       if (hintOwner instanceof IAccessibleElement) {
/* 481 */         elem = (IAccessibleElement)hintOwner;
/* 482 */       } else if (hintOwner instanceof IRenderer && ((IRenderer)hintOwner).getModelElement() instanceof IAccessibleElement) {
/* 483 */         elem = (IAccessibleElement)((IRenderer)hintOwner).getModelElement();
/*     */       } 
/* 485 */       hintKey = new TaggingHintKey(elem, hintOwner instanceof com.itextpdf.layout.element.IElement);
/* 486 */       if (elem != null && "Artifact".equals(elem.getAccessibilityProperties().getRole())) {
/* 487 */         hintKey.setArtifact();
/* 488 */         hintKey.setFinished();
/*     */       } 
/*     */       
/* 491 */       if (setProperty) {
/* 492 */         if (elem instanceof ILargeElement && !((ILargeElement)elem).isComplete()) {
/* 493 */           ((ILargeElement)elem).setProperty(109, hintKey);
/*     */         } else {
/* 495 */           hintOwner.setProperty(109, hintKey);
/*     */         } 
/*     */       }
/*     */     } 
/* 499 */     return hintKey;
/*     */   }
/*     */   
/*     */   private void addKidsHint(TaggingHintKey parentKey, Collection<TaggingHintKey> newKidsKeys, int insertIndex, boolean skipFinishedChecks) {
/* 503 */     if (newKidsKeys.isEmpty()) {
/*     */       return;
/*     */     }
/* 506 */     if (parentKey.isArtifact()) {
/* 507 */       for (TaggingHintKey kid : newKidsKeys) {
/* 508 */         markArtifactHint(kid);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 513 */     if (!skipFinishedChecks && parentKey.isFinished()) {
/* 514 */       Logger logger = LoggerFactory.getLogger(LayoutTaggingHelper.class);
/* 515 */       logger.error("Layout tagging hints addition failed: cannot add new kid hints to a parent which hint is already marked as finished. Consider using com.itextpdf.layout.tagging.LayoutTaggingHelper#replaceKidHint method for replacing not yet finished kid hint of a finished parent hint.");
/*     */       
/*     */       return;
/*     */     } 
/* 519 */     List<TaggingHintKey> kidsHint = this.kidsHints.get(parentKey);
/* 520 */     if (kidsHint == null) {
/* 521 */       kidsHint = new ArrayList<>();
/*     */     }
/*     */     
/* 524 */     TaggingHintKey parentTagHint = isNonAccessibleHint(parentKey) ? getAccessibleParentHint(parentKey) : parentKey;
/* 525 */     boolean parentTagAlreadyCreated = (parentTagHint != null && isTagAlreadyExistsForHint(parentTagHint));
/* 526 */     for (TaggingHintKey kidKey : newKidsKeys) {
/* 527 */       if (kidKey.isArtifact()) {
/*     */         continue;
/*     */       }
/*     */       
/* 531 */       TaggingHintKey prevParent = getParentHint(kidKey);
/* 532 */       if (prevParent != null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 538 */       if (!skipFinishedChecks && kidKey.isFinished()) {
/* 539 */         Logger logger = LoggerFactory.getLogger(LayoutTaggingHelper.class);
/* 540 */         logger.error("Layout tagging hints addition failed: cannot add a hint that is already marked as finished. Consider using com.itextpdf.layout.tagging.LayoutTaggingHelper#moveKidHint method for moving already finished kid hint from not yet finished parent hint.");
/*     */         continue;
/*     */       } 
/* 543 */       if (insertIndex > -1) {
/* 544 */         kidsHint.add(insertIndex++, kidKey);
/*     */       } else {
/* 546 */         kidsHint.add(kidKey);
/*     */       } 
/* 548 */       this.parentHints.put(kidKey, parentKey);
/*     */       
/* 550 */       if (parentTagAlreadyCreated) {
/* 551 */         if (kidKey.getAccessibleElement() instanceof TaggingDummyElement) {
/* 552 */           createTag(kidKey, new TagTreePointer(this.document));
/*     */         }
/* 554 */         if (isNonAccessibleHint(kidKey)) {
/* 555 */           for (TaggingHintKey nestedKid : getAccessibleKidsHint(kidKey)) {
/* 556 */             if (nestedKid.getAccessibleElement() instanceof TaggingDummyElement) {
/* 557 */               createTag(nestedKid, new TagTreePointer(this.document));
/*     */             }
/* 559 */             moveKidTagIfCreated(parentTagHint, nestedKid);
/*     */           }  continue;
/*     */         } 
/* 562 */         moveKidTagIfCreated(parentTagHint, kidKey);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 567 */     if (!kidsHint.isEmpty()) {
/* 568 */       this.kidsHints.put(parentKey, kidsHint);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean createSingleTag(TaggingHintKey hintKey, TagTreePointer tagPointer) {
/* 573 */     if (hintKey.isFinished()) {
/* 574 */       Logger logger = LoggerFactory.getLogger(LayoutTaggingHelper.class);
/* 575 */       logger.error("Attempt to create a tag for a hint which is already marked as finished, tag will not be created.");
/* 576 */       return false;
/*     */     } 
/*     */     
/* 579 */     if (isNonAccessibleHint(hintKey)) {
/*     */ 
/*     */       
/* 582 */       TaggingHintKey parentTagHint = getAccessibleParentHint(hintKey);
/* 583 */       this.context.getWaitingTagsManager().tryMovePointerToWaitingTag(tagPointer, parentTagHint);
/* 584 */       return false;
/*     */     } 
/*     */     
/* 587 */     WaitingTagsManager waitingTagsManager = this.context.getWaitingTagsManager();
/* 588 */     if (!waitingTagsManager.tryMovePointerToWaitingTag(tagPointer, hintKey)) {
/*     */       
/* 590 */       IAccessibleElement modelElement = hintKey.getAccessibleElement();
/*     */       
/* 592 */       TaggingHintKey parentHint = getAccessibleParentHint(hintKey);
/* 593 */       int ind = -1;
/* 594 */       if (parentHint != null)
/*     */       {
/* 596 */         if (waitingTagsManager.tryMovePointerToWaitingTag(tagPointer, parentHint)) {
/* 597 */           List<TaggingHintKey> siblingsHint = getAccessibleKidsHint(parentHint);
/* 598 */           int i = siblingsHint.indexOf(hintKey);
/* 599 */           ind = getNearestNextSiblingTagIndex(waitingTagsManager, tagPointer, siblingsHint, i);
/*     */         } 
/*     */       }
/*     */       
/* 603 */       tagPointer.addTag(ind, modelElement.getAccessibilityProperties());
/* 604 */       if (hintKey.getOverriddenRole() != null) {
/* 605 */         tagPointer.setRole(hintKey.getOverriddenRole());
/*     */       }
/* 607 */       waitingTagsManager.assignWaitingState(tagPointer, hintKey);
/*     */       
/* 609 */       List<TaggingHintKey> kidsHint = getAccessibleKidsHint(hintKey);
/* 610 */       for (TaggingHintKey kidKey : kidsHint) {
/* 611 */         moveKidTagIfCreated(hintKey, kidKey);
/*     */       }
/*     */       
/* 614 */       return true;
/*     */     } 
/*     */     
/* 617 */     return false;
/*     */   }
/*     */   
/*     */   private int removeParentHint(TaggingHintKey hintKey) {
/* 621 */     TaggingHintKey parentHint = this.parentHints.get(hintKey);
/*     */     
/* 623 */     if (parentHint == null) {
/* 624 */       return -1;
/*     */     }
/*     */     
/* 627 */     TaggingHintKey accessibleParentHint = getAccessibleParentHint(hintKey);
/* 628 */     if (hintKey.isFinished() && parentHint.isFinished() && (accessibleParentHint == null || accessibleParentHint.isFinished())) {
/* 629 */       return -2;
/*     */     }
/*     */     
/* 632 */     return removeParentHint(hintKey, parentHint);
/*     */   }
/*     */   
/*     */   private int removeParentHint(TaggingHintKey hintKey, TaggingHintKey parentHint) {
/* 636 */     this.parentHints.remove(hintKey);
/*     */     
/* 638 */     List<TaggingHintKey> kidsHint = this.kidsHints.get(parentHint);
/*     */     
/* 640 */     int size = kidsHint.size(); int i;
/* 641 */     for (i = 0; i < size; i++) {
/* 642 */       if (kidsHint.get(i) == hintKey) {
/* 643 */         kidsHint.remove(i);
/*     */         break;
/*     */       } 
/*     */     } 
/* 647 */     assert i < size;
/*     */     
/* 649 */     if (kidsHint.isEmpty()) {
/* 650 */       this.kidsHints.remove(parentHint);
/*     */     }
/* 652 */     return i;
/*     */   }
/*     */   
/*     */   private void finishDummyKids(List<TaggingHintKey> taggingHintKeys) {
/* 656 */     for (TaggingHintKey hintKey : taggingHintKeys) {
/* 657 */       boolean isDummy = hintKey.getAccessibleElement() instanceof TaggingDummyElement;
/* 658 */       if (isDummy) {
/* 659 */         finishTaggingHint((IPropertyContainer)hintKey.getAccessibleElement());
/*     */       }
/* 661 */       if (isNonAccessibleHint(hintKey) || isDummy) {
/* 662 */         finishDummyKids(getKidsHint(hintKey));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveKidTagIfCreated(TaggingHintKey parentKey, TaggingHintKey kidKey) {
/* 670 */     TagTreePointer kidPointer = new TagTreePointer(this.document);
/* 671 */     WaitingTagsManager waitingTagsManager = this.context.getWaitingTagsManager();
/* 672 */     if (!waitingTagsManager.tryMovePointerToWaitingTag(kidPointer, kidKey)) {
/*     */       return;
/*     */     }
/*     */     
/* 676 */     TagTreePointer parentPointer = new TagTreePointer(this.document);
/* 677 */     if (!waitingTagsManager.tryMovePointerToWaitingTag(parentPointer, parentKey)) {
/*     */       return;
/*     */     }
/*     */     
/* 681 */     int kidIndInParentKidsHint = getAccessibleKidsHint(parentKey).indexOf(kidKey);
/* 682 */     int ind = getNearestNextSiblingTagIndex(waitingTagsManager, parentPointer, getAccessibleKidsHint(parentKey), kidIndInParentKidsHint);
/*     */     
/* 684 */     parentPointer.setNextNewKidIndex(ind);
/* 685 */     kidPointer.relocate(parentPointer);
/*     */   }
/*     */   
/*     */   private int getNearestNextSiblingTagIndex(WaitingTagsManager waitingTagsManager, TagTreePointer parentPointer, List<TaggingHintKey> siblingsHint, int start) {
/* 689 */     int ind = -1;
/* 690 */     TagTreePointer nextSiblingPointer = new TagTreePointer(this.document);
/* 691 */     while (++start < siblingsHint.size()) {
/* 692 */       if (waitingTagsManager.tryMovePointerToWaitingTag(nextSiblingPointer, siblingsHint.get(start)) && parentPointer
/* 693 */         .isPointingToSameTag((new TagTreePointer(nextSiblingPointer)).moveToParent())) {
/* 694 */         ind = nextSiblingPointer.getIndexInParentKidsList();
/*     */         break;
/*     */       } 
/*     */     } 
/* 698 */     return ind;
/*     */   }
/*     */   
/*     */   private static boolean isNonAccessibleHint(TaggingHintKey hintKey) {
/* 702 */     return (hintKey.getAccessibleElement() == null || hintKey.getAccessibleElement().getAccessibilityProperties().getRole() == null);
/*     */   }
/*     */   
/*     */   private boolean isTagAlreadyExistsForHint(TaggingHintKey tagHint) {
/* 706 */     return this.context.getWaitingTagsManager().isObjectAssociatedWithWaitingTag(tagHint);
/*     */   }
/*     */   
/*     */   private void releaseHint(TaggingHintKey hint, Set<TaggingHintKey> hintsToBeHeld, boolean checkContextIsFinished) {
/* 710 */     TaggingHintKey parentHint = this.parentHints.get(hint);
/* 711 */     List<TaggingHintKey> kidsHint = this.kidsHints.get(hint);
/* 712 */     if (checkContextIsFinished && parentHint != null && 
/* 713 */       isSomeParentNotFinished(parentHint)) {
/*     */       return;
/*     */     }
/*     */     
/* 717 */     if (checkContextIsFinished && kidsHint != null && 
/* 718 */       isSomeKidNotFinished(hint)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 723 */     if (checkContextIsFinished && hintsToBeHeld != null && 
/* 724 */       hintsToBeHeld.contains(hint)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 729 */     if (parentHint != null) {
/* 730 */       removeParentHint(hint, parentHint);
/*     */     }
/* 732 */     if (kidsHint != null) {
/* 733 */       for (TaggingHintKey kidHint : kidsHint) {
/* 734 */         this.parentHints.remove(kidHint);
/*     */       }
/* 736 */       this.kidsHints.remove(hint);
/*     */     } 
/*     */     
/* 739 */     TagTreePointer tagPointer = new TagTreePointer(this.document);
/* 740 */     if (this.context.getWaitingTagsManager().tryMovePointerToWaitingTag(tagPointer, hint)) {
/* 741 */       this.context.getWaitingTagsManager().removeWaitingState(hint);
/* 742 */       if (this.immediateFlush) {
/* 743 */         tagPointer.flushParentsIfAllKidsFlushed();
/*     */       }
/*     */     } else {
/* 746 */       this.context.getWaitingTagsManager().removeWaitingState(hint);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isSomeParentNotFinished(TaggingHintKey parentHint) {
/* 751 */     TaggingHintKey hintKey = parentHint;
/*     */     while (true) {
/* 753 */       if (hintKey == null) {
/* 754 */         return false;
/*     */       }
/* 756 */       if (!hintKey.isFinished()) {
/* 757 */         return true;
/*     */       }
/* 759 */       if (!isNonAccessibleHint(hintKey)) {
/* 760 */         return false;
/*     */       }
/* 762 */       hintKey = getParentHint(hintKey);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isSomeKidNotFinished(TaggingHintKey hint) {
/* 767 */     for (TaggingHintKey kidHint : getKidsHint(hint)) {
/* 768 */       if (!kidHint.isFinished()) {
/* 769 */         return true;
/*     */       }
/* 771 */       if (isNonAccessibleHint(kidHint) && isSomeKidNotFinished(kidHint)) {
/* 772 */         return true;
/*     */       }
/*     */     } 
/* 775 */     return false;
/*     */   }
/*     */   
/*     */   private void registerRules(PdfVersion pdfVersion) {
/* 779 */     ITaggingRule tableRule = new TableTaggingRule();
/* 780 */     registerSingleRule("Table", tableRule);
/* 781 */     registerSingleRule("TFoot", tableRule);
/* 782 */     registerSingleRule("THead", tableRule);
/* 783 */     if (pdfVersion.compareTo(PdfVersion.PDF_1_5) < 0) {
/* 784 */       TableTaggingPriorToOneFiveVersionRule priorToOneFiveRule = new TableTaggingPriorToOneFiveVersionRule();
/* 785 */       registerSingleRule("Table", priorToOneFiveRule);
/* 786 */       registerSingleRule("THead", priorToOneFiveRule);
/* 787 */       registerSingleRule("TFoot", priorToOneFiveRule);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerSingleRule(String role, ITaggingRule rule) {
/* 792 */     List<ITaggingRule> rules = this.taggingRules.get(role);
/* 793 */     if (rules == null) {
/* 794 */       rules = new ArrayList<>();
/* 795 */       this.taggingRules.put(role, rules);
/*     */     } 
/* 797 */     rules.add(rule);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/tagging/LayoutTaggingHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */