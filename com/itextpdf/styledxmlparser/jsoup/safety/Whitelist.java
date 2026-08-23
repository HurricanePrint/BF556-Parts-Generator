/*     */ package com.itextpdf.styledxmlparser.jsoup.safety;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Whitelist
/*     */ {
/*     */   private Set<TagName> tagNames;
/*     */   private Map<TagName, Set<AttributeKey>> attributes;
/*     */   private Map<TagName, Map<AttributeKey, AttributeValue>> enforcedAttributes;
/*     */   private Map<TagName, Map<AttributeKey, Set<Protocol>>> protocols;
/*     */   private boolean preserveRelativeLinks;
/*     */   
/*     */   public static Whitelist none() {
/* 110 */     return new Whitelist();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Whitelist simpleText() {
/* 120 */     return (new Whitelist())
/* 121 */       .addTags(new String[] { "b", "em", "i", "strong", "u" });
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
/*     */   public static Whitelist basic() {
/* 137 */     return (new Whitelist())
/* 138 */       .addTags(new String[] { 
/*     */           "a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
/*     */           
/*     */           "i", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", 
/*     */           "sub", "sup", "u", "ul"
/* 143 */         }).addAttributes("a", new String[] { "href"
/* 144 */         }).addAttributes("blockquote", new String[] { "cite"
/* 145 */         }).addAttributes("q", new String[] { "cite"
/*     */         
/* 147 */         }).addProtocols("a", "href", new String[] { "ftp", "http", "https", "mailto"
/* 148 */         }).addProtocols("blockquote", "cite", new String[] { "http", "https"
/* 149 */         }).addProtocols("cite", "cite", new String[] { "http", "https"
/*     */         
/* 151 */         }).addEnforcedAttribute("a", "rel", "nofollow");
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
/*     */   public static Whitelist basicWithImages() {
/* 163 */     return basic()
/* 164 */       .addTags(new String[] { "img"
/* 165 */         }).addAttributes("img", new String[] { "align", "alt", "height", "src", "title", "width"
/* 166 */         }).addProtocols("img", "src", new String[] { "http", "https" });
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
/*     */   public static Whitelist relaxed() {
/* 180 */     return (new Whitelist())
/* 181 */       .addTags(new String[] { 
/*     */           "a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd",
/*     */           
/*     */           "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", 
/*     */           "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", 
/*     */           "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", 
/*     */           "u", "ul"
/* 188 */         }).addAttributes("a", new String[] { "href", "title"
/* 189 */         }).addAttributes("blockquote", new String[] { "cite"
/* 190 */         }).addAttributes("col", new String[] { "span", "width"
/* 191 */         }).addAttributes("colgroup", new String[] { "span", "width"
/* 192 */         }).addAttributes("img", new String[] { "align", "alt", "height", "src", "title", "width"
/* 193 */         }).addAttributes("ol", new String[] { "start", "type"
/* 194 */         }).addAttributes("q", new String[] { "cite"
/* 195 */         }).addAttributes("table", new String[] { "summary", "width"
/* 196 */         }).addAttributes("td", new String[] { "abbr", "axis", "colspan", "rowspan", "width"
/* 197 */         }).addAttributes("th", new String[] {
/*     */           
/*     */           "abbr", "axis", "colspan", "rowspan", "scope", "width"
/* 200 */         }).addAttributes("ul", new String[] { "type"
/*     */         
/* 202 */         }).addProtocols("a", "href", new String[] { "ftp", "http", "https", "mailto"
/* 203 */         }).addProtocols("blockquote", "cite", new String[] { "http", "https"
/* 204 */         }).addProtocols("cite", "cite", new String[] { "http", "https"
/* 205 */         }).addProtocols("img", "src", new String[] { "http", "https"
/* 206 */         }).addProtocols("q", "cite", new String[] { "http", "https" });
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
/*     */   public Whitelist() {
/* 219 */     this.tagNames = new HashSet<>();
/* 220 */     this.attributes = new HashMap<>();
/* 221 */     this.enforcedAttributes = new HashMap<>();
/* 222 */     this.protocols = new HashMap<>();
/* 223 */     this.preserveRelativeLinks = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Whitelist addTags(String... tags) {
/* 233 */     Validate.notNull(tags);
/*     */     
/* 235 */     for (String tagName : tags) {
/* 236 */       Validate.notEmpty(tagName);
/* 237 */       this.tagNames.add(TagName.valueOf(tagName));
/*     */     } 
/* 239 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Whitelist removeTags(String... tags) {
/* 249 */     Validate.notNull(tags);
/*     */     
/* 251 */     for (String tag : tags) {
/* 252 */       Validate.notEmpty(tag);
/* 253 */       TagName tagName = TagName.valueOf(tag);
/*     */       
/* 255 */       if (this.tagNames.remove(tagName)) {
/* 256 */         this.attributes.remove(tagName);
/* 257 */         this.enforcedAttributes.remove(tagName);
/* 258 */         this.protocols.remove(tagName);
/*     */       } 
/*     */     } 
/* 261 */     return this;
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
/*     */   public Whitelist addAttributes(String tag, String... keys) {
/* 278 */     Validate.notEmpty(tag);
/* 279 */     Validate.notNull(keys);
/* 280 */     Validate.isTrue((keys.length > 0), "No attributes supplied.");
/*     */     
/* 282 */     TagName tagName = TagName.valueOf(tag);
/* 283 */     if (!this.tagNames.contains(tagName))
/* 284 */       this.tagNames.add(tagName); 
/* 285 */     Set<AttributeKey> attributeSet = new HashSet<>();
/* 286 */     for (String key : keys) {
/* 287 */       Validate.notEmpty(key);
/* 288 */       attributeSet.add(AttributeKey.valueOf(key));
/*     */     } 
/* 290 */     if (this.attributes.containsKey(tagName)) {
/* 291 */       Set<AttributeKey> currentSet = this.attributes.get(tagName);
/* 292 */       currentSet.addAll(attributeSet);
/*     */     } else {
/* 294 */       this.attributes.put(tagName, attributeSet);
/*     */     } 
/* 296 */     return this;
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
/*     */   public Whitelist removeAttributes(String tag, String... keys) {
/* 313 */     Validate.notEmpty(tag);
/* 314 */     Validate.notNull(keys);
/* 315 */     Validate.isTrue((keys.length > 0), "No attributes supplied.");
/*     */     
/* 317 */     TagName tagName = TagName.valueOf(tag);
/* 318 */     Set<AttributeKey> attributeSet = new HashSet<>();
/* 319 */     for (String key : keys) {
/* 320 */       Validate.notEmpty(key);
/* 321 */       attributeSet.add(AttributeKey.valueOf(key));
/*     */     } 
/* 323 */     if (this.tagNames.contains(tagName) && this.attributes.containsKey(tagName)) {
/* 324 */       Set<AttributeKey> currentSet = this.attributes.get(tagName);
/* 325 */       currentSet.removeAll(attributeSet);
/*     */       
/* 327 */       if (currentSet.isEmpty())
/* 328 */         this.attributes.remove(tagName); 
/*     */     } 
/* 330 */     if (tag.equals(":all"))
/* 331 */       for (TagName name : this.attributes.keySet()) {
/* 332 */         Set<AttributeKey> currentSet = this.attributes.get(name);
/* 333 */         currentSet.removeAll(attributeSet);
/*     */         
/* 335 */         if (currentSet.isEmpty())
/* 336 */           this.attributes.remove(name); 
/*     */       }  
/* 338 */     return this;
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
/*     */   public Whitelist addEnforcedAttribute(String tag, String key, String value) {
/* 354 */     Validate.notEmpty(tag);
/* 355 */     Validate.notEmpty(key);
/* 356 */     Validate.notEmpty(value);
/*     */     
/* 358 */     TagName tagName = TagName.valueOf(tag);
/* 359 */     if (!this.tagNames.contains(tagName))
/* 360 */       this.tagNames.add(tagName); 
/* 361 */     AttributeKey attrKey = AttributeKey.valueOf(key);
/* 362 */     AttributeValue attrVal = AttributeValue.valueOf(value);
/*     */     
/* 364 */     if (this.enforcedAttributes.containsKey(tagName)) {
/* 365 */       ((Map<AttributeKey, AttributeValue>)this.enforcedAttributes.get(tagName)).put(attrKey, attrVal);
/*     */     } else {
/* 367 */       Map<AttributeKey, AttributeValue> attrMap = new HashMap<>();
/* 368 */       attrMap.put(attrKey, attrVal);
/* 369 */       this.enforcedAttributes.put(tagName, attrMap);
/*     */     } 
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Whitelist removeEnforcedAttribute(String tag, String key) {
/* 382 */     Validate.notEmpty(tag);
/* 383 */     Validate.notEmpty(key);
/*     */     
/* 385 */     TagName tagName = TagName.valueOf(tag);
/* 386 */     if (this.tagNames.contains(tagName) && this.enforcedAttributes.containsKey(tagName)) {
/* 387 */       AttributeKey attrKey = AttributeKey.valueOf(key);
/* 388 */       Map<AttributeKey, AttributeValue> attrMap = this.enforcedAttributes.get(tagName);
/* 389 */       attrMap.remove(attrKey);
/*     */       
/* 391 */       if (attrMap.isEmpty())
/* 392 */         this.enforcedAttributes.remove(tagName); 
/*     */     } 
/* 394 */     return this;
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
/*     */   public Whitelist preserveRelativeLinks(boolean preserve) {
/* 412 */     this.preserveRelativeLinks = preserve;
/* 413 */     return this;
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
/*     */   public Whitelist addProtocols(String tag, String key, String... protocols) {
/*     */     Map<AttributeKey, Set<Protocol>> attrMap;
/*     */     Set<Protocol> protSet;
/* 431 */     Validate.notEmpty(tag);
/* 432 */     Validate.notEmpty(key);
/* 433 */     Validate.notNull(protocols);
/*     */     
/* 435 */     TagName tagName = TagName.valueOf(tag);
/* 436 */     AttributeKey attrKey = AttributeKey.valueOf(key);
/*     */ 
/*     */ 
/*     */     
/* 440 */     if (this.protocols.containsKey(tagName)) {
/* 441 */       attrMap = this.protocols.get(tagName);
/*     */     } else {
/* 443 */       attrMap = new HashMap<>();
/* 444 */       this.protocols.put(tagName, attrMap);
/*     */     } 
/* 446 */     if (attrMap.containsKey(attrKey)) {
/* 447 */       protSet = attrMap.get(attrKey);
/*     */     } else {
/* 449 */       protSet = new HashSet<>();
/* 450 */       attrMap.put(attrKey, protSet);
/*     */     } 
/* 452 */     for (String protocol : protocols) {
/* 453 */       Validate.notEmpty(protocol);
/* 454 */       Protocol prot = Protocol.valueOf(protocol);
/* 455 */       protSet.add(prot);
/*     */     } 
/* 457 */     return this;
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
/*     */   public Whitelist removeProtocols(String tag, String key, String... protocols) {
/* 471 */     Validate.notEmpty(tag);
/* 472 */     Validate.notEmpty(key);
/* 473 */     Validate.notNull(protocols);
/*     */     
/* 475 */     TagName tagName = TagName.valueOf(tag);
/* 476 */     AttributeKey attrKey = AttributeKey.valueOf(key);
/*     */     
/* 478 */     if (this.protocols.containsKey(tagName)) {
/* 479 */       Map<AttributeKey, Set<Protocol>> attrMap = this.protocols.get(tagName);
/* 480 */       if (attrMap.containsKey(attrKey)) {
/* 481 */         Set<Protocol> protSet = attrMap.get(attrKey);
/* 482 */         for (String protocol : protocols) {
/* 483 */           Validate.notEmpty(protocol);
/* 484 */           Protocol prot = Protocol.valueOf(protocol);
/* 485 */           protSet.remove(prot);
/*     */         } 
/*     */         
/* 488 */         if (protSet.isEmpty()) {
/* 489 */           attrMap.remove(attrKey);
/* 490 */           if (attrMap.isEmpty())
/* 491 */             this.protocols.remove(tagName); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 495 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSafeTag(String tag) {
/* 504 */     return this.tagNames.contains(TagName.valueOf(tag));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
/* 515 */     TagName tag = TagName.valueOf(tagName);
/* 516 */     AttributeKey key = AttributeKey.valueOf(attr.getKey());
/*     */     
/* 518 */     if (this.attributes.containsKey(tag) && (
/* 519 */       (Set)this.attributes.get(tag)).contains(key)) {
/* 520 */       if (this.protocols.containsKey(tag)) {
/* 521 */         Map<AttributeKey, Set<Protocol>> attrProts = this.protocols.get(tag);
/*     */         
/* 523 */         return (!attrProts.containsKey(key) || testValidProtocol(el, attr, attrProts.get(key)));
/*     */       } 
/* 525 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 530 */     return (!tagName.equals(":all") && isSafeAttribute(":all", el, attr));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
/* 536 */     String value = el.absUrl(attr.getKey());
/* 537 */     if (value.length() == 0)
/* 538 */       value = attr.getValue(); 
/* 539 */     if (!this.preserveRelativeLinks) {
/* 540 */       attr.setValue(value);
/*     */     }
/* 542 */     for (Protocol protocol : protocols) {
/* 543 */       String prot = protocol.toString();
/*     */       
/* 545 */       if (prot.equals("#")) {
/* 546 */         if (isValidAnchor(value)) {
/* 547 */           return true;
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 553 */       prot = prot + ":";
/*     */       
/* 555 */       if (value.toLowerCase().startsWith(prot)) {
/* 556 */         return true;
/*     */       }
/*     */     } 
/* 559 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isValidAnchor(String value) {
/* 563 */     return (value.startsWith("#") && !value.matches(".*\\s.*"));
/*     */   }
/*     */   
/*     */   Attributes getEnforcedAttributes(String tagName) {
/* 567 */     Attributes attrs = new Attributes();
/* 568 */     TagName tag = TagName.valueOf(tagName);
/* 569 */     if (this.enforcedAttributes.containsKey(tag)) {
/* 570 */       Map<AttributeKey, AttributeValue> keyVals = this.enforcedAttributes.get(tag);
/* 571 */       for (Map.Entry<AttributeKey, AttributeValue> entry : keyVals.entrySet()) {
/* 572 */         attrs.put(((AttributeKey)entry.getKey()).toString(), ((AttributeValue)entry.getValue()).toString());
/*     */       }
/*     */     } 
/* 575 */     return attrs;
/*     */   }
/*     */   
/*     */   static class TagName
/*     */     extends TypedValue
/*     */   {
/*     */     TagName(String value) {
/* 582 */       super(value);
/*     */     }
/*     */     
/*     */     static TagName valueOf(String value) {
/* 586 */       return new TagName(value);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AttributeKey extends TypedValue {
/*     */     AttributeKey(String value) {
/* 592 */       super(value);
/*     */     }
/*     */     
/*     */     static AttributeKey valueOf(String value) {
/* 596 */       return new AttributeKey(value);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AttributeValue extends TypedValue {
/*     */     AttributeValue(String value) {
/* 602 */       super(value);
/*     */     }
/*     */     
/*     */     static AttributeValue valueOf(String value) {
/* 606 */       return new AttributeValue(value);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Protocol extends TypedValue {
/*     */     Protocol(String value) {
/* 612 */       super(value);
/*     */     }
/*     */     
/*     */     static Protocol valueOf(String value) {
/* 616 */       return new Protocol(value);
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class TypedValue {
/*     */     private String value;
/*     */     
/*     */     TypedValue(String value) {
/* 624 */       Validate.notNull(value);
/* 625 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 630 */       int prime = 31;
/* 631 */       int result = 1;
/* 632 */       result = 31 * result + ((this.value == null) ? 0 : this.value.hashCode());
/* 633 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 638 */       if (this == obj) return true; 
/* 639 */       if (obj == null) return false; 
/* 640 */       if (getClass() != obj.getClass()) return false; 
/* 641 */       TypedValue other = (TypedValue)obj;
/* 642 */       if (this.value == null)
/* 643 */       { if (other.value != null) return false;  }
/* 644 */       else if (!this.value.equals(other.value)) { return false; }
/* 645 */        return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 650 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/safety/Whitelist.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */