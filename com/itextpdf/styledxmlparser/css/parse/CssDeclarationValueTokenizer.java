/*     */ package com.itextpdf.styledxmlparser.css.parse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssDeclarationValueTokenizer
/*     */ {
/*     */   private String src;
/*  54 */   private int index = -1;
/*     */ 
/*     */   
/*     */   private char stringQuote;
/*     */ 
/*     */   
/*     */   private boolean inString;
/*     */ 
/*     */   
/*  63 */   private int functionDepth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CssDeclarationValueTokenizer(String propertyValue) {
/*  71 */     this.src = propertyValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Token getNextValidToken() {
/*  80 */     Token token = getNextToken();
/*  81 */     while (token != null && !token.isString() && token.getValue().trim().isEmpty()) {
/*  82 */       token = getNextToken();
/*     */     }
/*  84 */     if (token != null && this.functionDepth > 0) {
/*  85 */       StringBuilder functionBuffer = new StringBuilder();
/*  86 */       while (token != null && this.functionDepth > 0) {
/*  87 */         processFunctionToken(token, functionBuffer);
/*  88 */         token = getNextToken();
/*     */       } 
/*  90 */       this.functionDepth = 0;
/*  91 */       if (functionBuffer.length() != 0) {
/*  92 */         if (token != null) {
/*  93 */           processFunctionToken(token, functionBuffer);
/*     */         }
/*  95 */         return new Token(functionBuffer.toString(), TokenType.FUNCTION);
/*     */       } 
/*     */     } 
/*  98 */     return token;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Token getNextToken() {
/* 107 */     StringBuilder buff = new StringBuilder();
/*     */     
/* 109 */     if (this.index >= this.src.length() - 1) {
/* 110 */       return null;
/*     */     }
/* 112 */     if (this.inString) {
/* 113 */       boolean isEscaped = false;
/* 114 */       StringBuilder pendingUnicodeSequence = new StringBuilder();
/* 115 */       while (++this.index < this.src.length()) {
/* 116 */         char curChar = this.src.charAt(this.index);
/* 117 */         if (isEscaped) {
/* 118 */           if (isHexDigit(curChar) && pendingUnicodeSequence.length() < 6) {
/* 119 */             pendingUnicodeSequence.append(curChar); continue;
/* 120 */           }  if (pendingUnicodeSequence.length() != 0) {
/* 121 */             int codePoint = Integer.parseInt(pendingUnicodeSequence.toString(), 16);
/* 122 */             if (Character.isValidCodePoint(codePoint)) {
/* 123 */               buff.appendCodePoint(codePoint);
/*     */             } else {
/* 125 */               buff.append("�");
/*     */             } 
/* 127 */             pendingUnicodeSequence.setLength(0);
/* 128 */             if (curChar == this.stringQuote) {
/* 129 */               this.inString = false;
/* 130 */               return new Token(buff.toString(), TokenType.STRING);
/* 131 */             }  if (!Character.isWhitespace(curChar)) {
/* 132 */               buff.append(curChar);
/*     */             }
/* 134 */             isEscaped = false; continue;
/*     */           } 
/* 136 */           buff.append(curChar);
/* 137 */           isEscaped = false; continue;
/*     */         } 
/* 139 */         if (curChar == this.stringQuote) {
/* 140 */           this.inString = false;
/* 141 */           return new Token(buff.toString(), TokenType.STRING);
/* 142 */         }  if (curChar == '\\') {
/* 143 */           isEscaped = true; continue;
/*     */         } 
/* 145 */         buff.append(curChar);
/*     */       } 
/*     */     } else {
/*     */       
/* 149 */       while (++this.index < this.src.length()) {
/* 150 */         char curChar = this.src.charAt(this.index);
/* 151 */         if (curChar == '(') {
/* 152 */           this.functionDepth++;
/* 153 */           buff.append(curChar); continue;
/* 154 */         }  if (curChar == ')') {
/* 155 */           this.functionDepth--;
/* 156 */           buff.append(curChar);
/* 157 */           if (this.functionDepth == 0)
/* 158 */             return new Token(buff.toString(), TokenType.FUNCTION);  continue;
/*     */         } 
/* 160 */         if (curChar == '"' || curChar == '\'') {
/* 161 */           this.stringQuote = curChar;
/* 162 */           this.inString = true;
/* 163 */           return new Token(buff.toString(), TokenType.FUNCTION);
/* 164 */         }  if (curChar == ',' && !this.inString && this.functionDepth == 0) {
/* 165 */           if (buff.length() == 0) {
/* 166 */             return new Token(",", TokenType.COMMA);
/*     */           }
/* 168 */           this.index--;
/* 169 */           return new Token(buff.toString(), TokenType.UNKNOWN);
/*     */         } 
/* 171 */         if (Character.isWhitespace(curChar)) {
/* 172 */           if (this.functionDepth > 0) {
/* 173 */             buff.append(curChar);
/*     */           }
/* 175 */           return new Token(buff.toString(), (this.functionDepth > 0) ? TokenType.FUNCTION : TokenType.UNKNOWN);
/*     */         } 
/* 177 */         buff.append(curChar);
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     return new Token(buff.toString(), TokenType.FUNCTION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isHexDigit(char c) {
/* 191 */     return (('/' < c && c < ':') || ('@' < c && c < 'G') || ('`' < c && c < 'g'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processFunctionToken(Token token, StringBuilder functionBuffer) {
/* 201 */     if (token.isString()) {
/* 202 */       functionBuffer.append(this.stringQuote);
/* 203 */       functionBuffer.append(token.getValue());
/* 204 */       functionBuffer.append(this.stringQuote);
/*     */     } else {
/* 206 */       functionBuffer.append(token.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Token
/*     */   {
/*     */     private String value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private CssDeclarationValueTokenizer.TokenType type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Token(String value, CssDeclarationValueTokenizer.TokenType type) {
/* 228 */       this.value = value;
/* 229 */       this.type = type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 238 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CssDeclarationValueTokenizer.TokenType getType() {
/* 247 */       return this.type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isString() {
/* 256 */       return (this.type == CssDeclarationValueTokenizer.TokenType.STRING);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 264 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum TokenType
/*     */   {
/* 274 */     STRING,
/*     */ 
/*     */     
/* 277 */     FUNCTION,
/*     */ 
/*     */     
/* 280 */     COMMA,
/*     */ 
/*     */     
/* 283 */     UNKNOWN;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/parse/CssDeclarationValueTokenizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */