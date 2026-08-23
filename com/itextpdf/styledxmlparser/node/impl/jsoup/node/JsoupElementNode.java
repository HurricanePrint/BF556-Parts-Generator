/*     */ package com.itextpdf.styledxmlparser.node.impl.jsoup.node;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*     */ import com.itextpdf.styledxmlparser.node.IAttributes;
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsoupElementNode
/*     */   extends JsoupNode
/*     */   implements IElementNode
/*     */ {
/*     */   private Element element;
/*     */   private IAttributes attributes;
/*     */   private Map<String, String> elementResolvedStyles;
/*     */   private List<Map<String, String>> customDefaultStyles;
/*  75 */   private String lang = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsoupElementNode(Element element) {
/*  83 */     super((Node)element);
/*  84 */     this.element = element;
/*  85 */     this.attributes = new JsoupAttributes(element.attributes());
/*  86 */     this.lang = getAttribute("lang");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  94 */     return this.element.nodeName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IAttributes getAttributes() {
/* 101 */     return this.attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String key) {
/* 109 */     return this.attributes.getAttribute(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStyles(Map<String, String> elementResolvedStyles) {
/* 117 */     this.elementResolvedStyles = elementResolvedStyles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getStyles() {
/* 125 */     return this.elementResolvedStyles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Map<String, String>> getAdditionalHtmlStyles() {
/* 133 */     return this.customDefaultStyles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAdditionalHtmlStyles(Map<String, String> styles) {
/* 141 */     if (this.customDefaultStyles == null) {
/* 142 */       this.customDefaultStyles = new ArrayList<>();
/*     */     }
/* 144 */     this.customDefaultStyles.add(styles);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLang() {
/* 152 */     if (this.lang != null) {
/* 153 */       return this.lang;
/*     */     }
/* 155 */     INode parent = this.parentNode;
/* 156 */     this.lang = (parent instanceof IElementNode) ? ((IElementNode)parent).getLang() : null;
/* 157 */     if (this.lang == null)
/*     */     {
/*     */       
/* 160 */       this.lang = "";
/*     */     }
/* 162 */     return this.lang;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String text() {
/* 172 */     return this.element.text();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/node/JsoupElementNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */