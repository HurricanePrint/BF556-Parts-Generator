/*      */ package com.itextpdf.kernel.pdf;
/*      */ 
/*      */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*      */ import com.itextpdf.io.source.ByteUtils;
/*      */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.kernel.PdfException;
/*      */ import com.itextpdf.kernel.ProductInfo;
/*      */ import com.itextpdf.kernel.Version;
/*      */ import com.itextpdf.kernel.VersionInfo;
/*      */ import com.itextpdf.kernel.counter.EventCounterHandler;
/*      */ import com.itextpdf.kernel.counter.event.CoreEvent;
/*      */ import com.itextpdf.kernel.counter.event.IEvent;
/*      */ import com.itextpdf.kernel.crypto.BadPasswordException;
/*      */ import com.itextpdf.kernel.events.Event;
/*      */ import com.itextpdf.kernel.events.EventDispatcher;
/*      */ import com.itextpdf.kernel.events.IEventDispatcher;
/*      */ import com.itextpdf.kernel.events.IEventHandler;
/*      */ import com.itextpdf.kernel.events.PdfDocumentEvent;
/*      */ import com.itextpdf.kernel.font.PdfFont;
/*      */ import com.itextpdf.kernel.font.PdfFontFactory;
/*      */ import com.itextpdf.kernel.geom.PageSize;
/*      */ import com.itextpdf.kernel.log.CounterManager;
/*      */ import com.itextpdf.kernel.log.ICounter;
/*      */ import com.itextpdf.kernel.numbering.EnglishAlphabetNumbering;
/*      */ import com.itextpdf.kernel.numbering.RomanNumbering;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
/*      */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*      */ import com.itextpdf.kernel.pdf.collection.PdfCollection;
/*      */ import com.itextpdf.kernel.pdf.filespec.PdfEncryptedPayloadFileSpecFactory;
/*      */ import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
/*      */ import com.itextpdf.kernel.pdf.navigation.PdfDestination;
/*      */ import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
/*      */ import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
/*      */ import com.itextpdf.kernel.xmp.XMPException;
/*      */ import com.itextpdf.kernel.xmp.XMPMeta;
/*      */ import com.itextpdf.kernel.xmp.XMPMetaFactory;
/*      */ import com.itextpdf.kernel.xmp.options.PropertyOptions;
/*      */ import com.itextpdf.kernel.xmp.options.SerializeOptions;
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.atomic.AtomicLong;
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
/*      */ public class PdfDocument
/*      */   implements IEventDispatcher, Closeable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7041578979319799646L;
/*  116 */   private static IPdfPageFactory pdfPageFactory = new PdfPageFactory();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*  122 */   protected PdfPage currentPage = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   protected PageSize defaultPageSize = PageSize.Default;
/*      */   
/*  131 */   protected transient EventDispatcher eventDispatcher = new EventDispatcher();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  137 */   protected PdfWriter writer = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   protected PdfReader reader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   protected byte[] xmpMetadata = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   protected PdfCatalog catalog = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   protected PdfDictionary trailer = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  163 */   protected PdfDocumentInfo info = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  168 */   protected PdfVersion pdfVersion = PdfVersion.PDF_1_7;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PdfString originalDocumentId;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PdfString modifiedDocumentId;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  183 */   final PdfXrefTable xref = new PdfXrefTable();
/*      */   
/*      */   protected FingerPrint fingerPrint;
/*      */   
/*      */   protected final StampingProperties properties;
/*      */   
/*      */   protected PdfStructTreeRoot structTreeRoot;
/*  190 */   protected int structParentIndex = -1;
/*      */ 
/*      */   
/*      */   protected boolean closeReader = true;
/*      */ 
/*      */   
/*      */   protected boolean closeWriter = true;
/*      */   
/*      */   protected boolean isClosing = false;
/*      */   
/*      */   protected boolean closed = false;
/*      */   
/*      */   protected boolean flushUnusedObjects = false;
/*      */   
/*  204 */   private Map<PdfIndirectReference, PdfFont> documentFonts = new HashMap<>();
/*  205 */   private PdfFont defaultFont = null;
/*      */   
/*      */   protected transient TagStructureContext tagStructureContext;
/*      */   
/*  209 */   private static final AtomicLong lastDocumentId = new AtomicLong();
/*      */   
/*      */   private long documentId;
/*  212 */   private VersionInfo versionInfo = Version.getInstance().getInfo();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  219 */   private LinkedHashMap<PdfPage, List<PdfLinkAnnotation>> linkAnnotations = new LinkedHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  224 */   Map<PdfIndirectReference, byte[]> serializedObjectsCache = (Map)new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  229 */   MemoryLimitsAwareHandler memoryLimitsAwareHandler = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument(PdfReader reader) {
/*  237 */     this(reader, new DocumentProperties());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument(PdfReader reader, DocumentProperties properties) {
/*  247 */     if (reader == null) {
/*  248 */       throw new IllegalArgumentException("The reader in PdfDocument constructor can not be null.");
/*      */     }
/*  250 */     this.documentId = lastDocumentId.incrementAndGet();
/*  251 */     this.reader = reader;
/*      */     
/*  253 */     this.properties = new StampingProperties();
/*  254 */     this.properties.setEventCountingMetaInfo(properties.metaInfo);
/*  255 */     open(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument(PdfWriter writer) {
/*  265 */     this(writer, new DocumentProperties());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument(PdfWriter writer, DocumentProperties properties) {
/*  276 */     if (writer == null) {
/*  277 */       throw new IllegalArgumentException("The writer in PdfDocument constructor can not be null.");
/*      */     }
/*  279 */     this.documentId = lastDocumentId.incrementAndGet();
/*  280 */     this.writer = writer;
/*      */     
/*  282 */     this.properties = new StampingProperties();
/*  283 */     this.properties.setEventCountingMetaInfo(properties.metaInfo);
/*  284 */     open(writer.properties.pdfVersion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument(PdfReader reader, PdfWriter writer) {
/*  295 */     this(reader, writer, new StampingProperties());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument(PdfReader reader, PdfWriter writer, StampingProperties properties) {
/*  306 */     if (reader == null) {
/*  307 */       throw new IllegalArgumentException("The reader in PdfDocument constructor can not be null.");
/*      */     }
/*  309 */     if (writer == null) {
/*  310 */       throw new IllegalArgumentException("The writer in PdfDocument constructor can not be null.");
/*      */     }
/*  312 */     this.documentId = lastDocumentId.incrementAndGet();
/*  313 */     this.reader = reader;
/*  314 */     this.writer = writer;
/*  315 */     this.properties = properties;
/*      */     
/*  317 */     boolean writerHasEncryption = writerHasEncryption();
/*  318 */     if (properties.appendMode && writerHasEncryption) {
/*  319 */       Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/*  320 */       logger.warn("Writer encryption will be ignored, because append mode is used. Document will preserve the original encryption (or will stay unencrypted)");
/*      */     } 
/*  322 */     if (properties.preserveEncryption && writerHasEncryption) {
/*  323 */       Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/*  324 */       logger.warn("Writer encryption will be ignored, because preservation of encryption is enabled. Document will preserve the original encryption (or will stay unencrypted)");
/*      */     } 
/*      */     
/*  327 */     open(writer.properties.pdfVersion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setXmpMetadata(byte[] xmpMetadata) {
/*  336 */     this.xmpMetadata = xmpMetadata;
/*      */   }
/*      */   
/*      */   public void setXmpMetadata(XMPMeta xmpMeta, SerializeOptions serializeOptions) throws XMPException {
/*  340 */     setXmpMetadata(XMPMetaFactory.serializeToBuffer(xmpMeta, serializeOptions));
/*      */   }
/*      */   
/*      */   public void setXmpMetadata(XMPMeta xmpMeta) throws XMPException {
/*  344 */     SerializeOptions serializeOptions = new SerializeOptions();
/*  345 */     serializeOptions.setPadding(2000);
/*  346 */     setXmpMetadata(xmpMeta, serializeOptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getXmpMetadata() {
/*  355 */     return getXmpMetadata(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getXmpMetadata(boolean createNew) {
/*  365 */     if (this.xmpMetadata == null && createNew) {
/*  366 */       XMPMeta xmpMeta = XMPMetaFactory.create();
/*  367 */       xmpMeta.setObjectName("xmpmeta");
/*  368 */       xmpMeta.setObjectName("");
/*  369 */       addCustomMetadataExtensions(xmpMeta);
/*      */       try {
/*  371 */         xmpMeta.setProperty("http://purl.org/dc/elements/1.1/", "format", "application/pdf");
/*  372 */         xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", "Producer", this.versionInfo.getVersion());
/*  373 */         setXmpMetadata(xmpMeta);
/*  374 */       } catch (XMPException xMPException) {}
/*      */     } 
/*      */     
/*  377 */     return this.xmpMetadata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfObject getPdfObject(int objNum) {
/*  387 */     checkClosingStatus();
/*  388 */     PdfIndirectReference reference = this.xref.get(objNum);
/*  389 */     if (reference == null) {
/*  390 */       return null;
/*      */     }
/*  392 */     return reference.getRefersTo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumberOfPdfObjects() {
/*  402 */     return this.xref.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage getPage(int pageNum) {
/*  412 */     checkClosingStatus();
/*  413 */     return this.catalog.getPageTree().getPage(pageNum);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage getPage(PdfDictionary pageDictionary) {
/*  423 */     checkClosingStatus();
/*  424 */     return this.catalog.getPageTree().getPage(pageDictionary);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage getFirstPage() {
/*  433 */     checkClosingStatus();
/*  434 */     return getPage(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage getLastPage() {
/*  443 */     return getPage(getNumberOfPages());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage addNewPage() {
/*  452 */     return addNewPage(getDefaultPageSize());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage addNewPage(PageSize pageSize) {
/*  462 */     checkClosingStatus();
/*  463 */     PdfPage page = getPageFactory().createPdfPage(this, pageSize);
/*  464 */     checkAndAddPage(page);
/*  465 */     dispatchEvent((Event)new PdfDocumentEvent("StartPdfPage", page));
/*  466 */     dispatchEvent((Event)new PdfDocumentEvent("InsertPdfPage", page));
/*  467 */     return page;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage addNewPage(int index) {
/*  478 */     return addNewPage(index, getDefaultPageSize());
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
/*      */   public PdfPage addNewPage(int index, PageSize pageSize) {
/*  490 */     checkClosingStatus();
/*  491 */     PdfPage page = getPageFactory().createPdfPage(this, pageSize);
/*  492 */     checkAndAddPage(index, page);
/*  493 */     this.currentPage = page;
/*  494 */     dispatchEvent((Event)new PdfDocumentEvent("StartPdfPage", page));
/*  495 */     dispatchEvent((Event)new PdfDocumentEvent("InsertPdfPage", page));
/*  496 */     return page;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfPage addPage(PdfPage page) {
/*  507 */     checkClosingStatus();
/*  508 */     checkAndAddPage(page);
/*  509 */     dispatchEvent((Event)new PdfDocumentEvent("InsertPdfPage", page));
/*  510 */     return page;
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
/*      */   public PdfPage addPage(int index, PdfPage page) {
/*  522 */     checkClosingStatus();
/*  523 */     checkAndAddPage(index, page);
/*  524 */     this.currentPage = page;
/*  525 */     dispatchEvent((Event)new PdfDocumentEvent("InsertPdfPage", page));
/*  526 */     return page;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumberOfPages() {
/*  535 */     checkClosingStatus();
/*  536 */     return this.catalog.getPageTree().getNumberOfPages();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPageNumber(PdfPage page) {
/*  546 */     checkClosingStatus();
/*  547 */     return this.catalog.getPageTree().getPageNumber(page);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPageNumber(PdfDictionary pageDictionary) {
/*  557 */     return this.catalog.getPageTree().getPageNumber(pageDictionary);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean movePage(PdfPage page, int insertBefore) {
/*  568 */     checkClosingStatus();
/*  569 */     int pageNum = getPageNumber(page);
/*  570 */     if (pageNum > 0) {
/*  571 */       movePage(pageNum, insertBefore);
/*  572 */       return true;
/*      */     } 
/*  574 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void movePage(int pageNumber, int insertBefore) {
/*  584 */     checkClosingStatus();
/*  585 */     if (insertBefore < 1 || insertBefore > getNumberOfPages() + 1) {
/*  586 */       throw new IndexOutOfBoundsException(MessageFormatUtil.format("Requested page number {0} is out of bounds.", new Object[] { Integer.valueOf(insertBefore) }));
/*      */     }
/*  588 */     PdfPage page = getPage(pageNumber);
/*  589 */     if (isTagged()) {
/*  590 */       getStructTreeRoot().move(page, insertBefore);
/*  591 */       getTagStructureContext().normalizeDocumentRootTag();
/*      */     } 
/*  593 */     PdfPage removedPage = this.catalog.getPageTree().removePage(pageNumber);
/*  594 */     if (insertBefore > pageNumber) {
/*  595 */       insertBefore--;
/*      */     }
/*  597 */     this.catalog.getPageTree().addPage(insertBefore, removedPage);
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
/*      */   public boolean removePage(PdfPage page) {
/*  610 */     checkClosingStatus();
/*  611 */     int pageNum = getPageNumber(page);
/*  612 */     if (pageNum >= 1) {
/*  613 */       removePage(pageNum);
/*  614 */       return true;
/*      */     } 
/*  616 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePage(int pageNum) {
/*  625 */     checkClosingStatus();
/*      */     
/*  627 */     PdfPage removedPage = getPage(pageNum);
/*  628 */     if (removedPage != null && removedPage.isFlushed() && (isTagged() || hasAcroForm())) {
/*  629 */       throw new PdfException("Flushed page cannot be removed from a document which is tagged or has an AcroForm");
/*      */     }
/*      */     
/*  632 */     this.catalog.getPageTree().removePage(pageNum);
/*      */     
/*  634 */     if (removedPage != null) {
/*  635 */       this.catalog.removeOutlines(removedPage);
/*  636 */       removeUnusedWidgetsFromFields(removedPage);
/*  637 */       if (isTagged()) {
/*  638 */         getTagStructureContext().removePageTags(removedPage);
/*      */       }
/*  640 */       if (!removedPage.isFlushed()) {
/*  641 */         removedPage.getPdfObject().remove(PdfName.Parent);
/*  642 */         removedPage.getPdfObject().getIndirectReference().setFree();
/*      */       } 
/*      */       
/*  645 */       dispatchEvent((Event)new PdfDocumentEvent("RemovePdfPage", removedPage));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocumentInfo getDocumentInfo() {
/*  655 */     checkClosingStatus();
/*  656 */     return this.info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfString getOriginalDocumentId() {
/*  667 */     return this.originalDocumentId;
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
/*      */   public PdfString getModifiedDocumentId() {
/*  679 */     return this.modifiedDocumentId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PageSize getDefaultPageSize() {
/*  688 */     return this.defaultPageSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultPageSize(PageSize pageSize) {
/*  697 */     this.defaultPageSize = pageSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEventHandler(String type, IEventHandler handler) {
/*  705 */     this.eventDispatcher.addEventHandler(type, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchEvent(Event event) {
/*  713 */     this.eventDispatcher.dispatchEvent(event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchEvent(Event event, boolean delayed) {
/*  721 */     this.eventDispatcher.dispatchEvent(event, delayed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasEventHandler(String type) {
/*  729 */     return this.eventDispatcher.hasEventHandler(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEventHandler(String type, IEventHandler handler) {
/*  737 */     this.eventDispatcher.removeEventHandler(type, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllHandlers() {
/*  745 */     this.eventDispatcher.removeAllHandlers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfWriter getWriter() {
/*  754 */     checkClosingStatus();
/*  755 */     return this.writer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfReader getReader() {
/*  764 */     checkClosingStatus();
/*  765 */     return this.reader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAppendMode() {
/*  774 */     checkClosingStatus();
/*  775 */     return this.properties.appendMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfIndirectReference createNextIndirectReference() {
/*  784 */     checkClosingStatus();
/*  785 */     return this.xref.createNextIndirectReference(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfVersion getPdfVersion() {
/*  794 */     return this.pdfVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfCatalog getCatalog() {
/*  803 */     checkClosingStatus();
/*  804 */     return this.catalog;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/*  812 */     if (this.closed) {
/*      */       return;
/*      */     }
/*  815 */     this.isClosing = true;
/*      */     try {
/*  817 */       if (this.writer != null) {
/*  818 */         if (this.catalog.isFlushed()) {
/*  819 */           throw new PdfException("Cannot close document with already flushed PDF Catalog.");
/*      */         }
/*  821 */         updateProducerInInfoDictionary();
/*  822 */         updateXmpMetadata();
/*      */         
/*  824 */         if (this.pdfVersion.compareTo(PdfVersion.PDF_2_0) >= 0) {
/*  825 */           for (PdfName deprecatedKey : PdfDocumentInfo.PDF20_DEPRECATED_KEYS) {
/*  826 */             this.info.getPdfObject().remove(deprecatedKey);
/*      */           }
/*      */         }
/*  829 */         if (getXmpMetadata() != null) {
/*  830 */           PdfStream xmp = this.catalog.getPdfObject().getAsStream(PdfName.Metadata);
/*  831 */           if (isAppendMode() && xmp != null && !xmp.isFlushed() && xmp.getIndirectReference() != null) {
/*      */             
/*  833 */             xmp.setData(this.xmpMetadata);
/*  834 */             xmp.setModified();
/*      */           } else {
/*      */             
/*  837 */             xmp = (PdfStream)(new PdfStream()).makeIndirect(this);
/*  838 */             xmp.getOutputStream().write(this.xmpMetadata);
/*  839 */             this.catalog.getPdfObject().put(PdfName.Metadata, xmp);
/*  840 */             this.catalog.setModified();
/*      */           } 
/*  842 */           xmp.put(PdfName.Type, PdfName.Metadata);
/*  843 */           xmp.put(PdfName.Subtype, PdfName.XML);
/*  844 */           if (this.writer.crypto != null && !this.writer.crypto.isMetadataEncrypted()) {
/*  845 */             PdfArray ar = new PdfArray();
/*  846 */             ar.add(PdfName.Crypt);
/*  847 */             xmp.put(PdfName.Filter, ar);
/*      */           } 
/*      */         } 
/*  850 */         checkIsoConformance();
/*      */         
/*  852 */         PdfObject crypto = null;
/*  853 */         Set<PdfIndirectReference> forbiddenToFlush = new HashSet<>();
/*  854 */         if (this.properties.appendMode) {
/*  855 */           if (this.structTreeRoot != null) {
/*  856 */             tryFlushTagStructure(true);
/*      */           }
/*  858 */           if (this.catalog.isOCPropertiesMayHaveChanged() && ((PdfDictionary)this.catalog.getOCProperties(false).getPdfObject()).isModified()) {
/*  859 */             this.catalog.getOCProperties(false).flush();
/*      */           }
/*  861 */           if (this.catalog.pageLabels != null) {
/*  862 */             this.catalog.put(PdfName.PageLabels, this.catalog.pageLabels.buildTree());
/*      */           }
/*      */           
/*  865 */           for (Map.Entry<PdfName, PdfNameTree> entry : this.catalog.nameTrees.entrySet()) {
/*  866 */             PdfNameTree tree = entry.getValue();
/*  867 */             if (tree.isModified()) {
/*  868 */               ensureTreeRootAddedToNames(tree.buildTree().makeIndirect(this), entry.getKey());
/*      */             }
/*      */           } 
/*      */           
/*  872 */           PdfObject pageRoot = this.catalog.getPageTree().generateTree();
/*  873 */           if (this.catalog.getPdfObject().isModified() || pageRoot.isModified()) {
/*  874 */             this.catalog.put(PdfName.Pages, pageRoot);
/*  875 */             this.catalog.getPdfObject().flush(false);
/*      */           } 
/*      */ 
/*      */           
/*  879 */           if (this.info.getPdfObject().isModified()) {
/*  880 */             this.info.getPdfObject().flush(false);
/*      */           }
/*  882 */           flushFonts();
/*      */ 
/*      */           
/*  885 */           if (this.writer.crypto != null) {
/*  886 */             assert this.reader.decrypt.getPdfObject() == this.writer.crypto.getPdfObject() : "Conflict with source encryption";
/*  887 */             crypto = this.reader.decrypt.getPdfObject();
/*  888 */             if (crypto.getIndirectReference() != null)
/*      */             {
/*  890 */               forbiddenToFlush.add(crypto.getIndirectReference());
/*      */             }
/*      */           } 
/*      */           
/*  894 */           this.writer.flushModifiedWaitingObjects(forbiddenToFlush);
/*  895 */           for (int i = 0; i < this.xref.size(); i++) {
/*  896 */             PdfIndirectReference indirectReference = this.xref.get(i);
/*  897 */             if (indirectReference != null && !indirectReference.isFree() && indirectReference
/*  898 */               .checkState((short)8) && !indirectReference.checkState((short)1) && 
/*  899 */               !forbiddenToFlush.contains(indirectReference)) {
/*  900 */               indirectReference.setFree();
/*      */             }
/*      */           } 
/*      */         } else {
/*  904 */           if (this.catalog.isOCPropertiesMayHaveChanged()) {
/*  905 */             this.catalog.getPdfObject().put(PdfName.OCProperties, this.catalog.getOCProperties(false).getPdfObject());
/*  906 */             this.catalog.getOCProperties(false).flush();
/*      */           } 
/*  908 */           if (this.catalog.pageLabels != null) {
/*  909 */             this.catalog.put(PdfName.PageLabels, this.catalog.pageLabels.buildTree());
/*      */           }
/*      */           
/*  912 */           this.catalog.getPdfObject().put(PdfName.Pages, this.catalog.getPageTree().generateTree());
/*      */           
/*  914 */           for (Map.Entry<PdfName, PdfNameTree> entry : this.catalog.nameTrees.entrySet()) {
/*  915 */             PdfNameTree tree = entry.getValue();
/*  916 */             if (tree.isModified()) {
/*  917 */               ensureTreeRootAddedToNames(tree.buildTree().makeIndirect(this), entry.getKey());
/*      */             }
/*      */           } 
/*      */           
/*  921 */           for (int pageNum = 1; pageNum <= getNumberOfPages(); pageNum++) {
/*  922 */             getPage(pageNum).flush();
/*      */           }
/*  924 */           if (this.structTreeRoot != null) {
/*  925 */             tryFlushTagStructure(false);
/*      */           }
/*  927 */           this.catalog.getPdfObject().flush(false);
/*  928 */           this.info.getPdfObject().flush(false);
/*  929 */           flushFonts();
/*      */           
/*  931 */           if (this.writer.crypto != null) {
/*  932 */             crypto = this.writer.crypto.getPdfObject();
/*  933 */             crypto.makeIndirect(this);
/*  934 */             forbiddenToFlush.add(crypto.getIndirectReference());
/*      */           } 
/*      */           
/*  937 */           this.writer.flushWaitingObjects(forbiddenToFlush);
/*  938 */           for (int i = 0; i < this.xref.size(); i++) {
/*  939 */             PdfIndirectReference indirectReference = this.xref.get(i);
/*  940 */             if (indirectReference != null && !indirectReference.isFree() && !indirectReference.checkState((short)1) && !forbiddenToFlush.contains(indirectReference)) {
/*      */               PdfObject object;
/*  942 */               if (isFlushUnusedObjects() && !indirectReference.checkState((short)16) && (object = indirectReference.getRefersTo(false)) != null) {
/*  943 */                 object.flush();
/*      */               } else {
/*  945 */                 indirectReference.setFree();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  953 */         this.writer.crypto = null;
/*      */         
/*  955 */         if (!this.properties.appendMode && crypto != null)
/*      */         {
/*  957 */           crypto.flush(false);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  963 */         this.trailer.put(PdfName.Root, this.catalog.getPdfObject());
/*  964 */         this.trailer.put(PdfName.Info, this.info.getPdfObject());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  969 */         PdfObject fileId = PdfEncryption.createInfoId(ByteUtils.getIsoBytes(this.originalDocumentId.getValue()), 
/*  970 */             ByteUtils.getIsoBytes(this.modifiedDocumentId.getValue()));
/*  971 */         this.xref.writeXrefTableAndTrailer(this, fileId, crypto);
/*  972 */         this.writer.flush();
/*  973 */         for (ICounter counter : getCounters()) {
/*  974 */           counter.onDocumentWritten(this.writer.getCurrentPos());
/*      */         }
/*      */       } 
/*  977 */       this.catalog.getPageTree().clearPageRefs();
/*  978 */       removeAllHandlers();
/*  979 */     } catch (IOException e) {
/*  980 */       throw new PdfException("Cannot close document.", e, this);
/*      */     } finally {
/*  982 */       if (this.writer != null && isCloseWriter()) {
/*      */         try {
/*  984 */           this.writer.close();
/*  985 */         } catch (Exception e) {
/*  986 */           Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/*  987 */           logger.error("PdfWriter closing failed due to the error occurred!", e);
/*      */         } 
/*      */       }
/*      */       
/*  991 */       if (this.reader != null && isCloseReader()) {
/*      */         try {
/*  993 */           this.reader.close();
/*  994 */         } catch (Exception e) {
/*  995 */           Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/*  996 */           logger.error("PdfReader closing failed due to the error occurred!", e);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1001 */     this.closed = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosed() {
/* 1010 */     return this.closed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTagged() {
/* 1019 */     return (this.structTreeRoot != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDocument setTagged() {
/* 1029 */     checkClosingStatus();
/* 1030 */     if (this.structTreeRoot == null) {
/* 1031 */       this.structTreeRoot = new PdfStructTreeRoot(this);
/* 1032 */       this.catalog.getPdfObject().put(PdfName.StructTreeRoot, this.structTreeRoot.getPdfObject());
/* 1033 */       updateValueInMarkInfoDict(PdfName.Marked, PdfBoolean.TRUE);
/*      */       
/* 1035 */       this.structParentIndex = 0;
/*      */     } 
/* 1037 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfStructTreeRoot getStructTreeRoot() {
/* 1048 */     return this.structTreeRoot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextStructParentIndex() {
/* 1059 */     return (this.structParentIndex < 0) ? -1 : this.structParentIndex++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TagStructureContext getTagStructureContext() {
/* 1069 */     checkClosingStatus();
/* 1070 */     if (this.tagStructureContext == null) {
/* 1071 */       if (!isTagged()) {
/* 1072 */         throw new PdfException("Must be a tagged document.");
/*      */       }
/*      */       
/* 1075 */       initTagStructureContext();
/*      */     } 
/*      */     
/* 1078 */     return this.tagStructureContext;
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
/*      */   public List<PdfPage> copyPagesTo(int pageFrom, int pageTo, PdfDocument toDocument, int insertBeforePage) {
/* 1098 */     return copyPagesTo(pageFrom, pageTo, toDocument, insertBeforePage, null);
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
/*      */   
/*      */   public List<PdfPage> copyPagesTo(int pageFrom, int pageTo, PdfDocument toDocument, int insertBeforePage, IPdfPageExtraCopier copier) {
/* 1122 */     List<Integer> pages = new ArrayList<>();
/* 1123 */     for (int i = pageFrom; i <= pageTo; i++) {
/* 1124 */       pages.add(Integer.valueOf(i));
/*      */     }
/* 1126 */     return copyPagesTo(pages, toDocument, insertBeforePage, copier);
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
/*      */   public List<PdfPage> copyPagesTo(int pageFrom, int pageTo, PdfDocument toDocument) {
/* 1146 */     return copyPagesTo(pageFrom, pageTo, toDocument, (IPdfPageExtraCopier)null);
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
/*      */   public List<PdfPage> copyPagesTo(int pageFrom, int pageTo, PdfDocument toDocument, IPdfPageExtraCopier copier) {
/* 1169 */     return copyPagesTo(pageFrom, pageTo, toDocument, toDocument.getNumberOfPages() + 1, copier);
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
/*      */   public List<PdfPage> copyPagesTo(List<Integer> pagesToCopy, PdfDocument toDocument, int insertBeforePage) {
/* 1188 */     return copyPagesTo(pagesToCopy, toDocument, insertBeforePage, (IPdfPageExtraCopier)null);
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
/*      */   public List<PdfPage> copyPagesTo(List<Integer> pagesToCopy, PdfDocument toDocument, int insertBeforePage, IPdfPageExtraCopier copier) {
/* 1210 */     if (pagesToCopy.isEmpty()) {
/* 1211 */       return Collections.emptyList();
/*      */     }
/*      */     
/* 1214 */     checkClosingStatus();
/* 1215 */     List<PdfPage> copiedPages = new ArrayList<>();
/* 1216 */     Map<PdfPage, PdfPage> page2page = new LinkedHashMap<>();
/* 1217 */     Set<PdfOutline> outlinesToCopy = new HashSet<>();
/*      */     
/* 1219 */     List<Map<PdfPage, PdfPage>> rangesOfPagesWithIncreasingNumbers = new ArrayList<>();
/* 1220 */     int lastCopiedPageNum = ((Integer)pagesToCopy.get(0)).intValue();
/*      */     
/* 1222 */     int pageInsertIndex = insertBeforePage;
/* 1223 */     boolean insertInBetween = (insertBeforePage < toDocument.getNumberOfPages() + 1);
/*      */     
/* 1225 */     for (Integer pageNum : pagesToCopy) {
/* 1226 */       PdfPage page = getPage(pageNum.intValue());
/* 1227 */       PdfPage newPage = page.copyTo(toDocument, copier);
/* 1228 */       copiedPages.add(newPage);
/* 1229 */       page2page.put(page, newPage);
/*      */ 
/*      */       
/* 1232 */       if (lastCopiedPageNum >= pageNum.intValue()) {
/* 1233 */         rangesOfPagesWithIncreasingNumbers.add(new HashMap<>());
/*      */       }
/* 1235 */       int lastRangeInd = rangesOfPagesWithIncreasingNumbers.size() - 1;
/* 1236 */       ((Map<PdfPage, PdfPage>)rangesOfPagesWithIncreasingNumbers.get(lastRangeInd)).put(page, newPage);
/*      */       
/* 1238 */       if (insertInBetween) {
/* 1239 */         toDocument.addPage(pageInsertIndex, newPage);
/*      */       } else {
/* 1241 */         toDocument.addPage(newPage);
/*      */       } 
/* 1243 */       pageInsertIndex++;
/* 1244 */       if (toDocument.hasOutlines()) {
/* 1245 */         List<PdfOutline> pageOutlines = page.getOutlines(false);
/* 1246 */         if (pageOutlines != null)
/* 1247 */           outlinesToCopy.addAll(pageOutlines); 
/*      */       } 
/* 1249 */       lastCopiedPageNum = pageNum.intValue();
/*      */     } 
/*      */     
/* 1252 */     copyLinkAnnotations(toDocument, page2page);
/*      */ 
/*      */     
/* 1255 */     if (getCatalog() != null && getCatalog().getPdfObject().getAsDictionary(PdfName.OCProperties) != null) {
/* 1256 */       OcgPropertiesCopier.copyOCGProperties(this, toDocument, page2page);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1261 */     if (toDocument.isTagged()) {
/* 1262 */       if (isTagged()) {
/*      */         try {
/* 1264 */           for (Map<PdfPage, PdfPage> increasingPagesRange : rangesOfPagesWithIncreasingNumbers) {
/* 1265 */             if (insertInBetween) {
/* 1266 */               getStructTreeRoot().copyTo(toDocument, insertBeforePage, increasingPagesRange);
/*      */             } else {
/* 1268 */               getStructTreeRoot().copyTo(toDocument, increasingPagesRange);
/*      */             } 
/* 1270 */             insertBeforePage += increasingPagesRange.size();
/*      */           } 
/* 1272 */           toDocument.getTagStructureContext().normalizeDocumentRootTag();
/* 1273 */         } catch (Exception ex) {
/* 1274 */           throw new PdfException("Tag structure copying failed: it might be corrupted in one of the documents.", ex);
/*      */         } 
/*      */       } else {
/* 1277 */         Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 1278 */         logger.warn("Not tagged pages are copied to the tagged document. Destination document now may contain not tagged content.");
/*      */       } 
/*      */     }
/* 1281 */     if (this.catalog.isOutlineMode()) {
/* 1282 */       copyOutlines(outlinesToCopy, toDocument, page2page);
/*      */     }
/* 1284 */     return copiedPages;
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
/*      */   public List<PdfPage> copyPagesTo(List<Integer> pagesToCopy, PdfDocument toDocument) {
/* 1302 */     return copyPagesTo(pagesToCopy, toDocument, (IPdfPageExtraCopier)null);
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
/*      */   public List<PdfPage> copyPagesTo(List<Integer> pagesToCopy, PdfDocument toDocument, IPdfPageExtraCopier copier) {
/* 1323 */     return copyPagesTo(pagesToCopy, toDocument, toDocument.getNumberOfPages() + 1, copier);
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
/*      */   public void flushCopiedObjects(PdfDocument sourceDoc) {
/* 1335 */     if (getWriter() != null) {
/* 1336 */       getWriter().flushCopiedObjects(sourceDoc.getDocumentId());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCloseReader() {
/* 1346 */     return this.closeReader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCloseReader(boolean closeReader) {
/* 1355 */     checkClosingStatus();
/* 1356 */     this.closeReader = closeReader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCloseWriter() {
/* 1365 */     return this.closeWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCloseWriter(boolean closeWriter) {
/* 1374 */     checkClosingStatus();
/* 1375 */     this.closeWriter = closeWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlushUnusedObjects() {
/* 1385 */     return this.flushUnusedObjects;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlushUnusedObjects(boolean flushUnusedObjects) {
/* 1395 */     checkClosingStatus();
/* 1396 */     this.flushUnusedObjects = flushUnusedObjects;
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
/*      */   public PdfOutline getOutlines(boolean updateOutlines) {
/* 1408 */     checkClosingStatus();
/* 1409 */     return this.catalog.getOutlines(updateOutlines);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initializeOutlines() {
/* 1416 */     checkClosingStatus();
/* 1417 */     getOutlines(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addNamedDestination(String key, PdfObject value) {
/* 1428 */     checkClosingStatus();
/* 1429 */     if (value.isArray() && ((PdfArray)value).get(0).isNumber())
/* 1430 */       LoggerFactory.getLogger(PdfDocument.class).warn("When destination's not associated with a Remote or Embedded Go-To action, it shall specify page dictionary instead of page number. Otherwise destination might be considered invalid"); 
/* 1431 */     this.catalog.addNamedDestination(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<PdfIndirectReference> listIndirectReferences() {
/* 1440 */     checkClosingStatus();
/* 1441 */     List<PdfIndirectReference> indRefs = new ArrayList<>(this.xref.size());
/* 1442 */     for (int i = 0; i < this.xref.size(); i++) {
/* 1443 */       PdfIndirectReference indref = this.xref.get(i);
/* 1444 */       if (indref != null) {
/* 1445 */         indRefs.add(indref);
/*      */       }
/*      */     } 
/* 1448 */     return indRefs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfDictionary getTrailer() {
/* 1457 */     checkClosingStatus();
/* 1458 */     return this.trailer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addOutputIntent(PdfOutputIntent outputIntent) {
/* 1469 */     checkClosingStatus();
/* 1470 */     if (outputIntent == null) {
/*      */       return;
/*      */     }
/* 1473 */     PdfArray outputIntents = this.catalog.getPdfObject().getAsArray(PdfName.OutputIntents);
/* 1474 */     if (outputIntents == null) {
/* 1475 */       outputIntents = new PdfArray();
/* 1476 */       this.catalog.put(PdfName.OutputIntents, outputIntents);
/*      */     } 
/* 1478 */     outputIntents.add(outputIntent.getPdfObject());
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
/*      */   public void checkIsoConformance(Object obj, IsoKey key) {}
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
/*      */   @Deprecated
/*      */   public void checkIsoConformance(Object obj, IsoKey key, PdfResources resources) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkIsoConformance(Object obj, IsoKey key, PdfResources resources, PdfStream contentStream) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkShowTextIsoConformance(CanvasGraphicsState gState, PdfResources resources) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFileAttachment(String key, PdfFileSpec fs) {
/* 1533 */     checkClosingStatus();
/* 1534 */     this.catalog.addNameToNameTree(key, fs.getPdfObject(), PdfName.EmbeddedFiles);
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
/*      */   public void addAssociatedFile(String description, PdfFileSpec fs) {
/* 1551 */     if (null == ((PdfDictionary)fs.getPdfObject()).get(PdfName.AFRelationship)) {
/* 1552 */       Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 1553 */       logger.error("For associated files their associated file specification dictionaries shall include the AFRelationship key.");
/*      */     } 
/*      */     
/* 1556 */     PdfArray afArray = this.catalog.getPdfObject().getAsArray(PdfName.AF);
/* 1557 */     if (afArray == null) {
/* 1558 */       afArray = (PdfArray)(new PdfArray()).makeIndirect(this);
/* 1559 */       this.catalog.put(PdfName.AF, afArray);
/*      */     } 
/* 1561 */     afArray.add(fs.getPdfObject());
/*      */     
/* 1563 */     addFileAttachment(description, fs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfArray getAssociatedFiles() {
/* 1572 */     checkClosingStatus();
/* 1573 */     return this.catalog.getPdfObject().getAsArray(PdfName.AF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfEncryptedPayloadDocument getEncryptedPayloadDocument() {
/* 1583 */     if (getReader() != null && getReader().isEncrypted()) {
/* 1584 */       return null;
/*      */     }
/* 1586 */     PdfCollection collection = getCatalog().getCollection();
/* 1587 */     if (collection != null && collection.isViewHidden()) {
/* 1588 */       PdfString documentName = collection.getInitialDocument();
/* 1589 */       PdfNameTree embeddedFiles = getCatalog().getNameTree(PdfName.EmbeddedFiles);
/* 1590 */       String documentNameUnicode = documentName.toUnicodeString();
/* 1591 */       PdfObject fileSpecObject = embeddedFiles.getNames().get(documentNameUnicode);
/* 1592 */       if (fileSpecObject != null && fileSpecObject.isDictionary()) {
/*      */         try {
/* 1594 */           PdfFileSpec fileSpec = PdfEncryptedPayloadFileSpecFactory.wrap((PdfDictionary)fileSpecObject);
/* 1595 */           if (fileSpec != null) {
/* 1596 */             PdfDictionary embeddedDictionary = ((PdfDictionary)fileSpec.getPdfObject()).getAsDictionary(PdfName.EF);
/* 1597 */             PdfStream stream = embeddedDictionary.getAsStream(PdfName.UF);
/* 1598 */             if (stream == null) {
/* 1599 */               stream = embeddedDictionary.getAsStream(PdfName.F);
/*      */             }
/* 1601 */             if (stream != null) {
/* 1602 */               return new PdfEncryptedPayloadDocument(stream, fileSpec, documentNameUnicode);
/*      */             }
/*      */           } 
/* 1605 */         } catch (PdfException e) {
/* 1606 */           LoggerFactory.getLogger(getClass()).error(e.getMessage());
/*      */         } 
/*      */       }
/*      */     } 
/* 1610 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncryptedPayload(PdfFileSpec fs) {
/* 1621 */     if (getWriter() == null) {
/* 1622 */       throw new PdfException("Cannot set encrypted payload to a document opened in read only mode.");
/*      */     }
/* 1624 */     if (writerHasEncryption()) {
/* 1625 */       throw new PdfException("Cannot set encrypted payload to an encrypted document.");
/*      */     }
/* 1627 */     if (!PdfName.EncryptedPayload.equals(((PdfDictionary)fs.getPdfObject()).get(PdfName.AFRelationship))) {
/* 1628 */       LoggerFactory.getLogger(getClass()).error("Encrypted payload file spec shall have 'AFRelationship' filed equal to 'EncryptedPayload'");
/*      */     }
/* 1630 */     PdfEncryptedPayload encryptedPayload = PdfEncryptedPayload.extractFrom(fs);
/* 1631 */     if (encryptedPayload == null) {
/* 1632 */       throw new PdfException("Encrypted payload file spec shall have encrypted payload dictionary.");
/*      */     }
/* 1634 */     PdfCollection collection = getCatalog().getCollection();
/* 1635 */     if (collection != null) {
/* 1636 */       LoggerFactory.getLogger(getClass()).warn("Collection dictionary already exists. It will be modified.");
/*      */     } else {
/* 1638 */       collection = new PdfCollection();
/* 1639 */       getCatalog().setCollection(collection);
/*      */     } 
/* 1641 */     collection.setView(2);
/* 1642 */     String displayName = PdfEncryptedPayloadFileSpecFactory.generateFileDisplay(encryptedPayload);
/* 1643 */     collection.setInitialDocument(displayName);
/* 1644 */     addAssociatedFile(displayName, fs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getPageLabels() {
/* 1653 */     if (this.catalog.getPageLabelsTree(false) == null) {
/* 1654 */       return null;
/*      */     }
/* 1656 */     Map<Integer, PdfObject> pageLabels = this.catalog.getPageLabelsTree(false).getNumbers();
/* 1657 */     if (pageLabels.size() == 0) {
/* 1658 */       return null;
/*      */     }
/* 1660 */     String[] labelStrings = new String[getNumberOfPages()];
/* 1661 */     int pageCount = 1;
/* 1662 */     String prefix = "";
/* 1663 */     String type = "D";
/* 1664 */     for (int i = 0; i < getNumberOfPages(); i++) {
/* 1665 */       if (pageLabels.containsKey(Integer.valueOf(i))) {
/* 1666 */         PdfDictionary labelDictionary = (PdfDictionary)pageLabels.get(Integer.valueOf(i));
/* 1667 */         PdfNumber pageRange = labelDictionary.getAsNumber(PdfName.St);
/* 1668 */         if (pageRange != null) {
/* 1669 */           pageCount = pageRange.intValue();
/*      */         } else {
/* 1671 */           pageCount = 1;
/*      */         } 
/* 1673 */         PdfString p = labelDictionary.getAsString(PdfName.P);
/* 1674 */         if (p != null) {
/* 1675 */           prefix = p.toUnicodeString();
/*      */         } else {
/* 1677 */           prefix = "";
/*      */         } 
/* 1679 */         PdfName t = labelDictionary.getAsName(PdfName.S);
/* 1680 */         if (t != null) {
/* 1681 */           type = t.getValue();
/*      */         } else {
/* 1683 */           type = "e";
/*      */         } 
/*      */       } 
/* 1686 */       switch (type) {
/*      */         case "R":
/* 1688 */           labelStrings[i] = prefix + RomanNumbering.toRomanUpperCase(pageCount);
/*      */           break;
/*      */         case "r":
/* 1691 */           labelStrings[i] = prefix + RomanNumbering.toRomanLowerCase(pageCount);
/*      */           break;
/*      */         case "A":
/* 1694 */           labelStrings[i] = prefix + EnglishAlphabetNumbering.toLatinAlphabetNumberUpperCase(pageCount);
/*      */           break;
/*      */         case "a":
/* 1697 */           labelStrings[i] = prefix + EnglishAlphabetNumbering.toLatinAlphabetNumberLowerCase(pageCount);
/*      */           break;
/*      */         case "e":
/* 1700 */           labelStrings[i] = prefix;
/*      */           break;
/*      */         default:
/* 1703 */           labelStrings[i] = prefix + pageCount;
/*      */           break;
/*      */       } 
/* 1706 */       pageCount++;
/*      */     } 
/* 1708 */     return labelStrings;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasOutlines() {
/* 1717 */     return this.catalog.hasOutlines();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserProperties(boolean userProperties) {
/* 1726 */     PdfBoolean userPropsVal = userProperties ? PdfBoolean.TRUE : PdfBoolean.FALSE;
/* 1727 */     updateValueInMarkInfoDict(PdfName.UserProperties, userPropsVal);
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
/*      */   public PdfFont getFont(PdfDictionary dictionary) {
/* 1741 */     assert dictionary.getIndirectReference() != null;
/* 1742 */     if (this.documentFonts.containsKey(dictionary.getIndirectReference())) {
/* 1743 */       return this.documentFonts.get(dictionary.getIndirectReference());
/*      */     }
/* 1745 */     return addFont(PdfFontFactory.createFont(dictionary));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PdfFont getDefaultFont() {
/* 1756 */     if (this.defaultFont == null) {
/*      */       try {
/* 1758 */         this.defaultFont = PdfFontFactory.createFont();
/* 1759 */         if (this.writer != null) this.defaultFont.makeIndirect(this); 
/* 1760 */       } catch (IOException e) {
/* 1761 */         Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 1762 */         logger.error("Exception while creating default font (Helvetica, WinAnsi)", e);
/* 1763 */         this.defaultFont = null;
/*      */       } 
/*      */     }
/* 1766 */     return this.defaultFont;
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
/*      */   public PdfFont addFont(PdfFont font) {
/* 1778 */     font.makeIndirect(this);
/*      */     
/* 1780 */     font.setForbidRelease();
/* 1781 */     this.documentFonts.put(((PdfDictionary)font.getPdfObject()).getIndirectReference(), font);
/* 1782 */     return font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean registerProduct(ProductInfo productInfo) {
/* 1792 */     return this.fingerPrint.registerProduct(productInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FingerPrint getFingerPrint() {
/* 1801 */     return this.fingerPrint;
/*      */   }
/*      */   
/*      */   public PdfFont findFont(String fontProgram, String encoding) {
/* 1805 */     for (PdfFont font : this.documentFonts.values()) {
/* 1806 */       if (!font.isFlushed() && font.isBuiltWith(fontProgram, encoding))
/* 1807 */         return font; 
/*      */     } 
/* 1809 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PdfXrefTable getXref() {
/* 1818 */     return this.xref;
/*      */   }
/*      */   
/*      */   boolean isDocumentFont(PdfIndirectReference indRef) {
/* 1822 */     return (indRef != null && this.documentFonts.containsKey(indRef));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initTagStructureContext() {
/* 1829 */     this.tagStructureContext = new TagStructureContext(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void storeLinkAnnotation(PdfPage page, PdfLinkAnnotation annotation) {
/* 1839 */     List<PdfLinkAnnotation> pageAnnotations = this.linkAnnotations.get(page);
/* 1840 */     if (pageAnnotations == null) {
/* 1841 */       pageAnnotations = new ArrayList<>();
/* 1842 */       this.linkAnnotations.put(page, pageAnnotations);
/*      */     } 
/* 1844 */     pageAnnotations.add(annotation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkIsoConformance() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void markObjectAsMustBeFlushed(PdfObject pdfObject) {
/* 1860 */     if (pdfObject.getIndirectReference() != null) {
/* 1861 */       pdfObject.getIndirectReference().setState((short)32);
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
/*      */   protected void flushObject(PdfObject pdfObject, boolean canBeInObjStm) throws IOException {
/* 1873 */     this.writer.flushObject(pdfObject, canBeInObjStm);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void open(PdfVersion newPdfVersion) {
/* 1883 */     this.fingerPrint = new FingerPrint();
/*      */     
/*      */     try {
/* 1886 */       EventCounterHandler.getInstance().onEvent((IEvent)CoreEvent.PROCESS, this.properties.metaInfo, getClass());
/* 1887 */       if (this.reader != null) {
/* 1888 */         if (this.reader.pdfDocument != null) {
/* 1889 */           throw new PdfException("Given PdfReader instance has already been utilized. The PdfReader cannot be reused, please create a new instance.");
/*      */         }
/* 1891 */         this.reader.pdfDocument = this;
/* 1892 */         this.memoryLimitsAwareHandler = this.reader.properties.memoryLimitsAwareHandler;
/* 1893 */         if (null == this.memoryLimitsAwareHandler) {
/* 1894 */           this.memoryLimitsAwareHandler = new MemoryLimitsAwareHandler(this.reader.tokens.getSafeFile().length());
/*      */         }
/* 1896 */         this.reader.readPdf();
/* 1897 */         for (ICounter counter : getCounters()) {
/* 1898 */           counter.onDocumentRead(this.reader.getFileLength());
/*      */         }
/* 1900 */         this.pdfVersion = this.reader.headerPdfVersion;
/* 1901 */         this.trailer = new PdfDictionary(this.reader.trailer);
/*      */         
/* 1903 */         PdfArray id = this.reader.trailer.getAsArray(PdfName.ID);
/*      */         
/* 1905 */         if (id != null) {
/* 1906 */           if (id.size() == 2) {
/* 1907 */             this.originalDocumentId = id.getAsString(0);
/* 1908 */             this.modifiedDocumentId = id.getAsString(1);
/*      */           } 
/*      */           
/* 1911 */           if (this.originalDocumentId == null || this.modifiedDocumentId == null) {
/* 1912 */             Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 1913 */             logger.error("The document original and/or modified id is corrupted");
/*      */           } 
/*      */         } 
/*      */         
/* 1917 */         this.catalog = new PdfCatalog((PdfDictionary)this.trailer.get(PdfName.Root, true));
/* 1918 */         if (this.catalog.getPdfObject().containsKey(PdfName.Version)) {
/*      */ 
/*      */           
/* 1921 */           PdfVersion catalogVersion = PdfVersion.fromPdfName(this.catalog.getPdfObject().getAsName(PdfName.Version));
/* 1922 */           if (catalogVersion.compareTo(this.pdfVersion) > 0) {
/* 1923 */             this.pdfVersion = catalogVersion;
/*      */           }
/*      */         } 
/* 1926 */         PdfStream xmpMetadataStream = this.catalog.getPdfObject().getAsStream(PdfName.Metadata);
/* 1927 */         if (xmpMetadataStream != null) {
/* 1928 */           this.xmpMetadata = xmpMetadataStream.getBytes();
/*      */           try {
/* 1930 */             this.reader.pdfAConformanceLevel = PdfAConformanceLevel.getConformanceLevel(XMPMetaFactory.parseFromBuffer(this.xmpMetadata));
/* 1931 */           } catch (XMPException xMPException) {}
/*      */         } 
/*      */         
/* 1934 */         PdfObject infoDict = this.trailer.get(PdfName.Info);
/* 1935 */         this.info = new PdfDocumentInfo((infoDict instanceof PdfDictionary) ? (PdfDictionary)infoDict : new PdfDictionary(), this);
/* 1936 */         XmpMetaInfoConverter.appendMetadataToInfo(this.xmpMetadata, this.info);
/*      */         
/* 1938 */         PdfDictionary str = this.catalog.getPdfObject().getAsDictionary(PdfName.StructTreeRoot);
/* 1939 */         if (str != null) {
/* 1940 */           tryInitTagStructure(str);
/*      */         }
/* 1942 */         if (this.properties.appendMode && (this.reader.hasRebuiltXref() || this.reader.hasFixedXref()))
/* 1943 */           throw new PdfException("Append mode requires a document without errors, even if recovery is possible."); 
/*      */       } 
/* 1945 */       this.xref.initFreeReferencesList(this);
/* 1946 */       if (this.writer != null) {
/* 1947 */         if (this.reader != null && this.reader.hasXrefStm() && this.writer.properties.isFullCompression == null) {
/* 1948 */           this.writer.properties.isFullCompression = Boolean.valueOf(true);
/*      */         }
/* 1950 */         if (this.reader != null && !this.reader.isOpenedWithFullPermission()) {
/* 1951 */           throw new BadPasswordException("PdfReader is not opened with owner password");
/*      */         }
/* 1953 */         if (this.reader != null && this.properties.preserveEncryption) {
/* 1954 */           this.writer.crypto = this.reader.decrypt;
/*      */         }
/* 1956 */         this.writer.document = this;
/* 1957 */         if (this.reader == null) {
/* 1958 */           this.catalog = new PdfCatalog(this);
/* 1959 */           this.info = (new PdfDocumentInfo(this)).addCreationDate();
/*      */         } 
/* 1961 */         updateProducerInInfoDictionary();
/* 1962 */         this.info.addModDate();
/* 1963 */         this.trailer = new PdfDictionary();
/* 1964 */         this.trailer.put(PdfName.Root, this.catalog.getPdfObject().getIndirectReference());
/* 1965 */         this.trailer.put(PdfName.Info, this.info.getPdfObject().getIndirectReference());
/*      */         
/* 1967 */         if (this.reader != null)
/*      */         {
/* 1969 */           if (this.reader.trailer.containsKey(PdfName.ID)) {
/* 1970 */             this.trailer.put(PdfName.ID, this.reader.trailer.get(PdfName.ID));
/*      */           }
/*      */         }
/*      */         
/* 1974 */         if (this.writer.properties != null) {
/* 1975 */           PdfString readerModifiedId = this.modifiedDocumentId;
/* 1976 */           if (this.writer.properties.initialDocumentId != null && (this.reader == null || this.reader.decrypt == null || (!this.properties.appendMode && !this.properties.preserveEncryption)))
/*      */           {
/* 1978 */             this.originalDocumentId = this.writer.properties.initialDocumentId;
/*      */           }
/* 1980 */           if (this.writer.properties.modifiedDocumentId != null) {
/* 1981 */             this.modifiedDocumentId = this.writer.properties.modifiedDocumentId;
/*      */           }
/* 1983 */           if (this.originalDocumentId == null && this.modifiedDocumentId != null) {
/* 1984 */             this.originalDocumentId = this.modifiedDocumentId;
/*      */           }
/* 1986 */           if (this.modifiedDocumentId == null) {
/* 1987 */             if (this.originalDocumentId == null) {
/* 1988 */               this.originalDocumentId = new PdfString(PdfEncryption.generateNewDocumentId());
/*      */             }
/* 1990 */             this.modifiedDocumentId = this.originalDocumentId;
/*      */           } 
/* 1992 */           if (this.writer.properties.modifiedDocumentId == null && this.modifiedDocumentId.equals(readerModifiedId)) {
/* 1993 */             this.modifiedDocumentId = new PdfString(PdfEncryption.generateNewDocumentId());
/*      */           }
/*      */         } 
/*      */         
/* 1997 */         assert this.originalDocumentId != null;
/* 1998 */         assert this.modifiedDocumentId != null;
/*      */       } 
/* 2000 */       if (this.properties.appendMode) {
/*      */         
/* 2002 */         assert this.reader != null;
/* 2003 */         RandomAccessFileOrArray file = this.reader.tokens.getSafeFile();
/*      */         
/* 2005 */         byte[] buffer = new byte[8192]; int n;
/* 2006 */         while ((n = file.read(buffer)) > 0) {
/* 2007 */           this.writer.write(buffer, 0, n);
/*      */         }
/* 2009 */         file.close();
/* 2010 */         this.writer.write(10);
/*      */         
/* 2012 */         overrideFullCompressionInWriterProperties(this.writer.properties, this.reader.hasXrefStm());
/*      */         
/* 2014 */         this.writer.crypto = this.reader.decrypt;
/*      */         
/* 2016 */         if (newPdfVersion != null)
/*      */         {
/*      */           
/* 2019 */           if (this.pdfVersion.compareTo(PdfVersion.PDF_1_4) >= 0)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 2024 */             if (newPdfVersion.compareTo(this.reader.headerPdfVersion) > 0) {
/* 2025 */               this.catalog.put(PdfName.Version, newPdfVersion.toPdfName());
/* 2026 */               this.catalog.setModified();
/* 2027 */               this.pdfVersion = newPdfVersion;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         }
/*      */       }
/* 2034 */       else if (this.writer != null) {
/* 2035 */         if (newPdfVersion != null) {
/* 2036 */           this.pdfVersion = newPdfVersion;
/*      */         }
/* 2038 */         this.writer.writeHeader();
/*      */         
/* 2040 */         if (this.writer.crypto == null) {
/* 2041 */           this.writer.initCryptoIfSpecified(this.pdfVersion);
/*      */         }
/* 2043 */         if (this.writer.crypto != null) {
/* 2044 */           if (this.writer.crypto.getCryptoMode() < 3) {
/* 2045 */             VersionConforming.validatePdfVersionForDeprecatedFeatureLogWarn(this, PdfVersion.PDF_2_0, "Encryption algorithms STANDARD_ENCRYPTION_40, STANDARD_ENCRYPTION_128 and ENCRYPTION_AES_128 (see com.itextpdf.kernel.pdf.EncryptionConstants) are deprecated in PDF 2.0. It is highly recommended not to use it.");
/* 2046 */           } else if (this.writer.crypto.getCryptoMode() == 3) {
/* 2047 */             PdfNumber r = this.writer.crypto.getPdfObject().getAsNumber(PdfName.R);
/* 2048 */             if (r != null && r.intValue() == 5) {
/* 2049 */               VersionConforming.validatePdfVersionForDeprecatedFeatureLogWarn(this, PdfVersion.PDF_2_0, "It seems that PDF 1.7 document encrypted with AES256 was updated to PDF 2.0 version and StampingProperties#preserveEncryption flag was set: encryption shall be updated via WriterProperties#setStandardEncryption method. Standard security handler was found with revision 5, which is deprecated and shall not be used in PDF 2.0 documents.");
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/* 2054 */     } catch (IOException e) {
/* 2055 */       throw new PdfException("Cannot open document.", e, this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addCustomMetadataExtensions(XMPMeta xmpMeta) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateXmpMetadata() {
/*      */     try {
/* 2074 */       if (this.xmpMetadata != null || this.writer.properties.addXmpMetadata || this.pdfVersion.compareTo(PdfVersion.PDF_2_0) >= 0) {
/* 2075 */         setXmpMetadata(updateDefaultXmpMetadata());
/*      */       }
/* 2077 */     } catch (XMPException e) {
/* 2078 */       Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 2079 */       logger.error("Exception while updating XmpMetadata", (Throwable)e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMPMeta updateDefaultXmpMetadata() throws XMPException {
/* 2090 */     XMPMeta xmpMeta = XMPMetaFactory.parseFromBuffer(getXmpMetadata(true));
/* 2091 */     XmpMetaInfoConverter.appendDocumentInfoToMetadata(this.info, xmpMeta);
/*      */     
/* 2093 */     if (isTagged() && this.writer.properties.addUAXmpMetadata && !isXmpMetaHasProperty(xmpMeta, "http://www.aiim.org/pdfua/ns/id/", "part")) {
/* 2094 */       xmpMeta.setPropertyInteger("http://www.aiim.org/pdfua/ns/id/", "part", 1, new PropertyOptions(1073741824));
/*      */     }
/*      */     
/* 2097 */     return xmpMeta;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Collection<PdfFont> getDocumentFonts() {
/* 2106 */     return this.documentFonts.values();
/*      */   }
/*      */   
/*      */   protected void flushFonts() {
/* 2110 */     if (this.properties.appendMode) {
/* 2111 */       for (PdfFont font : getDocumentFonts()) {
/* 2112 */         if (((PdfDictionary)font.getPdfObject()).checkState((short)64) || ((PdfDictionary)font.getPdfObject()).getIndirectReference().checkState((short)8)) {
/* 2113 */           font.flush();
/*      */         }
/*      */       } 
/*      */     } else {
/* 2117 */       for (PdfFont font : getDocumentFonts()) {
/* 2118 */         font.flush();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkAndAddPage(int index, PdfPage page) {
/* 2130 */     if (page.isFlushed()) {
/* 2131 */       throw new PdfException("Flushed page cannot be added or inserted.", page);
/*      */     }
/* 2133 */     if (page.getDocument() != null && this != page.getDocument()) {
/* 2134 */       throw (new PdfException("Page {0} cannot be added to document {1}, because it belongs to document {2}.")).setMessageParams(new Object[] { page, this, page.getDocument() });
/*      */     }
/* 2136 */     this.catalog.getPageTree().addPage(index, page);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkAndAddPage(PdfPage page) {
/* 2145 */     if (page.isFlushed())
/* 2146 */       throw new PdfException("Flushed page cannot be added or inserted.", page); 
/* 2147 */     if (page.getDocument() != null && this != page.getDocument())
/* 2148 */       throw (new PdfException("Page {0} cannot be added to document {1}, because it belongs to document {2}.")).setMessageParams(new Object[] { page, this, page.getDocument() }); 
/* 2149 */     this.catalog.getPageTree().addPage(page);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkClosingStatus() {
/* 2156 */     if (this.closed) {
/* 2157 */       throw new PdfException("Document was closed. It is impossible to execute action.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected List<ICounter> getCounters() {
/* 2167 */     return CounterManager.getInstance().getCounters(PdfDocument.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IPdfPageFactory getPageFactory() {
/* 2176 */     return pdfPageFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final VersionInfo getVersionInfo() {
/* 2185 */     return this.versionInfo;
/*      */   }
/*      */   
/*      */   boolean hasAcroForm() {
/* 2189 */     return getCatalog().getPdfObject().containsKey(PdfName.AcroForm);
/*      */   }
/*      */   
/*      */   private void updateProducerInInfoDictionary() {
/* 2193 */     String producer = null;
/* 2194 */     if (this.reader == null) {
/* 2195 */       producer = this.versionInfo.getVersion();
/*      */     } else {
/* 2197 */       if (this.info.getPdfObject().containsKey(PdfName.Producer)) {
/* 2198 */         producer = this.info.getPdfObject().getAsString(PdfName.Producer).toUnicodeString();
/*      */       }
/* 2200 */       producer = addModifiedPostfix(producer);
/*      */     } 
/* 2202 */     this.info.getPdfObject().put(PdfName.Producer, new PdfString(producer));
/*      */   }
/*      */   
/*      */   protected void tryInitTagStructure(PdfDictionary str) {
/*      */     try {
/* 2207 */       this.structTreeRoot = new PdfStructTreeRoot(str, this);
/* 2208 */       this.structParentIndex = getStructTreeRoot().getParentTreeNextKey();
/* 2209 */     } catch (Exception ex) {
/* 2210 */       this.structTreeRoot = null;
/* 2211 */       this.structParentIndex = -1;
/* 2212 */       Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 2213 */       logger.error("Tag structure initialization failed, tag structure is ignored, it might be corrupted.", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void tryFlushTagStructure(boolean isAppendMode) {
/*      */     try {
/* 2219 */       if (this.tagStructureContext != null) {
/* 2220 */         this.tagStructureContext.prepareToDocumentClosing();
/*      */       }
/* 2222 */       if (!isAppendMode || ((PdfDictionary)this.structTreeRoot.getPdfObject()).isModified()) {
/* 2223 */         this.structTreeRoot.flush();
/*      */       }
/* 2225 */     } catch (Exception ex) {
/* 2226 */       throw new PdfException("Tag structure flushing failed: it might be corrupted.", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateValueInMarkInfoDict(PdfName key, PdfObject value) {
/* 2231 */     PdfDictionary markInfo = this.catalog.getPdfObject().getAsDictionary(PdfName.MarkInfo);
/* 2232 */     if (markInfo == null) {
/* 2233 */       markInfo = new PdfDictionary();
/* 2234 */       this.catalog.getPdfObject().put(PdfName.MarkInfo, markInfo);
/*      */     } 
/* 2236 */     markInfo.put(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeUnusedWidgetsFromFields(PdfPage page) {
/* 2245 */     if (page.isFlushed()) {
/*      */       return;
/*      */     }
/* 2248 */     List<PdfAnnotation> annots = page.getAnnotations();
/* 2249 */     for (PdfAnnotation annot : annots) {
/* 2250 */       if (annot.getSubtype().equals(PdfName.Widget)) {
/* 2251 */         ((PdfWidgetAnnotation)annot).releaseFormFieldFromWidgetAnnotation();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void copyLinkAnnotations(PdfDocument toDocument, Map<PdfPage, PdfPage> page2page) {
/* 2257 */     List<PdfName> excludedKeys = new ArrayList<>();
/* 2258 */     excludedKeys.add(PdfName.Dest);
/* 2259 */     excludedKeys.add(PdfName.A);
/* 2260 */     for (Map.Entry<PdfPage, List<PdfLinkAnnotation>> entry : this.linkAnnotations.entrySet()) {
/*      */       
/* 2262 */       for (PdfLinkAnnotation annot : entry.getValue()) {
/* 2263 */         boolean toCopyAnnot = true;
/* 2264 */         PdfDestination copiedDest = null;
/* 2265 */         PdfDictionary copiedAction = null;
/*      */         
/* 2267 */         PdfObject dest = annot.getDestinationObject();
/* 2268 */         if (dest != null) {
/*      */ 
/*      */ 
/*      */           
/* 2272 */           copiedDest = getCatalog().copyDestination(dest, page2page, toDocument);
/* 2273 */           toCopyAnnot = (copiedDest != null);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2278 */           PdfDictionary action = annot.getAction();
/* 2279 */           if (action != null) {
/* 2280 */             if (PdfName.GoTo.equals(action.get(PdfName.S))) {
/* 2281 */               copiedAction = action.copyTo(toDocument, Arrays.asList(new PdfName[] { PdfName.D }, ), false);
/* 2282 */               PdfDestination goToDest = getCatalog().copyDestination(action.get(PdfName.D), page2page, toDocument);
/* 2283 */               if (goToDest != null) {
/* 2284 */                 copiedAction.put(PdfName.D, goToDest.getPdfObject());
/*      */               } else {
/* 2286 */                 toCopyAnnot = false;
/*      */               } 
/*      */             } else {
/* 2289 */               copiedAction = (PdfDictionary)action.copyTo(toDocument, false);
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/* 2294 */         if (toCopyAnnot) {
/* 2295 */           PdfLinkAnnotation newAnnot = (PdfLinkAnnotation)PdfAnnotation.makeAnnotation(((PdfDictionary)annot.getPdfObject()).copyTo(toDocument, excludedKeys, true));
/* 2296 */           if (copiedDest != null) {
/* 2297 */             newAnnot.setDestination(copiedDest);
/*      */           }
/* 2299 */           if (copiedAction != null) {
/* 2300 */             newAnnot.setAction(copiedAction);
/*      */           }
/* 2302 */           ((PdfPage)entry.getKey()).addAnnotation(-1, (PdfAnnotation)newAnnot, false);
/*      */         } 
/*      */       } 
/*      */     } 
/* 2306 */     this.linkAnnotations.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void copyOutlines(Set<PdfOutline> outlines, PdfDocument toDocument, Map<PdfPage, PdfPage> page2page) {
/* 2317 */     Set<PdfOutline> outlinesToCopy = new HashSet<>();
/* 2318 */     outlinesToCopy.addAll(outlines);
/*      */     
/* 2320 */     for (PdfOutline outline : outlines) {
/* 2321 */       getAllOutlinesToCopy(outline, outlinesToCopy);
/*      */     }
/*      */     
/* 2324 */     PdfOutline rootOutline = toDocument.getOutlines(false);
/* 2325 */     if (rootOutline == null) {
/* 2326 */       rootOutline = new PdfOutline(toDocument);
/* 2327 */       rootOutline.setTitle("Outlines");
/*      */     } 
/*      */     
/* 2330 */     cloneOutlines(outlinesToCopy, rootOutline, getOutlines(false), page2page, toDocument);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getAllOutlinesToCopy(PdfOutline outline, Set<PdfOutline> outlinesToCopy) {
/* 2340 */     PdfOutline parent = outline.getParent();
/*      */ 
/*      */     
/* 2343 */     if ("Outlines".equals(parent.getTitle()) || outlinesToCopy.contains(parent)) {
/*      */       return;
/*      */     }
/* 2346 */     outlinesToCopy.add(parent);
/* 2347 */     getAllOutlinesToCopy(parent, outlinesToCopy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cloneOutlines(Set<PdfOutline> outlinesToCopy, PdfOutline newParent, PdfOutline oldParent, Map<PdfPage, PdfPage> page2page, PdfDocument toDocument) {
/* 2358 */     if (null == oldParent) {
/*      */       return;
/*      */     }
/* 2361 */     for (PdfOutline outline : oldParent.getAllChildren()) {
/* 2362 */       if (outlinesToCopy.contains(outline)) {
/* 2363 */         PdfDestination copiedDest = null;
/* 2364 */         if (null != outline.getDestination()) {
/* 2365 */           PdfObject destObjToCopy = outline.getDestination().getPdfObject();
/* 2366 */           copiedDest = getCatalog().copyDestination(destObjToCopy, page2page, toDocument);
/*      */         } 
/* 2368 */         PdfOutline child = newParent.addOutline(outline.getTitle());
/* 2369 */         if (copiedDest != null) {
/* 2370 */           child.addDestination(copiedDest);
/*      */         }
/*      */         
/* 2373 */         cloneOutlines(outlinesToCopy, child, outline, page2page, toDocument);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void ensureTreeRootAddedToNames(PdfObject treeRoot, PdfName treeType) {
/* 2379 */     PdfDictionary names = this.catalog.getPdfObject().getAsDictionary(PdfName.Names);
/* 2380 */     if (names == null) {
/* 2381 */       names = new PdfDictionary();
/* 2382 */       this.catalog.put(PdfName.Names, names);
/* 2383 */       names.makeIndirect(this);
/*      */     } 
/* 2385 */     names.put(treeType, treeRoot);
/* 2386 */     names.setModified();
/*      */   }
/*      */ 
/*      */   
/*      */   private byte[] getSerializedBytes() {
/* 2391 */     ByteArrayOutputStream bos = null;
/* 2392 */     ObjectOutputStream oos = null;
/*      */     try {
/* 2394 */       bos = new ByteArrayOutputStream();
/* 2395 */       oos = new ObjectOutputStream((OutputStream)bos);
/* 2396 */       oos.writeObject(this);
/* 2397 */       oos.flush();
/* 2398 */       return bos.toByteArray();
/* 2399 */     } catch (Exception e) {
/* 2400 */       Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 2401 */       logger.warn("Unhandled exception while serialization", e);
/* 2402 */       return null;
/*      */     } finally {
/* 2404 */       if (oos != null) {
/*      */         try {
/* 2406 */           oos.close();
/* 2407 */         } catch (IOException iOException) {}
/*      */       }
/*      */       
/* 2410 */       if (bos != null) {
/*      */         try {
/* 2412 */           bos.close();
/* 2413 */         } catch (IOException iOException) {}
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private long getDocumentId() {
/* 2420 */     return this.documentId;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 2424 */     if (this.tagStructureContext != null) {
/* 2425 */       LoggerFactory.getLogger(getClass()).warn("Tag structure context is not null and will be reinitialized in the copy of document. The copy may lose some data");
/*      */     }
/* 2427 */     out.defaultWriteObject();
/*      */   }
/*      */   
/*      */   private boolean writerHasEncryption() {
/* 2431 */     return (this.writer.properties.isStandardEncryptionUsed() || this.writer.properties.isPublicKeyEncryptionUsed());
/*      */   }
/*      */ 
/*      */   
/*      */   static class IndirectRefDescription
/*      */   {
/*      */     final long docId;
/*      */     
/*      */     final int objNr;
/*      */     
/*      */     final int genNr;
/*      */     
/*      */     IndirectRefDescription(PdfIndirectReference reference) {
/* 2444 */       this.docId = reference.getDocument().getDocumentId();
/* 2445 */       this.objNr = reference.getObjNumber();
/* 2446 */       this.genNr = reference.getGenNumber();
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 2451 */       int result = (int)this.docId;
/* 2452 */       result *= 31;
/* 2453 */       result += this.objNr;
/* 2454 */       result *= 31;
/* 2455 */       result += this.genNr;
/* 2456 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 2461 */       if (this == o) return true; 
/* 2462 */       if (o == null || getClass() != o.getClass()) return false;
/*      */       
/* 2464 */       IndirectRefDescription that = (IndirectRefDescription)o;
/*      */       
/* 2466 */       return (this.docId == that.docId && this.objNr == that.objNr && this.genNr == that.genNr);
/*      */     }
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 2471 */     in.defaultReadObject();
/* 2472 */     if (this.versionInfo == null) {
/* 2473 */       this.versionInfo = Version.getInstance().getInfo();
/*      */     }
/*      */     
/* 2476 */     this.eventDispatcher = new EventDispatcher();
/*      */   }
/*      */   private String addModifiedPostfix(String producer) {
/*      */     StringBuilder buf;
/* 2480 */     if (producer == null || !this.versionInfo.getVersion().contains(this.versionInfo.getProduct())) {
/* 2481 */       return this.versionInfo.getVersion();
/*      */     }
/* 2483 */     int idx = producer.indexOf("; modified using");
/*      */     
/* 2485 */     if (idx == -1) {
/* 2486 */       buf = new StringBuilder(producer);
/*      */     } else {
/* 2488 */       buf = new StringBuilder(producer.substring(0, idx));
/*      */     } 
/* 2490 */     buf.append("; modified using ");
/* 2491 */     buf.append(this.versionInfo.getVersion());
/* 2492 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void overrideFullCompressionInWriterProperties(WriterProperties properties, boolean readerHasXrefStream) {
/* 2497 */     if (Boolean.TRUE == properties.isFullCompression && !readerHasXrefStream) {
/* 2498 */       Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 2499 */       logger.warn("Full compression mode requested in append mode but the original document has cross-reference table, not cross-reference stream. Falling back to cross-reference table in appended document and switching full compression off");
/* 2500 */     } else if (Boolean.FALSE == properties.isFullCompression && readerHasXrefStream) {
/* 2501 */       Logger logger = LoggerFactory.getLogger(PdfDocument.class);
/* 2502 */       logger.warn("Full compression mode was requested to be switched off in append mode but the original document has cross-reference stream, not cross-reference table. Falling back to cross-reference stream in appended document and switching full compression on");
/*      */     } 
/* 2504 */     properties.isFullCompression = Boolean.valueOf(readerHasXrefStream);
/*      */   }
/*      */   
/*      */   private static boolean isXmpMetaHasProperty(XMPMeta xmpMeta, String schemaNS, String propName) throws XMPException {
/* 2508 */     return (xmpMeta.getProperty(schemaNS, propName) != null);
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfDocument.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */