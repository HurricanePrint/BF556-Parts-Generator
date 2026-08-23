/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.IsoKey;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import com.itextpdf.kernel.pdf.VersionConforming;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ public class PdfStructElem
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */   implements IStructureNode
/*     */ {
/*     */   private static final long serialVersionUID = 7204356181229674005L;
/*     */   
/*     */   public PdfStructElem(PdfDictionary pdfObject) {
/*  83 */     super((PdfObject)pdfObject);
/*  84 */     setForbidRelease();
/*     */   }
/*     */   
/*     */   public PdfStructElem(PdfDocument document, PdfName role, PdfPage page) {
/*  88 */     this(document, role);
/*     */     
/*  90 */     ((PdfDictionary)getPdfObject()).put(PdfName.Pg, (PdfObject)((PdfDictionary)page.getPdfObject()).getIndirectReference());
/*     */   }
/*     */   
/*     */   public PdfStructElem(PdfDocument document, PdfName role, PdfAnnotation annot) {
/*  94 */     this(document, role);
/*  95 */     if (annot.getPage() == null) {
/*  96 */       throw new PdfException("Annotation shall have reference to page.");
/*     */     }
/*  98 */     ((PdfDictionary)getPdfObject()).put(PdfName.Pg, (PdfObject)((PdfDictionary)annot.getPage().getPdfObject()).getIndirectReference());
/*     */   }
/*     */   
/*     */   public PdfStructElem(PdfDocument document, PdfName role) {
/* 102 */     this((PdfDictionary)(new PdfDictionary()).makeIndirect(document));
/* 103 */     ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.StructElem);
/* 104 */     ((PdfDictionary)getPdfObject()).put(PdfName.S, (PdfObject)role);
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
/*     */   public static boolean isStructElem(PdfDictionary dictionary) {
/* 116 */     return (PdfName.StructElem.equals(dictionary.getAsName(PdfName.Type)) || dictionary
/* 117 */       .containsKey(PdfName.S));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getAttributes(boolean createNewIfNull) {
/*     */     PdfDictionary pdfDictionary;
/* 129 */     PdfObject attributes = ((PdfDictionary)getPdfObject()).get(PdfName.A);
/* 130 */     if (attributes == null && createNewIfNull) {
/* 131 */       pdfDictionary = new PdfDictionary();
/* 132 */       setAttributes((PdfObject)pdfDictionary);
/*     */     } 
/* 134 */     return (PdfObject)pdfDictionary;
/*     */   }
/*     */   
/*     */   public void setAttributes(PdfObject attributes) {
/* 138 */     put(PdfName.A, attributes);
/*     */   }
/*     */   
/*     */   public PdfString getLang() {
/* 142 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.Lang);
/*     */   }
/*     */   
/*     */   public void setLang(PdfString lang) {
/* 146 */     put(PdfName.Lang, (PdfObject)lang);
/*     */   }
/*     */   
/*     */   public PdfString getAlt() {
/* 150 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.Alt);
/*     */   }
/*     */   
/*     */   public void setAlt(PdfString alt) {
/* 154 */     put(PdfName.Alt, (PdfObject)alt);
/*     */   }
/*     */   
/*     */   public PdfString getActualText() {
/* 158 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.ActualText);
/*     */   }
/*     */   
/*     */   public void setActualText(PdfString actualText) {
/* 162 */     put(PdfName.ActualText, (PdfObject)actualText);
/*     */   }
/*     */   
/*     */   public PdfString getE() {
/* 166 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.E);
/*     */   }
/*     */   
/*     */   public void setE(PdfString e) {
/* 170 */     put(PdfName.E, (PdfObject)e);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getRole() {
/* 175 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.S);
/*     */   }
/*     */   
/*     */   public void setRole(PdfName role) {
/* 179 */     put(PdfName.S, (PdfObject)role);
/*     */   }
/*     */   
/*     */   public PdfStructElem addKid(PdfStructElem kid) {
/* 183 */     return addKid(-1, kid);
/*     */   }
/*     */   
/*     */   public PdfStructElem addKid(int index, PdfStructElem kid) {
/* 187 */     addKidObject((PdfDictionary)getPdfObject(), index, kid.getPdfObject());
/* 188 */     return kid;
/*     */   }
/*     */   
/*     */   public PdfMcr addKid(PdfMcr kid) {
/* 192 */     return addKid(-1, kid);
/*     */   }
/*     */   
/*     */   public PdfMcr addKid(int index, PdfMcr kid) {
/* 196 */     getDocEnsureIndirectForKids().getStructTreeRoot().getParentTreeHandler().registerMcr(kid);
/* 197 */     addKidObject((PdfDictionary)getPdfObject(), index, kid.getPdfObject());
/* 198 */     return kid;
/*     */   }
/*     */   
/*     */   public IStructureNode removeKid(int index) {
/* 202 */     return removeKid(index, false);
/*     */   }
/*     */   
/*     */   public IStructureNode removeKid(int index, boolean prepareForReAdding) {
/* 206 */     PdfObject k = getK();
/* 207 */     if (k == null || (!k.isArray() && index != 0)) {
/* 208 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/* 211 */     if (k.isArray()) {
/* 212 */       PdfArray kidsArray = (PdfArray)k;
/* 213 */       k = kidsArray.get(index);
/* 214 */       kidsArray.remove(index);
/* 215 */       if (kidsArray.isEmpty()) {
/* 216 */         ((PdfDictionary)getPdfObject()).remove(PdfName.K);
/*     */       }
/*     */     } else {
/* 219 */       ((PdfDictionary)getPdfObject()).remove(PdfName.K);
/*     */     } 
/* 221 */     setModified();
/*     */     
/* 223 */     IStructureNode removedKid = convertPdfObjectToIPdfStructElem(k);
/* 224 */     PdfDocument doc = getDocument();
/* 225 */     if (removedKid instanceof PdfMcr && doc != null && !prepareForReAdding) {
/* 226 */       doc.getStructTreeRoot().getParentTreeHandler().unregisterMcr((PdfMcr)removedKid);
/*     */     }
/* 228 */     return removedKid;
/*     */   }
/*     */   
/*     */   public int removeKid(IStructureNode kid) {
/* 232 */     if (kid instanceof PdfMcr) {
/* 233 */       PdfMcr mcr = (PdfMcr)kid;
/* 234 */       PdfDocument doc = getDocument();
/* 235 */       if (doc != null) {
/* 236 */         doc.getStructTreeRoot().getParentTreeHandler().unregisterMcr(mcr);
/*     */       }
/* 238 */       return removeKidObject(mcr.getPdfObject());
/* 239 */     }  if (kid instanceof PdfStructElem) {
/* 240 */       return removeKidObject(((PdfStructElem)kid).getPdfObject());
/*     */     }
/* 242 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IStructureNode getParent() {
/* 250 */     PdfDictionary parent = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.P);
/* 251 */     if (parent == null) {
/* 252 */       return null;
/*     */     }
/*     */     
/* 255 */     if (parent.isFlushed()) {
/* 256 */       PdfDocument pdfDocument = getDocument();
/* 257 */       if (pdfDocument == null) {
/* 258 */         return null;
/*     */       }
/* 260 */       PdfStructTreeRoot structTreeRoot = pdfDocument.getStructTreeRoot();
/* 261 */       return (structTreeRoot.getPdfObject() == parent) ? structTreeRoot : new PdfStructElem(parent);
/*     */     } 
/*     */     
/* 264 */     if (isStructElem(parent)) {
/* 265 */       return new PdfStructElem(parent);
/*     */     }
/* 267 */     PdfDocument pdfDoc = getDocument();
/* 268 */     boolean parentIsRoot = (pdfDoc != null && PdfName.StructTreeRoot.equals(parent.getAsName(PdfName.Type)));
/* 269 */     parentIsRoot = (parentIsRoot || (pdfDoc != null && pdfDoc.getStructTreeRoot().getPdfObject() == parent));
/* 270 */     if (parentIsRoot) {
/* 271 */       return pdfDoc.getStructTreeRoot();
/*     */     }
/* 273 */     return null;
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
/*     */   public List<IStructureNode> getKids() {
/* 286 */     PdfObject k = getK();
/* 287 */     List<IStructureNode> kids = new ArrayList<>();
/* 288 */     if (k != null) {
/* 289 */       if (k.isArray()) {
/* 290 */         PdfArray a = (PdfArray)k;
/* 291 */         for (int i = 0; i < a.size(); i++) {
/* 292 */           addKidObjectToStructElemList(a.get(i), kids);
/*     */         }
/*     */       } else {
/* 295 */         addKidObjectToStructElemList(k, kids);
/*     */       } 
/*     */     }
/* 298 */     return kids;
/*     */   }
/*     */   
/*     */   public PdfObject getK() {
/* 302 */     return ((PdfDictionary)getPdfObject()).get(PdfName.K);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfStructElem> getRefsList() {
/* 312 */     PdfArray refsArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Ref);
/* 313 */     if (refsArray == null) {
/* 314 */       return Collections.emptyList();
/*     */     }
/* 316 */     List<PdfStructElem> refs = new ArrayList<>(refsArray.size());
/* 317 */     for (int i = 0; i < refsArray.size(); i++) {
/* 318 */       refs.add(new PdfStructElem(refsArray.getAsDictionary(i)));
/*     */     }
/* 320 */     return refs;
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
/*     */   public void addRef(PdfStructElem ref) {
/* 333 */     if (!((PdfDictionary)ref.getPdfObject()).isIndirect()) {
/* 334 */       throw new PdfException("Ref array items in structure element dictionary shall be indirect objects.");
/*     */     }
/* 336 */     VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.Ref, PdfName.StructElem);
/* 337 */     PdfArray refsArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Ref);
/* 338 */     if (refsArray == null) {
/* 339 */       refsArray = new PdfArray();
/* 340 */       put(PdfName.Ref, (PdfObject)refsArray);
/*     */     } 
/* 342 */     refsArray.add(ref.getPdfObject());
/* 343 */     setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNamespace getNamespace() {
/* 353 */     PdfDictionary nsDict = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.NS);
/* 354 */     return (nsDict != null) ? new PdfNamespace(nsDict) : null;
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
/*     */   public void setNamespace(PdfNamespace namespace) {
/* 366 */     VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.NS, PdfName.StructElem);
/* 367 */     if (namespace != null) {
/* 368 */       put(PdfName.NS, namespace.getPdfObject());
/*     */     } else {
/* 370 */       ((PdfDictionary)getPdfObject()).remove(PdfName.NS);
/* 371 */       setModified();
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
/*     */ 
/*     */   
/*     */   public void setPhoneme(PdfString elementPhoneme) {
/* 385 */     VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.Phoneme, PdfName.StructElem);
/* 386 */     put(PdfName.Phoneme, (PdfObject)elementPhoneme);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getPhoneme() {
/* 397 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.Phoneme);
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
/*     */   public void setPhoneticAlphabet(PdfName phoneticAlphabet) {
/* 417 */     VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.PhoneticAlphabet, PdfName.StructElem);
/* 418 */     put(PdfName.PhoneticAlphabet, (PdfObject)phoneticAlphabet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getPhoneticAlphabet() {
/* 429 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.PhoneticAlphabet);
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
/*     */   public void addAssociatedFile(String description, PdfFileSpec fs) {
/* 445 */     if (null == ((PdfDictionary)fs.getPdfObject()).get(PdfName.AFRelationship)) {
/* 446 */       Logger logger = LoggerFactory.getLogger(PdfStructElem.class);
/* 447 */       logger.error("For associated files their associated file specification dictionaries shall include the AFRelationship key.");
/*     */     } 
/* 449 */     if (null != description) {
/* 450 */       getDocument().getCatalog().getNameTree(PdfName.EmbeddedFiles).addEntry(description, fs.getPdfObject());
/*     */     }
/* 452 */     PdfArray afArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.AF);
/* 453 */     if (afArray == null) {
/* 454 */       afArray = new PdfArray();
/* 455 */       put(PdfName.AF, (PdfObject)afArray);
/*     */     } 
/* 457 */     afArray.add(fs.getPdfObject());
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
/*     */   public void addAssociatedFile(PdfFileSpec fs) {
/* 472 */     addAssociatedFile((String)null, fs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getAssociatedFiles(boolean create) {
/* 482 */     PdfArray afArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.AF);
/* 483 */     if (afArray == null && create) {
/* 484 */       afArray = new PdfArray();
/* 485 */       put(PdfName.AF, (PdfObject)afArray);
/*     */     } 
/* 487 */     return afArray;
/*     */   }
/*     */   
/*     */   public PdfStructElem put(PdfName key, PdfObject value) {
/* 491 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 492 */     setModified();
/* 493 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/* 498 */     PdfDictionary pageDict = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.Pg);
/* 499 */     if (pageDict == null || (pageDict.getIndirectReference() != null && pageDict.getIndirectReference().isFree())) {
/* 500 */       ((PdfDictionary)getPdfObject()).remove(PdfName.Pg);
/*     */     }
/*     */     
/* 503 */     PdfDocument doc = getDocument();
/* 504 */     if (doc != null) {
/* 505 */       doc.checkIsoConformance(getPdfObject(), IsoKey.TAG_STRUCTURE_ELEMENT);
/*     */     }
/* 507 */     super.flush();
/*     */   }
/*     */   
/*     */   static void addKidObject(PdfDictionary parent, int index, PdfObject kid) {
/* 511 */     if (parent.isFlushed()) {
/* 512 */       throw new PdfException("Cannot add kid to the flushed element.");
/*     */     }
/* 514 */     if (!parent.containsKey(PdfName.P)) {
/* 515 */       throw new PdfException("StructureElement shall contain parent object.", parent);
/*     */     }
/* 517 */     PdfObject k = parent.get(PdfName.K);
/* 518 */     if (k == null) {
/* 519 */       parent.put(PdfName.K, kid);
/*     */     } else {
/*     */       PdfArray a;
/* 522 */       if (k instanceof PdfArray) {
/* 523 */         a = (PdfArray)k;
/*     */       } else {
/* 525 */         a = new PdfArray();
/* 526 */         a.add(k);
/* 527 */         parent.put(PdfName.K, (PdfObject)a);
/*     */       } 
/* 529 */       if (index == -1) {
/* 530 */         a.add(kid);
/*     */       } else {
/* 532 */         a.add(index, kid);
/*     */       } 
/*     */     } 
/* 535 */     parent.setModified();
/* 536 */     if (kid instanceof PdfDictionary && isStructElem((PdfDictionary)kid)) {
/* 537 */       if (!parent.isIndirect()) {
/* 538 */         throw new PdfException("Structure element dictionary shall be an indirect object in order to have children.");
/*     */       }
/* 540 */       ((PdfDictionary)kid).put(PdfName.P, (PdfObject)parent);
/* 541 */       kid.setModified();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 547 */     return true;
/*     */   }
/*     */   
/*     */   protected PdfDocument getDocument() {
/* 551 */     PdfDictionary structDict = (PdfDictionary)getPdfObject();
/* 552 */     PdfIndirectReference indRef = structDict.getIndirectReference();
/* 553 */     if (indRef == null && structDict.getAsDictionary(PdfName.P) != null)
/*     */     {
/*     */       
/* 556 */       indRef = structDict.getAsDictionary(PdfName.P).getIndirectReference();
/*     */     }
/* 558 */     return (indRef != null) ? indRef.getDocument() : null;
/*     */   }
/*     */   
/*     */   private PdfDocument getDocEnsureIndirectForKids() {
/* 562 */     PdfDocument doc = getDocument();
/* 563 */     if (doc == null) {
/* 564 */       throw new PdfException("Structure element dictionary shall be an indirect object in order to have children.");
/*     */     }
/* 566 */     return doc;
/*     */   }
/*     */   
/*     */   private void addKidObjectToStructElemList(PdfObject k, List<IStructureNode> list) {
/* 570 */     if (k.isFlushed()) {
/* 571 */       list.add(null);
/*     */       
/*     */       return;
/*     */     } 
/* 575 */     list.add(convertPdfObjectToIPdfStructElem(k));
/*     */   }
/*     */   private IStructureNode convertPdfObjectToIPdfStructElem(PdfObject obj) {
/*     */     PdfDictionary d;
/* 579 */     IStructureNode elem = null;
/* 580 */     switch (obj.getType()) {
/*     */       case 3:
/* 582 */         d = (PdfDictionary)obj;
/* 583 */         if (isStructElem(d)) {
/* 584 */           elem = new PdfStructElem(d); break;
/* 585 */         }  if (PdfName.MCR.equals(d.getAsName(PdfName.Type))) {
/* 586 */           elem = new PdfMcrDictionary(d, this); break;
/* 587 */         }  if (PdfName.OBJR.equals(d.getAsName(PdfName.Type)))
/* 588 */           elem = new PdfObjRef(d, this); 
/*     */         break;
/*     */       case 8:
/* 591 */         elem = new PdfMcrNumber((PdfNumber)obj, this);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 597 */     return elem;
/*     */   }
/*     */   
/*     */   private int removeKidObject(PdfObject kid) {
/* 601 */     PdfObject k = getK();
/* 602 */     if (k == null || (!k.isArray() && k != kid && k != kid
/* 603 */       .getIndirectReference())) {
/* 604 */       return -1;
/*     */     }
/*     */     
/* 607 */     int removedIndex = -1;
/* 608 */     if (k.isArray()) {
/* 609 */       PdfArray kidsArray = (PdfArray)k;
/* 610 */       removedIndex = removeObjectFromArray(kidsArray, kid);
/*     */     } 
/* 612 */     if (!k.isArray() || (k.isArray() && ((PdfArray)k).isEmpty())) {
/* 613 */       ((PdfDictionary)getPdfObject()).remove(PdfName.K);
/* 614 */       removedIndex = 0;
/*     */     } 
/* 616 */     setModified();
/*     */     
/* 618 */     return removedIndex;
/*     */   }
/*     */   
/*     */   private static int removeObjectFromArray(PdfArray array, PdfObject toRemove) {
/*     */     int i;
/* 623 */     for (i = 0; i < array.size(); i++) {
/* 624 */       PdfObject obj = array.get(i);
/* 625 */       if (obj == toRemove || obj == toRemove.getIndirectReference()) {
/* 626 */         array.remove(i);
/*     */         break;
/*     */       } 
/*     */     } 
/* 630 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfStructElem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */