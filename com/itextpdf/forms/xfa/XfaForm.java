/*     */ package com.itextpdf.forms.xfa;
/*     */ 
/*     */ import com.itextpdf.forms.PdfAcroForm;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import com.itextpdf.kernel.pdf.VersionConforming;
/*     */ import com.itextpdf.kernel.xmp.XmlDomWriter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XfaForm
/*     */ {
/*     */   private static final int INIT_SERIALIZER_BUFFER_SIZE = 16384;
/*     */   private Node templateNode;
/*     */   private Xml2SomDatasets datasetsSom;
/*     */   private Node datasetsNode;
/*     */   private AcroFieldsSearch acroFieldsSom;
/*     */   private boolean xfaPresent = false;
/*     */   private Document domDocument;
/*     */   public static final String XFA_DATA_SCHEMA = "http://www.xfa.org/schema/xfa-data/1.0/";
/*     */   
/*     */   public XfaForm() {
/* 105 */     this(new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?><xdp:xdp xmlns:xdp=\"http://ns.adobe.com/xdp/\"><template xmlns=\"http://www.xfa.org/schema/xfa-template/3.3/\"></template><xfa:datasets xmlns:xfa=\"http://www.xfa.org/schema/xfa-data/1.0/\"><xfa:data></xfa:data></xfa:datasets></xdp:xdp>".getBytes(StandardCharsets.UTF_8)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XfaForm(InputStream inputStream) {
/*     */     try {
/* 114 */       initXfaForm(inputStream);
/* 115 */     } catch (Exception e) {
/* 116 */       throw new PdfException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XfaForm(Document domDocument) {
/* 125 */     setDomDocument(domDocument);
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
/*     */   public XfaForm(PdfDictionary acroFormDictionary) {
/* 138 */     PdfObject xfa = acroFormDictionary.get(PdfName.XFA);
/* 139 */     if (xfa != null) {
/*     */       try {
/* 141 */         initXfaForm(xfa);
/* 142 */       } catch (Exception e) {
/* 143 */         throw new PdfException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XfaForm(PdfDocument pdfDocument) {
/* 155 */     PdfObject xfa = getXfaObject(pdfDocument);
/* 156 */     if (xfa != null) {
/*     */       try {
/* 158 */         initXfaForm(xfa);
/* 159 */       } catch (Exception e) {
/* 160 */         throw new PdfException(e);
/*     */       } 
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
/*     */   public static void setXfaForm(XfaForm form, PdfDocument pdfDocument) throws IOException {
/* 173 */     PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdfDocument, true);
/* 174 */     setXfaForm(form, acroForm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setXfaForm(XfaForm form, PdfAcroForm acroForm) throws IOException {
/* 185 */     if (form == null || acroForm == null || acroForm.getPdfDocument() == null) {
/* 186 */       throw new IllegalArgumentException("XfaForm, PdfAcroForm and PdfAcroForm's document shall not be null");
/*     */     }
/* 188 */     PdfDocument document = acroForm.getPdfDocument();
/* 189 */     if (VersionConforming.validatePdfVersionForDeprecatedFeatureLogError(document, PdfVersion.PDF_2_0, "XFA is deprecated in PDF 2.0. The XFA form will not be written to the document")) {
/*     */       return;
/*     */     }
/* 192 */     PdfObject xfa = getXfaObject(acroForm);
/* 193 */     if (xfa != null && xfa.isArray()) {
/* 194 */       PdfArray ar = (PdfArray)xfa;
/* 195 */       int t = -1;
/* 196 */       int d = -1;
/* 197 */       for (int k = 0; k < ar.size(); k += 2) {
/* 198 */         PdfString s = ar.getAsString(k);
/* 199 */         if ("template".equals(s.toString())) {
/* 200 */           t = k + 1;
/*     */         }
/* 202 */         if ("datasets".equals(s.toString())) {
/* 203 */           d = k + 1;
/*     */         }
/*     */       } 
/* 206 */       if (t > -1 && d > -1) {
/*     */ 
/*     */         
/* 209 */         PdfStream tStream = new PdfStream(serializeDocument(form.templateNode));
/* 210 */         tStream.setCompressionLevel(document.getWriter().getCompressionLevel());
/* 211 */         ar.set(t, (PdfObject)tStream);
/* 212 */         PdfStream dStream = new PdfStream(serializeDocument(form.datasetsNode));
/* 213 */         dStream.setCompressionLevel(document.getWriter().getCompressionLevel());
/* 214 */         ar.set(d, (PdfObject)dStream);
/* 215 */         ar.setModified();
/* 216 */         ar.flush();
/* 217 */         acroForm.put(PdfName.XFA, (PdfObject)new PdfArray(ar));
/* 218 */         acroForm.setModified();
/* 219 */         if (!((PdfDictionary)acroForm.getPdfObject()).isIndirect()) {
/* 220 */           document.getCatalog().setModified();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 226 */     PdfStream stream = new PdfStream(serializeDocument(form.domDocument));
/* 227 */     stream.setCompressionLevel(document.getWriter().getCompressionLevel());
/* 228 */     stream.flush();
/* 229 */     acroForm.put(PdfName.XFA, (PdfObject)stream);
/* 230 */     acroForm.setModified();
/* 231 */     if (!((PdfDictionary)acroForm.getPdfObject()).isIndirect()) {
/* 232 */       document.getCatalog().setModified();
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
/*     */   public static Map<String, Node> extractXFANodes(Document domDocument) {
/* 245 */     Map<String, Node> xfaNodes = new HashMap<>();
/* 246 */     Node n = domDocument.getFirstChild();
/* 247 */     while (n.getChildNodes().getLength() == 0) {
/* 248 */       n = n.getNextSibling();
/*     */     }
/* 250 */     n = n.getFirstChild();
/* 251 */     while (n != null) {
/* 252 */       if (n.getNodeType() == 1) {
/* 253 */         String s = n.getLocalName();
/* 254 */         xfaNodes.put(s, n);
/*     */       } 
/* 256 */       n = n.getNextSibling();
/*     */     } 
/*     */     
/* 259 */     return xfaNodes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(PdfDocument document) throws IOException {
/* 269 */     setXfaForm(this, document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(PdfAcroForm acroForm) throws IOException {
/* 279 */     setXfaForm(this, acroForm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXfaFieldValue(String name, String value) {
/* 289 */     if (isXfaPresent()) {
/* 290 */       name = findFieldName(name);
/* 291 */       if (name != null) {
/* 292 */         String shortName = Xml2Som.getShortName(name);
/* 293 */         Node xn = findDatasetsNode(shortName);
/* 294 */         if (xn == null) {
/* 295 */           xn = this.datasetsSom.insertNode(getDatasetsNode(), shortName);
/*     */         }
/* 297 */         setNodeText(xn, value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXfaFieldValue(String name) {
/* 309 */     if (isXfaPresent()) {
/* 310 */       name = findFieldName(name);
/* 311 */       if (name != null) {
/*     */         
/* 313 */         name = Xml2Som.getShortName(name);
/* 314 */         return getNodeText(findDatasetsNode(name));
/*     */       } 
/*     */     } 
/* 317 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isXfaPresent() {
/* 326 */     return this.xfaPresent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findFieldName(String name) {
/* 336 */     if (this.acroFieldsSom == null && this.xfaPresent && this.datasetsSom != null) {
/* 337 */       this.acroFieldsSom = new AcroFieldsSearch(this.datasetsSom.getName2Node().keySet());
/*     */     }
/*     */     
/* 340 */     if (this.acroFieldsSom != null && this.xfaPresent) {
/* 341 */       return this.acroFieldsSom.getAcroShort2LongName().containsKey(name) ? this.acroFieldsSom.getAcroShort2LongName().get(name) : this.acroFieldsSom.inverseSearchGlobal(Xml2Som.splitParts(name));
/*     */     }
/*     */     
/* 344 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findDatasetsName(String name) {
/* 355 */     return this.datasetsSom.getName2Node().containsKey(name) ? name : this.datasetsSom.inverseSearchGlobal(Xml2Som.splitParts(name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node findDatasetsNode(String name) {
/* 366 */     if (name == null)
/* 367 */       return null; 
/* 368 */     name = findDatasetsName(name);
/* 369 */     if (name == null)
/* 370 */       return null; 
/* 371 */     return this.datasetsSom.getName2Node().get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getNodeText(Node n) {
/* 381 */     return (n == null) ? "" : getNodeText(n, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNodeText(Node n, String text) {
/* 392 */     if (n == null)
/*     */       return; 
/* 394 */     Node nc = null;
/* 395 */     while ((nc = n.getFirstChild()) != null) {
/* 396 */       n.removeChild(nc);
/*     */     }
/* 398 */     if (n.getAttributes().getNamedItemNS("http://www.xfa.org/schema/xfa-data/1.0/", "dataNode") != null)
/* 399 */       n.getAttributes().removeNamedItemNS("http://www.xfa.org/schema/xfa-data/1.0/", "dataNode"); 
/* 400 */     n.appendChild(this.domDocument.createTextNode(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getDomDocument() {
/* 409 */     return this.domDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDomDocument(Document domDocument) {
/* 418 */     this.domDocument = domDocument;
/* 419 */     extractNodes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getDatasetsNode() {
/* 428 */     return this.datasetsNode;
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
/*     */   public void fillXfaForm(File file) throws IOException {
/* 440 */     fillXfaForm(file, false);
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
/*     */   public void fillXfaForm(File file, boolean readOnly) throws IOException {
/* 452 */     fillXfaForm(new FileInputStream(file), readOnly);
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
/*     */   public void fillXfaForm(InputStream is) throws IOException {
/* 464 */     fillXfaForm(is, false);
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
/*     */   public void fillXfaForm(InputStream is, boolean readOnly) throws IOException {
/* 476 */     fillXfaForm(new InputSource(is), readOnly);
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
/*     */   public void fillXfaForm(InputSource is) throws IOException {
/* 488 */     fillXfaForm(is, false);
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
/*     */   public void fillXfaForm(InputSource is, boolean readOnly) throws IOException {
/* 500 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*     */     
/*     */     try {
/* 503 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 504 */       db.setEntityResolver(new SafeEmptyEntityResolver());
/* 505 */       Document newdoc = db.parse(is);
/* 506 */       fillXfaForm(newdoc.getDocumentElement(), readOnly);
/* 507 */     } catch (ParserConfigurationException e) {
/* 508 */       throw new PdfException(e);
/* 509 */     } catch (SAXException e) {
/* 510 */       throw new PdfException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillXfaForm(Node node) {
/* 520 */     fillXfaForm(node, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillXfaForm(Node node, boolean readOnly) {
/* 530 */     if (readOnly) {
/* 531 */       NodeList nodeList = this.domDocument.getElementsByTagName("field");
/* 532 */       for (int i = 0; i < nodeList.getLength(); i++) {
/* 533 */         ((Element)nodeList.item(i)).setAttribute("access", "readOnly");
/*     */       }
/*     */     } 
/* 536 */     NodeList allChilds = this.datasetsNode.getChildNodes();
/* 537 */     int len = allChilds.getLength();
/* 538 */     Node data = null;
/* 539 */     for (int k = 0; k < len; k++) {
/* 540 */       Node n = allChilds.item(k);
/* 541 */       if (n.getNodeType() == 1 && n.getLocalName().equals("data") && "http://www.xfa.org/schema/xfa-data/1.0/".equals(n.getNamespaceURI())) {
/* 542 */         data = n;
/*     */         break;
/*     */       } 
/*     */     } 
/* 546 */     if (data == null) {
/* 547 */       data = this.datasetsNode.getOwnerDocument().createElementNS("http://www.xfa.org/schema/xfa-data/1.0/", "xfa:data");
/* 548 */       this.datasetsNode.appendChild(data);
/*     */     } 
/* 550 */     NodeList list = data.getChildNodes();
/* 551 */     if (list.getLength() == 0) {
/* 552 */       data.appendChild(this.domDocument.importNode(node, true));
/*     */     }
/*     */     else {
/*     */       
/* 556 */       Node firstNode = getFirstElementNode(data);
/* 557 */       if (firstNode != null)
/* 558 */         data.replaceChild(this.domDocument.importNode(node, true), firstNode); 
/*     */     } 
/* 560 */     extractNodes();
/*     */   }
/*     */   
/*     */   private static String getNodeText(Node n, String name) {
/* 564 */     Node n2 = n.getFirstChild();
/* 565 */     while (n2 != null) {
/* 566 */       if (n2.getNodeType() == 1) {
/* 567 */         name = getNodeText(n2, name);
/* 568 */       } else if (n2.getNodeType() == 3) {
/* 569 */         name = name + n2.getNodeValue();
/*     */       } 
/* 571 */       n2 = n2.getNextSibling();
/*     */     } 
/* 573 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfObject getXfaObject(PdfDocument pdfDocument) {
/* 584 */     PdfDictionary af = ((PdfDictionary)pdfDocument.getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm);
/* 585 */     return (af == null) ? null : af.get(PdfName.XFA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PdfObject getXfaObject(PdfAcroForm acroForm) {
/* 596 */     return (acroForm == null || acroForm.getPdfObject() == null) ? null : ((PdfDictionary)acroForm.getPdfObject()).get(PdfName.XFA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] serializeDocument(Node n) throws IOException {
/* 607 */     XmlDomWriter xw = new XmlDomWriter(false);
/* 608 */     ByteArrayOutputStream fout = new ByteArrayOutputStream(16384);
/* 609 */     xw.setOutput(fout, null);
/* 610 */     xw.write(n);
/* 611 */     fout.close();
/* 612 */     return fout.toByteArray();
/*     */   }
/*     */   
/*     */   private void initXfaForm(PdfObject xfa) throws IOException, ParserConfigurationException, SAXException {
/* 616 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 617 */     if (xfa.isArray()) {
/* 618 */       PdfArray ar = (PdfArray)xfa;
/* 619 */       for (int k = 1; k < ar.size(); k += 2) {
/* 620 */         PdfObject ob = ar.get(k);
/* 621 */         if (ob instanceof PdfStream) {
/* 622 */           byte[] b = ((PdfStream)ob).getBytes();
/* 623 */           bout.write(b);
/*     */         } 
/*     */       } 
/* 626 */     } else if (xfa instanceof PdfStream) {
/* 627 */       byte[] b = ((PdfStream)xfa).getBytes();
/* 628 */       bout.write(b);
/*     */     } 
/* 630 */     bout.close();
/* 631 */     initXfaForm(new ByteArrayInputStream(bout.toByteArray()));
/*     */   }
/*     */   
/*     */   private void initXfaForm(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
/* 635 */     DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
/* 636 */     fact.setNamespaceAware(true);
/* 637 */     DocumentBuilder db = fact.newDocumentBuilder();
/* 638 */     db.setEntityResolver(new SafeEmptyEntityResolver());
/* 639 */     setDomDocument(db.parse(inputStream));
/* 640 */     this.xfaPresent = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractNodes() {
/* 647 */     Map<String, Node> xfaNodes = extractXFANodes(this.domDocument);
/*     */     
/* 649 */     if (xfaNodes.containsKey("template")) {
/* 650 */       this.templateNode = xfaNodes.get("template");
/*     */     }
/* 652 */     if (xfaNodes.containsKey("datasets")) {
/* 653 */       this.datasetsNode = xfaNodes.get("datasets");
/* 654 */       Node dataNode = findDataNode(this.datasetsNode);
/* 655 */       this.datasetsSom = new Xml2SomDatasets((dataNode != null) ? dataNode : this.datasetsNode.getFirstChild());
/*     */     } 
/* 657 */     if (this.datasetsNode == null) {
/* 658 */       createDatasetsNode(this.domDocument.getFirstChild());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createDatasetsNode(Node n) {
/* 667 */     while (n != null && n.getChildNodes().getLength() == 0) {
/* 668 */       n = n.getNextSibling();
/*     */     }
/* 670 */     if (n != null) {
/* 671 */       Element e = n.getOwnerDocument().createElement("xfa:datasets");
/* 672 */       e.setAttribute("xmlns:xfa", "http://www.xfa.org/schema/xfa-data/1.0/");
/* 673 */       this.datasetsNode = e;
/* 674 */       n.appendChild(this.datasetsNode);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Node getFirstElementNode(Node src) {
/* 679 */     Node result = null;
/* 680 */     NodeList list = src.getChildNodes();
/* 681 */     for (int i = 0; i < list.getLength(); i++) {
/* 682 */       if (list.item(i).getNodeType() == 1) {
/* 683 */         result = list.item(i);
/*     */         break;
/*     */       } 
/*     */     } 
/* 687 */     return result;
/*     */   }
/*     */   
/*     */   private Node findDataNode(Node datasetsNode) {
/* 691 */     NodeList childNodes = datasetsNode.getChildNodes();
/* 692 */     for (int i = 0; i < childNodes.getLength(); i++) {
/* 693 */       if (childNodes.item(i).getNodeName().equals("xfa:data")) {
/* 694 */         return childNodes.item(i);
/*     */       }
/*     */     } 
/* 697 */     return null;
/*     */   }
/*     */   
/*     */   private static class SafeEmptyEntityResolver
/*     */     implements EntityResolver {
/*     */     public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 703 */       return new InputSource(new StringReader(""));
/*     */     }
/*     */     
/*     */     private SafeEmptyEntityResolver() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfa/XfaForm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */