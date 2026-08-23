/*     */ package com.itextpdf.layout.hyphenation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TernaryTree
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3175412271203716160L;
/*     */   protected char[] lo;
/*     */   protected char[] hi;
/*     */   protected char[] eq;
/*     */   protected char[] sc;
/*     */   protected CharVector kv;
/*     */   protected char root;
/*     */   protected char freenode;
/*     */   protected int length;
/*     */   protected static final int BLOCK_SIZE = 2048;
/*     */   
/*     */   TernaryTree() {
/* 127 */     init();
/*     */   }
/*     */   
/*     */   TernaryTree(TernaryTree tt) {
/* 131 */     this.root = tt.root;
/* 132 */     this.freenode = tt.freenode;
/* 133 */     this.length = tt.length;
/* 134 */     this.lo = (char[])tt.lo.clone();
/* 135 */     this.hi = (char[])tt.hi.clone();
/* 136 */     this.eq = (char[])tt.eq.clone();
/* 137 */     this.sc = (char[])tt.sc.clone();
/* 138 */     this.kv = new CharVector(tt.kv);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 143 */     this.root = Character.MIN_VALUE;
/* 144 */     this.freenode = '\001';
/* 145 */     this.length = 0;
/* 146 */     this.lo = new char[2048];
/* 147 */     this.hi = new char[2048];
/* 148 */     this.eq = new char[2048];
/* 149 */     this.sc = new char[2048];
/* 150 */     this.kv = new CharVector();
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
/*     */   public void insert(String key, char val) {
/* 165 */     int len = key.length() + 1;
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (this.freenode + len > this.eq.length) {
/* 170 */       redimNodeArrays(this.eq.length + 2048);
/*     */     }
/* 172 */     char[] strkey = new char[len--];
/* 173 */     key.getChars(0, len, strkey, 0);
/* 174 */     strkey[len] = Character.MIN_VALUE;
/* 175 */     this.root = insert(new TreeInsertionParams(this.root, strkey, 0, val));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(char[] key, int start, char val) {
/* 185 */     int len = strlen(key) + 1;
/* 186 */     if (this.freenode + len > this.eq.length) {
/* 187 */       redimNodeArrays(this.eq.length + 2048);
/*     */     }
/* 189 */     this.root = insert(new TreeInsertionParams(this.root, key, start, val));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Character insertNewBranchIfNeeded(TreeInsertionParams params) {
/* 196 */     char p = params.p;
/* 197 */     char[] key = params.key;
/* 198 */     int start = params.start;
/* 199 */     char val = params.val;
/* 200 */     int len = strlen(key, start);
/* 201 */     if (p == '\000') {
/*     */ 
/*     */ 
/*     */       
/* 205 */       p = this.freenode = (char)(this.freenode + 1);
/*     */ 
/*     */       
/* 208 */       this.eq[p] = val;
/* 209 */       this.length++;
/* 210 */       this.hi[p] = Character.MIN_VALUE;
/* 211 */       if (len > 0) {
/*     */ 
/*     */         
/* 214 */         this.sc[p] = Character.MAX_VALUE;
/*     */ 
/*     */         
/* 217 */         this.lo[p] = (char)this.kv.alloc(len + 1);
/* 218 */         strcpy(this.kv.getArray(), this.lo[p], key, start);
/*     */       } else {
/* 220 */         this.sc[p] = Character.MIN_VALUE;
/* 221 */         this.lo[p] = Character.MIN_VALUE;
/*     */       } 
/* 223 */       return Character.valueOf(p);
/*     */     } 
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char insertIntoExistingBranch(TreeInsertionParams params) {
/* 232 */     char initialP = params.p;
/* 233 */     TreeInsertionParams paramsToInsertNext = params;
/* 234 */     while (paramsToInsertNext != null) {
/* 235 */       char p = paramsToInsertNext.p;
/*     */       
/* 237 */       assert p != '\000';
/* 238 */       char[] key = paramsToInsertNext.key;
/* 239 */       int start = paramsToInsertNext.start;
/* 240 */       char val = paramsToInsertNext.val;
/* 241 */       int len = strlen(key, start);
/* 242 */       paramsToInsertNext = null;
/*     */       
/* 244 */       if (this.sc[p] == Character.MAX_VALUE) {
/*     */ 
/*     */ 
/*     */         
/* 248 */         char pp = this.freenode = (char)(this.freenode + 1);
/*     */ 
/*     */         
/* 251 */         this.lo[pp] = this.lo[p];
/*     */ 
/*     */         
/* 254 */         this.eq[pp] = this.eq[p];
/* 255 */         this.lo[p] = Character.MIN_VALUE;
/* 256 */         if (len > 0) {
/* 257 */           this.sc[p] = this.kv.get(this.lo[pp]);
/* 258 */           this.eq[p] = pp;
/* 259 */           this.lo[pp] = (char)(this.lo[pp] + 1);
/* 260 */           if (this.kv.get(this.lo[pp]) == '\000') {
/*     */             
/* 262 */             this.lo[pp] = Character.MIN_VALUE;
/* 263 */             this.sc[pp] = Character.MIN_VALUE;
/* 264 */             this.hi[pp] = Character.MIN_VALUE;
/*     */           } else {
/*     */             
/* 267 */             this.sc[pp] = Character.MAX_VALUE;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 272 */           this.sc[pp] = Character.MAX_VALUE;
/* 273 */           this.hi[p] = pp;
/* 274 */           this.sc[p] = Character.MIN_VALUE;
/* 275 */           this.eq[p] = val;
/* 276 */           this.length++;
/*     */           break;
/*     */         } 
/*     */       } 
/* 280 */       char s = key[start];
/* 281 */       if (s < this.sc[p]) {
/* 282 */         TreeInsertionParams treeInsertionParams = new TreeInsertionParams(this.lo[p], key, start, val);
/* 283 */         Character character = insertNewBranchIfNeeded(treeInsertionParams);
/* 284 */         if (character == null) {
/* 285 */           paramsToInsertNext = treeInsertionParams; continue;
/*     */         } 
/* 287 */         this.lo[p] = character.charValue(); continue;
/*     */       } 
/* 289 */       if (s == this.sc[p]) {
/* 290 */         if (s != '\000') {
/* 291 */           TreeInsertionParams treeInsertionParams = new TreeInsertionParams(this.eq[p], key, start + 1, val);
/* 292 */           Character character = insertNewBranchIfNeeded(treeInsertionParams);
/* 293 */           if (character == null) {
/* 294 */             paramsToInsertNext = treeInsertionParams; continue;
/*     */           } 
/* 296 */           this.eq[p] = character.charValue();
/*     */           
/*     */           continue;
/*     */         } 
/* 300 */         this.eq[p] = val;
/*     */         continue;
/*     */       } 
/* 303 */       TreeInsertionParams branchParams = new TreeInsertionParams(this.hi[p], key, start, val);
/* 304 */       Character insertNew = insertNewBranchIfNeeded(branchParams);
/* 305 */       if (insertNew == null) {
/* 306 */         paramsToInsertNext = branchParams; continue;
/*     */       } 
/* 308 */       this.hi[p] = insertNew.charValue();
/*     */     } 
/*     */ 
/*     */     
/* 312 */     return initialP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char insert(TreeInsertionParams params) {
/* 320 */     Character newBranch = insertNewBranchIfNeeded(params);
/* 321 */     if (newBranch == null) {
/* 322 */       return insertIntoExistingBranch(params);
/*     */     }
/* 324 */     return newBranch.charValue();
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
/*     */   public static int strcmp(char[] a, int startA, char[] b, int startB) {
/* 337 */     for (; a[startA] == b[startB]; startA++, startB++) {
/* 338 */       if (a[startA] == '\000') {
/* 339 */         return 0;
/*     */       }
/*     */     } 
/* 342 */     return a[startA] - b[startB];
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
/*     */   public static int strcmp(String str, char[] a, int start) {
/* 355 */     int len = str.length(); int i;
/* 356 */     for (i = 0; i < len; i++) {
/* 357 */       int d = str.charAt(i) - a[start + i];
/* 358 */       if (d != 0) {
/* 359 */         return d;
/*     */       }
/* 361 */       if (a[start + i] == '\000') {
/* 362 */         return d;
/*     */       }
/*     */     } 
/* 365 */     if (a[start + i] != '\000') {
/* 366 */       return -a[start + i];
/*     */     }
/* 368 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void strcpy(char[] dst, int di, char[] src, int si) {
/* 379 */     while (src[si] != '\000') {
/* 380 */       dst[di++] = src[si++];
/*     */     }
/* 382 */     dst[di] = Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int strlen(char[] a, int start) {
/* 391 */     int len = 0;
/* 392 */     for (int i = start; i < a.length && a[i] != '\000'; i++) {
/* 393 */       len++;
/*     */     }
/* 395 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int strlen(char[] a) {
/* 403 */     return strlen(a, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int find(String key) {
/* 412 */     int len = key.length();
/* 413 */     char[] strkey = new char[len + 1];
/* 414 */     key.getChars(0, len, strkey, 0);
/* 415 */     strkey[len] = Character.MIN_VALUE;
/*     */     
/* 417 */     return find(strkey, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int find(char[] key, int start) {
/* 428 */     char p = this.root;
/* 429 */     int i = start;
/*     */ 
/*     */     
/* 432 */     while (p != '\000') {
/* 433 */       if (this.sc[p] == Character.MAX_VALUE) {
/* 434 */         if (strcmp(key, i, this.kv.getArray(), this.lo[p]) == 0) {
/* 435 */           return this.eq[p];
/*     */         }
/* 437 */         return -1;
/*     */       } 
/*     */       
/* 440 */       char c = key[i];
/* 441 */       int d = c - this.sc[p];
/* 442 */       if (d == 0) {
/* 443 */         if (c == '\000') {
/* 444 */           return this.eq[p];
/*     */         }
/* 446 */         i++;
/* 447 */         p = this.eq[p]; continue;
/* 448 */       }  if (d < 0) {
/* 449 */         p = this.lo[p]; continue;
/*     */       } 
/* 451 */       p = this.hi[p];
/*     */     } 
/*     */     
/* 454 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean knows(String key) {
/* 462 */     return (find(key) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void redimNodeArrays(int newsize) {
/* 467 */     int len = (newsize < this.lo.length) ? newsize : this.lo.length;
/* 468 */     char[] na = new char[newsize];
/* 469 */     System.arraycopy(this.lo, 0, na, 0, len);
/* 470 */     this.lo = na;
/* 471 */     na = new char[newsize];
/* 472 */     System.arraycopy(this.hi, 0, na, 0, len);
/* 473 */     this.hi = na;
/* 474 */     na = new char[newsize];
/* 475 */     System.arraycopy(this.eq, 0, na, 0, len);
/* 476 */     this.eq = na;
/* 477 */     na = new char[newsize];
/* 478 */     System.arraycopy(this.sc, 0, na, 0, len);
/* 479 */     this.sc = na;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 484 */     return this.length;
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
/*     */   protected void insertBalanced(String[] k, char[] v, int offset, int n) {
/* 499 */     if (n < 1) {
/*     */       return;
/*     */     }
/* 502 */     int m = n >> 1;
/*     */     
/* 504 */     insert(k[m + offset], v[m + offset]);
/* 505 */     insertBalanced(k, v, offset, m);
/*     */     
/* 507 */     insertBalanced(k, v, offset + m + 1, n - m - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void balance() {
/* 517 */     int i = 0;
/* 518 */     int n = this.length;
/* 519 */     String[] k = new String[n];
/* 520 */     char[] v = new char[n];
/* 521 */     TernaryTreeIterator iter = new TernaryTreeIterator(this);
/* 522 */     while (iter.hasMoreElements()) {
/* 523 */       v[i] = iter.getValue();
/* 524 */       k[i++] = (String)iter.nextElement();
/*     */     } 
/* 526 */     init();
/* 527 */     insertBalanced(k, v, 0, n);
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
/*     */   public void trimToSize() {
/* 548 */     balance();
/*     */ 
/*     */     
/* 551 */     redimNodeArrays(this.freenode);
/*     */ 
/*     */     
/* 554 */     CharVector kx = new CharVector();
/* 555 */     kx.alloc(1);
/* 556 */     TernaryTree map = new TernaryTree();
/* 557 */     compact(kx, map, this.root);
/* 558 */     this.kv = kx;
/* 559 */     this.kv.trimToSize();
/*     */   }
/*     */ 
/*     */   
/*     */   private void compact(CharVector kx, TernaryTree map, char p) {
/* 564 */     if (p == '\000') {
/*     */       return;
/*     */     }
/* 567 */     if (this.sc[p] == Character.MAX_VALUE) {
/* 568 */       int k = map.find(this.kv.getArray(), this.lo[p]);
/* 569 */       if (k < 0) {
/* 570 */         k = kx.alloc(strlen(this.kv.getArray(), this.lo[p]) + 1);
/* 571 */         strcpy(kx.getArray(), k, this.kv.getArray(), this.lo[p]);
/* 572 */         map.insert(kx.getArray(), k, (char)k);
/*     */       } 
/* 574 */       this.lo[p] = (char)k;
/*     */     } else {
/* 576 */       compact(kx, map, this.lo[p]);
/* 577 */       if (this.sc[p] != '\000') {
/* 578 */         compact(kx, map, this.eq[p]);
/*     */       }
/* 580 */       compact(kx, map, this.hi[p]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Enumeration keys() {
/* 586 */     return new TernaryTreeIterator(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class TreeInsertionParams
/*     */   {
/*     */     char p;
/*     */     char[] key;
/*     */     int start;
/*     */     char val;
/*     */     
/*     */     public TreeInsertionParams(char p, char[] key, int start, char val) {
/* 598 */       this.p = p;
/* 599 */       this.key = key;
/* 600 */       this.start = start;
/* 601 */       this.val = val;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/TernaryTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */