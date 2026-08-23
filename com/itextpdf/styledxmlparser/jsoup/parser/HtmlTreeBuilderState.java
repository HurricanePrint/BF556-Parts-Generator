/*      */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*      */ 
/*      */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
/*      */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
/*      */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
/*      */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*      */ import com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType;
/*      */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*      */ import com.itextpdf.styledxmlparser.jsoup.nodes.FormElement;
/*      */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ abstract class HtmlTreeBuilderState
/*      */ {
/*   60 */   static HtmlTreeBuilderState Initial = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*   64 */         return "Initial";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/*   68 */         if (HtmlTreeBuilderState.isWhitespace(t))
/*   69 */           return true; 
/*   70 */         if (t.isComment()) {
/*   71 */           tb.insert(t.asComment());
/*   72 */         } else if (t.isDoctype()) {
/*      */ 
/*      */           
/*   75 */           Token.Doctype d = t.asDoctype();
/*   76 */           DocumentType doctype = new DocumentType(d.getName(), d.getPublicIdentifier(), d.getSystemIdentifier(), tb.getBaseUri());
/*   77 */           tb.getDocument().appendChild((Node)doctype);
/*   78 */           if (d.isForceQuirks())
/*   79 */             tb.getDocument().quirksMode(Document.QuirksMode.quirks); 
/*   80 */           tb.transition(BeforeHtml);
/*      */         } else {
/*      */           
/*   83 */           tb.transition(BeforeHtml);
/*   84 */           return tb.process(t);
/*      */         } 
/*   86 */         return true;
/*      */       }
/*      */     };
/*      */   
/*   90 */   static HtmlTreeBuilderState BeforeHtml = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*   94 */         return "BeforeHtml";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/*   98 */         if (t.isDoctype()) {
/*   99 */           tb.error(this);
/*  100 */           return false;
/*  101 */         }  if (t.isComment())
/*  102 */         { tb.insert(t.asComment()); }
/*  103 */         else { if (HtmlTreeBuilderState.isWhitespace(t))
/*  104 */             return true; 
/*  105 */           if (t.isStartTag() && t.asStartTag().name().equals("html"))
/*  106 */           { tb.insert(t.asStartTag());
/*  107 */             tb.transition(BeforeHead); }
/*  108 */           else { if (t.isEndTag() && StringUtil.in(t.asEndTag().name(), new String[] { "head", "body", "html", "br" }))
/*  109 */               return anythingElse(t, tb); 
/*  110 */             if (t.isEndTag()) {
/*  111 */               tb.error(this);
/*  112 */               return false;
/*      */             } 
/*  114 */             return anythingElse(t, tb); }
/*      */            }
/*  116 */          return true;
/*      */       }
/*      */       
/*      */       private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
/*  120 */         tb.insertStartTag("html");
/*  121 */         tb.transition(BeforeHead);
/*  122 */         return tb.process(t);
/*      */       }
/*      */     };
/*      */   
/*  126 */   static HtmlTreeBuilderState BeforeHead = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  130 */         return "BeforeHead";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/*  134 */         if (HtmlTreeBuilderState.isWhitespace(t))
/*  135 */           return true; 
/*  136 */         if (t.isComment())
/*  137 */         { tb.insert(t.asComment()); }
/*  138 */         else { if (t.isDoctype()) {
/*  139 */             tb.error(this);
/*  140 */             return false;
/*  141 */           }  if (t.isStartTag() && t.asStartTag().name().equals("html"))
/*  142 */             return InBody.process(t, tb); 
/*  143 */           if (t.isStartTag() && t.asStartTag().name().equals("head"))
/*  144 */           { Element head = tb.insert(t.asStartTag());
/*  145 */             tb.setHeadElement(head);
/*  146 */             tb.transition(InHead); }
/*  147 */           else { if (t.isEndTag() && StringUtil.in(t.asEndTag().name(), new String[] { "head", "body", "html", "br" })) {
/*  148 */               tb.processStartTag("head");
/*  149 */               return tb.process(t);
/*  150 */             }  if (t.isEndTag()) {
/*  151 */               tb.error(this);
/*  152 */               return false;
/*      */             } 
/*  154 */             tb.processStartTag("head");
/*  155 */             return tb.process(t); }
/*      */            }
/*  157 */          return true;
/*      */       }
/*      */     };
/*      */   
/*  161 */   static HtmlTreeBuilderState InHead = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  165 */         return "InHead";
/*      */       } boolean process(Token t, HtmlTreeBuilder tb) {
/*      */         String name;
/*      */         Token.StartTag start;
/*      */         Token.EndTag end;
/*  170 */         if (HtmlTreeBuilderState.isWhitespace(t)) {
/*  171 */           tb.insert(t.asCharacter());
/*  172 */           return true;
/*      */         } 
/*  174 */         switch (t.type) {
/*      */           case Comment:
/*  176 */             tb.insert(t.asComment());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  231 */             return true;case Doctype: tb.error(this); return false;case StartTag: start = t.asStartTag(); name = start.name(); if (name.equals("html")) return InBody.process(t, tb);  if (StringUtil.in(name, new String[] { "base", "basefont", "bgsound", "command", "link" })) { Element el = tb.insertEmpty(start); if (name.equals("base") && el.hasAttr("href")) tb.maybeSetBaseUri(el);  } else if (name.equals("meta")) { tb.insertEmpty(start); } else if (name.equals("title")) { HtmlTreeBuilderState.handleRcData(start, tb); } else if (StringUtil.in(name, new String[] { "noframes", "style" })) { HtmlTreeBuilderState.handleRawtext(start, tb); } else if (name.equals("noscript")) { tb.insert(start); tb.transition(InHeadNoscript); } else if (name.equals("script")) { tb.tokeniser.transition(TokeniserState.ScriptData); tb.markInsertionMode(); tb.transition(Text); tb.insert(start); } else { if (name.equals("head")) { tb.error(this); return false; }  return anythingElse(t, tb); }  return true;case EndTag: end = t.asEndTag(); name = end.name(); if (name.equals("head")) { tb.pop(); tb.transition(AfterHead); } else { if (StringUtil.in(name, new String[] { "body", "html", "br" })) return anythingElse(t, tb);  tb.error(this); return false; }  return true;
/*      */         } 
/*      */         return anythingElse(t, tb);
/*      */       } private boolean anythingElse(Token t, TreeBuilder tb) {
/*  235 */         tb.processEndTag("head");
/*  236 */         return tb.process(t);
/*      */       }
/*      */     };
/*      */   
/*  240 */   static HtmlTreeBuilderState InHeadNoscript = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  244 */         return "InHeadNoscript";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/*  248 */         if (t.isDoctype())
/*  249 */         { tb.error(this); }
/*  250 */         else { if (t.isStartTag() && t.asStartTag().name().equals("html"))
/*  251 */             return tb.process(t, InBody); 
/*  252 */           if (t.isEndTag() && t.asEndTag().name().equals("noscript"))
/*  253 */           { tb.pop();
/*  254 */             tb.transition(InHead); }
/*  255 */           else { if (HtmlTreeBuilderState.isWhitespace(t) || t.isComment() || (t.isStartTag() && StringUtil.in(t.asStartTag().name(), new String[] { "basefont", "bgsound", "link", "meta", "noframes", "style" })))
/*      */             {
/*  257 */               return tb.process(t, InHead); } 
/*  258 */             if (t.isEndTag() && t.asEndTag().name().equals("br"))
/*  259 */               return anythingElse(t, tb); 
/*  260 */             if ((t.isStartTag() && StringUtil.in(t.asStartTag().name(), new String[] { "head", "noscript" })) || t.isEndTag()) {
/*  261 */               tb.error(this);
/*  262 */               return false;
/*      */             } 
/*  264 */             return anythingElse(t, tb); }
/*      */            }
/*  266 */          return true;
/*      */       }
/*      */       
/*      */       private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
/*  270 */         tb.error(this);
/*  271 */         tb.insert((new Token.Character()).data(t.toString()));
/*  272 */         return true;
/*      */       }
/*      */     };
/*      */   
/*  276 */   static HtmlTreeBuilderState AfterHead = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  280 */         return "AfterHead";
/*      */       }
/*      */ 
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/*  285 */         if (HtmlTreeBuilderState.isWhitespace(t)) {
/*  286 */           tb.insert(t.asCharacter());
/*  287 */         } else if (t.isComment()) {
/*  288 */           tb.insert(t.asComment());
/*  289 */         } else if (t.isDoctype()) {
/*  290 */           tb.error(this);
/*  291 */         } else if (t.isStartTag()) {
/*  292 */           Token.StartTag startTag = t.asStartTag();
/*  293 */           String name = startTag.name();
/*  294 */           if (name.equals("html"))
/*  295 */             return tb.process(t, InBody); 
/*  296 */           if (name.equals("body"))
/*  297 */           { tb.insert(startTag);
/*  298 */             tb.framesetOk(false);
/*  299 */             tb.transition(InBody); }
/*  300 */           else if (name.equals("frameset"))
/*  301 */           { tb.insert(startTag);
/*  302 */             tb.transition(InFrameset); }
/*  303 */           else if (StringUtil.in(name, new String[] { "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "title" }))
/*  304 */           { tb.error(this);
/*  305 */             Element head = tb.getHeadElement();
/*  306 */             tb.push(head);
/*  307 */             tb.process(t, InHead);
/*  308 */             tb.removeFromStack(head); }
/*  309 */           else { if (name.equals("head")) {
/*  310 */               tb.error(this);
/*  311 */               return false;
/*      */             } 
/*  313 */             anythingElse(t, tb); }
/*      */         
/*  315 */         } else if (t.isEndTag()) {
/*  316 */           if (StringUtil.in(t.asEndTag().name(), new String[] { "body", "html" })) {
/*  317 */             anythingElse(t, tb);
/*      */           } else {
/*  319 */             tb.error(this);
/*  320 */             return false;
/*      */           } 
/*      */         } else {
/*  323 */           anythingElse(t, tb);
/*      */         } 
/*  325 */         return true;
/*      */       }
/*      */       
/*      */       private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
/*  329 */         tb.processStartTag("body");
/*  330 */         tb.framesetOk(true);
/*  331 */         return tb.process(t);
/*      */       }
/*      */     };
/*      */   
/*  335 */   static HtmlTreeBuilderState InBody = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  339 */         return "InBody"; } boolean process(Token t, HtmlTreeBuilder tb) {
/*      */         String name;
/*      */         Token.Character c;
/*      */         Token.StartTag startTag;
/*      */         Token.EndTag endTag;
/*  344 */         switch (t.type) {
/*      */           case Character:
/*  346 */             c = t.asCharacter();
/*  347 */             if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
/*      */               
/*  349 */               tb.error(this);
/*  350 */               return false;
/*  351 */             }  if (tb.framesetOk() && HtmlTreeBuilderState.isWhitespace(c)) {
/*  352 */               tb.reconstructFormattingElements();
/*  353 */               tb.insert(c); break;
/*      */             } 
/*  355 */             tb.reconstructFormattingElements();
/*  356 */             tb.insert(c);
/*  357 */             tb.framesetOk(false);
/*      */             break;
/*      */ 
/*      */           
/*      */           case Comment:
/*  362 */             tb.insert(t.asComment());
/*      */             break;
/*      */           
/*      */           case Doctype:
/*  366 */             tb.error(this);
/*  367 */             return false;
/*      */           
/*      */           case StartTag:
/*  370 */             startTag = t.asStartTag();
/*  371 */             name = startTag.name();
/*  372 */             if (name.equals("a")) {
/*  373 */               if (tb.getActiveFormattingElement("a") != null) {
/*  374 */                 tb.error(this);
/*  375 */                 tb.processEndTag("a");
/*      */ 
/*      */                 
/*  378 */                 Element remainingA = tb.getFromStack("a");
/*  379 */                 if (remainingA != null) {
/*  380 */                   tb.removeFromActiveFormattingElements(remainingA);
/*  381 */                   tb.removeFromStack(remainingA);
/*      */                 } 
/*      */               } 
/*  384 */               tb.reconstructFormattingElements();
/*  385 */               Element a = tb.insert(startTag);
/*  386 */               tb.pushActiveFormattingElements(a); break;
/*  387 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartEmptyFormatters)) {
/*  388 */               tb.reconstructFormattingElements();
/*  389 */               tb.insertEmpty(startTag);
/*  390 */               tb.framesetOk(false); break;
/*  391 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartPClosers)) {
/*  392 */               if (tb.inButtonScope("p")) {
/*  393 */                 tb.processEndTag("p");
/*      */               }
/*  395 */               tb.insert(startTag); break;
/*  396 */             }  if (name.equals("span")) {
/*      */               
/*  398 */               tb.reconstructFormattingElements();
/*  399 */               tb.insert(startTag); break;
/*  400 */             }  if (name.equals("li")) {
/*  401 */               tb.framesetOk(false);
/*  402 */               ArrayList<Element> stack = tb.getStack();
/*  403 */               for (int i = stack.size() - 1; i > 0; i--) {
/*  404 */                 Element el = stack.get(i);
/*  405 */                 if (el.nodeName().equals("li")) {
/*  406 */                   tb.processEndTag("li");
/*      */                   break;
/*      */                 } 
/*  409 */                 if (tb.isSpecial(el) && !StringUtil.inSorted(el.nodeName(), HtmlTreeBuilderState.Constants.InBodyStartLiBreakers))
/*      */                   break; 
/*      */               } 
/*  412 */               if (tb.inButtonScope("p")) {
/*  413 */                 tb.processEndTag("p");
/*      */               }
/*  415 */               tb.insert(startTag); break;
/*  416 */             }  if (name.equals("html")) {
/*  417 */               tb.error(this);
/*      */               
/*  419 */               Element html = tb.getStack().get(0);
/*  420 */               for (Attribute attribute : startTag.getAttributes()) {
/*  421 */                 if (!html.hasAttr(attribute.getKey()))
/*  422 */                   html.attributes().put(attribute); 
/*      */               }  break;
/*  424 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartToHead))
/*  425 */               return tb.process(t, InHead); 
/*  426 */             if (name.equals("body")) {
/*  427 */               tb.error(this);
/*  428 */               ArrayList<Element> stack = tb.getStack();
/*  429 */               if (stack.size() == 1 || (stack.size() > 2 && !((Element)stack.get(1)).nodeName().equals("body")))
/*      */               {
/*  431 */                 return false;
/*      */               }
/*  433 */               tb.framesetOk(false);
/*  434 */               Element body = stack.get(1);
/*  435 */               for (Attribute attribute : startTag.getAttributes()) {
/*  436 */                 if (!body.hasAttr(attribute.getKey()))
/*  437 */                   body.attributes().put(attribute); 
/*      */               }  break;
/*      */             } 
/*  440 */             if (name.equals("frameset")) {
/*  441 */               tb.error(this);
/*  442 */               ArrayList<Element> stack = tb.getStack();
/*  443 */               if (stack.size() == 1 || (stack.size() > 2 && !((Element)stack.get(1)).nodeName().equals("body")))
/*      */               {
/*  445 */                 return false; } 
/*  446 */               if (!tb.framesetOk()) {
/*  447 */                 return false;
/*      */               }
/*  449 */               Element second = stack.get(1);
/*  450 */               if (second.parent() != null) {
/*  451 */                 second.remove();
/*      */               }
/*  453 */               while (stack.size() > 1)
/*  454 */                 stack.remove(stack.size() - 1); 
/*  455 */               tb.insert(startTag);
/*  456 */               tb.transition(InFrameset); break;
/*      */             } 
/*  458 */             if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.Headings)) {
/*  459 */               if (tb.inButtonScope("p")) {
/*  460 */                 tb.processEndTag("p");
/*      */               }
/*  462 */               if (StringUtil.inSorted(tb.currentElement().nodeName(), HtmlTreeBuilderState.Constants.Headings)) {
/*  463 */                 tb.error(this);
/*  464 */                 tb.pop();
/*      */               } 
/*  466 */               tb.insert(startTag); break;
/*  467 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartPreListing)) {
/*  468 */               if (tb.inButtonScope("p")) {
/*  469 */                 tb.processEndTag("p");
/*      */               }
/*  471 */               tb.insert(startTag);
/*      */               
/*  473 */               tb.framesetOk(false); break;
/*  474 */             }  if (name.equals("form")) {
/*  475 */               if (tb.getFormElement() != null) {
/*  476 */                 tb.error(this);
/*  477 */                 return false;
/*      */               } 
/*  479 */               if (tb.inButtonScope("p")) {
/*  480 */                 tb.processEndTag("p");
/*      */               }
/*  482 */               tb.insertForm(startTag, true); break;
/*  483 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.DdDt)) {
/*  484 */               tb.framesetOk(false);
/*  485 */               ArrayList<Element> stack = tb.getStack();
/*  486 */               for (int i = stack.size() - 1; i > 0; i--) {
/*  487 */                 Element el = stack.get(i);
/*  488 */                 if (StringUtil.inSorted(el.nodeName(), HtmlTreeBuilderState.Constants.DdDt)) {
/*  489 */                   tb.processEndTag(el.nodeName());
/*      */                   break;
/*      */                 } 
/*  492 */                 if (tb.isSpecial(el) && !StringUtil.inSorted(el.nodeName(), HtmlTreeBuilderState.Constants.InBodyStartLiBreakers))
/*      */                   break; 
/*      */               } 
/*  495 */               if (tb.inButtonScope("p")) {
/*  496 */                 tb.processEndTag("p");
/*      */               }
/*  498 */               tb.insert(startTag); break;
/*  499 */             }  if (name.equals("plaintext")) {
/*  500 */               if (tb.inButtonScope("p")) {
/*  501 */                 tb.processEndTag("p");
/*      */               }
/*  503 */               tb.insert(startTag);
/*  504 */               tb.tokeniser.transition(TokeniserState.PLAINTEXT); break;
/*  505 */             }  if (name.equals("button")) {
/*  506 */               if (tb.inButtonScope("button")) {
/*      */                 
/*  508 */                 tb.error(this);
/*  509 */                 tb.processEndTag("button");
/*  510 */                 tb.process(startTag); break;
/*      */               } 
/*  512 */               tb.reconstructFormattingElements();
/*  513 */               tb.insert(startTag);
/*  514 */               tb.framesetOk(false); break;
/*      */             } 
/*  516 */             if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.Formatters)) {
/*  517 */               tb.reconstructFormattingElements();
/*  518 */               Element el = tb.insert(startTag);
/*  519 */               tb.pushActiveFormattingElements(el); break;
/*  520 */             }  if (name.equals("nobr")) {
/*  521 */               tb.reconstructFormattingElements();
/*  522 */               if (tb.inScope("nobr")) {
/*  523 */                 tb.error(this);
/*  524 */                 tb.processEndTag("nobr");
/*  525 */                 tb.reconstructFormattingElements();
/*      */               } 
/*  527 */               Element el = tb.insert(startTag);
/*  528 */               tb.pushActiveFormattingElements(el); break;
/*  529 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartApplets)) {
/*  530 */               tb.reconstructFormattingElements();
/*  531 */               tb.insert(startTag);
/*  532 */               tb.insertMarkerToFormattingElements();
/*  533 */               tb.framesetOk(false); break;
/*  534 */             }  if (name.equals("table")) {
/*  535 */               if (tb.getDocument().quirksMode() != Document.QuirksMode.quirks && tb.inButtonScope("p")) {
/*  536 */                 tb.processEndTag("p");
/*      */               }
/*  538 */               tb.insert(startTag);
/*  539 */               tb.framesetOk(false);
/*  540 */               tb.transition(InTable); break;
/*  541 */             }  if (name.equals("input")) {
/*  542 */               tb.reconstructFormattingElements();
/*  543 */               Element el = tb.insertEmpty(startTag);
/*  544 */               if (!el.attr("type").equalsIgnoreCase("hidden"))
/*  545 */                 tb.framesetOk(false);  break;
/*  546 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartMedia)) {
/*  547 */               tb.insertEmpty(startTag); break;
/*  548 */             }  if (name.equals("hr")) {
/*  549 */               if (tb.inButtonScope("p")) {
/*  550 */                 tb.processEndTag("p");
/*      */               }
/*  552 */               tb.insertEmpty(startTag);
/*  553 */               tb.framesetOk(false); break;
/*  554 */             }  if (name.equals("image")) {
/*  555 */               if (tb.getFromStack("svg") == null) {
/*  556 */                 return tb.process(startTag.name("img"));
/*      */               }
/*  558 */               tb.insert(startTag); break;
/*  559 */             }  if (name.equals("isindex")) {
/*      */               
/*  561 */               tb.error(this);
/*  562 */               if (tb.getFormElement() != null) {
/*  563 */                 return false;
/*      */               }
/*  565 */               tb.tokeniser.acknowledgeSelfClosingFlag();
/*  566 */               tb.processStartTag("form");
/*  567 */               if (startTag.attributes.hasKey("action")) {
/*  568 */                 FormElement formElement = tb.getFormElement();
/*  569 */                 formElement.attr("action", startTag.attributes.get("action"));
/*      */               } 
/*  571 */               tb.processStartTag("hr");
/*  572 */               tb.processStartTag("label");
/*      */ 
/*      */               
/*  575 */               String prompt = startTag.attributes.hasKey("prompt") ? startTag.attributes.get("prompt") : "This is a searchable index. Enter search keywords: ";
/*      */ 
/*      */               
/*  578 */               tb.process((new Token.Character()).data(prompt));
/*      */ 
/*      */               
/*  581 */               Attributes inputAttribs = new Attributes();
/*  582 */               for (Attribute attr : startTag.attributes) {
/*  583 */                 if (!StringUtil.inSorted(attr.getKey(), HtmlTreeBuilderState.Constants.InBodyStartInputAttribs))
/*  584 */                   inputAttribs.put(attr); 
/*      */               } 
/*  586 */               inputAttribs.put("name", "isindex");
/*  587 */               tb.processStartTag("input", inputAttribs);
/*  588 */               tb.processEndTag("label");
/*  589 */               tb.processStartTag("hr");
/*  590 */               tb.processEndTag("form"); break;
/*  591 */             }  if (name.equals("textarea")) {
/*  592 */               tb.insert(startTag);
/*      */               
/*  594 */               tb.tokeniser.transition(TokeniserState.Rcdata);
/*  595 */               tb.markInsertionMode();
/*  596 */               tb.framesetOk(false);
/*  597 */               tb.transition(Text); break;
/*  598 */             }  if (name.equals("xmp")) {
/*  599 */               if (tb.inButtonScope("p")) {
/*  600 */                 tb.processEndTag("p");
/*      */               }
/*  602 */               tb.reconstructFormattingElements();
/*  603 */               tb.framesetOk(false);
/*  604 */               HtmlTreeBuilderState.handleRawtext(startTag, tb); break;
/*  605 */             }  if (name.equals("iframe")) {
/*  606 */               tb.framesetOk(false);
/*  607 */               HtmlTreeBuilderState.handleRawtext(startTag, tb); break;
/*  608 */             }  if (name.equals("noembed")) {
/*      */               
/*  610 */               HtmlTreeBuilderState.handleRawtext(startTag, tb); break;
/*  611 */             }  if (name.equals("select")) {
/*  612 */               tb.reconstructFormattingElements();
/*  613 */               tb.insert(startTag);
/*  614 */               tb.framesetOk(false);
/*      */               
/*  616 */               HtmlTreeBuilderState state = tb.state();
/*  617 */               if (state.equals(InTable) || state.equals(InCaption) || state.equals(InTableBody) || state.equals(InRow) || state.equals(InCell)) {
/*  618 */                 tb.transition(InSelectInTable); break;
/*      */               } 
/*  620 */               tb.transition(InSelect); break;
/*  621 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartOptions)) {
/*  622 */               if (tb.currentElement().nodeName().equals("option"))
/*  623 */                 tb.processEndTag("option"); 
/*  624 */               tb.reconstructFormattingElements();
/*  625 */               tb.insert(startTag); break;
/*  626 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartRuby)) {
/*  627 */               if (tb.inScope("ruby")) {
/*  628 */                 tb.generateImpliedEndTags();
/*  629 */                 if (!tb.currentElement().nodeName().equals("ruby")) {
/*  630 */                   tb.error(this);
/*  631 */                   tb.popStackToBefore("ruby");
/*      */                 } 
/*  633 */                 tb.insert(startTag);
/*      */               }  break;
/*  635 */             }  if (name.equals("math")) {
/*  636 */               tb.reconstructFormattingElements();
/*      */               
/*  638 */               tb.insert(startTag);
/*  639 */               tb.tokeniser.acknowledgeSelfClosingFlag(); break;
/*  640 */             }  if (name.equals("svg")) {
/*  641 */               tb.reconstructFormattingElements();
/*      */               
/*  643 */               tb.insert(startTag);
/*  644 */               tb.tokeniser.acknowledgeSelfClosingFlag(); break;
/*  645 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartDrop)) {
/*  646 */               tb.error(this);
/*  647 */               return false;
/*      */             } 
/*  649 */             tb.reconstructFormattingElements();
/*  650 */             tb.insert(startTag);
/*      */             break;
/*      */ 
/*      */           
/*      */           case EndTag:
/*  655 */             endTag = t.asEndTag();
/*  656 */             name = endTag.name();
/*  657 */             if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyEndAdoptionFormatters)) {
/*      */               
/*  659 */               for (int i = 0; i < 8; i++) {
/*  660 */                 Element formatEl = tb.getActiveFormattingElement(name);
/*  661 */                 if (formatEl == null)
/*  662 */                   return anyOtherEndTag(t, tb); 
/*  663 */                 if (!tb.onStack(formatEl)) {
/*  664 */                   tb.error(this);
/*  665 */                   tb.removeFromActiveFormattingElements(formatEl);
/*  666 */                   return true;
/*  667 */                 }  if (!tb.inScope(formatEl.nodeName())) {
/*  668 */                   tb.error(this);
/*  669 */                   return false;
/*  670 */                 }  if (tb.currentElement() != formatEl) {
/*  671 */                   tb.error(this);
/*      */                 }
/*  673 */                 Element furthestBlock = null;
/*  674 */                 Element commonAncestor = null;
/*  675 */                 boolean seenFormattingElement = false;
/*  676 */                 ArrayList<Element> stack = tb.getStack();
/*      */ 
/*      */                 
/*  679 */                 int stackSize = stack.size();
/*  680 */                 for (int si = 0; si < stackSize && si < 64; si++) {
/*  681 */                   Element el = stack.get(si);
/*  682 */                   if (el == formatEl) {
/*  683 */                     commonAncestor = stack.get(si - 1);
/*  684 */                     seenFormattingElement = true;
/*  685 */                   } else if (seenFormattingElement && tb.isSpecial(el)) {
/*  686 */                     furthestBlock = el;
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*  690 */                 if (furthestBlock == null) {
/*  691 */                   tb.popStackToClose(formatEl.nodeName());
/*  692 */                   tb.removeFromActiveFormattingElements(formatEl);
/*  693 */                   return true;
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/*  698 */                 Element node = furthestBlock;
/*  699 */                 Element lastNode = furthestBlock;
/*  700 */                 for (int j = 0; j < 3; j++) {
/*  701 */                   if (tb.onStack(node))
/*  702 */                     node = tb.aboveOnStack(node); 
/*  703 */                   if (!tb.isInActiveFormattingElements(node)) {
/*  704 */                     tb.removeFromStack(node);
/*      */                   } else {
/*  706 */                     if (node == formatEl) {
/*      */                       break;
/*      */                     }
/*  709 */                     Element replacement = new Element(Tag.valueOf(node.nodeName()), tb.getBaseUri());
/*  710 */                     tb.replaceActiveFormattingElement(node, replacement);
/*  711 */                     tb.replaceOnStack(node, replacement);
/*  712 */                     node = replacement;
/*      */                     
/*  714 */                     if (lastNode == furthestBlock);
/*      */ 
/*      */ 
/*      */                     
/*  718 */                     if (lastNode.parent() != null)
/*  719 */                       lastNode.remove(); 
/*  720 */                     node.appendChild((Node)lastNode);
/*      */                     
/*  722 */                     lastNode = node;
/*      */                   } 
/*      */                 } 
/*  725 */                 if (StringUtil.inSorted(commonAncestor.nodeName(), HtmlTreeBuilderState.Constants.InBodyEndTableFosters)) {
/*  726 */                   if (lastNode.parent() != null)
/*  727 */                     lastNode.remove(); 
/*  728 */                   tb.insertInFosterParent((Node)lastNode);
/*      */                 } else {
/*  730 */                   if (lastNode.parent() != null)
/*  731 */                     lastNode.remove(); 
/*  732 */                   commonAncestor.appendChild((Node)lastNode);
/*      */                 } 
/*      */                 
/*  735 */                 Element adopter = new Element(formatEl.tag(), tb.getBaseUri());
/*  736 */                 adopter.attributes().addAll(formatEl.attributes());
/*  737 */                 Node[] childNodes = (Node[])furthestBlock.childNodes().toArray((Object[])new Node[furthestBlock.childNodeSize()]);
/*  738 */                 for (Node childNode : childNodes) {
/*  739 */                   adopter.appendChild(childNode);
/*      */                 }
/*  741 */                 furthestBlock.appendChild((Node)adopter);
/*  742 */                 tb.removeFromActiveFormattingElements(formatEl);
/*      */                 
/*  744 */                 tb.removeFromStack(formatEl);
/*  745 */                 tb.insertOnStackAfter(furthestBlock, adopter);
/*      */               }  break;
/*  747 */             }  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyEndClosers)) {
/*  748 */               if (!tb.inScope(name)) {
/*      */                 
/*  750 */                 tb.error(this);
/*  751 */                 return false;
/*      */               } 
/*  753 */               tb.generateImpliedEndTags();
/*  754 */               if (!tb.currentElement().nodeName().equals(name))
/*  755 */                 tb.error(this); 
/*  756 */               tb.popStackToClose(name); break;
/*      */             } 
/*  758 */             if (name.equals("span"))
/*      */             {
/*  760 */               return anyOtherEndTag(t, tb); } 
/*  761 */             if (name.equals("li")) {
/*  762 */               if (!tb.inListItemScope(name)) {
/*  763 */                 tb.error(this);
/*  764 */                 return false;
/*      */               } 
/*  766 */               tb.generateImpliedEndTags(name);
/*  767 */               if (!tb.currentElement().nodeName().equals(name))
/*  768 */                 tb.error(this); 
/*  769 */               tb.popStackToClose(name); break;
/*      */             } 
/*  771 */             if (name.equals("body")) {
/*  772 */               if (!tb.inScope("body")) {
/*  773 */                 tb.error(this);
/*  774 */                 return false;
/*      */               } 
/*      */               
/*  777 */               tb.transition(AfterBody); break;
/*      */             } 
/*  779 */             if (name.equals("html")) {
/*  780 */               boolean notIgnored = tb.processEndTag("body");
/*  781 */               if (notIgnored)
/*  782 */                 return tb.process(endTag);  break;
/*  783 */             }  if (name.equals("form")) {
/*  784 */               FormElement formElement = tb.getFormElement();
/*  785 */               tb.setFormElement((FormElement)null);
/*  786 */               if (formElement == null || !tb.inScope(name)) {
/*  787 */                 tb.error(this);
/*  788 */                 return false;
/*      */               } 
/*  790 */               tb.generateImpliedEndTags();
/*  791 */               if (!tb.currentElement().nodeName().equals(name)) {
/*  792 */                 tb.error(this);
/*      */               }
/*  794 */               tb.removeFromStack((Element)formElement); break;
/*      */             } 
/*  796 */             if (name.equals("p")) {
/*  797 */               if (!tb.inButtonScope(name)) {
/*  798 */                 tb.error(this);
/*  799 */                 tb.processStartTag(name);
/*  800 */                 return tb.process(endTag);
/*      */               } 
/*  802 */               tb.generateImpliedEndTags(name);
/*  803 */               if (!tb.currentElement().nodeName().equals(name))
/*  804 */                 tb.error(this); 
/*  805 */               tb.popStackToClose(name); break;
/*      */             } 
/*  807 */             if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.DdDt)) {
/*  808 */               if (!tb.inScope(name)) {
/*  809 */                 tb.error(this);
/*  810 */                 return false;
/*      */               } 
/*  812 */               tb.generateImpliedEndTags(name);
/*  813 */               if (!tb.currentElement().nodeName().equals(name))
/*  814 */                 tb.error(this); 
/*  815 */               tb.popStackToClose(name); break;
/*      */             } 
/*  817 */             if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.Headings)) {
/*  818 */               if (!tb.inScope(HtmlTreeBuilderState.Constants.Headings)) {
/*  819 */                 tb.error(this);
/*  820 */                 return false;
/*      */               } 
/*  822 */               tb.generateImpliedEndTags(name);
/*  823 */               if (!tb.currentElement().nodeName().equals(name))
/*  824 */                 tb.error(this); 
/*  825 */               tb.popStackToClose(HtmlTreeBuilderState.Constants.Headings); break;
/*      */             } 
/*  827 */             if (name.equals("sarcasm"))
/*      */             {
/*  829 */               return anyOtherEndTag(t, tb); } 
/*  830 */             if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartApplets)) {
/*  831 */               if (!tb.inScope("name")) {
/*  832 */                 if (!tb.inScope(name)) {
/*  833 */                   tb.error(this);
/*  834 */                   return false;
/*      */                 } 
/*  836 */                 tb.generateImpliedEndTags();
/*  837 */                 if (!tb.currentElement().nodeName().equals(name))
/*  838 */                   tb.error(this); 
/*  839 */                 tb.popStackToClose(name);
/*  840 */                 tb.clearFormattingElementsToLastMarker();
/*      */               }  break;
/*  842 */             }  if (name.equals("br")) {
/*  843 */               tb.error(this);
/*  844 */               tb.processStartTag("br");
/*  845 */               return false;
/*      */             } 
/*  847 */             return anyOtherEndTag(t, tb);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  856 */         return true;
/*      */       }
/*      */       
/*      */       boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
/*  860 */         String name = t.asEndTag().name();
/*  861 */         ArrayList<Element> stack = tb.getStack();
/*  862 */         for (int pos = stack.size() - 1; pos >= 0; pos--) {
/*  863 */           Element node = stack.get(pos);
/*  864 */           if (node.nodeName().equals(name)) {
/*  865 */             tb.generateImpliedEndTags(name);
/*  866 */             if (!name.equals(tb.currentElement().nodeName()))
/*  867 */               tb.error(this); 
/*  868 */             tb.popStackToClose(name);
/*      */             break;
/*      */           } 
/*  871 */           if (tb.isSpecial(node)) {
/*  872 */             tb.error(this);
/*  873 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/*  877 */         return true;
/*      */       }
/*      */     };
/*      */   
/*  881 */   static HtmlTreeBuilderState Text = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  885 */         return "Text";
/*      */       }
/*      */ 
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/*  890 */         if (t.isCharacter())
/*  891 */         { tb.insert(t.asCharacter()); }
/*  892 */         else { if (t.isEOF()) {
/*  893 */             tb.error(this);
/*      */             
/*  895 */             tb.pop();
/*  896 */             tb.transition(tb.originalState());
/*  897 */             return tb.process(t);
/*  898 */           }  if (t.isEndTag()) {
/*      */             
/*  900 */             tb.pop();
/*  901 */             tb.transition(tb.originalState());
/*      */           }  }
/*  903 */          return true;
/*      */       }
/*      */     };
/*      */   
/*  907 */   static HtmlTreeBuilderState InTable = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  911 */         return "InTable";
/*      */       }
/*      */ 
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/*  916 */         if (t.isCharacter()) {
/*  917 */           tb.newPendingTableCharacters();
/*  918 */           tb.markInsertionMode();
/*  919 */           tb.transition(InTableText);
/*  920 */           return tb.process(t);
/*  921 */         }  if (t.isComment()) {
/*  922 */           tb.insert(t.asComment());
/*  923 */           return true;
/*  924 */         }  if (t.isDoctype()) {
/*  925 */           tb.error(this);
/*  926 */           return false;
/*  927 */         }  if (t.isStartTag()) {
/*  928 */           Token.StartTag startTag = t.asStartTag();
/*  929 */           String name = startTag.name();
/*  930 */           if (name.equals("caption"))
/*  931 */           { tb.clearStackToTableContext();
/*  932 */             tb.insertMarkerToFormattingElements();
/*  933 */             tb.insert(startTag);
/*  934 */             tb.transition(InCaption); }
/*  935 */           else if (name.equals("colgroup"))
/*  936 */           { tb.clearStackToTableContext();
/*  937 */             tb.insert(startTag);
/*  938 */             tb.transition(InColumnGroup); }
/*  939 */           else { if (name.equals("col")) {
/*  940 */               tb.processStartTag("colgroup");
/*  941 */               return tb.process(t);
/*  942 */             }  if (StringUtil.in(name, new String[] { "tbody", "tfoot", "thead" }))
/*  943 */             { tb.clearStackToTableContext();
/*  944 */               tb.insert(startTag);
/*  945 */               tb.transition(InTableBody); }
/*  946 */             else { if (StringUtil.in(name, new String[] { "td", "th", "tr" })) {
/*  947 */                 tb.processStartTag("tbody");
/*  948 */                 return tb.process(t);
/*  949 */               }  if (name.equals("table"))
/*  950 */               { tb.error(this);
/*  951 */                 boolean processed = tb.processEndTag("table");
/*  952 */                 if (processed)
/*  953 */                   return tb.process(t);  }
/*  954 */               else { if (StringUtil.in(name, new String[] { "style", "script" }))
/*  955 */                   return tb.process(t, InHead); 
/*  956 */                 if (name.equals("input"))
/*  957 */                 { if (!startTag.attributes.get("type").equalsIgnoreCase("hidden")) {
/*  958 */                     return anythingElse(t, tb);
/*      */                   }
/*  960 */                   tb.insertEmpty(startTag); }
/*      */                 
/*  962 */                 else if (name.equals("form"))
/*  963 */                 { tb.error(this);
/*  964 */                   if (tb.getFormElement() != null) {
/*  965 */                     return false;
/*      */                   }
/*  967 */                   tb.insertForm(startTag, false); }
/*      */                 else
/*      */                 
/*  970 */                 { return anythingElse(t, tb); }  }  }
/*      */              }
/*  972 */            return true;
/*  973 */         }  if (t.isEndTag()) {
/*  974 */           Token.EndTag endTag = t.asEndTag();
/*  975 */           String name = endTag.name();
/*      */           
/*  977 */           if (name.equals("table"))
/*  978 */           { if (!tb.inTableScope(name)) {
/*  979 */               tb.error(this);
/*  980 */               return false;
/*      */             } 
/*  982 */             tb.popStackToClose("table");
/*      */             
/*  984 */             tb.resetInsertionMode(); }
/*  985 */           else { if (StringUtil.in(name, new String[] { "body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr" })) {
/*      */               
/*  987 */               tb.error(this);
/*  988 */               return false;
/*      */             } 
/*  990 */             return anythingElse(t, tb); }
/*      */           
/*  992 */           return true;
/*  993 */         }  if (t.isEOF()) {
/*  994 */           if (tb.currentElement().nodeName().equals("html"))
/*  995 */             tb.error(this); 
/*  996 */           return true;
/*      */         } 
/*  998 */         return anythingElse(t, tb);
/*      */       }
/*      */       boolean anythingElse(Token t, HtmlTreeBuilder tb) {
/*      */         boolean processed;
/* 1002 */         tb.error(this);
/*      */         
/* 1004 */         if (StringUtil.in(tb.currentElement().nodeName(), new String[] { "table", "tbody", "tfoot", "thead", "tr" })) {
/* 1005 */           tb.setFosterInserts(true);
/* 1006 */           processed = tb.process(t, InBody);
/* 1007 */           tb.setFosterInserts(false);
/*      */         } else {
/* 1009 */           processed = tb.process(t, InBody);
/*      */         } 
/* 1011 */         return processed;
/*      */       }
/*      */     };
/*      */   
/* 1015 */   static HtmlTreeBuilderState InTableText = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1019 */         return "InTableText";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) { Token.Character c;
/* 1023 */         switch (t.type)
/*      */         { case Character:
/* 1025 */             c = t.asCharacter();
/* 1026 */             if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
/* 1027 */               tb.error(this);
/* 1028 */               return false;
/*      */             } 
/* 1030 */             tb.getPendingTableCharacters().add(c.getData());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1055 */             return true; }  if (tb.getPendingTableCharacters().size() > 0) { for (String character : tb.getPendingTableCharacters()) { if (!HtmlTreeBuilderState.isWhitespace(character)) { tb.error(this); if (StringUtil.in(tb.currentElement().nodeName(), new String[] { "table", "tbody", "tfoot", "thead", "tr" })) { tb.setFosterInserts(true); tb.process((new Token.Character()).data(character), InBody); tb.setFosterInserts(false); continue; }
/*      */                tb.process((new Token.Character()).data(character), InBody); continue; }
/*      */              tb.insert((new Token.Character()).data(character)); }
/*      */            tb.newPendingTableCharacters(); }
/* 1059 */          tb.transition(tb.originalState()); return tb.process(t); } }; static HtmlTreeBuilderState InCaption = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1063 */         return "InCaption";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1067 */         if (t.isEndTag() && t.asEndTag().name().equals("caption"))
/* 1068 */         { Token.EndTag endTag = t.asEndTag();
/* 1069 */           String name = endTag.name();
/* 1070 */           if (!tb.inTableScope(name)) {
/* 1071 */             tb.error(this);
/* 1072 */             return false;
/*      */           } 
/* 1074 */           tb.generateImpliedEndTags();
/* 1075 */           if (!tb.currentElement().nodeName().equals("caption"))
/* 1076 */             tb.error(this); 
/* 1077 */           tb.popStackToClose("caption");
/* 1078 */           tb.clearFormattingElementsToLastMarker();
/* 1079 */           tb.transition(InTable); }
/*      */         
/* 1081 */         else if ((t
/* 1082 */           .isStartTag() && StringUtil.in(t.asStartTag().name(), new String[] { "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr" })) || (t
/*      */           
/* 1084 */           .isEndTag() && t.asEndTag().name().equals("table")))
/*      */         
/* 1086 */         { tb.error(this);
/* 1087 */           boolean processed = tb.processEndTag("caption");
/* 1088 */           if (processed)
/* 1089 */             return tb.process(t);  }
/* 1090 */         else { if (t.isEndTag() && StringUtil.in(t.asEndTag().name(), new String[] { "body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr" })) {
/*      */             
/* 1092 */             tb.error(this);
/* 1093 */             return false;
/*      */           } 
/* 1095 */           return tb.process(t, InBody); }
/*      */         
/* 1097 */         return true;
/*      */       }
/*      */     };
/*      */   
/* 1101 */   static HtmlTreeBuilderState InColumnGroup = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1105 */         return "InColumnGroup";
/*      */       }
/*      */       boolean process(Token t, HtmlTreeBuilder tb) { String name;
/*      */         Token.StartTag startTag;
/*      */         Token.EndTag endTag;
/* 1110 */         if (HtmlTreeBuilderState.isWhitespace(t)) {
/* 1111 */           tb.insert(t.asCharacter());
/* 1112 */           return true;
/*      */         } 
/* 1114 */         switch (t.type) {
/*      */           case Comment:
/* 1116 */             tb.insert(t.asComment());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1153 */             return true;case Doctype: tb.error(this); return true;case StartTag: startTag = t.asStartTag(); name = startTag.name(); if (name.equals("html")) return tb.process(t, InBody);  if (name.equals("col")) { tb.insertEmpty(startTag); } else { return anythingElse(t, tb); }  return true;case EndTag: endTag = t.asEndTag(); name = endTag.name(); if (name.equals("colgroup")) { if (tb.currentElement().nodeName().equals("html")) { tb.error(this); return false; }  tb.pop(); tb.transition(InTable); } else { return anythingElse(t, tb); }  return true;
/*      */           case EOF:
/*      */             if (tb.currentElement().nodeName().equals("html"))
/*      */               return true;  return anythingElse(t, tb);
/* 1157 */         }  return anythingElse(t, tb); } private boolean anythingElse(Token t, TreeBuilder tb) { boolean processed = tb.processEndTag("colgroup");
/* 1158 */         if (processed)
/* 1159 */           return tb.process(t); 
/* 1160 */         return true; }
/*      */     
/*      */     };
/*      */   
/* 1164 */   static HtmlTreeBuilderState InTableBody = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1168 */         return "InTableBody";
/*      */       } boolean process(Token t, HtmlTreeBuilder tb) {
/*      */         String name;
/*      */         Token.StartTag startTag;
/*      */         Token.EndTag endTag;
/* 1173 */         switch (t.type) {
/*      */           case StartTag:
/* 1175 */             startTag = t.asStartTag();
/* 1176 */             name = startTag.name();
/* 1177 */             if (name.equals("tr"))
/* 1178 */             { tb.clearStackToTableBodyContext();
/* 1179 */               tb.insert(startTag);
/* 1180 */               tb.transition(InRow); }
/* 1181 */             else { if (StringUtil.in(name, new String[] { "th", "td" })) {
/* 1182 */                 tb.error(this);
/* 1183 */                 tb.processStartTag("tr");
/* 1184 */                 return tb.process(startTag);
/* 1185 */               }  if (StringUtil.in(name, new String[] { "caption", "col", "colgroup", "tbody", "tfoot", "thead" })) {
/* 1186 */                 return exitTableBody(t, tb);
/*      */               }
/* 1188 */               return anythingElse(t, tb); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1213 */             return true;case EndTag: endTag = t.asEndTag(); name = endTag.name(); if (StringUtil.in(name, new String[] { "tbody", "tfoot", "thead" })) { if (!tb.inTableScope(name)) { tb.error(this); return false; }  tb.clearStackToTableBodyContext(); tb.pop(); tb.transition(InTable); } else { if (name.equals("table")) return exitTableBody(t, tb);  if (StringUtil.in(name, new String[] { "body", "caption", "col", "colgroup", "html", "td", "th", "tr" })) { tb.error(this); return false; }  return anythingElse(t, tb); }  return true;
/*      */         } 
/*      */         return anythingElse(t, tb);
/*      */       } private boolean exitTableBody(Token t, HtmlTreeBuilder tb) {
/* 1217 */         if (!tb.inTableScope("tbody") && !tb.inTableScope("thead") && !tb.inScope("tfoot")) {
/*      */           
/* 1219 */           tb.error(this);
/* 1220 */           return false;
/*      */         } 
/* 1222 */         tb.clearStackToTableBodyContext();
/* 1223 */         tb.processEndTag(tb.currentElement().nodeName());
/* 1224 */         return tb.process(t);
/*      */       }
/*      */       
/*      */       private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
/* 1228 */         return tb.process(t, InTable);
/*      */       }
/*      */     };
/*      */   
/* 1232 */   static HtmlTreeBuilderState InRow = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1236 */         return "InRow";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1240 */         if (t.isStartTag()) {
/* 1241 */           Token.StartTag startTag = t.asStartTag();
/* 1242 */           String name = startTag.name();
/*      */           
/* 1244 */           if (StringUtil.in(name, new String[] { "th", "td" }))
/* 1245 */           { tb.clearStackToTableRowContext();
/* 1246 */             tb.insert(startTag);
/* 1247 */             tb.transition(InCell);
/* 1248 */             tb.insertMarkerToFormattingElements(); }
/* 1249 */           else { if (StringUtil.in(name, new String[] { "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr" })) {
/* 1250 */               return handleMissingTr(t, tb);
/*      */             }
/* 1252 */             return anythingElse(t, tb); }
/*      */         
/* 1254 */         } else if (t.isEndTag()) {
/* 1255 */           Token.EndTag endTag = t.asEndTag();
/* 1256 */           String name = endTag.name();
/*      */           
/* 1258 */           if (name.equals("tr"))
/* 1259 */           { if (!tb.inTableScope(name)) {
/* 1260 */               tb.error(this);
/* 1261 */               return false;
/*      */             } 
/* 1263 */             tb.clearStackToTableRowContext();
/* 1264 */             tb.pop();
/* 1265 */             tb.transition(InTableBody); }
/* 1266 */           else { if (name.equals("table"))
/* 1267 */               return handleMissingTr(t, tb); 
/* 1268 */             if (StringUtil.in(name, new String[] { "tbody", "tfoot", "thead" })) {
/* 1269 */               if (!tb.inTableScope(name)) {
/* 1270 */                 tb.error(this);
/* 1271 */                 return false;
/*      */               } 
/* 1273 */               tb.processEndTag("tr");
/* 1274 */               return tb.process(t);
/* 1275 */             }  if (StringUtil.in(name, new String[] { "body", "caption", "col", "colgroup", "html", "td", "th" })) {
/* 1276 */               tb.error(this);
/* 1277 */               return false;
/*      */             } 
/* 1279 */             return anythingElse(t, tb); }
/*      */         
/*      */         } else {
/* 1282 */           return anythingElse(t, tb);
/*      */         } 
/* 1284 */         return true;
/*      */       }
/*      */       
/*      */       private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
/* 1288 */         return tb.process(t, InTable);
/*      */       }
/*      */       
/*      */       private boolean handleMissingTr(Token t, TreeBuilder tb) {
/* 1292 */         boolean processed = tb.processEndTag("tr");
/* 1293 */         if (processed) {
/* 1294 */           return tb.process(t);
/*      */         }
/* 1296 */         return false;
/*      */       }
/*      */     };
/*      */   
/* 1300 */   static HtmlTreeBuilderState InCell = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1304 */         return "InCell";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1308 */         if (t.isEndTag())
/* 1309 */         { Token.EndTag endTag = t.asEndTag();
/* 1310 */           String name = endTag.name();
/*      */           
/* 1312 */           if (StringUtil.in(name, new String[] { "td", "th" }))
/* 1313 */           { if (!tb.inTableScope(name)) {
/* 1314 */               tb.error(this);
/* 1315 */               tb.transition(InRow);
/* 1316 */               return false;
/*      */             } 
/* 1318 */             tb.generateImpliedEndTags();
/* 1319 */             if (!tb.currentElement().nodeName().equals(name))
/* 1320 */               tb.error(this); 
/* 1321 */             tb.popStackToClose(name);
/* 1322 */             tb.clearFormattingElementsToLastMarker();
/* 1323 */             tb.transition(InRow); }
/* 1324 */           else { if (StringUtil.in(name, new String[] { "body", "caption", "col", "colgroup", "html" })) {
/* 1325 */               tb.error(this);
/* 1326 */               return false;
/* 1327 */             }  if (StringUtil.in(name, new String[] { "table", "tbody", "tfoot", "thead", "tr" })) {
/* 1328 */               if (!tb.inTableScope(name)) {
/* 1329 */                 tb.error(this);
/* 1330 */                 return false;
/*      */               } 
/* 1332 */               closeCell(tb);
/* 1333 */               return tb.process(t);
/*      */             } 
/* 1335 */             return anythingElse(t, tb); }
/*      */            }
/* 1337 */         else { if (t.isStartTag() && 
/* 1338 */             StringUtil.in(t.asStartTag().name(), new String[] { "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr" })) {
/*      */             
/* 1340 */             if (!tb.inTableScope("td") && !tb.inTableScope("th")) {
/* 1341 */               tb.error(this);
/* 1342 */               return false;
/*      */             } 
/* 1344 */             closeCell(tb);
/* 1345 */             return tb.process(t);
/*      */           } 
/* 1347 */           return anythingElse(t, tb); }
/*      */         
/* 1349 */         return true;
/*      */       }
/*      */       
/*      */       private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
/* 1353 */         return tb.process(t, InBody);
/*      */       }
/*      */       
/*      */       private void closeCell(HtmlTreeBuilder tb) {
/* 1357 */         if (tb.inTableScope("td")) {
/* 1358 */           tb.processEndTag("td");
/*      */         } else {
/* 1360 */           tb.processEndTag("th");
/*      */         } 
/*      */       }
/*      */     };
/* 1364 */   static HtmlTreeBuilderState InSelect = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1368 */         return "InSelect"; } boolean process(Token t, HtmlTreeBuilder tb) {
/*      */         String name;
/*      */         Token.Character c;
/*      */         Token.StartTag start;
/*      */         Token.EndTag end;
/* 1373 */         switch (t.type) {
/*      */           case Character:
/* 1375 */             c = t.asCharacter();
/* 1376 */             if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
/* 1377 */               tb.error(this);
/* 1378 */               return false;
/*      */             } 
/* 1380 */             tb.insert(c);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1451 */             return true;case Comment: tb.insert(t.asComment()); return true;case Doctype: tb.error(this); return false;case StartTag: start = t.asStartTag(); name = start.name(); if (name.equals("html")) return tb.process(start, InBody);  if (name.equals("option")) { tb.processEndTag("option"); tb.insert(start); } else if (name.equals("optgroup")) { if (tb.currentElement().nodeName().equals("option")) { tb.processEndTag("option"); } else if (tb.currentElement().nodeName().equals("optgroup")) { tb.processEndTag("optgroup"); }  tb.insert(start); } else { if (name.equals("select")) { tb.error(this); return tb.processEndTag("select"); }  if (StringUtil.in(name, new String[] { "input", "keygen", "textarea" })) { tb.error(this); if (!tb.inSelectScope("select")) return false;  tb.processEndTag("select"); return tb.process(start); }  if (name.equals("script")) return tb.process(t, InHead);  return anythingElse(t, tb); }  return true;case EndTag: end = t.asEndTag(); name = end.name(); if (name.equals("optgroup")) { if (tb.currentElement().nodeName().equals("option") && tb.aboveOnStack(tb.currentElement()) != null && tb.aboveOnStack(tb.currentElement()).nodeName().equals("optgroup")) tb.processEndTag("option");  if (tb.currentElement().nodeName().equals("optgroup")) { tb.pop(); } else { tb.error(this); }  } else if (name.equals("option")) { if (tb.currentElement().nodeName().equals("option")) { tb.pop(); } else { tb.error(this); }  } else if (name.equals("select")) { if (!tb.inSelectScope(name)) { tb.error(this); return false; }  tb.popStackToClose(name); tb.resetInsertionMode(); } else { return anythingElse(t, tb); }  return true;case EOF: if (!tb.currentElement().nodeName().equals("html")) tb.error(this);  return true;
/*      */         } 
/*      */         return anythingElse(t, tb);
/*      */       } private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
/* 1455 */         tb.error(this);
/* 1456 */         return false;
/*      */       }
/*      */     };
/*      */   
/* 1460 */   static HtmlTreeBuilderState InSelectInTable = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1464 */         return "InSelectInTable";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1468 */         if (t.isStartTag() && StringUtil.in(t.asStartTag().name(), new String[] { "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th" })) {
/* 1469 */           tb.error(this);
/* 1470 */           tb.processEndTag("select");
/* 1471 */           return tb.process(t);
/* 1472 */         }  if (t.isEndTag() && StringUtil.in(t.asEndTag().name(), new String[] { "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th" })) {
/* 1473 */           tb.error(this);
/* 1474 */           if (tb.inTableScope(t.asEndTag().name())) {
/* 1475 */             tb.processEndTag("select");
/* 1476 */             return tb.process(t);
/*      */           } 
/* 1478 */           return false;
/*      */         } 
/* 1480 */         return tb.process(t, InSelect);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1485 */   static HtmlTreeBuilderState AfterBody = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1489 */         return "AfterBody";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1493 */         if (HtmlTreeBuilderState.isWhitespace(t))
/* 1494 */           return tb.process(t, InBody); 
/* 1495 */         if (t.isComment())
/* 1496 */         { tb.insert(t.asComment()); }
/* 1497 */         else { if (t.isDoctype()) {
/* 1498 */             tb.error(this);
/* 1499 */             return false;
/* 1500 */           }  if (t.isStartTag() && t.asStartTag().name().equals("html"))
/* 1501 */             return tb.process(t, InBody); 
/* 1502 */           if (t.isEndTag() && t.asEndTag().name().equals("html")) {
/* 1503 */             if (tb.isFragmentParsing()) {
/* 1504 */               tb.error(this);
/* 1505 */               return false;
/*      */             } 
/* 1507 */             tb.transition(AfterAfterBody);
/*      */           }
/* 1509 */           else if (!t.isEOF()) {
/*      */ 
/*      */             
/* 1512 */             tb.error(this);
/* 1513 */             tb.transition(InBody);
/* 1514 */             return tb.process(t);
/*      */           }  }
/* 1516 */          return true;
/*      */       }
/*      */     };
/*      */   
/* 1520 */   static HtmlTreeBuilderState InFrameset = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1524 */         return "InFrameset";
/*      */       }
/*      */ 
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1529 */         if (HtmlTreeBuilderState.isWhitespace(t))
/* 1530 */         { tb.insert(t.asCharacter()); }
/* 1531 */         else if (t.isComment())
/* 1532 */         { tb.insert(t.asComment()); }
/* 1533 */         else { if (t.isDoctype()) {
/* 1534 */             tb.error(this);
/* 1535 */             return false;
/* 1536 */           }  if (t.isStartTag()) {
/* 1537 */             Token.StartTag start = t.asStartTag();
/* 1538 */             String name = start.name();
/* 1539 */             if (name.equals("html"))
/* 1540 */               return tb.process(start, InBody); 
/* 1541 */             if (name.equals("frameset"))
/* 1542 */             { tb.insert(start); }
/* 1543 */             else if (name.equals("frame"))
/* 1544 */             { tb.insertEmpty(start); }
/* 1545 */             else { if (name.equals("noframes")) {
/* 1546 */                 return tb.process(start, InHead);
/*      */               }
/* 1548 */               tb.error(this);
/* 1549 */               return false; }
/*      */           
/* 1551 */           } else if (t.isEndTag() && t.asEndTag().name().equals("frameset")) {
/* 1552 */             if (tb.currentElement().nodeName().equals("html")) {
/* 1553 */               tb.error(this);
/* 1554 */               return false;
/*      */             } 
/* 1556 */             tb.pop();
/* 1557 */             if (!tb.isFragmentParsing() && !tb.currentElement().nodeName().equals("frameset")) {
/* 1558 */               tb.transition(AfterFrameset);
/*      */             }
/*      */           }
/* 1561 */           else if (t.isEOF()) {
/* 1562 */             if (!tb.currentElement().nodeName().equals("html")) {
/* 1563 */               tb.error(this);
/* 1564 */               return true;
/*      */             } 
/*      */           } else {
/* 1567 */             tb.error(this);
/* 1568 */             return false;
/*      */           }  }
/* 1570 */          return true;
/*      */       }
/*      */     };
/*      */   
/* 1574 */   static HtmlTreeBuilderState AfterFrameset = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1578 */         return "AfterFrameset";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1582 */         if (HtmlTreeBuilderState.isWhitespace(t))
/* 1583 */         { tb.insert(t.asCharacter()); }
/* 1584 */         else if (t.isComment())
/* 1585 */         { tb.insert(t.asComment()); }
/* 1586 */         else { if (t.isDoctype()) {
/* 1587 */             tb.error(this);
/* 1588 */             return false;
/* 1589 */           }  if (t.isStartTag() && t.asStartTag().name().equals("html"))
/* 1590 */             return tb.process(t, InBody); 
/* 1591 */           if (t.isEndTag() && t.asEndTag().name().equals("html"))
/* 1592 */           { tb.transition(AfterAfterFrameset); }
/* 1593 */           else { if (t.isStartTag() && t.asStartTag().name().equals("noframes"))
/* 1594 */               return tb.process(t, InHead); 
/* 1595 */             if (!t.isEOF())
/*      */             
/*      */             { 
/* 1598 */               tb.error(this);
/* 1599 */               return false; }  }
/*      */            }
/* 1601 */          return true;
/*      */       }
/*      */     };
/*      */   
/* 1605 */   static HtmlTreeBuilderState AfterAfterBody = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1609 */         return "AfterAfterBody";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1613 */         if (t.isComment())
/* 1614 */         { tb.insert(t.asComment()); }
/* 1615 */         else { if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || (t.isStartTag() && t.asStartTag().name().equals("html")))
/* 1616 */             return tb.process(t, InBody); 
/* 1617 */           if (!t.isEOF()) {
/*      */ 
/*      */             
/* 1620 */             tb.error(this);
/* 1621 */             tb.transition(InBody);
/* 1622 */             return tb.process(t);
/*      */           }  }
/* 1624 */          return true;
/*      */       }
/*      */     };
/*      */   
/* 1628 */   static HtmlTreeBuilderState AfterAfterFrameset = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1632 */         return "AfterAfterFrameset";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1636 */         if (t.isComment())
/* 1637 */         { tb.insert(t.asComment()); }
/* 1638 */         else { if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || (t.isStartTag() && t.asStartTag().name().equals("html")))
/* 1639 */             return tb.process(t, InBody); 
/* 1640 */           if (!t.isEOF()) {
/*      */             
/* 1642 */             if (t.isStartTag() && t.asStartTag().name().equals("noframes")) {
/* 1643 */               return tb.process(t, InHead);
/*      */             }
/* 1645 */             tb.error(this);
/* 1646 */             return false;
/*      */           }  }
/* 1648 */          return true;
/*      */       }
/*      */     };
/*      */   
/* 1652 */   static HtmlTreeBuilderState ForeignContent = new HtmlTreeBuilderState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1656 */         return "ForeignContent";
/*      */       }
/*      */       
/*      */       boolean process(Token t, HtmlTreeBuilder tb) {
/* 1660 */         return true;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1667 */     return getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1673 */   private static String nullString = String.valueOf(false);
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isWhitespace(Token t) {
/* 1678 */     if (t.isCharacter()) {
/* 1679 */       String data = t.asCharacter().getData();
/* 1680 */       return isWhitespace(data);
/*      */     } 
/* 1682 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isWhitespace(String data) {
/* 1687 */     for (int i = 0; i < data.length(); i++) {
/* 1688 */       char c = data.charAt(i);
/* 1689 */       if (!StringUtil.isWhitespace(c))
/* 1690 */         return false; 
/*      */     } 
/* 1692 */     return true;
/*      */   }
/*      */   
/*      */   private static void handleRcData(Token.StartTag startTag, HtmlTreeBuilder tb) {
/* 1696 */     tb.insert(startTag);
/* 1697 */     tb.tokeniser.transition(TokeniserState.Rcdata);
/* 1698 */     tb.markInsertionMode();
/* 1699 */     tb.transition(Text);
/*      */   }
/*      */   
/*      */   private static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder tb) {
/* 1703 */     tb.insert(startTag);
/* 1704 */     tb.tokeniser.transition(TokeniserState.Rawtext);
/* 1705 */     tb.markInsertionMode();
/* 1706 */     tb.transition(Text);
/*      */   }
/*      */   abstract String getName();
/*      */   
/*      */   abstract boolean process(Token paramToken, HtmlTreeBuilder paramHtmlTreeBuilder);
/*      */   
/* 1712 */   private static final class Constants { static final String[] InBodyStartToHead = new String[] { "base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", "title" };
/* 1713 */     static final String[] InBodyStartPClosers = new String[] { "address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul" };
/*      */ 
/*      */     
/* 1716 */     static final String[] Headings = new String[] { "h1", "h2", "h3", "h4", "h5", "h6" };
/* 1717 */     static final String[] InBodyStartPreListing = new String[] { "pre", "listing" };
/* 1718 */     static final String[] InBodyStartLiBreakers = new String[] { "address", "div", "p" };
/* 1719 */     static final String[] DdDt = new String[] { "dd", "dt" };
/* 1720 */     static final String[] Formatters = new String[] { "b", "big", "code", "em", "font", "i", "s", "small", "strike", "strong", "tt", "u" };
/* 1721 */     static final String[] InBodyStartApplets = new String[] { "applet", "marquee", "object" };
/* 1722 */     static final String[] InBodyStartEmptyFormatters = new String[] { "area", "br", "embed", "img", "keygen", "wbr" };
/* 1723 */     static final String[] InBodyStartMedia = new String[] { "param", "source", "track" };
/* 1724 */     static final String[] InBodyStartInputAttribs = new String[] { "name", "action", "prompt" };
/* 1725 */     static final String[] InBodyStartOptions = new String[] { "optgroup", "option" };
/* 1726 */     static final String[] InBodyStartRuby = new String[] { "rp", "rt" };
/* 1727 */     static final String[] InBodyStartDrop = new String[] { "caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr" };
/* 1728 */     static final String[] InBodyEndClosers = new String[] { "address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul" };
/*      */ 
/*      */     
/* 1731 */     static final String[] InBodyEndAdoptionFormatters = new String[] { "a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u" };
/* 1732 */     static final String[] InBodyEndTableFosters = new String[] { "table", "tbody", "tfoot", "thead", "tr" }; }
/*      */ 
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/HtmlTreeBuilderState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */