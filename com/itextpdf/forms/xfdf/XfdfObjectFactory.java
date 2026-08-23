/*     */ package com.itextpdf.forms.xfdf;
/*     */ 
/*     */ import com.itextpdf.forms.PdfAcroForm;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfPage;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfCircleAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfFreeTextAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfLineAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfMarkupAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfPolyGeomAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfPopupAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfSquareAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfStampAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
/*     */ import com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class XfdfObjectFactory
/*     */ {
/*  89 */   private static Logger logger = LoggerFactory.getLogger(XfdfObjectFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XfdfObject createXfdfObject(PdfDocument document, String filename) {
/*  99 */     PdfAcroForm form = PdfAcroForm.getAcroForm(document, false);
/*     */     
/* 101 */     XfdfObject resultXfdf = new XfdfObject();
/* 102 */     FieldsObject xfdfFields = new FieldsObject();
/*     */     
/* 104 */     if (form != null && form.getFormFields() != null && !form.getFormFields().isEmpty()) {
/* 105 */       for (String fieldName : form.getFormFields().keySet()) {
/* 106 */         String delims = ".";
/* 107 */         StringTokenizer st = new StringTokenizer(fieldName, delims);
/* 108 */         List<String> nameParts = new ArrayList<>();
/* 109 */         while (st.hasMoreTokens()) {
/* 110 */           nameParts.add(st.nextToken());
/*     */         }
/* 112 */         String name = nameParts.get(nameParts.size() - 1);
/* 113 */         String value = form.getField(fieldName).getValueAsString();
/* 114 */         FieldObject childField = new FieldObject(name, value, false);
/* 115 */         if (nameParts.size() > 1) {
/* 116 */           FieldObject parentField = new FieldObject();
/* 117 */           parentField.setName(nameParts.get(nameParts.size() - 2));
/* 118 */           childField.setParent(parentField);
/*     */         } 
/* 120 */         xfdfFields.addField(childField);
/*     */       } 
/*     */     }
/* 123 */     resultXfdf.setFields(xfdfFields);
/*     */     
/* 125 */     String original = XfdfObjectUtils.convertIdToHexString(document.getOriginalDocumentId().getValue());
/* 126 */     String modified = XfdfObjectUtils.convertIdToHexString(document.getModifiedDocumentId().getValue());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     IdsObject ids = (new IdsObject()).setOriginal(original).setModified(modified);
/* 132 */     resultXfdf.setIds(ids);
/*     */     
/* 134 */     FObject f = new FObject(filename);
/* 135 */     resultXfdf.setF(f);
/*     */     
/* 137 */     addAnnotations(document, resultXfdf);
/*     */     
/* 139 */     return resultXfdf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XfdfObject createXfdfObject(InputStream xfdfInputStream) throws ParserConfigurationException, IOException, SAXException {
/* 149 */     XfdfObject xfdfObject = new XfdfObject();
/*     */     
/* 151 */     Document document = XfdfFileUtils.createXfdfDocumentFromStream(xfdfInputStream);
/*     */     
/* 153 */     Element root = document.getDocumentElement();
/* 154 */     List<AttributeObject> xfdfRootAttributes = readXfdfRootAttributes(root);
/* 155 */     xfdfObject.setAttributes(xfdfRootAttributes);
/*     */     
/* 157 */     NodeList nodeList = root.getChildNodes();
/*     */     
/* 159 */     visitChildNodes(nodeList, xfdfObject);
/*     */     
/* 161 */     return xfdfObject;
/*     */   }
/*     */   
/*     */   private void visitFNode(Node node, XfdfObject xfdfObject) {
/* 165 */     if (node.getAttributes() != null) {
/* 166 */       Node href = node.getAttributes().getNamedItem("href");
/* 167 */       if (href != null) {
/* 168 */         xfdfObject.setF(new FObject(href.getNodeValue()));
/*     */       } else {
/* 170 */         logger.info("Empty f element, no href attribute found.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void visitIdsNode(Node node, XfdfObject xfdfObject) {
/* 176 */     IdsObject idsObject = new IdsObject();
/* 177 */     if (node.getAttributes() != null) {
/* 178 */       Node original = node.getAttributes().getNamedItem("original");
/* 179 */       if (original != null) {
/* 180 */         idsObject.setOriginal(original.getNodeValue());
/*     */       }
/* 182 */       Node modified = node.getAttributes().getNamedItem("modified");
/* 183 */       if (modified != null) {
/* 184 */         idsObject.setModified(modified.getNodeValue());
/*     */       }
/* 186 */       xfdfObject.setIds(idsObject);
/*     */     } else {
/* 188 */       logger.info("Empty ids element, original and/or modified id attributes not found.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void visitElementNode(Node node, XfdfObject xfdfObject) {
/* 193 */     if ("fields".equalsIgnoreCase(node.getNodeName())) {
/* 194 */       FieldsObject fieldsObject = new FieldsObject();
/* 195 */       readFieldList(node, fieldsObject);
/* 196 */       xfdfObject.setFields(fieldsObject);
/*     */     } 
/* 198 */     if ("f".equalsIgnoreCase(node.getNodeName())) {
/* 199 */       visitFNode(node, xfdfObject);
/*     */     }
/* 201 */     if ("ids".equalsIgnoreCase(node.getNodeName())) {
/* 202 */       visitIdsNode(node, xfdfObject);
/*     */     }
/* 204 */     if ("annots".equalsIgnoreCase(node.getNodeName())) {
/* 205 */       AnnotsObject annotsObject = new AnnotsObject();
/* 206 */       readAnnotsList(node, annotsObject);
/* 207 */       xfdfObject.setAnnots(annotsObject);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void visitChildNodes(NodeList nList, XfdfObject xfdfObject) {
/* 212 */     for (int temp = 0; temp < nList.getLength(); temp++) {
/* 213 */       Node node = nList.item(temp);
/* 214 */       if (node.getNodeType() == 1) {
/* 215 */         visitElementNode(node, xfdfObject);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isAnnotSupported(String nodeName) {
/* 221 */     return ("text".equalsIgnoreCase(nodeName) || "highlight"
/* 222 */       .equalsIgnoreCase(nodeName) || "underline"
/* 223 */       .equalsIgnoreCase(nodeName) || "strikeout"
/* 224 */       .equalsIgnoreCase(nodeName) || "squiggly"
/* 225 */       .equalsIgnoreCase(nodeName) || "circle"
/* 226 */       .equalsIgnoreCase(nodeName) || "square"
/* 227 */       .equalsIgnoreCase(nodeName) || "polyline"
/* 228 */       .equalsIgnoreCase(nodeName) || "polygon"
/* 229 */       .equalsIgnoreCase(nodeName) || "line"
/* 230 */       .equalsIgnoreCase(nodeName));
/*     */   }
/*     */   
/*     */   private void readAnnotsList(Node node, AnnotsObject annotsObject) {
/* 234 */     NodeList annotsNodeList = node.getChildNodes();
/*     */     
/* 236 */     for (int temp = 0; temp < annotsNodeList.getLength(); temp++) {
/* 237 */       Node currentNode = annotsNodeList.item(temp);
/* 238 */       if (currentNode.getNodeType() == 1 && 
/* 239 */         isAnnotationSubtype(currentNode.getNodeName()) && 
/* 240 */         isAnnotSupported(currentNode.getNodeName())) {
/* 241 */         visitAnnotationNode(currentNode, annotsObject);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void visitAnnotationNode(Node currentNode, AnnotsObject annotsObject) {
/* 247 */     AnnotObject annotObject = new AnnotObject();
/* 248 */     annotObject.setName(currentNode.getNodeName());
/* 249 */     if (currentNode.getAttributes() != null) {
/* 250 */       NamedNodeMap attributes = currentNode.getAttributes();
/* 251 */       for (int i = 0; i < attributes.getLength(); i++) {
/* 252 */         addAnnotObjectAttribute(annotObject, attributes.item(i));
/*     */       }
/* 254 */       visitAnnotationInnerNodes(annotObject, currentNode);
/* 255 */       annotsObject.addAnnot(annotObject);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void visitAnnotationInnerNodes(AnnotObject annotObject, Node annotNode) {
/* 260 */     NodeList children = annotNode.getChildNodes();
/*     */     
/* 262 */     for (int temp = 0; temp < children.getLength(); temp++) {
/* 263 */       Node node = children.item(temp);
/* 264 */       if (node.getNodeType() == 1) {
/* 265 */         if ("contents".equalsIgnoreCase(node.getNodeName())) {
/* 266 */           visitContentsSubelement(node, annotObject);
/*     */         }
/* 268 */         if ("contents-richtext".equalsIgnoreCase(node.getNodeName())) {
/* 269 */           visitContentsRichTextSubelement(node, annotObject);
/*     */         }
/* 271 */         if ("popup".equalsIgnoreCase(node.getNodeName())) {
/* 272 */           visitPopupSubelement(node, annotObject);
/*     */         }
/* 274 */         if ("vertices".equalsIgnoreCase(node.getNodeName())) {
/* 275 */           visitVerticesSubelement(node, annotObject);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void visitPopupSubelement(Node popupNode, AnnotObject annotObject) {
/* 284 */     AnnotObject popupAnnotObject = new AnnotObject();
/* 285 */     NamedNodeMap attributes = popupNode.getAttributes();
/* 286 */     for (int i = 0; i < attributes.getLength(); i++) {
/* 287 */       addAnnotObjectAttribute(popupAnnotObject, attributes.item(i));
/*     */     }
/* 289 */     annotObject.setPopup(popupAnnotObject);
/*     */   }
/*     */ 
/*     */   
/*     */   private void visitContentsSubelement(Node parentNode, AnnotObject annotObject) {
/* 294 */     NodeList children = parentNode.getChildNodes();
/* 295 */     for (int temp = 0; temp < children.getLength(); temp++) {
/* 296 */       Node node = children.item(temp);
/* 297 */       if (node.getNodeType() == 3) {
/* 298 */         annotObject.setContents(new PdfString(node.getNodeValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void visitContentsRichTextSubelement(Node parentNode, AnnotObject annotObject) {
/* 305 */     NodeList children = parentNode.getChildNodes();
/* 306 */     for (int temp = 0; temp < children.getLength(); temp++) {
/* 307 */       Node node = children.item(temp);
/* 308 */       if (node.getNodeType() == 3) {
/* 309 */         annotObject.setContentsRichText(new PdfString(node.getNodeValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void visitVerticesSubelement(Node parentNode, AnnotObject annotObject) {
/* 316 */     NodeList children = parentNode.getChildNodes();
/* 317 */     for (int temp = 0; temp < children.getLength(); temp++) {
/* 318 */       Node node = children.item(temp);
/* 319 */       if (node.getNodeType() == 3) {
/* 320 */         annotObject.setVertices(node.getNodeValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addAnnotObjectAttribute(AnnotObject annotObject, Node attributeNode) {
/* 326 */     if (attributeNode != null) {
/* 327 */       String attributeName = attributeNode.getNodeName();
/* 328 */       switch (attributeName) {
/*     */         
/*     */         case "page":
/* 331 */           annotObject.addFdfAttributes(Integer.parseInt(attributeNode.getNodeValue()));
/*     */           return;
/*     */ 
/*     */         
/*     */         case "color":
/*     */         case "date":
/*     */         case "flags":
/*     */         case "name":
/*     */         case "rect":
/*     */         case "title":
/*     */         case "creationdate":
/*     */         case "opacity":
/*     */         case "subject":
/*     */         case "icon":
/*     */         case "state":
/*     */         case "statemodel":
/*     */         case "inreplyto":
/*     */         case "replyType":
/*     */         case "open":
/*     */         case "coords":
/*     */         case "fringe":
/* 352 */           annotObject.addAttribute(new AttributeObject(attributeName, attributeNode.getNodeValue())); return;
/*     */       } 
/* 354 */       logger.warn("Xfdf unsupported attribute type");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAnnotationSubtype(String tag) {
/* 361 */     return ("text".equalsIgnoreCase(tag) || "highlight"
/* 362 */       .equalsIgnoreCase(tag) || "underline"
/* 363 */       .equalsIgnoreCase(tag) || "strikeout"
/* 364 */       .equalsIgnoreCase(tag) || "squiggly"
/* 365 */       .equalsIgnoreCase(tag) || "line"
/* 366 */       .equalsIgnoreCase(tag) || "circle"
/* 367 */       .equalsIgnoreCase(tag) || "square"
/* 368 */       .equalsIgnoreCase(tag) || "caret"
/* 369 */       .equalsIgnoreCase(tag) || "polygon"
/* 370 */       .equalsIgnoreCase(tag) || "polyline"
/* 371 */       .equalsIgnoreCase(tag) || "stamp"
/* 372 */       .equalsIgnoreCase(tag) || "ink"
/* 373 */       .equalsIgnoreCase(tag) || "freetext"
/* 374 */       .equalsIgnoreCase(tag) || "fileattachment"
/* 375 */       .equalsIgnoreCase(tag) || "sound"
/* 376 */       .equalsIgnoreCase(tag) || "link"
/* 377 */       .equalsIgnoreCase(tag) || "redact"
/* 378 */       .equalsIgnoreCase(tag) || "projection"
/* 379 */       .equalsIgnoreCase(tag));
/*     */   }
/*     */ 
/*     */   
/*     */   private void readFieldList(Node node, FieldsObject fieldsObject) {
/* 384 */     NodeList fieldNodeList = node.getChildNodes();
/*     */     
/* 386 */     for (int temp = 0; temp < fieldNodeList.getLength(); temp++) {
/* 387 */       Node currentNode = fieldNodeList.item(temp);
/* 388 */       if (currentNode.getNodeType() == 1 && "field".equalsIgnoreCase(currentNode.getNodeName())) {
/* 389 */         FieldObject fieldObject = new FieldObject();
/* 390 */         visitInnerFields(fieldObject, currentNode, fieldsObject);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void visitFieldElementNode(Node node, FieldObject parentField, FieldsObject fieldsObject) {
/* 396 */     if ("value".equalsIgnoreCase(node.getNodeName())) {
/* 397 */       Node valueTextNode = node.getFirstChild();
/* 398 */       if (valueTextNode != null) {
/* 399 */         parentField.setValue(valueTextNode.getTextContent());
/*     */       } else {
/* 401 */         logger.info("Field has no value.");
/*     */       } 
/*     */       return;
/*     */     } 
/* 405 */     if ("field".equalsIgnoreCase(node.getNodeName())) {
/* 406 */       FieldObject childField = new FieldObject();
/* 407 */       childField.setParent(parentField);
/* 408 */       childField.setName(parentField.getName() + "." + node.getAttributes().item(0).getNodeValue());
/* 409 */       if (node.getChildNodes() != null) {
/* 410 */         visitInnerFields(childField, node, fieldsObject);
/*     */       }
/* 412 */       fieldsObject.addField(childField);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void visitInnerFields(FieldObject parentField, Node parentNode, FieldsObject fieldsObject) {
/* 417 */     if (parentNode.getAttributes().getLength() != 0) {
/* 418 */       if (parentField.getName() == null) {
/* 419 */         parentField.setName(parentNode.getAttributes().item(0).getNodeValue());
/*     */       }
/*     */     } else {
/* 422 */       logger.info("Field has no name attribute.");
/*     */     } 
/*     */     
/* 425 */     NodeList children = parentNode.getChildNodes();
/*     */     
/* 427 */     for (int temp = 0; temp < children.getLength(); temp++) {
/* 428 */       Node node = children.item(temp);
/* 429 */       if (node.getNodeType() == 1) {
/* 430 */         visitFieldElementNode(node, parentField, fieldsObject);
/*     */       }
/*     */     } 
/* 433 */     fieldsObject.addField(parentField);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<AttributeObject> readXfdfRootAttributes(Element root) {
/* 438 */     NamedNodeMap attributes = root.getAttributes();
/* 439 */     int length = attributes.getLength();
/* 440 */     List<AttributeObject> attributeObjects = new ArrayList<>();
/* 441 */     for (int i = 0; i < length; i++) {
/* 442 */       Node attributeNode = attributes.item(i);
/* 443 */       attributeObjects.add(new AttributeObject(attributeNode.getNodeName(), attributeNode.getNodeValue()));
/*     */     } 
/* 445 */     return attributeObjects;
/*     */   }
/*     */   
/*     */   private static void addPopup(PdfAnnotation pdfAnnot, AnnotsObject annots, int pageNumber) {
/* 449 */     if (((PdfPopupAnnotation)pdfAnnot).getParentObject() != null) {
/* 450 */       PdfAnnotation parentAnnotation = ((PdfPopupAnnotation)pdfAnnot).getParent();
/* 451 */       PdfIndirectReference parentRef = ((PdfDictionary)parentAnnotation.getPdfObject()).getIndirectReference();
/* 452 */       boolean hasParentAnnot = false;
/* 453 */       for (AnnotObject annot : annots.getAnnotsList()) {
/* 454 */         if (parentRef.equals(annot.getRef())) {
/* 455 */           hasParentAnnot = true;
/* 456 */           annot.setHasPopup(true);
/* 457 */           annot.setPopup(createXfdfAnnotation(pdfAnnot, pageNumber));
/*     */         } 
/*     */       } 
/* 460 */       if (!hasParentAnnot) {
/* 461 */         AnnotObject parentAnnot = new AnnotObject();
/* 462 */         parentAnnot.setRef(parentRef);
/* 463 */         parentAnnot.addFdfAttributes(pageNumber);
/* 464 */         parentAnnot.setHasPopup(true);
/* 465 */         parentAnnot.setPopup(createXfdfAnnotation(pdfAnnot, pageNumber));
/* 466 */         annots.addAnnot(parentAnnot);
/*     */       } 
/*     */     } else {
/* 469 */       annots.addAnnot(createXfdfAnnotation(pdfAnnot, pageNumber));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addAnnotation(PdfAnnotation pdfAnnot, AnnotsObject annots, int pageNumber) {
/* 474 */     boolean hasCorrecpondingAnnotObject = false;
/* 475 */     for (AnnotObject annot : annots.getAnnotsList()) {
/* 476 */       if (((PdfDictionary)pdfAnnot.getPdfObject()).getIndirectReference().equals(annot.getRef())) {
/* 477 */         hasCorrecpondingAnnotObject = true;
/* 478 */         updateXfdfAnnotation(annot, pdfAnnot, pageNumber);
/*     */       } 
/*     */     } 
/* 481 */     if (!hasCorrecpondingAnnotObject) {
/* 482 */       annots.addAnnot(createXfdfAnnotation(pdfAnnot, pageNumber));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addAnnotations(PdfDocument pdfDoc, XfdfObject resultXfdf) {
/* 487 */     AnnotsObject annots = new AnnotsObject();
/* 488 */     int pageNumber = pdfDoc.getNumberOfPages();
/* 489 */     for (int i = 1; i <= pageNumber; i++) {
/* 490 */       PdfPage page = pdfDoc.getPage(i);
/* 491 */       List<PdfAnnotation> pdfAnnots = page.getAnnotations();
/* 492 */       for (PdfAnnotation pdfAnnot : pdfAnnots) {
/* 493 */         if (pdfAnnot.getSubtype() == PdfName.Popup) {
/* 494 */           addPopup(pdfAnnot, annots, i); continue;
/*     */         } 
/* 496 */         addAnnotation(pdfAnnot, annots, i);
/*     */       } 
/*     */     } 
/*     */     
/* 500 */     resultXfdf.setAnnots(annots);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateXfdfAnnotation(AnnotObject annotObject, PdfAnnotation pdfAnnotation, int pageNumber) {}
/*     */ 
/*     */   
/*     */   private static void addCommonAnnotationAttributes(AnnotObject annot, PdfAnnotation pdfAnnotation) {
/* 508 */     annot.setName(pdfAnnotation.getSubtype().getValue().toLowerCase());
/*     */     
/* 510 */     if (pdfAnnotation.getColorObject() != null) {
/* 511 */       annot.addAttribute(new AttributeObject("color", XfdfObjectUtils.convertColorToString(pdfAnnotation.getColorObject().toFloatArray())));
/*     */     }
/* 513 */     annot.addAttribute("date", (PdfObject)pdfAnnotation.getDate());
/* 514 */     String flagsString = XfdfObjectUtils.convertFlagsToString(pdfAnnotation);
/* 515 */     if (flagsString != null) {
/* 516 */       annot.addAttribute(new AttributeObject("flags", flagsString));
/*     */     }
/*     */     
/* 519 */     annot.addAttribute("name", (PdfObject)pdfAnnotation.getName());
/*     */     
/* 521 */     annot.addAttribute("rect", pdfAnnotation.getRectangle().toRectangle());
/* 522 */     annot.addAttribute("title", (PdfObject)pdfAnnotation.getTitle());
/*     */   }
/*     */   
/*     */   private static void addMarkupAnnotationAttributes(AnnotObject annot, PdfMarkupAnnotation pdfMarkupAnnotation) {
/* 526 */     annot.addAttribute("creationdate", (PdfObject)pdfMarkupAnnotation.getCreationDate());
/* 527 */     annot.addAttribute("opacity", (PdfObject)pdfMarkupAnnotation.getOpacity());
/* 528 */     annot.addAttribute("subject", (PdfObject)pdfMarkupAnnotation.getSubject());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addBorderStyleAttributes(AnnotObject annotObject, PdfNumber width, PdfString dashes, PdfString style) {
/* 533 */     annotObject.addAttribute("width", (PdfObject)width);
/* 534 */     annotObject.addAttribute("dashes", (PdfObject)dashes);
/* 535 */     annotObject.addAttribute("style", (PdfObject)style);
/*     */   }
/*     */   
/*     */   private static void createTextMarkupAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
/* 539 */     PdfTextMarkupAnnotation pdfTextMarkupAnnotation = (PdfTextMarkupAnnotation)pdfAnnotation;
/*     */     
/* 541 */     annot.addAttribute(new AttributeObject("coords", 
/* 542 */           XfdfObjectUtils.convertQuadPointsToCoordsString(pdfTextMarkupAnnotation.getQuadPoints().toFloatArray())));
/*     */ 
/*     */     
/* 545 */     if (pdfTextMarkupAnnotation.getContents() != null) {
/* 546 */       annot.setContents(pdfTextMarkupAnnotation.getContents());
/*     */     }
/* 548 */     if (pdfTextMarkupAnnotation.getPopup() != null) {
/* 549 */       annot.setPopup(convertPdfPopupToAnnotObject(pdfTextMarkupAnnotation.getPopup(), pageNumber));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void createTextAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
/* 554 */     PdfTextAnnotation pdfTextAnnotation = (PdfTextAnnotation)pdfAnnotation;
/*     */     
/* 556 */     annot.addAttribute("icon", (PdfObject)pdfTextAnnotation.getIconName());
/* 557 */     annot.addAttribute("state", (PdfObject)pdfTextAnnotation.getState());
/* 558 */     annot.addAttribute("statemodel", (PdfObject)pdfTextAnnotation.getStateModel());
/*     */     
/* 560 */     if (pdfTextAnnotation.getReplyType() != null) {
/*     */       
/* 562 */       annot.addAttribute(new AttributeObject("inreplyto", pdfTextAnnotation.getInReplyTo().getName().getValue()));
/* 563 */       annot.addAttribute(new AttributeObject("replyType", pdfTextAnnotation.getReplyType().getValue()));
/*     */     } 
/*     */     
/* 566 */     if (pdfTextAnnotation.getContents() != null) {
/* 567 */       annot.setContents(pdfTextAnnotation.getContents());
/*     */     }
/* 569 */     if (pdfTextAnnotation.getPopup() != null) {
/* 570 */       annot.setPopup(convertPdfPopupToAnnotObject(pdfTextAnnotation.getPopup(), pageNumber));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void createCircleAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
/* 575 */     PdfCircleAnnotation pdfCircleAnnotation = (PdfCircleAnnotation)pdfAnnotation;
/*     */     
/* 577 */     PdfDictionary bs = pdfCircleAnnotation.getBorderStyle();
/* 578 */     if (bs != null) {
/* 579 */       addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.W), bs
/* 580 */           .getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
/*     */     }
/*     */     
/* 583 */     if (pdfCircleAnnotation.getBorderEffect() != null)
/*     */     {
/*     */       
/* 586 */       annot.addAttribute("style", (PdfObject)pdfCircleAnnotation.getBorderEffect().getAsString(PdfName.Style));
/*     */     }
/*     */ 
/*     */     
/* 590 */     if (pdfCircleAnnotation.getInteriorColor() != null && pdfCircleAnnotation.getInteriorColor().getColorValue() != null) {
/* 591 */       annot.addAttribute(new AttributeObject("interior-color", XfdfObjectUtils.convertColorToString(pdfCircleAnnotation.getInteriorColor().getColorValue())));
/*     */     }
/*     */     
/* 594 */     if (pdfCircleAnnotation.getRectangleDifferences() != null) {
/* 595 */       annot.addAttribute(new AttributeObject("fringe", XfdfObjectUtils.convertFringeToString(pdfCircleAnnotation.getRectangleDifferences().toFloatArray())));
/*     */     }
/*     */     
/* 598 */     annot.setContents(pdfAnnotation.getContents());
/* 599 */     if (pdfCircleAnnotation.getPopup() != null) {
/* 600 */       annot.setPopup(convertPdfPopupToAnnotObject(pdfCircleAnnotation.getPopup(), pageNumber));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void createSquareAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
/* 605 */     PdfSquareAnnotation pdfSquareAnnotation = (PdfSquareAnnotation)pdfAnnotation;
/*     */     
/* 607 */     PdfDictionary bs = pdfSquareAnnotation.getBorderStyle();
/* 608 */     if (bs != null) {
/* 609 */       addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.W), bs
/* 610 */           .getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
/*     */     }
/*     */     
/* 613 */     if (pdfSquareAnnotation.getBorderEffect() != null)
/*     */     {
/*     */       
/* 616 */       annot.addAttribute("style", (PdfObject)pdfSquareAnnotation.getBorderEffect().getAsString(PdfName.Style));
/*     */     }
/*     */     
/* 619 */     if (pdfSquareAnnotation.getInteriorColor() != null && pdfSquareAnnotation.getInteriorColor().getColorValue() != null) {
/* 620 */       annot.addAttribute(new AttributeObject("interior-color", XfdfObjectUtils.convertColorToString(pdfSquareAnnotation.getInteriorColor().getColorValue())));
/*     */     }
/* 622 */     if (pdfSquareAnnotation.getRectangleDifferences() != null) {
/* 623 */       annot.addAttribute(new AttributeObject("fringe", XfdfObjectUtils.convertFringeToString(pdfSquareAnnotation.getRectangleDifferences().toFloatArray())));
/*     */     }
/*     */     
/* 626 */     annot.setContents(pdfAnnotation.getContents());
/* 627 */     if (pdfSquareAnnotation.getPopup() != null) {
/* 628 */       annot.setPopup(convertPdfPopupToAnnotObject(pdfSquareAnnotation.getPopup(), pageNumber));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void createStampAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
/* 633 */     PdfStampAnnotation pdfStampAnnotation = (PdfStampAnnotation)pdfAnnotation;
/*     */     
/* 635 */     annot.addAttribute("icon", (PdfObject)pdfStampAnnotation.getIconName());
/*     */ 
/*     */     
/* 638 */     if (pdfStampAnnotation.getContents() != null) {
/* 639 */       annot.setContents(pdfStampAnnotation.getContents());
/*     */     }
/* 641 */     if (pdfStampAnnotation.getPopup() != null) {
/* 642 */       annot.setPopup(convertPdfPopupToAnnotObject(pdfStampAnnotation.getPopup(), pageNumber));
/*     */     }
/* 644 */     if (pdfStampAnnotation.getAppearanceDictionary() != null) {
/* 645 */       if (pdfAnnotation.getAppearanceObject(PdfName.N) != null) {
/* 646 */         annot.setAppearance(pdfStampAnnotation.getAppearanceDictionary().get(PdfName.N).toString());
/* 647 */       } else if (pdfAnnotation.getAppearanceObject(PdfName.R) != null) {
/* 648 */         annot.setAppearance(pdfStampAnnotation.getAppearanceDictionary().get(PdfName.R).toString());
/* 649 */       } else if (pdfAnnotation.getAppearanceObject(PdfName.D) != null) {
/* 650 */         annot.setAppearance(pdfStampAnnotation.getAppearanceDictionary().get(PdfName.D).toString());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void createFreeTextAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot) {
/* 656 */     PdfFreeTextAnnotation pdfFreeTextAnnotation = (PdfFreeTextAnnotation)pdfAnnotation;
/*     */     
/* 658 */     PdfDictionary bs = pdfFreeTextAnnotation.getBorderStyle();
/* 659 */     if (bs != null) {
/* 660 */       addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.W), bs
/* 661 */           .getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 666 */     annot.addAttribute(new AttributeObject("justification", String.valueOf(pdfFreeTextAnnotation.getJustification())));
/* 667 */     if (pdfFreeTextAnnotation.getIntent() != null) {
/* 668 */       annot.addAttribute(new AttributeObject("intent", pdfFreeTextAnnotation.getIntent().getValue()));
/*     */     }
/*     */     
/* 671 */     if (pdfFreeTextAnnotation.getContents() != null) {
/* 672 */       annot.setContents(pdfFreeTextAnnotation.getContents());
/*     */     }
/*     */     
/* 675 */     if (pdfFreeTextAnnotation.getDefaultAppearance() != null) {
/* 676 */       annot.setDefaultAppearance(pdfFreeTextAnnotation.getDefaultAppearance().getValue());
/*     */     }
/* 678 */     if (pdfFreeTextAnnotation.getDefaultStyleString() != null) {
/* 679 */       annot.setDefaultStyle(pdfFreeTextAnnotation.getDefaultStyleString().getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void createLineAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
/* 684 */     PdfLineAnnotation pdfLineAnnotation = (PdfLineAnnotation)pdfAnnotation;
/*     */     
/* 686 */     PdfArray line = pdfLineAnnotation.getLine();
/* 687 */     if (line != null) {
/* 688 */       annot.addAttribute(new AttributeObject("start", 
/* 689 */             XfdfObjectUtils.convertLineStartToString(line.toFloatArray())));
/* 690 */       annot.addAttribute(new AttributeObject("end", 
/* 691 */             XfdfObjectUtils.convertLineEndToString(line.toFloatArray())));
/*     */     } 
/* 693 */     if (pdfLineAnnotation.getLineEndingStyles() != null) {
/* 694 */       if (pdfLineAnnotation.getLineEndingStyles().get(0) != null) {
/* 695 */         annot.addAttribute(new AttributeObject("head", pdfLineAnnotation
/* 696 */               .getLineEndingStyles().get(0).toString().substring(1)));
/*     */       }
/* 698 */       if (pdfLineAnnotation.getLineEndingStyles().get(1) != null) {
/* 699 */         annot.addAttribute(new AttributeObject("tail", pdfLineAnnotation
/* 700 */               .getLineEndingStyles().get(1).toString().substring(1)));
/*     */       }
/*     */     } 
/*     */     
/* 704 */     if (pdfLineAnnotation.getInteriorColor() != null) {
/* 705 */       annot.addAttribute(new AttributeObject("interior-color", XfdfObjectUtils.convertColorToString(pdfLineAnnotation.getInteriorColor())));
/*     */     }
/* 707 */     annot.addAttribute("leaderExtended", pdfLineAnnotation.getLeaderLineExtension());
/* 708 */     annot.addAttribute("leaderLength", pdfLineAnnotation.getLeaderLineLength());
/* 709 */     annot.addAttribute("caption", pdfLineAnnotation.getContentsAsCaption());
/* 710 */     annot.addAttribute("intent", (PdfObject)pdfLineAnnotation.getIntent());
/* 711 */     annot.addAttribute("leader-offset", pdfLineAnnotation.getLeaderLineOffset());
/* 712 */     annot.addAttribute("caption-style", (PdfObject)pdfLineAnnotation.getCaptionPosition());
/* 713 */     if (pdfLineAnnotation.getCaptionOffset() != null) {
/* 714 */       annot.addAttribute("caption-offset-h", pdfLineAnnotation.getCaptionOffset().get(0));
/* 715 */       annot.addAttribute("caption-offset-v", pdfLineAnnotation.getCaptionOffset().get(1));
/*     */     } else {
/* 717 */       annot.addAttribute(new AttributeObject("caption-offset-h", "0"));
/* 718 */       annot.addAttribute(new AttributeObject("caption-offset-v", "0"));
/*     */     } 
/*     */     
/* 721 */     PdfDictionary bs = pdfLineAnnotation.getBorderStyle();
/* 722 */     if (bs != null) {
/* 723 */       addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.W), bs
/* 724 */           .getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
/*     */     }
/*     */     
/* 727 */     annot.setContents(pdfAnnotation.getContents());
/* 728 */     if (pdfLineAnnotation.getPopup() != null) {
/* 729 */       annot.setPopup(convertPdfPopupToAnnotObject(pdfLineAnnotation.getPopup(), pageNumber));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void createLinkAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot) {
/* 734 */     PdfLinkAnnotation pdfLinkAnnotation = (PdfLinkAnnotation)pdfAnnotation;
/*     */     
/* 736 */     if (pdfLinkAnnotation.getContents() != null) {
/* 737 */       annot.setContents(pdfLinkAnnotation.getContents());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 742 */     PdfDictionary action = pdfLinkAnnotation.getAction();
/* 743 */     if (pdfLinkAnnotation.getAction() != null) {
/* 744 */       PdfName type = action.getAsName(PdfName.S);
/* 745 */       ActionObject actionObject = new ActionObject(type);
/*     */       
/* 747 */       if (PdfName.URI.equals(type)) {
/* 748 */         actionObject.setUri(action.getAsString(PdfName.URI));
/* 749 */         if (action.get(PdfName.IsMap) != null) {
/* 750 */           actionObject.setMap(action.getAsBool(PdfName.IsMap).booleanValue());
/*     */         }
/*     */       } 
/*     */       
/* 754 */       annot.setAction(actionObject);
/*     */     } 
/* 756 */     PdfArray dest = (PdfArray)pdfLinkAnnotation.getDestinationObject();
/* 757 */     if (dest != null) {
/* 758 */       createDestElement(dest, annot);
/*     */     }
/*     */     
/* 761 */     PdfArray border = pdfLinkAnnotation.getBorder();
/* 762 */     if (border != null) {
/*     */       
/* 764 */       BorderStyleAltObject borderStyleAltObject = new BorderStyleAltObject(border.getAsNumber(0).floatValue(), border.getAsNumber(1).floatValue(), border.getAsNumber(2).floatValue());
/* 765 */       annot.setBorderStyleAlt(borderStyleAltObject);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void createDestElement(PdfArray dest, AnnotObject annot) {
/* 770 */     DestObject destObject = new DestObject();
/* 771 */     PdfName type = dest.getAsName(1);
/* 772 */     if (PdfName.XYZ.equals(type)) {
/* 773 */       FitObject xyz = new FitObject(dest.get(0));
/* 774 */       xyz.setLeft(dest.getAsNumber(2).floatValue())
/* 775 */         .setTop(dest.getAsNumber(3).floatValue())
/* 776 */         .setZoom(dest.getAsNumber(4).floatValue());
/* 777 */       destObject.setXyz(xyz);
/* 778 */     } else if (PdfName.Fit.equals(type)) {
/* 779 */       FitObject fit = new FitObject(dest.get(0));
/* 780 */       destObject.setFit(fit);
/* 781 */     } else if (PdfName.FitB.equals(type)) {
/* 782 */       FitObject fitB = new FitObject(dest.get(0));
/* 783 */       destObject.setFitB(fitB);
/* 784 */     } else if (PdfName.FitR.equals(type)) {
/* 785 */       FitObject fitR = new FitObject(dest.get(0));
/* 786 */       fitR.setLeft(dest.getAsNumber(2).floatValue());
/* 787 */       fitR.setBottom(dest.getAsNumber(3).floatValue());
/* 788 */       fitR.setRight(dest.getAsNumber(4).floatValue());
/* 789 */       fitR.setTop(dest.getAsNumber(5).floatValue());
/* 790 */       destObject.setFitR(fitR);
/* 791 */     } else if (PdfName.FitH.equals(type)) {
/* 792 */       FitObject fitH = new FitObject(dest.get(0));
/* 793 */       fitH.setTop(dest.getAsNumber(2).floatValue());
/* 794 */       destObject.setFitH(fitH);
/* 795 */     } else if (PdfName.FitBH.equals(type)) {
/* 796 */       FitObject fitBH = new FitObject(dest.get(0));
/* 797 */       fitBH.setTop(dest.getAsNumber(2).floatValue());
/* 798 */       destObject.setFitBH(fitBH);
/* 799 */     } else if (PdfName.FitBV.equals(type)) {
/* 800 */       FitObject fitBV = new FitObject(dest.get(0));
/* 801 */       fitBV.setLeft(dest.getAsNumber(2).floatValue());
/* 802 */       destObject.setFitBV(fitBV);
/* 803 */     } else if (PdfName.FitV.equals(type)) {
/* 804 */       FitObject fitV = new FitObject(dest.get(0));
/* 805 */       fitV.setLeft(dest.getAsNumber(2).floatValue());
/* 806 */       destObject.setFitV(fitV);
/*     */     } 
/* 808 */     annot.setDestination(destObject);
/*     */   }
/*     */   
/*     */   private static void createPolyGeomAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
/* 812 */     PdfPolyGeomAnnotation pdfPolyGeomAnnotation = (PdfPolyGeomAnnotation)pdfAnnotation;
/*     */     
/* 814 */     PdfDictionary bs = pdfPolyGeomAnnotation.getBorderStyle();
/* 815 */     if (bs != null) {
/* 816 */       addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.W), bs
/* 817 */           .getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
/*     */     }
/*     */     
/* 820 */     if (pdfPolyGeomAnnotation.getBorderEffect() != null)
/*     */     {
/*     */       
/* 823 */       annot.addAttribute("style", (PdfObject)pdfPolyGeomAnnotation.getBorderEffect().getAsString(PdfName.Style));
/*     */     }
/*     */     
/* 826 */     if (pdfPolyGeomAnnotation.getInteriorColor() != null) {
/* 827 */       annot.addAttribute(new AttributeObject("interior-color", XfdfObjectUtils.convertColorToString(pdfPolyGeomAnnotation.getInteriorColor())));
/*     */     }
/* 829 */     if (pdfPolyGeomAnnotation.getIntent() != null) {
/* 830 */       annot.addAttribute(new AttributeObject("intent", pdfPolyGeomAnnotation.getIntent().getValue()));
/*     */     }
/*     */ 
/*     */     
/* 834 */     if (pdfPolyGeomAnnotation.getLineEndingStyles() != null) {
/* 835 */       if (pdfPolyGeomAnnotation.getLineEndingStyles().get(0) != null) {
/* 836 */         annot.addAttribute(new AttributeObject("head", pdfPolyGeomAnnotation
/* 837 */               .getLineEndingStyles().get(0).toString().substring(1)));
/*     */       }
/* 839 */       if (pdfPolyGeomAnnotation.getLineEndingStyles().get(1) != null) {
/* 840 */         annot.addAttribute(new AttributeObject("tail", pdfPolyGeomAnnotation
/* 841 */               .getLineEndingStyles().get(1).toString().substring(1)));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 847 */     annot.setVertices(XfdfObjectUtils.convertVerticesToString(pdfPolyGeomAnnotation.getVertices().toFloatArray()));
/*     */     
/* 849 */     annot.setContents(pdfAnnotation.getContents());
/* 850 */     if (pdfPolyGeomAnnotation.getPopup() != null) {
/* 851 */       annot.setPopup(convertPdfPopupToAnnotObject(pdfPolyGeomAnnotation.getPopup(), pageNumber));
/*     */     }
/*     */   }
/*     */   
/*     */   private static AnnotObject createXfdfAnnotation(PdfAnnotation pdfAnnotation, int pageNumber) {
/* 856 */     AnnotObject annot = new AnnotObject();
/* 857 */     annot.setRef(((PdfDictionary)pdfAnnotation.getPdfObject()).getIndirectReference());
/* 858 */     annot.addFdfAttributes(pageNumber);
/*     */     
/* 860 */     if (pdfAnnotation instanceof PdfTextMarkupAnnotation) {
/* 861 */       createTextMarkupAnnotation(pdfAnnotation, annot, pageNumber);
/*     */     }
/* 863 */     if (pdfAnnotation instanceof PdfTextAnnotation) {
/* 864 */       createTextAnnotation(pdfAnnotation, annot, pageNumber);
/*     */     }
/* 866 */     if (pdfAnnotation instanceof PdfPopupAnnotation) {
/* 867 */       annot = convertPdfPopupToAnnotObject((PdfPopupAnnotation)pdfAnnotation, pageNumber);
/*     */     }
/* 869 */     if (pdfAnnotation instanceof PdfCircleAnnotation) {
/* 870 */       createCircleAnnotation(pdfAnnotation, annot, pageNumber);
/*     */     }
/* 872 */     if (pdfAnnotation instanceof PdfSquareAnnotation) {
/* 873 */       createSquareAnnotation(pdfAnnotation, annot, pageNumber);
/*     */     }
/* 875 */     if (pdfAnnotation instanceof PdfStampAnnotation) {
/* 876 */       createStampAnnotation(pdfAnnotation, annot, pageNumber);
/*     */     }
/* 878 */     if (pdfAnnotation instanceof PdfFreeTextAnnotation) {
/* 879 */       createFreeTextAnnotation(pdfAnnotation, annot);
/*     */     }
/* 881 */     if (pdfAnnotation instanceof PdfLineAnnotation) {
/* 882 */       createLineAnnotation(pdfAnnotation, annot, pageNumber);
/*     */     }
/* 884 */     if (pdfAnnotation instanceof PdfPolyGeomAnnotation) {
/* 885 */       createPolyGeomAnnotation(pdfAnnotation, annot, pageNumber);
/*     */     }
/* 887 */     if (pdfAnnotation instanceof PdfLinkAnnotation) {
/* 888 */       createLinkAnnotation(pdfAnnotation, annot);
/*     */     }
/*     */     
/* 891 */     if (isSupportedAnnotation(pdfAnnotation)) {
/* 892 */       addCommonAnnotationAttributes(annot, pdfAnnotation);
/* 893 */       if (pdfAnnotation instanceof PdfMarkupAnnotation) {
/* 894 */         addMarkupAnnotationAttributes(annot, (PdfMarkupAnnotation)pdfAnnotation);
/*     */       }
/*     */     } 
/*     */     
/* 898 */     return annot;
/*     */   }
/*     */   
/*     */   private static AnnotObject convertPdfPopupToAnnotObject(PdfPopupAnnotation pdfPopupAnnotation, int pageNumber) {
/* 902 */     AnnotObject annot = new AnnotObject();
/* 903 */     annot.addFdfAttributes(pageNumber);
/* 904 */     annot.setName("popup");
/* 905 */     annot.setRef(((PdfDictionary)pdfPopupAnnotation.getPdfObject()).getIndirectReference());
/*     */     
/* 907 */     annot.addAttribute("open", pdfPopupAnnotation.getOpen());
/* 908 */     return annot;
/*     */   }
/*     */   
/*     */   private static boolean isSupportedAnnotation(PdfAnnotation pdfAnnotation) {
/* 912 */     return (pdfAnnotation instanceof PdfTextMarkupAnnotation || pdfAnnotation instanceof PdfTextAnnotation || pdfAnnotation instanceof PdfCircleAnnotation || pdfAnnotation instanceof PdfSquareAnnotation || pdfAnnotation instanceof PdfStampAnnotation || pdfAnnotation instanceof PdfFreeTextAnnotation || pdfAnnotation instanceof PdfLineAnnotation || pdfAnnotation instanceof PdfPolyGeomAnnotation || pdfAnnotation instanceof PdfLinkAnnotation || pdfAnnotation instanceof PdfPopupAnnotation);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfdf/XfdfObjectFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */