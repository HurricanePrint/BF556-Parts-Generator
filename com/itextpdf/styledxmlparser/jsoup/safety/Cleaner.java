/*     */ package com.itextpdf.styledxmlparser.jsoup.safety;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.DataNode;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
/*     */ import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
/*     */ import com.itextpdf.styledxmlparser.jsoup.select.NodeTraversor;
/*     */ import com.itextpdf.styledxmlparser.jsoup.select.NodeVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cleaner
/*     */ {
/*     */   private Whitelist whitelist;
/*     */   
/*     */   public Cleaner(Whitelist whitelist) {
/*  79 */     Validate.notNull(whitelist);
/*  80 */     this.whitelist = whitelist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document clean(Document dirtyDocument) {
/*  90 */     Validate.notNull(dirtyDocument);
/*     */     
/*  92 */     Document clean = Document.createShell(dirtyDocument.baseUri());
/*  93 */     if (dirtyDocument.body() != null) {
/*  94 */       copySafeNodes(dirtyDocument.body(), clean.body());
/*     */     }
/*  96 */     return clean;
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
/*     */   public boolean isValid(Document dirtyDocument) {
/* 111 */     Validate.notNull(dirtyDocument);
/*     */     
/* 113 */     Document clean = Document.createShell(dirtyDocument.baseUri());
/* 114 */     int numDiscarded = copySafeNodes(dirtyDocument.body(), clean.body());
/* 115 */     return (numDiscarded == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private final class CleaningVisitor
/*     */     implements NodeVisitor
/*     */   {
/* 122 */     int numDiscarded = 0;
/*     */     final Element root;
/*     */     Element destination;
/*     */     
/*     */     CleaningVisitor(Element root, Element destination) {
/* 127 */       this.root = root;
/* 128 */       this.destination = destination;
/*     */     }
/*     */     
/*     */     public void head(Node source, int depth) {
/* 132 */       if (source instanceof Element) {
/* 133 */         Element sourceEl = (Element)source;
/*     */         
/* 135 */         if (Cleaner.this.whitelist.isSafeTag(sourceEl.tagName())) {
/* 136 */           Cleaner.ElementMeta meta = Cleaner.this.createSafeElement(sourceEl);
/* 137 */           Element destChild = meta.el;
/* 138 */           this.destination.appendChild((Node)destChild);
/*     */           
/* 140 */           this.numDiscarded += meta.numAttribsDiscarded;
/* 141 */           this.destination = destChild;
/* 142 */         } else if (source != this.root) {
/* 143 */           this.numDiscarded++;
/*     */         } 
/* 145 */       } else if (source instanceof TextNode) {
/* 146 */         TextNode sourceText = (TextNode)source;
/* 147 */         TextNode destText = new TextNode(sourceText.getWholeText(), source.baseUri());
/* 148 */         this.destination.appendChild((Node)destText);
/* 149 */       } else if (source instanceof DataNode && Cleaner.this.whitelist.isSafeTag(source.parent().nodeName())) {
/* 150 */         DataNode sourceData = (DataNode)source;
/* 151 */         DataNode destData = new DataNode(sourceData.getWholeData(), source.baseUri());
/* 152 */         this.destination.appendChild((Node)destData);
/*     */       } else {
/* 154 */         this.numDiscarded++;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void tail(Node source, int depth) {
/* 159 */       if (source instanceof Element && Cleaner.this.whitelist.isSafeTag(source.nodeName())) {
/* 160 */         this.destination = (Element)this.destination.parent();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private int copySafeNodes(Element source, Element dest) {
/* 166 */     CleaningVisitor cleaningVisitor = new CleaningVisitor(source, dest);
/* 167 */     NodeTraversor traversor = new NodeTraversor(cleaningVisitor);
/* 168 */     traversor.traverse((Node)source);
/* 169 */     return cleaningVisitor.numDiscarded;
/*     */   }
/*     */   
/*     */   private ElementMeta createSafeElement(Element sourceEl) {
/* 173 */     String sourceTag = sourceEl.tagName();
/* 174 */     Attributes destAttrs = new Attributes();
/* 175 */     Element dest = new Element(Tag.valueOf(sourceTag), sourceEl.baseUri(), destAttrs);
/* 176 */     int numDiscarded = 0;
/*     */     
/* 178 */     Attributes sourceAttrs = sourceEl.attributes();
/* 179 */     for (Attribute sourceAttr : sourceAttrs) {
/* 180 */       if (this.whitelist.isSafeAttribute(sourceTag, sourceEl, sourceAttr)) {
/* 181 */         destAttrs.put(sourceAttr); continue;
/*     */       } 
/* 183 */       numDiscarded++;
/*     */     } 
/* 185 */     Attributes enforcedAttrs = this.whitelist.getEnforcedAttributes(sourceTag);
/* 186 */     destAttrs.addAll(enforcedAttrs);
/*     */     
/* 188 */     return new ElementMeta(dest, numDiscarded);
/*     */   }
/*     */   
/*     */   private static class ElementMeta {
/*     */     Element el;
/*     */     int numAttribsDiscarded;
/*     */     
/*     */     ElementMeta(Element el, int numAttribsDiscarded) {
/* 196 */       this.el = el;
/* 197 */       this.numAttribsDiscarded = numAttribsDiscarded;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/safety/Cleaner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */