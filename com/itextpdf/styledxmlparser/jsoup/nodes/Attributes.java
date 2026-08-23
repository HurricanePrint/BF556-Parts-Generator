/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.SerializationException;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import java.io.IOException;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Attributes
/*     */   implements Iterable<Attribute>, Cloneable
/*     */ {
/*     */   protected static final String dataPrefix = "data-";
/*  73 */   private LinkedHashMap<String, Attribute> attributes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String get(String key) {
/*  84 */     Validate.notEmpty(key);
/*     */     
/*  86 */     if (this.attributes == null) {
/*  87 */       return "";
/*     */     }
/*  89 */     Attribute attr = this.attributes.get(key.toLowerCase());
/*  90 */     return (attr != null) ? attr.getValue() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String key, String value) {
/*  99 */     Attribute attr = new Attribute(key, value);
/* 100 */     put(attr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String key, boolean value) {
/* 109 */     if (value) {
/* 110 */       put(new BooleanAttribute(key));
/*     */     } else {
/* 112 */       remove(key);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(Attribute attribute) {
/* 120 */     Validate.notNull(attribute);
/* 121 */     if (this.attributes == null)
/* 122 */       this.attributes = new LinkedHashMap<>(2); 
/* 123 */     this.attributes.put(attribute.getKey(), attribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(String key) {
/* 131 */     Validate.notEmpty(key);
/* 132 */     if (this.attributes == null)
/*     */       return; 
/* 134 */     this.attributes.remove(key.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasKey(String key) {
/* 143 */     return (this.attributes != null && this.attributes.containsKey(key.toLowerCase()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 151 */     if (this.attributes == null)
/* 152 */       return 0; 
/* 153 */     return this.attributes.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(Attributes incoming) {
/* 161 */     if (incoming.size() == 0)
/*     */       return; 
/* 163 */     if (this.attributes == null)
/* 164 */       this.attributes = new LinkedHashMap<>(incoming.size()); 
/* 165 */     this.attributes.putAll(incoming.attributes);
/*     */   }
/*     */   
/*     */   public Iterator<Attribute> iterator() {
/* 169 */     return asList().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Attribute> asList() {
/* 178 */     if (this.attributes == null) {
/* 179 */       return Collections.emptyList();
/*     */     }
/* 181 */     List<Attribute> list = new ArrayList<>(this.attributes.size());
/* 182 */     for (Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
/* 183 */       list.add(entry.getValue());
/*     */     }
/* 185 */     return Collections.unmodifiableList(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> dataset() {
/* 194 */     return new Dataset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String html() {
/* 203 */     StringBuilder accum = new StringBuilder();
/*     */     try {
/* 205 */       html(accum, (new Document("")).outputSettings());
/* 206 */     } catch (IOException e) {
/* 207 */       throw new SerializationException(e);
/*     */     } 
/* 209 */     return accum.toString();
/*     */   }
/*     */   
/*     */   void html(Appendable accum, Document.OutputSettings out) throws IOException {
/* 213 */     if (this.attributes == null) {
/*     */       return;
/*     */     }
/* 216 */     for (Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
/* 217 */       Attribute attribute = entry.getValue();
/* 218 */       accum.append(" ");
/* 219 */       attribute.html(accum, out);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 225 */     return html();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 235 */     if (this == o) return true; 
/* 236 */     if (!(o instanceof Attributes)) return false;
/*     */     
/* 238 */     Attributes that = (Attributes)o;
/*     */     
/* 240 */     if ((this.attributes != null) ? !this.attributes.equals(that.attributes) : (that.attributes != null)) return false;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 249 */     return (this.attributes != null) ? this.attributes.hashCode() : 0;
/*     */   }
/*     */   
/*     */   public Object clone() {
/*     */     Attributes clone;
/* 254 */     if (this.attributes == null) {
/* 255 */       return new Attributes();
/*     */     }
/*     */     
/*     */     try {
/* 259 */       clone = (Attributes)super.clone();
/* 260 */     } catch (CloneNotSupportedException e) {
/* 261 */       throw new RuntimeException(e);
/*     */     } 
/* 263 */     clone.attributes = new LinkedHashMap<>(this.attributes.size());
/* 264 */     for (Attribute attribute : this)
/* 265 */       clone.attributes.put(attribute.getKey(), (Attribute)attribute.clone()); 
/* 266 */     return clone;
/*     */   }
/*     */   
/*     */   private class Dataset
/*     */     extends AbstractMap<String, String> {
/*     */     private Dataset() {
/* 272 */       if (Attributes.this.attributes == null)
/* 273 */         Attributes.this.attributes = (LinkedHashMap)new LinkedHashMap<>(2); 
/*     */     }
/*     */     
/*     */     public Set<Map.Entry<String, String>> entrySet() {
/* 277 */       return new EntrySet();
/*     */     }
/*     */     
/*     */     public String put(String key, String value) {
/* 281 */       String dataKey = Attributes.dataKey(key);
/* 282 */       String oldValue = Attributes.this.hasKey(dataKey) ? ((Attribute)Attributes.this.attributes.get(dataKey)).getValue() : null;
/* 283 */       Attribute attr = new Attribute(dataKey, value);
/* 284 */       Attributes.this.attributes.put(dataKey, attr);
/* 285 */       return oldValue;
/*     */     }
/*     */     
/*     */     private class EntrySet extends AbstractSet<Map.Entry<String, String>> {
/*     */       private EntrySet() {}
/*     */       
/*     */       public Iterator<Map.Entry<String, String>> iterator() {
/* 292 */         return new Attributes.Dataset.DatasetIterator();
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/* 297 */         int count = 0;
/* 298 */         Iterator iter = new Attributes.Dataset.DatasetIterator();
/* 299 */         while (iter.hasNext())
/* 300 */           count++; 
/* 301 */         return count;
/*     */       }
/*     */     }
/*     */     
/*     */     private class DatasetIterator implements Iterator<Map.Entry<String, String>> {
/* 306 */       private Iterator<Attribute> attrIter = Attributes.this.attributes.values().iterator();
/*     */       private Attribute attr;
/*     */       
/*     */       public boolean hasNext() {
/* 310 */         while (this.attrIter.hasNext()) {
/* 311 */           this.attr = this.attrIter.next();
/* 312 */           if (this.attr.isDataAttribute()) return true; 
/*     */         } 
/* 314 */         return false;
/*     */       }
/*     */       
/*     */       public Map.Entry<String, String> next() {
/* 318 */         return new Attribute(this.attr.getKey().substring("data-".length()), this.attr.getValue());
/*     */       }
/*     */       private DatasetIterator() {}
/*     */       public void remove() {
/* 322 */         Attributes.this.attributes.remove(this.attr.getKey());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static String dataKey(String key) {
/* 328 */     return "data-" + key;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/Attributes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */