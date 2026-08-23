/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.SerializationException;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
/*     */ import com.itextpdf.styledxmlparser.jsoup.select.Elements;
/*     */ import com.itextpdf.styledxmlparser.jsoup.select.NodeTraversor;
/*     */ import com.itextpdf.styledxmlparser.jsoup.select.NodeVisitor;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Node
/*     */   implements Cloneable
/*     */ {
/*  63 */   private static final List<Node> EMPTY_NODES = Collections.emptyList();
/*     */   
/*     */   Node parentNode;
/*     */   
/*     */   List<Node> childNodes;
/*     */   
/*     */   Attributes attributes;
/*     */   
/*     */   String baseUri;
/*     */   
/*     */   int siblingIndex;
/*     */   
/*     */   protected Node(String baseUri, Attributes attributes) {
/*  76 */     Validate.notNull(baseUri);
/*  77 */     Validate.notNull(attributes);
/*     */     
/*  79 */     this.childNodes = EMPTY_NODES;
/*  80 */     this.baseUri = baseUri.trim();
/*  81 */     this.attributes = attributes;
/*     */   }
/*     */   
/*     */   protected Node(String baseUri) {
/*  85 */     this(baseUri, new Attributes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Node() {
/*  92 */     this.childNodes = EMPTY_NODES;
/*  93 */     this.attributes = null;
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
/*     */   public abstract String nodeName();
/*     */ 
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
/* 118 */     Validate.notNull(attributeKey);
/*     */     
/* 120 */     if (this.attributes.hasKey(attributeKey))
/* 121 */       return this.attributes.get(attributeKey); 
/* 122 */     if (attributeKey.toLowerCase().startsWith("abs:"))
/* 123 */       return absUrl(attributeKey.substring("abs:".length())); 
/* 124 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes attributes() {
/* 132 */     return this.attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node attr(String attributeKey, String attributeValue) {
/* 142 */     this.attributes.put(attributeKey, attributeValue);
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAttr(String attributeKey) {
/* 152 */     Validate.notNull(attributeKey);
/*     */     
/* 154 */     if (attributeKey.startsWith("abs:")) {
/* 155 */       String key = attributeKey.substring("abs:".length());
/* 156 */       if (this.attributes.hasKey(key) && !absUrl(key).equals(""))
/* 157 */         return true; 
/*     */     } 
/* 159 */     return this.attributes.hasKey(attributeKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node removeAttr(String attributeKey) {
/* 168 */     Validate.notNull(attributeKey);
/* 169 */     this.attributes.remove(attributeKey);
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String baseUri() {
/* 178 */     return this.baseUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseUri(final String baseUri) {
/* 186 */     Validate.notNull(baseUri);
/*     */     
/* 188 */     traverse(new NodeVisitor() {
/*     */           public void head(Node node, int depth) {
/* 190 */             node.baseUri = baseUri;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void tail(Node node, int depth) {}
/*     */         });
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
/*     */   public String absUrl(String attributeKey) {
/* 219 */     Validate.notEmpty(attributeKey);
/*     */     
/* 221 */     if (!hasAttr(attributeKey)) {
/* 222 */       return "";
/*     */     }
/* 224 */     return StringUtil.resolve(this.baseUri, attr(attributeKey));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node childNode(int index) {
/* 234 */     return this.childNodes.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Node> childNodes() {
/* 243 */     return Collections.unmodifiableList(this.childNodes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Node> childNodesCopy() {
/* 252 */     List<Node> children = new ArrayList<>(this.childNodes.size());
/* 253 */     for (Node node : this.childNodes) {
/* 254 */       children.add((Node)node.clone());
/*     */     }
/* 256 */     return children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int childNodeSize() {
/* 264 */     return this.childNodes.size();
/*     */   }
/*     */   
/*     */   protected Node[] childNodesAsArray() {
/* 268 */     return this.childNodes.<Node>toArray(new Node[childNodeSize()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node parent() {
/* 276 */     return this.parentNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Node parentNode() {
/* 284 */     return this.parentNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document ownerDocument() {
/* 292 */     if (this instanceof Document)
/* 293 */       return (Document)this; 
/* 294 */     if (this.parentNode == null) {
/* 295 */       return null;
/*     */     }
/* 297 */     return this.parentNode.ownerDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/* 304 */     Validate.notNull(this.parentNode);
/* 305 */     this.parentNode.removeChild(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node before(String html) {
/* 315 */     addSiblingHtml(this.siblingIndex, html);
/* 316 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node before(Node node) {
/* 326 */     Validate.notNull(node);
/* 327 */     Validate.notNull(this.parentNode);
/*     */     
/* 329 */     this.parentNode.addChildren(this.siblingIndex, new Node[] { node });
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node after(String html) {
/* 340 */     addSiblingHtml(this.siblingIndex + 1, html);
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node after(Node node) {
/* 351 */     Validate.notNull(node);
/* 352 */     Validate.notNull(this.parentNode);
/*     */     
/* 354 */     this.parentNode.addChildren(this.siblingIndex + 1, new Node[] { node });
/* 355 */     return this;
/*     */   }
/*     */   
/*     */   private void addSiblingHtml(int index, String html) {
/* 359 */     Validate.notNull(html);
/* 360 */     Validate.notNull(this.parentNode);
/*     */     
/* 362 */     Element context = (parent() instanceof Element) ? (Element)parent() : null;
/* 363 */     List<Node> nodes = Parser.parseFragment(html, context, baseUri());
/* 364 */     this.parentNode.addChildren(index, nodes.<Node>toArray(new Node[nodes.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node wrap(String html) {
/* 373 */     Validate.notEmpty(html);
/*     */     
/* 375 */     Element context = (parent() instanceof Element) ? (Element)parent() : null;
/* 376 */     List<Node> wrapChildren = Parser.parseFragment(html, context, baseUri());
/* 377 */     Node wrapNode = wrapChildren.get(0);
/* 378 */     if (wrapNode == null || !(wrapNode instanceof Element)) {
/* 379 */       return null;
/*     */     }
/* 381 */     Element wrap = (Element)wrapNode;
/* 382 */     Element deepest = getDeepChild(wrap);
/* 383 */     this.parentNode.replaceChild(this, wrap);
/* 384 */     deepest.addChildren(new Node[] { this });
/*     */ 
/*     */     
/* 387 */     if (wrapChildren.size() > 0) {
/* 388 */       for (int i = 0; i < wrapChildren.size(); i++) {
/* 389 */         Node remainder = wrapChildren.get(i);
/* 390 */         remainder.parentNode.removeChild(remainder);
/* 391 */         wrap.appendChild(remainder);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node unwrap() {
/* 416 */     Validate.notNull(this.parentNode);
/*     */     
/* 418 */     Node firstChild = (this.childNodes.size() > 0) ? this.childNodes.get(0) : null;
/* 419 */     this.parentNode.addChildren(this.siblingIndex, childNodesAsArray());
/* 420 */     remove();
/*     */     
/* 422 */     return firstChild;
/*     */   }
/*     */   
/*     */   private Element getDeepChild(Element el) {
/* 426 */     Elements<Element> elements = el.children();
/* 427 */     if (elements.size() > 0) {
/* 428 */       return getDeepChild(elements.get(0));
/*     */     }
/* 430 */     return el;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceWith(Node in) {
/* 438 */     Validate.notNull(in);
/* 439 */     Validate.notNull(this.parentNode);
/* 440 */     this.parentNode.replaceChild(this, in);
/*     */   }
/*     */   
/*     */   protected void setParentNode(Node parentNode) {
/* 444 */     if (this.parentNode != null)
/* 445 */       this.parentNode.removeChild(this); 
/* 446 */     this.parentNode = parentNode;
/*     */   }
/*     */   
/*     */   protected void replaceChild(Node out, Node in) {
/* 450 */     Validate.isTrue((out.parentNode == this));
/* 451 */     Validate.notNull(in);
/* 452 */     if (in.parentNode != null) {
/* 453 */       in.parentNode.removeChild(in);
/*     */     }
/* 455 */     int index = out.siblingIndex;
/* 456 */     this.childNodes.set(index, in);
/* 457 */     in.parentNode = this;
/* 458 */     in.setSiblingIndex(index);
/* 459 */     out.parentNode = null;
/*     */   }
/*     */   
/*     */   protected void removeChild(Node out) {
/* 463 */     Validate.isTrue((out.parentNode == this));
/* 464 */     int index = out.siblingIndex;
/* 465 */     this.childNodes.remove(index);
/* 466 */     reindexChildren(index);
/* 467 */     out.parentNode = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addChildren(Node... children) {
/* 472 */     for (Node child : children) {
/* 473 */       reparentChild(child);
/* 474 */       ensureChildNodes();
/* 475 */       this.childNodes.add(child);
/* 476 */       child.setSiblingIndex(this.childNodes.size() - 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addChildren(int index, Node... children) {
/* 481 */     Validate.noNullElements((Object[])children);
/* 482 */     ensureChildNodes();
/* 483 */     for (int i = children.length - 1; i >= 0; i--) {
/* 484 */       Node in = children[i];
/* 485 */       reparentChild(in);
/* 486 */       this.childNodes.add(index, in);
/* 487 */       reindexChildren(index);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void ensureChildNodes() {
/* 492 */     if (this.childNodes == EMPTY_NODES) {
/* 493 */       this.childNodes = new ArrayList<>(4);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void reparentChild(Node child) {
/* 498 */     if (child.parentNode != null)
/* 499 */       child.parentNode.removeChild(child); 
/* 500 */     child.setParentNode(this);
/*     */   }
/*     */   
/*     */   private void reindexChildren(int start) {
/* 504 */     for (int i = start; i < this.childNodes.size(); i++) {
/* 505 */       ((Node)this.childNodes.get(i)).setSiblingIndex(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Node> siblingNodes() {
/* 515 */     if (this.parentNode == null) {
/* 516 */       return Collections.emptyList();
/*     */     }
/* 518 */     List<Node> nodes = this.parentNode.childNodes;
/* 519 */     List<Node> siblings = new ArrayList<>(nodes.size() - 1);
/* 520 */     for (Node node : nodes) {
/* 521 */       if (node != this)
/* 522 */         siblings.add(node); 
/* 523 */     }  return siblings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node nextSibling() {
/* 531 */     if (this.parentNode == null) {
/* 532 */       return null;
/*     */     }
/* 534 */     List<Node> siblings = this.parentNode.childNodes;
/* 535 */     int index = this.siblingIndex + 1;
/* 536 */     if (siblings.size() > index) {
/* 537 */       return siblings.get(index);
/*     */     }
/* 539 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node previousSibling() {
/* 547 */     if (this.parentNode == null) {
/* 548 */       return null;
/*     */     }
/* 550 */     if (this.siblingIndex > 0) {
/* 551 */       return this.parentNode.childNodes.get(this.siblingIndex - 1);
/*     */     }
/* 553 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int siblingIndex() {
/* 563 */     return this.siblingIndex;
/*     */   }
/*     */   
/*     */   protected void setSiblingIndex(int siblingIndex) {
/* 567 */     this.siblingIndex = siblingIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node traverse(NodeVisitor nodeVisitor) {
/* 576 */     Validate.notNull(nodeVisitor);
/* 577 */     NodeTraversor traversor = new NodeTraversor(nodeVisitor);
/* 578 */     traversor.traverse(this);
/* 579 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String outerHtml() {
/* 587 */     StringBuilder accum = new StringBuilder(128);
/* 588 */     outerHtml(accum);
/* 589 */     return accum.toString();
/*     */   }
/*     */   
/*     */   protected void outerHtml(Appendable accum) {
/* 593 */     (new NodeTraversor(new OuterHtmlVisitor(accum, getOutputSettings()))).traverse(this);
/*     */   }
/*     */ 
/*     */   
/*     */   Document.OutputSettings getOutputSettings() {
/* 598 */     return (ownerDocument() != null) ? ownerDocument().outputSettings() : (new Document("")).outputSettings();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void outerHtmlHead(Appendable paramAppendable, int paramInt, Document.OutputSettings paramOutputSettings) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void outerHtmlTail(Appendable paramAppendable, int paramInt, Document.OutputSettings paramOutputSettings) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Appendable html(Appendable appendable) {
/* 617 */     outerHtml(appendable);
/* 618 */     return appendable;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 622 */     return outerHtml();
/*     */   }
/*     */   
/*     */   protected void indent(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
/* 626 */     accum.append("\n").append(StringUtil.padding(depth * out.indentAmount()));
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
/*     */   public boolean equals(Object o) {
/* 638 */     return (this == o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSameValue(Object o) {
/* 649 */     if (this == o) return true; 
/* 650 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 652 */     return outerHtml().equals(((Node)o).outerHtml());
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
/*     */   public Object clone() {
/* 665 */     Node thisClone = doClone(null);
/*     */ 
/*     */     
/* 668 */     LinkedList<Node> nodesToProcess = new LinkedList<>();
/* 669 */     nodesToProcess.add(thisClone);
/*     */     
/* 671 */     while (!nodesToProcess.isEmpty()) {
/* 672 */       Node currParent = nodesToProcess.remove();
/*     */       
/* 674 */       for (int i = 0; i < currParent.childNodes.size(); i++) {
/* 675 */         Node childClone = ((Node)currParent.childNodes.get(i)).doClone(currParent);
/* 676 */         currParent.childNodes.set(i, childClone);
/* 677 */         nodesToProcess.add(childClone);
/*     */       } 
/*     */     } 
/*     */     
/* 681 */     return thisClone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Node doClone(Node parent) {
/* 690 */     Node clone = (Node)partialClone();
/* 691 */     clone.parentNode = parent;
/* 692 */     clone.siblingIndex = (parent == null) ? 0 : this.siblingIndex;
/* 693 */     clone.attributes = (this.attributes != null) ? (Attributes)this.attributes.clone() : null;
/* 694 */     clone.baseUri = this.baseUri;
/* 695 */     clone.childNodes = new ArrayList<>(this.childNodes.size());
/*     */     
/* 697 */     for (Node child : this.childNodes) {
/* 698 */       clone.childNodes.add(child);
/*     */     }
/* 700 */     return clone;
/*     */   }
/*     */   
/*     */   private Object partialClone() {
/*     */     try {
/* 705 */       return super.clone();
/* 706 */     } catch (CloneNotSupportedException e) {
/* 707 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class OuterHtmlVisitor implements NodeVisitor {
/*     */     private Appendable accum;
/*     */     private Document.OutputSettings out;
/*     */     
/*     */     OuterHtmlVisitor(Appendable accum, Document.OutputSettings out) {
/* 716 */       this.accum = accum;
/* 717 */       this.out = out;
/*     */     }
/*     */     
/*     */     public void head(Node node, int depth) {
/*     */       try {
/* 722 */         node.outerHtmlHead(this.accum, depth, this.out);
/* 723 */       } catch (IOException exception) {
/* 724 */         throw new SerializationException(exception);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void tail(Node node, int depth) {
/* 729 */       if (!node.nodeName().equals("#text"))
/*     */         try {
/* 731 */           node.outerHtmlTail(this.accum, depth, this.out);
/* 732 */         } catch (IOException exception) {
/* 733 */           throw new SerializationException(exception);
/*     */         }  
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/Node.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */