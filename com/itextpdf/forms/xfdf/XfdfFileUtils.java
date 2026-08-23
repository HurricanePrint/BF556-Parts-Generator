/*     */ package com.itextpdf.forms.xfdf;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class XfdfFileUtils
/*     */ {
/*     */   static Document createNewXfdfDocument() throws ParserConfigurationException {
/*  74 */     DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
/*  75 */     DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
/*  76 */     documentBuilder.setEntityResolver(new SafeEmptyEntityResolver());
/*  77 */     return documentBuilder.newDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Document createXfdfDocumentFromStream(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
/*  85 */     DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
/*  86 */     DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
/*  87 */     documentBuilder.setEntityResolver(new SafeEmptyEntityResolver());
/*  88 */     return documentBuilder.parse(inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void saveXfdfDocumentToFile(Document document, OutputStream outputStream) throws TransformerException {
/*  97 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*  98 */     transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  99 */     transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
/* 100 */     transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
/* 101 */     Transformer transformer = transformerFactory.newTransformer();
/* 102 */     DOMSource domSource = new DOMSource(document);
/* 103 */     StreamResult streamResult = new StreamResult(outputStream);
/* 104 */     transformer.transform(domSource, streamResult);
/*     */   }
/*     */   
/*     */   private static class SafeEmptyEntityResolver
/*     */     implements EntityResolver {
/*     */     public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 110 */       return new InputSource(new StringReader(""));
/*     */     }
/*     */     
/*     */     private SafeEmptyEntityResolver() {}
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfdf/XfdfFileUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */