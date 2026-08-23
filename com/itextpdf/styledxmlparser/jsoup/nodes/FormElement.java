/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.KeyVal;
/*     */ import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
/*     */ import com.itextpdf.styledxmlparser.jsoup.select.Elements;
/*     */ import java.util.ArrayList;
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
/*     */ public class FormElement
/*     */   extends Element
/*     */ {
/*  56 */   private final Elements elements = new Elements();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FormElement(Tag tag, String baseUri, Attributes attributes) {
/*  66 */     super(tag, baseUri, attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Elements elements() {
/*  74 */     return this.elements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FormElement addElement(Element element) {
/*  83 */     this.elements.add(element);
/*  84 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<KeyVal> formData() {
/* 111 */     ArrayList<KeyVal> data = new ArrayList<>();
/*     */ 
/*     */     
/* 114 */     for (Element el : this.elements) {
/* 115 */       if (!el.tag().isFormSubmittable() || 
/* 116 */         el.hasAttr("disabled"))
/* 117 */         continue;  String name = el.attr("name");
/* 118 */       if (name.length() == 0)
/* 119 */         continue;  String type = el.attr("type");
/*     */       
/* 121 */       if ("select".equals(el.tagName())) {
/* 122 */         Elements options = el.select("option[selected]");
/* 123 */         boolean set = false;
/* 124 */         for (Element option : options) {
/* 125 */           data.add(KeyVal.create(name, option.val()));
/* 126 */           set = true;
/*     */         } 
/* 128 */         if (!set) {
/* 129 */           Element option = el.select("option").first();
/* 130 */           if (option != null)
/* 131 */             data.add(KeyVal.create(name, option.val())); 
/*     */         }  continue;
/* 133 */       }  if ("checkbox".equalsIgnoreCase(type) || "radio".equalsIgnoreCase(type)) {
/*     */         
/* 135 */         if (el.hasAttr("checked")) {
/* 136 */           String val = (el.val().length() > 0) ? el.val() : "on";
/* 137 */           data.add(KeyVal.create(name, val));
/*     */         }  continue;
/*     */       } 
/* 140 */       data.add(KeyVal.create(name, el.val()));
/*     */     } 
/*     */     
/* 143 */     return data;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/FormElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */