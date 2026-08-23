/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfNameTree
/*     */   implements Serializable
/*     */ {
/*     */   private static final int NODE_SIZE = 40;
/*     */   private static final long serialVersionUID = 8153711383828989907L;
/*     */   private PdfCatalog catalog;
/*  66 */   private Map<String, PdfObject> items = new LinkedHashMap<>();
/*     */ 
/*     */   
/*     */   private PdfName treeType;
/*     */ 
/*     */   
/*     */   private boolean modified;
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNameTree(PdfCatalog catalog, PdfName treeType) {
/*  77 */     this.treeType = treeType;
/*  78 */     this.catalog = catalog;
/*  79 */     this.items = getNames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, PdfObject> getNames() {
/*  88 */     if (this.items.size() > 0) {
/*  89 */       return this.items;
/*     */     }
/*     */     
/*  92 */     PdfDictionary dictionary = this.catalog.getPdfObject().getAsDictionary(PdfName.Names);
/*  93 */     if (dictionary != null) {
/*  94 */       dictionary = dictionary.getAsDictionary(this.treeType);
/*  95 */       if (dictionary != null) {
/*  96 */         this.items = readTree(dictionary);
/*     */ 
/*     */         
/*  99 */         Set<String> keys = new HashSet<>();
/* 100 */         keys.addAll(this.items.keySet());
/* 101 */         for (String key : keys) {
/* 102 */           if (this.treeType.equals(PdfName.Dests)) {
/* 103 */             PdfArray arr = getDestArray(this.items.get(key));
/* 104 */             if (arr != null) {
/* 105 */               this.items.put(key, arr); continue;
/*     */             } 
/* 107 */             this.items.remove(key); continue;
/* 108 */           }  if (this.items.get(key) == null) {
/* 109 */             this.items.remove(key);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 114 */     if (this.treeType.equals(PdfName.Dests)) {
/* 115 */       PdfDictionary destinations = this.catalog.getPdfObject().getAsDictionary(PdfName.Dests);
/* 116 */       if (destinations != null) {
/* 117 */         Set<PdfName> keys = destinations.keySet();
/* 118 */         for (PdfName key : keys) {
/* 119 */           PdfArray array = getDestArray(destinations.get(key));
/* 120 */           if (array == null) {
/*     */             continue;
/*     */           }
/* 123 */           this.items.put(key.getValue(), array);
/*     */         } 
/*     */       } 
/*     */     } 
/* 127 */     return this.items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(String key, PdfObject value) {
/* 137 */     PdfObject existingVal = this.items.get(key);
/* 138 */     if (existingVal != null) {
/* 139 */       if (value.getIndirectReference() != null && value.getIndirectReference().equals(existingVal.getIndirectReference())) {
/*     */         return;
/*     */       }
/* 142 */       Logger logger = LoggerFactory.getLogger(PdfNameTree.class);
/* 143 */       logger.warn(MessageFormatUtil.format("Name \"{0}\" already exists in the name tree; old value will be replaced by the new one.", new Object[] { key }));
/*     */     } 
/*     */     
/* 146 */     this.modified = true;
/* 147 */     this.items.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModified() {
/* 154 */     return this.modified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModified() {
/* 161 */     this.modified = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary buildTree() {
/* 170 */     String[] names = new String[this.items.size()];
/* 171 */     names = (String[])this.items.keySet().toArray((Object[])names);
/* 172 */     Arrays.sort((Object[])names);
/* 173 */     if (names.length <= 40) {
/* 174 */       PdfDictionary dic = new PdfDictionary();
/* 175 */       PdfArray ar = new PdfArray();
/* 176 */       for (String name : names) {
/* 177 */         ar.add(new PdfString(name, null));
/* 178 */         ar.add(this.items.get(name));
/*     */       } 
/* 180 */       dic.put(PdfName.Names, ar);
/* 181 */       return dic;
/*     */     } 
/* 183 */     int skip = 40;
/* 184 */     PdfDictionary[] kids = new PdfDictionary[(names.length + 40 - 1) / 40];
/* 185 */     for (int k = 0; k < kids.length; k++) {
/* 186 */       int offset = k * 40;
/* 187 */       int end = Math.min(offset + 40, names.length);
/* 188 */       PdfDictionary dic = new PdfDictionary();
/* 189 */       PdfArray arr = new PdfArray();
/* 190 */       arr.add(new PdfString(names[offset], null));
/* 191 */       arr.add(new PdfString(names[end - 1], null));
/* 192 */       dic.put(PdfName.Limits, arr);
/* 193 */       arr = new PdfArray();
/* 194 */       for (; offset < end; offset++) {
/* 195 */         arr.add(new PdfString(names[offset], null));
/* 196 */         arr.add(this.items.get(names[offset]));
/*     */       } 
/* 198 */       dic.put(PdfName.Names, arr);
/* 199 */       dic.makeIndirect(this.catalog.getDocument());
/* 200 */       kids[k] = dic;
/*     */     } 
/* 202 */     int top = kids.length;
/*     */     while (true) {
/* 204 */       if (top <= 40) {
/* 205 */         PdfArray arr = new PdfArray();
/* 206 */         for (int j = 0; j < top; j++)
/* 207 */           arr.add(kids[j]); 
/* 208 */         PdfDictionary dic = new PdfDictionary();
/* 209 */         dic.put(PdfName.Kids, arr);
/* 210 */         return dic;
/*     */       } 
/* 212 */       skip *= 40;
/* 213 */       int tt = (names.length + skip - 1) / skip;
/* 214 */       for (int i = 0; i < tt; i++) {
/* 215 */         int offset = i * 40;
/* 216 */         int end = Math.min(offset + 40, top);
/* 217 */         PdfDictionary dic = (PdfDictionary)(new PdfDictionary()).makeIndirect(this.catalog.getDocument());
/* 218 */         PdfArray arr = new PdfArray();
/* 219 */         arr.add(new PdfString(names[i * skip], null));
/* 220 */         arr.add(new PdfString(names[Math.min((i + 1) * skip, names.length) - 1], null));
/* 221 */         dic.put(PdfName.Limits, arr);
/* 222 */         arr = new PdfArray();
/* 223 */         for (; offset < end; offset++) {
/* 224 */           arr.add(kids[offset]);
/*     */         }
/* 226 */         dic.put(PdfName.Kids, arr);
/* 227 */         kids[i] = dic;
/*     */       } 
/* 229 */       top = tt;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map<String, PdfObject> readTree(PdfDictionary dictionary) {
/* 234 */     Map<String, PdfObject> items = new LinkedHashMap<>();
/* 235 */     if (dictionary != null) {
/* 236 */       iterateItems(dictionary, items, null);
/*     */     }
/* 238 */     return items;
/*     */   }
/*     */   
/*     */   private PdfString iterateItems(PdfDictionary dictionary, Map<String, PdfObject> items, PdfString leftOver) {
/* 242 */     PdfArray names = dictionary.getAsArray(PdfName.Names);
/* 243 */     if (names != null) {
/* 244 */       for (int k = 0; k < names.size(); k++) {
/*     */         PdfString name;
/* 246 */         if (leftOver == null) {
/* 247 */           name = names.getAsString(k++);
/*     */         } else {
/* 249 */           name = leftOver;
/* 250 */           leftOver = null;
/*     */         } 
/* 252 */         if (k < names.size()) {
/* 253 */           items.put(name.toUnicodeString(), names.get(k));
/*     */         } else {
/* 255 */           return name;
/*     */         } 
/*     */       } 
/* 258 */     } else if ((names = dictionary.getAsArray(PdfName.Kids)) != null) {
/* 259 */       for (int k = 0; k < names.size(); k++) {
/* 260 */         PdfDictionary kid = names.getAsDictionary(k);
/* 261 */         leftOver = iterateItems(kid, items, leftOver);
/*     */       } 
/*     */     } 
/* 264 */     return null;
/*     */   }
/*     */   
/*     */   private PdfArray getDestArray(PdfObject obj) {
/* 268 */     if (obj == null)
/* 269 */       return null; 
/* 270 */     if (obj.isArray())
/* 271 */       return (PdfArray)obj; 
/* 272 */     if (obj.isDictionary()) {
/* 273 */       PdfArray arr = ((PdfDictionary)obj).getAsArray(PdfName.D);
/* 274 */       return arr;
/*     */     } 
/* 276 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfNameTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */