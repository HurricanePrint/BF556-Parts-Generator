/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.IsoKey;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNull;
/*     */ import com.itextpdf.kernel.pdf.PdfNumTree;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
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
/*     */ class ParentTreeHandler
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1593883864288316473L;
/*     */   private PdfStructTreeRoot structTreeRoot;
/*     */   private PdfNumTree parentTree;
/*     */   private Map<PdfIndirectReference, PageMcrsContainer> pageToPageMcrs;
/*     */   private Map<PdfIndirectReference, Integer> pageToStructParentsInd;
/*     */   private Map<PdfIndirectReference, Integer> xObjectToStructParentsInd;
/*     */   
/*     */   ParentTreeHandler(PdfStructTreeRoot structTreeRoot) {
/* 100 */     this.structTreeRoot = structTreeRoot;
/* 101 */     this.parentTree = new PdfNumTree(structTreeRoot.getDocument().getCatalog(), PdfName.ParentTree);
/* 102 */     this.xObjectToStructParentsInd = new HashMap<>();
/* 103 */     registerAllMcrs();
/* 104 */     this.pageToStructParentsInd = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageMcrsContainer getPageMarkedContentReferences(PdfPage page) {
/* 111 */     return this.pageToPageMcrs.get(((PdfDictionary)page.getPdfObject()).getIndirectReference());
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfMcr findMcrByMcid(PdfDictionary pageDict, int mcid) {
/* 116 */     PageMcrsContainer pageMcrs = this.pageToPageMcrs.get(pageDict.getIndirectReference());
/* 117 */     return (pageMcrs != null) ? pageMcrs.getPageContentStreamsMcrs().get(Integer.valueOf(mcid)) : null;
/*     */   }
/*     */   
/*     */   public PdfObjRef findObjRefByStructParentIndex(PdfDictionary pageDict, int structParentIndex) {
/* 121 */     PageMcrsContainer pageMcrs = this.pageToPageMcrs.get(pageDict.getIndirectReference());
/* 122 */     return (pageMcrs != null) ? (PdfObjRef)pageMcrs.getObjRefs().get(Integer.valueOf(structParentIndex)) : null;
/*     */   }
/*     */   
/*     */   public int getNextMcidForPage(PdfPage page) {
/* 126 */     PageMcrsContainer pageMcrs = getPageMarkedContentReferences(page);
/* 127 */     if (pageMcrs == null || pageMcrs.getPageContentStreamsMcrs().size() == 0) {
/* 128 */       return 0;
/*     */     }
/* 130 */     return ((Integer)pageMcrs.getPageContentStreamsMcrs().lastEntry().getKey()).intValue() + 1;
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
/*     */   public void createParentTreeEntryForPage(PdfPage page) {
/* 142 */     PageMcrsContainer mcrs = getPageMarkedContentReferences(page);
/* 143 */     if (mcrs == null) {
/*     */       return;
/*     */     }
/* 146 */     this.pageToPageMcrs.remove(((PdfDictionary)page.getPdfObject()).getIndirectReference());
/*     */     
/* 148 */     if (updateStructParentTreeEntries(page, mcrs)) {
/* 149 */       this.structTreeRoot.setModified();
/*     */     }
/*     */   }
/*     */   
/*     */   public void savePageStructParentIndexIfNeeded(PdfPage page) {
/* 154 */     PdfIndirectReference indRef = ((PdfDictionary)page.getPdfObject()).getIndirectReference();
/* 155 */     if (page.isFlushed() || this.pageToPageMcrs.get(indRef) == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 160 */     boolean hasNonObjRefMcr = (((PageMcrsContainer)this.pageToPageMcrs.get(indRef)).getPageContentStreamsMcrs().size() > 0 || ((PageMcrsContainer)this.pageToPageMcrs.get(indRef)).getPageResourceXObjects().size() > 0);
/*     */     
/* 162 */     if (hasNonObjRefMcr) {
/* 163 */       this.pageToStructParentsInd.put(indRef, Integer.valueOf(getOrCreatePageStructParentIndex(page)));
/*     */     }
/*     */   }
/*     */   
/*     */   public PdfDictionary buildParentTree() {
/* 168 */     return (PdfDictionary)this.parentTree.buildTree().makeIndirect(this.structTreeRoot.getDocument());
/*     */   }
/*     */   
/*     */   public void registerMcr(PdfMcr mcr) {
/* 172 */     registerMcr(mcr, false);
/*     */   }
/*     */   
/*     */   private void registerMcr(PdfMcr mcr, boolean registeringOnInit) {
/* 176 */     PdfIndirectReference mcrPageIndRef = mcr.getPageIndirectReference();
/* 177 */     if (mcrPageIndRef == null || (!(mcr instanceof PdfObjRef) && mcr.getMcid() < 0)) {
/* 178 */       Logger logger = LoggerFactory.getLogger(ParentTreeHandler.class);
/* 179 */       logger.error("Corrupted tag structure: encountered invalid marked content reference - it doesn't refer to any page or any mcid. This content reference will be ignored.");
/*     */       return;
/*     */     } 
/* 182 */     PageMcrsContainer pageMcrs = this.pageToPageMcrs.get(mcrPageIndRef);
/* 183 */     if (pageMcrs == null) {
/* 184 */       pageMcrs = new PageMcrsContainer();
/* 185 */       this.pageToPageMcrs.put(mcrPageIndRef, pageMcrs);
/*     */     } 
/*     */     
/*     */     PdfObject stm;
/* 189 */     if ((stm = getStm(mcr)) != null) {
/*     */       PdfIndirectReference stmIndRef;
/*     */       PdfStream xObjectStream;
/* 192 */       if (stm instanceof PdfIndirectReference) {
/* 193 */         stmIndRef = (PdfIndirectReference)stm;
/* 194 */         xObjectStream = (PdfStream)stmIndRef.getRefersTo();
/*     */       } else {
/* 196 */         if (stm.getIndirectReference() == null) {
/* 197 */           stm.makeIndirect(this.structTreeRoot.getDocument());
/*     */         }
/* 199 */         stmIndRef = stm.getIndirectReference();
/* 200 */         xObjectStream = (PdfStream)stm;
/*     */       } 
/*     */       
/* 203 */       Integer structParent = xObjectStream.getAsInt(PdfName.StructParents);
/* 204 */       if (structParent != null) {
/* 205 */         this.xObjectToStructParentsInd.put(stmIndRef, structParent);
/*     */       } else {
/*     */         
/* 208 */         Logger logger = LoggerFactory.getLogger(ParentTreeHandler.class);
/* 209 */         logger.error("XObject has no StructParents entry in its stream, no entry in ParentTree will be created for the corresponding structure elements");
/*     */       } 
/* 211 */       pageMcrs.putXObjectMcr(stmIndRef, mcr);
/* 212 */       if (registeringOnInit) {
/* 213 */         xObjectStream.release();
/*     */       }
/* 215 */     } else if (mcr instanceof PdfObjRef) {
/* 216 */       PdfDictionary obj = ((PdfDictionary)mcr.getPdfObject()).getAsDictionary(PdfName.Obj);
/* 217 */       if (obj == null || obj.isFlushed()) {
/* 218 */         throw new PdfException("When adding object reference to the tag tree, it must be connected to not flushed object.");
/*     */       }
/*     */       
/* 221 */       PdfNumber n = obj.getAsNumber(PdfName.StructParent);
/* 222 */       if (n != null) {
/* 223 */         pageMcrs.putObjectReferenceMcr(n.intValue(), mcr);
/*     */       } else {
/* 225 */         throw new PdfException("StructParent index not found in tagged object.");
/*     */       } 
/*     */     } else {
/* 228 */       pageMcrs.putPageContentStreamMcr(mcr.getMcid(), mcr);
/*     */     } 
/*     */     
/* 231 */     if (!registeringOnInit) {
/* 232 */       this.structTreeRoot.setModified();
/*     */     }
/*     */   }
/*     */   
/*     */   public void unregisterMcr(PdfMcr mcrToUnregister) {
/* 237 */     PdfDictionary pageDict = mcrToUnregister.getPageObject();
/* 238 */     if (pageDict == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 243 */     if (pageDict.isFlushed()) {
/* 244 */       throw new PdfException("Cannot remove marked content reference, because its page has been already flushed.");
/*     */     }
/* 246 */     PageMcrsContainer pageMcrs = this.pageToPageMcrs.get(pageDict.getIndirectReference());
/* 247 */     if (pageMcrs != null) {
/*     */       PdfObject stm;
/* 249 */       if ((stm = getStm(mcrToUnregister)) != null) {
/*     */         
/* 251 */         PdfIndirectReference xObjectReference = (stm instanceof PdfIndirectReference) ? (PdfIndirectReference)stm : stm.getIndirectReference();
/* 252 */         ((TreeMap)pageMcrs.getPageResourceXObjects().get(xObjectReference)).remove(Integer.valueOf(mcrToUnregister.getMcid()));
/* 253 */         if (((TreeMap)pageMcrs.getPageResourceXObjects().get(xObjectReference)).isEmpty()) {
/* 254 */           pageMcrs.getPageResourceXObjects().remove(xObjectReference);
/* 255 */           this.xObjectToStructParentsInd.remove(xObjectReference);
/*     */         } 
/* 257 */         this.structTreeRoot.setModified();
/* 258 */       } else if (mcrToUnregister instanceof PdfObjRef) {
/* 259 */         PdfDictionary obj = ((PdfDictionary)mcrToUnregister.getPdfObject()).getAsDictionary(PdfName.Obj);
/* 260 */         if (obj != null && !obj.isFlushed()) {
/* 261 */           PdfNumber n = obj.getAsNumber(PdfName.StructParent);
/* 262 */           if (n != null) {
/* 263 */             pageMcrs.getObjRefs().remove(Integer.valueOf(n.intValue()));
/* 264 */             this.structTreeRoot.setModified();
/*     */             return;
/*     */           } 
/*     */         } 
/* 268 */         for (Map.Entry<Integer, PdfMcr> entry : pageMcrs.getObjRefs().entrySet()) {
/* 269 */           if (((PdfMcr)entry.getValue()).getPdfObject() == mcrToUnregister.getPdfObject()) {
/* 270 */             pageMcrs.getObjRefs().remove(entry.getKey());
/* 271 */             this.structTreeRoot.setModified();
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 276 */         pageMcrs.getPageContentStreamsMcrs().remove(Integer.valueOf(mcrToUnregister.getMcid()));
/* 277 */         this.structTreeRoot.setModified();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerAllMcrs() {
/* 283 */     this.pageToPageMcrs = new HashMap<>();
/*     */ 
/*     */     
/* 286 */     Map<Integer, PdfObject> parentTreeEntries = (new PdfNumTree(this.structTreeRoot.getDocument().getCatalog(), PdfName.ParentTree)).getNumbers();
/* 287 */     Set<PdfDictionary> mcrParents = new LinkedHashSet<>();
/* 288 */     int maxStructParentIndex = -1;
/* 289 */     for (Map.Entry<Integer, PdfObject> entry : parentTreeEntries.entrySet()) {
/* 290 */       if (((Integer)entry.getKey()).intValue() > maxStructParentIndex) {
/* 291 */         maxStructParentIndex = ((Integer)entry.getKey()).intValue();
/*     */       }
/*     */       
/* 294 */       PdfObject entryValue = entry.getValue();
/* 295 */       if (entryValue.isDictionary()) {
/* 296 */         mcrParents.add((PdfDictionary)entryValue); continue;
/* 297 */       }  if (entryValue.isArray()) {
/* 298 */         PdfArray parentsArray = (PdfArray)entryValue;
/* 299 */         for (int i = 0; i < parentsArray.size(); i++) {
/* 300 */           PdfDictionary parent = parentsArray.getAsDictionary(i);
/* 301 */           if (parent != null) {
/* 302 */             mcrParents.add(parent);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 307 */     ((PdfDictionary)this.structTreeRoot.getPdfObject()).put(PdfName.ParentTreeNextKey, (PdfObject)new PdfNumber(maxStructParentIndex + 1));
/*     */     
/* 309 */     for (PdfObject mcrParent : mcrParents) {
/* 310 */       PdfStructElem mcrParentStructElem = new PdfStructElem((PdfDictionary)mcrParent);
/* 311 */       for (IStructureNode kid : mcrParentStructElem.getKids()) {
/* 312 */         if (kid instanceof PdfMcr)
/* 313 */           registerMcr((PdfMcr)kid, true); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean updateStructParentTreeEntries(PdfPage page, PageMcrsContainer mcrs) {
/*     */     int pageStructParentIndex;
/* 320 */     boolean res = false;
/*     */     
/* 322 */     for (Map.Entry<Integer, PdfMcr> entry : mcrs.getObjRefs().entrySet()) {
/* 323 */       PdfMcr mcr = entry.getValue();
/* 324 */       PdfDictionary parentObj = (PdfDictionary)((PdfStructElem)mcr.getParent()).getPdfObject();
/* 325 */       if (!parentObj.isIndirect()) {
/*     */         continue;
/*     */       }
/* 328 */       int structParent = ((Integer)entry.getKey()).intValue();
/* 329 */       this.parentTree.addEntry(structParent, (PdfObject)parentObj);
/* 330 */       res = true;
/*     */     } 
/*     */ 
/*     */     
/* 334 */     for (Map.Entry<PdfIndirectReference, TreeMap<Integer, PdfMcr>> entry : mcrs.getPageResourceXObjects()
/* 335 */       .entrySet()) {
/* 336 */       PdfIndirectReference xObjectRef = entry.getKey();
/* 337 */       if (this.xObjectToStructParentsInd.containsKey(xObjectRef)) {
/* 338 */         pageStructParentIndex = ((Integer)this.xObjectToStructParentsInd.remove(xObjectRef)).intValue();
/* 339 */         if (updateStructParentTreeForContentStreamEntries(entry.getValue(), pageStructParentIndex)) {
/* 340 */           res = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 344 */     if (page.isFlushed()) {
/* 345 */       PdfIndirectReference pageRef = ((PdfDictionary)page.getPdfObject()).getIndirectReference();
/* 346 */       if (!this.pageToStructParentsInd.containsKey(pageRef)) {
/* 347 */         return res;
/*     */       }
/* 349 */       pageStructParentIndex = ((Integer)this.pageToStructParentsInd.remove(pageRef)).intValue();
/*     */     } else {
/* 351 */       pageStructParentIndex = getOrCreatePageStructParentIndex(page);
/*     */     } 
/* 353 */     if (updateStructParentTreeForContentStreamEntries(mcrs.getPageContentStreamsMcrs(), pageStructParentIndex)) {
/* 354 */       res = true;
/*     */     }
/*     */     
/* 357 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean updateStructParentTreeForContentStreamEntries(Map<Integer, PdfMcr> mcrsOfContentStream, int pageStructParentIndex) {
/* 364 */     PdfArray parentsOfMcrs = new PdfArray();
/* 365 */     int currentMcid = 0;
/* 366 */     for (Map.Entry<Integer, PdfMcr> entry : mcrsOfContentStream.entrySet()) {
/* 367 */       PdfMcr mcr = entry.getValue();
/* 368 */       PdfDictionary parentObj = (PdfDictionary)((PdfStructElem)mcr.getParent()).getPdfObject();
/* 369 */       if (!parentObj.isIndirect()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 374 */       while (currentMcid++ < mcr.getMcid()) {
/* 375 */         parentsOfMcrs.add((PdfObject)PdfNull.PDF_NULL);
/*     */       }
/* 377 */       parentsOfMcrs.add((PdfObject)parentObj);
/*     */     } 
/*     */     
/* 380 */     if (!parentsOfMcrs.isEmpty()) {
/* 381 */       parentsOfMcrs.makeIndirect(this.structTreeRoot.getDocument());
/* 382 */       this.parentTree.addEntry(pageStructParentIndex, (PdfObject)parentsOfMcrs);
/* 383 */       this.structTreeRoot.getDocument().checkIsoConformance(parentsOfMcrs, IsoKey.TAG_STRUCTURE_ELEMENT);
/* 384 */       parentsOfMcrs.flush();
/* 385 */       return true;
/*     */     } 
/* 387 */     return false;
/*     */   }
/*     */   
/*     */   private int getOrCreatePageStructParentIndex(PdfPage page) {
/* 391 */     int structParentIndex = page.getStructParentIndex();
/* 392 */     if (structParentIndex < 0) {
/* 393 */       structParentIndex = page.getDocument().getNextStructParentIndex();
/* 394 */       ((PdfDictionary)page.getPdfObject()).put(PdfName.StructParents, (PdfObject)new PdfNumber(structParentIndex));
/*     */     } 
/* 396 */     return structParentIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfObject getStm(PdfMcr mcr) {
/* 404 */     if (mcr instanceof PdfMcrDictionary) {
/* 405 */       return ((PdfDictionary)mcr.getPdfObject()).get(PdfName.Stm, false);
/*     */     }
/* 407 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class PageMcrsContainer
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 8739394375814645643L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 423 */     Map<Integer, PdfMcr> objRefs = new LinkedHashMap<>();
/* 424 */     NavigableMap<Integer, PdfMcr> pageContentStreams = new TreeMap<>();
/* 425 */     Map<PdfIndirectReference, TreeMap<Integer, PdfMcr>> pageResourceXObjects = new LinkedHashMap<>();
/*     */ 
/*     */     
/*     */     void putObjectReferenceMcr(int structParentIndex, PdfMcr mcr) {
/* 429 */       this.objRefs.put(Integer.valueOf(structParentIndex), mcr);
/*     */     }
/*     */     
/*     */     void putPageContentStreamMcr(int mcid, PdfMcr mcr) {
/* 433 */       this.pageContentStreams.put(Integer.valueOf(mcid), mcr);
/*     */     }
/*     */     
/*     */     void putXObjectMcr(PdfIndirectReference xObjectIndRef, PdfMcr mcr) {
/* 437 */       TreeMap<Integer, PdfMcr> xObjectMcrs = this.pageResourceXObjects.get(xObjectIndRef);
/* 438 */       if (xObjectMcrs == null) {
/* 439 */         xObjectMcrs = new TreeMap<>();
/* 440 */         this.pageResourceXObjects.put(xObjectIndRef, xObjectMcrs);
/*     */       } 
/* 442 */       ((TreeMap<Integer, PdfMcr>)this.pageResourceXObjects.get(xObjectIndRef)).put(Integer.valueOf(mcr.getMcid()), mcr);
/*     */     }
/*     */     
/*     */     NavigableMap<Integer, PdfMcr> getPageContentStreamsMcrs() {
/* 446 */       return this.pageContentStreams;
/*     */     }
/*     */     
/*     */     Map<Integer, PdfMcr> getObjRefs() {
/* 450 */       return this.objRefs;
/*     */     }
/*     */     
/*     */     Map<PdfIndirectReference, TreeMap<Integer, PdfMcr>> getPageResourceXObjects() {
/* 454 */       return this.pageResourceXObjects;
/*     */     }
/*     */     
/*     */     Collection<PdfMcr> getAllMcrsAsCollection() {
/* 458 */       Collection<PdfMcr> collection = new ArrayList<>();
/* 459 */       collection.addAll(this.objRefs.values());
/* 460 */       collection.addAll(this.pageContentStreams.values());
/* 461 */       for (Map.Entry<PdfIndirectReference, TreeMap<Integer, PdfMcr>> entry : this.pageResourceXObjects.entrySet()) {
/* 462 */         collection.addAll(((TreeMap)entry.getValue()).values());
/*     */       }
/* 464 */       return collection;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/ParentTreeHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */