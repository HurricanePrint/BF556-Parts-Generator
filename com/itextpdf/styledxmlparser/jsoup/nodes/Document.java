/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
/*     */ import com.itextpdf.styledxmlparser.jsoup.select.Elements;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.util.ArrayList;
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
/*     */ public class Document
/*     */   extends Element
/*     */ {
/*  61 */   private OutputSettings outputSettings = new OutputSettings();
/*  62 */   private QuirksMode quirksMode = QuirksMode.noQuirks;
/*     */ 
/*     */   
/*     */   private String location;
/*     */ 
/*     */   
/*     */   private boolean updateMetaCharset = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public Document(String baseUri) {
/*  73 */     super(Tag.valueOf("#root"), baseUri);
/*  74 */     this.location = baseUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document createShell(String baseUri) {
/*  83 */     Validate.notNull(baseUri);
/*     */     
/*  85 */     Document doc = new Document(baseUri);
/*  86 */     Element html = doc.appendElement("html");
/*  87 */     html.appendElement("head");
/*  88 */     html.appendElement("body");
/*     */     
/*  90 */     return doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String location() {
/*  99 */     return this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element head() {
/* 107 */     return findFirstElementByTagName("head", this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element body() {
/* 115 */     return findFirstElementByTagName("body", this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String title() {
/* 124 */     Element titleEl = getElementsByTag("title").first();
/* 125 */     return (titleEl != null) ? StringUtil.normaliseWhitespace(titleEl.text()).trim() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void title(String title) {
/* 134 */     Validate.notNull(title);
/* 135 */     Element titleEl = getElementsByTag("title").first();
/* 136 */     if (titleEl == null) {
/* 137 */       head().appendElement("title").text(title);
/*     */     } else {
/* 139 */       titleEl.text(title);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element createElement(String tagName) {
/* 149 */     return new Element(Tag.valueOf(tagName), baseUri());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document normalise() {
/* 158 */     Element htmlEl = findFirstElementByTagName("html", this);
/* 159 */     if (htmlEl == null)
/* 160 */       htmlEl = appendElement("html"); 
/* 161 */     if (head() == null)
/* 162 */       htmlEl.prependElement("head"); 
/* 163 */     if (body() == null) {
/* 164 */       htmlEl.appendElement("body");
/*     */     }
/*     */ 
/*     */     
/* 168 */     normaliseTextNodes(head());
/* 169 */     normaliseTextNodes(htmlEl);
/* 170 */     normaliseTextNodes(this);
/*     */     
/* 172 */     normaliseStructure("head", htmlEl);
/* 173 */     normaliseStructure("body", htmlEl);
/*     */     
/* 175 */     ensureMetaCharsetElement();
/*     */     
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void normaliseTextNodes(Element element) {
/* 182 */     List<Node> toMove = new ArrayList<>();
/* 183 */     for (Node node : element.childNodes) {
/* 184 */       if (node instanceof TextNode) {
/* 185 */         TextNode tn = (TextNode)node;
/* 186 */         if (!tn.isBlank()) {
/* 187 */           toMove.add(tn);
/*     */         }
/*     */       } 
/*     */     } 
/* 191 */     for (int i = toMove.size() - 1; i >= 0; i--) {
/* 192 */       Node node = toMove.get(i);
/* 193 */       element.removeChild(node);
/* 194 */       body().prependChild(new TextNode(" ", ""));
/* 195 */       body().prependChild(node);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void normaliseStructure(String tag, Element htmlEl) {
/* 201 */     Elements elements = getElementsByTag(tag);
/* 202 */     Element master = elements.first();
/* 203 */     if (elements.size() > 1) {
/* 204 */       List<Node> toMove = new ArrayList<>();
/* 205 */       for (int i = 1; i < elements.size(); i++) {
/* 206 */         Node dupe = (Node)elements.get(i);
/* 207 */         for (Node node : dupe.childNodes)
/* 208 */           toMove.add(node); 
/* 209 */         dupe.remove();
/*     */       } 
/*     */       
/* 212 */       for (Node dupe : toMove) {
/* 213 */         master.appendChild(dupe);
/*     */       }
/*     */     } 
/* 216 */     if (!master.parent().equals(htmlEl)) {
/* 217 */       htmlEl.appendChild(master);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Element findFirstElementByTagName(String tag, Node node) {
/* 223 */     if (node.nodeName().equals(tag)) {
/* 224 */       return (Element)node;
/*     */     }
/* 226 */     for (Node child : node.childNodes) {
/* 227 */       Element found = findFirstElementByTagName(tag, child);
/* 228 */       if (found != null) {
/* 229 */         return found;
/*     */       }
/*     */     } 
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String outerHtml() {
/* 237 */     return html();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element text(String text) {
/* 247 */     body().text(text);
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String nodeName() {
/* 253 */     return "#document";
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
/*     */   public void charset(Charset charset) {
/* 281 */     updateMetaCharsetElement(true);
/* 282 */     this.outputSettings.charset(charset);
/* 283 */     ensureMetaCharsetElement();
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
/*     */   public Charset charset() {
/* 295 */     return this.outputSettings.charset();
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
/*     */   public void updateMetaCharsetElement(boolean update) {
/* 312 */     this.updateMetaCharset = update;
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
/*     */   public boolean updateMetaCharsetElement() {
/* 324 */     return this.updateMetaCharset;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 329 */     Document clone = (Document)super.clone();
/* 330 */     clone.outputSettings = (OutputSettings)this.outputSettings.clone();
/* 331 */     return clone;
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
/*     */   private void ensureMetaCharsetElement() {
/* 353 */     if (this.updateMetaCharset) {
/* 354 */       OutputSettings.Syntax syntax = outputSettings().syntax();
/*     */       
/* 356 */       if (syntax == OutputSettings.Syntax.html) {
/* 357 */         Element metaCharset = select("meta[charset]").first();
/*     */         
/* 359 */         if (metaCharset != null) {
/* 360 */           metaCharset.attr("charset", charset().displayName());
/*     */         } else {
/* 362 */           Element head = head();
/*     */           
/* 364 */           if (head != null) {
/* 365 */             head.appendElement("meta").attr("charset", charset().displayName());
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 370 */         select("meta[name=charset]").remove();
/* 371 */       } else if (syntax == OutputSettings.Syntax.xml) {
/* 372 */         Node node = childNodes().get(0);
/*     */         
/* 374 */         if (node instanceof XmlDeclaration) {
/* 375 */           XmlDeclaration decl = (XmlDeclaration)node;
/*     */           
/* 377 */           if (decl.name().equals("xml")) {
/* 378 */             decl.attr("encoding", charset().displayName());
/*     */             
/* 380 */             String version = decl.attr("version");
/*     */             
/* 382 */             if (version != null) {
/* 383 */               decl.attr("version", "1.0");
/*     */             }
/*     */           } else {
/* 386 */             decl = new XmlDeclaration("xml", this.baseUri, false);
/* 387 */             decl.attr("version", "1.0");
/* 388 */             decl.attr("encoding", charset().displayName());
/*     */             
/* 390 */             prependChild(decl);
/*     */           } 
/*     */         } else {
/* 393 */           XmlDeclaration decl = new XmlDeclaration("xml", this.baseUri, false);
/* 394 */           decl.attr("version", "1.0");
/* 395 */           decl.attr("encoding", charset().displayName());
/*     */           
/* 397 */           prependChild(decl);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Syntax
/*     */   {
/* 411 */     html, xml; } public static class OutputSettings implements Cloneable { public enum Syntax { html, xml; }
/*     */     
/* 413 */     private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
/* 414 */     private Charset charset = Charset.forName("UTF-8");
/*     */     private CharsetEncoder charsetEncoder;
/*     */     private boolean prettyPrint = true;
/*     */     private boolean outline = false;
/* 418 */     private int indentAmount = 1;
/* 419 */     private Syntax syntax = Syntax.html;
/*     */     
/*     */     public OutputSettings() {
/* 422 */       this.charsetEncoder = this.charset.newEncoder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Entities.EscapeMode escapeMode() {
/* 434 */       return this.escapeMode;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputSettings escapeMode(Entities.EscapeMode escapeMode) {
/* 444 */       this.escapeMode = escapeMode;
/* 445 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Charset charset() {
/* 457 */       return this.charset;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputSettings charset(Charset charset) {
/* 466 */       this.charset = charset;
/* 467 */       this.charsetEncoder = charset.newEncoder();
/* 468 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputSettings charset(String charset) {
/* 477 */       charset(Charset.forName(charset));
/* 478 */       return this;
/*     */     }
/*     */     
/*     */     CharsetEncoder encoder() {
/* 482 */       return this.charsetEncoder;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Syntax syntax() {
/* 490 */       return this.syntax;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputSettings syntax(Syntax syntax) {
/* 500 */       this.syntax = syntax;
/* 501 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean prettyPrint() {
/* 510 */       return this.prettyPrint;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputSettings prettyPrint(boolean pretty) {
/* 519 */       this.prettyPrint = pretty;
/* 520 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean outline() {
/* 529 */       return this.outline;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputSettings outline(boolean outlineMode) {
/* 538 */       this.outline = outlineMode;
/* 539 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indentAmount() {
/* 547 */       return this.indentAmount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputSettings indentAmount(int indentAmount) {
/* 556 */       Validate.isTrue((indentAmount >= 0));
/* 557 */       this.indentAmount = indentAmount;
/* 558 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 564 */       OutputSettings clone = (OutputSettings)partialClone();
/* 565 */       clone.charset(this.charset.name());
/* 566 */       clone.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
/*     */       
/* 568 */       return clone;
/*     */     }
/*     */     
/*     */     private Object partialClone() {
/*     */       try {
/* 573 */         return super.clone();
/* 574 */       } catch (CloneNotSupportedException e) {
/* 575 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputSettings outputSettings() {
/* 585 */     return this.outputSettings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document outputSettings(OutputSettings outputSettings) {
/* 594 */     Validate.notNull(outputSettings);
/* 595 */     this.outputSettings = outputSettings;
/* 596 */     return this;
/*     */   }
/*     */   
/*     */   public enum QuirksMode {
/* 600 */     noQuirks, quirks, limitedQuirks;
/*     */   }
/*     */   
/*     */   public QuirksMode quirksMode() {
/* 604 */     return this.quirksMode;
/*     */   }
/*     */   
/*     */   public Document quirksMode(QuirksMode quirksMode) {
/* 608 */     this.quirksMode = quirksMode;
/* 609 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/Document.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */