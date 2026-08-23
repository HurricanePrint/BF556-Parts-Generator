/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.events.Event;
/*     */ import com.itextpdf.kernel.events.PdfDocumentEvent;
/*     */ import com.itextpdf.kernel.pdf.layer.PdfLayer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
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
/*     */ public class PageFlushingHelper
/*     */ {
/* 107 */   private static final DeepFlushingContext pageContext = initPageFlushingContext();
/*     */ 
/*     */   
/*     */   private PdfDocument pdfDoc;
/*     */   
/*     */   private boolean release;
/*     */   
/* 114 */   private HashSet<PdfObject> currNestedObjParents = new HashSet<>();
/*     */   
/* 116 */   private Set<PdfIndirectReference> layersRefs = new HashSet<>();
/*     */ 
/*     */   
/*     */   public PageFlushingHelper(PdfDocument pdfDoc) {
/* 120 */     this.pdfDoc = pdfDoc;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsafeFlushDeep(int pageNum) {
/* 159 */     if (this.pdfDoc.getWriter() == null) {
/* 160 */       throw new IllegalArgumentException("Flushing writes the object to the output stream and releases it from memory. It is only possible for documents that have a PdfWriter associated with them. Use PageFlushingHelper#releaseDeep method instead.");
/*     */     }
/* 162 */     this.release = false;
/* 163 */     flushPage(pageNum);
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
/*     */   
/*     */   public void releaseDeep(int pageNum) {
/* 187 */     this.release = true;
/* 188 */     flushPage(pageNum);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendModeFlush(int pageNum) {
/* 214 */     if (this.pdfDoc.getWriter() == null) {
/* 215 */       throw new IllegalArgumentException("Flushing writes the object to the output stream and releases it from memory. It is only possible for documents that have a PdfWriter associated with them. Use PageFlushingHelper#releaseDeep method instead.");
/*     */     }
/*     */     
/* 218 */     PdfPage page = this.pdfDoc.getPage(pageNum);
/* 219 */     if (page.isFlushed()) {
/*     */       return;
/*     */     }
/* 222 */     page.getDocument().dispatchEvent((Event)new PdfDocumentEvent("EndPdfPage", page));
/*     */     
/* 224 */     boolean pageWasModified = page.getPdfObject().isModified();
/* 225 */     page.setModified();
/* 226 */     this.release = true;
/* 227 */     pageWasModified = (flushPage(pageNum) || pageWasModified);
/*     */     
/* 229 */     PdfArray annots = page.getPdfObject().getAsArray(PdfName.Annots);
/* 230 */     if (annots != null && !annots.isFlushed()) {
/* 231 */       arrayFlushIfModified(annots);
/*     */     }
/*     */     
/* 234 */     PdfObject thumb = page.getPdfObject().get(PdfName.Thumb, false);
/* 235 */     flushIfModified(thumb);
/*     */     
/* 237 */     PdfObject contents = page.getPdfObject().get(PdfName.Contents, false);
/* 238 */     if (contents instanceof PdfIndirectReference) {
/* 239 */       if (contents.checkState((short)8) && !contents.checkState((short)1)) {
/* 240 */         PdfObject contentsDirectObj = ((PdfIndirectReference)contents).getRefersTo();
/* 241 */         if (contentsDirectObj.isArray()) {
/* 242 */           arrayFlushIfModified((PdfArray)contentsDirectObj);
/*     */         } else {
/*     */           
/* 245 */           contentsDirectObj.flush();
/*     */         } 
/*     */       } 
/* 248 */     } else if (contents instanceof PdfArray) {
/* 249 */       arrayFlushIfModified((PdfArray)contents);
/* 250 */     } else if (contents instanceof PdfStream) {
/* 251 */       flushIfModified(contents);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     if (!pageWasModified) {
/* 259 */       page.getPdfObject().getIndirectReference().clearState((short)8);
/* 260 */       this.pdfDoc.getCatalog().getPageTree().releasePage(pageNum);
/* 261 */       page.unsetForbidRelease();
/* 262 */       page.getPdfObject().release();
/*     */     } else {
/*     */       
/* 265 */       page.releaseInstanceFields();
/* 266 */       page.getPdfObject().flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean flushPage(int pageNum) {
/* 271 */     PdfPage page = this.pdfDoc.getPage(pageNum);
/* 272 */     if (page.isFlushed()) {
/* 273 */       return false;
/*     */     }
/* 275 */     boolean pageChanged = false;
/*     */     
/* 277 */     if (!this.release) {
/* 278 */       this.pdfDoc.dispatchEvent((Event)new PdfDocumentEvent("EndPdfPage", page));
/* 279 */       initCurrentLayers(this.pdfDoc);
/*     */     } 
/*     */     
/* 282 */     PdfDictionary pageDict = page.getPdfObject();
/*     */ 
/*     */ 
/*     */     
/* 286 */     PdfDictionary resourcesDict = page.initResources(false);
/* 287 */     PdfResources resources = page.getResources(false);
/* 288 */     if (resources != null && resources.isModified() && !resources.isReadOnly()) {
/* 289 */       resourcesDict = resources.getPdfObject();
/* 290 */       pageDict.put(PdfName.Resources, resources.getPdfObject());
/* 291 */       pageDict.setModified();
/* 292 */       pageChanged = true;
/*     */     } 
/*     */     
/* 295 */     if (!resourcesDict.isFlushed()) {
/* 296 */       flushDictRecursively(resourcesDict, null);
/* 297 */       flushOrRelease(resourcesDict);
/*     */     } 
/*     */     
/* 300 */     flushDictRecursively(pageDict, pageContext);
/*     */     
/* 302 */     if (this.release) {
/* 303 */       if (!page.getPdfObject().isModified()) {
/* 304 */         this.pdfDoc.getCatalog().getPageTree().releasePage(pageNum);
/* 305 */         page.unsetForbidRelease();
/* 306 */         page.getPdfObject().release();
/*     */       } 
/*     */     } else {
/* 309 */       if (this.pdfDoc.isTagged() && !this.pdfDoc.getStructTreeRoot().isFlushed()) {
/* 310 */         page.tryFlushPageTags();
/*     */       }
/* 312 */       if (!this.pdfDoc.isAppendMode() || page.getPdfObject().isModified()) {
/* 313 */         page.releaseInstanceFields();
/* 314 */         page.getPdfObject().flush();
/*     */       }
/*     */       else {
/*     */         
/* 318 */         this.pdfDoc.getCatalog().getPageTree().releasePage(pageNum);
/* 319 */         page.unsetForbidRelease();
/* 320 */         page.getPdfObject().release();
/*     */       } 
/*     */     } 
/*     */     
/* 324 */     this.layersRefs.clear();
/*     */     
/* 326 */     return pageChanged;
/*     */   }
/*     */   
/*     */   private void initCurrentLayers(PdfDocument pdfDoc) {
/* 330 */     if (pdfDoc.getCatalog().isOCPropertiesMayHaveChanged()) {
/* 331 */       List<PdfLayer> layers = pdfDoc.getCatalog().getOCProperties(false).getLayers();
/* 332 */       for (PdfLayer layer : layers) {
/* 333 */         this.layersRefs.add(((PdfDictionary)layer.getPdfObject()).getIndirectReference());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void flushObjectRecursively(PdfObject obj, DeepFlushingContext context) {
/* 339 */     if (obj == null) {
/*     */       return;
/*     */     }
/* 342 */     boolean avoidReleaseForIndirectObjInstance = false;
/* 343 */     if (obj.isIndirectReference())
/* 344 */     { PdfIndirectReference indRef = (PdfIndirectReference)obj;
/* 345 */       if (indRef.refersTo == null || indRef.checkState((short)1)) {
/*     */         return;
/*     */       }
/*     */       
/* 349 */       obj = indRef.getRefersTo(); }
/* 350 */     else { if (obj.isFlushed())
/*     */         return; 
/* 352 */       if (this.release && obj.isIndirect()) {
/*     */ 
/*     */ 
/*     */         
/* 356 */         assert obj.isReleaseForbidden() || obj.getIndirectReference() == null;
/* 357 */         avoidReleaseForIndirectObjInstance = true;
/*     */       }  }
/* 359 */      if (this.pdfDoc.isDocumentFont(obj.getIndirectReference()) || this.layersRefs.contains(obj.getIndirectReference())) {
/*     */       return;
/*     */     }
/*     */     
/* 363 */     if (obj.isDictionary() || obj.isStream()) {
/* 364 */       if (!this.currNestedObjParents.add(obj)) {
/*     */         return;
/*     */       }
/* 367 */       flushDictRecursively((PdfDictionary)obj, context);
/* 368 */       this.currNestedObjParents.remove(obj);
/* 369 */     } else if (obj.isArray()) {
/* 370 */       if (!this.currNestedObjParents.add(obj)) {
/*     */         return;
/*     */       }
/* 373 */       PdfArray array = (PdfArray)obj;
/* 374 */       for (int i = 0; i < array.size(); i++) {
/* 375 */         flushObjectRecursively(array.get(i, false), context);
/*     */       }
/* 377 */       this.currNestedObjParents.remove(obj);
/*     */     } 
/*     */     
/* 380 */     if (!avoidReleaseForIndirectObjInstance) {
/* 381 */       flushOrRelease(obj);
/*     */     }
/*     */   }
/*     */   
/*     */   private void flushDictRecursively(PdfDictionary dict, DeepFlushingContext context) {
/* 386 */     for (PdfName key : dict.keySet()) {
/* 387 */       DeepFlushingContext innerContext = null;
/* 388 */       if (context != null) {
/* 389 */         if (context.isKeyInBlackList(key)) {
/*     */           continue;
/*     */         }
/* 392 */         innerContext = context.getInnerContextFor(key);
/*     */       } 
/* 394 */       PdfObject value = dict.get(key, false);
/* 395 */       flushObjectRecursively(value, innerContext);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void flushOrRelease(PdfObject obj) {
/* 400 */     if (this.release) {
/* 401 */       if (!obj.isReleaseForbidden()) {
/* 402 */         obj.release();
/*     */       }
/*     */     } else {
/* 405 */       makeIndirectIfNeeded(obj);
/* 406 */       if (!this.pdfDoc.isAppendMode() || obj.isModified()) {
/* 407 */         obj.flush();
/* 408 */       } else if (!obj.isReleaseForbidden()) {
/* 409 */         obj.release();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void flushIfModified(PdfObject o) {
/* 415 */     if (o != null && !(o instanceof PdfIndirectReference)) {
/* 416 */       makeIndirectIfNeeded(o);
/* 417 */       o = o.getIndirectReference();
/*     */     } 
/* 419 */     if (o != null && o.checkState((short)8) && !o.checkState((short)1)) {
/* 420 */       ((PdfIndirectReference)o).getRefersTo().flush();
/*     */     }
/*     */   }
/*     */   
/*     */   private void arrayFlushIfModified(PdfArray contentsArr) {
/* 425 */     for (int i = 0; i < contentsArr.size(); i++) {
/* 426 */       PdfObject c = contentsArr.get(i, false);
/* 427 */       flushIfModified(c);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void makeIndirectIfNeeded(PdfObject o) {
/* 432 */     if (o.checkState((short)64)) {
/* 433 */       o.makeIndirect(this.pdfDoc);
/*     */     }
/*     */   }
/*     */   
/*     */   private static DeepFlushingContext initPageFlushingContext() {
/* 438 */     Set<PdfName> ALL_KEYS_IN_BLACK_LIST = null;
/* 439 */     Map<PdfName, DeepFlushingContext> NO_INNER_CONTEXTS = Collections.emptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     DeepFlushingContext actionContext = new DeepFlushingContext(new LinkedHashSet<>(Arrays.asList(new PdfName[] { PdfName.D, PdfName.SD, PdfName.Dp, PdfName.B, PdfName.Annotation, PdfName.T, PdfName.AN, PdfName.TA }, )), NO_INNER_CONTEXTS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 458 */     DeepFlushingContext aaContext = new DeepFlushingContext(actionContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 466 */     LinkedHashMap<PdfName, DeepFlushingContext> annotInnerContexts = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 470 */     DeepFlushingContext annotsContext = new DeepFlushingContext(new LinkedHashSet<>(Arrays.asList(new PdfName[] { PdfName.P, PdfName.Popup, PdfName.Dest, PdfName.Parent, PdfName.V }, )), annotInnerContexts);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 481 */     annotInnerContexts.put(PdfName.A, actionContext);
/* 482 */     annotInnerContexts.put(PdfName.PA, actionContext);
/* 483 */     annotInnerContexts.put(PdfName.AA, aaContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     DeepFlushingContext sepInfoContext = new DeepFlushingContext(new LinkedHashSet<>(Collections.singletonList(PdfName.Pages)), NO_INNER_CONTEXTS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 497 */     DeepFlushingContext bContext = new DeepFlushingContext(ALL_KEYS_IN_BLACK_LIST, NO_INNER_CONTEXTS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 505 */     LinkedHashMap<PdfName, DeepFlushingContext> presStepsInnerContexts = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 509 */     DeepFlushingContext presStepsContext = new DeepFlushingContext(new LinkedHashSet<>(Collections.singletonList(PdfName.Prev)), presStepsInnerContexts);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     presStepsInnerContexts.put(PdfName.NA, actionContext);
/* 516 */     presStepsInnerContexts.put(PdfName.PA, actionContext);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 521 */     LinkedHashMap<PdfName, DeepFlushingContext> pageInnerContexts = new LinkedHashMap<>();
/*     */ 
/*     */     
/* 524 */     DeepFlushingContext pageContext = new DeepFlushingContext(new LinkedHashSet<>(Arrays.asList(new PdfName[] { PdfName.Parent, PdfName.DPart }, )), pageInnerContexts);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 531 */     pageInnerContexts.put(PdfName.Annots, annotsContext);
/* 532 */     pageInnerContexts.put(PdfName.B, bContext);
/* 533 */     pageInnerContexts.put(PdfName.AA, aaContext);
/* 534 */     pageInnerContexts.put(PdfName.SeparationInfo, sepInfoContext);
/* 535 */     pageInnerContexts.put(PdfName.PresSteps, presStepsContext);
/*     */ 
/*     */     
/* 538 */     return pageContext;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DeepFlushingContext
/*     */   {
/*     */     Set<PdfName> blackList;
/*     */     Map<PdfName, DeepFlushingContext> innerContexts;
/*     */     DeepFlushingContext unconditionalInnerContext;
/*     */     
/*     */     public DeepFlushingContext(Set<PdfName> blackList, Map<PdfName, DeepFlushingContext> innerContexts) {
/* 549 */       this.blackList = blackList;
/* 550 */       this.innerContexts = innerContexts;
/*     */     }
/*     */     
/*     */     public DeepFlushingContext(DeepFlushingContext unconditionalInnerContext) {
/* 554 */       this.blackList = Collections.emptySet();
/* 555 */       this.innerContexts = null;
/* 556 */       this.unconditionalInnerContext = unconditionalInnerContext;
/*     */     }
/*     */     
/*     */     public boolean isKeyInBlackList(PdfName key) {
/* 560 */       return (this.blackList == null || this.blackList.contains(key));
/*     */     }
/*     */     
/*     */     public DeepFlushingContext getInnerContextFor(PdfName key) {
/* 564 */       return (this.innerContexts == null) ? this.unconditionalInnerContext : this.innerContexts.get(key);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PageFlushingHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */