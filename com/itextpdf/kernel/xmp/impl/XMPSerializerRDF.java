/*      */ package com.itextpdf.kernel.xmp.impl;
/*      */ 
/*      */ import com.itextpdf.kernel.xmp.XMPException;
/*      */ import com.itextpdf.kernel.xmp.XMPMeta;
/*      */ import com.itextpdf.kernel.xmp.XMPMetaFactory;
/*      */ import com.itextpdf.kernel.xmp.options.SerializeOptions;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMPSerializerRDF
/*      */ {
/*      */   private static final int DEFAULT_PAD = 2048;
/*      */   private static final String PACKET_HEADER = "<?xpacket begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>";
/*      */   private static final String PACKET_TRAILER = "<?xpacket end=\"";
/*      */   private static final String PACKET_TRAILER2 = "\"?>";
/*      */   private static final String RDF_XMPMETA_START = "<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"";
/*      */   private static final String RDF_XMPMETA_END = "</x:xmpmeta>";
/*      */   private static final String RDF_RDF_START = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">";
/*      */   private static final String RDF_RDF_END = "</rdf:RDF>";
/*      */   private static final String RDF_SCHEMA_START = "<rdf:Description rdf:about=";
/*      */   private static final String RDF_SCHEMA_END = "</rdf:Description>";
/*      */   private static final String RDF_STRUCT_START = "<rdf:Description";
/*      */   private static final String RDF_STRUCT_END = "</rdf:Description>";
/*      */   private static final String RDF_EMPTY_STRUCT = "<rdf:Description/>";
/*   90 */   static final Set<String> RDF_ATTR_QUALIFIER = new HashSet<>(Arrays.asList(new String[] { "xml:lang", "rdf:resource", "rdf:ID", "rdf:bagID", "rdf:nodeID" }));
/*      */ 
/*      */   
/*      */   private XMPMetaImpl xmp;
/*      */ 
/*      */   
/*      */   private CountOutputStream outputStream;
/*      */ 
/*      */   
/*      */   private OutputStreamWriter writer;
/*      */ 
/*      */   
/*      */   private SerializeOptions options;
/*      */   
/*  104 */   private int unicodeSize = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int padding;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serialize(XMPMeta xmp, OutputStream out, SerializeOptions options) throws XMPException {
/*      */     try {
/*  124 */       this.outputStream = new CountOutputStream(out);
/*  125 */       this.xmp = (XMPMetaImpl)xmp;
/*  126 */       this.options = options;
/*  127 */       this.padding = options.getPadding();
/*      */       
/*  129 */       this.writer = new OutputStreamWriter(this.outputStream, options.getEncoding());
/*      */       
/*  131 */       checkOptionsConsistence();
/*      */ 
/*      */ 
/*      */       
/*  135 */       String tailStr = serializeAsRDF();
/*  136 */       this.writer.flush();
/*      */ 
/*      */       
/*  139 */       addPadding(tailStr.length());
/*      */ 
/*      */       
/*  142 */       write(tailStr);
/*  143 */       this.writer.flush();
/*      */       
/*  145 */       this.outputStream.close();
/*      */     }
/*  147 */     catch (IOException e) {
/*      */       
/*  149 */       throw new XMPException("Error writing to the OutputStream", 0);
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
/*      */   private void addPadding(int tailLength) throws XMPException, IOException {
/*  162 */     if (this.options.getExactPacketLength()) {
/*      */ 
/*      */       
/*  165 */       int minSize = this.outputStream.getBytesWritten() + tailLength * this.unicodeSize;
/*  166 */       if (minSize > this.padding)
/*      */       {
/*  168 */         throw new XMPException("Can't fit into specified packet size", 107);
/*      */       }
/*      */       
/*  171 */       this.padding -= minSize;
/*      */     } 
/*      */ 
/*      */     
/*  175 */     this.padding /= this.unicodeSize;
/*      */     
/*  177 */     int newlineLen = this.options.getNewline().length();
/*  178 */     if (this.padding >= newlineLen) {
/*      */       
/*  180 */       this.padding -= newlineLen;
/*  181 */       while (this.padding >= 100 + newlineLen) {
/*      */         
/*  183 */         writeChars(100, ' ');
/*  184 */         writeNewline();
/*  185 */         this.padding -= 100 + newlineLen;
/*      */       } 
/*  187 */       writeChars(this.padding, ' ');
/*  188 */       writeNewline();
/*      */     }
/*      */     else {
/*      */       
/*  192 */       writeChars(this.padding, ' ');
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkOptionsConsistence() throws XMPException {
/*  203 */     if ((this.options.getEncodeUTF16BE() | this.options.getEncodeUTF16LE()) != 0)
/*      */     {
/*  205 */       this.unicodeSize = 2;
/*      */     }
/*      */     
/*  208 */     if (this.options.getExactPacketLength()) {
/*      */       
/*  210 */       if ((this.options.getOmitPacketWrapper() | this.options.getIncludeThumbnailPad()) != 0)
/*      */       {
/*  212 */         throw new XMPException("Inconsistent options for exact size serialize", 103);
/*      */       }
/*      */       
/*  215 */       if ((this.options.getPadding() & this.unicodeSize - 1) != 0)
/*      */       {
/*  217 */         throw new XMPException("Exact size must be a multiple of the Unicode element", 103);
/*      */       
/*      */       }
/*      */     }
/*  221 */     else if (this.options.getReadOnlyPacket()) {
/*      */       
/*  223 */       if ((this.options.getOmitPacketWrapper() | this.options.getIncludeThumbnailPad()) != 0)
/*      */       {
/*  225 */         throw new XMPException("Inconsistent options for read-only packet", 103);
/*      */       }
/*      */       
/*  228 */       this.padding = 0;
/*      */     }
/*  230 */     else if (this.options.getOmitPacketWrapper()) {
/*      */       
/*  232 */       if (this.options.getIncludeThumbnailPad())
/*      */       {
/*  234 */         throw new XMPException("Inconsistent options for non-packet serialize", 103);
/*      */       }
/*      */       
/*  237 */       this.padding = 0;
/*      */     }
/*      */     else {
/*      */       
/*  241 */       if (this.padding == 0)
/*      */       {
/*  243 */         this.padding = 2048 * this.unicodeSize;
/*      */       }
/*      */       
/*  246 */       if (this.options.getIncludeThumbnailPad())
/*      */       {
/*  248 */         if (!this.xmp.doesPropertyExist("http://ns.adobe.com/xap/1.0/", "Thumbnails"))
/*      */         {
/*  250 */           this.padding += 10000 * this.unicodeSize;
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
/*      */   private String serializeAsRDF() throws IOException, XMPException {
/*  265 */     int level = 0;
/*      */ 
/*      */     
/*  268 */     if (!this.options.getOmitPacketWrapper()) {
/*      */       
/*  270 */       writeIndent(level);
/*  271 */       write("<?xpacket begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>");
/*  272 */       writeNewline();
/*      */     } 
/*      */ 
/*      */     
/*  276 */     if (!this.options.getOmitXmpMetaElement()) {
/*      */       
/*  278 */       writeIndent(level);
/*  279 */       write("<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"");
/*      */       
/*  281 */       if (!this.options.getOmitVersionAttribute())
/*      */       {
/*  283 */         write(XMPMetaFactory.getVersionInfo().getMessage());
/*      */       }
/*  285 */       write("\">");
/*  286 */       writeNewline();
/*  287 */       level++;
/*      */     } 
/*      */ 
/*      */     
/*  291 */     writeIndent(level);
/*  292 */     write("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">");
/*  293 */     writeNewline();
/*      */ 
/*      */     
/*  296 */     if (this.options.getUseCanonicalFormat()) {
/*      */       
/*  298 */       serializeCanonicalRDFSchemas(level);
/*      */     }
/*      */     else {
/*      */       
/*  302 */       serializeCompactRDFSchemas(level);
/*      */     } 
/*      */ 
/*      */     
/*  306 */     writeIndent(level);
/*  307 */     write("</rdf:RDF>");
/*  308 */     writeNewline();
/*      */ 
/*      */     
/*  311 */     if (!this.options.getOmitXmpMetaElement()) {
/*      */       
/*  313 */       level--;
/*  314 */       writeIndent(level);
/*  315 */       write("</x:xmpmeta>");
/*  316 */       writeNewline();
/*      */     } 
/*      */     
/*  319 */     String tailStr = "";
/*  320 */     if (!this.options.getOmitPacketWrapper()) {
/*      */       
/*  322 */       for (level = this.options.getBaseIndent(); level > 0; level--)
/*      */       {
/*  324 */         tailStr = tailStr + this.options.getIndent();
/*      */       }
/*      */       
/*  327 */       tailStr = tailStr + "<?xpacket end=\"";
/*  328 */       tailStr = tailStr + (this.options.getReadOnlyPacket() ? 114 : 119);
/*  329 */       tailStr = tailStr + "\"?>";
/*      */     } 
/*      */     
/*  332 */     return tailStr;
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
/*      */   private void serializeCanonicalRDFSchemas(int level) throws IOException, XMPException {
/*  344 */     if (this.xmp.getRoot().getChildrenLength() > 0) {
/*      */       
/*  346 */       startOuterRDFDescription(this.xmp.getRoot(), level);
/*      */       
/*  348 */       for (Iterator<XMPNode> it = this.xmp.getRoot().iterateChildren(); it.hasNext(); ) {
/*      */         
/*  350 */         XMPNode currSchema = it.next();
/*  351 */         serializeCanonicalRDFSchema(currSchema, level);
/*      */       } 
/*      */       
/*  354 */       endOuterRDFDescription(level);
/*      */     }
/*      */     else {
/*      */       
/*  358 */       writeIndent(level + 1);
/*  359 */       write("<rdf:Description rdf:about=");
/*  360 */       writeTreeName();
/*  361 */       write("/>");
/*  362 */       writeNewline();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeTreeName() throws IOException {
/*  372 */     write(34);
/*  373 */     String name = this.xmp.getRoot().getName();
/*  374 */     if (name != null)
/*      */     {
/*  376 */       appendNodeValue(name, true);
/*      */     }
/*  378 */     write(34);
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
/*      */   private void serializeCompactRDFSchemas(int level) throws IOException, XMPException {
/*  391 */     writeIndent(level + 1);
/*  392 */     write("<rdf:Description rdf:about=");
/*  393 */     writeTreeName();
/*      */ 
/*      */     
/*  396 */     Set<String> usedPrefixes = new HashSet<>();
/*  397 */     usedPrefixes.add("xml");
/*  398 */     usedPrefixes.add("rdf");
/*      */     
/*  400 */     for (Iterator<XMPNode> it = this.xmp.getRoot().iterateChildren(); it.hasNext(); ) {
/*      */       
/*  402 */       XMPNode schema = it.next();
/*  403 */       declareUsedNamespaces(schema, usedPrefixes, level + 3);
/*      */     } 
/*      */ 
/*      */     
/*  407 */     boolean allAreAttrs = true;
/*  408 */     for (Iterator<XMPNode> iterator2 = this.xmp.getRoot().iterateChildren(); iterator2.hasNext(); ) {
/*      */       
/*  410 */       XMPNode schema = iterator2.next();
/*  411 */       allAreAttrs &= serializeCompactRDFAttrProps(schema, level + 2);
/*      */     } 
/*      */     
/*  414 */     if (!allAreAttrs) {
/*      */       
/*  416 */       write(62);
/*  417 */       writeNewline();
/*      */     }
/*      */     else {
/*      */       
/*  421 */       write("/>");
/*  422 */       writeNewline();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  427 */     for (Iterator<XMPNode> iterator1 = this.xmp.getRoot().iterateChildren(); iterator1.hasNext(); ) {
/*      */       
/*  429 */       XMPNode schema = iterator1.next();
/*  430 */       serializeCompactRDFElementProps(schema, level + 2);
/*      */     } 
/*      */ 
/*      */     
/*  434 */     writeIndent(level + 1);
/*  435 */     write("</rdf:Description>");
/*  436 */     writeNewline();
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
/*      */   private boolean serializeCompactRDFAttrProps(XMPNode parentNode, int indent) throws IOException {
/*  452 */     boolean allAreAttrs = true;
/*      */     
/*  454 */     for (Iterator<XMPNode> it = parentNode.iterateChildren(); it.hasNext(); ) {
/*      */       
/*  456 */       XMPNode prop = it.next();
/*      */       
/*  458 */       if (canBeRDFAttrProp(prop)) {
/*      */         
/*  460 */         writeNewline();
/*  461 */         writeIndent(indent);
/*  462 */         write(prop.getName());
/*  463 */         write("=\"");
/*  464 */         appendNodeValue(prop.getValue(), true);
/*  465 */         write(34);
/*      */         
/*      */         continue;
/*      */       } 
/*  469 */       allAreAttrs = false;
/*      */     } 
/*      */     
/*  472 */     return allAreAttrs;
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
/*      */   private void serializeCompactRDFElementProps(XMPNode parentNode, int indent) throws IOException, XMPException {
/*  528 */     for (Iterator<XMPNode> it = parentNode.iterateChildren(); it.hasNext(); ) {
/*      */       
/*  530 */       XMPNode node = it.next();
/*  531 */       if (canBeRDFAttrProp(node)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*  536 */       boolean emitEndTag = true;
/*  537 */       boolean indentEndTag = true;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  542 */       String elemName = node.getName();
/*  543 */       if ("[]".equals(elemName))
/*      */       {
/*  545 */         elemName = "rdf:li";
/*      */       }
/*      */       
/*  548 */       writeIndent(indent);
/*  549 */       write(60);
/*  550 */       write(elemName);
/*      */       
/*  552 */       boolean hasGeneralQualifiers = false;
/*  553 */       boolean hasRDFResourceQual = false;
/*      */       
/*  555 */       for (Iterator<XMPNode> iq = node.iterateQualifier(); iq.hasNext(); ) {
/*      */         
/*  557 */         XMPNode qualifier = iq.next();
/*  558 */         if (!RDF_ATTR_QUALIFIER.contains(qualifier.getName())) {
/*      */           
/*  560 */           hasGeneralQualifiers = true;
/*      */           
/*      */           continue;
/*      */         } 
/*  564 */         hasRDFResourceQual = "rdf:resource".equals(qualifier.getName());
/*  565 */         write(32);
/*  566 */         write(qualifier.getName());
/*  567 */         write("=\"");
/*  568 */         appendNodeValue(qualifier.getValue(), true);
/*  569 */         write(34);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  575 */       if (hasGeneralQualifiers) {
/*      */         
/*  577 */         serializeCompactRDFGeneralQualifier(indent, node);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  582 */       else if (!node.getOptions().isCompositeProperty()) {
/*      */         
/*  584 */         boolean[] result = serializeCompactRDFSimpleProp(node);
/*  585 */         emitEndTag = result[0];
/*  586 */         indentEndTag = result[1];
/*      */       }
/*  588 */       else if (node.getOptions().isArray()) {
/*      */         
/*  590 */         serializeCompactRDFArrayProp(node, indent);
/*      */       }
/*      */       else {
/*      */         
/*  594 */         emitEndTag = serializeCompactRDFStructProp(node, indent, hasRDFResourceQual);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  601 */       if (emitEndTag) {
/*      */         
/*  603 */         if (indentEndTag)
/*      */         {
/*  605 */           writeIndent(indent);
/*      */         }
/*  607 */         write("</");
/*  608 */         write(elemName);
/*  609 */         write(62);
/*  610 */         writeNewline();
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
/*      */   private boolean[] serializeCompactRDFSimpleProp(XMPNode node) throws IOException {
/*  627 */     boolean emitEndTag = true;
/*  628 */     boolean indentEndTag = true;
/*      */     
/*  630 */     if (node.getOptions().isURI()) {
/*      */       
/*  632 */       write(" rdf:resource=\"");
/*  633 */       appendNodeValue(node.getValue(), true);
/*  634 */       write("\"/>");
/*  635 */       writeNewline();
/*  636 */       emitEndTag = false;
/*      */     }
/*  638 */     else if (node.getValue() == null || node.getValue().length() == 0) {
/*      */       
/*  640 */       write("/>");
/*  641 */       writeNewline();
/*  642 */       emitEndTag = false;
/*      */     }
/*      */     else {
/*      */       
/*  646 */       write(62);
/*  647 */       appendNodeValue(node.getValue(), false);
/*  648 */       indentEndTag = false;
/*      */     } 
/*      */     
/*  651 */     return new boolean[] { emitEndTag, indentEndTag };
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
/*      */   private void serializeCompactRDFArrayProp(XMPNode node, int indent) throws IOException, XMPException {
/*  667 */     write(62);
/*  668 */     writeNewline();
/*  669 */     emitRDFArrayTag(node, true, indent + 1);
/*      */     
/*  671 */     if (node.getOptions().isArrayAltText())
/*      */     {
/*  673 */       XMPNodeUtils.normalizeLangArray(node);
/*      */     }
/*      */     
/*  676 */     serializeCompactRDFElementProps(node, indent + 2);
/*      */     
/*  678 */     emitRDFArrayTag(node, false, indent + 1);
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
/*      */   private boolean serializeCompactRDFStructProp(XMPNode node, int indent, boolean hasRDFResourceQual) throws XMPException, IOException {
/*  696 */     boolean hasAttrFields = false;
/*  697 */     boolean hasElemFields = false;
/*  698 */     boolean emitEndTag = true;
/*      */     
/*  700 */     for (Iterator<XMPNode> ic = node.iterateChildren(); ic.hasNext(); ) {
/*      */       
/*  702 */       XMPNode field = ic.next();
/*  703 */       if (canBeRDFAttrProp(field)) {
/*      */         
/*  705 */         hasAttrFields = true;
/*      */       }
/*      */       else {
/*      */         
/*  709 */         hasElemFields = true;
/*      */       } 
/*      */       
/*  712 */       if (hasAttrFields && hasElemFields) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  718 */     if (hasRDFResourceQual && hasElemFields)
/*      */     {
/*  720 */       throw new XMPException("Can't mix rdf:resource qualifier and element fields", 202);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  725 */     if (!node.hasChildren()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  731 */       write(" rdf:parseType=\"Resource\"/>");
/*  732 */       writeNewline();
/*  733 */       emitEndTag = false;
/*      */     
/*      */     }
/*  736 */     else if (!hasElemFields) {
/*      */ 
/*      */ 
/*      */       
/*  740 */       serializeCompactRDFAttrProps(node, indent + 1);
/*  741 */       write("/>");
/*  742 */       writeNewline();
/*  743 */       emitEndTag = false;
/*      */     
/*      */     }
/*  746 */     else if (!hasAttrFields) {
/*      */ 
/*      */ 
/*      */       
/*  750 */       write(" rdf:parseType=\"Resource\">");
/*  751 */       writeNewline();
/*  752 */       serializeCompactRDFElementProps(node, indent + 1);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  758 */       write(62);
/*  759 */       writeNewline();
/*  760 */       writeIndent(indent + 1);
/*  761 */       write("<rdf:Description");
/*  762 */       serializeCompactRDFAttrProps(node, indent + 2);
/*  763 */       write(">");
/*  764 */       writeNewline();
/*  765 */       serializeCompactRDFElementProps(node, indent + 1);
/*  766 */       writeIndent(indent + 1);
/*  767 */       write("</rdf:Description>");
/*  768 */       writeNewline();
/*      */     } 
/*  770 */     return emitEndTag;
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
/*      */   private void serializeCompactRDFGeneralQualifier(int indent, XMPNode node) throws IOException, XMPException {
/*  789 */     write(" rdf:parseType=\"Resource\">");
/*  790 */     writeNewline();
/*      */     
/*  792 */     serializeCanonicalRDFProperty(node, false, true, indent + 1);
/*      */     
/*  794 */     for (Iterator<XMPNode> iq = node.iterateQualifier(); iq.hasNext(); ) {
/*      */       
/*  796 */       XMPNode qualifier = iq.next();
/*  797 */       serializeCanonicalRDFProperty(qualifier, false, false, indent + 1);
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
/*      */   private void serializeCanonicalRDFSchema(XMPNode schemaNode, int level) throws IOException, XMPException {
/*  834 */     for (Iterator<XMPNode> it = schemaNode.iterateChildren(); it.hasNext(); ) {
/*      */       
/*  836 */       XMPNode propNode = it.next();
/*  837 */       serializeCanonicalRDFProperty(propNode, this.options.getUseCanonicalFormat(), false, level + 2);
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
/*      */   private void declareUsedNamespaces(XMPNode node, Set<String> usedPrefixes, int indent) throws IOException {
/*  853 */     if (node.getOptions().isSchemaNode()) {
/*      */ 
/*      */       
/*  856 */       String prefix = node.getValue().substring(0, node.getValue().length() - 1);
/*  857 */       declareNamespace(prefix, node.getName(), usedPrefixes, indent);
/*      */     }
/*  859 */     else if (node.getOptions().isStruct()) {
/*      */       
/*  861 */       for (Iterator<XMPNode> iterator = node.iterateChildren(); iterator.hasNext(); ) {
/*      */         
/*  863 */         XMPNode field = iterator.next();
/*  864 */         declareNamespace(field.getName(), null, usedPrefixes, indent);
/*      */       } 
/*      */     } 
/*      */     
/*  868 */     for (Iterator<XMPNode> iterator1 = node.iterateChildren(); iterator1.hasNext(); ) {
/*      */       
/*  870 */       XMPNode child = iterator1.next();
/*  871 */       declareUsedNamespaces(child, usedPrefixes, indent);
/*      */     } 
/*      */     
/*  874 */     for (Iterator<XMPNode> it = node.iterateQualifier(); it.hasNext(); ) {
/*      */       
/*  876 */       XMPNode qualifier = it.next();
/*  877 */       declareNamespace(qualifier.getName(), null, usedPrefixes, indent);
/*  878 */       declareUsedNamespaces(qualifier, usedPrefixes, indent);
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
/*      */   private void declareNamespace(String prefix, String namespace, Set<String> usedPrefixes, int indent) throws IOException {
/*  894 */     if (namespace == null) {
/*      */ 
/*      */       
/*  897 */       QName qname = new QName(prefix);
/*  898 */       if (qname.hasPrefix()) {
/*      */         
/*  900 */         prefix = qname.getPrefix();
/*      */         
/*  902 */         namespace = XMPMetaFactory.getSchemaRegistry().getNamespaceURI(prefix + ":");
/*      */         
/*  904 */         declareNamespace(prefix, namespace, usedPrefixes, indent);
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  912 */     if (!usedPrefixes.contains(prefix)) {
/*      */       
/*  914 */       writeNewline();
/*  915 */       writeIndent(indent);
/*  916 */       write("xmlns:");
/*  917 */       write(prefix);
/*  918 */       write("=\"");
/*  919 */       write(namespace);
/*  920 */       write(34);
/*  921 */       usedPrefixes.add(prefix);
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
/*      */   private void startOuterRDFDescription(XMPNode schemaNode, int level) throws IOException {
/*  934 */     writeIndent(level + 1);
/*  935 */     write("<rdf:Description rdf:about=");
/*  936 */     writeTreeName();
/*      */     
/*  938 */     Set<String> usedPrefixes = new HashSet<>();
/*  939 */     usedPrefixes.add("xml");
/*  940 */     usedPrefixes.add("rdf");
/*      */     
/*  942 */     declareUsedNamespaces(schemaNode, usedPrefixes, level + 3);
/*      */     
/*  944 */     write(62);
/*  945 */     writeNewline();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void endOuterRDFDescription(int level) throws IOException {
/*  954 */     writeIndent(level + 1);
/*  955 */     write("</rdf:Description>");
/*  956 */     writeNewline();
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
/*      */   private void serializeCanonicalRDFProperty(XMPNode node, boolean useCanonicalRDF, boolean emitAsRDFValue, int indent) throws IOException, XMPException {
/* 1013 */     boolean emitEndTag = true;
/* 1014 */     boolean indentEndTag = true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1019 */     String elemName = node.getName();
/* 1020 */     if (emitAsRDFValue) {
/*      */       
/* 1022 */       elemName = "rdf:value";
/*      */     }
/* 1024 */     else if ("[]".equals(elemName)) {
/*      */       
/* 1026 */       elemName = "rdf:li";
/*      */     } 
/*      */     
/* 1029 */     writeIndent(indent);
/* 1030 */     write(60);
/* 1031 */     write(elemName);
/*      */     
/* 1033 */     boolean hasGeneralQualifiers = false;
/* 1034 */     boolean hasRDFResourceQual = false;
/*      */     
/* 1036 */     for (Iterator<XMPNode> it = node.iterateQualifier(); it.hasNext(); ) {
/*      */       
/* 1038 */       XMPNode qualifier = it.next();
/* 1039 */       if (!RDF_ATTR_QUALIFIER.contains(qualifier.getName())) {
/*      */         
/* 1041 */         hasGeneralQualifiers = true;
/*      */         
/*      */         continue;
/*      */       } 
/* 1045 */       hasRDFResourceQual = "rdf:resource".equals(qualifier.getName());
/* 1046 */       if (!emitAsRDFValue) {
/*      */         
/* 1048 */         write(32);
/* 1049 */         write(qualifier.getName());
/* 1050 */         write("=\"");
/* 1051 */         appendNodeValue(qualifier.getValue(), true);
/* 1052 */         write(34);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1059 */     if (hasGeneralQualifiers && !emitAsRDFValue) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1066 */       if (hasRDFResourceQual)
/*      */       {
/* 1068 */         throw new XMPException("Can't mix rdf:resource and general qualifiers", 202);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1074 */       if (useCanonicalRDF) {
/*      */         
/* 1076 */         write(">");
/* 1077 */         writeNewline();
/*      */         
/* 1079 */         indent++;
/* 1080 */         writeIndent(indent);
/* 1081 */         write("<rdf:Description");
/* 1082 */         write(">");
/*      */       }
/*      */       else {
/*      */         
/* 1086 */         write(" rdf:parseType=\"Resource\">");
/*      */       } 
/* 1088 */       writeNewline();
/*      */       
/* 1090 */       serializeCanonicalRDFProperty(node, useCanonicalRDF, true, indent + 1);
/*      */       
/* 1092 */       for (Iterator<XMPNode> iterator = node.iterateQualifier(); iterator.hasNext(); ) {
/*      */         
/* 1094 */         XMPNode qualifier = iterator.next();
/* 1095 */         if (!RDF_ATTR_QUALIFIER.contains(qualifier.getName()))
/*      */         {
/* 1097 */           serializeCanonicalRDFProperty(qualifier, useCanonicalRDF, false, indent + 1);
/*      */         }
/*      */       } 
/*      */       
/* 1101 */       if (useCanonicalRDF)
/*      */       {
/* 1103 */         writeIndent(indent);
/* 1104 */         write("</rdf:Description>");
/* 1105 */         writeNewline();
/* 1106 */         indent--;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1113 */     else if (!node.getOptions().isCompositeProperty()) {
/*      */ 
/*      */ 
/*      */       
/* 1117 */       if (node.getOptions().isURI())
/*      */       {
/* 1119 */         write(" rdf:resource=\"");
/* 1120 */         appendNodeValue(node.getValue(), true);
/* 1121 */         write("\"/>");
/* 1122 */         writeNewline();
/* 1123 */         emitEndTag = false;
/*      */       }
/* 1125 */       else if (node.getValue() == null || "".equals(node.getValue()))
/*      */       {
/* 1127 */         write("/>");
/* 1128 */         writeNewline();
/* 1129 */         emitEndTag = false;
/*      */       }
/*      */       else
/*      */       {
/* 1133 */         write(62);
/* 1134 */         appendNodeValue(node.getValue(), false);
/* 1135 */         indentEndTag = false;
/*      */       }
/*      */     
/* 1138 */     } else if (node.getOptions().isArray()) {
/*      */ 
/*      */       
/* 1141 */       write(62);
/* 1142 */       writeNewline();
/* 1143 */       emitRDFArrayTag(node, true, indent + 1);
/* 1144 */       if (node.getOptions().isArrayAltText())
/*      */       {
/* 1146 */         XMPNodeUtils.normalizeLangArray(node);
/*      */       }
/* 1148 */       for (Iterator<XMPNode> iterator = node.iterateChildren(); iterator.hasNext(); ) {
/*      */         
/* 1150 */         XMPNode child = iterator.next();
/* 1151 */         serializeCanonicalRDFProperty(child, useCanonicalRDF, false, indent + 2);
/*      */       } 
/* 1153 */       emitRDFArrayTag(node, false, indent + 1);
/*      */ 
/*      */     
/*      */     }
/* 1157 */     else if (!hasRDFResourceQual) {
/*      */ 
/*      */       
/* 1160 */       if (!node.hasChildren())
/*      */       {
/*      */ 
/*      */         
/* 1164 */         if (useCanonicalRDF) {
/*      */           
/* 1166 */           write(">");
/* 1167 */           writeNewline();
/* 1168 */           writeIndent(indent + 1);
/* 1169 */           write("<rdf:Description/>");
/*      */         }
/*      */         else {
/*      */           
/* 1173 */           write(" rdf:parseType=\"Resource\"/>");
/* 1174 */           emitEndTag = false;
/*      */         } 
/* 1176 */         writeNewline();
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */         
/* 1182 */         if (useCanonicalRDF) {
/*      */           
/* 1184 */           write(">");
/* 1185 */           writeNewline();
/* 1186 */           indent++;
/* 1187 */           writeIndent(indent);
/* 1188 */           write("<rdf:Description");
/* 1189 */           write(">");
/*      */         }
/*      */         else {
/*      */           
/* 1193 */           write(" rdf:parseType=\"Resource\">");
/*      */         } 
/* 1195 */         writeNewline();
/*      */         
/* 1197 */         for (Iterator<XMPNode> iterator = node.iterateChildren(); iterator.hasNext(); ) {
/*      */           
/* 1199 */           XMPNode child = iterator.next();
/* 1200 */           serializeCanonicalRDFProperty(child, useCanonicalRDF, false, indent + 1);
/*      */         } 
/*      */         
/* 1203 */         if (useCanonicalRDF)
/*      */         {
/* 1205 */           writeIndent(indent);
/* 1206 */           write("</rdf:Description>");
/* 1207 */           writeNewline();
/* 1208 */           indent--;
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1216 */       for (Iterator<XMPNode> iterator = node.iterateChildren(); iterator.hasNext(); ) {
/*      */         
/* 1218 */         XMPNode child = iterator.next();
/* 1219 */         if (!canBeRDFAttrProp(child))
/*      */         {
/* 1221 */           throw new XMPException("Can't mix rdf:resource and complex fields", 202);
/*      */         }
/*      */         
/* 1224 */         writeNewline();
/* 1225 */         writeIndent(indent + 1);
/* 1226 */         write(32);
/* 1227 */         write(child.getName());
/* 1228 */         write("=\"");
/* 1229 */         appendNodeValue(child.getValue(), true);
/* 1230 */         write(34);
/*      */       } 
/* 1232 */       write("/>");
/* 1233 */       writeNewline();
/* 1234 */       emitEndTag = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1239 */     if (emitEndTag) {
/*      */       
/* 1241 */       if (indentEndTag)
/*      */       {
/* 1243 */         writeIndent(indent);
/*      */       }
/* 1245 */       write("</");
/* 1246 */       write(elemName);
/* 1247 */       write(62);
/* 1248 */       writeNewline();
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
/*      */   private void emitRDFArrayTag(XMPNode arrayNode, boolean isStartTag, int indent) throws IOException {
/* 1264 */     if (isStartTag || arrayNode.hasChildren()) {
/*      */       
/* 1266 */       writeIndent(indent);
/* 1267 */       write(isStartTag ? "<rdf:" : "</rdf:");
/*      */       
/* 1269 */       if (arrayNode.getOptions().isArrayAlternate()) {
/*      */         
/* 1271 */         write("Alt");
/*      */       }
/* 1273 */       else if (arrayNode.getOptions().isArrayOrdered()) {
/*      */         
/* 1275 */         write("Seq");
/*      */       }
/*      */       else {
/*      */         
/* 1279 */         write("Bag");
/*      */       } 
/*      */       
/* 1282 */       if (isStartTag && !arrayNode.hasChildren()) {
/*      */         
/* 1284 */         write("/>");
/*      */       }
/*      */       else {
/*      */         
/* 1288 */         write(">");
/*      */       } 
/*      */       
/* 1291 */       writeNewline();
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
/*      */   private void appendNodeValue(String value, boolean forAttribute) throws IOException {
/* 1309 */     if (value == null)
/*      */     {
/* 1311 */       value = "";
/*      */     }
/* 1313 */     write(Utils.escapeXML(value, forAttribute, true));
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
/*      */   private boolean canBeRDFAttrProp(XMPNode node) {
/* 1331 */     return (
/* 1332 */       !node.hasQualifier() && 
/* 1333 */       !node.getOptions().isURI() && 
/* 1334 */       !node.getOptions().isCompositeProperty() && 
/* 1335 */       !node.getOptions().containsOneOf(1073741824) && 
/* 1336 */       !"[]".equals(node.getName()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeIndent(int times) throws IOException {
/* 1347 */     for (int i = this.options.getBaseIndent() + times; i > 0; i--)
/*      */     {
/* 1349 */       this.writer.write(this.options.getIndent());
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
/*      */   private void write(int c) throws IOException {
/* 1361 */     this.writer.write(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void write(String str) throws IOException {
/* 1372 */     this.writer.write(str);
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
/*      */   private void writeChars(int number, char c) throws IOException {
/* 1384 */     for (; number > 0; number--)
/*      */     {
/* 1386 */       this.writer.write(c);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeNewline() throws IOException {
/* 1397 */     this.writer.write(this.options.getNewline());
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/impl/XMPSerializerRDF.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */