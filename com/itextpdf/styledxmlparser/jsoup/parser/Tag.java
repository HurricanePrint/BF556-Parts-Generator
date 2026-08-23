/*     */ package com.itextpdf.styledxmlparser.jsoup.parser;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tag
/*     */ {
/*  56 */   private static final Map<String, Tag> tags = new HashMap<>();
/*     */   
/*     */   private String tagName;
/*     */   private boolean isBlock = true;
/*     */   private boolean formatAsBlock = true;
/*     */   private boolean canContainBlock = true;
/*     */   private boolean canContainInline = true;
/*     */   private boolean empty = false;
/*     */   private boolean selfClosing = false;
/*     */   private boolean preserveWhitespace = false;
/*     */   private boolean formList = false;
/*     */   private boolean formSubmit = false;
/*     */   
/*     */   private Tag(String tagName) {
/*  70 */     this.tagName = tagName.toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  79 */     return this.tagName;
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
/*     */   public static Tag valueOf(String tagName) {
/*  91 */     Validate.notNull(tagName);
/*  92 */     Tag tag = tags.get(tagName);
/*     */     
/*  94 */     if (tag == null) {
/*  95 */       tagName = tagName.trim().toLowerCase();
/*  96 */       Validate.notEmpty(tagName);
/*  97 */       tag = tags.get(tagName);
/*     */       
/*  99 */       if (tag == null) {
/*     */         
/* 101 */         tag = new Tag(tagName);
/* 102 */         tag.isBlock = false;
/* 103 */         tag.canContainBlock = true;
/*     */       } 
/*     */     } 
/* 106 */     return tag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlock() {
/* 115 */     return this.isBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean formatAsBlock() {
/* 124 */     return this.formatAsBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canContainBlock() {
/* 133 */     return this.canContainBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInline() {
/* 142 */     return !this.isBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isData() {
/* 151 */     return (!this.canContainInline && !isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 160 */     return this.empty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelfClosing() {
/* 169 */     return (this.empty || this.selfClosing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKnownTag() {
/* 178 */     return tags.containsKey(this.tagName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isKnownTag(String tagName) {
/* 188 */     return tags.containsKey(tagName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean preserveWhitespace() {
/* 197 */     return this.preserveWhitespace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFormListed() {
/* 205 */     return this.formList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFormSubmittable() {
/* 213 */     return this.formSubmit;
/*     */   }
/*     */   
/*     */   Tag setSelfClosing() {
/* 217 */     this.selfClosing = true;
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 223 */     if (this == o) return true; 
/* 224 */     if (!(o instanceof Tag)) return false;
/*     */     
/* 226 */     Tag tag = (Tag)o;
/*     */     
/* 228 */     if (!this.tagName.equals(tag.tagName)) return false; 
/* 229 */     if (this.canContainBlock != tag.canContainBlock) return false; 
/* 230 */     if (this.canContainInline != tag.canContainInline) return false; 
/* 231 */     if (this.empty != tag.empty) return false; 
/* 232 */     if (this.formatAsBlock != tag.formatAsBlock) return false; 
/* 233 */     if (this.isBlock != tag.isBlock) return false; 
/* 234 */     if (this.preserveWhitespace != tag.preserveWhitespace) return false; 
/* 235 */     if (this.selfClosing != tag.selfClosing) return false; 
/* 236 */     if (this.formList != tag.formList) return false; 
/* 237 */     return (this.formSubmit == tag.formSubmit);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 242 */     int result = this.tagName.hashCode();
/* 243 */     result = 31 * result + (this.isBlock ? 1 : 0);
/* 244 */     result = 31 * result + (this.formatAsBlock ? 1 : 0);
/* 245 */     result = 31 * result + (this.canContainBlock ? 1 : 0);
/* 246 */     result = 31 * result + (this.canContainInline ? 1 : 0);
/* 247 */     result = 31 * result + (this.empty ? 1 : 0);
/* 248 */     result = 31 * result + (this.selfClosing ? 1 : 0);
/* 249 */     result = 31 * result + (this.preserveWhitespace ? 1 : 0);
/* 250 */     result = 31 * result + (this.formList ? 1 : 0);
/* 251 */     result = 31 * result + (this.formSubmit ? 1 : 0);
/* 252 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 257 */     return this.tagName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 262 */   private static final String[] blockTags = new String[] { "html", "head", "body", "frameset", "script", "noscript", "style", "meta", "link", "title", "frame", "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins", "del", "s", "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th", "td", "video", "audio", "canvas", "details", "menu", "plaintext", "template", "article", "main", "svg", "math" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   private static final String[] inlineTags = new String[] { "object", "base", "font", "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q", "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", "label", "button", "optgroup", "option", "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", "source", "track", "summary", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track", "data", "bdi" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 278 */   private static final String[] emptyTags = new String[] { "meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track" };
/*     */ 
/*     */ 
/*     */   
/* 282 */   private static final String[] formatAsInlineTags = new String[] { "title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", "script", "style", "ins", "del", "s" };
/*     */ 
/*     */ 
/*     */   
/* 286 */   private static final String[] preserveWhitespaceTags = new String[] { "pre", "plaintext", "title", "textarea" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 291 */   private static final String[] formListedTags = new String[] { "button", "fieldset", "input", "keygen", "object", "output", "select", "textarea" };
/*     */ 
/*     */   
/* 294 */   private static final String[] formSubmitTags = new String[] { "input", "keygen", "object", "select", "textarea" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 300 */     for (String tagName : blockTags) {
/* 301 */       Tag tag = new Tag(tagName);
/* 302 */       register(tag);
/*     */     } 
/* 304 */     for (String tagName : inlineTags) {
/* 305 */       Tag tag = new Tag(tagName);
/* 306 */       tag.isBlock = false;
/* 307 */       tag.canContainBlock = false;
/* 308 */       tag.formatAsBlock = false;
/* 309 */       register(tag);
/*     */     } 
/*     */ 
/*     */     
/* 313 */     for (String tagName : emptyTags) {
/* 314 */       Tag tag = tags.get(tagName);
/* 315 */       Validate.notNull(tag);
/* 316 */       tag.canContainBlock = false;
/* 317 */       tag.canContainInline = false;
/* 318 */       tag.empty = true;
/*     */     } 
/*     */     
/* 321 */     for (String tagName : formatAsInlineTags) {
/* 322 */       Tag tag = tags.get(tagName);
/* 323 */       Validate.notNull(tag);
/* 324 */       tag.formatAsBlock = false;
/*     */     } 
/*     */     
/* 327 */     for (String tagName : preserveWhitespaceTags) {
/* 328 */       Tag tag = tags.get(tagName);
/* 329 */       Validate.notNull(tag);
/* 330 */       tag.preserveWhitespace = true;
/*     */     } 
/*     */     
/* 333 */     for (String tagName : formListedTags) {
/* 334 */       Tag tag = tags.get(tagName);
/* 335 */       Validate.notNull(tag);
/* 336 */       tag.formList = true;
/*     */     } 
/*     */     
/* 339 */     for (String tagName : formSubmitTags) {
/* 340 */       Tag tag = tags.get(tagName);
/* 341 */       Validate.notNull(tag);
/* 342 */       tag.formSubmit = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void register(Tag tag) {
/* 347 */     tags.put(tag.tagName, tag);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/parser/Tag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */