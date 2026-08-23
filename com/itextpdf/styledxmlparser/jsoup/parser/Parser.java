/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
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
/*     */ public class Parser
/*     */ {
/*     */   private static final int DEFAULT_MAX_ERRORS = 0;
/*     */   private TreeBuilder treeBuilder;
/*  60 */   private int maxErrors = 0;
/*     */ 
/*     */   
/*     */   private ParseErrorList errors;
/*     */ 
/*     */ 
/*     */   
/*     */   public Parser(TreeBuilder treeBuilder) {
/*  68 */     this.treeBuilder = treeBuilder;
/*     */   }
/*     */   
/*     */   public Document parseInput(String html, String baseUri) {
/*  72 */     this.errors = isTrackErrors() ? ParseErrorList.tracking(this.maxErrors) : ParseErrorList.noTracking();
/*  73 */     return this.treeBuilder.parse(html, baseUri, this.errors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeBuilder getTreeBuilder() {
/*  82 */     return this.treeBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Parser setTreeBuilder(TreeBuilder treeBuilder) {
/*  91 */     this.treeBuilder = treeBuilder;
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTrackErrors() {
/* 100 */     return (this.maxErrors > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Parser setTrackErrors(int maxErrors) {
/* 109 */     this.maxErrors = maxErrors;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ParseError> getErrors() {
/* 118 */     return this.errors;
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
/*     */   public static Document parse(String html, String baseUri) {
/* 131 */     TreeBuilder treeBuilder = new HtmlTreeBuilder();
/* 132 */     return treeBuilder.parse(html, baseUri, ParseErrorList.noTracking());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document parseXml(String xml, String baseUri) {
/* 143 */     TreeBuilder treeBuilder = new XmlTreeBuilder();
/* 144 */     return treeBuilder.parse(xml, baseUri, ParseErrorList.noTracking());
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
/*     */   public static List<Node> parseFragment(String fragmentHtml, Element context, String baseUri) {
/* 158 */     HtmlTreeBuilder treeBuilder = new HtmlTreeBuilder();
/* 159 */     return treeBuilder.parseFragment(fragmentHtml, context, baseUri, ParseErrorList.noTracking());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Node> parseXmlFragment(String fragmentXml, String baseUri) {
/* 170 */     XmlTreeBuilder treeBuilder = new XmlTreeBuilder();
/* 171 */     return treeBuilder.parseFragment(fragmentXml, baseUri, ParseErrorList.noTracking());
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
/*     */   public static Document parseBodyFragment(String bodyHtml, String baseUri) {
/* 183 */     Document doc = Document.createShell(baseUri);
/* 184 */     Element body = doc.body();
/* 185 */     List<Node> nodeList = parseFragment(bodyHtml, body, baseUri);
/* 186 */     Node[] nodes = nodeList.<Node>toArray(new Node[nodeList.size()]);
/* 187 */     for (int i = nodes.length - 1; i > 0; i--) {
/* 188 */       nodes[i].remove();
/*     */     }
/* 190 */     for (Node node : nodes) {
/* 191 */       body.appendChild(node);
/*     */     }
/* 193 */     return doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String unescapeEntities(String string, boolean inAttribute) {
/* 203 */     Tokeniser tokeniser = new Tokeniser(new CharacterReader(string), ParseErrorList.noTracking());
/* 204 */     return tokeniser.unescapeEntities(inAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document parseBodyFragmentRelaxed(String bodyHtml, String baseUri) {
/* 215 */     return parse(bodyHtml, baseUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Parser htmlParser() {
/* 226 */     return new Parser(new HtmlTreeBuilder());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Parser xmlParser() {
/* 235 */     return new Parser(new XmlTreeBuilder());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/Parser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */