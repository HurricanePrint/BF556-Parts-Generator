/*     */ package com.itextpdf.kernel.pdf.tagging;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import com.itextpdf.kernel.pdf.VersionConforming;
/*     */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class PdfStructTreeRoot
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */   implements IStructureNode
/*     */ {
/*     */   private static final long serialVersionUID = 2168384302241193868L;
/*     */   private PdfDocument document;
/*     */   private ParentTreeHandler parentTreeHandler;
/*  78 */   private static Map<String, PdfName> staticRoleNames = new ConcurrentHashMap<>();
/*     */   
/*     */   public PdfStructTreeRoot(PdfDocument document) {
/*  81 */     this((PdfDictionary)(new PdfDictionary()).makeIndirect(document), document);
/*  82 */     ((PdfDictionary)getPdfObject()).put(PdfName.Type, (PdfObject)PdfName.StructTreeRoot);
/*     */   }
/*     */   
/*     */   public PdfStructTreeRoot(PdfDictionary pdfObject, PdfDocument document) {
/*  86 */     super((PdfObject)pdfObject);
/*  87 */     this.document = document;
/*  88 */     if (this.document == null) {
/*  89 */       ensureObjectIsAddedToDocument((PdfObject)pdfObject);
/*  90 */       this.document = pdfObject.getIndirectReference().getDocument();
/*     */     } 
/*  92 */     setForbidRelease();
/*  93 */     this.parentTreeHandler = new ParentTreeHandler(this);
/*     */     
/*  95 */     getRoleMap();
/*     */   }
/*     */   
/*     */   public static PdfName convertRoleToPdfName(String role) {
/*  99 */     PdfName name = (PdfName)PdfName.staticNames.get(role);
/* 100 */     if (name != null) {
/* 101 */       return name;
/*     */     }
/* 103 */     name = staticRoleNames.get(role);
/* 104 */     if (name != null) {
/* 105 */       return name;
/*     */     }
/* 107 */     name = new PdfName(role);
/* 108 */     staticRoleNames.put(role, name);
/* 109 */     return name;
/*     */   }
/*     */   
/*     */   public PdfStructElem addKid(PdfStructElem structElem) {
/* 113 */     return addKid(-1, structElem);
/*     */   }
/*     */   
/*     */   public PdfStructElem addKid(int index, PdfStructElem structElem) {
/* 117 */     addKidObject(index, (PdfDictionary)structElem.getPdfObject());
/* 118 */     return structElem;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStructureNode getParent() {
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IStructureNode> getKids() {
/* 134 */     PdfObject k = ((PdfDictionary)getPdfObject()).get(PdfName.K);
/* 135 */     List<IStructureNode> kids = new ArrayList<>();
/*     */     
/* 137 */     if (k != null) {
/* 138 */       if (k.isArray()) {
/* 139 */         PdfArray a = (PdfArray)k;
/* 140 */         for (int i = 0; i < a.size(); i++) {
/* 141 */           ifKidIsStructElementAddToList(a.get(i), kids);
/*     */         }
/*     */       } else {
/* 144 */         ifKidIsStructElementAddToList(k, kids);
/*     */       } 
/*     */     }
/* 147 */     return kids;
/*     */   }
/*     */   
/*     */   public PdfArray getKidsObject() {
/* 151 */     PdfArray k = null;
/* 152 */     PdfObject kObj = ((PdfDictionary)getPdfObject()).get(PdfName.K);
/* 153 */     if (kObj != null && kObj.isArray()) {
/* 154 */       k = (PdfArray)kObj;
/*     */     }
/* 156 */     if (k == null) {
/* 157 */       k = new PdfArray();
/* 158 */       ((PdfDictionary)getPdfObject()).put(PdfName.K, (PdfObject)k);
/* 159 */       setModified();
/* 160 */       if (kObj != null) {
/* 161 */         k.add(kObj);
/*     */       }
/*     */     } 
/* 164 */     return k;
/*     */   }
/*     */   
/*     */   public void addRoleMapping(String fromRole, String toRole) {
/* 168 */     PdfDictionary roleMap = getRoleMap();
/* 169 */     PdfObject prevVal = roleMap.put(convertRoleToPdfName(fromRole), (PdfObject)convertRoleToPdfName(toRole));
/* 170 */     if (prevVal != null && prevVal instanceof PdfName) {
/* 171 */       Logger logger = LoggerFactory.getLogger(PdfStructTreeRoot.class);
/* 172 */       logger.warn(MessageFormat.format("Existing mapping for {0} in structure tree root role map was {1} and it was overwritten with {2}.", new Object[] { fromRole, prevVal, toRole }));
/*     */     } 
/*     */     
/* 175 */     if (roleMap.isIndirect()) {
/* 176 */       roleMap.setModified();
/*     */     } else {
/* 178 */       setModified();
/*     */     } 
/*     */   }
/*     */   
/*     */   public PdfDictionary getRoleMap() {
/* 183 */     PdfDictionary roleMap = ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.RoleMap);
/* 184 */     if (roleMap == null) {
/* 185 */       roleMap = new PdfDictionary();
/* 186 */       ((PdfDictionary)getPdfObject()).put(PdfName.RoleMap, (PdfObject)roleMap);
/* 187 */       setModified();
/*     */     } 
/* 189 */     return roleMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PdfNamespace> getNamespaces() {
/* 200 */     PdfArray namespacesArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Namespaces);
/* 201 */     if (namespacesArray == null) {
/* 202 */       return Collections.emptyList();
/*     */     }
/* 204 */     List<PdfNamespace> namespacesList = new ArrayList<>(namespacesArray.size());
/* 205 */     for (int i = 0; i < namespacesArray.size(); i++) {
/* 206 */       namespacesList.add(new PdfNamespace(namespacesArray.getAsDictionary(i)));
/*     */     }
/* 208 */     return namespacesList;
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
/*     */   public void addNamespace(PdfNamespace namespace) {
/* 220 */     getNamespacesObject().add(namespace.getPdfObject());
/* 221 */     setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getNamespacesObject() {
/* 232 */     PdfArray namespacesArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Namespaces);
/* 233 */     if (namespacesArray == null) {
/* 234 */       namespacesArray = new PdfArray();
/* 235 */       VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.Namespaces, PdfName.StructTreeRoot);
/* 236 */       ((PdfDictionary)getPdfObject()).put(PdfName.Namespaces, (PdfObject)namespacesArray);
/* 237 */       setModified();
/*     */     } 
/* 239 */     return namespacesArray;
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
/*     */   public List<PdfFileSpec> getPronunciationLexiconsList() {
/* 254 */     PdfArray pronunciationLexicons = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.PronunciationLexicon);
/* 255 */     if (pronunciationLexicons == null) {
/* 256 */       return Collections.emptyList();
/*     */     }
/* 258 */     List<PdfFileSpec> lexiconsList = new ArrayList<>(pronunciationLexicons.size());
/* 259 */     for (int i = 0; i < pronunciationLexicons.size(); i++) {
/* 260 */       lexiconsList.add(PdfFileSpec.wrapFileSpecObject(pronunciationLexicons.get(i)));
/*     */     }
/* 262 */     return lexiconsList;
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
/*     */   public void addPronunciationLexicon(PdfFileSpec pronunciationLexiconFileSpec) {
/* 275 */     PdfArray pronunciationLexicons = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.PronunciationLexicon);
/* 276 */     if (pronunciationLexicons == null) {
/* 277 */       pronunciationLexicons = new PdfArray();
/* 278 */       VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.PronunciationLexicon, PdfName.StructTreeRoot);
/* 279 */       ((PdfDictionary)getPdfObject()).put(PdfName.PronunciationLexicon, (PdfObject)pronunciationLexicons);
/*     */     } 
/* 281 */     pronunciationLexicons.add(pronunciationLexiconFileSpec.getPdfObject());
/* 282 */     setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createParentTreeEntryForPage(PdfPage page) {
/* 292 */     getParentTreeHandler().createParentTreeEntryForPage(page);
/*     */   }
/*     */   
/*     */   public void savePageStructParentIndexIfNeeded(PdfPage page) {
/* 296 */     getParentTreeHandler().savePageStructParentIndexIfNeeded(page);
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
/*     */   public Collection<PdfMcr> getPageMarkedContentReferences(PdfPage page) {
/* 310 */     ParentTreeHandler.PageMcrsContainer pageMcrs = getParentTreeHandler().getPageMarkedContentReferences(page);
/* 311 */     return (pageMcrs != null) ? Collections.<PdfMcr>unmodifiableCollection(pageMcrs.getAllMcrsAsCollection()) : null;
/*     */   }
/*     */   
/*     */   public PdfMcr findMcrByMcid(PdfDictionary pageDict, int mcid) {
/* 315 */     return getParentTreeHandler().findMcrByMcid(pageDict, mcid);
/*     */   }
/*     */   
/*     */   public PdfObjRef findObjRefByStructParentIndex(PdfDictionary pageDict, int structParentIndex) {
/* 319 */     return getParentTreeHandler().findObjRefByStructParentIndex(pageDict, structParentIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getRole() {
/* 324 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/* 329 */     for (int i = 0; i < getDocument().getNumberOfPages(); i++) {
/* 330 */       createParentTreeEntryForPage(getDocument().getPage(i + 1));
/*     */     }
/* 332 */     ((PdfDictionary)getPdfObject()).put(PdfName.ParentTree, (PdfObject)getParentTreeHandler().buildParentTree());
/* 333 */     ((PdfDictionary)getPdfObject()).put(PdfName.ParentTreeNextKey, (PdfObject)new PdfNumber(getDocument().getNextStructParentIndex()));
/* 334 */     if (!getDocument().isAppendMode()) {
/* 335 */       flushAllKids(this);
/*     */     }
/* 337 */     super.flush();
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
/*     */   public void copyTo(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page) {
/* 350 */     StructureTreeCopier.copyTo(destDocument, page2page, getDocument());
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
/*     */   public void copyTo(PdfDocument destDocument, int insertBeforePage, Map<PdfPage, PdfPage> page2page) {
/* 364 */     StructureTreeCopier.copyTo(destDocument, insertBeforePage, page2page, getDocument());
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
/*     */   public void move(PdfPage fromPage, int insertBeforePage) {
/* 376 */     for (int i = 1; i <= getDocument().getNumberOfPages(); i++) {
/* 377 */       if (getDocument().getPage(i).isFlushed()) {
/* 378 */         throw new PdfException(MessageFormatUtil.format("Cannot move pages in partly flushed document. Page number {0} is already flushed.", new Object[] { Integer.valueOf(i) }));
/*     */       }
/*     */     } 
/* 381 */     StructureTreeCopier.move(getDocument(), fromPage, insertBeforePage);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getParentTreeNextKey() {
/* 386 */     return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.ParentTreeNextKey).intValue();
/*     */   }
/*     */   
/*     */   public int getNextMcidForPage(PdfPage page) {
/* 390 */     return getParentTreeHandler().getNextMcidForPage(page);
/*     */   }
/*     */   
/*     */   public PdfDocument getDocument() {
/* 394 */     return this.document;
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
/* 410 */     if (null == ((PdfDictionary)fs.getPdfObject()).get(PdfName.AFRelationship)) {
/* 411 */       Logger logger = LoggerFactory.getLogger(PdfStructTreeRoot.class);
/* 412 */       logger.error("For associated files their associated file specification dictionaries shall include the AFRelationship key.");
/*     */     } 
/* 414 */     if (null != description) {
/* 415 */       getDocument().getCatalog().getNameTree(PdfName.EmbeddedFiles).addEntry(description, fs.getPdfObject());
/*     */     }
/* 417 */     PdfArray afArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.AF);
/* 418 */     if (afArray == null) {
/* 419 */       afArray = new PdfArray();
/* 420 */       ((PdfDictionary)getPdfObject()).put(PdfName.AF, (PdfObject)afArray);
/*     */     } 
/* 422 */     afArray.add(fs.getPdfObject());
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
/* 437 */     addAssociatedFile((String)null, fs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getAssociatedFiles(boolean create) {
/* 447 */     PdfArray afArray = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.AF);
/* 448 */     if (afArray == null && create) {
/* 449 */       afArray = new PdfArray();
/* 450 */       ((PdfDictionary)getPdfObject()).put(PdfName.AF, (PdfObject)afArray);
/*     */     } 
/* 452 */     return afArray;
/*     */   }
/*     */   
/*     */   ParentTreeHandler getParentTreeHandler() {
/* 456 */     return this.parentTreeHandler;
/*     */   }
/*     */   
/*     */   void addKidObject(int index, PdfDictionary structElem) {
/* 460 */     if (index == -1) {
/* 461 */       getKidsObject().add((PdfObject)structElem);
/*     */     } else {
/* 463 */       getKidsObject().add(index, (PdfObject)structElem);
/*     */     } 
/* 465 */     if (PdfStructElem.isStructElem(structElem)) {
/* 466 */       if (((PdfDictionary)getPdfObject()).getIndirectReference() == null) {
/* 467 */         throw new PdfException("Structure element dictionary shall be an indirect object in order to have children.");
/*     */       }
/* 469 */       structElem.put(PdfName.P, getPdfObject());
/*     */     } 
/* 471 */     setModified();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 476 */     return true;
/*     */   }
/*     */   
/*     */   private void flushAllKids(IStructureNode elem) {
/* 480 */     for (IStructureNode kid : elem.getKids()) {
/* 481 */       if (kid instanceof PdfStructElem && !((PdfStructElem)kid).isFlushed()) {
/* 482 */         flushAllKids(kid);
/* 483 */         ((PdfStructElem)kid).flush();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void ifKidIsStructElementAddToList(PdfObject kid, List<IStructureNode> kids) {
/* 489 */     if (kid.isFlushed()) {
/* 490 */       kids.add(null);
/* 491 */     } else if (kid.isDictionary() && PdfStructElem.isStructElem((PdfDictionary)kid)) {
/* 492 */       kids.add(new PdfStructElem((PdfDictionary)kid));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagging/PdfStructTreeRoot.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */