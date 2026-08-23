/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ class StructureTreeCopier
/*     */ {
/*  74 */   private static List<PdfName> ignoreKeysForCopy = new ArrayList<>();
/*     */   
/*  76 */   private static List<PdfName> ignoreKeysForClone = new ArrayList<>();
/*     */   
/*     */   static {
/*  79 */     ignoreKeysForCopy.add(PdfName.K);
/*  80 */     ignoreKeysForCopy.add(PdfName.P);
/*  81 */     ignoreKeysForCopy.add(PdfName.Pg);
/*  82 */     ignoreKeysForCopy.add(PdfName.Obj);
/*  83 */     ignoreKeysForCopy.add(PdfName.NS);
/*     */     
/*  85 */     ignoreKeysForClone.add(PdfName.K);
/*  86 */     ignoreKeysForClone.add(PdfName.P);
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
/*     */   public static void copyTo(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument) {
/*  99 */     if (!destDocument.isTagged()) {
/*     */       return;
/*     */     }
/* 102 */     copyTo(destDocument, page2page, callingDocument, false);
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
/*     */   public static void copyTo(PdfDocument destDocument, int insertBeforePage, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument) {
/* 119 */     if (!destDocument.isTagged()) {
/*     */       return;
/*     */     }
/* 122 */     copyTo(destDocument, insertBeforePage, page2page, callingDocument, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void move(PdfDocument document, PdfPage from, int insertBefore) {
/*     */     int destStruct;
/* 133 */     if (!document.isTagged() || insertBefore < 1 || insertBefore > document.getNumberOfPages() + 1) {
/*     */       return;
/*     */     }
/* 136 */     int fromNum = document.getPageNumber(from);
/* 137 */     if (fromNum == 0 || fromNum == insertBefore || fromNum + 1 == insertBefore) {
/*     */       return;
/*     */     }
/*     */     
/* 141 */     int currStruct = 0;
/* 142 */     if (fromNum > insertBefore) {
/* 143 */       destStruct = currStruct = separateStructure(document, 1, insertBefore, 0);
/* 144 */       currStruct = separateStructure(document, insertBefore, fromNum, currStruct);
/* 145 */       currStruct = separateStructure(document, fromNum, fromNum + 1, currStruct);
/*     */     } else {
/* 147 */       currStruct = separateStructure(document, 1, fromNum, 0);
/* 148 */       currStruct = separateStructure(document, fromNum, fromNum + 1, currStruct);
/* 149 */       destStruct = currStruct = separateStructure(document, fromNum + 1, insertBefore, currStruct);
/*     */     } 
/*     */     
/* 152 */     Set<PdfDictionary> topsToMove = new HashSet<>();
/* 153 */     Collection<PdfMcr> mcrs = document.getStructTreeRoot().getPageMarkedContentReferences(from);
/* 154 */     if (mcrs != null) {
/* 155 */       for (PdfMcr mcr : mcrs) {
/* 156 */         PdfDictionary top = getTopmostParent(mcr);
/* 157 */         if (top != null) {
/* 158 */           if (top.isFlushed()) {
/* 159 */             throw new PdfException("Cannot move flushed tag");
/*     */           }
/* 161 */           topsToMove.add(top);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 166 */     List<PdfDictionary> orderedTopsToMove = new ArrayList<>();
/* 167 */     PdfArray tops = document.getStructTreeRoot().getKidsObject();
/* 168 */     for (int i = 0; i < tops.size(); i++) {
/* 169 */       PdfDictionary top = tops.getAsDictionary(i);
/* 170 */       if (topsToMove.contains(top)) {
/* 171 */         orderedTopsToMove.add(top);
/* 172 */         tops.remove(i);
/* 173 */         if (i < destStruct) {
/* 174 */           destStruct--;
/*     */         }
/*     */       } 
/*     */     } 
/* 178 */     for (PdfDictionary top : orderedTopsToMove) {
/* 179 */       document.getStructTreeRoot().addKidObject(destStruct++, top);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int separateStructure(PdfDocument document, int beforePage) {
/* 187 */     return separateStructure(document, 1, beforePage, 0);
/*     */   }
/*     */   
/*     */   private static int separateStructure(PdfDocument document, int startPage, int beforePage, int startPageStructTopIndex) {
/* 191 */     if (!document.isTagged() || 1 > startPage || startPage > beforePage || beforePage > document.getNumberOfPages() + 1)
/* 192 */       return -1; 
/* 193 */     if (beforePage == startPage)
/* 194 */       return startPageStructTopIndex; 
/* 195 */     if (beforePage == document.getNumberOfPages() + 1) {
/* 196 */       return document.getStructTreeRoot().getKidsObject().size();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 201 */     Set<PdfObject> firstPartElems = new HashSet<>();
/* 202 */     for (int i = startPage; i < beforePage; i++) {
/* 203 */       PdfPage pageOfFirstHalf = document.getPage(i);
/* 204 */       Collection<PdfMcr> pageMcrs = document.getStructTreeRoot().getPageMarkedContentReferences(pageOfFirstHalf);
/* 205 */       if (pageMcrs != null) {
/* 206 */         for (PdfMcr mcr : pageMcrs) {
/* 207 */           firstPartElems.add(mcr.getPdfObject());
/* 208 */           PdfDictionary top = addAllParentsToSet(mcr, firstPartElems);
/* 209 */           if (top != null && top.isFlushed()) {
/* 210 */             throw new PdfException("Tag from the existing tag structure is flushed. Cannot add copied page tags.");
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 216 */     List<PdfDictionary> clonedTops = new ArrayList<>();
/* 217 */     PdfArray tops = document.getStructTreeRoot().getKidsObject();
/*     */ 
/*     */ 
/*     */     
/* 221 */     int lastTopBefore = startPageStructTopIndex - 1; int j;
/* 222 */     for (j = 0; j < tops.size(); j++) {
/* 223 */       PdfDictionary top = tops.getAsDictionary(j);
/* 224 */       if (firstPartElems.contains(top)) {
/* 225 */         lastTopBefore = j;
/*     */         
/* 227 */         LastClonedAncestor lastCloned = new LastClonedAncestor();
/* 228 */         lastCloned.ancestor = top;
/* 229 */         PdfDictionary topClone = top.clone(ignoreKeysForClone);
/* 230 */         topClone.put(PdfName.P, document.getStructTreeRoot().getPdfObject());
/* 231 */         lastCloned.clone = topClone;
/*     */         
/* 233 */         separateKids(top, firstPartElems, lastCloned, document);
/*     */         
/* 235 */         if (topClone.containsKey(PdfName.K)) {
/* 236 */           topClone.makeIndirect(document);
/* 237 */           clonedTops.add(topClone);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     for (j = 0; j < clonedTops.size(); j++) {
/* 243 */       document.getStructTreeRoot().addKidObject(lastTopBefore + 1 + j, clonedTops.get(j));
/*     */     }
/* 245 */     return lastTopBefore + 1;
/*     */   }
/*     */   
/*     */   private static void copyTo(PdfDocument destDocument, int insertBeforePage, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument, boolean copyFromDestDocument) {
/* 249 */     if (!destDocument.isTagged()) {
/*     */       return;
/*     */     }
/* 252 */     int insertIndex = separateStructure(destDocument, insertBeforePage);
/*     */     
/* 254 */     if (insertIndex > 0) {
/* 255 */       copyTo(destDocument, page2page, callingDocument, copyFromDestDocument, insertIndex);
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
/*     */   private static void copyTo(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument, boolean copyFromDestDocument) {
/* 267 */     copyTo(destDocument, page2page, callingDocument, copyFromDestDocument, -1);
/*     */   }
/*     */   
/*     */   private static void copyTo(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument, boolean copyFromDestDocument, int insertIndex) {
/* 271 */     CopyStructureResult copiedStructure = copyStructure(destDocument, page2page, callingDocument, copyFromDestDocument);
/* 272 */     PdfStructTreeRoot destStructTreeRoot = destDocument.getStructTreeRoot();
/* 273 */     destStructTreeRoot.makeIndirect(destDocument);
/* 274 */     for (PdfDictionary copied : copiedStructure.getTopsList()) {
/* 275 */       destStructTreeRoot.addKidObject(insertIndex, copied);
/* 276 */       if (insertIndex > -1) {
/* 277 */         insertIndex++;
/*     */       }
/*     */     } 
/*     */     
/* 281 */     if (!copyFromDestDocument) {
/* 282 */       if (!copiedStructure.getCopiedNamespaces().isEmpty()) {
/* 283 */         destStructTreeRoot.getNamespacesObject().addAll(copiedStructure.getCopiedNamespaces());
/*     */       }
/*     */       
/* 286 */       PdfDictionary srcRoleMap = callingDocument.getStructTreeRoot().getRoleMap();
/* 287 */       PdfDictionary destRoleMap = destStructTreeRoot.getRoleMap();
/* 288 */       for (Map.Entry<PdfName, PdfObject> mappingEntry : (Iterable<Map.Entry<PdfName, PdfObject>>)srcRoleMap.entrySet()) {
/* 289 */         if (!destRoleMap.containsKey(mappingEntry.getKey())) {
/* 290 */           destRoleMap.put(mappingEntry.getKey(), mappingEntry.getValue()); continue;
/*     */         } 
/* 292 */         if (!((PdfObject)mappingEntry.getValue()).equals(destRoleMap.get(mappingEntry.getKey()))) {
/* 293 */           String srcMapping = (new StringBuilder()).append(mappingEntry.getKey()).append(" -> ").append(mappingEntry.getValue()).toString();
/* 294 */           String destMapping = (new StringBuilder()).append(mappingEntry.getKey()).append(" -> ").append(destRoleMap.get(mappingEntry.getKey())).toString();
/*     */           
/* 296 */           Logger logger = LoggerFactory.getLogger(StructureTreeCopier.class);
/* 297 */           logger.warn(MessageFormat.format("Role mapping \"{0}\" from source document is not copied. Destination document already has \"{1}\" mapping.", new Object[] { srcMapping, destMapping }));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static CopyStructureResult copyStructure(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument, boolean copyFromDestDocument) {
/* 304 */     PdfDocument fromDocument = copyFromDestDocument ? destDocument : callingDocument;
/* 305 */     Map<PdfDictionary, PdfDictionary> topsToFirstDestPage = new HashMap<>();
/* 306 */     Set<PdfObject> objectsToCopy = new HashSet<>();
/* 307 */     Map<PdfDictionary, PdfDictionary> page2pageDictionaries = new HashMap<>();
/* 308 */     for (Map.Entry<PdfPage, PdfPage> page : page2page.entrySet()) {
/* 309 */       page2pageDictionaries.put(((PdfPage)page.getKey()).getPdfObject(), ((PdfPage)page.getValue()).getPdfObject());
/* 310 */       Collection<PdfMcr> mcrs = fromDocument.getStructTreeRoot().getPageMarkedContentReferences(page.getKey());
/* 311 */       if (mcrs != null) {
/* 312 */         for (PdfMcr mcr : mcrs) {
/* 313 */           if (mcr instanceof PdfMcrDictionary || mcr instanceof PdfObjRef) {
/* 314 */             objectsToCopy.add(mcr.getPdfObject());
/*     */           }
/* 316 */           PdfDictionary top = addAllParentsToSet(mcr, objectsToCopy);
/* 317 */           if (top != null) {
/* 318 */             if (top.isFlushed()) {
/* 319 */               throw new PdfException("Cannot copy flushed tag.");
/*     */             }
/* 321 */             if (!topsToFirstDestPage.containsKey(top)) {
/* 322 */               topsToFirstDestPage.put(top, ((PdfPage)page.getValue()).getPdfObject());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 329 */     List<PdfDictionary> topsInOriginalOrder = new ArrayList<>();
/* 330 */     for (IStructureNode kid : fromDocument.getStructTreeRoot().getKids()) {
/* 331 */       if (kid == null)
/*     */         continue; 
/* 333 */       PdfDictionary kidObject = (PdfDictionary)((PdfStructElem)kid).getPdfObject();
/* 334 */       if (topsToFirstDestPage.containsKey(kidObject)) {
/* 335 */         topsInOriginalOrder.add(kidObject);
/*     */       }
/*     */     } 
/* 338 */     StructElemCopyingParams structElemCopyingParams = new StructElemCopyingParams(objectsToCopy, destDocument, page2pageDictionaries, copyFromDestDocument);
/* 339 */     PdfStructTreeRoot destStructTreeRoot = destDocument.getStructTreeRoot();
/* 340 */     destStructTreeRoot.makeIndirect(destDocument);
/* 341 */     List<PdfDictionary> copiedTops = new ArrayList<>();
/* 342 */     for (PdfDictionary top : topsInOriginalOrder) {
/* 343 */       PdfDictionary copied = copyObject(top, topsToFirstDestPage.get(top), false, structElemCopyingParams);
/* 344 */       copiedTops.add(copied);
/*     */     } 
/* 346 */     return new CopyStructureResult(copiedTops, structElemCopyingParams.getCopiedNamespaces());
/*     */   }
/*     */   
/*     */   private static PdfDictionary copyObject(PdfDictionary source, PdfDictionary destPage, boolean parentChangePg, StructElemCopyingParams copyingParams) {
/*     */     PdfDictionary copied;
/* 351 */     if (copyingParams.isCopyFromDestDocument()) {
/*     */       
/* 353 */       copied = source.clone(ignoreKeysForClone);
/* 354 */       if (source.isIndirect()) {
/* 355 */         copied.makeIndirect(copyingParams.getToDocument());
/*     */       }
/*     */       
/* 358 */       PdfDictionary pg = source.getAsDictionary(PdfName.Pg);
/* 359 */       if (pg != null && 
/* 360 */         copyingParams.isCopyFromDestDocument()) {
/* 361 */         if (pg != destPage) {
/* 362 */           copied.put(PdfName.Pg, (PdfObject)destPage);
/* 363 */           parentChangePg = true;
/*     */         } else {
/* 365 */           parentChangePg = false;
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 370 */       copied = source.copyTo(copyingParams.getToDocument(), ignoreKeysForCopy, true);
/*     */       
/* 372 */       PdfDictionary obj = source.getAsDictionary(PdfName.Obj);
/* 373 */       if (obj != null) {
/*     */ 
/*     */ 
/*     */         
/* 377 */         obj = obj.copyTo(copyingParams.getToDocument(), Arrays.asList(new PdfName[] { PdfName.P }, ), false);
/* 378 */         copied.put(PdfName.Obj, (PdfObject)obj);
/*     */       } 
/*     */       
/* 381 */       PdfDictionary nsDict = source.getAsDictionary(PdfName.NS);
/* 382 */       if (nsDict != null) {
/* 383 */         PdfDictionary copiedNsDict = copyNamespaceDict(nsDict, copyingParams);
/* 384 */         copied.put(PdfName.NS, (PdfObject)copiedNsDict);
/*     */       } 
/*     */       
/* 387 */       PdfDictionary pg = source.getAsDictionary(PdfName.Pg);
/* 388 */       if (pg != null) {
/* 389 */         PdfDictionary pageAnalog = copyingParams.getPage2page().get(pg);
/* 390 */         if (pageAnalog == null) {
/* 391 */           pageAnalog = destPage;
/* 392 */           parentChangePg = true;
/*     */         } else {
/* 394 */           parentChangePg = false;
/*     */         } 
/* 396 */         copied.put(PdfName.Pg, (PdfObject)pageAnalog);
/*     */       } 
/*     */     } 
/*     */     
/* 400 */     PdfObject k = source.get(PdfName.K);
/* 401 */     if (k != null) {
/* 402 */       if (k.isArray()) {
/* 403 */         PdfArray kArr = (PdfArray)k;
/* 404 */         PdfArray newArr = new PdfArray();
/* 405 */         for (int i = 0; i < kArr.size(); i++) {
/* 406 */           PdfObject copiedKid = copyObjectKid(kArr.get(i), copied, destPage, parentChangePg, copyingParams);
/* 407 */           if (copiedKid != null) {
/* 408 */             newArr.add(copiedKid);
/*     */           }
/*     */         } 
/* 411 */         if (!newArr.isEmpty()) {
/* 412 */           if (newArr.size() == 1) {
/* 413 */             copied.put(PdfName.K, newArr.get(0));
/*     */           } else {
/* 415 */             copied.put(PdfName.K, (PdfObject)newArr);
/*     */           } 
/*     */         }
/*     */       } else {
/* 419 */         PdfObject copiedKid = copyObjectKid(k, copied, destPage, parentChangePg, copyingParams);
/* 420 */         if (copiedKid != null) {
/* 421 */           copied.put(PdfName.K, copiedKid);
/*     */         }
/*     */       } 
/*     */     }
/* 425 */     return copied;
/*     */   }
/*     */   
/*     */   private static PdfObject copyObjectKid(PdfObject kid, PdfDictionary copiedParent, PdfDictionary destPage, boolean parentChangePg, StructElemCopyingParams copyingParams) {
/* 429 */     if (kid.isNumber()) {
/* 430 */       if (!parentChangePg) {
/* 431 */         copyingParams.getToDocument().getStructTreeRoot().getParentTreeHandler()
/* 432 */           .registerMcr(new PdfMcrNumber((PdfNumber)kid, new PdfStructElem(copiedParent)));
/* 433 */         return kid;
/*     */       } 
/* 435 */     } else if (kid.isDictionary()) {
/* 436 */       PdfDictionary kidAsDict = (PdfDictionary)kid;
/* 437 */       if (copyingParams.getObjectsToCopy().contains(kidAsDict)) {
/* 438 */         boolean hasParent = kidAsDict.containsKey(PdfName.P);
/* 439 */         PdfDictionary copiedKid = copyObject(kidAsDict, destPage, parentChangePg, copyingParams);
/* 440 */         if (hasParent) {
/* 441 */           copiedKid.put(PdfName.P, (PdfObject)copiedParent);
/*     */         } else {
/*     */           PdfMcr mcr;
/* 444 */           if (copiedKid.containsKey(PdfName.Obj)) {
/* 445 */             mcr = new PdfObjRef(copiedKid, new PdfStructElem(copiedParent));
/* 446 */             PdfDictionary contentItemObject = copiedKid.getAsDictionary(PdfName.Obj);
/* 447 */             if (PdfName.Link.equals(contentItemObject.getAsName(PdfName.Subtype)) && 
/* 448 */               !contentItemObject.containsKey(PdfName.P))
/*     */             {
/* 450 */               return null;
/*     */             }
/* 452 */             contentItemObject.put(PdfName.StructParent, (PdfObject)new PdfNumber(copyingParams.getToDocument().getNextStructParentIndex()));
/*     */           } else {
/* 454 */             mcr = new PdfMcrDictionary(copiedKid, new PdfStructElem(copiedParent));
/*     */           } 
/* 456 */           copyingParams.getToDocument().getStructTreeRoot().getParentTreeHandler().registerMcr(mcr);
/*     */         } 
/* 458 */         return (PdfObject)copiedKid;
/*     */       } 
/*     */     } 
/* 461 */     return null;
/*     */   }
/*     */   
/*     */   private static PdfDictionary copyNamespaceDict(PdfDictionary srcNsDict, StructElemCopyingParams copyingParams) {
/* 465 */     List<PdfName> excludeKeys = Collections.singletonList(PdfName.RoleMapNS);
/* 466 */     PdfDocument toDocument = copyingParams.getToDocument();
/* 467 */     PdfDictionary copiedNsDict = srcNsDict.copyTo(toDocument, excludeKeys, false);
/* 468 */     copyingParams.addCopiedNamespace(copiedNsDict);
/*     */     
/* 470 */     PdfDictionary srcRoleMapNs = srcNsDict.getAsDictionary(PdfName.RoleMapNS);
/*     */     
/* 472 */     PdfDictionary copiedRoleMap = copiedNsDict.getAsDictionary(PdfName.RoleMapNS);
/* 473 */     if (srcRoleMapNs != null && copiedRoleMap == null) {
/* 474 */       copiedRoleMap = new PdfDictionary();
/* 475 */       copiedNsDict.put(PdfName.RoleMapNS, (PdfObject)copiedRoleMap);
/*     */       
/* 477 */       for (Map.Entry<PdfName, PdfObject> entry : (Iterable<Map.Entry<PdfName, PdfObject>>)srcRoleMapNs.entrySet()) {
/*     */         PdfObject copiedMapping;
/* 479 */         if (((PdfObject)entry.getValue()).isArray()) {
/* 480 */           PdfArray srcMappingArray = (PdfArray)entry.getValue();
/* 481 */           if (srcMappingArray.size() > 1 && srcMappingArray.get(1).isDictionary()) {
/* 482 */             PdfArray copiedMappingArray = new PdfArray();
/* 483 */             copiedMappingArray.add(srcMappingArray.get(0).copyTo(toDocument));
/* 484 */             PdfDictionary copiedNamespace = copyNamespaceDict(srcMappingArray.getAsDictionary(1), copyingParams);
/* 485 */             copiedMappingArray.add((PdfObject)copiedNamespace);
/* 486 */             PdfArray pdfArray1 = copiedMappingArray;
/*     */           } else {
/* 488 */             Logger logger = LoggerFactory.getLogger(StructureTreeCopier.class);
/* 489 */             logger.warn(MessageFormat.format("Role mapping for \"{0}\" from source document is not copied. Mapping to namespace is in an invalid form (should be [PdfName, PdfDictionary]).", new Object[] { ((PdfName)entry.getKey()).toString() }));
/*     */             continue;
/*     */           } 
/*     */         } else {
/* 493 */           copiedMapping = ((PdfObject)entry.getValue()).copyTo(toDocument);
/*     */         } 
/* 495 */         PdfName copiedRoleFrom = (PdfName)((PdfName)entry.getKey()).copyTo(toDocument);
/* 496 */         copiedRoleMap.put(copiedRoleFrom, copiedMapping);
/*     */       } 
/*     */     } 
/*     */     
/* 500 */     return copiedNsDict;
/*     */   }
/*     */   
/*     */   private static void separateKids(PdfDictionary structElem, Set<PdfObject> firstPartElems, LastClonedAncestor lastCloned, PdfDocument document) {
/* 504 */     PdfObject k = structElem.get(PdfName.K);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 509 */     if (!k.isArray()) {
/* 510 */       if (k.isDictionary() && PdfStructElem.isStructElem((PdfDictionary)k)) {
/* 511 */         separateKids((PdfDictionary)k, firstPartElems, lastCloned, document);
/*     */       }
/*     */     } else {
/* 514 */       PdfArray kids = (PdfArray)k;
/*     */       
/* 516 */       for (int i = 0; i < kids.size(); i++) {
/* 517 */         PdfObject kid = kids.get(i);
/* 518 */         PdfDictionary dictKid = null;
/* 519 */         if (kid.isDictionary()) {
/* 520 */           dictKid = (PdfDictionary)kid;
/*     */         }
/*     */         
/* 523 */         if (dictKid != null && PdfStructElem.isStructElem(dictKid)) {
/* 524 */           if (firstPartElems.contains(kid)) {
/* 525 */             separateKids((PdfDictionary)kid, firstPartElems, lastCloned, document);
/*     */           } else {
/* 527 */             if (dictKid.isFlushed()) {
/* 528 */               throw new PdfException("Tag from the existing tag structure is flushed. Cannot add copied page tags.");
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 533 */             if (dictKid.containsKey(PdfName.K)) {
/* 534 */               cloneParents(structElem, lastCloned, document);
/*     */               
/* 536 */               kids.remove(i--);
/* 537 */               PdfStructElem.addKidObject(lastCloned.clone, -1, kid);
/*     */             }
/*     */           
/*     */           } 
/* 541 */         } else if (!firstPartElems.contains(kid)) {
/* 542 */           PdfMcr mcr; cloneParents(structElem, lastCloned, document);
/*     */ 
/*     */           
/* 545 */           if (dictKid != null) {
/* 546 */             if (dictKid.get(PdfName.Type).equals(PdfName.MCR)) {
/* 547 */               mcr = new PdfMcrDictionary(dictKid, new PdfStructElem(lastCloned.clone));
/*     */             } else {
/* 549 */               mcr = new PdfObjRef(dictKid, new PdfStructElem(lastCloned.clone));
/*     */             } 
/*     */           } else {
/* 552 */             mcr = new PdfMcrNumber((PdfNumber)kid, new PdfStructElem(lastCloned.clone));
/*     */           } 
/*     */           
/* 555 */           kids.remove(i--);
/* 556 */           PdfStructElem.addKidObject(lastCloned.clone, -1, kid);
/*     */           
/* 558 */           document.getStructTreeRoot().getParentTreeHandler().registerMcr(mcr);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 564 */     if (lastCloned.ancestor == structElem) {
/* 565 */       lastCloned.ancestor = lastCloned.ancestor.getAsDictionary(PdfName.P);
/* 566 */       lastCloned.clone = lastCloned.clone.getAsDictionary(PdfName.P);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void cloneParents(PdfDictionary structElem, LastClonedAncestor lastCloned, PdfDocument document) {
/* 571 */     if (lastCloned.ancestor != structElem) {
/* 572 */       PdfDictionary structElemClone = (PdfDictionary)structElem.clone(ignoreKeysForClone).makeIndirect(document);
/* 573 */       PdfDictionary currClone = structElemClone;
/* 574 */       PdfDictionary currElem = structElem;
/* 575 */       while (currElem.get(PdfName.P) != lastCloned.ancestor) {
/* 576 */         PdfDictionary parent = currElem.getAsDictionary(PdfName.P);
/* 577 */         PdfDictionary parentClone = (PdfDictionary)parent.clone(ignoreKeysForClone).makeIndirect(document);
/* 578 */         currClone.put(PdfName.P, (PdfObject)parentClone);
/* 579 */         parentClone.put(PdfName.K, (PdfObject)currClone);
/* 580 */         currClone = parentClone;
/* 581 */         currElem = parent;
/*     */       } 
/* 583 */       PdfStructElem.addKidObject(lastCloned.clone, -1, (PdfObject)currClone);
/* 584 */       lastCloned.clone = structElemClone;
/* 585 */       lastCloned.ancestor = structElem;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfDictionary addAllParentsToSet(PdfMcr mcr, Set<PdfObject> set) {
/* 593 */     List<PdfDictionary> allParents = retrieveParents(mcr, true);
/* 594 */     set.addAll(allParents);
/* 595 */     return allParents.isEmpty() ? null : allParents.get(allParents.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfDictionary getTopmostParent(PdfMcr mcr) {
/* 605 */     return retrieveParents(mcr, false).get(0);
/*     */   }
/*     */   
/*     */   private static List<PdfDictionary> retrieveParents(PdfMcr mcr, boolean all) {
/* 609 */     List<PdfDictionary> parents = new ArrayList<>();
/* 610 */     IStructureNode firstParent = mcr.getParent();
/* 611 */     PdfDictionary previous = null;
/* 612 */     PdfDictionary current = (firstParent instanceof PdfStructElem) ? (PdfDictionary)((PdfStructElem)firstParent).getPdfObject() : null;
/* 613 */     while (current != null && !PdfName.StructTreeRoot.equals(current.getAsName(PdfName.Type))) {
/* 614 */       if (all) {
/* 615 */         parents.add(current);
/*     */       }
/* 617 */       previous = current;
/* 618 */       current = previous.isFlushed() ? null : previous.getAsDictionary(PdfName.P);
/*     */     } 
/* 620 */     if (!all) {
/* 621 */       parents.add(previous);
/*     */     }
/* 623 */     return parents;
/*     */   }
/*     */   
/*     */   static class LastClonedAncestor
/*     */   {
/*     */     PdfDictionary ancestor;
/*     */     PdfDictionary clone;
/*     */   }
/*     */   
/*     */   private static class StructElemCopyingParams {
/*     */     private final Set<PdfObject> objectsToCopy;
/*     */     private final PdfDocument toDocument;
/*     */     private final Map<PdfDictionary, PdfDictionary> page2page;
/*     */     private final boolean copyFromDestDocument;
/*     */     private final Set<PdfObject> copiedNamespaces;
/*     */     
/*     */     public StructElemCopyingParams(Set<PdfObject> objectsToCopy, PdfDocument toDocument, Map<PdfDictionary, PdfDictionary> page2page, boolean copyFromDestDocument) {
/* 640 */       this.objectsToCopy = objectsToCopy;
/* 641 */       this.toDocument = toDocument;
/* 642 */       this.page2page = page2page;
/* 643 */       this.copyFromDestDocument = copyFromDestDocument;
/* 644 */       this.copiedNamespaces = new LinkedHashSet<>();
/*     */     }
/*     */     
/*     */     public Set<PdfObject> getObjectsToCopy() {
/* 648 */       return this.objectsToCopy;
/*     */     }
/*     */     
/*     */     public PdfDocument getToDocument() {
/* 652 */       return this.toDocument;
/*     */     }
/*     */     
/*     */     public Map<PdfDictionary, PdfDictionary> getPage2page() {
/* 656 */       return this.page2page;
/*     */     }
/*     */     
/*     */     public boolean isCopyFromDestDocument() {
/* 660 */       return this.copyFromDestDocument;
/*     */     }
/*     */     
/*     */     public void addCopiedNamespace(PdfDictionary copiedNs) {
/* 664 */       this.copiedNamespaces.add(copiedNs);
/*     */     }
/*     */     
/*     */     public Set<PdfObject> getCopiedNamespaces() {
/* 668 */       return this.copiedNamespaces;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CopyStructureResult {
/*     */     private final List<PdfDictionary> topsList;
/*     */     private final Set<PdfObject> copiedNamespaces;
/*     */     
/*     */     public CopyStructureResult(List<PdfDictionary> topsList, Set<PdfObject> copiedNamespaces) {
/* 677 */       this.topsList = topsList;
/* 678 */       this.copiedNamespaces = copiedNamespaces;
/*     */     }
/*     */     
/*     */     public Set<PdfObject> getCopiedNamespaces() {
/* 682 */       return this.copiedNamespaces;
/*     */     }
/*     */     
/*     */     public List<PdfDictionary> getTopsList() {
/* 686 */       return this.topsList;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/StructureTreeCopier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */