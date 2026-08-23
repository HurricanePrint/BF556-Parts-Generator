/*     */ package com.itextpdf.styledxmlparser.css.selector.item;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
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
/*     */ public class CssAttributeSelectorItem
/*     */   implements ICssSelectorItem
/*     */ {
/*     */   private String property;
/*  62 */   private char matchSymbol = Character.MIN_VALUE;
/*     */ 
/*     */   
/*  65 */   private String value = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CssAttributeSelectorItem(String attrSelector) {
/*  73 */     int indexOfEqual = attrSelector.indexOf('=');
/*  74 */     if (indexOfEqual == -1) {
/*  75 */       this.property = attrSelector.substring(1, attrSelector.length() - 1);
/*     */     } else {
/*  77 */       if (attrSelector.charAt(indexOfEqual + 1) == '"' || attrSelector.charAt(indexOfEqual + 1) == '\'') {
/*  78 */         this.value = attrSelector.substring(indexOfEqual + 2, attrSelector.length() - 2);
/*     */       } else {
/*  80 */         this.value = attrSelector.substring(indexOfEqual + 1, attrSelector.length() - 1);
/*     */       } 
/*  82 */       this.matchSymbol = attrSelector.charAt(indexOfEqual - 1);
/*  83 */       if ("~^$*|".indexOf(this.matchSymbol) == -1) {
/*  84 */         this.matchSymbol = Character.MIN_VALUE;
/*  85 */         this.property = attrSelector.substring(1, indexOfEqual);
/*     */       } else {
/*  87 */         this.property = attrSelector.substring(1, indexOfEqual - 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpecificity() {
/*  97 */     return 1024;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(INode node) {
/*     */     String pattern;
/* 105 */     if (!(node instanceof IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/* 106 */       return false;
/*     */     }
/* 108 */     IElementNode element = (IElementNode)node;
/* 109 */     String attributeValue = element.getAttribute(this.property);
/* 110 */     if (attributeValue == null) {
/* 111 */       return false;
/*     */     }
/* 113 */     if (this.value == null) {
/* 114 */       return true;
/*     */     }
/* 116 */     switch (this.matchSymbol) {
/*     */       case '\000':
/* 118 */         return this.value.equals(attributeValue);
/*     */       case '|':
/* 120 */         return (this.value.length() > 0 && attributeValue.startsWith(this.value) && (attributeValue.length() == this.value.length() || attributeValue.charAt(this.value.length()) == '-'));
/*     */       case '^':
/* 122 */         return (this.value.length() > 0 && attributeValue.startsWith(this.value));
/*     */       case '$':
/* 124 */         return (this.value.length() > 0 && attributeValue.endsWith(this.value));
/*     */       case '~':
/* 126 */         pattern = MessageFormatUtil.format("(^{0}\\s+)|(\\s+{1}\\s+)|(\\s+{2}$)", new Object[] { this.value, this.value, this.value });
/* 127 */         return Pattern.compile(pattern).matcher(attributeValue).matches();
/*     */       case '*':
/* 129 */         return (this.value.length() > 0 && attributeValue.contains(this.value));
/*     */     } 
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     if (this.value == null) {
/* 142 */       return MessageFormatUtil.format("[{0}]", new Object[] { this.property });
/*     */     }
/* 144 */     return MessageFormatUtil.format("[{0}{1}=\"{2}\"]", new Object[] { this.property, (this.matchSymbol == '\000') ? "" : String.valueOf(this.matchSymbol), this.value });
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssAttributeSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */