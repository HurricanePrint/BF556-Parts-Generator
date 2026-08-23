/*      */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*      */ 
/*      */ import com.itextpdf.io.util.MessageFormatUtil;
/*      */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
/*      */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*      */ import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
/*      */ import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
/*      */ import com.itextpdf.styledxmlparser.jsoup.select.Collector;
/*      */ import com.itextpdf.styledxmlparser.jsoup.select.Elements;
/*      */ import com.itextpdf.styledxmlparser.jsoup.select.Evaluator;
/*      */ import com.itextpdf.styledxmlparser.jsoup.select.NodeTraversor;
/*      */ import com.itextpdf.styledxmlparser.jsoup.select.NodeVisitor;
/*      */ import com.itextpdf.styledxmlparser.jsoup.select.Selector;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Pattern;
/*      */ import java.util.regex.PatternSyntaxException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Element
/*      */   extends Node
/*      */ {
/*      */   private Tag tag;
/*   81 */   private static final Pattern classSplit = Pattern.compile("\\s+");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element(Tag tag, String baseUri, Attributes attributes) {
/*   93 */     super(baseUri, attributes);
/*      */     
/*   95 */     Validate.notNull(tag);
/*   96 */     this.tag = tag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element(Tag tag, String baseUri) {
/*  108 */     this(tag, baseUri, new Attributes());
/*      */   }
/*      */ 
/*      */   
/*      */   public String nodeName() {
/*  113 */     return this.tag.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String tagName() {
/*  122 */     return this.tag.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element tagName(String tagName) {
/*  133 */     Validate.notEmpty(tagName, "Tag name must not be empty.");
/*  134 */     this.tag = Tag.valueOf(tagName);
/*  135 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Tag tag() {
/*  144 */     return this.tag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlock() {
/*  154 */     return this.tag.isBlock();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String id() {
/*  163 */     return this.attributes.get("id");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node attr(String attributeKey, String attributeValue) {
/*  173 */     super.attr(attributeKey, attributeValue);
/*  174 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element attr(String attributeKey, boolean attributeValue) {
/*  188 */     this.attributes.put(attributeKey, attributeValue);
/*  189 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, String> dataset() {
/*  206 */     return this.attributes.dataset();
/*      */   }
/*      */ 
/*      */   
/*      */   public final Node parent() {
/*  211 */     return this.parentNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements parents() {
/*  219 */     Elements parents = new Elements();
/*  220 */     accumulateParents(this, parents);
/*  221 */     return parents;
/*      */   }
/*      */   
/*      */   private static void accumulateParents(Element el, Elements parents) {
/*  225 */     Element parent = (Element)el.parent();
/*  226 */     if (parent != null && !parent.tagName().equals("#root")) {
/*  227 */       parents.add(parent);
/*  228 */       accumulateParents(parent, parents);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element child(int index) {
/*  243 */     return (Element)children().get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements children() {
/*  257 */     List<Element> elements = new ArrayList<>(this.childNodes.size());
/*  258 */     for (Node node : this.childNodes) {
/*  259 */       if (node instanceof Element)
/*  260 */         elements.add((Element)node); 
/*      */     } 
/*  262 */     return new Elements(elements);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<TextNode> textNodes() {
/*  282 */     List<TextNode> textNodes = new ArrayList<>();
/*  283 */     for (Node node : this.childNodes) {
/*  284 */       if (node instanceof TextNode)
/*  285 */         textNodes.add((TextNode)node); 
/*      */     } 
/*  287 */     return Collections.unmodifiableList(textNodes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DataNode> dataNodes() {
/*  300 */     List<DataNode> dataNodes = new ArrayList<>();
/*  301 */     for (Node node : this.childNodes) {
/*  302 */       if (node instanceof DataNode)
/*  303 */         dataNodes.add((DataNode)node); 
/*      */     } 
/*  305 */     return Collections.unmodifiableList(dataNodes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements select(String cssQuery) {
/*  328 */     return Selector.select(cssQuery, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element appendChild(Node child) {
/*  338 */     Validate.notNull(child);
/*      */ 
/*      */     
/*  341 */     reparentChild(child);
/*  342 */     ensureChildNodes();
/*  343 */     this.childNodes.add(child);
/*  344 */     child.setSiblingIndex(this.childNodes.size() - 1);
/*  345 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element prependChild(Node child) {
/*  355 */     return insertChild(0, child);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element insertChild(int index, Node child) {
/*  368 */     if (index == -1) {
/*  369 */       return appendChild(child);
/*      */     }
/*  371 */     Validate.notNull(child);
/*  372 */     addChildren(index, new Node[] { child });
/*  373 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element insertChildren(int index, Collection<? extends Node> children) {
/*  387 */     Validate.notNull(children, "Children collection to be inserted must not be null.");
/*  388 */     int currentSize = childNodeSize();
/*  389 */     if (index < 0) index += currentSize + 1; 
/*  390 */     Validate.isTrue((index >= 0 && index <= currentSize), "Insert position out of bounds.");
/*      */     
/*  392 */     ArrayList<Node> nodes = new ArrayList<>(children);
/*  393 */     Node[] nodeArray = nodes.<Node>toArray(new Node[nodes.size()]);
/*  394 */     addChildren(index, nodeArray);
/*  395 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element appendElement(String tagName) {
/*  406 */     Element child = new Element(Tag.valueOf(tagName), baseUri());
/*  407 */     appendChild(child);
/*  408 */     return child;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element prependElement(String tagName) {
/*  419 */     Element child = new Element(Tag.valueOf(tagName), baseUri());
/*  420 */     prependChild(child);
/*  421 */     return child;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element appendText(String text) {
/*  431 */     Validate.notNull(text);
/*  432 */     TextNode node = new TextNode(text, baseUri());
/*  433 */     appendChild(node);
/*  434 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element prependText(String text) {
/*  444 */     Validate.notNull(text);
/*  445 */     TextNode node = new TextNode(text, baseUri());
/*  446 */     prependChild(node);
/*  447 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element append(String html) {
/*  457 */     Validate.notNull(html);
/*      */     
/*  459 */     List<Node> nodes = Parser.parseFragment(html, this, baseUri());
/*  460 */     addChildren(nodes.<Node>toArray(new Node[nodes.size()]));
/*  461 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element prepend(String html) {
/*  471 */     Validate.notNull(html);
/*      */     
/*  473 */     List<Node> nodes = Parser.parseFragment(html, this, baseUri());
/*  474 */     addChildren(0, nodes.<Node>toArray(new Node[nodes.size()]));
/*  475 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node before(String html) {
/*  487 */     return super.before(html);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node before(Node node) {
/*  498 */     return super.before(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node after(String html) {
/*  510 */     return super.after(html);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node after(Node node) {
/*  521 */     return super.after(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element empty() {
/*  529 */     this.childNodes.clear();
/*  530 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node wrap(String html) {
/*  541 */     return super.wrap(html);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String cssSelector() {
/*  554 */     if (id().length() > 0) {
/*  555 */       return "#" + id();
/*      */     }
/*      */     
/*  558 */     String tagName = tagName().replace(':', '|');
/*  559 */     StringBuilder selector = new StringBuilder(tagName);
/*  560 */     String classes = StringUtil.join(classNames(), ".");
/*  561 */     if (classes.length() > 0) {
/*  562 */       selector.append('.').append(classes);
/*      */     }
/*  564 */     if (parent() == null || parent() instanceof Document) {
/*  565 */       return selector.toString();
/*      */     }
/*  567 */     selector.insert(0, " > ");
/*  568 */     if (((Element)parent()).select(selector.toString()).size() > 1)
/*  569 */       selector.append(MessageFormatUtil.format(":nth-child({0})", new Object[] {
/*  570 */               Integer.valueOf(elementSiblingIndex() + 1)
/*      */             })); 
/*  572 */     return ((Element)parent()).cssSelector() + selector.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements siblingElements() {
/*  581 */     if (this.parentNode == null) {
/*  582 */       return new Elements(0);
/*      */     }
/*  584 */     Elements elements1 = ((Element)parent()).children();
/*  585 */     Elements siblings = new Elements(elements1.size() - 1);
/*  586 */     for (Element el : elements1) {
/*  587 */       if (el != this)
/*  588 */         siblings.add(el); 
/*  589 */     }  return siblings;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element nextElementSibling() {
/*  602 */     if (this.parentNode == null) return null; 
/*  603 */     Elements<Element> elements = ((Element)parent()).children();
/*  604 */     int index = indexInList(this, (List<Element>)elements);
/*  605 */     Validate.isTrue((index >= 0));
/*      */     
/*  607 */     if (elements.size() > index + 1) {
/*  608 */       return elements.get(index + 1);
/*      */     }
/*  610 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element previousElementSibling() {
/*  619 */     if (this.parentNode == null) return null; 
/*  620 */     Elements<Element> elements = ((Element)parent()).children();
/*  621 */     int index = indexInList(this, (List<Element>)elements);
/*  622 */     Validate.isTrue((index >= 0));
/*  623 */     if (index > 0) {
/*  624 */       return elements.get(index - 1);
/*      */     }
/*  626 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element firstElementSibling() {
/*  635 */     Elements<Element> elements = ((Element)parent()).children();
/*  636 */     return (elements.size() > 1) ? elements.get(0) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int elementSiblingIndex() {
/*  645 */     if (parent() == null) return 0; 
/*  646 */     return indexInList(this, (List<Element>)((Element)parent()).children());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element lastElementSibling() {
/*  654 */     Elements<Element> elements = ((Element)parent()).children();
/*  655 */     return (elements.size() > 1) ? elements.get(elements.size() - 1) : null;
/*      */   }
/*      */   
/*      */   private static <E extends Element> int indexInList(Element search, List<E> elements) {
/*  659 */     Validate.notNull(search);
/*  660 */     Validate.notNull(elements);
/*      */     
/*  662 */     for (int i = 0; i < elements.size(); i++) {
/*  663 */       Element element = (Element)elements.get(i);
/*  664 */       if (element == search)
/*  665 */         return i; 
/*      */     } 
/*  667 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByTag(String tagName) {
/*  678 */     Validate.notEmpty(tagName);
/*  679 */     tagName = tagName.toLowerCase().trim();
/*      */     
/*  681 */     return Collector.collect((Evaluator)new Evaluator.Tag(tagName), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element getElementById(String id) {
/*  694 */     Validate.notEmpty(id);
/*      */     
/*  696 */     Elements elements = Collector.collect((Evaluator)new Evaluator.Id(id), this);
/*  697 */     if (elements.size() > 0) {
/*  698 */       return (Element)elements.get(0);
/*      */     }
/*  700 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByClass(String className) {
/*  715 */     Validate.notEmpty(className);
/*      */     
/*  717 */     return Collector.collect((Evaluator)new Evaluator.Class(className), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttribute(String key) {
/*  727 */     Validate.notEmpty(key);
/*  728 */     key = key.trim().toLowerCase();
/*      */     
/*  730 */     return Collector.collect((Evaluator)new Evaluator.Attribute(key), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttributeStarting(String keyPrefix) {
/*  740 */     Validate.notEmpty(keyPrefix);
/*  741 */     keyPrefix = keyPrefix.trim().toLowerCase();
/*      */     
/*  743 */     return Collector.collect((Evaluator)new Evaluator.AttributeStarting(keyPrefix), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttributeValue(String key, String value) {
/*  754 */     return Collector.collect((Evaluator)new Evaluator.AttributeWithValue(key, value), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttributeValueNot(String key, String value) {
/*  765 */     return Collector.collect((Evaluator)new Evaluator.AttributeWithValueNot(key, value), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttributeValueStarting(String key, String valuePrefix) {
/*  776 */     return Collector.collect((Evaluator)new Evaluator.AttributeWithValueStarting(key, valuePrefix), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttributeValueEnding(String key, String valueSuffix) {
/*  787 */     return Collector.collect((Evaluator)new Evaluator.AttributeWithValueEnding(key, valueSuffix), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttributeValueContaining(String key, String match) {
/*  798 */     return Collector.collect((Evaluator)new Evaluator.AttributeWithValueContaining(key, match), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttributeValueMatching(String key, Pattern pattern) {
/*  808 */     return Collector.collect((Evaluator)new Evaluator.AttributeWithValueMatching(key, pattern), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByAttributeValueMatching(String key, String regex) {
/*      */     Pattern pattern;
/*      */     try {
/*  823 */       pattern = Pattern.compile(regex);
/*  824 */     } catch (PatternSyntaxException e) {
/*  825 */       throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
/*      */     } 
/*  827 */     return getElementsByAttributeValueMatching(key, pattern);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByIndexLessThan(int index) {
/*  836 */     return Collector.collect((Evaluator)new Evaluator.IndexLessThan(index), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByIndexGreaterThan(int index) {
/*  845 */     return Collector.collect((Evaluator)new Evaluator.IndexGreaterThan(index), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsByIndexEquals(int index) {
/*  854 */     return Collector.collect((Evaluator)new Evaluator.IndexEquals(index), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsContainingText(String searchText) {
/*  865 */     return Collector.collect((Evaluator)new Evaluator.ContainsText(searchText), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsContainingOwnText(String searchText) {
/*  876 */     return Collector.collect((Evaluator)new Evaluator.ContainsOwnText(searchText), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsMatchingText(Pattern pattern) {
/*  886 */     return Collector.collect((Evaluator)new Evaluator.Matches(pattern), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsMatchingText(String regex) {
/*      */     Pattern pattern;
/*      */     try {
/*  900 */       pattern = Pattern.compile(regex);
/*  901 */     } catch (PatternSyntaxException e) {
/*  902 */       throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
/*      */     } 
/*  904 */     return getElementsMatchingText(pattern);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsMatchingOwnText(Pattern pattern) {
/*  914 */     return Collector.collect((Evaluator)new Evaluator.MatchesOwn(pattern), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getElementsMatchingOwnText(String regex) {
/*      */     Pattern pattern;
/*      */     try {
/*  928 */       pattern = Pattern.compile(regex);
/*  929 */     } catch (PatternSyntaxException e) {
/*  930 */       throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
/*      */     } 
/*  932 */     return getElementsMatchingOwnText(pattern);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Elements getAllElements() {
/*  941 */     return Collector.collect((Evaluator)new Evaluator.AllElements(), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String text() {
/*  954 */     final StringBuilder accum = new StringBuilder();
/*  955 */     (new NodeTraversor(new NodeVisitor() {
/*      */           public void head(Node node, int depth) {
/*  957 */             if (node instanceof TextNode) {
/*  958 */               TextNode textNode = (TextNode)node;
/*  959 */               Element.appendNormalisedText(accum, textNode);
/*  960 */             } else if (node instanceof Element) {
/*  961 */               Element element = (Element)node;
/*  962 */               if (accum.length() > 0 && (element
/*  963 */                 .isBlock() || element.tag.getName().equals("br")) && 
/*  964 */                 !TextNode.lastCharIsWhitespace(accum)) {
/*  965 */                 accum.append(" ");
/*      */               }
/*      */             } 
/*      */           }
/*      */           
/*      */           public void tail(Node node, int depth) {}
/*  971 */         })).traverse(this);
/*  972 */     return accum.toString().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String ownText() {
/*  987 */     StringBuilder sb = new StringBuilder();
/*  988 */     ownText(sb);
/*  989 */     return sb.toString().trim();
/*      */   }
/*      */   
/*      */   private void ownText(StringBuilder accum) {
/*  993 */     for (Node child : this.childNodes) {
/*  994 */       if (child instanceof TextNode) {
/*  995 */         TextNode textNode = (TextNode)child;
/*  996 */         appendNormalisedText(accum, textNode); continue;
/*  997 */       }  if (child instanceof Element) {
/*  998 */         appendWhitespaceIfBr((Element)child, accum);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void appendNormalisedText(StringBuilder accum, TextNode textNode) {
/* 1004 */     String text = textNode.getWholeText();
/*      */     
/* 1006 */     if (preserveWhitespace(textNode.parentNode)) {
/* 1007 */       accum.append(text);
/*      */     } else {
/* 1009 */       StringUtil.appendNormalisedWhitespace(accum, text, TextNode.lastCharIsWhitespace(accum));
/*      */     } 
/*      */   }
/*      */   private static void appendWhitespaceIfBr(Element element, StringBuilder accum) {
/* 1013 */     if (element.tag.getName().equals("br") && !TextNode.lastCharIsWhitespace(accum)) {
/* 1014 */       accum.append(" ");
/*      */     }
/*      */   }
/*      */   
/*      */   static boolean preserveWhitespace(Node node) {
/* 1019 */     if (node != null && node instanceof Element) {
/* 1020 */       Element element = (Element)node;
/* 1021 */       return (element.tag.preserveWhitespace() || (element
/* 1022 */         .parent() != null && ((Element)element.parent()).tag.preserveWhitespace()));
/*      */     } 
/* 1024 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element text(String text) {
/* 1033 */     Validate.notNull(text);
/*      */     
/* 1035 */     empty();
/* 1036 */     TextNode textNode = new TextNode(text, this.baseUri);
/* 1037 */     appendChild(textNode);
/*      */     
/* 1039 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasText() {
/* 1047 */     for (Node child : this.childNodes) {
/* 1048 */       if (child instanceof TextNode) {
/* 1049 */         TextNode textNode = (TextNode)child;
/* 1050 */         if (!textNode.isBlank())
/* 1051 */           return true;  continue;
/* 1052 */       }  if (child instanceof Element) {
/* 1053 */         Element el = (Element)child;
/* 1054 */         if (el.hasText())
/* 1055 */           return true; 
/*      */       } 
/*      */     } 
/* 1058 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String data() {
/* 1068 */     StringBuilder sb = new StringBuilder();
/*      */     
/* 1070 */     for (Node childNode : this.childNodes) {
/* 1071 */       if (childNode instanceof DataNode) {
/* 1072 */         DataNode data = (DataNode)childNode;
/* 1073 */         sb.append(data.getWholeData()); continue;
/* 1074 */       }  if (childNode instanceof Element) {
/* 1075 */         Element element = (Element)childNode;
/* 1076 */         String elementData = element.data();
/* 1077 */         sb.append(elementData);
/*      */       } 
/*      */     } 
/* 1080 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String className() {
/* 1089 */     return attr("class").trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> classNames() {
/* 1099 */     String[] names = classSplit.split(className());
/* 1100 */     Set<String> classNames = new LinkedHashSet<>(Arrays.asList(names));
/* 1101 */     classNames.remove("");
/*      */     
/* 1103 */     return classNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element classNames(Set<String> classNames) {
/* 1112 */     Validate.notNull(classNames);
/* 1113 */     this.attributes.put("class", StringUtil.join(classNames, " "));
/* 1114 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasClass(String className) {
/* 1131 */     String classAttr = this.attributes.get("class");
/* 1132 */     if (classAttr.equals("") || classAttr.length() < className.length()) {
/* 1133 */       return false;
/*      */     }
/* 1135 */     String[] classes = classSplit.split(classAttr);
/* 1136 */     for (String name : classes) {
/* 1137 */       if (className.equalsIgnoreCase(name)) {
/* 1138 */         return true;
/*      */       }
/*      */     } 
/* 1141 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element addClass(String className) {
/* 1150 */     Validate.notNull(className);
/*      */     
/* 1152 */     Set<String> classes = classNames();
/* 1153 */     classes.add(className);
/* 1154 */     classNames(classes);
/*      */     
/* 1156 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element removeClass(String className) {
/* 1165 */     Validate.notNull(className);
/*      */     
/* 1167 */     Set<String> classes = classNames();
/* 1168 */     classes.remove(className);
/* 1169 */     classNames(classes);
/*      */     
/* 1171 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element toggleClass(String className) {
/* 1180 */     Validate.notNull(className);
/*      */     
/* 1182 */     Set<String> classes = classNames();
/* 1183 */     if (classes.contains(className)) {
/* 1184 */       classes.remove(className);
/*      */     } else {
/* 1186 */       classes.add(className);
/* 1187 */     }  classNames(classes);
/*      */     
/* 1189 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String val() {
/* 1197 */     if (tagName().equals("textarea")) {
/* 1198 */       return text();
/*      */     }
/* 1200 */     return attr("value");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element val(String value) {
/* 1209 */     if (tagName().equals("textarea")) {
/* 1210 */       text(value);
/*      */     } else {
/* 1212 */       attr("value", value);
/* 1213 */     }  return this;
/*      */   }
/*      */   
/*      */   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
/* 1217 */     if (out.prettyPrint() && (this.tag.formatAsBlock() || (parent() != null && ((Element)parent()).tag().formatAsBlock()) || out.outline())) {
/* 1218 */       if (accum instanceof StringBuilder) {
/* 1219 */         if (((StringBuilder)accum).length() > 0)
/* 1220 */           indent(accum, depth, out); 
/*      */       } else {
/* 1222 */         indent(accum, depth, out);
/*      */       } 
/*      */     }
/* 1225 */     accum
/* 1226 */       .append("<")
/* 1227 */       .append(tagName());
/* 1228 */     this.attributes.html(accum, out);
/*      */ 
/*      */     
/* 1231 */     if (this.childNodes.isEmpty() && this.tag.isSelfClosing()) {
/* 1232 */       if (out.syntax() == Document.OutputSettings.Syntax.html && this.tag.isEmpty()) {
/* 1233 */         accum.append('>');
/*      */       } else {
/* 1235 */         accum.append(" />");
/*      */       } 
/*      */     } else {
/* 1238 */       accum.append(">");
/*      */     } 
/*      */   }
/*      */   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
/* 1242 */     if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
/* 1243 */       if (out.prettyPrint() && !this.childNodes.isEmpty() && (this.tag
/* 1244 */         .formatAsBlock() || (out.outline() && (this.childNodes.size() > 1 || (this.childNodes.size() == 1 && !(this.childNodes.get(0) instanceof TextNode))))))
/*      */       {
/* 1246 */         indent(accum, depth, out); } 
/* 1247 */       accum.append("</").append(tagName()).append(">");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String html() {
/* 1259 */     StringBuilder accum = new StringBuilder();
/* 1260 */     html(accum);
/* 1261 */     return getOutputSettings().prettyPrint() ? accum.toString().trim() : accum.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Appendable html(Appendable appendable) {
/* 1269 */     for (Node node : this.childNodes) {
/* 1270 */       node.outerHtml(appendable);
/*      */     }
/* 1272 */     return appendable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element html(String html) {
/* 1282 */     empty();
/* 1283 */     append(html);
/* 1284 */     return this;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1288 */     return outerHtml();
/*      */   }
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 1293 */     return super.clone();
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/Element.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */