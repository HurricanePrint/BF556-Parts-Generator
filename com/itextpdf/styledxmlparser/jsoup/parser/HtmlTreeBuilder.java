/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Comment;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.DataNode;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.FormElement;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
/*     */ import com.itextpdf.styledxmlparser.jsoup.select.Elements;
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
/*     */ public class HtmlTreeBuilder
/*     */   extends TreeBuilder
/*     */ {
/*  64 */   public static final String[] TagsSearchInScope = new String[] { "applet", "caption", "html", "table", "td", "th", "marquee", "object" };
/*  65 */   private static final String[] TagSearchList = new String[] { "ol", "ul" };
/*  66 */   private static final String[] TagSearchButton = new String[] { "button" };
/*  67 */   private static final String[] TagSearchTableScope = new String[] { "html", "table" };
/*  68 */   private static final String[] TagSearchSelectScope = new String[] { "optgroup", "option" };
/*  69 */   private static final String[] TagSearchEndTags = new String[] { "dd", "dt", "li", "option", "optgroup", "p", "rp", "rt" };
/*  70 */   private static final String[] TagSearchSpecial = new String[] { "address", "applet", "area", "article", "aside", "base", "basefont", "bgsound", "blockquote", "body", "br", "button", "caption", "center", "col", "colgroup", "command", "dd", "details", "dir", "div", "dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup", "hr", "html", "iframe", "img", "input", "isindex", "li", "link", "listing", "marquee", "menu", "meta", "nav", "noembed", "noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", "script", "section", "select", "style", "summary", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "title", "tr", "ul", "wbr", "xmp" };
/*     */ 
/*     */   
/*     */   private HtmlTreeBuilderState state;
/*     */ 
/*     */   
/*     */   private HtmlTreeBuilderState originalState;
/*     */   
/*     */   private boolean baseUriSetFromDoc = false;
/*     */   
/*     */   private Element headElement;
/*     */   
/*     */   private FormElement formElement;
/*     */   
/*     */   private Element contextElement;
/*     */   
/*  86 */   private ArrayList<Element> formattingElements = new ArrayList<>();
/*  87 */   private List<String> pendingTableCharacters = new ArrayList<>();
/*  88 */   private Token.EndTag emptyEnd = new Token.EndTag();
/*     */   
/*     */   private boolean framesetOk = true;
/*     */   
/*     */   private boolean fosterInserts = false;
/*     */   
/*     */   private boolean fragmentParsing = false;
/*     */   private String[] specificScopeTarget;
/*     */   
/*     */   Document parse(String input, String baseUri, ParseErrorList errors) {
/*  98 */     this.state = HtmlTreeBuilderState.Initial;
/*  99 */     this.baseUriSetFromDoc = false;
/* 100 */     return super.parse(input, baseUri, errors);
/*     */   }
/*     */ 
/*     */   
/*     */   List<Node> parseFragment(String inputFragment, Element context, String baseUri, ParseErrorList errors) {
/* 105 */     this.state = HtmlTreeBuilderState.Initial;
/* 106 */     initialiseParse(inputFragment, baseUri, errors);
/* 107 */     this.contextElement = context;
/* 108 */     this.fragmentParsing = true;
/* 109 */     Element root = null;
/*     */     
/* 111 */     if (context != null) {
/* 112 */       if (context.ownerDocument() != null) {
/* 113 */         this.doc.quirksMode(context.ownerDocument().quirksMode());
/*     */       }
/*     */       
/* 116 */       String contextTag = context.tagName();
/* 117 */       if (StringUtil.in(contextTag, new String[] { "title", "textarea" })) {
/* 118 */         this.tokeniser.transition(TokeniserState.Rcdata);
/* 119 */       } else if (StringUtil.in(contextTag, new String[] { "iframe", "noembed", "noframes", "style", "xmp" })) {
/* 120 */         this.tokeniser.transition(TokeniserState.Rawtext);
/* 121 */       } else if (contextTag.equals("script")) {
/* 122 */         this.tokeniser.transition(TokeniserState.ScriptData);
/* 123 */       } else if (contextTag.equals("noscript")) {
/* 124 */         this.tokeniser.transition(TokeniserState.Data);
/* 125 */       } else if (contextTag.equals("plaintext")) {
/* 126 */         this.tokeniser.transition(TokeniserState.Data);
/*     */       } else {
/* 128 */         this.tokeniser.transition(TokeniserState.Data);
/*     */       } 
/* 130 */       root = new Element(Tag.valueOf("html"), baseUri);
/* 131 */       this.doc.appendChild((Node)root);
/* 132 */       this.stack.add(root);
/* 133 */       resetInsertionMode();
/*     */ 
/*     */ 
/*     */       
/* 137 */       Elements contextChain = context.parents();
/* 138 */       contextChain.add(0, context);
/* 139 */       for (Element parent : contextChain) {
/* 140 */         if (parent instanceof FormElement) {
/* 141 */           this.formElement = (FormElement)parent;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 147 */     runParser();
/* 148 */     if (context != null && root != null) {
/* 149 */       return root.childNodes();
/*     */     }
/* 151 */     return this.doc.childNodes();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean process(Token token) {
/* 156 */     this.currentToken = token;
/* 157 */     return this.state.process(token, this);
/*     */   }
/*     */   
/*     */   boolean process(Token token, HtmlTreeBuilderState state) {
/* 161 */     this.currentToken = token;
/* 162 */     return state.process(token, this);
/*     */   }
/*     */   
/*     */   void transition(HtmlTreeBuilderState state) {
/* 166 */     this.state = state;
/*     */   }
/*     */   
/*     */   HtmlTreeBuilderState state() {
/* 170 */     return this.state;
/*     */   }
/*     */   
/*     */   void markInsertionMode() {
/* 174 */     this.originalState = this.state;
/*     */   }
/*     */   
/*     */   HtmlTreeBuilderState originalState() {
/* 178 */     return this.originalState;
/*     */   }
/*     */   
/*     */   void framesetOk(boolean framesetOk) {
/* 182 */     this.framesetOk = framesetOk;
/*     */   }
/*     */   
/*     */   boolean framesetOk() {
/* 186 */     return this.framesetOk;
/*     */   }
/*     */   
/*     */   Document getDocument() {
/* 190 */     return this.doc;
/*     */   }
/*     */   
/*     */   String getBaseUri() {
/* 194 */     return this.baseUri;
/*     */   }
/*     */   
/*     */   void maybeSetBaseUri(Element base) {
/* 198 */     if (this.baseUriSetFromDoc) {
/*     */       return;
/*     */     }
/* 201 */     String href = base.absUrl("href");
/* 202 */     if (href.length() != 0) {
/* 203 */       this.baseUri = href;
/* 204 */       this.baseUriSetFromDoc = true;
/* 205 */       this.doc.setBaseUri(href);
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isFragmentParsing() {
/* 210 */     return this.fragmentParsing;
/*     */   }
/*     */   
/*     */   void error(HtmlTreeBuilderState state) {
/* 214 */     if (this.errors.canAddError()) {
/* 215 */       this.errors.add(new ParseError(this.reader.pos(), "Unexpected token [{0}] when in state [{1}]", new Object[] { this.currentToken.tokenType(), state }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   Element insert(Token.StartTag startTag) {
/* 221 */     if (startTag.isSelfClosing()) {
/* 222 */       Element element = insertEmpty(startTag);
/* 223 */       this.stack.add(element);
/* 224 */       this.tokeniser.transition(TokeniserState.Data);
/* 225 */       this.tokeniser.emit(((Token.Tag)this.emptyEnd.reset()).name(element.tagName()));
/* 226 */       return element;
/*     */     } 
/*     */     
/* 229 */     Element el = new Element(Tag.valueOf(startTag.name()), this.baseUri, startTag.attributes);
/* 230 */     insert(el);
/* 231 */     return el;
/*     */   }
/*     */   
/*     */   Element insertStartTag(String startTagName) {
/* 235 */     Element el = new Element(Tag.valueOf(startTagName), this.baseUri);
/* 236 */     insert(el);
/* 237 */     return el;
/*     */   }
/*     */   
/*     */   void insert(Element el) {
/* 241 */     insertNode((Node)el);
/* 242 */     this.stack.add(el);
/*     */   }
/*     */   
/*     */   Element insertEmpty(Token.StartTag startTag) {
/* 246 */     Tag tag = Tag.valueOf(startTag.name());
/* 247 */     Element el = new Element(tag, this.baseUri, startTag.attributes);
/* 248 */     insertNode((Node)el);
/* 249 */     if (startTag.isSelfClosing()) {
/* 250 */       if (tag.isKnownTag()) {
/* 251 */         if (tag.isSelfClosing()) this.tokeniser.acknowledgeSelfClosingFlag();
/*     */       
/*     */       } else {
/* 254 */         tag.setSelfClosing();
/* 255 */         this.tokeniser.acknowledgeSelfClosingFlag();
/*     */       } 
/*     */     }
/* 258 */     return el;
/*     */   }
/*     */   
/*     */   FormElement insertForm(Token.StartTag startTag, boolean onStack) {
/* 262 */     Tag tag = Tag.valueOf(startTag.name());
/* 263 */     FormElement el = new FormElement(tag, this.baseUri, startTag.attributes);
/* 264 */     setFormElement(el);
/* 265 */     insertNode((Node)el);
/* 266 */     if (onStack)
/* 267 */       this.stack.add(el); 
/* 268 */     return el;
/*     */   }
/*     */   
/*     */   void insert(Token.Comment commentToken) {
/* 272 */     Comment comment = new Comment(commentToken.getData(), this.baseUri);
/* 273 */     insertNode((Node)comment);
/*     */   }
/*     */ 
/*     */   
/*     */   void insert(Token.Character characterToken) {
/*     */     TextNode textNode;
/* 279 */     String tagName = currentElement().tagName();
/* 280 */     if (tagName.equals("script") || tagName.equals("style")) {
/* 281 */       DataNode dataNode = new DataNode(characterToken.getData(), this.baseUri);
/*     */     } else {
/* 283 */       textNode = new TextNode(characterToken.getData(), this.baseUri);
/* 284 */     }  currentElement().appendChild((Node)textNode);
/*     */   }
/*     */ 
/*     */   
/*     */   private void insertNode(Node node) {
/* 289 */     if (this.stack.size() == 0) {
/* 290 */       this.doc.appendChild(node);
/* 291 */     } else if (isFosterInserts()) {
/* 292 */       insertInFosterParent(node);
/*     */     } else {
/* 294 */       currentElement().appendChild(node);
/*     */     } 
/*     */     
/* 297 */     if (node instanceof Element && ((Element)node).tag().isFormListed() && 
/* 298 */       this.formElement != null) {
/* 299 */       this.formElement.addElement((Element)node);
/*     */     }
/*     */   }
/*     */   
/*     */   Element pop() {
/* 304 */     int size = this.stack.size();
/* 305 */     return this.stack.remove(size - 1);
/*     */   }
/*     */   
/*     */   void push(Element element) {
/* 309 */     this.stack.add(element);
/*     */   }
/*     */   
/*     */   ArrayList<Element> getStack() {
/* 313 */     return this.stack;
/*     */   }
/*     */   
/*     */   boolean onStack(Element el) {
/* 317 */     return isElementInQueue(this.stack, el);
/*     */   }
/*     */   
/*     */   private boolean isElementInQueue(ArrayList<Element> queue, Element element) {
/* 321 */     for (int pos = queue.size() - 1; pos >= 0; pos--) {
/* 322 */       Element next = queue.get(pos);
/* 323 */       if (next == element) {
/* 324 */         return true;
/*     */       }
/*     */     } 
/* 327 */     return false;
/*     */   }
/*     */   
/*     */   Element getFromStack(String elName) {
/* 331 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 332 */       Element next = this.stack.get(pos);
/* 333 */       if (next.nodeName().equals(elName)) {
/* 334 */         return next;
/*     */       }
/*     */     } 
/* 337 */     return null;
/*     */   }
/*     */   
/*     */   boolean removeFromStack(Element el) {
/* 341 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 342 */       Element next = this.stack.get(pos);
/* 343 */       if (next == el) {
/* 344 */         this.stack.remove(pos);
/* 345 */         return true;
/*     */       } 
/*     */     } 
/* 348 */     return false;
/*     */   }
/*     */   
/*     */   void popStackToClose(String elName) {
/* 352 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 353 */       Element next = this.stack.get(pos);
/* 354 */       this.stack.remove(pos);
/* 355 */       if (next.nodeName().equals(elName))
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   
/*     */   void popStackToClose(String... elNames) {
/* 361 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 362 */       Element next = this.stack.get(pos);
/* 363 */       this.stack.remove(pos);
/* 364 */       if (StringUtil.in(next.nodeName(), elNames))
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   
/*     */   void popStackToBefore(String elName) {
/* 370 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 371 */       Element next = this.stack.get(pos);
/* 372 */       if (next.nodeName().equals(elName)) {
/*     */         break;
/*     */       }
/* 375 */       this.stack.remove(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void clearStackToTableContext() {
/* 381 */     clearStackToContext(new String[] { "table" });
/*     */   }
/*     */   
/*     */   void clearStackToTableBodyContext() {
/* 385 */     clearStackToContext(new String[] { "tbody", "tfoot", "thead" });
/*     */   }
/*     */   
/*     */   void clearStackToTableRowContext() {
/* 389 */     clearStackToContext(new String[] { "tr" });
/*     */   }
/*     */   
/*     */   private void clearStackToContext(String... nodeNames) {
/* 393 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 394 */       Element next = this.stack.get(pos);
/* 395 */       if (StringUtil.in(next.nodeName(), nodeNames) || next.nodeName().equals("html")) {
/*     */         break;
/*     */       }
/* 398 */       this.stack.remove(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   Element aboveOnStack(Element el) {
/* 403 */     assert onStack(el);
/* 404 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 405 */       Element next = this.stack.get(pos);
/* 406 */       if (next == el) {
/* 407 */         return this.stack.get(pos - 1);
/*     */       }
/*     */     } 
/* 410 */     return null;
/*     */   }
/*     */   
/*     */   void insertOnStackAfter(Element after, Element in) {
/* 414 */     int i = this.stack.lastIndexOf(after);
/* 415 */     Validate.isTrue((i != -1));
/* 416 */     this.stack.add(i + 1, in);
/*     */   }
/*     */   
/*     */   void replaceOnStack(Element out, Element in) {
/* 420 */     replaceInQueue(this.stack, out, in);
/*     */   }
/*     */   
/*     */   private void replaceInQueue(ArrayList<Element> queue, Element out, Element in) {
/* 424 */     int i = queue.lastIndexOf(out);
/* 425 */     Validate.isTrue((i != -1));
/* 426 */     queue.set(i, in);
/*     */   }
/*     */   
/*     */   void resetInsertionMode() {
/* 430 */     boolean last = false;
/* 431 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 432 */       Element node = this.stack.get(pos);
/* 433 */       if (pos == 0) {
/* 434 */         last = true;
/* 435 */         node = this.contextElement;
/*     */       } 
/* 437 */       String name = node.nodeName();
/* 438 */       if ("select".equals(name)) {
/* 439 */         transition(HtmlTreeBuilderState.InSelect); break;
/*     */       } 
/* 441 */       if ("td".equals(name) || ("th".equals(name) && !last)) {
/* 442 */         transition(HtmlTreeBuilderState.InCell); break;
/*     */       } 
/* 444 */       if ("tr".equals(name)) {
/* 445 */         transition(HtmlTreeBuilderState.InRow); break;
/*     */       } 
/* 447 */       if ("tbody".equals(name) || "thead".equals(name) || "tfoot".equals(name)) {
/* 448 */         transition(HtmlTreeBuilderState.InTableBody); break;
/*     */       } 
/* 450 */       if ("caption".equals(name)) {
/* 451 */         transition(HtmlTreeBuilderState.InCaption); break;
/*     */       } 
/* 453 */       if ("colgroup".equals(name)) {
/* 454 */         transition(HtmlTreeBuilderState.InColumnGroup); break;
/*     */       } 
/* 456 */       if ("table".equals(name)) {
/* 457 */         transition(HtmlTreeBuilderState.InTable); break;
/*     */       } 
/* 459 */       if ("head".equals(name)) {
/* 460 */         transition(HtmlTreeBuilderState.InBody); break;
/*     */       } 
/* 462 */       if ("body".equals(name)) {
/* 463 */         transition(HtmlTreeBuilderState.InBody); break;
/*     */       } 
/* 465 */       if ("frameset".equals(name)) {
/* 466 */         transition(HtmlTreeBuilderState.InFrameset); break;
/*     */       } 
/* 468 */       if ("html".equals(name)) {
/* 469 */         transition(HtmlTreeBuilderState.BeforeHead); break;
/*     */       } 
/* 471 */       if (last) {
/* 472 */         transition(HtmlTreeBuilderState.InBody);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   HtmlTreeBuilder() {
/* 479 */     this.specificScopeTarget = new String[] { null };
/*     */   }
/*     */   private boolean inSpecificScope(String targetName, String[] baseTypes, String[] extraTypes) {
/* 482 */     this.specificScopeTarget[0] = targetName;
/* 483 */     return inSpecificScope(this.specificScopeTarget, baseTypes, extraTypes);
/*     */   }
/*     */   
/*     */   private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
/* 487 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 488 */       Element el = this.stack.get(pos);
/* 489 */       String elName = el.nodeName();
/* 490 */       if (StringUtil.in(elName, targetNames))
/* 491 */         return true; 
/* 492 */       if (StringUtil.in(elName, baseTypes))
/* 493 */         return false; 
/* 494 */       if (extraTypes != null && StringUtil.in(elName, extraTypes))
/* 495 */         return false; 
/*     */     } 
/* 497 */     Validate.fail("Should not be reachable");
/* 498 */     return false;
/*     */   }
/*     */   
/*     */   boolean inScope(String[] targetNames) {
/* 502 */     return inSpecificScope(targetNames, TagsSearchInScope, (String[])null);
/*     */   }
/*     */   
/*     */   boolean inScope(String targetName) {
/* 506 */     return inScope(targetName, (String[])null);
/*     */   }
/*     */   
/*     */   boolean inScope(String targetName, String[] extras) {
/* 510 */     return inSpecificScope(targetName, TagsSearchInScope, extras);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean inListItemScope(String targetName) {
/* 516 */     return inScope(targetName, TagSearchList);
/*     */   }
/*     */   
/*     */   boolean inButtonScope(String targetName) {
/* 520 */     return inScope(targetName, TagSearchButton);
/*     */   }
/*     */   
/*     */   boolean inTableScope(String targetName) {
/* 524 */     return inSpecificScope(targetName, TagSearchTableScope, (String[])null);
/*     */   }
/*     */   
/*     */   boolean inSelectScope(String targetName) {
/* 528 */     for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
/* 529 */       Element el = this.stack.get(pos);
/* 530 */       String elName = el.nodeName();
/* 531 */       if (elName.equals(targetName))
/* 532 */         return true; 
/* 533 */       if (!StringUtil.in(elName, TagSearchSelectScope))
/* 534 */         return false; 
/*     */     } 
/* 536 */     Validate.fail("Should not be reachable");
/* 537 */     return false;
/*     */   }
/*     */   
/*     */   void setHeadElement(Element headElement) {
/* 541 */     this.headElement = headElement;
/*     */   }
/*     */   
/*     */   Element getHeadElement() {
/* 545 */     return this.headElement;
/*     */   }
/*     */   
/*     */   boolean isFosterInserts() {
/* 549 */     return this.fosterInserts;
/*     */   }
/*     */   
/*     */   void setFosterInserts(boolean fosterInserts) {
/* 553 */     this.fosterInserts = fosterInserts;
/*     */   }
/*     */   
/*     */   FormElement getFormElement() {
/* 557 */     return this.formElement;
/*     */   }
/*     */   
/*     */   void setFormElement(FormElement formElement) {
/* 561 */     this.formElement = formElement;
/*     */   }
/*     */   
/*     */   void newPendingTableCharacters() {
/* 565 */     this.pendingTableCharacters = new ArrayList<>();
/*     */   }
/*     */   
/*     */   List<String> getPendingTableCharacters() {
/* 569 */     return this.pendingTableCharacters;
/*     */   }
/*     */   
/*     */   void setPendingTableCharacters(List<String> pendingTableCharacters) {
/* 573 */     this.pendingTableCharacters = pendingTableCharacters;
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
/*     */   void generateImpliedEndTags(String excludeTag) {
/* 586 */     while (excludeTag != null && !currentElement().nodeName().equals(excludeTag) && 
/* 587 */       StringUtil.in(currentElement().nodeName(), TagSearchEndTags))
/* 588 */       pop(); 
/*     */   }
/*     */   
/*     */   void generateImpliedEndTags() {
/* 592 */     generateImpliedEndTags((String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSpecial(Element el) {
/* 598 */     String name = el.nodeName();
/* 599 */     return StringUtil.in(name, TagSearchSpecial);
/*     */   }
/*     */   
/*     */   Element lastFormattingElement() {
/* 603 */     return (this.formattingElements.size() > 0) ? this.formattingElements.get(this.formattingElements.size() - 1) : null;
/*     */   }
/*     */   
/*     */   Element removeLastFormattingElement() {
/* 607 */     int size = this.formattingElements.size();
/* 608 */     if (size > 0) {
/* 609 */       return this.formattingElements.remove(size - 1);
/*     */     }
/* 611 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   void pushActiveFormattingElements(Element in) {
/* 616 */     int numSeen = 0;
/* 617 */     for (int pos = this.formattingElements.size() - 1; pos >= 0; pos--) {
/* 618 */       Element el = this.formattingElements.get(pos);
/* 619 */       if (el == null) {
/*     */         break;
/*     */       }
/* 622 */       if (isSameFormattingElement(in, el)) {
/* 623 */         numSeen++;
/*     */       }
/* 625 */       if (numSeen == 3) {
/* 626 */         this.formattingElements.remove(pos);
/*     */         break;
/*     */       } 
/*     */     } 
/* 630 */     this.formattingElements.add(in);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSameFormattingElement(Element a, Element b) {
/* 635 */     return (a.nodeName().equals(b.nodeName()) && a
/*     */       
/* 637 */       .attributes().equals(b.attributes()));
/*     */   }
/*     */ 
/*     */   
/*     */   void reconstructFormattingElements() {
/* 642 */     Element last = lastFormattingElement();
/* 643 */     if (last == null || onStack(last)) {
/*     */       return;
/*     */     }
/* 646 */     Element entry = last;
/* 647 */     int size = this.formattingElements.size();
/* 648 */     int pos = size - 1;
/* 649 */     boolean skip = false;
/*     */     do {
/* 651 */       if (pos == 0) {
/* 652 */         skip = true;
/*     */         break;
/*     */       } 
/* 655 */       entry = this.formattingElements.get(--pos);
/* 656 */     } while (entry != null && !onStack(entry));
/*     */ 
/*     */     
/*     */     do {
/* 660 */       if (!skip)
/* 661 */         entry = this.formattingElements.get(++pos); 
/* 662 */       Validate.notNull(entry);
/*     */ 
/*     */       
/* 665 */       skip = false;
/* 666 */       Element newEl = insertStartTag(entry.nodeName());
/*     */       
/* 668 */       newEl.attributes().addAll(entry.attributes());
/*     */ 
/*     */       
/* 671 */       this.formattingElements.set(pos, newEl);
/*     */     
/*     */     }
/* 674 */     while (pos != size - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void clearFormattingElementsToLastMarker() {
/* 680 */     while (!this.formattingElements.isEmpty()) {
/* 681 */       Element el = removeLastFormattingElement();
/* 682 */       if (el == null)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   
/*     */   void removeFromActiveFormattingElements(Element el) {
/* 688 */     for (int pos = this.formattingElements.size() - 1; pos >= 0; pos--) {
/* 689 */       Element next = this.formattingElements.get(pos);
/* 690 */       if (next == el) {
/* 691 */         this.formattingElements.remove(pos);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isInActiveFormattingElements(Element el) {
/* 698 */     return isElementInQueue(this.formattingElements, el);
/*     */   }
/*     */   
/*     */   Element getActiveFormattingElement(String nodeName) {
/* 702 */     for (int pos = this.formattingElements.size() - 1; pos >= 0; pos--) {
/* 703 */       Element next = this.formattingElements.get(pos);
/* 704 */       if (next == null)
/*     */         break; 
/* 706 */       if (next.nodeName().equals(nodeName))
/* 707 */         return next; 
/*     */     } 
/* 709 */     return null;
/*     */   }
/*     */   
/*     */   void replaceActiveFormattingElement(Element out, Element in) {
/* 713 */     replaceInQueue(this.formattingElements, out, in);
/*     */   }
/*     */   
/*     */   void insertMarkerToFormattingElements() {
/* 717 */     this.formattingElements.add(null);
/*     */   }
/*     */ 
/*     */   
/*     */   void insertInFosterParent(Node in) {
/* 722 */     Element fosterParent, lastTable = getFromStack("table");
/* 723 */     boolean isLastTableParent = false;
/* 724 */     if (lastTable != null)
/* 725 */     { if (lastTable.parent() != null) {
/* 726 */         fosterParent = (Element)lastTable.parent();
/* 727 */         isLastTableParent = true;
/*     */       } else {
/* 729 */         fosterParent = aboveOnStack(lastTable);
/*     */       }  }
/* 731 */     else { fosterParent = this.stack.get(0); }
/*     */ 
/*     */     
/* 734 */     if (isLastTableParent) {
/* 735 */       Validate.notNull(lastTable);
/* 736 */       lastTable.before(in);
/*     */     } else {
/*     */       
/* 739 */       fosterParent.appendChild(in);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 744 */     return "TreeBuilder{currentToken=" + this.currentToken + ", state=" + this.state + ", currentElement=" + 
/*     */ 
/*     */       
/* 747 */       currentElement() + '}';
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/HtmlTreeBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */