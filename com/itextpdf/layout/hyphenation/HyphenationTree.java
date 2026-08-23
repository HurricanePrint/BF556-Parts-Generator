/*     */ package com.itextpdf.layout.hyphenation;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HyphenationTree
/*     */   extends TernaryTree
/*     */   implements IPatternConsumer
/*     */ {
/*     */   private static final long serialVersionUID = -7842107987915665573L;
/*     */   protected ByteVector vspace;
/*     */   protected Map<String, List> stoplist;
/*     */   protected TernaryTree classmap;
/*     */   private transient TernaryTree ivalues;
/*     */   
/*     */   public HyphenationTree() {
/*  64 */     this.stoplist = new HashMap<>(23);
/*  65 */     this.classmap = new TernaryTree();
/*  66 */     this.vspace = new ByteVector();
/*     */ 
/*     */     
/*  69 */     this.vspace.alloc(1);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
/*  73 */     ois.defaultReadObject();
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
/*     */   protected int packValues(String values) {
/*  87 */     int n = values.length();
/*  88 */     int m = ((n & 0x1) == 1) ? ((n >> 1) + 2) : ((n >> 1) + 1);
/*  89 */     int offset = this.vspace.alloc(m);
/*  90 */     byte[] va = this.vspace.getArray();
/*  91 */     for (int i = 0; i < n; i++) {
/*  92 */       int j = i >> 1;
/*  93 */       byte v = (byte)(values.charAt(i) - 48 + 1 & 0xF);
/*  94 */       if ((i & 0x1) == 1) {
/*  95 */         va[j + offset] = (byte)(va[j + offset] | v);
/*     */       } else {
/*     */         
/*  98 */         va[j + offset] = (byte)(v << 4);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 103 */     va[m - 1 + offset] = 0;
/* 104 */     return offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String unpackValues(int k) {
/* 113 */     StringBuffer buf = new StringBuffer();
/* 114 */     byte v = this.vspace.get(k++);
/* 115 */     while (v != 0) {
/* 116 */       char c = (char)((v >>> 4) - 1 + 48);
/* 117 */       buf.append(c);
/* 118 */       c = (char)(v & 0xF);
/* 119 */       if (c == '\000') {
/*     */         break;
/*     */       }
/* 122 */       c = (char)(c - 1 + 48);
/* 123 */       buf.append(c);
/* 124 */       v = this.vspace.get(k++);
/*     */     } 
/* 126 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadPatterns(String filename) throws HyphenationException, FileNotFoundException {
/* 136 */     loadPatterns(new FileInputStream(filename), filename);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadPatterns(InputStream stream, String name) throws HyphenationException {
/* 146 */     PatternParser pp = new PatternParser(this);
/* 147 */     this.ivalues = new TernaryTree();
/*     */     
/* 149 */     pp.parse(stream, name);
/*     */ 
/*     */ 
/*     */     
/* 153 */     trimToSize();
/* 154 */     this.vspace.trimToSize();
/* 155 */     this.classmap.trimToSize();
/*     */ 
/*     */     
/* 158 */     this.ivalues = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findPattern(String pat) {
/* 167 */     int k = find(pat);
/* 168 */     if (k >= 0) {
/* 169 */       return unpackValues(k);
/*     */     }
/* 171 */     return "";
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
/*     */   protected int hstrcmp(char[] s, int si, char[] t, int ti) {
/* 184 */     for (; s[si] == t[ti]; si++, ti++) {
/* 185 */       if (s[si] == '\000') {
/* 186 */         return 0;
/*     */       }
/*     */     } 
/* 189 */     if (t[ti] == '\000') {
/* 190 */       return 0;
/*     */     }
/* 192 */     return s[si] - t[ti];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getValues(int k) {
/* 201 */     StringBuffer buf = new StringBuffer();
/* 202 */     byte v = this.vspace.get(k++);
/* 203 */     while (v != 0) {
/* 204 */       char c = (char)((v >>> 4) - 1);
/* 205 */       buf.append(c);
/* 206 */       c = (char)(v & 0xF);
/* 207 */       if (c == '\000') {
/*     */         break;
/*     */       }
/* 210 */       c = (char)(c - 1);
/* 211 */       buf.append(c);
/* 212 */       v = this.vspace.get(k++);
/*     */     } 
/* 214 */     byte[] res = new byte[buf.length()];
/* 215 */     for (int i = 0; i < res.length; i++) {
/* 216 */       res[i] = (byte)buf.charAt(i);
/*     */     }
/* 218 */     return res;
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void searchPatterns(char[] word, int index, byte[] il) {
/* 249 */     int i = index;
/*     */ 
/*     */     
/* 252 */     char sp = word[i];
/* 253 */     char p = this.root;
/*     */     
/* 255 */     while (p > '\000' && p < this.sc.length) {
/* 256 */       if (this.sc[p] == Character.MAX_VALUE) {
/* 257 */         if (hstrcmp(word, i, this.kv.getArray(), this.lo[p]) == 0) {
/*     */ 
/*     */           
/* 260 */           byte[] values = getValues(this.eq[p]);
/* 261 */           int j = index;
/* 262 */           for (int k = 0; k < values.length; k++) {
/* 263 */             if (j < il.length && values[k] > il[j]) {
/* 264 */               il[j] = values[k];
/*     */             }
/* 266 */             j++;
/*     */           } 
/*     */         } 
/*     */         return;
/*     */       } 
/* 271 */       int d = sp - this.sc[p];
/* 272 */       if (d == 0) {
/* 273 */         if (sp == '\000') {
/*     */           break;
/*     */         }
/* 276 */         sp = word[++i];
/* 277 */         p = this.eq[p];
/* 278 */         char q = p;
/*     */ 
/*     */ 
/*     */         
/* 282 */         while (q > '\000' && q < this.sc.length) {
/*     */ 
/*     */           
/* 285 */           if (this.sc[q] == Character.MAX_VALUE) {
/*     */             break;
/*     */           }
/* 288 */           if (this.sc[q] == '\000') {
/* 289 */             byte[] values = getValues(this.eq[q]);
/* 290 */             int j = index;
/* 291 */             for (int k = 0; k < values.length; k++) {
/* 292 */               if (j < il.length && values[k] > il[j]) {
/* 293 */                 il[j] = values[k];
/*     */               }
/* 295 */               j++;
/*     */             } 
/*     */             break;
/*     */           } 
/* 299 */           q = this.lo[q];
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 309 */       p = (d < 0) ? this.lo[p] : this.hi[p];
/*     */     } 
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
/*     */   public Hyphenation hyphenate(String word, int remainCharCount, int pushCharCount) {
/* 326 */     char[] w = word.toCharArray();
/* 327 */     if (isMultiPartWord(w, w.length)) {
/* 328 */       List<char[]> words = splitOnNonCharacters(w);
/* 329 */       return new Hyphenation(new String(w), 
/* 330 */           getHyphPointsForWords(words, remainCharCount, pushCharCount));
/*     */     } 
/* 332 */     return hyphenate(w, 0, w.length, remainCharCount, pushCharCount);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMultiPartWord(char[] w, int len) {
/* 337 */     int wordParts = 0;
/* 338 */     for (int i = 0; i < len; i++) {
/* 339 */       char[] c = new char[2];
/* 340 */       c[0] = w[i];
/* 341 */       int nc = this.classmap.find(c, 0);
/* 342 */       if (nc > 0) {
/* 343 */         if (wordParts > 1) {
/* 344 */           return true;
/*     */         }
/* 346 */         wordParts = 1;
/*     */       }
/* 348 */       else if (wordParts == 1) {
/* 349 */         wordParts++;
/*     */       } 
/*     */     } 
/*     */     
/* 353 */     return false;
/*     */   }
/*     */   
/*     */   private List<char[]> splitOnNonCharacters(char[] word) {
/* 357 */     List<Integer> breakPoints = getNonLetterBreaks(word);
/* 358 */     if (breakPoints.size() == 0) {
/* 359 */       return (List)Collections.emptyList();
/*     */     }
/* 361 */     List<char[]> words = (List)new ArrayList<>();
/* 362 */     for (int ibreak = 0; ibreak < breakPoints.size(); ibreak++) {
/* 363 */       char[] newWord = getWordFromCharArray(word, (ibreak == 0) ? 0 : ((Integer)breakPoints
/* 364 */           .get(ibreak - 1)).intValue(), ((Integer)breakPoints.get(ibreak)).intValue());
/* 365 */       words.add(newWord);
/*     */     } 
/* 367 */     if (word.length - ((Integer)breakPoints.get(breakPoints.size() - 1)).intValue() - 1 > 1) {
/* 368 */       char[] newWord = getWordFromCharArray(word, ((Integer)breakPoints.get(breakPoints.size() - 1)).intValue(), word.length);
/*     */       
/* 370 */       words.add(newWord);
/*     */     } 
/* 372 */     return words;
/*     */   }
/*     */   
/*     */   private List<Integer> getNonLetterBreaks(char[] word) {
/* 376 */     char[] c = new char[2];
/* 377 */     List<Integer> breakPoints = new ArrayList<>();
/* 378 */     boolean foundLetter = false;
/* 379 */     for (int i = 0; i < word.length; i++) {
/* 380 */       c[0] = word[i];
/* 381 */       if (this.classmap.find(c, 0) < 0) {
/* 382 */         if (foundLetter) {
/* 383 */           breakPoints.add(Integer.valueOf(i));
/*     */         }
/*     */       } else {
/* 386 */         foundLetter = true;
/*     */       } 
/*     */     } 
/* 389 */     return breakPoints;
/*     */   }
/*     */   
/*     */   private char[] getWordFromCharArray(char[] word, int startIndex, int endIndex) {
/* 393 */     char[] newWord = new char[endIndex - ((startIndex == 0) ? startIndex : (startIndex + 1))];
/* 394 */     int iChar = 0;
/* 395 */     for (int i = (startIndex == 0) ? 0 : (startIndex + 1); i < endIndex; i++) {
/* 396 */       newWord[iChar++] = word[i];
/*     */     }
/* 398 */     return newWord;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] getHyphPointsForWords(List<char[]> nonLetterWords, int remainCharCount, int pushCharCount) {
/* 403 */     int[] breaks = new int[0];
/* 404 */     for (int iNonLetterWord = 0; iNonLetterWord < nonLetterWords.size(); iNonLetterWord++) {
/* 405 */       char[] nonLetterWord = nonLetterWords.get(iNonLetterWord);
/* 406 */       Hyphenation curHyph = hyphenate(nonLetterWord, 0, nonLetterWord.length, (iNonLetterWord == 0) ? remainCharCount : 1, 
/*     */           
/* 408 */           (iNonLetterWord == nonLetterWords.size() - 1) ? pushCharCount : 1);
/* 409 */       if (curHyph != null) {
/*     */ 
/*     */         
/* 412 */         int[] combined = new int[breaks.length + (curHyph.getHyphenationPoints()).length];
/* 413 */         int[] hyphPoints = curHyph.getHyphenationPoints();
/* 414 */         int foreWordsSize = calcForeWordsSize(nonLetterWords, iNonLetterWord);
/* 415 */         for (int i = 0; i < hyphPoints.length; i++) {
/* 416 */           hyphPoints[i] = hyphPoints[i] + foreWordsSize;
/*     */         }
/* 418 */         System.arraycopy(breaks, 0, combined, 0, breaks.length);
/* 419 */         System.arraycopy(hyphPoints, 0, combined, breaks.length, hyphPoints.length);
/* 420 */         breaks = combined;
/*     */       } 
/* 422 */     }  return breaks;
/*     */   }
/*     */   
/*     */   private int calcForeWordsSize(List<char[]> nonLetterWords, int iNonLetterWord) {
/* 426 */     int result = 0;
/* 427 */     for (int i = 0; i < iNonLetterWord; i++) {
/* 428 */       result += ((char[])nonLetterWords.get(i)).length + 1;
/*     */     }
/* 430 */     return result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hyphenation hyphenate(char[] w, int offset, int len, int remainCharCount, int pushCharCount) {
/* 471 */     char[] word = new char[len + 3];
/*     */ 
/*     */     
/* 474 */     char[] c = new char[2];
/* 475 */     int iIgnoreAtBeginning = 0;
/* 476 */     int iLength = len;
/* 477 */     boolean bEndOfLetters = false; int i;
/* 478 */     for (i = 1; i <= len; i++) {
/* 479 */       c[0] = w[offset + i - 1];
/* 480 */       int nc = this.classmap.find(c, 0);
/*     */ 
/*     */       
/* 483 */       if (nc < 0) {
/* 484 */         if (i == 1 + iIgnoreAtBeginning) {
/*     */           
/* 486 */           iIgnoreAtBeginning++;
/*     */         } else {
/*     */           
/* 489 */           bEndOfLetters = true;
/*     */         } 
/* 491 */         iLength--;
/*     */       }
/* 493 */       else if (!bEndOfLetters) {
/* 494 */         word[i - iIgnoreAtBeginning] = (char)nc;
/*     */       } else {
/* 496 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 501 */     len = iLength;
/* 502 */     if (len < remainCharCount + pushCharCount)
/*     */     {
/* 504 */       return null;
/*     */     }
/* 506 */     int[] result = new int[len + 1];
/* 507 */     int k = 0;
/*     */ 
/*     */     
/* 510 */     String sw = new String(word, 1, len);
/* 511 */     if (this.stoplist.containsKey(sw)) {
/*     */       
/* 513 */       ArrayList hw = (ArrayList)this.stoplist.get(sw);
/* 514 */       int j = 0;
/* 515 */       for (i = 0; i < hw.size(); i++) {
/* 516 */         Object o = hw.get(i);
/*     */ 
/*     */         
/* 519 */         if (o instanceof String) {
/* 520 */           j += ((String)o).length();
/* 521 */           if (j >= remainCharCount && j < len - pushCharCount) {
/* 522 */             result[k++] = j + iIgnoreAtBeginning;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 530 */       word[0] = '.';
/*     */ 
/*     */       
/* 533 */       word[len + 1] = '.';
/*     */ 
/*     */       
/* 536 */       word[len + 2] = Character.MIN_VALUE;
/*     */ 
/*     */       
/* 539 */       byte[] il = new byte[len + 3];
/* 540 */       for (i = 0; i < len + 1; i++) {
/* 541 */         searchPatterns(word, i, il);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 548 */       for (i = 0; i < len; i++) {
/* 549 */         if ((il[i + 1] & 0x1) == 1 && i >= remainCharCount && i <= len - pushCharCount)
/*     */         {
/* 551 */           result[k++] = i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 557 */     if (k > 0) {
/*     */       
/* 559 */       int[] res = new int[k];
/* 560 */       System.arraycopy(result, 0, res, 0, k);
/* 561 */       return new Hyphenation(new String(w, iIgnoreAtBeginning, len), res);
/*     */     } 
/* 563 */     return null;
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
/*     */   public void addClass(String chargroup) {
/* 581 */     if (chargroup.length() > 0) {
/* 582 */       char equivChar = chargroup.charAt(0);
/* 583 */       char[] key = new char[2];
/* 584 */       key[1] = Character.MIN_VALUE;
/* 585 */       for (int i = 0; i < chargroup.length(); i++) {
/* 586 */         key[0] = chargroup.charAt(i);
/* 587 */         this.classmap.insert(key, 0, equivChar);
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
/*     */ 
/*     */   
/*     */   public void addException(String word, List hyphenatedword) {
/* 601 */     this.stoplist.put(word, hyphenatedword);
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
/*     */   public void addPattern(String pattern, String ivalue) {
/* 615 */     int k = this.ivalues.find(ivalue);
/* 616 */     if (k <= 0) {
/* 617 */       k = packValues(ivalue);
/* 618 */       this.ivalues.insert(ivalue, (char)k);
/*     */     } 
/* 620 */     insert(pattern, (char)k);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/HyphenationTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */