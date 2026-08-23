/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntHashtable
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7354463962269093965L;
/*     */   private Entry[] table;
/*     */   private int count;
/*     */   private int threshold;
/*     */   private float loadFactor;
/*     */   
/*     */   public IntHashtable() {
/*  96 */     this(150, 0.75F);
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
/*     */   public IntHashtable(int initialCapacity) {
/* 108 */     this(initialCapacity, 0.75F);
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
/*     */   public IntHashtable(int initialCapacity, float loadFactor) {
/* 121 */     if (initialCapacity < 0) {
/* 122 */       throw new IllegalArgumentException(MessageFormatUtil.format("Illegal Capacity: {0}", new Object[] { Integer.valueOf(initialCapacity) }));
/*     */     }
/* 124 */     if (loadFactor <= 0.0F) {
/* 125 */       throw new IllegalArgumentException(MessageFormatUtil.format("Illegal Load: {0}", new Object[] { Float.valueOf(loadFactor) }));
/*     */     }
/* 127 */     if (initialCapacity == 0) {
/* 128 */       initialCapacity = 1;
/*     */     }
/* 130 */     this.loadFactor = loadFactor;
/* 131 */     this.table = new Entry[initialCapacity];
/* 132 */     this.threshold = (int)(initialCapacity * loadFactor);
/*     */   }
/*     */   
/*     */   public IntHashtable(IntHashtable o) {
/* 136 */     this(o.table.length, o.loadFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 145 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 155 */     return (this.count == 0);
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
/*     */   public boolean contains(int value) {
/* 178 */     Entry[] tab = this.table;
/* 179 */     for (int i = tab.length; i-- > 0;) {
/* 180 */       for (Entry e = tab[i]; e != null; e = e.next) {
/* 181 */         if (e.value == value) {
/* 182 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 186 */     return false;
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
/*     */   public boolean containsValue(int value) {
/* 201 */     return contains(value);
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
/*     */   public boolean containsKey(int key) {
/* 214 */     Entry[] tab = this.table;
/* 215 */     int index = (key & Integer.MAX_VALUE) % tab.length;
/* 216 */     for (Entry e = tab[index]; e != null; e = e.next) {
/* 217 */       if (e.key == key) {
/* 218 */         return true;
/*     */       }
/*     */     } 
/* 221 */     return false;
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
/*     */   public int get(int key) {
/* 234 */     Entry[] tab = this.table;
/* 235 */     int index = (key & Integer.MAX_VALUE) % tab.length;
/* 236 */     for (Entry e = tab[index]; e != null; e = e.next) {
/* 237 */       if (e.key == key) {
/* 238 */         return e.value;
/*     */       }
/*     */     } 
/* 241 */     return 0;
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
/*     */   protected void rehash() {
/* 274 */     int oldCapacity = this.table.length;
/* 275 */     Entry[] oldMap = this.table;
/*     */     
/* 277 */     int newCapacity = oldCapacity * 2 + 1;
/* 278 */     Entry[] newMap = new Entry[newCapacity];
/*     */     
/* 280 */     this.threshold = (int)(newCapacity * this.loadFactor);
/* 281 */     this.table = newMap;
/*     */     
/* 283 */     for (int i = oldCapacity; i-- > 0;) {
/* 284 */       for (Entry old = oldMap[i]; old != null; ) {
/* 285 */         Entry e = old;
/* 286 */         old = old.next;
/*     */         
/* 288 */         int index = (e.key & Integer.MAX_VALUE) % newCapacity;
/* 289 */         e.next = newMap[index];
/* 290 */         newMap[index] = e;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int put(int key, int value) {
/* 312 */     Entry[] tab = this.table;
/* 313 */     int index = (key & Integer.MAX_VALUE) % tab.length; Entry e;
/* 314 */     for (e = tab[index]; e != null; e = e.next) {
/* 315 */       if (e.key == key) {
/* 316 */         int old = e.value;
/*     */         
/* 318 */         e.value = value;
/* 319 */         return old;
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     if (this.count >= this.threshold) {
/*     */       
/* 325 */       rehash();
/*     */       
/* 327 */       tab = this.table;
/* 328 */       index = (key & Integer.MAX_VALUE) % tab.length;
/*     */     } 
/*     */ 
/*     */     
/* 332 */     e = new Entry(key, value, tab[index]);
/* 333 */     tab[index] = e;
/* 334 */     this.count++;
/* 335 */     return 0;
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
/*     */   public int remove(int key) {
/* 350 */     Entry[] tab = this.table;
/* 351 */     int index = (key & Integer.MAX_VALUE) % tab.length;
/*     */ 
/*     */     
/* 354 */     for (Entry e = tab[index], prev = null; e != null; prev = e, e = e.next) {
/* 355 */       if (e.key == key) {
/* 356 */         if (prev != null) {
/* 357 */           prev.next = e.next;
/*     */         } else {
/* 359 */           tab[index] = e.next;
/*     */         } 
/* 361 */         this.count--;
/* 362 */         int oldValue = e.value;
/* 363 */         e.value = 0;
/* 364 */         return oldValue;
/*     */       } 
/*     */     } 
/* 367 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 374 */     Entry[] tab = this.table;
/* 375 */     for (int index = tab.length; --index >= 0;) {
/* 376 */       tab[index] = null;
/*     */     }
/* 378 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Entry
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 8057670534065316193L;
/*     */ 
/*     */     
/*     */     int key;
/*     */ 
/*     */     
/*     */     int value;
/*     */ 
/*     */     
/*     */     Entry next;
/*     */ 
/*     */ 
/*     */     
/*     */     Entry(int key, int value, Entry next) {
/* 400 */       this.key = key;
/* 401 */       this.value = value;
/* 402 */       this.next = next;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getKey() {
/* 408 */       return this.key;
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 412 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Object clone() throws CloneNotSupportedException {
/* 417 */       return new Entry(this.key, this.value, (this.next != null) ? (Entry)this.next.clone() : null);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 422 */       return MessageFormatUtil.format("{0}={1}", new Object[] { Integer.valueOf(this.key), Integer.valueOf(this.value) });
/*     */     }
/*     */   }
/*     */   
/*     */   public int[] toOrderedKeys() {
/* 427 */     int[] res = getKeys();
/* 428 */     Arrays.sort(res);
/* 429 */     return res;
/*     */   }
/*     */   
/*     */   public int[] getKeys() {
/* 433 */     int[] res = new int[this.count];
/* 434 */     int ptr = 0;
/* 435 */     int index = this.table.length;
/* 436 */     Entry entry = null;
/*     */     while (true) {
/* 438 */       if (entry == null)
/* 439 */         while (index-- > 0 && (entry = this.table[index]) == null); 
/* 440 */       if (entry == null)
/*     */         break; 
/* 442 */       Entry e = entry;
/* 443 */       entry = e.next;
/* 444 */       res[ptr++] = e.key;
/*     */     } 
/* 446 */     return res;
/*     */   }
/*     */   
/*     */   public int getOneKey() {
/* 450 */     if (this.count == 0)
/* 451 */       return 0; 
/* 452 */     int index = this.table.length;
/* 453 */     Entry entry = null;
/* 454 */     while (index-- > 0 && (entry = this.table[index]) == null);
/* 455 */     if (entry == null)
/* 456 */       return 0; 
/* 457 */     return entry.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*     */     try {
/* 463 */       IntHashtable t = new IntHashtable(this);
/* 464 */       t.table = new Entry[this.table.length];
/* 465 */       for (int i = this.table.length; i-- > 0;) {
/* 466 */         t.table[i] = (this.table[i] != null) ? (Entry)this.table[i]
/* 467 */           .clone() : null;
/*     */       }
/* 469 */       return t;
/* 470 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 472 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/IntHashtable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */