/*     */ package com.itextpdf.styledxmlparser.jsoup.select;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.FormElement;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
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
/*     */ public class Elements
/*     */   extends ArrayList<Element>
/*     */ {
/*     */   public Elements() {}
/*     */   
/*     */   public Elements(int initialCapacity) {
/*  72 */     super(initialCapacity);
/*     */   }
/*     */   
/*     */   public Elements(Collection<Element> elements) {
/*  76 */     super(elements);
/*     */   }
/*     */   
/*     */   public Elements(List<Element> elements) {
/*  80 */     super(elements);
/*     */   }
/*     */   
/*     */   public Elements(Element... elements) {
/*  84 */     super(Arrays.asList(elements));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*  93 */     Elements clone = new Elements(size());
/*     */     
/*  95 */     for (Element e : this) {
/*  96 */       clone.add((Element)e.clone());
/*     */     }
/*  98 */     return clone;
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
/*     */   public String attr(String attributeKey) {
/* 110 */     for (Element element : this) {
/* 111 */       if (element.hasAttr(attributeKey))
/* 112 */         return element.attr(attributeKey); 
/*     */     } 
/* 114 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAttr(String attributeKey) {
/* 123 */     for (Element element : this) {
/* 124 */       if (element.hasAttr(attributeKey))
/* 125 */         return true; 
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements attr(String attributeKey, String attributeValue) {
/* 137 */     for (Element element : this) {
/* 138 */       element.attr(attributeKey, attributeValue);
/*     */     }
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements removeAttr(String attributeKey) {
/* 149 */     for (Element element : this) {
/* 150 */       element.removeAttr(attributeKey);
/*     */     }
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements addClass(String className) {
/* 161 */     for (Element element : this) {
/* 162 */       element.addClass(className);
/*     */     }
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements removeClass(String className) {
/* 173 */     for (Element element : this) {
/* 174 */       element.removeClass(className);
/*     */     }
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements toggleClass(String className) {
/* 185 */     for (Element element : this) {
/* 186 */       element.toggleClass(className);
/*     */     }
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasClass(String className) {
/* 197 */     for (Element element : this) {
/* 198 */       if (element.hasClass(className))
/* 199 */         return true; 
/*     */     } 
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String val() {
/* 210 */     if (size() > 0) {
/* 211 */       return first().val();
/*     */     }
/* 213 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements val(String value) {
/* 222 */     for (Element element : this)
/* 223 */       element.val(value); 
/* 224 */     return this;
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
/*     */   public String text() {
/* 236 */     StringBuilder sb = new StringBuilder();
/* 237 */     for (Element element : this) {
/* 238 */       if (sb.length() != 0)
/* 239 */         sb.append(" "); 
/* 240 */       sb.append(element.text());
/*     */     } 
/* 242 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public boolean hasText() {
/* 246 */     for (Element element : this) {
/* 247 */       if (element.hasText())
/* 248 */         return true; 
/*     */     } 
/* 250 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String html() {
/* 260 */     StringBuilder sb = new StringBuilder();
/* 261 */     for (Element element : this) {
/* 262 */       if (sb.length() != 0)
/* 263 */         sb.append("\n"); 
/* 264 */       sb.append(element.html());
/*     */     } 
/* 266 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String outerHtml() {
/* 276 */     StringBuilder sb = new StringBuilder();
/* 277 */     for (Element element : this) {
/* 278 */       if (sb.length() != 0)
/* 279 */         sb.append("\n"); 
/* 280 */       sb.append(element.outerHtml());
/*     */     } 
/* 282 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 293 */     return outerHtml();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements tagName(String tagName) {
/* 304 */     for (Element element : this) {
/* 305 */       element.tagName(tagName);
/*     */     }
/* 307 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements html(String html) {
/* 317 */     for (Element element : this) {
/* 318 */       element.html(html);
/*     */     }
/* 320 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements prepend(String html) {
/* 330 */     for (Element element : this) {
/* 331 */       element.prepend(html);
/*     */     }
/* 333 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements append(String html) {
/* 343 */     for (Element element : this) {
/* 344 */       element.append(html);
/*     */     }
/* 346 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements before(String html) {
/* 356 */     for (Element element : this) {
/* 357 */       element.before(html);
/*     */     }
/* 359 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements after(String html) {
/* 369 */     for (Element element : this) {
/* 370 */       element.after(html);
/*     */     }
/* 372 */     return this;
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
/*     */   public Elements wrap(String html) {
/* 385 */     Validate.notEmpty(html);
/* 386 */     for (Element element : this) {
/* 387 */       element.wrap(html);
/*     */     }
/* 389 */     return this;
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
/*     */   public Elements unwrap() {
/* 409 */     for (Element element : this) {
/* 410 */       element.unwrap();
/*     */     }
/* 412 */     return this;
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
/*     */   public Elements empty() {
/* 428 */     for (Element element : this) {
/* 429 */       element.empty();
/*     */     }
/* 431 */     return this;
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
/*     */   public Elements remove() {
/* 448 */     for (Element element : this) {
/* 449 */       element.remove();
/*     */     }
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements select(String query) {
/* 462 */     return Selector.select(query, this);
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
/*     */   public Elements not(String query) {
/* 476 */     Elements out = Selector.select(query, this);
/* 477 */     return Selector.filterOut(this, out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements eq(int index) {
/* 488 */     return (size() > index) ? new Elements(new Element[] { get(index) }) : new Elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean is(String query) {
/* 497 */     Elements children = select(query);
/* 498 */     return !children.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements parents() {
/* 506 */     Set<Element> combo = new LinkedHashSet<>();
/* 507 */     for (Element e : this) {
/* 508 */       combo.addAll(e.parents());
/*     */     }
/* 510 */     return new Elements(combo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element first() {
/* 519 */     return isEmpty() ? null : get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element last() {
/* 527 */     return isEmpty() ? null : get(size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements traverse(NodeVisitor nodeVisitor) {
/* 536 */     Validate.notNull(nodeVisitor);
/* 537 */     NodeTraversor traversor = new NodeTraversor(nodeVisitor);
/* 538 */     for (Element el : this) {
/* 539 */       traversor.traverse((Node)el);
/*     */     }
/* 541 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FormElement> forms() {
/* 550 */     ArrayList<FormElement> forms = new ArrayList<>();
/* 551 */     for (Element el : this) {
/* 552 */       if (el instanceof FormElement)
/* 553 */         forms.add((FormElement)el); 
/* 554 */     }  return forms;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/select/Elements.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */