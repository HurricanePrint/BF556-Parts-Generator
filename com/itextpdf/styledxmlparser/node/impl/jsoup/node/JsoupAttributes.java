/*     */ package com.itextpdf.styledxmlparser.node.impl.jsoup.node;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
/*     */ import com.itextpdf.styledxmlparser.node.IAttribute;
/*     */ import com.itextpdf.styledxmlparser.node.IAttributes;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsoupAttributes
/*     */   implements IAttributes
/*     */ {
/*     */   private Attributes attributes;
/*     */   
/*     */   public JsoupAttributes(Attributes attributes) {
/*  69 */     this.attributes = attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String key) {
/*  77 */     return this.attributes.hasKey(key) ? this.attributes.get(key) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String key, String value) {
/*  85 */     if (this.attributes.hasKey(key)) {
/*  86 */       this.attributes.remove(key);
/*     */     }
/*  88 */     this.attributes.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  96 */     return this.attributes.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<IAttribute> iterator() {
/* 104 */     return new AttributeIterator(this.attributes.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class AttributeIterator
/*     */     implements Iterator<IAttribute>
/*     */   {
/*     */     private Iterator<Attribute> iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AttributeIterator(Iterator<Attribute> iterator) {
/* 123 */       this.iterator = iterator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 131 */       return this.iterator.hasNext();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public IAttribute next() {
/* 139 */       return new JsoupAttribute(this.iterator.next());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 146 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/node/JsoupAttributes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */