/*     */ package com.itextpdf.kernel.xmp.impl;
/*     */ 
/*     */ import com.itextpdf.kernel.xmp.XMPException;
/*     */ import com.itextpdf.kernel.xmp.XMPMeta;
/*     */ import com.itextpdf.kernel.xmp.options.ParseOptions;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ProcessingInstruction;
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
/*     */ public class XMPMetaParser
/*     */ {
/*  70 */   private static final Object XMP_RDF = new Object();
/*     */   
/*  72 */   private static DocumentBuilderFactory factory = createDocumentBuilderFactory();
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
/*     */   public static XMPMeta parse(Object input, ParseOptions options) throws XMPException {
/*  96 */     ParameterAsserts.assertNotNull(input);
/*  97 */     options = (options != null) ? options : new ParseOptions();
/*     */     
/*  99 */     Document document = parseXml(input, options);
/*     */     
/* 101 */     boolean xmpmetaRequired = options.getRequireXMPMeta();
/* 102 */     Object[] result = new Object[3];
/* 103 */     result = findRootNode(document, xmpmetaRequired, result);
/*     */     
/* 105 */     if (result != null && result[1] == XMP_RDF) {
/*     */       
/* 107 */       XMPMetaImpl xmp = ParseRDF.parse((Node)result[0]);
/* 108 */       xmp.setPacketHeader((String)result[2]);
/*     */ 
/*     */       
/* 111 */       if (!options.getOmitNormalization())
/*     */       {
/* 113 */         return XMPNormalizer.process(xmp, options);
/*     */       }
/*     */ 
/*     */       
/* 117 */       return xmp;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     return new XMPMetaImpl();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Document parseXml(Object input, ParseOptions options) throws XMPException {
/* 148 */     if (input instanceof InputStream)
/*     */     {
/* 150 */       return parseXmlFromInputStream((InputStream)input, options);
/*     */     }
/* 152 */     if (input instanceof byte[])
/*     */     {
/* 154 */       return parseXmlFromBytebuffer(new ByteBuffer((byte[])input), options);
/*     */     }
/*     */ 
/*     */     
/* 158 */     return parseXmlFromString((String)input, options);
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
/*     */   
/*     */   private static Document parseXmlFromInputStream(InputStream stream, ParseOptions options) throws XMPException {
/* 175 */     if (!options.getAcceptLatin1() && !options.getFixControlChars())
/*     */     {
/* 177 */       return parseInputSource(new InputSource(stream));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 184 */       ByteBuffer buffer = new ByteBuffer(stream);
/* 185 */       return parseXmlFromBytebuffer(buffer, options);
/*     */     }
/* 187 */     catch (IOException e) {
/*     */       
/* 189 */       throw new XMPException("Error reading the XML-file", 204, e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Document parseXmlFromBytebuffer(ByteBuffer buffer, ParseOptions options) throws XMPException {
/* 208 */     InputSource source = new InputSource(buffer.getByteStream());
/*     */     
/*     */     try {
/* 211 */       return parseInputSource(source);
/*     */     }
/* 213 */     catch (XMPException e) {
/*     */       
/* 215 */       if (e.getErrorCode() == 201 || e
/* 216 */         .getErrorCode() == 204) {
/*     */         
/* 218 */         if (options.getAcceptLatin1())
/*     */         {
/* 220 */           buffer = Latin1Converter.convert(buffer);
/*     */         }
/*     */         
/* 223 */         if (options.getFixControlChars()) {
/*     */           
/*     */           try {
/*     */             
/* 227 */             String encoding = buffer.getEncoding();
/*     */ 
/*     */             
/* 230 */             Reader fixReader = new FixASCIIControlsReader(new InputStreamReader(buffer.getByteStream(), encoding));
/* 231 */             return parseInputSource(new InputSource(fixReader));
/*     */           }
/* 233 */           catch (UnsupportedEncodingException e1) {
/*     */ 
/*     */             
/* 236 */             throw new XMPException("Unsupported Encoding", 9, e);
/*     */           } 
/*     */         }
/*     */         
/* 240 */         source = new InputSource(buffer.getByteStream());
/* 241 */         return parseInputSource(source);
/*     */       } 
/*     */ 
/*     */       
/* 245 */       throw e;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Document parseXmlFromString(String input, ParseOptions options) throws XMPException {
/* 263 */     InputSource source = new InputSource(new StringReader(input));
/*     */     
/*     */     try {
/* 266 */       return parseInputSource(source);
/*     */     }
/* 268 */     catch (XMPException e) {
/*     */       
/* 270 */       if (e.getErrorCode() == 201 && options.getFixControlChars()) {
/*     */         
/* 272 */         source = new InputSource(new FixASCIIControlsReader(new StringReader(input)));
/* 273 */         return parseInputSource(source);
/*     */       } 
/*     */ 
/*     */       
/* 277 */       throw e;
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
/*     */ 
/*     */   
/*     */   private static Document parseInputSource(InputSource source) throws XMPException {
/*     */     try {
/* 293 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 294 */       builder.setErrorHandler(null);
/* 295 */       builder.setEntityResolver(new SafeEmptyEntityResolver());
/* 296 */       return builder.parse(source);
/*     */     }
/* 298 */     catch (SAXException e) {
/*     */       
/* 300 */       throw new XMPException("XML parsing failure", 201, e);
/*     */     }
/* 302 */     catch (ParserConfigurationException e) {
/*     */       
/* 304 */       throw new XMPException("XML Parser not correctly configured", 0, e);
/*     */     
/*     */     }
/* 307 */     catch (IOException e) {
/*     */       
/* 309 */       throw new XMPException("Error reading the XML-file", 204, e);
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
/*     */   private static Object[] findRootNode(Node root, boolean xmpmetaRequired, Object[] result) {
/* 348 */     NodeList children = root.getChildNodes();
/* 349 */     for (int i = 0; i < children.getLength(); i++) {
/*     */       
/* 351 */       root = children.item(i);
/* 352 */       if (7 == root.getNodeType() && "xpacket"
/* 353 */         .equals(((ProcessingInstruction)root).getTarget())) {
/*     */ 
/*     */ 
/*     */         
/* 357 */         if (result != null)
/*     */         {
/* 359 */           result[2] = ((ProcessingInstruction)root).getData();
/*     */         }
/*     */       }
/* 362 */       else if (3 != root.getNodeType() && 7 != root
/* 363 */         .getNodeType()) {
/*     */         
/* 365 */         String rootNS = root.getNamespaceURI();
/* 366 */         String rootLocal = root.getLocalName();
/* 367 */         if (("xmpmeta"
/*     */           
/* 369 */           .equals(rootLocal) || "xapmeta"
/* 370 */           .equals(rootLocal)) && "adobe:ns:meta/"
/*     */           
/* 372 */           .equals(rootNS))
/*     */         {
/*     */ 
/*     */           
/* 376 */           return findRootNode(root, false, result);
/*     */         }
/* 378 */         if (!xmpmetaRequired && "RDF"
/* 379 */           .equals(rootLocal) && "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
/* 380 */           .equals(rootNS)) {
/*     */           
/* 382 */           if (result != null) {
/*     */             
/* 384 */             result[0] = root;
/* 385 */             result[1] = XMP_RDF;
/*     */           } 
/* 387 */           return result;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 392 */         Object[] newResult = findRootNode(root, xmpmetaRequired, result);
/* 393 */         if (newResult != null)
/*     */         {
/* 395 */           return newResult;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DocumentBuilderFactory createDocumentBuilderFactory() {
/* 417 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 418 */     factory.setNamespaceAware(true);
/* 419 */     factory.setIgnoringComments(true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 425 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*     */     }
/* 427 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 432 */     return factory;
/*     */   }
/*     */   
/*     */   private static class SafeEmptyEntityResolver
/*     */     implements EntityResolver {
/*     */     public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 438 */       return new InputSource(new StringReader(""));
/*     */     }
/*     */     
/*     */     private SafeEmptyEntityResolver() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/impl/XMPMetaParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */