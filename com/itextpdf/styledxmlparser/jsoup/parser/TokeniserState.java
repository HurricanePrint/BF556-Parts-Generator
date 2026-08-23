/*      */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*      */ 
/*      */ import java.util.Arrays;
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
/*      */ abstract class TokeniserState
/*      */ {
/*   52 */   static TokeniserState Data = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*   56 */         return "Data";
/*      */       }
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*   61 */         switch (r.current()) {
/*      */           case '&':
/*   63 */             t.advanceTransition(CharacterReferenceInData);
/*      */             return;
/*      */           case '<':
/*   66 */             t.advanceTransition(TagOpen);
/*      */             return;
/*      */           case '\000':
/*   69 */             t.error(this);
/*   70 */             t.emit(r.consume());
/*      */             return;
/*      */           case '￿':
/*   73 */             t.emit(new Token.EOF());
/*      */             return;
/*      */         } 
/*   76 */         String data = r.consumeData();
/*   77 */         t.emit(data);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*   83 */   static TokeniserState CharacterReferenceInData = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*   87 */         return "CharacterReferenceInData";
/*      */       }
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*   92 */         TokeniserState.readCharRef(t, Data);
/*      */       }
/*      */     };
/*      */   
/*   96 */   static TokeniserState Rcdata = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  100 */         return "Rcdata";
/*      */       }
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  105 */         switch (r.current()) {
/*      */           case '&':
/*  107 */             t.advanceTransition(CharacterReferenceInRcdata);
/*      */             return;
/*      */           case '<':
/*  110 */             t.advanceTransition(RcdataLessthanSign);
/*      */             return;
/*      */           case '\000':
/*  113 */             t.error(this);
/*  114 */             r.advance();
/*  115 */             t.emit('�');
/*      */             return;
/*      */           case '￿':
/*  118 */             t.emit(new Token.EOF());
/*      */             return;
/*      */         } 
/*  121 */         String data = r.consumeToAny(new char[] { '&', '<', Character.MIN_VALUE });
/*  122 */         t.emit(data);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*  128 */   static TokeniserState CharacterReferenceInRcdata = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  132 */         return "CharacterReferenceInRcdata";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  136 */         TokeniserState.readCharRef(t, Rcdata);
/*      */       }
/*      */     };
/*      */   
/*  140 */   static TokeniserState Rawtext = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  144 */         return "Rawtext";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  148 */         TokeniserState.readData(t, r, this, RawtextLessthanSign);
/*      */       }
/*      */     };
/*      */   
/*  152 */   static TokeniserState ScriptData = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  156 */         return "ScriptData";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  160 */         TokeniserState.readData(t, r, this, ScriptDataLessthanSign);
/*      */       }
/*      */     };
/*      */   
/*  164 */   static TokeniserState PLAINTEXT = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  168 */         return "PLAINTEXT";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  172 */         switch (r.current()) {
/*      */           case '\000':
/*  174 */             t.error(this);
/*  175 */             r.advance();
/*  176 */             t.emit('�');
/*      */             return;
/*      */           case '￿':
/*  179 */             t.emit(new Token.EOF());
/*      */             return;
/*      */         } 
/*  182 */         String data = r.consumeTo(false);
/*  183 */         t.emit(data);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*  189 */   static TokeniserState TagOpen = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  193 */         return "TagOpen";
/*      */       }
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  198 */         switch (r.current()) {
/*      */           case '!':
/*  200 */             t.advanceTransition(MarkupDeclarationOpen);
/*      */             return;
/*      */           case '/':
/*  203 */             t.advanceTransition(EndTagOpen);
/*      */             return;
/*      */           case '?':
/*  206 */             t.advanceTransition(BogusComment);
/*      */             return;
/*      */         } 
/*  209 */         if (r.matchesLetter()) {
/*  210 */           t.createTagPending(true);
/*  211 */           t.transition(TagName);
/*      */         } else {
/*  213 */           t.error(this);
/*  214 */           t.emit('<');
/*  215 */           t.transition(Data);
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*  222 */   static TokeniserState EndTagOpen = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  226 */         return "EndTagOpen";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  230 */         if (r.isEmpty()) {
/*  231 */           t.eofError(this);
/*  232 */           t.emit("</");
/*  233 */           t.transition(Data);
/*  234 */         } else if (r.matchesLetter()) {
/*  235 */           t.createTagPending(false);
/*  236 */           t.transition(TagName);
/*  237 */         } else if (r.matches('>')) {
/*  238 */           t.error(this);
/*  239 */           t.advanceTransition(Data);
/*      */         } else {
/*  241 */           t.error(this);
/*  242 */           t.advanceTransition(BogusComment);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  247 */   static TokeniserState TagName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  251 */         return "TagName";
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  258 */         String tagName = r.consumeTagName().toLowerCase();
/*  259 */         t.tagPending.appendTagName(tagName);
/*      */         
/*  261 */         switch (r.consume()) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*  267 */             t.transition(BeforeAttributeName);
/*      */             break;
/*      */           case '/':
/*  270 */             t.transition(SelfClosingStartTag);
/*      */             break;
/*      */           case '>':
/*  273 */             t.emitTagPending();
/*  274 */             t.transition(Data);
/*      */             break;
/*      */           case '\000':
/*  277 */             t.tagPending.appendTagName(TokeniserState.replacementStr);
/*      */             break;
/*      */           case '￿':
/*  280 */             t.eofError(this);
/*  281 */             t.transition(Data);
/*      */             break;
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  287 */   static TokeniserState RcdataLessthanSign = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  291 */         return "RcdataLessthanSign";
/*      */       }
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  296 */         if (r.matches('/')) {
/*  297 */           t.createTempBuffer();
/*  298 */           t.advanceTransition(RCDATAEndTagOpen);
/*  299 */         } else if (r.matchesLetter() && t.appropriateEndTagName() != null && !r.containsIgnoreCase("</" + t.appropriateEndTagName())) {
/*      */ 
/*      */           
/*  302 */           t.tagPending = t.createTagPending(false).name(t.appropriateEndTagName());
/*  303 */           t.emitTagPending();
/*  304 */           r.unconsume();
/*  305 */           t.transition(Data);
/*      */         } else {
/*  307 */           t.emit("<");
/*  308 */           t.transition(Rcdata);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  313 */   static TokeniserState RCDATAEndTagOpen = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  317 */         return "RCDATAEndTagOpen";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  321 */         if (r.matchesLetter()) {
/*  322 */           t.createTagPending(false);
/*  323 */           t.tagPending.appendTagName(Character.toLowerCase(r.current()));
/*  324 */           t.dataBuffer.append(Character.toLowerCase(r.current()));
/*  325 */           t.advanceTransition(RCDATAEndTagName);
/*      */         } else {
/*  327 */           t.emit("</");
/*  328 */           t.transition(Rcdata);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  333 */   static TokeniserState RCDATAEndTagName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  337 */         return "RCDATAEndTagName";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  341 */         if (r.matchesLetter()) {
/*  342 */           String name = r.consumeLetterSequence();
/*  343 */           t.tagPending.appendTagName(name.toLowerCase());
/*  344 */           t.dataBuffer.append(name);
/*      */           
/*      */           return;
/*      */         } 
/*  348 */         char c = r.consume();
/*  349 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*  355 */             if (t.isAppropriateEndTagToken()) {
/*  356 */               t.transition(BeforeAttributeName);
/*      */             } else {
/*  358 */               anythingElse(t, r);
/*      */             }  return;
/*      */           case '/':
/*  361 */             if (t.isAppropriateEndTagToken()) {
/*  362 */               t.transition(SelfClosingStartTag);
/*      */             } else {
/*  364 */               anythingElse(t, r);
/*      */             }  return;
/*      */           case '>':
/*  367 */             if (t.isAppropriateEndTagToken()) {
/*  368 */               t.emitTagPending();
/*  369 */               t.transition(Data);
/*      */             } else {
/*      */               
/*  372 */               anythingElse(t, r);
/*      */             }  return;
/*      */         } 
/*  375 */         anythingElse(t, r);
/*      */       }
/*      */ 
/*      */       
/*      */       private void anythingElse(Tokeniser t, CharacterReader r) {
/*  380 */         t.emit("</" + t.dataBuffer.toString());
/*  381 */         r.unconsume();
/*  382 */         t.transition(Rcdata);
/*      */       }
/*      */     };
/*      */   
/*  386 */   static TokeniserState RawtextLessthanSign = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  390 */         return "RawtextLessthanSign";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  394 */         if (r.matches('/')) {
/*  395 */           t.createTempBuffer();
/*  396 */           t.advanceTransition(RawtextEndTagOpen);
/*      */         } else {
/*  398 */           t.emit('<');
/*  399 */           t.transition(Rawtext);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  404 */   static TokeniserState RawtextEndTagOpen = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  408 */         return "RawtextEndTagOpen";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  412 */         TokeniserState.readEndTag(t, r, RawtextEndTagName, Rawtext);
/*      */       }
/*      */     };
/*      */   
/*  416 */   static TokeniserState RawtextEndTagName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  420 */         return "RawtextEndTagName";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  424 */         TokeniserState.handleDataEndTag(t, r, Rawtext);
/*      */       }
/*      */     };
/*      */   
/*  428 */   static TokeniserState ScriptDataLessthanSign = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  432 */         return "ScriptDataLessthanSign";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  436 */         switch (r.consume()) {
/*      */           case '/':
/*  438 */             t.createTempBuffer();
/*  439 */             t.transition(ScriptDataEndTagOpen);
/*      */             return;
/*      */           case '!':
/*  442 */             t.emit("<!");
/*  443 */             t.transition(ScriptDataEscapeStart);
/*      */             return;
/*      */         } 
/*  446 */         t.emit("<");
/*  447 */         r.unconsume();
/*  448 */         t.transition(ScriptData);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  453 */   static TokeniserState ScriptDataEndTagOpen = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  457 */         return "ScriptDataEndTagOpen";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  461 */         TokeniserState.readEndTag(t, r, ScriptDataEndTagName, ScriptData);
/*      */       }
/*      */     };
/*      */   
/*  465 */   static TokeniserState ScriptDataEndTagName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  469 */         return "ScriptDataEndTagName";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  473 */         TokeniserState.handleDataEndTag(t, r, ScriptData);
/*      */       }
/*      */     };
/*      */   
/*  477 */   static TokeniserState ScriptDataEscapeStart = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  481 */         return "ScriptDataEscapeStart";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  485 */         if (r.matches('-')) {
/*  486 */           t.emit('-');
/*  487 */           t.advanceTransition(ScriptDataEscapeStartDash);
/*      */         } else {
/*  489 */           t.transition(ScriptData);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  494 */   static TokeniserState ScriptDataEscapeStartDash = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  498 */         return "ScriptDataEscapeStartDash";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  502 */         if (r.matches('-')) {
/*  503 */           t.emit('-');
/*  504 */           t.advanceTransition(ScriptDataEscapedDashDash);
/*      */         } else {
/*  506 */           t.transition(ScriptData);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  511 */   static TokeniserState ScriptDataEscaped = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  515 */         return "ScriptDataEscaped";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  519 */         if (r.isEmpty()) {
/*  520 */           t.eofError(this);
/*  521 */           t.transition(Data);
/*      */           
/*      */           return;
/*      */         } 
/*  525 */         switch (r.current()) {
/*      */           case '-':
/*  527 */             t.emit('-');
/*  528 */             t.advanceTransition(ScriptDataEscapedDash);
/*      */             return;
/*      */           case '<':
/*  531 */             t.advanceTransition(ScriptDataEscapedLessthanSign);
/*      */             return;
/*      */           case '\000':
/*  534 */             t.error(this);
/*  535 */             r.advance();
/*  536 */             t.emit('�');
/*      */             return;
/*      */         } 
/*  539 */         String data = r.consumeToAny(new char[] { '-', '<', Character.MIN_VALUE });
/*  540 */         t.emit(data);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  545 */   static TokeniserState ScriptDataEscapedDash = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  549 */         return "ScriptDataEscapedDash";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  553 */         if (r.isEmpty()) {
/*  554 */           t.eofError(this);
/*  555 */           t.transition(Data);
/*      */           
/*      */           return;
/*      */         } 
/*  559 */         char c = r.consume();
/*  560 */         switch (c) {
/*      */           case '-':
/*  562 */             t.emit(c);
/*  563 */             t.transition(ScriptDataEscapedDashDash);
/*      */             return;
/*      */           case '<':
/*  566 */             t.transition(ScriptDataEscapedLessthanSign);
/*      */             return;
/*      */           case '\000':
/*  569 */             t.error(this);
/*  570 */             t.emit('�');
/*  571 */             t.transition(ScriptDataEscaped);
/*      */             return;
/*      */         } 
/*  574 */         t.emit(c);
/*  575 */         t.transition(ScriptDataEscaped);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  580 */   static TokeniserState ScriptDataEscapedDashDash = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  584 */         return "ScriptDataEscapedDashDash";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  588 */         if (r.isEmpty()) {
/*  589 */           t.eofError(this);
/*  590 */           t.transition(Data);
/*      */           
/*      */           return;
/*      */         } 
/*  594 */         char c = r.consume();
/*  595 */         switch (c) {
/*      */           case '-':
/*  597 */             t.emit(c);
/*      */             return;
/*      */           case '<':
/*  600 */             t.transition(ScriptDataEscapedLessthanSign);
/*      */             return;
/*      */           case '>':
/*  603 */             t.emit(c);
/*  604 */             t.transition(ScriptData);
/*      */             return;
/*      */           case '\000':
/*  607 */             t.error(this);
/*  608 */             t.emit('�');
/*  609 */             t.transition(ScriptDataEscaped);
/*      */             return;
/*      */         } 
/*  612 */         t.emit(c);
/*  613 */         t.transition(ScriptDataEscaped);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  618 */   static TokeniserState ScriptDataEscapedLessthanSign = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  622 */         return "ScriptDataEscapedLessthanSign";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  626 */         if (r.matchesLetter()) {
/*  627 */           t.createTempBuffer();
/*  628 */           t.dataBuffer.append(Character.toLowerCase(r.current()));
/*  629 */           t.emit("<" + r.current());
/*  630 */           t.advanceTransition(ScriptDataDoubleEscapeStart);
/*  631 */         } else if (r.matches('/')) {
/*  632 */           t.createTempBuffer();
/*  633 */           t.advanceTransition(ScriptDataEscapedEndTagOpen);
/*      */         } else {
/*  635 */           t.emit('<');
/*  636 */           t.transition(ScriptDataEscaped);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  641 */   static TokeniserState ScriptDataEscapedEndTagOpen = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  645 */         return "ScriptDataEscapedEndTagOpen";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  649 */         if (r.matchesLetter()) {
/*  650 */           t.createTagPending(false);
/*  651 */           t.tagPending.appendTagName(Character.toLowerCase(r.current()));
/*  652 */           t.dataBuffer.append(r.current());
/*  653 */           t.advanceTransition(ScriptDataEscapedEndTagName);
/*      */         } else {
/*  655 */           t.emit("</");
/*  656 */           t.transition(ScriptDataEscaped);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  661 */   static TokeniserState ScriptDataEscapedEndTagName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  665 */         return "ScriptDataEscapedEndTagName";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  669 */         TokeniserState.handleDataEndTag(t, r, ScriptDataEscaped);
/*      */       }
/*      */     };
/*      */   
/*  673 */   static TokeniserState ScriptDataDoubleEscapeStart = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  677 */         return "ScriptDataDoubleEscapeStart";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  681 */         TokeniserState.handleDataDoubleEscapeTag(t, r, ScriptDataDoubleEscaped, ScriptDataEscaped);
/*      */       }
/*      */     };
/*      */   
/*  685 */   static TokeniserState ScriptDataDoubleEscaped = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  689 */         return "ScriptDataDoubleEscaped";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  693 */         char c = r.current();
/*  694 */         switch (c) {
/*      */           case '-':
/*  696 */             t.emit(c);
/*  697 */             t.advanceTransition(ScriptDataDoubleEscapedDash);
/*      */             return;
/*      */           case '<':
/*  700 */             t.emit(c);
/*  701 */             t.advanceTransition(ScriptDataDoubleEscapedLessthanSign);
/*      */             return;
/*      */           case '\000':
/*  704 */             t.error(this);
/*  705 */             r.advance();
/*  706 */             t.emit('�');
/*      */             return;
/*      */           case '￿':
/*  709 */             t.eofError(this);
/*  710 */             t.transition(Data);
/*      */             return;
/*      */         } 
/*  713 */         String data = r.consumeToAny(new char[] { '-', '<', Character.MIN_VALUE });
/*  714 */         t.emit(data);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  719 */   static TokeniserState ScriptDataDoubleEscapedDash = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  723 */         return "ScriptDataDoubleEscapedDash";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  727 */         char c = r.consume();
/*  728 */         switch (c) {
/*      */           case '-':
/*  730 */             t.emit(c);
/*  731 */             t.transition(ScriptDataDoubleEscapedDashDash);
/*      */             return;
/*      */           case '<':
/*  734 */             t.emit(c);
/*  735 */             t.transition(ScriptDataDoubleEscapedLessthanSign);
/*      */             return;
/*      */           case '\000':
/*  738 */             t.error(this);
/*  739 */             t.emit('�');
/*  740 */             t.transition(ScriptDataDoubleEscaped);
/*      */             return;
/*      */           case '￿':
/*  743 */             t.eofError(this);
/*  744 */             t.transition(Data);
/*      */             return;
/*      */         } 
/*  747 */         t.emit(c);
/*  748 */         t.transition(ScriptDataDoubleEscaped);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  753 */   static TokeniserState ScriptDataDoubleEscapedDashDash = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  757 */         return "ScriptDataDoubleEscapedDashDash";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  761 */         char c = r.consume();
/*  762 */         switch (c) {
/*      */           case '-':
/*  764 */             t.emit(c);
/*      */             return;
/*      */           case '<':
/*  767 */             t.emit(c);
/*  768 */             t.transition(ScriptDataDoubleEscapedLessthanSign);
/*      */             return;
/*      */           case '>':
/*  771 */             t.emit(c);
/*  772 */             t.transition(ScriptData);
/*      */             return;
/*      */           case '\000':
/*  775 */             t.error(this);
/*  776 */             t.emit('�');
/*  777 */             t.transition(ScriptDataDoubleEscaped);
/*      */             return;
/*      */           case '￿':
/*  780 */             t.eofError(this);
/*  781 */             t.transition(Data);
/*      */             return;
/*      */         } 
/*  784 */         t.emit(c);
/*  785 */         t.transition(ScriptDataDoubleEscaped);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  790 */   static TokeniserState ScriptDataDoubleEscapedLessthanSign = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  794 */         return "ScriptDataDoubleEscapedLessthanSign";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  798 */         if (r.matches('/')) {
/*  799 */           t.emit('/');
/*  800 */           t.createTempBuffer();
/*  801 */           t.advanceTransition(ScriptDataDoubleEscapeEnd);
/*      */         } else {
/*  803 */           t.transition(ScriptDataDoubleEscaped);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  808 */   static TokeniserState ScriptDataDoubleEscapeEnd = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  812 */         return "ScriptDataDoubleEscapeEnd";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  816 */         TokeniserState.handleDataDoubleEscapeTag(t, r, ScriptDataEscaped, ScriptDataDoubleEscaped);
/*      */       }
/*      */     };
/*      */   
/*  820 */   static TokeniserState BeforeAttributeName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  824 */         return "BeforeAttributeName";
/*      */       }
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  829 */         char c = r.consume();
/*  830 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             return;
/*      */           case '/':
/*  838 */             t.transition(SelfClosingStartTag);
/*      */           
/*      */           case '>':
/*  841 */             t.emitTagPending();
/*  842 */             t.transition(Data);
/*      */           
/*      */           case '\000':
/*  845 */             t.error(this);
/*  846 */             t.tagPending.newAttribute();
/*  847 */             r.unconsume();
/*  848 */             t.transition(AttributeName);
/*      */           
/*      */           case '￿':
/*  851 */             t.eofError(this);
/*  852 */             t.transition(Data);
/*      */           
/*      */           case '"':
/*      */           case '\'':
/*      */           case '<':
/*      */           case '=':
/*  858 */             t.error(this);
/*  859 */             t.tagPending.newAttribute();
/*  860 */             t.tagPending.appendAttributeName(c);
/*  861 */             t.transition(AttributeName);
/*      */         } 
/*      */         
/*  864 */         t.tagPending.newAttribute();
/*  865 */         r.unconsume();
/*  866 */         t.transition(AttributeName);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  871 */   static TokeniserState AttributeName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  875 */         return "AttributeName";
/*      */       }
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  880 */         String name = r.consumeToAnySorted(TokeniserState.attributeNameCharsSorted);
/*  881 */         t.tagPending.appendAttributeName(name.toLowerCase());
/*      */         
/*  883 */         char c = r.consume();
/*  884 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*  890 */             t.transition(AfterAttributeName);
/*      */             break;
/*      */           case '/':
/*  893 */             t.transition(SelfClosingStartTag);
/*      */             break;
/*      */           case '=':
/*  896 */             t.transition(BeforeAttributeValue);
/*      */             break;
/*      */           case '>':
/*  899 */             t.emitTagPending();
/*  900 */             t.transition(Data);
/*      */             break;
/*      */           case '\000':
/*  903 */             t.error(this);
/*  904 */             t.tagPending.appendAttributeName('�');
/*      */             break;
/*      */           case '￿':
/*  907 */             t.eofError(this);
/*  908 */             t.transition(Data);
/*      */             break;
/*      */           case '"':
/*      */           case '\'':
/*      */           case '<':
/*  913 */             t.error(this);
/*  914 */             t.tagPending.appendAttributeName(c);
/*      */             break;
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*  920 */   static TokeniserState AfterAttributeName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  924 */         return "AfterAttributeName";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  928 */         char c = r.consume();
/*  929 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             return;
/*      */           
/*      */           case '/':
/*  938 */             t.transition(SelfClosingStartTag);
/*      */           
/*      */           case '=':
/*  941 */             t.transition(BeforeAttributeValue);
/*      */           
/*      */           case '>':
/*  944 */             t.emitTagPending();
/*  945 */             t.transition(Data);
/*      */           
/*      */           case '\000':
/*  948 */             t.error(this);
/*  949 */             t.tagPending.appendAttributeName('�');
/*  950 */             t.transition(AttributeName);
/*      */           
/*      */           case '￿':
/*  953 */             t.eofError(this);
/*  954 */             t.transition(Data);
/*      */           
/*      */           case '"':
/*      */           case '\'':
/*      */           case '<':
/*  959 */             t.error(this);
/*  960 */             t.tagPending.newAttribute();
/*  961 */             t.tagPending.appendAttributeName(c);
/*  962 */             t.transition(AttributeName);
/*      */         } 
/*      */         
/*  965 */         t.tagPending.newAttribute();
/*  966 */         r.unconsume();
/*  967 */         t.transition(AttributeName);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  972 */   static TokeniserState BeforeAttributeValue = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/*  976 */         return "BeforeAttributeValue";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/*  980 */         char c = r.consume();
/*  981 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             return;
/*      */           
/*      */           case '"':
/*  990 */             t.transition(AttributeValue_doubleQuoted);
/*      */           
/*      */           case '&':
/*  993 */             r.unconsume();
/*  994 */             t.transition(AttributeValue_unquoted);
/*      */           
/*      */           case '\'':
/*  997 */             t.transition(AttributeValue_singleQuoted);
/*      */           
/*      */           case '\000':
/* 1000 */             t.error(this);
/* 1001 */             t.tagPending.appendAttributeValue('�');
/* 1002 */             t.transition(AttributeValue_unquoted);
/*      */           
/*      */           case '￿':
/* 1005 */             t.eofError(this);
/* 1006 */             t.emitTagPending();
/* 1007 */             t.transition(Data);
/*      */           
/*      */           case '>':
/* 1010 */             t.error(this);
/* 1011 */             t.emitTagPending();
/* 1012 */             t.transition(Data);
/*      */           
/*      */           case '<':
/*      */           case '=':
/*      */           case '`':
/* 1017 */             t.error(this);
/* 1018 */             t.tagPending.appendAttributeValue(c);
/* 1019 */             t.transition(AttributeValue_unquoted);
/*      */         } 
/*      */         
/* 1022 */         r.unconsume();
/* 1023 */         t.transition(AttributeValue_unquoted);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1028 */   static TokeniserState AttributeValue_doubleQuoted = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1032 */         return "AttributeValue_doubleQuoted";
/*      */       }
/*      */       void read(Tokeniser t, CharacterReader r) {
/*      */         char[] ref;
/* 1036 */         String value = r.consumeToAny(TokeniserState.attributeDoubleValueCharsSorted);
/* 1037 */         if (value.length() > 0) {
/* 1038 */           t.tagPending.appendAttributeValue(value);
/*      */         } else {
/* 1040 */           t.tagPending.setEmptyAttributeValue();
/*      */         } 
/* 1042 */         char c = r.consume();
/* 1043 */         switch (c) {
/*      */           case '"':
/* 1045 */             t.transition(AfterAttributeValue_quoted);
/*      */             break;
/*      */           case '&':
/* 1048 */             ref = t.consumeCharacterReference(Character.valueOf('"'), true);
/* 1049 */             if (ref != null) {
/* 1050 */               t.tagPending.appendAttributeValue(ref); break;
/*      */             } 
/* 1052 */             t.tagPending.appendAttributeValue('&');
/*      */             break;
/*      */           case '\000':
/* 1055 */             t.error(this);
/* 1056 */             t.tagPending.appendAttributeValue('�');
/*      */             break;
/*      */           case '￿':
/* 1059 */             t.eofError(this);
/* 1060 */             t.transition(Data);
/*      */             break;
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1067 */   static TokeniserState AttributeValue_singleQuoted = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1071 */         return "AttributeValue_singleQuoted";
/*      */       }
/*      */       void read(Tokeniser t, CharacterReader r) {
/*      */         char[] ref;
/* 1075 */         String value = r.consumeToAny(TokeniserState.attributeSingleValueCharsSorted);
/* 1076 */         if (value.length() > 0) {
/* 1077 */           t.tagPending.appendAttributeValue(value);
/*      */         } else {
/* 1079 */           t.tagPending.setEmptyAttributeValue();
/*      */         } 
/* 1081 */         char c = r.consume();
/* 1082 */         switch (c) {
/*      */           case '\'':
/* 1084 */             t.transition(AfterAttributeValue_quoted);
/*      */             break;
/*      */           case '&':
/* 1087 */             ref = t.consumeCharacterReference(Character.valueOf('\''), true);
/* 1088 */             if (ref != null) {
/* 1089 */               t.tagPending.appendAttributeValue(ref); break;
/*      */             } 
/* 1091 */             t.tagPending.appendAttributeValue('&');
/*      */             break;
/*      */           case '\000':
/* 1094 */             t.error(this);
/* 1095 */             t.tagPending.appendAttributeValue('�');
/*      */             break;
/*      */           case '￿':
/* 1098 */             t.eofError(this);
/* 1099 */             t.transition(Data);
/*      */             break;
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1106 */   static TokeniserState AttributeValue_unquoted = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1110 */         return "AttributeValue_unquoted";
/*      */       }
/*      */       void read(Tokeniser t, CharacterReader r) {
/*      */         char[] ref;
/* 1114 */         String value = r.consumeToAnySorted(TokeniserState.attributeValueUnquoted);
/* 1115 */         if (value.length() > 0) {
/* 1116 */           t.tagPending.appendAttributeValue(value);
/*      */         }
/* 1118 */         char c = r.consume();
/* 1119 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/* 1125 */             t.transition(BeforeAttributeName);
/*      */             break;
/*      */           case '&':
/* 1128 */             ref = t.consumeCharacterReference(Character.valueOf('>'), true);
/* 1129 */             if (ref != null) {
/* 1130 */               t.tagPending.appendAttributeValue(ref); break;
/*      */             } 
/* 1132 */             t.tagPending.appendAttributeValue('&');
/*      */             break;
/*      */           case '>':
/* 1135 */             t.emitTagPending();
/* 1136 */             t.transition(Data);
/*      */             break;
/*      */           case '\000':
/* 1139 */             t.error(this);
/* 1140 */             t.tagPending.appendAttributeValue('�');
/*      */             break;
/*      */           case '￿':
/* 1143 */             t.eofError(this);
/* 1144 */             t.transition(Data);
/*      */             break;
/*      */           case '"':
/*      */           case '\'':
/*      */           case '<':
/*      */           case '=':
/*      */           case '`':
/* 1151 */             t.error(this);
/* 1152 */             t.tagPending.appendAttributeValue(c);
/*      */             break;
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1161 */   static TokeniserState AfterAttributeValue_quoted = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1165 */         return "AfterAttributeValue_quoted";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1169 */         char c = r.consume();
/* 1170 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/* 1176 */             t.transition(BeforeAttributeName);
/*      */             return;
/*      */           case '/':
/* 1179 */             t.transition(SelfClosingStartTag);
/*      */             return;
/*      */           case '>':
/* 1182 */             t.emitTagPending();
/* 1183 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 1186 */             t.eofError(this);
/* 1187 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1190 */         t.error(this);
/* 1191 */         r.unconsume();
/* 1192 */         t.transition(BeforeAttributeName);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/* 1198 */   static TokeniserState SelfClosingStartTag = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1202 */         return "SelfClosingStartTag";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1206 */         char c = r.consume();
/* 1207 */         switch (c) {
/*      */           case '>':
/* 1209 */             t.tagPending.selfClosing = true;
/* 1210 */             t.emitTagPending();
/* 1211 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 1214 */             t.eofError(this);
/* 1215 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1218 */         t.error(this);
/* 1219 */         t.transition(BeforeAttributeName);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1224 */   static TokeniserState BogusComment = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1228 */         return "BogusComment";
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1234 */         r.unconsume();
/* 1235 */         Token.Comment comment = new Token.Comment();
/* 1236 */         comment.bogus = true;
/* 1237 */         comment.data.append(r.consumeTo('>'));
/*      */         
/* 1239 */         t.emit(comment);
/* 1240 */         t.advanceTransition(Data);
/*      */       }
/*      */     };
/*      */   
/* 1244 */   static TokeniserState MarkupDeclarationOpen = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1248 */         return "MarkupDeclarationOpen";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1252 */         if (r.matchConsume("--")) {
/* 1253 */           t.createCommentPending();
/* 1254 */           t.transition(CommentStart);
/* 1255 */         } else if (r.matchConsumeIgnoreCase("DOCTYPE")) {
/* 1256 */           t.transition(Doctype);
/* 1257 */         } else if (r.matchConsume("[CDATA[")) {
/*      */ 
/*      */ 
/*      */           
/* 1261 */           t.transition(CdataSection);
/*      */         } else {
/* 1263 */           t.error(this);
/* 1264 */           t.advanceTransition(BogusComment);
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/* 1269 */   static TokeniserState CommentStart = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1273 */         return "CommentStart";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1277 */         char c = r.consume();
/* 1278 */         switch (c) {
/*      */           case '-':
/* 1280 */             t.transition(CommentStartDash);
/*      */             return;
/*      */           case '\000':
/* 1283 */             t.error(this);
/* 1284 */             t.commentPending.data.append('�');
/* 1285 */             t.transition(Comment);
/*      */             return;
/*      */           case '>':
/* 1288 */             t.error(this);
/* 1289 */             t.emitCommentPending();
/* 1290 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 1293 */             t.eofError(this);
/* 1294 */             t.emitCommentPending();
/* 1295 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1298 */         t.commentPending.data.append(c);
/* 1299 */         t.transition(Comment);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1304 */   static TokeniserState CommentStartDash = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1308 */         return "CommentStartDash";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1312 */         char c = r.consume();
/* 1313 */         switch (c) {
/*      */           case '-':
/* 1315 */             t.transition(CommentStartDash);
/*      */             return;
/*      */           case '\000':
/* 1318 */             t.error(this);
/* 1319 */             t.commentPending.data.append('�');
/* 1320 */             t.transition(Comment);
/*      */             return;
/*      */           case '>':
/* 1323 */             t.error(this);
/* 1324 */             t.emitCommentPending();
/* 1325 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 1328 */             t.eofError(this);
/* 1329 */             t.emitCommentPending();
/* 1330 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1333 */         t.commentPending.data.append(c);
/* 1334 */         t.transition(Comment);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1339 */   static TokeniserState Comment = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1343 */         return "Comment";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1347 */         char c = r.current();
/* 1348 */         switch (c) {
/*      */           case '-':
/* 1350 */             t.advanceTransition(CommentEndDash);
/*      */             return;
/*      */           case '\000':
/* 1353 */             t.error(this);
/* 1354 */             r.advance();
/* 1355 */             t.commentPending.data.append('�');
/*      */             return;
/*      */           case '￿':
/* 1358 */             t.eofError(this);
/* 1359 */             t.emitCommentPending();
/* 1360 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1363 */         t.commentPending.data.append(r.consumeToAny(new char[] { '-', Character.MIN_VALUE }));
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1368 */   static TokeniserState CommentEndDash = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1372 */         return "CommentEndDash";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1376 */         char c = r.consume();
/* 1377 */         switch (c) {
/*      */           case '-':
/* 1379 */             t.transition(CommentEnd);
/*      */             return;
/*      */           case '\000':
/* 1382 */             t.error(this);
/* 1383 */             t.commentPending.data.append('-').append('�');
/* 1384 */             t.transition(Comment);
/*      */             return;
/*      */           case '￿':
/* 1387 */             t.eofError(this);
/* 1388 */             t.emitCommentPending();
/* 1389 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1392 */         t.commentPending.data.append('-').append(c);
/* 1393 */         t.transition(Comment);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1398 */   static TokeniserState CommentEnd = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1402 */         return "CommentEnd";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1406 */         char c = r.consume();
/* 1407 */         switch (c) {
/*      */           case '>':
/* 1409 */             t.emitCommentPending();
/* 1410 */             t.transition(Data);
/*      */             return;
/*      */           case '\000':
/* 1413 */             t.error(this);
/* 1414 */             t.commentPending.data.append("--").append('�');
/* 1415 */             t.transition(Comment);
/*      */             return;
/*      */           case '!':
/* 1418 */             t.error(this);
/* 1419 */             t.transition(CommentEndBang);
/*      */             return;
/*      */           case '-':
/* 1422 */             t.error(this);
/* 1423 */             t.commentPending.data.append('-');
/*      */             return;
/*      */           case '￿':
/* 1426 */             t.eofError(this);
/* 1427 */             t.emitCommentPending();
/* 1428 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1431 */         t.error(this);
/* 1432 */         t.commentPending.data.append("--").append(c);
/* 1433 */         t.transition(Comment);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1438 */   static TokeniserState CommentEndBang = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1442 */         return "CommentEndBang";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1446 */         char c = r.consume();
/* 1447 */         switch (c) {
/*      */           case '-':
/* 1449 */             t.commentPending.data.append("--!");
/* 1450 */             t.transition(CommentEndDash);
/*      */             return;
/*      */           case '>':
/* 1453 */             t.emitCommentPending();
/* 1454 */             t.transition(Data);
/*      */             return;
/*      */           case '\000':
/* 1457 */             t.error(this);
/* 1458 */             t.commentPending.data.append("--!").append('�');
/* 1459 */             t.transition(Comment);
/*      */             return;
/*      */           case '￿':
/* 1462 */             t.eofError(this);
/* 1463 */             t.emitCommentPending();
/* 1464 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1467 */         t.commentPending.data.append("--!").append(c);
/* 1468 */         t.transition(Comment);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1473 */   static TokeniserState Doctype = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1477 */         return "Doctype";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1481 */         char c = r.consume();
/* 1482 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/* 1488 */             t.transition(BeforeDoctypeName);
/*      */             return;
/*      */           case '￿':
/* 1491 */             t.eofError(this);
/*      */           
/*      */           case '>':
/* 1494 */             t.error(this);
/* 1495 */             t.createDoctypePending();
/* 1496 */             t.doctypePending.forceQuirks = true;
/* 1497 */             t.emitDoctypePending();
/* 1498 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1501 */         t.error(this);
/* 1502 */         t.transition(BeforeDoctypeName);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1507 */   static TokeniserState BeforeDoctypeName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1511 */         return "BeforeDoctypeName";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1515 */         if (r.matchesLetter()) {
/* 1516 */           t.createDoctypePending();
/* 1517 */           t.transition(DoctypeName);
/*      */           return;
/*      */         } 
/* 1520 */         char c = r.consume();
/* 1521 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             return;
/*      */           case '\000':
/* 1529 */             t.error(this);
/* 1530 */             t.createDoctypePending();
/* 1531 */             t.doctypePending.name.append('�');
/* 1532 */             t.transition(DoctypeName);
/*      */           
/*      */           case '￿':
/* 1535 */             t.eofError(this);
/* 1536 */             t.createDoctypePending();
/* 1537 */             t.doctypePending.forceQuirks = true;
/* 1538 */             t.emitDoctypePending();
/* 1539 */             t.transition(Data);
/*      */         } 
/*      */         
/* 1542 */         t.createDoctypePending();
/* 1543 */         t.doctypePending.name.append(c);
/* 1544 */         t.transition(DoctypeName);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1549 */   static TokeniserState DoctypeName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1553 */         return "DoctypeName";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1557 */         if (r.matchesLetter()) {
/* 1558 */           String name = r.consumeLetterSequence();
/* 1559 */           t.doctypePending.name.append(name.toLowerCase());
/*      */           return;
/*      */         } 
/* 1562 */         char c = r.consume();
/* 1563 */         switch (c) {
/*      */           case '>':
/* 1565 */             t.emitDoctypePending();
/* 1566 */             t.transition(Data);
/*      */             return;
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/* 1573 */             t.transition(AfterDoctypeName);
/*      */             return;
/*      */           case '\000':
/* 1576 */             t.error(this);
/* 1577 */             t.doctypePending.name.append('�');
/*      */             return;
/*      */           case '￿':
/* 1580 */             t.eofError(this);
/* 1581 */             t.doctypePending.forceQuirks = true;
/* 1582 */             t.emitDoctypePending();
/* 1583 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1586 */         t.doctypePending.name.append(c);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1591 */   static TokeniserState AfterDoctypeName = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1595 */         return "AfterDoctypeName";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1599 */         if (r.isEmpty()) {
/* 1600 */           t.eofError(this);
/* 1601 */           t.doctypePending.forceQuirks = true;
/* 1602 */           t.emitDoctypePending();
/* 1603 */           t.transition(Data);
/*      */           return;
/*      */         } 
/* 1606 */         if (r.matchesAny(new char[] { '\t', '\n', '\r', '\f', ' ' })) {
/* 1607 */           r.advance();
/* 1608 */         } else if (r.matches('>')) {
/* 1609 */           t.emitDoctypePending();
/* 1610 */           t.advanceTransition(Data);
/* 1611 */         } else if (r.matchConsumeIgnoreCase("PUBLIC")) {
/* 1612 */           t.transition(AfterDoctypePublicKeyword);
/* 1613 */         } else if (r.matchConsumeIgnoreCase("SYSTEM")) {
/* 1614 */           t.transition(AfterDoctypeSystemKeyword);
/*      */         } else {
/* 1616 */           t.error(this);
/* 1617 */           t.doctypePending.forceQuirks = true;
/* 1618 */           t.advanceTransition(BogusDoctype);
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1624 */   static TokeniserState AfterDoctypePublicKeyword = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1628 */         return "AfterDoctypePublicKeyword";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1632 */         char c = r.consume();
/* 1633 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/* 1639 */             t.transition(BeforeDoctypePublicIdentifier);
/*      */             return;
/*      */           case '"':
/* 1642 */             t.error(this);
/*      */             
/* 1644 */             t.transition(DoctypePublicIdentifier_doubleQuoted);
/*      */             return;
/*      */           case '\'':
/* 1647 */             t.error(this);
/*      */             
/* 1649 */             t.transition(DoctypePublicIdentifier_singleQuoted);
/*      */             return;
/*      */           case '>':
/* 1652 */             t.error(this);
/* 1653 */             t.doctypePending.forceQuirks = true;
/* 1654 */             t.emitDoctypePending();
/* 1655 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 1658 */             t.eofError(this);
/* 1659 */             t.doctypePending.forceQuirks = true;
/* 1660 */             t.emitDoctypePending();
/* 1661 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1664 */         t.error(this);
/* 1665 */         t.doctypePending.forceQuirks = true;
/* 1666 */         t.transition(BogusDoctype);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1671 */   static TokeniserState BeforeDoctypePublicIdentifier = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1675 */         return "BeforeDoctypePublicIdentifier";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1679 */         char c = r.consume();
/* 1680 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             return;
/*      */           
/*      */           case '"':
/* 1689 */             t.transition(DoctypePublicIdentifier_doubleQuoted);
/*      */ 
/*      */           
/*      */           case '\'':
/* 1693 */             t.transition(DoctypePublicIdentifier_singleQuoted);
/*      */           
/*      */           case '>':
/* 1696 */             t.error(this);
/* 1697 */             t.doctypePending.forceQuirks = true;
/* 1698 */             t.emitDoctypePending();
/* 1699 */             t.transition(Data);
/*      */           
/*      */           case '￿':
/* 1702 */             t.eofError(this);
/* 1703 */             t.doctypePending.forceQuirks = true;
/* 1704 */             t.emitDoctypePending();
/* 1705 */             t.transition(Data);
/*      */         } 
/*      */         
/* 1708 */         t.error(this);
/* 1709 */         t.doctypePending.forceQuirks = true;
/* 1710 */         t.transition(BogusDoctype);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1715 */   static TokeniserState DoctypePublicIdentifier_doubleQuoted = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1719 */         return "DoctypePublicIdentifier_doubleQuoted";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1723 */         char c = r.consume();
/* 1724 */         switch (c) {
/*      */           case '"':
/* 1726 */             t.transition(AfterDoctypePublicIdentifier);
/*      */             return;
/*      */           case '\000':
/* 1729 */             t.error(this);
/* 1730 */             t.doctypePending.publicIdentifier.append('�');
/*      */             return;
/*      */           case '>':
/* 1733 */             t.error(this);
/* 1734 */             t.doctypePending.forceQuirks = true;
/* 1735 */             t.emitDoctypePending();
/* 1736 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 1739 */             t.eofError(this);
/* 1740 */             t.doctypePending.forceQuirks = true;
/* 1741 */             t.emitDoctypePending();
/* 1742 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1745 */         t.doctypePending.publicIdentifier.append(c);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1750 */   static TokeniserState DoctypePublicIdentifier_singleQuoted = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1754 */         return "DoctypePublicIdentifier_singleQuoted";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1758 */         char c = r.consume();
/* 1759 */         switch (c) {
/*      */           case '\'':
/* 1761 */             t.transition(AfterDoctypePublicIdentifier);
/*      */             return;
/*      */           case '\000':
/* 1764 */             t.error(this);
/* 1765 */             t.doctypePending.publicIdentifier.append('�');
/*      */             return;
/*      */           case '>':
/* 1768 */             t.error(this);
/* 1769 */             t.doctypePending.forceQuirks = true;
/* 1770 */             t.emitDoctypePending();
/* 1771 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 1774 */             t.eofError(this);
/* 1775 */             t.doctypePending.forceQuirks = true;
/* 1776 */             t.emitDoctypePending();
/* 1777 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1780 */         t.doctypePending.publicIdentifier.append(c);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1785 */   static TokeniserState AfterDoctypePublicIdentifier = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1789 */         return "AfterDoctypePublicIdentifier";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1793 */         char c = r.consume();
/* 1794 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/* 1800 */             t.transition(BetweenDoctypePublicAndSystemIdentifiers);
/*      */             return;
/*      */           case '>':
/* 1803 */             t.emitDoctypePending();
/* 1804 */             t.transition(Data);
/*      */             return;
/*      */           case '"':
/* 1807 */             t.error(this);
/*      */             
/* 1809 */             t.transition(DoctypeSystemIdentifier_doubleQuoted);
/*      */             return;
/*      */           case '\'':
/* 1812 */             t.error(this);
/*      */             
/* 1814 */             t.transition(DoctypeSystemIdentifier_singleQuoted);
/*      */             return;
/*      */           case '￿':
/* 1817 */             t.eofError(this);
/* 1818 */             t.doctypePending.forceQuirks = true;
/* 1819 */             t.emitDoctypePending();
/* 1820 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1823 */         t.error(this);
/* 1824 */         t.doctypePending.forceQuirks = true;
/* 1825 */         t.transition(BogusDoctype);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1830 */   static TokeniserState BetweenDoctypePublicAndSystemIdentifiers = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1834 */         return "BetweenDoctypePublicAndSystemIdentifiers";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1838 */         char c = r.consume();
/* 1839 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             return;
/*      */           case '>':
/* 1847 */             t.emitDoctypePending();
/* 1848 */             t.transition(Data);
/*      */           
/*      */           case '"':
/* 1851 */             t.error(this);
/*      */             
/* 1853 */             t.transition(DoctypeSystemIdentifier_doubleQuoted);
/*      */           
/*      */           case '\'':
/* 1856 */             t.error(this);
/*      */             
/* 1858 */             t.transition(DoctypeSystemIdentifier_singleQuoted);
/*      */           
/*      */           case '￿':
/* 1861 */             t.eofError(this);
/* 1862 */             t.doctypePending.forceQuirks = true;
/* 1863 */             t.emitDoctypePending();
/* 1864 */             t.transition(Data);
/*      */         } 
/*      */         
/* 1867 */         t.error(this);
/* 1868 */         t.doctypePending.forceQuirks = true;
/* 1869 */         t.transition(BogusDoctype);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1874 */   static TokeniserState AfterDoctypeSystemKeyword = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1878 */         return "AfterDoctypeSystemKeyword";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1882 */         char c = r.consume();
/* 1883 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/* 1889 */             t.transition(BeforeDoctypeSystemIdentifier);
/*      */             return;
/*      */           case '>':
/* 1892 */             t.error(this);
/* 1893 */             t.doctypePending.forceQuirks = true;
/* 1894 */             t.emitDoctypePending();
/* 1895 */             t.transition(Data);
/*      */             return;
/*      */           case '"':
/* 1898 */             t.error(this);
/*      */             
/* 1900 */             t.transition(DoctypeSystemIdentifier_doubleQuoted);
/*      */             return;
/*      */           case '\'':
/* 1903 */             t.error(this);
/*      */             
/* 1905 */             t.transition(DoctypeSystemIdentifier_singleQuoted);
/*      */             return;
/*      */           case '￿':
/* 1908 */             t.eofError(this);
/* 1909 */             t.doctypePending.forceQuirks = true;
/* 1910 */             t.emitDoctypePending();
/* 1911 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1914 */         t.error(this);
/* 1915 */         t.doctypePending.forceQuirks = true;
/* 1916 */         t.emitDoctypePending();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1921 */   static TokeniserState BeforeDoctypeSystemIdentifier = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1925 */         return "BeforeDoctypeSystemIdentifier";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1929 */         char c = r.consume();
/* 1930 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             return;
/*      */           
/*      */           case '"':
/* 1939 */             t.transition(DoctypeSystemIdentifier_doubleQuoted);
/*      */ 
/*      */           
/*      */           case '\'':
/* 1943 */             t.transition(DoctypeSystemIdentifier_singleQuoted);
/*      */           
/*      */           case '>':
/* 1946 */             t.error(this);
/* 1947 */             t.doctypePending.forceQuirks = true;
/* 1948 */             t.emitDoctypePending();
/* 1949 */             t.transition(Data);
/*      */           
/*      */           case '￿':
/* 1952 */             t.eofError(this);
/* 1953 */             t.doctypePending.forceQuirks = true;
/* 1954 */             t.emitDoctypePending();
/* 1955 */             t.transition(Data);
/*      */         } 
/*      */         
/* 1958 */         t.error(this);
/* 1959 */         t.doctypePending.forceQuirks = true;
/* 1960 */         t.transition(BogusDoctype);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 1965 */   static TokeniserState DoctypeSystemIdentifier_doubleQuoted = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 1969 */         return "DoctypeSystemIdentifier_doubleQuoted";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 1973 */         char c = r.consume();
/* 1974 */         switch (c) {
/*      */           case '"':
/* 1976 */             t.transition(AfterDoctypeSystemIdentifier);
/*      */             return;
/*      */           case '\000':
/* 1979 */             t.error(this);
/* 1980 */             t.doctypePending.systemIdentifier.append('�');
/*      */             return;
/*      */           case '>':
/* 1983 */             t.error(this);
/* 1984 */             t.doctypePending.forceQuirks = true;
/* 1985 */             t.emitDoctypePending();
/* 1986 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 1989 */             t.eofError(this);
/* 1990 */             t.doctypePending.forceQuirks = true;
/* 1991 */             t.emitDoctypePending();
/* 1992 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 1995 */         t.doctypePending.systemIdentifier.append(c);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 2000 */   static TokeniserState DoctypeSystemIdentifier_singleQuoted = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 2004 */         return "DoctypeSystemIdentifier_singleQuoted";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 2008 */         char c = r.consume();
/* 2009 */         switch (c) {
/*      */           case '\'':
/* 2011 */             t.transition(AfterDoctypeSystemIdentifier);
/*      */             return;
/*      */           case '\000':
/* 2014 */             t.error(this);
/* 2015 */             t.doctypePending.systemIdentifier.append('�');
/*      */             return;
/*      */           case '>':
/* 2018 */             t.error(this);
/* 2019 */             t.doctypePending.forceQuirks = true;
/* 2020 */             t.emitDoctypePending();
/* 2021 */             t.transition(Data);
/*      */             return;
/*      */           case '￿':
/* 2024 */             t.eofError(this);
/* 2025 */             t.doctypePending.forceQuirks = true;
/* 2026 */             t.emitDoctypePending();
/* 2027 */             t.transition(Data);
/*      */             return;
/*      */         } 
/* 2030 */         t.doctypePending.systemIdentifier.append(c);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/* 2035 */   static TokeniserState AfterDoctypeSystemIdentifier = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 2039 */         return "AfterDoctypeSystemIdentifier";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 2043 */         char c = r.consume();
/* 2044 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             return;
/*      */           case '>':
/* 2052 */             t.emitDoctypePending();
/* 2053 */             t.transition(Data);
/*      */           
/*      */           case '￿':
/* 2056 */             t.eofError(this);
/* 2057 */             t.doctypePending.forceQuirks = true;
/* 2058 */             t.emitDoctypePending();
/* 2059 */             t.transition(Data);
/*      */         } 
/*      */         
/* 2062 */         t.error(this);
/* 2063 */         t.transition(BogusDoctype);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/* 2069 */   static TokeniserState BogusDoctype = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 2073 */         return "BogusDoctype";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 2077 */         char c = r.consume();
/* 2078 */         switch (c) {
/*      */           case '>':
/* 2080 */             t.emitDoctypePending();
/* 2081 */             t.transition(Data);
/*      */             break;
/*      */           case '￿':
/* 2084 */             t.emitDoctypePending();
/* 2085 */             t.transition(Data);
/*      */             break;
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2094 */   static TokeniserState CdataSection = new TokeniserState()
/*      */     {
/*      */       String getName()
/*      */       {
/* 2098 */         return "CdataSection";
/*      */       }
/*      */       
/*      */       void read(Tokeniser t, CharacterReader r) {
/* 2102 */         String data = r.consumeTo("]]>");
/* 2103 */         t.emit(data);
/* 2104 */         r.matchConsume("]]>");
/* 2105 */         t.transition(Data);
/*      */       }
/*      */     };
/*      */   static final char nullChar = '\000';
/*      */   
/*      */   public String toString() {
/* 2111 */     return getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2121 */   private static final char[] attributeSingleValueCharsSorted = new char[] { '\'', '&', Character.MIN_VALUE };
/* 2122 */   private static final char[] attributeDoubleValueCharsSorted = new char[] { '"', '&', Character.MIN_VALUE };
/* 2123 */   private static final char[] attributeNameCharsSorted = new char[] { '\t', '\n', '\r', '\f', ' ', '/', '=', '>', Character.MIN_VALUE, '"', '\'', '<' };
/* 2124 */   private static final char[] attributeValueUnquoted = new char[] { '\t', '\n', '\r', '\f', ' ', '&', '>', Character.MIN_VALUE, '"', '\'', '<', '=', '`' };
/*      */   
/*      */   private static final char replacementChar = '�';
/* 2127 */   private static final String replacementStr = String.valueOf('�');
/*      */   private static final char eof = '￿';
/*      */   
/*      */   static {
/* 2131 */     Arrays.sort(attributeSingleValueCharsSorted);
/* 2132 */     Arrays.sort(attributeDoubleValueCharsSorted);
/* 2133 */     Arrays.sort(attributeNameCharsSorted);
/* 2134 */     Arrays.sort(attributeValueUnquoted);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void handleDataEndTag(Tokeniser t, CharacterReader r, TokeniserState elseTransition) {
/* 2142 */     if (r.matchesLetter()) {
/* 2143 */       String name = r.consumeLetterSequence();
/* 2144 */       t.tagPending.appendTagName(name.toLowerCase());
/* 2145 */       t.dataBuffer.append(name);
/*      */       
/*      */       return;
/*      */     } 
/* 2149 */     boolean needsExitTransition = false;
/* 2150 */     if (t.isAppropriateEndTagToken() && !r.isEmpty()) {
/* 2151 */       char c = r.consume();
/* 2152 */       switch (c) {
/*      */         case '\t':
/*      */         case '\n':
/*      */         case '\f':
/*      */         case '\r':
/*      */         case ' ':
/* 2158 */           t.transition(BeforeAttributeName);
/*      */           break;
/*      */         case '/':
/* 2161 */           t.transition(SelfClosingStartTag);
/*      */           break;
/*      */         case '>':
/* 2164 */           t.emitTagPending();
/* 2165 */           t.transition(Data);
/*      */           break;
/*      */         default:
/* 2168 */           t.dataBuffer.append(c);
/* 2169 */           needsExitTransition = true; break;
/*      */       } 
/*      */     } else {
/* 2172 */       needsExitTransition = true;
/*      */     } 
/*      */     
/* 2175 */     if (needsExitTransition) {
/* 2176 */       t.emit("</" + t.dataBuffer.toString());
/* 2177 */       t.transition(elseTransition);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void readData(Tokeniser t, CharacterReader r, TokeniserState current, TokeniserState advance) {
/* 2182 */     switch (r.current()) {
/*      */       case '<':
/* 2184 */         t.advanceTransition(advance);
/*      */         return;
/*      */       case '\000':
/* 2187 */         t.error(current);
/* 2188 */         r.advance();
/* 2189 */         t.emit('�');
/*      */         return;
/*      */       case '￿':
/* 2192 */         t.emit(new Token.EOF());
/*      */         return;
/*      */     } 
/* 2195 */     String data = r.consumeToAny(new char[] { '<', Character.MIN_VALUE });
/* 2196 */     t.emit(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readCharRef(Tokeniser t, TokeniserState advance) {
/* 2202 */     char[] c = t.consumeCharacterReference(null, false);
/* 2203 */     if (c == null) {
/* 2204 */       t.emit('&');
/*      */     } else {
/* 2206 */       t.emit(c);
/* 2207 */     }  t.transition(advance);
/*      */   }
/*      */   
/*      */   private static void readEndTag(Tokeniser t, CharacterReader r, TokeniserState a, TokeniserState b) {
/* 2211 */     if (r.matchesLetter()) {
/* 2212 */       t.createTagPending(false);
/* 2213 */       t.transition(a);
/*      */     } else {
/* 2215 */       t.emit("</");
/* 2216 */       t.transition(b);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void handleDataDoubleEscapeTag(Tokeniser t, CharacterReader r, TokeniserState primary, TokeniserState fallback) {
/* 2221 */     if (r.matchesLetter()) {
/* 2222 */       String name = r.consumeLetterSequence();
/* 2223 */       t.dataBuffer.append(name.toLowerCase());
/* 2224 */       t.emit(name);
/*      */       
/*      */       return;
/*      */     } 
/* 2228 */     char c = r.consume();
/* 2229 */     switch (c) {
/*      */       case '\t':
/*      */       case '\n':
/*      */       case '\f':
/*      */       case '\r':
/*      */       case ' ':
/*      */       case '/':
/*      */       case '>':
/* 2237 */         if (t.dataBuffer.toString().equals("script")) {
/* 2238 */           t.transition(primary);
/*      */         } else {
/* 2240 */           t.transition(fallback);
/* 2241 */         }  t.emit(c);
/*      */         return;
/*      */     } 
/* 2244 */     r.unconsume();
/* 2245 */     t.transition(fallback);
/*      */   }
/*      */   
/*      */   abstract String getName();
/*      */   
/*      */   abstract void read(Tokeniser paramTokeniser, CharacterReader paramCharacterReader);
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/TokeniserState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */