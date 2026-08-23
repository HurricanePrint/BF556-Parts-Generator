/*      */ package com.itextpdf.kernel.pdf;
/*      */ 
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.events.Event;
/*      */ import com.itextpdf.kernel.events.PdfDocumentEvent;
/*      */ import com.itextpdf.kernel.geom.PageSize;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.action.PdfAction;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
/*      */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
/*      */ import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
/*      */ import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*      */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*      */ import com.itextpdf.kernel.xmp.XMPException;
/*      */ import com.itextpdf.kernel.xmp.XMPMeta;
/*      */ import com.itextpdf.kernel.xmp.XMPMetaFactory;
/*      */ import com.itextpdf.kernel.xmp.options.SerializeOptions;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
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
/*      */ public class PdfPage
/*      */   extends PdfObjectWrapper<PdfDictionary>
/*      */ {
/*      */   private static final long serialVersionUID = -952395541908379500L;
/*   76 */   private PdfResources resources = null;
/*   77 */   private int mcid = -1;
/*      */   PdfPages parentPages;
/*   79 */   private static final List<PdfName> PAGE_EXCLUDED_KEYS = new ArrayList<>(Arrays.asList(new PdfName[] { PdfName.Parent, PdfName.Annots, PdfName.StructParents, PdfName.B }));
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
/*   90 */   private static final List<PdfName> XOBJECT_EXCLUDED_KEYS = new ArrayList<>(Arrays.asList(new PdfName[] { PdfName.MediaBox, PdfName.CropBox, PdfName.TrimBox, PdfName.Contents }));
/*      */ 
/*      */   
/*      */   static {
/*   94 */     XOBJECT_EXCLUDED_KEYS.addAll(PAGE_EXCLUDED_KEYS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignorePageRotationForContent = false;
/*      */ 
/*      */   
/*      */   private boolean pageRotationInverseMatrixWritten = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected PdfPage(PdfDictionary pdfObject) {
/*  108 */     super(pdfObject);
/*  109 */     setForbidRelease();
/*  110 */     ensureObjectIsAddedToDocument(pdfObject);
/*      */   }
/*      */   
/*      */   protected PdfPage(PdfDocument pdfDocument, PageSize pageSize) {
/*  114 */     this((PdfDictionary)(new PdfDictionary()).makeIndirect(pdfDocument));
/*  115 */     PdfStream contentStream = (PdfStream)(new PdfStream()).makeIndirect(pdfDocument);
/*  116 */     getPdfObject().put(PdfName.Contents, contentStream);
/*  117 */     getPdfObject().put(PdfName.Type, PdfName.Page);
/*  118 */     getPdfObject().put(PdfName.MediaBox, new PdfArray((Rectangle)pageSize));
/*  119 */     getPdfObject().put(PdfName.TrimBox, new PdfArray((Rectangle)pageSize));
/*  120 */     if (pdfDocument.isTagged()) {
/*  121 */       setTabOrder(PdfName.S);
/*      */     }
/*      */   }
/*      */   
/*      */   protected PdfPage(PdfDocument pdfDocument) {
/*  126 */     this(pdfDocument, pdfDocument.getDefaultPageSize());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getPageSize() {
/*  135 */     return getMediaBox();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getPageSizeWithRotation() {
/*  144 */     PageSize rect = new PageSize(getPageSize());
/*  145 */     int rotation = getRotation();
/*  146 */     while (rotation > 0) {
/*  147 */       rect = rect.rotate();
/*  148 */       rotation -= 90;
/*      */     } 
/*  150 */     return (Rectangle)rect;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRotation() {
/*  160 */     PdfNumber rotate = getPdfObject().getAsNumber(PdfName.Rotate);
/*  161 */     int rotateValue = 0;
/*  162 */     if (rotate == null) {
/*  163 */       rotate = (PdfNumber)getInheritedValue(PdfName.Rotate, 8);
/*      */     }
/*  165 */     if (rotate != null) {
/*  166 */       rotateValue = rotate.intValue();
/*      */     }
/*  168 */     rotateValue %= 360;
/*  169 */     return (rotateValue < 0) ? (rotateValue + 360) : rotateValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage setRotation(int degAngle) {
/*  180 */     put(PdfName.Rotate, new PdfNumber(degAngle));
/*  181 */     return this;
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
/*      */   public PdfStream getContentStream(int index) {
/*  194 */     int count = getContentStreamCount();
/*  195 */     if (index >= count || index < 0)
/*  196 */       throw new IndexOutOfBoundsException(MessageFormatUtil.format("Index: {0}, Size: {1}", new Object[] { Integer.valueOf(index), Integer.valueOf(count) })); 
/*  197 */     PdfObject contents = getPdfObject().get(PdfName.Contents);
/*  198 */     if (contents instanceof PdfStream)
/*  199 */       return (PdfStream)contents; 
/*  200 */     if (contents instanceof PdfArray) {
/*  201 */       PdfArray a = (PdfArray)contents;
/*  202 */       return a.getAsStream(index);
/*      */     } 
/*  204 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getContentStreamCount() {
/*  215 */     PdfObject contents = getPdfObject().get(PdfName.Contents);
/*  216 */     if (contents instanceof PdfStream)
/*  217 */       return 1; 
/*  218 */     if (contents instanceof PdfArray) {
/*  219 */       return ((PdfArray)contents).size();
/*      */     }
/*  221 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfStream getFirstContentStream() {
/*  231 */     if (getContentStreamCount() > 0)
/*  232 */       return getContentStream(0); 
/*  233 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfStream getLastContentStream() {
/*  242 */     int count = getContentStreamCount();
/*  243 */     if (count > 0)
/*  244 */       return getContentStream(count - 1); 
/*  245 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfStream newContentStreamBefore() {
/*  255 */     return newContentStream(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfStream newContentStreamAfter() {
/*  265 */     return newContentStream(false);
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
/*      */   public PdfResources getResources() {
/*  282 */     return getResources(true);
/*      */   }
/*      */   
/*      */   PdfResources getResources(boolean initResourcesField) {
/*  286 */     if (this.resources == null && initResourcesField) {
/*  287 */       initResources(true);
/*      */     }
/*  289 */     return this.resources;
/*      */   }
/*      */   
/*      */   PdfDictionary initResources(boolean initResourcesField) {
/*  293 */     boolean readOnly = false;
/*  294 */     PdfDictionary resources = getPdfObject().getAsDictionary(PdfName.Resources);
/*  295 */     if (resources == null) {
/*  296 */       resources = (PdfDictionary)getInheritedValue(PdfName.Resources, 3);
/*  297 */       if (resources != null) {
/*  298 */         readOnly = true;
/*      */       }
/*      */     } 
/*  301 */     if (resources == null) {
/*  302 */       resources = new PdfDictionary();
/*      */       
/*  304 */       getPdfObject().put(PdfName.Resources, resources);
/*      */     } 
/*  306 */     if (initResourcesField) {
/*  307 */       this.resources = new PdfResources(resources);
/*  308 */       this.resources.setReadOnly(readOnly);
/*      */     } 
/*  310 */     return resources;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage setResources(PdfResources pdfResources) {
/*  320 */     put(PdfName.Resources, pdfResources.getPdfObject());
/*  321 */     this.resources = pdfResources;
/*  322 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage setXmpMetadata(byte[] xmpMetadata) throws IOException {
/*  333 */     PdfStream xmp = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/*  334 */     xmp.getOutputStream().write(xmpMetadata);
/*  335 */     xmp.put(PdfName.Type, PdfName.Metadata);
/*  336 */     xmp.put(PdfName.Subtype, PdfName.XML);
/*  337 */     put(PdfName.Metadata, xmp);
/*  338 */     return this;
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
/*      */   public PdfPage setXmpMetadata(XMPMeta xmpMeta, SerializeOptions serializeOptions) throws XMPException, IOException {
/*  351 */     return setXmpMetadata(XMPMetaFactory.serializeToBuffer(xmpMeta, serializeOptions));
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
/*      */   public PdfPage setXmpMetadata(XMPMeta xmpMeta) throws XMPException, IOException {
/*  363 */     SerializeOptions serializeOptions = new SerializeOptions();
/*  364 */     serializeOptions.setPadding(2000);
/*  365 */     return setXmpMetadata(xmpMeta, serializeOptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfStream getXmpMetadata() {
/*  374 */     return getPdfObject().getAsStream(PdfName.Metadata);
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
/*      */   public PdfPage copyTo(PdfDocument toDocument) {
/*  386 */     return copyTo(toDocument, (IPdfPageExtraCopier)null);
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
/*      */   public PdfPage copyTo(PdfDocument toDocument, IPdfPageExtraCopier copier) {
/*  401 */     PdfDictionary dictionary = getPdfObject().copyTo(toDocument, PAGE_EXCLUDED_KEYS, true);
/*  402 */     PdfPage page = getDocument().getPageFactory().createPdfPage(dictionary);
/*  403 */     copyInheritedProperties(page, toDocument);
/*  404 */     for (PdfAnnotation annot : getAnnotations()) {
/*  405 */       if (annot.getSubtype().equals(PdfName.Link)) {
/*  406 */         getDocument().storeLinkAnnotation(page, (PdfLinkAnnotation)annot); continue;
/*      */       } 
/*  408 */       PdfAnnotation newAnnot = PdfAnnotation.makeAnnotation(((PdfDictionary)annot
/*  409 */           .getPdfObject()).copyTo(toDocument, Arrays.asList(new PdfName[] { PdfName.P, PdfName.Parent }, ), true));
/*      */       
/*  411 */       if (PdfName.Widget.equals(annot.getSubtype())) {
/*  412 */         rebuildFormFieldParent((PdfDictionary)annot.getPdfObject(), (PdfDictionary)newAnnot.getPdfObject(), toDocument);
/*      */       }
/*      */ 
/*      */       
/*  416 */       page.addAnnotation(-1, newAnnot, false);
/*      */     } 
/*      */ 
/*      */     
/*  420 */     if (copier != null) {
/*  421 */       copier.copy(this, page);
/*      */     }
/*  423 */     else if (!(toDocument.getWriter()).isUserWarnedAboutAcroFormCopying && getDocument().hasAcroForm()) {
/*  424 */       Logger logger = LoggerFactory.getLogger(PdfPage.class);
/*  425 */       logger.warn("Source document has AcroForm dictionary. The pages you are going to copy may have FormFields, but they will not be copied, because you have not used any IPdfPageExtraCopier");
/*  426 */       (toDocument.getWriter()).isUserWarnedAboutAcroFormCopying = true;
/*      */     } 
/*      */ 
/*      */     
/*  430 */     return page;
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
/*      */   public PdfFormXObject copyAsFormXObject(PdfDocument toDocument) throws IOException {
/*  442 */     PdfFormXObject xObject = new PdfFormXObject(getCropBox());
/*      */     
/*  444 */     for (PdfName key : getPdfObject().keySet()) {
/*  445 */       if (XOBJECT_EXCLUDED_KEYS.contains(key)) {
/*      */         continue;
/*      */       }
/*  448 */       PdfObject obj = getPdfObject().get(key);
/*  449 */       if (!((PdfStream)xObject.getPdfObject()).containsKey(key)) {
/*  450 */         PdfObject copyObj = obj.copyTo(toDocument, false);
/*  451 */         ((PdfStream)xObject.getPdfObject()).put(key, copyObj);
/*      */       } 
/*      */     } 
/*  454 */     ((PdfStream)xObject.getPdfObject()).getOutputStream().write(getContentBytes());
/*      */     
/*  456 */     if (!((PdfStream)xObject.getPdfObject()).containsKey(PdfName.Resources)) {
/*  457 */       PdfObject copyResource = getResources().getPdfObject().copyTo(toDocument, true);
/*  458 */       ((PdfStream)xObject.getPdfObject()).put(PdfName.Resources, copyResource);
/*      */     } 
/*      */     
/*  461 */     return xObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument getDocument() {
/*  470 */     if (getPdfObject().getIndirectReference() != null)
/*  471 */       return getPdfObject().getIndirectReference().getDocument(); 
/*  472 */     return null;
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
/*      */   public void flush() {
/*  487 */     flush(false);
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
/*      */ 
/*      */   
/*      */   public void flush(boolean flushResourcesContentStreams) {
/*  510 */     if (isFlushed()) {
/*      */       return;
/*      */     }
/*  513 */     getDocument().dispatchEvent((Event)new PdfDocumentEvent("EndPdfPage", this));
/*      */     
/*  515 */     if (getDocument().isTagged() && !getDocument().getStructTreeRoot().isFlushed()) {
/*  516 */       tryFlushPageTags();
/*      */     }
/*      */     
/*  519 */     if (this.resources == null) {
/*      */       
/*  521 */       initResources(false);
/*  522 */     } else if (this.resources.isModified() && !this.resources.isReadOnly()) {
/*  523 */       put(PdfName.Resources, this.resources.getPdfObject());
/*      */     } 
/*  525 */     if (flushResourcesContentStreams) {
/*  526 */       getDocument().checkIsoConformance(this, IsoKey.PAGE);
/*  527 */       flushResourcesContentStreams();
/*      */     } 
/*      */     
/*  530 */     PdfArray annots = getAnnots(false);
/*  531 */     if (annots != null && !annots.isFlushed()) {
/*  532 */       for (int i = 0; i < annots.size(); i++) {
/*  533 */         PdfObject a = annots.get(i);
/*  534 */         if (a != null) {
/*  535 */           a.makeIndirect(getDocument()).flush();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  540 */     PdfStream thumb = getPdfObject().getAsStream(PdfName.Thumb);
/*  541 */     if (thumb != null) {
/*  542 */       thumb.flush();
/*      */     }
/*      */     
/*  545 */     PdfObject contentsObj = getPdfObject().get(PdfName.Contents);
/*      */     
/*  547 */     if (contentsObj != null && !contentsObj.isFlushed()) {
/*  548 */       int contentStreamCount = getContentStreamCount();
/*  549 */       for (int i = 0; i < contentStreamCount; i++) {
/*  550 */         PdfStream contentStream = getContentStream(i);
/*  551 */         if (contentStream != null) {
/*  552 */           contentStream.flush(false);
/*      */         }
/*      */       } 
/*      */     } 
/*  556 */     releaseInstanceFields();
/*      */     
/*  558 */     super.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getMediaBox() {
/*  569 */     PdfArray mediaBox = getPdfObject().getAsArray(PdfName.MediaBox);
/*  570 */     if (mediaBox == null) {
/*  571 */       mediaBox = (PdfArray)getInheritedValue(PdfName.MediaBox, 1);
/*      */     }
/*  573 */     if (mediaBox == null) {
/*  574 */       throw new PdfException("Invalid PDF. There is no media box attribute for page or its parents.");
/*      */     }
/*      */     int mediaBoxSize;
/*  577 */     if ((mediaBoxSize = mediaBox.size()) != 4) {
/*  578 */       if (mediaBoxSize > 4) {
/*  579 */         Logger logger = LoggerFactory.getLogger(PdfPage.class);
/*  580 */         if (logger.isErrorEnabled()) {
/*  581 */           logger.error(MessageFormatUtil.format("Wrong media box size: {0}. The arguments beyond the 4th will be ignored", new Object[] { Integer.valueOf(mediaBoxSize) }));
/*      */         }
/*      */       } 
/*      */       
/*  585 */       if (mediaBoxSize < 4) {
/*  586 */         throw (new PdfException("Wrong media box size: {0}. Need at least 4 arguments")).setMessageParams(new Object[] { Integer.valueOf(mediaBox.size()) });
/*      */       }
/*      */     } 
/*      */     
/*  590 */     PdfNumber llx = mediaBox.getAsNumber(0);
/*  591 */     PdfNumber lly = mediaBox.getAsNumber(1);
/*  592 */     PdfNumber urx = mediaBox.getAsNumber(2);
/*  593 */     PdfNumber ury = mediaBox.getAsNumber(3);
/*  594 */     if (llx == null || lly == null || urx == null || ury == null) {
/*  595 */       throw new PdfException("Tne media box object has incorrect values.");
/*      */     }
/*  597 */     return new Rectangle(Math.min(llx.floatValue(), urx.floatValue()), 
/*  598 */         Math.min(lly.floatValue(), ury.floatValue()), 
/*  599 */         Math.abs(urx.floatValue() - llx.floatValue()), 
/*  600 */         Math.abs(ury.floatValue() - lly.floatValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage setMediaBox(Rectangle rectangle) {
/*  611 */     put(PdfName.MediaBox, new PdfArray(rectangle));
/*  612 */     return this;
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
/*      */   public Rectangle getCropBox() {
/*  624 */     PdfArray cropBox = getPdfObject().getAsArray(PdfName.CropBox);
/*  625 */     if (cropBox == null) {
/*  626 */       cropBox = (PdfArray)getInheritedValue(PdfName.CropBox, 1);
/*  627 */       if (cropBox == null) {
/*  628 */         return getMediaBox();
/*      */       }
/*      */     } 
/*  631 */     return cropBox.toRectangle();
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
/*      */   public PdfPage setCropBox(Rectangle rectangle) {
/*  643 */     put(PdfName.CropBox, new PdfArray(rectangle));
/*  644 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage setBleedBox(Rectangle rectangle) {
/*  655 */     put(PdfName.BleedBox, new PdfArray(rectangle));
/*  656 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getBleedBox() {
/*  667 */     Rectangle bleedBox = getPdfObject().getAsRectangle(PdfName.BleedBox);
/*  668 */     return (bleedBox == null) ? getCropBox() : bleedBox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage setArtBox(Rectangle rectangle) {
/*  679 */     if (getPdfObject().getAsRectangle(PdfName.TrimBox) != null) {
/*  680 */       getPdfObject().remove(PdfName.TrimBox);
/*  681 */       Logger logger = LoggerFactory.getLogger(PdfPage.class);
/*  682 */       logger.warn("Only one of artbox or trimbox can exist on the page. The trimbox will be deleted");
/*      */     } 
/*  684 */     put(PdfName.ArtBox, new PdfArray(rectangle));
/*  685 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getArtBox() {
/*  696 */     Rectangle artBox = getPdfObject().getAsRectangle(PdfName.ArtBox);
/*  697 */     return (artBox == null) ? getCropBox() : artBox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage setTrimBox(Rectangle rectangle) {
/*  707 */     if (getPdfObject().getAsRectangle(PdfName.ArtBox) != null) {
/*  708 */       getPdfObject().remove(PdfName.ArtBox);
/*  709 */       Logger logger = LoggerFactory.getLogger(PdfPage.class);
/*  710 */       logger.warn("Only one of artbox or trimbox can exist on the page. The trimbox will be deleted");
/*      */     } 
/*  712 */     put(PdfName.TrimBox, new PdfArray(rectangle));
/*  713 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getTrimBox() {
/*  724 */     Rectangle trimBox = getPdfObject().getAsRectangle(PdfName.TrimBox);
/*  725 */     return (trimBox == null) ? getCropBox() : trimBox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getContentBytes() {
/*      */     try {
/*  736 */       MemoryLimitsAwareHandler handler = (getDocument()).memoryLimitsAwareHandler;
/*  737 */       long usedMemory = (null == handler) ? -1L : handler.getAllMemoryUsedForDecompression();
/*      */       
/*  739 */       MemoryLimitsAwareOutputStream baos = new MemoryLimitsAwareOutputStream();
/*  740 */       int streamCount = getContentStreamCount();
/*      */       
/*  742 */       for (int i = 0; i < streamCount; i++) {
/*  743 */         byte[] streamBytes = getStreamBytes(i);
/*      */         
/*  745 */         if (null != handler && usedMemory < handler.getAllMemoryUsedForDecompression()) {
/*  746 */           baos.setMaxStreamSize(handler.getMaxSizeOfSingleDecompressedPdfStream());
/*      */         }
/*  748 */         baos.write(streamBytes);
/*  749 */         if (0 != streamBytes.length && !Character.isWhitespace((char)streamBytes[streamBytes.length - 1])) {
/*  750 */           baos.write(10);
/*      */         }
/*      */       } 
/*  753 */       return baos.toByteArray();
/*  754 */     } catch (IOException ioe) {
/*  755 */       throw new PdfException("Cannot get content bytes.", ioe, this);
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
/*      */   public byte[] getStreamBytes(int index) {
/*  767 */     return getContentStream(index).getBytes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextMcid() {
/*  777 */     if (!getDocument().isTagged()) {
/*  778 */       throw new PdfException("Must be a tagged document.");
/*      */     }
/*  780 */     if (this.mcid == -1) {
/*  781 */       PdfStructTreeRoot structTreeRoot = getDocument().getStructTreeRoot();
/*  782 */       this.mcid = structTreeRoot.getNextMcidForPage(this);
/*      */     } 
/*  784 */     return this.mcid++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStructParentIndex() {
/*  794 */     return (getPdfObject().getAsNumber(PdfName.StructParents) != null) ? getPdfObject().getAsNumber(PdfName.StructParents).intValue() : -1;
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
/*      */   public PdfPage setAdditionalAction(PdfName key, PdfAction action) {
/*  806 */     PdfAction.setAdditionalAction(this, key, action);
/*  807 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<PdfAnnotation> getAnnotations() {
/*  817 */     List<PdfAnnotation> annotations = new ArrayList<>();
/*  818 */     PdfArray annots = getPdfObject().getAsArray(PdfName.Annots);
/*  819 */     if (annots != null)
/*  820 */       for (int i = 0; i < annots.size(); i++) {
/*  821 */         PdfDictionary annot = annots.getAsDictionary(i);
/*  822 */         if (annot != null) {
/*      */ 
/*      */           
/*  825 */           PdfAnnotation annotation = PdfAnnotation.makeAnnotation(annot);
/*  826 */           if (annotation != null) {
/*      */ 
/*      */             
/*  829 */             boolean hasBeenNotModified = (annot.getIndirectReference() != null && !annot.getIndirectReference().checkState((short)8));
/*  830 */             annotations.add(annotation.setPage(this));
/*  831 */             if (hasBeenNotModified) {
/*  832 */               annot.getIndirectReference().clearState((short)8);
/*  833 */               annot.clearState((short)128);
/*      */             } 
/*      */           } 
/*      */         } 
/*  837 */       }   return annotations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsAnnotation(PdfAnnotation annotation) {
/*  847 */     for (PdfAnnotation a : getAnnotations()) {
/*  848 */       if (((PdfDictionary)a.getPdfObject()).equals(annotation.getPdfObject())) {
/*  849 */         return true;
/*      */       }
/*      */     } 
/*  852 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage addAnnotation(PdfAnnotation annotation) {
/*  863 */     return addAnnotation(-1, annotation, true);
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
/*      */   public PdfPage addAnnotation(int index, PdfAnnotation annotation, boolean tagAnnotation) {
/*  878 */     if (getDocument().isTagged()) {
/*  879 */       if (tagAnnotation) {
/*  880 */         TagTreePointer tagPointer = getDocument().getTagStructureContext().getAutoTaggingPointer();
/*  881 */         PdfPage prevPage = tagPointer.getCurrentPage();
/*  882 */         tagPointer.setPageForTagging(this).addAnnotationTag(annotation);
/*  883 */         if (prevPage != null) {
/*  884 */           tagPointer.setPageForTagging(prevPage);
/*      */         }
/*      */       } 
/*  887 */       if (getTabOrder() == null) {
/*  888 */         setTabOrder(PdfName.S);
/*      */       }
/*      */     } 
/*      */     
/*  892 */     PdfArray annots = getAnnots(true);
/*  893 */     if (index == -1) {
/*  894 */       annots.add(annotation.setPage(this).getPdfObject());
/*      */     } else {
/*  896 */       annots.add(index, annotation.setPage(this).getPdfObject());
/*      */     } 
/*      */     
/*  899 */     if (annots.getIndirectReference() == null) {
/*      */       
/*  901 */       setModified();
/*      */     } else {
/*      */       
/*  904 */       annots.setModified();
/*      */     } 
/*      */     
/*  907 */     return this;
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
/*      */   public PdfPage removeAnnotation(PdfAnnotation annotation) {
/*  919 */     PdfArray annots = getAnnots(false);
/*  920 */     if (annots != null) {
/*  921 */       annots.remove(annotation.getPdfObject());
/*      */       
/*  923 */       if (annots.isEmpty()) {
/*  924 */         getPdfObject().remove(PdfName.Annots);
/*  925 */         setModified();
/*  926 */       } else if (annots.getIndirectReference() == null) {
/*  927 */         setModified();
/*      */       } 
/*      */     } 
/*      */     
/*  931 */     if (getDocument().isTagged()) {
/*  932 */       TagTreePointer tagPointer = getDocument().getTagStructureContext().removeAnnotationTag(annotation);
/*  933 */       if (tagPointer != null) {
/*      */         
/*  935 */         boolean standardAnnotTagRole = ("Annot".equals(tagPointer.getRole()) || "Form".equals(tagPointer.getRole()));
/*  936 */         if (tagPointer.getKidsRoles().size() == 0 && standardAnnotTagRole) {
/*  937 */           tagPointer.removeTag();
/*      */         }
/*      */       } 
/*      */     } 
/*  941 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAnnotsSize() {
/*  950 */     PdfArray annots = getAnnots(false);
/*  951 */     if (annots == null)
/*  952 */       return 0; 
/*  953 */     return annots.size();
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
/*      */   public List<PdfOutline> getOutlines(boolean updateOutlines) {
/*  965 */     getDocument().getOutlines(updateOutlines);
/*  966 */     return getDocument().getCatalog().getPagesWithOutlines().get(getPdfObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isIgnorePageRotationForContent() {
/*  974 */     return this.ignorePageRotationForContent;
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
/*      */   public PdfPage setIgnorePageRotationForContent(boolean ignorePageRotationForContent) {
/*  986 */     this.ignorePageRotationForContent = ignorePageRotationForContent;
/*  987 */     return this;
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
/*      */   public PdfPage setPageLabel(PageLabelNumberingStyle numberingStyle, String labelPrefix) {
/*  999 */     return setPageLabel(numberingStyle, labelPrefix, 1);
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
/*      */   public PdfPage setPageLabel(PageLabelNumberingStyle numberingStyle, String labelPrefix, int firstPage) {
/* 1013 */     if (firstPage < 1)
/* 1014 */       throw new PdfException("In a page label the page numbers must be greater or equal to 1."); 
/* 1015 */     PdfDictionary pageLabel = new PdfDictionary();
/* 1016 */     if (numberingStyle != null) {
/* 1017 */       switch (numberingStyle) {
/*      */         case DECIMAL_ARABIC_NUMERALS:
/* 1019 */           pageLabel.put(PdfName.S, PdfName.D);
/*      */           break;
/*      */         case UPPERCASE_ROMAN_NUMERALS:
/* 1022 */           pageLabel.put(PdfName.S, PdfName.R);
/*      */           break;
/*      */         case LOWERCASE_ROMAN_NUMERALS:
/* 1025 */           pageLabel.put(PdfName.S, PdfName.r);
/*      */           break;
/*      */         case UPPERCASE_LETTERS:
/* 1028 */           pageLabel.put(PdfName.S, PdfName.A);
/*      */           break;
/*      */         case LOWERCASE_LETTERS:
/* 1031 */           pageLabel.put(PdfName.S, PdfName.a);
/*      */           break;
/*      */       } 
/*      */     
/*      */     }
/* 1036 */     if (labelPrefix != null) {
/* 1037 */       pageLabel.put(PdfName.P, new PdfString(labelPrefix));
/*      */     }
/*      */     
/* 1040 */     if (firstPage != 1) {
/* 1041 */       pageLabel.put(PdfName.St, new PdfNumber(firstPage));
/*      */     }
/* 1043 */     getDocument().getCatalog().getPageLabelsTree(true).addEntry(getDocument().getPageNumber(this) - 1, pageLabel);
/* 1044 */     return this;
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
/*      */   public PdfPage setTabOrder(PdfName tabOrder) {
/* 1057 */     put(PdfName.Tabs, tabOrder);
/* 1058 */     return this;
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
/*      */   public PdfName getTabOrder() {
/* 1070 */     return getPdfObject().getAsName(PdfName.Tabs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage setThumbnailImage(PdfImageXObject thumb) {
/* 1081 */     return put(PdfName.Thumb, thumb.getPdfObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfImageXObject getThumbnailImage() {
/* 1091 */     PdfStream thumbStream = getPdfObject().getAsStream(PdfName.Thumb);
/* 1092 */     return (thumbStream != null) ? new PdfImageXObject(thumbStream) : null;
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
/*      */   public PdfPage addOutputIntent(PdfOutputIntent outputIntent) {
/* 1104 */     if (outputIntent == null) {
/* 1105 */       return this;
/*      */     }
/* 1107 */     PdfArray outputIntents = getPdfObject().getAsArray(PdfName.OutputIntents);
/* 1108 */     if (outputIntents == null) {
/* 1109 */       outputIntents = new PdfArray();
/* 1110 */       put(PdfName.OutputIntents, outputIntents);
/*      */     } 
/* 1112 */     outputIntents.add(outputIntent.getPdfObject());
/* 1113 */     return this;
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
/*      */   public PdfPage put(PdfName key, PdfObject value) {
/* 1125 */     getPdfObject().put(key, value);
/* 1126 */     setModified();
/* 1127 */     return this;
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
/*      */   public boolean isPageRotationInverseMatrixWritten() {
/* 1141 */     return this.pageRotationInverseMatrixWritten;
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
/*      */   public void setPageRotationInverseMatrixWritten() {
/* 1153 */     this.pageRotationInverseMatrixWritten = true;
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
/*      */   public void addAssociatedFile(String description, PdfFileSpec fs) {
/* 1169 */     if (null == ((PdfDictionary)fs.getPdfObject()).get(PdfName.AFRelationship)) {
/* 1170 */       Logger logger = LoggerFactory.getLogger(PdfPage.class);
/* 1171 */       logger.error("For associated files their associated file specification dictionaries shall include the AFRelationship key.");
/*      */     } 
/* 1173 */     if (null != description) {
/* 1174 */       getDocument().getCatalog().addNameToNameTree(description, fs.getPdfObject(), PdfName.EmbeddedFiles);
/*      */     }
/* 1176 */     PdfArray afArray = getPdfObject().getAsArray(PdfName.AF);
/* 1177 */     if (afArray == null) {
/* 1178 */       afArray = new PdfArray();
/* 1179 */       put(PdfName.AF, afArray);
/*      */     } 
/* 1181 */     afArray.add(fs.getPdfObject());
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
/*      */   public void addAssociatedFile(PdfFileSpec fs) {
/* 1196 */     addAssociatedFile((String)null, fs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getAssociatedFiles(boolean create) {
/* 1206 */     PdfArray afArray = getPdfObject().getAsArray(PdfName.AF);
/* 1207 */     if (afArray == null && create) {
/* 1208 */       afArray = new PdfArray();
/* 1209 */       put(PdfName.AF, afArray);
/*      */     } 
/* 1211 */     return afArray;
/*      */   }
/*      */   
/*      */   void tryFlushPageTags() {
/*      */     try {
/* 1216 */       if (!(getDocument()).isClosing) {
/* 1217 */         getDocument().getTagStructureContext().flushPageTags(this);
/*      */       }
/* 1219 */       getDocument().getStructTreeRoot().savePageStructParentIndexIfNeeded(this);
/* 1220 */     } catch (Exception ex) {
/* 1221 */       throw new PdfException("Tag structure flushing failed: it might be corrupted.", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   void releaseInstanceFields() {
/* 1226 */     this.resources = null;
/* 1227 */     this.parentPages = null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isWrappedObjectMustBeIndirect() {
/* 1232 */     return true;
/*      */   }
/*      */   
/*      */   private PdfArray getAnnots(boolean create) {
/* 1236 */     PdfArray annots = getPdfObject().getAsArray(PdfName.Annots);
/* 1237 */     if (annots == null && create) {
/* 1238 */       annots = new PdfArray();
/* 1239 */       put(PdfName.Annots, annots);
/*      */     } 
/* 1241 */     return annots;
/*      */   }
/*      */   
/*      */   private PdfObject getInheritedValue(PdfName pdfName, int type) {
/* 1245 */     if (this.parentPages == null) {
/* 1246 */       this.parentPages = getDocument().getCatalog().getPageTree().findPageParent(this);
/*      */     }
/* 1248 */     PdfObject val = getInheritedValue(this.parentPages, pdfName);
/* 1249 */     return (val != null && val.getType() == type) ? val : null;
/*      */   }
/*      */   
/*      */   private static PdfObject getInheritedValue(PdfPages parentPages, PdfName pdfName) {
/* 1253 */     if (parentPages != null) {
/* 1254 */       PdfDictionary parentDictionary = parentPages.getPdfObject();
/* 1255 */       PdfObject value = parentDictionary.get(pdfName);
/* 1256 */       if (value != null) {
/* 1257 */         return value;
/*      */       }
/* 1259 */       return getInheritedValue(parentPages.getParent(), pdfName);
/*      */     } 
/*      */     
/* 1262 */     return null;
/*      */   }
/*      */   private PdfStream newContentStream(boolean before) {
/*      */     PdfArray array;
/* 1266 */     PdfObject contents = getPdfObject().get(PdfName.Contents);
/*      */     
/* 1268 */     if (contents instanceof PdfStream) {
/* 1269 */       array = new PdfArray();
/* 1270 */       if (contents.getIndirectReference() != null) {
/*      */         
/* 1272 */         array.add(contents.getIndirectReference());
/*      */       } else {
/* 1274 */         array.add(contents);
/*      */       } 
/* 1276 */       put(PdfName.Contents, array);
/* 1277 */     } else if (contents instanceof PdfArray) {
/* 1278 */       array = (PdfArray)contents;
/*      */     } else {
/* 1280 */       array = null;
/*      */     } 
/* 1282 */     PdfStream contentStream = (PdfStream)(new PdfStream()).makeIndirect(getDocument());
/* 1283 */     if (array != null) {
/* 1284 */       if (before) {
/* 1285 */         array.add(0, contentStream);
/*      */       } else {
/* 1287 */         array.add(contentStream);
/*      */       } 
/* 1289 */       if (array.getIndirectReference() != null) {
/* 1290 */         array.setModified();
/*      */       } else {
/* 1292 */         setModified();
/*      */       } 
/*      */     } else {
/* 1295 */       put(PdfName.Contents, contentStream);
/*      */     } 
/* 1297 */     return contentStream;
/*      */   }
/*      */   
/*      */   private void flushResourcesContentStreams() {
/* 1301 */     flushResourcesContentStreams(getResources().getPdfObject());
/*      */     
/* 1303 */     PdfArray annots = getAnnots(false);
/* 1304 */     if (annots != null && !annots.isFlushed()) {
/* 1305 */       for (int i = 0; i < annots.size(); i++) {
/* 1306 */         PdfDictionary apDict = annots.getAsDictionary(i).getAsDictionary(PdfName.AP);
/* 1307 */         if (apDict != null) {
/* 1308 */           flushAppearanceStreams(apDict);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void flushResourcesContentStreams(PdfDictionary resources) {
/* 1315 */     if (resources != null && !resources.isFlushed()) {
/* 1316 */       flushWithResources(resources.getAsDictionary(PdfName.XObject));
/* 1317 */       flushWithResources(resources.getAsDictionary(PdfName.Pattern));
/* 1318 */       flushWithResources(resources.getAsDictionary(PdfName.Shading));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void flushWithResources(PdfDictionary objsCollection) {
/* 1323 */     if (objsCollection == null || objsCollection.isFlushed()) {
/*      */       return;
/*      */     }
/*      */     
/* 1327 */     for (PdfObject obj : objsCollection.values()) {
/* 1328 */       if (obj.isFlushed())
/*      */         continue; 
/* 1330 */       flushResourcesContentStreams(((PdfDictionary)obj).getAsDictionary(PdfName.Resources));
/* 1331 */       flushMustBeIndirectObject(obj);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void flushAppearanceStreams(PdfDictionary appearanceStreamsDict) {
/* 1336 */     if (appearanceStreamsDict.isFlushed()) {
/*      */       return;
/*      */     }
/* 1339 */     for (PdfObject val : appearanceStreamsDict.values()) {
/* 1340 */       if (val instanceof PdfDictionary) {
/* 1341 */         PdfDictionary ap = (PdfDictionary)val;
/* 1342 */         if (ap.isDictionary()) {
/* 1343 */           flushAppearanceStreams(ap); continue;
/* 1344 */         }  if (ap.isStream()) {
/* 1345 */           flushMustBeIndirectObject(ap);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void flushMustBeIndirectObject(PdfObject obj) {
/* 1353 */     obj.makeIndirect(getDocument()).flush();
/*      */   }
/*      */   
/*      */   private void copyInheritedProperties(PdfPage copyPdfPage, PdfDocument pdfDocument) {
/* 1357 */     if (copyPdfPage.getPdfObject().get(PdfName.Resources) == null) {
/* 1358 */       PdfObject copyResource = pdfDocument.getWriter().copyObject(getResources().getPdfObject(), pdfDocument, false);
/* 1359 */       copyPdfPage.getPdfObject().put(PdfName.Resources, copyResource);
/*      */     } 
/* 1361 */     if (copyPdfPage.getPdfObject().get(PdfName.MediaBox) == null)
/*      */     {
/* 1363 */       copyPdfPage.setMediaBox(getMediaBox());
/*      */     }
/* 1365 */     if (copyPdfPage.getPdfObject().get(PdfName.CropBox) == null) {
/*      */       
/* 1367 */       PdfArray cropBox = (PdfArray)getInheritedValue(PdfName.CropBox, 1);
/*      */       
/* 1369 */       if (cropBox != null) {
/* 1370 */         copyPdfPage.put(PdfName.CropBox, cropBox.copyTo(pdfDocument));
/*      */       }
/*      */     } 
/* 1373 */     if (copyPdfPage.getPdfObject().get(PdfName.Rotate) == null) {
/*      */       
/* 1375 */       PdfNumber rotate = (PdfNumber)getInheritedValue(PdfName.Rotate, 8);
/*      */       
/* 1377 */       if (rotate != null) {
/* 1378 */         copyPdfPage.put(PdfName.Rotate, rotate.copyTo(pdfDocument));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void rebuildFormFieldParent(PdfDictionary field, PdfDictionary newField, PdfDocument toDocument) {
/* 1384 */     if (newField.containsKey(PdfName.Parent)) {
/*      */       return;
/*      */     }
/* 1387 */     PdfDictionary oldParent = field.getAsDictionary(PdfName.Parent);
/* 1388 */     if (oldParent != null) {
/* 1389 */       PdfDictionary newParent = oldParent.copyTo(toDocument, Arrays.asList(new PdfName[] { PdfName.P, PdfName.Kids, PdfName.Parent }, ), false);
/* 1390 */       if (newParent.isFlushed()) {
/* 1391 */         newParent = oldParent.copyTo(toDocument, Arrays.asList(new PdfName[] { PdfName.P, PdfName.Kids, PdfName.Parent }, ), true);
/*      */       }
/* 1393 */       rebuildFormFieldParent(oldParent, newParent, toDocument);
/*      */       
/* 1395 */       PdfArray kids = newParent.getAsArray(PdfName.Kids);
/* 1396 */       if (kids == null) {
/* 1397 */         newParent.put(PdfName.Kids, new PdfArray());
/*      */       }
/* 1399 */       newField.put(PdfName.Parent, newParent);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfPage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */