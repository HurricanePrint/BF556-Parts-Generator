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
/*     */ public class JsoupHtmlParser
/*     */   implements IXmlParser
/*     */ {
/*  74 */   private static Logger logger = LoggerFactory.getLogger(JsoupHtmlParser.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentNode parse(InputStream htmlStream, String charset) throws IOException {
/*  83 */     String baseUri = "";
/*  84 */     Document doc = Jsoup.parse(htmlStream, charset, baseUri);
/*  85 */     INode result = wrapJsoupHierarchy((Node)doc);
/*  86 */     if (result instanceof IDocumentNode) {
/*  87 */       return (IDocumentNode)result;
/*     */     }
/*  89 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentNode parse(String html) {
/*  98 */     Document doc = Jsoup.parse(html);
/*  99 */     INode result = wrapJsoupHierarchy((Node)doc);
/* 100 */     if (result instanceof IDocumentNode) {
/* 101 */       return (IDocumentNode)result;
/*     */     }
/* 103 */     throw new IllegalStateException();
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
/* 114 */     INode resultNode = null;
/* 115 */     if (jsoupNode instanceof Document) {
/* 116 */       JsoupDocumentNode jsoupDocumentNode = new JsoupDocumentNode((Document)jsoupNode);
/* 117 */     } else if (jsoupNode instanceof TextNode) {
/* 118 */       JsoupTextNode jsoupTextNode = new JsoupTextNode((TextNode)jsoupNode);
/* 119 */     } else if (jsoupNode instanceof Element) {
/* 120 */       JsoupElementNode jsoupElementNode = new JsoupElementNode((Element)jsoupNode);
/* 121 */     } else if (jsoupNode instanceof DataNode) {
/* 122 */       JsoupDataNode jsoupDataNode = new JsoupDataNode((DataNode)jsoupNode);
/* 123 */     } else if (jsoupNode instanceof DocumentType) {
/* 124 */       jsoupDocumentTypeNode = new JsoupDocumentTypeNode((DocumentType)jsoupNode);
/* 125 */     } else if (!(jsoupNode instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Comment)) {
/*     */       
/* 127 */       logger.error(MessageFormatUtil.format("Could not map node type: {0}", new Object[] { jsoupNode.getClass() }));
/*     */     } 
/*     */     
/* 130 */     for (Node node : jsoupNode.childNodes()) {
/* 131 */       INode childNode = wrapJsoupHierarchy(node);
/* 132 */       if (childNode != null) {
/* 133 */         jsoupDocumentTypeNode.addChild(childNode);
/*     */       }
/*     */     } 
/*     */     
/* 137 */     return (INode)jsoupDocumentTypeNode;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/JsoupHtmlParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */