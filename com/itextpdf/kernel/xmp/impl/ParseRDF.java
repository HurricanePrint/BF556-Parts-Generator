/*      */ package com.itextpdf.kernel.xmp.impl;
/*      */ 
/*      */ import com.itextpdf.kernel.xmp.XMPConst;
/*      */ import com.itextpdf.kernel.xmp.XMPError;
/*      */ import com.itextpdf.kernel.xmp.XMPException;
/*      */ import com.itextpdf.kernel.xmp.XMPMetaFactory;
/*      */ import com.itextpdf.kernel.xmp.XMPSchemaRegistry;
/*      */ import com.itextpdf.kernel.xmp.options.PropertyOptions;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ParseRDF
/*      */   implements XMPError, XMPConst
/*      */ {
/*      */   public static final int RDFTERM_OTHER = 0;
/*      */   public static final int RDFTERM_RDF = 1;
/*      */   public static final int RDFTERM_ID = 2;
/*      */   public static final int RDFTERM_ABOUT = 3;
/*      */   public static final int RDFTERM_PARSE_TYPE = 4;
/*      */   public static final int RDFTERM_RESOURCE = 5;
/*      */   public static final int RDFTERM_NODE_ID = 6;
/*      */   public static final int RDFTERM_DATATYPE = 7;
/*      */   public static final int RDFTERM_DESCRIPTION = 8;
/*      */   public static final int RDFTERM_LI = 9;
/*      */   public static final int RDFTERM_ABOUT_EACH = 10;
/*      */   public static final int RDFTERM_ABOUT_EACH_PREFIX = 11;
/*      */   public static final int RDFTERM_BAG_ID = 12;
/*      */   public static final int RDFTERM_FIRST_CORE = 1;
/*      */   public static final int RDFTERM_LAST_CORE = 7;
/*      */   public static final int RDFTERM_FIRST_SYNTAX = 1;
/*      */   public static final int RDFTERM_LAST_SYNTAX = 9;
/*      */   public static final int RDFTERM_FIRST_OLD = 10;
/*      */   public static final int RDFTERM_LAST_OLD = 12;
/*      */   public static final String DEFAULT_PREFIX = "_dflt";
/*      */   
/*      */   static XMPMetaImpl parse(Node xmlRoot) throws XMPException {
/*  110 */     XMPMetaImpl xmp = new XMPMetaImpl();
/*  111 */     rdf_RDF(xmp, xmlRoot);
/*  112 */     return xmp;
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
/*      */   static void rdf_RDF(XMPMetaImpl xmp, Node rdfRdfNode) throws XMPException {
/*  127 */     if (rdfRdfNode.hasAttributes()) {
/*      */       
/*  129 */       rdf_NodeElementList(xmp, xmp.getRoot(), rdfRdfNode);
/*      */     }
/*      */     else {
/*      */       
/*  133 */       throw new XMPException("Invalid attributes of rdf:RDF element", 202);
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
/*      */   private static void rdf_NodeElementList(XMPMetaImpl xmp, XMPNode xmpParent, Node rdfRdfNode) throws XMPException {
/*  151 */     for (int i = 0; i < rdfRdfNode.getChildNodes().getLength(); i++) {
/*      */       
/*  153 */       Node child = rdfRdfNode.getChildNodes().item(i);
/*      */       
/*  155 */       if (!isWhitespaceNode(child))
/*      */       {
/*  157 */         rdf_NodeElement(xmp, xmpParent, child, true);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rdf_NodeElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
/*  185 */     int nodeTerm = getRDFTermKind(xmlNode);
/*  186 */     if (nodeTerm != 8 && nodeTerm != 0)
/*      */     {
/*  188 */       throw new XMPException("Node element must be rdf:Description or typed node", 202);
/*      */     }
/*      */     
/*  191 */     if (isTopLevel && nodeTerm == 0)
/*      */     {
/*  193 */       throw new XMPException("Top level typed node not allowed", 203);
/*      */     }
/*      */ 
/*      */     
/*  197 */     rdf_NodeElementAttrs(xmp, xmpParent, xmlNode, isTopLevel);
/*  198 */     rdf_PropertyElementList(xmp, xmpParent, xmlNode, isTopLevel);
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
/*      */   private static void rdf_NodeElementAttrs(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
/*  229 */     int exclusiveAttrs = 0;
/*      */     
/*  231 */     for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
/*      */       
/*  233 */       Node attribute = xmlNode.getAttributes().item(i);
/*      */ 
/*      */ 
/*      */       
/*  237 */       if (!"xmlns".equals(attribute.getPrefix()) && (attribute
/*  238 */         .getPrefix() != null || !"xmlns".equals(attribute.getNodeName()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  243 */         int attrTerm = getRDFTermKind(attribute);
/*      */         
/*  245 */         switch (attrTerm) {
/*      */           
/*      */           case 2:
/*      */           case 3:
/*      */           case 6:
/*  250 */             if (exclusiveAttrs > 0)
/*      */             {
/*  252 */               throw new XMPException("Mutally exclusive about, ID, nodeID attributes", 202);
/*      */             }
/*      */ 
/*      */             
/*  256 */             exclusiveAttrs++;
/*      */             
/*  258 */             if (isTopLevel && attrTerm == 3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  264 */               if (xmpParent.getName() != null && xmpParent.getName().length() > 0) {
/*      */                 
/*  266 */                 if (!xmpParent.getName().equals(attribute.getNodeValue()))
/*      */                 {
/*  268 */                   throw new XMPException("Mismatched top level rdf:about values", 203);
/*      */                 }
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  274 */               xmpParent.setName(attribute.getNodeValue());
/*      */             } 
/*      */             break;
/*      */ 
/*      */           
/*      */           case 0:
/*  280 */             addChildNode(xmp, xmpParent, attribute, attribute.getNodeValue(), isTopLevel);
/*      */             break;
/*      */           
/*      */           default:
/*  284 */             throw new XMPException("Invalid nodeElement attribute", 202);
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rdf_PropertyElementList(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlParent, boolean isTopLevel) throws XMPException {
/*  304 */     for (int i = 0; i < xmlParent.getChildNodes().getLength(); i++) {
/*      */       
/*  306 */       Node currChild = xmlParent.getChildNodes().item(i);
/*  307 */       if (!isWhitespaceNode(currChild)) {
/*      */ 
/*      */ 
/*      */         
/*  311 */         if (currChild.getNodeType() != 1)
/*      */         {
/*  313 */           throw new XMPException("Expected property element node not found", 202);
/*      */         }
/*      */ 
/*      */         
/*  317 */         rdf_PropertyElement(xmp, xmpParent, currChild, isTopLevel);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rdf_PropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
/*  385 */     int nodeTerm = getRDFTermKind(xmlNode);
/*  386 */     if (!isPropertyElementName(nodeTerm))
/*      */     {
/*  388 */       throw new XMPException("Invalid property element name", 202);
/*      */     }
/*      */ 
/*      */     
/*  392 */     NamedNodeMap attributes = xmlNode.getAttributes();
/*  393 */     List<String> nsAttrs = null; int i;
/*  394 */     for (i = 0; i < attributes.getLength(); i++) {
/*      */       
/*  396 */       Node attribute = attributes.item(i);
/*  397 */       if ("xmlns".equals(attribute.getPrefix()) || (attribute
/*  398 */         .getPrefix() == null && "xmlns".equals(attribute.getNodeName()))) {
/*      */         
/*  400 */         if (nsAttrs == null)
/*      */         {
/*  402 */           nsAttrs = new ArrayList();
/*      */         }
/*  404 */         nsAttrs.add(attribute.getNodeName());
/*      */       } 
/*      */     } 
/*  407 */     if (nsAttrs != null)
/*      */     {
/*  409 */       for (Iterator<String> it = nsAttrs.iterator(); it.hasNext(); ) {
/*      */         
/*  411 */         String ns = it.next();
/*  412 */         attributes.removeNamedItem(ns);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  417 */     if (attributes.getLength() > 3) {
/*      */ 
/*      */       
/*  420 */       rdf_EmptyPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  428 */       for (i = 0; i < attributes.getLength(); i++) {
/*      */         
/*  430 */         Node attribute = attributes.item(i);
/*  431 */         String attrLocal = attribute.getLocalName();
/*  432 */         String attrNS = attribute.getNamespaceURI();
/*  433 */         String attrValue = attribute.getNodeValue();
/*  434 */         if (!"xml:lang".equals(attribute.getNodeName()) || ("ID"
/*  435 */           .equals(attrLocal) && "http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(attrNS))) {
/*      */           
/*  437 */           if ("datatype".equals(attrLocal) && "http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(attrNS)) {
/*      */             
/*  439 */             rdf_LiteralPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
/*      */           }
/*  441 */           else if (!"parseType".equals(attrLocal) || !"http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(attrNS)) {
/*      */             
/*  443 */             rdf_EmptyPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
/*      */           }
/*  445 */           else if ("Literal".equals(attrValue)) {
/*      */             
/*  447 */             rdf_ParseTypeLiteralPropertyElement();
/*      */           }
/*  449 */           else if ("Resource".equals(attrValue)) {
/*      */             
/*  451 */             rdf_ParseTypeResourcePropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
/*      */           }
/*  453 */           else if ("Collection".equals(attrValue)) {
/*      */             
/*  455 */             rdf_ParseTypeCollectionPropertyElement();
/*      */           }
/*      */           else {
/*      */             
/*  459 */             rdf_ParseTypeOtherPropertyElement();
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  469 */       if (xmlNode.hasChildNodes()) {
/*      */         
/*  471 */         for (i = 0; i < xmlNode.getChildNodes().getLength(); i++) {
/*      */           
/*  473 */           Node currChild = xmlNode.getChildNodes().item(i);
/*  474 */           if (currChild.getNodeType() != 3) {
/*      */             
/*  476 */             rdf_ResourcePropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*  481 */         rdf_LiteralPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
/*      */       }
/*      */       else {
/*      */         
/*  485 */         rdf_EmptyPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rdf_ResourcePropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
/*  510 */     if (isTopLevel && "iX:changes".equals(xmlNode.getNodeName())) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  516 */     XMPNode newCompound = addChildNode(xmp, xmpParent, xmlNode, "", isTopLevel);
/*      */ 
/*      */     
/*  519 */     for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
/*      */       
/*  521 */       Node attribute = xmlNode.getAttributes().item(i);
/*  522 */       if (!"xmlns".equals(attribute.getPrefix()) && (attribute
/*  523 */         .getPrefix() != null || !"xmlns".equals(attribute.getNodeName()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  528 */         String attrLocal = attribute.getLocalName();
/*  529 */         String attrNS = attribute.getNamespaceURI();
/*  530 */         if ("xml:lang".equals(attribute.getNodeName())) {
/*      */           
/*  532 */           addQualifierNode(newCompound, "xml:lang", attribute.getNodeValue());
/*      */         }
/*  534 */         else if (!"ID".equals(attrLocal) || !"http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(attrNS)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  540 */           throw new XMPException("Invalid attribute for resource property element", 202);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  547 */     Node currChild = null;
/*  548 */     boolean found = false;
/*      */     
/*  550 */     for (int j = 0; j < xmlNode.getChildNodes().getLength(); j++) {
/*      */       
/*  552 */       currChild = xmlNode.getChildNodes().item(j);
/*  553 */       if (!isWhitespaceNode(currChild))
/*      */       {
/*  555 */         if (currChild.getNodeType() == 1 && !found) {
/*      */           
/*  557 */           boolean isRDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(currChild.getNamespaceURI());
/*  558 */           String childLocal = currChild.getLocalName();
/*      */           
/*  560 */           if (isRDF && "Bag".equals(childLocal)) {
/*      */             
/*  562 */             newCompound.getOptions().setArray(true);
/*      */           }
/*  564 */           else if (isRDF && "Seq".equals(childLocal)) {
/*      */             
/*  566 */             newCompound.getOptions().setArray(true).setArrayOrdered(true);
/*      */           }
/*  568 */           else if (isRDF && "Alt".equals(childLocal)) {
/*      */             
/*  570 */             newCompound.getOptions().setArray(true).setArrayOrdered(true)
/*  571 */               .setArrayAlternate(true);
/*      */           }
/*      */           else {
/*      */             
/*  575 */             newCompound.getOptions().setStruct(true);
/*  576 */             if (!isRDF && !"Description".equals(childLocal)) {
/*      */               
/*  578 */               String typeName = currChild.getNamespaceURI();
/*  579 */               if (typeName == null)
/*      */               {
/*  581 */                 throw new XMPException("All XML elements must be in a namespace", 203);
/*      */               }
/*      */               
/*  584 */               typeName = typeName + ':' + childLocal;
/*  585 */               addQualifierNode(newCompound, "rdf:type", typeName);
/*      */             } 
/*      */           } 
/*      */           
/*  589 */           rdf_NodeElement(xmp, newCompound, currChild, false);
/*      */           
/*  591 */           if (newCompound.getHasValueChild()) {
/*      */             
/*  593 */             fixupQualifiedNode(newCompound);
/*      */           }
/*  595 */           else if (newCompound.getOptions().isArrayAlternate()) {
/*      */             
/*  597 */             XMPNodeUtils.detectAltText(newCompound);
/*      */           } 
/*      */           
/*  600 */           found = true;
/*      */         } else {
/*  602 */           if (found)
/*      */           {
/*      */             
/*  605 */             throw new XMPException("Invalid child of resource property element", 202);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  610 */           throw new XMPException("Children of resource property element must be XML elements", 202);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  616 */     if (!found)
/*      */     {
/*      */       
/*  619 */       throw new XMPException("Missing child of resource property element", 202);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rdf_LiteralPropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
/*  641 */     XMPNode newChild = addChildNode(xmp, xmpParent, xmlNode, null, isTopLevel);
/*      */     
/*  643 */     for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
/*      */       
/*  645 */       Node attribute = xmlNode.getAttributes().item(i);
/*  646 */       if (!"xmlns".equals(attribute.getPrefix()) && (attribute
/*  647 */         .getPrefix() != null || !"xmlns".equals(attribute.getNodeName()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  652 */         String attrNS = attribute.getNamespaceURI();
/*  653 */         String attrLocal = attribute.getLocalName();
/*  654 */         if ("xml:lang".equals(attribute.getNodeName())) {
/*      */           
/*  656 */           addQualifierNode(newChild, "xml:lang", attribute.getNodeValue());
/*      */         }
/*  658 */         else if (!"http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(attrNS) || (
/*  659 */           !"ID".equals(attrLocal) && !"datatype".equals(attrLocal))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  665 */           throw new XMPException("Invalid attribute for literal property element", 202);
/*      */         } 
/*      */       } 
/*      */     } 
/*  669 */     String textValue = "";
/*  670 */     for (int j = 0; j < xmlNode.getChildNodes().getLength(); j++) {
/*      */       
/*  672 */       Node child = xmlNode.getChildNodes().item(j);
/*  673 */       if (child.getNodeType() == 3) {
/*      */         
/*  675 */         textValue = textValue + child.getNodeValue();
/*      */       }
/*      */       else {
/*      */         
/*  679 */         throw new XMPException("Invalid child of literal property element", 202);
/*      */       } 
/*      */     } 
/*  682 */     newChild.setValue(textValue);
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
/*      */   private static void rdf_ParseTypeLiteralPropertyElement() throws XMPException {
/*  697 */     throw new XMPException("ParseTypeLiteral property element not allowed", 203);
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
/*      */   private static void rdf_ParseTypeResourcePropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
/*  720 */     XMPNode newStruct = addChildNode(xmp, xmpParent, xmlNode, "", isTopLevel);
/*      */     
/*  722 */     newStruct.getOptions().setStruct(true);
/*      */     
/*  724 */     for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
/*      */       
/*  726 */       Node attribute = xmlNode.getAttributes().item(i);
/*  727 */       if (!"xmlns".equals(attribute.getPrefix()) && (attribute
/*  728 */         .getPrefix() != null || !"xmlns".equals(attribute.getNodeName()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  733 */         String attrLocal = attribute.getLocalName();
/*  734 */         String attrNS = attribute.getNamespaceURI();
/*  735 */         if ("xml:lang".equals(attribute.getNodeName())) {
/*      */           
/*  737 */           addQualifierNode(newStruct, "xml:lang", attribute.getNodeValue());
/*      */         }
/*  739 */         else if (!"http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(attrNS) || (
/*  740 */           !"ID".equals(attrLocal) && !"parseType".equals(attrLocal))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  747 */           throw new XMPException("Invalid attribute for ParseTypeResource property element", 202);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  752 */     rdf_PropertyElementList(xmp, newStruct, xmlNode, false);
/*      */     
/*  754 */     if (newStruct.getHasValueChild())
/*      */     {
/*  756 */       fixupQualifiedNode(newStruct);
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
/*      */   private static void rdf_ParseTypeCollectionPropertyElement() throws XMPException {
/*  772 */     throw new XMPException("ParseTypeCollection property element not allowed", 203);
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
/*      */   private static void rdf_ParseTypeOtherPropertyElement() throws XMPException {
/*  786 */     throw new XMPException("ParseTypeOther property element not allowed", 203);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rdf_EmptyPropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
/*  838 */     boolean hasPropertyAttrs = false;
/*  839 */     boolean hasResourceAttr = false;
/*  840 */     boolean hasNodeIDAttr = false;
/*  841 */     boolean hasValueAttr = false;
/*      */     
/*  843 */     Node valueNode = null;
/*      */     
/*  845 */     if (xmlNode.hasChildNodes())
/*      */     {
/*  847 */       throw new XMPException("Nested content not allowed with rdf:resource or property attributes", 202);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  853 */     for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
/*      */       
/*  855 */       Node attribute = xmlNode.getAttributes().item(i);
/*  856 */       if (!"xmlns".equals(attribute.getPrefix()) && (attribute
/*  857 */         .getPrefix() != null || !"xmlns".equals(attribute.getNodeName()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  862 */         int attrTerm = getRDFTermKind(attribute);
/*      */         
/*  864 */         switch (attrTerm) {
/*      */           case 2:
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 5:
/*  871 */             if (hasNodeIDAttr)
/*      */             {
/*  873 */               throw new XMPException("Empty property element can't have both rdf:resource and rdf:nodeID", 202);
/*      */             }
/*      */ 
/*      */             
/*  877 */             if (hasValueAttr)
/*      */             {
/*  879 */               throw new XMPException("Empty property element can't have both rdf:value and rdf:resource", 203);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  884 */             hasResourceAttr = true;
/*  885 */             if (!hasValueAttr)
/*      */             {
/*  887 */               valueNode = attribute;
/*      */             }
/*      */             break;
/*      */           
/*      */           case 6:
/*  892 */             if (hasResourceAttr)
/*      */             {
/*  894 */               throw new XMPException("Empty property element can't have both rdf:resource and rdf:nodeID", 202);
/*      */             }
/*      */ 
/*      */             
/*  898 */             hasNodeIDAttr = true;
/*      */             break;
/*      */           
/*      */           case 0:
/*  902 */             if ("value".equals(attribute.getLocalName()) && "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
/*  903 */               .equals(attribute.getNamespaceURI())) {
/*      */               
/*  905 */               if (hasResourceAttr)
/*      */               {
/*  907 */                 throw new XMPException("Empty property element can't have both rdf:value and rdf:resource", 203);
/*      */               }
/*      */ 
/*      */               
/*  911 */               hasValueAttr = true;
/*  912 */               valueNode = attribute; break;
/*      */             } 
/*  914 */             if (!"xml:lang".equals(attribute.getNodeName()))
/*      */             {
/*  916 */               hasPropertyAttrs = true;
/*      */             }
/*      */             break;
/*      */           
/*      */           default:
/*  921 */             throw new XMPException("Unrecognized attribute of empty property element", 202);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/*      */     } 
/*  932 */     XMPNode childNode = addChildNode(xmp, xmpParent, xmlNode, "", isTopLevel);
/*  933 */     boolean childIsStruct = false;
/*      */     
/*  935 */     if (hasValueAttr || hasResourceAttr) {
/*      */       
/*  937 */       childNode.setValue((valueNode != null) ? valueNode.getNodeValue() : "");
/*  938 */       if (!hasValueAttr)
/*      */       {
/*      */         
/*  941 */         childNode.getOptions().setURI(true);
/*      */       }
/*      */     }
/*  944 */     else if (hasPropertyAttrs) {
/*      */       
/*  946 */       childNode.getOptions().setStruct(true);
/*  947 */       childIsStruct = true;
/*      */     } 
/*      */     
/*  950 */     for (int j = 0; j < xmlNode.getAttributes().getLength(); j++) {
/*      */       
/*  952 */       Node attribute = xmlNode.getAttributes().item(j);
/*  953 */       if (attribute != valueNode && 
/*  954 */         !"xmlns".equals(attribute.getPrefix()) && (attribute
/*  955 */         .getPrefix() != null || !"xmlns".equals(attribute.getNodeName()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  960 */         int attrTerm = getRDFTermKind(attribute);
/*      */         
/*  962 */         switch (attrTerm) {
/*      */           case 2:
/*      */           case 6:
/*      */             break;
/*      */ 
/*      */           
/*      */           case 5:
/*  969 */             addQualifierNode(childNode, "rdf:resource", attribute.getNodeValue());
/*      */             break;
/*      */           
/*      */           case 0:
/*  973 */             if (!childIsStruct) {
/*      */               
/*  975 */               addQualifierNode(childNode, attribute
/*  976 */                   .getNodeName(), attribute.getNodeValue()); break;
/*      */             } 
/*  978 */             if ("xml:lang".equals(attribute.getNodeName())) {
/*      */               
/*  980 */               addQualifierNode(childNode, "xml:lang", attribute.getNodeValue());
/*      */               
/*      */               break;
/*      */             } 
/*  984 */             addChildNode(xmp, childNode, attribute, attribute.getNodeValue(), false);
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/*  989 */             throw new XMPException("Unrecognized attribute of empty property element", 202);
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMPNode addChildNode(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, String value, boolean isTopLevel) throws XMPException {
/*      */     String childName;
/* 1011 */     XMPSchemaRegistry registry = XMPMetaFactory.getSchemaRegistry();
/* 1012 */     String namespace = xmlNode.getNamespaceURI();
/*      */     
/* 1014 */     if (namespace != null) {
/*      */       
/* 1016 */       if ("http://purl.org/dc/1.1/".equals(namespace))
/*      */       {
/*      */         
/* 1019 */         namespace = "http://purl.org/dc/elements/1.1/";
/*      */       }
/*      */       
/* 1022 */       String prefix = registry.getNamespacePrefix(namespace);
/* 1023 */       if (prefix == null) {
/*      */         
/* 1025 */         prefix = (xmlNode.getPrefix() != null) ? xmlNode.getPrefix() : "_dflt";
/* 1026 */         prefix = registry.registerNamespace(namespace, prefix);
/*      */       } 
/* 1028 */       childName = prefix + xmlNode.getLocalName();
/*      */     }
/*      */     else {
/*      */       
/* 1032 */       throw new XMPException("XML namespace required for all elements and attributes", 202);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1038 */     PropertyOptions childOptions = new PropertyOptions();
/* 1039 */     boolean isAlias = false;
/* 1040 */     if (isTopLevel) {
/*      */ 
/*      */ 
/*      */       
/* 1044 */       XMPNode schemaNode = XMPNodeUtils.findSchemaNode(xmp.getRoot(), namespace, "_dflt", true);
/*      */       
/* 1046 */       schemaNode.setImplicit(false);
/*      */       
/* 1048 */       xmpParent = schemaNode;
/*      */ 
/*      */ 
/*      */       
/* 1052 */       if (registry.findAlias(childName) != null) {
/*      */         
/* 1054 */         isAlias = true;
/* 1055 */         xmp.getRoot().setHasAliases(true);
/* 1056 */         schemaNode.setHasAliases(true);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1062 */     boolean isArrayItem = "rdf:li".equals(childName);
/* 1063 */     boolean isValueNode = "rdf:value".equals(childName);
/*      */ 
/*      */     
/* 1066 */     XMPNode newChild = new XMPNode(childName, value, childOptions);
/*      */     
/* 1068 */     newChild.setAlias(isAlias);
/*      */ 
/*      */     
/* 1071 */     if (!isValueNode) {
/*      */       
/* 1073 */       xmpParent.addChild(newChild);
/*      */     }
/*      */     else {
/*      */       
/* 1077 */       xmpParent.addChild(1, newChild);
/*      */     } 
/*      */ 
/*      */     
/* 1081 */     if (isValueNode) {
/*      */       
/* 1083 */       if (isTopLevel || !xmpParent.getOptions().isStruct())
/*      */       {
/* 1085 */         throw new XMPException("Misplaced rdf:value element", 202);
/*      */       }
/* 1087 */       xmpParent.setHasValueChild(true);
/*      */     } 
/*      */     
/* 1090 */     if (isArrayItem) {
/*      */       
/* 1092 */       if (!xmpParent.getOptions().isArray())
/*      */       {
/* 1094 */         throw new XMPException("Misplaced rdf:li element", 202);
/*      */       }
/* 1096 */       newChild.setName("[]");
/*      */     } 
/*      */     
/* 1099 */     return newChild;
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
/*      */   private static XMPNode addQualifierNode(XMPNode xmpParent, String name, String value) throws XMPException {
/* 1116 */     boolean isLang = "xml:lang".equals(name);
/*      */     
/* 1118 */     XMPNode newQual = null;
/*      */ 
/*      */     
/* 1121 */     newQual = new XMPNode(name, isLang ? Utils.normalizeLangValue(value) : value, null);
/* 1122 */     xmpParent.addQualifier(newQual);
/*      */     
/* 1124 */     return newQual;
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
/*      */   private static void fixupQualifiedNode(XMPNode xmpParent) throws XMPException {
/* 1140 */     assert xmpParent.getOptions().isStruct() && xmpParent.hasChildren();
/*      */     
/* 1142 */     XMPNode valueNode = xmpParent.getChild(1);
/* 1143 */     assert "rdf:value".equals(valueNode.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1150 */     if (valueNode.getOptions().getHasLanguage()) {
/*      */       
/* 1152 */       if (xmpParent.getOptions().getHasLanguage())
/*      */       {
/* 1154 */         throw new XMPException("Redundant xml:lang for rdf:value element", 203);
/*      */       }
/*      */       
/* 1157 */       XMPNode langQual = valueNode.getQualifier(1);
/* 1158 */       valueNode.removeQualifier(langQual);
/* 1159 */       xmpParent.addQualifier(langQual);
/*      */     } 
/*      */     
/*      */     int i;
/* 1163 */     for (i = 1; i <= valueNode.getQualifierLength(); i++) {
/*      */       
/* 1165 */       XMPNode qualifier = valueNode.getQualifier(i);
/* 1166 */       xmpParent.addQualifier(qualifier);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1172 */     for (i = 2; i <= xmpParent.getChildrenLength(); i++) {
/*      */       
/* 1174 */       XMPNode qualifier = xmpParent.getChild(i);
/* 1175 */       xmpParent.addQualifier(qualifier);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1180 */     assert xmpParent.getOptions().isStruct() || xmpParent.getHasValueChild();
/*      */     
/* 1182 */     xmpParent.setHasValueChild(false);
/* 1183 */     xmpParent.getOptions().setStruct(false);
/* 1184 */     xmpParent.getOptions().mergeWith(valueNode.getOptions());
/* 1185 */     xmpParent.setValue(valueNode.getValue());
/*      */     
/* 1187 */     xmpParent.removeChildren();
/* 1188 */     for (Iterator<XMPNode> it = valueNode.iterateChildren(); it.hasNext(); ) {
/*      */       
/* 1190 */       XMPNode child = it.next();
/* 1191 */       xmpParent.addChild(child);
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
/*      */   private static boolean isWhitespaceNode(Node node) {
/* 1204 */     if (node.getNodeType() != 3)
/*      */     {
/* 1206 */       return false;
/*      */     }
/*      */     
/* 1209 */     String value = node.getNodeValue();
/* 1210 */     for (int i = 0; i < value.length(); i++) {
/*      */       
/* 1212 */       if (!Character.isWhitespace(value.charAt(i)))
/*      */       {
/* 1214 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1218 */     return true;
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
/*      */   private static boolean isPropertyElementName(int term) {
/* 1231 */     if (term == 8 || isOldTerm(term))
/*      */     {
/* 1233 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1237 */     return !isCoreSyntaxTerm(term);
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
/*      */   private static boolean isOldTerm(int term) {
/* 1251 */     return (10 <= term && term <= 12);
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
/*      */   private static boolean isCoreSyntaxTerm(int term) {
/* 1265 */     return (1 <= term && term <= 7);
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
/*      */   private static int getRDFTermKind(Node node) {
/* 1278 */     String localName = node.getLocalName();
/* 1279 */     String namespace = node.getNamespaceURI();
/*      */     
/* 1281 */     if (namespace == null && ("about"
/*      */       
/* 1283 */       .equals(localName) || "ID".equals(localName)) && node instanceof Attr && "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
/*      */       
/* 1285 */       .equals(((Attr)node).getOwnerElement().getNamespaceURI()))
/*      */     {
/*      */       
/* 1288 */       namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
/*      */     }
/*      */     
/* 1291 */     if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(namespace))
/*      */     {
/* 1293 */       switch (localName) {
/*      */         case "li":
/* 1295 */           return 9;
/*      */         case "parseType":
/* 1297 */           return 4;
/*      */         case "Description":
/* 1299 */           return 8;
/*      */         case "about":
/* 1301 */           return 3;
/*      */         case "resource":
/* 1303 */           return 5;
/*      */         case "RDF":
/* 1305 */           return 1;
/*      */         case "ID":
/* 1307 */           return 2;
/*      */         case "nodeID":
/* 1309 */           return 6;
/*      */         case "datatype":
/* 1311 */           return 7;
/*      */         case "aboutEach":
/* 1313 */           return 10;
/*      */         case "aboutEachPrefix":
/* 1315 */           return 11;
/*      */         case "bagID":
/* 1317 */           return 12;
/*      */       } 
/*      */     
/*      */     }
/* 1321 */     return 0;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/impl/ParseRDF.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */