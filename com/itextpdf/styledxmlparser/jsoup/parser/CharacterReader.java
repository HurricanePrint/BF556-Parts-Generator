/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
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
/*     */ final class CharacterReader
/*     */ {
/*     */   static final char EOF = '￿';
/*     */   private static final int maxCacheLen = 12;
/*     */   private final char[] input;
/*     */   private final int length;
/*  58 */   private int pos = 0;
/*  59 */   private int mark = 0;
/*  60 */   private final String[] stringCache = new String[512];
/*     */   
/*     */   CharacterReader(String input) {
/*  63 */     Validate.notNull(input);
/*  64 */     this.input = input.toCharArray();
/*  65 */     this.length = this.input.length;
/*     */   }
/*     */   
/*     */   int pos() {
/*  69 */     return this.pos;
/*     */   }
/*     */   
/*     */   boolean isEmpty() {
/*  73 */     return (this.pos >= this.length);
/*     */   }
/*     */   
/*     */   char current() {
/*  77 */     return (this.pos >= this.length) ? Character.MAX_VALUE : this.input[this.pos];
/*     */   }
/*     */   
/*     */   char consume() {
/*  81 */     char val = (this.pos >= this.length) ? Character.MAX_VALUE : this.input[this.pos];
/*  82 */     this.pos++;
/*  83 */     return val;
/*     */   }
/*     */   
/*     */   void unconsume() {
/*  87 */     this.pos--;
/*     */   }
/*     */   
/*     */   void advance() {
/*  91 */     this.pos++;
/*     */   }
/*     */   
/*     */   void mark() {
/*  95 */     this.mark = this.pos;
/*     */   }
/*     */   
/*     */   void rewindToMark() {
/*  99 */     this.pos = this.mark;
/*     */   }
/*     */   
/*     */   String consumeAsString() {
/* 103 */     return new String(this.input, this.pos++, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int nextIndexOf(char c) {
/* 113 */     for (int i = this.pos; i < this.length; i++) {
/* 114 */       if (c == this.input[i])
/* 115 */         return i - this.pos; 
/*     */     } 
/* 117 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int nextIndexOf(CharSequence seq) {
/* 128 */     char startChar = seq.charAt(0);
/* 129 */     for (int offset = this.pos; offset < this.length; offset++) {
/*     */       
/* 131 */       if (startChar != this.input[offset])
/* 132 */         while (++offset < this.length && startChar != this.input[offset]); 
/* 133 */       int i = offset + 1;
/* 134 */       int last = i + seq.length() - 1;
/* 135 */       if (offset < this.length && last <= this.length) {
/* 136 */         for (int j = 1; i < last && seq.charAt(j) == this.input[i]; ) { i++; j++; }
/* 137 */          if (i == last)
/* 138 */           return offset - this.pos; 
/*     */       } 
/*     */     } 
/* 141 */     return -1;
/*     */   }
/*     */   
/*     */   String consumeTo(char c) {
/* 145 */     int offset = nextIndexOf(c);
/* 146 */     if (offset != -1) {
/* 147 */       String consumed = cacheString(this.pos, offset);
/* 148 */       this.pos += offset;
/* 149 */       return consumed;
/*     */     } 
/* 151 */     return consumeToEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   String consumeTo(String seq) {
/* 156 */     int offset = nextIndexOf(seq);
/* 157 */     if (offset != -1) {
/* 158 */       String consumed = cacheString(this.pos, offset);
/* 159 */       this.pos += offset;
/* 160 */       return consumed;
/*     */     } 
/* 162 */     return consumeToEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   String consumeToAny(char... chars) {
/* 167 */     int start = this.pos;
/* 168 */     int remaining = this.length;
/* 169 */     char[] val = this.input;
/*     */     
/* 171 */     label18: while (this.pos < remaining) {
/* 172 */       for (char c : chars) {
/* 173 */         if (val[this.pos] == c)
/*     */           break label18; 
/*     */       } 
/* 176 */       this.pos++;
/*     */     } 
/*     */     
/* 179 */     return (this.pos > start) ? cacheString(start, this.pos - start) : "";
/*     */   }
/*     */   
/*     */   String consumeToAnySorted(char... chars) {
/* 183 */     int start = this.pos;
/* 184 */     int remaining = this.length;
/* 185 */     char[] val = this.input;
/*     */     
/* 187 */     while (this.pos < remaining && 
/* 188 */       Arrays.binarySearch(chars, val[this.pos]) < 0)
/*     */     {
/* 190 */       this.pos++;
/*     */     }
/*     */     
/* 193 */     return (this.pos > start) ? cacheString(start, this.pos - start) : "";
/*     */   }
/*     */ 
/*     */   
/*     */   String consumeData() {
/* 198 */     int start = this.pos;
/* 199 */     int remaining = this.length;
/* 200 */     char[] val = this.input;
/*     */     
/* 202 */     while (this.pos < remaining) {
/* 203 */       char c = val[this.pos];
/* 204 */       if (c == '&' || c == '<' || c == '\000')
/*     */         break; 
/* 206 */       this.pos++;
/*     */     } 
/*     */     
/* 209 */     return (this.pos > start) ? cacheString(start, this.pos - start) : "";
/*     */   }
/*     */ 
/*     */   
/*     */   String consumeTagName() {
/* 214 */     int start = this.pos;
/* 215 */     int remaining = this.length;
/* 216 */     char[] val = this.input;
/*     */     
/* 218 */     while (this.pos < remaining) {
/* 219 */       char c = val[this.pos];
/* 220 */       if (c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == ' ' || c == '/' || c == '>' || c == '\000')
/*     */         break; 
/* 222 */       this.pos++;
/*     */     } 
/*     */     
/* 225 */     return (this.pos > start) ? cacheString(start, this.pos - start) : "";
/*     */   }
/*     */   
/*     */   String consumeToEnd() {
/* 229 */     String data = cacheString(this.pos, this.length - this.pos);
/* 230 */     this.pos = this.length;
/* 231 */     return data;
/*     */   }
/*     */   
/*     */   String consumeLetterSequence() {
/* 235 */     int start = this.pos;
/* 236 */     while (this.pos < this.length) {
/* 237 */       char c = this.input[this.pos];
/* 238 */       if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isLetter(c)) {
/* 239 */         this.pos++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 244 */     return cacheString(start, this.pos - start);
/*     */   }
/*     */   
/*     */   String consumeLetterThenDigitSequence() {
/* 248 */     int start = this.pos;
/* 249 */     while (this.pos < this.length) {
/* 250 */       char c = this.input[this.pos];
/* 251 */       if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isLetter(c)) {
/* 252 */         this.pos++;
/*     */       }
/*     */     } 
/*     */     
/* 256 */     while (!isEmpty()) {
/* 257 */       char c = this.input[this.pos];
/* 258 */       if (c >= '0' && c <= '9') {
/* 259 */         this.pos++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 264 */     return cacheString(start, this.pos - start);
/*     */   }
/*     */   
/*     */   String consumeHexSequence() {
/* 268 */     int start = this.pos;
/* 269 */     while (this.pos < this.length) {
/* 270 */       char c = this.input[this.pos];
/* 271 */       if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f')) {
/* 272 */         this.pos++;
/*     */       }
/*     */     } 
/*     */     
/* 276 */     return cacheString(start, this.pos - start);
/*     */   }
/*     */   
/*     */   String consumeDigitSequence() {
/* 280 */     int start = this.pos;
/* 281 */     while (this.pos < this.length) {
/* 282 */       char c = this.input[this.pos];
/* 283 */       if (c >= '0' && c <= '9') {
/* 284 */         this.pos++;
/*     */       }
/*     */     } 
/*     */     
/* 288 */     return cacheString(start, this.pos - start);
/*     */   }
/*     */   
/*     */   boolean matches(char c) {
/* 292 */     return (!isEmpty() && this.input[this.pos] == c);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean matches(String seq) {
/* 297 */     int scanLength = seq.length();
/* 298 */     if (scanLength > this.length - this.pos) {
/* 299 */       return false;
/*     */     }
/* 301 */     for (int offset = 0; offset < scanLength; offset++) {
/* 302 */       if (seq.charAt(offset) != this.input[this.pos + offset])
/* 303 */         return false; 
/* 304 */     }  return true;
/*     */   }
/*     */   
/*     */   boolean matchesIgnoreCase(String seq) {
/* 308 */     int scanLength = seq.length();
/* 309 */     if (scanLength > this.length - this.pos) {
/* 310 */       return false;
/*     */     }
/* 312 */     for (int offset = 0; offset < scanLength; offset++) {
/* 313 */       char upScan = Character.toUpperCase(seq.charAt(offset));
/* 314 */       char upTarget = Character.toUpperCase(this.input[this.pos + offset]);
/* 315 */       if (upScan != upTarget)
/* 316 */         return false; 
/*     */     } 
/* 318 */     return true;
/*     */   }
/*     */   
/*     */   boolean matchesAny(char... seq) {
/* 322 */     if (isEmpty()) {
/* 323 */       return false;
/*     */     }
/* 325 */     char c = this.input[this.pos];
/* 326 */     for (char seek : seq) {
/* 327 */       if (seek == c)
/* 328 */         return true; 
/*     */     } 
/* 330 */     return false;
/*     */   }
/*     */   
/*     */   boolean matchesAnySorted(char[] seq) {
/* 334 */     return (!isEmpty() && Arrays.binarySearch(seq, this.input[this.pos]) >= 0);
/*     */   }
/*     */   
/*     */   boolean matchesLetter() {
/* 338 */     if (isEmpty())
/* 339 */       return false; 
/* 340 */     char c = this.input[this.pos];
/* 341 */     return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isLetter(c));
/*     */   }
/*     */   
/*     */   boolean matchesDigit() {
/* 345 */     if (isEmpty())
/* 346 */       return false; 
/* 347 */     char c = this.input[this.pos];
/* 348 */     return (c >= '0' && c <= '9');
/*     */   }
/*     */   
/*     */   boolean matchConsume(String seq) {
/* 352 */     if (matches(seq)) {
/* 353 */       this.pos += seq.length();
/* 354 */       return true;
/*     */     } 
/* 356 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean matchConsumeIgnoreCase(String seq) {
/* 361 */     if (matchesIgnoreCase(seq)) {
/* 362 */       this.pos += seq.length();
/* 363 */       return true;
/*     */     } 
/* 365 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean containsIgnoreCase(String seq) {
/* 371 */     String loScan = seq.toLowerCase();
/* 372 */     String hiScan = seq.toUpperCase();
/* 373 */     return (nextIndexOf(loScan) > -1 || nextIndexOf(hiScan) > -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 378 */     return new String(this.input, this.pos, this.length - this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String cacheString(int start, int count) {
/* 389 */     char[] val = this.input;
/* 390 */     String[] cache = this.stringCache;
/*     */ 
/*     */     
/* 393 */     if (count > 12) {
/* 394 */       return new String(val, start, count);
/*     */     }
/*     */     
/* 397 */     int hash = 0;
/* 398 */     int offset = start;
/* 399 */     for (int i = 0; i < count; i++) {
/* 400 */       hash = 31 * hash + val[offset++];
/*     */     }
/*     */ 
/*     */     
/* 404 */     int index = hash & cache.length - 1;
/* 405 */     String cached = cache[index];
/*     */     
/* 407 */     if (cached == null) {
/* 408 */       cached = new String(val, start, count);
/* 409 */       cache[index] = cached;
/*     */     } else {
/* 411 */       if (rangeEquals(start, count, cached)) {
/* 412 */         return cached;
/*     */       }
/* 414 */       cached = new String(val, start, count);
/* 415 */       cache[index] = cached;
/*     */     } 
/*     */     
/* 418 */     return cached;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean rangeEquals(int start, int count, String cached) {
/* 425 */     if (count == cached.length()) {
/* 426 */       char[] one = this.input;
/* 427 */       int i = start;
/* 428 */       int j = 0;
/* 429 */       while (count-- != 0) {
/* 430 */         if (one[i++] != cached.charAt(j++))
/* 431 */           return false; 
/*     */       } 
/* 433 */       return true;
/*     */     } 
/* 435 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/CharacterReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */