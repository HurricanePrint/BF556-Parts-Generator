/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Entities;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Tokeniser
/*     */ {
/*     */   static final char replacementChar = '�';
/*  58 */   private static final char[] notCharRefCharsSorted = new char[] { '\t', '\n', '\r', '\f', ' ', '<', '&' }; private CharacterReader reader; private ParseErrorList errors;
/*     */   
/*     */   static {
/*  61 */     Arrays.sort(notCharRefCharsSorted);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private TokeniserState state = TokeniserState.Data;
/*     */   private Token emitPending;
/*     */   private boolean isEmitPending = false;
/*  70 */   private String charsString = null;
/*  71 */   private StringBuilder charsBuilder = new StringBuilder(1024);
/*  72 */   StringBuilder dataBuffer = new StringBuilder(1024);
/*     */   
/*     */   Token.Tag tagPending;
/*  75 */   Token.StartTag startPending = new Token.StartTag();
/*  76 */   Token.EndTag endPending = new Token.EndTag();
/*  77 */   Token.Character charPending = new Token.Character();
/*  78 */   Token.Doctype doctypePending = new Token.Doctype();
/*  79 */   Token.Comment commentPending = new Token.Comment();
/*     */   
/*     */   private String lastStartTag;
/*     */   
/*     */   private boolean selfClosingFlagAcknowledged = true;
/*     */   
/*     */   private final char[] charRefHolder;
/*     */ 
/*     */   
/*     */   Token read() {
/*  89 */     if (!this.selfClosingFlagAcknowledged) {
/*  90 */       error("Self closing flag not acknowledged");
/*  91 */       this.selfClosingFlagAcknowledged = true;
/*     */     } 
/*     */     
/*  94 */     while (!this.isEmitPending) {
/*  95 */       this.state.read(this, this.reader);
/*     */     }
/*     */     
/*  98 */     if (this.charsBuilder.length() > 0) {
/*  99 */       String str = this.charsBuilder.toString();
/* 100 */       this.charsBuilder.delete(0, this.charsBuilder.length());
/* 101 */       this.charsString = null;
/* 102 */       return this.charPending.data(str);
/* 103 */     }  if (this.charsString != null) {
/* 104 */       Token token = this.charPending.data(this.charsString);
/* 105 */       this.charsString = null;
/* 106 */       return token;
/*     */     } 
/* 108 */     this.isEmitPending = false;
/* 109 */     return this.emitPending;
/*     */   }
/*     */ 
/*     */   
/*     */   void emit(Token token) {
/* 114 */     Validate.isFalse(this.isEmitPending, "There is an unread token pending!");
/*     */     
/* 116 */     this.emitPending = token;
/* 117 */     this.isEmitPending = true;
/*     */     
/* 119 */     if (token.type == Token.TokenType.StartTag) {
/* 120 */       Token.StartTag startTag = (Token.StartTag)token;
/* 121 */       this.lastStartTag = startTag.tagName;
/* 122 */       if (startTag.selfClosing)
/* 123 */         this.selfClosingFlagAcknowledged = false; 
/* 124 */     } else if (token.type == Token.TokenType.EndTag) {
/* 125 */       Token.EndTag endTag = (Token.EndTag)token;
/* 126 */       if (endTag.attributes != null) {
/* 127 */         error("Attributes incorrectly present on end tag");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void emit(String str) {
/* 134 */     if (this.charsString == null) {
/* 135 */       this.charsString = str;
/*     */     } else {
/*     */       
/* 138 */       if (this.charsBuilder.length() == 0) {
/* 139 */         this.charsBuilder.append(this.charsString);
/*     */       }
/* 141 */       this.charsBuilder.append(str);
/*     */     } 
/*     */   }
/*     */   
/*     */   void emit(char[] chars) {
/* 146 */     emit(String.valueOf(chars));
/*     */   }
/*     */   
/*     */   void emit(char c) {
/* 150 */     emit(String.valueOf(c));
/*     */   }
/*     */   
/*     */   TokeniserState getState() {
/* 154 */     return this.state;
/*     */   }
/*     */   
/*     */   void transition(TokeniserState state) {
/* 158 */     this.state = state;
/*     */   }
/*     */   
/*     */   void advanceTransition(TokeniserState state) {
/* 162 */     this.reader.advance();
/* 163 */     this.state = state;
/*     */   }
/*     */   
/*     */   void acknowledgeSelfClosingFlag() {
/* 167 */     this.selfClosingFlagAcknowledged = true;
/*     */   }
/*     */   
/* 170 */   Tokeniser(CharacterReader reader, ParseErrorList errors) { this.charRefHolder = new char[1];
/*     */     this.reader = reader;
/* 172 */     this.errors = errors; } char[] consumeCharacterReference(Character additionalAllowedCharacter, boolean inAttribute) { if (this.reader.isEmpty())
/* 173 */       return null; 
/* 174 */     if (additionalAllowedCharacter != null && additionalAllowedCharacter.charValue() == this.reader.current())
/* 175 */       return null; 
/* 176 */     if (this.reader.matchesAnySorted(notCharRefCharsSorted)) {
/* 177 */       return null;
/*     */     }
/* 179 */     char[] charRef = this.charRefHolder;
/* 180 */     this.reader.mark();
/* 181 */     if (this.reader.matchConsume("#")) {
/* 182 */       boolean isHexMode = this.reader.matchConsumeIgnoreCase("X");
/* 183 */       String numRef = isHexMode ? this.reader.consumeHexSequence() : this.reader.consumeDigitSequence();
/* 184 */       if (numRef.length() == 0) {
/* 185 */         characterReferenceError("numeric reference with no numerals");
/* 186 */         this.reader.rewindToMark();
/* 187 */         return null;
/*     */       } 
/* 189 */       if (!this.reader.matchConsume(";"))
/* 190 */         characterReferenceError("missing semicolon"); 
/* 191 */       int charval = -1;
/*     */       try {
/* 193 */         int base = isHexMode ? 16 : 10;
/* 194 */         charval = Integer.valueOf(numRef, base).intValue();
/* 195 */       } catch (NumberFormatException numberFormatException) {}
/*     */       
/* 197 */       if (charval == -1 || (charval >= 55296 && charval <= 57343) || charval > 1114111) {
/* 198 */         characterReferenceError("character outside of valid range");
/* 199 */         charRef[0] = '�';
/* 200 */         return charRef;
/*     */       } 
/*     */ 
/*     */       
/* 204 */       if (charval < 65536) {
/* 205 */         charRef[0] = (char)charval;
/* 206 */         return charRef;
/*     */       } 
/* 208 */       return Character.toChars(charval);
/*     */     } 
/*     */ 
/*     */     
/* 212 */     String nameRef = this.reader.consumeLetterThenDigitSequence();
/* 213 */     boolean looksLegit = this.reader.matches(';');
/*     */     
/* 215 */     boolean found = (Entities.isBaseNamedEntity(nameRef) || (Entities.isNamedEntity(nameRef) && looksLegit));
/*     */     
/* 217 */     if (!found) {
/* 218 */       this.reader.rewindToMark();
/* 219 */       if (looksLegit)
/* 220 */         characterReferenceError(MessageFormatUtil.format("invalid named referenece ''{0}''", new Object[] { nameRef })); 
/* 221 */       return null;
/*     */     } 
/* 223 */     if (inAttribute && (this.reader.matchesLetter() || this.reader.matchesDigit() || this.reader.matchesAny(new char[] { '=', '-', '_' }))) {
/*     */       
/* 225 */       this.reader.rewindToMark();
/* 226 */       return null;
/*     */     } 
/* 228 */     if (!this.reader.matchConsume(";"))
/* 229 */       characterReferenceError("missing semicolon"); 
/* 230 */     charRef[0] = Entities.getCharacterByName(nameRef).charValue();
/* 231 */     return charRef; }
/*     */ 
/*     */ 
/*     */   
/*     */   Token.Tag createTagPending(boolean start) {
/* 236 */     this.tagPending = start ? (Token.Tag)this.startPending.reset() : (Token.Tag)this.endPending.reset();
/* 237 */     return this.tagPending;
/*     */   }
/*     */   
/*     */   void emitTagPending() {
/* 241 */     this.tagPending.finaliseTag();
/* 242 */     emit(this.tagPending);
/*     */   }
/*     */   
/*     */   void createCommentPending() {
/* 246 */     this.commentPending.reset();
/*     */   }
/*     */   
/*     */   void emitCommentPending() {
/* 250 */     emit(this.commentPending);
/*     */   }
/*     */   
/*     */   void createDoctypePending() {
/* 254 */     this.doctypePending.reset();
/*     */   }
/*     */   
/*     */   void emitDoctypePending() {
/* 258 */     emit(this.doctypePending);
/*     */   }
/*     */   
/*     */   void createTempBuffer() {
/* 262 */     Token.reset(this.dataBuffer);
/*     */   }
/*     */   
/*     */   boolean isAppropriateEndTagToken() {
/* 266 */     return (this.lastStartTag != null && this.tagPending.tagName.equals(this.lastStartTag));
/*     */   }
/*     */   
/*     */   String appropriateEndTagName() {
/* 270 */     if (this.lastStartTag == null)
/* 271 */       return null; 
/* 272 */     return this.lastStartTag;
/*     */   }
/*     */   
/*     */   void error(TokeniserState state) {
/* 276 */     if (this.errors.canAddError())
/* 277 */       this.errors.add(new ParseError(this.reader.pos(), "Unexpected character ''{0}'' in input state [{}]", new Object[] { Character.valueOf(this.reader.current()), state })); 
/*     */   }
/*     */   
/*     */   void eofError(TokeniserState state) {
/* 281 */     if (this.errors.canAddError())
/* 282 */       this.errors.add(new ParseError(this.reader.pos(), "Unexpectedly reached end of file (EOF) in input state [{0}]", new Object[] { state })); 
/*     */   }
/*     */   
/*     */   private void characterReferenceError(String message) {
/* 286 */     if (this.errors.canAddError())
/* 287 */       this.errors.add(new ParseError(this.reader.pos(), "Invalid character reference: {0}", new Object[] { message })); 
/*     */   }
/*     */   
/*     */   private void error(String errorMsg) {
/* 291 */     if (this.errors.canAddError()) {
/* 292 */       this.errors.add(new ParseError(this.reader.pos(), errorMsg));
/*     */     }
/*     */   }
/*     */   
/*     */   boolean currentNodeInHtmlNS() {
/* 297 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String unescapeEntities(boolean inAttribute) {
/* 308 */     StringBuilder builder = new StringBuilder();
/* 309 */     while (!this.reader.isEmpty()) {
/* 310 */       builder.append(this.reader.consumeTo('&'));
/* 311 */       if (this.reader.matches('&')) {
/* 312 */         this.reader.consume();
/* 313 */         char[] c = consumeCharacterReference(null, inAttribute);
/* 314 */         if (c == null || c.length == 0) {
/* 315 */           builder.append('&'); continue;
/*     */         } 
/* 317 */         builder.append(c);
/*     */       } 
/*     */     } 
/* 320 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/Tokeniser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */