/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TokenQueue
/*     */ {
/*     */   private String queue;
/*  55 */   private int pos = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final char ESC = '\\';
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenQueue(String data) {
/*  64 */     Validate.notNull(data);
/*  65 */     this.queue = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  73 */     return (remainingLength() == 0);
/*     */   }
/*     */   
/*     */   private int remainingLength() {
/*  77 */     return this.queue.length() - this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char peek() {
/*  85 */     return isEmpty() ? Character.MIN_VALUE : this.queue.charAt(this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFirst(Character c) {
/*  93 */     addFirst(c.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFirst(String seq) {
/* 102 */     this.queue = seq + this.queue.substring(this.pos);
/* 103 */     this.pos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(String seq) {
/* 112 */     return this.queue.regionMatches(true, this.pos, seq, 0, seq.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesCS(String seq) {
/* 121 */     return this.queue.startsWith(seq, this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesAny(String... seq) {
/* 131 */     for (String s : seq) {
/* 132 */       if (matches(s))
/* 133 */         return true; 
/*     */     } 
/* 135 */     return false;
/*     */   }
/*     */   
/*     */   public boolean matchesAny(char... seq) {
/* 139 */     if (isEmpty()) {
/* 140 */       return false;
/*     */     }
/* 142 */     for (char c : seq) {
/* 143 */       if (this.queue.charAt(this.pos) == c)
/* 144 */         return true; 
/*     */     } 
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesStartTag() {
/* 151 */     return (remainingLength() >= 2 && this.queue.charAt(this.pos) == '<' && Character.isLetter(this.queue.charAt(this.pos + 1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchChomp(String seq) {
/* 161 */     if (matches(seq)) {
/* 162 */       this.pos += seq.length();
/* 163 */       return true;
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesWhitespace() {
/* 174 */     return (!isEmpty() && StringUtil.isWhitespace(this.queue.charAt(this.pos)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesWord() {
/* 182 */     return (!isEmpty() && Character.isLetterOrDigit(this.queue.charAt(this.pos)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void advance() {
/* 189 */     if (!isEmpty()) this.pos++;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char consume() {
/* 197 */     return this.queue.charAt(this.pos++);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void consume(String seq) {
/* 208 */     if (!matches(seq))
/* 209 */       throw new IllegalStateException("Queue did not match expected sequence"); 
/* 210 */     int len = seq.length();
/* 211 */     if (len > remainingLength()) {
/* 212 */       throw new IllegalStateException("Queue not long enough to consume sequence");
/*     */     }
/* 214 */     this.pos += len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String consumeTo(String seq) {
/* 223 */     int offset = this.queue.indexOf(seq, this.pos);
/* 224 */     if (offset != -1) {
/* 225 */       String consumed = this.queue.substring(this.pos, offset);
/* 226 */       this.pos += consumed.length();
/* 227 */       return consumed;
/*     */     } 
/* 229 */     return remainder();
/*     */   }
/*     */ 
/*     */   
/*     */   public String consumeToIgnoreCase(String seq) {
/* 234 */     int start = this.pos;
/* 235 */     String first = seq.substring(0, 1);
/* 236 */     boolean canScan = first.toLowerCase().equals(first.toUpperCase());
/* 237 */     while (!isEmpty() && 
/* 238 */       !matches(seq)) {
/*     */ 
/*     */       
/* 241 */       if (canScan) {
/* 242 */         int skip = this.queue.indexOf(first, this.pos) - this.pos;
/* 243 */         if (skip == 0) {
/* 244 */           this.pos++; continue;
/* 245 */         }  if (skip < 0) {
/* 246 */           this.pos = this.queue.length(); continue;
/*     */         } 
/* 248 */         this.pos += skip;
/*     */         continue;
/*     */       } 
/* 251 */       this.pos++;
/*     */     } 
/*     */     
/* 254 */     return this.queue.substring(start, this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String consumeToAny(String... seq) {
/* 265 */     int start = this.pos;
/* 266 */     while (!isEmpty() && !matchesAny(seq)) {
/* 267 */       this.pos++;
/*     */     }
/*     */     
/* 270 */     return this.queue.substring(start, this.pos);
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
/*     */   public String chompTo(String seq) {
/* 282 */     String data = consumeTo(seq);
/* 283 */     matchChomp(seq);
/* 284 */     return data;
/*     */   }
/*     */   
/*     */   public String chompToIgnoreCase(String seq) {
/* 288 */     String data = consumeToIgnoreCase(seq);
/* 289 */     matchChomp(seq);
/* 290 */     return data;
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
/*     */   public String chompBalanced(char open, char close) {
/* 303 */     int start = -1;
/* 304 */     int end = -1;
/* 305 */     int depth = 0;
/* 306 */     char last = Character.MIN_VALUE;
/* 307 */     boolean inQuote = false;
/*     */     
/*     */     label32: do {
/* 310 */       if (isEmpty())
/* 311 */         break label32;  Character c = Character.valueOf(consume());
/* 312 */       if (last == '\000' || last != '\\') {
/* 313 */         if ((c.equals(Character.valueOf('\'')) || c.equals(Character.valueOf('"'))) && c.charValue() != open)
/* 314 */           inQuote = !inQuote; 
/* 315 */         if (inQuote)
/*     */           continue; 
/* 317 */         if (c.equals(Character.valueOf(open))) {
/* 318 */           depth++;
/* 319 */           if (start == -1) {
/* 320 */             start = this.pos;
/*     */           }
/* 322 */         } else if (c.equals(Character.valueOf(close))) {
/* 323 */           depth--;
/*     */         } 
/*     */       } 
/* 326 */       if (depth > 0 && last != '\000')
/* 327 */         end = this.pos; 
/* 328 */       last = c.charValue();
/* 329 */     } while (depth > 0);
/* 330 */     return (end >= 0) ? this.queue.substring(start, end) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String unescape(String in) {
/* 339 */     StringBuilder out = new StringBuilder();
/* 340 */     char last = Character.MIN_VALUE;
/* 341 */     for (char c : in.toCharArray()) {
/* 342 */       if (c == '\\') {
/* 343 */         if (last != '\000' && last == '\\') {
/* 344 */           out.append(c);
/*     */         }
/*     */       } else {
/* 347 */         out.append(c);
/* 348 */       }  last = c;
/*     */     } 
/* 350 */     return out.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean consumeWhitespace() {
/* 358 */     boolean seen = false;
/* 359 */     while (matchesWhitespace()) {
/* 360 */       this.pos++;
/* 361 */       seen = true;
/*     */     } 
/* 363 */     return seen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String consumeWord() {
/* 371 */     int start = this.pos;
/* 372 */     while (matchesWord())
/* 373 */       this.pos++; 
/* 374 */     return this.queue.substring(start, this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String consumeTagName() {
/* 383 */     int start = this.pos;
/* 384 */     while (!isEmpty() && (matchesWord() || matchesAny(new char[] { ':', '_', '-' }))) {
/* 385 */       this.pos++;
/*     */     }
/* 387 */     return this.queue.substring(start, this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String consumeElementSelector() {
/* 396 */     int start = this.pos;
/* 397 */     while (!isEmpty() && (matchesWord() || matchesAny(new char[] { '|', '_', '-' }))) {
/* 398 */       this.pos++;
/*     */     }
/* 400 */     return this.queue.substring(start, this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String consumeCssIdentifier() {
/* 409 */     int start = this.pos;
/* 410 */     while (!isEmpty() && (matchesWord() || matchesAny(new char[] { '-', '_' }))) {
/* 411 */       this.pos++;
/*     */     }
/* 413 */     return this.queue.substring(start, this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String consumeAttributeKey() {
/* 421 */     int start = this.pos;
/* 422 */     while (!isEmpty() && (matchesWord() || matchesAny(new char[] { '-', '_', ':' }))) {
/* 423 */       this.pos++;
/*     */     }
/* 425 */     return this.queue.substring(start, this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remainder() {
/* 433 */     String remainder = this.queue.substring(this.pos, this.queue.length());
/* 434 */     this.pos = this.queue.length();
/* 435 */     return remainder;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 440 */     return this.queue.substring(this.pos);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/TokenQueue.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */