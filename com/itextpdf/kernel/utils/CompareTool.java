/*      */ package com.itextpdf.kernel.utils;
/*      */ 
/*      */ import com.itextpdf.io.font.PdfEncodings;
/*      */ import com.itextpdf.io.util.FileUtil;
/*      */ import com.itextpdf.io.util.GhostscriptHelper;
/*      */ import com.itextpdf.io.util.ImageMagickHelper;
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.io.util.UrlUtil;
/*      */ import com.itextpdf.kernel.counter.event.IMetaInfo;
/*      */ import com.itextpdf.kernel.geom.Rectangle;
/*      */ import com.itextpdf.kernel.pdf.DocumentProperties;
/*      */ import com.itextpdf.kernel.pdf.PdfArray;
/*      */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*      */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfDocumentInfo;
/*      */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*      */ import com.itextpdf.kernel.pdf.PdfName;
/*      */ import com.itextpdf.kernel.pdf.PdfNumber;
/*      */ import com.itextpdf.kernel.pdf.PdfObject;
/*      */ import com.itextpdf.kernel.pdf.PdfReader;
/*      */ import com.itextpdf.kernel.pdf.PdfStream;
/*      */ import com.itextpdf.kernel.pdf.PdfString;
/*      */ import com.itextpdf.kernel.pdf.PdfWriter;
/*      */ import com.itextpdf.kernel.pdf.ReaderProperties;
/*      */ import com.itextpdf.kernel.pdf.StampingProperties;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*      */ import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
/*      */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*      */ import com.itextpdf.kernel.xmp.XMPMeta;
/*      */ import com.itextpdf.kernel.xmp.XMPMetaFactory;
/*      */ import com.itextpdf.kernel.xmp.XMPUtils;
/*      */ import com.itextpdf.kernel.xmp.options.ParseOptions;
/*      */ import com.itextpdf.kernel.xmp.options.SerializeOptions;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileFilter;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.TreeSet;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CompareTool
/*      */ {
/*      */   private static final String UNEXPECTED_NUMBER_OF_PAGES = "Unexpected number of pages for <filename>.";
/*      */   private static final String DIFFERENT_PAGES = "File file:///<filename> differs on page <pagenumber>.";
/*      */   private static final String IGNORED_AREAS_PREFIX = "ignored_areas_";
/*      */   private static final String VERSION_REGEXP = "(iText®( pdfX(FA|fa)| DITO)?|iTextSharp™) (\\d+\\.)+\\d+(-SNAPSHOT)?";
/*      */   private static final String VERSION_REPLACEMENT = "iText® <version>";
/*      */   private static final String COPYRIGHT_REGEXP = "©\\d+-\\d+ iText Group NV";
/*      */   private static final String COPYRIGHT_REPLACEMENT = "©<copyright years> iText Group NV";
/*      */   private static final String NEW_LINES = "\\r|\\n";
/*      */   private String cmpPdf;
/*      */   private String cmpPdfName;
/*      */   private String cmpImage;
/*      */   private String outPdf;
/*      */   private String outPdfName;
/*      */   private String outImage;
/*      */   private ReaderProperties outProps;
/*      */   private ReaderProperties cmpProps;
/*      */   private List<PdfIndirectReference> outPagesRef;
/*      */   private List<PdfIndirectReference> cmpPagesRef;
/*  153 */   private int compareByContentErrorsLimit = 1000;
/*      */   
/*      */   private boolean generateCompareByContentXmlReport = false;
/*      */   
/*      */   private boolean encryptionCompareEnabled = false;
/*      */   
/*      */   private boolean useCachedPagesForComparison = true;
/*      */   
/*      */   private IMetaInfo metaInfo;
/*      */   
/*      */   private String gsExec;
/*      */   
/*      */   private String compareExec;
/*      */   
/*      */   CompareTool(String gsExec, String compareExec) {
/*  168 */     this.gsExec = gsExec;
/*  169 */     this.compareExec = compareExec;
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
/*      */   public CompareResult compareByCatalog(PdfDocument outDocument, PdfDocument cmpDocument) throws IOException {
/*  192 */     CompareResult compareResult = null;
/*  193 */     compareResult = new CompareResult(this.compareByContentErrorsLimit);
/*      */     
/*  195 */     ObjectPath catalogPath = new ObjectPath(((PdfDictionary)cmpDocument.getCatalog().getPdfObject()).getIndirectReference(), ((PdfDictionary)outDocument.getCatalog().getPdfObject()).getIndirectReference());
/*  196 */     Set<PdfName> ignoredCatalogEntries = new LinkedHashSet<>(Arrays.asList(new PdfName[] { PdfName.Metadata }));
/*  197 */     compareDictionariesExtended((PdfDictionary)outDocument.getCatalog().getPdfObject(), (PdfDictionary)cmpDocument.getCatalog().getPdfObject(), catalogPath, compareResult, ignoredCatalogEntries);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  202 */     if (this.cmpPagesRef == null || this.outPagesRef == null) {
/*  203 */       return compareResult;
/*      */     }
/*      */     
/*  206 */     if (this.outPagesRef.size() != this.cmpPagesRef.size() && !compareResult.isMessageLimitReached()) {
/*  207 */       compareResult.addError(catalogPath, "Documents have different numbers of pages.");
/*      */     }
/*  209 */     for (int i = 0; i < Math.min(this.cmpPagesRef.size(), this.outPagesRef.size()) && 
/*  210 */       !compareResult.isMessageLimitReached(); i++) {
/*      */ 
/*      */       
/*  213 */       ObjectPath currentPath = new ObjectPath(this.cmpPagesRef.get(i), this.outPagesRef.get(i));
/*  214 */       PdfDictionary outPageDict = (PdfDictionary)((PdfIndirectReference)this.outPagesRef.get(i)).getRefersTo();
/*  215 */       PdfDictionary cmpPageDict = (PdfDictionary)((PdfIndirectReference)this.cmpPagesRef.get(i)).getRefersTo();
/*  216 */       compareDictionariesExtended(outPageDict, cmpPageDict, currentPath, compareResult);
/*      */     } 
/*  218 */     return compareResult;
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
/*      */   public CompareTool disableCachedPagesComparison() {
/*  241 */     this.useCachedPagesForComparison = false;
/*  242 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareTool setCompareByContentErrorsLimit(int compareByContentMaxErrorCount) {
/*  252 */     this.compareByContentErrorsLimit = compareByContentMaxErrorCount;
/*  253 */     return this;
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
/*      */   public CompareTool setGenerateCompareByContentXmlReport(boolean generateCompareByContentXmlReport) {
/*  265 */     this.generateCompareByContentXmlReport = generateCompareByContentXmlReport;
/*  266 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEventCountingMetaInfo(IMetaInfo metaInfo) {
/*  275 */     this.metaInfo = metaInfo;
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
/*      */   public CompareTool enableEncryptionCompare() {
/*  289 */     this.encryptionCompareEnabled = true;
/*  290 */     return this;
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
/*      */   public ReaderProperties getOutReaderProperties() {
/*  305 */     if (this.outProps == null) {
/*  306 */       this.outProps = new ReaderProperties();
/*      */     }
/*  308 */     return this.outProps;
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
/*      */   public ReaderProperties getCmpReaderProperties() {
/*  323 */     if (this.cmpProps == null) {
/*  324 */       this.cmpProps = new ReaderProperties();
/*      */     }
/*  326 */     return this.cmpProps;
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
/*      */   public String compareVisually(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix) throws InterruptedException, IOException {
/*  349 */     return compareVisually(outPdf, cmpPdf, outPath, differenceImagePrefix, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String compareVisually(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws InterruptedException, IOException {
/*  378 */     init(outPdf, cmpPdf);
/*  379 */     System.out.println("Out pdf: " + UrlUtil.getNormalizedFileUriString(outPdf));
/*  380 */     System.out.println("Cmp pdf: " + UrlUtil.getNormalizedFileUriString(cmpPdf) + "\n");
/*  381 */     return compareVisually(outPath, differenceImagePrefix, ignoredAreas);
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
/*      */ 
/*      */   
/*      */   public String compareByContent(String outPdf, String cmpPdf, String outPath) throws InterruptedException, IOException {
/*  407 */     return compareByContent(outPdf, cmpPdf, outPath, null, null, null, null);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public String compareByContent(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix) throws InterruptedException, IOException {
/*  434 */     return compareByContent(outPdf, cmpPdf, outPath, differenceImagePrefix, null, null, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String compareByContent(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix, byte[] outPass, byte[] cmpPass) throws InterruptedException, IOException {
/*  467 */     return compareByContent(outPdf, cmpPdf, outPath, differenceImagePrefix, null, outPass, cmpPass);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String compareByContent(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws InterruptedException, IOException {
/*  495 */     return compareByContent(outPdf, cmpPdf, outPath, differenceImagePrefix, ignoredAreas, null, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String compareByContent(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas, byte[] outPass, byte[] cmpPass) throws InterruptedException, IOException {
/*  528 */     init(outPdf, cmpPdf);
/*  529 */     System.out.println("Out pdf: " + UrlUtil.getNormalizedFileUriString(outPdf));
/*  530 */     System.out.println("Cmp pdf: " + UrlUtil.getNormalizedFileUriString(cmpPdf) + "\n");
/*  531 */     setPassword(outPass, cmpPass);
/*  532 */     return compareByContent(outPath, differenceImagePrefix, ignoredAreas);
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
/*      */   public boolean compareDictionaries(PdfDictionary outDict, PdfDictionary cmpDict) throws IOException {
/*  545 */     return compareDictionariesExtended(outDict, cmpDict, null, null);
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
/*      */   public CompareResult compareDictionariesStructure(PdfDictionary outDict, PdfDictionary cmpDict) {
/*  566 */     return compareDictionariesStructure(outDict, cmpDict, null);
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
/*      */   public CompareResult compareDictionariesStructure(PdfDictionary outDict, PdfDictionary cmpDict, Set<PdfName> excludedKeys) {
/*  589 */     if (outDict.getIndirectReference() == null || cmpDict.getIndirectReference() == null) {
/*  590 */       throw new IllegalArgumentException("The 'outDict' and 'cmpDict' objects shall have indirect references.");
/*      */     }
/*      */     
/*  593 */     CompareResult compareResult = new CompareResult(this.compareByContentErrorsLimit);
/*  594 */     ObjectPath currentPath = new ObjectPath(cmpDict.getIndirectReference(), outDict.getIndirectReference());
/*  595 */     if (!compareDictionariesExtended(outDict, cmpDict, currentPath, compareResult, excludedKeys)) {
/*  596 */       assert !compareResult.isOk();
/*  597 */       System.out.println(compareResult.getReport());
/*  598 */       return compareResult;
/*      */     } 
/*  600 */     assert compareResult.isOk();
/*  601 */     return null;
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
/*      */   public CompareResult compareStreamsStructure(PdfStream outStream, PdfStream cmpStream) {
/*  617 */     CompareResult compareResult = new CompareResult(this.compareByContentErrorsLimit);
/*  618 */     ObjectPath currentPath = new ObjectPath(cmpStream.getIndirectReference(), outStream.getIndirectReference());
/*  619 */     if (!compareStreamsExtended(outStream, cmpStream, currentPath, compareResult)) {
/*  620 */       assert !compareResult.isOk();
/*  621 */       System.out.println(compareResult.getReport());
/*  622 */       return compareResult;
/*      */     } 
/*  624 */     assert compareResult.isOk();
/*  625 */     return null;
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
/*      */   public boolean compareStreams(PdfStream outStream, PdfStream cmpStream) throws IOException {
/*  638 */     return compareStreamsExtended(outStream, cmpStream, null, null);
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
/*      */   public boolean compareArrays(PdfArray outArray, PdfArray cmpArray) throws IOException {
/*  651 */     return compareArraysExtended(outArray, cmpArray, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compareNames(PdfName outName, PdfName cmpName) {
/*  662 */     return cmpName.equals(outName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compareNumbers(PdfNumber outNumber, PdfNumber cmpNumber) {
/*  673 */     return (cmpNumber.getValue() == outNumber.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compareStrings(PdfString outString, PdfString cmpString) {
/*  684 */     return cmpString.getValue().equals(outString.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compareBooleans(PdfBoolean outBoolean, PdfBoolean cmpBoolean) {
/*  695 */     return (cmpBoolean.getValue() == outBoolean.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String compareXmp(String outPdf, String cmpPdf) {
/*  706 */     return compareXmp(outPdf, cmpPdf, false);
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
/*      */   public String compareXmp(String outPdf, String cmpPdf, boolean ignoreDateAndProducerProperties) {
/*  719 */     init(outPdf, cmpPdf);
/*  720 */     PdfDocument cmpDocument = null;
/*  721 */     PdfDocument outDocument = null;
/*      */     try {
/*  723 */       cmpDocument = new PdfDocument(new PdfReader(this.cmpPdf), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/*  724 */       outDocument = new PdfDocument(new PdfReader(this.outPdf), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/*  725 */       byte[] cmpBytes = cmpDocument.getXmpMetadata(), outBytes = outDocument.getXmpMetadata();
/*  726 */       if (ignoreDateAndProducerProperties) {
/*  727 */         XMPMeta xmpMeta = XMPMetaFactory.parseFromBuffer(cmpBytes, (new ParseOptions()).setOmitNormalization(true));
/*      */         
/*  729 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "CreateDate", true, true);
/*  730 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "ModifyDate", true, true);
/*  731 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "MetadataDate", true, true);
/*  732 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/pdf/1.3/", "Producer", true, true);
/*      */         
/*  734 */         cmpBytes = XMPMetaFactory.serializeToBuffer(xmpMeta, new SerializeOptions(8192));
/*      */         
/*  736 */         xmpMeta = XMPMetaFactory.parseFromBuffer(outBytes, (new ParseOptions()).setOmitNormalization(true));
/*  737 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "CreateDate", true, true);
/*  738 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "ModifyDate", true, true);
/*  739 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "MetadataDate", true, true);
/*  740 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/pdf/1.3/", "Producer", true, true);
/*      */         
/*  742 */         outBytes = XMPMetaFactory.serializeToBuffer(xmpMeta, new SerializeOptions(8192));
/*      */       } 
/*      */       
/*  745 */       if (!compareXmls(cmpBytes, outBytes)) {
/*  746 */         return "The XMP packages different!";
/*      */       }
/*  748 */     } catch (Exception ex) {
/*  749 */       return "XMP parsing failure!";
/*      */     } finally {
/*  751 */       if (cmpDocument != null)
/*  752 */         cmpDocument.close(); 
/*  753 */       if (outDocument != null)
/*  754 */         outDocument.close(); 
/*      */     } 
/*  756 */     return null;
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
/*      */   public boolean compareXmls(byte[] xml1, byte[] xml2) throws ParserConfigurationException, SAXException, IOException {
/*  771 */     return XmlUtils.compareXmls(new ByteArrayInputStream(xml1), new ByteArrayInputStream(xml2));
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
/*      */   public boolean compareXmls(String outXmlFile, String cmpXmlFile) throws ParserConfigurationException, SAXException, IOException {
/*  786 */     System.out.println("Out xml: " + UrlUtil.getNormalizedFileUriString(outXmlFile));
/*  787 */     System.out.println("Cmp xml: " + UrlUtil.getNormalizedFileUriString(cmpXmlFile) + "\n");
/*  788 */     try(InputStream outXmlStream = FileUtil.getInputStreamForFile(outXmlFile); 
/*  789 */         InputStream cmpXmlStream = FileUtil.getInputStreamForFile(cmpXmlFile)) {
/*  790 */       return XmlUtils.compareXmls(outXmlStream, cmpXmlStream);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String compareDocumentInfo(String outPdf, String cmpPdf, byte[] outPass, byte[] cmpPass) throws IOException {
/*  808 */     System.out.print("[itext] INFO  Comparing document info.......");
/*  809 */     String message = null;
/*  810 */     setPassword(outPass, cmpPass);
/*  811 */     PdfDocument outDocument = new PdfDocument(new PdfReader(outPdf, getOutReaderProperties()), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/*  812 */     PdfDocument cmpDocument = new PdfDocument(new PdfReader(cmpPdf, getCmpReaderProperties()), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/*  813 */     String[] cmpInfo = convertInfo(cmpDocument.getDocumentInfo());
/*  814 */     String[] outInfo = convertInfo(outDocument.getDocumentInfo());
/*  815 */     for (int i = 0; i < cmpInfo.length; i++) {
/*  816 */       if (!cmpInfo[i].equals(outInfo[i])) {
/*  817 */         message = MessageFormatUtil.format("Document info fail. Expected: \"{0}\", actual: \"{1}\"", new Object[] { cmpInfo[i], outInfo[i] });
/*      */         break;
/*      */       } 
/*      */     } 
/*  821 */     outDocument.close();
/*  822 */     cmpDocument.close();
/*      */     
/*  824 */     if (message == null) {
/*  825 */       System.out.println("OK");
/*      */     } else {
/*  827 */       System.out.println("Fail");
/*  828 */     }  System.out.flush();
/*  829 */     return message;
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
/*      */   public String compareDocumentInfo(String outPdf, String cmpPdf) throws IOException {
/*  841 */     return compareDocumentInfo(outPdf, cmpPdf, null, null);
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
/*      */   public String compareLinkAnnotations(String outPdf, String cmpPdf) throws IOException {
/*  853 */     System.out.print("[itext] INFO  Comparing link annotations....");
/*  854 */     String message = null;
/*  855 */     PdfDocument outDocument = new PdfDocument(new PdfReader(outPdf), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/*  856 */     PdfDocument cmpDocument = new PdfDocument(new PdfReader(cmpPdf), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/*  857 */     for (int i = 0; i < outDocument.getNumberOfPages() && i < cmpDocument.getNumberOfPages(); i++) {
/*  858 */       List<PdfLinkAnnotation> outLinks = getLinkAnnotations(i + 1, outDocument);
/*  859 */       List<PdfLinkAnnotation> cmpLinks = getLinkAnnotations(i + 1, cmpDocument);
/*      */       
/*  861 */       if (cmpLinks.size() != outLinks.size()) {
/*  862 */         message = MessageFormatUtil.format("Different number of links on page {0}.", new Object[] { Integer.valueOf(i + 1) });
/*      */         break;
/*      */       } 
/*  865 */       for (int j = 0; j < cmpLinks.size(); j++) {
/*  866 */         if (!compareLinkAnnotations(cmpLinks.get(j), outLinks.get(j), cmpDocument, outDocument)) {
/*  867 */           message = MessageFormatUtil.format("Different links on page {0}.\n{1}\n{2}", new Object[] { Integer.valueOf(i + 1), ((PdfLinkAnnotation)cmpLinks.get(j)).toString(), ((PdfLinkAnnotation)outLinks.get(j)).toString() });
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  872 */     outDocument.close();
/*  873 */     cmpDocument.close();
/*  874 */     if (message == null) {
/*  875 */       System.out.println("OK");
/*      */     } else {
/*  877 */       System.out.println("Fail");
/*  878 */     }  System.out.flush();
/*  879 */     return message;
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
/*      */   public String compareTagStructures(String outPdf, String cmpPdf) throws IOException, ParserConfigurationException, SAXException {
/*  898 */     System.out.print("[itext] INFO  Comparing tag structures......");
/*      */     
/*  900 */     String outXmlPath = outPdf.replace(".pdf", ".xml");
/*  901 */     String cmpXmlPath = outPdf.replace(".pdf", ".cmp.xml");
/*      */     
/*  903 */     String message = null;
/*      */     
/*  905 */     PdfReader readerOut = new PdfReader(outPdf);
/*  906 */     PdfDocument docOut = new PdfDocument(readerOut, (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/*  907 */     FileOutputStream xmlOut = new FileOutputStream(outXmlPath);
/*  908 */     (new TaggedPdfReaderTool(docOut)).setRootTag("root").convertToXml(xmlOut);
/*  909 */     docOut.close();
/*  910 */     xmlOut.close();
/*      */     
/*  912 */     PdfReader readerCmp = new PdfReader(cmpPdf);
/*  913 */     PdfDocument docCmp = new PdfDocument(readerCmp, (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/*  914 */     FileOutputStream xmlCmp = new FileOutputStream(cmpXmlPath);
/*  915 */     (new TaggedPdfReaderTool(docCmp)).setRootTag("root").convertToXml(xmlCmp);
/*  916 */     docCmp.close();
/*  917 */     xmlCmp.close();
/*      */     
/*  919 */     if (!compareXmls(outXmlPath, cmpXmlPath)) {
/*  920 */       message = "The tag structures are different.";
/*      */     }
/*  922 */     if (message == null) {
/*  923 */       System.out.println("OK");
/*      */     } else {
/*  925 */       System.out.println("Fail");
/*  926 */     }  System.out.flush();
/*  927 */     return message;
/*      */   }
/*      */   
/*      */   String[] convertInfo(PdfDocumentInfo info) {
/*  931 */     String[] convertedInfo = { "", "", "", "", "" };
/*  932 */     String infoValue = info.getTitle();
/*  933 */     if (infoValue != null)
/*  934 */       convertedInfo[0] = infoValue; 
/*  935 */     infoValue = info.getAuthor();
/*  936 */     if (infoValue != null)
/*  937 */       convertedInfo[1] = infoValue; 
/*  938 */     infoValue = info.getSubject();
/*  939 */     if (infoValue != null)
/*  940 */       convertedInfo[2] = infoValue; 
/*  941 */     infoValue = info.getKeywords();
/*  942 */     if (infoValue != null)
/*  943 */       convertedInfo[3] = infoValue; 
/*  944 */     infoValue = info.getProducer();
/*  945 */     if (infoValue != null) {
/*  946 */       convertedInfo[4] = convertProducerLine(infoValue);
/*      */     }
/*  948 */     return convertedInfo;
/*      */   }
/*      */   
/*      */   String convertProducerLine(String producer) {
/*  952 */     return producer.replaceAll("(iText®( pdfX(FA|fa)| DITO)?|iTextSharp™) (\\d+\\.)+\\d+(-SNAPSHOT)?", "iText® <version>").replaceAll("©\\d+-\\d+ iText Group NV", "©<copyright years> iText Group NV");
/*      */   }
/*      */ 
/*      */   
/*      */   private void init(String outPdf, String cmpPdf) {
/*  957 */     this.outPdf = outPdf;
/*  958 */     this.cmpPdf = cmpPdf;
/*  959 */     this.outPdfName = (new File(outPdf)).getName();
/*  960 */     this.cmpPdfName = (new File(cmpPdf)).getName();
/*  961 */     this.outImage = this.outPdfName + "-%03d.png";
/*  962 */     if (this.cmpPdfName.startsWith("cmp_")) { this.cmpImage = this.cmpPdfName + "-%03d.png"; }
/*  963 */     else { this.cmpImage = "cmp_" + this.cmpPdfName + "-%03d.png"; }
/*      */   
/*      */   }
/*      */   private void setPassword(byte[] outPass, byte[] cmpPass) {
/*  967 */     if (outPass != null) {
/*  968 */       getOutReaderProperties().setPassword(outPass);
/*      */     }
/*  970 */     if (cmpPass != null) {
/*  971 */       getCmpReaderProperties().setPassword(outPass);
/*      */     }
/*      */   }
/*      */   
/*      */   private String compareVisually(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws InterruptedException, IOException {
/*  976 */     return compareVisually(outPath, differenceImagePrefix, ignoredAreas, (List<Integer>)null);
/*      */   }
/*      */   
/*      */   private String compareVisually(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas, List<Integer> equalPages) throws IOException, InterruptedException {
/*  980 */     if (!outPath.endsWith("/")) {
/*  981 */       outPath = outPath + "/";
/*      */     }
/*  983 */     if (differenceImagePrefix == null) {
/*  984 */       String fileBasedPrefix = "";
/*  985 */       if (this.outPdfName != null)
/*      */       {
/*  987 */         fileBasedPrefix = this.outPdfName + "_";
/*      */       }
/*  989 */       differenceImagePrefix = "diff_" + fileBasedPrefix;
/*      */     } 
/*      */     
/*  992 */     prepareOutputDirs(outPath, differenceImagePrefix);
/*      */     
/*  994 */     System.out.println("Comparing visually..........");
/*      */     
/*  996 */     if (ignoredAreas != null && !ignoredAreas.isEmpty()) {
/*  997 */       createIgnoredAreasPdfs(outPath, ignoredAreas);
/*      */     }
/*      */     
/* 1000 */     GhostscriptHelper ghostscriptHelper = null;
/*      */     try {
/* 1002 */       ghostscriptHelper = new GhostscriptHelper(this.gsExec);
/* 1003 */     } catch (IllegalArgumentException e) {
/* 1004 */       throw new CompareToolExecutionException(e.getMessage());
/*      */     } 
/*      */     
/* 1007 */     ghostscriptHelper.runGhostScriptImageGeneration(this.outPdf, outPath, this.outImage);
/* 1008 */     ghostscriptHelper.runGhostScriptImageGeneration(this.cmpPdf, outPath, this.cmpImage);
/* 1009 */     return compareImagesOfPdfs(outPath, differenceImagePrefix, equalPages);
/*      */   }
/*      */   private String compareImagesOfPdfs(String outPath, String differenceImagePrefix, List<Integer> equalPages) throws IOException, InterruptedException {
/*      */     boolean compareExecIsOk;
/* 1013 */     File[] imageFiles = FileUtil.listFilesInDirectoryByFilter(outPath, new PngFileFilter());
/* 1014 */     File[] cmpImageFiles = FileUtil.listFilesInDirectoryByFilter(outPath, new CmpPngFileFilter());
/* 1015 */     boolean bUnexpectedNumberOfPages = false;
/* 1016 */     if (imageFiles.length != cmpImageFiles.length) {
/* 1017 */       bUnexpectedNumberOfPages = true;
/*      */     }
/* 1019 */     int cnt = Math.min(imageFiles.length, cmpImageFiles.length);
/* 1020 */     if (cnt < 1) {
/* 1021 */       throw new CompareToolExecutionException("No files for comparing. The result or sample pdf file is not processed by GhostScript.");
/*      */     }
/*      */     
/* 1024 */     Arrays.sort(imageFiles, new ImageNameComparator());
/* 1025 */     Arrays.sort(cmpImageFiles, new ImageNameComparator());
/*      */ 
/*      */     
/* 1028 */     String imageMagickInitError = null;
/* 1029 */     ImageMagickHelper imageMagickHelper = null;
/*      */     try {
/* 1031 */       imageMagickHelper = new ImageMagickHelper(this.compareExec);
/* 1032 */       compareExecIsOk = true;
/* 1033 */     } catch (IllegalArgumentException e) {
/* 1034 */       compareExecIsOk = false;
/* 1035 */       imageMagickInitError = e.getMessage();
/* 1036 */       LoggerFactory.getLogger(CompareTool.class).warn(e.getMessage());
/*      */     } 
/*      */     
/* 1039 */     List<Integer> diffPages = new ArrayList<>();
/* 1040 */     String differentPagesFail = null;
/*      */     
/* 1042 */     for (int i = 0; i < cnt; i++) {
/* 1043 */       if (equalPages == null || !equalPages.contains(Integer.valueOf(i))) {
/*      */         
/* 1045 */         System.out.println("Comparing page " + Integer.toString(i + 1) + ": " + UrlUtil.getNormalizedFileUriString(imageFiles[i].getName()) + " ...");
/* 1046 */         System.out.println("Comparing page " + Integer.toString(i + 1) + ": " + UrlUtil.getNormalizedFileUriString(imageFiles[i].getName()) + " ...");
/* 1047 */         FileInputStream is1 = new FileInputStream(imageFiles[i].getAbsolutePath());
/* 1048 */         FileInputStream is2 = new FileInputStream(cmpImageFiles[i].getAbsolutePath());
/* 1049 */         boolean cmpResult = compareStreams(is1, is2);
/* 1050 */         is1.close();
/* 1051 */         is2.close();
/* 1052 */         if (!cmpResult) {
/* 1053 */           differentPagesFail = "Page is different!";
/* 1054 */           diffPages.add(Integer.valueOf(i + 1));
/* 1055 */           if (compareExecIsOk) {
/* 1056 */             String diffName = outPath + differenceImagePrefix + Integer.toString(i + 1) + ".png";
/* 1057 */             if (!imageMagickHelper.runImageMagickImageCompare(imageFiles[i].getAbsolutePath(), cmpImageFiles[i]
/* 1058 */                 .getAbsolutePath(), diffName)) {
/* 1059 */               File diffFile = new File(diffName);
/* 1060 */               differentPagesFail = differentPagesFail + "\nPlease, examine file:///" + UrlUtil.toNormalizedURI(diffFile).getPath() + " for more details.";
/*      */             } 
/*      */           } 
/* 1063 */           System.out.println(differentPagesFail);
/*      */         } else {
/* 1065 */           System.out.println(" done.");
/*      */         } 
/*      */       } 
/* 1068 */     }  if (differentPagesFail != null) {
/* 1069 */       String errorMessage = "File file:///<filename> differs on page <pagenumber>.".replace("<filename>", UrlUtil.toNormalizedURI(this.outPdf).getPath()).replace("<pagenumber>", listDiffPagesAsString(diffPages));
/* 1070 */       if (!compareExecIsOk) {
/* 1071 */         errorMessage = errorMessage + "\n" + imageMagickInitError;
/*      */       }
/* 1073 */       return errorMessage;
/*      */     } 
/* 1075 */     if (bUnexpectedNumberOfPages) {
/* 1076 */       return "Unexpected number of pages for <filename>.".replace("<filename>", this.outPdf);
/*      */     }
/*      */     
/* 1079 */     return null;
/*      */   }
/*      */   
/*      */   private String listDiffPagesAsString(List<Integer> diffPages) {
/* 1083 */     StringBuilder sb = new StringBuilder("[");
/* 1084 */     for (int i = 0; i < diffPages.size(); i++) {
/* 1085 */       sb.append(diffPages.get(i));
/* 1086 */       if (i < diffPages.size() - 1) {
/* 1087 */         sb.append(", ");
/*      */       }
/*      */     } 
/* 1090 */     sb.append("]");
/* 1091 */     return sb.toString();
/*      */   }
/*      */   
/*      */   private void createIgnoredAreasPdfs(String outPath, Map<Integer, List<Rectangle>> ignoredAreas) throws IOException {
/* 1095 */     PdfWriter outWriter = new PdfWriter(outPath + "ignored_areas_" + this.outPdfName);
/* 1096 */     PdfWriter cmpWriter = new PdfWriter(outPath + "ignored_areas_" + this.cmpPdfName);
/*      */     
/* 1098 */     StampingProperties properties = new StampingProperties();
/* 1099 */     properties.setEventCountingMetaInfo(this.metaInfo);
/* 1100 */     PdfDocument pdfOutDoc = new PdfDocument(new PdfReader(this.outPdf), outWriter, properties);
/* 1101 */     PdfDocument pdfCmpDoc = new PdfDocument(new PdfReader(this.cmpPdf), cmpWriter, properties);
/*      */     
/* 1103 */     for (Map.Entry<Integer, List<Rectangle>> entry : ignoredAreas.entrySet()) {
/* 1104 */       int pageNumber = ((Integer)entry.getKey()).intValue();
/* 1105 */       List<Rectangle> rectangles = entry.getValue();
/*      */       
/* 1107 */       if (rectangles != null && !rectangles.isEmpty()) {
/* 1108 */         PdfCanvas outCanvas = new PdfCanvas(pdfOutDoc.getPage(pageNumber));
/* 1109 */         PdfCanvas cmpCanvas = new PdfCanvas(pdfCmpDoc.getPage(pageNumber));
/*      */         
/* 1111 */         outCanvas.saveState();
/* 1112 */         cmpCanvas.saveState();
/* 1113 */         for (Rectangle rect : rectangles) {
/* 1114 */           outCanvas.rectangle(rect).fill();
/* 1115 */           cmpCanvas.rectangle(rect).fill();
/*      */         } 
/* 1117 */         outCanvas.restoreState();
/* 1118 */         cmpCanvas.restoreState();
/*      */       } 
/*      */     } 
/*      */     
/* 1122 */     pdfOutDoc.close();
/* 1123 */     pdfCmpDoc.close();
/*      */     
/* 1125 */     init(outPath + "ignored_areas_" + this.outPdfName, outPath + "ignored_areas_" + this.cmpPdfName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void prepareOutputDirs(String outPath, String differenceImagePrefix) {
/* 1133 */     if (!FileUtil.directoryExists(outPath)) {
/* 1134 */       FileUtil.createDirectories(outPath);
/*      */     } else {
/* 1136 */       File[] imageFiles = FileUtil.listFilesInDirectoryByFilter(outPath, new PngFileFilter());
/* 1137 */       for (File file : imageFiles) {
/* 1138 */         file.delete();
/*      */       }
/* 1140 */       File[] cmpImageFiles = FileUtil.listFilesInDirectoryByFilter(outPath, new CmpPngFileFilter());
/* 1141 */       for (File file : cmpImageFiles) {
/* 1142 */         file.delete();
/*      */       }
/*      */       
/* 1145 */       File[] diffFiles = FileUtil.listFilesInDirectoryByFilter(outPath, new DiffPngFileFilter(differenceImagePrefix));
/* 1146 */       for (File file : diffFiles) {
/* 1147 */         file.delete();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void printOutCmpDirectories() {
/* 1153 */     System.out.println("Out file folder: file://" + UrlUtil.toNormalizedURI((new File(this.outPdf)).getParentFile()).getPath());
/* 1154 */     System.out.println("Cmp file folder: file://" + UrlUtil.toNormalizedURI((new File(this.cmpPdf)).getParentFile()).getPath());
/*      */   }
/*      */   private String compareByContent(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws InterruptedException, IOException {
/*      */     PdfDocument outDocument, cmpDocument;
/* 1158 */     printOutCmpDirectories();
/* 1159 */     System.out.print("Comparing by content..........");
/*      */     
/*      */     try {
/* 1162 */       outDocument = new PdfDocument(new PdfReader(this.outPdf, getOutReaderProperties()), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/* 1163 */     } catch (IOException e) {
/* 1164 */       throw new IOException("File \"" + this.outPdf + "\" not found", e);
/*      */     } 
/* 1166 */     List<PdfDictionary> outPages = new ArrayList<>();
/* 1167 */     this.outPagesRef = new ArrayList<>();
/* 1168 */     loadPagesFromReader(outDocument, outPages, this.outPagesRef);
/*      */ 
/*      */     
/*      */     try {
/* 1172 */       cmpDocument = new PdfDocument(new PdfReader(this.cmpPdf, getCmpReaderProperties()), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/* 1173 */     } catch (IOException e) {
/* 1174 */       throw new IOException("File \"" + this.cmpPdf + "\" not found", e);
/*      */     } 
/* 1176 */     List<PdfDictionary> cmpPages = new ArrayList<>();
/* 1177 */     this.cmpPagesRef = new ArrayList<>();
/* 1178 */     loadPagesFromReader(cmpDocument, cmpPages, this.cmpPagesRef);
/*      */     
/* 1180 */     if (outPages.size() != cmpPages.size()) {
/* 1181 */       return compareVisuallyAndCombineReports("Documents have different numbers of pages.", outPath, differenceImagePrefix, ignoredAreas, null);
/*      */     }
/* 1183 */     CompareResult compareResult = new CompareResult(this.compareByContentErrorsLimit);
/* 1184 */     List<Integer> equalPages = new ArrayList<>(cmpPages.size());
/* 1185 */     for (int i = 0; i < cmpPages.size(); i++) {
/* 1186 */       ObjectPath currentPath = new ObjectPath(this.cmpPagesRef.get(i), this.outPagesRef.get(i));
/* 1187 */       if (compareDictionariesExtended(outPages.get(i), cmpPages.get(i), currentPath, compareResult)) {
/* 1188 */         equalPages.add(Integer.valueOf(i));
/*      */       }
/*      */     } 
/*      */     
/* 1192 */     ObjectPath catalogPath = new ObjectPath(((PdfDictionary)cmpDocument.getCatalog().getPdfObject()).getIndirectReference(), ((PdfDictionary)outDocument.getCatalog().getPdfObject()).getIndirectReference());
/* 1193 */     Set<PdfName> ignoredCatalogEntries = new LinkedHashSet<>(Arrays.asList(new PdfName[] { PdfName.Pages, PdfName.Metadata }));
/* 1194 */     compareDictionariesExtended((PdfDictionary)outDocument.getCatalog().getPdfObject(), (PdfDictionary)cmpDocument.getCatalog().getPdfObject(), catalogPath, compareResult, ignoredCatalogEntries);
/*      */ 
/*      */     
/* 1197 */     if (this.encryptionCompareEnabled) {
/* 1198 */       compareDocumentsEncryption(outDocument, cmpDocument, compareResult);
/*      */     }
/*      */     
/* 1201 */     outDocument.close();
/* 1202 */     cmpDocument.close();
/*      */     
/* 1204 */     if (this.generateCompareByContentXmlReport) {
/* 1205 */       String outPdfName = (new File(this.outPdf)).getName();
/* 1206 */       FileOutputStream xml = new FileOutputStream(outPath + "/" + outPdfName.substring(0, outPdfName.length() - 3) + "report.xml");
/*      */       try {
/* 1208 */         compareResult.writeReportToXml(xml);
/* 1209 */       } catch (Exception e) {
/* 1210 */         throw new RuntimeException(e.getMessage(), e);
/*      */       } finally {
/* 1212 */         xml.close();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1217 */     if (equalPages.size() == cmpPages.size() && compareResult.isOk()) {
/* 1218 */       System.out.println("OK");
/* 1219 */       System.out.flush();
/* 1220 */       return null;
/*      */     } 
/* 1222 */     return compareVisuallyAndCombineReports(compareResult.getReport(), outPath, differenceImagePrefix, ignoredAreas, equalPages);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String compareVisuallyAndCombineReports(String compareByFailContentReason, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas, List<Integer> equalPages) throws IOException, InterruptedException {
/* 1229 */     System.out.println("Fail");
/* 1230 */     System.out.flush();
/* 1231 */     String compareByContentReport = "Compare by content report:\n" + compareByFailContentReason;
/* 1232 */     System.out.println(compareByContentReport);
/* 1233 */     System.out.flush();
/* 1234 */     String message = compareVisually(outPath, differenceImagePrefix, ignoredAreas, equalPages);
/* 1235 */     if (message == null || message.length() == 0)
/* 1236 */       return "Compare by content fails. No visual differences"; 
/* 1237 */     return message;
/*      */   }
/*      */   
/*      */   private void loadPagesFromReader(PdfDocument doc, List<PdfDictionary> pages, List<PdfIndirectReference> pagesRef) {
/* 1241 */     int numOfPages = doc.getNumberOfPages();
/* 1242 */     for (int i = 0; i < numOfPages; i++) {
/* 1243 */       pages.add(doc.getPage(i + 1).getPdfObject());
/* 1244 */       pagesRef.add(((PdfDictionary)pages.get(i)).getIndirectReference());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void compareDocumentsEncryption(PdfDocument outDocument, PdfDocument cmpDocument, CompareResult compareResult) {
/* 1249 */     PdfDictionary outEncrypt = outDocument.getTrailer().getAsDictionary(PdfName.Encrypt);
/* 1250 */     PdfDictionary cmpEncrypt = cmpDocument.getTrailer().getAsDictionary(PdfName.Encrypt);
/*      */     
/* 1252 */     if (outEncrypt == null && cmpEncrypt == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1256 */     TrailerPath trailerPath = new TrailerPath(cmpDocument, outDocument);
/* 1257 */     if (outEncrypt == null) {
/* 1258 */       compareResult.addError(trailerPath, "Expected encrypted document.");
/*      */       return;
/*      */     } 
/* 1261 */     if (cmpEncrypt == null) {
/* 1262 */       compareResult.addError(trailerPath, "Expected not encrypted document.");
/*      */       
/*      */       return;
/*      */     } 
/* 1266 */     Set<PdfName> ignoredEncryptEntries = new LinkedHashSet<>(Arrays.asList(new PdfName[] { PdfName.O, PdfName.U, PdfName.OE, PdfName.UE, PdfName.Perms, PdfName.CF, PdfName.Recipients }));
/* 1267 */     ObjectPath objectPath = new ObjectPath(outEncrypt.getIndirectReference(), cmpEncrypt.getIndirectReference());
/* 1268 */     compareDictionariesExtended(outEncrypt, cmpEncrypt, objectPath, compareResult, ignoredEncryptEntries);
/*      */     
/* 1270 */     PdfDictionary outCfDict = outEncrypt.getAsDictionary(PdfName.CF);
/* 1271 */     PdfDictionary cmpCfDict = cmpEncrypt.getAsDictionary(PdfName.CF);
/* 1272 */     if (cmpCfDict != null || outCfDict != null)
/* 1273 */       if ((cmpCfDict != null && outCfDict == null) || cmpCfDict == null) {
/* 1274 */         compareResult.addError(objectPath, "One of the dictionaries is null, the other is not.");
/*      */       } else {
/* 1276 */         Set<PdfName> mergedKeys = new TreeSet<>(outCfDict.keySet());
/* 1277 */         mergedKeys.addAll(cmpCfDict.keySet());
/* 1278 */         for (PdfName key : mergedKeys) {
/* 1279 */           objectPath.pushDictItemToPath(key);
/* 1280 */           LinkedHashSet<PdfName> excludedKeys = new LinkedHashSet<>(Arrays.asList(new PdfName[] { PdfName.Recipients }));
/* 1281 */           compareDictionariesExtended(outCfDict.getAsDictionary(key), cmpCfDict.getAsDictionary(key), objectPath, compareResult, excludedKeys);
/* 1282 */           objectPath.pop();
/*      */         } 
/*      */       }  
/*      */   }
/*      */   
/*      */   private boolean compareStreams(InputStream is1, InputStream is2) throws IOException {
/*      */     int len1;
/* 1289 */     byte[] buffer1 = new byte[65536];
/* 1290 */     byte[] buffer2 = new byte[65536];
/*      */ 
/*      */     
/*      */     do {
/* 1294 */       len1 = is1.read(buffer1);
/* 1295 */       int len2 = is2.read(buffer2);
/* 1296 */       if (len1 != len2)
/* 1297 */         return false; 
/* 1298 */       if (!Arrays.equals(buffer1, buffer2))
/* 1299 */         return false; 
/* 1300 */     } while (len1 != -1);
/*      */ 
/*      */     
/* 1303 */     return true;
/*      */   }
/*      */   
/*      */   private boolean compareDictionariesExtended(PdfDictionary outDict, PdfDictionary cmpDict, ObjectPath currentPath, CompareResult compareResult) {
/* 1307 */     return compareDictionariesExtended(outDict, cmpDict, currentPath, compareResult, null);
/*      */   }
/*      */   
/*      */   private boolean compareDictionariesExtended(PdfDictionary outDict, PdfDictionary cmpDict, ObjectPath currentPath, CompareResult compareResult, Set<PdfName> excludedKeys) {
/* 1311 */     if ((cmpDict != null && outDict == null) || (outDict != null && cmpDict == null)) {
/* 1312 */       compareResult.addError(currentPath, "One of the dictionaries is null, the other is not.");
/* 1313 */       return false;
/*      */     } 
/* 1315 */     boolean dictsAreSame = true;
/*      */     
/* 1317 */     Set<PdfName> mergedKeys = new TreeSet<>(cmpDict.keySet());
/* 1318 */     mergedKeys.addAll(outDict.keySet());
/* 1319 */     for (PdfName key : mergedKeys) {
/* 1320 */       if (!dictsAreSame && (currentPath == null || compareResult == null || compareResult.isMessageLimitReached())) {
/* 1321 */         return false;
/*      */       }
/*      */       
/* 1324 */       if (excludedKeys != null && excludedKeys.contains(key)) {
/*      */         continue;
/*      */       }
/* 1327 */       if (key.equals(PdfName.Parent) || key.equals(PdfName.P) || key.equals(PdfName.ModDate) || (
/* 1328 */         outDict.isStream() && cmpDict.isStream() && (key.equals(PdfName.Filter) || key.equals(PdfName.Length))))
/*      */         continue; 
/* 1330 */       if (key.equals(PdfName.BaseFont) || key.equals(PdfName.FontName)) {
/* 1331 */         PdfObject cmpObj = cmpDict.get(key);
/* 1332 */         if (cmpObj != null && cmpObj.isName() && cmpObj.toString().indexOf('+') > 0) {
/* 1333 */           PdfObject outObj = outDict.get(key);
/* 1334 */           if (!outObj.isName() || outObj.toString().indexOf('+') == -1) {
/* 1335 */             if (compareResult != null && currentPath != null)
/* 1336 */               compareResult.addError(currentPath, MessageFormatUtil.format("PdfDictionary {0} entry: Expected: {1}. Found: {2}", new Object[] { key.toString(), cmpObj.toString(), outObj.toString() })); 
/* 1337 */             dictsAreSame = false; continue;
/*      */           } 
/* 1339 */           String cmpName = cmpObj.toString().substring(cmpObj.toString().indexOf('+'));
/* 1340 */           String outName = outObj.toString().substring(outObj.toString().indexOf('+'));
/* 1341 */           if (!cmpName.equals(outName)) {
/* 1342 */             if (compareResult != null && currentPath != null)
/* 1343 */               compareResult.addError(currentPath, MessageFormatUtil.format("PdfDictionary {0} entry: Expected: {1}. Found: {2}", new Object[] { key.toString(), cmpObj.toString(), outObj.toString() })); 
/* 1344 */             dictsAreSame = false;
/*      */           } 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       
/* 1352 */       if (key.equals(PdfName.ParentTree) || key.equals(PdfName.PageLabels)) {
/* 1353 */         if (currentPath != null) {
/* 1354 */           currentPath.pushDictItemToPath(key);
/*      */         }
/* 1356 */         PdfDictionary outNumTree = outDict.getAsDictionary(key);
/* 1357 */         PdfDictionary cmpNumTree = cmpDict.getAsDictionary(key);
/* 1358 */         LinkedList<PdfObject> outItems = new LinkedList<>();
/* 1359 */         LinkedList<PdfObject> cmpItems = new LinkedList<>();
/* 1360 */         PdfNumber outLeftover = flattenNumTree(outNumTree, null, outItems);
/* 1361 */         PdfNumber cmpLeftover = flattenNumTree(cmpNumTree, null, cmpItems);
/* 1362 */         if (outLeftover != null) {
/* 1363 */           LoggerFactory.getLogger(CompareTool.class).warn("Number tree ends with a key which is invalid according to the PDF specification.");
/* 1364 */           if (cmpLeftover == null) {
/* 1365 */             if (compareResult != null && currentPath != null) {
/* 1366 */               compareResult.addError(currentPath, "Number tree unexpectedly ends with a key");
/*      */             }
/* 1368 */             dictsAreSame = false;
/*      */           } 
/*      */         } 
/* 1371 */         if (cmpLeftover != null) {
/* 1372 */           LoggerFactory.getLogger(CompareTool.class).warn("Number tree ends with a key which is invalid according to the PDF specification.");
/* 1373 */           if (outLeftover == null) {
/* 1374 */             if (compareResult != null && currentPath != null) {
/* 1375 */               compareResult.addError(currentPath, "Number tree was expected to end with a key (although it is invalid according to the specification), but ended with a value");
/*      */             }
/* 1377 */             dictsAreSame = false;
/*      */           } 
/*      */         } 
/* 1380 */         if (outLeftover != null && cmpLeftover != null && !compareNumbers(outLeftover, cmpLeftover)) {
/* 1381 */           if (compareResult != null && currentPath != null) {
/* 1382 */             compareResult.addError(currentPath, "Number tree was expected to end with a different key (although it is invalid according to the specification)");
/*      */           }
/* 1384 */           dictsAreSame = false;
/*      */         } 
/* 1386 */         PdfArray outArray = new PdfArray(outItems, outItems.size());
/* 1387 */         PdfArray cmpArray = new PdfArray(cmpItems, cmpItems.size());
/* 1388 */         if (!compareArraysExtended(outArray, cmpArray, currentPath, compareResult)) {
/* 1389 */           if (compareResult != null && currentPath != null) {
/* 1390 */             compareResult.addError(currentPath, "Number trees were flattened, compared and found to be different.");
/*      */           }
/* 1392 */           dictsAreSame = false;
/*      */         } 
/*      */         
/* 1395 */         if (currentPath != null) {
/* 1396 */           currentPath.pop();
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/* 1401 */       if (currentPath != null) {
/* 1402 */         currentPath.pushDictItemToPath(key);
/*      */       }
/* 1404 */       dictsAreSame = (compareObjects(outDict.get(key, false), cmpDict.get(key, false), currentPath, compareResult) && dictsAreSame);
/* 1405 */       if (currentPath != null) {
/* 1406 */         currentPath.pop();
/*      */       }
/*      */     } 
/* 1409 */     return dictsAreSame;
/*      */   }
/*      */   
/*      */   private PdfNumber flattenNumTree(PdfDictionary dictionary, PdfNumber leftOver, LinkedList<PdfObject> items) {
/* 1413 */     PdfArray nums = dictionary.getAsArray(PdfName.Nums);
/* 1414 */     if (nums != null) {
/* 1415 */       for (int k = 0; k < nums.size(); k++) {
/*      */         PdfNumber number;
/* 1417 */         if (leftOver == null) {
/* 1418 */           number = nums.getAsNumber(k++);
/*      */         } else {
/* 1420 */           number = leftOver;
/* 1421 */           leftOver = null;
/*      */         } 
/* 1423 */         if (k < nums.size()) {
/* 1424 */           items.addLast(number);
/* 1425 */           items.addLast(nums.get(k, false));
/*      */         } else {
/* 1427 */           return number;
/*      */         } 
/*      */       } 
/* 1430 */     } else if ((nums = dictionary.getAsArray(PdfName.Kids)) != null) {
/* 1431 */       for (int k = 0; k < nums.size(); k++) {
/* 1432 */         PdfDictionary kid = nums.getAsDictionary(k);
/* 1433 */         leftOver = flattenNumTree(kid, leftOver, items);
/*      */       } 
/*      */     } 
/* 1436 */     return null;
/*      */   }
/*      */   
/*      */   protected boolean compareObjects(PdfObject outObj, PdfObject cmpObj, ObjectPath currentPath, CompareResult compareResult) {
/* 1440 */     PdfObject outDirectObj = null;
/* 1441 */     PdfObject cmpDirectObj = null;
/* 1442 */     if (outObj != null)
/* 1443 */       outDirectObj = outObj.isIndirectReference() ? ((PdfIndirectReference)outObj).getRefersTo(false) : outObj; 
/* 1444 */     if (cmpObj != null) {
/* 1445 */       cmpDirectObj = cmpObj.isIndirectReference() ? ((PdfIndirectReference)cmpObj).getRefersTo(false) : cmpObj;
/*      */     }
/* 1447 */     if (cmpDirectObj == null && outDirectObj == null) {
/* 1448 */       return true;
/*      */     }
/* 1450 */     if (outDirectObj == null) {
/* 1451 */       compareResult.addError(currentPath, "Expected object was not found.");
/* 1452 */       return false;
/* 1453 */     }  if (cmpDirectObj == null) {
/* 1454 */       compareResult.addError(currentPath, "Found object which was not expected to be found.");
/* 1455 */       return false;
/* 1456 */     }  if (cmpDirectObj.getType() != outDirectObj.getType()) {
/* 1457 */       compareResult.addError(currentPath, MessageFormatUtil.format("Types do not match. Expected: {0}. Found: {1}.", new Object[] { cmpDirectObj.getClass().getSimpleName(), outDirectObj.getClass().getSimpleName() }));
/* 1458 */       return false;
/* 1459 */     }  if (cmpObj.isIndirectReference() && !outObj.isIndirectReference()) {
/* 1460 */       compareResult.addError(currentPath, "Expected indirect object.");
/* 1461 */       return false;
/* 1462 */     }  if (!cmpObj.isIndirectReference() && outObj.isIndirectReference()) {
/* 1463 */       compareResult.addError(currentPath, "Expected direct object.");
/* 1464 */       return false;
/*      */     } 
/*      */     
/* 1467 */     if (currentPath != null && cmpObj.isIndirectReference() && outObj.isIndirectReference()) {
/* 1468 */       if (currentPath.isComparing((PdfIndirectReference)cmpObj, (PdfIndirectReference)outObj))
/* 1469 */         return true; 
/* 1470 */       currentPath = currentPath.resetDirectPath((PdfIndirectReference)cmpObj, (PdfIndirectReference)outObj);
/*      */     } 
/*      */     
/* 1473 */     if (cmpDirectObj.isDictionary() && PdfName.Page.equals(((PdfDictionary)cmpDirectObj).getAsName(PdfName.Type)) && this.useCachedPagesForComparison) {
/*      */       
/* 1475 */       if (!outDirectObj.isDictionary() || !PdfName.Page.equals(((PdfDictionary)outDirectObj).getAsName(PdfName.Type))) {
/* 1476 */         if (compareResult != null && currentPath != null)
/* 1477 */           compareResult.addError(currentPath, "Expected a page. Found not a page."); 
/* 1478 */         return false;
/*      */       } 
/* 1480 */       PdfIndirectReference cmpRefKey = cmpObj.isIndirectReference() ? (PdfIndirectReference)cmpObj : cmpObj.getIndirectReference();
/* 1481 */       PdfIndirectReference outRefKey = outObj.isIndirectReference() ? (PdfIndirectReference)outObj : outObj.getIndirectReference();
/*      */       
/* 1483 */       if (this.cmpPagesRef == null) {
/* 1484 */         this.cmpPagesRef = new ArrayList<>();
/* 1485 */         for (int i = 1; i <= cmpRefKey.getDocument().getNumberOfPages(); i++) {
/* 1486 */           this.cmpPagesRef.add(((PdfDictionary)cmpRefKey.getDocument().getPage(i).getPdfObject()).getIndirectReference());
/*      */         }
/*      */       } 
/* 1489 */       if (this.outPagesRef == null) {
/* 1490 */         this.outPagesRef = new ArrayList<>();
/* 1491 */         for (int i = 1; i <= outRefKey.getDocument().getNumberOfPages(); i++) {
/* 1492 */           this.outPagesRef.add(((PdfDictionary)outRefKey.getDocument().getPage(i).getPdfObject()).getIndirectReference());
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1499 */       if (this.cmpPagesRef.contains(cmpRefKey) || this.outPagesRef.contains(outRefKey)) {
/* 1500 */         if (this.cmpPagesRef.contains(cmpRefKey) && this.cmpPagesRef.indexOf(cmpRefKey) == this.outPagesRef.indexOf(outRefKey)) {
/* 1501 */           return true;
/*      */         }
/* 1503 */         if (compareResult != null && currentPath != null)
/* 1504 */           compareResult.addError(currentPath, MessageFormatUtil.format("The dictionaries refer to different pages. Expected page number: {0}. Found: {1}", new Object[] {
/* 1505 */                   Integer.valueOf(this.cmpPagesRef.indexOf(cmpRefKey) + 1), Integer.valueOf(this.outPagesRef.indexOf(outRefKey) + 1) })); 
/* 1506 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1510 */     if (cmpDirectObj.isDictionary())
/* 1511 */       return compareDictionariesExtended((PdfDictionary)outDirectObj, (PdfDictionary)cmpDirectObj, currentPath, compareResult); 
/* 1512 */     if (cmpDirectObj.isStream())
/* 1513 */       return compareStreamsExtended((PdfStream)outDirectObj, (PdfStream)cmpDirectObj, currentPath, compareResult); 
/* 1514 */     if (cmpDirectObj.isArray())
/* 1515 */       return compareArraysExtended((PdfArray)outDirectObj, (PdfArray)cmpDirectObj, currentPath, compareResult); 
/* 1516 */     if (cmpDirectObj.isName())
/* 1517 */       return compareNamesExtended((PdfName)outDirectObj, (PdfName)cmpDirectObj, currentPath, compareResult); 
/* 1518 */     if (cmpDirectObj.isNumber())
/* 1519 */       return compareNumbersExtended((PdfNumber)outDirectObj, (PdfNumber)cmpDirectObj, currentPath, compareResult); 
/* 1520 */     if (cmpDirectObj.isString())
/* 1521 */       return compareStringsExtended((PdfString)outDirectObj, (PdfString)cmpDirectObj, currentPath, compareResult); 
/* 1522 */     if (cmpDirectObj.isBoolean())
/* 1523 */       return compareBooleansExtended((PdfBoolean)outDirectObj, (PdfBoolean)cmpDirectObj, currentPath, compareResult); 
/* 1524 */     if (outDirectObj.isNull() && cmpDirectObj.isNull()) {
/* 1525 */       return true;
/*      */     }
/* 1527 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean compareStreamsExtended(PdfStream outStream, PdfStream cmpStream, ObjectPath currentPath, CompareResult compareResult) {
/* 1532 */     boolean toDecode = PdfName.FlateDecode.equals(outStream.get(PdfName.Filter));
/* 1533 */     byte[] outStreamBytes = outStream.getBytes(toDecode);
/* 1534 */     byte[] cmpStreamBytes = cmpStream.getBytes(toDecode);
/* 1535 */     if (Arrays.equals(outStreamBytes, cmpStreamBytes)) {
/* 1536 */       return compareDictionariesExtended((PdfDictionary)outStream, (PdfDictionary)cmpStream, currentPath, compareResult);
/*      */     }
/* 1538 */     StringBuilder errorMessage = new StringBuilder();
/* 1539 */     if (cmpStreamBytes.length != outStreamBytes.length) {
/* 1540 */       errorMessage.append(MessageFormatUtil.format("PdfStream. Lengths are different. Expected: {0}. Found: {1}\n", new Object[] { Integer.valueOf(cmpStreamBytes.length), Integer.valueOf(outStreamBytes.length) }));
/*      */     } else {
/* 1542 */       errorMessage.append("PdfStream. Bytes are different.\n");
/*      */     } 
/* 1544 */     int firstDifferenceOffset = findBytesDifference(outStreamBytes, cmpStreamBytes, errorMessage);
/*      */     
/* 1546 */     if (compareResult != null && currentPath != null) {
/* 1547 */       currentPath.pushOffsetToPath(firstDifferenceOffset);
/* 1548 */       compareResult.addError(currentPath, errorMessage.toString());
/* 1549 */       currentPath.pop();
/*      */     } 
/* 1551 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int findBytesDifference(byte[] outStreamBytes, byte[] cmpStreamBytes, StringBuilder errorMessage) {
/* 1559 */     int numberOfDifferentBytes = 0;
/* 1560 */     int firstDifferenceOffset = 0;
/* 1561 */     int minLength = Math.min(cmpStreamBytes.length, outStreamBytes.length);
/* 1562 */     for (int i = 0; i < minLength; i++) {
/*      */       
/* 1564 */       numberOfDifferentBytes++;
/* 1565 */       if (cmpStreamBytes[i] != outStreamBytes[i] && numberOfDifferentBytes == 1) {
/* 1566 */         firstDifferenceOffset = i;
/*      */       }
/*      */     } 
/*      */     
/* 1570 */     String bytesDifference = null;
/* 1571 */     if (numberOfDifferentBytes > 0) {
/* 1572 */       int diffBytesAreaL = 10;
/* 1573 */       int diffBytesAreaR = 10;
/* 1574 */       int lCmp = Math.max(0, firstDifferenceOffset - diffBytesAreaL);
/* 1575 */       int rCmp = Math.min(cmpStreamBytes.length, firstDifferenceOffset + diffBytesAreaR);
/* 1576 */       int lOut = Math.max(0, firstDifferenceOffset - diffBytesAreaL);
/* 1577 */       int rOut = Math.min(outStreamBytes.length, firstDifferenceOffset + diffBytesAreaR);
/*      */ 
/*      */       
/* 1580 */       String cmpByte = new String(new byte[] { cmpStreamBytes[firstDifferenceOffset] }, StandardCharsets.ISO_8859_1);
/* 1581 */       String cmpByteNeighbours = (new String(cmpStreamBytes, lCmp, rCmp - lCmp, StandardCharsets.ISO_8859_1)).replaceAll("\\r|\\n", " ");
/* 1582 */       String outByte = new String(new byte[] { outStreamBytes[firstDifferenceOffset] }, StandardCharsets.ISO_8859_1);
/* 1583 */       String outBytesNeighbours = (new String(outStreamBytes, lOut, rOut - lOut, StandardCharsets.ISO_8859_1)).replaceAll("\\r|\\n", " ");
/* 1584 */       bytesDifference = MessageFormatUtil.format("First bytes difference is encountered at index {0}. Expected: {1} ({2}). Found: {3} ({4}). Total number of different bytes: {5}", new Object[] {
/* 1585 */             Integer.valueOf(firstDifferenceOffset).toString(), cmpByte, cmpByteNeighbours, outByte, outBytesNeighbours, Integer.valueOf(numberOfDifferentBytes)
/*      */           });
/*      */     } else {
/* 1588 */       firstDifferenceOffset = minLength;
/* 1589 */       bytesDifference = MessageFormatUtil.format("Bytes of the shorter array are the same as the first {0} bytes of the longer one.", new Object[] { Integer.valueOf(minLength) });
/*      */     } 
/*      */     
/* 1592 */     errorMessage.append(bytesDifference);
/* 1593 */     return firstDifferenceOffset;
/*      */   }
/*      */   
/*      */   private boolean compareArraysExtended(PdfArray outArray, PdfArray cmpArray, ObjectPath currentPath, CompareResult compareResult) {
/* 1597 */     if (outArray == null) {
/* 1598 */       if (compareResult != null && currentPath != null)
/* 1599 */         compareResult.addError(currentPath, "Found null. Expected PdfArray."); 
/* 1600 */       return false;
/* 1601 */     }  if (outArray.size() != cmpArray.size()) {
/* 1602 */       if (compareResult != null && currentPath != null)
/* 1603 */         compareResult.addError(currentPath, MessageFormatUtil.format("PdfArrays. Lengths are different. Expected: {0}. Found: {1}.", new Object[] { Integer.valueOf(cmpArray.size()), Integer.valueOf(outArray.size()) })); 
/* 1604 */       return false;
/*      */     } 
/* 1606 */     boolean arraysAreEqual = true;
/* 1607 */     for (int i = 0; i < cmpArray.size(); i++) {
/* 1608 */       if (currentPath != null)
/* 1609 */         currentPath.pushArrayItemToPath(i); 
/* 1610 */       arraysAreEqual = (compareObjects(outArray.get(i, false), cmpArray.get(i, false), currentPath, compareResult) && arraysAreEqual);
/* 1611 */       if (currentPath != null)
/* 1612 */         currentPath.pop(); 
/* 1613 */       if (!arraysAreEqual && (currentPath == null || compareResult == null || compareResult.isMessageLimitReached())) {
/* 1614 */         return false;
/*      */       }
/*      */     } 
/* 1617 */     return arraysAreEqual;
/*      */   }
/*      */   
/*      */   private boolean compareNamesExtended(PdfName outName, PdfName cmpName, ObjectPath currentPath, CompareResult compareResult) {
/* 1621 */     if (cmpName.equals(outName)) {
/* 1622 */       return true;
/*      */     }
/* 1624 */     if (compareResult != null && currentPath != null)
/* 1625 */       compareResult.addError(currentPath, MessageFormatUtil.format("PdfName. Expected: {0}. Found: {1}", new Object[] { cmpName.toString(), outName.toString() })); 
/* 1626 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean compareNumbersExtended(PdfNumber outNumber, PdfNumber cmpNumber, ObjectPath currentPath, CompareResult compareResult) {
/* 1631 */     if (cmpNumber.getValue() == outNumber.getValue()) {
/* 1632 */       return true;
/*      */     }
/* 1634 */     if (compareResult != null && currentPath != null)
/* 1635 */       compareResult.addError(currentPath, MessageFormatUtil.format("PdfNumber. Expected: {0}. Found: {1}", new Object[] { cmpNumber, outNumber })); 
/* 1636 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean compareStringsExtended(PdfString outString, PdfString cmpString, ObjectPath currentPath, CompareResult compareResult) {
/* 1641 */     if (Arrays.equals(convertPdfStringToBytes(cmpString), convertPdfStringToBytes(outString))) {
/* 1642 */       return true;
/*      */     }
/* 1644 */     String cmpStr = cmpString.toUnicodeString();
/* 1645 */     String outStr = outString.toUnicodeString();
/* 1646 */     StringBuilder errorMessage = new StringBuilder();
/* 1647 */     if (cmpStr.length() != outStr.length()) {
/* 1648 */       errorMessage.append(MessageFormatUtil.format("PdfString. Lengths are different. Expected: {0}. Found: {1}\n", new Object[] { Integer.valueOf(cmpStr.length()), Integer.valueOf(outStr.length()) }));
/*      */     } else {
/* 1650 */       errorMessage.append("PdfString. Characters are different.\n");
/*      */     } 
/* 1652 */     int firstDifferenceOffset = findStringDifference(outStr, cmpStr, errorMessage);
/*      */     
/* 1654 */     if (compareResult != null && currentPath != null) {
/* 1655 */       currentPath.pushOffsetToPath(firstDifferenceOffset);
/* 1656 */       compareResult.addError(currentPath, errorMessage.toString());
/* 1657 */       currentPath.pop();
/*      */     } 
/* 1659 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private int findStringDifference(String outString, String cmpString, StringBuilder errorMessage) {
/* 1664 */     int numberOfDifferentChars = 0;
/* 1665 */     int firstDifferenceOffset = 0;
/* 1666 */     int minLength = Math.min(cmpString.length(), outString.length());
/* 1667 */     for (int i = 0; i < minLength; i++) {
/*      */       
/* 1669 */       numberOfDifferentChars++;
/* 1670 */       if (cmpString.charAt(i) != outString.charAt(i) && numberOfDifferentChars == 1) {
/* 1671 */         firstDifferenceOffset = i;
/*      */       }
/*      */     } 
/*      */     
/* 1675 */     String stringDifference = null;
/* 1676 */     if (numberOfDifferentChars > 0) {
/* 1677 */       int diffBytesAreaL = 15;
/* 1678 */       int diffBytesAreaR = 15;
/* 1679 */       int lCmp = Math.max(0, firstDifferenceOffset - diffBytesAreaL);
/* 1680 */       int rCmp = Math.min(cmpString.length(), firstDifferenceOffset + diffBytesAreaR);
/* 1681 */       int lOut = Math.max(0, firstDifferenceOffset - diffBytesAreaL);
/* 1682 */       int rOut = Math.min(outString.length(), firstDifferenceOffset + diffBytesAreaR);
/*      */ 
/*      */       
/* 1685 */       String cmpByte = String.valueOf(cmpString.charAt(firstDifferenceOffset));
/* 1686 */       String cmpByteNeighbours = cmpString.substring(lCmp, rCmp).replaceAll("\\r|\\n", " ");
/* 1687 */       String outByte = String.valueOf(outString.charAt(firstDifferenceOffset));
/* 1688 */       String outBytesNeighbours = outString.substring(lOut, rOut).replaceAll("\\r|\\n", " ");
/* 1689 */       stringDifference = MessageFormatUtil.format("First characters difference is encountered at index {0}.\nExpected: {1} ({2}).\nFound: {3} ({4}).\nTotal number of different characters: {5}", new Object[] {
/* 1690 */             Integer.valueOf(firstDifferenceOffset).toString(), cmpByte, cmpByteNeighbours, outByte, outBytesNeighbours, Integer.valueOf(numberOfDifferentChars)
/*      */           });
/*      */     } else {
/*      */       
/* 1694 */       firstDifferenceOffset = minLength;
/* 1695 */       stringDifference = MessageFormatUtil.format("All characters of the shorter string are the same as the first {0} characters of the longer one.", new Object[] { Integer.valueOf(minLength) });
/*      */     } 
/*      */     
/* 1698 */     errorMessage.append(stringDifference);
/* 1699 */     return firstDifferenceOffset;
/*      */   }
/*      */   
/*      */   private byte[] convertPdfStringToBytes(PdfString pdfString) {
/*      */     byte[] bytes;
/* 1704 */     String value = pdfString.getValue();
/* 1705 */     String encoding = pdfString.getEncoding();
/* 1706 */     if (encoding != null && "UnicodeBig".equals(encoding) && PdfEncodings.isPdfDocEncoding(value)) {
/* 1707 */       bytes = PdfEncodings.convertToBytes(value, "PDF");
/*      */     } else {
/* 1709 */       bytes = PdfEncodings.convertToBytes(value, encoding);
/* 1710 */     }  return bytes;
/*      */   }
/*      */   
/*      */   private boolean compareBooleansExtended(PdfBoolean outBoolean, PdfBoolean cmpBoolean, ObjectPath currentPath, CompareResult compareResult) {
/* 1714 */     if (cmpBoolean.getValue() == outBoolean.getValue()) {
/* 1715 */       return true;
/*      */     }
/* 1717 */     if (compareResult != null && currentPath != null)
/* 1718 */       compareResult.addError(currentPath, MessageFormatUtil.format("PdfBoolean. Expected: {0}. Found: {1}.", new Object[] { Boolean.valueOf(cmpBoolean.getValue()), Boolean.valueOf(outBoolean.getValue()) })); 
/* 1719 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private List<PdfLinkAnnotation> getLinkAnnotations(int pageNum, PdfDocument document) {
/* 1724 */     List<PdfLinkAnnotation> linkAnnotations = new ArrayList<>();
/* 1725 */     List<PdfAnnotation> annotations = document.getPage(pageNum).getAnnotations();
/* 1726 */     for (PdfAnnotation annotation : annotations) {
/* 1727 */       if (PdfName.Link.equals(annotation.getSubtype())) {
/* 1728 */         linkAnnotations.add((PdfLinkAnnotation)annotation);
/*      */       }
/*      */     } 
/* 1731 */     return linkAnnotations;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean compareLinkAnnotations(PdfLinkAnnotation cmpLink, PdfLinkAnnotation outLink, PdfDocument cmpDocument, PdfDocument outDocument) {
/* 1736 */     PdfObject cmpDestObject = cmpLink.getDestinationObject();
/* 1737 */     PdfObject outDestObject = outLink.getDestinationObject();
/*      */     
/* 1739 */     if (cmpDestObject != null && outDestObject != null) {
/* 1740 */       if (cmpDestObject.getType() != outDestObject.getType()) {
/* 1741 */         return false;
/*      */       }
/* 1743 */       PdfArray explicitCmpDest = null;
/* 1744 */       PdfArray explicitOutDest = null;
/* 1745 */       Map<String, PdfObject> cmpNamedDestinations = cmpDocument.getCatalog().getNameTree(PdfName.Dests).getNames();
/* 1746 */       Map<String, PdfObject> outNamedDestinations = outDocument.getCatalog().getNameTree(PdfName.Dests).getNames();
/* 1747 */       switch (cmpDestObject.getType()) {
/*      */         case 1:
/* 1749 */           explicitCmpDest = (PdfArray)cmpDestObject;
/* 1750 */           explicitOutDest = (PdfArray)outDestObject;
/*      */           break;
/*      */         case 6:
/* 1753 */           explicitCmpDest = (PdfArray)cmpNamedDestinations.get(((PdfName)cmpDestObject).getValue());
/* 1754 */           explicitOutDest = (PdfArray)outNamedDestinations.get(((PdfName)outDestObject).getValue());
/*      */           break;
/*      */         case 10:
/* 1757 */           explicitCmpDest = (PdfArray)cmpNamedDestinations.get(((PdfString)cmpDestObject).toUnicodeString());
/* 1758 */           explicitOutDest = (PdfArray)outNamedDestinations.get(((PdfString)outDestObject).toUnicodeString());
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1764 */       if (getExplicitDestinationPageNum(explicitCmpDest) != getExplicitDestinationPageNum(explicitOutDest)) {
/* 1765 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1770 */     PdfDictionary cmpDict = (PdfDictionary)cmpLink.getPdfObject();
/* 1771 */     PdfDictionary outDict = (PdfDictionary)outLink.getPdfObject();
/* 1772 */     if (cmpDict.size() != outDict.size()) {
/* 1773 */       return false;
/*      */     }
/* 1775 */     Rectangle cmpRect = cmpDict.getAsRectangle(PdfName.Rect);
/* 1776 */     Rectangle outRect = outDict.getAsRectangle(PdfName.Rect);
/*      */     
/* 1778 */     if (cmpRect.getHeight() != outRect.getHeight() || cmpRect
/* 1779 */       .getWidth() != outRect.getWidth() || cmpRect
/* 1780 */       .getX() != outRect.getX() || cmpRect
/* 1781 */       .getY() != outRect.getY()) {
/* 1782 */       return false;
/*      */     }
/* 1784 */     for (Map.Entry<PdfName, PdfObject> cmpEntry : (Iterable<Map.Entry<PdfName, PdfObject>>)cmpDict.entrySet()) {
/* 1785 */       PdfObject cmpObj = cmpEntry.getValue();
/* 1786 */       if (!outDict.containsKey(cmpEntry.getKey()))
/* 1787 */         return false; 
/* 1788 */       PdfObject outObj = outDict.get(cmpEntry.getKey());
/* 1789 */       if (cmpObj.getType() != outObj.getType()) {
/* 1790 */         return false;
/*      */       }
/* 1792 */       switch (cmpObj.getType()) {
/*      */         case 2:
/*      */         case 6:
/*      */         case 7:
/*      */         case 8:
/*      */         case 10:
/* 1798 */           if (!cmpObj.toString().equals(outObj.toString())) {
/* 1799 */             return false;
/*      */           }
/*      */       } 
/*      */     } 
/* 1803 */     return true;
/*      */   }
/*      */   public CompareTool() {}
/*      */   private int getExplicitDestinationPageNum(PdfArray explicitDest) {
/* 1807 */     PdfIndirectReference pageReference = (PdfIndirectReference)explicitDest.get(0, false);
/*      */     
/* 1809 */     PdfDocument doc = pageReference.getDocument();
/* 1810 */     for (int i = 1; i <= doc.getNumberOfPages(); i++) {
/* 1811 */       if (((PdfDictionary)doc.getPage(i).getPdfObject()).getIndirectReference().equals(pageReference))
/* 1812 */         return i; 
/*      */     } 
/* 1814 */     throw new IllegalArgumentException("PdfLinkAnnotation comparison: Page not found.");
/*      */   }
/*      */   
/*      */   private class PngFileFilter implements FileFilter { private PngFileFilter() {}
/*      */     
/*      */     public boolean accept(File pathname) {
/* 1820 */       String ap = pathname.getName();
/* 1821 */       boolean b1 = ap.endsWith(".png");
/* 1822 */       boolean b2 = ap.contains("cmp_");
/* 1823 */       return (b1 && !b2 && ap.contains(CompareTool.this.outPdfName));
/*      */     } }
/*      */   
/*      */   private class CmpPngFileFilter implements FileFilter { private CmpPngFileFilter() {}
/*      */     
/*      */     public boolean accept(File pathname) {
/* 1829 */       String ap = pathname.getName();
/* 1830 */       boolean b1 = ap.endsWith(".png");
/* 1831 */       boolean b2 = ap.contains("cmp_");
/* 1832 */       return (b1 && b2 && ap.contains(CompareTool.this.cmpPdfName));
/*      */     } }
/*      */ 
/*      */   
/*      */   private class DiffPngFileFilter implements FileFilter {
/*      */     private String differenceImagePrefix;
/*      */     
/*      */     public DiffPngFileFilter(String differenceImagePrefix) {
/* 1840 */       this.differenceImagePrefix = differenceImagePrefix;
/*      */     }
/*      */     
/*      */     public boolean accept(File pathname) {
/* 1844 */       String ap = pathname.getName();
/* 1845 */       boolean b1 = ap.endsWith(".png");
/* 1846 */       boolean b2 = ap.startsWith(this.differenceImagePrefix);
/* 1847 */       return (b1 && b2);
/*      */     } }
/*      */   
/*      */   private class ImageNameComparator implements Comparator<File> { private ImageNameComparator() {}
/*      */     
/*      */     public int compare(File f1, File f2) {
/* 1853 */       String f1Name = f1.getName();
/* 1854 */       String f2Name = f2.getName();
/* 1855 */       return f1Name.compareTo(f2Name);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class CompareResult
/*      */   {
/* 1864 */     protected Map<CompareTool.ObjectPath, String> differences = new LinkedHashMap<>();
/* 1865 */     protected int messageLimit = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CompareResult(int messageLimit) {
/* 1873 */       this.messageLimit = messageLimit;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isOk() {
/* 1882 */       return (this.differences.size() == 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getErrorCount() {
/* 1891 */       return this.differences.size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getReport() {
/* 1900 */       StringBuilder sb = new StringBuilder();
/* 1901 */       boolean firstEntry = true;
/* 1902 */       for (Map.Entry<CompareTool.ObjectPath, String> entry : this.differences.entrySet()) {
/* 1903 */         if (!firstEntry)
/* 1904 */           sb.append("-----------------------------").append("\n"); 
/* 1905 */         CompareTool.ObjectPath diffPath = entry.getKey();
/* 1906 */         sb.append(entry.getValue()).append("\n").append(diffPath.toString()).append("\n");
/* 1907 */         firstEntry = false;
/*      */       } 
/* 1909 */       return sb.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<CompareTool.ObjectPath, String> getDifferences() {
/* 1918 */       return this.differences;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeReportToXml(OutputStream stream) throws ParserConfigurationException, TransformerException {
/* 1931 */       Document xmlReport = XmlUtils.initNewXmlDocument();
/* 1932 */       Element root = xmlReport.createElement("report");
/* 1933 */       Element errors = xmlReport.createElement("errors");
/* 1934 */       errors.setAttribute("count", String.valueOf(this.differences.size()));
/* 1935 */       root.appendChild(errors);
/* 1936 */       for (Map.Entry<CompareTool.ObjectPath, String> entry : this.differences.entrySet()) {
/* 1937 */         Node errorNode = xmlReport.createElement("error");
/* 1938 */         Node message = xmlReport.createElement("message");
/* 1939 */         message.appendChild(xmlReport.createTextNode(entry.getValue()));
/* 1940 */         Node path = ((CompareTool.ObjectPath)entry.getKey()).toXmlNode(xmlReport);
/* 1941 */         errorNode.appendChild(message);
/* 1942 */         errorNode.appendChild(path);
/* 1943 */         errors.appendChild(errorNode);
/*      */       } 
/* 1945 */       xmlReport.appendChild(root);
/*      */       
/* 1947 */       XmlUtils.writeXmlDocToStream(xmlReport, stream);
/*      */     }
/*      */     
/*      */     protected boolean isMessageLimitReached() {
/* 1951 */       return (this.differences.size() >= this.messageLimit);
/*      */     }
/*      */     
/*      */     protected void addError(CompareTool.ObjectPath path, String message) {
/* 1955 */       if (this.differences.size() < this.messageLimit) {
/* 1956 */         this.differences.put((CompareTool.ObjectPath)path.clone(), message);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class ObjectPath
/*      */   {
/*      */     protected PdfIndirectReference baseCmpObject;
/*      */ 
/*      */ 
/*      */     
/*      */     protected PdfIndirectReference baseOutObject;
/*      */ 
/*      */ 
/*      */     
/* 1975 */     protected Stack<LocalPathItem> path = new Stack<>();
/* 1976 */     protected Stack<IndirectPathItem> indirects = new Stack<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectPath() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectPath(PdfIndirectReference baseCmpObject, PdfIndirectReference baseOutObject) {
/* 1991 */       this.baseCmpObject = baseCmpObject;
/* 1992 */       this.baseOutObject = baseOutObject;
/* 1993 */       this.indirects.push(new IndirectPathItem(baseCmpObject, baseOutObject));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectPath(PdfIndirectReference baseCmpObject, PdfIndirectReference baseOutObject, Stack<LocalPathItem> path, Stack<IndirectPathItem> indirects) {
/* 1998 */       this.baseCmpObject = baseCmpObject;
/* 1999 */       this.baseOutObject = baseOutObject;
/* 2000 */       this.path = path;
/* 2001 */       this.indirects = indirects;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectPath resetDirectPath(PdfIndirectReference baseCmpObject, PdfIndirectReference baseOutObject) {
/* 2020 */       ObjectPath newPath = new ObjectPath(baseCmpObject, baseOutObject, new Stack<>(), (Stack<IndirectPathItem>)this.indirects.clone());
/* 2021 */       newPath.indirects.push(new IndirectPathItem(baseCmpObject, baseOutObject));
/* 2022 */       return newPath;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isComparing(PdfIndirectReference cmpObject, PdfIndirectReference outObject) {
/* 2035 */       return this.indirects.contains(new IndirectPathItem(cmpObject, outObject));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void pushArrayItemToPath(int index) {
/* 2044 */       this.path.push(new ArrayPathItem(index));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void pushDictItemToPath(PdfName key) {
/* 2053 */       this.path.push(new DictPathItem(key));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void pushOffsetToPath(int offset) {
/* 2062 */       this.path.push(new OffsetPathItem(offset));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void pop() {
/* 2069 */       this.path.pop();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Stack<LocalPathItem> getLocalPath() {
/* 2079 */       return this.path;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Stack<IndirectPathItem> getIndirectPath() {
/* 2089 */       return this.indirects;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfIndirectReference getBaseCmpObject() {
/* 2096 */       return this.baseCmpObject;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PdfIndirectReference getBaseOutObject() {
/* 2103 */       return this.baseOutObject;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Node toXmlNode(Document document) {
/* 2113 */       Element element = document.createElement("path");
/* 2114 */       Element baseNode = document.createElement("base");
/* 2115 */       baseNode.setAttribute("cmp", MessageFormatUtil.format("{0} {1} obj", new Object[] { Integer.valueOf(this.baseCmpObject.getObjNumber()), Integer.valueOf(this.baseCmpObject.getGenNumber()) }));
/* 2116 */       baseNode.setAttribute("out", MessageFormatUtil.format("{0} {1} obj", new Object[] { Integer.valueOf(this.baseOutObject.getObjNumber()), Integer.valueOf(this.baseOutObject.getGenNumber()) }));
/* 2117 */       element.appendChild(baseNode);
/* 2118 */       Stack<LocalPathItem> pathClone = (Stack<LocalPathItem>)this.path.clone();
/* 2119 */       List<LocalPathItem> localPathItems = new ArrayList<>(this.path.size()); int i;
/* 2120 */       for (i = 0; i < this.path.size(); i++) {
/* 2121 */         localPathItems.add(pathClone.pop());
/*      */       }
/*      */       
/* 2124 */       for (i = localPathItems.size() - 1; i >= 0; i--) {
/* 2125 */         element.appendChild(((LocalPathItem)localPathItems.get(i)).toXmlNode(document));
/*      */       }
/* 2127 */       return element;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2135 */       StringBuilder sb = new StringBuilder();
/* 2136 */       sb.append(MessageFormatUtil.format("Base cmp object: {0} obj. Base out object: {1} obj", new Object[] { this.baseCmpObject, this.baseOutObject }));
/*      */       
/* 2138 */       Stack<LocalPathItem> pathClone = (Stack<LocalPathItem>)this.path.clone();
/* 2139 */       List<LocalPathItem> localPathItems = new ArrayList<>(this.path.size()); int i;
/* 2140 */       for (i = 0; i < this.path.size(); i++) {
/* 2141 */         localPathItems.add(pathClone.pop());
/*      */       }
/* 2143 */       for (i = localPathItems.size() - 1; i >= 0; i--) {
/* 2144 */         sb.append("\n");
/* 2145 */         sb.append(((LocalPathItem)localPathItems.get(i)).toString());
/*      */       } 
/* 2147 */       return sb.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 2152 */       int hashCode = ((this.baseCmpObject != null) ? this.baseCmpObject.hashCode() : 0) * 31 + ((this.baseOutObject != null) ? this.baseOutObject.hashCode() : 0);
/* 2153 */       for (LocalPathItem pathItem : this.path) {
/* 2154 */         hashCode *= 31;
/* 2155 */         hashCode += pathItem.hashCode();
/*      */       } 
/* 2157 */       return hashCode;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/* 2162 */       return (obj.getClass() == getClass() && this.baseCmpObject.equals(((ObjectPath)obj).baseCmpObject) && this.baseOutObject.equals(((ObjectPath)obj).baseOutObject) && this.path
/* 2163 */         .equals(((ObjectPath)obj).path));
/*      */     }
/*      */ 
/*      */     
/*      */     protected Object clone() {
/* 2168 */       return new ObjectPath(this.baseCmpObject, this.baseOutObject, (Stack<LocalPathItem>)this.path.clone(), (Stack<IndirectPathItem>)this.indirects
/* 2169 */           .clone());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public class IndirectPathItem
/*      */     {
/*      */       private PdfIndirectReference cmpObject;
/*      */ 
/*      */ 
/*      */       
/*      */       private PdfIndirectReference outObject;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public IndirectPathItem(PdfIndirectReference cmpObject, PdfIndirectReference outObject) {
/* 2187 */         this.cmpObject = cmpObject;
/* 2188 */         this.outObject = outObject;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public PdfIndirectReference getCmpObject() {
/* 2195 */         return this.cmpObject;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public PdfIndirectReference getOutObject() {
/* 2202 */         return this.outObject;
/*      */       }
/*      */ 
/*      */       
/*      */       public int hashCode() {
/* 2207 */         return this.cmpObject.hashCode() * 31 + this.outObject.hashCode();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean equals(Object obj) {
/* 2212 */         return (obj.getClass() == getClass() && this.cmpObject.equals(((IndirectPathItem)obj).cmpObject) && this.outObject
/* 2213 */           .equals(((IndirectPathItem)obj).outObject));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract class LocalPathItem
/*      */     {
/*      */       protected abstract Node toXmlNode(Document param2Document);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public class DictPathItem
/*      */       extends LocalPathItem
/*      */     {
/*      */       PdfName key;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public DictPathItem(PdfName key) {
/* 2245 */         this.key = key;
/*      */       }
/*      */ 
/*      */       
/*      */       public String toString() {
/* 2250 */         return "Dict key: " + this.key;
/*      */       }
/*      */ 
/*      */       
/*      */       public int hashCode() {
/* 2255 */         return this.key.hashCode();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean equals(Object obj) {
/* 2260 */         return (obj.getClass() == getClass() && this.key.equals(((DictPathItem)obj).key));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public PdfName getKey() {
/* 2271 */         return this.key;
/*      */       }
/*      */ 
/*      */       
/*      */       protected Node toXmlNode(Document document) {
/* 2276 */         Element element = document.createElement("dictKey");
/* 2277 */         element.appendChild(document.createTextNode(this.key.toString()));
/* 2278 */         return element;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public class ArrayPathItem
/*      */       extends LocalPathItem
/*      */     {
/*      */       int index;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ArrayPathItem(int index) {
/* 2296 */         this.index = index;
/*      */       }
/*      */ 
/*      */       
/*      */       public String toString() {
/* 2301 */         return "Array index: " + String.valueOf(this.index);
/*      */       }
/*      */ 
/*      */       
/*      */       public int hashCode() {
/* 2306 */         return this.index;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean equals(Object obj) {
/* 2311 */         return (obj.getClass() == getClass() && this.index == ((ArrayPathItem)obj).index);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getIndex() {
/* 2321 */         return this.index;
/*      */       }
/*      */ 
/*      */       
/*      */       protected Node toXmlNode(Document document) {
/* 2326 */         Element element = document.createElement("arrayIndex");
/* 2327 */         element.appendChild(document.createTextNode(String.valueOf(this.index)));
/* 2328 */         return element;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public class OffsetPathItem
/*      */       extends LocalPathItem
/*      */     {
/*      */       int offset;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public OffsetPathItem(int offset) {
/* 2345 */         this.offset = offset;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getOffset() {
/* 2355 */         return this.offset;
/*      */       }
/*      */ 
/*      */       
/*      */       public String toString() {
/* 2360 */         return "Offset: " + String.valueOf(this.offset);
/*      */       }
/*      */ 
/*      */       
/*      */       public int hashCode() {
/* 2365 */         return this.offset;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean equals(Object obj) {
/* 2370 */         return (obj.getClass() == getClass() && this.offset == ((OffsetPathItem)obj).offset);
/*      */       }
/*      */ 
/*      */       
/*      */       protected Node toXmlNode(Document document) {
/* 2375 */         Element element = document.createElement("offset");
/* 2376 */         element.appendChild(document.createTextNode(String.valueOf(this.offset)));
/* 2377 */         return element;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class TrailerPath extends ObjectPath {
/*      */     private PdfDocument outDocument;
/*      */     private PdfDocument cmpDocument;
/*      */     
/*      */     public TrailerPath(PdfDocument cmpDoc, PdfDocument outDoc) {
/* 2387 */       this.outDocument = outDoc;
/* 2388 */       this.cmpDocument = cmpDoc;
/*      */     }
/*      */ 
/*      */     
/*      */     public TrailerPath(PdfDocument cmpDoc, PdfDocument outDoc, Stack<CompareTool.ObjectPath.LocalPathItem> path) {
/* 2393 */       this.outDocument = outDoc;
/* 2394 */       this.cmpDocument = cmpDoc;
/* 2395 */       this.path = path;
/*      */     }
/*      */ 
/*      */     
/*      */     public Node toXmlNode(Document document) {
/* 2400 */       Element element = document.createElement("path");
/* 2401 */       Element baseNode = document.createElement("base");
/* 2402 */       baseNode.setAttribute("cmp", "trailer");
/* 2403 */       baseNode.setAttribute("out", "trailer");
/* 2404 */       element.appendChild(baseNode);
/* 2405 */       for (CompareTool.ObjectPath.LocalPathItem pathItem : this.path) {
/* 2406 */         element.appendChild(pathItem.toXmlNode(document));
/*      */       }
/* 2408 */       return element;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2413 */       StringBuilder sb = new StringBuilder();
/* 2414 */       sb.append("Base cmp object: trailer. Base out object: trailer");
/* 2415 */       for (CompareTool.ObjectPath.LocalPathItem pathItem : this.path) {
/* 2416 */         sb.append("\n");
/* 2417 */         sb.append(pathItem.toString());
/*      */       } 
/* 2419 */       return sb.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 2424 */       int hashCode = this.outDocument.hashCode() * 31 + this.cmpDocument.hashCode();
/* 2425 */       for (CompareTool.ObjectPath.LocalPathItem pathItem : this.path) {
/* 2426 */         hashCode *= 31;
/* 2427 */         hashCode += pathItem.hashCode();
/*      */       } 
/* 2429 */       return hashCode;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/* 2434 */       return (obj.getClass() == getClass() && this.outDocument
/* 2435 */         .equals(((TrailerPath)obj).outDocument) && this.cmpDocument
/* 2436 */         .equals(((TrailerPath)obj).cmpDocument) && this.path
/* 2437 */         .equals(((CompareTool.ObjectPath)obj).path));
/*      */     }
/*      */ 
/*      */     
/*      */     protected Object clone() {
/* 2442 */       return new TrailerPath(this.cmpDocument, this.outDocument, (Stack<CompareTool.ObjectPath.LocalPathItem>)this.path.clone());
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
/*      */   
/*      */   public class CompareToolExecutionException
/*      */     extends RuntimeException
/*      */   {
/*      */     public CompareToolExecutionException(String msg) {
/* 2458 */       super(msg);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/utils/CompareTool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */