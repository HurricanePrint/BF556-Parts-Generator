/*     */ package com.itextpdf.kernel.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
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
/*     */ final class XmlUtils
/*     */ {
/*     */   public static void writeXmlDocToStream(Document xmlReport, OutputStream stream) throws TransformerException {
/*  67 */     TransformerFactory tFactory = TransformerFactory.newInstance();
/*     */     try {
/*  69 */       tFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
/*  70 */       tFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
/*  71 */     } catch (Exception exception) {}
/*  72 */     Transformer transformer = tFactory.newTransformer();
/*  73 */     transformer.setOutputProperty("indent", "yes");
/*  74 */     transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
/*  75 */     DOMSource source = new DOMSource(xmlReport);
/*  76 */     StreamResult result = new StreamResult(stream);
/*  77 */     transformer.transform(source, result);
/*     */   }
/*     */   
/*     */   public static boolean compareXmls(InputStream xml1, InputStream xml2) throws ParserConfigurationException, SAXException, IOException {
/*  81 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*  82 */     dbf.setNamespaceAware(true);
/*  83 */     dbf.setCoalescing(true);
/*  84 */     dbf.setIgnoringElementContentWhitespace(true);
/*  85 */     dbf.setIgnoringComments(true);
/*  86 */     DocumentBuilder db = dbf.newDocumentBuilder();
/*  87 */     db.setEntityResolver(new SafeEmptyEntityResolver());
/*     */     
/*  89 */     Document doc1 = db.parse(xml1);
/*  90 */     doc1.normalizeDocument();
/*     */     
/*  92 */     Document doc2 = db.parse(xml2);
/*  93 */     doc2.normalizeDocument();
/*     */     
/*  95 */     return doc2.isEqualNode(doc1);
/*     */   }
/*     */   
/*     */   public static Document initNewXmlDocument() throws ParserConfigurationException {
/*  99 */     return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
/*     */   }
/*     */   
/*     */   private static class SafeEmptyEntityResolver
/*     */     implements EntityResolver {
/*     */     public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 105 */       return new InputSource(new StringReader(""));
/*     */     }
/*     */     
/*     */     private SafeEmptyEntityResolver() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/utils/XmlUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */