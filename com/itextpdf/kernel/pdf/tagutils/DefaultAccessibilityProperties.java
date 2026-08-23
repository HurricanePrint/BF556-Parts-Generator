/*     */ package com.itextpdf.kernel.pdf.tagutils;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ public class DefaultAccessibilityProperties
/*     */   extends AccessibilityProperties
/*     */ {
/*     */   private static final long serialVersionUID = 3139055327755008473L;
/*     */   protected String role;
/*     */   protected String language;
/*     */   protected String actualText;
/*     */   protected String alternateDescription;
/*     */   protected String expansion;
/*  61 */   protected List<PdfStructureAttributes> attributesList = new ArrayList<>();
/*     */   
/*     */   protected String phoneme;
/*     */   protected String phoneticAlphabet;
/*     */   protected PdfNamespace namespace;
/*  66 */   protected List<TagTreePointer> refs = new ArrayList<>();
/*     */   
/*     */   public DefaultAccessibilityProperties(String role) {
/*  69 */     this.role = role;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRole() {
/*  74 */     return this.role;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setRole(String role) {
/*  79 */     this.role = role;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLanguage() {
/*  85 */     return this.language;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setLanguage(String language) {
/*  90 */     this.language = language;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getActualText() {
/*  96 */     return this.actualText;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setActualText(String actualText) {
/* 101 */     this.actualText = actualText;
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAlternateDescription() {
/* 107 */     return this.alternateDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setAlternateDescription(String alternateDescription) {
/* 112 */     this.alternateDescription = alternateDescription;
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getExpansion() {
/* 118 */     return this.expansion;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setExpansion(String expansion) {
/* 123 */     this.expansion = expansion;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties addAttributes(PdfStructureAttributes attributes) {
/* 129 */     return addAttributes(-1, attributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties addAttributes(int index, PdfStructureAttributes attributes) {
/* 134 */     if (attributes != null) {
/* 135 */       if (index > 0) {
/* 136 */         this.attributesList.add(index, attributes);
/*     */       } else {
/* 138 */         this.attributesList.add(attributes);
/*     */       } 
/*     */     }
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties clearAttributes() {
/* 146 */     this.attributesList.clear();
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PdfStructureAttributes> getAttributesList() {
/* 152 */     return this.attributesList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhoneme() {
/* 157 */     return this.phoneme;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setPhoneme(String phoneme) {
/* 162 */     this.phoneme = phoneme;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhoneticAlphabet() {
/* 168 */     return this.phoneticAlphabet;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setPhoneticAlphabet(String phoneticAlphabet) {
/* 173 */     this.phoneticAlphabet = phoneticAlphabet;
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfNamespace getNamespace() {
/* 179 */     return this.namespace;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties setNamespace(PdfNamespace namespace) {
/* 184 */     this.namespace = namespace;
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties addRef(TagTreePointer treePointer) {
/* 190 */     this.refs.add(new TagTreePointer(treePointer));
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TagTreePointer> getRefsList() {
/* 196 */     return Collections.unmodifiableList(this.refs);
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessibilityProperties clearRefs() {
/* 201 */     this.refs.clear();
/* 202 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/tagutils/DefaultAccessibilityProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */