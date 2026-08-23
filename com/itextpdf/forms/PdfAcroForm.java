/*      */ package com.itextpdf.forms;
/*      */ 
/*      */ import com.itextpdf.forms.fields.PdfFormField;
/*      */ import com.itextpdf.forms.xfa.XfaForm;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.geom.AffineTransform;
/*      */ import com.itextpdf.kernel.geom.Point;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*      */ import com.itextpdf.kernel.pdf.PdfPage;
/*      */ import com.itextpdf.kernel.pdf.PdfStream;
/*      */ import com.itextpdf.kernel.pdf.PdfString;
/*      */ import com.itextpdf.kernel.pdf.PdfVersion;
/*      */ import com.itextpdf.kernel.pdf.VersionConforming;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*      */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*      */ import com.itextpdf.kernel.pdf.tagutils.TagReference;
/*      */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PdfAcroForm
/*      */   extends PdfObjectWrapper<PdfDictionary>
/*      */ {
/*      */   public static final int SIGNATURE_EXIST = 1;
/*      */   public static final int APPEND_ONLY = 2;
/*      */   protected boolean generateAppearance = true;
/*  129 */   protected Map<String, PdfFormField> fields = new LinkedHashMap<>();
/*      */ 
/*      */   
/*      */   protected PdfDocument document;
/*      */ 
/*      */   
/*      */   private PdfDictionary defaultResources;
/*      */   
/*  137 */   private Set<PdfFormField> fieldsForFlattening = new LinkedHashSet<>();
/*      */   private XfaForm xfaForm;
/*  139 */   private static Logger logger = LoggerFactory.getLogger(PdfAcroForm.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PdfAcroForm(PdfDictionary pdfObject, PdfDocument pdfDocument) {
/*  149 */     super((PdfObject)pdfObject);
/*  150 */     this.document = pdfDocument;
/*  151 */     getFormFields();
/*  152 */     this.xfaForm = new XfaForm(pdfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PdfAcroForm(PdfArray fields) {
/*  162 */     this(createAcroFormDictionaryByFields(fields), (PdfDocument)null);
/*  163 */     setForbidRelease();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PdfAcroForm getAcroForm(PdfDocument document, boolean createIfNotExist) {
/*  177 */     PdfDictionary acroFormDictionary = ((PdfDictionary)document.getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm);
/*  178 */     PdfAcroForm acroForm = null;
/*  179 */     if (acroFormDictionary == null) {
/*  180 */       if (createIfNotExist) {
/*  181 */         acroForm = new PdfAcroForm(new PdfArray());
/*  182 */         acroForm.makeIndirect(document);
/*  183 */         document.getCatalog().put(PdfName.AcroForm, acroForm.getPdfObject());
/*  184 */         document.getCatalog().setModified();
/*      */       } 
/*      */     } else {
/*  187 */       acroForm = new PdfAcroForm(acroFormDictionary, document);
/*      */     } 
/*      */     
/*  190 */     if (acroForm != null) {
/*  191 */       acroForm.defaultResources = acroForm.getDefaultResources();
/*  192 */       if (acroForm.defaultResources == null) {
/*  193 */         acroForm.defaultResources = new PdfDictionary();
/*      */       }
/*  195 */       acroForm.document = document;
/*  196 */       acroForm.xfaForm = new XfaForm(document);
/*      */     } 
/*      */     
/*  199 */     return acroForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addField(PdfFormField field) {
/*  210 */     if (this.document.getNumberOfPages() == 0) {
/*  211 */       this.document.addNewPage();
/*      */     }
/*  213 */     PdfPage page = this.document.getLastPage();
/*  214 */     addField(field, page);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addField(PdfFormField field, PdfPage page) {
/*  224 */     PdfArray kids = field.getKids();
/*      */     
/*  226 */     PdfDictionary fieldDic = (PdfDictionary)field.getPdfObject();
/*  227 */     if (kids != null) {
/*  228 */       processKids(kids, fieldDic, page);
/*      */     }
/*      */     
/*  231 */     PdfArray fieldsArray = getFields();
/*  232 */     fieldsArray.add((PdfObject)fieldDic);
/*  233 */     fieldsArray.setModified();
/*      */     
/*  235 */     this.fields.put(field.getFieldName().toUnicodeString(), field);
/*  236 */     if (field.getKids() != null) {
/*  237 */       iterateFields(field.getKids(), this.fields);
/*      */     }
/*      */     
/*  240 */     if (fieldDic.containsKey(PdfName.Subtype) && page != null) {
/*  241 */       PdfAnnotation annot = PdfAnnotation.makeAnnotation((PdfObject)fieldDic);
/*  242 */       addWidgetAnnotationToPage(page, annot);
/*      */     } 
/*      */     
/*  245 */     setModified();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFieldAppearanceToPage(PdfFormField field, PdfPage page) {
/*  257 */     PdfDictionary fieldDict = (PdfDictionary)field.getPdfObject();
/*  258 */     PdfArray kids = field.getKids();
/*  259 */     if (kids == null || kids.size() > 1) {
/*      */       return;
/*      */     }
/*      */     
/*  263 */     PdfDictionary kidDict = (PdfDictionary)kids.get(0);
/*  264 */     PdfName type = kidDict.getAsName(PdfName.Subtype);
/*  265 */     if (type != null && type.equals(PdfName.Widget)) {
/*  266 */       if (!kidDict.containsKey(PdfName.FT))
/*      */       {
/*      */         
/*  269 */         mergeWidgetWithParentField(fieldDict, kidDict);
/*      */       }
/*  271 */       defineWidgetPageAndAddToIt(page, fieldDict, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, PdfFormField> getFormFields() {
/*  281 */     if (this.fields.size() == 0) {
/*  282 */       this.fields = iterateFields(getFields());
/*      */     }
/*  284 */     return this.fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<PdfFormField> getFieldsForFlattening() {
/*  294 */     return Collections.unmodifiableCollection(this.fieldsForFlattening);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument getPdfDocument() {
/*  303 */     return this.document;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setNeedAppearances(boolean needAppearances) {
/*  321 */     if (VersionConforming.validatePdfVersionForDeprecatedFeatureLogError(this.document, PdfVersion.PDF_2_0, "NeedAppearances has been deprecated in PDF 2.0. Appearance streams are required in PDF 2.0.")) {
/*  322 */       ((PdfDictionary)getPdfObject()).remove(PdfName.NeedAppearances);
/*  323 */       setModified();
/*      */     } else {
/*  325 */       put(PdfName.NeedAppearances, (PdfObject)PdfBoolean.valueOf(needAppearances));
/*      */     } 
/*  327 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfBoolean getNeedAppearances() {
/*  344 */     return ((PdfDictionary)getPdfObject()).getAsBoolean(PdfName.NeedAppearances);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setSignatureFlags(int sigFlags) {
/*  361 */     return put(PdfName.SigFlags, (PdfObject)new PdfNumber(sigFlags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setSignatureFlag(int sigFlag) {
/*  379 */     int flags = getSignatureFlags();
/*  380 */     flags |= sigFlag;
/*      */     
/*  382 */     return setSignatureFlags(flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSignatureFlags() {
/*  397 */     PdfNumber f = ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.SigFlags);
/*  398 */     if (f != null) {
/*  399 */       return f.intValue();
/*      */     }
/*  401 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setCalculationOrder(PdfArray calculationOrder) {
/*  419 */     return put(PdfName.CO, (PdfObject)calculationOrder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getCalculationOrder() {
/*  436 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.CO);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setDefaultResources(PdfDictionary defaultResources) {
/*  455 */     return put(PdfName.DR, (PdfObject)defaultResources);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getDefaultResources() {
/*  473 */     return ((PdfDictionary)getPdfObject()).getAsDictionary(PdfName.DR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setDefaultAppearance(String appearance) {
/*  487 */     return put(PdfName.DA, (PdfObject)new PdfString(appearance));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getDefaultAppearance() {
/*  499 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.DA);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setDefaultJustification(int justification) {
/*  513 */     return put(PdfName.Q, (PdfObject)new PdfNumber(justification));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfNumber getDefaultJustification() {
/*  526 */     return ((PdfDictionary)getPdfObject()).getAsNumber(PdfName.Q);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setXFAResource(PdfStream xfaResource) {
/*  539 */     return put(PdfName.XFA, (PdfObject)xfaResource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfAcroForm setXFAResource(PdfArray xfaResource) {
/*  554 */     return put(PdfName.XFA, (PdfObject)xfaResource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfObject getXFAResource() {
/*  564 */     return ((PdfDictionary)getPdfObject()).get(PdfName.XFA);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField getField(String fieldName) {
/*  575 */     return this.fields.get(fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isGenerateAppearance() {
/*  586 */     return this.generateAppearance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGenerateAppearance(boolean generateAppearance) {
/*  607 */     if (generateAppearance) {
/*  608 */       ((PdfDictionary)getPdfObject()).remove(PdfName.NeedAppearances);
/*  609 */       setModified();
/*      */     } 
/*  611 */     this.generateAppearance = generateAppearance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flattenFields() {
/*      */     Set<PdfFormField> fields;
/*  621 */     if (this.document.isAppendMode()) {
/*  622 */       throw new PdfException("Field flattening is not supported in append mode.");
/*      */     }
/*      */     
/*  625 */     if (this.fieldsForFlattening.size() == 0) {
/*  626 */       this.fields.clear();
/*  627 */       fields = new LinkedHashSet<>(getFormFields().values());
/*      */     } else {
/*  629 */       fields = new LinkedHashSet<>();
/*  630 */       for (PdfFormField field : this.fieldsForFlattening) {
/*  631 */         fields.addAll(prepareFieldsForFlattening(field));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  640 */     Map<Integer, PdfObject> initialPageResourceClones = new LinkedHashMap<>();
/*  641 */     for (int i = 1; i <= this.document.getNumberOfPages(); i++) {
/*  642 */       PdfDictionary pdfDictionary = ((PdfDictionary)this.document.getPage(i).getPdfObject()).getAsDictionary(PdfName.Resources);
/*  643 */       initialPageResourceClones.put(Integer.valueOf(i), (pdfDictionary == null) ? null : pdfDictionary.clone());
/*      */     } 
/*      */     
/*  646 */     Set<PdfPage> wrappedPages = new LinkedHashSet<>();
/*      */     
/*  648 */     for (PdfFormField field : fields) {
/*  649 */       PdfDictionary pdfDictionary1, fieldObject = (PdfDictionary)field.getPdfObject();
/*  650 */       PdfPage page = getFieldPage(fieldObject);
/*  651 */       if (page == null) {
/*      */         continue;
/*      */       }
/*      */       
/*  655 */       PdfAnnotation annotation = PdfAnnotation.makeAnnotation((PdfObject)fieldObject);
/*  656 */       TagTreePointer tagPointer = null;
/*  657 */       if (annotation != null && this.document.isTagged()) {
/*  658 */         tagPointer = this.document.getTagStructureContext().removeAnnotationTag(annotation);
/*      */       }
/*      */       
/*  661 */       PdfDictionary appDic = fieldObject.getAsDictionary(PdfName.AP);
/*  662 */       PdfObject asNormal = null;
/*  663 */       if (appDic != null) {
/*  664 */         PdfStream pdfStream = appDic.getAsStream(PdfName.N);
/*  665 */         if (pdfStream == null) {
/*  666 */           pdfDictionary1 = appDic.getAsDictionary(PdfName.N);
/*      */         }
/*      */       } 
/*  669 */       if (this.generateAppearance && (
/*  670 */         appDic == null || pdfDictionary1 == null)) {
/*  671 */         field.regenerateField();
/*  672 */         appDic = fieldObject.getAsDictionary(PdfName.AP);
/*      */       } 
/*      */       
/*  675 */       PdfObject normal = (appDic != null) ? appDic.get(PdfName.N) : null;
/*  676 */       if (null != normal) {
/*  677 */         PdfFormXObject xObject = null;
/*  678 */         if (normal.isStream()) {
/*  679 */           xObject = new PdfFormXObject((PdfStream)normal);
/*  680 */         } else if (normal.isDictionary()) {
/*  681 */           PdfName as = fieldObject.getAsName(PdfName.AS);
/*  682 */           if (((PdfDictionary)normal).getAsStream(as) != null) {
/*  683 */             xObject = new PdfFormXObject(((PdfDictionary)normal).getAsStream(as));
/*  684 */             xObject.makeIndirect(this.document);
/*      */           } 
/*      */         } 
/*      */         
/*  688 */         if (xObject != null) {
/*      */           
/*  690 */           xObject.put(PdfName.Subtype, (PdfObject)PdfName.Form);
/*  691 */           Rectangle annotBBox = fieldObject.getAsRectangle(PdfName.Rect);
/*  692 */           if (page.isFlushed()) {
/*  693 */             throw new PdfException("The page has been already flushed. Use PdfAcroForm#addFieldAppearanceToPage() method before page flushing.");
/*      */           }
/*  695 */           PdfCanvas canvas = new PdfCanvas(page, !wrappedPages.contains(page));
/*  696 */           wrappedPages.add(page);
/*      */ 
/*      */ 
/*      */           
/*  700 */           PdfObject xObjectResources = ((PdfStream)xObject.getPdfObject()).get(PdfName.Resources);
/*  701 */           PdfObject pageResources = page.getResources().getPdfObject();
/*  702 */           if (xObjectResources != null && xObjectResources == pageResources) {
/*  703 */             ((PdfStream)xObject.getPdfObject()).put(PdfName.Resources, initialPageResourceClones.get(Integer.valueOf(this.document.getPageNumber(page))));
/*      */           }
/*      */           
/*  706 */           if (tagPointer != null) {
/*  707 */             tagPointer.setPageForTagging(page);
/*  708 */             TagReference tagRef = tagPointer.getTagReference();
/*  709 */             canvas.openTag(tagRef);
/*      */           } 
/*      */           
/*  712 */           AffineTransform at = calcFieldAppTransformToAnnotRect(xObject, annotBBox);
/*  713 */           float[] m = new float[6];
/*  714 */           at.getMatrix(m);
/*  715 */           canvas.addXObject((PdfXObject)xObject, m[0], m[1], m[2], m[3], m[4], m[5]);
/*      */           
/*  717 */           if (tagPointer != null) {
/*  718 */             canvas.closeTag();
/*      */           }
/*      */         } 
/*      */       } else {
/*  722 */         logger.error("\\N entry is required to be present in an appearance dictionary.");
/*      */       } 
/*      */       
/*  725 */       PdfArray fFields = getFields();
/*  726 */       fFields.remove((PdfObject)fieldObject);
/*  727 */       if (annotation != null) {
/*  728 */         page.removeAnnotation(annotation);
/*      */       }
/*  730 */       PdfDictionary parent = fieldObject.getAsDictionary(PdfName.Parent);
/*  731 */       if (parent != null) {
/*  732 */         PdfArray kids = parent.getAsArray(PdfName.Kids);
/*  733 */         if (kids != null) {
/*  734 */           kids.remove((PdfObject)fieldObject);
/*      */           
/*  736 */           if (kids.isEmpty())
/*  737 */             fFields.remove((PdfObject)parent); 
/*      */           continue;
/*      */         } 
/*  740 */         fFields.remove((PdfObject)parent);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  745 */     ((PdfDictionary)getPdfObject()).remove(PdfName.NeedAppearances);
/*  746 */     if (this.fieldsForFlattening.size() == 0) {
/*  747 */       getFields().clear();
/*      */     }
/*  749 */     if (getFields().isEmpty()) {
/*  750 */       this.document.getCatalog().remove(PdfName.AcroForm);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeField(String fieldName) {
/*  762 */     PdfFormField field = getField(fieldName);
/*  763 */     if (field == null) {
/*  764 */       return false;
/*      */     }
/*      */     
/*  767 */     PdfDictionary fieldObject = (PdfDictionary)field.getPdfObject();
/*  768 */     PdfPage page = getFieldPage(fieldObject);
/*      */     
/*  770 */     PdfAnnotation annotation = PdfAnnotation.makeAnnotation((PdfObject)fieldObject);
/*  771 */     if (page != null && annotation != null) {
/*  772 */       page.removeAnnotation(annotation);
/*      */     }
/*      */     
/*  775 */     PdfDictionary parent = field.getParent();
/*  776 */     if (parent != null) {
/*  777 */       PdfArray kids = parent.getAsArray(PdfName.Kids);
/*  778 */       kids.remove((PdfObject)fieldObject);
/*  779 */       this.fields.remove(fieldName);
/*  780 */       kids.setModified();
/*  781 */       parent.setModified();
/*  782 */       return true;
/*      */     } 
/*      */     
/*  785 */     PdfArray fieldsPdfArray = getFields();
/*  786 */     if (fieldsPdfArray.contains((PdfObject)fieldObject)) {
/*  787 */       fieldsPdfArray.remove((PdfObject)fieldObject);
/*  788 */       this.fields.remove(fieldName);
/*  789 */       fieldsPdfArray.setModified();
/*  790 */       setModified();
/*  791 */       return true;
/*      */     } 
/*  793 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void partialFormFlattening(String fieldName) {
/*  803 */     PdfFormField field = getFormFields().get(fieldName);
/*  804 */     if (field != null) {
/*  805 */       this.fieldsForFlattening.add(field);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renameField(String oldName, String newName) {
/*  816 */     Map<String, PdfFormField> fields = getFormFields();
/*  817 */     if (fields.containsKey(newName)) {
/*      */       return;
/*      */     }
/*  820 */     PdfFormField field = fields.get(oldName);
/*  821 */     if (field != null) {
/*  822 */       field.setFieldName(newName);
/*  823 */       fields.remove(oldName);
/*  824 */       fields.put(newName, field);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFormField copyField(String name) {
/*  836 */     PdfFormField oldField = getField(name);
/*  837 */     if (oldField != null) {
/*  838 */       return new PdfFormField((PdfDictionary)((PdfDictionary)oldField.getPdfObject()).clone().makeIndirect(this.document));
/*      */     }
/*  840 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceField(String name, PdfFormField field) {
/*  851 */     removeField(name);
/*  852 */     addField(field);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfArray getFields() {
/*  861 */     PdfArray fields = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Fields);
/*  862 */     if (fields == null) {
/*  863 */       logger.warn("Required AcroForm entry /Fields does not exist in the document. Empty array /Fields will be created.");
/*  864 */       fields = new PdfArray();
/*  865 */       ((PdfDictionary)getPdfObject()).put(PdfName.Fields, (PdfObject)fields);
/*      */     } 
/*  867 */     return fields;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isWrappedObjectMustBeIndirect() {
/*  872 */     return false;
/*      */   }
/*      */   
/*      */   private Map<String, PdfFormField> iterateFields(PdfArray array, Map<String, PdfFormField> fields) {
/*  876 */     int index = 1;
/*  877 */     for (PdfObject field : array) {
/*  878 */       String name; if (field.isFlushed()) {
/*  879 */         logger.info("A form field was flushed. There's no way to create this field in the AcroForm dictionary.");
/*      */         continue;
/*      */       } 
/*  882 */       PdfFormField formField = PdfFormField.makeFormField(field, this.document);
/*  883 */       if (formField == null) {
/*  884 */         logger.warn(MessageFormatUtil.format("Cannot create form field from a given PDF object: {0}", new Object[] {
/*  885 */                 (field.getIndirectReference() == null) ? field : field.getIndirectReference() }));
/*      */         continue;
/*      */       } 
/*  888 */       PdfString fieldName = formField.getFieldName();
/*      */       
/*  890 */       if (fieldName == null) {
/*  891 */         PdfFormField parentField = PdfFormField.makeFormField((PdfObject)formField.getParent(), this.document);
/*  892 */         while (fieldName == null) {
/*  893 */           fieldName = parentField.getFieldName();
/*  894 */           if (fieldName == null) {
/*  895 */             parentField = PdfFormField.makeFormField((PdfObject)parentField.getParent(), this.document);
/*      */           }
/*      */         } 
/*  898 */         name = fieldName.toUnicodeString() + "." + index;
/*  899 */         index++;
/*      */       } else {
/*  901 */         name = fieldName.toUnicodeString();
/*      */       } 
/*  903 */       fields.put(name, formField);
/*  904 */       if (formField.getKids() != null) {
/*  905 */         iterateFields(formField.getKids(), fields);
/*      */       }
/*      */     } 
/*      */     
/*  909 */     return fields;
/*      */   }
/*      */   
/*      */   private Map<String, PdfFormField> iterateFields(PdfArray array) {
/*  913 */     return iterateFields(array, new LinkedHashMap<>());
/*      */   }
/*      */   
/*      */   private PdfDictionary processKids(PdfArray kids, PdfDictionary parent, PdfPage page) {
/*  917 */     if (kids.size() == 1) {
/*  918 */       PdfDictionary kidDict = (PdfDictionary)kids.get(0);
/*  919 */       PdfName type = kidDict.getAsName(PdfName.Subtype);
/*  920 */       if (type != null && type.equals(PdfName.Widget)) {
/*  921 */         if (!kidDict.containsKey(PdfName.FT)) {
/*      */ 
/*      */           
/*  924 */           mergeWidgetWithParentField(parent, kidDict);
/*  925 */           defineWidgetPageAndAddToIt(page, parent, true);
/*      */         } else {
/*  927 */           defineWidgetPageAndAddToIt(page, kidDict, true);
/*      */         } 
/*      */       } else {
/*  930 */         PdfArray otherKids = kidDict.getAsArray(PdfName.Kids);
/*  931 */         if (otherKids != null) {
/*  932 */           processKids(otherKids, kidDict, page);
/*      */         }
/*      */       } 
/*      */     } else {
/*  936 */       for (int i = 0; i < kids.size(); i++) {
/*  937 */         PdfObject kid = kids.get(i);
/*  938 */         PdfArray otherKids = ((PdfDictionary)kid).getAsArray(PdfName.Kids);
/*  939 */         if (otherKids != null) {
/*  940 */           processKids(otherKids, (PdfDictionary)kid, page);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  945 */     return parent;
/*      */   }
/*      */   
/*      */   private void mergeWidgetWithParentField(PdfDictionary parent, PdfDictionary widgetDict) {
/*  949 */     parent.remove(PdfName.Kids);
/*  950 */     widgetDict.remove(PdfName.Parent);
/*  951 */     parent.mergeDifferent(widgetDict);
/*      */   }
/*      */   
/*      */   private void defineWidgetPageAndAddToIt(PdfPage currentPage, PdfDictionary mergedFieldAndWidget, boolean warnIfPageFlushed) {
/*  955 */     PdfAnnotation annot = PdfAnnotation.makeAnnotation((PdfObject)mergedFieldAndWidget);
/*  956 */     PdfDictionary pageDic = annot.getPageObject();
/*  957 */     if (pageDic != null) {
/*  958 */       if (warnIfPageFlushed && pageDic.isFlushed()) {
/*  959 */         throw new PdfException("The page has been already flushed. Use PdfAcroForm#addFieldAppearanceToPage() method before page flushing.");
/*      */       }
/*  961 */       PdfDocument doc = pageDic.getIndirectReference().getDocument();
/*  962 */       PdfPage widgetPage = doc.getPage(pageDic);
/*  963 */       addWidgetAnnotationToPage(widgetPage, annot);
/*      */     } else {
/*  965 */       addWidgetAnnotationToPage(currentPage, annot);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addWidgetAnnotationToPage(PdfPage page, PdfAnnotation annot) {
/*  970 */     if (page.containsAnnotation(annot)) {
/*      */       return;
/*      */     }
/*      */     
/*  974 */     TagTreePointer tagPointer = null;
/*  975 */     boolean tagged = page.getDocument().isTagged();
/*  976 */     if (tagged) {
/*  977 */       tagPointer = page.getDocument().getTagStructureContext().getAutoTaggingPointer();
/*      */       
/*  979 */       tagPointer.addTag("Form");
/*      */     } 
/*      */     
/*  982 */     page.addAnnotation(annot);
/*      */     
/*  984 */     if (tagged) {
/*  985 */       tagPointer.moveToParent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasXfaForm() {
/*  995 */     return (this.xfaForm != null && this.xfaForm.isXfaPresent());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XfaForm getXfaForm() {
/* 1004 */     return this.xfaForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeXfaForm() {
/* 1011 */     if (hasXfaForm()) {
/* 1012 */       PdfDictionary root = (PdfDictionary)this.document.getCatalog().getPdfObject();
/* 1013 */       PdfDictionary acroform = root.getAsDictionary(PdfName.AcroForm);
/* 1014 */       acroform.remove(PdfName.XFA);
/* 1015 */       this.xfaForm = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PdfAcroForm put(PdfName key, PdfObject value) {
/* 1020 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 1021 */     setModified();
/* 1022 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void release() {
/* 1030 */     unsetForbidRelease();
/* 1031 */     ((PdfDictionary)getPdfObject()).release();
/* 1032 */     for (PdfFormField field : this.fields.values()) {
/* 1033 */       field.release();
/*      */     }
/* 1035 */     this.fields = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public PdfObjectWrapper<PdfDictionary> setModified() {
/* 1040 */     if (((PdfDictionary)getPdfObject()).getIndirectReference() != null) {
/* 1041 */       super.setModified();
/*      */     } else {
/* 1043 */       this.document.getCatalog().setModified();
/*      */     } 
/* 1045 */     return this;
/*      */   }
/*      */   
/*      */   private static PdfDictionary createAcroFormDictionaryByFields(PdfArray fields) {
/* 1049 */     PdfDictionary dictionary = new PdfDictionary();
/* 1050 */     dictionary.put(PdfName.Fields, (PdfObject)fields);
/* 1051 */     return dictionary;
/*      */   }
/*      */   
/*      */   private PdfPage getFieldPage(PdfDictionary annotDic) {
/* 1055 */     PdfDictionary pageDic = annotDic.getAsDictionary(PdfName.P);
/* 1056 */     if (pageDic != null) {
/* 1057 */       return this.document.getPage(pageDic);
/*      */     }
/* 1059 */     for (int i = 1; i <= this.document.getNumberOfPages(); i++) {
/* 1060 */       PdfPage page = this.document.getPage(i);
/* 1061 */       if (!page.isFlushed()) {
/* 1062 */         PdfAnnotation annotation = PdfAnnotation.makeAnnotation((PdfObject)annotDic);
/* 1063 */         if (annotation != null && page.containsAnnotation(annotation)) {
/* 1064 */           return page;
/*      */         }
/*      */       } 
/*      */     } 
/* 1068 */     return null;
/*      */   }
/*      */   
/*      */   private Set<PdfFormField> prepareFieldsForFlattening(PdfFormField field) {
/* 1072 */     Set<PdfFormField> preparedFields = new LinkedHashSet<>();
/* 1073 */     preparedFields.add(field);
/* 1074 */     PdfArray kids = field.getKids();
/* 1075 */     if (kids != null) {
/* 1076 */       for (PdfObject kid : kids) {
/* 1077 */         PdfFormField kidField = new PdfFormField((PdfDictionary)kid);
/* 1078 */         preparedFields.add(kidField);
/* 1079 */         if (kidField.getKids() != null) {
/* 1080 */           preparedFields.addAll(prepareFieldsForFlattening(kidField));
/*      */         }
/*      */       } 
/*      */     }
/* 1084 */     return preparedFields;
/*      */   }
/*      */   private AffineTransform calcFieldAppTransformToAnnotRect(PdfFormXObject xObject, Rectangle annotBBox) {
/*      */     Rectangle transformedRect;
/* 1088 */     PdfArray bBox = xObject.getBBox();
/* 1089 */     if (bBox.size() != 4) {
/* 1090 */       bBox = new PdfArray(new Rectangle(0.0F, 0.0F));
/* 1091 */       xObject.setBBox(bBox);
/*      */     } 
/* 1093 */     float[] xObjBBox = bBox.toFloatArray();
/*      */     
/* 1095 */     PdfArray xObjMatrix = ((PdfStream)xObject.getPdfObject()).getAsArray(PdfName.Matrix);
/*      */     
/* 1097 */     if (xObjMatrix != null && xObjMatrix.size() == 6) {
/* 1098 */       Point[] xObjRectPoints = { new Point(xObjBBox[0], xObjBBox[1]), new Point(xObjBBox[0], xObjBBox[3]), new Point(xObjBBox[2], xObjBBox[1]), new Point(xObjBBox[2], xObjBBox[3]) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1104 */       Point[] transformedAppBoxPoints = new Point[xObjRectPoints.length];
/* 1105 */       (new AffineTransform(xObjMatrix.toDoubleArray())).transform(xObjRectPoints, 0, transformedAppBoxPoints, 0, xObjRectPoints.length);
/*      */       
/* 1107 */       float[] transformedRectArr = { Float.MAX_VALUE, Float.MAX_VALUE, -3.4028235E38F, -3.4028235E38F };
/*      */ 
/*      */ 
/*      */       
/* 1111 */       for (Point p : transformedAppBoxPoints) {
/* 1112 */         transformedRectArr[0] = (float)Math.min(transformedRectArr[0], p.x);
/* 1113 */         transformedRectArr[1] = (float)Math.min(transformedRectArr[1], p.y);
/* 1114 */         transformedRectArr[2] = (float)Math.max(transformedRectArr[2], p.x);
/* 1115 */         transformedRectArr[3] = (float)Math.max(transformedRectArr[3], p.y);
/*      */       } 
/*      */       
/* 1118 */       transformedRect = new Rectangle(transformedRectArr[0], transformedRectArr[1], transformedRectArr[2] - transformedRectArr[0], transformedRectArr[3] - transformedRectArr[1]);
/*      */     } else {
/* 1120 */       transformedRect = (new Rectangle(0.0F, 0.0F)).setBbox(xObjBBox[0], xObjBBox[1], xObjBBox[2], xObjBBox[3]);
/*      */     } 
/*      */     
/* 1123 */     AffineTransform at = AffineTransform.getTranslateInstance(-transformedRect.getX(), -transformedRect.getY());
/* 1124 */     float scaleX = (transformedRect.getWidth() == 0.0F) ? 1.0F : (annotBBox.getWidth() / transformedRect.getWidth());
/* 1125 */     float scaleY = (transformedRect.getHeight() == 0.0F) ? 1.0F : (annotBBox.getHeight() / transformedRect.getHeight());
/* 1126 */     at.preConcatenate(AffineTransform.getScaleInstance(scaleX, scaleY));
/* 1127 */     at.preConcatenate(AffineTransform.getTranslateInstance(annotBBox.getX(), annotBBox.getY()));
/*      */     
/* 1129 */     return at;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/PdfAcroForm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */