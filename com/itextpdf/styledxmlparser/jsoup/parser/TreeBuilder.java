/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TreeBuilder
/*     */ {
/*     */   CharacterReader reader;
/*     */   Tokeniser tokeniser;
/*     */   protected Document doc;
/*     */   protected ArrayList<Element> stack;
/*     */   protected String baseUri;
/*     */   Token currentToken;
/*     */   ParseErrorList errors;
/*  64 */   private Token.StartTag start = new Token.StartTag();
/*  65 */   private Token.EndTag end = new Token.EndTag();
/*     */   
/*     */   void initialiseParse(String input, String baseUri, ParseErrorList errors) {
/*  68 */     Validate.notNull(input, "String input must not be null");
/*  69 */     Validate.notNull(baseUri, "BaseURI must not be null");
/*     */     
/*  71 */     this.doc = new Document(baseUri);
/*  72 */     this.reader = new CharacterReader(input);
/*  73 */     this.errors = errors;
/*  74 */     this.tokeniser = new Tokeniser(this.reader, errors);
/*  75 */     this.stack = new ArrayList<>(32);
/*  76 */     this.baseUri = baseUri;
/*     */   }
/*     */   
/*     */   Document parse(String input, String baseUri) {
/*  80 */     return parse(input, baseUri, ParseErrorList.noTracking());
/*     */   }
/*     */   
/*     */   Document parse(String input, String baseUri, ParseErrorList errors) {
/*  84 */     initialiseParse(input, baseUri, errors);
/*  85 */     runParser();
/*  86 */     return this.doc;
/*     */   }
/*     */   protected void runParser() {
/*     */     Token token;
/*     */     do {
/*  91 */       token = this.tokeniser.read();
/*  92 */       process(token);
/*  93 */       token.reset();
/*     */     }
/*  95 */     while (token.type != Token.TokenType.EOF);
/*     */   }
/*     */ 
/*     */   
/*     */   abstract boolean process(Token paramToken);
/*     */ 
/*     */   
/*     */   protected boolean processStartTag(String name) {
/* 103 */     if (this.currentToken == this.start) {
/* 104 */       return process((new Token.StartTag()).name(name));
/*     */     }
/* 106 */     return process(((Token.Tag)this.start.reset()).name(name));
/*     */   }
/*     */   
/*     */   public boolean processStartTag(String name, Attributes attrs) {
/* 110 */     if (this.currentToken == this.start) {
/* 111 */       return process((new Token.StartTag()).nameAttr(name, attrs));
/*     */     }
/* 113 */     this.start.reset();
/* 114 */     this.start.nameAttr(name, attrs);
/* 115 */     return process(this.start);
/*     */   }
/*     */   
/*     */   protected boolean processEndTag(String name) {
/* 119 */     if (this.currentToken == this.end) {
/* 120 */       return process((new Token.EndTag()).name(name));
/*     */     }
/* 122 */     return process(((Token.Tag)this.end.reset()).name(name));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Element currentElement() {
/* 127 */     int size = this.stack.size();
/* 128 */     return (size > 0) ? this.stack.get(size - 1) : null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/TreeBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */