/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.Jsoup;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Comment;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.XmlDeclaration;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlTreeBuilder
/*     */   extends TreeBuilder
/*     */ {
/*     */   protected void initialiseParse(String input, String baseUri, ParseErrorList errors) {
/*  68 */     super.initialiseParse(input, baseUri, errors);
/*  69 */     this.stack.add(this.doc);
/*  70 */     this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean process(Token token) {
/*  76 */     switch (token.type) {
/*     */       case StartTag:
/*  78 */         insert(token.asStartTag());
/*     */       
/*     */       case EndTag:
/*  81 */         popStackToClose(token.asEndTag());
/*     */       
/*     */       case Comment:
/*  84 */         insert(token.asComment());
/*     */       
/*     */       case Character:
/*  87 */         insert(token.asCharacter());
/*     */       
/*     */       case Doctype:
/*  90 */         insert(token.asDoctype());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case EOF:
/*  97 */         return true;
/*     */     } 
/*     */     Validate.fail("Unexpected token type: " + token.type);
/*     */   } private void insertNode(Node node) {
/* 101 */     currentElement().appendChild(node);
/*     */   }
/*     */   
/*     */   Element insert(Token.StartTag startTag) {
/* 105 */     Tag tag = Tag.valueOf(startTag.name());
/*     */     
/* 107 */     Element el = new Element(tag, this.baseUri, startTag.attributes);
/* 108 */     insertNode((Node)el);
/* 109 */     if (startTag.isSelfClosing()) {
/* 110 */       this.tokeniser.acknowledgeSelfClosingFlag();
/* 111 */       if (!tag.isKnownTag())
/* 112 */         tag.setSelfClosing(); 
/*     */     } else {
/* 114 */       this.stack.add(el);
/*     */     } 
/* 116 */     return el;
/*     */   }
/*     */   void insert(Token.Comment commentToken) {
/*     */     XmlDeclaration xmlDeclaration;
/* 120 */     Comment comment = new Comment(commentToken.getData(), this.baseUri);
/* 121 */     Comment comment1 = comment;
/* 122 */     if (commentToken.bogus) {
/*     */       
/* 124 */       String data = comment.getData();
/* 125 */       if (data.length() > 1 && (data.startsWith("!") || data.startsWith("?"))) {
/* 126 */         Document doc = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", this.baseUri, Parser.xmlParser());
/* 127 */         Element el = doc.child(0);
/* 128 */         xmlDeclaration = new XmlDeclaration(el.tagName(), comment.baseUri(), data.startsWith("!"));
/* 129 */         xmlDeclaration.attributes().addAll(el.attributes());
/*     */       } 
/*     */     } 
/* 132 */     insertNode((Node)xmlDeclaration);
/*     */   }
/*     */   
/*     */   void insert(Token.Character characterToken) {
/* 136 */     TextNode textNode = new TextNode(characterToken.getData(), this.baseUri);
/* 137 */     insertNode((Node)textNode);
/*     */   }
/*     */   
/*     */   void insert(Token.Doctype d) {
/* 141 */     DocumentType doctypeNode = new DocumentType(d.getName(), d.getPublicIdentifier(), d.getSystemIdentifier(), this.baseUri);
/* 142 */     insertNode((Node)doctypeNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void popStackToClose(Token.EndTag endTag) {
/* 152 */     String elName = endTag.name();
/* 153 */     Element firstFound = null;
/*     */     int pos;
/* 155 */     for (pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 156 */       Element next = this.stack.get(pos);
/* 157 */       if (next.nodeName().equals(elName)) {
/* 158 */         firstFound = next;
/*     */         break;
/*     */       } 
/*     */     } 
/* 162 */     if (firstFound == null) {
/*     */       return;
/*     */     }
/* 165 */     for (pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 166 */       Element next = this.stack.get(pos);
/* 167 */       this.stack.remove(pos);
/* 168 */       if (next == firstFound)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   
/*     */   List<Node> parseFragment(String inputFragment, String baseUri, ParseErrorList errors) {
/* 174 */     initialiseParse(inputFragment, baseUri, errors);
/* 175 */     runParser();
/* 176 */     return this.doc.childNodes();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/XmlTreeBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */