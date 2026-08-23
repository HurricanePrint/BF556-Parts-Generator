/*     */ package com.itextpdf.styledxmlparser.node.impl.jsoup;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.IXmlParser;
/*     */ import com.itextpdf.styledxmlparser.jsoup.Jsoup;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.DataNode;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
/*     */ import com.itextpdf.styledxmlparser.node.IDocumentNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupDataNode;
/*     */ import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupDocumentNode;
/*     */ import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupDocumentTypeNode;
/*     */ import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupTextNode;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class JsoupXmlParser
/*     */   implements IXmlParser
/*     */ {
/*  76 */   private static Logger logger = LoggerFactory.getLogger(JsoupXmlParser.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentNode parse(InputStream xmlStream, String charset) throws IOException {
/*  85 */     String baseUri = "";
/*  86 */     Document doc = Jsoup.parseXML(xmlStream, charset, baseUri);
/*  87 */     INode result = wrapJsoupHierarchy((Node)doc);
/*  88 */     if (result instanceof IDocumentNode) {
/*  89 */       return (IDocumentNode)result;
/*     */     }
/*  91 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentNode parse(String xml) {
/* 100 */     Document doc = Jsoup.parseXML(xml);
/* 101 */     INode result = wrapJsoupHierarchy((Node)doc);
/* 102 */     if (result instanceof IDocumentNode) {
/* 103 */       return (IDocumentNode)result;
/*     */     }
/* 105 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private INode wrapJsoupHierarchy(Node jsoupNode) {
/*     */     JsoupDocumentTypeNode jsoupDocumentTypeNode;
/* 116 */     INode resultNode = null;
/* 117 */     if (jsoupNode instanceof Document) {
/* 118 */       JsoupDocumentNode jsoupDocumentNode = new JsoupDocumentNode((Document)jsoupNode);
/* 119 */     } else if (jsoupNode instanceof TextNode) {
/* 120 */       JsoupTextNode jsoupTextNode = new JsoupTextNode((TextNode)jsoupNode);
/* 121 */     } else if (jsoupNode instanceof Element) {
/* 122 */       JsoupElementNode jsoupElementNode = new JsoupElementNode((Element)jsoupNode);
/* 123 */     } else if (jsoupNode instanceof DataNode) {
/* 124 */       JsoupDataNode jsoupDataNode = new JsoupDataNode((DataNode)jsoupNode);
/* 125 */     } else if (jsoupNode instanceof DocumentType) {
/* 126 */       jsoupDocumentTypeNode = new JsoupDocumentTypeNode((DocumentType)jsoupNode);
/* 127 */     } else if (!(jsoupNode instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Comment) && !(jsoupNode instanceof com.itextpdf.styledxmlparser.jsoup.nodes.XmlDeclaration)) {
/*     */ 
/*     */       
/* 130 */       logger.error(MessageFormatUtil.format("Could not map node type: {0}", new Object[] { jsoupNode.getClass() }));
/*     */     } 
/*     */     
/* 133 */     for (Node node : jsoupNode.childNodes()) {
/* 134 */       INode childNode = wrapJsoupHierarchy(node);
/* 135 */       if (childNode != null) {
/* 136 */         jsoupDocumentTypeNode.addChild(childNode);
/*     */       }
/*     */     } 
/*     */     
/* 140 */     return (INode)jsoupDocumentTypeNode;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/JsoupXmlParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */