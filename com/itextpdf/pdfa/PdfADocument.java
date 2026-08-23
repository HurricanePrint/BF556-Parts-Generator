/*     */ package com.itextpdf.pdfa;
/*     */ 
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.log.CounterManager;
/*     */ import com.itextpdf.kernel.log.ICounter;
/*     */ import com.itextpdf.kernel.pdf.DocumentProperties;
/*     */ import com.itextpdf.kernel.pdf.IPdfPageFactory;
/*     */ import com.itextpdf.kernel.pdf.IsoKey;
/*     */ import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfOutputIntent;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfReader;
/*     */ import com.itextpdf.kernel.pdf.PdfResources;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import com.itextpdf.kernel.pdf.PdfWriter;
/*     */ import com.itextpdf.kernel.pdf.PdfXrefTable;
/*     */ import com.itextpdf.kernel.pdf.StampingProperties;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
/*     */ import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
/*     */ import com.itextpdf.kernel.xmp.XMPException;
/*     */ import com.itextpdf.kernel.xmp.XMPMeta;
/*     */ import com.itextpdf.kernel.xmp.XMPMetaFactory;
/*     */ import com.itextpdf.kernel.xmp.XMPUtils;
/*     */ import com.itextpdf.pdfa.checker.PdfA1Checker;
/*     */ import com.itextpdf.pdfa.checker.PdfA2Checker;
/*     */ import com.itextpdf.pdfa.checker.PdfA3Checker;
/*     */ import com.itextpdf.pdfa.checker.PdfAChecker;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfADocument
/*     */   extends PdfDocument
/*     */ {
/*     */   private static final long serialVersionUID = -5908390625367471894L;
/*  99 */   private static IPdfPageFactory pdfAPageFactory = new PdfAPageFactory();
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfAChecker checker;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean alreadyLoggedThatObjectFlushingWasNotPerformed;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean alreadyLoggedThatPageFlushingWasNotPerformed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfADocument(PdfWriter writer, PdfAConformanceLevel conformanceLevel, PdfOutputIntent outputIntent) {
/* 117 */     this(writer, conformanceLevel, outputIntent, new DocumentProperties());
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
/*     */   public PdfADocument(PdfWriter writer, PdfAConformanceLevel conformanceLevel, PdfOutputIntent outputIntent, DocumentProperties properties) {
/* 131 */     super(writer, properties); this.alreadyLoggedThatObjectFlushingWasNotPerformed = false; this.alreadyLoggedThatPageFlushingWasNotPerformed = false;
/* 132 */     setChecker(conformanceLevel);
/* 133 */     addOutputIntent(outputIntent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfADocument(PdfReader reader, PdfWriter writer) {
/* 143 */     this(reader, writer, new StampingProperties());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfADocument(PdfReader reader, PdfWriter writer, StampingProperties properties) {
/* 154 */     super(reader, writer, properties); XMPMeta meta; this.alreadyLoggedThatObjectFlushingWasNotPerformed = false;
/*     */     this.alreadyLoggedThatPageFlushingWasNotPerformed = false;
/* 156 */     byte[] existingXmpMetadata = getXmpMetadata();
/* 157 */     if (existingXmpMetadata == null) {
/* 158 */       throw new PdfAConformanceException("Document to read from shall be a pdfa conformant file with valid xmp metadata");
/*     */     }
/*     */     
/*     */     try {
/* 162 */       meta = XMPMetaFactory.parseFromBuffer(existingXmpMetadata);
/* 163 */     } catch (XMPException exc) {
/* 164 */       throw new PdfAConformanceException("Document to read from shall be a pdfa conformant file with valid xmp metadata");
/*     */     } 
/* 166 */     PdfAConformanceLevel conformanceLevel = PdfAConformanceLevel.getConformanceLevel(meta);
/* 167 */     if (conformanceLevel == null) {
/* 168 */       throw new PdfAConformanceException("Document to read from shall be a pdfa conformant file with valid xmp metadata");
/*     */     }
/*     */     
/* 171 */     setChecker(conformanceLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkIsoConformance(Object obj, IsoKey key) {
/* 176 */     checkIsoConformance(obj, key, (PdfResources)null, (PdfStream)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void checkIsoConformance(Object obj, IsoKey key, PdfResources resources) {
/* 182 */     checkIsoConformance(obj, key, resources, (PdfStream)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkIsoConformance(Object obj, IsoKey key, PdfResources resources, PdfStream contentStream) {
/*     */     CanvasGraphicsState gState;
/* 188 */     PdfDictionary currentColorSpaces = null;
/* 189 */     if (resources != null) {
/* 190 */       currentColorSpaces = ((PdfDictionary)resources.getPdfObject()).getAsDictionary(PdfName.ColorSpace);
/*     */     }
/* 192 */     switch (key) {
/*     */       case CANVAS_STACK:
/* 194 */         this.checker.checkCanvasStack(((Character)obj).charValue());
/*     */         break;
/*     */       case PDF_OBJECT:
/* 197 */         this.checker.checkPdfObject((PdfObject)obj);
/*     */         break;
/*     */       case RENDERING_INTENT:
/* 200 */         this.checker.checkRenderingIntent((PdfName)obj);
/*     */         break;
/*     */       case INLINE_IMAGE:
/* 203 */         this.checker.checkInlineImage((PdfStream)obj, currentColorSpaces);
/*     */         break;
/*     */       case EXTENDED_GRAPHICS_STATE:
/* 206 */         gState = (CanvasGraphicsState)obj;
/* 207 */         this.checker.checkExtGState(gState, contentStream);
/*     */         break;
/*     */       case FILL_COLOR:
/* 210 */         gState = (CanvasGraphicsState)obj;
/* 211 */         this.checker.checkColor(gState.getFillColor(), currentColorSpaces, Boolean.valueOf(true), contentStream);
/*     */         break;
/*     */       case PAGE:
/* 214 */         this.checker.checkSinglePage((PdfPage)obj);
/*     */         break;
/*     */       case STROKE_COLOR:
/* 217 */         gState = (CanvasGraphicsState)obj;
/* 218 */         this.checker.checkColor(gState.getStrokeColor(), currentColorSpaces, Boolean.valueOf(false), contentStream);
/*     */         break;
/*     */       case TAG_STRUCTURE_ELEMENT:
/* 221 */         this.checker.checkTagStructureElement((PdfObject)obj);
/*     */         break;
/*     */       case FONT_GLYPHS:
/* 224 */         this.checker.checkFontGlyphs(((CanvasGraphicsState)obj).getFont(), contentStream);
/*     */         break;
/*     */       case XREF_TABLE:
/* 227 */         this.checker.checkXrefTable((PdfXrefTable)obj);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfAConformanceLevel getConformanceLevel() {
/* 238 */     return this.checker.getConformanceLevel();
/*     */   }
/*     */   
/*     */   void logThatPdfAPageFlushingWasNotPerformed() {
/* 242 */     if (!this.alreadyLoggedThatPageFlushingWasNotPerformed) {
/* 243 */       this.alreadyLoggedThatPageFlushingWasNotPerformed = true;
/*     */       
/* 245 */       LoggerFactory.getLogger(PdfADocument.class).warn("Page flushing was not performed. Pages flushing in PDF/A mode works only with explicit calls to PdfPage#flush(boolean) with flushResourcesContentStreams argument set to true");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addCustomMetadataExtensions(XMPMeta xmpMeta) {
/* 251 */     if (isTagged()) {
/*     */       try {
/* 253 */         if (xmpMeta.getPropertyInteger("http://www.aiim.org/pdfua/ns/id/", "part") != null) {
/* 254 */           XMPMeta taggedExtensionMeta = XMPMetaFactory.parseFromString("    <x:xmpmeta xmlns:x=\"adobe:ns:meta/\">\n      <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n        <rdf:Description rdf:about=\"\" xmlns:pdfaExtension=\"http://www.aiim.org/pdfa/ns/extension/\" xmlns:pdfaSchema=\"http://www.aiim.org/pdfa/ns/schema#\" xmlns:pdfaProperty=\"http://www.aiim.org/pdfa/ns/property#\">\n          <pdfaExtension:schemas>\n            <rdf:Bag>\n              <rdf:li rdf:parseType=\"Resource\">\n                <pdfaSchema:namespaceURI>http://www.aiim.org/pdfua/ns/id/</pdfaSchema:namespaceURI>\n                <pdfaSchema:prefix>pdfuaid</pdfaSchema:prefix>\n                <pdfaSchema:schema>PDF/UA identification schema</pdfaSchema:schema>\n                <pdfaSchema:property>\n                  <rdf:Seq>\n                    <rdf:li rdf:parseType=\"Resource\">\n                      <pdfaProperty:category>internal</pdfaProperty:category>\n                      <pdfaProperty:description>PDF/UA version identifier</pdfaProperty:description>\n                      <pdfaProperty:name>part</pdfaProperty:name>\n                      <pdfaProperty:valueType>Integer</pdfaProperty:valueType>\n                    </rdf:li>\n                    <rdf:li rdf:parseType=\"Resource\">\n                      <pdfaProperty:category>internal</pdfaProperty:category>\n                      <pdfaProperty:description>PDF/UA amendment identifier</pdfaProperty:description>\n                      <pdfaProperty:name>amd</pdfaProperty:name>\n                      <pdfaProperty:valueType>Text</pdfaProperty:valueType>\n                    </rdf:li>\n                    <rdf:li rdf:parseType=\"Resource\">\n                      <pdfaProperty:category>internal</pdfaProperty:category>\n                      <pdfaProperty:description>PDF/UA corrigenda identifier</pdfaProperty:description>\n                      <pdfaProperty:name>corr</pdfaProperty:name>\n                      <pdfaProperty:valueType>Text</pdfaProperty:valueType>\n                    </rdf:li>\n                  </rdf:Seq>\n                </pdfaSchema:property>\n              </rdf:li>\n            </rdf:Bag>\n          </pdfaExtension:schemas>\n        </rdf:Description>\n      </rdf:RDF>\n    </x:xmpmeta>");
/* 255 */           XMPUtils.appendProperties(taggedExtensionMeta, xmpMeta, true, false);
/*     */         } 
/* 257 */       } catch (XMPException exc) {
/* 258 */         Logger logger = LoggerFactory.getLogger(PdfADocument.class);
/* 259 */         logger.error("Exception while updating XmpMetadata", (Throwable)exc);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateXmpMetadata() {
/*     */     try {
/* 267 */       XMPMeta xmpMeta = updateDefaultXmpMetadata();
/* 268 */       xmpMeta.setProperty("http://www.aiim.org/pdfa/ns/id/", "part", this.checker.getConformanceLevel().getPart());
/* 269 */       xmpMeta.setProperty("http://www.aiim.org/pdfa/ns/id/", "conformance", this.checker.getConformanceLevel().getConformance());
/* 270 */       addCustomMetadataExtensions(xmpMeta);
/* 271 */       setXmpMetadata(xmpMeta);
/* 272 */     } catch (XMPException e) {
/* 273 */       Logger logger = LoggerFactory.getLogger(PdfADocument.class);
/* 274 */       logger.error("Exception while updating XmpMetadata", (Throwable)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkIsoConformance() {
/* 280 */     this.checker.checkDocument(this.catalog);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void flushObject(PdfObject pdfObject, boolean canBeInObjStm) throws IOException {
/* 285 */     markObjectAsMustBeFlushed(pdfObject);
/* 286 */     if (this.isClosing || this.checker.objectIsChecked(pdfObject)) {
/* 287 */       super.flushObject(pdfObject, canBeInObjStm);
/* 288 */     } else if (!this.alreadyLoggedThatObjectFlushingWasNotPerformed) {
/* 289 */       this.alreadyLoggedThatObjectFlushingWasNotPerformed = true;
/*     */       
/* 291 */       LoggerFactory.getLogger(PdfADocument.class).warn("Object flushing was not performed. Object in PDF/A mode can only be flushed if the document is closed or if this object has already been checked for compliance with PDF/A rules.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void flushFonts() {
/* 297 */     for (PdfFont pdfFont : getDocumentFonts()) {
/* 298 */       this.checker.checkFont(pdfFont);
/*     */     }
/* 300 */     super.flushFonts();
/*     */   }
/*     */   
/*     */   protected void setChecker(PdfAConformanceLevel conformanceLevel) {
/* 304 */     switch (conformanceLevel.getPart()) {
/*     */       case "1":
/* 306 */         this.checker = (PdfAChecker)new PdfA1Checker(conformanceLevel);
/*     */         break;
/*     */       case "2":
/* 309 */         this.checker = (PdfAChecker)new PdfA2Checker(conformanceLevel);
/*     */         break;
/*     */       case "3":
/* 312 */         this.checker = (PdfAChecker)new PdfA3Checker(conformanceLevel);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void initTagStructureContext() {
/* 318 */     this.tagStructureContext = new TagStructureContext(this, getPdfVersionForPdfA(this.checker.getConformanceLevel()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected List<ICounter> getCounters() {
/* 324 */     return CounterManager.getInstance().getCounters(PdfADocument.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected IPdfPageFactory getPageFactory() {
/* 329 */     return pdfAPageFactory;
/*     */   }
/*     */   
/*     */   boolean isClosing() {
/* 333 */     return this.isClosing;
/*     */   }
/*     */ 
/*     */   
/*     */   private static PdfVersion getPdfVersionForPdfA(PdfAConformanceLevel conformanceLevel) {
/* 338 */     switch (conformanceLevel.getPart())
/*     */     { case "1":
/* 340 */         version = PdfVersion.PDF_1_4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 352 */         return version;case "2": version = PdfVersion.PDF_1_7; return version;case "3": version = PdfVersion.PDF_1_7; return version; }  PdfVersion version = PdfVersion.PDF_1_4; return version;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/pdfa/PdfADocument.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */