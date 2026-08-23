/*     */ package com.itextpdf.layout.hyphenation;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TernaryTreeIterator
/*     */   implements Enumeration
/*     */ {
/*     */   int cur;
/*     */   String curkey;
/*     */   TernaryTree tt;
/*     */   Stack ns;
/*     */   StringBuffer ks;
/*     */   
/*     */   private class Item
/*     */   {
/*     */     char parent;
/*     */     char child;
/*     */     
/*     */     public Item() {
/*  80 */       this.parent = Character.MIN_VALUE;
/*  81 */       this.child = Character.MIN_VALUE;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Item(char p, char c) {
/*  91 */       this.parent = p;
/*  92 */       this.child = c;
/*     */     }
/*     */     
/*     */     public Item(Item i) {
/*  96 */       this.parent = i.parent;
/*  97 */       this.child = i.child;
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
/*     */   public TernaryTreeIterator(TernaryTree tt) {
/* 115 */     this.tt = tt;
/* 116 */     this.cur = -1;
/* 117 */     this.ns = new Stack();
/* 118 */     this.ks = new StringBuffer();
/* 119 */     reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 126 */     this.ns.removeAllElements();
/* 127 */     this.ks.setLength(0);
/* 128 */     this.cur = this.tt.root;
/* 129 */     run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object nextElement() {
/* 136 */     String res = this.curkey;
/* 137 */     this.cur = up();
/* 138 */     run();
/* 139 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getValue() {
/* 146 */     if (this.cur >= 0) {
/* 147 */       return this.tt.eq[this.cur];
/*     */     }
/* 149 */     return Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMoreElements() {
/* 156 */     return (this.cur != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int up() {
/* 163 */     Item i = new Item();
/* 164 */     int res = 0;
/*     */     
/* 166 */     if (this.ns.size() == 0) {
/* 167 */       return -1;
/*     */     }
/*     */     
/* 170 */     if (this.cur != 0 && this.tt.sc[this.cur] == '\000') {
/* 171 */       return this.tt.lo[this.cur];
/*     */     }
/*     */     
/* 174 */     boolean climb = true;
/*     */     
/* 176 */     while (climb) {
/* 177 */       i = this.ns.pop();
/* 178 */       i.child = (char)(i.child + 1);
/* 179 */       switch (i.child) {
/*     */         case '\001':
/* 181 */           if (this.tt.sc[i.parent] != '\000') {
/* 182 */             res = this.tt.eq[i.parent];
/* 183 */             this.ns.push(new Item(i));
/* 184 */             this.ks.append(this.tt.sc[i.parent]);
/*     */           } else {
/* 186 */             i.child = (char)(i.child + 1);
/* 187 */             this.ns.push(new Item(i));
/* 188 */             res = this.tt.hi[i.parent];
/*     */           } 
/* 190 */           climb = false;
/*     */           continue;
/*     */         
/*     */         case '\002':
/* 194 */           res = this.tt.hi[i.parent];
/* 195 */           this.ns.push(new Item(i));
/* 196 */           if (this.ks.length() > 0)
/*     */           {
/* 198 */             this.ks.setLength(this.ks.length() - 1);
/*     */           }
/* 200 */           climb = false;
/*     */           continue;
/*     */       } 
/*     */       
/* 204 */       if (this.ns.size() == 0) {
/* 205 */         return -1;
/*     */       }
/* 207 */       climb = true;
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int run() {
/* 218 */     if (this.cur == -1) {
/* 219 */       return -1;
/*     */     }
/*     */     
/* 222 */     boolean leaf = false;
/*     */     
/*     */     while (true) {
/* 225 */       if (this.cur != 0)
/* 226 */         if (this.tt.sc[this.cur] == Character.MAX_VALUE) {
/* 227 */           leaf = true;
/*     */         } else {
/*     */           
/* 230 */           this.ns.push(new Item((char)this.cur, false));
/* 231 */           if (this.tt.sc[this.cur] == '\000') {
/* 232 */             leaf = true;
/*     */           } else {
/*     */             
/* 235 */             this.cur = this.tt.lo[this.cur]; continue;
/*     */           } 
/* 237 */         }   if (leaf) {
/*     */         break;
/*     */       }
/*     */       
/* 241 */       this.cur = up();
/* 242 */       if (this.cur == -1) {
/* 243 */         return -1;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 248 */     StringBuffer buf = new StringBuffer(this.ks.toString());
/* 249 */     if (this.tt.sc[this.cur] == Character.MAX_VALUE) {
/* 250 */       int p = this.tt.lo[this.cur];
/* 251 */       while (this.tt.kv.get(p) != '\000') {
/* 252 */         buf.append(this.tt.kv.get(p++));
/*     */       }
/*     */     } 
/* 255 */     this.curkey = buf.toString();
/* 256 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/hyphenation/TernaryTreeIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */