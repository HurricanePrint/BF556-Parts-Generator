/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfDictionary
/*     */   extends PdfObject
/*     */ {
/*     */   private static final long serialVersionUID = -1122075818690871644L;
/*  65 */   private Map<PdfName, PdfObject> map = new TreeMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary(Map<PdfName, PdfObject> map) {
/*  81 */     this.map.putAll(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary(Set<Map.Entry<PdfName, PdfObject>> entrySet) {
/*  91 */     for (Map.Entry<PdfName, PdfObject> entry : entrySet) {
/*  92 */       this.map.put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary(PdfDictionary dictionary) {
/* 103 */     this.map.putAll(dictionary.map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 112 */     return this.map.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 121 */     return (this.map.size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(PdfName key) {
/* 131 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(PdfObject value) {
/* 141 */     return this.map.values().contains(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject get(PdfName key) {
/* 151 */     return get(key, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getAsArray(PdfName key) {
/* 161 */     PdfObject direct = get(key, true);
/* 162 */     if (direct != null && direct.getType() == 1)
/* 163 */       return (PdfArray)direct; 
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getAsDictionary(PdfName key) {
/* 174 */     PdfObject direct = get(key, true);
/* 175 */     if (direct != null && direct.getType() == 3)
/* 176 */       return (PdfDictionary)direct; 
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfStream getAsStream(PdfName key) {
/* 187 */     PdfObject direct = get(key, true);
/* 188 */     if (direct != null && direct.getType() == 9)
/* 189 */       return (PdfStream)direct; 
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfNumber getAsNumber(PdfName key) {
/* 200 */     PdfObject direct = get(key, true);
/* 201 */     if (direct != null && direct.getType() == 8)
/* 202 */       return (PdfNumber)direct; 
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getAsName(PdfName key) {
/* 213 */     PdfObject direct = get(key, true);
/* 214 */     if (direct != null && direct.getType() == 6)
/* 215 */       return (PdfName)direct; 
/* 216 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfString getAsString(PdfName key) {
/* 226 */     PdfObject direct = get(key, true);
/* 227 */     if (direct != null && direct.getType() == 10)
/* 228 */       return (PdfString)direct; 
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfBoolean getAsBoolean(PdfName key) {
/* 239 */     PdfObject direct = get(key, true);
/* 240 */     if (direct != null && direct.getType() == 2)
/* 241 */       return (PdfBoolean)direct; 
/* 242 */     return null;
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
/*     */   public Rectangle getAsRectangle(PdfName key) {
/* 254 */     PdfArray a = getAsArray(key);
/* 255 */     return (a == null) ? null : a.toRectangle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getAsFloat(PdfName key) {
/* 265 */     PdfNumber number = getAsNumber(key);
/* 266 */     Float floatNumber = null;
/* 267 */     if (number != null) {
/* 268 */       floatNumber = Float.valueOf(number.floatValue());
/*     */     }
/* 270 */     return floatNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getAsInt(PdfName key) {
/* 280 */     PdfNumber number = getAsNumber(key);
/* 281 */     Integer intNumber = null;
/* 282 */     if (number != null) {
/* 283 */       intNumber = Integer.valueOf(number.intValue());
/*     */     }
/* 285 */     return intNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getAsBool(PdfName key) {
/* 295 */     PdfBoolean b = getAsBoolean(key);
/* 296 */     Boolean booleanValue = null;
/* 297 */     if (b != null) {
/* 298 */       booleanValue = Boolean.valueOf(b.getValue());
/*     */     }
/*     */     
/* 301 */     return booleanValue;
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
/*     */   public PdfObject put(PdfName key, PdfObject value) {
/* 313 */     assert value != null;
/* 314 */     return this.map.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject remove(PdfName key) {
/* 324 */     return this.map.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(PdfDictionary d) {
/* 333 */     this.map.putAll(d.map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 340 */     this.map.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<PdfName> keySet() {
/* 349 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PdfObject> values(boolean asDirects) {
/* 360 */     if (asDirects) {
/* 361 */       return values();
/*     */     }
/* 363 */     return this.map.values();
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
/*     */   public Collection<PdfObject> values() {
/* 377 */     return new PdfDictionaryValues(this.map.values());
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
/*     */   public Set<Map.Entry<PdfName, PdfObject>> entrySet() {
/* 390 */     return new PdfDictionaryEntrySet(this.map.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getType() {
/* 395 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 400 */     if (!isFlushed()) {
/* 401 */       String string = "<<";
/* 402 */       for (Map.Entry<PdfName, PdfObject> entry : this.map.entrySet()) {
/* 403 */         PdfIndirectReference indirectReference = ((PdfObject)entry.getValue()).getIndirectReference();
/* 404 */         string = string + ((PdfName)entry.getKey()).toString() + " " + ((indirectReference == null) ? ((PdfObject)entry.getValue()).toString() : indirectReference.toString()) + " ";
/*     */       } 
/* 406 */       string = string + ">>";
/* 407 */       return string;
/*     */     } 
/* 409 */     return this.indirectReference.toString();
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
/*     */   public PdfDictionary clone(List<PdfName> excludeKeys) {
/* 421 */     Map<PdfName, PdfObject> excluded = new TreeMap<>();
/* 422 */     for (PdfName key : excludeKeys) {
/* 423 */       PdfObject obj = this.map.get(key);
/* 424 */       if (obj != null)
/* 425 */         excluded.put(key, this.map.remove(key)); 
/*     */     } 
/* 427 */     PdfDictionary dictionary = (PdfDictionary)clone();
/* 428 */     this.map.putAll(excluded);
/* 429 */     return dictionary;
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
/*     */   public PdfDictionary copyTo(PdfDocument document, List<PdfName> excludeKeys, boolean allowDuplicating) {
/* 442 */     Map<PdfName, PdfObject> excluded = new TreeMap<>();
/* 443 */     for (PdfName key : excludeKeys) {
/* 444 */       PdfObject obj = this.map.get(key);
/* 445 */       if (obj != null)
/* 446 */         excluded.put(key, this.map.remove(key)); 
/*     */     } 
/* 448 */     PdfDictionary dictionary = (PdfDictionary)copyTo(document, allowDuplicating);
/* 449 */     this.map.putAll(excluded);
/* 450 */     return dictionary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject get(PdfName key, boolean asDirect) {
/* 460 */     if (!asDirect) {
/* 461 */       return this.map.get(key);
/*     */     }
/* 463 */     PdfObject obj = this.map.get(key);
/* 464 */     if (obj != null && obj.getType() == 5) {
/* 465 */       return ((PdfIndirectReference)obj).getRefersTo(true);
/*     */     }
/* 467 */     return obj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeDifferent(PdfDictionary other) {
/* 476 */     for (PdfName key : other.keySet()) {
/* 477 */       if (!containsKey(key)) {
/* 478 */         put(key, other.get(key));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected PdfObject newInstance() {
/* 484 */     return new PdfDictionary();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 489 */     super.copyContent(from, document);
/* 490 */     PdfDictionary dictionary = (PdfDictionary)from;
/* 491 */     for (Map.Entry<PdfName, PdfObject> entry : dictionary.map.entrySet()) {
/* 492 */       this.map.put(entry.getKey(), ((PdfObject)entry.getValue()).processCopying(document, false));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void releaseContent() {
/* 500 */     this.map = null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfDictionary.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */