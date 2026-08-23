/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.BooleanAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class Token
/*     */ {
/*     */   TokenType type;
/*     */   
/*     */   private Token() {}
/*     */   
/*     */   String tokenType() {
/*  60 */     return getClass().getSimpleName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void reset(StringBuilder sb) {
/*  70 */     if (sb != null)
/*  71 */       sb.delete(0, sb.length()); 
/*     */   }
/*     */   
/*     */   static final class Doctype
/*     */     extends Token {
/*  76 */     final StringBuilder name = new StringBuilder();
/*  77 */     final StringBuilder publicIdentifier = new StringBuilder();
/*  78 */     final StringBuilder systemIdentifier = new StringBuilder();
/*     */     boolean forceQuirks = false;
/*     */     
/*     */     Doctype() {
/*  82 */       this.type = Token.TokenType.Doctype;
/*     */     }
/*     */ 
/*     */     
/*     */     Token reset() {
/*  87 */       reset(this.name);
/*  88 */       reset(this.publicIdentifier);
/*  89 */       reset(this.systemIdentifier);
/*  90 */       this.forceQuirks = false;
/*  91 */       return this;
/*     */     }
/*     */     
/*     */     String getName() {
/*  95 */       return this.name.toString();
/*     */     }
/*     */     
/*     */     String getPublicIdentifier() {
/*  99 */       return this.publicIdentifier.toString();
/*     */     }
/*     */     
/*     */     public String getSystemIdentifier() {
/* 103 */       return this.systemIdentifier.toString();
/*     */     }
/*     */     
/*     */     public boolean isForceQuirks() {
/* 107 */       return this.forceQuirks;
/*     */     } }
/*     */   static abstract class Tag extends Token { protected String tagName; private String pendingAttributeName;
/*     */     private StringBuilder pendingAttributeValue;
/*     */     private String pendingAttributeValueS;
/*     */     
/*     */     Tag() {
/* 114 */       this.pendingAttributeValue = new StringBuilder();
/*     */       
/* 116 */       this.hasEmptyAttributeValue = false;
/* 117 */       this.hasPendingAttributeValue = false;
/* 118 */       this.selfClosing = false;
/*     */     }
/*     */     private boolean hasEmptyAttributeValue; private boolean hasPendingAttributeValue; boolean selfClosing; Attributes attributes;
/*     */     
/*     */     Token reset() {
/* 123 */       this.tagName = null;
/* 124 */       this.pendingAttributeName = null;
/* 125 */       reset(this.pendingAttributeValue);
/* 126 */       this.pendingAttributeValueS = null;
/* 127 */       this.hasEmptyAttributeValue = false;
/* 128 */       this.hasPendingAttributeValue = false;
/* 129 */       this.selfClosing = false;
/* 130 */       this.attributes = null;
/* 131 */       return this;
/*     */     }
/*     */     
/*     */     final void newAttribute() {
/* 135 */       if (this.attributes == null) {
/* 136 */         this.attributes = new Attributes();
/*     */       }
/* 138 */       if (this.pendingAttributeName != null) {
/*     */         BooleanAttribute booleanAttribute;
/* 140 */         if (this.hasPendingAttributeValue) {
/*     */           
/* 142 */           Attribute attribute = new Attribute(this.pendingAttributeName, (this.pendingAttributeValue.length() > 0) ? this.pendingAttributeValue.toString() : this.pendingAttributeValueS);
/* 143 */         } else if (this.hasEmptyAttributeValue) {
/* 144 */           Attribute attribute = new Attribute(this.pendingAttributeName, "");
/*     */         } else {
/* 146 */           booleanAttribute = new BooleanAttribute(this.pendingAttributeName);
/* 147 */         }  this.attributes.put((Attribute)booleanAttribute);
/*     */       } 
/* 149 */       this.pendingAttributeName = null;
/* 150 */       this.hasEmptyAttributeValue = false;
/* 151 */       this.hasPendingAttributeValue = false;
/* 152 */       reset(this.pendingAttributeValue);
/* 153 */       this.pendingAttributeValueS = null;
/*     */     }
/*     */ 
/*     */     
/*     */     final void finaliseTag() {
/* 158 */       if (this.pendingAttributeName != null)
/*     */       {
/* 160 */         newAttribute();
/*     */       }
/*     */     }
/*     */     
/*     */     final String name() {
/* 165 */       Validate.isFalse((this.tagName == null || this.tagName.length() == 0));
/* 166 */       return this.tagName;
/*     */     }
/*     */     
/*     */     final Tag name(String name) {
/* 170 */       this.tagName = name;
/* 171 */       return this;
/*     */     }
/*     */     
/*     */     final boolean isSelfClosing() {
/* 175 */       return this.selfClosing;
/*     */     }
/*     */ 
/*     */     
/*     */     final Attributes getAttributes() {
/* 180 */       return this.attributes;
/*     */     }
/*     */ 
/*     */     
/*     */     final void appendTagName(String append) {
/* 185 */       this.tagName = (this.tagName == null) ? append : (this.tagName + append);
/*     */     }
/*     */     
/*     */     final void appendTagName(char append) {
/* 189 */       appendTagName(String.valueOf(append));
/*     */     }
/*     */     
/*     */     final void appendAttributeName(String append) {
/* 193 */       this.pendingAttributeName = (this.pendingAttributeName == null) ? append : (this.pendingAttributeName + append);
/*     */     }
/*     */     
/*     */     final void appendAttributeName(char append) {
/* 197 */       appendAttributeName(String.valueOf(append));
/*     */     }
/*     */     
/*     */     final void appendAttributeValue(String append) {
/* 201 */       ensureAttributeValue();
/* 202 */       if (this.pendingAttributeValue.length() == 0) {
/* 203 */         this.pendingAttributeValueS = append;
/*     */       } else {
/* 205 */         this.pendingAttributeValue.append(append);
/*     */       } 
/*     */     }
/*     */     
/*     */     final void appendAttributeValue(char append) {
/* 210 */       ensureAttributeValue();
/* 211 */       this.pendingAttributeValue.append(append);
/*     */     }
/*     */     
/*     */     final void appendAttributeValue(char[] append) {
/* 215 */       ensureAttributeValue();
/* 216 */       this.pendingAttributeValue.append(append);
/*     */     }
/*     */     
/*     */     final void setEmptyAttributeValue() {
/* 220 */       this.hasEmptyAttributeValue = true;
/*     */     }
/*     */     
/*     */     private void ensureAttributeValue() {
/* 224 */       this.hasPendingAttributeValue = true;
/*     */       
/* 226 */       if (this.pendingAttributeValueS != null) {
/* 227 */         this.pendingAttributeValue.append(this.pendingAttributeValueS);
/* 228 */         this.pendingAttributeValueS = null;
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class StartTag
/*     */     extends Tag
/*     */   {
/*     */     Token reset() {
/* 242 */       super.reset();
/* 243 */       this.attributes = new Attributes();
/*     */       
/* 245 */       return this;
/*     */     }
/*     */     
/*     */     StartTag nameAttr(String name, Attributes attributes) {
/* 249 */       this.tagName = name;
/* 250 */       this.attributes = attributes;
/* 251 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 256 */       if (this.attributes != null && this.attributes.size() > 0) {
/* 257 */         return "<" + name() + " " + this.attributes.toString() + ">";
/*     */       }
/* 259 */       return "<" + name() + ">";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class EndTag
/*     */     extends Tag
/*     */   {
/*     */     public String toString() {
/* 271 */       return "</" + name() + ">";
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Comment extends Token {
/* 276 */     final StringBuilder data = new StringBuilder();
/*     */     
/*     */     boolean bogus = false;
/*     */     
/*     */     Token reset() {
/* 281 */       reset(this.data);
/* 282 */       this.bogus = false;
/* 283 */       return this;
/*     */     }
/*     */     
/*     */     Comment() {
/* 287 */       this.type = Token.TokenType.Comment;
/*     */     }
/*     */     
/*     */     String getData() {
/* 291 */       return this.data.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 296 */       return "<!--" + getData() + "-->";
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Character
/*     */     extends Token {
/*     */     private String data;
/*     */     
/*     */     Character() {
/* 305 */       this.type = Token.TokenType.Character;
/*     */     }
/*     */ 
/*     */     
/*     */     Token reset() {
/* 310 */       this.data = null;
/* 311 */       return this;
/*     */     }
/*     */     
/*     */     Character data(String data) {
/* 315 */       this.data = data;
/* 316 */       return this;
/*     */     }
/*     */     
/*     */     String getData() {
/* 320 */       return this.data;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 325 */       return getData();
/*     */     }
/*     */   }
/*     */   
/*     */   static final class EOF extends Token {
/*     */     EOF() {
/* 331 */       this.type = Token.TokenType.EOF;
/*     */     }
/*     */ 
/*     */     
/*     */     Token reset() {
/* 336 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   final boolean isDoctype() {
/* 341 */     return (this.type == TokenType.Doctype);
/*     */   }
/*     */   
/*     */   final Doctype asDoctype() {
/* 345 */     return (Doctype)this;
/*     */   }
/*     */   
/*     */   final boolean isStartTag() {
/* 349 */     return (this.type == TokenType.StartTag);
/*     */   }
/*     */   
/*     */   final StartTag asStartTag() {
/* 353 */     return (StartTag)this;
/*     */   }
/*     */   
/*     */   final boolean isEndTag() {
/* 357 */     return (this.type == TokenType.EndTag);
/*     */   }
/*     */   
/*     */   final EndTag asEndTag() {
/* 361 */     return (EndTag)this;
/*     */   }
/*     */   
/*     */   final boolean isComment() {
/* 365 */     return (this.type == TokenType.Comment);
/*     */   }
/*     */   
/*     */   final Comment asComment() {
/* 369 */     return (Comment)this;
/*     */   }
/*     */   
/*     */   final boolean isCharacter() {
/* 373 */     return (this.type == TokenType.Character);
/*     */   }
/*     */   
/*     */   final Character asCharacter() {
/* 377 */     return (Character)this;
/*     */   }
/*     */   
/*     */   final boolean isEOF() {
/* 381 */     return (this.type == TokenType.EOF);
/*     */   }
/*     */   abstract Token reset();
/*     */   
/* 385 */   enum TokenType { Doctype,
/* 386 */     StartTag,
/* 387 */     EndTag,
/* 388 */     Comment,
/* 389 */     Character,
/* 390 */     EOF; }
/*     */ 
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/Token.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */