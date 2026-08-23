/*     */ package com.itextpdf.styledxmlparser.jsoup.select;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.PortUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Evaluator
/*     */ {
/*     */   public abstract boolean matches(Element paramElement1, Element paramElement2);
/*     */   
/*     */   public static final class Tag
/*     */     extends Evaluator
/*     */   {
/*     */     private String tagName;
/*     */     
/*     */     public Tag(String tagName) {
/*  84 */       this.tagName = tagName;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/*  89 */       return element.tagName().equals(this.tagName);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  94 */       return MessageFormatUtil.format("{0}", new Object[] { this.tagName });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Id
/*     */     extends Evaluator
/*     */   {
/*     */     private String id;
/*     */     
/*     */     public Id(String id) {
/* 105 */       this.id = id;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 110 */       return this.id.equals(element.id());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 115 */       return MessageFormatUtil.format("#{0}", new Object[] { this.id });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Class
/*     */     extends Evaluator
/*     */   {
/*     */     private String className;
/*     */ 
/*     */     
/*     */     public Class(String className) {
/* 127 */       this.className = className;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 132 */       return element.hasClass(this.className);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 137 */       return MessageFormatUtil.format(".{0}", new Object[] { this.className });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Attribute
/*     */     extends Evaluator
/*     */   {
/*     */     private String key;
/*     */ 
/*     */     
/*     */     public Attribute(String key) {
/* 149 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 154 */       return element.hasAttr(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 159 */       return MessageFormatUtil.format("[{0}]", new Object[] { this.key });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class AttributeStarting
/*     */     extends Evaluator
/*     */   {
/*     */     private String keyPrefix;
/*     */ 
/*     */     
/*     */     public AttributeStarting(String keyPrefix) {
/* 171 */       this.keyPrefix = keyPrefix;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 176 */       List<com.itextpdf.styledxmlparser.jsoup.nodes.Attribute> values = element.attributes().asList();
/* 177 */       for (com.itextpdf.styledxmlparser.jsoup.nodes.Attribute attribute : values) {
/* 178 */         if (attribute.getKey().startsWith(this.keyPrefix))
/* 179 */           return true; 
/*     */       } 
/* 181 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 186 */       return MessageFormatUtil.format("[^{0}]", new Object[] { this.keyPrefix });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class AttributeWithValue
/*     */     extends AttributeKeyPair
/*     */   {
/*     */     public AttributeWithValue(String key, String value) {
/* 196 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 201 */       return (element.hasAttr(this.key) && this.value.equalsIgnoreCase(element.attr(this.key).trim()));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 206 */       return MessageFormatUtil.format("[{0}={1}]", new Object[] { this.key, this.value });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class AttributeWithValueNot
/*     */     extends AttributeKeyPair
/*     */   {
/*     */     public AttributeWithValueNot(String key, String value) {
/* 216 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 221 */       return !this.value.equalsIgnoreCase(element.attr(this.key));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 226 */       return MessageFormatUtil.format("[{0}!={1}]", new Object[] { this.key, this.value });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class AttributeWithValueStarting
/*     */     extends AttributeKeyPair
/*     */   {
/*     */     public AttributeWithValueStarting(String key, String value) {
/* 236 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 241 */       return (element.hasAttr(this.key) && element.attr(this.key).toLowerCase().startsWith(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 246 */       return MessageFormatUtil.format("[{0}^={1}]", new Object[] { this.key, this.value });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class AttributeWithValueEnding
/*     */     extends AttributeKeyPair
/*     */   {
/*     */     public AttributeWithValueEnding(String key, String value) {
/* 256 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 261 */       return (element.hasAttr(this.key) && element.attr(this.key).toLowerCase().endsWith(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 266 */       return MessageFormatUtil.format("[{0}$={1}]", new Object[] { this.key, this.value });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class AttributeWithValueContaining
/*     */     extends AttributeKeyPair
/*     */   {
/*     */     public AttributeWithValueContaining(String key, String value) {
/* 276 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 281 */       return (element.hasAttr(this.key) && element.attr(this.key).toLowerCase().contains(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 286 */       return MessageFormatUtil.format("[{0}*={1}]", new Object[] { this.key, this.value });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class AttributeWithValueMatching
/*     */     extends Evaluator
/*     */   {
/*     */     String key;
/*     */     
/*     */     Pattern pattern;
/*     */     
/*     */     public AttributeWithValueMatching(String key, Pattern pattern) {
/* 299 */       this.key = key.trim().toLowerCase();
/* 300 */       this.pattern = pattern;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 305 */       return (element.hasAttr(this.key) && PortUtil.hasMatch(this.pattern, element.attr(this.key)));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 310 */       return MessageFormatUtil.format("[{0}~={1}]", new Object[] { this.key, this.pattern.toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class AttributeKeyPair
/*     */     extends Evaluator
/*     */   {
/*     */     String key;
/*     */     
/*     */     String value;
/*     */     
/*     */     public AttributeKeyPair(String key, String value) {
/* 323 */       Validate.notEmpty(key);
/* 324 */       Validate.notEmpty(value);
/*     */       
/* 326 */       this.key = key.trim().toLowerCase();
/* 327 */       if ((value.startsWith("\"") && value.endsWith("\"")) || (value
/* 328 */         .startsWith("'") && value.endsWith("'"))) {
/* 329 */         value = value.substring(1, value.length() - 1);
/*     */       }
/* 331 */       this.value = value.trim().toLowerCase();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class AllElements
/*     */     extends Evaluator
/*     */   {
/*     */     public boolean matches(Element root, Element element) {
/* 342 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 347 */       return "*";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class IndexLessThan
/*     */     extends IndexEvaluator
/*     */   {
/*     */     public IndexLessThan(int index) {
/* 356 */       super(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 361 */       return (element.elementSiblingIndex() < this.index);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 366 */       return MessageFormatUtil.format(":lt({0})", new Object[] { Integer.valueOf(this.index) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class IndexGreaterThan
/*     */     extends IndexEvaluator
/*     */   {
/*     */     public IndexGreaterThan(int index) {
/* 376 */       super(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 381 */       return (element.elementSiblingIndex() > this.index);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 386 */       return MessageFormatUtil.format(":gt({0})", new Object[] { Integer.valueOf(this.index) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class IndexEquals
/*     */     extends IndexEvaluator
/*     */   {
/*     */     public IndexEquals(int index) {
/* 396 */       super(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 401 */       return (element.elementSiblingIndex() == this.index);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 406 */       return MessageFormatUtil.format(":eq({0})", new Object[] { Integer.valueOf(this.index) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class IsLastChild
/*     */     extends Evaluator
/*     */   {
/*     */     public boolean matches(Element root, Element element) {
/* 417 */       Element p = (Element)element.parent();
/* 418 */       return (p != null && !(p instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Document) && element.elementSiblingIndex() == p.children().size() - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 423 */       return ":last-child";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IsFirstOfType extends IsNthOfType {
/*     */     public IsFirstOfType() {
/* 429 */       super(0, 1);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 433 */       return ":first-of-type";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IsLastOfType extends IsNthLastOfType {
/*     */     public IsLastOfType() {
/* 439 */       super(0, 1);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 443 */       return ":last-of-type";
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class CssNthEvaluator extends Evaluator {
/*     */     protected final int a;
/*     */     protected final int b;
/*     */     
/*     */     public CssNthEvaluator(int a, int b) {
/* 452 */       this.a = a;
/* 453 */       this.b = b;
/*     */     }
/*     */     public CssNthEvaluator(int b) {
/* 456 */       this(0, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 461 */       Element p = (Element)element.parent();
/* 462 */       if (p == null || p instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Document) return false;
/*     */       
/* 464 */       int pos = calculatePosition(root, element);
/* 465 */       if (this.a == 0) return (pos == this.b);
/*     */       
/* 467 */       return ((pos - this.b) * this.a >= 0 && (pos - this.b) % this.a == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 472 */       if (this.a == 0)
/* 473 */         return MessageFormatUtil.format(":{0}({1})", new Object[] { getPseudoClass(), Integer.valueOf(this.b) }); 
/* 474 */       if (this.b == 0)
/* 475 */         return MessageFormatUtil.format(":{0}({1}n)", new Object[] { getPseudoClass(), Integer.valueOf(this.a) }); 
/* 476 */       return MessageFormatUtil.format(":{0}({1}n{2,number,+#;-#})", new Object[] { getPseudoClass(), Integer.valueOf(this.a), Integer.valueOf(this.b) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract String getPseudoClass();
/*     */ 
/*     */     
/*     */     protected abstract int calculatePosition(Element param1Element1, Element param1Element2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class IsNthChild
/*     */     extends CssNthEvaluator
/*     */   {
/*     */     public IsNthChild(int a, int b) {
/* 492 */       super(a, b);
/*     */     }
/*     */     
/*     */     protected int calculatePosition(Element root, Element element) {
/* 496 */       return element.elementSiblingIndex() + 1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String getPseudoClass() {
/* 501 */       return "nth-child";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class IsNthLastChild
/*     */     extends CssNthEvaluator
/*     */   {
/*     */     public IsNthLastChild(int a, int b) {
/* 512 */       super(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int calculatePosition(Element root, Element element) {
/* 517 */       return ((Element)element.parent()).children().size() - element.elementSiblingIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     protected String getPseudoClass() {
/* 522 */       return "nth-last-child";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IsNthOfType
/*     */     extends CssNthEvaluator
/*     */   {
/*     */     public IsNthOfType(int a, int b) {
/* 532 */       super(a, b);
/*     */     }
/*     */     
/*     */     protected int calculatePosition(Element root, Element element) {
/* 536 */       int pos = 0;
/* 537 */       Elements family = ((Element)element.parent()).children();
/* 538 */       for (Element el : family) {
/* 539 */         if (el.tag().equals(element.tag())) pos++; 
/* 540 */         if (el == element)
/*     */           break; 
/* 542 */       }  return pos;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String getPseudoClass() {
/* 547 */       return "nth-of-type";
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IsNthLastOfType
/*     */     extends CssNthEvaluator {
/*     */     public IsNthLastOfType(int a, int b) {
/* 554 */       super(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int calculatePosition(Element root, Element element) {
/* 559 */       int pos = 0;
/* 560 */       Elements family = ((Element)element.parent()).children();
/* 561 */       for (int i = element.elementSiblingIndex(); i < family.size(); i++) {
/* 562 */         if (family.get(i).tag().equals(element.tag())) pos++; 
/*     */       } 
/* 564 */       return pos;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String getPseudoClass() {
/* 569 */       return "nth-last-of-type";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class IsFirstChild
/*     */     extends Evaluator
/*     */   {
/*     */     public boolean matches(Element root, Element element) {
/* 579 */       Element p = (Element)element.parent();
/* 580 */       return (p != null && !(p instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Document) && element.elementSiblingIndex() == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 585 */       return ":first-child";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class IsRoot
/*     */     extends Evaluator
/*     */   {
/*     */     public boolean matches(Element root, Element element) {
/* 596 */       Element r = (root instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Document) ? root.child(0) : root;
/* 597 */       return (element == r);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 601 */       return ":root";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IsOnlyChild
/*     */     extends Evaluator {
/*     */     public boolean matches(Element root, Element element) {
/* 608 */       Element p = (Element)element.parent();
/* 609 */       return (p != null && !(p instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Document) && element.siblingElements().size() == 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 613 */       return ":only-child";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IsOnlyOfType
/*     */     extends Evaluator {
/*     */     public boolean matches(Element root, Element element) {
/* 620 */       Element p = (Element)element.parent();
/* 621 */       if (p == null || p instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Document) return false;
/*     */       
/* 623 */       int pos = 0;
/* 624 */       Elements family = p.children();
/* 625 */       for (Element el : family) {
/* 626 */         if (el.tag().equals(element.tag())) pos++; 
/*     */       } 
/* 628 */       return (pos == 1);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 632 */       return ":only-of-type";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IsEmpty
/*     */     extends Evaluator {
/*     */     public boolean matches(Element root, Element element) {
/* 639 */       List<Node> family = element.childNodes();
/* 640 */       for (Node n : family) {
/* 641 */         if (!(n instanceof com.itextpdf.styledxmlparser.jsoup.nodes.Comment) && !(n instanceof com.itextpdf.styledxmlparser.jsoup.nodes.XmlDeclaration) && !(n instanceof com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType)) return false; 
/*     */       } 
/* 643 */       return true;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 647 */       return ":empty";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class IndexEvaluator
/*     */     extends Evaluator
/*     */   {
/*     */     int index;
/*     */ 
/*     */     
/*     */     public IndexEvaluator(int index) {
/* 660 */       this.index = index;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class ContainsText
/*     */     extends Evaluator
/*     */   {
/*     */     private String searchText;
/*     */     
/*     */     public ContainsText(String searchText) {
/* 671 */       this.searchText = searchText.toLowerCase();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 676 */       return element.text().toLowerCase().contains(this.searchText);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 681 */       return MessageFormatUtil.format(":contains({0}", new Object[] { this.searchText });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class ContainsOwnText
/*     */     extends Evaluator
/*     */   {
/*     */     private String searchText;
/*     */     
/*     */     public ContainsOwnText(String searchText) {
/* 692 */       this.searchText = searchText.toLowerCase();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 697 */       return element.ownText().toLowerCase().contains(this.searchText);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 702 */       return MessageFormatUtil.format(":containsOwn({0}", new Object[] { this.searchText });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Matches
/*     */     extends Evaluator
/*     */   {
/*     */     private Pattern pattern;
/*     */     
/*     */     public Matches(Pattern pattern) {
/* 713 */       this.pattern = pattern;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 718 */       return PortUtil.hasMatch(this.pattern, element.text());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 723 */       return MessageFormatUtil.format(":matches({0}", new Object[] { this.pattern });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class MatchesOwn
/*     */     extends Evaluator
/*     */   {
/*     */     private Pattern pattern;
/*     */     
/*     */     public MatchesOwn(Pattern pattern) {
/* 734 */       this.pattern = pattern;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Element root, Element element) {
/* 739 */       return PortUtil.hasMatch(this.pattern, element.ownText());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 744 */       return MessageFormatUtil.format(":matchesOwn({0}", new Object[] { this.pattern });
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/select/Evaluator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */